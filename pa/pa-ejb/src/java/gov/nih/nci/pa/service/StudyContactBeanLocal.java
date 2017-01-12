/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StructuralRole;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudyContactConverter;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.StudyContactSortCriterion;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class StudyContactBeanLocal extends AbstractRoleIsoService<StudyContactDTO, StudyContact, StudyContactConverter>
        implements StudyContactServiceLocal {

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyContactDTO> search(StudyContactDTO dto, LimitOffset pagingParams) throws PAException,
    TooManyResultsException {
        if (dto == null) {
            throw new PAException("StudyContactDTO should not be null ");
        }

        StudyContact criteria = Converters.get(StudyContactConverter.class).convertFromDtoToDomain(dto);
        criteria.setStatusCode(null);

        if (dto.getStatusCode() != null) {
          criteria.setStatusCode(FunctionalRoleStatusCode.getByCode(dto.getStatusCode().getCode()));
        }

        int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
        PageSortParams<StudyContact> params = new PageSortParams<StudyContact>(maxLimit, pagingParams.getOffset(),
                    StudyContactSortCriterion.STUDY_CONTACT_ID, false);
        List<StudyContact> studyContactList = search(new AnnotatedBeanSearchCriteria<StudyContact>(criteria), params);

        if (studyContactList.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        }
        return convertFromDomainToDTOs(studyContactList);
    }

    /**
     * @param dto dto
     * @throws PAException e
     * @return updated dto
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StudyContactDTO update(StudyContactDTO dto) throws PAException {
        validate(dto);
        getStatusCode(dto);
        return super.update(dto);
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StudyContactDTO nullify(final StudyContactDTO scDto) throws PAException {
        scDto.setStatusCode(CdConverter
                .convertToCd(FunctionalRoleStatusCode.NULLIFIED));
        return super.update(scDto);
    }

    /**
     * validates the dto.
     * @param dto dto to validate.
     * @throws PAException e
     */
    @Override
    public void validate(StudyContactDTO dto) throws PAException {
        PAServiceUtils paServiceUtil = new PAServiceUtils();
        if (!ISOUtil.isIiNull(dto.getClinicalResearchStaffIi()) && !PAUtil.isDSetTelNull(dto.getTelecomAddresses())) {
            StructuralRole sr = paServiceUtil.getStructuralRole(IiConverter.convertToPoClinicalResearchStaffIi(dto
                .getClinicalResearchStaffIi().getExtension()));
            if (sr != null) {
                ClinicalResearchStaffDTO poSrDto = (ClinicalResearchStaffDTO) paServiceUtil
                    .getCorrelationByIi(IiConverter.convertToPoClinicalResearchStaffIi(sr.getIdentifier()));

                paServiceUtil.validateAndFormatPhoneNumber(poSrDto.getPlayerIdentifier(), dto.getTelecomAddresses());
            }
        }
    }

    /**
     * @param dto dto to validate
     * @throws PAException e
     * @return dto
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public StudyContactDTO create(StudyContactDTO dto) throws PAException {
        validate(dto);
        return super.create(dto);
    }

    private void getStatusCode(StudyContactDTO dto) throws PAException {
        PAServiceUtils paServiceUtil = new PAServiceUtils();
        StructuralRole sr =  null;
        if (!ISOUtil.isIiNull(dto.getClinicalResearchStaffIi())) {
            sr = paServiceUtil.getStructuralRole(IiConverter.convertToPoClinicalResearchStaffIi(
                    dto.getClinicalResearchStaffIi().getExtension()));
        }
        if (!ISOUtil.isIiNull(dto.getHealthCareProviderIi())) {
            sr = paServiceUtil.getStructuralRole(IiConverter.convertToPoHealthcareProviderIi(
                    dto.getHealthCareProviderIi().getExtension()));
        }
        if (!ISOUtil.isIiNull(dto.getOrganizationalContactIi())) {
            sr = paServiceUtil.getStructuralRole(IiConverter.convertToPoOrganizationalContactIi(
                    dto.getOrganizationalContactIi().getExtension()));
        }
        if (sr != null) {
               dto.setStatusCode(getFunctionalRoleStatusCode(CdConverter.convertStringToCd(
                       sr.getStatusCode().getCode()), ActStatusCode.ACTIVE));
         }

    }

    @Override
    public StudyContactDTO getResponsiblePartyContact(Ii studyProtocolIi)
            throws PAException {
        List<StudyContactDTO> scDtos = getResponsiblePartyContacts(studyProtocolIi);
        return scDtos.isEmpty() ? null : scDtos.get(0);
    }

    /**
     * @param studyProtocolIi
     * @return
     * @throws PAException
     */
    private List<StudyContactDTO> getResponsiblePartyContacts(Ii studyProtocolIi)
            throws PAException {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter
                .convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos = getByStudyProtocol(studyProtocolIi,
                scDto);
        if (scDtos.isEmpty()) {
            scDto = new StudyContactDTO();
            scDto.setRoleCode(CdConverter
                    .convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_INVESTIGATOR));
            scDtos.addAll(getByStudyProtocol(studyProtocolIi, scDto));
        }
        return scDtos;
    }

    @Override
    public void removeResponsiblePartyContact(Ii studyProtocolIi)
            throws PAException {
        for (StudyContactDTO dto : getResponsiblePartyContacts(studyProtocolIi)) {
            delete(dto.getIdentifier());
        }
    }

   
}

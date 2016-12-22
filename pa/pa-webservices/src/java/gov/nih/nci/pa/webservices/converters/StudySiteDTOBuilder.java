/**
 * 
 */
package gov.nih.nci.pa.webservices.converters;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.webservices.types.BaseParticipatingSite;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

/**
 * @author dkrylov
 * 
 */
public class StudySiteDTOBuilder {

    /**
     * @param clinicalTrialsDotGovTrialID
     *            clinicalTrialsDotGovTrialID
     * @return StudySiteDTO
     * @throws PAException
     *             PAException
     */
    public StudySiteDTO buildClinicalTrialsGovIdAssigner(
            String clinicalTrialsDotGovTrialID) throws PAException {
        final String type = PAConstants.NCT_IDENTIFIER_TYPE;
        return buildIdAssignerSite(clinicalTrialsDotGovTrialID, type);
    }

    /**
     * @param ctepID
     *            ctepID
     * @return StudySiteDTO
     * @throws PAException
     *             PAException
     */
    public StudySiteDTO buildCtepIdAssigner(String ctepID) throws PAException {
        final String type = PAConstants.CTEP_IDENTIFIER_TYPE;
        return buildIdAssignerSite(ctepID, type);
    }

    /**
     * @param dcpID
     *            dcpID
     * @return StudySiteDTO
     * @throws PAException
     *             PAException
     */
    public StudySiteDTO buildDcpIdAssigner(String dcpID) throws PAException {
        final String type = PAConstants.DCP_IDENTIFIER_TYPE;
        return buildIdAssignerSite(dcpID, type);
    }

    /**
     * @param value
     * @param type
     * @return
     * @throws PAException
     */
    private StudySiteDTO buildIdAssignerSite(String value, final String type)
            throws PAException {
        StudySiteDTO isoDto = new StudySiteDTO();
        if (StringUtils.isNotEmpty(value)) {
            String poOrgId = PaRegistry.getOrganizationCorrelationService()
                    .getPOOrgIdentifierByIdentifierType(type);
            Ii nctROIi = PaRegistry.getOrganizationCorrelationService()
                    .getPoResearchOrganizationByEntityIdentifier(
                            IiConverter.convertToPoOrganizationIi(String
                                    .valueOf(poOrgId)));
            isoDto.setResearchOrganizationIi(nctROIi);
            isoDto.setLocalStudyProtocolIdentifier(StConverter
                    .convertToSt(value));
        }
        return isoDto;
    }

    /**
     * @param ps
     *            ParticipatingSite
     * @return StudySiteDTO
     */
    public StudySiteDTO build(BaseParticipatingSite ps) {
        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setStatusCode(CdConverter
                .convertToCd(FunctionalRoleStatusCode.PENDING));
        studySiteDTO.setStatusDateRange(IvlConverter.convertTs().convertToIvl(
                new Timestamp(ps.getRecruitmentStatusDate()
                        .toGregorianCalendar().getTimeInMillis()), null));
        studySiteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(ps
                .getLocalTrialIdentifier()));
        studySiteDTO.setProgramCodeText(StConverter.convertToSt(ps
                .getProgramCode()));
        studySiteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(
                ps.getOpenedForAccrual() != null ? ps.getOpenedForAccrual()
                        .toGregorianCalendar().getTime() : null,
                ps.getClosedForAccrual() != null ? ps.getClosedForAccrual()
                        .toGregorianCalendar().getTime() : null));
        if (ps.getTargetAccrualNumber() != null) {
            studySiteDTO.setTargetAccrualNumber(IntConverter.convertToInt(ps
                    .getTargetAccrualNumber().intValue()));
        }
        return studySiteDTO;
    }

}

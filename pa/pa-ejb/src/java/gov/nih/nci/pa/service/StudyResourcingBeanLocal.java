/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.logging.api.util.StringUtils;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.convert.StudyResourcingConverter;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.RealConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.exception.PAValidationException.Level;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.I2EGrantsServiceLocal;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;


/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class StudyResourcingBeanLocal extends
        AbstractStudyIsoService<StudyResourcingDTO, StudyResourcing, StudyResourcingConverter> implements
        StudyResourcingServiceLocal {
    private PAServiceUtils paServiceUtils = new PAServiceUtils();
    
    private static final Logger LOG = Logger.getLogger(StudyResourcingBeanLocal.class);

    private static final double MAX_FUNDING_PCT = 100d;

    @EJB 
    private LookUpTableServiceRemote lookUpTableSvc;
    @EJB
    private StudyProtocolServiceLocal studyProtocolSvc;
    @EJB
    private ProtocolQueryServiceLocal protocolQueryService;
    @EJB
    private I2EGrantsServiceLocal i2eSvc;

    private static final String STAT_P30 = "P30stat";
    private static final String STAT_CA = "CAstat";
    private static final String STAT_FUNDING_PCT = "FUNDINGstat";

    /**
     * @param paServiceUtils the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

    /**
     * @param lookUpTableSvc the lookUpTableSvc to set
     */
    public void setLookUpTableSvc(LookUpTableServiceRemote lookUpTableSvc) {
        this.lookUpTableSvc = lookUpTableSvc;
    }

    /**
     * @param studyProtocolSvc the studyProtocolSvc to set
     */
    public void setStudyProtocolSvc(StudyProtocolServiceLocal studyProtocolSvc) {
        this.studyProtocolSvc = studyProtocolSvc;
    }

    /**
     * @param i2eSvc the i2eSvc to set
     */
    public void setI2eSvc(I2EGrantsServiceLocal i2eSvc) {
        this.i2eSvc = i2eSvc;
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyResourcingDTO> getSummary4ReportedResourcing(Ii studyProtocolIi) throws PAException {
        if (ISOUtil.isIiNull(studyProtocolIi)) {
            throw new PAException("studyProtocol Identifier should not be null");
        }

        StudyResourcing criteria = new StudyResourcing();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(IiConverter.convertToLong(studyProtocolIi));
        criteria.setStudyProtocol(sp);
        criteria.setSummary4ReportedResourceIndicator(Boolean.TRUE);

        List<StudyResourcing> results = search(new AnnotatedBeanSearchCriteria<StudyResourcing>(criteria));

        return convertFromDomainToDTOs(results);
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyResourcingDTO getSummary4ReportedResourcingBySpAndOrgId(Ii studyProtocolIi, Long orgId) 
            throws PAException {
        if (ISOUtil.isIiNull(studyProtocolIi)) {
            throw new PAException("studyProtocol Identifier should not be null");
        }

        StudyResourcing criteria = new StudyResourcing();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(IiConverter.convertToLong(studyProtocolIi));
        criteria.setStudyProtocol(sp);
        criteria.setSummary4ReportedResourceIndicator(Boolean.TRUE);
        criteria.setOrganizationIdentifier(orgId.toString());

        List<StudyResourcing> results = search(new AnnotatedBeanSearchCriteria<StudyResourcing>(criteria));

        return results.isEmpty() ? null : convertFromDomainToDto(results.get(0));
    }

    /**
     * {@inheritDoc}
     */
    public StudyResourcingDTO updateStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException {
        validate(studyResourcingDTO);

        StudyResourcing studyResourcing = convertFromDtoToDomain(super.get(studyResourcingDTO.getIdentifier()));
        // set the values from parameter
        if (studyResourcingDTO.getTypeCode() != null) {
            studyResourcing.setTypeCode(SummaryFourFundingCategoryCode.getByCode(
                    studyResourcingDTO.getTypeCode().getCode()));
        }
        studyResourcing.setOrganizationIdentifier(IiConverter.convertToString(
                studyResourcingDTO.getOrganizationIdentifier()));
        studyResourcing.setFundingMechanismCode(CdConverter.convertCdToString(
                studyResourcingDTO.getFundingMechanismCode()));
        studyResourcing.setNciDivisionProgramCode(NciDivisionProgramCode.getByCode(
                studyResourcingDTO.getNciDivisionProgramCode().getCode()));
        studyResourcing.setNihInstituteCode(studyResourcingDTO.getNihInstitutionCode().getCode());
        studyResourcing.setSerialNumber(StConverter.convertToString(studyResourcingDTO.getSerialNumber()));
        studyResourcing.setFundingPercent(RealConverter.convertToDouble(studyResourcingDTO.getFundingPercent()));
        return super.update(convertFromDomainToDto(studyResourcing));
    }

    /**
     * {@inheritDoc}
     */
    public StudyResourcingDTO createStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException {
        validate(studyResourcingDTO);
        studyResourcingDTO.setActiveIndicator(BlConverter.convertToBl(Boolean.TRUE));
        return super.create(studyResourcingDTO);
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyResourcingDTO> getStudyResourcingByStudyProtocol(Ii studyProtocolIi) throws PAException {
        if (ISOUtil.isIiNull(studyProtocolIi)) {
            throw new PAException("studyProtocol Identifier should not be null.");
        }

        StudyResourcing criteria = new StudyResourcing();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(IiConverter.convertToLong(studyProtocolIi));
        criteria.setStudyProtocol(sp);
        criteria.setSummary4ReportedResourceIndicator(Boolean.FALSE);
        List<StudyResourcing> results = search(new AnnotatedBeanSearchCriteria<StudyResourcing>(criteria));
        return convertFromDomainToDTOs(results);
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyResourcingDTO getStudyResourcingById(Ii studyResourceIi) throws PAException {
        return get(studyResourceIi);
    }

    /**
     * {@inheritDoc}
     */
    public Boolean deleteStudyResourcingById(StudyResourcingDTO studyResourcingDTO) throws PAException {
        if (studyResourcingDTO == null) {
            throw new PAException("studyResourcingDTO should not be null.");
        }
        StudyResourcingDTO toDelete = super.get(studyResourcingDTO.getIdentifier());
        toDelete.setActiveIndicator(BlConverter.convertToBl(Boolean.FALSE));
        toDelete.setInactiveCommentText(studyResourcingDTO.getInactiveCommentText());
        super.update(toDelete);
        return Boolean.FALSE;
    }

    private boolean enforceNoDuplicate(StudyResourcingDTO dto) throws PAException {
        boolean duplicateExists = false;
        if (!ISOUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            //this means it is original submission thats why not having Ii
            List<StudyResourcingDTO> spList = getStudyResourcingByStudyProtocol(dto.getStudyProtocolIdentifier());
            for (StudyResourcingDTO sp : spList) {
                if (paServiceUtils.isGrantDuplicate(dto, sp) && (dto.getIdentifier() == null
                        || (!dto.getIdentifier().getExtension().equals(sp.getIdentifier().getExtension())))) {
                    duplicateExists = true;
                    break;
                }
            }
        }
        return duplicateExists;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Method method, Boolean nciFunded, String nciTrialIdentifier, Long leadOrgPoId,
            List<StudyResourcingDTO> dtos) throws PAException {
        Map<String, Object> stats = new HashMap<String, Object>();
        stats.put(STAT_CA, Boolean.FALSE);
        stats.put(STAT_P30, Boolean.FALSE);
        stats.put(STAT_FUNDING_PCT, Double.valueOf(0d));
        Set<Long> studyResourcingIds  = new HashSet<Long>();
        if (dtos != null) {
            for (StudyResourcingDTO dto : dtos) {
                studyResourcingIds.add(IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
                validate(dto);
                updateStats(stats, dto);
            }
        }
        StudyProtocolDTO existingSp = new StudyProtocolDTO();
        if (!StringUtils.isBlank(nciTrialIdentifier)) {
            Ii spIi = IiConverter.convertToStudyProtocolIi(0L);
            spIi.setExtension(nciTrialIdentifier);
            existingSp = studyProtocolSvc.getStudyProtocol(spIi);
            if (BlConverter.convertToBool(existingSp.getProprietaryTrialIndicator())) {
                return;  // grants information not used for industrial trials
            }
            updateStatsWithExistingGrants(stats, existingSp, studyResourcingIds);
        }
        if (grantsRequiredChecksActive(method)) {
            if (leadOrgPoId == null) {
                // if lead org not passed in look up
                StudyProtocolQueryDTO spQryDto = protocolQueryService.getTrialSummaryByStudyProtocolId(
                        IiConverter.convertToLong(existingSp.getIdentifier()));
                p30GrantsValidation(spQryDto == null ? null : spQryDto.getLeadOrganizationPOId(), stats);
            } else {
                p30GrantsValidation(leadOrgPoId, stats);
            }
            caGrantsValidation(nciFunded, stats);
        }
        if (method == Method.ABSTRACTION_VALIDATION) {
            if ((Double) stats.get(STAT_FUNDING_PCT) > MAX_FUNDING_PCT) {
                throw new PAValidationException(
                        "Total percent of grant funding this trial for all grants cannot be greater than 100.");
            }
            List<StudyResourcingDTO> existing = ISOUtil.isIiNull(existingSp.getIdentifier()) 
                    ? new ArrayList<StudyResourcingDTO>() : getByStudyProtocol(existingSp.getIdentifier());
            for (StudyResourcingDTO dto : existing) {
                String instCode = CdConverter.convertCdToString(dto.getNihInstitutionCode());
                String sn = StConverter.convertToString(dto.getSerialNumber());
                if ("CA".equals(instCode) && !i2eSvc.isValidCaGrant(sn)) {
                    throw new PAValidationException("Serial number " + sn 
                            + " was not found in the I2E Grants database for CA grants.", Level.WARN);
                }
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public PAException validateNoException(Method method, Boolean nciFunded,
            String nciTrialIdentifier, Long leadOrgPoId,
            List<StudyResourcingDTO> dtos) {
        try {
            this.validate(method, nciFunded, nciTrialIdentifier, leadOrgPoId,
                    dtos);
            return null;
        } catch (PAException e) {
            return e;
        }
    }

    private boolean isActiveGrantRecord(StudyResourcingDTO dto) {
        Boolean isActive = BlConverter.convertToBoolean(dto.getActiveIndicator());
        boolean isSumm4 = BlConverter.convertToBool(dto.getSummary4ReportedResourceIndicator());
        return (null == isActive || isActive) && !isSumm4;
    }

    private void updateStatsWithExistingGrants(Map<String, Object> stats, StudyProtocolDTO spDTO, 
            Set<Long> submittedGrants) throws PAException {
        if (spDTO == null) {
            return;
        }
        List<StudyResourcingDTO> existing = getByStudyProtocol(spDTO.getIdentifier());
        for (StudyResourcingDTO dto : existing) {
            if (!submittedGrants.contains(IiConverter.convertToLong(dto.getIdentifier()))) {
                updateStats(stats, dto);
            }
        }

    }

    private void updateStats(Map<String, Object> stats, StudyResourcingDTO dto) {
        if (isActiveGrantRecord(dto)) {
            if ("P30".equals(CdConverter.convertCdToString(dto.getFundingMechanismCode()))) {
                stats.put(STAT_P30, Boolean.TRUE);
            }
            if ("CA".equals(CdConverter.convertCdToString(dto.getNihInstitutionCode()))) {
                stats.put(STAT_CA, Boolean.TRUE);
            }
            if (!ISOUtil.isRealNull(dto.getFundingPercent())) {
                stats.put(STAT_FUNDING_PCT, ((Double) stats.get(STAT_FUNDING_PCT))
                        + RealConverter.convertToDouble(dto.getFundingPercent()));
            }
        }
    }

    private void p30GrantsValidation(Long leadOrgPoId, Map<String, Object> stats) throws PAException {
        boolean isCancerCenter = null != FamilyHelper.getP30GrantSerialNumber(leadOrgPoId);
        if (isCancerCenter && !((Boolean) stats.get(STAT_P30))) {
            throw new PAValidationException("A valid P30 grant record must be added.");
        }
    }

    private void caGrantsValidation(Boolean nciFunded, Map<String, Object> stats) throws PAException {
        boolean caGrants = (Boolean) stats.get(STAT_CA);
        if ((nciFunded == null) || nciFunded) {
            if (!caGrants) {
                throw new PAValidationException(
                        "This trial is funded by NCI; however, an NCI grant record was not entered.");
            }
        } else {
            if (caGrants) {
                throw new PAValidationException(
                        "This trial is not funded by NCI; however, an NCI grant record was entered.");
            }
        }
    }

    boolean grantsRequiredChecksActive(Method method) throws PAException {
        String activeDtStr;
        switch (method) {
        case BATCH: activeDtStr = lookUpTableSvc.getPropertyValue("GrantsRequiredBatchRegEffectiveDate"); break;
        case SERVICE: activeDtStr = lookUpTableSvc.getPropertyValue("GrantsRequiredRegServiceEffectiveDate"); break;
        default: activeDtStr = "01-JAN-2013";
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("dd-MMM-yyyy");
        Date activeDt;
        try {
            activeDt = sdf.parse(activeDtStr);
        } catch (ParseException e) {
            throw new PAException("Error parsing Grants effective date string: " + activeDtStr, e);
        }
        return (new Date()).after(activeDt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(StudyResourcingDTO studyResourcingDTO) throws PAException {
        if (studyResourcingDTO == null) {
            throw new PAValidationException("studyResourcingDTO should not be null");
        }
        StringBuffer errorBuffer =  new StringBuffer();
        if (!BooleanUtils.toBooleanDefaultIfNull(
                BlConverter.convertToBoolean(studyResourcingDTO.getSummary4ReportedResourceIndicator()), true)) {
            validateGrantInformation(studyResourcingDTO);
            //check if NIH institute code exists
            isCodeExists(studyResourcingDTO.getNihInstitutionCode(), errorBuffer, "NIH_INSTITUTE",
                    "nih_institute_code");
            isCodeExists(studyResourcingDTO.getFundingMechanismCode(), errorBuffer, "FUNDING_MECHANISM",
                    "funding_mechanism_code");
            validateSerialNo(studyResourcingDTO, errorBuffer);
            if (enforceNoDuplicate(studyResourcingDTO)) {
                errorBuffer.append("Duplicate grants are not allowed.\n");
            }
        }
        if (errorBuffer.length() > 0) {
            throw new PAValidationException("Validation Exception " + errorBuffer.toString());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyResourcingDTO create(StudyResourcingDTO dto) throws PAException {
        if (ISOUtil.isBlNull(dto.getSummary4ReportedResourceIndicator())) {
            throw new PAException("The summary4ReportedResourceIndicator is not set.");
        }
        if (!BooleanUtils.toBooleanDefaultIfNull(
                BlConverter.convertToBoolean(dto.getSummary4ReportedResourceIndicator()), true)) {
            return createStudyResourcing(dto);
        }
        return super.create(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyResourcingDTO update(StudyResourcingDTO dto) throws PAException {
        if (ISOUtil.isBlNull(dto.getSummary4ReportedResourceIndicator())) {
            throw new PAException("The summary4ReportedResourceIndicator is not set.");
        }
        if (!BooleanUtils.toBooleanDefaultIfNull(
                BlConverter.convertToBoolean(dto.getSummary4ReportedResourceIndicator()), true)) {
            return updateStudyResourcing(dto);
        }
        return super.update(dto);

    }

    private void validateGrantInformation(StudyResourcingDTO dto) throws PAException {
        if (ISOUtil.isCdNull(dto.getFundingMechanismCode())) {
            throw new PAValidationException("The funding mechanism code is not set.");
        }
        if (ISOUtil.isCdNull(dto.getNihInstitutionCode())) {
            throw new PAValidationException("The NIH Institution code is not set.");
        }
        if (ISOUtil.isStNull(dto.getSerialNumber())) {
            throw new PAValidationException("The serial number is not set.");
        }
        if (ISOUtil.isCdNull(dto.getNciDivisionProgramCode())) {
            throw new PAValidationException("The NCI Division/Program code is not set.");
        }
    }


    private void validateSerialNo(StudyResourcingDTO studyResourcingDTO, StringBuffer errorBuffer) {
        if (!ISOUtil.isStNull(studyResourcingDTO.getSerialNumber())) {
            String snValue = studyResourcingDTO.getSerialNumber().getValue();
            if (!NumberUtils.isDigits(snValue)) {
                errorBuffer.append("Grant serial number should have numbers from [0-9]\n");
            }
        }
    }

    private void isCodeExists(Cd code, StringBuffer errorBuffer, String tableName, String codeName) throws PAException {
        if (!ISOUtil.isCdNull(code)) {
            boolean isExists = PADomainUtils.checkIfValueExists(code.getCode(), tableName, codeName);
            if (!isExists) {
                errorBuffer.append("Error while checking for value ")
                .append(code.getCode()).append(" from table ").append(tableName).append("\n.");
            }
        }
    }

    @Override
    public List<StudyResourcingDTO> getActiveStudyResourcingByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        final List<StudyResourcingDTO> list = getStudyResourcingByStudyProtocol(studyProtocolIi);
        CollectionUtils.filter(list, new Predicate() {
            @Override
            public boolean evaluate(Object arg) {
                StudyResourcingDTO dto = (StudyResourcingDTO) arg;
                return !Boolean.FALSE.equals(BlConverter.convertToBoolean(dto
                        .getActiveIndicator()));
            }
        });
        return list;
    }

    @Override
    public void matchToExistentGrants(
            List<StudyResourcingDTO> studyResourcingDTOs, Ii identifier)
            throws PAException {
        if (studyResourcingDTOs != null) {
            List<StudyResourcingDTO> existentGrants = getStudyResourcingByStudyProtocol(identifier);
            for (StudyResourcingDTO existentGrant : existentGrants) {
                for (StudyResourcingDTO newGrant : studyResourcingDTOs) {
                    if (ISOUtil.isIiNull(newGrant.getIdentifier())
                            && paServiceUtils.isGrantDuplicate(newGrant,
                                    existentGrant)) {
                        try {
                            PropertyUtils.copyProperties(newGrant,
                                    existentGrant);
                        } catch (Exception e) {
                            LOG.error(e, e);
                        }
                    }
                }
            }
        }

    }
}

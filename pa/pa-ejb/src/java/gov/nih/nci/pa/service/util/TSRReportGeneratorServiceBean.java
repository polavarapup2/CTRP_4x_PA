/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.service.util;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.dto.EligibilityCriteriaDTO;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.OutcomeMeasureTypeCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.dto.StratumGroupDTO;
import gov.nih.nci.pa.iso.dto.StudyAlternateTitleDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolAssociationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.PqConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.StratumGroupServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyIdentifiersServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceCachingDecorator;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.report.AbstractTsrReportGenerator;
import gov.nih.nci.pa.service.util.report.HtmlTsrReportGenerator;
import gov.nih.nci.pa.service.util.report.PdfTsrReportGenerator;
import gov.nih.nci.pa.service.util.report.RtfTsrReportGenerator;
import gov.nih.nci.pa.service.util.report.TSRErrorReport;
import gov.nih.nci.pa.service.util.report.TSRReport;
import gov.nih.nci.pa.service.util.report.TSRReportArmGroup;
import gov.nih.nci.pa.service.util.report.TSRReportAssociatedTrial;
import gov.nih.nci.pa.service.util.report.TSRReportCollaborator;
import gov.nih.nci.pa.service.util.report.TSRReportDiseaseCondition;
import gov.nih.nci.pa.service.util.report.TSRReportEligibilityCriteria;
import gov.nih.nci.pa.service.util.report.TSRReportGeneralTrialDetails;
import gov.nih.nci.pa.service.util.report.TSRReportHumanSubjectSafety;
import gov.nih.nci.pa.service.util.report.TSRReportIndIde;
import gov.nih.nci.pa.service.util.report.TSRReportIntervention;
import gov.nih.nci.pa.service.util.report.TSRReportInvestigator;
import gov.nih.nci.pa.service.util.report.TSRReportLabelText;
import gov.nih.nci.pa.service.util.report.TSRReportNihGrant;
import gov.nih.nci.pa.service.util.report.TSRReportOutcomeMeasure;
import gov.nih.nci.pa.service.util.report.TSRReportParticipatingSite;
import gov.nih.nci.pa.service.util.report.TSRReportPlannedMarker;
import gov.nih.nci.pa.service.util.report.TSRReportRegulatoryInformation;
import gov.nih.nci.pa.service.util.report.TSRReportStatusDate;
import gov.nih.nci.pa.service.util.report.TSRReportSubGroupStratificationCriteria;
import gov.nih.nci.pa.service.util.report.TSRReportSummary4Information;
import gov.nih.nci.pa.service.util.report.TSRReportTrialDesign;
import gov.nih.nci.pa.service.util.report.TSRReportTrialIdentification;
import gov.nih.nci.pa.util.CacheUtils;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

/**
 * service bean for generating TSR.
 *
 * @author Naveen Amiruddin , Kalpana Guthikonda
 * @author Krishna Kanchinadam
 * @since 01/21/2009
 */
@SuppressWarnings("PMD")
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class TSRReportGeneratorServiceBean implements TSRReportGeneratorServiceLocal, TSRReportGeneratorServiceRemote {

    private static final String N_A = "N/A";
    @EJB
    private StudyOverallStatusServiceLocal studyOverallStatusService;
    @EJB
    private StudyIndldeServiceLocal studyIndldeService;
    @EJB
    private StudyDiseaseServiceLocal studyDiseaseService;
    @EJB
    private ArmServiceLocal armService;
    @EJB
    private PlannedActivityServiceLocal plannedActivityService;
    @EJB
    private StudySiteServiceLocal studySiteService;
    @EJB
    private StudySiteContactServiceLocal studySiteContactService;
    @EJB
    private StudyContactServiceLocal studyContactService;
    @EJB
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;
    @EJB
    private StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService;
    @EJB
    private StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService;
    @EJB
    private OrganizationCorrelationServiceRemote ocsr;
    @EJB
    private RegulatoryInformationServiceLocal regulatoryInformationService;
    @EJB
    private PDQDiseaseServiceLocal diseaseService;
    @EJB
    private InterventionServiceLocal interventionService;
    @EJB
    private InterventionAlternateNameServiceLocal interventionAlternateNameService;
    @EJB
    private StudyResourcingServiceLocal studyResourcingService;
    @EJB
    private PAOrganizationServiceRemote paOrganizationService;
    @EJB
    private StratumGroupServiceLocal stratumGroupService;
    @EJB
    private PlannedMarkerServiceLocal plannedMarkerService;
    @EJB
    private StudyIdentifiersServiceLocal studyIdentifiersService;

    private final CorrelationUtils correlationUtils = new CorrelationUtils();

    private final PAServiceUtils paServiceUtils = new PAServiceUtils();
    private AbstractTsrReportGenerator tsrReportGenerator;

    private static final String REPORT_TITLE = TSRReportLabelText.REPORT_TITLE;
    private static final String YES = "Yes";
    private static final String NO = "No";
    private static final String INFORMATION_NOT_PROVIDED = "No Data Available";
    private static final String BLANK = "";
    private static final String PROPRIETARY = "Abbreviated";
    private static final String NON_PROPRIETARY = "Complete";
    private static final String TYPE_INTERVENTIONAL = "Interventional";
    private static final String TYPE_NON_INTERVENTIONAL = "Non-Interventional";
    private static final String CRITERION_GENDER = "GENDER";
    private static final String CRITERION_AGE = "AGE";
    private static final String ROLE_PI = "Principal Investigator";
    private static final String SPACE = " ";
    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 999;
    private static final String AFFILIATED_WITH = " affiliated with ";
    private static final String IN_THE_ROLE_OF = " in the role of ";
    private static final String AS_OF = " as of ";
    private static final String EMAIL = " email: ";
    private static final String PHONE = " phone: ";
    /**
     * to compare the Attribute values with Other
     */
    protected static final String OTHER = "Other";
    /**
     * to compare the if attribute Text not present
     */
    protected static final String NOTPRESENT = "notPresent";
    /**
     * to compare the if other Text Present or not
     */
    protected static final String EMPTY = "empty";
    private static final Logger LOG = Logger.getLogger(TSRReportGeneratorServiceBean.class);

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ByteArrayOutputStream generatePdfTsrReport(Ii studyProtIi) throws PAException {
        tsrReportGenerator = new PdfTsrReportGenerator();
        return getTsrReport(tsrReportGenerator, studyProtIi);
    }


    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ByteArrayOutputStream generateRtfTsrReport(Ii studyProtIi) throws PAException {
        tsrReportGenerator = new RtfTsrReportGenerator();
        return getTsrReport(tsrReportGenerator, studyProtIi);
    }


    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ByteArrayOutputStream generateHtmlTsrReport(Ii studyProtIi) throws PAException {
        tsrReportGenerator = new HtmlTsrReportGenerator();
        return getTsrReport(tsrReportGenerator, studyProtIi);
    }


    private ByteArrayOutputStream getTsrReport(AbstractTsrReportGenerator reportGenerator, Ii studyProtocolIi)
        throws PAException {
        if (studyProtocolIi == null) {
            throw new PAException("Study Protocol Identifier is null.");
        }

        ByteArrayOutputStream outputByteStream = null;

        StudyProtocolDTO studyProtocolDto = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        boolean isProprietaryTrial = !ISOUtil.isBlNull(studyProtocolDto.getProprietaryTrialIndicator())
            && BlConverter.convertToBoolean(studyProtocolDto.getProprietaryTrialIndicator());
        boolean isNonInterventionalTrial = studyProtocolDto instanceof NonInterventionalStudyProtocolDTO;

        TSRReport tsrReport = new TSRReport(REPORT_TITLE, PAUtil.today(),
                PAUtil.convertTsToFormattedDate(studyProtocolDto.getRecordVerificationDate()));
        reportGenerator.setProprietaryTrial(isProprietaryTrial);
        reportGenerator.setTsrReport(tsrReport);
        reportGenerator.setNonInterventionalTrial(isNonInterventionalTrial);

        try {        
            setTrialReportDetails(studyProtocolDto);        
            outputByteStream = reportGenerator.generateTsrReport();
        } catch (Exception e) {
            LOG.error(e, e);
            TSRErrorReport tsrErrorReport =
                new TSRErrorReport(REPORT_TITLE, PAUtil.getAssignedIdentifierExtension(studyProtocolDto),
                        getValue(studyProtocolDto.getOfficialTitle()));
            tsrErrorReport.setErrorType(e.toString());
            for (StackTraceElement ste : e.getStackTrace()) {
                tsrErrorReport.getErrorReasons().add(ste.toString());
            }
            reportGenerator.setTsrErrorReport(tsrErrorReport);
            outputByteStream = reportGenerator.generateErrorReport();
        }
        return outputByteStream;
    }

    private void setTrialReportDetails(StudyProtocolDTO studyProtocolDto) throws PAException,
    NullifiedRoleException {
        final boolean isAbbr = BlConverter.convertToBool(studyProtocolDto
                .getProprietaryTrialIndicator());
        setTrialIdentificationDetails(studyProtocolDto,
                isAbbr);
        setAssociatedTrials(studyProtocolDto);
        setGeneralTrialDetails(studyProtocolDto, isAbbr);
        setStatusDatesDetails(studyProtocolDto);
        setRegulatoryInformation(studyProtocolDto);
        setHumanSubjectSafety(studyProtocolDto);
        setIndIdes(studyProtocolDto);
        setNihGrants(studyProtocolDto);
        setSummary4Information(studyProtocolDto);
        setCollaborators(studyProtocolDto);
        setDiseases(studyProtocolDto);
        setTrialDesign(studyProtocolDto);
        setEligibilityCriteria(studyProtocolDto);
        setInterventions(studyProtocolDto);
        setArmGroups(studyProtocolDto);
        setPrimaryAndSecondaryOutcomeMeasures(studyProtocolDto);
        setSubGroupStratificationCriteria(studyProtocolDto);
        setParticipatingSites(studyProtocolDto);
        setPlannedMarkers(studyProtocolDto);
    }

    private void setInterventions(StudyProtocolDTO studyProtocolDto) throws PAException {
        List<PlannedActivityDTO> paList = plannedActivityService.getByStudyProtocol(studyProtocolDto.getIdentifier());
        if (paList != null && !paList.isEmpty()) {
            for (PlannedActivityDTO paDto : paList) {
                if (PAUtil.isTypeIntervention(paDto.getCategoryCode())) {
                    tsrReportGenerator.getInterventions().add(getIntervention(paDto));
                }
            }
        }
    }
    
    private void setAssociatedTrials(StudyProtocolDTO studyProtocolDto)
            throws PAException {
        List<StudyProtocolAssociationDTO> list = PaRegistry
                .getStudyProtocolService().getTrialAssociations(
                        IiConverter.convertToLong(studyProtocolDto
                                .getIdentifier()));
        for (StudyProtocolAssociationDTO association : list) {
            tsrReportGenerator.getAssociatedTrials().add(
                    new TSRReportAssociatedTrial(association));
        }
    }

    private TSRReportIntervention getIntervention(PlannedActivityDTO paDto) throws PAException {
        InterventionDTO interventionDto = interventionService.get(paDto.getInterventionIdentifier());
        TSRReportIntervention intervention = new TSRReportIntervention();
        intervention.setType(getValue(paDto.getSubcategoryCode()));
        intervention.setName(getValue(interventionDto.getName()));
        intervention.setAlternateName(getInterventionAltNames(interventionDto));
        intervention.setDescription(getValue(paDto.getTextDescription()));
        return intervention;
    }

    private void setTrialIdentificationDetails(StudyProtocolDTO studyProtocolDto, boolean isProprietaryTrial)
    throws PAException {
        TSRReportTrialIdentification trialIdentification = new TSRReportTrialIdentification();
        trialIdentification.setTrialCategory(isProprietaryTrial ? PROPRIETARY : NON_PROPRIETARY);
        
        List<Ii> otherIdentifierIis = PAUtil.getOtherIdentifiers(studyProtocolDto);
        for (Ii otherIdIi : otherIdentifierIis) {
            trialIdentification.getOtherIdentifiers().add(otherIdIi.getExtension());
        }
        trialIdentification.getIdentifiers().addAll(
                studyIdentifiersService.getStudyIdentifiers(studyProtocolDto
                        .getIdentifier()));
        
        trialIdentification.setNciIdentifier(PAUtil.getAssignedIdentifierExtension(studyProtocolDto));
        trialIdentification.setLeadOrgIdentifier(getTiLeadOrgIdentifier(studyProtocolDto));
        trialIdentification.setNctNumber(getIdentifier(studyProtocolDto, PAConstants.NCT_IDENTIFIER_TYPE));
        trialIdentification.setDcpIdentifier(getIdentifier(studyProtocolDto, PAConstants.DCP_IDENTIFIER_TYPE));
        trialIdentification.setCtepIdentifier(getIdentifier(studyProtocolDto, PAConstants.CTEP_IDENTIFIER_TYPE));
        trialIdentification.setCcrIdentifier(getIdentifier(studyProtocolDto, PAConstants.CCR_IDENTIFIER_TYPE));
        trialIdentification.setAmendmentNumber(getValue(studyProtocolDto.getAmendmentNumber()));
        trialIdentification.setAmendmentDate(PAUtil.convertTsToFormattedDate(studyProtocolDto.getAmendmentDate()));
        Organization lead = ocsr.getOrganizationByFunctionRole(studyProtocolDto.getIdentifier(), CdConverter
                .convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        trialIdentification.setLeadOrganization(lead.getName());

        tsrReportGenerator.setTrialIdentification(trialIdentification);
    }

    private String getTiLeadOrgIdentifier(StudyProtocolDTO studyProtocolDto) throws PAException {
        String leadOrgIdentifier = null;
        StudySiteDTO spartDTO = new StudySiteDTO();
        spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        List<StudySiteDTO> sParts = studySiteService.getByStudyProtocol(studyProtocolDto.getIdentifier(), spartDTO);
        if (sParts != null && !sParts.isEmpty() && sParts.get(0).getLocalStudyProtocolIdentifier() != null) {
            leadOrgIdentifier = sParts.get(0).getLocalStudyProtocolIdentifier().getValue();
        }
        return leadOrgIdentifier;
    }

    private String getIdentifier(StudyProtocolDTO studyProtocolDto, String identifierType) throws PAException {
        String identifier = paServiceUtils.getStudyIdentifier(studyProtocolDto.getIdentifier(), identifierType);
        return (StringUtils.isNotBlank(identifier) ? identifier : INFORMATION_NOT_PROVIDED);
    }

    private void setGeneralTrialDetails(StudyProtocolDTO studyProtocolDto, boolean isProprietaryTrial) //NOPMD
    throws PAException, NullifiedRoleException {
        TSRReportGeneralTrialDetails gtd = new TSRReportGeneralTrialDetails();

        boolean interventionalType = !(studyProtocolDto instanceof NonInterventionalStudyProtocolDTO);
        gtd.setType(interventionalType ? TYPE_INTERVENTIONAL : TYPE_NON_INTERVENTIONAL);        
        gtd.setOfficialTitle(getValue(studyProtocolDto.getOfficialTitle(), INFORMATION_NOT_PROVIDED));
        setNonInterventionalInfo(studyProtocolDto, gtd);
        Set<StudyAlternateTitleDTO> alternativeTitles = studyProtocolDto.getStudyAlternateTitles();
        if (alternativeTitles != null) {
           for (StudyAlternateTitleDTO dto : alternativeTitles) {
               gtd.getStudyAlternateTitle().add(StConverter.convertToString(dto.getAlternateTitle())
                    + " (Category: " + StConverter.convertToString(dto.getCategory()) + ")");
           }
        }
        gtd.setBriefTitle(getValue(studyProtocolDto.getPublicTitle(), INFORMATION_NOT_PROVIDED));
        gtd.setAcronym(getValue(studyProtocolDto.getAcronym(), INFORMATION_NOT_PROVIDED));
        gtd.setBriefSummary(getValue(studyProtocolDto.getPublicDescription(), INFORMATION_NOT_PROVIDED));
        gtd.setDetailedDescription(getValue(studyProtocolDto.getScientificDescription(), INFORMATION_NOT_PROVIDED));
        gtd.setKeywords(getValue(studyProtocolDto.getKeywordText(), INFORMATION_NOT_PROVIDED));
        if (studyProtocolDto.getAccrualReportingMethodCode() != null) {
            gtd.setReportingDatasetMethod(getValue(studyProtocolDto.getAccrualReportingMethodCode()
                    .getDisplayName(), INFORMATION_NOT_PROVIDED));
        }
        final boolean ctGov = BlConverter.convertToBool(studyProtocolDto
                .getCtgovXmlRequiredIndicator());
        if (isProprietaryTrial
                || ctGov) {
            gtd.setSponsor(getGtdSponsorOrLeadOrganization(studyProtocolDto,
                    StudySiteFunctionalCode.SPONSOR, INFORMATION_NOT_PROVIDED));
        }
        String leadOrganization = getGtdSponsorOrLeadOrganization(studyProtocolDto,
                StudySiteFunctionalCode.LEAD_ORGANIZATION, INFORMATION_NOT_PROVIDED);
        gtd.setLeadOrganization(leadOrganization);
        
        // PI
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos =
            studyContactService.getByStudyProtocol(studyProtocolDto.getIdentifier(), scDto);
        if (!scDtos.isEmpty()) {
            Person leadPi = correlationUtils.getPAPersonByIi(scDtos.get(0).getClinicalResearchStaffIi());
            gtd.setPi(leadPi.getFullName() + AFFILIATED_WITH + leadOrganization);
            // Overall Official
            StringBuilder overallOffBuilder = new StringBuilder();
            overallOffBuilder.append(leadPi.getFullName()).append(AFFILIATED_WITH).append(leadOrganization).append(
                    IN_THE_ROLE_OF).append(ROLE_PI);
            gtd.setOverallOfficial(overallOffBuilder.toString());
            
            
        }
        
        // Central Contact
        gtd.setCentralContact(getCentralContactDetails(studyProtocolDto));
        // Responsible Party
        if (isProprietaryTrial || ctGov) {
            gtd.setResponsibleParty(getResponsiblePartyDetails(studyProtocolDto));
        }

        tsrReportGenerator.setGeneralTrialDetails(gtd);
    }


    /**
     * @param studyProtocolDto
     * @param gtd
     */
    private void setNonInterventionalInfo(StudyProtocolDTO studyProtocolDto,
            TSRReportGeneralTrialDetails gtd) {
        if (studyProtocolDto instanceof NonInterventionalStudyProtocolDTO) {
            NonInterventionalStudyProtocolDTO nonIntDTO = (NonInterventionalStudyProtocolDTO) studyProtocolDto;
            gtd.setSubType(getValue(nonIntDTO.getStudySubtypeCode(),
                    INFORMATION_NOT_PROVIDED));
        }
    }

    private String getCentralContactDetails(StudyProtocolDTO studyProtocolDto) throws PAException,
    NullifiedRoleException {
        String retVal = null;
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.CENTRAL_CONTACT));
        List<StudyContactDTO> scDtos = studyContactService.getByStudyProtocol(studyProtocolDto.getIdentifier(), scDto);
        if (!scDtos.isEmpty()) {
            String centralContactName = "";

            StudyContactDTO cc = scDtos.get(0);
            if (cc.getClinicalResearchStaffIi() != null) {
                centralContactName = correlationUtils.getPAPersonByIi(cc.getClinicalResearchStaffIi()).getFullName();
            } else if (cc.getOrganizationalContactIi() != null) {
                PAContactDTO paCDto = correlationUtils.getContactByPAOrganizationalContactId((Long.valueOf(cc
                        .getOrganizationalContactIi().getExtension())));
                centralContactName = paCDto.getTitle();
            }
            String email = StringUtils.defaultString(PAUtil.getEmail(cc.getTelecomAddresses()), N_A);
            String phone = StringUtils.defaultString(PAUtil.getPhone(cc.getTelecomAddresses()), N_A);
            String extn = PAUtil.getPhoneExtension(cc.getTelecomAddresses());

            StringBuilder builder = new StringBuilder();
            builder.append(centralContactName).append(",").append(EMAIL).append(email).append(",").append(PHONE)
            .append(phone).append(StringUtils.isNotEmpty(extn) ? ", ext: " + extn : BLANK);
            retVal = builder.toString();
        }
        return retVal;
    }

    private String getResponsiblePartyDetails(StudyProtocolDTO studyProtocolDto)
            throws PAException, NullifiedRoleException {
        String retVal = INFORMATION_NOT_PROVIDED;
        
        StudyContactDTO scDto = studyContactService
                .getResponsiblePartyContact(studyProtocolDto.getIdentifier());       
        if (scDto != null) {
            String title = StConverter.convertToString(scDto.getTitle());
            final ClinicalResearchStaff crs = (ClinicalResearchStaff) new gov.nih.nci.pa.util.CorrelationUtils()
                    .getStructuralRole(scDto.getClinicalResearchStaffIi(),
                            ClinicalResearchStaff.class);
            Organization o = crs.getOrganization();
            Person p = crs.getPerson();
            StudyContactRoleCode role = CdConverter.convertCdToEnum(StudyContactRoleCode.class, scDto.getRoleCode());
            
            StringBuilder builder = new StringBuilder();
            builder.append(
                    StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR
                            .equals(role) ? "Principal Investigator; "
                            : "Sponsor-Investigator; ").append("Name: ")
                    .append(StringUtils.defaultString(p.getFullName(), N_A))
                    .append(", ").append("Title: ")
                    .append(StringUtils.defaultString(title, N_A)).append(", ")
                    .append("Affiliation: ")
                    .append(StringUtils.defaultString(o.getName(), N_A));
            retVal = builder.toString();
        } else {
            if (paServiceUtils.isResponsiblePartySponsor(studyProtocolDto.getIdentifier())) {
                retVal = "Sponsor";
            }
        }
        return retVal;
    }

    private void setStatusDatesDetails(StudyProtocolDTO studyProtocolDto) throws PAException {
        TSRReportStatusDate statusDate = new TSRReportStatusDate();
        StudyOverallStatusDTO statusDateDto = studyOverallStatusService
                .getCurrentByStudyProtocol(studyProtocolDto.getIdentifier());
        if (statusDateDto != null) {
            statusDate.setCurrentTrialStatus(getValue(statusDateDto.getStatusCode()) + AS_OF
                    + PAUtil.convertTsToFormattedDate(statusDateDto.getStatusDate()));
            statusDate.setReasonText(getValue(statusDateDto.getReasonText(), null));
            if (ISOUtil.isTsNull(studyProtocolDto.getStartDate())) {
                statusDate.setTrialStartDate(INFORMATION_NOT_PROVIDED);
            } else {
                statusDate.setTrialStartDate(PAUtil.normalizeDateString(TsConverter.convertToTimestamp(
                        studyProtocolDto.getStartDate()).toString())
                        + "-" + getValue(studyProtocolDto.getStartDateTypeCode()));
            }
            if (!ISOUtil.isTsNull(studyProtocolDto.getPrimaryCompletionDate())) {
                statusDate.setPrimaryCompletionDate(PAUtil.normalizeDateString(TsConverter.convertToTimestamp(
                        studyProtocolDto.getPrimaryCompletionDate()).toString())
                        + "-" + getValue(studyProtocolDto.getPrimaryCompletionDateTypeCode()));
            } else {
                statusDate.setPrimaryCompletionDate(getValue(studyProtocolDto.getPrimaryCompletionDateTypeCode()));
            }
            if (!ISOUtil.isTsNull(studyProtocolDto.getCompletionDate())) {
                statusDate.setCompletionDate(PAUtil.normalizeDateString(TsConverter.convertToTimestamp(
                        studyProtocolDto.getCompletionDate()).toString())
                        + "-" + getValue(studyProtocolDto.getCompletionDateTypeCode()));
            } else if (ISOUtil.isTsNull(studyProtocolDto.getCompletionDate())
                    && !ISOUtil.isCdNull(studyProtocolDto.getCompletionDateTypeCode())) {
                statusDate.setCompletionDate(INFORMATION_NOT_PROVIDED
                        + "-" + getValue(studyProtocolDto.getCompletionDateTypeCode()));
            } else {   
                statusDate.setCompletionDate(INFORMATION_NOT_PROVIDED);
            }
            tsrReportGenerator.setStatusDate(statusDate);
        }
    }

    private void setRegulatoryInformation(StudyProtocolDTO studyProtocolDto) throws PAException {
        TSRReportRegulatoryInformation regInfo = new TSRReportRegulatoryInformation();
        if (BlConverter.convertToBool(studyProtocolDto
                .getCtgovXmlRequiredIndicator())
                || BlConverter.convertToBool(studyProtocolDto
                        .getProprietaryTrialIndicator())) {
           StudyRegulatoryAuthorityDTO sraDTO = studyRegulatoryAuthorityService
                .getCurrentByStudyProtocol(studyProtocolDto.getIdentifier());
            if (sraDTO != null) {
               String data = INFORMATION_NOT_PROVIDED;
               RegulatoryAuthority ra = regulatoryInformationService.get(Long.valueOf(sraDTO
                    .getRegulatoryAuthorityIdentifier().getExtension()));

                Country country = regulatoryInformationService.getRegulatoryAuthorityCountry(Long.valueOf(sraDTO
                    .getRegulatoryAuthorityIdentifier().getExtension()));
                if (country != null && ra != null) {
                   data = country.getName() + ": " + ra.getAuthorityName();
                } else if (country != null) {
                   data = country.getName();
                } else if (ra != null) {
                  data = ra.getAuthorityName();
                }
               regInfo.setTrialOversightAuthority(data);
           }
           regInfo.setDmcAppointed(getValue(studyProtocolDto.getDataMonitoringCommitteeAppointedIndicator(),
                INFORMATION_NOT_PROVIDED));
           regInfo.setFdaRegulatedIntervention(getValue(studyProtocolDto.getFdaRegulatedIndicator(),
                INFORMATION_NOT_PROVIDED));
           regInfo.setSection801(getValue(studyProtocolDto.getSection801Indicator(), INFORMATION_NOT_PROVIDED));
           List<StudyIndldeDTO> indIde = studyIndldeService.getByStudyProtocol(studyProtocolDto.getIdentifier());
           if (paServiceUtils.containsNonExemptInds(indIde)) {
              regInfo.setIndIdeStudy(YES);
           } else {
              regInfo.setIndIdeStudy(NO);
           }
          regInfo.setDelayedPosting(getValue(studyProtocolDto.getDelayedpostingIndicator(), INFORMATION_NOT_PROVIDED));
        }
        tsrReportGenerator.setRegulatoryInformation(regInfo);
    }

    private void setHumanSubjectSafety(StudyProtocolDTO studyProtocolDto) throws PAException {
        TSRReportHumanSubjectSafety hss = new TSRReportHumanSubjectSafety();

        boolean reviewBoardApprovalRequired =
            BlConverter.convertToBool(studyProtocolDto.getReviewBoardApprovalRequiredIndicator());
        if (reviewBoardApprovalRequired) {
            List<StudySiteDTO> partList = studySiteService.getByStudyProtocol(studyProtocolDto.getIdentifier());
            String[] submittedStatusCodes = new String[] {ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode(),
                    ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode(),
                    ReviewBoardApprovalStatusCode.SUBMITTED_PENDING.getCode(),
                    ReviewBoardApprovalStatusCode.SUBMITTED_DENIED.getCode()};
            for (StudySiteDTO part : partList) {
                if (ArrayUtils.contains(submittedStatusCodes, part.getReviewBoardApprovalStatusCode().getCode())) {
                    hss.setBoardApprovalStatus(getValue(part.getReviewBoardApprovalStatusCode(),
                            INFORMATION_NOT_PROVIDED));
                    hss.setBoardApprovalNumber(getValue(part.getReviewBoardApprovalNumber(), INFORMATION_NOT_PROVIDED));
                    Organization paOrg = correlationUtils.getPAOrganizationByIi(part.getOversightCommitteeIi());
                    if (paOrg != null) {
                        OrganizationDTO poOrg = null;
                        try {
                            poOrg = PoRegistry.getOrganizationEntityService().getOrganization(
                                    IiConverter.convertToPoOrganizationIi(paOrg.getIdentifier()));
                        } catch (NullifiedEntityException e) {
                            throw new PAException(" Po Identifier is nullified " + paOrg.getIdentifier(), e);
                        }
                        String email = null;
                        String phone = null;
                        String extn = null;
                        Object[] telList = poOrg.getTelecomAddress().getItem().toArray();
                        for (Object tel : telList) {
                            if (tel instanceof TelPhone) {
                                phone = ((TelPhone) tel).getValue().getSchemeSpecificPart();
                                extn = PAUtil.getPhoneExtn(phone);
                            } else if (tel instanceof TelEmail) {
                                email = ((TelEmail) tel).getValue().getSchemeSpecificPart();
                            }
                        }
                        hss.setBoard(getHSSBoardNameAndAddress(paOrg.getName(), AddressConverterUtil
                                .convertToAddress(poOrg.getPostalAddress()), email, phone, extn));
                        hss.setAffiliation(getValue(part.getReviewBoardOrganizationalAffiliation(), null));
                    }
                }
            }
        }
        tsrReportGenerator.setHumanSubjectSafety(hss);
    }

    private String getHSSBoardNameAndAddress(String orgName, String fullAddress,
            String email, String phone, String extn) {
        StringBuilder retVal = new StringBuilder();
        retVal.append(StringUtils.defaultString(orgName))
            .append(StringUtils.isNotEmpty(orgName) && StringUtils.isNotEmpty(fullAddress) ? ", " : "")
            .append(StringUtils.defaultString(fullAddress));
        if (StringUtils.isNotEmpty(phone)) {
            retVal.append(",").append(PHONE).append(phone)
            .append(StringUtils.isNotEmpty(extn) ? ", extn: " + extn : BLANK);
        }
        if (StringUtils.isNotEmpty(email)) {
            retVal.append(",").append(EMAIL).append(email);
        }
        if (StringUtils.isEmpty(retVal.toString())) {
            return null;
        }

        return retVal.toString();
    }

    private void setIndIdes(StudyProtocolDTO studyProtocolDto) throws PAException { //NOPMD
        List<TSRReportIndIde> indIdes = new ArrayList<TSRReportIndIde>();

        List<StudyIndldeDTO> indides = studyIndldeService.getByStudyProtocol(studyProtocolDto.getIdentifier());
        for (StudyIndldeDTO indDto : indides) {
            TSRReportIndIde indIde = new TSRReportIndIde();
            indIde.setType(getValue(indDto.getIndldeTypeCode()));
            indIde.setGrantor(getValue(indDto.getGrantorCode()));
            indIde.setNumber(getValue(indDto.getIndldeNumber()));
            indIde.setHolderType(getValue(indDto.getHolderTypeCode()));
            if (indDto.getHolderTypeCode() != null  && indDto.getHolderTypeCode().getCode() != null
                    && indDto.getHolderTypeCode().getCode().equals(HolderTypeCode.NIH.getCode())) {
                indIde.setHolder(getValue(indDto.getNihInstHolderCode()));
            } else if (indDto.getHolderTypeCode() != null && indDto.getHolderTypeCode().getCode() != null
                    && indDto.getHolderTypeCode().getCode().equals(HolderTypeCode.NCI.getCode())) {
                indIde.setHolder(getValue(indDto.getNciDivProgHolderCode()));
            }
            indIde.setExpandedAccess(getValue(indDto.getExpandedAccessIndicator()));
            indIde.setExpandedAccessStatus(getValue(indDto.getExpandedAccessStatusCode()));
            indIde.setExemptIndicator(BlConverter.convertBlToYesNoString(indDto.getExemptIndicator()));
            indIdes.add(indIde);
        }
        tsrReportGenerator.setIndIdes(indIdes);
    }

    private void setNihGrants(StudyProtocolDTO studyProtocolDto) throws PAException {
        List<TSRReportNihGrant> nihGrants = new ArrayList<TSRReportNihGrant>();
        List<StudyResourcingDTO> funds = studyResourcingService
                .getStudyResourcingByStudyProtocol(studyProtocolDto.getIdentifier());
        for (StudyResourcingDTO fund : funds) {
            if (!ISOUtil.isBlNull(fund.getActiveIndicator()) && fund.getActiveIndicator().getValue()) {
                TSRReportNihGrant nihGrant = new TSRReportNihGrant();
                nihGrant.setFundingMechanism(getValue(fund.getFundingMechanismCode()));
                nihGrant.setNihInstitutionCode(getValue(fund.getNihInstitutionCode()));
                nihGrant.setSerialNumber(getValue(fund.getSerialNumber()));
                nihGrant.setProgramCode(getValue(fund.getNciDivisionProgramCode()));
                nihGrants.add(nihGrant);
            }
        }
        tsrReportGenerator.setNihGrants(nihGrants);
    }

    private void setSummary4Information(StudyProtocolDTO studyProtocolDto) throws PAException {
        TSRReportSummary4Information sum4Info = new TSRReportSummary4Information();
        List<StudyResourcingDTO> studyResourcingDTOList = studyResourcingService
                .getSummary4ReportedResourcing(studyProtocolDto.getIdentifier());
        StringBuilder orgNames = new StringBuilder();
        for (StudyResourcingDTO studyResourcingDTO : studyResourcingDTOList) {
                sum4Info.setFundingCategory(getValue(studyResourcingDTO.getTypeCode(), INFORMATION_NOT_PROVIDED));
                Organization org = null;
                if (studyResourcingDTO.getOrganizationIdentifier() != null
                        && studyResourcingDTO.getOrganizationIdentifier().getExtension() != null) {
                    Organization o = new Organization();
                    o.setId(Long.valueOf(studyResourcingDTO.getOrganizationIdentifier().getExtension()));
                    org = paOrganizationService.getOrganizationByIndetifers(o);
                }
                if (org != null) {
                    orgNames.append(org.getName()).append(System.getProperty("line.separator"));
                }
        }
        sum4Info.setFundingSponsor(StringUtils.defaultString(orgNames.toString(), INFORMATION_NOT_PROVIDED));
        if (CollectionUtils.isNotEmpty(studyProtocolDto.getProgramCodes())) {
            StringBuilder programCodes = new StringBuilder();
            for (ProgramCodeDTO pg : studyProtocolDto.getProgramCodes()) {
                if (programCodes.length() > 0) {
                    programCodes.append(", ");
                }
                programCodes.append(pg.getProgramCode());
            }
            sum4Info.setProgramCode(programCodes.toString());
        } else if (studyProtocolDto.getProgramCodeText().getValue() != null) {
            sum4Info.setProgramCode(getValue(studyProtocolDto.getProgramCodeText(), INFORMATION_NOT_PROVIDED));
        }
        setSummary4AnatomicSite(studyProtocolDto, sum4Info);
        tsrReportGenerator.setSummary4Information(sum4Info);
    }
    private void setSummary4AnatomicSite(StudyProtocolDTO studyProtocolDto,
            TSRReportSummary4Information sum4Info) {
        if (ISOUtil.isDSetNotEmpty(studyProtocolDto.getSummary4AnatomicSites())) {
            List<String> anatomicSites =  new ArrayList<String>();
            for (Cd as : studyProtocolDto.getSummary4AnatomicSites().getItem()) {
                anatomicSites.add(StConverter.convertToString(as.getDisplayName()));
            }
            sum4Info.setAnatomicSites(anatomicSites);
        }
    }

    private void setCollaborators(StudyProtocolDTO studyProtocolDto) throws PAException {
        List<TSRReportCollaborator> lstCollabs = new ArrayList<TSRReportCollaborator>();

        ArrayList<StudySiteDTO> criteriaList = new ArrayList<StudySiteDTO>();
        for (StudySiteFunctionalCode cd : StudySiteFunctionalCode.values()) {
            if (cd.isCollaboratorCode()) {
                StudySiteDTO searchCode = new StudySiteDTO();
                searchCode.setFunctionalCode(CdConverter.convertToCd(cd));
                criteriaList.add(searchCode);
            }
        }
        List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(studyProtocolDto.getIdentifier(), criteriaList);
        for (StudySiteDTO sp : spList) {
            Organization orgBo = correlationUtils.getPAOrganizationByIi(sp.getResearchOrganizationIi());
            TSRReportCollaborator collab = new TSRReportCollaborator();
            collab.setName(orgBo.getName());
            collab.setRole(getValue(sp.getFunctionalCode()));
            lstCollabs.add(collab);
        }
        tsrReportGenerator.setCollaborators(lstCollabs);
    }

    private void setDiseases(StudyProtocolDTO studyProtocolDto) throws PAException {
        List<TSRReportDiseaseCondition> diseaseConditions = new ArrayList<TSRReportDiseaseCondition>();
        List<StudyDiseaseDTO> sdDtos = studyDiseaseService.getByStudyProtocol(studyProtocolDto.getIdentifier());
        if (sdDtos != null) {
            List<PDQDiseaseDTO> diseases = new ArrayList<PDQDiseaseDTO>();
            for (StudyDiseaseDTO sdDto : sdDtos) {
                diseases.add(diseaseService.get(sdDto.getDiseaseIdentifier()));
            }
            Collections.sort(diseases, new Comparator<PDQDiseaseDTO>() {
                public int compare(PDQDiseaseDTO o1, PDQDiseaseDTO o2) {
                    return o1.getPreferredName().getValue().compareToIgnoreCase(o2.getPreferredName().getValue());
                }
            });
            for (PDQDiseaseDTO d : diseases) {
                TSRReportDiseaseCondition diseaseCondition = new TSRReportDiseaseCondition();
                diseaseCondition.setName(getValue(d.getPreferredName()));
                diseaseConditions.add(diseaseCondition);
            }
        }
        tsrReportGenerator.setDiseaseConditions(diseaseConditions);
    }

    private void setTrialDesign(StudyProtocolDTO ispDTO) throws PAException {
        TSRReportTrialDesign trialDesign = new TSRReportTrialDesign();        
        boolean interventionalType = !(ispDTO instanceof NonInterventionalStudyProtocolDTO);
        trialDesign.setType(interventionalType ? TYPE_INTERVENTIONAL : TYPE_NON_INTERVENTIONAL);
        if (ispDTO != null) {
            trialDesign.setPrimaryPurpose(getValue(ispDTO.getPrimaryPurposeCode(), INFORMATION_NOT_PROVIDED));
            trialDesign.setPrimaryPurposeOtherText(getValue(ispDTO.getPrimaryPurposeOtherText()));
            trialDesign.setPhase(getValue(ispDTO.getPhaseCode(), INFORMATION_NOT_PROVIDED));
            trialDesign.setPhaseAdditonalQualifier(getValue(ispDTO.getPhaseAdditionalQualifierCode(),
                    INFORMATION_NOT_PROVIDED));
            trialDesign.setTargetEnrollment(getValue(ispDTO.getTargetAccrualNumber().getLow()));
            
            if (ispDTO instanceof InterventionalStudyProtocolDTO) {
                InterventionalStudyProtocolDTO intDTO = (InterventionalStudyProtocolDTO) ispDTO;
                trialDesign.setInterventionModel(getValue(intDTO.getDesignConfigurationCode(), 
                        INFORMATION_NOT_PROVIDED));
                trialDesign.setNumberOfArms(getValue(intDTO.getNumberOfInterventionGroups(), INFORMATION_NOT_PROVIDED));
                trialDesign.setMasking(getValue(intDTO.getBlindingSchemaCode()));
                if (intDTO.getBlindingSchemaCode() != null) {
                    trialDesign.setMaskedRoles(getTrialDesignMaskedRoles(intDTO));
                }
                trialDesign.setAllocation(getValue(intDTO.getAllocationCode()));
                trialDesign.setStudyClassification(getValue(intDTO.getStudyClassificationCode()));
                StringBuilder secondaryPurpose = new StringBuilder();
                if (intDTO.getSecondaryPurposes() != null) {
                    for (String sec : DSetConverter.convertDSetStToList(intDTO.getSecondaryPurposes())) {
                        secondaryPurpose.append(sec).append(" ");
                    }
                }
                trialDesign.setSecondaryPurpose(secondaryPurpose.toString().trim());
                if (PAUtil.isPrimaryPurposeCodeOther(secondaryPurpose
                        .toString().trim())) {
                    trialDesign.setSecondaryPurposeOtherText(getValue(ispDTO
                            .getSecondaryPurposeOtherText()));
                }
            } else if (ispDTO instanceof NonInterventionalStudyProtocolDTO) {
                NonInterventionalStudyProtocolDTO dto = (NonInterventionalStudyProtocolDTO) ispDTO;
                final String timePerspective = CdConverter
                        .convertCdToString(dto.getTimePerspectiveCode());
                final String studyModel = CdConverter.convertCdToString(dto.getStudyModelCode());

                trialDesign.setTimePerspective(timePerspective);
                trialDesign.setTimePerspectiveOtherText("Other".equalsIgnoreCase(timePerspective) ? StConverter
                        .convertToString(dto.getTimePerspectiveOtherText()) : null);

                trialDesign.setStudyModel(studyModel);
                trialDesign.setStudyModelOtherText("Other".equalsIgnoreCase(studyModel) ? StConverter
                        .convertToString(dto.getStudyModelOtherText()) : null);
                trialDesign.setBiospecimenRetentionCode(getValue(dto.getBiospecimenRetentionCode()));
                trialDesign.setBiospecimenDescription(getValue(dto.getBiospecimenDescription()));
                trialDesign.setNumberOfGroups(getValue(dto.getNumberOfGroups()));
                trialDesign.setStudySubtypeCode(getValue(dto.getStudySubtypeCode()));
            }            
            
        }
        tsrReportGenerator.setTrialDesign(trialDesign);
    }

    private String getTrialDesignMaskedRoles(InterventionalStudyProtocolDTO dto) {
        String maskedRoles = null;
        String subject = NO;
        String investigator = NO;
        String caregiver = NO;
        String outcomesAssessor = NO;
        List<Cd> cds = DSetConverter.convertDsetToCdList(dto.getBlindedRoleCode());
        for (Cd cd : cds) {
            if (BlindingRoleCode.CAREGIVER.getCode().equals(cd.getCode())) {
                caregiver = YES;
            } else if (BlindingRoleCode.INVESTIGATOR.getCode().equals(cd.getCode())) {
                investigator = YES;
            } else if (BlindingRoleCode.OUTCOMES_ASSESSOR.getCode().equals(cd.getCode())) {
                outcomesAssessor = YES;
            } else if (BlindingRoleCode.SUBJECT.getCode().equals(cd.getCode())) {
                subject = YES;
            }
            maskedRoles = "Subject: " + subject + "; Investigator: " + investigator + "; Caregiver: " + caregiver
            + "; Outcomes Assessor: " + outcomesAssessor;
        }
        return maskedRoles;
    }

    private void setEligibilityCriteria(StudyProtocolDTO studyProtocolDto) throws PAException {
        List<PlannedEligibilityCriterionDTO> paECs = plannedActivityService
                .getPlannedEligibilityCriterionByStudyProtocol(studyProtocolDto.getIdentifier());
        if (CollectionUtils.isNotEmpty(paECs)) {
            TSRReportEligibilityCriteria eligibilityCriteria = new TSRReportEligibilityCriteria();
            processAndDescribeEligCriteria(studyProtocolDto, paECs,
                    eligibilityCriteria);
            tsrReportGenerator.setEligibilityCriteria(eligibilityCriteria);
        }
    }


  
    /**
     * For a given protocol and a list of eligibility criterions, will build a human-readable fine-grained description
     * of the trial's eligibility criteria.
     * @param studyProtocolDto StudyProtocolDTO
     * @param paECs List<PlannedEligibilityCriterionDTO>
     * @param eligibilityCriteria EligibilityCriteriaDescription
     */
    public static void processAndDescribeEligCriteria(
            StudyProtocolDTO studyProtocolDto,
            List<PlannedEligibilityCriterionDTO> paECs,
            EligibilityCriteriaDTO eligibilityCriteria) {
        if (studyProtocolDto instanceof NonInterventionalStudyProtocolDTO) {
            final NonInterventionalStudyProtocolDTO nonIntStudy = (NonInterventionalStudyProtocolDTO) 
                    studyProtocolDto;
            eligibilityCriteria.setStudyPopulationDescription(getValue(
                    nonIntStudy.getStudyPopulationDescription(),
                    INFORMATION_NOT_PROVIDED));
            eligibilityCriteria.setSampleMethodCode(getValue(
                    nonIntStudy.getSamplingMethodCode(),
                    INFORMATION_NOT_PROVIDED));                
        }
        eligibilityCriteria.setAcceptsHealthyVolunteers(getValue(studyProtocolDto
                .getAcceptHealthyVolunteersIndicator(), INFORMATION_NOT_PROVIDED));
        Collections.sort(paECs, new Comparator<PlannedEligibilityCriterionDTO>() {
            public int compare(PlannedEligibilityCriterionDTO o1, PlannedEligibilityCriterionDTO o2) {
                return (!ISOUtil.isIntNull(o1.getDisplayOrder()) && !ISOUtil.isIntNull(o2.getDisplayOrder())) ? o1
                        .getDisplayOrder().getValue().compareTo(o2.getDisplayOrder().getValue()) : 0;
            }
        });

        for (PlannedEligibilityCriterionDTO paEC : paECs) {
            String criterionName = StConverter.convertToString(paEC.getCriterionName());
            String descriptionText = StConverter.convertToString(paEC.getTextDescription());
            Boolean inclusionCriteriaIndicator = BlConverter.convertToBoolean(paEC.getInclusionIndicator());
            Ivl<Pq> pq = paEC.getValue();

            if (StringUtils.equalsIgnoreCase(criterionName, CRITERION_GENDER)
                    && paEC.getEligibleGenderCode() != null) {
                eligibilityCriteria.setGender(getValue(paEC.getEligibleGenderCode(), INFORMATION_NOT_PROVIDED));
            } else if (StringUtils.equalsIgnoreCase(criterionName, CRITERION_AGE)) {
                BigDecimal low = PqConverter.convertToPqToDecimal(pq.getLow());
                BigDecimal high = PqConverter.convertToPqToDecimal(pq.getHigh());
                if (low != null) {
                    eligibilityCriteria.setMinimumAge(low.intValue() == MIN_AGE ? N_A
                                    : PAUtil.getAge(low) + SPACE + pq.getLow().getUnit());
                }
                if (high != null) {
                    eligibilityCriteria.setMaximumAge(high.intValue() == MAX_AGE ? N_A
                                    : PAUtil.getAge(high) + SPACE + pq.getHigh().getUnit());
                }
            } else {
                String criteriaText = null;
                if (StringUtils.isNotEmpty(descriptionText)) {
                    criteriaText = descriptionText;
                } else {
                    criteriaText = paEC.getCriterionName() + SPACE + getValue(paEC.getOperator()) + SPACE
                            + pq.getLow().getValue() + SPACE + pq.getLow().getUnit();
                }

                if (inclusionCriteriaIndicator == null) {
                    eligibilityCriteria.getOtherCriteria().add(criteriaText);
                } else if (inclusionCriteriaIndicator.booleanValue()) {
                    eligibilityCriteria.getInclusionCriteria().add(criteriaText);
                } else {
                    eligibilityCriteria.getExclusionCriteria().add(criteriaText);
                }
            }
        }
    }

    private void setArmGroups(StudyProtocolDTO studyProtocolDto) throws PAException {
        List<TSRReportArmGroup> armGroups = new ArrayList<TSRReportArmGroup>();
        List<ArmDTO> arms = armService.getByStudyProtocol(studyProtocolDto.getIdentifier());
        for (ArmDTO armDto : arms) {
            TSRReportArmGroup armGroup = new TSRReportArmGroup();
            armGroup.setType(getValue(armDto.getTypeCode()));
            armGroup.setLabel(getValue(armDto.getName()));
            armGroup.setDescription(getValue(armDto.getDescriptionText()));
            List<PlannedActivityDTO> paList = plannedActivityService.getByArm(armDto.getIdentifier());
            for (PlannedActivityDTO paDto : paList) {
                armGroup.getInterventions().add(getIntervention(paDto));
            }
            armGroups.add(armGroup);
        }
        tsrReportGenerator.setArmGroups(armGroups);
    }

    private void setPrimaryAndSecondaryOutcomeMeasures(StudyProtocolDTO studyProtocolDto) throws PAException {
        List<StudyOutcomeMeasureDTO> somDtos =
            studyOutcomeMeasureService.getByStudyProtocol(studyProtocolDto.getIdentifier());
        if (!somDtos.isEmpty()) {
            List<TSRReportOutcomeMeasure> primaryOutcomeMeasures = new ArrayList<TSRReportOutcomeMeasure>();
            List<TSRReportOutcomeMeasure> secondaryOutcomeMeasures = new ArrayList<TSRReportOutcomeMeasure>();
            List<TSRReportOutcomeMeasure> otherOutcomeMeasures = new ArrayList<TSRReportOutcomeMeasure>();
            for (StudyOutcomeMeasureDTO somDto : somDtos) {
                TSRReportOutcomeMeasure outcomeMeasure = new TSRReportOutcomeMeasure();
                outcomeMeasure.setDescription(StConverter.convertToString(somDto.getDescription()));
                outcomeMeasure.setTitle(StConverter.convertToString(somDto.getName()));
                outcomeMeasure.setTimeFrame(StConverter.convertToString(somDto.getTimeFrame()));
                outcomeMeasure.setSafetyIssue(getValue(somDto.getSafetyIndicator(), INFORMATION_NOT_PROVIDED));

                if (!ISOUtil.isCdNull(somDto.getTypeCode()) 
                        && somDto.getTypeCode().getCode().equalsIgnoreCase(OutcomeMeasureTypeCode.PRIMARY.getCode())) {
                    primaryOutcomeMeasures.add(outcomeMeasure);
                } else if (!ISOUtil.isCdNull(somDto.getTypeCode()) 
                    && somDto.getTypeCode().getCode().equalsIgnoreCase(OutcomeMeasureTypeCode.SECONDARY.getCode())) {
                    secondaryOutcomeMeasures.add(outcomeMeasure);
                } else if (!ISOUtil.isCdNull(somDto.getTypeCode()) 
                        && somDto.getTypeCode().getCode().equalsIgnoreCase(
                                OutcomeMeasureTypeCode.OTHER_PRE_SPECIFIED.getCode())) {
                    otherOutcomeMeasures.add(outcomeMeasure);
                }
            }
            tsrReportGenerator.setPrimaryOutcomeMeasures(primaryOutcomeMeasures);
            tsrReportGenerator.setSecondaryOutcomeMeasures(secondaryOutcomeMeasures);
            tsrReportGenerator.setOtherOutcomeMeasures(otherOutcomeMeasures);
        }
    }

    private void setPlannedMarkers(StudyProtocolDTO studyProtocolDto) throws PAException {
        List<PlannedMarkerDTO> plannedMarkers =
            plannedMarkerService.getByStudyProtocol(studyProtocolDto.getIdentifier());
        List<TSRReportPlannedMarker> tsrMarkers = new ArrayList<TSRReportPlannedMarker>();
        for (PlannedMarkerDTO marker : plannedMarkers) {
            TSRReportPlannedMarker tsrMarker = new TSRReportPlannedMarker();
            tsrMarker.setName(getValue(marker.getName()));
            String evaluationType = getValue(marker.getEvaluationType());
            evaluationType = otherTextPresentAndJoin(evaluationType, getValue(marker.getEvaluationTypeOtherText()));
            tsrMarker.setEvaluationType(evaluationType);
            String assayType = getValue(marker.getAssayTypeCode());
            assayType = otherTextPresentAndJoin(assayType, getValue(marker.getAssayTypeOtherText()));        
            tsrMarker.setAssayType(assayType);
            tsrMarker.setAssayUse(getValue(marker.getAssayUseCode()));
            String assayPurpose = getValue(marker.getAssayPurposeCode());   
            tsrMarker.setAssayPurpose(assayPurpose);
            String specimenType = getValue(marker.getTissueSpecimenTypeCode());
            specimenType = otherTextPresentAndJoin(specimenType, getValue(marker.getSpecimenTypeOtherText()));
            tsrMarker.setTissueSpecimenType(specimenType);
            tsrMarker.setTissueCollectionMethod(getValue(marker.getTissueCollectionMethodCode()));
            tsrMarkers.add(tsrMarker);
        }
        Collections.sort(tsrMarkers, new Comparator<TSRReportPlannedMarker>() {
            public int compare(TSRReportPlannedMarker o1, TSRReportPlannedMarker o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        tsrReportGenerator.setPlannedMarkers(tsrMarkers);
    }
    private String otherTextPresentAndJoin(String typeValue, String otherText) {
        StringBuffer otherTextValue = new StringBuffer();
        if (typeValue != null) {
            String[] typeList = typeValue.split(",\\s*");
            if (typeList != null) {
                for (int i = 0, j = typeList.length; i < typeList.length; i++, j--) {
                    if (j == 1) {
                        if (StringUtils.equals(typeList[i], OTHER)) {
                            otherTextValue.append(StringUtils.join(new Object[] {
                                    typeList[i], ": ", otherText}));     
                        } else {
                            otherTextValue.append(typeList[i]);
                        }
                    } else {
                        if (StringUtils.equals(typeList[i], OTHER)) {
                            otherTextValue.append(StringUtils.join(new Object[] {
                                    typeList[i], ": ", otherText + ", "}));      
                        } else {
                            otherTextValue.append(typeList[i]);
                            otherTextValue.append(", ");
                        }
                    }
                }
            }
        }
        return otherTextValue.toString();
    }
    
    
    private void setSubGroupStratificationCriteria(StudyProtocolDTO studyProtocolDto) throws PAException {
        List<StratumGroupDTO> stratumGrpDtos = stratumGroupService.getByStudyProtocol(studyProtocolDto.getIdentifier());
        if (!stratumGrpDtos.isEmpty()) {
            List<TSRReportSubGroupStratificationCriteria> sgsCriterias =
                new ArrayList<TSRReportSubGroupStratificationCriteria>();
            for (StratumGroupDTO sgDto : stratumGrpDtos) {
                TSRReportSubGroupStratificationCriteria sgsCrit = new TSRReportSubGroupStratificationCriteria();
                sgsCrit.setLabel(getValue(sgDto.getGroupNumberText(), INFORMATION_NOT_PROVIDED));
                sgsCrit.setDescription(getValue(sgDto.getDescription(), INFORMATION_NOT_PROVIDED));
                sgsCriterias.add(sgsCrit);
            }
            tsrReportGenerator.setSgsCriterias(sgsCriterias);
        }
    }

    private void setParticipatingSites(StudyProtocolDTO studyProtocolDto)
    throws PAException, NullifiedRoleException {
        StudySiteDTO srDTO = new StudySiteDTO();
        srDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        List<StudySiteDTO> siteList = studySiteService.getByStudyProtocol(studyProtocolDto.getIdentifier(), srDTO);
        if (!siteList.isEmpty()) {
            List<TSRReportParticipatingSite> participatingSites = new ArrayList<TSRReportParticipatingSite>();
            for (final StudySiteDTO site : siteList) {
                final String siteID = IiConverter.convertToString(site
                        .getIdentifier());
                TSRReportParticipatingSite participatingSite = (TSRReportParticipatingSite) CacheUtils
                        .getFromCacheOrBackend(CacheUtils.getTSRSitesCache(),
                                siteID, new CacheUtils.Closure() {
                                    @Override
                                    public Object execute() throws PAException {                                        
                                        try {
                                            return convertToTsrSite(site);
                                        } catch (NullifiedRoleException e) {
                                            throw new PAException(e);
                                        }
                                    }
                                });

                participatingSites.add(participatingSite);
            }
            Collections.sort(participatingSites);
            tsrReportGenerator.setParticipatingSites(participatingSites);
        }
    }


    /**
     * @param sp
     * @return
     * @throws PAException
     * @throws NullifiedRoleException
     * @throws NumberFormatException
     */
    private TSRReportParticipatingSite convertToTsrSite(final StudySiteDTO sp)
            throws PAException, NullifiedRoleException {
        TSRReportParticipatingSite participatingSite = new TSRReportParticipatingSite();
        StudySiteAccrualStatusDTO ssas = studySiteAccrualStatusService
                .getCurrentStudySiteAccrualStatusByStudySite(sp.getIdentifier());
        Organization orgBo = correlationUtils.getPAOrganizationByIi(sp.getHealthcareFacilityIi());
        participatingSite.setFacility(getFacility(orgBo.getName(), getLocation(orgBo)));
        String recruitmentStatus = getValue(ssas.getStatusCode()) + " as of "
                + PAUtil.convertTsToFormattedDate(ssas.getStatusDate());
        participatingSite.setRecruitmentStatus(recruitmentStatus);
        participatingSite.setPoId(orgBo.getIdentifier());
        participatingSite.setTargetAccrual(getValue(sp.getTargetAccrualNumber()));                
        participatingSite.setLocalTrialIdentifier(getValue(sp.getLocalStudyProtocolIdentifier(),
                INFORMATION_NOT_PROVIDED));
        participatingSite.setOpenForAccrualDate(getValue(IvlConverter.convertTs().convertLowToString(
                sp.getAccrualDateRange()), INFORMATION_NOT_PROVIDED));
        participatingSite.setClosedForAccrualDate(getValue(IvlConverter.convertTs().convertHighToString(
                sp.getAccrualDateRange()), INFORMATION_NOT_PROVIDED));
        participatingSite.setProgramCode(getValue(sp.getProgramCodeText()));
                       
        List<StudySiteContactDTO> spcDTOs = new StudySiteContactServiceCachingDecorator(
                studySiteContactService).getByStudySite(sp.getIdentifier());
        for (StudySiteContactDTO spcDto : spcDTOs) {
            if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().equals(spcDto.getRoleCode().getCode())) {
                // Set contact info.
                
                String email = StringUtils.isEmpty(PAUtil
                        .getEmail(spcDto.getTelecomAddresses())) ? ""
                        : PAUtil.getEmail(spcDto
                                .getTelecomAddresses());
                
                String phone = StringUtils.isEmpty(PAUtil
                        .getPhone(spcDto.getTelecomAddresses())) ? ""
                        : PAUtil.getPhone(spcDto
                                .getTelecomAddresses());
                
                String extn = StringUtils.isEmpty(PAUtil
                        .getPhoneExtension(spcDto
                                .getTelecomAddresses())) ? ""
                        : PAUtil.getPhoneExtension(spcDto
                                .getTelecomAddresses()); 
                
                String contact = null;

                if (spcDto.getClinicalResearchStaffIi() != null) {
                    Person p = correlationUtils.getPAPersonByIi(spcDto.getClinicalResearchStaffIi());
                    contact = p.getFirstName() + SPACE + p.getLastName();
                } else if (spcDto.getOrganizationalContactIi() != null) {
                    PAContactDTO paCDto = correlationUtils.getContactByPAOrganizationalContactId((Long
                            .valueOf(spcDto.getOrganizationalContactIi().getExtension())));
                    contact = paCDto.getTitle();
                }
                StringBuilder builder = new StringBuilder();
                builder.append(contact).append(",").append(EMAIL).append(email).append(",").append(PHONE)
                .append(phone).append(StringUtils.isNotEmpty(extn) ? ", ext: " + extn : BLANK);
                participatingSite.setContact(builder.toString());

            } else {
                // Principal Investigator
                Person p = correlationUtils.getPAPersonByIi(spcDto.getClinicalResearchStaffIi());
                TSRReportInvestigator pi = new TSRReportInvestigator(p.getFirstName(), p.getMiddleName(), p
                        .getLastName(), getValue(spcDto.getRoleCode()));
                participatingSite.getInvestigators().add(pi);
            }
        }
        return participatingSite;
    }

    private String getFacility(String orgName, String location) {
        String retVal = INFORMATION_NOT_PROVIDED;
        if (orgName != null && location != null) {
            retVal = orgName + ", " + location;
        } else if (orgName != null) {
            retVal = orgName;
        } else if (location != null) {
            retVal = location;
        }
        return retVal;
    }

    private String getLocation(Organization orgBo) {
        String city = StringUtils.defaultString(orgBo.getCity(), " ");
        String state = StringUtils.defaultString(orgBo.getState(), " ");
        String pc = StringUtils.defaultString(orgBo.getPostalCode(), " ");
        String country = StringUtils.defaultString(orgBo.getCountryName(), " ");
        return city + ", " + state + " " + pc + " " + country;
    }

    private String getInterventionAltNames(InterventionDTO i) throws PAException {
        List<InterventionAlternateNameDTO> ianList =
            interventionAlternateNameService.getByIntervention(i.getIdentifier());
        
        StringBuffer interventionAltName = new StringBuffer();
        List<InterventionAlternateNameDTO> interventionNames = new ArrayList<InterventionAlternateNameDTO>();

        for (InterventionAlternateNameDTO ian : ianList) {            
                interventionNames.add(ian);
        }
        
        Collections.sort(interventionNames, new Comparator<InterventionAlternateNameDTO>() {
            public int compare(InterventionAlternateNameDTO o1, InterventionAlternateNameDTO o2) {
                return o1.getName().getValue().compareToIgnoreCase(o2.getName().getValue());
            }
        });

        for (Iterator<InterventionAlternateNameDTO> iter = interventionNames.iterator(); iter.hasNext();) {
            InterventionAlternateNameDTO altname = iter.next();
            interventionAltName.append(altname.getName().getValue());
            if (iter.hasNext()) {
                interventionAltName.append(", ");
            }
        }
        return interventionAltName.toString();
    }

    private String getGtdSponsorOrLeadOrganization(StudyProtocolDTO studyProtocolDto, StudySiteFunctionalCode enumValue,
            String defaultValue) throws PAException {
        String retVal = defaultValue;
        Organization org = ocsr.getOrganizationByFunctionRole(studyProtocolDto.getIdentifier(), CdConverter
                .convertToCd(enumValue));
        if (org != null) {
            retVal = org.getName();
        }
        return retVal;
    }

    private static String getValue(St st) {
        return getValue(st, null);
    }

    private static String getValue(St st, String defaultValue) {
        return (st != null && st.getValue() != null ? st.getValue() : defaultValue);
    }

    private String getValue(Cd cd) {
        return getValue(cd, null);
    }

    private static String getValue(Cd cd, String defaultValue) {
        return (!ISOUtil.isCdNull(cd) ? CdConverter.convertCdToString(cd) : defaultValue); 
    }

    private String getValue(String str, String defaultValue) {
        return (str != null ? str : defaultValue);
    }

    private String getValue(Bl bl) {
        return getValue(bl, null);
    }

    private static String getValue(Bl bl, String defaultValue) {
        return (bl != null && bl.getValue() != null ? (bl.getValue().booleanValue() ? YES : NO) : defaultValue);
    }

    private String getValue(Int i) {
        return getValue(i, null);
    }

    private String getValue(Int i, String defaultValue) {
        return (i != null && i.getValue() != null ? i.getValue().toString() : defaultValue);
    }


    /**
     * @param studyOverallStatusService the studyOverallStatusService to set
     */
    public void setStudyOverallStatusService(StudyOverallStatusServiceLocal studyOverallStatusService) {
        this.studyOverallStatusService = studyOverallStatusService;
    }

    /**
     * @param studyIndldeService the studyIndldeService to set
     */
    public void setStudyIndldeService(StudyIndldeServiceLocal studyIndldeService) {
        this.studyIndldeService = studyIndldeService;
    }

    /**
     * @param studyDiseaseService the studyDiseaseService to set
     */
    public void setStudyDiseaseService(StudyDiseaseServiceLocal studyDiseaseService) {
        this.studyDiseaseService = studyDiseaseService;
    }

    /**
     * @param armService the armService to set
     */
    public void setArmService(ArmServiceLocal armService) {
        this.armService = armService;
    }

    /**
     * @param plannedActivityService the plannedActivityService to set
     */
    public void setPlannedActivityService(PlannedActivityServiceLocal plannedActivityService) {
        this.plannedActivityService = plannedActivityService;
    }

    /**
     * @param studySiteService the studySiteService to set
     */
    public void setStudySiteService(StudySiteServiceLocal studySiteService) {
        this.studySiteService = studySiteService;
    }

    /**
     * @param studySiteContactService the studySiteContactService to set
     */
    public void setStudySiteContactService(StudySiteContactServiceLocal studySiteContactService) {
        this.studySiteContactService = studySiteContactService;
    }

    /**
     * @param studyContactService the studyContactService to set
     */
    public void setStudyContactService(StudyContactServiceLocal studyContactService) {
        this.studyContactService = studyContactService;
    }

    /**
     * @param studySiteAccrualStatusService the studySiteAccrualStatusService to set
     */
    public void setStudySiteAccrualStatusService(StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService) {
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
    }

    /**
     * @param studyOutcomeMeasureService the studyOutcomeMeasureService to set
     */
    public void setStudyOutcomeMeasureService(StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService) {
        this.studyOutcomeMeasureService = studyOutcomeMeasureService;
    }

    /**
     * @param studyRegulatoryAuthoritySvc the studyRegulatoryAuthorityService to set
     */
    public void setStudyRegulatoryAuthorityService(StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthoritySvc) {
        this.studyRegulatoryAuthorityService = studyRegulatoryAuthoritySvc;
    }

    /**
     * @param regulatoryInformationService the regulatoryInformationService to set
     */
    public void setRegulatoryInformationService(RegulatoryInformationServiceLocal regulatoryInformationService) {
        this.regulatoryInformationService = regulatoryInformationService;
    }

    /**
     * @param diseaseService the diseaseService to set
     */
    public void setDiseaseService(PDQDiseaseServiceLocal diseaseService) {
        this.diseaseService = diseaseService;
    }

    /**
     * @param interventionService the interventionService to set
     */
    public void setInterventionService(InterventionServiceLocal interventionService) {
        this.interventionService = interventionService;
    }

    /**
     * @param interventionAltNameSvc the interventionAlternateNameService to set
     */
    public void setInterventionAlternateNameService(InterventionAlternateNameServiceLocal interventionAltNameSvc) {
        this.interventionAlternateNameService = interventionAltNameSvc;
    }

    /**
     * @param studyResourcingService the studyResourcingService to set
     */
    public void setStudyResourcingService(StudyResourcingServiceLocal studyResourcingService) {
        this.studyResourcingService = studyResourcingService;
    }

    /**
     * @param paOrganizationService the paOrganizationService to set
     */
    public void setPaOrganizationService(PAOrganizationServiceRemote paOrganizationService) {
        this.paOrganizationService = paOrganizationService;
    }

    /**
     * @param stratumGroupService the stratumGroupService to set
     */
    public void setStratumGroupService(StratumGroupServiceLocal stratumGroupService) {
        this.stratumGroupService = stratumGroupService;
    }

    /**
     * @param ocsr the ocsr to set
     */
    public void setOcsr(OrganizationCorrelationServiceRemote ocsr) {
        this.ocsr = ocsr;
    }

    /**
     * @return the plannedMarkerService
     */
    public PlannedMarkerServiceLocal getPlannedMarkerService() {
        return plannedMarkerService;
    }

    /**
     * @param plannedMarkerService the plannedMarkerService to set
     */
    public void setPlannedMarkerService(PlannedMarkerServiceLocal plannedMarkerService) {
        this.plannedMarkerService = plannedMarkerService;
    }


    /**
     * @param studyIdentifiersService the studyIdentifiersService to set
     */
    public void setStudyIdentifiersService(
            StudyIdentifiersServiceLocal studyIdentifiersService) {
        this.studyIdentifiersService = studyIdentifiersService;
    }
}

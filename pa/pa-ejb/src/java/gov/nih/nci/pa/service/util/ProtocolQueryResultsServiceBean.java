/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2012, Mackson Consulting, LLC.   The Protocol  Abstraction (PA) Application
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

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudySubtypeCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.iso.convert.BaseStudyProtocolQueryConverter;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.dto.StudyAlternateTitleDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Hugh Reinhart
 * @since Feb 7, 2012
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessiveClassLength", "PMD.CyclomaticComplexity" })
public class ProtocolQueryResultsServiceBean implements ProtocolQueryResultsServiceLocal {

    private static final String IDS = "ids";

    @EJB
    private DataAccessServiceLocal dataAccessService;

    @EJB
    private RegistryUserServiceLocal registryUserService;
        

    /**
     * @param dataAccessService service to set (used for testing).
     */
    void setDataAccessService(DataAccessServiceLocal dataAccessService) {
        this.dataAccessService = dataAccessService;
    }

    /**
     * @param registryUserService service to set (used for testing).
     */
    void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    static final String QRY_STRING = "SELECT study_protocol_identifier,official_title"
            + ",proprietary_trial_indicator,record_verification_date,ctgov_xml_required_indicator,updating"
            + ",date_last_created,submission_number,nci_number,nct_number,lead_org_poid,lead_org_name"
            + ",lead_org_sp_identifier,current_dwf_status_code,current_dwf_status_date,current_study_overall_status"
            + ",current_admin_milestone,current_scientific_milestone,current_other_milestone,admin_checkout_identifier"
            + ",admin_checkout_user,scientific_checkout_identifier,scientific_checkout_user,study_pi_first_name"
            + ",study_pi_last_name,user_last_created_login,user_last_created_first,user_last_created_last, " 
            + "dcp_id, ctep_id,amendment_date,date_last_updated,phase_code,primary_purpose_code,start_date," 
            + "summary4fundingSponsor_type,sponsor_name,responsible_party_organization_name,"
            + "responsible_party_PI_first_name,responsible_party_PI_last_name,user_last_updated_login, "
            + "user_last_updated_first,user_last_updated_last,primary_completion_date,"
            + "study_protocol_type,study_subtype_code, submitter_org_name, current_admin_milestone_date, "
            + "current_scientific_milestone_date, current_other_milestone_date, processing_priority, "
            + "last_milestone, last_milestone_date, active_milestone, active_milestone_date, "            
            + "admin_checkout_csm_fname, admin_checkout_csm_lname, admin_checkout_reg_fname, admin_checkout_reg_lname, "
            + "scientific_checkout_csm_fname, scientific_checkout_csm_lname, scientific_checkout_reg_fname, "
            + "scientific_checkout_reg_lname, onhold_reason_code, onhold_date, offhold_date, cdr_id, amendment_number,"
            + "admin_checkout_date, scientific_checkout_date, comments, onhold_reason_text, study_source, ccr_id,"
            + "accrual_disease_code_system, previous_dwf_status_code, submiting_org_id, onhold_reason_category, "
            + "expected_abstraction_completion_date, expected_abstraction_completion_comments, "
            + "pcd_sent_to_pio_date, pcd_confirm_date, designee_notified_date, reporting_in_process_date, "
            + "three_month_reminder_date, five_month_reminder_date, seven_month_escalation_to_pio_date, "
            + "results_sent_to_pio_date, "
            + "results_approved_by_pio_date, prs_release_date, qa_comments_return_date, trial_published_date "
            + "FROM rv_search_results "
            + "WHERE study_protocol_identifier IN (:ids)";

    static final String STUDY_ID_QRY_STRING = "select study_protocol.identifier, study_site_owner.user_id "
            + "from study_protocol "
            + "INNER JOIN study_site ON study_protocol.identifier=study_site.study_protocol_identifier "
            + "LEFT JOIN study_site_owner ON study_site_owner.study_site_id = study_site.identifier "
            + "where study_site.functional_code='"
            + StudySiteFunctionalCode.TREATING_SITE.name()
            + "' and "
            + "study_site.healthcare_facility_identifier in (SELECT identifier from healthcare_facility "
            + "where healthcare_facility.organization_identifier="
            + "(select organization.identifier from organization where "
            + "cast (organization.assigned_identifier as bigint)=:orgId))";
    
    static final String OTHER_IDENTIFIERS_QRY_STRING = "select study_protocol_id , extension, " 
            + "identifier_name FROM study_otheridentifiers "
            + "WHERE study_protocol_id IN (:ids)";
    
    static final String STUDY_ALTERNATE_TITLE_QRY_STRING = "SELECT study_protocol_identifier, alternate_title," 
        + " category from study_alternate_title where study_protocol_identifier IN (:ids)";
    
    static final String LAST_UPDATED_DATE = "SELECT study_inbox.study_protocol_identifier, csm_user.first_name, "
            + "csm_user.last_name, csm_user.login_name, study_inbox.open_date "
            + "FROM study_inbox "
            + "LEFT JOIN csm_user " 
            + "ON study_inbox.user_last_updated_id = csm_user.user_id, "
            + "(SELECT study_protocol_identifier id, max(open_date) maxdate "
            + "FROM study_inbox WHERE study_protocol_identifier "
            + "IN (:ids) " 
            + "group by study_protocol_identifier) IQ "
            + "WHERE study_protocol_identifier=IQ.id AND open_date=IQ.maxdate ";

    static final String STUDY_PROGRAM_CODE_QRY_STRING = "select sp.study_protocol_id, p.identifier,"
            + "p.program_code, p.program_name,p.status_code from study_program_code sp join program_code p on "
            + "p.identifier = sp.program_code_id where sp.study_protocol_id in (:ids)";

    private static final int STUDY_PROTOCOL_IDENTIFIER_IDX = 0;
    private static final int OFFICIAL_TITLE_IDX = 1;
    private static final int PROPRIETARY_TRIAL_INDICATOR_IDX = 2;
    private static final int RECORD_VERIFICATION_DATE_IDX = 3;
    private static final int CTGOV_XML_REQUIRED_INDICATOR_IDX = 4;
    private static final int UPDATING_IDX = 5;
    private static final int DATE_LAST_CREATED_IDX = 6;
    private static final int SUBMISSION_NUMBER_IDX = 7;
    private static final int NCI_NUMBER_IDX = 8;
    private static final int NCT_NUMBER_IDX = 9;
    private static final int LEAD_ORG_POID_IDX = 10;
    private static final int LEAD_ORG_NAME_IDX = 11;
    private static final int LEAD_ORG_SP_IDENTIFIER_IDX = 12;
    private static final int CURRENT_DWF_STATUS_CODE_IDX = 13;
    private static final int CURRENT_DWF_STATUS_DATE_IDX = 14;
    private static final int CURRENT_STUDY_OVERALL_STATUS_IDX = 15;
    private static final int CURRENT_ADMIN_MILESTONE_IDX = 16;
    private static final int CURRENT_SCIENTIFIC_MILESTONE_IDX = 17;
    private static final int CURRENT_OTHER_MILESTONE_IDX = 18;
    private static final int ADMIN_CHECKOUT_IDENTIFIER_IDX = 19;
    private static final int ADMIN_CHECKOUT_USER_IDX = 20;
    private static final int SCIENTIFIC_CHECKOUT_IDENTIFIER_IDX = 21;
    private static final int SCIENTIFIC_CHECKOUT_USER_IDX = 22;
    private static final int STUDY_PI_FIRST_NAME_IDX = 23;
    private static final int STUDY_PI_LAST_NAME_IDX = 24;
    private static final int USER_LAST_CREATED_LOGIN_IDX = 25;
    private static final int USER_LAST_CREATED_FIRST_IDX = 26;
    private static final int USER_LAST_CREATED_LAST_IDX = 27;
    private static final int DCP_ID_IDX = 28;
    private static final int CTEP_ID_IDX = 29;
    private static final int AMENDMENT_DATE = 30;
    private static final int DATE_LAST_UPDATED = 31;    
    private static final int PHASE_CODE = 32;
    private static final int PRIMARY_PURPOSE_CODE = 33;
    private static final int START_DATE = 34;    
    private static final int SUMMARY4_FUNDING_SPONSOR_TYPE = 35;
    private static final int SPONSOR_NAME = 36;
    private static final int RESPONSIBILITY_PARTY_ORG_NAME = 37;
    private static final int RESPONSIBILITY_PARTY_PI_FIRST_NAME = 38;
    private static final int RESPONSIBILITY_PARTY_PI_LAST_NAME = 39;    
    private static final int USER_LAST_UPDATED_LOGIN_IDX = 40;
    private static final int USER_LAST_UPDATED_FIRST_IDX = 41;
    private static final int USER_LAST_UPDATED_LAST_IDX = 42;
    private static final int PRIMARY_COMPLETION_DATE_IDX = 43;
    private static final int STUDY_PROTOCOL_TYPE = 44;
    private static final int STUDY_SUBTYPE_CODE = 45;
    private static final int SUBMITTER_ORG_NAME_IDX = 46;
    private static final int CURRENT_ADMIN_MILESTONE_DATE_IDX = 47;
    private static final int CURRENT_SCIENTIFIC_MILESTONE_DATE_IDX = 48;
    private static final int CURRENT_OTHER_MILESTONE_DATE_IDX = 49;
    private static final int PROCESSING_PRIORITY_IDX = 50;
    private static final int LAST_MILESTONE_IDX = 51;
    private static final int LAST_MILESTONE_DATE_IDX = 52;
    private static final int ACTIVE_MILESTONE_IDX = 53;
    private static final int ACTIVE_MILESTONE_DATE_IDX = 54;
    
    private static final int ADMIN_CHECKOUT_CSM_FNAME = 55;
    private static final int ADMIN_CHECKOUT_CSM_LNAME = 56;
    private static final int ADMIN_CHECKOUT_REG_FNAME = 57;
    private static final int ADMIN_CHECKOUT_REG_LNAME = 58;
    private static final int SCIENTIFIC_CHECKOUT_CSM_FNAME = 59;
    private static final int SCIENTIFIC_CHECKOUT_CSM_LNAME = 60;
    private static final int SCIENTIFIC_CHECKOUT_REG_FNAME = 61;
    private static final int SCIENTIFIC_CHECKOUT_REG_LNAME = 62;   
    
    private static final int ONHOLD_REASON_CODE = 63;
    private static final int ONHOLD_DATE = 64;
    private static final int OFFHOLD_DATE = 65;
    
    private static final int CDR_ID_IDX = 66;
    private static final int AMENDMENT_NUMBER_IDX = 67;
    private static final int ADMIN_CHECKOUT_DATE_IDX = 68;
    private static final int SCIENTIFIC_CHECKOUT_DATE_IDX = 69;
    private static final int COMMENTS_IDX = 70;
    private static final int ONHOLD_REASON_DESCRIPTION = 71;
    private static final int STUDY_SOURCE_CODE = 72;
    private static final int CCR_ID_IDX = 73;
    private static final int ACCRUAL_DISEASE_CODESYSTEM_IDX = 74;
    private static final int PREVIOUS_DWF_STATUS_CODE_IDX = 75;
    private static final int SUBMITTER_ORG_ID_IDX = 76;
    private static final int ONHOLD_CATEGORY = 77;    
    private static final int EXPECTED_ABSTRACTION_COMPLETION_DATE = 78;
    private static final int EXPECTED_ABSTRACTION_COMPLETION_COMMENTS = 79;
    private static final int PCD_SENT_TO_PIO_DATE = 80;
    private static final int PCD_CONFIRM_DATE = 81;
    private static final int DESIGNEE_NOTIFIED_DATE = 82;
    private static final int REPORTING_IN_PROCESS_DATE = 83;
    private static final int THREE_MONTH_REMINDER_DATE = 84;
    private static final int FIVE_MONTH_REMINDER_DATE = 85;
    private static final int SEVEN_MONTH_ESCALATION_TO_PIO_DATE = 86;
    private static final int RESULTS_SENT_TO_PIO_DATE = 87;
    private static final int RESULTS_APPROVED_BY_PIO_DATE = 88;
    private static final int PRS_RELEASE_DATE = 89;
    private static final int QA_COMMENTS_RETURN_DATE = 90;
    private static final int TRIAL_PUBLISHED_DATE = 91;

    
    private static final int UPDATER_FIRST_NAME_IDX = 1;
    private static final int UPDATER_LAST_NAME_IDX = 2;
    private static final int UPDATER_LOGIN_NAME_IDX = 3;
    private static final int LAST_UPDATE_DATE_INX = 4;
       
    private static final int ACCESS_NO = 0;
    private static final int ACCESS_ADMIN = 1;
    private static final int ACCESS_OWNER = 2;
    private static final int ACCESS_SITE = 3;


    private static final int PROGRAM_CODE_IDENTIFIER_IDX = 1;
    private static final int PROGRAM_CODE_CODE_IDX = 2;
    private static final int PROGRAM_CODE_PROGRAM_NAME_IDX = 3;
    private static final int PROGRAM_CODE_STATUS_IDX = 4;

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public List<StudyProtocolQueryDTO> getResults(List<Long> protocols,
            boolean myTrialsOnly, Long userId,
            ProtocolQueryPerformanceHints... hints) throws PAException {
        if (protocols == null) {
            return new ArrayList<StudyProtocolQueryDTO>();
        }
        RegistryUser user = registryUserService.getPartialUserById(userId);
        Map<Long, Boolean> studyIDAndSiteOwnershipMap = getStudiesOnWhichUserHasSite(user);
        Map<Long, Integer> ownerMap = getOwnerMapAndFilterTrials(protocols,
                myTrialsOnly, userId, user, studyIDAndSiteOwnershipMap.keySet());
        if (ownerMap.isEmpty()) {
            return new ArrayList<StudyProtocolQueryDTO>();
        }
        
        List<String> rssOrgs = getRSSOrganizationNames();
        DAQuery query = new DAQuery();
        query.setSql(true);
        query.setText(QRY_STRING);
        query.addParameter(IDS, ownerMap.keySet());
        List<Object[]> queryList = dataAccessService.findByQuery(query);
         
        List<StudyProtocolQueryDTO> dtoList = convertResults(queryList, 
                ownerMap, myTrialsOnly, user, studyIDAndSiteOwnershipMap, rssOrgs);
        
        if (!ArrayUtils.contains(hints, ProtocolQueryPerformanceHints.SKIP_OTHER_IDENTIFIERS)) {
            query = new DAQuery();
            query.setSql(true);
            query.setText(OTHER_IDENTIFIERS_QRY_STRING);
            query.addParameter(IDS, ownerMap.keySet());       
            List<Object[]> otherIdentifierQueryList = dataAccessService.findByQuery(query);
            
            for (Object[] obj : otherIdentifierQueryList) { 
                Long studyprotocolId = ((BigInteger) obj[0]).longValue();
                for (StudyProtocolQueryDTO dto : dtoList) {     
                   final String identifierName = StringUtils.defaultString((String) obj[2]);
                   if (dto.getStudyProtocolId().equals(studyprotocolId) 
                           && !identifierName.equals(IiConverter.STUDY_PROTOCOL_IDENTIFIER_NAME)) {                  
                       dto.getOtherIdentifiers().add((String) obj[1]);
                   }
                }
            }
        }
        
        if (!ArrayUtils.contains(hints, ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES)) {
            //populate study alternate title information
            query = new DAQuery();
            query.setSql(true);
            query.setText(STUDY_ALTERNATE_TITLE_QRY_STRING);
            query.addParameter(IDS, protocols);
            List<Object[]> studyAlternateTitlesQueryList = dataAccessService.findByQuery(query);
            
            for (Object[] obj : studyAlternateTitlesQueryList) {
                Long studyprotocolId = ((BigInteger) obj[0]).longValue();
                for (StudyProtocolQueryDTO dto : dtoList) {
                    String alternateTitle = (String) obj[1];
                    String category = (String) obj[2];
                    if (dto.getStudyProtocolId().equals(studyprotocolId)) {
                        if (dto.getStudyAlternateTitles() == null) { // NOPMD
                            dto.setStudyAlternateTitles(new TreeSet<StudyAlternateTitleDTO>());                        
                        }
                        StudyAlternateTitleDTO alternateTitleDTO = new StudyAlternateTitleDTO();
                        alternateTitleDTO.setAlternateTitle(StConverter.convertToSt(alternateTitle));
                        alternateTitleDTO.setCategory(StConverter.convertToSt(category));
                        dto.getStudyAlternateTitles().add(alternateTitleDTO);
                    }
                }
            }
        }
        
        if (!ArrayUtils.contains(hints, ProtocolQueryPerformanceHints.SKIP_LAST_UPDATER_INFO)) {
            query = new DAQuery();
            query.setSql(true);
            query.setText(LAST_UPDATED_DATE);
            query.addParameter(IDS, ownerMap.keySet());       
            List<Object[]> lastUpdatedDateQueryList = dataAccessService.findByQuery(query);
            for (StudyProtocolQueryDTO dto : dtoList) {   
                for (Object[] obj : lastUpdatedDateQueryList) {
                    Long studyprotocolId = ((BigInteger) obj[STUDY_PROTOCOL_IDENTIFIER_IDX]).longValue();
                    if (dto.getStudyProtocolId().equals(studyprotocolId)) {
                        Date openDate = (Date) obj[LAST_UPDATE_DATE_INX];
                        dto.setUpdatedDate(openDate);
                        
                        User updater = new User();
                        updater.setFirstName((String) obj[UPDATER_FIRST_NAME_IDX]);
                        updater.setLastName((String) obj[UPDATER_LAST_NAME_IDX]);
                        updater.setLoginName((String) obj[UPDATER_LOGIN_NAME_IDX]);
                        dto.setLastUpdaterDisplayName(CsmUserUtil.getDisplayUsername(updater));
                    }
                }
            }
        }
        if (!ArrayUtils.contains(hints, ProtocolQueryPerformanceHints.SKIP_PROGRAM_CODES)) {
            query = new DAQuery();
            query.setSql(true);
            query.setText(STUDY_PROGRAM_CODE_QRY_STRING);
            query.addParameter(IDS, ownerMap.keySet());
            List<Object[]> programCodeTupleList = dataAccessService.findByQuery(query);
            for (StudyProtocolQueryDTO dto : dtoList) {
                for (Object[] obj : programCodeTupleList) {
                    Long studyprotocolId = ((BigInteger) obj[STUDY_PROTOCOL_IDENTIFIER_IDX]).longValue();
                    if (dto.getStudyProtocolId().equals(studyprotocolId)) {
                        ProgramCodeDTO programCodeDTO = new ProgramCodeDTO();
                        String status = (String) obj[PROGRAM_CODE_STATUS_IDX];
                        programCodeDTO.setActive(ActiveInactiveCode.ACTIVE == ActiveInactiveCode.getByCode(status));
                        if (obj[PROGRAM_CODE_IDENTIFIER_IDX] instanceof BigInteger) {
                            programCodeDTO.setId(((BigInteger) obj[PROGRAM_CODE_IDENTIFIER_IDX]).longValue());
                        }  else {
                            programCodeDTO.setId(((Integer) obj[PROGRAM_CODE_IDENTIFIER_IDX]).longValue());
                        }

                        programCodeDTO.setProgramCode((String) obj[PROGRAM_CODE_CODE_IDX]);
                        programCodeDTO.setProgramName((String) obj[PROGRAM_CODE_PROGRAM_NAME_IDX]);
                        dto.getProgramCodes().add(programCodeDTO);
                    }
                }
            }
        }

        return dtoList;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyProtocolQueryDTO> getResultsLean(List<Long> ids) throws PAException {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<StudyProtocolQueryDTO>();
        }
        DAQuery query = new DAQuery();
        query.setSql(true);
        query.setText(QRY_STRING);
        query.addParameter(IDS, ids);
        List<Object[]> qryList = dataAccessService.findByQuery(query);
        List<StudyProtocolQueryDTO> result = new ArrayList<StudyProtocolQueryDTO>();
        for (Object[] row : qryList) {
            StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
            loadGeneralData(dto, row);
            loadStatusData(dto, row);
            result.add(dto);
        }
        return result;
    }

    /**
     * @return {@link List} list of RSS organization names.
     * @throws PAException PAException
     */
    private List<String> getRSSOrganizationNames() throws PAException {
        String[] rssOrgs = PaRegistry.getLookUpTableService()
                .getPropertyValue("rss.leadOrgs").split(",");
        return Arrays.asList(rssOrgs);
    }
    

    /**
     * @param user - the registry user
     * @return IDs of studies on which the user's affiliated organization is a participating site.
     * @throws PAException
     */
    Map<Long, Boolean> getStudiesOnWhichUserHasSite(RegistryUser user)
            throws PAException {
        Map<Long, Boolean> map = new HashMap<Long, Boolean>();        
        if (user != null && user.getAffiliatedOrganizationId() != null) {
            DAQuery query = new DAQuery();
            query.setSql(true);
            query.setText(STUDY_ID_QRY_STRING);
            query.addParameter("orgId", user.getAffiliatedOrganizationId());
            List<Object[]> queryList = dataAccessService.findByQuery(query);
            for (Object[] row : queryList) {
                BigInteger studyId = (BigInteger) row[0];
                BigInteger siteOwnerId = (BigInteger) row[1];
                Boolean isOwner = siteOwnerId == null
                        || (siteOwnerId != null && siteOwnerId.longValue() == user.getId()
                                .longValue())
                        || Boolean.TRUE.equals(map.get(studyId.longValue()));
                map.put(studyId.longValue(), isOwner);
            }
        }
        return map;
    }

    private Map<Long, Integer> getOwnerMapAndFilterTrials(
            List<Long> protocols, boolean myTrialsOnly, Long userId,
            RegistryUser user, Set<Long> studiesOnWhichUserHasSite) throws PAException {
        Set<Long> ownedStudies = getOwnedStudies(userId);        
        Map<Long, Integer> ownerMap = new HashMap<Long, Integer>();
        final boolean isAdmin = isAdmin(user);
        for (Long spID : protocols) {
            Integer access = ACCESS_NO;     
            if (ownedStudies.contains(spID)) {
                access = ACCESS_OWNER;
            } else if (studiesOnWhichUserHasSite.contains(spID)) {
                access = ACCESS_SITE;
            } else if (isAdmin) {
                access = ACCESS_ADMIN;
            }
            if (access != ACCESS_NO || !myTrialsOnly) {
                ownerMap.put(spID, access);
            }
        }
        return ownerMap;
    }

    /**
     * @param user
     * @return
     */
    private boolean isAdmin(RegistryUser user) {
        return user == null ? false : UserOrgType.ADMIN.equals(user.getAffiliatedOrgUserType());
    }

    private Set<Long> getOwnedStudies(Long userId) {
        Set<Long> ownedStudies = new HashSet<Long>();
        if (userId != null) {

            DAQuery query = new DAQuery();
            query.setSql(true);
            query.setText("SELECT study_id FROM study_owner WHERE user_id = :userId");
            query.addParameter("userId", userId);
            List<BigInteger> queryList = dataAccessService.findByQuery(query);
            for (BigInteger studyId : queryList) {
                ownedStudies.add(studyId.longValue());
            }
        }
        return ownedStudies;
    }

    /**
     * @param qryList
     * @param ownerMap
     * @param myTrialsOnly
     * @param userId
     * @param rssOrgs 
     * @param studyIDs IDs of studies on which the user's affiliated organization is a participating site.
     * @return
     * @throws PAException
     */
    @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ExcessiveParameterList" })
    private List<StudyProtocolQueryDTO> convertResults(List<Object[]> qryList,
            Map<Long, Integer> ownerMap, boolean myTrialsOnly, RegistryUser user,
            Map<Long, Boolean> studyIDAndSiteOwnershipMap, List<String> rssOrgs) throws PAException {
        String affiliatedOrg = getAffiliatedOrg(user);
        List<StudyProtocolQueryDTO> result = new ArrayList<StudyProtocolQueryDTO>();
        for (Object[] row : qryList) {
            StudyProtocolQueryDTO dto = convertRow(row, rssOrgs);
            String poid = (String) row[LEAD_ORG_POID_IDX];
            int access = ownerMap.get(dto.getStudyProtocolId());
            switch (access) {
                case ACCESS_OWNER:
                    dto.setSearcherTrialOwner(true);
                    result.add(dto);
                    break;
                case ACCESS_SITE:                    
                    dto.setSearcherTrialOwner(StringUtils.equals(affiliatedOrg, poid));
                    result.add(dto);
                    break;
                case ACCESS_ADMIN:                    
                    dto.setSearcherTrialOwner(StringUtils.equals(affiliatedOrg, poid));
                    if (!myTrialsOnly || dto.isSearcherTrialOwner()) {
                        result.add(dto);
                    }
                    break;
                default:
                    dto.setSearcherTrialOwner(false);
                    result.add(dto);
            }            
            if (studyIDAndSiteOwnershipMap.containsKey(dto.getStudyProtocolId())) {
                dto.setCurrentUserHasSite(true);
                dto.setCurrentUserIsSiteOwner(studyIDAndSiteOwnershipMap
                        .get(dto.getStudyProtocolId()));
            } 
        }
        return result;
    }

    private String getAffiliatedOrg(RegistryUser user) throws PAException {
        String affiliatedOrg = "";        
        if (user != null) {
            affiliatedOrg = user.getAffiliateOrg();
        }
        return affiliatedOrg;
    }

    private StudyProtocolQueryDTO convertRow(Object[] row, List<String> rssOrgs) {
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        loadGeneralData(dto, row);
        loadSubmissionType(dto, row);
        loadStatusData(dto, row);
        loadCheckoutData(dto, row);
        setFlags(dto, row, rssOrgs);
        return dto;
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    void setFlags(StudyProtocolQueryDTO dto, Object[] row,
            List<String> rssOrgs) {
        if (dto.isProprietaryTrial()
                && !rssOrgs.contains(row[LEAD_ORG_NAME_IDX])
                && dto.getDocumentWorkflowStatusCode() != null
                && dto.getDocumentWorkflowStatusCode().isAcceptedOrAbove()) {
            dto.setSiteSelfRegistrable(true);
        }
    }

    @SuppressWarnings("PMD.ExcessiveMethodLength")
    private void loadGeneralData(StudyProtocolQueryDTO dto, Object[] row) {
        dto.setOfficialTitle((String) row[OFFICIAL_TITLE_IDX]);
        dto.setCtgovXmlRequiredIndicator((Boolean) row[CTGOV_XML_REQUIRED_INDICATOR_IDX]);
        dto.getLastCreated().setDateLastCreated((Date) row[DATE_LAST_CREATED_IDX]);
        User user = new User();
        user.setFirstName((String) row[USER_LAST_CREATED_FIRST_IDX]);
        user.setLastName((String) row[USER_LAST_CREATED_LAST_IDX]);
        user.setLoginName((String) row[USER_LAST_CREATED_LOGIN_IDX]);
        dto.getLastCreated().setUserLastCreated(user.getLoginName());
        dto.getLastCreated().setUserLastDisplayName(CsmUserUtil.getDisplayUsername(user));
        dto.setLeadOrganizationName((String) row[LEAD_ORG_NAME_IDX]);
        dto.setLeadOrganizationPOId((String) row[LEAD_ORG_POID_IDX]);
        dto.setLocalStudyProtocolIdentifier((String) row[LEAD_ORG_SP_IDENTIFIER_IDX]);
        dto.setNciIdentifier((String) row[NCI_NUMBER_IDX]);
        dto.setNctIdentifier((String) row[NCT_NUMBER_IDX]);
        dto.setCdrId((String) row[CDR_ID_IDX]);
        String piFirst = (String) row[STUDY_PI_FIRST_NAME_IDX];
        String piLast = (String) row[STUDY_PI_LAST_NAME_IDX];
        dto.setPiFullName(piLast + (StringUtils.isEmpty(piFirst) ? "" : ", " + piFirst));
        dto.setProprietaryTrial(BooleanUtils.toBoolean((Boolean) row[PROPRIETARY_TRIAL_INDICATOR_IDX]));
        dto.setRecordVerificationDate((Date) row[RECORD_VERIFICATION_DATE_IDX]);
        dto.setStudyProtocolId(((BigInteger) row[STUDY_PROTOCOL_IDENTIFIER_IDX]).longValue());
        dto.setDcpId((String) row[DCP_ID_IDX]);
        dto.setCtepId((String) row[CTEP_ID_IDX]);
        dto.setCcrId((String) row[CCR_ID_IDX]);
        dto.setAccrualDiseaseCode((String) row[ACCRUAL_DISEASE_CODESYSTEM_IDX]);
        final Date amendmentDate = (Date) row[AMENDMENT_DATE];
        dto.setAmendmentDate(amendmentDate); 
        dto.setUpdatedDate((Date) row[DATE_LAST_UPDATED]);
        
        dto.setPhaseName((String) row[PHASE_CODE]);
        dto.setPrimaryPurpose((String) row[PRIMARY_PURPOSE_CODE]);
        dto.setStartDate((Date) row[START_DATE]);
        dto.setSponsorName((String) row[SPONSOR_NAME]);
        dto.setSummary4FundingSponsorType((String) row[SUMMARY4_FUNDING_SPONSOR_TYPE]);
        String responsibleParty = (String) row[RESPONSIBILITY_PARTY_ORG_NAME];
        if (StringUtils.isEmpty(responsibleParty) 
                && StringUtils.isNotEmpty((String) row[RESPONSIBILITY_PARTY_PI_FIRST_NAME])) {           
                responsibleParty = (String) row[RESPONSIBILITY_PARTY_PI_FIRST_NAME] + "," 
                                + (String) row[RESPONSIBILITY_PARTY_PI_LAST_NAME]; 
            
        }
        dto.setResponsiblePartyName(responsibleParty);
        dto.setPrimaryCompletionDate((Date) row[PRIMARY_COMPLETION_DATE_IDX]);        
        dto.setStudyProtocolType((String) row[STUDY_PROTOCOL_TYPE]);
        
        final StudySubtypeCode studySubtypeCode = getEnumFromString(
                StudySubtypeCode.class, (String) row[STUDY_SUBTYPE_CODE]);
        dto.setStudySubtypeCode(studySubtypeCode != null ? studySubtypeCode
                .getCode() : "");
        final StudySourceCode studySource = getEnumFromString(
                StudySourceCode.class, (String) row[STUDY_SOURCE_CODE]);
        dto.setStudySource(studySource != null ? studySource.getCode() : "");
        
        loadAmendmentData(dto, row, amendmentDate);
        
        dto.setProcessingPriority((Integer) row[PROCESSING_PRIORITY_IDX]);
        dto.setProcessingComments((String) row[COMMENTS_IDX]);
        dto.setSubmitterOrgName((String) row[SUBMITTER_ORG_NAME_IDX]);
        if (row[SUBMITTER_ORG_ID_IDX] != null) {
            dto.setSubmitterOrgId(Long.valueOf((String) row[SUBMITTER_ORG_ID_IDX]));
        }
        dto.setOverriddenExpectedAbstractionCompletionDate((Date) row[EXPECTED_ABSTRACTION_COMPLETION_DATE]);
        dto.setOverriddenExpectedAbstractionCompletionComments((String) row[EXPECTED_ABSTRACTION_COMPLETION_COMMENTS]);
        dto.setPcdSentToPIODate((Date) row[PCD_SENT_TO_PIO_DATE]);
        dto.setPcdConfirmedDate((Date) row[PCD_CONFIRM_DATE]);
        dto.setDesgneeNotifiedDate((Date) row[DESIGNEE_NOTIFIED_DATE]);
        dto.setReportingInProcessDate((Date) row[REPORTING_IN_PROCESS_DATE]);
        dto.setThreeMonthReminderDate((Date) row[THREE_MONTH_REMINDER_DATE]);
        dto.setFiveMonthReminderDate((Date) row[FIVE_MONTH_REMINDER_DATE]);
        dto.setSevenMonthEscalationtoPIODate((Date) row[SEVEN_MONTH_ESCALATION_TO_PIO_DATE]);
        dto.setResultsSentToPIODate((Date) row[RESULTS_SENT_TO_PIO_DATE]);
        dto.setResultsApprovedByPIODate((Date) row[RESULTS_APPROVED_BY_PIO_DATE]);
        dto.setPrsReleaseDate((Date) row[PRS_RELEASE_DATE]);
        dto.setQaCommentsReturnedDate((Date) row[QA_COMMENTS_RETURN_DATE]);
        dto.setTrialPublishedDate((Date) row[TRIAL_PUBLISHED_DATE]);
        
        loadRecentHoldData(dto, row);
    }

    /**
     * @param dto
     * @param row
     * @param amendmentDate
     */
    private void loadAmendmentData(StudyProtocolQueryDTO dto, Object[] row,
            final Date amendmentDate) {
        if (amendmentDate != null) {
            User updatedUser = new User();
            updatedUser.setFirstName((String) row[USER_LAST_UPDATED_FIRST_IDX]);
            updatedUser.setLastName((String) row[USER_LAST_UPDATED_LAST_IDX]);
            updatedUser.setLoginName((String) row[USER_LAST_UPDATED_LOGIN_IDX]);
            dto.setLastUpdatedUserDisplayName(CsmUserUtil.getDisplayUsername(updatedUser));
            dto.setAmendmentNumber((String) row[AMENDMENT_NUMBER_IDX]);
        }
    }

    /**
     * @param dto
     * @param row
     */
    private void loadRecentHoldData(StudyProtocolQueryDTO dto, Object[] row) {
        // Most recent on-hold.
        final OnholdReasonCode reasonCode = getEnumFromString(
                OnholdReasonCode.class, (String) row[ONHOLD_REASON_CODE]);
        dto.setRecentHoldReason(reasonCode != null ? reasonCode.getCode() : "");
        dto.setRecentOffHoldDate((Date) row[OFFHOLD_DATE]);
        dto.setRecentOnHoldDate((Date) row[ONHOLD_DATE]);
        dto.setRecentHoldDescription((String) row[ONHOLD_REASON_DESCRIPTION]);
        dto.setRecentHoldCategory((String) row[ONHOLD_CATEGORY]);
    }

    private void loadSubmissionType(StudyProtocolQueryDTO dto, Object[] row) {
        Boolean updating = (Boolean) row[UPDATING_IDX];
        if (!(updating == null)) {
            dto.setSubmissionTypeCode(SubmissionTypeCode.U);
        } else {
            Integer submissionNum = (Integer) row[SUBMISSION_NUMBER_IDX];
            if (submissionNum != null) {
                dto.setSubmissionTypeCode(submissionNum == 1 ? SubmissionTypeCode.O : SubmissionTypeCode.A);
            }
        }
    }

    private void loadStatusData(StudyProtocolQueryDTO dto, Object[] row) {
        String tstr = (String) row[CURRENT_DWF_STATUS_CODE_IDX];
        dto.setDocumentWorkflowStatusCode(getEnumFromString(DocumentWorkflowStatusCode.class, tstr));
        dto.setViewTSR(!BaseStudyProtocolQueryConverter.NON_TSR_DWF.contains(dto.getDocumentWorkflowStatusCode()));
        dto.setDocumentWorkflowStatusDate((Date) row[CURRENT_DWF_STATUS_DATE_IDX]);
        
        tstr = (String) row[PREVIOUS_DWF_STATUS_CODE_IDX];
        dto.setPreviousDocumentWorkflowStatusCode(getEnumFromString(DocumentWorkflowStatusCode.class, tstr));
        
        tstr = (String) row[CURRENT_ADMIN_MILESTONE_IDX];
        dto.getMilestones().getAdminMilestone().setMilestone(getEnumFromString(MilestoneCode.class, tstr));
        dto.getMilestones().getAdminMilestone().setMilestoneDate((Date) row[CURRENT_ADMIN_MILESTONE_DATE_IDX]);
        
        tstr = (String) row[CURRENT_SCIENTIFIC_MILESTONE_IDX];
        dto.getMilestones().getScientificMilestone().setMilestone(getEnumFromString(MilestoneCode.class, tstr));
        dto.getMilestones()
                .getScientificMilestone()
                .setMilestoneDate(
                        (Date) row[CURRENT_SCIENTIFIC_MILESTONE_DATE_IDX]);
        
        tstr = (String) row[CURRENT_OTHER_MILESTONE_IDX];
        dto.getMilestones().getStudyMilestone().setMilestone(getEnumFromString(MilestoneCode.class, tstr));
        dto.getMilestones().getStudyMilestone().setMilestoneDate((Date) row[CURRENT_OTHER_MILESTONE_DATE_IDX]);
        
        tstr = (String) row[LAST_MILESTONE_IDX];
        dto.getMilestones().getLastMilestone().setMilestone(getEnumFromString(MilestoneCode.class, tstr));
        dto.getMilestones().getLastMilestone().setMilestoneDate((Date) row[LAST_MILESTONE_DATE_IDX]);
        
        tstr = (String) row[ACTIVE_MILESTONE_IDX];
        dto.getMilestones().getActiveMilestone().setMilestone(getEnumFromString(MilestoneCode.class, tstr));
        dto.getMilestones().getActiveMilestone().setMilestoneDate((Date) row[ACTIVE_MILESTONE_DATE_IDX]);
        
        tstr = (String) row[CURRENT_STUDY_OVERALL_STATUS_IDX];
        dto.setStudyStatusCode(tstr == null ? null : StudyStatusCode.valueOf(tstr));
    }

    private static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
        if (string != null) {
            return Enum.valueOf(c, string);
        }
        return null;
    }

    private void loadCheckoutData(StudyProtocolQueryDTO dto, Object[] row) {
        final Number adminCid = (Number) row[ADMIN_CHECKOUT_IDENTIFIER_IDX];
        dto.getAdminCheckout().setCheckoutId(adminCid == null ? null : adminCid.longValue());
        dto.getAdminCheckout().setCheckoutBy((String) row[ADMIN_CHECKOUT_USER_IDX]);
        dto.getAdminCheckout().setCsmFirstName((String) row[ADMIN_CHECKOUT_CSM_FNAME]);
        dto.getAdminCheckout().setCsmLastName((String) row[ADMIN_CHECKOUT_CSM_LNAME]);
        dto.getAdminCheckout().setRegistryFirstName((String) row[ADMIN_CHECKOUT_REG_FNAME]);
        dto.getAdminCheckout().setRegistryLastName((String) row[ADMIN_CHECKOUT_REG_LNAME]);
        dto.getAdminCheckout().setCheckoutDate((Date) row[ADMIN_CHECKOUT_DATE_IDX]);

        
        final Number scientificCid = (Number) row[SCIENTIFIC_CHECKOUT_IDENTIFIER_IDX];
        dto.getScientificCheckout().setCheckoutId(scientificCid == null ? null : scientificCid.longValue());
        dto.getScientificCheckout().setCheckoutBy((String) row[SCIENTIFIC_CHECKOUT_USER_IDX]);
        dto.getScientificCheckout().setCsmFirstName((String) row[SCIENTIFIC_CHECKOUT_CSM_FNAME]);
        dto.getScientificCheckout().setCsmLastName((String) row[SCIENTIFIC_CHECKOUT_CSM_LNAME]);
        dto.getScientificCheckout().setRegistryFirstName((String) row[SCIENTIFIC_CHECKOUT_REG_FNAME]);
        dto.getScientificCheckout().setRegistryLastName((String) row[SCIENTIFIC_CHECKOUT_REG_LNAME]);
        dto.getScientificCheckout().setCheckoutDate((Date) row[SCIENTIFIC_CHECKOUT_DATE_IDX]);
        
    }
}

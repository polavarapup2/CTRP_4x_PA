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
package gov.nih.nci.accrual.service.util;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import gov.nih.nci.accrual.dto.util.AccrualCountsDto;
import gov.nih.nci.accrual.dto.util.SearchTrialCriteriaDto;
import gov.nih.nci.accrual.dto.util.SearchTrialResultDto;
import gov.nih.nci.accrual.service.SubjectAccrualServiceLocal;
import gov.nih.nci.accrual.service.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.accrual.util.AccrualServiceLocator;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.NonInterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;

/**
 * @author Hugh Reinhart
 * @since Aug 17, 2009
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SuppressWarnings({ "PMD.ExcessiveMethodLength", "PMD.ExcessiveClassLength", 
"PMD.NPathComplexity", "PMD.CyclomaticComplexity" })
public class SearchTrialBean implements SearchTrialService {
    
    private static final int SP_IDENTIFIER_IDX = 0;
    private static final int ORG_NAME_IDX = 1;
    private static final int SS_IDENTIFIER = 2;
    private static final int SP_TITLE_IDX = 3;
    private static final int SP_ID_IDX = 4;
    private static final int SOS_STATUS_IDX = 5;
    private static final int PERSON_IDX = 6;
    private static final int DIS_CODE_SYSTEM_IDX = 7;
    private static final String UNCHECKED = "unchecked"; 
    private static final String SPID = "spIds";
    private AccrualUtil accrualUtil = new AccrualUtil();
    
    /** The Constant DWF_QRY. */
    private static final String DWF_QRY = "select study_protocol_identifier, status_code from rv_dwf_current "
            + "where study_protocol_identifier in (:spIds)";
    
    /** The Constant NO_CTEP_DCP_TRIALID_SQRY_SITE. */
    public static final String NO_CTEP_DCP_TRIALID_SQRY_SITE =
    "select distinct(sp.identifier) from study_site_accrual_status ssas" 
    + " join study_site ss on ssas.study_site_identifier=ss.identifier"
    + " join study_protocol sp on ss.study_protocol_identifier=sp.identifier"
    + " join study_otheridentifiers so on sp.identifier=so.study_protocol_id"
    + " join study_resourcing sr on sr.study_protocol_identifier=sp.identifier"
    + " join healthcare_facility hcf on hcf.identifier=ss.healthcare_facility_identifier"
    + " join organization org on org.identifier= hcf.organization_identifier"
    + " where so.root='2.16.840.1.113883.3.26.4.3' and sp.status_code='ACTIVE'"
    + " and ss.functional_code = 'TREATING_SITE' and ssas.status_code <> 'IN_REVIEW' "
    + " and sr.summ_4_rept_indicator='true' and sr.type_code!='NATIONAL'"
    + " and ssas.identifier in (select identifier from study_site_accrual_status where "
    + " study_site_identifier  = ss.identifier and deleted=false"
    + " order by status_date desc, identifier desc limit 1)"
    + " and  org.assigned_identifier = (select "
    + " cast(affiliated_org_id as varchar)  from registry_user "
    + " where identifier = :userId  and site_accrual_submitter=true)"
    + " and( not exists (select study_protocol_identifier from rv_ctep_id where study_protocol_identifier"
    + " = sp.identifier and local_sp_indentifier is not null) and not exists "
    + " (select study_protocol_identifier from rv_dcp_id where study_protocol_identifier "
    + " =sp.identifier and local_sp_indentifier is not null))";

    /**
     * The constant LEAD_ORG_TRIALID_LIST
     */
    
    public static final String LEAD_ORG_TRIALID_LIST =
    "SELECT sp.identifier FROM study_protocol sp"
    + " INNER JOIN study_resourcing sr on sr.study_protocol_identifier=sp.identifier"
    + " INNER JOIN study_site ss  on ss.study_protocol_identifier=sp.identifier"
    + " INNER JOIN research_organization ro on ro.identifier= ss.research_organization_identifier"
    + " INNER JOIN organization org  on org.identifier = ro.organization_identifier"
    + " WHERE sp.status_code = 'ACTIVE' AND sp.proprietary_trial_indicator = false AND sr.summ_4_rept_indicator = true" 
    + " AND sr.type_code != 'NATIONAL' AND ss.functional_code = 'LEAD_ORGANIZATION'  "
    + "AND org.assigned_identifier IN (:orgIDS)";
    
    /**
     * The Constant NO_CTEP_DCP_TRIAL_ID
     */
    public static final String CTEP_DCP_TRIAL_ID =
    "SELECT DISTINCT(sp.identifier) FROM study_protocol sp"
    + " WHERE sp.status_code = 'ACTIVE' AND ( exists (select study_protocol_identifier from rv_ctep_id"
    + " where study_protocol_identifier=sp.identifier and local_sp_indentifier is not null) or  exists"
    + " (select study_protocol_identifier from rv_dcp_id where"
    + " study_protocol_identifier=sp.identifier and local_sp_indentifier is not null))  AND  sp.identifier in (:spIds)";
    /**
     * The constant TRIALID_LIST_FAMILY
     */
    public static final String TRIALID_LIST_FAMILY = 
     "select DISTINCT(ss.study_protocol_identifier) from study_site ss "
     + " join study_site_accrual_status ssas on (ssas.study_site_identifier = ss.identifier) "
     + " where ss.functional_code ='TREATING_SITE' and ss.study_protocol_identifier in (:spIds) "
     + " and ssas.status_code <> 'IN_REVIEW'"
     + " and ssas.identifier in (select identifier from study_site_accrual_status where "
     + " study_site_identifier  = ss.identifier and deleted=false"
     + " order by status_date desc, identifier desc limit 1)";
    
    /** The Constant LEAD_ORG_QRY_FAMILY_ORGS. */
    public static final String LEAD_ORG_QRY_FAMILY_ORGS = "select study_protocol_identifier "
            + "from rv_lead_organization where study_protocol_identifier in (:spIds)"
            + " and assigned_identifier in (:orgIDS)";
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    public List<SearchTrialResultDto> search(SearchTrialCriteriaDto criteria,  Ii authorizedUser) throws PAException {
        List<SearchTrialResultDto> result = new ArrayList<SearchTrialResultDto>();
        if (criteria != null && !ISOUtil.isIiNull(authorizedUser)) {
            try {
                Session session = PaHibernateUtil.getCurrentSession();
                String hql = generateStudyProtocolQuery(criteria);
                Query query = session.createQuery(hql);
                
                List<Long> queryList = query.list();
                Set<Long> authIds = getAuthorizedTrials(IiConverter.convertToLong(authorizedUser));
                Set<Long> siteSubmitterIds = getSiteAccrualSumbitterTrials(
                   IiConverter.convertToLong(authorizedUser));
                for (Long trialId : siteSubmitterIds) {
                    if (!authIds.contains(trialId)) {
                        authIds.add(trialId);
                    }
                }
                Collection<Long> searchIds = CollectionUtils.intersection(queryList, authIds);
                result = getTrialSummariesByStudyProtocolIdentifiers(searchIds);
                List<Long> searchIdList = new ArrayList<Long>(searchIds);
                Map<Long, Boolean> changeableList = PaServiceLocator.getInstance().
                        getAccrualDiseaseTerminologyService().canChangeCodeSystemForSpIds(searchIdList);
                for (SearchTrialResultDto dto : result) {
                    dto.setCanChangeDiseaseCodeSystem(BlConverter.convertToBl(
                            changeableList.get(IiConverter.convertToLong(dto.getIdentifier()))));
                }
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception in SearchTrialBean.search().", hbe);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public Bl isAuthorized(Ii studyProtocolIi, Ii authorizedUser) throws PAException {
        Bl returnValue = BlConverter.convertToBl(false);
        Set<Long> authIds = getAuthorizedTrials(IiConverter.convertToLong(authorizedUser));
        Set<Long> siteSubmitterIds = getSiteAccrualSumbitterTrials(IiConverter.convertToLong(authorizedUser));
        if (authIds.contains(IiConverter.convertToLong(studyProtocolIi)) 
          || siteSubmitterIds.contains(IiConverter.convertToLong(studyProtocolIi))) {
           returnValue = BlConverter.convertToBl(true);
        } 
        return returnValue;
    }

    /**
     * {@inheritDoc}
     */
    public SearchTrialResultDto getTrialSummaryByStudyProtocolIi(Ii studyProtocolIi) throws PAException {
        try {
            List<SearchTrialResultDto> results = getTrialSummariesByStudyProtocolIdentifiers(
                    Arrays.asList(IiConverter.convertToLong(studyProtocolIi)));
            if (results.isEmpty()) {
                throw new PAException("Trial not found in SearchTrialBean.getTrialSummaryByStudyProtocolIi().");
            }
            return results.get(0);
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in SearchTrialBean.getTrialSummaryByStudyProtocolIi().", hbe);
        }
    }
    
    @SuppressWarnings(UNCHECKED)
    private List<SearchTrialResultDto> getTrialSummariesByStudyProtocolIdentifiers(Collection<Long> identifiers) 
                            throws PAException {
        List<SearchTrialResultDto> results = new ArrayList<SearchTrialResultDto>();
        if (CollectionUtils.isEmpty(identifiers)) {
            return results;
        }
        Session session = PaHibernateUtil.getCurrentSession();
        String hql =
            " select oi.extension, org.name, ss.localStudyProtocolIdentifier, sp.officialTitle, "
            + "      sp, sos.statusCode, per, sp.accrualDiseaseCodeSystem "
            + "from StudyProtocol as sp "
            + "left outer join sp.studyOverallStatuses as sos "
            + "left outer join sp.studyContacts as sc "
            + "left outer join sc.clinicalResearchStaff as hcp "
            + "left outer join hcp.person as per "
            + "left outer join sp.studySites as ss  "
            + "left outer join ss.researchOrganization as ro "
            + "left outer join ro.organization as org "
            + "left outer join sp.otherIdentifiers as oi "
            + "where sp.id in (:studyProtocolIdentifiers) "
            + "  and (ss.functionalCode ='" + StudySiteFunctionalCode.LEAD_ORGANIZATION + "' "
            + "       or ss.functionalCode is null) "
            + "  and (sc.roleCode ='" + StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR + "' "
            + "       or sc.roleCode is null) "
            + "  and (sos.id in (select max(id) from StudyOverallStatus as sos1 "
            + "                where sos.studyProtocol = sos1.studyProtocol and sos1.deleted is not true and "
            + " sos1.statusDate = (select max(statusDate) from StudyOverallStatus as sos2 "
            + " where sos1.studyProtocol = sos2.studyProtocol and sos2.deleted is not true)) "
            + "       or sos.id is null)"
            + "  and (oi.root = '" + IiConverter.STUDY_PROTOCOL_ROOT + "' "
            + "       and oi.identifierName = '" + IiConverter.STUDY_PROTOCOL_IDENTIFIER_NAME + "') ";
        Query query = session.createQuery(hql);
        query.setParameterList("studyProtocolIdentifiers", identifiers);
        List<Object[]> queryList = query.list();
        SubjectAccrualServiceLocal subjectAccrualSer = AccrualServiceLocator.getInstance().getSubjectAccrualService();
        for (Object[] trialInfo : queryList) {
            results.add(convertToDto(trialInfo, subjectAccrualSer));
        }
        return results;
    }
    
    private SearchTrialResultDto convertToDto(Object[] obj, SubjectAccrualServiceLocal subjectAccrualSer) 
            throws PAException {
        SearchTrialResultDto trial = new SearchTrialResultDto();
        trial.setAssignedIdentifier(StConverter.convertToSt((String) obj[SP_IDENTIFIER_IDX]));
        trial.setLeadOrgName(StConverter.convertToSt((String) obj[ORG_NAME_IDX]));
        trial.setLeadOrgTrialIdentifier(StConverter.convertToSt((String) obj[SS_IDENTIFIER]));
        trial.setOfficialTitle(StConverter.convertToSt((String) obj[SP_TITLE_IDX]));
        StudyProtocol sp = (StudyProtocol) obj[SP_ID_IDX];
        trial.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(sp.getId()));
        trial.setStudyStatusCode(CdConverter.convertToCd((StudyStatusCode) obj[SOS_STATUS_IDX]));
        trial.setIdentifier(trial.getStudyProtocolIdentifier());
        trial.setIndustrial(BlConverter.convertToBl(sp.getProprietaryTrialIndicator()));
        Person person = (Person) obj[PERSON_IDX];
        trial.setPrincipalInvestigator(StConverter.convertToSt(person == null ? null : person.getFullName()));
        if (sp instanceof NonInterventionalStudyProtocol) {
            trial.setTrialType(StConverter.convertToSt(AccrualUtil.NONINTERVENTIONAL));
            Long patientAccruals = subjectAccrualSer.getAccrualCounts(true, sp.getId());
            Long summaryAccruals = subjectAccrualSer.getAccrualCounts(false, sp.getId());
            if (patientAccruals == 0 && summaryAccruals == 0) {
                trial.setAccrualSubmissionLevel(StConverter.convertToSt(AccrualUtil.BOTH));
            } else if (patientAccruals > 0) {
                trial.setAccrualSubmissionLevel(StConverter.convertToSt(AccrualUtil.SUBJECT_LEVEL));
            } else if (summaryAccruals > 0) {
                trial.setAccrualSubmissionLevel(StConverter.convertToSt(AccrualUtil.SUMMARY_LEVEL)); 
            }
        } else if (sp instanceof InterventionalStudyProtocol) {
            trial.setTrialType(StConverter.convertToSt(AccrualUtil.INTERVENTIONAL));
        }
        trial.setDiseaseCodeSystem(StConverter.convertToSt((String) obj[DIS_CODE_SYSTEM_IDX]));
        return trial;
    }

    @SuppressWarnings(UNCHECKED)
    private Set<Long> getAuthorizedTrials(Long registryUserId) {
        Set<Long> result = new HashSet<Long>();
        if (registryUserId != null) {
            Session session = PaHibernateUtil.getCurrentSession();
            String hql = "select distinct sp.id from StudySiteAccrualAccess ssaa join ssaa.studySite ss "
                       + "join ss.studyProtocol sp "
                       + "where ssaa.registryUser.id = :registryUserId and ssaa.statusCode = :statusCode";
            Query query = session.createQuery(hql);
            query.setParameter("registryUserId", registryUserId);
            query.setParameter("statusCode", ActiveInactiveCode.ACTIVE);
            List<Long> queryList = query.list();
            result.addAll(queryList);
        }
        return result;
    }
    
    @SuppressWarnings(UNCHECKED)
    private Set<Long> getSiteAccrualSumbitterTrials(Long registryUserId) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        RegistryUser ru = PaServiceLocator.getInstance().getRegistryUserService()
            .getUserById(registryUserId);
        Set<Long> finalTrialsWithoutCTEPOrDCPId = new HashSet<Long>();
        Set<Long> finalTrialsWithoutCTEPOrDCPIdFamily = new HashSet<Long>();
        if (ru.getSiteAccrualSubmitter()) {
            List<Long> noCtepDcpTrialIdsList = new ArrayList<Long>(); 
            SQLQuery query = session.createSQLQuery(NO_CTEP_DCP_TRIALID_SQRY_SITE);
            query.setParameter("userId", registryUserId);
            List<BigInteger> queryList = query.list();
            for (BigInteger obj : queryList) {
                Long studyProtocolId = obj.longValue();
                noCtepDcpTrialIdsList.add(studyProtocolId);
            }
            Map<Long, String> trialsWorkFlowStatus = new HashMap<Long, String>();
            SQLQuery qr = session.createSQLQuery(DWF_QRY);
            if (!noCtepDcpTrialIdsList.isEmpty()) {
                qr.setParameterList(SPID, noCtepDcpTrialIdsList);   
            } else {
                qr.setParameter(SPID, null,  Hibernate.LONG);  
            }
            List<Object[]> trialsDWFS = qr.list();
            for (Object[] row : trialsDWFS) {
                 Long studyId = ((BigInteger) row[0]).longValue();
                 String status = row[1].toString();
                 trialsWorkFlowStatus.put(studyId, status);
            }
            Set<Long> trialsWithoutCTEPOrDCPId = new HashSet<Long>();
            for (Long trialId : noCtepDcpTrialIdsList) {
               if (isEligibleForAccrual(trialId, trialsWorkFlowStatus)) {
                   trialsWithoutCTEPOrDCPId.add(trialId);
               }
            }
            // trialsWithoutCTEPOrDCPId contains user his/her affiliated organization*** participating on any trial 
            // Now check if these trial's lead organization is a member of his/her affiliated organization family.
            List<Long> values = accrualUtil.getAllFamilyOrgs(ru.getAffiliatedOrganizationId());
            
            if (values != null && !values.isEmpty()) { // PO-9267
            query = session.createSQLQuery(LEAD_ORG_QRY_FAMILY_ORGS);
            query.setParameterList("orgIDS", AccrualUtil.convertPoOrgIdsToStrings(values));
            if (!trialsWithoutCTEPOrDCPId.isEmpty()) {
                query.setParameterList(SPID, trialsWithoutCTEPOrDCPId);   
            } else {
                query.setParameter(SPID, null,  Hibernate.LONG);  
            }
            queryList = query.list();
            for (BigInteger obj : queryList) {
                Long studyProtocolId = obj.longValue();
                finalTrialsWithoutCTEPOrDCPId.add(studyProtocolId);
            }
          }
        }
        if (ru.getFamilyAccrualSubmitter()) {
            List<Long> values = accrualUtil.getAllFamilyOrgs(ru.getAffiliatedOrganizationId()); // step1
            //step2
            SQLQuery query = session.createSQLQuery(LEAD_ORG_TRIALID_LIST);
            query.setParameterList("orgIDS", AccrualUtil.convertPoOrgIdsToStrings(values));
            List<BigInteger> queryList = query.list();
            List<Long> leadOrgTrials = new ArrayList<Long>(); 
            for (BigInteger obj : queryList) {
                Long studyProtocolId = obj.longValue();
                leadOrgTrials.add(studyProtocolId);
            }
            //step3
            Map<Long, String> trialsWorkFlowStatus1 = new HashMap<Long, String>();
            SQLQuery qr = session.createSQLQuery(DWF_QRY);
            if (!leadOrgTrials.isEmpty()) {
                qr.setParameterList(SPID, leadOrgTrials);   
            } else {
                qr.setParameter(SPID, null,  Hibernate.LONG);  
            }
            List<Object[]> trialsDWFS1 = qr.list();
            for (Object[] row : trialsDWFS1) {
                 Long studyId = ((BigInteger) row[0]).longValue();
                 String status = row[1].toString();
                 trialsWorkFlowStatus1.put(studyId, status);
            }
            
            //step4
            List<Long> leadOrgTrialsNoCtepDcpID = new ArrayList<Long>(); 
            query = session.createSQLQuery(CTEP_DCP_TRIAL_ID);
            if (!leadOrgTrials.isEmpty()) {
                query.setParameterList(SPID, leadOrgTrials);   
            } else {
                query.setParameter(SPID, null,  Hibernate.LONG);  
            }
            List<BigInteger> list = query.list();
            List<Long> ctepDcpTrials = new ArrayList<Long>();
            for (BigInteger obj : list) {
                Long studyProtocolId = obj.longValue();
                ctepDcpTrials.add(studyProtocolId);
            }
            for (Long trialId : leadOrgTrials) {
                if (!ctepDcpTrials.contains(trialId)) {
                    leadOrgTrialsNoCtepDcpID.add(trialId);
                }
             }
            // step5
            List<Long> trialsWithoutCTEPOrDCPIdFamily = new ArrayList<Long>();
            for (Long trialId : leadOrgTrialsNoCtepDcpID) {
                if (isEligibleForAccrual(trialId, trialsWorkFlowStatus1)) {
                   trialsWithoutCTEPOrDCPIdFamily.add(trialId);
                }
             }
            //step6
            query = session.createSQLQuery(TRIALID_LIST_FAMILY);
            if (!trialsWithoutCTEPOrDCPIdFamily.isEmpty()) {
                query.setParameterList(SPID, trialsWithoutCTEPOrDCPIdFamily);   
            } else {
                query.setParameter(SPID, null,  Hibernate.LONG);  
            }
            List<BigInteger> lists = query.list();
            for (BigInteger obj : lists) {
                Long studyProtocolId = obj.longValue();
                finalTrialsWithoutCTEPOrDCPIdFamily.add(studyProtocolId);
            }

           for (Long trialId : finalTrialsWithoutCTEPOrDCPIdFamily) {
               if (!finalTrialsWithoutCTEPOrDCPId.contains(trialId)) {
                 finalTrialsWithoutCTEPOrDCPId.add(trialId);
                }
           }
        }
        return finalTrialsWithoutCTEPOrDCPId;
    }
    
    
    private boolean isEligibleForAccrual(Long trialId, Map<Long, String> trialsWorkFlowStatus)
          throws PAException {
        boolean result = false;
        if (trialsWorkFlowStatus.get(trialId) != null) {
             DocumentWorkflowStatusCode code = DocumentWorkflowStatusCode.valueOf(trialsWorkFlowStatus.get(trialId));
             result = code.isEligibleForAccrual();
        }
        return result;
    }
    
    private String generateStudyProtocolQuery(SearchTrialCriteriaDto criteria) throws PAException {
        StringBuffer hql = new StringBuffer();
        try {
            hql.append("select distinct sp.id from StudyProtocol as sp left outer join sp.studySites as sps ");
            hql.append(generateWhereClause(criteria));
        } catch (Exception e) {
            throw new PAException("Exception thrown in SearchTrialBean.generateStudyProtocolQuery().", e);
        }
        return hql.toString();
    }

    private String generateWhereClause(SearchTrialCriteriaDto criteria) {
        String assignedIdentifier = StConverter.convertToString(criteria.getAssignedIdentifier());
        String nctID = StConverter.convertToString(criteria.getLeadOrgTrialIdentifier());
        String officialTitle = StConverter.convertToString(criteria.getOfficialTitle());
        StringBuffer where = new StringBuffer();

        where.append("where sp.statusCode = '" + ActStatusCode.ACTIVE.name() + "' ");
        if (StringUtils.isNotEmpty(assignedIdentifier)) {
            where.append(" and exists (select oi.extension from sp.otherIdentifiers oi where oi.identifierName = '"
                    + IiConverter.STUDY_PROTOCOL_IDENTIFIER_NAME
                    + "' and upper(oi.extension) like '%"
                    + StringEscapeUtils.escapeSql(assignedIdentifier
                            .toUpperCase(Locale.US).trim()) + "%') ");

        }
        if (StringUtils.isNotEmpty(officialTitle)) {
            where.append(" and upper(sp.officialTitle)  like '%"
                    + officialTitle.toUpperCase(Locale.US).trim().replaceAll("'", "''")
                    + "%'");
        }
        if (StringUtils.isNotEmpty(nctID)) {
            where.append(" and exists (select ssdcp.id from StudySite ssdcp where " // NOPMD
                    + "ssdcp.studyProtocol.id = sp.id and lower(ssdcp.localStudyProtocolIdentifier) like '%"
                    + StringEscapeUtils.escapeSql(nctID // NOPMD
                            .toLowerCase().trim()) 
                    + "%' and ssdcp.functionalCode = '"
                    + StudySiteFunctionalCode.IDENTIFIER_ASSIGNER.name()
                    + "' and ssdcp.researchOrganization.organization.name='"
                    + PAConstants.CTGOV_ORG_NAME + "') ");

        }
        return where.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, String> getAuthorizedTrialMap(Long registryUserId) throws PAException {
        Map<Long, String> result = new HashMap<Long, String>();
        Set<Long> trials = getAuthorizedTrials(registryUserId);
        if (CollectionUtils.isNotEmpty(trials)) {
            Session session = PaHibernateUtil.getCurrentSession();
            String hql =
                    " select oi.extension, sp.id "
                            + "from StudyProtocol as sp "
                            + "left outer join sp.otherIdentifiers as oi "
                            + "where sp.id in (:studyProtocolIdentifiers) "
                            + "  and (oi.root = '" + IiConverter.STUDY_PROTOCOL_ROOT + "' "
                            + "       and oi.identifierName = '" + IiConverter.STUDY_PROTOCOL_IDENTIFIER_NAME + "') ";
            Query query = session.createQuery(hql);
            query.setParameterList("studyProtocolIdentifiers", trials);
            @SuppressWarnings(UNCHECKED)
            List<Object[]> queryList = query.list();
            for (Object[] trial : queryList) {
                result.put((Long) trial[1], (String) trial[0]);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    public List<AccrualCountsDto> getAccrualCountsForUser(RegistryUser ru) throws PAException {
        List<AccrualCountsDto> result = new ArrayList<AccrualCountsDto>();
        if (ru == null) {
            return result;
        }
        Long studyProtocolId = 0L;
        List<Long> ids = new ArrayList<Long>();
        String sql = "select distinct sp.identifier as spid, ss.identifier as ssid from study_protocol sp"
                + " join study_site ss on (ss.study_protocol_identifier = sp.identifier)"
                + " join study_site_accrual_access ssaa on (ss.identifier = ssaa.study_site_identifier)"
                + " join healthcare_facility hcf on (ss.healthcare_facility_identifier = hcf.identifier)"
                + " join organization org on (hcf.organization_identifier = org.identifier)"
                + " where sp.status_code = 'ACTIVE'" + "  and ss.functional_code = 'TREATING_SITE'"
                + "  and org.assigned_identifier ='" + ru.getAffiliatedOrganizationId() + "'"
                + "  and ssaa.registry_user_id =" + ru.getId() + " and ssaa.status_code = 'ACTIVE'"
                + " UNION"
                + " select distinct sp.identifier as spid, sstreating.identifier as ssid from study_protocol sp"
                + " join study_site sstreating on (sstreating.study_protocol_identifier = sp.identifier)"
                + " join study_site_accrual_access ssaa on (sstreating.identifier = ssaa.study_site_identifier)"
                + " join study_site sslead on (sslead.study_protocol_identifier = sp.identifier)"
                + " join research_organization ro on (sslead.research_organization_identifier = ro.identifier)"
                + " join organization org on (ro.organization_identifier = org.identifier)"
                + " where sp.status_code = 'ACTIVE'" + " and sstreating.functional_code = 'TREATING_SITE'"
                + " and sslead.functional_code = 'LEAD_ORGANIZATION'"
                + "  and ssaa.registry_user_id = " + ru.getId()                
                + "  and org.assigned_identifier ='" + ru.getAffiliatedOrganizationId() + "'"
                + " and ssaa.status_code = 'ACTIVE'"; 
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session.createSQLQuery(sql);
        List<Object[]> queryList = query.list();
        for (Object[] obj : queryList) {
            studyProtocolId = Long.valueOf(((Number) obj[0]).longValue());
            if (!ids.contains(studyProtocolId)) {
                ids.add(studyProtocolId);
            }
        }
        
        for (Long spId : ids) {
            SearchTrialResultDto dto = getTrialSummaryByStudyProtocolIi(IiConverter.convertToIi(spId));
            AccrualCountsDto ac = new AccrualCountsDto();
            ac.setNciNumber(StConverter.convertToString(dto.getAssignedIdentifier()));
            ac.setLeadOrgTrialIdentifier(StConverter.convertToString(dto.getLeadOrgTrialIdentifier()));
            ac.setNctNumber(getNCTNumber(spId));
            ac.setLeadOrgName(StConverter.convertToString(dto.getLeadOrgName()));
            setCounts(spId, ru.getAffiliatedOrganizationId(), session, dto, ac);
            result.add(ac);            
        }
    return result;    
    }

    @Override
    @SuppressWarnings(UNCHECKED)
    public void validate(Long studyProtocolId) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery("SELECT org.identifier FROM StudyProtocol sp JOIN sp.studySites ss "
                + "JOIN ss.healthCareFacility hf JOIN hf.organization org WHERE sp.id = :trialId "
                + "AND ss.functionalCode = :siteCode GROUP BY org.identifier HAVING count(*) > 1");
        query.setParameter("siteCode", StudySiteFunctionalCode.TREATING_SITE);
        query.setParameter("trialId", studyProtocolId);
        List<Object> queryList = query.list();
        if (!queryList.isEmpty()) {
            throw new PAException("Duplicate treating sites.");
        }
    }

    private void setCounts(Long studyProtocolId, Long affOrgId,
        Session session, SearchTrialResultDto dto, AccrualCountsDto ac) {
        if (dto.getIndustrial().getValue() && dto.getTrialType().getValue().equals(AccrualUtil.INTERVENTIONAL) 
                || !ISOUtil.isStNull(dto.getAccrualSubmissionLevel())
                && dto.getAccrualSubmissionLevel().getValue().equals(AccrualUtil.SUMMARY_LEVEL)) {
            Query sqlCount = session.createSQLQuery("select accrual_count from study_site_subject_accrual_count"
            + " where study_protocol_identifier = " + studyProtocolId + " and study_site_identifier in ( "
            + "select identifier from study_site where study_protocol_identifier = " + studyProtocolId
            + " and healthcare_facility_identifier in (select identifier from healthcare_facility "
            + "where organization_identifier in (select identifier from organization "
            + "where assigned_identifier = '" + affOrgId + "'))) ");
            String result = sqlCount.uniqueResult() != null ? sqlCount.uniqueResult().toString() : null;
            if (result != null) {
                ac.setAffiliateOrgCount(Long.valueOf(result));
            }
            sqlCount = session.createSQLQuery("select max(date_last_updated), sum(accrual_count) from "
            + "study_site_subject_accrual_count where study_protocol_identifier = " + studyProtocolId);
            setTrialCountAndMaxDate(ac, sqlCount);
        } else {
            Query sqlCount = session.createSQLQuery("select count(*) from study_subject where "
            + "study_protocol_identifier = " + studyProtocolId + " and study_site_identifier in ( "
            + "select identifier from study_site where study_protocol_identifier = " + studyProtocolId
            + " and healthcare_facility_identifier in (select identifier from healthcare_facility "
            + "where organization_identifier in (select identifier from organization "
            + "where assigned_identifier = '" + affOrgId + "'))) "
            + " and status_code <> 'NULLIFIED'");
            ac.setAffiliateOrgCount(Long.valueOf(sqlCount.uniqueResult().toString()));
            
            sqlCount = session.createSQLQuery("select max(date_last_updated), count(*) from study_subject "
            + "where study_protocol_identifier = " + studyProtocolId
            + " and status_code <> 'NULLIFIED'");
            setTrialCountAndMaxDate(ac, sqlCount);
        }
    }

    /**
     * @param ac AccrualCountsDto 
     * @param sqlCount query object
     */
    void setTrialCountAndMaxDate(AccrualCountsDto ac, Query sqlCount) {
            List<Object[]> qList =  sqlCount.list();
            for (Object[] row : qList) {
                ac.setDate(row[0] == null ? null : (java.sql.Timestamp) row[0]);
                ac.setTrialCount(row[1] == null ? null : Long.valueOf(((Number) row[1]).longValue()));
            }
    }

    private String getNCTNumber(Long studyProtocolId) throws PAException {
        String retIdentifier = "";
        StudySiteDTO identifierDto = new StudySiteDTO();
        identifierDto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(studyProtocolId));
        identifierDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
        String poOrgId = PaRegistry.getOrganizationCorrelationService()
                .getPOOrgIdentifierByIdentifierType(PAConstants.NCT_IDENTIFIER_TYPE);
        final Ii poOrgIi = IiConverter.convertToPoOrganizationIi(poOrgId);
        identifierDto.setResearchOrganizationIi(PaRegistry.getOrganizationCorrelationService()
            .getPoResearchOrganizationByEntityIdentifier(poOrgIi));
        LimitOffset pagingParams = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<StudySiteDTO> spDtos;
        try {
            spDtos = PaServiceLocator.getInstance().getStudySiteService().search(identifierDto, pagingParams);
        } catch (TooManyResultsException e) {
            throw new PAException(e);
        }

        StudySiteDTO spDto = PAUtil.getFirstObj(spDtos);
        if (spDto != null && !ISOUtil.isStNull(spDto.getLocalStudyProtocolIdentifier())) {
            retIdentifier = StConverter.convertToString(spDto.getLocalStudyProtocolIdentifier());
        }
        return retIdentifier;
    }
    /**
     * 
     * @param accrualUtil accrualUtil
     */
    public void setAccrualUtil(AccrualUtil accrualUtil) {
        this.accrualUtil = accrualUtil;
    }

    
}


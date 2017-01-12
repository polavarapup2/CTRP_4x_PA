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
*/
package gov.nih.nci.accrual.util;

import gov.nih.nci.accrual.service.MockPoOrganizationEntityService;
import gov.nih.nci.accrual.service.MockPoPersonEntityService;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Patient;
import gov.nih.nci.pa.domain.PerformedSubjectMilestone;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyDisease;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PatientEthnicityCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.enums.PatientRaceCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.StudySiteComparator;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Session;

/**
* @author Hugh Reinhart
* @since 08/07/2009
*/
public class TestSchema {
    public static List<AccrualDisease> diseases;
    public static List<StudyProtocol> studyProtocols;
    public static List<StudySite> studySites;
    public static List<StudyDisease> studyDiseases;
    public static List<Patient> patients;
    public static List<StudySubject> studySubjects;
    public static List<PerformedSubjectMilestone> performedSubjectMilestones;
    public static List<StudyOverallStatus> studyOverallStatuses;
    public static List<HealthCareFacility> healthCareFacilities;
    public static List<ResearchOrganization> researchOrganizations;
    public static List<Organization> organizations;
    public static List<StudySiteAccrualAccess> studySiteAccrualAccess;
    public static List<Country> countries;
    public static List<RegistryUser> registryUsers;
    public static List<StudySiteAccrualStatus> studySiteAccrualStatus;

    /**
     * @param <T> t
     * @param obj o
     */
    public static <T> void addUpdObject(T obj) {
        Session session = PaHibernateUtil.getCurrentSession();
        session.saveOrUpdate(obj);
        session.flush();
    }

    /**
     *
     * @param <T> t
     * @param oList o
     */
    public static <T> void addUpdObjects(List<T> oList) {
        for (T obj : oList) {
            addUpdObject(obj);
        }
    }

    public static void primeData() {
        diseases = new ArrayList<AccrualDisease>();
        studyProtocols = new ArrayList<StudyProtocol>();
        studyOverallStatuses = new ArrayList<StudyOverallStatus>();
        studySites = new ArrayList<StudySite>();
        patients = new ArrayList<Patient>();
        studySubjects = new ArrayList<StudySubject>();
        performedSubjectMilestones = new ArrayList<PerformedSubjectMilestone>();
        healthCareFacilities = new ArrayList<HealthCareFacility>();
        researchOrganizations = new ArrayList<ResearchOrganization>();
        organizations = new ArrayList<Organization>();
        studySiteAccrualAccess = new ArrayList<StudySiteAccrualAccess>();
        countries = new ArrayList<Country>();
        registryUsers = new ArrayList<RegistryUser>();
        studySiteAccrualStatus = new ArrayList<StudySiteAccrualStatus>();

        Country c =  new Country();
        c.setName("United States");
        c.setAlpha2("US");
        c.setAlpha3("USA");
        addUpdObject(c);
        countries.add(c);

        // Organization
        Organization org = new Organization();
        org.setCity("city");
        org.setCountryName("country name");
        org.setIdentifier("1");
        org.setName("orga name");
        org.setPostalCode("12345");
        org.setState("MD");
        org.setStatusCode(EntityStatusCode.ACTIVE);
        addUpdObject(org);
        organizations.add(org);

        org = new Organization();
        org.setCity("city2");
        org.setCountryName("country name2");
        org.setIdentifier("2");
        org.setName("orga name2");
        org.setPostalCode("22345");
        org.setState("MD");
        org.setStatusCode(EntityStatusCode.ACTIVE);
        addUpdObject(org);
        organizations.add(org);
        
        org = new Organization();
        org.setCity("city3");
        org.setCountryName("country name3");
        org.setIdentifier("3");
        org.setName("orga name3");
        org.setPostalCode("22345");
        org.setState("MD");
        org.setStatusCode(EntityStatusCode.ACTIVE);
        addUpdObject(org);
        organizations.add(org);

        // HealthcareFacility
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setIdentifier("po hcf id");
        hcf.setOrganization(organizations.get(0));
        hcf.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        hcf.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        addUpdObject(hcf);
        healthCareFacilities.add(hcf);

        hcf = new HealthCareFacility();
        hcf.setIdentifier("po hcf id1");
        hcf.setOrganization(organizations.get(1));
        hcf.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        hcf.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        addUpdObject(hcf);
        healthCareFacilities.add(hcf);
        
        hcf = new HealthCareFacility();
        hcf.setIdentifier("po hcf id2");
        hcf.setOrganization(organizations.get(2));
        hcf.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        hcf.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        addUpdObject(hcf);
        healthCareFacilities.add(hcf);
        
        ResearchOrganization ro = new ResearchOrganization();
        ro.setIdentifier("po ro id");
        ro.setOrganization(organizations.get(0));
        ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        ro.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        addUpdObject(ro);
        researchOrganizations.add(ro);
        

        // StudyProtocol
        StudyProtocol sp = new InterventionalStudyProtocol();
        sp.setOfficialTitle("Phase II study for Melanoma");
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(PAUtil.dateStringToTimestamp("1/1/2000"));
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2009"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);

        Set<Ii> studySecondaryIdentifiers =  new HashSet<Ii>();
        Ii assignedId = IiConverter.convertToAssignedIdentifierIi("NCI-2009-00001");
        studySecondaryIdentifiers.add(assignedId);

        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setProprietaryTrialIndicator(false);
        sp.setAccrualDiseaseCodeSystem("SDC");
        addUpdObject(sp);
        studyProtocols.add(sp);
        
        StudyResourcing sr = new StudyResourcing();
        sr.setTypeCode(SummaryFourFundingCategoryCode.NATIONAL);
        sr.setSummary4ReportedResourceIndicator(Boolean.TRUE);
        sr.setStudyProtocol(sp);
        addUpdObject(sr);
        
        sp = new StudyProtocol();
        sp.setOfficialTitle("A Phase II/III Randomized, Placebo-Controlled Double-Blind Clinical Trial of Ginger");
        dates = sp.getDates();
        dates.setStartDate(PAUtil.dateStringToTimestamp("1/1/2009"));
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2010"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);

        studySecondaryIdentifiers =  new HashSet<Ii>();
        assignedId = IiConverter.convertToAssignedIdentifierIi("NCI-2009-00002");
        studySecondaryIdentifiers.add(assignedId);

        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setStatusCode(ActStatusCode.INACTIVE);
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setProprietaryTrialIndicator(false);
        sp.setAccrualDiseaseCodeSystem("SDC");
        addUpdObject(sp);
        studyProtocols.add(sp);
        
        sr = new StudyResourcing();
        sr.setTypeCode(SummaryFourFundingCategoryCode.NATIONAL);
        sr.setSummary4ReportedResourceIndicator(Boolean.TRUE);
        sr.setStudyProtocol(sp);
        addUpdObject(sr);

        sp = new StudyProtocol();
        sp.setOfficialTitle("A Phase II/III Randomized, Placebo-Controlled Double-Blind Clinical Trial of Ginger");
        dates = sp.getDates();
        dates.setStartDate(PAUtil.dateStringToTimestamp("1/1/2009"));
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2010"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);

        studySecondaryIdentifiers =  new HashSet<Ii>();
        assignedId = IiConverter.convertToAssignedIdentifierIi("NCI-2010-00003");
        studySecondaryIdentifiers.add(assignedId);

        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setSubmissionNumber(Integer.valueOf(2));
        sp.setProprietaryTrialIndicator(false);
        sp.setAccrualDiseaseCodeSystem("SDC");
        addUpdObject(sp);

        sr = new StudyResourcing();
        sr.setTypeCode(SummaryFourFundingCategoryCode.INDUSTRIAL);
        sr.setSummary4ReportedResourceIndicator(Boolean.TRUE);
        sr.setStudyProtocol(sp);
        addUpdObject(sr);

        StudySite ssAccrualCount = new StudySite();
        ssAccrualCount.setLocalStudyProtocolIdentifier("T1 Local SP 001");
        ssAccrualCount.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ssAccrualCount.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        ssAccrualCount.setResearchOrganization(researchOrganizations.get(0));
        ssAccrualCount.setStudyProtocol(sp);
        addUpdObject(ssAccrualCount);
        
        List<StudyResourcing> srList = new ArrayList<StudyResourcing>();
        srList.add(sr);
        sp.setStudyResourcings(srList);
        addUpdObject(sp);
        studyProtocols.add(sp);

        sp = new StudyProtocol();
        sp.setOfficialTitle("Sample Preventative Study");
        dates = sp.getDates();
        dates.setStartDate(PAUtil.dateStringToTimestamp("1/1/2009"));
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2010"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.PREVENTION);
        studySecondaryIdentifiers =  new HashSet<Ii>();
        assignedId = IiConverter.convertToAssignedIdentifierIi("NCI-2009-00003");
        studySecondaryIdentifiers.add(assignedId);

        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setProprietaryTrialIndicator(false);
        sp.setAccrualDiseaseCodeSystem("SDC");
        addUpdObject(sp);
        studyProtocols.add(sp);
        
        sr = new StudyResourcing();
        sr.setTypeCode(SummaryFourFundingCategoryCode.NATIONAL);
        sr.setSummary4ReportedResourceIndicator(Boolean.TRUE);
        sr.setStudyProtocol(sp);
        addUpdObject(sr);
        
        // StudyOverallStatus
        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStatusCode(StudyStatusCode.APPROVED);
        sos.setStatusDate(PAUtil.dateStringToTimestamp("6/1/2009"));
        sos.setStudyProtocol(studyProtocols.get(0));
        addUpdObject(sos);
        studyOverallStatuses.add(sos);

        sos = new StudyOverallStatus();
        sos.setStatusCode(StudyStatusCode.ACTIVE);
        sos.setStatusDate(PAUtil.dateStringToTimestamp("6/15/2009"));
        sos.setStudyProtocol(studyProtocols.get(0));
        addUpdObject(sos);
        studyOverallStatuses.add(sos);

        // Disease
        AccrualDisease disease = new AccrualDisease();
        disease.setCodeSystem("SDC");
        disease.setDiseaseCode("code1");
        disease.setDisplayName("menu name");
        disease.setPreferredName("name");
        addUpdObject(disease);
        diseases.add(disease);

        // ICD9Disease
        disease = new AccrualDisease();
        disease.setCodeSystem("ICD9");
        disease.setDiseaseCode("code2");
        disease.setDisplayName("menu name");
        disease.setPreferredName("name");
        addUpdObject(disease);
        diseases.add(disease);

        // StudySite
        StudySite ss = new StudySite();
        ss.setLocalStudyProtocolIdentifier("T1 Local SP 001");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        ss.setResearchOrganization(researchOrganizations.get(0));
        ss.setStudyProtocol(studyProtocols.get(0));
        addUpdObject(ss);
        studySites.add(ss);

        ss = new StudySite();
        ss.setLocalStudyProtocolIdentifier("SWOG");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        ss.setHealthCareFacility(healthCareFacilities.get(0));
        ss.setStudyProtocol(studyProtocols.get(0));
        addUpdObject(ss);
        studySites.add(ss);

        ss = new StudySite();
        ss.setLocalStudyProtocolIdentifier("NCCTG");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        ss.setHealthCareFacility(healthCareFacilities.get(1));
        ss.setStudyProtocol(studyProtocols.get(0));
        addUpdObject(ss);
        studySites.add(ss);

        ss = new StudySite();
        ss.setLocalStudyProtocolIdentifier("T2 Local SP 001");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        ss.setResearchOrganization(researchOrganizations.get(0));
        ss.setStudyProtocol(studyProtocols.get(1));
        addUpdObject(ss);
        studySites.add(ss);

        ss = new StudySite();
        ss.setLocalStudyProtocolIdentifier("T2 Local SP 001");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        ss.setHealthCareFacility(healthCareFacilities.get(0));
        ss.setStudyProtocol(studyProtocols.get(1));
        addUpdObject(ss);
        studySites.add(ss);

        ss = new StudySite();
        ss.setLocalStudyProtocolIdentifier("T2 Local SP 001");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        ss.setResearchOrganization(researchOrganizations.get(0));
        ss.setStudyProtocol(studyProtocols.get(2));
        addUpdObject(ss);
        studySites.add(ss);

        ss = new StudySite();
        ss.setLocalStudyProtocolIdentifier("SWOG");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        ss.setHealthCareFacility(healthCareFacilities.get(0));
        ss.setStudyProtocol(studyProtocols.get(2));
        addUpdObject(ss);
        studySites.add(ss);
        
        ss = new StudySite();
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        ss.setHealthCareFacility(healthCareFacilities.get(0));
        ss.setStudyProtocol(studyProtocols.get(3));
        addUpdObject(ss);
        studySites.add(ss);
        
        ss = new StudySite();
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        ss.setHealthCareFacility(healthCareFacilities.get(1));
        ss.setStudyProtocol(studyProtocols.get(3));
        addUpdObject(ss);
        studySites.add(ss);
        
        //StudySiteAccrualStatus
        StudySiteAccrualStatus ssas = new StudySiteAccrualStatus();
        ssas.setStudySite(studySites.get(1));
        ssas.setStatusDate(new Timestamp(new Date().getTime()));
        ssas.setStatusCode(RecruitmentStatusCode.ACTIVE);
        addUpdObject(ssas);
        studySiteAccrualStatus.add(ssas);

        
        // StudySiteAccrualAccess
        RegistryUser registryUser = getRegistryUser();
        registryUsers.add(registryUser);
        
        StudySiteAccrualAccess ssaa = new StudySiteAccrualAccess();
        ssaa.setRegistryUser(registryUser);
        ssaa.setStudySite(studySites.get(1));
        ssaa.setStatusCode(ActiveInactiveCode.ACTIVE);
        ssaa.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        ssaa.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
        addUpdObject(ssaa);
        studySiteAccrualAccess.add(ssaa);

        ssaa = new StudySiteAccrualAccess();
        ssaa.setRegistryUser(registryUser);
        ssaa.setStudySite(studySites.get(2));
        ssaa.setStatusCode(ActiveInactiveCode.ACTIVE);
        ssaa.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        ssaa.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
        addUpdObject(ssaa);
        studySiteAccrualAccess.add(ssaa);

        ssaa = new StudySiteAccrualAccess();
        ssaa.setRegistryUser(registryUser);
        ssaa.setStudySite(studySites.get(4));
        ssaa.setStatusCode(ActiveInactiveCode.ACTIVE);
        ssaa.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        ssaa.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
        addUpdObject(ssaa);
        studySiteAccrualAccess.add(ssaa);
        
        ssaa = new StudySiteAccrualAccess();
        ssaa.setRegistryUser(registryUser);
        ssaa.setStudySite(studySites.get(6));
        ssaa.setStatusCode(ActiveInactiveCode.ACTIVE);
        ssaa.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        ssaa.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
        addUpdObject(ssaa);
        studySiteAccrualAccess.add(ssaa);
        
        ssaa = new StudySiteAccrualAccess();
        ssaa.setRegistryUser(registryUser);
        ssaa.setStudySite(studySites.get(7));
        ssaa.setStatusCode(ActiveInactiveCode.ACTIVE);
        ssaa.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        ssaa.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
        addUpdObject(ssaa);
        studySiteAccrualAccess.add(ssaa);
        
        ssaa = new StudySiteAccrualAccess();
        ssaa.setRegistryUser(registryUser);
        ssaa.setStudySite(studySites.get(8));
        ssaa.setStatusCode(ActiveInactiveCode.ACTIVE);
        ssaa.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        ssaa.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
        addUpdObject(ssaa);
        studySiteAccrualAccess.add(ssaa);
        
        registryUser = getRegistryUser();
        registryUsers.add(registryUser);
        
        ssaa = new StudySiteAccrualAccess();
        ssaa.setRegistryUser(registryUser);
        ssaa.setStudySite(studySites.get(1));
        ssaa.setStatusCode(ActiveInactiveCode.ACTIVE);
        ssaa.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        ssaa.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
        addUpdObject(ssaa);
        studySiteAccrualAccess.add(ssaa);
        //  to test PO-8644
        ssaa = new StudySiteAccrualAccess();
        ssaa.setRegistryUser(registryUsers.get(1));
        ssaa.setStudySite(studySites.get(1));
        ssaa.setStatusCode(ActiveInactiveCode.INACTIVE);
        ssaa.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        ssaa.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
        addUpdObject(ssaa);
        studySiteAccrualAccess.add(ssaa);
        
        // Patient
        Patient p = new Patient();
        p.setBirthDate(PAUtil.dateStringToTimestamp("7/1/1963"));
        p.setCountry(countries.get(0));
        p.setEthnicCode(PatientEthnicityCode.HISPANIC);
        p.setRaceCode(PatientRaceCode.AMERICAN_INDIAN.getName());
        p.setSexCode(PatientGenderCode.FEMALE);
        p.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        p.setZip("TX");
        addUpdObject(p);
        patients.add(p);

        p = new Patient();
        p.setBirthDate(PAUtil.dateStringToTimestamp("1/1/1963"));
        p.setCountry(countries.get(0));
        p.setEthnicCode(PatientEthnicityCode.NOT_HISPANIC);
        p.setRaceCode(PatientRaceCode.WHITE.getName());
        p.setSexCode(PatientGenderCode.MALE);
        p.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        p.setZip("TX");
        addUpdObject(p);
        patients.add(p);

        p = new Patient();
        p.setBirthDate(PAUtil.dateStringToTimestamp("8/1/1963"));
        p.setCountry(countries.get(0));
        p.setEthnicCode(PatientEthnicityCode.NOT_HISPANIC);
        p.setRaceCode(PatientRaceCode.WHITE.getName());
        p.setSexCode(PatientGenderCode.FEMALE);
        p.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        p.setZip("TX");
        addUpdObject(p);
        patients.add(p);

        p = new Patient();
        p.setBirthDate(PAUtil.dateStringToTimestamp("1/1/1960"));
        p.setCountry(countries.get(0));
        p.setEthnicCode(PatientEthnicityCode.NOT_REPORTED);
        p.setRaceCode(PatientRaceCode.NOT_REPORTED.getName());
        p.setSexCode(PatientGenderCode.MALE);
        p.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        p.setZip("TX");
        addUpdObject(p);
        patients.add(p);

        p = new Patient();
        p.setBirthDate(PAUtil.dateStringToTimestamp("9/1/1968"));
        p.setCountry(countries.get(0));
        p.setEthnicCode(PatientEthnicityCode.UNKNOWN);
        p.setRaceCode(PatientRaceCode.UNKNOWN.getName());
        p.setSexCode(PatientGenderCode.FEMALE);
        p.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        p.setZip("TX");
        addUpdObject(p);
        patients.add(p);

        // StudySubject
        StudySubject subj = new StudySubject();
        subj.setDisease(diseases.get(0));
        subj.setPatient(patients.get(0));
        subj.setAssignedIdentifier("001");
        subj.setPaymentMethodCode(PaymentMethodCode.MEDICARE);
        subj.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        subj.setStudyProtocol(studyProtocols.get(0));
        subj.setStudySite(studySites.get(1));
        subj.setSubmissionTypeCode(AccrualSubmissionTypeCode.UNKNOWN);
        subj.setDateLastCreated(PAUtil.dateStringToDateTime("1/1/2001"));
        addUpdObject(subj);
        studySubjects.add(subj);

        subj = new StudySubject();
        subj.setPatient(patients.get(1));
        subj.setAssignedIdentifier("002");
        subj.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        subj.setStudyProtocol(studyProtocols.get(0));
        subj.setStudySite(studySites.get(1));
        subj.setSubmissionTypeCode(AccrualSubmissionTypeCode.UNKNOWN);
        subj.setDateLastCreated(PAUtil.dateStringToDateTime("1/1/2002"));
        subj.setDateLastUpdated(PAUtil.dateStringToDateTime("1/1/2003"));
        addUpdObject(subj);
        studySubjects.add(subj);

        // PerformedSubjectMilestone
        PerformedSubjectMilestone m = new PerformedSubjectMilestone();
        m.setRegistrationDate(PAUtil.dateStringToTimestamp("5/21/2009"));
        m.setStudyProtocol(studyProtocols.get(0));
        m.setStudySubject(studySubjects.get(0));
        addUpdObject(m);
        performedSubjectMilestones.add(m);

        m = new PerformedSubjectMilestone();
        m.setRegistrationDate(PAUtil.dateStringToTimestamp("1/1/2000"));
        m.setStudyProtocol(studyProtocols.get(0));
        m.setStudySubject(studySubjects.get(1));
        addUpdObject(m);
        performedSubjectMilestones.add(m);

        RegistryUser ru = new RegistryUser();
        ru.setAddressLine("address");
        ru.setAffiliateOrg("affiliateOrg");
        ru.setCity("city");
        ru.setCountry(countries.get(0).getAlpha3());
        ru.setCsmUser(createUser());
        ru.setFirstName("fname");
        ru.setLastName("lname");
        ru.setPhone("1234567890");
        ru.setPoOrganizationId(IiConverter.convertToLong(MockPoOrganizationEntityService.orgDtoList.get(0).getIdentifier()));
        ru.setPoPersonId(IiConverter.convertToLong(MockPoPersonEntityService.personList.get(0).getIdentifier()));
        ru.setState("MD");
        addUpdObject(ru);
        
        RegulatoryAuthority ra = new RegulatoryAuthority();
        ra.setAuthorityName("Food and Drug Administration");
        ra.setCountry(c);
        addUpdObject(ra);
        
        ra = new RegulatoryAuthority();
        ra.setAuthorityName("CIA");
        ra.setCountry(c);
        addUpdObject(ra);

        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();
    }
    
    public static void clearUser() {
        CaseSensitiveUsernameHolder.setUser(null);
    }

    public static User createUser() {
        User user = new User();
        user.setLoginName("Abstractor: " + new Date());
        user.setFirstName("Joe");
        user.setLastName("Smith");
        user.setLoginName("curator");
        user.setUpdateDate(new Date());

        TestSchema.addUpdObject(user);
        CaseSensitiveUsernameHolder.setUser(user.getLoginName());
        return user;
    }
    
    private static RegistryUser getRegistryUser() {
        User user = createUser();
        RegistryUser ru = new RegistryUser();
        ru.setFirstName("Test");
        ru.setLastName("User");
        ru.setEmailAddress("test@example.com");
        ru.setPhone("123-456-7890");
        ru.setCsmUser(user);
        ru.setSiteAccrualSubmitter(true);
        ru.setAffiliatedOrganizationId(organizations.get(0).getId());
        TestSchema.addUpdObject(ru);
        return ru;
    }
    
    public static AnatomicSite createAnatomicSiteObj(String preferredName) {
        AnatomicSite create = new AnatomicSite();
        create.setCode(preferredName);
        create.setDisplayName("displayName");
        create.setCodingSystem("Summary 4 Anatomic Sites");
        create.setUserLastCreated(createUser());
        create.setDateLastCreated(new Timestamp(new Date().getTime()));
        create.setUserLastUpdated(createUser());
        create.setDateLastUpdated(new Timestamp(new Date().getTime()));
        return create;
    }
    
    
    public static StudyProtocol createStudyProtocol() {
        
        StudyProtocol sp = new StudyProtocol();
        sp.setOfficialTitle("Phase II study for Melanomaaa");
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(PAUtil.dateStringToTimestamp("1/1/2000"));
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2009"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);

        Set<Ii> studySecondaryIdentifiers =  new HashSet<Ii>();
        Ii assignedId = IiConverter.convertToAssignedIdentifierIi("NCI-2009-00005");
        assignedId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        studySecondaryIdentifiers.add(assignedId);

        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setProprietaryTrialIndicator(true);
        sp.setAccrualDiseaseCodeSystem("SDC");
        addUpdObject(sp);
        studyProtocols.add(sp);
        
        StudyResourcing sr = new StudyResourcing();
        sr.setTypeCode(SummaryFourFundingCategoryCode.EXTERNALLY_PEER_REVIEWED);
        sr.setSummary4ReportedResourceIndicator(Boolean.TRUE);
        sr.setStudyProtocol(sp);
        addUpdObject(sr);
        
        StudySite ss = new StudySite();
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        ss.setHealthCareFacility(healthCareFacilities.get(0));
        ss.setStudyProtocol(sp);
        addUpdObject(ss);
        studySites.add(ss);
        StudySite ss2 = new StudySite();
        ss2.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss2.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        ss2.setResearchOrganization(researchOrganizations.get(0));
        ss2.setStudyProtocol(sp);
        addUpdObject(ss2);
        studySites.add(ss2);

        StudySiteAccrualStatus ssas = new StudySiteAccrualStatus();
        ssas.setStudySite(ss);
        ssas.setStatusDate(new Timestamp(new Date().getTime()));
        ssas.setStatusCode(RecruitmentStatusCode.ACTIVE);
        addUpdObject(ssas);
        List<StudySiteAccrualStatus> ssass = new ArrayList<StudySiteAccrualStatus>();
        ssass.add(ssas);
        ss.setStudySiteAccrualStatuses(ssass);
        addUpdObject(ss);
        
        Set<StudySite> ssList = new TreeSet<StudySite>(new StudySiteComparator());
        ssList.add(ss);
        ssList.add(ss2);
        sp.setStudySites(ssList);
        List<StudyResourcing> studyResourcings = new ArrayList<StudyResourcing>();
        studyResourcings.add(sr);
        sp.setStudyResourcings(studyResourcings);
        addUpdObject(sp);
        return sp;
    }
}

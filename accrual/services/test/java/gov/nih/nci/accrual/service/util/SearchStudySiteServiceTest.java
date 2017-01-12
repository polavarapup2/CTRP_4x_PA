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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.dto.util.SearchStudySiteResultDto;
import gov.nih.nci.accrual.service.AbstractServiceTest;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.ServiceLocatorPaInterface;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.service.util.AccrualDiseaseTerminologyServiceRemote;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.StudySiteComparator;
import gov.nih.nci.pa.util.pomock.MockFamilyService;
import gov.nih.nci.pa.util.pomock.MockOrganizationEntityService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * @author Hugh Reinhart
 * @since Aug 25, 2009
 */
public class SearchStudySiteServiceTest extends AbstractServiceTest<SearchStudySiteService> {
    @Mock
    AccrualUtil acu;
    @Override
    @Before
    public void instantiateServiceBean() throws Exception {
        AccrualCsmUtil.setCsmUtil(new MockCsmUtil());
        bean = new SearchStudySiteBean();
        List<Long> values = new ArrayList<Long>();
        values.add(1L);
        acu = mock(AccrualUtil.class);
        when(acu.getAllFamilyOrgs(any(Long.class))).thenReturn(values);
        PoServiceLocator poServiceLocator = mock(PoServiceLocator.class);
        when(poServiceLocator.getOrganizationEntityService()).thenReturn(
                new MockOrganizationEntityService());
        when(poServiceLocator.getFamilyService())
                .thenReturn(new MockFamilyService());
       PoRegistry.getInstance().setPoServiceLocator(poServiceLocator);
    }

    @Test
    public void search() throws Exception {
        // first user can access all three sites
        Ii regUser1 = IiConverter.convertToIi(TestSchema.registryUsers.get(0).getId());

        // second user can only access 1 site
        Ii regUser2 = IiConverter.convertToIi(TestSchema.registryUsers.get(1).getId());
        ServiceLocatorPaInterface svcLocal = mock(ServiceLocatorPaInterface.class);
        RegistryUserServiceRemote registrySvr = mock(RegistryUserServiceRemote.class);
        RegistryUser ru = TestSchema.registryUsers.get(1);
        ru.setSiteAccrualSubmitter(false);
        ru.setFamilyAccrualSubmitter(false);
        when(registrySvr.getUserById(any(Long.class))).thenReturn(TestSchema.registryUsers.get(1));
        when(svcLocal.getRegistryUserService()).thenReturn(registrySvr);
        PaServiceLocator.getInstance().setServiceLocator(svcLocal);
        AccrualDiseaseTerminologyServiceRemote accrualDiseaseSvr = mock(AccrualDiseaseTerminologyServiceRemote.class);
        when(accrualDiseaseSvr.canChangeCodeSystemForSpIds(new ArrayList<Long>())).thenReturn(new HashMap<Long, Boolean>());
        when(svcLocal.getAccrualDiseaseTerminologyService()).thenReturn(accrualDiseaseSvr);
        PaServiceLocator.getInstance().setServiceLocator(svcLocal);
        
        // first trial has 2 accrual sites
        List<SearchStudySiteResultDto> rList = bean.search(
                IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()), regUser1);
        assertEquals(2, rList.size());
        // second user can only access one of them
        rList = bean.search(
                IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()), regUser2);
        assertEquals(1, rList.size());
        if (rList.size() > 0) {
            Ii id = rList.get(0).getStudySiteIi();
            assertNotNull(id);
            id = rList.get(0).getOrganizationIi();
            assertNotNull(id);
        }

        // second trial has 1 accrual site (first organization)
        rList = bean.search(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(1).getId()), regUser1);
        assertEquals(1, rList.size());
        assertEquals(TestSchema.organizations.get(0).getName(), StConverter.convertToString(rList.get(0).getOrganizationName()));
        // second user can't access it
        rList = bean.search(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(1).getId()), regUser2);
        assertEquals(0, rList.size());

        rList = bean.search(BII, BII);
        assertEquals(0, rList.size());
        
        Ii regUser3 = IiConverter.convertToIi(TestSchema.registryUsers.get(1).getId());
        ru = TestSchema.registryUsers.get(1);
        ru.setSiteAccrualSubmitter(true);
        ru.setFamilyAccrualSubmitter(true);
        when(registrySvr.getUserById(any(Long.class))).thenReturn(TestSchema.registryUsers.get(1));
        when(svcLocal.getRegistryUserService()).thenReturn(registrySvr);     
        rList = bean.search(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(1).getId()), regUser3);
        assertEquals(1, rList.size());

        
    }

    @Test
    public void studySiteExceptions() throws Exception {
        List<SearchStudySiteResultDto> list;
        Ii ii = new Ii();

        list = bean.search(null, null);
        assertNotNull(list);
        assertEquals(0, list.size());

        list = bean.search(null, ii);
        assertNotNull(list);
        assertEquals(0, list.size());

        list = bean.search(ii, null);
        assertNotNull(list);
        assertEquals(0, list.size());

        list = bean.search(ii, ii);
        assertNotNull(list);
        assertEquals(0, list.size());
    }
    
    @Test
    public void testGetStudySiteByOrg() throws Exception {
        Ii spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId());
        assertNotNull(bean.getStudySiteByOrg(spIi, IiConverter.convertToIi(TestSchema.organizations.get(0).getIdentifier())));
        assertNotNull(bean.getStudySiteByOrg(spIi, IiConverter.convertToIi(TestSchema.organizations.get(0).getIdentifier())));
        
        Ii otherSpIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(2).getId());
        assertNull(bean.getStudySiteByOrg(otherSpIi, IiConverter.convertToIi(TestSchema.organizations.get(1).getIdentifier())));
        assertNull(bean.getStudySiteByOrg(otherSpIi, IiConverter.convertToIi(TestSchema.organizations.get(1).getIdentifier())));
    }
    
    @Test
    public void testisStudySiteHasDCPId() throws Exception {        
    	StudyProtocol sp = new StudyProtocol();
        sp.setOfficialTitle("Test study");
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(PAUtil.dateStringToTimestamp("1/1/2012"));
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2012"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);

        Set<Ii> studySecondaryIdentifiers =  new HashSet<Ii>();
        Ii assignedId = IiConverter.convertToAssignedIdentifierIi("NCI-2012-00001");
        studySecondaryIdentifiers.add(assignedId);

        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setProprietaryTrialIndicator(false);
        sp.setAccrualDiseaseCodeSystem("SDC");
        TestSchema.addUpdObject(sp);
        
        Organization org = new Organization();
        org.setCity("city");
        org.setCountryName("country name");
        org.setName(PAConstants.DCP_ORG_NAME);
        org.setPostalCode("12345");
        org.setState("MD");
        org.setStatusCode(EntityStatusCode.ACTIVE);
        TestSchema.addUpdObject(org);
        
    	StudySite ss = new StudySite();
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
        ResearchOrganization ro = new ResearchOrganization();
        ro.setIdentifier("roid");
        ro.setOrganization(org);
        ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        TestSchema.addUpdObject(ro);
        ss.setResearchOrganization(ro);
        ss.setStudyProtocol(sp);
        TestSchema.addUpdObject(ss);
        Set<StudySite> studySites = new TreeSet<StudySite>(new StudySiteComparator());
        studySites.add(ss);
        sp.setStudySites(studySites);

        Ii spIi = IiConverter.convertToStudyProtocolIi(sp.getId());
        boolean result = bean.isStudyHasDCPId(spIi);
        assertTrue(result);
        
        Ii otherSpIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId());
        assertFalse(bean.isStudyHasDCPId(otherSpIi));
    }
    
    @Test
    public void testisStudyHasCTEPId() throws Exception {        
    	StudyProtocol sp = new StudyProtocol();
        sp.setOfficialTitle("Test CTEP study");
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(PAUtil.dateStringToTimestamp("9/3/2013"));
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("9/3/2013"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);

        Set<Ii> studySecondaryIdentifiers =  new HashSet<Ii>();
        Ii assignedId = IiConverter.convertToAssignedIdentifierIi("NCI-2013-00093");
        studySecondaryIdentifiers.add(assignedId);

        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setProprietaryTrialIndicator(false);
        sp.setAccrualDiseaseCodeSystem("SDC");
        TestSchema.addUpdObject(sp);
        
        Organization org = new Organization();
        org.setCity("city");
        org.setCountryName("country name");
        org.setName(PAConstants.CTEP_ORG_NAME);
        org.setPostalCode("12345");
        org.setState("MD");
        org.setStatusCode(EntityStatusCode.ACTIVE);
        TestSchema.addUpdObject(org);
        
    	StudySite ss = new StudySite();
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
        ResearchOrganization ro = new ResearchOrganization();
        ro.setIdentifier("roid");
        ro.setOrganization(org);
        ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        TestSchema.addUpdObject(ro);
        ss.setResearchOrganization(ro);
        ss.setStudyProtocol(sp);
        TestSchema.addUpdObject(ss);
        Set<StudySite> studySites = new TreeSet<StudySite>(new StudySiteComparator());
        studySites.add(ss);
        sp.setStudySites(studySites);

        Ii spIi = IiConverter.convertToStudyProtocolIi(sp.getId());
        boolean result = bean.isStudyHasCTEPId(spIi);
        assertTrue(result);
        
        Ii otherSpIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId());
        assertFalse(bean.isStudyHasCTEPId(otherSpIi));
    }

    @Test
    public void testGetTreatingSites() throws PAException {
        
    	StudyProtocol sp = new StudyProtocol();
        sp.setOfficialTitle("Test study2");
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(PAUtil.dateStringToTimestamp("1/1/2012"));
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2012"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);

        Set<Ii> studySecondaryIdentifiers =  new HashSet<Ii>();
        Ii assignedId = IiConverter.convertToAssignedIdentifierIi("NCI-2012-00001");
        studySecondaryIdentifiers.add(assignedId);

        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setProprietaryTrialIndicator(false);
        sp.setAccrualDiseaseCodeSystem("SDC");
        TestSchema.addUpdObject(sp);
        
    	StudySite ss = new StudySite();
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        ss.setHealthCareFacility(TestSchema.healthCareFacilities.get(0));
        ss.setStudyProtocol(sp);
        TestSchema.addUpdObject(ss);
        Set<StudySite> studySites = new TreeSet<StudySite>(new StudySiteComparator());
        studySites.add(ss);
        sp.setStudySites(studySites);
        
        StudySiteAccrualStatus ssas = new StudySiteAccrualStatus();
        ssas.setStudySite(ss);
        ssas.setStatusCode(RecruitmentStatusCode.ACTIVE);
        TestSchema.addUpdObject(ssas);
        
        StudySiteAccrualAccess ssaa = new StudySiteAccrualAccess();
        ssaa.setStudySite(ss);
        ssaa.setRegistryUser(TestSchema.registryUsers.get(0));
        ssaa.setStatusDateRangeLow(new Timestamp(new Date().getTime()));        
        TestSchema.addUpdObject(ssas);
        
        ServiceLocatorPaInterface paSvcLocator = mock(ServiceLocatorPaInterface.class);
        StudySiteAccrualStatusServiceRemote svc = mock(StudySiteAccrualStatusServiceRemote.class);
        when(paSvcLocator.getStudySiteAccrualStatusService()).thenReturn(svc);
        StudySiteAccrualStatusDTO dto = new StudySiteAccrualStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.ACTIVE));
        when(paSvcLocator.getStudySiteAccrualStatusService().getCurrentStudySiteAccrualStatusByStudySite(any(Ii.class))).thenReturn(dto);
        PaServiceLocator.getInstance().setServiceLocator(paSvcLocator);
        
        assertNotNull(bean.getTreatingSites(sp.getId()));
    }
}

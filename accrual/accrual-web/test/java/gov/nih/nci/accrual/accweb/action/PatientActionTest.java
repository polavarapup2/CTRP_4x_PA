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

package gov.nih.nci.accrual.accweb.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.accweb.dto.util.DiseaseWebDTO;
import gov.nih.nci.accrual.accweb.dto.util.PatientWebDto;
import gov.nih.nci.accrual.accweb.dto.util.SearchPatientsCriteriaWebDto;
import gov.nih.nci.accrual.accweb.dto.util.SearchStudySiteResultWebDto;
import gov.nih.nci.accrual.accweb.util.AccrualConstants;
import gov.nih.nci.accrual.accweb.util.MockPaServiceLocator;
import gov.nih.nci.accrual.accweb.util.MockSearchTrialBean;
import gov.nih.nci.accrual.accweb.util.MockServiceLocator;
import gov.nih.nci.accrual.accweb.util.MockStudySubjectBean;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PatientEthnicityCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.enums.PatientRaceCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Hugh Reinhart
 * @since Sep 26, 2009
 */
public class PatientActionTest extends AbstractAccrualActionTest {
    PatientAction action;
    SearchPatientsCriteriaWebDto criteria;
    List<SearchStudySiteResultWebDto> listOfStudySites;
    List<PatientWebDto> listOfPatients;
    PatientWebDto patient;

    @Before
    public void initAction() throws Exception {
        when(MockPaServiceLocator.accrualDiseaseTermSvc.getCodeSystem(1L)).thenReturn("SDC");
        action = new PatientAction();
        action.setStudyProtocolId(1L);
        action.setUnitedStatesId(1L);
        action.prepare();
        when(action.getLookupTableSvc().getPropertyValue("subject.delete.reasons")).thenReturn("Incorrect Study, Test1, Test2, Test3, Test4");
        criteria = new SearchPatientsCriteriaWebDto();
        patient = new PatientWebDto();
        listOfPatients = new ArrayList<PatientWebDto>();
        listOfStudySites = new ArrayList<SearchStudySiteResultWebDto>();
        action.setSpIi(IiConverter.convertToStudyProtocolIi(1L));
    }
    
    @Test
    public void prepare() throws Exception {
        setupMockHttpSession();
        
        action = new PatientAction();
        action.setUnitedStatesId(1L);        
        action.setStudyProtocolId(null);
        action.prepare();
        assertNull(ServletActionContext.getRequest().getSession().getAttribute("trialSummary"));
        action.setStudyProtocolId(1L);
        action.prepare();
        assertNotNull(ServletActionContext.getRequest().getSession().getAttribute("trialSummary"));
        List<String> codes = new ArrayList<String>();
        codes.add("SDC");
        codes.add("ICD9");
        codes.add("ICD-O-3");
        when(MockPaServiceLocator.accrualDiseaseTermSvc.getValidCodeSystems()).thenReturn(codes);
        action.prepare();
        assertTrue(action.isShowSite());
        assertFalse(action.hasActionErrors());

        MockSearchTrialBean.exception = true;
        action.prepare();
        assertTrue(action.hasActionErrors());
    }
    
    @Test
    public void executeIndustrialTrial() {
        setupMockHttpSession();

        action = new PatientAction();
        action.setUnitedStatesId(1L);
        action.setStudyProtocolId(3L);
        action.prepare();
        assertEquals("invalid", action.execute());
    }

    @Test
    public void executeNonInterventionalTrial() throws PAException {
		action = new PatientAction();
        action.setUnitedStatesId(1L);
        action.setStudyProtocolId(2L);
        action.prepare();
        assertEquals("success", action.execute());
        action.setStudyProtocolId(4L);
        action.prepare();
        assertEquals("invalid", action.execute());
	}

    @Override
    @Test
    public void executeTest() throws PAException {
    	RegistryUser ru = new RegistryUser();
        ru.setId(1L);
        User csmUser = new User();
        csmUser.setLoginName(AbstractAccrualActionTest.TEST_USER);
        ru.setCsmUser(csmUser);
        when(PaServiceLocator.getInstance().getRegistryUserService().getUser(any(String.class))).thenReturn(ru);
        assertEquals(ActionSupport.SUCCESS, action.execute());
        assertNotNull(ServletActionContext.getRequest().getSession().getAttribute("registryUserWebDTO"));
    }

    @Override
    @Test
    public void createTest() {
       assertEquals(AccrualConstants.AR_DETAIL, action.create());
    }

    @Override
    @Test
    public void retrieveTest() {
        action.retrieve();
        assertTrue(action.hasActionErrors());
        action.setSelectedRowIdentifier("1");
        assertEquals(AccrualConstants.AR_DETAIL, action.retrieve());
    }

    @Override
    @Test
     public void updateTest() {
        action.update();
        assertTrue(action.hasActionErrors());
        action.setSelectedRowIdentifier("1");
        assertEquals(AccrualConstants.AR_DETAIL, action.update());
    }

    @Override
    @Test
    public void deleteTest() throws Exception {
        action.setSelectedRowIdentifier("1");        
        action.setDeleteReason("Incorrect Study");
        assertEquals(ActionSupport.SUCCESS,action.delete());
    }
    
    @Test
    public void deleteTestDeleteInvoke() throws Exception {              
        action.setSelectedRowIdentifier("1");        
        action.setDeleteReason("Incorrect Study");
        action.delete();               
        verify(action.getSubjectAccrualSvc()).deleteSubjectAccrual(IiConverter.convertToIi("1"), action.getDeleteReason());      
    }

    @Override
    @Test
    public void addTest() throws Exception {
        patient.setStudyProtocolId(1L);
        action.setPatient(patient);
        assertEquals(AccrualConstants.AR_DETAIL, action.add());
        patient.setBirthDate("7/16/2009");
        patient.setCountryIdentifier(Long.valueOf(101));
        patient.setEthnicCode(PatientEthnicityCode.NOT_HISPANIC.getCode());
        patient.setGenderCode(PatientGenderCode.FEMALE.getCode());
        Set<String> raceCode = new HashSet<String>();
        raceCode.add(PatientRaceCode.WHITE.getName());
        patient.setRaceCode(raceCode);
        patient.setStatusCode(ActStatusCode.ACTIVE.getCode());
        patient.setAssignedIdentifier("PO PATIENT ID 01");
        patient.setStudySiteId(Long.valueOf("01"));
        patient.setDiseaseIdentifier(Long.valueOf("1"));
        patient.setRegistrationDate("12/10/2009");
        patient.setStudyProtocolId(1L);
        patient.setDateLastUpdated("12/10/2009");
        action.setPatient(patient);
        assertEquals(ActionSupport.SUCCESS, action.add());
        action.setStudyProtocolId(MockSearchTrialBean.NONINTERVENTIONAL_STUDY_PROTOCOL_ID);
    	patient.setStudyProtocolId(MockSearchTrialBean.NONINTERVENTIONAL_STUDY_PROTOCOL_ID);
    	patient.setAssignedIdentifier("TestNonInt01");        
        action.setPatient(patient);
        action.prepare();
        assertEquals(ActionSupport.SUCCESS, action.add());
    }

    @Test
    public void addDuplicate() throws Exception {        
        patient.setStudyProtocolId(1L);
        action.setPatient(patient);
        assertEquals(AccrualConstants.AR_DETAIL, action.add());
        patient.setBirthDate("7/16/2009");
        patient.setCountryIdentifier(Long.valueOf(101));
        patient.setEthnicCode(PatientEthnicityCode.NOT_HISPANIC.getCode());
        patient.setGenderCode(PatientGenderCode.FEMALE.getCode());
        Set<String> raceCode = new HashSet<String>();
        raceCode.add(PatientRaceCode.WHITE.getName());
        raceCode.add(PatientRaceCode.NOT_REPORTED.getName());
        patient.setRaceCode(raceCode);
        patient.setStatusCode(ActStatusCode.ACTIVE.getCode());
        patient.setAssignedIdentifier("PO PATIENT ID 01");
        patient.setStudySiteId(Long.valueOf("01"));
        patient.setDiseaseIdentifier(Long.valueOf("1"));
        patient.setRegistrationDate("12/10/2009");
        patient.setStudyProtocolId(1L);
        action.setPatient(patient);
        assertEquals(ActionSupport.SUCCESS, action.add());
        patient.setSiteDiseaseIdentifier(Long.valueOf("10"));
        assertEquals(AccrualConstants.AR_DETAIL, action.add());
        assertTrue(action.hasActionErrors());
        assertTrue(action.getActionErrors().contains("Unable to save because Site and Disease terminologies do not match."));
        assertNull(action.getPatient().getSiteDiseaseIdentifier());
        assertNull(action.getPatient().getSiteDiseasePreferredName());
        patient.setDiseaseIdentifier(Long.valueOf("20"));
        patient.setSiteDiseaseIdentifier(Long.valueOf("100"));
        assertEquals(AccrualConstants.AR_DETAIL, action.add());
        patient.setIdentifier(null);
        assertEquals(AccrualConstants.AR_DETAIL, action.add());
        assertTrue(action.hasActionErrors());
        assertTrue(action.getActionErrors().contains("This Study Subject Id (PO PATIENT ID 01) has already been added to this site."));
    }

    @Test
    public void addNullified() throws Exception {
        MockStudySubjectBean.ssList.get(0).setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.NULLIFIED));
        action.setPatient(patient);
        patient.setBirthDate("7/16/2009");
        patient.setCountryIdentifier(Long.valueOf(101));
        patient.setEthnicCode(PatientEthnicityCode.NOT_HISPANIC.getCode());
        patient.setGenderCode(PatientGenderCode.FEMALE.getCode());
        Set<String> raceCode = new HashSet<String>();
        raceCode.add(PatientRaceCode.WHITE.getName());
        patient.setRaceCode(raceCode);
        patient.setStatusCode(ActStatusCode.ACTIVE.getCode());
        patient.setAssignedIdentifier(StConverter.convertToString(MockStudySubjectBean.ssList.get(0).getAssignedIdentifier()));
        patient.setStudySiteId(Long.valueOf("01"));
        patient.setDiseaseIdentifier(Long.valueOf("1"));
        patient.setRegistrationDate("12/10/2009");
        patient.setStudyProtocolId(1L);
        action.setPatient(patient);
        assertEquals(ActionSupport.SUCCESS, action.add());
    }

    @Override
    @Test
    public void editTest() throws Exception {
        Long currentSiteId = IiConverter.convertToLong(MockStudySubjectBean.ssList.get(0).getStudySiteIdentifier());
        String currentAssignedIdentifier = StConverter.convertToString(MockStudySubjectBean.ssList.get(0).getAssignedIdentifier());
        patient.setStudyProtocolId(1L);
        action.setPatient(patient);
        assertEquals(AccrualConstants.AR_DETAIL, action.edit());
        patient.setBirthDate("7/16/2009");
        patient.setCountryIdentifier(Long.valueOf(101));
        patient.setEthnicCode(PatientEthnicityCode.NOT_HISPANIC.getCode());
        patient.setGenderCode(PatientGenderCode.FEMALE.getCode());
        Set<String> raceCode = new HashSet<String>();
        raceCode.add(PatientRaceCode.WHITE.getName());
        raceCode.add(PatientRaceCode.UNKNOWN.getName());
        patient.setRaceCode(raceCode);
        patient.setStatusCode(ActStatusCode.ACTIVE.getCode());
        patient.setAssignedIdentifier(currentAssignedIdentifier);
        patient.setStudySiteId(currentSiteId);
        patient.setDiseaseIdentifier(Long.valueOf("1"));
        patient.setStudySubjectId(1L);
        patient.setPatientId(1L);
        patient.setRegistrationDate("12/10/2009");
        action.setPatient(patient);
        // no key change
        assertEquals("success", action.edit());
        assertNotNull(action.getPatient());
        // change site
        patient.setStudySiteId(-1L);
        assertEquals("success", action.edit());
        assertNotNull(action.getPatient());
        // change study subject ID
        patient.setStudySiteId(currentSiteId);
        patient.setAssignedIdentifier("xyzzy");
        assertEquals("success", action.edit());
        assertNotNull(action.getPatient());
        // change both
        patient.setStudySiteId(-1L);
        assertEquals("success", action.edit());
        assertNotNull(action.getPatient());
    }

    @Test
    public void addExceptionTest() throws Exception {
    	patient.setStudyProtocolId(1L);
        action.setPatient(patient);
        assertEquals(AccrualConstants.AR_DETAIL, action.edit());
        patient.setStudyProtocolId(1L);
        patient.setBirthDate("7/16/2009");
        patient.setEthnicCode(PatientEthnicityCode.NOT_HISPANIC.getCode());
        patient.setGenderCode(PatientGenderCode.MALE.getCode());
        Set<String> raceCode = new HashSet<String>();
        raceCode.add(PatientRaceCode.WHITE.getName());
        patient.setRaceCode(raceCode);
        patient.setCountryIdentifier(Long.valueOf(2));
        patient.setStatusCode(ActStatusCode.ACTIVE.getCode());
        patient.setAssignedIdentifier("PO PATIENT ID 01");
        patient.setStudySiteId(Long.valueOf("01"));
        patient.setDiseaseIdentifier(Long.valueOf("1"));
        patient.setStudySubjectId(1L);
        patient.setZip("12345");
        patient.setPaymentMethodCode("paymentMethodCode");
        action.setPatient(patient);
        assertEquals("detail", action.add());
    }

    @Test
    public void displayDiseaseTest() throws Exception {
        try{
            action.getDisplayDisease();
        }catch(Exception e){
            //expected
        }
        ((MockHttpServletRequest) ServletActionContext.getRequest()).setupAddParameter("diseaseId", "1");
        assertEquals("displayDiseases", action.getDisplayDisease());
        ((MockHttpServletRequest) ServletActionContext.getRequest()).setupAddParameter("siteLookUp", "true");
        assertEquals("displaySiteDiseases", action.getDisplayDisease());
    }

    @Test
    public void criteriaTest() throws Exception {
        criteria.setAssignedIdentifier("PO PATIENT ID 01");
        criteria.setStudySiteId(Long.valueOf("02"));
        criteria.setBirthDate("7/16/2009");
        action.setCriteria(criteria);
        assertEquals(ActionSupport.SUCCESS, action.execute());
    }
    
    @Test
    public void birthAfterRegistrationDate() throws Exception {
        patient.setCountryIdentifier(Long.valueOf(101));
        patient.setEthnicCode(PatientEthnicityCode.NOT_HISPANIC.getCode());
        patient.setGenderCode(PatientGenderCode.FEMALE.getCode());
        Set<String> raceCode = new HashSet<String>();
        raceCode.add(PatientRaceCode.WHITE.getName());
        patient.setRaceCode(raceCode);
        patient.setStatusCode(ActStatusCode.ACTIVE.getCode());
        patient.setAssignedIdentifier("PO PATIENT ID 01");
        patient.setStudySiteId(Long.valueOf("01"));
        patient.setDiseaseIdentifier(Long.valueOf("1"));
        patient.setRegistrationDate("1/1/2011");
        patient.setBirthDate("2/1/2011");
        patient.setStudyProtocolId(1L);
        action.setPatient(patient);
        assertEquals("detail", action.add());
        assertTrue(action.hasActionErrors());
    }

    @Test
    public void setPatientDisease() {
        DiseaseWebDTO webDTO = new DiseaseWebDTO();        
        webDTO.setDiseaseIdentifier("1");
        webDTO.setPreferredName("preferredName");
        
        action.setPatientDisease(webDTO, "false");
        
        assertEquals(Long.valueOf(1), action.getPatient().getDiseaseIdentifier());
        assertEquals("preferredName", action.getPatient().getDiseasePreferredName());
        
        action.setPatientDisease(webDTO, "true");
        
        assertEquals(Long.valueOf(1), action.getPatient().getSiteDiseaseIdentifier());
        assertEquals("preferredName", action.getPatient().getSiteDiseasePreferredName());
    }

    @Test
    public void testCreateWithEmptydata() throws Exception {
        when(MockServiceLocator.diseaseService.diseaseCodeMandatory(anyLong())).thenReturn(true);
        action.setPatient(patient);
        assertEquals(AccrualConstants.AR_DETAIL, action.add());
        assertTrue(action.hasActionErrors());
        assertEquals(9, action.getActionErrors().size());
        assertTrue(StringUtils.contains(Arrays.toString(action.getActionErrors().toArray()), 
        		"Study Subject ID is required., Birth date is required., Gender is required., Race is required., " +
        		"Ethnicity is required., Country is required., Disease is required., Participating Site is required., " +
        		"Registration Date is required."));
        when(MockServiceLocator.diseaseService.diseaseCodeMandatory(anyLong())).thenReturn(false);
        action.setPatient(patient);
        assertEquals(AccrualConstants.AR_DETAIL, action.add());
        assertTrue(action.hasActionErrors());
        assertEquals(8, action.getActionErrors().size());
        assertTrue(StringUtils.contains(Arrays.toString(action.getActionErrors().toArray()), 
        		"Study Subject ID is required., Birth date is required., Gender is required., Race is required., " +
        		"Ethnicity is required., Country is required., Participating Site is required., " +
        		"Registration Date is required.")); 
        action.setPatient(patient);
        assertEquals(AccrualConstants.AR_DETAIL, action.edit());
        assertTrue(action.hasActionErrors());
        assertEquals(8, action.getActionErrors().size());
        assertTrue(StringUtils.contains(Arrays.toString(action.getActionErrors().toArray()), 
        		"Study Subject ID is required., Birth date is required., Gender is required., Race is required., " +
        		"Ethnicity is required., Country is required., Participating Site is required., " +
        		"Registration Date is required."));
    }
    
    @Test
    public void validateUnitedStatesTest() throws Exception {
        patient.setStudyProtocolId(1L);
        action.setPatient(patient);
        assertEquals(AccrualConstants.AR_DETAIL, action.add());
        patient.setBirthDate("7/16/2009");
        patient.setCountryIdentifier(Long.valueOf(1));
        patient.setEthnicCode(PatientEthnicityCode.NOT_HISPANIC.getCode());
        patient.setGenderCode(PatientGenderCode.FEMALE.getCode());
        Set<String> raceCode = new HashSet<String>();
        raceCode.add(PatientRaceCode.WHITE.getName());
        patient.setRaceCode(raceCode);
        patient.setStatusCode(ActStatusCode.ACTIVE.getCode());
        patient.setAssignedIdentifier("PO PATIENT ID 01");
        patient.setStudySiteId(Long.valueOf("01"));
        patient.setDiseaseIdentifier(Long.valueOf("1"));
        patient.setRegistrationDate("12/10/2009");
        patient.setStudyProtocolId(1L);
        patient.setDateLastUpdated("12/10/2009");
        action.setPatient(patient);
        assertEquals(AccrualConstants.AR_DETAIL, action.add());
        assertTrue(action.hasActionErrors());
        assertEquals(1, action.getActionErrors().size());
        assertTrue(StringUtils.contains(Arrays.toString(action.getActionErrors().toArray()), 
        		"Zip code is mandatory if country is United States."));

        patient.setCountryIdentifier(Long.valueOf(101));
        patient.setZip("123456");
        patient.setPaymentMethodCode("paymentMethodCode");
        action.setPatient(patient);
        assertEquals(AccrualConstants.AR_DETAIL, action.add());
        assertTrue(action.hasActionErrors());
        assertEquals(2, action.getActionErrors().size());
        assertTrue(StringUtils.contains(Arrays.toString(action.getActionErrors().toArray()), 
        		"Zip code should only be entered if country is United States., " + 
                "Method of payment should only be entered if country is United States."));
        
    }
    
    @Test
    public void getDeleteReasonsTest() throws Exception {
        assertEquals("deleteReason", action.getDeleteReasons());
    }
    
    @Test
    public void testGetDeleteReasonsList() throws Exception {
        action.getDeleteReasons();
        assertEquals(5, action.getReasonsList().size());
    }
    
    @Test
    public void testICDO3Validation() throws Exception {
    	List<String> codes = new ArrayList<String>();
        codes.add("SDC");
        codes.add("ICD9");
        codes.add("ICD-O-3");
        when(MockPaServiceLocator.accrualDiseaseTermSvc.getValidCodeSystems()).thenReturn(codes);
        when(action.getDiseaseSvc().diseaseCodeMandatory(any(Long.class))).thenReturn(true);
    	patient.setCountryIdentifier(Long.valueOf(101));
        patient.setZip("123456");
        patient.setPaymentMethodCode("paymentMethodCode");
        action.setPatient(patient);
        assertEquals(AccrualConstants.AR_DETAIL, action.add());
        assertNull(patient.getUserCreated());
        assertTrue(action.hasActionErrors());
        assertEquals(8, action.getActionErrors().size());
        assertTrue(StringUtils.contains(Arrays.toString(action.getActionErrors().toArray()), 
        		"Study Subject ID is required., Birth date is required., Gender is required., Race is required., Ethnicity is required., Disease is required., Participating Site is required., Registration Date is required."));
    }
}

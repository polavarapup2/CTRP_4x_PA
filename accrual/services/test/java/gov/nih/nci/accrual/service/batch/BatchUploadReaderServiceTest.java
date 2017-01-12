/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The accrual
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This accrual Software License (the License) is between NCI and You. You (or
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
 * its rights in the accrual Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the accrual Software; (ii) distribute and
 * have distributed to and by third parties the accrual Software and any
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
package gov.nih.nci.accrual.service.batch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.dto.util.SearchTrialResultDto;
import gov.nih.nci.accrual.service.util.SearchStudySiteBean;
import gov.nih.nci.accrual.service.util.SearchStudySiteService;
import gov.nih.nci.accrual.service.util.SearchTrialService;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.PoServiceLocator;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualCollections;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.BatchFile;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.NonInterventionalStudyProtocol;
import gov.nih.nci.pa.domain.PatientStage;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.AccrualChangeCode;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.noniso.dto.AccrualOutOfScopeTrialDTO;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceRemote;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author vrushali
 */
public class BatchUploadReaderServiceTest extends AbstractBatchUploadReaderTest {
    
   
	
	@Test
	public void testNoninterventionalTrialPatientLevelBatchupload() throws Exception {
		StudyProtocolServiceRemote spSvc = mock(StudyProtocolServiceRemote.class);
        when(spSvc.loadStudyProtocol(any(Ii.class))).thenAnswer(new Answer<StudyProtocolDTO>() {
        	public StudyProtocolDTO answer(InvocationOnMock invocation) throws Throwable {
        		StudyProtocolDTO dto = new StudyProtocolDTO();
        		dto.setProprietaryTrialIndicator(BlConverter.convertToBl(false));
        		dto.setStudyProtocolType(StConverter.convertToSt(NonInterventionalStudyProtocol.class.getSimpleName()));
                Set<Ii> secondaryIdentifiers =  new HashSet<Ii>();
        		Ii spSecId = new Ii();
        		spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        		dto.setIdentifier(completeIi);
        		dto.setStatusCode(CdConverter.convertToCd(ActStatusCode.ACTIVE));
                dto.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.OTHER));
        		spSecId.setExtension("NCI-2013-00001");
        		secondaryIdentifiers.add(spSecId);
        		dto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(secondaryIdentifiers));
        		return dto;
        	}
            });
        when(paSvcLocator.getStudyProtocolService()).thenReturn(spSvc);
        File file = new File(this.getClass().getResource("/NonInterventional_Complete.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(0, 1, 0);

        BatchFile r = getResultFromDb();
        assertTrue(r.isPassedValidation());
        assertTrue(r.isProcessed());
        assertTrue(r.getFileLocation().contains("NonInterventional_Complete.txt"));
        assertTrue(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        AccrualCollections collection = r.getAccrualCollections().get(0);
        assertTrue(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
        assertEquals("NCI-2013-00001", collection.getNciNumber());
        assertTrue(StringUtils.isEmpty(collection.getResults()));
        assertEquals((Integer) 3, collection.getTotalImports());
        
        when(cdusBatchUploadDataValidator.getSubjectAccrualService().getAccrualCounts(eq(true), any(Long.class))).thenReturn(3L);
        file = new File(this.getClass().getResource("/NonInterventional_accrualCounts.txt").toURI());
        batchFile = getBatchFile(file);
        results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertFalse(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertTrue(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(1, 1, 0);

        r = getResultFromDb();
        assertFalse(r.isPassedValidation());
        assertFalse(r.isProcessed());
        assertTrue(r.getFileLocation().contains("NonInterventional_accrualCounts.txt"));
        assertFalse(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        collection = r.getAccrualCollections().get(0);
        assertFalse(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.NO, collection.getChangeCode());
        assertEquals("NCI-2013-00001", collection.getNciNumber());
        assertFalse(StringUtils.isEmpty(collection.getResults()));
        assertEquals(null, collection.getTotalImports());
	}
	
	@Test
	public void testNoninterventionalTrialSummaryLevelBatchupload() throws Exception {
		StudyProtocolServiceRemote spSvc = mock(StudyProtocolServiceRemote.class);
        when(spSvc.loadStudyProtocol(any(Ii.class))).thenAnswer(new Answer<StudyProtocolDTO>() {
        	public StudyProtocolDTO answer(InvocationOnMock invocation) throws Throwable {
        		StudyProtocolDTO dto = new StudyProtocolDTO();
        		dto.setProprietaryTrialIndicator(BlConverter.convertToBl(false));
        		dto.setStudyProtocolType(StConverter.convertToSt(NonInterventionalStudyProtocol.class.getSimpleName()));
                Set<Ii> secondaryIdentifiers =  new HashSet<Ii>();
        		Ii spSecId = new Ii();
        		spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        		dto.setIdentifier(completeIi);
        		dto.setStatusCode(CdConverter.convertToCd(ActStatusCode.ACTIVE));
                dto.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.OTHER));
        		spSecId.setExtension("NCI-2013-00001");
        		secondaryIdentifiers.add(spSecId);
        		dto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(secondaryIdentifiers));
        		return dto;
        	}
            });
        when(paSvcLocator.getStudyProtocolService()).thenReturn(spSvc);
        File file = new File(this.getClass().getResource("/NonInterventional_accrualCounts.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(0, 1, 0);

        BatchFile r = getResultFromDb();
        assertTrue(r.isPassedValidation());
        assertTrue(r.isProcessed());
        assertTrue(r.getFileLocation().contains("NonInterventional_accrualCounts.txt"));
        assertTrue(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        AccrualCollections collection = r.getAccrualCollections().get(0);
        assertTrue(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
        assertEquals("NCI-2013-00001", collection.getNciNumber());
        assertTrue(StringUtils.isEmpty(collection.getResults()));
        assertEquals((Integer) 2, collection.getTotalImports());
        
        when(cdusBatchUploadDataValidator.getSubjectAccrualService().getAccrualCounts(eq(false), any(Long.class))).thenReturn(2L);
        file = new File(this.getClass().getResource("/NonInterventional_Complete.txt").toURI());
        batchFile = getBatchFile(file);
        results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertFalse(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertTrue(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(1, 1, 0);

        r = getResultFromDb();
        assertFalse(r.isPassedValidation());
        assertFalse(r.isProcessed());
        assertTrue(r.getFileLocation().contains("NonInterventional_Complete.txt"));
        assertFalse(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        collection = r.getAccrualCollections().get(0);
        assertFalse(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
        assertEquals("NCI-2013-00001", collection.getNciNumber());
        assertFalse(StringUtils.isEmpty(collection.getResults()));
        assertEquals(null, collection.getTotalImports());
	}
	   
    @Test
    public void testSuAbstractorBatchUpload() throws Exception {
    	
    	final StudyProtocol sp = new StudyProtocol();
        sp.setOfficialTitle("Test Sp1");
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(PAUtil.dateStringToTimestamp("1/1/2009"));
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2010"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        sp.setProprietaryTrialIndicator(true);

        Set<Ii> studySecondaryIdentifiers =  new HashSet<Ii>();
        Ii assignedId = IiConverter.convertToAssignedIdentifierIi("NCI-2012-00003");
        studySecondaryIdentifiers.add(assignedId);

        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setSubmissionNumber(Integer.valueOf(2));
        sp.setProprietaryTrialIndicator(false);
        sp.setAccrualDiseaseCodeSystem("SDC");
        TestSchema.addUpdObject(sp);
        
        StudySubject subj = new StudySubject();
        subj.setPatient(TestSchema.patients.get(0));
        subj.setAssignedIdentifier("001");
        subj.setPaymentMethodCode(PaymentMethodCode.MEDICARE);
        subj.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        subj.setStudyProtocol(sp);
        subj.setStudySite(TestSchema.studySites.get(1));
        subj.setSubmissionTypeCode(AccrualSubmissionTypeCode.UNKNOWN);
        subj.setDateLastCreated(PAUtil.dateStringToDateTime("1/1/2001"));
        TestSchema.addUpdObject(subj);
        
    	StudyProtocolServiceRemote spSvc = mock(StudyProtocolServiceRemote.class);
        when(spSvc.loadStudyProtocol(any(Ii.class))).thenAnswer(new Answer<StudyProtocolDTO>() {
        	public StudyProtocolDTO answer(InvocationOnMock invocation) throws Throwable {
        		StudyProtocolDTO dto = new StudyProtocolDTO();
        		dto.setProprietaryTrialIndicator(BlConverter.convertToBl(true));
        		dto.setStudyProtocolType(StConverter.convertToSt(InterventionalStudyProtocol.class.getSimpleName()));
                Set<Ii> secondaryIdentifiers =  new HashSet<Ii>();
        		Ii spSecId = new Ii();
        		spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        		dto.setIdentifier(IiConverter.convertToIi(sp.getId()));
        		dto.setStatusCode(CdConverter.convertToCd(ActStatusCode.ACTIVE));
                dto.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.PREVENTION));
        		spSecId.setExtension("NCI-2012-00003");
        		secondaryIdentifiers.add(spSecId);
        		dto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(secondaryIdentifiers));
        		return dto;
        	}
            });
        when(paSvcLocator.getStudyProtocolService()).thenReturn(spSvc);
        CSMUserUtil csmUtil = mock(CSMUserService.getInstance().getClass());
        when(csmUtil.isUserInGroup(any(String.class), any(String.class))).thenReturn(true);
        CSMUserService.setInstance(csmUtil);
        


        SearchTrialService searchTrialSvc = mock(SearchTrialService.class);
        when(searchTrialSvc.isAuthorized(any(Ii.class), any(Ii.class))).thenAnswer(new Answer<Bl>() {
            public Bl answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Ii spIi = (Ii) args[0];
                Bl result = new Bl();
                result.setValue(Boolean.FALSE);
                return result;
            }
        });
        when(searchTrialSvc.getTrialSummaryByStudyProtocolIi(any(Ii.class))).thenAnswer(new Answer<SearchTrialResultDto>() {
            public SearchTrialResultDto answer(InvocationOnMock invocation) throws Throwable {
                SearchTrialResultDto result = new SearchTrialResultDto();
                result.setIndustrial(BlConverter.convertToBl(true));
                return result;
            }
            
        });
        cdusBatchUploadDataValidator.setSearchTrialService(searchTrialSvc);       
        
        File file = new File(this.getClass().getResource("/suAbs-accrual-count-batch-file.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);
        BatchImportResults importResults = readerService.importBatchData(batchFile, validationResults.get(0));
        assertEquals("suAbs-accrual-count-batch-file.txt", importResults.getFileName());
        assertEquals(2, importResults.getTotalImports()); 
        verifyEmailsSent(0, 1, 0);
    }
    
    @Test
    public void testSuAbstractorMissingDiseaseBatchUpload() throws Exception {
    	
    	 CSMUserUtil csmUtil = mock(CSMUserService.getInstance().getClass());
        when(csmUtil.isUserInGroup(any(String.class), any(String.class))).thenReturn(true);
        CSMUserService.setInstance(csmUtil);
        
        SearchStudySiteService sssSvc = mock(SearchStudySiteBean.class);
        readerService.setSearchStudySiteService(sssSvc);
        when(sssSvc.isStudyHasCTEPId(any(Ii.class))).thenReturn(true);     

        AccrualDisease disease1 = new AccrualDisease();
        disease1.setCodeSystem("SDC");
        disease1.setDiseaseCode("80000001");
        when(diseaseSvc.getByCode("SDC", "80000001")).thenReturn(disease1);
        
        File file = new File(this.getClass().getResource("/suAbs-accrual-patients-batch-file2.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        String errorMsg = results.get(0).getErrors().toString();
   	    assertTrue(StringUtils.isEmpty(errorMsg));
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(0, 1, 0);

        BatchFile r = getResultFromDb();
        assertTrue(r.isPassedValidation());
        assertTrue(r.isProcessed());
        assertTrue(r.getFileLocation().contains("suAbs-accrual-patients-batch-file2.txt"));
        assertTrue(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        AccrualCollections collection = r.getAccrualCollections().get(0);
        assertTrue(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
        assertEquals("NCI-2010-00003", collection.getNciNumber());
        assertTrue(StringUtils.isEmpty(collection.getResults()));
        assertEquals((Integer) 3, collection.getTotalImports());
    }
	   
 @Test
 public void testSuAbstractorCtepTrialBatchUpload() throws Exception {
 	
 	 CSMUserUtil csmUtil = mock(CSMUserService.getInstance().getClass());
     when(csmUtil.isUserInGroup(any(String.class), any(String.class))).thenReturn(true);
     CSMUserService.setInstance(csmUtil);
     
     SearchStudySiteService sssSvc = mock(SearchStudySiteBean.class);
     readerService.setSearchStudySiteService(sssSvc);
     when(sssSvc.isStudyHasCTEPId(any(Ii.class))).thenReturn(true);     

     AccrualDisease disease1 = new AccrualDisease();
     disease1.setCodeSystem("SDC");
     disease1.setDiseaseCode("80000001");
     when(diseaseSvc.getByCode("SDC", "80000001")).thenReturn(disease1);
     
     File file = new File(this.getClass().getResource("/suAbs-accrual-patients-batch-file.txt").toURI());
     BatchFile batchFile = getBatchFile(file);
     List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
     assertEquals(1, results.size());
     assertFalse(results.get(0).isPassedValidation());
     String errorMsg = results.get(0).getErrors().toString();
     assertFalse(StringUtils.isEmpty(errorMsg));
	 assertTrue(StringUtils.contains(errorMsg, "The Registering Institution Code must be a valid PO or CTEP ID. Code: 93"));
     assertTrue(StringUtils.contains(errorMsg, "The Registering Institution Code must be a valid PO or CTEP ID. Code: 2013"));
     assertFalse(results.get(0).getValidatedLines().isEmpty());
     verifyEmailsSent(0, 1, 0);

     BatchFile r = getResultFromDb();
     assertFalse(r.isPassedValidation());
     assertTrue(r.isProcessed());
     assertTrue(r.getFileLocation().contains("suAbs-accrual-patients-batch-file.txt"));
     assertFalse(StringUtils.isEmpty(r.getResults()));
     assertEquals(1, r.getAccrualCollections().size());
     AccrualCollections collection = r.getAccrualCollections().get(0);
     assertFalse(collection.isPassedValidation());
     assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
     assertEquals("NCI-2010-00003", collection.getNciNumber());
     assertFalse(StringUtils.isEmpty(collection.getResults()));
     assertEquals((Integer) 1, collection.getTotalImports());
     
     List<PatientStage> queryList = getPatientStage("NCI-2010-00003");
     assertEquals(2, queryList.size());
     
     setTrialDiseaseCodeSystem("ICD9");
     disease1.setCodeSystem("ICD9");
     disease1.setDiseaseCode("V100");     
     when(diseaseSvc.getByCode("ICD9", "V100")).thenReturn(disease1);
     
     file = new File(this.getClass().getResource("/suAbs-accrual-patients-batch-file.txt").toURI());
     batchFile = getBatchFile(file);
     results = readerService.validateBatchData(batchFile); 
     verifyEmailsSent(0, 2, 0);
     
     setTrialDiseaseCodeSystem("ICD-O-3");
     disease1.setCodeSystem("ICD-O-3");
     disease1.setDiseaseCode("C998");
     when(diseaseSvc.getByCode("ICD-O-3", "C998")).thenReturn(disease1);
     disease1.setDiseaseCode("7001");
     when(diseaseSvc.getByCode("ICD-O-3", "7001")).thenReturn(disease1);
     
     file = new File(this.getClass().getResource("/suAbs-accrual-patients-batch-file.txt").toURI());
     batchFile = getBatchFile(file);
     results = readerService.validateBatchData(batchFile); 
     verifyEmailsSent(0, 3, 0);
     
     setStudyProtocolSvc();
     file = new File(this.getClass().getResource("/suAbs-accrual-count-batch-file2.txt").toURI());
     batchFile = getBatchFile(file);
     results = readerService.validateBatchData(batchFile);
     assertEquals(1, results.size());
     assertFalse(results.get(0).isPassedValidation());
     errorMsg = results.get(0).getErrors().toString();
	 assertFalse(StringUtils.isEmpty(errorMsg));
     assertTrue(StringUtils.contains(errorMsg, "The Registering Institution Code must be a valid PO or CTEP ID. Code: 2013"));
     assertFalse(results.get(0).getValidatedLines().isEmpty());
     verifyEmailsSent(0, 4, 0);

     r = getResultFromDb();
     assertFalse(r.isPassedValidation());
     assertTrue(r.isProcessed());
     assertTrue(r.getFileLocation().contains("suAbs-accrual-count-batch-file2.txt"));
     assertFalse(StringUtils.isEmpty(r.getResults()));
     assertEquals(1, r.getAccrualCollections().size());
     collection = r.getAccrualCollections().get(0);
     assertFalse(collection.isPassedValidation());
     assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
     assertEquals("NCI-2009-00003", collection.getNciNumber());
     assertFalse(StringUtils.isEmpty(collection.getResults()));
     assertEquals((Integer) 0, collection.getTotalImports());
     
     queryList = getPatientStage("NCI-2009-00003");
     assertEquals(3, queryList.size());
 }
 
    @Test
    public void testSuAbstractorOutOfScope() throws Exception {

        CSMUserUtil csmUtil = mock(CSMUserService.getInstance().getClass());
        when(csmUtil.isUserInGroup(any(String.class), any(String.class)))
                .thenReturn(true);
        CSMUserService.setInstance(csmUtil);

        SearchStudySiteService sssSvc = mock(SearchStudySiteBean.class);
        readerService.setSearchStudySiteService(sssSvc);
        when(sssSvc.isStudyHasCTEPId(any(Ii.class))).thenReturn(true);

        AccrualDisease disease1 = new AccrualDisease();
        disease1.setCodeSystem("SDC");
        disease1.setDiseaseCode("80000001");
        when(diseaseSvc.getByCode("SDC","80000001")).thenReturn(disease1);

        File file = new File(this.getClass()
                .getResource("/suAbs-accrual-patients-batch-file.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        
        final AccrualOutOfScopeTrialDTO dto = new AccrualOutOfScopeTrialDTO();
        dto.setAction("Rejected");
        dto.setCtepID("NCI-2010-00003");
        dto.setFailureReason("Not found");
        dto.setId(2L);
        dto.setSubmissionDate(new Date());
        dto.setUserLoginName("ctrpsubstractor");       
        OUT_OF_SCOPE_TRIALS_DATASTORE.put(dto.getId(), dto);
        
        List<BatchValidationResults> results = readerService
                .validateBatchData(batchFile);
        
        assertEquals(1, results.size());        
        assertTrue(results.get(0).isOutOfScope());     
        verifyEmailsSent(0, 0, 0);

    }
    
    @Test
    public void testSuAbstractorRemoveOutOfScopeEntry() throws Exception {

        CSMUserUtil csmUtil = mock(CSMUserService.getInstance().getClass());
        when(csmUtil.isUserInGroup(any(String.class), any(String.class)))
                .thenReturn(true);
        CSMUserService.setInstance(csmUtil);

        SearchStudySiteService sssSvc = mock(SearchStudySiteBean.class);
        readerService.setSearchStudySiteService(sssSvc);
        when(sssSvc.isStudyHasCTEPId(any(Ii.class))).thenReturn(true);

        AccrualDisease disease1 = new AccrualDisease();
        disease1.setCodeSystem("SDC");
        disease1.setDiseaseCode("80000001");
        when(diseaseSvc.getByCode("SDC","80000001")).thenReturn(disease1);

        File file = new File(this.getClass()
                .getResource("/suAbs-accrual-patients-batch-file.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        
        final AccrualOutOfScopeTrialDTO dto = new AccrualOutOfScopeTrialDTO();
        dto.setAction("");
        dto.setCtepID("NCI-2010-00003");
        dto.setFailureReason("Not found");
        dto.setId(2L);
        dto.setSubmissionDate(new Date());
        dto.setUserLoginName("ctrpsubstractor");       
        OUT_OF_SCOPE_TRIALS_DATASTORE.put(dto.getId(), dto);
        
        List<BatchValidationResults> results = readerService
                .validateBatchData(batchFile);
        
        assertEquals(1, results.size());        
        assertFalse(results.get(0).isOutOfScope());
        results.get(0).setOutOfScope(true);
        assertNull(OUT_OF_SCOPE_TRIALS_DATASTORE.get(dto.getId()));
        verifyEmailsSent(0, 1, 0);

    }
    
    @Test
    public void testSuAbstractorOutOfScopeEntryCreated() throws Exception {

        CSMUserUtil csmUtil = mock(CSMUserService.getInstance().getClass());
        when(csmUtil.isUserInGroup(any(String.class), any(String.class)))
                .thenReturn(true);
        CSMUserService.setInstance(csmUtil);

        SearchStudySiteService sssSvc = mock(SearchStudySiteBean.class);
        readerService.setSearchStudySiteService(sssSvc);
        when(sssSvc.isStudyHasCTEPId(any(Ii.class))).thenReturn(true);

        AccrualDisease disease1 = new AccrualDisease();
        disease1.setCodeSystem("SDC");
        disease1.setDiseaseCode("80000001");
        when(diseaseSvc.getByCode("SDC","80000001")).thenReturn(disease1);

        File file = new File(this.getClass()
                .getResource("/suAbs-accrual-patients-batch-file-nonexistent.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        
        List<BatchValidationResults> results = readerService
                .validateBatchData(batchFile);
        
        assertEquals(1, results.size());        
        assertFalse(results.get(0).isOutOfScope());
        verifyEmailsSent(1, 0, 0);
        
        AccrualOutOfScopeTrialDTO dto = PaServiceLocator
                .getInstance().getAccrualUtilityService().getByCtepID("NCI-2015-10003");
        assertNotNull(dto);
        assertEquals("Missing Trial", dto.getFailureReason());

    }



private List<PatientStage> getPatientStage(String nciId) {
	Session session = PaHibernateUtil.getCurrentSession();
     session.clear();
     String hql = "from PatientStage where studyIdentifier = '" + nciId + "'";
     Query query = session.createQuery(hql);
     List<PatientStage> queryList = query.list();
	return queryList;
}

	@Test
	public void patientRCCoverage() throws URISyntaxException, PAException, IOException {
		File file = new File(this.getClass().getResource("/patientRaceCodeValidation2.txt").toURI());
		BatchFile batchFile = getBatchFile(file);
		List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString())); 
        String errorMsg = results.get(0).getErrors().toString();
        assertTrue(StringUtils.contains(errorMsg, "Patient race code is missing for patient ID 200708"));
        assertTrue(results.get(0).getValidatedLines().isEmpty()); 
        verifyEmailsSent(1, 0, 0);
	}
	
	@Test
	public void testTrialwithCtepIdNotSuAbstractor() throws URISyntaxException, PAException, IOException {    
        CSMUserUtil csmUtil = mock(CSMUserService.getInstance().getClass());
        when(csmUtil.isUserInGroup(any(String.class), any(String.class))).thenReturn(false);
        CSMUserService.setInstance(csmUtil);

        SearchStudySiteService sssSvc = mock(SearchStudySiteBean.class);
        readerService.setSearchStudySiteService(sssSvc);
        cdusBatchUploadDataValidator.setSearchStudySiteService(sssSvc);
        when(sssSvc.isStudyHasCTEPId(any(Ii.class))).thenReturn(true);
        when(sssSvc.isStudyHasDCPId(any(Ii.class))).thenReturn(true);
        
        File file = new File(this.getClass().getResource("/CDUS_Complete.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString())); 
        String errorMsg = results.get(0).getErrors().toString(); 
        assertTrue(StringUtils.contains(errorMsg, "Only CTRO Team can do batch upload for NCI-2010-00003 identifier."));
        assertTrue(results.get(0).getValidatedLines().isEmpty()); 
        verifyEmailsSent(1, 0, 0);
        
	}

	@Test
	public void patientICDO3Coverage() throws URISyntaxException, PAException, IOException {
         setTrialDiseaseCodeSystem("ICD-O-3");
		 AccrualDisease disease1 = new AccrualDisease();
	     disease1.setCodeSystem("ICD-O-3");
	     disease1.setDiseaseCode("C34.1");
	     AccrualDisease disease2 = new AccrualDisease();
	     disease2.setCodeSystem("ICD-O-3");
	     disease2.setDiseaseCode("8000");
	     when(diseaseSvc.getByCode("ICD-O-3", "C34.1")).thenReturn(disease1);
	     when(diseaseSvc.getByCode("ICD-O-3", "C341")).thenReturn(disease1);
	     when(diseaseSvc.getByCode("ICD-O-3", "8000")).thenReturn(disease2);
	        
		File file = new File(this.getClass().getResource("/ICD-O-3_coverage.txt").toURI());
		BatchFile batchFile = getBatchFile(file);
		List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
		assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertFalse(results.get(0).getValidatedLines().isEmpty()); 
        verifyEmailsSent(0, 1, 0);

        BatchFile r = getResultFromDb();
        assertTrue(r.isPassedValidation());
        assertTrue(r.isProcessed());
        assertTrue(r.getFileLocation().contains("ICD-O-3_coverage.txt"));
        assertTrue(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        AccrualCollections collection = r.getAccrualCollections().get(0);
        assertTrue(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
        assertEquals("NCI-2009-00001", collection.getNciNumber());
        assertTrue(StringUtils.isEmpty(collection.getResults()));
        assertEquals((Integer) 3, collection.getTotalImports());
        
        file = new File(this.getClass().getResource("/ICD-O-3_coverage2.txt").toURI());
		batchFile = getBatchFile(file);
		results = readerService.validateBatchData(batchFile);
		assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString())); 
        String errorMsg = results.get(0).getErrors().toString(); 
        assertTrue(StringUtils.contains(errorMsg, "The Registering Institution Code must be a valid PO or CTEP ID. Code: 55"));
        assertTrue(results.get(0).getValidatedLines().isEmpty()); 
        verifyEmailsSent(1, 1, 0);
	}
        
	@Test
	public void junitCoverage() throws URISyntaxException, PAException, IOException {
		File file = new File(this.getClass().getResource("/patientRaceCodeValidation.txt").toURI());
		BatchFile batchFile = getBatchFile(file);
		List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(0, 1, 0);
		       
        file = new File(this.getClass().getResource("/junit_coverage.txt").toURI());
        batchFile = getBatchFile(file);
        results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString())); 
        String errorMsg = results.get(0).getErrors().toString();
        assertTrue(StringUtils.contains(errorMsg, "Found invalid change code 3. Valid value for COLLECTIONS.Change_Code are 1 and 2."));
        assertTrue(StringUtils.contains(errorMsg, "The Registering Institution Code must be a valid PO or CTEP ID. Code: 21"));
        assertTrue(StringUtils.contains(errorMsg, "Patients at line 4  must contain a valid NCI protocol identifier or the CTEP/DCP identifier."));
        assertTrue(StringUtils.contains(errorMsg, "Patient Registering Institution Code is missing for patient ID 223694 at line 4"));
        assertTrue(StringUtils.contains(errorMsg, "Please enter valid alpha2 country code for patient ID 223694 at line 4"));
        assertTrue(StringUtils.contains(errorMsg, "Please enter valid patient payment method for patient ID 207747 at line 2"));
        assertTrue(StringUtils.contains(errorMsg, "Patient birth date must be in YYYYMM format for patient ID 208847 at line 3"));
        assertTrue(StringUtils.contains(errorMsg, "Please enter valid patient ethnicity for patient ID 208847 at line 3"));
        assertTrue(results.get(0).getValidatedLines().isEmpty()); 
        verifyEmailsSent(1, 1, 0);

        file = new File(this.getClass().getResource("/no_protocol.txt").toURI());
        batchFile = getBatchFile(file);
        results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString())); 
        errorMsg = results.get(0).getErrors().toString();
        assertTrue(StringUtils.contains(errorMsg, "No Study Protocol Identifier could be found in the given file."));
        verifyEmailsSent(2, 1, 0);
        
        file = new File(this.getClass().getResource("/no_protocol.zip").toURI());
        batchFile = getBatchFile(file);
        results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString())); 
        errorMsg = results.get(0).getErrors().toString();
        assertTrue(StringUtils.contains(errorMsg, "No Study Protocol Identifier could be found in the given file."));
        verifyEmailsSent(3, 1, 0);
        
        file = new File(this.getClass().getResource("/accrual_format_issue_file.zip").toURI());
        batchFile = getBatchFile(file);
        readerService.validateBatchData(batchFile);
        verifyEmailsSent(5, 1, 0);
        BatchFile r = getResultFromDb(); 
        assertFalse(r.isPassedValidation());
        assertFalse(r.isProcessed());
        assertTrue(r.getFileLocation().contains("accrual_format_issue_file.zip"));
        assertFalse(StringUtils.isEmpty(r.getResults()));
        assertTrue(StringUtils.contains(r.getResults(), "No Study Protocol Identifier could be found in the given file."));
        
        LookUpTableServiceRemote lookUpTableSvc = paSvcLocator.getLookUpTableService();
        doThrow(new  gov.nih.nci.pa.service.PAException("PAException")).when(lookUpTableSvc).getPropertyValue(any(String.class));
        
        file = new File(this.getClass().getResource("/accrual_format_issue_file.zip").toURI());
        batchFile = getBatchFile(file);
        readerService.validateBatchData(batchFile);
	}

	@Test
	public void testDiseaseCodes() throws Exception, IOException {
        AccrualDisease disease1 = new AccrualDisease();
        disease1.setCodeSystem("SDC");
        disease1.setDiseaseCode("code1");
        AccrualDisease disease2 = new AccrualDisease();
        disease2.setCodeSystem("SDC");
        disease2.setDiseaseCode("code2");
        File file = new File(this.getClass().getResource("/junit_coverage2.txt").toURI());
        BatchFile batchFile = getBatchFile(file);

        // not found
        when(diseaseSvc.getByCode("SDC", "code1")).thenReturn(null);
        when(diseaseSvc.getByCode("SDC", "code2")).thenReturn(null);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        verifyEmailsSent(1, 0, 0);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString())); 
        String errorMsg = results.get(0).getErrors().toString();
        assertTrue(StringUtils.contains(errorMsg, "Patient SDC Disease Code is invalid for patient ID"));

        // found, code systems match
        when(diseaseSvc.getByCode("SDC", "code1")).thenReturn(disease1);
        when(diseaseSvc.getByCode("SDC", "code2")).thenReturn(disease2);
        results = readerService.validateBatchData(batchFile);
        verifyEmailsSent(1, 1, 0);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString())); 

        // found, code systems don't match, all existing in file
        setTrialDiseaseCodeSystem("ICD9");
        results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString())); 
        errorMsg = results.get(0).getErrors().toString();
        assertTrue(StringUtils.contains(errorMsg, "Patient ICD9 Disease Code is invalid for patient ID")); 

        // found, code systems don't match, file not inclusive of all existing
        StudySubject subj = new StudySubject();
        subj.setDisease(TestSchema.diseases.get(0));
        subj.setPatient(TestSchema.patients.get(0));
        subj.setAssignedIdentifier("xyzzy");
        subj.setPaymentMethodCode(PaymentMethodCode.MEDICARE);
        subj.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        subj.setStudyProtocol(TestSchema.studyProtocols.get(2));
        subj.setStudySite(TestSchema.studySites.get(1));
        subj.setSubmissionTypeCode(AccrualSubmissionTypeCode.UNKNOWN);
        subj.setDateLastCreated(PAUtil.dateStringToDateTime("1/1/2001"));
        TestSchema.addUpdObject(subj);
        results = readerService.validateBatchData(batchFile);
        verifyEmailsSent(3, 1, 0);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString())); 
        errorMsg = results.get(0).getErrors().toString();
        assertTrue(StringUtils.contains(errorMsg, "Patient ICD9 Disease Code is invalid for patient ID"));
	}
	
    @Test
    public void batchUploadWithValidationErrorsAndDuplicateSubjects() throws URISyntaxException,
            PAException, IOException {
        File file = new File(this.getClass()
                .getResource("/CDUS_Complete-modified-dupes.txt").toURI());
        BatchFile batchFile = getBatchFile(file);

        PaServiceLocator.getInstance().setServiceLocator(paSvcLocator);
        List<BatchValidationResults> results = readerService
                .validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils
                .isNotEmpty(results.get(0).getErrors().toString()));
        assertTrue(results.get(0).getValidatedLines().isEmpty());
        assertTrue(results.get(0).getPreprocessingResult().getValidationErrors().size() == 1);
        
        verifyEmailsSent(1, 0, 0);
    }
    
    @Test
    public void batchUploadWithDuplicateSubjects() throws Exception {
        assertEquals(0, studySubjectService.getByStudyProtocol(completeIi).size());

        File file = new File(this.getClass().getResource("/CDUS_Complete_dupes.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);
        assertEquals(1, validationResults.size());
        assertEquals(0, validationResults.get(0).getErrors().length());
        assertTrue(validationResults.get(0).getPreprocessingResult().getValidationErrors().size() == 1);
        BatchImportResults importResults = readerService.importBatchData(batchFile, validationResults.get(0));
        assertEquals(23, importResults.getTotalImports());        
        assertEquals(23, studySubjectService.getByStudyProtocol(completeIi).size());
        verifyEmailsSent(0, 1, 0);
    }
	
    @Test
    public void completeBatchValidation() throws URISyntaxException, PAException, IOException {
        File file = new File(this.getClass().getResource("/CDUS_Complete-modified.txt").toURI());
        BatchFile batchFile = getBatchFile(file);

        PaServiceLocator.getInstance().setServiceLocator(paSvcLocator);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString()));
        assertTrue(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(1, 0, 0);

        BatchFile r = getResultFromDb();
        assertFalse(r.isPassedValidation());
        assertFalse(r.isProcessed());
        assertTrue(r.getFileLocation().contains("CDUS_Complete-modified.txt"));
        assertFalse(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        AccrualCollections collection = r.getAccrualCollections().get(0);
        assertFalse(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
        assertEquals("NCI-2009-00001", collection.getNciNumber());
        assertFalse(StringUtils.isEmpty(collection.getResults()));
        assertNull(collection.getTotalImports());

        file = new File(this.getClass().getResource("/CDUS_Complete.txt").toURI());
        batchFile = getBatchFile(file);
        results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(1, 1, 0);

        r = getResultFromDb();
        assertTrue(r.isPassedValidation());
        assertTrue(r.isProcessed());
        assertTrue(r.getFileLocation().contains("CDUS_Complete.txt"));
        assertTrue(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        collection = r.getAccrualCollections().get(0);
        assertTrue(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
        assertEquals("NCI-2010-00003", collection.getNciNumber());
        assertTrue(StringUtils.isEmpty(collection.getResults()));
        assertEquals((Integer) 24, collection.getTotalImports());
    }

    @Test
    public void abbreviatedBatchValidation() throws URISyntaxException, PAException, IOException {
        File file = new File(this.getClass().getResource("/CDUS_Abbreviated.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(0, 1, 0);

        BatchFile r = getResultFromDb();
        assertTrue(r.isPassedValidation());
        assertTrue(r.isProcessed());
        assertTrue(r.getFileLocation().contains("CDUS_Abbreviated.txt"));
        assertTrue(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        AccrualCollections collection = r.getAccrualCollections().get(0);
        assertTrue(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
        assertEquals("NCI-2009-00001", collection.getNciNumber());
        assertTrue(StringUtils.isEmpty(collection.getResults()));
        assertEquals((Integer) 72, collection.getTotalImports());
    }

   @Test
    public void changeCode2BatchValidation() throws URISyntaxException, PAException, IOException {
        File file = new File(this.getClass().getResource("/CDUS_Abbreviated_cc2.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(0, 1, 0);

        BatchFile r = getResultFromDb();
        assertTrue(r.isPassedValidation());
        assertTrue(r.isProcessed());
        assertTrue(r.getFileLocation().contains("CDUS_Abbreviated_cc2.txt"));
        assertTrue(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        AccrualCollections collection = r.getAccrualCollections().get(0);
        assertTrue(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.NO, collection.getChangeCode());
        assertEquals("NCI-2009-00001", collection.getNciNumber());
        assertTrue(StringUtils.isEmpty(collection.getResults()));
        assertEquals((Integer) 0, collection.getTotalImports());
    }

    @Test
    public void accrualCountBatchValidation() throws URISyntaxException, PAException, IOException {
    	CSMUserUtil csmUtil = mock(CSMUserService.getInstance().getClass());
        when(csmUtil.isUserInGroup(any(String.class), any(String.class))).thenReturn(true);
        CSMUserService.setInstance(csmUtil);
        
        SearchStudySiteService sssSvc = mock(SearchStudySiteBean.class);
        readerService.setSearchStudySiteService(sssSvc);
        when(sssSvc.isStudyHasCTEPId(any(Ii.class))).thenReturn(true);
        
        File file = new File(this.getClass().getResource("/accrual-count-invalid-batch-file.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        String errorMsg = results.get(0).getErrors().toString();
        assertTrue(StringUtils.contains(errorMsg, "Accrual count has been provided for a non Industrial study. This is not allowed."));
        assertTrue(StringUtils.contains(errorMsg, "Accrual count is missing at line 2"));
        assertTrue(StringUtils.contains(errorMsg, "Accrual study site is missing at line 3"));
        assertTrue(StringUtils.contains(errorMsg, "The Registering Institution Code must be a valid PO or CTEP ID. Code: notvalidcode"));
        assertTrue(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(1, 0, 0);

        BatchFile r = getResultFromDb();
        assertFalse(r.isPassedValidation());
        assertFalse(r.isProcessed());
        assertTrue(r.getFileLocation().contains("accrual-count-invalid-batch-file.txt"));
        assertFalse(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        AccrualCollections collection = r.getAccrualCollections().get(0);
        assertFalse(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
        assertEquals("NCI-2009-00001", collection.getNciNumber());
        assertFalse(StringUtils.isEmpty(collection.getResults()));
        assertNull(collection.getTotalImports());
}
    @Test
    public void accrualCountBatch() throws URISyntaxException, PAException, IOException {

        setStudyProtocolSvc();
        
        File file = new File(this.getClass().getResource("/accrual-count-batch-file.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(0, 1, 0);

        BatchFile r = getResultFromDb();
        assertTrue(r.isPassedValidation());
        assertTrue(r.isProcessed());
        assertTrue(r.getFileLocation().contains("accrual-count-batch-file.txt"));
        assertTrue(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        AccrualCollections collection = r.getAccrualCollections().get(0);
        assertTrue(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
        assertEquals("NCI-2009-00003", collection.getNciNumber());
        assertTrue(StringUtils.isEmpty(collection.getResults()));
        assertEquals((Integer) 2, collection.getTotalImports());
    	
    }

    @Test
    public void abbreviatedPreventionBatchValidation() throws URISyntaxException, PAException, IOException {
        File file = new File(this.getClass().getResource("/cdus-abbreviated-prevention-study.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(0, 1, 0);
    }
    
    @Test
    public void crfValuesBatchValidation() throws URISyntaxException, PAException, IOException {
        File file = new File(this.getClass().getResource("/cdus-abbreviated-with-crf-values.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(0, 1, 0);
    }
    
    @Test
    public void archiveBatchValidation() throws URISyntaxException, PAException, IOException {
        File file = new File(this.getClass().getResource("/CDUS.zip").toURI());
        BatchFile batchFile = getBatchFile(file);
        
        batchFile.setPassedValidation(false);
        batchFile.setFileLocation(file.getAbsolutePath());
        batchFile.setSubmitter(TestSchema.registryUsers.get(0));
        batchFileSvc.update(batchFile);
        
        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);
        assertEquals(3, validationResults.size());
        for (BatchValidationResults result : validationResults) {
            assertTrue(result.isPassedValidation());
            assertTrue(StringUtils.isEmpty(result.getErrors().toString()));
            assertFalse(result.getValidatedLines().isEmpty());
        }
        verifyEmailsSent(0, 3, 0);

        BatchFile r = getResultFromDb();
        assertTrue(r.isPassedValidation());
        assertTrue(r.isProcessed());
        assertTrue(r.getFileLocation().contains("CDUS.zip"));
        assertEquals(3, r.getAccrualCollections().size());
             
        file = new File(this.getClass().getResource("/accrual_format_issue_file2.zip").toURI());
        batchFile = getBatchFile(file);
        validationResults = readerService.validateBatchData(batchFile);
        assertEquals(1, validationResults.size());
        verifyEmailsSent(0, 4, 1);
        
        r = getResultFromDb(); 
        assertTrue(r.isPassedValidation());
        assertTrue(r.isProcessed());
        assertTrue(r.getFileLocation().contains("accrual_format_issue_file2.zip"));
        assertFalse(StringUtils.isEmpty(r.getResults()));
        assertTrue(StringUtils.contains(r.getResults(), "Failed proceesing a batch file: batchfile1.txt due to null"));
    }

    @Test
    public void testIsValidProtocolId() throws PAException, TooManyResultsException, URISyntaxException, IOException {
        when(paSvcLocator.getMailManagerService()).thenReturn(mailService);
        PaServiceLocator.getInstance().setServiceLocator(paSvcLocator);

        File file = new File(this.getClass().getResource("/CDUS_Complete-modified.txt").toURI());
        List<BatchValidationResults> results = readerService.validateBatchData(getBatchFile(file));
        verifyEmailsSent(1, 0, 0);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString()));
        assertTrue(results.get(0).getValidatedLines().isEmpty());
    }

    @Test
    public void performCompleteBatchImport() throws Exception {
        assertEquals(0, studySubjectService.getByStudyProtocol(completeIi).size());

        File file = new File(this.getClass().getResource("/CDUS_Complete.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);
        assertEquals(1, validationResults.size());
        assertEquals(0, validationResults.get(0).getErrors().length());
        BatchImportResults importResults = readerService.importBatchData(batchFile, validationResults.get(0));
        assertEquals(24, importResults.getTotalImports());
        assertEquals("CDUS_Complete.txt", importResults.getFileName());
        assertEquals(24, studySubjectService.getByStudyProtocol(completeIi).size());
        verifyEmailsSent(0, 1, 0);
        mailService = mock(MailManagerServiceRemote.class);
        when(paSvcLocator.getMailManagerService()).thenReturn(mailService);

        file = new File(this.getClass().getResource("/CDUS_Complete-modified.txt").toURI());
        batchFile = getBatchFile(file);
        validationResults = readerService.validateBatchData(batchFile);
        assertEquals(1, validationResults.size());
        assertTrue(StringUtils.isNotBlank(validationResults.get(0).getErrors().toString()));
        verifyEmailsSent(1, 0, 0);
    }

    @Test
    public void performAbbreviatedBatchImport() throws Exception {
        assertEquals(2, studySubjectService.getByStudyProtocol(abbreviatedIi).size());

        File file = new File(this.getClass().getResource("/CDUS_Abbreviated.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);
        BatchImportResults importResults = readerService.importBatchData(batchFile, validationResults.get(0));
        assertEquals(72, importResults.getTotalImports());
        assertEquals("CDUS_Abbreviated.txt", importResults.getFileName());
        assertEquals(74, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        verifyEmailsSent(0, 1, 0);
    }


    @Test
    public void performAbbreviatedPreventionBatchImport() throws Exception {
        assertEquals(0, studySubjectService.getByStudyProtocol(preventionIi).size());

        File file = new File(this.getClass().getResource("/cdus-abbreviated-prevention-study.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);
        BatchImportResults importResults = readerService.importBatchData(batchFile, validationResults.get(0));
        assertEquals(72, importResults.getTotalImports());
        assertEquals("cdus-abbreviated-prevention-study.txt", importResults.getFileName());
        assertEquals(72, studySubjectService.getByStudyProtocol(preventionIi).size());
        verifyEmailsSent(0, 1, 0);
    }

    @Test
    public void testPerformImportOfArchive() throws Exception {
        File file = new File(this.getClass().getResource("/CDUS.zip").toURI());
        BatchFile batchFile = getBatchFile(file);
        
        batchFile.setPassedValidation(false);
        batchFile.setFileLocation(file.getAbsolutePath());
        batchFile.setSubmitter(TestSchema.registryUsers.get(0));
        batchFileSvc.update(batchFile);
        
        assertEquals(0, studySubjectService.getByStudyProtocol(completeIi).size());
        assertEquals(2, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        assertEquals(0, studySubjectService.getByStudyProtocol(preventionIi).size());

        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);

        assertEquals("cdus-abbreviated-prevention-study.txt", validationResults.get(0).getFileName());
        assertEquals(72, studySubjectService.getByStudyProtocol(preventionIi).size());

        assertEquals("CDUS_Abbreviated.txt", validationResults.get(1).getFileName());
        assertEquals(74, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        
        assertEquals("CDUS_Complete.txt", validationResults.get(2).getFileName());
        assertEquals(24, studySubjectService.getByStudyProtocol(completeIi).size());

        verifyEmailsSent(0, 3, 0);

        // resubmit to confirm that existing rows are only updated
        batchFile = getBatchFile(file);
        batchFile.setPassedValidation(false);
        batchFile.setFileLocation(file.getAbsolutePath());
        batchFile.setSubmitter(TestSchema.registryUsers.get(0));
        batchFileSvc.update(batchFile);

        validationResults = readerService.validateBatchData(batchFile);

        assertEquals("cdus-abbreviated-prevention-study.txt", validationResults.get(0).getFileName());
        assertEquals(72, studySubjectService.getByStudyProtocol(preventionIi).size());

        assertEquals("CDUS_Abbreviated.txt", validationResults.get(1).getFileName());
        assertEquals(74, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        
        assertEquals("CDUS_Complete.txt", validationResults.get(2).getFileName());
        assertEquals(24, studySubjectService.getByStudyProtocol(completeIi).size());
        verifyEmailsSent(0, 6, 0);

    }
    
    @Test
    public void duplicateFileImport() throws Exception {
        assertEquals(2, studySubjectService.getByStudyProtocol(abbreviatedIi).size());

        File file = new File(this.getClass().getResource("/CDUS_Abbreviated.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);
        BatchImportResults importResults = readerService.importBatchData(batchFile, validationResults.get(0));
        verifyEmailsSent(0, 1, 0);
        assertEquals(72, importResults.getTotalImports());
        assertEquals("CDUS_Abbreviated.txt", importResults.getFileName());
        assertEquals(74, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        
        importResults = readerService.importBatchData(batchFile, validationResults.get(0));
        assertEquals(72, importResults.getTotalImports());
        assertEquals("CDUS_Abbreviated.txt", importResults.getFileName());
        assertEquals(74, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
    }
    
    @Test
    public void crfValuesFileImport() throws Exception {
        assertEquals(2, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        
        File file = new File(this.getClass().getResource("/cdus-abbreviated-with-crf-values.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);
        BatchImportResults importResults = readerService.importBatchData(batchFile, validationResults.get(0));
        assertEquals(72, importResults.getTotalImports());
        assertEquals("cdus-abbreviated-with-crf-values.txt", importResults.getFileName());
        assertEquals(74, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        verifyEmailsSent(0, 1, 0);
    }
    
    @Test
    public void accrualCountBatchFileImport() throws Exception {
    	
    	setStudyProtocolSvc();
        
        File file = new File(this.getClass().getResource("/accrual-count-batch-file.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);
        BatchImportResults importResults = readerService.importBatchData(batchFile, validationResults.get(0));
        assertEquals("accrual-count-batch-file.txt", importResults.getFileName());
        assertEquals(2, importResults.getTotalImports());
        verifyEmailsSent(0, 1, 0);        
    }
    
    @Test
    public void testOrganizationIdBelongsToPO() throws Exception {
        OrganizationEntityServiceRemote organizationEntityService = mock(OrganizationEntityServiceRemote.class);
        when(organizationEntityService.getOrganization(any(Ii.class))).thenReturn(new OrganizationDTO());

        HealthCareFacilityCorrelationServiceRemote healthCareFacilityCorrelationService = 
            mock(HealthCareFacilityCorrelationServiceRemote.class);
        when(healthCareFacilityCorrelationService.search(any(HealthCareFacilityDTO.class)))
            .thenReturn(new ArrayList<HealthCareFacilityDTO>());

        poServiceLoc = mock(PoServiceLocator.class);
        setUpPoRegistry();
        when(poServiceLoc.getOrganizationEntityService()).thenReturn(organizationEntityService);
        when(poServiceLoc.getHealthCareFacilityCorrelationService()).thenReturn(healthCareFacilityCorrelationService);
        
        File file = new File(this.getClass().getResource("/CDUS_Complete.txt").toURI());
        List<BatchValidationResults> results = readerService.validateBatchData(getBatchFile(file));
        assertTrue(results.get(0).isPassedValidation());
        verifyEmailsSent(0, 1, 0);
    }
    
    @Test
    public void testOrganizationIdBelongsToCTEP() throws Exception {
        File file = new File(this.getClass().getResource("/CDUS_Complete.txt").toURI());
        List<BatchValidationResults> results = readerService.validateBatchData(getBatchFile(file));
        assertTrue(results.get(0).isPassedValidation());
        verifyEmailsSent(0, 1, 0);
    }

    @Test
    public void birthDateYearOnlyOr000000() throws Exception {
        File file = new File(this.getClass().getResource("/CDUS_Complete-BirthDates.txt").toURI());
        List<BatchValidationResults> results = readerService.validateBatchData(getBatchFile(file));
        verifyEmailsSent(1, 0, 0);
        assertFalse(results.get(0).isPassedValidation());
        assertEquals(16, StringUtils.countMatches(results.get(0).getErrors().toString(), "Patient birth date must be in YYYYMM format"));
    }

    @Test
    public void testIndustrialTrialWithpatients() throws Exception {
        assertEquals(0, studySubjectService.getByStudyProtocol(preventionIi).size());
        setStudyProtocolSvc();
        File file = new File(this.getClass().getResource("/cdus-abbreviated-prevention-study.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        verifyEmailsSent(1, 0, 0);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        String errorMsg = results.get(0).getErrors().toString();
        assertTrue(StringUtils.contains(errorMsg, "Individual Patients should not be added to Industrial Trials for patient ID 207747 at line 2"));
        assertTrue(StringUtils.contains(errorMsg, "Individual Patients should not be added to Industrial Trials for patient ID 223694 at line 3"));
        assertTrue(StringUtils.contains(errorMsg, "Individual Patients should not be added to Industrial Trials for patient ID 210081 at line 73"));
        assertTrue(results.get(0).getValidatedLines().isEmpty());
    }

    @Test
     public void processChangeCode2BatchValidation() throws URISyntaxException, PAException, IOException {
         File file = new File(this.getClass().getResource("/cc.txt").toURI());
         BatchFile batchFile = getBatchFile(file);
         List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
         assertEquals(1, results.size());
         assertTrue(results.get(0).isPassedValidation());
         assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
         assertFalse(results.get(0).getValidatedLines().isEmpty());
         verifyEmailsSent(0, 1, 0);

         BatchFile r = getResultFromDb();
         assertTrue(r.isPassedValidation());
         assertTrue(r.isProcessed());
         assertTrue(r.getFileLocation().contains("cc.txt"));
         assertTrue(StringUtils.isEmpty(r.getResults()));
         assertEquals(1, r.getAccrualCollections().size());
         AccrualCollections collection = r.getAccrualCollections().get(0);
         assertTrue(collection.isPassedValidation());
         assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
         assertEquals("NCI-2010-00003", collection.getNciNumber());
         assertTrue(StringUtils.isEmpty(collection.getResults()));
         assertEquals((Integer) 2, collection.getTotalImports());
         
     }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testBatchUploadConcurrencySameFile() throws Exception {
        File file1 = File.createTempFile(UUID.randomUUID().toString(), "txt");
        FileUtils.writeByteArrayToFile(
                file1,
                IOUtils.toByteArray(this.getClass().getResourceAsStream(
                        "/CDUS_Complete_dupes.txt")));
        file1.deleteOnExit();
        
        File file2 = File.createTempFile(UUID.randomUUID().toString(), "txt");
        FileUtils.writeByteArrayToFile(
                file2,
                IOUtils.toByteArray(this.getClass().getResourceAsStream(
                        "/CDUS_Complete_dupes.txt")));
        file2.deleteOnExit();
                
        final BatchFile batchFile1 = getBatchFile(file1);
        final BatchFile batchFile2 = getBatchFile(file2);
        
        final List<Thread> threadsThatEnteredProcessing = new ArrayList<Thread>();
       
        final CdusBatchUploadReaderBean bean1 = mock(CdusBatchUploadReaderBean.class);
        when(bean1.validateBatchData(any(BatchFile.class))).thenCallRealMethod();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                threadsThatEnteredProcessing.add(Thread.currentThread());
                Thread.sleep(5000);
                threadsThatEnteredProcessing.remove(Thread.currentThread());
                return null;
            }
        }).when(bean1).batchFileProcessing(any(List.class),
                any(BatchFile.class), any(String.class), any(File.class));
        
        final CdusBatchUploadReaderBean bean2 = mock(CdusBatchUploadReaderBean.class);
        when(bean2.validateBatchData(any(BatchFile.class))).thenCallRealMethod();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                threadsThatEnteredProcessing.add(Thread.currentThread());
                Thread.sleep(5000);
                threadsThatEnteredProcessing.remove(Thread.currentThread());
                return null;
            }
        }).when(bean2).batchFileProcessing(any(List.class),
                any(BatchFile.class), any(String.class), any(File.class));
        
        final Thread t1 = new Thread(new Runnable() {            
            @Override
            public void run() {
                try {
                    bean1.validateBatchData(batchFile1);
                } catch (IOException e) {                  
                }
            }
        });
        
        final Thread t2 = new Thread(new Runnable() {            
            @Override
            public void run() {
                try {
                    bean2.validateBatchData(batchFile2);
                } catch (IOException e) {                  
                }
            }
        });
        t1.setDaemon(true);
        t2.setDaemon(true);

        t1.start();
        Thread.sleep(1000L);
        t2.start();
        Thread.sleep(1000L);
        
        assertEquals(1, threadsThatEnteredProcessing.size());
        assertEquals(t1, threadsThatEnteredProcessing.get(0));        
        
        Thread.sleep(5000L);
        assertEquals(1, threadsThatEnteredProcessing.size());
        assertEquals(t2, threadsThatEnteredProcessing.get(0));
        
        
       
    } 
    
    @SuppressWarnings("unchecked")
    @Test
    public void testBatchUploadConcurrencyDifferentFiles() throws Exception {   
        System.out.println("testBatchUploadConcurrencyDifferentFiles start");
        File file1 = File.createTempFile(UUID.randomUUID().toString(), "txt");
        FileUtils.writeStringToFile(file1,
                RandomStringUtils.randomAlphanumeric(1000));
        file1.deleteOnExit();

        File file2 = File.createTempFile(UUID.randomUUID().toString(), "txt");
        FileUtils.writeStringToFile(file2,
                RandomStringUtils.randomAlphanumeric(1000));
        file2.deleteOnExit();
                
        final BatchFile batchFile1 = getBatchFile(file1);
        final BatchFile batchFile2 = getBatchFile(file2);
        
        final List<Thread> threadsThatEnteredProcessing = new ArrayList<Thread>();
       
        final CdusBatchUploadReaderBean bean1 = mock(CdusBatchUploadReaderBean.class);
        when(bean1.validateBatchData(any(BatchFile.class))).thenCallRealMethod();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                threadsThatEnteredProcessing.add(Thread.currentThread());
                Thread.sleep(5000);
                threadsThatEnteredProcessing.remove(Thread.currentThread());
                return null;
            }
        }).when(bean1).batchFileProcessing(any(List.class),
                any(BatchFile.class), any(String.class), any(File.class));
        
        final CdusBatchUploadReaderBean bean2 = mock(CdusBatchUploadReaderBean.class);
        when(bean2.validateBatchData(any(BatchFile.class))).thenCallRealMethod();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                threadsThatEnteredProcessing.add(Thread.currentThread());
                Thread.sleep(5000);
                threadsThatEnteredProcessing.remove(Thread.currentThread());
                return null;
            }
        }).when(bean2).batchFileProcessing(any(List.class),
                any(BatchFile.class), any(String.class), any(File.class));
        
        final Thread t1 = new Thread(new Runnable() {            
            @Override
            public void run() {
                try {
                    bean1.validateBatchData(batchFile1);
                } catch (IOException e) {                  
                }
            }
        });
        
        final Thread t2 = new Thread(new Runnable() {            
            @Override
            public void run() {
                try {
                    bean2.validateBatchData(batchFile2);
                } catch (IOException e) {                  
                }
            }
        });
        t1.setDaemon(true);
        t2.setDaemon(true);
        
        t1.start();
        Thread.sleep(1000L);
        t2.start();
        Thread.sleep(2000L);
        
        assertEquals(2, threadsThatEnteredProcessing.size());
        assertEquals(t1, threadsThatEnteredProcessing.get(0));        
        assertEquals(t2, threadsThatEnteredProcessing.get(1));
        
       
    }



    private void setStudyProtocolSvc() throws PAException {        
        StudyProtocolServiceRemote spSvc = mock(StudyProtocolServiceRemote.class);
        when(spSvc.loadStudyProtocol(any(Ii.class))).thenAnswer(new Answer<StudyProtocolDTO>() {
        	public StudyProtocolDTO answer(InvocationOnMock invocation) throws Throwable {
        		StudyProtocolDTO dto = new StudyProtocolDTO();
        		dto.setProprietaryTrialIndicator(BlConverter.convertToBl(true));
        		dto.setStudyProtocolType(StConverter.convertToSt(InterventionalStudyProtocol.class.getSimpleName()));
                Set<Ii> secondaryIdentifiers =  new HashSet<Ii>();
        		Ii spSecId = new Ii();
        		spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        		dto.setIdentifier(abbreviatedIi);
        		dto.setStatusCode(CdConverter.convertToCd(ActStatusCode.ACTIVE));
                dto.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.PREVENTION));
        		spSecId.setExtension("NCI-2009-00003");
        		secondaryIdentifiers.add(spSecId);
        		dto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(secondaryIdentifiers));
        		return dto;
        	}
            });
        when(paSvcLocator.getStudyProtocolService()).thenReturn(spSvc);
	}
    
    private BatchFile getBatchFile(File file) {
        BatchFile bf = new BatchFile();
        bf.setFileLocation(file.getAbsolutePath());
        RegistryUser ru = TestSchema.registryUsers.get(0);
        ru.setFamilyAccrualSubmitter(false);
        ru.setSiteAccrualSubmitter(true);
        bf.setSubmitter(ru);
        bf.setUserLastCreated(TestSchema.registryUsers.get(0).getCsmUser());
        bf.setSubmissionTypeCode(AccrualSubmissionTypeCode.BATCH);
        TestSchema.addUpdObject(bf);
        return bf;
    }

    private void verifyEmailsSent(int errorCount, int confirmationCount, int exceptionCount) {
        verify(mailService, times(errorCount)).sendMailWithHtmlBody(anyString(), contains("accrual.error.subject"), anyString());
        verify(mailService, times(confirmationCount)).sendMailWithHtmlBody(anyString(), contains("accrual.confirmation.subject"), anyString());
        verify(mailService, times(exceptionCount)).sendMailWithHtmlBody(anyString(), contains("accrual.exception.subject"), anyString());
    }

    private BatchFile getResultFromDb() {
        Session session = PaHibernateUtil.getCurrentSession();
        session.clear();
        String hql = "from BatchFile bf join fetch bf.accrualCollections ac order by bf.id desc";
        Query query = session.createQuery(hql);
        List<BatchFile> queryList = query.list();
        return queryList.get(0);
    }
}

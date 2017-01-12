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
package gov.nih.nci.accrual.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.SubjectAccrualDTO;
import gov.nih.nci.accrual.dto.util.SubjectAccrualKey;
import gov.nih.nci.accrual.service.StudySubjectServiceLocal;
import gov.nih.nci.accrual.service.exception.IndexedInputValidationException;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.ServiceLocatorPaInterface;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceRemote;
import gov.nih.nci.pa.service.util.AccrualDiseaseTerminologyServiceRemote;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author merenkoi
 */
public class SubjectAccrualValidatorTest {
    
    private SubjectAccrualValidatorBean bean;
    
    @Before
    public void setUp() {
        bean = new SubjectAccrualValidatorBean();
    }

    @Test
    public void validateInvalidIdFails() throws PAException {
        StudySubjectServiceLocal studySubjectService = mock(StudySubjectServiceLocal.class);
        when(studySubjectService.get(any(Ii.class))).thenReturn(null);
        bean.setStudySubjectService(studySubjectService);
        SubjectAccrualDTO subjectAccrual = createSubjectAccrualDTO();
        try {
            bean.validateNoStudySubjectDuplicates(subjectAccrual, 1);
            fail();
        } catch (IndexedInputValidationException e) {
            assertEquals("Subject identifier not found.", e.getMessage());
        }
    }

    @Test
    public void validateNoStudySubjectDuplicatesPasses() throws PAException {
        StudySubjectServiceLocal studySubjectService = mock(StudySubjectServiceLocal.class);
        StudySubject subject = new StudySubject();
        subject.setStatusCode(FunctionalRoleStatusCode.NULLIFIED);
        when(studySubjectService.get(any(SubjectAccrualKey.class))).thenReturn(subject);
        bean.setStudySubjectService(studySubjectService);
        SubjectAccrualDTO subjectAccrual = createSubjectAccrualDTO();
        subjectAccrual.setIdentifier(null);
        bean.validateNoStudySubjectDuplicates(subjectAccrual, 1);    

        when(studySubjectService.get(any(SubjectAccrualKey.class))).thenReturn(null);
        bean.validateNoStudySubjectDuplicates(subjectAccrual, 1);    
    }

    @Test(expected=IndexedInputValidationException.class)
    public void validateNoStudySubjectDuplicatesFails() throws PAException {
        StudySubjectServiceLocal studySubjectService = mock(StudySubjectServiceLocal.class);
        StudySubject subject = new StudySubject();
        subject.setId(-1L);
        subject.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        when(studySubjectService.get(any(SubjectAccrualKey.class))).thenReturn(subject);
        bean.setStudySubjectService(studySubjectService);
        SubjectAccrualDTO subjectAccrual = createSubjectAccrualDTO();
        subjectAccrual.setIdentifier(null);
        bean.validateNoStudySubjectDuplicates(subjectAccrual, 1); 
    }

    @Test
    public void validateRaces() throws Exception {
        SubjectAccrualDTO subject = createSubjectAccrualDTO();
        StringBuffer errors = new StringBuffer();
        bean.validateRaces(subject, errors);
        assertEquals("Race is a required field.\n", errors.toString());

        DSet<Cd> races = new DSet<Cd>();
        Set<Cd> raceSet = new HashSet<Cd>();
        races.setItem(raceSet);
        subject.setRace(races);
        errors = new StringBuffer();
        bean.validateRaces(subject, errors);
        assertEquals("Race is a required field.\n", errors.toString());

        raceSet.add(CdConverter.convertStringToCd("Asian"));
        errors = new StringBuffer();
        bean.validateRaces(subject, errors);
        assertTrue(errors.length() == 0);

        raceSet.clear();
        raceSet.add(CdConverter.convertStringToCd("05"));
        raceSet.add(CdConverter.convertStringToCd("01"));
        bean.validateRaces(subject, errors);
        assertTrue(errors.length() == 0);
        
        raceSet.clear();
        raceSet.add(CdConverter.convertStringToCd("A"));
        bean.validateRaces(subject, errors);
        assertEquals("A is not a valid value for Race Code.\n", errors.toString());
    }

    @Test
    public void validateGenderAndEthnicity() throws Exception {
        SubjectAccrualDTO subject = createSubjectAccrualDTO();
        StringBuffer errors = new StringBuffer();
        bean.validateGenderAndEthnicity(subject, errors);
        assertEquals("Gender is a required field.\n" 
                + "Ethnicity is a required field.\n", errors.toString());
        
        subject.setGender(CdConverter.convertStringToCd("Male"));
        subject.setEthnicity(CdConverter.convertStringToCd("Unknown"));
        errors = new StringBuffer();
        bean.validateGenderAndEthnicity(subject, errors);
        assertTrue(errors.length() == 0);

        subject.setGender(CdConverter.convertStringToCd("1"));
        subject.setEthnicity(CdConverter.convertStringToCd("9"));
        bean.validateGenderAndEthnicity(subject, errors);
        assertTrue(errors.length() == 0);

        subject.setGender(CdConverter.convertStringToCd("A"));
        subject.setEthnicity(CdConverter.convertStringToCd("B"));
        bean.validateGenderAndEthnicity(subject, errors);
        assertEquals("A is not a valid value for Gender.\nB is not a valid value for Ethnicity.\n", errors.toString());
    }

    @Test
    public void validateDatesAndPaymentMethod() throws Exception {
        SubjectAccrualDTO subject = createSubjectAccrualDTO();
        StringBuffer errors = new StringBuffer();
        bean.validateDatesAndPaymentMethod(subject, errors);
        assertEquals("Birth Date is a required field.\nRegistration Date is a required field.\n", errors.toString());

        subject.setBirthDate(TsConverter.convertToTs(new Date()));
        subject.setRegistrationDate(TsConverter.convertToTs(new Date()));
        errors = new StringBuffer();
        bean.validateDatesAndPaymentMethod(subject, errors);
        assertEquals("", errors.toString());

        subject.setPaymentMethod(CdConverter.convertStringToCd("6A"));
        bean.validateDatesAndPaymentMethod(subject, errors);
        assertEquals("", errors.toString());
        
        subject.setPaymentMethod(CdConverter.convertStringToCd("A"));
        bean.validateDatesAndPaymentMethod(subject, errors);
        assertEquals("A is not a valid value for Payment Method Code.\n", errors.toString());
    }
    
    @Test
    public void validateParticipatingSite() throws Exception {
    	 ServiceLocatorPaInterface serviceLocatorPaInterface = mock(ServiceLocatorPaInterface.class);

        PaServiceLocator.getInstance().setServiceLocator(
                serviceLocatorPaInterface);
        StudySiteServiceRemote studySiteServiceRemote = mock(StudySiteServiceRemote.class);
        when(serviceLocatorPaInterface.getStudySiteService()).thenReturn(
                studySiteServiceRemote);
         SubjectAccrualDTO subject = createSubjectAccrualDTO();
         StringBuffer errors = new StringBuffer();
         bean.validateDiseaseAndParticipatingSite(subject, errors);
         assertEquals("1 is not a valid value for Participating Site Identifier.\n", errors.toString());
    }
    
    @Test
    public void validateDiseaseIdentifier() throws Exception {
        ServiceLocatorPaInterface serviceLocatorPaInterface = mock(ServiceLocatorPaInterface.class);

        PaServiceLocator.getInstance().setServiceLocator(
                serviceLocatorPaInterface);
        StudySiteServiceRemote studySiteServiceRemote = mock(StudySiteServiceRemote.class);
        AccrualDiseaseTerminologyServiceRemote adtsRemote = mock(AccrualDiseaseTerminologyServiceRemote.class);
        when(serviceLocatorPaInterface.getStudySiteService()).thenReturn(
                studySiteServiceRemote);
        StudySiteDTO studySite = new StudySiteDTO();
        studySite.setIdentifier(IiConverter.convertToIi(1L));;
        when(studySiteServiceRemote.get(any(Ii.class))).thenReturn(studySite);
        when(serviceLocatorPaInterface.getAccrualDiseaseTerminologyService()).thenReturn(adtsRemote);
        when(adtsRemote.getCodeSystem(any(Long.class))).thenReturn("ICD-O-3");
        AccrualDiseaseServiceLocal diseaseService = mock(AccrualDiseaseServiceLocal.class);
        AccrualDisease disease = new AccrualDisease();
        disease.setId(1L);
        disease.setDiseaseCode("141.0");
        disease.setCodeSystem("ICD9");
        when(diseaseService.get(any(Ii.class))).thenReturn(disease);
        bean.setDiseaseService(diseaseService);
         SubjectAccrualDTO subject = createSubjectAccrualDTO();
         StringBuffer errors = new StringBuffer();
         bean.validateDiseaseAndParticipatingSite(subject, errors);
         assertEquals("The subject's disease's coding system ICD9 is different from the one used on the trial: ICD-O-3", errors.toString());
         
         when(diseaseService.get(any(Ii.class))).thenReturn(null);
         bean.setDiseaseService(diseaseService);
         subject = createSubjectAccrualDTO();
         errors = new StringBuffer();
          bean.validateDiseaseAndParticipatingSite(subject, errors);
          assertEquals("234 is not a valid value for Disease Identifier.\n", errors.toString());
          

          
          subject = createSubjectAccrualDTO();
          subject.setDiseaseIdentifier(IiConverter.convertToIi("not found"));
          errors = new StringBuffer();
           bean.validateDiseaseAndParticipatingSite(subject, errors);
           assertEquals("Disease code does not exist for given Disease code System.", errors.toString());
    }

    private StudySubjectDto createStudySubjectDto() {
        StudySubjectDto result = new StudySubjectDto();
        St assignedIdentifier = new St();
        assignedIdentifier.setValue("232323");
        result.setAssignedIdentifier(assignedIdentifier);
        Ii identifier = new Ii();
        identifier.setExtension("111");
        identifier.setRoot("root");
        result.setIdentifier(identifier);
        result.setStatusCode(CdConverter.convertStringToCd(FunctionalRoleStatusCode.ACTIVE.getCode()));        
        return result;        
    }
    
    private SubjectAccrualDTO createSubjectAccrualDTO() {
        SubjectAccrualDTO result = new SubjectAccrualDTO();
        St assignedIdentifier = new St();
        assignedIdentifier.setValue("232323");
        result.setAssignedIdentifier(assignedIdentifier);
        Ii identifier = new Ii();
        identifier.setExtension("111");
        identifier.setRoot("root");
        result.setIdentifier(identifier);
        result.setParticipatingSiteIdentifier(IiConverter.convertToStudySiteIi(1L));
        result.setDiseaseIdentifier(IiConverter.convertToStudySiteIi(234L));
        return result;
        
    }
    
 }

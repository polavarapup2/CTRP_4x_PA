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
package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO.ResponsiblePartyType;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.lov.Lov;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyInboxServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingService.Method;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.EntityDto;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.ejb.SessionContext;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fiveamsolutions.nci.commons.authentication.CommonsGridLoginModule;


/**
 * @author Michael Visee
 */
public class TrialRegistrationValidatorTest {
    private TrialRegistrationValidator validator;
    private CSMUserUtil csmUserUtil = mock(CSMUserUtil.class);
    private LookUpTableServiceRemote lookUpTableServiceRemote = mock(LookUpTableServiceRemote.class);
    private PAServiceUtils paServiceUtils = mock(PAServiceUtils.class);
    private RegistryUserServiceLocal registryUserServiceLocal = mock(RegistryUserServiceLocal.class);
    private StudyInboxServiceLocal studyInboxServiceLocal = mock(StudyInboxServiceLocal.class);
    private StudyIndldeServiceLocal studyIndldeService = mock(StudyIndldeServiceLocal.class);
    private StudyOverallStatusServiceLocal studyOverallStatusService = mock(StudyOverallStatusServiceLocal.class);
    private StudyProtocolServiceLocal studyProtocolService = mock(StudyProtocolServiceLocal.class);
    private StudyResourcingServiceLocal studyResourcingService = mock(StudyResourcingServiceLocal.class);
    private StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
    private StringBuilder errorMsg = new StringBuilder();
    
    /**
     * Exception rule.
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Initialization method.
     */
    @Before
    public void init() {
        CommonsGridLoginModule.setGridServicePrincipalSeparator("||");
        Principal princ = mock(Principal.class);
        when(princ.getName()).thenReturn("Grid||User");
        SessionContext ctx = mock(SessionContext.class);
        when(ctx.getCallerPrincipal()).thenReturn(princ);
        validator = new TrialRegistrationValidator(ctx);
        validator.setCsmUserUtil(csmUserUtil);
        validator.setLookUpTableServiceRemote(lookUpTableServiceRemote);
        validator.setPaServiceUtils(paServiceUtils);
        validator.setRegistryUserServiceLocal(registryUserServiceLocal);
        validator.setStudyInboxServiceLocal(studyInboxServiceLocal);
        validator.setStudyIndldeService(studyIndldeService);
        validator.setStudyOverallStatusService(studyOverallStatusService);
        validator.setStudyProtocolService(studyProtocolService);        
        validator.setStudyResourcingService(studyResourcingService);
    }

    private void checkErrorMsg(String expectedMessage) {
        assertEquals("Wrong error message", expectedMessage, errorMsg.toString());
    }

    /**
     * Test the validateUpdate method.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateUpdate() throws PAException {
        validator = mock(TrialRegistrationValidator.class);
        Ii spIi = IiConverter.convertToIi(1L);
        studyProtocolDTO.setIdentifier(spIi);
        StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
        List<StudyResourcingDTO> studyResourcingDTOs = new ArrayList<StudyResourcingDTO>();
        List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();        
        List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs = new ArrayList<StudySiteAccrualStatusDTO>();
        
        PAServiceUtils paServiceUtilsMock = mock(PAServiceUtils.class);
        when(paServiceUtilsMock.getCtepOrDcpId(any(Long.class), any(String.class))).thenReturn("DCPID");
        when(validator.getPaServiceUtils()).thenReturn(paServiceUtilsMock);
        
        doCallRealMethod().when(validator).validateUpdate(studyProtocolDTO, overallStatusDTO, null, studyResourcingDTOs,
                                                          documentDTOs, studySiteAccrualStatusDTOs);
        validator.validateUpdate(studyProtocolDTO, overallStatusDTO, null, studyResourcingDTOs, documentDTOs,
                                 studySiteAccrualStatusDTOs);
        verify(validator).validateUser(eq(studyProtocolDTO), eq("Update"), eq(true), (StringBuilder) any());
        verify(validator).validateStatusAndDates(eq(studyProtocolDTO),
                eq(overallStatusDTO), eq((List) null), eq(true),
                (StringBuilder) any());
        verify(validator).validateNihGrants(eq(studyProtocolDTO), eq((OrganizationDTO) null), eq(studyResourcingDTOs), (StringBuilder) any());
        verify(validator).validateDWFS(eq(spIi), eq(TrialRegistrationValidator.ERROR_DWFS_FOR_UPDATE),
                                       eq(TrialRegistrationValidator.ERROR_MESSAGE_DWFS_FOR_UPDATE),
                                       (StringBuilder) any());
        verify(validator).validateExistingStatus(eq(spIi), (StringBuilder) any());
        verify(validator).validateDocuments(eq(documentDTOs), (StringBuilder) any());
        verify(validator).validateParticipatingSites(eq(studyProtocolDTO), eq(studySiteAccrualStatusDTOs),
                                                     (StringBuilder) any());
    }
    
    /**
     * test the validateUser method when not user is provided.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateUserNoUser() throws PAException {
        validator.validateUser(studyProtocolDTO, "", true, errorMsg);
        checkErrorMsg("Submitter is required.");
    }
    
    /**
     * test the validateUser method when the provided user does not exist.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateUserUserNotFound() throws PAException {
        studyProtocolDTO.setUserLastCreated(StConverter.convertToSt("user"));
        validator.validateUser(studyProtocolDTO, "", true, errorMsg);
        checkErrorMsg("Submitter user does not exist. Please do self register in CTRP.");
    }
    
    /**
     * test the validateUser method when the provided user exists but can not access the trial.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateUserNoAccess() throws PAException {
        User user = new User();
        when(csmUserUtil.getCSMUser("user")).thenReturn(user);
        when(registryUserServiceLocal.hasTrialAccess("user", 1L)).thenReturn(false);
        studyProtocolDTO.setIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        studyProtocolDTO.setUserLastCreated(StConverter.convertToSt("user"));
        validator.validateUser(studyProtocolDTO, "Operation", true, errorMsg);
        checkErrorMsg("Operation to the trial can only be submitted by either an owner of the trial"
                                + " or a lead organization admin.\n");
    }
    
    /**
     * test the validateUser method when the provided user exists and can access the trial.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateUserAccess() throws PAException {
        User user = new User();
        when(csmUserUtil.getCSMUser("user")).thenReturn(user);
        when(registryUserServiceLocal.hasTrialAccess("user", 1L)).thenReturn(true);
        studyProtocolDTO.setIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        studyProtocolDTO.setUserLastCreated(StConverter.convertToSt("user"));
        validator.validateUser(studyProtocolDTO, "Operation", true, errorMsg);
        checkErrorMsg("");
    }
    
    /**
     * test the validateUser method when the provided user exists and no access check is done.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateUserNoAccessCheck() throws PAException {
        User user = new User();
        when(csmUserUtil.getCSMUser("user")).thenReturn(user);
        studyProtocolDTO.setIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        studyProtocolDTO.setUserLastCreated(StConverter.convertToSt("user"));
        validator.validateUser(studyProtocolDTO, "Operation", false, errorMsg);
        verify(registryUserServiceLocal, never()).hasTrialAccess("user", 1L);
        checkErrorMsg("");
    }
    
    /**
     * test the validateUser method when services throw an exception.
     * @throws PAException if an error occurs
     */
    @Test(expected=PAException.class)
    public void testValidateUserException() throws PAException {
        when(csmUserUtil.getCSMUser("user")).thenThrow(new PAException());
        studyProtocolDTO.setUserLastCreated(StConverter.convertToSt("user"));
        validator.validateUser(studyProtocolDTO, "Operation", true, errorMsg);
    }
    
    @Test
    public void testOnlyDcpTrialCanHavePcdAsNA() {
        validator = new TrialRegistrationValidator(null);
        studyProtocolDTO
                .setPrimaryCompletionDate(TsConverter.convertToTs(null));
        studyProtocolDTO.setPrimaryCompletionDateTypeCode(CdConverter
                .convertToCd(ActualAnticipatedTypeCode.NA));

        StringBuilder sb = new StringBuilder();
        assertFalse(validator.validateStudyProtocolDates(studyProtocolDTO,
                false, sb));
        assertTrue(sb
                .toString()
                .contains(
                        "Only a DCP trial can have a Primary Completion Date Type equals to 'N/A'. "));
    }
    
    @Test
    public void testPcdMustBeNullIfNA() {
        validator = new TrialRegistrationValidator(null);
        studyProtocolDTO
                .setPrimaryCompletionDate(TsConverter.convertToTs(new Date()));
        studyProtocolDTO.setPrimaryCompletionDateTypeCode(CdConverter
                .convertToCd(ActualAnticipatedTypeCode.NA));

        StringBuilder sb = new StringBuilder();
        assertFalse(validator.validateStudyProtocolDates(studyProtocolDTO,
                true, sb));
        assertTrue(sb
                .toString()
                .contains(
                        "When the Primary Completion Date Type is set to 'N/A', "
                                + "the Primary Completion Date must be null. "));
    }
    
    /**
     * test the validateStatusAndDates method with invalid results from the other methods.
     */
    @Test
    public void testValidateStatusAndDatesInvalid() {
        StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
        validator = mock(TrialRegistrationValidator.class);
        doCallRealMethod().when(validator).validateStatusAndDates(studyProtocolDTO, overallStatusDTO, null, false, errorMsg);
        validator.validateStatusAndDates(studyProtocolDTO, overallStatusDTO, null, false,errorMsg);
        verify(validator, never()).validateOverallStatus(studyProtocolDTO, overallStatusDTO, errorMsg);
    }
    
    /**
     * test the validateStatusAndDates method with valid results from the other methods.
     */
    @Test
    public void testValidateStatusAndDatesValid() {
        StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
        validator = mock(TrialRegistrationValidator.class);
        doCallRealMethod().when(validator).validateStatusAndDates(studyProtocolDTO, overallStatusDTO, null, false,errorMsg);
        when(validator.validateStudyProtocolDates(studyProtocolDTO,false, errorMsg)).thenReturn(true);
        when(validator.validateOverallStatusFields(overallStatusDTO, null, errorMsg)).thenReturn(true);
        validator.validateStatusAndDates(studyProtocolDTO, overallStatusDTO, null, false, errorMsg);
        verify(validator).validateOverallStatus(studyProtocolDTO, overallStatusDTO, errorMsg);
    }
    
    /**
     * test the validateStudyProtocolDates method with no data.
     */
    @Test
    public void testValidateStudyProtocolDatesNodata() {
        boolean result = validator.validateStudyProtocolDates(studyProtocolDTO, false, errorMsg);
        assertFalse("Validation should have failed", result);
        checkErrorMsg("Trial Start Date Type cannot be null. Primary Completion Date Type cannot be null. Primary Completion Date cannot be null. " 
                + "Trial Start Date cannot be null. ");
    }
    
    /**
     * test the validateStudyProtocolDates method with all fields null.
     */
    @Test
    public void testValidateStudyProtocolDatesNulldata() {
        studyProtocolDTO.setStartDate(TsConverter.convertToTs(null));
        studyProtocolDTO.setStartDateTypeCode(CdConverter.convertToCd((Lov) null));
        studyProtocolDTO.setPrimaryCompletionDate(TsConverter.convertToTs(null));
        studyProtocolDTO.setPrimaryCompletionDateTypeCode(CdConverter.convertToCd((Lov) null));
        boolean result = validator.validateStudyProtocolDates(studyProtocolDTO, false, errorMsg);
        assertFalse("Validation should have failed", result);
        checkErrorMsg("Trial Start Date Type cannot be null. Primary Completion Date Type cannot be null. Primary Completion Date cannot be null. " 
                + "Trial Start Date cannot be null. ");
    }
    
    /**
     * test the validateStudyProtocolDates method with all fields valid.
     */
    @Test
    public void testValidateStudyProtocolDatesValid() {
        studyProtocolDTO.setStartDate(TsConverter.convertToTs(new Date()));
        studyProtocolDTO.setStartDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.ACTUAL));
        studyProtocolDTO.setPrimaryCompletionDate(TsConverter.convertToTs(new Date()));
        studyProtocolDTO.setPrimaryCompletionDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.ACTUAL));
        boolean result = validator.validateStudyProtocolDates(studyProtocolDTO, false, errorMsg);
        assertTrue("Validation should have scucceeded", result);
        checkErrorMsg("");
    }
    
    /**
     * test the validateOverallStatusFields method with no status object.
     */
    @Test
    public void testValidateOverallStatusFieldsNoStatus() {
        boolean result = validator.validateOverallStatusFields(null, null, errorMsg);
        assertFalse("Validation should have failed", result);
        checkErrorMsg("Overall Status cannot be null. ");
    }
    
    /**
     * test the validateOverallStatusFields method with a status object containing no data.
     */
    @Test
    public void testValidateOverallStatusFieldsNoData() {
        StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
        boolean result = validator.validateOverallStatusFields(overallStatusDTO, null, errorMsg);
        assertFalse("Validation should have failed", result);
        checkErrorMsg("Current Trial Status cannot be null. Current Trial Status Date cannot be null. ");
    }
    
    /**
     * test the validateOverallStatusFields method with a status object containing valid data.
     */
    @Test
    public void testValidateOverallStatusFieldsValid() {
        StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
        overallStatusDTO.setStatusCode(CdConverter.convertToCd(StudyStatusCode.ACTIVE));
        overallStatusDTO.setStatusDate(TsConverter.convertToTs(new Date()));
        boolean result = validator.validateOverallStatusFields(overallStatusDTO, null, errorMsg);
        assertTrue("Validation should have scucceeded", result);
        checkErrorMsg("");
    }
    
    /**
     * test the validateOverallStatus method when the studyOverallStatusService generates an exception.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateOverallStatusException() throws PAException {
        StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((StringBuilder)invocation.getArguments()[2]).append("ERROR");
                return null;
            }
        }).when(studyOverallStatusService).validate(overallStatusDTO,
                studyProtocolDTO, errorMsg);       
        
        validator.validateOverallStatus(studyProtocolDTO, overallStatusDTO, errorMsg);
        verify(studyOverallStatusService).validate(overallStatusDTO, studyProtocolDTO, errorMsg);
        
        assertTrue(errorMsg.toString().equals("ERROR"));
        
    }
    
    /**
     * test the validateOverallStatus method when the studyOverallStatusService generates no exception.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateOverallStatusValid() throws PAException {
        StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
        validator.validateOverallStatus(studyProtocolDTO, overallStatusDTO, errorMsg);
        verify(studyOverallStatusService).validate(overallStatusDTO, studyProtocolDTO, errorMsg);
        assertTrue(errorMsg.length()==0);
    }
    
    /**
     * test the validateNihGrants method with no grant.
     */
    @Test
    public void testValidateNihGrantsEmpty() {
        validator.validateNihGrants(studyProtocolDTO, null, null, errorMsg);
        checkErrorMsg("");
    }

    /**
     * test the validateNihGrants method for invalid grants.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateNihGrantsInvalid() throws PAException {
        StudyResourcingDTO studyResourcingDTO = new StudyResourcingDTO();
        List<StudyResourcingDTO> studyResourcingDTOs = new ArrayList<StudyResourcingDTO>();
        studyResourcingDTOs.add(studyResourcingDTO);
        doThrow(new PAException("PAException")).when(studyResourcingService).validate(Method.SERVICE, null, null, null, studyResourcingDTOs);
        validator.validateNihGrants(studyProtocolDTO, null, studyResourcingDTOs, errorMsg);
        verify(studyResourcingService).validate(Method.SERVICE, null, null, null, studyResourcingDTOs);
        verify(paServiceUtils).enforceNoDuplicateGrants(studyResourcingDTOs);
        checkErrorMsg("PAException");
    }

    /**
     * test the validateNihGrants method for duplicate grants exception.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateNihGrantsDuplicate() throws PAException {
        StudyResourcingDTO studyResourcingDTO = new StudyResourcingDTO();
        List<StudyResourcingDTO> studyResourcingDTOs = new ArrayList<StudyResourcingDTO>();
        studyResourcingDTOs.add(studyResourcingDTO);
        doThrow(new PAException("Duplicate grants are not allowed.")).when(paServiceUtils).enforceNoDuplicateGrants(studyResourcingDTOs);
        validator.validateNihGrants(studyProtocolDTO, null, studyResourcingDTOs, errorMsg);
        verify(studyResourcingService).validate(Method.SERVICE, null, null, null, studyResourcingDTOs);
        verify(paServiceUtils).enforceNoDuplicateGrants(studyResourcingDTOs);
        checkErrorMsg("Duplicate grants are not allowed.");
    }

    /**
     * test the validateNihGrants method for valid case.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateNihGrantsValid() throws PAException {
        StudyResourcingDTO studyResourcingDTO = new StudyResourcingDTO();
        List<StudyResourcingDTO> studyResourcingDTOs = new ArrayList<StudyResourcingDTO>();
        studyResourcingDTOs.add(studyResourcingDTO);
        validator.validateNihGrants(studyProtocolDTO, null, studyResourcingDTOs, errorMsg);
        verify(studyResourcingService).validate(Method.SERVICE, null, null, null, studyResourcingDTOs);
        verify(paServiceUtils).enforceNoDuplicateGrants(studyResourcingDTOs);
        checkErrorMsg("");
    }
    
    /**
     * test the validateDocuments method with no document
     */
    @Test
    public void testValidateDocumentsValid() {
        List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTOs.add(documentDTO);
        Ii docIi = IiConverter.convertToDocumentIi(1L);
        documentDTO.setIdentifier(docIi);
        when(paServiceUtils.isIiExistInPA(docIi)).thenReturn(true);
        validator.validateDocuments(documentDTOs, errorMsg);
        verify(paServiceUtils).isIiExistInPA(docIi);
        checkErrorMsg("");
    }
    
    /**
     * test the validateDocuments method with no document
     */
    @Test
    public void testValidateDocumentsExist() {
        List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTOs.add(documentDTO);
        Ii docIi = IiConverter.convertToDocumentIi(1L);
        documentDTO.setIdentifier(docIi);
        when(paServiceUtils.isIiExistInPA(docIi)).thenReturn(false);
        validator.validateDocuments(documentDTOs, errorMsg);
        verify(paServiceUtils).isIiExistInPA(docIi);
        checkErrorMsg("Document id 1 does not exist.");
    }
    
    /**
     * test the validateDocuments method with no document
     */
    @Test
    public void testValidateDocumentsEmpty() {
        validator.validateDocuments(null, errorMsg);
        checkErrorMsg("");
    }
    
    /**
     * test the validateParticipatingSites method with no site
     * @throws PAException if an error occurs
     */
    @Test
    public void testvalidateParticipatingSitesEmpty() throws PAException {
        validator.validateParticipatingSites(studyProtocolDTO, null, errorMsg);
        checkErrorMsg("");
    }
    
    /**
     * test the validateParticipatingSites method with valid site
     * @throws PAException if an error occurs
     */
    @Test
    public void testvalidateParticipatingSitesValid() throws PAException {
        Ii spIi = IiConverter.convertToIi(1L);
        studyProtocolDTO.setIdentifier(spIi);
        List<StudySiteAccrualStatusDTO> siteDTOs = new ArrayList<StudySiteAccrualStatusDTO>();
        StudySiteAccrualStatusDTO dto = new StudySiteAccrualStatusDTO();
        siteDTOs.add(dto);
        StudyOverallStatusDTO recruitmentStatusDto = new StudyOverallStatusDTO();
        when(studyOverallStatusService.getCurrentByStudyProtocol(spIi)).thenReturn(recruitmentStatusDto);
        validator.validateParticipatingSites(studyProtocolDTO, siteDTOs, errorMsg);
        verify(studyOverallStatusService).getCurrentByStudyProtocol(spIi);
        verify(paServiceUtils).enforceRecruitmentStatus(studyProtocolDTO, siteDTOs, recruitmentStatusDto);
        checkErrorMsg("");
    }
    
    /**
     * test the validateParticipatingSites method with invalid site
     * @throws PAException if an error occurs
     */
    @Test
    public void testvalidateParticipatingSitesInvalid() throws PAException {
        Ii spIi = IiConverter.convertToIi(1L);
        studyProtocolDTO.setIdentifier(spIi);
        List<StudySiteAccrualStatusDTO> siteDTOs = new ArrayList<StudySiteAccrualStatusDTO>();
        StudySiteAccrualStatusDTO dto = new StudySiteAccrualStatusDTO();
        siteDTOs.add(dto);
        StudyOverallStatusDTO recruitmentStatusDto = new StudyOverallStatusDTO();
        when(studyOverallStatusService.getCurrentByStudyProtocol(spIi)).thenReturn(recruitmentStatusDto);
        doThrow(new PAException("PAException")).when(paServiceUtils).enforceRecruitmentStatus(studyProtocolDTO, siteDTOs, recruitmentStatusDto);
        validator.validateParticipatingSites(studyProtocolDTO, siteDTOs, errorMsg);
        verify(studyOverallStatusService).getCurrentByStudyProtocol(spIi);
        verify(paServiceUtils).enforceRecruitmentStatus(studyProtocolDTO, siteDTOs, recruitmentStatusDto);
        checkErrorMsg("PAException");
    }
    
    /**
     * test the validateParticipatingSites method when the service generates an exception
     * @throws PAException if an error occurs
     */
    @Test(expected = PAException.class)
    public void testvalidateParticipatingSitesException() throws PAException {
        Ii spIi = IiConverter.convertToIi(1L);
        studyProtocolDTO.setIdentifier(spIi);
        List<StudySiteAccrualStatusDTO> siteDTOs = new ArrayList<StudySiteAccrualStatusDTO>();
        StudySiteAccrualStatusDTO dto = new StudySiteAccrualStatusDTO();
        siteDTOs.add(dto);
        doThrow(new PAException("PAException")).when(studyOverallStatusService).getCurrentByStudyProtocol(spIi);
        validator.validateParticipatingSites(studyProtocolDTO, siteDTOs, errorMsg);
    }
    
    /**
     * test the validateSummary4SponsorAndCategory method with no StudyResourcing
     * @throws PAException if an error occurs
     */
    @Test
    public void testvalidateSummary4SponsorAndCategoryNoOrganization() throws PAException {
        validator = mock(TrialRegistrationValidator.class);
        doCallRealMethod().when(validator).validateSummary4SponsorAndCategory(studyProtocolDTO, null, null);
        try {
            validator.validateSummary4SponsorAndCategory(studyProtocolDTO, null, null);
            fail("PAException should have been thrown");
        } catch (PAException e) {
            assertEquals("Wrong exception message", "Validation Exception Summary Four Organization cannot be null, ",
                         e.getMessage());
            verify(validator).validateSummary4Resourcing(eq(studyProtocolDTO), (StudyResourcingDTO) isNull(),
                                                         any(StringBuilder.class));
        }
    }

    /**
     * test the validateSummary4SponsorAndCategory method with valid data
     * @throws PAException if an error occurs
     */
    @Test
    public void testvalidateSummary4SponsorAndCategoryValid() throws PAException {
        OrganizationDTO organization = new OrganizationDTO();
        StudyResourcingDTO studyResourcingDTO = new StudyResourcingDTO();
        validator = mock(TrialRegistrationValidator.class);
        doCallRealMethod().when(validator).validateSummary4SponsorAndCategory(studyProtocolDTO, organization,
                                                                              studyResourcingDTO);
        validator.validateSummary4SponsorAndCategory(studyProtocolDTO, organization, studyResourcingDTO);
        verify(validator).validateSummary4Resourcing(eq(studyProtocolDTO), eq(studyResourcingDTO),
                                                     any(StringBuilder.class));
    }
    
    /**
     * test the validateSummary4Resourcing method with no studyResourcing
     */
    @Test
    public void testValidateSummary4ResourcingNoResourcing() {
        validator.validateSummary4Resourcing(studyProtocolDTO, null, errorMsg);
        checkErrorMsg("Summary Four Study Resourcing cannot be null, ");
    }
    
    /**
     * test the validateSummary4Resourcing method with no category
     */
    @Test
    public void testValidateSummary4ResourcingNoCategory() {
        StudyResourcingDTO studyResourcingDTO = new StudyResourcingDTO();
        validator.validateSummary4Resourcing(studyProtocolDTO, studyResourcingDTO, errorMsg);
        checkErrorMsg("Summary Four Sponsor Category cannot be null, ");
    }

    /**
     * test the validateSummary4Resourcing method with valid data
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateSummary4ResourcingValid() throws PAException {
        validator = mock(TrialRegistrationValidator.class);
        StudyResourcingDTO studyResourcingDTO = new StudyResourcingDTO();
        Cd category = CdConverter.convertStringToCd(SummaryFourFundingCategoryCode.INSTITUTIONAL.getCode());
        studyResourcingDTO.setTypeCode(category);
        doCallRealMethod().when(validator).validateSummary4Resourcing(studyProtocolDTO, studyResourcingDTO, errorMsg);
        validator.validateSummary4Resourcing(studyProtocolDTO, studyResourcingDTO, errorMsg);
        verify(validator).validateSummary4Category(studyProtocolDTO, 
                                                   SummaryFourFundingCategoryCode.INSTITUTIONAL.getCode(), errorMsg);
        checkErrorMsg("");
    }
    
    /**
     * Test the validateAmendment method.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateAmend() throws PAException {
        validator = mock(TrialRegistrationValidator.class);
        Ii spIi = IiConverter.convertToIi(1L);
        studyProtocolDTO.setIdentifier(spIi);
        StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
        OrganizationDTO leadOrganizationDTO = new OrganizationDTO();
        OrganizationDTO sponsorOrganizationDTO = new OrganizationDTO();
        StudyContactDTO studyContactDTO = new StudyContactDTO();
        StudySiteContactDTO studySiteContactDTO = new StudySiteContactDTO();
        List<OrganizationDTO> summary4organizationDTO = new ArrayList<OrganizationDTO>();
        StudyResourcingDTO summary4StudyResourcingDTO = new StudyResourcingDTO();
        PersonDTO piPersonDTO = new PersonDTO();
        Ii responsiblePartyContactIi = IiConverter.convertToIi(1L);
        StudyRegulatoryAuthorityDTO studyRegAuthDTO = new StudyRegulatoryAuthorityDTO();
        List<StudyResourcingDTO> studyResourcingDTOs = new ArrayList<StudyResourcingDTO>();
        List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
        List<StudyIndldeDTO> studyIndldeDTOs = new ArrayList<StudyIndldeDTO>();
        StudySiteDTO nctIdentifierDTO = new StudySiteDTO();
        StudySiteDTO dcpIdentifierDTO = new StudySiteDTO();
        final ResponsiblePartyDTO responsiblePartyDTO = getResponsiblePartyDTO(piPersonDTO, leadOrganizationDTO);
        doCallRealMethod().when(validator).validateAmendment(studyProtocolDTO,
                overallStatusDTO, null, leadOrganizationDTO, sponsorOrganizationDTO,
                summary4organizationDTO, summary4StudyResourcingDTO,
                piPersonDTO,
                responsiblePartyDTO,
                studyRegAuthDTO, studyResourcingDTOs, documentDTOs,
                studyIndldeDTOs, nctIdentifierDTO, dcpIdentifierDTO);
        validator.validateAmendment(studyProtocolDTO, overallStatusDTO, null,
                leadOrganizationDTO, sponsorOrganizationDTO,
                summary4organizationDTO, summary4StudyResourcingDTO,
                piPersonDTO, responsiblePartyDTO, studyRegAuthDTO,
                studyResourcingDTOs, documentDTOs, studyIndldeDTOs,
                nctIdentifierDTO, dcpIdentifierDTO);
        verify(validator).validateUser(eq(studyProtocolDTO), eq("Amendment"), eq(true), (StringBuilder) any());
        verify(validator).validateStatusAndDates(eq(studyProtocolDTO),
                eq(overallStatusDTO), eq((List) null), eq(true),
                (StringBuilder) any());
        verify(validator).validateNihGrants(eq(studyProtocolDTO), eq(leadOrganizationDTO), eq(studyResourcingDTOs), (StringBuilder) any());
        verify(validator).validateIndlde(eq(studyProtocolDTO), eq(studyIndldeDTOs), (StringBuilder) any());
        verify(validator).validateDWFS(eq(spIi), eq(TrialRegistrationValidator.ERROR_DWFS_FOR_AMEND),
                                       eq(TrialRegistrationValidator.ERROR_MESSAGE_DWFS_FOR_AMEND),
                                       (StringBuilder) any());
        verify(validator).validateExistingStatus(eq(spIi), (StringBuilder) any());
        verify(validator).validateOtherIdentifiers(eq(studyProtocolDTO), (StringBuilder) any());
        verify(validator).validateMandatoryDocuments(eq(documentDTOs), (StringBuilder) any());
        verify(validator).validateAmendmentDocuments(eq(documentDTOs), (StringBuilder) any());
        verify(validator).validatePOObjects(eq(studyProtocolDTO), eq(leadOrganizationDTO), eq(sponsorOrganizationDTO),
                                            eq(summary4organizationDTO), eq(piPersonDTO),
                                            eq(piPersonDTO),eq(leadOrganizationDTO), (StringBuilder) any());
        verify(validator).validateAmendmentInfo(eq(studyProtocolDTO), (StringBuilder) any());        
        verify(validator).validateRegulatoryInfo(eq(studyProtocolDTO), eq(studyRegAuthDTO), eq(studyIndldeDTOs),
                                                 (StringBuilder) any(),  (String) any());
        verify(validator).validateSummary4Resourcing(eq(studyProtocolDTO), eq(summary4StudyResourcingDTO),
                                                     (StringBuilder) any());
    }
    
    private ResponsiblePartyDTO getResponsiblePartyDTO(PersonDTO p, OrganizationDTO o) {
        ResponsiblePartyDTO party = new ResponsiblePartyDTO();
        party.setType(ResponsiblePartyType.PRINCIPAL_INVESTIGATOR);
        party.setAffiliation(o);
        party.setInvestigator(p);
        return party;
    }

    /**
     * test the validateIndlde method with no ind/ide.
     */
    @Test
    public void testValidateIndldeEmpty() {
        validator.validateIndlde(studyProtocolDTO, null, errorMsg);
        checkErrorMsg("");
    }

    /**
     * test the validateIndlde method for invalid ind/ide.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateIndldeInvalid() throws PAException {
        StudyIndldeDTO studyIndldeDTO = new StudyIndldeDTO();
        List<StudyIndldeDTO> studyIndldeDTOs = new ArrayList<StudyIndldeDTO>();
        studyIndldeDTOs.add(studyIndldeDTO);
        when(studyIndldeService.validateWithoutRollback(studyIndldeDTO)).thenReturn("PAException");
        validator.validateIndlde(studyProtocolDTO, studyIndldeDTOs, errorMsg);
        verify(studyIndldeService).validateWithoutRollback(studyIndldeDTO);
        verify(paServiceUtils).enforceNoDuplicateIndIde(studyIndldeDTOs, studyProtocolDTO);
        checkErrorMsg("PAException");
    }

    /**
     * test the validateIndlde method for duplicate ind/ide exception.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateIndldeDuplicate() throws PAException {
        StudyIndldeDTO studyIndldeDTO = new StudyIndldeDTO();
        List<StudyIndldeDTO> studyIndldeDTOs = new ArrayList<StudyIndldeDTO>();
        studyIndldeDTOs.add(studyIndldeDTO);
        doThrow(new PAException("PAException")).when(paServiceUtils).enforceNoDuplicateIndIde(studyIndldeDTOs, studyProtocolDTO);;
        validator.validateIndlde(studyProtocolDTO, studyIndldeDTOs, errorMsg);
        verify(studyIndldeService).validateWithoutRollback(studyIndldeDTO);
        verify(paServiceUtils).enforceNoDuplicateIndIde(studyIndldeDTOs, studyProtocolDTO);
        checkErrorMsg("nullPAException");
    }

    /**
     * test the validateIndlde method for valid case.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateIndldeValid() throws PAException {
        StudyIndldeDTO studyIndldeDTO = new StudyIndldeDTO();
        List<StudyIndldeDTO> studyIndldeDTOs = new ArrayList<StudyIndldeDTO>();
        studyIndldeDTOs.add(studyIndldeDTO);
        validator.validateIndlde(studyProtocolDTO, studyIndldeDTOs, errorMsg);
        verify(studyIndldeService).validateWithoutRollback(studyIndldeDTO);
        verify(paServiceUtils).enforceNoDuplicateIndIde(studyIndldeDTOs, studyProtocolDTO);
        checkErrorMsg("null");
    }
    
    /**
     * test the validateOtherIdentifiers method with valid data
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateOtherIdentifiersValid() throws PAException {
        Ii spIi = IiConverter.convertToIi(1L);
        studyProtocolDTO.setIdentifier(spIi);
        DSet<Ii> newIdentifiers = new DSet<Ii>();
        newIdentifiers.setItem(new HashSet<Ii>());
        newIdentifiers.getItem().add(IiConverter.convertToIi(1L));
        studyProtocolDTO.setSecondaryIdentifiers(newIdentifiers);
        StudyProtocolDTO savedStudy = new StudyProtocolDTO();
        DSet<Ii> saved = new DSet<Ii>();
        saved.setItem(new HashSet<Ii>());
        saved.getItem().add(IiConverter.convertToIi(1L));
        savedStudy.setSecondaryIdentifiers(saved);
        when(studyProtocolService.getStudyProtocol(spIi)).thenReturn(savedStudy);
        validator.validateOtherIdentifiers(studyProtocolDTO, errorMsg);
        verify(studyProtocolService).getStudyProtocol(spIi);
        checkErrorMsg("");
    }
    
    /**
     * test the validateOtherIdentifiers method with null SecondaryIdentifiers data
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateOtherIdentifiersValidNullSecondaryIdentifiers() throws PAException {
        Ii spIi = IiConverter.convertToIi(1L);
        studyProtocolDTO.setIdentifier(spIi);       
        StudyProtocolDTO savedStudy = new StudyProtocolDTO();
        DSet<Ii> saved = new DSet<Ii>();
        saved.setItem(new HashSet<Ii>());        
        savedStudy.setSecondaryIdentifiers(saved);
        when(studyProtocolService.getStudyProtocol(spIi)).thenReturn(savedStudy);
        validator.validateOtherIdentifiers(studyProtocolDTO, errorMsg);
        verify(studyProtocolService).getStudyProtocol(spIi);
        checkErrorMsg("");
    }
    
    /**
     * test the validateOtherIdentifiers method with null SecondaryIdentifiers data
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateOtherIdentifiersInvalidNullSecondaryIdentifiers() throws PAException {
        Ii spIi = IiConverter.convertToIi(1L);
        studyProtocolDTO.setIdentifier(spIi);       
        StudyProtocolDTO savedStudy = new StudyProtocolDTO();
        DSet<Ii> saved = new DSet<Ii>();
        saved.setItem(new HashSet<Ii>());
        saved.getItem().add(IiConverter.convertToIi(1L));
        savedStudy.setSecondaryIdentifiers(saved);
        when(studyProtocolService.getStudyProtocol(spIi)).thenReturn(savedStudy);
        validator.validateOtherIdentifiers(studyProtocolDTO, errorMsg);
        verify(studyProtocolService).getStudyProtocol(spIi);
        checkErrorMsg("Other identifiers cannot be modified or deleted as part of an amendment.");
    }
    
    /**
     * test the validateOtherIdentifiers method with invalid data
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateOtherIdentifiersInValid() throws PAException {
        Ii spIi = IiConverter.convertToIi(1L);
        studyProtocolDTO.setIdentifier(spIi);
        DSet<Ii> newIdentifiers = new DSet<Ii>();
        newIdentifiers.setItem(new HashSet<Ii>());
        studyProtocolDTO.setSecondaryIdentifiers(newIdentifiers);
        StudyProtocolDTO savedStudy = new StudyProtocolDTO();
        DSet<Ii> saved = new DSet<Ii>();
        saved.setItem(new HashSet<Ii>());
        saved.getItem().add(IiConverter.convertToIi(1L));
        savedStudy.setSecondaryIdentifiers(saved);
        when(studyProtocolService.getStudyProtocol(spIi)).thenReturn(savedStudy);
        validator.validateOtherIdentifiers(studyProtocolDTO, errorMsg);
        verify(studyProtocolService).getStudyProtocol(spIi);
        checkErrorMsg("Other identifiers cannot be modified or deleted as part of an amendment.");
    }
    
    /**
     * test the validateMandatoryDocuments method with valid data
     */
    @Test
    public void testValidateMandatoryDocumentsValid() {
        List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
        when(paServiceUtils.checkDocumentListForValidFileTypes(documentDTOs)).thenReturn("");
        when(paServiceUtils.isDocumentInList(documentDTOs, DocumentTypeCode.PROTOCOL_DOCUMENT)).thenReturn(true);
        when(paServiceUtils.isDocumentInList(documentDTOs, DocumentTypeCode.IRB_APPROVAL_DOCUMENT)).thenReturn(true);
        validator.validateMandatoryDocuments(documentDTOs, errorMsg);
        checkErrorMsg("");
    }
    
    /**
     * test the validateMandatoryDocuments method with invalid data.
     */
    @Test
    public void testValidateMandatoryDocumentsInvalid() {
        List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
        when(paServiceUtils.checkDocumentListForValidFileTypes(documentDTOs)).thenReturn("Error ");
        when(paServiceUtils.isDocumentInList(documentDTOs, DocumentTypeCode.PROTOCOL_DOCUMENT)).thenReturn(false);
        when(paServiceUtils.isDocumentInList(documentDTOs, DocumentTypeCode.IRB_APPROVAL_DOCUMENT)).thenReturn(false);
        validator.validateMandatoryDocuments(documentDTOs, errorMsg);
        checkErrorMsg("Error Protocol Document is required.\nIRB Approval Document is required.\n");
    }
    
    /**
     * test the validateAmendmentDocuments method with valid data.
     */
    @Test
    public void testvalidateAmendmentDocumentsValid() {
        List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
        when(paServiceUtils.isDocumentInList(documentDTOs, DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT))
            .thenReturn(true);
        validator.validateAmendmentDocuments(documentDTOs, errorMsg);
        verify(paServiceUtils).isDocumentInList(documentDTOs, DocumentTypeCode.CHANGE_MEMO_DOCUMENT);
        verify(paServiceUtils).isDocumentInList(documentDTOs, DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT);
        checkErrorMsg("");
    }

    /**
     * test the validateAmendmentDocuments method with invalid data.
     */
    @Test
    public void testvalidateAmendmentDocumentsInvalid() {
        List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
        validator.validateAmendmentDocuments(documentDTOs, errorMsg);
        verify(paServiceUtils).isDocumentInList(documentDTOs, DocumentTypeCode.CHANGE_MEMO_DOCUMENT);
        verify(paServiceUtils).isDocumentInList(documentDTOs, DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT);
        checkErrorMsg("At least one is required: Change Memo Document or Protocol Highlighted Document.");
    }
    
    /**
     * test the ValidatePOObjects method for non ctgov study protocol.
     */
    @Test
    public void testValidatePOObjectsNotCTGOV() {
        studyProtocolDTO.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(false));
        OrganizationDTO leadOrganizationDTO = new OrganizationDTO();
        OrganizationDTO sponsorOrganizationDTO = new OrganizationDTO();
        OrganizationDTO summary4organizationDTO = new OrganizationDTO();
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(summary4organizationDTO);
        PersonDTO piPersonDTO = new PersonDTO();
        Ii responsiblePartyContactIi = IiConverter.convertToIi(1L);
        validator = mock(TrialRegistrationValidator.class);
        doCallRealMethod().when(validator).validatePOObjects(studyProtocolDTO, leadOrganizationDTO,
                                                             sponsorOrganizationDTO, summary4OrganizationDTO,
                                                             piPersonDTO, piPersonDTO, leadOrganizationDTO, errorMsg);
        when(validator.validatePoObject(leadOrganizationDTO, "Lead Organization", true)).thenReturn("1");
        when(validator.validatePoObject(summary4organizationDTO, "Data Table 4 Organization", false)).thenReturn("2");
        when(validator.validatePoObject(piPersonDTO, "Principal Investigator", true)).thenReturn("3");
        validator.validatePOObjects(studyProtocolDTO, leadOrganizationDTO, sponsorOrganizationDTO,
        		summary4OrganizationDTO, piPersonDTO, piPersonDTO, leadOrganizationDTO, errorMsg);
        verify(validator).validatePoObject(leadOrganizationDTO, "Lead Organization", true);
        verify(validator).validatePoObject(summary4organizationDTO, "Data Table 4 Organization", false);
        verify(validator).validatePoObject(piPersonDTO, "Principal Investigator", true);
        verify(validator, never()).validatePoObject(sponsorOrganizationDTO, "Sponsor Organization", true);
        verify(paServiceUtils, never()).isIiExistInPO(responsiblePartyContactIi);
        checkErrorMsg("123");
    }
    
    /**
     * test the ValidatePOObjects method for ctgov study protocol.
     */
    @Test
    public void testValidatePOObjectsCTGOVInvalid() {
        studyProtocolDTO.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(true));
        OrganizationDTO leadOrganizationDTO = new OrganizationDTO();
        OrganizationDTO sponsorOrganizationDTO = new OrganizationDTO();
        OrganizationDTO summary4organizationDTO = new OrganizationDTO();
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(summary4organizationDTO);
        PersonDTO piPersonDTO = new PersonDTO();        
        validator = mock(TrialRegistrationValidator.class);
        doCallRealMethod().when(validator).setPaServiceUtils(paServiceUtils);
        doCallRealMethod().when(validator).validatePOObjects(studyProtocolDTO, leadOrganizationDTO,
                                                             sponsorOrganizationDTO, summary4OrganizationDTO,
                                                             piPersonDTO, piPersonDTO, leadOrganizationDTO, errorMsg);
        when(validator.validatePoObject(leadOrganizationDTO, "Lead Organization", true)).thenReturn("1");
        when(validator.validatePoObject(summary4organizationDTO, "Data Table 4 Organization", false)).thenReturn("2");
        when(validator.validatePoObject(piPersonDTO, "Principal Investigator", true)).thenReturn("3");
        when(validator.validatePoObject(sponsorOrganizationDTO, "Sponsor Organization", true)).thenReturn("4");       
        validator.setPaServiceUtils(paServiceUtils);
        validator.validatePOObjects(studyProtocolDTO, leadOrganizationDTO, sponsorOrganizationDTO,
        		summary4OrganizationDTO, piPersonDTO, piPersonDTO, leadOrganizationDTO, errorMsg);
        verify(validator).validatePoObject(leadOrganizationDTO, "Lead Organization", true);
        verify(validator).validatePoObject(summary4organizationDTO, "Data Table 4 Organization", false);
        verify(validator).validatePoObject(piPersonDTO, "Principal Investigator", true);
        verify(validator).validatePoObject(sponsorOrganizationDTO, "Sponsor Organization", true);
        
        checkErrorMsg("1234nullnull");
    }
    
    /**
     * test the ValidatePOObjects method for ctgov study protocol.
     */
    @Test
    public void testValidatePOObjectsCTGOVValid() {
        studyProtocolDTO.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(true));
        OrganizationDTO leadOrganizationDTO = new OrganizationDTO();
        OrganizationDTO sponsorOrganizationDTO = new OrganizationDTO();
        OrganizationDTO summary4organizationDTO = new OrganizationDTO();
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(summary4organizationDTO);
        PersonDTO piPersonDTO = new PersonDTO();
        validator = mock(TrialRegistrationValidator.class);
        doCallRealMethod().when(validator).setPaServiceUtils(paServiceUtils);
        doCallRealMethod().when(validator).validatePOObjects(studyProtocolDTO, leadOrganizationDTO,
                                                             sponsorOrganizationDTO, summary4OrganizationDTO,
                                                             piPersonDTO, null, null, errorMsg);
        when(validator.validatePoObject(leadOrganizationDTO, "Lead Organization", true)).thenReturn("");
        when(validator.validatePoObject(summary4organizationDTO, "Data Table 4 Organization", false)).thenReturn("");
        when(validator.validatePoObject(piPersonDTO, "Principal Investigator", true)).thenReturn("");
        when(validator.validatePoObject(sponsorOrganizationDTO, "Sponsor Organization", true)).thenReturn("");
        validator.setPaServiceUtils(paServiceUtils);
        validator.validatePOObjects(studyProtocolDTO, leadOrganizationDTO, sponsorOrganizationDTO,
        		summary4OrganizationDTO, piPersonDTO, null, null, errorMsg);
        verify(validator).validatePoObject(leadOrganizationDTO, "Lead Organization", true);
        verify(validator).validatePoObject(summary4organizationDTO, "Data Table 4 Organization", false);
        verify(validator).validatePoObject(piPersonDTO, "Principal Investigator", true);
        verify(validator).validatePoObject(sponsorOrganizationDTO, "Sponsor Organization", true);
        verify(paServiceUtils, never()).isIiExistInPO(null);
        checkErrorMsg("");
    }
    
    /**
     * test the validatePoObject with no entity
     */
    @Test
    public void testValidatePoObjectNull() {
        String result = validator.validatePoObject(null, "fieldName", true);
        assertEquals("Wrong error message returned", "fieldName cannot be null.\n", result);
    }

    /**
     * test the validatePoObject with no Ii in the entity
     */
    @Test
    public void testValidatePoObjectInvalidIi() {
        OrganizationDTO organization = new OrganizationDTO();
        String result = validator.validatePoObject(organization, "fieldName", true);
        assertEquals("Wrong error message returned", "Error getting fieldName from PO. Identifier is required.\n",
                     result);
    }

    /**
     * test the validatePoObject with a non existent entity
     */
    @Test
    public void testValidatePoObjectInvalidOrganization() {
        OrganizationDTO organization = new OrganizationDTO();
        Ii orgIi = IiConverter.convertToIi(1L);
        organization.setIdentifier(orgIi);
        when(paServiceUtils.isIiExistInPO(orgIi)).thenReturn(false);
        String result = validator.validatePoObject(organization, "fieldName", true);
        verify(paServiceUtils).isIiExistInPO(orgIi);
        assertEquals("Wrong error message returned", "Error getting fieldName from PO for id = 1.\n", result);
    }

    /**
     * test the validatePoObject with a valid entity
     */
    @Test
    public void testValidatePoObjectValid() {
        OrganizationDTO organization = new OrganizationDTO();
        Ii orgIi = IiConverter.convertToIi(1L);
        organization.setIdentifier(orgIi);
        when(paServiceUtils.isIiExistInPO(orgIi)).thenReturn(true);
        String result = validator.validatePoObject(organization, "fieldName", true);
        verify(paServiceUtils).isIiExistInPO(orgIi);
        assertEquals("Wrong error message returned", "", result);
    }
    
    /**
     * test the validateAmendmentInfo with invalid data.
     * @throws PAException if an error occurs
     */
    @Test
    public void testvalidateAmendmentInfoInvalid() throws PAException {
        Ii spIi = IiConverter.convertToIi(1L);
        studyProtocolDTO.setIdentifier(spIi);
        List<StudyInboxDTO> inboxDTOs = new ArrayList<StudyInboxDTO>();
        inboxDTOs.add(new StudyInboxDTO());
        when(studyInboxServiceLocal.getOpenInboxEntries(spIi)).thenReturn(inboxDTOs);
        when(lookUpTableServiceRemote.getPropertyValue("fromaddress")).thenReturn("FromAddress");
        validator.validateAmendmentInfo(studyProtocolDTO, errorMsg);
        verify(studyInboxServiceLocal).getOpenInboxEntries(spIi);
        verify(lookUpTableServiceRemote).getPropertyValue("fromaddress");
        checkErrorMsg("Amendment Date is required.  A trial with unaccepted updates cannot be amended. Please contact"
                + " the CTRO at FromAddress to have your trial's updates accepted.");
    }

    /**
     * test the validateAmendmentInfo with valid data.
     * @throws PAException if an error occurs
     */
    @Test
    public void testvalidateAmendmentInfoValid() throws PAException {
        Ii spIi = IiConverter.convertToIi(1L);
        studyProtocolDTO.setIdentifier(spIi);
        studyProtocolDTO.setAmendmentDate(TsConverter.convertToTs(new Date()));
        List<StudyInboxDTO> inboxDTOs = new ArrayList<StudyInboxDTO>();
        when(studyInboxServiceLocal.getOpenInboxEntries(spIi)).thenReturn(inboxDTOs);
        validator.validateAmendmentInfo(studyProtocolDTO, errorMsg);
        verify(studyInboxServiceLocal).getOpenInboxEntries(spIi);
        checkErrorMsg("");
    }
    
    /**
     * test the validateAmendmentInfo exception.
     * @throws PAException if an error occurs
     */
    @Test(expected = PAException.class)
    public void testvalidateAmendmentInfoException() throws PAException {
        Ii spIi = IiConverter.convertToIi(1L);
        studyProtocolDTO.setIdentifier(spIi);
        when(studyInboxServiceLocal.getOpenInboxEntries(spIi)).thenThrow(new PAException());
        validator.validateAmendmentInfo(studyProtocolDTO, errorMsg);
        
    }
   
    
    /**
     * test the validateLeadOrgTrailIdLength exception.
     * @throws PAException if an error occurs
     */
    @Test(expected = PAException.class)
    public void testvalidateLeadOrgTrailIdLength() throws PAException {
        StudySiteDTO leadOrganizationSiteIdentifierDTO = new StudySiteDTO();
        leadOrganizationSiteIdentifierDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("LeadOrganizationWhichismorethan30characters"));
        
        validator.validateLeadOrgTrialIdLength(leadOrganizationSiteIdentifierDTO);
    }
   
    /**
     * test the validateLeadOrgTrailIdLength.
     */
    @Test
    public void testvalidateLeadOrgTrailIdLengthNoEx() throws PAException {
        StudySiteDTO leadOrganizationSiteIdentifierDTO = new StudySiteDTO();
        leadOrganizationSiteIdentifierDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("LeadOrganization"));
        
        validator.validateLeadOrgTrialIdLength(leadOrganizationSiteIdentifierDTO);
    } 
    
    /**
     * Test the validateCreation method.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateCreation() throws PAException {
        validator = mock(TrialRegistrationValidator.class);
        Ii spIi = IiConverter.convertToIi(1L);
        studyProtocolDTO.setIdentifier(spIi);
        StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
        OrganizationDTO leadOrganizationDTO = new OrganizationDTO();
        OrganizationDTO sponsorOrganizationDTO = new OrganizationDTO();
        StudyContactDTO studyContactDTO = new StudyContactDTO();
        StudySiteContactDTO studySiteContactDTO = new StudySiteContactDTO();
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        StudyResourcingDTO summary4StudyResourcingDTO = new StudyResourcingDTO();
        PersonDTO principalInvestigatorDTO = new PersonDTO();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = new StudySiteDTO();        
        StudyRegulatoryAuthorityDTO studyRegAuthDTO = new StudyRegulatoryAuthorityDTO();
        List<StudyResourcingDTO> studyResourcingDTOs = new ArrayList<StudyResourcingDTO>();
        List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
        List<StudyIndldeDTO> studyIndldeDTOs = new ArrayList<StudyIndldeDTO>();
        StudySiteDTO nctIdentifierDTO = new StudySiteDTO();
        StudySiteDTO dcpIdentifierDTO = new StudySiteDTO();
        
        final ResponsiblePartyDTO responsiblePartyDTO = getResponsiblePartyDTO(principalInvestigatorDTO, leadOrganizationDTO);
        doCallRealMethod().when(validator).validateCreation(studyProtocolDTO,
                overallStatusDTO, leadOrganizationDTO, sponsorOrganizationDTO,
                responsiblePartyDTO, summary4OrganizationDTO,
                summary4StudyResourcingDTO, principalInvestigatorDTO,
                leadOrganizationSiteIdentifierDTO, studyRegAuthDTO,
                studyResourcingDTOs, documentDTOs, studyIndldeDTOs,
                nctIdentifierDTO, dcpIdentifierDTO);
        validator.validateCreation(studyProtocolDTO, overallStatusDTO,
                leadOrganizationDTO, sponsorOrganizationDTO,
                responsiblePartyDTO, summary4OrganizationDTO,
                summary4StudyResourcingDTO, principalInvestigatorDTO,
                leadOrganizationSiteIdentifierDTO, studyRegAuthDTO,
                studyResourcingDTOs, documentDTOs, studyIndldeDTOs,
                nctIdentifierDTO, dcpIdentifierDTO);
        verify(validator).validateStudyProtocol(studyProtocolDTO);
        verify(validator).validateMandatoryFields(eq(studyProtocolDTO), eq(leadOrganizationSiteIdentifierDTO),
                                                  eq(documentDTOs), (StringBuilder) any());
        verify(validator).validateUser(eq(studyProtocolDTO), eq("Create"), eq(false), (StringBuilder) any());
        verify(validator).validateStatusAndDates(eq(studyProtocolDTO),
                eq(overallStatusDTO), eq((List) null), eq(true),
                (StringBuilder) any());
        verify(validator).validateNihGrants(eq(studyProtocolDTO), eq(leadOrganizationDTO), eq(studyResourcingDTOs), (StringBuilder) any());
        verify(validator).validateIndlde(eq(studyProtocolDTO), eq(studyIndldeDTOs), (StringBuilder) any());
        verify(validator).validateMandatoryDocuments(eq(documentDTOs), (StringBuilder) any());
        verify(validator).validatePOObjects(eq(studyProtocolDTO), eq(leadOrganizationDTO), eq(sponsorOrganizationDTO),
                                            eq(summary4OrganizationDTO), eq(principalInvestigatorDTO),
                                            eq(principalInvestigatorDTO), eq(leadOrganizationDTO), (StringBuilder) any());       
        verify(validator).validateRegulatoryInfo(eq(studyProtocolDTO), eq(studyRegAuthDTO), eq(studyIndldeDTOs),
                                                 (StringBuilder) any(), (String) any());
        verify(validator).validateSummary4Resourcing(eq(studyProtocolDTO), eq(summary4StudyResourcingDTO),
                                                     (StringBuilder) any());
    }

    /**
     * test the validateStudyProtocol method with a null study protocol.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateStudyProtocolNull() throws PAException {
        thrown.expect(PAException.class);
        thrown.expectMessage("Validation Exception Study Protocol cannot be null.");
        validator.validateStudyProtocol(null);
    }
    
    /**
     * test the validateStudyProtocol method with a null study protocol
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateStudyProtocolNoCTGOV() throws PAException {
        thrown.expect(PAException.class);
        thrown.expectMessage("Validation Exception Study Protocol ClinicalTrials.gov XML indicator cannot be null.");
        validator.validateStudyProtocol(studyProtocolDTO);
    }
    
    /**
     * test the validateStudyProtocol method with a null study protocol
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateStudyProtocolValid() throws PAException {
        studyProtocolDTO.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(true));
        validator.validateStudyProtocol(studyProtocolDTO);
    }
    
    /**
     * test the validateMandatoryFields method with invalid data.
     */
    @Test
    public void testValidateMandatoryFieldsInvalid() {
        StudySiteDTO leadOrganizationSiteIdentifierDTO = new StudySiteDTO();
        validator = mock(TrialRegistrationValidator.class);
        doCallRealMethod().when(validator).check(anyBoolean(), anyString(), eq(errorMsg));
        doCallRealMethod().when(validator).validateMandatoryFields(studyProtocolDTO, leadOrganizationSiteIdentifierDTO,
                                                                   null, errorMsg);
        validator.validateMandatoryFields(studyProtocolDTO, leadOrganizationSiteIdentifierDTO, null, errorMsg);
        verify(validator).validatePhase(studyProtocolDTO, errorMsg);
        checkErrorMsg("Local StudyProtocol Identifier cannot be null , Document DTO's cannot be null, Official Title"+
        " cannot be null");
    }

    /**
     * test the validateMandatoryFields method with valid data.
     */
    @Test
    public void testValidateMandatoryFieldsValid() {
        List<DocumentDTO> documents = new ArrayList<DocumentDTO>();
        studyProtocolDTO.setOfficialTitle(StConverter.convertToSt("Official title"));
        validator = mock(TrialRegistrationValidator.class);
        doCallRealMethod().when(validator).check(anyBoolean(), anyString(), eq(errorMsg));
        doCallRealMethod().when(validator).validateMandatoryFields(studyProtocolDTO, null, documents, errorMsg);
        validator.validateMandatoryFields(studyProtocolDTO, null, documents, errorMsg);
        verify(validator).validatePhase(studyProtocolDTO, errorMsg);
        checkErrorMsg("");
    }
    
    /**
     * test the validatePhase method with a null phase
     */
    @Test
    public void testvalidatePhaseNull() {
        validator.validatePhase(studyProtocolDTO, errorMsg);
        checkErrorMsg("Phase cannot be null , ");
    }
    
    /**
     * test the validatePhase method with a invalid phase code.
     */
    @Test
    public void testvalidatePhaseInvalid() {
        studyProtocolDTO.setPhaseCode(CdConverter.convertStringToCd("xxx"));
        validator.validatePhase(studyProtocolDTO, errorMsg);
        checkErrorMsg("Please enter valid value for Phase Code.");
    }
    
    /**
     * test the validatePhase method with a valid data.
     */
    @Test
    public void testvalidatePhaseValid() {
        studyProtocolDTO.setPhaseCode(CdConverter.convertStringToCd("0"));
        validator.validatePhase(studyProtocolDTO, errorMsg);
        checkErrorMsg("");
    }

    /**
     * Test the validateUpdate method.
     * @throws PAException if an error occurs
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testValidateRejection() throws PAException {
        validator = mock(TrialRegistrationValidator.class);
        Ii spIi = IiConverter.convertToIi(1L);
        studyProtocolDTO.setIdentifier(spIi);
        doCallRealMethod().when(validator).validateRejection(studyProtocolDTO, MilestoneCode.READY_FOR_TSR);
        validator.validateRejection(studyProtocolDTO, MilestoneCode.READY_FOR_TSR);
        verify(validator).validateUser(eq(studyProtocolDTO), eq("Reject"), eq(false), (StringBuilder) any());
        verify(validator).validateDWFS(eq(spIi), eq(TrialRegistrationValidator.ERROR_DWFS_FOR_REJECT),
                                       eq(TrialRegistrationValidator.ERROR_MESSAGE_DWFS_FOR_REJECT),
                                       (StringBuilder) any());
    }
    
    @SuppressWarnings("deprecation")
    @Test
    public void testValidateLateRejection() throws PAException {
        validator = mock(TrialRegistrationValidator.class);
        Ii spIi = IiConverter.convertToIi(1L);
        studyProtocolDTO.setIdentifier(spIi);
        doCallRealMethod().when(validator).validateRejection(studyProtocolDTO, MilestoneCode.LATE_REJECTION_DATE);
        validator.validateRejection(studyProtocolDTO, MilestoneCode.LATE_REJECTION_DATE);
        verify(validator).validateUser(eq(studyProtocolDTO), eq("Reject"), eq(false), (StringBuilder) any());
        verify(validator, never()).validateDWFS(eq(spIi), eq(TrialRegistrationValidator.ERROR_DWFS_FOR_REJECT),
                                       eq(TrialRegistrationValidator.ERROR_MESSAGE_DWFS_FOR_REJECT),
                                       (StringBuilder) any());
    }
    
    /**
     * Test the validateProprietaryCreation method.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateProprietaryCreation() throws PAException {
        validator = mock(TrialRegistrationValidator.class);
        Ii spIi = IiConverter.convertToIi(1L);
        studyProtocolDTO.setIdentifier(spIi);
        StudySiteAccrualStatusDTO studySiteAccrualStatusDTO = new StudySiteAccrualStatusDTO();
        OrganizationDTO leadOrganizationDTO = new OrganizationDTO();
        PersonDTO studySiteInvestigatorDTO = new PersonDTO();
        StudySiteDTO leadOrganizationStudySiteDTO = new StudySiteDTO();
        OrganizationDTO studySiteOrganizationDTO = new OrganizationDTO();
        StudySiteDTO studySiteDTO = new StudySiteDTO();
        StudySiteDTO nctIdentifierDTO = new StudySiteDTO();
        OrganizationDTO summary4OrganizationDTO = new OrganizationDTO();
        List<OrganizationDTO> summary4OrganizationDTOList = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTOList.add(summary4OrganizationDTO);
        StudyResourcingDTO summary4StudyResourcingDTO = new StudyResourcingDTO();
        List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();
        doCallRealMethod().when(validator).setPaServiceUtils(paServiceUtils);
        doCallRealMethod().when(validator).validateProprietaryCreation(studyProtocolDTO, studySiteAccrualStatusDTO,
                                                                       documentDTOs, leadOrganizationDTO,
                                                                       studySiteInvestigatorDTO,
                                                                       leadOrganizationStudySiteDTO,
                                                                       studySiteOrganizationDTO, studySiteDTO,
                                                                       nctIdentifierDTO, summary4OrganizationDTOList,
                                                                       summary4StudyResourcingDTO);
        when(paServiceUtils.validateRecuritmentStatusDateRule(studySiteAccrualStatusDTO, studySiteDTO)).thenReturn("");
        when(validator.validatePoObject(any(EntityDto.class), anyString(), anyBoolean())).thenReturn("");
        validator.setPaServiceUtils(paServiceUtils);
        validator.validateProprietaryCreation(studyProtocolDTO, studySiteAccrualStatusDTO, documentDTOs,
                                              leadOrganizationDTO, studySiteInvestigatorDTO,
                                              leadOrganizationStudySiteDTO, studySiteOrganizationDTO, studySiteDTO,
                                              nctIdentifierDTO, summary4OrganizationDTOList, summary4StudyResourcingDTO);
        verify(validator).validateMandatoryFieldsForProprietary(eq(studyProtocolDTO), eq(studySiteAccrualStatusDTO),
                                                                eq(leadOrganizationStudySiteDTO), eq(studySiteDTO),
                                                                eq(summary4StudyResourcingDTO), (StringBuilder) any());
        verify(validator).validateUser(eq(studyProtocolDTO), eq("Create"), eq(false), (StringBuilder) any());
        verify(validator).validatePhasePurposeAndTemplateDocument(eq(studyProtocolDTO), eq(documentDTOs),
                                                                  eq(nctIdentifierDTO), (StringBuilder) any());
        verify(paServiceUtils).validateRecuritmentStatusDateRule(studySiteAccrualStatusDTO, studySiteDTO);
        verify(validator).validatePoObject(leadOrganizationDTO, "Lead Organization", false);
        verify(validator).validatePoObject(studySiteOrganizationDTO, "Study Site Organization", false);
        verify(validator).validatePoObject(summary4OrganizationDTO, "Data Table 4 Organization", false);
        verify(validator).validatePoObject(studySiteInvestigatorDTO, "Study Site Investigator", false);
        verify(validator).validateSummary4Resourcing(eq(studyProtocolDTO), eq(summary4StudyResourcingDTO),
                                                     (StringBuilder) any());
    }

}

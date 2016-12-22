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
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.domain.StudySiteSubjectAccrualCount;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO.ErrorMessageTypeEnum;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.RejectionReasonCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IiConverterTest;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyMilestoneServiceBean;
import gov.nih.nci.pa.service.StudyMilestoneServicelocal;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mshestopalov
 *
 */
public class PaServiceUtilsTest extends AbstractHibernateTestCase {

    private PoServiceLocator poSvcLoc;
    private ServiceLocator paSvcLoc;
    private IdentifiedOrganizationCorrelationServiceRemote identifierOrganizationSvc;
    private final PAServiceUtils paServiceUtil = new PAServiceUtils();
    protected StudyMilestoneServicelocal studyMilestoneSvc;
    protected AbstractionCompletionServiceLocal abstractionCompletionSvc; 

    @Before
    public void setup() throws PAException, NullifiedEntityException, TooManyResultsException {
        paSvcLoc = mock(ServiceLocator.class);
        poSvcLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poSvcLoc);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);
        identifierOrganizationSvc = mock(IdentifiedOrganizationCorrelationServiceRemote.class);
        abstractionCompletionSvc = mock(AbstractionCompletionServiceLocal.class);
        when(poSvcLoc.getIdentifiedOrganizationEntityService()).thenReturn(identifierOrganizationSvc);
        when(paSvcLoc.getAbstractionCompletionService()).thenReturn(abstractionCompletionSvc);
        List<IdentifiedOrganizationDTO> identifiedOrgs = new ArrayList<IdentifiedOrganizationDTO>();
        IdentifiedOrganizationDTO identOrgDto = new IdentifiedOrganizationDTO();
        identOrgDto.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi("player ext"));
        identOrgDto.setScoperIdentifier(IiConverter.convertToPoOrganizationIi("scoper ext"));
        identifiedOrgs.add(identOrgDto);
        when(identifierOrganizationSvc.search(any(IdentifiedOrganizationDTO.class))).thenReturn(identifiedOrgs);
       
        List<AbstractionCompletionDTO> completionErrors = new ArrayList<AbstractionCompletionDTO>();
        AbstractionCompletionDTO error = new AbstractionCompletionDTO();
        error.setErrorCode(AbstractionCompletionDTO.ERROR_TYPE);
        error.setErrorType(AbstractionCompletionDTO.ERROR_TYPE);
        error.setErrorDescription("I am an error!");
        error.setComment("I am an error!");
        
        
        AbstractionCompletionDTO warning = new AbstractionCompletionDTO();
        warning.setErrorCode(AbstractionCompletionDTO.WARNING_TYPE);
        warning.setErrorType(AbstractionCompletionDTO.WARNING_TYPE);
        warning.setErrorDescription("I am a warning!");
        warning.setComment("I am a warning!");
        
        completionErrors.add(error);
        completionErrors.add(warning);
        when(abstractionCompletionSvc.validateAbstractionCompletion(any(Ii.class))).thenReturn(completionErrors);
        
        when(paSvcLoc.getLookUpTableService()).thenReturn(new MockLookUpTableServiceBean());
        studyMilestoneSvc = new StudyMilestoneServiceBean();
        
        
        setupPersonMocks();
    }

    private void setupPersonMocks() throws NullifiedEntityException{
        PersonDTO personDto = new PersonDTO();
        Ii personPoIi = IiConverterTest.makePersonPoIi("1");
        personDto.setIdentifier(personPoIi);
        PersonEntityServiceRemote personService = mock(PersonEntityServiceRemote.class);
        when(personService.getPerson(eq(personPoIi))).thenReturn(personDto);
        when(poSvcLoc.getPersonEntityService()).thenReturn(personService);
    }
    
    @Test
    public void testWarningMessageSkip() throws PAException {
    	DocumentWorkflowStatusDTO documentWorkflowStatusCode = new DocumentWorkflowStatusDTO();
    	documentWorkflowStatusCode.setStatusCode(CdConverter.convertStringToCd(DocumentWorkflowStatusCode.ABSTRACTED.getCode()));    	
    	StringBuilder sb = paServiceUtil.createAbstractionValidationErrorsTable(IiConverter.convertToIi((long) 1), documentWorkflowStatusCode);    	
    	assertTrue(sb.toString().contains("I am an error!"));
    	assertFalse(sb.toString().contains("I am a warning!"));
    }

    @Test
    public void testGetOrganizationByCtepId() {
        OrganizationDTO orgDto = paServiceUtil.getOrganizationByCtepId("some ctep id");
        assertEquals(orgDto.getIdentifier().getExtension(), "player ext");
    }

    @Test
    public void testbadFileTypeCheck() {
        DocumentDTO doc1 = new DocumentDTO();
        doc1.setFileName(StConverter.convertToSt("badFile1.xml"));
        doc1.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));
        List<DocumentDTO> docs = new ArrayList<DocumentDTO>();
        docs.add(doc1);
        assertTrue(paServiceUtil.checkDocumentListForValidFileTypes(docs).contains("badFile1.xml"));
        doc1.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.OTHER));
        assertFalse(paServiceUtil.checkDocumentListForValidFileTypes(docs).contains("badFile1.xml"));
        doc1.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.TSR));
        doc1.setFileName(StConverter.convertToSt("badFile1.xml"));
        assertTrue(paServiceUtil.checkDocumentListForValidFileTypes(docs).contains("badFile1.xml"));
        doc1.setFileName(StConverter.convertToSt("goodFile2.rtf"));
        assertEquals("", paServiceUtil.checkDocumentListForValidFileTypes(docs));
        doc1.setFileName(StConverter.convertToSt("goodFile1.doc"));
        doc1.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));
        assertEquals("", paServiceUtil.checkDocumentListForValidFileTypes(docs));
    }

    @Test
    public void testGetPersonByIi() {
        Ii personIi = IiConverterTest.makePersonPoIi("1");

        PersonDTO person = paServiceUtil.getPoPersonEntity(personIi);
        assertEquals("Person has wrong extension", "1", person.getIdentifier().getExtension());
        assertEquals("Person has wrong root", IiConverter.PERSON_ROOT, person.getIdentifier().getRoot());
    }


    @Test
    public void testGetPersonByIiNotFound() {
        Ii personIi = IiConverterTest.makePersonPoIi("2");

        PersonDTO person = paServiceUtil.getPoPersonEntity(personIi);
        assertNull("Person should not have been found", person);
    }

    @Test(expected = PAException.class)
    public void testGetEntityByIiWrongRoot() throws PAException {
        Ii ii = new Ii();
        ii.setRoot("bad root");
        ii.setExtension("1");

        paServiceUtil.getEntityByIi(ii);
    }
    
    @Test
    public void testAccrualCounts() throws Exception {
    	TestSchema.primeData();
    	assertEquals(0, paServiceUtil.getTrialAccruals(
        		IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0))));
    	
    	StudySite ss = new StudySite();
        ss.setLocalStudyProtocolIdentifier("treating site");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        StudyProtocol sp = (StudyProtocol) PaHibernateUtil.getCurrentSession().get(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        ss.setStudyProtocol(sp);
        TestSchema.addUpdObject(ss);
        StudySiteAccrualAccess ssAccAccess = new StudySiteAccrualAccess();
        ssAccAccess.setStudySite(ss);        
        ssAccAccess.setRegistryUser(TestSchema.getRegistryUser());
        ssAccAccess.setStatusCode(ActiveInactiveCode.ACTIVE);
        ssAccAccess.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        ssAccAccess.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
        TestSchema.addUpdObject(ssAccAccess);
        
        StudySiteSubjectAccrualCount cnt = new StudySiteSubjectAccrualCount();
        cnt.setStudySite(ss);
        cnt.setAccrualCount(10);
        cnt.setStudyProtocol(sp);
        cnt.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        TestSchema.addUpdObject(cnt);
        
        assertEquals(10, paServiceUtil.getTrialAccruals(
        		IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0))));
    }
    
    @Test 
    public void testSwapStudyProtocolIdentifiers() throws PAException {
       
        TestSchema.primeData();
        StudyProtocol sp = TestSchema.creatOriginalStudyProtocolObj(ActStatusCode.ACTIVE, "NCI-000-0110", "1");
        StudyProtocol sp1 = TestSchema.creatOriginalStudyProtocolObj(ActStatusCode.ACTIVE, "NCI-000-0111", "2");
        StudyMilestone sm1 = TestSchema.createStudyMilestoneObj(MilestoneCode.INITIAL_ABSTRACTION_VERIFY, RejectionReasonCode.OTHER, sp);
        StudyMilestone sm2 = TestSchema.createStudyMilestoneObj(MilestoneCode.SUBMISSION_REJECTED, RejectionReasonCode.DUPLICATE, sp1);
        Ii sourceIi =  IiConverter.convertToIi(sp.getId());
        Ii targetIi = IiConverter.convertToIi(sp1.getId());
        final Session session = PaHibernateUtil.getCurrentSession();
        List<StudyMilestone> sm = getMileStone(session, sm1.getId());
        assertEquals(4L, sm.get(0).getStudyProtocol().getId().longValue());
        sm = getMileStone(session, sm2.getId());
        assertEquals(5L, sm.get(0).getStudyProtocol().getId().longValue());
        // before swap Sm1->sp->4L Sm2->sp1->5L
        paServiceUtil.swapStudyProtocolIdentifiers("study_milestone", sourceIi, targetIi);
        session.flush();
        session.clear();
        sm = getMileStone(session, sm1.getId());
        assertEquals(5L, sm.get(0).getStudyProtocol().getId().longValue());
        sm = getMileStone(session, sm2.getId());
        assertEquals(4L, sm.get(0).getStudyProtocol().getId().longValue());
        // after swap Sm1->sp->5L Sm2->sp1->4L
        
        Document doc1 = TestSchema.createDocumentObj(DocumentTypeCode.IRB_APPROVAL_DOCUMENT, "IRB_Approval_Document.doc", sp);
        Document doc2 = TestSchema.createDocumentObj(DocumentTypeCode.PROTOCOL_DOCUMENT, "Document.doc", sp1);
       // before swap doc1->sp->4L doc2->sp1->5L
        List<Document> doc = getDocument(session, doc1.getId());
        assertEquals(4L, doc.get(0).getStudyProtocol().getId().longValue());
        doc = getDocument(session, doc2.getId());
        assertEquals(5L, doc.get(0).getStudyProtocol().getId().longValue());
        paServiceUtil.swapStudyProtocolIdentifiers("document", sourceIi, targetIi);
        session.flush();
        session.clear();
        // before swap doc1->sp->5L doc2->sp1->4L
        doc = getDocument(session, doc1.getId());
        assertEquals(5L, doc.get(0).getStudyProtocol().getId().longValue());
        doc = getDocument(session, doc2.getId());
        assertEquals(4L, doc.get(0).getStudyProtocol().getId().longValue());
        
    }
    

    @SuppressWarnings("unchecked")
    private List<StudyMilestone> getMileStone(
             final Session session, Long smId) throws HibernateException {
        List<StudyMilestone> sm = session
                .createQuery(
                        "from StudyMilestone sm where sm.id = " + smId).list();
        return sm;
    }
    
    @SuppressWarnings("unchecked")
    private List<Document> getDocument(
             final Session session, Long dId) throws HibernateException {
        List<Document> d = session
                .createQuery(
                        "from Document d where d.id = " + dId).list();
        return d;
    }

}

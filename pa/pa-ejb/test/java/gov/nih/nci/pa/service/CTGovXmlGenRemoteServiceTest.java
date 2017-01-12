package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceBean;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.ErrorCode;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.MockPaRegistryServiceLocator;
import gov.nih.nci.pa.util.PAExceptionAssertHelper;
import gov.nih.nci.pa.util.PaRegistry;

import org.junit.Before;
import org.junit.Test;

public class CTGovXmlGenRemoteServiceTest extends AbstractHibernateTestCase {

    private final CTGovXmlGeneratorServiceBean bean = new CTGovXmlGeneratorServiceBean() {
        @Override
        public String generateCTGovXml(Ii studyProtocolIi) throws PAException {
            if ("999".equals(studyProtocolIi.getExtension())) {
                throw new PAException("some sort of xml failure");
            } else {
                return "xml string";
            }
        }
    };
    private final StudyProtocolServiceLocal studyProtocolService = mock(StudyProtocolServiceLocal.class);
    private final ProtocolQueryServiceLocal protocolQueryService = mock(ProtocolQueryServiceLocal.class);

    @Before
    public void init() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());
        bean.setStudyProtocolService(studyProtocolService);    
        bean.setProtocolQueryService(protocolQueryService);
        when(
                protocolQueryService
                        .getTrialSummaryByStudyProtocolId(any(Long.class)))
                .thenReturn(new StudyProtocolQueryDTO());        
    }

    @Test
    public void testErrorOutOnTrialNotFound() throws PAException {
        when(studyProtocolService.getStudyProtocol(any(Ii.class))).thenReturn(null);
        try {
            bean.getCTGovXml(IiConverter.convertToStudyProtocolIi(1L));
            fail();
        } catch (PAException pe) {
            PAExceptionAssertHelper.assertErrorCode(pe, ErrorCode.PA_DATA_001);
        }
    }

    @Test
    public void testErrorOutOnTrialNotOwnership() throws PAException {
        setupRegUserMock(false);
        StudyProtocolDTO dto = new StudyProtocolDTO();
        dto.setIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        when(studyProtocolService.getStudyProtocol(any(Ii.class))).thenReturn(dto);
        try {
            bean.getCTGovXml(dto.getIdentifier());
            fail();
        } catch (PAException pe) {
            PAExceptionAssertHelper.assertErrorCode(pe, ErrorCode.PA_USER_002);
        }
    }

    @Test
    public void testErrorOutOnTrialProcessingStatus() throws PAException {
        setupRegUserMock(true);
        DocumentWorkflowStatusDTO dwDto = new DocumentWorkflowStatusDTO();
        dwDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ACCEPTED));
        DocumentWorkflowStatusServiceLocal dwSvc = mock(DocumentWorkflowStatusServiceLocal.class);
        when(dwSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(dwDto);
        bean.setDocumentWorkflowStatusService(dwSvc);
        StudyProtocolDTO dto = new StudyProtocolDTO();
        dto.setIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        when(studyProtocolService.getStudyProtocol(any(Ii.class))).thenReturn(dto);
        try {
            bean.getCTGovXml(dto.getIdentifier());
            fail();
        } catch (PAException pe) {
            PAExceptionAssertHelper.assertErrorCode(pe, ErrorCode.PA_DATA_002);
        }
    }

    @Test
    public void testErrorOutOnXmlFailure() throws PAException {
        setupRegUserMock(true);
        DocumentWorkflowStatusDTO dwDto = new DocumentWorkflowStatusDTO();
        dwDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE));
        DocumentWorkflowStatusServiceLocal dwSvc = mock(DocumentWorkflowStatusServiceLocal.class);
        when(dwSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(dwDto);
        bean.setDocumentWorkflowStatusService(dwSvc);
        StudyProtocolDTO dto = new StudyProtocolDTO();
        dto.setIdentifier(IiConverter.convertToStudyProtocolIi(999L));
        when(studyProtocolService.getStudyProtocol(any(Ii.class))).thenReturn(dto);
        try {
            bean.getCTGovXml(dto.getIdentifier());
        } catch (PAException pe) {
            PAExceptionAssertHelper.assertErrorCode(pe, ErrorCode.PA_XML_001, "some sort of xml failure");
        }
    }

    @Test
    public void testSuccess() throws PAException {
        setupRegUserMock(true);
        DocumentWorkflowStatusDTO dwDto = new DocumentWorkflowStatusDTO();
        dwDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE));
        DocumentWorkflowStatusServiceLocal dwSvc = mock(DocumentWorkflowStatusServiceLocal.class);
        when(dwSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(dwDto);
        bean.setDocumentWorkflowStatusService(dwSvc);
        StudyProtocolDTO dto = new StudyProtocolDTO();
        dto.setIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        when(studyProtocolService.getStudyProtocol(any(Ii.class))).thenReturn(dto);
        assertEquals("xml string", bean.getCTGovXml(dto.getIdentifier()).getValue());
    }

    @Test
    public void testNCIAssignedIdentifier() throws PAException {
        setupRegUserMock(true);
        Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(1L);
        DocumentWorkflowStatusDTO dwDto = new DocumentWorkflowStatusDTO();
        dwDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE));
        DocumentWorkflowStatusServiceLocal dwSvc = mock(DocumentWorkflowStatusServiceLocal.class);
        when(dwSvc.getCurrentByStudyProtocol(studyProtocolIi)).thenReturn(dwDto);
        bean.setDocumentWorkflowStatusService(dwSvc);
        StudyProtocolDTO dto = new StudyProtocolDTO();
        dto.setIdentifier(studyProtocolIi);
        Ii nciAssignedIdentifier = new Ii();
        nciAssignedIdentifier.setExtension("NCI-2011-00001");
        nciAssignedIdentifier.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        nciAssignedIdentifier.setIdentifierName(IiConverter.STUDY_PROTOCOL_IDENTIFIER_NAME);
        when(studyProtocolService.getStudyProtocol(any(Ii.class))).thenReturn(dto);
        assertEquals("xml string", bean.getCTGovXml(nciAssignedIdentifier).getValue());
    }

    @Test
    public void testOutErrorOnBadIiRootAndId() throws PAException {
        StudyProtocolBeanLocal spBean = new StudyProtocolBeanLocal();
        bean.setStudyProtocolService(spBean);
        setupRegUserMock(true);
        DocumentWorkflowStatusDTO dwDto = new DocumentWorkflowStatusDTO();
        dwDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE));
        DocumentWorkflowStatusServiceLocal dwSvc = mock(DocumentWorkflowStatusServiceLocal.class);
        when(dwSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(dwDto);
        bean.setDocumentWorkflowStatusService(dwSvc);
        try {
            bean.getCTGovXml(IiConverter.convertToStudyProtocolIi(999L));
            fail();
        } catch (PAException paEx) {
            PAExceptionAssertHelper.assertErrorCode(paEx, ErrorCode.PA_DATA_001);
        }
        Ii id = StudyProtocolTestHelper.prepareStudyProtocol(spBean, "NCI-2011-0000", "CTEP_ID", "DCP_ID",
                "NCT_ID", true);
        assertEquals("xml string", bean.getCTGovXml(id).getValue());
        id = new Ii();
        id.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        id.setExtension("NCI-2011-0001");
        try {
            bean.getCTGovXml(IiConverter.convertToStudyProtocolIi(999L));
            fail();
        } catch (PAException paEx) {
            PAExceptionAssertHelper.assertErrorCode(paEx, ErrorCode.PA_DATA_001);
        }
        id.setExtension("NCI-2011-0000");
        assertEquals("xml string", bean.getCTGovXml(id).getValue());
        id = new Ii();
        id.setRoot(IiConverter.CTEP_STUDY_PROTOCOL_ROOT);
        id.setExtension("NOT_CTEP_ID");
        try {
            bean.getCTGovXml(IiConverter.convertToStudyProtocolIi(999L));
            fail();
        } catch (PAException paEx) {
            PAExceptionAssertHelper.assertErrorCode(paEx, ErrorCode.PA_DATA_001);
        }
        id.setExtension("CTEP_ID");
        assertEquals("xml string", bean.getCTGovXml(id).getValue());
        id = new Ii();
        id.setRoot(IiConverter.DCP_STUDY_PROTOCOL_ROOT);
        id.setExtension("NOT_DCP_ID");
        try {
            bean.getCTGovXml(IiConverter.convertToStudyProtocolIi(999L));
            fail();
        } catch (PAException paEx) {
            PAExceptionAssertHelper.assertErrorCode(paEx, ErrorCode.PA_DATA_001);
        }
        id.setExtension("DCP_ID");
        assertEquals("xml string", bean.getCTGovXml(id).getValue());
        id = new Ii();
        id.setRoot(IiConverter.NCT_STUDY_PROTOCOL_ROOT);
        id.setExtension("NOT_NCT_ID");
        try {
            bean.getCTGovXml(IiConverter.convertToStudyProtocolIi(999L));
            fail();
        } catch (PAException paEx) {
            PAExceptionAssertHelper.assertErrorCode(paEx, ErrorCode.PA_DATA_001);
        }
        id.setExtension("NCT_ID");
        assertEquals("xml string", bean.getCTGovXml(id).getValue());

    }

    private void setupRegUserMock(final boolean isTrialOwner) {
        MockPaRegistryServiceLocator mpr = new MockPaRegistryServiceLocator() {
            @Override
            public RegistryUserServiceLocal getRegistryUserService() {
                RegistryUserServiceLocal regSvc = mock(RegistryUserServiceLocal.class);
                  try {
                      when(regSvc.isTrialOwner(any(Long.class), any(Long.class))).thenReturn(isTrialOwner);
                      when(regSvc.getUser(any(String.class))).thenReturn(new RegistryUser());
                  } catch (PAException e) {
                      e.printStackTrace();
                  }
                return regSvc;
            }
        };
        PaRegistry.getInstance().setServiceLocator(mpr);
    }
}

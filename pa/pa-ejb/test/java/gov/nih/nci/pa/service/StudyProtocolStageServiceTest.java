package gov.nih.nci.pa.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyFundingStageDTO;
import gov.nih.nci.pa.iso.dto.StudyIndIdeStageDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolStageDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.TestSchema;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author Reshma Koganti
 *
 */
public class StudyProtocolStageServiceTest extends AbstractHibernateTestCase {
    private final StudyProtocolStageBeanLocal bean = new StudyProtocolStageBeanLocal();
    private Ii spIi;
    private final Ii id = IiConverter.convertToIi(1L);
    private final MailManagerServiceLocal mailManagerSerivceLocal = mock(MailManagerServiceLocal.class);
    private final LookUpTableServiceRemote lookUpTableService = mock(LookUpTableServiceRemote.class); 
    private final RegistryUserServiceLocal registryUserServiceLocal = mock(RegistryUserServiceLocal.class);
    
    
    @Before
    public void setUp() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();
        spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
     }
    @Test
    public void getTest() throws PAException {
        StudyProtocolStageDTO dto = bean.get(id);
        assertTrue(dto !=null);
        assertEquals("1", dto.getIdentifier().getExtension());
    }
    
    @Test
    public void searchTest() throws PAException, TooManyResultsException {
        StudyProtocolStageDTO dto = new StudyProtocolStageDTO();
        dto.setIdentifier(id);
        dto.setLeadOrganizationIdentifier(id);
        dto.setPhaseAdditionalQualifierCode(CdConverter.convertToCd(PhaseAdditionalQualifierCode.PILOT));
        dto.setPhaseCode(CdConverter.convertToCd(PhaseCode.I));
        LimitOffset offset = new LimitOffset(5, 0);
        List<StudyProtocolStageDTO> list = bean.search(dto, offset);
        assertTrue(!list.isEmpty());
        assertTrue(list.size()==1);
    }
    
    @Test
    public void deleteTest() throws PAException {
        StudyProtocolStageDTO dto = bean.get(id);
        assertTrue(dto !=null);
        bean.delete(id);
        try {
            bean.get(id);
            fail("Ii could not be found.");
        }catch (PAException e) {
            //expected, testing duplication
        }
    }
    
    @Test
    public void getGrantsByStudyProtocolStageTest() throws PAException {
        List<StudyFundingStageDTO> dtoList = bean.getGrantsByStudyProtocolStage(id);
        assertTrue(!dtoList.isEmpty());
        assertEquals("Code", dtoList.get(0).getFundingMechanismCode().getCode());
    }
    
    @Test 
    public void getIndIdesByStudyProtocolStageTest() throws PAException {
        List<StudyIndIdeStageDTO> dtoList = bean.getIndIdesByStudyProtocolStage(id);
        assertTrue(!dtoList.isEmpty());
        assertEquals("CBER", dtoList.get(0).getGrantorCode().getCode());
    }
    
    @Test
    public void createTest() throws PAException {
        StudyProtocolStageDTO dto = new StudyProtocolStageDTO();
        dto.setLeadOrganizationIdentifier(id);
        dto.setLocalProtocolIdentifier(StConverter.convertToSt("123"));
        dto.setNctIdentifier(StConverter.convertToSt("NCT-123"));
        dto.setAccrualDiseaseCodeSystem(StConverter.convertToSt("SDC"));
        StudyFundingStageDTO fundingDto = new StudyFundingStageDTO();
        fundingDto.setFundingMechanismCode(CdConverter.convertStringToCd("CODE"));
        List<StudyFundingStageDTO> fundDTOs = new ArrayList<StudyFundingStageDTO>();
        fundDTOs.add(fundingDto);
        StudyIndIdeStageDTO indStageDto = new StudyIndIdeStageDTO();
        indStageDto.setGrantorCode(CdConverter.convertToCd(GrantorCode.CBER));
        List<StudyIndIdeStageDTO> indDTOs = new ArrayList<StudyIndIdeStageDTO>();
        indDTOs.add(indStageDto);
        DocumentDTO documentDto = new DocumentDTO();
        documentDto.setActiveIndicator(BlConverter.convertToBl(false));
        documentDto.setOriginal(BlConverter.convertToBl(true));
        documentDto.setStudyInboxIdentifier(id);
        documentDto.setFileName(StConverter.convertToSt("fileName"));
        String str = "Text";
        documentDto.setText(EdConverter.convertToEd(str.getBytes()));
        documentDto.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.OTHER));
        List<DocumentDTO> docDTOs = new ArrayList<DocumentDTO>();
        docDTOs.add(documentDto);
        Ii newId = bean.create(dto, fundDTOs, indDTOs, docDTOs);
        assertTrue(newId!=null);
    }
    
    @Test
    public void updateTest() throws PAException {
        StudyProtocolStageDTO dto = new StudyProtocolStageDTO();
        dto.setIdentifier(id);
        dto.setLeadOrganizationIdentifier(id);
        dto.setLocalProtocolIdentifier(StConverter.convertToSt("1"));
        dto.setNctIdentifier(StConverter.convertToSt("NCT-123"));
        dto.setAccrualDiseaseCodeSystem(StConverter.convertToSt("SDC"));
        StudyFundingStageDTO fundingDto = new StudyFundingStageDTO();
        fundingDto.setIdentifier(id);
        fundingDto.setFundingMechanismCode(CdConverter.convertStringToCd("CODE"));
        List<StudyFundingStageDTO> fundDTOs = new ArrayList<StudyFundingStageDTO>();
        fundDTOs.add(fundingDto);
        StudyIndIdeStageDTO indStageDto = new StudyIndIdeStageDTO();
        indStageDto.setIdentifier(id);
        indStageDto.setGrantorCode(CdConverter.convertToCd(GrantorCode.CBER));
        List<StudyIndIdeStageDTO> indDTOs = new ArrayList<StudyIndIdeStageDTO>();
        indDTOs.add(indStageDto);
        DocumentDTO documentDto = new DocumentDTO();
        documentDto.setIdentifier(id);
        documentDto.setActiveIndicator(BlConverter.convertToBl(false));
        documentDto.setOriginal(BlConverter.convertToBl(true));
        documentDto.setStudyInboxIdentifier(id);
        documentDto.setFileName(StConverter.convertToSt("fileName"));
        String str = "Text";
        documentDto.setText(EdConverter.convertToEd(str.getBytes()));
        documentDto.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.OTHER));
        List<DocumentDTO> docDTOs = new ArrayList<DocumentDTO>();
        docDTOs.add(documentDto);
        StudyProtocolStageDTO returnDto = bean.update(dto, fundDTOs, indDTOs, docDTOs);
        assertEquals(id, returnDto.getIdentifier());
        assertEquals("1", returnDto.getLeadOrganizationIdentifier().getExtension());
       
    }
    
}

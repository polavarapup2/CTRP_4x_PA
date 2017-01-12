package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.services.family.FamilyServiceRemote;

import org.hibernate.Session;

/**
 * 
 * @author Reshma Koganti
 *
 */
public class FamilyProgramCodeServiceTest extends AbstractHibernateTestCase {
    private FamilyProgramCodeBeanLocal familyProgramCodeBeanLocal;
    private FamilyServiceRemote fs;
    private LookUpTableServiceRemote lookUpTableService = mock(LookUpTableServiceRemote.class);
    @Before
    public void setUp() throws Exception {
        familyProgramCodeBeanLocal = new FamilyProgramCodeBeanLocal();
        fs = mock(FamilyServiceRemote.class);
        PoServiceLocator psl = mock(PoServiceLocator.class);
        when(psl.getFamilyService()).thenReturn(fs);
        PoRegistry.getInstance().setPoServiceLocator(psl);
        TestSchema.primeData();
        familyProgramCodeBeanLocal.setLookUpTableService(lookUpTableService);
        PaHibernateUtil.enableAudit();
    }
    
    @Test
    public void getFamilyDTOByPoIdTest() throws Exception {
        gov.nih.nci.services.family.FamilyDTO familyDTO = new gov.nih.nci.services.family.FamilyDTO();
        familyDTO.setIdentifier(IiConverter.convertToIi(1L));
        familyDTO.setName(EnOnConverter.convertToEnOn("Family"));
        when(fs.getFamily(any(Ii.class))).thenReturn(familyDTO);
        FamilyDTO family = familyProgramCodeBeanLocal.getFamilyDTOByPoId(1L);
        assertEquals(1, family.getPoId().longValue());
    }
    
    @Test
    public void getFamilyDTOByPoIdTestFromPA() throws Exception {
         TestSchema.createFamily(2L);
         FamilyDTO family = familyProgramCodeBeanLocal.getFamilyDTOByPoId(2L);
         assertEquals(2, family.getPoId().longValue());
    }
    
    @Test
    public void populateTest() throws Exception {
        deleteAllRecords();
        TestSchema.createFamily(2L);
        when(lookUpTableService.getPropertyValue("programcodes.reporting.default.end_date")).thenReturn("12/31/2015");
        when(lookUpTableService.getPropertyValue("programcodes.reporting.default.length")).thenReturn("12");
        List<gov.nih.nci.services.family.FamilyDTO> familyList = new ArrayList<gov.nih.nci.services.family.FamilyDTO>();
        gov.nih.nci.services.family.FamilyDTO dto= new gov.nih.nci.services.family.FamilyDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        dto.setName(EnOnConverter.convertToEnOn("Family1"));
        dto.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
        familyList.add(dto);
        when(fs.search(any(gov.nih.nci.services.family.FamilyDTO.class), any(LimitOffset.class))).thenReturn(familyList);
        int value = getFamily();
        assertEquals(2, value);
        familyProgramCodeBeanLocal.populate();
        value = getFamily();
        assertEquals(3, value);
        assertTrue(getAuditDetails("family") >= 1);
    }
    
    private int getFamily() {
        Session  session = PaHibernateUtil.getCurrentSession();
            Query qry = session.createQuery("FROM Family f ");
            return qry.list().size();
    }
    
    @Test
    public void updateTest() throws Exception {
        deleteAllRecords();
        TestSchema.createFamily(2L);
        gov.nih.nci.services.family.FamilyDTO familyDTO = new gov.nih.nci.services.family.FamilyDTO();
        familyDTO.setIdentifier(IiConverter.convertToIi(1L));
        familyDTO.setName(EnOnConverter.convertToEnOn("Family"));
        when(fs.getFamily(any(Ii.class))).thenReturn(familyDTO);
        FamilyDTO familyDto = new FamilyDTO(2L, new Date(), 12);
        FamilyDTO family = familyProgramCodeBeanLocal.update(familyDto);
        assertEquals(2, family.getPoId().longValue());
        assertTrue(getAuditDetails("family") >= 1);// we are creating 2 families after deleting audit records
    }
    
    @Test
    public void createTest() throws Exception {
        FamilyDTO familyDto = new FamilyDTO(1L, new Date(), 12);
        FamilyDTO dto = familyProgramCodeBeanLocal.create(familyDto);
        assertEquals(1, dto.getPoId().longValue());
        assertTrue(getAuditDetails("family") >= 1);
    }
    
    @Test
    public void createProgramCodeTest() throws Exception {
        FamilyDTO familyDto = new FamilyDTO(4L, new Date(), 12);
        ProgramCodeDTO programCodeDTO = new ProgramCodeDTO();
        programCodeDTO.setProgramCode("PG1");
        programCodeDTO.setProgramName("PG");
        programCodeDTO.setActive(true);
        gov.nih.nci.services.family.FamilyDTO familyDTO = new gov.nih.nci.services.family.FamilyDTO();
        familyDTO.setIdentifier(IiConverter.convertToIi(1L));
        familyDTO.setName(EnOnConverter.convertToEnOn("Family"));
        when(fs.getFamily(any(Ii.class))).thenReturn(familyDTO);
        int value = getProgramCode("PG1");
        assertEquals(0, value);
        deleteAllRecords();
        familyProgramCodeBeanLocal.createProgramCode(familyDto, programCodeDTO);
        value = getProgramCode("PG1");
        assertEquals(1, value);
        assertEquals(1,getAuditDetails("program_code"));
    }
    
    private int getProgramCode(String programCode) {
        Session  session = PaHibernateUtil.getCurrentSession();
            Query qry = session.createQuery("FROM ProgramCode p where programCode = :programCode");
            qry.setParameter("programCode", programCode);
            return qry.list().size();
    }
    
    private int getAuditDetails(String entityName) throws SQLException {
        Session  session = PaHibernateUtil.getCurrentSession();
        Query qry = session.createQuery("from AuditLogRecord where entityName = :entityName");
        qry.setParameter("entityName", entityName);
        int returnValue = qry.list().size();
        session.flush();
        return returnValue;
    }
    
    private void deleteAllRecords() throws SQLException {
        Session  session = PaHibernateUtil.getCurrentSession();
        Query qry = session.createSQLQuery("delete from auditlogdetail");
        qry.executeUpdate();
        
         qry = session.createSQLQuery("delete from auditlogrecord");
        qry.executeUpdate();
        session.flush();
    }
    

    @Test
    public void createProgramCodeTestException() throws Exception {
        FamilyDTO familyDto = new FamilyDTO(4L, new Date(), 12);
        ProgramCodeDTO programCodeDTO = new ProgramCodeDTO();
        programCodeDTO.setProgramCode("PG1");
        programCodeDTO.setProgramName("PG");
        programCodeDTO.setActive(true);
        gov.nih.nci.services.family.FamilyDTO familyDTO = new gov.nih.nci.services.family.FamilyDTO();
        familyDTO.setIdentifier(IiConverter.convertToIi(1L));
        familyDTO.setName(EnOnConverter.convertToEnOn("Family"));
        when(fs.getFamily(any(Ii.class))).thenReturn(familyDTO);
        familyProgramCodeBeanLocal.createProgramCode(familyDto, programCodeDTO);
        int value = getProgramCode("PG1");
        assertEquals(1, value);
        try {
            familyProgramCodeBeanLocal.createProgramCode(familyDto, programCodeDTO);
        } catch (Exception e) {
            
        }
     }
    
    @Test
    public void updateProgramCodeTest()  throws Exception {
        FamilyDTO familyDto = new FamilyDTO(4L, new Date(), 12);
        ProgramCodeDTO programCodeDTO = new ProgramCodeDTO();
        programCodeDTO.setProgramCode("PG1");
        programCodeDTO.setProgramName("PG");
        programCodeDTO.setActive(true);
        gov.nih.nci.services.family.FamilyDTO familyDTO = new gov.nih.nci.services.family.FamilyDTO();
        familyDTO.setIdentifier(IiConverter.convertToIi(1L));
        familyDTO.setName(EnOnConverter.convertToEnOn("Family"));
        when(fs.getFamily(any(Ii.class))).thenReturn(familyDTO);
        ProgramCodeDTO existingProgramCodeDTO = familyProgramCodeBeanLocal.createProgramCode(familyDto, programCodeDTO);
        ProgramCodeDTO updatedProgramCodeDTO = new ProgramCodeDTO();
        updatedProgramCodeDTO.setProgramCode("PG2");
        updatedProgramCodeDTO.setProgramName("PG");
        updatedProgramCodeDTO.setActive(true);
        int value = getProgramCode("PG2");
        assertEquals(0, value);
        deleteAllRecords();
        familyProgramCodeBeanLocal.updateProgramCode(familyDto, existingProgramCodeDTO, updatedProgramCodeDTO);
        value = getProgramCode("PG2");
        assertEquals(1, value);
        assertEquals(1,getAuditDetails("program_code"));
    }
}

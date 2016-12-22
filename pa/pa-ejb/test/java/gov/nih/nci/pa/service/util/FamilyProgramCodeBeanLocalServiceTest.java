/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.Family;
import gov.nih.nci.pa.domain.ProgramCode;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.util.AbstractEjbTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.util.Iterator;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author gundalar
 * 
 */
public class FamilyProgramCodeBeanLocalServiceTest extends AbstractEjbTestCase {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    FamilyProgramCodeBeanLocal bean;
    MockLookUpTableServiceBean lookUpTableServiceRemote;

    @Before
    public void init() throws Exception {
        bean = (FamilyProgramCodeBeanLocal) getEjbBean(FamilyProgramCodeBeanLocal.class);
        lookUpTableServiceRemote = new MockLookUpTableServiceBean();

        bean.setLookUpTableService(lookUpTableServiceRemote);
        TestSchema.primeData();        
        PaHibernateUtil.getCurrentSession().flush();

        UsernameHolder
                .setUserCaseSensitive("/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SuAbstractor");
    }
    
    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.FamilyProgramCodeBeanLocal#createProgramCode(gov.nih.nci.pa.dto.FamilyDTO,
     * gov.nih.nci.pa.iso.dto.ProgramCodeDTO)}
     * 
     * @throws PAException
     */
   @Test
   public final void testCreateNewProgramCode() throws PAException {
       FamilyDTO familyDTO = bean.getFamilyDTOByPoId(-1L);
       assertEquals(6,familyDTO.getProgramCodes().size());
       ProgramCodeDTO dto = new ProgramCodeDTO();
       dto.setProgramCode("PG1");
       dto.setProgramName("Program Name1");
       bean.createProgramCode(familyDTO,dto);
       
       //verify that program code is successfully created and added to family
       familyDTO = bean.getFamilyDTOByPoId(-1L);
       assertEquals(7,familyDTO.getProgramCodes().size());
       
   }
   
   
   /**
    * Test method for
    * {@link gov.nih.nci.pa.service.util.FamilyProgramCodeBeanLocal#createProgramCode(gov.nih.nci.pa.dto.FamilyDTO,
    * gov.nih.nci.pa.iso.dto.ProgramCodeDTO)}
    * 
    * @throws PAException
    */   
   @Test
   public final void testCreateDuplicateProgramCodeException() throws PAException {
       
       thrown.expect(PAValidationException.class);
       thrown.expectMessage(FamilyProgramCodeBeanLocal.DUPE_PROGRAM_CODE);

       FamilyDTO familyDTO = bean.getFamilyDTOByPoId(-1L);
       assertEquals(6,familyDTO.getProgramCodes().size());
       ProgramCodeDTO dto = new ProgramCodeDTO();
       dto.setProgramCode("1");
       dto.setProgramName("Program Name1");
       bean.createProgramCode(familyDTO,dto);
   }

    @Test
   public void testPopulate() throws Exception {
        //Given there are two new records in PO  and 1 family in PA
       int count1 = PaHibernateUtil.getCurrentSession().createQuery("select fm from Family fm").list().size();

       //when I call populate
       bean.populate();

       //then the number of families should be 3
       int count2 = PaHibernateUtil.getCurrentSession().createQuery("select fm from Family fm").list().size();

        assertEquals(1, count1);
        assertEquals(count2, count1 + 2);

        //If populate is called again,
        bean.populate();

        //Then no new records are created
        int count3 = PaHibernateUtil.getCurrentSession().createQuery("select fm from Family fm").list().size();
        assertEquals(count2, count3) ;

   }
    
    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.FamilyProgramCodeBeanLocal#updateProgramCode(gov.nih.nci.pa.dto.FamilyDTO,
     * gov.nih.nci.pa.iso.dto.ProgramCodeDTO,gov.nih.nci.pa.iso.dto.ProgramCodeDTO)}
     * 
     * @throws PAException
     */
   @Test
   public final void testUpdateProgramCode() throws PAException {
       FamilyDTO familyDTO = bean.getFamilyDTOByPoId(-1L);
       assertEquals(6,familyDTO.getProgramCodes().size());
       
       String existingProgramCodeValue = "2";
       // get existing program code
       ProgramCodeDTO existingProgramCodeDTO = findProgramCodeGivenCode(familyDTO, existingProgramCodeValue);
       assertNotNull(existingProgramCodeDTO);
       
       String nonExistingProgramCodeValue = "PG2";
       String nonExistingProgramCodeName = "Updated Program Name";
       assertNull(findProgramCodeGivenCode(familyDTO, nonExistingProgramCodeValue));
       
       ProgramCodeDTO changedProgramCodeDTO = new ProgramCodeDTO();
       changedProgramCodeDTO.setProgramCode(nonExistingProgramCodeValue);
       changedProgramCodeDTO.setProgramName(nonExistingProgramCodeName);
       
       bean.updateProgramCode(familyDTO, existingProgramCodeDTO, changedProgramCodeDTO);
       
       // re fetch updated familyDTO
       familyDTO = bean.getFamilyDTOByPoId(-1L);
       // assert same number of program codes are present in family
       assertEquals(6,familyDTO.getProgramCodes().size());
       // program code with old code value is no longer present 
       assertNull(findProgramCodeGivenCode(familyDTO, existingProgramCodeValue));
       // verify program code with new code value is present
       ProgramCodeDTO savedProgramCodeDTO = findProgramCodeGivenCode(familyDTO, nonExistingProgramCodeValue);
       assertNotNull(savedProgramCodeDTO);
       
       // assert program code id and status are same but code value and name got updated
       assertEquals(existingProgramCodeDTO.getId(),savedProgramCodeDTO.getId());
       assertEquals(existingProgramCodeDTO.isActive(),savedProgramCodeDTO.isActive());
       assertEquals(nonExistingProgramCodeValue,savedProgramCodeDTO.getProgramCode());
       assertEquals(nonExistingProgramCodeName,savedProgramCodeDTO.getProgramName());
   }
   
   
   /**
    * Test method for
    * {@link gov.nih.nci.pa.service.util.FamilyProgramCodeBeanLocal#updateProgramCode(gov.nih.nci.pa.dto.FamilyDTO,
    * gov.nih.nci.pa.iso.dto.ProgramCodeDTO,gov.nih.nci.pa.iso.dto.ProgramCodeDTO)}
    * 
    * @throws PAException
    */
  @Test
  public final void testUpdateProgramCodeDuplicateValidation() throws PAException {
      
      thrown.expect(PAValidationException.class);
      thrown.expectMessage(FamilyProgramCodeBeanLocal.DUPE_PROGRAM_CODE);
      
      FamilyDTO familyDTO = bean.getFamilyDTOByPoId(-1L);
      assertEquals(6,familyDTO.getProgramCodes().size());
      
      String existingProgramCodeValue = "2";
      // get existing program code
      ProgramCodeDTO existingProgramCodeDTO = findProgramCodeGivenCode(familyDTO, existingProgramCodeValue);
      assertNotNull(existingProgramCodeDTO);
      
      String anotherExistingProgramCodeValue = "3";
      assertNotNull(findProgramCodeGivenCode(familyDTO, anotherExistingProgramCodeValue));
      
      ProgramCodeDTO changedProgramCodeDTO = new ProgramCodeDTO();
      changedProgramCodeDTO.setProgramCode(anotherExistingProgramCodeValue);
      
      // updating to an existing program code should throw PAValidation exception
      
      bean.updateProgramCode(familyDTO, existingProgramCodeDTO, changedProgramCodeDTO);
  }
   
   
   private ProgramCodeDTO findProgramCodeGivenCode(FamilyDTO familyDTO,String code){
       Iterator<ProgramCodeDTO> iterator = familyDTO.getProgramCodes().iterator();
       
       while(iterator.hasNext()){
           ProgramCodeDTO matchingProgramCodeDTO = iterator.next();
           if(matchingProgramCodeDTO.getProgramCode().equalsIgnoreCase(code)){
               return matchingProgramCodeDTO;
           }
       }
       
       return null;
   }
   
   
   private ProgramCode findDomainProgramCodeGivenCode(Family family,String code){
       Iterator<ProgramCode> iterator = family.getProgramCodes().iterator();
       
       while(iterator.hasNext()){
           ProgramCode matchingProgramCode = iterator.next();
           if(matchingProgramCode.getProgramCode().equalsIgnoreCase(code)){
               return matchingProgramCode;
           }
       }
       
       return null;
   }
   
   
   /**
    * Test method for
    * {@link gov.nih.nci.pa.service.util.FamilyProgramCodeBeanLocal#deleteProgramCode(gov.nih.nci.pa.dto.FamilyDTO,
    * gov.nih.nci.pa.iso.dto.ProgramCodeDTO)}
    * 
    * @throws PAException
    */
  @Test
  public final void testDeleteProgramCode() throws PAException {
      // add a new program code which is not associated to any trial.
      FamilyDTO familyDTO = bean.getFamilyDTOByPoId(-1L);
      assertEquals(6,familyDTO.getProgramCodes().size());
      ProgramCodeDTO dto = new ProgramCodeDTO();
      String programCodeValue = "PG1";
      dto.setProgramCode(programCodeValue);
      dto.setProgramName("Program Name1");
      bean.createProgramCode(familyDTO,dto);
      
      //verify that program code is successfully created and added to family
      familyDTO = bean.getFamilyDTOByPoId(-1L);
      assertEquals(7,familyDTO.getProgramCodes().size());
      // get existing program code
      ProgramCodeDTO existingProgramCodeDTO = findProgramCodeGivenCode(familyDTO, programCodeValue);
      assertNotNull(existingProgramCodeDTO);
      
      assertFalse(bean.isProgramCodeAssociatedWithATrial(existingProgramCodeDTO));
      
      bean.deleteProgramCode(familyDTO, existingProgramCodeDTO);
      // re fetch updated familyDTO
      familyDTO = bean.getFamilyDTOByPoId(-1L);
      assertNull(findProgramCodeGivenCode(familyDTO, programCodeValue));
      assertEquals(6,familyDTO.getProgramCodes().size());
  }
  
  /**
   * Test method for
   * {@link gov.nih.nci.pa.service.util.FamilyProgramCodeBeanLocal#deleteProgramCode(gov.nih.nci.pa.dto.FamilyDTO,
   * gov.nih.nci.pa.iso.dto.ProgramCodeDTO)}
   * 
   * @throws PAException
   */
 @Test
 public final void testDeleteProgramCodeErrorScenario() throws PAException {
     
     thrown.expect(PAValidationException.class);
     thrown.expectMessage(FamilyProgramCodeBeanLocal.NOT_FOUND_PROGRAM_CODE);
     
     FamilyDTO familyDTO = bean.getFamilyDTOByPoId(-1L);
     assertEquals(6,familyDTO.getProgramCodes().size());
     
     ProgramCodeDTO nonExistingProgramCodeDTO = new ProgramCodeDTO();
     nonExistingProgramCodeDTO.setId(-1111L);
     nonExistingProgramCodeDTO.setProgramCode("PG-nonexisting");
     nonExistingProgramCodeDTO.setProgramName("Program Name-nonexisting");
     
     // verify program code doesn't exist
     assertNull(findProgramCodeGivenCode(familyDTO, "PG-nonexisting"));
    
     bean.deleteProgramCode(familyDTO,nonExistingProgramCodeDTO);
 }
 
    @Test
    public final void testInactiveTrialCodeAssociationIgnored()
            throws PAException {

        Session s = PaHibernateUtil.getCurrentSession();
        final StudyProtocol sp = TestSchema.studyProtocols.get(0);
        DocumentWorkflowStatus dws = TestSchema
                .createDocumentWorkflowStatus(sp);
        s.save(dws);
        s.flush();

        FamilyDTO familyDTO = bean.getFamilyDTOByPoId(-1L);
        String existingProgramCodeValue = "2";
        ProgramCodeDTO existingProgramCodeDTO = findProgramCodeGivenCode(
                familyDTO, existingProgramCodeValue);
        assertTrue(bean
                .isProgramCodeAssociatedWithATrial(existingProgramCodeDTO));

        s.createSQLQuery(
                "update study_protocol set status_code='INACTIVE' where identifier="
                        + sp.getId()).executeUpdate();
        s.flush();

        assertFalse(bean
                .isProgramCodeAssociatedWithATrial(existingProgramCodeDTO));

    }
 
    @Test
    public final void testTerminatedTrialCodeAssociationIgnored()
            throws PAException {

        Session s = PaHibernateUtil.getCurrentSession();
        DocumentWorkflowStatus dws = TestSchema
                .createDocumentWorkflowStatus(TestSchema.studyProtocols.get(0));
        s.save(dws);
        s.flush();

        FamilyDTO familyDTO = bean.getFamilyDTOByPoId(-1L);
        String existingProgramCodeValue = "2";
        ProgramCodeDTO existingProgramCodeDTO = findProgramCodeGivenCode(
                familyDTO, existingProgramCodeValue);
        assertTrue(bean
                .isProgramCodeAssociatedWithATrial(existingProgramCodeDTO));

        dws = TestSchema.createDocumentWorkflowStatus(TestSchema.studyProtocols
                .get(0));
        dws.setStatusCode(DocumentWorkflowStatusCode.SUBMISSION_TERMINATED);
        s.save(dws);
        s.flush();

        assertFalse(bean
                .isProgramCodeAssociatedWithATrial(existingProgramCodeDTO));

    }
 
    @Test
    public final void testRejectedTrialCodeAssociationIgnored()
            throws PAException {

        Session s = PaHibernateUtil.getCurrentSession();
        DocumentWorkflowStatus dws = TestSchema
                .createDocumentWorkflowStatus(TestSchema.studyProtocols.get(0));
        s.save(dws);
        s.flush();

        FamilyDTO familyDTO = bean.getFamilyDTOByPoId(-1L);
        String existingProgramCodeValue = "2";
        ProgramCodeDTO existingProgramCodeDTO = findProgramCodeGivenCode(
                familyDTO, existingProgramCodeValue);
        assertTrue(bean
                .isProgramCodeAssociatedWithATrial(existingProgramCodeDTO));

        dws = TestSchema.createDocumentWorkflowStatus(TestSchema.studyProtocols
                .get(0));
        dws.setStatusCode(DocumentWorkflowStatusCode.REJECTED);
        s.save(dws);
        s.flush();

        assertFalse(bean
                .isProgramCodeAssociatedWithATrial(existingProgramCodeDTO));

    }
  
  
  /**
   * Test method for
   * {@link gov.nih.nci.pa.service.util.FamilyProgramCodeBeanLocal#isProgramCodeAssociatedWithATrial(gov.nih.nci.pa.iso.dto.ProgramCodeDTO)}
   * 
   * @throws PAException
   */
 @Test
 public final void testIsProgramCodeAssociatedWithATrial() throws PAException {
     
     
        Session s = PaHibernateUtil.getCurrentSession();
        s.save(TestSchema
                .createDocumentWorkflowStatus(TestSchema.studyProtocols.get(0)));
        s.flush();
     
     FamilyDTO familyDTO = bean.getFamilyDTOByPoId(-1L);
     assertEquals(6,familyDTO.getProgramCodes().size());
     
     String existingProgramCodeValue = "2";
     // get existing program code
     ProgramCodeDTO existingProgramCodeDTO = findProgramCodeGivenCode(familyDTO, existingProgramCodeValue);
     assertNotNull(existingProgramCodeDTO);
     assertTrue(bean.isProgramCodeAssociatedWithATrial(existingProgramCodeDTO));
     
     
     // add a new program code which is not associated to any trial.
     assertEquals(6,familyDTO.getProgramCodes().size());
     ProgramCodeDTO dto = new ProgramCodeDTO();
     String programCodeValue = "PG1";
     dto.setProgramCode(programCodeValue);
     dto.setProgramName("Program Name1");
     bean.createProgramCode(familyDTO,dto);
     
     //verify that program code is successfully created and added to family
     familyDTO = bean.getFamilyDTOByPoId(-1L);
     assertEquals(7,familyDTO.getProgramCodes().size());
     
     // get newly added program code
     ProgramCodeDTO unassociatedProgramCodeDTO = findProgramCodeGivenCode(familyDTO, programCodeValue);
     assertNotNull(unassociatedProgramCodeDTO);
     
     assertFalse(bean.isProgramCodeAssociatedWithATrial(unassociatedProgramCodeDTO));
 }
 
 /**
  * Test method for
  * {@link gov.nih.nci.pa.service.util.FamilyProgramCodeBeanLocal#inactivateProgramCode(gov.nih.nci.pa.iso.dto.ProgramCodeDTO)}
  * 
  * @throws PAException
  */
@Test
public final void testInactivateProgramCode() throws PAException {
    
    Session s = PaHibernateUtil.getCurrentSession();
    s.save(TestSchema
            .createDocumentWorkflowStatus(TestSchema.studyProtocols.get(0)));
    s.flush();
    
    FamilyDTO familyDTO = bean.getFamilyDTOByPoId(-1L);
    assertEquals(6,familyDTO.getProgramCodes().size());
    
    String existingProgramCodeValue = "2";
    // get existing program code
    ProgramCodeDTO existingProgramCodeDTO = findProgramCodeGivenCode(familyDTO, existingProgramCodeValue);
    assertNotNull(existingProgramCodeDTO);
    assertTrue(bean.isProgramCodeAssociatedWithATrial(existingProgramCodeDTO));
    ProgramCode dbProgramCode = getDbProgramCodeById(existingProgramCodeDTO.getId());
    assertEquals(ActiveInactiveCode.ACTIVE,dbProgramCode.getStatusCode());
    
    bean.inactivateProgramCode(existingProgramCodeDTO);
    //refetch db program code
    dbProgramCode = getDbProgramCodeById(existingProgramCodeDTO.getId());
    
    assertEquals(ActiveInactiveCode.INACTIVE,dbProgramCode.getStatusCode());
    
}

/**
 * @param id the program code identifier
 *            name
 * @return ProgramCode
 */
private static ProgramCode getDbProgramCodeById(Long id) {
    Session session = PaHibernateUtil.getCurrentSession();
    return (ProgramCode) session.get(ProgramCode.class, id);
}

}

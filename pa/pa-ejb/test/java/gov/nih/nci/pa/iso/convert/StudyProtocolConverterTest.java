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
package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.domain.ProgramCode;
import gov.nih.nci.pa.domain.StudyAlternateTitle;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.dto.StudyAlternateTitleDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * test class for studyprotocol converter.
 * @author NAmiruddin
 *
 */
public class StudyProtocolConverterTest extends AbstractHibernateTestCase {

    @Before
    public void init() throws Exception {
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        LookUpTableServiceRemote lookupSvc = mock(LookUpTableServiceRemote.class);
        AnatomicSite as = new AnatomicSite();
        as.setCode("Lung");
        as.setCodingSystem("Summary 4 Anatomic Sites");
        when(lookupSvc.getLookupEntityByCode(any(Class.class), any(String.class))).thenReturn(as);
        when(paRegSvcLoc.getLookUpTableService()).thenReturn(lookupSvc);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
    }

    @Test
    public void convertFromDomainToDTOTest() {
        Session session  = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = TestSchema.createStudyProtocolObj(new StudyProtocol());
        addAlternateTitles(sp);
        addProgramCodes(sp);
        session.save(sp);
        assertNotNull(sp.getId());
        StudyProtocolDTO spDTO = StudyProtocolConverter.convertFromDomainToDTO(sp);
        assertStudyProtocol(sp, spDTO);
        assertStudyAlternateTitles(sp, spDTO);
        assertProgramCodes(sp, spDTO);
    }

    @Test
    public void convertFromDomainToDTOTest1() {
        Session session  = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = TestSchema.createStudyProtocolObj(new StudyProtocol());
        session.save(sp);
        assertNotNull(sp.getId());
        StudyProtocolDTO spDTO = StudyProtocolConverter.convertFromDomainToDTO(sp , new StudyProtocolDTO());
        assertStudyProtocol(sp , spDTO);
    }

    /**
     * @throws PAException
     *
     */
    @Test
    public void convertFromDtoToDomainTest() throws PAException {
        Session session  = PaHibernateUtil.getCurrentSession();
        StudyProtocol create = TestSchema.createStudyProtocolObj(new StudyProtocol());
        addAlternateTitles(create);
        addProgramCodes(create);
        session.save(create);
        assertNotNull(create.getId());

        //convert to DTO
        StudyProtocolDTO spDTO = StudyProtocolConverter.convertFromDomainToDTO(create);
        assertStudyProtocol(create, spDTO);
        assertStudyAlternateTitles(create, spDTO);
        assertProgramCodes(create, spDTO);
        AbstractStudyProtocolConverter.setCsmUserUtil(new MockCSMUserService());
        CSMUserUtil csmUserService = mock(CSMUserService.class);
        CSMUserService.setInstance(csmUserService);
        when(CSMUserService.getInstance().getCSMUser(any(String.class))).thenReturn(null);
        StudyProtocol sp = StudyProtocolConverter.convertFromDTOToDomain(spDTO);
        assertStudyProtocol(sp, spDTO);
        assertStudyAlternateTitles(sp, spDTO);
        assertProgramCodes(sp, spDTO);
    }

    @Test
    public void convertFromDtoToDomainTest1() throws PAException {
        Session session  = PaHibernateUtil.getCurrentSession();
        StudyProtocol create = TestSchema.createStudyProtocolObj(new StudyProtocol());
        session.save(create);
        assertNotNull(create.getId());
        //convert to DTO
        StudyProtocolDTO spDTO = StudyProtocolConverter.convertFromDomainToDTO(create);
        AbstractStudyProtocolConverter.setCsmUserUtil(new MockCSMUserService());        
        StudyProtocol sp = StudyProtocolConverter.convertFromDTOToDomain(spDTO, new StudyProtocol());
        assertStudyProtocol(sp , spDTO);
    }

    /**
     *
     * @param sp sp
     * @param spDTO spDTO
     */
    public void assertStudyProtocol(StudyProtocol sp, StudyProtocolDTO spDTO) {
        BaseStudyProtocolConverterTest baseTest = new BaseStudyProtocolConverterTest();
        baseTest.assertStudyProtocol(sp, spDTO);
        baseTest.assertStudyProtocolDates(sp, spDTO);
        assertEquals(sp.getAcronym(), spDTO.getAcronym().getValue());
        assertEquals(sp.getAccrualReportingMethodCode().getCode(), spDTO.getAccrualReportingMethodCode().getCode());
        assertEquals(sp.getExpandedAccessIndicator(), spDTO.getExpandedAccessIndicator().getValue());
        assertEquals(sp.getPublicDescription(), spDTO.getPublicDescription().getValue());
        assertEquals(sp.getPublicTitle(), spDTO.getPublicTitle().getValue());
        assertEquals(sp.getRecordVerificationDate() ,
                TsConverter.convertToTimestamp(spDTO.getRecordVerificationDate()));
        assertEquals(sp.getScientificDescription(), spDTO.getScientificDescription().getValue());
        assertEquals(sp.getAmendmentReasonCode().getCode() ,spDTO.getAmendmentReasonCode().getCode());
        assertEquals(sp.getStatusCode().getCode() ,spDTO.getStatusCode().getCode());
        assertEquals(sp.getComments() ,spDTO.getComments().getValue());
        assertEquals(sp.getProcessingPriority() ,spDTO.getProcessingPriority().getValue());
        assertEquals(sp.getAssignedUser().getUserId().toString() ,spDTO.getAssignedUser().getExtension());        
    }

    /**
     * Adds alternate titles to study protocol.
     * @param sp study protocol
     */
    private void addAlternateTitles(StudyProtocol sp) {
        StudyAlternateTitle obj1 = new StudyAlternateTitle();
        obj1.setAlternateTitle("Test3");
        obj1.setCategory("Other");
        obj1.setStudyProtocol(sp);
        sp.getStudyAlternateTitles().add(obj1);
        
        StudyAlternateTitle obj2 = new StudyAlternateTitle();
        obj2.setAlternateTitle("Test1");
        obj2.setCategory("Spelling/Formatting Correction");
        obj2.setStudyProtocol(sp);
        sp.getStudyAlternateTitles().add(obj2);
        
        StudyAlternateTitle obj3 = new StudyAlternateTitle();
        obj3.setAlternateTitle("Test2");
        obj3.setCategory("Other");
        obj3.setStudyProtocol(sp);
        sp.getStudyAlternateTitles().add(obj3);
    }
    
    /**
     * Adds program codes to study protocol.
     * @param sp study protocol
     */
    private void addProgramCodes(StudyProtocol sp) {
        ProgramCode programCode1 = new ProgramCode();   
        programCode1.setId(Long.valueOf(12345));
        programCode1.setProgramCode("Code1");
        programCode1.setProgramName("Test1");  
        programCode1.setStatusCode(ActiveInactiveCode.ACTIVE);
        sp.getProgramCodes().add(programCode1);
        
        ProgramCode programCode2 = new ProgramCode();
        programCode2.setId(Long.valueOf(123456));
        programCode2.setProgramCode("Code2");
        programCode2.setProgramName("Test2");
        programCode2.setStatusCode(ActiveInactiveCode.ACTIVE);
        sp.getProgramCodes().add(programCode2);
    }
    
    /**
     * Asserts study alternate title information. 
     * @param sp study protocol 
     * @param spDTO study protocol DTO.
     */
    private void assertStudyAlternateTitles(StudyProtocol sp, StudyProtocolDTO spDTO) {
        assertNotNull(sp.getStudyAlternateTitles());
        assertNotNull(spDTO.getStudyAlternateTitles());
        assertEquals(sp.getStudyAlternateTitles().size(), 
                spDTO.getStudyAlternateTitles().size());
        Iterator<StudyAlternateTitle> itr = sp.getStudyAlternateTitles().iterator();
        for (StudyAlternateTitleDTO dto : spDTO.getStudyAlternateTitles()) {            
            StudyAlternateTitle title = itr.next();
            assertEquals(title.getAlternateTitle(), dto.getAlternateTitle().getValue());
            assertEquals(title.getCategory(), dto.getCategory().getValue());
        }
    }
    
    @Test
    public void convertToDSetCd() throws PAException {
        Set<AnatomicSite> sasList = new HashSet<AnatomicSite>();
        AnatomicSite as1 = new AnatomicSite();
        as1.setCode("Lung");
        as1.setCodingSystem("Summary 4 Anatomic Sites");

        sasList.add(as1);

        DSet<Cd> dset = AnatomicSiteConverter.convertToDSet(sasList);
        List<String> sites = new ArrayList<String>();
        for (Cd cd : dset.getItem()) {
            sites.add(cd.getCode());
            assertEquals("Summary 4 Anatomic Sites", cd.getCodeSystem());
        }
        assertTrue(sites.contains("Lung"));
        Set<AnatomicSite> freshSites = AnatomicSiteConverter.convertToSet(dset);
        sites = new ArrayList<String>();
        for (AnatomicSite site : freshSites) {
            sites.add(site.getCode());
            assertEquals("Summary 4 Anatomic Sites", site.getCodingSystem());
        }
        assertTrue(sites.contains("Lung"));
    }
    
   @Test 
   public void convertFromDTOToDomainWithVariousAmendmentReasonCodes() throws PAException {
       Session session  = PaHibernateUtil.getCurrentSession();
       StudyProtocol create = TestSchema.createStudyProtocolObj(new StudyProtocol());
       session.save(create);
       assertNotNull(create.getId());
       //convert to DTO
       StudyProtocolDTO spDTO = StudyProtocolConverter.convertFromDomainToDTO(create);
       AbstractStudyProtocolConverter.setCsmUserUtil(new MockCSMUserService());
       StudyProtocol sp = StudyProtocolConverter.convertFromDTOToDomain(spDTO, new StudyProtocol());
       assertEquals(sp.getAmendmentReasonCode().getCode() ,spDTO.getAmendmentReasonCode().getCode());
       spDTO.setAmendmentReasonCode(null);
       sp = StudyProtocolConverter.convertFromDTOToDomain(spDTO, sp);
       assertEquals(null ,sp.getAmendmentReasonCode());
   } 
   
   /**
    * Asserts study alternate title information. 
    * @param sp study protocol 
    * @param spDTO study protocol DTO.
    */
   private void assertProgramCodes(StudyProtocol sp, StudyProtocolDTO spDTO) {
       assertNotNull(sp.getProgramCodes());
       assertNotNull(spDTO.getProgramCodes());
       assertEquals("Code1; Code2", spDTO.getProgramCodesAsString());
       assertEquals(sp.getProgramCodes().size(), 
               spDTO.getProgramCodes().size());
       Iterator<ProgramCode> itr = sp.getProgramCodes().iterator();
       for (ProgramCodeDTO dto : spDTO.getProgramCodes()) {            
           ProgramCode pg = itr.next();
           assertEquals(pg.getProgramCode(), dto.getProgramCode());
           assertEquals(pg.getProgramName(), dto.getProgramName());
       }
   }
    
}

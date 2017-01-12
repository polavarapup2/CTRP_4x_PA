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
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.PDQDisease;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.noniso.dto.PDQDiseaseNode;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.TestSchema;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
/**
 * @author hreinhart
 *
 */
public class PDQDiseaseServiceTest extends AbstractHibernateTestCase {
    private final PDQDiseaseBeanLocal bean = new PDQDiseaseBeanLocal();
    private final PDQDiseaseServiceLocal remote = bean;
    private Ii ii;

    @Before
    public void setUp() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();
        ii = IiConverter.convertToIi(TestSchema.pdqDiseaseIds.get(0));
     }

    private void compareDataAttributes(PDQDisease bo1, PDQDisease bo2) {
        assertEquals(bo1.getDiseaseCode(), bo2.getDiseaseCode());
        assertEquals(bo1.getDisplayName(), bo2.getDisplayName());
        assertEquals(bo1.getNtTermIdentifier(), bo2.getNtTermIdentifier());
        assertEquals(bo1.getPreferredName(), bo2.getPreferredName());
        assertEquals(bo1.getStatusCode(), bo2.getStatusCode());
        assertEquals(bo1.getStatusDateRangeLow(), bo2.getStatusDateRangeLow());
    }

    @Test
    public void getTest() throws Exception {
        PDQDiseaseDTO dto = remote.get(ii);
        assertFalse(ISOUtil.isIiNull(dto.getIdentifier()));
    }

    @Test
    public void createTest() throws Exception {
        PDQDisease bo = TestSchema.createPdqDisease("crud");
        assertNull(bo.getId());
        PDQDiseaseDTO dto = bean.convertFromDomainToDto(bo);
        PDQDiseaseDTO resultDto = remote.create(dto);
        PDQDisease resultBo = bean.convertFromDtoToDomain(resultDto);
        compareDataAttributes(bo, resultBo);
        assertNotNull(resultBo.getId());
    }

    @Test
    public void updateTest() throws Exception {
        PDQDiseaseDTO dto = remote.get(ii);
        PDQDisease bo = bean.convertFromDtoToDomain(dto);
        assertFalse("new name".equals(bo.getPreferredName()));
        bo.setPreferredName("new name");
        dto = bean.convertFromDomainToDto(bo);
        PDQDiseaseDTO resultDto = remote.update(dto);
        PDQDisease resultBo = bean.convertFromDtoToDomain(resultDto);
        this.compareDataAttributes(bo, resultBo);
        assertTrue("new name".equals(resultBo.getPreferredName()));
    }

    @Test
    public void deleteTest() throws Exception {
        remote.delete(ii);
        PDQDiseaseDTO dto;
        try {
          dto =  remote.get(ii);
        } catch (PAException e) {
            // expected behavior
            return;
        }
        assertNull(dto);
    }
    @Test
    public void searchTest() throws Exception {
        PDQDiseaseDTO searchCriteria = new PDQDiseaseDTO();
        searchCriteria.setPreferredName(StConverter.convertToSt("Toe*"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("true"));
        searchCriteria.setExactMatch(StConverter.convertToSt("false"));
        List<PDQDiseaseDTO> r = bean.search(searchCriteria);
        assertTrue(0 < r.size());

        searchCriteria.setPreferredName(StConverter.convertToSt("xToe*"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("true"));
        searchCriteria.setExactMatch(StConverter.convertToSt("false"));
        r = bean.search(searchCriteria);
        assertEquals(0, r.size());

        searchCriteria.setPreferredName(StConverter.convertToSt("*Piggy*"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("true"));
        searchCriteria.setExactMatch(StConverter.convertToSt("false"));
        r = bean.search(searchCriteria);
        assertTrue(0 < r.size());

        searchCriteria.setPreferredName(StConverter.convertToSt("Toe Cancer"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("true"));
        searchCriteria.setExactMatch(StConverter.convertToSt("true"));
        r = bean.search(searchCriteria);
        assertFalse("No diseases found.", r.isEmpty());
        assertEquals("1 disease should have been found.", 1, r.size());

        searchCriteria.setPreferredName(StConverter.convertToSt("Cancer"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("false"));
        searchCriteria.setExactMatch(StConverter.convertToSt("true"));
        r = bean.search(searchCriteria);
        assertTrue(r.isEmpty());

        searchCriteria.setPreferredName(StConverter.convertToSt("Cancer"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("false"));
        searchCriteria.setExactMatch(StConverter.convertToSt("false"));
        r = bean.search(searchCriteria);
        assertFalse(r.isEmpty());
        assertEquals(4, r.size());

        searchCriteria.setPreferredName(StConverter.convertToSt("Cancer"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("true"));
        searchCriteria.setExactMatch(StConverter.convertToSt("false"));
        r = bean.search(searchCriteria);
        assertFalse(r.isEmpty());

        searchCriteria.setNtTermIdentifier(null);
        searchCriteria.setPreferredName(null);
        try {
            r = bean.search(searchCriteria);
            fail("Service should throw PAException when searching w/o name and NCIt identifier.  ");
        } catch(PAException e) {
            // expected behavior
        }
        
        //Search by NCIt id        
        searchCriteria.setNtTermIdentifier(StConverter.convertToSt("CTEST"));
        r = bean.search(searchCriteria);
        assertEquals(1, r.size());
        
        searchCriteria.setNtTermIdentifier(StConverter.convertToSt("NONEXISTING"));
        r = bean.search(searchCriteria);
        assertEquals(0, r.size());
        
    }
    @Test
    public void searchDoesNotReturnInactiveTest() throws Exception {
        PDQDiseaseDTO searchCriteria = new PDQDiseaseDTO();
        searchCriteria.setPreferredName(StConverter.convertToSt("Toe*"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("true"));
        searchCriteria.setExactMatch(StConverter.convertToSt("false"));
        List<PDQDiseaseDTO> r = bean.search(searchCriteria);
        assertEquals(1, r.size());

        r.get(0).setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.INACTIVE));
        bean.update(r.get(0));

        r = bean.search(searchCriteria);
        assertEquals(0, r.size());
    }
    
    @Test
    public void getByIds() {
        PDQDisease bo1 = TestSchema.createPdqDisease("1");
        TestSchema.addUpdObject(bo1);
        PDQDisease bo2 = TestSchema.createPdqDisease("2");
        TestSchema.addUpdObject(bo2);
        PDQDisease bo3 = TestSchema.createPdqDisease("3");
        TestSchema.addUpdObject(bo3);
        PDQDisease bo4 = TestSchema.createPdqDisease("4");
        TestSchema.addUpdObject(bo4);        
        List<Long> ids = Arrays.asList(new Long[] {bo2.getId(), bo4.getId()});
        
        List<PDQDisease> result = bean.getByIds(ids);
        
        assertEquals(2, result.size());
        assertTrue(ids.contains(result.get(0).getId()));
        assertTrue(ids.contains(result.get(1).getId()));        
    }
    
    @Test
    public void testgetAllDisplayName() {
        PDQDisease bo1 = TestSchema.createPdqDisease("Disease 1");
        bo1.setDisplayName("Disease 1");
        TestSchema.addUpdObject(bo1);        
        PDQDisease bo2 = TestSchema.createPdqDisease("Disease 2");
        bo2.setDisplayName("Disease 2");
        TestSchema.addUpdObject(bo2);
        assertEquals(3, bean.getAllDisplayNames().size());
        assertTrue(bean.getAllDisplayNames().contains("\"Disease 1\""));
        assertTrue(bean.getAllDisplayNames().contains("\"Disease 2\""));
        assertTrue(bean.getAllDisplayNames().contains("\"menuDisplayName\""));
    }
    
    @Test
    public void testweightedSearchDisease(){
        TestSchema.addUpdObject(TestSchema.createPdqDisease("Disease"));        
        TestSchema.addUpdObject(TestSchema.createPdqDisease("A Disease"));
        TestSchema.addUpdObject(TestSchema.createPdqDisease("Z Disease"));
        TestSchema.addUpdObject(TestSchema.createPdqDisease("Test"));
        PDQDiseaseDTO searchCriteria = new PDQDiseaseDTO();
        searchCriteria.setPreferredName(StConverter.convertToSt("disease"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("false"));
        searchCriteria.setExactMatch(StConverter.convertToSt("false"));
        List<PDQDiseaseNode> r =  bean.weightedSearchDisease(searchCriteria);
        assertEquals(3, r.size());
            
        assertEquals("Disease",r.get(0).getName()); 
        assertEquals("A Disease",r.get(1).getName());
        assertEquals("Z Disease",r.get(2).getName());
        
        // Search for non exitsing disease
        
        searchCriteria.setPreferredName(StConverter.convertToSt("Non existing"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("true"));
        searchCriteria.setExactMatch(StConverter.convertToSt("false"));
        r =  bean.weightedSearchDisease(searchCriteria);
        assertEquals(0, r.size());

    }
    
    @Test
    public void testweightedSearchExact(){
        TestSchema.addUpdObject(TestSchema.createPdqDisease("Disease"));        
        TestSchema.addUpdObject(TestSchema.createPdqDisease("A Disease"));
        TestSchema.addUpdObject(TestSchema.createPdqDisease("Z Disease"));
        TestSchema.addUpdObject(TestSchema.createPdqDisease("Test"));
        PDQDiseaseDTO searchCriteria = new PDQDiseaseDTO();
        searchCriteria.setPreferredName(StConverter.convertToSt("disease"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("false"));
        searchCriteria.setExactMatch(StConverter.convertToSt("true"));
        List<PDQDiseaseNode> r =  bean.weightedSearchDisease(searchCriteria);
        assertEquals(1, r.size());
        
        assertEquals("Disease",r.get(0).getName()); 
      

    }
}

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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
/**
 * @author hreinhart
 *
 */
public class StudyDiseaseServiceTest extends AbstractHibernateTestCase {

    /**
     * Rule for exception.
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private StudyDiseaseBeanLocal bean = new StudyDiseaseBeanLocal();
    private PDQDiseaseServiceLocal pdqDiseaseServiceLocal = new PDQDiseaseBeanLocal();
    private final StringBuilder errorMsg = new StringBuilder();

    /**
     * Initialization method.
     */
    @Before
    public void setUp() {
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();
        bean.setPdqDiseaseServiceLocal(pdqDiseaseServiceLocal);
    }

    /**
     * Test the get method for a existing StudyDisease.
     * @throws PAException if an error occurs
     */
    @Test
    public void testGetExist() throws PAException {
        Ii studyDiseaseIi = IiConverter.convertToStudyDiseaseIi(TestSchema.studyDiseaseIds.get(0));
        StudyDiseaseDTO studyDiseaseDTO = bean.get(studyDiseaseIi);
        assertNotNull("studyDiseaseDTO not found", studyDiseaseDTO);
        assertEquals("Wrong studyDiseaseDTO returned", studyDiseaseIi, studyDiseaseDTO.getIdentifier());
    }

    /**
     * Test the get method for a existing StudyDisease.
     * @throws PAException if an error occurs
     */
    @Test
    public void testGetNotExist() throws PAException {
        StudyDiseaseDTO studyDiseaseDTO = bean.get(IiConverter.convertToStudyDiseaseIi(25L));
        assertNull("Get should not find anything", studyDiseaseDTO);
    }

    /**
     * Test the get method for a existing StudyDisease.
     * @throws PAException if an error occurs
     */
    @Test
    public void testGetByStudyProtocolExist() throws PAException {
        Ii spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
        List<StudyDiseaseDTO> dtoList = bean.getByStudyProtocol(spIi);
        assertNotNull("No result returned", dtoList);
        assertEquals("Wrong number of StudyDisease found", 2, dtoList.size());
        for (int i = 0; i < dtoList.size(); i++) {
            StudyDiseaseDTO studyDiseaseDTO = dtoList.get(i);
            Ii expected = IiConverter.convertToStudyDiseaseIi(TestSchema.studyDiseaseIds.get(i));
            assertEquals("Wrong StudyDisease found", expected, studyDiseaseDTO.getIdentifier());
            assertEquals("Wrong study protocol identifier", spIi, studyDiseaseDTO.getStudyProtocolIdentifier());
        }
    }

    /**
     * Test the get method for a existing StudyDisease.
     * @throws PAException if an error occurs
     */
    @Test
    public void testGetByStudyProtocolNotExist() throws PAException {
        StudyProtocol studyProtocol = TestSchema.createStudyProtocolObj();
        Ii spIi = IiConverter.convertToStudyProtocolIi(studyProtocol.getId());
        List<StudyDiseaseDTO> dtoList = bean.getByStudyProtocol(spIi);
        assertNotNull("No result returned", dtoList);
        assertEquals("Wrong number of StudyDisease found", 0, dtoList.size());
    }

    /**
     * Test the create method with valid data.
     * @throws PAException if an error occurs
     */
    @Test
    public void testCreateValid() throws PAException {
        StudyProtocol studyProtocol = TestSchema.createStudyProtocolObj();
        Ii spIi = IiConverter.convertToStudyProtocolIi(studyProtocol.getId());
        StudyDiseaseDTO studyDiseaseDTO = new StudyDiseaseDTO();
        studyDiseaseDTO.setStudyProtocolIdentifier(spIi);
        Ii diseaseIi = IiConverter.convertToIi(TestSchema.pdqDiseaseIds.get(0));
        studyDiseaseDTO.setDiseaseIdentifier(diseaseIi);
        studyDiseaseDTO.setCtGovXmlIndicator(BlConverter.convertToBl(true));
        StudyDiseaseDTO result = bean.create(studyDiseaseDTO);
        assertStudyDiseaseDTO(studyDiseaseDTO, result);
        List<StudyDiseaseDTO> dtoList = bean.getByStudyProtocol(spIi);
        assertNotNull("No result returned", dtoList);
        assertEquals("Wrong number of StudyDisease found", 1, dtoList.size());
        assertStudyDiseaseDTO(studyDiseaseDTO, dtoList.get(0));
    }

    private void assertStudyDiseaseDTO(StudyDiseaseDTO expected, StudyDiseaseDTO actual) {
        assertEquals("Wrong study protocol Ii", expected.getStudyProtocolIdentifier(),
                     actual.getStudyProtocolIdentifier());
        assertEquals("Wrong disease Ii", expected.getDiseaseIdentifier(), actual.getDiseaseIdentifier());
        assertEquals("Wrong ctgov Xml Indicator", expected.getCtGovXmlIndicator(), actual.getCtGovXmlIndicator());
    }

    /**
     * test that the create method with invalid data.
     * @throws PAException if an error occurs
     */
    @Test
    public void testCreateInvalid() throws PAException {
        thrown.expect(PAException.class);
        thrown.expectMessage("Missing Disease/Condition. ");
        StudyProtocol studyProtocol = TestSchema.createStudyProtocolObj();
        Ii spIi = IiConverter.convertToStudyProtocolIi(studyProtocol.getId());
        StudyDiseaseDTO studyDiseaseDTO = new StudyDiseaseDTO();
        studyDiseaseDTO.setStudyProtocolIdentifier(spIi);
        studyDiseaseDTO.setCtGovXmlIndicator(BlConverter.convertToBl(true));
        bean.create(studyDiseaseDTO);
    }

    /**
     * Test the update methodwith valid data.
     * @throws PAException if an error occurs
     */
    @Test
    public void testUpdateValid() throws PAException {
        Ii studyDiseaseIi = IiConverter.convertToStudyDiseaseIi(TestSchema.studyDiseaseIds.get(0));
        StudyDiseaseDTO studyDiseaseDTO = bean.get(studyDiseaseIi);
        Ii diseaseIi = IiConverter.convertToIi(TestSchema.pdqDiseaseIds.get(3));
        studyDiseaseDTO.setDiseaseIdentifier(diseaseIi);
        StudyDiseaseDTO result = bean.update(studyDiseaseDTO);
        assertStudyDiseaseDTO(studyDiseaseDTO, result);
        StudyDiseaseDTO saved = bean.get(studyDiseaseIi);
        assertNotNull("No result returned", saved);
        assertStudyDiseaseDTO(studyDiseaseDTO, saved);
    }

    /**
     * Test the update method with invalid data (twice the same disease).
     * @throws PAException if an error occurs
     */
    @Test
    public void testUpdateWithSameId() throws PAException {
        thrown.expect(PAException.class);
        thrown.expectMessage("Redundancy error:  this trial already includes the selected disease.  ");
        Ii spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
        List<StudyDiseaseDTO> dtoList = bean.getByStudyProtocol(spIi);
        assertEquals("Wrong number of StudyDisease found", 2, dtoList.size());
        StudyDiseaseDTO studyDiseaseDTO0 = dtoList.get(0);
        StudyDiseaseDTO studyDiseaseDTO1 = dtoList.get(1);
        studyDiseaseDTO1.setDiseaseIdentifier(studyDiseaseDTO0.getDiseaseIdentifier());
        bean.update(studyDiseaseDTO1);
    }

    /**
     * test the delete method.
     * @throws PAException if an error occurs
     */
    @Test
    public void testDelete() throws PAException {
        Ii spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
        List<StudyDiseaseDTO> dtoList = bean.getByStudyProtocol(spIi);
        int oldSize = dtoList.size();
        Ii ii = dtoList.get(0).getIdentifier();
        bean.delete(ii);
        dtoList = bean.getByStudyProtocol(spIi);
        assertEquals(oldSize - 1, dtoList.size());
    }

    /**
     * test the businessRules method with no disease identifier.
     * @throws PAException if an error occurs
     */
    @Test
    public void testBusinessRulesNoDiseaseId() throws PAException {
        thrown.expect(PAException.class);
        thrown.expectMessage("Missing Disease/Condition. ");
        StudyDiseaseDTO dto = new StudyDiseaseDTO();
        bean.businessRules(dto);
    }

    /**
     * test the businessRules method with valid data.
     * @throws PAException if an error occurs
     */
    @Test
    public void testBusinessRulesValid() throws PAException {
        StudyDiseaseDTO dto = new StudyDiseaseDTO();
        dto.setDiseaseIdentifier(IiConverter.convertToIi(1L));
        bean = mock(StudyDiseaseBeanLocal.class);
        doCallRealMethod().when(bean).businessRules(dto);
        bean.businessRules(dto);
        verify(bean).validateDisease(eq(dto.getDiseaseIdentifier()), any(StringBuilder.class));
        verify(bean).validateNoduplicate(eq(dto), any(StringBuilder.class));
    }

    /**
     * Test the validateDisease with valid data.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateDiseaseValid() throws PAException {
        pdqDiseaseServiceLocal = mock(PDQDiseaseServiceLocal.class);
        bean.setPdqDiseaseServiceLocal(pdqDiseaseServiceLocal);
        Ii diseaseIi = IiConverter.convertToIi(1L);
        PDQDiseaseDTO pdqDiseaseDTO = new PDQDiseaseDTO();
        pdqDiseaseDTO.setDisplayName(StConverter.convertToSt("displayName"));
        when(pdqDiseaseServiceLocal.get(diseaseIi)).thenReturn(pdqDiseaseDTO);
        bean.validateDisease(diseaseIi, errorMsg);
        verify(pdqDiseaseServiceLocal).get(diseaseIi);
        checkErrorMsg("");
    }

    /**
     * Test the validateDisease with valid data.
     * @throws PAException if an error occurs
     */
    @Test
    public void testValidateDiseaseInvalid() throws PAException {
        pdqDiseaseServiceLocal = mock(PDQDiseaseServiceLocal.class);
        bean.setPdqDiseaseServiceLocal(pdqDiseaseServiceLocal);
        Ii diseaseIi = IiConverter.convertToIi(1L);
        PDQDiseaseDTO pdqDiseaseDTO = new PDQDiseaseDTO();
        when(pdqDiseaseServiceLocal.get(diseaseIi)).thenReturn(pdqDiseaseDTO);
        bean.validateDisease(diseaseIi, errorMsg);
        verify(pdqDiseaseServiceLocal).get(diseaseIi);
        checkErrorMsg("Diseases without a menu display name are not suitable for reporting.  ");
    }

    private void checkErrorMsg(String expectedMessage) {
        assertEquals("Wrong error message", expectedMessage, errorMsg.toString());
    }

}

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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.domain.PDQDisease;
import gov.nih.nci.pa.domain.PDQDiseaseParent;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author merenkoi
 * 
 */
public class ProtocolQueryServiceTest  {
    
    private ProtocolQueryServiceBean bean;
    
    /**
     *  set up the tested bean
     */
    @Before
    public void setUp() {
        bean = new ProtocolQueryServiceBean();
    }
    
    
    /**
     * tests if tree traversal is correct
     */
    @Test
    public void  getPDQParentsAndDescendants() {
        TestBean<PDQDisease, Long> testBean = createListOfPDQDisease();
        PDQDiseaseServiceLocal pdqDiseaseService = mock(PDQDiseaseServiceLocal.class);
        List<Long> ids = new ArrayList<Long>();
        when(pdqDiseaseService.getByIds(ids)).thenReturn(testBean.input);
        bean.setPdqDiseaseService(pdqDiseaseService);
        
        List<Long> result = new ArrayList<Long>(bean.getPDQParentsAndDescendants(ids));
        
        Collections.sort(testBean.output);
        Collections.sort(result);
        assertTrue(CollectionUtils.isEqualCollection(testBean.output, result));
    }
    
    /**
     * tests getStudyProtocolByCriteriaForReporting()
     * @throws PAException 
     */
    @Test(expected=PAException.class)
    public void  getStudyProtocolByCriteriaForReporting() throws PAException {
        ProtocolQueryServiceBean bean = mock(ProtocolQueryServiceBean.class);      
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        doCallRealMethod().when(bean).getStudyProtocolByCriteriaForReporting(criteria);
        bean.getStudyProtocolByCriteriaForReporting(criteria);       
    } 
    
    /**
     * tests getTrialSummaryByStudyProtocolIdTest()
     * @throws PAException
     */
    @Test(expected = PAException.class)
    public void getTrialSummaryByStudyProtocolIdTest() throws PAException {
        bean.getTrialSummaryByStudyProtocolId(null);
    }  
    
    /**
     * tests getStudyProtocolByOrgIdentifier
     * @throws PAException
     */
    @Test(expected = PAException.class)
    public void getStudyProtocolByOrgIdentifier() throws PAException {
        bean.getStudyProtocolByOrgIdentifier(null);
    }

    @Test
    public void getStudyProtocolByAgentNsc() throws Exception {
        assertTrue(bean.getStudyProtocolByAgentNsc(null).isEmpty());
    }

    /**
     *    1                   6
     * 2     3             7       8      9    
     *     4  5         10   11         14  15
     *                     12  13 
     */   
    private TestBean<PDQDisease, Long> createListOfPDQDisease() {
        TestBean<PDQDisease, Long> result = new TestBean<PDQDisease, Long>();
        List<PDQDisease> diseases = new ArrayList<PDQDisease>();
        
        PDQDisease disease1 = new PDQDisease();
        disease1.setId(1L);
        result.output.add(disease1.getId());        
        
        PDQDiseaseParent parent2 = createPDQDiseaseParent(2L);
        result.output.add(parent2.getDisease().getId());
        disease1.getDiseaseChildren().add(parent2);
        
        PDQDiseaseParent parent3 = createPDQDiseaseParent(3L);
        result.output.add(parent3.getDisease().getId());
        disease1.getDiseaseChildren().add(parent3);
        
        PDQDiseaseParent parent4 = createPDQDiseaseParent(4L);
        result.output.add(parent4.getDisease().getId());
        parent3.getDisease().getDiseaseChildren().add(parent4);
        
        PDQDiseaseParent parent5 = createPDQDiseaseParent(5L);
        result.output.add(parent5.getDisease().getId());
        parent3.getDisease().getDiseaseChildren().add(parent5);        
        
        diseases.add(disease1);        
        
        PDQDisease disease6 = new PDQDisease();
        disease6.setId(6L);
        result.output.add(disease6.getId());
        
        PDQDiseaseParent parent7 = createPDQDiseaseParent(7L);
        result.output.add(parent7.getDisease().getId());
        disease6.getDiseaseChildren().add(parent7);
        
        PDQDiseaseParent parent8 = createPDQDiseaseParent(8L);
        result.output.add(parent8.getDisease().getId());
        disease6.getDiseaseChildren().add(parent8);
        
        PDQDiseaseParent parent9 = createPDQDiseaseParent(9L);
        result.output.add(parent9.getDisease().getId());
        disease6.getDiseaseChildren().add(parent9);
        
        PDQDiseaseParent parent10 = createPDQDiseaseParent(10L);
        result.output.add(parent10.getDisease().getId());
        parent7.getDisease().getDiseaseChildren().add(parent10);
        
        PDQDiseaseParent parent11 = createPDQDiseaseParent(11L);
        result.output.add(parent11.getDisease().getId());
        parent7.getDisease().getDiseaseChildren().add(parent11);
        
        PDQDiseaseParent parent12 = createPDQDiseaseParent(12L);
        result.output.add(parent12.getDisease().getId());
        parent11.getDisease().getDiseaseChildren().add(parent12);
        
        PDQDiseaseParent parent13 = createPDQDiseaseParent(13L);
        result.output.add(parent13.getDisease().getId());
        parent11.getDisease().getDiseaseChildren().add(parent13);
        
        PDQDiseaseParent parent14 = createPDQDiseaseParent(14L);
        result.output.add(parent14.getDisease().getId());
        parent9.getDisease().getDiseaseChildren().add(parent14);
        
        PDQDiseaseParent parent15 = createPDQDiseaseParent(15L);
        result.output.add(parent15.getDisease().getId());
        parent9.getDisease().getDiseaseChildren().add(parent15);
        
        diseases.add(disease6);
        
        diseases.add(parent9.getDisease());
       
        result.input = diseases;
        return result;
    }
    
    private PDQDiseaseParent createPDQDiseaseParent(Long diseaseId) {
        PDQDiseaseParent result = new PDQDiseaseParent();
        PDQDisease disease = new PDQDisease();
        disease.setId(diseaseId);
        result.setDisease(disease);
        return result;        
    }
   
    
    private static class TestBean<I, U> {
        List<I> input = new ArrayList<I>();
        List<U> output = new ArrayList<U>();
    } 
}

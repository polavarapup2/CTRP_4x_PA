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
*/
package gov.nih.nci.accrual.accweb.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.accweb.util.MockSearchTrialBean;
import gov.nih.nci.accrual.service.util.SubjectAccrualCountService;
import gov.nih.nci.accrual.util.AccrualServiceLocator;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.ServiceLocatorAccInterface;
import gov.nih.nci.accrual.util.ServiceLocatorPaInterface;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteSubjectAccrualCount;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


/**
 * @author oweisms
 */

public class IndustrialPatientActionTest extends AbstractAccrualActionTest {

    private IndustrialPatientAction action;
    @Rule public ExpectedException thrown = ExpectedException.none();
    @Before
    public void setup() {
        action = new IndustrialPatientAction();
    }
    @Test
    public void prepareSettingTheTrialInSession() {
        action.setStudyProtocolId(null);
        action.prepare();
        assertNull(ServletActionContext.getRequest().getSession().getAttribute("trialSummary"));

        action.setStudyProtocolId(1L);
        action.prepare();
        assertNotNull(ServletActionContext.getRequest().getSession().getAttribute("trialSummary"));
    }

    @Test
    public void preparePAException() throws PAException {
        setupPreparePAException();
        
        action.setStudyProtocolId(1L);
        action.prepare();
        assertEquals("Test message", action.getActionErrors().iterator().next());
    }

    private void setupPreparePAException() throws PAException {
        ServiceLocatorPaInterface serviceLocator = mock(ServiceLocatorPaInterface.class);
        RegistryUserServiceRemote mockService = mock(RegistryUserServiceRemote.class);
        when(serviceLocator.getRegistryUserService()).thenReturn(mockService);
        when(mockService.getUser(any(String.class))).thenThrow(new PAException("Test message"));
        PaServiceLocator.getInstance().setServiceLocator(serviceLocator);
    }

    @Test
    public void execute() {
        action.setStudyProtocolId(MockSearchTrialBean.INDUSTRIAL_STUDY_PROTOCOL_ID);
        action.prepare();
        assertEquals("success", action.execute());
    }


    @Test
    public void executeNonIndustrialTrial() {
        action.setStudyProtocolId(MockSearchTrialBean.NONINDUSTRIAL_STUDY_PROTOCOL_ID);
        action.prepare();
        assertEquals("invalid", action.execute());
    }
    
    @Test
    public void executeNonInterventionalTrial() {
		action.setStudyProtocolId(MockSearchTrialBean.NONINTERVENTIONAL_STUDY_PROTOCOL_ID);
        action.prepare();
        assertEquals("success", action.execute());
        action.setStudyProtocolId(5L);
        action.prepare();
        assertEquals("invalid", action.execute());
	}

    @Test
    public void update() {
        action.setStudyProtocolId(MockSearchTrialBean.INDUSTRIAL_STUDY_PROTOCOL_ID);
        action.prepare();
        action.setSelectedRowIdentifier("1");
        action.setSubmittedCounts("2");
        assertEquals("saved", action.update());
        action.setStudyProtocolId(MockSearchTrialBean.NONINTERVENTIONAL_STUDY_PROTOCOL_ID);
        action.prepare();
        action.setSubmittedCounts("2");
        assertEquals("saved", action.update());
    }

    @Test(expected= Exception.class)
    public void updatePAException() throws PAException {
        setupUpdatePAException();
        action.update();
        assertEquals("Test message", action.getActionErrors().iterator().next());
        assertEquals("input", action.update());
    }

    @Test
    public void delete() throws PAException {
    	action.setSelectedRowIdentifier("1");
        action.setStudyProtocolId(MockSearchTrialBean.INDUSTRIAL_STUDY_PROTOCOL_ID);
        action.prepare();
        assertEquals("saved", action.delete());
    }

    @SuppressWarnings("unchecked")
    private void setupUpdatePAException() throws PAException {
        action.setStudyProtocolId(MockSearchTrialBean.INDUSTRIAL_STUDY_PROTOCOL_ID);
        action.prepare();
        
        ServiceLocatorAccInterface serviceLocator = mock(ServiceLocatorAccInterface.class);
        SubjectAccrualCountService mockService = mock(SubjectAccrualCountService.class);
        when(serviceLocator.getSubjectAccrualCountService()).thenReturn(mockService);
        doThrow(new PAException("Test message")).when(mockService).save(any(List.class));
        AccrualServiceLocator.getInstance().setServiceLocator(serviceLocator);
    }
    @Test
    public void getSiteCount() throws PAException {
        List<StudySiteSubjectAccrualCount> counts = setupGetSiteCount();
        assertEquals((Integer) 123, action.getSiteCount(counts, 1L).getAccrualCount());
    }
    
    @Test
    public void getSiteCountInvalid() throws PAException {
        List<StudySiteSubjectAccrualCount> counts = setupGetSiteCount();
        thrown.expect(PAException.class);
        thrown.expectMessage("Invalid site id for study - (Study Protocol Id, Site Id):");
        action.getSiteCount(counts, 2L);
    }
    
    private List<StudySiteSubjectAccrualCount> setupGetSiteCount() {
        StudySiteSubjectAccrualCount count = new StudySiteSubjectAccrualCount();
        List<StudySiteSubjectAccrualCount> counts = new ArrayList<StudySiteSubjectAccrualCount>();
        counts.add(count);
        StudySite site = new StudySite();
        count.setStudySite(site);
        site.setId(1L);
        count.setAccrualCount(123);
        return counts;
    }
    
    @Test 
    public void updateCountForSiteIfSet() throws PAException {
        StudySiteSubjectAccrualCount count = new StudySiteSubjectAccrualCount();
        String submittedCount = null;
        action.updateCountForSiteIfSet(count, submittedCount);
        assertNull(count.getAccrualCount());
        
        submittedCount = "123";
        action.updateCountForSiteIfSet(count, submittedCount);
        assertEquals((Integer) 123, count.getAccrualCount());
        assertEquals(AccrualSubmissionTypeCode.UI, count.getSubmissionTypeCode());
    }
    @Test
    public void assertValidCount() throws PAException {
        action.assertValidCount("123");
        assertTrue(true);
    }

    @Test
    public void assertValidCountNegative() throws PAException {
        thrown.expect(PAException.class);
        thrown.expectMessage("error.accrual.count.invalid");
        action.assertValidCount("-123");
    }

    @Test
    public void assertValidCountCharacters() throws PAException {
        thrown.expect(PAException.class);
        thrown.expectMessage("error.accrual.count.invalid");
        action.assertValidCount("something");
    }
}

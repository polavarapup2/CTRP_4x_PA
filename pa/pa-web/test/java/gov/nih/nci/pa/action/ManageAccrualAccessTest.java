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
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.StudySiteAccrualAccessWebDTO;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualAccessDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.service.util.AccrualDiseaseTerminologyServiceRemote;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.test.util.MockServiceLocator;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.service.MockStudySiteService;
import gov.nih.nci.service.util.MockStudySiteAccrualAccessService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;

/**
 * @author Hugh Reinhart
 * @since Sep 8, 2009
 */
public class ManageAccrualAccessTest extends AbstractPaActionTest {
    private final Long testRegUserId = MockStudySiteAccrualAccessService.regUsers.get(0).getId();
    private final Long testStudySiteId = IiConverter.convertToLong(MockStudySiteService.dtos.get(2).getIdentifier());
    private final String testStatusCode = ActiveInactiveCode.ACTIVE.getCode();
    private final String origSourceCode = AccrualAccessSourceCode.REG_ADMIN_PROVIDED.getCode();
    private final String updSourceCode = AccrualAccessSourceCode.PA_SITE_REQUEST.getCode();
    private final String testRequestDetails = "test request details";
    private final RegistryUserServiceLocal registryUserService = mock(RegistryUserServiceLocal.class); 
    private StudySiteAccrualAccessServiceLocal ssAccSvc = null;

    private ManageAccrualAccessAction act;

    @Before
    public void prepare() throws Exception {
        when(MockServiceLocator.accDiseaseTerminologyService.canChangeCodeSystem(anyLong())).thenReturn(false);
        CSMUserService.setInstance(new MockCSMUserService());
        MockStudySiteAccrualAccessService.list.clear();
        act = new ManageAccrualAccessAction();
        act.prepare();
        when(registryUserService.getUserById(any(Long.class))).thenReturn(getRegUser());
        act.setRegistryUserService(registryUserService);
    }

    private void loadMocksInPaRegistry() throws PAException {
        ServiceLocator svcLoc = mock(ServiceLocator.class);
        ssAccSvc = mock(StudySiteAccrualAccessServiceLocal.class);
        when(svcLoc.getStudySiteAccrualAccessService()).thenReturn(ssAccSvc);
        when(svcLoc.getRegistryUserService()).thenReturn(registryUserService);
        Map<Long, String> treatingSiteMap = new HashMap<Long, String>();
        treatingSiteMap.put(1L, "Treating Site 1");
        treatingSiteMap.put(2L, "Treating Site 2");
        when(ssAccSvc.getTreatingSites(any(Long.class))).thenReturn(treatingSiteMap);
        PaRegistry.getInstance().setServiceLocator(svcLoc);
        act.setAccrualAccessService(ssAccSvc);
    }

    @Test
    public void listAccrualAccess() throws Exception {
        // select from menu
        assertEquals(AbstractListEditAction.AR_LIST, act.execute());
        assertEquals(0, act.getAccessList().size());
    }

    @Test
    public void addAccrualAccess() throws Exception {
        HttpSession session = ServletActionContext.getRequest().getSession();
        StudyProtocolQueryDTO protocolSessionBean = (StudyProtocolQueryDTO) session
            .getAttribute(Constants.TRIAL_SUMMARY);
        listAccrualAccess();

        // click Add button fails because dwf is not eligible for accrual
        assertFalse(DocumentWorkflowStatusCode.ABSTRACTED.isEligibleForAccrual());
        protocolSessionBean.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTED);
        assertEquals(AbstractListEditAction.AR_LIST, act.create());
        assertTrue(act.hasActionErrors());
        act.clearErrorsAndMessages();

        // click Add button
        assertTrue(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.isEligibleForAccrual());
        protocolSessionBean.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        assertEquals(AbstractListEditAction.AR_EDIT, act.create());
        assertFalse(act.hasActionErrors());

        // fill in data
        act.getAccess().setRegistryUserId(testRegUserId);
        act.getAccess().setStudySiteId(testStudySiteId);
        act.getAccess().setStatusCode(testStatusCode);
        act.getAccess().setSource(origSourceCode);
        act.getAccess().setRequestDetails(testRequestDetails);
        assertEquals(AbstractListEditAction.AR_LIST, act.add());
        assertFalse(act.hasActionErrors());
        assertEquals(1, act.getAccessList().size());
        StudySiteAccrualAccessWebDTO dto = act.getAccessList().get(0);
        assertEquals(testRegUserId, dto.getRegistryUserId());
        assertEquals(testStudySiteId, dto.getStudySiteId());
        assertEquals(testStatusCode, dto.getStatusCode());
        assertEquals(updSourceCode, dto.getSource());
        assertEquals(testRequestDetails, dto.getRequestDetails());
    }

    @Test
    public void addAllAccrualAccessWithoutUpdate() throws Exception {
        loadMocksInPaRegistry();
        listAccrualAccess();

        act.getAccess().setRegistryUserId(testRegUserId);
        act.getAccess().setStudySiteId(ManageAccrualAccessHelper.ALL_TREATING_SITES_ID);
        act.getAccess().setStatusCode(testStatusCode);
        act.getAccess().setSource(origSourceCode);

        loadTreatingSitesForProtocol();

        assertEquals(AbstractListEditAction.AR_LIST, act.add());
        assertTrue(act.hasActionErrors());
        assertEquals(2, act.getAccessList().size());

    }

    @Test
    public void addAllAccrualAccessWithUpdate() throws Exception {
        loadMocksInPaRegistry();
        listAccrualAccess();

        List<StudySiteAccrualAccessDTO> addAllReturnList = new ArrayList<StudySiteAccrualAccessDTO>();
        StudySiteAccrualAccessDTO ssAcDto1 = new StudySiteAccrualAccessDTO();
        ssAcDto1.setRegistryUserIdentifier(IiConverter.convertToIi(1L));
        ssAcDto1.setStudySiteIdentifier(IiConverter.convertToStudySiteIi(1L));
        ssAcDto1.setIdentifier(IiConverter.convertToActivityIi(1L));
        addAllReturnList.add(ssAcDto1);
        when(ssAccSvc.getByStudySite(any(Long.class))).thenReturn(addAllReturnList);
        when(ssAccSvc.getByStudyProtocol(any(Long.class))).thenReturn(addAllReturnList);

        assertEquals(AbstractListEditAction.AR_LIST, act.execute());
        assertEquals(1, act.getAccessList().size());

        act.getAccess().setRegistryUserId(testRegUserId);
        act.getAccess().setStudySiteId(ManageAccrualAccessHelper.ALL_TREATING_SITES_ID);
        act.getAccess().setStatusCode(testStatusCode);
        act.getAccess().setSource(origSourceCode);

        loadTreatingSitesForProtocol();
        when(ssAccSvc.create(any(StudySiteAccrualAccessDTO.class))).thenThrow(new PADuplicateException(""));
        assertEquals(AbstractListEditAction.AR_LIST, act.add());
        assertTrue(act.hasActionErrors());
        assertEquals(2, act.getAccessList().size());

    }

    private void loadTreatingSitesForProtocol() throws PAException {
        List<StudySiteAccrualAccessDTO> addAllReturnList = new ArrayList<StudySiteAccrualAccessDTO>();
        StudySiteAccrualAccessDTO ssAcDto1 = new StudySiteAccrualAccessDTO();
        ssAcDto1.setRegistryUserIdentifier(IiConverter.convertToIi(1L));
        ssAcDto1.setStudySiteIdentifier(IiConverter.convertToStudySiteIi(1L));
        addAllReturnList.add(ssAcDto1);
        StudySiteAccrualAccessDTO ssAcDto2 = new StudySiteAccrualAccessDTO();
        ssAcDto2.setRegistryUserIdentifier(IiConverter.convertToIi(1L));
        ssAcDto2.setStudySiteIdentifier(IiConverter.convertToStudySiteIi(2L));
        addAllReturnList.add(ssAcDto2);
        when(ssAccSvc.getByStudyProtocol(any(Long.class))).thenReturn(addAllReturnList);
    }

    @Test
    public void updateAccrualAccess() throws Exception {
        addAccrualAccess();

        // click Edit button
        act.setSelectedRowIdentifier(String.valueOf(act.getAccessList().get(0).getIdentifier()));
        assertEquals(AbstractListEditAction.AR_EDIT, act.edit());
        assertFalse(act.hasActionErrors());

        // fill in data
        String newStatusCode = ActiveInactiveCode.INACTIVE.getCode();
        String newRequestDetails = "new r. details";
        act.getAccess().setStatusCode(newStatusCode);
        act.getAccess().setRequestDetails(newRequestDetails);
        assertEquals(AbstractListEditAction.AR_LIST, act.update());
        assertFalse(act.hasActionErrors());
        assertEquals(1, act.getAccessList().size());
        StudySiteAccrualAccessWebDTO dto = act.getAccessList().get(0);
        assertEquals(testRegUserId, dto.getRegistryUserId());
        assertEquals(testStudySiteId, dto.getStudySiteId());
        assertEquals(newStatusCode, dto.getStatusCode());
        assertEquals(newRequestDetails, dto.getRequestDetails());
    }
    
    @Test(expected=UnsupportedOperationException.class)
    public void testDeleteObject() throws PAException {
        act.deleteObject(1l);
    }

    @Test
    public void updateDiseaseTerminologyTest() throws Exception {
        assertFalse(act.getAccrualDiseaseTerminologyEditable());

        AccrualDiseaseTerminologyServiceRemote mock = MockServiceLocator.accDiseaseTerminologyService;
        when(mock.canChangeCodeSystem(anyLong())).thenReturn(true);
        when(mock.getCodeSystem(anyLong())).thenReturn("SDC");
        when(mock.getValidCodeSystems()).thenReturn(Arrays.asList(new String[]{"SDC","ICD9"}));
        MockHttpServletRequest request = (MockHttpServletRequest) ServletActionContext.getRequest();
        request.setupAddParameter("newValue", "ICD9");
        act.prepare();
        act.updateDiseaseTerminology();
        assertTrue(act.getAccrualDiseaseTerminologyEditable());
        assertEquals("SDC", act.getAccrualDiseaseTerminology());
        assertEquals(2, act.getAccrualDiseaseTerminologyList().size());
        verify(mock).updateCodeSystem(anyLong(), eq("ICD9"));
    }

}

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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.accrual.accweb.dto.util.RegistryUserWebDTO;
import gov.nih.nci.accrual.accweb.util.AccrualConstants;
import gov.nih.nci.accrual.accweb.util.MockPaServiceLocator;
import gov.nih.nci.accrual.accweb.util.MockSearchTrialBean;
import gov.nih.nci.accrual.accweb.util.MockServiceLocator;
import gov.nih.nci.accrual.util.AccrualServiceLocator;
import gov.nih.nci.accrual.util.PaServiceLocator;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockServletContext;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.providers.XWorkConfigurationProvider;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;

/**
 * @author Hugh Reinhart
 * @since 7/7/2009
 */
public class AbstractAccrualActionTest {
    protected static final String TEST_USER = "joe@barngrill.com";

    private class TestAction extends AbstractListEditAccrualAction<Object> {
        private static final long serialVersionUID = 8637312133341800224L;

        @Override
        public void loadDisplayList() {
            // test method
        }
    };

    private final TestAction action = new TestAction();

    @Before
    public void initMockServiceLocator() throws Exception {
        AccrualServiceLocator.getInstance().setServiceLocator(new MockServiceLocator());
        PaServiceLocator.getInstance().setServiceLocator(new MockPaServiceLocator());
    }

    /**
     * Initialize the mock request.
     */
    @Before
    public void initMockRequest() {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.addContainerProvider(new XWorkConfigurationProvider());
        Configuration config = configurationManager.getConfiguration();
        Container container = config.getContainer();

        ValueStack stack = container.getInstance(ValueStackFactory.class).createValueStack();
        stack.getContext().put(ActionContext.CONTAINER, container);
        ActionContext.setContext(new ActionContext(stack.getContext()));

        assertNotNull(ActionContext.getContext());

        setupMockHttpSession();

        setRole(AccrualConstants.ROLE_PUBLIC);
        setDisclaimer(true);
    }

    public void setupMockHttpSession() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.setRemoteUser(TEST_USER);
        ServletActionContext.setServletContext(new MockServletContext());
        ServletActionContext.setRequest(request);
    }

    @Test
    public void sessionTimeoutExecuteTest() throws Exception {
        setRole(null);
        assertEquals(AccrualConstants.AR_LOGOUT, action.execute());
    }

    @Test
    public void executeTest() throws Exception {
        ((MockHttpServletRequest) ServletActionContext.getRequest()).setUserInRole(AccrualConstants.ROLE_PUBLIC, true);
        assertEquals(AccrualConstants.ROLE_PUBLIC,
                ServletActionContext.getRequest().getSession().getAttribute(AccrualConstants.SESSION_ATTR_ROLE));
        RegistryUserWebDTO registryUserWebDTO = new RegistryUserWebDTO();
        ServletActionContext.getRequest().getSession().setAttribute("registryUserWebDTO", registryUserWebDTO);
        assertEquals(Action.SUCCESS, action.execute());
    }

    @Test
    public void createTest() throws Exception {
        assertEquals(AccrualConstants.AR_DETAIL, action.create());
    }

    @Test
    public void retrieveTest() {
        assertEquals(AccrualConstants.AR_DETAIL, action.retrieve());
    }

    @Test
    public void updateTest() {
        assertEquals(AccrualConstants.AR_DETAIL, action.update());
    }

    @Test
    public void addTest() throws Exception {
        assertEquals(action.execute(), action.add());
    }

    @Test
    public void editTest() throws Exception {
        assertEquals(action.execute(), action.edit());
    }

    @Test
    public void deleteTest() throws Exception {
        assertEquals(action.execute(), action.delete());
    }

    @Test
    public void addActionErrorIfEmptyTest() throws Exception {
        assertFalse(action.hasActionErrors());
        action.addActionErrorIfEmpty("", "xxx");
        assertTrue(action.hasActionErrors());
        action.clearErrorsAndMessages();
        action.addActionErrorIfEmpty("xxx", "xxx");
        assertFalse(action.hasActionErrors());
        Set<String> hashSet = new HashSet<String>();
        action.addActionErrorIfEmpty(hashSet, "xxx");
        assertTrue(action.hasActionErrors());
        hashSet.add("xxx");
        action.clearErrorsAndMessages();
        action.addActionErrorIfEmpty(hashSet, "xxx");
        assertFalse(action.hasActionErrors());
        action.addActionErrorIfEmpty(null, "xxx");
        assertTrue(action.hasActionErrors());
        action.clearErrorsAndMessages();
        action.addActionErrorIfEmpty(Long.valueOf(1L), "xxx");
        assertFalse(action.hasActionErrors());
    }

    @Test
    public void currentActionPropertyTest() {
        action.setCurrentAction("currentAction");
        assertNotNull(action.getCurrentAction());
    }

    @Test
    public void selectedRowIdentifierPropertyTest() {
        action.setSelectedRowIdentifier("1");
        assertNotNull(action.getSelectedRowIdentifier());
    }

    /**
     * Clean out the action context to ensure one test does not impact another.
     */
    @After
    public void cleanUpActionContext() {
        ActionContext.setContext(null);
        MockSearchTrialBean.exception = false;
    }

    public void setRole(String role) {
        if (role != null) {
            ServletActionContext.getRequest().getSession().setAttribute(AccrualConstants.SESSION_ATTR_ROLE, role);
        } else {
            ServletActionContext.getRequest().getSession().removeAttribute(AccrualConstants.SESSION_ATTR_ROLE);
        }
    }

    public void setDisclaimer(boolean value) {
        if (value) {
            ServletActionContext.getRequest().getSession().setAttribute("disclaimerAccepted", true);
        } else {
            ServletActionContext.getRequest().getSession().removeAttribute("disclaimerAccepted");
        }
    }
}

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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.StudyContactWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactService;
import gov.nih.nci.pa.test.util.MockServiceLocator;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.MockPoServiceLocator;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.services.person.PersonSearchCriteriaDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.providers.XWorkConfigurationProvider;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;

/**
 * @author chandrasekaravr
 *
 */
public class ResultsReportingContactsActionTest extends AbstractHibernateTestCase {

    private StudyContactDTO desgneeSrchCrit = new StudyContactDTO();
    private StudyContactDTO pioSrchCrit = new StudyContactDTO();

    private Map<Long, StudyContactDTO> dscMap = new HashMap<Long, StudyContactDTO>();
    private Map<Long, StudyContactDTO> pscMap = new HashMap<Long, StudyContactDTO>();
    
   
    
    
    @Before
    public void setup() throws TooManyResultsException, PAException {
        desgneeSrchCrit.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.DESIGNEE_CONTACT));
        pioSrchCrit.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.PIO_CONTACT));
        TestSchema.primeData();
    }
    
    
    @Test
    public void query() throws Exception {
        ResultsReportingContactsAction action = getAction();
        action.setStudyProtocolId(1L);
        
        assertNull(action.getStudyDesigneeContactDtos());
        assertNull(action.getStudyPioContactDtos());
        assertNull(action.getStudyDesigneeContactWebDtos());
        assertNull(action.getStudyPioContactWebDtos());
        
        String result = action.query();
        assertEquals("success", result);
        assertEquals("add", action.getProcess());
        assertFalse(action.hasActionErrors());
        assertEquals(2, action.getStudyDesigneeContactDtos().size());
        assertEquals(2, action.getStudyPioContactDtos().size());
        assertEquals(1, action.getStudyDesigneeContactWebDtos().size());
        assertEquals(1, action.getStudyPioContactWebDtos().size());
    }
    
    @Test
    public void execute() throws Exception {
        ResultsReportingContactsAction action = getAction();
        action.setStudyProtocolId(1L);
        String result = action.execute();
        assertEquals("input", result);
        assertEquals("add", action.getProcess());
        assertFalse(action.hasActionErrors());
    }
    
    @Test
    public void viewDSC() throws Exception {
        ResultsReportingContactsAction action = getAction();
        action.setStudyProtocolId(1L);
        String result = action.execute();
        assertNull(action.getDscToEdit());
        assertNull(action.getEditedDesigneeSCWebDTO().getContactOrg().getId());
        
        result = action.viewDesigneeStudyContact();
        assertEquals("error", result);
        assertEquals("Please select a valid designee study contact to view",  getRequest().getAttribute(Constants.FAILURE_MESSAGE));
        
        action.setDscToEdit(1L);
        result = action.viewDesigneeStudyContact();
        assertEquals("success", result);
        assertNotNull(action.getEditedDesigneeSCWebDTO().getContactOrg().getId());
    }
    
    @Test
    public void viewPSC() throws Exception {
        ResultsReportingContactsAction action = getAction();
        action.setStudyProtocolId(1L);
        String result = action.execute();
        assertNull(action.getPscToEdit());
        assertNull(action.getEditedPioSCWebDTO().getContactPerson().getId());
        
        result = action.viewPioStudyContact();
        assertEquals("error", result);
        assertEquals("Please select a valid PIO study contact to view",  getRequest().getAttribute(Constants.FAILURE_MESSAGE));
        
        action.setPscToEdit(3L);
        result = action.viewPioStudyContact();
        assertEquals("success", result);
        assertNotNull(action.getEditedPioSCWebDTO().getContactPerson().getId());
        
    }

    @Test
    public void validateSc() throws Exception {
        ResultsReportingContactsAction action = getAction();
        action.setStudyProtocolId(1L);
        String result = action.execute();
        
        result = action.addOrEditDesigneeContact();
        assertEquals("error", result);
        assertTrue(action.hasFieldErrors());
        
        List<String> allerrors = new ArrayList<String>();
        for (List lst : action.getFieldErrors().values()) {
            allerrors.addAll(lst);
        }
        assertTrue(allerrors.contains("Organization is required"));
        assertTrue(allerrors.contains("Name is required"));
        assertTrue(allerrors.contains("Email is required"));
        
        StudyContactWebDTO sc = new StudyContactWebDTO(createSCDto(1L));
        sc.setEmail("invalidemail");
        action.setEditedDesigneeSCWebDTO(sc);
        result = action.addOrEditDesigneeContact();
        assertEquals("error", result);
        assertTrue(action.hasFieldErrors());
        
        allerrors = new ArrayList<String>();
        for (List lst : action.getFieldErrors().values()) {
            allerrors.addAll(lst);
        }
        assertTrue(allerrors.contains("Invalid email address"));
        
      
    }
    
    @Test
    public void dscActions() throws Exception {
        ResultsReportingContactsAction action = getAction();
        action.setStudyProtocolId(1L);
        String result = action.execute();
        assertNull(action.getDscToEdit());
        assertNull(action.getEditedDesigneeSCWebDTO().getContactOrg().getId());
        
        StudyContactWebDTO dupe = new StudyContactWebDTO(createSCDto(1L));
        dupe.setId(5L); 
        action.setEditedDesigneeSCWebDTO(dupe);
        result = action.addOrEditDesigneeContact();
        assertEquals("error", result);
        assertEquals("Duplicate designee study contact",  getRequest().getAttribute(Constants.FAILURE_MESSAGE));
        
        assertEquals(1, action.getStudyDesigneeContactWebDtos().size());
        action.setEditedDesigneeSCWebDTO(new StudyContactWebDTO(createSCDto(5L)));
        result = action.addOrEditDesigneeContact();
        assertEquals(2, action.getStudyDesigneeContactWebDtos().size());
        assertEquals("success", result);
        
        action.setEditedDesigneeSCWebDTO(null);
        action.setDscToEdit(null);
        result = action.viewDesigneeStudyContact();
        assertEquals("error", result);
        assertEquals("Please select a valid designee study contact to view",  getRequest().getAttribute(Constants.FAILURE_MESSAGE));
        action.setDscToEdit(5L);
        result = action.viewDesigneeStudyContact();
        assertEquals("success", result);
        assertNotNull(action.getEditedDesigneeSCWebDTO());
        assertEquals(5L, action.getEditedDesigneeSCWebDTO().getId().longValue());
        
        action.setEditedDesigneeSCWebDTO(new StudyContactWebDTO(createSCDto(5L)));
        String oldEmail = action.getEditedDesigneeSCWebDTO().getEmail();
        assertEquals(oldEmail, action.getStudyDesigneeContactWebDtos().get(1).getEmail());
        action.getEditedDesigneeSCWebDTO().setEmail("new" + oldEmail);
        
        action.setProcess("edit");
        result = action.addOrEditDesigneeContact();
        assertEquals("success", result);
        assertEquals(2, action.getStudyDesigneeContactWebDtos().size());
        assertEquals("new" + oldEmail, action.getStudyDesigneeContactWebDtos().get(1).getEmail());
        
        action.setDscToEdit(null);
        action.setPscToEdit(null);
        result = action.delete();
        assertEquals("error", result);
        assertEquals("Please select a valid designee/PIO contact to delete",  getRequest().getAttribute(Constants.FAILURE_MESSAGE));
        action.setDscToEdit(5L);
        result = action.delete();
        assertEquals("success", result);
        assertEquals(1, action.getStudyDesigneeContactWebDtos().size());
    }
    
    @Test
    public void pscActions() throws Exception {
        ResultsReportingContactsAction action = getAction();
        action.setStudyProtocolId(1L);
        String result = action.execute();
        assertNull(action.getPscToEdit());
        assertNull(action.getEditedPioSCWebDTO().getContactPerson().getId());
        
        StudyContactWebDTO dupe = new StudyContactWebDTO(createSCDto(3L));
        dupe.setId(6L); 
        action.setEditedPioSCWebDTO(dupe);
        result = action.addOrEditPIOContact();
        assertEquals("error", result);
        assertEquals("Duplicate PIO study contact",  getRequest().getAttribute(Constants.FAILURE_MESSAGE));
        
        assertEquals(1, action.getStudyPioContactWebDtos().size());
        action.setEditedPioSCWebDTO(new StudyContactWebDTO(createSCDto(6L)));
        result = action.addOrEditPIOContact();
        assertEquals("success", result);
        assertEquals(2, action.getStudyPioContactWebDtos().size());
        
        action.setEditedPioSCWebDTO(null);
        action.setPscToEdit(null);
        result = action.viewPioStudyContact();
        assertEquals("error", result);
        assertEquals("Please select a valid PIO study contact to view",  getRequest().getAttribute(Constants.FAILURE_MESSAGE));
        action.setPscToEdit(6L);
        result = action.viewPioStudyContact();
        assertEquals("success", result);
        assertNotNull(action.getEditedPioSCWebDTO());
        assertEquals(6L, action.getEditedPioSCWebDTO().getId().longValue());
        
        action.setEditedPioSCWebDTO(new StudyContactWebDTO(createSCDto(6L)));
        String oldEmail = action.getEditedPioSCWebDTO().getEmail();
        assertEquals(oldEmail, action.getStudyPioContactWebDtos().get(1).getEmail());
        action.getEditedPioSCWebDTO().setEmail("new" + oldEmail);
        
        action.setProcess("edit");
        result = action.addOrEditPIOContact();
        assertEquals(2, action.getStudyPioContactWebDtos().size());
        assertEquals("success", result);
        assertEquals("new" + oldEmail, action.getStudyPioContactWebDtos().get(1).getEmail());
        
        action.setDscToEdit(null);
        action.setPscToEdit(null);
        result = action.delete();
        assertEquals("error", result);
        assertEquals("Please select a valid designee/PIO contact to delete",  getRequest().getAttribute(Constants.FAILURE_MESSAGE));
        action.setPscToEdit(6L);
        result = action.delete();
        assertEquals("success", result);
        assertEquals(1, action.getStudyPioContactWebDtos().size());
    }
    
    private ResultsReportingContactsAction getAction() throws Exception {
        StudyProtocolQueryDTO spQDto = (StudyProtocolQueryDTO) 
                getSession().getAttribute(Constants.TRIAL_SUMMARY);
        spQDto.setLeadOrganizationPOId(1L);
        getSession().setAttribute(Constants.TRIAL_SUMMARY, spQDto); 
        
        ResultsReportingContactsAction action = new ResultsReportingContactsAction();
        action.setServletRequest(getRequest());
        action.prepare();
        action.setStudyContactService(getStudyContactService());
      
        return action;
    }

    private StudyContactService getStudyContactService() throws PAException, TooManyResultsException {
        final StudyContactService mock = mock(StudyContactService.class);
        StudyContactDTO sc = createSCDto(1);
        sc.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        sc.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.DESIGNEE_CONTACT));
        dscMap.put(1L, sc);
        sc = createSCDto(2);
        sc.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.NULLIFIED));
        sc.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.DESIGNEE_CONTACT));
        dscMap.put(2L, sc);
        sc = createSCDto(3);
        sc.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        sc.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.PIO_CONTACT));
        pscMap.put(3L, sc);
        sc = createSCDto(4);
        sc.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.NULLIFIED));
        sc.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.PIO_CONTACT));
        pscMap.put(4L, sc);
        
        when(mock.search(any(StudyContactDTO.class), 
                any(LimitOffset.class))).thenAnswer(new Answer<List<StudyContactDTO>>(){

                    @Override
                    public List<StudyContactDTO> answer(
                            InvocationOnMock invocation) throws Throwable {
                        StudyContactDTO crit = (StudyContactDTO) invocation.getArguments()[0];
                        if (StudyContactRoleCode.DESIGNEE_CONTACT.equals(
                                CdConverter.convertCdToEnum(StudyContactRoleCode.class, crit.getRoleCode()))) {
                            return new ArrayList<StudyContactDTO>(dscMap.values());
                        } else if (StudyContactRoleCode.PIO_CONTACT.equals(
                                CdConverter.convertCdToEnum(StudyContactRoleCode.class, crit.getRoleCode()))) {
                            return new ArrayList<StudyContactDTO>(pscMap.values());
                        }
                        return new ArrayList<StudyContactDTO>();
                    }});
        
        when(mock.create(any(StudyContactDTO.class))).thenAnswer(new Answer<StudyContactDTO>(){

                    @Override
                    public StudyContactDTO answer(
                            InvocationOnMock invocation) throws Throwable {
                        StudyContactDTO crit = (StudyContactDTO) invocation.getArguments()[0];
                        crit.setOrganizationalContactIi(IiConverter.convertToIi("abcdef"));
                        Long id = IiConverter.convertToLong(crit.getIdentifier());
                        if (StudyContactRoleCode.DESIGNEE_CONTACT.equals(
                                CdConverter.convertCdToEnum(StudyContactRoleCode.class, crit.getRoleCode()))) {
                            dscMap.put(id, crit);
                        } else if (StudyContactRoleCode.PIO_CONTACT.equals(
                                CdConverter.convertCdToEnum(StudyContactRoleCode.class, crit.getRoleCode()))) {
                            pscMap.put(id, crit);
                        }
                        return crit;
                    }});
        
        when(mock.update(any(StudyContactDTO.class))).thenAnswer(new Answer<StudyContactDTO>(){

            @Override
            public StudyContactDTO answer(
                    InvocationOnMock invocation) throws Throwable {
                StudyContactDTO crit = (StudyContactDTO) invocation.getArguments()[0];
                crit.setOrganizationalContactIi(IiConverter.convertToIi("abcdef"));
                Long id = IiConverter.convertToLong(crit.getIdentifier());
                if (StudyContactRoleCode.DESIGNEE_CONTACT.equals(
                        CdConverter.convertCdToEnum(StudyContactRoleCode.class, crit.getRoleCode()))) {
                    dscMap.put(id, crit);
                } else if (StudyContactRoleCode.PIO_CONTACT.equals(
                        CdConverter.convertCdToEnum(StudyContactRoleCode.class, crit.getRoleCode()))) {
                    pscMap.put(id, crit);
                }
                
                return crit;
            }});
        
        when(mock.nullify(any(StudyContactDTO.class))).thenAnswer(new Answer<StudyContactDTO>(){

            @Override
            public StudyContactDTO answer(
                    InvocationOnMock invocation) throws Throwable {
                final StudyContactDTO crit = (StudyContactDTO) invocation.getArguments()[0];
                crit.setOrganizationalContactIi(IiConverter.convertToIi("abcdef"));
                Long id = IiConverter.convertToLong(crit.getIdentifier());
                if (StudyContactRoleCode.DESIGNEE_CONTACT.equals(
                        CdConverter.convertCdToEnum(StudyContactRoleCode.class, crit.getRoleCode()))) {
                    dscMap.put(id, crit);
                } else if (StudyContactRoleCode.PIO_CONTACT.equals(
                        CdConverter.convertCdToEnum(StudyContactRoleCode.class, crit.getRoleCode()))) {
                    pscMap.put(id, crit);
                }
                crit.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.NULLIFIED));
                return crit;
            }});

        
        return mock;
    }
    
    private StudyContactDTO createSCDto(long index) {
        StudyContactDTO sc = new StudyContactDTO();
        
        
        sc.setIdentifier(IiConverter.convertToIi(index));
        sc.setOrganizationalContactIi(IiConverter.convertToIi("abcdef"));
        sc.setPrsUserName(StConverter.convertToSt("prsUserName" +  index));
        sc.setComments(StConverter.convertToSt("comments"));
        sc.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        
        List<String> phones = new ArrayList<String>();
        phones.add("703-111-1111X123" + index);
            
        List<String> emails = new ArrayList<String>();
        emails.add(index + "someone@some.com");
        DSet<Tel> dsetList = null;
        dsetList =  DSetConverter.convertListToDSet(phones, DSetConverter.TYPE_PHONE, dsetList);
        dsetList =  DSetConverter.convertListToDSet(emails, DSetConverter.TYPE_EMAIL, dsetList);
        
        sc.setTelecomAddresses(dsetList);
        
        return sc;
    }
    
    /**
     * Creates the action context with a mock request.
     */
    public static void initActionContext() {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.addContainerProvider(new XWorkConfigurationProvider());
        Configuration config = configurationManager.getConfiguration();
        Container container = config.getContainer();

        ValueStack stack = container.getInstance(ValueStackFactory.class).createValueStack();
        stack.getContext().put(ActionContext.CONTAINER, container);
        ActionContext.setContext(new ActionContext(stack.getContext()));

        assertNotNull(ActionContext.getContext());

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("");
        request.setSession(new MockHttpSession());
        ServletActionContext.setRequest(request);

        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletActionContext.setResponse(response);
    }

    /**
     * Set up services.
     */
    @Before
    public void setUpServices() {
        PaRegistry.getInstance().setServiceLocator(new MockServiceLocator());
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
    }

    /**
     * Initialize the mock request.
     */
    @Before
    public void initMockRequest() {
        initActionContext();
        HttpSession session = ServletActionContext.getRequest().getSession();
        StudyProtocolQueryDTO protocolSessionBean = new StudyProtocolQueryDTO();
        protocolSessionBean.setStudyProtocolId(1L);
        session.setAttribute(Constants.TRIAL_SUMMARY, protocolSessionBean);
    }
    
    /**
     * Clean out the action context to ensure one test does not impact another.
     */
    @After
    public void cleanUpActionContext() {
        ActionContext.setContext(null);
    }

    /**
     * @return MockHttpServletRequest
     */
    protected MockHttpServletRequest getRequest() {
        return (MockHttpServletRequest) ServletActionContext.getRequest();
    }

    /**
     * @return MockHttpSession
     */
    protected MockHttpSession getSession() {
        return (MockHttpSession) ServletActionContext.getRequest().getSession();
    }

    /**
     * Gets the response.
     *
     * @return the response
     */
    protected MockHttpServletResponse getResponse() {
        return (MockHttpServletResponse) ServletActionContext.getResponse();
    }
    
}

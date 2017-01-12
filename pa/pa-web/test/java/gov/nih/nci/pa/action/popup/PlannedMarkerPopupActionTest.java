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
package gov.nih.nci.pa.action.popup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.cadsr.domain.Designation;
import gov.nih.nci.cadsr.domain.EnumeratedValueDomain;
import gov.nih.nci.cadsr.domain.PermissibleValue;
import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
import gov.nih.nci.cadsr.domain.ValueMeaning;
import gov.nih.nci.pa.action.AbstractPaActionTest;
import gov.nih.nci.pa.dto.CaDSRWebDTO;
import gov.nih.nci.pa.dto.PlannedMarkerWebDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceBean;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.CsmHelper;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for testing the various caDSR related actions for markers.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class PlannedMarkerPopupActionTest extends AbstractPaActionTest {
    private PlannedMarkerPopupAction plannedMarkerAction;
    ProtocolQueryServiceBean protocolQrySrv = new ProtocolQueryServiceBean();
    ApplicationService appService = mock(ApplicationService.class);
    LookUpTableServiceRemote lookUpTableSrv = mock(LookUpTableServiceRemote.class);
    ValueMeaning vm = new ValueMeaning();
    PermissibleValue pv = new PermissibleValue();
    ValueDomainPermissibleValue vdpv = new ValueDomainPermissibleValue();
    @Before
    public void setUp() throws Exception {
        plannedMarkerAction = new PlannedMarkerPopupAction();
        plannedMarkerAction.prepare();

        CsmHelper userHelper = new CsmHelper("login1");
        CSMUserService.setInstance(new MockCSMUserService());
        
        getSession().setAttribute(Constants.LOGGED_USER_NAME, "login1");
        getSession().setAttribute("CsmHelper", userHelper);
        EnumeratedValueDomain vd = new EnumeratedValueDomain();
        vd.setId("1");
        
        DataElement de = new DataElement();
        de.setValueDomain(vd);

        List<Object> deResults = new ArrayList<Object>();
        deResults.add(de);


        when(appService.search(eq(DataElement.class), any(DataElement.class))).thenReturn(deResults);

     
        pv.setValue("N-Cadherin");
        
        
       
        vm.setLongName("N-Cadherin");
        vm.setDescription("cadherin");
        vm.setPublicID(2578250L);
        pv.setValueMeaning(vm);


        vdpv.setPermissibleValue(pv);
        vdpv.setId("1");
        List<Object> results = new ArrayList<Object>();
        results.add(vdpv);
        when(appService.query(any(DetachedCriteria.class))).thenReturn(deResults).thenReturn(results);
        plannedMarkerAction.setAppService(appService);
        assertNotNull(plannedMarkerAction.getAppService());
    }

    /**
     * Tests lookup of marker's via caDSR
     * @throws Exception  Exception
     */
    @Test
    public void testLookup() throws Exception {
       assertEquals(plannedMarkerAction.lookup(), "results");
       assertNotNull(getRequest().getAttribute(Constants.FAILURE_MESSAGE));

       getRequest().clearAttributes();
       plannedMarkerAction.setPublicId("foo");
       assertEquals(plannedMarkerAction.lookup(), "results");
       assertNotNull(getRequest().getAttribute(Constants.FAILURE_MESSAGE));
       ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
       PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
       when(PaRegistry.getLookUpTableService()).thenReturn(lookUpTableSrv);
       plannedMarkerAction.setLookUpTableService(lookUpTableSrv);
       when(lookUpTableSrv.getPropertyValue("CDE_PUBLIC_ID")).thenReturn("5473");
       when(lookUpTableSrv.getPropertyValue("Latest_Version_Indicator")).thenReturn("Yes");
       when(lookUpTableSrv.getPropertyValue("CDE_Version")).thenReturn("9.0");
       
       getRequest().clearAttributes();
       plannedMarkerAction.setPublicId(null);
       plannedMarkerAction.setName("lyco");
       plannedMarkerAction.setSearchBothTerms("both");
       assertEquals(plannedMarkerAction.lookup(), "results");
       assertNull(getRequest().getAttribute(Constants.FAILURE_MESSAGE));
       assertFalse(plannedMarkerAction.getMarkers().isEmpty());

       getRequest().clearAttributes();
       setUp();
       plannedMarkerAction.setPublicId(null);
       plannedMarkerAction.setName("ly-co");
       assertEquals(plannedMarkerAction.lookup(), "results");
       assertNull(getRequest().getAttribute(Constants.FAILURE_MESSAGE));
       assertFalse(plannedMarkerAction.getMarkers().isEmpty());
       
       getRequest().clearAttributes();
       setUp();
       Designation designation = new Designation();
       designation.setId("1L");
       designation.setName("Bivinyl");
       designation.setType("Biomarker Synonym");
       
       
       Designation designation1 = new Designation();
       designation1.setId("2L");
       designation1.setName("N-Cadherin");
       designation1.setType("Biomarker Marker");
       
       Collection<gov.nih.nci.cadsr.domain.Designation> designationCollection = new HashSet<Designation>();
       designationCollection.add(designation);
       designationCollection.add(designation1);
       vm.setDesignationCollection(designationCollection);
       plannedMarkerAction.setName("N-Cadherin");
       plannedMarkerAction.setSearchBothTerms("both");
       assertEquals(plannedMarkerAction.lookup(), "results");
       assertNull(getRequest().getAttribute(Constants.FAILURE_MESSAGE));
       assertFalse(plannedMarkerAction.getMarkers().isEmpty());
       assertEquals("N-Cadherin (Bivinyl)", plannedMarkerAction.getMarkers().get(0).getVmName());
       assertEquals("Bivinyl" , plannedMarkerAction.getMarkers().get(0).getAltNames().get(0));

       getRequest().clearAttributes();
       setUp();
       Designation designation2 = new Designation();
       designation2.setId("3L");
       designation2.setName("alpha");
       designation2.setType("Biomarker Synonym");
       plannedMarkerAction.setName("alpha");
       designationCollection.add(designation2);
       vm.setDesignationCollection(designationCollection);
       plannedMarkerAction.setSearchBothTerms("Synonym");
       assertEquals(plannedMarkerAction.lookup(), "results");
       assertNull(getRequest().getAttribute(Constants.FAILURE_MESSAGE));
       assertFalse(plannedMarkerAction.getMarkers().isEmpty());
       assertEquals("N-Cadherin (alpha; Bivinyl)", plannedMarkerAction.getMarkers().get(0).getVmName());
       assertEquals("alpha" , plannedMarkerAction.getMarkers().get(0).getAltNames().get(0));
       assertEquals("Bivinyl" , plannedMarkerAction.getMarkers().get(0).getAltNames().get(1));
       assertFalse(plannedMarkerAction.getMarkers().get(0).getAltNames().get(0).equals("N-Cadherin"));
       
       getRequest().clearAttributes();
       setUp();
       plannedMarkerAction.setName("alpha1");
       plannedMarkerAction.setSearchBothTerms("Synonym");
       assertEquals(plannedMarkerAction.lookup(), "results");
       assertNull(getRequest().getAttribute(Constants.FAILURE_MESSAGE));
       assertFalse(plannedMarkerAction.getMarkers().isEmpty());

       getRequest().clearAttributes();
       setUp();
       plannedMarkerAction.setPublicId("2578250");
       assertEquals(plannedMarkerAction.lookup(), "results");
       assertNull(getRequest().getAttribute(Constants.FAILURE_MESSAGE));
       assertFalse(plannedMarkerAction.getMarkers().isEmpty());
       plannedMarkerAction.setMarkers(new ArrayList<CaDSRWebDTO>());
       assertTrue(plannedMarkerAction.getMarkers().isEmpty());
    }
    /**
     * Test initialization of the marker request email form.
     * @throws PAException on error
     */
    @Test
    public void testSetupEmailRequest() throws PAException {
        assertEquals(plannedMarkerAction.setupEmailRequest(), "email");
        assertNotNull(plannedMarkerAction.getToEmail());
        assertNotNull(plannedMarkerAction.getPlannedMarker().getFromEmail());
        getSession().setAttribute(Constants.LOGGED_USER_NAME, "suAbstractor");
        assertEquals(plannedMarkerAction.setupEmailRequest(), "email");
    }

    /**
     * Tests sending of cde marker request.
     */
    @Test
    public void testSendEmailRequest() throws PAException {
        assertEquals(plannedMarkerAction.sendEmailRequest(), "email");
        assertTrue(plannedMarkerAction.hasFieldErrors());
        assertFalse(plannedMarkerAction.isPassedValidation());
        plannedMarkerAction.clearErrorsAndMessages();

        PlannedMarkerWebDTO dto = new PlannedMarkerWebDTO();
        dto.setFromEmail("from@example.com");
        dto.setName("Name");
        dto.setFoundInHugo(true);

        plannedMarkerAction.setToEmail("to@example.com");
        plannedMarkerAction.setSubject("subject");
        plannedMarkerAction.setPlannedMarker(dto);
        assertEquals(plannedMarkerAction.sendEmailRequest(), "email");
        assertTrue(plannedMarkerAction.hasFieldErrors());
        assertFalse(plannedMarkerAction.isPassedValidation());
        assertNotNull(plannedMarkerAction.getSubject());
        plannedMarkerAction.clearErrorsAndMessages();

        dto.setHugoCode("HUGO");
        assertEquals(plannedMarkerAction.sendEmailRequest(), "email");
        assertFalse(plannedMarkerAction.hasFieldErrors());
        assertTrue(plannedMarkerAction.isPassedValidation());
        plannedMarkerAction.setPassedValidation(true);
        assertTrue(plannedMarkerAction.isPassedValidation());
    }
    

    /**
     * Tests sending of cde marker request.
     */
    @Test
    public void testsendEmailRequestWithMarkerUpdate() throws PAException {
        assertEquals(plannedMarkerAction.sendEmailRequest(), "email");
        assertTrue(plannedMarkerAction.hasFieldErrors());
        assertFalse(plannedMarkerAction.isPassedValidation());
        plannedMarkerAction.clearErrorsAndMessages();

        PlannedMarkerWebDTO dto = new PlannedMarkerWebDTO();
        dto.setFromEmail("from@example.com");
        dto.setName("Name");
        dto.setFoundInHugo(true);

        plannedMarkerAction.setToEmail("to@example.com");
        plannedMarkerAction.setSubject("subject");
        plannedMarkerAction.setPlannedMarker(dto);
        assertEquals(plannedMarkerAction.sendEmailRequest(), "email");
        assertTrue(plannedMarkerAction.hasFieldErrors());
        assertFalse(plannedMarkerAction.isPassedValidation());
        assertNotNull(plannedMarkerAction.getSubject());
        plannedMarkerAction.clearErrorsAndMessages();
        StudyProtocolDTO spdto = new StudyProtocolDTO();
        spdto.setIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        dto.setHugoCode("HUGO");
        plannedMarkerAction.setNciIdentifier("1");
        assertEquals(plannedMarkerAction.sendEmailRequestWithMarkerUpdate(), "email");
        assertFalse(plannedMarkerAction.hasFieldErrors());
        assertTrue(plannedMarkerAction.isPassedValidation());
        plannedMarkerAction.setPassedValidation(true);
        assertTrue(plannedMarkerAction.isPassedValidation());
    }
    @Test
    public void testAccept() throws PAException {
        String result = plannedMarkerAction.accept();
        assertTrue(result.equals("accept"));
    }
}

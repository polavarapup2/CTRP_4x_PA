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
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.cadsr.domain.Designation;
import gov.nih.nci.cadsr.domain.PermissibleValue;
import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
import gov.nih.nci.cadsr.domain.ValueMeaning;
import gov.nih.nci.pa.dto.PlannedMarkerWebDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class PlannedMarkerActionTest extends AbstractPaActionTest {
    private PlannedMarkerAction plannedMarkerAction;
    private PlannedMarkerServiceLocal plannedMarkerService;
    ApplicationService appService = mock(ApplicationService.class);
    ValueMeaning vm = new ValueMeaning();

    @Before
    public void setUp() throws Exception {
        plannedMarkerAction = new PlannedMarkerAction();
        plannedMarkerService = PaRegistry.getPlannedMarkerService();
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        plannedMarkerAction.prepare();

        PermissibleValue pv = new PermissibleValue();
        pv.setValue("N-Cadherin");

        
        vm.setLongName("N-Cadherin");
        vm.setDescription("cadherin");
        pv.setValueMeaning(vm);

        ValueDomainPermissibleValue vdpv = new ValueDomainPermissibleValue();
        vdpv.setPermissibleValue(pv);

        List<Object> results = new ArrayList<Object>();
        results.add(vdpv);

        
        when(appService.search(eq(ValueDomainPermissibleValue.class), any(ValueDomainPermissibleValue.class))).thenReturn(results);
        when(appService.query(any(DetachedCriteria.class))).thenReturn(results);
        plannedMarkerAction.setAppService(appService);
        PlannedMarkerServiceLocal plannedMarkerService = mock(PlannedMarkerServiceLocal.class);PlannedMarkerDTO oldValue = new PlannedMarkerDTO();
        oldValue.setAssayTypeCode(CdConverter.convertStringToCd("Microarray"));
        oldValue.setAssayPurposeCode(CdConverter.convertStringToCd("Stratification Factor"));
        oldValue.setAssayUseCode(CdConverter.convertStringToCd("Integral"));
        oldValue.setTissueSpecimenTypeCode(CdConverter.convertStringToCd("Serum"));
        oldValue.setTissueCollectionMethodCode(CdConverter.convertStringToCd("Mandatory"));
        oldValue.setEvaluationType(CdConverter.convertStringToCd("Methylation"));
        
        when(plannedMarkerService.getPlannedMarkerWithID(1L)).thenReturn(oldValue);
        plannedMarkerAction.setPlannedMarkerService(plannedMarkerService);
    }

    @Test
    public void testAdd() throws PAException {
        assertNotNull(plannedMarkerAction.getAppService());
        PlannedMarkerDTO oldValue = new PlannedMarkerDTO();
        oldValue.setAssayTypeCode(CdConverter.convertStringToCd("Microarray"));
        oldValue.setAssayPurposeCode(CdConverter.convertStringToCd("Stratification Factor"));
        oldValue.setAssayUseCode(CdConverter.convertStringToCd("Integral"));
        oldValue.setTissueSpecimenTypeCode(CdConverter.convertStringToCd("Serum"));
        oldValue.setTissueCollectionMethodCode(CdConverter.convertStringToCd("Mandatory"));
        oldValue.setEvaluationType(CdConverter.convertStringToCd("Methylation"));
        
        when(plannedMarkerService.getPlannedMarkerWithID(1L)).thenReturn(oldValue);
        plannedMarkerAction.add();
        assertTrue(plannedMarkerAction.hasFieldErrors());
        plannedMarkerAction.clearErrorsAndMessages();

        PlannedMarkerWebDTO webDTO = new PlannedMarkerWebDTO();
        webDTO.setName("Marker #1");
        webDTO.setAssayType("Other");
        webDTO.setAssayUse("Correlative");
        webDTO.setAssayPurpose("Stratification Factor");
        webDTO.setTissueSpecimenType("Serum");
        webDTO.setTissueCollectionMethod("Unspecified");
        webDTO.setEvaluationType("Subtyping");
        plannedMarkerAction.setPlannedMarker(webDTO);

        plannedMarkerAction.add();
        assertTrue(plannedMarkerAction.hasFieldErrors());
        plannedMarkerAction.clearErrorsAndMessages();

        webDTO.setAssayTypeOtherText("More Text");
        
        plannedMarkerAction.setPlannedMarker(webDTO);
        assertEquals("list", plannedMarkerAction.add());
    }
    
    @Test
    public void testAddSave() throws PAException {
        plannedMarkerAction.add();
        plannedMarkerAction.setSaveResetAttribute(true);
        assertTrue(plannedMarkerAction.isSaveResetAttribute());
        plannedMarkerAction.loadEditForm();
        assertTrue(plannedMarkerAction.hasFieldErrors());
        plannedMarkerAction.clearErrorsAndMessages();
        PlannedMarkerWebDTO webDTO = new PlannedMarkerWebDTO();
        webDTO.setName("Marker #1");
        webDTO.setAssayType("Other");
        webDTO.setAssayUse("Correlative");
        webDTO.setAssayPurpose("Stratification Factor");
        webDTO.setTissueSpecimenType("Serum");
        webDTO.setTissueCollectionMethod("Unspecified");
        webDTO.setEvaluationType("Subtyping");
        plannedMarkerAction.setPlannedMarker(webDTO);

        plannedMarkerAction.add();
        assertTrue(plannedMarkerAction.hasFieldErrors());
        assertEquals("edit", plannedMarkerAction.add());
        plannedMarkerAction.clearErrorsAndMessages();
        webDTO.setAssayTypeOtherText("More Text");
        
        plannedMarkerAction.setPlannedMarker(webDTO);
        assertEquals("list", plannedMarkerAction.add());

    }
    @Test
    public void testAddSaveMarker() throws PAException {
        plannedMarkerAction.add();
        plannedMarkerAction.setSaveResetMarker(true);
        assertTrue(plannedMarkerAction.isSaveResetMarker());
        plannedMarkerAction.loadEditForm();
        assertTrue(plannedMarkerAction.hasFieldErrors());
        plannedMarkerAction.clearErrorsAndMessages();
        PlannedMarkerWebDTO webDTO = new PlannedMarkerWebDTO();
        webDTO.setName("Marker #1");
        webDTO.setAssayType("Other");
        webDTO.setAssayUse("Correlative");
        webDTO.setAssayPurpose("Stratification Factor");
        webDTO.setTissueSpecimenType("Serum");
        webDTO.setTissueCollectionMethod("Unspecified");
        webDTO.setEvaluationType("Subtyping");
        plannedMarkerAction.setPlannedMarker(webDTO);

        plannedMarkerAction.add();
        assertTrue(plannedMarkerAction.hasFieldErrors());
        assertEquals("edit", plannedMarkerAction.add());
        plannedMarkerAction.clearErrorsAndMessages();
        webDTO.setAssayTypeOtherText("More Text");
        
        plannedMarkerAction.setPlannedMarker(webDTO);
        assertEquals("list", plannedMarkerAction.add());
        plannedMarkerAction.setPendingStatus(true);
        assertTrue(plannedMarkerAction.isPendingStatus());
        plannedMarkerAction.setCadsrId("cadsrId");
        assertEquals("cadsrId", plannedMarkerAction.getCadsrId());
    }
    
    @Test
    public void testEdit() throws PAException {
        plannedMarkerAction.setSelectedRowIdentifier("1");
        assertEquals("edit", plannedMarkerAction.edit());

        PlannedMarkerWebDTO webDTO = new PlannedMarkerWebDTO();
        webDTO.setId(1L);
        webDTO.setName("Marker #1");
        webDTO.setAssayType("Other");
        webDTO.setAssayUse("Integral");
        webDTO.setAssayPurpose("Stratification Factor");
        webDTO.setTissueSpecimenType("Serum");
        webDTO.setTissueCollectionMethod("Unspecified");
        webDTO.setStatus("Pending");
        webDTO.setEvaluationType("Subtyping");
        plannedMarkerAction.setPlannedMarker(webDTO);

        assertEquals("edit", plannedMarkerAction.update());
        assertTrue(plannedMarkerAction.hasFieldErrors());
        plannedMarkerAction.clearErrorsAndMessages();

        webDTO.setAssayTypeOtherText("More Text");
        
        plannedMarkerAction.setPlannedMarker(webDTO);
        assertEquals("list", plannedMarkerAction.update());
        plannedMarkerAction.loadEditForm();
    }

    @Test
    public void testExecute() throws PAException {
        assertEquals(plannedMarkerAction.execute(), "list");
        assertNotNull(plannedMarkerAction.getPlannedMarkerList());
        plannedMarkerAction.setMarkerAttributesService(null);
        plannedMarkerAction.setPermissibleService(null);
    }

    @Test
    public void testDelete() throws PAException {
        plannedMarkerAction.setObjectsToDelete(new String[] {"1"});
        assertEquals(plannedMarkerAction.delete(), "list");
    }

    @Test
    public void testDisplaySelectedCDE() throws ApplicationException {
        //CDE ID for the N-Cadherin Marker

        plannedMarkerAction.setCdeId("6C28341E-9EF6-6D9E-E040-BB89AD435B0F");
        assertNull(plannedMarkerAction.getPlannedMarker().getName());
        assertNull(plannedMarkerAction.getPlannedMarker().getDescription());
        assertNull(plannedMarkerAction.getPlannedMarker().getMeaning());

        assertEquals(plannedMarkerAction.displaySelectedCDE(), "edit");
        assertEquals(plannedMarkerAction.getPlannedMarker().getName(), "N-Cadherin");
        assertEquals(plannedMarkerAction.getPlannedMarker().getMeaning(), "N-Cadherin");
        assertTrue(StringUtils.contains(plannedMarkerAction.getPlannedMarker().getDescription(), "cadherin"));
        
        Designation designation = new Designation();
        designation.setId("1L");
        designation.setName("Bivinyl");
        designation.setType("Biomarker Synonym");
        
        Collection<gov.nih.nci.cadsr.domain.Designation> designationCollection = new HashSet<Designation>();
        designationCollection.add(designation);
        vm.setDesignationCollection(designationCollection);
        
        assertEquals(plannedMarkerAction.displaySelectedCDE(), "edit");
        assertEquals(plannedMarkerAction.getPlannedMarker().getName(), "N-Cadherin (Bivinyl)");
        assertEquals(plannedMarkerAction.getPlannedMarker().getMeaning(), "N-Cadherin");
        assertEquals(plannedMarkerAction.getPlannedMarker().getSynonymNames(), "Bivinyl");
        assertTrue(StringUtils.contains(plannedMarkerAction.getPlannedMarker().getDescription(), "cadherin"));
        
        Designation designation1 = new Designation();
        designation1.setId("2L");
        designation1.setName("alpha");
        designation1.setType("Biomarker Synonym");
        Designation designation2 = new Designation();
        designation2.setId("3L");
        designation2.setName("N-Cadherin");
        designation2.setType("Biomarker Marker");

        designationCollection.add(designation1);
        designationCollection.add(designation2);
        vm.setDesignationCollection(designationCollection);

        assertEquals(plannedMarkerAction.displaySelectedCDE(), "edit");
        assertEquals(plannedMarkerAction.getPlannedMarker().getName(), "N-Cadherin (alpha; Bivinyl)");
        assertEquals(plannedMarkerAction.getPlannedMarker().getMeaning(), "N-Cadherin");
        assertEquals(plannedMarkerAction.getPlannedMarker().getSynonymNames(), "alpha; Bivinyl");
        assertFalse(plannedMarkerAction.getPlannedMarker().getSynonymNames().equals("Bivinyl; alpha; N-Cadherin"));
        assertTrue(StringUtils.contains(plannedMarkerAction.getPlannedMarker().getDescription(), "cadherin"));
    }
    
    @Test
    public void testViewSelectedProtocolMarker() throws PAException {
       plannedMarkerAction.setNciIdentifier("NCI-000-0001");
       String result = plannedMarkerAction.viewSelectedProtocolMarker();
       assertEquals("list", result);
    }
}

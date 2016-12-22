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
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.AssayPurposeCode;
import gov.nih.nci.pa.enums.AssayTypeCode;
import gov.nih.nci.pa.enums.AssayUseCode;
import gov.nih.nci.pa.enums.BioMarkerAttributesCode;
import gov.nih.nci.pa.enums.TissueCollectionMethodCode;
import gov.nih.nci.pa.enums.TissueSpecimenTypeCode;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class PlannedMarkerServiceTest extends AbstractHibernateTestCase {
    private final PlannedMarkerServiceBean bean = new PlannedMarkerServiceBean();
    private Ii spIi;
    
    private final StudyProtocolServiceLocal studyProtocolService = mock(StudyProtocolServiceLocal.class);

    @Before
    public void setUp() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();
        spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        bean.setStudyProtocolService(studyProtocolService);
     }

    @Test
    public void managePlannedMarker()  throws Exception {
        //Checking to make sure the 2 primed planned markers are present.
        List<PlannedMarkerDTO> dtos = bean.getByStudyProtocol(spIi);
        assertEquals(2, dtos.size());

        PlannedMarkerDTO markerDTO = bean.create(constructPlannedMarker());
        assertNotNull(markerDTO);
        assertNotNull(markerDTO.getIdentifier());

        dtos = bean.getByStudyProtocol(spIi);
        assertEquals(3, dtos.size());
       
        PlannedMarkerDTO dto = bean.getPlannedMarker(IiConverter.convertToIi(5L));
        assertTrue(dto !=null);
        assertEquals("Pending", dto.getStatusCode().getCode());
        
        dto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
        bean.update(dto);
        dto = bean.getPlannedMarker(IiConverter.convertToIi(5L));
        assertTrue(dto !=null);
        assertEquals("Active", dto.getStatusCode().getCode());
        
        try {
            bean.create(constructPlannedMarker());
            fail();
        } catch (PAException e) {
           //expected, testing duplication
        }
        
        
    }

    private PlannedMarkerDTO constructPlannedMarker() {
        PlannedMarkerDTO markerDTO = new PlannedMarkerDTO();
        markerDTO.setStudyProtocolIdentifier(spIi);
        markerDTO.setName(StConverter.convertToSt("Biomarker"));
        markerDTO.setLongName(StConverter.convertToSt("Biomarker long name"));
        markerDTO.setHugoBiomarkerCode(CdConverter.convertStringToCd("HUGO Biomarker Code"));
        markerDTO.setAssayTypeCode(CdConverter.convertToCd(AssayTypeCode.OTHER));
        markerDTO.setAssayTypeOtherText(StConverter.convertToSt("Assay Type Other Text"));
        markerDTO.setAssayUseCode(CdConverter.convertToCd(AssayUseCode.INTEGRAL));
        markerDTO.setAssayPurposeCode(CdConverter.convertToCd(AssayPurposeCode.ELIGIBILITY_CRITERION));
        markerDTO.setAssayPurposeOtherText(StConverter.convertToSt("Assay Purpose Other Text"));
        markerDTO.setTissueSpecimenTypeCode(CdConverter.convertToCd(TissueSpecimenTypeCode.TISSUE));
        markerDTO.setTissueCollectionMethodCode(CdConverter.convertToCd(TissueCollectionMethodCode.MANDATORY));
        markerDTO.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.PENDING));
        markerDTO.setPermissibleValue(IiConverter.convertToIi(1L));
        return markerDTO;
    }
    
    
    @Test
    public void getPendingPlannedMarkersShortName() throws PAException {
        String name = "Marker #1";
        constructPlannedMarker();
        List<PlannedMarkerDTO> markers = bean.getPendingPlannedMarkersShortName(name);
        assertTrue(markers.size() > 0);
    }
    @Test
    public void getPendingPlannedMarkersWithProtocolId() throws PAException {
        List<Long> listOfIds = new ArrayList<Long>();
        listOfIds.add(1L);
        listOfIds.add(2L);
        constructPlannedMarker();
        List<PlannedMarkerDTO> markers = bean.getPendingPlannedMarkersWithProtocolId(listOfIds);
        assertTrue(markers.size() > 0);
    }
    
    @Test
    public void getPendingPlannedMarkersShortNameAndNCIIdTest() throws PAException {
        String name = "Marker #1";
        List<Long> listOfIds = new ArrayList<Long>();
        listOfIds.add(1L);
        listOfIds.add(2L);
        constructPlannedMarker(); 
        String nciIdentifier = "NCI-0000-2010";
        when(PaRegistry.getStudyProtocolService()).thenReturn(studyProtocolService);
        when(studyProtocolService.getProtocolIdsWithNCIId(nciIdentifier)).thenReturn(listOfIds);
        List<PlannedMarkerDTO> markers = bean.getPendingPlannedMarkersShortNameAndNCIId(name, "NCI-0000-2010");
        assertTrue(markers.size() > 0);
        
        
        name = "Marker #1";
        listOfIds = new ArrayList<Long>();    
        when(studyProtocolService.getProtocolIdsWithNCIId(nciIdentifier)).thenReturn(listOfIds);
        constructPlannedMarker(); 
        markers =  bean.getPendingPlannedMarkersShortNameAndNCIId(name, "");
        assertTrue(markers.size() > 0);
    }
    
    @Test
    public void getPlannedMarkersTest() throws PAException {
        List<PlannedMarkerDTO> list = bean.getPlannedMarkers();
        assertTrue(list.size() > 0);
    }
    
    @Test
    public void getPendingPlannedMarkersWithNameTest() throws PAException {
        List<PlannedMarkerDTO> list = bean.getPendingPlannedMarkersWithName("Marker #1");
        assertTrue(list.size() > 0);
    }
    
    @Test
    public void getPendingPlannedMarkerWithSyncIDTest() throws PAException {
    	List<PlannedMarkerDTO> list = bean.getPendingPlannedMarkerWithSyncID(1L);
    	assertTrue(list.size() > 0);
    }
    @Test
    public void updatePlannedMarkerAttributeValuesTest() throws PAException {
        bean.updatePlannedMarkerAttributeValues(BioMarkerAttributesCode.ASSAY_TYPE_CODE, "PCR", "Microarray");
        PlannedMarkerDTO dto = bean.getPlannedMarkerWithID(5L);
        assertEquals("Microarray", dto.getAssayTypeCode().getCode());
    }
    @Test
    public void updateStatusByPMSynIDTest() throws PAException {
        bean.updateStatusByPMSynID(1L, ActiveInactivePendingCode.ACTIVE.getName());
        PlannedMarkerDTO dto = bean.getPlannedMarkerWithID(5L);
        assertEquals("Active", dto.getStatusCode().getCode());
    }
    @Test
    public void updateStatusOldIDByPMSynIDTest() throws PAException {
        bean.updateStatusOldIDByPMSynID(1L, 2L, ActiveInactivePendingCode.PENDING.getName());
        PlannedMarkerDTO dto = bean.getPlannedMarkerWithID(5L);
        assertEquals("Pending", dto.getStatusCode().getCode());
        assertEquals("2", dto.getPermissibleValue().getExtension());
    }
}

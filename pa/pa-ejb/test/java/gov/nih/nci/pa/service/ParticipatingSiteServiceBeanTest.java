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
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteContactDTO;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.MockPoServiceLocator;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

/**
 * @author Michael Visee
 */
public class ParticipatingSiteServiceBeanTest {
    private CorrelationUtils corrUtils = mock(CorrelationUtils.class);
    private OrganizationCorrelationServiceRemote organizationCorrelationService =
            mock(OrganizationCorrelationServiceRemote.class);
    private StudyProtocolServiceLocal studyProtocolService = mock(StudyProtocolServiceLocal.class);
    private StudySiteServiceLocal studySiteService = mock(StudySiteServiceLocal.class);
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService =
            mock(StudySiteAccrualStatusServiceLocal.class);
    private StudySiteContactServiceLocal studySiteContactService = mock(StudySiteContactServiceLocal.class);
    private ParticipatingSiteServiceBean sut;

    /**
     * Exception rule.
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Initialization method.
     */
    @Before
    public void init() {
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
    }

    /**
     * Creates a real ParticipatingSiteServiceBean and inject the mock services in it.
     * @return A real ParticipatingSiteServiceBean with mock services injected.
     */
    private ParticipatingSiteServiceBean createParticipatingSiteServiceBean() {
        ParticipatingSiteServiceBean service = new ParticipatingSiteServiceBean();
        setDependencies(service);
        return service;
    }

    /**
     * Creates a mock ParticipatingSiteServiceBean and inject the mock services in it.
     * @return A mock ParticipatingSiteServiceBean with mock services injected.
     */
    private ParticipatingSiteServiceBean createParticipatingSiteServiceBeanMock() {
        ParticipatingSiteServiceBean service = mock(ParticipatingSiteServiceBean.class);
        doCallRealMethod().when(service).getCorrUtils();
        doCallRealMethod().when(service).getOrganizationCorrelationService();
        doCallRealMethod().when(service).getStudyProtocolService();
        doCallRealMethod().when(service).getStudySiteService();
        doCallRealMethod().when(service).getStudySiteAccrualStatusService();
        doCallRealMethod().when(service).getStudySiteContactService();
        doCallRealMethod().when(service).getStudySiteAccrualStatusService();
        doCallRealMethod().when(service).setCorrUtils(corrUtils);
        doCallRealMethod().when(service).setOrganizationCorrelationService(organizationCorrelationService);
        doCallRealMethod().when(service).setStudyProtocolService(studyProtocolService);
        doCallRealMethod().when(service).setStudySiteService(studySiteService);
        doCallRealMethod().when(service).setStudySiteAccrualStatusService(studySiteAccrualStatusService);
        doCallRealMethod().when(service).setStudySiteContactService(studySiteContactService);
        doCallRealMethod().when(service).setStudySiteAccrualStatusService(studySiteAccrualStatusService);
        setDependencies(service);
        return service;
    }

    /**
     * Inject the mock services in the given ParticipatingSiteServiceBean.
     * @param service The ParticipatingSiteServiceBean to setup with mock services
     */
    private void setDependencies(ParticipatingSiteServiceBean service) {
        service.setCorrUtils(corrUtils);
        service.setOrganizationCorrelationService(organizationCorrelationService);
        service.setStudyProtocolService(studyProtocolService);
        service.setStudySiteService(studySiteService);
        service.setStudySiteAccrualStatusService(studySiteAccrualStatusService);
        service.setStudySiteContactService(studySiteContactService);
        service.setStudySiteAccrualStatusService(studySiteAccrualStatusService);
    }

    /**
     * test the getParticipatingSitesByStudyProtocol in the successful case
     * @throws PAException if an error occurs
     * @throws TooManyResultsException if an error occurs
     */
    @Test
    public void testGetParticipatingSitesByStudyProtocol() throws PAException, TooManyResultsException {
        sut = createParticipatingSiteServiceBeanMock();
        Ii spIi = IiConverter.convertToIi(1L);
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
        studyProtocolDTO.setIdentifier(spIi);
        when(studyProtocolService.getStudyProtocol(spIi)).thenReturn(studyProtocolDTO);
        List<StudySiteDTO> studySites = new ArrayList<StudySiteDTO>();
        when(studySiteService.search(any(StudySiteDTO.class), any(LimitOffset.class))).thenReturn(studySites);
        doCallRealMethod().when(sut).getParticipatingSitesByStudyProtocol(spIi);
        List<ParticipatingSiteDTO> participatingSites = new ArrayList<ParticipatingSiteDTO>();
        when(sut.convertStudySiteDTOsToParticipatingSiteDTOs(studySites)).thenReturn(participatingSites);

        List<ParticipatingSiteDTO> result = sut.getParticipatingSitesByStudyProtocol(spIi);

        assertEquals("Wrong result returned", participatingSites, result);
        ArgumentCaptor<StudySiteDTO> criteriaCaptor = ArgumentCaptor.forClass(StudySiteDTO.class);
        ArgumentCaptor<LimitOffset> limitOffsetCaptor = ArgumentCaptor.forClass(LimitOffset.class);
        verify(studySiteService).search(criteriaCaptor.capture(), limitOffsetCaptor.capture());
        assertEquals("Wrong protocol ii in the criteria", spIi, criteriaCaptor.getValue().getStudyProtocolIdentifier());
        LimitOffset limitOffset = limitOffsetCaptor.getValue();
        assertEquals("Wrong limit in limitOffset object", PAConstants.MAX_SEARCH_RESULTS, limitOffset.getLimit());
        assertEquals("Wrong offset in limitOffset object", 0, limitOffset.getOffset());
        verify(sut).convertStudySiteDTOsToParticipatingSiteDTOs(studySites);
    }

    /**
     * test the getParticipatingSitesByStudyProtocol in the successful case
     * @throws PAException if an error occurs
     * @throws TooManyResultsException if an error occurs
     */
    @Test
    public void testGetParticipatingSitesByStudyProtocolException() throws PAException, TooManyResultsException {
        thrown.expect(PAException.class);
        sut = createParticipatingSiteServiceBean();
        Ii spIi = IiConverter.convertToIi(1L);
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
        studyProtocolDTO.setIdentifier(spIi);
        when(studyProtocolService.getStudyProtocol(spIi)).thenReturn(studyProtocolDTO);
        TooManyResultsException exception = new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        when(studySiteService.search(any(StudySiteDTO.class), any(LimitOffset.class))).thenThrow(exception);
        sut.getParticipatingSitesByStudyProtocol(spIi);
    }

    /**
     * test the createStudySiteParticipant(studySiteDTO, currentStatusDTO, organizationDTO, healthCareFacilityDTO,
     * contacts) method .
     * @throws PAException if an error occurs
     */
    @Test
    public void testCreateStudySiteParticipant5params() throws PAException {
        sut = createParticipatingSiteServiceBeanMock();
        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setStudyProtocolIdentifier(IiConverter.convertToIi(1L));
        StudySiteAccrualStatusDTO currentStatusDTO = new StudySiteAccrualStatusDTO();
        OrganizationDTO organizationDTO = new OrganizationDTO();
        HealthCareFacilityDTO healthCareFacilityDTO = new HealthCareFacilityDTO();
        List<ParticipatingSiteContactDTO> contacts = new ArrayList<ParticipatingSiteContactDTO>();
        doCallRealMethod().when(sut).createStudySiteParticipant(studySiteDTO, currentStatusDTO, organizationDTO,
                                                                healthCareFacilityDTO, contacts);
        ParticipatingSiteDTO participatingSiteDTO = new ParticipatingSiteDTO();
        participatingSiteDTO.setIdentifier(IiConverter.convertToIi(1L));
        when(sut.createStudySiteParticipant(studySiteDTO, currentStatusDTO, organizationDTO, healthCareFacilityDTO))
            .thenReturn(participatingSiteDTO);
        when(sut.getParticipatingSite(participatingSiteDTO.getIdentifier())).thenReturn(participatingSiteDTO);

        sut.createStudySiteParticipant(studySiteDTO, currentStatusDTO, organizationDTO, healthCareFacilityDTO, contacts);

        InOrder inOrder = inOrder(sut);
        inOrder.verify(sut).checkStudyProtocol(studySiteDTO.getStudyProtocolIdentifier());
        inOrder.verify(sut).createStudySiteParticipant(studySiteDTO, currentStatusDTO, organizationDTO,
                                                       healthCareFacilityDTO);
        inOrder.verify(sut).updateStudySiteContacts(contacts, participatingSiteDTO);
        inOrder.verify(sut).getParticipatingSite(participatingSiteDTO.getIdentifier());
    }

    /**
     * test the createStudySiteParticipant(studySiteDTO, currentStatusDTO, healthCareFacilityIi, contacts) method.
     * @throws PAException if an error occurs
     */
    @Test
    public void testCreateStudySiteParticipant() throws PAException {
        sut = createParticipatingSiteServiceBeanMock();
        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setStudyProtocolIdentifier(IiConverter.convertToIi(1L));
        StudySiteAccrualStatusDTO currentStatusDTO = new StudySiteAccrualStatusDTO();
        Ii healthCareFacilityIi = IiConverter.convertToIi(1L);
        List<ParticipatingSiteContactDTO> contacts = new ArrayList<ParticipatingSiteContactDTO>();
        doCallRealMethod().when(sut).createStudySiteParticipant(studySiteDTO, currentStatusDTO, healthCareFacilityIi,
                                                                contacts);
        ParticipatingSiteDTO participatingSiteDTO = new ParticipatingSiteDTO();
        participatingSiteDTO.setIdentifier(IiConverter.convertToIi(1L));
        when(sut.createStudySiteParticipant(studySiteDTO, currentStatusDTO, healthCareFacilityIi))
            .thenReturn(participatingSiteDTO);
        when(sut.getParticipatingSite(participatingSiteDTO.getIdentifier())).thenReturn(participatingSiteDTO);

        sut.createStudySiteParticipant(studySiteDTO, currentStatusDTO, healthCareFacilityIi, contacts);

        InOrder inOrder = inOrder(sut);
        inOrder.verify(sut).checkStudyProtocol(studySiteDTO.getStudyProtocolIdentifier());
        inOrder.verify(sut).createStudySiteParticipant(studySiteDTO, currentStatusDTO, healthCareFacilityIi);
        inOrder.verify(sut).updateStudySiteContacts(contacts, participatingSiteDTO);
        inOrder.verify(sut).getParticipatingSite(participatingSiteDTO.getIdentifier());
    }
    
    @Test
    public void testUpdate() throws PAException {
    	sut = createParticipatingSiteServiceBeanMock();
    	StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setStudyProtocolIdentifier(IiConverter.convertToIi(1L));
        StudySiteAccrualStatusDTO currentStatusDTO = new StudySiteAccrualStatusDTO();
        List<ParticipatingSiteContactDTO> contacts = new ArrayList<ParticipatingSiteContactDTO>();
        doCallRealMethod().when(sut).updateStudySiteParticipant(studySiteDTO, currentStatusDTO, contacts);
        ParticipatingSiteDTO participatingSiteDTO = new ParticipatingSiteDTO();
        participatingSiteDTO.setIdentifier(IiConverter.convertToIi(1L));
        
        when(sut.getParticipatingSite(participatingSiteDTO.getIdentifier())).thenReturn(participatingSiteDTO);
        when(sut.getStudySiteDTO(any(Ii.class))).thenReturn(studySiteDTO);

        try {
        	sut.updateStudySiteParticipant(studySiteDTO, currentStatusDTO, contacts);
        	 fail();
        } catch (Exception e) {
            // expected
        }
    }
}

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

import static org.junit.Assert.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.convert.DocumentWorkflowStatusConverter;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InOrder;
/**
 * Test for the DocumentWorkflowStatusBeanLocal
 * 
 * @author Michael Visee
 */
public class DocumentWorkflowStatusServiceBeanTest extends AbstractHibernateTestCase {

    /**
     * Exception rule.
     */
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * Initialization method.
     */
    @Before
    public void init() {
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();
        TestSchema.addAbstractedWorkflowStatus(TestSchema.studyProtocolIds.get(0));
    }

    /**
     * Test the get method.
     * @throws PAException in case of error
     */
    @Test
    public void testGet() throws PAException {
        DocumentWorkflowStatusBeanLocal sut = new DocumentWorkflowStatusBeanLocal();
        Ii spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
        List<DocumentWorkflowStatusDTO> statusList = sut.getByStudyProtocol(spIi);
        assertEquals(1, statusList.size());
        DocumentWorkflowStatusDTO dto = sut.get(statusList.get(0).getIdentifier());
        assertEquals(IiConverter.convertToLong(statusList.get(0).getIdentifier()),
                     (IiConverter.convertToLong(dto.getIdentifier())));
    }

    /**
     * Test the create method in case of success.
     * @throws PAException in case of error
     */
    @Test
    public void testCreateSuccess() throws PAException {
        DocumentWorkflowStatusBeanLocal sut = mock(DocumentWorkflowStatusBeanLocal.class);
        DocumentWorkflowStatusDTO dto = new DocumentWorkflowStatusDTO();
        Ii spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
        dto.setStudyProtocolIdentifier(spIi);
        dto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ON_HOLD));
        doCallRealMethod().when(sut).create(dto);
        when(sut.getLatestStatus(spIi)).thenReturn(DocumentWorkflowStatusCode.SUBMITTED);
        DocumentWorkflowStatus dwsBo = new DocumentWorkflowStatusConverter().convertFromDtoToDomain(dto);
        when(sut.convertFromDtoToDomain(dto)).thenReturn(dwsBo);
        sut.create(dto);
        InOrder inOrder = inOrder(sut);
        inOrder.verify(sut).validationForCreation(dto);
        inOrder.verify(sut).getLatestStatus(spIi);
    }

    /**
     * Test the create method in case of consecutive statuses error.
     * @throws PAException in case of error
     */
    @Test
    public void testCreateError() throws PAException {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("Consecutive statuses must be different.");
        DocumentWorkflowStatusBeanLocal sut = mock(DocumentWorkflowStatusBeanLocal.class);
        DocumentWorkflowStatusDTO dto = new DocumentWorkflowStatusDTO();
        Ii spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
        dto.setStudyProtocolIdentifier(spIi);
        dto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ON_HOLD));
        doCallRealMethod().when(sut).create(dto);
        when(sut.getLatestStatus(spIi)).thenReturn(DocumentWorkflowStatusCode.ON_HOLD);
        sut.create(dto);
    }

    /**
     * Test the validationForCreation method with no data.
     * @throws PAException in case of error
     */
    @Test
    public void testValidationForCreationNoData() throws PAException {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("DocumentWorkflowStatusDTO object not provided.");
        DocumentWorkflowStatusBeanLocal sut = new DocumentWorkflowStatusBeanLocal();
        sut.validationForCreation(null);
    }

    /**
     * Test the validationForCreation method with an identifier.
     * @throws PAException in case of error
     */
    @Test
    public void testValidationForCreationWithId() throws PAException {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("Update method should be used to modify existing.  ");
        DocumentWorkflowStatusBeanLocal sut = new DocumentWorkflowStatusBeanLocal();
        DocumentWorkflowStatusDTO dto = new DocumentWorkflowStatusDTO();
        dto.setIdentifier(IiConverter.convertToDocumentWorkFlowStatusIi(1L));
        sut.validationForCreation(dto);

    }

    /**
     * Test the validationForCreation method without study protocol identifier
     * @throws PAException in case of error
     */
    @Test
    public void testValidationForCreationNoStudyProtocolId() throws PAException {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("Study Protocol is required.  ");
        DocumentWorkflowStatusBeanLocal sut = new DocumentWorkflowStatusBeanLocal();
        DocumentWorkflowStatusDTO dto = new DocumentWorkflowStatusDTO();
        sut.validationForCreation(dto);
    }

    /**
     * Test the validationForCreation method without status code.
     * @throws PAException in case of error
     */
    @Test
    public void testValidationForCreationNoStatus() throws PAException {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("Status Code is required.  ");
        DocumentWorkflowStatusBeanLocal sut = new DocumentWorkflowStatusBeanLocal();
        DocumentWorkflowStatusDTO dto = new DocumentWorkflowStatusDTO();
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        sut.validationForCreation(dto);
    }

    /**
     * Test the validationForCreation method with valid data.
     * @throws PAException in case of error
     */
    @Test
    public void testValidationForCreationSuccess() throws PAException {
        DocumentWorkflowStatusBeanLocal sut = new DocumentWorkflowStatusBeanLocal();
        DocumentWorkflowStatusDTO dto = new DocumentWorkflowStatusDTO();
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        dto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ON_HOLD));
        sut.validationForCreation(dto);
    }

    /**
     * Test the getLatestStatus method.
     * @throws PAException in case of error
     */
    @Test
    public void testGetLatestStatus() throws PAException {
        DocumentWorkflowStatusBeanLocal sut = mock(DocumentWorkflowStatusBeanLocal.class);
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        doCallRealMethod().when(sut).getLatestStatus(spIi);
        List<DocumentWorkflowStatusDTO> statuses = createStatusDtos();
        when(sut.getByStudyProtocol(spIi)).thenReturn(statuses);
        DocumentWorkflowStatusCode result = sut.getLatestStatus(spIi);
        assertEquals("Wrong status returned", DocumentWorkflowStatusCode.ON_HOLD, result);
    }

    /**
     * Test the getLatestOffholdStatus method.
     * @throws PAException in case of error
     */
    @Test
    public void testGetLatestOffholdStatus() throws PAException {
        DocumentWorkflowStatusBeanLocal sut = mock(DocumentWorkflowStatusBeanLocal.class);
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        doCallRealMethod().when(sut).getLatestOffholdStatus(spIi);
        List<DocumentWorkflowStatusDTO> statuses = createStatusDtos();
        when(sut.getByStudyProtocol(spIi)).thenReturn(statuses);
        DocumentWorkflowStatusDTO result = sut.getLatestOffholdStatus(spIi);
        assertEquals("Wrong status returned", statuses.get(2), result);
    }
    
    @Test
    public void testGetPreviousStatus() throws PAException {
        DocumentWorkflowStatusBeanLocal sut = mock(DocumentWorkflowStatusBeanLocal.class);
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        doCallRealMethod().when(sut).getPreviousStatus(spIi);
        List<DocumentWorkflowStatusDTO> statuses = createStatusDtos();
        when(sut.getByStudyProtocol(spIi)).thenReturn(statuses);
        DocumentWorkflowStatusDTO result = sut.getPreviousStatus(spIi);
        assertEquals("Wrong status returned", statuses.get(1), result);
        
        when(sut.getByStudyProtocol(spIi)).thenReturn(Arrays.asList(statuses.get(0)));
        result = sut.getPreviousStatus(spIi);
        assertNull(result);        
    }
    

    private List<DocumentWorkflowStatusDTO> createStatusDtos() {
        List<DocumentWorkflowStatusDTO> dtos = new ArrayList<DocumentWorkflowStatusDTO>();
        for (int i = 1; i <= 4; i++) {
            DocumentWorkflowStatusDTO dto = new DocumentWorkflowStatusDTO();
            Timestamp low = new Timestamp(new DateTime().minusHours(i).getMillis());
            dto.setStatusDateRange(IvlConverter.convertTs().convertToIvl(low, null));
            dto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ON_HOLD));
            dtos.add(dto);
        }
        dtos.get(2).setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ACCEPTED));
        dtos.get(3).setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.SUBMITTED));
        return dtos;
    }
    
    @Test
    public void deleteDWFStatustest() throws PAException {
        DocumentWorkflowStatusBeanLocal sut = new DocumentWorkflowStatusBeanLocal();
        Ii spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
        List<DocumentWorkflowStatusDTO> statusList = sut.getByStudyProtocol(spIi);
        DocumentWorkflowStatusDTO dto = new DocumentWorkflowStatusDTO();
        dto.setStudyProtocolIdentifier(spIi);
        dto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.REJECTED));
        sut.create(dto);
        statusList = sut.getByStudyProtocol(spIi);
        assertEquals(2, statusList.size()); // after create
        sut.deleteDWFStatus(DocumentWorkflowStatusCode.REJECTED, spIi);
        statusList = sut.getByStudyProtocol(spIi);
        assertEquals(1, statusList.size()); // after delete
        
    }

}

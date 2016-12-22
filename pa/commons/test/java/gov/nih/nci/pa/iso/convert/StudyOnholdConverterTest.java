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
package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;

import gov.nih.nci.pa.domain.StudyOnhold;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import java.sql.Timestamp;

import org.joda.time.DateTime;

/**
 * @author Michael Visee
 */
public class StudyOnholdConverterTest extends AbstractConverterTest<StudyOnholdConverter, StudyOnholdDTO, StudyOnhold> {

    private static final String REASON_TEXT = "reason";
    private static final String LOG_TEXT = "log";
    private static final OnholdReasonCode REASON_CODE = OnholdReasonCode.INVALID_GRANT;
    private static final Timestamp ON_HOLD_DATE = new Timestamp(new DateTime().getMillis());
    private static final Timestamp OFF_HOLD_DATE = new Timestamp(new DateTime().plusDays(1).getMillis());
    private static final DocumentWorkflowStatusCode STATUS_CODE = DocumentWorkflowStatusCode.SUBMITTED;

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyOnhold makeBo() {
        StudyOnhold bo = new StudyOnhold();
        bo.setId(1L);
        StudyProtocol sp = new StudyProtocol();
        sp.setId(2L);
        bo.setStudyProtocol(sp);
        bo.setOnholdReasonText(REASON_TEXT);
        bo.setOnholdReasonCode(REASON_CODE);
        bo.setOnholdDate(ON_HOLD_DATE);
        bo.setOffholdDate(OFF_HOLD_DATE);
        bo.setPreviousStatusCode(STATUS_CODE);
        bo.setProcessingLog(LOG_TEXT);
        return bo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyOnholdDTO makeDto() {
        StudyOnholdDTO dto = new StudyOnholdDTO();
        dto.setIdentifier(IiConverter.convertToStudyOnHoldIi(1L));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(2L));
        dto.setOnholdReasonText(StConverter.convertToSt(REASON_TEXT));
        dto.setOnholdReasonCode(CdConverter.convertToCd(REASON_CODE));
        dto.setOnholdDate(IvlConverter.convertTs().convertToIvl(ON_HOLD_DATE, OFF_HOLD_DATE));
        dto.setPreviousStatusCode(CdConverter.convertToCd(STATUS_CODE));
        dto.setProcessingLog(StConverter.convertToSt(LOG_TEXT));
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verifyBo(StudyOnhold bo) {
        assertEquals("Wrong identifier", 1L, bo.getId().longValue());
        assertEquals("Wrong study protocol identifier", 2L, bo.getStudyProtocol().getId().longValue());
        assertEquals("Wrong Reason Text", REASON_TEXT, bo.getOnholdReasonText());
        assertEquals("Wrong Reason Code", REASON_CODE, bo.getOnholdReasonCode());
        assertEquals("Wrong onhold date", ON_HOLD_DATE, bo.getOnholdDate());
        assertEquals("Wrong offhold date", OFF_HOLD_DATE, bo.getOffholdDate());
        assertEquals("Wrong previous status Code", STATUS_CODE, bo.getPreviousStatusCode());
        assertEquals("Wrong log text", LOG_TEXT, bo.getProcessingLog());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verifyDto(StudyOnholdDTO dto) {
        assertEquals("Wrong identifier", IiConverter.convertToStudyOnHoldIi(1L), dto.getIdentifier());
        assertEquals("Wrong study protocol identifier", IiConverter.convertToStudyProtocolIi(2L),
                     dto.getStudyProtocolIdentifier());
        assertEquals("Wrong Reason Text", REASON_TEXT, StConverter.convertToString(dto.getOnholdReasonText()));
        assertEquals("Wrong Log Text", LOG_TEXT, StConverter.convertToString(dto.getProcessingLog()));
        assertEquals("Wrong Reason Code", REASON_CODE.getCode(),
                     CdConverter.convertCdToString(dto.getOnholdReasonCode()));
        assertEquals("Wrong onhold date", ON_HOLD_DATE, IvlConverter.convertTs().convertLow(dto.getOnholdDate()));
        assertEquals("Wrong offhold date", OFF_HOLD_DATE, IvlConverter.convertTs().convertHigh(dto.getOnholdDate()));
        assertEquals("Wrong previous status Code", STATUS_CODE.getCode(),
                     CdConverter.convertCdToString(dto.getPreviousStatusCode()));
    }

}

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
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Parametric test for the AbstractCompletionServieBean.enforceRecruitmentStatus method
 * @author Michael Visee
 */
@RunWith(Parameterized.class)
public class AbstractionCompletionServiceBeanRecruitentStatusTest {
    private static final String[] COMMENTS = {
        "Select Participating Sites from Administrative Data menu.",
        "Select Participating Sites from Administrative Data menu.",
        "Select study start date."
        };
    
    private static final String[] DESCRIPTIONS = {
        "Data inconsistency: At least one location needs to be recruiting if the overall recruitment status is '%s'",
        "Data inconsistency. No site can recruit patients if the overall recruitment status is '%s'",
        "Data inconsistency. Study Start Date cannot be in the past if the overall recruitment status is '%s'"};
    
    private static final String[] TYPES = {"Error", "Warning", "Warning"};
    
    private StudyProtocolServiceLocal studyProtocolService = mock(StudyProtocolServiceLocal.class);
    private StudyOverallStatusServiceLocal studyRecruitmentStatusService = 
            mock(StudyOverallStatusServiceLocal.class);

    private RecruitmentStatusCode recruitmentStatus;
    private boolean studySiteRecruiting;
    private boolean isStartDatePast;
    private int[] expectedMessages;
    
    /**
     * Gets the parameterized test data.
     * 
     * @return The parameters of the test
     */
    @Parameters
    public static Collection<? extends Object> data() {
        Object[][] data = new Object[][]{{RecruitmentStatusCode.IN_REVIEW, false, false, new int[]{} },
                {RecruitmentStatusCode.APPROVED, false, false, new int[]{} },
                {RecruitmentStatusCode.ACTIVE, false, false, new int[]{0} },
                {RecruitmentStatusCode.ENROLLING_BY_INVITATION, false, false, new int[]{0} },
                {RecruitmentStatusCode.CLOSED_TO_ACCRUAL, false, false, new int[]{} },
                {RecruitmentStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION, false, false, new int[]{} },
                {RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL, false, false, new int[]{} },
                {RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION, false, false, new int[]{} },
                {RecruitmentStatusCode.WITHDRAWN, false, false, new int[]{} },
                {RecruitmentStatusCode.ADMINISTRATIVELY_COMPLETE, false, false, new int[]{} },
                {RecruitmentStatusCode.COMPLETED, false, false, new int[]{} },
                
                {RecruitmentStatusCode.IN_REVIEW, false, true, new int[]{2} },
                {RecruitmentStatusCode.APPROVED, false, true, new int[]{2} },
                {RecruitmentStatusCode.ACTIVE, false, true, new int[]{0} },
                {RecruitmentStatusCode.ENROLLING_BY_INVITATION, false, true, new int[]{0} },
                {RecruitmentStatusCode.CLOSED_TO_ACCRUAL, false, true, new int[]{} },
                {RecruitmentStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION, false, true, new int[]{} },
                {RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL, false, true, new int[]{} },
                {RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION, false, true, new int[]{} },
                {RecruitmentStatusCode.WITHDRAWN, false, true, new int[]{} },
                {RecruitmentStatusCode.ADMINISTRATIVELY_COMPLETE, false, true, new int[]{} },
                {RecruitmentStatusCode.COMPLETED, false, true, new int[]{} },
                
                {RecruitmentStatusCode.IN_REVIEW, true, false, new int[]{1} },
                {RecruitmentStatusCode.APPROVED, true, false, new int[]{1} },
                {RecruitmentStatusCode.ACTIVE, true, false, new int[]{} },
                {RecruitmentStatusCode.ENROLLING_BY_INVITATION, true, false, new int[]{} },
                {RecruitmentStatusCode.CLOSED_TO_ACCRUAL, true, false, new int[]{} },
                {RecruitmentStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION, true, false, new int[]{} },
                {RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL, true, false, new int[]{} },
                {RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION, true, false, new int[]{} },
                {RecruitmentStatusCode.WITHDRAWN, true, false, new int[]{} },
                {RecruitmentStatusCode.ADMINISTRATIVELY_COMPLETE, true, false, new int[]{} },
                {RecruitmentStatusCode.COMPLETED, true, false, new int[]{} },
                
                {RecruitmentStatusCode.IN_REVIEW, true, true, new int[]{2,1} },
                {RecruitmentStatusCode.APPROVED, true, true, new int[]{2,1} },
                {RecruitmentStatusCode.ACTIVE, true, true, new int[]{} },
                {RecruitmentStatusCode.ENROLLING_BY_INVITATION, true, true, new int[]{} },
                {RecruitmentStatusCode.CLOSED_TO_ACCRUAL, true, true, new int[]{} },
                {RecruitmentStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION, true, true, new int[]{} },
                {RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL, true, true, new int[]{} },
                {RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION, true, true, new int[]{} },
                {RecruitmentStatusCode.WITHDRAWN, true, true, new int[]{} },
                {RecruitmentStatusCode.ADMINISTRATIVELY_COMPLETE, true, true, new int[]{} },
                {RecruitmentStatusCode.COMPLETED, true, true, new int[]{} }};
        return Arrays.asList(data);
    }
    
    /**
     * Constructor.
     * @param recruitmentStatus The recruitmentStatus of the study
     * @param studySiteRecruiting true if a study site is recruiting
     * @param isStartDatePast true if the study date is in the past
     * @param expectedMessages The array of expected message indexes
     */
    public AbstractionCompletionServiceBeanRecruitentStatusTest(RecruitmentStatusCode recruitmentStatus,
            boolean studySiteRecruiting, boolean isStartDatePast, int[] expectedMessages) {
        this.recruitmentStatus = recruitmentStatus;
        this.studySiteRecruiting = studySiteRecruiting;
        this.isStartDatePast = isStartDatePast;
        this.expectedMessages = expectedMessages;
    }

    /**
     * test the enforceRecruitmentStatus method. 
     * @throws PAException if an error occurs
     */
    @Test
    public void testEnforceRecruitmentStatus() throws PAException {
        AbstractionCompletionServiceBean sut = createAbstractionCompletionServiceBeanMock();
        Ii spIi = IiConverter.convertToIi(1L);
        AbstractionMessageCollection messages = new AbstractionMessageCollection();
        doCallRealMethod().when(sut).enforceRecruitmentStatus(spIi, messages);
        when(sut.isStudySiteRecruiting(spIi)).thenReturn(studySiteRecruiting);
        StudyOverallStatusDTO studyRecruitmentStatusDTO = createStudyRecruitmentStatusDTO(recruitmentStatus);
        when(studyRecruitmentStatusService.getCurrentByStudyProtocol(spIi)).thenReturn(studyRecruitmentStatusDTO);
        StudyProtocolDTO studyProtocolDTO = createStudyProtocolDTO(isStartDatePast);
        when(studyProtocolService.getStudyProtocol(spIi)).thenReturn(studyProtocolDTO);

        sut.enforceRecruitmentStatus(spIi, messages);
        List<AbstractionCompletionDTO> result = messages.getMessages();

        assertEquals("Wrong result size", expectedMessages.length, result.size());
        for (int i = 0; i < result.size(); i++) {
            assertMessage(result.get(i), recruitmentStatus, expectedMessages[i]);
        }
        verify(studyRecruitmentStatusService).getCurrentByStudyProtocol(spIi);
        verify(sut).isStudySiteRecruiting(spIi);
        verify(studyProtocolService).getStudyProtocol(spIi);
    }

    private AbstractionCompletionServiceBean createAbstractionCompletionServiceBeanMock() {
        AbstractionCompletionServiceBean service = mock(AbstractionCompletionServiceBean.class);
        doCallRealMethod().when(service).setStudyProtocolService(
                studyProtocolService);
        service.setStudyProtocolService(studyProtocolService);
        doCallRealMethod().when(service).setStudyOverallStatusService(
                studyRecruitmentStatusService);
        service.setStudyOverallStatusService(studyRecruitmentStatusService);
        return service;
    }

    private StudyOverallStatusDTO createStudyRecruitmentStatusDTO(
            RecruitmentStatusCode recruitmentStatus) {
        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode
                .getByRecruitmentStatus(recruitmentStatus)));
        return dto;
    }

    private StudyProtocolDTO createStudyProtocolDTO(boolean isStartDatePast) {
        StudyProtocolDTO dto = new StudyProtocolDTO();
        DateTime now = new DateTime();
        DateTime startDate = (isStartDatePast) ? now.minusDays(1) : now.plusDays(1);
        dto.setStartDate(TsConverter.convertToTs(startDate.toDate()));
        return dto;
    }

    private void assertMessage(AbstractionCompletionDTO message, RecruitmentStatusCode recruitmentStatus,
            int expectedIndex) {
        assertEquals("Wrong type of message", TYPES[expectedIndex], message.getErrorType());
        assertEquals("Wrong comment", COMMENTS[expectedIndex], message.getComment());
        String expectedDescription = String.format(DESCRIPTIONS[expectedIndex], recruitmentStatus.getCode());
        assertEquals("Wrong description", expectedDescription, message.getErrorDescription());
    }
}

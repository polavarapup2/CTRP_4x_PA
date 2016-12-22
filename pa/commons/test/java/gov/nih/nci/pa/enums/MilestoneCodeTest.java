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
package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE;
import static gov.nih.nci.pa.enums.MilestoneCode.ADMINISTRATIVE_QC_COMPLETE;
import static gov.nih.nci.pa.enums.MilestoneCode.INITIAL_ABSTRACTION_VERIFY;
import static gov.nih.nci.pa.enums.MilestoneCode.LATE_REJECTION_DATE;
import static gov.nih.nci.pa.enums.MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION;
import static gov.nih.nci.pa.enums.MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE;
import static gov.nih.nci.pa.enums.MilestoneCode.SCIENTIFIC_QC_COMPLETE;
import static gov.nih.nci.pa.enums.MilestoneCode.SUBMISSION_ACCEPTED;
import static gov.nih.nci.pa.enums.MilestoneCode.SUBMISSION_REACTIVATED;
import static gov.nih.nci.pa.enums.MilestoneCode.SUBMISSION_RECEIVED;
import static gov.nih.nci.pa.enums.MilestoneCode.SUBMISSION_REJECTED;
import static gov.nih.nci.pa.enums.MilestoneCode.SUBMISSION_TERMINATED;
import static gov.nih.nci.pa.enums.MilestoneCode.TRIAL_SUMMARY_FEEDBACK;
import static gov.nih.nci.pa.enums.MilestoneCode.TRIAL_SUMMARY_REPORT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
/**
 * Tests for the MilestoneCode enum.
 * 
 * @author Michael Visee
 */
public class MilestoneCodeTest {
    
    /**
     * test the getDisplayNames method.
     */
    @Test
    public void testGetDisplayNames() {
        String[] result = MilestoneCode.getDisplayNames();
        assertNotNull("No result returned", result);
        MilestoneCode[] values = MilestoneCode.values();
        assertEquals("Result has the wrong size", values.length, result.length);
        for (int i = 0; i < result.length; i++) {
            assertEquals(values[i].getCode(), result[i]);
        }
    }
    
    /**
     * test the isAboveTrialSummaryReport method.
     */
    @Test
    public void testIsAboveTrialSummaryReport() {
       Set<MilestoneCode> trsAndAbove = EnumSet.of(MilestoneCode.TRIAL_SUMMARY_REPORT,
                                                   MilestoneCode.TRIAL_SUMMARY_FEEDBACK,
                                                   MilestoneCode.INITIAL_ABSTRACTION_VERIFY,
                                                   MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION);
       for (MilestoneCode milestoneCode : MilestoneCode.values()) {
           assertEquals(trsAndAbove.contains(milestoneCode), MilestoneCode.isAboveTrialSummaryReport(milestoneCode));
       }
    }
    
    /**
     * test the getCodes method.
     */
    @Test
    public void testGetCodes() {
        Set<MilestoneCode> milestones = EnumSet.allOf(MilestoneCode.class);
        milestones.remove(MilestoneCode.LATE_REJECTION_DATE);
        List<String> result = MilestoneCode.getCodes(milestones);
        assertNotNull("No result returned", result);
        MilestoneCode[] values = MilestoneCode.values();
        assertEquals("Result has the wrong size", values.length - 1, result.size());
        for (int i = 0; i < result.size(); i++) {
            assertEquals(values[i].getCode(), result.get(i));
        }
    }
    
    @Test
    public void testGetMilestoneCodesForReporting() {
        List<MilestoneCode> list = MilestoneCode
                .getMilestoneCodesForReporting();
        List<MilestoneCode> expectedList = Arrays.asList(SUBMISSION_RECEIVED,
                SUBMISSION_ACCEPTED, SUBMISSION_REJECTED,
                SUBMISSION_TERMINATED, SUBMISSION_REACTIVATED,
                ADMINISTRATIVE_PROCESSING_COMPLETED_DATE,
                ADMINISTRATIVE_QC_COMPLETE,
                SCIENTIFIC_PROCESSING_COMPLETED_DATE, SCIENTIFIC_QC_COMPLETE,
                TRIAL_SUMMARY_REPORT, TRIAL_SUMMARY_FEEDBACK,
                INITIAL_ABSTRACTION_VERIFY, ONGOING_ABSTRACTION_VERIFICATION,
                LATE_REJECTION_DATE);
        assertTrue(CollectionUtils.isEqualCollection(list, expectedList));
    }

    @Test
    public void testValidDwfStatus() {
        MilestoneCode  code = MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE;
        assertTrue(code.isValidDwfStatus(DocumentWorkflowStatusCode.ABSTRACTED));
    }
    
    @Test 
    public void testAdminMilestone() {
        assertTrue(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE.isAdminMilestone());
    }
    
    @Test 
    public void testScientificMilestone() {
        assertTrue(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE.isScientificMilestone());
    }
    
    @Test
    public void testGetValidDwfStatuses() {
        assertTrue(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE.getValidDwfStatuses().size( )> 0);
    }
}

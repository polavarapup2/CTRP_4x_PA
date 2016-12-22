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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.domain.StudySiteContact;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class ParticipatingSiteConverterTest extends
        AbstractConverterTest<ParticipatingSiteConverter, ParticipatingSiteDTO, StudySite> {
    private final Date today = DateUtils.truncate(new Date(), Calendar.DATE);

    @Override
    public StudySite makeBo() {
        StudySite studySite = new StudySite();
        studySite.setId(ID);
        studySite.setStudyProtocol(getStudyProtocol());
        studySite.setLocalStudyProtocolIdentifier("ABE");
        studySite.setReviewBoardApprovalDate(new Timestamp(today.getTime()));
        studySite.setReviewBoardApprovalNumber("1");
        studySite.setReviewBoardApprovalStatusCode(ReviewBoardApprovalStatusCode.SUBMITTED_PENDING);
        studySite.setReviewBoardOrganizationalAffiliation("TEST");
        studySite.setProgramCodeText("Testing");
        studySite.setAccrualDateRangeLow(new Timestamp(today.getTime()));
        studySite.setAccrualDateRangeHigh(new Timestamp(today.getTime()));
        studySite.getStudySiteContacts().add(makeStudySiteContact());
        
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setIdentifier("123");
        Organization org = new Organization();
        org.setIdentifier("456");
        hcf.setOrganization(org);
        studySite.setHealthCareFacility(hcf);

        Date yesterday = DateUtils.addDays(today, -1);
        Date twoDaysAgo = DateUtils.addDays(today, -2);

        StudySiteAccrualStatus ssaStatus = new StudySiteAccrualStatus();
        ssaStatus.setStatusCode(RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL);
        ssaStatus.setStatusDate(new Timestamp(today.getTime()));
        ssaStatus.setStudySite(studySite);
        ssaStatus.setId(1L);
        studySite.getStudySiteAccrualStatuses().add(ssaStatus);

        StudySiteAccrualStatus ssaStatus2 = new StudySiteAccrualStatus();
        ssaStatus2.setStatusCode(RecruitmentStatusCode.ACTIVE);
        ssaStatus2.setStatusDate(new Timestamp(yesterday.getTime()));
        ssaStatus2.setStudySite(studySite);
        ssaStatus2.setId(2L);
        studySite.getStudySiteAccrualStatuses().add(ssaStatus2);

        StudySiteAccrualStatus ssaStatus3 = new StudySiteAccrualStatus();
        ssaStatus3.setStatusCode(RecruitmentStatusCode.CLOSED_TO_ACCRUAL);
        ssaStatus3.setStatusDate(new Timestamp(twoDaysAgo.getTime()));
        ssaStatus3.setStudySite(studySite);
        ssaStatus3.setId(3L);
        studySite.getStudySiteAccrualStatuses().add(ssaStatus3);

        return studySite;
    }

    private StudySiteContact makeStudySiteContact() {
        StudySiteContact ssc = new StudySiteContact();
        ssc.setAddressLine("Address 1");
        ssc.setCity("City");
        ssc.setPhone("111");
        ssc.setEmail("test@example.com");
        ssc.setDeliveryAddressLine("Del. Address 1");
        ssc.setPostalCode("ZZZZZ");
        ssc.setPrimaryIndicator(true);
        ssc.setRoleCode(StudySiteContactRoleCode.SUBMITTER);
        ssc.setState("ZZ");
        ssc.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ssc.setStatusDateRangeLow(ISOUtil.dateStringToTimestamp("1/15/2008"));
        ssc.setStudyProtocol(getStudyProtocol());
        return ssc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingSiteDTO makeDto() {
        ParticipatingSiteDTO dto = new ParticipatingSiteDTO();
        dto.setIdentifier(IiConverter.convertToIi(ID));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(STUDY_PROTOCOL_ID));
        dto.setLocalStudyProtocolIdentifier(StConverter.convertToSt("ABE"));
        dto.setReviewBoardApprovalDate(TsConverter.convertToTs(today));
        dto.setReviewBoardApprovalNumber(StConverter.convertToSt("1"));
        dto.setReviewBoardApprovalStatusCode(CdConverter
            .convertStringToCd(ReviewBoardApprovalStatusCode.SUBMITTED_PENDING.getCode()));

        dto.setReviewBoardOrganizationalAffiliation(StConverter.convertToSt("TEST"));
        dto.setProgramCodeText(StConverter.convertToSt("Testing"));
        dto.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(today, today));
        dto.setStudySiteContacts(new ArrayList<StudySiteContactDTO>());
        try {
            dto.getStudySiteContacts().add(new StudySiteContactConverter()
                                               .convertFromDomainToDto(makeStudySiteContact()));
        } catch (PAException e) {
            throw new IllegalStateException("An unexpected exception occurred", e);
        }

        StudySiteAccrualStatusDTO ssasDto = new StudySiteAccrualStatusDTO();
        ssasDto.setStatusCode(CdConverter.convertStringToCd(RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getCode()));
        ssasDto.setStatusDate(TsConverter.convertToTs(today));

        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verifyBo(StudySite bo) {
        assertEquals(ID, bo.getId());
        assertEquals(STUDY_PROTOCOL_ID, bo.getStudyProtocol().getId());
        assertEquals("ABE", bo.getLocalStudyProtocolIdentifier());
        assertEquals(today, bo.getReviewBoardApprovalDate());
        assertEquals("1", bo.getReviewBoardApprovalNumber());
        assertEquals(ReviewBoardApprovalStatusCode.SUBMITTED_PENDING, bo.getReviewBoardApprovalStatusCode());
        assertEquals("TEST", bo.getReviewBoardOrganizationalAffiliation());
        assertEquals("Testing", bo.getProgramCodeText());
        assertEquals(today, bo.getAccrualDateRangeHigh());
        assertEquals(today, bo.getAccrualDateRangeLow());

        assertTrue(bo.getStudySiteContacts().isEmpty());
        assertTrue(bo.getStudySiteAccrualStatuses().isEmpty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verifyBoUpdate(StudySite bo) {
        assertEquals(ID, bo.getId());
        assertEquals(STUDY_PROTOCOL_ID, bo.getStudyProtocol().getId());
        assertEquals("ABE", bo.getLocalStudyProtocolIdentifier());
        assertEquals(today, bo.getReviewBoardApprovalDate());
        assertEquals("1", bo.getReviewBoardApprovalNumber());
        assertEquals(ReviewBoardApprovalStatusCode.SUBMITTED_PENDING, bo.getReviewBoardApprovalStatusCode());
        assertEquals("TEST", bo.getReviewBoardOrganizationalAffiliation());
        assertEquals("Testing", bo.getProgramCodeText());
        assertEquals(today, bo.getAccrualDateRangeHigh());
        assertEquals(today, bo.getAccrualDateRangeLow());

        assertEquals(1, bo.getStudySiteContacts().size());
        assertEquals(3, bo.getStudySiteAccrualStatuses().size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verifyDto(ParticipatingSiteDTO dto) {
        assertEquals(ID, IiConverter.convertToLong(dto.getIdentifier()));
        assertEquals(STUDY_PROTOCOL_ID, IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
        assertEquals("ABE", dto.getLocalStudyProtocolIdentifier().getValue());
        assertEquals(today, dto.getReviewBoardApprovalDate().getValue());
        assertEquals("1", dto.getReviewBoardApprovalNumber().getValue());
        assertEquals(ReviewBoardApprovalStatusCode.SUBMITTED_PENDING.getCode(), dto.getReviewBoardApprovalStatusCode()
            .getCode());
        assertEquals("TEST", dto.getReviewBoardOrganizationalAffiliation().getValue());
        assertEquals("Testing", dto.getProgramCodeText().getValue());
        assertEquals(today, dto.getAccrualDateRange().getHigh().getValue());
        assertEquals(today, dto.getAccrualDateRange().getLow().getValue());
        assertFalse(dto.getStudySiteContacts().isEmpty());
        assertEquals(RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getCode(), dto.getStudySiteAccrualStatus()
            .getStatusCode().getCode());
        assertEquals(today, dto.getStudySiteAccrualStatus().getStatusDate().getValue());
        assertEquals("456", dto.getSiteOrgPoId());
    }

}

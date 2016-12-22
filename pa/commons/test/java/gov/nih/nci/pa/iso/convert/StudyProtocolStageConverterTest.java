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
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.domain.StudyProtocolStage;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolStageDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author mshestopalov
 */
public class StudyProtocolStageConverterTest extends
        AbstractConverterTest<StudyProtocolStageConverter, StudyProtocolStageDTO, StudyProtocolStage> {
    private final Timestamp now = new Timestamp(new Date().getTime());

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyProtocolStage makeBo() {
        StudyProtocolStage bo = new StudyProtocolStage();
        bo.setDataMonitoringCommitteeAppointedIndicator(Boolean.TRUE);
        bo.setDelayedpostingIndicator(Boolean.TRUE);
        bo.setFdaRegulatedIndicator(Boolean.TRUE);
        Set<Ii> studySecondaryIdentifiers = new HashSet<Ii>();
        Ii spSecId = new Ii();
        spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        spSecId.setExtension("NCI-2009-00001");
        studySecondaryIdentifiers.add(spSecId);
        bo.setOtherIdentifiers(studySecondaryIdentifiers);
        bo.setOfficialTitle("Cancer for kids");
        bo.setPhaseCode(PhaseCode.I);
        bo.setPhaseAdditionalQualifierCode(PhaseAdditionalQualifierCode.PILOT);
        //bo.setPrimaryPurposeCode(PrimaryPurposeCode.PREVENTION);
        bo.setPrimaryPurposeAdditionalQualifierCode(PrimaryPurposeAdditionalQualifierCode.ANCILLARY);
        bo.setPrimaryPurposeOtherText("primaryPurposeOtherText");
        bo.setSection801Indicator(Boolean.TRUE);
        User u = new User();
        u.setLoginName("testUser");
        bo.setDateLastUpdated(now);
        bo.setUserLastUpdated(u);
        bo.setDateLastCreated(now);
        bo.setUserLastCreated(u);
        bo.setProprietaryTrialIndicator(Boolean.FALSE);
        bo.setCtgovXmlRequiredIndicator(Boolean.TRUE);
        bo.setProgramCodeText("program code");
        StudyProtocolDates dates = bo.getDates();
        dates.setStartDate(now);
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        dates.setPrimaryCompletionDate(now);
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setCompletionDate(now);
        dates.setCompletionDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        bo.setNciGrant(Boolean.TRUE);
        bo.setAccrualDiseaseCodeSystem("SDC");
        return bo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyProtocolStageDTO makeDto() {
        StudyProtocolStageDTO dto = new StudyProtocolStageDTO();
        final Bl trueBl = BlConverter.convertToBl(Boolean.TRUE);
        dto.setDataMonitoringCommitteeAppointedIndicator(trueBl);
        dto.setDelayedpostingIndicator(trueBl);
        dto.setFdaRegulatedIndicator(trueBl);
        List<Ii> studySecondaryIdentifiers = new ArrayList<Ii>();
        Ii spSecId = new Ii();
        spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        spSecId.setExtension("NCI-2009-00001");
        studySecondaryIdentifiers.add(spSecId);
        dto.setSecondaryIdentifierList(studySecondaryIdentifiers);
        dto.setOfficialTitle(StConverter.convertToSt("Cancer for kids"));
        dto.setPhaseCode(CdConverter.convertToCd(PhaseCode.I));
        dto.setPhaseAdditionalQualifierCode(CdConverter.convertToCd(PhaseAdditionalQualifierCode.PILOT));
        //dto.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.PREVENTION));
        dto.setPrimaryPurposeAdditionalQualifierCode(CdConverter
            .convertToCd(PrimaryPurposeAdditionalQualifierCode.ANCILLARY));
        dto.setPrimaryPurposeOtherText(StConverter.convertToSt("primaryPurposeOtherText"));
        dto.setSection801Indicator(trueBl);
        dto.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.FALSE));
        dto.setCtgovXmlRequiredIndicator(trueBl);
        dto.setProgramCodeText(StConverter.convertToSt("program code"));
        Ts nowTs = TsConverter.convertToTs(now);
        dto.setStartDate(nowTs);
        dto.setStartDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.ANTICIPATED));
        dto.setPrimaryCompletionDate(nowTs);
        dto.setPrimaryCompletionDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.ACTUAL));
        dto.setCompletionDate(nowTs);
        dto.setCompletionDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.ACTUAL));
        dto.setNciGrant(BlConverter.convertToBl(true));
        dto.setAccrualDiseaseCodeSystem(StConverter.convertToSt("SDC"));
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verifyBo(StudyProtocolStage bo) {
        assertTrue(bo.getDataMonitoringCommitteeAppointedIndicator());
        assertTrue(bo.getDelayedpostingIndicator());
        assertTrue(bo.getFdaRegulatedIndicator());
        assertEquals(1, bo.getOtherIdentifiers().size());
        Ii otherId = bo.getOtherIdentifiers().iterator().next();
        assertEquals("NCI-2009-00001", otherId.getExtension());
        assertEquals("2.16.840.1.113883.3.26.4.3", otherId.getRoot());
        assertEquals("Cancer for kids", bo.getOfficialTitle());
        assertEquals(PhaseCode.I, bo.getPhaseCode());
        assertEquals(PhaseAdditionalQualifierCode.PILOT, bo.getPhaseAdditionalQualifierCode());
        //assertEquals(PrimaryPurposeCode.PREVENTION, bo.getPrimaryPurposeCode());
        assertEquals(PrimaryPurposeAdditionalQualifierCode.ANCILLARY, bo.getPrimaryPurposeAdditionalQualifierCode());
        assertEquals("primaryPurposeOtherText", bo.getPrimaryPurposeOtherText());
        assertTrue(bo.getSection801Indicator());
        assertFalse(bo.getProprietaryTrialIndicator());
        assertTrue(bo.getCtgovXmlRequiredIndicator());
        assertEquals("program code", bo.getProgramCodeText());
        StudyProtocolDates dates = bo.getDates();
        assertEquals(now, dates.getStartDate());
        assertEquals(ActualAnticipatedTypeCode.ANTICIPATED, dates.getStartDateTypeCode());
        assertEquals(now, dates.getPrimaryCompletionDate());
        assertEquals(ActualAnticipatedTypeCode.ACTUAL, dates.getPrimaryCompletionDateTypeCode());
        assertTrue(bo.getNciGrant());
        assertEquals("SDC", bo.getAccrualDiseaseCodeSystem());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verifyDto(StudyProtocolStageDTO dto) {
        assertTrue(dto.getDataMonitoringCommitteeAppointedIndicator().getValue());
        assertTrue(dto.getDelayedpostingIndicator().getValue());
        assertTrue(dto.getFdaRegulatedIndicator().getValue());
        assertEquals(1, dto.getSecondaryIdentifierList().size());
        assertEquals("NCI-2009-00001", dto.getSecondaryIdentifierList().get(0).getExtension());
        assertEquals("2.16.840.1.113883.3.26.4.3", dto.getSecondaryIdentifierList().get(0).getRoot());
        assertEquals("Cancer for kids", dto.getOfficialTitle().getValue());
        assertEquals(PhaseCode.I.getCode(), dto.getPhaseCode().getCode());
        assertEquals(PhaseAdditionalQualifierCode.PILOT.getCode(), dto.getPhaseAdditionalQualifierCode().getCode());
        //assertEquals(PrimaryPurposeCode.PREVENTION.getCode(), dto.getPrimaryPurposeCode().getCode());
        assertEquals(PrimaryPurposeAdditionalQualifierCode.ANCILLARY.getCode(), dto
            .getPrimaryPurposeAdditionalQualifierCode().getCode());
        assertEquals("primaryPurposeOtherText", dto.getPrimaryPurposeOtherText().getValue());
        assertEquals(now, dto.getPrimaryCompletionDate().getValue());
        assertEquals(ActualAnticipatedTypeCode.ACTUAL.getCode(), dto.getPrimaryCompletionDateTypeCode().getCode());
        assertTrue(dto.getSection801Indicator().getValue());
        assertEquals(now, dto.getStartDate().getValue());
        assertEquals(ActualAnticipatedTypeCode.ANTICIPATED.getCode(), dto.getStartDateTypeCode().getCode());
        assertFalse(dto.getProprietaryTrialIndicator().getValue());
        assertTrue(dto.getCtgovXmlRequiredIndicator().getValue());
        assertEquals("program code", dto.getProgramCodeText().getValue());
        assertTrue(dto.getNciGrant().getValue());
        assertEquals("SDC", dto.getAccrualDiseaseCodeSystem().getValue());
    }

}

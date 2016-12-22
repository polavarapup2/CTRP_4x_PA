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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.AbstractStudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.AmendmentReasonCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.iso.dto.AbstractStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * test class for StudyProtocol converter.
 * @author NAmiruddin
 */
public class BaseStudyProtocolConverterTest {
    // AbstractStudyProtocolConverter doesn't extend AbstractConverter, so we can't use the AbstractConverterTest as a
    // base for this test

    /**
     * Test the convertFromDomainToDTO in the general case.
     */
    @Test
    public void convertFromDomainToDTOTest() {
        StudyProtocol sp = createStudyProtocol();
        User user = createUser();
        sp.setUserLastCreated(user);
        AbstractStudyProtocolDTO spDTO = new StudyProtocolDTO();
        AbstractStudyProtocolConverter.convertFromDomainToDTO(sp, spDTO);
        assertStudyProtocol(sp, spDTO);
        assertStudyProtocolDates(sp, spDTO);
        assertEquals("Wrong user name", user.getLoginName(), spDTO.getUserLastCreated().getValue());
    }
    
    /**
     * Test the convertFromDomainToDTO for null primaryCompletionDate and null completionDate.
     */
    @Test
    public void convertFromDomainToDTOTestNullCompletionDates() {
        StudyProtocol sp = createStudyProtocol();
        StudyProtocolDates dates = sp.getDates();
        dates.setPrimaryCompletionDate(null);
        dates.setCompletionDate(null);
        AbstractStudyProtocolDTO spDTO = new StudyProtocolDTO();
        AbstractStudyProtocolConverter.convertFromDomainToDTO(sp, spDTO);
        assertStudyProtocol(sp, spDTO);
        assertEquals(dates.getStartDate(), TsConverter.convertToTimestamp(spDTO.getStartDate()));
        assertEquals(dates.getStartDateTypeCode().getCode(), spDTO.getStartDateTypeCode().getCode());
        assertNotNull(spDTO.getPrimaryCompletionDate());
        assertEquals(NullFlavor.UNK, spDTO.getPrimaryCompletionDate().getNullFlavor());
        assertEquals(dates.getPrimaryCompletionDateTypeCode().getCode(), spDTO.getPrimaryCompletionDateTypeCode()
            .getCode());
        assertNotNull(spDTO.getCompletionDate());
        assertEquals(NullFlavor.UNK, spDTO.getCompletionDate().getNullFlavor());
        assertEquals(dates.getCompletionDateTypeCode().getCode(), spDTO.getCompletionDateTypeCode().getCode());
    }

    /**
     * Test the convertFromDTOToDomain method in the general case.
     */
    @Test
    public void convertFromDtoToDomainTest() {
        StudyProtocol sp = createStudyProtocol();
        User user = createUser();
        sp.setUserLastCreated(user);
        AbstractStudyProtocolDTO spDTO = new StudyProtocolDTO();
        AbstractStudyProtocolConverter.convertFromDomainToDTO(sp, spDTO);
        sp = new StudyProtocol();
        AbstractStudyProtocolConverter.convertFromDTOToDomain(spDTO, sp);
        assertStudyProtocol(sp, spDTO);
        assertStudyProtocolDates(sp, spDTO);
        assertEquals("Wrong user name", user.getLoginName(), spDTO.getUserLastCreated().getValue());
    }
    
    /**
     * Test the convertFromDTOToDomain method for null primaryCompletionDate and null completionDate.
     */
    @Test
    public void convertFromDtoToDomainTestNullCompletionDates() {
        StudyProtocol sp = createStudyProtocol();
        User user = createUser();
        sp.setUserLastCreated(user);
        AbstractStudyProtocolDTO spDTO = new StudyProtocolDTO();
        AbstractStudyProtocolConverter.convertFromDomainToDTO(sp, spDTO);
        Ts unknownTs = new Ts();
        unknownTs.setNullFlavor(NullFlavor.UNK);
        spDTO.setPrimaryCompletionDate(unknownTs);
        spDTO.setCompletionDate(unknownTs);
        sp = new StudyProtocol();
        AbstractStudyProtocolConverter.convertFromDTOToDomain(spDTO, sp);
        assertStudyProtocol(sp, spDTO);
        assertNull(sp.getDates().getPrimaryCompletionDate());
        assertNull(sp.getDates().getCompletionDate());
        assertEquals("Wrong user name", user.getLoginName(), spDTO.getUserLastCreated().getValue());
    }


    private StudyProtocol createStudyProtocol() {
        Timestamp now = new Timestamp((new java.util.Date()).getTime());
        StudyProtocol sp = new StudyProtocol();
        sp.setAcronym("Acronym .....");
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        sp.setDataMonitoringCommitteeAppointedIndicator(Boolean.TRUE);
        sp.setDelayedpostingIndicator(Boolean.TRUE);
        sp.setExpandedAccessIndicator(Boolean.TRUE);
        sp.setFdaRegulatedIndicator(Boolean.TRUE);

        Ii spNciId = new Ii();
        spNciId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        spNciId.setExtension("NCI-2009-00001");
        Set<Ii> studySecondaryIdentifiers =  new HashSet<Ii>();
        studySecondaryIdentifiers.add(spNciId);
        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setKeywordText("keywordText");
        sp.setOfficialTitle("Cancer for kids");
        sp.setPhaseCode(PhaseCode.I);
        sp.setPhaseAdditionalQualifierCode(PhaseAdditionalQualifierCode.PILOT);
        //sp.setPrimaryPurposeCode(PrimaryPurposeCode.PREVENTION);
        sp.setPrimaryPurposeAdditionalQualifierCode(PrimaryPurposeAdditionalQualifierCode.ANCILLARY);
        sp.setPrimaryPurposeOtherText("primaryPurposeOtherText");
        
        sp.setPublicDescription("publicDescription");
        sp.setPublicTitle("publicTitle");
        sp.setRecordVerificationDate(now);
        sp.setScientificDescription("scientificDescription");
        sp.setSection801Indicator(Boolean.TRUE);
        
        sp.setDateLastUpdated(now);
        sp.setDateLastCreated(now);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setAmendmentReasonCode(AmendmentReasonCode.BOTH);
        sp.setStatusDate(now);
        sp.setAmendmentDate(now);
        sp.setAmendmentNumber("amendmentNumber");
        sp.setSubmissionNumber(2);
        sp.setProprietaryTrialIndicator(Boolean.FALSE);
        sp.setCtgovXmlRequiredIndicator(Boolean.TRUE);
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(now);
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        dates.setPrimaryCompletionDate(now);
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setCompletionDate(now);
        dates.setCompletionDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        return sp;
    }
    
    private User createUser() {
        User user = new User();
        user.setLoginName("Abstractor: " + new Date());
        user.setFirstName("Joe");
        user.setLastName("Smith");
        user.setUpdateDate(new Date());
        return user;
    }

    /**
     * Compare the abastractStudyProtocol fields but the dates.
     * @param sp sp
     * @param spDTO spDTO
     */
    public void assertStudyProtocol(AbstractStudyProtocol sp, AbstractStudyProtocolDTO spDTO) {
        assertEquals(sp.getDataMonitoringCommitteeAppointedIndicator(), spDTO
            .getDataMonitoringCommitteeAppointedIndicator().getValue());
        assertEquals(sp.getDelayedpostingIndicator(), spDTO.getDelayedpostingIndicator().getValue());
        assertEquals(sp.getFdaRegulatedIndicator(), spDTO.getFdaRegulatedIndicator().getValue());
        assertEquals(sp.getOfficialTitle(), spDTO.getOfficialTitle().getValue());
        assertEquals(sp.getPhaseCode().getCode(), spDTO.getPhaseCode().getCode());
        assertEquals(sp.getPhaseAdditionalQualifierCode().getCode(), spDTO.getPhaseAdditionalQualifierCode().getCode());

        //assertEquals(sp.getPrimaryPurposeCode().getCode(), spDTO.getPrimaryPurposeCode().getCode());
        assertEquals(sp.getPrimaryPurposeAdditionalQualifierCode().getCode(), spDTO
            .getPrimaryPurposeAdditionalQualifierCode().getCode());
        assertEquals(sp.getPrimaryPurposeOtherText(), spDTO.getPrimaryPurposeOtherText().getValue());
        assertEquals(sp.getSection801Indicator(), spDTO.getSection801Indicator().getValue());
       
    }
    
    /**
     * Compare the abastractStudyProtocol dates.
     * @param sp sp
     * @param spDTO spDTO
     */
    public void assertStudyProtocolDates(AbstractStudyProtocol sp, AbstractStudyProtocolDTO spDTO) {
        StudyProtocolDates dates = sp.getDates();
        assertEquals(dates.getStartDate(), TsConverter.convertToTimestamp(spDTO.getStartDate()));
        assertEquals(dates.getStartDateTypeCode().getCode(), spDTO.getStartDateTypeCode().getCode());
        assertEquals(dates.getPrimaryCompletionDate(), TsConverter.convertToTimestamp(spDTO.getPrimaryCompletionDate()));
        assertEquals(dates.getPrimaryCompletionDateTypeCode().getCode(), spDTO.getPrimaryCompletionDateTypeCode()
            .getCode());
        assertEquals(dates.getCompletionDate(), TsConverter.convertToTimestamp(spDTO.getCompletionDate()));
        assertEquals(dates.getCompletionDateTypeCode().getCode(), spDTO.getCompletionDateTypeCode().getCode());
    }
}

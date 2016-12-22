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
package gov.nih.nci.registry.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyFundingStage;
import gov.nih.nci.pa.domain.StudyIndIdeStage;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.domain.StudyProtocolStage;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.convert.StudyFundingStageConverter;
import gov.nih.nci.pa.iso.convert.StudyIndIdeStageConverter;
import gov.nih.nci.pa.iso.convert.StudyProtocolStageConverter;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyFundingStageDTO;
import gov.nih.nci.pa.iso.dto.StudyIndIdeStageDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolStageDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolStageServiceLocal;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;

/**
 * @author Vrushali
 *
 */
public class MockStudyProtocolStageService extends AbstractBaseSearchBean<StudyProtocolStage> implements
        StudyProtocolStageServiceLocal {
    static List<StudyProtocolStage> list;
    static List<StudyFundingStage> studyFundingList;
    static List<StudyIndIdeStage> studyIndIdeList;
    static {
        list = new ArrayList<StudyProtocolStage>();
        StudyProtocolStage sp = new StudyProtocolStage();
        sp.setId(1L);
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setStartDate(PAUtil.dateStringToTimestamp("01/20/2008"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("01/20/2010"));
        sp.setOfficialTitle("officialTitle");
        sp.setLeadOrganizationIdentifier("1");
        sp.setLocalProtocolIdentifier("localProtocolIdentifier");
        sp.setPhaseCode(PhaseCode.I_II);
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.PREVENTION);
        sp.setTrialStatusCode(StudyStatusCode.ACTIVE);
        sp.setPiIdentifier("2");
        sp.setSponsorIdentifier("1");
        sp.setSummaryFourFundingCategoryCode(SummaryFourFundingCategoryCode.NATIONAL);
        sp.getSummaryFourOrgIdentifiers().add("1");
        sp.setOversightAuthorityCountryId("1");
        sp.setOversightAuthorityOrgId("1");
        list.add(sp);
        sp = new StudyProtocolStage();
        sp.setId(2L);
        dates = sp.getDates();
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setStartDate(PAUtil.dateStringToTimestamp("01/20/2008"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("01/20/2010"));
        sp.setOfficialTitle("officialTitle");
        sp.setLeadOrganizationIdentifier("1");
        sp.setLocalProtocolIdentifier("localProtocolIdentifier");
        sp.setPhaseCode(PhaseCode.I_II);
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.PREVENTION);
        sp.setTrialStatusCode(StudyStatusCode.ACTIVE);
        sp.setPiIdentifier("2");
        sp.setSponsorIdentifier("1");
        sp.setSummaryFourFundingCategoryCode(SummaryFourFundingCategoryCode.NATIONAL);
        sp.getSummaryFourOrgIdentifiers().add("1");
        sp.setFdaRegulatedIndicator(Boolean.TRUE);
        sp.setSection801Indicator(Boolean.TRUE);
        sp.setDelayedpostingIndicator(Boolean.TRUE);
        sp.setDataMonitoringCommitteeAppointedIndicator(Boolean.TRUE);
        sp.setOversightAuthorityCountryId("1");
        sp.setOversightAuthorityOrgId("1");
        list.add(sp);
        sp = new StudyProtocolStage();
        sp.setId(3L);
        dates = sp.getDates();
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setStartDate(PAUtil.dateStringToTimestamp("01/20/2008"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("01/20/2010"));
        sp.setOfficialTitle("officialTitle");
        sp.setLeadOrganizationIdentifier("1");
        sp.setLocalProtocolIdentifier("localProtocolIdentifier");
        sp.setFdaRegulatedIndicator(Boolean.FALSE);
        sp.setSection801Indicator(Boolean.FALSE);
        sp.setDelayedpostingIndicator(Boolean.FALSE);
        sp.setDataMonitoringCommitteeAppointedIndicator(Boolean.FALSE);
        sp.setResponsibleIdentifier("1");
        sp.setOversightAuthorityCountryId("1");
        sp.setOversightAuthorityOrgId("1");
        list.add(sp);
        studyFundingList = new ArrayList<StudyFundingStage>();
        StudyFundingStage studyFunding = new StudyFundingStage();
        studyFunding.setFundingMechanismCode("fundingMechanismCode");
        studyFunding.setNciDivisionProgramCode(NciDivisionProgramCode.CCR);
        studyFunding.setSerialNumber("serialNumber");
        studyFunding.setFundingPercent(60d);
        studyFunding.setNihInstituteCode("nihInstituteCode");
        studyFunding.setStudyProtocolStage(sp);
        studyFundingList.add(studyFunding);

        studyIndIdeList = new ArrayList<StudyIndIdeStage>();
        StudyIndIdeStage indIde = new StudyIndIdeStage();
        indIde.setIndIdeNumber("indIdeNumber");
        indIde.setIndldeTypeCode(IndldeTypeCode.IDE);
        indIde.setGrantorCode(GrantorCode.CBER);
        indIde.setHolderTypeCode(HolderTypeCode.INDUSTRY);
        indIde.setExpandedAccessIndicator(Boolean.FALSE);
        indIde.setStudyProtocolStage(sp);
        studyIndIdeList.add(indIde);
        indIde = new StudyIndIdeStage();
        indIde.setIndIdeNumber("indIdeNumber");
        indIde.setIndldeTypeCode(IndldeTypeCode.IDE);
        indIde.setGrantorCode(GrantorCode.CBER);
        indIde.setHolderTypeCode(HolderTypeCode.NCI);
        indIde.setNciDivPrgHolderCode(NciDivisionProgramCode.CCR);
        indIde.setExpandedAccessIndicator(Boolean.TRUE);
        indIde.setExpandedAccessStatusCode(ExpandedAccessStatusCode.AVAILABLE);
        indIde.setStudyProtocolStage(sp);
        studyIndIdeList.add(indIde);
        indIde = new StudyIndIdeStage();
        indIde.setIndIdeNumber("indIdeNumber");
        indIde.setIndldeTypeCode(IndldeTypeCode.IDE);
        indIde.setGrantorCode(GrantorCode.CBER);
        indIde.setHolderTypeCode(HolderTypeCode.NIH);
        indIde.setNihInstHolderCode(NihInstituteCode.NEI);
        indIde.setExpandedAccessIndicator(Boolean.TRUE);
        indIde.setExpandedAccessStatusCode(ExpandedAccessStatusCode.AVAILABLE);
        indIde.setStudyProtocolStage(sp);
        studyIndIdeList.add(indIde);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Ii ii) throws PAException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyProtocolStageDTO get(Ii ii) throws PAException {
        for (StudyProtocolStage tsp : list) {
            if (tsp.getId().equals(IiConverter.convertToLong(ii))) {
                return new StudyProtocolStageConverter().convertFromDomainToDto(tsp);
            }
        }
        return new StudyProtocolStageDTO();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyFundingStageDTO> getGrantsByStudyProtocolStage(Ii studyProtocolStageIi) throws PAException {
        List<StudyFundingStageDTO> retList = new ArrayList<StudyFundingStageDTO>();
        for (StudyFundingStage tsf : studyFundingList) {
            if (tsf.getStudyProtocolStage().getId().equals(IiConverter.convertToLong(studyProtocolStageIi))) {
                retList.add(StudyFundingStageConverter.convertFromDomainToDTO(tsf));
            }
        }
        return retList;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyIndIdeStageDTO> getIndIdesByStudyProtocolStage(Ii studyProtocolStageIi) throws PAException {
        List<StudyIndIdeStageDTO> retList = new ArrayList<StudyIndIdeStageDTO>();
        for (StudyIndIdeStage tsf : studyIndIdeList) {
            if (tsf.getStudyProtocolStage().getId().equals(IiConverter.convertToLong(studyProtocolStageIi))) {
                retList.add(new StudyIndIdeStageConverter().convertFromDomainToDto(tsf));
            }
        }
        return retList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyProtocolStageDTO> search(StudyProtocolStageDTO dto, LimitOffset pagingParams) throws PAException,
            TooManyResultsException {
        List<StudyProtocolStageDTO> returnList = new ArrayList<StudyProtocolStageDTO>();
        if (dto.getOfficialTitle() != null && dto.getOfficialTitle().getValue().equalsIgnoreCase("ThrowException")) {
            throw new PAException("Test");
        }
        for (StudyProtocolStage tempSp : list) {
            String phaseCode = CdConverter.convertCdToString(dto.getPhaseCode());
            if (StringUtils.isNotEmpty(phaseCode) && tempSp.getPhaseCode().getCode().equalsIgnoreCase(phaseCode)) {
                returnList.add(new StudyProtocolStageConverter().convertFromDomainToDto(tempSp));
            }
            if (!ISOUtil.isStNull(dto.getOfficialTitle())
                    && tempSp.getOfficialTitle().equalsIgnoreCase(StConverter.convertToString(dto.getOfficialTitle()))) {
                returnList.add(new StudyProtocolStageConverter().convertFromDomainToDto(tempSp));
            }
        }
        return returnList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ii create(StudyProtocolStageDTO ispDTO, List<StudyFundingStageDTO> fundDTOs,
            List<StudyIndIdeStageDTO> indDTOs, List<DocumentDTO> docDTOs) throws PAException {
        return IiConverter.convertToIi("1");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DocumentDTO> getDocumentsByStudyProtocolStage(Ii studyProtocolStageIi) throws PAException {
        return new ArrayList<DocumentDTO>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyProtocolStageDTO update(StudyProtocolStageDTO isoDTO, List<StudyFundingStageDTO> fundDTOs,
            List<StudyIndIdeStageDTO> indDTOs, List<DocumentDTO> docDTOs) throws PAException {
        StudyProtocolStageDTO isoDto = new StudyProtocolStageDTO();
        isoDto.setIdentifier(IiConverter.convertToIi("1"));
        return isoDto;
    }

}

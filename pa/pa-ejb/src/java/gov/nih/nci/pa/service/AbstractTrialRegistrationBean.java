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

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.CodedEnum;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author vrushali
 *
 */
public abstract class AbstractTrialRegistrationBean {
    private PAServiceUtils paServiceUtils = new PAServiceUtils();
    /**
     * @param paServiceUtils the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

    /**
     * @return PAServiceUtils
     */
    protected PAServiceUtils getPAServiceUtils() {
        return this.paServiceUtils;
    }

    /**
     * @param studyProtocolIi protocol ii
     * @param smCode  milestone code
     * @param inbox list Of In box DTO
     * @throws PAException on err
     */
    protected void sendTSRXML(Ii studyProtocolIi, Cd smCode, List<StudyInboxDTO> inbox)
            throws PAException {
        if (!ISOUtil.isIiNull(studyProtocolIi) && !ISOUtil.isCdNull(smCode) && !isActiveRecordInInbox(inbox)
                && MilestoneCode.isAboveTrialSummaryReport(MilestoneCode.getByCode(CdConverter
                        .convertCdToString(smCode)))) {
            paServiceUtils.createMilestone(studyProtocolIi, MilestoneCode.TRIAL_SUMMARY_REPORT, null, null);
        }
    }
    /**
     * @param inbox inbox
     * @return boolean
     */
    private boolean isActiveRecordInInbox(List<StudyInboxDTO> inbox) {
        boolean activeRecord = false;
        if (CollectionUtils.isNotEmpty(inbox)) {
            for (StudyInboxDTO inboxDto : inbox) {
                String strCloseDate = IvlConverter.convertTs().convertHighToString(inboxDto.getInboxDateRange());
                if (StringUtils.isEmpty(strCloseDate)) {
                    activeRecord = true;
                }
            }
        }
        return activeRecord;
    }
    /**
     * @param studyProtocolDTO studyProtocolDTO
     * @param returnStudyProtocolDTO returnDTO
     */
    protected void setPhaseAdditionalQualifier(StudyProtocolDTO studyProtocolDTO,
            StudyProtocolDTO returnStudyProtocolDTO) {
        if (PAUtil.isPhaseCodeNA(CdConverter.convertCdToString(studyProtocolDTO.getPhaseCode()))) {
            returnStudyProtocolDTO.setPhaseAdditionalQualifierCode(studyProtocolDTO.getPhaseAdditionalQualifierCode());
        } else {
            returnStudyProtocolDTO.setPhaseAdditionalQualifierCode(CdConverter.convertStringToCd(null));
        }
    }
    /**
     * @param studyProtocolDTO dto
     * @param returnStudyProtocolDTO  dto
     */
    @SuppressWarnings("rawtypes")
    protected void setPrimaryPurposeCode(StudyProtocolDTO studyProtocolDTO,
            StudyProtocolDTO returnStudyProtocolDTO) {
        returnStudyProtocolDTO.setPrimaryPurposeCode(studyProtocolDTO.getPrimaryPurposeCode());
        if (PAUtil.isPrimaryPurposeCodeOther(CdConverter.convertCdToString(studyProtocolDTO.getPrimaryPurposeCode()))) {
            returnStudyProtocolDTO.setPrimaryPurposeOtherText(studyProtocolDTO.getPrimaryPurposeOtherText());
            returnStudyProtocolDTO.setPrimaryPurposeAdditionalQualifierCode(studyProtocolDTO
                    .getPrimaryPurposeAdditionalQualifierCode());
        } else {
            returnStudyProtocolDTO.setPrimaryPurposeOtherText(StConverter.convertToSt(null));
            returnStudyProtocolDTO
                    .setPrimaryPurposeAdditionalQualifierCode(CdConverter
                            .convertToCd((CodedEnum) null));
        }     
        returnStudyProtocolDTO.setSecondaryPurposes(studyProtocolDTO.getSecondaryPurposes());
        returnStudyProtocolDTO.setSecondaryPurposeOtherText(studyProtocolDTO.getSecondaryPurposeOtherText());
    }
    
    
    /**
     * @param from
     *            StudyProtocolDTO
     * @param to
     *            StudyProtocolDTO
     */
    protected void setNonInterventionalTrialFields(StudyProtocolDTO from,
            StudyProtocolDTO to) {
        if (from instanceof NonInterventionalStudyProtocolDTO
                && to instanceof NonInterventionalStudyProtocolDTO) {
            NonInterventionalStudyProtocolDTO fromDTO = (NonInterventionalStudyProtocolDTO) from;
            NonInterventionalStudyProtocolDTO toDTO = (NonInterventionalStudyProtocolDTO) to;
            toDTO.setStudySubtypeCode(fromDTO.getStudySubtypeCode());
            toDTO.setStudyModelCode(fromDTO.getStudyModelCode());
            toDTO.setStudyModelOtherText(fromDTO.getStudyModelOtherText());
            toDTO.setTimePerspectiveCode(fromDTO.getTimePerspectiveCode());
            toDTO.setTimePerspectiveOtherText(fromDTO
                    .getTimePerspectiveOtherText());
            if (!ISOUtil.isIntNull(fromDTO.getNumberOfGroups())) {
                toDTO.setNumberOfGroups(fromDTO
                        .getNumberOfGroups());
            }
            if (!ISOUtil.isCdNull(fromDTO.getSamplingMethodCode())) {
                toDTO.setSamplingMethodCode(fromDTO.getSamplingMethodCode());
            }
            if (!ISOUtil.isStNull(fromDTO.getStudyPopulationDescription())) {
                toDTO.setStudyPopulationDescription(fromDTO.getStudyPopulationDescription());
            }
        }
    }
    
    /**
     * @param from
     *            StudyProtocolDTO
     * @param to
     *            StudyProtocolDTO
     */
    protected void setInterventionalTrialFields(StudyProtocolDTO from,
            StudyProtocolDTO to) {
        if (from instanceof InterventionalStudyProtocolDTO
                && to instanceof InterventionalStudyProtocolDTO) {
            InterventionalStudyProtocolDTO fromDTO = (InterventionalStudyProtocolDTO) from;
            InterventionalStudyProtocolDTO toDTO = (InterventionalStudyProtocolDTO) to;
            if (!ISOUtil.isIntNull(fromDTO.getNumberOfInterventionGroups())) {
                toDTO.setNumberOfInterventionGroups(fromDTO
                        .getNumberOfInterventionGroups());
            }
            if (!ISOUtil.isCdNull(fromDTO.getAllocationCode())) {
                toDTO.setAllocationCode(fromDTO.getAllocationCode());
            }
            if (!ISOUtil.isCdNull(fromDTO.getStudyClassificationCode())) {
                toDTO.setStudyClassificationCode(fromDTO.getStudyClassificationCode());
            }
            if (!ISOUtil.isCdNull(fromDTO.getDesignConfigurationCode())) {
                toDTO.setDesignConfigurationCode(fromDTO.getDesignConfigurationCode());
            }
            if (!ISOUtil.isCdNull(fromDTO.getBlindingSchemaCode())) {
                toDTO.setBlindingSchemaCode(fromDTO.getBlindingSchemaCode());
            }
            if (ISOUtil.isDSetNotEmpty(fromDTO.getBlindedRoleCode())) {
                toDTO.setBlindedRoleCode(fromDTO.getBlindedRoleCode());
            }
        }
    }

}

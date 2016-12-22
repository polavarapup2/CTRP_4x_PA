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

import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.NonInterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.ProgramCode;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyCheckout;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyInbox;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.dto.MilestoneDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.builder.CompareToBuilder;



/**
 * Study Protocol Query Converter for trial search.
 * @author mshestopalov
 *
 */
public class TrialSearchStudyProtocolQueryConverter extends BaseStudyProtocolQueryConverter {
    
    /**
     * Const.
     * @param registryUserSvc registry user service.
     * @param paSvcUtils pa service utils.
     */
    public TrialSearchStudyProtocolQueryConverter(RegistryUserServiceLocal registryUserSvc, PAServiceUtils paSvcUtils) {
        super(registryUserSvc, paSvcUtils);
    }

    /**
     * Convert trial domain object into a fully loaded dto for trial search and trial summary.
     * @param studyProtocol trial domain object
     * @param myTrialsOnly only able to view user's trials
     * @param potentialOwner trial owner
     * @return dto
     * @throws PAException when error
     */
    public StudyProtocolQueryDTO convertToStudyProtocolDto(StudyProtocol studyProtocol,
            boolean myTrialsOnly, RegistryUser potentialOwner) throws PAException {
        StudyProtocolQueryDTO studyProtocolDto = new StudyProtocolQueryDTO();

        if (!userHasAccess(potentialOwner, studyProtocolDto, studyProtocol.getId(), myTrialsOnly)) {
            return null;
        }

        setStudyProtocolFields(studyProtocolDto, studyProtocol);
        setNonInterventionalFields(studyProtocolDto, studyProtocol);
        setStudyResourcing(studyProtocolDto, studyProtocol);
        setOverallStatus(studyProtocolDto, studyProtocol);
        setInboxAndSubmissionType(studyProtocolDto, studyProtocol);
        setOrganization(studyProtocolDto, studyProtocol);
        setPerson(studyProtocolDto, studyProtocol);
        setDocumentWorkflowStatus(studyProtocolDto, studyProtocol);
        studyProtocolDto.setNciIdentifier(PADomainUtils.getAssignedIdentifierExtension(studyProtocol));
        studyProtocolDto.setOtherIdentifiers(PADomainUtils.getOtherIdentifierExtensions(studyProtocol));
        studyProtocolDto.setCdrId(PADomainUtils.getCDRId(studyProtocol));
        studyProtocolDto.setDiseaseNames(PADomainUtils.getDiseaseNames(studyProtocol));
        studyProtocolDto.setInterventionType(PADomainUtils.getInterventionTypes(studyProtocol));
        if (studyProtocol.getUserLastCreated() != null) {
            studyProtocolDto.getLastCreated().setUserLastCreated(
                    studyProtocol.getUserLastCreated().getLoginName());

            studyProtocolDto.getLastCreated().setUserLastDisplayName(CsmUserUtil
                    .getDisplayUsername(studyProtocol.getUserLastCreated()));
        }
        setCheckout(studyProtocolDto, studyProtocol);

        PAUtil.convertMilestonesToDTO(studyProtocolDto.getMilestones(),
                studyProtocol.getStudyMilestones());
        setMilestoneHistory(studyProtocolDto, studyProtocol);

        String nctNumber = getPaServiceUtils().getStudyIdentifier(IiConverter
                .convertToStudyProtocolIi(studyProtocol.getId()), PAConstants.NCT_IDENTIFIER_TYPE);
        studyProtocolDto.setNctNumber(nctNumber);
        
        String ctepIdentifier = getPaServiceUtils().getStudyIdentifier(IiConverter
                .convertToStudyProtocolIi(studyProtocol.getId()), PAConstants.CTEP_IDENTIFIER_TYPE);
        studyProtocolDto.setCtepId(ctepIdentifier);
        
        String dcpIdentifier = getPaServiceUtils().getStudyIdentifier(IiConverter
                .convertToStudyProtocolIi(studyProtocol.getId()), PAConstants.DCP_IDENTIFIER_TYPE);
        studyProtocolDto.setDcpId(dcpIdentifier);
        
        
        studyProtocolDto.setStudySource(studyProtocol.getStudySource());
        if (studyProtocol.getSubmitingOrganization() != null) {
            studyProtocolDto.setSubmitterOrgId(Long.valueOf(studyProtocol.getSubmitingOrganization().getIdentifier()));
            studyProtocolDto.setSubmitterOrgName(studyProtocol.getSubmitingOrganization().getName());
        }

        if (CollectionUtils.isNotEmpty(studyProtocol.getProgramCodes())) {
            for (ProgramCode programCode : studyProtocol.getProgramCodes()) {

                ProgramCodeDTO programCodeDTO = new ProgramCodeDTO();
                programCodeDTO.setProgramCode(programCode.getProgramCode());
                programCodeDTO.setProgramName(programCode.getProgramName());
                programCodeDTO.setId(programCode.getId());
                programCodeDTO.setActive(programCode.getStatusCode() == ActiveInactiveCode.ACTIVE);

                studyProtocolDto.getProgramCodes().add(programCodeDTO);
            }
        }
        return studyProtocolDto;
    }

    private void setMilestoneHistory(StudyProtocolQueryDTO dto, StudyProtocol sp) {
        Set<StudyMilestone> copy = new TreeSet<StudyMilestone>(
                new Comparator<StudyMilestone>() {
                    @Override
                    public int compare(StudyMilestone o1, StudyMilestone o2) {
                        return new CompareToBuilder()
                                .append(o1.getMilestoneDate(),
                                        o2.getMilestoneDate())
                                .append(o1.getId(), o2.getId()).toComparison();
                    }
                });
        copy.addAll(sp.getStudyMilestones());
        for (StudyMilestone sm : copy) {
            MilestoneDTO mDTO = new MilestoneDTO();
            mDTO.setMilestone(sm.getMilestoneCode());
            mDTO.setMilestoneDate(sm.getMilestoneDate());
            mDTO.setCreateDate(sm.getDateLastCreated());
            mDTO.setCreator(CsmUserUtil.getDisplayUsername(sm
                    .getUserLastCreated()));
            dto.getMilestoneHistory().add(mDTO);
        }
    }

    private void setNonInterventionalFields(
            StudyProtocolQueryDTO studyProtocolDto, StudyProtocol studyProtocol) {
        if (studyProtocol instanceof NonInterventionalStudyProtocol) {
            NonInterventionalStudyProtocol nonIntSp = (NonInterventionalStudyProtocol) studyProtocol;
            studyProtocolDto
                    .setStudySubtypeCode(nonIntSp.getStudySubtypeCode() != null ? nonIntSp
                            .getStudySubtypeCode().getCode() : "");
            studyProtocolDto
                    .setStudyModelCode(nonIntSp.getStudyModelCode() != null ? nonIntSp
                            .getStudyModelCode().getCode() : "");
            studyProtocolDto.setStudyModelOtherText(nonIntSp
                    .getStudyModelOtherText());
            studyProtocolDto.setTimePerspectiveCode(nonIntSp
                    .getTimePerspectiveCode() != null ? nonIntSp
                    .getTimePerspectiveCode().getCode() : "");
            studyProtocolDto.setTimePerspectiveOtherText(nonIntSp
                    .getTimePerspectiveOtherText());

        }
    }

    /**
     * Set checkout user.
     * @param studyProtocolDto trial dto
     * @param studyProtocol trial domain object
     */
    private void setCheckout(StudyProtocolQueryDTO studyProtocolDto, StudyProtocol studyProtocol) {
        if (CollectionUtils.isNotEmpty(studyProtocol.getStudyCheckout())) {
            for (StudyCheckout studyCheckout : studyProtocol.getStudyCheckout()) {
                if (studyCheckout.getCheckInDate() != null) {
                    continue;
                }
                switch (studyCheckout.getCheckOutType()) {
                case ADMINISTRATIVE:
                    studyProtocolDto.getAdminCheckout().setCheckoutBy(studyCheckout.getUserIdentifier());
                    studyProtocolDto.getAdminCheckout().setCheckoutId(studyCheckout.getId());
                    break;
                case SCIENTIFIC:
                    studyProtocolDto.getScientificCheckout().setCheckoutBy(studyCheckout.getUserIdentifier());
                    studyProtocolDto.getScientificCheckout().setCheckoutId(studyCheckout.getId());
                    break;
                default:
                    break;
                }
            }
        }
    }
    /**
     * Set overall status.
     * @param studyProtocolDto trial dto
     * @param studyProtocol trial domain object
     */
    private void setOverallStatus(StudyProtocolQueryDTO studyProtocolDto, StudyProtocol studyProtocol) {
        // get studyOverallStatus
        StudyOverallStatus studyOverallStatus = studyProtocol.getStudyOverallStatuses().isEmpty() ? null
                : studyProtocol.getStudyOverallStatuses().iterator().next();
        if (studyOverallStatus != null) {
            studyProtocolDto.setStudyStatusCode(studyOverallStatus.getStatusCode());
            studyProtocolDto.setStudyStatusDate(studyOverallStatus.getStatusDate());
        }
    }

    private StudyResourcing findSumm4FundingSrc(StudyProtocol studyProtocol) {

        for (StudyResourcing item : studyProtocol.getStudyResourcings()) {
            if (item.getSummary4ReportedResourceIndicator()) {
                return item;
            }
        }

        return null;
    }

    /**
     * Set study resourcing.
     * @param studyProtocolDto trial dto
     * @param studyProtocol trial domain object
     */
    private void setStudyResourcing(StudyProtocolQueryDTO studyProtocolDto, StudyProtocol studyProtocol) {
        StudyResourcing studyResourcing = findSumm4FundingSrc(studyProtocol);
        studyProtocolDto.setSumm4FundingSrcCategory(studyResourcing != null
                && studyResourcing.getTypeCode() != null ? studyResourcing.getTypeCode().getCode() : null);

    }

    /**
     * Set Organization.
     * @param studyProtocolDto trial dto
     * @param studyProtocol trial domain object
     */
    private void setOrganization(StudyProtocolQueryDTO studyProtocolDto, StudyProtocol studyProtocol) {
        // study site and organization
        StudySite studySite = studyProtocol.getStudySites().isEmpty() ? null
                : studyProtocol.getStudySites().iterator().next();
        Organization organization = (studySite != null) ? studySite.getResearchOrganization().getOrganization()
                : null;
        if (organization != null) {
            studyProtocolDto.setLeadOrganizationName(organization.getName());
            studyProtocolDto.setLeadOrganizationId(organization.getId());
            studyProtocolDto.setLeadOrganizationPOId(Long.valueOf(organization.getIdentifier()));
        }

        if (studySite != null) {
            studyProtocolDto.setLocalStudyProtocolIdentifier(studySite.getLocalStudyProtocolIdentifier());
        }
    }

    /**
     * Set document workflow status.
     * @param studyProtocolDto trial dto
     * @param studyProtocol trial domain object
     */
    private void setDocumentWorkflowStatus(StudyProtocolQueryDTO studyProtocolDto, StudyProtocol studyProtocol) {
        if (!studyProtocol.getDocumentWorkflowStatuses().isEmpty()) {
            DocumentWorkflowStatus dws = studyProtocol.getDocumentWorkflowStatuses().iterator().next();
            studyProtocolDto.setDocumentWorkflowStatusCode(dws.getStatusCode());
            studyProtocolDto.setDocumentWorkflowStatusDate(dws.getStatusDateRangeLow());
            for (DocumentWorkflowStatus status : studyProtocol.getDocumentWorkflowStatuses()) {
                if (status.getStatusCode() != DocumentWorkflowStatusCode.ON_HOLD) {
                    studyProtocolDto.setLastOffHollStatusCode(status.getStatusCode());
                    break;
                }
            }
            setViewTSR(studyProtocolDto, dws.getStatusCode());
        }
    }

    /**
     * Set Inbox and Submission Type.
     * @param studyProtocolDto trial dto
     * @param studyProtocol trial domain object
     */
    private void setInboxAndSubmissionType(StudyProtocolQueryDTO studyProtocolDto, StudyProtocol studyProtocol) {
        // get the StudyInbox
        StudyInbox studyInbox = studyProtocol.getStudyInbox().isEmpty() ? null
                : studyProtocol.getStudyInbox().iterator().next();

        if (studyInbox != null) {
            studyProtocolDto.setStudyInboxId(studyInbox.getId());
            studyProtocolDto.setUpdatedComments(studyInbox.getComments());
            studyProtocolDto.setUpdatedDate(studyInbox.getOpenDate());
        }

        SubmissionTypeVars subVars = new SubmissionTypeVars();
        subVars.setAmendmentDate(studyProtocol.getAmendmentDate());
        subVars.setAmendmentNumber(studyProtocol.getAmendmentNumber());
        subVars.setSubmissionNumber(studyProtocol.getSubmissionNumber());
        subVars.setClosedDate(studyInbox != null ? studyInbox.getCloseDate() : null);
        subVars.setInboxExists(studyInbox != null);
        setSubmissionType(studyProtocolDto, subVars);

    }

    /**
     * Set Person.
     * 
     * @param studyProtocolDto
     *            trial dto
     * @param studyProtocol
     *            trial domain object
     */
    private void setPerson(StudyProtocolQueryDTO studyProtocolDto,
            StudyProtocol studyProtocol) {
        for (StudyContact sc : studyProtocol.getStudyContacts()) {
            final ClinicalResearchStaff crs = sc.getClinicalResearchStaff();
            if (StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.equals(sc
                    .getRoleCode()) && crs != null && crs.getPerson() != null) {
                Person person = crs.getPerson();
                studyProtocolDto.setPiFullName(person.getFullName());
                studyProtocolDto.setPiId(person.getId());
            }
        }
    }

    /**
     * Set Study Protocol fields.
     * @param studyProtocolDto trial dto
     * @param studyProtocol trial domain object
     */
    private void setStudyProtocolFields(StudyProtocolQueryDTO studyProtocolDto, StudyProtocol studyProtocol) {
        if (studyProtocol instanceof NonInterventionalStudyProtocol) {
            studyProtocolDto.setStudyProtocolType("NonInterventionalStudyProtocol");
        } else {
            studyProtocolDto.setStudyProtocolType("InterventionalStudyProtocol");
        }
        studyProtocolDto.setOfficialTitle(studyProtocol.getOfficialTitle());
        studyProtocolDto.setStudyProtocolId(studyProtocol.getId());
        studyProtocolDto.setPhaseCode(studyProtocol.getPhaseCode());
        if (studyProtocol.getPrimaryPurposeCode() != null) {
            studyProtocolDto.setPrimaryPurpose(studyProtocol.getPrimaryPurposeCode().getCode());
        }
        studyProtocolDto.setProprietaryTrial(
                BooleanUtils.toBoolean(studyProtocol.getProprietaryTrialIndicator()));
        studyProtocolDto.setRecordVerificationDate(studyProtocol.getRecordVerificationDate());
        studyProtocolDto.setCtgovXmlRequiredIndicator(
                BooleanUtils.toBoolean(studyProtocol.getCtgovXmlRequiredIndicator()));
        studyProtocolDto.setStudyTypeCode(StudyTypeCode.INTERVENTIONAL);
        studyProtocolDto.setPhaseAdditionalQualifier(studyProtocol.getPhaseAdditionalQualifierCode());
        studyProtocolDto.getLastCreated().setDateLastCreated(studyProtocol.getDateLastCreated());
        
        studyProtocolDto.setProcessingPriority(studyProtocol
                .getProcessingPriority());
        studyProtocolDto.setProcessingComments(studyProtocol.getComments());
        if (studyProtocol.getAssignedUser() != null) {
            studyProtocolDto.setAssignedUserId(studyProtocol.getAssignedUser()
                    .getUserId());
        }
        
    }
}

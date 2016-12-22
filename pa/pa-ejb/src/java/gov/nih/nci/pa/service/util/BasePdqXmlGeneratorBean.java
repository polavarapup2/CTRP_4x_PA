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

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Base class to handle Pdq Xml differences over CT gov xml generation.
 * @author mshestopalov
 *
 */
@SuppressWarnings({ "PMD.TooManyMethods" })
public class BasePdqXmlGeneratorBean extends CTGovXmlGeneratorServiceBeanLocal {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Element createIrbInfo(StudyProtocolDTO spDTO, Document doc) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createOverallStatus(StudyProtocolDTO spDTO, final Document doc, final Element root)
    throws PAException {
        Element trialStatusElement = doc.createElement("trial_status");
        StudyOverallStatusDTO sosDTO = getStudyOverallStatusService().getCurrentByStudyProtocol(spDTO.getIdentifier());
        if (sosDTO == null) {
            return;
        }
        StudyStatusCode overStatusCode = StudyStatusCode.getByCode(sosDTO.getStatusCode().getCode());
        BaseXmlGenHelper.appendElement(trialStatusElement,
                BaseXmlGenHelper.createElementWithTextblock("current_trial_status", overStatusCode.getCode(), doc));
        BaseXmlGenHelper.appendElement(trialStatusElement,
                BaseXmlGenHelper.createElementWithTextblock("current_trial_status_date",
                        convertTsToYYYYMMDDFormat(sosDTO.getStatusDate()), doc));

        if (StudyStatusCode.WITHDRAWN.equals(overStatusCode)
                || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.equals(overStatusCode)
                || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION.equals(overStatusCode)) {
            BaseXmlGenHelper.appendElement(trialStatusElement,
                    BaseXmlGenHelper.createElementWithTextblock("why_stopped",
                            StringUtils.substring(StConverter.convertToString(sosDTO
                    .getReasonText()), 0, PAAttributeMaxLen.LEN_160), doc));
        }
        BaseXmlGenHelper.appendElement(trialStatusElement,
                BaseXmlGenHelper.createElementWithTextblock("current_trial_start_date",
                        convertTsToYYYYMMDDFormat(spDTO.getStartDate()), doc));
        BaseXmlGenHelper.appendElement(trialStatusElement,
                BaseXmlGenHelper.createElementWithTextblock("current_trial_start_date_type",
                        spDTO.getStartDateTypeCode().getCode(), doc));
        BaseXmlGenHelper.appendElement(trialStatusElement,
                BaseXmlGenHelper.createElementWithTextblock("current_trial_completion_date",
                        convertTsToYYYYMMDDFormat(spDTO.getPrimaryCompletionDate()), doc));
        BaseXmlGenHelper.appendElement(trialStatusElement,
                BaseXmlGenHelper.createElementWithTextblock("current_trial_completion_date_type",
                        spDTO.getPrimaryCompletionDateTypeCode().getCode(), doc));
        BaseXmlGenHelper.appendElement(root, trialStatusElement);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createEligibility(StudyProtocolDTO spDTO, Document doc, Element root) throws PAException {
        List<PlannedEligibilityCriterionDTO> paECs = getPlannedActivityService()
                .getPlannedEligibilityCriterionByStudyProtocol(spDTO.getIdentifier());

        if (CollectionUtils.isEmpty(paECs)) {
            return;
        }

        Element eligibility = doc.createElement("eligibility");
        EligibilityComponentHelper eligHelper = new EligibilityComponentHelper();
        // sorts the list on display order
        Collections.sort(paECs, new Comparator<PlannedEligibilityCriterionDTO>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public int compare(PlannedEligibilityCriterionDTO o1, PlannedEligibilityCriterionDTO o2) {
                return (!ISOUtil.isIntNull(o1.getDisplayOrder()) && !ISOUtil.isIntNull(o2.getDisplayOrder())) ? o1
                        .getDisplayOrder().getValue().compareTo(o2.getDisplayOrder().getValue()) : 0;
            }
        });
        Element criteriaElt = doc.createElement("criteria");
        for (PlannedEligibilityCriterionDTO paEC : paECs) {
            PdqXmlGenHelper.handleEligCritTraversal(paEC, eligHelper, criteriaElt, doc);
        }

        BaseXmlGenHelper.appendElement(eligibility, criteriaElt);
        String healthy = BlConverter.convertBlToYesNoString(spDTO.getAcceptHealthyVolunteersIndicator());
        BaseXmlGenHelper.appendElement(eligibility,
                                       BaseXmlGenHelper.createElementWithTextblock("healthy_volunteers", healthy, doc));
        BaseXmlGenHelper.appendElement(eligibility,
                BaseXmlGenHelper.createElementWithTextblock("gender", eligHelper.getGenderCode(), doc));
        BaseXmlGenHelper.appendElement(eligibility,
                XmlGenHelper.createElementWithTextblock("minimum_age", XmlGenHelper.getAgeUnit(eligHelper.getMinAge(),
                        eligHelper.getMinUnit()), doc));
        BaseXmlGenHelper.appendElement(eligibility,
                XmlGenHelper.createElementWithTextblock("maximum_age", XmlGenHelper.getAgeUnit(eligHelper.getMaxAge(),
                        eligHelper.getMaxUnit()), doc));

        BaseXmlGenHelper.appendElement(root, eligibility);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createCondition(Ii studyProtocolIi, Document doc, Element root) throws PAException {
        List<StudyDiseaseDTO> sdDtos = getStudyDiseaseService().getByStudyProtocol(studyProtocolIi);

        if (CollectionUtils.isNotEmpty(sdDtos)) {
            List<PDQDiseaseDTO> diseases = new ArrayList<PDQDiseaseDTO>();
            for (StudyDiseaseDTO sdDto : sdDtos) {
                if (BlConverter.convertToBool(sdDto.getCtGovXmlIndicator())) {
                    PDQDiseaseDTO d = getDiseaseService().get(sdDto.getDiseaseIdentifier());
                    diseases.add(d);
                }
            }
            Collections.sort(diseases, new Comparator<PDQDiseaseDTO>() {
                @Override
                public int compare(PDQDiseaseDTO o1, PDQDiseaseDTO o2) {
                    return o1.getPreferredName().getValue().compareToIgnoreCase(o2.getPreferredName().getValue());
                }

            });
            PdqXmlGenHelper.handleDiseaseCollection(diseases, doc, root);

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createIndInfo(StudyProtocolDTO spDTO, Document doc, Element root) throws PAException {
        List<StudyIndldeDTO> ideDtos = getStudyIndldeService().getByStudyProtocol(spDTO.getIdentifier());
        if (!CollectionUtils.isEmpty(ideDtos)) {
            Element trialIndIdeElement = doc.createElement("trial_ind_ide");

            for (StudyIndldeDTO ideDTO : ideDtos) {
                Element idInfo = doc.createElement("ind_info");

                BaseXmlGenHelper.appendElement(idInfo,
                        BaseXmlGenHelper.createElementWithTextblock("ind_ide_type",
                                ideDTO.getIndldeTypeCode().getCode(), doc));
                BaseXmlGenHelper.appendElement(idInfo,
                        BaseXmlGenHelper.createElementWithTextblock("ind_holder_type",
                                ideDTO.getHolderTypeCode().getCode(), doc));
                BaseXmlGenHelper.appendElement(idInfo,
                        BaseXmlGenHelper.createElementWithTextblock("ind_nih_inst_holder",
                                ideDTO.getNihInstHolderCode().getCode(), doc));
                BaseXmlGenHelper.appendElement(idInfo,
                        BaseXmlGenHelper.createElementWithTextblock("ind_nci_div_holder",
                                ideDTO.getNciDivProgHolderCode().getCode(), doc));
                BaseXmlGenHelper.appendElement(idInfo,
                        BaseXmlGenHelper.createElementWithTextblock("has_expanded_access",
                                BlConverter.convertBlToYesNoString(ideDTO.getExpandedAccessIndicator()), doc));
                if (!ISOUtil.isBlNull(spDTO.getExpandedAccessIndicator())) {
                    if (ideDTO.getExpandedAccessIndicator().getValue()) {
                        BaseXmlGenHelper.appendElement(root,
                                BaseXmlGenHelper.createElementWithTextblock("expanded_access_status", ideDTO
                                        .getExpandedAccessStatusCode().getCode(), doc));
                    } else {
                        BaseXmlGenHelper.appendElement(root,
                                BaseXmlGenHelper.createElementWithTextblock("expanded_access_status",
                                        "No longer available", doc));
                    }
                }
                String exempt = BlConverter.convertBlToYesNoString(ideDTO.getExemptIndicator());
                BaseXmlGenHelper.appendElement(idInfo,
                                               BaseXmlGenHelper.createElementWithTextblock("is_exempt", exempt, doc));
                if (idInfo.hasChildNodes()) {
                    BaseXmlGenHelper.appendElement(trialIndIdeElement, idInfo);
                }
            }
            BaseXmlGenHelper.appendElement(root, trialIndIdeElement);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createTrialFunding(StudyProtocolDTO spDTO, Document doc, Element root) throws PAException {
        List<StudyResourcingDTO> isoList = PaRegistry.getStudyResourcingService().
        getStudyResourcingByStudyProtocol(spDTO.getIdentifier());
        if (!CollectionUtils.isEmpty(isoList)) {
            Element trialIndIdeElement = doc.createElement("trial_funding");

            for (StudyResourcingDTO srDTO : isoList) {
                Element idInfo = doc.createElement("funding_info");

                BaseXmlGenHelper.appendElement(idInfo,
                        BaseXmlGenHelper.createElementWithTextblock("funding_code",
                                srDTO.getFundingMechanismCode().getCode(), doc));
                BaseXmlGenHelper.appendElement(idInfo,
                        BaseXmlGenHelper.createElementWithTextblock("funding_nih_inst_code",
                                srDTO.getNihInstitutionCode().getCode(), doc));
                BaseXmlGenHelper.appendElement(idInfo,
                        BaseXmlGenHelper.createElementWithTextblock("funding_serial_number",
                                srDTO.getSerialNumber().getValue(), doc));
                BaseXmlGenHelper.appendElement(idInfo,
                        BaseXmlGenHelper.createElementWithTextblock("funding_nci_div_program", srDTO
                        .getNciDivisionProgramCode().getCode(), doc));

                if (idInfo.hasChildNodes()) {
                    BaseXmlGenHelper.appendElement(trialIndIdeElement, idInfo);
                }
            }
            BaseXmlGenHelper.appendElement(root, trialIndIdeElement);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Element createLeadSponsor(Ii studyProtocolIi, Document doc)
        throws PAException {

        Ii paRoIi = getOrgCorrelationService().getROByFunctionRole(studyProtocolIi, CdConverter
                        .convertToCd(StudySiteFunctionalCode.SPONSOR));
        Element lead = doc.createElement("lead_sponsor");
        addPoOrganizationByPaRoIi(lead, null, paRoIi, doc, this.getCorUtils());

        return lead;
    }

    /**
     * addPoOrganizationByPaRoIi.
     * @param root Element
     * @param childName of element
     * @param paRoIi pa ro ii
     * @param doc Document
     * @param corrUtils utility
     * @throws PAException when error
     */
    protected void addPoOrganizationByPaRoIi(Element root, String childName,
            Ii paRoIi, Document doc, CorrelationUtils corrUtils)
        throws PAException {
        ResearchOrganizationDTO roDTO = PdqXmlGenHelper.getPoRODTOByPaRoIi(paRoIi, corrUtils);
        if (roDTO == null) {
            return;
        }
        OrganizationDTO orgDTO;
        try {
            orgDTO = PoRegistry.getOrganizationEntityService()
                .getOrganization(roDTO.getPlayerIdentifier());
        } catch (NullifiedEntityException e) {
            throw new PAException(e);
        }

        // Change org name if name is CTEP or DCP
        String orgName = EnOnConverter.convertEnOnToString(orgDTO.getName());

        if (PAConstants.DCP_ORG_NAME.equals(orgName) || PAConstants.CTEP_ORG_NAME.equals(orgName)) {
            orgName = PAConstants.NCI_ORG_NAME;
        }
        orgDTO.setName(EnOnConverter.convertToEnOn(orgName));
        PdqXmlGenHelper.addPoOrganizationByPaRoIi(root, childName, doc, roDTO, orgDTO);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected Element createResponsibleParty(Ii studyProtocolIi, Document doc)
    throws PAException, NullifiedRoleException {
        Element responsibleParty = doc.createElement("resp_party");
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos = getStudyContactService().getByStudyProtocol(studyProtocolIi, scDto);

        if (CollectionUtils.isNotEmpty(scDtos)) {

            PdqXmlGenHelper.addPoPersonByPaCrsIi(responsibleParty,
                    "resp_party_person", scDtos.get(0).getClinicalResearchStaffIi(), doc, this.getCorUtils());

            StudySiteDTO spartDTO = new StudySiteDTO();
            spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
            List<StudySiteDTO> sParts = getStudySiteService().getByStudyProtocol(studyProtocolIi, spartDTO);

            PdqXmlGenHelper.addPoOrganizationByPaRoIi(responsibleParty,
                    "resp_party_organization", sParts.get(0).getResearchOrganizationIi(), doc, this.getCorUtils());

        } else {

            StudySiteContactDTO spart = new StudySiteContactDTO();
            spart.setRoleCode(CdConverter.convertToCd(StudySiteContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
            List<StudySiteContactDTO> spcDtos = getStudySiteContactService()
                .getByStudyProtocol(studyProtocolIi, spart);
            if (CollectionUtils.isNotEmpty(spcDtos)) {
                spart = spcDtos.get(0);
                PdqXmlGenHelper.addPoOrganizationalContactByPaOcIi(responsibleParty, "resp_party_generic_contact",
                        spart.getOrganizationalContactIi(), doc, this.getCorUtils());
            }
            StudySiteDTO spDto = new StudySiteDTO();
            spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
            List<StudySiteDTO> ssDtos = getStudySiteService().getByStudyProtocol(studyProtocolIi, spDto);
            if (CollectionUtils.isNotEmpty(ssDtos)) {
                spDto = ssDtos.get(0);
                PdqXmlGenHelper.addPoOrganizationByPaRoIi(responsibleParty,
                        "resp_party_organization", spDto.getResearchOrganizationIi(), doc, this.getCorUtils());
            }

        }

        return responsibleParty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addMiddleName(Element contact, String name, Document doc) {
        String middleName = StringUtils.substring(name, 0, PAAttributeMaxLen.LEN_1);
        if (StringUtils.isNotBlank(middleName)) {
            XmlGenHelper.appendElement(contact,
                    XmlGenHelper.createElementWithTextblock("middle_initial", middleName + "." , doc));
}
    }

}

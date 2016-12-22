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
package gov.nih.nci.pa.service.util; // NOPMD

import static gov.nih.nci.pa.service.AbstractBaseIsoService.ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.ADMIN_ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SCIENTIFIC_ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SECURITY_DOMAIN;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUBMITTER_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUPER_ABSTRACTOR_ROLE;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StratumGroupDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Generates the xml representation of pdq data.
 * @author mshestopalov
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SecurityDomain(SECURITY_DOMAIN)
@RolesAllowed({ SUBMITTER_ROLE, ADMIN_ABSTRACTOR_ROLE, ABSTRACTOR_ROLE,
    SCIENTIFIC_ABSTRACTOR_ROLE, SUPER_ABSTRACTOR_ROLE })
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PDQXmlGeneratorServiceBean extends BasePdqXmlGeneratorBean implements PDQXmlGeneratorServiceRemote {

    private static final Logger LOG = Logger.getLogger(PDQXmlGeneratorServiceBean.class);
    private static final String PDQ_XSD_FILE = "CTRPProtocolExport.xsd";
    private static final String NAME = "name";
    private static final String SEC_ID = "secondary_id";
    private static final String ID = "id";
    private static final String ID_TYPE = "id_type";
    private static final String ID_DOMAIN = "id_domain";

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addTrialSecondaryIdInfo(StudyProtocolDTO spDTO, Document doc, Element idInfoNode)
        throws PAException {
        for (Ii item : spDTO.getSecondaryIdentifiers().getItem()) {
            if (IiConverter.STUDY_PROTOCOL_ROOT.equals(item.getRoot())) {
                continue;
            }
            Element secId = doc.createElement(SEC_ID);
            XmlGenHelper.appendElement(secId, XmlGenHelper.createElementWithTextblock(ID, item.getExtension(), doc));
            XmlGenHelper.appendElement(idInfoNode, secId);
        }
        String ctepId = new PAServiceUtils()
            .getStudyIdentifier(spDTO.getIdentifier(), PAConstants.CTEP_IDENTIFIER_TYPE);
        if (StringUtils.isNotEmpty(ctepId)) {
            Element secCtepId = doc.createElement(SEC_ID);
            XmlGenHelper.appendElement(secCtepId, XmlGenHelper.createElementWithTextblock(ID, ctepId, doc));
            XmlGenHelper.appendElement(secCtepId, XmlGenHelper.createElementWithTextblock(ID_TYPE, "ctep-id", doc));
            XmlGenHelper.appendElement(secCtepId, XmlGenHelper.createElementWithTextblock(ID_DOMAIN, "CTEP", doc));
            XmlGenHelper.appendElement(idInfoNode, secCtepId);
        }
        String dcpId = new PAServiceUtils().getStudyIdentifier(spDTO.getIdentifier(), PAConstants.DCP_IDENTIFIER_TYPE);
        if (StringUtils.isNotEmpty(dcpId)) {
            Element secDcpId = doc.createElement(SEC_ID);
            XmlGenHelper.appendElement(secDcpId, XmlGenHelper.createElementWithTextblock(ID, dcpId, doc));
            XmlGenHelper.appendElement(secDcpId, XmlGenHelper.createElementWithTextblock(ID_TYPE, "dcp-id", doc));
            XmlGenHelper.appendElement(secDcpId, XmlGenHelper.createElementWithTextblock(ID_DOMAIN, "DCP", doc));
            XmlGenHelper.appendElement(idInfoNode, secDcpId);
        }
        String nctId = new PAServiceUtils().getStudyIdentifier(spDTO.getIdentifier(), PAConstants.NCT_IDENTIFIER_TYPE);
        if (StringUtils.isNotEmpty(nctId)) {
            Element secNctId = doc.createElement(SEC_ID);
            XmlGenHelper.appendElement(secNctId, XmlGenHelper.createElementWithTextblock(ID, nctId, doc));
            XmlGenHelper.appendElement(secNctId, XmlGenHelper.createElementWithTextblock(ID_TYPE, "nct-id", doc));
            XmlGenHelper.appendElement(secNctId, XmlGenHelper.createElementWithTextblock(ID_DOMAIN, "NCT", doc));
            XmlGenHelper.appendElement(idInfoNode, secNctId);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addHumanSafetySubjectInfo(StudyProtocolDTO spDTO, Document doc, Element root)
    throws PAException {
        //NOOP
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addLeadOrgInfo(StudyProtocolDTO spDTO, Document doc, Element root)
        throws PAException {

        List<StudySiteDTO> list = this.getStudySiteService().getByStudyProtocol(spDTO.getIdentifier());
        Ii paRoIi = null;
        for (StudySiteDTO item : list) {
            if (StudySiteFunctionalCode.LEAD_ORGANIZATION.getCode().equals(item.getFunctionalCode().getCode())) {
                paRoIi = item.getResearchOrganizationIi();
                break;
            }
       }
        PdqXmlGenHelper.addPoOrganizationByPaRoIi(root, "lead_org", paRoIi, doc, this.getCorUtils());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addStudyOwnersInfo(StudyProtocolDTO spDTO, Document doc, Element root)
        throws PAException {

        List<String> loginNames = getRegistryUserService().getTrialOwnerNames(
                Long.valueOf(spDTO.getIdentifier().getExtension()));

        Element studyOwners = doc.createElement("trial_owners");
        for (String item : loginNames) {
            XmlGenHelper.appendElement(studyOwners, XmlGenHelper.createElementWithTextblock(NAME, item, doc));
        }
        XmlGenHelper.appendElement(root, studyOwners);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected void addDesignDetailsAdditionalQualifiers(InterventionalStudyProtocolDTO ispDTO,
            Document doc, Element invDesign) {
        XmlGenHelper.appendElement(invDesign,
                XmlGenHelper.createElementWithTextblock("interventional_additional_qualifier",
                        convertToCtValues(ispDTO.getPrimaryPurposeAdditionalQualifierCode()), doc));
        XmlGenHelper.appendElement(invDesign,
                XmlGenHelper.createElementWithTextblock("interventional_other_text",
                        ispDTO.getPrimaryPurposeOtherText().getValue(), doc));
        XmlGenHelper.appendElement(invDesign,
                XmlGenHelper.createElementWithTextblock("phase_additional_qualifier",
                        convertToCtValues(ispDTO.getPhaseAdditionalQualifierCode()), doc));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void addNciSpecificInfo(StudyProtocolDTO spDTO, Document doc, Element root)
        throws PAException {
        List<StudyResourcingDTO> srDTOList = getStudyResourcingService()
        .getSummary4ReportedResourcing(spDTO.getIdentifier());

        Element nciSpecRoot = doc.createElement("nci_specific_information");
        XmlGenHelper.appendElement(nciSpecRoot, XmlGenHelper.createElementWithTextblock("reporting_data_set_method",
                spDTO.getAccrualReportingMethodCode().getCode(), doc));
        if (CollectionUtils.isNotEmpty(srDTOList) && srDTOList.get(0).getTypeCode() != null) {
            XmlGenHelper.appendElement(nciSpecRoot, XmlGenHelper.createElementWithTextblock(
                    "summary_4_funding_category", srDTOList.get(0).getTypeCode().getCode(), doc));
        }
        if (CollectionUtils.isNotEmpty(srDTOList)) {
            for (StudyResourcingDTO srDTO : srDTOList) {
                if (srDTO != null && !ISOUtil.isIiNull(srDTO.getOrganizationIdentifier())) {
                    Element nciSpecFundSpons = doc.createElement("summary_4_funding_sponsor_source");
                    OrganizationDTO poOrgDTO =
                        PdqXmlGenHelper.getPoOrgDTOByPaOrgIi(srDTO.getOrganizationIdentifier(), this.getCorUtils());
                    XmlGenHelper.appendElement(nciSpecFundSpons,
                        XmlGenHelper.createElementWithTextblock(NAME, StringUtils.substring(EnOnConverter
                                .convertEnOnToString(poOrgDTO.getName()), 0, PAAttributeMaxLen.LEN_160), doc));
                    XmlGenHelper.loadPoOrganization(poOrgDTO, nciSpecFundSpons, doc, null);
                    XmlGenHelper.appendElement(nciSpecRoot, nciSpecFundSpons);
                }
            }
        }
        XmlGenHelper.appendElement(nciSpecRoot, XmlGenHelper.createElement("program_code",
                StConverter.convertToString(spDTO.getProgramCodeText()), doc));
        XmlGenHelper.appendElement(root, nciSpecRoot);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createSubGroups(Ii studyProtocolIi, Document doc, Element root)
        throws PAException {
        List<StratumGroupDTO> isoList = PaRegistry.getStratumGroupService().
            getByStudyProtocol(studyProtocolIi);
        if (!(isoList.isEmpty())) {
            Element subGroupsElement = doc.createElement("sub_groups");
            for (StratumGroupDTO dto : isoList) {
                Element subGroupsSingleElement = doc.createElement("sub_groups_info");
                XmlGenHelper.appendElement(subGroupsSingleElement, XmlGenHelper.createElementWithTextblock(
                        "group_number", StConverter.convertToString(dto.getGroupNumberText()), doc));
                XmlGenHelper.appendElement(subGroupsSingleElement, XmlGenHelper.createElementWithTextblock(
                        "description", StConverter.convertToString(dto.getDescription()), doc));
                XmlGenHelper.appendElement(subGroupsElement, subGroupsSingleElement);
            }
            XmlGenHelper.appendElement(root, subGroupsElement);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generatePdqXml(Ii studyProtocolIi) throws PAException {
        String generatedXml = super.generateCTGovXml(studyProtocolIi);
        try {
            validate(generatedXml);
        } catch (Exception e) {
            String errorMsg =
                    String.format("Exception in generating PDQ XML for Study %s", studyProtocolIi.getExtension());
            LOG.error(errorMsg.concat(" (see generated xml file for source xml)"), e);
        }
        return generatedXml;
    }

    private void validate(String generatedXml) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder parser = dbf.newDocumentBuilder();


        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schemaXSD = schemaFactory.newSchema(this.getClass().getClassLoader().getResource(PDQ_XSD_FILE));

        Document document = parser.parse(new InputSource(new StringReader(generatedXml)));
        schemaXSD.newValidator().validate(new DOMSource(document));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Element> createCollaborators(Ii studyProtocolIi, Document doc) throws PAException {
       List<Element> collaborators = new ArrayList<Element>();

       StudySiteDTO spartDTO = new StudySiteDTO();
       List<StudySiteDTO> sParts = new ArrayList<StudySiteDTO>();
       spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.FUNDING_SOURCE));
       sParts.addAll(getStudySiteService().getByStudyProtocol(studyProtocolIi, spartDTO));
       spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LABORATORY));
       sParts.addAll(getStudySiteService().getByStudyProtocol(studyProtocolIi, spartDTO));
       spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.AGENT_SOURCE));
       sParts.addAll(getStudySiteService().getByStudyProtocol(studyProtocolIi, spartDTO));

       if (CollectionUtils.isNotEmpty(sParts)) {
           for (StudySiteDTO sPart : sParts) {
               Element collaborator = doc.createElement("collaborator");
               PdqXmlGenHelper.addPoOrganizationByPaRoIi(collaborator,
                        null, sPart.getResearchOrganizationIi(), doc, this.getCorUtils());
               collaborators.add(collaborator);
           }
       }
       return collaborators;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addInterventionCdrValue(InterventionDTO iDto, Element root, Document doc) {
        root.setAttribute("cdr-id", StringUtils.substring(StConverter
                .convertToString(iDto.getPdqTermIdentifier()), 0, PAAttributeMaxLen.LEN_160));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addOverallOfficialPerson(StudyContactDTO scDTO, Element overallofficial, Document doc)
        throws PAException {
        PdqXmlGenHelper.addPoPersonByPaCrsIi(overallofficial,
                null, scDTO.getClinicalResearchStaffIi(), doc, this.getCorUtils());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addOverallOfficialAffiliation(Ii roIi, Element overallofficial, Document doc) throws PAException {
        PdqXmlGenHelper.addPoOrganizationByPaRoIi(overallofficial,
                "affiliation", roIi, doc, this.getCorUtils());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createFacility(StudySiteDTO sp, Element location, Document doc) throws PAException {
       PdqXmlGenHelper.addPoOrganizationByPaHcfIi(location,
               "facility", sp.getHealthcareFacilityIi(), doc, this.getCorUtils());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addVerificationDate(Document doc, Element root, Ts tsVerificationDate) {
        XmlGenHelper.appendElement(root,
                XmlGenHelper.createElementWithTextblock("verification_date", PAUtil.convertTsToFormattedDate(
                        tsVerificationDate, "yyyy-MM-dd"), doc));
    }

    /**
     * createContact.
     * @param spcDTOs list of site contacts
     * @param location element
     * @param doc document
     * @throws PAException when error
     * @throws NullifiedRoleException when error.
     */
    @Override
    protected void createContact(List<StudySiteContactDTO> spcDTOs, Element location, Document doc)
            throws PAException, NullifiedRoleException {
        for (StudySiteContactDTO sscDTO : spcDTOs) {
            if (!StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().equals(sscDTO.getRoleCode().getCode())) {
                continue;
            }
            Element contact = doc.createElement("contact");
            if (sscDTO.getClinicalResearchStaffIi() != null) {
                addContactPerson(sscDTO, contact, doc);
                PdqXmlGenHelper.loadPersonIdsByPaCrsIi(contact, sscDTO.getClinicalResearchStaffIi(),
                        doc, this.getCorUtils());
                addPhoneAndEmail(sscDTO.getTelecomAddresses(), contact, doc);
            } else if (sscDTO.getOrganizationalContactIi() != null) {
                Long contactId = IiConverter.convertToLong(sscDTO.getOrganizationalContactIi());
                PAContactDTO paCDto = getCorUtils().getContactByPAOrganizationalContactId(contactId);
                XmlGenHelper.appendElement(contact,
                        XmlGenHelper.createElementWithTextblock("last_name", paCDto.getTitle(), doc));
                String poId = StringUtils.substring(IiConverter.convertToString(paCDto.getPersonIdentifier()), 0,
                                                    PAAttributeMaxLen.LEN_160);
                XmlGenHelper.appendElement(contact, XmlGenHelper.createElement("po_id", poId, doc));
                addPhoneAndEmail(sscDTO.getTelecomAddresses(), contact, doc);
            }
            if (contact.hasChildNodes()) {
                XmlGenHelper.appendElement(location, contact);
            }
        }
    }

    /**
     * createInvestigators.
     * @param spcDTOs list of site contacts.
     * @param location element
     * @param doc document
     * @throws PAException when error.
     * @throws
     */
    @Override
    protected void createInvestigators(List<StudySiteContactDTO> spcDTOs, Element location, Document doc)
            throws PAException {
        for (StudySiteContactDTO spcDTO : spcDTOs) {
            if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().equals(spcDTO.getRoleCode().getCode())) {
                continue;
            }
            Element investigator = doc.createElement("investigator");
            try {
                addContactPerson(spcDTO, investigator, doc);
            } catch (NullifiedRoleException e) {
                throw new PAException(e);
            }
            PdqXmlGenHelper.loadPersonIdsByPaCrsIi(investigator,
                    spcDTO.getClinicalResearchStaffIi(), doc, this.getCorUtils());
            addPhoneAndEmail(spcDTO.getTelecomAddresses(), investigator, doc);
            XmlGenHelper.appendElement(investigator,
                    XmlGenHelper.createElementWithTextblock("role", convertToCtValues(spcDTO.getRoleCode()), doc));
            if (investigator.hasChildNodes()) {
                XmlGenHelper.appendElement(location, investigator);
            }
        }
    }

}

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

import static gov.nih.nci.pa.service.AbstractBaseIsoService.SECURITY_DOMAIN;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUBMITTER_ROLE;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

/**
 * service for loading registration element from pdq.xml into CTRP.
 *
 * @author vrushali
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SecurityDomain(SECURITY_DOMAIN)
@RolesAllowed(SUBMITTER_ROLE)
public class PDQTrialRegistrationServiceBean extends AbstractPDQTrialServiceHelper
    implements PDQTrialRegistrationServiceBeanRemote {
    @EJB
    private OrganizationCorrelationServiceRemote orgCorrelationService;
    @EJB
    private ProtocolQueryServiceLocal protocolQueryService;

    private static final Logger LOG = Logger.getLogger(PDQTrialRegistrationServiceBean.class);
    private PAServiceUtils paServiceUtils = new PAServiceUtils();
    private Ii responsiblePartyContactIi = null;
    private static final Map<String, String> OVERALL_STATUS_MAP = new HashMap<String, String>();
    static {
        OVERALL_STATUS_MAP.put("NOT YET RECRUITING", StudyStatusCode.APPROVED.getCode());
        OVERALL_STATUS_MAP.put("RECRUITING ACTIVE", StudyStatusCode.ACTIVE.getCode());
        OVERALL_STATUS_MAP.put("NOT RECRUITING", StudyStatusCode.CLOSED_TO_ACCRUAL.getCode());
        OVERALL_STATUS_MAP.put("SUSPENDED", StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getCode());
        OVERALL_STATUS_MAP.put("WITHDRAWN", StudyStatusCode.WITHDRAWN.getCode());
        OVERALL_STATUS_MAP.put("COMPLETED", StudyStatusCode.COMPLETE.getCode());
        OVERALL_STATUS_MAP.put("TERMINATED", StudyStatusCode.ADMINISTRATIVELY_COMPLETE.getCode());

        OVERALL_STATUS_MAP.put("NO LONGER RECRUITING", StudyStatusCode.CLOSED_TO_ACCRUAL.getCode());
        OVERALL_STATUS_MAP.put("ACTIVE, NOT RECRUITING", StudyStatusCode.APPROVED.getCode());

        OVERALL_STATUS_MAP.put("RECRUITING", StudyStatusCode.ACTIVE.getCode());

    }

    /**
     * @param xmlUrl url
     * @param userName grid userName
     * @return Ii
     *@throws PAException on error
     * @throws IOException on error
     */   
    public Ii loadRegistrationElementFromPDQXml(URL xmlUrl, String userName) throws PAException, IOException {
        PDQRegistrationXMLParser parser = new PDQRegistrationXMLParser();
        parser.setUrl(xmlUrl);
        parser.parse();

        StudySiteDTO nctSite =
            getStudyIdentifierDTOs(parser.getStudyIdentifierMap().get(PAConstants.NCT_IDENTIFIER_TYPE),
                    PAConstants.NCT_IDENTIFIER_TYPE);
        StudyProtocolQueryDTO trial = getExistingTrial(nctSite);
        Ii resultIi = null;

        if (trial == null) {
            //No existing trial means we can go ahead and register.
            resultIi = registerTrial(parser, userName);
        } else {
            //Otherwise we're just going to update.
            resultIi = updateTrial(parser, IiConverter.convertToStudyProtocolIi(trial.getStudyProtocolId()), userName);
        }
        return resultIi;
    }

    /**
     * Registers a trial with the infomation provided by the PDQ XML.
     * @param parser the parsed XML
     * @param userName grid username
     * @return the identifier of the newly created trial
     * @throws PAException on error
     */
    private Ii registerTrial(PDQRegistrationXMLParser parser, String userName) throws PAException, IOException {
        OrganizationDTO sponserOrg = findOrCreateEntity(parser.getSponsorOrganizationDTO());
        StudySiteContactDTO studySiteContactDTO;
        try {
            studySiteContactDTO = getStudySiteContactDTO(
                    parser.getResponsiblePartyContact(), parser.getRespPartytype(), sponserOrg);
        } catch (EntityValidationException e) {
            throw new PAException("EntityValidationException during updateTrial " , e);
        } catch (CurationException e) {
            throw new PAException("CurationException during updateTrial " , e);
        }
        StudyProtocolDTO dto = getStudyProtocol(parser.getStudyProtocolDTO(), userName, null);
        dto.setStudySource(CdConverter.convertToCd(StudySourceCode.PDQ));
        return PaRegistry.getTrialRegistrationService().createCompleteInterventionalStudyProtocol(
                dto,
                getOverallStatusDTO(parser.getStudyOverallStatusDTO()),
                getStudyIndIde(parser.getStudyIndldeDTOs(), parser.getStudyIdentifierMap()), null,
                getDocumentDtos(parser.getUrl()), findOrCreateEntity(parser.getLeadOrganizationDTO()),
                findOrCreateEntity(parser.getPrincipalInvestigatorDTO()),
                sponserOrg,
                parser.getLeadOrganizationSiteIdentifierDTO(), loadStudyIdentifierDTOs(parser.getStudyIdentifierMap()),
                getStudyContactDTO(parser.getResponsiblePartyContact(), parser.getRespPartytype()), 
                studySiteContactDTO, 
                getSummary4OrganizationDTO(),
                getSummary4StudyResourcingDTO(), responsiblePartyContactIi,
                getStudyRegulatoryAuthDTO(parser.getRegAuthMap(), parser.getStudyIndldeDTOs()),
                BlConverter.convertToBl(Boolean.TRUE));
    }

    /**
     * Updates a trial with the information provided by the PDQ XML.
     * @param parser the parsed XML
     * @param studyProtocolIi the id of the trial to update
     * @param userName grid username
     * @return the identifier of the newly created trial
     * @throws PAException on error
     * @throws IOException on error
     */
    private Ii updateTrial(PDQRegistrationXMLParser parser, Ii studyProtocolIi, String userName) throws PAException,
    IOException {
        StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);

        //Generate the tsr report to attach to newly created trial.
        ByteArrayOutputStream tsrReport =
            PaRegistry.getTSRReportGeneratorServiceLocal().generateRtfTsrReport(spDTO.getIdentifier());
        DocumentDTO tsrDoc = getDocument(DocumentTypeCode.TSR, "tsrreport.rtf", tsrReport.toByteArray());
        List<DocumentDTO> allDocuments = getDocumentDtos(parser.getUrl());
        allDocuments.add(tsrDoc);

        //This is the old NCI ID which will replace the NCI ID of the newly created trial.
        Ii nciId = PAUtil.getAssignedIdentifier(spDTO);

        //Delete the old trial
        PaRegistry.getStudyProtocolService().deleteStudyProtocol(studyProtocolIi);
        
        //Clean the existing trial doc folder
        getPaServiceUtils().handleUpdatedTrialDocuments(nciId);
        
        //Then create the new trial
        OrganizationDTO sponserOrg = findOrCreateEntity(parser.getSponsorOrganizationDTO());
        StudySiteContactDTO studySiteContactDTO;
        try {
            studySiteContactDTO = getStudySiteContactDTO(
                    parser.getResponsiblePartyContact(), parser.getRespPartytype(), sponserOrg);
        } catch (EntityValidationException e) {
            throw new PAException("EntityValidationException during updateTrial " , e);
        } catch (CurationException e) {
            throw new PAException("CurationException during updateTrial " , e);
        }
        
        Ii newSpId = PaRegistry.getTrialRegistrationService().createCompleteInterventionalStudyProtocol(
                getStudyProtocol(parser.getStudyProtocolDTO(), userName, nciId),
                getOverallStatusDTO(parser.getStudyOverallStatusDTO()),
                getStudyIndIde(parser.getStudyIndldeDTOs(), parser.getStudyIdentifierMap()), null,
                allDocuments,
                findOrCreateEntity(parser.getLeadOrganizationDTO()),
                findOrCreateEntity(parser.getPrincipalInvestigatorDTO()),
                sponserOrg,
                parser.getLeadOrganizationSiteIdentifierDTO(),
                loadStudyIdentifierDTOs(parser.getStudyIdentifierMap()),
                getStudyContactDTO(parser.getResponsiblePartyContact(), parser.getRespPartytype()), 
                studySiteContactDTO, 
                getSummary4OrganizationDTO(),
                getSummary4StudyResourcingDTO(), responsiblePartyContactIi,
                getStudyRegulatoryAuthDTO(parser.getRegAuthMap(), parser.getStudyIndldeDTOs()),
                BlConverter.convertToBl(Boolean.TRUE));

        return newSpId;
    }

    /**
     * retrieves an existing trial with a study site with the give NCT identifier.
     * @param studySiteDTO the study site with a NCT identifier
     * @throws PAException on error
     * @return the trial with the given nct identifier
     */
    private StudyProtocolQueryDTO getExistingTrial(StudySiteDTO studySiteDTO) throws PAException {
        StudyProtocolQueryCriteria crit = new StudyProtocolQueryCriteria();
        crit.setNctNumber(StConverter.convertToString(studySiteDTO.getLocalStudyProtocolIdentifier()));
        StudyProtocolQueryDTO result = null;

        List<StudyProtocolQueryDTO> results = protocolQueryService.getStudyProtocolByCriteria(crit);
        if (CollectionUtils.isNotEmpty(results)) {
            result = results.get(0);
        }
        return result;
    }

    /**
     * @param studyIndldeDTOs
     * @return
     */
    private List<StudyIndldeDTO> getStudyIndIde(List<StudyIndldeDTO> studyIndldeDTOs,
            Map<String, String>studyIdentifierMap) {
        List<StudyIndldeDTO> returnIndIdeList = null;
        if (CollectionUtils.isNotEmpty(studyIndldeDTOs)) {
            returnIndIdeList = new ArrayList<StudyIndldeDTO>();
            for (StudyIndldeDTO indDTO : studyIndldeDTOs) {
                if (StringUtils.equalsIgnoreCase(indDTO.getGrantorCode().getCode(), GrantorCode.CDRH.getCode())) {
                    indDTO.setIndldeTypeCode(CdConverter.convertToCd(IndldeTypeCode.IDE));
                } else {
                    indDTO.setIndldeTypeCode(CdConverter.convertToCd(IndldeTypeCode.IND));
                }
                if (StringUtils.isNotEmpty(studyIdentifierMap.get(PAConstants.CTEP_IDENTIFIER_TYPE))) {
                    indDTO.setHolderTypeCode(CdConverter.convertToCd(HolderTypeCode.NCI));
                    indDTO.setNciDivProgHolderCode(CdConverter.convertToCd(NciDivisionProgramCode.CTEP));
                }
                if (StringUtils.isNotEmpty(studyIdentifierMap.get(PAConstants.DCP_IDENTIFIER_TYPE))) {
                    indDTO.setHolderTypeCode(CdConverter.convertToCd(HolderTypeCode.NCI));
                    indDTO.setNciDivProgHolderCode(CdConverter.convertToCd(NciDivisionProgramCode.DCP));
                }
                indDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.FALSE));
                returnIndIdeList.add(indDTO);
            }
        }
        return returnIndIdeList;
    }

    /**
     * @param studyProtocolDTO
     * @param nciId 
     * @return
     */
    private StudyProtocolDTO getStudyProtocol(StudyProtocolDTO studyProtocolDTO, String userName, Ii nciId) {
        studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(userName));
        studyProtocolDTO.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(Boolean.TRUE));
        if (nciId != null) {
            studyProtocolDTO.getSecondaryIdentifiers().getItem().add(nciId);
        }
        return studyProtocolDTO;
    }

    /**
     * @param studyOverallStatusDTO
     * @return
     */
    private StudyOverallStatusDTO getOverallStatusDTO(StudyOverallStatusDTO studyOverallStatusDTO) {
        StudyOverallStatusDTO statusDTO = new StudyOverallStatusDTO();
        String studySiteCode = OVERALL_STATUS_MAP.get(StringUtils.upperCase(
                studyOverallStatusDTO.getStatusCode().getCode()));
        statusDTO.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByCode(studySiteCode)));
        statusDTO.setStatusDate(studyOverallStatusDTO.getStatusDate());
        //Need to provide an xml element for status reason
        statusDTO.setReasonText(StConverter.convertToSt("Pending Reason."));
        return statusDTO;
    }

    /**
     * @param responsiblePartyContact
     * @return
     */
    private StudyContactDTO getStudyContactDTO(PersonDTO responsiblePartyContact, String respPartytype) {
        StudyContactDTO contactDTO = null;
        if (respPartytype != null && !respPartytype.equalsIgnoreCase("organization") || respPartytype == null) {
            contactDTO = new StudyContactDTO();
            if (!PAUtil.isDSetTelNull(responsiblePartyContact.getTelecomAddress())) {
                contactDTO.setTelecomAddresses(responsiblePartyContact.getTelecomAddress());
            } else {
                contactDTO.setTelecomAddresses(getUnknownTelecomeAddress());
            }
        }
        return contactDTO;
    }
    
    /**
     * @param responsiblePartyContact
     * @param sponserOrg 
     * @return
     */
    private StudySiteContactDTO getStudySiteContactDTO(PersonDTO responsiblePartyContact, 
            String respPartytype, OrganizationDTO sponserOrg) throws EntityValidationException, CurationException {
        StudySiteContactDTO iso = null;
        if (respPartytype != null && respPartytype.equalsIgnoreCase("organization")) {
            iso = new StudySiteContactDTO();
            iso.setTelecomAddresses(responsiblePartyContact.getTelecomAddress());
            OrganizationalContactDTO contactDTO = new OrganizationalContactDTO();
            contactDTO.setScoperIdentifier(sponserOrg.getIdentifier());
            contactDTO.setTitle(StConverter.convertToSt(
                    EnPnConverter.convertToLastCommaFirstName(responsiblePartyContact.getName())));
            contactDTO.setTelecomAddress(responsiblePartyContact.getTelecomAddress());
            contactDTO.setTypeCode(CdConverter.convertStringToCd("Responsible Party"));
            List<OrganizationalContactDTO> isoDtoList = new ArrayList<OrganizationalContactDTO>();
            isoDtoList = PoRegistry.getOrganizationalContactCorrelationService().search(contactDTO);
            if (isoDtoList.isEmpty()) {
                // create the title
                responsiblePartyContactIi = 
                        PoRegistry.getOrganizationalContactCorrelationService().createCorrelation(contactDTO);
            } else {
                responsiblePartyContactIi = DSetConverter.convertToIi(isoDtoList.get(0).getIdentifier());
            }
            
        } 
        return iso;
    }


    /**
     * @return
     */
    private StudyResourcingDTO getSummary4StudyResourcingDTO() {
        StudyResourcingDTO resourcingDTO = new StudyResourcingDTO();
        resourcingDTO.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode.NATIONAL));
        return resourcingDTO;
    }

    /**
     * @return
     * @throws PAException
     */
    private List<OrganizationDTO> getSummary4OrganizationDTO() throws PAException {
        OrganizationDTO summ4OrgDTO = new OrganizationDTO();
        summ4OrgDTO.setName(EnOnConverter.convertToEnOn("unknown"));
        summ4OrgDTO.setTelecomAddress(DSetConverter.convertListToDSet(Arrays.asList("unknown@unknown.com"),
                DSetConverter.TYPE_EMAIL, null));
        summ4OrgDTO.setPostalAddress(AddressConverterUtil.create("UNKNOWN", "UNKNOWN", "UNKNOWN", "MD", "00000",
        "USA"));
        summ4OrgDTO = paServiceUtils.findOrCreateEntity(summ4OrgDTO);
        List<OrganizationDTO> summary4OrganizationList = new ArrayList<OrganizationDTO>();
        summary4OrganizationList.add(summ4OrgDTO);
        return summary4OrganizationList;
    }
    /**
     * @param urlXML
     * @return
     * @throws IOException
     */
    private List<DocumentDTO> getDocumentDtos(URL urlXML) throws IOException {
        List<DocumentDTO> docList = new ArrayList<DocumentDTO>();
        String irbdata = "This document is a placeholder for the protocol IRB document for this trial.";
        String protocolData = "This document is a placeholder for the protocol document for this trial.";
        docList.add(getDocument(DocumentTypeCode.PROTOCOL_DOCUMENT, "Protocol_Document_Place_Holder.doc",
                protocolData.getBytes()));
        docList.add(getDocument(DocumentTypeCode.IRB_APPROVAL_DOCUMENT, "Protocol_IRB_Document_Place_Holder.doc",
                irbdata.getBytes()));
        docList.add(getDocument(DocumentTypeCode.OTHER, "pdq.xml", IOUtils.toByteArray(urlXML.openStream())));
        return docList;
    }

    /**
     * @return
     */
    private DocumentDTO getDocument(DocumentTypeCode typeCode, String documentName, byte[] data) {
        DocumentDTO doc = new DocumentDTO();
        doc.setTypeCode(CdConverter.convertToCd(typeCode));
        doc.setFileName(StConverter.convertToSt(documentName));
        doc.setText(EdConverter.convertToEd(data));
        return doc;
    }

    /**
     *
     * @param regAuthmap  regAuthDTO
     * @return studyRegulatoryDTO
     */
    private StudyRegulatoryAuthorityDTO getStudyRegulatoryAuthDTO(Map<String, String> regAuthmap,
            List<StudyIndldeDTO> indDtos) {
        StudyRegulatoryAuthorityDTO studyRegAuthDTO = new StudyRegulatoryAuthorityDTO();
        try {
            String authorityName = regAuthmap.get("AuthorityName");
            String countryName = regAuthmap.get("CountryName");
            if (StringUtils.isEmpty(authorityName) && StringUtils.isEmpty(countryName)) {
                if (CollectionUtils.isEmpty(indDtos)) {
                    authorityName = "Institutional Review Board";
                } else {
                    authorityName = "Food and Drug Administration";
                }
                countryName = "United States";
            }
            Long regulatoryId = PaRegistry.getRegulatoryInformationService().getRegulatoryAuthorityId(authorityName,
                    countryName);
            studyRegAuthDTO.setRegulatoryAuthorityIdentifier(IiConverter.convertToIi(regulatoryId));
        } catch (PAException e) {
            LOG.error(e);
        }
        return studyRegAuthDTO;
    }
    /**
     * @param id id
     * @param identifierType type
     * @throws PAException on error
     * @return studySiteDTO
     */
    public StudySiteDTO getStudyIdentifierDTOs(String id, String identifierType) throws PAException {
        StudySiteDTO studySiteIdDTO =  new StudySiteDTO();
        studySiteIdDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(id));
        String poOrgId = orgCorrelationService.getPOOrgIdentifierByIdentifierType(identifierType);
        Ii nctROIi = orgCorrelationService.getPoResearchOrganizationByEntityIdentifier(
                IiConverter.convertToPoOrganizationIi(String.valueOf(poOrgId)));
        studySiteIdDTO.setResearchOrganizationIi(nctROIi);
        return studySiteIdDTO;
    }

    /**
     *
     * @param map map of study id
     * @return list of study Site DTO
     * @throws PAException on error
     */
    public List<StudySiteDTO> loadStudyIdentifierDTOs(Map<String, String> map) throws PAException {
        List<StudySiteDTO> studyIdentifierDTOs = new ArrayList<StudySiteDTO>();
        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
            String idType = iter.next();
            studyIdentifierDTOs.add(getStudyIdentifierDTOs(map.get(idType), idType));
        }
        return studyIdentifierDTOs;
    }

    /**
     * @param paServiceUtils the paServiceUtils to set
     */
    @Override
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

    /**
     * @return the paServiceUtils
     */
    @Override
    public PAServiceUtils getPaServiceUtils() {
        return paServiceUtils;
    }

    /**
     * @param orgCorrelationService the orgCorrelationService to set
     */
    public void setOrgCorrelationService(OrganizationCorrelationServiceRemote orgCorrelationService) {
        this.orgCorrelationService = orgCorrelationService;
    }

    /**
     * @return the orgCorrelationService
     */
    public OrganizationCorrelationServiceRemote getOrgCorrelationService() {
        return orgCorrelationService;
    }

    /**
     * @return the protocol query service
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }

    /**
     * @param protocolQueryService the protocol query service to set
     */
    public void setProtocolQueryService(ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @return the responsiblePartyContactIi
     */
    public Ii getResponsiblePartyContactIi() {
        return responsiblePartyContactIi;
    }

    /**
     * @param responsiblePartyContactIi the responsiblePartyContactIi to set
     */
    public void setResponsiblePartyContactIi(Ii responsiblePartyContactIi) {
        this.responsiblePartyContactIi = responsiblePartyContactIi;
    }
}

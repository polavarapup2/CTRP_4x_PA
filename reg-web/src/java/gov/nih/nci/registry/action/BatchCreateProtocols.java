/***
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Clinical Trials Protocol Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate, execute, reproduce,  copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the foregoing rights  set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties.For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License is  granted  at no  charge  to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions, You have no right to download,  copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code
 * form must reproduce the above copyright notice, this list of  conditions  and the  disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any, must include the  following acknowledgment:
 * This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
 * shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
 * appear.
 *
 * 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
 * caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
 * as required to comply with the terms of this License.
 *
 * 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
 * programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
 * party proprietary programs, You agree  that You are  solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation to  secure  any  required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction, and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO, PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnPn;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO.ResponsiblePartyType;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.registry.dto.OrganizationBatchDTO;
import gov.nih.nci.registry.dto.PersonBatchDTO;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.dto.SummaryFourSponsorsWebDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

/**
 * @author Vrushali
 *
 */
public class BatchCreateProtocols {
    private static final Logger LOG = Logger.getLogger(BatchCreateProtocols.class);
    private int sucessCount = 0;
    private int failedCount = 0;
    private String delayedPostingIndWarning = ""; 
    private static final String FAILED = "Failed:";
    
    private final String protocolDocumentCode = DocumentTypeCode.PROTOCOL_DOCUMENT.getCode();
    private final String irbApprovalDocCode = DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode();
    private final String otherDocCode = DocumentTypeCode.OTHER.getCode();
    private final String informedConsentDocumentCode = DocumentTypeCode.INFORMED_CONSENT_DOCUMENT.getCode();
    private final String participatingSitesCode = DocumentTypeCode.PARTICIPATING_SITES.getCode();

    /**
     *
     * @param dtoList list
     * @param folderPath path
     * @param userName name
     * @return map
     */
    public Map<String, String> createProtocols(List<StudyProtocolBatchDTO> dtoList, String folderPath, 
                                               String userName) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (dtoList == null || dtoList.size() < 1) {
            map.put("Failed Trial Count", String.valueOf(failedCount));
            map.put("Success Trial Count", String.valueOf(sucessCount));
            return map;
        }
        StringBuilder result = new StringBuilder();
        for (StudyProtocolBatchDTO batchDto : dtoList) {
            // check if the record qualifies to be added
            // validate the record
            if (batchDto != null) {
                result = new StringBuilder();
                TrialBatchDataValidator validator = new TrialBatchDataValidator();
                if (StringUtils.isBlank(batchDto.getLeadOrgPOId())) {
                    OrganizationBatchDTO leadOrgDto = validator.buildLeadOrgDto(batchDto);
                    try {
                        Ii orgIdIi = lookUpOrgs(leadOrgDto);
                        batchDto.setLeadOrgPOId(IiConverter.convertToString(orgIdIi));
                    } catch (Exception e) {
                        LOG.debug(e);
                    }
                }
                result.append(validator.validateBatchDTO(batchDto));
                if (result.length() < 1) {
                    result.append(buildProtocol(batchDto, folderPath, userName));
                } else {
                    result.insert(0, FAILED);
                    failedCount += 1;
                }
                map.put(batchDto.getUniqueTrialId(), result.toString());
            }
        }
        map.put("Failed Trial Count", String.valueOf(failedCount));
        map.put("Success Trial Count", String.valueOf(sucessCount));
        map.put("Delayed Posting Indicator warning", delayedPostingIndWarning);
        return map;
    }

    /**
     *
     * @param dto dto
     * @param folderPath path
     * @param userName name
     * @return protocol Id
     * @throws PAException ex
     */
    private String buildProtocol(StudyProtocolBatchDTO dto, String folderPath, String userName) {
        Ii studyProtocolIi = null;
        String protocolAssignedId = "";
        try {
            if (!dto.getSubmissionType().equalsIgnoreCase("U")) {
                studyProtocolIi = submitOriginalOrAmendment(dto, folderPath, userName);
            } else {
                studyProtocolIi = submitUpdate(dto, folderPath, userName);
            }
            if (ISOUtil.isIiNull(studyProtocolIi)) {
                ++failedCount;
            } else {
                String strMsg = " Successfully Registered with NCI Identifier "; // get the protocol
                if (dto.getSubmissionType().equalsIgnoreCase("U")) {
                    strMsg = " Successfully Updated NCI Identifier ";

                }
                ++sucessCount;
                String assignedIdExtension = PAUtil.getAssignedIdentifierExtension(PaRegistry.getStudyProtocolService()
                        .getStudyProtocol(studyProtocolIi));
                protocolAssignedId = strMsg + assignedIdExtension;
                if (!dto.getSubmissionType().equalsIgnoreCase("U") && !delayedPostingIndWarning.isEmpty()) {
                      delayedPostingIndWarning = delayedPostingIndWarning + ":" + assignedIdExtension + " ";
                }
            }
        } catch (PAException ex) {
            ++failedCount;
            LOG.error("buildprotocol exception", ex);
            protocolAssignedId = FAILED + ex.getMessage() + "\n";
        } catch (Exception exc) {
            ++failedCount;
            protocolAssignedId = FAILED + exc.getMessage() + "\n";
            LOG.error("buildprotocol exception", exc);
        }
        LOG.info("response " + protocolAssignedId);
        return protocolAssignedId;
    }

    private Ii submitUpdate(StudyProtocolBatchDTO dto, String folderPath, String userName) throws PAException,
            NullifiedRoleException, NullifiedEntityException, URISyntaxException, EntityValidationException,
            CurationException, IOException, ParseException {
        TrialUtil util = new TrialUtil();
        TrialDTO trialDTO = new TrialDTO();

        StudyProtocolQueryCriteria viewCriteria = new StudyProtocolQueryCriteria();
        viewCriteria.setNciIdentifier(dto.getNciTrialIdentifier());
        List<StudyProtocolQueryDTO> listofDto =
                PaRegistry.getProtocolQueryService().getStudyProtocolByCriteria(viewCriteria);
        if (listofDto.isEmpty() || listofDto.size() > 1) {
            throw new PAException("mutliple trial or no trial found for given NCI Trial Identifier.\n");
        }
        StudyProtocolQueryDTO querydto = listofDto.get(0);
        if (querydto.isProprietaryTrial()) {
            throw new PAException("Proprietary trials Update not supported. ");
        }
        Ii studyProtocolIi = IiConverter.convertToIi(listofDto.get(0).getStudyProtocolId());

        trialDTO = getTrialDTOForUpdate(dto, folderPath, studyProtocolIi);

        StudyProtocolDTO studyProtocolDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        if (studyProtocolDTO.getSecondaryIdentifiers() != null
                && studyProtocolDTO.getSecondaryIdentifiers().getItem() != null) {
            List<Ii> listIi = new ArrayList<Ii>();
            for (Ii ii : studyProtocolDTO.getSecondaryIdentifiers().getItem()) {
                if (!IiConverter.STUDY_PROTOCOL_ROOT.equals(ii.getRoot())) {
                    listIi.add(ii);
                }
            }
            trialDTO.setSecondaryIdentifierList(listIi);
        }
        util.addSecondaryIdentifiers(studyProtocolDTO, trialDTO);
        util.updateStudyProtcolDTO(studyProtocolDTO, trialDTO);

        studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(userName));
        studyProtocolDTO.setIdentifier(IiConverter.convertToStudyProtocolIi(Long.parseLong(studyProtocolDTO
                .getIdentifier().getExtension())));
        StudyOverallStatusDTO overallStatusDTO = util.convertToStudyOverallStatusDTO(trialDTO);
        overallStatusDTO.setStudyProtocolIdentifier(studyProtocolIi);
        
        List<DocumentDTO> documentDTOs = util.convertToISODocumentList(trialDTO.getDocDtos());
        
        List<StudyIndldeDTO> studyIndldeDTOs = util.convertISOINDIDEList(trialDTO.getIndIdeDtos(), null);
        if (studyIndldeDTOs != null && !studyIndldeDTOs.isEmpty()) {
            for (Iterator<StudyIndldeDTO> it = studyIndldeDTOs.iterator(); it.hasNext();) {
                StudyIndldeDTO indIdeDto = it.next();
                try {
                    PaRegistry.getStudyIndldeService().validate(indIdeDto);
                } catch (PADuplicateException dupEx) {
                    it.remove();
                }
            }
        }
        List<StudyResourcingDTO> studyResourcingDTOs = util.convertISOGrantsList(trialDTO.getFundingDtos());
        if (studyResourcingDTOs != null && !studyResourcingDTOs.isEmpty()) {
            for (Iterator<StudyResourcingDTO> it = studyResourcingDTOs.iterator(); it.hasNext();) {
                StudyResourcingDTO studyResourcingDTO = it.next();
                try {
                    PaRegistry.getStudyResourcingService().validate(studyResourcingDTO);
                } catch (PADuplicateException dupEx) {
                    it.remove();
                }
            }
        }
        List<StudySiteDTO> studyIdentifierDTOs = new ArrayList<StudySiteDTO>();
        studyIdentifierDTOs.add(util.convertToNCTStudySiteDTO(trialDTO, null));        

        // get the values from db and update only those are needed and then convert
        PaRegistry.getTrialRegistrationService().update(studyProtocolDTO, overallStatusDTO, studyIdentifierDTOs, null, 
                studyResourcingDTOs, documentDTOs, null, null, null, null, null, 
                null, null, null, null, BlConverter.convertToBl(Boolean.TRUE));
        
        return studyProtocolIi;
    }

    private TrialDTO getTrialDTOForUpdate(StudyProtocolBatchDTO batchDto, String folderPath, Ii studyProtocolIi)
            throws PAException, NullifiedRoleException, NullifiedEntityException, URISyntaxException,
            EntityValidationException, CurationException, IOException, ParseException { 

        TrialDTO trialDTO = new TrialDTO();
        TrialUtil util = new TrialUtil();
        util.getTrialDTOFromDb(studyProtocolIi, trialDTO);
        // update the trial DTO with new values
        trialDTO.setReason(batchDto.getReasonForStudyStopped());
        trialDTO.setStartDate(batchDto.getStudyStartDate());
        trialDTO.setStartDateType(batchDto.getStudyStartDateType());
       
        StatusDto status = new StatusDto();
        status.setStatusDate(DateUtils.parseDate(
                batchDto.getCurrentTrialStatusDate(), new String[] {
                        "MM/dd/yyyy", "MM/dd/yy" }));
        status.setStatusCode(StudyStatusCode.getByCode(batchDto
                .getCurrentTrialStatus()) != null ? StudyStatusCode.getByCode(
                batchDto.getCurrentTrialStatus()).name() : null);
        trialDTO.setStatusHistory(Arrays.asList(status));
        
        trialDTO.setPrimaryCompletionDate(batchDto.getPrimaryCompletionDate());
        trialDTO.setPrimaryCompletionDateType(batchDto.getPrimaryCompletionDateType());
        
        trialDTO.setDocDtos(getDocDTOListForUpdate(batchDto, folderPath, studyProtocolIi.getExtension())); // add doc to
                                                                                                             // the dto
        trialDTO.setIndIdeDtos(getIndsListForUpdate(batchDto, studyProtocolIi.getExtension())); // add ind
        trialDTO.setFundingDtos(getFundingListForUpdate(batchDto, studyProtocolIi.getExtension())); // add grants
        trialDTO.setSecondaryIdentifierAddList(batchDto.getOtherTrialIdentifiers());
        return trialDTO;
    }

    private List<TrialFundingWebDTO> getFundingListForUpdate(StudyProtocolBatchDTO batchDto, String studyProtocolId) {
        List<TrialFundingWebDTO> fundingList = new ArrayList<TrialFundingWebDTO>();
        TrialBatchDataValidator dataValidator = new TrialBatchDataValidator();
        fundingList = dataValidator.convertToGrantList(batchDto);
        for (TrialFundingWebDTO dto : fundingList) {
            dto.setStudyProtocolId(studyProtocolId);
        }
        return fundingList;
    }

    private List<TrialIndIdeDTO> getIndsListForUpdate(StudyProtocolBatchDTO batchDto, String studyProtocolId) {
        List<TrialIndIdeDTO> indList = new ArrayList<TrialIndIdeDTO>();
        TrialBatchDataValidator dataValidator = new TrialBatchDataValidator();
        indList = dataValidator.convertIndsToList(batchDto);
        for (TrialIndIdeDTO dto : indList) {
            dto.setStudyProtocolId(studyProtocolId);
        }
        return indList;
    }

    @SuppressWarnings("deprecation")
    private List<TrialDocumentWebDTO> getDocDTOListForUpdate(StudyProtocolBatchDTO batchDto, String folderPath,
            String studyProtocolId) throws IOException, PAException { 
        
        List<DocumentDTO> docsFromDB =
                PaRegistry.getDocumentService().getDocumentsByStudyProtocol(IiConverter.convertToIi(studyProtocolId));
        
        Ii protocolDocId = null;
        Ii irbDocId = null;
        Ii iConsentDocId = null;
        Ii partDocId = null;
        
        for (DocumentDTO doc : docsFromDB) {
            if (protocolDocumentCode.equals(CdConverter.convertCdToString(doc.getTypeCode()))) {
                protocolDocId = doc.getIdentifier();
            }
            if (irbApprovalDocCode.equals(CdConverter.convertCdToString(doc.getTypeCode()))) {
                irbDocId = doc.getIdentifier();
            }
            if (informedConsentDocumentCode.equals(CdConverter.convertCdToString(doc.getTypeCode()))) {
                iConsentDocId = doc.getIdentifier();
            }
            if (participatingSitesCode.equals(CdConverter.convertCdToString(doc.getTypeCode()))) {
                partDocId = doc.getIdentifier();
            }
        }
        
        List<TrialDocumentWebDTO> docDTOList = new ArrayList<TrialDocumentWebDTO>();
        if (StringUtils.isNotEmpty(batchDto.getProtcolDocumentFileName())) {
            docDTOList.add(handleDocument(batchDto, folderPath, studyProtocolId,
                    protocolDocId, protocolDocumentCode));
        } 
        if (StringUtils.isNotEmpty(batchDto.getInformedConsentDocumentFileName())) {
            docDTOList.add(handleDocument(batchDto, folderPath, studyProtocolId,
                    iConsentDocId, informedConsentDocumentCode));
        }        
        if (StringUtils.isNotEmpty(batchDto.getParticipatinSiteDocumentFileName())) {
            docDTOList.add(handleDocument(batchDto, folderPath, studyProtocolId,
                    partDocId, participatingSitesCode));
        } 
        if (StringUtils.isNotEmpty(batchDto.getIrbApprovalDocumentFileName())) {
            docDTOList.add(handleDocument(batchDto, folderPath, studyProtocolId,
                    irbDocId, irbApprovalDocCode));
        }
        if (StringUtils.isNotEmpty(batchDto.getOtherTrialRelDocumentFileName())) {
            docDTOList.add(handleDocument(batchDto, folderPath, studyProtocolId,
                    null, otherDocCode));
        }
        return docDTOList;
    }

    private TrialDocumentWebDTO handleDocument(StudyProtocolBatchDTO batchDto, String folderPath, // NOPMD
            String studyProtocolId, Ii docId, String documentCode) throws IOException, PAException {       
    
        TrialDocumentWebDTO webDto = new TrialDocumentWebDTO();
        TrialUtil util = new TrialUtil();
        File doc;
        String fileBeingProcessed = ""; 
        try {
            if (documentCode.equals(protocolDocumentCode)) {
                fileBeingProcessed = batchDto.getProtcolDocumentFileName();
                doc = new File(folderPath +  fileBeingProcessed);
                webDto = util.convertToDocumentDTO(protocolDocumentCode,
                        batchDto.getProtcolDocumentFileName(), doc);
            } else if (documentCode.equals(informedConsentDocumentCode)) {
                fileBeingProcessed = batchDto.getInformedConsentDocumentFileName();
                doc = new File(folderPath + fileBeingProcessed);
                webDto = util.convertToDocumentDTO(informedConsentDocumentCode,
                        batchDto.getInformedConsentDocumentFileName(), doc);
            } else if (documentCode.equals(participatingSitesCode)) {
                fileBeingProcessed = batchDto.getParticipatinSiteDocumentFileName();
                doc = new File(folderPath + fileBeingProcessed);
                webDto = util.convertToDocumentDTO(participatingSitesCode,
                        batchDto.getParticipatinSiteDocumentFileName(), doc);
            } else if (documentCode.equals(irbApprovalDocCode)) {
                fileBeingProcessed = batchDto.getIrbApprovalDocumentFileName();
                doc = new File(folderPath + fileBeingProcessed);
                webDto = util.convertToDocumentDTO(irbApprovalDocCode,
                        batchDto.getIrbApprovalDocumentFileName(), doc);
            } else if (documentCode.equals(otherDocCode)) {
                fileBeingProcessed = batchDto.getOtherTrialRelDocumentFileName();
                doc = new File(folderPath + fileBeingProcessed);
                webDto = util.convertToDocumentDTO(otherDocCode,
                        batchDto.getOtherTrialRelDocumentFileName(), doc);
            }
        } catch (FileNotFoundException fnfEx) {
            throw new PAException(//NOPMD
                    "No accompanying zip file containing the file "
                            + fileBeingProcessed + " mentioned in the trial data file.\n");
        }

        if (!ISOUtil.isIiNull(docId)) {
            webDto.setId(docId.getExtension());
        }
        webDto.setStudyProtocolId(studyProtocolId);
        return webDto;
    }    

    @SuppressWarnings("deprecation")
    private Ii submitOriginalOrAmendment(StudyProtocolBatchDTO dto, String folderPath, String userName)
            throws NullifiedEntityException, PAException, URISyntaxException, EntityValidationException,
            CurationException, IOException, ParseException {
        Ii studyProtocolIi = null;

        TrialUtil util = new TrialUtil();
        StudyProtocolDTO studyProtocolDTO = null;
        TrialDTO trialDTO = convertToTrialDTO(dto, folderPath);
       
        if (dto.getSubmissionType().equalsIgnoreCase("O") && StringUtils.isEmpty(trialDTO.getAssignedIdentifier())) {
            trialDTO.setSecondaryIdentifierList(dto.getOtherTrialIdentifiers());
        } else if (dto.getSubmissionType().equalsIgnoreCase("A")) {
            trialDTO.setSecondaryIdentifierAddList(dto.getOtherTrialIdentifiers());
        }
       
        if (trialDTO.getTrialType().equals("Interventional")) {
            studyProtocolDTO = util.convertToInterventionalStudyProtocolDTO(trialDTO);
        } else {
            studyProtocolDTO = util.convertToInterventionalStudyProtocolDTO(trialDTO);
        }
        studyProtocolDTO.setStudySource(CdConverter.convertToCd(StudySourceCode.BATCH));
        studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(userName));
        StudyOverallStatusDTO overallStatusDTO = util.convertToStudyOverallStatusDTO(trialDTO);
        List<DocumentDTO> documentDTOs = util.convertToISODocumentList(trialDTO.getDocDtos());
        OrganizationDTO leadOrgDTO = util.convertToLeadOrgDTO(trialDTO);
        PersonDTO principalInvestigatorDTO = util.convertToLeadPI(trialDTO);
        StudySiteDTO leadOrgSiteIdDTO = util.convertToleadOrgSiteIdDTO(trialDTO);
        List<OrganizationDTO> summary4orgDTO = util.convertToSummary4OrgDTO(trialDTO);
        StudyResourcingDTO summary4studyResourcingDTO = util.convertToSummary4StudyResourcingDTO(trialDTO);
        
        OrganizationDTO sponsorOrgDTO = null;
        StudyRegulatoryAuthorityDTO studyRegAuthDTO = new StudyRegulatoryAuthorityDTO();
        if (trialDTO.isXmlRequired()) {
            sponsorOrgDTO = util.convertToSponsorOrgDTO(trialDTO);            
            // Regulatory authority
            Long regAuthId =
                    PaRegistry.getRegulatoryInformationService().getRegulatoryAuthorityId(dto.getOversightOrgName(),
                            dto.getOversightAuthorityCountry());
            studyRegAuthDTO.setRegulatoryAuthorityIdentifier(IiConverter.convertToIi(regAuthId));
        }

        List<StudyIndldeDTO> studyIndldeDTOs = util.convertISOINDIDEList(trialDTO.getIndIdeDtos(), null);
        List<StudyResourcingDTO> studyResourcingDTOs = util.convertISOGrantsList(trialDTO.getFundingDtos());
        List<StudySiteDTO> studyIdentifierDTOs = new ArrayList<StudySiteDTO>();
        studyIdentifierDTOs.add(util.convertToNCTStudySiteDTO(trialDTO, studyProtocolIi));
        studyIdentifierDTOs.add(util.convertToCTEPStudySiteDTO(trialDTO, studyProtocolIi));
        studyIdentifierDTOs.add(util.convertToDCPStudySiteDTO(trialDTO, studyProtocolIi));
        
        ResponsiblePartyDTO partyDTO = convertToResponsiblePartyDTO(dto, principalInvestigatorDTO, sponsorOrgDTO);
        
        if (dto.getSubmissionType().equalsIgnoreCase("O") && StringUtils.isEmpty(trialDTO.getAssignedIdentifier())) {
            studyProtocolIi =
                    PaRegistry.getTrialRegistrationService().createCompleteInterventionalStudyProtocol(studyProtocolDTO,
                            overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs, documentDTOs, leadOrgDTO,
                            principalInvestigatorDTO, sponsorOrgDTO, partyDTO, leadOrgSiteIdDTO, studyIdentifierDTOs,
                            summary4orgDTO, summary4studyResourcingDTO,
                            studyRegAuthDTO, BlConverter.convertToBl(Boolean.TRUE));
        } else if (dto.getSubmissionType().equalsIgnoreCase("A")) {
            // get the Identifier of study protocol by giving nci identifier
            StudyProtocolQueryCriteria viewCriteria = new StudyProtocolQueryCriteria();
            viewCriteria.setNciIdentifier(trialDTO.getAssignedIdentifier());
            List<StudyProtocolQueryDTO> listofDto =
                    PaRegistry.getProtocolQueryService().getStudyProtocolByCriteria(viewCriteria);
            if (listofDto.isEmpty() || listofDto.size() > 1) {
                throw new PAException("mutliple trial or no trial found for given NCI Trial Identifier.\n");
            }
            trialDTO.setIdentifier(listofDto.get(0).getStudyProtocolId().toString());
            // ignore duplicate inds and grants
            if (studyIndldeDTOs != null && !studyIndldeDTOs.isEmpty()) {
                for (Iterator<StudyIndldeDTO> it = studyIndldeDTOs.iterator(); it.hasNext();) {
                    StudyIndldeDTO indIdeDto = it.next();
                    try {
                        indIdeDto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(Long.valueOf(trialDTO
                                .getIdentifier())));
                        PaRegistry.getStudyIndldeService().validate(indIdeDto);
                    } catch (PADuplicateException dupEx) {
                        it.remove();
                    }
                }
            }
            if (studyResourcingDTOs != null && !studyResourcingDTOs.isEmpty()) {
                for (Iterator<StudyResourcingDTO> it = studyResourcingDTOs.iterator(); it.hasNext();) {
                    StudyResourcingDTO studyResourcingDTO = it.next();
                    try {
                        studyResourcingDTO.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(Long
                                .valueOf(trialDTO.getIdentifier())));
                        PaRegistry.getStudyResourcingService().validate(studyResourcingDTO);
                    } catch (PADuplicateException dupEx) {
                        it.remove();
                    }
                }
            }
            StudyProtocolDTO sPDTO = null;
            sPDTO =
                    PaRegistry.getStudyProtocolService().getStudyProtocol(
                            IiConverter.convertToStudyProtocolIi(Long.valueOf(trialDTO.getIdentifier())));
            if (sPDTO.getSecondaryIdentifiers() != null && sPDTO.getSecondaryIdentifiers().getItem() != null) {
                List<Ii> listIi = new ArrayList<Ii>();
                for (Ii ii : sPDTO.getSecondaryIdentifiers().getItem()) {
                    if (!IiConverter.STUDY_PROTOCOL_ROOT.equals(ii.getRoot())
                          && !isOtherIdDuplicate(trialDTO.getSecondaryIdentifierAddList(), ii.getExtension())) {
                        listIi.add(ii);
                    }
                }
                trialDTO.setSecondaryIdentifierList(listIi);
            }
            studyProtocolDTO = util.convertToStudyProtocolDTOForAmendment(trialDTO);
            studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(userName));
            studyProtocolIi =
                    PaRegistry.getTrialRegistrationService().amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                            studyResourcingDTOs, documentDTOs, leadOrgDTO, principalInvestigatorDTO, sponsorOrgDTO,
                            partyDTO,
                            leadOrgSiteIdDTO, studyIdentifierDTOs, 
                            summary4orgDTO, summary4studyResourcingDTO, 
                            studyRegAuthDTO, BlConverter.convertToBl(Boolean.TRUE));

        }
        return studyProtocolIi;
    }

    private ResponsiblePartyDTO convertToResponsiblePartyDTO(
            StudyProtocolBatchDTO batchDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrgDTO) throws NullifiedEntityException,
            PAException, URISyntaxException, EntityValidationException,
            CurationException {

        TrialBatchDataValidator dataValidator = new TrialBatchDataValidator();
        ResponsiblePartyDTO partyDTO = new ResponsiblePartyDTO();
        if (batchDTO.isCtGovXmlIndicator()) {
            final ResponsiblePartyType type = ResponsiblePartyType
                    .getByCode(batchDTO.getResponsibleParty());
            partyDTO.setType(type);
            
            final String title = StringUtils.defaultIfEmpty(
                    batchDTO.getPartyInvestigatorTitle(),
                    "Principal Investigator");
            
            if (ResponsiblePartyType.PRINCIPAL_INVESTIGATOR.equals(type)) {
                partyDTO.setTitle(title);
                partyDTO.setInvestigator(principalInvestigatorDTO);

                OrganizationBatchDTO affiliationBatch = dataValidator
                        .buildAffiliationOrgDto(batchDTO);
                if (dataValidator.isEmpty(affiliationBatch)) {
                    partyDTO.setAffiliation(sponsorOrgDTO);
                } else {
                    Ii orgIdIi = lookUpOrgs(affiliationBatch);
                    OrganizationDTO orgDTO = new OrganizationDTO();
                    orgDTO.setIdentifier(orgIdIi);
                    partyDTO.setAffiliation(orgDTO);
                }

            }
            if (ResponsiblePartyType.SPONSOR_INVESTIGATOR.equals(type)) {
                partyDTO.setTitle(title);
                partyDTO.setAffiliation(sponsorOrgDTO);
                PersonBatchDTO investigatorDto = dataValidator
                        .buildInvestigatorDto(batchDTO);
                if (dataValidator.isEmpty(investigatorDto)) {
                    partyDTO.setInvestigator(principalInvestigatorDTO);
                } else {
                    Ii personID = lookUpPersons(investigatorDto);
                    PersonDTO personDTO = new PersonDTO();
                    personDTO.setIdentifier(personID);
                    partyDTO.setInvestigator(personDTO);
                }

            }

        }

        return partyDTO;
    }

    /**
     * @param listofDto
     */
    private boolean isOtherIdDuplicate(List<Ii> listOfOtherId, String newOtherId) {
        // ignore duplicate other ids
        if (listOfOtherId != null) {
            for (Ii orgOId : listOfOtherId) {
                if (orgOId.getExtension().equals(newOtherId)) {
                    return true;
                }
            }
        }
        return false;
    }

    private TrialDTO convertToTrialDTO(StudyProtocolBatchDTO batchDTO,
            String folderPath) throws IOException, NullifiedEntityException,
            PAException, URISyntaxException, EntityValidationException,
            CurationException, ParseException {

        TrialDTO trialDTO = new TrialDTO();
        trialDTO.setPrimaryCompletionDate(batchDTO.getPrimaryCompletionDate());
        trialDTO.setPrimaryCompletionDateType(batchDTO.getPrimaryCompletionDateType());
        trialDTO.setCompletionDate(batchDTO.getCompletionDate());
        trialDTO.setCompletionDateType(batchDTO.getCompletionDateType());
        trialDTO.setLeadOrgTrialIdentifier(batchDTO.getLocalProtocolIdentifier());
        trialDTO.setNctIdentifier(batchDTO.getNctNumber());
        trialDTO.setOfficialTitle(batchDTO.getTitle());
        trialDTO.setPrimaryPurposeCode(batchDTO.getPrimaryPurpose());
        trialDTO.setPrimaryPurposeAdditionalQualifierCode(batchDTO.getPrimaryPurposeAdditionalQualifierCode());
        trialDTO.setPrimaryPurposeOtherText(batchDTO.getPrimaryPurposeOtherText());
        trialDTO.setReason(batchDTO.getReasonForStudyStopped());
        trialDTO.setStartDate(batchDTO.getStudyStartDate());
        trialDTO.setStartDateType(batchDTO.getStudyStartDateType());
        
        StatusDto status = new StatusDto();
        status.setStatusDate(DateUtils.parseDate(
                batchDTO.getCurrentTrialStatusDate(), new String[] {
                        "MM/dd/yyyy", "MM/dd/yy" }));
        status.setStatusCode(StudyStatusCode.getByCode(batchDTO
                .getCurrentTrialStatus()) != null ? StudyStatusCode.getByCode(
                batchDTO.getCurrentTrialStatus()).name() : null);
        trialDTO.setStatusHistory(Arrays.asList(status));
        
        trialDTO.setSummaryFourFundingCategoryCode(batchDTO.getSumm4FundingCat());

        trialDTO.setTrialType(batchDTO.getTrialType());
        trialDTO.setXmlRequired(batchDTO.isCtGovXmlIndicator());
        trialDTO.setNciGrant(batchDTO.getNciGrant());

        // if the Phase's value is not in allowed LOV then save phase as Other
        // and comments as the value of current phase
        trialDTO.setPhaseCode(batchDTO.getPhase());
        if (PhaseCode.NA.name().equals(batchDTO.getPhase())) {
            trialDTO.setPhaseAdditionalQualifier("Yes"
                    .equalsIgnoreCase(batchDTO
                            .getPhaseAdditionalQualifierCode()) ? PhaseAdditionalQualifierCode.PILOT
                    .getCode() : null);
        }
        trialDTO.setProgramCodeText(batchDTO.getProgramCodeText());
        if (batchDTO.getSubmissionType().equalsIgnoreCase("A")) {
            trialDTO.setAssignedIdentifier(batchDTO.getNciTrialIdentifier());
            trialDTO.setAmendmentDate(batchDTO.getAmendmentDate());
            trialDTO.setLocalAmendmentNumber(batchDTO.getAmendmentNumber());
        }
        
        TrialBatchDataValidator dataValidator = new TrialBatchDataValidator();
        // before creating the protocol check for duplicate
        // using the Lead Org Trial Identifier and Lead Org Identifier

        // Lead Org
        OrganizationBatchDTO leadOrgDto = dataValidator.buildLeadOrgDto(batchDTO);
        Ii orgIdIi = lookUpOrgs(leadOrgDto);
        trialDTO.setLeadOrganizationIdentifier(orgIdIi.getExtension()); // All the Ii

        // lead PI
        PersonBatchDTO piDto = dataValidator.buildLeadPIDto(batchDTO); // look up Person
        Ii leadPrincipalInvestigator = lookUpPersons(piDto);
        trialDTO.setPiIdentifier(leadPrincipalInvestigator.getExtension());
        
        if (batchDTO.isCtGovXmlIndicator()) {
            // oversight info
            trialDTO.setFdaRegulatoryInformationIndicator(batchDTO.getFdaRegulatoryInformationIndicator());
            trialDTO.setSection801Indicator(batchDTO.getSection801Indicator());
            trialDTO.setDataMonitoringCommitteeAppointedIndicator(batchDTO
                    .getDataMonitoringCommitteeAppointedIndicator());
            if (batchDTO.getSubmissionType().equalsIgnoreCase("A")) {
                StudyProtocolQueryCriteria viewCriteria = new StudyProtocolQueryCriteria();
                viewCriteria.setNciIdentifier(trialDTO.getAssignedIdentifier());
                List<StudyProtocolQueryDTO> listofDto =
                        PaRegistry.getProtocolQueryService().getStudyProtocolByCriteria(viewCriteria);
                if (listofDto.isEmpty() || listofDto.size() > 1) {
                    throw new PAException("mutliple trial or no trial found for given NCI Trial Identifier.\n");
                }
                 StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService()
                        .getStudyProtocol(IiConverter.convertToIi(listofDto.get(0).getStudyProtocolId()));
                 if (!spDTO.getDelayedpostingIndicator().equals(BlConverter
                           .convertYesNoStringToBl(batchDTO.getDelayedPostingIndicator()))) {
                     trialDTO.setDelayedPostingIndicator(BlConverter.convertBlToYesNoString(spDTO
                           .getDelayedpostingIndicator()));
                     delayedPostingIndWarning = "AmendWarning";
                 }
            } else if (batchDTO.getSubmissionType().equalsIgnoreCase("O") && StringUtils
                 .isNotEmpty(batchDTO.getDelayedPostingIndicator())) {
                if (batchDTO.getDelayedPostingIndicator().equalsIgnoreCase("Yes")) {
                     trialDTO.setDelayedPostingIndicator("No");
                     delayedPostingIndWarning = "CreateWarning";
              } 
            }
            // Sponsor org
            OrganizationBatchDTO sponsorOrgDto = dataValidator.buildSponsorOrgDto(batchDTO); // look up sponsor
            Ii sponsorIdIi = lookUpOrgs(sponsorOrgDto);
            trialDTO.setSponsorIdentifier(sponsorIdIi.getExtension());
        }

        // Summary for sponsor
        OrganizationBatchDTO summ4Sponsor = dataValidator.buildSummary4Sponsor(batchDTO); // Summary 4 Info
        Ii summary4Sponsor = null;
        if (!dataValidator.orgDTOIsEmpty(summ4Sponsor)) {
            summary4Sponsor = lookUpOrgs(summ4Sponsor); // look up for org only when dto is not empty
        }
        if (summary4Sponsor != null) {
            SummaryFourSponsorsWebDTO summarySp = new SummaryFourSponsorsWebDTO();
            summarySp.setOrgId(summary4Sponsor.getExtension());
            if (!trialDTO.getSummaryFourOrgIdentifiers().contains(summarySp)) {
                trialDTO.getSummaryFourOrgIdentifiers().add(summarySp);
            }
        }
        
        trialDTO.setDocDtos(addDocDTOToList(batchDTO, folderPath)); // add doc to the dto
        trialDTO.setIndIdeDtos(dataValidator.convertIndsToList(batchDTO)); // add ind
        trialDTO.setFundingDtos(dataValidator.convertToGrantList(batchDTO)); // add grants
        trialDTO.setCtepIdentifier(batchDTO.getCtepIdentifier());
        trialDTO.setDcpIdentifier(batchDTO.getDcpIdentifier());
        return trialDTO;
    }

    /**
     * This method is first look up the Org. If org Found then return the id else it will create the new org assumpion-
     * even if the lookup return multiple org we are taking the first one
     *
     * @param batchDto batchDto
     * @return Str
     * @throws PAException PAException
     * @throws EntityValidationException
     * @throws URISyntaxException
     * @throws NullifiedEntityException
     * @throws CurationException
     */
    private Ii lookUpOrgs(OrganizationBatchDTO batchDto) throws PAException, NullifiedEntityException,
            URISyntaxException, EntityValidationException, CurationException {
        Ii orgId = null;

        String orgName = batchDto.getName();
        String countryName = batchDto.getCountry();
        String streetAddress = batchDto.getStreetAddress();
        String cityName = batchDto.getCity();
        String zipCode = batchDto.getZip();

        OrganizationDTO criteria = new OrganizationDTO();
        if (StringUtils.isNotEmpty(batchDto.getPoIdentifier())) {
            criteria.setIdentifier(IiConverter.convertToPoOrganizationIi(batchDto.getPoIdentifier()));
        } else {
            criteria.setName(EnOnConverter.convertToEnOn(orgName));
            criteria.setPostalAddress(AddressConverterUtil.create(streetAddress, null, cityName, null, zipCode,
                    countryName.toUpperCase(Locale.US)));

        }
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<OrganizationDTO> poOrgDtos = null;
        try {
            poOrgDtos = PoRegistry.getOrganizationEntityService().search(criteria, limit);
        } catch (TooManyResultsException e) {
            throw new PAException(e);
        }

        if (CollectionUtils.isEmpty(poOrgDtos)) {
            if (StringUtils.isNotEmpty(batchDto.getPoIdentifier())) {
                throw new PAException("Organization Identifier not found " + batchDto.getPoIdentifier());
            }
            orgId = createOrganization(batchDto);
        } else {
            // return the Id of the org
            orgId = poOrgDtos.get(0).getIdentifier();
            LOG.debug(" lookUpOrgs search returned orgId " + orgId.getExtension().toString());
        }
        return orgId;
    }

    /**
     * his method is first look up the Person. If person Found then return the id else it will create the new Person
     * Assumpion- even if the lookup return multiple Person we are taking the first one
     *
     * @param batchDto batchDto
     * @return str
     * @throws PAException ex
     * @throws URISyntaxException
     * @throws EntityValidationException
     * @throws NullifiedEntityException
     * @throws CurationException
     */
    private Ii createOrganization(OrganizationBatchDTO batchDto) throws PAException, URISyntaxException,
            EntityValidationException, NullifiedEntityException, CurationException {
        OrganizationDTO orgDto = new OrganizationDTO();
        Ii orgId = null;
        String orgName = batchDto.getName();
        String orgStAddress = batchDto.getStreetAddress();
        String cityName = batchDto.getCity();
        String zipCode = batchDto.getZip();
        String countryName = batchDto.getCountry().toUpperCase(Locale.US);
        String stateName = batchDto.getState();
        if (StringUtils.isNotEmpty(stateName)
                && (batchDto.getCountry().equalsIgnoreCase("USA") || batchDto.getCountry().equalsIgnoreCase("CAN")
                        || batchDto.getCountry().equalsIgnoreCase("AUS"))) {
            stateName = stateName.toUpperCase(Locale.US);
        }
        String email = batchDto.getEmail();
        String phoneNumer = batchDto.getPhone();
        String faxNumber = batchDto.getFax();
        String ttyNumber = batchDto.getTty();
        String url = batchDto.getUrl();

        orgDto.setName(EnOnConverter.convertToEnOn(orgName));
        orgDto.setPostalAddress(AddressConverterUtil.create(orgStAddress, null, cityName, stateName, zipCode,
                countryName));
        DSet<Tel> telco = getDSetTelList(email, phoneNumer, faxNumber, ttyNumber, url);
        orgDto.setTelecomAddress(telco);
        Ii id = PoRegistry.getOrganizationEntityService().createOrganization(orgDto);
        List<OrganizationDTO> callConvert = new ArrayList<OrganizationDTO>();
        callConvert.add(PoRegistry.getOrganizationEntityService().getOrganization(id));
        orgId = callConvert.get(0).getIdentifier();

        return orgId;
    }

    /**
     * @param email
     * @param phoneNumer
     * @param faxNumber
     * @param ttyNumber
     * @param url
     * @return
     * @throws URISyntaxException
     */
    private DSet<Tel> getDSetTelList(String email, String phoneNumer, String faxNumber, String ttyNumber, String url)
            throws URISyntaxException {
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());

        if (phoneNumer != null && phoneNumer.length() > 0) {
            Tel t = new Tel();
            t.setValue(new URI("tel", phoneNumer, null));
            telco.getItem().add(t);
        }
        if (faxNumber != null && faxNumber.length() > 0) {
            Tel fax = new Tel();
            fax.setValue(new URI("x-text-fax", faxNumber, null));
            telco.getItem().add(fax);
        }
        if (ttyNumber != null && ttyNumber.length() > 0) {
            Tel tty = new Tel();
            tty.setValue(new URI("x-text-tel", ttyNumber, null));
            telco.getItem().add(tty);
        }
        if (url != null && url.length() > 0) {
            TelUrl telurl = new TelUrl();
            telurl.setValue(new URI(url));
            telco.getItem().add(telurl);
        }
        TelEmail telemail = new TelEmail();
        telemail.setValue(new URI("mailto:" + email));
        telco.getItem().add(telemail);
        return telco;
    }

    /**
     *
     * @param batchDto dto
     * @return Ii personId
     * @throws PAException ex
     * @throws EntityValidationException
     * @throws URISyntaxException
     * @throws CurationException
     */
    private Ii lookUpPersons(PersonBatchDTO batchDto) throws PAException, URISyntaxException,
            EntityValidationException, CurationException {
        Ii personId = null;
        String firstName = batchDto.getFirstName();
        String lastName = batchDto.getLastName();
        String streetAddress = batchDto.getStreetAddress();
        String email = batchDto.getEmail();
        String identifier = batchDto.getPoIdentifier();
        PersonDTO person = new PersonDTO();
        List<PersonDTO> poPersonList = new ArrayList<PersonDTO>();
        if (StringUtils.isNotEmpty(identifier)) {
            person.setIdentifier(IiConverter.convertToPoPersonIi(batchDto.getPoIdentifier()));
        } else {
            person.setPostalAddress(AddressConverterUtil.create(streetAddress, null, batchDto.getCity(), null, batchDto
                    .getZip(), batchDto.getCountry().toUpperCase(Locale.US)));
            if (email != null && email.length() > 0) {
                DSet<Tel> list = new DSet<Tel>();
                list.setItem(new HashSet<Tel>());
                TelEmail telemail = new TelEmail();
                telemail.setValue(new URI("mailto:" + email));
                list.getItem().add(telemail);
                person.setTelecomAddress(list);
            }
            person.setName(RemoteApiUtil.convertToEnPn(firstName, null, lastName, null, null));
        }

        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        try {
            poPersonList = PoRegistry.getPersonEntityService().search(person, limit);
        } catch (TooManyResultsException e) {
            throw new PAException(e);
        }

        if (CollectionUtils.isEmpty(poPersonList)) {
            if (StringUtils.isNotEmpty(batchDto.getPoIdentifier())) {
                throw new PAException("Person Identifier not found " + batchDto.getPoIdentifier());
            }
            personId = createPerson(batchDto);
        } else {
            personId = poPersonList.get(0).getIdentifier();
        }
        return personId;
    }

    /**
     *
     * @param batchDto dto
     * @return Ii personId
     * @throws PAException ex
     * @throws URISyntaxException
     * @throws EntityValidationException
     * @throws CurationException
     */
    private Ii createPerson(PersonBatchDTO batchDto) throws PAException, URISyntaxException, EntityValidationException,
            CurationException {
        Ii personId = null;
        String firstName = batchDto.getFirstName();
        String lastName = batchDto.getLastName();
        String email = batchDto.getEmail();
        String streetAddr = batchDto.getStreetAddress();
        String city = batchDto.getCity();
        String zip = batchDto.getZip();
        String country = batchDto.getCountry().toUpperCase(Locale.US);
        String state = batchDto.getState();
        if (StringUtils.isNotEmpty(state)
                && (batchDto.getCountry().equalsIgnoreCase("USA") || batchDto.getCountry().equalsIgnoreCase("CAN")
                        || batchDto.getCountry().equalsIgnoreCase("AUS"))) {
            state = state.toUpperCase(Locale.US);
        }

        LOG.error("State as" + state + " Country as " + country);
        String midName = batchDto.getMiddleName();
        String phone = batchDto.getPhone();
        String tty = batchDto.getTty();
        String fax = batchDto.getFax();
        String url = batchDto.getUrl();
        //
        gov.nih.nci.services.person.PersonDTO dto = new gov.nih.nci.services.person.PersonDTO();
        dto.setName(new EnPn());
        Enxp part = new Enxp(EntityNamePartType.GIV);
        part.setValue(firstName);
        dto.getName().getPart().add(part);
        // if middel name exists stick it in here!
        if (StringUtils.isNotEmpty(midName)) {
            Enxp partMid = new Enxp(EntityNamePartType.GIV);
            partMid.setValue(midName);
            dto.getName().getPart().add(partMid);
        }
        Enxp partFam = new Enxp(EntityNamePartType.FAM);
        partFam.setValue(lastName);
        dto.getName().getPart().add(partFam);

        DSet<Tel> list = getDSetTelList(email, phone, fax, tty, url);
        dto.setTelecomAddress(list);
        dto.setPostalAddress(AddressConverterUtil.create(streetAddr, null, city, state, zip, country));
        personId = PoRegistry.getPersonEntityService().createPerson(dto);

        return personId;
    }

    private List<TrialDocumentWebDTO> addDocDTOToList(StudyProtocolBatchDTO dto, String folderPath) throws IOException {
        TrialUtil util = new TrialUtil();
        File doc = new File(folderPath + dto.getProtcolDocumentFileName());
        List<TrialDocumentWebDTO> docDTOList = new ArrayList<TrialDocumentWebDTO>();
        docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.PROTOCOL_DOCUMENT.getCode(), dto
                .getProtcolDocumentFileName(), doc));
        doc = new File(folderPath + dto.getIrbApprovalDocumentFileName());
        docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode(), dto
                .getIrbApprovalDocumentFileName(), doc));

        if (StringUtils.isNotEmpty(dto.getInformedConsentDocumentFileName())) {
            doc = new File(folderPath + dto.getInformedConsentDocumentFileName());
            docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT.getCode(), dto
                    .getInformedConsentDocumentFileName(), doc));
        }
        if (StringUtils.isNotEmpty(dto.getParticipatinSiteDocumentFileName())) {
            doc = new File(folderPath + dto.getParticipatinSiteDocumentFileName());
            docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.PARTICIPATING_SITES.getCode(), dto
                    .getParticipatinSiteDocumentFileName(), doc));
        }
        // for Amendment Other document type will be skipped.
        if (StringUtils.isNotEmpty(dto.getOtherTrialRelDocumentFileName())) {
            doc = new File(folderPath + dto.getOtherTrialRelDocumentFileName());
            docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.OTHER.getCode(), dto
                    .getOtherTrialRelDocumentFileName(), doc));
        }
        // original submission will not have amendment date so protocol Highlighted doc and change memo will be skipped.
        if (StringUtils.isNotEmpty(dto.getAmendmentDate()) && StringUtils.isNotEmpty(dto.getNciTrialIdentifier())) {
            if (StringUtils.isNotEmpty(dto.getProtocolHighlightDocFileName())) {
                doc = new File(folderPath + dto.getProtocolHighlightDocFileName());
                docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT.getCode(), dto
                        .getProtocolHighlightDocFileName(), doc));
            }
            if (StringUtils.isNotEmpty(dto.getChangeRequestDocFileName())) {
                doc = new File(folderPath + dto.getChangeRequestDocFileName());
                docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.CHANGE_MEMO_DOCUMENT.getCode(), dto
                        .getChangeRequestDocFileName(), doc));
            }
        }
        return docDTOList;
    }
}

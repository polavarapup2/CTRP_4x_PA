/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
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
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.dto.NCISpecificInformationWebDTO;
import gov.nih.nci.pa.dto.OrgFamilyDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.SummaryFourSponsorsWebDTO;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.lov.ConsortiaTrialCategoryCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.FamilyProgramCodeService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;







import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author gnaveh
 *
 */
@SuppressWarnings({ "PMD.ExcessiveMethodLength", "PMD.UseCollectionIsEmpty", "PMD.LongInstantiation",
    "PMD.SingularField" , "PMD.CyclomaticComplexity" })
public class NCISpecificInformationAction extends ActionSupport {
    private static final long serialVersionUID = -5560377425534113809L;
    private static final String DISPLAY_ORG_FLD = "displayOrgFld";
    private NCISpecificInformationWebDTO nciSpecificInformationWebDTO = new NCISpecificInformationWebDTO();
    private String chosenOrg;
    private TrialHelper trialHelper = new TrialHelper();
    private PAServiceUtils paServiceUtil = new PAServiceUtils();    
    private static final int MAX_CTRO_OVERRIDE_COMMENTS_SIZE = 1000;
    private ProtocolQueryServiceLocal queryServiceLocal;
    private FamilyProgramCodeService familyProgramCodeService;
    private List<ProgramCodeDTO> programCodeList = new ArrayList<ProgramCodeDTO>();

    private List<Long> programCodeIds;
    private boolean cancerTrial;

    /**
     * @return result
     */
    @Override
    public String execute() {
        return SUCCESS;
    }

    /**
     * @return result
     */
    public String query() {
        try {
           
            StudyProtocolDTO studyProtocolDTO = getStudyProtocol();
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    Constants.STUDY_PROTOCOL_II);
            List<StudyResourcingDTO> studyResourcingDTO = PaRegistry.getStudyResourcingService()
                .getSummary4ReportedResourcing(studyProtocolIi);
            nciSpecificInformationWebDTO = setNCISpecificDTO(studyProtocolDTO, studyResourcingDTO);
            loadProgramCodes();
            syncProgramCodes();
            
            //keep existing database logic as it is this mean false value should be shown as true in UI
            //and true value will be shown as false in UI
            if (nciSpecificInformationWebDTO.getCtroOverride() != null) {
                nciSpecificInformationWebDTO.setCtroOverride(!nciSpecificInformationWebDTO.getCtroOverride());    
            }
            
            
           
            ServletActionContext.getRequest().getSession().setAttribute("summary4Sponsors", 
                    nciSpecificInformationWebDTO.getSummary4Sponsors());
            
         
            
            boolean displayXmlFlag = getDisplayXmlFlag(studyProtocolIi);
            ServletActionContext.getRequest().setAttribute("displayXmlFlag", displayXmlFlag);
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }

    /**
     * @param paOrgId
     * @throws PAException
     */
    private Organization getPAOrganizationById(Ii paOrgId) throws PAException {
        // get the name of the organization using primary id
        Organization o = new Organization();
        o.setId(Long.valueOf(paOrgId.getExtension()));
        Organization org = PaRegistry.getPAOrganizationService().getOrganizationByIndetifers(o);
        //if the entity status if nullified check if there is dup of it
        if (EntityStatusCode.NULLIFIED.equals(org.getStatusCode())) {
            Organization dupOrg = getOrCreateDuplicateOrgInPA(org.getIdentifier());
            if (dupOrg != null) {
                org = dupOrg;
                Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                        Constants.STUDY_PROTOCOL_II);
                updateSummary4DTO(studyProtocolIi, dupOrg.getId());
            }
        }
        return org;
    }

    /**
     * @param poId
     * @return
     * @throws PAException
     */
    private Organization getOrCreateDuplicateOrgInPA(String poId)
            throws PAException {
        Organization dupOrg = null;
        Ii dupId = paServiceUtil.getDuplicateOrganizationIi(IiConverter.convertToPoOrganizationIi(poId));
        if (!ISOUtil.isIiNull(dupId)) {
            dupOrg = paServiceUtil.getOrCreatePAOrganizationByIi(dupId);
        }
        return dupOrg;
    }

    /**
     * @return res
     */
    public String update() {
        // Step1 : check for any errors
        if (!BlConverter.convertToBool(getStudyProtocol().getProprietaryTrialIndicator())
              && StringUtils.isEmpty(nciSpecificInformationWebDTO.getAccrualReportingMethodCode())) {
            addFieldError("nciSpecificInformationWebDTO.accrualReportingMethodCode",
                    getText("error.studyProtocol.accrualReportingMethodCode"));
        }
        try {
        Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                Constants.STUDY_PROTOCOL_II);
        boolean displayXmlFlag = getDisplayXmlFlag(studyProtocolIi);
        
        if (nciSpecificInformationWebDTO.getCtroOverideFlagComments() == null && displayXmlFlag) {
            addFieldError("nciSpecificInformationWebDTO.ctroOverideFlagComments",
                    getText("error.studyProtocol.ctroOverideFlagComments.required"));
        }
        
        if (nciSpecificInformationWebDTO.getCtroOverideFlagComments() != null 
                && nciSpecificInformationWebDTO.getCtroOverideFlagComments().length()
                > MAX_CTRO_OVERRIDE_COMMENTS_SIZE) {
            addFieldError("nciSpecificInformationWebDTO.ctroOverideFlagComments",
                    getText("error.studyProtocol.ctroOverideFlagComments.length"));
        }
        //load program codes
        loadProgramCodes();

        if (hasFieldErrors()) {
            ServletActionContext.getRequest().setAttribute("displayXmlFlag", displayXmlFlag);         
            return ERROR;
        }
        // Step2 : retrieve the studyprotocol
        StudyResourcingDTO srDTO = new StudyResourcingDTO();

            //bind
            bindProgramCodes();
       
            // Step 0 : get the studyprotocol from database
            
            StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            // Step1 : update values to StudyProtocol
            spDTO.setAccrualReportingMethodCode(CdConverter.convertToCd(AccrualReportingMethodCode
                    .getByCode(nciSpecificInformationWebDTO.getAccrualReportingMethodCode())));
            spDTO.setProgramCodeText(StConverter.convertToSt(nciSpecificInformationWebDTO.getProgramCodeText()));

            //copy program codes into studyProtocolDTO
            spDTO.setProgramCodes(nciSpecificInformationWebDTO.getProgramCodes());
            
            //keep existing database logic as it is this means false value shown in UI should be passed and true
            //and true value should be passed as false
            if (nciSpecificInformationWebDTO.getCtroOverride() != null) {
                
                spDTO.setCtroOverride(BlConverter.convertToBl(!nciSpecificInformationWebDTO.getCtroOverride()));
            }
            
            spDTO.setConsortiaTrialCategoryCode(CdConverter
                    .convertStringToCd(nciSpecificInformationWebDTO
                            .getConsortiaTrialCategoryCode()));
            spDTO.setCtroOverideFlagComments(nciSpecificInformationWebDTO.getCtroOverideFlagComments());
            // Step2 : update values to StudyResourcing
            srDTO.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode
                    .getByCode(nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode())));
            srDTO.setStudyProtocolIdentifier(studyProtocolIi);
            
            // Step3: update studyprotocol
            spDTO = PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);
            // Step 4: check if we have an organization for PO id            
            trialHelper.saveSummary4Information(studyProtocolIi, nciSpecificInformationWebDTO.getSummary4Sponsors(), 
                    nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode());
            
            ServletActionContext.getRequest().setAttribute("displayXmlFlag", displayXmlFlag);
            
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, "Update succeeded.");
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return ERROR;
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        return SUCCESS;
    }
    
    //this method checks if trial is sponsored and not already sent to ctgov
    //if trial is not sponsored or excluded from nightly build then hide flag and comments from ui
    private boolean getDisplayXmlFlag(Ii studyProtocolIi) throws PAException {
        boolean displayXmlFlag = false;
        try {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setNciSponsored(true);
        criteria.setTrialCategory("n");
        
        criteria.setStudyProtocolId(new Long(studyProtocolIi.getExtension()));
       
        //check if trial is sponsored 
        queryServiceLocal =  PaRegistry.getProtocolQueryService();
        List<StudyProtocolQueryDTO> list = queryServiceLocal
                .getStudyProtocolByCriteria(criteria);
       
        if (list != null && list.size() > 0) {
            StudyProtocolQueryDTO studyProtocolQueryDTO = list.get(0);
            
            String nctIdentifier = studyProtocolQueryDTO.getNctIdentifier();
            String trialStatus = studyProtocolQueryDTO.getStudyStatusCode().getCode();
                
            displayXmlFlag = PaRegistry.getCTGovUploadService().checkIfTrialExcludeAndUpdateCtroOverride(
                    new Long(studyProtocolIi.getExtension()), trialStatus, nctIdentifier);
            //this is flag value comes is exclude from this method
            displayXmlFlag = !displayXmlFlag;
         }
        
        } catch (Exception e) {
            LOG.error("getDisplayXmlFlag in method ", e.getMessage());
        }
        return displayXmlFlag;
    }

    /**
     * @param studyProtocolIi ii
     * @param orgId paOrgId
     * @throws PAException on error
     */
    private void updateSummary4DTO(Ii studyProtocolIi, Long orgId)
            throws PAException {
        // Step4 : find out if summary 4 records already exists
        StudyResourcingDTO summary4ResoureDTO = PaRegistry.getStudyResourcingService()
                .getSummary4ReportedResourcingBySpAndOrgId(studyProtocolIi, orgId);
        if (summary4ResoureDTO == null) {
            // summary 4 record does not exist,so create a new one
            summary4ResoureDTO = new StudyResourcingDTO();
            summary4ResoureDTO.setStudyProtocolIdentifier(studyProtocolIi);
            summary4ResoureDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.TRUE));
            summary4ResoureDTO.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode
                    .getByCode(nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode())));
            summary4ResoureDTO.setOrganizationIdentifier(IiConverter.convertToIi(orgId));
            PaRegistry.getStudyResourcingService().createStudyResourcing(summary4ResoureDTO);
        } else {
            // summary 4 record does exist,so so do an update
            summary4ResoureDTO.setStudyProtocolIdentifier(studyProtocolIi);
            summary4ResoureDTO.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode
                    .getByCode(nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode())));
            summary4ResoureDTO.setOrganizationIdentifier(IiConverter.convertToIi(orgId));
            PaRegistry.getStudyResourcingService().updateStudyResourcing(summary4ResoureDTO);
        }
    }

    /**
     *
     * @return nciSpecificInformationWebDTO
     */
    public NCISpecificInformationWebDTO getNciSpecificInformationWebDTO() {
        return nciSpecificInformationWebDTO;
    }

    /**
     *
     * @param nciSpecificInformationWebDTO nciSpecificInformationWebDTO
     */
    public void setNciSpecificInformationWebDTO(NCISpecificInformationWebDTO nciSpecificInformationWebDTO) {
        this.nciSpecificInformationWebDTO = nciSpecificInformationWebDTO;
    }

    // @todo : catch and throw paexception
    private StudyProtocolDTO getStudyProtocol() {
        try {
            return PaRegistry.getStudyProtocolService().getStudyProtocol(
                    (Ii) ServletActionContext.getRequest().getSession().getAttribute(Constants.STUDY_PROTOCOL_II));
        } catch (Exception e) {
            return null;
        }
    }

    private NCISpecificInformationWebDTO setNCISpecificDTO(StudyProtocolDTO spDTO, List<StudyResourcingDTO> srDTO) 
            throws PAException {
        NCISpecificInformationWebDTO nciSpDTO = new NCISpecificInformationWebDTO();
        if (spDTO != null) {
            convertStudyProtocolDto(spDTO, nciSpDTO);
        }
        if (srDTO != null) {
            convertStudyResourcingDto(srDTO, nciSpDTO);
        }
        
        return nciSpDTO;
    }

    private void convertStudyResourcingDto(List<StudyResourcingDTO> srDTO, NCISpecificInformationWebDTO nciSpDTO) 
            throws PAException {
        if (CollectionUtils.isNotEmpty(srDTO) && srDTO.get(0).getTypeCode() != null) {
            nciSpDTO.setSummaryFourFundingCategoryCode(srDTO.get(0).getTypeCode().getCode());
        }
        if (CollectionUtils.isNotEmpty(srDTO)) {
            for (StudyResourcingDTO dto : srDTO) {
                if (dto.getOrganizationIdentifier() != null) {
                    Organization org = getPAOrganizationById(dto.getOrganizationIdentifier());
                    SummaryFourSponsorsWebDTO webDto = new SummaryFourSponsorsWebDTO();
                    webDto.setRowId(UUID.randomUUID().toString());
                    webDto.setOrgId(org.getIdentifier());
                    webDto.setOrgName(org.getName());                    
                    if (!nciSpDTO.getSummary4Sponsors().contains(webDto)) {
                        nciSpDTO.getSummary4Sponsors().add(webDto);
                    }
                }
            }
        }
    }

    private void convertStudyProtocolDto(StudyProtocolDTO spDTO, NCISpecificInformationWebDTO nciSpDTO) {
        if (spDTO.getAccrualReportingMethodCode() != null) {
            nciSpDTO.setAccrualReportingMethodCode(spDTO.getAccrualReportingMethodCode().getCode());
        }
        nciSpDTO.setCtroOverride(BlConverter.convertToBoolean(spDTO.getCtroOverride()));
        nciSpDTO.setConsortiaTrialCategoryCode(CdConverter.convertCdToString(spDTO.getConsortiaTrialCategoryCode()));
        nciSpDTO.setCtroOverideFlagComments(spDTO.getCtroOverideFlagComments());
        if (CollectionUtils.isNotEmpty(spDTO.getProgramCodes())) {
            nciSpDTO.setProgramCodes(spDTO.getProgramCodes());
        }
        
    }

    /**
     *
     * @return result
     */
    @SuppressWarnings("unchecked")
    public String displayOrg() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        List<SummaryFourSponsorsWebDTO> summary4SponsorsList = (List<SummaryFourSponsorsWebDTO>) 
                session.getAttribute("summary4Sponsors");
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        LimitOffset limit = new LimitOffset(1, 0);
        OrganizationDTO selectedOrgDTO;
        try {
            selectedOrgDTO = PoRegistry.getOrganizationEntityService().search(criteria, limit).get(0);
        } catch (TooManyResultsException e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return DISPLAY_ORG_FLD;
        }
        SummaryFourSponsorsWebDTO summarySp = new SummaryFourSponsorsWebDTO();
        summarySp.setRowId(UUID.randomUUID().toString());
        summarySp.setOrgId(orgId);
        summarySp.setOrgName(selectedOrgDTO.getName().getPart().get(0).getValue());
        if (summary4SponsorsList == null) {
            summary4SponsorsList = new ArrayList<SummaryFourSponsorsWebDTO>();
        } 
        if (summary4SponsorsList.contains(summarySp)) {
            addFieldError("nciSpecificInformationWebDTO.organizationName", 
                             "Selected Sponsor already exists for this trial");
        } else if (!summary4SponsorsList.contains(summarySp)) {
            summary4SponsorsList.add(summarySp);
        }
        nciSpecificInformationWebDTO.getSummary4Sponsors().addAll(summary4SponsorsList);
        return DISPLAY_ORG_FLD;
    }
    
    /**
     * @return string
     */
    @SuppressWarnings("unchecked")
    public String deleteSummaryFourOrg() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        List<SummaryFourSponsorsWebDTO> summary4SponsorsList = (List<SummaryFourSponsorsWebDTO>) 
                session.getAttribute("summary4Sponsors");
        String uuId = ServletActionContext.getRequest().getParameter("uuid");
        for (int i = summary4SponsorsList.size() - 1; i >= 0; i--) {
            SummaryFourSponsorsWebDTO webDto = summary4SponsorsList.get(i);
            if (webDto.getRowId().equals(uuId)) {
                summary4SponsorsList.remove(i);
            }
        }
        nciSpecificInformationWebDTO.getSummary4Sponsors().addAll(summary4SponsorsList);
        return DISPLAY_ORG_FLD;
    }

    /**
     * Will load the program codes from database
     *
     * @throws PAException - up on error
     */
    protected void loadProgramCodes() throws PAException {
        familyProgramCodeService = PaRegistry.getProgramCodesFamilyService();

        StudyProtocolQueryDTO trialSummaryDto = (StudyProtocolQueryDTO) ServletActionContext
                .getRequest()
                .getSession()
                .getAttribute(Constants.TRIAL_SUMMARY);

        HashMap<Long, ProgramCodeDTO> programCodeIndex = new HashMap<Long, ProgramCodeDTO>();

        //find the families of the lead organization
        List<OrgFamilyDTO> ofList = FamilyHelper.getByOrgId(trialSummaryDto.getLeadOrganizationPOId());
        for (OrgFamilyDTO of : ofList) {
            FamilyDTO familyDTO = familyProgramCodeService.getFamilyDTOByPoId(of.getId());
            if (familyDTO == null) {
                continue;
            }
            for (ProgramCodeDTO pgc : familyDTO.getProgramCodes()) {
                if (pgc.isActive()) {
                    programCodeIndex.put(pgc.getId(), pgc);
                }
            }
        }

        //also add the program codes accumulated on the study from other sites
        for (ProgramCodeDTO pgc : trialSummaryDto.getProgramCodes()) {
            programCodeIndex.put(pgc.getId(), pgc);
        }

        setCancerTrial(!programCodeIndex.isEmpty());
        programCodeList.addAll(programCodeIndex.values());
        Collections.sort(programCodeList, new Comparator<ProgramCodeDTO>() {
            @Override
            public int compare(ProgramCodeDTO o1, ProgramCodeDTO o2) {
                return o1.getProgramCode().compareTo(o2.getProgramCode());
            }
        });


    }

    /**
     * Will sync the selected program code ids
     */
    protected void syncProgramCodes() {
        programCodeIds = new ArrayList<Long>();
        for (ProgramCodeDTO pgc : nciSpecificInformationWebDTO.getProgramCodes()) {
            programCodeIds.add(pgc.getId());
        }
    }

    /**
     * Will bind the program codes
     */
    protected void bindProgramCodes() {
        nciSpecificInformationWebDTO.getProgramCodes().clear();
        if (CollectionUtils.isEmpty(programCodeIds)) {
            return;
        }

        for (Long pgcId : programCodeIds) {
            for (ProgramCodeDTO pgc : programCodeList) {
                if (pgc.getId().equals(pgcId)) {
                    nciSpecificInformationWebDTO.getProgramCodes().add(pgc);
                }
            }
        }
    }

    /**
     * @return the chosenOrg
     */
    public String getChosenOrg() {
        return chosenOrg;
    }

    /**
     * @param chosenOrg the chosenOrg to set
     */
    public void setChosenOrg(String chosenOrg) {
        this.chosenOrg = chosenOrg;
    }

    /**
     * @return String success or failure
     */
    public String lookup1() {
        return SUCCESS;
    }
    
    /**
     * @return ConsortiaTrialCategoryValueMap
     */
    public Map<String, String> getConsortiaTrialCategoryValueMap() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (String code : ConsortiaTrialCategoryCode.getDisplayNames()) {
            map.put(code, "No - " + code);
        }
        return map;
    }
    
    /**
     * @return ConsortiaTrialCategoryValueMap as JSON
     */
    public String getConsortiaTrialCategoryValueMapJSON() {
        return new Gson().toJson(getConsortiaTrialCategoryValueMap());
    }
    
    /**
     * @return a JSON map between summary 4 funding codes and list of applicable
     *         consortia trial categories.
     */
    public String getAllowableConsortiaCategoriesJSON() {
        Map<String, Collection<String>> map = new HashMap<String, Collection<String>>();
        for (ConsortiaTrialCategoryCode code : ConsortiaTrialCategoryCode
                .values()) {
            for (String sum4Cat : StringUtils.defaultString(
                    code.getSummary4FundingCodes()).split(";")) {
                Collection<String> set = map.get(sum4Cat);
                if (set == null) {
                    set = new LinkedHashSet<String>();
                    map.put(sum4Cat, set);
                }
                set.add(code.getCode());
            }
        }
        return new Gson().toJson(map);
    }

    /**
     * @param paServiceUtil the paServiceUtil to set
     */
    public void setPaServiceUtil(PAServiceUtils paServiceUtil) {
        this.paServiceUtil = paServiceUtil;
    }

    /**
     * Will set the family programcode service
     * @param familyProgramCodeService  the service class
     */
    public void setFamilyProgramCodeService(FamilyProgramCodeService familyProgramCodeService) {
        this.familyProgramCodeService = familyProgramCodeService;
    }

    /**
     * The ids of the program codes that were selected
     *
     * @return list having program code ids
     */
    public List<Long> getProgramCodeIds() {
        return programCodeIds;
    }

    /**
     * Sets the ids of the program codes
     *
     * @param programCodeIds - list of program code ids
     */
    public void setProgramCodeIds(List<Long> programCodeIds) {
        this.programCodeIds = programCodeIds;
    }

    /**
     * Will return the valid program codes
     *
     * @return - valid program codes
     */
    public List<ProgramCodeDTO> getProgramCodeList() {
        return programCodeList;
    }

    /**
     * Will set the valid program codes
     * @param programCodeList - valid program codes
     */
    public void setProgramCodeList(List<ProgramCodeDTO> programCodeList) {
        this.programCodeList = programCodeList;
    }

    /**
     * Sets if cancerTrial or not
     *
     * @return - true if cancer trial
     */
    public boolean isCancerTrial() {
        return cancerTrial;
    }

    /**
     * Sets cancer trial
     *
     * @param cancerTrial - true if cancer trial
     */
    public void setCancerTrial(boolean cancerTrial) {
        this.cancerTrial = cancerTrial;
    }

}

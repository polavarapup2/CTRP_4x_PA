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
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.StudyContactWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
/**
 *
 * @author chandrasekaravr
 * @since 08/2015
 * copyright NCI 2015.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */

@SuppressWarnings({ "PMD.TooManyFields", "PMD.TooManyMethods", "PMD.ExcessiveMethodLength"
    , "PMD.SignatureDeclareThrowsException" , "PMD.ExcessiveClassLength", "PMD.CyclomaticComplexity" })
public class ResultsReportingContactsAction extends ActionSupport implements
ServletRequestAware , Preparable {
    
    private static final long serialVersionUID = 5340547992533377701L;

    private static final String ERROR_DC_MSG = "Error while adding/updating designee contact";
    private static final String ERROR_PC_MSG = "Error while adding/updating pio contact";
   
    private static final String RSLTS_RPRTNG_PIO_WEB_CNTCTS = "RSLTS_RPRTNG_PIO_WEB_CNTCTS";   
    private static final String RSLTS_RPRTNG_DSGNEE_WEB_CNTCTS = "RSLTS_RPRTNG_DSGNEE_WEB_CNTCTS";
    private static final String RSLTS_RPRTNG_PIO_CNTCTS = "RSLTS_RPRTNG_PIO_CNTCTS";   
    private static final String RSLTS_RPRTNG_DSGNEE_CNTCTS = "RSLTS_RPRTNG_DSGNEE_CNTCTS";
    
    private static final String ADD_STR = "add";
    private static final String EDIT_STR = "edit";
    
    private HttpServletRequest request;
    
    private StudyContactService studyContactService;
    
    private Long studyProtocolId;
    private Long leadOrgId;
    
    private List<StudyContactDTO> studyDesigneeContactDtos;
    private List<StudyContactDTO> studyPioContactDtos;
    
    private List<StudyContactWebDTO> studyDesigneeContactWebDtos;
    private List<StudyContactWebDTO> studyPioContactWebDtos;
    
    private StudyContactWebDTO editedDesigneeSCWebDTO;
    private StudyContactWebDTO editedPioSCWebDTO;
    
    private Long dscToEdit;
    private Long pscToEdit;
    
    private String process;
    private String contactType;
    private String dcCountry;
    private String pcCountry;
    
    
    
    private PAServiceUtils paSvcUtils = new PAServiceUtils();
    
    private static final Logger LOG = Logger
            .getLogger(ResultsReportingContactsAction.class);
    
    @Override
    public void prepare() throws Exception {
        studyContactService = PaRegistry.getStudyContactService();
        
        StudyProtocolQueryDTO spQDto = (StudyProtocolQueryDTO) 
                request.getSession().getAttribute(Constants.TRIAL_SUMMARY);
        leadOrgId = spQDto.getLeadOrganizationPOId();
    }
    
    /**
     * execute method
     * @return result
     */
    public String execute() {
        try {
            clearErrorsAndMessages();
            clearSession();
            
            queryDetails();
        } catch (Exception e) {
            //remove attribute in case of failure
            request.removeAttribute(Constants.SUCCESS_MESSAGE);
            addActionError(e.getLocalizedMessage());
        }
        
        return INPUT;
    }
    
    /** 
     * Study designee and pio contacts data
     * @return result
     */
    public String query()  {
        
        try {
            clearErrorsAndMessages();
            clearSession();
            
            queryDetails();
        } catch (Exception e) {
            
            //remove attribute in case of failure
            request.removeAttribute(Constants.SUCCESS_MESSAGE);
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * @throws PAException
     * @throws TooManyResultsException
     */
    private void queryDetails() throws PAException, TooManyResultsException {
        try {
            PaHibernateUtil.getCurrentSession().flush();
            PaHibernateUtil.getCurrentSession().clear();
            Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(getStudyProtocolId());
            LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
            StudyContactDTO searchCriteria = new StudyContactDTO();
            searchCriteria.setStudyProtocolIdentifier(studyProtocolIi);
            
            searchCriteria.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.DESIGNEE_CONTACT));
            studyDesigneeContactDtos = studyContactService.search(searchCriteria, limit);
            studyDesigneeContactWebDtos = new ArrayList<StudyContactWebDTO>();
            populateWebDtos(studyDesigneeContactWebDtos, studyDesigneeContactDtos);
            
            searchCriteria.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.PIO_CONTACT));
            studyPioContactDtos = studyContactService.search(searchCriteria, limit);
            studyPioContactWebDtos = new ArrayList<StudyContactWebDTO>();
            populateWebDtos(studyPioContactWebDtos, studyPioContactDtos);
            
            
            populateSession();
            editedDesigneeSCWebDTO = initWebDto();
            editedPioSCWebDTO = initWebDto();
            setProcess(ADD_STR);
        } catch (Exception e) {
            throw new PAException(e);
        }
    }

    /**
     * @throws PAException
     */
    private void populateWebDtos(List<StudyContactWebDTO> scWebDtos, List<StudyContactDTO> scDtos) throws Exception {
        if (CollectionUtils.isNotEmpty(scDtos)) {
            for (StudyContactDTO scDto : scDtos) {
                FunctionalRoleStatusCode stsCd = CdConverter.convertCdToEnum(FunctionalRoleStatusCode.class, 
                        scDto.getStatusCode());
                if (!FunctionalRoleStatusCode.ACTIVE.equals(stsCd) 
                        && !FunctionalRoleStatusCode.PENDING.equals(stsCd)
                        && !FunctionalRoleStatusCode.SUSPENDED.equals(stsCd)) {
                    continue;
                }
                
                final StudyContactWebDTO webDTO = new StudyContactWebDTO(scDto);
                scWebDtos.add(webDTO);
                LOG.info("Added webDTO: "
                        + ToStringBuilder.reflectionToString(webDTO));
            }
        }
    }
    
    
    /**
     * Save all the changes
     * @return string
     * @throws PAException PAException
     */
    public String saveFinalChanges() throws PAException {
        try {
            clearErrorsAndMessages();
            retriveSession();
            
            saveChanges(studyDesigneeContactWebDtos, studyDesigneeContactDtos);
            saveChanges(studyPioContactWebDtos, studyPioContactDtos);
            
            request.setAttribute(Constants.SUCCESS_MESSAGE,
                    "Saved final changes to study contacts successfully");
            clearSession();
            
            queryDetails();
        } catch (Exception e) {
            //remove attribute in case of failure
            request.removeAttribute(Constants.SUCCESS_MESSAGE);
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
        
        return SUCCESS;
    }
    
    
    /**
     * Initiates selected designee contact info for view/edit
     * @return result
     */
    public String viewDesigneeStudyContact() {
        clearErrorsAndMessages();
        retriveSession();
        
        if (dscToEdit == null) {
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    "Please select a valid designee study contact to view");
            return ERROR;
        }
        
        for (StudyContactWebDTO scWebDTO : studyDesigneeContactWebDtos) {
            if (dscToEdit.equals(scWebDTO.getId())) {
                editedDesigneeSCWebDTO = scWebDTO;
                break;
            }
        }
        contactType = "dc";
        dcCountry = editedDesigneeSCWebDTO.getCountry();
        return SUCCESS;
    }
    
    /**
     * Initiates selected pio contact info for view/edit
     * @return result
     */
    public String viewPioStudyContact() {
        clearErrorsAndMessages();
        retriveSession();
        
        if (pscToEdit == null) {
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    "Please select a valid PIO study contact to view");
            return ERROR;
        }
        
        for (StudyContactWebDTO scWebDTO : studyPioContactWebDtos) {
            if (pscToEdit.equals(scWebDTO.getId())) {
                editedPioSCWebDTO = scWebDTO;
                break;
            }
        }
        contactType = "pc";
        pcCountry = editedPioSCWebDTO.getCountry();
        return SUCCESS;
    }
    
    /**
     * Deletes selected study contact
     * @return result string
     * @throws PAException PAException
     */
    public String delete() throws PAException {
        try { 
            clearErrorsAndMessages();
            retriveSession();
            
            if (dscToEdit == null && pscToEdit == null) {
                request.setAttribute(Constants.FAILURE_MESSAGE,
                        "Please select a valid designee/PIO contact to delete");
                return ERROR;
            }
            
            Map<Long, StudyContactDTO> scMap = null;
            ListIterator<StudyContactWebDTO> listIter = null;
            Long scToDelete = null;
            if (dscToEdit != null) {
                scToDelete = dscToEdit;
                scMap = getAsMap(studyDesigneeContactDtos);
                listIter = studyDesigneeContactWebDtos.listIterator();
            } else {
                scToDelete = pscToEdit;
                scMap = getAsMap(studyPioContactDtos);
                listIter = studyPioContactWebDtos.listIterator();
            }
            LOG.info("scToDelete=" + scToDelete);
            LOG.info("scMap=" + scMap);
            while (listIter.hasNext()) {
                final StudyContactWebDTO studyContactWebDTO = (StudyContactWebDTO) listIter
                        .next();
                LOG.info("studyContactWebDTO="
                        + ToStringBuilder
                                .reflectionToString(studyContactWebDTO));
                if (studyContactWebDTO.getId().equals(scToDelete)) {
                    if (scToDelete > 0) {
                        StudyContactDTO scDto = scMap.get(scToDelete);                        
                        LOG.info("Deleting: "
                                + ToStringBuilder.reflectionToString(scDto));
                        studyContactService.nullify(scDto);
                    } else {
                        listIter.remove();
                    }
                } 
            }
            clearSession();
            
            queryDetails();
            request.setAttribute(Constants.SUCCESS_MESSAGE,
                    "Selected designee/PIO study contact deleted successfully");
            
        } catch (Exception e) {
            //remove attribute in case of failure
            request.removeAttribute(Constants.SUCCESS_MESSAGE);
            addActionError("Error deleting study contact. Error: " + e.getLocalizedMessage());
            return ERROR;
        }
        
        return SUCCESS;
    }
    
    
    /**
     * @return Action result.
     * @throws IOException
     *             IOException
     * @throws TooManyResultsException TooManyResultsException
     */
    public String addOrEditDesigneeContact() throws IOException, TooManyResultsException {
        try {
            clearErrorsAndMessages();
            retriveSession();
            
            if (!validateScWebDto(editedDesigneeSCWebDTO, StudyContactRoleCode.DESIGNEE_CONTACT)) {
                return ERROR;
            }
            if (checkDuplicate(editedDesigneeSCWebDTO, StudyContactRoleCode.DESIGNEE_CONTACT)) {
                request.setAttribute(Constants.FAILURE_MESSAGE,
                        "Duplicate designee study contact");
                return ERROR;
            }

            populatePOOrgAndPerson(editedDesigneeSCWebDTO);

            
            if (EDIT_STR.equals(process)) {
                editDesigneeContact();
            } else if (ADD_STR.equals(process)) {
                addDesigneeContact();
            }
            
            saveChanges(studyDesigneeContactWebDtos, studyDesigneeContactDtos);
            queryDetails();
            
            request.setAttribute(Constants.SUCCESS_MESSAGE,
                    "Designee contact has been added/updated successfully");
        } catch (PAException e) {
            e.printStackTrace();
            LOG.error(ERROR_DC_MSG, e);
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    ERROR_DC_MSG);
            return ERROR;
        }
        return SUCCESS;
    }
    
    /**
     * @return Action result.
     * @throws IOException
     *             IOException
     * @throws TooManyResultsException TooManyResultsException
     */
    public String addOrEditPIOContact() throws IOException, TooManyResultsException {
        try {
            clearErrorsAndMessages();
            retriveSession();
            
            if (!validateScWebDto(editedPioSCWebDTO, StudyContactRoleCode.PIO_CONTACT)) {
                return ERROR;
            }
            
            if (checkDuplicate(editedPioSCWebDTO, StudyContactRoleCode.PIO_CONTACT)) {
                request.setAttribute(Constants.FAILURE_MESSAGE,
                        "Duplicate PIO study contact");
                return ERROR;
            }
            
            editedPioSCWebDTO.setSelPoOrgId(leadOrgId.toString());
            populatePOOrgAndPerson(editedPioSCWebDTO);
          
            
            if (EDIT_STR.equals(process)) {
                editPIOContact();
            } else if (ADD_STR.equals(process)) {
                addPIOContact();
            }
           
            saveChanges(studyPioContactWebDtos, studyPioContactDtos);
            queryDetails();
            
            request.setAttribute(Constants.SUCCESS_MESSAGE,
                    "PIO contact has been added/updated successfully");
        } catch (PAException e) {
            LOG.error(ERROR_PC_MSG, e);
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    ERROR_PC_MSG);
            return ERROR;
        }
        return SUCCESS;
    }
    
    private void addDesigneeContact() throws PAException {
        editedDesigneeSCWebDTO.setUpdated(true);
        editedDesigneeSCWebDTO.setRoleCode(StudyContactRoleCode.DESIGNEE_CONTACT.getCode());
        editedDesigneeSCWebDTO.setStudyProtocolId(studyProtocolId);
        studyDesigneeContactWebDtos.add(editedDesigneeSCWebDTO);
        editedDesigneeSCWebDTO = initWebDto();
    }

    private void editDesigneeContact() throws PAException {
        ListIterator<StudyContactWebDTO> listIter = studyDesigneeContactWebDtos.listIterator();
        while (listIter.hasNext()) {
            StudyContactWebDTO studyContactWebDTO = (StudyContactWebDTO) listIter
                    .next();
            if (studyContactWebDTO.getId().equals(editedDesigneeSCWebDTO.getId())) {
                editedDesigneeSCWebDTO.setRoleCode(StudyContactRoleCode.DESIGNEE_CONTACT.getCode());
                editedDesigneeSCWebDTO.setStudyProtocolId(studyProtocolId);
                
                editedDesigneeSCWebDTO.setUpdated(true);
                listIter.set(editedDesigneeSCWebDTO);
                break;
            }
        }
        editedDesigneeSCWebDTO = initWebDto();
    }
    
    private void addPIOContact() throws PAException {
        editedPioSCWebDTO.setUpdated(true);
        editedPioSCWebDTO.setRoleCode(StudyContactRoleCode.PIO_CONTACT.getCode());
        editedPioSCWebDTO.setStudyProtocolId(studyProtocolId);
        studyPioContactWebDtos.add(editedPioSCWebDTO);
        editedPioSCWebDTO = initWebDto();
    }

    private void editPIOContact() throws PAException {
        ListIterator<StudyContactWebDTO> listIter = studyPioContactWebDtos.listIterator();
        while (listIter.hasNext()) {
            StudyContactWebDTO studyContactWebDTO = (StudyContactWebDTO) listIter
                    .next();
            if (studyContactWebDTO.getId().equals(editedPioSCWebDTO.getId())) {
                editedPioSCWebDTO.setRoleCode(StudyContactRoleCode.PIO_CONTACT.getCode());
                editedPioSCWebDTO.setStudyProtocolId(studyProtocolId);
                
                editedPioSCWebDTO.setUpdated(true);
                listIter.set(editedPioSCWebDTO);
                break;
            }
        }
        editedPioSCWebDTO = initWebDto();
    }
    
    private StudyContactWebDTO initWebDto() {
        StudyContactWebDTO scWebDTO = new StudyContactWebDTO();
        //setting -ve id to support newitems
        scWebDTO.setId(-1 * System.currentTimeMillis());
        scWebDTO.setStudyProtocolId(studyProtocolId);
        return scWebDTO;
    }
   
    private void populatePOOrgAndPerson(StudyContactWebDTO editedSCWebDTO)
            throws PAException {
        Organization o = getPaOrganizationByPoId(editedSCWebDTO.getSelPoOrgId());
        Person p = getPaPersonByPoId(editedSCWebDTO.getSelPoPrsnId());
        
        populateOrgInfo(editedSCWebDTO, o);
        populatePersonInfo(editedSCWebDTO, p);
    }

    private void populatePersonInfo(StudyContactWebDTO studyContactWebDTO, Person p) {
        studyContactWebDTO.setContactPerson(p);
        studyContactWebDTO.setEditedPrsnNm(p.getFullName());
        studyContactWebDTO.setEditedPoPrsnId(p.getIdentifier());
    }

    private void populateOrgInfo(StudyContactWebDTO studyContactWebDTO, Organization o) {
        studyContactWebDTO.setContactOrg(o);
        studyContactWebDTO.setEditedOrgNm(o.getName());
        studyContactWebDTO.setEditedPoOrgId(o.getIdentifier());
    }
    
    private Organization getPaOrganizationByPoId(String orgPoId) throws PAException {
        return paSvcUtils.getOrCreatePAOrganizationByIi(IiConverter.convertToPoOrganizationIi(orgPoId));
    }
    
    private Person getPaPersonByPoId(String prsnPoId) throws PAException {
        return paSvcUtils.getOrCreatePAPersonByPoIi(IiConverter.convertToPoPersonIi(prsnPoId));
    }
 
    
    private boolean validateScWebDto(StudyContactWebDTO scWebDto, StudyContactRoleCode scrStsCd) {
        if (scWebDto == null) {
            return false;
        }
        String pfx = StudyContactRoleCode.DESIGNEE_CONTACT.equals(scrStsCd)  
                ? "editedDesigneeSCWebDTO." : "editedPioSCWebDTO.";
        
        if (StudyContactRoleCode.DESIGNEE_CONTACT.equals(scrStsCd)
                && StringUtils.isEmpty(scWebDto.getSelPoOrgId())) {
            addFieldError(pfx + "contactOrg", "Organization is required");
        }
        
        if (StringUtils.isEmpty(scWebDto.getSelPoPrsnId())) {
            addFieldError(pfx + "contactPerson", "Name is required");
        }
        
        if (StringUtils.isEmpty(scWebDto.getEmail())) {
            addFieldError(pfx + "email", "Email is required");
        } else if (!PAUtil.isValidEmail(scWebDto.getEmail()))  {
            addFieldError(pfx + "email", "Invalid email address");
        }
        
        return !hasFieldErrors();
    }
    
   
    
    private boolean checkDuplicate(StudyContactWebDTO scWebDto, StudyContactRoleCode scrStsCd) {
        if (scWebDto == null) {
            return false;
        }
        List<StudyContactWebDTO> scWebDtos = StudyContactRoleCode.DESIGNEE_CONTACT.equals(scrStsCd) 
                ? studyDesigneeContactWebDtos : studyPioContactWebDtos;
        StudyContactWebDTO match = (StudyContactWebDTO) 
                CollectionUtils.find(scWebDtos, PredicateUtils.equalPredicate(scWebDto));
        return match == null ? false : !match.getId().equals(scWebDto.getId());
    }
        
    
    private void populateSession() {
        request.getSession().setAttribute(RSLTS_RPRTNG_DSGNEE_WEB_CNTCTS
                , studyDesigneeContactWebDtos);
        request.getSession().setAttribute(RSLTS_RPRTNG_PIO_WEB_CNTCTS
                , studyPioContactWebDtos);
        request.getSession().setAttribute(RSLTS_RPRTNG_DSGNEE_CNTCTS
                , studyDesigneeContactDtos);
        request.getSession().setAttribute(RSLTS_RPRTNG_PIO_CNTCTS
                , studyPioContactDtos);
    }
    
    private void clearSession() {
        request.getSession().removeAttribute(RSLTS_RPRTNG_DSGNEE_WEB_CNTCTS);
        request.getSession().removeAttribute(RSLTS_RPRTNG_PIO_WEB_CNTCTS);
        request.getSession().removeAttribute(RSLTS_RPRTNG_DSGNEE_CNTCTS);
        request.getSession().removeAttribute(RSLTS_RPRTNG_PIO_CNTCTS);
    }
    
    @SuppressWarnings("unchecked")
    private void retriveSession() {
        studyDesigneeContactWebDtos = (List<StudyContactWebDTO>) 
                request.getSession().getAttribute(RSLTS_RPRTNG_DSGNEE_WEB_CNTCTS);
        studyPioContactWebDtos = (List<StudyContactWebDTO>) 
                request.getSession().getAttribute(RSLTS_RPRTNG_PIO_WEB_CNTCTS);
        studyDesigneeContactDtos = (List<StudyContactDTO>) 
                request.getSession().getAttribute(RSLTS_RPRTNG_DSGNEE_CNTCTS);
        studyPioContactDtos = (List<StudyContactDTO>) 
                request.getSession().getAttribute(RSLTS_RPRTNG_PIO_CNTCTS);
    }
    
    private void saveChanges(List<StudyContactWebDTO> scWebDtos, List<StudyContactDTO> scDtos) throws PAException {
        Map<Long, StudyContactDTO> scMap = getAsMap(scDtos);
        
        for (StudyContactWebDTO scWebDto : scWebDtos) {
             if (scWebDto.isUpdated() && scWebDto.getId() > 0) {
                studyContactService.update(
                        scWebDto.convertToStudyContactDto(scMap.get(scWebDto.getId())));
            } else if (scWebDto.isUpdated() && scWebDto.getId() < 0) {
                studyContactService.create(scWebDto.convertToStudyContactDto(null));
            }
        }
    }
    
    private Map<Long, StudyContactDTO> getAsMap(List<StudyContactDTO> scDtos) {
        Map<Long, StudyContactDTO> scDtoMap = new HashMap<Long, StudyContactDTO>();
        if (scDtos == null || scDtos.isEmpty()) {
            return scDtoMap;
        }
        for (StudyContactDTO scDTO : scDtos) {
            scDtoMap.put(IiConverter.convertToLong(scDTO.getIdentifier()), scDTO);
        }
        return scDtoMap;
    }
    
   
    @Override
    public void setServletRequest(HttpServletRequest httpRequest) {
        this.request = httpRequest; 
        
    }

    /**
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * @param studyProtocolId studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * @return the studyContactService
     */
    public StudyContactService getStudyContactService() {
        return studyContactService;
    }

    /**
     * @param studyContactService the studyContactService to set
     */
    public void setStudyContactService(StudyContactService studyContactService) {
        this.studyContactService = studyContactService;
    }
    

    /**
     * @return the studyDesigneeContactDtos
     */
    public List<StudyContactDTO> getStudyDesigneeContactDtos() {
        return studyDesigneeContactDtos;
    }

    /**
     * @param studyDesigneeContactDtos the studyDesigneeContactDtos to set
     */
    public void setStudyDesigneeContactDtos(
            List<StudyContactDTO> studyDesigneeContactDtos) {
        this.studyDesigneeContactDtos = studyDesigneeContactDtos;
    }

    /**
     * @return the studyPioContactDtos
     */
    public List<StudyContactDTO> getStudyPioContactDtos() {
        return studyPioContactDtos;
    }

    /**
     * @param studyPioContactDtos the studyPioContactDtos to set
     */
    public void setStudyPioContactDtos(List<StudyContactDTO> studyPioContactDtos) {
        this.studyPioContactDtos = studyPioContactDtos;
    }
    
    /**
     * @return the studyDesigneeContactWebDtos
     */
    public List<StudyContactWebDTO> getStudyDesigneeContactWebDtos() {
        return studyDesigneeContactWebDtos;
    }

    /**
     * @param studyDesigneeContactWebDtos the studyDesigneeContactWebDtos to set
     */
    public void setStudyDesigneeContactWebDtos(
            List<StudyContactWebDTO> studyDesigneeContactWebDtos) {
        this.studyDesigneeContactWebDtos = studyDesigneeContactWebDtos;
    }

    /**
     * @return the studyPioContactWebDtos
     */
    public List<StudyContactWebDTO> getStudyPioContactWebDtos() {
        return studyPioContactWebDtos;
    }

    /**
     * @param studyPioContactWebDtos the studyPioContactWebDtos to set
     */
    public void setStudyPioContactWebDtos(
            List<StudyContactWebDTO> studyPioContactWebDtos) {
        this.studyPioContactWebDtos = studyPioContactWebDtos;
    }

    /**
     * @return the editedDesigneeSCWebDTO
     */
    public StudyContactWebDTO getEditedDesigneeSCWebDTO() {
        return editedDesigneeSCWebDTO;
    }

    /**
     * @param editedDesigneeSCWebDTO the editedDesigneeSCWebDTO to set
     */
    public void setEditedDesigneeSCWebDTO(StudyContactWebDTO editedDesigneeSCWebDTO) {
        this.editedDesigneeSCWebDTO = editedDesigneeSCWebDTO;
    }

    /**
     * @return the editedPioSCWebDTO
     */
    public StudyContactWebDTO getEditedPioSCWebDTO() {
        return editedPioSCWebDTO;
    }

    /**
     * @param editedPioSCWebDTO the editedPioSCWebDTO to set
     */
    public void setEditedPioSCWebDTO(StudyContactWebDTO editedPioSCWebDTO) {
        this.editedPioSCWebDTO = editedPioSCWebDTO;
    }

    /**
     * @return the dscToEdit
     */
    public Long getDscToEdit() {
        return dscToEdit;
    }

    /**
     * @param dscToEdit the dscToEdit to set
     */
    public void setDscToEdit(Long dscToEdit) {
        this.dscToEdit = dscToEdit;
    }

    /**
     * @return the pscToEdit
     */
    public Long getPscToEdit() {
        return pscToEdit;
    }

    /**
     * @param pscToEdit the pscToEdit to set
     */
    public void setPscToEdit(Long pscToEdit) {
        this.pscToEdit = pscToEdit;
    }

    /**
     * @return the process
     */
    public String getProcess() {
        return process;
    }

    /**
     * @param process the process to set
     */
    public void setProcess(String process) {
        this.process = process;
    }

    /**
     * @return the leadOrgId
     */
    public Long getLeadOrgId() {
        return leadOrgId;
    }

    /**
     * @param leadOrgId the leadOrgId to set
     */
    public void setLeadOrgId(Long leadOrgId) {
        this.leadOrgId = leadOrgId;
    }

    /**
     * @return the paSvcUtils
     */
    public PAServiceUtils getPaSvcUtils() {
        return paSvcUtils;
    }

    /**
     * @param paSvcUtils the paSvcUtils to set
     */
    public void setPaSvcUtils(PAServiceUtils paSvcUtils) {
        this.paSvcUtils = paSvcUtils;
    }

    /**
     * @return contactType
     */
    public String getContactType() {
        return contactType;
    }

    /**
     * @param contactType contactType
     */
    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    /**
     * @return dcCountry
     */
    public String getDcCountry() {
        return dcCountry;
    }

    /**
     * @param dcCountry dcCountry
     */
    public void setDcCountry(String dcCountry) {
        this.dcCountry = dcCountry;
    }

    /**
     * @return pcCountry
     */
    public String getPcCountry() {
        return pcCountry;
    }

    /**
     * @param pcCountry pcCountry
     */
    public void setPcCountry(String pcCountry) {
        this.pcCountry = pcCountry;
    }

    
}

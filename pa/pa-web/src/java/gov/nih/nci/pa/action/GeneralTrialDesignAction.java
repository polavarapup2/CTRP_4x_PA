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
package gov.nih.nci.pa.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.CorrelationUtilsRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 * action for edit general trial design.
 * @author NAmiruddin
 *
 */
@SuppressWarnings("PMD.TooManyMethods")
public class GeneralTrialDesignAction extends AbstractGeneralTrialDesignAction {

    private static final long serialVersionUID = -541776965053776382L;
    
    private CorrelationUtilsRemote correlationUtils = new CorrelationUtils();

    private static final String RESULT = "edit";
    
    private static final Logger LOG = Logger.getLogger(GeneralTrialDesignAction.class);

    /**
     * @return res
     */
    public String query() {
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession()
                .getAttribute(Constants.STUDY_PROTOCOL_II);
            TrialHelper helper = new TrialHelper();
            gtdDTO = helper.getTrialDTO(studyProtocolIi, "Abstraction");
            populateOtherIdentifiers();
        } catch (NullifiedRoleException e) {
            ServletActionContext.getRequest().setAttribute("failureMessage", e.getLocalizedMessage());
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return RESULT;
    }

    /**
     * @return result
     */
    public String update() {
        enforceBusinessRules();
        final HttpServletRequest req = ServletActionContext.getRequest();
        
        if (hasFieldErrors()) {
            req.setAttribute(
                    Constants.FAILURE_MESSAGE,
                    "Update failed; please see errors below");
            return RESULT;
        }
        try {
            save();
        } catch (Exception e) {
            LOG.error(e, e);
            final String msg = StringUtils.isBlank(e.getLocalizedMessage()) ? "An internal error has occurred"
                    : e.getLocalizedMessage();
            req.setAttribute(Constants.FAILURE_MESSAGE, msg);
        }
        return RESULT;
    }

    

    private void save() throws PAException, NullifiedEntityException, NullifiedRoleException, CurationException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        Ii studyProtocolIi = (Ii) session.getAttribute(Constants.STUDY_PROTOCOL_II);
        
        TrialHelper helper = new TrialHelper();
        gtdDTO.setProgramCodes(PaRegistry.getStudyProtocolService()
                .getStudyProtocol(studyProtocolIi).getProgramCodes());
        helper.saveTrial(studyProtocolIi, gtdDTO, "Abstraction");
        StudyProtocolQueryDTO studyProtocolQueryDTO = PaRegistry.getProtocolQueryService()
            .getTrialSummaryByStudyProtocolId(Long.valueOf(studyProtocolIi.getExtension()));

        studyProtocolQueryDTO.setOfficialTitle(StringUtils.abbreviate(studyProtocolQueryDTO.getOfficialTitle(),
                                                                      PAAttributeMaxLen.DISPLAY_OFFICIAL_TITLE));
        session.setAttribute(Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
        request.setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        if (studyProtocolQueryDTO.getPiId() != null) {
            Person piPersonInfo =
                    correlationUtils.getPAPersonByIi(IiConverter.convertToPaPersonIi(studyProtocolQueryDTO
                            .getPiId()));
            session.setAttribute(Constants.PI_PO_ID, piPersonInfo.getIdentifier());
        }
        populateOtherIdentifiers();
        query();
    }

    /**
     * Removes/Deletes the Central Contact from General Trial Details when the user click on the remove button.
     * @return result
     */
    public String removeCentralContact() {
        final HttpServletRequest req = ServletActionContext.getRequest();
        try {
            Ii studyProtocolIi = (Ii) req.getSession()
                .getAttribute(Constants.STUDY_PROTOCOL_II);
            StudyContactDTO scDto = new StudyContactDTO();

            scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.CENTRAL_CONTACT));
            List<StudyContactDTO> scDtos = PaRegistry.getStudyContactService().getByStudyProtocol(studyProtocolIi,
                                                                                                  scDto);
            if (scDtos != null && !scDtos.isEmpty()) {
                scDto = scDtos.get(0);
                PaRegistry.getStudyContactService().delete(scDtos.get(0).getIdentifier());
            }

            gtdDTO.setCentralContactEmail("");
            gtdDTO.setCentralContactName("");
            gtdDTO.setCentralContactPhone("");
            gtdDTO.setCentralContactIdentifier("");
            gtdDTO.setCentralContactTitle("");
            
            req.setAttribute(Constants.SUCCESS_MESSAGE, "Central contact removed");

        } catch (Exception e) {
            req.setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }

        return RESULT;
    }

    private void enforceBusinessRules() {
        if (StringUtils.isEmpty(gtdDTO.getOfficialTitle())) {
            addFieldError("gtdDTO.officialTitle", getText("OfficialTitle must be Entered"));
        }
        if (isCentralContactSet()) {
            validateCentralContact();
        }
        if ((isNotProprietary() && gtdDTO.isCtGovXmlRequired())
                || (!isNotProprietary() && StringUtils.isNotEmpty(gtdDTO
                        .getSponsorIdentifier()))) {
            validateCtGovXmlRequiredFields();
        }
        
        validateResponsibleParty();
    }
      

    private boolean isCentralContactSet() {
        return StringUtils.isNotEmpty(gtdDTO.getCentralContactIdentifier())
                || StringUtils.isNotEmpty(gtdDTO.getCentralContactPhone())
                || StringUtils.isNotEmpty(gtdDTO.getCentralContactEmail());
    }

    private void validateCentralContact() {
        if (StringUtils.isEmpty(gtdDTO.getCentralContactName())
                && StringUtils.isEmpty(gtdDTO.getCentralContactTitle())) {
            addFieldError("gtdDTO.centralContactName", getText("Central contact Name or Title must be entered"));
        }
        if (StringUtils.isEmpty(gtdDTO.getCentralContactPhone())) {
            addFieldError("gtdDTO.centralContactPhone", getText("Central Contact Phone must be Entered"));
        }
        if (StringUtils.isEmpty(gtdDTO.getCentralContactEmail())) {
            addFieldError("gtdDTO.centralContactEmail", getText("Central Contact Email must be Entered"));
        } else if (!PAUtil.isValidEmail(gtdDTO.getCentralContactEmail())) {
            addFieldError("gtdDTO.centralContactEmail", getText("Central Contact Email is not a valid format"));
        }
    }

    
    private void populateOtherIdentifiers() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute(Constants.OTHER_IDENTIFIERS_LIST, gtdDTO.getOtherIdentifiers());
        session.setAttribute("nctIdentifier", gtdDTO.getNctIdentifier());
        session.setAttribute("nciIdentifier", gtdDTO.getAssignedIdentifier());
    }

    
    
    /**
     * @param correlationUtils the correlationUtils to set
     */
    public void setCorrelationUtils(CorrelationUtilsRemote correlationUtils) {
        this.correlationUtils = correlationUtils;
    }
}

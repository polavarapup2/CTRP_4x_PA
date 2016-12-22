/***
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Clinical Trials Protocol Application
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
package gov.nih.nci.registry.action;

import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.pa.dto.PAOrganizationalContactDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * This action class manages the Organization Geenric contact(s).
 *
 * @author Anupama Sharma
 *
 */
public class OrganizationGenericContactAction extends ActionSupport {
    private static final String FAILURE_MESSAGE = "failureMessage";
    private static final long serialVersionUID = 1L;
    private List<PAOrganizationalContactDTO> orgContactList = new ArrayList<PAOrganizationalContactDTO>();
    private String title;
    private String orgGenericContactIdentifier;
    private String email;
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;


    /**
     *
     * @return res
     */
    public String lookupByTitle() {
        return SUCCESS;
    }
    /**
     *
     * @return res
     */
    public String displayTitleList() {

        if (handleErrors()) {
            return SUCCESS;
        }

        try {
            OrganizationalContactDTO contactDTO = new OrganizationalContactDTO();
            contactDTO.setScoperIdentifier(IiConverter.convertToPoOrganizationIi(this.orgGenericContactIdentifier));
            contactDTO.setTitle(StConverter.convertToSt(title));
            contactDTO.setTypeCode(CdConverter.convertStringToCd("Responsible Party"));
            List<OrganizationalContactDTO> isoDtoList = new ArrayList<OrganizationalContactDTO>();
            isoDtoList = PoRegistry.getOrganizationalContactCorrelationService().search(contactDTO);
            convertFromISO(isoDtoList);
        } catch (Exception e) {
            LOG.error("Exception occured while getting organization contact", e);
            addActionError("Exception occured while getting organization contact : " + e.getMessage());
            ServletActionContext.getRequest().setAttribute(FAILURE_MESSAGE,
                    "Exception occured while getting organization contact : " + e.getMessage());
            return SUCCESS;
        }
        return SUCCESS;
    }

    private boolean handleErrors() {
        if (isMissingSponsor()) {
            orgContactList = null;
            addActionError("Please select a Sponsor.");
        }
        if (StringUtils.isBlank(title)) {
            orgContactList = null;
            addActionError("Please enter at least one search criteria");
        }
        if (hasActionErrors()) {
            StringBuffer message = new StringBuffer();
            for (String error : getActionErrors()) {
                message.append(" - ").append(error.toString());
            }
            ServletActionContext.getRequest().setAttribute(FAILURE_MESSAGE, message);
            return true;
        }
        return false;
    }

    private boolean isMissingSponsor() {
        return orgGenericContactIdentifier != null && (orgGenericContactIdentifier.equals("undefined")
                || orgGenericContactIdentifier.equals(""));
    }

    /**
     *
     * @return s
     */
    public String ajaxCreate() {
        validateForCreate(email, phone);
        if (hasActionErrors()) {
            StringBuffer errMsg = new StringBuffer();
            for (String error : getActionErrors()) {
                errMsg.append(" - ").append(error.toString());
            }
            ServletActionContext.getRequest().setAttribute(FAILURE_MESSAGE, errMsg.toString());
            return "create_org_contact_response";
        }
        try {
            OrganizationalContactDTO contactDTO = new OrganizationalContactDTO();
            contactDTO.setScoperIdentifier(IiConverter.convertToPoOrganizationIi(orgGenericContactIdentifier));
            contactDTO.setTitle(StConverter.convertToSt(title));
            DSet<Tel> list = new DSet<Tel>();
            list.setItem(new HashSet<Tel>());

            Tel t = new Tel();
            t.setValue(new URI("tel", phone, null));
            list.getItem().add(t);

            TelEmail telemail = new TelEmail();
            telemail.setValue(new URI("mailto:" + email));
            list.getItem().add(telemail);

            contactDTO.setTelecomAddress(list);
            contactDTO.setTypeCode(CdConverter.convertStringToCd("Responsible Party"));           
            PAServiceUtils paServiceUtil = new PAServiceUtils();
            paServiceUtil.validateAndFormatPhoneNumber(
                    contactDTO.getScoperIdentifier(), contactDTO.getTelecomAddress());
            PoRegistry.getOrganizationalContactCorrelationService().createCorrelation(contactDTO);
            List<OrganizationalContactDTO> isoDtoList = new ArrayList<OrganizationalContactDTO>();
            isoDtoList = PoRegistry.getOrganizationalContactCorrelationService().search(contactDTO);
            convertFromISO(isoDtoList);
        } catch (Exception e) {
            LOG.error("Exception occured while creating organization contact", e);
            String errMsg = getErrorMessage(e);
            addActionError("Exception occured while creating organization contact: " + errMsg);
            ServletActionContext.getRequest().setAttribute(FAILURE_MESSAGE,
                    "Exception occured while creating organization contact: " + errMsg);
            return "create_org_contact_response";
        }
        return "create_org_contact_response";
    }

    private void validateForCreate(String myEmail, String myPhone) {
        if (StringUtils.isEmpty(orgGenericContactIdentifier)) {
            addActionError("Sponsor is a required field");
        }
        if (StringUtils.isEmpty(title)) {
            addActionError("Title is a required field");
        }
        if (StringUtils.isEmpty(myEmail)) {
            addActionError("Email is a required field");
        } else if (!PAUtil.isValidEmail(myEmail)) {
            addActionError("Email address is invalid");
        }
        if (StringUtils.isEmpty(myPhone)) {
            addActionError("Phone is a required field");
        }
    }

    private String getErrorMessage(Exception e) {
        String errMsg;
        if (e instanceof EntityValidationException) {
            Map<String, String[]> errMap = ((EntityValidationException) e).getErrors();
            errMsg = PAUtil.getErrorMsg(errMap);
        } else {
            errMsg = e.getMessage();
        }
        return errMsg;
    }

    /**
     * @param isoDtoList
     */
    private void convertFromISO(List<OrganizationalContactDTO> isoDtoList) {
        PAOrganizationalContactDTO dto = null;
        for (OrganizationalContactDTO isoDto : isoDtoList) {
            dto = new PAOrganizationalContactDTO();
            dto.setIdentifier(DSetConverter.convertToIi(isoDto.getIdentifier()));
            dto.setTitle(StConverter.convertToString(isoDto.getTitle()));
            //dto.setTypeCode(typeCode)
            dto.setEmails(DSetConverter.convertDSetToList(isoDto.getTelecomAddress(), "EMAIL"));
            dto.setPhones(DSetConverter.convertDSetToList(isoDto.getTelecomAddress(), "PHONE"));
            orgContactList.add(dto);
        }
    }

    /**
     * @return the orgContact
     */
    public List<PAOrganizationalContactDTO> getOrgContactList() {
        return orgContactList;
    }

    /**
     * @param orgContact the orgContact to set
     */
    public void setOrgContactList(List<PAOrganizationalContactDTO> orgContact) {
        this.orgContactList = orgContact;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the orgContactId
     */
    public String getOrgGenericContactIdentifier() {
        return orgGenericContactIdentifier;
    }

    /**
     * @param orgContactId the orgContactId to set
     */
    public void setOrgGenericContactIdentifier(String orgContactId) {
        this.orgGenericContactIdentifier = orgContactId;
    }

}

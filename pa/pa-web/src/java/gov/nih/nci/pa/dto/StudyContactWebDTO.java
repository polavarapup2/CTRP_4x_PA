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
package gov.nih.nci.pa.dto;

import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.convert.OrganizationalContactConverter;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.PABaseCorrelation;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.person.PersonSearchCriteriaDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * DTO class for displaying study contacts as a list.
 * 
 * @author chandrasekaravr
 * @since 08/02/2015 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
 @SuppressWarnings({ "PMD.TooManyFields", "PMD.TooManyMethods", "PMD.ExcessiveMethodLength"
    , "PMD.SignatureDeclareThrowsException" , "PMD.SimpleDateFormatNeedsLocale" , "PMD.ExcessiveClassLength"
    , "PMD.CyclomaticComplexity" })
public class StudyContactWebDTO implements Serializable {
   
    /**
     * 
     */
    private static final String PO_ORG_CNTCT_TYPE_SITE = "Site";

    private static final long serialVersionUID = 399744343915434679L;
    
    private Long id;
    private String orgContactId;
    private String prsUserName;
    private String email;
    private String phone;
    private String ext;
    private String comments;
    private String roleCode;
    private String statusCode;
    private Long studyProtocolId;
    
    private String selPoOrgId;
    private String selPoPrsnId;
    
    private String editedPoOrgId;
    private String editedPoPrsnId;
    
    private String editedOrgNm;
    private String editedPrsnNm;

    private Organization contactOrg = new Organization();
    private Person contactPerson = new Person();
    
    private boolean updated = false;
    private boolean deleted = false;
    
    private transient PAServiceUtils paServiceUtils = new PAServiceUtils();
    
    private String country;
    
    private transient PABaseCorrelation<PAOrganizationalContactDTO , OrganizationalContactDTO , OrganizationalContact ,
    OrganizationalContactConverter> paBaseCorrltn = new PABaseCorrelation<PAOrganizationalContactDTO ,
    OrganizationalContactDTO , OrganizationalContact , OrganizationalContactConverter>(
       PAOrganizationalContactDTO.class, OrganizationalContact.class, OrganizationalContactConverter.class);

    /**
     * default constructor
     */
    public StudyContactWebDTO() {
        super();
    }

    /**
     * @param dto The iso dto object.
     * @throws PAException  PAException
     */
    public StudyContactWebDTO(StudyContactDTO dto) throws PAException {
        populateFrom(dto); // NOPMD      
    }

    /**
     * Populates attributes with values from StudyContactDTO
     * @param dto StudyContactDTO
     * @throws PAException if any error
     */
    public void populateFrom(StudyContactDTO dto) throws PAException {
        this.id = IiConverter.convertToLong(dto.getIdentifier());
        this.orgContactId = IiConverter.convertToString(dto.getOrganizationalContactIi());
        this.prsUserName = StConverter.convertToString(dto.getPrsUserName());
        this.comments = StConverter.convertToString(dto.getComments());
        this.roleCode = CdConverter.convertCdToString(dto.getRoleCode());
        this.statusCode = CdConverter.convertCdToString(dto.getStatusCode());
        this.studyProtocolId = IiConverter.convertToLong(dto.getStudyProtocolIdentifier());
        
        OrganizationalContact oc = paServiceUtils.getOrganizationalContact(orgContactId.toString());
        
        if (oc.getOrganization() != null) {
            contactOrg = oc.getOrganization();
            editedPoOrgId = contactOrg.getIdentifier();
            selPoOrgId = contactOrg.getIdentifier();
            editedOrgNm = contactOrg.getName();
        } else {
            contactOrg = new Organization();
        }
        
        if (oc.getPerson() != null) {
            contactPerson = oc.getPerson();
            editedPoPrsnId = contactPerson.getIdentifier();
            selPoPrsnId = contactPerson.getIdentifier();
            editedPrsnNm = contactPerson.getFullName();
            
            
          
                PersonSearchCriteriaDTO personSearchCriteriaDTO = new PersonSearchCriteriaDTO();
                personSearchCriteriaDTO.setId(selPoPrsnId);
                List<PaPersonDTO> personList;
                try {
                    personList = PADomainUtils.searchPoPersons(personSearchCriteriaDTO);
                if (personList != null && personList.size() > 0) {
                    this.country = personList.get(0).getCountry();
                }
                } catch (Exception e) {
                    throw new PAException(e.getMessage());
                }
                
          
        } else {
            contactPerson = new Person();
        }
        
        List<String> emailsLst = DSetConverter.convertDSetToList(
                dto.getTelecomAddresses(), DSetConverter.TYPE_EMAIL);
        if (CollectionUtils.isNotEmpty(emailsLst)) {
            this.email = emailsLst.get(0);
        }
        
        List<String> phoneLst = DSetConverter.convertDSetToList(
                dto.getTelecomAddresses(), DSetConverter.TYPE_PHONE);
        if (CollectionUtils.isNotEmpty(phoneLst)) {
            setPhoneWithExt(phoneLst.get(0));
        }
        
    }
    
    /**
     * converts the study contact web dto to StudyContactDTO
     * @param studyContactDTO - StudyContactDTO the iso object 
     * @return StudyContactDTO - StudyContactDTO the iso object 
     * @throws PAException error if any
     */
    public StudyContactDTO convertToStudyContactDto(StudyContactDTO studyContactDTO) throws PAException {
        StudyContactDTO scDTO = studyContactDTO == null ? new StudyContactDTO() : studyContactDTO;
        
        //-ve id indicates new item
        if (this.id != null && this.id > 0) {
            scDTO.setIdentifier(IiConverter.convertToStudyContactIi(this.id));
        }
        scDTO.setPrsUserName(StConverter.convertToSt(this.prsUserName));
        scDTO.setComments(StConverter.convertToSt(this.comments));
        StudyContactRoleCode scRoleCode = StudyContactRoleCode.getByCode(this.roleCode);
        scDTO.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        scDTO.setRoleCode(CdConverter.convertToCd(scRoleCode));
        
        
        PAOrganizationalContactDTO orgContacPaDto = new PAOrganizationalContactDTO();
        //Leaving it default(prg-M, Pers-Not M) for others
        if (StudyContactRoleCode.PIO_CONTACT.equals(scRoleCode)) {
            orgContacPaDto.setOrganizationMandatory(false);
            orgContacPaDto.setPersonMandatory(true);
        } else {
            orgContacPaDto.setOrganizationMandatory(true);
            orgContacPaDto.setPersonMandatory(true);
        }
        
        orgContacPaDto.setOrganizationIdentifier(IiConverter.convertToPoOrganizationIi(
                contactOrg.getIdentifier()));
        orgContacPaDto.setPersonIdentifier(IiConverter.convertToPoPersonIi(contactPerson.getIdentifier()));
        
        //TODO: Check if this id needs to be sent. 
        //Looks like, single orgContactId is located by the POPerson and POOrganization combo
        if (orgContactId != null) {
            orgContacPaDto.setIdentifier(
                    IiConverter.convertToPoOrganizationalContactIi(orgContactId));
        }
        
        orgContacPaDto.setTypeCode(PO_ORG_CNTCT_TYPE_SITE);
        Long ocId = paBaseCorrltn.create(orgContacPaDto);
        
        scDTO.setOrganizationalContactIi(IiConverter.convertToIi(ocId.toString()));
        scDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(""));
        
        scDTO.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(studyProtocolId));
        List<String> phones = new ArrayList<String>();
        String phExtStr = getPhoneWithExt();
        phones.add(phExtStr);
            
        List<String> emails = new ArrayList<String>();
        emails.add(this.email);
        DSet<Tel> dsetList = null;
        dsetList =  DSetConverter.convertListToDSet(phones, DSetConverter.TYPE_PHONE, dsetList);
        dsetList =  DSetConverter.convertListToDSet(emails, DSetConverter.TYPE_EMAIL, dsetList);
        scDTO.setTelecomAddresses(dsetList);
        
        return scDTO;
    }
    
    
    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder()
        .append(studyProtocolId)
        .append(selPoOrgId)
        .append(selPoPrsnId)
        .append(email);
        return hcb.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof StudyContactWebDTO)) {
            return false;
        }
        StudyContactWebDTO scWebDto = (StudyContactWebDTO) obj;
        EqualsBuilder eb = new EqualsBuilder()
        .append(studyProtocolId, scWebDto.getStudyProtocolId())
        .append(selPoOrgId, scWebDto.getSelPoOrgId())
        .append(selPoPrsnId, scWebDto.getSelPoPrsnId())
        .append(email, scWebDto.getEmail());
       
        return eb.isEquals();
    }
    

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the orgContactId
     */
    public String getOrgContactId() {
        return orgContactId;
    }

    /**
     * @param orgContactId the orgContactId to set
     */
    public void setOrgContactId(String orgContactId) {
        this.orgContactId = orgContactId;
    }

    /**
     * @return the prsUserName
     */
    public String getPrsUserName() {
        return prsUserName;
    }

    /**
     * @param prsUserName the prsUserName to set
     */
    public void setPrsUserName(String prsUserName) {
        this.prsUserName = prsUserName;
    }

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

    /**
     * @return the ext
     */
    public String getExt() {
        return ext;
    }

    /**
     * @param ext the ext to set
     */
    public void setExt(String ext) {
        this.ext = ext;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    /**
     * @return the selPoOrgId
     */
    public String getSelPoOrgId() {
        return selPoOrgId;
    }

    /**
     * @param selPoOrgId the selPoOrgId to set
     */
    public void setSelPoOrgId(String selPoOrgId) {
        this.selPoOrgId = selPoOrgId;
    }

    /**
     * @return the selPoPrsnId
     */
    public String getSelPoPrsnId() {
        return selPoPrsnId;
    }

    /**
     * @param selPoPrsnId the selPoPrsnId to set
     */
    public void setSelPoPrsnId(String selPoPrsnId) {
        this.selPoPrsnId = selPoPrsnId;
    }

    /**
     * @return the editedPoOrgId
     */
    public String getEditedPoOrgId() {
        return editedPoOrgId;
    }

    /**
     * @param editedPoOrgId the editedPoOrgId to set
     */
    public void setEditedPoOrgId(String editedPoOrgId) {
        this.editedPoOrgId = editedPoOrgId;
    }

    /**
     * @return the editedPoPrsnId
     */
    public String getEditedPoPrsnId() {
        return editedPoPrsnId;
    }

    /**
     * @param editedPoPrsnId the editedPoPrsnId to set
     */
    public void setEditedPoPrsnId(String editedPoPrsnId) {
        this.editedPoPrsnId = editedPoPrsnId;
    }

    /**
     * @return the editedOrgNm
     */
    public String getEditedOrgNm() {
        return editedOrgNm;
    }

    /**
     * @param editedOrgNm the editedOrgNm to set
     */
    public void setEditedOrgNm(String editedOrgNm) {
        this.editedOrgNm = editedOrgNm;
    }

    /**
     * @return the editedPrsnNm
     */
    public String getEditedPrsnNm() {
        return editedPrsnNm;
    }

    /**
     * @param editedPrsnNm the editedPrsnNm to set
     */
    public void setEditedPrsnNm(String editedPrsnNm) {
        this.editedPrsnNm = editedPrsnNm;
    }

    /**
     * @return the contactOrg
     */
    public Organization getContactOrg() {
        return contactOrg;
    }

    /**
     * @param contactOrg the contactOrg to set
     */
    public void setContactOrg(Organization contactOrg) {
        this.contactOrg = contactOrg;
    }

    /**
     * @return the contactPerson
     */
    public Person getContactPerson() {
        return contactPerson;
    }

    /**
     * @param contactPerson the contactPerson to set
     */
    public void setContactPerson(Person contactPerson) {
        this.contactPerson = contactPerson;
    }

    /**
     * @return the roleCode
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * @param roleCode the roleCode to set
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    /**
     * @return the studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * @param studyProtocolId the studyProtocolId to set
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }
    

    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the updated
     */
    public boolean isUpdated() {
        return updated;
    }

    /**
     * @param updated the updated to set
     */
    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    /**
     * @return the deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * @param deleted the deleted to set
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    /**
     * @return the phoneWithExt
     */
    public String getPhoneWithExt() {
        return StringUtils.isNotEmpty(this.ext) && StringUtils.isNotEmpty(this.phone) 
                 ? this.phone + " X" + this.ext : this.phone;
    }

    /**
     * @param phoneWithExt the phoneWithExt to set
     */
    public void setPhoneWithExt(String phoneWithExt) {
       if (StringUtils.isNotEmpty(phoneWithExt)) {
           String[] phoneArr = phoneWithExt.split("X");
           this.phone = phoneArr[0].trim();
           if (phoneArr.length > 1) {
               this.ext = phoneArr[1].trim();
           }
       }
    }

    /**
     * @param paServiceUtils the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

    /**
     * @param paBaseCorrltn the paBaseCorrltn to set
     */
    public void setPaBaseCorrltn(
            PABaseCorrelation<PAOrganizationalContactDTO,
            OrganizationalContactDTO, OrganizationalContact,
            OrganizationalContactConverter> paBaseCorrltn) {
        this.paBaseCorrltn = paBaseCorrltn;
    }

    /**
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country country
     */
    public void setCountry(String country) {
        this.country = country;
    }
    

}




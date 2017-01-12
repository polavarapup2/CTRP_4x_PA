/**
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
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fiveamsolutions.nci.commons.audit.Auditable;
/**
 * @author Bala Nair
 *
 */
@Entity
@Table(name = "REGISTRY_USER")
@SuppressWarnings("PMD.TooManyFields")
public class RegistryUser extends AbstractEntity implements Auditable {
    private static final long serialVersionUID = -6519568778371398209L;
    private String firstName;
    private String lastName;
    private String middleName;
    private String addressLine;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String phone;
    private String affiliateOrg;
    private User csmUser;
    private String prsOrgName;
    private Long poOrganizationId;
    private Long poPersonId;
    private String emailAddress;
    private Set<StudyProtocol> studyProtocols = new HashSet<StudyProtocol>();
    private Set<StudySite> studySites = new HashSet<StudySite>();
    private Long affiliatedOrganizationId;
    private UserOrgType affiliatedOrgUserType;
    private Boolean enableEmails;
    private Boolean siteAccrualSubmitter;
    private Boolean familyAccrualSubmitter;
    private Boolean enableReports;
    private String reportGroups;
    private String token;

    /**
     * @return the csmUser
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CSM_USER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public User getCsmUser() {
        return csmUser;
    }

    /**
     * @param csmUser the csmUser to set
     */
    public void setCsmUser(User csmUser) {
        this.csmUser = csmUser;
    }

    /**
     * @return the firstName
     */
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the middleName
     */
    @Column(name = "MIDDLE_NAME")
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return the addressLine
     */
    @Column(name = "ADDRESS_LINE")
    public String getAddressLine() {
        return addressLine;
    }

    /**
     * @param addressLine the addressLine to set
     */
    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    /**
     * @return the city
     */
    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    @Column(name = "STATE")
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the postalCode
     */
    @Column(name = "POSTAL_CODE")
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the country
     */
    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the phone
     */
    @Column(name = "PHONE")
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
     * @return the affiliateOrg
     */
    @Column(name = "AFFILIATE_ORG")
    public String getAffiliateOrg() {
        return affiliateOrg;
    }

    /**
     * @param affiliateOrg the affiliateOrg to set
     */
    public void setAffiliateOrg(String affiliateOrg) {
        this.affiliateOrg = affiliateOrg;
    }

    /**
     * @return the prsOrgName
     */
    @Column(name = "PRS_ORG_NAME")
    public String getPrsOrgName() {
        return prsOrgName;
    }

    /**
     * @param prsOrgName the prsOrgName to set
     */
    public void setPrsOrgName(String prsOrgName) {
        this.prsOrgName = prsOrgName;
    }

    /**
     * @return the poOrganizationId
     */
    @Column(name = "PO_ORGANIZATION_ID")
    public Long getPoOrganizationId() {
        return poOrganizationId;
    }

    /**
     * @param poOrganizationId the poOrganizationId to set
     */
    public void setPoOrganizationId(Long poOrganizationId) {
        this.poOrganizationId = poOrganizationId;
    }

    /**
     * @return the poPersonId
     */
    @Column(name = "PO_PERSON_ID")
    public Long getPoPersonId() {
        return poPersonId;
    }

    /**
     * @param poPersonId the poPersonId to set
     */
    public void setPoPersonId(Long poPersonId) {
        this.poPersonId = poPersonId;
    }

    /**
     * @return the emailAddress
     */
    @Column(name = "EMAIL_ADDRESS")
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the studyProtocols
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "STUDY_OWNER", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(
            name = "STUDY_ID"))
    public Set<StudyProtocol> getStudyProtocols() {
        return studyProtocols;
    }

    /**
     * @param studyProtocols the studyProtocols to set
     */
    public void setStudyProtocols(Set<StudyProtocol> studyProtocols) {
        this.studyProtocols = studyProtocols;
    }
    
    /**
     * @return the studySites
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "STUDY_SITE_OWNER", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(
            name = "STUDY_SITE_ID"))
    public Set<StudySite> getStudySites() {
        return studySites;
    }

    /**
     * @param studySites the studySites to set
     */
    public void setStudySites(Set<StudySite> studySites) {
        this.studySites = studySites;
    }
    

    /**
     * @return the affiliatedOrganizationId
     */
    @Column(name = "AFFILIATED_ORG_ID")
    public Long getAffiliatedOrganizationId() {
        return affiliatedOrganizationId;
    }

    /**
     * @param affiliatedOrganizationId the affiliatedOrganizationId to set
     */
    public void setAffiliatedOrganizationId(Long affiliatedOrganizationId) {
        this.affiliatedOrganizationId = affiliatedOrganizationId;
    }

    /**
     * @return the affiliatedOrgUserType
     */
    @Column(name = "AFFILIATED_ORG_USER_TYPE")
    @Enumerated(EnumType.STRING)
    public UserOrgType getAffiliatedOrgUserType() {
        return affiliatedOrgUserType;
    }

    /**
     * @param affiliatedOrgUserType the affiliatedOrgUserType to set
     */
    public void setAffiliatedOrgUserType(UserOrgType affiliatedOrgUserType) {
        this.affiliatedOrgUserType = affiliatedOrgUserType;
    }
    
    /**
     * Returns first name plus space plus last name. Handles nulls.
     * @return String
     */
    @Transient
    public String getFullName() {
        return (StringUtils.defaultString(getFirstName()) + " " + StringUtils
                .defaultString(getLastName())).trim();
    }
    
    /**
     * @return LastFirstName
     */
    @Transient
    public String getLastFirstName() {
        return (StringUtils.defaultString(getLastName()) + ", " + StringUtils
                .defaultString(getFirstName())).trim().replaceFirst(
                "(^\\s*,\\s+)|(,\\s+$)", "");
    }

    /**
     * @return the enableEmails
     */
    @Column(name = "ENABLE_EMAILS")
    public Boolean getEnableEmails() {
        return enableEmails;
    }

    /**
     * @param enableEmails the enableEmails to set
     */
    public void setEnableEmails(Boolean enableEmails) {
        this.enableEmails = enableEmails;
    }

    /**
     * @return the siteAccrualSubmitter
     */
    @Column(name = "SITE_ACCRUAL_SUBMITTER")
    public Boolean getSiteAccrualSubmitter() {
        return siteAccrualSubmitter;
    }

    /**
     * @param siteAccrualSubmitter the siteAccrualSubmitter to set
     */
    public void setSiteAccrualSubmitter(Boolean siteAccrualSubmitter) {
        this.siteAccrualSubmitter = siteAccrualSubmitter;
    }

    /**
     * @return the familyAccrualSubmitter
     */
    @Column(name = "FAMILY_ACCRUAL_SUBMITTER")
    public Boolean getFamilyAccrualSubmitter() {
        return familyAccrualSubmitter;
    }

    /**
     * @param familyAccrualSubmitter the familyAccrualSubmitter to set
     */
    public void setFamilyAccrualSubmitter(Boolean familyAccrualSubmitter) {
        this.familyAccrualSubmitter = familyAccrualSubmitter;
    }
    
    /*
     * Created below fields for JIRA PO-7595
     */

    /**
     * @return enableReports - shows whether user have permission to view
     *         reports
     */
    @Column(name = "ENABLE_REPORTS")
    public Boolean getEnableReports() {
      return enableReports;
    }

    /**
     * @param enableReports
     *            - sets whether user have permission to view reports
     */
    public void setEnableReports(Boolean enableReports) {
      this.enableReports = enableReports;
    }

    /**
     * @return reportGroups - shows all the report groups those are allowed for
     *         the user
     */
    @Column(name = "REPORT_GROUPS")
    public String getReportGroups() {
      return reportGroups;
    }

    /**
     * @param reportGroups
     *            - sets all the allowed report groups for the user
     */
    public void setReportGroups(String reportGroups) {
      this.reportGroups = reportGroups;
    }

    /**
     * @return the token
     */
    @Column(name = "TOKEN")    
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }
}

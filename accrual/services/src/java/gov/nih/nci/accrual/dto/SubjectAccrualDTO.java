/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The accrual
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This accrual Software License (the License) is between NCI and You. You (or 
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
 * its rights in the accrual Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the accrual Software; (ii) distribute and 
 * have distributed to and by third parties the accrual Software and any 
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
package gov.nih.nci.accrual.dto;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.iso.dto.BaseDTO;

/**
 * DTO to represent the condensed study subject and patient information.
 * 
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class SubjectAccrualDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    
    private St assignedIdentifier;
    private Ts birthDate;
    private Cd gender;
    private DSet<Cd> race;
    private Cd ethnicity;
    private Cd countryCode;
    private St zipCode;
    private Ts registrationDate;
    private Cd paymentMethod;
    private Ii diseaseIdentifier;
    private Ii siteDiseaseIdentifier;
    private Ii participatingSiteIdentifier;
    private St registrationGroupId;
    private Cd submissionTypeCode;
    
    /**
     * @return the assignedIdentifier
     */
    public St getAssignedIdentifier() {
        return assignedIdentifier;
    }
    
    /**
     * @param assignedIdentifier the assignedIdentifier to set
     */
    public void setAssignedIdentifier(St assignedIdentifier) {
        this.assignedIdentifier = assignedIdentifier;
    }
    
    /**
     * @return the birthDate
     */
    public Ts getBirthDate() {
        return birthDate;
    }
    
    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Ts birthDate) {
        this.birthDate = birthDate;
    }
    
    /**
     * @return the gender
     */
    public Cd getGender() {
        return gender;
    }
    
    /**
     * @param gender the gender to set
     */
    public void setGender(Cd gender) {
        this.gender = gender;
    }
    
    /**
     * @return the race
     */
    public DSet<Cd> getRace() {
        return race;
    }
    
    /**
     * @param race the race to set
     */
    public void setRace(DSet<Cd> race) {
        this.race = race;
    }
    
    /**
     * @return the ethnicity
     */
    public Cd getEthnicity() {
        return ethnicity;
    }
    
    /**
     * @param ethnicity the ethnicity to set
     */
    public void setEthnicity(Cd ethnicity) {
        this.ethnicity = ethnicity;
    }
    
    /**
     * @return the zipCode
     */
    public St getZipCode() {
        return zipCode;
    }
    
    /**
     * @param zipCode the zipCode to set
     */
    public void setZipCode(St zipCode) {
        this.zipCode = zipCode;
    }
    
    /**
     * @return the registrationDate
     */
    public Ts getRegistrationDate() {
        return registrationDate;
    }
    
    /**
     * @param registrationDate the registrationDate to set
     */
    public void setRegistrationDate(Ts registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    /**
     * @return the paymentMethod
     */
    public Cd getPaymentMethod() {
        return paymentMethod;
    }
    
    /**
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod(Cd paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    /**
     * @return the diseaseIdentifier
     */
    public Ii getDiseaseIdentifier() {
        return diseaseIdentifier;
    }
    
    /**
     * @param diseaseIdentifier the diseaseIdentifier to set
     */
    public void setDiseaseIdentifier(Ii diseaseIdentifier) {
        this.diseaseIdentifier = diseaseIdentifier;
    }
    /**
     * @return the siteDiseaseIdentifier
     */
    public Ii getSiteDiseaseIdentifier() {
        return siteDiseaseIdentifier;
    }
    /**
     * @param siteDiseaseIdentifier the siteDiseaseIdentifier to set
     */
    public void setSiteDiseaseIdentifier(Ii siteDiseaseIdentifier) {
        this.siteDiseaseIdentifier = siteDiseaseIdentifier;
    }
    
    /**
     * @return the participatingSiteIdentifier
     */
    public Ii getParticipatingSiteIdentifier() {
        return participatingSiteIdentifier;
    }
    
    /**
     * @param participatingSiteIdentifier the participatingSiteIdentifier to set
     */
    public void setParticipatingSiteIdentifier(Ii participatingSiteIdentifier) {
        this.participatingSiteIdentifier = participatingSiteIdentifier;
    }

    /**
     * @return the countryCode
     */
    public Cd getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(Cd countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return the registrationGroupId
     */
    public St getRegistrationGroupId() {
        return registrationGroupId;
    }

    /**
     * @param registrationGroupId the registrationGroupId to set
     */
    public void setRegistrationGroupId(St registrationGroupId) {
        this.registrationGroupId = registrationGroupId;
    }

    /**
     * @return the submissionTypeCode
     */
    public Cd getSubmissionTypeCode() {
        return submissionTypeCode;
    }

    /**
     * @param submissionTypeCode the submissionTypeCode to set
     */
    public void setSubmissionTypeCode(Cd submissionTypeCode) {
        this.submissionTypeCode = submissionTypeCode;
    }
}

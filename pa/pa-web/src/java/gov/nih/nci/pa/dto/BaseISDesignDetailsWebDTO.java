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
package gov.nih.nci.pa.dto;

import java.io.Serializable;

/**
 * @author mshestopalov
 *
 */
public class BaseISDesignDetailsWebDTO implements Serializable {
    private static final long serialVersionUID = 7596932135076679411L;
    private String id;
    private String minUOM;
    private String maxUOM;
    private String criterionName;
    private String inclusionIndicator;
    private String operator;
    private String valueText;
    private String labTestNameValueText;
    private String unit;
    private String valueIntegerMin;
    private String valueIntegerMax;
    private String cdePublicIdentifier;
    private String cdeVersionNumber;
    private String cdeCategoryCode;
    
    /**
     * @return the minUOM
     */
    public String getMinUOM() {
        return minUOM;
    }
    /**
     * @param minUOM the minUOM to set
     */
    public void setMinUOM(String minUOM) {
        this.minUOM = minUOM;
    }
    /**
     * @return the maxUOM
     */
    public String getMaxUOM() {
        return maxUOM;
    }
    /**
     * @param maxUOM the maxUOM to set
     */
    public void setMaxUOM(String maxUOM) {
        this.maxUOM = maxUOM;
    }
    /**
     * @param plabTestNameValueText the labTestNameValueText to set
     */
    public void setLabTestNameValueText(String plabTestNameValueText) {
        this.labTestNameValueText = plabTestNameValueText;
    }
    /**
     * @return the labTestNameValueTest
     */
    public String getLabTestNameValueText() {
        return labTestNameValueText;
    }
    
    /**
     * @return the valueIntegerMin
     */
    public String getValueIntegerMin() {
      return valueIntegerMin;
    }
    /**
     * @param valueIntegerMin the valueIntegerMin to set
     */
     public void setValueIntegerMin(String valueIntegerMin) {
       this.valueIntegerMin = valueIntegerMin;
     }
    /**
     * @return the valueIntegerMax
     */
     public String getValueIntegerMax() {
       return valueIntegerMax;
     }
    /**
     * @param valueIntegerMax the valueIntegerMax to set
     */
     public void setValueIntegerMax(String valueIntegerMax) {
      this.valueIntegerMax = valueIntegerMax;
    }
    /**
     * @return the cdeCategoryCode
     */
     public String getCdeCategoryCode() {
       return cdeCategoryCode;
     }
    /**
     * @param cdeCategoryCode the cdeCategoryCode to set
     */
     public void setCdeCategoryCode(String cdeCategoryCode) {
       this.cdeCategoryCode = cdeCategoryCode;
     }
     
     /**
      * @return criterionName
      */
     public String getCriterionName() {
         return criterionName;
     }
     /**
      * @param criterionName criterionName
      */
     public void setCriterionName(String criterionName) {
         this.criterionName = criterionName;
     }
     /**
      * @return inclusionIndicator
      */
     public String getInclusionIndicator() {
         return inclusionIndicator;
     }
     /**
      * @param inclusionIndicator inclusionIndicator
      */
     public void setInclusionIndicator(String inclusionIndicator) {
         this.inclusionIndicator = inclusionIndicator;
     }
     /**
      * @return operator
      */
     public String getOperator() {
         return operator;
     }
     /**
      * @param operator operator
      */
     public void setOperator(String operator) {
         this.operator = operator;
     }
     /**
      * @return unit
      */
     public String getUnit() {
         return unit;
     }
     /**
      * @param unit unit
      */
     public void setUnit(String unit) {
         this.unit = unit;
     }

     /**
      * @return the valueText
      */
     public String getValueText() {
       return valueText;
     }
     /**
      * @param valueText the valueText to set
      */
      public void setValueText(String valueText) {
        this.valueText = valueText;
      }
      
      /**
       * @return the cdePublicIdentifier
       */
       public String getCdePublicIdentifier() {
          return cdePublicIdentifier;
       }
      /**
       * @param cdePublicIdentifier the cdePublicIdentifier to set
       */
       public void setCdePublicIdentifier(String cdePublicIdentifier) {
         this.cdePublicIdentifier = cdePublicIdentifier;
       }
      /**
       * @return the cdeVersionNumber
       */
       public String getCdeVersionNumber() {
         return cdeVersionNumber;
       }
      /**
       * @param cdeVersionNumber the cdeVersionNumber to set
       */
       public void setCdeVersionNumber(String cdeVersionNumber) {
         this.cdeVersionNumber = cdeVersionNumber;
      }
       
       /**
        * @return id
        */
       public String getId() {
           return id;
       }
       /**
        * @param id id
        */
       public void setId(String id) {
           this.id = id;
       }
}

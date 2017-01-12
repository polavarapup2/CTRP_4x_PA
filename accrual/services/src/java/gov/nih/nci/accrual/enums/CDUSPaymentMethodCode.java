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
package gov.nih.nci.accrual.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;
import gov.nih.nci.pa.enums.CodedEnum;
import gov.nih.nci.pa.enums.PaymentMethodCode;

import org.apache.commons.lang.StringUtils;

/**
 * CDUS Payment Code mapping.
 * 
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public enum CDUSPaymentMethodCode implements CodedEnum<String> {
    /** Private Insurance. */
    PRIVATE("1", PaymentMethodCode.PRIVATE.getCode(), PaymentMethodCode.PRIVATE),
    /** Medicare. */
    MEDICARE("2", PaymentMethodCode.MEDICARE.getCode(), PaymentMethodCode.MEDICARE),
    /** Medicare and Private Insurance. */
    MEDICARE_AND_PRIVATE("3", PaymentMethodCode.MEDICARE_AND_PRIVATE.getCode(), PaymentMethodCode.MEDICARE_AND_PRIVATE),
    /** Medicaid. */
    MEDICAID("4", PaymentMethodCode.MEDICAID.getCode(), PaymentMethodCode.MEDICAID),
    /** Medicaid and Medicare. */
    MEDICAID_AND_MEDICARE("5", PaymentMethodCode.MEDICAID_AND_MEDICARE.getCode(),
            PaymentMethodCode.MEDICAID_AND_MEDICARE),
    /** Military or Veterans Sponsored, Not Otherwise Specified (NOS). */
    MILITARY_OR_VETERANS("6", PaymentMethodCode.MILITARY_OR_VETERANS.getCode(), PaymentMethodCode.MILITARY_OR_VETERANS),
    /** Military Sponsored (including CHAMPUS or TRICARE). */
    MILITARY("6A", PaymentMethodCode.MILITARY.getCode(), PaymentMethodCode.MILITARY),
    /** Veterans Sponsored. */
    VETERANS("6B", PaymentMethodCode.VETERANS.getCode(), PaymentMethodCode.VETERANS),
    /** Military Sponsored (including CHAMPUS or TRICARE). */
    ALT_MILITARY_6A("6a", PaymentMethodCode.MILITARY.getCode(), PaymentMethodCode.MILITARY),
    /** Veterans Sponsored. */
    ALT_VETERANS("6b", PaymentMethodCode.VETERANS.getCode(), PaymentMethodCode.VETERANS),
    /** Self pay (no insurance). */
    SELF("7", PaymentMethodCode.SELF.getCode(), PaymentMethodCode.SELF),
    /** No means of payment (no insurance). */
    NO_MEANS_OF_PAYMENT("8", PaymentMethodCode.NO_MEANS_OF_PAYMENT.getCode(), PaymentMethodCode.NO_MEANS_OF_PAYMENT),
    /** Managed care. */
    MANAGED_CARE("9", PaymentMethodCode.MANAGED_CARE.getCode(), PaymentMethodCode.MANAGED_CARE),
    /** State Supplemental Health Insurance. */
    STATE_SUPPLEMENTAL("10", PaymentMethodCode.STATE_SUPPLEMENTAL.getCode(), PaymentMethodCode.STATE_SUPPLEMENTAL),
    /** Other. */
    OTHER("98", PaymentMethodCode.OTHER.getCode(), PaymentMethodCode.OTHER),
    /** Unknown. */
    UNKNOWN("99", PaymentMethodCode.UNKNOWN.getCode(), PaymentMethodCode.UNKNOWN),
    /** Alternate Text for Military or Veterans Sponsored, Not Otherwise Specified (NOS). */
    ALT_MILITARY_OR_VETERANS("Military or Veterans Sponsored, Not Otherwise Specified (NOS)", 
            PaymentMethodCode.MILITARY_OR_VETERANS.getCode(), PaymentMethodCode.MILITARY_OR_VETERANS),
    /** Alternate Text for Military Sponsored (including CHAMPUS or TRICARE). */
    ALT_MILITARY("Military Sponsored (including CHAMPUS or TRICARE)",
            PaymentMethodCode.MILITARY.getCode(), PaymentMethodCode.MILITARY),
    /** Self pay (no insurance). */
    ALT_SELF("Self pay (no insurance)", PaymentMethodCode.SELF.getCode(), PaymentMethodCode.SELF),
    /** No means of payment (no insurance). */
    ALT_NO_MEANS_OF_PAYMENT("No means of payment (no insurance)", 
            PaymentMethodCode.NO_MEANS_OF_PAYMENT.getCode(), PaymentMethodCode.NO_MEANS_OF_PAYMENT);

    private String cdusCode;
    private String crfCode;
    private PaymentMethodCode value;

    /**
     * @param cdusCode the cdus representation
     * @param crf the crf representation
     * @param value the system representation
     */
    private CDUSPaymentMethodCode(String cdusCode, String crf, PaymentMethodCode value) {
        this.cdusCode = cdusCode;
        crfCode = crf;
        this.value = value;
        register(this);
    }

    /**
     * @param code the code to lookup the code for
     * @return the cdus payment method code
     */
    public static CDUSPaymentMethodCode getByCode(String code) {
        for (CDUSPaymentMethodCode pmc : values()) {
            if (StringUtils.equals(pmc.getCdusCode(), code)) {
                return pmc;
            }
        }
        return getByClassAndCode(CDUSPaymentMethodCode.class, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCode() {
        return crfCode;
    }

    /**
     * @return the crfCode
     */
    public String getCdusCode() {
        return cdusCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    /**
     * @return the value
     */
    public PaymentMethodCode getValue() {
        return value;
    }

}

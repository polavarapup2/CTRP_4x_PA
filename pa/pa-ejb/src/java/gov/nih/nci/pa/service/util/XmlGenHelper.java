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
package gov.nih.nci.pa.service.util;

import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.AdxpAl;
import gov.nih.nci.iso21090.AdxpCnt;
import gov.nih.nci.iso21090.AdxpCty;
import gov.nih.nci.iso21090.AdxpSta;
import gov.nih.nci.iso21090.AdxpZip;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Helper class for basic level xml generation.
 * @author mshestopalov
 *
 */
public class XmlGenHelper extends BaseXmlGenHelper {
    /**
     * TEXT_BLOCK.
     */
    public static final String TEXT_BLOCK = "textblock";
    /**
     * YES.
     */
    public static final String YES = "Yes";
    /**
     * NO.
     */
    public static final String NO = "No";
    /**
     * MAX_AGE.
     */
    public static final int MAX_AGE = 999;
    /**
     * ERROR_COUNT.
     */
    public static final int ERROR_COUNT = 5;
    /**
     * FIRST_NAME.
     */
    public static final String FIRST_NAME = "first_name";
    /**
     * MIDDLE_INITIAL.
     */
    public static final String MIDDLE_INITIAL = "middle_initial";
    /**
     * LAST_NAME.
     */
    public static final String LAST_NAME = "last_name";
    /**
     * PHONE.
     */
    public static final String PHONE = "phone";
    /**
     * PHONE EXTN.
     */
    public static final String PHONE_EXT = "phone_ext";
    /**
     * EMAIL.
     */
    public static final String EMAIL = "email";
    /**
     * NA.
     */
    public static final String NA = "N/A";
    /**
     * TAB.
     */
    public static final String TAB = "     ";
    /**
     * DASH.
     */
    public static final String DASH = "- ";
    
    /**
     * Bullet Level 1.
     */
    public static final String BULLET_L1 = "  " + DASH;
    
    private static final String PO_ID = "po_id";
    private static final String CTEP_ID = "ctep_id";
    

    /**
     * Load PO Organization into xml.
     * @param orgDTO dto
     * @param lead element
     * @param doc document
     * @param ctepId ctep id
     */
    public static void loadPoOrganization(OrganizationDTO orgDTO, Element lead, Document doc, Ii ctepId) {
        Map<String, String> addressBo = AddressConverterUtil.convertToAddressBo(orgDTO.getPostalAddress());
        DSet<Tel> telecom = orgDTO.getTelecomAddress();

        appendElement(lead, createElementWithTextblock(PO_ID, StringUtils.substring(
                orgDTO.getIdentifier().getExtension(), 0, PAAttributeMaxLen.LEN_160), doc));

        if (!ISOUtil.isIiNull(ctepId))  {
            appendElement(lead, createElementWithTextblock(CTEP_ID, StringUtils.substring(ctepId.getExtension(), 0,
                PAAttributeMaxLen.LEN_160), doc));
        }

        loadPoAddress(addressBo, lead, doc);
        loadPoTelecom(telecom, lead, doc);

    }

    /**
     * Load PO Person into xml.
     * @param perDTO dto
     * @param lead element
     * @param doc document
     * @param ctepId ctep id
     */
    public static void loadPoPerson(PersonDTO perDTO, Element lead, Document doc, Ii ctepId) {
        loadPersonIds(perDTO, lead, doc, ctepId);
        Map<String, String> addressBo = AddressConverterUtil.convertToAddressBo(perDTO.getPostalAddress());
        DSet<Tel> telecom = perDTO.getTelecomAddress();

        loadPoAddress(addressBo, lead, doc);
        loadPoTelecom(telecom, lead, doc);
    }
    
    /**
     * Load PO Person ID into xml.
     * @param perDTO dto
     * @param lead element
     * @param doc document
     * @param ctepId ctep id
     */
    public static void loadPersonIds(PersonDTO perDTO, Element lead, Document doc, Ii ctepId) {
        appendElement(lead, createElementWithTextblock(PO_ID, StringUtils.substring(
                perDTO.getIdentifier().getExtension(), 0, PAAttributeMaxLen.LEN_160), doc));

        if (!ISOUtil.isIiNull(ctepId))  {
            appendElement(lead, createElementWithTextblock(CTEP_ID, StringUtils.substring(ctepId.getExtension(), 0,
                    PAAttributeMaxLen.LEN_160), doc));
        }
    }
    
    /**
     * Load PO Person ID into xml.
     * @param per dto
     * @param lead element
     * @param doc document
     * @param ctepId ctep id
     */
    public static void loadPersonIds(Person per, Element lead, Document doc, Ii ctepId) {
        appendElement(lead, createElementWithTextblock(PO_ID, per.getIdentifier(), doc));

        if (!ISOUtil.isIiNull(ctepId))  {
            appendElement(lead, createElementWithTextblock(CTEP_ID, StringUtils.substring(ctepId.getExtension(), 0,
                    PAAttributeMaxLen.LEN_160), doc));
        }
    }    
    

    /**
     * Load PO Person into xml.
     * @param perDTO dto
     * @param lead element
     * @param doc document
     * @param ctepId ctep id
     */
    public static void loadPoPersonNoAddress(PersonDTO perDTO, Element lead, Document doc, Ii ctepId) {
        loadPersonIds(perDTO, lead, doc, ctepId);
        DSet<Tel> telecom = perDTO.getTelecomAddress();
        loadPoTelecom(telecom, lead, doc);
    }

    /**
     * Load PO Organizational Contact into xml.
     * @param ocDTO dto
     * @param lead element
     * @param doc document
     * @param ctepId ctep id
     */
    @SuppressWarnings("unchecked")
    public static void loadPoOrgContact(OrganizationalContactDTO ocDTO, Element lead, Document doc, Ii ctepId) {
        DSet<Tel> telecom = ocDTO.getTelecomAddress();
        appendElement(lead, createElementWithTextblock(PO_ID,
                StringUtils.substring(DSetConverter.convertToIi(ocDTO.getIdentifier()).getExtension(), 0,
                PAAttributeMaxLen.LEN_160), doc));
        if (!ISOUtil.isIiNull(ctepId))  {
            appendElement(lead, createElementWithTextblock(CTEP_ID, StringUtils.substring(ctepId.getExtension(), 0,
                PAAttributeMaxLen.LEN_160), doc));
        }
        if (ISOUtil.isDSetNotEmpty(ocDTO.getPostalAddress())) {
            Map<String, String> addressBo = null;
            addressBo =
                AddressConverterUtil.convertToAddressBo((Ad) ocDTO.getPostalAddress().getItem().iterator().next());
            loadPoAddress(addressBo, lead, doc);
        }
        loadPoTelecom(telecom, lead, doc);
    }

    /**
     * Load a PO address and telecom info into xml.
     * @param telecom set of telecom
     * @param lead element
     * @param doc doc
     */
    public static void loadPoTelecom(DSet<Tel> telecom, Element lead, Document doc) {
        if (PAUtil.isDSetTelAndEmailNull(telecom)) {
            return;
        }
        List<String> phones = DSetConverter.getTelByType(telecom, "tel:");
        for (String phone : phones) {
            appendElement(lead, createElementWithTextblock("phone", phone, doc));
        }
        List<String> faxes = DSetConverter.getTelByType(telecom, "x-text-fax:");
        for (String fax : faxes) {
            appendElement(lead, createElementWithTextblock("fax", fax, doc));
        }
        List<String> ttyes = DSetConverter.getTelByType(telecom, "x-text-tel:");
        for (String tty : ttyes) {
            appendElement(lead, createElementWithTextblock("tty", tty, doc));
        }
        List<String> emails = DSetConverter.getTelByType(telecom, "mailto:");
        for (String email : emails) {
            appendElement(lead, createElementWithTextblock("email", email, doc));
        }
    }

    /**
     * Load a PO address and telecom info into xml.
     * @param addressBo map of address info
     * @param lead element
     * @param doc doc
     */
    public static void loadPoAddress(
            Map<String, String> addressBo, Element lead, Document doc) {
        Element address = doc.createElement("address");
        appendElement(address, createElementWithTextblock("street", addressBo.get(AdxpAl.class.getName()), doc));
        appendElement(address, createElementWithTextblock("city", addressBo.get(AdxpCty.class.getName()), doc));
        appendElement(address, createElementWithTextblock("state", addressBo.get(AdxpSta.class.getName()), doc));
        appendElement(address, createElementWithTextblock("zip", addressBo.get(AdxpZip.class.getName()), doc));
        appendElement(address, createElementWithTextblock("country",
                PADomainUtils.getCountryNameUsingAlpha3Code(addressBo.get(AdxpCnt.class.getName())), doc));
        appendElement(lead, address);
    }

    static boolean containsXmlChars(String data) {
        boolean retVal = false;
        if (StringUtils.isNotEmpty(data)) {
            retVal = data.contains("<") || data.contains(">") || data.contains("&");
        }
        return retVal;
    }

    /**
     * getAgeUnit.
     * @param b number
     * @param unit unit
     * @return string
     */
    public static String getAgeUnit(BigDecimal b, String unit) {
        if (b.intValue() == 0 || b.intValue() == XmlGenHelper.MAX_AGE) {
            return "N/A";
        } else if (unit == null) {
            return null;
        }
        return PAUtil.getAge(b) + " " + unit.toLowerCase(Locale.US);
    }
}

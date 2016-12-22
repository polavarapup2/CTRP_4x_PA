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

import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.services.person.PersonDTO;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;

/**
 * @author ludetc
 * 
 */
public abstract class AbstractPDQXmlParser {

    private static final Pattern USA_PATTERN = Pattern.compile("^\\s*U\\.?S\\.?A\\.?\\s*$", Pattern.CASE_INSENSITIVE);

    private URL url;
    private Document document;
    private PAServiceUtils paServiceUtils = new PAServiceUtils();

    /**
     * set the url of the source to parse.
     * @param url source URL
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * @return the url to parse
     */
    public URL getUrl() {
        return this.url;
    }

    /**
     * parse the xml.
     * @throws PAException Thrown if parsing failed.
     */
    protected void parse() throws PAException {
        if (url == null) {
            throw new IllegalStateException("URL is not set, call setUrl first.");
        }
        try {
            setDocument(new SAXBuilder().build(url));
        } catch (JDOMException e) {
            throw new IllegalArgumentException("The file cannot be parsed. ", e);
        } catch (IOException e) {
            throw new IllegalArgumentException("The URL to parse does not point to a valid file", e);
        }

    }

    /**
     * 
     * @param parent parent
     * @param name name
     * @return string
     */
    protected String getText(Element parent, String name) {
        if (parent == null) {
            return null;
        }
        return StringUtils.isEmpty(parent.getChildText(name)) ? null : parent.getChildText(name).trim();
    }

    /**
     * 
     * @param format format
     * @param date date
     * @return TS
     */
    protected Ts tsFromString(String format, String date) {
        Ts ts = new Ts();
        try {
            ts.setValue(new SimpleDateFormat(format, Locale.getDefault()).parse(date));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Value " + date + " is not a valid date.", e);
        }
        return ts;
    }

    /**
     * 
     * @param element element
     * @param appendNewLine string to append.
     * @param appendSpace String to append.
     * @return String
     */
    public String getFullText(Element element, String appendNewLine, String appendSpace) {
        if (element == null) {
            return null;
        }
        StringBuffer result = new StringBuffer();
        for (Object o : element.getContent()) {
            if (o instanceof Text) {
                Text t = (Text) o;
                result.append(t.getTextTrim()).append(appendNewLine);
            } else if (o instanceof Element) {
                Element child = (Element) o;
                result.append(appendSpace).append(getFullText(child, appendNewLine, appendSpace));
            }
        }
        return result.toString();
    }

    /**
     * @param document the document to set
     */
    public void setDocument(Document document) {
        this.document = document;
    }

    /**
     * @return the document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * @param countryName name of country
     * @return alpha3 code
     * @throws PAException If any error occurs or the country is not found
     */
    protected String getAlpha3CountryName(String countryName) throws PAException {
        if (USA_PATTERN.matcher(countryName).matches()) {
            return "USA";
        }
        try {
            Country country = PaRegistry.getLookUpTableService().getCountryByName(countryName);
            if (country != null) {
                return country.getAlpha3();
            }
        } catch (Exception e) {
            throw new PAException("Error getting country: " + countryName, e);
        }
        throw new PAException("Error getting country: " + countryName);
    }

    /**
     * Converts to Ivl<Pq>.
     * @param minUom min UOM
     * @param minValue value
     * @param maxUom UOM
     * @param maxValue value
     * @return ivl<pq>
     */
    protected Ivl<Pq> convertToIvlPq(String minUom, String minValue, String maxUom, String maxValue) {
        if (minUom == null && minValue == null && maxValue == null && maxUom == null) {
            return null;
        }
        IvlConverter.JavaPq low = new IvlConverter.JavaPq(minUom, PAUtil.convertStringToDecimal(minValue), null);
        IvlConverter.JavaPq high = new IvlConverter.JavaPq(maxUom, PAUtil.convertStringToDecimal(maxValue), null);
        return IvlConverter.convertPq().convertToIvl(low, high);
    }

    /**
     * this method parse the PI information.
     * @param overallOfficialElement element
     * @return personDTO
     */
    protected PersonDTO readPrincipalInvestigatorInfo(Element overallOfficialElement) {
        String ctepId = overallOfficialElement.getAttributeValue("ctep-id");
        PersonDTO per = null;
        if (StringUtils.isNotEmpty(ctepId)) {
            per = getPaServiceUtils().getPersonByCtepId(ctepId);
        }
        if (per == null) {
            per = new PersonDTO();
        }
        per.setName(EnPnConverter.convertToEnPn(getText(overallOfficialElement, "first_name"),
                                                getText(overallOfficialElement, "middle_initial"),
                                                getText(overallOfficialElement, "last_name"), null, null));
        return per;
    }

    /**
     * @param paServiceUtils the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

    /**
     * @return the paServiceUtils
     */
    public PAServiceUtils getPaServiceUtils() {
        return paServiceUtils;
    }

}

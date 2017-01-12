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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author mshestopalov
 *
 */
public class PdqXmlGenHelperTest extends AbstractMockitoTest {

    private Document doc = null;
    private EligibilityComponentHelper eligHelper = null;

    @Before
    public void setup() throws ParserConfigurationException, PAException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        doc = docBuilder.newDocument();
        eligHelper = new EligibilityComponentHelper();
    }

    private Pq getPq10Years() {
        Pq pq = new Pq();
        pq.setUnit("Years");
        pq.setValue(new BigDecimal(10));
        return pq;
    }

    private Pq getPq20Years() {
        Pq pq = new Pq();
        pq.setUnit("Years");
        pq.setValue(new BigDecimal(20));
        return pq;
    }

    private Pq getPq10Units() {
        Pq pq = new Pq();
        pq.setUnit("10");
        return pq;
    }

    private Pq getPq20Units() {
        Pq pq = new Pq();
        pq.setUnit("20");
        return pq;
    }

    @Test
    public void testGetMaxAgeOnlyHighSet() {
        Ivl<Pq> input = new Ivl<Pq>();
        input.setHigh(getPq10Years());
        assertEquals(new BigDecimal(10), PdqXmlGenHelper.getMaxAge(input));
        input.setHigh(null);
        assertEquals(BigDecimal.ZERO, PdqXmlGenHelper.getMaxAge(input));
    }

    @Test
    public void testGetMaxAgeOnlyLowSet() {
        Ivl<Pq> input = new Ivl<Pq>();
        input.setLow(getPq10Years());
        assertEquals(BigDecimal.ZERO, PdqXmlGenHelper.getMaxAge(input));
    }

    @Test
    public void testGetMaxAgeHighLowSet() {
        Ivl<Pq> input = new Ivl<Pq>();
        input.setHigh(getPq20Years());
        input.setLow(getPq10Years());
        assertEquals(new BigDecimal(20), PdqXmlGenHelper.getMaxAge(input));
        assertEquals(new BigDecimal(10), PdqXmlGenHelper.getMinAge(input));
    }

    @Test
    public void testGetMinAgeOnlyLowSet() {
        Ivl<Pq> input = new Ivl<Pq>();
        input.setLow(getPq10Years());
        assertEquals(new BigDecimal(10), PdqXmlGenHelper.getMinAge(input));
        input.setLow(null);
        assertEquals(BigDecimal.ZERO, PdqXmlGenHelper.getMinAge(input));
    }

    @Test
    public void testGetMinUnitOnlyLowSet() {
        Ivl<Pq> input = new Ivl<Pq>();
        input.setLow(getPq10Units());
        assertEquals("10", PdqXmlGenHelper.getMinUnit(input));
        input.setLow(null);
        assertEquals("", PdqXmlGenHelper.getMinUnit(input));
    }

    @Test
    public void testGetMaxUnitOnlyHighSet() {
        Ivl<Pq> input = new Ivl<Pq>();
        input.setHigh(getPq10Units());
        assertEquals("10", PdqXmlGenHelper.getMaxUnit(input));
        input.setHigh(null);
        assertEquals("", PdqXmlGenHelper.getMaxUnit(input));
    }

    @Test
    public void testGetMinUnitHighLowSet() {
        Ivl<Pq> input = new Ivl<Pq>();
        input.setHigh(getPq20Units());
        input.setLow(getPq10Units());
        assertEquals("20", PdqXmlGenHelper.getMaxUnit(input));
        assertEquals("10", PdqXmlGenHelper.getMinUnit(input));
    }

    private String getXml(Document doc) throws TransformerException {
        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(domSource, result);
        return writer.toString();

    }

    @Test
    public void testHandleDiseaseCollection() throws ParserConfigurationException, TransformerException {
        Element root = doc.createElement("diseases");
        doc.appendChild(root);
        List<PDQDiseaseDTO> diseases = new ArrayList<PDQDiseaseDTO>();
        PdqXmlGenHelper.handleDiseaseCollection(diseases, doc, root);
        assertTrue(getXml(doc).contains("<diseases/>"));
        PDQDiseaseDTO disease = new PDQDiseaseDTO();
        disease.setDiseaseCode(StConverter.convertToSt("diseaseCode"));
        disease.setDisplayName(StConverter.convertToSt("displayName"));
        disease.setNtTermIdentifier(StConverter.convertToSt("ntTermIdentifier"));
        disease.setPreferredName(StConverter.convertToSt("preferredName"));
        diseases.add(disease);
        PdqXmlGenHelper.handleDiseaseCollection(diseases, doc, root);
        String docStr = getXml(doc);
        assertTrue(docStr.contains("<diseases>" + NEWLINE));
        assertTrue(docStr.contains("<disease_conditions>" + NEWLINE));
        assertTrue(docStr.contains("<condition_info>" + NEWLINE));
        assertTrue(docStr.contains("<preferred_name>preferredName</preferred_name>" + NEWLINE));
        assertTrue(docStr.contains("<disease_code>diseaseCode</disease_code>" + NEWLINE));
        assertTrue(docStr.contains("<nci_thesaurus_id>ntTermIdentifier</nci_thesaurus_id>" + NEWLINE));
        assertTrue(docStr.contains("<menu_display_name>displayName</menu_display_name>" + NEWLINE));
        assertTrue(docStr.contains("</condition_info>" + NEWLINE));
        assertTrue(docStr.contains("</disease_conditions>" + NEWLINE));
        assertTrue(docStr.contains("</diseases>"));
    }

    @Test
    public void testGetPoHCFDTOByPaHcfIi() throws PAException {
        HealthCareFacilityDTO hcfDto = PdqXmlGenHelper.getPoHCFDTOByPaHcfIi(
                IiConverter.convertToPoHealthCareFacilityIi("1"), getCorUtils());
        Iterator<Ii> ids = hcfDto.getIdentifier().getItem().iterator();
        assertEquals("1", ids.next().getExtension());
        assertEquals("ctep org id", ids.next().getExtension());
        assertEquals("1", hcfDto.getPlayerIdentifier().getExtension());
    }

    @Test
    public void testAddPoOrganizationByPaHcfIi() throws PAException, TransformerException {
        Element root = doc.createElement("test");
        doc.appendChild(root);
        PdqXmlGenHelper.addPoOrganizationByPaHcfIi(root, "organization",
                IiConverter.convertToPoHealthCareFacilityIi("1"), doc, getCorUtils());
        String docStr = getXml(doc);
        String[] ss = new String[]{"<test>" + NEWLINE, "<organization>" + NEWLINE, 
                "<name>some org name</name>" + NEWLINE,"<po_id>1</po_id>" + NEWLINE,
                "<ctep_id>ctep org id</ctep_id>" + NEWLINE, "<address>" + NEWLINE,
                "<street>street</street>" + NEWLINE, "<city>city</city>" + NEWLINE,
                "<state>MD</state>" + NEWLINE, "<zip>20000</zip>" + NEWLINE,
                "<country>United States</country>" + NEWLINE, "</address>" + NEWLINE,
                "<phone>111-222-3333ext444</phone>" + NEWLINE, "<email>X</email>" + NEWLINE,
                "</organization>" + NEWLINE, "</test>"};
        for (String s : ss) {
            StringBuffer assertMsg = new StringBuffer("\n=====================================================\n");
            assertMsg.append("NEWLINE:\n");
            if (NEWLINE == null) {
                assertMsg.append("NULL\n");
            } else  {
                assertMsg.append("length() == " + NEWLINE.length() + "\n");
                for (int x = 0; x < NEWLINE.length(); x++) {
                    int ascii = NEWLINE.charAt(x);
                    assertMsg.append(ascii);
                    assertMsg.append("\n");
                }
            }
            assertMsg.append("=====================================================\n");
            assertMsg.append("Looking for\n[" + s + "]\n");
            assertMsg.append("Found\n[" + docStr + "]\n");
            assertTrue(assertMsg.toString(), docStr.contains(s));
        }
    }

    @Test
    public void testIncCriteriaWithCriterionPqValuesAndOp() throws TransformerException, PAException {
        Element eligibility = doc.createElement("test");
        doc.appendChild(eligibility);
        PlannedEligibilityCriterionDTO eligibilityCriterion = new PlannedEligibilityCriterionDTO();
        eligibilityCriterion.setCategoryCode(CdConverter.convertStringToCd("categoryCode"));
        eligibilityCriterion.setInclusionIndicator(BlConverter.convertToBl(true));
        Ivl<Pq> input = new Ivl<Pq>();
        Pq pqLow = new Pq();
        pqLow.setUnit("warp power");
        pqLow.setValue(new BigDecimal(10));
        Pq pqHigh = new Pq();
        pqHigh.setUnit("warp power");
        pqHigh.setValue(new BigDecimal(20));
        input.setHigh(pqHigh);
        input.setLow(pqLow);
        eligibilityCriterion.setValue(input);
        eligibilityCriterion.setOperator((StConverter.convertToSt("=")));
        eligibilityCriterion.setCriterionName(StConverter.convertToSt("highest speed"));
        doc.removeChild(eligibility);
        eligibility = doc.createElement("test");
        doc.appendChild(eligibility);
        PdqXmlGenHelper.handleEligCritTraversal(eligibilityCriterion, eligHelper, eligibility, doc);
        String docStr = getXml(doc);
        assertTrue(docStr.contains("<test>" + NEWLINE));
        assertTrue(docStr.contains("<criterion>" + NEWLINE));
        assertTrue(docStr.contains("<type>Inclusion Criteria</type>" + NEWLINE));
        assertTrue(docStr.contains("<data>- highest speed 10 = warp power" + NEWLINE + "</data>" + NEWLINE));
        assertTrue(docStr.contains("</criterion>" + NEWLINE));
        assertTrue(docStr.contains("</test>"));
    }

    @Test
    public void testIncCriteriaWithTxtDescription() throws PAException, TransformerException {
        Element eligibility = doc.createElement("test");
        doc.appendChild(eligibility);
        PlannedEligibilityCriterionDTO eligibilityCriterion = new PlannedEligibilityCriterionDTO();
        eligibilityCriterion.setCategoryCode(CdConverter.convertStringToCd("categoryCode"));
        eligibilityCriterion.setInclusionIndicator(BlConverter.convertToBl(true));
        eligibilityCriterion.setTextDescription(StConverter.convertToSt("text"));

        PdqXmlGenHelper.handleEligCritTraversal(eligibilityCriterion, eligHelper, eligibility, doc);
        String docStr = getXml(doc);
        assertTrue(docStr.contains("<test>" + NEWLINE));
        assertTrue(docStr.contains("<criterion>" + NEWLINE));
        assertTrue(docStr.contains("<type>Inclusion Criteria</type>" + NEWLINE));
        assertTrue(docStr.contains("<data>     - text" + NEWLINE + "</data>" + NEWLINE));
        assertTrue(docStr.contains("</criterion>" + NEWLINE));
        assertTrue(docStr.contains("</test>"));
    }

}

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
package gov.nih.nci.pa.pdq.sql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Script generator for database cleanup for PO-3660.
 * Replaces the recruitment status based on the trial status.
 * @author Michael Visee
 */
public class PDQReplaceRecruitmentStatus {
    private static final String QUERY = "UPDATE STUDY_SITE_ACCRUAL_STATUS SET STATUS_CODE = ':1'" +
    		" WHERE STUDY_SITE_IDENTIFIER IN (SELECT SS.IDENTIFIER FROM STUDY_SITE SS " +
    		" WHERE SS.FUNCTIONAL_CODE = 'TREATING_SITE' and SS.STUDY_PROTOCOL_IDENTIFIER IN " +
    		" (SELECT SP.IDENTIFIER FROM STUDY_PROTOCOL SP, STUDY_OVERALL_STATUS SOS, STUDY_SITE SS," +
    		" RESEARCH_ORGANIZATION RO, ORGANIZATION ORG WHERE SOS.STATUS_CODE = ':2' AND " +
    		" SOS.STUDY_PROTOCOL_IDENTIFIER = SS.STUDY_PROTOCOL_IDENTIFIER " +
    		" AND SS.LOCAL_SP_INDENTIFIER = ':3' AND SS.RESEARCH_ORGANIZATION_IDENTIFIER = RO.IDENTIFIER " +
    		" AND SS.STUDY_PROTOCOL_IDENTIFIER = SP.IDENTIFIER " +
    		" AND RO.ORGANIZATION_IDENTIFIER = ORG.IDENTIFIER AND ORG.NAME = 'ClinicalTrials.gov'));\n";

    private static final Map<String, String> UPDATES = loadUpdatesMap();

    private static Map<String, String> loadUpdatesMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("ENROLLING_BY_INVITATION", "ENROLLING_BY_INVITATION");
        map.put("CLOSED_TO_ACCRUAL", "ACTIVE_NOT_RECRUITING");
        map.put("CLOSED_TO_ACCRUAL_AND_INTERVENTION", "ACTIVE_NOT_RECRUITING");
        map.put("ADMINISTRATIVELY_COMPLETE", "TERMINATED_RECRUITING");
        map.put("COMPLETE", "COMPLETED");
        return map;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: PDQReplaceRecruitmentStatus <Dummy NCT number file path> <SQL output file> <xml input folder>");
            System.exit(0);
        }
        try {
            List<String> numbers = loadNCTNumbers(new File(args[2]));
            if (!numbers.isEmpty()) {
                writeSqlFile(args[1], numbers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> loadNCTNumbers(File path) throws IOException, JDOMException {
        List<String> numbers = new ArrayList<String>();
        String[] xmlFiles = path.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        });
        for (String xmlFile : xmlFiles) {
            String nctNumber = getNCTNumber(new File(path, xmlFile));
            if (nctNumber != null) {
                numbers.add(nctNumber);
            }
        }
        return numbers;
    }

    @SuppressWarnings("unchecked")
    private static String getNCTNumber(File xmlFile) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(xmlFile);
        Element clinicalStudy = document.getRootElement();
        String nctNumber = clinicalStudy.getAttributeValue("nct-id");
        if (StringUtils.isEmpty(nctNumber)) {
            return null;
        }
        List<Element> locations = clinicalStudy.getChildren("location");
        for (Element location : locations) {
            Element facilityElmt = location.getChild("facility");
            String ctepId = facilityElmt.getAttributeValue("ctep-id");
            if (StringUtils.isNotEmpty(ctepId)) {
                return null;
            }
        }
        return nctNumber;
    }

    private static void writeSqlFile(String fileName, List<String> numbers) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write("\n-- Cleanup script for PO-3660\n\n");
        for (String number : numbers) {
            for (Map.Entry<String, String> entry : UPDATES.entrySet()) {
                String query = QUERY.replace(":1", entry.getValue()).replace(":2", entry.getKey())
                    .replace(":3", number);
                writer.write(query);
            }
        }
        writer.close();
    }
}

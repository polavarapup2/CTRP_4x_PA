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
package gov.nih.nci.accrual.service.batch;

import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

/**
 * Utility methods for converting batch uploads into data objects.
 * 
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class BatchUploadUtils {
    private static final Logger LOG = Logger.getLogger(BatchUploadUtils.class);
    private static final String FULL_DATE_FORMAT = "yyyyMMdd";
    /**
     * Index of a patients race code from the PATIENT_RACE line.
     */
    private static final int RACE_INDEX = 3;
    /**
     * The index of the identifier of a line (i.e it's type: PATIENT, ACCRUAL_COUNT, COLLECTION, etc.).
     */
    private static final int LINE_IDENTIFIER_INDEX = 0;
    /**
     * The study site from an ACCRUAL_COUNT line.
     */
    private static final int ACCRUAL_COUNT_STUDY_SITE_INDEX = 2;
    /**
     * The accrual counts from an ACCRUAL_COUNT line.
     */
    private static final int ACCRUAL_COUNT_INDEX = 3;
    /**
     * The unique identifier of a patient on a PATIENT_RACE line.
     */
    private static final int PATIENT_ID_INDEX = 2;

    /**
     * Returns a date from the given date string (yyyyMMdd).
     * @param date the date to parse
     * @return the parsed date or null if the date is unparseable
     */
    public static Date getDate(String date) {
        return formatDate(date, FULL_DATE_FORMAT);
    }
    
    private static Date formatDate(String input, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        Date date = null;
        try {
            date = formatter.parse(input);
        } catch (ParseException e) {
            LOG.error("Error parsing the date " + input + " with the following format " + format);
        }
        return date;
    }
    
    /**
     * Returns the 'COLLECTION' line info from the batch file.
     * @param batchFile the batch file as a list
     * @return the line containing the collection info
     */
    public static String[] getStudyLine(List<String[]> batchFile) {
        String[] results = new String[] {};
        for (String[] line : batchFile) {
            if (StringUtils.equalsIgnoreCase("COLLECTIONS", line[LINE_IDENTIFIER_INDEX])) {
                results = line;
                break;
            }
        }
        return results;
    }
    
    /**
     * Returns a list of all accrual count lines from the batch file.
     * @param batchFile the batch file as a list
     * @return the list of accrual counts
     * @throws PAException when error.
     */ 
    public static Map<Ii, Int> getAccrualCounts(List<String[]> batchFile) throws PAException {
        Map<Ii, Int> accrualCounts = new HashMap<Ii, Int>();
        for (String[] line : batchFile) {
            if (StringUtils.equalsIgnoreCase("ACCRUAL_COUNT", line[LINE_IDENTIFIER_INDEX])) {
                Integer count = Integer.valueOf(line[ACCRUAL_COUNT_INDEX]);
                // assume validator has already vetted all invalid part sites.
                Ii partSiteIi = getOrganizationIi(line[ACCRUAL_COUNT_STUDY_SITE_INDEX]);
                if (!ISOUtil.isIiNull(partSiteIi)) {
                    accrualCounts.put(partSiteIi, IntConverter.convertToInt(count));
                } else {
                    accrualCounts.put(IiConverter.convertToIi(line[ACCRUAL_COUNT_STUDY_SITE_INDEX]), 
                            IntConverter.convertToInt(count));
                }
            }
        }
        return accrualCounts;
    }
    
    /**
    * Retrieves the PO identifier of the organization related with the given identifier.
    * @param orgIdentifier the CTEP/DCP identifier or the po id of the org
    * @return the po identifier of the org
    * @throws PAException on error
    */
   public static Ii getOrganizationIi(String orgIdentifier) throws PAException {
       Ii resultingIi = null;
       //Look up via other identifiers first in case a CTEP/DCP id is being passed
       IdentifiedOrganizationDTO identifiedOrg = new IdentifiedOrganizationDTO();
       identifiedOrg.setAssignedId(IiConverter.convertToIdentifiedOrgEntityIi(orgIdentifier));
       List<IdentifiedOrganizationDTO> results = 
           PoRegistry.getIdentifiedOrganizationCorrelationService().search(identifiedOrg);
       //If any results are found, select the first one and get the org id from there.
       //Otherwise assume that the identifier given is the po id and just return that.
       if (CollectionUtils.isNotEmpty(results)) {
           resultingIi = results.get(0).getPlayerIdentifier();
       } else {
           try {
                if (NumberUtils.isNumber(orgIdentifier)) {
                    OrganizationDTO org = PoRegistry.getOrganizationEntityService().getOrganization(
                            IiConverter.convertToPoOrganizationIi(orgIdentifier));
                    resultingIi = org != null ? org.getIdentifier() : null;
                }
           } catch (NullifiedEntityException e) {
               LOG.error("The organization with the identifier " + orgIdentifier 
                       +  " that is attempting to be loaded is nullified.");
           }
       }       
       return resultingIi;
   }    
    
    /**
     * Returns a list of all the patient lines from the batch file.
     * @param batchFile the batch file as a list
     * @return the list of patient lines
     */
    public static List<String[]> getPatientInfo(List<String[]> batchFile) {
        List<String[]> patients = new ArrayList<String[]>();
        for (String[] line : batchFile) {
            if (StringUtils.equalsIgnoreCase("PATIENTS" , line[LINE_IDENTIFIER_INDEX])) {
                patients.add(line);
            }
        }
        return patients;
    }
    
    /**
     * Returns a map of patient ids to patient race codes.
     * @param batchFile the batch file as a list
     * @return a map of patient ids to races
     */
    public static Map<String, List<String>> getPatientRaceInfo(List<String[]> batchFile) {
        Map<String, List<String>> raceMap = new HashMap<String, List<String>>();
        for (String[] line : batchFile) {
            if (StringUtils.equalsIgnoreCase("PATIENT_RACES", line[LINE_IDENTIFIER_INDEX])) {
                String patientId = line[PATIENT_ID_INDEX];
                if (raceMap.get(patientId) == null) {
                    raceMap.put(patientId, Arrays.asList(line[RACE_INDEX]));
                } else {
                    Set<String> hashSet = new HashSet<String>();
                    hashSet.addAll(raceMap.get(patientId));
                    hashSet.add(line[RACE_INDEX]);
                    raceMap.put(patientId, new LinkedList<String>(hashSet));
                }
            }
        }
        return raceMap;
    }
    
    /**
     * Returns a list of all accrual count lines from the batch file.
     * @param batchFile the batch file as a list
     * @return the list of accrual counts
     * @throws PAException when error.
     */ 
    public static Map<String, Integer> getStudySiteCounts(List<String[]> batchFile) throws PAException {
        Map<String, Integer> accrualCounts = new HashMap<String, Integer>();
        for (String[] line : batchFile) {
            if (StringUtils.equalsIgnoreCase("ACCRUAL_COUNT", line[LINE_IDENTIFIER_INDEX])) {
                Integer count = Integer.valueOf(line[ACCRUAL_COUNT_INDEX]);
                accrualCounts.put(line[ACCRUAL_COUNT_STUDY_SITE_INDEX], count);
            }
        }
        return accrualCounts;
    }
}

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
package gov.nih.nci.pa.util;

import static gov.nih.nci.pa.service.AbstractBaseIsoService.ADMIN_ABSTRACTOR_ROLE;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.AddressPartType;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.dto.MilestonesDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.CodedEnum;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;
import gov.nih.nci.pa.enums.UnitsCode;
import gov.nih.nci.pa.iso.convert.StudyMilestoneConverter;
import gov.nih.nci.pa.iso.dto.BaseDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter.JavaPq;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PAExceptionConstants;
import gov.nih.nci.pa.service.StudySourceInterceptor;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.ISOUtil.ValidDateFormat;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.SessionContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.fiveamsolutions.nci.commons.authentication.CommonsGridLoginModule;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;

import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;

/**
 * This is a selection of utilities, useful for PA. This set of utilities is safe to use in the grid services. Do
 * not, I repeat, do not add methods that reference domain objects. If you need to manipulate domain objects do so
 * in PADomainUtils.
 *
 * @author Naveen Amiruddin
 * @since 05/30/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessiveClassLength", "PMD.CyclomaticComplexity" })
public class PAUtil {
    
    private static final String OTHER = "Other";
    
    private static final int TM_FMT_INDX = 6;
    /**
     * Date format.
     */
    public static final String DATE_FORMAT = "MM/dd/yyyy";
    /**
     * Date format with time
     */
    public static final String DATE_FORMAT_WITH_TIME = "MM/dd/yyyy hh:mm:ss a";
    
    private static final String EXTN = "extn";
    private static final String EXT = "ext";
    private static final int EXTN_COUNT = 4;
    private static final Logger LOG = Logger.getLogger(PAUtil.class);
    private static final String ID_OPEN_PAREN = " (id = ";
    private static final Map<String, String> ROOT_TO_NULLIFIED_ERROR_MAP = new HashMap<String, String>();
    private static final String UTF_8 = "UTF-8";
    private static final String TEMP_DOC_LOCATION = "temp_docs";

    private static final String HOLIDAY_STATE = "dc";
    private static final int DATE_YEAR_DIFF = 1900;
    private static final HolidayManager HOLIDAY_MANAGER = HolidayManager
            .getInstance(HolidayCalendar.UNITED_STATES);

    static {
        ROOT_TO_NULLIFIED_ERROR_MAP.put(IiConverter.HEALTH_CARE_FACILITY_ROOT, PAExceptionConstants.NULLIFIED_HCF);
        ROOT_TO_NULLIFIED_ERROR_MAP.put(IiConverter.HEALTH_CARE_PROVIDER_ROOT, PAExceptionConstants.NULLIFIED_HCP);
        ROOT_TO_NULLIFIED_ERROR_MAP.put(IiConverter.CLINICAL_RESEARCH_STAFF_ROOT, PAExceptionConstants.NULLIFIED_CRS);
        ROOT_TO_NULLIFIED_ERROR_MAP.put(IiConverter.OVERSIGHT_COMMITTEE_ROOT, PAExceptionConstants.NULLIFIED_OC);
        ROOT_TO_NULLIFIED_ERROR_MAP.put(IiConverter.IDENTIFIED_ORG_ROOT, PAExceptionConstants.NULLIFIED_IO);
        ROOT_TO_NULLIFIED_ERROR_MAP.put(IiConverter.RESEARCH_ORG_ROOT, PAExceptionConstants.NULLIFIED_RO);
        ROOT_TO_NULLIFIED_ERROR_MAP.put(IiConverter.IDENTIFIED_PERSON_ROOT, PAExceptionConstants.NULLIFIED_IP);
        ROOT_TO_NULLIFIED_ERROR_MAP.put(IiConverter.ORGANIZATIONAL_CONTACT_ROOT, PAExceptionConstants.NULLIFIED_OCT);
    }

    /**
     *
     * @param toValidate ii to validate
     * @param source source of the ii to validate
     * @return boolean
     * @throws PAException on invalid ii
     */
    public static boolean isValidIi(Ii toValidate , Ii source) throws PAException {
        boolean isValid = true;
        StringBuffer sb = new StringBuffer();
        if (ISOUtil.isIiNull(toValidate)) {
            throw new PAException("to Validate Identifier is null");
        }
        if (!source.getIdentifierName().equals(toValidate.getIdentifierName())) {
            sb.append(" Identifier Name does not match for " + source.getIdentifierName()
                    +  ", Exptected is " + toValidate.getIdentifierName());
        }
        if (!source.getRoot().equals(toValidate.getRoot())) {
            sb.append(" Root does not match for " + source.getRoot() + ", Exptected is " + toValidate.getRoot());
        }
        if (sb.length() > 0) {
            throw new PAException(sb.toString());
        }
        return isValid;
    }

    /**
     * Convert an input string to a Date.
     *
     * @param inDate string to be normalized
     * @return Date
     */
    public static Date dateStringToDate(String inDate) {
        if (inDate == null || inDate.isEmpty()) {
            return null;
        }

        Date outDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        for (ValidDateFormat fm : ValidDateFormat.getDateFormats()) {
            if (outDate != null) {
                break;
            }
            sdf.applyPattern(fm.getPattern());
            sdf.setLenient(fm.isLenient());
            try {
                int endIndex = (inDate.trim().length() < fm.getEndIndex()) ? inDate.trim().length() : fm.getEndIndex();
                outDate = sdf.parse(inDate.trim().substring(0, endIndex));
            } catch (ParseException e) {
                // BUGBUG: outDate can only be null here - this method does nothing!
                outDate = null;
            }
        }
        return outDate;
    }
    /**
     * Convert an input string to a Date.
     *
     * @param inDate string to be normalized
     * @return Date
     */
    public static Date dateStringToDateTime(String inDate) {
        if (inDate == null) {
            return null;
        }
        Date outDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        for (ValidDateFormat fm : ValidDateFormat.getDateFormats()) {
            sdf.applyPattern(fm.getPattern());
            sdf.setLenient(false);
            try {
                int endIndex = (inDate.trim().length() < fm.getEndIndex()) ? inDate.trim().length() : fm.getEndIndex();
                String dateToParse = inDate.trim().substring(0, endIndex);
                outDate = sdf.parse(dateToParse);
                break;
            } catch (ParseException e) {
               continue; //best effort to try the other date format(s).
            }
        }
        return outDate;
    }

    /**
     *
     * @param isoTs timestamp
     * @param format data format
     * @return String
     */
    public static String convertTsToFormattedDate(Ts isoTs , String format) {
        Timestamp ts = TsConverter.convertToTimestamp(isoTs);
        if (ts == null) {
            return  null;
        }
        DateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        return  formatter.format(ts);
    }

    /**
    * Convert a Ts to a formatted date string. Output format is
    * determined by the first element in the static dateFormats array.
    *
    * @param isoTs timestamp
    * @return String
    */
   public static String convertTsToFormattedDate(Ts isoTs) {
       return convertTsToFormattedDate(isoTs, ValidDateFormat.getDateFormats().get(0).getPattern());
   }

    /**
     * Convert an input string to a normalized date string.
     * The output format is determined by the first element in
     * the static dateFormats array.
     *
     * @param inDate string to be normalized
     * @return normalized string
     */
    public static String normalizeDateString(String inDate) {
        Date outDate = dateStringToDate(inDate);
        if (outDate == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(ValidDateFormat.getDateFormats().get(0).getPattern());
        return sdf.format(outDate);
    }
    /**
     * Convert an input string to a normalized date string.
     * The output format is determined by the first element in
     * the static dateFormats array.
     *
     * @param inDate string to be normalized
     * @return normalized string
     */
    public static String normalizeDateStringWithTime(String inDate) {
        Date outDate = dateStringToDateTime(inDate);
        if (outDate == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(ValidDateFormat.getDateFormats().get(1).getPattern());
        return sdf.format(outDate);
    }
    
    /**
     * Convert an input string to a normalized time string.
     * The output format is determined by the seventh element in
     * the static dateFormats array.
     *
     * @param inDate string to be normalized
     * @return normalized string 
     */
    public static String normalizeTimeString(String inDate) {
        Date outDate = dateStringToDate(inDate);
        if (outDate == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(ValidDateFormat.getDateFormats().get(TM_FMT_INDX).getPattern());
        return sdf.format(outDate);
    }

    /**
     * Convert an input string to a Timestamp.
     *
     * @param inDate string to be normalized
     * @return Timestamp
     */
    public static Timestamp dateStringToTimestamp(String inDate) {
        Date dt = dateStringToDate(inDate);
        return (dt == null) ? null : new Timestamp(dt.getTime());
    }

    /**
     * @return today's date as a string
     */
    public static String today() {
        return normalizeDateString(new Timestamp((new Date()).getTime()).toString());
    }  
    

    /**
     * Util method to validate email addresses.
     *
     * @param email to check the string
     * @return boolean whether email is valid or not
     */
    public static boolean isValidEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        String match = email.trim();
        Pattern p = Pattern.compile("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$");
        Matcher m = p.matcher(match);
        return  m.matches();
    }

    /**
     * Checks if is valid phone.
     *
     * @param phone the phone
     *
     * @return true, if is valid phone
     */
    public static boolean isValidPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        String match = phone.trim();
        Pattern p = Pattern.compile("^([\\w\\s\\-\\.\\+\\(\\)])*$");
        Matcher m = p.matcher(match);
        return  m.matches();
    }
    
    /**
     * Check validity of US or Canada phone number.
     * 
     * @param phone
     *            phone.
     * @return boolean match
     */
    public static boolean isUsOrCanadaPhoneNumber(String phone) {
        Pattern p = Pattern.compile("^\\d{3}-\\d{3}-\\d{4}(x\\d+)?$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }
    

     /**
     * Util method to validate Selection Yes/No.
     *
     * @param selection to check the string
     * @return boolean whether selection is valid or not
     */
    public static boolean isYesNo(String selection) {
       return "yes".equalsIgnoreCase(selection) || "no".equalsIgnoreCase(selection);
    }

    /**
     * @param data the input value which will be converted parameter used in search query
     * @return not null sting with * converted to %
     */
    public static String wildcardCriteria(String data) {
        String criteria = data;
        if (criteria == null) {
            criteria = "";
        }
        return criteria.replace('*', '%');
    }
    /**
     * Check if the given string is longer than <code>len</code>.
     * @param st String data
     * @param len length to compare against
     * @return true iff st is longer than len
     */
    public static boolean isGreaterThan(St st, int len) {
        boolean ret = false;
        String str = null;
        if (st == null) {
            ret = false;
        }
        str = StConverter.convertToString(st);
        if (str == null) {
            ret = false;
        } else if (str.length() > len) {
            ret = true;
        }
        return ret;
    }

    /**
     * Check if the length of a String is between min and max.
     * @param st String data
     * @param min minimum numbers of characters
     * @param max maximim numbers of characters
     * @return true if string length is within the giving range
     */
    public static boolean isWithinRange(St st, int min, int max) {
        boolean ret = false;
        String str = null;
        if (st == null) {
            ret = true;
        }
        str = StConverter.convertToString(st);
        if (str == null) {
            ret = true;
        } else if (str.length() >= min && str.length() <= max) {
            ret = true;
        }
        return ret;
    }
    /**
     *
     * @param identifier Ii
     * @return str
     */
    public static String getIiExtension(Ii identifier) {
        String ext = "";
        if (!ISOUtil.isIiNull(identifier)) {
            ext = identifier.getExtension();
        }
        return ext;
    }
    /**
     * @param errMap error map
     * @return str
     */
    public static String getErrorMsg(Map<String, String[]> errMap) {
        TreeSet<String> orderedKeys = new TreeSet<String>(errMap.keySet());
        StringBuffer errMsg = new StringBuffer();
        for (Iterator<String> iterator = orderedKeys.iterator(); iterator.hasNext();) {
            String key = iterator.next();
            String dash = " - ";
            errMsg.append(key).append(dash);
            errMsg.append(Arrays.deepToString(errMap.get(key)));
            if (iterator.hasNext()) {
                errMsg.append('\n');
            }
        }
        String strMsg = "";
        if (errMsg.length() > 1) {
            strMsg =  errMsg.toString();
            strMsg = strMsg.replace('[', ' ');
            strMsg = strMsg.replace(']', ' ');
        }
        return strMsg;
    }

    /**
     * loops through the map and returns the identifier of the ii.
     * @param map map of iis
     * @param key key
     * @return matching ii
     */
    public static Ii containsIi(Map<Ii, Ii> map , Ii key) {
        Ii value = null;
        if (map == null || key == null) {
            return value;
        }
        for (Ii tmp : map.keySet()) {
            if (tmp.getExtension().equals(key.getExtension())) {
                value = map.get(tmp);
                break;
            }
        }
        return value;
    }

    /**
     *
     * @param <TYPE> any base object extending BaseDTO
     * @param list list of objects
     * @return <TYPE> any base object extending BaseDTO
     */
    @SuppressWarnings("unchecked")
    public static <TYPE extends BaseDTO> TYPE getFirstObj(List<? extends BaseDTO> list) {
        TYPE type = null;
        if (list != null && !list.isEmpty()) {
             type =  (TYPE) list.get(0);
        }
        return type;

    }

    /**
     * Gets the document path where the document data should be stored.
     * @param id document ID.
     * @param filename document filename.
     * @param nciIdentifier nci identifier
     * @return the file path
     * @throws PAException on error
     */
    public static String getDocumentFilePath(Long id, String filename, String nciIdentifier) throws PAException {
        String folderPath = PaEarPropertyReader.getDocUploadPath();
        StringBuffer sb  = new StringBuffer(folderPath);
        sb.append(File.separator).append(nciIdentifier)
            .append(File.separator).append(id).append('-')
            .append(filename);
        return sb.toString();
    }

    /**
     * Gets the temporary document path for the given document.
     * @param dto the document dto
     * @return the temporary file path
     * @throws PAException on error
     */
    public static String getTemporaryDocumentFilePath(DocumentDTO dto) throws PAException {
        String folderPath = PaEarPropertyReader.getDocUploadPath();
        StringBuffer sb  = new StringBuffer(folderPath);
        sb.append(File.separator).append(TEMP_DOC_LOCATION).append(File.separator)
            .append(IiConverter.convertToLong(dto.getIdentifier())).append('-')
            .append(StConverter.convertToString(dto.getFileName()));
        return sb.toString();
    }

    /**
     * @param date date
     * @return boolean
     */
    public static boolean isDateCurrentOrPast(String date) {
        Timestamp siteStatusDate = PAUtil.dateStringToTimestamp(date);
        return isDateCurrentOrPast(siteStatusDate);
    }

    /**
     * @param date date
     * @return boolean
     */
    public static boolean isDateCurrentOrPast(Timestamp date) {
        boolean retValue = false;
        Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
        if (currentTimeStamp.before(date)) {
            retValue = true;
        }
        return retValue;
    }

    /**
     * check if the date is of valid format.
     * @param dateString dateString
     * @return boolean
     */
    public static boolean isValidDate(String dateString) {
        if (StringUtils.isEmpty(dateString)) {
            return false;
        }
        //set the format to use as a constructor argument
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        if (dateString.trim().length() != dateFormat.toPattern().length())  {
            return false;
        }
        dateFormat.setLenient(false);
        try {
            //parse the date
            dateFormat.parse(dateString.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
   }

    /**
     * @param value value
     * @return bd
     */
    public static BigDecimal convertStringToDecimal(String value) {
        BigDecimal bd = null;
        if (NumberUtils.isNumber(value)) {
            bd = BigDecimal.valueOf(Double.parseDouble(value));
        }
        return bd;
    }

    /**
     * @param javapq javapq
     * @return bd
     */
    public static String convertPqToUnit(JavaPq javapq) {
        String unit = null;
        if (javapq != null && javapq.getUnit() != null) {
           unit = javapq.getUnit();
        }
        return unit;
    }

    /**
     * @param javapq javapq
     * @return bd
     */
    public static BigDecimal convertPqToDecimal(JavaPq javapq) {
        BigDecimal bd = null;
        if (javapq != null && javapq.getValue() != null) {
            bd = javapq.getValue();
        }
        return bd;
    }

    /**
     * @param javapq javapq
     * @return bd
     */
    public static Integer convertPqToPrecision(JavaPq javapq) {
        Integer precision = null;
        if (javapq != null && javapq.getPrecision() != null) {
          precision = javapq.getPrecision();
        }
        return precision;
    }

    /**
     * Checks if is type intervention.
     *
     * @param cd the cd
     *
     * @return true, if is type intervention
     */
    public static boolean isTypeIntervention(Cd cd) {
      boolean isTypeIntervention = false;
      if (ActivityCategoryCode.INTERVENTION.equals(ActivityCategoryCode.getByCode(CdConverter
                 .convertCdToString(cd)))
            || ActivityCategoryCode.PLANNED_PROCEDURE.equals(ActivityCategoryCode.getByCode(CdConverter
                     .convertCdToString(cd)))
            || ActivityCategoryCode.SUBSTANCE_ADMINISTRATION.equals(ActivityCategoryCode.getByCode(CdConverter
                     .convertCdToString(cd)))) {
        isTypeIntervention = true;
      }
       return isTypeIntervention;
    }

    /**
     * isDSetTelAndEmailNull.
     * @param telecomAddresses tel
     * @return boolean
     */
    public static boolean isDSetTelAndEmailNull(DSet<Tel> telecomAddresses) {
        return telecomAddresses == null || telecomAddresses.getItem() == null
            || isDsetItemsEmpty(telecomAddresses, true, true);
    }

    private static boolean isDsetItemsEmpty(DSet<Tel> telecomAddresses, boolean checkPhone, boolean checkEmail) {
        for (Tel t : telecomAddresses.getItem()) {
            if (t.getNullFlavor() != null) {
                    continue;
            }

            try {
                if (StringUtils.isNotEmpty(getSchemeSpecificPart(t, checkPhone, checkEmail))) {
                    return false;
                }
            } catch (UnsupportedEncodingException e) {
                    continue;
            }
        }
        return true;
    }

    /**
    *
    * @param telecomAddresses tel
    * @return boolean
    */
   public static boolean isDSetTelNull(DSet<Tel> telecomAddresses) {
       return telecomAddresses == null || telecomAddresses.getItem() == null
           || isDsetItemsEmpty(telecomAddresses, true, false);
   }

    private static String getSchemeSpecificPart(Tel t, boolean checkPhone, boolean checkEmail)
        throws UnsupportedEncodingException {
        String data = "";
        if (checkPhone && t instanceof TelPhone) {
            data = URLDecoder.decode(t.getValue().getSchemeSpecificPart(), UTF_8);
        } else if (checkEmail && t instanceof TelEmail) {
            data = URLDecoder.decode(t.getValue().getSchemeSpecificPart(), UTF_8);
        } else {
            data = getSchemeSpecificPartByUrl(t, checkPhone, checkEmail);
        }
        return data;
    }

    private static String getSchemeSpecificPartByUrl(Tel t, boolean checkPhone, boolean checkEmail)
    throws UnsupportedEncodingException {
        String url = t.getValue().toString();
        if (url != null && ((checkPhone
                && url.startsWith("tel")) || (checkEmail && url.startsWith("mailto")))) {
            return URLDecoder.decode(t.getValue().getSchemeSpecificPart(), UTF_8);
        }
        return "";
    }

    /**
     * @param dset telecom address
     * @return email the email
     */
    public static String getEmail(DSet<Tel> dset) {
        return getEmailOrPhone(DSetConverter.convertDSetToList(dset, "EMAIL"));
    }
    
    /**
     * @param tel
     *            telecom address, presumably an email address.
     * @return email the email address
     */
    public static String getEmail(Tel tel) {
        if (tel == null || tel.getValue() == null
                || tel.getNullFlavor() != null) {
            return null;
        }
        DSet<Tel> dset = new DSet<Tel>();
        dset.setItem(new HashSet<Tel>());
        dset.getItem().add(tel);
        return getEmail(dset);
    }    

    /**
     * @param dset telecom address
     * @return phone the phone without extension
     */
    public static String getPhone(DSet<Tel> dset) {
        String phone = getEmailOrPhone(DSetConverter.convertDSetToList(dset, "PHONE"));
        return (phone != null ? getPhone(phone) : null);
    }

    /**
     * @param dset telecom address
     * @return extn the phone extension
     */
    public static String getPhoneExtension(DSet<Tel> dset) {
        String phoneWithExtn = getEmailOrPhone(DSetConverter.convertDSetToList(dset, "PHONE"));
        return (phoneWithExtn != null ? getPhoneExtn(phoneWithExtn) : null);
    }

    private static String getEmailOrPhone(List<String> emailsOrPhones) {
        String retVal = null;
        if (emailsOrPhones != null && !emailsOrPhones.isEmpty()) {
            retVal = emailsOrPhones.get(0);
        }
        return retVal;
    }

    /**
     *
     * @param phone phone with ex
     * @return extn
     */
    public static String getPhoneExtn(String phone) {
        if (phone == null) {
            return StringUtils.EMPTY;
        }        
        String strExtn = "";
        if (phone.contains(EXTN)) {
            strExtn = phone.substring(phone.indexOf(EXTN) + EXTN_COUNT);
        } else if (phone.contains(EXT)) {
            strExtn = phone.substring(phone.indexOf(EXT) + EXT.length());
        }
        return strExtn;
    }
    /**
     *
     * @param phone phone
     * @return phone
     */
    public static String getPhone(String phone) {
        if (phone == null) {
            return StringUtils.EMPTY;
        }
        String strPhone = "";
        if (phone.contains(EXTN)) {
            strPhone = phone.substring(0, phone.indexOf(EXTN));
        } else if (phone.contains(EXT)) {
            strPhone = phone.substring(0, phone.indexOf(EXT));
        } else {
            strPhone = phone;
        }
        return strPhone;
    }

    private static boolean isMaxUnitInDaysAndMinUnitValid(String minUnit, String maxUnit) {
        return maxUnit.equalsIgnoreCase(UnitsCode.DAYS.getCode())
                && !(minUnit.equalsIgnoreCase(UnitsCode.WEEKS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.MONTHS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.YEARS.getCode()));
    }

    private static boolean isMaxUnitInHoursAndMinUnitValid(String minUnit, String maxUnit) {
        return maxUnit.equalsIgnoreCase(UnitsCode.HOURS.getCode())
                && !(minUnit.equalsIgnoreCase(UnitsCode.DAYS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.WEEKS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.MONTHS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.YEARS.getCode()));
    }

    private static boolean isMaxUnitInMinutesAndMinUnitValid(String minUnit, String maxUnit) {
        return maxUnit.equalsIgnoreCase(UnitsCode.MINUTES.getCode())
                && !(minUnit.equalsIgnoreCase(UnitsCode.HOURS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.DAYS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.WEEKS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.MONTHS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.YEARS.getCode()));
    }

    private static boolean isMaxUnitInWeeksAndMinUnitValid(String minUnit, String maxUnit) {
        return maxUnit.equalsIgnoreCase(UnitsCode.WEEKS.getCode())
                && !(minUnit.equalsIgnoreCase(UnitsCode.MONTHS.getCode())
                || minUnit.equalsIgnoreCase(UnitsCode.YEARS.getCode()));
    }

    private static boolean isMaxUnitInMonthsAndMinUnitValid(String minUnit, String maxUnit) {
        return maxUnit.equalsIgnoreCase(UnitsCode.MONTHS.getCode())
        && !minUnit.equalsIgnoreCase(UnitsCode.YEARS.getCode());
    }

    private static boolean checkMaxUnitSameOrLess(String minUnit, String maxUnit) {
        boolean isSameOrLess = false;
        if (isMaxUnitInMonthsAndMinUnitValid(minUnit, maxUnit)) {
            isSameOrLess = true;
        } else if (isMaxUnitInWeeksAndMinUnitValid(minUnit, maxUnit)) {
            isSameOrLess = true;
        } else if (isMaxUnitInDaysAndMinUnitValid(minUnit, maxUnit)) {
            isSameOrLess = true;
        } else if (isMaxUnitInHoursAndMinUnitValid(minUnit, maxUnit)) {
            isSameOrLess = true;
        } else if (isMaxUnitInMinutesAndMinUnitValid(minUnit, maxUnit)) {
            isSameOrLess = true;
        }
        return isSameOrLess;
    }

    /**
     *
     * @param minUnit min
     * @param maxUnit max
     * @return true
     */
    public static boolean isUnitLessOrSame(String minUnit, String maxUnit) {
        boolean isSameOrLess = false;
        if (minUnit.equalsIgnoreCase(maxUnit)) {
            isSameOrLess = true;
        }
        if (maxUnit.equalsIgnoreCase(UnitsCode.YEARS.getCode())) {
            isSameOrLess = true;
        } else {
            isSameOrLess = checkMaxUnitSameOrLess(minUnit, maxUnit);
        }
        return isSameOrLess;
    }
    /**
     * @param age age
     * @return s
     */
    public static String getAge(BigDecimal age) {
        String retAge = age.toString();
        if (retAge.endsWith(".0")) {
            retAge = retAge.replace(".0", "");
        }
        return retAge;
    }

    /**
     * Gets the assigned identifier extension.
     *
     * @param spDTO the sp dto
     *
     * @return the assigned identifier
     */
    public static String getAssignedIdentifierExtension(StudyProtocolDTO spDTO) {
        String assignedIdentifier = "";
        if (ISOUtil.isDSetNotEmpty(spDTO.getSecondaryIdentifiers())) {
            for (Ii ii : spDTO.getSecondaryIdentifiers().getItem()) {
                if (IiConverter.STUDY_PROTOCOL_ROOT.equals(ii.getRoot())) {
                    return ii.getExtension();
                }
            }
        }
        return assignedIdentifier;
    }

    /**
     * Gets the assigned identifier.
     *
     * @param spDTO the sp dto
     *
     * @return the assigned identifier
     */
    public static Ii getAssignedIdentifier(StudyProtocolDTO spDTO) {
        Ii assignedIdentifier = new Ii();
        assignedIdentifier.setNullFlavor(NullFlavor.NI);
        if (ISOUtil.isDSetNotEmpty(spDTO.getSecondaryIdentifiers())) {
            for (Ii ii : spDTO.getSecondaryIdentifiers().getItem()) {
                if (IiConverter.STUDY_PROTOCOL_ROOT.equals(ii.getRoot())) {
                    return ii;
                }
            }
        }
        return assignedIdentifier;
    }

    /**
     * Returns a listing of a study protocols other identifier extensions.
     * @param spDTO the study protocol dto
     * @return the other identifiers
     */
    public static List<Ii> getOtherIdentifiers(StudyProtocolDTO spDTO) {
        List<Ii> results = new ArrayList<Ii>();
        if (ISOUtil.isDSetNotEmpty(spDTO.getSecondaryIdentifiers())) {
            for (Ii id : spDTO.getSecondaryIdentifiers().getItem()) {
                if (StringUtils.equals(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT, id.getRoot())) {
                    results.add(id);
                }
            }
        }
        return results;
    }

    /**
     * Returns a listing of a study protocols identifiers that are not other identifiers.
     * @param spDTO the study protocol dto
     * @return the identifiers
     */
    public static Ii getNonOtherIdentifiers(StudyProtocolDTO spDTO) {
        Ii results = new Ii();
        if (ISOUtil.isDSetNotEmpty(spDTO.getSecondaryIdentifiers())) {
            for (Ii id : spDTO.getSecondaryIdentifiers().getItem()) {
                if (!StringUtils.equals(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT, id.getRoot())) {
                    return id;
                }
            }
        }
        return results;
    }


    /**
     * Check assigned identifier exists dto.
     *
     * @param spDTO the sp dto
     *
     * @return true, if successful
     */
    public static boolean checkAssignedIdentifierExists(StudyProtocolDTO spDTO) {
        boolean assignedIdentifierExists = false;
        if (ISOUtil.isDSetNotEmpty(spDTO.getSecondaryIdentifiers())) {
            for (Ii ii : spDTO.getSecondaryIdentifiers().getItem()) {
                if (IiConverter.STUDY_PROTOCOL_ROOT.equals(ii.getRoot())) {
                    return true;
                }
            }
        }
        return assignedIdentifierExists;
    }


    /**
     * Given a NullifiedEntityException pull out a useful error message.
     * @param e NEE.
     * @return message
     */
    public static String handleNullifiedEntityException(NullifiedEntityException e) {
        StringBuffer message = new StringBuffer("The entity is no longer available.");
        if (e.getNullifiedEntities().size() > 0) {
            message = new StringBuffer("");
            for (Ii key : e.getNullifiedEntities().keySet()) {
                if (IiConverter.ORG_ROOT.equals(key.getRoot())) {
                    message.append(handleNullifiedOrganization(key, e.getNullifiedEntities().get(key)));
                } else if (IiConverter.PERSON_ROOT.equals(key.getRoot())) {
                    message.append(handleNullifiedPerson(key, e.getNullifiedEntities().get(key)));
                } else {
                    continue;
                }
            }
        }
        return message.toString();
    }

    /**
     * Handle error message when nullified org exception happens.
     * @param oldIi nullified org ii.
     * @param newIi dup org ii.
     * @return string message.
     */
    public static String handleNullifiedOrganization(Ii oldIi, Ii newIi) {
        StringBuilder message = new StringBuilder();
        message.append(PAExceptionConstants.NULLIFIED_ORG);
        message.append(ID_OPEN_PAREN + oldIi.getExtension() + ")");
        OrganizationDTO poOrg = null;

          if (!ISOUtil.isIiNull(newIi)) {
                try {
                    poOrg = PoRegistry.getOrganizationEntityService().getOrganization(newIi);
                } catch (NullifiedEntityException e) {
                    LOG.info("handleNullifiedOrganization: " + e.getMessage());
                }
                if (poOrg == null) {
                    LOG.info("handleNullifiedOrganization failed to find a PO org");
                } else {
                    message.append(" , instead use ");
                    message.append(EnOnConverter.convertEnOnToString(poOrg.getName()));
                    message.append(ID_OPEN_PAREN + newIi.getExtension() + ")");
                }
            }

        return message.toString();
    }

    /**
     * Given a NullifiedRoleException pull out a useful error message.
     * @param e NRE.
     * @return message
     */
    public static String handleNullifiedRoleException(NullifiedRoleException e) {
        StringBuffer message = new StringBuffer("The entity is no longer available.");
        if (e.getNullifiedEntities().size() > 0) {
            message = new StringBuffer("");
            for (Ii key : e.getNullifiedEntities().keySet()) {
                message.append(handleNullifiedSR(key, e));
            }
        }
        return message.toString();
    }

    private static String handleNullifiedSR(Ii key, NullifiedRoleException e) {
        StringBuffer message = new StringBuffer("");
        String errorMsg = ROOT_TO_NULLIFIED_ERROR_MAP.get(key.getRoot());
        if (StringUtils.isEmpty(errorMsg)) {
            message.append("The structural role is not available.");
        } else {
            message.append(handleNullifiedSR(
                    errorMsg, key, e.getNullifiedEntities().get(key)));
        }
        return message.toString();
    }

    private static String handleNullifiedSR(String errorMsg, Ii oldIi, Ii newIi) {
        StringBuilder message = new StringBuilder();
        message.append(errorMsg);
        message.append(ID_OPEN_PAREN + oldIi.getExtension() + ")");

        if (!ISOUtil.isIiNull(newIi)) {
            message.append(" , instead use id = ");
            message.append(newIi.getExtension());
        }

        return message.toString();
    }

    /**
     * Handle error message when nullified org exception happens.
     * @param oldIi nullified org ii.
     * @param newIi dup org ii.
     * @return string message.
     */
    public static String handleNullifiedPerson(Ii oldIi, Ii newIi) {
        StringBuilder message = new StringBuilder();
        message.append(PAExceptionConstants.NULLIFIED_PERSON);
        message.append(ID_OPEN_PAREN + oldIi.getExtension() + ")");
        PersonDTO poPer = null;
        if (!ISOUtil.isIiNull(newIi)) {
            try {
               poPer = PoRegistry.getPersonEntityService().getPerson(newIi);
            } catch (NullifiedEntityException e) {
                LOG.info("handleNullifiedPerson: " + e.getMessage());
            }

            if (poPer == null) {
                LOG.info("handleNullifiedPerson failed to find a PO org");
            } else {
                message.append(" , instead use ");
                message.append(PADomainUtils.convertToPaPersonDTO(poPer).getFullName());
                message.append(ID_OPEN_PAREN + newIi.getExtension() + ")");
            }
        }
        return message.toString();
    }

    /**
     * Current time.
     * @return current time.
     * @throws ParseException if parse error.
     */
    public static Timestamp getCurrentTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        String simpleDate = sdf.format(new Date());
        return new Timestamp(sdf.parse(simpleDate).getTime());
    }

    /**
     * Returns the proper Primary Purpose Additional Qualifier Code based on the passed Primary Purpose Code.
     * @param primaryPurposeCode the Primary Purpose Code
     * @return 'Other' if Primary Purpose Code is 'Other', and null otherwise.
     */
    public static String lookupPrimaryPurposeAdditionalQualifierCode(String primaryPurposeCode) {
        String retVal = null;
        if (StringUtils.isNotEmpty(primaryPurposeCode) && PAUtil.isPrimaryPurposeCodeOther(primaryPurposeCode)) {
            retVal = PrimaryPurposeAdditionalQualifierCode.OTHER.getCode();
        }
        return retVal;
    }

    /**
     * For the case where study protocol is non-interventional and CTGovXMLRequired is false, 
     * primary completion date is optional.
     * @param studyProtocolDTO study protocol to examine.
     * @return false if the study protocol is non-interventional and CTGovXMLRequired is false, 
     * else returns true. 
     */
    public static boolean isPrimaryCompletionDateRequired(StudyProtocolDTO studyProtocolDTO) {
        boolean isPrimaryCompletionDateRequired = true;
        if (studyProtocolDTO instanceof NonInterventionalStudyProtocolDTO 
                && !BlConverter.convertToBool(studyProtocolDTO.getCtgovXmlRequiredIndicator())) {
            isPrimaryCompletionDateRequired = false;
        }
        return isPrimaryCompletionDateRequired;
    }
    
    /**
     * primaryPurposeOtherCode is req or not.
     * @param primaryPurposeCode primaryPurposeCode
     * @param primaryPurposeAdditionalQualifierCode primaryPurposeAdditionalQualifierCode
     * @return primaryPurposeOtherText is req or not.
     */
    public static boolean isPrimaryPurposeOtherCodeReq(String primaryPurposeCode,
            String primaryPurposeAdditionalQualifierCode) {
        return isPrimaryPurposeCodeOther(primaryPurposeCode)
            && StringUtils.isEmpty(primaryPurposeAdditionalQualifierCode);
    }
    /**
     * primaryPurposeOtherText is req or not.
     * @param primaryPurposeCode primaryPurposeCode
     * @param primaryPurposeAdditionalQualifierCode primaryPurposeAdditionalQualifierCode
     * @param primaryPurposeOtherText primaryPurposeOtherText
     * @return primaryPurposeOtherText is req or not.
     */
    public static boolean isPrimaryPurposeOtherTextReq(String primaryPurposeCode,
            String primaryPurposeAdditionalQualifierCode, String primaryPurposeOtherText) {
        return isPrimaryPurposeAdditionQualifierCodeOther(primaryPurposeCode, primaryPurposeAdditionalQualifierCode)
            && StringUtils.isEmpty(primaryPurposeOtherText);
    }
    /**
     *
     * @param primaryPurposeCode primaryPurposeCode
     * @param primaryPurposeAdditionalQualifierCode primaryPurposeAdditionalQualifierCode
     * @return boolean
     */
    public static boolean isPrimaryPurposeAdditionQualifierCodeOther(String primaryPurposeCode,
            String primaryPurposeAdditionalQualifierCode) {
        return isPrimaryPurposeCodeOther(primaryPurposeCode)
        && StringUtils.equalsIgnoreCase(primaryPurposeAdditionalQualifierCode,
                PrimaryPurposeAdditionalQualifierCode.OTHER.getCode());
    }

    /**
     *
     * @param primaryPurposeCode primaryPurposeCode
     * @return boolean
     */
    public static boolean isPrimaryPurposeCodeOther(String primaryPurposeCode) {
        return StringUtils.equalsIgnoreCase(primaryPurposeCode, OTHER);
    }
    /**
     * phaseOtherCode is req or not.
     * @param phaseCode phaseCode
     * @return phaseOtherText is req or not.
     */
    public static boolean isPhaseCodeNA(String phaseCode) {
        return StringUtils.equalsIgnoreCase(phaseCode, PhaseCode.NA.getCode());
    }

    /**
     * Convert a list of trial milestone dtos to latest admin, scientific, and general.
     * @param milestoneDto dto to load
     * @param studyMilestonesDtos dtos to order and translate.
     * @throws PAException if error.
     */
    public static void convertMilestoneDtosToDTO(MilestonesDTO milestoneDto,
            List<StudyMilestoneDTO> studyMilestonesDtos) throws PAException {
        Set<StudyMilestone> studyMilestones = new TreeSet<StudyMilestone>(new LastCreatedComparator());
        StudyMilestoneConverter smConv = new StudyMilestoneConverter();
        for (StudyMilestoneDTO smDto : studyMilestonesDtos) {
            studyMilestones.add(smConv.convertFromDtoToDomain(smDto));
        }
        convertMilestonesCopyToDTO(milestoneDto, studyMilestones);
    }

    /**
     * Convert a list of trial milestones into latest admin, scientific, and general.
     * A copy of the set is used to maintain the original set members in the trial domain object.
     * @param milestonesDto dto to load
     * @param studyMilestones list of trial milestones
     */
    public static void convertMilestonesToDTO(MilestonesDTO milestonesDto,
            Collection<StudyMilestone> studyMilestones) {
        Set<StudyMilestone> copy = new TreeSet<StudyMilestone>(new LastCreatedComparator());
        copy.addAll(studyMilestones);
        convertMilestonesCopyToDTO(milestonesDto, copy);
    }

    private static void convertMilestonesCopyToDTO(MilestonesDTO milestonesDto,
            Set<StudyMilestone> studyMilestones) {
        if (studyMilestones.isEmpty()) {
            return;
        }
        StudyMilestone studyMilestone = studyMilestones.iterator().next();
        if (isNotAdminOrScientificMilestone(studyMilestone.getMilestoneCode())) {
            milestonesDto.getStudyMilestone().setMilestone(studyMilestone.getMilestoneCode());
            milestonesDto.getStudyMilestone().setMilestoneDate(studyMilestone.getMilestoneDate());
            return;
        } else if (isAdminMilestone(milestonesDto.getAdminMilestone().getMilestone(),
                studyMilestone.getMilestoneCode())) {
            milestonesDto.getAdminMilestone().setMilestone(studyMilestone.getMilestoneCode());
            milestonesDto.getAdminMilestone().setMilestoneDate(studyMilestone.getMilestoneDate());
        } else if (isScientificMilestone(milestonesDto.getScientificMilestone().getMilestone(),
                studyMilestone.getMilestoneCode())) {
            milestonesDto.getScientificMilestone().setMilestone(studyMilestone.getMilestoneCode());
            milestonesDto.getScientificMilestone().setMilestoneDate(studyMilestone.getMilestoneDate());
        }
        studyMilestones.remove(studyMilestone);
        convertMilestonesCopyToDTO(milestonesDto, studyMilestones);
    }

    private static boolean isAdminMilestone(MilestoneCode currentCode, MilestoneCode input) {
        return currentCode == null && MilestoneCode.ADMIN_SEQ.contains(input);
    }

    private static boolean isScientificMilestone(MilestoneCode currentCode, MilestoneCode input) {
        return currentCode == null && MilestoneCode.SCIENTIFIC_SEQ.contains(input);
    }

    private static boolean isNotAdminOrScientificMilestone(MilestoneCode input) {
        return !MilestoneCode.ADMIN_SEQ.contains(input) && !MilestoneCode.SCIENTIFIC_SEQ.contains(input);
    }

    /**
     * Check that the user is a trial owner or abstractor.
     * @param spDTO trial dto
     * @throws PAException on error
     */
    public static void checkUserIsTrialOwnerOrAbstractor(StudyProtocolDTO spDTO) throws PAException {
        CSMUserUtil userService = CSMUserService.getInstance();
        String userName = UsernameHolder.getUser();
        if (userService.isUserInGroup(userName, ADMIN_ABSTRACTOR_ROLE)) {
            return;
        }
        User user = userService.getCSMUser(userName);
        RegistryUser userId = PaRegistry.getRegistryUserService().getUser(user.getLoginName());
        Long trialId = IiConverter.convertToLong(spDTO.getIdentifier());
        if (!PaRegistry.getRegistryUserService().isTrialOwner(userId.getId(), trialId)) {
            throw new PAException("User " + user.getLoginName() + " is not a trial owner for trial id " + trialId);
        }
    }
    
    /**
     * Checks whether the given URL is valid and complete, i.e. explicitly
     * specifies at least protocol name and host name. This method is often used
     * for checking URLs before passing them into {@link Tel} instances.
     * 
     * @param urlString URL
     * @return boolean true or false
     */
    public static boolean isCompleteURL(String urlString) {
        try {
            URL url = new URL(urlString);
            if (StringUtils.isBlank(url.getProtocol())
                    || StringUtils.isBlank(url.getHost())) {
                return false;
            }
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }
    
    /**
     * Gets a country name from a postal address.
     * 
     * @param postalAddress
     *            Ad
     * @return String
     */
    public static String getCountryName(Ad postalAddress) {
        int partSize = postalAddress.getPart().size();
        String countryName = "";
        AddressPartType type = null;
        for (int k = 0; k < partSize; k++) {
            type = postalAddress.getPart().get(k).getType();
            if (type.name().equals("CNT")) {
                countryName = postalAddress.getPart().get(k).getCode();
            }
        }
        return countryName;
    }
    
    /**
     * Adds or substract business days from the given date.
     * 
     * @param date
     *            Date
     * @param days
     *            days - adds when positive, substracts when negative.
     * @return Date
     */
    public static Date addBusinessDays(Date date, int days) { // NOPMD
        if (date == null) {
            return null;
        }
        int counter = days > 0 ? 1 : -1;
        int latch = Math.abs(days);
        while (latch > 0) {
            date = DateUtils.addDays(date, counter);
            if (PAUtil.isBusinessDay(date)) {
                latch--;
            }
        }
        return date;
    }
    
    /**
     * Counts the number of business days between the two dates, inclusive.
     * 
     * @param start
     *            date
     * @param end
     *            date
     * @return days
     */
    public static int getBusinessDaysBetween(final Date start, final Date end) {
        if (start == null || end == null || end.before(start)) {
            return 0;
        }
        int days = 0;
        Date current = start;
        do {
            if (isBusinessDay(current)) {
                days++;
            }
            current = DateUtils.addDays(current, 1);
        } while (!DateUtils.isSameDay(current, DateUtils.addDays(end, 1)));
        return days;
    }

    /**
     * Returns true if the given date is a business day in Washington, DC, USA.
     * 
     * @param date
     *            Date
     * @return boolean
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public static boolean isBusinessDay(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return false;
        }

        final Date cacheKey = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
        try {
            return Boolean.TRUE.equals(CacheUtils.getFromCacheOrBackend(
                    CacheUtils.getBizDayCache(), cacheKey.toGMTString(),
                    new CacheUtils.Closure() {
                        @SuppressWarnings("deprecation")
                        @Override
                        public Object execute() throws PAException {
                            final Set<Holiday> holidays = HOLIDAY_MANAGER
                                    .getHolidays(date.getYear()
                                            + DATE_YEAR_DIFF, HOLIDAY_STATE);
                            for (Holiday holiday : holidays) {
                                final Date holidayDate = holiday.getDate()
                                        .toDateTimeAtStartOfDay().toDate();
                                if (DateUtils.isSameDay(date, holidayDate)) {
                                    return Boolean.FALSE;
                                }
                                // The library does not properly handle
                                // situation
                                // when a holiday
                                // falls on Sat/Sun and is therefore
                                // pushed to the following Monday.
                                // CHECKSTYLE:OFF
                                if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
                                        && (DateUtils.isSameDay(
                                                DateUtils.addDays(date, -1),
                                                holidayDate) || DateUtils
                                                .isSameDay(DateUtils.addDays(
                                                        date, -2), holidayDate))) {
                                    return Boolean.FALSE;
                                }
                            }
                            return Boolean.TRUE;
                        }
                    }));
        } catch (PAException e) {
            LOG.error(e, e);
            throw new RuntimeException(e);
        }

    }
    
    /**
     * Null-safe method to get the code from a coded enum; returns "" if the enum is null.
     * @param ce CodedEnum from which to get the code (may be null)
     * @return the code or the empty string if <code>ce</code> was null
     */
    public static String getCode(CodedEnum<String> ce) {
        String str = "";
        if (ce != null) {
            str = ce.getCode();
        }
        return str;
    }
    
    
    /**
     * For the given {@link DocumentDTO} attempts to determine full name of the
     * person who last updated the document.
     * 
     * @param documentDTO
     *            DocumentDTO
     * @return full name of the person who last updated the document
     * @throws PAException 
     */
    public static String getDocumentUserLastUpdatedName(DocumentDTO documentDTO) {
        try {
            if (!ISOUtil.isStNull(documentDTO.getUserLastUpdated())) {
                User user = CSMUserService.getInstance().getCSMUser(
                        documentDTO.getUserLastUpdated().getValue());
                if (user != null) {
                    RegistryUser regUser = PaRegistry.getRegistryUserService()
                            .getUser(user.getLoginName());
                    String userName = regUser != null ? regUser.getFullName()
                            : CsmUserUtil.getDisplayUsername(user);
                    return userName;
                }
            }
        } catch (PAException e) {
           LOG.error(e, e);
        }
        return "";
    }
    
    /**
     * For the given {@link DocumentDTO} attempts to determine full name of the
     * person who last updated the document.
     * 
     * @param documentDTO
     *            DocumentDTO
     * @param isCtro isCtro
     * @return full name of the ctro user who reviewed the document
     * @throws PAException 
     */
    public static String getDocumentUserCtroOrCcctReviewerName(DocumentDTO documentDTO , boolean isCtro) {
        try {
            boolean isNotNull = false;
            if (isCtro) {
                isNotNull = !ISOUtil.isStNull(documentDTO.getCtroUserName());
            }
            else {
                isNotNull = !ISOUtil.isStNull(documentDTO.getCcctUserName());
            }
            if (isNotNull) {
                User user = null;
                if (isCtro) {
                    user= CSMUserService.getInstance().getCSMUser(
                            documentDTO.getCtroUserName().getValue());
                }
                else {
                    user = CSMUserService.getInstance().getCSMUser(
                            documentDTO.getCcctUserName().getValue());
                }
                       
                if (user != null) {
                    RegistryUser regUser = PaRegistry.getRegistryUserService()
                            .getUser(user.getLoginName());
                    String userName = regUser != null ? regUser.getFullName()
                            : CsmUserUtil.getDisplayUsername(user);
                    return userName;
                }
            }
        } catch (PAException e) {
           LOG.error(e, e);
        }
        return "";
    }
    

    /**
     * Sets this date to the very last instant of the day it represents.
     * 
     * @param date
     *            date, e.g. 01/01/2012
     * @return Date, e.g. 01/01/2012 23:59:59.999
     */
    public static Date endOfDay(Date date) {
        return date!=null?DateUtils.setMilliseconds(DateUtils.setSeconds(
                DateUtils.setMinutes(DateUtils.setHours(date, 23), 59), 59),
                999):null;
    }

    /**
     * Updates a PA Property value.
     * 
     * @param name
     *            name
     * @param value
     *            value
     */
    public static void updatePaProperty(String name, String value) {
        PaHibernateUtil
                .getCurrentSession()
                .createSQLQuery(
                        "update pa_properties set value='"
                                + StringEscapeUtils.escapeSql(value)
                                + "' where name='"
                                + StringEscapeUtils.escapeSql(name) + "'")
                .executeUpdate();
    }

    /**
     * Check the context and determine if call to ejb came from grid service.
     * @param ctx the ejb session context
     * @return true if call came in from grid
     */
    public static boolean isGridCall(SessionContext ctx) {
        boolean result = false;
        String gridSeparator = CommonsGridLoginModule
                .getGridServicePrincipalSeparator();
        if (ctx != null && ctx.getCallerPrincipal() != null
                && ctx.getCallerPrincipal().getName() != null
                && gridSeparator != null) {
            String name = ctx.getCallerPrincipal().getName();
            result = name.contains(gridSeparator)
                    || StudySourceInterceptor.STUDY_SOURCE_CONTEXT.get() == StudySourceCode.GRID_SERVICE;
        }
        LOG.info("Is Grid call? " + result);
        return result;
    }
    /**
     * @param email email
     * @param phone phone
     * @return dset
     */
    public static DSet<Tel> getDset(String email, String phone) {
        DSet<Tel> telecomAddress = null;
        if (StringUtils.isNotEmpty(phone)) {
            List<String> phoneList = new ArrayList<String>();
            phoneList.add(phone);
            telecomAddress = DSetConverter.convertListToDSet(phoneList, "PHONE", telecomAddress);
        }
        if (StringUtils.isNotEmpty(email)) {
            List<String> emailList = new ArrayList<String>();
            emailList.add(email);
            telecomAddress = DSetConverter.convertListToDSet(emailList, "EMAIL", telecomAddress);
        }
        return telecomAddress;
    }
    
    /**
     * @param item
     *            Set<Ii>
     * @param extension
     *            extension
     * @param root
     *            root
     * @return boolean
     */
    public static boolean containsIi(Set<Ii> item, String extension, String root) {
        for (Ii ii : item) {
            if (StringUtils.equals(ii.getExtension(), extension)
                    && StringUtils.equals(ii.getRoot(), root)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given number is in the given range. Range format supported:
     * &lt;N, N-M, &gt;N
     * 
     * @param n
     *            number
     * @param range
     *            Range format supported: &lt;N, N-M, &gt;N
     * @return true or false.
     */
    public static boolean isInRange(int n, final String range) {
        int start = 1;
        int end = -1;
        if (StringUtils.isNotBlank(range)) {
            if (range.matches("^<\\d+$")) {
                end = Integer.parseInt(range.replaceFirst("^<", "")) - 1;
                start = Integer.MIN_VALUE;
            } else if (range.matches("^>\\d+$")) {
                start = Integer.parseInt(range.replaceFirst("^>", "")) + 1;
                end = Integer.MAX_VALUE;
            } else if (range.matches("^\\d+\\-\\d+$")) {
                start = Integer.parseInt(range.replaceFirst("\\-\\d+$", ""));
                end = Integer.parseInt(range.replaceFirst("^\\d+\\-", ""));
            }

        }
        return n >= start && n <= end;
    }
    
    /**
     * apply additional filters on the result
     * 
     * @param studyProtocols
     */
    public static List<StudyProtocolQueryDTO> applyAdditionalFilters(
            List<StudyProtocolQueryDTO> studyProtocols) {
        List<String> documentWorkflowStatusCodes = getResultsDashboadStatusCodeFilter();
        List<StudyProtocolQueryDTO> filteredList = new ArrayList<>();
        for (StudyProtocolQueryDTO studyProtocolQueryDTO : studyProtocols) {
            // Filter out all studies with lead org = NCI-CCR  
            if (PAConstants.CCR_ORG_NAME
                    .equalsIgnoreCase(studyProtocolQueryDTO
                            .getLeadOrganizationName())) {
                continue;

            }
            if (!(documentWorkflowStatusCodes.contains(studyProtocolQueryDTO.getDocumentWorkflowStatusCode().getCode()) 
                || ((studyProtocolQueryDTO.getSubmissionTypeCode() == SubmissionTypeCode.A 
                    && studyProtocolQueryDTO.getDocumentWorkflowStatusCode() 
                        !=  DocumentWorkflowStatusCode.AMENDMENT_SUBMITTED) 
                    || (studyProtocolQueryDTO.getSubmissionTypeCode() == SubmissionTypeCode.U)))) {
                continue;
            }
            filteredList.add(studyProtocolQueryDTO);
        }
        return filteredList;
    }
    
    private static List<String> getResultsDashboadStatusCodeFilter() {
        List<String> list = new ArrayList<String>();
        list.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE
                .getCode());
        list.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE
                .getCode());
        list.add(DocumentWorkflowStatusCode.VERIFICATION_PENDING.getCode());
        return list;
    }

}

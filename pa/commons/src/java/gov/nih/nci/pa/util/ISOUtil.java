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

import gov.nih.nci.iso21090.Any;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ed;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.iso21090.Real;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.iso.util.IvlConverter.JavaPq;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
*
* @author Kalpana Guthikonda
* @since 1/12/2010
*/
public class ISOUtil {

    /**
     * checks if Ii is null.
     * @param ii ii
     * @return boolean
     */
    public static boolean isIiNull(Ii ii) {
        boolean isNull = false;
        if (ii == null || ii.getExtension() == null) {
            return true;
        }
        if (ii.getExtension().trim().length() == 0) {
            isNull = true;
        }
        return isNull;
    }

    /**
     *checks if Ed is null.
     * @param ed ed
     * @return boolean
     */
    public static boolean isEdNull(Ed ed) {
      return (ed == null || ed.getData() == null);
    }


    /**
     * checks if Cd is null.
     * @param cd cd
     * @return boolean
     */
    public static boolean isCdNull(Cd cd) {
        boolean isNull = false;
        if (cd == null || cd.getCode() == null) {
            return true;
        }
        if (cd.getCode().trim().length() == 0) {
            isNull = true;
        }
        return isNull;
    }

    /**
     * checks if St is null.
     * @param st st
     * @return boolean
     */
    public static boolean isStNull(St st) {
        boolean isNull = false;
        if (st == null || st.getValue() == null) {
            return true;
        }
        if (st.getValue().trim().length() == 0) {
            isNull = true;
        }
        return isNull;
    }

    /**
     * checks if Ts is null.
     * @param ts Ts
     * @return boolean
     */
    public static boolean isTsNull(Ts ts) {
        boolean isNull = false;
        if ((ts == null) || (ts.getValue() == null)) {
            isNull = true;
        }
        return isNull;
    }

    /**
     * checks if Bl is null.
     * @param bl Bl
     * @return boolean
     */
    public static boolean isBlNull(Bl bl) {
        boolean isNull = false;
        if (bl == null || bl.getValue() == null) {
            return true;
        }
        return isNull;
    }

    /**
     * checks if Int is null.
     * @param in Int
     * @return boolean
     */
    public static boolean isIntNull(Int in) {
        boolean isNull = false;
        if (in == null || in.getValue() == null) {
            return true;
        }
        return isNull;
    }

    /**
     * checks if Real is null.
     * @param real Real
     * @return boolean
     */
    public static boolean isRealNull(Real real) {
        return real == null || real.getValue() == null;
    }

    /**
     * checks if pq value is null.
     * @param pq Pq
     * @return boolean
     */
    public static boolean isPqValueNull(Pq pq) {
        boolean isNull = false;
        if (pq == null || pq.getValue() == null) {
            return true;
        }
        return isNull;
    }
    /**
     * checks if pq Unit is null.
     * @param pq Pq
     * @return boolean
     */
    public static boolean isPqUnitNull(Pq pq) {
        boolean isNull = false;
        if (pq == null || pq.getUnit() == null) {
            return true;
        }
        return isNull;
    }

    /**
     * checks if ivl high is null.
     * @param ivl Ivl
     * @return boolean
     */
    public static boolean isIvlHighNull(Ivl<Pq> ivl) {
        boolean isNull = false;
        if (ivl == null || ivl.getHigh() == null || ivl.getHigh().getValue() == null) {
            return true;
        }
        return isNull;
    }
    /**
     * checks if ivl low is null.
     * @param ivl Ivl
     * @return boolean
     */
    public static boolean isIvlLowNull(Ivl<Pq> ivl) {
        boolean isNull = false;
        if (ivl == null || ivl.getLow() == null || ivl.getLow().getValue() == null) {
            return true;
        }
        return isNull;
    }
    
    
    /**
     * checks if ivl unit is null.
     * @param ivl Ivl
     * @return boolean
     */
    public static boolean isIvlUnitNull(Ivl<Pq> ivl) {
        boolean isNull = false;
        if (ivl == null ||  ivl.getHigh() == null
            || ivl.getLow() == null || ivl.getHigh().getUnit() == null || ivl.getLow().getUnit() == null) {
            return true;
        }
        return isNull;
    }

    /**
     * Checks to see if the given DSet is empty, meaning it's either null or has no items in it.
     * @param dset the DSet to check
     * @param <T> type
     * @return true iff the given DSet is null or contains no items
     */
    public static <T extends Any> boolean isDSetEmpty(DSet<T> dset) {
        return dset == null || CollectionUtils.isEmpty(dset.getItem());
    }


    /**
     * Checks to see if the given DSet is not empty, meaning it's not null there's at least one items in it.
     * @param dset the DSet to check
     * @param <T> type
     * @return true iff the given DSet isn't null and contains contains at least one item
     */
    public static <T extends Any> boolean isDSetNotEmpty(DSet<T> dset) {
        return dset != null && !CollectionUtils.isEmpty(dset.getItem());
    }

    /**
     * Public class used to decode and normalize date strings.
     */
    public static class ValidDateFormat {
        private final String pattern;
        private final int endIndex;
        private final boolean lenient;
        /**
         * Static ordered list of valid date format patterns.
         */
        private static ValidDateFormat[] dateFormats;

        static {
            dateFormats = new ValidDateFormat[] {
                    new ValidDateFormat("MM/dd/yyyy"),
                    new ValidDateFormat("yyyy-MM-dd HH:mm:ss"),
                    new ValidDateFormat("yyyy-MM-dd"),
                    new ValidDateFormat("yyyy/MM/dd"),
                    new ValidDateFormat("MM-dd-yyyy"),
                    new ValidDateFormat("MM-dd-yyyy HH:mm:ss"),
                    new ValidDateFormat("HH:mm:ss")
            };
        }

        /**
         * Public constructor.
         * @param pattern Pattern
         */
        public ValidDateFormat(String pattern) {
            this.pattern = pattern;
            this.endIndex = pattern.length();
            this.lenient = false;
        }

        /**
         * @return the dateFormats
         */
        public static List<ValidDateFormat> getDateFormats() {
            return Arrays.asList(dateFormats);
        }

        /**
         * @return the pattern
         */
        public String getPattern() {
            return pattern;
        }

        /**
         * @return the endIndex
         */
        public int getEndIndex() {
            return endIndex;
        }

        /**
         * @return the lenient
         */
        public boolean isLenient() {
            return lenient;
        }
    }

    /**
     * Convert an input string to a Date.
     *
     * @param inDate string to be normalized
     * @return Date
     */
    public static Date dateStringToDate(String inDate) {
        if (inDate == null) {
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
}

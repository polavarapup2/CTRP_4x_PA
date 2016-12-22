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
package gov.nih.nci.pa.iso.util;

import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.AdxpAdl;
import gov.nih.nci.iso21090.AdxpAl;
import gov.nih.nci.iso21090.AdxpCnt;
import gov.nih.nci.iso21090.AdxpCty;
import gov.nih.nci.iso21090.AdxpSta;
import gov.nih.nci.iso21090.AdxpZip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Harsha
 *
 */
public class AddressConverterUtil {

    /**
     * @param streetAddressLine street address
     * @param deliveryAddressLine delivery address
     * @param cityOrMunicipality city name
     * @param stateOrProvince state or province
     * @param postalCode postal code
     * @param countryAlpha3 ISO-3316 3-letter country code
     * @return simply ISO address
     */
    public static Ad create(String streetAddressLine, String deliveryAddressLine, String cityOrMunicipality,
            String stateOrProvince, String postalCode, String countryAlpha3) {
        Ad iso = new Ad();
        List<Adxp> addressParts = new ArrayList<Adxp>();
        iso.setPart(addressParts);
        setValue(addressParts, streetAddressLine, new AdxpAl());
        if (StringUtils.isNotBlank(deliveryAddressLine)) {
            setValue(addressParts, deliveryAddressLine, new AdxpAdl());
        }
        setValue(addressParts, cityOrMunicipality, new AdxpCty());
        setValue(addressParts, stateOrProvince, new AdxpSta());
        setValue(addressParts, postalCode, new AdxpZip());

        if (countryAlpha3 != null) {
            Adxp country = new AdxpCnt();
            country.setCode(countryAlpha3);
            country.setValue(countryAlpha3);
            country.setCodeSystem("ISO 3166-1 alpha-3 code");
            addressParts.add(country);
        }
        return iso;
    }

    private static void setValue(List<Adxp> addressParts, String value, Adxp addressPart) {
        if (StringUtils.isNotBlank(value)) {
            addressPart.setValue(value);
            addressParts.add(addressPart);
        }
    }

    /**
     * converts the iso ad to address.
     * @param ad iso address
     * @return appended string buffer
     */
    public static String convertToAddress(Ad ad) {
        if (ad == null || CollectionUtils.isEmpty(ad.getPart())) {
            return null;
        }
        List<Adxp> adxpList = ad.getPart();
        StringBuffer sb = new StringBuffer();
        for (Adxp adxp : adxpList) {
            if (isNextToLastAdxp(adxp)) {
                sb.append(adxp.getValue()).append(", ");
            }
            if (adxp instanceof AdxpCnt) {
                sb.append(adxp.getCode());
            }
        }
        if (sb.lastIndexOf(",") > 0) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        return sb.toString();
    }

    private static boolean isNextToLastAdxp(Adxp adxp) {
        return (adxp instanceof AdxpAl
                || adxp instanceof AdxpCty
                || adxp instanceof AdxpSta
                || adxp instanceof AdxpZip);
    }

    /**
     * Help convert an Ad into a Address Bo.
     * @param ad address
     * @return address
     */
    public static Map<String, String> convertToAddressBo(Ad ad) {
        Map<String, String> addressMap = new HashMap<String, String>();
        if (ad == null || CollectionUtils.isEmpty(ad.getPart())) {
            return null;
        }
        List<Adxp> adxpList = ad.getPart();

        for (Adxp adxp : adxpList) {
            addressMap.put(AdxpAl.class.getName(),
                    getAddressPart(adxp, AdxpAl.class, addressMap.get(AdxpAl.class.getName())));
            addressMap.put(AdxpCty.class.getName(),
                    getAddressPart(adxp, AdxpCty.class, addressMap.get(AdxpCty.class.getName())));
            addressMap.put(AdxpSta.class.getName(),
                    getAddressPart(adxp, AdxpSta.class, addressMap.get(AdxpSta.class.getName())));
            addressMap.put(AdxpZip.class.getName(),
                    getAddressPart(adxp, AdxpZip.class, addressMap.get(AdxpZip.class.getName())));
            addressMap.put(AdxpCnt.class.getName(),
                    getAddressPart(adxp, AdxpCnt.class, addressMap.get(AdxpCnt.class.getName())));
        }
        return addressMap;
    }

    private static String getAddressPart(Adxp part, Class<? extends Adxp> clazz, String defaultValue) {
        if (clazz == AdxpCnt.class && clazz.isInstance(part)) {
            return part.getCode();
        } else if (clazz.isInstance(part)) {
           return part.getValue();
        } else {
            return defaultValue;
        }
    }

}

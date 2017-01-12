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
*/
package gov.nih.nci.pa.pdq.dml;

import gov.nih.nci.pa.domain.PDQDisease;
import gov.nih.nci.pa.domain.PDQDiseaseAltername;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.pdq.PDQConstants;
import gov.nih.nci.pa.pdq.PDQException;
import gov.nih.nci.pa.pdq.jdbc.ExistingIds;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;

/**
 * @author Hugh Reinhart
 * @since 7/13/2009
 */
public class DiseaseScript extends BaseScript {
    private static final Logger LOG = Logger.getLogger(DiseaseScript.class);
    private static String fName = "disease.sql";
    private static DiseaseScript script = new DiseaseScript();
    private static Map<String, Long> codeMap = new HashMap<String, Long>();

    private class DisPar {
        String childCode;
        String parentCode;
        String parentType;
    }

    private static List<DisPar> parents = new ArrayList<DisPar>();

    public static DiseaseScript get() {
        return script;
    }

    public DiseaseScript() {
        try{
            new FileOutputStream(fName);
            out = new PrintStream(new BufferedOutputStream(new FileOutputStream(fName, true)), true, "UTF-8");
            out.println("UPDATE pdq_disease SET status_code = '" + ActiveInactivePendingCode.INACTIVE.getName() + "';");
            out.println("DELETE FROM pdq_disease_altername;");
            out.println("DELETE FROM pdq_disease_parent;");
          }
          catch (IOException iox)
          {
              LOG.error("Error creatinig script file'"+fName+".  IO Error:  " + iox.toString());
          }
    }
    public void add(PDQDisease dis, List<PDQDiseaseAltername> danList, String user) throws PDQException {
        if (StringUtils.isEmpty(dis.getPreferredName())) {
            LOG.error("Tried to create a pdq disease with no name.  ");
            System.exit(1);
        }

        StringBuffer sql = new StringBuffer();
        Long id;
        if (!ExistingIds.getDiseases().containsKey(dis.getDiseaseCode())) {
            id = ExistingIds.getNextDiseaseSqn();
            sql.append("INSERT INTO pdq_disease (identifier,disease_code,nt_term_identifier,preferred_name,menu_display_name,"
                    + "status_code,status_date_range_low,date_last_created) VALUES (" );
            sql.append(id);
            sql.append("," + fixString(dis.getDiseaseCode()));
            sql.append("," + fixString(dis.getNtTermIdentifier()));
            sql.append("," + fixString(dis.getPreferredName()));
            sql.append("," + fixString(dis.getDisplayName()));
            sql.append(",'" + ActiveInactivePendingCode.ACTIVE.getName() + "',now(),'" + PDQConstants.DATA_DUMP_DATE + "');");
        } else {
            id = ExistingIds.getDiseases().get(dis.getDiseaseCode()).longValue();
            sql.append("UPDATE pdq_disease SET nt_term_identifier=");
            sql.append(fixString(dis.getNtTermIdentifier()) + ",");
            sql.append("preferred_name=" + fixString(dis.getPreferredName()));
            sql.append(", menu_display_name=" + fixString(dis.getDisplayName()));
            sql.append(", status_code='" + ActiveInactivePendingCode.ACTIVE.getName());
            sql.append("', status_date_range_low=now(),date_last_updated='" + PDQConstants.DATA_DUMP_DATE);
            sql.append("' WHERE identifier=" + id + ";");
        }
        out.println(sql.toString());
        codeMap.put(dis.getDiseaseCode(), id);
        if (!danList.isEmpty()) {
            for (PDQDiseaseAltername dan : danList) {
                alternameAdd(dan, id);
            }
        }
    }
    public void addParent(String child, String parent, String type) {
        DisPar x = new DisPar();
        x.childCode = child;
        x.parentCode = parent;
        x.parentType = type;
        parents.add(x);
    }

    private void  alternameAdd(PDQDiseaseAltername dan, Long disId) {
        out.println("INSERT INTO pdq_disease_altername (alternate_name,disease_identifier,status_code,status_date_range_low) "
                + "VALUES (" + fixString(dan.getAlternateName()) + "," + disId + ",'" + ActiveInactiveCode.ACTIVE.getName() + "',now());");
    }

    private void parentsAdd() {
        for (DisPar dp : parents) {
            LOG.debug(dp.childCode + " - " + dp.parentCode + " - " + dp.parentType);
            if (codeMap.containsKey(dp.childCode) && codeMap.containsKey(dp.parentCode)) {
                out.println("INSERT INTO pdq_disease_parent (disease_identifier,parent_disease_identifier,parent_disease_code,status_code,status_date_range_low)"
                        + " VALUES (" + codeMap.get(dp.childCode) + "," + codeMap.get(dp.parentCode) + "," + fixString(dp.parentType) + ",'"
                        + ActiveInactiveCode.ACTIVE.getName() + "',now());");
            } else {

                LOG.warn("Bad parent child relationship ignored." + dp.childCode + " - " + dp.parentCode + " - " + dp.parentType);
            }
        }
    }

    public void close() throws PDQException {
        parentsAdd();
        out.println("UPDATE pdq_disease_altername SET date_last_created = '" + PDQConstants.DATA_DUMP_DATE + "';");
        out.println("UPDATE pdq_disease_parent SET date_last_created = '" + PDQConstants.DATA_DUMP_DATE + "';");
        out.println("DELETE FROM pdq_disease WHERE status_code = '" + ActiveInactivePendingCode.INACTIVE.getName()
                + "' AND identifier NOT IN (SELECT disease_identifier FROM study_disease);");
        out.close();
    }
}

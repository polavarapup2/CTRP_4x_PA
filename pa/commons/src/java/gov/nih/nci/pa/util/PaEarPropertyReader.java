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

import gov.nih.nci.pa.service.PAException;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Property File reader which can be extended for passing in the run time properties for PA.
 *
 * @author Harsha
 * @since 09/14/2008
 *
 */
public class PaEarPropertyReader {
    private static final Logger LOG = Logger.getLogger(PaEarPropertyReader.class);
    private static final String RESOURCE_NAME = "paear.properties";
    private static final Properties PROPS;
    private static final String DOC_UPLOAD_PATH = "doc.upload.path";
    private static final String PDQ_UPLOAD_PATH = "pdq.upload.path";
    private static final String ACCRUAL_BATCH_UPLOAD_PATH = "accrual.batch.upload.path";
    private static final String PO_SERVER_NAME = "po.server.name";
    private static final String PO_SERVER_PORT = "po.port.number";
    private static final String PO_SERVER_PRINCIPAL = "po.principal";
    private static final String PO_SERVER_CREDENTIALS = "po.credentials";
    private static final String PO_JMS_CLIENT_ID = "po.topic.clientId";
    private static final String PO_JMS_PRINCIPAL = "po.topic.userName";
    private static final String PO_JMS_CREDENTIALS = "po.topic.password";
    private static final String CSM_SUBMITTER_GROUP = "csm.submitter.group";
    private static final String ALLOWED_UPLOAD_FILE_TYPES = "allowed.uploadfile.types";
    private static final String BATCH_UPLOAD_PATH = "batch.upload.path";
    private static final String TOOLTIPS_PATH = "tooltips.path";    
    private static final String INVALID_DIRECTORY_ERROR_MSG = " is not a valid directory.";
    private static final String PA_HELP_URL = "wikiHelp.baseUrl.pa";
    private static final String REGISTRY_HELP_URL = "wikiHelp.baseUrl.registry";
    private static final String ACCRUAL_HELP_URL = "wikiHelp.baseUrl.accrual";
    private static final String NCI_LDAP_PREFIX = "nci.ldap.prefix";
    private static final String STATE_TRANSITION_DIAGRAM_URL = "wikiHelp.baseUrl.trialStatusRules";
    private static final String CTGOV_FTP_URL = "ctgov.ftp.url";

    private static final String NO_VALUE = "does not have a value in paear.properties";

    static {
        try {
            PROPS = new Properties();
            PROPS.load(PaEarPropertyReader.class.getClassLoader().getResourceAsStream(RESOURCE_NAME));
        } catch (Exception e) {
            LOG.error("Unable to read paear.properties", e);
            throw new IllegalStateException(e);
        }
    }

    /**
    *
    * @return tooltip folder path
    * @throws PAException e
    */
    public static String getTooltipsPath() throws PAException {
      return getDirPropTemplate(TOOLTIPS_PATH);
    }

    /**
     *
     * @return folder path
     * @throws PAException e
     */
    public static String getDocUploadPath() throws PAException {
        return getDirPropTemplate(DOC_UPLOAD_PATH);
    }

    /**
     *
     * @return folder path
     * @throws PAException e
     */
   public static String getPDQUploadPath() throws PAException {
       return getDirPropTemplate(PDQ_UPLOAD_PATH);
   }

   private static String getDirPropTemplate(String propName) throws PAException {
       String folderPath = PROPS.getProperty(propName);
       if (folderPath == null) {
           throw new PAException(ErrorCode.PA_SYS_001, propName
                   + NO_VALUE);
       }
       File f = new File(folderPath);
       if (!f.isDirectory()) {
           throw new PAException(ErrorCode.PA_SYS_001, folderPath + INVALID_DIRECTORY_ERROR_MSG);
       }
       return folderPath;
   }

    /**
     *
     * @return batch upload docs folder path
     * @throws PAException e
     */
    public static String getBatchUploadPath() throws PAException {
        return getDirPropTemplate(BATCH_UPLOAD_PATH);
    }

    /**
     * @return accrual batch upload docs folder path
     * @throws PAException e
     */
    public static String getAccrualBatchUploadPath() throws PAException {
        return getDirPropTemplate(ACCRUAL_BATCH_UPLOAD_PATH);
    }

    /**
     * Get the po lookup url.
     * @return String for the server info (server:port)
     * @throws PAException on error
     */
    public static String getLookUpServerInfo() throws PAException {
        return getPropTemplate(PO_SERVER_NAME) + ":" + getPropTemplate(PO_SERVER_PORT);
    }

    /**
     * @return String for the PO principal
     * @throws PAException exception
     */
    public static String getLookUpServerPoPrincipal() throws PAException {
        return getPropTemplate(PO_SERVER_PRINCIPAL);
    }

    /**
     * @return String for the PO credentials
     * @throws PAException exception
     */
    public static String getLookUpServerPoCredentials() throws PAException {
        return getPropTemplate(PO_SERVER_CREDENTIALS);
    }

    /**
     * @return String for the PO credentials
     * @throws PAException exception
     */
    public static String getLookUpServerPoJmsClientId() throws PAException {
        return getPropTemplate(PO_JMS_CLIENT_ID);
    }

    /**
     * @return String for the PO credentials
     * @throws PAException exception
     */
    public static String getLookUpServerPoJmsPricipal() throws PAException {
        return getPropTemplate(PO_JMS_PRINCIPAL);
    }

    /**
     * @return String for the PO credentials
     * @throws PAException exception
     */
    public static String getLookUpServerPoJmsCredentials() throws PAException {
        return getPropTemplate(PO_JMS_CREDENTIALS);
    }

    /**
     *
     * @return String for the CSM Trial Submitter Group
     * @throws PAException on error
     */
    public static String getCSMSubmitterGroup() throws PAException {
        return getPropTemplate(CSM_SUBMITTER_GROUP);
    }

    /**
     * @return a comma separated String of allowed upload file types
     * @throws PAException on error
     */
    public static String getAllowedUploadFileTypes() throws PAException {
        return getPropTemplate(ALLOWED_UPLOAD_FILE_TYPES);
    }

    
    /**
     * Returns the base URL of the wiki-based help for PA.
     * @return url
     * @throws PAException if the property is missing
     */
    public static String getPaHelpUrl() throws PAException {
        return getPropTemplate(PA_HELP_URL);
    }

    /**
     * Returns the base URL of the wiki-based help for Registry.
     * @return url
     * @throws PAException if the property is missing
     */
    public static String getRegistryHelpUrl() throws PAException {
        return getPropTemplate(REGISTRY_HELP_URL);
    }

    /**
     * Returns the base URL of the wiki-based help for Accrual.
     * @return url
     * @throws PAException if the property is missing
     */
    public static String getAccrualHelpUrl() throws PAException {
        return getPropTemplate(ACCRUAL_HELP_URL);
    }

    /**
     * Returns the NCI LDAP Prefix.
     * @return a DN.
     * @throws PAException if the property is missing.
     */
    public static String getNciLdapPrefix() throws PAException {
        return getPropTemplate(NCI_LDAP_PREFIX);
    }

    /**
     * Returns the STATE TRANSITION DIAGRAM URL.
     * @return a URL.
     * @throws PAException if the property is missing.
     */    
    public static String getStateTransitionDiagramUrl() throws PAException {
        return getPropTemplate(STATE_TRANSITION_DIAGRAM_URL);
    }
    
    /**
     * @return CTGOV_FTP_URL
     * @throws PAException PAException
     */
    public static String getCTGovFtpURL() throws PAException {
        return getPropTemplate(CTGOV_FTP_URL);
    }

    /**
     * @return the properties
     */
    public static Properties getProperties() {
        return PROPS;
    }

    private static String getPropTemplate(String propName) throws PAException {
        String value = PROPS.getProperty(propName);
        if (value == null) {
            throw new PAException(ErrorCode.PA_SYS_001,
                    propName + NO_VALUE);
        }
        return value;
    }

}

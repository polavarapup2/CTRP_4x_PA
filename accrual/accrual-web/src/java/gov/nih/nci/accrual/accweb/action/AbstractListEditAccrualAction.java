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
package gov.nih.nci.accrual.accweb.action;

import gov.nih.nci.accrual.accweb.util.AccrualConstants;
import gov.nih.nci.pa.service.PAException;

import org.apache.struts2.ServletActionContext;

/**
 * @author Hugh Reinhart
 * @since Oct 19, 2009
 * @param <DTO> class for dto's displayed in list
 */
public abstract class AbstractListEditAccrualAction<DTO> extends AbstractListAccrualAction<DTO> {
    private static final long serialVersionUID = -8797184349026635679L;

    /** String value for currentAction property when doing a create. */
    private static final String CA_CREATE = "create";
    /** String value for currentAction property when doing a create. */
    private static final String CA_RETRIEVE = "retrieve";
    /** String value for currentAction property when doing a create. */
    private static final String CA_UPDATE = "update";
    /** Action result returned to display the detail page. */
    private static final String AR_DETAIL = "detail";

    /** Bean to store current action. */
    private String currentAction;
    /** Bean to store row id selected from list view. */
    private String selectedRowIdentifier;

    /**
     * Method called to begin create workflow.
     * @return action result to display detail page
     */
    public String create() {
        setCurrentAction(CA_CREATE);
        return AR_DETAIL;
    }
    /**
     * Method called to begin retrieve workflow.
     * @return action result to display detail page
     */
    public String retrieve() {
        setCurrentAction(CA_RETRIEVE);
        return AR_DETAIL;
    }
    /**
     * Method called to begin update workflow.
     * @return action result to display detail page
     */
    public String update() {
        setCurrentAction(CA_UPDATE);
        return AR_DETAIL;
    }

    /**
     * Add a new record to database (needs to be overridden).  Returns to list jsp.
     * @return result
     * @throws PAException exception
     */
    public String add() throws PAException {
        loadDisplayList();
        ServletActionContext.getRequest().setAttribute(AccrualConstants.SUCCESS_MESSAGE,
                AccrualConstants.CREATE_MESSAGE);
        return execute();
    }

    /**
     * Update a record in the database (needs to be overridden).  Returns to list jsp.
     * @return result
     * @throws PAException exception
     */
    public String edit() throws PAException {
        loadDisplayList();
        ServletActionContext.getRequest().setAttribute(AccrualConstants.SUCCESS_MESSAGE,
                AccrualConstants.UPDATE_MESSAGE);
        return execute();
    }

    /**
     * Delete a record from database (needs to be overridden). Returns to list jsp.
     * @return action result
     * @throws PAException exception
     */
    public String delete() throws PAException {
        loadDisplayList();
        ServletActionContext.getRequest().setAttribute(AccrualConstants.SUCCESS_MESSAGE,
                AccrualConstants.DELETE_MESSAGE);
        return execute();
    }

    /**
     * @return the currentAction
     */
    public String getCurrentAction() {
        return currentAction;
    }
    /**
     * @param currentAction the currentAction to set
     */
    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
    }
    /**
     * @return the selectedRowIdentifier
     */
    public String getSelectedRowIdentifier() {
        return selectedRowIdentifier;
    }
    /**
     * @param selectedRowIdentifier the selectedRowIdentifier to set
     */
    public void setSelectedRowIdentifier(String selectedRowIdentifier) {
        this.selectedRowIdentifier = selectedRowIdentifier;
    }
}

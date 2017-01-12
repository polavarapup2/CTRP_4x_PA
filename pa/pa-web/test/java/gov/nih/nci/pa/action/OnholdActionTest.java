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
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyOnholdServiceLocal;
import gov.nih.nci.pa.util.PAUtil;

import java.io.InputStream;
import java.lang.reflect.Field;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.dispatcher.StreamResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

/**
 * @author Hugh Reinhart
 */
public class OnholdActionTest extends AbstractPaActionTest {
    private static OnholdAction act;

    @Before
    public void prepare() throws Exception {
        act = new OnholdAction();
        act.prepare();
      
    }
    
    @Test
    public void listTest() throws Exception {
        // select from main menu
        assertEquals(AbstractListEditAction.AR_LIST, act.execute());
        assertFalse(act.hasErrors());
    }
    
    @Test
    public void addTest() throws Exception {
        // select from main menu
        assertEquals(AbstractListEditAction.AR_LIST, act.execute());
        int initialSize = act.getOnholdList().size();
        
        // click add button
        assertEquals(AbstractListEditAction.AR_EDIT, act.create());
        assertFalse(act.hasErrors());
        assertEquals(AbstractListEditAction.CA_CREATE, act.getCurrentAction());
        assertNull(act.getOnhold().getIdentifier());
        
        // fill in form
        act.getOnhold().setDateLow(PAUtil.today());
        act.getOnhold().setReasonCode(OnholdReasonCode.INVALID_GRANT.getCode());
        act.getOnhold().setReasonText("Grant paperwork missing.");
        
        // test exception
        act.getOnhold().setIdentifier("1");
        assertEquals(AbstractListEditAction.AR_EDIT, act.add());
        assertTrue(act.hasActionErrors());
        act.clearErrorsAndMessages();

        // test field exceptions
        act.getOnhold().setIdentifier(null);
        act.getOnhold().setReasonCode(null);
        assertEquals(AbstractListEditAction.AR_EDIT, act.add());
        assertTrue(act.hasFieldErrors());
        act.clearErrorsAndMessages();
        
        act.getOnhold().setReasonCode(OnholdReasonCode.OTHER.getCode());
        act.getOnhold().setDateLow(null);
        assertEquals(AbstractListEditAction.AR_EDIT, act.add());
        assertTrue(act.hasFieldErrors());
        act.clearErrorsAndMessages();
        
        act.getOnhold().setDateLow(PAUtil.today());
        act.getOnhold().setDateHigh("1/1/2000");
        assertEquals(AbstractListEditAction.AR_EDIT, act.add());
        assertTrue(act.hasFieldErrors());
        act.clearErrorsAndMessages();
        
        // add
        act.getOnhold().setDateHigh(null);
        assertEquals(AbstractListEditAction.AR_LIST, act.add());
        assertFalse(act.hasErrors());
        assertEquals(initialSize + 1, act.getOnholdList().size());
    }
    
    @Test
    public void updateTest() throws Exception {
        // select from main menu
        assertEquals(AbstractListEditAction.AR_LIST, act.execute());
        assertTrue(1 <= act.getOnholdList().size());
        int initialSize = act.getOnholdList().size();
        String id = act.getOnholdList().get(0).getIdentifier();

        // click edit icon
        act.setSelectedRowIdentifier(id);
        assertEquals(AbstractListEditAction.AR_EDIT, act.edit());
        assertFalse(act.hasErrors());
        assertEquals(AbstractListEditAction.CA_EDIT, act.getCurrentAction());
        
        // test exception
        act.getOnhold().setIdentifier(null);
        assertEquals(AbstractListEditAction.AR_EDIT, act.update());
        assertTrue(act.hasActionErrors());
        act.clearErrorsAndMessages();
        
        // test field exception
        act.getOnhold().setIdentifier(id);
        act.getOnhold().setDateHigh("1/1/1900");
        assertEquals(AbstractListEditAction.AR_EDIT, act.update());
        assertTrue(act.hasFieldErrors());
        act.clearErrorsAndMessages();
        
        // update
        act.getOnhold().setDateHigh(PAUtil.today());
        assertEquals(AbstractListEditAction.AR_LIST, act.update());
        assertFalse(act.hasErrors());
        assertEquals(initialSize, act.getOnholdList().size());
    }
    
    @Test(expected=UnsupportedOperationException.class)
    public void testDeleteObject() throws PAException {
        act.deleteObject(1l);
    }
    
    @Test
    public void testSave() throws PAException {
        assertEquals("edit", act.save());
    }
    
    @Test
    public void getOnHoldReasonCodeTest() throws Exception {
        
        StudyOnholdServiceLocal studyOnholdService;
        studyOnholdService = mock(StudyOnholdServiceLocal.class);
        when(studyOnholdService.getReasonCategoryValue(any(String.class))).thenReturn("CTRP");
        act.setStudyOnholdService(studyOnholdService);
        
        // select from main menu
        assertEquals(AbstractListEditAction.AR_LIST, act.execute());
        // click add button
        assertEquals(AbstractListEditAction.AR_EDIT, act.create());
        act.getOnhold().setReasonCode(OnholdReasonCode.INVALID_GRANT.getCode());
        
       StreamResult result= act.getOnHoldReasonCode();
               
       final Field field = StreamResult.class.getDeclaredField("inputStream");
       ReflectionUtils.makeAccessible(field);
       InputStream is = (InputStream) field.get(result);
       String value = IOUtils.toString(is);
        
       assertTrue(value.equals("CTRP"));
       
       
    }
}

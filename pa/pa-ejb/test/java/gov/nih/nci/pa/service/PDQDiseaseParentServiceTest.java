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
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.PDQDisease;
import gov.nih.nci.pa.domain.PDQDiseaseParent;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseParentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class PDQDiseaseParentServiceTest extends AbstractHibernateTestCase {
    private final PDQDiseaseParentServiceBean bean = new PDQDiseaseParentServiceBean();
    private final PDQDiseaseParentServiceRemote remote = bean;
    private final PDQDiseaseBeanLocal diseaseBean = new PDQDiseaseBeanLocal();
    private Ii dIi;

    @Before
    public void setUp() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();
        dIi = IiConverter.convertToIi(TestSchema.pdqDiseaseIds.get(0));
        bean.setDiseaseService(diseaseBean);
        
     }

    private void compareDataAttributes(PDQDiseaseParent bo1, PDQDiseaseParent bo2) {
        assertEquals(bo1.getParentDiseaseCode(), bo2.getParentDiseaseCode());
        assertEquals(bo1.getStatusCode(), bo2.getStatusCode());
        assertEquals(bo1.getStatusDateRangeLow(), bo2.getStatusDateRangeLow());
    }

    @Test
    public void getTest() throws Exception {
        List<PDQDiseaseParentDTO> dtoList = bean.getByChildDisease(dIi);
        assertTrue(dtoList.size() > 0);
        Ii ii = dtoList.get(0).getIdentifier();
        assertFalse(ISOUtil.isIiNull(ii));
        PDQDiseaseParentDTO resultDto = bean.get(ii);
        assertFalse(ISOUtil.isIiNull(resultDto.getIdentifier()));
    }

    @Test
    public void getTreeTest() throws Exception {
        Long childId = IiConverter.convertToLong(dIi);
        List<PDQDiseaseParentDTO> dtoList = bean.getByChildDisease(dIi);
        assertTrue(dtoList.size() > 0);
        Ii parentDiseaseIi = dtoList.get(0).getParentDiseaseIdentifier();
        List<PDQDiseaseParentDTO> dtoListChildren = bean.getByParentDisease(parentDiseaseIi);
        List<Long> childIdList = new ArrayList<Long>();
        for (PDQDiseaseParentDTO d : dtoListChildren) {
            childIdList.add(IiConverter.convertToLong(d.getDiseaseIdentifier()));
        }
        assertTrue(childIdList.contains(childId));

        dtoList = bean.getByChildDisease(new Ii[] {dIi});
        assertTrue(dtoList.size() > 0);
        parentDiseaseIi = dtoList.get(0).getParentDiseaseIdentifier();
        dtoListChildren = bean.getByParentDisease(parentDiseaseIi);
        childIdList = new ArrayList<Long>();
        for (PDQDiseaseParentDTO d : dtoListChildren) {
            childIdList.add(IiConverter.convertToLong(d.getDiseaseIdentifier()));
        }
        assertTrue(childIdList.contains(childId));
    }

    @Test
    public void createTest() throws Exception {
        PDQDiseaseDTO tn = diseaseBean.create(diseaseBean.convertFromDomainToDto(TestSchema.createPdqDisease("toenail cancer")));
        PDQDisease toeNail = new PDQDisease();
        toeNail.setId(IiConverter.convertToLong(tn.getIdentifier()));
        PDQDisease toe = diseaseBean.convertFromDtoToDomain(diseaseBean.get(dIi));

        PDQDiseaseParent bo = TestSchema.createPdqDiseaseParent(toeNail, toe);
        assertNull(bo.getId());
        PDQDiseaseParentDTO resultDto = remote.create(bean.convertFromDomainToDto(bo));
        PDQDiseaseParent resultBo = bean.convertFromDtoToDomain(resultDto);
        compareDataAttributes(bo, resultBo);
        assertNotNull(resultBo.getId());
    }

    @Test
    public void updateTest() throws Exception {
        List<PDQDiseaseParentDTO> dtoList = bean.getByChildDisease(dIi);
        assertTrue(dtoList.size() > 0);
        PDQDiseaseParentDTO dto = dtoList.get(0);
        PDQDiseaseParent bo = bean.convertFromDtoToDomain(dto);
        assertFalse(ActiveInactiveCode.INACTIVE.equals(bo.getStatusCode()));
        bo.setStatusCode(ActiveInactiveCode.INACTIVE);

        PDQDiseaseParentDTO resultDto = remote.update(bean.convertFromDomainToDto(bo));
        PDQDiseaseParent resultBo = bean.convertFromDtoToDomain(resultDto);
        compareDataAttributes(bo, resultBo);
        assertEquals(bo.getId(), resultBo.getId());
    }

    @Test
    public void deleteTest() throws Exception {
        List<PDQDiseaseParentDTO> dtoList = bean.getByChildDisease(dIi);
        assertTrue(dtoList.size() > 0);
        int oldSize = dtoList.size();
        Ii ii = dtoList.get(0).getIdentifier();
        remote.delete(ii);
        dtoList = bean.getByChildDisease(dIi);
        assertEquals(oldSize - 1, dtoList.size());
    }
    
   
    
    @Test
    public void syncTest() throws Exception {
    	
        List<PDQDiseaseParentDTO> parents = bean.getByChildDisease(dIi);
        List<PDQDiseaseParentDTO>childs = bean.getByParentDisease(dIi);
        
        PDQDiseaseDTO tn = diseaseBean.create(diseaseBean.convertFromDomainToDto(TestSchema.createPdqDisease("toenail cancer")));
        PDQDisease pdqDisease = diseaseBean.convertFromDtoToDomain(tn);
        pdqDisease.setDisplayName("new name");
        tn = diseaseBean.convertFromDomainToDto(pdqDisease);
        
        List<PDQDiseaseParentDTO> parentstoAdd = new ArrayList<PDQDiseaseParentDTO>();
        List<PDQDiseaseParentDTO> childsToAdd = new ArrayList<PDQDiseaseParentDTO>();
        
        PDQDiseaseDTO parentBean = diseaseBean.create(diseaseBean.convertFromDomainToDto(TestSchema.createPdqDisease("parent bean"))); 
        PDQDiseaseDTO childBean = diseaseBean.create(diseaseBean.convertFromDomainToDto(TestSchema.createPdqDisease("parent bean")));

        //create a new parent that needs to be added
        //and old parent got successfully deleted
        PDQDiseaseParentDTO p = new PDQDiseaseParentDTO();
        p.setParentDiseaseCode(StConverter
                .convertToSt("ISA"));
        p.setParentDiseaseIdentifier(parentBean.getIdentifier());
        p.setStatusCode(CdConverter
                .convertToCd(ActiveInactivePendingCode.ACTIVE));
        p.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil
                .dateStringToTimestamp(PAUtil.today())));
        p.setDiseaseIdentifier(tn.getIdentifier());
      
        parentstoAdd.add(p);
        
        //create a new child
        
        PDQDiseaseParentDTO c = new PDQDiseaseParentDTO();
        c.setParentDiseaseCode(StConverter
                .convertToSt("ISA"));
        c.setParentDiseaseIdentifier(tn.getIdentifier());
        c.setStatusCode(CdConverter
                .convertToCd(ActiveInactivePendingCode.ACTIVE));
        c.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil
                .dateStringToTimestamp(PAUtil.today())));
        c.setDiseaseIdentifier(childBean.getIdentifier());
        
        childsToAdd.add(c);
       
    	bean.syncDisease(tn, parents, childs, parentstoAdd, childsToAdd);
    	
    	//check if parent got successfully added
    	
        List<PDQDiseaseParentDTO> newParents = bean.getByChildDisease(tn.getIdentifier());
        assertTrue(newParents.size()>0);
        PDQDiseaseParentDTO  diseaseParentDTO = newParents.get(0);
        assertTrue(diseaseParentDTO.getDiseaseIdentifier().equals(tn.getIdentifier()));
        assertTrue(diseaseParentDTO.getParentDiseaseIdentifier().equals(parentBean.getIdentifier()));
        
        //check of child got successfully added
    	
        List<PDQDiseaseParentDTO> newChilds = bean.getByParentDisease(tn.getIdentifier());
        assertTrue(newChilds.size()>0);
        PDQDiseaseParentDTO diseaseParentDTO2 = newChilds.get(0);
        assertTrue(diseaseParentDTO2.getDiseaseIdentifier().equals(childBean.getIdentifier()));
        assertTrue(diseaseParentDTO2.getParentDiseaseIdentifier().equals(tn.getIdentifier()));
    	pdqDisease = diseaseBean.convertFromDtoToDomain(tn);
    	
    	assertTrue(pdqDisease.getDisplayName().equals("new name"));
    	
    }
}

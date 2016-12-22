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
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.AbstractEjbTestCase;
import gov.nih.nci.pa.util.TestSchema;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 */
public class ArmServiceTest extends AbstractEjbTestCase {
    
    private ArmBeanLocal remoteEjb;
    private PlannedActivityBeanLocal paRemoteEjb;
    private Ii ii;
    private Ii spIi;
    private Ii intIi;

    @Before
    public void init() throws Exception {
        TestSchema.primeData();
        remoteEjb = (ArmBeanLocal) getEjbBean(ArmBeanLocal.class);
        paRemoteEjb = (PlannedActivityBeanLocal) getEjbBean(PlannedActivityBeanLocal.class);
    
        ii = IiConverter.convertToIi(TestSchema.armIds.get(0));
        spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds
                .get(0));
        intIi = IiConverter.convertToIi(TestSchema.plannedActivityIds.get(0));
    }

    @Test
    public void getTest() throws Exception {
        ArmDTO dto = remoteEjb.get(ii);
        assertTrue("ARM 01".equals(StConverter.convertToString(dto.getName())));
        assertNotNull(dto.getInterventions());
        Collection<Ii> interventionIis = dto.getInterventions().getItem();
        assertTrue(0 < interventionIis.size());
        PlannedActivityDTO paDto = paRemoteEjb.get((Ii) interventionIis
                .toArray()[0]);
        assertEquals(
                IiConverter.convertToLong(paDto.getStudyProtocolIdentifier()),
                IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
    }

    @Test
    public void getByStudyProtocolTest() throws Exception {
        List<ArmDTO> dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals(1, dtoList.size());
    }

    @Test
    public void createTestWithInterventions() throws Exception {
        List<ArmDTO> dtoList = remoteEjb.getByStudyProtocol(spIi);
        int originalCount = dtoList.size();
        ArmDTO dto = new ArmDTO();
        dto.setStudyProtocolIdentifier(spIi);
        dto.setDescriptionText(StConverter.convertToSt("description of arm"));
        dto.setIdentifier(new Ii());
        Set<Ii> intSet = new HashSet<Ii>();
        intSet.add(intIi);
        DSet<Ii> intDSet = new DSet<Ii>();
        intDSet.setItem(intSet);
        dto.setInterventions(intDSet);
        dto.setName(StConverter.convertToSt("name"));
        dto.setTypeCode(CdConverter.convertToCd(ArmTypeCode.ACTIVE_COMPARATOR));
        ArmDTO resultDto = remoteEjb.create(dto);
        dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals(originalCount + 1, dtoList.size());
        Set<Ii> resultIntSet = resultDto.getInterventions().getItem();
        assertEquals(1, resultIntSet.size());
        assertEquals(IiConverter.convertToLong(intIi),
                IiConverter.convertToLong((Ii) resultIntSet.toArray()[0]));
    }

    @Test
    public void createTestNoInterventions() throws Exception {
        List<ArmDTO> dtoList = remoteEjb.getByStudyProtocol(spIi);
        int originalCount = dtoList.size();
        ArmDTO dto = new ArmDTO();
        dto.setStudyProtocolIdentifier(spIi);
        dto.setDescriptionText(StConverter.convertToSt("description of arm"));
        dto.setIdentifier(new Ii());
        dto.setInterventions(new DSet<Ii>());
        dto.setName(StConverter.convertToSt("name"));
        dto.setTypeCode(CdConverter.convertToCd(ArmTypeCode.ACTIVE_COMPARATOR));
        remoteEjb.create(dto);
        dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals(originalCount + 1, dtoList.size());
    }

    @Test
    public void updateTest() throws Exception {
        ArmDTO dto = remoteEjb.get(ii);
        String oldName = StConverter.convertToString(dto.getName());
        String newName = "new name";
        assertFalse(newName.equals(oldName));
        dto.setName(StConverter.convertToSt(newName));
        dto.setTypeCode(CdConverter.convertToCd(ArmTypeCode.EXPERIMENTAL));
        remoteEjb.update(dto);
        ArmDTO newDto = remoteEjb.get(ii);
        assertEquals(newName, StConverter.convertToString(newDto.getName()));
    }

    @Test
    public void deleteTest() throws Exception {
        List<ArmDTO> dtoList = remoteEjb.getByStudyProtocol(spIi);
        int originalCount = dtoList.size();
        assertTrue(originalCount > 0);
        remoteEjb.delete(ii);
        dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals(originalCount - 1, dtoList.size());
    }

    @Test
    public void getByPlannedActivityTest() throws Exception {
        Ii paIi = IiConverter.convertToIi(TestSchema.plannedActivityIds.get(0));
        List<ArmDTO> dtoList = remoteEjb.getByPlannedActivity(paIi);
        if (dtoList.size() > 0) {
            for (ArmDTO dto : dtoList) {
                dto.setInterventions(null);
                remoteEjb.update(dto);
            }
            dtoList = remoteEjb.getByPlannedActivity(paIi);
        }
        assertEquals(0, dtoList.size());

        ArmDTO dto = remoteEjb.get(ii);
        DSet<Ii> paDSet = dto.getInterventions();
        Set<Ii> paSet = paDSet.getItem();
        paSet.add(paIi);
        paDSet.setItem(paSet);
        dto.setInterventions(paDSet);
        remoteEjb.update(dto);

        dtoList = remoteEjb.getByPlannedActivity(paIi);
        assertEquals(1, dtoList.size());
    }

    @Test
    public void iiRootTest() throws Exception {
        ArmDTO dto = remoteEjb.get(ii);
        assertEquals(dto.getIdentifier().getRoot(), IiConverter.ARM_ROOT);
        assertTrue(StringUtils.isNotEmpty(dto.getIdentifier()
                .getIdentifierName()));
        assertEquals(dto.getStudyProtocolIdentifier().getRoot(),
                IiConverter.STUDY_PROTOCOL_ROOT);
    }

    @Test
    public void testDuplicateArmRule() throws PAException {
        ArmDTO dto = remoteEjb.get(ii);
        dto.setIdentifier(null);
        dto.setName(StConverter.convertToSt("new ARM"));
        try {
            remoteEjb.create(dto);
            fail("Duplicates Arms are not allowed.");
        } catch (PAException e) {
            assertEquals("Duplicate Arms are not allowed.", e.getMessage());
        }
        dto = new ArmDTO();
        dto.setName(StConverter.convertToSt("ARM 01"));
        dto.setTypeCode(CdConverter.convertToCd(ArmTypeCode.ACTIVE_COMPARATOR));
        dto.setStudyProtocolIdentifier(spIi);
        try {
            remoteEjb.create(dto);
            fail("Duplicates Arms are not allowed.");
        } catch (PAException e) {
            assertEquals("Duplicate Arms are not allowed.", e.getMessage());
        }
        dto = new ArmDTO();
        dto.setName(StConverter.convertToSt("ARM Name"));
        dto.setTypeCode(CdConverter.convertToCd(ArmTypeCode.ACTIVE_COMPARATOR));
        dto.setStudyProtocolIdentifier(spIi);
        dto.setInterventions(DSetConverter
                .convertIiSetToDset(new HashSet<Ii>()));
        remoteEjb.create(dto);
    }

    @Test
    public void testCopy() throws PAException {
        Ii toStudyProtocolIi = TestSchema.createAmendStudyProtocol();
        Map<Ii, Ii> map = remoteEjb.copy(spIi, toStudyProtocolIi);
        assertNotNull(map);
        assertTrue(map.size() == 1);
        Ii fromIi = map.keySet().iterator().next();
        Ii toIi = map.get(fromIi);
        ArmDTO fromDto = remoteEjb.get(fromIi);
        ArmDTO toDto = remoteEjb.get(toIi);
        assertNotNull(fromDto.getInterventions().getItem());
        assertNotNull(toDto.getInterventions().getItem());

        Ii fromPAIi = fromDto.getInterventions().getItem().iterator().next();
        Ii toPAIi = toDto.getInterventions().getItem().iterator().next();

        PlannedActivityDTO fromPADTO = paRemoteEjb.get(fromPAIi);
        PlannedActivityDTO toPADTO = paRemoteEjb.get(toPAIi);

        assertEquals(fromPADTO.getInterventionIdentifier(),
                toPADTO.getInterventionIdentifier());
        assertNotSame(fromPADTO.getIdentifier(), toPADTO.getIdentifier());
    }
}

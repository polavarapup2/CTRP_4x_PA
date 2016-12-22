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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.pa.domain.PlannedSubstanceAdministration;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.enums.UnitsCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.PlannedProcedureDTO;
import gov.nih.nci.pa.iso.dto.PlannedSubstanceAdministrationDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.TestSchema;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class PlannedActivityServiceTest extends AbstractHibernateTestCase {

    private final PlannedActivityBeanLocal remoteBean = new PlannedActivityBeanLocal();
    private final PlannedActivityServiceLocal remoteEjb = remoteBean;
    private final ArmServiceLocal remoteArmEjb = new ArmServiceBean();
    private final InterventionServiceLocal remoteIntervention = new InterventionBeanLocal();

    private Ii ii;
    private Ii spIi;
    private Ii armIi;

    @Before
    public void setUp() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();
        ii = IiConverter.convertToIi(TestSchema.plannedActivityIds.get(0));
        spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
        armIi = IiConverter.convertToIi(TestSchema.armIds.get(0));
        remoteBean.setInterventionSrv(remoteIntervention);
    }

    @Test
    public void getTest() throws Exception {
        PlannedActivityDTO dto = remoteEjb.get(ii);
        assertEquals(TestSchema.interventionIds.get(0), IiConverter.convertToLong(dto.getInterventionIdentifier()));
    }

    @Test
    public void getByStudyProtocolTest() throws Exception {
        List<PlannedActivityDTO> dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals(6, dtoList.size());
    }

    @Test
    public void getByArmTest() throws Exception {
        List<PlannedActivityDTO> dtoList = remoteEjb.getByArm(armIi);
        assertEquals(1, dtoList.size());
        dtoList = remoteEjb.getByArm(null);
        assertTrue(dtoList.isEmpty());
    }

    @Test
    public void createTest() throws Exception {
        List<PlannedActivityDTO> dtoList = remoteEjb.getByStudyProtocol(spIi);
        int originalCount = dtoList.size();
        PlannedActivityDTO dto = new PlannedActivityDTO();
        dto.setStudyProtocolIdentifier(spIi);
        dto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.INTERVENTION));
        dto.setInterventionIdentifier(null);
        dto.setLeadProductIndicator(null);
        dto.setSubcategoryCode(CdConverter.convertToCd(ActivitySubcategoryCode.BEHAVIORAL));
        try {
            remoteEjb.create(dto);
            fail("Test should have failed for null in interventionIdentifier.  ");
        } catch (PAException e) {
            // expected behavior, failure for not logging user
        }
        dto.setInterventionIdentifier(IiConverter.convertToIi(TestSchema.interventionIds.get(0)));
        remoteEjb.create(dto);
        dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals(originalCount + 1, dtoList.size());
    }

    @Test
    public void updateTest() throws Exception {
        PlannedActivityDTO dto = remoteEjb.get(ii);
        assertFalse(ActivitySubcategoryCode.DRUG.equals(ActivitySubcategoryCode.getByCode(CdConverter.convertCdToString(dto.getSubcategoryCode()))));
        dto.setSubcategoryCode(CdConverter.convertToCd(ActivitySubcategoryCode.DRUG));
        PlannedActivityDTO resultDto = remoteEjb.update(dto);
        assertTrue(ActivitySubcategoryCode.DRUG.equals(ActivitySubcategoryCode.getByCode(CdConverter.convertCdToString(resultDto.getSubcategoryCode()))));
    }

    @Test
    public void deleteTest() throws Exception {
        List<PlannedActivityDTO> dtoList = remoteEjb.getByStudyProtocol(spIi);
        int originalCount = dtoList.size();
        for (Long armId : TestSchema.armIds) {
            ArmDTO arm = remoteArmEjb.get(IiConverter.convertToIi(armId));
            arm.setInterventions(null);
            remoteArmEjb.update(arm);
        }
        remoteEjb.delete(ii);
        dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals(originalCount - 1, dtoList.size());
    }
    
    @Test
    public void reorderTest() throws Exception {
        List<PlannedActivityDTO> dtoList = remoteEjb.getByStudyProtocol(spIi);
        int originalCount = dtoList.size();
        
        PlannedActivityDTO dto1 = new PlannedActivityDTO();
        dto1.setStudyProtocolIdentifier(spIi);
        dto1.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.INTERVENTION));
        dto1.setInterventionIdentifier(null);
        dto1.setLeadProductIndicator(null);
        dto1.setSubcategoryCode(CdConverter.convertToCd(ActivitySubcategoryCode.BEHAVIORAL));
        dto1.setInterventionIdentifier(IiConverter.convertToIi(TestSchema.interventionIds.get(0)));
        dto1.setDisplayOrder(IntConverter.convertToInt(2));
        dto1 = remoteEjb.create(dto1);
        
        PlannedActivityDTO dto2 = new PlannedActivityDTO();
        dto2.setStudyProtocolIdentifier(spIi);
        dto2.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.INTERVENTION));
        dto2.setInterventionIdentifier(null);
        dto2.setLeadProductIndicator(null);
        dto2.setSubcategoryCode(CdConverter.convertToCd(ActivitySubcategoryCode.BIOLOGICAL_VACCINE));
        dto2.setInterventionIdentifier(IiConverter.convertToIi(TestSchema.interventionIds.get(0)));
        dto2.setDisplayOrder(IntConverter.convertToInt(0));
        dto2 = remoteEjb.create(dto2);
        
        dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals(originalCount + 2, dtoList.size());
        assertEquals(dto2.getIdentifier().getExtension(),
                dtoList.get(dtoList.size() - 2).getIdentifier().getExtension());
        assertEquals(dto1.getIdentifier().getExtension(),
                dtoList.get(dtoList.size() - 1).getIdentifier().getExtension());

        remoteEjb.reorderInterventions(
                spIi,
                Arrays.asList(new String[] {
                        dto1.getIdentifier().getExtension(),
                        dto2.getIdentifier().getExtension() }));

        dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals(originalCount + 2, dtoList.size());
        assertEquals(dto1.getIdentifier().getExtension(),
                dtoList.get(dtoList.size() - 2).getIdentifier().getExtension());
        assertEquals(dto2.getIdentifier().getExtension(),
                dtoList.get(dtoList.size() - 1).getIdentifier().getExtension());
        
    }
    

    @Test
    public void getPlannedEligibilityCriterion() throws Exception {
        List<PlannedEligibilityCriterionDTO> statusList = remoteEjb.getPlannedEligibilityCriterionByStudyProtocol(spIi);
        assertEquals(1, statusList.size());
        assertEquals(0, remoteEjb.getMaxDisplayOrderValue(spIi));

        PlannedEligibilityCriterionDTO dto = remoteEjb.getPlannedEligibilityCriterion(statusList.get(0).getIdentifier());
        assertEquals(IiConverter.convertToLong(statusList.get(0).getIdentifier()),
                     (IiConverter.convertToLong(dto.getIdentifier())));
        PlannedEligibilityCriterionDTO dto2 = null;

        dto2 = new PlannedEligibilityCriterionDTO();
        dto2 = remoteEjb.updatePlannedEligibilityCriterion(dto);
        assertEquals(dto.getCriterionName().getValue(), dto2.getCriterionName().getValue());
        
        remoteEjb.deletePlannedEligibilityCriterion(dto.getIdentifier());
        
        statusList = remoteEjb.getPlannedEligibilityCriterionByStudyProtocol(null);
        assertTrue(statusList.isEmpty());
        
        dto = remoteEjb.getPlannedEligibilityCriterion(null);
        assertTrue(dto == null);
        
        try {
            dto = remoteEjb.getPlannedEligibilityCriterion(IiConverter.convertToIi(1L));
            fail("Object not found using get() for id = 1.");
        }catch (Exception e) {
            //expected behavior
        }
        
    }
    
    @Test
    public void testUpdatePlannedEligibilityCriterionError() throws Exception {
        PlannedEligibilityCriterionDTO dto = new PlannedEligibilityCriterionDTO();
        dto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.ELIGIBILITY_CRITERION));
        try {
            PlannedEligibilityCriterionDTO dto2 = remoteEjb.updatePlannedEligibilityCriterion(dto);
            fail("Cannot call updatePlannedEligibilityCriterion with a null identifier");
        } catch (Exception e) {
            // expected behavior
        }
        try {
            remoteEjb.deletePlannedEligibilityCriterion(dto.getIdentifier());
            fail("Check the Ii value; found null.");
        } catch (Exception e) {
            // expected behavior
        }
    }
    
    @Test
    public void testCreatePlannedEligibilityCriterion() throws Exception {
        PlannedEligibilityCriterionDTO dto = new PlannedEligibilityCriterionDTO();
        dto.setStudyProtocolIdentifier(spIi);
        dto.setCriterionName(StConverter.convertToSt("WHC"));
        dto.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
        dto.setOperator(StConverter.convertToSt(">"));
        Pq pqLow = new Pq();
        pqLow.setValue(new BigDecimal("80"));
        pqLow.setUnit(UnitsCode.YEARS.getCode());
        Pq pqHigh = new Pq();
        pqHigh.setValue(new BigDecimal("90"));
        pqHigh.setUnit(UnitsCode.YEARS.getCode());
        Ivl<Pq> ivlPq = new Ivl<Pq>();
        ivlPq.setHigh(pqHigh);
        ivlPq.setLow(pqLow);
        dto.setValue(ivlPq);
        assertEquals(dto.getStudyProtocolIdentifier(), spIi);
    }

    @Test
    public void testCreatePlannedEligibilityCriterionError() throws Exception {
        PlannedEligibilityCriterionDTO dto = new PlannedEligibilityCriterionDTO();
        dto.setCriterionName(StConverter.convertToSt("WHC"));
        dto.setInclusionIndicator(BlConverter.convertToBl(Boolean.TRUE));
        dto.setOperator(StConverter.convertToSt(">"));
        dto.setIdentifier(IiConverter.convertToIi(1L));
        try {
            remoteEjb.createPlannedEligibilityCriterion(dto);
            fail("Cannot call createPlannedEligibilityCriterion with a non null identifier");
        } catch (Exception e) {
            // expected behavior
        }
        dto.setIdentifier(null);
        try {
            remoteEjb.createPlannedEligibilityCriterion(dto);
            fail("Cannot call createPlannedEligibilityCriterion with a null protocol identifier");
        } catch (Exception e) {
            // expected behavior
        }
    }
    @Test
    public void oneLeadDrugPerStudyTest() throws Exception {
        PlannedActivityDTO dto = remoteEjb.get(ii);
        assertFalse(ActivitySubcategoryCode.DRUG.equals(ActivitySubcategoryCode.getByCode(CdConverter.convertCdToString(dto.getSubcategoryCode()))));
        dto.setSubcategoryCode(CdConverter.convertToCd(ActivitySubcategoryCode.DRUG));
        PlannedActivityDTO resultDto = remoteEjb.update(dto);
        assertTrue(ActivitySubcategoryCode.DRUG.equals(ActivitySubcategoryCode.getByCode(CdConverter.convertCdToString(resultDto.getSubcategoryCode()))));
        dto.setIdentifier(null);
        try {
            remoteEjb.create(dto);
            fail("Should have failed form multiple lead drugs.");
        } catch (Exception e) {
            // expected behavior
        }
        dto.setSubcategoryCode(CdConverter.convertToCd(ActivitySubcategoryCode.DEVICE));
        resultDto = remoteEjb.create(dto);
        assertTrue(ActivitySubcategoryCode.DEVICE.equals(ActivitySubcategoryCode.getByCode(CdConverter.convertCdToString(resultDto.getSubcategoryCode()))));
    }

    @Test
    public void iiRootTest() throws Exception {
        PlannedActivityDTO dto = remoteEjb.get(ii);
        assertEquals(dto.getIdentifier().getRoot(), IiConverter.ACTIVITY_ROOT);
        assertTrue(StringUtils.isNotEmpty(dto.getIdentifier().getIdentifierName()));
        assertEquals(dto.getStudyProtocolIdentifier().getRoot(), IiConverter.STUDY_PROTOCOL_ROOT);
    }
    
    @Test
    public void testCopyPlannedEligibilityStudyCriterions() throws PAException {
       Ii toStudyProtocolIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(1));
       remoteEjb.copyPlannedEligibilityStudyCriterions(spIi, toStudyProtocolIi);
       List<PlannedEligibilityCriterionDTO> dtos = remoteEjb.getPlannedEligibilityCriterionByStudyProtocol(toStudyProtocolIi);
       assertEquals(dtos.get(0).getStudyProtocolIdentifier(), toStudyProtocolIi);
       assertTrue(dtos.get(0).getIdentifier() != null);
    }
    
    @Test
    public void testCreatePlannedSubstanceAdministration() throws PAException {
       PlannedSubstanceAdministrationDTO dto = new PlannedSubstanceAdministrationDTO();
       dto.setStudyProtocolIdentifier(spIi);
       dto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.OTHER));
       dto.setSubcategoryCode(CdConverter.convertToCd(ActivitySubcategoryCode.RADIATION));
       PlannedSubstanceAdministrationDTO returnDto = remoteEjb.createPlannedSubstanceAdministration(dto);
       assertTrue(returnDto.getIdentifier() != null);
       
       dto.setDoseFormCode(CdConverter.convertStringToCd("dose"));
       dto.setDoseFrequencyCode(CdConverter.convertStringToCd("frequency"));
       dto.setRouteOfAdministrationCode(CdConverter.convertStringToCd("Admin"));
       Pq pqLow = new Pq();
       pqLow.setValue(new BigDecimal("80"));
       pqLow.setUnit(UnitsCode.YEARS.getCode());
       Pq pqHigh = new Pq();
       pqHigh.setValue(new BigDecimal("90"));
       pqHigh.setUnit(UnitsCode.YEARS.getCode());
       Ivl<Pq> ivlPq = new Ivl<Pq>();
       ivlPq.setHigh(pqHigh);
       ivlPq.setLow(pqLow);
       dto.setDose(ivlPq);
       dto.setDoseTotal(ivlPq);
       dto.setDoseDescription(StConverter.convertToSt("description"));
       dto.setApproachSiteCode(CdConverter.convertStringToCd("Approach"));
       dto.setTargetSiteCode(CdConverter.convertStringToCd("Target"));
       dto.setDoseDuration(pqHigh);
       try {
           remoteEjb.createPlannedSubstanceAdministration(dto);
           fail("Validation Exception ");
       } catch (Exception e) {
           // expected behavior
       }
       
       dto.setIdentifier(ii);
       try {
           remoteEjb.createPlannedSubstanceAdministration(dto);
           fail("Cannot call createPlannedSubstanceAdministration with a non null identifier.");
       } catch (Exception e) {
           // expected behavior
       }
       dto.setIdentifier(null);
       dto.setStudyProtocolIdentifier(null);
       try {
           remoteEjb.createPlannedSubstanceAdministration(dto);
           fail("Cannot call createPlannedSubstanceAdministration with a null study protocol identifier");
       } catch (Exception e) {
           // expected behavior
       }
    }
    
    @Test
    public void testupdatePlannedSubstanceAdministration() throws PAException {
       PlannedSubstanceAdministrationDTO dto = new PlannedSubstanceAdministrationDTO();
       Ii id = IiConverter.convertToIi(2L);
       dto.setIdentifier(id);
       dto.setStudyProtocolIdentifier(spIi);
       dto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.OTHER));
       dto.setSubcategoryCode(CdConverter.convertToCd(ActivitySubcategoryCode.BEHAVIORAL));
       PlannedSubstanceAdministrationDTO returnDto = remoteEjb.updatePlannedSubstanceAdministration(dto);
       assertTrue(returnDto.getIdentifier() != null);
       
       dto.setIdentifier(null);
       try {
           remoteEjb.updatePlannedSubstanceAdministration(dto);
           fail("Create method should be used to modify existing.");
       } catch (Exception e) {
           // expected behavior
       }
    }
    
    @Test
    public void testGetPlannedSubstanceAdministrationByStudyProtocol() throws PAException {
       List<PlannedSubstanceAdministrationDTO> dtos = remoteEjb.getPlannedSubstanceAdministrationByStudyProtocol(spIi);
       assertTrue(dtos.size() > 0);
       assertEquals(spIi, dtos.get(0).getStudyProtocolIdentifier());
       assertEquals(ActivityCategoryCode.SUBSTANCE_ADMINISTRATION.getCode(), dtos.get(0).getCategoryCode().getCode());
       
       dtos = remoteEjb.getPlannedSubstanceAdministrationByStudyProtocol(null);
       assertTrue(dtos.isEmpty());
    }
    
    @Test
    public void testGetPlannedSubstanceAdministration() throws PAException {
       Ii pid = IiConverter.convertToIi(4L);
       PlannedSubstanceAdministrationDTO dto = remoteEjb.getPlannedSubstanceAdministration(pid);
       assertEquals(pid.getExtension(), dto.getIdentifier().getExtension());
       assertEquals(dto.getCategoryCode().getCode(),ActivityCategoryCode.SUBSTANCE_ADMINISTRATION.getCode());
       
      
       dto = remoteEjb.getPlannedSubstanceAdministration(null);
       assertTrue(dto == null);
       
       pid = IiConverter.convertToIi(3L);
       try {
           dto = remoteEjb.getPlannedSubstanceAdministration(pid);
           fail("Object not found using get() for id = " + IiConverter.convertToString(pid) + ".  ");
       }catch (Exception e) {
           // expected behavior
       }
    }
    
    @Test
    public void testCreatePlannedProcedure() throws PAException {
       PlannedProcedureDTO dto = new PlannedProcedureDTO();
       dto.setStudyProtocolIdentifier(spIi);
       dto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.OTHER));
       PlannedProcedureDTO returnDto = remoteEjb.createPlannedProcedure(dto);
       assertEquals("7", returnDto.getIdentifier().getExtension());
       
       dto.setMethodCode(CdConverter.convertStringToCd("A"));
       dto.setTargetSiteCode(CdConverter.convertStringToCd("Site"));
       try {
           remoteEjb.createPlannedProcedure(dto);
           fail("Validation Exception ");
       }catch (Exception e) {
           // expected behavior
       }

       dto.setIdentifier(ii);
       try {
           remoteEjb.createPlannedProcedure(dto);
           fail("Update method should be used to modify existing.  ");
       }catch (Exception e) {
           // expected behavior
       }
       dto.setIdentifier(null);
       dto.setStudyProtocolIdentifier(null);
       try {
           remoteEjb.createPlannedProcedure(dto);
           fail("StudyProtocol must be set.  ");
       }catch (Exception e) {
           // expected behavior
       }
       
       
    }
    
    @Test
    public void testUpdatePlannedProcedure() throws PAException {
       PlannedProcedureDTO dto = new PlannedProcedureDTO();
       dto.setIdentifier(ii);
       dto.setStudyProtocolIdentifier(spIi);
       dto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.OTHER));
       PlannedProcedureDTO returnDto = remoteEjb.updatePlannedProcedure(dto);
       assertEquals("1", returnDto.getIdentifier().getExtension());
       
       dto.setIdentifier(null);
       try {
           remoteEjb.updatePlannedProcedure(dto);
           fail("Create method should be used to modify existing.  ");
       }catch (Exception e) {
           // expected behavior
       }
    }
    @Test
    public void testGetPlannedProcedureByStudyProtocol() throws PAException {
        List<PlannedProcedureDTO> dtos = remoteEjb.getPlannedProcedureByStudyProtocol(spIi);
        assertTrue(dtos.size() > 0);
        assertEquals("2", dtos.get(0).getIdentifier().getExtension());
        assertEquals(dtos.get(0).getCategoryCode().getCode(),ActivityCategoryCode.PLANNED_PROCEDURE.getCode());
        
        dtos = remoteEjb.getPlannedProcedureByStudyProtocol(null);
        assertTrue(dtos.isEmpty());
    }
    @Test
    public void testGetPlannedProcedure() throws PAException {
        Ii pid = IiConverter.convertToIi(2L); 
        PlannedProcedureDTO dto = remoteEjb.getPlannedProcedure(pid);
        assertEquals(dto.getCategoryCode().getCode(),ActivityCategoryCode.PLANNED_PROCEDURE.getCode());
        
        dto = remoteEjb.getPlannedProcedure(null);
        assertTrue(dto == null);
        
        pid = IiConverter.convertToIi(1L);
        try {
            dto = remoteEjb.getPlannedProcedure(pid);
            fail("Object not found using get() for id = " + IiConverter.convertToString(pid) + ".  ");
        }catch (Exception e) {
            // expected behavior
        }
    }
}

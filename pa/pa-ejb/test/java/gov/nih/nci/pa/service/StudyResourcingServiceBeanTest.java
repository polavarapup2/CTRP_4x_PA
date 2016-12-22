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
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.PAProperties;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.RealConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.StudyResourcingService.Method;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.I2EGrantsServiceLocal;
import gov.nih.nci.pa.service.util.LookUpTableServiceBean;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.family.FamilyP30DTO;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StudyResourcingServiceBeanTest extends AbstractHibernateTestCase {

    private final StudyResourcingServiceLocal remoteEjb = new StudyResourcingBeanLocal();
    Ii pid;
    private I2EGrantsServiceLocal i2eSvc = mock(I2EGrantsServiceLocal.class);
 
    @Rule  
    public ExpectedException thrown = ExpectedException.none();  

    @Before
    public void setUp() throws Exception {
        ((StudyResourcingBeanLocal) remoteEjb).setLookUpTableSvc(new LookUpTableServiceBean());
        ((StudyResourcingBeanLocal) remoteEjb).setStudyProtocolSvc(new StudyProtocolBeanLocal());
        ((StudyResourcingBeanLocal) remoteEjb).setI2eSvc(i2eSvc);
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();
        pid = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
        setupFamilyHelper(null);
    }

    private void setupFamilyHelper(String p30SerialNumber) throws Exception {
        PoServiceLocator psl = mock(PoServiceLocator.class);
        OrganizationEntityServiceRemote oes = mock(OrganizationEntityServiceRemote.class);
        when(psl.getOrganizationEntityService()).thenReturn(oes);
        FamilyServiceRemote fs = mock(FamilyServiceRemote.class);
        when(psl.getFamilyService()).thenReturn(fs);
        PoRegistry.getInstance().setPoServiceLocator(psl);
        if (p30SerialNumber != null) {
            OrganizationDTO org  = new OrganizationDTO();
            DSet<Ii> dset = new DSet<Ii>();
            org.setFamilyOrganizationRelationships(dset);
            List<OrganizationDTO> result = new ArrayList<OrganizationDTO>();
            result.add(org);
            when(oes.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenReturn(result);
            Set<Ii> familySet = new HashSet<Ii>();
            familySet.add(IiConverter.convertToPoFamilyIi("1"));
            dset.setItem(familySet);
            Map<Ii, FamilyDTO> familyMap = new HashMap<Ii, FamilyDTO>();
            FamilyDTO family = new FamilyDTO();
            family.setName(EnOnConverter.convertToEnOn("family name"));
            familyMap.put(IiConverter.convertToPoFamilyIi("1"), family);
            when(fs.getFamilies(any(Set.class))).thenReturn(familyMap);
            FamilyOrganizationRelationshipDTO forDto = new FamilyOrganizationRelationshipDTO();
            forDto.setFunctionalType(CdConverter.convertStringToCd(FamilyFunctionalType.ORGANIZATIONAL.name()));
            when(fs.getFamilyOrganizationRelationship(any(Ii.class))).thenReturn(forDto);
            FamilyP30DTO p30 = new FamilyP30DTO();
            p30.setSerialNumber(EnOnConverter.convertToEnOn(p30SerialNumber));
            when(fs.getP30Grant(anyLong())).thenReturn(p30);
        }
    }

    @Test
    public void getTest() throws Exception {
        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());

        StudyResourcing sr = StudyResourcingServiceBeanTest.createStudyResourcingObj(sp);
        TestSchema.addUpdObject(sr);
        assertNotNull(sr.getId());

        List<StudyResourcingDTO> srDTO = remoteEjb.getSummary4ReportedResourcing(IiConverter.convertToIi(sp.getId()));
        assertNotNull(srDTO);
        StudyResourcingDTO srDTO2 = remoteEjb.getStudyResourcingById(srDTO.get(0).getIdentifier());
        assertNotNull(srDTO2);
        remoteEjb.deleteStudyResourcingById(srDTO2);

    }

    @Test
    public void createStudyResourcingTest() throws Exception {

        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());

        StudyResourcing sr = StudyResourcingServiceBeanTest.createStudyResourcingObj(sp);
        TestSchema.addUpdObject(sr);
        assertNotNull(sr.getId());

        StudyResourcingDTO srDTO1 = new StudyResourcingDTO();
        srDTO1.setSerialNumber(StConverter.convertToSt("12345"));
        srDTO1.setStudyProtocolIdentifier(IiConverter.convertToIi(sp.getId()));
        srDTO1.setFundingPercent(RealConverter.convertToReal(33.3d));

        StudyResourcingDTO srDTO2 = remoteEjb.createStudyResourcing(srDTO1);
        assertNotNull(srDTO2);

        // assertEquals (srDTO1.getFundingMechanismCode().getCode() , srDTO2.getFundingMechanismCode().getCode());
        assertEquals(srDTO1.getSerialNumber().getValue(), srDTO2.getSerialNumber().getValue());
        assertEquals(srDTO1.getFundingPercent().getValue(), srDTO2.getFundingPercent().getValue());
    }

    @Test
    public void updateStudyResourcingTest() throws Exception {

        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());

        StudyResourcing sr = StudyResourcingServiceBeanTest.createStudyResourcingObj(sp);
        TestSchema.addUpdObject(sr);
        assertNotNull(sr.getId());

        StudyResourcingDTO srDTO1 = new StudyResourcingDTO();
        srDTO1.setSerialNumber(StConverter.convertToSt("12345"));
        srDTO1.setStudyProtocolIdentifier(IiConverter.convertToIi(sp.getId()));
        srDTO1.setFundingPercent(RealConverter.convertToReal(33.3d));

        StudyResourcingDTO srDTO2 = remoteEjb.createStudyResourcing(srDTO1);
        assertNotNull(srDTO2);
        assertEquals(srDTO1.getSerialNumber().getValue(), srDTO2.getSerialNumber().getValue());
        assertEquals(srDTO1.getFundingPercent().getValue(), srDTO2.getFundingPercent().getValue());

        srDTO2.setStudyProtocolIdentifier(IiConverter.convertToIi(sp.getId()));
        srDTO2.setSerialNumber(StConverter.convertToSt("123123"));
        StudyResourcingDTO srDTO3 = remoteEjb.updateStudyResourcing(srDTO2);
        assertNotNull(srDTO3);
        assertEquals(srDTO3.getSerialNumber().getValue().toString(), "123123");

        List<StudyResourcingDTO> statusList = remoteEjb.getStudyResourcingByStudyProtocol(pid);
        assertNotNull(statusList);
    }

    @Test
    public void iiRootTest() throws Exception {
        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());

        StudyResourcing sr = StudyResourcingServiceBeanTest.createStudyResourcingObj(sp);
        TestSchema.addUpdObject(sr);
        assertNotNull(sr.getId());

        StudyResourcingDTO dto = remoteEjb.getStudyResourcingById(IiConverter.convertToStudyResourcingIi(sr.getId()));
        assertEquals(dto.getIdentifier().getRoot(), IiConverter.STUDY_RESOURCING_ROOT);
        assertTrue(StringUtils.isNotEmpty(dto.getIdentifier().getIdentifierName()));
        assertEquals(dto.getStudyProtocolIdentifier().getRoot(), IiConverter.STUDY_PROTOCOL_ROOT);
    }

    @Test
    public void grantsRequiredChecksActiveTest() throws Exception {
        StudyResourcingBeanLocal bean = (StudyResourcingBeanLocal) remoteEjb;
        PAProperties prop = new PAProperties();
        prop.setName("GrantsRequiredBatchRegEffectiveDate");
        prop.setValue("01-JAN-1990");
        TestSchema.addUpdObject(prop);
        prop = new PAProperties();
        prop.setName("GrantsRequiredRegServiceEffectiveDate");
        prop.setValue("01-JAN-2099");
        TestSchema.addUpdObject(prop);
        assertTrue(bean.grantsRequiredChecksActive(Method.BATCH));
        assertFalse(bean.grantsRequiredChecksActive(Method.SERVICE));
        assertTrue(bean.grantsRequiredChecksActive(Method.UI));
    }

    @Test
    public void validateP30NotRequiredNoDataTest() throws Exception {
        remoteEjb.validate(Method.UI, false, null, 1L, new ArrayList<StudyResourcingDTO>());
    }

    @Test
    public void validateP30RequiredNoDataTest() throws Exception {
        setupFamilyHelper("123456");
        thrown.expect(PAValidationException.class);  
        thrown.expectMessage("A valid P30 grant record must be added.");  
        List<StudyResourcingDTO> dtos = new ArrayList<StudyResourcingDTO>();
        StudyResourcingDTO dto = createStudyResourcingISOObj();
        dto.setFundingMechanismCode(CdConverter.convertStringToCd("P30"));
        dto.setActiveIndicator(BlConverter.convertToBl(false));
        dtos.add(dto);
        remoteEjb.validate(Method.UI, false, null, 1L, dtos);
    }

    @Test
    public void validateP30NotRequiredTest() throws Exception {
        List<StudyResourcingDTO> dtos = new ArrayList<StudyResourcingDTO>();
        remoteEjb.validate(Method.UI, false, null, 1L, dtos);

        StudyResourcingDTO dto = createStudyResourcingISOObj();
        dto.setFundingMechanismCode(CdConverter.convertStringToCd("P30"));
        dtos.add(dto);
        remoteEjb.validate(Method.UI, false, null, 1L, dtos);
    }

    @Test
    public void validateCANotReguiredNotSubmittedTest() throws Exception {
        List<StudyResourcingDTO> dtos = new ArrayList<StudyResourcingDTO>();
        StudyResourcingDTO dto = createStudyResourcingISOObj();
        dto.setNihInstitutionCode(CdConverter.convertStringToCd("CA"));
        dto.setActiveIndicator(BlConverter.convertToBl(false));
        dtos.add(dto);
        remoteEjb.validate(Method.UI, false, null, 1L, dtos);
    }

    @Test
    public void validateCANotReguiredSubmittedTest() throws Exception {
        thrown.expect(PAValidationException.class);  
        thrown.expectMessage("This trial is not funded by NCI; however, an NCI grant record was entered.");  
        List<StudyResourcingDTO> dtos = new ArrayList<StudyResourcingDTO>();
        StudyResourcingDTO dto = createStudyResourcingISOObj();
        dto.setNihInstitutionCode(CdConverter.convertStringToCd("CA"));
        dtos.add(dto);
        remoteEjb.validate(Method.UI, false, null, 1L, dtos);
    }

    @Test
    public void validateCAReguiredNotSubmittedTest() throws Exception {
        thrown.expect(PAValidationException.class);  
        thrown.expectMessage("This trial is funded by NCI; however, an NCI grant record was not entered.");  
        List<StudyResourcingDTO> dtos = new ArrayList<StudyResourcingDTO>();
        remoteEjb.validate(Method.UI, true, null, 1L, dtos);
    }

    @Test
    public void validateCAReguiredSubmittedTest() throws Exception {
        List<StudyResourcingDTO> dtos = new ArrayList<StudyResourcingDTO>();
        StudyResourcingDTO dto = createStudyResourcingISOObj();
        dto.setNihInstitutionCode(CdConverter.convertStringToCd("CA"));
        dtos.add(dto);
        remoteEjb.validate(Method.UI, true, null, 1L, dtos);
    }

    @Test
    public void validateTotalPctTest() throws Exception {
        remoteEjb.validate(Method.ABSTRACTION_VALIDATION, false, null, 1L, new ArrayList<StudyResourcingDTO>());
    }

    @Test
    public void validateTotalPctNotTestedTest() throws Exception {
        List<StudyResourcingDTO> dtos = new ArrayList<StudyResourcingDTO>();
        StudyResourcingDTO dto = createStudyResourcingISOObj();
        dto.setFundingPercent(RealConverter.convertToReal(60d));
        dtos.add(dto);
        dto = createStudyResourcingISOObj();
        dto.setFundingPercent(RealConverter.convertToReal(60d));
        dtos.add(dto);
        remoteEjb.validate(Method.UI, false, null, 1L, dtos);
    }

    @Test
    public void validateTotalPctFailureTest() throws Exception {
        thrown.expect(PAValidationException.class);  
        thrown.expectMessage("Total percent of grant funding this trial for all grants cannot be greater than 100.");  
        List<StudyResourcingDTO> dtos = new ArrayList<StudyResourcingDTO>();
        StudyResourcingDTO dto = createStudyResourcingISOObj();
        dto.setFundingPercent(RealConverter.convertToReal(60d));
        dtos.add(dto);
        dto = createStudyResourcingISOObj();
        dto.setFundingPercent(RealConverter.convertToReal(60d));
        dtos.add(dto);
        remoteEjb.validate(Method.ABSTRACTION_VALIDATION, false, null, 1L, dtos);
     }

    @Test
    public void validateExistingGrantsTest() throws Exception {
        thrown.expect(PAValidationException.class);  
        thrown.expectMessage("Total percent of grant funding this trial for all grants cannot be greater than 100.");  
        Long spId = TestSchema.studyProtocolIds.get(0);
        StudyProtocol sp = new StudyProtocol();
        sp.setId(spId);
        StudyResourcing grant = new StudyResourcing();
        grant.setSummary4ReportedResourceIndicator(Boolean.FALSE);
        grant.setStudyProtocol(sp);
        grant.setFundingMechanismCode("U10");
        grant.setNihInstituteCode("HL");
        grant.setNciDivisionProgramCode(NciDivisionProgramCode.CCR);
        grant.setSerialNumber("123456");
        grant.setFundingPercent(100.1d);
        TestSchema.addUpdObject(grant);
        remoteEjb.validate(Method.ABSTRACTION_VALIDATION, false, String.valueOf(spId), 1L, new ArrayList<StudyResourcingDTO>());
    }

    private static StudyResourcing createStudyResourcingObj(StudyProtocol sp) {
        StudyResourcing sr = new StudyResourcing();
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        sr.setDateLastUpdated(now);
        sr.setOrganizationIdentifier("1");
        sr.setStudyProtocol(sp);
        sr.setSummary4ReportedResourceIndicator(Boolean.TRUE);
        sr.setTypeCode(SummaryFourFundingCategoryCode.INDUSTRIAL);
        sr.setUserLastUpdated(TestSchema.getUser());
        return sr;
    }

    private static StudyResourcingDTO createStudyResourcingISOObj() {
        StudyResourcingDTO sr = new StudyResourcingDTO();
        sr.setActiveIndicator(BlConverter.convertToBl(true));
        return sr;
    }
}

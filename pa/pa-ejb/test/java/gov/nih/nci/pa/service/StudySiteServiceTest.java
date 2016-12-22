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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.convert.StudySiteConverter;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test service and converter.
 * @author hreinhart
 *
 */
public class StudySiteServiceTest extends AbstractHibernateTestCase {
    private final StudySiteServiceLocal remoteEjb = new StudySiteBeanLocal();
    private final StudySiteConverter studySiteConverter = new StudySiteConverter();
    Long studyId;
    Ii studyIi;
    Long siteId;
    Ii siteIi;
    Long facilityId;
    Ii facilityIi;
    Long researchOrgId;
    Ii researchOrgIi;
    Long oversightCommitteeId;
    Ii oversightCommitteeIi;


    @Rule public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();
        studyId = TestSchema.studyProtocolIds.get(0);
        studyIi = IiConverter.convertToStudyProtocolIi(studyId);
        siteId = TestSchema.studySiteIds.get(0);
        siteIi = IiConverter.convertToIi(siteId);
        facilityId = TestSchema.healthCareFacilityIds.get(0);
        facilityIi = IiConverter.convertToIi(facilityId);
        researchOrgId = TestSchema.researchOrganizationIds.get(0);
        researchOrgIi = IiConverter.convertToIi(researchOrgId);
        oversightCommitteeId = TestSchema.oversightCommitteeIds.get(0);
        oversightCommitteeIi = IiConverter.convertToIi(oversightCommitteeId);
    }
    @Test
    public void get() throws Exception {
        StudySiteDTO spDto = remoteEjb.get(siteIi);
        StudySite spBo = studySiteConverter.convertFromDtoToDomain(spDto);
        assertEquals(studyId, spBo.getStudyProtocol().getId());
        assertEquals(StudySiteFunctionalCode.TREATING_SITE.getName(), spBo.getFunctionalCode().getName());
        assertEquals(FunctionalRoleStatusCode.ACTIVE.getName(), spBo.getStatusCode().getName());
        assertEquals("Local SP ID 01", spBo.getLocalStudyProtocolIdentifier());
    }
    @Test
    public void create() throws Exception {
        StudySiteDTO spDto = createStudySite();
        StudySiteDTO result = remoteEjb.create(spDto);

        assertFalse(ISOUtil.isIiNull(result.getIdentifier()));
        assertEquals(CdConverter.convertCdToString(spDto.getFunctionalCode()),
                     CdConverter.convertCdToString(result.getFunctionalCode()));
        assertEquals(new Integer(63), IntConverter.convertToInteger(result.getTargetAccrualNumber()));
        assertEquals("abc", result.getOversightCommitteeIi().getExtension());

        remoteEjb.copy(IiConverter.convertToStudyProtocolIi(studyId), IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(1)));
        
        LimitOffset pagingParams = new LimitOffset(1, 1);
        StudySiteDTO dto2 = new StudySiteDTO();
        dto2.setStudyProtocolIdentifier(studyIi);
        List<StudySiteDTO> list = remoteEjb.search(dto2, pagingParams);
        assertEquals(1,list.size());
        try {
        	remoteEjb.search(null, pagingParams);
        	fail();
        } catch (Exception e) {
            // expected
        }
    }

    private StudySiteDTO createStudySite() {
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setIdentifier(null);
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.STUDY_OVERSIGHT_COMMITTEE));
        spDto.setOversightCommitteeIi(oversightCommitteeIi);
        spDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt("Local SP ID 02"));
        spDto.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.ACTIVE));
        spDto.setStatusDateRange(IvlConverter.convertTs().convertToIvl(PAUtil.dateStringToTimestamp("1/1/2005"),null));
        spDto.setReviewBoardApprovalStatusCode
            (CdConverter.convertToCd(ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED));
        spDto.setReviewBoardApprovalNumber(StConverter.convertToSt("777"));
        spDto.setStudyProtocolIdentifier(studyIi);
        spDto.setTargetAccrualNumber(IntConverter.convertToInt(63));
        return spDto;
    }
    
    
    @Test
    public void createNCTIdentifier() throws Exception {    	
    	TestSchema.addAbstractedWorkflowStatus(studyId);
    	Organization ctgov = new Organization();
        ctgov.setName(PAConstants.CTGOV_ORG_NAME);
        ctgov.setUserLastUpdated(TestSchema.getUser());
        ctgov.setDateLastUpdated(TestSchema.TODAY);
        ctgov.setIdentifier("2");
        ctgov.setStatusCode(EntityStatusCode.PENDING);
        TestSchema.addUpdObject(ctgov);
        
        ResearchOrganization resOrg = new ResearchOrganization();
        resOrg.setOrganization(ctgov);
        resOrg.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        resOrg.setIdentifier("1");
        TestSchema.addUpdObject(resOrg);
        
        StudySite sPart3 = new StudySite();
        sPart3.setLocalStudyProtocolIdentifier("Local NCTID");
        StudyProtocol sp = new StudyProtocol();
        sp.setId(studyId);
        sPart3.setStudyProtocol(sp);
        sPart3.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
        sPart3.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        sPart3.setStatusDateRangeLow(ISOUtil.dateStringToTimestamp("8/1/2013"));
        sPart3.setResearchOrganization(resOrg);
        TestSchema.addUpdObject(sPart3);
    	
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(studyId));
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
        Ii resOrgIi = new Ii();
        resOrgIi.setExtension(resOrg.getIdentifier());
        resOrgIi.setIdentifierName(IiConverter.RESEARCH_ORG_IDENTIFIER_NAME);
        spDto.setResearchOrganizationIi(resOrgIi);
        try {
            remoteEjb.create(spDto);
            fail("This organization has already been entered as a 'Identifier Assigner' for this study.");
        } catch (PAException e) {
            // expected behavior
        }
                
        spDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt("Local NCTID"));
        spDto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(1)));
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
        resOrgIi = new Ii();
        resOrgIi.setExtension(researchOrgId.toString());
        resOrgIi.setIdentifierName(IiConverter.RESEARCH_ORG_IDENTIFIER_NAME);
        spDto.setResearchOrganizationIi(resOrgIi);
        try {
            remoteEjb.create(spDto);
            fail("The NCT Trial Identifier provided is tied to another trial in CTRP system. Please check the ID provided and try again. If you believe that the ID provided is correct then please contact CTRO staff.");
        } catch (PAException e) {
            // expected behavior
        }
    }

    @Test
    public void delete() throws Exception {
        remoteEjb.delete(siteIi);
        StudySiteDTO spDto = remoteEjb.get(siteIi);
        assertNull(spDto);
    }
    @Test
    public void getByProtocol() throws Exception {
        List<StudySiteDTO> spList = remoteEjb.getByStudyProtocol(studyIi);
        assertEquals(siteId, IiConverter.convertToLong(spList.get(0).getIdentifier()));
        List<StudySiteDTO> spList2 = remoteEjb.getByStudyProtocol(studyIi, spList);
        assertEquals(siteId, IiConverter.convertToLong(spList2.get(0).getIdentifier()));
        List<StudySiteDTO> spList3 = remoteEjb.getByStudyProtocol(studyIi, spList2.get(0));
        assertEquals(siteId, IiConverter.convertToLong(spList3.get(0).getIdentifier()));

    }
    @Test
    public void businessRules() throws Exception {
        StudySiteDTO dto = remoteEjb.get(siteIi);
        dto.setOversightCommitteeIi(null);
        dto.setReviewBoardApprovalStatusCode
        (CdConverter.convertToCd(ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED));
        try {
            remoteEjb.update(dto);
            fail("Should have thrown an exception for either oversight committee must be set.");
        } catch (PAException e) {
            // expected behavior
        }
        dto.setHealthcareFacilityIi(null);
        dto.setResearchOrganizationIi(null);
        try {
            remoteEjb.update(dto);
            fail("Either healthcare facility or research organization or Oversight committee must be set.");
        } catch (PAException e) {
        	// expected behavior
        }
        dto.setHealthcareFacilityIi(facilityIi);
        dto.setResearchOrganizationIi(researchOrgIi);
        dto.setOversightCommitteeIi(null);
        try {
            remoteEjb.update(dto);
            fail("Healthcare facility and research organization cannot both be set.");
        } catch (PAException e) {
        	// expected behavior
        }
        dto.setHealthcareFacilityIi(facilityIi);
        dto.setOversightCommitteeIi(oversightCommitteeIi);
        dto.setResearchOrganizationIi(null);
        try {
            remoteEjb.update(dto);
            fail("Healthcare facility and oversight committee cannot both be set.");
        } catch (PAException e) {
            // expected behavior
        }
        dto.setOversightCommitteeIi(oversightCommitteeIi);
        dto.setResearchOrganizationIi(researchOrgIi);
        dto.setHealthcareFacilityIi(null);
        try {
            remoteEjb.update(dto);
            fail("research organization and oversight committee cannot both be set.");
        } catch (PAException e) {
            // expected behavior
        }       
        dto.setHealthcareFacilityIi(null);
        dto.setResearchOrganizationIi(null);
        dto.setReviewBoardApprovalStatusCode
        (CdConverter.convertToCd(ReviewBoardApprovalStatusCode.SUBMITTED_DENIED));
        dto.setReviewBoardApprovalNumber(StConverter.convertToSt("0"));
        remoteEjb.update(dto);
        
        dto.setHealthcareFacilityIi(null);
        dto.setOversightCommitteeIi(null);
        dto.setResearchOrganizationIi(researchOrgIi);
        dto.setReviewBoardApprovalStatusCode
        (CdConverter.convertToCd(ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT));
        dto.setReviewBoardApprovalNumber(StConverter.convertToSt("2"));
        try {
        	remoteEjb.update(dto);
        	fail("Oversight committee (board) must be set when review board approval status is 'Submitted approved' or 'Submitted exempt'.");
        } catch (PAException e) {
            // expected behavior
        }      
    }

    @Test
    public void iiRootTest() throws Exception {
        StudySiteDTO dto = remoteEjb.get(siteIi);
        assertEquals(dto.getStudyProtocolIdentifier().getRoot(), IiConverter.STUDY_PROTOCOL_ROOT);
    }

    @Test
    public void testCascadeRoleStatusErrors() throws PAException {
        Cd roleStatusCode = CdConverter.convertStringToCd("");

        // test missing name
        Ii ii = new Ii();
        ii.setIdentifierName("some bad name");
        try {
            remoteEjb.cascadeRoleStatus(ii, roleStatusCode);
            fail("this should have generated an exception");
        } catch (PAException ex) {
            assertTrue(ex.getMessage().contains("ii without name"));
        }

        // test incorrect role for studySite
        ii.setIdentifierName(IiConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME);
        try {
            remoteEjb.cascadeRoleStatus(ii, roleStatusCode);
            fail("this should have generated an exception");
        } catch (PAException ex) {
            assertTrue(ex.getMessage().contains("Unable to update Role"));
        }
    }

    @Test
    public void testCascadeRoleStatusForHcf() throws PAException {
        Ii ii = new Ii();
        ii.setExtension("1");
        ii.setIdentifierName(IiConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
        Cd roleStatusCode = CdConverter.convertStringToCd("Nullified");

        remoteEjb.cascadeRoleStatus(ii, roleStatusCode);

        StudySiteDTO ssdto = remoteEjb.get(siteIi);
        // verify Id is still 1
        assertEquals("1", ssdto.getHealthcareFacilityIi().getExtension());
        // verify site status is changed to Nullified
        assertEquals("Nullified", ssdto.getStatusCode().getCode());
    }

    @Test
    public void testCascadeRoleStatusForOC() throws PAException {
        StudySiteDTO spDto = createStudySite();
        StudySiteDTO result = remoteEjb.create(spDto);

        Ii ii = new Ii();
        ii.setExtension("abc");
        ii.setIdentifierName(IiConverter.OVERSIGHT_COMMITTEE_IDENTIFIER_NAME);
        Cd roleStatusCode = CdConverter.convertStringToCd("Nullified");

        remoteEjb.cascadeRoleStatus(ii, roleStatusCode);

        PaHibernateUtil.getCurrentSession().clear();

        StudySiteDTO ssdto = remoteEjb.get(result.getIdentifier());
        // verify Id is still abc
        assertEquals("abc", ssdto.getOversightCommitteeIi().getExtension());
        // verify site status is changed to Nullified
        assertEquals("Nullified", ssdto.getStatusCode().getCode());
    }

    @Test
    public void testCascadeRoleStatusForRO() throws PAException {
        StudySiteDTO spDto = createStudySite();
        spDto.setResearchOrganizationIi(researchOrgIi);
        spDto.setReviewBoardApprovalStatusCode(null);
        spDto.setReviewBoardApprovalDate(null);
        spDto.setReviewBoardApprovalNumber(null);
        spDto.setOversightCommitteeIi(null);
        StudySiteDTO result = remoteEjb.create(spDto);

        Ii ii = new Ii();
        ii.setExtension("abc");
        ii.setIdentifierName(IiConverter.RESEARCH_ORG_IDENTIFIER_NAME);
        Cd roleStatusCode = CdConverter.convertStringToCd("Nullified");

        remoteEjb.cascadeRoleStatus(ii, roleStatusCode);

        PaHibernateUtil.getCurrentSession().clear();

        StudySiteDTO ssdto = remoteEjb.get(result.getIdentifier());
        // verify Id is still abc
        assertEquals("abc", ssdto.getResearchOrganizationIi().getExtension());
        // verify site status is changed to Nullified
        assertEquals("Nullified", ssdto.getStatusCode().getCode());
    }

    @Test
    public void testGetOrganizationByStudySiteId() throws PAException {
        // oversight committee
        StudySiteDTO dto = createStudySite();
        dto.setOversightCommitteeIi(oversightCommitteeIi);
        dto.setResearchOrganizationIi(null);
        dto.setHealthcareFacilityIi(null);
        assertTrue(ISOUtil.isIiNull(dto.getIdentifier()));
        dto = remoteEjb.create(dto);
        assertFalse(ISOUtil.isIiNull(dto.getIdentifier()));
        Organization org = remoteEjb.getOrganizationByStudySiteId(IiConverter.convertToLong(dto.getIdentifier()));
        assertEquals("Mayo University", org.getName());

        // research organization
        dto = createStudySite();
        dto.setOversightCommitteeIi(null);
        dto.setResearchOrganizationIi(researchOrgIi);
        dto.setHealthcareFacilityIi(null);
        dto.setReviewBoardApprovalStatusCode(null);
        dto.setReviewBoardApprovalDate(null);
        dto.setReviewBoardApprovalNumber(null);
        assertTrue(ISOUtil.isIiNull(dto.getIdentifier()));
        dto = remoteEjb.create(dto);
        assertFalse(ISOUtil.isIiNull(dto.getIdentifier()));
        org = remoteEjb.getOrganizationByStudySiteId(IiConverter.convertToLong(dto.getIdentifier()));
        assertEquals("Mayo University", org.getName());

        // healthcare facility
        dto = createStudySite();
        dto.setOversightCommitteeIi(null);
        dto.setResearchOrganizationIi(null);
        dto.setHealthcareFacilityIi(facilityIi);
        dto.setReviewBoardApprovalStatusCode(null);
        dto.setReviewBoardApprovalDate(null);
        dto.setReviewBoardApprovalNumber(null);
        assertTrue(ISOUtil.isIiNull(dto.getIdentifier()));
        dto = remoteEjb.create(dto);
        assertFalse(ISOUtil.isIiNull(dto.getIdentifier()));
        org = remoteEjb.getOrganizationByStudySiteId(IiConverter.convertToLong(dto.getIdentifier()));
        assertEquals("Mayo University", org.getName());
    }

    @Test
    public void testGetAllAssociatedTrials() throws Exception {
        Session sess = PaHibernateUtil.getCurrentSession();
        SQLQuery qry = sess.createSQLQuery("DELETE FROM study_site WHERE study_protocol_identifier = " + studyId);
        qry.executeUpdate();
        Set<Long> trials = remoteEjb.getAllAssociatedTrials("1", StudySiteFunctionalCode.TREATING_SITE);
        assertFalse(trials.contains(studyId));

        StudySiteDTO dto = createStudySite();
        dto.setOversightCommitteeIi(null);
        dto.setHealthcareFacilityIi(facilityIi);
        dto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        dto.setReviewBoardApprovalStatusCode(null);
        dto.setReviewBoardApprovalDate(null);
        dto.setReviewBoardApprovalNumber(null);
        assertTrue(ISOUtil.isIiNull(dto.getIdentifier()));
        dto = remoteEjb.create(dto);
        assertFalse(ISOUtil.isIiNull(dto.getIdentifier()));
        trials = remoteEjb.getAllAssociatedTrials("1", StudySiteFunctionalCode.TREATING_SITE);
        assertFalse(trials.isEmpty());
        assertTrue(trials.contains(studyId));
    }

    @Test
    public void testGetAllAssociatedTrialsNotSupported() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Method does not currently support this functionalCode.");
        remoteEjb.getAllAssociatedTrials("1", StudySiteFunctionalCode.LEAD_ORGANIZATION);
    }

    @Test
    public void testGetAllAssociatedTrialsNull() throws Exception {
        assertTrue(remoteEjb.getAllAssociatedTrials(null, StudySiteFunctionalCode.TREATING_SITE).isEmpty());
    }

    @Test
    public void testGetTrialsAssciatedWithTreatingSite() throws Exception {
        Session sess = PaHibernateUtil.getCurrentSession();
        SQLQuery qry = sess.createSQLQuery("DELETE FROM study_site WHERE study_protocol_identifier = " + studyId);
        qry.executeUpdate();
        Set<Long> trials = remoteEjb.getTrialsAssociatedWithTreatingSite(1L);
        assertFalse(trials.contains(studyId));

        StudySiteDTO dto = createStudySite();
        dto.setOversightCommitteeIi(null);
        dto.setHealthcareFacilityIi(facilityIi);
        dto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        dto.setReviewBoardApprovalStatusCode(null);
        dto.setReviewBoardApprovalDate(null);
        dto.setReviewBoardApprovalNumber(null);
        assertTrue(ISOUtil.isIiNull(dto.getIdentifier()));
        dto = remoteEjb.create(dto);
        assertFalse(ISOUtil.isIiNull(dto.getIdentifier()));
        trials = remoteEjb.getTrialsAssociatedWithTreatingSite(1L);
        assertFalse(trials.isEmpty());
        assertTrue(trials.contains(studyId));
    }

    @Test
    public void testGetTrialsAssciatedWithTreatingSiteNull() throws Exception {
        assertTrue(remoteEjb.getTrialsAssociatedWithTreatingSite(null).isEmpty());
    }
}

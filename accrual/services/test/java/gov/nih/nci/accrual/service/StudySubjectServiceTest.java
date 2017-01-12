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

package gov.nih.nci.accrual.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.dto.PatientListDto;
import gov.nih.nci.accrual.dto.SearchSSPCriteriaDto;
import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.util.SubjectAccrualKey;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Patient;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

/**
 * @author Hugh Reinhart
 * @since Aug 29, 2009
 */
public class StudySubjectServiceTest extends AbstractServiceTest<StudySubjectServiceLocal> {

    @Override
    @Before
    public void instantiateServiceBean() throws Exception {
        bean = new StudySubjectBean();
    }

    @Test
    public void getIso() throws Exception {
        StudySubjectDto dto = bean.get(IiConverter.convertToIi(TestSchema.studySubjects.get(0).getId()));
        assertNotNull(dto);
        try {
            dto = bean.get(BII);
        } catch (PAException e) {
            // expected behavior
        }
    }

    @Test
    public void getBo() throws Exception {
        StudySubject bo = bean.get(TestSchema.studySubjects.get(0).getId());
        assertNotNull(bo);
        bo = bean.get(-1L);
        assertNull(bo);
    }

    @Test
    public void create() throws Exception {
        StudySubjectDto dto = new StudySubjectDto();
        dto.setPatientIdentifier(IiConverter.convertToIi(TestSchema.patients.get(1).getId()));
        dto.setPaymentMethodCode(CdConverter.convertToCd(PaymentMethodCode.MILITARY));
        dto.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.ACTIVE));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));
        dto.setStudySiteIdentifier(IiConverter.convertToIi(TestSchema.studySites.get(1).getId()));
        dto.setSubmissionTypeCode(CdConverter.convertToCd(AccrualSubmissionTypeCode.UI));
        StudySubjectDto r = bean.create(dto);
        assertNotNull(r);
        try {
	        bean.delete(r.getIdentifier());
	        fail();
	    } catch (Exception ex) {
	        // expected
	    }
    }
    @Test
    public void update() throws Exception {
        PaymentMethodCode newCode = PaymentMethodCode.MEDICARE_AND_PRIVATE;
        StudySubjectDto dto = bean.get(IiConverter.convertToIi(TestSchema.studySubjects.get(0).getId()));
        assertFalse(newCode.equals(PaymentMethodCode.getByCode(dto.getPaymentMethodCode().getCode())));
        dto.setPaymentMethodCode(CdConverter.convertToCd(newCode));
        bean.update(dto);
        assertTrue(newCode.equals(PaymentMethodCode.getByCode(dto.getPaymentMethodCode().getCode())));
    }
    
    @Test
    public void getByStudySite() throws Exception {
        List<StudySubjectDto> rList = bean.getByStudySite(IiConverter.convertToIi(TestSchema.studySites.get(1).getId()));
        assertTrue(0 < rList.size());
    }
    
    @Test
    public void getStudySubjects() throws Exception {
        Date birthDate = AccrualUtil.yearMonthStringToTimestamp("07/1963");
        List<StudySubjectDto> results = bean.getStudySubjects("", TestSchema.studySites.get(1).getId(), birthDate);
        assertEquals(1, results.size());
        
        results = bean.getStudySubjects("001", TestSchema.studySites.get(1).getId(), birthDate);
        assertEquals(1, results.size());
        
        results = bean.getStudySubjects("002", TestSchema.studySites.get(1).getId(), birthDate);
        assertEquals(0, results.size());
        
        results = bean.getStudySubjects("004", TestSchema.studySites.get(1).getId(), birthDate);
        assertEquals(0, results.size());
    }
    
    @Test
    public void searchStudySubjectByProtocolIdSiteId() throws PAException {
        List<StudySubject> result = ((StudySubjectBean)bean).searchStudySubject(TestSchema.studyProtocols.get(0).getId(),
                                                   TestSchema.studySites.get(1).getId(), null, null, null);
          assertEquals(2, result.size());
    }
    
    @Test
    public void searchPaganation() throws PAException {
        LimitOffset pagingParams = new LimitOffset(5, 1);
        List<StudySubject> result = ((StudySubjectBean)bean).searchStudySubject(TestSchema.studyProtocols.get(0).getId(),
                                                   TestSchema.studySites.get(1).getId(), null, null, pagingParams);
        assertEquals(1, result.size());
        assertEquals(TestSchema.studySubjects.get(1).getId(),
                     Long.valueOf(result.get(0).getId()));
    }
    
    @Test(expected = PAException.class)
    public void searchByIncorrectProtocolId() throws PAException {
        StudySubjectBeanLocal bean = mock(StudySubjectBeanLocal.class);
        StudyProtocolService studyProtocolService = mock(StudyProtocolService.class);
        when(studyProtocolService.getStudyProtocol(IiConverter.convertToStudyProtocolIi(Long.valueOf(999999999))))
            .thenReturn(null);
        when(bean.getStudyProtocolService()).thenReturn(studyProtocolService);
        
        Long studyId = Long.valueOf(999999999);
        doCallRealMethod().when(bean).search(studyId, null, null, null, null); 
        doCallRealMethod().when(bean).verifyStudyIdentifier(studyId);
        bean.search(studyId, null, null, null, null);
    }
    
    @Test
    public void searchStudySubjectByProtocolIdDates() throws PAException {
        List<StudySubject> result = ((StudySubjectBean) bean).searchStudySubject(TestSchema.studyProtocols.get(0)
            .getId(), null, PAUtil.dateStringToTimestamp("5/20/2009"), PAUtil.dateStringToTimestamp("5/22/2009"), null);
        assertEquals(1, result.size());
        assertEquals(TestSchema.studySubjects.get(0).getId(), Long.valueOf(result.get(0).getId()));
    }
    
    @Test
    public void calculateAccessibleStudySubjects() {
        List<StudySubject> studySubjects = createStudySubjectsForUserSearch();
        ((StudySubjectBean)bean).calculateAccessibleStudySubjects(studySubjects, 2L);
        assertEquals(3, studySubjects.size());
        List<Long> expectedList = new ArrayList<Long>(Arrays.asList(new Long[] {2L, 3L, 7L}));
        List<Long> actualList = new ArrayList<Long>();
        for (StudySubject studySubject :  studySubjects) {
            actualList.add(studySubject.getId());
        }        
        CollectionUtils.isEqualCollection(expectedList, actualList);        
    }

    @Test
    public void searchIso() throws PAException {
        StudySubjectBean bean = mock(StudySubjectBean.class);

        Long studyIdentifier = 2L;
        Long participatingSiteIdentifier = 3L;
        Timestamp startDate = new Timestamp(21212);
        Timestamp endDate = new Timestamp(322323);
        LimitOffset pagingParams = new LimitOffset(2, 4);
        List<StudySubject> studySubjects = new ArrayList<StudySubject>();
        List<StudySubjectDto> studySubjectDtos = new ArrayList<StudySubjectDto>();

        Long userId = 100L;

        when(bean.searchStudySubject(studyIdentifier, participatingSiteIdentifier, startDate, endDate, pagingParams))
            .thenReturn(studySubjects);
        when(bean.getUserId()).thenReturn(userId);
        when(bean.convertFromBoListToDtoList(studySubjects)).thenReturn(studySubjectDtos);

        doCallRealMethod().when(bean).search(studyIdentifier, participatingSiteIdentifier, startDate, endDate, pagingParams); 
        List<StudySubjectDto> result = bean.search(studyIdentifier, participatingSiteIdentifier, startDate, endDate, pagingParams);
        assertEquals(studySubjectDtos, result);
        
        InOrder inOrder = inOrder(bean);
        inOrder.verify(bean).verifyStudyIdentifier(studyIdentifier);
        inOrder.verify(bean).searchStudySubject(studyIdentifier, participatingSiteIdentifier, startDate, endDate, pagingParams);

        inOrder.verify(bean).getUserId();
        inOrder.verify(bean).calculateAccessibleStudySubjects(studySubjects, userId);
        inOrder.verify(bean).convertFromBoListToDtoList(studySubjects);
    }


    @Test
    public void searchBo() throws PAException {
        List<StudySubject> boList = bean.search(null);
        assertTrue(boList.isEmpty());
        SearchSSPCriteriaDto criteria = new SearchSSPCriteriaDto();
        boList = bean.search(criteria);
        assertTrue(boList.isEmpty());        
        criteria.setStudySiteIds(new ArrayList<Long>());
        criteria.getStudySiteIds().add(-1L);
        boList = bean.search(criteria);
        assertTrue(boList.isEmpty());
        criteria.getStudySiteIds().add(TestSchema.studySubjects.get(0).getStudySite().getId());
        assertEquals(2, criteria.getStudySiteIds().size());
        boList = bean.search(criteria);
        assertEquals(2, boList.size());
        criteria.setStudySubjectAssignedIdentifier("xyzzy");
        boList = bean.search(criteria);
        assertTrue(boList.isEmpty());
        criteria.setStudySubjectAssignedIdentifier(TestSchema.studySubjects.get(0).getAssignedIdentifier());
        boList = bean.search(criteria);
        assertEquals(1, boList.size());
        criteria.setPatientBirthDate("01/1801");
        boList = bean.search(criteria);
        assertTrue(boList.isEmpty());
        criteria.setPatientBirthDate(AccrualUtil.timestampToYearMonthString(TestSchema.patients.get(0).getBirthDate()));
        boList = bean.search(criteria);
        assertEquals(1, boList.size());
        criteria.setStudySubjectStatusCode(FunctionalRoleStatusCode.SUSPENDED);
        boList = bean.search(criteria);
        assertTrue(boList.isEmpty());
        criteria.setStudySubjectStatusCode(TestSchema.studySubjects.get(0).getStatusCode());
        boList = bean.search(criteria);
        assertEquals(1, boList.size());
    }

    private List<StudySubject> createStudySubjectsForUserSearch() {
        List<StudySubject> result = new ArrayList<StudySubject>();
        result.add(createStudySubjectForUserSearch(1L,1L) );
        result.add(createStudySubjectForUserSearch(2L,2L) );
        result.add(createStudySubjectForUserSearch(3L,2L) );
        result.add(createStudySubjectForUserSearch(4L,1L) );
        result.add(createStudySubjectForUserSearch(5L,1L) );
        result.add(createStudySubjectForUserSearch(6L,3L) );
        result.add(createStudySubjectForUserSearch(7L,2L) );
       return result;        
    }
    
    private StudySubject createStudySubjectForUserSearch(Long subjectId, Long userId) {
        StudySubject result = new StudySubject();
        result.setId(subjectId);
        StudySite site = new StudySite();
        List<StudySiteAccrualAccess> accesses = new ArrayList<StudySiteAccrualAccess>();
        StudySiteAccrualAccess access = new StudySiteAccrualAccess();
        RegistryUser registryUser = new RegistryUser();
        registryUser.setId(userId);
        access.setRegistryUser(registryUser);
        accesses.add(access);
        site.setStudySiteAccrualAccess(accesses);
        result.setStudySite(site);
        return result;
    }

    @Test
    public void studySiteExceptions() throws Exception {
        try {
            bean.getByStudySite(null);
            fail();
        } catch (PAException ex) {
            // expected
        }

        try {
            bean.getByStudyProtocol(null);
            fail();
        } catch (PAException ex) {
            // expected
        }

        Ii ii = IiConverter.convertToIi("test ii");
        try {
            bean.getByStudySite(ii);
            fail();
        } catch (Exception ex) {
            // expected
        }

        ii = IiConverter.convertToIi("100000");
        try {
            List<StudySubjectDto> ssd = bean.getByStudySite(ii);
            assertEquals(0, ssd.size());
        } catch (Exception ex) {
            // expected
        }

        try {
            bean.get((Ii) null);
            fail();
        } catch (PAException ex) {
            // expected
        }

        StudySubjectDto dto = new StudySubjectDto();

        try {
            bean.update(dto);
            fail();
        } catch (PAException ex) {
            // expected
        }

        try {
            ii = IiConverter.convertToStudySiteIi(Long.valueOf(1));
            dto.setIdentifier(ii);
            bean.create(dto);
            fail();
        } catch (PAException ex) {
            // expected
        }

        try {
            bean.delete(null);
            fail();
        } catch (PAException ex) {
            // expected
        }
    }

    @Test
    public void getSubjectAndPatientKeys() throws Exception {
        Long spId = TestSchema.studyProtocols.get(0).getId();
        StudySubject ss1 = TestSchema.studySubjects.get(0);
        StudySubject ss2 = TestSchema.studySubjects.get(1);
        SubjectAccrualKey key1 = new SubjectAccrualKey(ss1.getStudySite().getId(), ss1.getAssignedIdentifier());
        SubjectAccrualKey key2 = new SubjectAccrualKey(ss2.getStudySite().getId(), ss2.getAssignedIdentifier());
        Map<SubjectAccrualKey, Long[]> map = bean.getSubjectAndPatientKeys(spId, false);
        Long[] ids1 = map.get(key1);
        Long[] ids2 = map.get(key2);
        assertEquals(ss1.getId(), ids1[0]);
        assertEquals(ss1.getPatient().getId(), ids1[1]);
        assertEquals(ss2.getId(), ids2[0]);
        assertEquals(ss2.getPatient().getId(), ids2[1]);
        assertNull(map.get(new SubjectAccrualKey(-1L, "xyzzy")));
    }

    @Test(expected = PAException.class) 
    public void getSubjectAndPatientKeysException() throws Exception {
        bean.getSubjectAndPatientKeys(null, false);
    }
    
    @Test
    public void getBySubjectAccrualKey() throws Exception {
        StudySubject ss1 = TestSchema.studySubjects.get(0);
        StudySubject ss2 = TestSchema.studySubjects.get(1);
        SubjectAccrualKey key1 = new SubjectAccrualKey(ss1.getStudySite().getId(), ss1.getAssignedIdentifier());
        SubjectAccrualKey key2 = new SubjectAccrualKey(ss2.getStudySite().getId(), ss2.getAssignedIdentifier());
        assertEquals(ss1.getId(), bean.get(key1).getId());
        assertEquals(ss2.getId(), bean.get(key2).getId());
        assertNull(bean.get(new SubjectAccrualKey(-3L, "xyldksfj")));
        assertTrue(key1.equals(key1));
        assertFalse(key1.equals(null));
        assertFalse(key1.equals(new StudySubject()));
        assertFalse(key1.equals(key2));
        key1.setStudySiteId(null);
        key2.setAssignedIdentifier("001");
        assertFalse(key1.equals(key2));
        key1.setAssignedIdentifier(null);
        assertFalse(key1.equals(key2));
        key1.setAssignedIdentifier("001");
        key2.setAssignedIdentifier("001");
        key1.setStudySiteId(TestSchema.studySites.get(0).getId());
        assertFalse(key1.equals(key2));
    }

    @Test(expected = PAException.class) 
    public void getBySubjectAccrualKeyException() throws Exception {
        bean.get(new SubjectAccrualKey((Long) null, (String) null));
    }

    @Test
    public void searchFast() throws Exception {
        SearchSSPCriteriaDto criteria = new SearchSSPCriteriaDto();
        List<Long> ssids = new ArrayList<Long>();
        criteria.setStudySiteIds(ssids);
        
        // get none based on site ids
        ssids.add(-1L);
        assertTrue(bean.searchFast(criteria).isEmpty());

        // get all based on site ids
        ssids.add(TestSchema.studySites.get(1).getId());
        List<PatientListDto> l = bean.searchFast(criteria);
        assertFalse(l.isEmpty());
        assertEquals(2, l.size());

        // get based on different type birth dates
        Patient patient = TestSchema.studySubjects.get(1).getPatient();
        String yrMoStr = AccrualUtil.timestampToYearMonthString(patient.getBirthDate());
        // yearMonth
        criteria.setPatientBirthDate(yrMoStr);
        l = bean.searchFast(criteria);
        assertEquals(1, l.size());
        assertEquals(TestSchema.studySubjects.get(1).getAssignedIdentifier(), l.get(0).getAssignedIdentifier());
        criteria.setPatientBirthDate(null);

        // get none based on status
        criteria.setStudySubjectStatusCode(FunctionalRoleStatusCode.NULLIFIED);
        l = bean.searchFast(criteria);
        assertTrue(l.isEmpty());
        criteria.setStudySubjectStatusCode(FunctionalRoleStatusCode.ACTIVE);

        // get one based on subjectIdentifier
        StudySubject ss = TestSchema.studySubjects.get(0);
        criteria.setStudySubjectAssignedIdentifier(ss.getAssignedIdentifier());
        l = bean.searchFast(criteria);
        assertEquals(1, l.size());

        // test values pulling date from created
        assertNull(ss.getDateLastUpdated());
        PatientListDto p = l.get(0);
        assertEquals(ss.getAssignedIdentifier(), p.getAssignedIdentifier());
        assertEquals(ss.getDateLastCreated(), p.getDateLastUpdated());
        assertEquals(String.valueOf(ss.getId()), p.getIdentifier());
        assertEquals(ss.getStudySite().getHealthCareFacility().getOrganization().getName(), p.getOrganizationName());
        assertEquals(TestSchema.performedSubjectMilestones.get(0).getRegistrationDate(), p.getRegistrationDate());

        // test pulling date from updated
        ss = TestSchema.studySubjects.get(1);
        assertNotNull(ss.getDateLastUpdated());
        criteria.setStudySubjectAssignedIdentifier(ss.getAssignedIdentifier());
        l = bean.searchFast(criteria);
        assertEquals(1, l.size());
        assertEquals(ss.getDateLastUpdated(), l.get(0).getDateLastUpdated());
    }

    @Test
    public void searchFastInvalidCriteria() throws Exception {
        assertTrue(bean.searchFast(null).isEmpty());
        assertTrue(bean.searchFast(new SearchSSPCriteriaDto()).isEmpty());
    }
    
    @Test
    public void searchByStudyProtocolAndStatus() throws Exception {
    	assertNotNull(bean.searchActiveByStudyProtocol(TestSchema.studyProtocols.get(0).getId()));
    	assertNull(bean.searchActiveByStudyProtocol(TestSchema.studyProtocols.get(1).getId()));
    }
}

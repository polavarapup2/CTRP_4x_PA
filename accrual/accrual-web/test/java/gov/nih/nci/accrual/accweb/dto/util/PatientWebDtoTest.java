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
package gov.nih.nci.accrual.accweb.dto.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Patient;
import gov.nih.nci.pa.domain.PerformedActivity;
import gov.nih.nci.pa.domain.PerformedProcedure;
import gov.nih.nci.pa.domain.PerformedSubjectMilestone;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PatientEthnicityCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Rajani Kumar
 * @since Oct 6, 2009
 */
public class PatientWebDtoTest {
    PatientWebDto patientWebDto;

    @Before
    public void initDto() {
        patientWebDto = new PatientWebDto();
        patientWebDto.setAssignedIdentifier("assignedIdentifier");
        patientWebDto.setBirthDate(AccrualUtil.normalizeYearMonthString("197711"));
        patientWebDto.setCountryIdentifier(Long.valueOf(1));
        patientWebDto.setEthnicCode("ethnicCode");
        patientWebDto.setGenderCode("genderCode");
        patientWebDto.setIdentifier("identifier");
        patientWebDto.setOrganizationName("organizationName");
        patientWebDto.setPatientId(Long.valueOf(1));
        patientWebDto.setPaymentMethodCode("paymentMethodCode");
        patientWebDto.setPerformedSubjectMilestoneId(Long.valueOf(1));
        patientWebDto.setRegistrationDate("1/1/2009");
        patientWebDto.setRegistrationGroupId("registrationGroupId");
        patientWebDto.setStatusCode("statusCode");
        patientWebDto.setStudyProtocolId(Long.valueOf(1));
        patientWebDto.setStudySiteId(Long.valueOf(1));
        patientWebDto.setStudySubjectId(Long.valueOf(1));
        patientWebDto.setSubmissionTypeCode("submissionTypeCode");
        patientWebDto.setZip("zip");
    }
    
    @Test
    public void constructorTest() {
        // minimal data
        StudySubject ss = new StudySubject();
        ss.setId(123L);
        StudyProtocol sp = new StudyProtocol();
        sp.setId(3212L);
        ss.setStudyProtocol(sp);
        StudySite ssite = new StudySite();
        ssite.setId(942387L);
        ss.setStudySite(ssite);
        ss.setAssignedIdentifier("slkdfj");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setDateLastUpdated(new Timestamp(new Date().getTime()));
        Patient p = new Patient();
        Country country = new Country();
        country.setId(1423L);
        country.setName("fdsjklfsdjkljk");
        p.setCountry(country);
        ss.setPatient(p);
        PatientWebDto r = new PatientWebDto(ss);
        assertEquals(r.getAssignedIdentifier(), ss.getAssignedIdentifier());
        assertNull(r.getBirthDate());
        assertEquals(r.getCountryIdentifier(), country.getId());
        assertEquals(r.getCountryName(), country.getName());
        assertEquals(r.getDiseaseIdentifier(), null);
        assertEquals(r.getDiseasePreferredName(), null);
        assertEquals(r.getEthnicCode(), null);
        assertEquals(r.getGenderCode(), null);
        assertEquals(r.getDiseaseIdentifier(), null);
        assertEquals(r.getDiseasePreferredName(), null);
        assertEquals(r.getIdentifier(), ss.getId().toString());
        assertEquals(r.getOrganizationName(), null);
        assertEquals(r.getPaymentMethodCode(), null);
        assertEquals(r.getPerformedSubjectMilestoneId(), null);
        assertTrue(r.getRaceCode().isEmpty());
        assertEquals(r.getRegistrationDate(), null);
        assertEquals(r.getStatusCode(), ss.getStatusCode().getCode());
        assertEquals(r.getStudyProtocolId(), sp.getId());
        assertEquals(r.getStudySiteId(), ssite.getId());
        assertEquals(r.getStudySubjectId(), ss.getId());
        assertEquals(r.getZip(), null);

        // full data ICD9
        p.setBirthDate(new Timestamp(new Date().getTime()));
        AccrualDisease icd9 = new AccrualDisease();
        icd9.setId(87987L);
        icd9.setPreferredName("adfasdf");
        icd9.setDiseaseCode("jklfds");
        ss.setDisease(icd9);
        
        AccrualDisease icdo3 = new AccrualDisease();
        icdo3.setId(87988L);
        icdo3.setPreferredName("icdo3adfasdf");
        icdo3.setDiseaseCode("icdo3jklfds");
        ss.setSiteDisease(icdo3);
        
        p.setEthnicCode(PatientEthnicityCode.NOT_HISPANIC);
        p.setSexCode(PatientGenderCode.FEMALE);
        HealthCareFacility hcf = new HealthCareFacility();
        Organization org = new Organization();
        org.setName("lkjdsflfjd");
        hcf.setOrganization(org);
        ssite.setHealthCareFacility(hcf);
        ss.setPaymentMethodCode(PaymentMethodCode.MEDICAID);
        List<PerformedActivity> paList = new ArrayList<PerformedActivity>();
        paList.add(new PerformedProcedure());
        PerformedSubjectMilestone psm = new PerformedSubjectMilestone();
        psm.setId(54798L);
        paList.add(psm);
        ss.setPerformedActivities(paList);
        p.setRaceCode("WHITE,ASIAN");
        p.setZip("12345");
        
        r = new PatientWebDto(ss);
        
        assertEquals(r.getBirthDate(), AccrualUtil.normalizeYearMonthString(p.getBirthDate().toString()));
        assertEquals(r.getDiseaseIdentifier(), icd9.getId());
        assertEquals(r.getDiseasePreferredName(), icd9.getPreferredName());
        assertEquals(r.getEthnicCode(), p.getEthnicCode().getCode());
        assertEquals(r.getGenderCode(), p.getSexCode().getCode());
        assertEquals(r.getDiseaseIdentifier(), icd9.getId());
        assertEquals(r.getDiseasePreferredName(),icd9.getPreferredName());
        assertEquals(r.getOrganizationName(), org.getName());
        assertEquals(r.getPaymentMethodCode(), ss.getPaymentMethodCode().getCode());
        assertEquals(r.getPerformedSubjectMilestoneId(), psm.getId());
        assertEquals(r.getRaceCode().toString(), "[White, Asian]");
        assertEquals(r.getRegistrationDate(), null);
        assertEquals(r.getZip(), p.getZip());
        assertNotNull(r.getDateLastUpdated());
    }

    @Test
    public void assignedIdentifierPropertyTest() {
        assertNotNull(patientWebDto.getAssignedIdentifier());
    }

    @Test
    public void birthDatePropertyTest() {
        assertEquals("11/1977", patientWebDto.getBirthDate());
    }

    @Test
    public void countryIdentifierPropertyTest() {
        assertNotNull(patientWebDto.getCountryIdentifier());
    }      

    @Test
    public void ethnicCodePropertyTest() {
        assertNotNull(patientWebDto.getEthnicCode());
    }

    @Test
    public void genderCodePropertyTest() {
        assertNotNull(patientWebDto.getGenderCode());
    }

    @Test
    public void identifierPropertyTest() {
        assertNotNull(patientWebDto.getIdentifier());
    }

    @Test
    public void patientIdPropertyTest() {
        assertNotNull(patientWebDto.getPatientId());
    }

    @Test
    public void paymentMethodCodePropertyTest() {
        assertNotNull(patientWebDto.getPaymentMethodCode());
    }

    @Test
    public void performedSubjectMilestoneIdPropertyTest() {
        assertNotNull(patientWebDto.getPerformedSubjectMilestoneId());
    }

    @Test
    public void registrationDatePropertyTest() {
        assertNotNull(patientWebDto.getRegistrationDate());
    }

    @Test
    public void statusCodePropertyTest() {
        assertNotNull(patientWebDto.getStatusCode());
    }

    @Test
    public void studyProtocolIdPropertyTest() {
        assertNotNull(patientWebDto.getStudyProtocolId());
    }

    @Test
    public void studySiteIdPropertyTest() {
        assertNotNull(patientWebDto.getStudySiteId());
    }

    @Test
    public void studySubjectIdPropertyTest() {
        assertNotNull(patientWebDto.getStudySubjectId());
    }

    @Test
    public void zipPropertyTest() {
        assertNotNull(patientWebDto.getZip());
    }

    @Test
    public void countryNameTest() {
        assertNull(patientWebDto.getCountryName());
    }

    @Test
    public void organizationNamePropertyTest() {
        assertNotNull(patientWebDto.getOrganizationName());
    }
    
    @Test
    public void diseasePreferredNameTest() {
        patientWebDto.setDiseasePreferredName("name");
        assertEquals("name", patientWebDto.getDiseasePreferredName());       
    }
    
    @Test
    public void diseaseIdentifierTest() {
        patientWebDto.setDiseaseIdentifier(1L);
        assertEquals((Long) 1L, patientWebDto.getDiseaseIdentifier());        
    }
}

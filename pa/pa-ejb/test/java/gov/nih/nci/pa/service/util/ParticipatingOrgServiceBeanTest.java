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
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Hugh Reinhart
 * @since Jun 20, 2012
 */
public class ParticipatingOrgServiceBeanTest extends AbstractHibernateTestCase {

    private ParticipatingOrgServiceBean bean;
    private StudySiteAccrualStatusServiceLocal ssas;
    private PAHealthCareProviderLocal paHcp;

    @Before
    public void setup() throws Exception {
        bean = new ParticipatingOrgServiceBean();
        ssas = mock(StudySiteAccrualStatusServiceLocal.class);
        when(ssas.getCurrentStudySiteAccrualStatus(any(Long[].class))).thenReturn(
                new HashMap<Long, StudySiteAccrualStatus>());
        paHcp = mock(PAHealthCareProviderLocal.class);
        when(paHcp.getPersonsByStudySiteId(any(Long[].class), any(String.class))).thenReturn(
                new HashMap<Long, List<PaPersonDTO>>());
        bean.setStudySiteAccrualStatusService(ssas);
        bean.setPaHealthCareProviderService(paHcp);
        
        TestSchema.primeData();
    }

    @Test
    public void getTreatingSitesNoData() throws Exception {
        List<ParticipatingOrgDTO> rList = bean.getTreatingSites(-1L);
        assertTrue(rList.isEmpty());
        rList = bean.getTreatingSites(null);
        assertTrue(rList.isEmpty());
    }

    @Test
    public void getTreatingSites() throws Exception {
        
        Long spId = TestSchema.studyProtocolIds.get(0);
        Long ssId = TestSchema.studySiteIds.get(0);
        Map<Long, StudySiteAccrualStatus> rMap = new HashMap<Long, StudySiteAccrualStatus>();
        StudySiteAccrualStatus r = new StudySiteAccrualStatus();
        StudySite ss = new StudySite();
        ss.setId(ssId);
        r.setStudySite(ss);
        r.setStatusCode(RecruitmentStatusCode.ACTIVE);
        r.setStatusDate(new Timestamp((new Date()).getTime()));
        rMap.put(ssId, r);
        when(ssas.getCurrentStudySiteAccrualStatus(any(Long[].class))).thenReturn(rMap);

        Map<Long, List<PaPersonDTO>> pimap = new HashMap<Long, List<PaPersonDTO>>();
        List<PaPersonDTO> pis = new ArrayList<PaPersonDTO>();
        pis.add(new PaPersonDTO());
        pimap.put(ssId, pis);
        when(paHcp.getPersonsByStudySiteId(any(Long[].class),
                eq(StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getName()))).thenReturn(pimap);

        List<ParticipatingOrgDTO> rList = bean.getTreatingSites(spId);
        assertEquals(1, rList.size());

        StudySite hdbSs = (StudySite) PaHibernateUtil.getCurrentSession().get(StudySite.class, ssId);
        assertEquals(hdbSs.getId(), rList.get(0).getStudySiteId());
        assertEquals(hdbSs.getStatusCode(), rList.get(0).getStatusCode());
        assertEquals(hdbSs.getTargetAccrualNumber(), rList.get(0).getTargetAccrualNumber());
        assertEquals(hdbSs.getProgramCodeText(), rList.get(0).getProgramCodeText());
        assertEquals(hdbSs.getHealthCareFacility().getOrganization().getName(), rList.get(0).getName());
        assertEquals(hdbSs.getHealthCareFacility().getOrganization().getIdentifier(), rList.get(0).getPoId());
        assertEquals(r.getStatusCode(), rList.get(0).getRecruitmentStatus());
        assertEquals(r.getStatusDate(), rList.get(0).getRecruitmentStatusDate());
        assertEquals(0, rList.get(0).getPrimaryContacts().size());
        assertEquals(1, rList.get(0).getPrincipalInvestigators().size());
        assertEquals(0, rList.get(0).getSubInvestigators().size());
        
    }
    
    
    @Test
    public void getTreatingSitesSortOrder() throws Exception {
        StudyProtocol sp =
                (StudyProtocol) getCurrentSession().get(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        StudySite ss = new StudySite();
        ss.setStudyProtocol(sp);
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        HealthCareFacility hf = new HealthCareFacility();
        hf.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        hf.setIdentifier("hf_po_id");
        ss.setHealthCareFacility(hf);
        ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        Organization org = new Organization();
        org.setStatusCode(EntityStatusCode.ACTIVE);
        hf.setOrganization(org);
        org.setName("aaa");
        TestSchema.addUpdObject(org);
        TestSchema.addUpdObject(hf);
        TestSchema.addUpdObject(ss);

        List<ParticipatingOrgDTO> rList = bean.getTreatingSites(sp.getId());
        assertEquals(2, rList.size());
        assertEquals(org.getName(), rList.get(0).getName());

        org.setName("zzz");
        TestSchema.addUpdObject(org);
        rList = bean.getTreatingSites(sp.getId());
        assertEquals(2, rList.size());
        assertEquals(org.getName(), rList.get(1).getName());

        org.setName("AAA");
        TestSchema.addUpdObject(org);
        rList = bean.getTreatingSites(sp.getId());
        assertEquals(2, rList.size());
        assertEquals(org.getName(), rList.get(0).getName());

        org.setName("ZZZ");
        TestSchema.addUpdObject(org);
        rList = bean.getTreatingSites(sp.getId());
        assertEquals(2, rList.size());
        assertEquals(org.getName(), rList.get(1).getName());
    }

    @Test
    public void getTreatingSite() throws Exception {
        Long spId = TestSchema.studyProtocolIds.get(0);
        Long ssId = TestSchema.studySiteIds.get(0);
        Map<Long, StudySiteAccrualStatus> rMap = new HashMap<Long, StudySiteAccrualStatus>();
        StudySiteAccrualStatus r = new StudySiteAccrualStatus();
        StudySite ss = new StudySite();
        ss.setId(ssId);
        r.setStudySite(ss);
        r.setStatusCode(RecruitmentStatusCode.ACTIVE);
        r.setStatusDate(new Timestamp((new Date()).getTime()));
        rMap.put(ssId, r);
        when(ssas.getCurrentStudySiteAccrualStatus(any(Long[].class))).thenReturn(rMap);

        Map<Long, List<PaPersonDTO>> pimap = new HashMap<Long, List<PaPersonDTO>>();
        List<PaPersonDTO> pis = new ArrayList<PaPersonDTO>();
        pis.add(new PaPersonDTO());
        pimap.put(ssId, pis);
        when(paHcp.getPersonsByStudySiteId(any(Long[].class),
                eq(StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getName()))).thenReturn(pimap);

        ParticipatingOrgDTO orgDTO = bean.getTreatingSite(ssId);
        assertNotNull(orgDTO);

        StudySite hdbSs = (StudySite) PaHibernateUtil.getCurrentSession().get(StudySite.class, ssId);
        assertEquals(hdbSs.getId(), orgDTO.getStudySiteId());
        assertEquals(hdbSs.getStatusCode(), orgDTO.getStatusCode());
        assertEquals(hdbSs.getTargetAccrualNumber(), orgDTO.getTargetAccrualNumber());
        assertEquals(hdbSs.getProgramCodeText(), orgDTO.getProgramCodeText());
        assertEquals(hdbSs.getHealthCareFacility().getOrganization().getName(), orgDTO.getName());
        assertEquals(hdbSs.getHealthCareFacility().getOrganization().getIdentifier(), orgDTO.getPoId());
        assertEquals(r.getStatusCode(), orgDTO.getRecruitmentStatus());
        assertEquals(r.getStatusDate(), orgDTO.getRecruitmentStatusDate());
        assertEquals(0, orgDTO.getPrimaryContacts().size());
        assertEquals(1, orgDTO.getPrincipalInvestigators().size());
        assertEquals(0, orgDTO.getSubInvestigators().size());
    }
    
    @Test
    public void getOrganizationsThatAreNotSiteYet() throws Exception {
        StudyProtocol sp =
                (StudyProtocol) getCurrentSession().get(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        StudySite ss = new StudySite();
        ss.setStudyProtocol(sp);
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        HealthCareFacility hf = new HealthCareFacility();
        hf.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        hf.setIdentifier("hf_po_id");
        ss.setHealthCareFacility(hf);
        ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        Organization org = new Organization();
        org.setIdentifier("1234567890");        
        org.setStatusCode(EntityStatusCode.ACTIVE);
        hf.setOrganization(org);
        org.setName("aaa");
        TestSchema.addUpdObject(org);
        TestSchema.addUpdObject(hf);
        TestSchema.addUpdObject(ss);
        
        OrganizationDTO orgDTO1 = new OrganizationDTO();
        orgDTO1.setIdentifier(IiConverter.convertToIi("1234567890"));
        OrganizationDTO orgDTO2 = new OrganizationDTO();
        orgDTO2.setIdentifier(IiConverter.convertToIi("48239084"));

        Collection<OrganizationDTO> rList = bean
                .getOrganizationsThatAreNotSiteYet(sp.getId(),
                        Arrays.asList(orgDTO1, orgDTO2));
        assertEquals(1, rList.size());
        assertEquals("48239084", rList.iterator().next().getIdentifier()
                .getExtension());
    }

}

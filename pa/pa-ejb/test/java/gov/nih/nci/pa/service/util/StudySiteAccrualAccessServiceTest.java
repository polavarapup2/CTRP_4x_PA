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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyAccrualAccess;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.dto.AccrualAccessAssignmentByTrialDTO;
import gov.nih.nci.pa.dto.AccrualAccessAssignmentHistoryDTO;
import gov.nih.nci.pa.dto.AccrualSubmissionAccessDTO;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.AssignmentActionCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualAccessDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteAccrualStatusBeanLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceBean;
import gov.nih.nci.pa.util.AbstractEjbTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import net.sf.ehcache.Cache;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author Hugh Reinhart
 * @since Sep 3, 2009
 */
public class StudySiteAccrualAccessServiceTest extends AbstractEjbTestCase {
    private static final String REQUEST_DETAILS = "request details";
    private Long ssId;
    private Long spId;
    private static Ii REGISTRY_USER_IDENTIFIER;
    private Ii identifier;

    StudySiteAccrualAccessServiceBean bean;
    StudySiteAccrualStatusBeanLocal statusBean;
    ParticipatingOrgServiceBean participatingOrgServiceLocal;

    @Before
    public void setUp() throws Exception {     
        TestSchema.primeData();
        ssId = TestSchema.studySiteIds.get(0);
        spId = TestSchema.studyProtocolIds.get(0);
        REGISTRY_USER_IDENTIFIER = IiConverter.convertToIi(TestSchema.registryUserIds.get(0));
        statusBean = (StudySiteAccrualStatusBeanLocal) getEjbBean(StudySiteAccrualStatusServiceBean.class);        
        participatingOrgServiceLocal = (ParticipatingOrgServiceBean) getEjbBean(ParticipatingOrgServiceBean.class);
        this.bean = (StudySiteAccrualAccessServiceBean) getEjbBean(StudySiteAccrualAccessServiceBean.class);
        Method m = ReflectionUtils.findMethod(StudySiteAccrualAccessServiceBean.class, "getIndTrialCache");
        ReflectionUtils.makeAccessible(m);
        Cache cache = (Cache) ReflectionUtils.invokeMethod(m, null);
        cache.removeAll();
        
        UsernameHolder
        .setUserCaseSensitive("/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SuAbstractor");
     }

    @Test
    public void create() throws Exception {
        StudySiteAccrualAccessDTO dto = new StudySiteAccrualAccessDTO();
        dto.setRegistryUserIdentifier(REGISTRY_USER_IDENTIFIER);
        dto.setRequestDetails(StConverter.convertToSt(REQUEST_DETAILS));
        dto.setStudySiteIdentifier(IiConverter.convertToIi(ssId));
        dto.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
        dto.setSource(CdConverter.convertToCd(AccrualAccessSourceCode.PA_SITE_REQUEST));
        assertNull(dto.getIdentifier());      
        StudySiteAccrualAccessDTO r = bean.create(dto);
        assertNotNull(r);
        identifier = r.getIdentifier();
    }

    @Test
    public void get() throws Exception {
        create();
        StudySiteAccrualAccessDTO r = bean.get(identifier);
        assertEquals(REGISTRY_USER_IDENTIFIER, r.getRegistryUserIdentifier());
        assertEquals(ssId, IiConverter.convertToLong(r.getStudySiteIdentifier()));
        assertEquals(REQUEST_DETAILS, StConverter.convertToString(r.getRequestDetails()));
    }

    @Test
    public void getByStudyProtocol() throws Exception {
        create();
        List<StudySiteAccrualAccessDTO> rList = bean.getByStudyProtocol(spId);
        assertEquals(1, rList.size());
        assertEquals(identifier, rList.get(0).getIdentifier());
        assertEquals(ssId, new Long(rList.get(0).getStudySiteIdentifier().getExtension()));
    }

    @Test
    public void getByStudySite() throws Exception {
        create();
        List<StudySiteAccrualAccessDTO> rList = bean.getByStudyProtocol(ssId);
        assertEquals(1, rList.size());
        assertEquals(identifier, rList.get(0).getIdentifier());
        assertEquals(ssId, new Long(rList.get(0).getStudySiteIdentifier().getExtension()));
    }

    @Test
    public void update() throws Exception {
        St updatedRequestDetails = StConverter.convertToSt("xxx");
        create();
        StudySiteAccrualAccessDTO dto = bean.get(identifier);
        assertFalse(dto.getRequestDetails().equals(updatedRequestDetails));
        dto.setRequestDetails(updatedRequestDetails);
        StudySiteAccrualAccessDTO r = bean.update(dto);
        assertTrue(r.getRequestDetails().equals(updatedRequestDetails));
    }
    
    @Test
    public void testNullChecks() {
    	StudySiteAccrualAccessDTO  dto = new StudySiteAccrualAccessDTO();
    	Ii ii = new Ii();
    	ii.setExtension("1");
    	dto.setIdentifier(ii);
    	try {
			bean.create(dto);
            fail();
		} catch (PAException e) {
			assertEquals("Id is not null when calling StudySiteAccrualAccess.create().", e.getMessage());
		}
    	dto.setIdentifier(null);
    	try {
			bean.update(dto);
            fail();
		} catch (PAException e) {
			assertEquals("Id is null when calling StudySiteAccrualAccess.update().", e.getMessage());
		}
    }
    
    @Test
    public void testUncoveredLines() throws Exception {
    	assertNotNull(bean.getByStudySite(ssId));
    	assertNotNull(bean.getSubmitters());
    	Session session = PaHibernateUtil.getCurrentSession();
    	RegistryUser user = (RegistryUser) session.get(RegistryUser.class,
                TestSchema.registryUserIds.get(0));
    	assertNotNull(bean.getFullName(user));
    }

    @Test
    public void createOnlyForAccruingSites() throws Exception {
        assertTrue(RecruitmentStatusCode.WITHDRAWN.isEligibleForAccrual());
        assertTrue(RecruitmentStatusCode.APPROVED.isEligibleForAccrual());
        StudySiteAccrualStatusDTO dto = new StudySiteAccrualStatusDTO();
        dto.setStudySiteIi(IiConverter.convertToStudySiteIi(ssId));
        dto.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.WITHDRAWN));
        dto.setStatusDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        statusBean.createStudySiteAccrualStatus(dto);
        try {
            create();
        } catch (PAException e) {
            // expected behavior
        }
    }

    @Test
    public void testGetTreatingSites() throws Exception {
        assertNotNull(bean.getTreatingSites(spId));
        
        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        StudySite site = TestSchema.createParticipatingSite(sp);
        StudySiteAccrualStatus studySiteAccrualStatus = site.getStudySiteAccrualStatuses().get(0);
        studySiteAccrualStatus.setStatusCode(RecruitmentStatusCode.IN_REVIEW);
        TestSchema.addUpdObject(studySiteAccrualStatus);
        
        assertTrue(bean.getTreatingSites(sp.getId()).size() == 0);
        
        studySiteAccrualStatus.setStatusCode(RecruitmentStatusCode.ACTIVE);
        TestSchema.addUpdObject(studySiteAccrualStatus);
        
        assertTrue(bean.getTreatingSites(sp.getId()).size() == 1);
        
    }
    
    @Test
    public void getAccrualSubmissionAccess() throws Exception {
        create();
        StudySiteAccrualAccessDTO r = bean.get(identifier);
        assertEquals(REGISTRY_USER_IDENTIFIER, r.getRegistryUserIdentifier());
        RegistryUser user = new RegistryUser();
        user.setId(TestSchema.registryUserIds.get(0));
        List<AccrualSubmissionAccessDTO> list = bean
                .getAccrualSubmissionAccess(user);
        assertEquals(1, list.size());
        AccrualSubmissionAccessDTO dto = list.get(0);
        assertEquals("Mayo University", dto.getParticipatingSiteOrgName());
        assertEquals("1", dto.getParticipatingSitePoOrgId());
        assertEquals(new Long(1), dto.getTrialId());
        assertEquals("NCI-2009-00001", dto.getTrialNciId());
        assertEquals("cancer for THOLA", dto.getTrialTitle());
    }
    
    @Test
    public void getActiveTrialLevelAccrualAccess() throws Exception {
        RegistryUser user = new RegistryUser();
        user.setId(TestSchema.registryUserIds.get(0));
        List<Long> list = bean.getActiveTrialLevelAccrualAccess(user);
        assertEquals(1, list.size());
        assertEquals(TestSchema.studyProtocolIds.get(0), list.get(0));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void assignTrialLevelAccrualAccess() throws Exception {

        Session session = PaHibernateUtil.getCurrentSession();

        RegistryUser user = (RegistryUser) session.get(RegistryUser.class,
                TestSchema.registryUserIds.get(0));
        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        StudySite site = TestSchema.createParticipatingSite(sp);
        StudyRecruitmentStatus recruitmentStatus = TestSchema
                .createStudyRecruitmentStatus(sp);
        TestSchema.addUpdObject(recruitmentStatus);

        bean.assignTrialLevelAccrualAccess(user, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE, 
                Arrays.asList(sp.getId()), "TEST", user);

        // Make sure study level access has been provisioned with correct
        // values.
        List<StudyAccrualAccess> trialLevelAccessList = session.createQuery(
                "from " + StudyAccrualAccess.class.getName()
                        + " saa where saa.registryUser.id = " + user.getId()
                        + " and studyProtocol.id = " + sp.getId()).list();
        assertEquals(1, trialLevelAccessList.size());
        StudyAccrualAccess saa = trialLevelAccessList.get(0);
        assertEquals(saa.getActionCode(), AssignmentActionCode.ASSIGNED);
        assertEquals(saa.getComments(), "TEST");
        assertTrue(DateUtils.isSameDay(saa.getDateLastCreated(), new Date()));
        assertEquals(saa.getRegistryUser().getId(), user.getId());
        assertEquals(saa.getStatusCode(), ActiveInactiveCode.ACTIVE);
        assertTrue(DateUtils.isSameDay(saa.getStatusDateRangeLow(), new Date()));
        assertEquals(saa.getStudyProtocol().getId(), sp.getId());
        assertEquals(saa.getUserLastCreated().getUserId(), user.getCsmUser()
                .getUserId());
        assertEquals(AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE, saa.getSource());

        // Make sure site-level access has been provisioned automatically as well.
        List<StudySiteAccrualAccess> siteAccessList = session.createQuery(
                "from " + StudySiteAccrualAccess.class.getName()
                        + " saa where saa.registryUser.id = " + user.getId()
                        + " and studySite.id = " + site.getId()).list();        

        assertEquals(1, siteAccessList.size());
        StudySiteAccrualAccess ssaa = siteAccessList.get(0);
        assertEquals(AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE, ssaa.getSource());
        assertEquals(ssaa.getStatusCode(), ActiveInactiveCode.ACTIVE);
        assertEquals(ssaa.getUserLastCreated().getUserId(), CSMUserService
                .getInstance().getCSMUser(UsernameHolder.getUser()).getUserId());

    
    }
    @Test
    public void assignTrialLevelAccrualAccessNullCreator() throws Exception {
        Session session = PaHibernateUtil.getCurrentSession();
        RegistryUser user = (RegistryUser) session.get(RegistryUser.class,
                TestSchema.registryUserIds.get(0));
        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        TestSchema.createParticipatingSite(sp);
        StudyRecruitmentStatus recruitmentStatus = TestSchema
                .createStudyRecruitmentStatus(sp);
        TestSchema.addUpdObject(recruitmentStatus);

        bean.assignTrialLevelAccrualAccess(user, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE, 
                Arrays.asList(sp.getId()), "TEST", null);
        bean.assignTrialLevelAccrualAccess(user, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE, 
                Arrays.asList(sp.getId()), "TEST", new RegistryUser());
    }

    @SuppressWarnings("unchecked")
//    @Test
    public void assignTrialLevelAccrualAccessInactiveToActive() throws Exception {

        Session session = PaHibernateUtil.getCurrentSession();

        RegistryUser user = (RegistryUser) session.get(RegistryUser.class,
                TestSchema.registryUserIds.get(0));
        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        StudySite site = TestSchema.createParticipatingSite(sp);
        StudyRecruitmentStatus recruitmentStatus = TestSchema
                .createStudyRecruitmentStatus(sp);
        TestSchema.addUpdObject(recruitmentStatus);
        
        StudySiteAccrualAccess inactive = new StudySiteAccrualAccess();        
        inactive.setDateLastCreated(new Date());
        inactive.setRegistryUser(user);
        inactive.setSource(AccrualAccessSourceCode.REG_ADMIN_PROVIDED);
        inactive.setStatusCode(ActiveInactiveCode.INACTIVE);
        inactive.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        inactive.setStudySite(site);
        TestSchema.addUpdObject(inactive);
        
        
        bean.assignTrialLevelAccrualAccess(user, AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE, 
                Arrays.asList(sp.getId()), "TEST", user);
        
        // Make sure site-level access has been provisioned automatically as well.
        List<StudySiteAccrualAccess> siteAccessList = session.createQuery(
                "from " + StudySiteAccrualAccess.class.getName()
                        + " saa where saa.registryUser.id = " + user.getId()
                        + " and studySite.id = " + site.getId()).list();        

        assertEquals(1, siteAccessList.size());
        StudySiteAccrualAccess ssaa = siteAccessList.get(0);
        assertEquals(AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE, ssaa.getSource());
        assertEquals(ssaa.getStatusCode(), ActiveInactiveCode.ACTIVE);
        assertEquals(ssaa.getId(), inactive.getId());
    }
    

    @SuppressWarnings("unchecked")
    @Test
    public void assignTrialLevelAccrualAccessIneligibleForAccrual() throws Exception {

        Session session = PaHibernateUtil.getCurrentSession();

        RegistryUser user = (RegistryUser) session.get(RegistryUser.class,
                TestSchema.registryUserIds.get(0));
        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        
        StudySite site = TestSchema.createParticipatingSite(sp);
        final StudySiteAccrualStatus studySiteAccrualStatus = site.getStudySiteAccrualStatuses().get(0);
        studySiteAccrualStatus.setStatusCode(RecruitmentStatusCode.IN_REVIEW);
        TestSchema.addUpdObject(studySiteAccrualStatus);
        
        StudyRecruitmentStatus recruitmentStatus = TestSchema
                .createStudyRecruitmentStatus(sp);
        recruitmentStatus.setStatusCode(RecruitmentStatusCode.IN_REVIEW);
        TestSchema.addUpdObject(recruitmentStatus);

        bean.assignTrialLevelAccrualAccess(user, AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE,
                Arrays.asList(sp.getId()), "TEST", user);

        // Make sure study level access has been provisioned with correct
        // values.
        List<StudyAccrualAccess> trialLevelAccessList = session.createQuery(
                "from " + StudyAccrualAccess.class.getName()
                        + " saa where saa.registryUser.id = " + user.getId()
                        + " and studyProtocol.id = " + sp.getId()).list();
        assertEquals(1, trialLevelAccessList.size());
        
        // Make sure no site-level access has been provisioned automatically because not eligible for accrual yet.
        List<StudySiteAccrualAccess> siteAccessList = session.createQuery(
                "from " + StudySiteAccrualAccess.class.getName()
                        + " saa where saa.registryUser.id = " + user.getId()
                        + " and studySite.id = " + site.getId()).list();        

        assertEquals(0, siteAccessList.size());
    }
    

    @SuppressWarnings("unchecked")
    @Test
    public void synchronizeSiteAccrualAccess() throws Exception {

        Session session = PaHibernateUtil.getCurrentSession();

        RegistryUser user = (RegistryUser) session.get(RegistryUser.class,
                TestSchema.registryUserIds.get(0));
        StudyProtocol sp = TestSchema.createStudyProtocolObj();        
        StudyRecruitmentStatus recruitmentStatus = TestSchema
                .createStudyRecruitmentStatus(sp);
        TestSchema.addUpdObject(recruitmentStatus);

        bean.assignTrialLevelAccrualAccess(user, AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE,
                Arrays.asList(sp.getId()), "TEST", user);

        // Make sure study level access has been provisioned with correct
        // values.
        List<StudyAccrualAccess> trialLevelAccessList = session.createQuery(
                "from " + StudyAccrualAccess.class.getName()
                        + " saa where saa.registryUser.id = " + user.getId()
                        + " and studyProtocol.id = " + sp.getId()).list();
        assertEquals(1, trialLevelAccessList.size());
        
        // Now create a site
        StudySite site = TestSchema.createParticipatingSite(sp);
        
        // Make sure no site-level access yet.
        final String queryString = "from " + StudySiteAccrualAccess.class.getName()
                + " saa where saa.registryUser.id = " + user.getId()
                + " and studySite.id = " + site.getId();
        List<StudySiteAccrualAccess> siteAccessList = session.createQuery(
                queryString).list();        
        assertEquals(0, siteAccessList.size());
        
        // Now synchronize.
        bean.synchronizeSiteAccrualAccess(sp.getId(), user);
        
        // Make sure site-level access has been provisioned.
        siteAccessList = session.createQuery(
                queryString).list();        

        assertEquals(1, siteAccessList.size());
        StudySiteAccrualAccess ssaa = siteAccessList.get(0);
        assertEquals(AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE, ssaa.getSource());
        assertEquals(ssaa.getStatusCode(), ActiveInactiveCode.ACTIVE);
        assertEquals(ssaa.getUserLastCreated().getUserId(), CSMUserService
                .getInstance().getCSMUser(UsernameHolder.getUser())
                .getUserId());
    }    

    @SuppressWarnings("unchecked")
    @Test
    public void unassignTrialLevelAccrualAccess() throws Exception {

        Session session = PaHibernateUtil.getCurrentSession();

        RegistryUser user = (RegistryUser) session.get(RegistryUser.class,
                TestSchema.registryUserIds.get(0));
        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        StudySite site = TestSchema.createParticipatingSite(sp);
        StudyRecruitmentStatus recruitmentStatus = TestSchema
                .createStudyRecruitmentStatus(sp);
        TestSchema.addUpdObject(recruitmentStatus);

        bean.assignTrialLevelAccrualAccess(user, AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE, 
                Arrays.asList(sp.getId()), "TEST", user);

        // Make sure study level access has been provisioned with correct
        // values.
        List<StudyAccrualAccess> trialLevelAccessList = session.createQuery(
                "from " + StudyAccrualAccess.class.getName()
                        + " saa where saa.registryUser.id = " + user.getId()
                        + " and studyProtocol.id = " + sp.getId()).list();
        assertEquals(1, trialLevelAccessList.size());
        
        // Make sure site-level access has been provisioned automatically as well.
        List<StudySiteAccrualAccess> siteAccessList = session.createQuery(
                "from " + StudySiteAccrualAccess.class.getName()
                        + " saa where saa.registryUser.id = " + user.getId()
                        + " and studySite.id = " + site.getId()).list();        

        assertEquals(1, siteAccessList.size());
        StudySiteAccrualAccess ssaa = siteAccessList.get(0);
        assertEquals(ssaa.getStatusCode(), ActiveInactiveCode.ACTIVE);
        assertEquals(ssaa.getSource(), AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE);
        
        // now un-assign.
        bean.unassignTrialLevelAccrualAccess(user, AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE,
                Arrays.asList(sp.getId()), "TEST", user);
        trialLevelAccessList = session.createQuery(
                "from " + StudyAccrualAccess.class.getName()
                        + " saa where saa.registryUser.id = " + user.getId()
                        + " and studyProtocol.id = " + sp.getId()).list();
        
        assertEquals(2, trialLevelAccessList.size());
        
        // First one is the one that's cancelled
        StudyAccrualAccess saa1 = trialLevelAccessList.get(0);
        assertEquals(saa1.getActionCode(), AssignmentActionCode.ASSIGNED);
        assertEquals(saa1.getStatusCode(), ActiveInactiveCode.INACTIVE);
        assertEquals(AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE, saa1.getSource());

        StudyAccrualAccess saa2 = trialLevelAccessList.get(1);
        assertEquals(saa2.getActionCode(), AssignmentActionCode.UNASSIGNED);
        assertEquals(saa2.getStatusCode(), ActiveInactiveCode.INACTIVE);
        assertEquals(AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE, saa1.getSource());

        // Make sure site-level access has been cancelled.
        siteAccessList = session.createQuery(
                "from " + StudySiteAccrualAccess.class.getName()
                        + " saa where saa.registryUser.id = " + user.getId()
                        + " and studySite.id = " + site.getId()).list();        

        assertEquals(1, siteAccessList.size());
        ssaa = siteAccessList.get(0);
        assertEquals(ssaa.getStatusCode(), ActiveInactiveCode.INACTIVE);        
        
    }

    @Test
    public void unassignTrialLevelAccrualAccessNullCreator() throws Exception {

        Session session = PaHibernateUtil.getCurrentSession();

        RegistryUser user = (RegistryUser) session.get(RegistryUser.class,
                TestSchema.registryUserIds.get(0));
        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        StudySite site = TestSchema.createParticipatingSite(sp);
        StudyRecruitmentStatus recruitmentStatus = TestSchema
                .createStudyRecruitmentStatus(sp);
        TestSchema.addUpdObject(recruitmentStatus);

        bean.assignTrialLevelAccrualAccess(user, AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE, 
                Arrays.asList(sp.getId()), "TEST", null);
        bean.assignTrialLevelAccrualAccess(user, AccrualAccessSourceCode.REG_SITE_ADMIN_ROLE, 
                Arrays.asList(sp.getId()), "TEST", new RegistryUser());

    }

    @Test
    public void getAccrualAccessAssignmentHistory() throws Exception {

        // Delete any previous history records.
        Session session = PaHibernateUtil.getCurrentSession();
        session.createQuery("delete " + StudyAccrualAccess.class.getName())
                .executeUpdate();
        session.flush();

        RegistryUser user = TestSchema.getRegistryUser();
        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        StudySite site = TestSchema.createParticipatingSite(sp);
        StudyRecruitmentStatus recruitmentStatus = TestSchema
                .createStudyRecruitmentStatus(sp);
        TestSchema.addUpdObject(recruitmentStatus);

        // assign
        bean.assignTrialLevelAccrualAccess(user, AccrualAccessSourceCode.REG_ADMIN_PROVIDED,
                Arrays.asList(sp.getId()), "TEST-ASSIGN", user);        
        // now un-assign.
        bean.unassignTrialLevelAccrualAccess(user, AccrualAccessSourceCode.REG_ADMIN_PROVIDED,
                Arrays.asList(sp.getId()), "TEST-UNASSIGN", user);

        // Mock user service
        RegistryUserServiceLocal userServiceLocal = mock(RegistryUserServiceLocal.class);
        when(userServiceLocal.getUser(any(String.class))).thenReturn(user);
        bean.setRegistryUserService(userServiceLocal);        
        
        List<AccrualAccessAssignmentHistoryDTO>  list = bean.getAccrualAccessAssignmentHistory(Arrays.asList(sp.getId()));
        assertEquals(2, list.size());
        
        AccrualAccessAssignmentHistoryDTO unassign = list.get(0);
        assertEquals("Unassigned", unassign.getAction());        
        assertEquals("Test User", unassign.getAssigner());
        assertEquals("TEST-UNASSIGN", unassign.getComments());
        assertEquals("NCI-2009-00001", unassign.getTrialNciId());

        AccrualAccessAssignmentHistoryDTO assign = list.get(1);
        assertEquals("Assigned", assign.getAction());        
        assertEquals("Test User", assign.getAssigner());
        assertEquals("TEST-ASSIGN", assign.getComments());
        assertEquals("NCI-2009-00001", assign.getTrialNciId());
        
        
    }
    
    @Test
    public void getAccrualAccessAssignmentByTrial() throws Exception {

        // Delete any previous history records.
        Session session = PaHibernateUtil.getCurrentSession();
        session.createQuery("delete " + StudyAccrualAccess.class.getName())
                .executeUpdate();
        session.flush();

        RegistryUser user = TestSchema.getRegistryUser();
        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        StudySite site = TestSchema.createParticipatingSite(sp);
        StudyRecruitmentStatus recruitmentStatus = TestSchema
                .createStudyRecruitmentStatus(sp);
        TestSchema.addUpdObject(recruitmentStatus);

        // assign
        bean.assignTrialLevelAccrualAccess(user, AccrualAccessSourceCode.REG_ADMIN_PROVIDED,
                Arrays.asList(sp.getId()), "TEST-ASSIGN", user);

        // Mock user service
        RegistryUserServiceLocal userServiceLocal = mock(RegistryUserServiceLocal.class);
        when(userServiceLocal.getUser(any(String.class))).thenReturn(user);
        bean.setRegistryUserService(userServiceLocal);        
        
        List<AccrualAccessAssignmentByTrialDTO>  list = bean.getAccrualAccessAssignmentByTrial(Arrays.asList(sp.getId()));
        assertEquals(1, list.size());
        
        // now un-assign.
        bean.unassignTrialLevelAccrualAccess(user, AccrualAccessSourceCode.REG_ADMIN_PROVIDED,
                Arrays.asList(sp.getId()), "TEST-UNASSIGN", user);

        list = bean.getAccrualAccessAssignmentByTrial(Arrays.asList(sp.getId()));
        assertEquals(0, list.size());
        
    }
}

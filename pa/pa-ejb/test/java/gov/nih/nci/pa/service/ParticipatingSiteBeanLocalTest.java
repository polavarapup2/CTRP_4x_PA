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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.OrganizationalStructuralRole;
import gov.nih.nci.pa.domain.StructuralRole;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractEjbTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.pa.util.pomock.MockFamilyService;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

/**
 * @author hreinhart
 */
public class ParticipatingSiteBeanLocalTest extends AbstractEjbTestCase {

    private ParticipatingSiteBeanLocal remoteEjb;
    private OrganizationDTO mayoFL;
    private OrganizationDTO mayoMN;
    private OrganizationDTO mayoCancerCenter;

    @Before
    public void init() throws Exception {
        TestSchema.primeData();
        remoteEjb = (ParticipatingSiteBeanLocal) getEjbBean(ParticipatingSiteBeanLocal.class);

        // Family
        FamilyDTO fam = new FamilyDTO();
        fam.setName(EnOnConverter.convertToEnOn("Mayo Family"));
        fam.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
        fam = new MockFamilyService().createFamily(fam);

        // Cancer Center
        mayoCancerCenter = new OrganizationDTO();
        mayoCancerCenter.setName(EnOnConverter.convertToEnOn("Mayo Center"));
        PoRegistry.getOrganizationEntityService().createOrganization(
                mayoCancerCenter);
        ResearchOrganizationDTO roDTO = new ResearchOrganizationDTO();
        roDTO.setPlayerIdentifier(mayoCancerCenter.getIdentifier());
        roDTO.setTypeCode(CdConverter.convertStringToCd("CCR"));
        PoRegistry.getResearchOrganizationCorrelationService()
                .createCorrelation(roDTO);
        new MockFamilyService().relate(mayoCancerCenter, fam);
        

        // Sibling 1
        mayoFL = new OrganizationDTO();
        mayoFL.setName(EnOnConverter.convertToEnOn("Mayo FL"));
        PoRegistry.getOrganizationEntityService().createOrganization(mayoFL);
        roDTO = new ResearchOrganizationDTO();
        roDTO.setPlayerIdentifier(mayoFL.getIdentifier());
        roDTO.setTypeCode(CdConverter.convertStringToCd("CSM"));
        PoRegistry.getResearchOrganizationCorrelationService()
                .createCorrelation(roDTO);
        new MockFamilyService().relate(mayoFL, fam);
       

        // Sibling 2
        mayoMN = new OrganizationDTO();
        mayoMN.setName(EnOnConverter.convertToEnOn("Mayo MN"));
        PoRegistry.getOrganizationEntityService().createOrganization(mayoMN);
        roDTO = new ResearchOrganizationDTO();
        roDTO.setPlayerIdentifier(mayoMN.getIdentifier());
        roDTO.setTypeCode(CdConverter.convertStringToCd("CSM"));
        PoRegistry.getResearchOrganizationCorrelationService()
                .createCorrelation(roDTO);
        new MockFamilyService().relate(mayoMN, fam);

    }
    
	private void addSites() throws Exception {
		addSite(mayoCancerCenter, TestSchema.studyProtocols.get(0));
		addSite(mayoFL, TestSchema.studyProtocols.get(0));
		addSite(mayoMN, TestSchema.studyProtocols.get(0));
	}

    private ParticipatingSiteDTO addSite(OrganizationDTO org, StudyProtocol studyProtocol)
            throws EntityValidationException, CurationException, PAException {
           return addSite(org, studyProtocol, null);
    }

    private ParticipatingSiteDTO addSite(OrganizationDTO org, StudyProtocol studyProtocol, String pgCodes)
            throws EntityValidationException, CurationException, PAException {
        StudySiteDTO studySiteDTO = new StudySiteDTO();
        if (StringUtils.isNotEmpty(pgCodes)) {
            studySiteDTO.setProgramCodeText(StConverter.convertToSt(pgCodes));
        }
        studySiteDTO.setAccrualDateRange(IvlConverter.convertTs()
                .convertToIvl(
                        new Timestamp(new Date().getTime()
                                - Long.valueOf("300000000")), null));
        studySiteDTO.setLocalStudyProtocolIdentifier(StConverter
                .convertToSt(EnOnConverter.convertEnOnToString(org.getName())));

        studySiteDTO.setStudyProtocolIdentifier(IiConverter
                .convertToStudyProtocolIi(studyProtocol.getId()));

        StudySiteAccrualStatusDTO currentStatus = new StudySiteAccrualStatusDTO();
        currentStatus.setStatusCode(CdConverter
                .convertStringToCd(RecruitmentStatusCode.ACTIVE.getCode()));

        currentStatus.setStatusDate(TsConverter.convertToTs(new Timestamp(
                new Date().getTime() - Long.valueOf("300000000"))));

        HealthCareFacilityDTO hcf = new HealthCareFacilityDTO();
        hcf.setPlayerIdentifier(org.getIdentifier());
        PoRegistry.getHealthCareFacilityCorrelationService().createCorrelation(
                hcf);

        return remoteEjb.createStudySiteParticipant(studySiteDTO, currentStatus,
                hcf.getIdentifier().getItem().iterator().next());

    }

    @Test
    public void testLegacyProgramCode() throws Exception {

        remoteEjb.setCorrUtils(new CorrelationUtils(){
            @Override
            public <T extends StructuralRole> T getStructuralRoleByIi(Ii isoIi) throws PAException {
                OrganizationalStructuralRole s = null ;
                try {

                    s = (OrganizationalStructuralRole)Class.forName("gov.nih.nci.pa.domain.HealthCareFacility").newInstance();
                    Organization o = new Organization();
                    s.setOrganization(o);
                    o.setIdentifier(IiConverter.convertToString(isoIi));
                } catch (Exception e) {
                   throw new PAException(e);
                }
                return (T)s;
            }
        });
        //When I add a site
        ParticipatingSiteDTO site = addSite(mayoMN, TestSchema.studyProtocols.get(1), "2;3");
        String pgText = StConverter.convertToString(site.getProgramCodeText());
        assertNotNull(pgText);
        List<String> codes = Arrays.asList(StringUtils.split(pgText, ";"));
        assertTrue(codes.contains("2"));
        assertTrue(codes.contains("3"));


        //And when I add site with program codes having space in it.

        ParticipatingSiteDTO site1 = addSite(mayoFL, TestSchema.studyProtocols.get(2), "2; 3;4");
        String pgText1 = StConverter.convertToString(site1.getProgramCodeText());
        assertNotNull(pgText1);
        List<String> codes1 = Arrays.asList(StringUtils.split(pgText1, ";"));
        assertTrue(codes1.contains("2"));
        assertTrue(codes1.contains("4"));
        assertTrue(codes1.contains("3"));
    }

    @Test
    public void getListOfSitesUserCanUpdateCancerCenter() throws Exception {
    	addSites();
        Session s = PaHibernateUtil.getCurrentSession();
        User user = CSMUserService.getInstance().getCSMUser(
                UsernameHolder.getUser());
        RegistryUser ru = TestSchema.createRegistryUser(user);
        s.flush();

        ru.setAffiliatedOrganizationId(IiConverter
                .convertToLong(mayoCancerCenter.getIdentifier()));
        ru.setAffiliateOrg(EnOnConverter.convertEnOnToString(mayoCancerCenter
                .getName()));
        s.update(ru);
        s.flush();

        // User is affiliated with the Cancer Center, so should be able to
        // update all 3 sites on trial.
        List<Organization> sites = remoteEjb.getListOfSitesUserCanUpdate(ru,
                IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols
                        .get(0).getId()));
        assertEquals(3, sites.size());
        assertTrue(sites.contains(s.createQuery(
                "from Organization where name='"
                        + EnOnConverter.convertEnOnToString(mayoCancerCenter
                                .getName()) + "'").uniqueResult()));
        assertTrue(sites.contains(s.createQuery(
                "from Organization where name='"
                        + EnOnConverter.convertEnOnToString(mayoFL.getName())
                        + "'").uniqueResult()));
        assertTrue(sites.contains(s.createQuery(
                "from Organization where name='"
                        + EnOnConverter.convertEnOnToString(mayoMN.getName())
                        + "'").uniqueResult()));
    }

    @Test
    public void getListOfSitesUserCanUpdateNonCancerCenter()
            throws Exception {
    	addSites();
        Session s = PaHibernateUtil.getCurrentSession();
        User user = CSMUserService.getInstance().getCSMUser(
                UsernameHolder.getUser());
        RegistryUser ru = TestSchema.createRegistryUser(user);
        s.flush();

        ru.setAffiliatedOrganizationId(IiConverter.convertToLong(mayoFL
                .getIdentifier()));
        ru.setAffiliateOrg(EnOnConverter.convertEnOnToString(mayoFL.getName()));
        s.update(ru);
        s.flush();

        // User is affiliated with the Cancer Center, so should be able to
        // update all 3 sites on trial.
        List<Organization> sites = remoteEjb.getListOfSitesUserCanUpdate(ru,
                IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols
                        .get(0).getId()));
        assertEquals(2, sites.size());
        assertTrue(sites.contains(s.createQuery(
                "from Organization where name='"
                        + EnOnConverter.convertEnOnToString(mayoFL.getName())
                        + "'").uniqueResult()));
        assertTrue(sites.contains(s.createQuery(
                "from Organization where name='"
                        + EnOnConverter.convertEnOnToString(mayoMN.getName())
                        + "'").uniqueResult()));
    }
    
    @Test
    public void testGetListOfSitesUserCanAdd() throws PAException,
            NullifiedRoleException {
        Session s = PaHibernateUtil.getCurrentSession();
        User user = CSMUserService.getInstance().getCSMUser(
                UsernameHolder.getUser());
        RegistryUser ru = TestSchema.createRegistryUser(user);
        s.flush();
        
        ru.setAffiliatedOrganizationId(IiConverter
                .convertToLong(mayoCancerCenter.getIdentifier()));
        ru.setAffiliateOrg(EnOnConverter.convertEnOnToString(mayoCancerCenter
                .getName()));
        s.update(ru);
        s.flush();

        List<Organization> sites = remoteEjb.getListOfSitesUserCanAdd(ru,
                IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols
                        .get(0).getId()));
        assertEquals(3, sites.size());
    }
    
    @Test
    public void testGetListOfSitesUserCanAddExcludeTheAddedSite() throws PAException,
            NullifiedRoleException, EntityValidationException, CurationException {
    	
    	addSite(mayoCancerCenter, TestSchema.studyProtocols.get(0));
    	
        Session s = PaHibernateUtil.getCurrentSession();
        User user = CSMUserService.getInstance().getCSMUser(
                UsernameHolder.getUser());
        RegistryUser ru = TestSchema.createRegistryUser(user);
        s.flush();
        
        ru.setAffiliatedOrganizationId(IiConverter
                .convertToLong(mayoCancerCenter.getIdentifier()));
        ru.setAffiliateOrg(EnOnConverter.convertEnOnToString(mayoCancerCenter
                .getName()));
        s.update(ru);
        s.flush();

        List<Organization> sites = remoteEjb.getListOfSitesUserCanAdd(ru,
                IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols
                        .get(0).getId()));
        assertEquals(2, sites.size());
        
        assertTrue(!sites.contains(s.createQuery(
                "from Organization where name='"
                        + EnOnConverter.convertEnOnToString(mayoCancerCenter
                                .getName()) + "'").uniqueResult()));
        assertTrue(sites.contains(s.createQuery(
                "from Organization where name='"
                        + EnOnConverter.convertEnOnToString(mayoFL.getName())
                        + "'").uniqueResult()));
        assertTrue(sites.contains(s.createQuery(
                "from Organization where name='"
                        + EnOnConverter.convertEnOnToString(mayoMN.getName())
                        + "'").uniqueResult()));

    }

}

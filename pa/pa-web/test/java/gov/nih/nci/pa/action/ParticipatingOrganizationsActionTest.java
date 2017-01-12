/**
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce, copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties.For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License  is  granted  at no  charge to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions,  You have no right to download, copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any, must include the  following acknowledgment:
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
 * party proprietary programs,  You agree  that You are solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to  secure  any required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction, and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro,Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.ParticipatingOrganizationsTabWebDTO;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.StudySubjectServiceLocal;
import gov.nih.nci.pa.service.util.PAHealthCareProviderLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.service.MockCorrelationUtils;
import gov.nih.nci.service.MockOrganizationCorrelationService;
import gov.nih.nci.service.MockStudySiteService;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author hreinhart
 *
 */
public class ParticipatingOrganizationsActionTest extends AbstractPaActionTest {
    private static ParticipatingOrganizationsAction act;

    @Before
    public void prepare() throws Exception {
        act = new ParticipatingOrganizationsAction();
        act.prepare();
        act.setCorrelationUtils(new MockCorrelationUtils());
    }

    @Test
    public void listOrganizationsTest() throws Exception {
        // select from menu
        String ar = act.execute();
        assertEquals(ActionSupport.SUCCESS, ar);
        assertNotNull(act.getOrganizationList());
        act.setOrganizationList(null);
        assertNull(act.getOrganizationList());
        act.setSelectedOrgDTO(null);
        assertNull(act.getSelectedOrgDTO());
        act.setSpContactDTO(null);
        assertNull(act.getSpContactDTO());
        assertFalse(act.isNewParticipation());
        act.setEditOrg(new Organization());
        assertNotNull(act.getEditOrg());
        act.setPersonWebDTOList(null);
        assertNull(act.getPersonWebDTOList());
        act.setPersonContactWebDTO(null);
        assertNull(act.getPersonContactWebDTO());
        assertNull(act.getOrganizationName());
        assertNotNull(act.getOrgFromPO());
        assertNotNull(act.getCurrentAction());
        assertNull(act.getStatusCode());
        act.setSiteProgramCodeText("siteProgramCodeText");
        assertNotNull(act.getSiteProgramCodeText());
        assertNull(act.getDateOpenedForAccrual());
        assertNull(act.getDateClosedForAccrual());
        act.setStudySiteIdentifier(1L);
        assertNotNull(act.getStudySiteIdentifier());
        getRequest().setupAddParameter("perId", "1");
        PoServiceLocator poSvcLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poSvcLoc);
        PersonEntityServiceRemote poPersonSvc = mock(PersonEntityServiceRemote.class);
        when(poSvcLoc.getPersonEntityService()).thenReturn(poPersonSvc);
        List<PersonDTO> personDtos = new ArrayList<PersonDTO>();
        PersonDTO personDTO2 = new PersonDTO();
        personDTO2.setIdentifier(IiConverter.convertToIi("3"));
        personDTO2.setName(EnPnConverter.convertToEnPn("OtherName", null, "OtherName", null, null));
        personDTO2.setPostalAddress(AddressConverterUtil.create("streetAddressLine", null, "cityOrMunicipality",
                "stateOrProvince", "postalCode", "USA"));
        personDTO2.setStatusCode(CdConverter.convertStringToCd("ACTIVE"));
		personDtos.add(personDTO2);
        when(poPersonSvc.search(any(PersonDTO.class), any(LimitOffset.class))).thenReturn(personDtos);
        act.prepare();
        assertEquals("displayPerson", act.displayPerson());
    }

     @Test
    public void addOrganizationTest() throws Exception {

        // click the add button
        String ar = act.create();
        assertEquals(ActionSupport.SUCCESS, ar);

        // try to save without entering any info.
        ar = act.facilitySave();
        assertEquals(Action.ERROR, ar);
        assertTrue(act.hasFieldErrors());
        assertTrue(act.getFieldErrors().containsKey("recStatus"));
        assertTrue(act.getFieldErrors().containsKey("recStatusDate"));
        assertTrue(act.getFieldErrors().containsKey("editOrg.name"));

        // enter recruitment information and save
        act.setRecStatus(RecruitmentStatusCode.IN_REVIEW.getCode());
        act.setRecStatusDate(PAUtil.today());
        ar = act.facilitySave();
        assertEquals(Action.ERROR, ar);
        assertTrue(act.hasFieldErrors());
        assertFalse(act.getFieldErrors().containsKey("recStatus"));
        assertFalse(act.getFieldErrors().containsKey("recStatusDate"));
        assertTrue(act.getFieldErrors().containsKey("editOrg.name"));

        act.setRecStatus(RecruitmentStatusCode.APPROVED.getCode());
        ar = act.facilitySave();
        assertEquals(Action.ERROR, ar);
        assertTrue(act.hasFieldErrors());
        assertFalse(act.getFieldErrors().containsKey("recStatus"));
        assertFalse(act.getFieldErrors().containsKey("recStatusDate"));
        assertTrue(act.getFieldErrors().containsKey("editOrg.name"));

        act.setRecStatus(RecruitmentStatusCode.WITHDRAWN.getCode());
        ar = act.facilitySave();
        assertEquals(Action.ERROR, ar);
        assertTrue(act.hasFieldErrors());
        assertFalse(act.getFieldErrors().containsKey("recStatus"));
        assertFalse(act.getFieldErrors().containsKey("recStatusDate"));
        assertTrue(act.getFieldErrors().containsKey("editOrg.name"));

        assertEquals("historypopup", act.historyPopup());
        // look-up org information and save
        String poIdentifier = "abc";
        String newOrgName = MockOrganizationCorrelationService.getOrgNameFromPoIdentifier(poIdentifier);
        ParticipatingOrganizationsTabWebDTO tab = new ParticipatingOrganizationsTabWebDTO();
        PaOrganizationDTO org = new PaOrganizationDTO();
        org.setName(newOrgName);
        act.setOrgFromPO(org);
        Organization facility = new Organization();
        facility.setIdentifier(poIdentifier);
        facility.setName(newOrgName);
        tab.setFacilityOrganization(facility);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);

        ar = act.facilitySave();
        assertEquals("facilitySave", ar);
        assertFalse(act.hasFieldErrors());

        // confirmation message displayed
        String message = (String) ServletActionContext.getRequest().getAttribute(Constants.SUCCESS_MESSAGE);
        assertEquals(Constants.CREATE_MESSAGE, message);

        // select from menu again (return to list screen)
        ar = act.execute();
        assertEquals(ActionSupport.SUCCESS, ar);

        // confirm that list contains new org.
        boolean found = false;
        for (PaOrganizationDTO webDto : act.getOrganizationList()) {
            if (MockOrganizationCorrelationService.getOrgNameFromPoIdentifier(poIdentifier).equals(webDto.getName())) {
                found = true;
            }
        }
        tab.setFacilityOrganization(facility);
        tab.setStudyParticipationId(1L);
        getRequest().setupAddParameter("recStatus", RecruitmentStatusCode.APPROVED.getCode());
        assertEquals("error_edit", act.facilityUpdate());        
        getRequest().setupAddParameter("recStatusDate", "12/23/2013");
        getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        assertEquals("edit", act.facilityUpdate()); 
        act.setProprietaryTrialIndicator("true");
        StudySiteDTO dto = new StudySiteDTO();
        dto.setFunctionalCode(CdConverter.convertStringToCd("functionalCode"));
        dto.setStatusCode(CdConverter.convertStringToCd("statusCode"));
        dto.setHealthcareFacilityIi(IiConverter.convertToPoHealthCareFacilityIi("1"));
        StudySiteServiceLocal svc = mock(StudySiteServiceLocal.class);
        when(svc.get(any(Ii.class))).thenReturn(dto);
        act.setStudySiteService(svc);
        assertEquals("edit", act.edit());
        assertEquals("delete", act.delete());
        act.deleteObject(1L);
        getRequest().setupAddParameter("orgId", "1");
        assertEquals("displayJsp",act.displayOrg());
        assertEquals("display_StudyPartipants",act.getStudyParticipationContacts());
        assertEquals("error_prim_contacts", act.saveStudyParticipationContact());
        assertEquals("display_spContacts", act.deleteStudyPartContact());
        getRequest().setupAddParameter("contactpersid", "1");
        assertEquals("display_primContacts", act.displayStudyParticipationPrimContact());
        getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        assertEquals("display_spContacts", act.saveStudyParticipationContact());
        assertEquals("display_spContacts", act.getDisplaySPContacts());
        
        ServiceLocator paSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);
        PAHealthCareProviderLocal rmtSvc = mock(PAHealthCareProviderLocal.class);
        when(rmtSvc.getPersonsByStudySiteId(any(Long.class), any(String.class))).thenReturn(new ArrayList<PaPersonDTO>());
        when(rmtSvc.getIdentifierBySPCId(any(Long.class))).thenReturn(new PaPersonDTO());
        when(paSvcLoc.getPAHealthCareProviderService()).thenReturn(rmtSvc);
        when(paSvcLoc.getStudySiteService()).thenReturn(new MockStudySiteService());
        StudySiteAccrualStatusServiceLocal ssas = mock(StudySiteAccrualStatusServiceLocal.class);
        StudySiteAccrualStatusDTO sasdto = new StudySiteAccrualStatusDTO();
        sasdto.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.ACTIVE));
        sasdto.setStatusDate(TsConverter.convertToTs(new Date()));
        sasdto.setComments(StConverter.convertToSt("Some Comments"));
		when(ssas.getCurrentStudySiteAccrualStatusByStudySite(any(Ii.class))).thenReturn(sasdto);
		when(ssas.getStudySiteAccrualStatusByStudySite(any(Ii.class))).thenReturn(Arrays.asList(sasdto));
        when(paSvcLoc.getStudySiteAccrualStatusService()).thenReturn(ssas);
        StudySiteContactServiceLocal service = mock(StudySiteContactServiceLocal.class);
        StudySiteContactDTO result = new StudySiteContactDTO();
        result.setOrganizationalContactIi(IiConverter.convertToPoOrganizationalContactIi("1"));
        result.setRoleCode(CdConverter.convertToCd(StudySiteContactRoleCode.PRIMARY_CONTACT));
        when(service.getByStudyProtocol(any(Ii.class), any(StudySiteContactDTO.class))).thenReturn(Arrays.asList(result));
        when(service.getByStudySite(any(Ii.class))).thenReturn(Arrays.asList(result));
        when(paSvcLoc.getStudySiteContactService()).thenReturn(service);
        act.prepare();
        act.setCbValue(1L);
        tab.setStudyParticipationId(1L);
        getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        assertEquals("historypopup", act.historyPopup());
        assertNotNull(act.getCbValue());
        assertEquals("edit", act.edit());
        assertEquals("display_primContacts", act.refreshPrimaryContact());
        getRequest().setupAddParameter("editmode", "yes");
        getRequest().getSession().setAttribute("emailEntered", "test@test.com");
        getRequest().getSession().setAttribute("telephoneEntered", "1234567890");
        assertEquals("display_primContacts", act.displayStudyParticipationPrimContact());
        act.setPaServiceUtil(null);
        act.setOrganizationalContactCorrelationService(null);
        act.setOrganizationEntityService(null);
        act.setPaHealthCareProviderService(rmtSvc);
        act.setParticipatingSiteService(null);
        act.setPersonEntityService(null);
        act.setStudyProtocolService(null);
        act.setStudySiteAccrualStatusService(null);
        act.setStudySiteContactService(null);
        
    }
    @Test
    public void testsaveStudyParticipationPrimContact() throws NullifiedEntityException, PAException{
        getRequest().setupAddParameter("contactpersid", "");
        getRequest().setupAddParameter("email", "");
        getRequest().setupAddParameter("tel", "");
        ParticipatingOrganizationsTabWebDTO tab = new ParticipatingOrganizationsTabWebDTO();
        tab.setStudyParticipationId(3L);
        Organization org = new Organization();
        org.setId(1L);
        org.setIdentifier("1");
        tab.setFacilityOrganization(org);
        getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        assertEquals("error_prim_contacts", act.saveStudyParticipationPrimContact());
        getRequest().setupAddParameter("contactpersid", "2");
        getRequest().setupAddParameter("email", "example@example");
        getRequest().setupAddParameter("tel", "1");
        assertEquals("error_prim_contacts", act.saveStudyParticipationPrimContact());
        getRequest().setupAddParameter("email", "example@example.com");
        getRequest().setupAddParameter("tel", "1");
        assertEquals("error_prim_contacts",act.saveStudyParticipationPrimContact());
        getRequest().setupAddParameter("tel", "111-222-3333 ext 1");
        assertEquals("refreshPrimaryContact",act.saveStudyParticipationPrimContact());
        getRequest().setupAddParameter("tel", "111-222-3333");
        assertEquals("refreshPrimaryContact",act.saveStudyParticipationPrimContact());
        getRequest().setupAddParameter("tel", "111-222-3333x12345");
        assertEquals("refreshPrimaryContact",act.saveStudyParticipationPrimContact());
        getRequest().setupAddParameter("tel", "111-222-3333extn12345");
        assertEquals("refreshPrimaryContact",act.saveStudyParticipationPrimContact());
        getRequest().setupAddParameter("email", "example.com");
        getRequest().setupAddParameter("tel", "111-222-3333extn12345");
        assertEquals("error_prim_contacts",act.saveStudyParticipationPrimContact());
        getRequest().setupAddParameter("email", "example.com");
        getRequest().setupAddParameter("tel", "");
        assertEquals("error_prim_contacts",act.saveStudyParticipationPrimContact());
        org.setIdentifier(null);
        tab.setFacilityOrganization(org);
        getRequest().setupAddParameter("email", "example@example.com");
        getRequest().setupAddParameter("tel", "111-222-3333 ext 1");
        assertEquals("refreshPrimaryContact",act.saveStudyParticipationPrimContact());
    }

    @Test
    public void isProgramCodeOrTargetAccrualNumberUpdatedTest() throws Exception {
        Integer testInt = 123;
        String testStr = "abc";

        // initially null
        StudySiteDTO studySite = new StudySiteDTO();
        assertFalse(act.isProgramCodeOrTargetAccrualNumberUpdated(studySite, null, null));
        assertTrue(act.isProgramCodeOrTargetAccrualNumberUpdated(studySite, testStr, null));
        assertEquals(testStr, StConverter.convertToString(studySite.getProgramCodeText()));
        studySite = new StudySiteDTO();
        assertTrue(act.isProgramCodeOrTargetAccrualNumberUpdated(studySite, null, testInt));
        assertEquals(testInt, IntConverter.convertToInteger(studySite.getTargetAccrualNumber()));

        // initially not null
        studySite = new StudySiteDTO();
        studySite.setProgramCodeText(StConverter.convertToSt(testStr));
        studySite.setTargetAccrualNumber(IntConverter.convertToInt(testInt));
        assertFalse(act.isProgramCodeOrTargetAccrualNumberUpdated(studySite, testStr, testInt));
        assertEquals(testStr, StConverter.convertToString(studySite.getProgramCodeText()));
        assertEquals(testInt, IntConverter.convertToInteger(studySite.getTargetAccrualNumber()));
        assertTrue(act.isProgramCodeOrTargetAccrualNumberUpdated(studySite, null, null));
        assertNull(StConverter.convertToString(studySite.getProgramCodeText()));
        assertNull(IntConverter.convertToInteger(studySite.getTargetAccrualNumber()));
    }
    
    @Test
    public void accrualDeleteWarningTest() throws PAException {
        StudySubjectServiceLocal ssservice = mock(StudySubjectServiceLocal.class);
        act.setStudySubjectService(ssservice);
        List<StudySubject> subjectList = new ArrayList<StudySubject>();
        StudySubject ss = new StudySubject();
        ss.setId(1L);
        StudyProtocol sp = new StudyProtocol();
        sp.setId(1L);
        Organization org = new Organization();
        org.setName("Mayo University");
        org.setDateLastUpdated(new Date());
        org.setIdentifier("1");
        org.setStatusCode(EntityStatusCode.ACTIVE);
        org.setIdentifier("123");
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setOrganization(org);
        hcf.setIdentifier("1");
        hcf.setStatusCode(StructuralRoleStatusCode.PENDING);
        StudySite ssite = new StudySite();
        ssite.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        ssite.setLocalStudyProtocolIdentifier("Ecog1");
        ssite.setDateLastUpdated(new Date());
        ssite.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ssite.setStudyProtocol(sp);
        ssite.setHealthCareFacility(hcf);
        ss.setStudySite(ssite);
        subjectList.add(ss);
        String[] str = new String[1];
        str[0] = "1";
        act.setObjectsToDelete(str);
        when(ssservice.getBySiteAndStudyId(any(Long.class), any(Long.class))).thenReturn(subjectList);
        assertEquals("deleteStatus", act.accrualDeleteWarning());
        assertNotNull(act.getSubjects());
        assertTrue(act.getSubjects().size() > 0);
    }
    
}

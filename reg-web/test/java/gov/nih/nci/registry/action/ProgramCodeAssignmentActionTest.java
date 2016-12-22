package gov.nih.nci.registry.action;

import com.mockrunner.mock.web.MockHttpServletRequest;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.FamilyProgramCodeService;
import gov.nih.nci.pa.service.util.ParticipatingOrgServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.registry.util.Constants;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StreamResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_LAST_UPDATER_INFO;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Will test ProgramCodeAssignmentAction
 */
public class ProgramCodeAssignmentActionTest  extends AbstractRegWebTest {
    private ProgramCodeAssignmentAction action;
    private FamilyProgramCodeService familyProgramCodeService;
    private ProtocolQueryServiceLocal protocolQueryService;
    private RegistryUserServiceLocal registryUserService;
    private ParticipatingOrgServiceLocal participatingOrgService;
    private StudyProtocolServiceLocal studyProtocolService;

    @Before
    public void init() throws Exception {
        action = new ProgramCodeAssignmentAction();

        familyProgramCodeService = mock(FamilyProgramCodeService.class);
        protocolQueryService = mock(ProtocolQueryServiceLocal.class);
        registryUserService = mock(RegistryUserServiceLocal.class);
        participatingOrgService = mock(ParticipatingOrgServiceLocal.class);
        studyProtocolService = mock(StudyProtocolServiceLocal.class);

        action.setRegistryUserService(registryUserService);
        action.setFamilyProgramCodeService(familyProgramCodeService);
        action.setProtocolQueryService(protocolQueryService);
        action.setParticipatingOrgService(participatingOrgService);
        action.setStudyProtocolService(studyProtocolService);

        RegistryUser user = new RegistryUser();
        user.setAffiliatedOrganizationId(1L);
        when(registryUserService.getUser(anyString())).thenReturn(user);

        FamilyDTO f = new FamilyDTO(1L);
        f.setName("FamilyDTO-1");
        ProgramCodeDTO pg3 = new ProgramCodeDTO(3L, "PG3", "ProgramCode3");
        ProgramCodeDTO pg4 = new ProgramCodeDTO(4L, "PG4", "ProgramCode4");
        f.getProgramCodes().add(pg3);
        f.getProgramCodes().add(pg4);
        when(familyProgramCodeService.getFamilyDTOByPoId(1L)).thenReturn(f);


        ParticipatingOrgDTO site = new ParticipatingOrgDTO();
        site.setPoId("1");
        site.setName("DCP");
        site.setRecruitmentStatusDate(new Timestamp(DateUtils.parseDate(
                "01/01/2015", new String[]{"MM/dd/yyyy"}).getTime()));
        site.setRecruitmentStatus(RecruitmentStatusCode.ACTIVE);

        when(participatingOrgService.getTreatingSites(eq(1L))).thenReturn(Arrays.asList(site));


        when(protocolQueryService.getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class),
            eq(SKIP_ALTERNATE_TITLES), eq(SKIP_LAST_UPDATER_INFO))).thenReturn(createTrials());
        ServletActionContext.getRequest().getSession().setAttribute("isSiteAdmin", true);
    }

    @Test
    public void testExecute() throws Exception {

        //Given that the user is not admin
       MockHttpServletRequest req = (MockHttpServletRequest) ServletActionContext.getRequest();
       req.setUserInRole(Constants.PROGRAM_CODE_ADMINISTRATOR, false);

        //When I execute
       assertEquals("success", action.execute());

        //I get only family affiliated to my org
       assertEquals(1, action.getAffiliatedFamilies().size());

       //When an admin executes it
       req.setUserInRole(Constants.PROGRAM_CODE_ADMINISTRATOR, true);
       assertEquals("success", action.execute());

        //then I see more than one families
       assertEquals(2, action.getAffiliatedFamilies().size());


    }

    @Test
    public void testParticipation() throws  Exception {
        Field field = StreamResult.class.getDeclaredField("inputStream");
        ReflectionUtils.makeAccessible(field);

        StreamResult sr = action.participation();
        InputStream is = (InputStream) field.get(sr);
        String json = IOUtils.toString(is);
        assertEquals(json, "{\"data\":[]}");

        action.setFamilyPoId(1L);
        action.setStudyProtocolId(1L);

        when(FamilyHelper.getRelatedOrgsInFamily(1L)).thenReturn(Arrays.asList(1L, 2L));

        sr = action.participation();
        is = (InputStream) field.get(sr);
        json = IOUtils.toString(is);
        assertEquals(json, "{\"data\":[]}");
    }


    @Test
    public void testUnassignProgramCode() throws  Exception {
        Field field = StreamResult.class.getDeclaredField("inputStream");
        ReflectionUtils.makeAccessible(field);
        action.setStudyProtocolId(1L);
        action.setFamilyPoId(1L);
        action.setStudyProtocolId(1L);
        action.setPgcParam("3");
        when(FamilyHelper.getRelatedOrgsInFamily(1L)).thenReturn(Arrays.asList(1L, 2L));

        StreamResult sr = action.unassignProgramCode();
        InputStream is = (InputStream) field.get(sr);
        String json = IOUtils.toString(is);
        assertEquals(json, "{\"status\":\"REMOVED\"}");
    }

    @Test
    public void testAssignProgramCode() throws  Exception {
        Field field = StreamResult.class.getDeclaredField("inputStream");
        ReflectionUtils.makeAccessible(field);
        action.setStudyProtocolId(1L);
        action.setFamilyPoId(1L);
        action.setStudyProtocolId(1L);
        action.setPgcParam("3");
        when(FamilyHelper.getRelatedOrgsInFamily(1L)).thenReturn(Arrays.asList(1L, 2L));

        StreamResult sr = action.assignProgramCode();
        InputStream is = (InputStream) field.get(sr);
        String json = IOUtils.toString(is);
        assertEquals(json, "{\"status\":\"ADDED\"}");
    }


    @Test
    public void testAssignProgramCodesToTrials() throws  Exception {
        Field field = StreamResult.class.getDeclaredField("inputStream");
        ReflectionUtils.makeAccessible(field);

        action.setFamilyPoId(1L);
        action.setStudyProtocolIdListParam("1");
        action.setPgcListParam("3");
        when(FamilyHelper.getRelatedOrgsInFamily(1L)).thenReturn(Arrays.asList(1L, 2L));

        StreamResult sr = action.assignProgramCodesToTrials();
        InputStream is = (InputStream) field.get(sr);
        String json = IOUtils.toString(is);
        assertEquals(json, "{\"status\":\"ADDED\"}");
    }


    @Test
    public void testUnassignProgramCodesToTrials() throws  Exception {
        Field field = StreamResult.class.getDeclaredField("inputStream");
        ReflectionUtils.makeAccessible(field);

        action.setFamilyPoId(1L);
        action.setStudyProtocolIdListParam("1");
        action.setPgcListParam("3");
        when(FamilyHelper.getRelatedOrgsInFamily(1L)).thenReturn(Arrays.asList(1L, 2L));

        StreamResult sr = action.unassignProgramCodesFromTrials();
        InputStream is = (InputStream) field.get(sr);
        String json = IOUtils.toString(is);
        assertEquals(json, "{\"status\":\"REMOVED\"}");
    }



    @Test
    public void testReplaceProgramCodesInTrials() throws  Exception {
        Field field = StreamResult.class.getDeclaredField("inputStream");
        ReflectionUtils.makeAccessible(field);

        action.setFamilyPoId(1L);
        action.setStudyProtocolIdListParam("1");
        action.setPgcListParam("3");
        action.setPgcParam("5");
        when(FamilyHelper.getRelatedOrgsInFamily(1L)).thenReturn(Arrays.asList(1L, 2L));

        StreamResult sr = action.replaceProgramCodesInTrials();
        InputStream is = (InputStream) field.get(sr);
        String json = IOUtils.toString(is);
        assertEquals(json, "{\"status\":\"REPLACED\"}");
    }


    @Test
    public void testFindTrials() {
      try {

          Field field = StreamResult.class.getDeclaredField("inputStream");
          ReflectionUtils.makeAccessible(field);

          //when I reset the family
          StreamResult sr = action.findTrials();

          InputStream is = (InputStream) field.get(sr);
          String json = IOUtils.toString(is);
          assertEquals("{\"endDateChanged\":false,\"updated\":false,\"lengthChanged\":false,\"data\":[]}", json);

          //When I reset to a valid family
          action.setFamilyPoId(1L);

          sr = action.findTrials();

          //then I must see no fields
          is = (InputStream) field.get(sr);
          json = IOUtils.toString(is);
          assertEquals("{\"endDateChanged\":false,\"updated\":false,\"lengthChanged\":false,\"data\":[{\"title\":\"title\",\"identifiers\":\"1, 2\",\"programCodes\":[{\"id\":3,\"name\":\"3\",\"code\":\"3\"},{\"id\":4,\"name\":\"4\",\"code\":\"4\"}],\"DT_RowId\":\"trial_null\",\"trialStatus\":\"Active\",\"piFullName\":\"Joel Joseph\",\"nciIdentifier\":\"1\"}]}", json);


      } catch (Exception e) {
          e.printStackTrace();
          fail("should not throw exception");
      }
    }


    @Test
    public void testFindTrialsWhenNoneSelected() {
        try {

            Field field = StreamResult.class.getDeclaredField("inputStream");
            ReflectionUtils.makeAccessible(field);

            //when I reset the family
            StreamResult sr = action.findTrials();

            InputStream is = (InputStream) field.get(sr);
            String json = IOUtils.toString(is);
            assertEquals("{\"endDateChanged\":false,\"updated\":false,\"lengthChanged\":false,\"data\":[]}", json);

            //When I reset to a valid family
            action.setFamilyPoId(1L);
            action.setPgcListParam("-1");

            sr = action.findTrials();

            //then I must see no fields
            is = (InputStream) field.get(sr);
            json = IOUtils.toString(is);
            assertEquals("{\"endDateChanged\":false,\"updated\":false,\"lengthChanged\":false,\"data\":[]}", json);

            //when I invoke with None and PG4
            action.setPgcListParam("-1,4");

            sr = action.findTrials();

            //then I must see no fields
            is = (InputStream) field.get(sr);
            json = IOUtils.toString(is);

            assertEquals("{\"endDateChanged\":false,\"updated\":false,\"lengthChanged\":false,\"data\":[{\"title\":\"title\",\"identifiers\":\"1, 2\",\"programCodes\":[{\"id\":3,\"name\":\"3\",\"code\":\"3\"},{\"id\":4,\"name\":\"4\",\"code\":\"4\"}],\"DT_RowId\":\"trial_null\",\"trialStatus\":\"Active\",\"piFullName\":\"Joel Joseph\",\"nciIdentifier\":\"1\"}]}", json);


        } catch (Exception e) {
            e.printStackTrace();
            fail("should not throw exception");
        }
    }


    @Test
    public void testChangeFamily() {

         //When I send null familyPoId
         action.setFamilyPoId(null);
         //Then I should see success page
         assertEquals("success", action.changeFamily());


        //When I send 1 as familyPoId
        action.setFamilyPoId(1L);
        //Then I should see success page
        assertEquals("success", action.changeFamily());

        //when I do not have privilege and issue change family
        ServletActionContext.getRequest().getSession().removeAttribute("isSiteAdmin");
        //then I should be redirected to error page.
        assertEquals("error", action.changeFamily());

        //Also when I am not a siteAdmin
        ServletActionContext.getRequest().getSession().setAttribute("isSiteAdmin", false);
        //then I should be redirected to error page.
        assertEquals("error", action.changeFamily());
    }

    private List<StudyProtocolQueryDTO> createTrials() {
        List<StudyProtocolQueryDTO> trials = new ArrayList<StudyProtocolQueryDTO>();

        StudyProtocolQueryDTO trial = new StudyProtocolQueryDTO();
        trial.setNciIdentifier("1");
        trial.setNctIdentifier("2");
        trial.setOfficialTitle("title");
        trial.setPiFullName("Joel Joseph");
        ProgramCodeDTO pg1 = new ProgramCodeDTO();
        pg1.setProgramCode("3");
        pg1.setProgramName("3");
        pg1.setId(3L);
        ProgramCodeDTO pg2 = new ProgramCodeDTO();
        pg2.setProgramCode("4");
        pg2.setProgramName("4");
        pg2.setId(4L);
        trial.setProgramCodes(Arrays.asList(pg1, pg2));
        trial.setStudyStatusCode(StudyStatusCode.ACTIVE);
        trials.add(trial);

        return trials;
    }
}

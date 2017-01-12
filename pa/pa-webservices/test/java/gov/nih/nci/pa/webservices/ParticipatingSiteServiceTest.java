/**
 * 
 */
package gov.nih.nci.pa.webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteContactDTO;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ParticipatingOrgServiceLocal;
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.pa.util.MockPoJndiServiceLocator;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.webservices.types.BaseParticipatingSite;
import gov.nih.nci.pa.webservices.types.BaseParticipatingSite.PrimaryContact;
import gov.nih.nci.pa.webservices.types.ObjectFactory;
import gov.nih.nci.pa.webservices.types.ParticipatingSite;
import gov.nih.nci.pa.webservices.types.ParticipatingSiteUpdate;
import gov.nih.nci.pa.webservices.types.Sites;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.SimpleLayout;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.xml.sax.SAXException;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author dkrylov
 * 
 */
@SuppressWarnings("deprecation")
public class ParticipatingSiteServiceTest extends AbstractMockitoTest {

    static {
        try {
            final ConsoleAppender appender = new ConsoleAppender(
                    new SimpleLayout());
            appender.setThreshold(Priority.INFO);
            Logger.getRootLogger().addAppender(appender);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private ParticipatingSiteService service;
    private CorrelationUtils correlationUtils;
    private PAServiceUtils paServiceUtils;
    private ParticipatingOrgDTO participatingOrgDTO;

    @SuppressWarnings("unchecked")
    @Before
    public void before() throws PAException, ParseException {
        service = new ParticipatingSiteService();

        paServiceUtils = mock(PAServiceUtils.class);
        when(paServiceUtils.getCrsDTO(any(Ii.class), any(String.class)))
                .thenCallRealMethod();
        when(paServiceUtils.getPoHcfIi(any(String.class))).thenCallRealMethod();
        service.setPaServiceUtils(paServiceUtils);

        correlationUtils = mock(CorrelationUtils.class);
        org.setIdentifier("2");
        when(correlationUtils.getPAOrganizationByIi(any(Ii.class))).thenReturn(
                org);
        service.setCorrelationUtils(correlationUtils);

        PoRegistry.getInstance().setPoServiceLocator(
                new MockPoJndiServiceLocator());
        when(
                PaRegistry.getInstance().getServiceLocator()
                        .getOrganizationCorrelationService()).thenReturn(
                new OrganizationCorrelationServiceBean());

        final ParticipatingSiteServiceLocal participatingSiteServiceLocal = mock(ParticipatingSiteServiceLocal.class);
        final ParticipatingSiteDTO ps = new ParticipatingSiteDTO();
        ps.setIdentifier(IiConverter.convertToStudySiteIi(12345L));
        when(
                participatingSiteServiceLocal.createStudySiteParticipant(
                        any(StudySiteDTO.class),
                        any(StudySiteAccrualStatusDTO.class), any(Ii.class),
                        any(List.class)

                )).thenReturn(ps);
        when(
                participatingSiteServiceLocal.updateStudySiteParticipant(
                        any(StudySiteDTO.class),
                        any(StudySiteAccrualStatusDTO.class), any(List.class)

                )).thenReturn(ps);
        when(
                PaRegistry.getInstance().getServiceLocator()
                        .getParticipatingSiteService()).thenReturn(
                participatingSiteServiceLocal);

        StudySiteDTO site = new StudySiteDTO();
        site.setIdentifier(IiConverter.convertToStudySiteIi(12345L));
        site.setStudyProtocolIdentifier(spId);
        when(studySiteSvc.get(any(Ii.class))).thenReturn(site);
        when(
                participatingSiteServiceLocal.getParticipatingSite(
                        any(Ii.class), any(String.class)

                )).thenReturn(site);

        List<ParticipatingOrgDTO> rList = new ArrayList<ParticipatingOrgDTO>();
        participatingOrgDTO = new ParticipatingOrgDTO();
        participatingOrgDTO.setName("Site 1");
        participatingOrgDTO.setPoId("1");
        participatingOrgDTO.setProgramCodeText("Program Code Text");
        participatingOrgDTO.setRecruitmentStatus(RecruitmentStatusCode.ACTIVE);
        participatingOrgDTO.setRecruitmentStatusDate(new Timestamp((new Date())
                .getTime()));
        participatingOrgDTO.setStatusCode(FunctionalRoleStatusCode.PENDING);
        participatingOrgDTO.setStudySiteId(12345L);
        participatingOrgDTO.setTargetAccrualNumber(100);
        participatingOrgDTO.setDateOpenedForAccrual(DateUtils.parseDate(
                "01/01/2011", new String[] { "MM/dd/yyyy" }));
        participatingOrgDTO.setDateClosedForAccrual(DateUtils.parseDate(
                "01/01/2012", new String[] { "MM/dd/yyyy" }));

        List<PaPersonDTO> personList = new ArrayList<PaPersonDTO>();
        final PaPersonDTO inv = new PaPersonDTO();
        inv.setPaPersonId(1L);
        personList.add(inv);

        participatingOrgDTO.setPrimaryContacts(personList);
        participatingOrgDTO.setPrincipalInvestigators(personList);
        participatingOrgDTO.setSubInvestigators(personList);
        rList.add(participatingOrgDTO);

        ParticipatingOrgServiceLocal participatingOrgService = mock(ParticipatingOrgServiceLocal.class);
        when(participatingOrgService.getTreatingSites(any(Long.class)))
                .thenReturn(rList);
        when(
                PaRegistry.getInstance().getServiceLocator()
                        .getParticipatingOrgService()).thenReturn(
                participatingOrgService);

        UsernameHolder.setUser("jdoe01");

    }

    @Test
    public final void testGetSites() throws JAXBException, SAXException,
            PAException, NullifiedEntityException, NullifiedRoleException {
        Response r = service.getSites("pa", "1");
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        Sites el = (Sites) r.getEntity();
        verifySites(el);

    }

    private void verifySites(Sites s) {
        assertEquals(1, s.getSite().size());

        ParticipatingSite ps = s.getSite().get(0);
        assertEquals(participatingOrgDTO.getPoId(), ps.getOrganization()
                .getExistingOrganization().getPoID().toString());
        assertEquals(participatingOrgDTO.getRecruitmentStatus().getCode(), ps
                .getRecruitmentStatus().value());
        assertTrue(DateUtils.isSameDay(
                participatingOrgDTO.getRecruitmentStatusDate(), ps
                        .getRecruitmentStatusDate().toGregorianCalendar()
                        .getTime()));
        assertEquals(participatingOrgDTO.getLocalProtocolIdentifier(),
                ps.getLocalTrialIdentifier());
        assertEquals(participatingOrgDTO.getProgramCodeText(),
                ps.getProgramCode());
        assertTrue(DateUtils.isSameDay(
                participatingOrgDTO.getDateOpenedForAccrual(), ps
                        .getOpenedForAccrual().toGregorianCalendar().getTime()));
        assertTrue(DateUtils.isSameDay(
                participatingOrgDTO.getDateClosedForAccrual(), ps
                        .getClosedForAccrual().toGregorianCalendar().getTime()));
        assertEquals(participatingOrgDTO.getTargetAccrualNumber().intValue(),
                ps.getTargetAccrualNumber().intValue());

        assertEquals(participatingOrgDTO.getPrincipalInvestigators().get(0)
                .getPaPersonId(), ps.getInvestigator().get(0).getPerson()
                .getExistingPerson().getPoID());
        assertTrue(ps.getInvestigator().get(0).isPrimaryContact());
        assertEquals("Principal Investigator", ps.getInvestigator().get(0)
                .getRole());

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testCreate() throws JAXBException, SAXException,
            PAException, NullifiedEntityException, NullifiedRoleException {
        final String filename = "/ps_create.xml";
        createFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdate() throws JAXBException, SAXException,
            PAException, NullifiedEntityException, NullifiedRoleException {
        final String filename = "/ps_update.xml";
        updateFromXmlFileAndVerify(filename);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdateByPoId() throws JAXBException, SAXException,
            PAException, NullifiedEntityException, NullifiedRoleException {
        final String filename = "/ps_update.xml";
        ParticipatingSiteUpdate reg = readParticipatingSiteUpdateFromFile(filename);

        Response r = service.updateSite("pa", "1", 2, reg);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        String siteID = r.getEntity().toString();
        assertEquals("12345", siteID);
        captureAndVerifyArguments(reg);

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testUpdateByCtepId() throws JAXBException, SAXException,
            PAException, NullifiedEntityException, NullifiedRoleException {
        final String filename = "/ps_update.xml";
        ParticipatingSiteUpdate reg = readParticipatingSiteUpdateFromFile(filename);

        Response r = service.updateSite("pa", "1", "CTEP", reg);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        String siteID = r.getEntity().toString();
        assertEquals("12345", siteID);
        captureAndVerifyArguments(reg);

    }

    private void updateFromXmlFileAndVerify(final String filename)
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException, NullifiedRoleException {
        ParticipatingSiteUpdate reg = readParticipatingSiteUpdateFromFile(filename);
        updateAndVerify(12345, reg);
    }

    @SuppressWarnings("unchecked")
    private ParticipatingSiteUpdate readParticipatingSiteUpdateFromFile(
            String filename) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = getClass().getResource(filename);
        ParticipatingSiteUpdate o = ((JAXBElement<ParticipatingSiteUpdate>) u
                .unmarshal(url)).getValue();
        return o;
    }

    private void createFromXmlFileAndVerify(final String filename)
            throws JAXBException, SAXException, PAException,
            NullifiedEntityException, NullifiedRoleException {
        ParticipatingSite reg = readParticipatingSiteFromFile(filename);
        createAndVerify("pa", "1", reg);
    }

    @SuppressWarnings("unchecked")
    private void updateAndVerify(long siteIDLong, ParticipatingSiteUpdate reg)
            throws PAException, NullifiedEntityException,
            NullifiedRoleException {
        Response r = service.updateSite(siteIDLong, reg);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        String siteID = r.getEntity().toString();
        assertEquals("12345", siteID);
        captureAndVerifyArguments(reg);
    }

    @SuppressWarnings("unchecked")
    private void createAndVerify(String idType, String trialID,
            ParticipatingSite reg) throws PAException,
            NullifiedEntityException, NullifiedRoleException {
        Response r = service.addSite(idType, trialID, reg);
        assertEquals(Status.OK.getStatusCode(), r.getStatus());
        String siteID = r.getEntity().toString();
        assertEquals("12345", siteID);

        captureAndVerifyArguments(reg);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void captureAndVerifyArguments(ParticipatingSiteUpdate ps)
            throws PAException, NullifiedEntityException,
            NullifiedRoleException {
        ArgumentCaptor<StudySiteDTO> studySiteDTO = ArgumentCaptor
                .forClass(StudySiteDTO.class);
        ArgumentCaptor<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTO = ArgumentCaptor
                .forClass(StudySiteAccrualStatusDTO.class);
        ArgumentCaptor<List> participatingSiteContactDTOList = ArgumentCaptor
                .forClass(List.class);
        verify(PaRegistry.getParticipatingSiteService())
                .updateStudySiteParticipant(studySiteDTO.capture(),
                        studySiteAccrualStatusDTO.capture(),
                        participatingSiteContactDTOList.capture()

                );

        verifyStudySiteDTO(ps, studySiteDTO.getValue());
        verifyStudySiteAccrualStatusDTO(ps,
                studySiteAccrualStatusDTO.getValue());
        verifyParticipatingSiteContactDTOList(ps,
                participatingSiteContactDTOList.getValue());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void captureAndVerifyArguments(ParticipatingSite ps)
            throws PAException, NullifiedEntityException,
            NullifiedRoleException {
        ArgumentCaptor<StudySiteDTO> studySiteDTO = ArgumentCaptor
                .forClass(StudySiteDTO.class);
        ArgumentCaptor<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTO = ArgumentCaptor
                .forClass(StudySiteAccrualStatusDTO.class);
        ArgumentCaptor<Ii> poHealthFacilID = ArgumentCaptor.forClass(Ii.class);
        ArgumentCaptor<List> participatingSiteContactDTOList = ArgumentCaptor
                .forClass(List.class);
        verify(PaRegistry.getParticipatingSiteService())
                .createStudySiteParticipant(studySiteDTO.capture(),
                        studySiteAccrualStatusDTO.capture(),
                        poHealthFacilID.capture(),
                        participatingSiteContactDTOList.capture()

                );

        verifyStudySiteDTO(ps, studySiteDTO.getValue());
        verifyStudySiteAccrualStatusDTO(ps,
                studySiteAccrualStatusDTO.getValue());

        verifyHCF((ParticipatingSite) ps, poHealthFacilID.getValue());

        verifyParticipatingSiteContactDTOList(ps,
                participatingSiteContactDTOList.getValue());

    }

    private void verifyParticipatingSiteContactDTOList(
            BaseParticipatingSite ps, List<ParticipatingSiteContactDTO> list)
            throws NullifiedEntityException {
        final PrimaryContact pc = ps.getPrimaryContact();
        for (int i = 0; i < ps.getInvestigator().size(); i++) {
            BaseParticipatingSite.Investigator inv = ps.getInvestigator()
                    .get(i);
            ParticipatingSiteContactDTO dto = list.get(i);
            assertEquals(inv.getPerson().getExistingPerson().getPoID()
                    .toString(), dto.getPersonDTO().getIdentifier()
                    .getExtension());
            assertEquals(inv.getPerson().getExistingPerson().getPoID()
                    .toString(), dto.getAbstractPersonRoleDTO()
                    .getPlayerIdentifier().getExtension());
            assertEquals("2", dto.getAbstractPersonRoleDTO()
                    .getScoperIdentifier().getExtension());

            StudySiteContactDTO studySiteContact = dto.getStudySiteContactDTO();
            assertEquals(inv.isPrimaryContact() && pc == null,
                    BlConverter.convertToBool(studySiteContact
                            .getPrimaryIndicator()));
            assertEquals(inv.getRole(),
                    CdConverter.convertCdToString(studySiteContact
                            .getRoleCode()));
            assertEquals(
                    PoRegistry
                            .getPersonEntityService()
                            .getPerson(
                                    IiConverter.convertToPoPersonIi(inv
                                            .getPerson().getExistingPerson()
                                            .getPoID().toString()))
                            .getTelecomAddress(),
                    studySiteContact.getTelecomAddresses());
            assertEquals(
                    PoRegistry
                            .getPersonEntityService()
                            .getPerson(
                                    IiConverter.convertToPoPersonIi(inv
                                            .getPerson().getExistingPerson()
                                            .getPoID().toString()))
                            .getPostalAddress(),
                    studySiteContact.getPostalAddress());
        }

        if (pc != null) {
            ParticipatingSiteContactDTO dto = list.get(list.size() - 1);
            assertEquals(pc.getPerson().getExistingPerson().getPoID()
                    .toString(), dto.getPersonDTO().getIdentifier()
                    .getExtension());
            assertEquals(pc.getPerson().getExistingPerson().getPoID()
                    .toString(), dto.getAbstractPersonRoleDTO()
                    .getPlayerIdentifier().getExtension());
            assertEquals("2", dto.getAbstractPersonRoleDTO()
                    .getScoperIdentifier().getExtension());

            StudySiteContactDTO studySiteContact = dto.getStudySiteContactDTO();
            assertTrue(BlConverter.convertToBool(studySiteContact
                    .getPrimaryIndicator()));
            assertEquals("Primary Contact",
                    CdConverter.convertCdToString(studySiteContact
                            .getRoleCode()));

            final Iterator<Tel> iterator = studySiteContact
                    .getTelecomAddresses().getItem().iterator();
            assertEquals(
                    "mailto:"
                            + URLEncoder.encode(pc.getContactDetails()
                                    .getContent().get(0).getValue()), iterator
                            .next().getValue().toString());
            assertEquals(
                    "tel:"
                            + URLEncoder.encode(pc.getContactDetails()
                                    .getContent().get(1).getValue()), iterator
                            .next().getValue().toString());
        }
    }

    private void verifyStudySiteAccrualStatusDTO(BaseParticipatingSite ps,
            StudySiteAccrualStatusDTO value) {
        assertEquals(
                ps.getRecruitmentStatus().value(),
                CdConverter.convertCdToEnum(RecruitmentStatusCode.class,
                        value.getStatusCode()).getCode());
        assertEquals(ps.getRecruitmentStatusDate().toGregorianCalendar()
                .getTime(),
                TsConverter.convertToTimestamp(value.getStatusDate()));
    }

    private void verifyHCF(ParticipatingSite ps, Ii hcfIi)
            throws NullifiedRoleException {
        assertEquals(ps.getOrganization().getExistingOrganization().getPoID()
                .toString(), PoRegistry
                .getHealthCareFacilityCorrelationService()
                .getCorrelation(hcfIi).getPlayerIdentifier().getExtension());

    }

    private void verifyStudySiteDTO(BaseParticipatingSite ps, StudySiteDTO ss)
            throws NullifiedRoleException {
        assertEquals(ps.getLocalTrialIdentifier(),
                StConverter.convertToString(ss
                        .getLocalStudyProtocolIdentifier()));
        assertEquals(ps.getProgramCode(),
                StConverter.convertToString(ss.getProgramCodeText()));
        assertEquals(ps.getOpenedForAccrual().toGregorianCalendar().getTime(),
                IvlConverter.convertTs().convertLow(ss.getAccrualDateRange()));
        assertEquals(ps.getClosedForAccrual().toGregorianCalendar().getTime(),
                IvlConverter.convertTs().convertHigh(ss.getAccrualDateRange()));
        assertEquals(ps.getTargetAccrualNumber().intValue(), IntConverter
                .convertToInteger(ss.getTargetAccrualNumber()).intValue());

    }

    @SuppressWarnings("unchecked")
    private ParticipatingSite readParticipatingSiteFromFile(String string)
            throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller u = jc.createUnmarshaller();
        URL url = getClass().getResource(string);
        ParticipatingSite o = ((JAXBElement<ParticipatingSite>) u
                .unmarshal(url)).getValue();
        return o;
    }
}

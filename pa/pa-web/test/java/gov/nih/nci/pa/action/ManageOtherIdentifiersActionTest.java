package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.StudyIdentifierDTO;
import gov.nih.nci.pa.enums.StudyIdentifierType;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.MockPAServiceUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * 
 * @author Reshma Koganti
 * 
 */
public class ManageOtherIdentifiersActionTest extends AbstractPaActionTest {
    ManageOtherIdentifiersAction action;
    MockPAServiceUtils util;
    final List<StudyIdentifierDTO> identifiers = new ArrayList<StudyIdentifierDTO>();

    @SuppressWarnings("deprecation")
    Ii spID = IiConverter.convertToIi(1L);

    @SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
    @Before
    public void setUp() throws PAException {
        action = new ManageOtherIdentifiersAction();

        getRequest().setupAddParameter("otherIdentifier", "NCT1111111111");
        getRequest().setupAddParameter("otherIdentifierType",
                StudyIdentifierType.OBSOLETE_CTGOV.getCode());
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, spID);

        initIdentifiersCollectionAndMocks();

    }

    /**
     * @throws PAException
     * 
     */
    @SuppressWarnings("rawtypes")
    private void initIdentifiersCollectionAndMocks() throws PAException {
        identifiers.clear();
        identifiers.addAll(Arrays.asList(new StudyIdentifierDTO[] {
                new StudyIdentifierDTO(StudyIdentifierType.CTEP, "CTEP"),
                new StudyIdentifierDTO(StudyIdentifierType.CTGOV, "CTGOV"),
                new StudyIdentifierDTO(StudyIdentifierType.DCP, "DCP"),
                new StudyIdentifierDTO(StudyIdentifierType.CCR, "CCR"),
                new StudyIdentifierDTO(StudyIdentifierType.LEAD_ORG_ID,
                        "LEAD_ORG_ID"),
                new StudyIdentifierDTO(StudyIdentifierType.OTHER, "OTHER"), }));

        when(PaRegistry.getStudyIdentifiersService().getStudyIdentifiers(spID))
                .thenReturn(identifiers);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                identifiers.add((StudyIdentifierDTO) invocation.getArguments()[1]);
                return null;
            }
        }).when(PaRegistry.getStudyIdentifiersService()).add(eq(spID),
                any(StudyIdentifierDTO.class));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                identifiers.remove((StudyIdentifierDTO) invocation
                        .getArguments()[1]);
                return null;
            }
        }).when(PaRegistry.getStudyIdentifiersService()).delete(eq(spID),
                any(StudyIdentifierDTO.class));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((StudyIdentifierDTO) invocation.getArguments()[1])
                        .setValue((String) invocation.getArguments()[2]);
                return null;
            }
        }).when(PaRegistry.getStudyIdentifiersService()).update(eq(spID),
                any(StudyIdentifierDTO.class), any(String.class));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void query() throws PAException {
        initIdentifiersCollectionAndMocks();
        final HttpSession session = ServletActionContext.getRequest()
                .getSession();
        assertEquals("display_otherIdentifiers", action.query());
        List<StudyIdentifierDTO> studyIdentifiers = (List<StudyIdentifierDTO>) session
                .getAttribute(Constants.OTHER_IDENTIFIERS_LIST);
        assertEquals(identifiers, studyIdentifiers);

        List<StudyIdentifierType> types = (List<StudyIdentifierType>) session
                .getAttribute(Constants.OTHER_IDENTIFIERS_TYPES_LIST);
        assertEquals(3, types.size());
        assertTrue(types.contains(StudyIdentifierType.OBSOLETE_CTGOV));
        assertTrue(types.contains(StudyIdentifierType.DUPLICATE_NCI));
        assertTrue(types.contains(StudyIdentifierType.OTHER));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void addOtherIdentifierTest() throws PAException {
        initIdentifiersCollectionAndMocks();
        final HttpSession session = ServletActionContext.getRequest()
                .getSession();
        assertEquals("display_otherIdentifiers", action.addOtherIdentifier());
        List<StudyIdentifierDTO> studyIdentifiers = (List<StudyIdentifierDTO>) session
                .getAttribute(Constants.OTHER_IDENTIFIERS_LIST);
        assertEquals(7, studyIdentifiers.size());
        assertEquals("Identifier added to the trial", ServletActionContext
                .getRequest().getAttribute(Constants.SUCCESS_MESSAGE));

        for (StudyIdentifierDTO dto : studyIdentifiers) {
            if (dto.getType().equals(StudyIdentifierType.OBSOLETE_CTGOV)
                    && dto.getValue().equals("NCT1111111111")) {
                return;
            }
        }
        Assert.fail();

    }

    @SuppressWarnings("unchecked")
    @Test
    public void deleteOtherIdentifiertest() throws PAException {
        getRequest().setupAddParameter("uuid", "1");
        initIdentifiersCollectionAndMocks();
        final HttpSession session = ServletActionContext.getRequest()
                .getSession();
        session.setAttribute(Constants.OTHER_IDENTIFIERS_LIST, identifiers);
        assertEquals("display_otherIdentifiers", action.deleteOtherIdentifier());
        List<StudyIdentifierDTO> studyIdentifiers = (List<StudyIdentifierDTO>) session
                .getAttribute(Constants.OTHER_IDENTIFIERS_LIST);
        assertEquals(5, studyIdentifiers.size());
        assertEquals("Identifier deleted from the trial", ServletActionContext
                .getRequest().getAttribute(Constants.SUCCESS_MESSAGE));

        for (StudyIdentifierDTO dto : studyIdentifiers) {
            if (dto.getType().equals(StudyIdentifierType.CTEP)) {
                Assert.fail();
            }
        }

    }

    @SuppressWarnings("unchecked")
    @Test
    public void saveOtherIdentifierRowTest() throws PAException {
        getRequest().setupAddParameter("uuid", "1");
        getRequest().setupAddParameter("otherIdentifier", "NEW_CTEP");
        initIdentifiersCollectionAndMocks();
        final HttpSession session = ServletActionContext.getRequest()
                .getSession();
        session.setAttribute(Constants.OTHER_IDENTIFIERS_LIST, identifiers);
        assertEquals("display_otherIdentifiers",
                action.saveOtherIdentifierRow());
        List<StudyIdentifierDTO> studyIdentifiers = (List<StudyIdentifierDTO>) session
                .getAttribute(Constants.OTHER_IDENTIFIERS_LIST);
        assertEquals(6, studyIdentifiers.size());
        assertEquals("New identifier value saved", ServletActionContext
                .getRequest().getAttribute(Constants.SUCCESS_MESSAGE));

        for (StudyIdentifierDTO dto : studyIdentifiers) {
            if (dto.getType().equals(StudyIdentifierType.CTEP)) {
                assertEquals("NEW_CTEP", dto.getValue());
            }
        }

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testErrorHandling() throws PAException {
        getRequest().setupAddParameter("uuid", "1");
        getRequest().setupAddParameter("otherIdentifier", "NEW_CTEP");
        initIdentifiersCollectionAndMocks();

        doThrow(new PAException("testErrorHandling")).when(
                PaRegistry.getStudyIdentifiersService()).add(eq(spID),
                any(StudyIdentifierDTO.class));

        doThrow(new PAException("testErrorHandling")).when(
                PaRegistry.getStudyIdentifiersService()).delete(eq(spID),
                any(StudyIdentifierDTO.class));

        doThrow(new PAException("testErrorHandling")).when(
                PaRegistry.getStudyIdentifiersService()).update(eq(spID),
                any(StudyIdentifierDTO.class), any(String.class));

        ServletActionContext.getRequest().removeAttribute(
                Constants.FAILURE_MESSAGE);
        action.addOtherIdentifier();
        assertEquals("testErrorHandling", ServletActionContext.getRequest()
                .getAttribute(Constants.FAILURE_MESSAGE));
        
        ServletActionContext.getRequest().removeAttribute(
                Constants.FAILURE_MESSAGE);
        action.deleteOtherIdentifier();
        assertEquals("testErrorHandling", ServletActionContext.getRequest()
                .getAttribute(Constants.FAILURE_MESSAGE));
        
        ServletActionContext.getRequest().removeAttribute(
                Constants.FAILURE_MESSAGE);
        action.saveOtherIdentifierRow();
        assertEquals("testErrorHandling", ServletActionContext.getRequest()
                .getAttribute(Constants.FAILURE_MESSAGE));



    }

    @Test
    public void showWaitDialogTest() {
        assertEquals("show_ok_create", action.showWaitDialog());
    }
}

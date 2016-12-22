package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.CheckOutType;
import gov.nih.nci.pa.iso.dto.StudyCheckoutDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StudyCheckoutServiceBeanTest extends AbstractHibernateTestCase {

    private final StudyCheckoutServiceBean localEjb = new StudyCheckoutServiceBean();
    Ii pid;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
      TestSchema.primeData();
      pid = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
      CSMUserService.setInstance(new MockCSMUserService());
    }

    @Test
    public void get() throws Exception {
        List<StudyCheckoutDTO> statusList = localEjb.getByStudyProtocol(pid);
        assertEquals(2, statusList.size());
        StudyCheckoutDTO dto = localEjb.get(statusList.get(0).getIdentifier());
        assertEquals(IiConverter.convertToLong(statusList.get(0).getIdentifier())
                , (IiConverter.convertToLong(dto.getIdentifier())));
        assertTrue(dto.getUserIdentifier().getValue().equals("Abstractor"));
      }

    @Test
    public void create() throws Exception {
        expectedEx.expect(PAException.class);
        expectedEx.expectMessage("Creates should be done using the checkOut() method.");
        localEjb.create(new StudyCheckoutDTO());
    }

    @Test
    public void update() throws Exception {
        expectedEx.expect(PAException.class);
        expectedEx.expectMessage("Updates disabled. Use checkIn() and checkOut().");
        localEjb.update(new StudyCheckoutDTO());
    }

    @Test
    public void delete() throws Exception {
        expectedEx.expect(PAException.class);
        expectedEx.expectMessage("Deletes should be done using the checkIn() method.");
        localEjb.delete(new Ii());
    }

    @Test
    public void checkOutAdmin() throws Exception {
        checkOutTest(CheckOutType.ADMINISTRATIVE);
    }

    @Test
    public void checkOutScientific() throws Exception {
        checkOutTest(CheckOutType.SCIENTIFIC);
    }

    @Test
    public void checkInAdmin() throws Exception {
        checkInTest(CheckOutType.ADMINISTRATIVE);
    }

    @Test
    public void checkInScientific() throws Exception {
        checkInTest(CheckOutType.SCIENTIFIC);
    }
    
    @Test
    public void testHandleTrialAssigneeChange() throws Exception {
        if (getCurrentCheckout(pid, CheckOutType.ADMINISTRATIVE) != null) {
            localEjb.checkIn(pid, CdConverter.convertToCd(CheckOutType.ADMINISTRATIVE), StConverter.convertToSt("user1"), null);
        }
        localEjb.handleTrialAssigneeChange(new Long(pid.getExtension()));
        StudyCheckoutDTO dto = getCurrentCheckout(pid, CheckOutType.ADMINISTRATIVE);
        assertNotNull(dto);
        assertEquals(IiConverter.convertToString(pid), IiConverter.convertToString(dto.getStudyProtocolIdentifier()));
        assertEquals(CheckOutType.ADMINISTRATIVE,
                CdConverter.convertCdToEnum(CheckOutType.class, dto.getCheckOutTypeCode()));        

        Session session = PaHibernateUtil.getCurrentSession();
        session.createSQLQuery("update study_protocol set assigned_user_id=null where identifier="+pid.getExtension()).executeUpdate();
        session.flush();
        
        localEjb.handleTrialAssigneeChange(new Long(pid.getExtension()));
        
        List<StudyCheckoutDTO> dtos = localEjb.getByStudyProtocol(pid);        
        for (StudyCheckoutDTO dto2 : dtos) {
            if (ISOUtil.isTsNull(dto2.getCheckInDate())) {
                fail();
            }
        }
               
    }

    private void checkOutTest(CheckOutType type) throws Exception {
        if (getCurrentCheckout(pid, type) != null) {
            localEjb.checkIn(pid, CdConverter.convertToCd(type), StConverter.convertToSt("user1"), null);
        }
        localEjb.checkOut(pid, CdConverter.convertToCd(type), StConverter.convertToSt("user1"));
        StudyCheckoutDTO dto = getCurrentCheckout(pid, type);
        assertNotNull(dto);
        assertEquals(IiConverter.convertToString(pid), IiConverter.convertToString(dto.getStudyProtocolIdentifier()));
        assertEquals(type,
                CdConverter.convertCdToEnum(CheckOutType.class, dto.getCheckOutTypeCode()));
        assertEquals("user1", StConverter.convertToString(dto.getUserIdentifier()));

        // try to check out again
        expectedEx.expect(PAException.class);
        expectedEx.expectMessage("Study already checked out.");
        localEjb.checkOut(pid, CdConverter.convertToCd(type), StConverter.convertToSt("user2"));
    }

    private void checkInTest(CheckOutType type) throws Exception {
        if (getCurrentCheckout(pid, type) != null) {
            localEjb.checkIn(pid, CdConverter.convertToCd(type), StConverter.convertToSt("user1"), null);
        }
        localEjb.checkOut(pid, CdConverter.convertToCd(type), StConverter.convertToSt("user1"));
        String user = UUID.randomUUID().toString();
        String comment = UUID.randomUUID().toString();
        localEjb.checkIn(pid, CdConverter.convertToCd(type), StConverter.convertToSt(user),
                StConverter.convertToSt(comment));
        List<StudyCheckoutDTO> dtos = localEjb.getByStudyProtocol(pid);
        boolean bFound = false;
        for (StudyCheckoutDTO dto : dtos) {
            if (ISOUtil.isTsNull(dto.getCheckInDate())) {
                continue;
            }
            if (type == CdConverter.convertCdToEnum(CheckOutType.class, dto.getCheckOutTypeCode())) {
                if (user == StConverter.convertToString(dto.getCheckInUserIdentifier())) {
                    bFound = true;
                    assertEquals(comment, StConverter.convertToString(dto.getCheckInComment()));
                }
            }
        }
        assertTrue(bFound);
    }

    private StudyCheckoutDTO getCurrentCheckout(Ii spIi, CheckOutType type) throws Exception {
        StudyCheckoutDTO result = null;
        List<StudyCheckoutDTO> dtos = localEjb.getByStudyProtocol(spIi);
        for (StudyCheckoutDTO dto : dtos) {
            if (!ISOUtil.isTsNull(dto.getCheckInDate())) {
                continue;
            }
            if (type == CdConverter.convertCdToEnum(CheckOutType.class, dto.getCheckOutTypeCode())) {
                if (result == null) {
                    result = dto;
                } else {
                    fail("More than one active checkout found for: " + type.getCode());
                }
            }
        }
        return result;
    }
}

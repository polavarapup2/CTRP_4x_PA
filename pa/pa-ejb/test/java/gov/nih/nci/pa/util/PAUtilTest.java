/**
 *
 */
package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.dto.MilestonesDTO;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.iso.dto.StudyCheckoutDTO;
import gov.nih.nci.pa.iso.dto.StudyDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter.JavaPq;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySourceInterceptor;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.SessionContext;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.authentication.CommonsGridLoginModule;

/**
 * @author asharma
 * 
 */
public class PAUtilTest {

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#isValidIi(gov.nih.nci.iso21090.Ii, gov.nih.nci.iso21090.Ii)}
     * .
     */
    @Test
    public void testIsValidIi1() throws PAException {
        assertTrue(PAUtil.isValidIi(IiConverter.convertToStudyProtocolIi(1L),
                IiConverter.convertToStudyProtocolIi(null)));
        Ii ii = new Ii();
        ii.setIdentifierName(IiConverter.STUDY_PROTOCOL_IDENTIFIER_NAME);
        Ii ii2 = new Ii();
        ii2.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME);
        try {
            PAUtil.isValidIi(ii, ii2);
        } catch (Exception e) {
            // PA exception
        }
        ii = new Ii();
        ii.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        ii2 = new Ii();
        ii2.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);
        try {
            PAUtil.isValidIi(ii, ii2);
        } catch (Exception e) {
            // PA exception
        }
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#isValidIi(gov.nih.nci.iso21090.Ii, gov.nih.nci.iso21090.Ii)}
     * .
     */
    @Test(expected = PAException.class)
    public void testIsValidIi2() throws PAException {
        PAUtil.isValidIi(null, null);
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#convertTsToFormarttedDate(gov.nih.nci.iso21090.Ts, java.lang.String)}
     * .
     */
    @Test
    public void testConvertTsToFormarttedDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 10, 16); // month field is 0-based, so 10 is november
        String date = PAUtil.convertTsToFormattedDate(
                TsConverter.convertToTs(new Timestamp(cal.getTimeInMillis())),
                "yyyy-MM");
        assertEquals("2009-11", date);
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#convertTsToFormattedDate(gov.nih.nci.iso21090.Ts)}
     * .
     */
    @Test
    public void testConvertTsToDefaultFormattedDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 0, 16); // month field is 0-based, so 0 is january
        String date = PAUtil.convertTsToFormattedDate(TsConverter
                .convertToTs(new Timestamp(cal.getTimeInMillis())));
        assertEquals("01/16/2009", date);
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#normalizeDateStringWithTime(java.lang.String)}
     * .
     */
    @Test
    public void normalizeDateStringWithTime() {
        assertNull(PAUtil.normalizeDateStringWithTime(null));
        assertNotNull(PAUtil.normalizeDateStringWithTime("01/16/2009"));
        assertEquals(PAUtil.normalizeDateStringWithTime("01/16/2009"),
                "2009-01-16 00:00:00");
    }
    
    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#normalizeTimeString(java.lang.String)}
     * .
     */
    @Test
    public void normalizeTimeString() {
        assertNull(PAUtil.normalizeTimeString(null));
        assertNotNull(PAUtil.normalizeTimeString("01/16/2009"));
        assertEquals("00:00:00", PAUtil.normalizeTimeString("01/16/2009"));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#normalizeDateString(java.lang.String)}.
     */
    @Test
    public void testNormalizeDateString() {
        assertEquals("01/31/2001",
                PAUtil.normalizeDateString("1/31/2001abcdefg"));
        assertEquals("01/31/2001",
                PAUtil.normalizeDateString("2001-01-31abcdefg"));
        assertNull(PAUtil.normalizeDateString("Tuesday"));
        assertNull(PAUtil.normalizeDateString(null));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#normalizeDateStringWithTime(java.lang.String)}
     * .
     */
    @Test
    public void testNormalizeDateStringWithTime() {
        assertNull(PAUtil.normalizeDateStringWithTime(null));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#dateStringToTimestamp(java.lang.String)}
     * .
     */
    @Test
    public void testDateStringToTimestamp() {
        Timestamp now = new Timestamp(new Date().getTime());
        assertTrue(now.after(PAUtil.dateStringToTimestamp(now.toString())));
        assertNull(PAUtil.dateStringToTimestamp(null));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#today()}.
     */
    @Test
    public void testToday() {
        assertNotNull(PAUtil.today());
    }    

    @Test
    public void testYesOrNo() {
        assertEquals(true, PAUtil.isYesNo("Yes"));
        assertEquals(true, PAUtil.isYesNo("NO"));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#isValidEmail(java.lang.String)}.
     */
    @Test
    public void testIsValidEmail() {
        assertTrue(PAUtil.isValidEmail("a@a.com"));
        assertFalse(PAUtil.isValidEmail(null));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#isValidPhone(java.lang.String)}.
     */
    @Test
    public void testIsValidPhone() {
        assertTrue(PAUtil.isValidPhone("111111"));
        assertFalse(PAUtil.isValidPhone(null));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#wildcardCriteria(java.lang.String)}.
     */
    @Test
    public void testWildcardCriteria() {
        assertEquals("", PAUtil.wildcardCriteria(null));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#isGreaterThan(gov.nih.nci.iso21090.St, int)}
     * .
     */
    @Test
    public void testIsGreaterThan() {
        assertTrue(PAUtil.isGreaterThan(StConverter.convertToSt("hello"), 2));
        assertFalse(PAUtil.isGreaterThan(null, 2));
        St st = new St();
        st.setValue(null);
        assertFalse(PAUtil.isGreaterThan(st, 2));
        st.setValue("hello");
        assertTrue(PAUtil.isGreaterThan(st, 2));
        assertFalse(PAUtil.isGreaterThan(st, 6));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#isWithinRange(gov.nih.nci.iso21090.St, int, int)}
     * .
     */
    @Test
    public void testIsWithinRange() {
        assertTrue(PAUtil.isWithinRange(StConverter.convertToSt("hello"), 2, 6));
        assertTrue(PAUtil.isWithinRange(null, 2, 6));
        St st = new St();
        st.setValue(null);
        assertTrue(PAUtil.isWithinRange(st, 2, 6));
        st.setValue("hello");
        assertFalse(PAUtil.isWithinRange(st, 2, 3));
        assertTrue(PAUtil.isWithinRange(st, 2, 10));

    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#getIiExtension(gov.nih.nci.iso21090.Ii)}
     * .
     */
    @Test
    public void testGetIiExtension() {
        assertEquals("1", PAUtil.getIiExtension(IiConverter.convertToIi("1")));
        assertEquals("", PAUtil.getIiExtension(null));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#getErrorMsg(java.util.Map)}.
     */
    @Test
    public void testGetErrorMsg() {
        Map<String, String[]> errMap = new HashMap<String, String[]>();
        assertEquals("", PAUtil.getErrorMsg(errMap));
        String key = "key";
        String keyArr[] = { "k", "e", "y" };
        errMap.put(key, keyArr);
        assertEquals("key -  k, e, y ", PAUtil.getErrorMsg(errMap));

    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#containsIi(java.util.Map, gov.nih.nci.iso21090.Ii)}
     * .
     */
    @Test
    public void testContainsIi() {
        Map<Ii, Ii> iiMap = null;
        Ii iiKey = null;
        assertNull(PAUtil.containsIi(iiMap, iiKey));
        iiMap = new HashMap<Ii, Ii>();
        assertNull(PAUtil.containsIi(iiMap, iiKey));
        iiKey = IiConverter.convertToIi("1");
        assertNull(PAUtil.containsIi(iiMap, iiKey));
        iiMap.put(IiConverter.convertToIi("1"), IiConverter.convertToIi("1"));
        iiMap.put(IiConverter.convertToIi("2"), IiConverter.convertToIi("2"));
        iiKey = IiConverter.convertToIi("1");
        assertEquals(PAUtil.containsIi(iiMap, iiKey).getExtension(), "1");

    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#getFirstObj(java.util.List)}.
     */
    @Test
    public void testGetFirstObj() {
        List<StudyDTO> studyList = null;
        assertNull(PAUtil.getFirstObj(studyList));
        StudyCheckoutDTO scDto = new StudyCheckoutDTO();
        studyList = new ArrayList<StudyDTO>();
        assertNull(PAUtil.getFirstObj(studyList));
        studyList.add(scDto);
        assertNotNull(PAUtil.getFirstObj(studyList));
    }

    /*
    *//**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#getDocumentFilePath(java.lang.Long, java.lang.String, java.lang.String)}
     * .
     */
    /*
     * @Test public void testGetDocumentFilePath() throws PAException {
     * PAUtil.getDocumentFilePath(1L, "IRB.doc", "1"); }
     */

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#isDateCurrentOrPast(java.lang.String)}.
     */
    @Test
    public void testIsDateCurrentOrPastString() {
        assertFalse(PAUtil.isDateCurrentOrPast("10/29/2009"));
        assertTrue(PAUtil.isDateCurrentOrPast("10/29/2999"));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#isDateCurrentOrPast(java.sql.Timestamp)}
     * .
     */
    @Test
    public void testIsDateCurrentOrPastTimestamp() {
        assertFalse(PAUtil.isDateCurrentOrPast(new Timestamp(new Date()
                .getTime())));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#isValidDate(java.lang.String)}.
     */
    @Test
    public void testIsValidDate() {
        assertFalse(PAUtil.isValidDate(""));
        assertFalse(PAUtil.isValidDate("abcbs"));
        assertTrue(PAUtil.isValidDate("01/01/2010"));
        assertFalse(PAUtil.isValidDate("31/01/2010"));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#convertStringToDecimal(java.lang.String)}
     * .
     */
    @Test
    public void testConvertStringToDecimal() {
        assertNotNull(PAUtil.convertStringToDecimal("2"));
        assertNull(PAUtil.convertStringToDecimal(null));
        assertNull(PAUtil.convertStringToDecimal("abc"));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#convertPqToUnit(gov.nih.nci.pa.iso.util.IvlConverter.JavaPq)}
     * .
     */
    @Test
    public void testConvertPqToUnit() {
        assertNull(PAUtil.convertPqToUnit(null));
        JavaPq jPq = new JavaPq("kg", new BigDecimal("100"), new Integer(1));
        assertNotNull(PAUtil.convertPqToUnit(jPq));
        assertEquals(PAUtil.convertPqToUnit(jPq), "kg");
        jPq = new JavaPq(null, new BigDecimal("100"), new Integer(1));
        assertNull(PAUtil.convertPqToUnit(jPq));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#convertPqToDecimal(gov.nih.nci.pa.iso.util.IvlConverter.JavaPq)}
     * .
     */
    @Test
    public void testConvertPqToDecimal() {
        assertNull(PAUtil.convertPqToDecimal(null));
        JavaPq jPq = new JavaPq(null, null, new Integer(1));
        assertNull(PAUtil.convertPqToDecimal(null));
        jPq = new JavaPq("kg", new BigDecimal("100"), new Integer(1));
        assertNotNull(PAUtil.convertPqToDecimal(jPq));
        assertEquals(PAUtil.convertPqToDecimal(jPq), new BigDecimal("100"));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#convertPqToPrecision(gov.nih.nci.pa.iso.util.IvlConverter.JavaPq)}
     * .
     */
    @Test
    public void testConvertPqToPrecision() {
        assertNull(PAUtil.convertPqToPrecision(null));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#isTypeIntervention(gov.nih.nci.iso21090.Cd)}
     * .
     */
    @Test
    public void testIsTypeIntervention() {
        assertTrue(PAUtil
                .isTypeIntervention(CdConverter
                        .convertStringToCd(ActivityCategoryCode.INTERVENTION
                                .getCode())));
        assertTrue(PAUtil.isTypeIntervention(CdConverter
                .convertStringToCd(ActivityCategoryCode.PLANNED_PROCEDURE
                        .getCode())));
        assertTrue(PAUtil
                .isTypeIntervention(CdConverter
                        .convertStringToCd(ActivityCategoryCode.SUBSTANCE_ADMINISTRATION
                                .getCode())));
        assertFalse(PAUtil.isTypeIntervention(CdConverter
                .convertStringToCd(ActivityCategoryCode.COURSE.getCode())));

    }

    /**
     * Test method for {@link
     * gov.nih.nci.pa.util.PAUtil#isDSetTelNull(gov.nih.nci.iso21090.DSet<Tel>)}
     * .
     */
    @Test
    public void testIsDSetTelNull() {
        assertTrue(PAUtil.isDSetTelNull(null));
        DSet<Tel> telecomAddresses = new DSet<Tel>();
        assertTrue(PAUtil.isDSetTelNull(telecomAddresses));
        List<String> st = new ArrayList<String>();
        st.add("email:n.n.com");
        DSetConverter.convertListToDSet(st, "EMAIL", telecomAddresses);
        assertTrue(PAUtil.isDSetTelNull(telecomAddresses));
        assertFalse(PAUtil.isDSetTelAndEmailNull(telecomAddresses));
        st = new ArrayList<String>();
        st.add("tel:111-111-1111");
        DSetConverter.convertListToDSet(st, "PHONE", telecomAddresses);
        assertFalse(PAUtil.isDSetTelNull(telecomAddresses));
    }

    /**
     * Test method for {@link
     * gov.nih.nci.pa.util.PAUtil#getEmail(gov.nih.nci.iso21090.DSet<Tel>)}.
     */
    @Test
    public void testGetEmail() {
        assertNull(PAUtil.getEmail((DSet) null));
        DSet<Tel> telecomAddresses = new DSet<Tel>();
        assertNull(PAUtil.getEmail(telecomAddresses));
        List<String> st = new ArrayList<String>();
        st.add("email:n.n.com");
        DSetConverter.convertListToDSet(st, "EMAIL", telecomAddresses);
        assertNotNull(PAUtil.getEmail(telecomAddresses));
        assertEquals(PAUtil.getEmail(telecomAddresses), "email:n.n.com");
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#getEmail(Tel)}.
     * 
     * @throws URISyntaxException
     */
    @Test
    public void testGetEmailFromTel() throws URISyntaxException {
        assertNull(PAUtil.getEmail((Tel) null));
        assertNull(PAUtil.getEmail(new Tel()));

        Tel tel = new Tel();
        tel.setValue(new URI("mailto:denis.krylov@semanticbits.com"));
        assertEquals(PAUtil.getEmail(tel), "denis.krylov@semanticbits.com");

        tel = new Tel();
        tel.setValue(new URI("https://tracker.nci.nih.gov/browse/PO-3441"));
        assertNull(PAUtil.getEmail(tel));

        tel = new Tel();
        tel.setValue(new URI("mailto:denis.krylov@semanticbits.com"));
        tel.setNullFlavor(NullFlavor.NI);
        assertNull(PAUtil.getEmail(tel));

    }

    /**
     * Test method for {@link
     * gov.nih.nci.pa.util.PAUtil#getPhone(gov.nih.nci.iso21090.DSet<Tel>)}.
     */
    @Test
    public void testGetPhone() {
        DSet<Tel> telecomAddresses = new DSet<Tel>();
        assertNull(PAUtil.getEmail(telecomAddresses));
        List<String> st = new ArrayList<String>();
        st.add("email:n.n.com");
        DSetConverter.convertListToDSet(st, "EMAIL", telecomAddresses);
        assertNull(PAUtil.getPhone(telecomAddresses));
        st = new ArrayList<String>();
        st.add("tel:111-111-1111");
        DSetConverter.convertListToDSet(st, "PHONE", telecomAddresses);
        assertNotNull(PAUtil.getPhone(telecomAddresses));
        assertEquals(PAUtil.getPhone(telecomAddresses), "tel:111-111-1111");
    }

    /**
     * Test method for {@link
     * gov.nih.nci.pa.util.PAUtil#getPhoneExtension(gov.nih.nci.iso21090.DSet<
     * Tel>)}.
     */
    @Test
    public void testGetPhoneExtension() {
        DSet<Tel> telecomAddresses = new DSet<Tel>();
        assertNull(PAUtil.getEmail(telecomAddresses));
        List<String> st = new ArrayList<String>();
        st.add("tel:111-111-1111");
        DSetConverter.convertListToDSet(st, "PHONE", telecomAddresses);
        assertEquals(PAUtil.getPhoneExtension(telecomAddresses), "");
        st = new ArrayList<String>();
        st.add("tel:111-111-1111;extn222");
        DSetConverter.convertListToDSet(st, "PHONE", telecomAddresses);
        assertNotNull(PAUtil.getPhoneExtension(telecomAddresses));
        assertEquals(PAUtil.getPhoneExtension(telecomAddresses), "222");
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#getPhone(String)}.
     */
    @Test
    public void testGetPhone1() {
        assertEquals(PAUtil.getPhone("tel:111-111-1111"), "tel:111-111-1111");
        assertEquals(PAUtil.getPhone("tel:111-111-1111extn1111"),
                "tel:111-111-1111");
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#getPhoneExtn(String)}.
     */
    @Test
    public void testGetPhoneExtn1() {
        assertEquals(PAUtil.getPhoneExtn(null), "");
        assertEquals(PAUtil.getPhoneExtn("tel:111-111-1111"), "");
        assertEquals(PAUtil.getPhoneExtn("tel:111-111-1111extn2222"), "2222");
        assertEquals(PAUtil.getPhoneExtn("tel:111-111-1111ext2222"), "2222");
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#isUnitLessOrSame(String,String)}.
     */
    @Test
    public void testIsUnitLessOrSame() {
        assertTrue(PAUtil.isUnitLessOrSame("Days", "Days"));
        assertTrue(PAUtil.isUnitLessOrSame("Days", "Years"));
        assertTrue(PAUtil.isUnitLessOrSame("Weeks", "Months"));
        assertTrue(PAUtil.isUnitLessOrSame("Weeks", "Years"));
        assertTrue(PAUtil.isUnitLessOrSame("Days", "Weeks"));
        assertTrue(PAUtil.isUnitLessOrSame("Days", "Months"));
        assertTrue(PAUtil.isUnitLessOrSame("Days", "Years"));
        assertTrue(PAUtil.isUnitLessOrSame("Hours", "Days"));
        assertTrue(PAUtil.isUnitLessOrSame("Hours", "Weeks"));
        assertTrue(PAUtil.isUnitLessOrSame("Hours", "Months"));
        assertTrue(PAUtil.isUnitLessOrSame("Hours", "Years"));
        assertTrue(PAUtil.isUnitLessOrSame("Minutes", "Hours"));
        assertTrue(PAUtil.isUnitLessOrSame("Minutes", "Days"));
        assertTrue(PAUtil.isUnitLessOrSame("Minutes", "Weeks"));
        assertTrue(PAUtil.isUnitLessOrSame("Minutes", "Months"));
        assertTrue(PAUtil.isUnitLessOrSame("Minutes", "Years"));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.PAUtil#getAge(BigDecimal)}.
     */
    @Test
    public void testGetAge() {
        assertEquals(PAUtil.getAge(new BigDecimal("100.00")), "100.00");
        assertEquals(PAUtil.getAge(new BigDecimal("100")), "100");
        assertEquals(PAUtil.getAge(new BigDecimal("100.0")), "100");
    }

    @Test
    public void testPPurpose() {
        assertEquals("Other",
                PAUtil.lookupPrimaryPurposeAdditionalQualifierCode("Other"));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#getAssignedIdentifierExtension(StudyProtocolDTO)}
     * .
     */
    @Test
    public void getAssignedIdentifierExtension() {
        StudyProtocolDTO spDto = new StudyProtocolDTO();
        assertEquals(PAUtil.getAssignedIdentifierExtension(spDto), "");
        Set<Ii> iiSet = new HashSet<Ii>();
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertEquals(PAUtil.getAssignedIdentifierExtension(spDto), "");
        iiSet.add(IiConverter.convertToIi("1"));
        iiSet.add(IiConverter.convertToStudyProtocolIi(new Long(2222)));
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertEquals(PAUtil.getAssignedIdentifierExtension(spDto), "2222");
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#getgetOtherIdentifiers(StudyProtocolDTO)}
     * .
     */
    @Test
    public void getOtherIdentifiers() {
        StudyProtocolDTO spDto = new StudyProtocolDTO();
        assertEquals(PAUtil.getOtherIdentifiers(spDto).size(), 0);
        Set<Ii> iiSet = new HashSet<Ii>();
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertEquals(PAUtil.getOtherIdentifiers(spDto).size(), 0);
        iiSet.add(IiConverter.convertToIi("1"));
        assertEquals(PAUtil.getOtherIdentifiers(spDto).size(), 0);
        iiSet.add(IiConverter.convertToOtherIdentifierIi("2222"));
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertEquals(PAUtil.getOtherIdentifiers(spDto).size(), 1);
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#getNonOtherIdentifiers(StudyProtocolDTO)}
     * .
     */
    @Test
    public void getNonOtherIdentifiers() {
        StudyProtocolDTO spDto = new StudyProtocolDTO();
        assertEquals(PAUtil.getNonOtherIdentifiers(spDto).getExtension(), null);
        Set<Ii> iiSet = new HashSet<Ii>();
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertEquals(PAUtil.getNonOtherIdentifiers(spDto).getExtension(), null);
        iiSet.add(IiConverter.convertToIi("2222"));
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertEquals(PAUtil.getNonOtherIdentifiers(spDto).getExtension(),
                "2222");
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#getAssignedIdentifier(StudyProtocolDTO)}
     * .
     */
    @Test
    public void getAssignedIdentifier() {
        StudyProtocolDTO spDto = new StudyProtocolDTO();
        assertEquals(PAUtil.getAssignedIdentifier(spDto).getExtension(), null);
        Set<Ii> iiSet = new HashSet<Ii>();
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertEquals(PAUtil.getAssignedIdentifier(spDto).getExtension(), null);

        iiSet.add(IiConverter.convertToIi("1"));
        iiSet.add(IiConverter.convertToStudyProtocolIi(new Long(2222)));
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertEquals(PAUtil.getAssignedIdentifier(spDto).getExtension(), "2222");
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.util.PAUtil#checkAssignedIdentifierExists(StudyProtocolDTO)}
     * .
     */
    @Test
    public void checkAssignedIdentifierExists() {
        StudyProtocolDTO spDto = new StudyProtocolDTO();
        assertFalse(PAUtil.checkAssignedIdentifierExists(spDto));
        Set<Ii> iiSet = new HashSet<Ii>();
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertFalse(PAUtil.checkAssignedIdentifierExists(spDto));
        iiSet.add(IiConverter.convertToIi("1"));
        iiSet.add(IiConverter.convertToStudyProtocolIi(new Long(2222)));
        spDto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(iiSet));
        assertTrue(PAUtil.checkAssignedIdentifierExists(spDto));
    }

    private StudyMilestone createUnboundStudyMilestone(MilestoneCode msCode,
            long id) {
        StudyMilestone sm = new StudyMilestone();
        sm.setMilestoneCode(msCode);
        sm.setMilestoneDate(new Timestamp((new Date()).getTime()));
        sm.setDateLastCreated(sm.getMilestoneDate());
        sm.setId(id);
        return sm;
    }

    @Test
    public void testMilestoneSortingSciNoAdm() {
        MilestonesDTO msDto = new MilestonesDTO();
        Set<StudyMilestone> studyMilestones = new TreeSet<StudyMilestone>(
                new LastCreatedComparator());
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.SUBMISSION_RECEIVED, studyMilestones.size()));
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.SUBMISSION_ACCEPTED, studyMilestones.size()));
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE,
                studyMilestones.size()));
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE,
                studyMilestones.size()));
        PAUtil.convertMilestonesToDTO(msDto, studyMilestones);
        assertEquals(MilestoneCode.SUBMISSION_ACCEPTED, msDto
                .getStudyMilestone().getMilestone());
        assertNull(msDto.getAdminMilestone().getMilestone());
        assertEquals(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE, msDto
                .getScientificMilestone().getMilestone());
    }

    @Test
    public void testMilestoneSortingAdmNoSci() {
        MilestonesDTO msDto = new MilestonesDTO();
        Set<StudyMilestone> studyMilestones = new TreeSet<StudyMilestone>(
                new LastCreatedComparator());
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.SUBMISSION_RECEIVED, studyMilestones.size()));
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.SUBMISSION_ACCEPTED, studyMilestones.size()));
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE,
                studyMilestones.size()));
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE,
                studyMilestones.size()));
        PAUtil.convertMilestonesToDTO(msDto, studyMilestones);
        assertEquals(MilestoneCode.SUBMISSION_ACCEPTED, msDto
                .getStudyMilestone().getMilestone());
        assertEquals(msDto.getAdminMilestone().getMilestone(),
                MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE);
        assertNull(msDto.getScientificMilestone().getMilestone());
    }

    @Test
    public void testMilestoneSortingSciAndAdm() {
        MilestonesDTO msDto = new MilestonesDTO();
        Set<StudyMilestone> studyMilestones = new TreeSet<StudyMilestone>(
                new LastCreatedComparator());
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.SUBMISSION_RECEIVED, studyMilestones.size()));
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.SUBMISSION_ACCEPTED, studyMilestones.size()));
        PAUtil.convertMilestonesToDTO(msDto, studyMilestones);
        assertEquals(MilestoneCode.SUBMISSION_ACCEPTED, msDto
                .getStudyMilestone().getMilestone());
        assertNull(msDto.getAdminMilestone().getMilestone());
        assertNull(msDto.getScientificMilestone().getMilestone());
        msDto = new MilestonesDTO();
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE,
                studyMilestones.size()));
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE,
                studyMilestones.size()));
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE,
                studyMilestones.size()));
        PAUtil.convertMilestonesToDTO(msDto, studyMilestones);
        assertEquals(MilestoneCode.SUBMISSION_ACCEPTED, msDto
                .getStudyMilestone().getMilestone());
        assertEquals(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE,
                msDto.getAdminMilestone().getMilestone());
        assertEquals(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, msDto
                .getScientificMilestone().getMilestone());
        msDto = new MilestonesDTO();
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.ADMINISTRATIVE_READY_FOR_QC,
                studyMilestones.size()));
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.ADMINISTRATIVE_QC_START, studyMilestones.size()));
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.ADMINISTRATIVE_QC_COMPLETE,
                studyMilestones.size()));
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE,
                studyMilestones.size()));
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.SCIENTIFIC_READY_FOR_QC, studyMilestones.size()));
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.SCIENTIFIC_QC_START, studyMilestones.size()));
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.SCIENTIFIC_QC_COMPLETE, studyMilestones.size()));
        studyMilestones.add(createUnboundStudyMilestone(
                MilestoneCode.INITIAL_ABSTRACTION_VERIFY,
                studyMilestones.size()));
        PAUtil.convertMilestonesToDTO(msDto, studyMilestones);
        assertEquals(MilestoneCode.INITIAL_ABSTRACTION_VERIFY, msDto
                .getStudyMilestone().getMilestone());
        assertNull(msDto.getAdminMilestone().getMilestone());
        assertNull(msDto.getScientificMilestone().getMilestone());
    }

    @Test
    public void testIsCompleteURL() {
        assertFalse(PAUtil.isCompleteURL(""));
        assertFalse(PAUtil.isCompleteURL("www.google.com"));
        assertFalse(PAUtil.isCompleteURL(" @@@ "));
        assertFalse(PAUtil.isCompleteURL("http:/www.google.com"));
        assertTrue(PAUtil.isCompleteURL("http://www.google.com"));
        assertTrue(PAUtil.isCompleteURL("https://www.google.com"));
        assertTrue(PAUtil.isCompleteURL("http://www.google.com/"));
        assertTrue(PAUtil.isCompleteURL("http://www.google.com:9999/"));
        assertTrue(PAUtil
                .isCompleteURL("http://localhost:39480/registry/protected/submitProprietaryTrial.action?"
                        + "sum4FundingCatCode=Industrial#"));
    }

    @Test
    public void testIsUsOrCanadaPhoneNumber() {
        assertFalse(PAUtil.isUsOrCanadaPhoneNumber(""));
        assertFalse(PAUtil.isUsOrCanadaPhoneNumber("123"));
        assertFalse(PAUtil.isUsOrCanadaPhoneNumber("abc"));
        assertFalse(PAUtil.isUsOrCanadaPhoneNumber("5555555555"));
        assertFalse(PAUtil.isUsOrCanadaPhoneNumber("555-555-55a5"));
        assertTrue(PAUtil.isUsOrCanadaPhoneNumber("555-555-5555"));
        assertTrue(PAUtil.isUsOrCanadaPhoneNumber("555-555-5555x123"));
    }

    @Test
    public void testIsBusinessDay() throws ParseException {
        final String[] pattern = new String[] { "MM/dd/yyyy" };
        // days of week
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("03/24/2012",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("03/25/2012",
                pattern)));
        assertTrue(PAUtil.isBusinessDay(DateUtils.parseDate("03/19/2012",
                pattern)));
        assertTrue(PAUtil.isBusinessDay(DateUtils.parseDate("03/20/2012",
                pattern)));
        assertTrue(PAUtil.isBusinessDay(DateUtils.parseDate("03/21/2012",
                pattern)));
        assertTrue(PAUtil.isBusinessDay(DateUtils.parseDate("03/22/2012",
                pattern)));
        assertTrue(PAUtil.isBusinessDay(DateUtils.parseDate("03/23/2012",
                pattern)));

        // federal holidays
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("01/02/2012",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("01/16/2012",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("02/20/2012",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("05/28/2012",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("07/04/2012",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("09/03/2012",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("10/08/2012",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("11/12/2012",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("11/22/2012",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("12/25/2012",
                pattern)));

        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("01/01/2013",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("01/21/2013",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("02/18/2013",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("05/27/2013",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("07/04/2013",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("09/02/2013",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("10/14/2013",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("11/11/2013",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("11/28/2013",
                pattern)));
        assertFalse(PAUtil.isBusinessDay(DateUtils.parseDate("12/25/2013",
                pattern)));

    }

    @Test
    public void endOfDay() {
        assertNull(PAUtil.endOfDay(null));
        Date date = new Date();
        Date endOfDay = PAUtil.endOfDay(date);
        assertTrue(DateUtils.isSameDay(date, endOfDay));
        assertFalse(DateUtils.isSameDay(date,
                DateUtils.addMilliseconds(endOfDay, 1)));
    }

    @Test
    public void getBusinessDaysBetween() throws ParseException {
        final Date date = new Date();

        // boundary conditions
        assertEquals(0, PAUtil.getBusinessDaysBetween(null, null));
        assertEquals(0, PAUtil.getBusinessDaysBetween(date, null));
        assertEquals(0, PAUtil.getBusinessDaysBetween(null, date));
        assertEquals(0, PAUtil.getBusinessDaysBetween(date("06/02/2015"),
                date("06/01/2015")));

        // test regular week.
        assertEquals(1, PAUtil.getBusinessDaysBetween(date("06/01/2015"),
                date("06/01/2015")));
        assertEquals(2, PAUtil.getBusinessDaysBetween(date("06/01/2015"),
                date("06/02/2015")));
        assertEquals(3, PAUtil.getBusinessDaysBetween(date("06/01/2015"),
                date("06/03/2015")));
        assertEquals(4, PAUtil.getBusinessDaysBetween(date("06/01/2015"),
                date("06/04/2015")));
        assertEquals(5, PAUtil.getBusinessDaysBetween(date("06/01/2015"),
                date("06/05/2015")));
        assertEquals(5, PAUtil.getBusinessDaysBetween(date("06/01/2015"),
                date("06/06/2015")));
        assertEquals(5, PAUtil.getBusinessDaysBetween(date("06/01/2015"),
                date("06/07/2015")));
        assertEquals(6, PAUtil.getBusinessDaysBetween(date("06/01/2015"),
                date("06/08/2015")));

        // test a holiday.
        assertEquals(1, PAUtil.getBusinessDaysBetween(date("05/22/2015"),
                date("05/22/2015")));
        assertEquals(1, PAUtil.getBusinessDaysBetween(date("05/22/2015"),
                date("05/23/2015")));
        assertEquals(1, PAUtil.getBusinessDaysBetween(date("05/22/2015"),
                date("05/24/2015")));
        assertEquals(1, PAUtil.getBusinessDaysBetween(date("05/22/2015"),
                date("05/25/2015")));
        assertEquals(2, PAUtil.getBusinessDaysBetween(date("05/22/2015"),
                date("05/26/2015")));

        // counting biz days within a long weekend must result in 0.
        assertEquals(0, PAUtil.getBusinessDaysBetween(date("05/23/2015"),
                date("05/23/2015")));
        assertEquals(0, PAUtil.getBusinessDaysBetween(date("05/23/2015"),
                date("05/24/2015")));
        assertEquals(0, PAUtil.getBusinessDaysBetween(date("05/23/2015"),
                date("05/25/2015")));
        assertEquals(0, PAUtil.getBusinessDaysBetween(date("05/24/2015"),
                date("05/25/2015")));

    }

    @Test
    public void addBusinessDays() throws ParseException {
        assertNull(PAUtil.addBusinessDays(null, 1));

        final Date date = new Date();
        // Negative increments not supported; result in same date being
        // returned.
        assertEquals(date, PAUtil.addBusinessDays(date, 0));
        assertTrue(DateUtils.isSameDay(date("06/01/2015"), PAUtil.addBusinessDays(date("06/02/2015"), -1)));
        assertTrue(DateUtils.isSameDay(date("06/01/2015"), PAUtil.addBusinessDays(date("06/03/2015"), -2)));

        assertTrue(DateUtils.isSameDay(date("06/02/2015"),
                (PAUtil.addBusinessDays(date("06/01/2015"), 1))));
        assertTrue(DateUtils.isSameDay(date("06/03/2015"),
                (PAUtil.addBusinessDays(date("06/01/2015"), 2))));
        assertTrue(DateUtils.isSameDay(date("06/04/2015"),
                (PAUtil.addBusinessDays(date("06/01/2015"), 3))));
        assertTrue(DateUtils.isSameDay(date("06/05/2015"),
                (PAUtil.addBusinessDays(date("06/01/2015"), 4))));
        assertTrue(DateUtils.isSameDay(date("06/08/2015"),
                (PAUtil.addBusinessDays(date("06/01/2015"), 5))));
        assertTrue(DateUtils.isSameDay(date("06/22/2015"),
                (PAUtil.addBusinessDays(date("06/01/2015"), 15))));
        assertTrue(DateUtils.isSameDay(date("07/03/2015"),
                (PAUtil.addBusinessDays(date("06/01/2015"), 24))));
        assertTrue(DateUtils.isSameDay(date("07/07/2015"),
                (PAUtil.addBusinessDays(date("06/01/2015"), 25))));

        assertTrue(DateUtils.isSameDay(date("05/26/2015"),
                (PAUtil.addBusinessDays(date("05/23/2015"), 1))));

    }

    private Date date(String date) throws ParseException {
        return DateUtils.parseDate(date, new String[] { "MM/dd/yyyy" });
    }

    @Test
    public void isGridCall() {
        StudySourceInterceptor.STUDY_SOURCE_CONTEXT.remove();
        assertFalse(PAUtil.isGridCall(null));

        SessionContext ctx = mock(SessionContext.class);
        assertFalse(PAUtil.isGridCall(ctx));

        Principal princ = mock(Principal.class);
        when(ctx.getCallerPrincipal()).thenReturn(princ);
        assertFalse(PAUtil.isGridCall(ctx));

        CommonsGridLoginModule.setGridServicePrincipalSeparator(null);
        when(princ.getName()).thenReturn("Grid||User");
        assertFalse(PAUtil.isGridCall(ctx));

        CommonsGridLoginModule.setGridServicePrincipalSeparator("||");
        assertTrue(PAUtil.isGridCall(ctx));

        when(princ.getName()).thenReturn("User");
        assertFalse(PAUtil.isGridCall(ctx));
        
        StudySourceInterceptor.STUDY_SOURCE_CONTEXT.set(StudySourceCode.GRID_SERVICE);
        assertTrue(PAUtil.isGridCall(ctx));
        StudySourceInterceptor.STUDY_SOURCE_CONTEXT.remove();
        
    }

    @Test
    public void isInRange() {
        assertFalse(PAUtil.isInRange(0, null));
        assertFalse(PAUtil.isInRange(0, " "));
        
        assertTrue(PAUtil.isInRange(0, "<2"));
        assertTrue(PAUtil.isInRange(1, "<2"));
        assertTrue(PAUtil.isInRange(10, ">9"));
        assertTrue(PAUtil.isInRange(1000, ">999"));
        assertTrue(PAUtil.isInRange(100, "99-101"));
        assertTrue(PAUtil.isInRange(99, "99-101"));
        assertTrue(PAUtil.isInRange(101, "99-101"));
        
        assertFalse(PAUtil.isInRange(1, "<1"));
        assertFalse(PAUtil.isInRange(1, ">1"));
        
        assertFalse(PAUtil.isInRange(-1, "sdaf"));
        assertFalse(PAUtil.isInRange(0, "sdfsdf"));
        assertFalse(PAUtil.isInRange(1, "fsdsf"));
        
        
    }
}

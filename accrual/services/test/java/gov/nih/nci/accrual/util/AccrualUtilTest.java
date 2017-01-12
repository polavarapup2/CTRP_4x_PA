/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The accrual
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This accrual Software License (the License) is between NCI and You. You (or 
 * Your) shall mean a person or an entity, and all other entities that control, 
 * are controlled by, or are under common control with the entity. Control for 
 * purposes of this definition means (i) the direct or indirect power to cause 
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares, 
 * or (iii) beneficial ownership of such entity. 
 *
 * This License is granted provided that You agree to the conditions described 
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up, 
 * no-charge, irrevocable, transferable and royalty-free right and license in 
 * its rights in the accrual Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the accrual Software; (ii) distribute and 
 * have distributed to and by third parties the accrual Software and any 
 * modifications and derivative works thereof; and (iii) sublicense the 
 * foregoing rights set out in (i) and (ii) to third parties, including the 
 * right to license such rights to further third parties. For sake of clarity, 
 * and not by way of limitation, NCI shall have no right of accounting or right 
 * of payment from You or Your sub-licensees for the rights granted under this 
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the 
 * above copyright notice, this list of conditions and the disclaimer and 
 * limitation of liability of Article 6, below. Your redistributions in object 
 * code form must reproduce the above copyright notice, this list of conditions 
 * and the disclaimer of Article 6 in the documentation and/or other materials 
 * provided with the distribution, if any. 
 *
 * Your end-user documentation included with the redistribution, if any, must 
 * include the following acknowledgment: This product includes software 
 * developed by 5AM and the National Cancer Institute. If You do not include 
 * such end-user documentation, You shall include this acknowledgment in the 
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM" 
 * to endorse or promote products derived from this Software. This License does 
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the 
 * terms of this License. 
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this 
 * Software into Your proprietary programs and into any third party proprietary 
 * programs. However, if You incorporate the Software into third party 
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software 
 * into such third party proprietary programs and for informing Your 
 * sub-licensees, including without limitation Your end-users, of their 
 * obligation to secure any required permissions from such third parties before 
 * incorporating the Software into such third party proprietary software 
 * programs. In the event that You fail to obtain such permissions, You agree 
 * to indemnify NCI for any claims against NCI by such third parties, except to 
 * the extent prohibited by law, resulting from Your failure to obtain such 
 * permissions. 
 *
 * For sake of clarity, and not by way of limitation, You may add Your own 
 * copyright statement to Your modifications and to the derivative works, and 
 * You may provide additional or different license terms and conditions in Your 
 * sublicenses of modifications of the Software, or any derivative works of the 
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY, 
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO 
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR 
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.accrual.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.service.util.AccrualCsmUtil;
import gov.nih.nci.accrual.service.util.MockCsmUtil;
import gov.nih.nci.iso21090.EdText;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceRemote;
import gov.nih.nci.pa.service.util.AccrualDiseaseTerminologyServiceRemote;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.pomock.MockFamilyService;
import gov.nih.nci.pa.util.pomock.MockOrganizationEntityService;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.family.FamilyServiceRemote;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
/**
 * @author mshestopalov
 *
 */
public class AccrualUtilTest extends AbstractAccrualHibernateTestCase {

    StudySiteDTO studySiteDto;

    @Before
    public void setup() throws PAException {
        TestSchema.primeData();
        AccrualCsmUtil.setCsmUtil(new MockCsmUtil());
        ServiceLocatorPaInterface svcLocal = mock(ServiceLocatorPaInterface.class);
        RegistryUserServiceRemote regSvc = mock(RegistryUserServiceRemote.class);
        when(regSvc.getUser(any(String.class))).thenReturn(TestSchema.registryUsers.get(0));
        when(svcLocal.getRegistryUserService()).thenReturn(regSvc);

        studySiteDto = new StudySiteDTO();
        StudySiteServiceRemote studySiteSvc = mock(StudySiteServiceRemote.class);
        when(studySiteSvc.get(any(Ii.class))).thenReturn(studySiteDto);
        when(svcLocal.getStudySiteService()).thenReturn(studySiteSvc);
        PaServiceLocator.getInstance().setServiceLocator(svcLocal);
        
        try {
            PaHibernateUtil
                    .getCurrentSession()
                    .createSQLQuery(
                            "create table rv_dcp_id (local_sp_indentifier varchar, study_protocol_identifier int)")
                    .executeUpdate();

            PaHibernateUtil
                    .getCurrentSession()
                    .createSQLQuery(
                            "create table rv_ctep_id (local_sp_indentifier varchar, study_protocol_identifier int)")
                    .executeUpdate();
            PaHibernateUtil.getCurrentSession().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PaHibernateUtil.getCurrentSession()
                    .createSQLQuery("delete from rv_dcp_id").executeUpdate();
            PaHibernateUtil.getCurrentSession().flush();

            PaHibernateUtil.getCurrentSession()
                    .createSQLQuery("delete from rv_ctep_id").executeUpdate();
            PaHibernateUtil.getCurrentSession().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
    @Test
    public void testIsSuperAbstractor() throws PAException {
        //assertFalse(AccrualUtil.isSuAbstractor(TestSchema.registryUsers.get(0)));
        assertFalse(AccrualUtil.isSuAbstractor(null));        
        assertFalse(AccrualUtil.isSuAbstractor(new RegistryUser()));
    }
    
    @Test
    public void testAccrualAccessCheck() throws PAException {
        // doesn't find access due to status
        StudySiteAccrualAccess bo = new StudySiteAccrualAccess();
        bo.setRegistryUser(TestSchema.registryUsers.get(0));
        bo.setStudySite(TestSchema.studySites.get(0));
        bo.setStatusCode(ActiveInactiveCode.INACTIVE);
        bo.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        bo.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
        TestSchema.addUpdObject(bo);
        assertFalse(AccrualUtil.isUserAllowedAccrualAccess(
                IiConverter.convertToIi(TestSchema.studySites.get(0).getId())));
       
        
        // finds access
        bo.setStatusCode(ActiveInactiveCode.ACTIVE);       
        TestSchema.addUpdObject(bo);
        assertTrue(AccrualUtil.isUserAllowedAccrualAccess(
                IiConverter.convertToIi(TestSchema.studySites.get(0).getId())));
      
        // doesn't find access due to site id
        bo.setStatusCode(ActiveInactiveCode.INACTIVE);       
        TestSchema.addUpdObject(bo);
        
        StudySite ss = new StudySite();
        ss.setLocalStudyProtocolIdentifier("T1 Local SP 001");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        ss.setStudyProtocol(TestSchema.studyProtocols.get(0));
        TestSchema.addUpdObject(ss);
        StudySiteAccrualAccess bo2 = new StudySiteAccrualAccess();
        bo2.setRegistryUser(TestSchema.registryUsers.get(0));
        bo2.setStudySite(ss);
        bo2.setStatusCode(ActiveInactiveCode.ACTIVE);
        bo2.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        bo2.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
        TestSchema.addUpdObject(bo2);
        
        assertFalse(AccrualUtil.isUserAllowedAccrualAccess(
                IiConverter.convertToIi(TestSchema.studySites.get(0).getId())));
    }

    @Test
    public void isValidTreatingSite() throws Exception {
        assertFalse(AccrualUtil.isValidTreatingSite(null));

        Ii ii = IiConverter.convertToStudySiteIi(null);
        assertFalse(AccrualUtil.isValidTreatingSite(ii));

        ii = IiConverter.convertToStudySiteIi(1L);
        studySiteDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        assertTrue(AccrualUtil.isValidTreatingSite(ii));

        studySiteDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        assertFalse(AccrualUtil.isValidTreatingSite(ii));
        
        when(PaServiceLocator.getInstance().getStudySiteService().get(any(Ii.class))).thenReturn(null);
        assertFalse(AccrualUtil.isValidTreatingSite(ii));
    }

    @Test 
    public void safeGetTest() {
        assertNull(AccrualUtil.safeGet((List)null, 1));
        assertNull(AccrualUtil.safeGet(new ArrayList<String>(), 2));
        List<String> list = new ArrayList<String>();
        list.add("value1");
        assertEquals("value1", AccrualUtil.safeGet(list, 0));
        assertNull(AccrualUtil.safeGet(list, 1));
        list.add(" value2 ");
        assertEquals("value2", AccrualUtil.safeGet(list, 1));
        assertNull(AccrualUtil.safeGet(list, 2));
    }
    
    @Test
    public void safeGetArrayTest() {
        String[] arr = null;
        assertEquals("", AccrualUtil.safeGet(arr, 0));
        assertEquals("", AccrualUtil.safeGet(arr, 1));
        
        arr = new String[] {" test "};
        assertEquals("test", AccrualUtil.safeGet(arr, 0));
        assertEquals("", AccrualUtil.safeGet(arr, 1));
    }


    @Test
    public void csvParseAndTrimTest() {
        String[] arr = AccrualUtil.csvParseAndTrim("\"12 \",,A,null,\"xyz\"     ");
        assertEquals("12", arr[0]);
        assertEquals("", arr[1]);
        assertEquals("A", arr[2]);
        assertEquals("null", arr[3]);
        assertEquals("xyz", arr[4]);
        assertEquals(5, arr.length);
        
        String[] arr2 = AccrualUtil.csvParseAndTrim(null);
        assertNull(arr2);
        
        String[] arr3 = AccrualUtil.csvParseAndTrim("");
        assertEquals("", arr3[0]);
        assertEquals(1, arr3.length);
    }

    @Test
    public void getCodeTest() {
        assertNull(AccrualUtil.getCode(null));
        assertEquals(AccrualSubmissionTypeCode.BATCH.getCode(), AccrualUtil.getCode(AccrualSubmissionTypeCode.BATCH));
    }
    
    @Test
    public void getDisplayNameTest() {
        assertEquals("", AccrualUtil.getDisplayName(null));
        RegistryUser ru = new RegistryUser();
        User cu = new User();
        ru.setCsmUser(cu);
        assertEquals("", AccrualUtil.getDisplayName(ru));
        cu.setFirstName("x");
        assertEquals("x", AccrualUtil.getDisplayName(ru));
        cu.setLastName("y");
        assertEquals("x y", AccrualUtil.getDisplayName(ru));
        ru.setLastName("b");
        assertEquals("b", AccrualUtil.getDisplayName(ru));
        ru.setFirstName("a");
        assertEquals("a b", AccrualUtil.getDisplayName(ru));
    }

    @Test
    public void normalizeYearMonthStringTest() {
        assertNull(AccrualUtil.normalizeYearMonthString(null));
        assertNull(AccrualUtil.normalizeYearMonthString(""));
        assertNull(AccrualUtil.normalizeYearMonthString(" 000000 "));
        assertNull(AccrualUtil.normalizeYearMonthString("00000"));
        assertEquals("03/1999", AccrualUtil.normalizeYearMonthString("3/1999"));
        assertNull(AccrualUtil.normalizeYearMonthString("1999 "));
    }

    @Test
    public void tsToYearMonthStringTest() {
        assertNull(AccrualUtil.tsToYearMonthString(null));
        assertNull(AccrualUtil.tsToYearMonthString(new Ts()));
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("MM/yyyy");
        String nowYrMo = sdf.format(now);
        Ts ts = new Ts();
        ts.setValue(now);
        assertEquals(nowYrMo, AccrualUtil.tsToYearMonthString(ts));
        EdText edt = new EdText();
        edt.setValue(nowYrMo);
        ts.setOriginalText(edt);
        assertEquals(nowYrMo, AccrualUtil.tsToYearMonthString(ts));
    }

    @Test
    public void yearMonthStringToTsTest() {
        assertNull(AccrualUtil.yearMonthStringToTs(null));
        assertNull(AccrualUtil.yearMonthStringToTs(""));
        Ts ts = AccrualUtil.yearMonthStringToTs("2/2011");
        assertEquals(PAUtil.dateStringToDateTime("2/1/2011"), ts.getValue());
        assertEquals("02/2011", ts.getOriginalText().getValue());
        ts = AccrualUtil.yearMonthStringToTs("2011");
        assertTrue(ISOUtil.isTsNull(ts));
    }

    @Test
    public void yearMonthTsToTimestampTest() {
        assertNull(AccrualUtil.yearMonthTsToTimestamp(null));
        assertNull(AccrualUtil.yearMonthTsToTimestamp(new Ts()));
        Date now = DateUtils.truncate(new Date(), Calendar.MONTH);
        Timestamp nowYrMo = new Timestamp(DateUtils.truncate(now, Calendar.MONTH).getTime());
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy");
        sdf.applyPattern("MM/yyyy");
        Ts ts = new Ts();
        ts.setValue(now);
        assertEquals(nowYrMo, AccrualUtil.yearMonthTsToTimestamp(ts));
    }

    @Test
    public void yearMonthStringToTimestampTest() {
        assertNull(AccrualUtil.yearMonthStringToTimestamp(null));
        assertNull(AccrualUtil.yearMonthStringToTimestamp(""));
        Timestamp yrMoValue = PAUtil.dateStringToTimestamp("2/1/1999");
        assertEquals(yrMoValue, AccrualUtil.yearMonthStringToTimestamp("2/1999"));
        assertNull(AccrualUtil.yearMonthStringToTimestamp("1999"));
    }

    @Test
    public void timestampToYearMonthStringTest() {
        Date now = new Date();
        Timestamp nowTstamp = new Timestamp(now.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("MM/yyyy");
        String nowYrMoStr = sdf.format(now);
        assertEquals(nowYrMoStr, AccrualUtil.timestampToYearMonthString(nowTstamp));
        AccrualUtil au = new AccrualUtil();
        assertNull(au.timestampToYearMonthString(null));
    }
    
    @Test
    public void testforwardslash() {
    	String histologyCode = "8000/6";
    	assertEquals("8000", AccrualUtil.checkIfStringHasForwardSlash(histologyCode));
    }
    
    @Test
    public void testisUserAllowedSiteOrFamilyAccrualAccess() throws PAException {
    	PoServiceLocator poServiceLocator = mock(PoServiceLocator.class);
        when(poServiceLocator.getOrganizationEntityService()).thenReturn(
                new MockOrganizationEntityService());
        when(poServiceLocator.getFamilyService())
                .thenReturn(new MockFamilyService());
       PoRegistry.getInstance().setPoServiceLocator(poServiceLocator);
    	ServiceLocatorPaInterface svcLocal = mock(ServiceLocatorPaInterface.class);
        RegistryUserServiceRemote registrySvr = mock(RegistryUserServiceRemote.class);
        RegistryUser ru = TestSchema.registryUsers.get(1);
        ru.setSiteAccrualSubmitter(true);
        ru.setFamilyAccrualSubmitter(false);
        when(registrySvr.getUser(any(String.class))).thenReturn(ru);
        when(svcLocal.getRegistryUserService()).thenReturn(registrySvr);
        PaServiceLocator.getInstance().setServiceLocator(svcLocal);
        AccrualDiseaseTerminologyServiceRemote accrualDiseaseSvr = mock(AccrualDiseaseTerminologyServiceRemote.class);
        when(accrualDiseaseSvr.canChangeCodeSystemForSpIds(new ArrayList<Long>())).thenReturn(new HashMap<Long, Boolean>());
        when(svcLocal.getAccrualDiseaseTerminologyService()).thenReturn(accrualDiseaseSvr);
        PaServiceLocator.getInstance().setServiceLocator(svcLocal);
        AccrualUtil au = new AccrualUtil();
        assertTrue(au.isUserAllowedSiteOrFamilyAccrualAccess("1"));
        
        ru = TestSchema.registryUsers.get(1);
        ru.setSiteAccrualSubmitter(false);
        ru.setFamilyAccrualSubmitter(true);
        when(registrySvr.getUser(any(String.class))).thenReturn(ru);
        when(svcLocal.getRegistryUserService()).thenReturn(registrySvr);
        FamilyServiceRemote fs = mock(FamilyServiceRemote.class);
        when(fs.getActiveRelationships(any(Long.class))).thenReturn(getRelationships(new Long[] {1L, 2L}));
        when(poServiceLocator.getFamilyService())
        .thenReturn(fs);
        PoRegistry.getInstance().setPoServiceLocator(poServiceLocator);
        Map<Ii, FamilyDTO> familyMap = new HashMap<Ii, FamilyDTO>();
        FamilyDTO family = new FamilyDTO();
        family.setName(EnOnConverter.convertToEnOn("family name"));
        familyMap.put(IiConverter.convertToPoFamilyIi("1"), family);
        when(fs.getFamilies(any(Set.class))).thenReturn(familyMap);
        au = new AccrualUtil();
        assertTrue(au.isUserAllowedSiteOrFamilyAccrualAccess("1"));
    }
    
    @Test
    public void testgetAllFamilyOrgs() throws PAException {
        PoServiceLocator poServiceLocator = mock(PoServiceLocator.class);
        FamilyServiceRemote fs = mock(FamilyServiceRemote.class);
        when(poServiceLocator.getOrganizationEntityService()).thenReturn(
                new MockOrganizationEntityService());

        when(fs.getActiveRelationships(any(Long.class))).thenReturn(getRelationships(new Long[] {1L, 2L}));
        when(poServiceLocator.getFamilyService())
        .thenReturn(fs);
        PoRegistry.getInstance().setPoServiceLocator(poServiceLocator);
        Map<Ii, FamilyDTO> familyMap = new HashMap<Ii, FamilyDTO>();
        FamilyDTO family = new FamilyDTO();
        family.setName(EnOnConverter.convertToEnOn("family name"));
        familyMap.put(IiConverter.convertToPoFamilyIi("1"), family);
        when(fs.getFamilies(any(Set.class))).thenReturn(familyMap);
        AccrualUtil au = new AccrualUtil();
        List<Long> list = au.getAllFamilyOrgs(1L);
        assertTrue(list.size()==2);
        assertEquals("1",list.get(0).toString());
    }
    
    public static List<FamilyOrganizationRelationshipDTO> getRelationships(Long[] orgIds) {
        List<FamilyOrganizationRelationshipDTO> result = new ArrayList<FamilyOrganizationRelationshipDTO>();
        for (Long orgId : orgIds) {
            FamilyOrganizationRelationshipDTO rel = new FamilyOrganizationRelationshipDTO();
            rel.setOrgIdentifier(IiConverter.convertToPaOrganizationIi(orgId));
            result.add(rel);
        }
        return result;
    }
}

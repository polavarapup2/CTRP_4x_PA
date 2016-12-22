package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Patient;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.TestSchema;
/**
 * 
 * @author Reshma Koganti
 *
 */
public class StudySubjectServiceTest extends AbstractHibernateTestCase{
     private StudySubjectBeanLocal bean = new StudySubjectBeanLocal();
     private Ii spIi;
     private Ii ssIi;
     @Before
     public void setUp() throws Exception {
         
         
         StudyProtocol sp = TestSchema.createStudyProtocolObj();
         TestSchema.addUpdObject(sp);
         Organization org = TestSchema.createOrganizationObj();
         TestSchema.addUpdObject(org);
         HealthCareFacility hcf = TestSchema.createHealthCareFacilityObj(org);
         TestSchema.addUpdObject(hcf);
         StudySite ssite = TestSchema.createStudySiteObj(sp, hcf);
         TestSchema.addUpdObject(ssite);
         Country country = new Country();
         country.setAlpha2("US");
         country.setAlpha3("USA");
         country.setName("United States");
         country.setNumeric("840");
         TestSchema.addUpdObject(country);
         Patient p = new Patient();
         p.setCountry(country);
         p.setBirthDate(new Timestamp( new Date().getTime()));
         p.setStatusCode(StructuralRoleStatusCode.ACTIVE);
         TestSchema.addUpdObject(p);
         StudySubject ss = new StudySubject();
         ss.setStudyProtocol(sp);
         ss.setStudySite(ssite);
         ss.setAssignedIdentifier("123");
         ss.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI); 
         ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
         ss.setPatient(p);
         TestSchema.addUpdObject(ss);
         spIi = IiConverter.convertToStudyProtocolIi(sp.getId());
         ssIi = IiConverter.convertToStudySiteIi(ssite.getId());
     }
     
     @Test
     public void testGetBySiteAndStudyId() throws PAException {
         List<StudySubject> subjects = bean.getBySiteAndStudyId(IiConverter.convertToLong(spIi), IiConverter.convertToLong(ssIi));
         assertNotNull(subjects);
         assertEquals(1, subjects.size());
         assertEquals("123", subjects.get(0).getAssignedIdentifier());
     }
}

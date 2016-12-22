/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.dto.TrialDTO;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class BatchCreateProtocolsTest extends AbstractHibernateTestCase {
      private final BatchCreateProtocols trial = new BatchCreateProtocols();
      @Test
      public void testCreateProtocolsForOriginal() throws URISyntaxException {
          URL fileUrl = ClassLoader.getSystemClassLoader().getResource("batchUploadTest.xls");
          File f = new File(fileUrl.toURI());
          List<StudyProtocolBatchDTO> dtoList = new ArrayList<StudyProtocolBatchDTO>();
          StudyProtocolBatchDTO dto=  getBatchDto();
          dto.setProtcolDocumentFileName("ProtocolDoc.doc");
          dto.setIrbApprovalDocumentFileName("ProtocolDoc.doc");
          dto.setParticipatinSiteDocumentFileName(null);
          dto.setInformedConsentDocumentFileName(null);
          dto.setOtherTrialRelDocumentFileName(null);
          dto.setProtocolHighlightDocFileName(null);
          dto.setChangeRequestDocFileName(null);
          dto.setSponsorContactPhone("1234567");
          dtoList.add(dto);
          dto =  getBatchDto();
          getBatchGrants(dto);
          getBatchIndIde(dto);
          dto.setNihGrantNCIDivisionCode("CCR");
          dto.setResponsibleParty(TrialDTO.RESPONSIBLE_PARTY_TYPE_PI);
          dto.setSponsorContactPhone("1234567");
          dto.setProtcolDocumentFileName("ProtocolDoc.doc");
          dto.setIrbApprovalDocumentFileName("ProtocolDoc.doc");
          dto.setParticipatinSiteDocumentFileName("ProtocolDoc.doc");
          dto.setInformedConsentDocumentFileName("ProtocolDoc.doc");
          dto.setOtherTrialRelDocumentFileName("ProtocolDoc.doc");
          dto.setProtocolHighlightDocFileName("ProtocolDoc.doc");
          dto.setChangeRequestDocFileName("ProtocolDoc.doc");
          dto.setDataMonitoringCommitteeAppointedIndicator("yes");
          dto.setDelayedPostingIndicator("yes");
          dtoList.add(dto);
          dto =  getBatchDto();
          getBatchGrants(dto);
          getBatchIndIde(dto);
          dto.setProtcolDocumentFileName("ProtocolDoc.doc");
          dto.setIrbApprovalDocumentFileName("ProtocolDoc.doc");
          dto.setParticipatinSiteDocumentFileName("ProtocolDoc.doc");
          dto.setInformedConsentDocumentFileName("ProtocolDoc.doc");
          dto.setOtherTrialRelDocumentFileName("ProtocolDoc.doc");
          dto.setProtocolHighlightDocFileName("ProtocolDoc.doc");
          dto.setChangeRequestDocFileName("ProtocolDoc.doc");
          dtoList.add(dto);
          Map<String,String> map = trial.createProtocols(dtoList, f.getParent()+ File.separator, "testUserName");
          //Map<String,String> map = trial.createProtocols(dtoList, "C:\\NCI\\Projects\\COPPA\\code\\reg-web\\test\\resources"+ File.separator, "testUserName");
          assertNotNull(map);
          assertEquals("3",map.get("Success Trial Count"));
      }
      @Test
      public void testCreateProtocolsNullList() {
          List<StudyProtocolBatchDTO> dtoList = new ArrayList<StudyProtocolBatchDTO>();
          dtoList.add(null);
          assertNotNull(trial.createProtocols(new ArrayList<StudyProtocolBatchDTO>(), null, "testUserName"));
          assertNotNull(trial.createProtocols(null, null, "testUserName"));
          assertNotNull(trial.createProtocols(new ArrayList<StudyProtocolBatchDTO>(), null, "testUserName"));
          assertNotNull(trial.createProtocols(dtoList, null, "testUserName"));

      }
      @Test
      public void testCreateProtocolsForAmendment() throws URISyntaxException {
          URL fileUrl = ClassLoader.getSystemClassLoader().getResource("batchUploadTest.xls");
          File f = new File(fileUrl.toURI());
          List<StudyProtocolBatchDTO> dtoList = new ArrayList<StudyProtocolBatchDTO>();
          StudyProtocolBatchDTO dto=  getBatchDto();
          dto.setSubmissionType("A");
          dto.setNciTrialIdentifier("NCI-2009-00001");
          dto.setAmendmentDate("12/11/2009");
          dto.setAmendmentNumber("amendmentNumber");
          dto.setProtcolDocumentFileName("ProtocolDoc.doc");
          dto.setIrbApprovalDocumentFileName("ProtocolDoc.doc");
          dto.setParticipatinSiteDocumentFileName("ProtocolDoc.doc");
          dto.setInformedConsentDocumentFileName("ProtocolDoc.doc");
          dto.setOtherTrialRelDocumentFileName("ProtocolDoc.doc");
          dto.setProtocolHighlightDocFileName("ProtocolDoc.doc");
          dto.setChangeRequestDocFileName("ProtocolDoc.doc");
          dto.setPhase("Other");
          dto.setPhaseAdditionalQualifierCode("phaseOtherValueSp");
          getBatchIndIde(dto);
          getBatchGrants(dto);
          dto.setNihGrantNCIDivisionCode("CCR");
          dto.setLeadOrgName("OrgName");
          dtoList.add(dto);

          dto =  getBatchDto();
          dto.setSubmissionType("A");
          dto.setNciTrialIdentifier("NCI-2009-00001");
          dto.setAmendmentDate("12/11/2009");
          dto.setAmendmentNumber("amendmentNumber");
          dto.setProtcolDocumentFileName("ProtocolDoc.doc");
          dto.setIrbApprovalDocumentFileName("ProtocolDoc.doc");
          dto.setParticipatinSiteDocumentFileName(null);
          dto.setInformedConsentDocumentFileName(null);
          dto.setOtherTrialRelDocumentFileName(null);
          dto.setProtocolHighlightDocFileName(null);
          dto.setChangeRequestDocFileName("ProtocolDoc.doc");
          dto.setResponsibleParty(TrialDTO.RESPONSIBLE_PARTY_TYPE_SPONSOR);
          dto.setSponsorContactType("Generic");
          dto.setResponsibleGenericContactName("responsibleGenericContactName");
          dto.setSponsorContactPhone("sponsorContactPhone");
          getBatchSumm4Info(dto);
          dto.setPhase("phase");
          dto.setLeadOrgFax("leadOrgFax");
          dto.setLeadOrgUrl("http://www.leadOrgUrl.com");
          dto.setLeadOrgTTY("leadOrgTTY");
          dto.setPiFirstName("firstName");
          dto.setPiLastName("lastName");
          dtoList.add(dto);

          dto =  getBatchDto();
          dto.setSubmissionType("A");
          dto.setNciTrialIdentifier("NCI-2009-00001");
          dto.setAmendmentDate("12/11/2009");
          dto.setAmendmentNumber("amendmentNumber");
          dto.setProtcolDocumentFileName("ProtocolDoc.doc");
          dto.setIrbApprovalDocumentFileName("ProtocolDoc.doc");
          dto.setParticipatinSiteDocumentFileName(null);
          dto.setInformedConsentDocumentFileName(null);
          dto.setOtherTrialRelDocumentFileName(null);
          dto.setProtocolHighlightDocFileName(null);
          dto.setChangeRequestDocFileName("ProtocolDoc.doc");
          dto.setSponsorContactType("Generic");
          dto.setResponsibleGenericContactName("Test Create Title");
          dto.setSponsorContactPhone("sponsorContactPhone");
          dto.setPiMiddleName("piMiddleName");
          getBatchSumm4Info(dto);
          dtoList.add(dto);

          dto =  getBatchDto();
          dto.setSubmissionType("A");
          dto.setNciTrialIdentifier("NCI-2009-00004");
          dto.setAmendmentDate("12/11/2009");
          dto.setAmendmentNumber("amendmentNumber");
          dto.setProtcolDocumentFileName("ProtocolDoc.doc");
          dto.setIrbApprovalDocumentFileName("ProtocolDoc.doc");
          dto.setParticipatinSiteDocumentFileName(null);
          dto.setInformedConsentDocumentFileName(null);
          dto.setOtherTrialRelDocumentFileName(null);
          dto.setProtocolHighlightDocFileName(null);
          dto.setChangeRequestDocFileName("ProtocolDoc.doc");
          dtoList.add(dto);
          Map<String,String> map = trial.createProtocols(dtoList, f.getParent()+ File.separator, "testUserName");
          //Map<String,String> map = trial.createProtocols(dtoList, "C:\\NCI\\Projects\\COPPA\\code\\reg-web\\test\\resources"+ File.separator, "testUserName");
          assertNotNull(map);
      }
      @Test
      public void testUpdateSubmission() throws URISyntaxException {
          URL fileUrl = ClassLoader.getSystemClassLoader().getResource("batchUploadTest.xls");
          File f = new File(fileUrl.toURI());
          List<StudyProtocolBatchDTO> dtoList = new ArrayList<StudyProtocolBatchDTO>();
          StudyProtocolBatchDTO
          dto = getBatchDto();
          dto.setSubmissionType("U");
          dto.setNciTrialIdentifier("NCI-2009-00001");
          List<Ii> otherIdentifiers = new ArrayList<Ii>();
          otherIdentifiers.add(IiConverter.convertToOtherIdentifierIi("OTHER_ID_1"));
          otherIdentifiers.add(IiConverter.convertToOtherIdentifierIi("OTHER_ID_2"));
          dto.setOtherTrialIdentifiers(otherIdentifiers);
          dtoList.add(dto);
          dto = getBatchDto();
          dto.setSubmissionType("U");
          dto.setNciTrialIdentifier("NCI-2009-00004");
          dtoList.add(dto);
          Map<String,String> map = trial.createProtocols(dtoList, f.getParent()+ File.separator, "testUserName");
          assertNotNull(map);
      }
}

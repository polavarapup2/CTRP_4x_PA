/**
 *
 */
package gov.nih.nci.registry.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class ExcelReaderTest {

    private static final String FILE_NAME = "batchUploadTest.xls";
    private final String orgName = "Test";
    private ExcelReader excelReader;
    private HSSFWorkbook wb;
    private InputStream is;

    @Before
    public void setup() throws Exception {
        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(FILE_NAME);
        File f = new File(fileUrl.toURI());
        is = new FileInputStream(f);
        excelReader = new ExcelReader();
    }

    @Test
    public void testParseExcel() throws Exception {
        wb = excelReader.parseExcel(is);
        assertNotNull(wb);
    }

    @Test
    public void testConvertToDTOFromExcelWorkbook() throws Exception {
        List<StudyProtocolBatchDTO> list = null;
        testParseExcel();
        list = excelReader.convertToDTOFromExcelWorkbook(wb, orgName);
        assertNotNull(list);
        assertNotNull(list.get(0).getUniqueTrialId());
        assertNotNull(list.get(0).getSubmissionType());
        assertTrue(list.get(0).isCtGovXmlIndicator());
        if (!list.get(0).getSubmissionType().equalsIgnoreCase("O")) {
            assertNotNull(list.get(0).getNciTrialIdentifier());
        }
        if (list.get(0).getSubmissionType().equalsIgnoreCase("A")) {
            assertNotNull(list.get(0).getAmendmentNumber());
            assertNotNull(list.get(0).getAmendmentDate());
        }
        assertNotNull(list.get(0).getLocalProtocolIdentifier());
        assertNotNull(list.get(0).getNctNumber());
        assertNull(list.get(0).getOtherTrialIdentifiers());
        assertNotNull(list.get(0).getTitle());
        assertNotNull(list.get(0).getTrialType());
        assertNotNull(list.get(0).getPrimaryPurpose());
        assertNull(list.get(0).getPrimaryPurposeAdditionalQualifierCode());
        assertNotNull(list.get(0).getPhase());
        assertNull(list.get(0).getPhaseAdditionalQualifierCode());
        assertNotNull(list.get(0).getSponsorOrgName());
        assertNull(list.get(0).getSponsorPOId());
        assertNotNull(list.get(0).getSponsorStreetAddress());
        assertNotNull(list.get(0).getSponsorCity());
        assertNotNull(list.get(0).getSponsorState());
        assertNotNull(list.get(0).getSponsorCountry());
        assertNotNull(list.get(0).getSponsorEmail());
        assertNotNull(list.get(0).getSponsorPhone());
        assertNull(list.get(0).getSponsorTTY());
        assertNull(list.get(0).getSponsorFax());
        assertNull(list.get(0).getSponsorURL());
        assertNotNull(list.get(0).getResponsibleParty());
        assertNotNull(list.get(0).getSponsorContactType());
        assertNotNull(list.get(0).getResponsibleGenericContactName());
        assertNull(list.get(0).getSponsorContactFName());
        assertNull(list.get(0).getSponsorContactMName());
        assertNull(list.get(0).getSponsorContactLName());
        assertNull(list.get(0).getSponsorContactPOId());
        assertNull(list.get(0).getSponsorContactStreetAddress());
        assertNull(list.get(0).getSponsorContactCity());
        assertNull(list.get(0).getSponsorContactState());
        assertNull(list.get(0).getSponsorContactZip());
        assertNull(list.get(0).getSponsorContactCountry());
        assertNotNull(list.get(0).getSponsorContactEmail());
        assertNotNull(list.get(0).getSponsorContactPhone());
        assertNull(list.get(0).getSponsorContactTTY());
        assertNull(list.get(0).getSponsorContactFax());
        assertNull(list.get(0).getSponsorContactUrl());
        assertNotNull(list.get(0).getLeadOrgName());
        assertNotNull(list.get(0).getLeadOrgStreetAddress());
        assertNotNull(list.get(0).getLeadOrgCity());
        assertNotNull(list.get(0).getLeadOrgState());
        assertNotNull(list.get(0).getLeadOrgZip());
        assertNotNull(list.get(0).getLeadOrgCountry());
        assertNotNull(list.get(0).getLeadOrgEmail());
        assertNotNull(list.get(0).getLeadOrgPhone());
        assertNull(list.get(0).getLeadOrgTTY());
        assertNull(list.get(0).getLeadOrgUrl());
        assertNull(list.get(0).getLeadOrgType());
        assertNotNull(list.get(0).getPiFirstName());
        assertNull(list.get(0).getPiMiddleName());
        assertNotNull(list.get(0).getPiLastName());
        assertNull(list.get(0).getPiPOId());
        assertNotNull(list.get(0).getPiStreetAddress());
        assertNotNull(list.get(0).getPiCity());
        assertNotNull(list.get(0).getPiState());
        assertNotNull(list.get(0).getPiZip());
        assertNotNull(list.get(0).getPiCountry());
        assertNotNull(list.get(0).getPiEmail());
        assertNotNull(list.get(0).getPiPhone());
        assertNull(list.get(0).getPiTTY());
        assertNull(list.get(0).getPiFax());
        assertNull(list.get(0).getPiUrl());

        assertNull(list.get(0).getSumm4FundingCat());
        assertNull(list.get(0).getSumm4OrgName());
        assertNull(list.get(0).getSumm4OrgPOId());
        assertNull(list.get(0).getSumm4OrgStreetAddress());
        assertNull(list.get(0).getSumm4City());
        assertNull(list.get(0).getSumm4State());
        assertNull(list.get(0).getSumm4Zip());
        assertNull(list.get(0).getSumm4Country());
        assertNull(list.get(0).getSumm4Email());
        assertNull(list.get(0).getSumm4Phone());
        assertNull(list.get(0).getSumm4TTY());
        assertNull(list.get(0).getSumm4Fax());
        assertNull(list.get(0).getSumm4Url());
        assertNotNull(list.get(0).getProgramCodeText());
        assertNull(list.get(0).getNihGrantFundingMechanism());
        assertNull(list.get(0).getNihGrantInstituteCode());
        assertNull(list.get(0).getNihGrantSrNumber());
        assertNull(list.get(0).getNihGrantFundingPct());
        assertNull(list.get(0).getNihGrantNCIDivisionCode());
        assertNotNull(list.get(0).getCurrentTrialStatus());
        assertNull(list.get(0).getReasonForStudyStopped());
        assertNotNull(list.get(0).getCurrentTrialStatusDate());
        assertNotNull(list.get(0).getStudyStartDate());
        assertNotNull(list.get(0).getStudyStartDateType());
        assertNotNull(list.get(0).getPrimaryCompletionDate());
        assertNotNull(list.get(0).getPrimaryCompletionDateType());
        assertNull(list.get(0).getIndType());
        assertNull(list.get(0).getIndNumber());
        assertNull(list.get(0).getIndGrantor());
        assertNull(list.get(0).getIndHolderType());
        assertNull(list.get(0).getIndNIHInstitution());
        assertNull(list.get(0).getIndNCIDivision());
        assertNull(list.get(0).getIndHasExpandedAccess());
        assertNull(list.get(0).getIndExpandedAccessStatus());
        assertNotNull(list.get(0).getOversightAuthorityCountry());
        assertNotNull(list.get(0).getOversightOrgName());
        assertNotNull(list.get(0).getFdaRegulatoryInformationIndicator());
        assertNotNull(list.get(0).getSection801Indicator());
        assertNull(list.get(0).getDelayedPostingIndicator());
        assertNotNull(list.get(0).getDataMonitoringCommitteeAppointedIndicator());
        assertNotNull(list.get(0).getProtcolDocumentFileName());
        assertNotNull(list.get(0).getIrbApprovalDocumentFileName());
        assertNotNull(list.get(0).getParticipatinSiteDocumentFileName());
        assertNotNull(list.get(0).getInformedConsentDocumentFileName());
        assertNotNull(list.get(0).getOtherTrialRelDocumentFileName());
        assertNotNull(list.get(0).getChangeRequestDocFileName());
        assertNotNull(list.get(0).getProtocolHighlightDocFileName());
    }

    @Test
    public void testConvertDateToString() {
        assertNull(ExcelReader.convertDateToString(new Date(), null));
    }

}

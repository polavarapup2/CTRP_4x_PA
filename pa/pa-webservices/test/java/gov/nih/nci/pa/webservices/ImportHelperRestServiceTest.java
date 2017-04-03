package gov.nih.nci.pa.webservices;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import javax.ws.rs.core.Response;


import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.webservices.dto.StudyProtocolWebServiceDTO;

/**
 * 
 * @author Reshma
 *
 */
public class ImportHelperRestServiceTest extends AbstractMockitoTest {
    private ImportHelperRestService service = new ImportHelperRestService();
    @Test
    public void getStudyProtocolIdentityTest() throws PAException {

        when(PaRegistry.getStudyProtocolService().getStudyProtocolsByNctId(
                        eq("NCT290384"))).thenReturn(
                Arrays.asList((StudyProtocolDTO) spDto));
        when(PaRegistry.getProtocolQueryService()).thenReturn(protocolQueryServiceLocal);
        when(
                protocolQueryServiceLocal
                        .getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
                .thenReturn(queryDTOList);
        Response r = service.getStudyProtocolIdentity("NCT290384");
       // assertEquals(Status.OK.getStatusCode(), r.getStatus());
    }
    
    @Test
    public void extractStudyProtocolDTOTest() {
        String jsonStr ="{" + 
                "    \"type\": \"gov.nih.nci.ctrp.importtrials.dto.InterventionalStudyProtocolDTO\"," + 
                "    \"studyProtocolId\": \"130686\"," + 
                "    \"nciId\": \"NCI-2017-00334\"," + 
                "    \"acronym\": null," + 
                "    \"publicDescription\": \"\\n      RATIONALE: Drugs used in chemotherapy, such as melphalan, work in different ways to stop the\\n      growth of tumor cells, either by killing the cells or by stopping them from dividing. Giving\\n      melphalan directly into the arteries around the tumor may kill more tumor cells. It is not\\n      yet known whether hepatic arterial infusion with melphalan is more effective than standard\\n      therapy in treating liver metastases due to melanoma.\\n\\n      PURPOSE: This randomized phase III trial is studying hepatic arterial infusion with\\n      melphalan to see how well it works compared to standard therapy in treating patients with\\n      unresectable liver metastases due to melanoma.\\n    \"," + 
                "    \"publicTitle\": \"Hepatic Arterial Infusion With Melphalan Compared With Standard Therapy in Treating Patients With Unresectable Liver Metastases Due to Melanoma\"," + 
                "    \"scientificDescription\": \"\\n      OBJECTIVES:\\n\\n      Primary\\n\\n        -  Compare the hepatic progression-free survival of patients with unresectable liver\\n           metastases secondary to ocular or cutaneous melanoma treated with percutaneous isolated\\n           hepatic arterial perfusion (PHP) with melphalan with subsequent venous hemofiltration\\n           vs the best alternative standard treatment.\\n\\n      Secondary\\n\\n        -  Determine the response rate and duration of response in patients treated with melphalan\\n           PHP.\\n\\n        -  Determine the patterns of recurrence in patients treated with melphalan PHP.\\n\\n        -  Compare the overall survival of patients treated with these regimens.\\n\\n        -  Compare the safety and tolerability of these regimens in these patients.\\n\\n        -  Determine the pharmacokinetics of melphalan after PHP.\\n\\n      OUTLINE: This is a multicenter study. Patients are stratified according to site of disease\\n      (ocular vs cutaneous). Patients are randomized to 1 of 2 treatment arms.\\n\\n        -  Arm I: Patients undergo an isolated hepatic arterial infusion of melphalan over 30\\n           minutes on day 1. Treatment repeats every 4 weeks for 4 courses in the absence of\\n           disease progression or unacceptable toxicity. Patients with complete or partial\\n           response undergo 2 additional courses in the absence of ongoing or increasing toxicity.\\n\\n        -  Arm II: Patients receive the best alternative therapy comprising supportive care,\\n           systemic or regional chemotherapy, hepatic artery (chemo)-embolization, or any other\\n           appropriate therapy at the National Cancer Institute or therapy at the discretion of\\n           their physician. Patients may cross over to arm I if they have evidence of disease\\n           progression.\\n\\n      Blood samples are collected periodically for pharmacokinetic analysis of melphalan.\\n\\n      After completion of study treatment, patients are followed periodically for 4 years and then\\n      annually for survival.\\n\\n      PROJECTED ACCRUAL: A total of 92 patients will be accrued for this study.\\n    \"," + 
                "    \"keywordText\": \"liver metastases, extraocular extension melanoma, stage IV melanoma, recurrent melanoma, recurrent intraocular melanoma, metastatic intraocular melanoma, iris melanoma, ciliary body and choroid melanoma, medium/large size\"," + 
                "    \"officialTitle\": \"A Random-Assignment Study of Hepatic Arterial Infusion of Melphalan With Venous Filtration Via Peripheral Hepatic Perfusion (PHP) (Delcath System) Versus Best Alternative Care for Ocular and Cutaneous Melanoma Metastatic to the Liver\"," + 
                "    \"startDateTypeCode\": \"Actual\"," + 
                "    \"primaryCompletionDateTypeCode\": \"Actual\"," + 
                "    \"completionDateTypeCode\": \"Actual\"," + 
                "    \"startDate\": \"February 2006\"," + 
                "    \"primaryCompletionDate\": \"August 2012\"," + 
                "    \"completionDate\": \"August 2012\"," + 
                "    \"targetAccrualNumber\": 93," + 
                "    \"expandedAccessIndicator\": false," + 
                "    \"phaseCode\": \"III\"," + 
                "    \"recordVerificationDate\": \"October 2013\"," + 
                "    \"acceptHealthyVolunteersIndicator\": false," + 
                "    \"dataMonitoringCommitteeAppointedIndicator\": null," + 
                "    \"primaryPurposeCode\": \"Other\"," + 
                "    \"primaryPurposeAdditionalQualifierCode\": \"Other\"," + 
                "    \"primaryPurposeOtherText\": \"Not provided by ClinicalTrials.gov\"," + 
                "    \"userLastCreated\": \"ClinicalTrials.gov Import\"," + 
                "    \"secondaryIdentifiers\": [" + 
                "      \"NCI-06-C-0088\"," + 
                "      \"NCI-P6701\"" + 
                "    ]," + 
                "    \"studySource\": \"ClinicalTrials.gov\"," + 
                "    \"allocationCode\": \"Randomized Controlled Trial\"," + 
                "    \"blindedRoleCode\": null," + 
                "    \"designConfigurationCode\": \"Cross-over\"," + 
                "    \"numberOfInterventionGroups\": 2," + 
                "    \"studyClassificationCode\": null" + 
                "  }";
        try {
            ObjectMapper mapper = new ObjectMapper();
            // JSON from String to Object
            StudyProtocolWebServiceDTO dto =  mapper.readValue(jsonStr, StudyProtocolWebServiceDTO.class);
           // dto.getAcronym();
            System.out.println("Hello World");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

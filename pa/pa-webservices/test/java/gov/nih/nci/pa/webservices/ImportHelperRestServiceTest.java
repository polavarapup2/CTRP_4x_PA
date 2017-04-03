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
                "    \"studyProtocolId\": \"187455855\"," + 
                "    \"nciId\": \"NCI-2015-00758\"," + 
                "    \"acronym\": null," + 
                "    \"startDateTypeCode\": \"Anticipated\"," + 
                "    \"primaryCompletionDateTypeCode\": \"Actual\"," + 
                "    \"completionDateTypeCode\": \"Actual\"," + 
                "    \"startDate\": \"May 29, 2014\"," + 
                "    \"primaryCompletionDate\": \"August 12, 2015\"," + 
                "    \"completionDate\": \"August 12, 2015\"," + 
                "    \"targetAccrualNumber\": 0," + 
                "    \"expandedAccessIndicator\": false," + 
                "    \"phaseCode\": \"II\"," + 
                "    \"recordVerificationDate\": \"March 2017\"," + 
                "    \"acceptHealthyVolunteersIndicator\": false," + 
                "    \"dataMonitoringCommitteeAppointedIndicator\": true," + 
                "    \"primaryPurposeCode\": \"Other\"," + 
                "    \"primaryPurposeAdditionalQualifierCode\": \"Other\"," + 
                "    \"primaryPurposeOtherText\": \"Not provided by ClinicalTrials.gov\"," + 
                "    \"userLastCreated\": \"ClinicalTrials.gov Import\"," + 
                "    \"secondaryIdentifiers\": [" + 
                "      \"1200.203\"" + 
                "    ]," + 
                "    \"studySource\": \"ClinicalTrials.gov\"," + 
                "    \"allocationCode\": null," + 
                "    \"blindedRoleCode\": null," + 
                "    \"designConfigurationCode\": \"Single Group\"," + 
                "    \"numberOfInterventionGroups\": 1," + 
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

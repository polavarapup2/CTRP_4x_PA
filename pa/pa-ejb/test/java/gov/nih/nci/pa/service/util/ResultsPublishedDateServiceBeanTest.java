/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.service.ctgov.ClinicalStudy;
import gov.nih.nci.pa.service.ctgov.DateStruct;
import gov.nih.nci.pa.util.AbstractEjbTestCase;
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.pa.util.PaEarPropertyReader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;


public class ResultsPublishedDateServiceBeanTest extends AbstractEjbTestCase {

   

private ResultsPublishedDateServiceBean resultsPublishedDateServiceBean;

private MailManagerServiceLocal mailManagerSvc;

protected ProtocolQueryServiceLocal protocolQueryServiceLocal;


   
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
      
        AbstractMockitoTest mockitoTest = new AbstractMockitoTest();
        mockitoTest.setUp();
        

        
        final Field propsField = PaEarPropertyReader.class
                .getDeclaredField("PROPS");
        propsField.setAccessible(true);
        Properties props = (Properties) propsField.get(null);
        props.put("resultsUpdater.trials.job.email.subject", "resultsUpdater.trials.job.email.subject");
        
        
        resultsPublishedDateServiceBean = 
                (ResultsPublishedDateServiceBean) getEjbBean(ResultsPublishedDateServiceBean.class);
        
        mailManagerSvc = mock(MailManagerServiceLocal.class);
        
        protocolQueryServiceLocal = mock(ProtocolQueryServiceLocal.class);
        CTGovSyncServiceLocal ctGovSyncServiceLocal =mock(CTGovSyncServiceLocal.class);
       
        
        
        
        List<StudyProtocolQueryDTO> queryDTOList = new ArrayList<StudyProtocolQueryDTO>();
        
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setNciIdentifier("NCI-2012-0001");
        dto.setNctIdentifier("NCI20120001");
       
        dto.setStudyStatusCode(StudyStatusCode.COMPLETE);
        dto.setLocalStudyProtocolIdentifier("LEAD_ORG_ID_0001");
        dto.setStudyProtocolId(100L);
        dto.setLeadOrganizationPOId(1L);
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        dto.setPrsReleaseDate(new Date());
        
        queryDTOList.add(dto);

        
        when(
                protocolQueryServiceLocal
                        .getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class),
                                Matchers.<ProtocolQueryPerformanceHints>anyVararg()))
                .thenReturn(queryDTOList);
        
        ClinicalStudy clinicalStudy = new ClinicalStudy();
        DateStruct dateStruct = new DateStruct();
        dateStruct.setContent("October 16, 2013");
        clinicalStudy.setFirstreceivedResultsDate(dateStruct);
        
        
        when (ctGovSyncServiceLocal.getCtGovStudyByNctId(any(String.class))).thenReturn(clinicalStudy);
 
        
        resultsPublishedDateServiceBean.setMailManagerSerivceLocal(mailManagerSvc);
        resultsPublishedDateServiceBean.setProtocolQueryService(protocolQueryServiceLocal);
       resultsPublishedDateServiceBean.setCtGovSyncService(ctGovSyncServiceLocal);
        
        
    }

   @Test
   public void testIfTrialPublishDateIsUpdated() throws Exception {
       
      
       resultsPublishedDateServiceBean.updatePublishedDate();
       List<String> updatedTrialsList = resultsPublishedDateServiceBean.getUpdatedTrialsList();
       assertTrue(updatedTrialsList!=null);
       assertTrue(updatedTrialsList.size() > 0);
       assertTrue(updatedTrialsList.get(0).equalsIgnoreCase("NCI-2012-0001"));
       
   }

 
}

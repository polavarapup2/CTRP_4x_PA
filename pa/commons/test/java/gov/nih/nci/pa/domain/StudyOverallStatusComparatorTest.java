package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.enums.StudyStatusCode;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import org.junit.Test;
/**
 * 
 * @author Reshma Koganti
 *
 */
public class StudyOverallStatusComparatorTest {
    @Test
    public void testStudyOverallStatusComparator() {
       StudyProtocol sp = new StudyProtocol();
       sp.setId(1L);
       sp.getStudyOverallStatuses().addAll(createStudyOverallStatusList(113317096L,74987750L,111485238L,"2015-01-23 10:10:10.0","2015-01-23 10:10:10.0","2015-01-23 10:10:10.0"));
       Iterator itr = sp.getStudyOverallStatuses().iterator(); 
       assertEquals(113317096L, ((StudyOverallStatus) itr.next()).getId().longValue());
       assertEquals(111485238L, ((StudyOverallStatus) itr.next()).getId().longValue());
       assertEquals(74987750L, ((StudyOverallStatus) itr.next()).getId().longValue());
       
       sp = new StudyProtocol();
       sp.setId(1L);
       sp.getStudyOverallStatuses().addAll(createStudyOverallStatusList(113317096L,74987750L,111485238L,"","2015-01-23 10:10:10.0","2015-01-23 10:10:10.0"));
       itr = sp.getStudyOverallStatuses().iterator(); 
       assertEquals(111485238L, ((StudyOverallStatus) itr.next()).getId().longValue());
       assertEquals(74987750L, ((StudyOverallStatus) itr.next()).getId().longValue());
       assertEquals(113317096L, ((StudyOverallStatus) itr.next()).getId().longValue());
       
       sp = new StudyProtocol();
       sp.setId(1L);
       sp.getStudyOverallStatuses().addAll(createStudyOverallStatusList(113317096L,74987750L,111485238L,"","",""));
       itr = sp.getStudyOverallStatuses().iterator(); 
       assertEquals(113317096L, ((StudyOverallStatus) itr.next()).getId().longValue());
       assertEquals(111485238L, ((StudyOverallStatus) itr.next()).getId().longValue());
       assertEquals(74987750L, ((StudyOverallStatus) itr.next()).getId().longValue());
       
       sp = new StudyProtocol();
       sp.setId(1L);
       sp.getStudyOverallStatuses().addAll(createStudyOverallStatusList(0L,0L,0L,"","",""));
       itr = sp.getStudyOverallStatuses().iterator(); 
       assertEquals(0, ((StudyOverallStatus) itr.next()).getId().longValue());
       
       sp = new StudyProtocol();
       sp.setId(1L);
       sp.getStudyOverallStatuses().addAll(createStudyOverallStatusList(0L,12L,11L,"","",""));
       itr = sp.getStudyOverallStatuses().iterator(); 
       assertEquals(12L, ((StudyOverallStatus) itr.next()).getId().longValue());
       assertEquals(11L, ((StudyOverallStatus) itr.next()).getId().longValue());
       assertEquals(0, ((StudyOverallStatus) itr.next()).getId().longValue());
       
       sp = new StudyProtocol();
       sp.setId(1L);
       sp.getStudyOverallStatuses().addAll(createStudyOverallStatusList(0L,0L,0L,"2015-01-23 10:10:10.0","2015-01-23 10:10:10.0","2015-01-23 10:10:10.0"));
       itr = sp.getStudyOverallStatuses().iterator(); 
       assertEquals(0, ((StudyOverallStatus) itr.next()).getId().longValue());
       
       
       sp = new StudyProtocol();
       sp.setId(1L);
       sp.getStudyOverallStatuses().addAll(createStudyOverallStatusList(0L,1L,2L,"","2015-01-23 10:10:10.0","2015-02-23 10:10:10.0"));
       itr = sp.getStudyOverallStatuses().iterator(); 
       assertEquals(2, ((StudyOverallStatus) itr.next()).getId().longValue());
       assertEquals(1, ((StudyOverallStatus) itr.next()).getId().longValue());
       assertEquals(0, ((StudyOverallStatus) itr.next()).getId().longValue());
    }
    
    private List<StudyOverallStatus> createStudyOverallStatusList(Long id1,Long id2, 
          Long id3,String statusDate1,String statusDate2,String statusDate3) {
        List<StudyOverallStatus> result = new ArrayList<StudyOverallStatus>();
        
        StudyOverallStatus sos1 = new StudyOverallStatus();
        sos1.setId(id1);
        if (statusDate1.isEmpty()) {
           sos1.setStatusDate(null);
        } else {
            sos1.setStatusDate(Timestamp.valueOf(statusDate1));
        }
        sos1.setStatusCode(StudyStatusCode.APPROVED);
        result.add(sos1);
        
        StudyOverallStatus sos2 = new StudyOverallStatus();
        sos2.setId(id2);
        if (statusDate2.isEmpty()) {
            sos2.setStatusDate(null);
         } else {
             sos2.setStatusDate(Timestamp.valueOf(statusDate2));
         }
        sos2.setStatusCode(StudyStatusCode.CLOSED_TO_ACCRUAL);
        result.add(sos2);
        
        StudyOverallStatus sos3 = new StudyOverallStatus();
        sos3.setId(id3);
        if (statusDate3.isEmpty()) {
            sos3.setStatusDate(null);
         } else {
             sos3.setStatusDate(Timestamp.valueOf(statusDate3));
         }
        sos3.setStatusCode(StudyStatusCode.ACTIVE);
        result.add(sos3);
        
        return result;
        
    }
}

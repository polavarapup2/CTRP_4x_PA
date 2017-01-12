package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.StudyRecordChange;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class StudyRecordServiceTest extends AbstractHibernateTestCase {

    private final StudyRecordServiceLocal notesService = new StudyRecordServiceLocal();
    
    @Before
    public void init() throws Exception {
        TestSchema.primeData();
        CSMUserService.setInstance(new MockCSMUserService());
      
    }
    
   
    
    @Test
    public void createStudyChangeRecord() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        long studyProtocolId = TestSchema.studyProtocolIds.get(0);
        notesService.addStudyRecordChange("Some change needed", "Added some change",
                simpleDateFormat.format(new Date()), studyProtocolId);
        
        List<StudyRecordChange> studyRercodsList= notesService.getStudyRecordsList(studyProtocolId);
        assert(studyRercodsList.size() ==1);
       
    }
    
    @Test
    public void createStudyChangeRecordExceptionTest() throws Exception {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
       
        try {
        notesService.addStudyRecordChange(
               null, null, simpleDateFormat.format(new Date()), 0l);
        } catch(PAException e) {
        e.printStackTrace();
        String msg = e.getMessage();
        Assert.assertTrue(msg
                .contains("could not insert"));
        }
    }
    
   
    
    
    
    @Test
    public void editStudyRecordChange() throws Exception {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        long studyProtocolId = TestSchema.studyProtocolIds.get(0);
        
        notesService.addStudyRecordChange("Some change needed", "Added some change",
                simpleDateFormat.format(new Date()), studyProtocolId);
        List<StudyRecordChange> studyRercodsList= notesService.getStudyRecordsList(studyProtocolId);
        
        assert(studyRercodsList.size() ==1);
        
        StudyRecordChange studyRecordChange = (StudyRecordChange)studyRercodsList.get(0);
        
        notesService.editStudyRecordChange("Edit disc type", "edit action taken",
                simpleDateFormat.format(new Date()), studyRecordChange.getId());
        
        studyRercodsList= notesService.getStudyRecordsList(studyProtocolId);
        
        assert(studyRercodsList.size() ==1);
        studyRecordChange = new StudyRecordChange();
        studyRecordChange = (StudyRecordChange)studyRercodsList.get(0);
        
        assert(studyRecordChange.getChangeType().equals("Edit disc type"));
        assert(studyRecordChange.getActionTaken().equals("edit action taken")); 
        
    }
    
    @Test
    public void editStudyRecordChangeExceptionTest() throws Exception {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
       
        try {
        notesService.editStudyRecordChange(
               "change", "change", simpleDateFormat.format(new Date()), 0l);
        } catch(PAException e) {
        e.printStackTrace();
        String msg = e.getMessage();
        Assert.assertTrue(msg==null);
        }
    }
    
    
    
    
    @Test
    public void deleteStudyChangeRecord() throws Exception {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        long studyProtocolId = TestSchema.studyProtocolIds.get(0);
        notesService.addStudyRecordChange("Some change needed", "Added some change",
                simpleDateFormat.format(new Date()), studyProtocolId);
        
        List<StudyRecordChange> studyRercodsList= notesService.getStudyRecordsList(studyProtocolId);
        assert(studyRercodsList.size() ==1);
        
        StudyRecordChange studyRecordChange = (StudyRecordChange)studyRercodsList.get(0);
        
        notesService.deleteStudyRecord(studyRecordChange.getId());
        
        studyRercodsList= notesService.getStudyRecordsList(studyProtocolId);
        
        assert(studyRercodsList.size() ==0);
        
    }
    
    @Test
    public void deleteDataDiscExceptionTest() throws Exception {
       try {
        notesService.deleteStudyRecord(0l);
       } catch (Exception e) {
           e.printStackTrace();
           String msg = e.getMessage();
           Assert.assertTrue(msg==null);
       }
       
    }
     
}

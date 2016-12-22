

package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.StudyRecordChange;

import java.util.List;

import javax.ejb.Local;

/**
 * @author Apratim K
 * @since 06/26/2015
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Local
public interface StudyRecordService   {
   
    /**
     * @param studyProtocolId studyProtocolId
     * @return StudyRecordsList
     * @throws PAException PAException
     */
    List<StudyRecordChange> getStudyRecordsList(Long studyProtocolId) throws PAException;
    
  
    
    /**
     * @param id id
     * @throws PAException PAException
     */
    void deleteStudyRecord(Long id) throws PAException;
    
   
    /**
     * @param changeType changeType
     * @param actionTaken actionTaken
     * @param actionCompletionDate actionCompletionDate
     * @param studyProtocolId studyProtocolId
     * @throws PAException PAException
     */
    void addStudyRecordChange(String changeType, String actionTaken, String actionCompletionDate ,
            long studyProtocolId) throws PAException;
    
    /**
     * @param changeType changeType
     * @param actionTaken actionTaken
     * @param actionCompletionDate actionCompletionDate
     * @param studyRecordChangeId studyRecordChangeId
     * @throws PAException PAException
     */ 
    void editStudyRecordChange(String changeType, String actionTaken, String actionCompletionDate ,
            long studyRecordChangeId) throws PAException;
   
}

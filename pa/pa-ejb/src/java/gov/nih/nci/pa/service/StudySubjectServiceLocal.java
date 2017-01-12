package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.StudySubject;

import java.util.List;

import javax.ejb.Local;

/**
 * 
 * @author Reshma Koganti
 *
 */
@Local
public interface StudySubjectServiceLocal {
    /**
     *
     *  @return result list (empty list if none found)
     *  @param studyIdentifier studyIdentifier
     *  @param participatingSiteIdentifier participatingSiteIdentifier
     *  @throws PAException PAException
     *  
     */
     List<StudySubject> getBySiteAndStudyId(Long studyIdentifier, Long participatingSiteIdentifier) throws PAException;

}

package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRecordChange;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author Apratim K
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)

public class StudyRecordServiceLocal implements StudyRecordService {

    @Override
 public List<StudyRecordChange> getStudyRecordsList(
            Long studyProtocolId) throws PAException {
        
        List<StudyRecordChange>  studyRecordsList = null;
        Criteria criteria = null;
        Session session = PaHibernateUtil.getCurrentSession();
        criteria = session.createCriteria(StudyRecordChange.class);
        
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setId(studyProtocolId);
        criteria.add(Restrictions.eq("studyProtocol", studyProtocol));
        studyRecordsList = criteria.list();
        
        return studyRecordsList;
    }

  
    @Override
    public void deleteStudyRecord(Long id) throws PAException {
        try {
            Session session = PaHibernateUtil.getCurrentSession();
            String queryString = null;
             queryString = "delete from StudyRecordChange where id=" + id;
            Query query = session.createQuery(queryString);
            query.executeUpdate();
            
         
        } catch (Exception e) {
            throw new PAException(e.getMessage());
          }
        
    }

   

    @Override
    public void addStudyRecordChange(String changeType, String actionTaken,
            String actionCompletionDate, long studyProtocolId)
            throws PAException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        Date parsedDate;
        try {
            parsedDate = simpleDateFormat.parse(actionCompletionDate);
       
        Session session = PaHibernateUtil.getCurrentSession();
        StudyRecordChange studyRecordChange = new StudyRecordChange();
        studyRecordChange.setChangeType(changeType);
        studyRecordChange.setActionTaken(actionTaken);
        studyRecordChange.setActionCompletionDate(new Timestamp(parsedDate.getTime()));
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setId(studyProtocolId);
        studyRecordChange.setStudyProtocol(studyProtocol);
        
        Date today = new Date();
        User user = CSMUserService.getInstance().getCSMUserFromCache(
                UsernameHolder.getUser()); 
        studyRecordChange.setDateLastCreated(today);
        studyRecordChange.setUserLastCreated(user);
        
        session.save(studyRecordChange);
        
        } catch (Exception e) {
          throw new PAException(e.getMessage());
        }
        
    }

    @Override
    public void editStudyRecordChange(String changeType, String actionTaken,
            String actionCompletionDate, long studyRecordChangeId)
            throws PAException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        Date parsedDate;
        try {
        
        Date today = new Date();
        User user = CSMUserService.getInstance().getCSMUserFromCache(
                    UsernameHolder.getUser());     
        parsedDate = simpleDateFormat.parse(actionCompletionDate);
        Session session = PaHibernateUtil.getCurrentSession();
        StudyRecordChange studyRecordChange = (StudyRecordChange)
                session.get(StudyRecordChange.class, studyRecordChangeId);
        studyRecordChange.setChangeType(changeType);
        studyRecordChange.setActionTaken(actionTaken);
        studyRecordChange.setActionCompletionDate(new Timestamp(parsedDate.getTime()));
        studyRecordChange.setDateLastUpdated(today);
        studyRecordChange.setUserLastUpdated(user);
        session.update(studyRecordChange);
        } catch (Exception e) {
            throw new PAException(e.getMessage());
          }
        
    }

}

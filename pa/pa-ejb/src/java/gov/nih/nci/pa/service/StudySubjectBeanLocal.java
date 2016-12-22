package gov.nih.nci.pa.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import org.hibernate.Query;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;
/**
 * 
 * @author Reshma Koganti
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class StudySubjectBeanLocal extends AbstractBaseSearchBean<StudySubject>
        implements StudySubjectServiceLocal {

     @Override
     public List<StudySubject> getBySiteAndStudyId(Long studyIdentifier, Long participatingSiteIdentifier)
              throws PAException {
        String str = "select ssub from StudySubject ssub"
            + " where ssub.studyProtocol.id = :studyProtocolId "
            + " and ssub.statusCode = '" + FunctionalRoleStatusCode.ACTIVE.getName() + "'"
            + " and ssub.studySite.id = :studySiteId"
            + " order by ssub.id ";
        Query query = PaHibernateUtil.getCurrentSession().createQuery(str);
        query.setParameter("studyProtocolId", studyIdentifier);
        query.setParameter("studySiteId", participatingSiteIdentifier);
        return query.list();
     }
     
}

package gov.nih.nci.pa.service.util;


import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.Family;
import gov.nih.nci.pa.domain.StudyCancerCenterAccrual;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * StudyCancerCenterAccrualBeanLocal
 * @author chandrasekaravr 
 */
@Stateless
@Interceptors({ RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyCancerCenterAccrualBean implements StudyCancerCenterAccrualServiceLocal, 
        StudyCancerCenterAccrualServiceRemote {

    private static final Logger LOG = Logger.getLogger(StudyCancerCenterAccrualBean.class);

    /**
     * Returns the study cancer center accruals for a given family po id
     * @param familyPoId family PO id 
     * @return Map<Long, Integer> - map of trial id to its target accrual
     * @throws PAValidationException if program code already exists
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<Long, Integer> getStudyCancerCenterAccrualsByFamily(Long familyPoId) 
            throws PAValidationException {
        final Session s = PaHibernateUtil.getCurrentSession();
        
        Query sccAccFetchQuery = s.createQuery(
                        "select scca from StudyCancerCenterAccrual scca where scca.family.poId=:poId");
        
        sccAccFetchQuery.setParameter("poId", familyPoId);
        
        List<Object> results = sccAccFetchQuery.list();
        Map<Long, Integer> resMap = new HashMap<Long, Integer>();
        StudyCancerCenterAccrual scca = null;
        if (results != null) {
            for (Object obj : results) {
                scca = (StudyCancerCenterAccrual) obj;
                resMap.put(scca.getStudyProtocol().getId(), scca.getTargetedAccrual());
            }
        }
        return resMap;
    }

    /**
     * saves or updates Study Cancer Center Accrual in db
     * @param familyPOId family PO id
     * @param studyProtocolId study Protocol id
     * @param targetAccrual - target accrual count
     * @throws PAValidationException if program code already exists
     */
    @Override
    public void saveOrUpdateStudyCancerCenterAccrual(Long familyPOId,
            Long studyProtocolId, Integer targetAccrual)
            throws PAValidationException {
        if (studyProtocolId == null) {
            throw new PAValidationException("StudyProtocol identifier is required");
        }
        
        if (familyPOId == null) {
            throw new PAValidationException("Family PO identifier is required");
        }
        
        final Session s = PaHibernateUtil.getCurrentSession();
        
        Query sccAccFetchQuery = s.createQuery(
                "select scca from StudyCancerCenterAccrual scca "
                + "where scca.family.poId=:poId and scca.studyProtocol.id=:spId");

        sccAccFetchQuery.setParameter("poId", familyPOId);  
        sccAccFetchQuery.setParameter("spId", studyProtocolId);  

        Object sccAccObj = sccAccFetchQuery.uniqueResult();
        StudyCancerCenterAccrual sccAccrual = new StudyCancerCenterAccrual();
        if (sccAccObj != null) {
            sccAccrual = (StudyCancerCenterAccrual) sccAccObj;
        } else {
            Query familyFetchQuery = s.createQuery(
                            "select fm from Family fm where fm.poId=:poId");
            
            familyFetchQuery.setParameter("poId", familyPOId);        
            
            Object familyObj = familyFetchQuery.uniqueResult();
            
            if (familyObj == null) {
                throw new PAValidationException("Family not found for the PO Id, " + familyPOId);
            }
            
            Family family = (Family) familyObj;
            
            final StudyProtocol sp = (StudyProtocol) s.get(StudyProtocol.class,
                    studyProtocolId);
            
            sccAccrual.setFamily(family);
            sccAccrual.setStudyProtocol(sp);
        }
        
        sccAccrual.setTargetedAccrual(targetAccrual);
        
        s.saveOrUpdate(sccAccrual);
    }
    
    
}

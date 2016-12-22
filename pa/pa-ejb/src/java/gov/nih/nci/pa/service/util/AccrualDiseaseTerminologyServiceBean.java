package gov.nih.nci.pa.service.util; // NOPMD

import static gov.nih.nci.pa.service.AbstractBaseIsoService.ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.ADMIN_ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SCIENTIFIC_ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SECURITY_DOMAIN;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUBMITTER_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUPER_ABSTRACTOR_ROLE;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jboss.ejb3.annotation.SecurityDomain;

/**
 * @author Hugh Reinhart
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SecurityDomain(SECURITY_DOMAIN)
@RolesAllowed({ SUBMITTER_ROLE, ADMIN_ABSTRACTOR_ROLE, ABSTRACTOR_ROLE,
    SCIENTIFIC_ABSTRACTOR_ROLE, SUPER_ABSTRACTOR_ROLE })
public class AccrualDiseaseTerminologyServiceBean implements
        AccrualDiseaseTerminologyServiceRemote {

    private static final Logger LOG = Logger.getLogger(AccrualDiseaseTerminologyServiceBean.class);

    /** The default disease terminology. */
    public static final String DEFAULT_CODE_SYSTEM = "SDC";

    @Override
    public List<String> getValidCodeSystems() {
        LinkedList<String> result = new LinkedList<String>();
        try {
            Session session = PaHibernateUtil.getCurrentSession();
            String hql = "select distinct d.codeSystem from AccrualDisease d order by d.codeSystem";
            Query query = session.createQuery(hql);
            @SuppressWarnings("unchecked")
            List<String> qList = query.list();
            for (String code : qList) {
                if (DEFAULT_CODE_SYSTEM.equals(code)) {
                    result.addFirst(code);
                } else {
                    result.add(code);
                }
            }
        } catch (Exception e) {
            LOG.error(e);
        }
        return result;
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public String getCodeSystem(Long studyProtocolId) {
        if (studyProtocolId == null) {
            return null;
        }
        String qryStr = "SELECT accrualDiseaseCodeSystem FROM " + StudyProtocol.class.getSimpleName()
                + " WHERE id = :id";
        Query qry = PaHibernateUtil.getCurrentSession().createQuery(qryStr);
        qry.setParameter("id", studyProtocolId);
        List<String> rList = qry.list();
        return rList.isEmpty() ? null : rList.get(0);
    }

    @Override
    public void updateCodeSystem(Long studyProtocolId, String codeSystem)
            throws PAException {
        if (!canChangeCodeSystem(studyProtocolId)) {
            throw new PAException("The disease code system for this trial cannot be changed.");
        }
        List<String> validCodes = getValidCodeSystems();
        if (!validCodes.contains(codeSystem)) {
            throw new PAException("Invalid disease code system: " + codeSystem);
        }
        String qryStr = "UPDATE " + StudyProtocol.class.getSimpleName()
                + " SET accrualDiseaseCodeSystem = :newCodeSystem"
                + " WHERE id = :id";
        Query qry = PaHibernateUtil.getCurrentSession().createQuery(qryStr);
        qry.setParameter("newCodeSystem", codeSystem);
        qry.setParameter("id", studyProtocolId);
        qry.executeUpdate();
    }

    @Override
    public Boolean canChangeCodeSystem(Long studyProtocolId) {
        if (studyProtocolId == null) {
            return true;
        }
        Criteria criteria = PaHibernateUtil.getCurrentSession().createCriteria(StudySubject.class, "ss");
        criteria.createAlias("studyProtocol", "sp");
        criteria.add(Restrictions.eq("sp.id", studyProtocolId));
        criteria.add(Restrictions.eq("statusCode", FunctionalRoleStatusCode.ACTIVE));
        criteria.add(Restrictions.isNotNull("disease"));
        criteria.setProjection(Projections.rowCount());
        Integer count = (Integer) criteria.uniqueResult();
        return (0 == count);
    }
    
    @Override
    public Map<Long, Boolean> canChangeCodeSystemForSpIds(List<Long> studyProtocolIds) {
        Map<Long, Boolean> result = new HashMap<Long, Boolean>();
        Session session = PaHibernateUtil.getCurrentSession();
        List<Object[]> queryList = null;
        Map<Long, Long> resultSet = new HashMap<Long, Long>();
        if (!studyProtocolIds.isEmpty()) {
            SQLQuery query = session
            .createSQLQuery("select study_protocol_identifier, count(study_protocol_identifier)"
                 + " from study_subject where study_protocol_identifier in (:ids)"
                 + " and disease_identifier IS not null and status_code = 'ACTIVE' GROUP BY "
                 + " study_protocol_identifier HAVING count(study_protocol_identifier) >= 1");
            query.setParameterList("ids", studyProtocolIds);
            queryList = query.list();
            for (Object[] oArr : queryList) {
                BigInteger ret = null;
                if (oArr[0] instanceof BigInteger) { 
                    ret =  (BigInteger) oArr[0];
                    resultSet.put(ret.longValue(), Long.parseLong(oArr[1].toString()));
                }       
            }
            for (Long id : studyProtocolIds) {
               if (resultSet.containsKey(id)) {
                 result.put(id, false);
               } else {
                 result.put(id, true);
               }
            }
        }
        return result;
    }
    
}

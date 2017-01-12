package gov.nih.nci.pa.service;


import java.util.List;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.PlannedMarkerSynonyms;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.convert.PlannedMarkerSynonymsConverter;
import gov.nih.nci.pa.iso.dto.PlannedMarkerSynonymsDTO;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * 
 * @author Reshma.Koganti
 * 
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@SuppressWarnings("PMD.ExcessiveParameterList")
public class PlannedMarkerSynonymsBeanLocal extends
      AbstractBaseIsoService<PlannedMarkerSynonymsDTO, 
      PlannedMarkerSynonyms, PlannedMarkerSynonymsConverter>
      implements PlannedMarkerSynonymsServiceLocal {
    /**
     * 
     * @param pmSyncId
     *            pmSyncId
     * @param synonyms
     *            synonyms
     * @param statusCode
     *            statusCode
     */
    public void insertValues(Long pmSyncId, List<String> synonyms, String statusCode) {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("insert into planned_marker_synonyms"
                            + " (alternate_name, pm_sync_identifier, STATUS_CODE)"
                            + " values (:altName, :pmSyncId, :statusCode)");
            for (String synonym : synonyms) {
               query.setParameter("altName", synonym);
               query.setParameter("pmSyncId", pmSyncId);
               query.setParameter("statusCode", statusCode);
               query.executeUpdate();
            }
        
    }
    
    /**
     * Gets the List of all Integer values.
     * 
     * @return the list of all Integer values
     * @throws PAException
     *             on error.
     * @param id
     *            id
     */
    public List<Number> getIdentifierBySyncId(Long id) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        SQLQuery query = session
                .createSQLQuery("select identifier from planned_marker_synonyms where pm_sync_identifier=:id");
        query.setParameter("id", id);
        return (List<Number>) query.list();
    }
    
    /**
     * Gets the List
     * 
     * @return the list of all Integer values
     * @throws PAException
     *             on error.
     * @param id
     *            id
     */
    public List<String> getAltNamesBySyncId(Long id) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        SQLQuery query = session
                .createSQLQuery("select alternate_name from planned_marker_synonyms where pm_sync_identifier=:id");
        query.setParameter("id", id);
        return (List<String>) query.list();
    }
    
    
    /**
     * 
     * @param oldSynonyms oldSynonyms
     * @param synonyms synonyms
     * @param statusCode statusCode
     * @param  pmId pmId
     * @throws PAException on error.
     */
    public void insertAndUpdateLogic(List<String> oldSynonyms, 
              List<String> synonyms, String statusCode, Long pmId) throws PAException {

        for (String synonym : synonyms) {
           if (!oldSynonyms.contains(synonym)) {
               //delete old and insert new synonym values 
                deleteBySyncId(pmId);
                insertValues(pmId, synonyms, statusCode);
                break;
           } else {
               updateStatusByPMSynID(pmId, statusCode); 
           }
        }
        if ((synonyms == null || synonyms.isEmpty()) 
               && (oldSynonyms != null && !oldSynonyms.isEmpty())) {
             updateStatusByPMSynID(pmId, ActiveInactivePendingCode.DELECTED_IN_CADSR
                    .getName());
        }


    }
    
    /**
     * deletes PlannedMarkerSynonyms values based on Pmsyncid.
     * @param pmSyncId pmSyncId
     */
    public void deleteBySyncId(Long pmSyncId) {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("Delete from planned_marker_synonyms where pm_sync_identifier=:pmSyncId");
        query.setParameter("pmSyncId", pmSyncId);
        query.executeUpdate();
    }
    
    
    /**
     * updates the Planned Marker Synonyms with status
     * 
     * @param identifier identifier
     * @param status status
     * @throws PAException PAException
     */
    public void updateStatusByPMSynID(Long identifier, String status) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        
        SQLQuery query = session.createSQLQuery("update planned_marker_synonyms set status_code=:status"
                + " where pm_sync_identifier=:identifier");
        query.setParameter("status", status);
        query.setParameter("identifier", identifier);
        query.executeUpdate();
    }

}

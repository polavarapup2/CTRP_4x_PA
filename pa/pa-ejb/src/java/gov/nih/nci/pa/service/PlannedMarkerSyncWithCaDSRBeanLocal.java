package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.PlannedMarkerSyncWithCaDSR;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.convert.PlannedMarkerSyncWithCaDSRConverter;
import gov.nih.nci.pa.iso.dto.CaDSRDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerSyncWithCaDSRDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
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
@SuppressWarnings({ "PMD.ExcessiveParameterList", "PMD.CyclomaticComplexity",
    "PMD.ExcessiveClassLength", "PMD.TooManyMethods" })
public class PlannedMarkerSyncWithCaDSRBeanLocal
        extends
        AbstractBaseIsoService<PlannedMarkerSyncWithCaDSRDTO, 
        PlannedMarkerSyncWithCaDSR, PlannedMarkerSyncWithCaDSRConverter>
        implements PlannedMarkerSyncWithCaDSRServiceLocal {

    private PlannedMarkerServiceLocal plannedMarkerService;
    private PlannedMarkerSynonymsServiceLocal pmSynonymService;

    // private static final String CSM_LOOKUP_ERR_MSG =
    // "CSM exception while retrieving CSM user: ";
    private static final String NAME = "name";

    private static final String CADSRID = "cadsrId";
    private static final String MEANING = "meaning";
    private static final String DESCRIPTION = "description";
    private static final String  STATUS = "statusCode";
    private static final String NT_TERM = "ntTermId";
    private static final String PV_VALUE = "pvValue";
    private static final String INSERT_STATEMENT = "insert into planned_marker_sync_cadsr";
    /** The LOG details. */
    private static final Logger LOG = Logger
            .getLogger(PlannedMarkerSyncWithCaDSRBeanLocal.class);
    /**
     * @param valuesList
     *            valuesList
     * @throws PAException
     *             on error.
     */
    public void syncTableWithCaDSR(List<CaDSRDTO> valuesList)
            throws PAException {
        plannedMarkerService = PaRegistry.getPlannedMarkerService();
        pmSynonymService = PaRegistry.getPMSynonymService();
        // update and insert
        insertAndUpdateLogic(valuesList);
        // deletion
        changeStatusCodeValue(valuesList);
    }

    private Long maxIdentifierValue() throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        List<Number> queryList = null;
        SQLQuery query = session.createSQLQuery("select MAX(identifier) from planned_marker_sync_cadsr");
        queryList = query.list();
        return queryList.get(0).longValue();
    }
    @SuppressWarnings({ "PMD.CyclomaticComplexity" , "PMD.ExcessiveMethodLength" })
    private void insertAndUpdateLogic(List<CaDSRDTO> valuesList) throws PAException {
        for (CaDSRDTO value : valuesList) {
          try {
            Long caDSRId = value.getPublicId();
            String name = value.getVmName();
            String meaning = value.getVmMeaning();
            String pvValue = value.getPvValue();
            List<String> synonyms = value.getAltNames();
            String description = value.getVmDescription();
            String ntTermId = value.getNtTermIdentifier();
            List<Number> idList = (List<Number>) getIdentifierByCadsrId(caDSRId);
            if (!idList.isEmpty()) { 
                Long newId = idList.get(0).longValue();
                List<Number> nameList = getIdentifierByPvName(pvValue);
                if (!nameList.isEmpty()) {
                  Long oldId = nameList.get(0).longValue();
                   if (nameList.size() > 1) {
                     newId = updateNameLogic(newId, nameList);
                   } else {
                   if (!newId.equals(oldId)) {
                       pmSynonymService.deleteBySyncId(oldId);
                       deleteById(oldId);
                       plannedMarkerService.updateStatusOldIDByPMSynID(oldId, 
                         newId, ActiveInactivePendingCode.ACTIVE.getName());
                   }
                  }
                } else { 
                    List<Number> nameUniqueList = getIdentifierByName(pvValue);
                    if (!nameUniqueList.isEmpty()) {
                       newId = updateNameLogic(newId, nameUniqueList);
                    }
                }
                List<String> listofSynValues = pmSynonymService.getAltNamesBySyncId(newId);
                if (synonyms != null && !synonyms.isEmpty()) {
                    if (listofSynValues.isEmpty()) {
                            pmSynonymService.insertValues(newId, 
                                  synonyms, ActiveInactivePendingCode.ACTIVE.getName());
                    } else {
                        pmSynonymService.insertAndUpdateLogic(listofSynValues, 
                                synonyms, ActiveInactivePendingCode.ACTIVE.getName(), newId);
                    }
                }
                updateValues(caDSRId, name, meaning, description, ntTermId, pvValue, 
                        ActiveInactivePendingCode.ACTIVE.getName());
                
            } else {
                // for checking if the name is in pending status or not.
                Long newPMID = null;
                List<Number> nameList = getIdentifierByName(pvValue);
                if (nameList.isEmpty()) {
                    insertValues(caDSRId, name, meaning, description, 
                            ntTermId, pvValue, ActiveInactivePendingCode.ACTIVE.getName());
                    List<Number> insertedPvId = getIdentifierByCadsrId(caDSRId);
                    newPMID = insertedPvId.get(0).longValue();
                } else {
                   updateValueByName(caDSRId, name, meaning, description, ntTermId, pvValue, 
                            ActiveInactivePendingCode.ACTIVE.getName());
                    plannedMarkerService.updateStatusByPMSynID(nameList.get(0)
                         .longValue(), ActiveInactivePendingCode.ACTIVE.getName());
                    newPMID = nameList.get(0).longValue();
                }
                if (synonyms != null && !synonyms.isEmpty()) {
                    pmSynonymService.insertValues(newPMID, 
                        synonyms, ActiveInactivePendingCode.ACTIVE.getName());
                }
            }
          } catch (Exception e) {
            LOG.error("Error with a record", e);
          }
       }
    }
    
    private Long updateNameLogic(Long newId, List<Number> nameUniqueList) throws PAException {
       if (!nameUniqueList.isEmpty()) {
            Long oldId = nameUniqueList.get(0).longValue();
            for (int i = 0; i < nameUniqueList.size(); i++) {
               List<PlannedMarkerSyncWithCaDSRDTO> dtoList = 
                         getValuesById(nameUniqueList.get(i).longValue());
               if (StringUtils.equalsIgnoreCase(dtoList.get(0)
                       .getStatusCode().getCode(), "Pending")) {
                    oldId = IiConverter.convertToLong(dtoList.get(0).getIdentifier());
                    if (!newId.equals(oldId)) {
                        pmSynonymService.deleteBySyncId(oldId);
                        deleteById(oldId);
                        plannedMarkerService.updateStatusOldIDByPMSynID(oldId, 
                          newId, ActiveInactivePendingCode.ACTIVE.getName());
                    }
               } else if (StringUtils.equalsIgnoreCase(dtoList.get(0)
                       .getStatusCode().getCode(), "Inactive")) {
               if (IiConverter.convertToLong(dtoList.get(0).getCaDSRId()) != null) {
                  updateValues(IiConverter.convertToLong(dtoList.get(0).getCaDSRId())
                      , StConverter.convertToString(dtoList.get(0).getName()) + "a"
                      , StConverter.convertToString(dtoList.get(0).getMeaning())
                      , StConverter.convertToString(dtoList.get(0).getDescription())
                      , null, null, ActiveInactivePendingCode.INACTIVE.getName());
               } else {
                  updateValueById(StConverter.convertToString(dtoList.get(0).getName()) + "a"
                       , IiConverter.convertToLong(dtoList.get(0).getIdentifier()));
                }
               }
            }
        }
       return newId;
    }
    /**
     * deletes PlannedMarkerSyncWithCaDSRDTO values based on identifier.
     * @param identifier identifier
     */
    public void deleteById(Long identifier) {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("Delete from planned_marker_sync_cadsr where identifier=:identifier");
        query.setParameter("identifier", identifier);
        query.executeUpdate();
    }

    private void changeStatusCodeValue(List<CaDSRDTO> valuesList)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query1 = session
                .createSQLQuery("Select cadsrid from planned_marker_sync_cadsr "
                        + " where cadsrid is not null");
        List<Number> queryList1 = query1.list();
        Long idValue = 0L;
        List<Long> caDSRValues = new ArrayList<Long>();
        for (CaDSRDTO value : valuesList) {
            caDSRValues.add(value.getPublicId());
        } 
        for (Number oArr : queryList1) {
                idValue = oArr.longValue();
                if (!caDSRValues.contains(idValue)) {
                    updateStatusCode(idValue, 
                            ActiveInactivePendingCode.INACTIVE.getName());
                    List<Number> identifier = getIdentifierByCadsrId(idValue);
                    if (!identifier.isEmpty()) {
                      plannedMarkerService.updateStatusByPMSynID(identifier
                            .get(0).longValue(), 
                            ActiveInactivePendingCode.DELECTED_IN_CADSR
                                    .getName());
                      pmSynonymService.updateStatusByPMSynID(identifier
                            .get(0).longValue(), 
                            ActiveInactivePendingCode.DELECTED_IN_CADSR
                                    .getName());
                    }
                } else {
                    updateStatusCode(idValue, 
                            ActiveInactivePendingCode.ACTIVE.getName());
                    List<Number> identifier = getIdentifierByCadsrId(idValue);
                    if (!identifier.isEmpty()) {
                      plannedMarkerService.updateStatusByPMSynID(identifier
                            .get(0).longValue(), 
                            ActiveInactivePendingCode.ACTIVE.getName());
                      pmSynonymService.updateStatusByPMSynID(identifier
                            .get(0).longValue(), 
                            ActiveInactivePendingCode.ACTIVE.getName());
                    }
                }
        }
    }

    /**
     * Updated the values
     * 
     * @param caDSRId
     *            caDSRId
     * @param name
     *            name
     * @param meaning
     *            meaning
     * @param description
     *            description
     * @param statusCode
     *            statusCode
     * @param ntTermId ntTermId
     * @param pvValue pvValue
     */

    public void updateValues(Long caDSRId, String name, String meaning,
            String description, String ntTermId, String pvValue, String statusCode) {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("update planned_marker_sync_cadsr set name =:name,"
                        + "meaning =:caDSRmeaning,"
                        + " description =:caDSRdescription, nt_term_identifier =:ntTermId,"
                        + "  pv_name =:pvValue, status_code =:caDSRstatusCode"
                        + "  where cadsrid =:cadsrId");
        query.setParameter(NAME, name);
        query.setParameter("caDSRmeaning", meaning);
        query.setParameter("caDSRdescription", description);
        query.setParameter(CADSRID, caDSRId);
        query.setParameter(NT_TERM, ntTermId);
        query.setParameter(PV_VALUE, pvValue);
        query.setParameter("caDSRstatusCode", statusCode);
        query.executeUpdate();
    }
    
    /**
     * Updated the values
     * 
     * @param caDSRId
     *            caDSRId
     * @param name
     *            name
     * @param meaning
     *            meaning
     * @param description
     *            description
     * @param statusCode
     *            statusCode
     */
    public void updateValueByName(Long caDSRId, String name, String meaning,
            String description, String statusCode) {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("update planned_marker_sync_cadsr set meaning =:meaning,"
                        + " description =:description, status_code =:statusCode, cadsrid =:cadsrId"
                        + "  where name =:name");
        query.setParameter(NAME, name);
        query.setParameter(MEANING, meaning);
        query.setParameter(DESCRIPTION, description);
        query.setParameter(CADSRID, caDSRId);
        query.setParameter(STATUS, statusCode);
        query.executeUpdate();
    }
    
    /**
     * Updated the values
     * 
     * @param caDSRId
     *            caDSRId
     * @param name
     *            name
     * @param meaning
     *            meaning
     * @param description
     *            description
     * @param ntTermId ntTermId
     * @param statusCode
     *            statusCode
     * @param pvValue
     *            pvValue           
     *         
     */
    public void updateValueByName(Long caDSRId, String name, String meaning,
            String description, String ntTermId, String pvValue, String statusCode) {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("update planned_marker_sync_cadsr set name =:name,"
                        + " description =:description, status_code =:statusCode,"
                        + " nt_term_identifier =:ntTermId, meaning =:meaning, cadsrid =:cadsrId, pv_name =:pvValue"
                        + "  where name =:pvValue");
        query.setParameter(NAME, name);
        query.setParameter(MEANING, meaning);
        query.setParameter(DESCRIPTION, description);
        query.setParameter(CADSRID, caDSRId);
        query.setParameter(NT_TERM, ntTermId);
        query.setParameter(STATUS, statusCode);
        query.setParameter(PV_VALUE, pvValue);
        query.executeUpdate();
    }

    /**
     * 
     * @param caDSRId
     *            caDSRID
     * @param statusCode
     *            statusCode
     */
    public void updateStatusCode(Long caDSRId, String statusCode) {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("update planned_marker_sync_cadsr set status_code = :statusCode"
                        + "  where cadsrid = :cadsrId");
        query.setParameter("cadsrId", caDSRId);
        query.setParameter(STATUS, statusCode);
        query.executeUpdate();
    }
    
    /**
     * 
     * @param caDSRId
     *            caDSRId
     * @param name
     *            name
     * @param meaning
     *            meaning
     * @param description
     *            description
     * @param statusCode
     *            statusCode
     * @param ntTermId ntTermId
     * @param pvValue pvValue
     */
    public void insertValues(Long caDSRId, String name, String meaning,
            String description, String ntTermId, String pvValue, String statusCode) {
        Session session = PaHibernateUtil.getCurrentSession();
        if (caDSRId != null) {
            SQLQuery query = session
                    .createSQLQuery(INSERT_STATEMENT
                            + " (NAME, MEANING, DESCRIPTION, CADSRID, STATUS_CODE, NT_TERM_IDENTIFIER, PV_NAME)"
                            + " values (:name, :meaning, :description, :cadsrId, :statusCode, :ntTermId, :pvValue)");
            query.setParameter(NAME, name);
            query.setParameter(MEANING, meaning);
            query.setParameter(DESCRIPTION, description);
            query.setParameter(CADSRID, caDSRId);
            query.setParameter(STATUS, statusCode);
            query.setParameter(NT_TERM, ntTermId);
            query.setParameter(PV_VALUE, pvValue);
            query.executeUpdate();
        } else {
            SQLQuery query = session
                    .createSQLQuery(INSERT_STATEMENT
                            + " (NAME, MEANING, DESCRIPTION, CADSRID, STATUS_CODE, PV_NAME)"
                            + " values (:name, :meaning, :description, null , :statusCode, :pvValue)");
            query.setParameter(NAME, name);
            query.setParameter(MEANING, meaning);
            query.setParameter(DESCRIPTION, description);
            query.setParameter(STATUS, statusCode);
            query.setParameter(PV_VALUE, pvValue);
            query.executeUpdate();
        }
    }

    /**
     * Gets the List of all PlannedMarkerSyncWithCaDSRDTO values.
     * 
     * @return the list of all PlannedMarkerSyncWithCaDSRDTO values
     * @throws PAException
     *             on error.
     * @param id
     *            id
     */
    public List<PlannedMarkerSyncWithCaDSRDTO> getValuesById(Long id)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        String hql = "from PlannedMarkerSyncWithCaDSR as pm where pm.id=:id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        List<PlannedMarkerSyncWithCaDSR> values = query.list();
        return (List<PlannedMarkerSyncWithCaDSRDTO>) convertFromDomainToDTOs(values);
    }

    /**
     * Gets the List of all PlannedMarkerSyncWithCaDSRDTO values.
     * 
     * @return the list of all PlannedMarkerSyncWithCaDSRDTO values
     * @throws PAException
     *             on error.
     * @param name
     *            name
     */
    public List<PlannedMarkerSyncWithCaDSRDTO> getValuesByName(String name)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        String hql = "from PlannedMarkerSyncWithCaDSR as pm where pm.name=:name";
        Query query = session.createQuery(hql);
        query.setParameter(NAME, name);
        List<PlannedMarkerSyncWithCaDSR> values = query.list();
        return (List<PlannedMarkerSyncWithCaDSRDTO>) convertFromDomainToDTOs(values);
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
    public List<Number> getIdentifierByCadsrId(Long id) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        SQLQuery query = session
                .createSQLQuery("select identifier from planned_marker_sync_cadsr where cadsrid=:id");
        query.setParameter("id", id);
        return (List<Number>) query.list();
    }

    /**
     * Gets the List of all name values.
     * 
     * @return the list of all name values
     * @throws PAException
     *             on error.
     * @param name
     *            name
     */
    public List<Number> getPendingIdentifierByCadsrName(String name)
            throws PAException {
        List<Number> values = getIdentifierByName(name);
        if (values.isEmpty()) {
           insertValues(null, name, name, null, null, name,
                    ActiveInactivePendingCode.PENDING.getName());
            Long maxValue = maxIdentifierValue();
            values.add(maxValue.intValue());
        }
        return values;
    }
    /**
     * 
     * @param name name
     * @return List the List
     * @throws PAException PAException
     */
    protected List<Number> getIdentifierByName(String name) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        SQLQuery query = session
                .createSQLQuery("select identifier from planned_marker_sync_cadsr where name=:name");
        query.setParameter(NAME, name);
        return (List<Number>) query.list();
    }
    /**
     * 
     * @param name name
     * @return List the List
     * @throws PAException PAException
     */
    protected List<Number> getIdentifierByPvName(String name) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        SQLQuery query = session
                .createSQLQuery("select identifier from planned_marker_sync_cadsr where pv_name=:name");
        query.setParameter(NAME, name);
        return (List<Number>) query.list();
    }
    /**
     * Updated the values
     * 
     * @param name
     *            name
     * @param id
     *            id
     * 
     */
    public void updateValueById(String name, Long id) {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("update planned_marker_sync_cadsr set meaning =:name,"
                        + " name =:name where identifier =:id");
        query.setParameter(NAME, name);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    /**
     * @return plannedMarkerService
     * 
     */
    public PlannedMarkerServiceLocal getPlannedMarkerService() {
        return plannedMarkerService;
    }

    /**
     * @param plannedMarkerService
     *            plannedMarkerService
     * 
     */
    public void setPlannedMarkerService(
            PlannedMarkerServiceLocal plannedMarkerService) {
        this.plannedMarkerService = plannedMarkerService;
    }
    
    /**
     * 
     * @param pmSynonymService pmSynonymService
     */
    public void setPmSynonymService(
            PlannedMarkerSynonymsServiceLocal pmSynonymService) {
        this.pmSynonymService = pmSynonymService;
    }

}

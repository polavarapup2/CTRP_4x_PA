package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.IdentifierType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ctgov.ClinicalStudy;
import gov.nih.nci.pa.service.search.CTGovImportLogSearchCriteria;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author ADas
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@SuppressWarnings({ "unchecked", "PMD.TooManyMethods",
    "PMD.ExcessiveClassLength", "PMD.CyclomaticComplexity" })
public class CTGovSyncNightlyServiceBeanLocal implements
        CTGovSyncNightlyServiceLocal {

    private static final Logger LOG = Logger.getLogger(CTGovSyncNightlyServiceBeanLocal.class);
    
    @EJB
    private ProtocolQueryServiceLocal queryServiceLocal;
    
    @EJB
    private CTGovSyncServiceLocal ctGovSyncServiceLocal;
    
    @EJB
    private LookUpTableServiceRemote lookUpTableService;
    
    @EJB 
    private MailManagerServiceLocal mailManagerService;        
    
    @Override
    public void updateIndustrialAndConsortiaTrials() throws PAException {     
        if (isSyncEnabled()) {
            UsernameHolder.setUserCaseSensitive(CTGovSyncServiceBean.CTGOVIMPORT_USERNAME);
            try {
                findAndUpdateTrials();
            } finally {
                UsernameHolder.setUser(null);
            }
        }
    }

    /**
     * @throws PAException
     */
    private void findAndUpdateTrials() throws PAException {
        //Record the start time of the nighlty job
        Date startDate = new Date();
        //Query all industrial and consortia trials with NCT identifiers in CTRP.
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setIdentifierType(IdentifierType.NCT.getCode());
        criteria.setNctNumber("NCT");
        criteria.setTrialCategory("p");
        criteria.setExcludeRejectProtocol(true);                        
        List<StudyProtocolQueryDTO> trials = queryServiceLocal.getStudyProtocolByCriteria(criteria);            
        LOG.info("Number of Industrial and Consortia records : " + trials.size());            
        if (trials != null && !trials.isEmpty()) {            
            //Loop over all the trials
            for (StudyProtocolQueryDTO trial : trials) {
                String nctIdentifier = trial.getNctIdentifier();
                LOG.info("NCT Number : " + nctIdentifier);
                try {
                    if (!StringUtils.isEmpty(nctIdentifier)) {                    
                        //Retrieve the trial xml from CT.Gov and populate the clinical study object.
                        ClinicalStudy study = ctGovSyncServiceLocal.getCtGovStudyByNctId(nctIdentifier);
                        if (study != null) {
                            //Check if there is a corresponding CT.Gov import log entry for the NCT identifier.
                            List<CTGovImportLog> associatedImportLogs = getLogEntries(nctIdentifier);
                            if (associatedImportLogs != null && !associatedImportLogs.isEmpty()) {
                                if (study.getLastchangedDate() != null) {
                                    try {
                                        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                                        //last updated date in CTGov
                                        Date lastUpdatedDateInCTGov = dateFormat.parse(
                                                study.getLastchangedDate().getContent());
                                        CTGovImportLog latestImportLog = associatedImportLogs.get(0);
                                        //last updated date in CTRP
                                        Date lastUpdatedDateInCTRP = latestImportLog.getDateCreated();
                                        //if last update date in CTGov is more recent than last update in 
                                        //CTRP then import the trial from CT.Gov and update the same in CTRP. 
                                        //Else update should be skipped.
                                        LOG.info("Comparing " + study.getLastchangedDate().getContent() 
                                                + " and " + lastUpdatedDateInCTRP.toString());
                                        if (lastUpdatedDateInCTGov.compareTo(lastUpdatedDateInCTRP) > 0) {
                                            LOG.info("Last update date in CT.Gov is recent than the last update " 
                                        + "date in CTRP. Performing the update for : " + trial.getNctIdentifier());
                                            updateTrial(nctIdentifier);                                    
                                        } else {
                                            LOG.info("Last update date in CTRP is recent than the last update " 
                                        + "date in CT.Gov. Skipping the update for : " + trial.getNctIdentifier());
                                        }
                                    } catch (ParseException pae) {
                                        LOG.error("Skipping the update for : " + trial.getNctIdentifier() 
                                                + " as " + pae.getMessage());
                                    }
                                } else {
                                    //update the trial as the last changed date is not populated in CT.Gov trial.
                                    LOG.info("Performing the update for : " + trial.getNctIdentifier());
                                    //import the trial from CT.Gov and update the same in CTRP.
                                    updateTrial(nctIdentifier);
                                }                                    
                            } else {
                                LOG.info("No Import Log Entries Found. Performing the update for : " 
                            + trial.getNctIdentifier());
                                //import the trial from CT.Gov and update the same in CTRP.
                                updateTrial(nctIdentifier);
                            }
                        }
                    }
                } catch (PAException pae) {
                    LOG.error("Update for : " + nctIdentifier + " failed. " + pae.getMessage());
                }
            }             
            //Record the finish time of nightly job
            Date endDate = new Date();
            //Get the log entries which got created between start date and end date
            CTGovImportLogSearchCriteria searchCriteria = new CTGovImportLogSearchCriteria();
            searchCriteria.setOnOrAfter(startDate);
            searchCriteria.setOnOrBefore(endDate);
            List<CTGovImportLog> logEntries = ctGovSyncServiceLocal.getLogEntries(searchCriteria);
            if (logEntries != null && !logEntries.isEmpty()) {
                //Send a status e-mail with a summary of trials in CTRP updated from CTGov to 
                //authorized users
                mailManagerService.sendCTGovSyncStatusSummaryMail(logEntries);                    
            }
        }
    }

    /**
     * Gets the CT.Gov import log entries with matching NCT identifier.
     * @param nctIdentifier NCT identifier to match.
     * @return list of CT.Gov import log entries with matching NCT identifer.
     * @throws PAException PAException
     */
    private List<CTGovImportLog> getLogEntries(String nctIdentifier)
            throws PAException {
        String hqlQuery = "from CTGovImportLog log where log.nctID = :nctID and " 
            + "log.importStatus = :importStatus order by log.dateCreated DESC";
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery(hqlQuery);
        query.setParameter("nctID", nctIdentifier);
        query.setParameter("importStatus", "Success");
        return query.list();
    }
    
    /**
     * @param queryServiceLocal ProtocolQueryServiceLocal
     */
    public void setQueryServiceLocal(ProtocolQueryServiceLocal queryServiceLocal) {
        this.queryServiceLocal = queryServiceLocal;
    }       
    
    /**
     * @param ctGovSyncServiceLocal CTGovSyncServiceLocal
     */
    public void setCtGovSyncServiceLocal(CTGovSyncServiceLocal ctGovSyncServiceLocal) {
        this.ctGovSyncServiceLocal = ctGovSyncServiceLocal;
    }

    /**
     * @param lookUpTableService LookUpTableServiceRemote
     */
    public void setLookUpTableService(LookUpTableServiceRemote lookUpTableService) {
        this.lookUpTableService = lookUpTableService;
    }
    
    /**
     * @param mailManagerService the mailManagerService to set
     */
    public void setMailManagerService(MailManagerServiceLocal mailManagerService) {
        this.mailManagerService = mailManagerService;
    }
    
    /**
     * Imports the trial from CT.Gov and updates the same in CTRP. 
     * @param nctIdentifier NCT identifier.
     * @throws PAException PAException
     */
    private void updateTrial(String nctIdentifier) throws PAException {
        try {
            //update the trial in CTRP
            PaHibernateUtil.getCurrentSession().flush();
            PaHibernateUtil.getCurrentSession().clear();
            ctGovSyncServiceLocal.importTrial(nctIdentifier);            
        } catch (PAException pae) {
            LOG.error("Update for : " + nctIdentifier + " failed. " + pae.getMessage());
        }
    }
    
    /**
     * @return returns the value of "ctgov.sync.enabled" property.
     * @throws PAException PAException
     */
    private boolean isSyncEnabled() throws PAException {
        return Boolean.valueOf(lookUpTableService
                .getPropertyValue("ctgov.sync.enabled"));
    }
}

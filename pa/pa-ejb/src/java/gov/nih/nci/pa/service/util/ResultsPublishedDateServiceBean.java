package gov.nih.nci.pa.service.util;

import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_LAST_UPDATER_INFO;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_OTHER_IDENTIFIERS;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_PROGRAM_CODES;
import freemarker.template.TemplateException;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ctgov.ClinicalStudy;
import gov.nih.nci.pa.service.ctgov.DateStruct;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.util.CollectionUtils;

/**
 * @author Apratim K
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors({ RemoteAuthorizationInterceptor.class,
        PaHibernateSessionInterceptor.class })
public class ResultsPublishedDateServiceBean implements
        ResultsPublishedDateService {
    
    @EJB
    private ProtocolQueryServiceLocal protocolQueryService;
    
    @EJB
    private CTGovSyncServiceLocal ctGovSyncService;
    
    @EJB
    private MailManagerServiceLocal mailManagerSerivceLocal;
    
    private List<String> updatedTrialsList;
    
    /** The LOG details. */
    private static final Logger LOG = Logger.getLogger(ResultsPublishedDateServiceBean.class);
    static {
        LOG.setLevel(Level.INFO);
    }

    @Override
    public void updatePublishedDate() throws PAException, IOException, TemplateException {
       
        LOG.info("Starting update published date job");
        List<StudyProtocolQueryDTO> trialsList =  getTrialsToUpdate(); 
        updatedTrialsList = new ArrayList<String>();
        List<Long> trialIdList = new ArrayList<Long>();
        LOG.info("Found " + trialsList.size() + " trials matching criteria to update");
        
        for (StudyProtocolQueryDTO studyProtocolQueryDTO :trialsList) {
            LOG.info("Fetching firstreceivedResultsDate from CTGOV for  " + studyProtocolQueryDTO.getNciIdentifier());
            if (isResultsDatePresentInCtGov(studyProtocolQueryDTO.getNctIdentifier())) {
                trialIdList.add(studyProtocolQueryDTO.getStudyProtocolId());
                updatedTrialsList.add(studyProtocolQueryDTO.getNciIdentifier());
            }
        }
        //update trial publish date
        if (trialIdList.size() > 0) {
            LOG.info("Updating following trials published date to today " + updatedTrialsList);
            updateTrialPublishedDate(trialIdList);
            //send trial publish update email
            LOG.info("Sending trials update email");
            mailManagerSerivceLocal.sendTrialPublishDateUpdateEmail(updatedTrialsList);
        } else {
           //send no trials update email
            LOG.info("Sending no trials update email");
            mailManagerSerivceLocal.sendTrialPublishDateNoUpdateEmail();
        }
        
       LOG.info("Finished update published date job");
    }
    
    /**
     * Fetch trials where prs release date is not null
     * and trial results published date is null   
     * @return List 
     * @throws PAException exception
     */
    private List<StudyProtocolQueryDTO> getTrialsToUpdate() throws PAException {
        LOG.info("reading number of trials to update published date");
        List<StudyProtocolQueryDTO> resultsList = new ArrayList<StudyProtocolQueryDTO>();
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setNciSponsored(true);
        criteria.setStudyProtocolType(InterventionalStudyProtocol.class
                .getSimpleName()); 
        
        List<StudyProtocolQueryDTO> currentResults = 
                PAUtil.applyAdditionalFilters(protocolQueryService
                .getStudyProtocolByCriteria(
                        criteria,
                        SKIP_ALTERNATE_TITLES,
                        SKIP_LAST_UPDATER_INFO,
                        SKIP_OTHER_IDENTIFIERS,
                        SKIP_PROGRAM_CODES));
        
      //find out trials for which prs release date is not null and trial results published
      //date is null  
       if (!CollectionUtils.isEmpty(currentResults)) { 
        for (StudyProtocolQueryDTO studyProtocolQueryDTO :currentResults) {
            if (studyProtocolQueryDTO.getPrsReleaseDate() != null
             && studyProtocolQueryDTO.getTrialPublishedDate() == null) {
                resultsList.add(studyProtocolQueryDTO);
            }
          
        }
       } 
       
       return resultsList;
        
    }
    
    /**
     * Retrieves firstreceived_results_date from ctgov xml for trial
     * @param nctId nciId
     * @return boolean if date is present in ctgov xml
     * @throws PAException exception 
     */
    private boolean isResultsDatePresentInCtGov(String nctId) throws PAException {
       boolean isDatePresent = false; 
       
       DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
       if (nctId != null) {
       ClinicalStudy clinicalStudy = ctGovSyncService.getCtGovStudyByNctId(nctId);
       if (clinicalStudy != null) {
           DateStruct dateStruct = clinicalStudy.getFirstreceivedResultsDate();
           if (dateStruct != null) {
               //last updated date in CTGov
               try {
                dateFormat.parse(
                           dateStruct.getContent());
            } catch (ParseException e) {
                //any exception means invalid date
                LOG.warn("Invalid date format for trial " + nctId + " and date value is " + dateStruct.getContent());
                return false;
            }
            
               isDatePresent = true;
           }
       }
       } 
       LOG.info("Is firstreceivedResultsDate valid for trial " + nctId + " " + isDatePresent);
        
       return isDatePresent; 
    }
    
    /**
     * Update trial id to todays date
     * @param trialIds trialIds
     * @throws PAException exception 
     */
    private void updateTrialPublishedDate(List<Long>trialIds) throws PAException {
        try {
            Date today = new Date();
            Session session = PaHibernateUtil.getCurrentSession();
            String hql = "update StudyProtocol set trialPublishedDate=:today where id in (:ids)";
            Query query = session.createQuery(hql);
            query.setParameter("today", today);
            query.setParameterList("ids", trialIds);
            query.executeUpdate();
             
        } catch (Exception e) {
            LOG.info("Exception in updateTrialPublishedDate" + e.getMessage());
            throw new PAException(e.getMessage());
        }
        
        
        
    }

    /**
     * @return protocolQueryService
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }

    /** 
     * @param protocolQueryService protocolQueryService
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @return protocolQueryService
     */
    public CTGovSyncServiceLocal getCtGovSyncService() {
        return ctGovSyncService;
    }

    /**
     * @param ctGovSyncService ctGovSyncService
     */
    public void setCtGovSyncService(CTGovSyncServiceLocal ctGovSyncService) {
        this.ctGovSyncService = ctGovSyncService;
    }

    /**
     * @return mailManagerSerivceLocal
     */
    public MailManagerServiceLocal getMailManagerSerivceLocal() {
        return mailManagerSerivceLocal;
    }

    /**
     * @param mailManagerSerivceLocal MailManagerServiceLocal
     */
    public void setMailManagerSerivceLocal(
            MailManagerServiceLocal mailManagerSerivceLocal) {
        this.mailManagerSerivceLocal = mailManagerSerivceLocal;
    }

    /**
     * @return updatedTrialsList
     */
    public List<String> getUpdatedTrialsList() {
        return updatedTrialsList;
    }

    /**
     * @param updatedTrialsList updatedTrialsList
     */
    public void setUpdatedTrialsList(List<String> updatedTrialsList) {
        this.updatedTrialsList = updatedTrialsList;
    }
    
    

}

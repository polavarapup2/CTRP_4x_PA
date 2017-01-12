package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * @author Denis G. Krylov
 * 
 */

@Stateless
@Interceptors({ RemoteAuthorizationInterceptor.class,
        PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@SuppressWarnings({ "PMD.CyclomaticComplexity" })
public class CTGovUploadServiceBeanLocal implements CTGovUploadServiceLocal {

    private static final int DAYS_TO_KEEP_HISTORY = -30;

    private static final String PRS_UPLOAD_FILENAME = "clinical.txt";

    private static final Logger LOG = Logger
            .getLogger(CTGovUploadServiceBeanLocal.class);

    private static final int TIMEOUT = 120 * 1000;
    static {
        LOG.setLevel(Level.INFO);
    }

    @EJB
    private CTGovXmlGeneratorServiceLocal generatorServiceLocal;

    @EJB
    private ProtocolQueryServiceLocal queryServiceLocal;

    @EJB
    private LookUpTableServiceRemote lookUpTableService;

    @EJB
    private CTGovSyncServiceLocal ctGovSyncService;

    private List<String> getAbstractedStatusCodes() {
        List<String> list = new ArrayList<String>();
        for (DocumentWorkflowStatusCode code : DocumentWorkflowStatusCode
                .values()) {
            if (code.isAbstractedOrAbove()) {
                list.add(code.getCode());
            }
        }
        return list;
    }

    /**
     * @return List<Ii>
     * @throws PAException
     *             EXCEPTION
     */
    public List<Ii> getTrialIdsForUpload() throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        final List<Ii> ids = new ArrayList<Ii>();
        try {

            criteria.setNciSponsored(true);
            criteria.setDocumentWorkflowStatusCodes(getAbstractedStatusCodes());
            criteria.setTrialCategory("n");
            criteria.setCtroOverride(false);
            criteria.setStudyProtocolType("InterventionalStudyProtocol");

            List<String> ccrLeadIDs = getCCRTrialLeadOrgIds();
            boolean isExcludeLeadCCR = false;

            for (StudyProtocolQueryDTO dto : queryServiceLocal
                    .getStudyProtocolByCriteria(criteria)) {
                isExcludeLeadCCR = false;
                String nctIdentifier = dto.getNctIdentifier();
                String trialStatus = dto.getStudyStatusCode().getCode();

                StudyProtocolQueryDTO studyProtocolQueryDTOSummary = queryServiceLocal
                        .getTrialSummaryByStudyProtocolId(dto
                                .getStudyProtocolId());

                String orgCtepID = null;

                PaOrganizationDTO paOrganizationDTO = PADomainUtils
                        .getOrgDetailsPopup(studyProtocolQueryDTOSummary
                                .getLeadOrganizationPOId() + "");

                if (paOrganizationDTO != null
                        && paOrganizationDTO.getCtepId() != null) {
                    orgCtepID = paOrganizationDTO.getCtepId();
                }
                // check if lead or is one of ccr if yes then exclude this trial
                // and update flag
                List<String> exludeOrgIds = getExcludeLeadOrgIds();

                if (orgCtepID != null && exludeOrgIds.contains(orgCtepID)) {
                    isExcludeLeadCCR = true;
                    updateCtroOverride(dto.getStudyProtocolId());
                }

                if (!ccrLeadIDs.contains(dto.getLocalStudyProtocolIdentifier())
                        && !isExcludeLeadCCR) {
                    boolean isExcludeTrial = checkIfTrialExcludeAndUpdateCtroOverride(
                            dto.getStudyProtocolId(), trialStatus,
                            nctIdentifier);

                    if (!isExcludeTrial && !isNonApplicablePcd(dto)) {
                        ids.add(IiConverter.convertToStudyProtocolIi(dto
                                .getStudyProtocolId()));

                    }
                }
            }

        } catch (Exception e) {
            throw new PAException(e);
        }
        return ids;
    }

    private boolean isNonApplicablePcd(StudyProtocolQueryDTO dto) {
        Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocol studyProtocol = (StudyProtocol) session.get(
                StudyProtocol.class, dto.getStudyProtocolId());
        return ActualAnticipatedTypeCode.NA == studyProtocol.getDates()
                .getPrimaryCompletionDateTypeCode();
    }

    private List<String> getCCRTrialLeadOrgIds() throws PAException {
        String ccrTrialList = lookUpTableService
                .getPropertyValue("ctep.ccr.trials");
        return Arrays.asList(ccrTrialList.replaceAll("\\s+", "").split(","));
    }

    private List<String> getExcludeLeadOrgIds() throws PAException {
        String ccrTrialList = lookUpTableService
                .getPropertyValue("ctep.ccr.learOrgIds");
        return Arrays.asList(ccrTrialList.replaceAll("\\s+", "").split(","));
    }

    private String hidePassword(URL url) {
        return url.toString().replaceAll(":[^\\:]+@", ":*****@");
    }

    /**
     * @param generatorServiceLocal
     *            the generatorServiceLocal to set
     */
    public void setGeneratorServiceLocal(
            CTGovXmlGeneratorServiceLocal generatorServiceLocal) {
        this.generatorServiceLocal = generatorServiceLocal;
    }

    /**
     * @param lookUpTableService
     *            the lookUpTableService to set
     */
    public void setLookUpTableService(
            LookUpTableServiceRemote lookUpTableService) {
        this.lookUpTableService = lookUpTableService;
    }

    /**
     * @param queryServiceLocal
     *            ProtocolQueryServiceLocal
     */
    public void setQueryServiceLocal(ProtocolQueryServiceLocal queryServiceLocal) {
        this.queryServiceLocal = queryServiceLocal;
    }

    private void uploadFileToCTGovAndUpdateHistory(String xml, URL ftpURL) {
        OutputStream os = null;
        InputStream fileIs = null;
        try {
            URL completeUploadURL = new URL(ftpURL, PRS_UPLOAD_FILENAME);
            URLConnection connection = completeUploadURL.openConnection();
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.setDoOutput(true);

            os = connection.getOutputStream();
            fileIs = IOUtils.toInputStream(xml, "UTF-8");
            IOUtils.copy(fileIs, os);
            os.flush();
            LOG.info(PRS_UPLOAD_FILENAME + " uploaded to "
                    + hidePassword(completeUploadURL));
            updateHistory(xml);
        } catch (Exception e) {
            LOG.error("Upload of " + PRS_UPLOAD_FILENAME + " failed due to "
                    + e.getMessage());
        } finally {
            IOUtils.closeQuietly(fileIs);
            IOUtils.closeQuietly(os);
        }

    }

    private void updateHistory(String xml) {
        SQLQuery query = PaHibernateUtil
                .getCurrentSession()
                .createSQLQuery(
                        "insert into prs_sync_history(sync_date, data) values(current_timestamp,:data)");
        query.setText("data", xml);
        query.executeUpdate();

        SQLQuery deleteQuery = PaHibernateUtil.getCurrentSession()
                .createSQLQuery(
                        "delete from prs_sync_history where sync_date<:date");
        deleteQuery.setTimestamp("date",
                DateUtils.addDays(new Date(), DAYS_TO_KEEP_HISTORY));
        deleteQuery.executeUpdate();

    }

    @Override
    public void uploadToCTGov() throws PAException {
        if (isUploadEnabled()) {
            LOG.info("Nightly CT.Gov FTP Upload kicked off.");
            try {
                final URL ftpURL = new URL(PaEarPropertyReader.getCTGovFtpURL());
                List<Ii> trialIDs = getTrialIdsForUpload();
                LOG.info("Got " + trialIDs.size() + " trials to upload.");
                LOG.info("CT.Gov FTP is " + hidePassword(ftpURL));
                if (CollectionUtils.isNotEmpty(trialIDs)) {
                    uploadToCTGov(trialIDs, ftpURL);
                }
                LOG.info("Done.");
            } catch (Exception e) {
                LOG.error("CT.Gov FTP Upload has failed due to " + e);
                LOG.error(e, e);
            }
        }
    }

    @Override
    public boolean checkIfTrialExcludeAndUpdateCtroOverride(Long id,
            String trialStatus, String ncitIdentifier) throws PAException {
        boolean result = false;

        // first check if trail is not from excluded study ccr organisation

        // if trial is sent to ct gov check trial status if terminal status
        if (checkIfTerminalStats(trialStatus)) {
            CTGovStudyAdapter ctGovStudyAdapter = ctGovSyncService
                    .getAdaptedCtGovStudyByNctId(ncitIdentifier);
            // if trial status is terminal status check if this matches with
            // clinical trials gov
            if (ctGovStudyAdapter != null) {
                String ctGovStatus = ctGovStudyAdapter.getStatus();
                if (checkIfStatusMatch(trialStatus, ctGovStatus)) {
                    // if status match then set ctro override flag to false so
                    // that next time trial will be skipped
                    updateCtroOverride(id);
                    result = true;
                }
            }

        } else {
            result = false;
        }

        return result;
    }

    private boolean checkIfStatusMatch(String trialStatus, String ctGovStatus) {
        boolean isStatusMatch = false;
        if (trialStatus.equalsIgnoreCase(StudyStatusCode.COMPLETE.getCode())) {
            if (ctGovStatus.equalsIgnoreCase("Completed")) {
                isStatusMatch = true;
            }
        } else if (trialStatus
                .equalsIgnoreCase(StudyStatusCode.ADMINISTRATIVELY_COMPLETE
                        .getCode())) {
            if (ctGovStatus.equalsIgnoreCase("Terminated")) {
                isStatusMatch = true;
            }
        } else if (trialStatus.equalsIgnoreCase(StudyStatusCode.WITHDRAWN
                .getCode())) {
            if (ctGovStatus.equalsIgnoreCase("Withdrawn")
                    || ctGovStatus.equalsIgnoreCase("Withheld")) {
                isStatusMatch = true;
            }
        }
        return isStatusMatch;
    }

    private void updateCtroOverride(Long id) {
        Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocol studyProtocol = (StudyProtocol) session.get(
                StudyProtocol.class, id);
        studyProtocol.setCtroOverride(true);
        session.update(studyProtocol);
        session.flush();
    }

    private boolean checkIfTerminalStats(String trialStatus) {
        boolean result = false;
        if (trialStatus.equalsIgnoreCase(StudyStatusCode.COMPLETE.getCode())
                || trialStatus
                        .equalsIgnoreCase(StudyStatusCode.ADMINISTRATIVELY_COMPLETE
                                .getCode())
                || trialStatus.equalsIgnoreCase(StudyStatusCode.WITHDRAWN
                        .getCode())) {
            result = true;
        }
        return result;
    }

    private boolean isUploadEnabled() throws PAException {
        return Boolean.valueOf(lookUpTableService
                .getPropertyValue("ctgov.ftp.enabled"));
    }

    private void uploadToCTGov(List<Ii> trialIDs, URL ftpURL)
            throws PAException {
        try {
            String xml = generatorServiceLocal.generateCTGovXml(trialIDs,
                    CTGovXmlGeneratorOptions.USE_SUBMITTERS_PRS);
            uploadFileToCTGovAndUpdateHistory(xml, ftpURL);
        } catch (PAException e) {
            LOG.error(ExceptionUtils.getFullStackTrace(e));
        }
    }

    /**
     * @return ctGovSyncService
     */
    public CTGovSyncServiceLocal getCtGovSyncService() {
        return ctGovSyncService;
    }

    /**
     * @param ctGovSyncService
     *            ctGovSyncService
     */
    public void setCtGovSyncService(CTGovSyncServiceLocal ctGovSyncService) {
        this.ctGovSyncService = ctGovSyncService;
    }

}

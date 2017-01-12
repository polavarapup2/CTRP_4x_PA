package gov.nih.nci.pa.service.util;

import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_LAST_UPDATER_INFO;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_OTHER_IDENTIFIERS;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_PROGRAM_CODES;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.Tweet;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyFlagReasonCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;
import gov.nih.nci.pa.enums.TweetStatusCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

/**
 * @author Denis G. Krylov
 * 
 */
@Stateless
@Interceptors({ RemoteAuthorizationInterceptor.class,
        PaHibernateSessionInterceptor.class })
@SuppressWarnings({ "PMD.CyclomaticComplexity" })
public class TrialTweetingBean implements TrialTweetingService {

    private static final int TWEET_SIZE = 140;

    private static final int OK = 200;

    private static final int TIMEOUT = 30000;

    private static final Logger LOG = Logger.getLogger("Twitter");

    @EJB
    private LookUpTableServiceRemote lookUpTableService;

    @EJB
    private ProtocolQueryServiceLocal protocolQueryService;

    @EJB
    private URLShortenerServiceLocal urlShortenerService;

    private volatile Boolean mostRecentEnabledCheck;

    @Override
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void processTrials() throws PAException, IOException {
        LOG.info("mostRecentEnabledCheck=" + mostRecentEnabledCheck);
        final boolean wasDisabledOnPreviousCheck = Boolean.FALSE
                .equals(mostRecentEnabledCheck);
        if (!isTweetingEnabled()) {
            return;
        }
        LOG.info("Tweeting enabled; searching for new trials to be tweeted about...");
        final List<StudyProtocolQueryDTO> trials = findTrialsToTweet();
        LOG.info(trials.size()
                + " trial(s) can potentially be tweeted, but this number is before we check Cancer.gov");
        if (isFirstTimeRun()) {
            LOG.info("Tweeting job appears to be running for the first time. "
                    + "In this case we do not tweet about the trials we found; "
                    + "instead we mark them for future exclusion.");
            markWithCanceledTweets(trials);
        } else if (wasDisabledOnPreviousCheck) {
            LOG.info("Tweeting job appears to be running after having been disabled for some time. "
                    + "In this case we do not tweet about the trials we found; "
                    + "instead we mark them for future exclusion.");
            markWithCanceledTweets(trials);
        } else {
            tweet(trials);
        }

    }

    private void tweet(List<StudyProtocolQueryDTO> trials) throws PAException,
            MalformedURLException {
        for (StudyProtocolQueryDTO trial : trials) {
            tweet(trial);
        }

    }

    private void tweet(StudyProtocolQueryDTO trial) throws PAException,
            MalformedURLException {
        if (isInCancerGov(trial.getNctIdentifier())) {
            buildAndQueueTweet(trial);
        }
    }

    private void buildAndQueueTweet(StudyProtocolQueryDTO trial)
            throws PAException, MalformedURLException {
        String nctID = trial.getNctIdentifier();
        String text = lookUpTableService.getPropertyValue(
                "twitter.trials."
                        + (trial.isProprietaryTrial() ? "industrial"
                                : "complete") + ".basetext").trim();
        URL refURL = new URL(getReferenceURL(nctID));
        URL shortened = urlShortenerService.shorten(refURL);
        if (shortened == null) {
            LOG.warn("We could not shorten URL for this trial. Will try tweeting again later.");
            return;
        }
        text = text.replace("{url}", shortened.toString());

        // Now determine hash tags.
        final Session s = PaHibernateUtil.getCurrentSession();
        final StudyProtocol sp = (StudyProtocol) s.get(StudyProtocol.class,
                trial.getStudyProtocolId());
        final Collection<String> tagsUsed = new HashSet<>();
        final StringBuilder sb = new StringBuilder();
        boolean proceed = true;
        while (proceed) {
            proceed = false;
            for (AnatomicSite site : sp.getSummary4AnatomicSites()) {
                if (StringUtils.isNotBlank(site.getTwitterHashtags())) {
                    String[] tags = site.getTwitterHashtags()
                            .replaceAll("\\s+", "").split(",");
                    for (String tag : tags) {
                        if (!tagsUsed.contains(tag)) {
                            tagsUsed.add(tag);
                            String hashtagToAppend = (sb.length() == 0 ? ""
                                    : " ") + "#" + tag;
                            int newSbLen = sb.length()
                                    + hashtagToAppend.length();
                            if (text.replace("{hashtags}",
                                    StringUtils.repeat(" ", newSbLen)).length() <= TWEET_SIZE) {
                                sb.append(hashtagToAppend);
                                proceed = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (sb.length() == 0
                && text.replace("{hashtags}", "#cancer").length() <= TWEET_SIZE) {
            sb.append("#cancer");
        }
        text = text.replace("{hashtags}", sb.toString());

        // Tweet ready. Schedule for delivery.
        LOG.info("Queueing tweet: " + text);
        Tweet t = new Tweet();
        t.setText(StringUtils.left(text, TWEET_SIZE));
        t.setStatus(TweetStatusCode.PENDING);
        t.setStudyProtocol(sp);
        t.setCreateDate(new Date());
        t.setAccountName(lookUpTableService.getPropertyValue("twitter.account"));
        s.save(t);
        s.flush();
    }

    @SuppressWarnings("deprecation")
    private boolean isInCancerGov(String nctID) throws PAException {
        String url = getReferenceURL(nctID);
        LOG.info("Hitting " + url + " to check if the trial is in Cancer.gov");

        try {
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
            final HttpClient httpClient = new DefaultHttpClient(httpParams);
            HttpGet req = new HttpGet(url);
            req.addHeader("Accept", "text/html");
            HttpResponse response = httpClient.execute(req);
            final int statusCode = response.getStatusLine().getStatusCode();
            LOG.info("Status code: " + statusCode);
            return statusCode == OK;
        } catch (IOException e) {
            LOG.error(e, e);
            return false;
        }

    }

    /**
     * @param nctID
     * @return
     * @throws PAException
     */
    private String getReferenceURL(String nctID) throws PAException {
        return lookUpTableService.getPropertyValue("twitter.trials.url")
                .replace("{nctid}", StringUtils.trim(nctID));
    }

    private void markWithCanceledTweets(final List<StudyProtocolQueryDTO> trials) {
        if (CollectionUtils.isEmpty(trials)) {
            return;
        }
        Session s = PaHibernateUtil.getCurrentSession();
        for (StudyProtocolQueryDTO trial : trials) {
            StudyProtocol sp = new StudyProtocol();
            sp.setId(trial.getStudyProtocolId());
            Tweet t = new Tweet();
            t.setText(StringUtils.EMPTY);
            t.setStatus(TweetStatusCode.CANCELED);
            t.setStudyProtocol(sp);
            t.setCreateDate(new Date());
            s.save(t);
        }
        s.flush();

    }

    private boolean isFirstTimeRun() {
        return ((Number) PaHibernateUtil.getCurrentSession()
                .createCriteria(Tweet.class)
                .setProjection(Projections.rowCount()).uniqueResult())
                .intValue() == 0;
    }

    private List<StudyProtocolQueryDTO> findTrialsToTweet() throws PAException {
        List<StudyProtocolQueryDTO> trials = new ArrayList<>();
        trials.addAll(findCompleteTrialsToTweet());
        trials.addAll(findIndustrialTrialsToTweet());
        return trials;
    }

    private Collection<StudyProtocolQueryDTO> findCompleteTrialsToTweet()
            throws PAException {
        StudyProtocolQueryCriteria criteria = getBaseSearchCriteria();
        criteria.setTrialSubmissionTypes(Arrays.asList(SubmissionTypeCode.O));
        criteria.setTrialCategory("n");
        criteria.setStudyStatusCodeList(Arrays.asList(
                StudyStatusCode.ACTIVE.getCode(),
                StudyStatusCode.ENROLLING_BY_INVITATION.getCode()));
        return protocolQueryService.getStudyProtocolByCriteria(criteria,
                SKIP_ALTERNATE_TITLES, SKIP_LAST_UPDATER_INFO,
                SKIP_OTHER_IDENTIFIERS, SKIP_PROGRAM_CODES);
    }

    private Collection<StudyProtocolQueryDTO> findIndustrialTrialsToTweet()
            throws PAException {
        StudyProtocolQueryCriteria criteria = getBaseSearchCriteria();
        criteria.setTrialCategory("p");
        criteria.setSiteStatusCodes(Arrays.asList(RecruitmentStatusCode.ACTIVE,
                RecruitmentStatusCode.ENROLLING_BY_INVITATION));
        return protocolQueryService.getStudyProtocolByCriteria(criteria,
                SKIP_ALTERNATE_TITLES, SKIP_LAST_UPDATER_INFO,
                SKIP_OTHER_IDENTIFIERS, SKIP_PROGRAM_CODES);
    }

    /**
     * @return
     */
    private StudyProtocolQueryCriteria getBaseSearchCriteria() {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setExcludeRejectProtocol(true);
        criteria.setExcludeTerminatedTrials(true);
        criteria.setDocumentWorkflowStatusCodes(Arrays.asList(
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE
                        .getCode(),
                DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE
                        .getCode()));
        criteria.setHasTweets(false);
        criteria.setIdentifierType("NCT");
        criteria.setIdentifier("NCT");
        criteria.setNotFlaggedWith(StudyFlagReasonCode.DO_NOT_SUBMIT_TWEETS);
        return criteria;
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
     * @return
     * @throws PAException
     */
    // CHECKSTYLE:OFF
    private boolean isTweetingEnabled() throws PAException {
        return (mostRecentEnabledCheck = Boolean.valueOf(lookUpTableService
                .getPropertyValue("twitter.enabled")));
    }

    /**
     * @param protocolQueryService
     *            the protocolQueryService to set
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @param urlShortenerService
     *            the urlShortenerService to set
     */
    public void setUrlShortenerService(
            URLShortenerServiceLocal urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }
}

/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.service.search;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.search.StudyProtocolOptions.MilestoneFilter;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.functors.NotNullPredicate;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.fiveamsolutions.nci.commons.search.SearchableUtils;

/**
 * @author Abraham J. Evans-EL
 */
@SuppressWarnings({ "PMD.ExcessiveClassLength", "PMD.TooManyMethods" })
public class StudyProtocolQueryBeanSearchCriteria extends AnnotatedBeanSearchCriteria<StudyProtocol> {
    /**
     * 
     */
    private static final long serialVersionUID = -4056363230689185168L;
    private final StudyProtocolOptions spo;
    private final String joinClause;

    /**
     * Default constructor.
     * 
     * @param o
     *            the example
     * @param spo
     *            the study protocol search options
     */
    public StudyProtocolQueryBeanSearchCriteria(StudyProtocol o,
            StudyProtocolOptions spo) {
        super(o);
        this.spo = spo;
        joinClause = figureOutJoinClause();
    }

    private String figureOutJoinClause() {
        final StringBuilder join = new StringBuilder();
        join.append(" left outer join obj.documentWorkflowStatuses as dws  ");
        if (needJoinOnMilestones()) {
            join.append(" left outer join obj.studyMilestones as sms ");
        }
        if (needJoinOnOverallStatuses()) {
            join.append(" left outer join obj.studyOverallStatuses as sos ");
        }
        join.append(" left outer join obj.studyOwners as sowner ");
        if (needJoinStudyProgramCodes()) {
           join.append(" join obj.programCodes as pgc ");
        }

        return join.toString();

    }

    private boolean needJoinStudyProgramCodes() {
        return CollectionUtils.isNotEmpty(spo.getProgramCodeIds());
    }

    private boolean needJoinOnOverallStatuses() {
        return CollectionUtils.isNotEmpty(getCriteria().getStudyOverallStatuses())
               || CollectionUtils.isNotEmpty(spo.getStudyStatusCodes());
    }

    private boolean needJoinOnMilestones() {
        return CollectionUtils.isNotEmpty(getCriteria().getStudyMilestones())
                || CollectionUtils.isNotEmpty(spo.getMilestoneFilters());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasOneCriterionSpecified() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query getQuery(String orderByProperty, boolean isCountOnly) {
        return SearchableUtils.getQueryBySearchableFields(getCriteria(), isCountOnly, orderByProperty, joinClause,
                getSession(), new StudyProtocolHelper(getCriteria(), spo));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query getQuery(String orderByProperty, String leftJoinClause, boolean isCountOnly) {
        return SearchableUtils.getQueryBySearchableFields(getCriteria(), isCountOnly, orderByProperty,
                leftJoinClause + joinClause, getSession(), new StudyProtocolHelper(getCriteria(), spo));
    }

    /**
     * @param attributes attributes
     * @param orderByProperty orderByProperty
     * @param leftJoinClause leftJoinClause
     * @param isCountOnly isCountOnly
     * @return Query
     */
    public Query getQuery(List<String> attributes, String orderByProperty,
            String leftJoinClause, boolean isCountOnly) {
        return SearchableUtils.getQueryBySearchableFields(getCriteria(),
                attributes, isCountOnly, orderByProperty, "", leftJoinClause
                        + joinClause, getSession(), new StudyProtocolHelper(
                        getCriteria(), spo));
    }

    /**
     * Helper that adds synonym checks to the searches.
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyMethods" })
    private static class StudyProtocolHelper implements SearchableUtils.AfterIterationHelper {

        private static final String CONNECTOR = " %s ";
        private static final String CITY_PARAM = "city";
        private static final String STATES_PARAM = "states";
        private static final String COUNTRY_NAME_PARAM = "countryName";
        private static final String LEAD_ORGANIZATION_FUNCTIONAL_CODE_PARAM = "leadOrganizationFunctionalCode";
        private static final String ID_ASSIGNER_FUNCTIONAL_CODE_PARAM = "idAssignerFunctionalCode";
        private static final String PARTICIPATING_SITE_FUNCTIONAL_CODE_PARAM = "participatingSitefunctionalCode";
        private static final String SOS_PARAM = "studyOverallStatusParam";
        private static final String REPORTING_PERIOD_START_PARAM = "reportingPeriodStartParam";
        private static final String REPORTING_PERIOD_END_PARAM = "reportingPeriodEndParam";
        private static final String DWS_PARAM = "documentWorkflowStatusParam";
        private static final String REJECTED_DWS_PARAM = "rejectedDocumentWorkflowStatusParam";
        private static final String TERMINATED_DWS_PARAM = "terminatedDocumentWorkflowStatusParam";
        private static final String SMS_PARAM = "studyMilestoneStatusParam";
        private static final String CHECKOUT_PARAM = "checkedOutUserParam";
        private static final String STUDY_OWNER_PARAM = "studyOwnerParam";
        private static final String STUDY_OWNER_TYPE_PARAM = "studyOwnerTypeParam";
        private static final String STUDY_OWNER_DWS_PARAM = "studyOwnerDWSParam";
        private static final String STUDY_OWNER_SITE_PARAM = "studyOwnerSiteFunctionalCodeParam";
        private static final String STUDY_PHASE_CODE_PARAM = "studyPhaseCodeParam";
        private static final String PRIMARY_PURPOSE_CODE_PARAM = "primaryPurposeCodeParam";
        private static final String ANATOMIC_SITES_PARAM  = "anatomicSitesParam";
        private static final String PARTICIPATING_SITE_PARAM  = "participatingSiteParam";
        private static final String BIOMARKERS_PARAM  = "biomarkersParam";
        private static final String STUDY_ALTERNATE_TITLE_PARAM  = "studyAlternateTitleParam";
        private static final String PDQDISEASES_PARAM  = "pdqdiseaseParam";
        private static final String INTERVENTIONS_PARAM  = "interventions";
        private static final String INTERVENTIONS_ALTERNAMES_PARAM  = "interventionAlternates";
        private static final String INTERVENTIONS_TYPES_PARAM  = "interventionTypes";
        private static final String LEAD_ORG_FUNCTIONAL_CODE_PARAM = "leadOrgFunctionalCode";
        private static final String LEAD_ORG_IDS_PARAM = "leadOrgIds";
        private static final String CTGOV_XML_REQUIRED_INDICATOR = "ctgovXmlRequiredIndicator";
        private static final String SUBMITTED_ON_OR_AFTER_PARAM = "submittedOnOrAfter";
        private static final String SUBMITTED_ON_OR_BEFORE_PARAM = "submittedOnOrBefore";
        private static final String AFFILIATE_ORG_ID_PARAM = "affiliatedOrganizationId";
        private static final String AFFILIATE_ORG_NAME_PARAM = "affiliatedOrganizationName";
        private static final String SPONSOR_FUNCTIONAL_CODE_PARAM = "sponsorFuncCode";
        private static final String CURRENT_OR_PREV_MILESTONE_PARAM = "currentOrPreviousMilestone";
        private static final String ONHOLD_REASONS_PARAM = "onholdReasons";
        private static final String ACTIVE_MILESTONES_PARAM = "activeMilestones";
        private static final String INACTIVE_MILESTONES_PARAM = "inactiveMilestones";
        private static final String PROCESSING_PRIORITY_PARAM = "processingPriorityParam";
        private static final String STUDY_SOURCE_PARAM = "studySourceParam";
        private static final String TREATING_SITE_CODE_PARAM = "treatingSiteCodeParam";
        private static final String NULLIFIED_STATUS_PARAM = "nulifiedStatusParam";
        private static final String ONHOLD_OTHER_CODE_PARAM = "onHoldOtherParam";
        private static final String ONHOLD_OTHER_CATEGORIES_PARAM = "onHoldOtherCategoriesParam";
        private static final String SECTION_801_INDICATOR = "section801Indicator";
        private static final String PCD_FROM_DATE = "pcdFromDate";
        private static final String PCD_TO_DATE = "pcdToDate";
        private static final String PCD_DATE_TYPE = "pcdDateType";
        private static final String FLAG_CODE_PARAM = "flagReason";
        private static final String SITE_STATUSES_PARAM = "siteStatuses";
        private static final String PROGRAM_CODE_ID_PARAM = "pgCodeIds";
        private final StudyProtocol sp;
        private final StudyProtocolOptions spo;

        public StudyProtocolHelper(StudyProtocol sp, StudyProtocolOptions spo) {
            this.sp = sp;
            this.spo = spo;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void afterIteration(Object obj, boolean isCountOnly, StringBuffer whereClause,
                Map<String, Object> params) {
            handleStudyOverallStatuses(whereClause, params);
            handleDocumentWorkflowStatuses(whereClause, params);
            handleMilestones(whereClause, params);
            handleOfficialTitle(whereClause, params);

            if (spo.isExcludeRejectedTrials()) {
                appendDWSExclusionClause(whereClause, params,
                        DocumentWorkflowStatusCode.REJECTED, REJECTED_DWS_PARAM);
            }
            if (spo.isExcludeTerminatedTrials()) {
                appendDWSExclusionClause(whereClause, params,
                        DocumentWorkflowStatusCode.SUBMISSION_TERMINATED,
                        TERMINATED_DWS_PARAM);
            }

            handlePrimaryPurposeCodes(whereClause, params);
            handlePhaseCodes(whereClause, params);
            handleSummary4AnatomicSites(whereClause, params);
            handleOrganizationIds(whereClause, params);
            generateLocationWhereClause(whereClause, params);
            handleIdentifiersAndOwnership(whereClause, params);
            handleAdditionalCriteria(whereClause, params);

            if (spo.getCtgovXmlRequiredIndicator() != null) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s %s.ctgovXmlRequiredIndicator = :%s" , operator,
                         SearchableUtils.ROOT_OBJ_ALIAS, CTGOV_XML_REQUIRED_INDICATOR));
                params.put(CTGOV_XML_REQUIRED_INDICATOR, spo.getCtgovXmlRequiredIndicator());
            }

            handleStudySource(whereClause, params);
            handleResultsFilters(whereClause, params);
            handleStudyReportingPeriodStatusCritera(whereClause, params);
            handleProgramCodeFilters(whereClause, params);
        }


        private void handleResultsFilters(StringBuffer whereClause, Map<String, Object> params) {
            if (spo.getSection801Indicators() != null && spo.getSection801Indicators().size() > 0) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s %s.section801Indicator in (:%s) " , operator,
                        SearchableUtils.ROOT_OBJ_ALIAS, SECTION_801_INDICATOR));
                params.put(SECTION_801_INDICATOR, spo.getSection801Indicators());
            }
            if (spo.getPcdFromDate() != null) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format("%s %s.dates.primaryCompletionDate >= :%s" , operator,
                        SearchableUtils.ROOT_OBJ_ALIAS, PCD_FROM_DATE));
                params.put(PCD_FROM_DATE, spo.getPcdFromDate());
            }

            if (spo.getPcdToDate() != null) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format("%s %s.dates.primaryCompletionDate <= :%s" , operator,
                        SearchableUtils.ROOT_OBJ_ALIAS, PCD_TO_DATE));
                params.put(PCD_TO_DATE, spo.getPcdToDate());
            }

            if ((spo.getPcdToDate() != null || spo.getPcdFromDate() != null) && spo.getPcdDateTypes() != null
                        && spo.getPcdDateTypes().size() > 0) {
                Set<ActualAnticipatedTypeCode> dateTypeCodes = new HashSet<ActualAnticipatedTypeCode>();
                for (ActualAnticipatedTypeCode code : spo.getPcdDateTypes()) {
                    dateTypeCodes.add(code);
                }
                String operator = determineOperator(whereClause);
                whereClause.append(String.format("%s %s.dates.primaryCompletionDateTypeCode in (:%s) " , operator,
                        SearchableUtils.ROOT_OBJ_ALIAS, PCD_DATE_TYPE));
                params.put(PCD_DATE_TYPE, dateTypeCodes);
            }

        }

        /**
         * @param whereClause
         * @param params
         */
        private void handleStudySource(StringBuffer whereClause,
                Map<String, Object> params) {
            if (CollectionUtils.isNotEmpty(spo.getStudySource())) {
                Set<StudySourceCode> statusCodes = new HashSet<StudySourceCode>();
                for (String source : spo.getStudySource()) {
                    statusCodes.add(StudySourceCode.getByCode(source));
                }
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(
                        " %s %s.studySource in (:%s) ", operator,
                        SearchableUtils.ROOT_OBJ_ALIAS, STUDY_SOURCE_PARAM));
                params.put(STUDY_SOURCE_PARAM, statusCodes);
            }
        }

        /**
         * @param whereClause
         * @param params
         */
        private void handleDocumentWorkflowStatuses(StringBuffer whereClause,
                Map<String, Object> params) {
            if (CollectionUtils.isNotEmpty(sp.getDocumentWorkflowStatuses())) {
                String operator = determineOperator(whereClause);
                Set<DocumentWorkflowStatusCode> statusCodes = new HashSet<DocumentWorkflowStatusCode>();
                for (DocumentWorkflowStatus status : sp.getDocumentWorkflowStatuses()) {
                    statusCodes.add(status.getStatusCode());
                }
                whereClause.append(String.format(" %s dws.statusCode in (:%s) ", operator, DWS_PARAM));
                whereClause.append(" and dws.currentlyActive = true");
                params.put(DWS_PARAM, statusCodes);
            }
        }

        /**
         * @param whereClause
         * @param params
         */
        private void handleStudyOverallStatuses(StringBuffer whereClause,
                Map<String, Object> params) {
            if (CollectionUtils.isNotEmpty(sp.getStudyOverallStatuses())) {
                String operator = determineOperator(whereClause);
                Set<StudyStatusCode> sosCodes = new HashSet<StudyStatusCode>();
                for (StudyOverallStatus status : sp.getStudyOverallStatuses()) {
                    sosCodes.add(status.getStatusCode());
                }
                // StudyStatusCode sos =
                // sp.getStudyOverallStatuses().iterator().next().getStatusCode();
                whereClause.append(String.format(
                        " %s sos.statusCode in (:%s) ", operator, SOS_PARAM));
                whereClause
                        .append(String
                                .format(" and sos.id = (select max(id) from %s.studyOverallStatuses where "
                                        + "statusDate = (select max(statusDate) from %s.studyOverallStatuses))",
                                        SearchableUtils.ROOT_OBJ_ALIAS,
                                        SearchableUtils.ROOT_OBJ_ALIAS));
                params.put(SOS_PARAM, sosCodes);
            }
        }


        /**
         * @param whereClause
         * @param params
         */
        private void handleStudyReportingPeriodStatusCritera(StringBuffer whereClause,
                                                Map<String, Object> params) {

            if (CollectionUtils.isNotEmpty(spo.getStudyStatusCodes())
                    && spo.getReportingPeriodStart() != null
                    && spo.getReportingPeriodEnd() != null) {
                String operator = determineOperator(whereClause);
                Set<StudyStatusCode> sosCodes = spo.getStudyStatusCodes();                
                whereClause
                        .append(String
                                .format("  %s (exists (select id from %s.studyOverallStatuses where "
                                        + "statusDate between :%s and :%s and statusCode in (:%s)) OR "
                                        + "exists (select id from %s.studyOverallStatuses where "
                                        + "statusDate < :%s and statusCode in (:%s) and currentlyActive=true) OR "
                                        + "exists (select sos1 from %s.studyOverallStatuses sos1 where "
                                        + "sos1.statusDate < :%s and sos1.statusCode in (:%s) and "
                                        + "exists (select sos2 from %s.studyOverallStatuses sos2 where "
                                        + "sos2.statusDate > :%s AND sos2.position=sos1.position+1)) )",
                                        operator,
                                        SearchableUtils.ROOT_OBJ_ALIAS,
                                        REPORTING_PERIOD_START_PARAM,
                                        REPORTING_PERIOD_END_PARAM, SOS_PARAM,
                                        SearchableUtils.ROOT_OBJ_ALIAS,
                                        REPORTING_PERIOD_START_PARAM,
                                        SOS_PARAM,
                                        SearchableUtils.ROOT_OBJ_ALIAS,
                                        REPORTING_PERIOD_START_PARAM,
                                        SOS_PARAM,
                                        SearchableUtils.ROOT_OBJ_ALIAS,
                                        REPORTING_PERIOD_START_PARAM));
                params.put(SOS_PARAM, sosCodes);
                params.put(REPORTING_PERIOD_START_PARAM,
                        spo.getReportingPeriodStart());
                params.put(REPORTING_PERIOD_END_PARAM,
                        spo.getReportingPeriodEnd());
            }
        }

        /**
         * Will add the programCode id filter
         * @param whereClause
         * @param params
         */
        public void handleProgramCodeFilters(StringBuffer whereClause, Map<String, Object> params) {
            if (CollectionUtils.isNotEmpty(spo.getProgramCodeIds())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s pgc.id in (:%s)", operator, PROGRAM_CODE_ID_PARAM));
                params.put(PROGRAM_CODE_ID_PARAM, spo.getProgramCodeIds());
            }
        }


        /**
         * @param whereClause where clause
         * @param params mapping of params and values
         */
        private void handleOfficialTitle(StringBuffer whereClause, Map<String, Object> params) {
            if (!StringUtils.isEmpty(sp.getOfficialTitle())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s (lower(%s.officialTitle) like :%s or "
                        + "exists (select sat.id from StudyAlternateTitle sat where "
                        + "sat.studyProtocol.id = %s.id and lower(sat.alternateTitle) like :%s))",
                        operator, SearchableUtils.ROOT_OBJ_ALIAS, STUDY_ALTERNATE_TITLE_PARAM,
                        SearchableUtils.ROOT_OBJ_ALIAS, STUDY_ALTERNATE_TITLE_PARAM));
                params.put(STUDY_ALTERNATE_TITLE_PARAM, "%" + sp.getOfficialTitle().toLowerCase() + "%");
            }
        }

        /**
         * @param whereClause
         * @param params
         * @param code
         * @param param
         */
        private void appendDWSExclusionClause(StringBuffer whereClause,
                Map<String, Object> params,
                final DocumentWorkflowStatusCode code, final String param) {
            String operator = determineOperator(whereClause);
            whereClause.append(String.format(" %s dws.statusCode != :%s", operator, param));
            whereClause.append(" and dws.currentlyActive = true");
            params.put(param, code);
        }

        /**
         * @param whereClause
         * @param params
         */
        @SuppressWarnings("PMD.ExcessiveMethodLength")
        private void handleMilestones(StringBuffer whereClause,
                Map<String, Object> params) {
            if (CollectionUtils.isNotEmpty(sp.getStudyMilestones())) {
                String operator = determineOperator(whereClause);
                Set<MilestoneCode> statusCodes = new HashSet<MilestoneCode>();
                for (StudyMilestone status : sp.getStudyMilestones()) {
                    statusCodes.add(status.getMilestoneCode());
                }
                whereClause
                        .append(String
                                .format(" %s (sms.milestoneCode in (:%s) and sms.last = true)",
                                        operator, SMS_PARAM));
                params.put(SMS_PARAM, statusCodes);
            }

            if (spo.getCurrentOrPreviousMilestone() != null) {
                String operator = determineOperator(whereClause);
                whereClause
                        .append(String
                                .format(" %s (select count(id) from %s.studyMilestones where milestoneCode=:%s) > 0",
                                        operator,
                                        SearchableUtils.ROOT_OBJ_ALIAS,
                                        CURRENT_OR_PREV_MILESTONE_PARAM));
                params.put(CURRENT_OR_PREV_MILESTONE_PARAM,
                        spo.getCurrentOrPreviousMilestone());
            }

            if (CollectionUtils.isNotEmpty(spo.getMilestoneFilters())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s ( ", operator));
                int cnt = 0;
                for (MilestoneFilter filter : spo.getMilestoneFilters()) {
                    whereClause.append(String.format(
                            "(sms.milestoneCode in (:%s) ",
                            ACTIVE_MILESTONES_PARAM + cnt));

                    if (!filter.getMilestonesToExclude().isEmpty()) {
                        whereClause.append(String.format(
                                " and (select count(id) from %s.studyMilestones "
                                        + "where milestoneCode in (:%s)) = 0 ",
                                SearchableUtils.ROOT_OBJ_ALIAS,
                                INACTIVE_MILESTONES_PARAM + cnt));
                        params.put(INACTIVE_MILESTONES_PARAM + cnt,
                                filter.getMilestonesToExclude());
                    }

                    whereClause.append(" and sms.currentlyActive = true)");

                    params.put(ACTIVE_MILESTONES_PARAM + cnt,
                            filter.getActiveMilestones());

                    if (spo.getMilestoneFilters().indexOf(filter) < spo
                            .getMilestoneFilters().size() - 1) {
                        whereClause.append(" OR ");
                    }
                    cnt++;
                }
                whereClause.append(" ) ");
            }

        }

        private void handlePrimaryPurposeCodes(StringBuffer whereClause,
                Map<String, Object> params) {
            if (CollectionUtils.isNotEmpty(spo.getPrimaryPurposeCodes())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(
                        " %s %s.primaryPurposeCode  in (:%s) ", operator,
                        SearchableUtils.ROOT_OBJ_ALIAS,
                        PRIMARY_PURPOSE_CODE_PARAM));
                params.put(PRIMARY_PURPOSE_CODE_PARAM,
                        spo.getPrimaryPurposeCodeEnums());
            }
        }

        private void handlePhaseCodes(StringBuffer whereClause, Map<String, Object> params) {
            if (CollectionUtils.isNotEmpty(spo.getPhaseCodes())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s %s.phaseCode  in (:%s) ", operator,
                                                 SearchableUtils.ROOT_OBJ_ALIAS, STUDY_PHASE_CODE_PARAM));
                params.put(STUDY_PHASE_CODE_PARAM, spo.getPhaseCodes());
            }
        }

        private void handleSummary4AnatomicSites(StringBuffer whereClause, Map<String, Object> params) {
            if (CollectionUtils.isNotEmpty(spo.getSummary4AnatomicSites())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s ans.id  in (:%s) ", operator, ANATOMIC_SITES_PARAM));
                params.put(ANATOMIC_SITES_PARAM, spo.getSummary4AnatomicSites());
            }
        }

        private void handleOrganizationIds(StringBuffer whereClause, Map<String, Object> params) {
            final boolean hasLeadOrgs = CollectionUtils.isNotEmpty(spo.getLeadOrganizationIds());
            final boolean hasSites = CollectionUtils.isNotEmpty(spo.getParticipatingSiteIds());
            final String leadOrgClause = "leadOrgSite.functionalCode = :%s and leadOrgRo.organization.id  in (:%s) ";
            final String siteClause = "exists (select sps2.id from StudySite sps2 "
                    + "left outer join sps2.healthCareFacility as hcf "
                    + "left outer join hcf.organization as site "
                    + "where sps2.studyProtocol.id = %s.id and site.id in (:%s))";
            final String operator = determineOperator(whereClause);

            if (hasLeadOrgs && hasSites) {
                String fmt = CONNECTOR;
                whereClause.append(String.format(fmt, operator));
                whereClause.append("((");
                whereClause.append(String.format(leadOrgClause,
                        LEAD_ORG_FUNCTIONAL_CODE_PARAM, LEAD_ORG_IDS_PARAM));
                whereClause.append(") or ");
                whereClause.append(String.format(siteClause,
                        SearchableUtils.ROOT_OBJ_ALIAS,
                        PARTICIPATING_SITE_PARAM));
                whereClause.append(") ");
                params.put(PARTICIPATING_SITE_PARAM, spo.getParticipatingSiteIds());
                params.put(LEAD_ORG_FUNCTIONAL_CODE_PARAM, StudySiteFunctionalCode.LEAD_ORGANIZATION);
                params.put(LEAD_ORG_IDS_PARAM, spo.getLeadOrganizationIds());
            } else if (hasLeadOrgs) {
                String fmt = CONNECTOR
                        + leadOrgClause;
                whereClause.append(String.format(fmt, operator, LEAD_ORG_FUNCTIONAL_CODE_PARAM, LEAD_ORG_IDS_PARAM));
                params.put(LEAD_ORG_FUNCTIONAL_CODE_PARAM, StudySiteFunctionalCode.LEAD_ORGANIZATION);
                params.put(LEAD_ORG_IDS_PARAM, spo.getLeadOrganizationIds());
            } else if (hasSites) {
                whereClause
                        .append(String
                                .format(CONNECTOR + siteClause,
                                        operator,
                                        SearchableUtils.ROOT_OBJ_ALIAS,
                                        PARTICIPATING_SITE_PARAM));
                params.put(PARTICIPATING_SITE_PARAM, spo.getParticipatingSiteIds());
            }

        }

        private void generateLocationWhereClause(StringBuffer whereClause, Map<String, Object> params) {
            if (spo.isByLocation()) {
                String operator = determineOperator(whereClause);
                whereClause.append(" " + operator + "(");
                createWhereClauseForLeadOrganization(whereClause);
                whereClause.append(" or ");
                createWhereClauseForParticipatingSite(whereClause);
                whereClause.append(" )");
                setLocationParams(params);
            }
        }


        private void createWhereClauseForLeadOrganization(StringBuffer whereClause) {
            whereClause.append("exists (select sslo.id from StudySite sslo where sslo.studyProtocol.id = "
                    + SearchableUtils.ROOT_OBJ_ALIAS + ".id and sslo.functionalCode = :"
                    + LEAD_ORGANIZATION_FUNCTIONAL_CODE_PARAM);

            if (StringUtils.isNotBlank(spo.getCountryName())) {
                whereClause.append(" and  sslo.researchOrganization.organization.countryName = :" + COUNTRY_NAME_PARAM);
            }

            if (CollectionUtils.isNotEmpty(spo.getStates())) {
                whereClause.append(" and  sslo.researchOrganization.organization.state in (:" + STATES_PARAM + ")");
            }

            if (StringUtils.isNotBlank(spo.getCity())) {
                whereClause.append(" and  sslo.researchOrganization.organization.city like :" + CITY_PARAM);
            }

            whereClause.append(" )");
        }

        private void createWhereClauseForParticipatingSite(StringBuffer whereClause) {
            whereClause.append("exists (select sslh.id from StudySite sslh where sslh.studyProtocol.id = "
                    + SearchableUtils.ROOT_OBJ_ALIAS + ".id and sslh.functionalCode = :"
                    + PARTICIPATING_SITE_FUNCTIONAL_CODE_PARAM);

            if (StringUtils.isNotBlank(spo.getCountryName())) {
                whereClause.append(" and sslh.healthCareFacility.organization.countryName = :" + COUNTRY_NAME_PARAM);
            }

            if (CollectionUtils.isNotEmpty(spo.getStates())) {
                whereClause.append(" and sslh.healthCareFacility.organization.state in (:" + STATES_PARAM + ")");
            }

            if (StringUtils.isNotBlank(spo.getCity())) {
                whereClause.append(" and sslh.healthCareFacility.organization.city like :" + CITY_PARAM);
            }

            whereClause.append(" )");
        }

        private void setLocationParams(Map<String, Object> params) {
            params.put(LEAD_ORGANIZATION_FUNCTIONAL_CODE_PARAM, StudySiteFunctionalCode.LEAD_ORGANIZATION);
            params.put(PARTICIPATING_SITE_FUNCTIONAL_CODE_PARAM, StudySiteFunctionalCode.TREATING_SITE);
            if (StringUtils.isNotBlank(spo.getCountryName())) {
                params.put(COUNTRY_NAME_PARAM, spo.getCountryName());
            }

            if (CollectionUtils.isNotEmpty(spo.getStates())) {
                params.put(STATES_PARAM, spo.getStates());
            }

            if (StringUtils.isNotBlank(spo.getCity())) {
                params.put(CITY_PARAM, "%" + spo.getCity() + "%");
            }
        }

        @SuppressWarnings({ "PMD.ExcessiveMethodLength", "PMD.NPathComplexity" })
        private void handleAdditionalCriteria(StringBuffer whereClause, Map<String, Object> params) {

            handleOnHolds(whereClause, params);
            handleSubmissionTypes(whereClause);
            handleCheckouts(whereClause, params);
            handleSubmissionDateRange(whereClause, params);
            handleNciSponsored(whereClause, params);
            handleFlags(whereClause, params);
            handleCTEPAndDCP(whereClause, params);
            handleTweetingCriteria(whereClause, params);
            handleSiteStatusCriteria(whereClause, params);

            if (spo.isInboxProcessing()) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s (select count(id) from %s.studyInbox where closeDate is null) "
                        + "> 0", operator, SearchableUtils.ROOT_OBJ_ALIAS));
            }

            if (CollectionUtils.isNotEmpty(spo.getBioMarkers())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s exists (select plm.id from PlannedMarker plm "
                                                         + "where plm.studyProtocol.id = %s.id and plm.id in (:%s))",
                                                 operator,
                                                 SearchableUtils.ROOT_OBJ_ALIAS, BIOMARKERS_PARAM));
                params.put(BIOMARKERS_PARAM, spo.getBioMarkers());
            }

            if (CollectionUtils.isNotEmpty(spo.getPdqDiseases())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String
                    .format(" %s exists (select sd.id from StudyDisease sd "
                                    + "where sd.studyProtocol.id = %s.id and sd.disease.id in (:%s))", operator,
                            SearchableUtils.ROOT_OBJ_ALIAS, PDQDISEASES_PARAM));
                params.put(PDQDISEASES_PARAM, spo.getPdqDiseases());
            }

            if (CollectionUtils.isNotEmpty(spo.getProcessingPriority())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(
                        " %s (%s.processingPriority in (:%s)) ", operator,
                        SearchableUtils.ROOT_OBJ_ALIAS,
                        PROCESSING_PRIORITY_PARAM));
                params.put(PROCESSING_PRIORITY_PARAM,
                        spo.getProcessingPriority());
            }

            searchByIntervention(whereClause, params);
        }

        private void handleSiteStatusCriteria(StringBuffer whereClause,
                Map<String, Object> params) {
            if (CollectionUtils.isNotEmpty(spo.getSiteStatusCodes())) {
                String operator = determineOperator(whereClause);
                whereClause
                        .append(String
                                .format(" %s exists (select ss from StudySite ss join ss.studySiteAccrualStatuses ssas "
                                        + " where ss.studyProtocol.id = %s.id "
                                        + " and ss.functionalCode = :%s "
                                        + " and ssas.statusCode in (:%s)"
                                        + " and ssas.id = (select max(id) "
                                        + "    from StudySiteAccrualStatus where studySite.id = ss.id"
                                        + "    and deleted=false"
                                        + "    and statusDate = (select max(statusDate) "
                                        + "        from StudySiteAccrualStatus where studySite.id =ss.id"
                                        + "        and deleted=false))) ",
                                        operator,
                                        SearchableUtils.ROOT_OBJ_ALIAS,
                                        PARTICIPATING_SITE_FUNCTIONAL_CODE_PARAM,
                                        SITE_STATUSES_PARAM));
                params.put(PARTICIPATING_SITE_FUNCTIONAL_CODE_PARAM,
                        StudySiteFunctionalCode.TREATING_SITE);
                params.put(SITE_STATUSES_PARAM, spo.getSiteStatusCodes());
            }

        }

        /**
         * @param whereClause
         * @param params
         */
        @SuppressWarnings("PMD.ExcessiveMethodLength")
        private void handleCTEPAndDCP(StringBuffer whereClause,
                Map<String, Object> params) {
            final String dcpIdExistsClause = "exists (select ssdcp.id from StudySite ssdcp where "
                    + "ssdcp.studyProtocol.id = "
                    + SearchableUtils.ROOT_OBJ_ALIAS
                    + ".id and ssdcp.localStudyProtocolIdentifier is not null "
                    + "and ssdcp.functionalCode = :"
                    + ID_ASSIGNER_FUNCTIONAL_CODE_PARAM
                    + " and ssdcp.researchOrganization.organization.name='"
                    + PAConstants.DCP_ORG_NAME + "') ";

            final String ctepIdExistsClause = "exists (select ssctep.id from StudySite ssctep where "
                    + "ssctep.studyProtocol.id = "
                    + SearchableUtils.ROOT_OBJ_ALIAS
                    + ".id and ssctep.localStudyProtocolIdentifier is not null "
                    + "and ssctep.functionalCode = :"
                    + ID_ASSIGNER_FUNCTIONAL_CODE_PARAM
                    + " and ssctep.researchOrganization.organization.name='"
                    + PAConstants.CTEP_ORG_NAME
                    + "') ";

            if (spo.isSearchDCPTrials()) {
                String operator = determineOperator(whereClause);
                params.put(ID_ASSIGNER_FUNCTIONAL_CODE_PARAM,
                        StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
                whereClause.append(String.format(CONNECTOR + dcpIdExistsClause,
                        operator));
            }

            if (spo.isSearchCTEPTrials()) {
                String operator = determineOperator(whereClause);
                params.put(ID_ASSIGNER_FUNCTIONAL_CODE_PARAM,
                        StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
                whereClause
                        .append(String
                                .format(CONNECTOR + ctepIdExistsClause + " and not " + dcpIdExistsClause,
                                        operator));
            }

            if (spo.isSearchCTEPAndDCPTrials()) {
                String operator = determineOperator(whereClause);
                params.put(ID_ASSIGNER_FUNCTIONAL_CODE_PARAM,
                        StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
                whereClause.append(String.format(" %s (" + ctepIdExistsClause
                        + " or " + dcpIdExistsClause + ") ", operator));
            }

            if (spo.isExcludeCtepDcpTrials()) {
                String operator = determineOperator(whereClause);
                params.put(ID_ASSIGNER_FUNCTIONAL_CODE_PARAM,
                        StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
                whereClause.append(String.format(" %s not " + ctepIdExistsClause
                        + " and not " + dcpIdExistsClause + " ", operator));
            }

        }

        /**
         * @param whereClause
         */
        private void handleOnHolds(StringBuffer whereClause, Map<String, Object> params) {
            if (spo.isSearchOnHoldTrials()) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(
                        " %s (select count(id) from %s.studyOnholds where onholdDate is " //NOPMD
                        + "not null and offholdDate is null) > 0", operator, SearchableUtils.ROOT_OBJ_ALIAS));
            } else if (spo.isSearchOffHoldTrials()) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s (select count(id) from %s.studyOnholds where onholdDate is "
                        + "not null and offholdDate is null) = 0", operator, SearchableUtils.ROOT_OBJ_ALIAS));
            }

            if (Boolean.TRUE.equals(spo.getHoldRecordExists())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(
                        " %s (select count(id) from %s.studyOnholds where onholdDate is "
                                + "not null) > 0", operator,
                        SearchableUtils.ROOT_OBJ_ALIAS));
            } else if (Boolean.FALSE.equals(spo.getHoldRecordExists())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(
                        " %s (select count(id) from %s.studyOnholds where onholdDate is "
                                + "not null) = 0", operator,
                        SearchableUtils.ROOT_OBJ_ALIAS));
            }

            if (CollectionUtils.isNotEmpty(spo.getOnholdReasons())) {
                String operator = determineOperator(whereClause);
                // On-hold reason OTHER needs special handling: we need to
                // consider On-Hold Reason Category if provided
                // See PO-8093 for more details.
                final Collection<OnholdReasonCode> notFilteredByReasonCategory = new LinkedHashSet<>(
                        spo.getOnholdReasons());
                boolean otherHoldWithReasonCategoriesPresent = false;
                boolean otherHoldOnly = false;
                if (notFilteredByReasonCategory
                        .contains(OnholdReasonCode.OTHER)
                        && !spo.getOnholdOtherReasonCategories().isEmpty()) {
                    // At this point we know we need to treat OTHER in a special
                    // way: narrow it down by categories specified.
                    otherHoldWithReasonCategoriesPresent = true;
                    notFilteredByReasonCategory.remove(OnholdReasonCode.OTHER);
                    otherHoldOnly = notFilteredByReasonCategory.isEmpty();
                }
                if (spo.isSearchOnHoldTrials()
                        && !Boolean.TRUE.equals(spo.getHoldRecordExists())) {
                    if (!otherHoldWithReasonCategoriesPresent) {
                        whereClause
                                .append(String
                                        .format(" %s (select count(id) from %s.studyOnholds where onholdDate is "
                                                + "not null and offholdDate is null and onholdReasonCode in (:%s)) > 0",
                                                operator,
                                                SearchableUtils.ROOT_OBJ_ALIAS,
                                                ONHOLD_REASONS_PARAM));
                        params.put(ONHOLD_REASONS_PARAM,
                                notFilteredByReasonCategory);
                    } else {
                        if (otherHoldOnly) {
                            whereClause
                                    .append(String
                                            .format(" %s ((select count(id) from %s.studyOnholds where onholdDate is "
                                                    + "not null and offholdDate is null and onholdReasonCode=:%s "
                                                    + "and onholdReasonCategory in (:%s)) > 0)",
                                                    operator,
                                                    SearchableUtils.ROOT_OBJ_ALIAS,
                                                    ONHOLD_OTHER_CODE_PARAM,
                                                    ONHOLD_OTHER_CATEGORIES_PARAM));
                        } else {
                            whereClause
                                    .append(String
                                            .format(" %s ((select count(id) from %s.studyOnholds where onholdDate is "
                                                    + "not null and "
                                                    + "offholdDate is null and onholdReasonCode in (:%s)) > 0 "
                                                    + " OR "
                                                    + "(select count(id) from %s.studyOnholds where onholdDate is "
                                                    + "not null and offholdDate is null and onholdReasonCode=:%s "
                                                    + "and onholdReasonCategory in (:%s)) > 0)",
                                                    operator,
                                                    SearchableUtils.ROOT_OBJ_ALIAS,
                                                    ONHOLD_REASONS_PARAM,
                                                    SearchableUtils.ROOT_OBJ_ALIAS,
                                                    ONHOLD_OTHER_CODE_PARAM,
                                                    ONHOLD_OTHER_CATEGORIES_PARAM));
                            params.put(ONHOLD_REASONS_PARAM,
                                    notFilteredByReasonCategory);
                        }
                        params.put(ONHOLD_OTHER_CODE_PARAM,
                                OnholdReasonCode.OTHER);
                        params.put(ONHOLD_OTHER_CATEGORIES_PARAM,
                                spo.getOnholdOtherReasonCategories());
                    }
                } else {
                    if (!otherHoldWithReasonCategoriesPresent) {
                        whereClause
                                .append(String
                                        .format(" %s (select count(id) from %s.studyOnholds where onholdDate is "
                                                + "not null and onholdReasonCode in (:%s)) > 0",
                                                operator,
                                                SearchableUtils.ROOT_OBJ_ALIAS,
                                                ONHOLD_REASONS_PARAM));
                        params.put(ONHOLD_REASONS_PARAM,
                                notFilteredByReasonCategory);
                    } else {
                        if (otherHoldOnly) {
                            whereClause
                                    .append(String
                                            .format(" %s ((select count(id) from %s.studyOnholds where onholdDate is "
                                                    + "not null and onholdReasonCode=:%s "
                                                    + "and onholdReasonCategory in (:%s)) > 0)",
                                                    operator,
                                                    SearchableUtils.ROOT_OBJ_ALIAS,
                                                    ONHOLD_OTHER_CODE_PARAM,
                                                    ONHOLD_OTHER_CATEGORIES_PARAM));
                        } else {
                            whereClause
                                    .append(String
                                            .format(" %s ((select count(id) from %s.studyOnholds where onholdDate is "
                                                    + "not null and onholdReasonCode in (:%s)) > 0 OR "
                                                    + "(select count(id) from %s.studyOnholds where onholdDate is "
                                                    + "not null and onholdReasonCode=:%s "
                                                    + "and onholdReasonCategory in (:%s)) > 0)",
                                                    operator,
                                                    SearchableUtils.ROOT_OBJ_ALIAS,
                                                    ONHOLD_REASONS_PARAM,
                                                    SearchableUtils.ROOT_OBJ_ALIAS,
                                                    ONHOLD_OTHER_CODE_PARAM,
                                                    ONHOLD_OTHER_CATEGORIES_PARAM));
                            params.put(ONHOLD_REASONS_PARAM,
                                    notFilteredByReasonCategory);
                        }
                        params.put(ONHOLD_OTHER_CODE_PARAM,
                                OnholdReasonCode.OTHER);
                        params.put(ONHOLD_OTHER_CATEGORIES_PARAM,
                                spo.getOnholdOtherReasonCategories());
                    }
                }
            }
        }

        /**
         * @param whereClause
         * @param params
         */
        private void handleFlags(StringBuffer whereClause,
                Map<String, Object> params) {
            if (spo.getNotFlaggedWith() != null) {
                String operator = determineOperator(whereClause);
                whereClause
                        .append(String
                                .format(" %s (not exists (select f.id from StudyProtocolFlag f where "
                                        + "f.studyProtocol.id = %s.id and f.flagReason = :"
                                        + FLAG_CODE_PARAM
                                        + " and f.deleted<>true)) ", operator,
                                        SearchableUtils.ROOT_OBJ_ALIAS));

                params.put(FLAG_CODE_PARAM, spo.getNotFlaggedWith());
            }
        }


        /**
         * @param whereClause
         * @param params
         */
        private void handleNciSponsored(StringBuffer whereClause,
                Map<String, Object> params) {
            if (Boolean.TRUE.equals(spo.getNciSponsored())) {
                addNciSponsoredClause(whereClause, params, false);
            } else if (Boolean.FALSE.equals(spo.getNciSponsored())) {
                addNciSponsoredClause(whereClause, params, true);
            }
        }

        /**
         * @param whereClause
         * @param params
         */
        private void handleTweetingCriteria(StringBuffer whereClause,
                Map<String, Object> params) {
            if (Boolean.TRUE.equals(spo.getHasTweets())) {
                addHasTweetsClause(whereClause, params, false);
            } else if (Boolean.FALSE.equals(spo.getHasTweets())) {
                addHasTweetsClause(whereClause, params, true);
            }
        }

        private void addHasTweetsClause(StringBuffer whereClause,
                Map<String, Object> params, boolean negation) {
            String operator = determineOperator(whereClause);
            whereClause.append(String.format(
                    " %s (%s exists (select t.id from Tweet t where "
                            + "t.studyProtocol.id = %s.id )) ", operator,
                    (negation ? "not" : ""), SearchableUtils.ROOT_OBJ_ALIAS));

        }

        /**
         * @param whereClause
         * @param params
         */
        private void addNciSponsoredClause(StringBuffer whereClause,
                Map<String, Object> params, boolean negation) {
            try {
                String operator = determineOperator(whereClause);
                whereClause
                        .append(String
                                .format(" %s (%s exists (select sponsor.id from StudySite sponsor where "
                                        + "sponsor.studyProtocol.id = %s.id and sponsor.functionalCode = :"
                                        + SPONSOR_FUNCTIONAL_CODE_PARAM
                                        + " and sponsor.researchOrganization.organization.identifier='"
                                        + PaRegistry.getLookUpTableService()
                                                .getPropertyValue("nci.poid")
                                        + "')) ", operator, (negation ? "not"
                                        : ""), SearchableUtils.ROOT_OBJ_ALIAS));

                params.put(SPONSOR_FUNCTIONAL_CODE_PARAM,
                        StudySiteFunctionalCode.SPONSOR);
            } catch (PAException e) {
                throw new RuntimeException(e); // NOPMD
            }
        }

        /**
         * @param whereClause
         */
        private void handleSubmissionTypes(StringBuffer whereClause) {
            CollectionUtils.filter(spo.getTrialSubmissionTypes(), NotNullPredicate.INSTANCE);
            if (CollectionUtils.isNotEmpty(spo.getTrialSubmissionTypes())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s (", operator));

                if (spo.getTrialSubmissionTypes()
                        .contains(SubmissionTypeCode.A)) {
                    whereClause
                            .append(String
                                    .format("(%s.submissionNumber > 1 and %s.amendmentDate is not null) or ",
                                            SearchableUtils.ROOT_OBJ_ALIAS,
                                            SearchableUtils.ROOT_OBJ_ALIAS));
                }
                if (spo.getTrialSubmissionTypes()
                        .contains(SubmissionTypeCode.O)) {
                    whereClause.append(String.format(
                            "(%s.submissionNumber = 1 and %s.amendmentNumber is null "
                                    + "and %s.amendmentDate is null) or ",
                            SearchableUtils.ROOT_OBJ_ALIAS,
                            SearchableUtils.ROOT_OBJ_ALIAS,
                            SearchableUtils.ROOT_OBJ_ALIAS));
                }
                if (spo.getTrialSubmissionTypes()
                        .contains(SubmissionTypeCode.U)) {
                    // A trial is considered update when it has at least one
                    // study inbox entry without a close date.
                    whereClause.append(String.format(
                            "((select count(id) from %s.studyInbox where closeDate is null) "
                                    + "> 0) or ",
                            SearchableUtils.ROOT_OBJ_ALIAS));
                }

                whereClause.append(" 1=2)");

            }
        }

        /**
         * @param whereClause
         * @param params
         */
        private void handleSubmissionDateRange(StringBuffer whereClause,
                Map<String, Object> params) {
            if (spo.getSubmittedOnOrAfter() != null) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(
                        " %s (%s.dateLastCreated >=:%s)", operator,
                        SearchableUtils.ROOT_OBJ_ALIAS,
                        SUBMITTED_ON_OR_AFTER_PARAM));
                params.put(SUBMITTED_ON_OR_AFTER_PARAM,
                        spo.getSubmittedOnOrAfter());
            }
            if (spo.getSubmittedOnOrBefore() != null) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(
                        " %s (%s.dateLastCreated <=:%s)", operator,
                        SearchableUtils.ROOT_OBJ_ALIAS,
                        SUBMITTED_ON_OR_BEFORE_PARAM));
                params.put(SUBMITTED_ON_OR_BEFORE_PARAM,
                        spo.getSubmittedOnOrBefore());
            }
        }

        /**
         * @param whereClause
         * @param params
         */
        private void handleCheckouts(StringBuffer whereClause,
                Map<String, Object> params) {
            if (spo.isLockedTrials()) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s (select count(id) from %s.studyCheckout where "
                        + "userIdentifier = :%s and checkInDate is null) > 0", operator,
                        SearchableUtils.ROOT_OBJ_ALIAS, CHECKOUT_PARAM));
                params.put(CHECKOUT_PARAM, spo.getLockedUser());
            }
            if (Boolean.TRUE.equals(spo.getCheckedOut())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s (select count(id) from %s.studyCheckout where "
                        + "userIdentifier is not null and checkInDate is null) > 0", operator,
                        SearchableUtils.ROOT_OBJ_ALIAS));
            }
            if (Boolean.FALSE.equals(spo.getCheckedOut())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s (select count(id) from %s.studyCheckout where "
                        + "userIdentifier is not null and checkInDate is null) = 0", operator,
                        SearchableUtils.ROOT_OBJ_ALIAS));
            }
        }

        private void searchByIntervention(StringBuffer whereClause, Map<String, Object> params) {
            if (CollectionUtils.isNotEmpty(spo.getInterventionIds())
                    || CollectionUtils.isNotEmpty(spo.getInterventionAlternateNameIds())
                    || CollectionUtils.isNotEmpty(spo.getInterventionTypes())) {
                StringBuilder sql = createSqlForSearchByIntervention(whereClause, params);
                whereClause.append(sql.toString());
            }
        }

        private StringBuilder createSqlForSearchByIntervention(StringBuffer whereClause, Map<String, Object> params) {
            String operator = determineOperator(whereClause);

            StringBuilder sql = new StringBuilder(" ");

            sql.append(operator);

            sql.append(" exists (select pa.id from PlannedActivity pa");

            if (CollectionUtils.isNotEmpty(spo.getInterventionAlternateNameIds())) {
                sql.append(" join pa.intervention intr left join intr.interventionAlternateNames aln ");
            }

            sql.append(" where pa.studyProtocol.id = " + SearchableUtils.ROOT_OBJ_ALIAS + ".id and (0=1");

            if (CollectionUtils.isNotEmpty(spo.getInterventionTypes())) {
                sql.append(" or pa.intervention.typeCode in (:" + INTERVENTIONS_TYPES_PARAM + ") ");
                params.put(INTERVENTIONS_TYPES_PARAM, spo.getInterventionTypes());
            }

            if (CollectionUtils.isNotEmpty(spo.getInterventionIds())) {
                sql.append(" or pa.intervention.id in (:" + INTERVENTIONS_PARAM + ") ");
                params.put(INTERVENTIONS_PARAM, spo.getInterventionIds());
            }

            if (CollectionUtils.isNotEmpty(spo.getInterventionAlternateNameIds())) {
                sql.append(" or aln.id in (:" + INTERVENTIONS_ALTERNAMES_PARAM + ")");
                params.put(INTERVENTIONS_ALTERNAMES_PARAM, spo.getInterventionAlternateNameIds());
            }

            sql.append("))");
            return sql;
        }

        private void handleIdentifiersAndOwnership(StringBuffer whereClause,
                Map<String, Object> params) {
            final String anyTypeIdentifier = spo.getAnyTypeIdentifier();
            if (StringUtils.isNotBlank(anyTypeIdentifier)) {
                final String anyTypeIdentifierSQL = StringEscapeUtils
                        .escapeSql(anyTypeIdentifier);
                String operator = determineOperator(whereClause);
                whereClause.append(String.format("%s ( ", operator));

                // Other identifiers
                // Intentionally disable PMD complains here, because I want
                // multiple appends for readability
                // and know for sure what the default Locale will be.
                
                // NCI ID
                whereClause.append("upper(obj.nciId) like '%" // NOPMD
                        + anyTypeIdentifierSQL.toUpperCase() // NOPMD
                        + "%'");
                
                whereClause.append(" or ");
                appendOtherIdentifierSearchClause(whereClause,
                        anyTypeIdentifierSQL,
                        IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);
                whereClause.append(" or "); // NOPMD

                // DCP
                whereClause.append("upper(obj.dcpId) like '%" // NOPMD
                        + anyTypeIdentifierSQL.toUpperCase() // NOPMD
                        + "%'");
                whereClause.append(" or "); // NOPMD

                // CCR
                whereClause.append("upper(obj.ccrId) like '%" // NOPMD
                        + anyTypeIdentifierSQL.toUpperCase() // NOPMD
                        + "%'");
                whereClause.append(" or "); // NOPMD

                // NCT
                whereClause.append("upper(obj.nctId) like '%" // NOPMD
                        + anyTypeIdentifierSQL.toUpperCase() // NOPMD
                        + "%'");
                whereClause.append(" or "); // NOPMD

                // CTEP
                whereClause.append("upper(obj.ctepId) like '%" // NOPMD
                        + anyTypeIdentifierSQL.toUpperCase() // NOPMD
                        + "%'");
                whereClause.append(" or "); // NOPMD

                // Lead Org.
                whereClause.append("upper(obj.leadOrgId) like '%" // NOPMD
                        + anyTypeIdentifierSQL.toUpperCase() // NOPMD
                        + "%'");

                whereClause.append(" ) ");
            }
            handleOtherIdentifiers(whereClause);
            handleMyTrialsOnly(whereClause, params);
            handleSubmitterAffiliation(whereClause, params);
            handleExcludeSubmitterAffiliation(whereClause, params);
        }

        private void appendOtherIdentifierSearchClause(
                StringBuffer whereClause, String anyTypeIdentifierSQL,
                String root) {
            whereClause
                    .append("exists (select oi.extension from obj.otherIdentifiers oi where oi.root = '" // NOPMD
                            + root
                            + "' and upper(oi.extension) like '%"
                            + anyTypeIdentifierSQL.toUpperCase() // NOPMD
                            + "%')");

        }

        /**
         * @param whereClause
         * @param params
         */
        private void handleMyTrialsOnly(StringBuffer whereClause,
                Map<String, Object> params) {
            if (spo.isMyTrialsOnly() && spo.getUserId() != null) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s  ( sowner.id = :%s or :%s in (select id from RegistryUser where "
                        + "affiliatedOrgUserType = :%s and str(affiliatedOrganizationId) in ( select "
                        + "ss.researchOrganization.organization.identifier from  %s.studySites ss where "
                        + "ss.functionalCode = :%s )) or (:%s in (select id from RegistryUser where "
                        + "str(affiliatedOrganizationId) in (select "
                        + "ss.healthCareFacility.organization.identifier from  %s.studySites ss where "
                        + "ss.functionalCode = :%s and ss.statusCode != :%s)) and %s.proprietaryTrialIndicator=true)) "
                        + "and dws.statusCode  != :%s ",
                        operator, STUDY_OWNER_PARAM,
                        STUDY_OWNER_PARAM, STUDY_OWNER_TYPE_PARAM, SearchableUtils.ROOT_OBJ_ALIAS,
                        STUDY_OWNER_SITE_PARAM, STUDY_OWNER_PARAM, SearchableUtils.ROOT_OBJ_ALIAS,
                        TREATING_SITE_CODE_PARAM,
                        NULLIFIED_STATUS_PARAM, SearchableUtils.ROOT_OBJ_ALIAS,
                        STUDY_OWNER_DWS_PARAM));
                params.put(STUDY_OWNER_PARAM, spo.getUserId());
                params.put(STUDY_OWNER_TYPE_PARAM, UserOrgType.ADMIN);
                params.put(STUDY_OWNER_SITE_PARAM, StudySiteFunctionalCode.LEAD_ORGANIZATION);
                params.put(TREATING_SITE_CODE_PARAM, StudySiteFunctionalCode.TREATING_SITE);
                params.put(NULLIFIED_STATUS_PARAM, FunctionalRoleStatusCode.NULLIFIED);
                params.put(STUDY_OWNER_DWS_PARAM, DocumentWorkflowStatusCode.REJECTED);
            } else if (!spo.isMyTrialsOnly() && spo.getUserId() != null) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(
                        "%s (sowner.id = :%s or ((sowner.id <> :%s or sowner.id is null) and dws.statusCode != :%s))",
                        operator, STUDY_OWNER_PARAM, STUDY_OWNER_PARAM, STUDY_OWNER_DWS_PARAM));
                params.put(STUDY_OWNER_PARAM, spo.getUserId());
                params.put(STUDY_OWNER_DWS_PARAM, DocumentWorkflowStatusCode.SUBMITTED);
            }
        }

        /**
         * @param whereClause
         * @param params
         */
        private void handleSubmitterAffiliation(StringBuffer whereClause,
                Map<String, Object> params) {
            if (StringUtils.isNotBlank(spo.getSubmitterAffiliateOrgId())) {
                String operator = determineOperator(whereClause);
                whereClause
                        .append(String
                                .format(" %s (exists (select id from RegistryUser where "
                                        + "str(affiliatedOrganizationId) = :%s "
                                        + "and csmUser.userId=%s.userLastCreated.userId))",
                                        operator, AFFILIATE_ORG_ID_PARAM,
                                        SearchableUtils.ROOT_OBJ_ALIAS));
                params.put(AFFILIATE_ORG_ID_PARAM,
                        spo.getSubmitterAffiliateOrgId());
            }
        }

        /**
         * @param whereClause
         * @param params
         */
        private void handleExcludeSubmitterAffiliation(StringBuffer whereClause,
                Map<String, Object> params) {
            if (CollectionUtils.isNotEmpty(spo.getSubmitterAffiliateOrgName())) {
                String operator = determineOperator(whereClause);
                Set<String> orgList = new HashSet<String>();
                for (String org : spo.getSubmitterAffiliateOrgName()) {
                    orgList.add(org);
                }
                whereClause
                        .append(String
                                .format(" %s (not exists (select id from RegistryUser where "
                                        + "affiliatedOrganizationName in (:%s) "
                                        + "and csmUser.userId=%s.userLastCreated.userId))",
                                        operator, AFFILIATE_ORG_NAME_PARAM,
                                        SearchableUtils.ROOT_OBJ_ALIAS));
                params.put(AFFILIATE_ORG_NAME_PARAM,
                        orgList);
            }
        }

        /**
         * @param whereClause
         */
        private void handleOtherIdentifiers(StringBuffer whereClause) {
            if (CollectionUtils.isNotEmpty(sp.getOtherIdentifiers())) {
                String operator = determineOperator(whereClause);
                Iterator<Ii> ids = sp.getOtherIdentifiers().iterator();
                whereClause.append(String.format("%s ( ", operator));
                while (ids.hasNext()) {
                    Ii id = ids.next();
                    whereClause
                            .append("exists (select oi.extension from obj.otherIdentifiers oi where oi.root = '"
                                    + id.getRoot()
                                    + "' and upper(oi.extension) like '%"
                                    + StringEscapeUtils.escapeSql(id
                                            .getExtension().toUpperCase())
                                    + "%') ");
                    if (ids.hasNext()) {
                        whereClause.append(" or ");
                    } else {
                        whereClause.append(") ");
                    }
                }
            }
        }

        private static String determineOperator(final StringBuffer whereClause) {
            String operator = "";
            if (StringUtils.isBlank(whereClause.toString())) {
                operator = SearchableUtils.WHERE;
            } else if (!SearchableUtils.WHERE.equals(whereClause.toString())) {
                //handle case when no criterion are provided.
                operator = SearchableUtils.AND;
            }
            return operator;
        }
    }
}
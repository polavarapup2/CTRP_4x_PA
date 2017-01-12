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
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;

/**
 * @author mshestopalov
 *
 */
public class ReportStudyProtocolQueryConverter extends BaseStudyProtocolQueryConverter {

    private static final int SUMM_FIELD_LASTNAME = 0;
    private static final int SUMM_FIELD_FIRSTNAME = 1;
    private static final int SUMM_FIELD_PI_ID = 2;
    private static final int SUMM_FIELD_LEADORG_NAME = 3;
    private static final int SUMM_FIELD_LEADORG_ID = 4;
    private static final int SUMM_FIELD_LOCAL_PROTOCOL_ID = 5;
    private static final int SUMM_FIELD_FUND_SRC_CAT = 6;
    private static final int SUMM_FIELD_STATUS_CODE = 7;
    private static final int SUMM_FIELD_STATUS_DATE = 8;
    private static final int SUMM_FIELD_DOC_WORKFLOW_STATUS = 9;
    private static final int SUMM_FIELD_DOC_WORKFLOW_DATE = 10;
    private static final int SUMM_FIELD_STUDY_INBOX_ID = 11;
    private static final int SUMM_FIELD_STUDY_INBOX_COMMENTS = 12;
    private static final int SUMM_FIELD_STUDY_INBOX_OPEN_DATE = 13;
    private static final int SUMM_FIELD_STUDY_INBOX_CLOSE_DATE = 14;

    private static final int SUMM_FIELD_OFFICIAL_TITLE = 15;
    private static final int SUMM_FIELD_PHASE_CODE = 16;
    private static final int SUMM_FIELD_PRIMARY_PURPOSE_CODE = 17;
    private static final int SUMM_FIELD_PROP_TRIAL = 18;
    private static final int SUMM_FIELD_RECORD_VERIFY_DATE = 19;
    private static final int SUMM_FIELD_CTGOX_XML = 20;
    private static final int SUMM_FIELD_PHASE_ADDITIONAL = 21;
    private static final int SUMM_FIELD_DATE_LAST_CREATED = 22;
    private static final int SUMM_FIELD_AMENDMENT_NUMBER = 23;
    private static final int SUMM_FIELD_AMENDMENT_DATE = 24;
    private static final int SUMM_FIELD_SUBMISSION_NUMBER = 25;
    private static final int SUMM_FIELD_STUDY_PROTOCOL_TYPE = 26;
    private static final int SUMM_FIELD_STUDY_NCI_ID = 27;
    private static final int SUMM_FIELD_LEAD_ORG_TRIAL_ID = 28;
    private static final int SUMM_FIELD_NCT_ID = 29;
    private static final int SUMM_FIELD_STUDY_SOURCE = 30;

    private final String generateDiseaseNamesSql =
        "select d.PREFERRED_NAME from study_disease AS sd "
        + "inner JOIN PDQ_DISEASE AS d ON sd.DISEASE_IDENTIFIER = d.identifier "
        + "where sd.study_protocol_identifier = :spId order by d.PREFERRED_NAME";

    private final String generateInterventionTypeCodesSql =
        "select i.TYPE_CODE from PLANNED_ACTIVITY AS pa "
        + "inner JOIN INTERVENTION AS i ON pa.INTERVENTION_IDENTIFIER = i.identifier "
        + "where pa.study_protocol_identifier = :spId order by i.TYPE_CODE";
    /**
     * Const.
     * @param registryUserSvc registry user service.
     * @param paSvcUtils pa utils.
     */
    public ReportStudyProtocolQueryConverter(RegistryUserServiceLocal registryUserSvc, PAServiceUtils paSvcUtils) {
        super(registryUserSvc, paSvcUtils);
    }

    /**
     * Convert trial domain object into a fully loaded dto for ad hoc report.
     * @param studyProtocolId trial domain object id
     * @param myTrialsOnly only able to view user's trials
     * @param potentialOwner trial owner
     * @return dto
     * @throws PAException when error
     */
    public StudyProtocolQueryDTO convertToStudyProtocolDtoForReporting(Long studyProtocolId,
            boolean myTrialsOnly, RegistryUser potentialOwner) throws PAException {
        StudyProtocolQueryDTO studyProtocolDto = new StudyProtocolQueryDTO();

        if (!userHasAccess(potentialOwner, studyProtocolDto, studyProtocolId, myTrialsOnly)) {
            return null;
        }

        findTrialSummaryFields(studyProtocolDto, studyProtocolId);
        findStudyMilestones(studyProtocolDto, studyProtocolId);
        findDiseaseNameFields(studyProtocolDto, studyProtocolId);
        findInterventionTypeFields(studyProtocolDto, studyProtocolId);

        setViewTSR(studyProtocolDto, studyProtocolDto.getDocumentWorkflowStatusCode());
        return studyProtocolDto;
    }

    /**
     * Find Milestones.
     * @param studyProtocolDto dto
     * @param studyProtocolId trial id.
     */
    protected void findStudyMilestones(StudyProtocolQueryDTO studyProtocolDto, Long studyProtocolId) {
        Query query = PaHibernateUtil.getCurrentSession().createQuery(
                "select sm from StudyMilestone as sm where sm.studyProtocol.id = :trialId order by sm.id desc");
        query.setLong("trialId", studyProtocolId);
        @SuppressWarnings("unchecked")
        List<StudyMilestone> smList = query.list();
        PAUtil.convertMilestonesToDTO(studyProtocolDto.getMilestones(),
                smList);
    }

    /**
     * Find needed fieds for trial.
     * @param spDto dto
     * @param spId trial id.
     */
    protected void findTrialSummaryFields(StudyProtocolQueryDTO spDto, Long spId) {
        String sql = generateReportingSql();
        Query query = PaHibernateUtil.getCurrentSession().createSQLQuery(sql);
        query.setLong("spId", spId);
        query.setString("leadOrgRole", StudySiteFunctionalCode.LEAD_ORGANIZATION.name());
        query.setString("piRole", StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.name());
        query.setString("NCI_II_ROOT", IiConverter.STUDY_PROTOCOL_ROOT);
        Object[] piData = (Object[]) query.uniqueResult();
        spDto.setStudyProtocolId(spId);
        spDto.setStudyTypeCode(StudyTypeCode.INTERVENTIONAL);
        if (piData != null) {
            loadStudyProtocolDto(piData, spDto);
        }
    }

    /**
     * Find Disease Names.
     * @param spDto dto
     * @param studyProtocolId trial id.
     */
    protected void findDiseaseNameFields(StudyProtocolQueryDTO spDto, Long studyProtocolId) {
        Query query = PaHibernateUtil.getCurrentSession().createSQLQuery(generateDiseaseNamesSql);
        query.setLong("spId", studyProtocolId);
        Set<String> diseaseSet = new HashSet<String>();
        for (Object disease : query.list()) {
            diseaseSet.add((String) disease);
        }
        spDto.setDiseaseNames(diseaseSet);
    }

    /**
     * Find Intervention Type Fields.
     * @param spDto dto.
     * @param studyProtocolId trial id.
     */
    protected void findInterventionTypeFields(StudyProtocolQueryDTO spDto, Long studyProtocolId) {
        Query query = PaHibernateUtil.getCurrentSession().createSQLQuery(generateInterventionTypeCodesSql);
        query.setLong("spId", studyProtocolId);
        Set<String> interventionTypeSet = new HashSet<String>();
        for (Object interventionType : query.list()) {
            interventionTypeSet.add(InterventionTypeCode.valueOf((String) interventionType).getCode());
        }
        spDto.setInterventionType(interventionTypeSet);
    }

    private void loadStudyProtocolDto(Object[] piData, StudyProtocolQueryDTO spDto) {
        loadPiAndLeadOrgIntoStudyProtocolDto(piData, spDto);
        loadNctIdentifier(piData, spDto);
        if (piData[SUMM_FIELD_STATUS_CODE] != null) {
            spDto.setStudyStatusCode(StudyStatusCode.valueOf((String) piData[SUMM_FIELD_STATUS_CODE]));
        }
        spDto.setStudyStatusDate((Timestamp) piData[SUMM_FIELD_STATUS_DATE]);
        if (piData[SUMM_FIELD_DOC_WORKFLOW_STATUS] != null) {
            spDto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.valueOf(
                (String) piData[SUMM_FIELD_DOC_WORKFLOW_STATUS]));
        }
        spDto.setDocumentWorkflowStatusDate((Timestamp) piData[SUMM_FIELD_DOC_WORKFLOW_DATE]);
        if (piData[SUMM_FIELD_STUDY_INBOX_ID] != null) {
            spDto.setStudyInboxId(((Number) piData[SUMM_FIELD_STUDY_INBOX_ID]).longValue());
        }
        spDto.setUpdatedComments((String) piData[SUMM_FIELD_STUDY_INBOX_COMMENTS]);
        spDto.setUpdatedDate((Timestamp) piData[SUMM_FIELD_STUDY_INBOX_OPEN_DATE]);

        SubmissionTypeVars subVars = new SubmissionTypeVars();
        subVars.setAmendmentDate((Timestamp) piData[SUMM_FIELD_AMENDMENT_DATE]);
        subVars.setAmendmentNumber((String) piData[SUMM_FIELD_AMENDMENT_NUMBER]);
        subVars.setSubmissionNumber((Integer) piData[SUMM_FIELD_SUBMISSION_NUMBER]);
        subVars.setClosedDate((Timestamp) piData[SUMM_FIELD_STUDY_INBOX_CLOSE_DATE]);
        subVars.setInboxExists(spDto.getStudyInboxId() != null);
        setSubmissionType(spDto, subVars);

        spDto.setOfficialTitle((String) piData[SUMM_FIELD_OFFICIAL_TITLE]);
        loadPhaseCodeAndPurposeIntoStudyProtocolDto(piData, spDto);
        spDto.setRecordVerificationDate((Timestamp) piData[SUMM_FIELD_RECORD_VERIFY_DATE]);
        if (piData[SUMM_FIELD_CTGOX_XML] != null) {
            spDto.setCtgovXmlRequiredIndicator((Boolean) piData[SUMM_FIELD_CTGOX_XML]);
        }
        if (piData[SUMM_FIELD_PHASE_ADDITIONAL] != null) {
            spDto.setPhaseAdditionalQualifier(PhaseAdditionalQualifierCode.valueOf(
                (String) piData[SUMM_FIELD_PHASE_ADDITIONAL]));
        }
        spDto.getLastCreated().setDateLastCreated((Timestamp) piData[SUMM_FIELD_DATE_LAST_CREATED]);
        spDto.setStudyProtocolType((String) piData[SUMM_FIELD_STUDY_PROTOCOL_TYPE]);
        spDto.setNciIdentifier((String) piData[SUMM_FIELD_STUDY_NCI_ID]);
        spDto.setStudySource((String) piData[SUMM_FIELD_STUDY_SOURCE]);
    }

    private void loadPhaseCodeAndPurposeIntoStudyProtocolDto(Object[] piData, StudyProtocolQueryDTO spDto) {
        if (piData[SUMM_FIELD_PHASE_CODE] != null) {
            spDto.setPhaseCode(PhaseCode.valueOf((String) piData[SUMM_FIELD_PHASE_CODE]));
        }
        spDto.setPrimaryPurpose((String) piData[SUMM_FIELD_PRIMARY_PURPOSE_CODE]);
        if (piData[SUMM_FIELD_PROP_TRIAL] != null) {
            spDto.setProprietaryTrial((Boolean) piData[SUMM_FIELD_PROP_TRIAL]);
        }
    }

    private void loadPiAndLeadOrgIntoStudyProtocolDto(Object[] piData, StudyProtocolQueryDTO spDto) {
        if (piData[1] == null) {
            spDto.setPiFullName((String) piData[SUMM_FIELD_LASTNAME]);
        } else {
            spDto.setPiFullName((String) piData[SUMM_FIELD_LASTNAME]
                                                + ", " + (String) piData[SUMM_FIELD_FIRSTNAME]);
        }
        if (piData[SUMM_FIELD_PI_ID] != null) {
            spDto.setPiId(((Number) piData[SUMM_FIELD_PI_ID]).longValue());
        }
        spDto.setLeadOrganizationName((String) piData[SUMM_FIELD_LEADORG_NAME]);
        if (piData[SUMM_FIELD_LEADORG_ID] != null) {
            spDto.setLeadOrganizationId(((Number) piData[SUMM_FIELD_LEADORG_ID]).longValue());
        }
        if (piData[SUMM_FIELD_LEAD_ORG_TRIAL_ID] != null) {
            spDto.setLeadOrganizationTrialIdentifier(piData[SUMM_FIELD_LEAD_ORG_TRIAL_ID].toString());
        }
        spDto.setLocalStudyProtocolIdentifier((String) piData[SUMM_FIELD_LOCAL_PROTOCOL_ID]);
        if (piData[SUMM_FIELD_FUND_SRC_CAT] != null) {
            spDto.setSumm4FundingSrcCategory(SummaryFourFundingCategoryCode.valueOf(
                    (String) piData[SUMM_FIELD_FUND_SRC_CAT]).getCode());
        }

    }

    private void loadNctIdentifier(Object[] piData, StudyProtocolQueryDTO spDto) {
        if (piData[SUMM_FIELD_NCT_ID] != null) {
            spDto.setNctIdentifier(piData[SUMM_FIELD_NCT_ID].toString());
        }
    }

    /**
     * Generate reporting sql.
     * @return sql.
     */
    protected String generateReportingSql() {
        return "select sc.last_name, sc.first_name, sc.crsp_id, ss.org_name, ss.org_id, "
        + "ss.local_id, sr.type_code, sos.sosSc, sos.sosSd, dws.dwsSc, "
        + "dws.dwsSd, si.siId, si.siCm, si.siOd, si.siCd, "
        + "sp.OFFICIAL_TITLE, sp.PHASE_CODE, sp.PRIMARY_PURPOSE_CODE, sp.PROPRIETARY_TRIAL_INDICATOR, "
        + "sp.RECORD_VERIFICATION_DATE, sp.CTGOV_XML_REQUIRED_INDICATOR, sp.PHASE_ADDITIONAL_QUALIFIER_CODE, "
        + "sp.DATE_LAST_CREATED, sp.AMENDMENT_NUMBER, sp.AMENDMENT_DATE, sp.SUBMISSION_NUMBER, "
        + "sp.STUDY_PROTOCOL_TYPE, sOi.extension, ss2.local_sp_indentifier as leadOrgId, nct.nctidentifier, "
        + "sp.study_source "
        + "from study_protocol AS sp "
        + "left JOIN study_otheridentifiers sOi ON sp.identifier = sOi.study_protocol_id AND sOi.root = :NCI_II_ROOT "
        + "left join (select study_protocol_identifier as spid, local_sp_indentifier as local_id, "
        + "  ro_org.name as org_name, ro_org.identifier as org_id FROM study_site " 
        + "  JOIN research_organization AS ro ON research_organization_identifier = ro.identifier "
        + "  JOIN organization AS ro_org ON ro.organization_identifier = ro_org.identifier "
        + "  WHERE study_protocol_identifier = :spId and functional_code = :leadOrgRole) AS ss "
        + "  ON ss.spid = sp.identifier " 
        + "left JOIN (select study_protocol_identifier as spid, crs_p.identifier as crsp_id, "
        + "  crs_p.first_name as first_name, crs_p.last_name as last_name from study_contact "
        + "  JOIN clinical_research_staff AS crs ON clinical_research_staff_identifier = crs.identifier "
        + "  JOIN person AS crs_p ON crs.person_identifier = crs_p.identifier "
        + "  WHERE study_protocol_identifier = :spId and role_code = :piRole) AS sc ON sc.spid = sp.identifier "
        + "left JOIN study_resourcing AS sr ON sr.study_protocol_identifier = sp.identifier and "
        + "  sr.SUMM_4_REPT_INDICATOR = true "
        + "left join (select status_code as sosSc, status_date as sosSd, study_protocol_identifier as sosSpi from "
        + "  study_overall_status where deleted=false and study_protocol_identifier = :spId "
        + "order by identifier desc limit 1) AS sos "
        + "  ON sos.sosSpi = sp.identifier "
        + "left join (select status_code as dwsSc, status_date_range_low as dwsSd, study_protocol_identifier as dwsSpi "
        + "  from document_workflow_status where study_protocol_identifier = :spId order by identifier desc limit 1) "
        + "  AS dws ON dws.dwsSpi = sp.identifier "
        + "  left join (select identifier as siId, comments as siCm, open_date as siOd, close_date as siCd, "
        + "  study_protocol_identifier as siSpi from study_inbox "
        + "  where study_protocol_identifier = :spId order by identifier desc limit 1) AS si "
        + "  ON si.siSpi = sp.identifier "
        + "left join study_site AS ss2 ON sp.identifier = ss2.study_protocol_identifier and "
        + "  ss2.functional_code = 'LEAD_ORGANIZATION' "
        + "left join (select ss3.study_protocol_identifier as spid, ss3.local_sp_indentifier as nctidentifier " 
        + "  from study_site AS ss3 "
        + "  join research_organization ro3 ON ss3.research_organization_identifier = ro3.identifier "  
        + "  join organization o3 ON ro3.organization_identifier = o3.identifier and o3.name = '"
        + PAConstants.CTGOV_ORG_NAME + "' where ss3.study_protocol_identifier = :spId and "
        + "  ss3.functional_code = 'IDENTIFIER_ASSIGNER') AS nct ON nct.spid = sp.identifier "
        + "where sp.identifier = :spId";
    }

}

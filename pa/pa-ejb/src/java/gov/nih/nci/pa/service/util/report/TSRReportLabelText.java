/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
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
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
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

package gov.nih.nci.pa.service.util.report;

/**
 * Class to store the report labels/text.
 *
 * @author kkanchinadam
 */
public class TSRReportLabelText { //NOPMD

    private static final String DESCRIPTION = "Description";
    /**
     * Information not provided.
     */
    public static final String INFORMATION_NOT_PROVIDED = "No Data Available";

    /**
     * type.
     */
    public static final String TYPE = "Type";
    
    /**
     * 
     */
    public static final String TRIAL_TYPE = "Trial Type";

    // Error Report related message strings.
    /**
     * Error message for tsr exception.
     */
    public static final String ERROR_MSG = "Unable to generate Trial Summary Report for:";
    /**
     * study title.
     */
    public static final String STUDY_TITLE = "Study Title: ";
    /**
     * study identifier.
     */
    public static final String STUDY_ID = "Study Identifier: ";
    /**
     * error contact message.
     */
    public static final String CONTACT_CTRP = "Please contact CTRP staff.";
    /**
     * error type.
     */
    public static final String ERROR_TYPE = "Error Type:";
    /**
     * error reasons.
     */
    public static final String ERROR_REASON = "Error Reason(s):";

    // Basic Information.

    /**
     * Report title.
     */
    public static final String REPORT_TITLE = "Trial Summary Report";
    /**
     * Date.
     */
    public static final String REPORT_DATE = "Date";
    /**
     * Record verification date.
     */
    public static final String REPORT_RECORD_VERIFICATION_DATE = "Record Verification Date";

    // Trial Identification
    /**
     * Trial identification table header.
     */
    public static final String TABLE_TRIAL_IDENTIFICATION = "Trial Identification";
    /**
     * trial category.
     */
    public static final String TI_TRIAL_CATEGORY = "Trial Category";
    /**
     * nci trial identifier.
     */
    public static final String TI_NCI_IDENTIFIER = "NCI Trial Identifier";
    /**
     * Lead organization trial identifier.
     */
    public static final String TI_LEAD_ORG_IDENTIFIER = "Lead Organization Identifier";
    /**
     * Trial Other identifiers.
     */
    public static final String TI_OTHER_IDENTIFIER = "Other Trial Identifiers";
    /**
     * NCT number.
     */
    public static final String TI_NCT_NUMBER = "ClinicalTrials.gov Identifier";
    /**
     * DCP Trial Identifier.
     */
    public static final String TI_DCP_IDENTIFIER = "DCP Identifier";
    
    /**
     * 
     */
    public static final String TI_CCR_IDENTIFIER = "CCR Identifier";
    
    /**
     * CTEP Trial Identifier.
     */
    public static final String TI_CTEP_IDENTIFIER = "CTEP Identifier";
    /**
     * Lead Organization.
     */
    public static final String TI_LEAD_ORGANIZATION = "Lead Organization";
    /**
     * Amendment Number.
     */
    public static final String TI_AMENDMENT_NUMBER = "Amendment Number";
    /**
     * Amendment Date.
     */
    public static final String TI_AMENDMENT_DATE = "Amendment Date";

    // General Trial Details
    /**
     * Table general trial details.
     */
    public static final String TABLE_GENERAL_TRIAL_DETAILS = "General Trial Details";
    /**
     * Official title.
     */
    public static final String GTD_OFFICIAL_TITLE = "Official Title";
    /**
     * Alternate Title.
     */
    public static final String GTD_ALTERNATE_TITLE = "Alternate Title";
    /**
     * Brief title.
     */
    public static final String GTD_BRIEF_TITLE = "Brief Title";
    /**
     * Acronym.
     */
    public static final String GTD_ACRONYM = "Acronym";
    /**
     * Brief Summary.
     */
    public static final String GTD_BRIEF_SUMMARY = "Brief Summary";
    /**
     * Detailed Description.
     */
    public static final String GTD_DETAILED_DESCRIPTION = "Detailed Description";
    /**
     * keywords.
     */
    public static final String GTD_KEYWORDS = "Keywords";
    /**
     * reporting dataset method.
     */
    public static final String GTD_REPORTING_DATASET_METHOD = "Reporting Dataset Method";
    /**
     * sponsor.
     */
    public static final String GTD_SPONSOR = "Sponsor";
    /**
     * lead organization.
     */
    public static final String GTD_LEAD_ORGANIZATION = "Lead Organization";
    /**
     * PI.
     */
    public static final String GTD_PI = "Principal Investigator";
    /**
     * Central Contact.
     */
    public static final String GTD_CENTRAL_CONTACT = "Central Contact";
    /**
     * Responsible Party.
     */
    public static final String GTD_RESPONSIBLE_PARTY = "Responsible Party";
    /**
     * Overall Official.
     */
    public static final String GTD_OVERALL_OFFICIAL = "Overall Official";
    /**
     * primary purpose.
     */
    public static final String GTD_PRIMARY_PURPOSE = "Primary Purpose";
    /**
     * primary purpose comment.
     */
    public static final String GTD_PRIMARY_PURPOSE_ADDITIONAL_QUALIFIER = "Primary Purpose Additional Qualifier";
    /**
     * primary purpose comment.
     */
    public static final String GTD_PRIMARY_PURPOSE_OTHER_TEXT = "If Primary Purpose is 'Other', describe";
    /**
     * phase.
     */
    public static final String GTD_PHASE = "Phase";
    /**
     * phase comment.
     */
    public static final String GTD_PHASE_ADDITIONAL_QUALIFIER = "Pilot Trial?";

    // Status Dates
    /**
     * table status dates.
     */
    public static final String TABLE_STATUS_DATES = "Status/Dates";
    /**
     * current trial status.
     */
    public static final String SD_STATUS = "Current Trial Status";
    /**
     * reason text.
     */
    public static final String SD_REASON_TEXT = "Reason Text";
    /**
     * trial start date.
     */
    public static final String SD_TRIAL_START_DATE = "Trial Start Date";
    /**
     * primary completion date.
     */
    public static final String SD_PRIMARY_COMPLETION_DATE = "Primary Completion Date";
    /**
     * completion date.
     */
    public static final String SD_COMPLETION_DATE = "Trial Completion Date";

    // Regulatory Information
    /**
     * table regulatory information.
     */
    public static final String TABLE_REGULATORY_INFORMATION = "Regulatory Information";
    /**
     * oversight authorities.
     */
    public static final String RI_TRIAL_OVERSIGHT_AUTHORITY = "Oversight Authorities";
    /**
     * fda regulated intervention.
     */
    public static final String RI_FDA_REGULATED_INTERVENTION = "FDA Regulated Intervention?";
    /**
     * Is Section 801.
     */
    public static final String RI_SECTION_801 = "Section 801?";
    /**
     * Is Delayed posting indicator.
     */
    public static final String RI_DELAYED_POSTING = "Delayed Posting Indicator?";
    /**
     * Is DMC appointed.
     */
    public static final String RI_DMC_APPOINTED = "DMC Appointed?";
    /**
     * Is IND/IDE study.
     */
    public static final String RI_IND_IDE_STUDY = "IND/IDE Study?";

    // Human Subject Safety
    /**
     * Table human subject safety.
     */
    public static final String TABLE_HUMAN_SUBJECT_SAFETY = "Human Subject Safety";
    /**
     * Board approval status.
     */
    public static final String HSS_BOARD_APPROVAL_STATUS = "Board Approval Status";
    /**
     * Board approval number.
     */
    public static final String HSS_BOARD_APPROVAL_NUMBER = "Board Approval Number";
    /**
     * Board.
     */
    public static final String HSS_BOARD = "Board";
    /**
     * Affiliation.
     */
    public static final String HSS_AFFILIATED_WITH = "Affiliation";

    // IND/IDE
    /**
     * Table IND/IDE.
     */
    public static final String TABLE_IND_IDE = "IND/IDE";
    /**
     * Grantor.
     */
    public static final String IND_IDE_GRANTOR = "Grantor";
    /**
     * Number.
     */
    public static final String IND_IDE_NUMBER = "Number";
    /**
     * Holder Type.
     */
    public static final String IND_IDE_HOLDER_TYPE = "Holder Type";
    /**
     * Holder.
     */
    public static final String IND_IDE_HOLDER = "Holder";
    /**
     * Expanded Access.
     */
    public static final String IND_IDE_EXPANDED_ACCESS = "Expanded Access";
    /**
     * Expanded Access Status.
     */
    public static final String IND_IDE_EXPANDED_ACCESS_STATUS = "Expanded Access Status";
    /**
     * Exempt Indicator.
     */
    public static final String IND_IDE_EXEMPT_INDICATOR = "Exempt Indicator";
    // NIH Grants.
    /**
     * Tbale NIH Grants.
     */
    public static final String TABLE_NIH_GRANTS = "NIH Grants";
    /**
     * Funding Mechanism.
     */
    public static final String NIH_GRANTS_FUNDING_MECH = "Funding Mechanism";
    /**
     * NIH Institution Code.
     */
    public static final String NIH_GRANTS_NIH_INSTITUTION_CODE = "NIH Institution Code";
    /**
     * Serial Number.
     */
    public static final String NIH_GRANTS_SERIAL_NUMBER = "Serial Number";
    /**
     * NCI Division or Program Code.
     */
    public static final String NIH_GRANTS_PROGRAM_CODE = "NCI Division/Program Code";

    // Summary 4 Information
    /**
     * Table Summary 4 information.
     */
    public static final String TABLE_SUMMARY_4_INFORMATION = "Data Table 4 Information";
    /**
     * Funding Category.
     */
    public static final String S4I_FUNDING_CATEGORY = "Funding Category";
    /**
     * Funding sponsor or source.
     */
    public static final String S4I_FUNDING_SPONSOR = "Funding Sponsor/Source";
    /**
     * Program code.
     */
    public static final String S4I_PROGRAM_CODE_TEXT = "Program Code";
    /**
     * Anatomic sites.
     */
    public static final String S4I_ANATOMIC_SITES_TEXT = "Anatomic Site Code";

    // Collaborators
    /**
     * Table collaborators.
     */
    public static final String TABLE_COLLABORATORS = "Collaborators";
    /**
     * Collaborator Name.
     */
    public static final String C_NAME = "Name";
    /**
     * Collaborator Role.
     */
    public static final String C_ROLE = "Role";

    // Disease/Condition
    /**
     * Table Disease Condition .
     */
    public static final String TABLE_DISEASE_CONDITION = "Disease/Condition";
    /**
     * Disease Name.
     */
    public static final String DC_NAME = "Name";

    // Trial Design
    /**
     * Table Trial Design.
     */
    public static final String TABLE_TRIAL_DESIGN = "Trial Design";
    /**
     * Trial Design Primary Purpose.
     */
    public static final String TD_PRIMARY_PURPOSE = "Primary Purpose";
    /**
     * Trial Design primary purpose comment.
     */
    public static final String TD_PRIMARY_PURPOSE_ADDITIONAL_QUALIFIER = "Primary Purpose Additional Qualifier";
    /**
     * Trial Design primary purpose Other text.
     */
    public static final String TD_PRIMARY_PURPOSE_OTHER_TEXT = "If Primary Purpose is 'Other', describe";    
    /**
     * Trial Design Secondary Purpose.
     */
    public static final String TD_SECONDARY_PURPOSE = "Secondary Purpose";
    /**
     * Trial Design Secondary purpose Other text.
     */
    public static final String TD_SECONDARY_PURPOSE_OTHER_TEXT = "If Secondary Purpose is 'Other', describe";
    /**
     * Trial Design phase.
     */
    public static final String TD_PHASE = "Phase";
    /**
     * Trial Phase comment.
     */
    public static final String TD_PHASE_ADDITIONAL_QUALIFIER = "Pilot Study?";
    /**
     * Trial Design Intervention Model.
     */
    public static final String TD_INTERVENTION_MODEL = "Intervention Model";
    /**
     * Trial Design Number of Arms.
     */
    public static final String TD_NUM_OF_ARMS = "Number of Arms";
    /**
     * Trial Design Masking.
     */
    public static final String TD_MASKING = "Masking";
    /**
     * Trial Design Masked Roles.
     */
    public static final String TD_MASKED_ROLES = "Masked Roles";
    /**
     * Trial Design Allocation.
     */
    public static final String TD_ALLOCATION = "Allocation";
    /**
     * Trial Design Study Classification.
     */
    public static final String TD_STUDY_CLASSIFICATION = "Classification";    
    /**
     * Trial Design Bio-specimen Retention.
     */
    public static final String BIO_SPECIMEN_RETENTION = "Bio-specimen Retention";    
    /**
     * Trial Design Bio-specimen Description.
     */
    public static final String BIO_SPECIMEN_DESCRIPTION = "Bio-specimen Description";    
    /**
     * Trial Design Number of Groups/Cohorts.
     */
    public static final String NUMBER_OF_GROUPS_COHORTS = "Number of Groups/Cohorts";
    /**
     * Trial Design Target Enrollment.
     */
    public static final String TD_TARGET_ENROLLMENT = "Target Enrollment";

    // Eligibility Criteria
    /**
     * Table Eligibility Criteria.
     */
    public static final String TABLE_ELIGIBILITY_CRITERIA = "Eligibility Criteria";
    /**
     * Study population description.
     */
    public static final String EC_STUDY_POPULATION_DESCRIPTION = "Study Population Description";
    /**
     * Accepts healthy volunteers?
     */
    public static final String EC_ACCEPTS_HEALTHY_VOLUNTEERS = "Accepts Healthy Volunteers?";
    /**
     * Gender.
     */
    public static final String EC_GENDER = "Gender";
    /**
     * Minimum age.
     */
    public static final String EC_MINIMUM_AGE = "Minimum Age";
    /**
     * Maximum age.
     */
    public static final String EC_MAXIMUM_AGE = "Maximum Age";
    /**
     * Other Criteria.
     */
    public static final String EC_OTHER_CRITERIA = "Other Criteria";
    /**
     * Inclusion criteria.
     */
    public static final String EC_INCLUSION_CRITERIA = "Inclusion Criteria";
    /**
     * Exclusion criteria.
     */
    public static final String EC_EXCLUSION_CRITERIA = "Exclusion Criteria";

    // ARM GROUP(S)
    /**
     * Table Arm Groups.
     */
    public static final String TABLE_ARM_GROUPS = "Arm/Group(s)";
    /**
     * label.
     */
    public static final String AG_LABEL = "Label";
    /**
     * Description.
     */
    public static final String AG_DESCRIPTION = DESCRIPTION;
    /**
     * Intervention.
     */
    public static final String TABLE_INTERVENTION = "Intervention(s)";
    
    /**
     * TABLE_ASSOCIATED_TRIALS
     */
    public static final String TABLE_ASSOCIATED_TRIALS = "Associated Trials";
    
    /**
     * Intervention Name.
     */
    public static final String I_INTERVENTION_NAME = "Name";
    /**
     * Intervention Alternate Name.
     */
    public static final String I_INTERVENTION_ALTERNATE_NAME = "Alternate Name";
    /**
     * Intervention Description.
     */
    public static final String I_INTERVENTION_DESCRIPTION = DESCRIPTION;

    // Primary Outcome Measures
    /**
     * Table primary outcome measures.
     */
    public static final String TABLE_PRIMARY_OUTCOME_MEASURES = "Primary Outcome Measures";
    /**
     * Title.
     */
    public static final String POM_TITLE = "Title";
    /**
     * Outcome Measure Desc.
     */
    public static final String POM_DESCRIPTION = DESCRIPTION;
    /**
     * Time Frame.
     */
    public static final String POM_TIMEFRAME = "Time Frame";
    /**
     * Is Safety Issue.
     */
    public static final String POM_SAFETY_ISSUE = "Safety Issue?";

    // Secondary Outcome Measures
    /**
     * Table Secondary outcome measures.
     */
    public static final String TABLE_SECONDARY_OUTCOME_MEASURES = "Secondary Outcome Measures";
    /**
     * Table Other Pre-specified outcome measures.
     */
    public static final String TABLE_OTHER_OUTCOME_MEASURES = "Other Pre-specified Outcome Measures";
    /**
     * Title.
     */
    public static final String SOM_TITLE = "Title";
    /**
     * Secondary Outcome Desc.
     */
    public static final String SOM_DESCRIPTION = DESCRIPTION;
    /**
     * Time Frame.
     */
    public static final String SOM_TIMEFRAME = "Time Frame";
    /**
     * Is Safety Issue.
     */
    public static final String SOM_SAFETY_ISSUE = "Safety Issue?";

    // Sub Group Stratification Criteria
    /**
     * Sub Group Stratification criteria.
     */
    public static final String TABLE_SUB_GROUP_STRATIFICATION_CRITERIA = "Sub-groups Stratification Criteria";
    /**
     * Label.
     */
    public static final String SGSC_LABEL = "Label";
    /**
     * Description.
     */
    public static final String SGSC_DESCRIPTION = DESCRIPTION;

    // Participating Sites
    /**
     * Table Participating Sites.
     */
    public static final String TABLE_PARTICIPATING_SITES = "Participating Sites";
    /**
     * Facility.
     */
    public static final String PS_FACILITY = "Facility";
    /**
     * Contact.
     */
    public static final String PS_CONTACT = "Contact";
    /**
     * Recruitment Status and Dates.
     */
    public static final String PS_RECRUITMENT_STATUS_AND_DATES = "Recruitment Status & Date(s)";
    /**
     * Target Accrual.
     */
    public static final String PS_TARGET_ACCRUAL = "Target Accrual";
    /**
     * Investigators.
     */
    public static final String PS_INVESTIGATORS = "Investigator(s)";
    /**
     * Trial Identifier.
     */
    public static final String PS_LOCAL_TRIAL_IDENTIFIER = "Trial Identifier";
     /**
     * Summary 4 sponsor or source.
     */
    public static final String PS_SUMMARY4_SPONSOR = "Summary 4 Sponsor/Source";

    /**
     * Planned Markers.
     */
    public static final String TABLE_PLANNED_MARKERS = "Markers";

    /**
     * Marker Name.
     */
    public static final String PLANNED_MARKER_NAME = "Marker Name";
    
    /**
     * Evaluation Type.
     */
    public static final String PLANNED_MARKER_EVALUATION_TYPE = "Evaluation Type";

    /**
     * Assay Type.
     */
    public static final String PLANNED_MARKER_ASSAY_TYPE = "Assay Type";

    /**
     * Biomarker Use.
     */
    public static final String PLANNED_MARKER_BIOMARKER_USE = "Biomarker Use";

    /**
     * Biomarker Purpose.
     */
    public static final String PLANNED_MARKER_BIOMARKER_PURPOSE = "Biomarker Purpose";

    /**
     * Specimen Type.
     */
    public static final String PLANNED_MARKER_SPECIMEN_TYPE = "Specimen Type";

    /**
     * Tissue Collection Method.
     */
    public static final String PLANNED_MARKER_TISSUE_COLLECTION_METHOD = "Tissue Collection Method";
    
    /**
     * 
     */
    public static final String SUBTYPE = "Non-Interventional Trial Type";
    /**
     * 
     */
    public static final String SAMPLING_METHOD_CODE = "Sampling Method Code";
    /**
     * 
     */
    public static final String STUDY_POPULATION_DESC = "Study Population Description";
    /**
     * 
     */
    public static final String TD_STUDY_MODEL = "Study Model";
    /**
     * 
     */
    public static final String TD_STUDY_MODEL_OTHER_TEXT = "Study Model Description";
    /**
     * 
     */
    public static final String TD_TIME_PERSPECTIVE = "Time Perspective";
    /**
     * 
     */
    /**
     * 
     */
    public static final String TD_TIME_PERSPECTIVE_OTHER_TEXT = "Time Perspective Description";
    /**
     * 
     */
    public static final String TRIAL_IDENTIFIER = "Trial Identifier";
    /**
     * 
     */
    public static final String IDENTIFIER_TYPE = "Identifier Type";
    /**
     * 
     */
    public static final String TRIAL_SUBTYPE = "Trial Sub-type";
    /**
     * 
     */
    public static final String OFFICIAL_TITLE = "Official Title";
    
    /**
     * 
     */
    public static final String SPACER = "    ";
    /**
     * 
     */
    public static final String PS_PO_ID = "PO ID";
}

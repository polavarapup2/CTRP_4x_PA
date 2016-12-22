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
package gov.nih.nci.pa.service.util.report;

import gov.nih.nci.pa.dto.StudyIdentifierDTO;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.service.PAException;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.ListItem;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;

/**
 * Abstract Class for generating the TSR Report.
 *
 * @author kkanchinadam
 */
public abstract class AbstractTsrReportGenerator {
    private static final int INT_2 = 2;
    private static final int INT_10 = 10;
    private static final int INT_12 = 12;
    private static final int INT_14 = 14;
    private static final int INT_20 = 20;
    private static final int INT_178 = 178;
    private static final int INT_229 = 229;
    private static final int INT_0XA5 = 0xa5;
    private static final int INT_0X2A = 0x2a;
    private static final int INT_0X66 = 0x66;
    private static final int INT_0X99 = 0x99;
    private static final float FLOAT_MINUS_2 = -2f;
    private static final float FLOAT_POINT_5 = 0.5f;
    private static final float FLOAT_1 = 1.0f;
    private static final float FLOAT_100 = 100.0f;
    private static final String PERC_100 = "100%";
    
    private static final String SPACE = " ";
    private static final String HYPHEN = "-";

    private final Font outerTableHeaderCellFont = FontFactory.getFont(FontFactory.HELVETICA, INT_14, Font.BOLD,
            new Color(INT_0XA5, INT_0X2A, INT_0X2A));
    private final Font innerTableHeaderCellFont = FontFactory.getFont(FontFactory.HELVETICA, INT_12, Font.BOLD,
            new Color(INT_0XA5, INT_0X2A, INT_0X2A));
    private final Font separatorRowCellFont = FontFactory.getFont(FontFactory.HELVETICA, INT_12, Font.BOLD,
            Color.BLACK);

    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private Document reportDocument;
    private boolean proprietaryTrial;
    private boolean nonInterventionalTrial;
    private TSRReport tsrReport;
    private TSRErrorReport tsrErrorReport;
    private TSRReportTrialIdentification trialIdentification;
    private TSRReportGeneralTrialDetails generalTrialDetails;
    private TSRReportStatusDate statusDate;
    private TSRReportRegulatoryInformation regulatoryInformation;
    private TSRReportHumanSubjectSafety humanSubjectSafety;
    private List<TSRReportIndIde> indIdes = new ArrayList<TSRReportIndIde>();
    private List<TSRReportNihGrant> nihGrants = new ArrayList<TSRReportNihGrant>();
    private TSRReportSummary4Information summary4Information;
    private List<TSRReportCollaborator> collaborators = new ArrayList<TSRReportCollaborator>();
    private List<TSRReportDiseaseCondition> diseaseConditions = new ArrayList<TSRReportDiseaseCondition>();
    private TSRReportTrialDesign trialDesign;
    private TSRReportEligibilityCriteria eligibilityCriteria;
    private List<TSRReportArmGroup> armGroups = new ArrayList<TSRReportArmGroup>();
    private List<TSRReportOutcomeMeasure> primaryOutcomeMeasures = new ArrayList<TSRReportOutcomeMeasure>();
    private List<TSRReportOutcomeMeasure> secondaryOutcomeMeasures = new ArrayList<TSRReportOutcomeMeasure>();
    private List<TSRReportOutcomeMeasure> otherOutcomeMeasures = new ArrayList<TSRReportOutcomeMeasure>();
    private List<TSRReportSubGroupStratificationCriteria> sgsCriterias =
        new ArrayList<TSRReportSubGroupStratificationCriteria>();
    private List<TSRReportParticipatingSite> participatingSites = new ArrayList<TSRReportParticipatingSite>();
    private List<TSRReportIntervention> interventions = new ArrayList<TSRReportIntervention>();
    private List<TSRReportPlannedMarker> plannedMarkers = new ArrayList<TSRReportPlannedMarker>();
    private final List<TSRReportAssociatedTrial> associatedTrials = new ArrayList<TSRReportAssociatedTrial>();

    /**
     * @param tsrReport basic information about the report.
     * @param proprietaryTrial indicator whether the trial is proprietary or not.
     */
    public AbstractTsrReportGenerator(TSRReport tsrReport, boolean proprietaryTrial) {
        super();
        this.tsrReport = tsrReport;
        this.proprietaryTrial = proprietaryTrial;
    }

    /**
     * @param tsrReport basic information about the report.
     */
    public AbstractTsrReportGenerator(TSRReport tsrReport) {
        super();
        this.tsrReport = tsrReport;
    }

    /**
     * @param tsrErrorReport error information during report generation.
     */
    public AbstractTsrReportGenerator(TSRErrorReport tsrErrorReport) {
        super();
        this.tsrErrorReport = tsrErrorReport;
    }

    /**
     * Default constructor.
     */
    public AbstractTsrReportGenerator() {
        super();
    }

    /**
     * @return the proprietaryTrial
     */
    public boolean isProprietaryTrial() {
        return proprietaryTrial;
    }

    /**
     * @param proprietaryTrial whether the trial is proprietary or not
     */
    public void setProprietaryTrial(boolean proprietaryTrial) {
        this.proprietaryTrial = proprietaryTrial;
    }

    /**
     * Generates the TSR Report in the appropriate format.
     * @return baos the byte array output stream.
     * @throws PAException the exception.
     */
    public abstract ByteArrayOutputStream generateTsrReport() throws PAException;

    /**
     * Generates the TSR error report in the appropriate format.
     * @return the byte array output stream.
     * @throws PAException on error
     */
    public abstract ByteArrayOutputStream generateErrorReport() throws PAException;


    /**
     * @throws DocumentException on error
     */
    protected void generateReport() throws DocumentException {
        getReportDocument().open();
        addReportTitle();
        addReportDate();
        addReportRecordVerificationDate();
        getReportDocument().add(getLineBreak());
        
        addTrialIdentificationTable();
        addTrialAssociationsTable();
        addGeneralTrialDetailsTable();
        addStatusDatesTable();
        addRegulatoryInformationTable();
        addHumanSubjectSafetyTable();
        addIndIdeTable();
        addNihGrantsTable();
        addSummary4InformationTable();
        addCollaboratorsTable();
        addDiseaseConditionTable();
        addTrialDesignTable();
        addEligibilityCriteriaTable();
        addInterventionsTable();
        addArmGroupsTable();
        addPrimaryOutcomesMeasuresTable();
        addSecondaryOutcomesMeasuresTable();
        addOtherOutcomesMeasuresTable();
        addSubGroupsStratificationCriteriaTable();
        addPlannedMarkers();
        addParticipatingSitesTable();
         
        getReportDocument().close();
    }

    /**
     * Generates a line break.
     * @return the line break
     */
    protected Paragraph getLineBreak() {
        return new Paragraph("\n");
    }

    /**
     * Add the error report title.
     * @throws DocumentException on error
     */
    protected void addErrorReportTitle() throws DocumentException {
        if (getTsrErrorReport() != null && getTsrErrorReport().getReportTitle() != null) {
            addTitleToDocument(getTsrErrorReport().getReportTitle());
        }
    }

    private void addReportTitle() throws DocumentException {
        if (getTsrReport() != null && getTsrReport().getTitle() != null) {
            addTitleToDocument(getTsrReport().getTitle());
        }
    }

    private void addTitleToDocument(String title) throws DocumentException {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, INT_20, Font.BOLD,
                new Color(INT_0X66, INT_0X66, INT_0X99));
        Paragraph reportHeader = new Paragraph("", headerFont);
        reportHeader.setAlignment(Element.ALIGN_CENTER);
        Chunk reportTitle = new Chunk(title);
        reportTitle.setUnderline(FLOAT_POINT_5, FLOAT_MINUS_2);
        reportHeader.add(reportTitle);
        reportDocument.add(reportHeader);
    }

    private void addReportDate() throws DocumentException {
        if (getTsrReport() != null && getTsrReport().getDate() != null) {
            Paragraph reportHeader = new Paragraph();
            reportHeader.setAlignment(Element.ALIGN_LEFT);
            Chunk reportDateText = new Chunk(TSRReportLabelText.REPORT_DATE + ": ", FontFactory.getFont(
                    FontFactory.HELVETICA, INT_14, Font.BOLD, new Color(INT_0X66, INT_0X66, INT_0X99)));
            reportHeader.add(reportDateText);

            Chunk reportDateValue = new Chunk(getTsrReport().getDate(), FontFactory.getFont(FontFactory.HELVETICA,
                    INT_14, Font.NORMAL, new Color(INT_0X66, INT_0X66, INT_0X99)));
            reportHeader.add(reportDateValue);
            reportDocument.add(reportHeader);
        }
    }

    private void addReportRecordVerificationDate() throws DocumentException {
        if (getTsrReport() != null && getTsrReport().getRecordVerificationDate() != null) {
            Paragraph reportHeader = new Paragraph();
            reportHeader.setAlignment(Element.ALIGN_LEFT);
            Chunk reportDateText = new Chunk(TSRReportLabelText.REPORT_RECORD_VERIFICATION_DATE + ": ", FontFactory
                    .getFont(FontFactory.HELVETICA, INT_14, Font.BOLD,
                            new Color(INT_0X66, INT_0X66, INT_0X99)));
            reportHeader.add(reportDateText);

            Chunk reportDateValue = new Chunk(getTsrReport().getRecordVerificationDate(), FontFactory.getFont(
                    FontFactory.HELVETICA, INT_14, Font.NORMAL, new Color(INT_0X66, INT_0X66, INT_0X99)));
            reportHeader.add(reportDateValue);
            reportDocument.add(reportHeader);
        }
    }

    /**
     * Adds the error report information.
     * @throws DocumentException on error
     */
    protected void addErrorInformation() throws DocumentException {
        if (getTsrErrorReport() != null) {
            Font font = FontFactory.getFont(FontFactory.HELVETICA, INT_12, Font.NORMAL, Color.BLACK);
            Font fontUnderline = FontFactory.getFont(FontFactory.HELVETICA, INT_12, Font.UNDERLINE, Color.BLACK);
            Paragraph paragraph = new Paragraph(TSRReportLabelText.ERROR_MSG, font);
            reportDocument.add(paragraph);

            Chunk listSymbol = new Chunk("\u2022", font);

            paragraph = new Paragraph("", font);
            paragraph.setIndentationLeft(INT_10);
            com.lowagie.text.List textList = new com.lowagie.text.List(false, INT_10);
            textList.setListSymbol(listSymbol);
            textList.add(TSRReportLabelText.STUDY_ID + getTsrErrorReport().getStudyId());
            textList.add(TSRReportLabelText.STUDY_TITLE + getTsrErrorReport().getStudyTitle());
            paragraph.add(textList);
            reportDocument.add(paragraph);

            paragraph = new Paragraph(TSRReportLabelText.CONTACT_CTRP, font);
            reportDocument.add(paragraph);
            reportDocument.add(getLineBreak());

            paragraph = new Paragraph(TSRReportLabelText.ERROR_TYPE, fontUnderline);
            reportDocument.add(paragraph);
            paragraph = new Paragraph(getTsrErrorReport().getErrorType(), font);
            paragraph.setIndentationLeft(INT_10);
            reportDocument.add(paragraph);
            reportDocument.add(getLineBreak());

            paragraph = new Paragraph(TSRReportLabelText.ERROR_REASON, fontUnderline);
            reportDocument.add(paragraph);
            paragraph = new Paragraph("", font);
            paragraph.setIndentationLeft(INT_10);
            textList = new com.lowagie.text.List(false, INT_10);
            textList.setListSymbol(listSymbol);
            for (String reason : getTsrErrorReport().getErrorReasons()) {
                textList.add(reason);
            }
            paragraph.add(textList);
            reportDocument.add(paragraph);
        }
    }

    private void addTrialIdentificationTable() throws DocumentException {
        final TSRReportTrialIdentification trialInfo = getTrialIdentification();
        final TSRReportGeneralTrialDetails gtd = getGeneralTrialDetails();
        if (trialInfo != null) {
            Table table = getOuterTable(
                    TSRReportLabelText.TABLE_TRIAL_IDENTIFICATION, false, // CHECKSTYLE:OFF
                    new float[] {1.3f, 1.7f}); // CHECKSTYLE:ON 
            addTableRow(table, TSRReportLabelText.TI_TRIAL_CATEGORY, trialInfo.getTrialCategory());            
            addTableRow(table, TSRReportLabelText.TRIAL_TYPE, gtd.getType());
            if (StringUtils.isNotBlank(gtd.getSubType())) {
                addTableRow(table, TSRReportLabelText.SUBTYPE,
                        gtd.getSubType());
            }
            addTableRow(table, TSRReportLabelText.TI_NCI_IDENTIFIER, trialInfo.getNciIdentifier());
            
            table.addCell(getItemCell(TSRReportLabelText.TI_OTHER_IDENTIFIER, 2));
            
            addTableRow(table, TSRReportLabelText.SPACER
                    + TSRReportLabelText.TI_LEAD_ORG_IDENTIFIER,
                    trialInfo.getLeadOrgIdentifier());
            addTableRow(table, TSRReportLabelText.SPACER
                    + TSRReportLabelText.TI_NCT_NUMBER, trialInfo.getNctNumber());
            if (!isProprietaryTrial()) {
                addTableRow(table, TSRReportLabelText.SPACER
                        + TSRReportLabelText.TI_DCP_IDENTIFIER,
                        trialInfo.getDcpIdentifier());
                addTableRow(table, TSRReportLabelText.SPACER
                        + TSRReportLabelText.TI_CTEP_IDENTIFIER,
                        trialInfo.getCtepIdentifier());
            }
            addTableRow(table, TSRReportLabelText.SPACER
                    + TSRReportLabelText.TI_CCR_IDENTIFIER,
                    trialInfo.getCcrIdentifier());
            for (StudyIdentifierDTO dto : trialInfo.getIdentifiers()) {
                if (!dto.getType().isStudySiteBased()) {
                    addTableRow(table, TSRReportLabelText.SPACER
                            + dto.getType().getCode(), dto.getValue());
                }
            }

            if (isProprietaryTrial()) {
                addTableRow(table, TSRReportLabelText.TI_LEAD_ORGANIZATION, trialInfo
                        .getLeadOrganization());
            } else {      
                addTableRow(table, TSRReportLabelText.TI_AMENDMENT_NUMBER, trialInfo
                        .getAmendmentNumber());
                addTableRow(table, TSRReportLabelText.TI_AMENDMENT_DATE, trialInfo.getAmendmentDate());
            }

            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }

    private void addGeneralTrialDetailsTable() throws DocumentException {
        if (getGeneralTrialDetails() != null) {
            Table table = getOuterTable(TSRReportLabelText.TABLE_GENERAL_TRIAL_DETAILS, false);
            
            addTableRow(table, TSRReportLabelText.TYPE, getGeneralTrialDetails().getType());
            addTableRow(table, TSRReportLabelText.GTD_OFFICIAL_TITLE, getGeneralTrialDetails().getOfficialTitle());
            if (!getGeneralTrialDetails().getStudyAlternateTitle().isEmpty()) {
                List<String> altTitleList = getGeneralTrialDetails().getStudyAlternateTitle();
                for (int i = 1; i <= altTitleList.size(); i++) {
                    addTableRow(table, TSRReportLabelText.GTD_ALTERNATE_TITLE 
                           + " " + i, getGeneralTrialDetails().getStudyAlternateTitle().get(i - 1));
                }
            }
            addTableRow(table, TSRReportLabelText.GTD_BRIEF_TITLE, getGeneralTrialDetails().getBriefTitle());
            addTableRow(table, TSRReportLabelText.GTD_ACRONYM, getGeneralTrialDetails().getAcronym());
            addTableRow(table, TSRReportLabelText.GTD_BRIEF_SUMMARY, getGeneralTrialDetails().getBriefSummary(), 2);
            addTableRow(table, TSRReportLabelText.GTD_DETAILED_DESCRIPTION, getGeneralTrialDetails()
                    .getDetailedDescription(), 2);
            addTableRow(table, TSRReportLabelText.GTD_KEYWORDS, getGeneralTrialDetails().getKeywords());
            addTableRow(table, TSRReportLabelText.GTD_REPORTING_DATASET_METHOD, getGeneralTrialDetails()
                    .getReportingDatasetMethod());
            addTableRow(table, TSRReportLabelText.GTD_SPONSOR, getGeneralTrialDetails().getSponsor());
            addTableRow(table, TSRReportLabelText.GTD_LEAD_ORGANIZATION, getGeneralTrialDetails()
                    .getLeadOrganization());
            addTableRow(table, TSRReportLabelText.GTD_PI, getGeneralTrialDetails().getPi());
            addTableRow(table, TSRReportLabelText.GTD_RESPONSIBLE_PARTY, getGeneralTrialDetails()
                    .getResponsibleParty());
            addTableRow(table, TSRReportLabelText.GTD_OVERALL_OFFICIAL, getGeneralTrialDetails()
                    .getOverallOfficial());
            addTableRow(table, TSRReportLabelText.GTD_CENTRAL_CONTACT,
                    getGeneralTrialDetails().getCentralContact());
                    reportDocument.add(table);
                    
            reportDocument.add(getLineBreak());
        }
    }

    private void addStatusDatesTable() throws DocumentException {
        if (getStatusDate() != null) {
            Table table = getOuterTable(TSRReportLabelText.TABLE_STATUS_DATES, false);
            addTableRow(table, TSRReportLabelText.SD_STATUS, getStatusDate().getCurrentTrialStatus());
            addTableRow(table, TSRReportLabelText.SD_REASON_TEXT, getStatusDate().getReasonText());
            addTableRow(table, TSRReportLabelText.SD_TRIAL_START_DATE, getStatusDate().getTrialStartDate());
            addTableRow(table, TSRReportLabelText.SD_PRIMARY_COMPLETION_DATE, getStatusDate()
                    .getPrimaryCompletionDate());
            addTableRow(table, TSRReportLabelText.SD_COMPLETION_DATE, getStatusDate().getCompletionDate());
            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }

    private void addRegulatoryInformationTable() throws DocumentException {
        final TSRReportRegulatoryInformation regInfo = getRegulatoryInformation();
        if (regInfo != null) {
            Table table = getOuterTable(TSRReportLabelText.TABLE_REGULATORY_INFORMATION, false);
            addTableRow(table, TSRReportLabelText.RI_TRIAL_OVERSIGHT_AUTHORITY, regInfo.getTrialOversightAuthority());
            addTableRow(table, TSRReportLabelText.RI_FDA_REGULATED_INTERVENTION, regInfo.getFdaRegulatedIntervention());
            if (BooleanUtils.toBoolean(regInfo.getFdaRegulatedIntervention())) {
                addTableRow(table, TSRReportLabelText.RI_SECTION_801, regInfo.getSection801());
                if (BooleanUtils.toBoolean(regInfo.getSection801())) {
                    addTableRow(table, TSRReportLabelText.RI_DELAYED_POSTING, regInfo.getDelayedPosting());
                }
            }
            addTableRow(table, TSRReportLabelText.RI_DMC_APPOINTED, regInfo.getDmcAppointed());
            addTableRow(table, TSRReportLabelText.RI_IND_IDE_STUDY, regInfo.getIndIdeStudy());
            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }

    private void addHumanSubjectSafetyTable() throws DocumentException {
        if (getHumanSubjectSafety() != null && StringUtils.isNotBlank(getHumanSubjectSafety().getBoard())) {
            Table table = getOuterTable(TSRReportLabelText.TABLE_HUMAN_SUBJECT_SAFETY, false);
            addTableRow(table, TSRReportLabelText.HSS_BOARD_APPROVAL_STATUS, getHumanSubjectSafety()
                    .getBoardApprovalStatus());
            addTableRow(table, TSRReportLabelText.HSS_BOARD_APPROVAL_NUMBER, getHumanSubjectSafety()
                    .getBoardApprovalNumber());
            addTableRow(table, TSRReportLabelText.HSS_BOARD, getHumanSubjectSafety().getBoard());
            addTableRow(table, TSRReportLabelText.HSS_AFFILIATED_WITH, getHumanSubjectSafety().getAffiliation());

            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }

    private void addIndIdeTable() throws DocumentException {
        if (CollectionUtils.isNotEmpty(getIndIdes())) {
            Table table = getOuterTable(TSRReportLabelText.TABLE_IND_IDE, true);
            Table indIdesTable = getInnerTable(new ArrayList<String>(Arrays.asList(TSRReportLabelText.TYPE,
                    TSRReportLabelText.IND_IDE_GRANTOR, TSRReportLabelText.IND_IDE_NUMBER,
                    TSRReportLabelText.IND_IDE_HOLDER_TYPE, TSRReportLabelText.IND_IDE_HOLDER,
                    TSRReportLabelText.IND_IDE_EXPANDED_ACCESS, TSRReportLabelText.IND_IDE_EXPANDED_ACCESS_STATUS,
                    TSRReportLabelText.IND_IDE_EXEMPT_INDICATOR)));
            for (TSRReportIndIde indIde : getIndIdes()) {
                indIdesTable.addCell(getItemValueCell(indIde.getType()));
                indIdesTable.addCell(getItemValueCell(indIde.getGrantor()));
                indIdesTable.addCell(getItemValueCell(indIde.getNumber()));
                indIdesTable.addCell(getItemValueCell(indIde.getHolderType()));
                indIdesTable.addCell(getItemValueCell(indIde.getHolder()));
                indIdesTable.addCell(getItemValueCell(indIde.getExpandedAccess()));
                indIdesTable.addCell(getItemValueCell(indIde.getExpandedAccessStatus()));
                indIdesTable.addCell(getItemValueCell(indIde.getExemptIndicator()));
            }
            table.insertTable(indIdesTable);
            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }

    private void addNihGrantsTable() throws DocumentException {
        if (CollectionUtils.isNotEmpty(getNihGrants())) {
            Table table = getOuterTable(TSRReportLabelText.TABLE_NIH_GRANTS, true);
            Table nihGrantsTable = getInnerTable(new ArrayList<String>(Arrays.asList(
                    TSRReportLabelText.NIH_GRANTS_FUNDING_MECH, TSRReportLabelText.NIH_GRANTS_NIH_INSTITUTION_CODE,
                    TSRReportLabelText.NIH_GRANTS_SERIAL_NUMBER, TSRReportLabelText.NIH_GRANTS_PROGRAM_CODE)));
            for (TSRReportNihGrant nihGrant : getNihGrants()) {
                nihGrantsTable.addCell(getItemValueCell(nihGrant.getFundingMechanism()));
                nihGrantsTable.addCell(getItemValueCell(nihGrant.getNihInstitutionCode()));
                nihGrantsTable.addCell(getItemValueCell(nihGrant.getSerialNumber()));
                nihGrantsTable.addCell(getItemValueCell(nihGrant.getProgramCode()));
            }
            table.insertTable(nihGrantsTable);
            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }

    private void addSummary4InformationTable() throws DocumentException {
        if (getSummary4Information() != null) {
            Table table = getOuterTable(TSRReportLabelText.TABLE_SUMMARY_4_INFORMATION, true);
            Table innerTable = getInnerTable(new ArrayList<String>());
            addTableRow(innerTable, TSRReportLabelText.S4I_FUNDING_CATEGORY,
                    getSummary4Information().getFundingCategory());
            addTableRow(innerTable, TSRReportLabelText.S4I_FUNDING_SPONSOR,
                    getSummary4Information().getFundingSponsor());
            addTableRow(innerTable, TSRReportLabelText.S4I_PROGRAM_CODE_TEXT,
                    getSummary4Information().getProgramCode());
            table.insertTable(innerTable);
            if (CollectionUtils.isNotEmpty(getSummary4Information().getAnatomicSites())) {
                Table anatomicSiteTable = getInnerTable(Arrays.asList(TSRReportLabelText.S4I_ANATOMIC_SITES_TEXT));
                for (String site : getSummary4Information().getAnatomicSites()) {
                  anatomicSiteTable.addCell(getItemValueCell(site));
                }
                table.insertTable(anatomicSiteTable);
            }
            reportDocument.add(table);
            reportDocument.add(getLineBreak());

        }
    }

    private void addCollaboratorsTable() throws DocumentException {
        if (CollectionUtils.isNotEmpty(getCollaborators())) {
            Table table = getOuterTable(TSRReportLabelText.TABLE_COLLABORATORS, true);
            Table collaboratorsTable = getInnerTable(Arrays.asList(TSRReportLabelText.C_NAME,
                    TSRReportLabelText.C_ROLE));
            for (TSRReportCollaborator colab : getCollaborators()) {
                collaboratorsTable.addCell(getItemValueCell(colab.getName()));
                collaboratorsTable.addCell(getItemValueCell(colab.getRole()));
            }
            table.insertTable(collaboratorsTable);
            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }

    private void addDiseaseConditionTable() throws DocumentException {
        Table table = getOuterTable(TSRReportLabelText.TABLE_DISEASE_CONDITION, true);
        Table dcTable = getInnerTable(Arrays.asList(TSRReportLabelText.DC_NAME));
        if (CollectionUtils.isNotEmpty(getDiseaseConditions())) {
            for (TSRReportDiseaseCondition dc : getDiseaseConditions()) {
                dcTable.addCell(getItemValueCell(dc.getName()));
            }
        } else {
            dcTable.addCell(getItemValueCell(TSRReportLabelText.INFORMATION_NOT_PROVIDED));
        }
        table.insertTable(dcTable);
        reportDocument.add(table);
        reportDocument.add(getLineBreak());
    }

    private void addTrialDesignTable() throws DocumentException {
        if (getTrialDesign() != null) {
            Table table = getOuterTable(TSRReportLabelText.TABLE_TRIAL_DESIGN, false);
            addTableRow(table, TSRReportLabelText.TYPE, getTrialDesign().getType());
            addTableRow(table, TSRReportLabelText.SUBTYPE, getTrialDesign().getStudySubtypeCode());
            addPrimaryPurposeRow(table, TSRReportLabelText.TD_PRIMARY_PURPOSE, getTrialDesign().getPrimaryPurpose(),
                    getTrialDesign().getPrimaryPurposeOtherText());
            addSecondaryPurposeRow(table, TSRReportLabelText.TD_SECONDARY_PURPOSE, 
                    getTrialDesign().getSecondaryPurpose(), getTrialDesign().getSecondaryPurposeOtherText());
            addTableRow(table, TSRReportLabelText.TD_PHASE, getTrialDesign().getPhase());
            if (StringUtils.isNotEmpty(getTrialDesign().getPhase())
                    && PhaseCode.NA.getCode().equals(getTrialDesign().getPhase())) {
                addPhaseAdditionalQualifierRow(table, TSRReportLabelText.TD_PHASE_ADDITIONAL_QUALIFIER, 
                        getTrialDesign().getPhaseAdditonalQualifier());
            }
            addTableRow(table, TSRReportLabelText.TD_STUDY_MODEL, getTrialDesign().getStudyModel());
            addTableRow(table, TSRReportLabelText.TD_STUDY_MODEL_OTHER_TEXT,
                    getTrialDesign().getStudyModelOtherText());
            addTableRow(table, TSRReportLabelText.TD_TIME_PERSPECTIVE, getTrialDesign().getTimePerspective());
            addTableRow(table, TSRReportLabelText.TD_TIME_PERSPECTIVE_OTHER_TEXT,
                    getTrialDesign().getTimePerspectiveOtherText());            
            
            addTableRow(table, TSRReportLabelText.TD_INTERVENTION_MODEL, getTrialDesign().getInterventionModel());
            addTableRow(table, TSRReportLabelText.TD_NUM_OF_ARMS, getTrialDesign().getNumberOfArms());
            addTableRow(table, TSRReportLabelText.TD_MASKING, getTrialDesign().getMasking());
            addTableRow(table, TSRReportLabelText.TD_MASKED_ROLES, getTrialDesign().getMaskedRoles());
            addTableRow(table, TSRReportLabelText.TD_ALLOCATION, getTrialDesign().getAllocation());
            addTableRow(table, TSRReportLabelText.TD_STUDY_CLASSIFICATION, getTrialDesign().getStudyClassification());
            addTableRow(table, TSRReportLabelText.BIO_SPECIMEN_RETENTION, 
                    getTrialDesign().getBiospecimenRetentionCode());
            addTableRow(table, TSRReportLabelText.BIO_SPECIMEN_DESCRIPTION, 
                    getTrialDesign().getBiospecimenDescription());
            addTableRow(table, TSRReportLabelText.NUMBER_OF_GROUPS_COHORTS, getTrialDesign().getNumberOfGroups());
            addTableRow(table, TSRReportLabelText.TD_TARGET_ENROLLMENT, getTrialDesign().getTargetEnrollment());
            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }

    private void addSecondaryPurposeRow(Table table, String label, String secondaryPurpose, String secPurposeOtherText)
            throws BadElementException {
        if (!StringUtils.isEmpty(secondaryPurpose)) {
            StringBuffer secondaryPurposeBuffer = new StringBuffer(secondaryPurpose);
            if (StringUtils.isNotEmpty(secPurposeOtherText)) {
                secondaryPurposeBuffer.append(", ").append(secPurposeOtherText);
            }
            addTableRow(table, label, secondaryPurposeBuffer.toString());
        }
    }

    private void addEligibilityCriteriaTable() throws DocumentException {
        final TSRReportEligibilityCriteria ec = getEligibilityCriteria();
        if (ec != null) {
            Table table = getOuterTable(TSRReportLabelText.TABLE_ELIGIBILITY_CRITERIA, true);
            Table innerTable = getInnerTable(new ArrayList<String>());
            if (isNonInterventionalTrial()) {
                addTableRow(innerTable,
                        TSRReportLabelText.SAMPLING_METHOD_CODE,
                        ec.getSampleMethodCode());
                addTableRow(innerTable,
                        TSRReportLabelText.STUDY_POPULATION_DESC,
                        ec
                                .getStudyPopulationDescription());
            }
            addTableRow(innerTable, TSRReportLabelText.EC_ACCEPTS_HEALTHY_VOLUNTEERS,
                    ec.getAcceptsHealthyVolunteers());
            addTableRow(innerTable, TSRReportLabelText.EC_GENDER, ec.getGender());
            addTableRow(innerTable, TSRReportLabelText.EC_MINIMUM_AGE, ec.getMinimumAge());
            addTableRow(innerTable, TSRReportLabelText.EC_MAXIMUM_AGE, ec.getMaximumAge());
            addTableSeparatorRow(innerTable, 2);
            table.insertTable(innerTable);

            // inclusion criteria
            if (ec.getInclusionCriteria().size() > 0) {
                Table inclusionCriteriaTable = getInnerTable(Arrays.asList(TSRReportLabelText.EC_INCLUSION_CRITERIA));
                inclusionCriteriaTable.addCell(getItemValueCell(ec.getInclusionCriteria()));
                addTableSeparatorRow(inclusionCriteriaTable, 1);
                table.insertTable(inclusionCriteriaTable);
            }

            // exclusion criteria
            if (ec.getExclusionCriteria().size() > 0) {
                Table exclusionCriteriaTable = getInnerTable(Arrays.asList(TSRReportLabelText.EC_EXCLUSION_CRITERIA));
                exclusionCriteriaTable.addCell(getItemValueCell(ec.getExclusionCriteria()));
                addTableSeparatorRow(exclusionCriteriaTable, 1);
                table.insertTable(exclusionCriteriaTable);
            }

            // other criteria
            if (ec.getOtherCriteria().size() > 0) {
                Table otherCriteriaTable = getInnerTable(Arrays.asList(TSRReportLabelText.EC_OTHER_CRITERIA));
                otherCriteriaTable.addCell(getItemValueCell(ec.getOtherCriteria()));
                addTableSeparatorRow(otherCriteriaTable, 1);
                table.insertTable(otherCriteriaTable);
            }
            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }

    private void addArmGroupsTable() throws DocumentException {
        if (CollectionUtils.isNotEmpty(getArmGroups())) {
            Table table = getOuterTable(TSRReportLabelText.TABLE_ARM_GROUPS, true);
            for (TSRReportArmGroup armGroup : getArmGroups()) {
                Table innerTable = getInnerTable(new ArrayList<String>());
                addTableSeparatorRow(innerTable, 2);
                addTableRow(innerTable, TSRReportLabelText.TYPE, armGroup.getType());
                addTableRow(innerTable, TSRReportLabelText.AG_LABEL, armGroup.getLabel());
                addTableRow(innerTable, TSRReportLabelText.AG_DESCRIPTION, armGroup.getDescription());
                addTableSeparatorRow(innerTable, 2, TSRReportLabelText.TABLE_INTERVENTION);
                table.insertTable(innerTable);
                if (armGroup.getInterventions().size() > 0) {
                    Table interventionTable = getInnerTable(Arrays.asList(
                            TSRReportLabelText.TYPE, TSRReportLabelText.I_INTERVENTION_NAME,
                            TSRReportLabelText.I_INTERVENTION_ALTERNATE_NAME,
                            TSRReportLabelText.I_INTERVENTION_DESCRIPTION));
                    for (TSRReportIntervention intervention : armGroup.getInterventions()) {
                        interventionTable.addCell(getItemValueCell(intervention.getType()));
                        interventionTable.addCell(getItemValueCell(intervention.getName()));
                        interventionTable.addCell(getItemValueCell(intervention.getAlternateName()));
                        interventionTable.addCell(getItemValueCell(intervention.getDescription()));
                    }
                    table.insertTable(interventionTable);
                }
                addTableSeparatorRow(table, 2);
            }
            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }

    private void addInterventionsTable() throws DocumentException {
        if (CollectionUtils.isNotEmpty(getInterventions())) {
            Table table = getOuterTable(TSRReportLabelText.TABLE_INTERVENTION, true);
            Table interventionTable = getInnerTable(Arrays.asList(TSRReportLabelText.TYPE,
                    TSRReportLabelText.I_INTERVENTION_NAME, TSRReportLabelText.I_INTERVENTION_ALTERNATE_NAME,
                    TSRReportLabelText.I_INTERVENTION_DESCRIPTION));
            for (TSRReportIntervention intv : getInterventions()) {
                interventionTable.addCell(getItemValueCell(intv.getType()));
                interventionTable.addCell(getItemValueCell(intv.getName()));
                interventionTable.addCell(getItemValueCell(intv.getAlternateName()));
                interventionTable.addCell(getItemValueCell(intv.getDescription()));
            }
            table.insertTable(interventionTable);
            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }
    
    private void addTrialAssociationsTable() throws DocumentException {
        if (CollectionUtils.isNotEmpty(getAssociatedTrials())) {
            Table table = getOuterTable(
                    TSRReportLabelText.TABLE_ASSOCIATED_TRIALS, true);
            Table trialAssociationsTable = getInnerTable(Arrays.asList(
                    TSRReportLabelText.TRIAL_IDENTIFIER,
                    TSRReportLabelText.IDENTIFIER_TYPE,
                    TSRReportLabelText.TRIAL_TYPE,
                    TSRReportLabelText.TRIAL_SUBTYPE,
                    TSRReportLabelText.OFFICIAL_TITLE));
            for (TSRReportAssociatedTrial trial : getAssociatedTrials()) {
                trialAssociationsTable.addCell(getItemValueCell(trial
                        .getTrialIdentifier()));
                trialAssociationsTable.addCell(getItemValueCell(trial
                        .getIdentifierType()));
                trialAssociationsTable
                        .addCell(getItemValueCell(trial.getTrialType()));
                trialAssociationsTable.addCell(getItemValueCell(trial
                        .getTrialSubType()));
                trialAssociationsTable.addCell(getItemValueCell(trial
                        .getOfficialTitle()));
            }
            table.insertTable(trialAssociationsTable);
            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }

    private void addPrimaryOutcomesMeasuresTable() throws DocumentException {
        if (CollectionUtils.isNotEmpty(getPrimaryOutcomeMeasures())) {
            Table table = getOuterTable(TSRReportLabelText.TABLE_PRIMARY_OUTCOME_MEASURES, true);
            Table pomTable = getInnerTable(Arrays.asList(TSRReportLabelText.POM_TITLE,
                    TSRReportLabelText.POM_DESCRIPTION, TSRReportLabelText.POM_TIMEFRAME,
                    TSRReportLabelText.POM_SAFETY_ISSUE));
            for (TSRReportOutcomeMeasure pom : getPrimaryOutcomeMeasures()) {
                pomTable.addCell(getItemValueCell(pom.getTitle()));
                pomTable.addCell(getItemValueCell(pom.getDescription()));
                pomTable.addCell(getItemValueCell(pom.getTimeFrame()));
                pomTable.addCell(getItemValueCell(pom.getSafetyIssue()));
            }
            table.insertTable(pomTable);
            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }

    private void addSecondaryOutcomesMeasuresTable() throws DocumentException {
        if (CollectionUtils.isNotEmpty(getSecondaryOutcomeMeasures())) {
            Table table = getOuterTable(TSRReportLabelText.TABLE_SECONDARY_OUTCOME_MEASURES, true);
            Table somTable = getInnerTable(Arrays.asList(TSRReportLabelText.SOM_TITLE,
                    TSRReportLabelText.SOM_DESCRIPTION, TSRReportLabelText.SOM_TIMEFRAME,
                    TSRReportLabelText.SOM_SAFETY_ISSUE));
            for (TSRReportOutcomeMeasure som : getSecondaryOutcomeMeasures()) {
                somTable.addCell(getItemValueCell(som.getTitle()));
                somTable.addCell(getItemValueCell(som.getDescription()));
                somTable.addCell(getItemValueCell(som.getTimeFrame()));
                somTable.addCell(getItemValueCell(som.getSafetyIssue()));
            }
            table.insertTable(somTable);
            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }

    private void addOtherOutcomesMeasuresTable() throws DocumentException {
        if (CollectionUtils.isNotEmpty(getOtherOutcomeMeasures())) {
            Table table = getOuterTable(TSRReportLabelText.TABLE_OTHER_OUTCOME_MEASURES, true);
            Table somTable = getInnerTable(Arrays.asList(TSRReportLabelText.SOM_TITLE,
                    TSRReportLabelText.SOM_DESCRIPTION, TSRReportLabelText.SOM_TIMEFRAME,
                    TSRReportLabelText.SOM_SAFETY_ISSUE));
            for (TSRReportOutcomeMeasure som : getOtherOutcomeMeasures()) {
                somTable.addCell(getItemValueCell(som.getTitle()));
                somTable.addCell(getItemValueCell(som.getDescription()));
                somTable.addCell(getItemValueCell(som.getTimeFrame()));
                somTable.addCell(getItemValueCell(som.getSafetyIssue()));
            }
            table.insertTable(somTable);
            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }

    private void addSubGroupsStratificationCriteriaTable() throws DocumentException {
        if (CollectionUtils.isNotEmpty(getSgsCriterias())) {
            Table table = getOuterTable(TSRReportLabelText.TABLE_SUB_GROUP_STRATIFICATION_CRITERIA, true);
            Table sgscTable = getInnerTable(Arrays.asList(TSRReportLabelText.SGSC_LABEL,
                    TSRReportLabelText.SGSC_DESCRIPTION));
            for (TSRReportSubGroupStratificationCriteria sgsc : getSgsCriterias()) {
                sgscTable.addCell(getItemValueCell(sgsc.getLabel()));
                sgscTable.addCell(getItemValueCell(sgsc.getDescription()));
            }
            table.insertTable(sgscTable);
            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }

    private void addPlannedMarkers() throws DocumentException {
        if (CollectionUtils.isNotEmpty(getPlannedMarkers())) {
            List<TSRReportPlannedMarker> plannedMarkersList = getPlannedMarkers();
            Collections.sort(plannedMarkersList, new Comparator<TSRReportPlannedMarker>() {
                @Override
                public int compare(TSRReportPlannedMarker o1, TSRReportPlannedMarker o2) {
                    return (o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()));
                }
            });
            Table table = getOuterTable(TSRReportLabelText.TABLE_PLANNED_MARKERS, true);
            Table markerTable = getInnerTable(Arrays.asList(TSRReportLabelText.PLANNED_MARKER_NAME,
                    TSRReportLabelText.PLANNED_MARKER_EVALUATION_TYPE,
                    TSRReportLabelText.PLANNED_MARKER_ASSAY_TYPE, TSRReportLabelText.PLANNED_MARKER_BIOMARKER_USE,
                    TSRReportLabelText.PLANNED_MARKER_BIOMARKER_PURPOSE,
                    TSRReportLabelText.PLANNED_MARKER_SPECIMEN_TYPE));
            for (TSRReportPlannedMarker marker : plannedMarkersList) {
                markerTable.addCell(getItemValueCell(marker.getName()));
                markerTable.addCell(getItemValueCell(marker.getEvaluationType()));
                markerTable.addCell(getItemValueCell(marker.getAssayType()));
                markerTable.addCell(getItemValueCell(marker.getAssayUse()));
                markerTable.addCell(getItemValueCell(marker.getAssayPurpose()));
                markerTable.addCell(getItemValueCell(marker.getTissueSpecimenType()));
            }
            table.insertTable(markerTable);
            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }

    private void addParticipatingSitesTable() throws DocumentException {
        if (CollectionUtils.isNotEmpty(getParticipatingSites())) {
            Table table = getOuterTable(TSRReportLabelText.TABLE_PARTICIPATING_SITES, true);
            Table siteTable = isProprietaryTrial() ? getParticipatingSitesTableForProprietaryTrials()
                    : getParticipatingSitesTableForNonProprietaryTrials();
            table.insertTable(siteTable);
            reportDocument.add(table);
            reportDocument.add(getLineBreak());
        }
    }

    private void addPrimaryPurposeRow(Table table, String label, String primaryPurpose, String primaryPurposeOtherText)
            throws BadElementException {
        if (!StringUtils.isEmpty(primaryPurpose)) {
            StringBuffer primaryPurposeBuffer = new StringBuffer(primaryPurpose);
            if (StringUtils.isNotEmpty(primaryPurposeOtherText)) {
                primaryPurposeBuffer.append(", ").append(primaryPurposeOtherText);
            }
            addTableRow(table, label, primaryPurposeBuffer.toString());
        }
    }

    private void addPhaseAdditionalQualifierRow(Table table, String label, String additionalQualifier)
            throws BadElementException {
        if (!StringUtils.isEmpty(additionalQualifier)) {
            StringBuffer phaseBuffer = new StringBuffer();
            if (StringUtils.isNotEmpty(additionalQualifier)
                    && PhaseAdditionalQualifierCode.PILOT.getCode().equals(additionalQualifier)) {
                phaseBuffer.append("Yes");
            } else if (StringUtils.isNotEmpty(additionalQualifier)
                    && (!PhaseAdditionalQualifierCode.PILOT.getCode().equals(additionalQualifier))) {
                phaseBuffer.append("No");
            }
            addTableRow(table, label, phaseBuffer.toString());
        }
    }

    private Table getParticipatingSitesTableForProprietaryTrials() throws DocumentException {
        Table siteTable = getInnerTable(Arrays.asList(TSRReportLabelText.PS_PO_ID, TSRReportLabelText.PS_FACILITY,
                TSRReportLabelText.PS_INVESTIGATORS, TSRReportLabelText.PS_LOCAL_TRIAL_IDENTIFIER,
                TSRReportLabelText.PS_RECRUITMENT_STATUS_AND_DATES, TSRReportLabelText.PS_TARGET_ACCRUAL,
                TSRReportLabelText.PS_SUMMARY4_SPONSOR));
        String summary4Sponsor = "";
        if (getSummary4Information() != null) {
            summary4Sponsor = "Funding Sponsor/Source: " + getSummary4Information().getFundingSponsor()
                    + "; Category: " + getSummary4Information().getFundingCategory();
        }
        for (TSRReportParticipatingSite site : getParticipatingSites()) {
            siteTable.addCell(getItemValueCell(site.getPoId()));
            siteTable.addCell(getItemValueCell(site.getFacility()));
            List<String> investigatorList = new ArrayList<String>();
            StringBuffer sb = new StringBuffer();
            for (TSRReportInvestigator investigator : site.getInvestigators()) {
                investigatorList.add(sb.append(investigator.getFirstName()).append(SPACE)
                        .append(investigator.getLastName())
                        .append(HYPHEN).append(investigator.getRole()).toString());
            }

            sb = new StringBuffer();
            sb.append(site.getRecruitmentStatus()).append(";\nOpened for Accrual Date: ")
            .append(site.getOpenForAccrualDate()).append("\nClosed for Accrual Date: ")
            .append(site.getClosedForAccrualDate());

            siteTable.addCell(getItemValueCell(investigatorList));
            siteTable.addCell(getItemValueCell(site.getLocalTrialIdentifier()));
            siteTable.addCell(getItemValueCell(sb.toString()));
            siteTable.addCell(getItemValueCell(site.getTargetAccrual()));
            siteTable.addCell(getItemValueCell(summary4Sponsor));
        }

        return siteTable;
    }

    private Table getParticipatingSitesTableForNonProprietaryTrials() throws DocumentException {
        Table siteTable = getInnerTable(Arrays.asList(TSRReportLabelText.PS_PO_ID, TSRReportLabelText.PS_FACILITY,
                TSRReportLabelText.PS_CONTACT, TSRReportLabelText.PS_RECRUITMENT_STATUS_AND_DATES,
                TSRReportLabelText.PS_TARGET_ACCRUAL, TSRReportLabelText.PS_INVESTIGATORS));
        for (TSRReportParticipatingSite site : getParticipatingSites()) {
            siteTable.addCell(getItemValueCell(site.getPoId()));
            siteTable.addCell(getItemValueCell(site.getFacility()));
            siteTable.addCell(getItemValueCell(site.getContact()));
            siteTable.addCell(getItemValueCell(site.getRecruitmentStatus()));
            siteTable.addCell(getItemValueCell(site.getTargetAccrual()));
            List<String> investigatorList = new ArrayList<String>();
            for (TSRReportInvestigator investigator : site.getInvestigators()) {
                investigatorList.add(investigator.getFirstName() + " " + investigator.getLastName() + " - "
                        + investigator.getRole());
            }
            siteTable.addCell(getItemValueCell(investigatorList));
        }

        return siteTable;
    }

    private Table getOuterTable(String tableHeader, boolean singleCell)
            throws BadElementException {
        return getOuterTable(tableHeader, singleCell, new float[] {1f, 2f});
    }
    
    private Table getOuterTable(String tableHeader, boolean singleCell, float[] colsWidth) throws BadElementException {
        Table table = null;
        if (singleCell) {
            table = new Table(1);
        } else {           
            table = new Table(INT_2);
            table.setWidths(colsWidth);
        }
        table.setPadding(FLOAT_1);
        table.setWidth(FLOAT_100);

        Cell cell = new Cell(new Paragraph(tableHeader, outerTableHeaderCellFont));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setWidth(FLOAT_100);
        if (!singleCell) {
            cell.setColspan(INT_2);
        }
        table.addCell(cell);
        return table;
    }

    private Table getInnerTable(List<String> cols) throws BadElementException {
        Table table = new Table(cols.isEmpty() ? INT_2 : cols.size());
        table.setWidth(FLOAT_100);
        for (String col : cols) {
            Cell cell = new Cell(new Paragraph(col, innerTableHeaderCellFont));
            cell.setBackgroundColor(new Color(INT_229, INT_178, INT_178));
            cell.setWidth(FLOAT_100);
            table.addCell(cell);
        }
        return table;
    }

    private void addTableSeparatorRow(Table table, int colSpan, String text) throws BadElementException {
        Cell cell = new Cell();
        if (text != null) {
            cell.addElement(new Phrase(text, separatorRowCellFont));
        }
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setColspan(colSpan);
        table.addCell(cell);
    }

    private void addTableSeparatorRow(Table table, int colSize) throws BadElementException {
        addTableSeparatorRow(table, colSize, null);
    }

    private void addTableRow(Table table, String columnHeader, String columnValue) throws BadElementException {
        addTableRow(table, columnHeader, columnValue, 1);
    }

    private void addTableRow(Table table, String columnHeader, String columnValue, int colspan)
        throws BadElementException {
        if (!StringUtils.isEmpty(columnValue)) {
            table.addCell(getItemCell(columnHeader, colspan));
            table.addCell(getItemValueCell(columnValue, colspan));
        }
    }

    private Cell getItemCell(String itemText, int colspan) throws BadElementException {
        Font itemNameFont = FontFactory.getFont(FontFactory.HELVETICA, Font.DEFAULTSIZE, Font.BOLD, Color.BLACK);
        Cell cell = new Cell(new Phrase(StringUtils.replace(itemText, "\r\n", "\n"), itemNameFont));
        cell.setWidth(FLOAT_100);
        if (colspan > 1) {
            cell.setColspan(colspan);
        }
        return cell;
    }

    private Cell getItemValueCell(String itemValueText) throws BadElementException {
        return getItemValueCell(itemValueText, 1);
    }

    private Cell getItemValueCell(String itemValueText, int colspan) throws BadElementException {
        Font itemValueFont = FontFactory.getFont(FontFactory.HELVETICA, Font.DEFAULTSIZE, Font.NORMAL, Color.BLACK);
        Cell cell = new Cell(new Phrase(StringUtils.replace(itemValueText, "\r\n", "\n"), itemValueFont));
        if (StringUtils.length(itemValueText) <= FLOAT_100) {
            cell.setWidth(FLOAT_100 * new Float(StringUtils.length(itemValueText)).floatValue());   
        } else {
            cell.setWidth(PERC_100);
        }
        cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
        if (colspan > 1) {
            cell.setColspan(colspan);
        }
        return cell;
    }

    private Cell getItemValueCell(List<String> lst) throws BadElementException {
        com.lowagie.text.List textList = new com.lowagie.text.List(false, INT_10);
        textList.setListSymbol(new Chunk("\u2022", FontFactory.getFont(FontFactory.HELVETICA, INT_12, Font.BOLD)));
        Font itemValueFont = FontFactory.getFont(FontFactory.HELVETICA, Font.DEFAULTSIZE, Font.NORMAL, Color.BLACK);
        for (String itemStr : lst) {
            ListItem item = new ListItem(StringUtils.replace(itemStr, "\r\n", "\n"));
            item.setFont(itemValueFont);
            textList.add(item);
        }

        Cell cell = new Cell();
        cell.setWidth(FLOAT_100);
        cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
        cell.addElement(textList);
        return cell;
    }

    /**
     * @return the tsrErrorReport
     */
    public TSRErrorReport getTsrErrorReport() {
        return tsrErrorReport;
    }

    /**
     * @param tsrErrorReport the tsrErrorReport to set
     */
    public void setTsrErrorReport(TSRErrorReport tsrErrorReport) {
        this.tsrErrorReport = tsrErrorReport;
    }

    /**
     * @return the outputStream
     */
    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * @param outputStream the outputStream to set
     */
    public void setOutputStream(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * @param trialIdentification the trialIdentification to set
     */
    public void setTrialIdentification(TSRReportTrialIdentification trialIdentification) {
        this.trialIdentification = trialIdentification;
    }

    /**
     * @param generalTrialDetails the generalTrialDetails to set
     */
    public void setGeneralTrialDetails(TSRReportGeneralTrialDetails generalTrialDetails) {
        this.generalTrialDetails = generalTrialDetails;
    }

    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(TSRReportStatusDate statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * @param regulatoryInformation the regulatoryInformation to set
     */
    public void setRegulatoryInformation(TSRReportRegulatoryInformation regulatoryInformation) {
        this.regulatoryInformation = regulatoryInformation;
    }

    /**
     * @param humanSubjectSafety the humanSubjectSafety to set
     */
    public void setHumanSubjectSafety(TSRReportHumanSubjectSafety humanSubjectSafety) {
        this.humanSubjectSafety = humanSubjectSafety;
    }

    /**
     * @param indIdes the indIdes to set
     */
    public void setIndIdes(List<TSRReportIndIde> indIdes) {
        this.indIdes = indIdes;
    }

    /**
     * @param nihGrants the nihGrants to set
     */
    public void setNihGrants(List<TSRReportNihGrant> nihGrants) {
        this.nihGrants = nihGrants;
    }

    /**
     * @param summary4Information the summary4Information to set
     */
    public void setSummary4Information(TSRReportSummary4Information summary4Information) {
        this.summary4Information = summary4Information;
    }

    /**
     * @param collaborators the collaborators to set
     */
    public void setCollaborators(List<TSRReportCollaborator> collaborators) {
        this.collaborators = collaborators;
    }

    /**
     * @param diseaseConditions the diseaseConditions to set
     */
    public void setDiseaseConditions(List<TSRReportDiseaseCondition> diseaseConditions) {
        this.diseaseConditions = diseaseConditions;
    }

    /**
     * @param trialDesign the trialDesign to set
     */
    public void setTrialDesign(TSRReportTrialDesign trialDesign) {
        this.trialDesign = trialDesign;
    }

    /**
     * @param eligibilityCriteria the eligibilityCriteria to set
     */
    public void setEligibilityCriteria(TSRReportEligibilityCriteria eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    /**
     * @param armGroups the armGroups to set
     */
    public void setArmGroups(List<TSRReportArmGroup> armGroups) {
        this.armGroups = armGroups;
    }

    /**
     * @param primaryOutcomeMeasures the primaryOutcomeMeasures to set
     */
    public void setPrimaryOutcomeMeasures(List<TSRReportOutcomeMeasure> primaryOutcomeMeasures) {
        this.primaryOutcomeMeasures = primaryOutcomeMeasures;
    }

    /**
     * @param secondaryOutcomeMeasures the secondaryOutcomeMeasures to set
     */
    public void setSecondaryOutcomeMeasures(List<TSRReportOutcomeMeasure> secondaryOutcomeMeasures) {
        this.secondaryOutcomeMeasures = secondaryOutcomeMeasures;
    }

    /**
     * @param otherOutcomeMeasures the otherOutcomeMeasures to set
     */
    public void setOtherOutcomeMeasures(
            List<TSRReportOutcomeMeasure> otherOutcomeMeasures) {
        this.otherOutcomeMeasures = otherOutcomeMeasures;
    }

    /**
     * @param sgsCriterias the sgsCriterias to set
     */
    public void setSgsCriterias(List<TSRReportSubGroupStratificationCriteria> sgsCriterias) {
        this.sgsCriterias = sgsCriterias;
    }

    /**
     * @param participatingSites the participatingSites to set
     */
    public void setParticipatingSites(List<TSRReportParticipatingSite> participatingSites) {
        this.participatingSites = participatingSites;
    }

    /**
     * @param interventions the interventions to set
     */
    public void setInterventions(List<TSRReportIntervention> interventions) {
        this.interventions = interventions;
    }

    /**
     * @return the tsrReport
     */
    public TSRReport getTsrReport() {
        return tsrReport;
    }

    /**
     * @param tsrReport the tsr report to set
     */
    public void setTsrReport(TSRReport tsrReport) {
        this.tsrReport = tsrReport;
    }

    /**
     * @return the trialIdentification
     */
    public TSRReportTrialIdentification getTrialIdentification() {
        return trialIdentification;
    }

    /**
     * @return the generalTrialDetails
     */
    public TSRReportGeneralTrialDetails getGeneralTrialDetails() {
        return generalTrialDetails;
    }

    /**
     * @return the statusDate
     */
    public TSRReportStatusDate getStatusDate() {
        return statusDate;
    }

    /**
     * @return the regulatoryInformation
     */
    public TSRReportRegulatoryInformation getRegulatoryInformation() {
        return regulatoryInformation;
    }

    /**
     * @return the humanSubjectSafety
     */
    public TSRReportHumanSubjectSafety getHumanSubjectSafety() {
        return humanSubjectSafety;
    }

    /**
     * @return the indIdes
     */
    public List<TSRReportIndIde> getIndIdes() {
        return indIdes;
    }

    /**
     * @return the nihGrants
     */
    public List<TSRReportNihGrant> getNihGrants() {
        return nihGrants;
    }

    /**
     * @return the summary4Information
     */
    public TSRReportSummary4Information getSummary4Information() {
        return summary4Information;
    }

    /**
     * @return the collaborators
     */
    public List<TSRReportCollaborator> getCollaborators() {
        return collaborators;
    }

    /**
     * @return the diseaseConditions
     */
    public List<TSRReportDiseaseCondition> getDiseaseConditions() {
        return diseaseConditions;
    }

    /**
     * @return the trialDesign
     */
    public TSRReportTrialDesign getTrialDesign() {
        return trialDesign;
    }

    /**
     * @return the eligibilityCriteria
     */
    public TSRReportEligibilityCriteria getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    /**
     * @return the armGroups
     */
    public List<TSRReportArmGroup> getArmGroups() {
        return armGroups;
    }

    /**
     * @return the primaryOutcomeMeasures
     */
    public List<TSRReportOutcomeMeasure> getPrimaryOutcomeMeasures() {
        return primaryOutcomeMeasures;
    }

    /**
     * @return the secondaryOutcomeMeasures
     */
    public List<TSRReportOutcomeMeasure> getSecondaryOutcomeMeasures() {
        return secondaryOutcomeMeasures;
    }

    /**
     * @return the otherOutcomeMeasures
     */
    public List<TSRReportOutcomeMeasure> getOtherOutcomeMeasures() {
        return otherOutcomeMeasures;
    }

    /**
     * @return the sgsCriterias
     */
    public List<TSRReportSubGroupStratificationCriteria> getSgsCriterias() {
        return sgsCriterias;
    }

    /**
     * @return the participatingSites
     */
    public List<TSRReportParticipatingSite> getParticipatingSites() {
        return participatingSites;
    }

    /**
     * @return the interventions
     */
    public List<TSRReportIntervention> getInterventions() {
        return interventions;
    }

    /**
     * @return the document
     */
    public Document getReportDocument() {
        return reportDocument;
    }

    /**
     * @param reportDoc the report document to set
     */
    public void setReportDocument(Document reportDoc) {
        this.reportDocument = reportDoc;
    }

    /**
     * @return the markers
     */
    public List<TSRReportPlannedMarker> getPlannedMarkers() {
        return plannedMarkers;
    }

    /**
     * @param markers the markers to set
     */
    public void setPlannedMarkers(List<TSRReportPlannedMarker> markers) {
        this.plannedMarkers = markers;
    }

    /**
     * @return the nonInterventionalTrial
     */
    public boolean isNonInterventionalTrial() {
        return nonInterventionalTrial;
    }

    /**
     * @param nonInterventionalTrial the nonInterventionalTrial to set
     */
    public void setNonInterventionalTrial(boolean nonInterventionalTrial) {
        this.nonInterventionalTrial = nonInterventionalTrial;
    }

    /**
     * @return the associatedTrials
     */
    public List<TSRReportAssociatedTrial> getAssociatedTrials() {
        return associatedTrials;
    }

}

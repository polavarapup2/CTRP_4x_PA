/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The accrual
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This accrual Software License (the License) is between NCI and You. You (or 
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
 * its rights in the accrual Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the accrual Software; (ii) distribute and 
 * have distributed to and by third parties the accrual Software and any 
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
package gov.nih.nci.registry.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;
import gov.nih.nci.pa.enums.CodedEnum;

/**
 * @author asharma
 * 
 */
public enum BatchStringConstants implements CodedEnum<String> {

    /** */
    UNIQUE_TRIAL_IDENTIFIER("UNIQUE TRIAL IDENTIFIER", "setUniqueTrialId"),
    /** */
    SUBMISSION_TYPE("SUBMISSION TYPE", "setSubmissionType"),
    /** */
    NCI_TRIAL_IDENTIFIER("NCI TRIAL IDENTIFIER", "setNciTrialIdentifier"),
    /** */
    CTGOV_XML_INDICATOR("CLINICALTRIALS.GOV XML REQUIRED?", "setCtGovXmlIndicator"),
    /** */
    NCI_FUNDED_INDICATOR("NCI FUNDED?", "setNciGrant"),
    /** */
    AMENDMENT_NUMBER("AMENDMENT NUMBER", "setAmendmentNumber"),
    /** */
    AMENDMENT_DATE("AMENDMENT DATE", "setAmendmentDate"),
    /** */
    LEAD_ORGANIZATION_TRIAL_IDENTIFIER("LEAD ORGANIZATION TRIAL IDENTIFIER", "setLocalProtocolIdentifier"),
    /** */
    NCT_NUMBER("NCT", "setNctNumber"),
    /** */
    OTHER_TRIAL_IDENTIFIER("OTHER TRIAL IDENTIFIER", "setOtherTrialIdentifiers"),
    /** */
    TITLE("TITLE", "setTitle"),
    /** */
    TRIAL_TYPE("TRIAL TYPE", "setTrialType"),
    /** */
    PRIMARY_PURPOSE("PRIMARY PURPOSE", "setPrimaryPurpose"),
    /** */
    PRIMARY_PURPOSE_ADDITIONAL_QUALIFIER("[PRIMARY PURPOSE] ADDITIONAL QUALIFIER",
            "setPrimaryPurposeAdditionalQualifierCode"),
    /** */
    PRIMARY_PURPOSE_OTHER_TEXT("[PRIMARY PURPOSE] OTHER TEXT", "setPrimaryPurposeOtherText"),
    /** */
    PHASE("PHASE", "setPhase"),
    /** */
    PHASE_ADDITIONAL_QUALIFIER("PILOT TRIAL?", "setPhaseAdditionalQualifierCode"),
    /** */
    SPONSOR_ORG_NAME("[SPONSOR] ORGANIZATION NAME", "setSponsorOrgName"),
    /** */
    SPONSOR_CETP_ORG_NO("[SPONSOR] ORGANIZATION PO-ID", "setSponsorPOId"),
    /** */
    SPONSOR_STREET_ADDRESS("[SPONSOR] STREET ADDRESS", "setSponsorStreetAddress"),
    /** */
    SPONSOR_CITY("[SPONSOR] CITY", "setSponsorCity"),
    /** */
    SPONSOR_STATE("[SPONSOR] STATE/PROVINCE", "setSponsorState"),
    /** */
    SPONSOR_ZIP("[SPONSOR] ZIP/POSTAL CODE", "setSponsorZip"),
    /** */
    SPONSOR_COUNTRY("[SPONSOR] COUNTRY", "setSponsorCountry"),
    /** */
    SPONSOR_EMAIL("[SPONSOR] EMAIL ADDRESS", "setSponsorEmail"),
    /** */
    SPONSOR_PHONE("[SPONSOR] PHONE", "setSponsorPhone"),
    /** */
    SPONSOR_TTY("[SPONSOR] TTY", "setSponsorTTY"),
    /** */
    SPONSOR_FAX("[SPONSOR] FAX", "setSponsorFax"),
    /** */
    SPONSOR_URL("[SPONSOR] URL", "setSponsorURL"),
    /** */
    RESPONSIBLE_PARTY("RESPONSIBLE PARTY", "setResponsibleParty"),
    /** */
    SPONSOR_CONTACT_TYPE("SPONSOR CONTACT TYPE", "setSponsorContactType"),
    /** */
    SPONSOR_CONTACT_TITLE("[SPONSOR CONTACT] TITLE", "setResponsibleGenericContactName"),
    /** */
    SPONSOR_CONTACT_FIRST_NAME("[SPONSOR CONTACT] FIRST NAME", "setSponsorContactFName"),
    /** */
    SPONSOR_CONTACT_MIDDLE_NAME("[SPONSOR CONTACT] MIDDLE NAME", "setSponsorContactMName"),
    /** */
    SPONSOR_CONTACT_LAST_NAME("[SPONSOR CONTACT] LAST NAME", "setSponsorContactLName"),
    /** */
    SPONSOR_CONTACT_PERSON_ID("[SPONSOR CONTACT] PERSON PO-ID", "setSponsorContactPOId"),
    /** */
    SPONSOR_CONTACT_STREET_ADDRESS("[SPONSOR CONTACT] STREET ADDRESS", "setSponsorContactStreetAddress"),
    /** */
    SPONSOR_CONTACT_CITY("[SPONSOR CONTACT] CITY", "setSponsorContactCity"),
    /** */
    SPONSOR_CONTACT_STATE("[SPONSOR CONTACT] STATE/PROVINCE", "setSponsorContactState"),
    /** */
    SPONSOR_CONTACT_ZIP("[SPONSOR CONTACT] ZIP/POSTAL CODE", "setSponsorContactZip"),
    /** */
    SPONSOR_CONTACT_COUNTRY("[SPONSOR CONTACT] COUNTRY", "setSponsorContactCountry"),
    /** */
    SPONSOR_CONTACT_EMAIL_ID("[SPONSOR CONTACT] EMAIL ADDRESS", "setSponsorContactEmail"),
    /** */
    SPONSOR_CONTACT_PHONE("[SPONSOR CONTACT] PHONE", "setSponsorContactPhone"),
    /** */
    SPONSOR_CONTACT_TTY("[SPONSOR CONTACT] TTY", "setSponsorContactTTY"),
    /** */
    SPONSOR_CONTACT_FAX("[SPONSOR CONTACT] FAX", "setSponsorContactFax"),
    /** */
    SPONSOR_CONTACT_URL("[SPONSOR CONTACT] URL", "setSponsorContactUrl"),
    /** */
    LEAD_ORG_NAME("[LEAD ORGANIZATION] NAME", "setLeadOrgName"),
    /** */
    LEAD_ORG_PO_ID("[LEAD ORGANIZATION] ORGANIZATION PO-ID", "setLeadOrgPOId"),
    /** */
    LEAD_ORG_STREET_ADDRESS("[LEAD ORGANIZATION] STREET ADDRESS", "setLeadOrgStreetAddress"),
    /** */
    LEAD_ORG_CITY("[LEAD ORGANIZATION] CITY", "setLeadOrgCity"),
    /** */
    LEAD_ORG_STATE("[LEAD ORGANIZATION] STATE/PROVINCE", "setLeadOrgState"),
    /** */
    LEAD_ORG_ZIP("[LEAD ORGANIZATION] ZIP/POSTAL CODE", "setLeadOrgZip"),
    /** */
    LEAD_ORG_COUNTRY("[LEAD ORGANIZATION] COUNTRY", "setLeadOrgCountry"),
    /** */
    LEAD_ORG_EMAIL("[LEAD ORGANIZATION] EMAIL ADDRESS", "setLeadOrgEmail"),
    /** */
    LEAD_ORG_PHONE("[LEAD ORGANIZATION] PHONE", "setLeadOrgPhone"),
    /** */
    LEAD_ORG_TTY("[LEAD ORGANIZATION] TTY", "setLeadOrgTTY"),
    /** */
    LEAD_ORG_FAX("[LEAD ORGANIZATION] FAX", "setLeadOrgFax"),
    /** */
    LEAD_ORG_URL("[LEAD ORGANIZATION] URL", "setLeadOrgUrl"),
    /** */
    LEAD_ORG_TYPE("[LEAD ORGANIZATION] ORGANIZATION TYPE", "setLeadOrgType"),
    /** */
    PI_FIRST_NAME("[PRINCIPAL INVESTIGATOR] FIRST NAME", "setPiFirstName"),
    /** */
    PI_MIDDLE_NAME("[PRINCIPAL INVESTIGATOR] MIDDLE NAME", "setPiMiddleName"),
    /** */
    PI_LAST_NAME("[PRINCIPAL INVESTIGATOR] LAST NAME", "setPiLastName"),
    /** */
    PI_PERSON_PERSON_PO_ID("[PRINCIPAL INVESTIGATOR] PERSON PO-ID", "setPiPOId"),
    /** */
    PI_STREET_ADDRESS("[PRINCIPAL INVESTIGATOR] STREET ADDRESS", "setPiStreetAddress"),
    /** */
    PI_CITY("[PRINCIPAL INVESTIGATOR] CITY", "setPiCity"),
    /** */
    PI_STATE("[PRINCIPAL INVESTIGATOR] STATE/PROVINCE", "setPiState"),
    /** */
    PI_ZIP("[PRINCIPAL INVESTIGATOR] ZIP/POSTAL CODE", "setPiZip"),
    /** */
    PI_COUNTRY("[PRINCIPAL INVESTIGATOR] COUNTRY", "setPiCountry"),
    /** */
    PI_EMAIL("[PRINCIPAL INVESTIGATOR] EMAIL ADDRESS", "setPiEmail"),
    /** */
    PI_PHONE("[PRINCIPAL INVESTIGATOR] PHONE", "setPiPhone"),
    /** */
    PI_TTY("[PRINCIPAL INVESTIGATOR] TTY", "setPiTTY"),
    /** */
    PI_FAX("[PRINCIPAL INVESTIGATOR] FAX", "setPiFax"),
    /** */
    PI_URL("[PRINCIPAL INVESTIGATOR] URL", "setPiUrl"),
    
    /** */
    PARTY_INVESTIGATOR_FIRST_NAME("[RESPONSIBLE PARTY INVESTIGATOR] FIRST NAME", "setPartyInvestigatorFirstName"),
    /** */
    PARTY_INVESTIGATOR_MIDDLE_NAME("[RESPONSIBLE PARTY INVESTIGATOR] MIDDLE NAME", "setPartyInvestigatorMiddleName"),
    /** */
    PARTY_INVESTIGATOR_LAST_NAME("[RESPONSIBLE PARTY INVESTIGATOR] LAST NAME", "setPartyInvestigatorLastName"),
    /** */
    PARTY_INVESTIGATOR_PERSON_PERSON_PO_ID("[RESPONSIBLE PARTY INVESTIGATOR] PERSON PO-ID", "setPartyInvestigatorPOId"),
    /** */
    PARTY_INVESTIGATOR_STREET_ADDRESS("[RESPONSIBLE PARTY INVESTIGATOR] STREET ADDRESS", 
            "setPartyInvestigatorStreetAddress"),
    /** */
    PARTY_INVESTIGATOR_CITY("[RESPONSIBLE PARTY INVESTIGATOR] CITY", "setPartyInvestigatorCity"),
    /** */
    PARTY_INVESTIGATOR_STATE("[RESPONSIBLE PARTY INVESTIGATOR] STATE/PROVINCE", "setPartyInvestigatorState"),
    /** */
    PARTY_INVESTIGATOR_ZIP("[RESPONSIBLE PARTY INVESTIGATOR] ZIP/POSTAL CODE", "setPartyInvestigatorZip"),
    /** */
    PARTY_INVESTIGATOR_COUNTRY("[RESPONSIBLE PARTY INVESTIGATOR] COUNTRY", "setPartyInvestigatorCountry"),
    /** */
    PARTY_INVESTIGATOR_EMAIL("[RESPONSIBLE PARTY INVESTIGATOR] EMAIL ADDRESS", "setPartyInvestigatorEmail"),
    /** */
    PARTY_INVESTIGATOR_PHONE("[RESPONSIBLE PARTY INVESTIGATOR] PHONE", "setPartyInvestigatorPhone"),
    /** */
    PARTY_INVESTIGATOR_TTY("[RESPONSIBLE PARTY INVESTIGATOR] TTY", "setPartyInvestigatorTTY"),
    /** */
    PARTY_INVESTIGATOR_FAX("[RESPONSIBLE PARTY INVESTIGATOR] FAX", "setPartyInvestigatorFax"),
    /** */
    PARTY_INVESTIGATOR_URL("[RESPONSIBLE PARTY INVESTIGATOR] URL", "setPartyInvestigatorUrl"),

    /**
     * 
     */
    PARTY_INVESTIGATOR_TITLE("[RESPONSIBLE PARTY INVESTIGATOR] TITLE", "setPartyInvestigatorTitle"),
    
    
    /** */
    PARTY_AFFILIATION_NAME("[RESPONSIBLE PARTY AFFILIATION] NAME", "setPartyAffiliationName"),
    /** */
    PARTY_AFFILIATION_PO_ID("[RESPONSIBLE PARTY AFFILIATION] ORGANIZATION PO-ID", "setPartyAffiliationPOId"),
    /** */
    PARTY_AFFILIATION_STREET_ADDRESS("[RESPONSIBLE PARTY AFFILIATION] STREET ADDRESS", 
            "setPartyAffiliationStreetAddress"),
    /** */
    PARTY_AFFILIATION_CITY("[RESPONSIBLE PARTY AFFILIATION] CITY", "setPartyAffiliationCity"),
    /** */
    PARTY_AFFILIATION_STATE("[RESPONSIBLE PARTY AFFILIATION] STATE/PROVINCE", "setPartyAffiliationState"),
    /** */
    PARTY_AFFILIATION_ZIP("[RESPONSIBLE PARTY AFFILIATION] ZIP/POSTAL CODE", "setPartyAffiliationZip"),
    /** */
    PARTY_AFFILIATION_COUNTRY("[RESPONSIBLE PARTY AFFILIATION] COUNTRY", "setPartyAffiliationCountry"),
    /** */
    PARTY_AFFILIATION_EMAIL("[RESPONSIBLE PARTY AFFILIATION] EMAIL ADDRESS", "setPartyAffiliationEmail"),
    /** */
    PARTY_AFFILIATION_PHONE("[RESPONSIBLE PARTY AFFILIATION] PHONE", "setPartyAffiliationPhone"),
    /** */
    PARTY_AFFILIATION_TTY("[RESPONSIBLE PARTY AFFILIATION] TTY", "setPartyAffiliationTTY"),
    /** */
    PARTY_AFFILIATION_FAX("[RESPONSIBLE PARTY AFFILIATION] FAX", "setPartyAffiliationFax"),
    /** */
    PARTY_AFFILIATION_URL("[RESPONSIBLE PARTY AFFILIATION] URL", "setPartyAffiliationUrl"),

    
    
    /** */
    S4_FUND_CAT("DATA TABLE 4 FUNDING CATEGORY", "setSumm4FundingCat"),
    /** */
    S4_FUND_ORG_NAME("[DATA TABLE 4 FUNDING SPONSOR/SOURCE] ORGANIZATION NAME", "setSumm4OrgName"),
    /** */
    S4_FUND_ORG_ORG_PO_ID("[DATA TABLE 4 FUNDING SPONSOR/SOURCE] ORGANIZATION PO-ID", "setSumm4OrgPOId"),
    /** */
    S4_FUND_ORG_STREET_ADDRESS("[DATA TABLE 4 FUNDING SPONSOR/SOURCE] STREET ADDRESS", "setSumm4OrgStreetAddress"),
    /** */
    S4_FUND_CITY("[DATA TABLE 4 FUNDING SPONSOR/SOURCE] CITY", "setSumm4City"),
    /** */
    S4_FUND_STATE("[DATA TABLE 4 FUNDING SPONSOR/SOURCE] STATE/PROVINCE", "setSumm4State"),
    /** */
    S4_FUND_ZIP("[DATA TABLE 4 FUNDING SPONSOR/SOURCE] ZIP/POSTAL CODE", "setSumm4Zip"),
    /** */
    S4_FUND_COUNTRY("[DATA TABLE 4 FUNDING SPONSOR/SOURCE ] COUNTRY", "setSumm4Country"),
    /** */
    S4_FUND_EMAIL("[DATA TABLE 4 FUNDING SPONSOR/SOURCE ] EMAIL ADDRESS", "setSumm4Email"),
    /** */
    S4_FUND_PHONE("[DATA TABLE 4 FUNDING SPONSOR/SOURCE ] PHONE", "setSumm4Phone"),
    /** */
    S4_FUND_TTY("[DATA TABLE 4 FUNDING SPONSOR/SOURCE ] TTY", "setSumm4TTY"),
    /** */
    S4_FUND_FAX("[DATA TABLE 4 FUNDING SPONSOR/SOURCE ] FAX", "setSumm4Fax"),
    /** */
    S4_FUND_URL("[DATA TABLE 4 FUNDING SPONSOR/SOURCE ] URL", "setSumm4Url"),
    /** */
    S4_PRG_CODE_TEXT("PROGRAM CODE", "setProgramCodeText"),
    /** */
    NIH_GRANT_FUND_MC("[NIH GRANT] FUNDING MECHANISM", "setNihGrantFundingMechanism"),
    /** */
    NIH_GRANT_INSTITUTE_CODE("[NIH GRANT] INSTITUTE CODE", "setNihGrantInstituteCode"),
    /** */
    NIH_GRANT_SR_NO("[NIH GRANT] SERIAL NUMBER", "setNihGrantSrNumber"),
    /** */
    NIH_GRANT_NCI_DIV_CODE("[NIH GRANT] NCI DIVISION/PROGRAM CODE", "setNihGrantNCIDivisionCode"),
    /** */
    NIH_GRANT_FUNDING_PCT("[NIH GRANT] FUNDING PERCENT", "setNihGrantFundingPct"),
    /** */
    CURRENT_TRIAL_STATUS("CURRENT TRIAL STATUS", "setCurrentTrialStatus"),
    /** */
    REASON_FOR_STUDY_STOPPED("WHY STUDY STOPPED?", "setReasonForStudyStopped"),
    /** */
    CURRENT_TRIAL_STATUS_DATE("CURRENT TRIAL STATUS DATE", "setCurrentTrialStatusDate"),
    /** */
    STUDY_START_DATE("STUDY START DATE", "setStudyStartDate"),
    /** */
    STUDY_START_DATE_TYPE("STUDY START DATE TYPE", "setStudyStartDateType"),
    /** */
    PRIMARY_COMP_DATE("PRIMARY COMPLETION DATE", "setPrimaryCompletionDate"),
    /** */
    PRIMARY_COMP_DATE_TYPE("PRIMARY COMPLETION DATE TYPE", "setPrimaryCompletionDateType"),
    /** */
    STUDY_COMP_DATE("STUDY COMPLETION DATE", "setCompletionDate"),
    /** */
    STUDY_COMP_DATE_TYPE("STUDY COMPLETION DATE TYPE", "setCompletionDateType"),
    /** */
    IND_TYPE("IND/IDE TYPE", "setIndType"),
    /** */
    IND_NUMBER("IND/IDE NUMBER", "setIndNumber"),
    /** */
    IND_GRANTOR("IND/IDE GRANTOR", "setIndGrantor"),
    /** */
    IND_HOLDER_TYPE("IND/IDE HOLDER TYPE", "setIndHolderType"),
    /** */
    IND_NIH_INSTITUTION("[IND/IDE] NIH INSTITUTION", "setIndNIHInstitution"),
    /** */
    IND_NCI_DIV_CODE("[IND/IDE] NCI DIVISION /PROGRAM", "setIndNCIDivision"),
    /** */
    IND_HAS_EXPANDED_ACCESS("[IND/IDE] HAS EXPANDED ACCESS?", "setIndHasExpandedAccess"),
    /** */
    IND_EXPANED_ACCESS_STATUS("[IND/IDE] EXPANDED ACCESS STATUS", "setIndExpandedAccessStatus"),
    /** */
    IND_EXEMPT_INDICATOR("[IND/IDE] EXEMPT INDICATOR", "setExemptIndicator"),
    /** */
    OVERSIGHT_AUTHORITY_COUNTRY("OVERSIGHT AUTHORITY COUNTRY", "setOversightAuthorityCountry"),
    /** */
    OVERSIGHT_AUTHORITY_ORG_NAME("OVERSIGHT AUTHORITY ORGANIZATION NAME", "setOversightOrgName"),
    /** */
    FDA_REGULATORY_INFORMATION_INDICATOR("FDA REGULATORY INFORMATION INDICATOR", 
                                         "setFdaRegulatoryInformationIndicator"),
    /** */
    SECTION_801_INDICATOR("SECTION 801 INDICATOR", "setSection801Indicator"),
    /** */
    DELAYED_POSTING_INDICATOR("DELAYED POSTING INDICATOR", "setDelayedPostingIndicator"),
    /** */
    DATA_MONITORING_COMMITTEE_APPOINTED_INDICATOR("DATA MONITORING COMMITTEE APPOINTED INDICATOR",
            "setDataMonitoringCommitteeAppointedIndicator"),
    /** */
    PROTOCOL_DOC_FILE_NAME("PROTOCOL DOCUMENT FILE NAME", "setProtcolDocumentFileName"),
    /** */
    IRB_APPROVAL_DOC_FILE_NAME("IRB APPROVAL DOCUMENT FILE NAME", "setIrbApprovalDocumentFileName"),
    /** */
    PARTICIPATIING_SITE_DOC_FILE_NAME("PARTICIPATING SITES DOCUMENT FILE NAME", "setParticipatinSiteDocumentFileName"),
    /** */
    INFORMED_CONSENT_DOC_FILE_NAME("INFORMED CONSENT DOCUMENT FILE NAME", "setInformedConsentDocumentFileName"),
    /** */
    OTHER_TRIAL_DOC_FILE_NAME("OTHER TRIAL RELATED DOCUMENT FILE NAME", "setOtherTrialRelDocumentFileName"),
    /** */
    CHANGE_MEMO_DOC_FILE_NAME("CHANGE MEMO DOCUMENT NAME", "setChangeRequestDocFileName"),
    /** */
    PROTOCOL_HIGHLIGHTED_DOC_FILE_NAME("PROTOCOL HIGHLIGHT DOCUMENT NAME", "setProtocolHighlightDocFileName");

    private String code;
    private String methodName;

    /**
     * Constructor for BatchStringConstants.
     * @param code
     */

    private BatchStringConstants(String code, String methodName) {
        this.code = code;
        this.methodName = methodName;
        register(this);
    }

    /**
     * @return code coded value of enum
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * @return methodName coded value of enum
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @return String DisplayName
     */
    @Override
    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    /**
     * @return String display name
     */
    public String getName() {
        return name();
    }

    /**
     * @param code code
     * @return BatchStringConstants
     */
    public static BatchStringConstants getByCode(String code) {
        return getByClassAndCode(BatchStringConstants.class, code);
    }

    /**
     * construct a array of display names for BatchStringConstantsEnum.
     * @return String[] display names for BatchStringConstants
     */
    public static String[] getDisplayNames() {
        BatchStringConstants[] batchStringConstants = BatchStringConstants.values();
        String[] codedNames = new String[batchStringConstants.length];
        for (int i = 0; i < batchStringConstants.length; i++) {
            codedNames[i] = batchStringConstants[i].getCode();
        }
        return codedNames;
    }

}

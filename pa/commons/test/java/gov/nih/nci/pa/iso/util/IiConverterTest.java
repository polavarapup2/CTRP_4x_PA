package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;

import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

public class IiConverterTest {

    @Test
    public void testConvertNullToLong() {
        assertNull("Null should produce null", IiConverter.convertToLong(null));
    }

    @Test
    public void testConvertNullFlavorToLong() {
        Ii ii = new Ii();
        ii.setNullFlavor(NullFlavor.NI);
        assertNull("Null flavor should produce null", IiConverter.convertToLong(ii));
    }

    @Test
    public void testConvertNullFlavorWithExtensionToLong() {
        Ii ii = new Ii();
        ii.setNullFlavor(NullFlavor.NI);
        ii.setExtension("12345");
        assertNull("Null flavor with extension should produce null", IiConverter.convertToLong(ii));
    }

    @Test
    public void testConvertNullExtensionToLong() {
        Ii ii = new Ii();
        ii.setExtension(null);
        assertNull("Null extension should produce null", IiConverter.convertToLong(ii));
    }

    @Test
    public void testConvertIiToLong() {
        Ii ii = new Ii();
        ii.setExtension("12345");
        assertEquals(12345L, IiConverter.convertToLong(ii).longValue());
    }

    @Test
    public void testConvertNullIiToString() {
        assertNull("Null II should be converted to null String", IiConverter.convertToString(null));
    }

    @Test
    public void testConvertNullFlavorIiToString() {
        Ii ii = new Ii();
        ii.setNullFlavor(NullFlavor.NI);
        assertNull("Null-flavored II should be converted to null String", IiConverter.convertToString(ii));
    }

    @Test
    public void testConvertNullFlavorIiWithExtensionToString() {
        Ii ii = new Ii();
        ii.setNullFlavor(NullFlavor.NI);
        ii.setExtension("1234");
        assertNull("II with null flavor and extenstion should be converted to null String",
                   IiConverter.convertToString(ii));
    }

    @Test(expected = Exception.class)
    @Ignore("convertToString currently sets the null flavor if there's no extension, rather than throwing an exception or just leaving the input unmodified")
    public void testConvertIiWithNullExtensionToString() {
        Ii ii = new Ii();
        assertNull("II with no extension and no null flavor should be converted to null String",
                   IiConverter.convertToString(ii));
        assertNull("Converting II to String shouldn't modify II", ii.getNullFlavor());
    }

    @Test
    public void testConvertIiWithNullExtensionToStringWithSideEffects() {
        // This test should be removed when convertToString handles null extensions properly by throwing an exception
        Ii ii = new Ii();
        assertNull("II with no extension and no null flavor should be converted to null String",
                   IiConverter.convertToString(ii));
        assertNotNull(ii.getNullFlavor());
    }

    @Test
    public void testConvertNonNullIiToString() {
        Ii ii = new Ii();
        ii.setExtension("1234");
        assertEquals("II's extension should be returned as String", "1234", IiConverter.convertToString(ii));
    }

    @Test
    public void testConvertNullToIi() {
        Ii ii = IiConverter.convertToIi((Long) null);
        assertNotNull("Null II should have null flavor set", ii.getNullFlavor());
        assertNull("Null II shouldn't have extension", ii.getExtension());
    }

    @Test
    public void testConvertLongToIi() {
        Ii ii = IiConverter.convertToIi(123L);
        assertNull("Non-null II should not have null flavor set", ii.getNullFlavor());
        assertEquals("Non-null II should have extension set", "123", ii.getExtension());
    }

    @Test
    public void testConvertStringToIi() {
        Ii ii = IiConverter.convertToIi("potato");
        assertNull("Non-null II should not have null flavor set", ii.getNullFlavor());
        assertEquals("Non-null II should have extension set", "potato", ii.getExtension());
    }

    @Test
    public void testConvertNullToActivityIi() {
        Ii activityIi = IiConverter.convertToActivityIi(null);
        verifyNullObjectIi(activityIi, IiConverter.ACTIVITY_ROOT, IiConverter.ACTIVITY_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToActivityIi() {
        Ii activityIi = IiConverter.convertToActivityIi(1234L);
        assertEquals(makeActivityIi("1234"), activityIi);
    }

    @Test
    public void testConvertNullToArmIi() {
        Ii armIi = IiConverter.convertToArmIi(null);
        verifyNullObjectIi(armIi, IiConverter.ARM_ROOT, IiConverter.ARM_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToArmIi() {
        Ii armIi = IiConverter.convertToArmIi(1234L);
        assertEquals(makeArmIi("1234"), armIi);
    }

    @Test
    public void testConvertNullToAssignedIdentifierIi() {
        Ii assignedIdentifierIi = IiConverter.convertToAssignedIdentifierIi(null);
        verifyNullObjectIi(assignedIdentifierIi, IiConverter.STUDY_PROTOCOL_ROOT,
                           IiConverter.STUDY_PROTOCOL_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToAssignedIdentifierIi() {
        Ii assignedIdentifierIi = IiConverter.convertToAssignedIdentifierIi("1234");
        assertEquals(makeAssignedIdentifierIi("1234"), assignedIdentifierIi);
    }

    @Test
    public void testConvertNullToCountryIi() {
        Ii countryIi = IiConverter.convertToCountryIi(null);
        verifyNullObjectIi(countryIi, IiConverter.COUNTRY_ROOT, IiConverter.COUNTRY_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToCountryIi() {
        Ii countryIi = IiConverter.convertToCountryIi(1234L);
        assertEquals(makeCountryIi("1234"), countryIi);
    }

    @Test
    public void testConvertNullToDocumentIi() {
        Ii documentIi = IiConverter.convertToDocumentIi(null);
        verifyNullObjectIi(documentIi, IiConverter.DOCUMENT_ROOT, IiConverter.DOCUMENT_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToDocumentIi() {
        Ii documentIi = IiConverter.convertToDocumentIi(1234L);
        assertEquals(makeDocumentIi("1234"), documentIi);
    }

    @Test
    public void testConvertNullToDocumentWorkFlowStatusIi() {
        Ii documentWorkFlowStatusIi = IiConverter.convertToDocumentWorkFlowStatusIi(null);
        verifyNullObjectIi(documentWorkFlowStatusIi, IiConverter.DOCUMENT_WORKFLOW_STATUS_ROOT,
                           IiConverter.DOCUMENT_WORKFLOW_STATUS_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToDocumentWorkFlowStatusIi() {
        Ii documentWorkFlowStatusIi = IiConverter.convertToDocumentWorkFlowStatusIi(1234L);
        assertEquals(makeDocumentWorkflowStatusIi("1234"), documentWorkFlowStatusIi);
    }

    @Test
    public void testConvertNullToIdentifiedOrgEntityIi() {
        Ii identifiedOrgEntityIi = IiConverter.convertToIdentifiedOrgEntityIi(null);
        verifyNullObjectIi(identifiedOrgEntityIi, IiConverter.CTEP_ORG_IDENTIFIER_ROOT,
                           IiConverter.CTEP_ORG_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToIdentifiedOrgEntityIi() {
        Ii identifiedOrgEntityIi = IiConverter.convertToIdentifiedOrgEntityIi("1234");
        assertEquals(makeIdentifiedOrgIi("1234"), identifiedOrgEntityIi);
    }

    @Test
    public void testConvertNullToIdentifiedPersonEntityIi() {
        Ii IdentifiedPersonEntityIi = IiConverter.convertToIdentifiedPersonEntityIi(null);
        verifyNullObjectIi(IdentifiedPersonEntityIi, IiConverter.CTEP_PERSON_IDENTIFIER_ROOT,
                           IiConverter.CTEP_PERSON_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToIdentifiedPersonEntityIi() {
        Ii identifiedPersonEntityIi = IiConverter.convertToIdentifiedPersonEntityIi("1234");
        assertEquals(makeIdentifiedPersonIi("1234"), identifiedPersonEntityIi);
    }

    @Test
    public void testConvertNullToOtherIdentifierIi() {
        Ii OtherIdentifierIi = IiConverter.convertToOtherIdentifierIi(null);
        verifyNullObjectIi(OtherIdentifierIi, IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT,
                           IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToOtherIdentifierIi() {
        Ii otherIdentifierIi = IiConverter.convertToOtherIdentifierIi("1234");
        assertEquals(makeOtherIdentifierIi("1234"), otherIdentifierIi);
    }

    @Test
    public void testConvertNullToPaOrganizationIi() {
        Ii paOrganizationIi = IiConverter.convertToPaOrganizationIi(null);
        verifyNullObjectIi(paOrganizationIi, IiConverter.ORG_ROOT, IiConverter.ORG_PA_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToPaOrganizationIi() {
        Ii paOrganizationIi = IiConverter.convertToPaOrganizationIi(1234L);
        assertEquals(makePaOrganizationIi("1234"), paOrganizationIi);
    }

    @Test
    public void testConvertNullToPaPersonIi() {
        Ii paPersonIi = IiConverter.convertToPaPersonIi(null);
        verifyNullObjectIi(paPersonIi, IiConverter.PERSON_ROOT, IiConverter.PERSON_PA_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToPaPersonIi() {
        Ii paPersonIi = IiConverter.convertToPaPersonIi(1234L);
        assertEquals(makePaPersonIi("1234"), paPersonIi);
    }

    @Test
    public void testConvertNullToPlannedActivityIi() {
        Ii plannedActivityIi = IiConverter.convertToPlannedActivityIi(null);
        verifyNullObjectIi(plannedActivityIi, IiConverter.PLANNED_ACTIVITY_ROOT,
                           IiConverter.PLANNED_ACTIVITY_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToPlannedActivityIi() {
        Ii plannedActivityIi = IiConverter.convertToPlannedActivityIi(1234L);
        assertEquals(makePlannedActivityIi("1234"), plannedActivityIi);
    }

    @Test
    public void testConvertNullToPoClinicalResearchStaffIi() {
        Ii poClinicalResearchStaffIi = IiConverter.convertToPoClinicalResearchStaffIi(null);
        verifyNullObjectIi(poClinicalResearchStaffIi, IiConverter.CLINICAL_RESEARCH_STAFF_ROOT,
                           IiConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToPoClinicalResearchStaffIi() {
        Ii poClinicalResearchStaffIi = IiConverter.convertToPoClinicalResearchStaffIi("1234");
        assertEquals(makePoClinicalResearchStaffIi("1234"), poClinicalResearchStaffIi);
    }

    @Test
    public void testConvertNullToPoFamilyIi() {
        Ii poFamilyIi = IiConverter.convertToPoFamilyIi(null);
        verifyNullObjectIi(poFamilyIi, IiConverter.FAMILY_ROOT, IiConverter.FAMILY_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToPoFamilyIi() {
        Ii poFamilyIi = IiConverter.convertToPoFamilyIi("1234");
        assertEquals(makePoFamilyIi("1234"), poFamilyIi);
    }

    @Test
    public void testConvertNullToPoHealthcareProviderIi() {
        Ii poHealthcareProviderIi = IiConverter.convertToPoHealthcareProviderIi(null);
        verifyNullObjectIi(poHealthcareProviderIi, IiConverter.HEALTH_CARE_PROVIDER_ROOT,
                           IiConverter.HEALTH_CARE_PROVIDER_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToPoHealthcareProviderIi() {
        Ii poHealthcareProviderIi = IiConverter.convertToPoHealthcareProviderIi("1234");
        assertEquals(makeHealthcareProviderIi("1234"), poHealthcareProviderIi);
    }

    @Test
    public void testConvertNullToPoHealthCareFacilityIi() {
        Ii poHealthCareFacilityIi = IiConverter.convertToPoHealthCareFacilityIi(null);
        verifyNullObjectIi(poHealthCareFacilityIi, IiConverter.HEALTH_CARE_FACILITY_ROOT,
                           IiConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToPoHealthCareFacilityIi() {
        Ii poHealthCareFacilityIi = IiConverter.convertToPoHealthCareFacilityIi("1234");
        assertEquals(makeHealthcareFacilityIi("1234"), poHealthCareFacilityIi);
    }

    @Test
    public void testConvertNullToPoOrganizationalContactIi() {
        Ii poOrganizationalContactIi = IiConverter.convertToPoOrganizationalContactIi(null);
        verifyNullObjectIi(poOrganizationalContactIi, IiConverter.ORGANIZATIONAL_CONTACT_ROOT,
                           IiConverter.ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToPoOrganizationalContactIi() {
        Ii poOrganizationalContactIi = IiConverter.convertToPoOrganizationalContactIi("1234");
        assertEquals(makePoOrganizationalContactIi("1234"), poOrganizationalContactIi);
    }

    @Test
    public void testConvertNullToPoOrganizationIi() {
        Ii poOrganizationIi = IiConverter.convertToPoOrganizationIi(null);
        verifyNullObjectIi(poOrganizationIi, IiConverter.ORG_ROOT, IiConverter.ORG_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToPoOrganizationIi() {
        Ii poOrganizationIi = IiConverter.convertToPoOrganizationIi("1234");
        assertEquals(makePoOrganizationIi("1234"), poOrganizationIi);
    }

    @Test
    public void testConvertNullToPoOversightCommitteeIi() {
        Ii poOversightCommitteeIi = IiConverter.convertToPoOversightCommitteeIi(null);
        verifyNullObjectIi(poOversightCommitteeIi, IiConverter.OVERSIGHT_COMMITTEE_ROOT,
                           IiConverter.OVERSIGHT_COMMITTEE_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToPoOversightCommitteeIi() {
        Ii poOversightCommitteeIi = IiConverter.convertToPoOversightCommitteeIi("1234");
        assertEquals(makePoOversightCommitteeIi("1234"), poOversightCommitteeIi);
    }

    @Test
    public void testConvertNullToPoPatientIi() {
        Ii poPatientIi = IiConverter.convertToPoPatientIi(null);
        verifyNullObjectIi(poPatientIi, IiConverter.PO_PATIENT_ROOT, IiConverter.PO_PATIENT_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToPoPatientIi() {
        Ii poPatientIi = IiConverter.convertToPoPatientIi(1234L);
        assertEquals(makePoPatientIi("1234"), poPatientIi);
    }

    @Test
    public void testConvertNullToPoPersonIi() {
        Ii poPersonIi = IiConverter.convertToPoPersonIi(null);
        verifyNullObjectIi(poPersonIi, IiConverter.PERSON_ROOT, IiConverter.PERSON_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToPoPersonIi() {
        Ii poPersonIi = IiConverter.convertToPoPersonIi("1234");
        assertEquals(makePersonPoIi("1234"), poPersonIi);
    }

    @Test
    public void testConvertNullToPoResearchOrganizationIi() {
        Ii poResearchOrganizationIi = IiConverter.convertToPoResearchOrganizationIi(null);
        verifyNullObjectIi(poResearchOrganizationIi, IiConverter.RESEARCH_ORG_ROOT,
                           IiConverter.RESEARCH_ORG_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToPoResearchOrganizationIi() {
        Ii poResearchOrganizationIi = IiConverter.convertToPoResearchOrganizationIi("1234");
        assertEquals(makePoResearchOrganizationIi("1234"), poResearchOrganizationIi);
    }

    @Test
    public void testConvertNullToRegulatoryAuthorityIi() {
        Ii RegulatoryAuthorityIi = IiConverter.convertToRegulatoryAuthorityIi(null);
        verifyNullObjectIi(RegulatoryAuthorityIi, IiConverter.REGULATORY_AUTHORITY_ROOT,
                           IiConverter.REGULATORY_AUTHORITY_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToRegulatoryAuthorityIi() {
        Ii RegulatoryAuthorityIi = IiConverter.convertToRegulatoryAuthorityIi(1234L);
        assertEquals(makeRegulatoryAuthorityIi("1234"), RegulatoryAuthorityIi);
    }

    @Test
    public void testConvertNullToStratumGroupIi() {
        Ii stratumGroupIi = IiConverter.convertToStratumGroupIi(null);
        verifyNullObjectIi(stratumGroupIi, IiConverter.STRATUM_GROUP_ROOT, IiConverter.STRATUM_GROUP_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStratumGroupIi() {
        Ii stratumGroupIi = IiConverter.convertToStratumGroupIi(1234L);
        assertEquals(makeStratumGroupIi("1234"), stratumGroupIi);
    }

    @Test
    public void testConvertNullToStudyContactIi() {
        Ii studyContactIi = IiConverter.convertToStudyContactIi(null);
        verifyNullObjectIi(studyContactIi, IiConverter.STUDY_CONTACT_ROOT, IiConverter.STUDY_CONTACT_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStudyContactIi() {
        Ii studyContactIi = IiConverter.convertToStudyContactIi(1234L);
        assertEquals(makeStudyContactIi("1234"), studyContactIi);
    }

    @Test
    public void testConvertNullToStudyDiseaseIi() {
        Ii studyDiseaseIi = IiConverter.convertToStudyDiseaseIi(null);
        verifyNullObjectIi(studyDiseaseIi, IiConverter.STUDY_DISEASE_ROOT, IiConverter.STUDY_DISEASE_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStudyDiseaseIi() {
        Ii studyDiseaseIi = IiConverter.convertToStudyDiseaseIi(1234L);
        assertEquals(makeStudyDiseaseIi("1234"), studyDiseaseIi);
    }

    @Test
    public void testConvertNullToStudyIndIdeIi() {
        Ii studyIndIdeIi = IiConverter.convertToStudyIndIdeIi(null);
        verifyNullObjectIi(studyIndIdeIi, IiConverter.STUDY_IND_IDE_ROOT, IiConverter.STUDY_IND_IDE_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStudyIndIdeIi() {
        Ii studyIndIdeIi = IiConverter.convertToStudyIndIdeIi(1234L);
        assertEquals(makeStudyIndIdeIi("1234"), studyIndIdeIi);
    }

    @Test
    public void testConvertNullToStudyMilestoneIi() {
        Ii studyMilestoneIi = IiConverter.convertToStudyMilestoneIi(null);
        verifyNullObjectIi(studyMilestoneIi, IiConverter.STUDY_MILESTONE_ROOT,
                           IiConverter.STUDY_MILESTONE_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStudyMilestoneIi() {
        Ii studyMilestoneIi = IiConverter.convertToStudyMilestoneIi(1234L);
        assertEquals(makeStudyMilestoneIi("1234"), studyMilestoneIi);
    }

    @Test
    public void testConvertNullToStudyObjectiveIi() {
        Ii studyObjectiveIi = IiConverter.convertToStudyObjectiveIi(null);
        verifyNullObjectIi(studyObjectiveIi, IiConverter.STUDY_OBJECTIVE_ROOT,
                           IiConverter.STUDY_OBJECTIVE_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStudyObjectiveIi() {
        Ii studyObjectiveIi = IiConverter.convertToStudyObjectiveIi(1234L);
        assertEquals(makeStudyObjectiveIi("1234"), studyObjectiveIi);
    }

    @Test
    public void testConvertNullToStudyOnHoldIi() {
        Ii studyOnHoldIi = IiConverter.convertToStudyOnHoldIi(null);
        verifyNullObjectIi(studyOnHoldIi, IiConverter.STUDY_ONHOLD_ROOT, IiConverter.STUDY_ONHOLD_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStudyOnHoldIi() {
        Ii studyOnHoldIi = IiConverter.convertToStudyOnHoldIi(1234L);
        assertEquals(makeStudyOnHoldIi("1234"), studyOnHoldIi);
    }

    @Test
    public void testConvertNullToStudyOutcomeMeasureIi() {
        Ii studyOutcomeMeasureIi = IiConverter.convertToStudyOutcomeMeasureIi(null);
        verifyNullObjectIi(studyOutcomeMeasureIi, IiConverter.STUDY_OUTCOME_MEASURE_ROOT,
                           IiConverter.STUDY_OUTCOME_MEASURE_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStudyOutcomeMeasureIi() {
        Ii studyOutcomeMeasureIi = IiConverter.convertToStudyOutcomeMeasureIi(1234L);
        assertEquals(makeStudyOutcomeMeasureIi("1234"), studyOutcomeMeasureIi);
    }

    @Test
    public void testConvertNullToStudyOverallStatusIi() {
        Ii studyOverallStatusIi = IiConverter.convertToStudyOverallStatusIi(null);
        verifyNullObjectIi(studyOverallStatusIi, IiConverter.STUDY_OVERALL_STATUS_ROOT,
                           IiConverter.STUDY_OVERALL_STATUS_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStudyOverallStatusIi() {
        Ii studyOverallStatusIi = IiConverter.convertToStudyOverallStatusIi(1234L);
        assertEquals(makeStudyOverallStatusIi("1234"), studyOverallStatusIi);
    }

    @Test
    public void testConvertNullToStudyProtocolIi() {
        Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(null);
        verifyNullObjectIi(studyProtocolIi, IiConverter.STUDY_PROTOCOL_ROOT, IiConverter.STUDY_PROTOCOL_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStudyProtocolIi() {
        Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(1234L);
        assertEquals(makeStudyProtocolIi("1234"), studyProtocolIi);
    }

    @Test
    public void testConvertNullToStudyRecruitmentStatusIi() {
        Ii studyRecruitmentStatusIi = IiConverter.convertToStudyRecruitmentStatusIi(null);
        verifyNullObjectIi(studyRecruitmentStatusIi, IiConverter.STUDY_RECRUITMENT_STATUS_ROOT,
                           IiConverter.STUDY_RECRUITMENT_STATUS_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStudyRecruitmentStatusIi() {
        Ii studyRecruitmentStatusIi = IiConverter.convertToStudyRecruitmentStatusIi(1234L);
        assertEquals(makeStudyRecruitmentStatusIi("1234"), studyRecruitmentStatusIi);
    }

    @Test
    public void testConvertNullToStudyRegulatoryAuthorityIi() {
        Ii studyRegulatoryAuthorityIi = IiConverter.convertToStudyRegulatoryAuthorityIi(null);
        verifyNullObjectIi(studyRegulatoryAuthorityIi, IiConverter.STUDY_REGULATORY_AUTHORITY_ROOT,
                           IiConverter.STUDY_REGULATORY_AUTHORITY_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStudyRegulatoryAuthorityIi() {
        Ii studyRegulatoryAuthorityIi = IiConverter.convertToStudyRegulatoryAuthorityIi(1234L);
        assertEquals(makeStudyRegulatoryAuthorityIi("1234"), studyRegulatoryAuthorityIi);
    }

    @Test
    public void testConvertNullToStudyRelationshipIi() {
        Ii studyRelationshipIi = IiConverter.convertToStudyRelationshipIi(null);
        verifyNullObjectIi(studyRelationshipIi, IiConverter.STUDY_RELATIONSHIP_ROOT,
                           IiConverter.STUDY_RELATIONSHIP_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStudyRelationshipIi() {
        Ii studyRelationshipIi = IiConverter.convertToStudyRelationshipIi(1234L);
        assertEquals(makeStudyRelationshipIi("1234"), studyRelationshipIi);
    }

    @Test
    public void testConvertNullToStudyResourcingIi() {
        Ii studyResourcingIi = IiConverter.convertToStudyResourcingIi(null);
        verifyNullObjectIi(studyResourcingIi, IiConverter.STUDY_RESOURCING_ROOT,
                           IiConverter.STUDY_RESOURCING_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStudyResourcingIi() {
        Ii studyResourcingIi = IiConverter.convertToStudyResourcingIi(1234L);
        assertEquals(makeStudyResourcingIi("1234"), studyResourcingIi);
    }

    @Test
    public void testConvertNullToStudySiteAccrualStatusIi() {
        Ii studySiteAccrualStatusIi = IiConverter.convertToStudySiteAccrualStatusIi(null);
        verifyNullObjectIi(studySiteAccrualStatusIi, IiConverter.STUDY_SITE_ACCRUAL_STATUS_ROOT,
                           IiConverter.STUDY_SITE_ACCRUAL_STATUS_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStudySiteAccrualStatusIi() {
        Ii studySiteAccrualStatusIi = IiConverter.convertToStudySiteAccrualStatusIi(1234L);
        assertEquals(makeStudySiteAccrualStatusIi("1234"), studySiteAccrualStatusIi);
    }

    @Test
    public void testConvertNullToStudySiteContactIi() {
        Ii studySiteContactIi = IiConverter.convertToStudySiteContactIi(null);
        verifyNullObjectIi(studySiteContactIi, IiConverter.STUDY_SITE_CONTACT_ROOT,
                           IiConverter.STUDY_SITE_CONTACT_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStudySiteContactIi() {
        Ii studySiteContactIi = IiConverter.convertToStudySiteContactIi(1234L);
        assertEquals(makeStudySiteContactIi("1234"), studySiteContactIi);
    }

    @Test
    public void testConvertNullToStudySiteIi() {
        Ii studySiteIi = IiConverter.convertToStudySiteIi(null);
        verifyNullObjectIi(studySiteIi, IiConverter.STUDY_SITE_ROOT, IiConverter.STUDY_SITE_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStudySiteIi() {
        Ii studySiteIi = IiConverter.convertToStudySiteIi(1234L);
        assertEquals(makeStudySiteIi("1234"), studySiteIi);
    }

    @Test
    public void testConvertNullToStudySiteOverallStatusIi() {
        Ii studySiteOverallStatusIi = IiConverter.convertToStudySiteOverallStatusIi(null);
        verifyNullObjectIi(studySiteOverallStatusIi, IiConverter.STUDY_SITE_OVERALL_STATUS_ROOT,
                           IiConverter.STUDY_SITE_OVERALL_STATUS_IDENTIFIER_NAME);
    }

    @Test
    public void testConvertNonNullToStudySiteOverallStatusIi() {
        Ii studySiteOverallStatusIi = IiConverter.convertToStudySiteOverallStatusIi(1234L);
        assertEquals(makeStudySiteOverallStatusIi("1234"), studySiteOverallStatusIi);
    }

    @Test
    public void convertNullToSubjectAccrualIi() {
        Ii subjectAccrualIi = IiConverter.convertToSubjectAccrualIi(null);
        verifyNullObjectIi(subjectAccrualIi, IiConverter.SUBJECT_ACCRUAL_ROOT,
                           IiConverter.SUBJECT_ACCRUAL_IDENTIFIER_NAME);
    }

    @Test
    public void convertNonNullToSubjectAccrualIi() {
        Ii subjectAccrualIi = IiConverter.convertToSubjectAccrualIi(1234L);
        assertEquals(makeSubjectAccrualIi("1234"), subjectAccrualIi);
    }

    /**
     * An "Object II" is an II that is configured with the root and identifier name for a specific object, rather than a
     * generic II.
     */
    private void verifyNullObjectIi(Ii objectIi, String expectedRoot, String expectedIdentifierName) {
        assertTrue("Null II shouldn't have extension set", StringUtils.isEmpty(objectIi.getExtension()));
        assertEquals("Null II should have root set", expectedRoot, objectIi.getRoot());
        assertEquals("Null II should have identifier name set", expectedIdentifierName, objectIi.getIdentifierName());
        assertEquals("Null II should have NI null flavor", NullFlavor.NI, objectIi.getNullFlavor());
    }

    public static Ii makeActivityIi(String extension) {
        return makeIi(extension, IiConverter.ACTIVITY_ROOT, IiConverter.ACTIVITY_IDENTIFIER_NAME);
    }

    public static Ii makeArmIi(String extension) {
        return makeIi(extension, IiConverter.ARM_ROOT, IiConverter.ARM_IDENTIFIER_NAME);
    }

    public static Ii makeAssignedIdentifierIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_PROTOCOL_ROOT, IiConverter.STUDY_PROTOCOL_IDENTIFIER_NAME);
    }

    public static Ii makeCountryIi(String extension) {
        return makeIi(extension, IiConverter.COUNTRY_ROOT, IiConverter.COUNTRY_IDENTIFIER_NAME);
    }

    public static Ii makeDocumentIi(String extension) {
        return makeIi(extension, IiConverter.DOCUMENT_ROOT, IiConverter.DOCUMENT_IDENTIFIER_NAME);
    }

    public static Ii makeDocumentWorkflowStatusIi(String extension) {
        return makeIi(extension, IiConverter.DOCUMENT_WORKFLOW_STATUS_ROOT,
                      IiConverter.DOCUMENT_WORKFLOW_STATUS_IDENTIFIER_NAME);
    }

    public static Ii makeIdentifiedOrgIi(String extension) {
        return makeIi(extension, IiConverter.CTEP_ORG_IDENTIFIER_ROOT, IiConverter.CTEP_ORG_IDENTIFIER_NAME);
    }

    public static Ii makeIdentifiedPersonIi(String extension) {
        return makeIi(extension, IiConverter.CTEP_PERSON_IDENTIFIER_ROOT, IiConverter.CTEP_PERSON_IDENTIFIER_NAME);
    }

    public static Ii makeOtherIdentifierIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT,
                      IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME);
    }

    public static Ii makePaOrganizationIi(String extension) {
        return makeIi(extension, IiConverter.ORG_ROOT, IiConverter.ORG_PA_IDENTIFIER_NAME);
    }

    public static Ii makePaPersonIi(String extension) {
        return makeIi(extension, IiConverter.PERSON_ROOT, IiConverter.PERSON_PA_IDENTIFIER_NAME);
    }

    public static Ii makePlannedActivityIi(String extension) {
        return makeIi(extension, IiConverter.PLANNED_ACTIVITY_ROOT, IiConverter.PLANNED_ACTIVITY_IDENTIFIER_NAME);
    }

    public static Ii makePoClinicalResearchStaffIi(String extension) {
        return makeIi(extension, IiConverter.CLINICAL_RESEARCH_STAFF_ROOT,
                      IiConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME);
    }

    public static Ii makePoFamilyIi(String extension) {
        return makeIi(extension, IiConverter.FAMILY_ROOT, IiConverter.FAMILY_IDENTIFIER_NAME);
    }

    public static Ii makeHealthcareProviderIi(String extension) {
        return makeIi(extension, IiConverter.HEALTH_CARE_PROVIDER_ROOT,
                      IiConverter.HEALTH_CARE_PROVIDER_IDENTIFIER_NAME);
    }

    public static Ii makeHealthcareFacilityIi(String extension) {
        return makeIi(extension, IiConverter.HEALTH_CARE_FACILITY_ROOT,
                      IiConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
    }

    public static Ii makePoOrganizationalContactIi(String extension) {
        return makeIi(extension, IiConverter.ORGANIZATIONAL_CONTACT_ROOT,
                      IiConverter.ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME);
    }

    public static Ii makePoOrganizationIi(String extension) {
        return makeIi(extension, IiConverter.ORG_ROOT, IiConverter.ORG_IDENTIFIER_NAME);
    }

    public static Ii makePoOversightCommitteeIi(String extension) {
        return makeIi(extension, IiConverter.OVERSIGHT_COMMITTEE_ROOT, IiConverter.OVERSIGHT_COMMITTEE_IDENTIFIER_NAME);
    }

    public static Ii makePoPatientIi(String extension) {
        return makeIi(extension, IiConverter.PO_PATIENT_ROOT, IiConverter.PO_PATIENT_IDENTIFIER_NAME);
    }

    public static Ii makePersonPoIi(String extension) {
        return makeIi(extension, IiConverter.PERSON_ROOT, IiConverter.PERSON_IDENTIFIER_NAME);
    }

    public static Ii makePoResearchOrganizationIi(String extension) {
        return makeIi(extension, IiConverter.RESEARCH_ORG_ROOT, IiConverter.RESEARCH_ORG_IDENTIFIER_NAME);
    }

    public static Ii makeRegulatoryAuthorityIi(String extension) {
        return makeIi(extension, IiConverter.REGULATORY_AUTHORITY_ROOT,
                      IiConverter.REGULATORY_AUTHORITY_IDENTIFIER_NAME);
    }

    public static Ii makeStratumGroupIi(String extension) {
        return makeIi(extension, IiConverter.STRATUM_GROUP_ROOT, IiConverter.STRATUM_GROUP_IDENTIFIER_NAME);
    }

    public static Ii makeStudyContactIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_CONTACT_ROOT, IiConverter.STUDY_CONTACT_IDENTIFIER_NAME);
    }

    public static Ii makeStudyDiseaseIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_DISEASE_ROOT, IiConverter.STUDY_DISEASE_IDENTIFIER_NAME);
    }

    public static Ii makeStudyIndIdeIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_IND_IDE_ROOT, IiConverter.STUDY_IND_IDE_IDENTIFIER_NAME);
    }

    public static Ii makeStudyMilestoneIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_MILESTONE_ROOT, IiConverter.STUDY_MILESTONE_IDENTIFIER_NAME);
    }

    public static Ii makeStudyObjectiveIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_OBJECTIVE_ROOT, IiConverter.STUDY_OBJECTIVE_IDENTIFIER_NAME);
    }

    public static Ii makeStudyOnHoldIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_ONHOLD_ROOT, IiConverter.STUDY_ONHOLD_IDENTIFIER_NAME);
    }

    public static Ii makeStudyOutcomeMeasureIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_OUTCOME_MEASURE_ROOT,
                      IiConverter.STUDY_OUTCOME_MEASURE_IDENTIFIER_NAME);
    }

    public static Ii makeStudyOverallStatusIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_OVERALL_STATUS_ROOT,
                      IiConverter.STUDY_OVERALL_STATUS_IDENTIFIER_NAME);
    }

    public static Ii makeStudyProtocolIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_PROTOCOL_ROOT, IiConverter.STUDY_PROTOCOL_IDENTIFIER_NAME);
    }

    public static Ii makeStudyRecruitmentStatusIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_RECRUITMENT_STATUS_ROOT,
                      IiConverter.STUDY_RECRUITMENT_STATUS_IDENTIFIER_NAME);
    }

    public static Ii makeStudyRegulatoryAuthorityIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_REGULATORY_AUTHORITY_ROOT,
                      IiConverter.STUDY_REGULATORY_AUTHORITY_IDENTIFIER_NAME);
    }

    public static Ii makeStudyRelationshipIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_RELATIONSHIP_ROOT, IiConverter.STUDY_RELATIONSHIP_IDENTIFIER_NAME);
    }

    public static Ii makeStudyResourcingIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_RESOURCING_ROOT, IiConverter.STUDY_RESOURCING_IDENTIFIER_NAME);
    }

    public static Ii makeStudySiteAccrualStatusIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_SITE_ACCRUAL_STATUS_ROOT,
                      IiConverter.STUDY_SITE_ACCRUAL_STATUS_IDENTIFIER_NAME);
    }

    public static Ii makeStudySiteContactIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_SITE_CONTACT_ROOT, IiConverter.STUDY_SITE_CONTACT_IDENTIFIER_NAME);
    }

    public static Ii makeStudySiteIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_SITE_ROOT, IiConverter.STUDY_SITE_IDENTIFIER_NAME);
    }

    public static Ii makeStudySiteOverallStatusIi(String extension) {
        return makeIi(extension, IiConverter.STUDY_SITE_OVERALL_STATUS_ROOT,
                      IiConverter.STUDY_SITE_OVERALL_STATUS_IDENTIFIER_NAME);
    }

    public static Ii makeSubjectAccrualIi(String extension) {
        return makeIi(extension, IiConverter.SUBJECT_ACCRUAL_ROOT, IiConverter.SUBJECT_ACCRUAL_IDENTIFIER_NAME);
    }

    private static Ii makeIi(String extension, String root, String identifierName) {
        Ii ii = new Ii();
        ii.setRoot(root);
        ii.setIdentifierName(identifierName);
        ii.setExtension(extension);
        return ii;
    }

}

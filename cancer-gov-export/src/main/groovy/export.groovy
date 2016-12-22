import groovy.sql.Sql
import groovy.xml.MarkupBuilder
import groovy.xml.StreamingMarkupBuilder
import org.apache.commons.lang3.StringUtils
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

def resolvedProperties = [:]

if (!properties['ant.home']) {
	println 'We are not running from Ant; so accepting properties passed to JVM'
	resolvedProperties << System.getProperties()
} else
	resolvedProperties << properties

def poJdbcUrl = "jdbc:postgresql://${resolvedProperties['database.server']}:${resolvedProperties['database.port']}/${resolvedProperties['database.name']}"
def paJdbcUrl = "jdbc:postgresql://${resolvedProperties['db.server']}:${resolvedProperties['db.port']}/${resolvedProperties['db.name']}"

println "PO DB: ${poJdbcUrl}"
println "PA DB: ${paJdbcUrl}"

def poConn = Sql.newInstance(poJdbcUrl, resolvedProperties['database.user'],
		resolvedProperties['po.db.password'], 'org.postgresql.Driver')
def paConn = Sql.newInstance(paJdbcUrl, , resolvedProperties['db.username'],
		resolvedProperties['pa.db.password'], 'org.postgresql.Driver')

// First, create log table if needed
paConn.executeUpdate("""
create table if not exists cancer_gov_export_log (
	study_protocol_id int8,
	nci_id varchar(32) NOT NULL,
	nct_id varchar(32) NOT NULL,
	datetime timestamp NOT NULL DEFAULT current_timestamp,
	xml text NOT NULL,
    lastchanged_date timestamp,
	CONSTRAINT study_protocol_id FOREIGN KEY (study_protocol_id) REFERENCES study_protocol(identifier) ON DELETE SET NULL	
)
"""
		)


// Cache some PO data...
def preLoader = new PoPreLoad(poConn)
def orgsMap = preLoader.getOrgsMap()
println "got Orgs " + orgsMap.size()

def getTrialsSQL = """
    select sp.identifier,
        nci_id.extension as nciId,
        ctepSs.local_sp_indentifier as ctepId,
        dcpSs.local_sp_indentifier as dcpId,
		ccr.local_sp_indentifier as ccrId,
        rv_trial_id_nct.local_sp_indentifier as nctId,
        leadOrgSs.local_sp_indentifier as leadOrgId,        
		leadOrgSs.name as leadOrgName,
		sponsorSs.name as sponsorOrgName,
		submitter.submitter_org_name as source,                
        sp.public_tittle as brief_title,
        sp.public_description as brief_summary,
        sp.scientific_description as detailed_description,
        sp.official_title,
		sp.acronym,		
		rp_sponsor.assigned_identifier as rp_sponsor_po_id,
        central_contact.email as centralContactEmail,
        central_contact.telephone as centralContactPhone,        
        ra_country.name || ': ' || ra.authority_name as reg_authority,
		ra_country.identifier as reg_authority_country_id,
        CASE
            WHEN sos.status_code = 'APPROVED' then 'Not yet recruiting'
            WHEN sos.status_code = 'IN_REVIEW' then 'Not yet recruiting'
            WHEN sos.status_code = 'ACTIVE' then 'Recruiting'
            WHEN sos.status_code = 'ENROLLING_BY_INVITATION' then 'Enrolling by invitation'
            WHEN sos.status_code = 'CLOSED_TO_ACCRUAL' then 'Active, not recruiting'
            WHEN sos.status_code = 'CLOSED_TO_ACCRUAL_AND_INTERVENTION' then 'Active, not recruiting'
            WHEN sos.status_code = 'TEMPORARILY_CLOSED_TO_ACCRUAL' then 'Suspended'
            WHEN sos.status_code = 'TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION' then 'Suspended'
            WHEN sos.status_code = 'WITHDRAWN' then 'Withdrawn'
            WHEN sos.status_code = 'ADMINISTRATIVELY_COMPLETE' then 'Terminated'
            WHEN sos.status_code = 'COMPLETED' then 'Completed'
            WHEN sos.status_code = 'COMPLETE' then 'Completed'
        END as current_trial_status,
		CASE
            WHEN sos.status_code in ('TEMPORARILY_CLOSED_TO_ACCRUAL','TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION',
				'WITHDRAWN','ADMINISTRATIVELY_COMPLETE') THEN sos.comment_text			
        END as why_stopped,
        sos.status_date::date as current_trial_status_date,
        sp.start_date:: date as start_date,
        CASE
            WHEN sp.start_date_type_code = 'ACTUAL' then 'Actual'
            WHEN sp.start_date_type_code = 'ANTICIPATED' then 'Anticipated'
        END as start_date_type_code,
        sp.completion_date::date as completion_date,
        CASE
            WHEN sp.completion_date_type_code = 'ACTUAL' then 'Actual'
            WHEN sp.completion_date_type_code = 'ANTICIPATED' then 'Anticipated'
        END as completion_date_type_code,
		sp.pri_compl_date::date as pri_compl_date,
        CASE
            WHEN sp.pri_compl_date_type_code = 'ACTUAL' then 'Actual'
            WHEN sp.pri_compl_date_type_code = 'ANTICIPATED' then 'Anticipated'
        END as pri_compl_date_type_code,   
        pp.code as primary_purpose_code,
		CASE
            WHEN sp.phase_code = 'O' then 'Phase 0'           
			WHEN sp.phase_code = 'I' then 'Phase 1'
			WHEN sp.phase_code = 'I_II' then 'Phase 1/Phase 2'
			WHEN sp.phase_code = 'II' then 'Phase 2'
			WHEN sp.phase_code = 'II_III' then 'Phase 2/Phase 3'
			WHEN sp.phase_code = 'III' then 'Phase 3'
			WHEN sp.phase_code = 'IV' then 'Phase 4'
			WHEN sp.phase_code = 'NA' then 'N/A'
        END as phase_code,   
		CASE
            WHEN sp.study_protocol_type = 'NonInterventionalStudyProtocol' then 'Observational'
			WHEN sp.study_protocol_type = 'InterventionalStudyProtocol' AND sp.expd_access_indidicator=true then 'Expanded Access'
            ELSE 'Interventional'
        END as study_type,
        CASE
            WHEN sp.allocation_code = 'RANDOMIZED_CONTROLLED_TRIAL' then 'Randomized'
            WHEN sp.allocation_code = 'NON_RANDOMIZED_TRIAL' then 'Non-randomized'
			WHEN sp.allocation_code = 'NA' then 'N/A'			
        END as allocation_code,
        CASE
            WHEN sp.blinding_schema_code = 'OPEN' then 'Open Label'
            WHEN sp.blinding_schema_code = 'SINGLE_BLIND' then 'Single Blind'
            WHEN sp.blinding_schema_code = 'DOUBLE_BLIND' then 'Double Blind'
        END as blinding_schema_code,
        CASE
            WHEN sp.design_configuration_code = 'SINGLE_GROUP' then 'Single Group Assignment'
            WHEN sp.design_configuration_code = 'PARALLEL' then 'Parallel Assignment'
            WHEN sp.design_configuration_code = 'CROSSOVER' then 'Crossover Assignment'
            WHEN sp.design_configuration_code = 'FACTORIAL' then 'Factorial Assignment'
        END as design_configuration_code,
		CASE
            WHEN sp.study_model_code = 'CASE_ONLY' then 'Case-Only'
			WHEN sp.study_model_code = 'COHORT' then 'Cohort'
			WHEN sp.study_model_code = 'CASE_CONTROL' then 'Case Control'
			WHEN sp.study_model_code = 'FAMILY_BASED' then 'Family-Based'
			WHEN sp.study_model_code = 'OTHER' then 'Other'
			WHEN sp.study_model_code = 'ECOLOGIC_OR_COMMUNITY_STUDIES' then 'Ecologic or Community'
			WHEN sp.study_model_code = 'CASE_CROSSOVER' then 'Case-Crossover'
        END as study_model_code,
		CASE
            WHEN sp.time_perspective_code = 'PROSPECTIVE' then 'Prospective'
			WHEN sp.time_perspective_code = 'RETROSPECTIVE' then 'Retrospective'
			WHEN sp.time_perspective_code = 'CROSS_SECTION' then 'Cross-Sectional'
			WHEN sp.time_perspective_code = 'OTHER' then 'Other'
        END as time_perspective_code,
        sp.number_of_intervention_groups,
		sp.number_of_groups,
        CASE
            WHEN sp.min_target_accrual_num is null then 0
            ELSE sp.min_target_accrual_num
        END as min_target_accrual_num,        
		pi.assigned_identifier as pi_po_id,
		pi.first_name as pi_first_name,
		pi.last_name as pi_last_name,
		pi.middle_name as pi_middle_name,
        
		central_contact_person.assigned_identifier as cc_po_id,
		central_contact_person.first_name as cc_first_name,
		central_contact_person.last_name as cc_last_name,
		central_contact_person.middle_name as cc_middle_name,
        
        CASE WHEN sp.proprietary_trial_indicator then 'Abbreviated'
                ELSE 'Complete'
            END as category,
        CASE WHEN sp.fda_regulated_indicator THEN 'Yes'
                ELSE 'No'
            END as fda_indicator,
        CASE WHEN sp.section801_indicator THEN 'Yes'
                 ELSE 'No'
            END as section801_indicator,
        CASE WHEN sp.delayed_posting_indicator THEN 'Yes'
                 ELSE 'No'
            END as delayed_posting_indicator,
        CASE WHEN sp.data_monty_comty_apptn_indicator THEN 'Yes'
                 ELSE 'No'
            END as dmc_indicator,
		CASE
            WHEN sp.expd_access_indidicator=true then 'Yes'			
            ELSE 'No'
        END as has_expanded_access,
        CASE WHEN sp.accept_healthy_volunteers_indicator THEN 'Yes'
                 ELSE 'No'
            END as healthy_volunteer_indicator,        
        sp.blinding_role_code_subject,
        sp.blinding_role_code_caregiver,
        sp.blinding_role_code_investigator,
        sp.blinding_role_code_outcome,
        CASE
            WHEN sp.study_classification_code = 'EFFICACY' then 'Efficacy Study'
            WHEN sp.study_classification_code = 'SAFETY_OR_EFFICACY' then 'Safety/Efficacy Study'
            WHEN sp.study_classification_code = 'BIO_EQUIVALENCE' then 'Bio-equivalence Study'
            WHEN sp.study_classification_code = 'BIO_AVAILABILITY' then 'Bio-availability Study'
            WHEN sp.study_classification_code = 'PHARMACOKINETICS' then 'Pharmacokinetics Study'
            WHEN sp.study_classification_code = 'PHARMACODYNAMICS' then 'Pharmacodynamics Study'
            WHEN sp.study_classification_code = 'PHARMACOKINETICS_OR_DYNAMICS' then 'Pharmacokinetics/dynamics Study'
            WHEN sp.study_classification_code = 'SAFETY' then 'Safety Study'
			WHEN sp.study_classification_code = 'NA' then 'N/A'
        END as classification_code,
		CASE
            WHEN sp.bio_specimen_retention_code = 'SAMPLES_WITH_DNA' then 'Samples With DNA'
            WHEN sp.bio_specimen_retention_code = 'SAMPLES_WITHOUT_DNA' then 'Samples Without DNA'
            WHEN sp.bio_specimen_retention_code = 'NONE_RETAINED' then 'None Retained'
        END as biospec_retention,
		sp.bio_specimen_description as biospec_descr,
		sp.study_population_description as study_pop,
		CASE
            WHEN sp.sampling_method_code = 'NON_PROBABILITY_SAMPLE' then 'Non-Probability Sample'
            WHEN sp.sampling_method_code = 'PROBABILITY_SAMPLE' then 'Probability Sample'            
        END as sampling_method,
	 sp.record_verification_date as verification_date,
	 sp.keyword_text as keywords,
     sp.date_last_created as date_last_created		
     from study_protocol sp
     inner join rv_trial_id_nct on rv_trial_id_nct.study_protocol_identifier = sp.identifier
     left join rv_sponsor_organization sponsorSs on sponsorSs.study_protocol_identifier = sp.identifier 
     inner join rv_dwf_current dws on dws.study_protocol_identifier = sp.identifier
        and dws.status_code in ('ABSTRACTED','VERIFICATION_PENDING','ABSTRACTION_VERIFIED_NORESPONSE', 'ABSTRACTION_VERIFIED_RESPONSE')        
     inner join rv_trial_id_nci as nci_id on nci_id.study_protocol_id = sp.identifier       
     left outer join rv_ctep_id ctepSs on ctepSs.study_protocol_identifier = sp.identifier 
     left outer join rv_dcp_id dcpSs on dcpSs.study_protocol_identifier = sp.identifier 
	 left outer join rv_ccr_id ccr on ccr.study_protocol_identifier = sp.identifier
     left outer join rv_lead_organization leadOrgSs on leadOrgSs.study_protocol_identifier = sp.identifier
	 left outer join rv_trial_submitter submitter on submitter.study_protocol_identifier = sp.identifier
     left outer join study_regulatory_authority sra on sra.study_protocol_identifier = sp.identifier
     left outer join regulatory_authority ra on ra.identifier = sra.regulatory_authority_identifier
     left outer join country ra_country on ra_country.identifier = ra.country_identifier
     join study_overall_status sos on sos.study_protocol_identifier = sp.identifier
          and sos.identifier = (SELECT sos2.identifier FROM study_overall_status sos2 WHERE sos2.study_protocol_identifier = sp.identifier
                                AND sos2.deleted = false ORDER BY sos2.status_date DESC, sos2.identifier DESC LIMIT 1)
     left outer join study_contact ov_off on ov_off.study_protocol_identifier = sp.identifier and ov_off.role_code = 'STUDY_PRINCIPAL_INVESTIGATOR'
     left outer join clinical_research_staff ov_off_crs on ov_off_crs.identifier = ov_off.clinical_research_staff_identifier
     left outer join person pi on pi.identifier = ov_off_crs.person_identifier
     left outer join study_contact central_contact on central_contact.study_protocol_identifier = sp.identifier and central_contact.role_code = 'CENTRAL_CONTACT'
     left outer join clinical_research_staff central_contact_crs on central_contact_crs.identifier = central_contact.clinical_research_staff_identifier
	 left outer join person central_contact_person on central_contact_person.identifier = central_contact_crs.person_identifier          
     left outer join primary_purpose pp on sp.primary_purpose_code = pp.name        
     left outer join document_workflow_status as processing_status on processing_status.study_protocol_identifier = sp.identifier
        and processing_status.identifier = (select max(identifier) from document_workflow_status where study_protocol_identifier = sp.identifier)
	 left outer join rv_organization_responsible_party rp_sponsor on rp_sponsor.study_protocol_identifier=sp.identifier
     where sp.status_code = 'ACTIVE' and (sp.delayed_posting_indicator is null or sp.delayed_posting_indicator!= true) and rv_trial_id_nct.local_sp_indentifier is not null
		and char_length(trim(both ' ' from rv_trial_id_nct.local_sp_indentifier))>0 
		and (sp.pri_compl_date_type_code <> 'NA' or sp.pri_compl_date_type_code is null)
		and (sp.expd_access_indidicator<>true or sp.expd_access_indidicator is null)
     
"""

def getAmendedSQL = """
  select sp.identifier,
         nci_id.extension as nciId,
         rv_trial_id_nct.local_sp_indentifier as nctId
        from study_protocol sp
	     inner join rv_trial_id_nct on rv_trial_id_nct.study_protocol_identifier = sp.identifier	    
	     inner join rv_trial_id_nci as nci_id on nci_id.study_protocol_id = sp.identifier    
	     inner join rv_dwf_current dws on dws.study_protocol_identifier = sp.identifier
	        and dws.status_code in ('AMENDMENT_SUBMITTED','ACCEPTED','ON_HOLD')        
	     where sp.status_code = 'ACTIVE'  and rv_trial_id_nct.local_sp_indentifier is not null and char_length(trim(both ' ' from rv_trial_id_nct.local_sp_indentifier))>0 
	     and amendment_date is not null and submission_number>=2
"""

def nbOfTrials = 0

def outputDir = new File("${resolvedProperties['output.dir']}")
println "Output directory: ${outputDir.getCanonicalPath()}"

paConn.eachRow(getTrialsSQL) { spRow ->

	def trialFile = new File(outputDir, "${spRow.nctId.toUpperCase()}.xml")
	def out = new FileOutputStream(trialFile)
	def writer = new OutputStreamWriter( out , "UTF-8")
	writer.write """<?xml version="1.0" encoding="UTF-8"?>\n"""

	def xml = new MarkupBuilder(writer)
	xml.setDoubleQuotes(true)

	def studyProtocolID = spRow.identifier
	def lastChangedDate = spRow.date_last_created
	def lastChangedDateUsedInLiueOfVerificationDate = false

	xml.clinical_study {
		xml.required_header {
			xml.download_date(new Date().format("MMMM dd, yyyy"))
			xml.link_text("Link to the current ClinicalTrials.gov record.")
			xml.url("http://clinicaltrials.gov/show/${spRow.nctId}")
		}
		xml.id_info {
			xml.org_study_id(spRow.leadOrgId)
			xml.secondary_id (spRow.nciId)
			paConn.eachRow(Queries.otherIdsSQL, [studyProtocolID]) { row ->
				xml.secondary_id (row.extension)
			}
			if (spRow.ctepId != null) {
				xml.secondary_id (spRow.ctepId)
			}
			if (spRow.dcpId != null) {
				xml.secondary_id (spRow.dcpId)
			}
			if (spRow.ccrId != null) {
				xml.secondary_id (spRow.ccrId)
			}
			xml.nct_id(spRow.nctId)
		}
		xml.brief_title(spRow.brief_title)
		if (spRow.acronym)
			xml.acronym(spRow.acronym)
		if (spRow.official_title)
			xml.official_title(spRow.official_title)

		xml.sponsors {
			xml.lead_sponsor {
				def sponsor = spRow.sponsorOrgName ?: spRow.leadOrgName
				if (sponsor) {
					xml.agency(sponsor.trim())
					xml.agency_class(determineAgencyClass(sponsor.trim(), poConn))
				}
			}
			paConn.eachRow(Queries.collabsSQL, [studyProtocolID]) { collabRow ->
				xml.collaborator {
					xml.agency(collabRow.name.trim())
					xml.agency_class(determineAgencyClass(collabRow.name.trim(), poConn))
				}
			}
		} // end sponsors

		xml.source(spRow.source?:"Unknown")
		if (spRow.reg_authority_country_id)
			xml.oversight_info {
				xml.authority(spRow.reg_authority)
				xml.has_dmc(spRow.dmc_indicator)
			}

		if (spRow.brief_summary)
			xml.brief_summary {
				xml.textblock(formatBlock(spRow.brief_summary))
			}
		if (spRow.detailed_description)
			xml.detailed_description {
				xml.textblock(formatBlock(spRow.detailed_description))
			}

		xml.overall_status(spRow.current_trial_status)
		if (spRow.why_stopped)
			xml.why_stopped(spRow.why_stopped)
		if (spRow.start_date)
			xml.start_date(type:spRow.start_date_type_code, spRow.start_date.format("MMMM yyyy"))
		if (spRow.completion_date)
			xml.completion_date(type:spRow.completion_date_type_code, spRow.completion_date.format("MMMM yyyy"))
		if (spRow.pri_compl_date)
			xml.primary_completion_date(type:spRow.pri_compl_date_type_code, spRow.pri_compl_date.format("MMMM yyyy"))
		xml.phase(spRow.phase_code)
		xml.study_type(spRow.study_type)
		xml.study_design(buildStudyDesign(spRow))
		paConn.eachRow(Queries.primOutcomesSQL, [studyProtocolID]) { row ->
			xml.primary_outcome {
				xml.measure(row.prim_som_name);
				if (row.prim_som_timeframe)
					xml.time_frame(row.prim_som_timeframe)
				xml.safety_issue(row.prim_som_safety_ind)
				if (row.description)
					xml.description(row.description.replace('\u001F', ' '))
			}
		}
		paConn.eachRow(Queries.secondOutcomesSQL, [studyProtocolID]) { row ->
			xml.secondary_outcome {
				xml.measure(row.prim_som_name);
				if (row.prim_som_timeframe)
					xml.time_frame(row.prim_som_timeframe)
				xml.safety_issue(row.prim_som_safety_ind)
				if (row.description)
					xml.description(row.description.replace('\u001F', ' '))
			}
		}
		paConn.eachRow(Queries.otherOutcomesSQL, [studyProtocolID]) { row ->
			xml.other_outcome {
				xml.measure(row.prim_som_name);
				if (row.prim_som_timeframe)
					xml.time_frame(row.prim_som_timeframe)
				xml.safety_issue(row.prim_som_safety_ind)
				if (row.description)
					xml.description(row.description.replace('\u001F', ' '))
			}
		}
		if (spRow.study_type=='Observational') {
			if (spRow.number_of_groups)
				xml.number_of_groups(spRow.number_of_groups)
		} else {
			if (spRow.number_of_intervention_groups)
				xml.number_of_arms(spRow.number_of_intervention_groups)
		}
		xml.enrollment(type:'Anticipated', spRow.min_target_accrual_num)
		paConn.eachRow(Queries.conditionsSQL, [studyProtocolID]) { row ->
			xml.condition(row.preferred_name)
		}

		def allArms = []
		paConn.eachRow(Queries.armsSQL, [studyProtocolID]) { row ->
			allArms.add(row.toRowResult())
		}
		def allInts = []
		paConn.eachRow(Queries.interventionsSQL, [studyProtocolID]) { row ->
			allInts.add(row.toRowResult())
		}
		// get arms out of it.
		def armsList = []
		allArms.each {
			def row = it
			if (!armsList.contains(row.arm_id)) {
				xml.arm_group {
					xml.arm_group_label(row.arm_name)
					if (row.arm_type)
						xml.arm_group_type(row.arm_type)
					if (row.arm_desc)
						xml.description(row.arm_desc)
				}
				armsList.add(row.arm_id)
			}
		}

		// interventions.
		def intsList =[]
		allInts.each {
			def intRow = it
			if (!intsList.contains(intRow.int_id)) {
				xml.intervention  {
					xml.intervention_type(intRow.int_type)
					xml.intervention_name(intRow.int_name)
					if (intRow.int_desc) {
						xml.description (intRow.int_desc)
					}

					groupNamesList = []
					allArms.each {
						def groupRow = it
						if (intRow.int_id == groupRow.int_id && !groupNamesList.contains(groupRow.arm_name)) {
							xml.arm_group_label(groupRow.arm_name)
							groupNamesList.add(groupRow.arm_name)
						}
					}

					// other names
					otherNamesList = []
					allInts.each {
						def otherNameRow = it
						if (intRow.int_id == otherNameRow.int_id && !otherNamesList.contains(otherNameRow.alt_name)
						&& otherNameRow.alt_name != null && otherNameRow.alt_name.size() > 0) {
							xml.other_name(otherNameRow.alt_name)
							otherNamesList.add(otherNameRow.alt_name)
						}
					}

				}
				intsList.add(intRow.int_id)
			}
		}
		if (spRow.study_type=='Observational') {
			if (spRow.biospec_retention)
				xml.biospec_retention(spRow.biospec_retention)
			if (spRow.biospec_descr)
				xml.biospec_descr {
					xml.textblock(formatBlock(spRow.biospec_descr))
				}
		}

		// Eligibility
		def gender
		def minAge
		def maxAge
		def inCriteria = new StringBuilder()
		def exCriteria = new StringBuilder()
		def genCriteria = new StringBuilder()

		paConn.eachRow(Queries.eligsSQL, [studyProtocolID]) { row ->
			if (row.gender) {
				gender = row.gender
			} else if (row.criterion_name == 'AGE') {
				minAge = row.min_age
				maxAge = row.max_age
			} else if (row.elig_criteria_text) {
				switch (row.elig_type) {
					case 'Inclusion Criteria':
						inCriteria << buildEligibilityCriterionDescription(row.elig_criteria_text)
						break
					case 'Exclusion Criteria':
						exCriteria << buildEligibilityCriterionDescription(row.elig_criteria_text)
						break
					default:
						genCriteria << buildEligibilityCriterionDescription(row.elig_criteria_text)
				}
			}
		}
		// To satisfy the XSD, we must include eligibility element only if all three are provided.
		if (gender && minAge && maxAge) {
			xml.eligibility {
				if (spRow.study_type=='Observational') {
					if (spRow.study_pop)
						xml.study_pop {
							xml.textblock(formatBlock(spRow.study_pop))
						}
					if (spRow.sampling_method)
						xml.sampling_method(spRow.sampling_method)
				}
				if (inCriteria || exCriteria || genCriteria) {
					def criteria = new StringBuilder("\n")
					if (genCriteria)
						criteria << "Criteria: \n\n" << genCriteria << "\n"
					if (inCriteria)
						criteria << "Inclusion Criteria: \n\n" << inCriteria << "\n"
					if (exCriteria)
						criteria << "Exclusion Criteria: \n\n" << exCriteria << "\n"
					xml.criteria { xml.textblock(formatBlock(criteria)) }
				}
				xml.gender(gender)
				xml.minimum_age(minAge)
				xml.maximum_age(maxAge)
				xml.healthy_volunteers(spRow.healthy_volunteer_indicator)
			}
		}

		if(spRow.pi_po_id) {
			xml.overall_official {
				xml.last_name("${spRow.pi_first_name} ${spRow.pi_middle_name?:''} ${spRow.pi_last_name}".trim().replaceAll('\\s+', ' '))
				xml.role("Principal Investigator")
				xml.affiliation(spRow.leadOrgName?.trim())
			}
		}

		if (spRow.cc_po_id) {
			xml.overall_contact {
				xml.last_name("${spRow.cc_first_name} ${spRow.cc_middle_name?:''} ${spRow.cc_last_name}".trim().replaceAll('\\s+', ' '))
				if (spRow.centralContactPhone)
					xml.phone(spRow.centralContactPhone)
				if (spRow.centralContactEmail)
					xml.email(spRow.centralContactEmail)
			}
		}

		// Participating sites.
		def countryList = new TreeSet()
		paConn.eachRow(Queries.partSitesSQL, [studyProtocolID]) { row ->
			xml.location {
				xml.facility {
					xml.name(row.name?.trim())
					def orgRow = orgsMap.get(row.org_poid.toLong())
					address(xml, orgRow)
					countryList.add(orgRow.country_name)
				}

				xml.status(row.status)

				paConn.eachRow(Queries.primaryContactSQL, [
					row.ss_identifier,
					studyProtocolID
				]) { primconrow ->
					xml.contact {
						xml.last_name("${primconrow.first_name} ${primconrow.middle_name?:''} ${primconrow.last_name}".trim().replaceAll('\\s+', ' '))
						if (primconrow.prim_phone)
							xml.phone(primconrow.prim_phone)
						if (primconrow.prim_email)
							xml.email(primconrow.prim_email)
					}

				}

				paConn.eachRow(Queries.investigatorsSQL, [
					row.ss_identifier,
					studyProtocolID
				]) { invsrow ->
					xml.investigator {
						xml.last_name("${invsrow.first_name} ${invsrow.middle_name?:''} ${invsrow.last_name}".trim().replaceAll('\\s+', ' '))
						xml.role(invsrow.role)
					}

				}
			}
		}  // end part sites

		// Participating site countries.
		if (!countryList.empty)
			xml.location_countries {
				countryList.each {  xml.country(it) }
			}

		xml.link {
			xml.url("http://clinicaltrials.gov/show/${spRow.nctId}")
			xml.description("Clinical trial summary from the National Library of Medicine (NLM)'s database")
		}

		
		if (spRow.verification_date) {
			xml.verification_date(spRow.verification_date.format("MMMM yyyy"))
			lastChangedDate = spRow.verification_date
		} else {
			xml.verification_date(lastChangedDate.format("MMMM yyyy"))
			lastChangedDateUsedInLiueOfVerificationDate = true
		}

		xml.lastchanged_date(lastChangedDate.format("MMMM d, yyyy"))

		def firstSubmitDate = paConn.firstRow(Queries.firstSubmissionSQL, [spRow.nciId])?.milestone_date
		xml.firstreceived_date(firstSubmitDate?firstSubmitDate.format("MMMM d, yyyy"):'N/A')


		// Responsible Party
		def partyRow = paConn.firstRow(Queries.respPartySQL, [studyProtocolID])
		if (partyRow || spRow.rp_sponsor_po_id)
			xml.responsible_party {
				if (spRow.rp_sponsor_po_id) {
					xml.responsible_party_type("Sponsor")
				} else {
					xml.responsible_party_type(partyRow.type)
					xml.investigator_affiliation(partyRow.org_name)
					xml.investigator_full_name("${partyRow.first_name} ${partyRow.middle_name?:''} ${partyRow.last_name}".trim().replaceAll('\\s+', ' '))
					if (partyRow.title)
						xml.investigator_title(partyRow.title)
				}
			}

		if (spRow.keywords) {
			spRow.keywords.split(',|;|\\n|\\r\\n').each {
				if (it.trim())
					xml.keyword(it.trim())
			}
		}


		xml.is_fda_regulated(spRow.fda_indicator)
		xml.is_section_801(spRow.section801_indicator)
		xml.has_expanded_access(spRow.has_expanded_access)


	}
	writer.flush();
	writer.close();

	// PO-8570 kicks in here. If there is a history of Cancer.gov exports of this trial in cancer_gov_export_log table, then we need
	// to compare this export with a previous one to properly determine lastchanged_date. See JIRA for details.
	def previousExport = paConn.firstRow(Queries.prevExportXml, [spRow.nctId.toUpperCase()])
	def previousExportXml = previousExport?.xml
	if (previousExportXml) {
		if (!twoExportsAreTheSame(previousExportXml, trialFile.getText("UTF-8"))) {
			// Things have changed since the last export!
			lastChangedDate = new Date()
		} else {
			lastChangedDate = previousExport.lastchanged_date?:lastChangedDate
		}
		trialFile.setText(trialFile.getText("UTF-8").replaceFirst("<lastchanged_date>.*?</lastchanged_date>", "<lastchanged_date>"+
				lastChangedDate.format("MMMM d, yyyy")+"</lastchanged_date>"), "UTF-8")
		if (lastChangedDateUsedInLiueOfVerificationDate) {
			trialFile.setText(trialFile.getText("UTF-8").replaceFirst("<verification_date>.*?</verification_date>", "<verification_date>"+
				lastChangedDate.format("MMMM yyyy")+"</verification_date>"), "UTF-8")
		}
	}

	// We need to validate the file we just produced against the NLM's XSD to make sure we didn't produce something odd due to a bug or something.
	def factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
	def schema = factory.newSchema(new StreamSource(new File("${outputDir}/../src/main/resources/public.xsd")))
	def validator = schema.newValidator()
	validator.validate(new StreamSource(trialFile))

	// All looks normal; store in the log table.
	paConn.executeInsert("INSERT INTO cancer_gov_export_log (study_protocol_id,nci_id,nct_id,xml,lastchanged_date) VALUES (?,?,?,?,?)", [
		studyProtocolID,
		spRow.nciId,
		spRow.nctId.toUpperCase(),
		trialFile.getText("UTF-8"),
		new java.sql.Timestamp(lastChangedDate.getTime())
	])

	nbOfTrials++



}

// Now handle PO-8440: amendments that have not been abstracted yet must include the previous XML file from the log.
paConn.eachRow(getAmendedSQL) { spRow ->
	// see if we have a log entry for this trial.
	def xml = paConn.firstRow("select xml from cancer_gov_export_log where study_protocol_id=? order by datetime desc LIMIT 1", [spRow.identifier])?.xml
	if (xml) {
		def trialFile = new File(outputDir, "${spRow.nctId.toUpperCase()}.xml")
		trialFile.setText(xml, "UTF-8")
	}
}

boolean twoExportsAreTheSame(String xml1, String xml2) {
	return xml1.replaceFirst("<download_date>.*?</download_date>", "").replaceFirst("<lastchanged_date>.*?</lastchanged_date>", "") ==
	xml2.replaceFirst("<download_date>.*?</download_date>", "").replaceFirst("<lastchanged_date>.*?</lastchanged_date>", "")
}


String determineAgencyClass(org, poConn) {
	def agencyClass
	poConn.eachRow(Queries.familyNamesByOrgSQL, [org]) { row ->
		if (!agencyClass && row.name)
			agencyClass = "Other"
		if (row.name==Constants.CCR)
			agencyClass = "NIH"
	}
	return agencyClass?:"Industry"
}


void address(MarkupBuilder xml, Object row) {
	xml.address {
		xml.city(row.cityormunicipality?.trim())
		if (row.stateorprovince)
			xml.state(row.stateorprovince?.trim())
		if (row.postalcode)
			xml.zip(row.postalcode?.trim())
		xml.country(row.country_name?.trim())
	}
}


String buildEligibilityCriterionDescription(criterion) {
	return "  - " <<  applyPrsFormattingFixes(criterion) << '\n'
}

/**
 * Apply some formatting fixes to achieve a better display in PRS.
 * @param text
 * @return
 */
String applyPrsFormattingFixes(String text) {
	text = text.replaceAll("(?m)^ \\*", "  *");
	text = text.replaceAll("(?m)^\\*", "  *");
	text = text.replaceAll("(?m)^ \\-\\s", "  * ");
	text = text.replaceAll("(?m)^\\-\\s", "  * ");
	return text;
}


/**
 * Builds study design string that matches the format ClinicalTrials.gov uses.
 * @param spRow
 * @return
 */
String buildStudyDesign(spRow)	{
	def design = new StringBuilder()
	if (spRow.study_type=='Observational') {
		if (spRow.study_model_code)
			design << (design.size()>0?', ':'') << "Observational Model:  ${spRow.study_model_code}"
		if (spRow.time_perspective_code)
			design << (design.size()>0?', ':'') << "Time Perspective:  ${spRow.time_perspective_code}"
	} else {
		if (spRow.allocation_code)
			design << (design.size()>0?', ':'') << "Allocation:  ${spRow.allocation_code}"
		if (spRow.classification_code)
			design << (design.size()>0?', ':'') << "Endpoint Classification:  ${spRow.classification_code}"
		if (spRow.design_configuration_code)
			design << (design.size()>0?', ':'') << "Intervention Model:  ${spRow.design_configuration_code}"
		if (spRow.blinding_schema_code) {
			design << (design.size()>0?', ':'') << "Masking:  ${spRow.blinding_schema_code}"
			if (spRow.blinding_role_code_caregiver || spRow.blinding_role_code_investigator || spRow.blinding_role_code_subject || spRow.blinding_role_code_outcome) {
				design << ' ('
				def maskingRoles = new StringBuilder()
				if (spRow.blinding_role_code_subject)
					maskingRoles << (maskingRoles.size()>0?', ':'') << "Subject"
				if (spRow.blinding_role_code_caregiver)
					maskingRoles << (maskingRoles.size()>0?', ':'') << "Caregiver"
				if (spRow.blinding_role_code_investigator)
					maskingRoles << (maskingRoles.size()>0?', ':'') << "Investigator"
				if (spRow.blinding_role_code_outcome)
					maskingRoles << (maskingRoles.size()>0?', ':'') << "Outcomes Assessor"
				design << maskingRoles << ')'
			}
		}

	}

	if (spRow.primary_purpose_code)
		design << (design.size()>0?', ':'') << "Primary Purpose:  ${spRow.primary_purpose_code}"

	return design.size()>0?design:'N/A'
}


String formatBlock(text) {
	if (text) {
		// PO-8550, item 1.
		text = '\n' + text.toString().trim().replaceAll("(\\r\\n|\\n)(\\r|\\n)*", "\n\n")
		// PO-8550, item 2.
		text = text.toString().trim().replaceAll("(?m)^( |\\t)+\\-\\s+", "    ").replaceAll("(?m)^( |\\t)+\\*\\s+", "    ").
			replaceAll("(?m)^( |\\t)+\\*\\*\\s+", "    - ").replaceAll("(?m)^( |\\t)+\\*(\\w)", '    $2')
	}
	return text
}

println ""
println "************PDQ EXPORT SUMMARY******************"
println ""
println "Exported $nbOfTrials trials"
println ""
println "************************************************"
println ""
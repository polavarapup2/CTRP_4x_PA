class Queries {
    
    public static def otherIdsSQL = """
        select extension from study_otheridentifiers where study_protocol_id = ? and root <> '2.16.840.1.113883.3.26.4.3'
			and extension is not null
    """
    
    public static def partSitesSQL = """
        select
            org.name,
            hcf.assigned_identifier as hcf_poid,
            org.assigned_identifier as org_poid,
            ss.identifier as ss_identifier,
            CASE 
                WHEN ssas.status_code = 'APPROVED' then 'Not yet recruiting'
                WHEN ssas.status_code = 'IN_REVIEW' then 'Not yet recruiting'
                WHEN ssas.status_code = 'ACTIVE' then 'Recruiting'
                WHEN ssas.status_code = 'ENROLLING_BY_INVITATION' then 'Enrolling by invitation'
                WHEN ssas.status_code = 'CLOSED_TO_ACCRUAL' then 'Active, not recruiting'
                WHEN ssas.status_code = 'CLOSED_TO_ACCRUAL_AND_INTERVENTION' then 'Active, not recruiting'
                WHEN ssas.status_code = 'TEMPORARILY_CLOSED_TO_ACCRUAL' then 'Suspended'
                WHEN ssas.status_code = 'TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION' then 'Suspended'
                WHEN ssas.status_code = 'WITHDRAWN' then 'Withdrawn'
                WHEN ssas.status_code = 'ADMINISTRATIVELY_COMPLETE' then 'Terminated'
                WHEN ssas.status_code = 'COMPLETED' then 'Completed'
				WHEN ssas.status_code = 'COMPLETE' then 'Completed'
            END as status
        FROM Study_Site ss
        inner join healthcare_facility hcf on hcf.identifier = ss.healthcare_facility_identifier
        inner join organization org on org.identifier = hcf.organization_identifier
        left outer join study_site_accrual_status ssas on ssas.study_site_identifier = ss.identifier 
            and ssas.identifier = (SELECT sos2.identifier FROM study_site_accrual_status sos2 WHERE sos2.study_site_identifier = ss.identifier
                                AND sos2.deleted = false ORDER BY sos2.status_date DESC, sos2.identifier DESC LIMIT 1)
        where ss.functional_code = 'TREATING_SITE' and ss.status_code in ('ACTIVE','PENDING')
        and ss.study_protocol_identifier = ?
    """

    public static def collabsSQL = """
        select org.name,
            ro.assigned_identifier as ro_poid,
            org.assigned_identifier as org_poid,
            ss.functional_code
        from Study_Site ss
            inner join research_organization ro on ro.identifier = ss.research_organization_identifier
            inner join organization org on org.identifier = ro.organization_identifier
        where ss.functional_code in ('FUNDING_SOURCE', 'LABORATORY', 'AGENT_SOURCE')
            and ss.study_protocol_identifier = ?
    """
  
    
    public static def conditionsSQL = """
        select 
            d.disease_code,
            d.nt_term_identifier as nci_thesaurus_id,
            d.menu_display_name,
            d.preferred_name
        from study_disease sd
             inner join pdq_disease d on d.identifier = sd.disease_identifier
        where sd.study_protocol_identifier = ?
    """
    
    public static def armsSQL = """
        select 
            a.identifier as arm_id,
            a.name as arm_name,
            CASE 
                WHEN a.type_code = 'EXPERIMENTAL' then 'Experimental' 
                WHEN a.type_code = 'ACTIVE_COMPARATOR' then 'Active Comparator' 
                WHEN a.type_code = 'PLACEBO_COMPARATOR' then 'Placebo Comparator' 
                WHEN a.type_code = 'SHAM_COMPARATOR' then 'Sham Comparator' 
                WHEN a.type_code = 'NO_INTERVENTION' then 'No Intervention' 
                WHEN a.type_code = 'OTHER' then 'Other' 
            END as arm_type,
            a.description_text as arm_desc,
            int.identifier as int_id,
            int.pdq_term_identifier as cdr_id,
            pa.subcategory_code as int_type,
            int.name as int_name,
            pa.text_description as int_desc,
            altName.name as alt_name
        from ARM a
            left outer join arm_intervention a_i on a_i.arm_identifier = a.identifier   
            left outer join planned_activity pa on pa.identifier = a_i.planned_activity_identifier   
            left join intervention int on int.identifier = pa.intervention_identifier 
            left outer join intervention_alternate_name altName on altName.intervention_identifier = int.identifier 
        where a.study_protocol_identifier = ?            
    """
	
	public static def interventionsSQL = """
        select 
            a.identifier as arm_id,
            a.name as arm_name,
            CASE 
                WHEN a.type_code = 'EXPERIMENTAL' then 'Experimental' 
                WHEN a.type_code = 'ACTIVE_COMPARATOR' then 'Active Comparator' 
                WHEN a.type_code = 'PLACEBO_COMPARATOR' then 'Placebo Comparator' 
                WHEN a.type_code = 'SHAM_COMPARATOR' then 'Sham Comparator' 
                WHEN a.type_code = 'NO_INTERVENTION' then 'No Intervention' 
                WHEN a.type_code = 'OTHER' then 'Other' 
            END as arm_type,
            a.description_text as arm_desc,
            int.identifier as int_id,
            int.pdq_term_identifier as cdr_id,
            pa.subcategory_code as int_type,
            int.name as int_name,
            pa.text_description as int_desc,
            altName.name as alt_name
        from intervention int
			inner join planned_activity pa on pa.intervention_identifier = int.identifier
			left outer join arm_intervention a_i on a_i.planned_activity_identifier = pa.identifier
			left outer join arm a on a.identifier = a_i.arm_identifier
			left outer join intervention_alternate_name altName on altName.intervention_identifier = int.identifier
        where pa.study_protocol_identifier = ?            
    """

    
    public static def primOutcomesSQL = """
        select 
            prim_som.name as prim_som_name,
            CASE WHEN prim_som.safety_indicator then 'Yes'
                ELSE 'No'
            END as prim_som_safety_ind,
            prim_som.timeframe as prim_som_timeframe,
			prim_som.description
        from study_outcome_measure prim_som 
            where prim_som.study_protocol_identifier = ?
            and type_code='PRIMARY'
    """
	
	public static def secondOutcomesSQL = """
        select 
            prim_som.name as prim_som_name,
            CASE WHEN prim_som.safety_indicator then 'Yes'
                ELSE 'No'
            END as prim_som_safety_ind,
            prim_som.timeframe as prim_som_timeframe,
			prim_som.description
        from study_outcome_measure prim_som 
            where prim_som.study_protocol_identifier = ?
            and type_code='SECONDARY'
    """

	public static def otherOutcomesSQL = """
        select 
            prim_som.name as prim_som_name,
            CASE WHEN prim_som.safety_indicator then 'Yes'
                ELSE 'No'
            END as prim_som_safety_ind,
            prim_som.timeframe as prim_som_timeframe,
			prim_som.description
        from study_outcome_measure prim_som 
            where prim_som.study_protocol_identifier = ?
            and type_code='OTHER_PRE_SPECIFIED'
    """

    public static def eligsSQL = """
        select 
            CASE 
				 WHEN elig.inclusion_indicator=true THEN 'Inclusion Criteria'
				 WHEN elig.inclusion_indicator=false THEN 'Exclusion Criteria'
                 ELSE 'Criteria'
            END as elig_type,
            CASE WHEN elig.eligible_gender_code = 'MALE' THEN 'Male'
                WHEN elig.eligible_gender_code = 'FEMALE' THEN 'Female'
                WHEN elig.eligible_gender_code = 'BOTH' THEN 'Both'
                ELSE null
            END as gender,
            criterion_name,
            CASE WHEN max_value = 999 THEN 'N/A'
                ELSE regexp_replace(cast(max_value as varchar),'\\.0+\\Z','') || ' ' || max_unit
            END as max_age,            
            CASE WHEN min_value = 0 THEN 'N/A'
            ELSE regexp_replace(cast(min_value as varchar),'\\.0+\\Z','') || ' ' || min_unit
            END as min_age,
			CASE 
				WHEN (elig.criterion_name is null OR elig.criterion_name NOT IN ('GENDER', 'AGE', 'MINIMUM-AGE')) 
					AND category_code in ('ELIGIBILITY_CRITERION','OTHER') THEN text_description                
            END as elig_criteria_text
         from planned_eligibility_criterion elig
            join planned_activity pa on pa.identifier = elig.identifier 
        where pa.study_protocol_identifier = ? order by elig.DISPLAY_ORDER,elig.identifier
    """
    
   public static def primaryContactSQL = """
        select 
        prim_crs.assigned_identifier as prim_crs_id,
        prim_ssc.telephone as prim_phone,
        prim_ssc.email as prim_email,
   		p.first_name,
		p.middle_name,
		p.last_name,
		p.assigned_identifier as person_po_id
        from study_site_contact prim_ssc, clinical_research_staff prim_crs, person p 
        where 
        prim_ssc.role_code = 'PRIMARY_CONTACT' and
        prim_crs.identifier = prim_ssc.clinical_research_staff_identifier and
   		p.identifier=prim_crs.person_identifier and
   		p.assigned_identifier is not null and
        prim_ssc.study_site_identifier = ? and
        prim_ssc.study_protocol_identifier = ? LIMIT 1
   """
   
   public static def investigatorsSQL = """
        select 
        inv_crs.assigned_identifier as inv_crs_id,
        inv_ssc.telephone as inv_phone,
        inv_ssc.email as inv_email,
		p.first_name,
		p.middle_name,
		p.last_name,
		p.assigned_identifier as person_po_id,
   		CASE WHEN inv_ssc.role_code = 'PRINCIPAL_INVESTIGATOR' THEN 'Principal Investigator'               
                ELSE 'Sub-Investigator'
        END as role
        from study_site_contact inv_ssc, clinical_research_staff inv_crs, person p 
        where
        inv_ssc.role_code in ('PRINCIPAL_INVESTIGATOR', 'SUB_INVESTIGATOR') and
        inv_crs.identifier = inv_ssc.clinical_research_staff_identifier and
   		p.identifier=inv_crs.person_identifier and
   	    p.assigned_identifier is not null and
        inv_ssc.study_site_identifier = ? and
        inv_ssc.study_protocol_identifier = ?
   """
    
   public static def firstSubmissionSQL = """
        SELECT sm.milestone_date FROM STUDY_MILESTONE sm
			inner join rv_trial_id_nci as nci_id on nci_id.study_protocol_id = sm.study_protocol_identifier     
		     where sm.milestone_code='SUBMISSION_RECEIVED' 
		     and extension=?
		     order by sm.milestone_date asc LIMIT 1
    """
   
	public static def respPartySQL = """
		SELECT sc.study_protocol_identifier,per.first_name,per.last_name, per.middle_name, org.name as org_name, sc.title,
			CASE
				WHEN role_code='RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR' THEN 'Principal Investigator'
				WHEN role_code='RESPONSIBLE_PARTY_SPONSOR_INVESTIGATOR' THEN 'Sponsor-Investigator'
			END as type
			FROM study_contact sc
			INNER JOIN clinical_research_staff crs ON (crs.identifier = sc.clinical_research_staff_identifier)
			INNER JOIN person per ON (crs.person_identifier = per.identifier)
			INNER JOIN organization org ON crs.organization_identifier=org.identifier
			WHERE role_code in ('RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR','RESPONSIBLE_PARTY_SPONSOR_INVESTIGATOR')
			AND sc.study_protocol_identifier=?
    """
	
	public static def familyNamesByOrgSQL = """
	     select f.name from family f inner join familyorganizationrelationship rel on rel.family_id=f.id 
			inner join organization o on rel.organization_id=o.id where f.enddate is null and rel.enddate is null
			and o.name=?
    """
    
	public static def prevExportXml = """
        SELECT xml, lastchanged_date from cancer_gov_export_log where nct_id=? order by datetime desc LIMIT 1
    """
}
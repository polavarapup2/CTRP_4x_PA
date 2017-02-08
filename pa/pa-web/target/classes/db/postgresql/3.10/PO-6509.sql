-- Default Disease codes

insert into accrual_disease (identifier, code_system, disease_code, preferred_name, display_name) 
(select (select (max(identifier)+1) as identifier from accrual_disease), 
'ICD9' as code_system,'V100' as disease_code, 'Disease not specified' as preferred_name, 
'Disease not specified' as display_name 
where not exists (select 1 from accrual_disease where code_system = 'ICD9' and disease_code = 'V100'));

insert into accrual_disease (identifier, code_system, disease_code, preferred_name, display_name) 
(select (select (max(identifier)+1) as identifier from accrual_disease), 'SDC' as code_system,'80000001' as disease_code, 
'Disease not specified' as preferred_name, 'Disease not specified' as display_name 
where not exists (select 1 from accrual_disease where code_system = 'SDC' and disease_code = '80000001'));

insert into accrual_disease (identifier, code_system, disease_code, preferred_name, display_name) 
(select (select (max(identifier)+1) as identifier from accrual_disease), 
'ICD-O-3' as code_system,'C998' as disease_code, 'Disease not specified' as preferred_name, 
'Disease not specified' as display_name 
where not exists (select 1 from accrual_disease where code_system = 'ICD-O-3' and disease_code = 'C998'));

insert into accrual_disease (identifier, code_system, disease_code, preferred_name, display_name) 
(select (select (max(identifier)+1) as identifier from accrual_disease), 'ICD-O-3' as code_system,'7001' as disease_code, 
'Disease not specified' as preferred_name, 'Disease not specified' as display_name 
where not exists (select 1 from accrual_disease where code_system = 'ICD-O-3' and disease_code = '7001'));

-- Default Healthy volunteer

insert into accrual_disease (identifier, code_system, disease_code, preferred_name, display_name) 
(select(select (max(identifier)+1) as identifier from accrual_disease),
'ICD9' as code_system, 'V99' as disease_code,'Healthy  volunteer'  as preferred_name, 'Healthy volunteer' as display_name
where not exists (select 1 from accrual_disease where code_system = 'ICD9' and disease_code = 'V99'));

insert into accrual_disease (identifier, code_system, disease_code, preferred_name, display_name) 
(select(select (max(identifier)+1) as identifier from accrual_disease),
'SDC' as code_system, '80000000' as disease_code, 'Healthy volunteer'  as preferred_name, 'Healthy volunteer' as display_name
where not exists (select 1 from accrual_disease where code_system = 'SDC' and disease_code = '80000000'));

insert into accrual_disease (identifier, code_system, disease_code, preferred_name, display_name) 
(select(select (max(identifier)+1) as identifier from accrual_disease),
'ICD-O-3' as code_system, 'C999' as disease_code,'Healthy  volunteer'  as preferred_name, 'Healthy volunteer' as display_name
where not exists (select 1 from accrual_disease where code_system = 'ICD-O-3' and disease_code = 'C999'));

insert into accrual_disease (identifier, code_system, disease_code, preferred_name, display_name) 
(select(select (max(identifier)+1) as identifier from accrual_disease),
'ICD-O-3' as code_system, '7002' as disease_code, 'Healthy volunteer'  as preferred_name, 'Healthy volunteer' as display_name
where not exists (select 1 from accrual_disease where code_system = 'ICD-O-3' and disease_code = '7002'));

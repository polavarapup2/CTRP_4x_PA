CREATE SEQUENCE nci_identifiers_seq;

select setval ('nci_identifiers_seq', (select cast(substring(max(extension),10) as int)+1 from study_otheridentifiers sp where sp.extension like 'NCI-'||to_char(now(), 'YYYY')||'%' and sp.root = '2.16.840.1.113883.3.26.4.3'));
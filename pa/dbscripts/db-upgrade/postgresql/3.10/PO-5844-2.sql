update study_contact set title='Principal Investigator' 
    where role_code in ('RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR',
        'RESPONSIBLE_PARTY_SPONSOR_INVESTIGATOR');
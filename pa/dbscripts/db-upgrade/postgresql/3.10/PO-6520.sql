delete from study_regulatory_authority sra1 where exists (

    select identifier from study_regulatory_authority sra2 where sra2.identifier>sra1.identifier and sra1.study_protocol_identifier=sra2.study_protocol_identifier and sra1.regulatory_authority_identifier=sra2.regulatory_authority_identifier

);
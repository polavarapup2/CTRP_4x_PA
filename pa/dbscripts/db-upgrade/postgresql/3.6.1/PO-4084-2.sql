update pa_properties set value = 'Dear ${firstName} ${lastName},

Thank you for your inquiry.

Per your request, please find below is the NCI CTRP Username associated with the email you provided.

Username: ${userNames}

If you have questions, please contact us at ncictro@mail.nih.gov.

Thank you,

NCI Clinical Trials Reporting Office
http://www.cancer.gov/ncictrp
ncictro@mail.nih.gov
'
where name = 'user.usernameSearch.body';

alter table registry_user alter column identifier type bigint;
alter table registry_user alter column csm_user_id type bigint;
alter table registry_user alter column user_last_created_id type bigint;
alter table registry_user alter column user_last_updated_id type bigint;
alter table registry_user alter column identifier drop default;
drop sequence if exists registry_user_identifier_seq;
alter table registry_user alter column csm_user_id set not null;
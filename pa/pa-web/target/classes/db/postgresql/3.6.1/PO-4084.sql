INSERT INTO PA_PROPERTIES (identifier, name, value) values ((select max(identifier)+1 from PA_PROPERTIES), 'user.usernameSearch.body', 'Thank you for your inquiry.

Per your request, please find below is the NCI CTRP Username associated with the email you provided.

Username: ${userNames}

If you have questions, please contact us at ncictro@mail.nih.gov.

Thank you,

NCI Clinical Trials Reporting Office
http://www.cancer.gov/ncictrp
ncictro@mail.nih.gov
');

INSERT INTO PA_PROPERTIES (identifier, name, value) values ((select max(identifier)+1 from PA_PROPERTIES), 'user.usernameSearch.subject', 'NCI CTRP Username Retrieval.');

UPDATE DOCUMENT_WORKFLOW_STATUS
SET STATUS_CODE = 'ABSTRACTION_VERIFIED_NORESPONSE'
where STATUS_CODE = 'ABSTRACTION_VERIFIED' ;
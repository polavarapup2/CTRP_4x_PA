
-- Purpose: To remove email addresses improperly added through previous db script.
-- Allow only addresses w/ a @ symbol to remain.

update registry_user set email_address = null where email_address not like '%@%';

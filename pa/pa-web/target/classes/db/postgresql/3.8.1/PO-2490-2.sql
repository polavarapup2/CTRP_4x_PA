CREATE OR REPLACE VIEW rv_checkout_admin AS 
 SELECT study_checkout.identifier, study_checkout.study_protocol_identifier, study_checkout.user_identifier
   FROM study_checkout
  WHERE study_checkout.checkout_type  = 'ADMINISTRATIVE'
    AND study_checkout.checkin_date IS NULL;

    
CREATE OR REPLACE VIEW rv_checkout_scientific AS 
 SELECT study_checkout.identifier, study_checkout.study_protocol_identifier, study_checkout.user_identifier
   FROM study_checkout
  WHERE study_checkout.checkout_type = 'SCIENTIFIC'
    AND study_checkout.checkin_date IS NULL;


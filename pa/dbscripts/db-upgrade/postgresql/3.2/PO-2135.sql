INSERT INTO CSM_GROUP (GROUP_NAME, GROUP_DESC, APPLICATION_ID, UPDATE_DATE) 
    SELECT 'SuAbstractor', 'Super User Abstractor User Role', (select application_id from csm_application where application_name = 'pa'), current_date
    WHERE NOT EXISTS (SELECT GROUP_NAME, APPLICATION_ID FROM CSM_GROUP WHERE GROUP_NAME = 'SuAbstractor' 
        AND APPLICATION_ID = (select application_id from csm_application where application_name = 'pa'));

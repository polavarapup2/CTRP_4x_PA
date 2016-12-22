CREATE OR REPLACE FUNCTION  update_Planned_Activity() RETURNS void AS $$
DECLARE
rr RECORD;
maxRowId VARCHAR(200);
minVal NUMERIC;
BEGIN
FOR rr IN SELECT * FROM planned_activity where criterion_name = 'MAXIMUM-AGE' LOOP


UPDATE PLANNED_ACTIVITY SET MAX_VALUE = MIN_VALUE WHERE IDENTIFIER = rr.identifier;

SELECT INTO minVal MIN_VALUE FROM PLANNED_ACTIVITY WHERE IDENTIFIER = rr.identifier + 1 AND criterion_name = 'MINIMUM-AGE';

UPDATE PLANNED_ACTIVITY SET MIN_VALUE = minVal where IDENTIFIER = rr.identifier;

DELETE FROM PLANNED_ACTIVITY WHERE IDENTIFIER = rr.identifier + 1 AND criterion_name = 'MINIMUM-AGE';

END LOOP;
END;
$$ LANGUAGE 'plpgsql';

select update_Planned_Activity();

drop function  update_Planned_Activity();


UPDATE planned_activity SET criterion_name = 'AGE' where criterion_name = 'MAXIMUM-AGE' ;
CREATE OR REPLACE FUNCTION  update_planned_activity() RETURNS void AS $$
DECLARE
rr RECORD;
txtDescription VARCHAR(2000);
maxVal VARCHAR(200);
minVal VARCHAR(200);
criteriaName VARCHAR(200);
operat VARCHAR(200);
unit VARCHAR(200);
BEGIN
FOR rr IN SELECT * FROM planned_activity where text_description IS NULL and planned_activity_type= 'PlannedEligibilityCriterion' and category_code = 'OTHER' LOOP
if rr.max_value is null then 
  maxVal = '';
else 
 maxVal = ' - ' || rr.max_value;
end if;
if rr.min_value is null then 
  minVal = '';
else 
 minVal = rr.min_value;
end if;
if rr.operator is null then 
  operat = '';
else 
 operat = rr.operator;
end if;
if rr.min_unit is null then 
  unit = '';
else 
 unit = rr.min_unit;
end if;
if rr.criterion_name is null then 
  criteriaName = '';
else 
 criteriaName = rr.criterion_name;
end if;
select into txtDescription criteriaName ||' '|| operat ||' '|| minVal || maxVal ||' '|| unit;
update planned_activity set 
 text_description = txtDescription, criterion_name = null, max_value= null, min_value = null, operator=null, min_unit=null, max_unit=null
where identifier = rr.identifier;
END LOOP;
END;
$$ LANGUAGE 'plpgsql';

select update_planned_activity();

drop function  update_planned_activity();
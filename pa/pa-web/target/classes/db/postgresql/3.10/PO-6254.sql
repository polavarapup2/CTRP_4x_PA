INSERT INTO pa_properties VALUES ((select max(identifier) + 1 from pa_properties), 'I2EGrantsUrl'
 , 'jdbc:oracle:thin:ctrp_ru/XO4DJLXR@(DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=nci-oraclecm-test.nci.nih.gov)(PORT=1610))(CONNECT_DATA=(SERVICE_NAME=NDMT.NCI.NIH.GOV)))');

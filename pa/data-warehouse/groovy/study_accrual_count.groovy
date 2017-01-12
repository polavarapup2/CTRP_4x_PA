import groovy.sql.Sql
def sql = """
    SELECT 
       accrual_count,
       study_site_identifier AS internal_system_id
    FROM study_site_subject_accrual_count
"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def sac = destinationConnection.dataSet("stg_dw_study_accrual_count");

sourceConnection.eachRow(sql) { row ->
    sac.add(
        accrual_count : row.accrual_count,
        count_type : 'SITE_TOTAL',
        study_site_id : row.internal_system_id        
    )};
   
destinationConnection.execute("""UPDATE stg_dw_study_accrual_count sac
                                 SET nci_id = ps.nci_id, 
                                     org_name = ps.org_name, 
                                     org_org_family = ps.org_org_family
                                 FROM stg_dw_study_participating_site ps where sac.study_site_id = ps.internal_system_id""");

destinationConnection.execute("""INSERT INTO stg_dw_study_accrual_count(accrual_count, count_type, nci_id)
                                 SELECT sum(accrual_count), 'STUDY_TOTAL', nci_id
                                 FROM stg_dw_study_accrual_count
                                 GROUP BY nci_id""");    
							 
							 sourceConnection.close()
							 destinationConnection.close()
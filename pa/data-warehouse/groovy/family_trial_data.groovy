import groovy.sql.Sql
import org.apache.commons.lang.StringUtils


def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])

destinationConnection.execute("""INSERT INTO stg_dw_family_trial_data
                                 SELECT DISTINCT org_org_family, nci_id, NULL
                                 FROM stg_dw_study_participating_site
                                 WHERE org_org_family IS NOT NULL
                                   """);

					   
								   
								   destinationConnection.close()

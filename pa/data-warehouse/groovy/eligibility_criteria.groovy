import groovy.sql.Sql
def sql = """SELECT
				elig.inclusion_indicator,
				elig.identifier,
				elig.criterion_name,
				elig.operator,
				elig.eligible_gender_code,
				elig.unit,
				elig.display_order,
				elig.structured_indicator,
				elig.text_value,
				elig.cde_public_identifier,
				elig.cde_version_number,
				pa.text_description,
				nci_id.extension as extension
                FROM PLANNED_eligibility_criterion elig
                inner join planned_activity as pa on pa.identifier = elig.identifier
                inner join study_otheridentifiers as nci_id on nci_id.study_protocol_id = pa.study_protocol_identifier
                    and nci_id.root = '2.16.840.1.113883.3.26.4.3' 
                where elig.criterion_name is null or elig.criterion_name NOT IN ('GENDER', 'AGE', 'MINIMUM-AGE')
                """                   

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def oids = destinationConnection.dataSet("STG_DW_STUDY_ELIGIBILITY_CRITERIA");

sourceConnection.eachRow(sql) { row ->
    oids.add(
            nci_id: row.extension,
            cde_public_identifier: row.cde_public_identifier,
            cde_version: row.cde_version_number,
            display_order: row.display_order,
            eligible_gender_code: row.eligible_gender_code,
            inclusion_indicator: row.inclusion_indicator,
            structured_indicator: row.structured_indicator,
            internal_system_id: row.identifier,
            criterion_name: row.criterion_name,
            operator: row.operator,
            unit: row.unit,
            value: row.text_value,
            description: row.text_description
            )
            }
            

sourceConnection.close()
destinationConnection.close()
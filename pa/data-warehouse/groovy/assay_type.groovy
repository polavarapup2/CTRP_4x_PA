import groovy.sql.Sql

def sql =
"""
SELECT type_code, description_text, cadsr_id FROM assay_type
"""


def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
        properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
        properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def out = destinationConnection.dataSet("STG_DW_ASSAY_TYPE")

sourceConnection.eachRow(sql) { row ->
    out.withTransaction {
        try {
            out.add(
                type_code : row.type_code,
                description_text : row.description_text,
                cadsr_id : row.cadsr_id
            )
        } catch (Exception e) {
            println "Error adding row : " + row
            println e.getMessage()
        }
    }
}
sourceConnection.close()
destinationConnection.close()
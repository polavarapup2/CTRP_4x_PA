import groovy.sql.Sql
def sql = """SELECT soi.extension, ibx.*
             FROM study_inbox ibx
             JOIN study_otheridentifiers soi ON (soi.study_protocol_id = ibx.study_protocol_identifier
                                                 AND root = '2.16.840.1.113883.3.26.4.3')
"""

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def inbox = destinationConnection.dataSet("STG_DW_STUDY_INBOX");

sourceConnection.eachRow(sql) { row ->
    inbox.add(
        id: row.identifier,
        nci_id: row.extension,
        comments: row.comments,
        open_date: row.open_date,
        close_date: row.close_date,
        type_code: row.type_code,   
        admin: row.admin,
        scientific: row.scientific,
        admin_close_date: row.admin_close_date,
        scientific_close_date: row.scientific_close_date
    )
}

sourceConnection.close()
destinationConnection.close()
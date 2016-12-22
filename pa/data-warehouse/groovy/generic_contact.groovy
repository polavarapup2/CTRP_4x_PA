import groovy.sql.Sql

def sql = "select id, title from organizationalcontact where title is not null order by id asc"
def sourceConnection = Sql.newInstance(properties['datawarehouse.po.jdbc.url'], properties['datawarehouse.po.db.username'], 
    properties['datawarehouse.po.db.password'], properties['datawarehouse.po.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'], 
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def generic_contacts = destinationConnection.dataSet("STG_DW_GENERIC_CONTACT");
sourceConnection.eachRow(sql) { row ->
    generic_contacts.add(identifier: row.id, title: row.title)
}

sourceConnection.close()
destinationConnection.close()
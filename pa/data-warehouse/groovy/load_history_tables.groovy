import groovy.sql.Sql
import org.apache.commons.lang.StringUtils
import java.util.*
import java.text.*


def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def dw_run = destinationConnection.dataSet("DW_RUN");


def now = new Date()
def timestamp = new java.sql.Timestamp((now).getTime())


  dw_run.add(
        RUN_ID: timestamp
 );

println "Successfully inserted RUN_ID in DW_RUN " +  timestamp  

println "Listing tables " 

def sql = """SELECT table_name FROM information_schema.tables WHERE 
           table_schema='public' and table_type='BASE TABLE' order by table_name
                """
def tableExistsSql = """SELECT EXISTS(
    SELECT table_name
    FROM information_schema.tables 
    WHERE table_schema='public' and table_type='BASE TABLE'
    and lower(table_name)=?
)
                """ 
def columnSql = """SELECT column_name  FROM information_schema.columns WHERE lower(table_name)=? """ 
                
                

destinationConnection.eachRow(sql) { row ->
    def tableName = row.table_name.toLowerCase();
    
 
        if(tableName.startsWith("dw_")) {
        
            //for this table if corsposnding history table exists
            def historyTableName = "hist_"+tableName
            def resultRow = destinationConnection.firstRow(tableExistsSql, [historyTableName])
            
            //if exists insert record for this run
            
            if(resultRow.exists) {
               println "History  table exists for "+tableName
               println "Loading data for "+historyTableName
               
               StringBuffer columnNames = new StringBuffer(); 
               //needs to add double quotes around column names
               //because some of the column names are reserved keywords e.g. group
               
               destinationConnection.eachRow(columnSql,[tableName]) { columnRow ->
                   if (columnNames.size() == 0) {
                       columnNames.append("\""+columnRow.column_name+"\"");
                   } else {
                   columnNames.append(" , \""+columnRow.column_name+"\"");
                   } 
               }
               
              def inserQuerySql = "insert into "+historyTableName+"(run_id, "+columnNames.toString() +" ) "+
              " SELECT * FROM ( select RUN_ID from DW_RUN order by RUN_ID desc limit 1) t "+
              " CROSS JOIN ( SELECT "+columnNames.toString()+" from  "+tableName+" ) m"
                 
               
               destinationConnection.execute(inserQuerySql)
               
            }
        }
   
   
}
     
  
  destinationConnection.close()
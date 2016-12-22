import groovy.sql.Sql;
import groovyx.net.http.RESTClient
import org.apache.commons.lang.StringEscapeUtils

public class SyncDiseasesFromNCIt{

  def restClient = new RESTClient()
  
  StringBuffer fileContents = new StringBuffer("BEGIN;");

  TreeSet<String> parentsExtracted = new TreeSet<String>();
  TreeSet<String> childrenExtracted = new TreeSet<String>();

  def sql;

  public SyncDiseasesFromNCIt() {
      restClient.getClient().getParams().setParameter("http.socket.timeout", new Integer(60000));
  }
  

  def checkifTermExistsInNcit(def ncitCode, url){
      boolean isValid =false;
      def lexEVSRESTDetailsUrl = url+"/${ncitCode}"
    
     def response = null;
     try{
          response = restClient.get(uri: lexEVSRESTDetailsUrl)
          isValid = true;
     }
     catch(Exception e){
        println "Exception in checkifTermExistsInNcit "+e.getMessage();
         isValid = false;
         
     }
     return isValid;
  }   
 
  def getTermNames(def ncitCode, url){
    def lexEVSRESTDetailsUrl = url+"/${ncitCode}?format=xml"
   def response = null;
    response = restClient.get(uri: lexEVSRESTDetailsUrl)
    
    if (! response.success || response.status != 200) {
      println "-- fetchChildren - failed to fetch children for ${ncitCode} from ${url} with error ${response.data.text}"
      throw new RuntimeException("Failure from LexEVS: " + response.data.text)
    }

    def synonyms = []
    def doc = response.data;
    def element = doc.EntityDescription.namedEntity.designation.find{ it.@designationRole == 'PREFERRED' }
    def prefName;
    if(element!=null) {
        prefName =StringEscapeUtils.escapeSql(element.value?.text());
    }
  
    element = null;
    def altDesignations = doc.EntityDescription.namedEntity.designation.findAll{ it.@designationRole == 'ALTERNATIVE' }
    def synName
    for (def altDesignation : altDesignations) {
      synName = StringEscapeUtils.escapeSql(altDesignation.value?.text())
     
      if(!synonyms.contains(synName)){
        synonyms += synName
      }
    }
    
    def displayName="";
    try {
        def propertyElement = doc.EntityDescription.namedEntity.property.predicate.find{it.name =='Display_Name'};
        if (propertyElement!=null) {
             displayName =StringEscapeUtils.escapeSql(propertyElement.parent().value.literal.value?.text());
            
        }
    }//exception should not stop job
     catch (Exception e) {
        println "Exception in sync displayName ";
    }
    return [prefName, synonyms,displayName]
  }


  def generateDiseaseSynUpdateSQL(def ncitCode, def synonyms){
       String idQuery ="select identifier from pdq_disease where nt_term_identifier='${ncitCode}' limit 1"
       
  
    fileContents.append(" DELETE FROM pdq_disease_altername where disease_identifier =("+idQuery+");")
    synonyms.each{ syn ->
        fileContents.append(" INSERT INTO pdq_disease_altername ( ");
         fileContents.append("identifier,disease_identifier, alternate_name,status_code, status_date_range_low, date_last_created)");
          fileContents.append(" values ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')) , ("+idQuery+"),'${syn}','ACTIVE',now(),now() );")  }
    
  }

  /**
   * Extract and persist the parent tree for a term
   */
  def extractParentTree(def ncitCode , url) {
   
    def lexEVSRESTParentsUrl = url +"/${ncitCode}?format=xml"
    def parents = []
    def response = null
    response = restClient.get(uri: lexEVSRESTParentsUrl)
     

    if (! response.success || response.status != 200) {
      println "-- fetchChildren - failed to fetch parents for ${ncitCode} from ${url} with error ${response.data.text}"
      throw new RuntimeException("Failure from LexEVS: " + response.data.text)
    }
    
    //delete existing parent
    StringBuffer  deleteExistingParentQuery =new StringBuffer();
    deleteExistingParentQuery.append (" delete from pdq_disease_parent where identifier in ( ");
    deleteExistingParentQuery.append (" select identifier from pdq_disease_parent where disease_identifier = ");
    deleteExistingParentQuery.append (" (select identifier from pdq_disease where nt_term_identifier='"+ncitCode+"' limit 1));");
    fileContents.append(deleteExistingParentQuery.toString());
    def doc = response.data;
    def parentCodes = doc.EntityDescription.namedEntity.parent.name.findAll{ it.text().startsWith('C') }
    for (def parentCode : parentCodes) {
      if(!parents.contains(parentCode.text())){
        parents += parentCode.text()
      }
    }

    parentsExtracted.add(ncitCode)
    println "-- Parents of ${ncitCode}: ${parents}"
    parents.each { p ->
      insertOrUpdateTerm(p , url)
      insertParentChild(p,ncitCode)
      if(! parentsExtracted.contains(p)) {
        extractParentTree(p , url)
      }
    }

  }

  def insertParentChild( parent,  child) {
    
     String query = "select identifier from  pdq_disease where nt_term_identifier = '"+child+"' limit 1";
     String parentQuery ="select  identifier from  pdq_disease where nt_term_identifier = '"+parent+"' limit 1";
     String pdqParentquery = "select identifier from pdq_disease_parent where "+
     "disease_identifier=("+query+") and parent_disease_identifier=("+parentQuery+")";
  
    
      def insertParentSql = "INSERT INTO pdq_disease_parent(identifier,disease_identifier, parent_disease_identifier, parent_disease_code, status_code, status_date_range_low)"+
          " VALUES ((SELECT NEXTVAL('HIBERNATE_SEQUENCE')) , ("+query+"),("+parentQuery+"), 'ISA', 'ACTIVE',now());"
      fileContents.append(insertParentSql+";");
  
  }


  /**
   * Extract and persist a term
   */
  def insertOrUpdateTerm (def term, String url) {
    println "-- Extracting ${term}"
    def termNames = getTermNames(term, url)
    if(termNames!=null) {
        def prefName =   termNames[0]
        def displayName = termNames[2]
        def sqlInsertUpdate
       
        String selectExistingTerm =" select 1 from pdq_disease where nt_term_identifier = '"+term+"' ";
        
        if (sql.rows("select identifier from pdq_disease where nt_term_identifier = '"+term+"'").size() == 0) { // If term does not exist, insert it, else update
         sqlInsertUpdate = "INSERT INTO pdq_disease(identifier,nt_term_identifier, preferred_name, menu_display_name, status_code, status_date_range_low)"+
          " select (SELECT NEXTVAL('HIBERNATE_SEQUENCE')) , '"+term+"','"+prefName+"','"+ displayName+"', 'ACTIVE', now()"+
          " WHERE NOT EXISTS("+selectExistingTerm+");"
        }else {
        if (displayName!=null && displayName.length() > 0) {
        sqlInsertUpdate = " UPDATE pdq_disease set preferred_name='"+prefName+"',menu_display_name='"+displayName+"', date_last_updated=now() where nt_term_identifier='"+term+"';"
        } else {
        sqlInsertUpdate = " UPDATE pdq_disease set preferred_name='"+prefName+"',menu_display_name='"+prefName+"', date_last_updated=now() where nt_term_identifier='"+term+"';"
        }
       }
        
         fileContents.append(sqlInsertUpdate);
         // Update synonyms
        generateDiseaseSynUpdateSQL(term, termNames[1])
    }
    
   
  }

  /**
   * Extract and persist the child tree for the term
   */
  def extractChildTree(def ncitCode, url) {
    def lexEVSRESTChildrenUrl = url +"/${ncitCode}/children?maxtoreturn=1000"
    def response = null
  
        response = restClient.get(uri: lexEVSRESTChildrenUrl)
    

    if (! response.success || response.status != 200) {
      println "-- fetchChildren - failed to fetch children for ${ncitCode} from ${url} with error ${response.data.text}"
      throw new RuntimeException("Failure from LexEVS: " + response.data.text)
    }
    
    StringBuffer deleteExistingChildsQuery  = new StringBuffer();
    deleteExistingChildsQuery.append (" delete from pdq_disease_parent where identifier in ( ");
    deleteExistingChildsQuery.append (" select identifier from pdq_disease_parent where parent_disease_identifier = ");
    deleteExistingChildsQuery.append (" (select identifier from pdq_disease where nt_term_identifier='"+ncitCode+"' limit 1));");
    fileContents.append(deleteExistingChildsQuery.toString());

    def children = []
    def doc = response.data;
    def childName
    def childCodes = doc.entry.name.name.findAll{ it.text().startsWith('C')  }
    for (def childCode : childCodes) {
      if(!children.contains(childCode.text())){
        children += childCode.text()
      }
    }

    
    
    childrenExtracted.add(ncitCode)
    println "-- Children of ${ncitCode}: ${children}"
    children.each { c ->
       
      insertOrUpdateTerm(c , url)
      insertParentChild(ncitCode, c)
      if(! childrenExtracted.contains(c)) {
        extractChildTree(c , url)
      }
    }
  }
  
  def syncDiseaseParentChilds(ncitTerm, url) {
      println "-- SYNCING disease term "+ncitTerm
      insertOrUpdateTerm(ncitTerm, url)
      println "-- Syncing parents of CTRP term "+ncitTerm
       // Retrieve parent and children tree for the term
      extractParentTree(ncitTerm , url)
      println "-- Syncing children of CTRP term "+ncitTerm
      extractChildTree(ncitTerm , url)
  }
  
  def performSync(ncitTerm, url) {
      
      boolean isExists = checkifTermExistsInNcit(ncitTerm, url);
      
      if(isExists) {
          RetryUtil.retry(20, 10000){
                syncDiseaseParentChilds(ncitTerm, url);
          }
       }
      else {
          println "The term "+ncitTerm+" does not exists in ncit hence not synced"
      }
  }

  /**
   * Retrieve parent tree and child tree for all CTRP disease terms indexed on trials
   */
  def syncDiseaseTerms( String paJdbcUrl ,String outputDir, String url, dbuser, dbpassword) {
      def resolvedProperties = [:]
      
      sql = Sql.newInstance(paJdbcUrl, dbuser, dbpassword, "org.postgresql.Driver")
     
      
    def ctrpTerms = sql.rows("select distinct nt_term_identifier from pdq_disease, study_disease where study_disease.disease_identifier = pdq_disease.identifier and nt_term_identifier is not null order by nt_term_identifier")
    println "-- Syncing ${ctrpTerms.size()} CTRP Disease terms from NCIt..."
    List <String> ncitTermsList = new ArrayList<String>();
    
    ctrpTerms.each (){
      ncitTermsList.add(it.nt_term_identifier);
    }
   
    //closing connection here becase we don't need to query on study disease table
    sql.close();
    //need to keep connection again becaause we do select query pdq_disease table 
    sql = Sql.newInstance(paJdbcUrl, dbuser, dbpassword, "org.postgresql.Driver")
    for(String ncitTerm : ncitTermsList) {
        performSync(ncitTerm, url);
    }
   
    
    def outputFile = new File(outputDir)
    File sqlFile = new File(outputFile, "queries.sql")
    if(sqlFile.exists()) {
        sqlFile.delete();
    }
    def out = new FileOutputStream(sqlFile)
    def writer = new OutputStreamWriter( out , "UTF-8")
    fileContents.append(";COMMIT;")
    writer.write fileContents.toString()
    writer.flush();
    writer.close();
   
   sql.executeUpdate(fileContents.toString());
   sql.close();
   
   fileContents = new StringBuffer("BEGIN;");
   
   ctrpTerms = null
   sql = Sql.newInstance(paJdbcUrl, dbuser, dbpassword, "org.postgresql.Driver")
   ncitTermsList = new ArrayList<String>();
   //sync terms that does not have parent and childs and at the rool level as a result of sync
   ctrpTerms = sql.rows("select nt_term_identifier from pdq_disease where nt_term_identifier in ("
   +" select distinct(nt_term_identifier) from pdq_disease where nt_term_identifier not in "
   +" (select distinct nt_term_identifier from pdq_disease, study_disease "
   +" where study_disease.disease_identifier = pdq_disease.identifier and nt_term_identifier is not null order by nt_term_identifier) "
   +" ) and identifier not in (select parent_disease_identifier from pdq_disease_parent) "
   +" and identifier not in(select disease_identifier from pdq_disease_parent)");

  println "-- Syncing additional ${ctrpTerms.size()} CTRP Disease terms from NCIt..."

   ctrpTerms.each (){
       ncitTermsList.add(it.nt_term_identifier);
    }
   
   for(String ncitTerm : ncitTermsList) {
       performSync(ncitTerm, url);
   }
  
   fileContents.append(";COMMIT;")
   sql.executeUpdate(fileContents.toString());
   sql.close();
  }

 
}
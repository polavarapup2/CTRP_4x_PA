import groovy.sql.Sql
import groovy.xml.MarkupBuilder
import groovy.xml.StreamingMarkupBuilder
import org.apache.commons.lang3.StringUtils

public static void main(String [] args) {
    def resolvedProperties = [:]

if (!properties['ant.home']) {
    println 'We are not running from Ant; so accepting properties passed to JVM'
    resolvedProperties << System.getProperties()
} else
    resolvedProperties << properties

def paJdbcUrl = "jdbc:postgresql://${resolvedProperties['db.server']}:${resolvedProperties['db.port']}/${resolvedProperties['db.name']}"
def outputDir ="${resolvedProperties['output.dir']}"

def url ="${resolvedProperties['ncit.lexEVSRESTDetailsUrl']}"
def user = "${resolvedProperties['db.username']}"
def password =  "${resolvedProperties['pa.db.password']}"

String preferredNameUrl = "${resolvedProperties['ncit.interventionPreferredNameUrl']}"
String interventionSyncUrl ="${resolvedProperties['ncit.interventionSyncUrl']}"


println "the url here--->"+url
println "the preferredNameUrl here--->"+preferredNameUrl
println "the interventionSyncUrl here--->"+interventionSyncUrl

SyncDiseasesFromNCIt lexc = new SyncDiseasesFromNCIt()
lexc.syncDiseaseTerms(paJdbcUrl,outputDir,url,user,password);

PDQNCItInterventionMapper interventionMapper = new PDQNCItInterventionMapper()
interventionMapper.syncIntervention(outputDir,preferredNameUrl,interventionSyncUrl,paJdbcUrl,user,password)




println "************PDQ SYNC FINISHED******************"
}
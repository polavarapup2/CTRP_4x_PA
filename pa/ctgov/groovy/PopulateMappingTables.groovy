import java.util.regex.Pattern

import static org.apache.commons.lang.StringUtils.left
import groovy.sql.Sql

import java.util.regex.Pattern

import org.apache.commons.lang.ArrayUtils
import org.apache.commons.lang.StringUtils
import org.supercsv.io.CsvListReader
import org.supercsv.prefs.CsvPreference

def props = new Properties()
new File("resolved.build.properties").withInputStream { stream ->
    props.load(stream)
}

new Populator().run(props)

class Populator {


    Map<CtGovOrg, PdqOrg> agencies = new TreeMap<CtGovOrg, PdqOrg>()
    Map<CtGovOrg, PdqOrg> facilities = new TreeMap<CtGovOrg, PdqOrg>()
    Map<String, PdqOrg> cdrIdToPdqOrgMap  = new HashMap<String, PdqOrg>()

    Map<CtGovPerson, PdqPerson> investigators = new TreeMap<CtGovPerson, PdqPerson>()
    Map<CtGovPerson, PdqPerson> officials = new TreeMap<CtGovPerson, PdqPerson>()
    Map<String, PdqPerson> cdrIdToPdqPersonMap  = new HashMap<String, PdqPerson>()

    Sql paConnection
    Sql poConnection

    final List<String> STATES = Arrays.asList("alabama,alaska,arizona,arkansas,california,colorado,connecticut,delaware,district of columbia,florida,georgia,hawaii,idaho,illinois,indiana,iowa,kansas,kentucky,louisiana,maine,maryland,massachusetts,michigan,minnesota,mississippi,missouri,montana,nebraska,nevada,new hampshire,new jersey,new mexico,new york,north carolina,north dakota,ohio,oklahoma,oregon,palau,pennsylvania,puerto rico,rhode island,south carolina,south dakota,tennessee,texas,utah,vermont,virginia,washington,west virginia,wisconsin,wyoming".split(","))

    def run(props) {

        println "Using " + props['pa.jdbc.url'] + " to connect to PA database"
        println "Using " + props['po.jdbc.url'] + " to connect to PO database"

        paConnection = Sql.newInstance(props['pa.jdbc.url'], props['pa.db.username'],
                props['pa.db.password'], props['pa.jdbc.driver'])

        poConnection = Sql.newInstance(props['po.jdbc.url'], props['po.db.username'],
                props['po.db.password'], props['po.jdbc.driver'])

        def mappingFile = new File(props['mapping.file'])
        println "Using '" + mappingFile.getAbsolutePath() + "' as mapping file"

        def listReader = new CsvListReader(new FileReader(mappingFile), CsvPreference.STANDARD_PREFERENCE);
        listReader.getHeader(true);
        List<String> row;
        while( (row = listReader.read()) != null ) {
            String type = row[0]
            if ("CT.gov Agencies".equals(type)) {
                handleAgencyMapping(row)
            } else if ("CT.gov Facilities".equals(type)) {
                handleFacilityMapping(row)
            } else if ("CTEP_Institution_Code".equals(type)) {
                handleCTEPInstMapping(row)
            } else if ("CTRP_PO_ID".equals(type)) {
                handlePoIdMapping(row)
            } else if ("CT.gov Investigators".equals(type)) {
                handleInvestigatorMapping(row)
            } else if ("CT.gov Officials".equals(type)) {
                handleOfficialMapping(row)
            } else if ("CTSU_Person_ID".equals(type)) {
                handleCtsuIdMapping(row)
            } else {
                // println "Unknown mapping row type: ${type}"
            }
        }

        writeOrgMapping()
        writePersonMapping()
    }
    
    def writePersonMapping() {
        paConnection.execute("delete from ctgov_person_map")
        writePersonMapping(investigators, 'INVESTIGATOR')
        writePersonMapping(officials, 'OFFICIAL')
    }
    
    def writePersonMapping(Map<CtGovPerson, PdqPerson> map, String role) {
        map.each {ctgov, pdq ->
            paConnection.execute("INSERT INTO ctgov_person_map (role,ctgov_affiliation,ctgov_fullname,ctgov_firstname,ctgov_middlename,ctgov_lastname,ctgov_prefix,ctgov_suffix,cdr_id,pdq_fullname,pdq_firstname,pdq_middlename,pdq_lastname,pdq_city,pdq_state,pdq_country,po_id,ctep_id) VALUES (${role}, ${ctgov.affiliation}, ${ctgov.fullName}, ${ctgov.firstName}, ${ctgov.middleName}, ${ctgov.lastName}, ${ctgov.prefix}, ${ctgov.suffix}, ${pdq.cdrId}, ${pdq.fullName}, ${pdq.firstName}, ${pdq.middleName}, ${pdq.lastName}, ${pdq.city},${pdq.state},${pdq.country}, ${pdq.getPoIdsString()}, ${pdq.getCtepIdsString()})")
        }
    }

    def writeOrgMapping() {
        paConnection.execute("delete from ctgov_org_map")
        writeOrgMapping(agencies, 'AGENCY')
        writeOrgMapping(facilities, 'FACILITY')
    }

    def writeOrgMapping(Map<CtGovOrg, PdqOrg> map, String role) {
        map.each {ctgov, pdq ->
            paConnection.execute("INSERT INTO ctgov_org_map (role,ctgov_name,ctgov_city,ctgov_state,ctgov_zip,ctgov_country,cdr_id,pdq_name,pdq_city,pdq_state,pdq_country,po_id,ctep_id) VALUES (${role}, ${ctgov.name}, ${ctgov.city}, ${ctgov.state}, ${ctgov.zip}, ${ctgov.country}, ${pdq.cdrId}, ${pdq.name}, ${pdq.city}, ${pdq.state}, ${pdq.country}, ${pdq.getPoIdsString()}, ${pdq.getCtepIdsString()})")
        }
    }

    def validateAndPrintWarnings(final PdqOrg org) {
        org.poIds.each {poid -> checkPoOrgNameMatch(org.name, poid) }
    }

    def checkPoOrgNameMatch(name, poid) {
        def poOrgName = poConnection.firstRow("select name from organization where id=cast(${poid} as bigint)").getAt(0)
        if (StringUtils.getLevenshteinDistance(name, poOrgName)>20) {
            println "Potential mismatch between PO Org ${poOrgName} and PDQ Org ${name}"
        }
    }

    def handlePoIdMapping(List<String> row) {
        final String id = row[1]
        String cdrID = row[2]

        if (StringUtils.isNotBlank(id)) {
            boolean isOrgPoId = isOrgPoId(id)
            boolean isOrgCtepId = !isOrgPoId && isOrgCtep(id)
            findPdqOrgsByCdrId(cdrID).each {org ->
                if (isOrgCtepId) org.addCtepId(id)
                if (isOrgPoId) org.addPoId(id)
            }

            boolean isPersonPoId = isPersonPoId(id)
            boolean isPersonCtepId = !isPersonPoId
            findPdqPersonsByCdrId(cdrID).each {p ->
                if (isPersonCtepId) p.addCtepId(id)
                if (isPersonPoId) p.addPoId(id)
            }
        }
    }

    def handleCtsuIdMapping(List<String> row) {
        final String id = row[1]
        String cdrID = row[2]
        findPdqPersonsByCdrId(cdrID).each {p ->
            p.addCtepId(id)
        }
    }

    def isPersonPoId(poid) {
        if (!StringUtils.isNumeric(poid)) {
            return false;
        }
        return !poConnection.rows("select * from person where id=cast(${poid} as bigint)").isEmpty()
    }

    def isOrgCtep(String ctepid) {
        return ctepid.find(/[a-zA-Z]/)!=null || !poConnection.rows("select * from identifiedorganization where assigned_identifier_extension=${ctepid} and assigned_identifier_root='2.16.840.1.113883.3.26.6.2'").isEmpty()
    }

    def isOrgPoId(poid) {
        if (!StringUtils.isNumeric(poid)) {
            return false;
        }
        return !poConnection.rows("select * from organization where id=cast(${poid} as bigint)").isEmpty()
    }

    def handleCTEPInstMapping(List<String> row) {
        final String ctepID = row[1]
        String cdrID = row[2]

        if (StringUtils.isNotBlank(ctepID)) {
            findPdqOrgsByCdrId(cdrID).each {org ->
                org.addCtepId(ctepID)
            }
        }
    }

    def findPdqOrgsByCdrId(String cdrId) {
        return cdrIdToPdqOrgMap.get(cdrId)
    }

    def findPdqPersonsByCdrId(String cdrId) {
        return cdrIdToPdqPersonMap.get(cdrId)
    }

    def handleInvestigatorMapping(List<String> row) {
        String ctName = row[1]
        String cdrId = row[2]
        String pdqName = row[3]

        CtGovPerson ct = new CtGovPerson()
        PdqPerson pdq = new PdqPerson(cdrId:cdrId)

        populatePersonFromBarSeparatedInfo(ct, ctName)
        populatePersonFromPdqInfo(pdq, pdqName)

        investigators.put(ct, pdq)
        addToCdrToPdqPersonMap(pdq)
    }

    def handleOfficialMapping(List<String> row) {
        String ctName = row[1]
        String cdrId = row[2]
        String pdqName = row[3]

        CtGovPerson ct = new CtGovPerson()
        PdqPerson pdq = new PdqPerson(cdrId:cdrId)

        populatePersonFromReverseBarSeparatedInfo(ct, ctName)
        populatePersonFromPdqInfo(pdq, pdqName)

        officials.put(ct, pdq)
        addToCdrToPdqPersonMap(pdq)
    }

    def populatePersonFromPdqInfo(Person p, String str) {
        String[] parts = str.split(";")

        def addressParts = ArrayUtils.subarray(parts, 1, Integer.MAX_VALUE)
        parseAddressPartsAndPopulateOrg(p, addressParts)

        p.fullName = parts[0]
        String[] firstAndLastParts = p.fullName.split(",\\s*")
        if (firstAndLastParts.length<2) {
            // println "Warning: PDQ person name has less than two parts: ${p.fullName}"
            p.lastName = firstAndLastParts[0]
        } else {
            p.firstName = firstAndLastParts[1]
            p.lastName = firstAndLastParts[0]
            if (firstAndLastParts.length>2) {
                println "Warning: PDQ person name has more than two parts: ${p.fullName}"
            }
        }
    }

    def populatePersonFromBarSeparatedInfo(Person p, String str) {
        String[] parts = str.split("\\|")
        populatePersonFromNameParts(p, parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim())
    }

    def populatePersonFromReverseBarSeparatedInfo(Person p, String str) {
        String[] parts = str.split("\\|")
        if (parts.length>=4) {
            populatePersonFromNameParts(p, parts[3].trim(), parts[0].trim(), parts[1].trim(), parts[2].trim())
        } else {
            println "Incomplete CTGov Person info; affiliation missing most likely: ${str}"
            populatePersonFromNameParts(p, "", parts[0].trim(), parts[1].trim(), parts[2].trim())
        }
    }

    def populatePersonFromNameParts(Person p, String affiliation, String firstName, String middleName, String lastName) {
        p.affiliation = affiliation
        if (firstName.isEmpty() && middleName.isEmpty()) {
            populatePersonFromFullName(p, lastName)
        } else {
            p.firstName = firstName
            p.middleName = middleName
            p.lastName = lastName
            p.fullName = ((firstName.isEmpty()?"":firstName+" ")+(middleName.isEmpty()?"":middleName+" ")
                    +(lastName.isEmpty()?"":lastName)).trim()
        }
    }

    def populatePersonFromFullName(Person person, String fullName) {
        person.fullName = fullName

        def prefix, suffix, lastName, firstName, middleName

        def p = Pattern
                .compile(Const.FULL_NAME_PATTERN);
        def m = p.matcher(fullName);
        if (!m.matches()) {
        } else {
            prefix = m.group(1)
            suffix = m.group(5)
            String mainNamePart = m.group(2).trim();
            String[] parts = mainNamePart.split("\\s+");
            switch (parts.length) {
                case 1:
                    lastName = mainNamePart;
                    firstName = "";
                    break;
                case 2:
                    firstName = parts[0];
                    lastName = parts[1];
                    break;
                case 3:
                    firstName = parts[0];
                    middleName = parts[1];
                    lastName = parts[2];
                    break;
                default:
                    firstName = parts[0];
                    middleName = parts[1];
                    lastName = StringUtils.join(
                            Arrays.copyOfRange(parts, 2, parts.length), " ");
                    break;
            }
        }

        person.firstName = firstName
        person.middleName = middleName
        person.lastName = lastName
        person.prefix = prefix
        person.suffix = suffix
    }

    def addToCdrToPdqPersonMap(PdqPerson p) {
        String cdrId = p.cdrId
        List list = cdrIdToPdqPersonMap.get(cdrId)
        if (list == null) {
            list = new ArrayList()
            cdrIdToPdqPersonMap.put(cdrId, list)
        }
        list.add(p)
    }

    def handleFacilityMapping(List<String> row) {
        String ctName = row[1]
        String cdrId = row[2]
        String pdqName = row[3]

        CtGovOrg ctOrg = new CtGovOrg()
        PdqOrg pdqOrg = new PdqOrg(cdrId:cdrId)

        populateOrgFromBarSeparatedInfo(ctOrg, ctName)
        populateOrgFromSemicolonSeparatedInfo(pdqOrg, pdqName)

        facilities.put(ctOrg, pdqOrg)
        addToCdrToPdqOrgMap(pdqOrg)
    }

    def populateOrgFromBarSeparatedInfo(Org org, String str) {
        String[] parts = str.split("\\|")
        org.name = parts[0].trim()

        if (parts.length >= 2) {
            def addressParts = ArrayUtils.subarray(parts, 1, Integer.MAX_VALUE)
            parseExtendedAddressPartsAndPopulateOrg(org, addressParts)
        }
    }

    def handleAgencyMapping(List<String> row) {
        String ctName = row[1]
        String cdrId = row[2]
        String pdqName = row[3]

        CtGovOrg ctOrg = new CtGovOrg()
        PdqOrg pdqOrg = new PdqOrg(cdrId:cdrId)

        populateOrgFromSemicolonSeparatedInfo(ctOrg, ctName)
        populateOrgFromSemicolonSeparatedInfo(pdqOrg, pdqName)

        agencies.put(ctOrg, pdqOrg)
        addToCdrToPdqOrgMap(pdqOrg)
    }

    def addToCdrToPdqOrgMap(PdqOrg org) {
        String cdrId = org.cdrId
        List list = cdrIdToPdqOrgMap.get(cdrId)
        if (list == null) {
            list = new ArrayList()
            cdrIdToPdqOrgMap.put(cdrId, list)
        }
        list.add(org)
    }

    def populateOrgFromSemicolonSeparatedInfo(Org org, String str) {
        String[] parts = str.split(";")
        org.name = parts[0].trim()

        if (parts.length >= 2) {
            def addressParts = ArrayUtils.subarray(parts, 1, Integer.MAX_VALUE)
            parseAddressPartsAndPopulateOrg(org, addressParts)
        }
    }

    def parseAddressPartsAndPopulateOrg(orgOrPerson, String[] parts) {
        if (parts.length>=2) {
            String stateOrCountry = parts[parts.length-1]
            if (isUSState(stateOrCountry)) {
                orgOrPerson.country = "USA"
                orgOrPerson.state = stateOrCountry
                orgOrPerson.city = parts[parts.length-2]
            } else {
                orgOrPerson.country = stateOrCountry
                orgOrPerson.city = parts[parts.length-2]
            }
        } else {
            println " Address consists of a single part -- ${parts} -- skipping..."
        }
    }

    def parseExtendedAddressPartsAndPopulateOrg(Org org, String[] parts) {
        if (parts.length < 4) {
            println "Extended address incomplete: ${parts}"
        } else {
            org.city = parts[0]
            org.state = parts[1]
            org.zip = parts [2]
            org.country = parts [3]
        }
    }

    def isUSState(String str) {
        STATES.contains(str.toLowerCase().trim())
    }
}



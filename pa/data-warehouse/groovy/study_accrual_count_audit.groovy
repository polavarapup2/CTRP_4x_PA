import groovy.sql.Sql

def sourceConnection = Sql.newInstance(properties['datawarehouse.pa.source.jdbc.url'], properties['datawarehouse.pa.source.db.username'],
    properties['datawarehouse.pa.source.db.password'], properties['datawarehouse.pa.source.jdbc.driver'])
def destinationConnection = Sql.newInstance(properties['datawarehouse.pa.dest.jdbc.url'], properties['datawarehouse.pa.dest.db.username'],
    properties['datawarehouse.pa.dest.db.password'], properties['datawarehouse.pa.dest.jdbc.driver'])
def audit = destinationConnection.dataSet("stg_dw_study_accrual_count_audit")

def sql1 = """
SELECT entityid, newvalue::integer, createddate, sssac.study_site_identifier
FROM auditlogrecord_022713 rec
JOIN auditlogdetail_022713 det ON (rec.id = det.record_id)
JOIN study_site_subject_accrual_count sssac ON (rec.entityid = sssac.identifier)
WHERE rec.entityname = 'STUDY_SITE_SUBJECT_ACCRUAL_COUNT'
  AND det.attribute = 'accrualCount'
"""

def sql2 = """
SELECT entityid, newvalue::integer, createddate, sssac.study_site_identifier
FROM auditlogrecord rec
JOIN auditlogdetail det ON (rec.id = det.record_id)
JOIN study_site_subject_accrual_count sssac ON (rec.entityid = sssac.identifier)
WHERE rec.entityname = 'STUDY_SITE_SUBJECT_ACCRUAL_COUNT'
  AND det.attribute = 'accrualCount'
"""

sourceConnection.eachRow(sql1) { row ->
        audit.add(
            study_site_id: row.study_site_identifier,
            accrual_count: row.newvalue,
            entityid: row.entityid,
            createddate: row.createddate
            )
    }

sourceConnection.eachRow(sql2) { row ->
        audit.add(
            study_site_id: row.study_site_identifier,
            accrual_count: row.newvalue,
            entityid: row.entityid,
            createddate: row.createddate
            )
    }

sourceConnection.close()
destinationConnection.close()
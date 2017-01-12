/**
 * 
 */
package gov.nih.nci.pa.util.testsupport;

import static gov.nih.nci.pa.util.testsupport.HsqlDbTriggerSupportInterceptor.SQLS_TO_RUN;

/**
 * @author dkrylov
 * 
 */
// CHECKSTYLE:OFF
public final class set_current_study_overall_status extends // NOPMD
        AbstractHsqlDbSupportTrigger {

    /*
     * (non-Javadoc)
     * 
     * @see org.hsqldb.Trigger#fire(int, java.lang.String, java.lang.String,
     * java.lang.Object[], java.lang.Object[])
     */
    @Override
    public void fireInternal(int type, String trigName, String tabName,
            Object[] oldRow, Object[] newRow) { // NOPMD

        Long spID = (Long) (newRow != null ? newRow[columnIndex(tabName,
                "study_protocol_identifier")] : oldRow[columnIndex(tabName,
                        "study_protocol_identifier")]);
        if (spID != null) {
            SQLS_TO_RUN
                    .add("update study_overall_status sos1 set current = false where sos1.study_protocol_identifier="
                            + spID);
            SQLS_TO_RUN
                    .add("update study_overall_status sos1 set current = true WHERE sos1.study_protocol_identifier="
                            + spID
                            + " AND                 (                    sos1.identifier =                    (                       SELECT                       sos2.identifier                       FROM study_overall_status sos2                       WHERE                       (                          (sos1.study_protocol_identifier = sos2.study_protocol_identifier)                          AND (sos2.deleted = false)                       )                       ORDER BY sos2.status_date DESC, sos2.identifier DESC LIMIT 1                    )             )");

        }

    }

}

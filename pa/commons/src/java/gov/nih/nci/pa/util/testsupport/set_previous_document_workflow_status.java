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
public final class set_previous_document_workflow_status extends // NOPMD
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
                    .add("update document_workflow_status dwf1 set previous = false where dwf1.study_protocol_identifier="
                            + spID);
            SQLS_TO_RUN
                    .add("update document_workflow_status dwf1 set previous = true WHERE dwf1.study_protocol_identifier="
                            + spID
                            + " AND                    (                       dwf1.identifier IN                      (                          SELECT                          dwf2.identifier                         FROM document_workflow_status dwf2                          WHERE (dwf1.study_protocol_identifier = dwf2.study_protocol_identifier)                         ORDER BY dwf2.status_date_range_low DESC LIMIT 1 OFFSET 1                        )                    )");

        }

    }

}

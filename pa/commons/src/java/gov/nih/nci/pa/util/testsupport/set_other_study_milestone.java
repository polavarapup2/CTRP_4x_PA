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
public final class set_other_study_milestone extends // NOPMD
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
                    .add("update study_milestone sm1 set other = false where sm1.study_protocol_identifier="
                            + spID);
            SQLS_TO_RUN
                    .add("            update study_milestone sm1 set other = true WHERE sm1.study_protocol_identifier="
                            + spID
                            + " AND                 (                    sm1.identifier IN                   (                      SELECT                      sm2.identifier                      FROM study_milestone sm2                    WHERE                       (                          (sm1.study_protocol_identifier = sm2.study_protocol_identifier)                         AND                         (                                                         sm1.milestone_code                                                       NOT IN                          (                              'ADMINISTRATIVE_PROCESSING_START_DATE',                             'ADMINISTRATIVE_PROCESSING_COMPLETED_DATE',                             'ADMINISTRATIVE_READY_FOR_QC',                              'ADMINISTRATIVE_QC_START',                              'ADMINISTRATIVE_QC_COMPLETE',                               'SCIENTIFIC_PROCESSING_START_DATE',                             'SCIENTIFIC_PROCESSING_COMPLETED_DATE',                             'SCIENTIFIC_READY_FOR_QC',                              'SCIENTIFIC_QC_START',                              'SCIENTIFIC_QC_COMPLETE'                             )                        )                    )                       ORDER BY sm2.milestone_date DESC, sm2.identifier DESC LIMIT 1                    )                )");

        }

    }

}

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
public final class set_last_study_milestone extends // NOPMD
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
                    .add("update study_milestone sm1 set last = false where sm1.study_protocol_identifier = "
                            + spID);
            SQLS_TO_RUN
                    .add("update study_milestone sm1 set last = true  WHERE sm1.study_protocol_identifier = "
                            + spID
                            + " AND                                            sm1.identifier =                                                         (SELECT                               sm2.identifier                               FROM study_milestone sm2                               WHERE sm1.study_protocol_identifier = sm2.study_protocol_identifier                               ORDER BY sm2.milestone_date DESC, sm2.identifier DESC LIMIT 1  )                                                 ");

        }

    }

}

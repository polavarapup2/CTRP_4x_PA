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
public final class sync_trial_nci_id extends // NOPMD
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
                "study_protocol_id")] : oldRow[columnIndex(tabName,
                "study_protocol_id")]);
        if (spID != null) {
            SQLS_TO_RUN
                    .add("update study_protocol set nci_id = (select extension from rv_trial_id_nci where study_protocol_id="
                            + spID + " limit 1) where identifier=" + spID);
        }

    }

}

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
public final class sync_trial_identifier_assigner_ids extends // NOPMD
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
                    .add("update study_protocol set dcp_id = (select local_sp_indentifier from rv_dcp_id where study_protocol_identifier="
                            + spID + " limit 1) where identifier=" + spID); // NOPMD
            SQLS_TO_RUN
                    .add("update study_protocol set ccr_id = (select local_sp_indentifier from rv_ccr_id where study_protocol_identifier="
                            + spID + " limit 1) where identifier=" + spID);
            SQLS_TO_RUN
                    .add("update study_protocol set nct_id = (select local_sp_indentifier from rv_trial_id_nct where study_protocol_identifier="
                            + spID + " limit 1) where identifier=" + spID);

            SQLS_TO_RUN
                    .add("update study_protocol set ctep_id = (select local_sp_indentifier from rv_ctep_id where study_protocol_identifier="
                            + spID + " limit 1) where identifier=" + spID);

            SQLS_TO_RUN
                    .add("update study_protocol set lead_org_id = (select local_sp_indentifier from rv_lead_organization where study_protocol_identifier="
                            + spID + " limit 1) where identifier=" + spID);

        }

    }

}

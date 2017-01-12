/**
 * 
 */
package gov.nih.nci.pa.util.testsupport;

import gov.nih.nci.pa.util.PaHibernateUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.hsqldb.Trigger;

/**
 * @author dkrylov
 * 
 */
// CHECKSTYLE:OFF
public abstract class AbstractHsqlDbSupportTrigger implements Trigger {

    public static volatile boolean ENABLED = true; // NOPMD

    /*
     * (non-Javadoc)
     * 
     * @see org.hsqldb.Trigger#fire(int, java.lang.String, java.lang.String,
     * java.lang.Object[], java.lang.Object[])
     */
    @Override
    public final void fire(int type, String trigName, String tabName,
            Object[] oldRow, Object[] newRow) { // NOPMD
        if (ENABLED) {
            fireInternal(type, trigName, tabName, oldRow, newRow);
        }

    }

    /**
     * @param type
     *            type
     * @param trigName
     *            trigName
     * @param tabName
     *            tabName
     * @param oldRow
     *            oldRow
     * @param newRow
     *            newRow
     */
    protected abstract void fireInternal(int type, String trigName,
            String tabName, Object[] oldRow, Object[] newRow);// NOPMD

    @SuppressWarnings("resource")
    public final int columnIndex(final String table, final String column) {
        Connection c = PaHibernateUtil.getCurrentSession().connection();
        ResultSet rs = null;
        try {
            DatabaseMetaData meta = c.getMetaData();
            rs = meta.getColumns(null, null, table, column.toUpperCase()); // NOPMD
            while (rs.next()) {
                if (rs.getString("COLUMN_NAME").equalsIgnoreCase(column)) {
                    return rs.getInt("ORDINAL_POSITION") - 1; // NOPMD
                }
            }
            throw new RuntimeException("Column not found: " + table + "."
                    + column);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(rs);
        }

    }

}

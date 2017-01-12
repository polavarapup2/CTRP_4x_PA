/**
 * 
 */
package gov.nih.nci.pa.util.testsupport;

import gov.nih.nci.pa.util.PaHibernateUtil;

import java.sql.Statement;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.hibernate.EmptyInterceptor;

/**
 * Only used for integration testing with HSQLDB and JUnit.
 * 
 * @author dkrylov
 * 
 */
public final class HsqlDbTriggerSupportInterceptor extends EmptyInterceptor {

    /**
     * SQLS_TO_RUN. Use only in tests.
     */
    public static final Queue<String> SQLS_TO_RUN = new LinkedBlockingQueue<>();

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("rawtypes")
    @Override
    public void postFlush(final Iterator entities) {
        String sql = null;
        while ((sql = SQLS_TO_RUN.poll()) != null) {
            run(sql);
        }
    }

    private void run(final String sql) {
        AbstractHsqlDbSupportTrigger.ENABLED = false;
        try (Statement st = PaHibernateUtil.getCurrentSession().connection()
                .createStatement()) {
            st.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            AbstractHsqlDbSupportTrigger.ENABLED = true;
        }
    }

}

/**
 * 
 */
package gov.nih.nci.pa.util;

import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.Session;

/**
 * Access point to the misc_documents table.
 * 
 * @author Denis G. Krylov
 * 
 */
public final class MiscDocumentUtils {

    /**
     * Returns content of the first found document with the given name and for
     * the given application, ignoring the version.
     * 
     * @param docName
     *            String
     * @param appName
     *            String
     * @return String
     */
    public static String getDocumentContent(String docName, String appName) {
        Session s = PaHibernateUtil.getCurrentSession();
        return (String) s.createSQLQuery(
                "select content from misc_documents where application='"
                        + StringEscapeUtils.escapeSql(appName) + "' and name='"
                        + StringEscapeUtils.escapeSql(docName) + "' limit 1")
                .uniqueResult();
    }
    
    /**
     * Returns version of the first found document with the given name and for
     * the given application, ignoring the version.
     * 
     * @param docName
     *            String
     * @param appName
     *            String
     * @return String
     */
    public static String getDocumentVersion(String docName, String appName) {
        Session s = PaHibernateUtil.getCurrentSession();
        return (String) s.createSQLQuery(
                "select version from misc_documents where application='"
                        + StringEscapeUtils.escapeSql(appName) + "' and name='"
                        + StringEscapeUtils.escapeSql(docName) + "' limit 1")
                .uniqueResult();
    }
    
    /**
     * Returns expiration date of the first found document with the given name and for
     * the given application, ignoring the version.
     * 
     * @param docName
     *            String
     * @param appName
     *            String
     * @return String
     */
    public static Date getDocumentExpiration(String docName, String appName) {
        Session s = PaHibernateUtil.getCurrentSession();
        return (Date) s.createSQLQuery(
                "select expiration_date from misc_documents where application='"
                        + StringEscapeUtils.escapeSql(appName) + "' and name='"
                        + StringEscapeUtils.escapeSql(docName) + "' limit 1")
                .uniqueResult();
    }
}

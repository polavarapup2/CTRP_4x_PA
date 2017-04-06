package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.lang.StringUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugh Reinhart
 * @since Jun 26, 2013
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class I2EGrantsServiceBean implements I2EGrantsServiceLocal {

    private static final int COL_SN = 1;
    private static final int COL_INST = 2;
    private static final int COL_PROJ = 3;
    private static final int COL_FIRST = 4;
    private static final int COL_LAST = 5;

    private static final String SQL_SEARCH =
            "SELECT serial_number, institution_name, project_title, pi_first_name, pi_last_name "
            + "FROM grants "
            + "WHERE to_char(serial_number, 'FM999999999999999999') LIKE ? ";

    private static final String SQL_VALIDATE =
            "SELECT COUNT(*) "
            + "FROM grants "
            + "WHERE serial_number = ?";

    @EJB
    private LookUpTableServiceRemote lookUpTableSvc;

    /** DB Connection. */
    private static Connection conn;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<I2EGrant> getBySerialNumber(String serialNumber) throws PAException {
        String data = fixSerialNumber(serialNumber);
        if (data == null) {
            return new ArrayList<I2EGrant>();
        }
        ResultSetHandler<List<I2EGrant>> handler = new ResultSetHandler<List<I2EGrant>>() {
            public List<I2EGrant> handle(ResultSet rs) throws SQLException {
                List<I2EGrant> result = new ArrayList<I2EGrant>(); 
                while (rs.next()) {
                    I2EGrant grant = new I2EGrant();
                    grant.setSerialNumber(String.valueOf(rs.getObject(COL_SN)));
                    grant.setOrganizationName((String) rs.getObject(COL_INST));
                    grant.setProjectName((String) rs.getObject(COL_PROJ));
                    grant.setPiFirstName((String) rs.getObject(COL_FIRST));
                    grant.setPiLastName((String) rs.getObject(COL_LAST));
                    result.add(grant);
                }
                return result;
            }
        };
        QueryRunner run = new QueryRunner();
        List<I2EGrant> result = new ArrayList<I2EGrantsServiceLocal.I2EGrant>();
        Connection connection = getConnection();
        try {
            result = run.query(connection, SQL_SEARCH, handler, data + "%");
        } catch (SQLException e) {
            throw new PAException("Error querying Postgres database.", e);
        }
        return result;
    }

    @Override
    public Boolean isValidCaGrant(String serialNumber) throws PAException {
        String data = fixSerialNumber(serialNumber);
        if (data == null) {
            return false;
        }
        Boolean result = false;
        Connection connection = getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(SQL_VALIDATE);
            stmt.setString(1, data);
            rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new PAException("Error querying Postgres database.", e);
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(stmt);
        }
        return result;
    }

    private String fixSerialNumber(String serialNumber) {
        String regex = "[0-9]+"; 
        String result = StringUtils.trim(serialNumber); 
        if (result == null || !result.matches(regex)) {
            result = null;
        }
        return result;
    }

    private synchronized Connection getConnection() throws PAException {
        try {
            if (conn != null) {
                return conn;
            }
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new PAException("Couldn't find driver class.", e);
            }
            conn = DriverManager.getConnection(lookUpTableSvc.getPropertyValue("I2EGrantsUrl"));
        } catch (SQLException e) {
            throw new PAException("Postgres connection failed.", e);
        }
        return conn;
    }

    /**
     * @return the conn
     */
    public static Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public static void setConn(Connection conn) {
        I2EGrantsServiceBean.conn = conn;
    }
}

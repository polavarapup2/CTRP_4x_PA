package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.service.util.I2EGrantsServiceLocal.I2EGrant;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.sql.Statement;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Hugh Reinhart
 * @since Jul 29, 2013
 */
public class I2EGrantsServiceBeanTest extends AbstractHibernateTestCase {
    I2EGrantsServiceLocal svc;

    @Before
    public void setup() throws Exception {
        svc = new I2EGrantsServiceBean();
        PaHibernateUtil.getHibernateHelper().beginTransaction();
        Statement s = PaHibernateUtil.getCurrentSession().connection().createStatement();
        s.execute("drop table if exists ctrp_grants_r_vw;");
        s.execute("create table ctrp_grants_r_vw (serial_number DECIMAL, " 
                + "institution_name character varying, project_title character varying, "
                + "pi_first_name character varying, pi_last_name character varying, rownum integer);");
        s.execute("insert into ctrp_grants_r_vw values (123,'orgName1','title1','piFirst1','piLast1', 1);");
        s.execute("insert into ctrp_grants_r_vw values (1234,'orgName2','title2','piFirst2','piLast2', 2);");
        I2EGrantsServiceBean.setConn(PaHibernateUtil.getCurrentSession().connection());
    }

    @Test
    public void getBySerialNumberNull() throws Exception {
        assertTrue(svc.getBySerialNumber(null).isEmpty());
    }

    @Test
    public void getBySerialNumberFound1() throws Exception {
        List<I2EGrant> result = svc.getBySerialNumber("1234");
        assertEquals(1, result.size());
        assertEquals("1234 - orgName2; title2; piFirst2 piLast2", result.get(0).toString());
    }

    @Test
    public void getBySerialNumberFound2() throws Exception {
        assertEquals(2, svc.getBySerialNumber("123").size());
    }

    @Test
    public void getBySerialNumberNotFound() throws Exception {
        assertTrue(svc.getBySerialNumber("23").isEmpty());
    }

    @Test
    public void isValidCaGrantNull() throws Exception {
        assertFalse(svc.isValidCaGrant(null));
    }

    @Test
    public void isValidCaGrantBadData() throws Exception {
        assertFalse(svc.isValidCaGrant("abd"));
    }

    @Test
    public void isValidCaGrantNotFound() throws Exception {
        assertFalse(svc.isValidCaGrant("23"));
    }

    @Test
    public void isValidCaGrantFound() throws Exception {
        assertTrue(svc.isValidCaGrant(" 123 "));
    }
}

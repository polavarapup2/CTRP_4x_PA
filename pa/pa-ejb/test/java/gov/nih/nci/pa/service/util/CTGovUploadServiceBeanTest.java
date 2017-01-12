/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

/**
 * @author Denis G. Krylov
 * 
 */
public class CTGovUploadServiceBeanTest extends AbstractHibernateTestCase {

    private CTGovUploadServiceBeanLocal serviceBean;
    private FakeFtpServer fakeFtpServer;
    private UnixFakeFileSystem fileSystem;

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.pa.util.AbstractMockitoTest#setUp()
     */
    @Before
    public void setUp() throws Exception {

        AbstractMockitoTest mockitoTest = new AbstractMockitoTest();
        mockitoTest.setUp();

        serviceBean = new CTGovUploadServiceBeanLocal();
        serviceBean.setQueryServiceLocal(mockitoTest
                .getProtocolQueryServiceLocal());
        serviceBean.setGeneratorServiceLocal(mockitoTest
                .getCtGovXmlGeneratorServiceLocal());
        serviceBean.setLookUpTableService(mockitoTest.getLookupSvc());
        serviceBean.setCtGovSyncService(mockitoTest.getCtGovSyncServiceLocal());

        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.setServerControlPort(51239); // use any free port

        fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new FileEntry("/test.txt", ""));
        fakeFtpServer.setFileSystem(fileSystem);

        UserAccount userAccount = new UserAccount("ctrppa", "ctrppa", "/");
        userAccount.setAccountRequiredForLogin(true);
        userAccount.setPasswordCheckedDuringValidation(true);
        fakeFtpServer.addUserAccount(userAccount);

        fakeFtpServer.start();

        final Field propsField = PaEarPropertyReader.class
                .getDeclaredField("PROPS");
        propsField.setAccessible(true);
        Properties props = (Properties) propsField.get(null);
        props.put("ctgov.ftp.url", "ftp://ctrppa:ctrppa@localhost:51239/");

        TestSchema.primeData();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void done() throws Exception {
        try {
            fakeFtpServer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.CTGovUploadServiceBeanLocal#uploadToCTGov()}
     * .
     * 
     * @throws PAException
     * @throws IOException
     * @throws HibernateException
     */
    @Test
    public final void testUploadToCTGov() throws PAException,
            HibernateException, IOException {
        Session s = PaHibernateUtil.getCurrentSession();
        SQLQuery query = PaHibernateUtil
                .getCurrentSession()
                .createSQLQuery(
                        "insert into prs_sync_history(sync_date, data) values(:date,:data)");
        query.setText("data", "old");
        query.setTimestamp("date", DateUtils.addDays(new Date(), -31));
        query.executeUpdate();
        s.flush();

        serviceBean.uploadToCTGov();
        assertTrue(fileSystem.exists("/clinical.txt"));

        assertEquals(IOUtils.toString(getClass().getResourceAsStream(
                "/CDR360805.xml")),
                s.createSQLQuery("select data from prs_sync_history")
                        .uniqueResult());

    }

    @Test
    public void testNonApplicablePcdIsExcluded() throws PAException {

        Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocol studyProtocol = (StudyProtocol) session.get(
                StudyProtocol.class, 2L);
        studyProtocol.getDates().setPrimaryCompletionDateTypeCode(
                ActualAnticipatedTypeCode.NA);
        studyProtocol.getDates().setPrimaryCompletionDate(null);
        session.update(studyProtocol);
        session.flush();

        List<Ii> idsList = serviceBean.getTrialIdsForUpload();

        assertTrue(idsList.size() == 0);

    }

    @Test
    public void checkTerminalStatusAndCCRExcluded() throws PAException {
        List<Ii> idsList = serviceBean.getTrialIdsForUpload();

        assertTrue(idsList.size() == 1);

        Ii ii = idsList.get(0);
        assertTrue(ii.getExtension().equals("2"));
    }

}

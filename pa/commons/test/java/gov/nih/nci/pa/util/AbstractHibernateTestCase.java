/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The nci-commons
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This nci-commons Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the nci-commons Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the nci-commons Software; (ii) distribute and
 * have distributed to and by third parties the nci-commons Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.util;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.IOUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.persister.collection.AbstractCollectionPersister;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;

/**
 * @author Scott Miller
 * 
 */
public abstract class AbstractHibernateTestCase {

    protected Transaction transaction;

    /**
     * Sets up the SessionFactory, transaction, and everything else needed for
     * hibernate tests.
     */
    @SuppressWarnings("unchecked")
    @Before
    public final void setUpHibernate() {
        // clean up the hibernate second level cache between runs
        SessionFactory sf = getCurrentSession().getSessionFactory();
        Map<?, EntityPersister> classMetadata = sf.getAllClassMetadata();
        for (EntityPersister ep : classMetadata.values()) {
            if (ep.hasCache()) {
                sf.evictEntity(ep.getCache().getRegionName());
            }
        }

        Map<?, AbstractCollectionPersister> collMetadata = sf
                .getAllCollectionMetadata();
        for (AbstractCollectionPersister acp : collMetadata.values()) {
            if (acp.hasCache()) {
                sf.evictCollection(acp.getCache().getRegionName());
            }
        }
    }

    /**
     * Handles committing or rolling back the transaction after the test is
     * complete.
     */
    @After
    public final void tearDown() {
        clearUser();
        try {
            transaction.commit();
        } catch (Exception e) {
            PaHibernateUtil.getHibernateHelper().rollbackTransaction(
                    transaction);
        }
    }

    @Before
    final public void initDb() throws HibernateException, SQLException,
            IOException {

        Transaction tx = PaHibernateUtil.getHibernateHelper()
                .beginTransaction();
        
        dropViewsTriggersSequences();
        SchemaExport se = new SchemaExport(PaHibernateUtil.getHibernateHelper()
                .getConfiguration());
        se.drop(false, true);
        se.create(false, true);        
        createViewsTriggersSequences();

        TestSchema.loadPrimaryPurposeCodes();
        makeHsqlDbSpecificAdjustments();
        tx.commit();
        transaction = PaHibernateUtil.getHibernateHelper().beginTransaction();
    }

    protected void dropViewsTriggersSequences() throws HibernateException, IOException {
        final String sqlFileName = "/drop_db_objects.sql";
        runSQLFile(sqlFileName);

    }

    protected void createViewsTriggersSequences() throws IOException,
            HibernateException, SQLException {
        final String sqlFileName = "/create_db_objects.sql";
        runSQLFile(sqlFileName);
    }

    /**
     * @param sqlFileName
     * @throws IOException
     * @throws HibernateException
     */
    private void runSQLFile(final String sqlFileName) throws IOException,
            HibernateException {
        String sqlBatch = IOUtils.toString(getClass().getResourceAsStream(
                sqlFileName));
        for (String sql : sqlBatch.split(";")) {
            try (Statement s = PaHibernateUtil.getCurrentSession().connection()
                    .createStatement()) {
                s.execute(sql.trim());
            } catch (SQLException e) {
                //System.out.println("Failed to run: " + sql.trim()
                //        + ". Reason: " + e.getMessage());
            }
        }
        PaHibernateUtil.getCurrentSession().flush();
    }

    private void makeHsqlDbSpecificAdjustments() throws HibernateException,
            SQLException {
        CommonsConstant.SELECT_NEXTVAL_NCI_IDENTIFIERS_SEQ = "call next value for nci_identifiers_seq";

    }

    protected Session getCurrentSession() {
        return PaHibernateUtil.getHibernateHelper().getCurrentSession();
    }

    public void clearUser() {
        TestSchema.clearUser();
    }

    static {
        new Timer(true).schedule(new TimerTask() {
            @SuppressWarnings("rawtypes")
            @Override
            public void run() {
                Map allThreads = Thread.getAllStackTraces();
                Iterator iterator = allThreads.keySet().iterator();
                StringBuffer stringBuffer = new StringBuffer();
                while (iterator.hasNext()) {
                    Thread key = (Thread) iterator.next();
                    StackTraceElement[] trace = (StackTraceElement[]) allThreads
                            .get(key);
                    stringBuffer.append(key + "\r\n");
                    for (int i = 0; i < trace.length; i++) {
                        stringBuffer.append(" " + trace[i] + "\r\n");
                    }
                    stringBuffer.append("\r\n");
                }
                System.out.println(stringBuffer);
            }
        }, 60000, 60000);
    }

}
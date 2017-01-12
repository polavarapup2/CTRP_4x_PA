/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
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
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
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
package gov.nih.nci.pa.service.audittrail;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.mapping.PersistentClass;

import com.fiveamsolutions.nci.commons.audit.AuditLogDetail;
import com.fiveamsolutions.nci.commons.audit.AuditLogRecord;
import com.fiveamsolutions.nci.commons.audit.Auditable;

/**
 * Implementation of the audit history service.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AuditTrailServiceBean implements AuditTrailServiceLocal {
    private static final String UNCHECKED = "unchecked";
    private static final List<String> EXCLUDED_FIELDS = Arrays.asList("dateLastUpdated", "dateLastCreated",
            "studyProtocol");

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    public <T extends Auditable> List<AuditLogDetail> getAuditTrail(Class<T> clazz, Ii identifier, Date startDate,
            Date endDate) {
        StringBuffer hql = new StringBuffer();
        hql.append("from ").append(AuditLogDetail.class.getName())
            .append(" ald where ald.record.entityName = :entityName and ald.record.entityId = :entityId"
                    + " and ald.attribute not in (:excludedAttributes)");
        hql.append(generateDateClause(startDate, endDate));
        Query query = PaHibernateUtil.getCurrentSession().createQuery(hql.toString());
        query.setParameter("entityName", getEntityName(clazz));
        query.setParameter("entityId", IiConverter.convertToLong(identifier));
        query.setParameterList("excludedAttributes", EXCLUDED_FIELDS);
        populateDateParameters(startDate, endDate, query);
        return query.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Auditable> List<AuditLogRecord> getAuditTrail(
            Class<T> clazz, Ii identifier) {
        StringBuffer hql = new StringBuffer();
        hql.append("select distinct record from ")
                .append(AuditLogRecord.class.getName())
                .append(" as record inner join fetch record.details where record.entityName = :entityName "
                        + "and record.entityId = :entityId"
                        + " and record.details.attribute not in (:excludedAttributes) order by record.createdDate");
        Query query = PaHibernateUtil.getCurrentSession().createQuery(
                hql.toString());
        query.setParameter("entityName", getEntityName(clazz));
        query.setParameter("entityId", IiConverter.convertToLong(identifier));
        query.setParameterList("excludedAttributes", EXCLUDED_FIELDS);
        return query.list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    public <T extends Auditable> List<AuditLogDetail> getAuditTrailByFields(Class<T> clazz, Ii identifier,
            Date startDate, Date endDate, String... fieldNames) {
        StringBuffer hql = new StringBuffer();
        hql.append("from ").append(AuditLogDetail.class.getName())
            .append(" ald where ald.record.entityName = :entityName and ald.record.entityId = :entityId and "
                    + " ald.attribute in (:attributes) and ald.attribute not in (:excludedAttributes)");
        hql.append(generateDateClause(startDate, endDate));

        Query query = PaHibernateUtil.getCurrentSession().createQuery(hql.toString());
        query.setParameter("entityName", getEntityName(clazz));
        query.setParameter("entityId", IiConverter.convertToLong(identifier));
        query.setParameterList("attributes", fieldNames);
        query.setParameterList("excludedAttributes", EXCLUDED_FIELDS);
        populateDateParameters(startDate, endDate, query);
        return query.list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(UNCHECKED)
    public <T> List<AuditLogDetail> getAuditTrailByStudyProtocol(Class<T> clazz, Ii studyProtocolIi, Date startDate,
            Date endDate) {
        Collection<Long> entityIds = getRelatedIdentifiers(clazz, studyProtocolIi);
        List<AuditLogDetail> results = new ArrayList<AuditLogDetail>();
        if (CollectionUtils.isNotEmpty(entityIds)) {
            StringBuffer hql = new StringBuffer();
            hql.append("from ").append(AuditLogDetail.class.getName())
                .append(" ald where ald.record.entityName = :entityName and ald.record.entityId in (:entityIds)"
                        + " and ald.attribute not in (:excludedAttributes)");
            hql.append(generateDateClause(startDate, endDate));
            Query query = PaHibernateUtil.getCurrentSession().createQuery(hql.toString());
            query.setParameter("entityName", getEntityName(clazz));
            query.setParameterList("entityIds", entityIds);
            query.setParameterList("excludedAttributes", EXCLUDED_FIELDS);
            populateDateParameters(startDate, endDate, query);
            results.addAll(query.list());
        }
        return results;
    }

    private <T> String getEntityName(Class<T> clazz) {
        PersistentClass pc = PaHibernateUtil.getHibernateHelper().getConfiguration().getClassMapping(clazz.getName());
        return pc.getTable().getName();
    }

    @SuppressWarnings(UNCHECKED)
    private <T> Collection<Long> getRelatedIdentifiers(Class<T> clazz, Ii studyProtocolIi) {
        String hql = "select id from " + clazz.getName() + " where studyProtocol.id = :studyProtocolId";
        Query query = PaHibernateUtil.getCurrentSession().createQuery(hql);
        query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
        return query.list();
    }

    /**
     * Generates the needed date clause for queries.
     * @param startDate the start date
     * @param endDate the end date
     * @return the clause appropriate for the given dates
     */
    private String generateDateClause(Date startDate, Date endDate) {
        StringBuffer clause = new StringBuffer();
        if (startDate != null) {
            clause.append(" and ald.record.createdDate >= :startDate");
        }
        if (endDate != null) {
            clause.append(" and ald.record.createdDate < :endDate");
        }
        return clause.toString();
    }

    private void populateDateParameters(Date startDate, Date endDate, Query query) {
        if (startDate != null) {
            query.setParameter("startDate", startDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", DateUtils.addDays(endDate, 1));
        }
    }

    
}

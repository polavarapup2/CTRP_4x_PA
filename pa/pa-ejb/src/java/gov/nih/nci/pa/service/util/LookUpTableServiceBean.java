/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.AbstractLookUpEntity;
import gov.nih.nci.pa.domain.Account;
import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.FundingMechanism;
import gov.nih.nci.pa.domain.NIHinstitute;
import gov.nih.nci.pa.domain.PAProperties;
import gov.nih.nci.pa.enums.ExternalSystemCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.CacheUtils;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.CacheUtils.Closure;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
* Bean implementation for providing access to look up tables.
*
* @author Naveen Amiruddin
*/
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SuppressWarnings("unchecked")
public class LookUpTableServiceBean implements LookUpTableServiceRemote {

    private static final String ALL_FUNDING_MECHANISMS_QUERY = "select fm from FundingMechanism fm "
            + "order by fundingMechanismCode";
    private static final String ALL_NIH_INSTITUES_QUERY = "select nih from NIHinstitute nih order by nihInstituteCode";
    private static final String ALL_COUNTRIES_QUERY = "select c from Country c order by name";
    private static final String COUNTRY_BY_NAME_QUERY = "select c from Country c where c.name = :name";
    private static final String PAPROPERTY_BY_NAME_QUERY = "select p from PAProperties p where p.name = :name";
    private static final String ALL_ANATOMIC_SITES_QUERY = "select items from AnatomicSite items order by code";
    private static final String LOOKUP_BY_CODE_QUERY = "select item from {0} item where item.code = :code";
    private static final String STUDY_ALTERNATE_TITLE_TYPES = "studyAlternateTitleTypes";
    
    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<FundingMechanism> getFundingMechanisms() throws PAException {
        return executeQuery(ALL_FUNDING_MECHANISMS_QUERY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<NIHinstitute> getNihInstitutes() throws PAException {
        return executeQuery(ALL_NIH_INSTITUES_QUERY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Country> getCountries() throws PAException {
        return executeQuery(ALL_COUNTRIES_QUERY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Country getCountryByName(String name) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery(COUNTRY_BY_NAME_QUERY);
        query.setParameter("name", name);
        return (Country) query.uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String getPropertyValueFromCache(final String name) throws PAException {
        return (String) CacheUtils.getFromCacheOrBackend(CacheUtils.getStatusRulesCache(),
                name,
                new Closure() {
                 
                 @Override
                 public Object execute() throws PAException {
                     Session session = PaHibernateUtil.getCurrentSession();
                     Query query = session.createQuery(PAPROPERTY_BY_NAME_QUERY);
                     query.setString("name", name);
                     List<PAProperties> paProperties = query.list();
                     if (CollectionUtils.isEmpty(paProperties)) {
                         throw new PAException("PA_PROPERTIES does not have entry for  " + name);
                     }
                     return paProperties.get(0).getValue();
                 }
             });
        
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String getPropertyValue(String name) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery(PAPROPERTY_BY_NAME_QUERY);
        query.setString("name", name);
        List<PAProperties> paProperties = query.list();
        if (CollectionUtils.isEmpty(paProperties)) {
            throw new PAException("PA_PROPERTIES does not have entry for  " + name);
        }
        return paProperties.get(0).getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Country> searchCountry(Country country) throws PAException {
        if (country == null) {
            throw new PAException("country cannot be null");
        }
        Session session = PaHibernateUtil.getCurrentSession();
        Criteria criteria = session.createCriteria(Country.class, "country");
        if (StringUtils.isNotEmpty(country.getName())) {
            criteria.add(Restrictions.eq("country.name", country.getName()));
        }
        if (StringUtils.isNotEmpty(country.getAlpha2())) {
            criteria.add(Restrictions.eq("country.alpha2", country.getAlpha2()));
        }
        if (StringUtils.isNotEmpty(country.getAlpha3())) {
            criteria.add(Restrictions.eq("country.alpha3", country.getAlpha3()));
        }
        return criteria.list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AnatomicSite> getAnatomicSites() throws PAException {
        return executeQuery(ALL_ANATOMIC_SITES_QUERY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public <T extends AbstractLookUpEntity> T getLookupEntityByCode(Class<T> clazz, String code) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery(MessageFormat.format(LOOKUP_BY_CODE_QUERY, clazz.getName()));
        query.setString("code", code);
        return (T) query.uniqueResult();
    }

    /**
     * Execute the given hql query (without parameter).
     * @param hql The hql query
     * @return The result of the query
     */
    private <T> List<T> executeQuery(String hql) {
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery(hql);
        return query.list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getStudyAlternateTitleTypes() throws PAException {
        String studyAlternateTitleTypes = getPropertyValue(STUDY_ALTERNATE_TITLE_TYPES);
        return Arrays.asList(studyAlternateTitleTypes.split(","));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Account getJasperCredentialsAccount()
            throws PAException {
        return (Account) PaHibernateUtil
                .getCurrentSession()
                .createQuery(
                        "from Account where accountName=:name and externalSystem=:system")
                .setMaxResults(1)
                .setString("name", "jasper.token")
                .setParameter("system", ExternalSystemCode.JASPER)
                .uniqueResult();
    }
}

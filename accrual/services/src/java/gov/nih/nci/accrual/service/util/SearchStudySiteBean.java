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
package gov.nih.nci.accrual.service.util;

import gov.nih.nci.accrual.dto.util.SearchStudySiteResultDto;
import gov.nih.nci.accrual.service.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
/**
 * @author Hugh Reinhart
 * @since Aug 17, 2009
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class SearchStudySiteBean implements SearchStudySiteService {

    private static final String FUNCTIONAL_CODE = "functionalCode";
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<SearchStudySiteResultDto> search(Ii studyProtocolIi, Ii registryUserIi) throws PAException {
        List<SearchStudySiteResultDto> result = new ArrayList<SearchStudySiteResultDto>();
        if (!ISOUtil.isIiNull(studyProtocolIi) && !ISOUtil.isIiNull(registryUserIi)) {
            try {
                Session session = PaHibernateUtil.getCurrentSession();
                String hql = "select ss.id, org.name, org.identifier "
                    + "from StudyProtocol as sp join sp.studySites as ss "
                    + "left outer join ss.healthCareFacility as ro "
                    + "left outer join ro.organization as org "
                    + "where sp.id = :spId and ss.functionalCode = :functionalCode";
                Query query = session.createQuery(hql);
                query.setParameter("spId", IiConverter.convertToLong(studyProtocolIi));
                query.setParameter(FUNCTIONAL_CODE, StudySiteFunctionalCode.TREATING_SITE);
                List<Object> queryList = query.list();
                Set<Long> authIds = getAuthorizedSites(registryUserIi);
                Set<String> siteAndFamilySubmitterSites = getSiteAndFamilySubmitterSites(registryUserIi);
              
                for (Object qArr : queryList) {
                    Object[] site = (Object[]) qArr;
                    if (authIds.contains(site[0]) || siteAndFamilySubmitterSites.contains(site[2])) {
                        SearchStudySiteResultDto dto = new SearchStudySiteResultDto();
                        dto.setStudySiteIi(IiConverter.convertToIi((Long) site[0]));
                        dto.setOrganizationName(StConverter.convertToSt((String) site[1]));
                        dto.setOrganizationIi(IiConverter.convertToIi((String) site[2]));
                        result.add(dto);
                    }
                }
            } catch (HibernateException hbe) {
                throw new PAException("Hibernate exception in SearchStudySiteBean.getTrialSummaryByStudyProtocolIi().",
                        hbe);
            }
        }
        return result;
    }

    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SearchStudySiteResultDto> getTreatingSites(Long studyProtocolId) throws PAException {
        List<SearchStudySiteResultDto> result = new ArrayList<SearchStudySiteResultDto>();
        StudySiteAccrualStatusServiceRemote accrualStatusSvc = PaServiceLocator.getInstance()
                .getStudySiteAccrualStatusService();
        Session session = PaHibernateUtil.getCurrentSession();
        String hql = "select ss.id, org.name, org.identifier "
                + "from StudyProtocol as sp join sp.studySites as ss "
                + "left outer join ss.healthCareFacility as ro "
                + "left outer join ro.organization as org "
                + "where sp.id = :spId and ss.functionalCode = :functionalCode";
        Query query = session.createQuery(hql);
        query.setParameter("spId", studyProtocolId);
        query.setParameter(FUNCTIONAL_CODE, StudySiteFunctionalCode.TREATING_SITE);
        List<Object[]> queryList = query.list();
        for (Object[] qArr : queryList) {
            Object[] site = qArr;
            StudySiteAccrualStatusDTO ssas = accrualStatusSvc.getCurrentStudySiteAccrualStatusByStudySite(
                    IiConverter.convertToIi((Long) site[0]));
            if (ssas != null 
                    && RecruitmentStatusCode.getByCode(ssas.getStatusCode().getCode()).isEligibleForAccrual()) {
                SearchStudySiteResultDto dto = new SearchStudySiteResultDto();
                dto.setStudySiteIi(IiConverter.convertToIi((Long) site[0]));
                dto.setOrganizationName(StConverter.convertToSt((String) site[1]));
                dto.setOrganizationIi(IiConverter.convertToIi((String) site[2]));
                result.add(dto);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public SearchStudySiteResultDto getStudySiteByOrg(Ii studyProtocolIi, Ii orgIi)  throws PAException {
        Criteria criteria = PaHibernateUtil.getCurrentSession().createCriteria(StudySite.class);
        criteria.add(Restrictions.eq("studyProtocol.id", IiConverter.convertToLong(studyProtocolIi)));
        criteria.add(Restrictions.eq(FUNCTIONAL_CODE, StudySiteFunctionalCode.TREATING_SITE));
        criteria.createCriteria("healthCareFacility").createCriteria("organization")
            .add(Restrictions.eq("identifier", orgIi.getExtension()));
        SearchStudySiteResultDto returnDto = null;
        try {
            StudySite ss = (StudySite) criteria.uniqueResult();
            if (ss != null) {
                returnDto = new SearchStudySiteResultDto();
                returnDto.setOrganizationIi(
                        IiConverter.convertToIi(ss.getHealthCareFacility().getOrganization().getIdentifier()));
                returnDto.setStudySiteIi(IiConverter.convertToIi(ss.getId()));
                returnDto.setOrganizationName(
                        StConverter.convertToSt(ss.getHealthCareFacility().getOrganization().getName()));
            }
        } catch (HibernateException e) {
            throw new PAException("Error retrieving study site ii for the organization " + orgIi.getExtension(), e);
        }
        return returnDto;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isStudyHasDCPId(Ii studyProtocolIi)  throws PAException {
        Criteria criteria = PaHibernateUtil.getCurrentSession().createCriteria(StudySite.class);
        criteria.add(Restrictions.eq("studyProtocol.id", IiConverter.convertToLong(studyProtocolIi)));
        criteria.add(Restrictions.eq(FUNCTIONAL_CODE, StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
        criteria.createCriteria("researchOrganization").createCriteria("organization")
            .add(Restrictions.eq("name", PAConstants.DCP_ORG_NAME));
        try {
            StudySite ss = (StudySite) criteria.uniqueResult();
            if (ss != null) {
                return true;
            }
        } catch (HibernateException e) {
            throw new PAException("Error while checking if a study site has DCP Id ", e);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isStudyHasCTEPId(Ii studyProtocolIi)  throws PAException {
        Criteria criteria = PaHibernateUtil.getCurrentSession().createCriteria(StudySite.class);
        criteria.add(Restrictions.eq("studyProtocol.id", IiConverter.convertToLong(studyProtocolIi)));
        criteria.add(Restrictions.eq(FUNCTIONAL_CODE, StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
        criteria.createCriteria("researchOrganization").createCriteria("organization")
            .add(Restrictions.eq("name", PAConstants.CTEP_ORG_NAME));
        try {
            StudySite ss = (StudySite) criteria.uniqueResult();
            if (ss != null) {
                return true;
            }
        } catch (HibernateException e) {
            throw new PAException("Error while checking if a study site has CTEP Id ", e);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private Set<Long> getAuthorizedSites(Ii registryUserIi) {
        Set<Long> result = new HashSet<Long>();
        Long userId = IiConverter.convertToLong(registryUserIi);
        if (userId != null) {
            Session session = PaHibernateUtil.getCurrentSession();
            String hql = "select distinct ss.id from StudySiteAccrualAccess ssaa "
                + " join ssaa.studySite ss where ssaa.registryUser.id = :userId and ssaa.statusCode = :statusCode";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.setParameter("statusCode", ActiveInactiveCode.ACTIVE);
            List<Long> queryList = query.list();
            result.addAll(queryList);
        }
        return result;
    }
    
    private Set<String> getSiteAndFamilySubmitterSites(Ii registryUserIi) throws PAException {
        List<Long> result = new ArrayList<Long>();
        Long userId = IiConverter.convertToLong(registryUserIi);
        RegistryUser ru = PaServiceLocator.getInstance().getRegistryUserService()
                .getUserById(userId);
        if (ru.getSiteAccrualSubmitter()) {
            result.add(ru.getAffiliatedOrganizationId());
        } 
        if (ru.getFamilyAccrualSubmitter()) {
            result.addAll(new AccrualUtil().getAllFamilyOrgs(ru.getAffiliatedOrganizationId()));
        }
        return AccrualUtil.convertPoOrgIdsToStrings(result);
    }
    
} 

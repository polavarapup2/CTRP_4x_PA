/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The accrual
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This accrual Software License (the License) is between NCI and You. You (or 
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
 * its rights in the accrual Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the accrual Software; (ii) distribute and 
 * have distributed to and by third parties the accrual Software and any 
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
package gov.nih.nci.accrual.service.util;

import gov.nih.nci.accrual.dto.util.SearchTrialResultDto;
import gov.nih.nci.accrual.service.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteSubjectAccrualCount;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * @author moweis
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class SubjectAccrualCountBean implements SubjectAccrualCountService {

    @EJB
    private SearchTrialService searchTrialService;

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<StudySiteSubjectAccrualCount> getCounts(Ii studyProtocolIi) throws PAException {
        Long studyProtocolId = IiConverter.convertToLong(studyProtocolIi);
        RegistryUser user = PaServiceLocator.getInstance().getRegistryUserService().getUser(
                CaseSensitiveUsernameHolder.getUser());
        try {
            String hql = "select ss from StudySite ss join ss.studySiteAccrualAccess ssas " 
                + "where ss.studyProtocol.id = :studyProtocolId " 
                + "and ss.functionalCode = :functionalCode "
                + "and ssas.registryUser.id = :registerUserId "
                + "and ssas.statusCode =:statusCode";
            Query query = PaHibernateUtil.getCurrentSession().createQuery(hql);
            query.setParameter("studyProtocolId", studyProtocolId);
            query.setParameter("functionalCode", StudySiteFunctionalCode.TREATING_SITE);
            query.setParameter("registerUserId", user.getId());
            query.setParameter("statusCode", ActiveInactiveCode.ACTIVE);

            List<StudySite> queryResults = query.list(); 
            
            List<StudySite> ssList = getSiteAndFamilySubmittersCount(studyProtocolIi, user);
            for (StudySite site : ssList) {
                if (!queryResults.contains(site)) {
                   queryResults.add(site);
                 }
            }
            
            return getCountsForSites(queryResults);
            
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in getByStudyProtocol().", hbe);
        }
    }

    private List<StudySiteSubjectAccrualCount> getCountsForSites(List<StudySite> studySites) {
        List<StudySiteSubjectAccrualCount> resultList = new ArrayList<StudySiteSubjectAccrualCount>();
        for (StudySite site : studySites) {
            StudySiteSubjectAccrualCount accrualCount = site.getAccrualCount(); 
            if (accrualCount == null) {
                accrualCount = new StudySiteSubjectAccrualCount();                
                accrualCount.setStudySite(site);
                accrualCount.setStudyProtocol(site.getStudyProtocol());
            }
            resultList.add(accrualCount);
        }
        return resultList;
    }
    
    private List<StudySite> getSiteAndFamilySubmittersCount(Ii studyProtocolIi, RegistryUser user)
       throws PAException {
         Long studyProtocolId = IiConverter.convertToLong(studyProtocolIi);
         List<StudySite> finalSiteList = new ArrayList<StudySite>();
         List<StudySite> familyList = new ArrayList<StudySite>();
         String hql = "select ss from StudySite ss join ss.studySiteAccrualStatuses ssas " 
                 + " join ss.healthCareFacility hcf join hcf.organization org "
                 + " where ss.studyProtocol.id = :studyProtocolId " 
                 + " and ss.functionalCode = :functionalCode "
                 + " and org.identifier IN  (:orgIDS)"
                 + " and ssas.statusCode <> :statusCode"
                 + " and ssas.id = (select max(id) "
                 + "    from StudySiteAccrualStatus where studySite.id = ss.id"
                 + "    and deleted=false"
                 + "    and statusDate = (select max(statusDate) "
                 + "        from StudySiteAccrualStatus where studySite.id =ss.id"
                 + "        and deleted=false)) ";
         
             Query query = PaHibernateUtil.getCurrentSession().createQuery(hql);
             query.setParameter("studyProtocolId", studyProtocolId);
             query.setParameter("functionalCode", StudySiteFunctionalCode.TREATING_SITE);
             query.setParameter("statusCode", RecruitmentStatusCode.IN_REVIEW);
         if (user.getSiteAccrualSubmitter()) {
            List<Long> values = new ArrayList<Long>();
            values.add(user.getAffiliatedOrganizationId());
            query.setParameterList("orgIDS", AccrualUtil.convertPoOrgIdsToStrings(values));
            List<StudySite> queryResults = query.list(); 
            finalSiteList.addAll(queryResults);
         } 
         if (user.getFamilyAccrualSubmitter()) {
             List<Long> values = new AccrualUtil().getAllFamilyOrgs(user.getAffiliatedOrganizationId());
             query.setParameterList("orgIDS", AccrualUtil.convertPoOrgIdsToStrings(values));
             familyList = query.list(); 
         }
         for (StudySite site : familyList) {
             if (!finalSiteList.contains(site)) {
                 finalSiteList.add(site);
              }
         }
         return finalSiteList;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteSubjectAccrualCount getCountByStudySiteId(Ii studySiteIi) {    
            return (StudySiteSubjectAccrualCount) PaHibernateUtil.getCurrentSession()
                .createCriteria(StudySiteSubjectAccrualCount.class)
                .add(Restrictions.eq("studySite.id", IiConverter.convertToLong(studySiteIi)))
                .uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(List<StudySiteSubjectAccrualCount> counts) throws PAException {
        assertAccrualAccess(counts);
        for (StudySiteSubjectAccrualCount count : counts) {
            if (count.getAccrualCount() != null) {
                saveAccrualCount(count);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Ii siteIi, Ii studyProtocolIi) throws PAException {
        if (ISOUtil.isIiNull(siteIi)) {
            throw new PAException("StudySite Ii must be valid.");
        }
        if (ISOUtil.isIiNull(studyProtocolIi)) {
            throw new PAException("StudyProtocol Ii must be valid.");
        }
        StudySiteSubjectAccrualCount accrualCount = (StudySiteSubjectAccrualCount) 
                PaHibernateUtil.getCurrentSession().createCriteria(StudySiteSubjectAccrualCount.class)
                .add(Restrictions.eq("studySite.id", IiConverter.convertToLong(siteIi)))
                .add(Restrictions.eq("studyProtocol.id", IiConverter.convertToLong(studyProtocolIi)))
                .uniqueResult();
        if (accrualCount == null) {
            throw new PAException("Counts for the StudySite id " + siteIi.getExtension() + " does not exist.");
        }
        boolean isSiteFamilySubmitter = false;
        if (accrualCount.getStudySite().getHealthCareFacility() != null 
            && new AccrualUtil().isUserAllowedSiteOrFamilyAccrualAccess(accrualCount.getStudySite()
                .getHealthCareFacility().getOrganization().getIdentifier())) {
                isSiteFamilySubmitter = true;
        }
        if (!AccrualUtil.isUserAllowedAccrualAccess(IiConverter
                    .convertToStudySiteIi(accrualCount.getStudySite().getId())) 
                    && !isSiteFamilySubmitter) {
            throw new PAException("User does not have accrual access to site.");
        }
        PaHibernateUtil.getCurrentSession().delete(accrualCount);
    }

    private void saveAccrualCount(StudySiteSubjectAccrualCount newCount) throws PAException {
        StudySiteSubjectAccrualCount countToSave = newCount;
        if (newCount.getId() != null) {
            countToSave = (StudySiteSubjectAccrualCount) PaHibernateUtil.getCurrentSession()
                    .load(StudySiteSubjectAccrualCount.class, newCount.getId());
            countToSave.setAccrualCount(newCount.getAccrualCount());
            countToSave.setSubmissionTypeCode(newCount.getSubmissionTypeCode());
        }
        countToSave.setDateLastUpdated(new Date());
        countToSave.setUserLastUpdated(AccrualCsmUtil.getInstance().getCSMUser(CaseSensitiveUsernameHolder.getUser()));
        PaHibernateUtil.getCurrentSession().saveOrUpdate(countToSave);
    }

    private void assertAccrualAccess(List<StudySiteSubjectAccrualCount> counts) throws PAException {
        for (StudySiteSubjectAccrualCount count : counts) {
            if (count.getAccrualCount() != null) {
                assertIndustrialAccrualAccess(count.getStudySite());
            }
        }
    }
    @SuppressWarnings({ "PMD.NPathComplexity" })
    private void assertIndustrialAccrualAccess(StudySite site) throws PAException {
        SearchTrialResultDto trialSummary = searchTrialService.getTrialSummaryByStudyProtocolIi(IiConverter
                .convertToStudyProtocolIi(site.getStudyProtocol().getId()));
        if (!BlConverter.convertToBool(trialSummary.getIndustrial())
                && StConverter.convertToString(trialSummary.getTrialType()).equals(AccrualUtil.INTERVENTIONAL)) {
            throw new PAException("Action can not be performed as the participating site (" + site.getId() 
                    + ") does not belong to an Industrial trial.");
        }
        boolean isSiteFamilySubmitter = false;
        if (site.getHealthCareFacility() != null 
            && new AccrualUtil().isUserAllowedSiteOrFamilyAccrualAccess(site
                .getHealthCareFacility().getOrganization().getIdentifier())) {
                isSiteFamilySubmitter = true;
        }
        
        if (!AccrualUtil.isUserAllowedAccrualAccess(IiConverter.convertToStudySiteIi(site.getId()))
             &&  !isSiteFamilySubmitter) {
            User user = AccrualCsmUtil.getInstance().getCSMUser(CaseSensitiveUsernameHolder.getUser());
            Organization org = getOrganizationByStudySiteId(site.getId());
            throw new PAException("User " + user.getFirstName() + " " +  user.getLastName() 
                    + " (User ID " + AccrualCsmUtil.getInstance().getGridIdentityUsername(user.getLoginName()) 
                    + ") does not have accrual access to site: " 
                    + (org != null ? org.getName() : null) + " (PO ID = "
                    + (org != null ? org.getIdentifier() : null) + ")");
        }
    }

    private Organization getOrganizationByStudySiteId(Long ssid) {
        Session session = PaHibernateUtil.getCurrentSession();
        StudySite ss = (StudySite) session.get(StudySite.class, ssid);
        if (ss.getHealthCareFacility() != null) {
            return ss.getHealthCareFacility().getOrganization();
        }
        return null;
    }
    /**
     * @param searchTrialService the searchTrialService to set
     */
    public void setSearchTrialService(SearchTrialService searchTrialService) {
        this.searchTrialService = searchTrialService;
    }
}

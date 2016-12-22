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
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.PDQDisease;
import gov.nih.nci.pa.domain.PDQDiseaseAltername;
import gov.nih.nci.pa.domain.PDQDiseaseParent;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.convert.PDQDiseaseConverter;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.noniso.dto.PDQDiseaseNode;
import gov.nih.nci.pa.service.search.PDQDiseaseBeanSearchCriteria;
import gov.nih.nci.pa.service.search.PDQDiseaseSortCriterion;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

/**
 * @author asharma
 */
@Stateless
@Interceptors({ RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class PDQDiseaseBeanLocal extends AbstractBaseIsoService<PDQDiseaseDTO, PDQDisease, PDQDiseaseConverter>
        implements PDQDiseaseServiceLocal {

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PDQDiseaseDTO> search(PDQDiseaseDTO searchCriteria) throws PAException {
        checkSearchCriteria(searchCriteria);

        boolean includeSynonyms = BooleanUtils
                .toBoolean(StConverter.convertToString(searchCriteria.getIncludeSynonym()));
        boolean exactMatch = BooleanUtils.toBoolean(StConverter.convertToString(searchCriteria.getExactMatch()));
        String preferredName = StConverter.convertToString(searchCriteria.getPreferredName());
        String ntTermIdentifier = StConverter.convertToString(searchCriteria.getNtTermIdentifier());

        PDQDisease criteria = new PDQDisease();
        if (includeSynonyms) {
            criteria.setDiseaseAlternames(new ArrayList<PDQDiseaseAltername>());
            PDQDiseaseAltername alt = new PDQDiseaseAltername();
            alt.setStatusCode(ActiveInactiveCode.ACTIVE);
            criteria.getDiseaseAlternames().add(alt);
        }

        criteria.setStatusCode(ActiveInactivePendingCode.ACTIVE);
        if (StringUtils.isNotEmpty(ntTermIdentifier)) {
            criteria.setNtTermIdentifier(ntTermIdentifier);
        }

        if (StringUtils.isNotEmpty(preferredName)) {
            if (exactMatch) {
                preferredName = stringToSearch(preferredName);
            } else {
                preferredName = PAUtil.wildcardCriteria(preferredName);
            }
        }

        PageSortParams<PDQDisease> params = new PageSortParams<PDQDisease>(PAConstants.MAX_SEARCH_RESULTS, 0,
                PDQDiseaseSortCriterion.DISEASE_PREFERRED_NAME, false);
        PDQDiseaseBeanSearchCriteria<PDQDisease> crit;
        if (StringUtils.isNotEmpty(preferredName)) {
            crit = new PDQDiseaseBeanSearchCriteria<PDQDisease>(criteria, includeSynonyms, exactMatch, preferredName);
        } else {
            crit = new PDQDiseaseBeanSearchCriteria<PDQDisease>(criteria, ntTermIdentifier);
        }
        List<PDQDisease> results = search(crit, params);
        if (results.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new PAException("Too many diseases found.  Please narrow search.");
        }

        return convertFromDomainToDTOs(results);
    }

    private void checkSearchCriteria(PDQDiseaseDTO searchCriteria) throws PAException {
        if (searchCriteria == null) {
            throw new PAException("Must pass in search criteria when calling search().");
        }
        if (searchCriteria.getPreferredName() == null && searchCriteria.getNtTermIdentifier() == null) {
            throw new PAException("Must pass in a name or NCIt Identifier when calling search().");
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PDQDisease> getByIds(List<Long> ids) {
        Session session = PaHibernateUtil.getCurrentSession();
        String hql = "select pd from PDQDisease pd where id in (:ids) order by id ";
        Query query = session.createQuery(hql);
        query.setParameterList("ids", ids);
        return query.list();
    }

    /**
     * String to search.
     *
     * @param searchTerm
     *            the search term
     *
     * @return the string
     */
    private String stringToSearch(String searchTerm) {
        String term = "";
        // checks if wildcard is present within the string not at extremities
        Pattern pat = Pattern.compile("^[^*].*\\**.*[^*]$");
        Matcher mat = pat.matcher(searchTerm);

        if (!searchTerm.contains("*")) {
            term = searchTerm;
        }
        if (mat.find()) {
            term = PAUtil.wildcardCriteria(searchTerm);
        } else {
            term = searchTerm;
        }

        return term;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PDQDiseaseNode> getDiseaseTree() {
        List<PDQDiseaseNode> tree = new ArrayList<PDQDiseaseNode>();
        Session session = PaHibernateUtil.getCurrentSession();
        String hql = "from PDQDisease order by preferredName";
        Query query = session.createQuery(hql);
        for (PDQDisease disease : (List<PDQDisease>) query.list()) {
            if (CollectionUtils.isEmpty(disease.getDiseaseParents())) {
                tree.add(getNode(disease, null, !CollectionUtils.isEmpty(disease.getDiseaseChildren())));
            } else {
                // Get unique ACTIVE parent ids, there could be duplicates in
                // the DB
                HashSet<Long> parentIds = new HashSet<Long>();
                for (PDQDiseaseParent parent : disease.getDiseaseParents()) {
                    if (parent.getStatusCode() == ActiveInactiveCode.ACTIVE) {
                        parentIds.add(parent.getParentDisease().getId());
                    }
                }

                for (Long parentId : parentIds) {
                    tree.add(getNode(disease, parentId, !CollectionUtils.isEmpty(disease.getDiseaseChildren())));
                }
            }
        }
        return tree;
    }

    private PDQDiseaseNode getNode(PDQDisease disease, Long parentId, Boolean hasChildren) {
        PDQDiseaseNode node = new PDQDiseaseNode();
        node.setId(disease.getId());
        node.setName(disease.getPreferredName());
        node.setParentId(parentId);
        node.setHasChildren(hasChildren);
        node.setNcitId(disease.getNtTermIdentifier());
        // Set alternate names
        List<PDQDiseaseAltername> alts = disease.getDiseaseAlternames();
        if (alts != null && !alts.isEmpty()) {
          
            for (int i = 0; i < alts.size(); i++) {
                node.getAlterNames().add(alts.get(i).getAlternateName());
            }
        }
        return node;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllDisplayNames() {
        Session session = PaHibernateUtil.getCurrentSession();
        String hql = "select distinct pd.displayName from PDQDisease pd order by pd.displayName";
        Query query = session.createQuery(hql);
        List<String> result = new ArrayList<String>();
        for (Iterator iterator = query.list().iterator(); iterator.hasNext();) {
            String dn = (String) iterator.next();
            if (dn != null && !dn.isEmpty()) {
                StringBuilder builder = new StringBuilder();
                builder.append("\"").append(dn.replace("\"", "\\\"")).append("\"");
                result.add(builder.toString());
            }
        }
        return result;
    }

    /**
     * Search diseases and return weighted results 
     * @param searchCriteria searchCriteria
     * @return matched diseases
     */
    public List<PDQDiseaseNode> weightedSearchDisease(PDQDiseaseDTO searchCriteria) {
        List<PDQDiseaseNode> result = new ArrayList<PDQDiseaseNode>();
        Session session = PaHibernateUtil.getCurrentSession();
        Query query;

        boolean searchSyn = Boolean.valueOf(searchCriteria.getIncludeSynonym().getValue());
        boolean exactSearch = Boolean.valueOf(searchCriteria.getExactMatch().getValue());
        String searchString = searchCriteria.getPreferredName().getValue();
        if (!searchSyn) {
            if (!exactSearch) {
                query = session.createQuery(
                        "select distinct pd from PDQDisease pd where lower(pd.preferredName) like "
                        + ":name order by pd.preferredName")
                        .setString("name", "%" + searchString.toLowerCase() + "%");    
            } else {
                
                query = session.createQuery(
                        "select distinct pd from PDQDisease pd where lower(pd.preferredName) = "
                        + ":name order by pd.preferredName")
                        .setString("name", searchString.toLowerCase());    
            }
            
        } else {
            if (!exactSearch) {
            query = session
                    .createQuery(
                            "select distinct pd from PDQDisease pd  where lower(pd.preferredName) like :name "
                            + "or lower(pd.diseaseAlternames.alternateName) like :name order by pd.preferredName")
                    .setString("name", "%" + searchString.toLowerCase() + "%");
            } else {
                query = session
                        .createQuery(
                                "select distinct pd from PDQDisease pd  where lower(pd.preferredName) = :name "
                                + "or lower(pd.diseaseAlternames.alternateName) = :name order by pd.preferredName")
                        .setString("name", searchString.toLowerCase());
            }
        }

        for (Iterator iterator = query.list().iterator(); iterator.hasNext();) {
            boolean exactMatch = false;
            PDQDisease d = (PDQDisease) iterator.next();
            PDQDiseaseNode dn = new PDQDiseaseNode();
            dn.setId(d.getId());
            dn.setName(d.getPreferredName());
            if (dn.getName().equalsIgnoreCase(searchString)) {
                exactMatch = true;
            }
            dn.setNcitId(d.getNtTermIdentifier());
            if (searchSyn) {
                for (Iterator iterator2 = d.getDiseaseAlternames().iterator(); iterator2.hasNext();) {
                    PDQDiseaseAltername altName = (PDQDiseaseAltername) iterator2.next();
                    if (!exactMatch && altName.getAlternateName().equalsIgnoreCase(searchString)) {
                        exactMatch = true;
                    }
                    dn.getAlterNames().add(altName.getAlternateName());
                }
            }
            if (exactMatch) {
                result.add(0, dn);
            } else {
                result.add(dn);
            }
        }

        return result;
    }
}

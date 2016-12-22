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
package gov.nih.nci.pa.service.util;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.NonInterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.PDQDisease;
import gov.nih.nci.pa.domain.PDQDiseaseParent;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyOnhold;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.dto.MilestoneDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudySubtypeCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.convert.ReportStudyProtocolQueryConverter;
import gov.nih.nci.pa.iso.convert.StudyOnholdConverter;
import gov.nih.nci.pa.iso.convert.TrialSearchStudyProtocolQueryConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.search.StudyProtocolBeanSearchCriteria;
import gov.nih.nci.pa.service.search.StudyProtocolOptions;
import gov.nih.nci.pa.service.search.StudyProtocolQueryBeanSearchCriteria;
import gov.nih.nci.pa.service.search.StudyProtocolSortCriterion;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_LAST_UPDATER_INFO;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_OTHER_IDENTIFIERS;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_PROGRAM_CODES;

/**
 * @author Naveen Amiruddin
 * @since 08/13/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessiveClassLength" })
public class ProtocolQueryServiceBean extends AbstractBaseSearchBean<StudyProtocol>
    implements ProtocolQueryServiceLocal {

    private static final double MILLIS_PER_SECOND = 1000D;

    private static final String DCP = "dcp";

    private static final String CTEP = "ctep";

    private static final String CTEP_DCP = "ctepdcp";

    private static final Logger LOG = Logger.getLogger(ProtocolQueryServiceBean.class);

    private static final String EXCLUDE_CTEP_DCP = "exclude";

    @EJB
    private DataAccessServiceLocal dataAccessService;

    @EJB
    private RegistryUserServiceLocal registryUserService;

    @EJB
    private PDQDiseaseServiceLocal pdqDiseaseService;

    @EJB
    private ProtocolQueryResultsServiceLocal protocolQueryResultsService;
    
    @EJB
    private LookUpTableServiceRemote lookUpService;

    private PAServiceUtils paServiceUtils;

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyProtocolQueryDTO> getStudyProtocolByCriteria(
            StudyProtocolQueryCriteria spsc) throws PAException {
        return getStudyProtocolByCriteria(spsc, SKIP_PROGRAM_CODES);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyProtocolQueryDTO> getStudyProtocolByCriteria(
            StudyProtocolQueryCriteria spsc,
            ProtocolQueryPerformanceHints... hints) throws PAException {
        if (isCriteriaEmpty(spsc)) {
            throw new PAException("At least one criteria is required");
        }
        long start = System.currentTimeMillis();
        List<StudyProtocolQueryDTO> pdtos = new ArrayList<StudyProtocolQueryDTO>();
        List<Long> queryList = getStudyProtocolIdQueryResults(spsc);
        pdtos = protocolQueryResultsService.getResults(queryList,
                BooleanUtils.toBoolean(spsc.isMyTrialsOnly()), spsc.getUserId(), hints);        
        if (CollectionUtils.isNotEmpty(pdtos)) {
            pdtos = appendOnHold(pdtos);
        }
        long end = System.currentTimeMillis();
        LOG.warn(spsc.toString() + " took "
                + ((end - start) / MILLIS_PER_SECOND)
                + " seconds and returned " + (pdtos != null ? pdtos.size() : 0)
                + " trials.");
        return pdtos;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyProtocolQueryDTO> getStudyProtocolByCriteriaForReporting(StudyProtocolQueryCriteria criteria)
            throws PAException {
        if (isCriteriaEmpty(criteria)) {
            throw new PAException("At least one criteria is required");
        }
        lookupBiomarkerIds(criteria);
        List<StudyProtocolQueryDTO> pdtos = new ArrayList<StudyProtocolQueryDTO>();
        List<StudyProtocol> studies = getStudyProtocolQueryResultList(criteria);
        List<Long> spIdList = convertProtocolListToIdList(studies);
        pdtos = convertToStudyProtocolDTOById(spIdList, criteria.getUserId(),
                                              BooleanUtils.toBoolean(criteria.isMyTrialsOnly()));
        if (CollectionUtils.isNotEmpty(pdtos)) {
            pdtos = appendOnHold(pdtos);
        }
        return pdtos;
    }

    /**
     * Lookup the biomarker ids by names for the search by biomarkers.
     * @param criteria The search criteria
     */
    void lookupBiomarkerIds(StudyProtocolQueryCriteria criteria) {
        if (CollectionUtils.isNotEmpty(criteria.getBioMarkerNames())) {
            DAQuery query = DAQuery.queryByName("gov.nih.nci.pa.domain.PlannedMarker.idsByLongNames");
            query.addParameter("longNames", criteria.getBioMarkerNames());
            List<Long> ids = dataAccessService.findByQuery(query);
            criteria.setBioMarkerIds(ids);
        }
    }

    /**
     * @param parentDiseases parent Diseases.
     * @return list of diseases including parent and children etc..
     */
    Set<Long> getPDQParentsAndDescendants(List<Long> parentDiseases) {
        Set<Long> result = new HashSet<Long>();
        List<PDQDisease> diseases = pdqDiseaseService.getByIds(parentDiseases);
        for (PDQDisease disease : diseases) {
            if (!result.contains(disease.getId())) {
                result.add(disease.getId());
                traverseTree(disease, result);
            }
        }
        return result;
    }


    private void traverseTree(PDQDisease disease, Set<Long> result) {
        for (PDQDiseaseParent pdqDiseaseParent : disease.getDiseaseChildren()) {
            result.add(pdqDiseaseParent.getDisease().getId());
            traverseTree(pdqDiseaseParent.getDisease(), result);
        }
    }

    private List<Long> convertProtocolListToIdList(List<StudyProtocol> studyProtocols) {
        List<Long> result = new ArrayList<Long>();
        for (StudyProtocol studyProtocol : studyProtocols) {
            result.add(studyProtocol.getId());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyProtocolQueryDTO getTrialSummaryByStudyProtocolId(Long studyProtocolId) throws PAException {
        if (studyProtocolId == null) {
            LOG.error(" studyProtocol Identifier cannot be null ");
            throw new PAException(" studyProtocol Identifier cannot be null ");
        }

        Session session = PaHibernateUtil.getCurrentSession();
        // PO-3608. Session needs to be cleared, or else, when calling save first,
        // an incomplete object is returned.
        session.flush();
        session.clear();

        StudyProtocolQueryCriteria spqc = new StudyProtocolQueryCriteria();
        spqc.setStudyProtocolId(studyProtocolId);
        List<StudyProtocol> queryList = getStudyProtocolQueryResults(spqc);
        if (queryList == null) {
            throw new PAException(" Study protocol was not found for id " + studyProtocolId);
        }
        List<StudyProtocolQueryDTO> trialSummaries = convertToStudyProtocolDTO(queryList, null, false);

        if (trialSummaries == null || trialSummaries.size() <= 0) {
            throw new PAException(" Could not be converted to DTO for id " + studyProtocolId);
        }
        return trialSummaries.get(0);
    }

    /**
     * Converts Study protocols objects to dtos.
     * @param protocolQueryResult the resulting study protocols
     * @param userId the id of the person calling the search
     * @param myTrialsOnly whether to return trials for the given user only
     * @return a list of StudyProtocolQueryDTOs
     * @throws PAException on error
     */
    private List<StudyProtocolQueryDTO> convertToStudyProtocolDTO(List<StudyProtocol> protocolQueryResult,
            Long userId, boolean myTrialsOnly)
        throws PAException {
        TrialSearchStudyProtocolQueryConverter studyProtocolQueryConverter =
            new TrialSearchStudyProtocolQueryConverter(getRegistryUserService(), getPaServiceUtils());
        List<StudyProtocolQueryDTO> studyProtocolDtos = new ArrayList<StudyProtocolQueryDTO>();
        RegistryUser potentialOwner = getPotentialOwner(userId);
        try {
            for (StudyProtocol studyProtocol : protocolQueryResult) {

                StudyProtocolQueryDTO studyProtocolDto = studyProtocolQueryConverter.convertToStudyProtocolDto(
                            studyProtocol, myTrialsOnly, potentialOwner);

                if (studyProtocolDto != null) {
                    studyProtocolDtos.add(studyProtocolDto);
                }
            }
        } catch (Exception e) {
            throw new PAException("General error in while converting to StudyProtocolQueryDTO", e);
        }
        return studyProtocolDtos;
    }

    private List<StudyProtocolQueryDTO> convertToStudyProtocolDTOById(List<Long> protocolQueryResult,
            Long userId, boolean myTrialsOnly)
        throws PAException {
        ReportStudyProtocolQueryConverter studyProtocolQueryConverter =
            new ReportStudyProtocolQueryConverter(getRegistryUserService(), getPaServiceUtils());
        List<StudyProtocolQueryDTO> studyProtocolDtos = new ArrayList<StudyProtocolQueryDTO>();
        RegistryUser potentialOwner = getPotentialOwner(userId);
        try {
            for (Long spId : protocolQueryResult) {
                StudyProtocolQueryDTO studyProtocolDto =
                    studyProtocolQueryConverter.convertToStudyProtocolDtoForReporting(
                            spId, myTrialsOnly, potentialOwner);

                if (studyProtocolDto != null) {
                    studyProtocolDtos.add(studyProtocolDto);
                }
            }
        } catch (Exception e) {
            throw new PAException("General error in while converting to StudyProtocolQueryDTO", e);
        }
        return studyProtocolDtos;
    }

    private RegistryUser getPotentialOwner(Long userId) throws PAException {
        return userId == null ? null : registryUserService.getUserById(userId);
    }

    @SuppressWarnings("unchecked")
    private List<StudyProtocolQueryDTO> appendOnHold(List<StudyProtocolQueryDTO> spDtos) throws PAException {
        DAQuery query = DAQuery.queryByName("gov.nih.nci.pa.domain.StudyOnhold.offholdByStudyProtocolIds");
        query.addParameter("spIds", getProtocolIds(spDtos));
        List<Object[]> studyOnHoldList = dataAccessService.findByQuery(query);
        Map<Long, List<StudyOnhold>> studyOnHolds = generateActiveHoldMap(studyOnHoldList);
        populateActiveHoldData(spDtos, studyOnHolds);        
        populateAllHolds(spDtos, studyOnHoldList);
        return spDtos;
    }    
   
   
    private void populateAllHolds(final List<StudyProtocolQueryDTO> spDtos,
            final List<Object[]> studyOnHoldList) {
        for (Object[] searchResult : studyOnHoldList) {
            Long studyProtocolId = (Long) searchResult[0];
            StudyOnhold studyOnhold = (StudyOnhold) searchResult[1];
            for (StudyProtocolQueryDTO sp : spDtos) {
                if (studyProtocolId.equals(sp.getStudyProtocolId())) {
                    sp.getAllHolds().add(
                            new StudyOnholdConverter()
                                    .convertFromDomainToDto(studyOnhold));
                    break;
                }
            }
        }
    }

    private Map<Long, List<StudyOnhold>> generateActiveHoldMap(
            List<Object[]> onHoldReasons) {
        Map<Long, List<StudyOnhold>> studyOnHolds = new HashMap<Long, List<StudyOnhold>>();
        for (Object[] searchResult : onHoldReasons) {
            Long studyProtocolId = (Long) searchResult[0];
            StudyOnhold studyOnhold = (StudyOnhold) searchResult[1];
            if (studyOnhold.getOffholdDate() != null) {
                continue;
            }
            if (studyOnHolds.containsKey(studyProtocolId)) {
                studyOnHolds.get(studyProtocolId).add(studyOnhold);
            } else {
                List<StudyOnhold> sohList = new ArrayList<StudyOnhold>();
                sohList.add(studyOnhold);
                studyOnHolds.put(studyProtocolId, sohList);
            }
        }
        return studyOnHolds;
    }

    private void populateActiveHoldData(List<StudyProtocolQueryDTO> spDtos, Map<Long, List<StudyOnhold>> onHold) {
        List<StudyOnhold> sohLists = null;
        StringBuffer sb = new StringBuffer();
        StringBuffer sbDate = new StringBuffer();        
        for (StudyProtocolQueryDTO spqDto : spDtos) {
            if (onHold.containsKey(spqDto.getStudyProtocolId())) {
                sohLists = onHold.get(spqDto.getStudyProtocolId());
                sb = new StringBuffer();
                sbDate = new StringBuffer();
                String offHoldDate = "";
                for (StudyOnhold studyOnhold : sohLists) {
                    if (sb.length() == 0) {
                        sb.append(studyOnhold.getOnholdReasonCode().getCode());
                    } else {
                        sb.append(PAConstants.COMMA).append(studyOnhold.getOnholdReasonCode().getCode());
                    }
                    sbDate.append(PAUtil.normalizeDateString((
                                studyOnhold.getOnholdDate()).toString())).append(PAConstants.WHITESPACE);
                    offHoldDate = studyOnhold.getOffholdDate() != null ? PAUtil
                            .normalizeDateString((studyOnhold.getOffholdDate())
                                    .toString()) : "";  
                    spqDto.setActiveHoldDate(studyOnhold.getOnholdDate());
                    spqDto.setActiveHoldReason(studyOnhold.getOnholdReasonCode());
                    spqDto.setActiveHoldReasonCategory(studyOnhold.getOnholdReasonCategory());
                }
                spqDto.setOnHoldReasons(sb.toString());
                spqDto.setOnHoldDate(sbDate.toString());                
                spqDto.setOffHoldDate(offHoldDate);
            }
        }
    }

    private List<Long> getProtocolIds(List<StudyProtocolQueryDTO> spDtos) {
        List<Long> spIds = new ArrayList<Long>();
        for (StudyProtocolQueryDTO spqDto : spDtos) {
            spIds.add(spqDto.getStudyProtocolId());
        }
        return spIds;
    }

    @SuppressWarnings("PMD.ExcessiveMethodLength")
    private StudyProtocolQueryBeanSearchCriteria getExampleCriteria(StudyProtocolQueryCriteria criteria) 
            throws PAException {
        StudyProtocol example = instantiateExampleObject(criteria);
        StudyProtocolOptions options = new StudyProtocolOptions();
        options.setUserId(criteria.getUserId());
        options.setExcludeRejectedTrials(BooleanUtils.isTrue(criteria.isExcludeRejectProtocol()));
        options.setExcludeTerminatedTrials(BooleanUtils.isTrue(criteria.isExcludeTerminatedTrials()));
        options.setLockedTrials(criteria.isStudyLockedBy());
        options.setLockedUser(criteria.getUserLastCreated());
        options.setSearchCTEPTrials(includeCTEP(criteria));
        options.setSearchDCPTrials(includeDCP(criteria));
        options.setSearchCTEPAndDCPTrials(includeDCPAndCTEP(criteria));
        options.setExcludeCtepDcpTrials(excludeDCPAndCTEP(criteria));
        options.setMyTrialsOnly(BooleanUtils.isTrue(criteria.isMyTrialsOnly()));        
        options.setParticipatingSiteIds(criteria.getParticipatingSiteIds());
        options.setTrialSubmissionType(SubmissionTypeCode.getByCode(criteria.getSubmissionType()));
        options.getTrialSubmissionTypes().addAll(criteria.getTrialSubmissionTypes());
        options.setNciSponsored(criteria.getNciSponsored());
        options.setNotFlaggedWith(criteria.getNotFlaggedWith());
        options.setSiteStatusCodes(criteria.getSiteStatusCodes());
        options.setHasTweets(criteria.getHasTweets());
        options.setInboxProcessing(BooleanUtils.isTrue(criteria.isInBoxProcessing()));
        options.setPhaseCodesByValues(criteria.getPhaseCodes());
        options.setCountryName(criteria.getCountryName());
        options.setStates(criteria.getStates());
        options.setCity(criteria.getCity());
        options.setSummary4AnatomicSites(criteria.getSummary4AnatomicSites());
        options.setBioMarkers(criteria.getBioMarkerIds());
        options.setPdqDiseases(criteria.getPdqDiseases());
        options.setInterventionIds(criteria.getInterventionIds());
        options.setInterventionAlternateNameIds(criteria.getInterventionAlternateNameIds());
        options.setInterventionTypes(criteria.getInterventionTypes());
        options.setLeadOrganizationIds(criteria.getLeadOrganizationIds());
        options.setAnyTypeIdentifier(criteria.getAnyTypeIdentifier());
        options.setCheckedOut(criteria.getCheckedOut());
        options.setHoldRecordExists(criteria.getHoldRecordExists());
        options.setOnholdReasons(criteria.getOnholdReasons());
        options.setOnholdOtherReasonCategories(criteria.getOnholdOtherReasonCategories());
        options.setSubmittedOnOrAfter(criteria.getSubmittedOnOrAfter());
        options.setSubmittedOnOrBefore(criteria.getSubmittedOnOrBefore());
        options.setSubmitterAffiliateOrgId(criteria.getSubmitterAffiliateOrgId());       
        options.setMilestoneFilters(criteria.getMilestoneFilters());  
        options.setProcessingPriority(toIntegerList(criteria.getProcessingPriority()));
        options.setStudySource(criteria.getStudySource());
        options.setPrimaryPurposeCodes(criteria.getPrimaryPurposeCodes());
        if (criteria.getCtgovXmlRequiredIndicator() != null && !criteria.getCtgovXmlRequiredIndicator().equals("")) {
            options.setCtgovXmlRequiredIndicator(Boolean.valueOf((criteria.getCtgovXmlRequiredIndicator())));
        } else {
            options.setCtgovXmlRequiredIndicator(null);
        }
        
        if (StringUtils.equalsIgnoreCase(criteria.getHoldStatus(), PAConstants.ON_HOLD)) {
            options.setSearchOnHoldTrials(true);
        } else if (StringUtils.equalsIgnoreCase(criteria.getHoldStatus(), PAConstants.NOT_ON_HOLD)) { 
            options.setSearchOffHoldTrials(true);
        }        
        options.setCurrentOrPreviousMilestone(criteria.getCurrentOrPreviousMilestone());
        if (criteria.getSection801Indicators() != null) {
            options.setSection801Indicators(criteria.getSection801Indicators());
        }
        options.setPcdFromDate(criteria.getPcdFrom());
        options.setPcdToDate(criteria.getPcdTo());
        List<ActualAnticipatedTypeCode> pcdDateTypes = new ArrayList<ActualAnticipatedTypeCode>();
        if (criteria.getPcdType() != null) {
            pcdDateTypes.add(ActualAnticipatedTypeCode.getByCode(criteria.getPcdType()));
        }        
        options.setPcdDateTypes(pcdDateTypes);
        populateExample(criteria, example);
        if (criteria.getReportingPeriodStatusCriterion() != null) {
            options.setReportingPeriodEnd(criteria.getReportingPeriodStatusCriterion().getEndDate());
            options.setReportingPeriodStart(criteria.getReportingPeriodStatusCriterion().getStartDate());
            options.getStudyStatusCodes().addAll(criteria.getReportingPeriodStatusCriterion().getStudyStatusCodes());
        }
        if (criteria.getProgramCodeIds() != null) {
            options.getProgramCodeIds().addAll(criteria.getProgramCodeIds());
        }

        return new StudyProtocolQueryBeanSearchCriteria(example, options);
    }

    private List<Integer> toIntegerList(List<String> list) {
        List<Integer> intList = new ArrayList<Integer>();
        for (String str : list) {
            if (StringUtils.isNotBlank(str)) {
                intList.add(Integer.valueOf(str));
            }
        }
        return intList;
    }

    /**
     * @param criteria 
     * @return
     */
    private StudyProtocol instantiateExampleObject(
            StudyProtocolQueryCriteria criteria) {
        StudyProtocol protocol;
        if (NonInterventionalStudyProtocol.class.getSimpleName().equalsIgnoreCase(
                criteria.getStudyProtocolType())) {
            protocol = new NonInterventionalStudyProtocol();
            ((NonInterventionalStudyProtocol) protocol)
                    .setStudySubtypeCode(StudySubtypeCode.getByCode(criteria
                            .getStudySubtypeCode()));
        } else if (InterventionalStudyProtocol.class.getSimpleName()
                .equalsIgnoreCase(criteria.getStudyProtocolType())) {
            protocol = new InterventionalStudyProtocol();

        } else {
            protocol = new StudyProtocol();
        }
        return protocol;
    }

    private boolean includeCTEP(StudyProtocolQueryCriteria criteria) {
        return CTEP.equals(criteria.getCtepDcpCategory());
    }
    
    private boolean includeDCP(StudyProtocolQueryCriteria criteria) {
        return DCP.equals(criteria.getCtepDcpCategory());
    }
    
    private boolean includeDCPAndCTEP(StudyProtocolQueryCriteria criteria) {
        return CTEP_DCP.equals(criteria.getCtepDcpCategory());
    }  
    
    private boolean excludeDCPAndCTEP(StudyProtocolQueryCriteria criteria) {
        return EXCLUDE_CTEP_DCP.equals(criteria.getCtepDcpCategory());
    } 


    @SuppressWarnings("unchecked")
    private List<StudyProtocol> getStudyProtocolQueryResults(StudyProtocolQueryCriteria criteria)
    throws PAException {
        StudyProtocolQueryBeanSearchCriteria crit = getExampleCriteria(criteria);
        List<StudyProtocol> results = new ArrayList<StudyProtocol>();
        try {
            validateSearchCriteria(crit);
            String orderBy = "";
            String joinClause = createJoinClauseByCriteria(criteria);
            Query query = crit.getQuery(orderBy, joinClause, false);
            LOG.debug(query.getQueryString());
            results = query.list();
        } catch (Exception e) {
            throw new PAException("An error has occurred when searching for trials.", e);
        }
        return results;
    }
    
    @SuppressWarnings("unchecked")
    private List<Long> getStudyProtocolIdQueryResults(
            StudyProtocolQueryCriteria criteria) throws PAException {
        StudyProtocolQueryBeanSearchCriteria crit = getExampleCriteria(criteria);
        List<Long> results = new ArrayList<Long>();       
        try {
            validateSearchCriteria(crit);
            String orderBy = "";
            String joinClause = createJoinClauseByCriteria(criteria);
            Query query = crit.getQuery(Arrays.asList(new String[] {"id"}),
                    orderBy, joinClause, false);
            query.setCacheable(false);            
            LOG.debug(query.getQueryString());           
            results = query.list();
        } catch (Exception e) {
            throw new PAException(
                    "An error has occurred when searching for trials.", e);
        } 
        return results;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<StudyProtocol> getStudyProtocolQueryResultList(StudyProtocolQueryCriteria criteria) throws PAException {
        if (CollectionUtils.isNotEmpty(criteria.getPdqDiseases())) {
            List<Long> allDiseases = new ArrayList<Long>(getPDQParentsAndDescendants(criteria.getPdqDiseases()));
            criteria.setPdqDiseases(allDiseases);
        }
        StudyProtocolQueryBeanSearchCriteria crit = getExampleCriteria(criteria);
        try {
            validateSearchCriteria(crit);
            String orderBy = "";
            String joinClause = createJoinClauseByCriteria(criteria);
            Query query = crit.getQuery(orderBy, joinClause, false);
            LOG.debug(query.getQueryString());
            return query.list();
        } catch (Exception e) {
            throw new PAException("An error has occurred when searching for trials.", e);
        }

    }

    private String createJoinClauseByCriteria(StudyProtocolQueryCriteria criteria) {
        StringBuilder result = new StringBuilder();
        if (CollectionUtils.isNotEmpty(criteria.getSummary4AnatomicSites())) {
            result.append(" left outer join obj.summary4AnatomicSites as ans ");
        }
        if (CollectionUtils.isNotEmpty(criteria.getLeadOrganizationIds())) {
            result.append(" join obj.studySites as leadOrgSite join leadOrgSite.researchOrganization as leadOrgRo ");
        }
//        if (criteria.getPcdFrom() != null || criteria.getPcdTo() != null) {
//            result.append(" join obj.dates as studyDates ");
//        }
        return result.toString();
    }

    private void populateExampleStudyProtocol(StudyProtocolQueryCriteria crit, StudyProtocol sp) throws PAException {
        sp.setId(crit.getStudyProtocolId());
        sp.setOfficialTitle(crit.getOfficialTitle());        
        sp.setPhaseAdditionalQualifierCode(
                PhaseAdditionalQualifierCode.getByCode(crit.getPhaseAdditionalQualifierCode()));
        if (StringUtils.equalsIgnoreCase(crit.getTrialCategory(), "p")) {
            sp.setProprietaryTrialIndicator(Boolean.TRUE);
        } else if (StringUtils.equalsIgnoreCase(crit.getTrialCategory(), "n")) {
            sp.setProprietaryTrialIndicator(Boolean.FALSE);
        }
        
        if (StringUtils.isNotBlank(crit.getSubmitter())) {
            sp.setUserLastCreated(CSMUserService.getInstance().getCSMUser(crit.getSubmitter()));
        }
            sp.setStatusCode(ActStatusCode.ACTIVE);            
        
        sp.setCtroOverride(crit.getCtroOverride());
        
        if (crit.getAssignedUserId() != null) {
            User user = new User();
            user.setUserId(crit.getAssignedUserId());
            sp.setAssignedUser(user);
        }
        
        populateExampleStudyProtocolDiseaseInterventionType(crit, sp);
    }
   
    private void populateExampleStudyProtocolSumm4FundSrc(StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        if (crit.getSumm4FundingSourceId() != null
                || StringUtils.isNotBlank(crit.getSumm4FundingSourceTypeCode())) {
            StudyResourcing stdRes = new StudyResourcing();
            if (crit.getSumm4FundingSourceId() != null
                    && StringUtils.isNotBlank(crit.getSumm4FundingSourceId().toString())) {
                stdRes.setOrganizationIdentifier(crit.getSumm4FundingSourceId().toString());
            }
            stdRes.setSummary4ReportedResourceIndicator(true);
            stdRes.setTypeCode(SummaryFourFundingCategoryCode.getByCode(crit.getSumm4FundingSourceTypeCode()));
            stdRes.setActiveIndicator(true);
            sp.getStudyResourcings().add(stdRes);
        }
    }

    private void populateExampleStudyProtocolDiseaseInterventionType(
            StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        populateExampleStudyProtocolSumm4FundSrc(crit, sp);
    }

    private void populateExampleSpOtherIdentifiers(StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        if (StringUtils.isNotEmpty(crit.getNciIdentifier())) {
            Ii nciId = new Ii();
            nciId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
            nciId.setExtension(crit.getNciIdentifier());
            sp.getOtherIdentifiers().add(nciId);
        }

        if (StringUtils.isNotEmpty(crit.getOtherIdentifier())) {
            Ii otherId = new Ii();
            otherId.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);
            otherId.setExtension(crit.getOtherIdentifier());
            sp.getOtherIdentifiers().add(otherId);
        }
    }

    private void populateExampleStudyOverallStatus(StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        if (StringUtils.isNotEmpty(crit.getStudyStatusCode())) {
            StudyOverallStatus sos = new StudyOverallStatus();
            sos.setStatusCode(StudyStatusCode.getByCode(crit.getStudyStatusCode()));
            sp.getStudyOverallStatuses().add(sos);
        }
    }
    
    private void populateExampleStudyOverallStatuses(StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        for (String statusCode : crit.getStudyStatusCodeList()) {
            StudyOverallStatus sos = new StudyOverallStatus();
            sos.setStatusCode(StudyStatusCode.getByCode(statusCode));
            sp.getStudyOverallStatuses().add(sos);
        }
    }

    private void populateExampleDocumentWork(StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        for (String statusCode : crit.getDocumentWorkflowStatusCodes()) {
            DocumentWorkflowStatus dws = new DocumentWorkflowStatus();
            dws.setStatusCode(DocumentWorkflowStatusCode.getByCode(statusCode));
            sp.getDocumentWorkflowStatuses().add(dws);
        }
    }

    private void populateExampleStudySites(StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        if (StringUtils.isNotEmpty(crit.getLeadOrganizationTrialIdentifier())) {
            StudySite ss = new StudySite();
            ss.setLocalStudyProtocolIdentifier(crit.getLeadOrganizationTrialIdentifier());
            ss.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
            sp.getStudySites().add(ss);
        }

        if (StringUtils.isNotEmpty(crit.getNctNumber())) {
            StudySite ss = new StudySite();
            ss.setLocalStudyProtocolIdentifier(crit.getNctNumber());
            ss.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
            ss.setResearchOrganization(PADomainUtils.createROExampleObjectByOrgName(PAConstants.CTGOV_ORG_NAME));
            sp.getStudySites().add(ss);
        }

        if (StringUtils.isNotEmpty(crit.getCtepIdentifier())) {
            StudySite ss = new StudySite();
            ss.setLocalStudyProtocolIdentifier(crit.getCtepIdentifier());
            ss.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
            ss.setResearchOrganization(PADomainUtils.createROExampleObjectByOrgName(PAConstants.CTEP_ORG_NAME));
            sp.getStudySites().add(ss);
        }

        if (StringUtils.isNotEmpty(crit.getDcpIdentifier())) {
            StudySite ss = new StudySite();
            ss.setLocalStudyProtocolIdentifier(crit.getDcpIdentifier());
            ss.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
            ss.setResearchOrganization(PADomainUtils.createROExampleObjectByOrgName(PAConstants.DCP_ORG_NAME));
            sp.getStudySites().add(ss);
        }
        
        if (StringUtils.isNotEmpty(crit.getCcrIdentifier())) {
            StudySite ss = new StudySite();
            ss.setLocalStudyProtocolIdentifier(crit.getCcrIdentifier());
            ss.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
            ss.setResearchOrganization(PADomainUtils.createROExampleObjectByOrgName(PAConstants.CCR_ORG_NAME));
            sp.getStudySites().add(ss);
        }


    }

    private void populateExampleStudyMilestones(
            StudyProtocolQueryCriteria crit, StudyProtocol sp) {
        for (String code : crit.getStudyMilestone()) {
            if (StringUtils.isNotEmpty(code)) {
                StudyMilestone sm = new StudyMilestone();
                sm.setMilestoneCode(MilestoneCode.getByCode(code));
                sp.getStudyMilestones().add(sm);
            }
        }
    }

    private void populateExampleStudyContacts(StudyProtocolQueryCriteria crit,
            StudyProtocol sp) {
        if (CollectionUtils.isNotEmpty(crit.getPrincipalInvestigatorIds())) {
            for (String piID : crit.getPrincipalInvestigatorIds()) {
                StudyContact sc = new StudyContact();
                ClinicalResearchStaff crs = new ClinicalResearchStaff();
                Person person = new Person();
                person.setId(Long.valueOf(piID));
                crs.setPerson(person);
                sc.setClinicalResearchStaff(crs);
                sc.setRoleCode(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR);
                sp.getStudyContacts().add(sc);
            }
        }
    }

    private void populateExample(StudyProtocolQueryCriteria crit, StudyProtocol sp) throws PAException {
        populateExampleStudyProtocol(crit, sp);
        populateExampleSpOtherIdentifiers(crit, sp);
        populateExampleStudyOverallStatus(crit, sp);
        populateExampleStudyOverallStatuses(crit, sp);
        populateExampleDocumentWork(crit, sp);
        populateExampleStudySites(crit, sp);
        populateExampleStudyMilestones(crit, sp);
        populateExampleStudyContacts(crit, sp);
    }

    @SuppressWarnings("PMD.ExcessiveMethodLength")
    private boolean isCriteriaEmpty(StudyProtocolQueryCriteria criteria) {
        return (StringUtils.isEmpty(criteria.getNciIdentifier())
                && StringUtils.isEmpty(criteria.getCtgovXmlRequiredIndicator())
                && criteria.getStudyProtocolId() == null                
                && criteria.getAssignedUserId() == null
                && criteria.getSubmittedOnOrAfter() == null
                && criteria.getSubmittedOnOrBefore() == null
                && criteria.getNciSponsored() == null
                && criteria.getNotFlaggedWith() == null
                && criteria.getHasTweets() == null
                && criteria.getHoldRecordExists() == null
                && criteria.getCtroOverride() == null
                && criteria.getCurrentOrPreviousMilestone() == null               
                && StringUtils.isEmpty(criteria.getSubmitterAffiliateOrgId())
                && StringUtils.isEmpty(criteria.getOfficialTitle())
                && StringUtils.isEmpty(criteria.getSubmitter())
                && StringUtils.isEmpty(criteria.getLeadOrganizationTrialIdentifier())
                && CollectionUtils.isEmpty(criteria.getPrincipalInvestigatorIds())
                && CollectionUtils.isEmpty(criteria.getPrimaryPurposeCodes())
                && CollectionUtils.isEmpty(criteria.getSiteStatusCodes())
                && StringUtils.isEmpty(criteria.getPhaseAdditionalQualifierCode())
                && StringUtils.isEmpty(criteria.getStudyStatusCode())
                && CollectionUtils.isEmpty(criteria.getStudyStatusCodeList())
                && CollectionUtils.isEmpty(criteria.getStudyMilestone())
                && CollectionUtils.isEmpty(criteria.getMilestoneFilters())
                && CollectionUtils.isEmpty(criteria.getProcessingPriority())
                && StringUtils.isEmpty(criteria.getOtherIdentifier())
                && StringUtils.isEmpty(criteria.getNctNumber())
                && StringUtils.isEmpty(criteria.getDcpIdentifier())
                && StringUtils.isEmpty(criteria.getCcrIdentifier())
                && StringUtils.isEmpty(criteria.getCtepIdentifier())
                && StringUtils.isEmpty(criteria.getAnyTypeIdentifier())
                && StringUtils.isEmpty(criteria.getCountryName())
                && StringUtils.isEmpty(criteria.getCity())
                && CollectionUtils.isEmpty(criteria.getDocumentWorkflowStatusCodes())                
                && CollectionUtils.isEmpty(criteria.getOnholdReasons())
                && CollectionUtils.isEmpty(criteria.getStates())
                && CollectionUtils.isEmpty(criteria.getPhaseCodes())
                && CollectionUtils.isEmpty(criteria.getSummary4AnatomicSites())
                && CollectionUtils.isEmpty(criteria.getBioMarkerNames())
                && CollectionUtils.isEmpty(criteria.getPdqDiseases())
                && CollectionUtils.isEmpty(criteria.getParticipatingSiteIds())
                && CollectionUtils.isEmpty(criteria.getLeadOrganizationIds())
                && CollectionUtils.isEmpty(criteria.getTrialSubmissionTypes())
                && StringUtils.isEmpty(criteria.getHoldStatus())
                && !criteria.isStudyLockedBy()
                && criteria.getCheckedOut() == null
                && StringUtils.isEmpty(criteria.getCtepDcpCategory())
                && StringUtils.isEmpty(criteria.getSubmissionType())
                && StringUtils.isEmpty(criteria.getTrialCategory())
                && StringUtils.isEmpty(criteria.getStudyProtocolType())
                && StringUtils.isEmpty(criteria.getStudySubtypeCode())
                && criteria.getSumm4FundingSourceId() == null
                && CollectionUtils.isEmpty(criteria.getStudySource())
                && StringUtils.isEmpty(criteria.getSumm4FundingSourceTypeCode())
                && CollectionUtils.isEmpty(criteria.getInterventionIds())
                && CollectionUtils.isEmpty(criteria.getInterventionAlternateNameIds())
                && CollectionUtils.isEmpty(criteria.getInterventionTypes())
                && CollectionUtils.isEmpty(criteria.getProgramCodeIds())
                && criteria.getReportingPeriodStatusCriterion() == null
                && (criteria.isMyTrialsOnly() != null && !criteria.isMyTrialsOnly()
                        || criteria.isMyTrialsOnly() == null));
    }


    /**
     * @param orgIdentifier the org identifier
     * @return list studyProtocols
     * @throws PAException on error
     */
    @Override
    public List<StudyProtocol> getStudyProtocolByOrgIdentifier(Long orgIdentifier) throws PAException {
        if (orgIdentifier == null) {
            throw new PAException("Organization Identifier is null.");
        }
        StudyProtocol sp = new StudyProtocol();
        sp.setStatusCode(ActStatusCode.ACTIVE);

        Organization org = new Organization();
        org.setIdentifier(String.valueOf(orgIdentifier));

        ResearchOrganization ro = new ResearchOrganization();
        ro.setOrganization(org);

        StudySite ss = new StudySite();
        ss.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        ss.setResearchOrganization(ro);

        sp.getStudySites().add(ss);

        StudyProtocolBeanSearchCriteria crit = new StudyProtocolBeanSearchCriteria(sp);

        PageSortParams<StudyProtocol> params = new PageSortParams<StudyProtocol>(PAConstants.MAX_SEARCH_RESULTS, 0,
                StudyProtocolSortCriterion.STUDY_PROTOCOL_ID, false);
        return search(crit, params);
    }
    
    
    
    @Override
    public List<String> getOfficialTitles(String matchString)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        StringBuffer hql = new StringBuffer();
        hql.append("select sp.officialTitle from StudyProtocol sp where upper(sp.officialTitle) like :matchString");
        @SuppressWarnings("unchecked")
        List<String> matches = session.createQuery(hql.toString())
                .setParameter("matchString", "%" + matchString.toUpperCase(Locale.US) + "%").list();
        return matches;
    }

    /**
     * @return the registry user service
     */
    public RegistryUserServiceLocal getRegistryUserService() {
        return registryUserService;
    }

    /**
     * @param registryUserService the registry user service to set
     */
    public void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * @return the paServiceUtils
     */
    public PAServiceUtils getPaServiceUtils() {
        if (paServiceUtils == null) {
            paServiceUtils = new PAServiceUtils();
        }
        return paServiceUtils;
    }

    /**
     * @param paServiceUtils the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

    /**
     * @param dataAccessService the dataAccessService to set
     */
    public void setDataAccessService(DataAccessServiceLocal dataAccessService) {
        this.dataAccessService = dataAccessService;
    }

    /**
     * @param pdqDiseaseService the pdqDiseaseService to set
     */
    public void setPdqDiseaseService(PDQDiseaseServiceLocal pdqDiseaseService) {
        this.pdqDiseaseService = pdqDiseaseService;
    }

    /**
     * @param protocolQueryResultsService the protocolQueryResultsService to set
     */
    public void setProtocolQueryResultsService(ProtocolQueryResultsServiceLocal protocolQueryResultsService) {
        this.protocolQueryResultsService = protocolQueryResultsService;
    }

    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void populateMilestoneHistory(List<StudyProtocolQueryDTO> trials)
            throws PAException {
        if (trials.isEmpty()) {
            return;
        }
        List<Long> ids = new ArrayList<Long>();
        Map<Long, List<MilestoneDTO>> map = new HashMap<Long, List<MilestoneDTO>>();
        Map<Long, String> users = CSMUserService.getInstance().getAbstractors();

        for (StudyProtocolQueryDTO dto : trials) {
            ids.add(dto.getStudyProtocolId());
        }
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session
                .createQuery("select sm.studyProtocol.id, sm.milestoneDate, sm.milestoneCode, sm.dateLastCreated, "
                        + "sm.userLastCreated.userId from "
                        + StudyMilestone.class.getSimpleName()
                        + " sm where sm.studyProtocol.id in (:ids) order by sm.studyProtocol.id, sm.milestoneDate, "
                        + "sm.id asc");

        query.setParameterList("ids", ids);
        // CHECKSTYLE:OFF
        for (Object[] row : (List<Object[]>) query.list()) {
            Long studyId = ((Long) row[0]);
            Date milestoneDate = (Date) row[1];
            MilestoneCode code = (MilestoneCode) row[2];
            Date dateCreated = (Date) row[3];
            Long userId = (Long) row[4];

            List<MilestoneDTO> mstones = map.get(studyId);
            if (mstones == null) {
                mstones = new ArrayList<MilestoneDTO>();
                map.put(studyId, mstones);
            }
            mstones.add(new MilestoneDTO(code, milestoneDate, StringUtils
                    .defaultString(users.get(userId)), dateCreated));
        }
        for (StudyProtocolQueryDTO dto : trials) {
            dto.setMilestoneHistory(map.get(dto.getStudyProtocolId()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StudyProtocolQueryDTO> getStudyProtocolByAgentNsc(String agentNsc) throws PAException {
        if (StringUtils.isBlank(agentNsc)) {
            return new ArrayList<StudyProtocolQueryDTO>();
        }
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session
                .createQuery("select distinct sp.id"
                        + " from InterventionAlternateName ian"
                        + " join ian.intervention int"
                        + " join int.plannedActivities pa"
                        + " join pa.studyProtocol sp"
                        + " join sp.otherIdentifiers soi"
                        + " where ian.name = :name and ian.nameTypeCode = 'NSC number'"
                        + "   and int.typeCode = :type"
                        + "   and sp.statusCode = :statusCode");
        query.setParameter("name", agentNsc);
        query.setParameter("type", InterventionTypeCode.DRUG);
        query.setParameter("statusCode", ActStatusCode.ACTIVE);
        return protocolQueryResultsService.getResultsLean(query.list());
    }
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StudyProtocolQueryDTO> getActiveInactiveStudyProtocolsById(Long studyProtocolId) throws PAException {
         Session session = PaHibernateUtil.getCurrentSession();
         SQLQuery query = session.createSQLQuery("select soi.study_protocol_id from "
                + "study_otheridentifiers soi where soi.extension IN "
                + "(select soi.extension from study_otheridentifiers soi where "
                + " soi.study_protocol_id = :studyProtocolId"
                + " and soi.root = '2.16.840.1.113883.3.26.4.3')");
         query.setParameter("studyProtocolId", studyProtocolId);
         return protocolQueryResultsService.getResultsLean(query.list());
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyProtocolQueryDTO> getWorkload() throws PAException {
        StudyProtocolQueryCriteria criteria = buildWorkloadCriteria();
        List<StudyProtocolQueryDTO> results = getStudyProtocolByCriteria(
                criteria, SKIP_ALTERNATE_TITLES, SKIP_LAST_UPDATER_INFO,
                SKIP_OTHER_IDENTIFIERS, SKIP_PROGRAM_CODES);
        populateMilestoneHistory(results);
        return results;
    }

    private StudyProtocolQueryCriteria buildWorkloadCriteria()
            throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setExcludeRejectProtocol(true);
        criteria.setExcludeTerminatedTrials(true);
        criteria.setStudyMilestone(Arrays.asList(lookUpService
                .getPropertyValue("dashboard.workload.milestones").split(",")));
        return criteria;
    }

    /**
     * @param lookUpService
     *            the lookUpService to set
     */
    public void setLookUpService(LookUpTableServiceRemote lookUpService) {
        this.lookUpService = lookUpService;
    }
}

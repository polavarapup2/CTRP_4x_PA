/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.CacheUtils;
import gov.nih.nci.pa.util.CacheUtils.Closure;

import java.util.List;

/**
 * Decorates {@link ProtocolQueryServiceLocal} and adds caching capability.
 * 
 * @author Denis G. Krylov
 * 
 */
public class ProtocolQueryServiceCachingDecorator implements ProtocolQueryServiceLocal {
    
    private final ProtocolQueryServiceLocal serviceLocal;

    /**
     * @param serviceLocal ProtocolQueryServiceLocal
     */
    public ProtocolQueryServiceCachingDecorator(
            ProtocolQueryServiceLocal serviceLocal) {
        this.serviceLocal = serviceLocal;
    }

    /**
     * @param criteria criteria
     * @return List<StudyProtocolQueryDTO> 
     * @throws PAException PAException
     * @see gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal#getStudyProtocolByCriteria
     * (gov.nih.nci.pa.dto.StudyProtocolQueryCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<StudyProtocolQueryDTO> getStudyProtocolByCriteria(
            final StudyProtocolQueryCriteria criteria) throws PAException {
        return (List<StudyProtocolQueryDTO>) CacheUtils.getFromCacheOrBackend(
                CacheUtils.getSearchResultsCache(),
                criteria.getUniqueCriteriaKey(), new Closure() {
                    public Object execute() throws PAException {
                        return serviceLocal
                                .getStudyProtocolByCriteria(criteria);
                    }
                });
    }

    /**
     * @param pSc pSc 
     * @return List<StudyProtocolQueryDTO>
     * @throws PAException PAException
     * @see gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal#getStudyProtocolByCriteriaForReporting
     * (gov.nih.nci.pa.dto.StudyProtocolQueryCriteria)
     */
    public List<StudyProtocolQueryDTO> getStudyProtocolByCriteriaForReporting(
            StudyProtocolQueryCriteria pSc) throws PAException {
        return serviceLocal.getStudyProtocolByCriteriaForReporting(pSc);
    }

    /**
     * @param criteria StudyProtocolQueryCriteria
     * @return List<StudyProtocol>
     * @throws PAException PAException
     * @see gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal#getStudyProtocolQueryResultList
     * (gov.nih.nci.pa.dto.StudyProtocolQueryCriteria)
     */
    public List<StudyProtocol> getStudyProtocolQueryResultList(
            StudyProtocolQueryCriteria criteria) throws PAException {
        return serviceLocal.getStudyProtocolQueryResultList(criteria);
    }

    /**
     * @param studyProtocolId Long
     * @return StudyProtocolQueryDTO
     * @throws PAException PAException
     * @see gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal#getTrialSummaryByStudyProtocolId(java.lang.Long)
     */
    public StudyProtocolQueryDTO getTrialSummaryByStudyProtocolId(
            Long studyProtocolId) throws PAException {
        return serviceLocal.getTrialSummaryByStudyProtocolId(studyProtocolId);
    }

    /**
     * @param orgIdentifier
     *            Long
     * @return List<StudyProtocol>
     * @throws PAException
     *             PAException
     * @see gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal#getStudyProtocolByOrgIdentifier(java.lang.Long)
     */
    public List<StudyProtocol> getStudyProtocolByOrgIdentifier(
            Long orgIdentifier) throws PAException {
        return serviceLocal.getStudyProtocolByOrgIdentifier(orgIdentifier);
    }

    /**
     * @param matchString
     *            String
     * @return List<String>
     * @throws PAException
     *             PAException
     * @see gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal#getOfficialTitles(java.lang.Long)
     */
    public List<String> getOfficialTitles(String matchString)
            throws PAException {
        return serviceLocal.getOfficialTitles(matchString);
    }

    @Override
    public void populateMilestoneHistory(List<StudyProtocolQueryDTO> trials)
            throws PAException {
        serviceLocal.populateMilestoneHistory(trials);        
    }

    @Override
    public List<StudyProtocolQueryDTO> getStudyProtocolByAgentNsc(String agentNsc) throws PAException {
        return serviceLocal.getStudyProtocolByAgentNsc(agentNsc);
    }
    
    @Override
    public List<StudyProtocolQueryDTO> getActiveInactiveStudyProtocolsById(Long studyProtocolId) throws PAException {
       return serviceLocal.getActiveInactiveStudyProtocolsById(studyProtocolId);
    }

    @Override
    public List<StudyProtocolQueryDTO> getStudyProtocolByCriteria(
            StudyProtocolQueryCriteria pSc,
            ProtocolQueryPerformanceHints... hint) throws PAException {
        return serviceLocal.getStudyProtocolByCriteria(pSc, hint);
    }

    @Override
    public List<StudyProtocolQueryDTO> getWorkload() throws PAException {
        return serviceLocal.getWorkload();
    }
}

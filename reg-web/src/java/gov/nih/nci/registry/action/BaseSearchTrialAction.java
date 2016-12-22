package gov.nih.nci.registry.action;

import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.CacheUtils;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Denis G. Krylov
 * 
 */
public class BaseSearchTrialAction extends ActionSupport implements
        ServletRequestAware, ServletResponseAware, Preparable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private HttpServletRequest request;

    private List<StudyProtocolQueryDTO> records;
    
    private ProtocolQueryServiceLocal protocolQueryService;

    private HttpServletResponse response;
    
    private Boolean studyAlternateTitlesPresent;
    
    /**
     * @param spQueryCriteria StudyProtocolQueryCriteria
     * @throws PAException PAException
     */
    protected void searchAndSort(final StudyProtocolQueryCriteria spQueryCriteria)
            throws PAException {
        // The way Search Trials screen works today is that POST means a user is executing a new search,
        // while GET means the user is paginating through results. So for POST we always hit the back-end,
        // while for GET we also look in cache for previously retrieved query results.
        // Based on Search Trials usage pattern, if more than 10 results are retrieved by initial search,
        // the user is likely to go through pages. It makes sense to cache the search results just for a little
        // while and avoid hitting the database on each page change.
        // We are not using HttpSession as cache, because it is long-lived, is specific to each user, and does not
        // handle multiple browser tabs very well. Using HttpSession would increase risk of significant memory 
        // consumption, a memory that we don't really have.
        // We are using an EhCache instance instead, which is strictly limited by a max. number of elements in memory
        // and TTL. Enough to improve pagination performance.
        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            CacheUtils.removeItemFromCache(CacheUtils.getSearchResultsCache(), spQueryCriteria.getUniqueCriteriaKey());
        }        
        records = protocolQueryService
                .getStudyProtocolByCriteria(spQueryCriteria);
        if (CollectionUtils.isNotEmpty(records)) {            
            Collections.sort(records, new Comparator<StudyProtocolQueryDTO>() {
                public int compare(StudyProtocolQueryDTO o1, StudyProtocolQueryDTO o2) {
                    return StringUtils.defaultString(o2.getNciIdentifier()).compareTo(
                            StringUtils.defaultString(o1.getNciIdentifier()));
                }
            });              
            for (StudyProtocolQueryDTO record : records) {
                if (CollectionUtils.isNotEmpty(record.getStudyAlternateTitles())) {
                    studyAlternateTitlesPresent = true;
                }
            }
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(
     * javax.servlet.http.HttpServletRequest)
     */
    @Override
    public final void setServletRequest(HttpServletRequest r) {
        this.request = r;
    }
    
    /**
     * @return HttpServletRequest
     */
    public final HttpServletRequest getServletRequest() {
        return request;
    }
    
    /**
     * 
     * @return records
     */
    public List<StudyProtocolQueryDTO> getRecords() {
        return records;
    }
    
    /**
     * @param records records
     */
    public void setRecords(List<StudyProtocolQueryDTO> records) {
        this.records = records;
    }

    @Override
    public void prepare() {
        protocolQueryService = PaRegistry.getCachingProtocolQueryService();
    }
    
    /**
     * @return ProtocolQueryServiceLocal
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }

    @Override
    public void setServletResponse(HttpServletResponse r) {
        this.response = r;
    }
    
    /**
     * @return HttpServletResponse
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * @param protocolQueryService the protocolQueryService to set
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @return studyAlternateTitlesPresent
     */
    public Boolean getStudyAlternateTitlesPresent() {
        return studyAlternateTitlesPresent;
    }

    /**
     * @param studyAlternateTitlesPresent studyAlternateTitlesPresent
     */
    public void setStudyAlternateTitlesPresent(Boolean studyAlternateTitlesPresent) {
        this.studyAlternateTitlesPresent = studyAlternateTitlesPresent;
    }

}

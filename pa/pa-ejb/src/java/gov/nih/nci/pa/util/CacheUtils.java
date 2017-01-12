/**
 * 
 */
package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.PAException;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Methods to interact with EhCache-backed cache.
 * 
 * @author Denis G. Krylov
 * 
 */
public final class CacheUtils {

    private static final CacheManager CACHE_MANAGER = CacheManager.create();

    private static final String CRITERIA_COLLECTIONS_CACHE_KEY = "CRITERIA_COLLECTIONS_CACHE";
    private static final String SEARCH_RESULTS_CACHE_KEY = "SEARCH_RESULTS_CACHE";
    private static final String SUBMITTER_REGISTRY_USERS_KEY = "SUBMITTER_REGISTRY_USERS";
    private static final String CADSR_CLASSIFICATION_SCHEMES_KEY = "CADSR_CLASSIFICATION_SCHEMES";
    private static final String VIEW_PARTICIPATING_SITES_CACHE_KEY = "VIEW_PARTICIPATING_SITES_CACHE";
    private static final String CTEP_ORGANIZATIONS_CACHE_KEY = "CTEP_ORGANIZATIONS_CACHE";
    private static final String ORGANIZATIONS_FAMILY_CACHE_KEY = "ORGANIZATION_FAMILY_CACHE";
    private static final String PA_PROPS_CACHE_KEY = "PA_PROPS_CACHE";
    private static final String STATUS_RULES_CACHE_KEY = "STATUS_RULES_CACHE";
    private static final String USER_BY_LOGIN_CACHE_KEY = "USER_BY_LOGIN_CACHE";
    private static final String TSR_SITES_CACHE_KEY = "TSR_SITES_CACHE";    
    private static final String TSR_CACHE_KEY = "TSR_CACHE";
    private static final String STUDY_SITE_CONTACTS_CACHE_KEY = "STUDY_SITE_CONTACTS_CACHE";
    private static final String REPORTING_RESULTS_CACHE_KEY = "REPORTING_RESULTS_CACHE_KEY";
    private static final String BIZ_DAY_CACHE_KEY = "BIZ_DAY_CACHE_KEY";
    private static final String RO_BY_ORG_ID_CACHE_KEY = "RO_BY_ORG_ID_CACHE_KEY";
    
    /**
     * @return RO_BY_ORG_ID_CACHE_KEY
     */
    public static Cache getPoResearchOrganizationByEntityIdentifierCache() {
        return CACHE_MANAGER.getCache(RO_BY_ORG_ID_CACHE_KEY);
    }
    
    /**
     * @return BIZ_DAY_CACHE
     */
    public static Cache getBizDayCache() {
        return CACHE_MANAGER.getCache(BIZ_DAY_CACHE_KEY);
    }

    /**
     * @return TSR_CACHE
     */
    public static Cache getTSRCache() {
        return CACHE_MANAGER.getCache(TSR_CACHE_KEY);
    }

    /**
     * @return STUDY_SITE_CONTACTS_CACHE
     */
    public static Cache getStudySiteContactsCache() {
        return CACHE_MANAGER.getCache(STUDY_SITE_CONTACTS_CACHE_KEY);
    }
    
    /**
     * Cache used for storing {@link TSRReportParticipatingSite} objects used in
     * TSR.
     * 
     * @return Cache
     */
    public static Cache getTSRSitesCache() {
        return CACHE_MANAGER.getCache(TSR_SITES_CACHE_KEY);
    }
    
    /**
     * Cache used for storing {@link User} objects by login names.
     * 
     * @return Cache
     */
    public static Cache getUserByLoginCache() {
        return CACHE_MANAGER.getCache(USER_BY_LOGIN_CACHE_KEY);
    }

    /**
     * Cache used for storing criteria's referenced collections, usually Lead
     * Orgs and Investigators on Search Trial UI.
     * 
     * @return Cache
     */
    public static Cache getCriteriaCollectionsCache() {
        return CACHE_MANAGER.getCache(CRITERIA_COLLECTIONS_CACHE_KEY);

    }
    
    /**
     * Cache used for storing CTEP ID's of select organizations, mostly for the
     * organization dropdowns.
     * 
     * @return Cache
     */
    public static Cache getOrganizationCtepIdCache() {
        return CACHE_MANAGER.getCache(CTEP_ORGANIZATIONS_CACHE_KEY);

    }
    
    /**
     * Cache used for storing participating sites for "View Participating Sites" operation
     * in Registry.
     * 
     * @return Cache
     */
    public static Cache getViewParticipatingSitesCache() {
        return CACHE_MANAGER.getCache(VIEW_PARTICIPATING_SITES_CACHE_KEY);

    }

    /**
     * Cache used for storing search results, usually Trial Search results.
     * 
     * @return Cache
     */
    public static Cache getSearchResultsCache() {
        return CACHE_MANAGER.getCache(SEARCH_RESULTS_CACHE_KEY);
    }
    
    /**
     * Cache used for storing Submitters.
     * 
     * @return Cache
     */
    public static Cache getSubmittersCache() {
        return CACHE_MANAGER.getCache(SUBMITTER_REGISTRY_USERS_KEY);
    }
    
    /**
     * Cache used for storing caDSR classification schemes.
     * 
     * @return Cache
     */
    public static Cache getCaDSRClassificationSchemesCache() {
        return CACHE_MANAGER.getCache(CADSR_CLASSIFICATION_SCHEMES_KEY);
    }
    
    /**
     * Cache used for storing the related orgs to one..
     * 
     * @return Cache
     */
    public static Cache getOrganizationFamilyCache() {
        return CACHE_MANAGER.getCache(ORGANIZATIONS_FAMILY_CACHE_KEY);
    }
    
    /**
     * Cache used for storing some of the PA properties
     * 
     * @return Cache
     */
    public static Cache getPAPropertiesCache() {
        return CACHE_MANAGER.getCache(PA_PROPS_CACHE_KEY);
    }
    
    /**
     * Cache used for storing the status rules
     * 
     * @return Cache
     */
    public static Cache getStatusRulesCache() {
        return CACHE_MANAGER.getCache(STATUS_RULES_CACHE_KEY);
    }

    /**
     * Cache used for storing the study reporting results
     *
     * @return Cache
     */
    public static Cache getReportingResultsCache() {
        return CACHE_MANAGER.getCache(REPORTING_RESULTS_CACHE_KEY);
    }

    /**
     * Attempts to find the given item in the given cache. If not found,
     * retrieves the item from the backend using the given {@link Closure} and
     * stores the result in the cache.
     * 
     * @param cache
     *            Cache
     * @param elementKey
     *            elementKey
     * @param backendAccessClosure
     *            Closure
     * @return Object
     * @throws PAException PAException
     */
    public static Object getFromCacheOrBackend(Cache cache, String elementKey,
            Closure backendAccessClosure) throws PAException {
        Element element = cache.get((Object) elementKey);
        if (element != null) {
            Object cachedObj = element.getObjectValue();
            if (cachedObj != null) {
                return cachedObj;
            }
        }
        Object result = backendAccessClosure.execute();
        if (result != null) {
            element = new Element(elementKey, result);
            cache.put(element);
        }
        return result;

    }
    
    /**
     * Removes item from cache.
     * 
     * @param cache
     *            Cache
     * @param elementKey
     *            elementKey
     */
    public static void removeItemFromCache(Cache cache, String elementKey) {
        cache.remove((Object) elementKey);
    }

    /**
     * Closure used in
     * {@link CacheUtils#getFromCacheOrBackend(Cache, String, Closure)}.
     * 
     * @author Denis G. Krylov
     * 
     */
    public interface Closure {
        /**
         * Executes and results a result.
         * 
         * @return Object result.
         * @throws PAException
         *             PAException
         */
        Object execute() throws PAException;
    }

}

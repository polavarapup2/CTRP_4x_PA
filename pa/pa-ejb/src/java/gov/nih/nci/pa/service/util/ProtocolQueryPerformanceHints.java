/**
 * 
 */
package gov.nih.nci.pa.service.util;

/**
 * Hints for optimizing protocol search.
 * 
 * @author dkrylov
 * 
 */
public enum ProtocolQueryPerformanceHints {

    /**
     * Tells the search bean to skip querying for other identifiers.
     */
    SKIP_OTHER_IDENTIFIERS, /**
     * Tells the search bean to skip querying for
     * alternate titles.
     */
    SKIP_ALTERNATE_TITLES, /**
     * Tells the search bean to skip querying for last
     * updater info.
     */
    SKIP_LAST_UPDATER_INFO,
    /** Tells the search bean to skip querying for program codes
     */
    SKIP_PROGRAM_CODES;

}

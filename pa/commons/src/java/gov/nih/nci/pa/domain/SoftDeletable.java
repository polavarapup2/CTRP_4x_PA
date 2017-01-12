/**
 * 
 */
package gov.nih.nci.pa.domain;

/**
 * Indicates an entity can be soft-deleted.
 * 
 * @author dkrylov
 * 
 */
public interface SoftDeletable {

    /**
     * Delete indicator.
     * 
     * @return Boolean
     */
    Boolean getDeleted();

}

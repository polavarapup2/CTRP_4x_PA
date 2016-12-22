/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.service.PAException;

import java.util.Collection;

import javax.ejb.Local;

/**
 * Performs comparison of protocol data at different points in time. Useful for
 * tracking changes between protocol updates in a user-friendly manner.
 * 
 * @author Denis G. Krylov
 * 
 */
@Local
public interface ProtocolComparisonServiceLocal {

    /**
     * Contains a snapshot of a protocol at some point in time. Marker interface
     * at this point.
     * 
     * @author Denis G. Krylov
     * 
     */
    public interface ProtocolSnapshot {
    }

    /**
     * Represents a difference in a single field between two snapshots of a
     * protocol. Differences have field key, old value, and new value.
     * 
     * @author Denis G. Krylov
     * 
     */
    public interface Difference {

        /**
         * @return key of the field that differs
         */
        String getFieldKey();

        /**
         * @return old value
         */
        Object getOldValue();

        /**
         * @return new value
         */
        Object getNewValue();

    }

    /**
     * Captures a protocol data snapshot at the given point in time.
     * 
     * @param protocolID
     *            Long
     * @return ProtocolSnapshot
     * @throws PAException
     *             PAException
     */
    ProtocolSnapshot captureSnapshot(Long protocolID) throws PAException;

    /**
     * Runs a comparison of two protocol snapshots using the supplied list of
     * OGNL expressions to determine which properties to compare. Typically, the
     * list of OGNL expressions will come from a pa_properties table. The list
     * is dynamic so that CTRO could modify the criteria that determines which
     * trials need review on the fly without changing the code.
     * 
     * @param ognlExpr
     *            OGNL expressions
     * @param before
     *            ProtocolSnapshot
     * @param after
     *            ProtocolSnapshot
     * @return Collection<Difference>
     * @throws PAException
     *             PAException
     */
    Collection<Difference> compare(ProtocolSnapshot before,
            ProtocolSnapshot after, Collection<String> ognlExpr)
            throws PAException;

}

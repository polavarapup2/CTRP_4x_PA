/**
 * 
 */
package gov.nih.nci.pa.lov;



import java.io.Serializable;

/**
 * Interface for database-driven list of value classes, such as
 * {@link PrimaryPurposeCode}.
 * 
 * @author Denis G. Krylov
 * 
 */
public interface Lov extends Serializable {

    /**
     * Code, as in {@link CodedEnum}.
     * @return code
     */
    String getCode();

    /**
     * Name, as in {@link CodedEnum}.
     * @return name
     */
    String getName();

    /**
     * Display value.
     * @return String
     */
    String getDisplayName();

}

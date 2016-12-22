/**
 * 
 */
package gov.nih.nci.registry.util;

import gov.nih.nci.pa.dto.PaOrganizationDTO;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * I needed {@link PaOrganizationDTO} with Comparable and
 * {@link Object#equals(Object)} properly implemented. However, changing
 * {@link PaOrganizationDTO} to override and implement
 * {@link Object#equals(Object)} would result in a significant risk of breaking
 * backward compatibility and having cascading effect on the existent code.
 * Hence, a sub-class.
 * 
 * @author Denis G. Krylov
 * 
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
// Eclpse-generated code.
public final class ComparableOrganizationDTO extends PaOrganizationDTO
        implements Comparable<ComparableOrganizationDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 923193261090332029L;
    
    /**
     * Constructor.
     */
    public ComparableOrganizationDTO() {    
        // Default
    }

    /**
     * @param dto
     *            PaOrganizationDTO
     * @throws InvocationTargetException
     *             InvocationTargetException
     * @throws IllegalAccessException
     *             IllegalAccessException
     */
    public ComparableOrganizationDTO(PaOrganizationDTO dto)
            throws IllegalAccessException, InvocationTargetException {
        BeanUtils.copyProperties(this, dto);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) { // NOPMD Eclipse-generated.
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PaOrganizationDTO)) {
            return false;
        }
        PaOrganizationDTO other = (PaOrganizationDTO) obj;
        if (getId() == null || other.getId() == null) {
                return false;

        } else if (!getId().equals(other.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(ComparableOrganizationDTO o) {
        return StringUtils
                .trim(StringUtils.defaultString(getName()))
                .compareTo(
                        StringUtils.trim(StringUtils.defaultString(o.getName())));
    }

}

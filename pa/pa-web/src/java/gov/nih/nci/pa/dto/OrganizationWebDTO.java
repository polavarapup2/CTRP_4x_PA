/**
 * 
 */
package gov.nih.nci.pa.dto;

import java.io.Serializable;

/**
 * @author Denis G. Krylov
 * 
 */
public class OrganizationWebDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3038177375773145589L;

    private String poId;
    private String name;

    /**
     * @return the poId
     */
    public String getPoId() {
        return poId;
    }

    /**
     * @param poId
     *            the poId to set
     */
    public void setPoId(String poId) {
        this.poId = poId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrganizationWebDTO [poId=").append(poId)
                .append(", name=").append(name).append("]");
        return builder.toString();
    }

}

package gov.nih.nci.registry.dto;

import org.hibernate.validator.NotEmpty;

/**
 * 
 * @author Vrushali
 *
 */
public class OrganizationBatchDTO extends AddressDTO {
    private static final long serialVersionUID = -1007617488248050075L;
    private String poIdentifier;
    private String name;
    private String type;
    private static final int ORG_NAME_MAX_LENGTH = 160;
    
    /**
     * @return the po identifier
     */
    public String getPoIdentifier() {
        return poIdentifier;
    }
    /**
     * 
     * @param poIdentifier identifier of the entity
     */
    public void setPoIdentifier(String poIdentifier) {
        this.poIdentifier = poIdentifier;
    }
    /**
     * @return the name
     */
    @org.hibernate.validator.Length(message = "(fieldName) Name must be 160 characters max.\n", 
            max = ORG_NAME_MAX_LENGTH)
    @NotEmpty(message = "(fieldName) Name is required.\n")
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
   
    
}

package gov.nih.nci.pa.webservices.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 
 * @author Reshma
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArmDTO {
    private String name;
    private String typeCode;
    private String descriptionText;
    //private DSet<Ii> interventions; //TODO: will be added if needed
    

    /**
     * const
     */
    public ArmDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param name name
     * @param typeCode typeCode
     * @param descriptionText descriptionText
     */
    public ArmDTO(String name, String typeCode, String descriptionText) {
        super();
        this.name = name;
        this.typeCode = typeCode;
        this.descriptionText = descriptionText;
    }
    
    /**
     * 
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * 
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 
     * @return typeCode
     */
    public String getTypeCode() {
        return typeCode;
    }
    /** 
     * 
     * @param typeCode the typeCode
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    /**
     * 
     * @return descriptionText
     */
    public String getDescriptionText() {
        return descriptionText;
    }
    /** 
     * 
     * @param descriptionText the descriptionText
     */
    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }
}


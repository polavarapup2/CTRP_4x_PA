package gov.nih.nci.pa.webservices.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 
 * @author Reshma
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class ArmDTO {
    private String name;
    private String typeCode;
    private String descriptionText;
    private Long id;
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
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((descriptionText == null) ? 0 : descriptionText.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((typeCode == null) ? 0 : typeCode.hashCode());
        return result;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ArmDTO other = (ArmDTO) obj;
        if (descriptionText == null) {
            if (other.descriptionText != null) {
                return false;
            }
        } else if (!descriptionText.equals(other.descriptionText)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (typeCode == null) {
            if (other.typeCode != null) {
                return false;
            }
        } else if (!typeCode.equals(other.typeCode)) {
            return false;
        }
        return true;
    }
   
}


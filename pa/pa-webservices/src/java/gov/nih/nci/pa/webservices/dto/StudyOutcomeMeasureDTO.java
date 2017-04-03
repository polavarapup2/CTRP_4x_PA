package gov.nih.nci.pa.webservices.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * Created by chandrasekaranp on 3/21/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyOutcomeMeasureDTO {

    private String name;
    private String description;
    private String timeFrame;
    private boolean primaryIndicator;
    private boolean safetyIndicator;
    private Integer displayOrder;
    private String typeCode;
    /**
     * const
     */
    public StudyOutcomeMeasureDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param name name
     * @param description description
     * @param timeFrame timeFrame
     * @param primaryIndicator primaryIndicator
     * @param safetyIndicator safetyIndicator
     * @param displayOrder displayOrder
     * @param typeCode typeCode
     */
    public StudyOutcomeMeasureDTO(String name, String description, // NOPMD
            String timeFrame, boolean primaryIndicator,
            boolean safetyIndicator, Integer displayOrder, String typeCode) {
        super();
        this.name = name;
        this.description = description;
        this.timeFrame = timeFrame;
        this.primaryIndicator = primaryIndicator;
        this.safetyIndicator = safetyIndicator;
        this.displayOrder = displayOrder;
        this.typeCode = typeCode;
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
     * @return description
     */
    public String getDescription() {
        return description;
    }
    /**
     * 
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * 
     * @return timeFrame
     */
    public String getTimeFrame() {
        return timeFrame;
    }
    /**
     * 
     * @param timeFrame the timeFrame
     */
    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }
    /**
     * 
     * @return primaryIndicator
     */
    public boolean isPrimaryIndicator() {
        return primaryIndicator;
    }
    /**
     * 
     * @param primaryIndicator the primaryIndicator
     */
    public void setPrimaryIndicator(boolean primaryIndicator) {
        this.primaryIndicator = primaryIndicator;
    }
    /**
     * 
     * @return safetyIndicator
     */
    public boolean isSafetyIndicator() {
        return safetyIndicator;
    }
    /**
     * 
     * @param safetyIndicator the safetyIndicator
     */
    public void setSafetyIndicator(boolean safetyIndicator) {
        this.safetyIndicator = safetyIndicator;
    }
    /**
     * 
     * @return displayOrder
     */
    public Integer getDisplayOrder() {
        return displayOrder;
    }
    /**
     * 
     * @param displayOrder the displayOrder
     */
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
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
}

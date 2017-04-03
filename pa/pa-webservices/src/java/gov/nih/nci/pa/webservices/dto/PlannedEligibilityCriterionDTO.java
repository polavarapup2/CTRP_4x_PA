package gov.nih.nci.pa.webservices.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * Created by chandrasekaranp on 3/21/17.
 */
@SuppressWarnings({ "PMD.TooManyFields", "PMD.ExcessiveClassLength" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlannedEligibilityCriterionDTO {

    private boolean inclusionIndicator;
    private String criterionName;
    private String operator;
    private AgeDTO minValue;
    private AgeDTO maxValue;
    private String eligibleGenderCode;
    private Integer displayOrder;
    private boolean structuredIndicator;
    private String textValue;
    private Long cdePublicIdentifier;
    private String cdeVersionNumber;
    private String categoryCode;
    private Long interventionIdentifier;
    private boolean leadProductIndicator;
    private String textDescription;
    private String userLastCreated;
    /**
     * const
     */
    public PlannedEligibilityCriterionDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
  //CHECKSTYLE:OFF
    /**
     * 
     * @param inclusionIndicator inclusionIndicator
     * @param criterionName criterionName
     * @param operator operator
     * @param minValue minValue
     * @param maxValue maxValue
     * @param eligibleGenderCode eligibleGenderCode
     * @param displayOrder displayOrder
     * @param structuredIndicator structuredIndicator
     * @param textValue textValue
     * @param cdePublicIdentifier cdePublicIdentifier
     * @param cdeVersionNumber cdeVersionNumber
     * @param categoryCode categoryCode
     * @param interventionIdentifier interventionIdentifier
     * @param leadProductIndicator leadProductIndicator
     * @param textDescription textDescription
     * @param userLastCreated userLastCreated
     */
    public PlannedEligibilityCriterionDTO(boolean inclusionIndicator,// NOPMD
            String criterionName, String operator, AgeDTO minValue,
            AgeDTO maxValue, String eligibleGenderCode, Integer displayOrder,
            boolean structuredIndicator, String textValue,
            Long cdePublicIdentifier, String cdeVersionNumber,
            String categoryCode, Long interventionIdentifier,
            boolean leadProductIndicator, String textDescription,
            String userLastCreated) {
        super();
        this.inclusionIndicator = inclusionIndicator;
        this.criterionName = criterionName;
        this.operator = operator;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.eligibleGenderCode = eligibleGenderCode;
        this.displayOrder = displayOrder;
        this.structuredIndicator = structuredIndicator;
        this.textValue = textValue;
        this.cdePublicIdentifier = cdePublicIdentifier;
        this.cdeVersionNumber = cdeVersionNumber;
        this.categoryCode = categoryCode;
        this.interventionIdentifier = interventionIdentifier;
        this.leadProductIndicator = leadProductIndicator;
        this.textDescription = textDescription;
        this.userLastCreated = userLastCreated;
    }
  //CHECKSTYLE:ON
    /**
     * 
     * @return inclusionIndicator
     */
    public boolean isInclusionIndicator() {
        return inclusionIndicator;
    }
    /**
     * 
     * @param inclusionIndicator the inclusionIndicator
     */
    public void setInclusionIndicator(boolean inclusionIndicator) {
        this.inclusionIndicator = inclusionIndicator;
    }
    /**
     * 
     * @return criterionName
     */
    public String getCriterionName() {
        return criterionName;
    }
    /**
     * 
     * @param criterionName the criterionName
     */
    public void setCriterionName(String criterionName) {
        this.criterionName = criterionName;
    }
    /**
     * 
     * @return operator
     */
    public String getOperator() {
        return operator;
    }
    /**
     * 
     * @param operator the operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
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
     * @param displayOrder displayOrder
     */
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
    /**
     * 
     * @return structuredIndicator
     */
    public boolean isStructuredIndicator() {
        return structuredIndicator;
    }
    /**
     * 
     * @param structuredIndicator the structuredIndicator
     */
    public void setStructuredIndicator(boolean structuredIndicator) {
        this.structuredIndicator = structuredIndicator;
    }
    /**
     * 
     * @return textValue
     */
    public String getTextValue() {
        return textValue;
    }
    /**
     * 
     * @param textValue the textValue
     */
    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }
    /**
     * 
     * @return cdePublicIdentifier
     */
    public Long getCdePublicIdentifier() {
        return cdePublicIdentifier;
    }
    /**
     * 
     * @param cdePublicIdentifier cdePublicIdentifier
     */
    public void setCdePublicIdentifier(Long cdePublicIdentifier) {
        this.cdePublicIdentifier = cdePublicIdentifier;
    }
    /**
     * 
     * @return cdeVersionNumber
     */
    public String getCdeVersionNumber() {
        return cdeVersionNumber;
    }
    /**
     * 
     * @param cdeVersionNumber cdeVersionNumber
     */
    public void setCdeVersionNumber(String cdeVersionNumber) {
        this.cdeVersionNumber = cdeVersionNumber;
    }
    /**
     * 
     * @return interventionIdentifier
     */
    public Long getInterventionIdentifier() {
        return interventionIdentifier;
    }
    /**
     * 
     * @param interventionIdentifier the interventionIdentifier
     */
    public void setInterventionIdentifier(Long interventionIdentifier) {
        this.interventionIdentifier = interventionIdentifier;
    }
    /**
     * 
     * @return leadProductIndicator
     */
    public boolean isLeadProductIndicator() {
        return leadProductIndicator;
    }
    /**
     * 
     * @param leadProductIndicator the leadProductIndicator
     */
    public void setLeadProductIndicator(boolean leadProductIndicator) {
        this.leadProductIndicator = leadProductIndicator;
    }
    /**
     * 
     * @return textDescription
     */
    public String getTextDescription() {
        return textDescription;
    }
    /**
     * 
     * @param textDescription  the textDescription
     */
    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }
    /**
     * 
     * @return userLastCreated
     */
    public String getUserLastCreated() {
        return userLastCreated;
    }
    /**
     * 
     * @param userLastCreated the userLastCreated
     */
    public void setUserLastCreated(String userLastCreated) {
        this.userLastCreated = userLastCreated;
    }
    /**
     * 
     * @return categoryCode
     */
    public String getCategoryCode() {
        return categoryCode;
    }
    /**
     * 
     * @param categoryCode the categoryCode
     */
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
    /**
     * 
     * @return eligibleGenderCode
     */
    public String getEligibleGenderCode() {
        return eligibleGenderCode;
    }
    /**
     * 
     * @param eligibleGenderCode the eligibleGenderCode
     */
    public void setEligibleGenderCode(String eligibleGenderCode) {
        this.eligibleGenderCode = eligibleGenderCode;
    }
    /**
     * 
     * @return minValue
     */
    public AgeDTO getMinValue() {
        return minValue;
    }
    /***
     * 
     * @param minValue the minValue
     */
    public void setMinValue(AgeDTO minValue) {
        this.minValue = minValue;
    }
    /**
     * 
     * @return maxValue
     */
    public AgeDTO getMaxValue() {
        return maxValue;
    }
/**
 * 
 * @param maxValue the maxValue
 */
    public void setMaxValue(AgeDTO maxValue) {
        this.maxValue = maxValue;
    }
}

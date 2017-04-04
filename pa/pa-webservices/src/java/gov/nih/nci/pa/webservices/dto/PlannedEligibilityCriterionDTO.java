package gov.nih.nci.pa.webservices.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * Created by chandrasekaranp on 3/21/17.
 */
@SuppressWarnings({ "PMD.TooManyFields", "PMD.ExcessiveClassLength", 
    "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
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
    private Long identifier;
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
     * @param identifier identifier
     */
    public PlannedEligibilityCriterionDTO(boolean inclusionIndicator,// NOPMD
            String criterionName, String operator, AgeDTO minValue,
            AgeDTO maxValue, String eligibleGenderCode, Integer displayOrder,
            boolean structuredIndicator, String textValue,
            Long cdePublicIdentifier, String cdeVersionNumber,
            String categoryCode, Long interventionIdentifier,
            boolean leadProductIndicator, String textDescription,
            String userLastCreated, Long identifier) {
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
        this.identifier = identifier;
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
    /**
     * @return the identifier
     */
    public Long getIdentifier() {
    return identifier;
    }
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }
    //CHECKSTYLE:OFF
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    /**
     * @return int 
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((categoryCode == null) ? 0 : categoryCode.hashCode());
        result = prime
                * result
                + ((cdePublicIdentifier == null) ? 0 : cdePublicIdentifier
                        .hashCode());
        result = prime
                * result
                + ((cdeVersionNumber == null) ? 0 : cdeVersionNumber.hashCode());
        result = prime * result
                + ((criterionName == null) ? 0 : criterionName.hashCode());
        result = prime * result
                + ((displayOrder == null) ? 0 : displayOrder.hashCode());
        result = prime
                * result
                + ((eligibleGenderCode == null) ? 0 : eligibleGenderCode
                        .hashCode());
        result = prime * result
                + ((identifier == null) ? 0 : identifier.hashCode());
        result = prime * result + (inclusionIndicator ? 1231 : 1237);
        result = prime
                * result
                + ((interventionIdentifier == null) ? 0
                        : interventionIdentifier.hashCode());
        result = prime * result + (leadProductIndicator ? 1231 : 1237);
        result = prime * result
                + ((maxValue == null) ? 0 : maxValue.hashCode());
        result = prime * result
                + ((minValue == null) ? 0 : minValue.hashCode());
        result = prime * result
                + ((operator == null) ? 0 : operator.hashCode());
        result = prime * result + (structuredIndicator ? 1231 : 1237);
        result = prime * result
                + ((textDescription == null) ? 0 : textDescription.hashCode());
        result = prime * result
                + ((textValue == null) ? 0 : textValue.hashCode());
        result = prime * result
                + ((userLastCreated == null) ? 0 : userLastCreated.hashCode());
        return result;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    /**
     * @param obj obj
     * @return boolean
     */
  //CHECKSTYLE:OFF
    @SuppressWarnings({ "PMD.ExcessiveMethodLength" })
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
        PlannedEligibilityCriterionDTO other = (PlannedEligibilityCriterionDTO) obj;
        if (categoryCode == null) {
            if (other.categoryCode != null) {
                return false;
            }
        } else if (!categoryCode.equals(other.categoryCode)) {
            return false;
        }
        if (cdePublicIdentifier == null) {
            if (other.cdePublicIdentifier != null) {
                return false;
            }
        } else if (!cdePublicIdentifier.equals(other.cdePublicIdentifier)) {
            return false;
        }
        if (cdeVersionNumber == null) {
            if (other.cdeVersionNumber != null) {
                return false;
            }
        } else if (!cdeVersionNumber.equals(other.cdeVersionNumber)) {
            return false;
        }
        if (criterionName == null) {
            if (other.criterionName != null) {
                return false;
            }
        } else if (!criterionName.equals(other.criterionName)) {
            return false;
        }
        if (displayOrder == null) {
            if (other.displayOrder != null) {
                return false;
            }
        } else if (!displayOrder.equals(other.displayOrder)) {
            return false;
        }
        if (eligibleGenderCode == null) {
            if (other.eligibleGenderCode != null) {
                return false;
            }
        } else if (!eligibleGenderCode.equals(other.eligibleGenderCode)) {
            return false;
        }
        if (identifier == null) {
            if (other.identifier != null) {
                return false;
            }
        } else if (!identifier.equals(other.identifier)) {
            return false;
        }
        if (inclusionIndicator != other.inclusionIndicator) {
            return false;
        }
        if (interventionIdentifier == null) {
            if (other.interventionIdentifier != null) {
                return false;
            }
        } else if (!interventionIdentifier.equals(other.interventionIdentifier)) {
            return false;
        }
        if (leadProductIndicator != other.leadProductIndicator) {
            return false;
        }
        if (maxValue == null) {
            if (other.maxValue != null) {
                return false;
            }
        } else if (!maxValue.equals(other.maxValue)) {
            return false;
        }
        if (minValue == null) {
            if (other.minValue != null) {
                return false;
            }
        } else if (!minValue.equals(other.minValue)) {
            return false;
        }
        if (operator == null) {
            if (other.operator != null) {
                return false;
            }
        } else if (!operator.equals(other.operator)) {
            return false;
        }
        if (structuredIndicator != other.structuredIndicator) {
            return false;
        }
        if (textDescription == null) {
            if (other.textDescription != null) {
                return false;
            }
        } else if (!textDescription.equals(other.textDescription)) {
            return false;
        }
        if (textValue == null) {
            if (other.textValue != null) {
                return false;
            }
        } else if (!textValue.equals(other.textValue)) {
            return false;
        }
        if (userLastCreated == null) {
            if (other.userLastCreated != null) {
                return false;
            }
        } else if (!userLastCreated.equals(other.userLastCreated)) {
            return false;
        }
        return true;
    }
  //CHECKSTYLE:ON
    
}

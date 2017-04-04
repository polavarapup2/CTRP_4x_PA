package gov.nih.nci.pa.webservices.dto;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
/**
 * 
 * @author Reshma
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class AgeDTO {
    private String unitCode;
    private BigDecimal value;
    private int precision;
    
    /**
     * const
     */
    public AgeDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param unitCode unitCode
     */
    public AgeDTO(String unitCode) {
        this.unitCode = unitCode;
    }
    /**
     * 
     * @param unitCode unitCode
     * @param value value
     * @param precision precision
     */
    public AgeDTO(String unitCode, BigDecimal value, int precision) {
        super();
        this.unitCode = unitCode;
        this.value = value;
        this.precision = precision;
    }
    /**
     * 
     * @return unitCode
     */
    public String getUnitCode() {
        return unitCode;
    }
    /**
     * 
     * @param unitCode the unitCode
     */
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
    /**
     * 
     * @return value
     */
    public BigDecimal getValue() {
        return value;
    }
    /**
     * 
     * @param value value
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }
    /**
     * 
     * @return precision
     */
    public int getPrecision() {
        return precision;
    }
    /**
     * 
     * @param precision precision
     */
    public void setPrecision(int precision) {
        this.precision = precision;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + precision;
        result = prime * result
                + ((unitCode == null) ? 0 : unitCode.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        AgeDTO other = (AgeDTO) obj;
        if (precision != other.precision) {
            return false;
        }
        if (unitCode == null) {
            if (other.unitCode != null) {
                return false;
            }
        } else if (!unitCode.equals(other.unitCode)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }
    
}

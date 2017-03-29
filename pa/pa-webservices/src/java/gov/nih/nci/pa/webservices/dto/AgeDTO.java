package gov.nih.nci.pa.webservices.dto;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
/**
 * 
 * @author Reshma
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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
}

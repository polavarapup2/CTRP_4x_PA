//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.11 at 01:47:08 PM EST 
//


package gov.nih.nci.pa.webservices.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Captures information about a grant that a trial is
 * 				funded with.
 * 			
 * 
 * <p>Java class for Grant complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Grant">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fundingMechanism">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[A-Z0-9]{3}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="nihInstitutionCode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[A-Z]{2}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="serialNumber">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[0-9]{1,200}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="nciDivisionProgramCode" type="{gov.nih.nci.pa.webservices.types}NciDivisionProgramCode"/>
 *         &lt;element name="fundingPercentage" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Grant", propOrder = {
    "fundingMechanism",
    "nihInstitutionCode",
    "serialNumber",
    "nciDivisionProgramCode",
    "fundingPercentage"
})
public class Grant {

    @XmlElement(required = true)
    protected String fundingMechanism;
    @XmlElement(required = true)
    protected String nihInstitutionCode;
    @XmlElement(required = true)
    protected String serialNumber;
    @XmlElement(required = true)
    protected NciDivisionProgramCode nciDivisionProgramCode;
    protected Float fundingPercentage;

    /**
     * Gets the value of the fundingMechanism property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFundingMechanism() {
        return fundingMechanism;
    }

    /**
     * Sets the value of the fundingMechanism property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFundingMechanism(String value) {
        this.fundingMechanism = value;
    }

    /**
     * Gets the value of the nihInstitutionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNihInstitutionCode() {
        return nihInstitutionCode;
    }

    /**
     * Sets the value of the nihInstitutionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNihInstitutionCode(String value) {
        this.nihInstitutionCode = value;
    }

    /**
     * Gets the value of the serialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the value of the serialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNumber(String value) {
        this.serialNumber = value;
    }

    /**
     * Gets the value of the nciDivisionProgramCode property.
     * 
     * @return
     *     possible object is
     *     {@link NciDivisionProgramCode }
     *     
     */
    public NciDivisionProgramCode getNciDivisionProgramCode() {
        return nciDivisionProgramCode;
    }

    /**
     * Sets the value of the nciDivisionProgramCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link NciDivisionProgramCode }
     *     
     */
    public void setNciDivisionProgramCode(NciDivisionProgramCode value) {
        this.nciDivisionProgramCode = value;
    }

    /**
     * Gets the value of the fundingPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getFundingPercentage() {
        return fundingPercentage;
    }

    /**
     * Sets the value of the fundingPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setFundingPercentage(Float value) {
        this.fundingPercentage = value;
    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.07 at 03:16:32 PM EST 
//


package gov.nih.nci.pa.webservices.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Provides a trial's responsible party details. Only
 * 				applicable to trials that
 * 				require uploads to ClinicalTrials.gov.
 * 			
 * 
 * <p>Java class for ResponsibleParty complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResponsibleParty">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="type" type="{gov.nih.nci.pa.webservices.types}ResponsiblePartyType"/>
 *         &lt;element name="investigator" type="{gov.nih.nci.pa.webservices.types}Person" minOccurs="0"/>
 *         &lt;element name="investigatorTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="investigatorAffiliation" type="{gov.nih.nci.pa.webservices.types}Organization" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponsibleParty", propOrder = {
    "type",
    "investigator",
    "investigatorTitle",
    "investigatorAffiliation"
})
public class ResponsibleParty {

    @XmlElement(required = true)
    protected ResponsiblePartyType type;
    protected Person investigator;
    protected String investigatorTitle;
    protected Organization investigatorAffiliation;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ResponsiblePartyType }
     *     
     */
    public ResponsiblePartyType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponsiblePartyType }
     *     
     */
    public void setType(ResponsiblePartyType value) {
        this.type = value;
    }

    /**
     * Gets the value of the investigator property.
     * 
     * @return
     *     possible object is
     *     {@link Person }
     *     
     */
    public Person getInvestigator() {
        return investigator;
    }

    /**
     * Sets the value of the investigator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Person }
     *     
     */
    public void setInvestigator(Person value) {
        this.investigator = value;
    }

    /**
     * Gets the value of the investigatorTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvestigatorTitle() {
        return investigatorTitle;
    }

    /**
     * Sets the value of the investigatorTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvestigatorTitle(String value) {
        this.investigatorTitle = value;
    }

    /**
     * Gets the value of the investigatorAffiliation property.
     * 
     * @return
     *     possible object is
     *     {@link Organization }
     *     
     */
    public Organization getInvestigatorAffiliation() {
        return investigatorAffiliation;
    }

    /**
     * Sets the value of the investigatorAffiliation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Organization }
     *     
     */
    public void setInvestigatorAffiliation(Organization value) {
        this.investigatorAffiliation = value;
    }

}

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
 * A snapshot of basic trial information that serves as a
 * 				confirmation of a successful trial registration, update, or
 * 				amendment.
 * 			
 * 
 * <p>Java class for TrialRegistrationConfirmation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TrialRegistrationConfirmation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paTrialID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="nciTrialID">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="NCI-[0-9]{4}-[0-9]{1,8}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrialRegistrationConfirmation", propOrder = {
    "paTrialID",
    "nciTrialID"
})
public class TrialRegistrationConfirmation {

    protected long paTrialID;
    @XmlElement(required = true)
    protected String nciTrialID;

    /**
     * Gets the value of the paTrialID property.
     * 
     */
    public long getPaTrialID() {
        return paTrialID;
    }

    /**
     * Sets the value of the paTrialID property.
     * 
     */
    public void setPaTrialID(long value) {
        this.paTrialID = value;
    }

    /**
     * Gets the value of the nciTrialID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNciTrialID() {
        return nciTrialID;
    }

    /**
     * Sets the value of the nciTrialID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNciTrialID(String value) {
        this.nciTrialID = value;
    }

}

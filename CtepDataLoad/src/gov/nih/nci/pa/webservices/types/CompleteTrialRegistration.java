//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.28 at 12:41:09 PM EST 
//


package gov.nih.nci.pa.webservices.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * A request to register a new Complete trial in CTRP.
 * 			
 * 
 * <p>Java class for CompleteTrialRegistration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CompleteTrialRegistration">
 *   &lt;complexContent>
 *     &lt;extension base="{gov.nih.nci.pa.webservices.types}BaseTrialInformation">
 *       &lt;sequence>
 *         &lt;element name="category" type="{gov.nih.nci.pa.webservices.types}TrialCategory"/>
 *         &lt;element name="trialOwner" maxOccurs="unbounded" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="254"/>
 *               &lt;pattern value="[_\-a-zA-Z0-9\.\+]+@[a-zA-Z0-9](\.?[\-a-zA-Z0-9]*[a-zA-Z0-9])*"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompleteTrialRegistration", propOrder = {
    "category",
    "trialOwner"
})
public class CompleteTrialRegistration
    extends BaseTrialInformation
{

    @XmlElement(required = true)
    protected TrialCategory category;
    protected List<String> trialOwner;

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link TrialCategory }
     *     
     */
    public TrialCategory getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrialCategory }
     *     
     */
    public void setCategory(TrialCategory value) {
        this.category = value;
    }

    /**
     * Gets the value of the trialOwner property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trialOwner property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrialOwner().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTrialOwner() {
        if (trialOwner == null) {
            trialOwner = new ArrayList<String>();
        }
        return this.trialOwner;
    }

}

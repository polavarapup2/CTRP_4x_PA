//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.07 at 03:16:27 PM EST 
//


package gov.nih.nci.pa.service.ctgov;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for intervention_struct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="intervention_struct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="intervention_type" type="{}intervention_type_enum"/>
 *         &lt;element name="intervention_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arm_group_label" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="other_name" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "intervention_struct", propOrder = {
    "interventionType",
    "interventionName",
    "description",
    "armGroupLabel",
    "otherName"
})
public class InterventionStruct {

    @XmlElement(name = "intervention_type", required = true)
    protected InterventionTypeEnum interventionType;
    @XmlElement(name = "intervention_name", required = true)
    protected String interventionName;
    protected String description;
    @XmlElement(name = "arm_group_label")
    protected List<String> armGroupLabel;
    @XmlElement(name = "other_name")
    protected List<String> otherName;

    /**
     * Gets the value of the interventionType property.
     * 
     * @return
     *     possible object is
     *     {@link InterventionTypeEnum }
     *     
     */
    public InterventionTypeEnum getInterventionType() {
        return interventionType;
    }

    /**
     * Sets the value of the interventionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link InterventionTypeEnum }
     *     
     */
    public void setInterventionType(InterventionTypeEnum value) {
        this.interventionType = value;
    }

    /**
     * Gets the value of the interventionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterventionName() {
        return interventionName;
    }

    /**
     * Sets the value of the interventionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterventionName(String value) {
        this.interventionName = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the armGroupLabel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the armGroupLabel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArmGroupLabel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getArmGroupLabel() {
        if (armGroupLabel == null) {
            armGroupLabel = new ArrayList<String>();
        }
        return this.armGroupLabel;
    }

    /**
     * Gets the value of the otherName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the otherName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOtherName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOtherName() {
        if (otherName == null) {
            otherName = new ArrayList<String>();
        }
        return this.otherName;
    }

}

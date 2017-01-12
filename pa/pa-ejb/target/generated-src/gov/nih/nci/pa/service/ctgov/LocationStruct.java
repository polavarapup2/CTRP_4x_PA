//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.11 at 01:47:01 PM EST 
//


package gov.nih.nci.pa.service.ctgov;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for location_struct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="location_struct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="facility" type="{}facility_struct" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contact" type="{}contact_struct" minOccurs="0"/>
 *         &lt;element name="contact_backup" type="{}contact_struct" minOccurs="0"/>
 *         &lt;element name="investigator" type="{}investigator_struct" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "location_struct", propOrder = {
    "facility",
    "status",
    "contact",
    "contactBackup",
    "investigator"
})
public class LocationStruct {

    protected FacilityStruct facility;
    protected String status;
    protected ContactStruct contact;
    @XmlElement(name = "contact_backup")
    protected ContactStruct contactBackup;
    protected List<InvestigatorStruct> investigator;

    /**
     * Gets the value of the facility property.
     * 
     * @return
     *     possible object is
     *     {@link FacilityStruct }
     *     
     */
    public FacilityStruct getFacility() {
        return facility;
    }

    /**
     * Sets the value of the facility property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacilityStruct }
     *     
     */
    public void setFacility(FacilityStruct value) {
        this.facility = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the contact property.
     * 
     * @return
     *     possible object is
     *     {@link ContactStruct }
     *     
     */
    public ContactStruct getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactStruct }
     *     
     */
    public void setContact(ContactStruct value) {
        this.contact = value;
    }

    /**
     * Gets the value of the contactBackup property.
     * 
     * @return
     *     possible object is
     *     {@link ContactStruct }
     *     
     */
    public ContactStruct getContactBackup() {
        return contactBackup;
    }

    /**
     * Sets the value of the contactBackup property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactStruct }
     *     
     */
    public void setContactBackup(ContactStruct value) {
        this.contactBackup = value;
    }

    /**
     * Gets the value of the investigator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the investigator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvestigator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvestigatorStruct }
     * 
     * 
     */
    public List<InvestigatorStruct> getInvestigator() {
        if (investigator == null) {
            investigator = new ArrayList<InvestigatorStruct>();
        }
        return this.investigator;
    }

}

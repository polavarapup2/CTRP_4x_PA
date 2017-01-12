//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.11 at 01:47:08 PM EST 
//


package gov.nih.nci.pa.webservices.types;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * A participating site on a trial.
 * 			
 * 
 * <p>Java class for BaseParticipatingSite complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseParticipatingSite">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="recruitmentStatus" type="{gov.nih.nci.pa.webservices.types}RecruitmentStatus"/>
 *         &lt;element name="recruitmentStatusDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="localTrialIdentifier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="programCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="openedForAccrual" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="closedForAccrual" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="targetAccrualNumber" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *         &lt;element name="investigator" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="person" type="{gov.nih.nci.pa.webservices.types}Person"/>
 *                   &lt;element name="role">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="Principal Investigator"/>
 *                         &lt;enumeration value="Sub Investigator"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="primaryContact" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="primaryContact" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="person" type="{gov.nih.nci.pa.webservices.types}Person"/>
 *                   &lt;element name="contactDetails" type="{gov.nih.nci.pa.webservices.types}EmailOrPhone"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
@XmlType(name = "BaseParticipatingSite", propOrder = {
    "recruitmentStatus",
    "recruitmentStatusDate",
    "localTrialIdentifier",
    "programCode",
    "openedForAccrual",
    "closedForAccrual",
    "targetAccrualNumber",
    "investigator",
    "primaryContact"
})
@XmlSeeAlso({
    ParticipatingSite.class,
    ParticipatingSiteUpdate.class
})
public abstract class BaseParticipatingSite {

    @XmlElement(required = true)
    protected RecruitmentStatus recruitmentStatus;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar recruitmentStatusDate;
    @XmlElement(required = true)
    protected String localTrialIdentifier;
    protected String programCode;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar openedForAccrual;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar closedForAccrual;
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger targetAccrualNumber;
    protected List<BaseParticipatingSite.Investigator> investigator;
    protected BaseParticipatingSite.PrimaryContact primaryContact;

    /**
     * Gets the value of the recruitmentStatus property.
     * 
     * @return
     *     possible object is
     *     {@link RecruitmentStatus }
     *     
     */
    public RecruitmentStatus getRecruitmentStatus() {
        return recruitmentStatus;
    }

    /**
     * Sets the value of the recruitmentStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecruitmentStatus }
     *     
     */
    public void setRecruitmentStatus(RecruitmentStatus value) {
        this.recruitmentStatus = value;
    }

    /**
     * Gets the value of the recruitmentStatusDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRecruitmentStatusDate() {
        return recruitmentStatusDate;
    }

    /**
     * Sets the value of the recruitmentStatusDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRecruitmentStatusDate(XMLGregorianCalendar value) {
        this.recruitmentStatusDate = value;
    }

    /**
     * Gets the value of the localTrialIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalTrialIdentifier() {
        return localTrialIdentifier;
    }

    /**
     * Sets the value of the localTrialIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalTrialIdentifier(String value) {
        this.localTrialIdentifier = value;
    }

    /**
     * Gets the value of the programCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgramCode() {
        return programCode;
    }

    /**
     * Sets the value of the programCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgramCode(String value) {
        this.programCode = value;
    }

    /**
     * Gets the value of the openedForAccrual property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOpenedForAccrual() {
        return openedForAccrual;
    }

    /**
     * Sets the value of the openedForAccrual property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOpenedForAccrual(XMLGregorianCalendar value) {
        this.openedForAccrual = value;
    }

    /**
     * Gets the value of the closedForAccrual property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getClosedForAccrual() {
        return closedForAccrual;
    }

    /**
     * Sets the value of the closedForAccrual property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setClosedForAccrual(XMLGregorianCalendar value) {
        this.closedForAccrual = value;
    }

    /**
     * Gets the value of the targetAccrualNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTargetAccrualNumber() {
        return targetAccrualNumber;
    }

    /**
     * Sets the value of the targetAccrualNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTargetAccrualNumber(BigInteger value) {
        this.targetAccrualNumber = value;
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
     * {@link BaseParticipatingSite.Investigator }
     * 
     * 
     */
    public List<BaseParticipatingSite.Investigator> getInvestigator() {
        if (investigator == null) {
            investigator = new ArrayList<BaseParticipatingSite.Investigator>();
        }
        return this.investigator;
    }

    /**
     * Gets the value of the primaryContact property.
     * 
     * @return
     *     possible object is
     *     {@link BaseParticipatingSite.PrimaryContact }
     *     
     */
    public BaseParticipatingSite.PrimaryContact getPrimaryContact() {
        return primaryContact;
    }

    /**
     * Sets the value of the primaryContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseParticipatingSite.PrimaryContact }
     *     
     */
    public void setPrimaryContact(BaseParticipatingSite.PrimaryContact value) {
        this.primaryContact = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="person" type="{gov.nih.nci.pa.webservices.types}Person"/>
     *         &lt;element name="role">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="Principal Investigator"/>
     *               &lt;enumeration value="Sub Investigator"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="primaryContact" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "person",
        "role",
        "primaryContact"
    })
    public static class Investigator {

        @XmlElement(required = true)
        protected Person person;
        @XmlElement(required = true)
        protected String role;
        protected boolean primaryContact;

        /**
         * Gets the value of the person property.
         * 
         * @return
         *     possible object is
         *     {@link Person }
         *     
         */
        public Person getPerson() {
            return person;
        }

        /**
         * Sets the value of the person property.
         * 
         * @param value
         *     allowed object is
         *     {@link Person }
         *     
         */
        public void setPerson(Person value) {
            this.person = value;
        }

        /**
         * Gets the value of the role property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRole() {
            return role;
        }

        /**
         * Sets the value of the role property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRole(String value) {
            this.role = value;
        }

        /**
         * Gets the value of the primaryContact property.
         * 
         */
        public boolean isPrimaryContact() {
            return primaryContact;
        }

        /**
         * Sets the value of the primaryContact property.
         * 
         */
        public void setPrimaryContact(boolean value) {
            this.primaryContact = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="person" type="{gov.nih.nci.pa.webservices.types}Person"/>
     *         &lt;element name="contactDetails" type="{gov.nih.nci.pa.webservices.types}EmailOrPhone"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "person",
        "contactDetails"
    })
    public static class PrimaryContact {

        @XmlElement(required = true)
        protected Person person;
        @XmlElement(required = true)
        protected EmailOrPhone contactDetails;

        /**
         * Gets the value of the person property.
         * 
         * @return
         *     possible object is
         *     {@link Person }
         *     
         */
        public Person getPerson() {
            return person;
        }

        /**
         * Sets the value of the person property.
         * 
         * @param value
         *     allowed object is
         *     {@link Person }
         *     
         */
        public void setPerson(Person value) {
            this.person = value;
        }

        /**
         * Gets the value of the contactDetails property.
         * 
         * @return
         *     possible object is
         *     {@link EmailOrPhone }
         *     
         */
        public EmailOrPhone getContactDetails() {
            return contactDetails;
        }

        /**
         * Sets the value of the contactDetails property.
         * 
         * @param value
         *     allowed object is
         *     {@link EmailOrPhone }
         *     
         */
        public void setContactDetails(EmailOrPhone value) {
            this.contactDetails = value;
        }

    }

}
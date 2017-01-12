//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.11 at 01:46:55 PM EST 
//


package gov.nih.nci.po.ws.common.types;

import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Research Organization role
 * 
 * <p>Java class for ResearchOrganization complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResearchOrganization">
 *   &lt;complexContent>
 *     &lt;extension base="{gov.nih.nci.po.webservices.types}OrganizationRole">
 *       &lt;sequence>
 *         &lt;element name="name" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="160"/>
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ctepId" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="type" type="{gov.nih.nci.po.webservices.types}ResearchOrganizationType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResearchOrganization", propOrder = {
    "name",
    "ctepId",
    "type"
})
public class ResearchOrganization
    extends OrganizationRole
{

    protected String name;
    protected String ctepId;
    protected ResearchOrganizationType type;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the ctepId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtepId() {
        return ctepId;
    }

    /**
     * Sets the value of the ctepId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCtepId(String value) {
        this.ctepId = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ResearchOrganizationType }
     *     
     */
    public ResearchOrganizationType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResearchOrganizationType }
     *     
     */
    public void setType(ResearchOrganizationType value) {
        this.type = value;
    }

    public ResearchOrganization withName(String value) {
        setName(value);
        return this;
    }

    public ResearchOrganization withCtepId(String value) {
        setCtepId(value);
        return this;
    }

    public ResearchOrganization withType(ResearchOrganizationType value) {
        setType(value);
        return this;
    }

    @Override
    public ResearchOrganization withOrganizationId(long value) {
        setOrganizationId(value);
        return this;
    }

    @Override
    public ResearchOrganization withAddress(Address... values) {
        if (values!= null) {
            for (Address value: values) {
                getAddress().add(value);
            }
        }
        return this;
    }

    @Override
    public ResearchOrganization withAddress(Collection<Address> values) {
        if (values!= null) {
            getAddress().addAll(values);
        }
        return this;
    }

    @Override
    public ResearchOrganization withContact(Contact... values) {
        if (values!= null) {
            for (Contact value: values) {
                getContact().add(value);
            }
        }
        return this;
    }

    @Override
    public ResearchOrganization withContact(Collection<Contact> values) {
        if (values!= null) {
            getContact().addAll(values);
        }
        return this;
    }

    @Override
    public ResearchOrganization withStatus(EntityStatus value) {
        setStatus(value);
        return this;
    }

    @Override
    public ResearchOrganization withId(Long value) {
        setId(value);
        return this;
    }

}

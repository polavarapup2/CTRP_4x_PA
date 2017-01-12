//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.11 at 01:46:55 PM EST 
//


package gov.nih.nci.po.ws.common.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Entity with a status
 * 
 * <p>Java class for EntityWithStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EntityWithStatus">
 *   &lt;complexContent>
 *     &lt;extension base="{gov.nih.nci.po.webservices.types}Entity">
 *       &lt;sequence>
 *         &lt;element name="status" type="{gov.nih.nci.po.webservices.types}EntityStatus"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EntityWithStatus", propOrder = {
    "status"
})
@XmlSeeAlso({
    Person.class,
    Family.class,
    Organization.class,
    Role.class
})
public abstract class EntityWithStatus
    extends Entity
{

    @XmlElement(required = true)
    protected EntityStatus status;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link EntityStatus }
     *     
     */
    public EntityStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityStatus }
     *     
     */
    public void setStatus(EntityStatus value) {
        this.status = value;
    }

    public EntityWithStatus withStatus(EntityStatus value) {
        setStatus(value);
        return this;
    }

    @Override
    public EntityWithStatus withId(Long value) {
        setId(value);
        return this;
    }

}

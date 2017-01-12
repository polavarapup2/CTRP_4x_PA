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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.codehaus.jackson.annotate.JsonTypeInfo;


/**
 * A role that an organization plays within the cancer
 * 				research community
 * 			
 * 
 * <p>Java class for OrganizationRole complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrganizationRole">
 *   &lt;complexContent>
 *     &lt;extension base="{gov.nih.nci.po.webservices.types}Role">
 *       &lt;sequence>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrganizationRole")
@XmlSeeAlso({
    ResearchOrganization.class,
    OversightCommittee.class,
    HealthCareFacility.class
})
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class", include = JsonTypeInfo.As.PROPERTY)
public abstract class OrganizationRole
    extends Role
{


    @Override
    public OrganizationRole withOrganizationId(long value) {
        setOrganizationId(value);
        return this;
    }

    @Override
    public OrganizationRole withAddress(Address... values) {
        if (values!= null) {
            for (Address value: values) {
                getAddress().add(value);
            }
        }
        return this;
    }

    @Override
    public OrganizationRole withAddress(Collection<Address> values) {
        if (values!= null) {
            getAddress().addAll(values);
        }
        return this;
    }

    @Override
    public OrganizationRole withContact(Contact... values) {
        if (values!= null) {
            for (Contact value: values) {
                getContact().add(value);
            }
        }
        return this;
    }

    @Override
    public OrganizationRole withContact(Collection<Contact> values) {
        if (values!= null) {
            getContact().addAll(values);
        }
        return this;
    }

    @Override
    public OrganizationRole withStatus(EntityStatus value) {
        setStatus(value);
        return this;
    }

    @Override
    public OrganizationRole withId(Long value) {
        setId(value);
        return this;
    }

}

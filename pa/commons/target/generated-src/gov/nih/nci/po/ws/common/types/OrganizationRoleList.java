//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.11 at 01:46:55 PM EST 
//


package gov.nih.nci.po.ws.common.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{gov.nih.nci.po.webservices.types}organizationRole" maxOccurs="unbounded" minOccurs="0"/>
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
    "organizationRole"
})
@XmlRootElement(name = "organizationRoleList")
public class OrganizationRoleList {

    protected List<OrganizationRole> organizationRole;

    /**
     * Gets the value of the organizationRole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the organizationRole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrganizationRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrganizationRole }
     * 
     * 
     */
    public List<OrganizationRole> getOrganizationRole() {
        if (organizationRole == null) {
            organizationRole = new ArrayList<OrganizationRole>();
        }
        return this.organizationRole;
    }

    public OrganizationRoleList withOrganizationRole(OrganizationRole... values) {
        if (values!= null) {
            for (OrganizationRole value: values) {
                getOrganizationRole().add(value);
            }
        }
        return this;
    }

    public OrganizationRoleList withOrganizationRole(Collection<OrganizationRole> values) {
        if (values!= null) {
            getOrganizationRole().addAll(values);
        }
        return this;
    }

}
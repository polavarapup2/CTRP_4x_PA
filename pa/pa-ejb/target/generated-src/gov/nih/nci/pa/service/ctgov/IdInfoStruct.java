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
 * <p>Java class for id_info_struct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="id_info_struct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="org_study_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondary_id" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="nct_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nct_alias" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "id_info_struct", propOrder = {
    "orgStudyId",
    "secondaryId",
    "nctId",
    "nctAlias"
})
public class IdInfoStruct {

    @XmlElement(name = "org_study_id")
    protected String orgStudyId;
    @XmlElement(name = "secondary_id")
    protected List<String> secondaryId;
    @XmlElement(name = "nct_id", required = true)
    protected String nctId;
    @XmlElement(name = "nct_alias")
    protected List<String> nctAlias;

    /**
     * Gets the value of the orgStudyId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgStudyId() {
        return orgStudyId;
    }

    /**
     * Sets the value of the orgStudyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgStudyId(String value) {
        this.orgStudyId = value;
    }

    /**
     * Gets the value of the secondaryId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the secondaryId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSecondaryId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSecondaryId() {
        if (secondaryId == null) {
            secondaryId = new ArrayList<String>();
        }
        return this.secondaryId;
    }

    /**
     * Gets the value of the nctId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNctId() {
        return nctId;
    }

    /**
     * Sets the value of the nctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNctId(String value) {
        this.nctId = value;
    }

    /**
     * Gets the value of the nctAlias property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nctAlias property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNctAlias().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNctAlias() {
        if (nctAlias == null) {
            nctAlias = new ArrayList<String>();
        }
        return this.nctAlias;
    }

}

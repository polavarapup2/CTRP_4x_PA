//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.07 at 03:16:27 PM EST 
//


package gov.nih.nci.pa.service.ctgov;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for study_doc_struct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="study_doc_struct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="doc_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="doc_type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="doc_url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="doc_comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "study_doc_struct", propOrder = {
    "docId",
    "docType",
    "docUrl",
    "docComment"
})
public class StudyDocStruct {

    @XmlElement(name = "doc_id")
    protected String docId;
    @XmlElement(name = "doc_type")
    protected String docType;
    @XmlElement(name = "doc_url")
    protected String docUrl;
    @XmlElement(name = "doc_comment")
    protected String docComment;

    /**
     * Gets the value of the docId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocId() {
        return docId;
    }

    /**
     * Sets the value of the docId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocId(String value) {
        this.docId = value;
    }

    /**
     * Gets the value of the docType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocType() {
        return docType;
    }

    /**
     * Sets the value of the docType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocType(String value) {
        this.docType = value;
    }

    /**
     * Gets the value of the docUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocUrl() {
        return docUrl;
    }

    /**
     * Sets the value of the docUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocUrl(String value) {
        this.docUrl = value;
    }

    /**
     * Gets the value of the docComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocComment() {
        return docComment;
    }

    /**
     * Sets the value of the docComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocComment(String value) {
        this.docComment = value;
    }

}

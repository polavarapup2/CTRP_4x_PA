//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.11 at 01:47:08 PM EST 
//


package gov.nih.nci.pa.webservices.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GrantorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GrantorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CDER"/>
 *     &lt;enumeration value="CBER"/>
 *     &lt;enumeration value="CDRH"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GrantorCode")
@XmlEnum
public enum GrantorCode {

    CDER,
    CBER,
    CDRH;

    public String value() {
        return name();
    }

    public static GrantorCode fromValue(String v) {
        return valueOf(v);
    }

}
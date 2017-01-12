//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.11 at 01:47:08 PM EST 
//


package gov.nih.nci.pa.webservices.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExpandedAccessType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ExpandedAccessType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Available"/>
 *     &lt;enumeration value="No longer available"/>
 *     &lt;enumeration value="Temporarily not available"/>
 *     &lt;enumeration value="Approved for marketing"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ExpandedAccessType")
@XmlEnum
public enum ExpandedAccessType {

    @XmlEnumValue("Available")
    AVAILABLE("Available"),
    @XmlEnumValue("No longer available")
    NO_LONGER_AVAILABLE("No longer available"),
    @XmlEnumValue("Temporarily not available")
    TEMPORARILY_NOT_AVAILABLE("Temporarily not available"),
    @XmlEnumValue("Approved for marketing")
    APPROVED_FOR_MARKETING("Approved for marketing");
    private final String value;

    ExpandedAccessType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ExpandedAccessType fromValue(String v) {
        for (ExpandedAccessType c: ExpandedAccessType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.28 at 12:41:09 PM EST 
//


package gov.nih.nci.pa.webservices.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PrimaryPurpose.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PrimaryPurpose">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Treatment"/>
 *     &lt;enumeration value="Prevention"/>
 *     &lt;enumeration value="Supportive Care"/>
 *     &lt;enumeration value="Screening"/>
 *     &lt;enumeration value="Diagnostic"/>
 *     &lt;enumeration value="Health Services Research"/>
 *     &lt;enumeration value="Basic Science"/>
 *     &lt;enumeration value="Other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PrimaryPurpose")
@XmlEnum
public enum PrimaryPurpose {

    @XmlEnumValue("Treatment")
    TREATMENT("Treatment"),
    @XmlEnumValue("Prevention")
    PREVENTION("Prevention"),
    @XmlEnumValue("Supportive Care")
    SUPPORTIVE_CARE("Supportive Care"),
    @XmlEnumValue("Screening")
    SCREENING("Screening"),
    @XmlEnumValue("Diagnostic")
    DIAGNOSTIC("Diagnostic"),
    @XmlEnumValue("Health Services Research")
    HEALTH_SERVICES_RESEARCH("Health Services Research"),
    @XmlEnumValue("Basic Science")
    BASIC_SCIENCE("Basic Science"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    PrimaryPurpose(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PrimaryPurpose fromValue(String v) {
        for (PrimaryPurpose c: PrimaryPurpose.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

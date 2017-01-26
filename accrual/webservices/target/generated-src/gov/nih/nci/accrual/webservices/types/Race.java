//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.26 at 12:39:11 PM EST 
//


package gov.nih.nci.accrual.webservices.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Race.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Race">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="American Indian or Alaska Native"/>
 *     &lt;enumeration value="Asian"/>
 *     &lt;enumeration value="Black or African American"/>
 *     &lt;enumeration value="Native Hawaiian or Other Pacific Islander"/>
 *     &lt;enumeration value="Not Reported"/>
 *     &lt;enumeration value="Unknown"/>
 *     &lt;enumeration value="White"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Race")
@XmlEnum
public enum Race {

    @XmlEnumValue("American Indian or Alaska Native")
    AMERICAN_INDIAN_OR_ALASKA_NATIVE("American Indian or Alaska Native"),
    @XmlEnumValue("Asian")
    ASIAN("Asian"),
    @XmlEnumValue("Black or African American")
    BLACK_OR_AFRICAN_AMERICAN("Black or African American"),
    @XmlEnumValue("Native Hawaiian or Other Pacific Islander")
    NATIVE_HAWAIIAN_OR_OTHER_PACIFIC_ISLANDER("Native Hawaiian or Other Pacific Islander"),
    @XmlEnumValue("Not Reported")
    NOT_REPORTED("Not Reported"),
    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown"),
    @XmlEnumValue("White")
    WHITE("White");
    private final String value;

    Race(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Race fromValue(String v) {
        for (Race c: Race.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.07 at 03:16:27 PM EST 
//


package gov.nih.nci.pa.service.ctgov;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for intervention_type_enum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="intervention_type_enum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Behavioral"/>
 *     &lt;enumeration value="Biological"/>
 *     &lt;enumeration value="Compound Product"/>
 *     &lt;enumeration value="Device"/>
 *     &lt;enumeration value="Diagnostic Test"/>
 *     &lt;enumeration value="Dietary Supplement"/>
 *     &lt;enumeration value="Drug"/>
 *     &lt;enumeration value="Genetic"/>
 *     &lt;enumeration value="Procedure"/>
 *     &lt;enumeration value="Radiation"/>
 *     &lt;enumeration value="Other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "intervention_type_enum")
@XmlEnum
public enum InterventionTypeEnum {

    @XmlEnumValue("Behavioral")
    BEHAVIORAL("Behavioral"),
    @XmlEnumValue("Biological")
    BIOLOGICAL("Biological"),
    @XmlEnumValue("Compound Product")
    COMPOUND_PRODUCT("Compound Product"),
    @XmlEnumValue("Device")
    DEVICE("Device"),
    @XmlEnumValue("Diagnostic Test")
    DIAGNOSTIC_TEST("Diagnostic Test"),
    @XmlEnumValue("Dietary Supplement")
    DIETARY_SUPPLEMENT("Dietary Supplement"),
    @XmlEnumValue("Drug")
    DRUG("Drug"),
    @XmlEnumValue("Genetic")
    GENETIC("Genetic"),
    @XmlEnumValue("Procedure")
    PROCEDURE("Procedure"),
    @XmlEnumValue("Radiation")
    RADIATION("Radiation"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    InterventionTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static InterventionTypeEnum fromValue(String v) {
        for (InterventionTypeEnum c: InterventionTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

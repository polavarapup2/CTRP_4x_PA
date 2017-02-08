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
 * <p>Java class for sampling_method_enum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="sampling_method_enum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Probability Sample"/>
 *     &lt;enumeration value="Non-Probability Sample"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "sampling_method_enum")
@XmlEnum
public enum SamplingMethodEnum {

    @XmlEnumValue("Probability Sample")
    PROBABILITY_SAMPLE("Probability Sample"),
    @XmlEnumValue("Non-Probability Sample")
    NON_PROBABILITY_SAMPLE("Non-Probability Sample");
    private final String value;

    SamplingMethodEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SamplingMethodEnum fromValue(String v) {
        for (SamplingMethodEnum c: SamplingMethodEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

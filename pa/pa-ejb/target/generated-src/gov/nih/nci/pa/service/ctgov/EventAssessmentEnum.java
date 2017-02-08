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
 * <p>Java class for event_assessment_enum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="event_assessment_enum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Non-systematic Assessment"/>
 *     &lt;enumeration value="Systematic Assessment"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "event_assessment_enum")
@XmlEnum
public enum EventAssessmentEnum {

    @XmlEnumValue("Non-systematic Assessment")
    NON_SYSTEMATIC_ASSESSMENT("Non-systematic Assessment"),
    @XmlEnumValue("Systematic Assessment")
    SYSTEMATIC_ASSESSMENT("Systematic Assessment");
    private final String value;

    EventAssessmentEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EventAssessmentEnum fromValue(String v) {
        for (EventAssessmentEnum c: EventAssessmentEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

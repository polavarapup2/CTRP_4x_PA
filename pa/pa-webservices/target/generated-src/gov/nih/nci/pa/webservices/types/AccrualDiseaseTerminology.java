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
 * <p>Java class for AccrualDiseaseTerminology.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AccrualDiseaseTerminology">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SDC"/>
 *     &lt;enumeration value="ICD10"/>
 *     &lt;enumeration value="ICD9"/>
 *     &lt;enumeration value="ICD-O-3"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AccrualDiseaseTerminology")
@XmlEnum
public enum AccrualDiseaseTerminology {

    SDC("SDC"),
    @XmlEnumValue("ICD10")
    ICD_10("ICD10"),
    @XmlEnumValue("ICD9")
    ICD_9("ICD9"),
    @XmlEnumValue("ICD-O-3")
    ICD_O_3("ICD-O-3");
    private final String value;

    AccrualDiseaseTerminology(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AccrualDiseaseTerminology fromValue(String v) {
        for (AccrualDiseaseTerminology c: AccrualDiseaseTerminology.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

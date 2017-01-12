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
 * <p>Java class for NciDivisionProgramCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NciDivisionProgramCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CCR"/>
 *     &lt;enumeration value="CCT/CTB"/>
 *     &lt;enumeration value="CIP"/>
 *     &lt;enumeration value="CDP"/>
 *     &lt;enumeration value="CTEP"/>
 *     &lt;enumeration value="DCB"/>
 *     &lt;enumeration value="DCCPS"/>
 *     &lt;enumeration value="DCEG"/>
 *     &lt;enumeration value="DCP"/>
 *     &lt;enumeration value="DEA"/>
 *     &lt;enumeration value="DTP"/>
 *     &lt;enumeration value="OD"/>
 *     &lt;enumeration value="OSB/SPOREs"/>
 *     &lt;enumeration value="TRP"/>
 *     &lt;enumeration value="RRP"/>
 *     &lt;enumeration value="N/A"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "NciDivisionProgramCode")
@XmlEnum
public enum NciDivisionProgramCode {

    CCR("CCR"),
    @XmlEnumValue("CCT/CTB")
    CCT_CTB("CCT/CTB"),
    CIP("CIP"),
    CDP("CDP"),
    CTEP("CTEP"),
    DCB("DCB"),
    DCCPS("DCCPS"),
    DCEG("DCEG"),
    DCP("DCP"),
    DEA("DEA"),
    DTP("DTP"),
    OD("OD"),
    @XmlEnumValue("OSB/SPOREs")
    OSB_SPOR_ES("OSB/SPOREs"),
    TRP("TRP"),
    RRP("RRP"),
    @XmlEnumValue("N/A")
    N_A("N/A");
    private final String value;

    NciDivisionProgramCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NciDivisionProgramCode fromValue(String v) {
        for (NciDivisionProgramCode c: NciDivisionProgramCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

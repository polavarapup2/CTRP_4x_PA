//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.07 at 03:16:32 PM EST 
//


package gov.nih.nci.pa.webservices.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NihInstitutionCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NihInstitutionCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NEI"/>
 *     &lt;enumeration value="NHLBI"/>
 *     &lt;enumeration value="NHGRI"/>
 *     &lt;enumeration value="NIA"/>
 *     &lt;enumeration value="NIAAA"/>
 *     &lt;enumeration value="NIAID"/>
 *     &lt;enumeration value="NIAMS"/>
 *     &lt;enumeration value="NIBIB"/>
 *     &lt;enumeration value="NICHD"/>
 *     &lt;enumeration value="NIDCD"/>
 *     &lt;enumeration value="NIDCR"/>
 *     &lt;enumeration value="NIDDK"/>
 *     &lt;enumeration value="NIDA"/>
 *     &lt;enumeration value="NIEHS"/>
 *     &lt;enumeration value="NIGMS"/>
 *     &lt;enumeration value="NIMH"/>
 *     &lt;enumeration value="NINDS"/>
 *     &lt;enumeration value="NINR"/>
 *     &lt;enumeration value="NLM"/>
 *     &lt;enumeration value="CIT"/>
 *     &lt;enumeration value="CSR"/>
 *     &lt;enumeration value="FIC"/>
 *     &lt;enumeration value="NCCAM"/>
 *     &lt;enumeration value="NCMHD"/>
 *     &lt;enumeration value="NCRR"/>
 *     &lt;enumeration value="CC"/>
 *     &lt;enumeration value="OD"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "NihInstitutionCode")
@XmlEnum
public enum NihInstitutionCode {

    NEI,
    NHLBI,
    NHGRI,
    NIA,
    NIAAA,
    NIAID,
    NIAMS,
    NIBIB,
    NICHD,
    NIDCD,
    NIDCR,
    NIDDK,
    NIDA,
    NIEHS,
    NIGMS,
    NIMH,
    NINDS,
    NINR,
    NLM,
    CIT,
    CSR,
    FIC,
    NCCAM,
    NCMHD,
    NCRR,
    CC,
    OD;

    public String value() {
        return name();
    }

    public static NihInstitutionCode fromValue(String v) {
        return valueOf(v);
    }

}

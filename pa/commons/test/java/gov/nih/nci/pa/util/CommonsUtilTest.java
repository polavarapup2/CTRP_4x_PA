package gov.nih.nci.pa.util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import gov.nih.nci.pa.noniso.dto.TrialRegistrationConfirmationDTO;
import gov.nih.nci.pa.noniso.dto.TrialRegistrationConfirmationDTOs;

import org.junit.Test;
/**
 * 
 * @author Reshma
 *
 */
public class CommonsUtilTest {
    @Test
    public void unmarshallXMLTest() {
        String actualString = "<List><item><paTrialID>176578</paTrialID><nciTrialID>NCI-2017-00420</nciTrialID></item></List>";
        String acStringList = "<List><item><paTrialID>176578</paTrialID><nciTrialID>NCI-2017-00420</nciTrialID></item>,"
                + "<item><paTrialID>176548</paTrialID><nciTrialID>NCI-2017-00423</nciTrialID></item></List>";

        TrialRegistrationConfirmationDTOs resultObj = CommonsUtil
                .unmarshallXML(actualString,
                        TrialRegistrationConfirmationDTOs.class);
        assertTrue(resultObj.getDtos() != null);
        assertTrue(resultObj.getDtos().size() == 1);
        resultObj = CommonsUtil
                .unmarshallXML(acStringList,
                        TrialRegistrationConfirmationDTOs.class);
        assertTrue(resultObj.getDtos() != null);
        assertTrue(resultObj.getDtos().size() == 2);
    }
    
    @Test
    public void unmarshallXMLInsertTest() {
        String actualString = "<TrialRegistrationConfirmation><paTrialID>234984367</paTrialID><nciTrialID>NCI-2017-00117</nciTrialID></TrialRegistrationConfirmation>";
        TrialRegistrationConfirmationDTO resultObj = CommonsUtil
                .unmarshallXML(actualString,
                        TrialRegistrationConfirmationDTO.class);
        assertTrue(resultObj.getNciTrialID().equals("NCI-2017-00117"));
       
    }
    
    @Test
    public void unmarshallXMLNullTest() {
        TrialRegistrationConfirmationDTO dto = CommonsUtil.unmarshallXML(null, TrialRegistrationConfirmationDTO.class);
        assertNull(dto);
    }
    
    @Test
    public void unmarshallXMLEmptyStringTest() {
        TrialRegistrationConfirmationDTO dto = CommonsUtil.unmarshallXML("", TrialRegistrationConfirmationDTO.class);
        assertNull(dto);
    }

    @Test
    public void unmarshallXMLInvalidXMLTest() {
        String xmlStr = "<TrialRegistrationConfirmation><paTrialID>234984367</paTrialID><nciTrialID>NCI-2017-00117</nciTrialID></TrialRegistrationConfirmation";
        TrialRegistrationConfirmationDTO dto = CommonsUtil.unmarshallXML(xmlStr, TrialRegistrationConfirmationDTO.class);
        assertNull(dto);
    }
}

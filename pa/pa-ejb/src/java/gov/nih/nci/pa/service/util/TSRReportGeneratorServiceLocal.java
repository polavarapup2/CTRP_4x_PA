package gov.nih.nci.pa.service.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.service.PAException;

import java.io.ByteArrayOutputStream;

import javax.ejb.Local;

/**
 * @author Hugh Reinhart
 * @since Feb 12, 2014
 */
@Local
public interface TSRReportGeneratorServiceLocal {

    /**
     * Generates the TSR report in the PDF format.
     * @param studyProtocolIi ii of studyprotocol
     * @return ByteArrayOutputStream the report stream bytes
     * @throws PAException on error
     */
    ByteArrayOutputStream generatePdfTsrReport(Ii studyProtocolIi) throws PAException;

    /**
     * Generates the TSR report in the RTF format.
     * @param studyProtocolIi ii of studyprotocol
     * @return ByteArrayOutputStream the report stream bytes
     * @throws PAException on error
     */
    ByteArrayOutputStream generateRtfTsrReport(Ii studyProtocolIi) throws PAException;

    /**
     * Generates the TSR report in the HTML format.
     * @param studyProtocolIi ii of studyprotocol
     * @return ByteArrayOutputStream the report stream bytes
     * @throws PAException on error
     */
    ByteArrayOutputStream generateHtmlTsrReport(Ii studyProtocolIi) throws PAException;
}

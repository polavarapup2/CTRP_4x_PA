/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.CacheUtils;

import java.io.ByteArrayOutputStream;

/**
 * @author dkrylov
 * 
 */
public final class TSRReportGeneratorServiceCachingDecorator implements
        TSRReportGeneratorServiceLocal {

    private final TSRReportGeneratorServiceLocal service;

    /**
     * @param service
     *            TSRReportGeneratorServiceLocal
     */
    public TSRReportGeneratorServiceCachingDecorator(
            TSRReportGeneratorServiceLocal service) {
        this.service = service;
    }

    @Override
    public ByteArrayOutputStream generatePdfTsrReport(Ii studyProtocolIi)
            throws PAException {
        return service.generatePdfTsrReport(studyProtocolIi);
    }

    @Override
    public ByteArrayOutputStream generateRtfTsrReport(final Ii studyProtocolIi)
            throws PAException {
        final String key = IiConverter.convertToString(studyProtocolIi);
        return (ByteArrayOutputStream) CacheUtils.getFromCacheOrBackend(
                CacheUtils.getTSRCache(), key, new CacheUtils.Closure() {
                    @Override
                    public Object execute() throws PAException {
                        return service.generateRtfTsrReport(studyProtocolIi);
                    }
                });

    }

    @Override
    public ByteArrayOutputStream generateHtmlTsrReport(Ii studyProtocolIi)
            throws PAException {
        return service.generateHtmlTsrReport(studyProtocolIi);
    }

}

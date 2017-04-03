package gov.nih.nci.pa.util;

import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

/**
 * 
 * @author Reshma
 *
 */
public final class CommonsUtil {
    private static final Logger LOG = Logger.getLogger(CommonsUtil.class);
    /**
     * const
     */
    private CommonsUtil() {
        super();
        // TODO Auto-generated constructor stub
    }
    

/**
 * 
 * @param xmlString xmlString
 * @param objClass objClass
 * @param <T> the type
 * @return T
 */
    @SuppressWarnings("unchecked")
    public static <T> T unmarshallXML(String xmlString, Class<T> objClass) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(objClass); 
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (T) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

}

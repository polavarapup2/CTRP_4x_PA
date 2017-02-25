package gov.nih.nci.pa.util;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
/**
 * 
 * @author Purnima, Reshma
 *
 */
public final class PAWebUtil {
    private static final Logger LOG = Logger.getLogger(PAWebUtil.class);
    
    private PAWebUtil() {
       super();
    }
    
    /**
     * 
     * @param obj the obj object to convert to JSON
     * @return - responseJSONStr String
     * @throws IOException IOException
     */
    public static String marshallJSON(Object obj)
            throws IOException {

        String responseJSONStr = "";
        ObjectMapper mapper = new ObjectMapper();
        responseJSONStr = mapper.writeValueAsString(obj);

        return responseJSONStr;
    }

    /**
     * 
     * @param jsonString
     *            the jsonString
     * @param objClass
     *            the objClass
     * @return Object
     */
    public static Object unmarshallJSON(String jsonString, Class objClass) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // JSON from String to Object
             return (mapper.readValue(jsonString, objClass));

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
}

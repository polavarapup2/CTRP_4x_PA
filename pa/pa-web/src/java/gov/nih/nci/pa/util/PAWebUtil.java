package gov.nih.nci.pa.util;

import java.io.IOException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
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
     * @param <T> the type
     * @param obj the obj
     * @return the JSON String for the obj
     * @throws IOException IOException
     */
    public static <T> String marshallJSON(T obj)
            throws IOException {

        String responseJSONStr = "";
        ObjectMapper mapper = new ObjectMapper();
        responseJSONStr = mapper.writeValueAsString(obj);

        return responseJSONStr;
    }

    /**
     * 
     * @param jsonString the jsonString
     * @param <T> the <T>
     * @param objClass the objClass
     * @return the Object for the JSONString
     */
    public static <T> T unmarshallJSON(String jsonString, Class<T> objClass) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // JSON from String to Object
             return (mapper.readValue(jsonString, objClass));

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 
     * @param jsonString the jsonString
     * @param <T> the <T>
     * @param valueTypeRef the valueTypeRef
     * @return the Object for the JSONString
     */
    public static <T> T unmarshallJSON(String jsonString, TypeReference<T> valueTypeRef) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // JSON from String to Object
             return (mapper.readValue(jsonString, valueTypeRef));
             
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 
     * @param booleanStr the booleanStr
     * @return Boolean
     */
    public static Boolean isValidBooleanString(String booleanStr) {
        Boolean b = Boolean.FALSE;
        try {
            BooleanUtils.toBooleanObject(booleanStr, "true", "false", "null");
            b = Boolean.TRUE;
        } catch (IllegalArgumentException ile) {
            b = Boolean.FALSE;
        }
        return b; 
    }

}

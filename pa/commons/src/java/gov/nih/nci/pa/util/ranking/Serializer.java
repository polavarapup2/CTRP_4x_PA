package gov.nih.nci.pa.util.ranking;

/**
 * 
 * @author 
 * @param <T>
 */
public interface Serializer<T> {



    /**
     * Will serailize to string the object passed-in.    
     * @param object to serialize
     * @return string
     */
    String serialize(T object);

}

package gov.nih.nci.pa.util;


import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;

import org.apache.log4j.Logger;

/**
 * 
 * @author Reshma Koganti
 *
 */
public class CaDSRValueFromDB {
    /** The LOG details. */
    private static final Logger LOG = Logger.getLogger(CaDSRValueFromDB.class);
    /** The caDSR url */
    private static final String CADSR_URL = "CADSR_URL";
    
    private String url;
    /**
     * 
     * @return String String
     * @throws PAException PAException
     */
   public String getUrl() throws PAException {
        LOG.info("got into cadsr class");
        LookUpTableServiceRemote lookUpTableService = PaRegistry.getLookUpTableService();
        url = lookUpTableService.getPropertyValue(CADSR_URL);
        LOG.info("value" + url);
        return url;
   }
   /**
    * 
    * @param url url
    */
   public void setUrl(String url) {
       this.url = url;
   }
    
    
}

package gov.nih.nci.pa.service.util;

import java.io.IOException;

import gov.nih.nci.pa.service.PAException;

import javax.ejb.Local;

/**
 * @author Denis G. Krylov
 */
@Local
public interface TwitterServiceLocal {

    /**
     * Submits queued tweets to Twitter.
     * 
     * @throws PAException
     *             PAException
     * @throws IOException
     *             IOException
     */
    void processQueue() throws PAException, IOException;

}

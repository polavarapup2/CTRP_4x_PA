package gov.nih.nci.pa.service.util;

import java.io.IOException;

import gov.nih.nci.pa.service.PAException;

import javax.ejb.Local;

/**
 * @author Denis G. Krylov
 */
@Local
public interface TrialTweetingService {

    /**
     * Finds new trials that are ready to be tweeted about, and schedules
     * tweets.
     * 
     * @throws PAException
     *             PAException
     * @throws IOException
     *             IOException
     */
    void processTrials() throws PAException, IOException;

}

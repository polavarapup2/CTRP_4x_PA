package gov.nih.nci.pa.service.util;

import java.net.URL;

import javax.ejb.Local;

/**
 * @author Denis G. Krylov
 */
@Local
public interface URLShortenerServiceLocal {

    /**
     * Shorten a URL using go.usa.gov.
     * 
     * @param url
     *            URL
     * @return URL or null if can't shorten.
     */
    URL shorten(URL url);

}

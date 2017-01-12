/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.RegistryUser;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

/**
 * @author Denis G. Krylov
 * 
 */
public final class RegistryUserConverter {

    private static final Logger LOG = Logger
            .getLogger(RegistryUserConverter.class);

    private RegistryUserConverter() {
    }

    /**
     * @param users collection of users
     * @return DSet<Tel> DSet of Tel with email addresses.
     */
    public static DSet<Tel> convertToDSet(Collection<RegistryUser> users) {
        DSet<Tel> dset = new DSet<Tel>();
        dset.setItem(new LinkedHashSet<Tel>());
        if (CollectionUtils.isNotEmpty(users)) {
            for (RegistryUser user : users) {
                Tel tel = new Tel();
                try {
                    tel.setValue(new URI("mailto:" + user.getEmailAddress()));
                    dset.getItem().add(tel);
                } catch (URISyntaxException e) {
                    LOG.error("Invalid or empty email address encountered: "
                            + user.getEmailAddress() + ". Registry user: "
                            + user.getId());
                }

            }
        }
        return dset;
    }

}

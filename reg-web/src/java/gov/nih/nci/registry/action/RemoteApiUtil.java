package gov.nih.nci.registry.action;

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnPn;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.commons.lang.StringUtils;

/**
 * Adapted from PO modified for PA to call the PO API's.
 * @author Harsha
 *
 */
public class RemoteApiUtil {
    /**
     * @param firstName given name
     * @param middleName middle name
     * @param lastName family name
     * @param prefix prefix
     * @param suffix suffix
     * @return ISO EN Person Name
     */
    public static final EnPn convertToEnPn(String firstName, String middleName,
            String lastName, String prefix, String suffix) {
        EnPn enpn = new EnPn();
        addEnxp(enpn, lastName, EntityNamePartType.FAM);
        addEnxp(enpn, firstName, EntityNamePartType.GIV);
        addEnxp(enpn, middleName, EntityNamePartType.GIV);
        addEnxp(enpn, prefix, EntityNamePartType.PFX);
        addEnxp(enpn, suffix, EntityNamePartType.SFX);
        return enpn;
    }
    
    private static void addEnxp(EnPn enpn, String value, EntityNamePartType type) {
        if (StringUtils.isNotEmpty(value)) {
            Enxp part = new Enxp(type);
            part.setValue(value);
            enpn.getPart().add(part);
        }
    }

    /**
     * 
     * @param value a boolean to parse.
     * @return an iso BL
     */
    public static Bl convertToBl(Boolean value) {
        Bl iso = new Bl();
        if (value == null) {
            iso.setNullFlavor(NullFlavor.NI);
        } else {
            iso.setValue(value);
        }
        return iso;
    }
    
    /**
     * @param email email
     * @param fax fax
     * @param phone phone
     * @param url url
     * @param text tty
     * @return a list containg all the params' content converted to a Tel type.
     */
    public static DSet<Tel> convertToDSetTel(List<String> email, List<String> fax,
            List<String> phone, List<String> url, List<String> text) {
        DSet<Tel> dset = new DSet<Tel>();
        @SuppressWarnings("unchecked")
        Set<Tel> set = new ListOrderedSet();
        dset.setItem(set);
        for (String c : email) {
            TelEmail t = new TelEmail();
            t.setValue(createURI(TelEmail.SCHEME_MAILTO, c));
            set.add(t);
        }
        for (String c : fax) {
            TelPhone t = new TelPhone();
            t.setValue(createURI(TelPhone.SCHEME_X_TEXT_FAX, c));
            set.add(t);
        }
        for (String c : phone) {
            TelPhone t = new TelPhone();
            t.setValue(createURI(TelPhone.SCHEME_TEL, c));
            set.add(t);
        }
        for (String c : url) {
            TelUrl t = new TelUrl();
            t.setValue(URI.create(c));
            set.add(t);
        }
        for (String c : text) {
            TelPhone t = new TelPhone();
            t.setValue(createURI(TelPhone.SCHEME_X_TEXT_TEL, c));
            set.add(t);
        }
        return dset;
    }    

    private static URI createURI(String scheme, String schemeSpecificPart) {
        try {
            return new URI(scheme, schemeSpecificPart, null);
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException(ex);
        }
    }    
    

}


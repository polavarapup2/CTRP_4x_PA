/**
 * 
 */
package gov.nih.nci.pa.lov;

import java.util.List;

import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

/**
 * @author Denis G. Krylov
 * 
 */
public abstract class AbstractLov implements Lov {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    @Transient
    public String getDisplayName() {
        if (StringUtils.isNotBlank(getName())) {
            StringBuilder displayName = new StringBuilder(getName()
                    .toLowerCase());
            displayName
                    .replace(0, 1, displayName.substring(0, 1).toUpperCase());
            for (int i = 0; i < displayName.length(); i++) {
                if (displayName.charAt(i) == '_') {
                    displayName.setCharAt(i, ' ');
                }
            }
            return displayName.toString();
        } else {
            return "";
        }
    }

    /**
     * @param list
     *            List<Lov>
     * @return String[]
     */
    protected static String[] loadDisplayNamesArray(List<? extends Lov> list) {
        Lov[] l = list.toArray(new Lov[0]); // NOPMD
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
}

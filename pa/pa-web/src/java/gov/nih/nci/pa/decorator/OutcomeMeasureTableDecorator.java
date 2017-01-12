/**
 * 
 */
package gov.nih.nci.pa.decorator;

import org.apache.commons.beanutils.PropertyUtils;
import org.displaytag.decorator.TableDecorator;

/**
 * Produces ID attributes on individual table rows. Row IDs are needed, for
 * example, for drag-n-drop to work.
 * 
 * @author Denis G. Krylov
 * 
 */
public final class OutcomeMeasureTableDecorator extends TableDecorator {

    @Override
    public String addRowId() {
        try {
            return "rowid_"
                    + PropertyUtils.getProperty(getCurrentRowObject(), "outcomeMeasure.id")
                            .toString();
        } catch (Exception e) {
            return "undefined";
        }
    }

}

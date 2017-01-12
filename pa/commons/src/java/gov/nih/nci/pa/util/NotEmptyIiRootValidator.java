//$Id: $
package gov.nih.nci.pa.util;

import gov.nih.nci.iso21090.Ii;

import java.util.Iterator;

import org.hibernate.mapping.Column;
import org.hibernate.mapping.Property;

/**
 * Checks that the Ii root has been set.
 */
public class NotEmptyIiRootValidator extends AbstractIiValidator<NotEmptyIiRoot> {
    private static final long serialVersionUID = 1L;
    private String columnName;

    /**
     * {@inheritDoc}
     */
    public void initialize(NotEmptyIiRoot parameters) {
        columnName = parameters.columnName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    boolean validate(Ii ii) {
        return new NotEmptyValidator().isValid(ii.getRoot());
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void apply(Property property) {
        Iterator<Column> iter = property.getColumnIterator();
        while (iter.hasNext()) {
            Column next = iter.next();
            if (columnName.equals(next.getName())) {
                next.setNullable(false);
                break;
            }
        }
    }

}

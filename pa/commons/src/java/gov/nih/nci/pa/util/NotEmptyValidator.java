//$Id: $
package gov.nih.nci.pa.util;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.mapping.Column;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.SingleTableSubclass;
import org.hibernate.validator.PropertyConstraint;
import org.hibernate.validator.Validator;

/**
 * Check the non emptyness of the element.
 *
 * @author Gavin King
 */
public class NotEmptyValidator implements Validator<NotEmpty>, PropertyConstraint, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public void initialize(NotEmpty parameters) {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    public boolean isValid(Object value) {
        if (value == null) {
            return false;
        }
        if (value.getClass().isArray()) {
            return Array.getLength(value) > 0;
        } else if (value instanceof Collection) {
            return ((Collection<?>) value).size() > 0;
        } else if (value instanceof Map) {
            return ((Map<?, ?>) value).size() > 0;
        } else {
            return ((String) value).length() > 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void apply(Property property) {
        if (!(property.getPersistentClass() instanceof SingleTableSubclass)
                && !(property.getValue() instanceof Collection)
                && !property.isComposite()) {
            Iterator<Column> iter = property.getColumnIterator();
            while (iter.hasNext()) {
                iter.next().setNullable(false);
            }
        }
    }

}

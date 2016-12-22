package gov.nih.nci.registry.decorator;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.displaytag.decorator.ColumnDecorator;
import org.displaytag.exception.DecoratorException;

/**
 * 
 * @author Reshma Koganti
 * 
 */
@SuppressWarnings("deprecation")
public final class HtmlEscapeDecorator implements ColumnDecorator {

    @Override
    public String decorate(Object value) throws DecoratorException {
        return StringEscapeUtils.escapeHtml(ObjectUtils
                .defaultIfNull(value, "").toString());
    }

}

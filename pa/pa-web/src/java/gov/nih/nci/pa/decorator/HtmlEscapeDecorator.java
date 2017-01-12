/**
 * 
 */
package gov.nih.nci.pa.decorator;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.displaytag.decorator.ColumnDecorator;
import org.displaytag.exception.DecoratorException;

/**
 * Replaces DisplayTag's default HTML escaping to support IE versions prior to
 * 9: some of XML entities, such as &amp;apos;, are not rendered in IE 8 and
 * earlier. See https://tracker
 * .nci.nih.gov/browse/PO-5077?focusedCommentId=147564#comment-147564 for
 * details.
 * 
 * @author Denis G. Krylov
 * @see https://tracker.nci.nih.gov/browse/PO-5077
 */
@SuppressWarnings("deprecation")
public final class HtmlEscapeDecorator implements ColumnDecorator {

    @Override
    public String decorate(Object value) throws DecoratorException {
        return StringEscapeUtils.escapeHtml(ObjectUtils
                .defaultIfNull(value, "").toString());
    }

}

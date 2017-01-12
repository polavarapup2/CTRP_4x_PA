package gov.nih.nci.pa.decorator;

import gov.nih.nci.pa.domain.StudyCheckout;
import gov.nih.nci.pa.util.CsmUserUtil;

import org.apache.commons.lang.time.FastDateFormat;
import org.displaytag.decorator.TableDecorator;

/**
 * @author Hugh Reinhart
 * @since Jun 12, 2012
 */
public class CheckOutHistoryTagDecorator extends TableDecorator {
    private final FastDateFormat fdf = FastDateFormat.getInstance("MM/dd/yyyy kk:mm");

    /**
     * @return value for display
     */
    public String getCheckOutDate() {
        StudyCheckout row = (StudyCheckout) getCurrentRowObject();
        return String.format("<!--%s-->", row.getCheckOutDate().toString())
                + fdf.format(row.getCheckOutDate());
    }

    /**
     * @return value for display
     */
    public String getCheckOutType() {
        StudyCheckout row = (StudyCheckout) getCurrentRowObject();
        return row.getCheckOutType().getCode();
    }

    /**
     * @return value for display
     */
    public String getUserIdentifier() {
        StudyCheckout row = (StudyCheckout) getCurrentRowObject();
        return CsmUserUtil.getGridIdentityUsername(row.getUserIdentifier());
    }

    /**
     * @return value for display
     */
    public String getCheckInDate() {
        StudyCheckout row = (StudyCheckout) getCurrentRowObject();
        if (row.getCheckInDate() == null) {
            return null;
        }
        return String.format("<!--%s-->", row.getCheckInDate().toString())
                + fdf.format(row.getCheckInDate());
    }

    /**
     * @return value for display
     */
    public String getCheckInUserIdentifier() {
        StudyCheckout row = (StudyCheckout) getCurrentRowObject();
        return CsmUserUtil.getGridIdentityUsername(row.getCheckInUserIdentifier());
    }
}

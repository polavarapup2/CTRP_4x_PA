package gov.nih.nci.accrual.accweb.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

/**
 * @author Hugh Reinhart
 * @since Jul 23, 2012
 * @param <DTO> class for dto's displayed in list
 */
public abstract class AbstractListAccrualAction<DTO> extends AbstractAccrualAction {

    private static final long serialVersionUID = -1827591152282978805L;

    /** Identifier for request bean to hold displaytag list. */
    private static final String REQUEST_ATTR_DISPLAY_LIST = "displayTagList";
    /** List to be displayed in displaytag table. */
    private List<DTO> displayTagList;

    /**
     * Set request bean with list to be displayed using displaytag table.
     */
    public abstract void loadDisplayList();


    /**
     * Default execute method for action classes.
     * @return action result
     */
    @Override
    public String execute() {
        loadDisplayList();
        return super.execute();
    }

    /**
     * @return the displayTaglist
     */
    public List<DTO> getDisplayTagList() {
        return displayTagList;
    }
    /**
     * @param displayTaglist the displayTaglist to set
     */
    public void setDisplayTagList(List<DTO> displayTaglist) {
        this.displayTagList = displayTaglist;
        ServletActionContext.getRequest().setAttribute(REQUEST_ATTR_DISPLAY_LIST , getDisplayTagList());
    }
}

package gov.nih.nci.accrual.accweb.action;

import gov.nih.nci.accrual.dto.util.AccrualCountsDto;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

/**
 * @author Kalpana Guthikonda
 * @since 10/24/2012
 */
public class AccrualCountsAction extends
        AbstractListAccrualAction<AccrualCountsDto> {

    private static final long serialVersionUID = -8243314140150276938L;

    private static final String ACCRUAL_COUNTS_SESS_KEY = "accrualCountsSession";
    
    /**
     * @return SUCCESS
     */
    public String loopback() {
        return SUCCESS;
    }

    @Override
    public void loadDisplayList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        request.getSession().removeAttribute(ACCRUAL_COUNTS_SESS_KEY);
        try {
            RegistryUser ru = PaServiceLocator.getInstance()
                    .getRegistryUserService()
                    .getUser(CaseSensitiveUsernameHolder.getUser());
            List<AccrualCountsDto> countList = getSearchTrialSvc()
                    .getAccrualCountsForUser(ru);
            setDisplayTagList(countList);
            request.getSession().setAttribute(ACCRUAL_COUNTS_SESS_KEY,
                    countList);
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
    }
}

package gov.nih.nci.pa.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyCheckout;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.convert.StudyCheckoutConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Hugh Reinhart
 * @since Jun 11, 2012
 */
public class CheckOutHistoryAction extends ActionSupport {

    private static final long serialVersionUID = -1889945159687412651L;

    private List<StudyCheckout> checkOutList;

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() throws PAException {
        HttpSession session = ServletActionContext.getRequest().getSession();
        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) session.getAttribute(Constants.TRIAL_SUMMARY);
        Ii spIi = IiConverter.convertToStudyProtocolIi(spDTO.getStudyProtocolId());
        StudyCheckoutConverter cv = new StudyCheckoutConverter();
        setCheckOutList(cv.convertFromDtoToDomains(PaRegistry.getStudyCheckoutService().getByStudyProtocol(spIi)));
        return SUCCESS;
    }

    /**
     * @return the checkOutList
     */
    public List<StudyCheckout> getCheckOutList() {
        return checkOutList;
    }

    /**
     * @param checkOutList the checkOutList to set
     */
    public void setCheckOutList(List<StudyCheckout> checkOutList) {
        this.checkOutList = checkOutList;
    }

}

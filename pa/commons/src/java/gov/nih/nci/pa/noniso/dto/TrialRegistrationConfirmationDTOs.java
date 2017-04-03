package gov.nih.nci.pa.noniso.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * @author Reshma
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "List")
public class TrialRegistrationConfirmationDTOs {

    @XmlElement(name = "item", type = TrialRegistrationConfirmationDTO.class)
    private List<TrialRegistrationConfirmationDTO> dtos = new ArrayList<TrialRegistrationConfirmationDTO>();
    /**
     * const
     */
    public TrialRegistrationConfirmationDTOs() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param dtos dtos
     */
    public TrialRegistrationConfirmationDTOs(
            List<TrialRegistrationConfirmationDTO> dtos) {
        super();
        this.dtos = dtos;
    }
    /**
     * 
     * @return dtos
     */
    public List<TrialRegistrationConfirmationDTO> getDtos() {
        return dtos;
    }
    /**
     * 
     * @param dtos dtos
     */
    public void setDtos(List<TrialRegistrationConfirmationDTO> dtos) {
        this.dtos = dtos;
    }
    
}

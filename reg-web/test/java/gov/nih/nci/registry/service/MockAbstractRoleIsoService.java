package gov.nih.nci.registry.service;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.FunctionalRoleDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.RolePaService;

import java.util.ArrayList;
import java.util.List;

public class MockAbstractRoleIsoService<DTO extends FunctionalRoleDTO> extends MockAbstractStudyIsoService<DTO> implements RolePaService<DTO>{

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.AbstractRoleIsoService#cascadeRoleStatus(gov.nih.nci.iso21090.Ii)
     */
    public void cascadeRoleStatus(Ii ii, Cd roleStatusCode) throws PAException {
        // TODO Auto-generated method stub
        
    }

    public List<DTO> getByStudyProtocol(Ii studyProtocolIi, DTO dto)
            throws PAException {
        List<DTO> returnList = new ArrayList<DTO>();
        return returnList;
    }

    public List<DTO> getByStudyProtocol(Ii studyProtocolIi, List<DTO> dto)
            throws PAException {
        List<DTO> returnList = new ArrayList<DTO>();
        return returnList;
    }
}

package gov.nih.nci.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.BaseDTO;
import gov.nih.nci.pa.service.BasePaService;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

public class MockAbstractBaseIsoService <DTO extends BaseDTO> implements BasePaService<DTO> {

    public DTO create(DTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public void delete(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        
    }

    public DTO get(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public DTO update(DTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public void validate(DTO dto) throws PAException {
        // TODO Auto-generated method stub
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DTO> getAll() throws PAException {
        return null;
    }



}

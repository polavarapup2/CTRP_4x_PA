package gov.nih.nci.registry.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudyDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyPaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Default adapter class for MockAbstractBaseIsoService.
 * 
 * @author amiruddinn
 * 
 * @param <DTO> The type of DTO processed
 */
public class MockAbstractStudyIsoService<DTO extends StudyDTO> extends MockAbstractBaseIsoService<DTO> implements StudyPaService<DTO> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DTO> getByStudyProtocol(Ii ii) throws PAException {
        return new ArrayList<DTO>();
    }

}

/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.CacheUtils;

import java.util.List;
import java.util.Map;

/**
 * @author dkrylov
 * 
 */
public final class StudySiteContactServiceCachingDecorator implements
        StudySiteContactService {

    private final StudySiteContactService service;

    /**
     * @param service
     *            StudySiteContactService
     */
    public StudySiteContactServiceCachingDecorator(
            StudySiteContactService service) {
        this.service = service;
    }

    @Override
    public List<StudySiteContactDTO> getByStudyProtocol(Ii studyProtocolIi,
            StudySiteContactDTO dto) throws PAException {
        return service.getByStudyProtocol(studyProtocolIi, dto);
    }

    @Override
    public StudySiteContactDTO get(Ii ii) throws PAException {
        return service.get(ii);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StudySiteContactDTO> getByStudySite(final Ii studySiteIi)
            throws PAException {
        final String key = IiConverter.convertToString(studySiteIi);
        return (List<StudySiteContactDTO>) CacheUtils.getFromCacheOrBackend(
                CacheUtils.getStudySiteContactsCache(), key, new CacheUtils.Closure() {
                    @Override
                    public Object execute() throws PAException {
                        return service.getByStudySite(studySiteIi);
                    }
                });

    }

    @Override
    public List<StudySiteContactDTO> getByStudyProtocol(Ii ii)
            throws PAException {
        return service.getByStudyProtocol(ii);
    }

    @Override
    public StudySiteContactDTO create(StudySiteContactDTO dto)
            throws PAException {
        return service.create(dto);
    }

    @Override
    public List<StudySiteContactDTO> getByStudyProtocol(Ii studyProtocolIi,
            List<StudySiteContactDTO> dto) throws PAException {
        return service.getByStudyProtocol(studyProtocolIi, dto);
    }

    @Override
    public StudySiteContactDTO update(StudySiteContactDTO dto)
            throws PAException {
        return service.update(dto);
    }

    @Override
    public void delete(Ii ii) throws PAException {
        service.delete(ii);
    }

    @Override
    public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi)
            throws PAException {
        return service.copy(fromStudyProtocolIi, toStudyProtocolIi);
    }

    @Override
    public void cascadeRoleStatus(Ii ii, Cd roleStatusCode) throws PAException {
        service.cascadeRoleStatus(ii, roleStatusCode);
    }

    @Override
    public void validate(StudySiteContactDTO dto) throws PAException {
        service.validate(dto);
    }

    @Override
    public List<StudySiteContactDTO> getAll() throws PAException {
        return service.getAll();
    }

}

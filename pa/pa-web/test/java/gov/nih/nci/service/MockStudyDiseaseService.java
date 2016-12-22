/**
 *
 */
package gov.nih.nci.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.util.ISOUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author asharma
 *
 */
public class MockStudyDiseaseService implements StudyDiseaseServiceLocal {
    private static List<StudyDiseaseDTO> dtoList;
    static {
        dtoList = new ArrayList<StudyDiseaseDTO>();
        StudyDiseaseDTO dto = new StudyDiseaseDTO();
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
        dto.setCtGovXmlIndicator(BlConverter.convertToBl(Boolean.FALSE));
        dtoList.add(dto);
        dto = new StudyDiseaseDTO();
        dto.setIdentifier(IiConverter.convertToIi("2"));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
        dto.setCtGovXmlIndicator(BlConverter.convertToBl(Boolean.TRUE));
        dtoList.add(dto);
    }

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
    public List<StudyDiseaseDTO> getByStudyProtocol(Ii ii) throws PAException {
        List<StudyDiseaseDTO> sdList = new ArrayList<StudyDiseaseDTO>();
        if (!ISOUtil.isIiNull(ii)) {
            for (StudyDiseaseDTO d : dtoList) {
                if (d.getStudyProtocolIdentifier().getExtension().equals(ii.getExtension())) {
                    sdList.add(d);
                }
            }
        }
        return sdList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyDiseaseDTO create(StudyDiseaseDTO dto) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Ii ii) throws PAException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyDiseaseDTO get(Ii ii) throws PAException {
        StudyDiseaseDTO sd = new StudyDiseaseDTO();
        sd.setDiseaseIdentifier(IiConverter.convertToIi("1"));
        sd.setCtGovXmlIndicator(BlConverter.convertToBl(Boolean.FALSE));
        return sd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyDiseaseDTO update(StudyDiseaseDTO dto) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(StudyDiseaseDTO dto) throws PAException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyDiseaseDTO> getAll() throws PAException {
        return null;
    }
    
    

}

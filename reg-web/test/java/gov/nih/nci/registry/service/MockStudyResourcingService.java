/**
 *
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.RealConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingService.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockStudyResourcingService implements StudyResourcingServiceLocal {
    private static List<StudyResourcingDTO> list;
    static {
        list = new ArrayList<StudyResourcingDTO>();
        StudyResourcingDTO studyRDto = new StudyResourcingDTO();
        studyRDto.setFundingMechanismCode(CdConverter.convertStringToCd("B09"));
        studyRDto.setNihInstitutionCode(CdConverter.convertStringToCd("AG"));
        studyRDto.setNciDivisionProgramCode(CdConverter.convertStringToCd("CCR"));
        studyRDto.setSerialNumber(StConverter.convertToSt("123456"));
        studyRDto.setFundingPercent(RealConverter.convertToReal(50d));
        studyRDto.setIdentifier(IiConverter.convertToIi("1"));
        studyRDto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
        list.add(studyRDto);
        studyRDto = new StudyResourcingDTO();
        studyRDto.setFundingMechanismCode(CdConverter.convertStringToCd("B09"));
        studyRDto.setNihInstitutionCode(CdConverter.convertStringToCd("AG"));
        studyRDto.setNciDivisionProgramCode(CdConverter.convertStringToCd("CCR"));
        studyRDto.setSerialNumber(StConverter.convertToSt("123456"));
        studyRDto.setFundingPercent(RealConverter.convertToReal(50d));
        studyRDto.setIdentifier(IiConverter.convertToIi("2"));
        studyRDto.setStudyProtocolIdentifier(IiConverter.convertToIi("3"));
        list.add(studyRDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyResourcingDTO createStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteStudyResourcingById(StudyResourcingDTO studyResourcingDTO) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyResourcingDTO getStudyResourcingById(Ii studyResourceIi) throws PAException {
        StudyResourcingDTO matchingDto = new StudyResourcingDTO();
        for (StudyResourcingDTO dto : list) {
            if (dto.getIdentifier().getExtension().equals(studyResourceIi.getExtension())) {
                matchingDto = dto;
            }
        }
        return matchingDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyResourcingDTO> getStudyResourcingByStudyProtocol(Ii studyProtocolIi) throws PAException {
        List<StudyResourcingDTO> matchingDtosList = new ArrayList<StudyResourcingDTO>();
        for (StudyResourcingDTO dto : list) {
            if (dto.getStudyProtocolIdentifier().getExtension().equals(studyProtocolIi.getExtension())) {
                matchingDtosList.add(dto);
            }
        }
        return matchingDtosList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyResourcingDTO> getSummary4ReportedResourcing(Ii studyProtocolIi) throws PAException {
        if (studyProtocolIi != null && studyProtocolIi.getExtension().equals("3")) {
            return new ArrayList<StudyResourcingDTO>();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyResourcingDTO updateStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException {
        return null;
    }

    public void validate(Method method, Boolean nciFunded, String nciTrialNumber, Long leadOrgPoId,
            List<StudyResourcingDTO> dtos) throws PAException {
    }

    @Override
    public void validate(StudyResourcingDTO studyResourcingDTO) throws PAException {
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
    public List<StudyResourcingDTO> getByStudyProtocol(Ii ii) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyResourcingDTO create(StudyResourcingDTO dto) throws PAException {
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
    public StudyResourcingDTO get(Ii ii) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyResourcingDTO update(StudyResourcingDTO dto) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyResourcingDTO> getAll() throws PAException {
        return null;
    }

    @Override
    public List<StudyResourcingDTO> getActiveStudyResourcingByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        return getStudyResourcingByStudyProtocol(studyProtocolIi);
    }

   
    public void matchToExistentGrants(
            List<StudyResourcingDTO> studyResourcingDTOs, Ii identifier)
            throws PAException {
        // TODO Auto-generated method stub
        
    }

	@Override
	public StudyResourcingDTO getSummary4ReportedResourcingBySpAndOrgId(
			Ii studyProtocolIi, Long orgId) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public PAException validateNoException(Method method, Boolean nciFunded,
            String nciTrialNumber, Long leadOrgPoId,
            List<StudyResourcingDTO> dtos) {       
        return null;
    }
}

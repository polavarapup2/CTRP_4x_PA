/**
 *
 */
package gov.nih.nci.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyMilestoneServicelocal;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockStudyMilestoneService extends MockAbstractBaseIsoService<StudyMilestoneDTO> implements
        StudyMilestoneServicelocal {
    private static List<StudyMilestoneDTO> mileList;
    static {
        mileList = new ArrayList<StudyMilestoneDTO>();
        StudyMilestoneDTO dto = new StudyMilestoneDTO();
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.ADMINISTRATIVE_QC_START));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
        dto.setMilestoneDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("06/19/2009")));
        dto.setCreationDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("06/19/2009")));
        mileList.add(dto);
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
    public List<StudyMilestoneDTO> getByStudyProtocol(Ii ii) throws PAException {
        List<StudyMilestoneDTO> returnList = new ArrayList<StudyMilestoneDTO>();
        for (StudyMilestoneDTO dto : mileList) {
            if (dto.getStudyProtocolIdentifier().getExtension().equals(ii.getExtension())) {
                returnList.add(dto);
            }
        }
        return returnList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyMilestoneDTO getCurrentByStudyProtocol(Ii studyProtocolIi) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyMilestoneDTO create(StudyMilestoneDTO dto) throws PAException {
        if (dto.getStudyProtocolIdentifier().getExtension().equals("9")) {
            throw new PAException("test");
        }
        return dto;
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
    public StudyMilestoneDTO get(Ii ii) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyMilestoneDTO update(StudyMilestoneDTO dto) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyMilestoneDTO> search(StudyMilestoneDTO dto, LimitOffset pagingParams) throws PAException,
            TooManyResultsException {
        return null;
    }
    
    @Override
    public void deleteMilestoneByCodeAndStudy(MilestoneCode status, Ii spIi)
          throws PAException {
   }
    
    @Override
    public void updateMilestoneCodeCommentWithDateAndUser(
         StudyMilestoneDTO dto, String reason, String submitterFullName)
               throws PAException {
    }



}

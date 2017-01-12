package gov.nih.nci.accrual.accweb.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.PlannedProcedureDTO;
import gov.nih.nci.pa.iso.dto.PlannedSubstanceAdministrationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author guthikondak
 */
public class MockPaPlannedActivityServiceBean implements PlannedActivityServiceRemote {

    private List<PlannedEligibilityCriterionDTO> pecList;

    /**
     * Default constructor.
     */
    public MockPaPlannedActivityServiceBean() {
        pecList = new ArrayList<PlannedEligibilityCriterionDTO>();
        PlannedEligibilityCriterionDTO dto = new PlannedEligibilityCriterionDTO();
        dto.setCriterionName(StConverter.convertToSt("GENDER"));
        dto.setEligibleGenderCode(CdConverter.convertToCd(PatientGenderCode.FEMALE));
        pecList.add(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void copyPlannedEligibilityStudyCriterions(Ii fromStudyProtocolIi, Ii toStudyProtocolIi) throws PAException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedEligibilityCriterionDTO createPlannedEligibilityCriterion(PlannedEligibilityCriterionDTO dto)
            throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedProcedureDTO createPlannedProcedure(PlannedProcedureDTO dto) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedSubstanceAdministrationDTO createPlannedSubstanceAdministration(PlannedSubstanceAdministrationDTO dto)
            throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePlannedEligibilityCriterion(Ii ii) throws PAException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlannedActivityDTO> getByArm(Ii ii) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedEligibilityCriterionDTO getPlannedEligibilityCriterion(Ii ii) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlannedEligibilityCriterionDTO> getPlannedEligibilityCriterionByStudyProtocol(Ii ii) throws PAException {
        return pecList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedProcedureDTO getPlannedProcedure(Ii ii) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlannedProcedureDTO> getPlannedProcedureByStudyProtocol(Ii ii) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedSubstanceAdministrationDTO getPlannedSubstanceAdministration(Ii ii) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlannedSubstanceAdministrationDTO> getPlannedSubstanceAdministrationByStudyProtocol(Ii ii)
            throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedEligibilityCriterionDTO updatePlannedEligibilityCriterion(PlannedEligibilityCriterionDTO dto)
            throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedProcedureDTO updatePlannedProcedure(PlannedProcedureDTO dto) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedSubstanceAdministrationDTO updatePlannedSubstanceAdministration(PlannedSubstanceAdministrationDTO dto)
            throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedActivityDTO create(PlannedActivityDTO dto) throws PAException {
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
    public PlannedActivityDTO get(Ii ii) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedActivityDTO update(PlannedActivityDTO dto) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(PlannedActivityDTO dto) throws PAException {
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
    public List<PlannedActivityDTO> getByStudyProtocol(Ii ii) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ii getDuplicateIi(PlannedActivityDTO dto) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlannedActivityDTO> getAll() throws PAException {
        return null;
    }
    /**
     * {@inheritDoc}
     */
    public void reorderInterventions(Ii studyProtocolIi, List<String> ids)
            throws PAException {
        throw new PAException("Not supported.");
    }

    @Override
    public int getMaxDisplayOrderValue(Ii studyProtocolIi) throws PAException {
        // TODO Auto-generated method stub
        return 0;
    }
}

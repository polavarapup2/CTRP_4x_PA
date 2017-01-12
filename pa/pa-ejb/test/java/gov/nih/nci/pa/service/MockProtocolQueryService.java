/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;

/**
 * @author Vrushali
 *
 */
public class MockProtocolQueryService extends AbstractBaseSearchBean<StudyProtocol>
    implements ProtocolQueryServiceLocal {
    static List<StudyProtocolQueryDTO> list;
    static {
        list = new ArrayList<StudyProtocolQueryDTO>();
        StudyProtocolQueryDTO spQueryDTO = new StudyProtocolQueryDTO();
        spQueryDTO.setStudyProtocolId(1L);
        spQueryDTO.setNciIdentifier("NCI-2009-00001");
        spQueryDTO.setLocalStudyProtocolIdentifier("localStudyProtocolIdentifier");
        spQueryDTO.setOfficialTitle("officialTitle");
        spQueryDTO.setStudyStatusCode(StudyStatusCode.ACTIVE);
        spQueryDTO.setStudyStatusDate(PAUtil.dateStringToTimestamp("4/15/2009"));
        spQueryDTO.setLeadOrganizationId(1L);
        list.add(spQueryDTO);
        spQueryDTO = new StudyProtocolQueryDTO();
        spQueryDTO.setStudyProtocolId(2L);
        spQueryDTO.setNciIdentifier("NCI-2009-00001");
        spQueryDTO.setLocalStudyProtocolIdentifier("DupTestinglocalStudyProtocolId");
        spQueryDTO.setOfficialTitle("officialTitle");
        spQueryDTO.setStudyStatusCode(StudyStatusCode.ACTIVE);
        spQueryDTO.setStudyStatusDate(PAUtil.dateStringToTimestamp("4/15/2009"));
        spQueryDTO.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.SUBMITTED);
        list.add(spQueryDTO);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyProtocolQueryDTO> getStudyProtocolByCriteria(
            StudyProtocolQueryCriteria sc) throws PAException {
        if(sc.getOfficialTitle() != null && sc.getOfficialTitle().equalsIgnoreCase("ThrowException")) {
            throw new PAException("Test");
        }
        List<StudyProtocolQueryDTO> returnList = new ArrayList<StudyProtocolQueryDTO>();
        for (StudyProtocolQueryDTO sp: list) {
            if(sp.getLocalStudyProtocolIdentifier().equals(sc.getLeadOrganizationTrialIdentifier())) {
                // TODO need to check with OrgId too
                returnList.add(sp);
            }
        }
        return returnList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyProtocolQueryDTO getTrialSummaryByStudyProtocolId(
            Long studyProtocolId) throws PAException {

        for (StudyProtocolQueryDTO sp: list) {
            if (studyProtocolId != null && studyProtocolId == 3) {
                throw new PAException("test");
            }
            if(sp.getStudyProtocolId().equals(studyProtocolId)) {
                return sp;
            }
        }
        return new StudyProtocolQueryDTO();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyProtocol> getStudyProtocolByOrgIdentifier(Long orgIdentifier) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyProtocolQueryDTO> getStudyProtocolByCriteriaForReporting(StudyProtocolQueryCriteria pSc)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyProtocol> getStudyProtocolQueryResultList(StudyProtocolQueryCriteria criteria) throws PAException {
        return null;
    }

    @Override
    public List<String> getOfficialTitles(String matchString)
            throws PAException {
        return null;
    }

    @Override
    public void populateMilestoneHistory(List<StudyProtocolQueryDTO> trials)
            throws PAException { 
    }

    @Override
    public List<StudyProtocolQueryDTO> getStudyProtocolByAgentNsc(String agentNsc) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<StudyProtocolQueryDTO> getActiveInactiveStudyProtocolsById(
         Long studyProtocolId) throws PAException {
         // TODO Auto-generated method stub
         return null;
    }

    @Override
    public List<StudyProtocolQueryDTO> getStudyProtocolByCriteria(
            StudyProtocolQueryCriteria pSc,
            ProtocolQueryPerformanceHints... hint) throws PAException {       
        return getStudyProtocolByCriteria(pSc);
    }
    
    @Override
    public List<StudyProtocolQueryDTO> getWorkload() throws PAException {       
        return new ArrayList<>();
    }
}
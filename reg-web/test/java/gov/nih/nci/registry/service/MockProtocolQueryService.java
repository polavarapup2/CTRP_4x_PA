/**
 *
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;

/**
 * @author Vrushali
 *
 */
public class MockProtocolQueryService extends AbstractBaseSearchBean<StudyProtocol> implements ProtocolQueryServiceLocal {
    public static List<StudyProtocolQueryDTO> list;
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
        spQueryDTO.setPiId(2L);
        spQueryDTO.setProprietaryTrial(false);
        spQueryDTO.setCtgovXmlRequiredIndicator(true);
        spQueryDTO.getLastCreated().setUserLastCreated("TestUser@test.com");
        spQueryDTO.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTED);
        spQueryDTO.setSearcherTrialOwner(true);
        list.add(spQueryDTO);
        spQueryDTO = new StudyProtocolQueryDTO();
        spQueryDTO.setStudyProtocolId(2L);
        spQueryDTO.setLeadOrganizationId(2L);
        spQueryDTO.setNciIdentifier("NCI-2009-00002");
        spQueryDTO.setLocalStudyProtocolIdentifier("DupTestinglocalStudyProtocolId");
        spQueryDTO.setOfficialTitle("officialTitle");
        spQueryDTO.setStudyStatusCode(StudyStatusCode.ACTIVE);
        spQueryDTO.setStudyStatusDate(PAUtil.dateStringToTimestamp("4/15/2009"));
        spQueryDTO.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTED);
        spQueryDTO.setCtgovXmlRequiredIndicator(true);
        list.add(spQueryDTO);
        spQueryDTO = new StudyProtocolQueryDTO();
        spQueryDTO.setLeadOrganizationId(3L);
        spQueryDTO.setStudyProtocolId(3L);
        spQueryDTO.setNciIdentifier("NCI-2009-00003");
        spQueryDTO.setLocalStudyProtocolIdentifier("localStudyProtocolIdentifier3");
        spQueryDTO.setOfficialTitle("officialTitle");
        spQueryDTO.setStudyStatusCode(StudyStatusCode.ACTIVE);
        spQueryDTO.setStudyStatusDate(PAUtil.dateStringToTimestamp("4/15/2009"));
        spQueryDTO.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        spQueryDTO.setCtgovXmlRequiredIndicator(true);
        spQueryDTO.setSummary4FundingSponsorType(SummaryFourFundingCategoryCode.INSTITUTIONAL.getName());
        spQueryDTO.getLastCreated().setUserLastCreated("firstName");
        list.add(spQueryDTO);
        
        // bump up the list to go over a single page of results.
        for (int i = 10; i < 20; i++) {
            spQueryDTO = new StudyProtocolQueryDTO();
            spQueryDTO.setLeadOrganizationId(3L);
            spQueryDTO.setStudyProtocolId(3L);
            spQueryDTO.setNciIdentifier("NCI-2009-000" + i);
            spQueryDTO
                    .setLocalStudyProtocolIdentifier("localStudyProtocolIdentifier"
                            + i);
            spQueryDTO.setOfficialTitle("officialTitle");
            spQueryDTO.setStudyStatusCode(StudyStatusCode.ACTIVE);
            spQueryDTO.setStudyStatusDate(PAUtil
                    .dateStringToTimestamp("4/15/2009"));
            spQueryDTO
                    .setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTED);
            spQueryDTO.setCtgovXmlRequiredIndicator(true);
            if (i != 15) { // leave one null to test handling of nulls
                spQueryDTO.setSummary4FundingSponsorType(SummaryFourFundingCategoryCode.NATIONAL.getName());
            }
            list.add(spQueryDTO);
        }
        
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal#getStudyProtocolByCriteria(gov.nih.nci.pa.dto.StudyProtocolQueryCriteria)
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
            if(sc.getNciIdentifier() != null && !sc.getNciIdentifier().equals("")
                    && sp.getNciIdentifier().equalsIgnoreCase(sc.getNciIdentifier())) {
                returnList.add(sp);
            }
            if(sc.getLeadOrganizationIds() != null && sc.getLeadOrganizationIds().contains(sp.getLeadOrganizationId())) {
                returnList.add(sp);
            }
            if (sc.isMyTrialsOnly() != null && sc.isMyTrialsOnly()) {
               return list;
            }
        }
        return returnList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal#getTrialSummaryByStudyProtocolId(java.lang.Long)
     */
    @Override
    public StudyProtocolQueryDTO getTrialSummaryByStudyProtocolId(
            Long studyProtocolId) throws PAException {

        for (StudyProtocolQueryDTO sp: list) {
            if(sp.getStudyProtocolId().equals(studyProtocolId)) {
                return sp;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal#getStudyProtocolByOrgIdentifier(java.lang.Long)
     */
    @Override
    public List<StudyProtocol> getStudyProtocolByOrgIdentifier(Long orgIdentifier) throws PAException {
        List<StudyProtocol> returnList = new ArrayList<StudyProtocol>();
        for (StudyProtocolQueryDTO spDto : list) {
            if (spDto.getLeadOrganizationId().equals(orgIdentifier)) {
                StudyProtocol sp = populateStudyProtocol(spDto);
                returnList.add(sp);
            }
        }
        return returnList;
    }

    /**
     * @param spDto
     * @return
     */
    private StudyProtocol populateStudyProtocol(StudyProtocolQueryDTO spDto) {
        StudyProtocol sp = new StudyProtocol();
        sp.setId(spDto.getStudyProtocolId());
        Set<Ii> others = new HashSet<Ii>();
        Ii nciid = IiConverter.convertToAssignedIdentifierIi(spDto.getNciIdentifier());
        others.add(nciid);
        sp.setOtherIdentifiers(others);
        sp.setOfficialTitle(spDto.getOfficialTitle());
        sp.setProprietaryTrialIndicator(Boolean.valueOf(spDto.isProprietaryTrial()));
        sp.setCtgovXmlRequiredIndicator(spDto.getCtgovXmlRequiredIndicator());
        sp.setUserLastCreated(null);
        StudySite ss = new StudySite();
        ss.setFunctionalCode(StudySiteFunctionalCode.LEAD_ORGANIZATION);
        ss.setLocalStudyProtocolIdentifier(spDto.getLocalStudyProtocolIdentifier());
        sp.getStudySites().add(ss);
        return sp;
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
    public List<StudyProtocol> getStudyProtocolQueryResultList(
            StudyProtocolQueryCriteria criteria) throws PAException {
        List<StudyProtocol> returnList = new ArrayList<StudyProtocol>();
        for (StudyProtocolQueryDTO spDto : list) {
            StudyProtocol sp = populateStudyProtocol(spDto);
            returnList.add(sp);
        }
        return returnList;
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
         //TODO Auto-generated method stub
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

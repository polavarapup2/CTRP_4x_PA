/**
 * 
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Vrushali
 *
 */
public class MockStudySiteService extends MockAbstractRoleIsoService<StudySiteDTO> implements
        StudySiteServiceLocal {
     static List<StudySiteDTO> list;
     static{
         list = new ArrayList<StudySiteDTO>();
         StudySiteDTO  dto = new StudySiteDTO();
         dto.setIdentifier(IiConverter.convertToIi("1"));
         dto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
         dto.setLocalStudyProtocolIdentifier(StConverter.convertToSt("localStudyProtocolIdentifier"));
         dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
         list.add(dto);
         dto = new StudySiteDTO();
         dto.setIdentifier(IiConverter.convertToIi("1"));
         dto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.FUNDING_SOURCE));
         dto.setResearchOrganizationIi(IiConverter.convertToPoResearchOrganizationIi("1"));
         dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
         dto.setStatusCode(CdConverter.convertStringToCd("PENDING"));
         list.add(dto);
         dto = new StudySiteDTO();
         dto.setIdentifier(IiConverter.convertToIi("1"));
         dto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
         dto.setHealthcareFacilityIi(IiConverter.convertToPoHealthCareFacilityIi("1"));
         dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
         dto.setProgramCodeText(StConverter.convertToSt("programCodeText"));
         list.add(dto);
         dto = new StudySiteDTO();
         dto.setIdentifier(IiConverter.convertToIi("2"));
         dto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
         dto.setHealthcareFacilityIi(IiConverter.convertToPoHealthCareFacilityIi("1"));
         dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
         dto.setProgramCodeText(StConverter.convertToSt("programCodeText"));
         dto.setStatusCode(CdConverter.convertStringToCd("PENDING"));
         dto.setStatusDateRange(IvlConverter.convertTs().convertToIvl("12/16/2009",
                 "12/16/2009"));
         list.add(dto);
         dto = new StudySiteDTO();
         dto.setIdentifier(IiConverter.convertToIi("1"));
         dto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
         dto.setResearchOrganizationIi(IiConverter.convertToPoResearchOrganizationIi("1"));
         dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
         list.add(dto);
     }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.RolePaService#getByStudyProtocol(gov.nih.nci.iso21090.Ii, java.lang.Object)
     */
     @Override
    public List<StudySiteDTO> getByStudyProtocol(Ii studyProtocolIi,
            StudySiteDTO dto) throws PAException {
        
        List<StudySiteDTO> matchDtosList = new ArrayList<StudySiteDTO>();
        for (StudySiteDTO studyPDto: list) {
            if(studyPDto.getStudyProtocolIdentifier().getExtension()
                    .equals(studyProtocolIi.getExtension())
                    && studyPDto.getFunctionalCode().getCode().equals(dto.getFunctionalCode().getCode())) {
                matchDtosList.add(studyPDto);
            }
        }
        return matchDtosList;
    }
     @Override
     public List<StudySiteDTO> getByStudyProtocol(Ii studyProtocolIi, List<StudySiteDTO> dtoList)
         throws PAException {
         List<StudySiteDTO> returnList = new ArrayList<StudySiteDTO>();
         for (StudySiteDTO dto: dtoList){
             for (StudySiteDTO studyPDto: list) {
                 if(studyPDto.getStudyProtocolIdentifier().getExtension()
                         .equals(studyProtocolIi.getExtension())
                         && studyPDto.getFunctionalCode().getCode().equals(dto.getFunctionalCode().getCode())) {
                     returnList.add(studyPDto);
                 }
             }
         }
         return returnList;
     }

    public List<StudySiteDTO> search(StudySiteDTO dto, LimitOffset pagingParams)
            throws PAException, TooManyResultsException {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public StudySiteDTO get(Ii ii) {
     return new StudySiteDTO();   
    }
    
    public Ii getStudySiteIiByTrialAndPoHcfIi(Ii studyProtocolIi, Ii poHcfIi) throws EntityValidationException,
            CurationException, PAException, TooManyResultsException {
        return IiConverter.convertToStudySiteIi(1L);
    }
    @Override
    public Organization getOrganizationByStudySiteId(Long ssid) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Set<Long> getAllAssociatedTrials(String poOrgId, StudySiteFunctionalCode functionalCode) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Set<Long> getTrialsAssociatedWithTreatingSite(Long paHcfId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }
}

/**
 *
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;

/**
 * @author Vrushali
 *
 */
public class MockStudySiteAccrualStatusService extends AbstractBaseSearchBean<StudySiteAccrualStatus>
    implements StudySiteAccrualStatusServiceLocal {
    static List<StudySiteAccrualStatusDTO> dtoList;
    static {
        dtoList = new ArrayList<StudySiteAccrualStatusDTO>();
        StudySiteAccrualStatusDTO dto = new StudySiteAccrualStatusDTO();
        dto.setIdentifier(IiConverter.convertToStudySiteAccrualStatusIi(1L));
        dto.setStatusCode(CdConverter.convertStringToCd("PENDING"));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("12/16/2009")));
        dto.setStudySiteIi(IiConverter.convertToStudySiteIi(1L));
        dtoList.add(dto);

    }
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudySiteAccrualStatusService#createStudySiteAccrualStatus(gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO)
     */
    @Override
    public StudySiteAccrualStatusDTO createStudySiteAccrualStatus(
            StudySiteAccrualStatusDTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudySiteAccrualStatusService#getCurrentStudySiteAccrualStatusByStudySite(gov.nih.nci.iso21090.Ii)
     */
    @Override
    public StudySiteAccrualStatusDTO getCurrentStudySiteAccrualStatusByStudySite(
            Ii studySiteIi) throws PAException {
        StudySiteAccrualStatusDTO  returnDto = new StudySiteAccrualStatusDTO();
        for (StudySiteAccrualStatusDTO dto :dtoList) {
            if (!ISOUtil.isIiNull(studySiteIi) && !ISOUtil.isIiNull(dto.getStudySiteIi())
                    && studySiteIi.getExtension().equalsIgnoreCase(dto.getStudySiteIi().getExtension())) {
                returnDto = dto;
            }
        }
        return returnDto;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudySiteAccrualStatusService#getStudySiteAccrualStatus(gov.nih.nci.iso21090.Ii)
     */
    @Override
    public StudySiteAccrualStatusDTO getStudySiteAccrualStatus(Ii ii)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudySiteAccrualStatusService#getStudySiteAccrualStatusByStudySite(gov.nih.nci.iso21090.Ii)
     */
    @Override
    public List<StudySiteAccrualStatusDTO> getStudySiteAccrualStatusByStudySite(
            Ii studySiteIi) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudySiteAccrualStatusService#updateStudySiteAccrualStatus(gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO)
     */
    @Override
    public StudySiteAccrualStatusDTO updateStudySiteAccrualStatus(
            StudySiteAccrualStatusDTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, StudySiteAccrualStatus> getCurrentStudySiteAccrualStatus(Long[] ids) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudySiteAccrualStatusService#softDelete(gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO)
     */
    @Override
    public void softDelete(StudySiteAccrualStatusDTO dto) throws PAException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudySiteAccrualStatusService#getDeletedByStudySite(gov.nih.nci.iso21090.Ii)
     */
    @Override
    public List<StudySiteAccrualStatusDTO> getDeletedByStudySite(Ii studySiteIi)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<StatusDto> getStatusHistory(Ii identifier)
            throws PAException {      
        return new ArrayList<>();
    }

    @Override
    public boolean ifCloseStatusExistsInHistory(Ii studySiteIi)
            throws PAException {
        // TODO Auto-generated method stub
        return false;
    }

}

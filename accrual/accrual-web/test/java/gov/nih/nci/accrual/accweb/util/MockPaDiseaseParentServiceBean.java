package gov.nih.nci.accrual.accweb.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseParentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseParentServiceRemote;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

public class MockPaDiseaseParentServiceBean implements PDQDiseaseParentServiceRemote {

    /** mock data. */
    public static List<PDQDiseaseParentDTO> dtos;

    static {
        dtos = new ArrayList<PDQDiseaseParentDTO>();
        PDQDiseaseParentDTO r = new PDQDiseaseParentDTO();
        r.setIdentifier(IiConverter.convertToIi(1L));
        r.setParentDiseaseIdentifier(IiConverter.convertToIi(1L));
        r.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
        r.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/2008")));
        dtos.add(r);
        r = new PDQDiseaseParentDTO();
        r.setIdentifier(IiConverter.convertToIi(2L));
        r.setParentDiseaseIdentifier(IiConverter.convertToIi(2L));
        r.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
        r.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/2008")));
        dtos.add(r);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PDQDiseaseParentDTO> getByChildDisease(Ii arg0) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PDQDiseaseParentDTO> getByChildDisease(Ii[] arg0) throws PAException {
        return dtos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PDQDiseaseParentDTO> getByParentDisease(Ii arg0) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQDiseaseParentDTO create(PDQDiseaseParentDTO arg0) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Ii arg0) throws PAException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQDiseaseParentDTO get(Ii arg0) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PDQDiseaseParentDTO update(PDQDiseaseParentDTO arg0) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(PDQDiseaseParentDTO arg0) throws PAException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PDQDiseaseParentDTO> getAll() throws PAException {
        return null;
    }

	@Override
	public void syncDisease(PDQDiseaseDTO currDisease, List<PDQDiseaseParentDTO> parents,
			List<PDQDiseaseParentDTO> childs,
			List<PDQDiseaseParentDTO> parentsToAdd,
			List<PDQDiseaseParentDTO> childsToAdd) throws PAException {
		// TODO Auto-generated method stub
		
	}

	

}

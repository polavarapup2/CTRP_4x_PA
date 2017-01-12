package gov.nih.nci.accrual.accweb.util;

import gov.nih.nci.accrual.dto.util.POPatientDTO;
import gov.nih.nci.accrual.service.util.POPatientService;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;

public class MockPaPatientServiceBean  implements POPatientService {

    public POPatientDTO create(POPatientDTO arg0) throws PAException {
        POPatientDTO dto = new POPatientDTO();
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setPlayerIdentifier(IiConverter.convertToIi("PT01"));
        return dto;
    }

    public POPatientDTO get(Ii arg0) throws PAException {
        POPatientDTO dto = new POPatientDTO();
        return dto;
    }

    public POPatientDTO update(POPatientDTO arg0) throws PAException {
        POPatientDTO dto = new POPatientDTO();
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setPlayerIdentifier(IiConverter.convertToIi("PT01"));
        return dto;
    }

}

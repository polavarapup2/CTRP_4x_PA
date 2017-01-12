package gov.nih.nci.accrual.accweb.decorator;

import gov.nih.nci.accrual.dto.PatientListDto;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.displaytag.decorator.TableDecorator;

/**
 * @author Hugh Reinhart
 * @since Aug 4, 2012
 */
public class SearchPatientDecorator extends TableDecorator {

    /**
     * @return registration date
     */
    public String getRegistrationDate() {
        PatientListDto patient = (PatientListDto) getCurrentRowObject();
        if (patient.getRegistrationDate() != null) {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            return format.format(patient.getRegistrationDate());
        }
        return "";
    }

    /**
     * @return last updated date
     */
    public String getDateLastUpdated() {
        PatientListDto patient = (PatientListDto) getCurrentRowObject();
        if (patient.getDateLastUpdated() != null) {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US);
            return format.format(patient.getDateLastUpdated());
        }
        return "";
    }
}

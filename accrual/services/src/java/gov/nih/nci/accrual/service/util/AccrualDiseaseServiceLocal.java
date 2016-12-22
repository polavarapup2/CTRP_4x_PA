package gov.nih.nci.accrual.service.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualDisease;

import java.util.List;

import javax.ejb.Local;

/**
 * @author Hugh Reinhart
 * @since Dec 14, 2012
 */
@Local
public interface AccrualDiseaseServiceLocal {

    /**
     * Get using internal id.
     * @param id id
     * @return the object
     */
    AccrualDisease get(Long id);
    /**
     * Get using iso Ii.
     * @param ii iso identifier
     * @return the object
     */
    AccrualDisease get(Ii ii);
    /**
     * Get using code.
     * @param codeSystem the disease terminology code system
     * @param diseaseCode the code to search for
     * @return the object
     */
    AccrualDisease getByCode(String codeSystem, String diseaseCode);
    /**
     * Search for desired disease.
     * @param searchCriteria criteria
     * @return all matching diseases
     */
    List<AccrualDisease> search(AccrualDisease searchCriteria);
    /**
     * Return if the disease code is mandatory for patients on a given trial.
     * @param spId spId the StudyProtcol.id
     * @return true if mandatory
     */
    boolean diseaseCodeMandatory(Long spId);
}

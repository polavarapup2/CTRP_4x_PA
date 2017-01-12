/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyDisease;
import gov.nih.nci.pa.iso.convert.StudyDiseaseConverter;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class StudyDiseaseBeanLocal extends
        AbstractStudyIsoService<StudyDiseaseDTO, StudyDisease, StudyDiseaseConverter> implements
        StudyDiseaseServiceLocal {
    
    @EJB private PDQDiseaseServiceLocal pdqDiseaseServiceLocal;

    /**
     * Validates the StudyDiseaseDTO.
     * @param dto The dto to validate
     * @throws PAException If a validation error occurs
     */
    void businessRules(StudyDiseaseDTO dto) throws PAException {
        StringBuilder errorMsg = new StringBuilder();
        if (ISOUtil.isIiNull(dto.getDiseaseIdentifier())) {
            errorMsg.append("Missing Disease/Condition. ");
        } else {
            validateDisease(dto.getDiseaseIdentifier(), errorMsg);
            validateNoduplicate(dto, errorMsg);
        }
        if (errorMsg.length() > 0) {
            throw new PAException(errorMsg.toString());
        }
    }

    /**
     * Validates the disease.
     * @param diseaseIi The diseaseIi
     * @param errorMsg The StringBuilder collecting error messages
     * @throws PAException If an error occurs
     */
    void validateDisease(Ii diseaseIi, StringBuilder errorMsg) throws PAException {
        PDQDiseaseDTO pdqDiseaseDTO = pdqDiseaseServiceLocal.get(diseaseIi);
        if (StringUtils.isEmpty(StConverter.convertToString(pdqDiseaseDTO.getDisplayName()))) {
            errorMsg.append("Diseases without a menu display name are not suitable for reporting.  ");
        }
    }

    /**
     * Validates that there will be no duplicate.
     * @param dto The StudyDiseaseDTO
     * @param errorMsg The StringBuilder collecting error messages
     * @throws PAException If an error occurs
     */
    void validateNoduplicate(StudyDiseaseDTO dto, StringBuilder errorMsg) throws PAException {
        boolean isNew = ISOUtil.isIiNull(dto.getIdentifier());
        // no duplicate diseases in a study
        long newDiseaseId = IiConverter.convertToLong(dto.getDiseaseIdentifier());
        List<StudyDiseaseDTO> sdList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
        for (StudyDiseaseDTO sd : sdList) {
            if (newDiseaseId == IiConverter.convertToLong(sd.getDiseaseIdentifier())
                    && (isNew || !StringUtils.equals(dto.getIdentifier().getExtension(), sd.getIdentifier()
                        .getExtension()))) {
                errorMsg.append("Redundancy error:  this trial already includes the selected disease.  ");
                break;
            }
        }
    }

    /**
     * @param dto new study disease
     * @return created study disease
     * @throws PAException exception
     */
    @Override
    public StudyDiseaseDTO create(StudyDiseaseDTO dto) throws PAException {
        businessRules(dto);
        return super.create(dto);
    }

    /**
     * @param dto changed study disease
     * @return updated study disease
     * @throws PAException exception
     */
    @Override
    public StudyDiseaseDTO update(StudyDiseaseDTO dto) throws PAException {
        businessRules(dto);
        return super.update(dto);
    }

    /**
     * @param pdqDiseaseServiceLocal the pdqDiseaseServiceLocal to set
     */
    public void setPdqDiseaseServiceLocal(PDQDiseaseServiceLocal pdqDiseaseServiceLocal) {
        this.pdqDiseaseServiceLocal = pdqDiseaseServiceLocal;
    }

}

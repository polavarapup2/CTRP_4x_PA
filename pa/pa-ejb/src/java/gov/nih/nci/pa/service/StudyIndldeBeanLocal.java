/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyIndlde;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.iso.convert.StudyIndldeConverter;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class StudyIndldeBeanLocal extends AbstractStudyIsoService<StudyIndldeDTO, StudyIndlde, StudyIndldeConverter>
        implements StudyIndldeServiceLocal {
   private static final int IND_FIELD_COUNT = 6;
   private static final String VALIDATION_EXCEPTION = "Validation Exception ";
   private PAServiceUtils paServiceUtils = new PAServiceUtils();
   
   private static final Logger LOG = Logger.getLogger(StudyIndldeBeanLocal.class);

   /**
     * @param paServiceUtils the paServiceUtils to set
   */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

/**
    * @param dto StudyIndldeDTO
    * @return StudyIndldeDTO
    * @throws PAException PAException
    */
    @Override
    public StudyIndldeDTO create(StudyIndldeDTO dto) throws PAException {
      validate(dto);
      return super.create(dto);
    }

    /**
     * @param dto StudyIndldeDTO
     * @return StudyIndldeDTO
     * @throws PAException PAException
     */
    @Override
    public StudyIndldeDTO update(StudyIndldeDTO dto) throws PAException {
      validate(dto);
      return super.update(dto);
    }


    private void enforceNoDuplicate(StudyIndldeDTO dto) throws PAException {
        List<StudyIndldeDTO> spList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
        for (StudyIndldeDTO sp : spList) {
            if (paServiceUtils.isIndIdeDuplicate(dto, sp) && (dto.getIdentifier() == null
                                || (!dto.getIdentifier().getExtension().equals(sp.getIdentifier().getExtension())))) {
                throw new PADuplicateException("Duplicate IND/IDEs are not allowed.");
            }
        }
    }

    /**
     * @param studyIndldeDTO dto
     * @throws PAException e
     */
    @Override
    public void validate(StudyIndldeDTO studyIndldeDTO) throws PAException {
        StringBuffer errorMsg = new StringBuffer();
        errorMsg.append(validateIndIdeObject(studyIndldeDTO));
        if (errorMsg.length() > 0) {
            throw new PAValidationException(VALIDATION_EXCEPTION + " " + errorMsg.toString());
        }
        if (!ISOUtil.isIiNull(studyIndldeDTO.getStudyProtocolIdentifier())) {
            enforceNoDuplicate(studyIndldeDTO);
        }
    }
    
    @Override
    public String validateWithoutRollback(StudyIndldeDTO studyIndldeDTO) {
        try {
            this.validate(studyIndldeDTO);
        } catch (PAException e) {
            return e.getMessage();
        }
        return StringUtils.EMPTY;
    }

    private boolean isIdeGrantorValid(StudyIndldeDTO studyIndldeDTO) {
        return !(!ISOUtil.isCdNull(studyIndldeDTO.getIndldeTypeCode())
            && IndldeTypeCode.IDE.getCode().equals(studyIndldeDTO.getIndldeTypeCode().getCode())
            && !ISOUtil.isCdNull(studyIndldeDTO.getGrantorCode())
            && !GrantorCode.CDRH.getCode().equals(studyIndldeDTO.getGrantorCode().getCode())
            && !GrantorCode.CBER.getCode().equals(studyIndldeDTO.getGrantorCode().getCode()));

    }

    private boolean isIndGrantorValid(StudyIndldeDTO studyIndldeDTO) {
        return !(!ISOUtil.isCdNull(studyIndldeDTO.getIndldeTypeCode())
                && IndldeTypeCode.IND.getCode().equals(studyIndldeDTO.getIndldeTypeCode().getCode())
                && !ISOUtil.isCdNull(studyIndldeDTO.getGrantorCode())
                && !GrantorCode.CBER.getCode().equals(studyIndldeDTO.getGrantorCode().getCode())
                && !GrantorCode.CDER.getCode().equals(studyIndldeDTO.getGrantorCode().getCode()));
        }

     /**
      * @param studyIndldeDTO
      * @return errorMsg
      */
      private String validateIndIdeObject(StudyIndldeDTO studyIndldeDTO) {
        StringBuffer errorMsg = new StringBuffer();
        if (!isIndIdeContainsAllInfo(studyIndldeDTO)) {
          errorMsg.append("All IND/IDE values are required.\n");
        } else {
        if (!ISOUtil.isBlNull(studyIndldeDTO.getExpandedAccessIndicator())
            && BlConverter.convertToBool(studyIndldeDTO.getExpandedAccessIndicator())
            && (ISOUtil.isCdNull(studyIndldeDTO.getExpandedAccessStatusCode())
            || StringUtils.isBlank(CdConverter.convertCdToString(studyIndldeDTO.getExpandedAccessStatusCode())))) {
           errorMsg.append("Expanded Access Status value is required.\n");
        }

        if (!ISOUtil.isCdNull(studyIndldeDTO.getHolderTypeCode())
            && HolderTypeCode.NIH.getCode().equalsIgnoreCase(studyIndldeDTO.getHolderTypeCode().getCode())
            && ISOUtil.isCdNull(studyIndldeDTO.getNihInstHolderCode())) {
             errorMsg.append("NIH Institution value is required.\n");
        }
        if (!ISOUtil.isCdNull(studyIndldeDTO.getHolderTypeCode())
            && HolderTypeCode.NCI.getCode().equalsIgnoreCase(studyIndldeDTO.getHolderTypeCode().getCode())
            && ISOUtil.isCdNull(studyIndldeDTO.getNciDivProgHolderCode())) {
             errorMsg.append("NCI Division/Program Code value is required.\n");
        }
       }
        //Validate List of values
        if (!ISOUtil.isCdNull(studyIndldeDTO.getIndldeTypeCode())
            && null == IndldeTypeCode.getByCode(studyIndldeDTO.getIndldeTypeCode().getCode())) {
              errorMsg.append("Please enter valid value for IND/IDE.\n");
        }
        if (!ISOUtil.isCdNull(studyIndldeDTO.getHolderTypeCode())
            && null == HolderTypeCode.getByCode(studyIndldeDTO.getHolderTypeCode().getCode())) {
              errorMsg.append("Please enter valid value for IND/IDE Holder Type.\n");
        }
        if (!ISOUtil.isCdNull(studyIndldeDTO.getGrantorCode())
             && null == GrantorCode.getByCode(studyIndldeDTO.getGrantorCode().getCode())) {
             errorMsg.append("Please enter valid value for IND/IDE Grantor.\n");
        }
        if (!isIdeGrantorValid(studyIndldeDTO)) {
              errorMsg.append("IDE Grantor can have only CDRH value.\n");
        }
        if (!isIndGrantorValid(studyIndldeDTO)) {
                  errorMsg.append("IND Grantor must have either CBER or CDER value.\n");
        }
        if (!ISOUtil.isCdNull(studyIndldeDTO.getExpandedAccessStatusCode())
            && null == ExpandedAccessStatusCode.getByCode(studyIndldeDTO.getExpandedAccessStatusCode().getCode())) {
              errorMsg.append("Please enter valid value for IND/IDE Expanded Access Status.\n");
        }
        //validate NIH Institution values
        if (!ISOUtil.isCdNull(studyIndldeDTO.getHolderTypeCode())
            && studyIndldeDTO.getHolderTypeCode().getCode().equalsIgnoreCase(HolderTypeCode.NIH.getCode())
            && !ISOUtil.isCdNull(studyIndldeDTO.getNihInstHolderCode())
            && null == NihInstituteCode.getByCode(studyIndldeDTO.getNihInstHolderCode().getCode())) {
             errorMsg.append("Please enter valid value for IND/IDE NIH Institution.\n");
        }
        //validate NCI Division values
        if (!ISOUtil.isCdNull(studyIndldeDTO.getHolderTypeCode())
            && studyIndldeDTO.getHolderTypeCode().getCode().equalsIgnoreCase(HolderTypeCode.NCI.getCode())
            && !ISOUtil.isCdNull(studyIndldeDTO.getNciDivProgHolderCode())
            && null == NciDivisionProgramCode.getByCode(studyIndldeDTO.getNciDivProgHolderCode().getCode())) {
             errorMsg.append("Please enter valid value for IND/IDE NCI Division /Program.\n");
        }
         return errorMsg.toString();
      }

      private boolean isIndIdeContainsAllInfo(StudyIndldeDTO dto) {
        int nullCount = checkIndIdeCodes(dto);
        if (ISOUtil.isStNull(dto.getIndldeNumber())) {
          nullCount += 1;
        }
        if (ISOUtil.isBlNull(dto.getExpandedAccessIndicator())) {
          nullCount += 1;
        }
        if (ISOUtil.isBlNull(dto.getExemptIndicator())) {
            nullCount += 1;
        }
        if (nullCount == 0 || nullCount == IND_FIELD_COUNT) {
          return true;
        }
        return false;
     }

     private int checkIndIdeCodes(StudyIndldeDTO dto) {
          int nullCount = 0;
          if (ISOUtil.isCdNull(dto.getIndldeTypeCode())) {
            nullCount += 1;
          }
          if (ISOUtil.isCdNull(dto.getGrantorCode())) {
            nullCount += 1;
          }
          if (ISOUtil.isCdNull(dto.getHolderTypeCode())) {
            nullCount += 1;
          }
          return nullCount;
       }

    @Override
    public void matchToExistentIndIde(List<StudyIndldeDTO> studyIndldeDTOs,
            Ii identifier) throws PAException {
        if (studyIndldeDTOs != null) {
            for (StudyIndldeDTO existent : getByStudyProtocol(identifier)) {
                for (StudyIndldeDTO newOne : studyIndldeDTOs) {
                    if (ISOUtil.isIiNull(newOne.getIdentifier())
                            && paServiceUtils.isIndIdeDuplicate(existent,
                                    newOne)) {
                        try {
                            PropertyUtils.copyProperties(newOne, existent);
                        } catch (Exception e) {
                            LOG.error(e, e);
                        }
                    }
                }
            }
        }
    }

}

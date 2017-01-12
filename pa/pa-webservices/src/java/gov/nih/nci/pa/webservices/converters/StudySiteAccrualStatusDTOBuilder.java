/**
 * 
 */
package gov.nih.nci.pa.webservices.converters;

import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.webservices.types.BaseParticipatingSite;

/**
 * @author dkrylov
 * 
 */
public class StudySiteAccrualStatusDTOBuilder {

    /**
     * @param reg
     *            ParticipatingSite
     * @return StudySiteAccrualStatusDTO
     */
    public StudySiteAccrualStatusDTO build(BaseParticipatingSite reg) {
        StudySiteAccrualStatusDTO ssas = new StudySiteAccrualStatusDTO();
        ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode
                .getByCode(reg.getRecruitmentStatus().value())));
        ssas.setStatusDate(TsConverter.convertToTs(reg
                .getRecruitmentStatusDate().toGregorianCalendar().getTime()));
        return ssas;
    }

}

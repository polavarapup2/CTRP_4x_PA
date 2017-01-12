/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.accrual.convert;

import gov.nih.nci.accrual.dto.PerformedActivityDto;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.pa.domain.Intervention;
import gov.nih.nci.pa.domain.PerformedActivity;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivityNameCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.ISOUtil;

/**
 * @author Kalpana Guthikonda
 * @since 11/09/2009
 */
public class PerformedActivityConverter extends AbstractConverter<PerformedActivityDto, PerformedActivity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public PerformedActivityDto convertFromDomainToDto(PerformedActivity pa) {
        return convertFromDomainToDTO(pa, new PerformedActivityDto());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PerformedActivity convertFromDtoToDomain(PerformedActivityDto paDto) {
        return convertFromDTOToDomain(paDto, new PerformedActivity());
    }

    /**
     * Convert from domain to dto.
     *
     * @param bo the bo
     * @param dto the dto
     *
     * @return the performed activity dto
     */
    public static PerformedActivityDto convertFromDomainToDTO(PerformedActivity bo, PerformedActivityDto dto) {
        dto.setActualDateRange(IvlConverter.convertTs().convertToIvl(bo.getActualDateRangeLow(),
                                                                     bo.getActualDateRangeHigh()));
        dto.setCategoryCode(CdConverter.convertToCd(bo.getCategoryCode()));
        dto.setIdentifier(IiConverter.convertToActivityIi(bo.getId()));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(bo.getStudyProtocol() == null ? null : bo
            .getStudyProtocol().getId()));
        dto.setStudySubjectIdentifier(IiConverter.convertToIi(bo.getStudySubject() == null ? null : bo
            .getStudySubject().getId()));
        dto.setSubcategoryCode(CdConverter.convertStringToCd(bo.getSubcategoryCode()));
        dto.setTextDescription(StConverter.convertToSt(bo.getTextDescription()));
        dto.setName(StConverter.convertToSt(bo.getName()));
        dto.setNameCode(CdConverter.convertToCd(bo.getNameCode()));
        Pq actualDuration = new Pq();
        actualDuration.setValue(bo.getActualDurationValue());
        if (bo.getActualDurationUnit() != null) {
            actualDuration.setUnit(bo.getActualDurationUnit());
        }
        dto.setActualDuration(actualDuration);
        if (bo.getIntervention() != null) {
            dto.setInterventionIdentifier(IiConverter.convertToIi(bo.getIntervention().getId()));
        }
        return dto;
    }

    /**
     * Convert from dto to domain.
     *
     * @param dto the dto
     * @param bo the bo
     *
     * @return the performed activity
     */
    public static PerformedActivity convertFromDTOToDomain(PerformedActivityDto dto, PerformedActivity bo) {
        if (dto.getActualDateRange() != null) {
            bo.setActualDateRangeHigh(IvlConverter.convertTs().convertHigh(dto.getActualDateRange()));
            bo.setActualDateRangeLow(IvlConverter.convertTs().convertLow(dto.getActualDateRange()));
        }
        if (!ISOUtil.isCdNull(dto.getCategoryCode())) {
            bo.setCategoryCode(ActivityCategoryCode.getByCode(dto.getCategoryCode().getCode()));
        }
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        StudyProtocol spBo = null;
        if (!ISOUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            spBo = new StudyProtocol();
            spBo.setId(IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
        }
        bo.setStudyProtocol(spBo);
        convertStudySubject(dto, bo);
        bo.setSubcategoryCode(CdConverter.convertCdToString(dto.getSubcategoryCode()));
        bo.setTextDescription(StConverter.convertToString(dto.getTextDescription()));
        bo.setName(StConverter.convertToString(dto.getName()));
        if (!ISOUtil.isCdNull(dto.getNameCode())) {
            bo.setNameCode(ActivityNameCode.getByCode(dto.getNameCode().getCode()));
        }
        convertActualDuration(dto, bo);
        Intervention invBo = null;
        if (!ISOUtil.isIiNull(dto.getInterventionIdentifier())) {
            invBo = new Intervention();
            invBo.setId(IiConverter.convertToLong(dto.getInterventionIdentifier()));
        }
        bo.setIntervention(invBo);
        return bo;
    }

    private static void convertStudySubject(PerformedActivityDto dto, PerformedActivity bo) {
        StudySubject ssBo = null;
        if (!ISOUtil.isIiNull(dto.getStudySubjectIdentifier())) {
            ssBo = new StudySubject();
            ssBo.setId(IiConverter.convertToLong(dto.getStudySubjectIdentifier()));
        }
        bo.setStudySubject(ssBo);
    }

    private static void convertActualDuration(PerformedActivityDto dto, PerformedActivity bo) {
        if (dto.getActualDuration() != null) {
            if (!ISOUtil.isPqValueNull(dto.getActualDuration())) {
                bo.setActualDurationValue(dto.getActualDuration().getValue());
            }
            if (!ISOUtil.isPqValueNull(dto.getActualDuration())) {
                bo.setActualDurationUnit(dto.getActualDuration().getUnit());
            }
        }
    }
}

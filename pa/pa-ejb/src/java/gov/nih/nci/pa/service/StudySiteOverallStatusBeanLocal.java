/**
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce, copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties.For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License  is  granted  at no  charge to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions,  You have no right to download, copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any, must include the  following acknowledgment:
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
 * party proprietary programs,  You agree  that You are solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to  secure  any required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction, and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro,Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteOverallStatus;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudySiteOverallStatusConverter;
import gov.nih.nci.pa.iso.dto.StudySiteOverallStatusDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.StudySiteOverallStatusSortCriterion;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class StudySiteOverallStatusBeanLocal extends AbstractBaseSearchBean<StudySiteOverallStatus>
    implements StudySiteOverallStatusServiceLocal {

    /**
     *
     * @param dto iso
     * @return bo
     * @throws PAException e
     */
    public StudySiteOverallStatusDTO create(StudySiteOverallStatusDTO dto) throws PAException {
        if (!ISOUtil.isIiNull(dto.getIdentifier())) {
            String errMsg = " Existing StudyOverallStatus objects cannot be modified.  Append new object instead. ";
            throw new PAException(errMsg);
        }

        Session session = PaHibernateUtil.getCurrentSession();
        validate(dto);

        StudySiteOverallStatus bo = Converters.get(StudySiteOverallStatusConverter.class).convertFromDtoToDomain(dto);

        // update
        session.saveOrUpdate(bo);
        return Converters.get(StudySiteOverallStatusConverter.class).convertFromDomainToDto(bo);
    }

    /**
     * @param dto dto to validate
     * @throws PAException e
     */
    public void validate(StudySiteOverallStatusDTO dto) throws PAException {
        StringBuffer sb = new StringBuffer();
        // enforce business rules
        StudySiteOverallStatusDTO oldStatus = getCurrentByStudySite(dto.getStudySiteIdentifier());
        StudyStatusCode oldCode = null;
        Timestamp oldDate = null;
        if (oldStatus != null) {
            oldCode = StudyStatusCode.getByCode(oldStatus.getStatusCode().getCode());
            oldDate = TsConverter.convertToTimestamp(oldStatus.getStatusDate());
        }
        StudyStatusCode newCode = StudyStatusCode.getByCode(dto.getStatusCode().getCode());
        Timestamp newDate = TsConverter.convertToTimestamp(dto.getStatusDate());
        if (newCode == null) {
            sb.append("Study status must be set.  ");
        }
        if (newDate == null) {
            sb.append("Study status date must be set.  ");
        }
        if ((oldCode != null) && isTrialStatusOrDateChanged(dto, dto.getStudySiteIdentifier())
                && !oldCode.canTransitionTo(newCode)) {
            sb.append("Invalid site status transition from " + oldCode.getCode() + " to " + newCode.getCode() + ".  ");
        }
        if ((oldDate != null) && newDate.before(oldDate)) {
            sb.append("New current site status date should be bigger/same as old date.  ");
        }
        if (sb.length() > 1) {
            throw new PAException(sb.toString());
        }
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteOverallStatusDTO getCurrentByStudySite(Ii studySiteIdentifier) throws PAException {
        List<StudySiteOverallStatusDTO> dtoList = getByStudySite(studySiteIdentifier);
        StudySiteOverallStatusDTO result = null;
        if (!dtoList.isEmpty()) {
            result = dtoList.get(dtoList.size() - 1);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudySiteOverallStatusDTO> getByStudySite(Ii studySiteIi) throws PAException {
        if (ISOUtil.isIiNull(studySiteIi)) {
            throw new PAException("Ii should not be null.");
        }

        StudySiteOverallStatus criteria = new StudySiteOverallStatus();
        StudySite ss = new StudySite();
        ss.setId(IiConverter.convertToLong(studySiteIi));
        criteria.setStudySite(ss);

        PageSortParams<StudySiteOverallStatus> params =
            new PageSortParams<StudySiteOverallStatus>(PAConstants.MAX_SEARCH_RESULTS, 0,
                    StudySiteOverallStatusSortCriterion.STUDY_SITE_OVERALL_STATUS_ID, false);
        List<StudySiteOverallStatus> results =
            search(new AnnotatedBeanSearchCriteria<StudySiteOverallStatus>(criteria), params);

        List<StudySiteOverallStatusDTO> returnList = new ArrayList<StudySiteOverallStatusDTO>();
        for (StudySiteOverallStatus bo : results) {
            returnList.add(new StudySiteOverallStatusConverter().convertFromDomainToDto(bo));
        }
        return returnList;
    }

    private boolean isTrialStatusOrDateChanged(StudySiteOverallStatusDTO newStatusDto, Ii studySiteIdentifier)
            throws PAException {

        boolean statusOrDateChanged = true;
        StudySiteOverallStatusDTO currentDBdto = getCurrentByStudySite(studySiteIdentifier);
        StudyStatusCode currentStatusCode = StudyStatusCode.getByCode(currentDBdto.getStatusCode().getCode());
        Timestamp currentStatusDate = PAUtil.dateStringToTimestamp(currentDBdto.getStatusDate().toString());

        boolean codeChanged = (StudyStatusCode.getByCode(newStatusDto.getStatusCode().getCode()) == null)
                ? (currentStatusCode != null) : !StudyStatusCode.getByCode(newStatusDto.getStatusCode().getCode())
                    .equals(currentStatusCode);
        boolean statusDateChanged = (currentStatusDate == null) ? (PAUtil.dateStringToTimestamp(newStatusDto
            .getStatusDate().toString()) != null) : !currentStatusDate.equals(PAUtil.dateStringToTimestamp(newStatusDto
            .getStatusDate().toString()));
        if (!codeChanged && !statusDateChanged) {
            statusOrDateChanged = false;
        }
        return statusOrDateChanged;
    }

}

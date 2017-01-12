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

package gov.nih.nci.accrual.accweb.util;

import gov.nih.nci.accrual.convert.PatientConverter;
import gov.nih.nci.accrual.convert.PerformedActivityConverter;
import gov.nih.nci.accrual.convert.StudySubjectConverter;
import gov.nih.nci.accrual.dto.PatientListDto;
import gov.nih.nci.accrual.dto.SearchSSPCriteriaDto;
import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.util.SubjectAccrualKey;
import gov.nih.nci.accrual.service.StudySubjectServiceLocal;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.PerformedActivity;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Hugh Reinhart
 * @since Sep 26, 2009
 */
public class MockStudySubjectBean implements StudySubjectServiceLocal {

    Long seq = 1L;
    public static List<StudySubjectDto> ssList;
    User user;
    
    {
        user = new User();
        user.setLoginName("Abstractor: " + new Date());
        user.setFirstName("Joe");
        user.setLastName("Smith");
        user.setLoginName("curator");
        user.setUpdateDate(new Date());
        
        ssList = new ArrayList<StudySubjectDto>();
        StudySubjectDto dto = new StudySubjectDto();
        dto.setAssignedIdentifier(StConverter.convertToSt("SUBJ 001"));
        dto.setDiseaseIdentifier(null);
        dto.setIdentifier(IiConverter.convertToIi(seq++));
        dto.setPatientIdentifier(IiConverter.convertToIi(1L));
        dto.setPaymentMethodCode(CdConverter.convertToCd(PaymentMethodCode.MEDICAID));
        dto.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
        dto.setStudySiteIdentifier(IiConverter.convertToStudySiteIi(1L));
        ssList.add(dto);
    }
    StudySubjectConverter conv = new StudySubjectConverter();
    PatientConverter pConv = new PatientConverter();
    PerformedActivityConverter paConv = new PerformedActivityConverter();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudySubjectDto> getByStudySite(Ii ii) throws PAException {
        return ssList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySubjectDto create(StudySubjectDto dto) throws PAException {
        dto.setIdentifier(IiConverter.convertToIi(seq++));
        ssList.add(dto);
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Ii ii) throws PAException {
        Long id = IiConverter.convertToLong(ii);
        for (StudySubjectDto dto : ssList) {
            if (id.equals(IiConverter.convertToLong(dto.getIdentifier()))) {
                dto.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.NULLIFIED));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySubjectDto get(Ii ii) throws PAException {
        Long id = IiConverter.convertToLong(ii);
        StudySubjectDto result = null;
        for (StudySubjectDto dto : ssList) {
            if (id.equals(IiConverter.convertToLong(dto.getIdentifier()))) {
                result = dto;
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySubjectDto update(StudySubjectDto dto) throws PAException {
        Long id = IiConverter.convertToLong(dto.getIdentifier());
        for (StudySubjectDto ss : ssList) {
            if (id.equals(IiConverter.convertToLong(ss.getIdentifier()))) {
                ss = dto;
            }
        }
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudySubjectDto> getByStudyProtocol(Ii ii) throws PAException {
        List<StudySubjectDto> result = new ArrayList<StudySubjectDto>();
        for (StudySubjectDto ss : ssList) {
            if (!StringUtils.equals(FunctionalRoleStatusCode.NULLIFIED.getCode(), CdConverter.convertCdToString(ss.getStatusCode()))) {
                result.add(ss);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudySubjectDto> getStudySubjects(String assignedIdentifier, Long studySiteId, Date birthDate) 
    		throws PAException {
        return ssList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudySubjectDto> search(Long studyIdentifier, Long participatingSiteIdentifier, Timestamp startDate,
                                        Timestamp endDate, LimitOffset pagingParams) throws PAException {
       throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudySubject> search(SearchSSPCriteriaDto criteria) throws PAException {
        List<StudySubject> result = new ArrayList<StudySubject>();        
        for (StudySubjectDto ssDto : ssList) {
        	StudySubject ss = conv.convertFromDtoToDomain(ssDto);
        	ss.setUserLastCreated(user);
        	ss.setDateLastUpdated(new Timestamp(new Date().getTime()));
            result.add(ss);
        }
        return result;
    }

    @Override
    public StudySubject get(Long id) throws PAException {
        StudySubject result = null;
        for (StudySubjectDto ssDto : ssList) {
            StudySubject ss = conv.convertFromDtoToDomain(ssDto);
            if(id.equals(ss.getId())) {
                result = ss;
                result.setUserLastCreated(user);
                result.setDateLastUpdated(new Timestamp(new Date().getTime()));
                result.setPatient(pConv.convertFromDtoToDomain(MockPatientBean.pList.get(0)));
                List<PerformedActivity> paList = new ArrayList<PerformedActivity>();
                paList.add(paConv.convertFromDtoToDomain(MockPerformedActivityBean.psmList.get(0)));
                result.setPerformedActivities(paList);
            }
        }
        return result;
    }

    @Override
    public Map<SubjectAccrualKey, Long[]> getSubjectAndPatientKeys(Long studyProtocolId, boolean activeOnly) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StudySubject get(SubjectAccrualKey key) throws PAException {
        StudySubject result = null;
        for (StudySubjectDto dto : ssList) {
            SubjectAccrualKey mockkey = new SubjectAccrualKey(dto.getStudySiteIdentifier(), dto.getAssignedIdentifier());
            if (ObjectUtils.equals(key, mockkey)) {
                result = conv.convertFromDtoToDomain(dto);
                result.setPatient(pConv.convertFromDtoToDomain(MockPatientBean.pList.get(0)));
                List<PerformedActivity> paList = new ArrayList<PerformedActivity>();
                paList.add(paConv.convertFromDtoToDomain(MockPerformedActivityBean.psmList.get(0)));
                result.setPerformedActivities(paList);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PatientListDto> searchFast(SearchSSPCriteriaDto criteria) throws PAException {
        List<StudySubject> ssList = search(criteria);
        List<PatientListDto> pList = new ArrayList<PatientListDto>();
        for (StudySubject ss : ssList) {
            PatientListDto p = new PatientListDto();
            p.setIdentifier(String.valueOf(ss.getId()));
            p.setDateLastUpdated(new Timestamp((ss.getDateLastUpdated() == null ? ss.getDateLastCreated() : ss.getDateLastUpdated()).getTime()));
            p.setAssignedIdentifier(ss.getAssignedIdentifier());
            p.setOrganizationName("xxxx");
            p.setRegistrationDate(new Timestamp(new Date().getTime()));
        }
        return pList;
    }

	@Override
	public StudySubject searchActiveByStudyProtocol(Long spId)
			throws PAException {
		StudySubject ss = new StudySubject();
		ss.setDisease(new AccrualDisease());
		return ss;
	}
}

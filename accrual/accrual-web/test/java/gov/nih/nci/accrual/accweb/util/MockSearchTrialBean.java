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

import gov.nih.nci.accrual.dto.util.AccrualCountsDto;
import gov.nih.nci.accrual.dto.util.SearchTrialCriteriaDto;
import gov.nih.nci.accrual.dto.util.SearchTrialResultDto;
import gov.nih.nci.accrual.service.util.SearchTrialService;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author Hugh Reinhart
 * @since Aug 25, 2009
 */
public class MockSearchTrialBean implements SearchTrialService {

    public static final long NONINDUSTRIAL_STUDY_PROTOCOL_ID = 1l;
    public static final long INDUSTRIAL_STUDY_PROTOCOL_ID = 3l;
    public static final long NONINTERVENTIONAL_STUDY_PROTOCOL_ID = 2l;

    /** mock data. */
    public static List<SearchTrialResultDto> dtos;
    public static boolean exception;

    static {
        dtos = new ArrayList<SearchTrialResultDto>();
        SearchTrialResultDto r = new SearchTrialResultDto();
        r.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(NONINDUSTRIAL_STUDY_PROTOCOL_ID));
        r.setAssignedIdentifier(StConverter.convertToSt("NCI-2009-00001"));
        r.setLeadOrgName(StConverter.convertToSt("Duke"));
        r.setLeadOrgTrialIdentifier(StConverter.convertToSt("DUKE 001"));
        r.setOfficialTitle(StConverter.convertToSt("Phase II study for Melanoma"));
        r.setPrincipalInvestigator(StConverter.convertToSt("John Doe"));
        r.setIndustrial(BlConverter.convertToBl(false));
        r.setTrialType(StConverter.convertToSt(AccrualUtil.INTERVENTIONAL));
        dtos.add(r);

        r = new SearchTrialResultDto();
        r.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(NONINTERVENTIONAL_STUDY_PROTOCOL_ID));
        r.setAssignedIdentifier(StConverter.convertToSt("NCI-2009-00002"));
        r.setLeadOrgName(StConverter.convertToSt("Wake Forest"));
        r.setLeadOrgTrialIdentifier(StConverter.convertToSt("WAKE 001"));
        r.setOfficialTitle(StConverter.convertToSt("Phase IV study for Breast Cancer"));
        r.setPrincipalInvestigator(StConverter.convertToSt("Azam Baig"));
        r.setTrialType(StConverter.convertToSt(AccrualUtil.NONINTERVENTIONAL));
        r.setAccrualSubmissionLevel(StConverter.convertToSt(AccrualUtil.BOTH));
        r.setIndustrial(BlConverter.convertToBl(false));
        dtos.add(r);

        r = new SearchTrialResultDto();
        r.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(INDUSTRIAL_STUDY_PROTOCOL_ID));
        r.setAssignedIdentifier(StConverter.convertToSt("NCI-2009-00003"));
        r.setLeadOrgName(StConverter.convertToSt("Mayo"));
        r.setLeadOrgTrialIdentifier(StConverter.convertToSt("Mayo 001"));
        r.setOfficialTitle(StConverter.convertToSt("Phase IV study for Breast Cancer"));
        r.setPrincipalInvestigator(StConverter.convertToSt("Azam Baig"));
        r.setIndustrial(BlConverter.convertToBl(true));
        r.setTrialType(StConverter.convertToSt(AccrualUtil.INTERVENTIONAL));
        dtos.add(r);

        r = new SearchTrialResultDto();
        r.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(4l));
        r.setAssignedIdentifier(StConverter.convertToSt("NCI-2013-00002"));
        r.setLeadOrgName(StConverter.convertToSt("Wake Forest"));
        r.setLeadOrgTrialIdentifier(StConverter.convertToSt("WAKE 002"));
        r.setOfficialTitle(StConverter.convertToSt("Phase IV study for Breast Cancer"));
        r.setPrincipalInvestigator(StConverter.convertToSt("Azam Baig"));
        r.setTrialType(StConverter.convertToSt(AccrualUtil.NONINTERVENTIONAL));
        r.setAccrualSubmissionLevel(StConverter.convertToSt(AccrualUtil.SUMMARY_LEVEL));
        r.setIndustrial(BlConverter.convertToBl(false));
        dtos.add(r);

        r = new SearchTrialResultDto();
        r.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(5l));
        r.setAssignedIdentifier(StConverter.convertToSt("NCI-2013-00003"));
        r.setLeadOrgName(StConverter.convertToSt("Wake Forest"));
        r.setLeadOrgTrialIdentifier(StConverter.convertToSt("WAKE 003"));
        r.setOfficialTitle(StConverter.convertToSt("Phase IV study for Breast Cancer"));
        r.setPrincipalInvestigator(StConverter.convertToSt("Azam Baig"));
        r.setTrialType(StConverter.convertToSt(AccrualUtil.NONINTERVENTIONAL));
        r.setAccrualSubmissionLevel(StConverter.convertToSt(AccrualUtil.SUBJECT_LEVEL));
        r.setIndustrial(BlConverter.convertToBl(false));
        dtos.add(r);
    }

    /**
     * {@inheritDoc}
     */
    public List<SearchTrialResultDto> search(SearchTrialCriteriaDto criteria, Ii authUser) throws PAException {
        if (exception) {
            throw new PAException();
    	}
        List<SearchTrialResultDto> result = new ArrayList<SearchTrialResultDto>();
        for (SearchTrialResultDto dto : dtos) {
            if (contains(dto.getAssignedIdentifier(), criteria.getAssignedIdentifier())
                && contains(dto.getLeadOrgTrialIdentifier(), criteria.getLeadOrgTrialIdentifier())
                && contains(dto.getOfficialTitle(), criteria.getOfficialTitle())) {
                result.add(dto);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public SearchTrialResultDto getTrialSummaryByStudyProtocolIi(Ii studyProtocolIi) throws PAException {
        if (exception) {
            throw new PAException();
    	}
        SearchTrialResultDto result = null;
        for (SearchTrialResultDto dto : dtos) {
            if (IiConverter.convertToLong(studyProtocolIi).equals(IiConverter.convertToLong(
                    dto.getStudyProtocolIdentifier()))) {
                result = dto;
            }
        }
        return result;
    }

    private boolean contains(St value, St crit) {
        boolean result = true;
        String c = StConverter.convertToString(crit);
        String v = StConverter.convertToString(value);
        if (StringUtils.isNotEmpty(c) &&  !v.contains(c)) {
            result = false;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public Bl isAuthorized(Ii studyProtocolIi, Ii authorizedUser) throws PAException {
        return BlConverter.convertToBl(true);
    }

    @Override
    public Map<Long, String> getAuthorizedTrialMap(Long authorizedUser) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public List<AccrualCountsDto> getAccrualCountsForUser(RegistryUser ru)
			throws PAException {
		return new ArrayList<AccrualCountsDto>();
	}

    @Override
    public void validate(Long studyProtocolId) throws PAException {
        // TODO Auto-generated method stub
    }
}

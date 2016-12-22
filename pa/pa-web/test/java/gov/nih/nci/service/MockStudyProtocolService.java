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
package gov.nih.nci.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.AmendmentReasonCode;
import gov.nih.nci.pa.enums.BiospecimenRetentionCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.StudyModelCode;
import gov.nih.nci.pa.enums.StudySubtypeCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.TimePerspectiveCode;
import gov.nih.nci.pa.iso.convert.InterventionalStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolAssociationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;

/**
 * @author hreinhart
 *
 */
public class MockStudyProtocolService extends AbstractBaseSearchBean<StudyProtocol>
    implements StudyProtocolServiceLocal {

    public static List<StudyProtocol> list;
    public static List<InterventionalStudyProtocol> isplist;

    static {
        list = new ArrayList<StudyProtocol>();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(1L);
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setStartDate(PAUtil.dateStringToTimestamp("1/1/2000"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("4/15/2010"));
        sp.setSubmissionNumber(1);
        sp.setCtroOverride(true);
        User userLastCreated = new User();
        userLastCreated.setLoginName("user2@mail.nih.gov");
        sp.setUserLastCreated(userLastCreated);
        list.add(sp);
        isplist = new ArrayList<InterventionalStudyProtocol>();
        InterventionalStudyProtocol isp = new InterventionalStudyProtocol();
        isp.setId(1L);
        dates = isp.getDates();
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setStartDate(PAUtil.dateStringToTimestamp("1/1/2000"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("4/15/2010"));
        isp.setOfficialTitle("officialTitle");
        User userLastCreated2 = new User();
        userLastCreated.setLoginName("user1@mail.nih.gov");
        isp.setUserLastCreated(userLastCreated2);
        isplist.add(isp);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public StudyProtocolDTO getStudyProtocol(Ii ii) throws PAException {
        return loadStudyProtocol(ii);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyProtocolDTO loadStudyProtocol(Ii ii) {
        for (StudyProtocol sp: list) {
            if(sp.getId().equals(IiConverter.convertToLong(ii))) {
                return StudyProtocolConverter.convertFromDomainToDTO(sp);
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyProtocolDTO updateStudyProtocol(StudyProtocolDTO dto) throws PAException {
        for (StudyProtocol bo : list) {
            if (bo.getId().equals(IiConverter.convertToLong(dto.getIdentifier()))) {
                StudyProtocolDates dates = bo.getDates();
                dates.setStartDateTypeCode(ActualAnticipatedTypeCode.getByCode(dto.getStartDateTypeCode().getCode()));
                dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.getByCode(dto
                    .getPrimaryCompletionDateTypeCode().getCode()));
                dates.setStartDate(TsConverter.convertToTimestamp(dto.getStartDate()));
                dates.setPrimaryCompletionDate(TsConverter.convertToTimestamp(dto.getPrimaryCompletionDate()));
                return StudyProtocolConverter.convertFromDomainToDTO(bo);
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionalStudyProtocolDTO getInterventionalStudyProtocol(Ii ii) throws PAException {
        for (InterventionalStudyProtocol isp : isplist) {
            if (isp.getId().equals(IiConverter.convertToLong(ii))) {
                return InterventionalStudyProtocolConverter.convertFromDomainToDTO(isp);
            }
        }
        if (ii.getExtension().equals("9")) {
            throw new PAException("test");
        }

        return null;
    }

   


   /**
    * {@inheritDoc}
    */
    @Override
    public InterventionalStudyProtocolDTO updateInterventionalStudyProtocol(
            InterventionalStudyProtocolDTO ispDTO, String page) throws PAException {
        return null;
    }

   /**
    * {@inheritDoc}
    */
    @Override
    public NonInterventionalStudyProtocolDTO getNonInterventionalStudyProtocol(Ii ii) throws PAException {
    	    NonInterventionalStudyProtocolDTO ospDTO = new NonInterventionalStudyProtocolDTO();
            Timestamp now = new Timestamp((new Date()).getTime());
            ospDTO.setStartDate(TsConverter.convertToTs(now));
            ospDTO.setStartDateTypeCode(CdConverter.convertStringToCd(ActualAnticipatedTypeCode.ACTUAL.getCode()));
            ospDTO.setPrimaryCompletionDate(TsConverter.convertToTs(now));
            ospDTO.setPrimaryCompletionDateTypeCode(CdConverter.convertStringToCd(ActualAnticipatedTypeCode.ACTUAL.getCode()));
            ospDTO.setStudyModelCode(CdConverter.convertStringToCd(StudyModelCode.CASE_CONTROL.getCode()));
            ospDTO.setTimePerspectiveCode(CdConverter.convertStringToCd(TimePerspectiveCode.PROSPECTIVE.getCode()));
            ospDTO.setBiospecimenDescription(StConverter.convertToSt("BiospecimenDescription"));
            ospDTO.setBiospecimenRetentionCode(CdConverter.convertStringToCd(BiospecimenRetentionCode.
                    NONE_RETAINED.getCode()));
            ospDTO.setNumberOfGroups(IntConverter.convertToInt(4));
            ospDTO.setStatusCode(CdConverter.convertStringToCd(ActStatusCode.ACTIVE.getCode()));
            ospDTO.setAmendmentReasonCode(CdConverter.convertStringToCd(AmendmentReasonCode.BOTH.getCode()));
            ospDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.FALSE));
            ospDTO.setSubmissionNumber(IntConverter.convertToInt(Integer.valueOf(1)));
            ospDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.I));
            ospDTO.setPhaseAdditionalQualifierCode(CdConverter.convertToCd(PhaseAdditionalQualifierCode.PILOT));
            ospDTO.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.OTHER));
            ospDTO.setPrimaryPurposeOtherText(StConverter.convertToSt("primaryPurposeOtherText"));
            ospDTO.setPrimaryPurposeAdditionalQualifierCode(CdConverter.convertToCd(PrimaryPurposeAdditionalQualifierCode.CORRELATIVE));
            ospDTO.setStudySubtypeCode(CdConverter.convertToCd(StudySubtypeCode.OBSERVATIONAL)); 
            return ospDTO;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Ii createInterventionalStudyProtocol(InterventionalStudyProtocolDTO ispDTO)
    throws PAException {
        return null;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Ii createNonInterventionalStudyProtocol(
            NonInterventionalStudyProtocolDTO ospDTO) throws PAException {
        return null;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public NonInterventionalStudyProtocolDTO updateNonInterventionalStudyProtocol(
            NonInterventionalStudyProtocolDTO ospDTO) throws PAException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteStudyProtocol(Ii ii) throws PAException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyProtocolDTO> search(StudyProtocolDTO dto, LimitOffset pagingParams) throws PAException,
            TooManyResultsException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(StudyProtocolDTO studyProtocolDTO) throws PAException {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void changeOwnership(StudyProtocolDTO studyProtocolDTO)
            throws PAException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> changeOwnership(Ii id, DSet<Tel> recordOwners)
            throws PAException {
        return new ArrayList<String>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyProtocolDTO> getAbstractedCollaborativeTrials() throws PAException {
        return new ArrayList<StudyProtocolDTO>();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, String> getTrialNciId(List<Long> studyProtocolIDs) { 
        return new HashMap<Long, String>();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, String> getTrialProcessingStatus(List<Long> studyProtocolIDs) { 
        return new HashMap<Long, String>();
    }
    
    /**
     * {@inheritDoc}
     */
    public List<StudyProtocolAssociationDTO> getTrialAssociations(Long studyId) throws PAException {
        return new ArrayList<StudyProtocolAssociationDTO>();
    }
    
    /**
     * {@inheritDoc}
     */
    public void createPendingTrialAssociation(
            StudyProtocolAssociationDTO trialAssociation) throws PAException {  
    }
    
    /**
     * {@inheritDoc}
     */
    public void deleteTrialAssociation(Ii convertToIi) throws PAException {               
    }
    /**
     * {@inheritDoc}
     */
    public StudyProtocolAssociationDTO getTrialAssociation(long id)
            throws PAException {        
        return new StudyProtocolAssociationDTO();
    }
    
    /**
     * {@inheritDoc}
     */
    public void update(StudyProtocolAssociationDTO association)
            throws PAException {                
    }
    
    /**
     * {@inheritDoc}
     */
    public void createActiveTrialAssociation(Long trialA, Long trialB,
            Long associationToReplace) throws PAException {
        
        
    }
    
    /**
     * {@inheritDoc}
     */
    public void updatePendingTrialAssociationsToActive(long studyId) {               
    }
    /**
     * {@inheritDoc}
     */
    public void changeStudyProtocolType(Ii studyProtocolIi,
            StudyTypeCode interventional) throws PAException {
       
        
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> getProtocolIdsWithNCIId(String nciId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addAnatomicSite(Ii studyProtocolIi, Cd site) throws PAException {             
    }

    @Override
    public void removeAnatomicSite(Ii studyProtocolIi, Cd site)
            throws PAException {
    }

    @Override
    public void updateRecordVerificationDate(Long studyProtocolId)
            throws PAException {
        // TODO Auto-generated method stub
        
    }

    /**
     * {@inheritDoc}
     */
    public List<StudyProtocolDTO> getStudyProtocolsByNctId(String nctID)
            throws PAException {       
        return null;
    }

	@Override
	public List<Long> getActiveAndInActiveTrialsByspId(Long id)
			throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public List<Long> getNonRejectedByPublicTitle(String publicTitle) 
            throws PAException {
        return new ArrayList<Long>();
    }

    @Override
    public boolean updateStudyProtocolResultsDate(Long studyId,
            String attribute, Timestamp value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void assignProgramCodes(Long studyId, Long organizationPoID, List<ProgramCodeDTO> programCodes) throws PAException {

    }

    @Override
    public void unAssignProgramCode(Long studyId, ProgramCodeDTO programCode) throws PAException {

    }

    @Override
    public void assignProgramCodesToTrials(List<Long> studyIds, Long familyPoId, List<ProgramCodeDTO> programCodes) {
    }

    @Override
    public void unassignProgramCodesFromTrials(List<Long> studyIds, List<ProgramCodeDTO> programCodes) {
    }

    @Override
    public void replaceProgramCodesOnTrials(List<Long> studyIds,Long familyPoId,  ProgramCodeDTO programCode, List<ProgramCodeDTO> programCodes) throws PAException {

    }
}

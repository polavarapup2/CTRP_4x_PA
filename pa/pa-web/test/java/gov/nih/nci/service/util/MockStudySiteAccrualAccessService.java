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
package gov.nih.nci.service.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.dto.AccrualAccessAssignmentByTrialDTO;
import gov.nih.nci.pa.dto.AccrualAccessAssignmentHistoryDTO;
import gov.nih.nci.pa.dto.AccrualSubmissionAccessDTO;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.iso.convert.StudySiteAccrualAccessConverter;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualAccessDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.service.MockAbstractBaseIsoService;
import gov.nih.nci.service.MockStudySiteService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Hugh Reinhart
 * @since Sep 8, 2009
 */
public class MockStudySiteAccrualAccessService extends MockAbstractBaseIsoService<StudySiteAccrualAccessDTO>
    implements StudySiteAccrualAccessServiceLocal {

    public static Long seq = 1L;
    public static List<StudySiteAccrualAccess> list = new ArrayList<StudySiteAccrualAccess>();
    public static List<RegistryUser> regUsers;
    private static final StudySiteAccrualAccessConverter CONVERTER = new StudySiteAccrualAccessConverter();
    static {
        regUsers = new ArrayList<RegistryUser>();

        RegistryUser ru = new RegistryUser();
        ru.setId(1L);
        User csmUser = new User();
        csmUser.setUserId(1L);
        ru.setCsmUser(csmUser);
        regUsers.add(ru);

        ru = new RegistryUser();
        ru.setId(2L);
        csmUser = new User();
        csmUser.setUserId(2L);
        ru.setCsmUser(csmUser);
        regUsers.add(ru);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteAccrualAccessDTO create(StudySiteAccrualAccessDTO access) throws PAException {
        StudySiteAccrualAccess bo = new StudySiteAccrualAccess();
        bo.setId(seq++);
        bo.setRegistryUser(new RegistryUser());
        bo.getRegistryUser().setId(IiConverter.convertToLong(access.getRegistryUserIdentifier()));
        bo.setSource(CdConverter.convertCdToEnum(AccrualAccessSourceCode.class, access.getSource()));
        bo.setRequestDetails(StConverter.convertToString(access.getRequestDetails()));
        bo.setStatusCode(ActiveInactiveCode.getByCode(CdConverter.convertCdToString(access.getStatusCode())));
        for (StudySiteDTO ss : MockStudySiteService.dtos) {
            if (ss.getIdentifier().getExtension().equals(access.getStudySiteIdentifier().getExtension())) {
                StudySite s = new StudySite();
                s.setId(IiConverter.convertToLong(ss.getIdentifier()));
                bo.setStudySite(s);
            }
        }
        list.add(bo);
        return access;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteAccrualAccessDTO get(Ii identifier) throws PAException {
        for (StudySiteAccrualAccess ssaa : list) {
            if (ssaa.getId().equals(IiConverter.convertToLong(identifier))) {
                return CONVERTER.convertFromDomainToDto(ssaa);
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudySiteAccrualAccessDTO> getByStudyProtocol(Long studyProtocolId) throws PAException {
        return CONVERTER.convertFromDomainToDtos(list);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<User> getSubmitters() throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, String> getTreatingSites(Long studyProtocolId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteAccrualAccessDTO update(StudySiteAccrualAccessDTO access) throws PAException {
        StudySiteAccrualAccess bo = null;
        for (StudySiteAccrualAccess item : list) {
            if (item.getRegistryUser().getId().equals(IiConverter.convertToLong(access.getRegistryUserIdentifier()))
                    && item.getStudySite().getId().equals(IiConverter.convertToLong(access.getStudySiteIdentifier()))) {
                bo = item;
            }
        }
        bo.setRequestDetails(StConverter.convertToString(access.getRequestDetails()));
        bo.setStatusCode(ActiveInactiveCode.getByCode(CdConverter.convertCdToString(access.getStatusCode())));
        return access;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudySiteAccrualAccessDTO> getByStudySite(Long studySiteId) throws PAException {
        List<StudySiteAccrualAccess> result = new ArrayList<StudySiteAccrualAccess>();
        for (StudySiteAccrualAccess item : list) {
            if (item.getStudySite().getId().equals(studySiteId)) {
                result.add(item);
            }
        }
        return CONVERTER.convertFromDomainToDtos(result);
    }

   
    public List<AccrualSubmissionAccessDTO> getAccrualSubmissionAccess(
            RegistryUser user) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

   
    public List<Long> getActiveTrialLevelAccrualAccess(RegistryUser user)
            throws PAException {       
        return new ArrayList<Long>();
    }

  
    public void assignTrialLevelAccrualAccess(RegistryUser user, AccrualAccessSourceCode source,
            Collection<Long> trialIDs, String comments, RegistryUser creator) throws PAException {
              
    }

    
    public void unassignTrialLevelAccrualAccess(RegistryUser user, AccrualAccessSourceCode source,
            Collection<Long> trialIDs, String comment, RegistryUser creator)
            throws PAException {        
    }

    
    public void synchronizeSiteAccrualAccess(Long trialID, RegistryUser user)
            throws PAException {  // TODO Auto-generated method stub
        
    }


    public List<AccrualAccessAssignmentHistoryDTO> getAccrualAccessAssignmentHistory(Collection<Long> trialIds)
            throws PAException {
        return new ArrayList<AccrualAccessAssignmentHistoryDTO>();
    }

    @Override
    public List<AccrualAccessAssignmentByTrialDTO> getAccrualAccessAssignmentByTrial(Collection<Long> trialIds)
            throws PAException {
        return new ArrayList<AccrualAccessAssignmentByTrialDTO>();
    }

	@Override
	public StudySiteAccrualAccessDTO getByStudySiteAndUser(Long studySiteId,
			Long registryUserId) throws PAException {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public Map<Long, Organization> getTreatingOrganizations(Long studyProtocolId) throws PAException {
    	Map<Long, Organization> siteOrgList = new HashMap<Long, Organization>();
        Organization org = new Organization();
        org.setId(1L);
        org.setIdentifier("1");
        org.setName("orgname");
        siteOrgList.put(3L, org);
        return siteOrgList;
    }

    @Override
    public List<StudySiteAccrualAccessDTO> getActiveByUser(Long registryUserId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void createStudySiteAccrualAccess(Long registryUserId, Long siteId, AccrualAccessSourceCode source)
            throws PAException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeStudySiteAccrualAccess(RegistryUser user, List<StudySiteAccrualAccessDTO> list,
            AccrualAccessSourceCode source) throws PAException {
        // TODO Auto-generated method stub
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createTrialAccessHistory(RegistryUser user, AccrualAccessSourceCode source, Long trialID,
            String comment, RegistryUser creator) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void assignTrialLevelAccrualAccessNoTransaction(RegistryUser user, AccrualAccessSourceCode source,
            Collection<Long> trialIDs, String comment, RegistryUser creator) throws PAException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void unassignAllAccrualAccess(RegistryUser user, AccrualAccessSourceCode source, String comment,
            RegistryUser creator) throws PAException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void unassignTrialLevelAccrualAccessNoTransaction(RegistryUser user, AccrualAccessSourceCode source,
            Collection<Long> trialIDs, String comment, RegistryUser creator) throws PAException {
        // TODO Auto-generated method stub
        
    }
}

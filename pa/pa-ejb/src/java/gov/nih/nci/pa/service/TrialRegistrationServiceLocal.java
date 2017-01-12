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

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.List;

import javax.ejb.Local;

/**
 * @author Naveen Amiruddin
 * @since 03/19/2009 copyright NCI 2007. All rights reserved. This code may not be used without the express written
 *        permission of the copyright holder, NCI.
 */
@Local
@SuppressWarnings("PMD.ExcessiveParameterList")
public interface TrialRegistrationServiceLocal {
    
    /**
     * Creates a study protocol.
     *
     * @param studyProtocolDTO StudyProtocolDTO
     * @param statusHistory List<StudyOverallStatusDTO>
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs list of documents
     * @param leadOrganizationDTO Pead organization
     * @param principalInvestigatorDTO Principal Investigator
     * @param sponsorOrganizationDTO Sponsort Organization
     * @param leadOrganizationSiteIdentifierDTO local protocol identifier
     * @param studyIdentifierDTOs list of study Identifier    
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param studyRegAuthDTO studyRegAuthDTO
     * @param isBatchMode to identify if batch is caller
     * @return ii of Study Protocol
     * @throws PAException on error
     */
    // CHECKSTYLE:OFF More than 7 parameters
    Ii createCompleteInterventionalStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            List<StudyOverallStatusDTO> statusHistory, List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, ResponsiblePartyDTO partyDTO, 
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, 
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO, 
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode) throws PAException;

    // CHECKSTYLE:ON

    /**
     * Creates a study protocol.
     *
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs list of documents
     * @param leadOrganizationDTO Pead organization
     * @param principalInvestigatorDTO Principal Investigator
     * @param sponsorOrganizationDTO Sponsort Organization
     * @param leadOrganizationSiteIdentifierDTO local protocol identifier
     * @param studyIdentifierDTOs list of study Identifier    
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param studyRegAuthDTO studyRegAuthDTO
     * @param isBatchMode to identify if batch is caller
     * @return ii of Study Protocol
     * @throws PAException on error
     */
    // CHECKSTYLE:OFF More than 7 parameters
    Ii createCompleteInterventionalStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO, List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, ResponsiblePartyDTO partyDTO, 
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, 
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO, 
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode) throws PAException;

    // CHECKSTYLE:ON
    
    /**
     * Creates a study protocol and assigns ownership.
     *
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs list of documents
     * @param leadOrganizationDTO Pead organization
     * @param principalInvestigatorDTO Principal Investigator
     * @param sponsorOrganizationDTO Sponsort Organization
     * @param leadOrganizationSiteIdentifierDTO local protocol identifier
     * @param studyIdentifierDTOs list of study Identifier    
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code     
     * @param studyRegAuthDTO studyRegAuthDTO
     * @param isBatchMode to identify if batch is caller
     * @param owners trial record owners identified by email addresses
     * @return ii of Study Protocol
     * @throws PAException on error
     */
    // CHECKSTYLE:OFF More than 7 parameters
    Ii createCompleteInterventionalStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO, List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, ResponsiblePartyDTO partyDTO, 
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, 
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO, 
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode, DSet<Tel> owners) throws PAException;

    // CHECKSTYLE:ON
    

    /**
     * Amends a study protocol.
     *
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs list of documents
     * @param leadOrganizationDTO Pead organization
     * @param principalInvestigatorDTO Principal Investigator
     * @param sponsorOrganizationDTO Sponsort Organization
     * @param leadOrganizationSiteIdentifierDTO local protocol identifier
     * @param studyIdentifierDTOs list of Study Identifier    
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code    
     * @param studyRegAuthDTO studyRegAuthDTO
     * @param isBatchMode to identify if batch is caller
     * @return ii of Study Protocol
     * @throws PAException on error
     */
    // CHECKSTYLE:OFF More than 7 parameters
    Ii amend(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs, List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            ResponsiblePartyDTO partyDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, 
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO, 
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode) throws PAException;

    // CHECKSTYLE:ON
    
    /**
     * Amends a study protocol.
     *
     * @param studyProtocolDTO StudyProtocolDTO
     * @param statusHistory statusHistory
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs list of documents
     * @param leadOrganizationDTO Pead organization
     * @param principalInvestigatorDTO Principal Investigator
     * @param sponsorOrganizationDTO Sponsort Organization
     * @param leadOrganizationSiteIdentifierDTO local protocol identifier
     * @param studyIdentifierDTOs list of Study Identifier    
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code    
     * @param studyRegAuthDTO studyRegAuthDTO
     * @param isBatchMode to identify if batch is caller
     * @return ii of Study Protocol
     * @throws PAException on error
     */
    // CHECKSTYLE:OFF More than 7 parameters
    Ii amend(StudyProtocolDTO studyProtocolDTO, List<StudyOverallStatusDTO> statusHistory,
            List<StudyIndldeDTO> studyIndldeDTOs, List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            ResponsiblePartyDTO partyDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, 
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO, 
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode) throws PAException;

    // CHECKSTYLE:ON

    
    /**
     * Amends a study protocol.
     *
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs list of documents
     * @param leadOrganizationDTO Pead organization
     * @param principalInvestigatorDTO Principal Investigator
     * @param sponsorOrganizationDTO Sponsort Organization
     * @param leadOrganizationSiteIdentifierDTO local protocol identifier
     * @param studyIdentifierDTOs list of Study Identifier     
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param studyRegAuthDTO studyRegAuthDTO
     * @param isBatchMode to identify if batch is caller
     * @param handleDuplicateGrantAndINDsGracefully PO-6172: ensure that the registration service does not fail 
     * an amendment with grant or IND/IDE data already in CTRP 
     * @return ii of Study Protocol
     * @throws PAException on error
     */
    // CHECKSTYLE:OFF More than 7 parameters
    Ii amend(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs, List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            ResponsiblePartyDTO partyDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, 
            List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO, 
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode, Bl handleDuplicateGrantAndINDsGracefully) 
                    throws PAException;

    // CHECKSTYLE:ON

    /**
     * Updates a study protocol.
     *
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param studyIdentifierDTOs List of Study Identifier
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs List of documents IRB and Participating doc
     * @param studyContactDTO phone and email info when Pi is responsible
     * @param studyParticipationContactDTO StudySiteContactDTO
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param responsiblePartyContactIi id of the person when sponsor is responsible
     * @param studyRegAuthDTO updated studyRegAuthDTO
     * @param collaboratorDTOs list of updated collaborators
     * @param studySiteAccrualStatusDTOs list of updated participating sites
     * @param studySiteDTOs list of StudySite DTOs with updated program code
     * @param isBatchMode to identify if batch is caller
     * @throws PAException on error
     */
    // CHECKSTYLE:OFF More than 7 parameters
    void update(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<StudySiteDTO> studyIdentifierDTOs, List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            StudyContactDTO studyContactDTO, StudySiteContactDTO studyParticipationContactDTO,
            OrganizationDTO summary4organizationDTO, StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi, StudyRegulatoryAuthorityDTO studyRegAuthDTO,
            List<StudySiteDTO> collaboratorDTOs, List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs,
            List<StudySiteDTO> studySiteDTOs, Bl isBatchMode) throws PAException;

    // CHECKSTYLE:ON
    
    /**
     * Updates a study protocol.
     * 
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs List of documents IRB and Participating doc
     * @param studySiteAccrualStatusDTOs list of updated participating sites
     * @param studySiteDTOs list of StudySite DTOs with updated program code
     * @param isBatchMode to identify if batch is caller
     * @throws PAException on error
     */
    void update(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs, List<StudySiteDTO> studySiteDTOs, 
            Bl isBatchMode) throws PAException;

    /**
     * Creates a proprietary study protocol.
     * 
     * @param studyProtocolDTO studyProtocolDTO
     * @param studySiteAccrualStatusDTO studySiteAccrualStatusDTO
     * @param documentDTOs documentDTOs
     * @param leadOrganizationDTO leadOrganizationDTO
     * @param studySiteInvestigatorDTO studySiteInvestigatorDTO
     * @param leadOrganizationStudySiteDTO leadOrganizationStudySiteDTO
     * @param studySiteOrganizationDTO studySiteOrganizationDTO
     * @param studySiteDTO studySiteDTO
     * @param nctIdentifierDTO nctIdentifierDTO
     * @param summary4OrganizationDTO summary4OrganizationDTO
     * @param summary4StudyResourcingDTO summary4StudyResourcingDTO
     * @param isBatchMode to identify if batch is caller
     * @return Ii
     * @throws PAException e
     */
    // CHECKSTYLE:OFF More than 7 parameters
    Ii createAbbreviatedInterventionalStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO, List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO, PersonDTO studySiteInvestigatorDTO,
            StudySiteDTO leadOrganizationStudySiteDTO, OrganizationDTO studySiteOrganizationDTO,
            StudySiteDTO studySiteDTO, StudySiteDTO nctIdentifierDTO, List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO, Bl isBatchMode) throws PAException;

    // CHECKSTYLE:ON
    
    /**
     * Creates a proprietary study protocol and assigns ownership.
     * 
     * @param studyProtocolDTO studyProtocolDTO
     * @param studySiteAccrualStatusDTO studySiteAccrualStatusDTO
     * @param documentDTOs documentDTOs
     * @param leadOrganizationDTO leadOrganizationDTO
     * @param studySiteInvestigatorDTO studySiteInvestigatorDTO
     * @param leadOrganizationStudySiteDTO leadOrganizationStudySiteDTO
     * @param studySiteOrganizationDTO studySiteOrganizationDTO
     * @param studySiteDTO studySiteDTO
     * @param nctIdentifierDTO nctIdentifierDTO
     * @param summary4OrganizationDTO summary4OrganizationDTO
     * @param summary4StudyResourcingDTO summary4StudyResourcingDTO
     * @param isBatchMode to identify if batch is caller
     * @param owners trial record owners identified by email addresses
     * @return Ii
     * @throws PAException e
     */
    // CHECKSTYLE:OFF More than 7 parameters
    Ii createAbbreviatedInterventionalStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO, List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO, PersonDTO studySiteInvestigatorDTO,
            StudySiteDTO leadOrganizationStudySiteDTO, OrganizationDTO studySiteOrganizationDTO,
            StudySiteDTO studySiteDTO, StudySiteDTO nctIdentifierDTO, List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO, Bl isBatchMode, DSet<Tel> owners) throws PAException;

    // CHECKSTYLE:ON
    

    /**
     * Reject a protocol and rollback all the changes.
     * @param studyProtocolIi study protocol identifier
     * @param rejectionReason rejectionReason
     * @param rejectionReasonCode rejectionReasonCode
     * @param milestoneCode milestoneCode
     * @throws PAException on error
     */
    void reject(Ii studyProtocolIi, St rejectionReason, Cd rejectionReasonCode, 
               MilestoneCode milestoneCode) throws PAException;
    
    // CHECKSTYLE:OFF More than 7 parameters
    
    /**
     * @param studyProtocolDTO
     * @param nctID
     * @param leadOrgDTO
     * @param leadOrgID
     * @param sponsorDTO
     * @param investigatorDTO
     * @param centralContactDTO
     * @param overallStatusDTO
     * @param regAuthDTO
     * @param arms
     * @param eligibility
     * @param outcomes
     * @param collaborators
     * @param documentDTOs
     * @return
     * @throws PAException
     */
    Ii createAbbreviatedStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            StudySiteDTO nctID, OrganizationDTO leadOrgDTO,
            StudySiteDTO leadOrgID, OrganizationDTO sponsorDTO,
            PersonDTO investigatorDTO, ResponsiblePartyDTO responsiblePartyDTO,
            PersonDTO centralContactDTO,
            StudyOverallStatusDTO overallStatusDTO,
            StudyRegulatoryAuthorityDTO regAuthDTO, List<ArmDTO> arms,
            List<PlannedEligibilityCriterionDTO> eligibility,
            List<StudyOutcomeMeasureDTO> outcomes,
            List<OrganizationDTO> collaborators, List<DocumentDTO> documentDTOs)
            throws PAException;
    
    /**
     * @param studyProtocolDTO
     * @param nctID     
     * @param sponsorDTO
     * @param investigatorDTO
     * @param centralContactDTO
     * @param overallStatusDTO
     * @param regAuthDTO
     * @param arms
     * @param eligibility
     * @param outcomes
     * @param collaborators
     * @param documentDTOs
     * @return
     * @throws PAException
     */
    Ii updateAbbreviatedStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            StudySiteDTO nctID, OrganizationDTO leadOrgDTO,
            StudySiteDTO leadOrgID, OrganizationDTO sponsorDTO,
            PersonDTO investigatorDTO, ResponsiblePartyDTO responsiblePartyDTO,
            PersonDTO centralContactDTO,
            StudyOverallStatusDTO overallStatusDTO,
            StudyRegulatoryAuthorityDTO regAuthDTO, List<ArmDTO> arms,
            List<PlannedEligibilityCriterionDTO> eligibility,
            List<StudyOutcomeMeasureDTO> outcomes,
            List<OrganizationDTO> collaborators, List<DocumentDTO> documentDTOs)
            throws PAException;
    // CHECKSTYLE:ON
    
    
    /**
     * Creates a study protocol.
     *
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs list of documents
     * @param leadOrganizationDTO Pead organization
     * @param principalInvestigatorDTO Principal Investigator
     * @param sponsorOrganizationDTO Sponsort Organization
     * @param leadOrganizationSiteIdentifierDTO local protocol identifier
     * @param studyIdentifierDTOs list of study Identifier
     * @param studyContactDTO phone and email info when Pi is responsible
     * @param studySiteContactDTO phone and email info when sponsor is responsible
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param responsiblePartyContactIi Id of the person when sponsor is responsible
     * @param studyRegAuthDTO studyRegAuthDTO
     * @param isBatchMode to identify if batch is caller
     * @return ii of Study Protocol
     * @throws PAException on error
     * @deprecated
     */
    // CHECKSTYLE:OFF More than 7 parameters
    @Deprecated
    Ii createCompleteInterventionalStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO, List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO, List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO, Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode) throws PAException;

    // CHECKSTYLE:ON
    
    /**
     * Creates a study protocol and assigns ownership.
     *
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs list of documents
     * @param leadOrganizationDTO Pead organization
     * @param principalInvestigatorDTO Principal Investigator
     * @param sponsorOrganizationDTO Sponsort Organization
     * @param leadOrganizationSiteIdentifierDTO local protocol identifier
     * @param studyIdentifierDTOs list of study Identifier
     * @param studyContactDTO phone and email info when Pi is responsible
     * @param studySiteContactDTO phone and email info when sponsor is responsible
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param responsiblePartyContactIi Id of the person when sponsor is responsible
     * @param studyRegAuthDTO studyRegAuthDTO
     * @param isBatchMode to identify if batch is caller
     * @param owners trial record owners identified by email addresses
     * @return ii of Study Protocol
     * @throws PAException on error
     * @deprecated
     */
    // CHECKSTYLE:OFF More than 7 parameters
    @Deprecated
    Ii createCompleteInterventionalStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO, List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO, List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO, Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode, DSet<Tel> owners) throws PAException;

    // CHECKSTYLE:ON
    
    
    /**
     * Amends a study protocol.
     *
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs list of documents
     * @param leadOrganizationDTO Pead organization
     * @param principalInvestigatorDTO Principal Investigator
     * @param sponsorOrganizationDTO Sponsort Organization
     * @param leadOrganizationSiteIdentifierDTO local protocol identifier
     * @param studyIdentifierDTOs list of Study Identifier
     * @param studyContactDTO phone and email info when Pi is responsible
     * @param studySiteContactDTO phone and email info when sponsor is responsible
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param responsiblePartyContactIi id of the person when sponsor is responsible
     * @param studyRegAuthDTO studyRegAuthDTO
     * @param isBatchMode to identify if batch is caller
     * @return ii of Study Protocol
     * @throws PAException on error
     * @deprecated
     */
    // CHECKSTYLE:OFF More than 7 parameters
    @Deprecated
    Ii amend(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs, List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO, List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO, Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode) throws PAException;

    // CHECKSTYLE:ON
    
    /**
     * Amends a study protocol.
     *
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs list of documents
     * @param leadOrganizationDTO Pead organization
     * @param principalInvestigatorDTO Principal Investigator
     * @param sponsorOrganizationDTO Sponsort Organization
     * @param leadOrganizationSiteIdentifierDTO local protocol identifier
     * @param studyIdentifierDTOs list of Study Identifier
     * @param studyContactDTO phone and email info when Pi is responsible
     * @param studySiteContactDTO phone and email info when sponsor is responsible
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param responsiblePartyContactIi id of the person when sponsor is responsible
     * @param studyRegAuthDTO studyRegAuthDTO
     * @param isBatchMode to identify if batch is caller
     * @param handleDuplicateGrantAndINDsGracefully PO-6172: ensure that the registration service does not fail 
     * an amendment with grant or IND/IDE data already in CTRP 
     * @return ii of Study Protocol
     * @throws PAException on error
     * @deprecated
     */
    // CHECKSTYLE:OFF More than 7 parameters
    @Deprecated
    Ii amend(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs, List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO, List<OrganizationDTO> summary4organizationDTO,
            StudyResourcingDTO summary4studyResourcingDTO, Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode, Bl handleDuplicateGrantAndINDsGracefully) 
                    throws PAException;

    /**
     * Updates a study protocol.
     *
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param studyIdentifierDTOs List of Study Identifier
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs List of documents IRB and Participating doc
     * @param studyContactDTO phone and email info when Pi is responsible
     * @param studyParticipationContactDTO StudySiteContactDTO
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param responsiblePartyContactIi id of the person when sponsor is responsible
     * @param studyRegAuthDTO updated studyRegAuthDTO
     * @param collaboratorDTOs list of updated collaborators
     * @param studySiteAccrualStatusDTOs list of updated participating sites
     * @param studySiteDTOs list of StudySite DTOs with updated program code
     * @param isBatchMode to identify if batch is caller
     * @throws PAException on error
     */
    // CHECKSTYLE:OFF More than 7 parameters
    void update(StudyProtocolDTO studyProtocolDTO, List<StudyOverallStatusDTO> overallStatusDTO,
            List<StudySiteDTO> studyIdentifierDTOs, List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            StudyContactDTO studyContactDTO, StudySiteContactDTO studyParticipationContactDTO,
            OrganizationDTO summary4organizationDTO, StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi, StudyRegulatoryAuthorityDTO studyRegAuthDTO,
            List<StudySiteDTO> collaboratorDTOs, List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs,
            List<StudySiteDTO> studySiteDTOs, Bl isBatchMode) throws PAException;

    // CHECKSTYLE:ON

}

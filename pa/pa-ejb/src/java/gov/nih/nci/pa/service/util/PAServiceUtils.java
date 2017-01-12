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

package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.OrganizationalStructuralRole;
import gov.nih.nci.pa.domain.OversightCommittee;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.SecondaryPurpose;
import gov.nih.nci.pa.domain.StructuralRole;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO.ResponsiblePartyType;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PAExceptionConstants;
import gov.nih.nci.pa.service.StudyPaService;
import gov.nih.nci.pa.service.correlation.ClinicalResearchStaffCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.HealthCareProviderCorrelationBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.correlation.PARelationServiceBean;
import gov.nih.nci.pa.util.CommonsConstant;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PhoneUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.TrialRegistrationValidator;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.CorrelationService;
import gov.nih.nci.services.EntityDto;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.correlation.AbstractEnhancedOrganizationRoleDTO;
import gov.nih.nci.services.correlation.AbstractPersonRoleDTO;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * A utility class for all pa services .
 * @author Naveen Amiruddin
 * @since 10/26/2009
 */
@SuppressWarnings("unchecked")
public class PAServiceUtils {
    private static final String DUPLICATE_IND_ERR_MSG = "Duplicate IND/IDEs are not allowed.";
    private static final Logger LOG = Logger.getLogger(PAServiceUtils.class);
    private static final String ERR_MSG = "Found more than 1 record for a protocol id = %s for a given status %s";
    /**ORGANIZATION_IDENTIFIER_IS_NULL.*/
    public static final String ORGANIZATION_IDENTIFIER_IS_NULL = "Organization Identifier is null";
    /**PRINCIPAL_INVESTIGATOR_NULLIFIED.*/
    public static final String PRINCIPAL_INVESTIGATOR = "Principal Investigator: ";
    /** SPONSOR_NULLIFIED.*/
    public static final String SPONSOR = "Sponsor: ";
    /**SPONSOR_NULLIFIED.*/
    public static final String RESP_PARTY = "Responsible Party: ";
    /**LEAD_ORGANIZATION_NULLIFIED.*/
    public static final String LEAD_ORGANIZATION_NULLIFIED = "The Lead Organization has been nullified";
    /**The size of the counter portion of the NCI ID.*/
    protected static final int NCI_ID_SIZE = 5;
    private static final String ERR_MSG_SEPARATOR = "\n";    
    /**
     * Executes an sql.
     * @param sql sql to be executed
     * @return number of counts
     */
    public int executeSql(String sql) {
        Session session = PaHibernateUtil.getCurrentSession();
        return session.createSQLQuery(sql).executeUpdate();
    }    
    /**
     * Executes an list of sql.
     * @param sqls list of sqls
     */
    public void executeSql(List<String> sqls) {
        for (String sql : sqls) {
            executeSql(sql);
        }
    }
    /**
     * does a deep copy of protocol to a new protocol.
     * @param fromStudyProtocolIi study protocol ii
     * @throws PAException on error
     * @return ii
     */
    public Ii copy(Ii fromStudyProtocolIi) throws PAException {
        StudyProtocolDTO dto =
                PaRegistry.getStudyProtocolService().getStudyProtocol(fromStudyProtocolIi);
        dto.setIdentifier(null);
        dto.setStatusCode(CdConverter.convertToCd(ActStatusCode.INACTIVE));
        Ii toIi;
        if (dto instanceof NonInterventionalStudyProtocolDTO) {
            toIi = PaRegistry.getStudyProtocolService()
                    .createNonInterventionalStudyProtocol(
                            (NonInterventionalStudyProtocolDTO) dto);
        } else {
            toIi = PaRegistry.getStudyProtocolService()
                    .createInterventionalStudyProtocol(
                            (InterventionalStudyProtocolDTO) dto);
        }        
        getRemoteService(IiConverter.convertToStudyMilestoneIi(null)).copy(fromStudyProtocolIi, toIi);
        getRemoteService(IiConverter.convertToDocumentWorkFlowStatusIi(null)).copy(fromStudyProtocolIi, toIi);
        getRemoteService(IiConverter.convertToStudyIndIdeIi(null)).copy(fromStudyProtocolIi, toIi);
        getRemoteService(IiConverter.convertToStudyDiseaseIi(null)).copy(fromStudyProtocolIi, toIi);
        getRemoteService(IiConverter.convertToStudyObjectiveIi(null)).copy(fromStudyProtocolIi, toIi);
        getRemoteService(IiConverter.convertToStratumGroupIi(null)).copy(fromStudyProtocolIi, toIi);
        getRemoteService(IiConverter.convertToStudyResourcingIi(null)).copy(fromStudyProtocolIi, toIi);
        getRemoteService(IiConverter.convertToStudyOnHoldIi(null)).copy(fromStudyProtocolIi, toIi);
        getRemoteService(IiConverter.convertToStudyOverallStatusIi(null)).copy(fromStudyProtocolIi, toIi);
        getRemoteService(IiConverter.convertToStudyRecruitmentStatusIi(null)).copy(fromStudyProtocolIi, toIi);
        getRemoteService(IiConverter.convertToArmIi(null)).copy(fromStudyProtocolIi, toIi);
        getRemoteService(IiConverter.convertToStudyContactIi(null)).copy(fromStudyProtocolIi, toIi);
        getRemoteService(IiConverter.convertToStudySiteIi(null)).copy(fromStudyProtocolIi, toIi);
        getRemoteService(IiConverter.convertToStudyOutcomeMeasureIi(null)).copy(fromStudyProtocolIi, toIi);
        getRemoteService(IiConverter.convertToStudyRegulatoryAuthorityIi(null)).copy(fromStudyProtocolIi, toIi);
        getRemoteService(IiConverter.convertToDocumentIi(null)).copy(fromStudyProtocolIi, toIi);
        PaRegistry.getPlannedMarkerService().copy(fromStudyProtocolIi, toIi);
        addNciIdentifierToTrial(toIi);
        return toIi;
    }
    /**
     * Method to add an nci identifier to a study protocol.
     * @param spIi trial ii
     * @throws PAException on error.
     */
    public void addNciIdentifierToTrial(Ii spIi) throws PAException {
        if (ISOUtil.isIiNull(spIi)) {
            throw new PAException("Trial must have an Ii created.");
        }
        Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) session.get(StudyProtocol.class, Long.valueOf(spIi.getExtension()));
        //check if the assigned identifier exists
        //if no - generate the nci identifier and set it in the sp.
        if (!PADomainUtils.checkAssignedIdentifier(sp)) {
            Ii spSecAssignedId = IiConverter.convertToAssignedIdentifierIi(generateNciIdentifier(session));
            if (sp.getOtherIdentifiers() != null) {
              sp.getOtherIdentifiers().add(spSecAssignedId);
            } else {
              Set<Ii> secondaryIds = new HashSet<Ii>();
              secondaryIds.add(spSecAssignedId);
              sp.setOtherIdentifiers(secondaryIds);
            }
          }
        session.save(sp);
    }
    /**
     * Generate a unique nci id.
     * @param session the session
     * @return string nci id.
     */
    protected String generateNciIdentifier(Session session) {
        Calendar today = Calendar.getInstance();
        int currentYear  = today.get(Calendar.YEAR);
        String query = CommonsConstant.SELECT_NEXTVAL_NCI_IDENTIFIERS_SEQ;
        StringBuffer nciIdentifier = new StringBuffer();
        nciIdentifier.append("NCI-");
        nciIdentifier.append(currentYear);
        nciIdentifier.append('-');

        Query queryObject = session.createSQLQuery(query);
        String maxValue = queryObject.uniqueResult().toString();
        String maxNumber = maxValue.substring(maxValue.lastIndexOf('-') + 1 , maxValue.length());
        String nextNumber = String.valueOf(Integer.parseInt(maxNumber));
        nciIdentifier.append(StringUtils.leftPad(nextNumber, NCI_ID_SIZE, "0"));

        return nciIdentifier.toString();
    }
    /**
     * an utility method to create or update.
     * @param <T> the dto
     * @param dtos list of dtos
     * @param id identifier
     * @param studyProtocolIi study protocol ii
     * @return the updated or created dtos
     * @throws PAException on error
     */
    public <T extends StudyDTO> List<T> createOrUpdate(List<T> dtos, Ii id, Ii studyProtocolIi) throws PAException {
        List<T> results = new ArrayList<T>();
        if (CollectionUtils.isEmpty(dtos)) {
            return results;
        }
        //validate all documents before saving (PO-9433)
        StudyPaService<StudyDTO> paService = getRemoteService(id);
        for (StudyDTO dto : dtos) {
            dto.setStudyProtocolIdentifier(studyProtocolIi);
            paService.validate(dto);
        }
        for (StudyDTO dto : dtos) {
            if (ISOUtil.isIiNull(dto.getIdentifier())) {
                results.add((T) paService.create(dto));
            } else {
                results.add((T) paService.update(dto));
            }
        }
        return results;
    }
    /**
     *
     * @param <TYPE> any type extending StudyPaService
     * @param isoIi iso ii
     * @return any type extending StudyPaService
     * @throws PAException on error
     */
    public <TYPE extends StudyPaService<?>>  TYPE getRemoteService(Ii isoIi) throws PAException {
        if (IiConverter.STUDY_MILESTONE_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            return (TYPE) PaRegistry.getStudyMilestoneService();
        } else if (IiConverter.STUDY_IND_IDE_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            return (TYPE) PaRegistry.getStudyIndldeService();
        } else if (IiConverter.STUDY_DISEASE_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            return (TYPE) PaRegistry.getStudyDiseaseService();
        } else if (IiConverter.STUDY_OBJECTIVE_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            return (TYPE) PaRegistry.getStudyObjectiveService();
        } else if (IiConverter.STRATUM_GROUP_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            return (TYPE) PaRegistry.getStratumGroupService();
        } else if (IiConverter.STUDY_RESOURCING_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            return (TYPE) PaRegistry.getStudyResourcingService();
        } else if (IiConverter.STUDY_ONHOLD_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            return (TYPE) PaRegistry.getStudyOnholdService();
        } else if (IiConverter.STUDY_OVERALL_STATUS_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            return (TYPE) PaRegistry.getStudyOverallStatusService();
        } else if (IiConverter.STUDY_RECRUITMENT_STATUS_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            return (TYPE) PaRegistry.getStudyRecruitmentStatusService();
        } else if (IiConverter.PLANNED_ACTIVITY_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            return (TYPE) PaRegistry.getPlannedActivityService();
        } else if (IiConverter.ARM_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            return (TYPE) PaRegistry.getArmService();
        } else if (IiConverter.STUDY_SITE_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            return (TYPE) PaRegistry.getStudySiteService();
        } else if (IiConverter.STUDY_CONTACT_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            return (TYPE) PaRegistry.getStudyContactService();
        } else if (IiConverter.STUDY_OUTCOME_MEASURE_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            return (TYPE) PaRegistry.getStudyOutcomeMeasurService();
        } else if (IiConverter.DOCUMENT_WORKFLOW_STATUS_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            return (TYPE) PaRegistry.getDocumentWorkflowStatusService();
        } else if (IiConverter.STUDY_REGULATORY_AUTHORITY_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            return (TYPE) PaRegistry.getStudyRegulatoryAuthorityService();
        } else if (IiConverter.DOCUMENT_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            return (TYPE) PaRegistry.getDocumentService();
        } else {
            throw new PAException(" unknown identifier name provided  : " + isoIi.getIdentifierName());
        }
    }
    
    /**
     * removes the sponsor contact.
     * @param studyProtocolIi studyPorotocol Ii
     * @throws PAException on error
     */
    public void removeResponsibleParty(Ii studyProtocolIi) throws PAException {
        PaRegistry.getStudyContactService().removeResponsiblePartyContact(studyProtocolIi);
        
        // delete from Study Site and it will delete study_site contact
        List<StudySiteDTO> spDtos = getRespPartySponsorSites(studyProtocolIi);
        if (CollectionUtils.isNotEmpty(spDtos)) {
            PaRegistry.getStudySiteService().delete(spDtos.get(0).getIdentifier());
        }
        
    }
    /**
     * @param studyProtocolIi
     * @return
     * @throws PAException
     */
    private List<StudySiteDTO> getRespPartySponsorSites(Ii studyProtocolIi)
            throws PAException {
        StudySiteDTO spart = new StudySiteDTO();
        spart.setFunctionalCode(CdConverter.convertToCd(
              StudySiteFunctionalCode.RESPONSIBLE_PARTY_SPONSOR));
        List<StudySiteDTO> spDtos = PaRegistry.getStudySiteService()
              .getByStudyProtocol(studyProtocolIi, spart);
        return spDtos;
    }
    
    
  
    /**
     * @param studyProtocolIi
     *            studyProtocolIi
     * @return boolean
     * @throws PAException PAException  
     */
    public boolean isResponsiblePartySponsor(Ii studyProtocolIi) throws PAException {
        return CollectionUtils.isNotEmpty(getRespPartySponsorSites(studyProtocolIi));
    }
    
    /**
     * removes the sponsor contact.
     * @param studyProtocolIi studyPorotocol Ii
     * @throws PAException on error
     */
    public void removeSponsor(Ii studyProtocolIi) throws PAException {
        StudySiteDTO ssCriteriaDTO = new StudySiteDTO();
        ssCriteriaDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
        ssCriteriaDTO.setStudyProtocolIdentifier(studyProtocolIi);
        List<StudySiteDTO> spDtos = PaRegistry.getStudySiteService().getByStudyProtocol(studyProtocolIi, ssCriteriaDTO);
        if (CollectionUtils.isNotEmpty(spDtos)) {
            PaRegistry.getStudySiteService().delete(spDtos.get(0).getIdentifier());
        }
    }
    /**
     * removes the regulatory authority.
     * @param studyProtocolIi studyPorotocol Ii
     * @throws PAException on error
     */
    public void removeRegulatoryAuthority(Ii studyProtocolIi) throws PAException {
        List<StudyRegulatoryAuthorityDTO> sraDtos = PaRegistry.getStudyRegulatoryAuthorityService()
            .getByStudyProtocol(studyProtocolIi);
        if (CollectionUtils.isNotEmpty(sraDtos)) {
            PaRegistry.getStudyRegulatoryAuthorityService().delete(sraDtos.get(0).getIdentifier());
        }
    }

    /**
     * @param studyProtocolIi studyProtocolIi
     * @param partyDTO partyDTO
     * @param sponsorOrganizationDTO sponsorOrganizationDTO
     * @throws PAException PAException
     */
    public void createResponsibleParty(Ii studyProtocolIi,
            ResponsiblePartyDTO partyDTO, OrganizationDTO sponsorOrganizationDTO)
            throws PAException {
        if (ResponsiblePartyType.PRINCIPAL_INVESTIGATOR.equals(partyDTO
                .getType())) {
            createPIAsResponsibleParty(studyProtocolIi,
                    partyDTO.getAffiliation(), partyDTO.getInvestigator(),
                    partyDTO.getTitle());
        } else if (ResponsiblePartyType.SPONSOR_INVESTIGATOR.equals(partyDTO
                .getType())) {
            createSIAsResponsibleParty(studyProtocolIi,
                    partyDTO.getAffiliation(), partyDTO.getInvestigator(),
                    partyDTO.getTitle());
        } else {
            createSponsorAsPrimaryContact(studyProtocolIi,
                    sponsorOrganizationDTO);
        }
    }
    

    /**
     * @param studyProtocolIi studyProtocolIi
     * @param affiliationOrg affiliationOrg
     * @param investigator investigator
     * @param title title
     * @throws PAException PAException
     */
    public void createPIAsResponsibleParty(Ii studyProtocolIi,
            OrganizationDTO affiliationOrg, PersonDTO investigator, String title)
            throws PAException {
        new PARelationServiceBean().createPIAsResponsiblePartyRelations(
                affiliationOrg.getIdentifier().getExtension(), investigator
                        .getIdentifier().getExtension(), IiConverter
                        .convertToLong(studyProtocolIi), title);
    }
    
    /**
     * @param studyProtocolIi studyProtocolIi
     * @param affiliationOrg affiliationOrg
     * @param investigator investigator
     * @param title title
     * @throws PAException PAException
     */
    public void createSIAsResponsibleParty(Ii studyProtocolIi,
            OrganizationDTO affiliationOrg, PersonDTO investigator, String title)
            throws PAException {
        new PARelationServiceBean().createSIAsResponsiblePartyRelations(
                affiliationOrg.getIdentifier().getExtension(), investigator
                        .getIdentifier().getExtension(), IiConverter
                        .convertToLong(studyProtocolIi), title);
    }

    private void createSponsorAsPrimaryContact(Ii studyProtocolIi,
            OrganizationDTO sponsorOrganizationDTO) throws PAException {
        PAContactDTO contactDto = new PAContactDTO();
        contactDto.setOrganizationIdentifier(IiConverter
                .convertToPoOrganizationIi(sponsorOrganizationDTO
                        .getIdentifier().getExtension()));
        contactDto.setStudyProtocolIdentifier(studyProtocolIi);

        new PARelationServiceBean()
                .createSponsorAsPrimaryContactRelations(contactDto);
    }
    /**
     *
     * @param studyProtocolIi study protocol identifier
     * @param organizationDtoList organization Dto
     * @param summary4studyResourcingDTO summary four Resourcing Dto
     * @throws PAException on error
     */
    @SuppressWarnings("deprecation")
    public void manageSummaryFour(Ii studyProtocolIi, List<OrganizationDTO> organizationDtoList,
            StudyResourcingDTO summary4studyResourcingDTO) throws PAException {
        List<StudyResourcingDTO> summaryFourOrgList = PaRegistry.getStudyResourcingService()
                .getSummary4ReportedResourcing(studyProtocolIi);
        List<Long> dbOrgIds = new ArrayList<Long>();
        List<Long> summaryFourOrgIds = new ArrayList<Long>();
        for (StudyResourcingDTO dto : summaryFourOrgList) {
            dbOrgIds.add(IiConverter.convertToLong(dto.getOrganizationIdentifier()));
        }
        
        for (OrganizationDTO organizationDto : organizationDtoList) {
            if (organizationDto != null && organizationDto.getIdentifier() != null) {
                SummaryFourFundingCategoryCode summaryFourFundingCategoryCode = null;

                if (summary4studyResourcingDTO != null && !ISOUtil.isCdNull(summary4studyResourcingDTO.getTypeCode())) {
                    summaryFourFundingCategoryCode = SummaryFourFundingCategoryCode.getByCode(
                            summary4studyResourcingDTO.getTypeCode().getCode());
                }
                CorrelationUtils corrUtils = new CorrelationUtils();
                String orgPoIdentifier = organizationDto.getIdentifier().getExtension();
                if (orgPoIdentifier  == null) {
                    throw new PAException(" Organization PO Identifier is null");
                }

                // Step 1 : get the PO Organization
                OrganizationDTO poOrg = null;
                try {
                    poOrg = PoRegistry.getOrganizationEntityService()
                            .getOrganization(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
                } catch (NullifiedEntityException e) {
                    // Map m = e.getNullifiedEntities();
                    // LOG.error("This Organization is no longer available instead use
                    // ");
                    throw new PAException("This Organization is no longer available instead use ", e);
                }
                // Step 3 : check for pa org, if not create one
                Organization paOrg = corrUtils.getPAOrganizationByIi(
                        IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
                if (paOrg == null) {
                    paOrg = corrUtils.createPAOrganization(poOrg);
                }

                StudyResourcingDTO summary4ResoureDTO = PaRegistry.getStudyResourcingService()
                        .getSummary4ReportedResourcingBySpAndOrgId(studyProtocolIi, paOrg.getId());
                if (summary4ResoureDTO == null) {
                    // summary 4 record does not exist,so create a new one
                    summary4ResoureDTO = new StudyResourcingDTO();
                    summary4ResoureDTO.setStudyProtocolIdentifier(studyProtocolIi);
                    summary4ResoureDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.TRUE));
                    if (summaryFourFundingCategoryCode != null) {
                        summary4ResoureDTO.setTypeCode(CdConverter.convertToCd(summaryFourFundingCategoryCode));
                    }
                    summary4ResoureDTO.setOrganizationIdentifier(IiConverter.convertToIi(paOrg.getId()));
                    PaRegistry.getStudyResourcingService().createStudyResourcing(summary4ResoureDTO);
                } else {
                    // summary 4 record does exist,so so do an update
                    summary4ResoureDTO.setStudyProtocolIdentifier(studyProtocolIi);
                    summary4ResoureDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.TRUE));
                    if (summaryFourFundingCategoryCode != null) {
                        summary4ResoureDTO.setTypeCode(CdConverter.convertToCd(summaryFourFundingCategoryCode));
                    }
                    summary4ResoureDTO.setOrganizationIdentifier(IiConverter.convertToIi(paOrg.getId()));
                    PaRegistry.getStudyResourcingService().updateStudyResourcing(summary4ResoureDTO);
                }
                summaryFourOrgIds.add(paOrg.getId());
            }
        }
        for (Long orgId : dbOrgIds) {
            if (orgId != null && CollectionUtils.isNotEmpty(summaryFourOrgIds)
                    && !summaryFourOrgIds.contains(orgId)) {
                //delete the row.
                StudyResourcingDTO summary4ResoureDTO = PaRegistry.getStudyResourcingService()
                        .getSummary4ReportedResourcingBySpAndOrgId(studyProtocolIi, orgId);
                PaRegistry.getStudyResourcingService().delete(summary4ResoureDTO.getIdentifier());
            }
        }
    }

    /**
     *
     * @param studyProtocolIi Study Protocol Identifier
     * @param sponsorDto Lead Organization Dto
     * @throws PAException on error
     */
    public void manageSponsor(Ii studyProtocolIi, OrganizationDTO sponsorDto) throws PAException {
        OrganizationCorrelationServiceRemote ocsr = PaRegistry.getOrganizationCorrelationService();
        String orgPoIdentifier = sponsorDto.getIdentifier().getExtension();
        validateNotNullParamtrs(studyProtocolIi, orgPoIdentifier);
        Long roId = null;
        try {
            roId = ocsr.createResearchOrganizationCorrelations(orgPoIdentifier);
        }  catch (PAException pae) {
            if (pae.getMessage().contains(PAExceptionConstants.NULLIFIED_ORG)) {
                throw new PAException(SPONSOR + pae.getMessage(), pae);
            }
            throw pae;
        }
        StudySiteDTO ssCriteriaDTO = new StudySiteDTO();
        ssCriteriaDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
        ssCriteriaDTO.setStudyProtocolIdentifier(studyProtocolIi);
        List<StudySiteDTO> studySiteDtos = getStudySite(ssCriteriaDTO, true);
        StudySiteDTO studySiteDTO = null;
        if (PAUtil.getFirstObj(studySiteDtos) != null) {
            studySiteDTO = PAUtil.getFirstObj(studySiteDtos);
        } else {
            studySiteDTO = new StudySiteDTO();
        }
        studySiteDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
        studySiteDTO.setResearchOrganizationIi(IiConverter.convertToIi(roId));
        studySiteDTO.setStudyProtocolIdentifier(studyProtocolIi);
        studySiteDtos  = new ArrayList<StudySiteDTO>();
        studySiteDtos.add(studySiteDTO);
        try {
            createOrUpdate(studySiteDtos, IiConverter.convertToStudySiteIi(null), studyProtocolIi);
        }  catch (PAException pae) {
            if (pae.getMessage().contains(PAExceptionConstants.NULLIFIED_PERSON)) {
                throw new PAException(RESP_PARTY + pae.getMessage(), pae);
            }
            throw pae;
        }
    }

    private void validateNotNullParamtrs(Ii studyProtocolIi, String orgPoIdentifier) throws PAException {
        if (orgPoIdentifier == null) {
            throw new PAException(ORGANIZATION_IDENTIFIER_IS_NULL);
        }
        if (studyProtocolIi == null) {
            throw new PAException("Protocol Identifier is Null");
        }
    }

    /**
     *
     * @param studyProtocolIi study protocol identifier
     * @param leadOrganizationDto lead organization identifier
     * @param principalInvestigatorDto pi    
     * @throws PAException on error
     */
    public void managePrincipalInvestigator(Ii studyProtocolIi, OrganizationDTO leadOrganizationDto,
            PersonDTO principalInvestigatorDto) throws PAException {
        String orgPoIdentifier = leadOrganizationDto.getIdentifier().getExtension();
        String personPoIdentifier = principalInvestigatorDto.getIdentifier().getExtension();
        if (orgPoIdentifier == null) {
            throw new PAException(" Organization PO Identifier is null");
        }
        if (personPoIdentifier == null) {
            throw new PAException(" Person PO Identifier is null");
        }
        if (studyProtocolIi == null) {
            throw new PAException("Study Protocol Identifier is null");
        }        
        ClinicalResearchStaffCorrelationServiceBean crs = new ClinicalResearchStaffCorrelationServiceBean();
        HealthCareProviderCorrelationBean hcp = new HealthCareProviderCorrelationBean();
        Long crsId = null;
        Long hcpId = null;
        try {
            crsId = crs.createClinicalResearchStaffCorrelations(orgPoIdentifier, personPoIdentifier);
            hcpId = hcp.createHealthCareProviderCorrelationBeans(orgPoIdentifier, personPoIdentifier);            
        } catch (PAException pae) {
            if (pae.getMessage().contains(PAExceptionConstants.NULLIFIED_PERSON)) {
                throw new PAException(PRINCIPAL_INVESTIGATOR + pae.getMessage(), pae);
            } else if (pae.getMessage().contains(PAExceptionConstants.NULLIFIED_ORG)) {
                throw new PAException(LEAD_ORGANIZATION_NULLIFIED , pae);
            } else {
                throw pae;
            }
        }
        List<StudyContactDTO> studyContactDtos =
            getStudyContact(studyProtocolIi, StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR, true);
        StudyContactDTO studyContactDTO = null;
        if (PAUtil.getFirstObj(studyContactDtos) != null) {
            studyContactDTO = PAUtil.getFirstObj(studyContactDtos);
        } else {
            studyContactDTO = new StudyContactDTO();
        }
        studyContactDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(crsId));
        studyContactDTO.setHealthCareProviderIi(IiConverter.convertToIi(hcpId));
        studyContactDTO.setRoleCode(CdConverter.convertStringToCd(
                StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode()));
        studyContactDTO.setStudyProtocolIdentifier(studyProtocolIi);
        studyContactDtos  = new ArrayList<StudyContactDTO>();
        studyContactDtos.add(studyContactDTO);
        createOrUpdate(studyContactDtos, IiConverter.convertToStudyContactIi(null), studyProtocolIi);
    }

    /**
     * checks if any device is found in the planned activities list.
     * @param studyProtocolIi sp id
     * @param paList planned activities list
     * @return boolean
     */
    public boolean isDeviceFound(Ii studyProtocolIi , List<PlannedActivityDTO> paList) {
        boolean found = false;
        for (PlannedActivityDTO pa : paList) {
            if (pa.getCategoryCode() != null
                    && ActivityCategoryCode.INTERVENTION
                            .equals(ActivityCategoryCode.getByCode(CdConverter
                                    .convertCdToString(pa.getCategoryCode())))
                    && pa.getSubcategoryCode() != null
                    && pa.getSubcategoryCode().getCode() != null
                    && InterventionTypeCode.DEVICE.getCode().equalsIgnoreCase(
                            pa.getSubcategoryCode().getCode())) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     *
     * @param spDto functional code, StudyProtocol Identifier
     * @param isUnique determines if the result is unique
     * @return list of StudySiteDtos
     * @throws PAException on error
     */
    @SuppressWarnings("PMD.PreserveStackTrace") // TooManyResultsException stack trace isn't needed
    public List<StudySiteDTO> getStudySite(StudySiteDTO spDto, boolean isUnique) throws PAException {
        if (ISOUtil.isIiNull(spDto.getStudyProtocolIdentifier())) {
            throw new PAException(" StudyProtocol Ii is null");
        }
        LimitOffset pagingParams = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<StudySiteDTO> spDtos;
        try {
            spDtos = PaRegistry.getStudySiteService().search(spDto, pagingParams);
        } catch (TooManyResultsException e) {
            throw new PAException(String.format(ERR_MSG, spDto.getStudyProtocolIdentifier().getExtension(),
                                                spDto.getFunctionalCode().getCode()));
        }
        if (spDtos != null && spDtos.size() == 1) {
            return spDtos;
        } else if (spDtos != null && spDtos.size() > 1 && isUnique) {
            throw new PAException(String.format(ERR_MSG, spDto.getStudyProtocolIdentifier().getExtension(),
                                                spDto.getFunctionalCode().getCode()));

        }
        return spDtos;
    }

    /**
     *
     * @param studyProtocolIi StudyProtocol Identifier
     * @param scCode functional code
     * @param isUnique determines if the result is unique
     * @return list of StudySiteDtos
     * @throws PAException on error
     */
    public List<StudyContactDTO> getStudyContact(Ii studyProtocolIi , StudyContactRoleCode scCode , boolean isUnique)
    throws PAException {
        if (studyProtocolIi == null) {
            throw new PAException(" StudyProtocol Ii is null");
        }
        StudyContactDTO spDto = new StudyContactDTO();
        Cd cd = CdConverter.convertToCd(scCode);
        spDto.setRoleCode(cd);

        List<StudyContactDTO> spDtos = PaRegistry.getStudyContactService().getByStudyProtocol(studyProtocolIi, spDto);
        if (spDtos != null && spDtos.size() == 1) {
            return spDtos;
        } else if (spDtos != null && spDtos.size() > 1 && isUnique) {
            throw new PAException(String.format(ERR_MSG, studyProtocolIi.getExtension(), cd.getCode()));

        }
        return spDtos;
    }

    /**
     *
     * @param studyIndldeDTOs list of dtos
     * @param studyProtocolDTO studyProtocol
     * @throws PAException on error
     * @return Collection<String>
     * 
     */
    public Collection<String> enforceNoDuplicateIndIde(
            List<StudyIndldeDTO> studyIndldeDTOs,
            StudyProtocolDTO studyProtocolDTO) throws PAException  {
        Set<String> errorMsg = new LinkedHashSet<String>();
        if (CollectionUtils.isNotEmpty(studyIndldeDTOs)) {
            for (int i = 0; i < studyIndldeDTOs.size(); i++) {
                StudyIndldeDTO sp = studyIndldeDTOs.get(i);
                isIndIdeUpdated(errorMsg, sp);
                for (int j = i + 1; j < studyIndldeDTOs.size(); j++) {
                    StudyIndldeDTO newType = studyIndldeDTOs.get(j);
                    if (isIndIdeDuplicate(sp, newType)) {
                        errorMsg.add(DUPLICATE_IND_ERR_MSG + ERR_MSG_SEPARATOR);
                    }
                }
            }
            Boolean ctGovIndicator = BlConverter
                    .convertToBoolean(studyProtocolDTO
                            .getCtgovXmlRequiredIndicator());
            if (containsNonExemptInds(studyIndldeDTOs)
                    && BooleanUtils.isTrue(ctGovIndicator)
                    && !BlConverter.convertToBool(studyProtocolDTO
                            .getFdaRegulatedIndicator())) {
                errorMsg.add(TrialRegistrationValidator.FDA_REGULATED_INTERVENTION_INDICATOR_ERR_MSG
                        + ERR_MSG_SEPARATOR);
            }
        }
        return errorMsg;
    }

    /**
     * @param errorMsg
     * @param sp
     * @throws NumberFormatException 
     * @throws PAException
     */
    private void isIndIdeUpdated(Collection<String> errorMsg, StudyIndldeDTO sp) throws PAException  {
        if (!ISOUtil.isIiNull(sp.getIdentifier())) {
            StudyPaService<StudyDTO> paService = getRemoteService(IiConverter.convertToStudyIndIdeIi(
                    Long.valueOf(sp.getIdentifier().getExtension())));
            StudyIndldeDTO dbDTO = (StudyIndldeDTO) paService.get(sp.getIdentifier());
            if (dbDTO == null) {
                errorMsg.add("IND/IDE ID " + sp.getIdentifier().getExtension() + " does not exist");
            } else if (!isIndIdeDuplicate(sp, dbDTO)) {
                errorMsg.add("Existing IND/IDEs cannot be modified." + ERR_MSG_SEPARATOR);
            }
        }
    }
    /**
     * @param ind indDTO
     * @param toCompare indDTO
     * @return isDup
     */
    public boolean isIndIdeDuplicate(StudyIndldeDTO ind, StudyIndldeDTO toCompare) {
        boolean sameType = StringUtils.equals(toCompare.getIndldeTypeCode().getCode(),
                ind.getIndldeTypeCode().getCode());
        boolean sameNumber = StringUtils.equals(StringUtils.trim(toCompare.getIndldeNumber().getValue()),
                StringUtils.trim(ind.getIndldeNumber().getValue()));
        boolean sameGrantor = StringUtils.equals(toCompare.getGrantorCode().getCode(), ind.getGrantorCode().getCode());
        return sameType && sameNumber && sameGrantor;
    }
    /**
     *
     * @param studyResourcingDTOs list of
     * @throws PAException on error
     */
    public void enforceNoDuplicateGrants(List<StudyResourcingDTO> studyResourcingDTOs) throws PAException {
        StringBuffer errorMsg = new StringBuffer();
        if (CollectionUtils.isNotEmpty(studyResourcingDTOs)) {
            for (int i = 0; i < studyResourcingDTOs.size(); i++) {
                StudyResourcingDTO sp =  studyResourcingDTOs.get(i);
                isGrantUpdated(sp, errorMsg);
                for (int j = ++i; j < studyResourcingDTOs.size(); j++) {
                    StudyResourcingDTO newType =  studyResourcingDTOs.get(j);
                    if (isGrantDuplicate(sp, newType)) {
                        errorMsg.append("Duplicate grants are not allowed." + ERR_MSG_SEPARATOR);
                  }
                }
            }
            if (errorMsg.length() > 1) {
               throw new PAException(errorMsg.toString());
            }
        }
    }
    /**
     * @param sp
     * @return
     * @throws PAException
     */
    private void isGrantUpdated(StudyResourcingDTO sp, StringBuffer errorMsg) throws PAException {
        if (!ISOUtil.isIiNull(sp.getIdentifier())) {
            StudyPaService<StudyDTO> paService = getRemoteService(IiConverter.convertToStudyResourcingIi(
                    Long.valueOf(sp.getIdentifier().getExtension())));
            StudyResourcingDTO dbDTO = (StudyResourcingDTO) paService.get(sp.getIdentifier());
            if (dbDTO == null) {
                errorMsg.append("Grant ID " + sp.getIdentifier().getExtension() + " does not exist");
            } else if (!areTwoGrantsSame(sp, dbDTO)) {
                errorMsg.append("Existing grants cannot be modified." + ERR_MSG_SEPARATOR);
            }
        }
    }

    /**
     * @param grantDto
     *            grant DTO
     * @param grantToCompare
     *            grant DTO
     * @return isGrantDup
     */
    public boolean isGrantDuplicate(StudyResourcingDTO grantDto,
            StudyResourcingDTO grantToCompare) {
        // Check if the grant with the same duplicate is marked deleted. If yes
        // then return false.
        final Boolean active = BlConverter.convertToBoolean(grantToCompare
                .getActiveIndicator());
        if (Boolean.FALSE.equals(active)) {
            return false;
        }
        return areTwoGrantsSame(grantDto, grantToCompare);
    }

    private boolean areTwoGrantsSame(StudyResourcingDTO grantDto,
            StudyResourcingDTO grantToCompare) {
        boolean sameFundingMech = StringUtils.equals(grantToCompare.getFundingMechanismCode().getCode(),
                       grantDto.getFundingMechanismCode().getCode());
        boolean sameNih = StringUtils.equals(grantToCompare.getNihInstitutionCode().getCode(),
                       grantDto.getNihInstitutionCode().getCode());
        boolean sameNci = StringUtils.equals(grantToCompare.getNciDivisionProgramCode().getCode(),
                        grantDto.getNciDivisionProgramCode().getCode());
        boolean sameSerial = StringUtils.equals(grantToCompare.getSerialNumber().getValue(),
                        grantDto.getSerialNumber().getValue());
        return sameFundingMech && sameNih && sameNci && sameSerial;
    }
    /**
     *
     * @param identifier nci Identifier
     * @return next submission number
     */
    public Integer generateSubmissionNumber(String identifier) {
        Session session = PaHibernateUtil.getCurrentSession();
        String query = "select max(sp.submissionNumber) from StudyProtocol sp "
            + "join sp.otherIdentifiers oi where "
            + "oi.extension = '" + identifier + "' ";
        Integer maxValue = (Integer) session.createQuery(query).list().get(0);
        return (maxValue == null ? 1 : maxValue + 1);
    }
    /**
     * Enforce recruitment status.
     *
     * @param studyProtocolDTO the study protocol dto
     * @param participatingSites the participating sites
     * @param studyOverallStatus the recruitment status dto
     *
     * @throws PAException the PA exception
     */
    public void enforceRecruitmentStatus(StudyProtocolDTO studyProtocolDTO,
            List<StudySiteAccrualStatusDTO> participatingSites, StudyOverallStatusDTO studyOverallStatus)
            throws PAException {
        RecruitmentStatusCode studyRecruitmentStatus = RecruitmentStatusCode
                .getByStatusCode(StudyStatusCode.getByCode(studyOverallStatus
                        .getStatusCode().getCode()));
        StringBuffer errorMsg = new StringBuffer();
        if (CollectionUtils.isNotEmpty(participatingSites)
                && studyRecruitmentStatus.isRecruiting()) {
            boolean recruiting = false;
            StudySiteAccrualStatusDTO latestDTO = null;
            List<StudySiteAccrualStatusDTO> participatingSitesOld = null;
            for (StudySiteAccrualStatusDTO studySiteAccrualStatus : participatingSites) {
                if (!ISOUtil.isIiNull(studySiteAccrualStatus.getStudySiteIi())
                        && !isIiExistInPA(IiConverter.convertToStudySiteIi(Long.valueOf(studySiteAccrualStatus
                            .getStudySiteIi().getExtension())))) {
                    errorMsg.append("Study Site Id " + studySiteAccrualStatus.getStudySiteIi().getExtension()
                            + " does not exit");
                }
                Long latestId = IiConverter.convertToLong(studySiteAccrualStatus.getIdentifier());
                RecruitmentStatusCode recruimentStatus =
                    RecruitmentStatusCode.getByCode(studySiteAccrualStatus.getStatusCode().getCode());
                // base condition if one of the newly changed status is recruiting ;then break
                if (latestId == null && recruimentStatus.isRecruiting()) {
                    recruiting = true;
                    break;
                } else {
                    participatingSitesOld = new ArrayList<StudySiteAccrualStatusDTO>();
                    participatingSitesOld.add(studySiteAccrualStatus);
                }
            }
            if (CollectionUtils.isNotEmpty(participatingSitesOld)) {
                // else sort the old statuses and the get the latest
                Collections.sort(participatingSitesOld, new Comparator<StudySiteAccrualStatusDTO>() {
                    @Override
                    public int compare(StudySiteAccrualStatusDTO o1, StudySiteAccrualStatusDTO o2) {
                        int result = o1.getStatusDate().getValue().compareTo(o2.getStatusDate().getValue());
                        if (result == 0) {
                            result = o1.getIdentifier().getExtension()
                                    .compareToIgnoreCase(o2.getIdentifier().getExtension());
                        }
                        return result;
                    }
                });
                latestDTO = participatingSitesOld.get(participatingSitesOld.size() - 1);
                RecruitmentStatusCode recruimentStatus = latestDTO == null ? null
                        : RecruitmentStatusCode.getByCode(latestDTO.getStatusCode().getCode());
                if (recruimentStatus != null && recruimentStatus.isRecruiting()) {
                    recruiting = true;
                }
                if (!recruiting) {
                    throw new PAException("Data inconsistency: At least one location needs to be recruiting"
                            + " if the overall status recruitment status is\'Recruiting\'." + ERR_MSG_SEPARATOR);
                }
            }
        }
    }
    /**
     * Enforce regulatory info.
     *
     * @param studyProtocolDTO the study protocol dto
     * @param studyRegAuthDTO the study reg auth dto
     * @param studyIndldeDTOs the study indlde dt os
     * @param regulatoryInfoBean the regulatory info bean
     * @throws PAException the PA exception
     */
    public void enforceRegulatoryInfo(StudyProtocolDTO studyProtocolDTO,
                                      StudyRegulatoryAuthorityDTO studyRegAuthDTO ,
                                      List<StudyIndldeDTO> studyIndldeDTOs,
                                      RegulatoryInformationServiceLocal regulatoryInfoBean) throws PAException {
        StringBuffer errMsg = new StringBuffer();
        if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            if (studyRegAuthDTO == null) {
                 errMsg.append("Regulatory Information fields must be Entered.\n");
            }
            if (ISOUtil.isBlNull(studyProtocolDTO.getFdaRegulatedIndicator())) {
                 errMsg.append("FDA Regulated Intervention Indicator is required field.\n");
            }
            if (CommonsConstant.YES.equalsIgnoreCase(
                BlConverter.convertBlToYesNoString(studyProtocolDTO.getFdaRegulatedIndicator()))
                && ISOUtil.isBlNull(studyProtocolDTO.getSection801Indicator())) {
                 errMsg.append("Section 801 is required if FDA Regulated indicator is true." + ERR_MSG_SEPARATOR);
            }
            if (CommonsConstant.YES.equalsIgnoreCase(
                 BlConverter.convertBlToYesNoString(studyProtocolDTO.getSection801Indicator()))
                 && ISOUtil.isBlNull(studyProtocolDTO.getDelayedpostingIndicator())) {
                   errMsg.append("Delayed Posting Indicator is required if Section 801 is true." + ERR_MSG_SEPARATOR);
            }

            if (containsNonExemptInds(studyIndldeDTOs)) {
                     if (CommonsConstant.NO.equalsIgnoreCase(BlConverter.convertBlToYesNoString(
                        studyProtocolDTO.getFdaRegulatedIndicator()))) {
                         errMsg.append("FDA Regulated Intervention Indicator must be Yes "
                      +                       " since it has Trial IND/IDE records.\n");
                     }
                     if (studyRegAuthDTO != null && !ISOUtil.isIiNull(
                              studyRegAuthDTO.getRegulatoryAuthorityIdentifier())) {
                          Long sraId = Long.valueOf(studyRegAuthDTO.getRegulatoryAuthorityIdentifier().getExtension());
                          //doing this just to load the country since its lazy loaded.
                          Country country = regulatoryInfoBean.getRegulatoryAuthorityCountry(sraId);
                          String regAuthName = regulatoryInfoBean.getCountryOrOrgName(sraId, "RegulatoryAuthority");
                          if (!(country.getAlpha3().equals("USA")
                                  && regAuthName.equalsIgnoreCase("Food and Drug Administration"))) {
                           errMsg.append("For IND protocols, Oversight Authorities "
                              + " must include United States: Food and Drug Administration.\n");
                          }
                  }
          }
        }
        if (errMsg.length() > 1) {
            throw new PAException(errMsg.toString());
        }
    }
    /**
     * @param studyIndldeDTOs ind
     * @return true if least one non-exempt IND/IDE exists
     */
    public boolean containsNonExemptInds(List<StudyIndldeDTO> studyIndldeDTOs) {
        boolean isNonExemptInds = false;
        if (CollectionUtils.isNotEmpty(studyIndldeDTOs)) {
            for (StudyIndldeDTO dto : studyIndldeDTOs) {
                if (BooleanUtils.isFalse(BlConverter.convertToBoolean(dto.getExemptIndicator()))) {
                    isNonExemptInds = true;
                    break;
                }
            }
        }
        return isNonExemptInds;
    }
    /**
     *
     * @param studyProtocolIi Ii
     * @param msc milestonCode
     * @param commentText text
     * @param rejectionReasonCode rejectionReasonCode
     * @throws PAException e
     */

    public void createMilestone(Ii studyProtocolIi, MilestoneCode msc, St commentText, Cd rejectionReasonCode)
            throws PAException {
        StudyMilestoneDTO smDto = new StudyMilestoneDTO();
        smDto.setMilestoneDate(TsConverter.convertToTs(new Timestamp((new Date()).getTime())));
        smDto.setStudyProtocolIdentifier(studyProtocolIi);
        smDto.setMilestoneCode(CdConverter.convertToCd(msc));
        smDto.setRejectionReasonCode(rejectionReasonCode);
        smDto.setCommentText(commentText);
        StudyPaService<StudyMilestoneDTO> spService = getRemoteService(IiConverter.convertToStudyMilestoneIi(null));
        spService.create(smDto);
    }
    /**
     *
     * @param documentDTOs listOf doc
     * @param docTypeCode type code
     * @return s
     */
    public boolean isDocumentInList(List<DocumentDTO> documentDTOs, DocumentTypeCode docTypeCode) {
        boolean retValue = false;
        if (CollectionUtils.isNotEmpty(documentDTOs)) {
            for (DocumentDTO doc : documentDTOs) {
                if (docTypeCode.getCode().equals(CdConverter.convertCdToString(doc.getTypeCode()))) {
                    retValue = true;
                }
            }
        }
        return retValue;
    }
    /**
     * Check that all documents coming in are of a valid field type.
     * @param documentDTOs doc list
     * @return list of errors.
     */
    public String checkDocumentListForValidFileTypes(List<DocumentDTO> documentDTOs) {
        StringBuilder sBuilder = new StringBuilder();
        if (CollectionUtils.isNotEmpty(documentDTOs)) {
            for (DocumentDTO doc : documentDTOs) {
                String filename = StConverter.convertToString(doc.getFileName());
                DocumentTypeCode docType = DocumentTypeCode.getByCode(CdConverter.convertCdToString(doc.getTypeCode()));
                if (docType == DocumentTypeCode.TSR && !isValidFileType(filename, "doc,pdf,html,rtf")) {
                    sBuilder.append("TSR Document ").append(filename).append(" has an invalid file type.\n");
                } else if (docType != DocumentTypeCode.OTHER && docType != DocumentTypeCode.TSR
                        && !isValidFileType(filename)) {
                    sBuilder.append("Document ").append(filename).append(" has an invalid file type.\n");
                }
            }
        }
        return sBuilder.toString();
    }
    /**
     * check if the uploaded file type is valid.
     * @param fileName filename
     * @return boolean validation
     */
    public boolean isValidFileType(String fileName) {
        return isValidFileType(fileName, null);
    }
    /**
     * check if the uploaded file type is valid.
     * @param fileName filename
     * @param allowedFileTypes user requested types.
     * @return boolean validation
     */
    public boolean isValidFileType(String fileName, String allowedFileTypes) {
        boolean isValidFileType = false;
        String allowedUploadFileTypes = null;
        if (StringUtils.isBlank(allowedFileTypes)) {
            try {
                allowedUploadFileTypes = PaRegistry
                    .getLookUpTableService().getPropertyValue("allowed.uploadfile.types");
            } catch (PAException e) {
                LOG.error("Document file types could not be validated because there was an error "
                        + "retrieving allowed types from the database", e);
            }
        } else {
            allowedUploadFileTypes = allowedFileTypes;
        }

        if (allowedUploadFileTypes != null) {
            int pos = fileName.lastIndexOf('.');
            String uploadedFileType = fileName.substring(pos + 1, fileName.length());
            StringTokenizer st = new StringTokenizer(allowedUploadFileTypes, ",");
            while (st.hasMoreTokens()) {
                String allowedFileType = st.nextToken();
                if (allowedFileType.equalsIgnoreCase(uploadedFileType)) {
                    isValidFileType = true;
                    break;
                }
            }
        }
        return isValidFileType;
    }
    /**
     *
     * @param poIi po  Ii
     * @return true
     */
    public boolean isIiExistInPO(Ii poIi) {
        boolean retValue = false;
        if (ISOUtil.isIiNull(poIi)) {
            retValue = false;
            return retValue;
        }
        if (IiConverter.ORG_IDENTIFIER_NAME.equals(poIi.getIdentifierName())
                || IiConverter.ORG_ROOT.equalsIgnoreCase(poIi.getRoot())) {
            OrganizationDTO poOrg = getPOOrganizationEntity(IiConverter.convertToPoOrganizationIi(poIi.getExtension()));
            retValue = poOrg != null;
        }
        if (IiConverter.PERSON_IDENTIFIER_NAME.equalsIgnoreCase(poIi.getIdentifierName())
                || IiConverter.PERSON_ROOT.equalsIgnoreCase(poIi.getRoot())) {
            PersonDTO poPerson = getPoPersonEntity(IiConverter.convertToPoPersonIi(poIi.getExtension()));
            retValue = poPerson != null;
        }
        if (IiConverter.ORGANIZATIONAL_CONTACT_ROOT.equalsIgnoreCase(poIi.getRoot())) {
            try {
                OrganizationalContactDTO  contactDto = PoRegistry.
                    getOrganizationalContactCorrelationService().getCorrelation(poIi);
                if (contactDto != null) {
                    retValue = true;
                }
            } catch (NullifiedRoleException e) {
                retValue = false;
            }
        }
        return retValue;
    }
    /**
     *
     * @param paIi Ii
     * @return s
     */
    public boolean isIiExistInPA(Ii paIi) {
        boolean retValue = false;
        try {
            StudyPaService<StudyDTO> paService = getRemoteService(paIi);
            StudyDTO dto = paService.get(paIi);
            if (dto != null) {
                retValue = true;
            }
        } catch (PAException e) {
            retValue = false;
        }
     return retValue;
    }
    /**
     *
     * @param poDTO poDTO
     * @return s
     */
    public String isDTOValidInPO(PoDto poDTO) {
        String retValue = "";
        if (poDTO == null) {
            return "";
        }

        if (poDTO instanceof OrganizationDTO) {
            Map <String, String[]> errMap = PoRegistry.getOrganizationEntityService().validate(
                    (OrganizationDTO) poDTO);
            retValue = PAUtil.getErrorMsg(errMap);
        }
        if (poDTO instanceof PersonDTO) {
            Map <String, String[]> errMap = PoRegistry.getPersonEntityService().validate((PersonDTO) poDTO);
            retValue = PAUtil.getErrorMsg(errMap);
        }
        return retValue;
    }
    /**
     *
     * @param listOfObject to create
     * @throws PAException e
     *
     */
    public void createPoObject(List<? extends PoDto> listOfObject) throws PAException {
      for (PoDto poDto : listOfObject) {
         try {
             if (poDto instanceof OrganizationDTO && ISOUtil.isIiNull(((OrganizationDTO) poDto).getIdentifier())) {
                     PoRegistry.getOrganizationEntityService().createOrganization((OrganizationDTO) poDto);
                 }
             if (poDto instanceof PersonDTO && ISOUtil.isIiNull(((PersonDTO) poDto).getIdentifier())) {
                 PoRegistry.getPersonEntityService().createPerson((PersonDTO) poDto);
             }
         } catch (Exception e) {
            throw new PAException(e);
         }
      }
    }

    /**
     *
     * @param <Entity> Person or Organization class
     * @param entity Person or Organization class
     * @return PoDto Person or Organization class
     * @throws PAException on error
     *
     */
    public <Entity extends PoDto> Entity createEntity(Entity entity) throws PAException {
        Ii entityIi = null;
        try {
            if (entity instanceof OrganizationDTO) {
                entityIi = PoRegistry.getOrganizationEntityService().createOrganization((OrganizationDTO) entity);
                return (Entity) PoRegistry.getOrganizationEntityService().getOrganization(entityIi);
            } else if (entity instanceof PersonDTO) {
                entityIi = PoRegistry.getPersonEntityService().createPerson((PersonDTO) entity);
                return (Entity) PoRegistry.getPersonEntityService().getPerson(entityIi);
            } else {
                throw new PAException("createEntity Should be called only for Entity/Person");
            }
        } catch (Exception e) {
            throw new PAException(e);
        }

    }

    /**
     * @param <Entity> Person or Organization
     * @param entityIi Organization of Person Ii
     * @return PoDto Organization of Person entity
     * @throws PAException e
     *
     */
    public <Entity extends PoDto> Entity getEntityByIi(Ii entityIi) throws PAException {
        Entity retEntity = null;
        try {
            if (IiConverter.ORG_ROOT.equals(entityIi.getRoot())) {
                retEntity = (Entity) PoRegistry.getOrganizationEntityService().getOrganization(entityIi);
            } else if (IiConverter.PERSON_ROOT.equals(entityIi.getRoot())) {
                retEntity = (Entity) PoRegistry.getPersonEntityService().getPerson(entityIi);
            } else {
                throw new PAException("getEntityByIi Should be called only for Entity/Person");
            }

        } catch (Exception e) {
            throw new PAException(e);
        }
        return retEntity;
    }
    /**
     *
     * @param <Entity> Person or Organization
     * @param entity Person or Organization
     * @return PoDto
     * @throws PAException on error
     */
    public <Entity extends PoDto> Entity findOrCreateEntity(Entity entity) throws PAException {
        if (entity == null) {
            throw new PAException("Entity cannot be null");
        }
        Entity retEntity = findEntity(entity);
        if (retEntity != null) {
            return retEntity;
        }
        String validMsg = isDTOValidInPO(entity);
        if (validMsg.length() > 0) {
            throw new PAException(validMsg);
        }

        return entity;
    }
    /**
     * Search the Person or Organization Entity.
     * @param <Entity> Person or Organization
     * @param entity Person or Organization
     * @return Person or Organization
     * @throws PAException on error
     */
    public <Entity extends PoDto> Entity findEntity(Entity entity) throws PAException {
        if (entity instanceof EntityDto && !ISOUtil.isIiNull(((EntityDto) entity).getIdentifier())) {
            return (Entity) getEntityByIi(((EntityDto) entity).getIdentifier());
        }
        return search(entity);
    }

    private <Entity extends PoDto> Entity search(Entity entity) throws PAException {
        LimitOffset limit = new LimitOffset(PAAttributeMaxLen.LEN_2, 0);
        List<? extends Entity> entities = null;
        Entity retEntity = null;
        try {
            if (entity instanceof OrganizationDTO) {
                entities = (List<Entity>) PoRegistry.getOrganizationEntityService().search((OrganizationDTO) entity,
                                                                                           limit);
            } else if (entity instanceof PersonDTO) {
                entities = (List<Entity>) PoRegistry.getPersonEntityService().search((PersonDTO) entity, limit);
            }
            if (!entities.isEmpty()) {
                retEntity = entities.get(0);
            }
        } catch (Exception e) {
            throw new PAException(e);
        }
        return retEntity;
    }
    /**
     * @param studySiteAccrualStatusDTO accrualdto
     * @param studySiteDTO site dto
     * @return errorMsg
     */
      public String validateRecuritmentStatusDateRule(StudySiteAccrualStatusDTO studySiteAccrualStatusDTO,
            StudySiteDTO studySiteDTO) {
        StringBuffer errorMsg = new StringBuffer();
        if (studySiteAccrualStatusDTO != null) {
            errorMsg.append(ISOUtil.isCdNull(studySiteAccrualStatusDTO.getStatusCode())
                    ? "Site recruitment Status Code cannot be null. " : "");
            errorMsg.append(ISOUtil.isTsNull(studySiteAccrualStatusDTO.getStatusDate())
                    ? "Site recruitment Status Date should be a valid date. " : "");
            if (!ISOUtil.isCdNull(studySiteAccrualStatusDTO.getStatusCode())
                    && null == RecruitmentStatusCode.getByCode(studySiteAccrualStatusDTO.getStatusCode().getCode())) {
                errorMsg.append("Please enter valid RecruitmentStatusCode. ");
            }
            if (!ISOUtil.isTsNull(studySiteAccrualStatusDTO.getStatusDate())) {
            errorMsg.append(PAUtil.isDateCurrentOrPast(TsConverter.convertToTimestamp(
                    studySiteAccrualStatusDTO.getStatusDate()))
                    ? "Site recruitment Status Date cannot be in the future. " : "");
            }
        }
        if (studySiteAccrualStatusDTO != null && studySiteDTO != null) {
              Timestamp dateOpenedForAccrual = IvlConverter.convertTs().convertLow(studySiteDTO.getAccrualDateRange());
              Timestamp dateClosedForAccrual = IvlConverter.convertTs().convertHigh(studySiteDTO.getAccrualDateRange());
              if (dateOpenedForAccrual != null) {
                  errorMsg.append(PAUtil.isDateCurrentOrPast(dateOpenedForAccrual)
                  ? "Date Opened for Accrual cannot be in the future. " : "");
              }
              if (dateClosedForAccrual != null) {
                  errorMsg.append(PAUtil.isDateCurrentOrPast(dateClosedForAccrual)
                          ? "Date Closed For Accrual cannot be in the future. " : "");
              }
              if (dateClosedForAccrual != null && dateOpenedForAccrual == null) {
                  errorMsg.append("Opened for Accrual Date is  mandatory if Closed for Accrual Date is provided. ");
              }
              if (dateClosedForAccrual != null  && dateOpenedForAccrual != null
                      && dateClosedForAccrual.before(dateOpenedForAccrual)) {
                 errorMsg.append("Date Closed for Accrual must be same or bigger "
                         + " than Date Opened for Accrual. ");
              }
              if (!ISOUtil.isCdNull(studySiteAccrualStatusDTO.getStatusCode())) {
                  RecruitmentStatusCode recruitmentStatus =
                      RecruitmentStatusCode.getByCode(studySiteAccrualStatusDTO.getStatusCode().getCode());
                  if (recruitmentStatus != null) {
                      String recStatus = CdConverter.convertCdToString(studySiteAccrualStatusDTO.getStatusCode());
                      if (recruitmentStatus.isNonRecruiting()) {
                          if (dateOpenedForAccrual != null) {
                            errorMsg.append(
                                    "Date Opened for Accrual must be null for ")
                                    .append(recStatus).append(". ");
                          }
                      } else if (dateOpenedForAccrual == null) {
                        errorMsg.append(
                                "Date Opened for Accrual must be a valid date for ")
                                .append(recStatus).append(". ");
                      }
                      if ((RecruitmentStatusCode.ADMINISTRATIVELY_COMPLETE.getCode().equalsIgnoreCase(recStatus)
                              || RecruitmentStatusCode.COMPLETED.getCode().equalsIgnoreCase(recStatus))
                              && dateClosedForAccrual == null) {
                        errorMsg.append(
                                "Date Closed for Accrual must be a valid date for ")
                                .append(recStatus).append(". ");
                      }
                 }
              }

          }
        return errorMsg.toString();
      }
    /**
     *
     * @param <TYPE> type
     * @param correlationIi ii
     * @return service
     * @throws PAException e
     */
    public <TYPE extends CorrelationService> TYPE getPoService(Ii correlationIi) throws PAException {
        if (IiConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME.equals(correlationIi.getIdentifierName())) {
            return (TYPE) PoRegistry.getHealthCareFacilityCorrelationService();
        }
        if (IiConverter.RESEARCH_ORG_IDENTIFIER_NAME.equals(correlationIi.getIdentifierName())) {
            return (TYPE) PoRegistry.getResearchOrganizationCorrelationService();
        }
        if (IiConverter.OVERSIGHT_COMMITTEE_IDENTIFIER_NAME.equals(correlationIi.getIdentifierName())) {
            return (TYPE) PoRegistry.getOversightCommitteeCorrelationService();
        }
        if (IiConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME.equals(correlationIi.getIdentifierName())) {
            return (TYPE) PoRegistry.getClinicalResearchStaffCorrelationService();
        }
        if (IiConverter.HEALTH_CARE_PROVIDER_IDENTIFIER_NAME.equals(correlationIi.getIdentifierName())) {
            return (TYPE) PoRegistry.getHealthCareProviderCorrelationService();
        }
        if (IiConverter.ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME.equals(correlationIi.getIdentifierName())) {
            return (TYPE) PoRegistry.getOrganizationalContactCorrelationService();
        }
        throw new PAException(" Unknown identifier for " + correlationIi.getIdentifierName());
    }
    /**
     * @param <T> any class extends {@link StructuralRole}
     * @param isoIi iso identitifier
     * @return StucturalRole class for an correspondong iso ii
     * @throws PAException on error
     */
    public <T extends StructuralRole> T getStructuralRole(Ii isoIi) throws PAException {
        StringBuffer hql = new StringBuffer("select role from ");
        if (IiConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            hql.append("HealthCareFacility role where role.id = '" + isoIi.getExtension() + "'");
        } else if (IiConverter.RESEARCH_ORG_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            hql.append("ResearchOrganization role where role.id = '" + isoIi.getExtension() + "'");
        } else if (IiConverter.OVERSIGHT_COMMITTEE_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            hql.append("OversightCommittee role where role.id = '" + isoIi.getExtension() + "'");
        } else if (IiConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            hql.append("ClinicalResearchStaff role where role.id = '" + isoIi.getExtension() + "'");
        } else if (IiConverter.HEALTH_CARE_PROVIDER_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            hql.append("HealthCareProvider role where role.id = '" + isoIi.getExtension() + "'");
        } else if (IiConverter.ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
            hql.append("OrganizationalContact role where role.id  = '" + isoIi.getExtension() + "'");
        } else {
            throw new PAException(" unknown identifier name provided  : " + isoIi.getIdentifierName());
        }
        List<T> queryList = PaHibernateUtil.getCurrentSession().createQuery(hql.toString()).list();
        T sr = null;

        if (!queryList.isEmpty()) {
            sr = queryList.get(0);
        }

        return sr;
    }
    
    /**
     * @param poIdentifier poIdentifier
     * @return OrganizationalContact
     * @throws PAException PAException
     */
    public OrganizationalContact getOrganizationalContact(String poIdentifier) throws PAException {

        Session session = null;

        List<OrganizationalContact> queryList = 

                new ArrayList<OrganizationalContact>();

        session = PaHibernateUtil.getCurrentSession();

        Query query = null;

        // step 1: form the hql

        String hql = "select alias "

                   + "from OrganizationalContact alias "

                   + "where alias.identifier = :identifier ";



        // step 2: construct query object

        query = session.createQuery(hql);

        query.setParameter("identifier", poIdentifier);

        

        OrganizationalContact oc = null;

        // step 3: query the result

        queryList = query.list();

        if (queryList.size() > 1) {

            throw new PAException(" More than one Structural role found in OrganizationalContact"

                    + " for identifier " + poIdentifier);

        } else if (!queryList.isEmpty()) {

            oc = queryList.get(0);

        }

        return oc;

    }


    
    /**
     *
     * @param correlationIi Ii
     * @return poDto
     * @throws PAException e
     */
    public PoDto getCorrelationByIi(Ii correlationIi) throws PAException {
        PoDto poCorrelationDto = null;
        CorrelationService corrService = getPoService(correlationIi);
        try {
            poCorrelationDto = corrService.getCorrelation(correlationIi);
        } catch (NullifiedRoleException e1) {
            Ii nullfiedIi = null;
            Map<Ii, Ii> nullifiedEntities = e1.getNullifiedEntities();
            for (Ii tmp : nullifiedEntities.keySet()) {
                if (tmp.getExtension().equals(correlationIi.getExtension())) {
                    nullfiedIi = tmp;
                }
            }
            Ii dupCorrelationIi = null;
            if (nullfiedIi != null) {
                dupCorrelationIi = nullifiedEntities.get(nullfiedIi);
            }
            if (!ISOUtil.isIiNull(dupCorrelationIi)) {
                try {
                    poCorrelationDto = corrService.getCorrelation(dupCorrelationIi);
                } catch (NullifiedRoleException e2) {
                    throw new PAException("This scenario is currently not handled .... "
                            + "Duplicate Ii of nullified is also nullified", e2);
                }
            }
        }
        return poCorrelationDto;
    }
    /**
     *
     * @param isoIi iso Identifier
     * @return Organization
     * @throws PAException on error
     */
    public Organization getOrCreatePAOrganizationByIi(Ii isoIi) throws PAException {
        Organization org = null;
        CorrelationUtils cUtils = new CorrelationUtils();
        org = cUtils.getPAOrganizationByIi(isoIi);
        if (org == null) {
            OrganizationDTO poOrg = null;
            try {
                poOrg = PoRegistry.getOrganizationEntityService().getOrganization(isoIi);
            } catch (NullifiedEntityException e) {
                throw new PAException(PAUtil.handleNullifiedEntityException(e));
            }
            org = cUtils.createPAOrganization(poOrg);
        }
        return org;
    }
    /**
    *
    * @param isoIi iso Identifier
    * @return Person
    * @throws PAException on error
    */
   public Person getOrCreatePAPersonByPoIi(Ii isoIi) throws PAException {
       CorrelationUtils cUtils = new CorrelationUtils();
       Person per = cUtils.getPAPersonByIi(isoIi);
       if (per == null) {
           PersonDTO poPer = null;
           try {
               poPer = PoRegistry.getPersonEntityService().getPerson(isoIi);
           } catch (NullifiedEntityException e) {
               throw new PAException(PAUtil.handleNullifiedEntityException(e));
           }
           if (poPer != null) {
               per = cUtils.createPAPerson(poPer);
           } else {
               throw new PAException("PO ii: " + isoIi.getExtension() + " cannot be found");
           }
       }
       return per;
   }
    /**
     *
     * @param <T> type
     * @param isoIi ii
     * @return sr
     * @throws PAException e
     */
    public <T extends OrganizationalStructuralRole> T getOrCreateOrganizationalStructuralRoleInPA(Ii isoIi)
            throws PAException {
        CorrelationUtils cUtils = new CorrelationUtils();
        CorrelationDto poDto = (CorrelationDto) getCorrelationByIi(isoIi);
        // Step 1 : Check of PA has structural role , if not create one
        gov.nih.nci.pa.util.CorrelationUtils commonsCorrUtils = new gov.nih.nci.pa.util.CorrelationUtils();
        OrganizationalStructuralRole dupSR = commonsCorrUtils.getStructuralRoleByIi(DSetConverter.convertToIi(poDto
            .getIdentifier()));
        if (dupSR == null) {
            // create a new structural role
            if (IiConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
                dupSR = new HealthCareFacility();
            } else if (IiConverter.RESEARCH_ORG_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
                dupSR = new ResearchOrganization();
            } else if (IiConverter.OVERSIGHT_COMMITTEE_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {
                dupSR = new OversightCommittee();
            }
            dupSR.setOrganization(getOrCreatePAOrganizationByIi(((AbstractEnhancedOrganizationRoleDTO) poDto)
                                                                .getPlayerIdentifier()));
            dupSR.setIdentifier(DSetConverter.convertToIi(poDto.getIdentifier()).getExtension());
            dupSR.setStatusCode(cUtils.convertPORoleStatusToPARoleStatus(poDto.getStatus()));
            return (T) cUtils.createPADomain(dupSR);
        }
        return (T) dupSR;
    }
    /**
     * @param nullifiedIi ii
     * @return ii
     * @throws PAException e
     */
    public Ii getDuplicateIiOfNullifiedSR(Ii nullifiedIi) throws PAException {
        Ii dupSRIi = null;
        try {
            CorrelationService<PoDto> correlationService = getPoService(nullifiedIi);
            correlationService.getCorrelation(nullifiedIi);
        } catch (NullifiedRoleException e) {
            // SR is nullified, find out if it has any duplicates
            Ii nullfiedIi = null;
            Map<Ii, Ii> nullifiedEntities = e.getNullifiedEntities();
            for (Ii tmp : nullifiedEntities.keySet()) {
                if (tmp.getExtension().equals(nullifiedIi.getExtension())) {
                    nullfiedIi = tmp;
                }
            }
            if (nullfiedIi != null) {
                // this scenario is sr nullification with duplicate
                dupSRIi = nullifiedEntities.get(nullfiedIi);
            }
        }
        return dupSRIi;
    }
    /**
     *
     * @param <T> type
     * @param poIi poIi
     * @param srDTO srdto
     * @return srdto
     * @throws PAException e
     */
    public <T extends OrganizationalStructuralRole> T updateScoper(Ii poIi, T srDTO) throws PAException {
        String poOrgIi = poIi.getExtension();
        String paOrgAssignedId = srDTO.getOrganization().getIdentifier();
        if (StringUtils.isNotEmpty(poOrgIi) && StringUtils.isNotEmpty(paOrgAssignedId)
                && !poOrgIi.equalsIgnoreCase(paOrgAssignedId)) {
            // this means scoper is changed. check if exist in PA if not create and update the SR
            Organization paOrg = getOrCreatePAOrganizationByIi(poIi);
            srDTO.setOrganization(paOrg);
        }
        return srDTO;
    }
    /**
     * @param studyProtocolIi ii
     * @param identifierType type
     * @return str
     * @throws PAException e
     */
    public String getStudyIdentifier(Ii studyProtocolIi, String identifierType) throws PAException {
        String retIdentifier = "";
        StudySiteDTO identifierDto = new StudySiteDTO();
        identifierDto.setStudyProtocolIdentifier(studyProtocolIi);
        if (identifierType.equalsIgnoreCase(PAConstants.LEAD_IDENTIFER_TYPE)) {
            identifierDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        } else {
            identifierDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
            String poOrgId = PaRegistry.getOrganizationCorrelationService()
                                       .getPOOrgIdentifierByIdentifierType(identifierType);
            final Ii poOrgIi = IiConverter.convertToPoOrganizationIi(poOrgId);
            identifierDto.setResearchOrganizationIi(PaRegistry.getOrganizationCorrelationService()
                                                              .getPoResearchOrganizationByEntityIdentifier(poOrgIi));
        }
        StudySiteDTO spDto = PAUtil.getFirstObj(getStudySite(identifierDto, true));
        if (spDto != null && !ISOUtil.isStNull(spDto.getLocalStudyProtocolIdentifier())) {
            retIdentifier = StConverter.convertToString(spDto.getLocalStudyProtocolIdentifier());
        }
        return retIdentifier;
    }
      /**
       * Create or update of Identifiers.
       * @param identifierDTO study site identifier
       * @throws PAException on error
       */
    public void manageStudyIdentifiers(StudySiteDTO identifierDTO) throws PAException {
        boolean isNCT = false;
        if (identifierDTO == null) {
            throw new PAException("Identifier DTO cannot be null");
        }
        if (ISOUtil.isIiNull(identifierDTO.getResearchOrganizationIi())) {
            throw new PAException("Research Org Identifier cannot be null");
        }
        if (ISOUtil.isIiNull(identifierDTO.getStudyProtocolIdentifier())) {
            throw new PAException("Study Protocol Identifier cannot be null");
        }
        if (ISOUtil.isCdNull(identifierDTO.getFunctionalCode())) {
            throw new PAException("Functional Code cannot be null");
        }
        if (!ISOUtil.isIiNull(identifierDTO.getResearchOrganizationIi())) {
            gov.nih.nci.pa.util.CorrelationUtils commonsCorrUtils = new gov.nih.nci.pa.util.CorrelationUtils();
            StructuralRole srRO = commonsCorrUtils.getStructuralRoleByIi(identifierDTO.getResearchOrganizationIi());
            if (srRO == null) {
                final String roIdExt = identifierDTO.getResearchOrganizationIi().getExtension();
                ResearchOrganizationDTO poSrDto = (ResearchOrganizationDTO) getCorrelationByIi(IiConverter
                    .convertToPoResearchOrganizationIi(roIdExt));
                if (poSrDto == null) {
                    throw new PAException("Structral Role is not found in PO: "
                            + roIdExt);
                }
                Long roId = PaRegistry.getOrganizationCorrelationService()
                    .createResearchOrganizationCorrelations(poSrDto.getPlayerIdentifier().getExtension());
                srRO = getStructuralRole(IiConverter.convertToPoResearchOrganizationIi(String.valueOf(roId)));
            } else {
                if (srRO instanceof ResearchOrganization
                        && StringUtils.equals(PAConstants.CTGOV_ORG_NAME,
                                ((ResearchOrganization) srRO).getOrganization()
                                        .getName())
                        && StringUtils.equals(
                                StudySiteFunctionalCode.IDENTIFIER_ASSIGNER
                                        .getCode(), identifierDTO
                                        .getFunctionalCode().getCode())) {
                    isNCT = true;
                }
            }
            // here only using pa ro id but expecting always po ro id
            identifierDTO.setResearchOrganizationIi(IiConverter.convertToIi(srRO.getId()));
        }
        // check if any record is there in db
        LimitOffset pagingParams = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<StudySiteDTO> spDtos;
        try {
            StudySiteDTO criteriaDTO = new StudySiteDTO();
            criteriaDTO.setFunctionalCode(identifierDTO.getFunctionalCode());
            criteriaDTO.setStudyProtocolIdentifier(identifierDTO.getStudyProtocolIdentifier());
            if (identifierDTO.getFunctionalCode().getCode()
                .equalsIgnoreCase(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER.getCode())) {
                criteriaDTO.setResearchOrganizationIi(identifierDTO.getResearchOrganizationIi());
            }
            spDtos = PaRegistry.getStudySiteService().search(criteriaDTO, pagingParams);
        } catch (TooManyResultsException e) {
            throw new PAException(String.format(ERR_MSG, identifierDTO.getStudyProtocolIdentifier().getExtension(),
                                                identifierDTO.getFunctionalCode().getCode()), e);
        }
        StudySiteDTO studySiteDB = PAUtil.getFirstObj(spDtos);
        if (studySiteDB != null && !ISOUtil.isIiNull(studySiteDB.getIdentifier())) {
            identifierDTO.setIdentifier(studySiteDB.getIdentifier());
        }
        List<StudySiteDTO> newSiteDTOS = new ArrayList<StudySiteDTO>();
        newSiteDTOS.add(identifierDTO);
        
        String existingNCT = getStudyIdentifier(
                identifierDTO.getStudyProtocolIdentifier(),
                PAConstants.NCT_IDENTIFIER_TYPE);
        
        createOrUpdate(newSiteDTOS, IiConverter.convertToStudySiteIi(null), identifierDTO.getStudyProtocolIdentifier());
        
        String newNCT = StConverter.convertToString(identifierDTO.getLocalStudyProtocolIdentifier());
        if (isNCT
                && StringUtils.isNotEmpty(getStudyIdentifier(
                        identifierDTO.getStudyProtocolIdentifier(),
                        PAConstants.DCP_IDENTIFIER_TYPE))
                && !StringUtils.equals(existingNCT, newNCT)) {

            PaRegistry.getMailManagerService().sendNCTIDChangeNotificationMail(
                    identifierDTO.getStudyProtocolIdentifier(), newNCT,
                    existingNCT);
        }
    }
    /**
     * Gets the country name of the entity (organization of person).
     * @param entityIi The entityIi
     * @return The country name.
     */
    public String getEntityCountryName(Ii entityIi) {
        String countryName = null;
        if (!ISOUtil.isIiNull(entityIi)) {
            if (IiConverter.ORG_IDENTIFIER_NAME.equals(entityIi.getIdentifierName())) {
                OrganizationDTO orgDTO = getPOOrganizationEntity(entityIi);
                countryName = PAUtil.getCountryName(orgDTO.getPostalAddress());
            }
            if (IiConverter.PERSON_IDENTIFIER_NAME.equals(entityIi.getIdentifierName())) {
                PersonDTO perDTO = getPoPersonEntity(entityIi);
                countryName = PAUtil.getCountryName(perDTO.getPostalAddress());
            }
        }
        return countryName;
    }
    
    /**
     * Validates and format the phonenumber for the given scoper and adresses.
     * @param scoperIi The scoper Ii
     * @param addresses The DSet<Tel> containing the phone number
     * @throws PAException If the phone number is not valid
     */
    public void validateAndFormatPhoneNumber(Ii scoperIi, DSet<Tel> addresses) throws PAException {
        String countryName = getEntityCountryName(scoperIi);
        String phoneNumber = DSetConverter.convertDSetToList(addresses, PAConstants.PHONE).get(0);
        List<String> formattedNumbers = new ArrayList<String>();
        try {
            formattedNumbers.add(PhoneUtil.formatPhoneNumber(countryName, phoneNumber));
        } catch (IllegalArgumentException e) {
            throw new PAException(e.getMessage(), e);
        }
        DSetConverter.replacePhones(addresses, formattedNumbers);
    }
    /**
     * @param entityIi Ii
     * @return poEntity
     */
    public OrganizationDTO getPOOrganizationEntity(Ii entityIi) {
        OrganizationDTO poOrg = null;
        try {
            poOrg = PoRegistry.getOrganizationEntityService()
                              .getOrganization(IiConverter.convertToPoOrganizationIi(entityIi.getExtension()));
        } catch (NullifiedEntityException e) {
            poOrg = null;
        }
        return poOrg;
    }

    /**
     * @param entityIi Ii
     * @return personDto
     */
    public PersonDTO getPoPersonEntity(Ii entityIi) {
        PersonDTO poPerson = null;
        try {
            poPerson = PoRegistry.getPersonEntityService()
                                 .getPerson(IiConverter.convertToPoPersonIi(entityIi.getExtension()));
        } catch (NullifiedEntityException e) {
            poPerson = null;
        }
        return poPerson;
    }

    /**
     * @param entityIi ii
     * @return name
     */
    public String getOrgName(Ii entityIi) {
        OrganizationDTO orgDto = getPOOrganizationEntity(entityIi);
        if (orgDto == null) {
            return "No Org Found";
        }
        return EnOnConverter.convertEnOnToString(orgDto.getName());
    }

    /**
     * @param poOrgId orgId
     * @return ii
     * @throws PAException on error
     */
    public Ii getDuplicateOrganizationIi(Ii poOrgId) throws PAException {
        try {
            PoRegistry.getOrganizationEntityService().getOrganization(poOrgId);
            // we get here if the org is NOT nullified
        } catch (NullifiedEntityException e) {
            // we get in here if the org WAS nullified.
            Map<Ii, Ii> nullifiedEntities = e.getNullifiedEntities();
            for (Map.Entry<Ii, Ii> entry : nullifiedEntities.entrySet()) {
                if (entry.getKey().getExtension().equals(poOrgId.getExtension())) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

      /**
       * Get Ii of duplicate person.
       * @param poPerId poPerId
       * @return ii
       * @throws PAException on error
       */
      public Ii getDuplicatePersonIi(Ii poPerId) throws PAException {
          try {
              PoRegistry.getPersonEntityService().getPerson(poPerId);
              // we get here if the org is NOT nullified
          } catch (NullifiedEntityException e) {
              // we get in here if the org WAS nullified.
              Map<Ii, Ii> nullifiedEntities = e.getNullifiedEntities();
              for (Map.Entry<Ii, Ii> entry : nullifiedEntities.entrySet()) {
                  if (entry.getKey().getExtension().equals(poPerId.getExtension())) {
                      return entry.getValue();
                  }
              }
          }
          return null;
      }
      /**Gets the Person by giving ctepId.
       * @param ctepId id
       * @return person
       */
      public PersonDTO getPersonByCtepId(String ctepId) {
          PersonDTO personDto = null;
          if (StringUtils.isNotEmpty(ctepId)) {
              personDto = new PersonDTO();
              IdentifiedPersonDTO identifiedPersonDTO = new IdentifiedPersonDTO();
              identifiedPersonDTO.setAssignedId(IiConverter.convertToIdentifiedPersonEntityIi(ctepId));
              List<IdentifiedPersonDTO> identifiedPersons;
              identifiedPersons = PoRegistry.getIdentifiedPersonEntityService().search(identifiedPersonDTO);
              if (CollectionUtils.isNotEmpty(identifiedPersons)) {
                  personDto.setIdentifier(identifiedPersons.get(0).getPlayerIdentifier());
              }
          }
          return personDto;
      }
      /**Gets the Organization by giving ctepId.
       * @param ctepId id
       * @return organization
       */
      public OrganizationDTO getOrganizationByCtepId(String ctepId) {
          OrganizationDTO orgDto = null;
          if (StringUtils.isNotEmpty(ctepId)) {
              IdentifiedOrganizationDTO identifiedOrgDto = new IdentifiedOrganizationDTO();
              identifiedOrgDto.setAssignedId(IiConverter.convertToIdentifiedOrgEntityIi(ctepId));
              List<IdentifiedOrganizationDTO> identifiedOrgs =
                  PoRegistry.getIdentifiedOrganizationEntityService().search(identifiedOrgDto);
              if (CollectionUtils.isNotEmpty(identifiedOrgs)) {
                  orgDto = new OrganizationDTO();
                  orgDto.setIdentifier(identifiedOrgs.get(0).getPlayerIdentifier());
              }
          }
          return orgDto;
      }
      /**
       * This method gives the HCF ii for given Org Ii.
       * @param poOrgId org ii
       * @return Hcf ii
       * @throws PAException on error
       */
      public Ii getPoHcfIi(String poOrgId) throws PAException {
          Ii poHcfIi = null;
          try {
              List<HealthCareFacilityDTO> poHcfList =
                   PoRegistry.getHealthCareFacilityCorrelationService()
                  .getCorrelationsByPlayerIds(new Ii[]{IiConverter.convertToPoOrganizationIi(poOrgId)});
              if (poHcfList.isEmpty()) {
                  OrganizationDTO orgDTO = PoRegistry.getOrganizationEntityService()
                      .getOrganization(IiConverter.convertToPoOrganizationIi(poOrgId));
                  HealthCareFacilityDTO hcfDTO = new HealthCareFacilityDTO();
                  hcfDTO.setPlayerIdentifier(orgDTO.getIdentifier());
                  poHcfIi = PoRegistry.getHealthCareFacilityCorrelationService().createCorrelation(hcfDTO);
              } else {
                  poHcfIi = DSetConverter.convertToIi(poHcfList.get(0).getIdentifier());
              }
          } catch (NullifiedRoleException e) {
              throw new PAException("The HCF for po Org id: " + poOrgId + " no longer exists.", e);
          } catch (EntityValidationException e) {
              throw new PAException("HCF could not pass validation for po Org with id: " + poOrgId, e);
          } catch (CurationException e) {
              throw new PAException("Could not curate HCF for po Org with id" + poOrgId, e);
          } catch (NullifiedEntityException e) {
              throw new PAException(PAUtil.handleNullifiedEntityException(e), e);
          }
          return poHcfIi;
      }
      /**
       * This method gives ClinicalResearchStaffDTO for given Person ii and org Ii.
       * @param investigatorIi personIi
       * @param poOrgId org Ii
       * @return ClinicalResearchStaffDTO
       * @throws PAException on error
       */
      public ClinicalResearchStaffDTO getCrsDTO(Ii investigatorIi, String poOrgId) throws PAException {
          ClinicalResearchStaffDTO crsDTO = null;
          List<ClinicalResearchStaffDTO> poCrsList;
          try {
            final ClinicalResearchStaffCorrelationServiceRemote service = PoRegistry
                    .getClinicalResearchStaffCorrelationService();
            poCrsList = filterByScoper(service
                    .getCorrelationsByPlayerIds(new Ii[] {investigatorIi}), poOrgId);
            if (poCrsList.isEmpty()) {
                crsDTO = new ClinicalResearchStaffDTO();
                crsDTO.setPlayerIdentifier(investigatorIi);
                crsDTO.setScoperIdentifier(IiConverter.convertToPoOrganizationIi(poOrgId));
                Ii newCrsIi = isAutoCurationEnabled() ? service
                        .createActiveCorrelation(crsDTO) : service
                        .createCorrelation(crsDTO);
                crsDTO = service.getCorrelation(newCrsIi);
            } else {
                crsDTO = poCrsList.get(0);
            }
          } catch (NullifiedRoleException e) {
              throw new PAException("The CRS for Person with id: " + investigatorIi + " no longer exists.", e);
          } catch (EntityValidationException e) {
              throw new PAException("CRS failed validation for Person id: " + investigatorIi, e);
          } catch (CurationException e) {
              throw new PAException("Could not curate CRS for Person id: " + investigatorIi, e);
          }
          return crsDTO;
      }
      /**
       * This method give the HealthCareProviderDTO for given Po OrgID.
       * @param trialType trialType
       * @param investigatorIi investigatorIi
       * @param poOrgId poOrg Id
       * @return  HealthCareProvider
       * @throws PAException on error
       */
      public HealthCareProviderDTO getHcpDTO(String trialType, Ii investigatorIi, String poOrgId) throws PAException {
          HealthCareProviderDTO hcpDTO = null;
          if (trialType.startsWith("Interventional")) {
              List<HealthCareProviderDTO> poHcpList;
              try {
                final HealthCareProviderCorrelationServiceRemote service = 
                        PoRegistry.getHealthCareProviderCorrelationService();
                poHcpList = filterByScoper(service
                          .getCorrelationsByPlayerIds(new Ii[]{investigatorIi}), poOrgId);
                  if (poHcpList.isEmpty()) {
                      hcpDTO = new HealthCareProviderDTO();
                      hcpDTO.setPlayerIdentifier(investigatorIi);
                      hcpDTO.setScoperIdentifier(IiConverter.convertToPoOrganizationIi(poOrgId));
                      Ii newHcpIi = isAutoCurationEnabled() ? service
                              .createActiveCorrelation(hcpDTO) : service
                              .createCorrelation(hcpDTO);                      
                      hcpDTO = service.getCorrelation(newHcpIi);
                  } else {
                      hcpDTO = poHcpList.get(0);
                  }
              } catch (NullifiedRoleException e) {
                  throw new PAException("The Person no longer exists.", e);
              } catch (EntityValidationException e) {
                  throw new PAException("HCP failed validation.", e);
              } catch (CurationException e) {
                  throw new PAException("Could not curate HCP", e);
              }
          }
          return hcpDTO;
      }
      private <T extends AbstractPersonRoleDTO> List<T> filterByScoper(List<T> correlationsByPlayerIds,
              String scoperId) {
          List<T> filteredList = new ArrayList<T>();
          for (T dto : correlationsByPlayerIds) {
              if (dto.getScoperIdentifier().getExtension().equals(scoperId)) {
                  filteredList.add(dto);
              }
          }
          return filteredList;
      }

    /**
     * Moves the documents into their proper directories if they have been previously saved in a temporary directory.
     * @param docs the documents to move if necessary
     * @param assignedIdentifier the assignedIdentifier of the study protocol these documents belong to
     * @throws PAException on error
     */
    public void moveDocumentContents(List<DocumentDTO> docs, String assignedIdentifier) throws PAException {
        for (DocumentDTO doc : docs) {
            File tempLocation = new File(PAUtil.getTemporaryDocumentFilePath(doc));
            File finalLocation =
                    new File(PAUtil.getDocumentFilePath(IiConverter.convertToLong(doc.getIdentifier()),
                                                        StConverter.convertToString(doc.getFileName()),
                                                        assignedIdentifier));
            if (tempLocation.exists()) {
                try {
                    FileUtils.copyFile(tempLocation, finalLocation);
                    FileUtils.deleteQuietly(tempLocation);
                } catch (IOException e) {
                    throw new PAException("Error moving document from " + tempLocation.getAbsolutePath() + " to "
                            + finalLocation.getAbsolutePath(), e);
                }
            }
        }
    }

    /**
     * Cleans an existing trial folder.
     * @param destinationId the assigned id of the trial that will be the final location for the trial documents
     * @throws PAException on error
     * @throws IOException on file manipulation error
     */
      public void handleUpdatedTrialDocuments(Ii destinationId) throws PAException, IOException {
          String docPath = PaEarPropertyReader.getDocUploadPath();
          File destination  = new File(docPath + File.separator + destinationId.getExtension());

          //First clean out the destination directory.
          FileUtils.cleanDirectory(destination);
      }
      
    /**
     * Generates an HTML table with abstraction validation errors in it.
     * 
     * @param spIi spIi
     * @param isoDocWrkStatus isoDocWrkStatus
     * @throws PAException PAException
     * @return StringBuilder StringBuilder
     */
    public StringBuilder createAbstractionValidationErrorsTable(Ii spIi,
            DocumentWorkflowStatusDTO isoDocWrkStatus) throws PAException {
        StringBuilder sbuf = new StringBuilder();
        DocumentWorkflowStatusCode statusCode = CdConverter.convertCdToEnum(
                DocumentWorkflowStatusCode.class,
                isoDocWrkStatus.getStatusCode());
        if (statusCode != null && statusCode.isAbstractedOrAbove()) {
            List<AbstractionCompletionDTO> errorList = PaRegistry
                    .getAbstractionCompletionService()
                    .validateAbstractionCompletion(spIi);
            if (!errorList.isEmpty()) {
                sbuf.append("<table><tr><td><b>Type</b></td><td><b>Description</b></td><td><b>Comments</b></td></tr>");
                for (AbstractionCompletionDTO abDTO : errorList) {
                    String errorType = abDTO.getErrorType();
                    if (errorType == AbstractionCompletionDTO.ERROR_TYPE) {
                        sbuf.append("<tr><td>" + errorType)
                                .append("</td><td>")
                                .append(abDTO.getErrorDescription())
                                .append("</td><td>").append(abDTO.getComment())
                                .append("</td></tr>");
                    }
                }
                sbuf.append("</table>");
            }
        }
        return sbuf;
    }
    
    /**
     * @return List<SecondaryPurposeDTO>
     */
    public static List<String> getSecondaryPurposeList() {
        List<String> list = new ArrayList<String>();
        Session session = PaHibernateUtil.getCurrentSession();
        for (SecondaryPurpose purpose : (List<SecondaryPurpose>) session
                .createQuery(" from " + SecondaryPurpose.class.getName())
                .list()) {
            list.add(purpose.getName());
        }
        return list;
    }  
    
    /**
     * Returns NCI ID of the given trial.
     * @param id ID
     * @return NCI ID
     */
    public String getTrialNciId(Long id) {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("select extension from study_otheridentifiers where study_protocol_id="
                        + id
                        + " and root='"
                        + IiConverter.STUDY_PROTOCOL_ROOT
                        + "'");
        List<String> list = query.list();
        return list.isEmpty() ? "" : list.get(0);
    }
    
    /**
     * Returns true if automated PO curation (i.e. elimination of curation
     * events) should be attempted for the current user.
     * 
     * @return isAutoCurationEnabled
     * @throws PAException PAException
     */
    public boolean isAutoCurationEnabled() throws PAException {
        return CSMUserService.getInstance().isCurrentUserAutoCuration()
                || CSMUserService.getInstance().isCurrentUserAbstractor();
    }

    /**
     * @param nctIdentifier the nctIdentifier
     * @param studyProtocolIi the identifier
     * @return errorMsg
     */
    public String validateNCTIdentifier(String nctIdentifier, Ii studyProtocolIi) {
        String errorMsg = null;
        StudySiteDTO nctIdentifierDTO = new StudySiteDTO();
        nctIdentifierDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(nctIdentifier));
        nctIdentifierDTO.setFunctionalCode(
                CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
        nctIdentifierDTO.setStudyProtocolIdentifier(studyProtocolIi);
        try {
            String poOrgId = PaRegistry.getOrganizationCorrelationService().getPOOrgIdentifierByIdentifierType(
                PAConstants.NCT_IDENTIFIER_TYPE);
            nctIdentifierDTO.setResearchOrganizationIi(PaRegistry.getOrganizationCorrelationService().
                getPoResearchOrganizationByEntityIdentifier(IiConverter.convertToPoOrganizationIi(poOrgId)));
            PaRegistry.getStudySiteService().validate(nctIdentifierDTO);
        } catch (PAException e) {
            errorMsg = e.getMessage();
        }
        return errorMsg;
    }  
    
    /**
     * Returns Duplicate NCI Trial Id based on Obsolete NCT identifier of the given trial.
     * @param id ID
     * @param nctId NCT ID
     * @return NCI ID
     */
    public String validateTrialObsoleteNctId(Long id, String nctId) {
        String nciId = getTrialNciId(id);
        String duplicateNciId = null;
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("select extension from study_otheridentifiers where study_protocol_id in "
                        + "(select study_protocol_id from study_otheridentifiers where extension = '"
                        + nctId
                        + "' and identifier_name = '" 
                        + IiConverter.OBSOLETE_NCT_STUDY_PROTOCOL_IDENTIFIER_NAME + "')"
                        + " and root='" 
                        + IiConverter.STUDY_PROTOCOL_ROOT
                        + "'");
        List<String> list = query.list();
        for (String ext : list) {
            if (!nciId.equals(ext)) {
                duplicateNciId = ext;
                break;
            }
        }
        return duplicateNciId;
    }
    
    /**
     * @param studyIdentifierDTOs
     *            list of studyIdentifierDTOs
     * @return StudySiteDTO
     * @throws PAException
     *             the exception
     */
    public StudySiteDTO extractDcpID(List<StudySiteDTO> studyIdentifierDTOs)
            throws PAException {
        final String identifierAssigner = PAConstants.DCP_IDENTIFIER_TYPE;
        return extractIdentifierAssignerSite(studyIdentifierDTOs,
                identifierAssigner);
    }

    /**
     * @param studyIdentifierDTOs list of studyIdentifierDTOs
     * @return StudySiteDTO
     * @throws PAException the exception
     */
    public StudySiteDTO extractNCTDto(List<StudySiteDTO> studyIdentifierDTOs) throws PAException {
        final String identifierAssigner = PAConstants.NCT_IDENTIFIER_TYPE;
        return extractIdentifierAssignerSite(studyIdentifierDTOs,
                identifierAssigner);
    }
    /**
     * @param studyIdentifierDTOs
     * @param identifierAssigner
     * @return
     * @throws PAException
     */
    private StudySiteDTO extractIdentifierAssignerSite(
            final List<StudySiteDTO> studyIdentifierDTOs,
            final String identifierAssigner) throws PAException {
        StudySiteDTO assignerSite = null;
        Ii roID = null;
        if (CollectionUtils.isNotEmpty(studyIdentifierDTOs)) {            
            String poOrgId = PaRegistry.getOrganizationCorrelationService().getPOOrgIdentifierByIdentifierType(
                   identifierAssigner);
            roID = PaRegistry.getOrganizationCorrelationService().getPoResearchOrganizationByEntityIdentifier(
                    IiConverter.convertToPoOrganizationIi(String.valueOf(poOrgId)));
            for (StudySiteDTO dto : studyIdentifierDTOs) {
                if (!ISOUtil.isIiNull(dto.getResearchOrganizationIi())
                        && dto.getResearchOrganizationIi().equals(roID)) {
                    assignerSite = dto;
                    break;
                }
            }
        }
        return assignerSite;
    }
    
    /**
     * Returns total number of accruals for a trial.
     * @param spId trial ID
     * @return accruals
     */
    public int getTrialAccruals(Ii spId) {
        int result = 0;
        Long studyProtocolId = IiConverter.convertToLong(spId);
        Session session = PaHibernateUtil.getCurrentSession();
        
        String patientLevelQry = "select count(*) from study_subject "
                + "where study_protocol_identifier = " + studyProtocolId + " and status_code <> 'NULLIFIED'";
        
        String summaryLevelQry = "select coalesce(sum(accrual_count),0) from "
                + "study_site_subject_accrual_count where study_protocol_identifier = " + studyProtocolId;
        
        result = Integer.valueOf(session.createSQLQuery(patientLevelQry).uniqueResult().toString());
        
        if (result == 0) {        
            result = Integer.valueOf(session.createSQLQuery(summaryLevelQry).uniqueResult().toString());
        }
        return result;
    }
    
    /**
     * Returns the ctep or dcp id. 
     * @param spId studyProtocol id
     * @param whichId kind of id
     * @return String value
     * @throws PAException the exception
     */
    public String getCtepOrDcpId(Long spId, String whichId) throws PAException {
        String ctepIdQuery = "select local_sp_indentifier from rv_ctep_id where study_protocol_identifier = :spId";
        String dcpIdQuery = "select local_sp_indentifier from rv_dcp_id  where study_protocol_identifier = :spId";
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = null;
        if (whichId.equals(PAConstants.DCP_IDENTIFIER_TYPE)) {
            query = session.createSQLQuery(dcpIdQuery);
            query.setLong("spId", spId);
        } else if (whichId.equals(PAConstants.CTEP_IDENTIFIER_TYPE)) {
            query = session.createSQLQuery(ctepIdQuery);
            query.setLong("spId", spId);
        }
        List<String> queryList = query.list();
        return CollectionUtils.isNotEmpty(queryList) ? queryList.get(0) : "";
    }
    /**
     * 
     * @param tableName tableName
     * @param sourceIi sourceIi
     * @param targetIi targetIi
     * @throws PAException PAException
     */
    public void swapStudyProtocolIdentifiers(String tableName, Ii sourceIi, Ii targetIi) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        List<Integer> queryList = null;
        SQLQuery query = session.createSQLQuery("select identifier from "
               + tableName 
               + " where study_protocol_identifier =:sourceIi");
        query.setParameter("sourceIi", Integer.parseInt(sourceIi.getExtension()));
        queryList = query.list();
        SQLQuery query2 = session.createSQLQuery("update "
                + tableName + " SET study_protocol_identifier = "
                + "(:targetIi) where study_protocol_identifier =(:sourceIi)"); 
        query2.setParameter("sourceIi", Integer.parseInt(sourceIi.getExtension()));
        query2.setParameter("targetIi", Integer.parseInt(targetIi.getExtension()));
        query2.executeUpdate();
        StringBuffer sql = new StringBuffer();
        sql.append("update "
                + tableName + " SET study_protocol_identifier = (:sourceIi) "
                + "where study_protocol_identifier = (:targetIi) and identifier NOT IN (:idList)");
        SQLQuery query1 = session.createSQLQuery(sql.toString());
        if (!queryList.isEmpty()) {
            query1.setParameterList("idList", queryList);
        } else {
            query1.setParameter("idList", null);
        }
        query1.setParameter("sourceIi", Integer.parseInt(sourceIi.getExtension()));
        query1.setParameter("targetIi", Integer.parseInt(targetIi.getExtension()));
        query1.executeUpdate();
    }
    
}
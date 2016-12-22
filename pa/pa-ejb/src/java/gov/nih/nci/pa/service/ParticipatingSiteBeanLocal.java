/*
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.service;


import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteSubjectAccrualCount;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.convert.ParticipatingSiteConverter;
import gov.nih.nci.pa.iso.convert.StudySiteConverter;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteContactDTO;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.exception.DuplicateParticipatingSiteException;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.AbstractPersonRoleDTO;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.ExceptionUtils;

/**
 * @author mshestopalov
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@SuppressWarnings({"PMD.AvoidRethrowingException" 
    , "PMD.CyclomaticComplexity" }) //Suppressed to catch and throw PAException to avoid re-wrapping.
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ParticipatingSiteBeanLocal extends AbstractParticipatingSitesBean // NOPMD
    implements ParticipatingSiteServiceLocal { // NOPMD
    
    private static final Logger LOG = Logger.getLogger(ParticipatingSiteBeanLocal.class);
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<ParticipatingSiteDTO> getParticipatingSitesByStudyProtocol(Ii studyProtocolIi) throws PAException {
        StudyProtocolDTO studyProtocolDTO = getStudyProtocolService().getStudyProtocol(studyProtocolIi);

        StudySiteDTO criteria = new StudySiteDTO();
        criteria.setStudyProtocolIdentifier(studyProtocolDTO.getIdentifier());
        criteria.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));

        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        try {
            List<StudySiteDTO> results = getStudySiteService().search(criteria, limit);
            return convertStudySiteDTOsToParticipatingSiteDTOs(results);
        } catch (TooManyResultsException e) {
            throw new PAException(e.getMessage(), e);
        }
    }
 // commented as part of PO-9862
    /*
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Map <String, ParticipatingSiteDTO> closeOpenSites(Ii spID, StudyOverallStatusDTO oldStatus,
            StudyOverallStatusDTO trialStatus, boolean notifyTrialOwners)
            throws PAException {        
        
       
        Map <String, ParticipatingSiteDTO> siteStatusMap = 
                new HashMap<String, ParticipatingSiteDTO>();
        StudyStatusCode trialStatusCode = CdConverter.convertCdToEnum(
                StudyStatusCode.class, trialStatus.getStatusCode());
        Date trialStatusDate = TsConverter.convertToTimestamp(trialStatus
                .getStatusDate());
        
        SiteStatusChangeNotificationData dataForEmail = new SiteStatusChangeNotificationData(
                spID, oldStatus, trialStatus);
        
        SiteStatusChangeNotificationData dataForNotClosedEmail = new SiteStatusChangeNotificationData(
                spID, oldStatus, trialStatus);

        List<ParticipatingSiteDTO> sites = getParticipatingSitesByStudyProtocol(spID);
        for (ParticipatingSiteDTO site : sites) {
            StudySiteAccrualStatusDTO siteStatus = site
                    .getStudySiteAccrualStatus();
            RecruitmentStatusCode siteStatusCode = CdConverter.convertCdToEnum(
                    RecruitmentStatusCode.class, siteStatus.getStatusCode());
            Date siteStatusDate = TsConverter.convertToTimestamp(siteStatus
                    .getStatusDate());
            if (siteStatusCode != null && !siteStatusCode.isClosed()
                    && siteStatusDate != null) {
                
                if (!getStudySiteAccrualStatusService().ifCloseStatusExistsInHistory(
                        site.getIdentifier()) && trialStatusDate.after(siteStatusDate)) {
                    
                       
                
                StudySiteAccrualStatusDTO newStatus = new StudySiteAccrualStatusDTO();                
                newStatus.setStatusDate(TsConverter.convertToTs(trialStatusDate));
                final RecruitmentStatusCode newStatusCode = RecruitmentStatusCode
                        .getByStatusCode(trialStatusCode);
                newStatus.setStatusCode(CdConverter
                        .convertToCd(newStatusCode));
                //set comments as this is automatically closed
                newStatus.setComments(
                StConverter.convertToSt(
                        "The CTRP application automatically closed this site because the trial was closed"));
                createStudySiteAccrualStatus(site.getIdentifier(), newStatus);
                
                //if site is closed collect details so it can be used later when 
                //application is sending email for industrial site status close
                siteStatusMap.put(siteStatusCode.getDisplayName(), site);
               
                
                if (notifyTrialOwners) {
                    SiteData siteData = new SiteData(site.getSiteOrgName(),
                            siteStatusCode, newStatusCode,
                            getStatusHistoryErrors(site));
                    dataForEmail.getSiteData().add(siteData);
                }
              } else { //if this site is not closed then add this to list so that email can be sent later
                    SiteData siteData = new SiteData(site.getSiteOrgName(),
                            siteStatusCode, null,
                            null);
                    siteData.setPreviousTrialStatusDate(TsConverter.convertToString(siteStatus
                        .getStatusDate()));
                    dataForNotClosedEmail.getSiteData().add(siteData);
                }  
            } 
           
        }
        
        if (!dataForEmail.getSiteData().isEmpty()) {
            Collections.sort(dataForEmail.getSiteData());
            getMailManagerService().sendSiteCloseNotification(dataForEmail);
        }
        
        //send sites not closed email
        if (!dataForNotClosedEmail.getSiteData().isEmpty()) {
            Collections.sort(dataForEmail.getSiteData());
            getMailManagerService().sendSiteNotCloseNotification(dataForNotClosedEmail);
        }
        
        return siteStatusMap;
    }
    


    private String getStatusHistoryErrors(ParticipatingSiteDTO site)
            throws PAException {
        Collection<StatusDto> hist = getStudySiteAccrualStatusService()
                .getStatusHistory(site.getIdentifier());
        if (CollectionUtils.isNotEmpty(hist)) {
            getStatusTransitionService().validateStatusHistory(
                    AppName.REGISTRATION, TrialType.COMPLETE,
                    TransitionFor.SITE_STATUS, new ArrayList<StatusDto>(hist));
            Collection<String> issues = new TreeSet<String>();
            for (StatusDto status : hist) {
                for (ValidationError err : status.getValidationErrors()) {
                    issues.add(err.getErrorMessage());
                }
            }
            return StringUtils.join(issues, ". ");
        }
        return StringUtils.EMPTY;
    }
   */
    /**
     * Convert the given list of StudySiteDTO into a list of ParticipatingSiteDTO.
     * @param dtos The list of StudySiteDTO to convert
     * @return The list of ParticipatingSiteDTO
     * @throws PAException if an error occurs
     */
    @SuppressWarnings("unchecked")
    List<ParticipatingSiteDTO> convertStudySiteDTOsToParticipatingSiteDTOs(List<StudySiteDTO> dtos)
            throws PAException {
        List<Long> ids = new ArrayList<Long>();
        List<StudySite> results = new ArrayList<StudySite>();
        if (CollectionUtils.isNotEmpty(dtos)) {
            for (StudySiteDTO dto : dtos) {
                ids.add(IiConverter.convertToLong(dto.getIdentifier()));
            }
            Criteria criteria = PaHibernateUtil.getCurrentSession().createCriteria(StudySite.class);
            criteria.add(Restrictions.in("id", ids));
            results = criteria.list();
        }
        ParticipatingSiteConverter converter = new ParticipatingSiteConverter();
        return converter.convertFromDomainToDtos(results);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingSiteDTO createStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO, OrganizationDTO orgDTO, HealthCareFacilityDTO hcfDTO,
            List<ParticipatingSiteContactDTO> participatingSiteContactDTOList) throws PAException {
        checkStudyProtocol(studySiteDTO.getStudyProtocolIdentifier());
        ParticipatingSiteDTO participatingSiteDTO = createStudySiteParticipant(studySiteDTO, currentStatusDTO, orgDTO,
                                                                               hcfDTO);
        updateStudySiteContacts(participatingSiteContactDTOList, participatingSiteDTO);
        return getParticipatingSite(participatingSiteDTO.getIdentifier());
    }

    
    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ParticipatingSiteDTO updateStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO,
            List<ParticipatingSiteContactDTO> participatingSiteContactDTOList) throws PAException {
        StudySiteDTO currentSite = getStudySiteDTO(studySiteDTO.getIdentifier());
        checkStudyProtocol(currentSite.getStudyProtocolIdentifier());
        ParticipatingSiteDTO participatingSiteDTO = updateStudySiteParticipant(studySiteDTO, currentStatusDTO);
        updateStudySiteContacts(participatingSiteContactDTOList, participatingSiteDTO);
        return getParticipatingSite(participatingSiteDTO.getIdentifier());
    }
   

    
    
    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ParticipatingSiteDTO createStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO, Ii poHcfIi,
            List<ParticipatingSiteContactDTO> participatingSiteContactDTOList) throws PAException {
        checkStudyProtocol(studySiteDTO.getStudyProtocolIdentifier());
        ParticipatingSiteDTO participatingSiteDTO = createStudySiteParticipant(studySiteDTO, currentStatusDTO, poHcfIi);
        updateStudySiteContacts(participatingSiteContactDTOList, participatingSiteDTO);
        return getParticipatingSite(participatingSiteDTO.getIdentifier());
    }
   
    /**
     * Gets the Participating site with the given Ii.
     * @param studySiteIi The study site Ii
     * @return The Participating site with the given Ii.
     * @throws PAException if an error occurs
     */
    @Override
    public ParticipatingSiteDTO getParticipatingSite(Ii studySiteIi) throws PAException {
        ParticipatingSiteDTO participatingSiteDTO = new ParticipatingSiteConverter()
            .convertFromDomainToDto(getStudySite(studySiteIi));
        // we should be able just to do a participatingSiteDTO.getStudySiteContacts() to fetch the StudySiteContactlist,
        // but it doesn't appear to be working properly. PO-2911 created to address that.
        participatingSiteDTO.setStudySiteContacts(getStudySiteContactService().getByStudySite(studySiteIi));
        return participatingSiteDTO;
    }
    
    /**
     * Check that the study protocol with the given Ii exist and that the current user can access it.
     * @param studyProtocolIi The study protocil Ii
     * @throws PAException if the study protocol does not exist or the user can not access it
     */
    void checkStudyProtocol(Ii studyProtocolIi) throws PAException {
        StudyProtocolDTO studyProtocolDTO = getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        if (studyProtocolDTO == null || ISOUtil.isIiNull(studyProtocolDTO.getIdentifier())) {
            throw new PAException("Trial id " + studyProtocolIi.getExtension() + " does not exist.");
        }
        PAUtil.checkUserIsTrialOwnerOrAbstractor(studyProtocolDTO);
    }
    
    
    /**
     * Update the contacts of a given study site.
     * @param participatingSiteContactDTOList The new contacts
     * @param participatingSiteDTO The participating site to update
     * @throws PAException if an error occurs
     */
    void updateStudySiteContacts(List<ParticipatingSiteContactDTO> participatingSiteContactDTOList,
            ParticipatingSiteDTO participatingSiteDTO) throws PAException {
        StudySiteContactService studySiteContactService = getStudySiteContactService();
        for (StudySiteContactDTO dto : studySiteContactService.getByStudySite(participatingSiteDTO.getIdentifier())) {
            studySiteContactService.delete(dto.getIdentifier());
        }
        for (ParticipatingSiteContactDTO participatingSiteContactDTO : participatingSiteContactDTOList) {
            addStudySiteContact(participatingSiteDTO, participatingSiteContactDTO);
        }
    }
    
    private void addStudySiteContact(ParticipatingSiteDTO participatingSiteDTO,
            ParticipatingSiteContactDTO participatingSiteContactDTO) throws PAException {
        StudySiteContactDTO studySiteContactDTO = participatingSiteContactDTO.getStudySiteContactDTO();
        PersonDTO personDTO = participatingSiteContactDTO.getPersonDTO();
        AbstractPersonRoleDTO personRoleDTO = participatingSiteContactDTO.getAbstractPersonRoleDTO();
        String roleCode = getRoleCode(studySiteContactDTO.getRoleCode());
        Boolean isPrimary = getPrimaryIndicator(studySiteContactDTO.getPrimaryIndicator());

        if (personRoleDTO instanceof ClinicalResearchStaffDTO) {
            this.addStudySiteInvestigator(participatingSiteDTO.getIdentifier(),
                                          (ClinicalResearchStaffDTO) personRoleDTO, null, personDTO, roleCode);
            if (isPrimary) {
                this.addStudySitePrimaryContact(participatingSiteDTO.getIdentifier(),
                                                (ClinicalResearchStaffDTO) personRoleDTO, null, personDTO,
                                                studySiteContactDTO.getTelecomAddresses());
            }
        } else if (personRoleDTO instanceof HealthCareProviderDTO) {
            this.addStudySiteInvestigator(participatingSiteDTO.getIdentifier(), null,
                                          (HealthCareProviderDTO) personRoleDTO, personDTO, roleCode);
            if (isPrimary) {
                this.addStudySitePrimaryContact(participatingSiteDTO.getIdentifier(), null,
                                                (HealthCareProviderDTO) personRoleDTO, personDTO,
                                                studySiteContactDTO.getTelecomAddresses());
            }
        } else if (personRoleDTO instanceof OrganizationalContactDTO) {
            this.addStudySiteGenericContact(participatingSiteDTO.getIdentifier(),
                                            (OrganizationalContactDTO) personRoleDTO, isPrimary,
                                            studySiteContactDTO.getTelecomAddresses());
        }
    }

    
    private Boolean getPrimaryIndicator(Bl primInd) throws PAException {
        Boolean isPrimary = BlConverter.convertToBoolean(primInd);
        if (isPrimary == null) {
            throw new PAException("Primary indicator must be set on all study site contacts.");
        }
        return isPrimary;
    }

    private String getRoleCode(Cd cdCode) throws PAException {
        String code = CdConverter.convertCdToString(cdCode);
        if (code == null) {
            throw new PAException("Role Code must be set on all study site contacts.");
        }
        return code;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Ii getParticipatingSiteIi(Ii studyProtocolIi, Ii someHcfIi) throws PAException {
        try {
            StudyProtocolDTO studyProtocolDTO = getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            HealthCareFacilityDTO hcfDTO = new HealthCareFacilityDTO();
            hcfDTO.setIdentifier(DSetConverter.convertIiToDset(someHcfIi));
            Ii poHcfIi = generateHcfIiFromCtepIdOrNewOrg(null, hcfDTO);
            return getStudySiteService().getStudySiteIiByTrialAndPoHcfIi(studyProtocolDTO.getIdentifier(), poHcfIi);
        } catch (PAException e) {
            throw e;
        } catch (Exception e) {
            throw new PAException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addStudySiteGenericContact(Ii studySite, OrganizationalContactDTO contactDTO, boolean isPrimaryContact,
            DSet<Tel> telecom) throws PAException {
        try {
            validateGenericContact(contactDTO, telecom);
            StudySiteDTO studySiteDTO = getStudySiteService().get(studySite);
            Organization paOrg = getCorrUtils().getPAOrganizationByIi(studySiteDTO.getHealthcareFacilityIi());
            contactDTO.setScoperIdentifier(IiConverter.convertToPoOrganizationIi(paOrg.getIdentifier()));
            Ii genericContactIi = getCorrUtils().getGenericContactIiFromCtepId(contactDTO);
            Ii orgIi = getCorrUtils().getPoOrgIiFromPaHcfIi(studySiteDTO.getHealthcareFacilityIi());
            Map<String, Ii> myMap = new HashMap<String, Ii>();
            myMap.put(IiConverter.ORGANIZATIONAL_CONTACT_ROOT, genericContactIi);
            myMap.put(IiConverter.ORG_ROOT, orgIi);
            createStudyParticationContactRecord(studySiteDTO, myMap, isPrimaryContact, null, telecom);
        } catch (PAException e) {
            throw e;
        } catch (Exception e) {
            throw new PAException(e);
        }
    }

    private void validateGenericContact(OrganizationalContactDTO contactDTO, DSet<Tel> telecom) throws PAException {
        String email = DSetConverter.getFirstElement(telecom, "EMAIL");
        String phone = DSetConverter.getFirstElement(telecom, "PHONE");
        if (StringUtils.isEmpty(email) && StringUtils.isEmpty(phone)) {
            throw new PAException("Generic contact '" + StConverter.convertToString(contactDTO.getTitle())
                    + "' must have either a phone number or email address specified.");
        }
        String typeCode = CdConverter.convertCdToString(contactDTO.getTypeCode());
        if (!StringUtils.equalsIgnoreCase(typeCode, "Site")) {
            throw new PAException("Generic contact '" + StConverter.convertToString(contactDTO.getTitle())
                    + "' can only have a type of 'Site' not " + typeCode);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addStudySiteInvestigator(Ii studySiteIi, ClinicalResearchStaffDTO poCrsDTO,
            HealthCareProviderDTO poHcpDTO, PersonDTO investigatorDTO, String roleCode) throws PAException {
        try {
            StudySiteDTO studySiteDTO = getStudySiteService().get(studySiteIi);
            Ii orgIi = getCorrUtils().getPoOrgIiFromPaHcfIi(studySiteDTO.getHealthcareFacilityIi());
            Map<String, Ii> myMap = generateCrsAndHcpFromCtepIdOrNewPerson(investigatorDTO, poCrsDTO, poHcpDTO, orgIi);
            createStudyParticationContactRecord(studySiteDTO, myMap, false, roleCode, null);
        } catch (PAException e) {
            throw e;
        } catch (Exception e) {
            throw new PAException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addStudySitePrimaryContact(Ii studySiteIi, ClinicalResearchStaffDTO poCrsDTO,
            HealthCareProviderDTO poHcpDTO, PersonDTO personDTO, DSet<Tel> telecom) throws PAException {
        try {
            if (ISOUtil.isDSetEmpty(telecom)) {
                throw new PAException("Study Site Contacts must have telecom address info for primary contact.");
            }
            StudySiteDTO studySiteDTO = getStudySiteService().get(studySiteIi);
            Ii orgIi = getCorrUtils().getPoOrgIiFromPaHcfIi(studySiteDTO.getHealthcareFacilityIi());
            Map<String, Ii> myMap = generateCrsAndHcpFromCtepIdOrNewPerson(personDTO, poCrsDTO, poHcpDTO, orgIi);
            createStudyParticationContactRecord(studySiteDTO, myMap, true, null, telecom);
        } catch (PAException e) {
            throw e;
        } catch (Exception e) {
            throw new PAException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingSiteDTO createStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO, OrganizationDTO orgDTO, HealthCareFacilityDTO hcfDTO)
            throws PAException {
        // assume that siteDTO has a real Ii for studyProtocol
        Ii poHcfIi = null;
        try {
            poHcfIi = generateHcfIiFromCtepIdOrNewOrg(orgDTO, hcfDTO);
            return createStudySiteParticipant(studySiteDTO, currentStatusDTO, poHcfIi);
        } catch (EJBTransactionRolledbackException e) {
            LOG.error(e, e);
            if (e.getCause() instanceof ConstraintViolationException) {
                DuplicateParticipatingSiteException duplicateParticipatingSiteException =
                getCorrectException(// NOPMD                        
                            studySiteDTO.getStudyProtocolIdentifier(), poHcfIi,
                            findDuplicateSiteId(
                                    studySiteDTO.getStudyProtocolIdentifier(),
                                    poHcfIi));
                throw duplicateParticipatingSiteException;
                
            } else {
                throw new PAException(e);
            }
        } catch (PAException e) {
            throw e;
        } catch (Exception e) {
            throw new PAException(e);
        }
    }
    
    private DuplicateParticipatingSiteException getCorrectException(Ii trialIi, Ii hcfIi, Ii duplicateSiteID)
     {
        DuplicateParticipatingSiteException duplicateParticipatingSiteException;
        String [] trialIds = getTrialDetails(trialIi);
        String siteCtepId = null;
        StringBuffer errorMessage = new StringBuffer();
        String orgPoId = null;
        try {
            orgPoId = getCorrUtils().getPoOrgIiFromPaHcfIi(hcfIi).getExtension();
            PaOrganizationDTO paOrganizationDTO =
                    PADomainUtils.getOrgDetailsPopup(orgPoId); 
            siteCtepId = paOrganizationDTO.getCtepId();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        //construct error message depending on what identifiers are available
        errorMessage.append("A Participating Site with PO ID: ");
        errorMessage.append(orgPoId);
        if (!StringUtils.isEmpty(siteCtepId)) {
            errorMessage.append(" , CTEP ID: ");
            errorMessage.append(siteCtepId);
        }
        errorMessage.append(" already exists on Trial with");
        errorMessage.append(" NCI ID: ");
        errorMessage.append(trialIds[1]);
        if (!StringUtils.isEmpty(trialIds[0])) {
            errorMessage.append(" , CTEP ID: ");
            errorMessage.append(trialIds[0]);
        }
         duplicateParticipatingSiteException = 
          new DuplicateParticipatingSiteException(errorMessage.toString());
         
         
        
        return duplicateParticipatingSiteException;
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ParticipatingSiteDTO createStudySiteParticipant(
            StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO,
            Collection<StatusDto> statusHistory, Ii poHcfIi) throws PAException {
        if (CollectionUtils.isEmpty(statusHistory)) {
            return createStudySiteParticipant(studySiteDTO, currentStatusDTO,
                    poHcfIi);
        }
        // create the site with the first item in the status history being the
        // current status, and then
        // completely the status history by persisting the rest of it.
        StatusDto initialStatus = statusHistory.iterator().next();
        statusHistory.remove(initialStatus);
        StudySiteAccrualStatusDTO initialStatusDTO = convert(initialStatus);
        ParticipatingSiteDTO site = createStudySiteParticipant(studySiteDTO, initialStatusDTO,
                poHcfIi);
        for (StatusDto stat : statusHistory) {
            StudySiteAccrualStatusDTO statDTO = convert(stat);          
            createStudySiteAccrualStatus(site.getIdentifier(), statDTO);
        }
        return site;
    }

    @SuppressWarnings("deprecation")
    private StudySiteAccrualStatusDTO convert(StatusDto stat) {
        StudySiteAccrualStatusDTO ssas = new StudySiteAccrualStatusDTO();
        ssas.setIdentifier(IiConverter.convertToIi(stat.getId()));
        ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode
                .valueOf(stat.getStatusCode())));
        ssas.setStatusDate(TsConverter.convertToTs(stat.getStatusDate()));
        ssas.setComments(StConverter.convertToSt(stat.getComments()));        
        return ssas;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingSiteDTO createStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO, Ii poHcfIi) throws PAException {
        // assume that there is a poHcf out there already
        // assume that siteDTO has a real Ii for studyProtocol
        try {
            // check business rules based on trial type.
            StudyProtocolDTO spDTO = getStudyProtocolService().getStudyProtocol(
                    studySiteDTO.getStudyProtocolIdentifier());
            studySiteDTO.setStudyProtocolIdentifier(spDTO.getIdentifier());
            if (spDTO.getProprietaryTrialIndicator().getValue().booleanValue()) {
                enforceBusinessRulesForProprietary(spDTO, studySiteDTO, currentStatusDTO);
            } else {
                enforceBusinessRules(currentStatusDTO, PAUtil.getCurrentTime());
            }
            
            StudySite ss = saveOrUpdateStudySiteHelper(true, studySiteDTO, poHcfIi, currentStatusDTO);            
            return new ParticipatingSiteConverter().convertFromDomainToDto(ss);
        } catch (EJBTransactionRolledbackException e) {
            LOG.error(e, e);
            if (e.getCause() instanceof ConstraintViolationException 
               || ExceptionUtils.getRootCause(e).getMessage().equalsIgnoreCase(
                "ERROR: Can not add duplicate Participating site")) {
                DuplicateParticipatingSiteException duplicateParticipatingSiteException =
                        getCorrectException(// NOPMD                        
                                    studySiteDTO.getStudyProtocolIdentifier(), poHcfIi,
                                    findDuplicateSiteId(
                                            studySiteDTO.getStudyProtocolIdentifier(),
                                            poHcfIi));
                        throw duplicateParticipatingSiteException;
            } else {
                throw new PAException(e);
            }
        } catch (PAException e) {
            throw e;
        } catch (Exception e) {
            throw new PAException(e);
        }
    }
    
    private String [] getTrialDetails(Ii studyProtocolIdentifier) {
        String [] trialIds = new String[2];
        
        try {
            StudyProtocolQueryCriteria studyProtocolQueryCriteria = new StudyProtocolQueryCriteria();
            studyProtocolQueryCriteria.setStudyProtocolId(IiConverter.convertToLong(studyProtocolIdentifier));
            List<StudyProtocolQueryDTO> results = getProtocolQueryService()
                    .getStudyProtocolByCriteria(studyProtocolQueryCriteria);
            if (!CollectionUtils.isEmpty(results)) {
                String ctepId = results.get(0).getCtepId();
                if (StringUtils.isNotEmpty(ctepId)) {
                    trialIds[0] = ctepId;
                }
                trialIds[1] = results.get(0).getNciIdentifier();
            }
            
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return trialIds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParticipatingSiteDTO updateStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO) throws PAException {
        // assume that siteDTO has a real Ii for studyProtocol
        try {
            // check business rules based on trial type.
            StudySiteDTO currentSite = getStudySiteDTO(studySiteDTO.getIdentifier());
            validateStudySite(studySiteDTO, currentSite);
            studySiteDTO.setStudyProtocolIdentifier(currentSite.getStudyProtocolIdentifier());
            studySiteDTO.setHealthcareFacilityIi(currentSite.getHealthcareFacilityIi());
            studySiteDTO.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));

            StudyProtocolDTO spDTO = getStudyProtocolService().getStudyProtocol(
                    currentSite.getStudyProtocolIdentifier());
            if (spDTO.getProprietaryTrialIndicator().getValue().booleanValue()) {
                enforceBusinessRulesForProprietary(spDTO, studySiteDTO, currentStatusDTO);
            } else {
                enforceBusinessRules(currentStatusDTO, PAUtil.getCurrentTime());
            }
            StudySite ss = saveOrUpdateStudySiteHelper(false, studySiteDTO, null, currentStatusDTO);
            return new ParticipatingSiteConverter().convertFromDomainToDto(ss);
        } catch (PAException e) {
            throw e;
        } catch (Exception e) {
            throw new PAException(e);
        }
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ParticipatingSiteDTO updateStudySiteParticipantWithStatusHistory(
            StudySiteDTO studySiteDTO, StudySiteAccrualStatusDTO newStatus,
            Collection<StatusDto> statusHistory) throws PAException {
        if (CollectionUtils.isEmpty(statusHistory)) {
            return updateStudySiteParticipant(studySiteDTO, newStatus);
        }
        StudySiteAccrualStatusDTO currentStatus = getStudySiteAccrualStatusService()
                .getCurrentStudySiteAccrualStatusByStudySite(
                        studySiteDTO.getIdentifier());
        // This will take care of site updates, leaving the status unchanged.
        ParticipatingSiteDTO updated = updateStudySiteParticipant(studySiteDTO,
                currentStatus);
        
        // now handle the history: sort out deletes, updates, additions.
        for (StatusDto stat : statusHistory) {
            StudySiteAccrualStatusDTO statDTO = convert(stat);
            statDTO.setStudySiteIi(studySiteDTO.getIdentifier());
            if (stat.isDeleted() && stat.getId() != null) {
                getStudySiteAccrualStatusService().softDelete(statDTO);
            } else if (!stat.isDeleted() && stat.getId() == null) {
                getStudySiteAccrualStatusService()
                        .createStudySiteAccrualStatus(statDTO);
            } else if (!stat.isDeleted() && stat.getId() != null) {
                getStudySiteAccrualStatusService()
                        .updateStudySiteAccrualStatus(statDTO);
            }
        }
        return updated;
    }

    private void validateStudySite(StudySiteDTO studySiteDTO, StudySiteDTO currentSite) throws PAException {
        if (!ISOUtil.isIiNull(studySiteDTO.getStudyProtocolIdentifier())) {
            StudyProtocolDTO spDto = getStudyProtocolService().getStudyProtocol(studySiteDTO
                                                                                    .getStudyProtocolIdentifier());
            if (spDto == null
                    || !currentSite.getStudyProtocolIdentifier().getExtension()
                        .equals(spDto.getIdentifier().getExtension())) {
                throw new PAException("Trial identifier provided with this update, does not match"
                        + " participating site trial identifier.");
            }
        }
    }

    @SuppressWarnings("deprecation")
    private StudySite saveOrUpdateStudySiteHelper(boolean isCreate, StudySiteDTO siteDTO, Ii poHcfIi,
            StudySiteAccrualStatusDTO currentStatus) throws PAException, EntityValidationException, CurationException {
        if (isCreate && poHcfIi != null) {
            Long paHealthCareFacilityId = getOrganizationCorrelationService().createHcfWithExistingPoHcf(poHcfIi);
            // check that we are not creating another part site w/ same trial and hcf ids.
            Ii paHcfIi = IiConverter.convertToIi(paHealthCareFacilityId);
            if (isDuplicate(siteDTO.getStudyProtocolIdentifier(), paHcfIi)) {
                DuplicateParticipatingSiteException duplicateParticipatingSiteException =
                        getCorrectException(// NOPMD                        
                                siteDTO.getStudyProtocolIdentifier(), poHcfIi,
                                    findDuplicateSiteId(
                                            siteDTO.getStudyProtocolIdentifier(),
                                            poHcfIi));
              throw duplicateParticipatingSiteException;
            }
            siteDTO.setHealthcareFacilityIi(paHcfIi);
        }
        siteDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        
        //update program codes on legacy study - PO-9192
        String pgcText = StConverter.convertToString(siteDTO.getProgramCodeText());
        if (StringUtils.isNotEmpty(pgcText)) { //legacy
            pgcText = StringUtils.join(pgcText.trim().split("\\s*;\\s*"), ";");
            siteDTO.setProgramCodeText(StConverter.convertToSt(pgcText));            
        }

        if (isCreate) {
            siteDTO.setIdentifier(null);
        }

        siteDTO.setStatusDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));

        StudySiteDTO studySiteDTO = null;
        if (isCreate) {
            studySiteDTO = getStudySiteService().create(siteDTO);
        } else {
            studySiteDTO = getStudySiteService().update(siteDTO);
        }
        createStudySiteAccrualStatus(studySiteDTO.getIdentifier(), currentStatus);

        final StudySite savedStudySite = getStudySite(studySiteDTO.getIdentifier());
        
        //update program codes on legacy study - PO-9192        
        if (StringUtils.isNotEmpty(pgcText)) { //legacy
            Long studyId = IiConverter.convertToLong(siteDTO.getStudyProtocolIdentifier());           
            HealthCareFacility hcf = savedStudySite.getHealthCareFacility();
            Long orgPoId = Long.parseLong(hcf.getOrganization().getIdentifier());
            List<String> programCodes = Arrays.asList(pgcText.split("\\s*;\\s*"));
            List<ProgramCodeDTO> pgcList = new ArrayList<ProgramCodeDTO>();
            for (String code : programCodes) {
                pgcList.add(new ProgramCodeDTO(null, code));
            }
            getStudyProtocolService().assignProgramCodes(studyId, orgPoId, pgcList);
        }

        return savedStudySite;        
    }

    @SuppressWarnings("deprecation")
    private Ii findDuplicateSiteId(Ii studyProtocolIdentifier, Ii poHcfIi)
            throws PAException {
        Long paHealthCareFacilityId = getOrganizationCorrelationService()
                .createHcfWithExistingPoHcf(poHcfIi);
        Ii paHcfIi = IiConverter.convertToIi(paHealthCareFacilityId);
        List<StudySiteDTO> list = getStudySiteByTrialAndPaHcf(
                studyProtocolIdentifier, paHcfIi);
        return list.isEmpty() ? IiConverter.convertToIi((Long) null) : list
                .get(0).getIdentifier();
    }

    private boolean isDuplicate(Ii trialIi, Ii paHcfIi) throws PAException {
        return CollectionUtils.isNotEmpty(getStudySiteByTrialAndPaHcf(trialIi,
                paHcfIi));
    }

    private List<StudySiteDTO> getStudySiteByTrialAndPaHcf(Ii trialIi,
            Ii paHcfIi) throws PAException {
        StudySiteDTO ssDto = new StudySiteDTO();
        ssDto.setStudyProtocolIdentifier(trialIi);
        ssDto.setHealthcareFacilityIi(paHcfIi);
        ssDto.setFunctionalCode(CdConverter
                .convertStringToCd(StudySiteFunctionalCode.TREATING_SITE
                        .getCode()));

        List<StudySiteDTO> ssDtoList = new ArrayList<StudySiteDTO>();
        try {
            ssDtoList = this.getStudySiteService().search(ssDto,
                    new LimitOffset(1, 0));
        } catch (TooManyResultsException e) { // NOPMD
            LOG.error(e, e);
        }
        return ssDtoList;
    }
   

    /**
     * Gets the study site dto with the given studySiteIi.
     * @param studySiteIi The study site Ii
     * @return The study site dto with the given studySiteIi
     * @throws PAException if an error occurs
     */
    protected StudySiteDTO getStudySiteDTO(Ii studySiteIi) throws PAException {
        if (ISOUtil.isIiNull(studySiteIi)) {
            throw new PAException("Study site identifier must be provided for update.");
        }
        StudySiteDTO currentSite = getStudySiteService().get(studySiteIi);
        if (currentSite == null) {
            throw new PAException("No participating site found by identifier" + studySiteIi.getExtension());
        }
        return currentSite;
    }

    /**
     * Gets the study site with the given studySiteIi.
     * @param studySiteIi The study site Ii
     * @return The study site with the given studySiteIi.
     */
    protected StudySite getStudySite(Ii studySiteIi) {
        return (StudySite) PaHibernateUtil.getCurrentSession().get(StudySite.class,
                                                                   IiConverter.convertToLong(studySiteIi));
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudySiteDTO getParticipatingSite(Ii studyProtocolID, String orgPoId)
            throws PAException {
        Query qry = PaHibernateUtil.getCurrentSession().createQuery(
                "select ss " + "from StudySite ss "
                        + "join ss.studyProtocol sp "
                        + "join ss.healthCareFacility hf "
                        + "join hf.organization org " + "where sp.id = :spId "
                        + "and ss.functionalCode = :functionalCode "
                        + "and org.identifier = :orgId");
        qry.setParameter("spId", IiConverter.convertToLong(studyProtocolID));
        qry.setParameter("functionalCode",
                StudySiteFunctionalCode.TREATING_SITE);
        qry.setParameter("orgId", orgPoId);
        qry.setMaxResults(1);
        StudySite ss = (StudySite) qry.uniqueResult();
        if (ss != null) {
            return new StudySiteConverter().convertFromDomainToDto(ss);
        } else {
            return null;
        }
    }

    @Override
    public void mergeParicipatingSites(Long srcId, Long destId) throws PAException {
        if (srcId == null || destId == null) {
            throw new PAException("Called ParticipatingSiteServiceBean.mergeParicipatingSites() with null argument.");
        }
        Session sess = PaHibernateUtil.getCurrentSession();
        StudySite src = (StudySite) sess.get(StudySite.class, srcId);
        StudySite dest = (StudySite) sess.get(StudySite.class, destId);
        if (src == null || dest == null) {
            throw new PAException("Site not found when merging participating sites.");
        }
        if (src.getStudyProtocol().getId().longValue() != dest
                .getStudyProtocol().getId().longValue()) {
            throw new PAException(
                    "Trying to merge participating sites from different trials.");
        }
        mergeAccrualCounts(src, dest);
        mergeAccrualSubjects(src, dest);
        sess.flush();
        getStudySiteService().delete(IiConverter.convertToStudySiteIi(src.getId()));
    }

    private void mergeAccrualCounts(StudySite src, StudySite dest) {
        StudySiteSubjectAccrualCount countToMove = src.getAccrualCount();
        if (countToMove != null && countToMove.getAccrualCount() > 0) {
            StudySiteSubjectAccrualCount count = dest.getAccrualCount();
            if (count == null) {
                PaHibernateUtil.getCurrentSession().evict(countToMove);
                count = countToMove;
                count.setStudySite(dest);
                count.setId(null);
            } else {
                count.setAccrualCount(count.getAccrualCount() + countToMove.getAccrualCount());
            }
            PaHibernateUtil.getCurrentSession().save(count);
        }
    }

    private void mergeAccrualSubjects(StudySite src, StudySite dest) {
        if (CollectionUtils.isNotEmpty(src.getStudySubjects())) {
            Set<String> existingIds = new HashSet<String>();
            if (CollectionUtils.isNotEmpty(dest.getStudySubjects())) {
                for (StudySubject subj : dest.getStudySubjects()) {
                    existingIds.add(subj.getAssignedIdentifier());
                }
            }
            for (StudySubject subj : src.getStudySubjects()) {
                if (!existingIds.contains(subj.getAssignedIdentifier())) {
                    subj.setStudySite(dest);
                    PaHibernateUtil.getCurrentSession().save(subj);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Organization> getListOfSitesUserCanUpdate(RegistryUser user,
            Ii studyProtocolID) throws PAException, NullifiedRoleException {
        Long userOrgPoId = user.getAffiliatedOrganizationId();
        List<Long> allFamilyMembersPoIds = FamilyHelper
                .getAllRelatedOrgs(userOrgPoId);
        // If the user is affiliated directly to the Cancer Center, she can
        // update any org from the allFamilyMembers list. Otherwise, remove
        // Cancer Centers from the list.
        if (!isCancerCenter(userOrgPoId)) {
            removeCancerCentersFromCollection(allFamilyMembersPoIds);
        }
        allFamilyMembersPoIds.add(userOrgPoId);

        Query qry = PaHibernateUtil.getCurrentSession().createQuery(
                "select org " + "from StudySite ss "
                        + "inner join ss.studyProtocol sp "
                        + "inner join ss.healthCareFacility hf "
                        + "inner join hf.organization org "
                        + "where sp.id = :spId "
                        + "and ss.functionalCode = :functionalCode "
                        + "and cast(org.identifier as long) in (:orgId)");
        qry.setParameter("spId", IiConverter.convertToLong(studyProtocolID));
        qry.setParameter("functionalCode",
                StudySiteFunctionalCode.TREATING_SITE);
        qry.setParameterList("orgId", allFamilyMembersPoIds);
        return qry.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Organization> getListOfSitesUserCanAdd(RegistryUser user,
            Ii studyProtocolID) throws PAException, NullifiedRoleException {
        PAServiceUtils paServiceUtil = new PAServiceUtils();
        Long userOrgPoId = user.getAffiliatedOrganizationId();
        List<Long> siblingsPoIds = FamilyHelper
                .getAllRelatedOrgs(userOrgPoId);
        Collection<OrganizationDTO> allMembersList = new TreeSet<OrganizationDTO>(
                new Comparator<OrganizationDTO>() {
                    @Override
                    public int compare(OrganizationDTO o1, OrganizationDTO o2) {
                        return IiConverter.convertToLong(o1.getIdentifier())
                                .compareTo(
                                        IiConverter.convertToLong(o2
                                                .getIdentifier()));
                    }
                });
        OrganizationDTO affiliation = paServiceUtil.getPOOrganizationEntity(IiConverter.convertToIi(userOrgPoId));
        allMembersList.add(affiliation);
        for (Long poID : siblingsPoIds) {
            OrganizationDTO sibling = paServiceUtil.getPOOrganizationEntity(IiConverter.convertToIi(poID));
            if (sibling != null) {
                allMembersList.add(sibling);
            }
        }
        List<OrganizationDTO> properlySortedList = new ArrayList<OrganizationDTO>();
        properlySortedList.add(affiliation);
        allMembersList.remove(affiliation);
        properlySortedList.addAll(allMembersList);        
        Collection<OrganizationDTO> candidates = PaRegistry.getParticipatingOrgService()
                .getOrganizationsThatAreNotSiteYet(IiConverter.convertToLong(studyProtocolID), properlySortedList);
       
         List<Organization> orgsThatCanBeAddeddASite = new ArrayList<>(candidates.size());
         for (OrganizationDTO tempOrgDto : candidates) {
             orgsThatCanBeAddeddASite.add(paServiceUtil.getOrCreatePAOrganizationByIi(tempOrgDto.getIdentifier()));
          }
        return orgsThatCanBeAddeddASite;
    }

    private void removeCancerCentersFromCollection(
            final List<Long> allFamilyMembersPoIds) {
        CollectionUtils.filter(allFamilyMembersPoIds, new Predicate() {
            @Override
            public boolean evaluate(Object arg0) {
                try {
                    return !isCancerCenter((Long) arg0);
                } catch (NullifiedRoleException e) {
                    throw new RuntimeException(e); // NOPMD
                }
            }
        });
    }

    private boolean isCancerCenter(Long poOrgId) throws NullifiedRoleException {
        List<ResearchOrganizationDTO> roList = PoRegistry
                .getResearchOrganizationCorrelationService()
                .getCorrelationsByPlayerIds(
                        new Ii[] {IiConverter
                                .convertToPoOrganizationIi(poOrgId.toString()) });
        for (ResearchOrganizationDTO ro : roList) {
            if (StringUtils.equalsIgnoreCase(
                    CdConverter.convertCdToString(ro.getTypeCode()), "CCR")) {
                return true;
            }
        }
        return false;
    }
}
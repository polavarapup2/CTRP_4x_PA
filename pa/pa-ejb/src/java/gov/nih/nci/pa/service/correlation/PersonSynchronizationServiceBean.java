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
package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.PAOrganizationalContactDTO;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.convert.OrganizationalContactConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.CorrelationService;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * Syncrhonization service bean for Person and its structural roles.
 *
 * @author Naveen Amiruddin
 * @since 07/07/2007 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class PersonSynchronizationServiceBean implements PersonSynchronizationServiceLocal {

    private static final Logger LOG = Logger.getLogger(PersonSynchronizationServiceBean.class);
    private static CorrelationUtils cUtils = new CorrelationUtils();
    private static final String STUDY_CONTACT = "STUDY_CONTACT";
    private static final String STUDY_SITE_CONTACT = "STUDY_SITE_CONTACT";
    private static PAServiceUtils paServiceUtil = new PAServiceUtils();
    private CorrelationService correlationService = null;
    @EJB
    private StudyContactServiceLocal scLocal;
    @EJB
    private StudySiteContactServiceLocal spcLocal;

    /**
     *
     * @param perIdentifier ii of Person
     * @throws PAException on error
     */

    @Override
    public void synchronizePerson(final Ii perIdentifier) throws PAException {

        PersonDTO personDto = null;
        try {
            personDto = PoRegistry.getPersonEntityService().getPerson(perIdentifier);
            updatePerson(perIdentifier, personDto);
        } catch (NullifiedEntityException e) {
           LOG.info("This Person is nullified " + perIdentifier.getExtension());
           updatePerson(perIdentifier, null);
        }
    }

    /***
     * @param crsIdentifier po ClinicalResearchStaff identifier
     * @throws PAException on error
     */
    @Override
    public void synchronizeClinicalResearchStaff(final Ii crsIdentifier) throws PAException {
        ClinicalResearchStaffDTO crsDto = null;
        try {
            crsDto = PoRegistry.getClinicalResearchStaffCorrelationService().getCorrelation(crsIdentifier);
            updateClinicalResearchStaff(crsIdentifier , crsDto);
        } catch (NullifiedRoleException e) {
           LOG.info("This ClinicalResearchStaff is nullified " + crsIdentifier.getExtension());
           updateClinicalResearchStaff(crsIdentifier , null);
        }
    }

    /***
     * @param hcpIdentifier po HealthCareProvider identifier
     * @throws PAException on error
     */
    @Override
    public void synchronizeHealthCareProvider(final Ii hcpIdentifier) throws PAException {
        HealthCareProviderDTO hcpDto = null;
        try {
            hcpDto = PoRegistry.getHealthCareProviderCorrelationService().getCorrelation(hcpIdentifier);
            updateHealthCareProvider(hcpIdentifier , hcpDto);
        } catch (NullifiedRoleException e) {
           LOG.info("This HealthCareProvider is nullified " + hcpIdentifier.getExtension());
           updateHealthCareProvider(hcpIdentifier , null);

        }
    }

    /***
     * OrganizationalContact.
     * @param ocIdentifier oc HealthCareProvider identifier
     * @throws PAException on error
     */
    @Override
    public  void synchronizeOrganizationalContact(final Ii ocIdentifier) throws PAException {
        OrganizationalContactDTO ocDto = null;
        try {
            ocDto = PoRegistry.getOrganizationalContactCorrelationService().getCorrelation(ocIdentifier);
            updateOrganizationalContact(ocIdentifier , ocDto);
        } catch (NullifiedRoleException e) {
           LOG.info("This OrganizationalContact is nullified " + ocIdentifier.getExtension());
           updateOrganizationalContact(ocIdentifier , null);
        }
    }

    private void updatePerson(final Ii ii , final PersonDTO perDto) throws PAException {
        Person paPer = cUtils.getPAPersonByIi(ii);

        if (paPer != null) {
            Session session = null;
            session = PaHibernateUtil.getCurrentSession();
            // update the organization
            Person person = (Person) session.get(Person.class, paPer.getId());
            if (perDto == null) {
                // its nullified
                person.setStatusCode(EntityStatusCode.NULLIFIED);
                final Ii dupId = paServiceUtil.getDuplicatePersonIi(ii);

                if (dupId != null) {
                    paServiceUtil.getOrCreatePAPersonByPoIi(dupId);
                    try {
                        updateStudyProtocolStage(ii, dupId);
                    } catch (NullifiedEntityException e) {
                        LOG.error("Org was nullified with nullified duplicate.");
                    }
                }
            } else {
                // that means its not nullified
                paPer = cUtils.convertPOToPAPerson(perDto);
                person.setFirstName(paPer.getFirstName());
                person.setLastName(paPer.getLastName());
                person.setMiddleName(paPer.getMiddleName());
                person.setStatusCode(paPer.getStatusCode());

            }
            person.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            person.setUserLastUpdated(CSMUserService.getInstance().getCSMUser(UsernameHolder.getUser()));
            session.update(person);
            session.flush();
        }
    }

    private void updateStudyProtocolStage(Ii identifier, Ii dupId) throws NullifiedEntityException, PAException {
        Person oldPaPer = cUtils.getPAPersonByIi(identifier);
        Person newPaPer = cUtils.getPAPersonByIi(dupId);
        cUtils.updateItemIdForStudyProtocolStage("piIdentifier", oldPaPer.getIdentifier(),
                newPaPer.getIdentifier());
        cUtils.updateItemIdForStudyProtocolStage("responsibleIdentifier", oldPaPer.getIdentifier(),
                newPaPer.getIdentifier());
        cUtils.updateItemIdForStudyProtocolStage("sitePiIdentifier", oldPaPer.getIdentifier(),
                newPaPer.getIdentifier());
    }

    private void updateClinicalResearchStaff(final Ii crsIdentifier, final ClinicalResearchStaffDTO crsDto)
            throws PAException {
        gov.nih.nci.pa.util.CorrelationUtils commonsCorrUtils = new gov.nih.nci.pa.util.CorrelationUtils();
        ClinicalResearchStaff crs = commonsCorrUtils.getStructuralRoleByIi(crsIdentifier);
        Session session = null;
        StructuralRoleStatusCode newRoleCode = null;
        if (crs != null) {
            // process only if pa has any clinical research staff records
            session = PaHibernateUtil.getCurrentSession();
            if (crsDto == null) {
                // this is a nullified, so treat it in a special manner
                // step 1: get the po person,org identifier (player)
                Long paOrgId = crs.getOrganization().getId();
                Long paPerId = crs.getPerson().getId();
                Long duplicateCrsId = null;
                String poOrgId = cUtils.getPAOrganizationByIi(
                        IiConverter.convertToPaOrganizationIi(paOrgId)).getIdentifier();
                String poPerId = cUtils.getPAPersonByIi(IiConverter.convertToPaPersonIi(paPerId)).getIdentifier();
                PersonDTO personDto = getPoPerson(poPerId);
                OrganizationDTO organizationDto = getPoOrganization(poOrgId);
                if (personDto != null && organizationDto != null) {
                    // create a new crs
                    ClinicalResearchStaffCorrelationServiceBean crsBean =
                        new ClinicalResearchStaffCorrelationServiceBean();
                    duplicateCrsId = crsBean.createClinicalResearchStaffCorrelations(
                            organizationDto.getIdentifier().getExtension(), personDto.getIdentifier().getExtension());
                    ClinicalResearchStaff dupCrs = new ClinicalResearchStaff();
                    dupCrs.setId(duplicateCrsId);
                    dupCrs = cUtils.getStructuralRole(dupCrs);
                    newRoleCode = dupCrs.getStatusCode();
                    // replace the old, with the new change identifiers
                    replaceStudyContactIdentifiers(
                                IiConverter.convertToPoClinicalResearchStaffIi(crs.getId().toString()),
                                IiConverter.convertToPoClinicalResearchStaffIi(duplicateCrsId.toString()) ,
                                STUDY_CONTACT);
                    replaceStudyContactIdentifiers(
                            IiConverter.convertToPoClinicalResearchStaffIi(crs.getId().toString()),
                            IiConverter.convertToPoClinicalResearchStaffIi(duplicateCrsId.toString()) ,
                            STUDY_SITE_CONTACT);
                    // nullify the current
                    crs.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                } else {
                    // this is nullified scenario with no org or person associated, in that case nullify the role
                    crs.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                    newRoleCode = StructuralRoleStatusCode.NULLIFIED;
                }
            } else {
                String poOrgIi = crsDto.getScoperIdentifier().getExtension();
                String paOrgAssignedId = crs.getOrganization().getIdentifier();
                if (StringUtils.isNotEmpty(poOrgIi) && StringUtils.isNotEmpty(paOrgAssignedId)
                        && !poOrgIi.equalsIgnoreCase(paOrgAssignedId)) {
                    //this means scoper is changed. check if exist in PA if not create and update the SR
                    Organization paOrg = paServiceUtil.getOrCreatePAOrganizationByIi(crsDto.getScoperIdentifier());
                    crs.setOrganization(paOrg);
                }
                if (!crs.getStatusCode().equals(cUtils.convertPORoleStatusToPARoleStatus(crsDto.getStatus()))) {
                    // this is a update scenario with a status change
                    newRoleCode = cUtils.convertPORoleStatusToPARoleStatus(crsDto.getStatus());
                    crs.setStatusCode(newRoleCode);
                }

            }
            crs.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            session.update(crs);
            session.flush();
            scLocal.cascadeRoleStatus(crsIdentifier, CdConverter.convertToCd(newRoleCode));
            spcLocal.cascadeRoleStatus(crsIdentifier, CdConverter.convertToCd(newRoleCode));
        } //if (crsDto == null)
    }

    private void updateHealthCareProvider(final Ii hcpIdentifier, final HealthCareProviderDTO hcpDto)
            throws PAException {
        gov.nih.nci.pa.util.CorrelationUtils commonsCorrUtils = new gov.nih.nci.pa.util.CorrelationUtils();
        HealthCareProvider hcp = commonsCorrUtils.getStructuralRoleByIi(hcpIdentifier);
        if (hcp != null) {
            Session session = PaHibernateUtil.getCurrentSession();
            StructuralRoleStatusCode newRoleCode = null;
            if (hcpDto == null) {
                // this is a nullified, so treat it in a special manner
                // step 1: get the po person,org identifier (player)
                Long paOrgId = hcp.getOrganization().getId();
                Long paPerId = hcp.getPerson().getId();
                Long duplicateHcpId = null;
                String poOrgId = cUtils.getPAOrganizationByIi(
                        IiConverter.convertToPaOrganizationIi(paOrgId)).getIdentifier();
                String poPerId = cUtils.getPAPersonByIi(IiConverter.convertToPaPersonIi(paPerId)).getIdentifier();
                PersonDTO personDto = getPoPerson(poPerId);
                OrganizationDTO organizationDto = getPoOrganization(poOrgId);
                if (personDto != null && organizationDto != null) {
                    HealthCareProviderCorrelationBean hcpBean = new HealthCareProviderCorrelationBean();
                    duplicateHcpId = hcpBean.createHealthCareProviderCorrelationBeans(
                            organizationDto.getIdentifier().getExtension(), personDto.getIdentifier().getExtension());
                    HealthCareProvider dupHcp = new HealthCareProvider();
                    dupHcp.setId(duplicateHcpId);
                    dupHcp = cUtils.getStructuralRole(dupHcp);
                    newRoleCode = dupHcp.getStatusCode();
                    // replace the old, with the new change identifiers
                    replaceStudyContactIdentifiers(IiConverter.convertToPoHealthcareProviderIi(hcp.getId().toString()),
                                                   IiConverter.convertToPoHealthcareProviderIi(duplicateHcpId
                                                       .toString()), STUDY_CONTACT);
                    replaceStudyContactIdentifiers(IiConverter.convertToPoHealthcareProviderIi(hcp.getId().toString()),
                                                   IiConverter.convertToPoHealthcareProviderIi(duplicateHcpId
                                                       .toString()), STUDY_SITE_CONTACT);
                    // nullify the current
                    hcp.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                } else {
                    // this is nullified scenario with no org or person associated, in that case nullify the role
                    hcp.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                    newRoleCode = StructuralRoleStatusCode.NULLIFIED;
                }
            } else {
                String poOrgIi = hcpDto.getScoperIdentifier().getExtension();
                String paOrgAssignedId = hcp.getOrganization().getIdentifier();
                if (StringUtils.isNotEmpty(poOrgIi) && StringUtils.isNotEmpty(paOrgAssignedId)
                        && !poOrgIi.equalsIgnoreCase(paOrgAssignedId)) {
                    //this means scoper is changed. check if exist in PA if not create and update the SR
                    Organization paOrg = paServiceUtil.getOrCreatePAOrganizationByIi(hcpDto.getScoperIdentifier());
                    hcp.setOrganization(paOrg);
                }

                if (!hcp.getStatusCode().equals(cUtils.convertPORoleStatusToPARoleStatus(hcpDto.getStatus()))) {
                // this is a update scenario with a status change
                newRoleCode = cUtils.convertPORoleStatusToPARoleStatus(hcpDto.getStatus());
                hcp.setStatusCode(newRoleCode);
                }

            }
            hcp.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            session.update(hcp);
            session.flush();
            scLocal.cascadeRoleStatus(hcpIdentifier, CdConverter.convertToCd(newRoleCode));
            spcLocal.cascadeRoleStatus(hcpIdentifier, CdConverter.convertToCd(newRoleCode));
        }
    }

    private void updateOrganizationalContact(final Ii ocIdentifier, final OrganizationalContactDTO ocDto)
            throws PAException {
        OrganizationalContact oc = getPAOrganizationalContact(ocIdentifier.getExtension());

        Session session = null;
        StructuralRoleStatusCode newRoleCode = null;
        if (oc != null) {
            // process only if pa has any oc  records
            session = PaHibernateUtil.getCurrentSession();
            if (ocDto == null) {
                Ii srIi = null;
                //nullified without duplicate
                //so check if Entity or SR are not nullified
                //if nullified just update the Status to nullified in PA
                // this is a nullified, so treat it in a special manner
                // step 1: get the po person,org identifier (player)
                Long duplicateOcId = null;
                try {
                    correlationService = paServiceUtil.getPoService(ocIdentifier);
                    correlationService.getCorrelation(ocIdentifier);
                } catch (NullifiedRoleException e) {
                  LOG.info("Nullified Role exception for Organizational Contact for id " + oc.getIdentifier());
                  // SR is nullified, find out if it has any duplicates
                  Ii nullfiedIi = null;
                  Map<Ii, Ii> nullifiedEntities = e.getNullifiedEntities();
                  for (Ii tmp : nullifiedEntities.keySet()) {
                      if (tmp.getExtension().equals(ocIdentifier.getExtension())) {
                          nullfiedIi = tmp;
                      }
                  }
                  if (nullfiedIi != null) {
                      srIi = nullifiedEntities.get(nullfiedIi);
                  }
                  PABaseCorrelation<PAOrganizationalContactDTO , OrganizationalContactDTO ,
                  OrganizationalContact , OrganizationalContactConverter> ocBean = new
                      PABaseCorrelation<PAOrganizationalContactDTO ,
                      OrganizationalContactDTO , OrganizationalContact , OrganizationalContactConverter>(
                      PAOrganizationalContactDTO.class, OrganizationalContact.class,
                      OrganizationalContactConverter.class);

                  if (!ISOUtil.isIiNull(srIi)) {
                      //nullified with Duplicate
                      OrganizationalContactDTO poOrgContactDto = (OrganizationalContactDTO)
                      paServiceUtil.getCorrelationByIi(srIi);
                      PAOrganizationalContactDTO orgContacPaDto = new PAOrganizationalContactDTO();
                      orgContacPaDto.setOrganizationIdentifier(poOrgContactDto.getScoperIdentifier());
                      if (!ISOUtil.isIiNull(poOrgContactDto.getPlayerIdentifier())) {
                          orgContacPaDto.setPersonIdentifier(poOrgContactDto.getPlayerIdentifier());
                      }
                      orgContacPaDto.setIdentifier(DSetConverter.convertToIi(poOrgContactDto.getIdentifier()));
                      duplicateOcId = ocBean.create(orgContacPaDto);
                      replaceStudyContactIdentifiers(IiConverter.convertToPoOrganizationalContactIi(
                              oc.getId().toString()), IiConverter.convertToPoOrganizationalContactIi(
                                      duplicateOcId.toString()), STUDY_SITE_CONTACT);
                      oc.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                  } else {
                      String poOrgId = oc.getOrganization().getIdentifier();
                      PersonDTO personDto = null;
                      String poPerId = null;
                      OrganizationDTO organizationDto = getPoOrganization(poOrgId);

                      if (oc.getPerson() != null && StringUtils.isNotEmpty(oc.getPerson().getIdentifier())) {
                          poPerId = oc.getPerson().getIdentifier();
                          personDto = getPoPerson(poPerId);
                      }
                      if (organizationDto != null && personDto != null) {
                          //We're just going to nullify the current OC and let the places that previously showed it
                          //show up as Nullified.
                          oc.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                      } else {
                          // this is nullified scenario with no org or person associated, in that case nullify the role
                          // or this can also mean the Player was SR
                          oc.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
                          newRoleCode = StructuralRoleStatusCode.NULLIFIED;
                      }
                  }
                } //end if (ocDto == null)
            } else {
                String poOrgIi = ocDto.getScoperIdentifier().getExtension();
                String paOrgAssignedId = oc.getOrganization().getIdentifier();
                if (StringUtils.isNotEmpty(poOrgIi) && StringUtils.isNotEmpty(paOrgAssignedId)
                        && !poOrgIi.equalsIgnoreCase(paOrgAssignedId)) {
                    //this means scoper is changed. check if exist in PA if not create and update the SR
                    Organization paOrg = paServiceUtil.getOrCreatePAOrganizationByIi(ocDto.getScoperIdentifier());
                    oc.setOrganization(paOrg);
                }
                if (!oc.getStatusCode().equals(cUtils.convertPORoleStatusToPARoleStatus(ocDto.getStatus()))) {
                    // this is a update scenario with a status change
                    newRoleCode = cUtils.convertPORoleStatusToPARoleStatus(ocDto.getStatus());
                    oc.setStatusCode(newRoleCode);
                }
            }
            oc.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            session.update(oc);
            session.flush();
            spcLocal.cascadeRoleStatus(ocIdentifier, CdConverter.convertToCd(newRoleCode));
        }
    }

    private void replaceStudyContactIdentifiers(final Ii fromId  , final Ii toId , final String tableName) {
        String sql = null;
        if (IiConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME.equals(fromId.getIdentifierName())) {
            sql = "update " + tableName + " set clinical_research_staff_identifier = " + toId.getExtension()
            + " where clinical_research_staff_identifier = " + fromId.getExtension();
        }
        if (IiConverter.HEALTH_CARE_PROVIDER_IDENTIFIER_NAME.equals(fromId.getIdentifierName())) {
            sql = "update " + tableName + " set healthcare_provider_identifier = " + toId.getExtension()
            + " where healthcare_provider_identifier = " + fromId.getExtension();
        }
        if (IiConverter.ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME.equals(fromId.getIdentifierName())) {
            sql = "update " + tableName + " set organizational_contact_identifier = " + toId.getExtension()
            + " where organizational_contact_identifier = " + fromId.getExtension();
        }

        Session session = PaHibernateUtil.getCurrentSession();
        int count = session.createSQLQuery(sql).executeUpdate();
        LOG.info("nullified crs indentifier is " + fromId.getExtension());
        LOG.info("duplicate hcf indentifier is " + toId.getExtension());
        LOG.info("total records got update in  " + tableName + " is " + count);
    }


    private PersonDTO getPoPerson(final String poPersonId) throws PAException {
        PersonDTO personDto = null;
        Ii personIi = null;
        try {
            personDto = PoRegistry.getPersonEntityService().getPerson(
                     IiConverter.convertToPoPersonIi(poPersonId));
        } catch (NullifiedEntityException e) {
               // Person is nullified, find out if it has any duplicates

            Ii nullfiedIi = null;
            Map<Ii, Ii> nullifiedEntities = e.getNullifiedEntities();
            for (Ii tmp : nullifiedEntities.keySet()) {
                if (tmp.getExtension().equals(poPersonId)) {
                    nullfiedIi = tmp;
                }
            }
            if (nullfiedIi != null) {
              personIi = nullifiedEntities.get(nullfiedIi);
            }

            if (personIi != null) {
                   try {
                       personDto = PoRegistry.getPersonEntityService().getPerson(personIi);
                   } catch (NullifiedEntityException e1) {
                       // TODO refactor the code to handle chain of nullified entities ... Naveen Amiruddin
                       throw new PAException("This scenario is currently not handled .... "
                               + "Duplicate Ii of nullified is also nullified", e1);
                   }
               }
       }
       return personDto;
    }

    private OrganizationDTO getPoOrganization(final  String poOrganizationId) throws PAException {
        OrganizationDTO organizationDto = null;
        Ii organizationIi = null;
        try {
            Ii lookupPoIi = IiConverter.convertToPoOrganizationIi(poOrganizationId);
            organizationDto = PoRegistry.getOrganizationEntityService().getOrganization(lookupPoIi);
        } catch (NullifiedEntityException e) {
               // org is nullified, find out if it has any duplicates
            Ii nullfiedIi = null;
            Map<Ii, Ii> nullifiedEntities = e.getNullifiedEntities();
            for (Ii tmp : nullifiedEntities.keySet()) {
                if (tmp.getExtension().equals(poOrganizationId)) {
                    nullfiedIi = tmp;
                }
            }
            if (nullfiedIi != null) {
               organizationIi = nullifiedEntities.get(nullfiedIi);
            }
            if (organizationIi != null) {
                   try {
                       organizationDto = PoRegistry.getOrganizationEntityService().getOrganization(organizationIi);
                   } catch (NullifiedEntityException e1) {
                       // TODO refactor the code to handle chain of nullified entities ... Naveen Amiruddin
                       throw new PAException("This scenario is currently not handled .... "
                               + "Duplicate Ii of nullified is also nullified" , e1);
                   }
               }
       }
       return organizationDto;
    }
    /**
    *
    * @param oc oc
    * @return oc
    * @throws PAException pe
    */

   @SuppressWarnings("unchecked")
   private OrganizationalContact getPAOrganizationalContact(String identifier)
   throws PAException {
       if (identifier == null) {
           LOG.error("Clinicial Research Staff cannot be null");
           throw new PAException("Clinicial Research Staff cannot be null");
       }
       OrganizationalContact ocOut = null;
       Session session = null;
       List<OrganizationalContact> queryList = new ArrayList<OrganizationalContact>();
       StringBuffer hql = new StringBuffer();

       hql.append(" select oc from OrganizationalContact oc  "
               + " where oc.identifier = :identifier");
       session = PaHibernateUtil.getCurrentSession();
       Query query = null;
       query = session.createQuery(hql.toString());
       query.setParameter("identifier", identifier);
       queryList = query.list();

       if (queryList.size() > 1) {
           LOG.error(" Clinical Reasrch Staff should be more than 1 for any given criteria");
           throw new PAException(" Clinical Reasrch Staff should be more than 1 for any given criteria");

       }

       if (!queryList.isEmpty()) {
           ocOut = queryList.get(0);
       }
       return ocOut;
   }

    /**
     * @param scLocal the scLocal to set
     */
    public void setScLocal(StudyContactServiceLocal scLocal) {
        this.scLocal = scLocal;
    }

    /**
     * @param spcLocal the spcLocal to set
     */
    public void setSpcLocal(StudySiteContactServiceLocal spcLocal) {
        this.spcLocal = spcLocal;
    }
}

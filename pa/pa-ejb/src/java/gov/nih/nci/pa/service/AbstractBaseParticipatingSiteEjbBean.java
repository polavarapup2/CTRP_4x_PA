/**
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
package gov.nih.nci.pa.service; // NOPMD

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.status.StatusTransitionServiceLocal;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.AbstractRoleDTO;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.apache.commons.collections.CollectionUtils;

/**
 * Ejbs getters/setters for part site bean.
 * @author mshestopalov
 *
 */
public class AbstractBaseParticipatingSiteEjbBean {
    private CorrelationUtils corrUtils = new CorrelationUtils();
    @EJB
    private OrganizationCorrelationServiceRemote organizationCorrelationService;
    @EJB
    private StudyProtocolServiceLocal studyProtocolService;
    @EJB
    private StudySiteServiceLocal studySiteService;
    @EJB
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;
    @EJB
    private StudySiteContactServiceLocal studySiteContactService;
    @EJB
    private StudySiteAccrualAccessServiceLocal accrualAccessServiceLocal;
    @EJB
    private StatusTransitionServiceLocal statusTransitionService;
    @EJB
    private MailManagerServiceLocal mailManagerService;
    @EJB
    private ProtocolQueryServiceLocal protocolQueryService;
    

    /**
     * generateCrsAndHcpFromCtepIdOrNewPerson.
     * @param investigatorDTO pi
     * @param crsDTO dto
     * @param hcpDTO dto
     * @param orgIi ii
     * @return map
     * @throws EntityValidationException when error
     * @throws CurationException when error
     * @throws PAException when error
     */
    protected Map<String, Ii> generateCrsAndHcpFromCtepIdOrNewPerson(PersonDTO investigatorDTO,
            ClinicalResearchStaffDTO crsDTO, HealthCareProviderDTO hcpDTO, Ii orgIi) throws EntityValidationException,
            CurationException, PAException {

        Ii someCrsIi = extractIdentifer(crsDTO);
        Ii someHcpIi = extractIdentifer(hcpDTO);

        Map<String, Ii> myMap = new HashMap<String, Ii>();
        myMap.put(IiConverter.ORG_ROOT, orgIi);

        loadPersonInMapByHcp(someHcpIi, myMap);
        loadPersonInMapByCrs(someCrsIi, myMap);

        boolean isSrEmpty = (someCrsIi == null && someHcpIi == null);
        loadOrCreatePerson(myMap, crsDTO, hcpDTO, investigatorDTO, isSrEmpty);

        return myMap;

    }

    private void loadPersonInMapByHcp(Ii someHcpIi, Map<String, Ii> myMap) throws PAException,
            EntityValidationException, CurationException {
        if (someHcpIi != null) {
            if (IiConverter.HEALTH_CARE_PROVIDER_ROOT.equals(someHcpIi.getRoot())) {
                myMap.put(IiConverter.HEALTH_CARE_PROVIDER_ROOT, someHcpIi);
            }
            try {
                HealthCareProviderDTO freshHcpDTO =
                    PoRegistry.getHealthCareProviderCorrelationService().getCorrelation(someHcpIi);
                if (freshHcpDTO != null) {
                    myMap.put(IiConverter.PERSON_ROOT, freshHcpDTO.getPlayerIdentifier());
                } else {
                    throw new PAException("Could not find a Health Care Provider with id = " + someHcpIi.getExtension()
                            + " in PO db.");
                }
            } catch (NullifiedRoleException e) {
                throw new PAException(PAUtil.handleNullifiedRoleException(e));
            }
        }
    }

    private void loadPersonInMapByCrs(Ii someCrsIi, Map<String, Ii> myMap) throws PAException,
            EntityValidationException, CurationException {
        if (someCrsIi != null) {
            if (IiConverter.CLINICAL_RESEARCH_STAFF_ROOT.equals(someCrsIi.getRoot())) {
                myMap.put(IiConverter.CLINICAL_RESEARCH_STAFF_ROOT, someCrsIi);
            }
            try {
                ClinicalResearchStaffDTO freshCrsDTO = PoRegistry.getClinicalResearchStaffCorrelationService()
                    .getCorrelation(someCrsIi);
                if (freshCrsDTO != null) {
                    myMap.put(IiConverter.PERSON_ROOT, freshCrsDTO.getPlayerIdentifier());
                } else {
                    throw new PAException("Could not find a Clinical Research Staff with id = "
                            + someCrsIi.getExtension() + " in PO db.");
                }
            } catch (NullifiedRoleException e) {
                throw new PAException(PAUtil.handleNullifiedRoleException(e));
            }
        }
    }

    private Ii extractIdentifer(AbstractRoleDTO abstractRoleDTO) {
        Ii ii = null;
        if (abstractRoleDTO != null) {
            ii = DSetConverter.convertToIi(abstractRoleDTO.getIdentifier());
            if (ii == null) {
                ii = DSetConverter.convertToCTEPPersonIi(abstractRoleDTO.getIdentifier());
            }
        }
        return ii;
    }

    private void loadOrCreatePerson(Map<String, Ii> myMap, ClinicalResearchStaffDTO crsDTO,
            HealthCareProviderDTO hcpDTO, PersonDTO investigatorDTO, boolean isSrEmpty)
    throws EntityValidationException, CurationException, PAException {

        if (isSrEmpty && investigatorDTO != null) {
            Ii poPersonIi = getPoPersonIiFromPoPersonDTO(investigatorDTO);
            myMap.put(IiConverter.PERSON_ROOT, poPersonIi);
            myMap.put(IiConverter.CLINICAL_RESEARCH_STAFF_ROOT, createPoCrs(poPersonIi,
                    myMap.get(IiConverter.ORG_ROOT), crsDTO));
            myMap.put(IiConverter.HEALTH_CARE_PROVIDER_ROOT, createPoHcp(poPersonIi,
                    myMap.get(IiConverter.ORG_ROOT), hcpDTO));
        } else if (isSrEmpty && investigatorDTO == null) {
            throw new PAException("Expecting either full person dto or po crs and po hcp ii.");
        }
    }

    private Ii getPoPersonIiFromPoPersonDTO(PersonDTO investigatorDTO) throws EntityValidationException,
            CurationException, PAException {
        Ii poPersonIi = null;
        final PersonEntityServiceRemote personService = PoRegistry.getPersonEntityService();
        if (!ISOUtil.isIiNull(investigatorDTO.getIdentifier())
                && IiConverter.PERSON_ROOT.equals(investigatorDTO.getIdentifier().getRoot())) {
            PersonDTO personDTO = null;
            try {
                personDTO = personService.getPerson(investigatorDTO.getIdentifier());
            } catch (NullifiedEntityException e) {
                throw new PAException(PAUtil.handleNullifiedEntityException(e));
            }

            if (personDTO == null) {
                throw new PAException("Unable to find Person for identifier: "
                        + investigatorDTO.getIdentifier().getExtension());
            }
            poPersonIi = personDTO.getIdentifier();
        } else if (!ISOUtil.isIiNull(investigatorDTO.getIdentifier())) {
            IdentifiedPersonDTO idP = new IdentifiedPersonDTO();
            idP.setAssignedId(investigatorDTO.getIdentifier());
            List<IdentifiedPersonDTO> idps = PoRegistry.getIdentifiedPersonEntityService().search(idP);
            if (idps.isEmpty()) {
                throw new PAException(" Cannot find Identified Person for root / extension: "
                        + investigatorDTO.getIdentifier().getRoot() + " / "
                        + investigatorDTO.getIdentifier().getExtension());
            } else if (idps.size() > 1) {
                throw new PAException("more than 1 Identified Person found for given ID: "
                        + investigatorDTO.getIdentifier().getExtension());
            }
            poPersonIi = idps.get(0).getPlayerIdentifier();
        } else {
            boolean autoCurate = new PAServiceUtils().isAutoCurationEnabled();
            investigatorDTO.setStatusCode(autoCurate ? CdConverter
                    .convertStringToCd("ACTIVE") : CdConverter
                    .convertStringToCd(null));
            poPersonIi = personService.createPerson(investigatorDTO);
        }
        return poPersonIi;
    }

    private Ii createPoCrs(Ii personIi, Ii orgIi, ClinicalResearchStaffDTO toStoreDTO) throws PAException {
        Ii poCrsIi = null;
        ClinicalResearchStaffDTO crsDTO = null;
        if (toStoreDTO == null) {
            crsDTO = new ClinicalResearchStaffDTO();
        } else {
            crsDTO = toStoreDTO;
        }
        crsDTO.setIdentifier(null);
        crsDTO.setPlayerIdentifier(IiConverter.convertToPoPersonIi(personIi.getExtension()));
        crsDTO.setScoperIdentifier(orgIi);
        try {
                poCrsIi = checkExistingCrs(crsDTO);
        } catch (NullifiedRoleException e) {
                throw new PAException(PAUtil.handleNullifiedRoleException(e));
        } catch (EntityValidationException e) {
                throw new PAException("Validation exception during create ClinicalResearchStaff " , e);
        } catch (CurationException e) {
                throw new PAException("CurationException during create ClinicalResearchStaff " , e);
        }

        return poCrsIi;
    }

    private Ii checkExistingCrs(ClinicalResearchStaffDTO crsDTO) throws PAException, EntityValidationException,
            CurationException, NullifiedRoleException {
        final ClinicalResearchStaffCorrelationServiceRemote service = PoRegistry
                .getClinicalResearchStaffCorrelationService();
        List<ClinicalResearchStaffDTO> crsList = service.search(crsDTO);
        ClinicalResearchStaffDTO freshDTO = null;
        if (CollectionUtils.isEmpty(crsList)) {
            Ii ii = new PAServiceUtils().isAutoCurationEnabled() ? service
                    .createActiveCorrelation(crsDTO) : service
                    .createCorrelation(crsDTO);
            freshDTO = service.getCorrelation(ii);
        } else if (crsList.size() > 1) {
            throw new PAException("For a given Person id " + crsDTO.getPlayerIdentifier().getExtension()
                    + " and Organization id " + crsDTO.getScoperIdentifier()
                    + " more than 1 clinical research staff were found.");
        } else {
            freshDTO = crsList.get(0);
        }
        return DSetConverter.convertToIi(freshDTO.getIdentifier());
    }

    private Ii checkExistingHcp(HealthCareProviderDTO hcpDTO) throws PAException, EntityValidationException,
            CurationException, NullifiedRoleException {
        final HealthCareProviderCorrelationServiceRemote service = PoRegistry.getHealthCareProviderCorrelationService();
        List<HealthCareProviderDTO> hcpList = service.search(hcpDTO);
        HealthCareProviderDTO freshDTO = null;
        if (CollectionUtils.isEmpty(hcpList)) {
            Ii ii = new PAServiceUtils().isAutoCurationEnabled() ? service
                    .createActiveCorrelation(hcpDTO) : service
                    .createCorrelation(hcpDTO);
            freshDTO = service.getCorrelation(ii);
        } else if (hcpList.size() > 1) {
            throw new PAException("For a given Person id " + hcpDTO.getPlayerIdentifier().getExtension()
                    + " and Organization id " + hcpDTO.getScoperIdentifier()
                    + " more than 1 health care provider were found.");
        } else {
            freshDTO = hcpList.get(0);
        }
        return DSetConverter.convertToIi(freshDTO.getIdentifier());
    }

    private Ii createPoHcp(Ii personIi, Ii orgIi, HealthCareProviderDTO toStoreDTO) throws PAException {
        Ii poHcpIi = null;
        HealthCareProviderDTO hcpDTO = null;
        if (toStoreDTO == null) {
            hcpDTO = new HealthCareProviderDTO();
        } else {
            hcpDTO = toStoreDTO;
        }
        hcpDTO.setIdentifier(null);
        hcpDTO.setPlayerIdentifier(IiConverter.convertToPoPersonIi(personIi.getExtension()));
        hcpDTO.setScoperIdentifier(orgIi);
        try {
                poHcpIi = checkExistingHcp(hcpDTO);
        } catch (NullifiedRoleException e) {
                throw new PAException(PAUtil.handleNullifiedRoleException(e));
        } catch (EntityValidationException e) {
                throw new PAException("Validation exception during create HealthCareProvider " , e);
        } catch (CurationException e) {
                throw new PAException("CurationException during create HealthCareProvider " , e);
        }

        return poHcpIi;
    }

    /**
     * @return the corrUtils
     */
    protected CorrelationUtils getCorrUtils() {
        return corrUtils;
    }

    /**
     * @param corrUtils the corrUtils to set
     */
    protected void setCorrUtils(CorrelationUtils corrUtils) {
        this.corrUtils = corrUtils;
    }

    /**
     * @return the organizationCorrelationServiceRemote
     */
    protected OrganizationCorrelationServiceRemote getOrganizationCorrelationService() {
        return organizationCorrelationService;
    }

    /**
     * @param organizationCorrelationService the organizationCorrelationService to set
     */
    protected void setOrganizationCorrelationService(
            OrganizationCorrelationServiceRemote organizationCorrelationService) {
        this.organizationCorrelationService = organizationCorrelationService;
    }

    /**
     * @return the studyProtocolService
     */
    protected StudyProtocolServiceLocal getStudyProtocolService() {
        return studyProtocolService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    protected void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * @return the studySiteService
     */
    protected StudySiteServiceLocal getStudySiteService() {
        return studySiteService;
    }

    /**
     * @param studySiteService the studySiteService to set
     */
    protected void setStudySiteService(StudySiteServiceLocal studySiteService) {
        this.studySiteService = studySiteService;
    }

    /**
     * @return the studySiteAccrualStatusService
     */
    protected StudySiteAccrualStatusServiceLocal getStudySiteAccrualStatusService() {
        return studySiteAccrualStatusService;
    }

    /**
     * @param studySiteAccrualStatusService the studySiteAccrualStatusService to set
     */
    protected void setStudySiteAccrualStatusService(StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService) {
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
    }

    /**
     * @return the studySiteContactService
     */
    protected StudySiteContactServiceLocal getStudySiteContactService() {
        return studySiteContactService;
    }

    /**
     * @param studySiteContactService the studySiteContactService to set
     */
    protected void setStudySiteContactService(StudySiteContactServiceLocal studySiteContactService) {
        this.studySiteContactService = studySiteContactService;
    }

    /**
     * @return the accrualAccessServiceLocal
     */
    public StudySiteAccrualAccessServiceLocal getAccrualAccessServiceLocal() {
        return accrualAccessServiceLocal;
    }

    /**
     * @param accrualAccessServiceLocal the accrualAccessServiceLocal to set
     */
    public void setAccrualAccessServiceLocal(
            StudySiteAccrualAccessServiceLocal accrualAccessServiceLocal) {
        this.accrualAccessServiceLocal = accrualAccessServiceLocal;
    }

    /**
     * @return the statusTransitionService
     */
    public StatusTransitionServiceLocal getStatusTransitionService() {
        return statusTransitionService;
    }

    /**
     * @param statusTransitionService the statusTransitionService to set
     */
    public void setStatusTransitionService(
            StatusTransitionServiceLocal statusTransitionService) {
        this.statusTransitionService = statusTransitionService;
    }

    /**
     * @return the mailManagerService
     */
    public MailManagerServiceLocal getMailManagerService() {
        return mailManagerService;
    }

    /**
     * @param mailManagerService the mailManagerService to set
     */
    public void setMailManagerService(MailManagerServiceLocal mailManagerService) {
        this.mailManagerService = mailManagerService;
    }

    /**
     * @return protocolQueryService
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }

    /**
     * @param protocolQueryService protocolQueryService
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }
}

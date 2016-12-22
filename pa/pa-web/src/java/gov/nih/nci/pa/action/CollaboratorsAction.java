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
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.ParticipatingOrganizationsTabWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.CorrelationUtilsRemote;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;

/**
 * Action class for viewing and editing the collaborating organizations.
 *
 * @author Hugh Reinhart
 * @since 08/20/2008
 */
@SuppressWarnings("PMD.TooManyMethods")
public class CollaboratorsAction extends AbstractMultiObjectDeleteAction implements Preparable {

    private static final long serialVersionUID = 123412653L;
    private static final int AD_CITY_IDX = 1;
    private static final int AD_COUNTRY_IDX = 3;
    private static final int AD_ZIP_IDX = 2;
    private static final String ACT_FACILITY_SAVE = "facilitySave";
    private static final String ACT_EDIT = "edit";
    private static final String ACT_DELETE = "delete";
    private static final String ACT_CREATE = "create";

    private StudySiteServiceLocal sPartService;
    private OrganizationCorrelationServiceRemote ocService;
    private CorrelationUtilsRemote correlationUtils;
    private List<CountryRegAuthorityDTO> countryRegDTO;
    private Ii spIi;
    private List<PaOrganizationDTO> organizationList = null;
    private OrganizationDTO selectedOrgDTO = null;
    private static final String DISPLAYJSP = "displayJsp";
    private Long cbValue;
    private String functionalCode;
    private String currentAction;
    private PaOrganizationDTO orgFromPO = new PaOrganizationDTO();
    private String statusCode;
    private Organization editOrg;
    

    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        sPartService = PaRegistry.getStudySiteService();
        ocService = PaRegistry.getOrganizationCorrelationService();
        correlationUtils = new CorrelationUtils();

        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext.getRequest().getSession()
            .getAttribute(Constants.TRIAL_SUMMARY);

        spIi = IiConverter.convertToStudyProtocolIi(spDTO.getStudyProtocolId());
    }

    /**
     * @return Action result.
     * @throws PAException exception.
     */
    @Override
    public String execute() throws PAException {
        loadForm();
        return SUCCESS;
    }

    /**
     * @return result
     */
    public String create() {
        setCurrentAction(ACT_CREATE);
        return ACT_CREATE;
    }

    /**
     * @return result
     * @throws PAException exception
     */
    public String facilitySave() throws PAException {
        clearErrorsAndMessages();

        HttpServletRequest request = ServletActionContext.getRequest();
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) request.getSession()
            .getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        if (tab == null) {
            addActionError("You must select an organization.");
            setCurrentAction(ACT_CREATE);
            return ACT_CREATE;
        }
        if (StringUtils.isEmpty(getFunctionalCode())) {
            addActionError("You must select a functional role.");
            setCurrentAction(ACT_CREATE);
            return ACT_CREATE;
        }
        String poOrgId = tab.getFacilityOrganization().getIdentifier();
        Long paOrgId = ocService.createResearchOrganizationCorrelations(poOrgId);

        StudySiteDTO sp = new StudySiteDTO();
        sp.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.ACTIVE));
        sp.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.getByCode(functionalCode)));
        sp.setHealthcareFacilityIi(null);
        sp.setResearchOrganizationIi(IiConverter.convertToIi(paOrgId));
        sp.setIdentifier(null);
        sp.setStudyProtocolIdentifier(spIi);
        try {
            sp = sPartService.create(sp);
        } catch (PADuplicateException e) {
            addActionError(e.getMessage());
            setCurrentAction(ACT_CREATE);
            return ACT_CREATE;
        }
        request.setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
        loadForm();
        return ACT_FACILITY_SAVE;
    }

    /**
     * @return result
     * @throws PAException exception
     */
    public String facilityUpdate() throws PAException {
        clearErrorsAndMessages();

        HttpServletRequest request = ServletActionContext.getRequest();
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) request.getSession()
            .getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        if (tab == null) {
            loadForm();
            addActionError("System error getting participating orgainzation data from session.");
            return SUCCESS;
        }
        if (StringUtils.isBlank(getFunctionalCode())) {
            addActionError("You must select a functional role.");
            setCurrentAction(ACT_EDIT);
            return ACT_EDIT;
        }
        StudySiteDTO sp = sPartService.get(IiConverter.convertToStudySiteIi(tab.getStudyParticipationId()));
        sp.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.getByCode(functionalCode)));
        try {
            sp = sPartService.update(sp);
        } catch (PAException e) {
            addActionError(e.getMessage());
            return SUCCESS;
        }
        request.setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        loadForm();
        return ACT_FACILITY_SAVE;
    }

    /**
     * @return result
     * @throws PAException exception
     */
    public String edit() throws PAException {
        setCurrentAction(ACT_EDIT);
        StudySiteDTO spDto = sPartService.get(IiConverter.convertToStudySiteIi(cbValue));
        setEditOrg(correlationUtils.getPAOrganizationByIi(spDto.getResearchOrganizationIi()));
        orgFromPO.setCity(getEditOrg().getCity());
        orgFromPO.setCountry(editOrg.getCountryName());
        orgFromPO.setName(editOrg.getName());
        orgFromPO.setZip(editOrg.getPostalCode());
        setFunctionalCode(spDto.getFunctionalCode().getCode());
        setStatusCode(spDto.getStatusCode().getCode());
        ParticipatingOrganizationsTabWebDTO tab = new ParticipatingOrganizationsTabWebDTO();
        tab.setStudyParticipationId(cbValue);
        tab.setFacilityOrganization(null);
        tab.setResearchOrganization(editOrg);
        tab.setPoHealthCareFacilityIi(null);
        tab.setPoOrganizationIi(null);
        tab.setNewParticipation(false);

        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        return ACT_EDIT;
    }

    /**
     * @return result
     * @throws PAException on error
     */
    public String delete() throws PAException {
        clearErrorsAndMessages();
        try {
            deleteSelectedObjects();
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.MULTI_DELETE_MESSAGE);
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        loadForm();
        return ACT_DELETE;
    }
    
    @Override
    public void deleteObject(Long objectId) throws PAException {
        sPartService.delete(IiConverter.convertToStudySiteIi(objectId));        
    }

    private void loadForm() throws PAException {
        ArrayList<StudySiteDTO> criteriaList = new ArrayList<StudySiteDTO>();
        for (StudySiteFunctionalCode cd : StudySiteFunctionalCode.values()) {
            if (cd.isCollaboratorCode()) {
                StudySiteDTO searchCode = new StudySiteDTO();
                searchCode.setFunctionalCode(CdConverter.convertToCd(cd));
                criteriaList.add(searchCode);
            }
        }
        organizationList = new ArrayList<PaOrganizationDTO>();
        List<StudySiteDTO> spList = sPartService.getByStudyProtocol(spIi, criteriaList);
        for (StudySiteDTO sp : spList) {
            Organization orgBo = correlationUtils.getPAOrganizationByIi(sp.getResearchOrganizationIi());
            PaOrganizationDTO orgWebDTO = new PaOrganizationDTO();
            orgWebDTO.setId(IiConverter.convertToString(sp.getIdentifier()));
            orgWebDTO.setName(orgBo.getName());
            orgWebDTO.setNciNumber(orgBo.getIdentifier());
            orgWebDTO.setFunctionalRole(sp.getFunctionalCode().getCode());
            orgWebDTO.setStatus(sp.getStatusCode().getCode());
            organizationList.add(orgWebDTO);
        }
        ServletActionContext.getRequest().getSession().removeAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
    }

    /**
     * @return the organizationList
     * @throws PAException on error.
     */
    public String displayOrg() throws PAException {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        LimitOffset limit = new LimitOffset(1, 0);
        try {
            selectedOrgDTO = PoRegistry.getOrganizationEntityService().search(criteria, limit).get(0);
        } catch (TooManyResultsException e) {
            // This should never happen, because we set the limit to 1
            throw new PAException("TooManyResultsException caught while searching with a limit of 1", e);
        }

        // store selection
        Organization org = new Organization();
        org.setCity(selectedOrgDTO.getPostalAddress().getPart().get(AD_CITY_IDX).getValue());
        org.setCountryName(selectedOrgDTO.getPostalAddress().getPart().get(AD_COUNTRY_IDX).getCode());
        org.setIdentifier(IiConverter.convertToString(selectedOrgDTO.getIdentifier()));
        org.setName(selectedOrgDTO.getName().getPart().get(0).getValue());
        org.setPostalCode(selectedOrgDTO.getPostalAddress().getPart().get(AD_ZIP_IDX).getValue());

        ParticipatingOrganizationsTabWebDTO tab =  new ParticipatingOrganizationsTabWebDTO();
        tab.setPoOrganizationIi(selectedOrgDTO.getIdentifier());
        tab.setFacilityOrganization(org);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        return DISPLAYJSP;
    }

    /**
     * @return the organizationList
     */
    public List<PaOrganizationDTO> getOrganizationList() {
        return organizationList;
    }

    /**
     * @param organizationList the organizationList to set
     */
    public void setOrganizationList(List<PaOrganizationDTO> organizationList) {
        this.organizationList = organizationList;
    }

    /**
     * @return result
     * @throws PAException on error.
     */
    public String nodecorlookup() throws PAException {
        countryRegDTO = PaRegistry.getRegulatoryInformationService().getDistinctCountryNames();
        return "lookup";
    }

    /**
     * @return the countryRegDTO
     */
    public List<CountryRegAuthorityDTO> getCountryRegDTO() {
        return countryRegDTO;
    }

    /**
     * @param countryRegDTO the countryRegDTO to set
     */
    public void setCountryRegDTO(List<CountryRegAuthorityDTO> countryRegDTO) {
        this.countryRegDTO = countryRegDTO;
    }

    /**
     * @return the selectedOrgDTO
     */
    public OrganizationDTO getSelectedOrgDTO() {
        return selectedOrgDTO;
    }

    /**
     * @param selectedOrgDTO the selectedOrgDTO to set
     */
    public void setSelectedOrgDTO(OrganizationDTO selectedOrgDTO) {
        this.selectedOrgDTO = selectedOrgDTO;
    }
    /**
     * @return the cbValue
     */
    public Long getCbValue() {
        return cbValue;
    }

    /**
     * @param cbValue the cbValue to set
     */
    public void setCbValue(Long cbValue) {
        this.cbValue = cbValue;
    }

    /**
     * @return the functionalCode
     */
    public String getFunctionalCode() {
        return functionalCode;
    }

    /**
     * @param functionalCode the functionalCode to set
     */
    public void setFunctionalCode(String functionalCode) {
        this.functionalCode = functionalCode;
    }

    /**
     * @return the currentAction
     */
    public String getCurrentAction() {
        return currentAction;
    }

    /**
     * @param currentAction the currentAction to set
     */
    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
    }

    /**
     * @return the orgFromPO
     */
    public PaOrganizationDTO getOrgFromPO() {
        return orgFromPO;
    }

    /**
     * @param orgFromPO the orgFromPO to set
     */
    public void setOrgFromPO(PaOrganizationDTO orgFromPO) {
        this.orgFromPO = orgFromPO;
    }

    /**
     * @param correlationUtils the correlationUtils to set
     */
    public void setCorrelationUtils(CorrelationUtilsRemote correlationUtils) {
        this.correlationUtils = correlationUtils;
    }

    /**
     * @return the editOrg
     */
    public Organization getEditOrg() {
        return editOrg;
    }

    /**
     * @param editOrg the editOrg to set
     */
    public void setEditOrg(Organization editOrg) {
        this.editOrg = editOrg;
    }
    
}

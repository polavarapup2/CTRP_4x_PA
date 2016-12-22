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

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author Harsha
 * @since 08/05/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 *
 */
public class RegulatoryInformationAction extends ActionSupport {
    private static final long serialVersionUID = 1L;

    private List<CountryRegAuthorityDTO> countryList = new ArrayList<CountryRegAuthorityDTO>();
    private String lst;
    private RegulatoryAuthorityWebDTO webDTO = new RegulatoryAuthorityWebDTO();
    private List<RegulatoryAuthOrgDTO> regIdAuthOrgList = new ArrayList<RegulatoryAuthOrgDTO>();
    private String selectedRegAuth;

    /**
     * Method to save the regulatory information to the database.
     *
     * @return String success or failure
     * @throws PAException PAException
     */
    @SuppressWarnings({ "PMD.ExcessiveMethodLength",
            "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
    public String update() throws PAException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        Ii studyProtocolIi = (Ii) request.getSession().getAttribute(
                Constants.STUDY_PROTOCOL_II);
        // Update InterventionalSP
        StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        validateForm(spDTO);
        String orgName;
        try {
            if (hasFieldErrors()) {
                return query();
            }
            orgName = selectedRegAuth != null ? PaRegistry
                    .getRegulatoryInformationService().getCountryOrOrgName(
                            Long.valueOf(selectedRegAuth),
                            "RegulatoryAuthority") : null;
            String countryName = getLst() != null ? PaRegistry
                    .getRegulatoryInformationService().getCountryOrOrgName(
                            Long.valueOf(getLst()), "Country") : null;
            webDTO.setTrialOversgtAuthOrgName(orgName);
            webDTO.setTrialOversgtAuthCountry(countryName);            
            if (webDTO.getSection801Indicator() == null) {
                spDTO.setSection801Indicator(BlConverter.convertToBl(null));
            } else {
                spDTO.setSection801Indicator(BlConverter.convertToBl(Boolean.valueOf(webDTO.getSection801Indicator())));
            }
            if (webDTO.getFdaRegulatedInterventionIndicator() == null) {
                spDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(null));
            } else {
                spDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO
                        .getFdaRegulatedInterventionIndicator())));
            }
            if (webDTO.getDelayedPostingIndicator() == null) {
                spDTO.setDelayedpostingIndicator(BlConverter.convertToBl(null));
            } else {
                spDTO.setDelayedpostingIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO
                        .getDelayedPostingIndicator())));
            }
            if (webDTO.getDataMonitoringIndicator() == null) {
                spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(null));
            } else {
                spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO
                        .getDataMonitoringIndicator())));
            }
            PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);
            
            // Update StudyRegulatoryAuthority
            final StudyRegulatoryAuthorityServiceLocal regService = PaRegistry.getStudyRegulatoryAuthorityService();
            StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = regService
                    .getCurrentByStudyProtocol(studyProtocolIi);                
            if (sraFromDatabaseDTO == null) {
                if (StringUtils.isNotBlank(selectedRegAuth)) {
                    StudyRegulatoryAuthorityDTO sraDTO = new StudyRegulatoryAuthorityDTO();
                    sraDTO.setStudyProtocolIdentifier(studyProtocolIi);
                    sraDTO.setRegulatoryAuthorityIdentifier(IiConverter
                            .convertToIi(selectedRegAuth));
                    regService.create(
                            sraDTO);
                }
            } else {
                if (StringUtils.isNotBlank(selectedRegAuth)) {
                    sraFromDatabaseDTO
                            .setRegulatoryAuthorityIdentifier(IiConverter
                                    .convertToIi(selectedRegAuth));
                    regService.update(
                            sraFromDatabaseDTO);
                } else {
                    regService.delete(
                            sraFromDatabaseDTO.getIdentifier());
                }
            }

            request.setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
            return query();
        } catch (PAException e) {
            request.setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return query();
        }
    }

    /**
     *
     * @return String success or failure
     */
    public String query() {
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    Constants.STUDY_PROTOCOL_II);
            StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            StudyRegulatoryAuthorityDTO authorityDTO =
                            PaRegistry.getStudyRegulatoryAuthorityService().getCurrentByStudyProtocol(studyProtocolIi);
            //on error page if country and reg auth are chosen
            if (getSelectedRegAuth() != null) {
                regIdAuthOrgList = PaRegistry.getRegulatoryInformationService().getRegulatoryAuthorityNameId(
                        Long.valueOf(getLst()));
                setSelectedRegAuth(getSelectedRegAuth());
            }
            //countryList = PaRegistry.getRegulatoryInformationService().getDistinctCountryNames();
            countryList = PaRegistry.getRegulatoryInformationService().getDistinctCountryNamesStartWithUSA();
            if (authorityDTO != null
                    || BlConverter.convertToBool(spDTO
                            .getProprietaryTrialIndicator())) { // load values
                                                                // from database
                if (spDTO.getSection801Indicator().getValue() != null) {
                    webDTO.setSection801Indicator(BlConverter
                            .convertToString(spDTO.getSection801Indicator()));
                }
                if (spDTO.getFdaRegulatedIndicator().getValue() != null) {
                    webDTO.setFdaRegulatedInterventionIndicator(BlConverter
                            .convertToString(spDTO.getFdaRegulatedIndicator()));
                }
                if (spDTO.getDelayedpostingIndicator().getValue() != null) {
                    webDTO.setDelayedPostingIndicator(BlConverter
                            .convertToString(spDTO.getDelayedpostingIndicator()));
                }
                if (spDTO.getDataMonitoringCommitteeAppointedIndicator()
                        .getValue() != null) {
                    webDTO.setDataMonitoringIndicator((BlConverter.convertToString(spDTO
                            .getDataMonitoringCommitteeAppointedIndicator())));
                }
            }
            if (authorityDTO != null) { // load values from database
               
               StudyRegulatoryAuthorityDTO sraFromDatabaseDTO =
                    PaRegistry.getStudyRegulatoryAuthorityService().getCurrentByStudyProtocol(studyProtocolIi);
              if (sraFromDatabaseDTO != null) {
                Long sraId = Long.valueOf(sraFromDatabaseDTO.getRegulatoryAuthorityIdentifier().getExtension());
                List<Long> regInfo = PaRegistry.getRegulatoryInformationService().getRegulatoryAuthorityInfo(sraId);
                setLst(regInfo.get(1).toString());
                //set selected the name of the regulatory authority chosen
                regIdAuthOrgList = PaRegistry.getRegulatoryInformationService().getRegulatoryAuthorityNameId(
                                    Long.valueOf(regInfo.get(1).toString()));
                setSelectedRegAuth(regInfo.get(0).toString());
            }
         }

        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return SUCCESS;
        }
        return SUCCESS;
    }

    private void validateForm(StudyProtocolDTO spDTO) {
        if (!BlConverter.convertToBool(spDTO.getProprietaryTrialIndicator())) {
            if (StringUtils.isBlank(getLst())) {
                addFieldError("lst", "Country is required field");
            }
            if (StringUtils.isBlank(getSelectedRegAuth())) {
                addFieldError("selectedRegAuth", "Oversight Authority is required field");
            }
    
            if (StringUtils.isBlank(webDTO.getFdaRegulatedInterventionIndicator())) {
                addFieldError("webDTO.fdaRegulatedInterventionIndicator",
                        "FDA Regulated Intervention Indicator is required field");
            }
        }
        if (Boolean.TRUE
                .equals(Boolean.valueOf(webDTO.getSection801Indicator()))
                && spDTO instanceof NonInterventionalStudyProtocolDTO) {
            addFieldError("webDTO.section801Indicator",
                    "Section 801 Indicator should be No for Non-interventional trials");
        }
    }

    /**
     * @return String success or failure
     */
    public String getRegAuthoritiesList() {
        try {
            String countryId = ServletActionContext.getRequest().getParameter("countryid");
            if (countryId != null && !("".equals(countryId))) {
                regIdAuthOrgList = PaRegistry.getRegulatoryInformationService().getRegulatoryAuthorityNameId(
                    Long.valueOf(countryId));
            } else {
                RegulatoryAuthOrgDTO defaultVal = new RegulatoryAuthOrgDTO();
                defaultVal.setName("-Select Country-");
                regIdAuthOrgList.add(defaultVal);
            }

        } catch (PAException e) {
            return SUCCESS;
        }
        return SUCCESS;
    }

    /**
     * @return the countryList
     */
    public List<CountryRegAuthorityDTO> getCountryList() {
        return countryList;
    }

    /**
     * @param countryList
     *            the countryList to set
     */
    public void setCountryList(List<CountryRegAuthorityDTO> countryList) {
        this.countryList = countryList;
    }

    /**
     * @return the lst
     */
    public String getLst() {
        return lst;
    }

    /**
     * @param lst
     *            the lst to set
     */
    public void setLst(String lst) {
        this.lst = lst;
    }

    /**
     * @return the webDTO
     */
    public RegulatoryAuthorityWebDTO getWebDTO() {
        return webDTO;
    }

    /**
     * @param webDTO
     *            the webDTO to set
     */
    public void setWebDTO(RegulatoryAuthorityWebDTO webDTO) {
        this.webDTO = webDTO;
    }

    /**
     * @return the regIdAuthOrgListget
     */
    public List<RegulatoryAuthOrgDTO> getRegIdAuthOrgList() {
        return regIdAuthOrgList;
    }

    /**
     * @param regIdAuthOrgList
     *            the regIdAuthOrgList to set
     */
    public void setRegIdAuthOrgList(List<RegulatoryAuthOrgDTO> regIdAuthOrgList) {
        this.regIdAuthOrgList = regIdAuthOrgList;
    }

    /**
     * @return the selectedRegAuth
     */
    public String getSelectedRegAuth() {
        return selectedRegAuth;
    }

    /**
     * @param selectedRegAuth the selectedRegAuth to set
     */
    public void setSelectedRegAuth(String selectedRegAuth) {
        this.selectedRegAuth = selectedRegAuth;
    }
}

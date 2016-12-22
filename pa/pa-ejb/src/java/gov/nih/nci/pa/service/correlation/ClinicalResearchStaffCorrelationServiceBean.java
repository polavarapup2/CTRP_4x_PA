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

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.logging.api.util.StringUtils;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PAExceptionConstants;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * A Service bean for maintaining Individuals who are employed and/or involved in any aspect of clinical research.
 * @author Naveen Amiruddin
 * @since 04/11/2008
 */
public class ClinicalResearchStaffCorrelationServiceBean {

    private static final Logger LOG  = Logger.getLogger(ClinicalResearchStaffCorrelationServiceBean.class);
    private final CorrelationUtils corrUtils = new CorrelationUtils();
    private final gov.nih.nci.pa.util.CorrelationUtils commonsCorrUtils = new gov.nih.nci.pa.util.CorrelationUtils();
    /**
     * This method assumes Organization and Person record exists in PO.
     * @param orgPoIdentifier po primary org id
     * @param personPoIdentifier po primary person id
     * @return long id
     * @throws PAException pe
     */
    public Long createClinicalResearchStaffCorrelations(String orgPoIdentifier, String personPoIdentifier)
            throws PAException {

        if (StringUtils.isBlank(orgPoIdentifier)) {
            throw new PAException(PAExceptionConstants.NULL_II_ORG);
        }
        if (StringUtils.isBlank(personPoIdentifier)) {
            throw new PAException(PAExceptionConstants.NULL_II_PERSON);
        }

        // Step 1 : get the PO Organization
        OrganizationDTO poOrg = null;
        try {
            poOrg = PoRegistry.getOrganizationEntityService().
                getOrganization(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
        } catch (NullifiedEntityException e) {
            throw new PAException(PAUtil.handleNullifiedEntityException(e), e);
        }

        // Step 2 : get the PO Person
        PersonDTO poPer = null;
        try {
            poPer = PoRegistry.getPersonEntityService().getPerson(IiConverter.convertToPoPersonIi(personPoIdentifier));
        } catch (NullifiedEntityException e) {
            throw new PAException(PAUtil.handleNullifiedEntityException(e), e);
         }


        // Step 2 : check if PO has crs correlation if not create one
        ClinicalResearchStaffDTO crsDTO = new ClinicalResearchStaffDTO();
        List<ClinicalResearchStaffDTO> crsDTOs = null;
        crsDTO.setScoperIdentifier(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
        crsDTO.setPlayerIdentifier(IiConverter.convertToPoPersonIi(personPoIdentifier));
        final ClinicalResearchStaffCorrelationServiceRemote crsService = PoRegistry
                .getClinicalResearchStaffCorrelationService();
        crsDTOs = crsService.search(crsDTO);
        if (crsDTOs != null && crsDTOs.size() > 1) {
            throw new PAException("PO CRS Correlation should not have more than 1  ");
        }
        if (crsDTOs == null || crsDTOs.isEmpty()) {
            try {
                Ii ii = new PAServiceUtils().isAutoCurationEnabled() ? crsService
                        .createActiveCorrelation(crsDTO) : crsService
                        .createCorrelation(crsDTO);
                crsDTO = crsService.getCorrelation(ii);
            } catch (NullifiedRoleException e) {
                throw new PAException(PAUtil.handleNullifiedRoleException(e));
            } catch (EntityValidationException e) {
                throw new PAException("Validation exception during create ClinicalResearchStaff " , e);
            } catch (CurationException e) {
                throw new PAException("CurationException exception during get ClinicalResearchStaff " , e);
            }
        } else {
            crsDTO = crsDTOs.get(0);
        }

        // Step 3 : check for pa org, if not create one
        Organization paOrg = corrUtils.getPAOrganizationByIi(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
        if (paOrg == null) {
            paOrg = corrUtils.createPAOrganization(poOrg);
        }
        // Step 4 : check for pa person, if not create one
        Person paPer = corrUtils.getPAPersonByIi(IiConverter.convertToPoPersonIi(personPoIdentifier));
        if (paPer == null) {
            paPer = corrUtils.createPAPerson(poPer);
        }

        // Step 6 : Check of PA has crs , if not create one
        ClinicalResearchStaff crs = commonsCorrUtils.getStructuralRoleByIi(DSetConverter.convertToIi(crsDTO
            .getIdentifier()));
        if (crs == null) {
            // create a new crs
            crs = new ClinicalResearchStaff();
            crs.setPerson(paPer);
            crs.setOrganization(paOrg);
            crs.setIdentifier(DSetConverter.convertToIi(crsDTO.getIdentifier()).getExtension());
            crs.setStatusCode(corrUtils.convertPORoleStatusToPARoleStatus(crsDTO.getStatus()));
            createPAClinicalResearchStaff(crs);
        }
        return crs.getId();
    }

    private ClinicalResearchStaffDTO getCrsDTOByIdentifier(Ii poCrsIdentifier)
        throws PAException {
        ClinicalResearchStaffDTO crsDTO = null;
        try {
            crsDTO = PoRegistry.getClinicalResearchStaffCorrelationService().getCorrelation(poCrsIdentifier);
        } catch (NullifiedRoleException e) {
            throw new PAException(PAUtil.handleNullifiedRoleException(e));
        }
        if (crsDTO == null) {
            throw new PAException("Unable to find ClinicalResearchStaff for identifier: " + poCrsIdentifier);
        }
        return crsDTO;
    }

    /**
     * createClinicalResearchStaffCorrelationsWithExistingPoCrs.
     * @param poCrsIdentifier po crs id
     * @return pa crs id.
     * @throws PAException when error.
     */
    public Long createClinicalResearchStaffCorrelationsWithExistingPoCrs(Ii poCrsIdentifier) throws PAException {
        ClinicalResearchStaffDTO crsDTO = getCrsDTOByIdentifier(poCrsIdentifier);
        ClinicalResearchStaff crs = commonsCorrUtils.getStructuralRoleByIi(poCrsIdentifier);
        if (crs == null) {

            PersonDTO poPersonDTO;
            OrganizationDTO poOrganizationDTO;
            try {
                poPersonDTO = PoRegistry.getPersonEntityService().getPerson(crsDTO.getPlayerIdentifier());
                poOrganizationDTO =
                    PoRegistry.getOrganizationEntityService().getOrganization(crsDTO.getScoperIdentifier());
            } catch (NullifiedEntityException e) {
                throw new PAException(PAUtil.handleNullifiedEntityException(e), e);
            }

            if (poPersonDTO == null) {
                throw new PAException("Unable to find Person for identifier: "
                        + crsDTO.getPlayerIdentifier().getExtension());
            }

            if (poOrganizationDTO == null) {
                throw new PAException("Unable to find Organization for identifier: "
                        + crsDTO.getScoperIdentifier().getExtension());
            }

            Person paPerson = corrUtils.createPAPerson(poPersonDTO);
            Organization paOrg = corrUtils.createPAOrganization(poOrganizationDTO);
            crs = new ClinicalResearchStaff();
            crs.setPerson(paPerson);
            crs.setOrganization(paOrg);
            crs.setIdentifier(poCrsIdentifier.getExtension());
            crs.setStatusCode(corrUtils.convertPORoleStatusToPARoleStatus(crsDTO.getStatus()));
            corrUtils.createPADomain(crs);
        }
        return crs.getId();
    }


    /**
     *
     * @param crs ClinicalResearchStaff
     * @return ClinicalResearchStaff
     * @throws PAException PAException
     */
    private ClinicalResearchStaff createPAClinicalResearchStaff(ClinicalResearchStaff crs) throws PAException {
        if (crs == null) {
            LOG.error(" ClinicalResearchStaff should not be null ");
            throw new PAException(" ClinicalResearchStaff should not be null ");
        }
        Session session = null;
        session = PaHibernateUtil.getCurrentSession();
        session.save(crs);
        session.flush();
        return crs;
    }

}

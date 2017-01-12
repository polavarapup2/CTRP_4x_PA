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
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Helper for AbstractParticipatingSitesBean.
 * @author mshestopalov
 *
 */
public abstract class AbstractBaseParticipatingSiteBean extends AbstractBaseParticipatingSiteEjbBean {
    private static final String INVALID_STATUS_DATE_CURRENT = "Trial Status Date cannot be in the future.";
    private static final String INVALID_OPEN_DATE_CURRENT = "Open Date cannot be in the future.";
    private static final String INVALID_CLOSED_DATE_CURRENT = "Closed Date cannot be in the future.";

    private void enforceBusinessRecruitmentRules1ForProprietary(boolean openDateAvail,
            StudySiteAccrualStatusDTO currentStatus) throws PAException {
        RecruitmentStatusCode status = RecruitmentStatusCode.getByCode(currentStatus.getStatusCode().getCode());
        if (status.isNonRecruiting() && openDateAvail) {
            throw new PAException("Date Opened for Accrual must be null for "
                    + currentStatus.getStatusCode().getCode());

        }
    }
  

    private void enforceBusinessDateRules1ForProprietary(boolean openDateAvail,
            boolean closedDateAvail, StudySiteDTO studySiteDTO,
            Timestamp currentTime) throws PAException {

            if (openDateAvail && currentTime.before(studySiteDTO.getAccrualDateRange().getLow().getValue())) {
                throw new PAException(INVALID_OPEN_DATE_CURRENT);
            }

            if (closedDateAvail && !openDateAvail) {
                throw new PAException("Date opened is required if date closed is provided.");
            }

    }

    private void enforceBusinessDateRules2ForProprietary(boolean openDateAvail,
            boolean closedDateAvail, StudySiteDTO studySiteDTO,
            Timestamp currentTime) throws PAException {

            if (closedDateAvail && currentTime.before(studySiteDTO.getAccrualDateRange().getHigh().getValue())) {
                throw new PAException(INVALID_CLOSED_DATE_CURRENT);
            }

            if (closedDateAvail && openDateAvail
                && studySiteDTO.getAccrualDateRange().getHigh().getValue()
                .before(studySiteDTO.getAccrualDateRange().getLow().getValue())) {
                throw new PAException("Date Closed cannot be bigger than Date Opened.");
            }
    }

    private void enforceBusinessDateRulesForProprietary(boolean openDateAvail,
            boolean closedDateAvail, StudySiteDTO studySiteDTO,
            Timestamp currentTime) throws PAException {

            enforceBusinessDateRules1ForProprietary(openDateAvail, closedDateAvail, studySiteDTO, currentTime);

            enforceBusinessDateRules2ForProprietary(openDateAvail, closedDateAvail, studySiteDTO, currentTime);

    }

    private void enforceBusinessSiteRulesForProprietary(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatus,
            Timestamp currentTime) throws PAException {

        boolean openDateAvail = isAccrualRangeLowAvailable(studySiteDTO.getAccrualDateRange());
        boolean closedDateAvail = isAccrualRangeHighAvailable(studySiteDTO.getAccrualDateRange());

        enforceBusinessDateRulesForProprietary(openDateAvail, closedDateAvail, studySiteDTO, currentTime);
        enforceBusinessRecruitmentRules1ForProprietary(openDateAvail, currentStatus);        
    }

    private boolean isAccrualRangeLowAvailable(Ivl<Ts> accrualDateRange) {
        return accrualDateRange != null
        && accrualDateRange.getLow() != null
        && accrualDateRange.getLow().getValue() != null;
    }

    private boolean isAccrualRangeHighAvailable(Ivl<Ts> accrualDateRange) {
        return accrualDateRange != null
        && accrualDateRange.getHigh() != null
        && accrualDateRange.getHigh().getValue() != null;
    }

    /**
     * enforceBusinessRulesForProprietary.
     * @param studyProtocolDTO dto
     * @param studySiteDTO dto
     * @param currentStatus status
     * @throws PAException when error
     * @throws ParseException when error
     */

    protected void enforceBusinessRulesForProprietary(StudyProtocolDTO studyProtocolDTO,
            StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatus) throws PAException, ParseException {

        // check prop status
        if (BooleanUtils.isFalse(studyProtocolDTO.getProprietaryTrialIndicator().getValue())) {
            throw new PAException("Not a prop trial.");
        }

        Timestamp currentTime = PAUtil.getCurrentTime();
        enforceBusinessRules(currentStatus, currentTime);


        if (StringUtils.isEmpty(studySiteDTO.getLocalStudyProtocolIdentifier().getValue())) {
            throw new PAException("Local Trial Identifier is required.");
        }

        enforceBusinessSiteRulesForProprietary(studySiteDTO, currentStatus, currentTime);

    }

    /**
     * enforceBusinessRules.
     * @param currentStatus status
     * @param currentTime time
     * @throws PAException when error
     */
    protected void enforceBusinessRules(StudySiteAccrualStatusDTO currentStatus,
            Timestamp currentTime) throws PAException {
        if (StringUtils.isEmpty(currentStatus.getStatusCode().getCode())) {
            throw new PAException("Please provide a trial status status.");
        }

        if (currentStatus.getStatusDate() != null
                && currentStatus.getStatusDate().getValue() != null) {
            if (currentTime.before(currentStatus.getStatusDate().getValue())) {
                throw new PAException(INVALID_STATUS_DATE_CURRENT);
            }
        } else {
            throw new PAException("Please provide a valid trial status date.");
        }
    }



    /**
     * generateHcfIiFromCtepIdOrNewOrg.
     * @param organizationDTO dto.
     * @param hcfDTO hcf dto.
     * @return po hcf ii
     * @throws EntityValidationException when error
     * @throws CurationException when error
     * @throws PAException when error
     * @throws NullifiedEntityException when error
     */
    protected Ii generateHcfIiFromCtepIdOrNewOrg(OrganizationDTO organizationDTO,
            HealthCareFacilityDTO hcfDTO)
        throws EntityValidationException, CurationException, PAException, NullifiedEntityException {

        Ii someHcfIi = null;
        if (hcfDTO != null && hcfDTO.getIdentifier() != null) {
            someHcfIi = DSetConverter.getFirstInDSet(hcfDTO.getIdentifier());
        }

        return generateHcfIiFromCtepIdOrNewOrg(organizationDTO, someHcfIi, hcfDTO);
    }

    private Ii generateHcfIiFromCtepIdOrNewOrg(OrganizationDTO organizationDTO, Ii someHcfIi,
            HealthCareFacilityDTO hcfDTO) throws PAException,
            EntityValidationException, CurationException, NullifiedEntityException {
        Ii poHcfIi = null;
        if (someHcfIi != null && IiConverter.HEALTH_CARE_FACILITY_ROOT.equals(someHcfIi.getRoot())) {
            poHcfIi = someHcfIi;
        } else if (someHcfIi != null && IiConverter.CTEP_ORG_IDENTIFIER_ROOT.equals(someHcfIi.getRoot())) {
                poHcfIi = getCorrUtils().getPoHcfByCtepId(someHcfIi);
        } else if (organizationDTO != null) {
            poHcfIi = getPoHcfFromNewOrCurrentOrg(organizationDTO, hcfDTO);
        } else {
            throw new PAException("Expecting either full org dto or po hcf ii, got ii with ext: "
                    + someHcfIi.getExtension());
        }
        return poHcfIi;
    }

    private Ii getPoHcfFromNewOrCurrentOrg(OrganizationDTO organizationDTO,
            HealthCareFacilityDTO toStoreDTO) throws EntityValidationException,
            CurationException, PAException, NullifiedEntityException {

        Ii poOrgIi = null;
        if (!ISOUtil.isIiNull(organizationDTO.getIdentifier())
                && IiConverter.ORG_ROOT.equals(organizationDTO.getIdentifier().getRoot())) {
            OrganizationDTO currOrgDTO = PoRegistry.getOrganizationEntityService()
                .getOrganization(organizationDTO.getIdentifier());
            poOrgIi = currOrgDTO.getIdentifier();
        } else {
            poOrgIi = PoRegistry.getOrganizationEntityService().createOrganization(organizationDTO);
        }
        HealthCareFacilityDTO hcfDTO = null;
        if (toStoreDTO == null) {
            hcfDTO = new HealthCareFacilityDTO();
        } else {
            hcfDTO = toStoreDTO;
        }
        hcfDTO.setIdentifier(null);
        hcfDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi(poOrgIi.getExtension()));
        List<HealthCareFacilityDTO> hcfDTOs =
            PoRegistry.getHealthCareFacilityCorrelationService().search(hcfDTO);
        if (CollectionUtils.isEmpty(hcfDTOs)) {
            try {
                return PoRegistry.getHealthCareFacilityCorrelationService().createCorrelation(hcfDTO);
            } catch (EntityValidationException e) {
                throw new PAException("Validation exception during create HealthCareFacility " , e);
            } catch (CurationException e) {
                throw new PAException("CurationException during create HealthCareFacility " , e);
            }
        } else {
            return DSetConverter.convertToIi(hcfDTOs.get(0).getIdentifier());
        }

    }


}

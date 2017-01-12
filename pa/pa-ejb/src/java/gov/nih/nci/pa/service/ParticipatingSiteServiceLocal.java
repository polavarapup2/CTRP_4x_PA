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

import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteContactDTO;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

/**
 * 
 * @author mshestopalov
 * 
 */
@Local
@SuppressWarnings({"PMD.ExcessiveParameterList", "PMD.TooManyMethods" })
public interface ParticipatingSiteServiceLocal {
    
    /**
     * Save a study site and its status with a new organization or a ctep id for the po hcf. StudySiteDTO is expected to
     * have the studyProtocol id set.
     * 
     * @param orgDTO po org dto
     * @param studySiteDTO dto
     * @param currentStatusDTO dto
     * @param hcfDTO po hcf dto
     * @param participatingSiteContactDTOList list of ParticipatingSiteContactDTOs to add.
     * @return the participating site dto
     * @throws PAException when error
     */
    ParticipatingSiteDTO createStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO, OrganizationDTO orgDTO, HealthCareFacilityDTO hcfDTO,
            List<ParticipatingSiteContactDTO> participatingSiteContactDTOList) throws PAException;
    
    /**
     * Returns all participating sites for a given study protocol.
     * @param studyProtocolIi the id of the study protocol to retrieve participating sites for
     * @return the list of participating sites
     * @throws PAException on error
     */
    List<ParticipatingSiteDTO> getParticipatingSitesByStudyProtocol(Ii studyProtocolIi) throws PAException;
    
    
    /**
     * Update the study site and its status. Expect id set on studySiteDTO.
     * 
     * @param studySiteDTO dto.
     * @param currentStatusDTO dto.
     * @param participatingSiteContactDTOList list of ParticipatingSiteContactDTOs to add.
     * @return the participating site dto
     * @throws PAException when error.
     */
    ParticipatingSiteDTO updateStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO,
            List<ParticipatingSiteContactDTO> participatingSiteContactDTOList) throws PAException;
    
    /**
     * Save a study site and its status where the PO org already exists and will be found using the po hcf ii or the
     * ctep hcf ii. StudySiteDTO is expected to have the studyProtocol id set.
     * 
     * @param studySiteDTO dto
     * @param currentStatusDTO dto
     * @param poHcfIi po hcf ii
     * @param participatingSiteContactDTOList list of ParticipatingSiteContactDTOs to add.
     * @return the participating site dto
     * @throws PAException when error
     */
    ParticipatingSiteDTO createStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO, Ii poHcfIi,
            List<ParticipatingSiteContactDTO> participatingSiteContactDTOList) throws PAException;


    /**
     * Save a study site and its status with a new organization or a ctep id for the po hcf. StudySiteDTO is expected to
     * have the studyProtocol id set.
     * 
     * @param orgDTO po org dto
     * @param studySiteDTO dto
     * @param currentStatusDTO dto
     * @param hcfDTO po hcf dto
     * @return the participating site dto
     * @throws PAException when error
     */
    ParticipatingSiteDTO createStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO, OrganizationDTO orgDTO, HealthCareFacilityDTO hcfDTO)
            throws PAException;

    /**
     * Save a study site and its status where the PO org already exists and will be found using the po hcf ii or the
     * ctep hcf ii. StudySiteDTO is expected to have the studyProtocol id set.
     * 
     * @param studySiteDTO dto
     * @param currentStatusDTO dto
     * @param poHcfIi po hcf ii
     * @return the participating site dto
     * @throws PAException when error
     */
    ParticipatingSiteDTO createStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO, Ii poHcfIi) throws PAException;
    
    /**
     * Save a study site and its status where the PO org already exists and will
     * be found using the po hcf ii or the ctep hcf ii. StudySiteDTO is expected
     * to have the studyProtocol id set.
     * 
     * @param studySiteDTO
     *            dto
     * @param currentStatusDTO
     *            dto
     * @param poHcfIi
     *            po hcf ii
     * @param statusHistory statusHistory
     * @return the participating site dto
     * @throws PAException
     *             when error
     */
    ParticipatingSiteDTO createStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO,
            Collection<StatusDto> statusHistory, Ii poHcfIi) throws PAException;

    /**
     * Update the study site and its status. Expect id set on studySiteDTO.
     * 
     * @param studySiteDTO dto.
     * @param currentStatusDTO dto.
     * @return the participating site dto
     * @throws PAException when error.
     */
    ParticipatingSiteDTO updateStudySiteParticipant(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO) throws PAException;
    
    /**
     * Update the study site and its status. Expect id set on studySiteDTO.
     * 
     * @param studySiteDTO
     *            dto.
     * @param currentStatusDTO
     *            dto.
     * @param  statusHistory statusHistory
     * @return the participating site dto
     * @throws PAException
     *             when error.
     */
    ParticipatingSiteDTO updateStudySiteParticipantWithStatusHistory(StudySiteDTO studySiteDTO,
            StudySiteAccrualStatusDTO currentStatusDTO,
            Collection<StatusDto> statusHistory) throws PAException;

    /**
     * Lookup study site id given a trial id and a hcf id.
     * 
     * @param studyProtocolIi trial id (nci or pa)
     * @param someHcfIi hcf id (po hcf or ctep id)
     * @return Ii.
     * @throws PAException in case of error.
     */
    Ii getParticipatingSiteIi(Ii studyProtocolIi, Ii someHcfIi) throws PAException;

    /**
     * Gets the Participating site with the given Ii.
     * 
     * @param studySiteIi
     *            The study site Ii
     * @return The Participating site with the given Ii.
     * @throws PAException
     *             if an error occurs
     */
    ParticipatingSiteDTO getParticipatingSite(Ii studySiteIi)
            throws PAException;

    /**
     * Add a investigator contact. Either a po crs id or po hcp id is needed or can be null if creating a new person
     * from the person dto. If crs or hcp ids are set they must be po ids or ctep ids for the person. If crs or hcp are
     * provided but the ids are not set, they will be used in creating new SRs for Person.
     * 
     * @param studySiteIi ii
     * @param poCrsDTO dto
     * @param poHcpDTO dto
     * @param investigatorDTO dto
     * @param roleCode role (principal or sub)
     * @throws PAException when error.
     */
    void addStudySiteInvestigator(Ii studySiteIi, ClinicalResearchStaffDTO poCrsDTO, HealthCareProviderDTO poHcpDTO,
            PersonDTO investigatorDTO, String roleCode) throws PAException;

    /**
     * Add a primary person contact. Either a po crs id or po hcp id is needed or can be null if creating a new person
     * from the person dto. If crs or hcp ids are set they must be po ids or ctep ids for the person. If crs or hcp are
     * provided but the ids are not set, they will be used in creating new SRs for Person.
     * 
     * @param studySiteIi ii
     * @param poCrsDTO dto
     * @param poHcpDTO dto
     * @param personDTO dto
     * @param telecom telecom list of tel and email can be null.
     * @throws PAException when error
     */
    void addStudySitePrimaryContact(Ii studySiteIi, ClinicalResearchStaffDTO poCrsDTO, HealthCareProviderDTO poHcpDTO,
            PersonDTO personDTO, DSet<Tel> telecom) throws PAException;

    /**
     * Add a generic contact. Provide a dto w/ id set to reuse existing organizational contact. If id is set, it must be
     * a po id or a ctep id. Otherwise provide dto data to create new.
     * 
     * @param studySite ii
     * @param contactDTO dto.
     * @param isPrimaryContact bool
     * @param telecom list of tel and email can be null.
     * @throws PAException when error.
     */
    void addStudySiteGenericContact(Ii studySite, OrganizationalContactDTO contactDTO, boolean isPrimaryContact,
            DSet<Tel> telecom) throws PAException;

    /**
     * Returns participating site of the given PO Org on the given trial.
     * @param studyProtocolID studyProtocolID
     * @param orgPoId orgPoId
     * @return StudySiteDTO or null
     * @throws PAException PAException
     */
    StudySiteDTO getParticipatingSite(Ii studyProtocolID, String orgPoId)  throws PAException;

    /**
     * Merge on participating site with another. Only the accrual data from the src site is
     * maintained.
     * @param srcId the study site id of the site to be deleted 
     * @param destId the study site id of the site which will have accrual data added
     * @throws PAException on error
     */
    void mergeParicipatingSites(Long srcId, Long destId) throws PAException;

    /**
     * Returns a list of participating sites acting on the given trial, which user can update.
     * @param user RegistryUser
     * @param studyProtocolID studyProtocolID
     * @return List<Organization>
     * @throws PAException  PAException
     * @throws NullifiedRoleException  NullifiedRoleException
     */
    List<Organization> getListOfSitesUserCanUpdate(RegistryUser user,
            Ii studyProtocolID) throws PAException, NullifiedRoleException;

    /**
     * Close open sites using the same status code as the trial's.
     * 
     * @param spID
     *            spID
     * @param  oldStatus oldStatus
     * @param currentStatus
     *            trial status to use
     * @param mustNotifyTrialOwners
     *            mustNotifyTrialOwners
     * @return Map <String, ParticipatingSiteDTO> map         
     * @throws PAException
     *             PAException
     */
 // commented as part of PO-9862
    /*
    Map <String, ParticipatingSiteDTO>  closeOpenSites(Ii spID, StudyOverallStatusDTO oldStatus,
            StudyOverallStatusDTO currentStatus, boolean mustNotifyTrialOwners)
            throws PAException;
*/
    /**
     * Returns a list of participating sites acting on the given trial, which user can Add.
     * @param user RegistryUser
     * @param studyProtocolID studyProtocolID
     * @return List<Organization>
     * @throws PAException  PAException
     * @throws NullifiedRoleException  NullifiedRoleException
     */   
    List<Organization> getListOfSitesUserCanAdd(RegistryUser user, Ii studyProtocolID) 
          throws PAException, NullifiedRoleException;

}

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
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.PlannedSubstanceAdministration;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.iso.convert.PlannedSubstanceAdministrationConverter;
import gov.nih.nci.pa.iso.dto.PlannedSubstanceAdministrationDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.PlannedSubstanceAdministrationSortCriterion;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class PlannedSubstanceAdministrationBeanLocal
extends AbstractStudyIsoService<PlannedSubstanceAdministrationDTO, PlannedSubstanceAdministration,
    PlannedSubstanceAdministrationConverter> implements PlannedSubstanceAdministrationServiceLocal {

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PlannedSubstanceAdministrationDTO> getPlannedSubstanceAdministrationByStudyProtocol(Ii ii)
    throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            throw new PAException("Check the Ii value; found null.");
        }

        PlannedSubstanceAdministration criteria = new PlannedSubstanceAdministration();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(IiConverter.convertToLong(ii));
        criteria.setStudyProtocol(sp);

        PageSortParams<PlannedSubstanceAdministration> params =
            new PageSortParams<PlannedSubstanceAdministration>(PAConstants.MAX_SEARCH_RESULTS, 0,
                    PlannedSubstanceAdministrationSortCriterion.PLANNED_SUBSTANCE_ADMINISTRATION_ID, false);
        List<PlannedSubstanceAdministration> results =
            search(new AnnotatedBeanSearchCriteria<PlannedSubstanceAdministration>(criteria), params);
        return convertFromDomainToDTOs(results);
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public PlannedSubstanceAdministrationDTO getPlannedSubstanceAdministration(Ii ii) throws PAException {
        return super.get(ii);
    }

    /**
     * {@inheritDoc}
     */
    public PlannedSubstanceAdministrationDTO createPlannedSubstanceAdministration(PlannedSubstanceAdministrationDTO dto)
        throws PAException {
        if (!ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Update method should be used to modify existing.");
        }
        if (ISOUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new PAException("StudyProtocol must be set.");
        }
        return createOrUpdatePlannedSubstanceAdministration(dto);
    }

    /**
     * {@inheritDoc}
     */
    public PlannedSubstanceAdministrationDTO updatePlannedSubstanceAdministration(PlannedSubstanceAdministrationDTO dto)
        throws PAException {
        if (ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Create method should be used to modify existing.");
        }
        return createOrUpdatePlannedSubstanceAdministration(dto);
    }

    /**
     * {@inheritDoc}
     */
    public void deletePlannedSubstanceAdministration(Ii ii) throws PAException {
        super.delete(ii);
    }

    private PlannedSubstanceAdministrationDTO createOrUpdatePlannedSubstanceAdministration(
            PlannedSubstanceAdministrationDTO dto) throws PAException {
        PlannedSubstanceAdministration bo = null;
        Session session = PaHibernateUtil.getCurrentSession();
        PlannedSubstanceAdministrationConverter converter = new PlannedSubstanceAdministrationConverter();
        if (ISOUtil.isIiNull(dto.getIdentifier())) {
            bo = converter.convertFromDtoToDomain(dto);
        } else {
            bo = (PlannedSubstanceAdministration) session.get(PlannedSubstanceAdministration.class,
                    IiConverter.convertToLong(dto.getIdentifier()));

            PlannedSubstanceAdministration delta = converter.convertFromDtoToDomain(dto);
            bo.setDoseDescription(delta.getDoseDescription());
            bo.setDoseRegimen(delta.getDoseRegimen());
            bo.setDoseFormCode(delta.getDoseFormCode());
            bo.setDoseFrequencyCode(delta.getDoseFrequencyCode());
            bo.setDoseMinValue(delta.getDoseMinValue());
            bo.setDoseMinUnit(delta.getDoseMinUnit());
            bo.setDoseMaxValue(delta.getDoseMaxValue());
            bo.setDoseMaxUnit(delta.getDoseMaxUnit());
            bo.setDoseTotalMinValue(delta.getDoseTotalMinValue());
            bo.setDoseTotalMinUnit(delta.getDoseTotalMinUnit());
            bo.setDoseTotalMaxValue(delta.getDoseTotalMaxValue());
            bo.setDoseTotalMaxUnit(delta.getDoseTotalMaxUnit());
            bo.setRouteOfAdministrationCode(delta.getRouteOfAdministrationCode());
            bo.setDoseDurationValue(delta.getDoseDurationValue());
            bo.setDoseDurationUnit(delta.getDoseDurationUnit());
            bo.setUserLastUpdated(delta.getUserLastCreated());
        }
        bo.setDateLastUpdated(new Date());
        session.saveOrUpdate(bo);
        return converter.convertFromDomainToDto(bo);
    }
}

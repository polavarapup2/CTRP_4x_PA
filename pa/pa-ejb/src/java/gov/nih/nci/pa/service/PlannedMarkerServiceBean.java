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
import gov.nih.nci.pa.domain.PlannedMarker;
import gov.nih.nci.pa.enums.BioMarkerAttributesCode;
import gov.nih.nci.pa.iso.convert.PlannedMarkerConverter;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.PlannedMarkerSortCriterion;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

/**
 * Service for performing CRUD operations on planned markers.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class PlannedMarkerServiceBean extends
AbstractStudyIsoService<PlannedMarkerDTO, PlannedMarker, PlannedMarkerConverter>
implements PlannedMarkerServiceLocal {
    private StudyProtocolServiceLocal studyProtocolService;
    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public PlannedMarkerDTO getPlannedMarker(Ii ii) throws PAException {
        return super.get(ii);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedMarkerDTO create(PlannedMarkerDTO dto) throws PAException {
        validate(dto);
        return super.create(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlannedMarkerDTO update(PlannedMarkerDTO dto) throws PAException {
        validate(dto);
        return super.update(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(PlannedMarkerDTO dto) throws PAException {
        super.validate(dto);
        enforceNoDuplicates(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getQueryOrderClause() {
        return " order by alias.id";
    }

    private void enforceNoDuplicates(PlannedMarkerDTO markerDTO) throws PAException {
        PlannedMarker criteria = convertFromDtoToDomain(markerDTO);
        criteria.setId(null);
        PageSortParams<PlannedMarker> params = new PageSortParams<PlannedMarker>(PAConstants.MAX_SEARCH_RESULTS, 0,
                PlannedMarkerSortCriterion.PLANNED_MARKER_ID, false);
        List<PlannedMarker> results = search(new AnnotatedBeanSearchCriteria<PlannedMarker>(criteria), params);
        for (PlannedMarker m : results) {
            if (!m.getId().equals(
                    IiConverter.convertToLong(markerDTO.getIdentifier()))
                    && ObjectUtils.equals(m.getPermissibleValue().getId(),
                            (IiConverter.convertToLong(markerDTO
                                    .getPermissibleValue())))) {
                throw new PADuplicateException(
                        "Duplicate Planned Markers are not allowed.");
            }     
        }  
    }
           
    /**
     * returns list of plannedMarkers with pending status.
     * @return list of PlannedMarkerDTO
     * @throws PAException exception
     */
    public List<PlannedMarkerDTO> getPlannedMarkers() throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        String hql = "from PlannedMarker as pm where pm.statusCode='PENDING'";
        Query query = session.createQuery(hql);
        List<PlannedMarker> markers = query.list();
        return (List<PlannedMarkerDTO>) convertFromDomainToDTOs(markers);         
    }
    
    
    /**
     * returns list of plannedMarkers with the matching long name with pending status.
     * @return list of PlannedMarkerDTO
     * @param longName long name
     * @throws PAException exception
     */
    public List<PlannedMarkerDTO> getPendingPlannedMarkersWithName(String longName) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        String hql = "from PlannedMarker as pm where pm.statusCode='PENDING' and pm.permissibleValue= " 
            + " (select ps.id from PlannedMarkerSyncWithCaDSR as ps where ps.name ='"
            + longName + "')";
        Query query = session.createQuery(hql);
        List<PlannedMarker> markers = query.list();
        return (List<PlannedMarkerDTO>) convertFromDomainToDTOs(markers);         
    }
    
    /**
     * returns list of plannedMarkers with the matching Sync id with pending status.
     * @return list of PlannedMarkerDTO
     * @param syncId syncId
     * @throws PAException exception
     */
    public List<PlannedMarkerDTO> getPendingPlannedMarkerWithSyncID(Long syncId) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        String hql = "from PlannedMarker as pm where pm.statusCode='PENDING' and pm.permissibleValue=" + syncId;
        Query query = session.createQuery(hql);
        List<PlannedMarker> markers = query.list();
        return (List<PlannedMarkerDTO>) convertFromDomainToDTOs(markers);         
    }

    /**
     * returns list of plannedMarkers with the matching short name NCI ID and with pending status.
     * @return list of PlannedMarkerDTO
     * @param name name
     * @param nciIdentifier nciIdentifier
     * @throws PAException exception
     */
    public List<PlannedMarkerDTO> getPendingPlannedMarkersShortNameAndNCIId(String name, String nciIdentifier) 
    throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        studyProtocolService = PaRegistry.getStudyProtocolService();
        String hql = "";
        List<Long> protocolIds = new ArrayList<Long>();
        if (!StringUtils.isBlank(nciIdentifier)) {
            protocolIds = studyProtocolService.getProtocolIdsWithNCIId(nciIdentifier);    
        }
        if (!StringUtils.isBlank(name) && !StringUtils.isBlank(nciIdentifier)) {
            hql = "from PlannedMarker as pm where pm.statusCode='PENDING' and pm.permissibleValue IN "
                + " (select ps.id from PlannedMarkerSyncWithCaDSR as ps where UPPER(ps.name)"
                + " like UPPER(:name)) and pm.id IN" 
                + " (select pa.id from PlannedActivity as pa where pa.studyProtocol.id IN (:listOfIds))";
        } else {         
            hql = "from PlannedMarker as pm where pm.statusCode='PENDING' and pm.permissibleValue IN "
                + " (select ps.id from PlannedMarkerSyncWithCaDSR as ps where UPPER(ps.name)"
                + " like UPPER(:name)) or  pm.statusCode='PENDING' and pm.id IN" 
                + " (select pa.id from PlannedActivity as pa where pa.studyProtocol.id IN (:listOfIds))";
        } 
        Query query = session.createQuery(hql);
        if (!protocolIds.isEmpty()) {
            query.setParameterList("listOfIds", protocolIds);   
        } else {
            query.setParameter("listOfIds", null);  
        }
        
        query.setParameter("name", "%" + name + "%");
        List<PlannedMarker> markers = query.list();
        return (List<PlannedMarkerDTO>) convertFromDomainToDTOs(markers);  
    }
    /**
     * returns list of plannedMarkers with the matching name with pending status.
     * @return list of PlannedMarkerDTO
     * @param name name
     * @throws PAException exception
     */
    public List<PlannedMarkerDTO> getPendingPlannedMarkersShortName(String name) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        String hql = "from PlannedMarker as pm where pm.statusCode='PENDING' and pm.permissibleValue IN" 
            + "(select ps.id from PlannedMarkerSyncWithCaDSR as ps where UPPER(ps.name)"
            + " like UPPER(:name))";
        Query query = session.createQuery(hql);
        query.setParameter("name", "%" + name + "%");
        List<PlannedMarker> markers = query.list();
        return (List<PlannedMarkerDTO>) convertFromDomainToDTOs(markers);         
    }
    
    /**
     * returns list of plannedMarkers with the matching list of ids with pending status.
     * @return list of PlannedMarkerDTO
     * @param listOfIds listOfIds
     * @throws PAException exception
     */
    public List<PlannedMarkerDTO> getPendingPlannedMarkersWithProtocolId(List<Long> listOfIds) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        String hql = "from PlannedMarker as pm where pm.statusCode='PENDING' and pm.id IN" 
            + " (select pa.id from PlannedActivity as pa " 
            + " where pa.studyProtocol.id IN (:listOfIds))";
        Query query = session.createQuery(hql);
        query.setParameterList("listOfIds", listOfIds);  
        List<PlannedMarker> markers = query.list();
        return (List<PlannedMarkerDTO>) convertFromDomainToDTOs(markers);     
    }
    
    /**
     * updates  plannedMarker with the new bio marker attribute values 
     * 
     * @param typeCode typeCode
     * @param oldValue oldValue
     * @param newValue newValue
     * @throws PAException exception
     */
    public void updatePlannedMarkerAttributeValues(BioMarkerAttributesCode typeCode, String oldValue, String newValue) 
    throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        List<Object[]> queryList = null;
        SQLQuery query = session.createSQLQuery("select identifier, "
                + typeCode.getName() + " from planned_marker where "
                + typeCode.getName() + " = '" + oldValue + "' or "
                + typeCode.getName() + " like (:oldValue1) or "
                + typeCode.getName() + " like (:oldValue2)");  
        query.setParameter("oldValue1", "%," + oldValue);
        query.setParameter("oldValue2", oldValue + ",%");
        queryList = query.list();
        String newValue1 = "";
        for (Object[] oArr : queryList) {
            if (StringUtils.containsIgnoreCase(oArr[1].toString(), oldValue)) {
                newValue1 = StringUtils.replace(oArr[1].toString(), oldValue, newValue);
            }  
            SQLQuery query1 = session
            .createSQLQuery("update planned_marker set " + typeCode.getName() 
                    + " = '" + newValue1 + "' where identifier = " + oArr[0]); 
            query1.executeUpdate();    
        }      
    }
    
    /**
     * returns  plannedMarker with the matching ID 
     * @return PlannedMarkerDTO
     * @param id id
     * @throws PAException exception
     */
    public PlannedMarkerDTO getPlannedMarkerWithID(Long id) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        String hql = "from PlannedMarker as pm where pm.id = :id ";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        List<PlannedMarker> markers = query.list();  
        PlannedMarker marker = null;
        PlannedMarkerDTO markerDto = null;
        if (!markers.isEmpty()) {
            marker = markers.get(0);
            markerDto = (PlannedMarkerDTO) convertFromDomainToDto(marker);
        }
        return  markerDto;
    }
    
    
    /**
     * updates the marker with status
     * 
     * @param identifier identifier
     * @param status status
     * @throws PAException PAException
     */
    public void updateStatusByPMSynID(Long identifier, String status) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        
        SQLQuery query = session.createSQLQuery("update Planned_Marker set status_code=:status"
                + " where pm_sync_identifier=:identifier");
        query.setParameter("status", status);
        query.setParameter("identifier", identifier);
        query.executeUpdate();
    }
    
    /**
     * updates the marker with status
     * 
     * @param newId newId
     * @param status status
     * @param oldId oldId
     * @throws PAException PAException
     * 
     */
    public void updateStatusOldIDByPMSynID(Long oldId, Long newId, String status) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        
        SQLQuery query = session.createSQLQuery("update Planned_Marker set status_code=:status, " 
                + " pm_sync_identifier=:newId"
                + " where pm_sync_identifier=:oldId");
        query.setParameter("status", status);
        query.setParameter("oldId", oldId);
        query.setParameter("newId", newId);
        query.executeUpdate();
    }
    
    /**
     * @return the studyProtocolService
     */
    public StudyProtocolServiceLocal getStudyProtocolService() {
        return studyProtocolService;
    }
    
    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }
    
    
}

/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.convert.StudySiteAccrualStatusConverter;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.status.StatusDto;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TrialUpdatesRecorder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author asharma
 *
 */
@Stateless

@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class StudySiteAccrualStatusBeanLocal extends AbstractBaseSearchBean<StudySiteAccrualStatus>
    implements StudySiteAccrualStatusServiceLocal {

    @EJB
//    @IgnoreDependency
    private StudySiteAccrualAccessServiceLocal studySiteAccrualAccessServiceLocal;
    
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    private StudySiteAccrualStatus get(Long id) throws PAException {
        if (id == null) {
            throw new PAException(
                    "Study Site Accrual Status identifier is required");
        }
        Session session = PaHibernateUtil.getCurrentSession();
        StudySiteAccrualStatus ssas = (StudySiteAccrualStatus) session.load(
                StudySiteAccrualStatus.class, id);
        if (ssas != null) {
            return ssas;
        } else {
            throw new PAException(
                    "Study Site Accrual Status not found for identifer, " + id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudySiteAccrualStatusDTO getStudySiteAccrualStatus(Ii ii) throws PAException {
        if (ii == null) {
            throw new PAException(
                    "Study Site Accrual Status identifier is required");
        }
        Session session = PaHibernateUtil.getCurrentSession();
        StudySiteAccrualStatus ssas = (StudySiteAccrualStatus) session.load(
                StudySiteAccrualStatus.class, 
                IiConverter.convertToLong(ii));
        if (ssas != null) {
            StudySiteAccrualStatusConverter converter = new StudySiteAccrualStatusConverter();
            return converter.convertFromDomainToDto(ssas);
        } else {
            throw new PAException(
                    "Study Site Accrual Status not found for identifer, " 
                            + IiConverter.convertToLong(ii));
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void softDelete(StudySiteAccrualStatusDTO dto) throws PAException {
        checkCondition(StringUtils.isBlank(StConverter.convertToString(dto.getComments())), 
                "A comment is required when deleting a status");
        Ii ii = dto.getIdentifier();
        Session session = PaHibernateUtil.getCurrentSession();
        StudySiteAccrualStatus ssas = (StudySiteAccrualStatus) session.load(
                StudySiteAccrualStatus.class, 
                IiConverter.convertToLong(ii));
        if (ssas != null) {
            ssas.setDeleted(true);
            ssas.setComments(StConverter.convertToString(dto.getComments()));
            ssas.setDateLastUpdated(new Date());
            ssas.setUserLastUpdated(CSMUserService.getInstance().getCSMUser(
                    UsernameHolder.getUser()));
            session.saveOrUpdate(ssas);
            session.flush();
        } else {
            throw new PAException(
                    "Study Site Accrual Status not found for identifer, " 
                            + IiConverter.convertToLong(ii));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteAccrualStatusDTO createStudySiteAccrualStatus(StudySiteAccrualStatusDTO dto) 
            throws PAException {
        if (!ISOUtil.isIiNull(dto.getIdentifier())) {
            String errMsg = "Existing StudySiteAccrualStatus objects cannot be modified."
                    + " Append new object instead.";
            throw new PAException(errMsg);
        }
        return update(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudySiteAccrualStatusDTO updateStudySiteAccrualStatus(StudySiteAccrualStatusDTO dto) throws PAException {
        if (ISOUtil.isIiNull(dto.getIdentifier())) {
            String errMsg = "StudySiteAccrualStatus identifier is required for update.";
            throw new PAException(errMsg);
        }
        return update(dto);
    }
    
    private StudySiteAccrualStatusDTO update(StudySiteAccrualStatusDTO dto) throws PAException {
        StudySiteAccrualStatusDTO resultDto = null;
        Session session = PaHibernateUtil.getCurrentSession();
        StudySiteAccrualStatusDTO current = getCurrentStudySiteAccrualStatusByStudySite(dto.getStudySiteIi());
        RecruitmentStatusCode oldCode = null;
        Timestamp oldDate = null;
        if (current != null) {
            oldCode = RecruitmentStatusCode.getByCode(current.getStatusCode().getCode());
            oldDate = TsConverter.convertToTimestamp(current.getStatusDate());
        }

        RecruitmentStatusCode newCode = RecruitmentStatusCode.getByCode(dto.getStatusCode().getCode());
        Timestamp newDate = TsConverter.convertToTimestamp(dto.getStatusDate());
        dto.setUpdatedOn(TsConverter.convertToTs(new Date()));
        validateNewStatus(newCode, newDate);
        if (!newCode.equals(oldCode) || !newDate.equals(oldDate)
                || !ISOUtil.isIiNull(dto.getIdentifier())) {
            StudySiteAccrualStatus bo =  null;
            if (ISOUtil.isIiNull(dto.getIdentifier())) {
                bo = new StudySiteAccrualStatus();
            } else {
                bo = get(IiConverter.convertToLong(dto.getIdentifier()));
            }
            StudySiteAccrualStatusConverter converter = new StudySiteAccrualStatusConverter();
            converter.convertFromDtoToDomain(dto, bo);
            bo.setDateLastUpdated(TsConverter.convertToTimestamp(dto.getUpdatedOn()));
            bo.setUserLastUpdated(CSMUserService.getInstance().getCSMUser(UsernameHolder.getUser()));
            session.saveOrUpdate(bo);
            session.flush();
            
            resultDto = converter.convertFromDomainToDto(bo);
            TrialUpdatesRecorder
                    .recordUpdate(TrialUpdatesRecorder.RECRUITMENT_STATUS_DATE_UPDATED);
        }
        return resultDto;
    }
    
    private void validateNewStatus(RecruitmentStatusCode newCode, Timestamp newDate) throws PAException {
        if (newCode == null) {
            throw new PAException("Study site accrual status must be set.");
        }
        if (newDate == null) {
            throw new PAException("Study site accrual status date must be set.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudySiteAccrualStatusDTO> getStudySiteAccrualStatusByStudySite(Ii studySiteIi) throws PAException {
        List<StudySiteAccrualStatus> queryList = getStudySiteAccrualStatusBOs(studySiteIi);
        StudySiteAccrualStatusConverter converter = new StudySiteAccrualStatusConverter();
        List<StudySiteAccrualStatusDTO> returnList = new ArrayList<StudySiteAccrualStatusDTO>();
        for (StudySiteAccrualStatus bo : queryList) {
            returnList.add(converter.convertFromDomainToDto(bo));
        }
        return returnList;
    }
   
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)  
   public boolean ifCloseStatusExistsInHistory(Ii studySiteIi) throws PAException {
       
        List<StudySiteAccrualStatus> queryList = getStudySiteAccrualStatusBOs(studySiteIi);
        
            for (StudySiteAccrualStatus bo : queryList) {
                if (bo != null && bo.getStatusCode() != null 
                        && bo.getStatusCode().isClosed()) {
                    return true;
                }
            }
            
        return false;
    }

    /**
     * @param studySiteIi
     * @return
     * @throws PAException
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    private List<StudySiteAccrualStatus> getStudySiteAccrualStatusBOs(
            Ii studySiteIi) throws PAException {
        if (ISOUtil.isIiNull(studySiteIi)) {
            throw new PAException("Cannot call getStudySiteAccrualStatusByStudySite with a null identifier.");
        }

        Session session = PaHibernateUtil.getCurrentSession();
        String hql = "select alias from StudySiteAccrualStatus alias join fetch alias.studySite ss "
                            + " where ss.id = :ssId"
                            + " and alias.deleted=false " 
                            + getQueryOrderClause();        
        Query query = session.createQuery(hql);
        query.setParameter("ssId", IiConverter.convertToLong(studySiteIi));
        return query.list();
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Collection<StatusDto> getStatusHistory(Ii studySiteIi)
            throws PAException {
        List<StudySiteAccrualStatus> queryList = getStudySiteAccrualStatusBOs(studySiteIi);
        List<StatusDto> returnList = new ArrayList<StatusDto>();        
        for (StudySiteAccrualStatus bo : queryList) {
            returnList.add(convertFromDomainToStatusDto(bo));
        }
        return returnList;
    }
    
    /**
     * @param bo StudySiteAccrualStatus
     * @return StatusDto
     */
    private StatusDto convertFromDomainToStatusDto(StudySiteAccrualStatus bo) {
        StatusDto dto = new StatusDto();
        dto.setId(bo.getId());
        dto.setStatusCode(bo.getStatusCode().name());
        dto.setStatusDate(bo.getStatusDate());
        dto.setComments(bo.getComments());        
        return dto;
    }

    /**
     * @param studySiteIi Primary key assigned to a StudyProtocl.
     * @return StudySiteAccrualStatusDTO Current status.
     * @throws PAException Exception.
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudySiteAccrualStatusDTO getCurrentStudySiteAccrualStatusByStudySite(Ii studySiteIi) throws PAException {
        Long ssId = Long.valueOf(studySiteIi.getExtension());
        Map<Long, StudySiteAccrualStatus> map = getCurrentStudySiteAccrualStatus(new Long[] {ssId});
        StudySiteAccrualStatusDTO result = null;
        if (!map.isEmpty()) {
            result = new StudySiteAccrualStatusConverter().convertFromDomainToDto(map.get(ssId));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, StudySiteAccrualStatus> getCurrentStudySiteAccrualStatus(Long[] ids) throws PAException {
        Map<Long, StudySiteAccrualStatus> result = new HashMap<Long, StudySiteAccrualStatus>();
        try {
            Query qry = PaHibernateUtil.getCurrentSession().createQuery(
                    "from StudySiteAccrualStatus alias join fetch alias.studySite ss "
                            + " where ss.id in (:ids) and alias.deleted=false "
                            + getQueryOrderClause());
            qry.setParameterList("ids", ids);
            @SuppressWarnings("unchecked")
            List<StudySiteAccrualStatus> queryList = qry.list();
            for (StudySiteAccrualStatus row : queryList) {
                result.put(row.getStudySite().getId(), row);
            }
        } catch (HibernateException e) {
            throw new PAException(e);
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StudySiteAccrualStatusDTO> getDeletedByStudySite(Ii studySiteIi)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        String hql = "select alias from StudySiteAccrualStatus alias join fetch alias.studySite ss "
                            + " where ss.id = :ssId"
                            + " and alias.deleted=true " + getQueryOrderClause();        
        Query query = session.createQuery(hql);
        query.setParameter("ssId", IiConverter.convertToLong(studySiteIi));
        @SuppressWarnings("unchecked")
        List<StudySiteAccrualStatus> queryList = query.list();
        StudySiteAccrualStatusConverter converter = new StudySiteAccrualStatusConverter();
        List<StudySiteAccrualStatusDTO> returnList = new ArrayList<StudySiteAccrualStatusDTO>();
        for (StudySiteAccrualStatus bo : queryList) {
            returnList.add(converter.convertFromDomainToDto(bo));
        }
        return returnList;
    }
    
    /**
     * 
     * @return query order clause string
     */
    protected String getQueryOrderClause() {
        return " order by alias.statusDate, alias.id";
    }
    
    /**
     * Checks the given condition and generates a PAException accordingly.
     * @param condition The condition that must cause a PAException
     * @param msg The message in the exception
     * @throws PAException thrown if the given condition is true.
     */
    private void checkCondition(boolean condition, String msg) throws PAException {
        if (condition) {
            throw new PAException(msg);
        }
    }

    /**
     * @return the studySiteAccrualAccessServiceLocal
     */
    public StudySiteAccrualAccessServiceLocal getStudySiteAccrualAccessServiceLocal() {
        return studySiteAccrualAccessServiceLocal;
    }

    /**
     * @param studySiteAccrualAccessServiceLocal the studySiteAccrualAccessServiceLocal to set
     */
    public void setStudySiteAccrualAccessServiceLocal(
            StudySiteAccrualAccessServiceLocal studySiteAccrualAccessServiceLocal) {
        this.studySiteAccrualAccessServiceLocal = studySiteAccrualAccessServiceLocal;
    }
}

package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.AccrualOutOfScopeTrial;
import gov.nih.nci.pa.noniso.dto.AccrualOutOfScopeTrialDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

/**
 * @author Denis G. Krylov
 */
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AccrualUtilityServiceBean implements AccrualUtilityServiceLocal {

    @SuppressWarnings("unchecked")
    @Override
    public List<AccrualOutOfScopeTrialDTO> getAllOutOfScopeTrials()
            throws PAException {
        List<AccrualOutOfScopeTrialDTO> list = new ArrayList<AccrualOutOfScopeTrialDTO>();
        Query query = PaHibernateUtil.getCurrentSession().createQuery(
                "from " + AccrualOutOfScopeTrial.class.getSimpleName());
        for (AccrualOutOfScopeTrial bo : (List<AccrualOutOfScopeTrial>) query
                .list()) {
            list.add(convert(bo));
        }
        return list;
    }

    private AccrualOutOfScopeTrialDTO convert(AccrualOutOfScopeTrial bo)
            throws PAException {
        if (bo == null) {
            return null;
        }
        AccrualOutOfScopeTrialDTO dto = new AccrualOutOfScopeTrialDTO();
        try {
            PropertyUtils.copyProperties(dto, bo);
            if (bo.getUser() != null) {
                dto.setUserLoginName(bo.getUser().getLoginName());
            }
        } catch (Exception e) {
            throw new PAException(e);
        }
        return dto;
    }

    @Override
    public void update(AccrualOutOfScopeTrialDTO dto) throws PAException {
        AccrualOutOfScopeTrial bo = (AccrualOutOfScopeTrial) PaHibernateUtil
                .getCurrentSession().load(AccrualOutOfScopeTrial.class,
                        dto.getId());
        convert(dto, bo);
        PaHibernateUtil.getCurrentSession().update(bo);
    }

    @Override
    public void delete(AccrualOutOfScopeTrialDTO dto) throws PAException {
        AccrualOutOfScopeTrial bo = (AccrualOutOfScopeTrial) PaHibernateUtil
                .getCurrentSession().load(AccrualOutOfScopeTrial.class,
                        dto.getId());
        PaHibernateUtil.getCurrentSession().delete(bo);
    }

    @Override
    public void create(AccrualOutOfScopeTrialDTO dto) throws PAException {
        AccrualOutOfScopeTrial bo = convert(dto);
        PaHibernateUtil.getCurrentSession().save(bo);
    }

    private AccrualOutOfScopeTrial convert(AccrualOutOfScopeTrialDTO dto)
            throws PAException {
        AccrualOutOfScopeTrial bo = new AccrualOutOfScopeTrial();
        convert(dto, bo);
        return bo;
    }

    private void convert(AccrualOutOfScopeTrialDTO dto,
            AccrualOutOfScopeTrial bo) throws PAException {
        try {
            PropertyUtils.copyProperties(bo, dto);
            if (StringUtils.isNotBlank(dto.getUserLoginName())) {
                bo.setUser(CSMUserService.getInstance().getCSMUser(
                        dto.getUserLoginName()));
            }
        } catch (Exception e) {
            throw new PAException(e);
        }

    }

    @Override
    public AccrualOutOfScopeTrialDTO getByCtepID(String ctepID)
            throws PAException {
        Query query = PaHibernateUtil.getCurrentSession().createQuery(
                "from " + AccrualOutOfScopeTrial.class.getSimpleName()
                        + " where ctepID=:ctepID");
        query.setParameter("ctepID", ctepID);
        return convert((AccrualOutOfScopeTrial) query.uniqueResult());
    }

}

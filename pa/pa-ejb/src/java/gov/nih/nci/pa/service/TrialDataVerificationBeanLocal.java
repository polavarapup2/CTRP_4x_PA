package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.TrialDataVerification;
import gov.nih.nci.pa.iso.convert.TrialVerificationDataConverter;
import gov.nih.nci.pa.iso.dto.TrialVerificationDataDTO;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.hibernate.Query;
import org.hibernate.Session;


/**
 * 
 * @author Reshma.Koganti
 * 
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class TrialDataVerificationBeanLocal
        extends
        AbstractBaseIsoService<TrialVerificationDataDTO, TrialDataVerification, TrialVerificationDataConverter>
        implements TrialDataVerificationServiceLocal {
    /**
     * {@inheritDoc}
     */
    @Override
    public TrialVerificationDataDTO create(TrialVerificationDataDTO dto) throws PAException {
        validate(dto);
        return super.create(dto);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public TrialVerificationDataDTO update(TrialVerificationDataDTO dto) throws PAException {
        validate(dto);
        return super.update(dto);
    }

    @Override
    public List<TrialVerificationDataDTO> getByStudyProtocol(Ii ii)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**                                        
     *                                         
     * @param studyProtocolId studyProtocolId  
     * @return list of TrialVerificationDataDTO
     * @throws PAException   PAException                   
     */                                        
    public List<TrialVerificationDataDTO> getDataByStudyProtocolId(Long studyProtocolId) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
//        session.flush();
        String hql = "from TrialDataVerification as tdv where tdv.studyProtocol.id= :studyProtocolId";
        Query query = session.createQuery(hql);
        query.setParameter("studyProtocolId", studyProtocolId.longValue());
        List<TrialDataVerification> trialDataList = query.list();
        return (List<TrialVerificationDataDTO>) convertFromDomainToDTOs(trialDataList);       
    }

}

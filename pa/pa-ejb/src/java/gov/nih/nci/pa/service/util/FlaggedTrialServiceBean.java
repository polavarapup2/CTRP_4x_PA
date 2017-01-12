/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolFlag;
import gov.nih.nci.pa.enums.StudyFlagReasonCode;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.noniso.dto.StudyProtocolFlagDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author dkrylov
 * 
 */
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FlaggedTrialServiceBean implements FlaggedTrialServiceLocal {

    /**
     * DUPE_FLAG
     */
    public static final String DUPE_FLAG = "This trial is already marked for the specified reason."
            + " Please select a different trial and/or reason";
    @EJB
    private StudyProtocolServiceLocal studyProtocolService;

    @SuppressWarnings("unchecked")
    @Override
    public List<StudyProtocolFlagDTO> getActiveFlaggedTrials() {
        return convert(PaHibernateUtil.getCurrentSession()
                .createCriteria(StudyProtocolFlag.class)
                .add(Restrictions.eq("deleted", Boolean.FALSE)).list());
    }

    @SuppressWarnings("unchecked")
    private List<StudyProtocolFlagDTO> convert(Collection<StudyProtocolFlag> c) {
        return new ArrayList<StudyProtocolFlagDTO>(CollectionUtils.collect(c,
                new Transformer() {
                    @Override
                    public Object transform(Object o) {
                        StudyProtocolFlag flag = (StudyProtocolFlag) o;
                        StudyProtocolFlagDTO dto = new StudyProtocolFlagDTO();
                        dto.setId(flag.getId());
                        dto.setComments(flag.getComments());
                        dto.setDeleteComments(flag.getDeleteComments());
                        dto.setDeleted(flag.getDeleted());
                        dto.setDeletedBy(flag.getDeletingUserName());
                        dto.setDeletedOn(flag.getDateDeleted());
                        dto.setFlaggedBy(flag.getFlaggingUserName());
                        dto.setFlaggedOn(flag.getDateFlagged());
                        dto.setNciID(flag.getNciID());
                        dto.setReason(flag.getFlagReason().getCode());
                        return dto;
                    }
                }));
    }

    @Override
    public void updateFlaggedTrial(Long id, StudyFlagReasonCode reason,
            String comments) throws PAException {
        Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocolFlag flag = (StudyProtocolFlag) s.load(
                StudyProtocolFlag.class, id);
        flag.setComments(comments);
        flag.setFlagReason(reason);
        try {
            s.update(flag);
            s.flush();
        } catch (ConstraintViolationException e) {
            throw new PAException(DUPE_FLAG, e);
        }

    }

    @Override
    public void addFlaggedTrial(String nciID, StudyFlagReasonCode code,
            String comments) throws PAException {
        Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocolDTO dto = findTrial(nciID);

        StudyProtocolFlag flag = new StudyProtocolFlag();
        flag.setComments(comments);
        flag.setDateFlagged(new Date());
        flag.setDeleted(false);
        flag.setFlaggingUser(CSMUserService.getInstance().getCSMUser(
                UsernameHolder.getUser()));
        flag.setFlagReason(code);
        flag.setStudyProtocol((StudyProtocol) s.load(StudyProtocol.class,
                IiConverter.convertToLong(dto.getIdentifier())));
        try {
            s.save(flag);
            s.flush();
        } catch (ConstraintViolationException e) {
            throw new PAException(DUPE_FLAG, e);
        }

    }

    /**
     * @param nciID
     * @return
     * @throws PAException
     */
    private StudyProtocolDTO findTrial(String nciID) throws PAException {
        try {
            Ii ii = new Ii();
            ii.setExtension(nciID.toUpperCase()); // NOPMD
            ii.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
            return studyProtocolService.getStudyProtocol(ii);
        } catch (PAException e) {
            throw new PAException(
                    "This trial does not exist in CTRP. Please enter a different NCI Identifier",
                    e);
        }
    }

    /**
     * @param studyProtocolService
     *            the studyProtocolService to set
     */
    public void setStudyProtocolService(
            StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    @Override
    public void delete(Long flaggedTrialID, String comment) throws PAException {
        Session s = PaHibernateUtil.getCurrentSession();
        StudyProtocolFlag flag = (StudyProtocolFlag) s.load(
                StudyProtocolFlag.class, flaggedTrialID);
        flag.setDeleteComments(comment);
        flag.setDateDeleted(new Date());
        flag.setDeleted(true);
        flag.setDeletingUser(CSMUserService.getInstance().getCSMUser(
                UsernameHolder.getUser()));
        s.save(flag);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StudyProtocolFlagDTO> getDeletedFlaggedTrials()
            throws PAException {
        return convert(PaHibernateUtil.getCurrentSession()
                .createCriteria(StudyProtocolFlag.class)
                .add(Restrictions.eq("deleted", Boolean.TRUE)).list());
    }

    @Override
    public boolean isFlagged(StudyProtocolDTO study, StudyFlagReasonCode code) throws PAException {
        return !PaHibernateUtil
                .getCurrentSession()
                .createCriteria(StudyProtocolFlag.class)
                .add(Restrictions.eq("studyProtocol",
                        StudyProtocolConverter.convertFromDTOToDomain(study)))
                .add(Restrictions.eq("flagReason", code))
                .add(Restrictions.eq("deleted", Boolean.FALSE)).list()
                .isEmpty();
    }
}

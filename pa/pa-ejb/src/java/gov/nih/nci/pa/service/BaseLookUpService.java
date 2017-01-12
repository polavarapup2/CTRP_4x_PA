/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.AbstractLookUpEntity;
import gov.nih.nci.pa.util.PaHibernateUtil;

import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;


/**
 * @author asharma
 *
 * @param <BO>
 */
public class BaseLookUpService<BO extends AbstractLookUpEntity> extends AbstractBaseSearchBean<BO> {
    private static final String UNCHECKED = "unchecked";
    private final Class<BO> typeArgument;

    /**
     *
     * @param typeArgument BO
     */
    public BaseLookUpService(Class<BO> typeArgument) {
        this.typeArgument = typeArgument;
    }

    /**
     * Gets By Id.
     *
     * @param boId the bo id
     *
     * @return the selected
     *
     * @throws PAException the PA exception
     */
    @SuppressWarnings(UNCHECKED)
    public BO getById(Long boId) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        return (BO) session.get(getTypeArgument(), boId);
    }

    /**
     * @return the typeArgument
     */
    public Class<BO> getTypeArgument() {
        return typeArgument;
    }
}

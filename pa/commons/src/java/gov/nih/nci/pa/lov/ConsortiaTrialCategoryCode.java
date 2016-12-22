package gov.nih.nci.pa.lov;

import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

/**
 * @author Denis G. Krylov
 * 
 */
@Entity
@Table(name = "consortia_category")
public class ConsortiaTrialCategoryCode extends AbstractLov {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger
            .getLogger(ConsortiaTrialCategoryCode.class);

    private String name;

    private String code;

    private String summary4FundingCodes;

    /**
     * @param code
     *            code
     * @return ConsortiaTrialCategoryCode
     */
    @SuppressWarnings("unchecked")
    public static ConsortiaTrialCategoryCode getByCode(String code) {
        try {
            if (StringUtils.isNotBlank(code)) {
                List<ConsortiaTrialCategoryCode> list = PaHibernateUtil
                        .getCurrentSession()
                        .createCriteria(ConsortiaTrialCategoryCode.class)
                        .add(Restrictions.eq("code", code)).list();
                return list.isEmpty() ? null : list.get(0);
            }

        } catch (Exception e) {
            LOG.error(e, e);
        }
        return null;
    }

    /**
     * construct a array of display names for ConsortiaTrialCategoryCode Enum.
     * 
     * @return String[] display names for ConsortiaTrialCategoryCode
     */    
    public static String[] getDisplayNames() {       
        return AbstractLov.loadDisplayNamesArray(values());
    }
    
    /**
     * @return list of all codes
     */
    @SuppressWarnings("unchecked")
    public static List<ConsortiaTrialCategoryCode> values() {
        return PaHibernateUtil.getCurrentSession()
                .createCriteria(ConsortiaTrialCategoryCode.class).list();
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {        
        return getName();
    }

    /**
     * @return the name
     */
    @Id
    @Column(name = "name")
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the code
     */
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the summary4FundingCodes
     */
    @Column(name = "summary4_funding_codes")
    public String getSummary4FundingCodes() {
        return summary4FundingCodes;
    }

    /**
     * @param summary4FundingCodes
     *            the summary4FundingCodes to set
     */
    public void setSummary4FundingCodes(String summary4FundingCodes) {
        this.summary4FundingCodes = summary4FundingCodes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ConsortiaTrialCategoryCode)) {
            return false;
        }
        ConsortiaTrialCategoryCode other = (ConsortiaTrialCategoryCode) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}

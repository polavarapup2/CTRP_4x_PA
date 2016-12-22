package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * This table represents the list of selected ID's to be displaye in he dropdown on the my account page.
 * @author Dirk
 *
 */
@Entity
@Table(name =  "account_common_organizations")
public class AccountCommonOrganization  implements PersistentObject  {
    private static final long serialVersionUID = 1234567890L;

    private long orgId;

    /**
     * gets the ctep organization id. 
     * @return the org id
     */
    @Override
    @Id
    @Column(name = "organization_id")
    public Long getId() {
        return orgId;
    }
    
    /**
     * Set the id.
     * @param organizationId the ctep org id to set.
     */
    public void setId(Long organizationId) {
        this.orgId = organizationId;
    }
}

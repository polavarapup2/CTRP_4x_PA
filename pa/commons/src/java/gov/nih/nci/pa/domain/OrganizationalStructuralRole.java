/**
 *
 */
package gov.nih.nci.pa.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.NotNull;

import com.fiveamsolutions.nci.commons.search.Searchable;

/**
 * @author Vrushali
 *
 */
@MappedSuperclass
public class OrganizationalStructuralRole extends StructuralRole {
    private static final long serialVersionUID = -3599890593921898089L;
    private Organization organization;
    /**
    *
    * @return organization
    */
   @ManyToOne
   @JoinColumn(name = "ORGANIZATION_IDENTIFIER", nullable = false)
   @NotNull
   @Searchable(nested = true)
   public Organization getOrganization() {
       return organization;
   }
   /**
    *
    * @param organization organization
    */
   public void setOrganization(Organization organization) {
       this.organization = organization;
   }
}

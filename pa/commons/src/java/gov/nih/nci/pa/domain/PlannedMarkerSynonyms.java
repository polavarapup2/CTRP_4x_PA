package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ActiveInactivePendingCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.fiveamsolutions.nci.commons.audit.Auditable;
import com.fiveamsolutions.nci.commons.search.Searchable;

/**
 * 
 * @author Reshma Koganti
 * 
 */
@Entity
@Table(name = "PLANNED_MARKER_SYNONYMS")
public class PlannedMarkerSynonyms extends AbstractEntity  implements Auditable {
    private static final long serialVersionUID = 1L;
    /** Maximum length of textDescription attribute. */
    public static final int TEXT_DESCRIPTION_LENGTH = 2000;
    /** Default length of the column. */
    private static final int DEFAULT_COLUMN_LENGTH = 200;
    private PlannedMarkerSyncWithCaDSR permissibleValue;
    private String alternativeName;
    private ActiveInactivePendingCode statusCode;
    
    /**
     * 
     * @return alternativeName alternativeName
     */
    @Column(name = "ALTERNATE_NAME", length = TEXT_DESCRIPTION_LENGTH, nullable = false)
    @NotNull
    @Searchable(caseSensitive = false, matchMode = Searchable.MATCH_MODE_EXACT)
    public String getAlternativeName() {
        return alternativeName;
    }
    /**
     * 
     * @param alternativeName alternativeName
     */
    public void setAlternativeName(String alternativeName) {
        this.alternativeName = alternativeName;
    }
    /**
    *
    * @return permisibleValue permisibleValue
    */
   @ManyToOne
   @JoinColumn(name = "PM_SYNC_IDENTIFIER")
    public PlannedMarkerSyncWithCaDSR getPermissibleValue() {
        return permissibleValue;
    }
   /**
   *
   * @param permissibleValue permissibleValue
   */
    public void setPermissibleValue(PlannedMarkerSyncWithCaDSR permissibleValue) {
        this.permissibleValue = permissibleValue;
    }
    
    /**
    *
    * @return statusCode
    */
   @Column(name = "STATUS_CODE", length = DEFAULT_COLUMN_LENGTH, nullable = false)
   @Enumerated(EnumType.STRING)
   @NotNull
   public ActiveInactivePendingCode getStatusCode() {
       return statusCode;
   }

   /**
   *
   * @param statusCode statusCode
   */
  public void setStatusCode(ActiveInactivePendingCode statusCode) {
      this.statusCode = statusCode;
  }

}

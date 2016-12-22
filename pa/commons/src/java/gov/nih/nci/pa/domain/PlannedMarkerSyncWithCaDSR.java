package gov.nih.nci.pa.domain;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.util.CommonsConstant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import com.fiveamsolutions.nci.commons.audit.Auditable;
import com.fiveamsolutions.nci.commons.search.Searchable;

/**
 * 
 * @author Reshma Koganti
 * 
 */
@Entity
@Table(name = "PLANNED_MARKER_SYNC_CADSR")
public class PlannedMarkerSyncWithCaDSR extends AbstractEntity  implements Auditable {
    private static final long serialVersionUID = 1L;
    private static final String PLANNED_MARKER_MAPPING = "permissibleValue";
    /** Maximum length of textDescription attribute. */
    public static final int TEXT_DESCRIPTION_LENGTH = 2000;
    /** Default length of the column. */
    private static final int DEFAULT_COLUMN_LENGTH = 200;
    private String name;
    private String meaning;
    private String description;
    private Long caDSRId;
    private ActiveInactivePendingCode statusCode;
    private String ntTermIdentifier;
    private String pvName;
    private List<PlannedMarker> plannedMarkers = new ArrayList<PlannedMarker>();
    /**
     * @return the name
     */
    @Column(name = "NAME", length = TEXT_DESCRIPTION_LENGTH, nullable = false, unique = true)
    @NotNull
    @Searchable(caseSensitive = false, matchMode = Searchable.MATCH_MODE_EXACT)
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
     * @return the meaning
     */
    @Column(name = "MEANING", length = TEXT_DESCRIPTION_LENGTH)
    public String getMeaning() {
        return meaning;
    }

    /**
     * @param meaning
     *            the meaning to set
     */
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    /**
     * @return the description
     */
    @Column(name = "DESCRIPTION", length = TEXT_DESCRIPTION_LENGTH)
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return caDSRId
     */
    @Column(name = "cadsrid", unique = true)
    public Long getCaDSRId() {
        return caDSRId;
    }

    /**
     * 
     * @param caDSRId
     *            caDSRId
     */
    public void setCaDSRId(Long caDSRId) {
        this.caDSRId = caDSRId;
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
  
  /**
   * @return the plannedMarkers
   */
  @OneToMany(mappedBy = PLANNED_MARKER_MAPPING)
  public List<PlannedMarker> getPlannedMarkers() {
      return plannedMarkers;
  }

  /**
   * @param plannedMarkers the plannedMarkers to set
   */
  public void setPlannedMarkers(List<PlannedMarker> plannedMarkers) {
      this.plannedMarkers = plannedMarkers;
  }
  
  /**
   * @return the ntTermIdentifier
   */
  @Column(name = "NT_TERM_IDENTIFIER")
  @Length(max = CommonsConstant.LONG_TEXT_LENGTH)
  public String getNtTermIdentifier() {
      return ntTermIdentifier;
  }
  /**
   * @param ntTermIdentifier the ntTermIdentifier to set
   */
  public void setNtTermIdentifier(String ntTermIdentifier) {
      this.ntTermIdentifier = ntTermIdentifier;
  }
  /**
   * 
   * @return pvName pvName
   */
  @Column(name = "pv_name")
  @Length(max = TEXT_DESCRIPTION_LENGTH)
  public String getPvName() {
      return pvName;
  }
  /**
   * 
   * @param pvName pvName
   */
  public void setPvName(String pvName) {
      this.pvName = pvName;
  }
  
  
}

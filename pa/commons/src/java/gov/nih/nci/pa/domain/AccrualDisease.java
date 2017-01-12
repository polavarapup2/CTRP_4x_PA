package gov.nih.nci.pa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import com.fiveamsolutions.nci.commons.search.Searchable;


/**
 * @author Hugh Reinhart
 * @since Dec 13, 2012
 */
@Entity
@Table(name = "ACCRUAL_DISEASE")
public class AccrualDisease implements PersistentObject, Disease {

    private static final long serialVersionUID = -15217297450287841L;

    private Long id;
    private String codeSystem;
    private String diseaseCode;
    private String preferredName;
    private String displayName;

    private List<StudySubject> studySubjects = new ArrayList<StudySubject>();

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDENTIFIER")
    @Searchable
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the codeSystem
     */
    @Column(name = "CODE_SYSTEM", updatable = false)
    @Searchable(matchMode = Searchable.MATCH_MODE_EXACT)
    @NotNull
    public String getCodeSystem() {
        return codeSystem;
    }
    /**
     * @param codeSystem the codeSystem to set
     */
    public void setCodeSystem(String codeSystem) {
        this.codeSystem = codeSystem;
    }
    /**
     * @return the diseaseCode
     */
    @Column(name = "DISEASE_CODE", updatable = false)
    @Searchable(matchMode = Searchable.MATCH_MODE_EXACT)
    @NotNull
    public String getDiseaseCode() {
        return diseaseCode;
    }
    /**
     * @param diseaseCode the diseaseCode to set
     */
    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }
    /**
     * @return the preferredName
     */
    @Column(name = "PREFERRED_NAME", updatable = false)
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    @NotNull
    public String getPreferredName() {
        return preferredName;
    }
    /**
     * @param preferredName the preferredName to set
     */
    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }
    /**
     * @return the displayName
     */
    @Column(name = "DISPLAY_NAME", updatable = false)
    @NotNull
    public String getDisplayName() {
        return displayName;
    }
    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    /**
     * @return the studySubjects
     */
    @OneToMany(mappedBy = "disease")
    public List<StudySubject> getStudySubjects() {
        return studySubjects;
    }
    /**
     * @param studySubjects the studySubjects to set
     */
    public void setStudySubjects(List<StudySubject> studySubjects) {
        this.studySubjects = studySubjects;
    }
}

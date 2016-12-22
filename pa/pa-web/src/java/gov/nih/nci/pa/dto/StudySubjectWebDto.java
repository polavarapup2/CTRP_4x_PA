package gov.nih.nci.pa.dto;


import java.io.Serializable;

/**
 *
 * @author Reshma Koganti
 */
public class StudySubjectWebDto implements Serializable {
    private static final long serialVersionUID = 2910126628394365838L;

    private String id;
    private String poID;
    private Long accuralCount;
    private String siteName;
    /**
     * 
     * @return id id
     */
    public String getId() {
        return id;
    }
    /**
     * 
     * @return poID poID
     */
    public String getPoID() {
        return poID;
    }
    /**
     * 
     * @return accuralCount accuralCount
     */
    public Long getAccuralCount() {
        return accuralCount;
    }
    /**
     * 
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * 
     * @param poID poID
     */
    public void setPoID(String poID) {
        this.poID = poID;
    }
    /**
     * 
     * @param accuralCount accuralCount
     */
    public void setAccuralCount(Long accuralCount) {
        this.accuralCount = accuralCount;
    }
    /**
     * 
     * @return siteName  siteName
     */
    public String getSiteName() {
        return siteName;
    }
    /**
     * 
     * @param siteName siteName
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}

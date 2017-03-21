package gov.nih.nci.pa.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * 
 * @author Reshma
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdditionalDesignDetailsDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("nci_id")
    private String nciId;
    @JsonProperty("study_protocol_id")
    private String studyProtocolId;
    @JsonProperty("date_updated")
    private String dateUpdated;
    @JsonProperty("model_description")
    private String modelDescription;
    @JsonProperty("masking_description")
    private String maskingDescription;
    @JsonProperty("no_masking")
    private String noMasking;
    /**
     * constructor
     */
    public AdditionalDesignDetailsDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @return id
     */
    public String getId() {
        return id;
    }
    /**
     * 
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * 
     * @return nciId
     */
    public String getNciId() {
        return nciId;
    }
    /**
     * 
     * @param nciId the nciId
     */
    public void setNciId(String nciId) {
        this.nciId = nciId;
    }
    /**
     * 
     * @return studyProtocolId
     */
    public String getStudyProtocolId() {
        return studyProtocolId;
    }
    /**
     * 
     * @param studyProtocolId the studyProtocolId
     */
    public void setStudyProtocolId(String studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }
    /**
     * 
     * @return dateUpdated
     */
    public String getDateUpdated() {
        return dateUpdated;
    }
    /**
     * 
     * @param dateUpdated the dateUpdated
     */
    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
    /**
     * 
     * @return modelDescription
     */
    public String getModelDescription() {
        return modelDescription;
    }
    /**
     *  
     * @param modelDescription the  modelDescription
     */
    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }
    /**
     * 
     * @return maskingDescription
     */
    public String getMaskingDescription() {
        return maskingDescription;
    }
    /**
     * 
     * @param maskingDescription the maskingDescription
     */
    public void setMaskingDescription(String maskingDescription) {
        this.maskingDescription = maskingDescription;
    }
    /**
     * 
     * @return noMasking
     */
    public String getNoMasking() {
        return noMasking;
    }
    /**
     * 
     * @param noMasking the noMasking
     */
    public void setNoMasking(String noMasking) {
        this.noMasking = noMasking;
    }
    
    
}

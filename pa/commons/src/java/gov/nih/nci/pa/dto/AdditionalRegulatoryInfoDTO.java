package gov.nih.nci.pa.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
/**
 * 
 * @author Purnima, Reshma
 *
 */
//CHECKSTYLE:OFF
@SuppressWarnings("PMD")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdditionalRegulatoryInfoDTO { // NOPMD
    private String study_protocol_id;
    private String nci_id;
    private String fda_regulated_drug;
    private String fda_regulated_device;
    private String post_prior_to_approval;
    private String ped_postmarket_surv;
    private String exported_from_us;
    private String date_updated;
    private String id;
    private String study_protocol_stage_id;
    
    /**
     * Constructor 
     */
    public AdditionalRegulatoryInfoDTO() {
        super();
    }
    /**
     * 
     * @param study_protocol_id the study_protocol_id
     * @param nci_id the nci_id
     * @param fda_regulated_drug the fda_regulated_drug
     * @param fda_regulated_device the fda_regulated_device
     * @param post_prior_to_approval the post_prior_to_approval
     * @param ped_postmarket_surv the ped_postmarket_surv
     * @param exported_from_us the exported_from_us
     * @param date_updated the date_updated
     * @param id the id
     */
    public AdditionalRegulatoryInfoDTO(String study_protocol_id, String nci_id,
            String fda_regulated_drug, String fda_regulated_device,
            String post_prior_to_approval, String ped_postmarket_surv,
            String exported_from_us, String date_updated, String id) {
        super();
        this.study_protocol_id = study_protocol_id;
        this.nci_id = nci_id;
        this.fda_regulated_drug = fda_regulated_drug;
        this.fda_regulated_device = fda_regulated_device;
        this.post_prior_to_approval = post_prior_to_approval;
        this.ped_postmarket_surv = ped_postmarket_surv;
        this.exported_from_us = exported_from_us;
        this.date_updated = date_updated;
        this.id = id;
    }
    /**
     * 
     * @return study_protocol_id
     */
    public String getStudy_protocol_id() {
        return study_protocol_id;
    }
    /**
     * 
     * @param study_protocol_id the study_protocol_id
     */
    public void setStudy_protocol_id(String study_protocol_id) {
        this.study_protocol_id = study_protocol_id;
    }
    /**
     * 
     * @return nci_id
     */
    public String getNci_id() {
        return nci_id;
    }
    /**
     * 
     * @param nci_id the nci_id
     */
    public void setNci_id(String nci_id) {
        this.nci_id = nci_id;
    }
    /**
     * 
     * @return fda_regulated_drug
     */
    public String getFda_regulated_drug() {
        return fda_regulated_drug;
    }
    /**
     * 
     * @param fda_regulated_drug the fda_regulated_drug
     */
    public void setFda_regulated_drug(String fda_regulated_drug) {
        this.fda_regulated_drug = fda_regulated_drug;
    }
    /**
     * 
     * @return fda_regulated_device the fda_regulated_device
     */
    public String getFda_regulated_device() {
        return fda_regulated_device;
    }
    /**
     * 
     * @param fda_regulated_device fda_regulated_device
     */
    public void setFda_regulated_device(String fda_regulated_device) {
        this.fda_regulated_device = fda_regulated_device;
    }
    /**
     * 
     * @return post_prior_to_approval
     */
    public String getPost_prior_to_approval() {
        return post_prior_to_approval;
    }
    /**
     * 
     * @param post_prior_to_approval the post_prior_to_approval
     */
    public void setPost_prior_to_approval(String post_prior_to_approval) {
        this.post_prior_to_approval = post_prior_to_approval;
    }
    /**
     * 
     * @return ped_postmarket_surv
     */
    public String getPed_postmarket_surv() {
        return ped_postmarket_surv;
    }
    /**
     * 
     * @param ped_postmarket_surv the ped_postmarket_surv
     */
    public void setPed_postmarket_surv(String ped_postmarket_surv) {
        this.ped_postmarket_surv = ped_postmarket_surv;
    }
    /**
     * 
     * @return exported_from_us 
     */
    public String getExported_from_us() {
        return exported_from_us;
    }
    /**
     * 
     * @param exported_from_us the exported_from_us
     */
    public void setExported_from_us(String exported_from_us) {
        this.exported_from_us = exported_from_us;
    }
    /**
     * 
     * @return data_updated
     */
    public String getDate_updated() {
        return date_updated;
    }
    /**
     * 
     * @param date_updated the date_updated
     */
    public void setDate_updated(String date_updated) {
        this.date_updated = date_updated;
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
     * @return study_protocol_stage_id
     */
    public String getStudy_protocol_stage_id() {
        return study_protocol_stage_id;
    }

    /**
     *
     * @param study_protocol_stage_id the study_protocol_stage_id
     */
    public void setStudy_protocol_stage_id(String study_protocol_stage_id) {
        this.study_protocol_stage_id = study_protocol_stage_id;
    }
    //CHECKSTYLE:ON
}

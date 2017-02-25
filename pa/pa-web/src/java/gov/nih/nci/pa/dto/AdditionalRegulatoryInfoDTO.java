package gov.nih.nci.pa.dto;
/**
 * 
 * @author Purnima, Reshma
 *
 */
public class AdditionalRegulatoryInfoDTO {
    private String fdaRegulatedDrug;
    private String fdaRegulatedDevice;
    private String postPriorToApproval;
    private String pedPostmarketSurv;
    private String exportedFromUs;
    /**
     * Constructor 
     */
    public AdditionalRegulatoryInfoDTO() {
        super();
    }
    /**
     * 
     * @param fdaRegulatedDrug the fdaRegulatedDrug
     * @param fdaRegulatedDevice the fdaRegulatedDevice
     * @param postPriorToApproval the postPriorToApproval
     * @param pedPostmarketSurv the  pedPostmarketSurv
     * @param exportedFromUs the exportedFromUs
     */
    public AdditionalRegulatoryInfoDTO(String fdaRegulatedDrug,
            String fdaRegulatedDevice, String postPriorToApproval,
            String pedPostmarketSurv, String exportedFromUs) {
        super();
        this.fdaRegulatedDrug = fdaRegulatedDrug;
        this.fdaRegulatedDevice = fdaRegulatedDevice;
        this.postPriorToApproval = postPriorToApproval;
        this.pedPostmarketSurv = pedPostmarketSurv;
        this.exportedFromUs = exportedFromUs;
    }

    /**
     * 
     * @return fdaRegulatedDrug
     */
    public String getFdaRegulatedDrug() {
        return fdaRegulatedDrug;
    }

    /**
     * 
     * @param fdaRegulatedDrug
     *            the fdaRegulatedDrug
     */
    public void setFdaRegulatedDrug(String fdaRegulatedDrug) {
        this.fdaRegulatedDrug = fdaRegulatedDrug;
    }

    /**
     * 
     * @return fdaRegulatedDevice
     */
    public String getFdaRegulatedDevice() {
        return fdaRegulatedDevice;
    }

    /**
     * 
     * @param fdaRegulatedDevice
     *            the fdaRegulatedDevice
     */
    public void setFdaRegulatedDevice(String fdaRegulatedDevice) {
        this.fdaRegulatedDevice = fdaRegulatedDevice;
    }

    /**
     * 
     * @return postPriorToApproval
     */
    public String getPostPriorToApproval() {
        return postPriorToApproval;
    }

    /**
     * 
     * @param postPriorToApproval
     *            the postPriorToApproval
     */
    public void setPostPriorToApproval(String postPriorToApproval) {
        this.postPriorToApproval = postPriorToApproval;
    }

    /**
     * 
     * @return pedPostmarketSurv
     */
    public String getPedPostmarketSurv() {
        return pedPostmarketSurv;
    }

    /**
     * 
     * @param pedPostmarketSurv
     *            the pedPostmarketSurv
     */
    public void setPedPostmarketSurv(String pedPostmarketSurv) {
        this.pedPostmarketSurv = pedPostmarketSurv;
    }

    /**
     * 
     * @return exportedFromUs
     */
    public String getExportedFromUs() {
        return exportedFromUs;
    }

    /**
     * 
     * @param exportedFromUs
     *            the exportedFromUs
     */
    public void setExportedFromUs(String exportedFromUs) {
        this.exportedFromUs = exportedFromUs;
    }

}

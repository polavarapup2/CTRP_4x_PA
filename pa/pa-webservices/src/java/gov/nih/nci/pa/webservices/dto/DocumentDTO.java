package gov.nih.nci.pa.webservices.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;



/**
 * 
 * @author Reshma
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentDTO {

    private boolean activeIndicator;
    private String fileName;
    private String typeCode;
    private String text;
    
    
    /**
     * const
     */
    public DocumentDTO() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * 
     * @param activeIndicator activeIndicator
     * @param fileName fileName
     * @param typeCode typeCode
     * @param text text
     */
    public DocumentDTO(boolean activeIndicator, String fileName,
            String typeCode, String text) {
        super();
        this.activeIndicator = activeIndicator;
        this.fileName = fileName;
        this.typeCode = typeCode;
        this.text = text;
    }
    /**
     * 
     * @return activeIndicator
     */
    public boolean isActiveIndicator() {
        return activeIndicator;
    }
    /**
     * 
     * @param activeIndicator the activeIndicator
     */
    public void setActiveIndicator(boolean activeIndicator) {
        this.activeIndicator = activeIndicator;
    }
    /**
     * 
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * 
     * @param fileName the fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * 
     * @return typeCode
     */
    public String getTypeCode() {
        return typeCode;
    }
    /**
     * 
     * @param typeCode the typeCode
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    /**
     * 
     * @return text
     */
    public String getText() {
        return text;
    }
    /**
     * 
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }
}

package gov.nih.nci.registry.dto;

import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import java.io.Serializable;

import org.apache.commons.lang.ArrayUtils;
/**
 *
 * @author Bala Nair
 */
public class TrialDocumentWebDTO implements Serializable {
    private static final long serialVersionUID = -8148568063972210023L;
    private String typeCode;
    private String fileName;
    private String id;
    private String inactiveCommentText;
    private byte[] text;
    private String studyProtocolId;

    /**
     * @param iso DocumentDTO object
     */
    public TrialDocumentWebDTO(DocumentDTO iso) {
        super();
        this.typeCode = CdConverter.convertCdToString(iso.getTypeCode());
        this.fileName = StConverter.convertToString(iso.getFileName());
        this.id = IiConverter.convertToString(iso.getIdentifier());
        this.inactiveCommentText = StConverter.convertToString(iso.getInactiveCommentText());
        this.text = EdConverter.convertToByte(iso.getText());
    }

    /** .
     *  Default Constructor
     */
    public TrialDocumentWebDTO() {
        super();
    }

    /**
     * @return typeCode
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * @param typeCode typeCode
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return inactiveCommentText
     */
    public String getInactiveCommentText() {
        return inactiveCommentText;
    }

    /**
     * @param inactiveCommentText inactiveCommentText
     */
    public void setInactiveCommentText(String inactiveCommentText) {
        this.inactiveCommentText = inactiveCommentText;
    }

    /**
     * @return the text
     */
    public byte[] getText() {
        return ArrayUtils.clone(text);
    }

    /**
     * @param text the text to set
     */
    public void setText(byte[] text) {
        byte[] temp = text;
        this.text = temp;

    }

    /**
     * @return the studyProtocolId
     */
    public String getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * @param studyProtocolId the studyProtocolId to set
     */
    public void setStudyProtocolId(String studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

}

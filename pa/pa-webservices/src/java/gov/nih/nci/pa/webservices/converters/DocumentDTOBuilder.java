/**
 * 
 */
package gov.nih.nci.pa.webservices.converters;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.webservices.types.AbbreviatedTrialUpdate;
import gov.nih.nci.pa.webservices.types.BaseTrialInformation;
import gov.nih.nci.pa.webservices.types.CompleteTrialAmendment;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;
import gov.nih.nci.pa.webservices.types.CompleteTrialUpdate;
import gov.nih.nci.pa.webservices.types.TrialDocument;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

/**
 * @author dkrylov
 * 
 */
public class DocumentDTOBuilder {

    /**
     * @param reg
     *            CompleteTrialRegistration
     * @return List<DocumentDTO>
     * @throws PAException
     *             PAException
     */
    public List<DocumentDTO> build(CompleteTrialRegistration reg)
            throws PAException {
        List<DocumentDTO> list = new ArrayList<>();
        convertDocumentsCommonToAmendmentAndRegistration(reg, list);
        return list;
    }

    /**
     * @param reg
     *            CompleteTrialAmendment
     * @return List<DocumentDTO>
     * @throws PAException
     *             PAException
     */
    public List<DocumentDTO> build(CompleteTrialAmendment reg)
            throws PAException {
        List<DocumentDTO> list = new ArrayList<>();
        convertDocumentsCommonToAmendmentAndRegistration(reg, list);
        convertAndStore(list, reg.getChangeMemoDocument(),
                DocumentTypeCode.CHANGE_MEMO_DOCUMENT, null);
        convertAndStore(list, reg.getProtocolHighlightDocument(),
                DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT, null);
        return list;
    }

    /**
     * @param reg
     * @param list
     * @throws PAException
     */
    private void convertDocumentsCommonToAmendmentAndRegistration(
            BaseTrialInformation reg, List<DocumentDTO> list)
            throws PAException {
        convertAndStore(list, reg.getProtocolDocument(),
                DocumentTypeCode.PROTOCOL_DOCUMENT, null);
        convertAndStore(list, reg.getIrbApprovalDocument(),
                DocumentTypeCode.IRB_APPROVAL_DOCUMENT, null);
        convertAndStore(list, reg.getParticipatingSitesDocument(),
                DocumentTypeCode.PARTICIPATING_SITES, null);
        convertAndStore(list, reg.getInformedConsentDocument(),
                DocumentTypeCode.INFORMED_CONSENT_DOCUMENT, null);
        for (TrialDocument doc : reg.getOtherDocument()) {
            convertAndStore(list, doc, DocumentTypeCode.OTHER, null);
        }
    }

    private void convertAndStore(List<DocumentDTO> list, TrialDocument doc,
            DocumentTypeCode type, Ii spID) throws PAException {
        if (doc != null) {
            DocumentDTO isoDTO = chooseNewOrExistingDocDTO(type, spID);
            isoDTO.setTypeCode(CdConverter.convertToCd(type));
            isoDTO.setFileName(StConverter.convertToSt(doc.getFilename()));
            isoDTO.setText(EdConverter.convertToEd(doc.getValue()));
            list.add(isoDTO);
        }
    }

    private DocumentDTO chooseNewOrExistingDocDTO(DocumentTypeCode type, Ii spID)
            throws PAException {
        if (ISOUtil.isIiNull(spID)) {
            return new DocumentDTO();
        }
        for (DocumentDTO doc : PaRegistry.getDocumentService()
                .getDocumentsByStudyProtocol(spID)) {
            if (type == CdConverter.convertCdToEnum(DocumentTypeCode.class,
                    doc.getTypeCode())) {
                return doc;
            }
        }
        return new DocumentDTO();
    }

    /**
     * @param spDTO
     *            StudyProtocolDTO
     * @param reg
     *            CompleteTrialUpdate
     * @return List<DocumentDTO>
     * @throws PAException
     *             PAException
     */
    public List<DocumentDTO> build(StudyProtocolDTO spDTO,
            CompleteTrialUpdate reg) throws PAException {
        List<DocumentDTO> list = new ArrayList<>();
        convertAndStore(list, reg.getProtocolDocument(),
                DocumentTypeCode.PROTOCOL_DOCUMENT, spDTO.getIdentifier());
        convertAndStore(list, reg.getIrbApprovalDocument(),
                DocumentTypeCode.IRB_APPROVAL_DOCUMENT, spDTO.getIdentifier());
        convertAndStore(list, reg.getParticipatingSitesDocument(),
                DocumentTypeCode.PARTICIPATING_SITES, spDTO.getIdentifier());
        convertAndStore(list, reg.getInformedConsentDocument(),
                DocumentTypeCode.INFORMED_CONSENT_DOCUMENT,
                spDTO.getIdentifier());
        for (TrialDocument doc : reg.getOtherDocument()) {
            convertAndStore(list, doc, DocumentTypeCode.OTHER, null);
        }
        return list;
    }

    /**
     * @param spDTO
     *            StudyProtocolDTO
     * @param reg
     *            AbbreviatedTrialUpdate
     * @return List<DocumentDTO>
     * @throws PAException
     *             PAException
     */
    public List<DocumentDTO> build(StudyProtocolDTO spDTO,
            AbbreviatedTrialUpdate reg) throws PAException {
        List<DocumentDTO> list = new ArrayList<>();
        convertAndStore(list, reg.getIrbApprovalDocument(),
                DocumentTypeCode.IRB_APPROVAL_DOCUMENT, null);
        convertAndStore(list, reg.getInformedConsentDocument(),
                DocumentTypeCode.INFORMED_CONSENT_DOCUMENT, null);

        // ProprietaryTrialManagementBeanLocal.update has an odd way of handling
        // the documents.
        List<DocumentDTO> existingDocs = PaRegistry.getDocumentService()
                .getDocumentsByStudyProtocol(spDTO.getIdentifier());
        for (DocumentDTO doc : existingDocs) {
            if (!hasDocOfType(list, doc.getTypeCode())
                    || DocumentTypeCode.OTHER == CdConverter.convertCdToEnum(
                            DocumentTypeCode.class, doc.getTypeCode())) {
                list.add(doc);
            }
        }

        for (TrialDocument doc : reg.getOtherDocument()) {
            convertAndStore(list, doc, DocumentTypeCode.OTHER, null);
        }

        return list;
    }

    private boolean hasDocOfType(final List<DocumentDTO> list, final Cd cd) {
        return CollectionUtils.find(list, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                DocumentDTO doc = (DocumentDTO) o;
                return StringUtils.equals(cd.getCode(), doc.getTypeCode()
                        .getCode());
            }
        }) != null;
    }

}

/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.convert.DocumentConverter;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.DocumentSortCriterion;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

/**
 * @author asharma
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class DocumentBeanLocal extends AbstractStudyIsoService<DocumentDTO, Document, DocumentConverter> implements
        DocumentServiceLocal { //NOPMD
    
    
    @EJB
    private StudyProtocolServiceLocal studyProtocolService;

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DocumentDTO> getDocumentsByStudyProtocol(Ii studyProtocolIi) throws PAException {
        protocolIdCheck(studyProtocolIi);

        Document criteria = new Document();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(IiConverter.convertToLong(studyProtocolIi));
        criteria.setStudyProtocol(sp);
        criteria.setActiveIndicator(Boolean.TRUE);

        PageSortParams<Document> params =
            new PageSortParams<Document>(PAConstants.MAX_SEARCH_RESULTS, 0, DocumentSortCriterion.DOCUMENT_ID, false);

        List<Document> inputDocumentsList = search(new AnnotatedBeanSearchCriteria<Document>(criteria), params);
        
        List<Document> results =  filterDocuments(inputDocumentsList , false);
        return convertFromDomainToDTOs(results);
    }
    
    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DocumentDTO> getReportsDocumentsByStudyProtocol(Ii studyProtocolIi) throws PAException {
        protocolIdCheck(studyProtocolIi);

        Document criteria = new Document();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(IiConverter.convertToLong(studyProtocolIi));
        criteria.setStudyProtocol(sp);
        criteria.setActiveIndicator(Boolean.TRUE);

        PageSortParams<Document> params =
            new PageSortParams<Document>(PAConstants.MAX_SEARCH_RESULTS, 0, DocumentSortCriterion.DOCUMENT_ID, false);

        List<Document> inputDocumentsList = search(new AnnotatedBeanSearchCriteria<Document>(criteria), params);
        
        List<Document> results =  filterDocuments(inputDocumentsList , true);
        return convertFromDomainToDTOs(results);
    }
    
    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DocumentDTO getComparisonDocumentByStudyProtocol(Ii studyProtocolIi) throws PAException {
        protocolIdCheck(studyProtocolIi);

        Document criteria = new Document();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(IiConverter.convertToLong(studyProtocolIi));
        criteria.setStudyProtocol(sp);
        criteria.setActiveIndicator(Boolean.TRUE);

        PageSortParams<Document> params =
            new PageSortParams<Document>(PAConstants.MAX_SEARCH_RESULTS, 0, DocumentSortCriterion.DOCUMENT_ID, false);

        List<Document> inputDocumentsList = search(new AnnotatedBeanSearchCriteria<Document>(criteria), params);
        
        Document result =  filterComparisonDocument(inputDocumentsList);
        
        if (result != null) {
            return convertFromDomainToDto(result);
        }
        
        return null;
    }
    
    private Document filterComparisonDocument(List<Document> inputDocumentsList) {     
        
        for (Document document : inputDocumentsList) {
            if (document.getTypeCode() == DocumentTypeCode.COMPARISON) {
                    return document;
            }            
        }        
        return null;
    }
    
    private List<Document> filterDocuments(List<Document> inputDocumentsList , boolean isReportsOnly) {
        List<Document> results = new ArrayList<Document>();
        
        for (Document document : inputDocumentsList) {
            
            if (!isReportsOnly) {
                if (!document.getTypeCode().getShortName().contains("_reportdoc")) {
                    results.add(document);
                }
            } else {
                if (document.getTypeCode().getShortName().contains("_reportdoc")) {
                    results.add(document);
                }
            }
        }
        
        return results;
    }

    /**
     * @param studyProtocolIi
     * @throws PAException PAException
     */
    private void protocolIdCheck(Ii studyProtocolIi) throws PAException {
        if (ISOUtil.isIiNull(studyProtocolIi)) {
            throw new PAException("studyProtocol Identifier should not be null.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentDTO create(DocumentDTO docDTO) throws PAException {
        validate(docDTO);
        enforceDuplicateDocument(docDTO);
        return createDocument(docDTO);
    }

    /**
     * @param docDTO
     * @return
     * @throws PAException
     */
    private DocumentDTO createDocument(DocumentDTO docDTO) throws PAException {
        docDTO.setActiveIndicator(BlConverter.convertToBl(Boolean.TRUE));
        docDTO.setDeleted(BlConverter.convertToBl(Boolean.FALSE));
        if (docDTO.getOriginal() == null) {
            docDTO.setOriginal(BlConverter.convertToBl(Boolean.FALSE));
        }
        DocumentDTO saved = super.create(docDTO);
        saved.setText(docDTO.getText());
        saved.setFileName(docDTO.getFileName());
        saveFile(saved);
        return saved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DocumentDTO get(Ii id) throws PAException {
        DocumentDTO docDTO = super.get(id);
        if (docDTO != null) {
            try {
                StudyProtocolBeanLocal spBean = new StudyProtocolBeanLocal();
                String nciIdentifier =
                    PAUtil.getAssignedIdentifierExtension(spBean.getStudyProtocol(docDTO.getStudyProtocolIdentifier()));
                String docPath = PAUtil.getDocumentFilePath(IiConverter.convertToLong(docDTO.getIdentifier()),
                        StConverter.convertToString(docDTO.getFileName()), nciIdentifier);
                File downloadFile = new File(docPath);
                createFileIfNeedBe(downloadFile);
                final FileInputStream stream = FileUtils.openInputStream(downloadFile);
                docDTO.setText(EdConverter.convertToEd(IOUtils.toByteArray(stream)));
                IOUtils.closeQuietly(stream);
            } catch (FileNotFoundException fe) {
                throw new PAException("File Not found " + fe.getLocalizedMessage(), fe);
            } catch (IOException io) {
                throw new PAException("IO Exception" + io.getLocalizedMessage(), io);
            }
        }
        return docDTO;
    }

    /**
     * @param file
     * @throws IOException
     */
    private void createFileIfNeedBe(File file) throws IOException {
        if (Boolean.valueOf(System.getProperty("ctrp.env.ci"))
                && !file.exists()) {
            file.getParentFile().mkdirs();
            FileUtils.writeStringToFile(file, " ");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentDTO update(DocumentDTO docDTO) throws PAException {
        validate(docDTO);
        
        // We no longer update document records directly; instead we retire old versions and create new ones.
        // That is needed to preserve history. 
        DocumentDTO docToUpdate = get(docDTO.getIdentifier());
        docToUpdate.setActiveIndicator(BlConverter.convertToBl(Boolean.FALSE));
        docToUpdate.setDeleted(BlConverter.convertToBl(false));
        super.update(docToUpdate);        
        
        docDTO.setInactiveCommentText(null);
        docDTO.setIdentifier(null);
        docDTO.setOriginal(BlConverter.convertToBl(Boolean.FALSE));
        return createDocument(docDTO); 
    }
    

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Ii documentIi) throws PAException {
        this.delete(documentIi, StConverter.convertToSt(null));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void forceDelete(Ii documentIi) throws PAException {
        if (ISOUtil.isIiNull(documentIi)) {
            throw new PAException("Document Ii should not be null.");
        }
        DocumentDTO docDTO = get(documentIi);        
        docDTO.setActiveIndicator(BlConverter.convertToBl(Boolean.FALSE));
        docDTO.setDeleted(BlConverter.convertToBl(true));
        super.update(docDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi) throws PAException {
        Map<Ii, Ii> map = super.copy(fromStudyProtocolIi, toStudyProtocolIi);
        List<DocumentDTO> dtos = getByStudyProtocol(fromStudyProtocolIi);
        Session session = PaHibernateUtil.getCurrentSession();
        String fromName = null;
        String toName = null;
        StudyProtocolBeanLocal spBean = new StudyProtocolBeanLocal();
        String nciIdentifier = PAUtil.getAssignedIdentifierExtension(spBean.getStudyProtocol(fromStudyProtocolIi));

        for (DocumentDTO dto : dtos) {
            Document doc = (Document) session.load(Document.class, IiConverter.convertToLong(dto.getIdentifier()));
            Ii toIi = PAUtil.containsIi(map, dto.getIdentifier());
            if (toIi != null) {
                // rename the file
                fromName = PAUtil.getDocumentFilePath(doc.getId(), doc.getFileName(), nciIdentifier);
                toName = PAUtil.getDocumentFilePath(Long.valueOf(toIi.getExtension()),
                        doc.getFileName(), nciIdentifier);
                try {
                    final File fromFile = new File(fromName);
                    createFileIfNeedBe(fromFile);
                    FileUtils.copyFile(fromFile, new File(toName));
                } catch (IOException e) {
                    throw new PAException("Error while copy file from " + fromName + " to " + toName, e);
                }
            }
            session.delete(doc);
        }
        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(DocumentDTO docDTO) throws PAException {
        super.validate(docDTO);
        if (ISOUtil.isEdNull(docDTO.getText())) {
            throw new PAValidationException("Document data cannot be null ");
        }
    }

    private void enforceDuplicateDocument(DocumentDTO docDTO) throws PAException {
        List<DocumentDTO> resultList = getDocumentsByStudyProtocol(docDTO.getStudyProtocolIdentifier());
        resultList.addAll(getReportsDocumentsByStudyProtocol(docDTO.getStudyProtocolIdentifier()));
        
        for (DocumentDTO check : resultList) {
            if (!StringUtils.equalsIgnoreCase(check.getTypeCode().getCode(), DocumentTypeCode.OTHER.getCode())
                    && !StringUtils.equalsIgnoreCase(check.getTypeCode().getCode(), DocumentTypeCode.TSR.getCode())
                    && StringUtils.equals(check.getTypeCode().getCode(), docDTO.getTypeCode().getCode())) {
                throw new PADuplicateException("Document with selected type already exists on the trial.");
            }
        }
    }

    private void checkTypeCodesForDelete(DocumentDTO docDTO) throws PAException {
        if (docDTO.getTypeCode().getCode().equals(DocumentTypeCode.PROTOCOL_DOCUMENT.getCode())
                || docDTO.getTypeCode().getCode().equals(DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode())
                || docDTO.getTypeCode().getCode().equals(DocumentTypeCode.CHANGE_MEMO_DOCUMENT.getCode())) {
            throw new PAException("Document with selected type cannot be deleted. ");
        }
    }

    /**
     * This saves the document its final storage place if the studies nci identifier has been set. Otherwise,
     * it is stored  in a temporary location and needs to be later moved into its final location once the assigned id
     * of the related study protocol has been set.
     * @param docDTO
     * @throws PAException on error
     */
    private void saveFile(DocumentDTO docDTO) throws PAException {
        StudyProtocolBeanLocal spBean = new StudyProtocolBeanLocal();
        String nciIdentifier =
            PAUtil.getAssignedIdentifierExtension(spBean.getStudyProtocol(docDTO.getStudyProtocolIdentifier()));
        String docPath;
        if (StringUtils.isEmpty(nciIdentifier)) {
            docPath = PAUtil.getTemporaryDocumentFilePath(docDTO);
        } else {
            docPath = PAUtil.getDocumentFilePath(IiConverter.convertToLong(docDTO.getIdentifier()),
                    StConverter.convertToString(docDTO.getFileName()), nciIdentifier);
        }

        try {
            File outFile = new File(docPath);
            FileUtils.writeByteArrayToFile(outFile, docDTO.getText().getData());            
        } catch (IOException e) {
            throw new PAException("Error while attempting to save the file to " + docPath, e);
        }
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.DocumentService#getDocumentsAndAllTSRByStudyProtocol(gov.nih.nci.iso21090.Ii)
     */
    @Override
    public List<DocumentDTO> getDocumentsAndAllTSRByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        List<DocumentDTO> docs = getDocumentsByStudyProtocol(studyProtocolIi);

        StudyProtocolDTO spDTO = studyProtocolService
                .getStudyProtocol(studyProtocolIi);
        StudyProtocolDTO toSearchspDTO = new StudyProtocolDTO();
        toSearchspDTO.setSecondaryIdentifiers(DSetConverter
                .convertIiToDset(PAUtil.getAssignedIdentifier(spDTO)));
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        toSearchspDTO.setStatusCode(CdConverter
                .convertToCd(ActStatusCode.INACTIVE));
        List<StudyProtocolDTO> spList = null;
        try {
            spList = studyProtocolService.search(toSearchspDTO, limit);
        } catch (TooManyResultsException e) {
            throw new PAException(e);
        }

        Set<DocumentDTO> previousTSRs = new TreeSet<DocumentDTO>(
                documentComparator());
        for (StudyProtocolDTO previousProtocolDTO : spList) {
            List<DocumentDTO> previousDocs = getDocumentsByStudyProtocol(previousProtocolDTO
                    .getIdentifier());
            for (DocumentDTO doc : previousDocs) {
                if (CdConverter.convertToCd(DocumentTypeCode.TSR).equals(
                        doc.getTypeCode())) {
                    previousTSRs.add(doc);
                }
            }
        }
        docs.addAll(previousTSRs);
        Collections.sort(docs, documentComparator());
        return docs;
    }

    /**
     * @return
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    private Comparator<DocumentDTO> documentComparator() {
        return new Comparator<DocumentDTO>() {
            @Override
            public int compare(DocumentDTO o1, DocumentDTO o2) { // NOPMD
                Timestamp date1 = TsConverter.convertToTimestamp(o1
                        .getDateLastUpdated());
                Timestamp date2 = TsConverter.convertToTimestamp(o2
                        .getDateLastUpdated());
                if (date1 == null && date2 == null) {
                    return -1;
                }
                if (date1 == null && date2 != null) {
                    return -1;
                }
                if (date1 != null && date2 == null) {
                    return 1;
                }
                return -date1.compareTo(date2);
            }
        };
    }

    /**
     * @return the studyProtocolService
     */
    public StudyProtocolServiceLocal getStudyProtocolService() {
        return studyProtocolService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(
            StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    @Override
    public void markAsOriginalSubmission(List<DocumentDTO> savedDocs)
            throws PAException {
        if (savedDocs != null) {
            for (DocumentDTO dto : savedDocs) {
                DocumentDTO doc = get(dto.getIdentifier());
                if (doc != null) {
                    doc.setOriginal(BlConverter.convertToBl(true));
                    super.update(doc);
                }
            }
        }
    }

    @Override
    public void associateDocumentsWithStudyInbox(List<DocumentDTO> savedDocs,
            StudyInboxDTO inbox) throws PAException {
        if (savedDocs != null) {            
            for (DocumentDTO dto : savedDocs) {
                DocumentDTO doc = get(dto.getIdentifier());
                if (doc != null) {                    
                    doc.setStudyInboxIdentifier(inbox.getIdentifier());
                    super.update(doc);
                }
            }
        }        
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DocumentDTO> getOriginalDocumentsByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        protocolIdCheck(studyProtocolIi);

        Document criteria = new Document();
        StudyProtocol sp = new StudyProtocol();
        sp.setId(IiConverter.convertToLong(studyProtocolIi));
        criteria.setStudyProtocol(sp);
        criteria.setOriginal(Boolean.TRUE);

        PageSortParams<Document> params = new PageSortParams<Document>(
                PAConstants.MAX_SEARCH_RESULTS, 0,
                DocumentSortCriterion.DOCUMENT_ID, false);

        List<Document> results = search(
                new AnnotatedBeanSearchCriteria<Document>(criteria), params);
        // zero results means this protocol is pre-3.9, which means we have to
        // fall back to the old
        // functionality rather than totally suppress protocol document list.
        // For pre-3.9 trials,
        // document list might not be exactly 'original'.
        if (CollectionUtils.isEmpty(results)) {             
            return getDocumentsByStudyProtocol(studyProtocolIi);
        } else {
            return convertFromDomainToDTOs(results);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DocumentDTO> getOriginalDocumentsByStudyInbox(StudyInboxDTO dto)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery("from " + Document.class.getName()
                + " where studyInbox.id = "
                + dto.getIdentifier().getExtension());
        return convertFromDomainToDTOs(query.list());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DocumentDTO> getDeletedDocumentsByTrial(Ii studyProtocolIi)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session
                .createQuery("from "
                        + Document.class.getName()
                        + " where studyProtocol.id = "
                        + studyProtocolIi.getExtension()
                        + " and activeIndicator=false and (deleted is null or deleted=true) "
                        + "order by dateLastUpdated desc");
        return convertFromDomainToDTOs(query.list());
    }

    @Override
    public void delete(Ii documentIi, St reasonToDelete) throws PAException {
        if (ISOUtil.isIiNull(documentIi)) {
            throw new PAException("Document Ii should not be null.");
        }
        DocumentDTO docDTO = get(documentIi);
        checkTypeCodesForDelete(docDTO);
        docDTO.setActiveIndicator(BlConverter.convertToBl(Boolean.FALSE));
        docDTO.setDeleted(BlConverter.convertToBl(true));
        docDTO.setInactiveCommentText(reasonToDelete);
        super.update(docDTO);        
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public  Map<Long, DocumentDTO> getDocumentByIDListAndType(List<Long> listOfTrialIDs
           , DocumentTypeCode type) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        Map<Long, DocumentDTO> resultSet = new HashMap<Long, DocumentDTO>();
        if (CollectionUtils.isEmpty(listOfTrialIDs)) {
            return resultSet;    //PO-9245
        }

        String hql = "from Document as doc where doc.studyProtocol.id in (:ids) "
               + "and doc.activeIndicator=true and doc.typeCode=:type";
        Query query = session.createQuery(hql);
        query.setParameterList("ids", listOfTrialIDs);
        query.setParameter("type", type);
        List<Document> documents = query.list();
        for (Document document : documents) {
              if (!documents.isEmpty()) {
                  DocumentDTO dto = (DocumentDTO) convertFromDomainToDto(document);
                  resultSet.put(IiConverter.convertToLong(dto.getStudyProtocolIdentifier()), dto);
              }
            }
        return resultSet;
    }

    @Override
    public void updateForReview(DocumentDTO docDTO) throws PAException {
        super.update(docDTO);    
        
    }

    
}

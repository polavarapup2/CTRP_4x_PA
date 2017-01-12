/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class DocumentServiceBeanTest extends AbstractHibernateTestCase {
    private final DocumentBeanLocal remoteEjb = new DocumentBeanLocal();
    private Ii pid;

    @Before
    public void init() throws Exception {
        TestSchema.primeData();
        pid = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
        CSMUserService.setInstance(new MockCSMUserService());
        remoteEjb.setStudyProtocolService(new StudyProtocolBeanLocal());
    }

    @Test
    public void create() throws Exception {
        DocumentDTO docDTO = new DocumentDTO();
        docDTO.setStudyProtocolIdentifier(pid);
        docDTO.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));
        docDTO.setFileName(StConverter.convertToSt("Informed_Consent.doc"));
        docDTO.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        remoteEjb.create(docDTO);

        docDTO = new DocumentDTO();
        docDTO.setStudyProtocolIdentifier(pid);
        docDTO.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.OTHER));
        docDTO.setFileName(StConverter.convertToSt("Other.doc"));
        docDTO.setText(EdConverter.convertToEd("Other".getBytes()));
        remoteEjb.create(docDTO);
    }

    @Test(expected = PAException.class)
    public void testFailedCreate() throws Exception {
        DocumentDTO docDTO = new DocumentDTO();
        docDTO.setStudyProtocolIdentifier(pid);
        docDTO.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));
        docDTO.setFileName(StConverter.convertToSt("Protocol_Document.doc"));
        docDTO.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        remoteEjb.create(docDTO);
    }
    
    @Test
    public void testCreateDupTSR() throws Exception {
        
        StudyProtocol sp = new StudyProtocol();
        sp.setId(TestSchema.studyProtocolIds.get(0));
        
        Document doc = new Document();
        doc.setStudyProtocol(sp);
        doc.setTypeCode(DocumentTypeCode.TSR);
        doc.setActiveIndicator(true);
        doc.setFileName("TSR.doc");
        TestSchema.addUpdObject(doc);        
        
        // duplicate TSRs are now permitted, so no exception is expected here.
        // see https://tracker.nci.nih.gov/browse/PO-2106?focusedCommentId=139596#comment-139596
        DocumentDTO docDTO = new DocumentDTO();
        docDTO.setStudyProtocolIdentifier(pid);
        docDTO.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.TSR));
        docDTO.setFileName(StConverter.convertToSt("TSR.rtf"));
        docDTO.setText(EdConverter.convertToEd("TSR".getBytes()));
        remoteEjb.create(docDTO);
    }
    

    @Test
    public void testGet() throws Exception {
        DocumentDTO docDTO = new DocumentDTO();
        docDTO.setStudyProtocolIdentifier(pid);
        docDTO.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.OTHER));
        docDTO.setFileName(StConverter.convertToSt("Other.doc"));
        docDTO.setText(EdConverter.convertToEd("Other".getBytes()));
        docDTO = remoteEjb.create(docDTO);

        docDTO = remoteEjb.get(docDTO.getIdentifier());
        assertNotNull(docDTO);
        assertFalse(ISOUtil.isEdNull(docDTO.getText()));
    }
    
    @Test
    public void testGetDocumentsAndAllTSRByStudyProtocol() throws PAException, ParseException {
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery("delete Document");
        query.executeUpdate();
        session.flush();
        
        StudyProtocol sp = new StudyProtocol();
        sp.setId(TestSchema.studyProtocolIds.get(0));
        
        StudyProtocol origSp = new StudyProtocol();
        origSp.setId(TestSchema.inactiveProtocolId);
        
        Document doc1 = new Document();
        doc1.setStudyProtocol(origSp);
        doc1.setTypeCode(DocumentTypeCode.TSR);
        doc1.setActiveIndicator(true);
        doc1.setFileName("TSR1.doc");
        doc1.setDateLastUpdated(DateUtils.parseDate("01/01/2012", new String[] {"MM/dd/yyyy"}));
        TestSchema.addUpdObject(doc1);
        
        Document doc2 = new Document();
        doc2.setStudyProtocol(origSp);
        doc2.setTypeCode(DocumentTypeCode.TSR);
        doc2.setActiveIndicator(true);
        doc2.setFileName("TSR2.doc");
        doc2.setDateLastUpdated(DateUtils.parseDate("12/31/2011", new String[] {"MM/dd/yyyy"}));
        TestSchema.addUpdObject(doc2);
        
        Document doc3 = new Document();
        doc3.setStudyProtocol(sp);
        doc3.setTypeCode(DocumentTypeCode.TSR);
        doc3.setActiveIndicator(true);
        doc3.setFileName("TSR3.doc");
        doc3.setDateLastUpdated(DateUtils.parseDate("02/01/2012", new String[] {"MM/dd/yyyy"}));
        TestSchema.addUpdObject(doc3);      
        
        List<DocumentDTO> list = remoteEjb.getDocumentsAndAllTSRByStudyProtocol(pid);
        assertEquals(3, list.size());     
        assertEquals("TSR3.doc", list.get(0).getFileName().getValue());
        assertEquals("TSR1.doc", list.get(1).getFileName().getValue());
        assertEquals("TSR2.doc", list.get(2).getFileName().getValue());
    }
    
    @Test
    public void testGetComparisonDocumentByStudyProtocol() throws PAException, ParseException {
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery("delete Document");
        query.executeUpdate();
        session.flush();
        
        StudyProtocol sp = new StudyProtocol();
        sp.setId(TestSchema.studyProtocolIds.get(0));
        
        StudyProtocol origSp = new StudyProtocol();
        origSp.setId(TestSchema.inactiveProtocolId);
        
        Document doc1 = new Document();
        doc1.setStudyProtocol(sp);
        doc1.setTypeCode(DocumentTypeCode.COMPARISON);
        doc1.setActiveIndicator(true);
        doc1.setFileName("COMPARISON.doc");
        doc1.setDateLastUpdated(DateUtils.parseDate("01/01/2012", new String[] {"MM/dd/yyyy"}));
        doc1.setCcctUserCreatedDate(DateUtils.parseDate("01/01/2013", new String[] {"MM/dd/yyyy"}));
        doc1.setCtroUserCreatedDate(DateUtils.parseDate("01/01/2013", new String[] {"MM/dd/yyyy"}));
        TestSchema.addUpdObject(doc1);
        
        Document doc2 = new Document();
        doc2.setStudyProtocol(origSp);
        doc2.setTypeCode(DocumentTypeCode.TSR);
        doc2.setActiveIndicator(true);
        doc2.setFileName("TSR2.doc");
        doc2.setDateLastUpdated(DateUtils.parseDate("12/31/2011", new String[] {"MM/dd/yyyy"}));
        TestSchema.addUpdObject(doc2);
        
        Document doc3 = new Document();
        doc3.setStudyProtocol(sp);
        doc3.setTypeCode(DocumentTypeCode.TSR);
        doc3.setActiveIndicator(true);
        doc3.setFileName("TSR3.doc");
        doc3.setDateLastUpdated(DateUtils.parseDate("02/01/2012", new String[] {"MM/dd/yyyy"}));
        TestSchema.addUpdObject(doc3);      
        
        DocumentDTO document = remoteEjb.getComparisonDocumentByStudyProtocol(pid);        
        assertEquals("COMPARISON.doc", document.getFileName().getValue());        
    }

    @Test
    public void iiRootTest() throws Exception {
        List<DocumentDTO> statusList = remoteEjb.getDocumentsByStudyProtocol(pid);
        assertTrue(statusList.size() > 0);
        DocumentDTO dto = statusList.get(0);
        assertEquals(dto.getIdentifier().getRoot(), IiConverter.DOCUMENT_ROOT);
        assertTrue(StringUtils.isNotEmpty(dto.getIdentifier().getIdentifierName()));
        assertEquals(dto.getStudyProtocolIdentifier().getRoot(), IiConverter.STUDY_PROTOCOL_ROOT);
    }

    @Test
    public void testUpdate() throws Exception {
        DocumentDTO docDTO = new DocumentDTO();
        docDTO.setStudyProtocolIdentifier(pid);
        docDTO.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.OTHER));
        docDTO.setFileName(StConverter.convertToSt("Other Document.doc"));
        docDTO.setText(EdConverter.convertToEd("Other Document".getBytes()));

        docDTO = remoteEjb.create(docDTO);
        remoteEjb.update(docDTO);
    }

    @Test
    public void testDelete() throws Exception {
        try {
            remoteEjb.delete(null);
            fail("Ii should not be null");
        } catch(PAException e) {
            assertEquals("Document Ii should not be null.", e.getMessage());
        }

        DocumentDTO docDTO = new DocumentDTO();
        docDTO.setStudyProtocolIdentifier(pid);
        docDTO.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.OTHER));
        docDTO.setFileName(StConverter.convertToSt("Other Document.doc"));
        docDTO.setText(EdConverter.convertToEd("Other Document".getBytes()));

        docDTO = remoteEjb.create(docDTO);
        docDTO = remoteEjb.get(docDTO.getIdentifier());
        remoteEjb.delete(docDTO.getIdentifier());
    }
    
    @Test
    public void testGetDocumentByIDListAndType() throws PAException {
        //Given 2 protocol-ids
        List<Long> listOfTrialIDs = new ArrayList<Long>();
        listOfTrialIDs.add(1L);
        listOfTrialIDs.add(2L);

        //when I ask for protocol-document
        Map<Long, DocumentDTO> map = remoteEjb.getDocumentByIDListAndType(listOfTrialIDs,
             DocumentTypeCode.PROTOCOL_DOCUMENT);

        //I get a valid index having protocols
        assertTrue(map.size() > 0);
        assertEquals(DocumentTypeCode.PROTOCOL_DOCUMENT.getCode(),
             map.get(1L).getTypeCode().getCode());
        assertEquals("1", IiConverter.convertToLong(map.get(1L).getStudyProtocolIdentifier()).toString());

        //And when I supply empty protocol-ids
        Map<Long, DocumentDTO> map2 = remoteEjb.getDocumentByIDListAndType( new ArrayList<Long>(),
                DocumentTypeCode.PROTOCOL_DOCUMENT);

        //I must get empty index map
        assertTrue(map2.isEmpty());


        //And when I supply null protocol-ids
        listOfTrialIDs.clear();
        Map<Long, DocumentDTO> map3 = remoteEjb.getDocumentByIDListAndType(null,
                DocumentTypeCode.PROTOCOL_DOCUMENT);

        //I must get empty index map
        assertTrue(map3.isEmpty());

    }
    
    @Test
    public void testUpdateForReview() throws Exception {
        DocumentDTO docDTO = new DocumentDTO();
        docDTO.setStudyProtocolIdentifier(pid);
        docDTO.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.AFTER_RESULTS));
        docDTO.setFileName(StConverter.convertToSt("Other Document.doc"));
        docDTO.setText(EdConverter.convertToEd("Other Document".getBytes()));

        docDTO = remoteEjb.create(docDTO);
        docDTO.setCcctUserReviewDateTime(TsConverter.convertToTs(new Date()));
        remoteEjb.updateForReview(docDTO);
        
        docDTO = remoteEjb.get(docDTO.getIdentifier());
        assertNotNull(docDTO);
        assertNotNull(docDTO.getCtroUserReviewDateTime());
        
        
    }

}

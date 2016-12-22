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
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.OversightCommittee;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StratumGroupServiceBean;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolBeanLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceBean;
import gov.nih.nci.pa.service.StudyProtocolServiceBeanTest;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceBean;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceBean;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.AbstractMockitoTest;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.TestSchema;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.lowagie.text.DocumentException;

/**
 *
 * @author NAmiruddin, kkanchinadam
 *
 */
public class TSRReportGeneratorServiceTest extends AbstractHibernateTestCase {
    private final TSRReportGeneratorServiceBean bean = new TSRReportGeneratorServiceBean();
    private final StudyProtocolServiceLocal remoteEjb = new StudyProtocolBeanLocal();
    private AbstractMockitoTest mockitoTest;

    @Before
    public void setup() throws Exception {
    	mockitoTest = new AbstractMockitoTest();
        mockitoTest.setUp();

        when(PaRegistry.getStudyProtocolService()).thenReturn(new StudyProtocolServiceBean());

        bean.setArmService(mockitoTest.getArmSvc());
        bean.setOcsr(mockitoTest.getOrgSvc());
        bean.setPlannedActivityService(mockitoTest.getPlannedActSvc());
        bean.setStudyDiseaseService(mockitoTest.getStudyDiseaseSvc());
        bean.setStudyIndldeService(mockitoTest.getStudyIndIdeSvc());
        bean.setStudyOutcomeMeasureService(mockitoTest.getStudyOutcomeMeasureSvc());
        bean.setStudyOverallStatusService(mockitoTest.getStudyOverallStatusSvc());
        bean.setStudySiteContactService(mockitoTest.getStudySiteContactSvc());
        bean.setStudySiteAccrualStatusService(mockitoTest.getStudySiteAccrualStatusSvc());
        bean.setRegulatoryInformationService(mockitoTest.getRegulInfoSvc());
        bean.setDiseaseService(mockitoTest.getDiseaseSvc());
        bean.setInterventionAlternateNameService(mockitoTest.getInterventionAltNameSvc());
        bean.setInterventionService(mockitoTest.getInterventionSvc());
        StudyResourcingServiceLocal studyResourcingSvc = mockitoTest.getStudyResourcingSvc();        
        List<StudyResourcingDTO> studyResourcingDtoList = mockitoTest.getStudyResourcingDtoList();
        studyResourcingDtoList.get(0).setOrganizationIdentifier(IiConverter.convertToPaOrganizationIi(1l));
		when(studyResourcingSvc.getStudyResourcingByStudyProtocol(any(Ii.class))).thenReturn(studyResourcingDtoList);
        when(studyResourcingSvc.getSummary4ReportedResourcing(any(Ii.class))).thenReturn(studyResourcingDtoList);
		bean.setStudyResourcingService(studyResourcingSvc);
        bean.setPlannedMarkerService(mockitoTest.getPlannedMarkerSvc());
        bean.setStudyRegulatoryAuthorityService(new StudyRegulatoryAuthorityServiceBean());
        bean.setStudySiteService(new StudySiteServiceBean());
        bean.setPaOrganizationService(PaRegistry.getPAOrganizationService());
        bean.setStratumGroupService(new StratumGroupServiceBean());        
        StudyContactServiceLocal studyContactSvc = mockitoTest.getStudyContactSvc();
        when(studyContactSvc.getByStudyProtocol(any(Ii.class), any(StudyContactDTO.class))).thenReturn(mockitoTest.getScDtoList());
        when(studyContactSvc.getResponsiblePartyContact(any(Ii.class))).thenReturn(mockitoTest.getScDtoList().get(0));
		bean.setStudyContactService(studyContactSvc);

        TestSchema.primeData();
        AnatomicSite as = new AnatomicSite();
        as.setCode("Lung");
        as.setCodingSystem("Summary 4 Anatomic Sites");
        TestSchema.addUpdObject(as);
        Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocol sp = (StudyProtocol) session.get(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        sp.setReviewBoardApprovalRequiredIndicator(true);
        session.save(sp);
        session.flush();
        session.clear();
        LookUpTableServiceRemote lookupSvc = mock(LookUpTableServiceRemote.class);
        when(lookupSvc.getLookupEntityByCode(any(Class.class), any(String.class))).thenReturn(as);
        when(PaRegistry.getLookUpTableService()).thenReturn(lookupSvc);
        
        CSMUserService.setInstance(new MockCSMUserService());
        UsernameHolder.setUserCaseSensitive("user1@mail.nih.gov");
    }

    @Test
    public void generateTsrReports() throws Exception {
        Ii ii = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
        ByteArrayOutputStream x = bean.generatePdfTsrReport(ii);
        assertNotNull(x);
        assertTrue(x.size() > 0);
        writeToFile(x, "./tsr_report.pdf");

        x = bean.generateRtfTsrReport(ii);
        assertNotNull(x);
        assertTrue(x.size() > 0);
        writeToFile(x, "./tsr_report.rtf");
        
        Session session = PaHibernateUtil.getCurrentSession();
        StudySite ss = (StudySite) session.get(StudySite.class, TestSchema.studySiteIds.get(0));
        ss.setReviewBoardApprovalStatusCode(ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED);
        OversightCommittee osc = new OversightCommittee();
        osc.setId(1L);
        ss.setOversightCommittee(osc);
        session.save(ss);
        
        InterventionalStudyProtocol isp = (InterventionalStudyProtocol) session.get(InterventionalStudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        isp.setBlindingRoleCodeCaregiver(BlindingRoleCode.CAREGIVER);
        isp.setBlindingRoleCodeInvestigator(BlindingRoleCode.INVESTIGATOR);
        isp.setBlindingRoleCodeOutcome(BlindingRoleCode.OUTCOMES_ASSESSOR);
        isp.setBlindingRoleCodeSubject(BlindingRoleCode.SUBJECT);
        isp.setProgramCodeText("programCodeText");
        session.save(isp);
        
        StudySite sPart = new StudySite();
        sPart.setFunctionalCode(StudySiteFunctionalCode.FUNDING_SOURCE);
        ResearchOrganization rOrg = new ResearchOrganization();
        rOrg.setId(TestSchema.researchOrganizationIds.get(0));
        sPart.setResearchOrganization(rOrg);
        sPart.setLocalStudyProtocolIdentifier("Local SP ID 01");
        sPart.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        sPart.setStatusDateRangeLow(ISOUtil.dateStringToTimestamp("12/18/2013"));
        sPart.setAccrualDateRangeLow(ISOUtil
                .dateStringToTimestamp("12/18/2013"));
        sPart.setStudyProtocol(isp);
        session.save(sPart);
        session.flush();
        StudyContactServiceLocal studyContactSvc = mockitoTest.getStudyContactSvc();
        when(studyContactSvc.getByStudyProtocol(any(Ii.class), any(StudyContactDTO.class))).thenReturn(mockitoTest.getScDtoList());
        when(studyContactSvc.getResponsiblePartyContact(any(Ii.class))).thenReturn(null);
		bean.setStudyContactService(studyContactSvc);
        x = bean.generateHtmlTsrReport(ii);
        assertNotNull(x);
        assertTrue(x.size() > 0);
        writeToFile(x, "./tsr_report.html");
        
        try {
        	bean.generatePdfTsrReport(null);
        	fail();
        } catch (PAException e) {
	        // expected
		}
        NonInterventionalStudyProtocolDTO ispDTO = StudyProtocolServiceBeanTest.createNonInterventionalStudyProtocolDTOObj();
        ispDTO.setReviewBoardApprovalRequiredIndicator(BlConverter.convertToBl(true));
        ii = remoteEjb.createNonInterventionalStudyProtocol(ispDTO);
        assertNotNull(ii.getExtension());
        x = bean.generatePdfTsrReport(ii);
        assertNotNull(x);
        assertTrue(x.size() > 0);
    }

    private void writeToFile(ByteArrayOutputStream os, String fileName) throws DocumentException, IOException {
        File file = new File(fileName);
        OutputStream fos = new FileOutputStream(file);
        os.writeTo(fos);
        fos.close();
        file.deleteOnExit();
    }
}

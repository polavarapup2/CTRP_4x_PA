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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.enums.OutcomeMeasureTypeCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CTGovXmlGeneratorServiceTest extends AbstractXmlGeneratorTest {

    private final CTGovXmlGeneratorServiceBeanLocal bean = new CTGovXmlGeneratorServiceBeanLocal();

    @Override
    public CTGovXmlGeneratorServiceBeanLocal getBean() {
        return bean;
    }

    private void assertCreatedLeadSponsorName(String orgName) throws ParserConfigurationException, PAException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element result = getBean().createLeadSponsor(spId, doc);
        assertEquals(orgName, result.getElementsByTagName("agency").item(0).getTextContent());
    }

    @Test
    public void testCreateLeadSponsor() throws PAException, ParserConfigurationException {
        assertCreatedLeadSponsorName("some name");
    }

    @Test
    public void testCreateLeadSponsorDCP() throws PAException, ParserConfigurationException {
        org.setName(PAConstants.DCP_ORG_NAME);
        assertCreatedLeadSponsorName(PAConstants.DCP_ORG_NAME);
    }

    @Test
    public void testCreateLeadSponsorCTEP() throws PAException, ParserConfigurationException {
        org.setName(PAConstants.CTEP_ORG_NAME);
        assertCreatedLeadSponsorName(PAConstants.CTEP_ORG_NAME);
    }

    @Test(expected=PAException.class)
    public void nullParameter() throws Exception {
        getBean().generateCTGovXml(null);
    }

    @Test
    public void testHappyPath() throws PAException {
       String st = getBean().generateCTGovXml(spId);
       assertTrue(st.contains("<clinical_study>"));
       assertTrue(st.contains("<is_section_801>"));
       assertTrue(st.contains("<id_type>Registry Identifier</id_type>"));
       assertTrue(st.contains("<primary_compl_date>1969-12</primary_compl_date>"));
       assertTrue(st.contains("<primary_compl_date_type>Anticipated</primary_compl_date_type>"));
       assertTrue(st.contains("<last_follow_up_date>1969-12</last_follow_up_date>"));
       assertTrue(st.contains("<last_follow_up_date_type>Anticipated</last_follow_up_date_type>"));
       
       st = getBean().generateCTGovXml(spId,CTGovXmlGeneratorOptions.USE_SUBMITTERS_PRS);
       assertTrue(st.contains("<clinical_study>"));
       assertTrue(st.contains("<is_section_801>"));
       assertTrue(st.contains("<id_type>Registry Identifier</id_type>"));
       
    }

    @Test
    public void testCtGovFalse() throws PAException {
        spDto.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(false));
        assertFalse(getBean().generateCTGovXml(spId).contains("<is_section_801>"));
    }

    @Test
    public void test801False() throws PAException {
        spDto.setSection801Indicator(BlConverter.convertToBl(false));
        assertTrue(getBean().generateCTGovXml(spId).contains("<is_section_801>No</is_section_801>"));
    }

    @Test
    public void testExpAccIndNull() throws PAException {
        spDto.setExpandedAccessIndicator(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<expanded_access_status>"));
    }

    @Test
    public void testExpAccIndFalse() throws PAException {
        spDto.setExpandedAccessIndicator(BlConverter.convertToBl(false));
        assertTrue(getBean().generateCTGovXml(spId).contains("<expanded_access_status>No longer available</expanded_access_status>"));
    }

    @Test
    public void testSosByCurrentIsNull() throws PAException {
        when(studyOverallStatusSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<overall_status>"));
    }

    @Test
    public void testSosByCurrent() throws PAException {
        StudyOverallStatusDTO recruitmentStatus = new StudyOverallStatusDTO();
        recruitmentStatus.setStatusCode(CdConverter.convertToCd(StudyStatusCode.ACTIVE));
        when(studyOverallStatusSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(recruitmentStatus);
        
        recruitmentStatus.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByRecruitmentStatus(RecruitmentStatusCode.IN_REVIEW)));        
        String xml = getBean().generateCTGovXml(spId);
        assertTrue(xml.contains("<overall_status>Not yet recruiting</overall_status>"));

        recruitmentStatus.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByRecruitmentStatus(RecruitmentStatusCode.APPROVED)));
        assertTrue(getBean().generateCTGovXml(spId).contains("<overall_status>Not yet recruiting</overall_status>"));

        recruitmentStatus.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByRecruitmentStatus(RecruitmentStatusCode.WITHDRAWN)));
        assertTrue(getBean().generateCTGovXml(spId).contains("<overall_status>Withdrawn</overall_status>"));

        recruitmentStatus.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByRecruitmentStatus(RecruitmentStatusCode.ACTIVE)));
        assertTrue(getBean().generateCTGovXml(spId).contains("<overall_status>Recruiting</overall_status>"));

        recruitmentStatus.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByRecruitmentStatus(RecruitmentStatusCode.ENROLLING_BY_INVITATION)));
        assertTrue(getBean().generateCTGovXml(spId).contains("<overall_status>Enrolling by Invitation</overall_status>"));

        recruitmentStatus.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByRecruitmentStatus(RecruitmentStatusCode.CLOSED_TO_ACCRUAL)));
        assertTrue(getBean().generateCTGovXml(spId).contains("<overall_status>Active, not recruiting</overall_status>"));

        recruitmentStatus.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByRecruitmentStatus(RecruitmentStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION)));
        assertTrue(getBean().generateCTGovXml(spId).contains("<overall_status>Active, not recruiting</overall_status>"));

        recruitmentStatus.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByRecruitmentStatus(RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL)));
        assertTrue(getBean().generateCTGovXml(spId).contains("<overall_status>Suspended</overall_status>"));

        recruitmentStatus.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByRecruitmentStatus(RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION)));
        assertTrue(getBean().generateCTGovXml(spId).contains("<overall_status>Suspended</overall_status>"));

        recruitmentStatus.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByRecruitmentStatus(RecruitmentStatusCode.COMPLETED)));
        assertTrue(getBean().generateCTGovXml(spId).contains("<overall_status>Completed</overall_status>"));

        recruitmentStatus.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByRecruitmentStatus(RecruitmentStatusCode.ADMINISTRATIVELY_COMPLETE)));
        assertTrue(getBean().generateCTGovXml(spId).contains("<overall_status>Terminated</overall_status>"));
    }

    @Test
    public void testCtGovNull() throws PAException {
        studyDiseaseDto.setCtGovXmlIndicator(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSDCtGovNull() throws PAException {
        studyDiseaseDtoList.remove(0);
        studyDiseaseDto.setCtGovXmlIndicator(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testOrgContIiNull() throws PAException {
        studyContactDto.setOrganizationalContactIi(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("some title"));
    }

    @Test
    public void testNoGoodTel() throws PAException, URISyntaxException {
        DSet<Tel> telAd = new DSet<Tel>();
        Set<Tel> telSet = new HashSet<Tel>();
        TelUrl url = new TelUrl();
        url.setValue(new URI("http://abc.com"));
        telSet.add(url);
        telAd.setItem(telSet);
        studyContactDto.setTelecomAddresses(telAd);
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testTelNull() throws PAException {
        studyContactDto.setTelecomAddresses(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<email>X</email>\n</overall_contact>"));
    }

    @Test
    public void testCrsNull() throws PAException {
        studyContactDto.setClinicalResearchStaffIi(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<last_name>some title"));
    }

    @Test
    public void testCurrentSraNull() throws PAException {
        when(studyRegAuthSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<regulatory_authority>"));
    }

    @Test
    public void testRIgetCountryNull() throws PAException {
        when(regulInfoSvc.getRegulatoryAuthorityCountry(anyLong())).thenReturn(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<regulatory_authority>"));
    }

    @Test
    public void testRiGetNull() throws PAException {
        when(regulInfoSvc.get(anyLong())).thenReturn(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<regulatory_authority>"));
    }

    @Test
    public void testRbApNull() throws PAException {
        spDto.setReviewBoardApprovalRequiredIndicator(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<approval_status>Not required</"));
    }

    @Test
    public void testRbAppFalse() throws PAException {
        spDto.setReviewBoardApprovalRequiredIndicator(BlConverter.convertToBl(false));
        assertTrue(getBean().generateCTGovXml(spId).contains("<approval_status>Not required</"));
    }

    @Test
    public void testGetOrgThrowNpe() throws PAException, NullifiedEntityException {
        when(poOrgSvc.getOrganization(any(Ii.class))).thenThrow(new NullifiedEntityException(spId));
        String ctgov = getBean().generateCTGovXml(spId);
        assertTrue(ctgov.contains("<error_description>"));
        assertTrue(ctgov.contains("<study_identifier>"));
        assertTrue(ctgov.contains("<error_messages>"));
    }

    @Test
    public void testGetPlActECBySpReturnNull() throws PAException {
        when(plannedActSvc.getPlannedEligibilityCriterionByStudyProtocol(any(Ii.class))).thenReturn(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testSIndGetNull() throws PAException {
        when(studyIndIdeSvc.getByStudyProtocol(any(Ii.class))).thenReturn(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<is_ind_study>No</"));
    }

    @Test
    public void testCritNameNull() throws PAException {
        plannedECDto.setCriterionName(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testInclIndTrue() throws PAException {
        plannedECDto.setInclusionIndicator(BlConverter.convertToBl(true));
        assertTrue(getBean().generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testCritAndInclIndNull() throws PAException {
        plannedECDto.setCriterionName(null);
        plannedECDto.setInclusionIndicator(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testCritNullInclIndFalse() throws PAException {
        plannedECDto.setCriterionName(null);
        plannedECDto.setInclusionIndicator(BlConverter.convertToBl(false));
        assertTrue(getBean().generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testCritNullTextNull() throws PAException {
        plannedECDto.setCriterionName(null);
        plannedECDto.setTextDescription(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testCritNullTextNullOpNull() throws PAException {
        plannedECDto.setCriterionName(null);
        plannedECDto.setTextDescription(null);
        plannedECDto.setOperator(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testCritNullTextNullInclIndFalse() throws PAException {
        plannedECDto.setCriterionName(null);
        plannedECDto.setTextDescription(null);
        plannedECDto.setInclusionIndicator(BlConverter.convertToBl(false));
        assertTrue(getBean().generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testCritNullTextNullInclIndNull() throws PAException {
        plannedECDto.setCriterionName(null);
        plannedECDto.setTextDescription(null);
        plannedECDto.setInclusionIndicator(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testPlLowUnitNull() throws PAException {
        plannedECDto.getValue().getLow().setUnit(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testArmBySpNull() throws PAException {
        when(armSvc.getByStudyProtocol(any(Ii.class))).thenReturn(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<arm_group>"));
    }

    @Test
    public void testPlSubCatNull() throws PAException {
        plannedActDto.setSubcategoryCode(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<intervention_type>"));
    }
    
    @Test
    public void testSecondaryIdOrder() throws PAException {
    	 String xml  = getBean().generateCTGovXml(spId);
    	 String newXML = createUnformattedXML(xml);
    	 assertTrue(newXML.contains
        ("<secondary_id><id>AAAAId</id></secondary_id>"
        +"<secondary_id><id>OtherId</id></secondary_id>"));
    }
    
    @Test
    public void testArmsGroupOrder() throws PAException {
    	 String xml  = getBean().generateCTGovXml(spId);
    	 String newXML = createUnformattedXML(xml);
    	         assertTrue(newXML.contains
        		("<arm_group><arm_group_label>Aname</arm_group_label>"
        	     +"<arm_type>Experimental</arm_type><arm_group_description>"
        		 +"<textblock>some description</textblock></arm_group_description>"
        	     +"</arm_group><arm_group><arm_group_label>Bname</arm_group_label>"));
    }
    
    @Test
    public void testOutComeMeasureOrder() throws PAException {
    	 String xml  = getBean().generateCTGovXml(spId);
    	 String newXML = createUnformattedXML(xml);
    	         assertTrue(newXML.contains
        		("<primary_outcome><outcome_measure>A name</outcome_measure>"
        	     +"<outcome_safety_issue>Yes</outcome_safety_issue><outcome_time_frame>some time</outcome_time_frame>"
        		 +"</primary_outcome><primary_outcome><outcome_measure>some name</outcome_measure>"));
    	         
    	         assertTrue(newXML.contains
    	         		("<secondary_outcome><outcome_measure>A name</outcome_measure>"
    	         		+"<outcome_safety_issue>Yes</outcome_safety_issue><outcome_time_frame>some time</outcome_time_frame>"
    	         		+"</secondary_outcome><secondary_outcome><outcome_measure>some name</outcome_measure>"));
    	         
    	         assertTrue(newXML.contains
     	         		("<other_outcome><outcome_measure>A name</outcome_measure>"
     	         		+"<outcome_safety_issue>No</outcome_safety_issue><outcome_time_frame>some time</outcome_time_frame>"
     	         	    +"</other_outcome><other_outcome><outcome_measure>some name</outcome_measure>"));
    }
    
    @Test
    public void testLongInterventionName() throws PAException {
    	 String xml  = getBean().generateCTGovXml(spId);
    	 String newXML = createUnformattedXML(xml);
    	         assertTrue(newXML.contains
        		("<intervention_name>This is to test if name is more than 160 characters hence adding very "
        		+"long name to check. Also this string should contains very long name full 200 length string "
        		+ "to check we are setting this string 123</intervention_name>"));
    }
    
    @Test
    public void testInterventionOtherNamesOrder() throws PAException {
    	 String xml  = getBean().generateCTGovXml(spId);
    	 String newXML = createUnformattedXML(xml);
    	         assertTrue(newXML.contains
        		("<intervention_other_name>A name starting with A</intervention_other_name><intervention_other_name>"
        		 +"duplicate name</intervention_other_name><intervention_other_name>Z name starting with Z</intervention_other_name>"));
    }
    
    @Test
    public void testPhoneWithExt() throws PAException {
         String xml  = getBean().generateCTGovXml(spId);
         String newXML = createUnformattedXML(xml);
         System.out.println(newXML);
                 assertTrue(newXML.contains
                ("<location><facility><name>some name</name><address/></facility>"
                 + "<status>Not yet recruiting</status>"
                 +"<contact><first_name>first name</first_name><last_name>last Name</last_name><phone>111-222-3333</phone>"
                 +"<phone_ext>444</phone_ext><email>X</email></contact>"));
    }

    @Test
    public void testAPlCatNull() throws PAException {
        plannedActDto.getCategoryCode().setCode(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<intervention_type>"));
    }

    @Test
    public void testInterTypeNull() throws PAException {
        interventionAltNameDto.getNameTypeCode().setValue(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSpTypeObs() throws PAException {
        spDto.setStudyProtocolType(StConverter.convertToSt("NonInterventionalStudyProtocol"));
        assertTrue(getBean().generateCTGovXml(spId).contains("<study_type>Observational</"));
    }

    @Test
    public void testSpTypeOther() throws PAException {
        spDto.setStudyProtocolType(StConverter.convertToSt("some other type"));
        assertTrue(getBean().generateCTGovXml(spId).contains("<study_type>Interventional</"));
    }

    @Test
    public void testSomNull() throws PAException {
       when(studyOutcomeMeasureSvc.getByStudyProtocol(any(Ii.class))).thenReturn(null);
       assertFalse(getBean().generateCTGovXml(spId).contains("<primary_outcome>"));
    }

    @Test
    public void testOrgEmpty() throws PAException {
        orgList = new ArrayList<Organization>();
        assertTrue(getBean().generateCTGovXml(spId).contains("<collaborator>"));
    }

    @Test
    public void testSsConRolePrim() throws PAException {
        studySiteContactDto.setRoleCode(CdConverter.convertStringToCd(StudySiteContactRoleCode.PRIMARY_CONTACT.getCode()));
        assertTrue(getBean().generateCTGovXml(spId).contains("<contact>"));
    }

    @Test
    public void testScListEmpty() throws PAException {
        when(studyContactSvc.getByStudyProtocol(any(Ii.class), any(StudyContactDTO.class))).thenReturn(new ArrayList<StudyContactDTO>());
        assertFalse(getBean().generateCTGovXml(spId).contains("<overall_contact>"));
    }

    @Test
    public void testOrgBySSNull() throws PAException {
        when(orgSvc.getOrganizationByStudySite(anyLong(), any(StudySiteFunctionalCode.class))).thenReturn(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<collaborator>"));
    }

    @Test
    public void testSpNull() throws PAException {
        when(spSvc.getStudyProtocol(any(Ii.class))).thenReturn(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<study_identifier>Unknown"));
    }

    @Test
    public void testOrgAdNull() throws PAException {
        orgDto.setPostalAddress(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("</full_address>\n</irb_info>"));
    }

    @Test
    public void testRecVerNull() throws PAException {
        spDto.setRecordVerificationDate(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSrsNull() throws PAException {
        when(studyRecruitmentStatusSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSosStatApp() throws PAException {
        studyOverallStatusDto.setStatusCode(CdConverter.convertStringToCd(StudyStatusCode.APPROVED.getCode()));
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSosStatTemp() throws PAException {
        studyOverallStatusDto.setStatusCode(CdConverter.convertStringToCd(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getCode()));
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSosStatAccInt() throws PAException {
        studyOverallStatusDto.setStatusCode(CdConverter.convertStringToCd(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION.getCode()));
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSdNull() throws PAException {
       when(studyDiseaseSvc.getByStudyProtocol(any(Ii.class))).thenReturn(null);
       assertFalse(getBean().generateCTGovXml(spId).contains("<condition>some disease</condition>"));
    }

    @Test
    public void testScCrsNull() throws PAException {
        studyContactDto.setClinicalResearchStaffIi(null);
        studyContactDto.setOrganizationalContactIi(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<overall_contact>\n<first_name>"));
    }

    @Test
    public void testScAdNull() throws PAException {
        studyContactDto.setClinicalResearchStaffIi(null);
        studyContactDto.setOrganizationalContactIi(null);
        studyContactDto.setTelecomAddresses(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<overall_contact>"));
    }

    @Test
    public void testScRolCent() throws PAException {
        studyContactDto.setRoleCode(CdConverter.convertStringToCd(StudyContactRoleCode.CENTRAL_CONTACT.getCode()));
        assertFalse(getBean().generateCTGovXml(spId).contains("<overall_official>"));
    }

    @Test
    public void testRBStatSub() throws PAException {
        studySiteDto.setReviewBoardApprovalStatusCode(CdConverter.convertStringToCd(ReviewBoardApprovalStatusCode.SUBMISSION_NOT_REQUIRED.getCode()));
        assertTrue(getBean().generateCTGovXml(spId).contains("<irb_info/>"));
    }

    @Test
    public void testRegPrsEmpty() throws PAException {
        regUser.setPrsOrgName("");
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSIndExpTrue() throws PAException {
        studySiteIndIdeDto.setExpandedAccessIndicator(BlConverter.convertToBl(true));
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
        PAServiceUtils paServiceUtil = mock (PAServiceUtils.class);
        getBean().setPaServiceUtil(paServiceUtil);
        when(paServiceUtil.containsNonExemptInds(any(List.class))).thenReturn(Boolean.TRUE);
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
        studySiteIndIdeDto.setExemptIndicator(BlConverter.convertToBl(false));
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
        when(studyIndIdeSvc.getByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<StudyIndldeDTO>());
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSsListEmpty() throws PAException {
        when(studySiteSvc.getByStudyProtocol(any(Ii.class), any(StudySiteDTO.class))).thenReturn(new ArrayList<StudySiteDTO>());
        assertFalse(getBean().generateCTGovXml(spId).contains("<organization>\n</resp_party>"));
    }

    @Test
    public void testSsRbSubDen() throws PAException {
        studySiteDto.setReviewBoardApprovalStatusCode(CdConverter.convertStringToCd(ReviewBoardApprovalStatusCode.SUBMITTED_DENIED.getCode()));
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSsRbSubEx() throws PAException {
        studySiteDto.setReviewBoardApprovalStatusCode(CdConverter.convertStringToCd(ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode()));
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSsRbSubPen() throws PAException {
        studySiteDto.setReviewBoardApprovalStatusCode(CdConverter.convertStringToCd(ReviewBoardApprovalStatusCode.SUBMITTED_PENDING.getCode()));
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testOrgTelEmpty() throws PAException {
        orgDto.setTelecomAddress(new DSet<Tel>());
        String results = getBean().generateCTGovXml(spId);
        assertTrue(results.contains("<irb_info>" + NEWLINE));
        assertTrue(results.contains("<approval_status>Approved</approval_status>" + NEWLINE));
        assertTrue(results.contains("<name>some name</name>" + NEWLINE));
        assertTrue(results.contains("<full_address>street, city, MD, 20000 USA</full_address>" + NEWLINE));
        assertTrue(results.contains("</irb_info>"));
    }

    @Test
    public void testSindGrantCodeNull() throws PAException {
        studySiteIndIdeDto.setGrantorCode(null);
        studySiteIndIdeDto.setIndldeNumber(null);
        studySiteIndIdeDto.setExpandedAccessIndicator(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<has_expanded_access>"));
    }

    @Test
    public void testPlEcDispOrdNull() throws PAException {
        plannedECDto.setDisplayOrder(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testPlEcCritAge() throws PAException {
        plannedECDto.setCriterionName(StConverter.convertToSt("AGE"));
        plannedECDto.getValue().setLow(null);
        plannedECDto.getValue().setHigh(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testPlEcCritGender() throws PAException {
        plannedECDto.setCriterionName(StConverter.convertToSt("GENDER"));
        plannedECDto.setEligibleGenderCode(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testArmDescNull() throws PAException {
        armDto.setDescriptionText(null);
        armDto.setName(null);
        armDto.setTypeCode(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<arm_group>"));
    }

    @Test
    public void testIspBlCodeNull() throws PAException {
        interventionalSPDto.setBlindedRoleCode(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<interventional_design>"));
    }

    @Test
    public void testIspBlCodeItemNull() throws PAException {
        interventionalSPDto.getBlindedRoleCode().setItem(null);
        assertTrue(getBean().generateCTGovXml(spId).contains("<interventional_design>"));
    }

    @Test
    public void testSomPrimTrue() throws PAException {
    	studyOutcomeMeasureDtoList = new ArrayList<StudyOutcomeMeasureDTO>();
        studyOutcomeMeasureDto = new StudyOutcomeMeasureDTO();
        studyOutcomeMeasureDto.setName(new St());
        studyOutcomeMeasureDto.setTimeFrame(new St());
        studyOutcomeMeasureDto.setTypeCode(new Cd());
        St desc = new St();
        desc.setValue("testSomPrimTrue -- Some description");
        studyOutcomeMeasureDto.setDescription(desc);
        studyOutcomeMeasureDtoList.add(studyOutcomeMeasureDto);
        when(studyOutcomeMeasureSvc.getByStudyProtocol(any(Ii.class))).thenReturn(studyOutcomeMeasureDtoList);
        // to test null checks
        String ctGovXml = getBean().generateCTGovXml(spId);
    	
        studyOutcomeMeasureDtoList = new ArrayList<StudyOutcomeMeasureDTO>();
        studyOutcomeMeasureDto = new StudyOutcomeMeasureDTO();
        studyOutcomeMeasureDto.setName(new St());
        studyOutcomeMeasureDto.setTimeFrame(new St());
        studyOutcomeMeasureDto.setTypeCode(CdConverter.convertStringToCd(OutcomeMeasureTypeCode.PRIMARY.getCode()));
        desc = new St();
        desc.setValue("testSomPrimTrue -- Some description");
        studyOutcomeMeasureDto.setDescription(desc);
        studyOutcomeMeasureDtoList.add(studyOutcomeMeasureDto);
        when(studyOutcomeMeasureSvc.getByStudyProtocol(any(Ii.class))).thenReturn(studyOutcomeMeasureDtoList);
        ctGovXml = getBean().generateCTGovXml(spId);
        assertTrue(ctGovXml.contains("<primary_outcome>"));
        assertFalse(ctGovXml.contains("<secondary_outcome>"));
        assertTrue(ctGovXml.contains("<outcome_description>"));
        
    }

    @Test
    public void testSomPrimFalse() throws PAException {
        studyOutcomeMeasureDtoList = new ArrayList<StudyOutcomeMeasureDTO>();
        studyOutcomeMeasureDto = new StudyOutcomeMeasureDTO();
        studyOutcomeMeasureDto.setName(new St());
        studyOutcomeMeasureDto.setTimeFrame(new St());
        studyOutcomeMeasureDto.setTypeCode(CdConverter.convertStringToCd(OutcomeMeasureTypeCode.SECONDARY.getCode()));
        St desc = new St();
        desc.setValue("testSomPrimFalse -- Some description");
        studyOutcomeMeasureDto.setDescription(desc);
        studyOutcomeMeasureDtoList.add(studyOutcomeMeasureDto);
        when(studyOutcomeMeasureSvc.getByStudyProtocol(any(Ii.class))).thenReturn(studyOutcomeMeasureDtoList);
        String ctGovXml = getBean().generateCTGovXml(spId);
        assertFalse(ctGovXml.contains("<primary_outcome>"));
        assertTrue(ctGovXml.contains("<secondary_outcome>"));
        assertTrue(ctGovXml.contains("<outcome_description>"));
    }

    @Test
    public void testSomOtherPrespecified() throws PAException {
        studyOutcomeMeasureDtoList = new ArrayList<StudyOutcomeMeasureDTO>();
        studyOutcomeMeasureDto = new StudyOutcomeMeasureDTO();
        studyOutcomeMeasureDto.setName(new St());
        studyOutcomeMeasureDto.setTimeFrame(new St());
        studyOutcomeMeasureDto.setTypeCode(CdConverter.convertStringToCd(OutcomeMeasureTypeCode.OTHER_PRE_SPECIFIED.getCode()));
        St desc = new St();
        desc.setValue("testSomPrimFalse -- Some description");
        studyOutcomeMeasureDto.setDescription(desc);
        studyOutcomeMeasureDtoList.add(studyOutcomeMeasureDto);
        when(studyOutcomeMeasureSvc.getByStudyProtocol(any(Ii.class))).thenReturn(studyOutcomeMeasureDtoList);
        String ctGovXml = getBean().generateCTGovXml(spId);
        assertFalse(ctGovXml.contains("<primary_outcome>"));
        assertFalse(ctGovXml.contains("<secondary_outcome>"));
        assertTrue(ctGovXml.contains("<other_outcome>"));
        assertTrue(ctGovXml.contains("<outcome_description>"));
    }

    @Test
    public void testOrgNameNull() throws PAException {
        org.setName(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<lead_sponsor>"));
    }

    @Test
    public void testMiddleNameNullCheck() throws PAException {
        person.setMiddleName(null);
        assertFalse(getBean().generateCTGovXml(spId).contains("<middle_name>"));
        person.setMiddleName("Test");
        assertTrue(getBean().generateCTGovXml(spId).contains("<middle_name>T.</middle_name>"));
    }

    @Test
    public void testOrgBySsEmpty() throws PAException {
        when(orgSvc.getOrganizationByStudySite(anyLong(), any(StudySiteFunctionalCode.class)))
           .thenReturn(new ArrayList<Organization>());
        assertFalse(getBean().generateCTGovXml(spId).contains("<collaborator>"));
    }

    @Test
    public void testLocations() throws PAException {
        StudySiteDTO studySiteDto = new StudySiteDTO();
        studySiteDto.setReviewBoardApprovalStatusCode(CdConverter
                .convertStringToCd(ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode()));
        studySiteDto.setHealthcareFacilityIi(IiConverter.convertToPoHealthCareFacilityIi("1"));
        studySiteDto.setResearchOrganizationIi(IiConverter.convertToPoResearchOrganizationIi("1"));
        studySiteDto.setStatusCode(CdConverter.convertToCd(StructuralRoleStatusCode.ACTIVE));
        studySiteDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        studySiteDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt("SITE_1"));
        when(studySiteSvc.getByStudyProtocol(any(Ii.class), any(StudySiteDTO.class))).thenReturn(Arrays.asList(studySiteDto));

        StudySiteAccrualStatusDTO accrualStatus = new StudySiteAccrualStatusDTO();
        accrualStatus.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.IN_REVIEW));

        when(studySiteAccrualStatusSvc.getCurrentStudySiteAccrualStatusByStudySite(any(Ii.class))).thenReturn(accrualStatus);

        assertTrue(getBean().generateCTGovXml(spId).contains("<status>Not yet recruiting</status>"));

        accrualStatus.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.APPROVED));
        assertTrue(getBean().generateCTGovXml(spId).contains("<status>Not yet recruiting</status>"));

        accrualStatus.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.WITHDRAWN));
        assertTrue(getBean().generateCTGovXml(spId).contains("<status>Withdrawn</status>"));

        accrualStatus.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.ACTIVE));
        assertTrue(getBean().generateCTGovXml(spId).contains("<status>Recruiting</status>"));

        accrualStatus.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.ENROLLING_BY_INVITATION));
        assertTrue(getBean().generateCTGovXml(spId).contains("<status>Enrolling by Invitation</status>"));

        accrualStatus.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.CLOSED_TO_ACCRUAL));
        assertTrue(getBean().generateCTGovXml(spId).contains("<status>Active, not recruiting</status>"));

        accrualStatus.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertTrue(getBean().generateCTGovXml(spId).contains("<status>Active, not recruiting</status>"));

        accrualStatus.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL));
        assertTrue(getBean().generateCTGovXml(spId).contains("<status>Suspended</status>"));

        accrualStatus.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION));
        assertTrue(getBean().generateCTGovXml(spId).contains("<status>Suspended</status>"));

        accrualStatus.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.COMPLETED));
        assertTrue(getBean().generateCTGovXml(spId).contains("<status>Completed</status>"));

        accrualStatus.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.ADMINISTRATIVELY_COMPLETE));
        assertTrue(getBean().generateCTGovXml(spId).contains("<status>Terminated</status>"));
    }

    @Test
    public void createDescriptionTextString() {
        String text = "text";

        String result = bean.createDescriptionTextString(text);

        String expected = "  - text\n";
        assertEquals(expected, result);

    }
    
    @Test
    public void applyPrsFormattingFixes() {
        assertNull(bean.applyPrsFormattingFixes(null));
        assertEquals("  * Level 1 \r\n  ** Level2",
                bean.applyPrsFormattingFixes("* Level 1 \r\n** Level2"));
        assertEquals("  * Level 1", bean.applyPrsFormattingFixes("- Level 1"));
        assertEquals("  * Level 1", bean.applyPrsFormattingFixes(" - Level 1"));
        assertEquals("  * abc", bean.applyPrsFormattingFixes(" * abc"));
        assertEquals("  * abc", bean.applyPrsFormattingFixes(" - abc"));
        assertEquals(
                "Test:\r  * Level 1\r  * Level2\r  ** Level3",
                bean.applyPrsFormattingFixes("Test:\r- Level 1\r* Level2\r ** Level3"));
    }  

    @Test
    public void createValueUnitOperatorString() {
        String criterionName = "criteria";
        BigDecimal value = new BigDecimal(3);
        String unit = "unit";
        String operator = "operator";

        String result = bean.createValueUnitOperatorString(criterionName, value, unit, operator);

        String expected = "     criteria 3 operator unit\n";
        assertEquals(expected, result);

    }
    public String createUnformattedXML(String xml) throws PAException {
    	 StringWriter sw;
    	 OutputFormat format = OutputFormat.createCompactFormat();
    	 try {
    	 org.dom4j.Document document = DocumentHelper.parseText(xml);
    	 sw = new StringWriter();
    	 XMLWriter writer = new XMLWriter(sw, format);
         writer.write(document);
    	 }
    	 catch(Exception e) {
    		 throw new PAException(e);
    	 }
    	 String newXML = sw.toString();	
    	 return newXML;
    }
}

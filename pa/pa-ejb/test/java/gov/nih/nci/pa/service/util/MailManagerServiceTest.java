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

package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.PAProperties;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyContact;

import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.domain.StudyRecordChange;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.dto.LastCreatedDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.AmendmentReasonCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceBean;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceBean;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author Vrushali
 * 
 */
public class MailManagerServiceTest extends AbstractHibernateTestCase {
	private static final String DATE_PATTERN = "MM/dd/yyyy";
    private static String email1 = "example1@example.com";
    private static String email2 = "example2@example.com";
    private static String smtpHost = "";
    private static String fromAddress = "fromAddress@example.com";

    private static final String NOTFICATION_SUBJECT_KEY = "trial.register.subject";
    private static final String NOTFICATION_SUBJECT_VALUE = "trial.register.subject - ${leadOrgTrialIdentifier},"
            + " ${nciTrialIdentifier}.";
    private static final String NOTFICATION_BODY_KEY = "trial.register.body";
    private static final String NOTFICATION_BODY_VALUE = "${CurrentDate} ${SubmitterName} ${nciTrialIdentifier},"
            + " ${leadOrgTrialIdentifier}, ${leadOrgName}, ${trialTitle}.${errors}";

    private static final String PROP_NOTFICATION_SUBJECT_KEY = "proprietarytrial.register.subject";
    private static final String PROP_NOTFICATION_SUBJECT_VALUE = "proprietarytrial.register.subject - "
            + "${leadOrgTrialIdentifier}, ${nciTrialIdentifier} ${subOrgTrialIdentifier}.";
    private static final String PROP_NOTFICATION_BODY_KEY = "proprietarytrial.register.body";
    private static final String PROP_NOTFICATION_BODY_VALUE = "${CurrentDate} ${SubmitterName} ${nciTrialIdentifier},"
            + " ${leadOrgTrialIdentifier}, ${leadOrgName}, ${leadOrgID}, ${trialTitle}, ${subOrgTrialIdentifier}, ${subOrg}. ${errors}  ";
    private static final String ERRORS_BODY_VALUE = "{0}";
    private static final String ERRORS_BODY_KEY = "trial.register.unidentifiableOwner.sub.email.body";

    private final LookUpTableServiceRemote lookUpTableService = mock(LookUpTableServiceRemote.class);
    private final ProtocolQueryServiceLocal protocolQueryService = mock(ProtocolQueryServiceLocal.class);
    private final RegistryUserServiceLocal registryUserService = mock(RegistryUserServiceLocal.class);
    private final StudySiteServiceLocal studySiteService = mock(StudySiteServiceLocal.class);
    private final PAServiceUtils paServiceUtils = mock(PAServiceUtils.class);
    private final DocumentWorkflowStatusServiceLocal docWrkStatService = mock(DocumentWorkflowStatusServiceLocal.class);
    private MailManagerBeanLocal sut;

    MailManagerBeanLocal bean = new MailManagerBeanLocal();
    ProtocolQueryServiceBean protocolQrySrv = new ProtocolQueryServiceBean();
    RegistryUserServiceLocal registryUserSrv = new RegistryUserServiceBean();
    CTGovXmlGeneratorServiceBeanLocal ctGovXmlSrv = new CTGovXmlGeneratorServiceBeanLocal();
    TSRReportGeneratorServiceRemote tsrReptSrv = new TSRReportGeneratorServiceBean();
    LookUpTableServiceRemote lookUpTableSrv = new LookUpTableServiceBean();
    DocumentWorkflowStatusServiceLocal docWrkStatSrv = new DocumentWorkflowStatusServiceBean();

    Ii nonProprietaryTrialIi;
    Ii proprietaryTrialIi;

    @Before
    public void setUp() throws Exception {
        ServiceLocator svcLocator = mock(ServiceLocator.class);
        when(svcLocator.getStudyProtocolService()).thenReturn(
                new StudyProtocolServiceBean());
        PaRegistry.getInstance().setServiceLocator(svcLocator);
        protocolQrySrv.setPaServiceUtils(paServiceUtils);

        CSMUserService.setInstance(new MockCSMUserService());

        bean.setCtGovXmlGeneratorService(ctGovXmlSrv);
        bean.setProtocolQueryService(protocolQrySrv);
        bean.setRegistryUserService(registryUserSrv);
        bean.setTsrReportGeneratorService(tsrReptSrv);
        bean.setLookUpTableService(lookUpTableSrv);
        bean.setDocWrkflStatusSrv(docWrkStatSrv);
        bean.setStudySiteService(studySiteService);
        bean.setPaServiceUtils(paServiceUtils);

        // setup owners for both prop/nonprop trials.
        User csmOwner1 = createUser("loginName1", "fname1", "lname1");
        TestSchema.addUpdObject(csmOwner1);
        RegistryUser owner1 = new RegistryUser();
        owner1.setLastName("fname1");
        owner1.setFirstName("lname1");
        owner1.setEmailAddress(email1);
        owner1.setCsmUser(csmOwner1);
        TestSchema.addUpdObject(owner1);

        User csmOwner2 = createUser("loginName2", "fname2", "lname2");
        TestSchema.addUpdObject(csmOwner2);
        RegistryUser owner2 = new RegistryUser();
        owner2.setLastName("fname2");
        owner2.setFirstName("lname2");
        owner2.setEmailAddress(email2);
        owner2.setCsmUser(csmOwner2);
        TestSchema.addUpdObject(owner2);

        Organization o = TestSchema.createOrganizationObj();
        TestSchema.addUpdObject(o);
        Person p = TestSchema.createPersonObj();
        p.setIdentifier("11");
        TestSchema.addUpdObject(p);
        HealthCareFacility hcf = TestSchema.createHealthCareFacilityObj(o);
        TestSchema.addUpdObject(hcf);

        HealthCareProvider hcp = TestSchema.createHealthCareProviderObj(p, o);
        TestSchema.addUpdObject(hcp);

        Country c = TestSchema.createCountryObj();
        TestSchema.addUpdObject(c);

        ClinicalResearchStaff crs = TestSchema.createClinicalResearchStaffObj(
                o, p);
        TestSchema.addUpdObject(crs);

        ResearchOrganization ro = new ResearchOrganization();
        ro.setOrganization(o);
        ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        ro.setIdentifier("abc");
        TestSchema.addUpdObject(ro);

        // Non Prop Trial
        StudyProtocol nonPropTrial = TestSchema.createStudyProtocolObj();
        nonPropTrial.getStudyOwners().add(owner1);
        nonPropTrial.getStudyOwners().add(owner2);
        TestSchema.addUpdObject(nonPropTrial);
        nonProprietaryTrialIi = IiConverter.convertToIi(nonPropTrial.getId());

        StudyContact sc = TestSchema.createStudyContactObj(nonPropTrial, c,
                hcp, crs);
        TestSchema.addUpdObject(sc);

        StudySite spc = TestSchema.createStudySiteObj(nonPropTrial, hcf);
        spc.setResearchOrganization(ro);
        TestSchema.addUpdObject(spc);
        nonPropTrial.getStudySites().add(spc);

        StudyRecruitmentStatus studyRecStatus = TestSchema
                .createStudyRecruitmentStatus(nonPropTrial);
        TestSchema.addUpdObject(studyRecStatus);

        DocumentWorkflowStatus docWrkNonProp = TestSchema
                .createDocumentWorkflowStatus(nonPropTrial);
        TestSchema.addUpdObject(docWrkNonProp);
        nonPropTrial.getDocumentWorkflowStatuses().add(docWrkNonProp);
        TestSchema.addUpdObject(nonPropTrial);

        // Prop Trial
        StudyProtocol propTrial = createProprietaryStudyProtocolObj();
        propTrial.getStudyOwners().add(owner1);
        propTrial.getStudyOwners().add(owner2);

        Set<Ii> otherIdentifiers = new HashSet<Ii>();
        otherIdentifiers.add(IiConverter
                .convertToAssignedIdentifierIi("NCI-2009-00002"));

        propTrial.setOtherIdentifiers(otherIdentifiers);
        propTrial.setSubmissionNumber(Integer.valueOf(1));
        propTrial.setAccrualDiseaseCodeSystem("SDC");
        // propTrial.setCtgovXmlRequiredIndicator(Boolean.FALSE);
        TestSchema.addUpdObject(propTrial);
        proprietaryTrialIi = IiConverter.convertToIi(propTrial.getId());

        sc = TestSchema.createStudyContactObj(propTrial, c, hcp, crs);
        TestSchema.addUpdObject(sc);

        spc = TestSchema.createStudySiteObj(propTrial, hcf);
        spc.setResearchOrganization(ro);
        TestSchema.addUpdObject(spc);
        propTrial.getStudySites().add(spc);

        studyRecStatus = TestSchema.createStudyRecruitmentStatus(propTrial);
        TestSchema.addUpdObject(studyRecStatus);

        DocumentWorkflowStatus docWrk = TestSchema
                .createDocumentWorkflowStatus(propTrial);
        TestSchema.addUpdObject(docWrk);
        propTrial.getDocumentWorkflowStatuses().add(docWrk);
        TestSchema.addUpdObject(propTrial);

        User csmUser = createUser("loginName", "firstName", "lastName");
        TestSchema.addUpdObject(csmUser);
        RegistryUser registryUser = new RegistryUser();
        registryUser.setFirstName("firstName");
        registryUser.setLastName("lastName");
        registryUser.setUserLastCreated(TestSchema.getUser());
        registryUser.setCsmUser(csmUser);
        TestSchema.addUpdObject(registryUser);

        // properties
        PAProperties prop = new PAProperties();
        prop.setName("smtp");
        prop.setValue(smtpHost);
        TestSchema.addUpdObject(prop);
        
        prop = new PAProperties();
        prop.setName("smtp.port");
        prop.setValue("21");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("fromaddress");
        prop.setValue(fromAddress);
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("tsr.amend.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${leadOrgTrialIdentifier},${leadOrgID}, ${trialTitle},${nciTrialIdentifier}, (${amendmentNumber}), ${amendmentDate}, (${fileName}), ${fileName2}.");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("tsr.proprietary.subject");
        prop.setValue("Proprietary Subject");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("tsr.proprietary.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${leadOrgTrialIdentifier}, ${trialTitle},${nciTrialIdentifier}, (${amendmentNumber}), ${amendmentDate}, (${fileName}), ${fileName2}.");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("xml.subject");
        prop.setValue("xml.subject, ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}.");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("xml.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier}, ${trialTitle}, (${leadOrgTrialIdentifier}), ${receiptDate} ${fileName}.");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("noxml.tsr.amend.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${leadOrgTrialIdentifier}, ${trialTitle},${nciTrialIdentifier}, (${amendmentNumber}), ${amendmentDate}, (${fileName}), ${fileName2}.");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("CDE_MARKER_REQUEST_BODY");
        prop.setValue("${markerName} ${foundInHugo} ${hugoCodeClause} ${markerTextClause}");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("CDE_MARKER_REQUEST_SUBJECT");
        prop.setValue("CDE Marker Request ${markerName} for ${trialIdentifier}");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("CDE_MARKER_REQUEST_HUGO_CLAUSE");
        prop.setValue("${hugoCode}");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("CDE_MARKER_REQUEST_MARKER_TEXT_CLAUSE");
        prop.setValue("${markerText}");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("CDE_REQUEST_TO_EMAIL");
        prop.setValue("to@example.com, to1@example.com, test@gmail.com");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("log.email.address");
        prop.setValue("logctrp@example.com");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("CDE_MARKER_REQUEST_FROM_EMAIL");
        prop.setValue("ncictro@example.com");
        TestSchema.addUpdObject(prop);
        
        prop = new PAProperties();
        prop.setName("abstraction.script.mailTo");
        prop.setValue("ncictro@example.com");
        TestSchema.addUpdObject(prop);
        
        prop = new PAProperties();
        prop.setName("smtp.auth.username");
        prop.setValue("");
        TestSchema.addUpdObject(prop);
        
        prop = new PAProperties();
        prop.setName("trial.identifiers.header");
        prop.setValue("<table border='0'> <tr> <td><b>NCI Trial ID:</b></td> <td>${spDTO.nciIdentifier}</td> </tr> " + 
        		"<tr> <td><b>Lead Organization Trial ID:</b></td> <td>${spDTO.localStudyProtocolIdentifier}</td> </tr> " +
        		"<tr> <td><b>Lead Organization:</b></td> <td>${spDTO.leadOrganizationName}</td> </tr> <tr> " +
        		"<td><b>CTRP-assigned Lead Organization ID:</b></td> <td>${spDTO.leadOrganizationPOId?c}</td> </tr> " +
        		"<#if spDTO.ctepId??> <tr> <td><b>CTEP ID:</b></td> <td>${spDTO.ctepId}</td> </tr> </#if> " +
                "<#if spDTO.dcpId??> <tr> <td><b>DCP ID:</b></td> <td>${spDTO.dcpId}</td> </tr> </#if> " +
                "<#if spDTO.nctNumber??><tr><td><b>ClinicalTrials.gov ID:</b></td><td>${spDTO.nctNumber}</td></tr></#if></table>");
        TestSchema.addUpdObject(prop);
    }

    private User createUser(String loginName, String firstName, String lastName) {
        User user = new User();
        user.setLoginName(loginName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUpdateDate(new Date());
        return user;
    }

    private MailManagerBeanLocal createMailManagerServiceBean() {
        MailManagerBeanLocal service = new MailManagerBeanLocal();
        setDependencies(service);
        return service;
    }

    private MailManagerBeanLocal createMailManagerServiceMock() {
        MailManagerBeanLocal service = mock(MailManagerBeanLocal.class);
        doCallRealMethod().when(service).setLookUpTableService(
                lookUpTableService);
        doCallRealMethod().when(service).setProtocolQueryService(
                protocolQueryService);
        doCallRealMethod().when(service).setRegistryUserService(
                registryUserService);
        doCallRealMethod().when(service).setStudySiteService(studySiteService);
        doCallRealMethod().when(service).setPaServiceUtils(paServiceUtils);
        doCallRealMethod().when(service).setDocWrkflStatusSrv(docWrkStatService);
        setDependencies(service);
        return service;
    }

    private void setDependencies(MailManagerBeanLocal service) {
        service.setLookUpTableService(lookUpTableService);
        service.setProtocolQueryService(protocolQueryService);
        service.setRegistryUserService(registryUserService);
        service.setStudySiteService(studySiteService);
        service.setPaServiceUtils(paServiceUtils);
        service.setDocWrkflStatusSrv(docWrkStatService);
    }

    @Test(expected = PAException.class)
    public void testSendTSREmail() throws PAException {
        bean.sendTSREmail(nonProprietaryTrialIi);
    }

    @Test
    public void testSendAdminAcceptanceEmail() throws PAException {
        testSendAdminAcceptanceOrRejectionEmail(true, "");
    }

    @Test
    public void testSendAdminRejectionEmail() throws PAException {
        testSendAdminAcceptanceOrRejectionEmail(false, "Reason for Rejection");
        testSendAdminAcceptanceOrRejectionEmail(false, null);
    }

    private void testSendAdminAcceptanceOrRejectionEmail(boolean accept,
            String reason) throws PAException {
        User csmUser = createUser("LoginName", "FirstName", "LastName");
        TestSchema.addUpdObject(csmUser);
        RegistryUser user = new RegistryUser();
        user.setLastName("LastName");
        user.setFirstName("FirstName");
        user.setEmailAddress(email1);
        user.setAffiliateOrg("Affiliated Organization");
        user.setCsmUser(csmUser);
        TestSchema.addUpdObject(user);

        PAProperties prop = new PAProperties();
        prop.setName("trial.admin.accept.subject"); // same property being used
                                                    // for both accept/reject
                                                    // emails.
        prop.setValue("Administration Status Request");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setValue("Date: ${CurrentDate} -- Name: ${SubmitterName} -- Organization: ${affliateOrgName}");
        if (accept) {
            prop.setName("trial.admin.accept.body");
        } else {
            prop.setName("trial.admin.reject.body");
        }
        TestSchema.addUpdObject(prop);

        if (accept) {
            bean.sendAdminAcceptanceEmail(user.getId());
        } else {
            bean.sendAdminRejectionEmail(user.getId(), reason);
        }
    }
    
    @Test
    public void testEmailHeaders() throws PAException {    	        
        
        StudyProtocolQueryDTO spDTO = protocolQrySrv.getTrialSummaryByStudyProtocolId(
                IiConverter.convertToLong(nonProprietaryTrialIi));
        
        // Asserting without CTEP / DCP ids set.
        assertEquals("<table border='0'> "
        		+ "<tr> <td><b>NCI Trial ID:</b></td> <td>NCI-2009-00001</td> </tr> "
        		+ "<tr> <td><b>Lead Organization Trial ID:</b></td> <td>Ecog1</td> </tr> "
        		+ "<tr> <td><b>Lead Organization:</b></td> <td>Mayo University</td> </tr> "
        		+ "<tr> <td><b>CTRP-assigned Lead Organization ID:</b></td> <td>1</td> </tr>   "
        		+ "</table>", 
        		bean.getStudyIdentifiers(spDTO));
        
        spDTO.setCtepId("CTEP");
        spDTO.setDcpId("DCP");
        spDTO.setLocalStudyProtocolIdentifier("LOCAL");
        spDTO.setNciIdentifier("NCI");
        spDTO.setLeadOrganizationName("LEADORG");
        spDTO.setNctNumber("12345");
        
        // Asserting with all the values
        assertEquals("<table border='0'> "
        		+ "<tr> <td><b>NCI Trial ID:</b></td> <td>NCI</td> </tr> "
        		+ "<tr> <td><b>Lead Organization Trial ID:</b></td> <td>LOCAL</td> </tr> "
        		+ "<tr> <td><b>Lead Organization:</b></td> <td>LEADORG</td> </tr> "
        		+ "<tr> <td><b>CTRP-assigned Lead Organization ID:</b></td> <td>1</td> </tr>  "
        		+ "<tr> <td><b>CTEP ID:</b></td> <td>CTEP</td> </tr>   "
        		+ "<tr> <td><b>DCP ID:</b></td> <td>DCP</td> </tr>  "
        		+ "<tr><td><b>ClinicalTrials.gov ID:</b></td><td>12345</td></tr>"
        		+ "</table>", 
        		bean.getStudyIdentifiers(spDTO));        
    }

    @Test
    public void testSendAcceptEmail() throws PAException {
    	PAProperties prop = new PAProperties();
        prop.setName("trial.accept.subject");
        prop.setValue("trial.accept.subject");
        TestSchema.addUpdObject(prop);
        
        prop = new PAProperties();
        prop.setName("trial.accept.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier}, ${leadOrgTrialIdentifier} ${leadOrgID}.");
        TestSchema.addUpdObject(prop);

        bean.sendAcceptEmail(nonProprietaryTrialIi);
        StudyProtocolQueryDTO spDTO = protocolQrySrv.getTrialSummaryByStudyProtocolId(
                IiConverter.convertToLong(nonProprietaryTrialIi));
        spDTO.setLeadOrganizationPOId(1L);
        spDTO.setAmendmentNumber("1");
        assertEquals(getFormatedCurrentDate() + " ${SubmitterName}NCI-2009-00001, Ecog1 1.", 
        		bean.commonMailBodyReplacements(spDTO, prop.getValue()));
    }

    @Test
    public void testSendAmendAcceptEmail() throws PAException {
        PAProperties prop = new PAProperties();
        prop.setName("trial.amend.accept.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier}, ${title}, (${amendmentNumber}), ${amendmentDate}.");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("trial.amend.accept.subject");
        prop.setValue("trial.amend.accept.subject-${amendmentNumber},${leadOrgTrialIdentifier}, ${nciTrialIdentifier}.");
        TestSchema.addUpdObject(prop);
        StudyProtocolQueryDTO spDTO = protocolQrySrv.getTrialSummaryByStudyProtocolId(
                IiConverter.convertToLong(nonProprietaryTrialIi));
        spDTO.setAmendmentNumber("1");
        bean.sendAmendAcceptEmail(nonProprietaryTrialIi);
        assertEquals("trial.amend.accept.subject-1,Ecog1, NCI-2009-00001.", bean.commonMailSubjectReplacements(spDTO, prop.getValue()));

        prop = new PAProperties();
        prop.setName("trial.amend.accept.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier}, ${title}, ${leadOrgID}");
        TestSchema.addUpdObject(prop);
        spDTO.setAmendmentNumber("1");
        spDTO.setLeadOrganizationPOId(1L);
        bean.sendAmendAcceptEmail(nonProprietaryTrialIi);
        assertEquals(getFormatedCurrentDate()+ " ${SubmitterName}NCI-2009-00001, ${title}, 1", bean.commonMailBodyReplacements(spDTO, prop.getValue()));
        
    }

    @Test
    public void testSendAmendNotificationEmail() throws PAException {
        PAProperties prop = new PAProperties();
        prop.setName("trial.amend.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier}, ${title}, (${amendmentNumber}), ${amendmentDate}.");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("trial.amend.subject");
        prop.setValue("trial.amend.subject -${amendmentNumber},${leadOrgTrialIdentifier}, ${nciTrialIdentifier}.");
        TestSchema.addUpdObject(prop);
        
        StudyProtocolQueryDTO spDTO = protocolQrySrv.getTrialSummaryByStudyProtocolId(
                IiConverter.convertToLong(nonProprietaryTrialIi));
        spDTO.setAmendmentNumber("1");
        bean.sendAmendNotificationMail(nonProprietaryTrialIi);
        assertEquals("trial.amend.subject -1,Ecog1, NCI-2009-00001.", bean.commonMailSubjectReplacements(spDTO, prop.getValue()));
        
        prop = new PAProperties();
        prop.setName("trial.amend.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier}, ${title}, ${leadOrgID}");
        TestSchema.addUpdObject(prop);
        spDTO.setAmendmentNumber("1");
        spDTO.setLeadOrganizationPOId(1L);
        bean.sendAmendNotificationMail(nonProprietaryTrialIi);
        assertEquals(getFormatedCurrentDate()+ " ${SubmitterName}NCI-2009-00001, ${title}, 1", bean.commonMailBodyReplacements(spDTO, prop.getValue()));
        
    }

    @Test
    public void testSendAmendRejectEmail() throws PAException {
        PAProperties prop = new PAProperties();
        prop.setName("trial.amend.reject.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier} ${leadOrgTrialIdentifier}, ${trialTitle}, (${amendmentNumber}), ${amendmentDate},${reasonForRejection}.");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("trial.amend.reject.subject");
        prop.setValue("Amendment # ${amendmentNumber}, ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}");
        TestSchema.addUpdObject(prop);

        StudyProtocolQueryDTO spDTO = protocolQrySrv.getTrialSummaryByStudyProtocolId(
                IiConverter.convertToLong(nonProprietaryTrialIi));
        spDTO.setAmendmentNumber("1");
        bean.sendAmendRejectEmail(spDTO, "rejectReason");
        assertEquals("Amendment # 1, NCI-2009-00001, Ecog1", bean.commonMailSubjectReplacements(spDTO, prop.getValue()));
        
        prop = new PAProperties();
        prop.setName("trial.amend.reject.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier}, ${title}, ${leadOrgID}");
        TestSchema.addUpdObject(prop);
        spDTO.setAmendmentNumber("1");
        spDTO.setLeadOrganizationPOId(1L);
        bean.sendAmendRejectEmail(spDTO, "rejectReason");
        assertEquals(getFormatedCurrentDate()+ " ${SubmitterName}NCI-2009-00001, ${title}, 1", bean.commonMailBodyReplacements(spDTO, prop.getValue()));
        
    }

    @Test
    public void testSendRejectionEmail() throws PAException {
        sut = createMailManagerServiceMock();
        IiConverter.convertToStudyProtocolIi(1L);
        doCallRealMethod().when(sut).sendRejectionEmail(any(Ii.class));

        doCallRealMethod().when(sut).getFormatedCurrentDate();

        doCallRealMethod().when(sut).commonMailBodyReplacements(
                any(StudyProtocolQueryDTO.class), any(String.class));
        when(lookUpTableService
                .getPropertyValue("rejection.subject")).thenReturn("subject");
        when(
                lookUpTableService
                        .getPropertyValue("rejection.body"))
                .thenReturn(
                        "BODY ${CurrentDate} ${SubmitterName} ${leadOrgTrialIdentifier},${leadOrgID}, ${trialTitle},${nciTrialIdentifier}, ${receiptDate}, ${reasoncode}.");


        StudyProtocolQueryDTO spDTO = new StudyProtocolQueryDTO();
        spDTO.setProprietaryTrial(false);
        spDTO.setLocalStudyProtocolIdentifier("localStudyProtocolIdentifier");
        spDTO.setNciIdentifier("nciIdentifier");
        spDTO.setLeadOrganizationName("NCI");
        spDTO.setLeadOrganizationPOId(1L);
        spDTO.setOfficialTitle("officialTitle");
        spDTO.setStudyProtocolId(IiConverter.convertToLong(nonProprietaryTrialIi));
        spDTO.setCurrentUserIsSiteOwner(true);
        spDTO.setSearcherTrialOwner(true);
        LastCreatedDTO lastCreated = new LastCreatedDTO();
        lastCreated.setUserLastCreated("loginName");
        lastCreated.setDateLastCreated(getCurrentDate());
        spDTO.setLastCreated(lastCreated);
        when(protocolQueryService.getTrialSummaryByStudyProtocolId(1L))
                .thenReturn(spDTO);
        List<DocumentWorkflowStatusDTO> documentList = new ArrayList<DocumentWorkflowStatusDTO>();
        DocumentWorkflowStatusDTO documentDto = new DocumentWorkflowStatusDTO();
        documentDto.setIdentifier(IiConverter.convertToIi(1L));
        documentDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.REJECTED));
        documentDto.setCommentText(StConverter.convertToSt("Comment"));
        documentList.add(documentDto);
        when(docWrkStatService.getByStudyProtocol(nonProprietaryTrialIi)).thenReturn(documentList);
        when(registryUserService.isEmailNotificationsEnabled(any(Long.class), any(Long.class))).thenReturn(true);
        
        sut.sendRejectionEmail(nonProprietaryTrialIi);

        verify(protocolQueryService, atLeastOnce()).getTrialSummaryByStudyProtocolId(1L);

        ArgumentCaptor<String> mailSubjectCaptor = ArgumentCaptor
                .forClass(String.class);
        ArgumentCaptor<String> mailBodyCaptor = ArgumentCaptor
                .forClass(String.class);

        verify(sut).sendMailWithHtmlBody(eq(email1),
                mailSubjectCaptor.capture(), mailBodyCaptor.capture());

        assertEquals("BODY "+ getFormatedCurrentDate() +" lname1 fname1 localStudyProtocolIdentifier,1, "
        		+ "officialTitle,nciIdentifier, "+getFormatedCurrentDate() +", Comment.",
                mailBodyCaptor.getValue());
    }

    @Test
    public void testSendXmlTSREmail() throws PAException {
    	 PAProperties prop = new PAProperties();
         prop.setName("xml.body");
         prop.setValue("${nciTrialIdentifier} ${leadOrgTrialIdentifier} ${leadOrgID}.");
         TestSchema.addUpdObject(prop);
        bean.sendXMLAndTSREmail(email1, email1, nonProprietaryTrialIi);
        StudyProtocolQueryDTO spDTO = protocolQrySrv.getTrialSummaryByStudyProtocolId(
                IiConverter.convertToLong(nonProprietaryTrialIi));
        spDTO.setLeadOrganizationPOId(1L);
        spDTO.setAmendmentNumber("1");
        assertEquals("NCI-2009-00001 Ecog1 1.", bean.commonMailBodyReplacements(spDTO, prop.getValue()));
    }

    @Test
    public void testSendUpdateNotificationMail() throws PAException {
        PAProperties prop = new PAProperties();
        prop.setName("trial.update.subject");
        prop.setValue("trial.update.subject ${leadOrgTrialIdentifier}, ${nciTrialIdentifier}.");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("trial.update.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier}, ${trialTitle}, (${leadOrgTrialIdentifier}), ${leadOrgID}.");
        TestSchema.addUpdObject(prop);

        bean.sendUpdateNotificationMail(nonProprietaryTrialIi, "");
        StudyProtocolQueryDTO spDTO = protocolQrySrv.getTrialSummaryByStudyProtocolId(
                IiConverter.convertToLong(nonProprietaryTrialIi));
        spDTO.setLeadOrganizationPOId(1L);
        spDTO.setAmendmentNumber("1");
        assertEquals(getFormatedCurrentDate() + " ${SubmitterName}NCI-2009-00001, Cancer for kids, (Ecog1), 1.", 
        		bean.commonMailBodyReplacements(spDTO, prop.getValue()));
    }

    @Test
    public void testSendMailWithAttachment() throws PAException {
        bean.sendMailWithAttachment(email1, "testSubject", "testBody", null);
    }

    @Test
    public void testSendTSREmailProprietary() throws PAException {
        bean.sendTSREmail(proprietaryTrialIi);
    }

    @Test
    public void testSendMarkerCDERequestEmail() throws PAException {
        PlannedMarkerDTO dto = new PlannedMarkerDTO();
        dto.setName(StConverter.convertToSt("Marker #1"));
        dto.setHugoBiomarkerCode(CdConverter.convertStringToCd("HUGO"));
        bean.sendMarkerCDERequestMail(proprietaryTrialIi, "from@example.com",
                dto, "Marker Text");
    }

    @Test
    public void testSendMarkerCDERequestEmailSubmitterName() throws PAException {
        PlannedMarkerDTO dto = new PlannedMarkerDTO();
        dto.setName(StConverter.convertToSt("Marker #1"));
        dto.setHugoBiomarkerCode(CdConverter.convertStringToCd("HUGO"));
        User csmUser = createUser("LoginName", "", "");
        TestSchema.addUpdObject(csmUser);
        RegistryUser user = new RegistryUser();
        user.setLastName("LastName");
        user.setFirstName("FirstName");
        user.setEmailAddress(email1);
        user.setAffiliateOrg("Affiliated Organization");
        user.setCsmUser(csmUser);
        TestSchema.addUpdObject(user);
        bean.sendMarkerCDERequestMail(proprietaryTrialIi, "from@example.com",
                dto, "Marker Text");
    }

    @Test
    public void testSendMarkerCDERequestEmailNoSubmitterName()
            throws PAException {
        PlannedMarkerDTO dto = new PlannedMarkerDTO();
        dto.setName(StConverter.convertToSt("Marker #1"));
        dto.setHugoBiomarkerCode(CdConverter.convertStringToCd("HUGO"));
        User csmUser = createUser("LoginName", "", "");
        TestSchema.addUpdObject(csmUser);
        RegistryUser user = new RegistryUser();
        user.setLastName("");
        user.setFirstName("");
        user.setEmailAddress(email1);
        user.setAffiliateOrg("Affiliated Organization");
        user.setCsmUser(csmUser);
        TestSchema.addUpdObject(user);
        bean.sendMarkerCDERequestMail(proprietaryTrialIi, "from@example.com",
                dto, "Marker Text");
    }

    @Test
    public void testSendMarkerCDERequestEmailNoSubmitterEmail()
            throws PAException {
        PlannedMarkerDTO dto = new PlannedMarkerDTO();
        dto.setName(StConverter.convertToSt("Marker #1"));
        dto.setHugoBiomarkerCode(CdConverter.convertStringToCd("HUGO"));
        User csmUser = createUser("LoginName", "", "");
        TestSchema.addUpdObject(csmUser);
        RegistryUser user = new RegistryUser();
        user.setLastName("");
        user.setFirstName("");
        user.setEmailAddress("");
        user.setAffiliateOrg("Affiliated Organization");
        user.setCsmUser(csmUser);
        TestSchema.addUpdObject(user);
        bean.sendMarkerCDERequestMail(proprietaryTrialIi, "from@example.com",
                dto, "Marker Text");
    }

    @Test
    public void testGetMarkerEmailAddress() throws PAException {
        PlannedMarkerDTO dto = new PlannedMarkerDTO();
        dto.setName(StConverter.convertToSt("Marker #1"));
        dto.setHugoBiomarkerCode(CdConverter.convertStringToCd("HUGO"));
        dto.setUserLastCreated(StConverter.convertToSt("3"));
        assertEquals("user1@mail.nih.gov", bean.getMarkerEmailAddress(dto));
        dto.setUserLastCreated(StConverter.convertToSt("1000"));
        assertEquals("example1@example.com", bean.getMarkerEmailAddress(dto));
    }

    @Test
    public void testsendMarkerAcceptanceMailToCDE() throws PAException {
         sut = createMailManagerServiceMock();
         String nciIdentifier = "nciIdentifier";
         String from = "from@example.com";
         PAProperties prop = new PAProperties();
         prop.setName("CDE_REQUEST_TO_EMAIL");
         prop.setValue("to@example.com, to1@example.com, test@gmail.com");
         PlannedMarkerDTO dto = new PlannedMarkerDTO();
         dto.setName(StConverter.convertToSt("Marker #1"));
         dto.setHugoBiomarkerCode(CdConverter.convertStringToCd("HUGO"));
         dto.setUserLastCreated(StConverter.convertToSt("1"));

         doCallRealMethod().when(sut).sendMarkerAcceptanceMailToCDE(nciIdentifier, from, dto);
         when(lookUpTableService.getPropertyValue("CDE_REQUEST_TO_EMAIL"))
                 .thenReturn("to@example.com,to1@example.com,test@gmail.com");
         when(lookUpTableService.getPropertyValue("fromaddress"))
         .thenReturn("fromAddress@example.com");
         DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault());
         
         sut.sendMarkerAcceptanceMailToCDE(nciIdentifier, from, dto);
         File[] file = null;
         List<String> copyList = new ArrayList<String>();
         copyList.add("to1@example.com");
         copyList.add("test@gmail.com");
         ArgumentCaptor<String> mailSubjectCaptor = ArgumentCaptor
                 .forClass(String.class);
         ArgumentCaptor<String> mailBodyCaptor = ArgumentCaptor
                 .forClass(String.class);
         verify(sut).sendMailWithAttachment(eq("to@example.com"), 
             eq("from@example.com"), eq(copyList), 
             mailSubjectCaptor.capture(), mailBodyCaptor.capture(), eq(file),
              eq(false));

         assertEquals("Mail subject",
                 "Accepted New biomarker Marker #1,HUGO code:HUGO in CTRP PA",
                 mailSubjectCaptor.getValue());
         assertEquals("Mail body",
                 "Dear caDSR," + "\n\n"
                + "This is just to notify you that a marker 'Marker #1,HUGO code:HUGO' has been "
                + "accepted in the CTRP Protocol Abstraction  on " + dateFormat.format(new Date()) 
                + ". However, this marker may still "
                + "need to be added into the caDSR repository."
                + "\n\n"
                + "Thank you"
                + "\n"
                + "NCI Clinical Trials Reporting Program", mailBodyCaptor.getValue().trim());
    }

    @Test
    public void testsendMarkerAcceptanceMailToCDEUserNameNull()
            throws PAException {
        String nciIdentifier = "nciIdentifier";
        String from = "from@example.com";
        User csmUser = createUser("LoginName", "", "");
        TestSchema.addUpdObject(csmUser);
        RegistryUser user = new RegistryUser();
        user.setLastName("LastName");
        user.setFirstName("FirstName");
        user.setEmailAddress(email1);
        user.setAffiliateOrg("Affiliated Organization");
        user.setCsmUser(csmUser);
        TestSchema.addUpdObject(user);
        PlannedMarkerDTO dto = new PlannedMarkerDTO();
        dto.setName(StConverter.convertToSt("Marker #1"));
        dto.setHugoBiomarkerCode(CdConverter.convertStringToCd("HUGO"));
        dto.setUserLastCreated(StConverter.convertToSt("1"));

        bean.sendMarkerAcceptanceMailToCDE(nciIdentifier, from, dto);
    }

    @Test
    public void testsendMarkerAcceptanceMailToCDERegUserNameNull()
            throws PAException {
        String nciIdentifier = "nciIdentifier";
        String from = "from@example.com";
        User csmUser = createUser("LoginName", "", "");
        TestSchema.addUpdObject(csmUser);
        RegistryUser user = new RegistryUser();
        user.setLastName("");
        user.setFirstName("");
        user.setEmailAddress(email1);
        user.setAffiliateOrg("Affiliated Organization");
        user.setCsmUser(csmUser);
        TestSchema.addUpdObject(user);
        PlannedMarkerDTO dto = new PlannedMarkerDTO();
        dto.setName(StConverter.convertToSt("Marker #1"));
        dto.setHugoBiomarkerCode(CdConverter.convertStringToCd("HUGO"));
        dto.setUserLastCreated(StConverter.convertToSt("1"));

        bean.sendMarkerAcceptanceMailToCDE(nciIdentifier, from, dto);
    }

    @Test
    public void testsendMarkerAcceptanceMailToCDENoException()
            throws PAException {
        String nciIdentifier = "nciIdentifier";
        String from = "from@example.com";

        UsernameHolder.setUser(null);
        PlannedMarkerDTO dto = new PlannedMarkerDTO();
        dto.setName(StConverter.convertToSt("Marker #1"));
        dto.setHugoBiomarkerCode(CdConverter.convertStringToCd("HUGO"));
        dto.setUserLastCreated(StConverter.convertToSt("abc"));
        try {
            bean.sendMarkerAcceptanceMailToCDE(nciIdentifier, from, dto);
        } catch (Exception e) {

        }

    }

    @Test
    public void testsendMarkerQuestionToCTROMail() throws PAException {
        String nciIdentifier = "nciIdentifier";
        String to = "from@example.com";
        String question = "This is a question";

        PlannedMarkerDTO dto = new PlannedMarkerDTO();
        dto.setName(StConverter.convertToSt("Marker #1"));
        dto.setHugoBiomarkerCode(CdConverter.convertStringToCd("HUGO"));
        dto.setUserLastCreated(StConverter.convertToSt("1"));

        bean.sendMarkerQuestionToCTROMail(nciIdentifier, to, dto, question);
    }

    private StudyProtocol createProprietaryStudyProtocolObj() {
        StudyProtocol sp = new StudyProtocol();
        Timestamp now = new Timestamp(new Date().getTime());

        sp.setAcronym("Acronym .....");
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        sp.setDataMonitoringCommitteeAppointedIndicator(Boolean.TRUE);
        sp.setDelayedpostingIndicator(Boolean.TRUE);
        sp.setExpandedAccessIndicator(Boolean.TRUE);
        sp.setFdaRegulatedIndicator(Boolean.TRUE);
        Set<Ii> studySecondaryIdentifiers = new HashSet<Ii>();
        Ii spSecId = new Ii();
        spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        spSecId.setExtension("NCI-P-2009-00001");
        studySecondaryIdentifiers.add(spSecId);
        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setKeywordText("keywordText");
        sp.setOfficialTitle("Cancer for kids");
        sp.setPhaseCode(PhaseCode.I);
        sp.setPhaseAdditionalQualifierCode(PhaseAdditionalQualifierCode.PILOT);
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.PREVENTION);
        sp.setPrimaryPurposeAdditionalQualifierCode(PrimaryPurposeAdditionalQualifierCode.CORRELATIVE);

        sp.setPublicDescription("publicDescription");
        sp.setPublicTitle("publicTitle");
        sp.setRecordVerificationDate(now);
        sp.setScientificDescription("scientificDescription");
        sp.setSection801Indicator(Boolean.TRUE);

        sp.setDateLastUpdated(new Timestamp(new Date().getTime()));
        sp.setUserLastUpdated(TestSchema.getUser());
        sp.setDateLastCreated(now);
        sp.setUserLastCreated(TestSchema.getUser());
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setAmendmentReasonCode(AmendmentReasonCode.BOTH);
        sp.setStatusDate(now);
        sp.setAmendmentDate(now);
        sp.setAmendmentNumber("amendmentNumber");
        sp.setSubmissionNumber(1);
        sp.setProprietaryTrialIndicator(Boolean.TRUE);
        sp.setCtgovXmlRequiredIndicator(Boolean.FALSE);
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(now);
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        dates.setPrimaryCompletionDate(now);
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        return sp;
    }

    /**
     * Test the sendNotificationMail method.
     * 
     * @throws PAException
     *             if an error occurs
     */
    @Test
    public void testSendNotificationMail() throws PAException {
        sut = createMailManagerServiceMock();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        doCallRealMethod().when(sut).sendNotificationMail(spIi,
                Arrays.asList("bademail"));

        doCallRealMethod().when(sut).commonMailSubjectReplacements(
                any(StudyProtocolQueryDTO.class), any(String.class));

        doCallRealMethod().when(sut).commonMailBodyReplacements(
                any(StudyProtocolQueryDTO.class), any(String.class));

        when(sut.getFormatedCurrentDate()).thenReturn("Current Date");
        when(lookUpTableService.getPropertyValue(NOTFICATION_SUBJECT_KEY))
                .thenReturn(NOTFICATION_SUBJECT_VALUE);
        when(lookUpTableService.getPropertyValue(NOTFICATION_BODY_KEY))
                .thenReturn(NOTFICATION_BODY_VALUE);
        when(lookUpTableService.getPropertyValue(ERRORS_BODY_KEY)).thenReturn(
                ERRORS_BODY_VALUE);

        StudyProtocolQueryDTO spDTO = new StudyProtocolQueryDTO();
        spDTO.setProprietaryTrial(false);
        spDTO.setLocalStudyProtocolIdentifier("localStudyProtocolIdentifier");
        spDTO.setNciIdentifier("nciIdentifier");
        spDTO.setLeadOrganizationName("leadOrganizationName");
        spDTO.setOfficialTitle("officialTitle");
        LastCreatedDTO lastCreated = new LastCreatedDTO();
        lastCreated.setUserLastCreated("loginName");
        lastCreated.setDateLastCreated(getCurrentDate());
        spDTO.setLastCreated(lastCreated);

        when(protocolQueryService.getTrialSummaryByStudyProtocolId(1L))
                .thenReturn(spDTO);
        RegistryUser user = new RegistryUser();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmailAddress("emailAddress");
        when(registryUserService.getUser("loginName")).thenReturn(user);
        sut.sendNotificationMail(spIi, Arrays.asList("bademail"));
        verify(protocolQueryService).getTrialSummaryByStudyProtocolId(1L);
        verify(registryUserService).getUser("loginName");
        ArgumentCaptor<String> mailSubjectCaptor = ArgumentCaptor
                .forClass(String.class);
        ArgumentCaptor<String> mailBodyCaptor = ArgumentCaptor
                .forClass(String.class);

        verify(sut).sendMailWithHtmlBody(eq("emailAddress"),
                mailSubjectCaptor.capture(), mailBodyCaptor.capture());
        assertEquals(
                "Wrong mail subject",
                "trial.register.subject - localStudyProtocolIdentifier, nciIdentifier.",
                mailSubjectCaptor.getValue());
        assertEquals("Wrong mail body",
                "Current Date firstName lastName nciIdentifier, localStudyProtocolIdentifier, "
                        + "leadOrganizationName, officialTitle.bademail",
                mailBodyCaptor.getValue());
    }

    /**
     * Test the sendNotificationMail method for a proprietary study protocol.
     * 
     * @throws PAException
     *             if an error occurs
     */
    @Test
    public void testSendNotificationMailProprietary() throws PAException {
        sut = createMailManagerServiceMock();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        doCallRealMethod().when(sut).sendNotificationMail(spIi, null);

        doCallRealMethod().when(sut).commonMailSubjectReplacements(
                any(StudyProtocolQueryDTO.class), any(String.class));

        doCallRealMethod().when(sut).commonMailBodyReplacements(
                any(StudyProtocolQueryDTO.class), any(String.class));

        when(sut.getFormatedCurrentDate()).thenReturn("Current Date");
        when(lookUpTableService.getPropertyValue(PROP_NOTFICATION_SUBJECT_KEY))
                .thenReturn(PROP_NOTFICATION_SUBJECT_VALUE);
        when(lookUpTableService.getPropertyValue(PROP_NOTFICATION_BODY_KEY))
                .thenReturn(PROP_NOTFICATION_BODY_VALUE);

        StudyProtocolQueryDTO spDTO = new StudyProtocolQueryDTO();
        spDTO.setProprietaryTrial(true);
        spDTO.setLocalStudyProtocolIdentifier("localStudyProtocolIdentifier");
        spDTO.setNciIdentifier("nciIdentifier");
        spDTO.setLeadOrganizationName("leadOrganizationName");
        spDTO.setLeadOrganizationPOId(1L);
        spDTO.setOfficialTitle("officialTitle");
        LastCreatedDTO lastCreated = new LastCreatedDTO();
        lastCreated.setUserLastCreated("loginName");
        lastCreated.setDateLastCreated(getCurrentDate());
        spDTO.setLastCreated(lastCreated);

        when(protocolQueryService.getTrialSummaryByStudyProtocolId(1L))
                .thenReturn(spDTO);
        RegistryUser user = new RegistryUser();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmailAddress("emailAddress");
        when(registryUserService.getUser("loginName")).thenReturn(user);

        List<StudySiteDTO> studySites = new ArrayList<StudySiteDTO>();
        StudySiteDTO site = new StudySiteDTO();
        site.setLocalStudyProtocolIdentifier(StConverter
                .convertToSt("siteLocalStudyProtocolIdentifier"));
        site.setFunctionalCode(CdConverter
                .convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        site.setHealthcareFacilityIi(IiConverter
                .convertToPoHealthCareFacilityIi("1"));
        studySites.add(site);
        when(
                studySiteService.getByStudyProtocol(any(Ii.class),
                        anyListOf(StudySiteDTO.class))).thenReturn(studySites);
        sut.setStudySiteService(studySiteService);
        
        sut.sendNotificationMail(spIi, null);
        verify(protocolQueryService).getTrialSummaryByStudyProtocolId(1L);
        verify(registryUserService).getUser("loginName");
        
        ArgumentCaptor<String> mailSubjectCaptor = ArgumentCaptor
                .forClass(String.class);
        ArgumentCaptor<String> mailBodyCaptor = ArgumentCaptor
                .forClass(String.class);

        verify(sut).sendMailWithHtmlBody(eq("emailAddress"),
                mailSubjectCaptor.capture(), mailBodyCaptor.capture());

        assertEquals("Wrong mail subject",
                "proprietarytrial.register.subject - localStudyProtocolIdentifier, "
                        + "nciIdentifier localStudyProtocolIdentifier.",
                mailSubjectCaptor.getValue());
        assertEquals(
                "Wrong mail body",
                "Current Date firstName lastName nciIdentifier, localStudyProtocolIdentifier,"
                        + " leadOrganizationName, 1, officialTitle, siteLocalStudyProtocolIdentifier,"
                        + " Mayo University.", mailBodyCaptor.getValue().trim());
    }

    /**
     * Test the sendNotificationMail method when the user does not exist.
     * 
     * @throws PAException
     *             if an error occurs
     */
    @Test
    public void testSendNotificationMailNoUser() throws PAException {
        sut = createMailManagerServiceMock();
        Ii spIi = IiConverter.convertToStudyProtocolIi(1L);
        doCallRealMethod().when(sut).sendNotificationMail(spIi, null);

        StudyProtocolQueryDTO spDTO = new StudyProtocolQueryDTO();
        LastCreatedDTO lastCreated = new LastCreatedDTO();
        lastCreated.setUserLastCreated("loginName");
        spDTO.setLastCreated(lastCreated);
        when(protocolQueryService.getTrialSummaryByStudyProtocolId(1L))
                .thenReturn(spDTO);

        sut.sendNotificationMail(spIi, null);
        verify(protocolQueryService).getTrialSummaryByStudyProtocolId(1L);
        verify(registryUserService).getUser("loginName");
        verify(sut, never()).sendMailWithAttachment(anyString(), anyString(),
                anyString(), (File[]) any());
    }

    /**
     * Test the sendSearchUsernameEmail method with an invalid email.
     * 
     * @throws PAException
     *             if an error occurs
     */
    @Test(expected = PAException.class)
    public void testSendSearchUsernameEmailInvalid() throws PAException {
        sut = createMailManagerServiceBean();
        sut.sendSearchUsernameEmail("abcd");
    }

    /**
     * Test the sendSearchUsernameEmail method with no user.
     * 
     * @throws PAException
     *             if an error occurs
     */
    @Test
    public void testSendSearchUsernameEmailNotFound() throws PAException {
        sut = createMailManagerServiceMock();
        String emailAddress = "username@nci.nih.gov";
        doCallRealMethod().when(sut).sendSearchUsernameEmail(emailAddress);
        List<RegistryUser> users = new ArrayList<RegistryUser>();
        when(registryUserService.getLoginNamesByEmailAddress(emailAddress))
                .thenReturn(users);
        boolean result = sut.sendSearchUsernameEmail(emailAddress);
        assertFalse("Wrong result returned", result);
        verify(registryUserService).getLoginNamesByEmailAddress(emailAddress);
    }

    /**
     * Test the sendSearchUsernameEmail method with one user.
     * 
     * @throws PAException
     *             if an error occurs
     */
    @Test
    public void testSendSearchUsernameEmailFound() throws PAException {
        sut = createMailManagerServiceMock();
        String emailAddress = "username@nci.nih.gov";
        doCallRealMethod().when(sut).sendSearchUsernameEmail(emailAddress);
        List<RegistryUser> users = new ArrayList<RegistryUser>();
        RegistryUser registryUser = new RegistryUser();
        users.add(registryUser);
        when(registryUserService.getLoginNamesByEmailAddress(emailAddress))
                .thenReturn(users);
        List<String> userNames = new ArrayList<String>();
        userNames.add("username");
        when(sut.getGridIdentityUsernames(users)).thenReturn(userNames);
        boolean result = sut.sendSearchUsernameEmail(emailAddress);
        assertTrue("Wrong result returned", result);
        verify(registryUserService).getLoginNamesByEmailAddress(emailAddress);
        verify(sut).sendSearchUsernameEmail(emailAddress, users);
    }

    /**
     * Test the getGridIdentityUsernames method.
     */
    @Test
    public void testGetGridIdentityUsernames() {
        sut = createMailManagerServiceBean();
        List<RegistryUser> users = new ArrayList<RegistryUser>();
        users.add(createRegistryUser("/CN=username1"));
        users.add(createRegistryUser("username2"));
        List<String> result = sut.getGridIdentityUsernames(users);
        assertNotNull("No result returned", result);
        assertEquals("Wrong result size", 2, result.size());
        assertEquals("Wrong username in position 0", "username1", result.get(0));
        assertEquals("Wrong username in position 1", "username2", result.get(1));
    }

    /**
     * Test the sendUsernameSearchEmail method.
     * 
     * @throws PAException
     *             if an error occurs
     */
    @Test
    public void testSendSearchUsernameEmail() throws PAException {
        sut = createMailManagerServiceMock();
        String emailAddress = "username@nci.nih.gov";
        List<RegistryUser> users = new ArrayList<RegistryUser>();
        RegistryUser user = createRegistryUser("/CN=username1");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        users.add(user);
        users.add(createRegistryUser("username2"));
        doCallRealMethod().when(sut).sendSearchUsernameEmail(emailAddress,
                users);
        doCallRealMethod().when(sut).getGridIdentityUsernames(users);
        String body = "body ${firstName} ${lastName} ${userNames} end";
        String subject = "subject";
        when(lookUpTableService.getPropertyValue("user.usernameSearch.body"))
                .thenReturn(body);
        when(lookUpTableService.getPropertyValue("user.usernameSearch.subject"))
                .thenReturn(subject);
        sut.sendSearchUsernameEmail(emailAddress, users);
        verify(lookUpTableService).getPropertyValue("user.usernameSearch.body");
        verify(lookUpTableService).getPropertyValue(
                "user.usernameSearch.subject");
        verify(sut).getGridIdentityUsernames(users);
        String expectedBody = "body firstName lastName username1, username2 end";

        verify(sut).sendMailWithHtmlBody(emailAddress, subject, expectedBody);
    }

    private RegistryUser createRegistryUser(String loginName) {
        RegistryUser registryUser = new RegistryUser();
        User csmUser = new User();
        csmUser.setLoginName(loginName);
        registryUser.setCsmUser(csmUser);
        return registryUser;
    }

    /**
     * Test the sendUnidentifiableOwnerEmail method.
     * 
     * @throws PAException
     *             if an error occurs
     */
    @Test
    public void testSendUnidentifiableOwnerEmail() throws PAException {
        sut = createMailManagerServiceMock();
        IiConverter.convertToStudyProtocolIi(1L);
        doCallRealMethod().when(sut).sendUnidentifiableOwnerEmail(
                any(Long.class), any(Collection.class));
        when(sut.findAffiliatedOrg(any(RegistryUser.class))).thenReturn("NCI");

        doCallRealMethod().when(sut).getFormatedCurrentDate();

        doCallRealMethod().when(sut).commonMailBodyReplacements(
                any(StudyProtocolQueryDTO.class), any(String.class));

        when(
                lookUpTableService
                        .getPropertyValue("trial.register.unidentifiableOwner.email.subject"))
                .thenReturn(
                        "NCI CTRP: CTRP Trial Record Ownership Assignment: EMAIL ADDRESS ERROR");
        when(
                lookUpTableService
                        .getPropertyValue("trial.register.unidentifiableOwner.email.body"))
                .thenReturn(
                        "BODY ${nciTrialIdentifier} ${leadOrgName} ${badEmail} ${leadOrgID}");
        when(
                lookUpTableService
                        .getPropertyValue("trial.register.mismatchedUser.email.subject"))
                .thenReturn(
                        "NCI CTRP: CTRP ACCOUNT STATUS: TRIAL OWNERSHIP ERROR");
        when(
                lookUpTableService
                        .getPropertyValue("trial.register.mismatchedUser.email.body"))
                .thenReturn(
                        "BODY ${nciTrialIdentifier} ${leadOrgName} ${badEmail}");

        when(lookUpTableService.getPropertyValue("abstraction.script.mailTo"))
                .thenReturn("denis.krylov@semanticbits.com");

        StudyProtocolQueryDTO spDTO = new StudyProtocolQueryDTO();
        spDTO.setProprietaryTrial(false);
        spDTO.setLocalStudyProtocolIdentifier("localStudyProtocolIdentifier");
        spDTO.setNciIdentifier("nciIdentifier");
        spDTO.setLeadOrganizationName("NCI");
        spDTO.setLeadOrganizationPOId(1L);
        spDTO.setOfficialTitle("officialTitle");
        LastCreatedDTO lastCreated = new LastCreatedDTO();
        lastCreated.setUserLastCreated("loginName");
        lastCreated.setDateLastCreated(getCurrentDate());
        spDTO.setLastCreated(lastCreated);
        when(protocolQueryService.getTrialSummaryByStudyProtocolId(1L))
                .thenReturn(spDTO);

        RegistryUser user = new RegistryUser();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmailAddress("denis.krylov@semanticbits.com");
        when(registryUserService.getUser("loginName")).thenReturn(user);

        sut.sendUnidentifiableOwnerEmail(1L,
                Arrays.asList("bademail@semanticbits.com"));

        verify(protocolQueryService, atLeastOnce())
                .getTrialSummaryByStudyProtocolId(1L);
        verify(registryUserService, atLeastOnce()).getUser("loginName");

        ArgumentCaptor<String> mailSubjectCaptor = ArgumentCaptor
                .forClass(String.class);
        ArgumentCaptor<String> mailBodyCaptor = ArgumentCaptor
                .forClass(String.class);

        verify(sut).sendMailWithHtmlBody(eq("denis.krylov@semanticbits.com"),
                mailSubjectCaptor.capture(), mailBodyCaptor.capture());

        assertEquals(
                "Wrong mail subject",
                "NCI CTRP: CTRP Trial Record Ownership Assignment: EMAIL ADDRESS ERROR",
                mailSubjectCaptor.getValue());
        assertEquals("Wrong mail body",
                "BODY nciIdentifier NCI bademail@semanticbits.com 1",
                mailBodyCaptor.getValue());
    }

    /**
     * Test the method used to prepare all MIME messages.
     */
    @Test
    public void prepareMessageTest() throws Exception {
        MimeMessage result = bean.prepareMessage("to", "from", null, "subject");
        Address from = result.getFrom()[0];
        Address to = result.getRecipients(Message.RecipientType.TO)[0];
        Address bcc = result.getRecipients(Message.RecipientType.BCC)[0];
        assertEquals("from", from.toString());
        assertEquals("to", to.toString());
        assertEquals("logctrp@example.com", bcc.toString());
        assertEquals("subject", result.getSubject());
    }

    @Test
    public void prepareMessageTestWithCC() throws Exception {
        List<String> copy = new ArrayList<String>();
        copy.add("copy");
        copy.add("CDE_MARKER_REQUEST_FROM_EMAIL");
        MimeMessage result = bean.prepareMessage("to", "from", copy, "subject");
        Address from = result.getFrom()[0];
        Address to = result.getRecipients(Message.RecipientType.TO)[0];
        Address cc = result.getRecipients(Message.RecipientType.CC)[0];
        Address bcc = result.getRecipients(Message.RecipientType.BCC)[0];
        assertEquals("from", from.toString());
        assertEquals("to", to.toString());
        assertEquals("logctrp@example.com", bcc.toString());
        assertEquals("subject", result.getSubject());
        assertEquals("copy", cc.toString());
    }

    @Test
    public void sendCadsrJobErrorEMailTest() throws Exception {
        sut = createMailManagerServiceMock();
        PAProperties prop = new PAProperties();
        prop.setName("CADSR_SYNC_JOB_EMAIl_LIST");
        prop.setValue("to@example.com, to1@example.com, test@gmail.com");
        
        prop = new PAProperties();
        prop.setName("CADSR_SYNC_JOB_ERROR_BODY");
        prop.setValue(" ${CurrentDate} end");
        
        doCallRealMethod().when(sut).sendCadsrJobErrorEMail();
        when(lookUpTableService.getPropertyValue("CADSR_SYNC_JOB_ERROR_SUBJECT"))
        .thenReturn("NCI CTRP: caDSR Biomarker SYNCHRONIZATION FAILURE");  
        when(lookUpTableService.getPropertyValue("CADSR_SYNC_JOB_ERROR_BODY"))
        .thenReturn("${CurrentDate} end"); 
        when(lookUpTableService.getPropertyValue("CADSR_SYNC_JOB_EMAIl_LIST"))
                .thenReturn("to@example.com, to1@example.com, test@gmail.com");
        when(lookUpTableService.getPropertyValue("CADSR_SYNC_JOB_FROM_ADDRESS"))
        .thenReturn("fromAddress@example.com");
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        
        sut.sendCadsrJobErrorEMail();
        List<String> copyList = new ArrayList<String>();
        copyList.add(" to1@example.com");
        copyList.add(" test@gmail.com");
        ArgumentCaptor<String> mailSubjectCaptor = ArgumentCaptor
                .forClass(String.class);
        ArgumentCaptor<String> mailBodyCaptor = ArgumentCaptor
                .forClass(String.class);
        verify(sut).sendMailWithHtmlBody(eq("fromAddress@example.com"), 
            eq("to@example.com"), eq(copyList), 
            mailSubjectCaptor.capture(), mailBodyCaptor.capture());

        assertEquals("Mail subject",
                "NCI CTRP: caDSR Biomarker SYNCHRONIZATION FAILURE",
                mailSubjectCaptor.getValue());
        assertEquals("Mail body",
                dateFormat.format(new Date()) + " end"
            , mailBodyCaptor.getValue().trim());
    }

    private Date getCurrentDate() {
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        return date;
    }

    @Test
    public void sendCTROVerifyDataEmail() throws PAException {
        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setNciIdentifier("NCI-2009-0010");
        dto.setLeadOrganizationName("organization");
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        LastCreatedDTO lastCreated = new LastCreatedDTO();
        lastCreated.setUserLastCreated("User");
        lastCreated.setUserLastDisplayName("firstLastName");
        lastCreated.setDateLastCreated(new Date());
        dto.setLastCreated(lastCreated);
        dto.setRecordVerificationDate(new Date());
        dto.setVerificationDueDate(new Date());
        list.add(dto);

        PAProperties prop = new PAProperties();
        prop.setName("verifyDataCTRO.email.subject");
        prop.setValue("CTRP Trials Due for Data Verification on ${dueDate}");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("verifyDataCTRO.email.bodyHeader");
        prop.setValue("verifyDataCTRO.email.bodyHeader");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("verifyDataCTRO.email.body");
        prop.setValue("verifyDataCTRO.email.body");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("verifyDataCTRO.email.bodyFooter");
        prop.setValue("verifyDataCTRO.email.bodyFooter");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("TrialDataVerificationNotificationsEffectiveDate");
        prop.setValue("06/01/2013");
        TestSchema.addUpdObject(prop);

        bean.sendCTROVerifyDataEmail(list);
        
        prop = new PAProperties();
        prop.setName("verifyDataCTRO.email.body");
        prop.setValue("${nciTrialIdentifier} ${leadOrgTrialIdentifier} ${leadOrgID}");
        TestSchema.addUpdObject(prop);
        bean.sendCTROVerifyDataEmail(list);
        StudyProtocolQueryDTO spDTO = protocolQrySrv.getTrialSummaryByStudyProtocolId(
               IiConverter.convertToLong(nonProprietaryTrialIi));
       spDTO.setLeadOrganizationPOId(1L);
       spDTO.setAmendmentNumber("1");
       assertEquals("NCI-2009-00001 Ecog1 1", bean.commonMailBodyReplacements(spDTO, prop.getValue()));
      
    }
    
    @Test
    public void sendVerifyDataEmail() throws PAException {
        Map<RegistryUser, List<StudyProtocolQueryDTO>> map = new HashMap<RegistryUser, List<StudyProtocolQueryDTO>>();
        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        dto.setNciIdentifier("NCI-2009-0010");
        dto.setLeadOrganizationName("organization");
        dto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        LastCreatedDTO lastCreated = new LastCreatedDTO();
        lastCreated.setUserLastCreated("User");
        lastCreated.setUserLastDisplayName("firstLastName");
        lastCreated.setDateLastCreated(new Date());
        dto.setLastCreated(lastCreated);
        dto.setRecordVerificationDate(new Date());
        dto.setVerificationDueDate(new Date());
        list.add(dto);
        RegistryUser user = new RegistryUser();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmailAddress("email@gmail.com");
        map.put(user, list);
        PAProperties prop = new PAProperties();
        prop.setName("verifyData.email.subject");
        prop.setValue("CTRP Trials Due for Data Verification");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("verifyData.email.bodyHeader");
        prop.setValue("verifyData.email.bodyHeader");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("verifyData.email.body");
        prop.setValue("verifyData.email.body");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("verifyData.email.bodyFooter");
        prop.setValue("verifyData.email.bodyFooter");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("TrialDataVerificationNotificationsEffectiveDate");
        prop.setValue("06/01/2013");
        TestSchema.addUpdObject(prop);
        
        prop = new PAProperties();
        prop.setName("group1TrialsVerificationFrequency");
        prop.setValue("12");
        TestSchema.addUpdObject(prop);
        bean.sendVerifyDataEmail(map);
        
        prop = new PAProperties();
        prop.setName("verifyData.email.body");
        prop.setValue("${nciTrialIdentifier} ${leadOrgTrialIdentifier}");
        TestSchema.addUpdObject(prop);
       bean.sendVerifyDataEmail(map);
       StudyProtocolQueryDTO spDTO = protocolQrySrv.getTrialSummaryByStudyProtocolId(
               IiConverter.convertToLong(nonProprietaryTrialIi));
       spDTO.setLeadOrganizationPOId(1L);
       spDTO.setAmendmentNumber("1");
       assertEquals("NCI-2009-00001 Ecog1", bean.commonMailBodyReplacements(spDTO, prop.getValue()));
    }
    /**
     * Gets the current date properly formatted.
     * @return The current date properly formatted.
     */
    String getFormatedCurrentDate() {
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        DateFormat format = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
        return format.format(date);
    }
    
    @Test
    public void sendVerifyCoverSheetEmail() throws PAException {
       
        PAProperties prop = new PAProperties();
        prop.setName("ccct.comparision.email.tolist");
        prop.setValue("ccct.comparision.email.tolist");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("ctro.coversheet.email.body");
        prop.setValue("ctro.coversheet.email.body");
        TestSchema.addUpdObject(prop);
        
        prop = new PAProperties();
        prop.setName("ctro.coversheet.email.subject");
        prop.setValue("Trial Data Verification sheet ${nciId}");
        TestSchema.addUpdObject(prop);
        
        StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
        
       
        List<StudyRecordChange> studyRecordChangeList = new ArrayList<StudyRecordChange>();
       
       
        
        StudyRecordChange studyRecordChange = new StudyRecordChange();
        studyRecordChange.setChangeType("change Type");
        studyRecordChange.setActionTaken("action taken");
        studyRecordChange.setActionCompletionDate(new Timestamp(new Date().getTime()));
        studyRecordChangeList.add(studyRecordChange);
      

        bean.sendCoverSheetEmail("123", studyProtocolDTO,  studyRecordChangeList);
        String mailSubject= bean.commonMailSubjectReplacementsForNCI(prop.getValue(), "123");
        assert(mailSubject.contains("123"));  
     
      
    }
    
    @Test
    public void sendVerifyComparisonDocumentToCtro() throws PAException {
        
        PAProperties prop = new PAProperties();
        prop.setName("abstraction.script.mailTo");
        prop.setValue("abstraction.script.mailTo");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("ctro.comparision.email.body");
        prop.setValue("ctro.comparision.email.body");
        TestSchema.addUpdObject(prop);
        
        prop = new PAProperties();
        prop.setName("ctro.comparision.email.subject");
        prop.setValue("Trial comparision document ${nciId}");
        TestSchema.addUpdObject(prop);
        
        bean.sendComparisonDocumentToCtro("123", "456", null);
        String mailSubject= bean.commonMailSubjectReplacementsForNCI(prop.getValue(), "123");
        assert(mailSubject.contains("123"));  
        
    }
    
    @Test
    public void sendVerifyComparisonDocumentToCcct() throws PAException {
        
        PAProperties prop = new PAProperties();
        prop.setName("ccct.comparision.email.tolist");
        prop.setValue("ccct.comparision.email.tolist");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("ccct.comparision.email.body");
        prop.setValue("ccct.comparision.email.body");
        TestSchema.addUpdObject(prop);
        
        prop = new PAProperties();
        prop.setName("ccct.comparision.email.subject");
        prop.setValue("Trial comparision document ${nciId}");
        TestSchema.addUpdObject(prop);
        
        bean.sendComparisonDocumentToCcct("123", "456", null);
        String mailSubject= bean.commonMailSubjectReplacementsForNCI(prop.getValue(), "123");
        assert(mailSubject.contains("123"));  
        
    }
    
    @Test
    public void sendNoTrialPublishDateUpdateEmail() throws PAException {
        
        PAProperties prop = new PAProperties();
        prop.setName("ccct.comparision.email.tolist");
        prop.setValue("ccct.comparision.email.tolist");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("resultsUpdater.trials.job.email.subject");
        prop.setValue("CTRP Nightly Job Update Trial Results Published Date'");
        TestSchema.addUpdObject(prop);
        
        prop = new PAProperties();
        prop.setName("resultsUpdater.trials.job.notupdated.email.body");
        prop.setValue("resultsUpdater.trials.job.notupdated.email.body");
        TestSchema.addUpdObject(prop);
        
        bean.sendTrialPublishDateNoUpdateEmail();
        String mailSubject= bean.commonMailSubjectReplacementsForNCI(prop.getValue(), "123");
        assert(mailSubject.contains("123"));  
        
    }
    
    @Test
    public void sendTrialPublishDateUpdateEmail() throws PAException {
        
        PAProperties prop = new PAProperties();
        prop.setName("ccct.comparision.email.tolist");
        prop.setValue("ccct.comparision.email.tolist");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("resultsUpdater.trials.job.email.subject");
        prop.setValue("CTRP Nightly Job Update Trial Results Published Date");
        TestSchema.addUpdObject(prop);
        
        prop = new PAProperties();
        prop.setName("resultsUpdater.trials.job.notupdated.email.body");
        prop.setValue("resultsUpdater.trials.job.notupdated.email.body");
        TestSchema.addUpdObject(prop);
        
        bean.sendTrialPublishDateNoUpdateEmail();
        String mailSubject= bean.commonMailSubjectReplacementsForNCI(prop.getValue(), "123");
        assert(mailSubject.contains("123"));  
        
    }


}

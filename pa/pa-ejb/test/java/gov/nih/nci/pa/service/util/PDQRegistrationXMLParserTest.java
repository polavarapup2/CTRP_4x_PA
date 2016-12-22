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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ludetc
 *
 */
public class PDQRegistrationXMLParserTest {

    private final URL testXMLUrl = this.getClass().getResource("/sample-pdq-input.xml");

    private PDQRegistrationXMLParser rXMLParser;
    private PoServiceLocator poSvcLoc;
    private IdentifiedPersonCorrelationServiceRemote identifierPersonSvc;

    @Before
    public void setup() throws PAException, NullifiedEntityException, TooManyResultsException {
        rXMLParser = new PDQRegistrationXMLParser();
        setupPoSvc();
        PAServiceUtils paServiceUtil = mock(PAServiceUtils.class);
        rXMLParser.setPaServiceUtils(paServiceUtil);
        when(paServiceUtil.getOrganizationByCtepId(anyString())).thenReturn(new OrganizationDTO());
        when(paServiceUtil.getPersonByCtepId(anyString())).thenReturn(new PersonDTO());

        ServiceLocator paSvcLoc = mock (ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);
        LookUpTableServiceRemote lookupSvc = mock (LookUpTableServiceRemote.class);
        when(paSvcLoc.getLookUpTableService()).thenReturn(lookupSvc);
    }

    private void setupPoSvc() throws NullifiedEntityException, PAException, TooManyResultsException {
        poSvcLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poSvcLoc);
        identifierPersonSvc = mock(IdentifiedPersonCorrelationServiceRemote.class);
        when(poSvcLoc.getIdentifiedPersonEntityService()).thenReturn(identifierPersonSvc);
        IdentifiedPersonDTO idPersonDTO = new IdentifiedPersonDTO();
        idPersonDTO.setPlayerIdentifier(IiConverter.convertToPoPersonIi("1"));
        List<IdentifiedPersonDTO> idPerDtos = new ArrayList<IdentifiedPersonDTO>();
        idPerDtos.add(idPersonDTO);
        when(identifierPersonSvc.search(any(IdentifiedPersonDTO.class))).thenReturn(idPerDtos);
    }

    @Test(expected = IllegalStateException.class)
    public void testIForgotToCallSetURL() throws PAException {
        rXMLParser.parse();
        rXMLParser.getStudyProtocolDTO();
    }

    @Test
    public void testMissingRespParty() throws PAException {
        URL url = this.getClass().getResource("/pdq-no-resp-party.xml");
        rXMLParser.setUrl(url);
        rXMLParser.parse();

        List<String> phones = DSetConverter.convertDSetToList(rXMLParser.getResponsiblePartyContact()
                .getTelecomAddress(), DSetConverter.TYPE_PHONE);
        assertTrue(phones.contains("000-000-0000"));
        assertEquals(1, phones.size());

        List<String> emails = DSetConverter.convertDSetToList(rXMLParser.getResponsiblePartyContact()
                .getTelecomAddress(), DSetConverter.TYPE_EMAIL);
        assertTrue(emails.contains("PIO@ctep.nci.nih.gov"));
        assertEquals(1, emails.size());

    }

    @Test
    public void testGetStudyProtocolDTO() throws PAException {
        rXMLParser.setUrl(testXMLUrl);
        rXMLParser.parse();

        StudyProtocolDTO spDto = rXMLParser.getStudyProtocolDTO();

        assertEquals("12/01/2001", TsConverter.convertToString(spDto.getStartDate()));
        assertEquals("09/27/2002", TsConverter.convertToString(spDto.getPrimaryCompletionDate()));
        assertEquals(ActualAnticipatedTypeCode.ACTUAL.getCode(), spDto.getPrimaryCompletionDateTypeCode().getCode());
        assertEquals("II", spDto.getPhaseCode().getCode());

        StudyOverallStatusDTO statusDTO = rXMLParser.getStudyOverallStatusDTO();
        assertEquals("No longer recruiting", CdConverter.convertCdToString(statusDTO.getStatusCode()));

        assertEquals(2, rXMLParser.getStudyProtocolDTO().getSecondaryIdentifiers().getItem().size());

        assertEquals(true, spDto.getFdaRegulatedIndicator().getValue());
        assertEquals(true, spDto.getSection801Indicator().getValue());
        assertEquals(false, spDto.getDelayedpostingIndicator().getValue());

        assertTrue(spDto.getOfficialTitle().getValue().startsWith("A Phase 2 Study Of Bevacizumab And"));
        StudySiteDTO leadTrialId = rXMLParser.getLeadOrganizationSiteIdentifierDTO();
        assertEquals("CDR0000069010", leadTrialId.getLocalStudyProtocolIdentifier().getValue());
        assertTrue(rXMLParser.getStudyIdentifierMap().containsValue("NCT00026221"));
        // test resp party
        assertNotNull(rXMLParser.getResponsiblePartyContact());
        assertNotNull(rXMLParser.getPrincipalInvestigatorDTO());
        assertNotNull(rXMLParser.getLeadOrganizationDTO());
        assertTrue(StringUtils.contains(EnOnConverter
            .convertEnOnToString(rXMLParser.getLeadOrganizationDTO().getName()), "Tom Baker Cancer Centre "));

    }

    @Test
    public void testNewXml() throws PAException {
        rXMLParser.setUrl(this.getClass().getResource("/CDR65658.xml"));
        rXMLParser.parse();
        assertEquals("Actual", CdConverter.convertCdToString(rXMLParser.getStudyProtocolDTO().getStartDateTypeCode()));
        assertTrue(StringUtils.contains(EnOnConverter
            .convertEnOnToString(rXMLParser.getLeadOrganizationDTO().getName()), "Southwest Oncology Group"));
        assertEquals("12/15/2007", TsConverter.convertToString(rXMLParser.getStudyOverallStatusDTO().getStatusDate()));
    }

    @Test
    public void testResponsiblePartyPhone() throws PAException {
        rXMLParser.setUrl(this.getClass().getResource("/CDR65658.xml"));
        rXMLParser.parse();
        List<String> phones = DSetConverter.convertDSetToList(rXMLParser.getResponsiblePartyContact()
            .getTelecomAddress(), DSetConverter.TYPE_PHONE);
        assertTrue(phones.contains("734-998-7130"));
        assertEquals(1, phones.size());
    }

    @Test
    public void testMissingPrimCompletion() throws PAException {
        rXMLParser.setUrl(this.getClass().getResource("/sample-missing-prim-completion-all.xml"));
        rXMLParser.parse();
        StudyProtocolDTO spDto = rXMLParser.getStudyProtocolDTO();
        assertEquals("01/01/2100", TsConverter.convertToString(spDto.getPrimaryCompletionDate()));
        assertEquals(ActualAnticipatedTypeCode.ANTICIPATED.getCode(), spDto.getPrimaryCompletionDateTypeCode()
            .getCode());
    }

    @Test
    public void testMissingPrimCompletionDate() throws PAException {
        rXMLParser.setUrl(this.getClass().getResource("/sample-missing-prim-completion-date.xml"));
        rXMLParser.parse();
        StudyProtocolDTO spDto = rXMLParser.getStudyProtocolDTO();
        assertEquals("01/01/2100", TsConverter.convertToString(spDto.getPrimaryCompletionDate()));
        assertEquals(ActualAnticipatedTypeCode.ANTICIPATED.getCode(), spDto.getPrimaryCompletionDateTypeCode()
            .getCode());
    }

    @Test
    public void testMissingPrimCompletionDateType() throws PAException {
        rXMLParser.setUrl(this.getClass().getResource("/sample-missing-prim-completion-date-type.xml"));
        rXMLParser.parse();
        StudyProtocolDTO spDto = rXMLParser.getStudyProtocolDTO();
        assertEquals("09/27/2030", TsConverter.convertToString(spDto.getPrimaryCompletionDate()));
        assertEquals(ActualAnticipatedTypeCode.ANTICIPATED.getCode(), spDto.getPrimaryCompletionDateTypeCode()
            .getCode());
    }

    @Test
    public void testMissingPrimCompletionInvalid() throws PAException {
        rXMLParser.setUrl(this.getClass().getResource("/sample-invalid-prim-completion.xml"));
        rXMLParser.parse();
        StudyProtocolDTO spDto = rXMLParser.getStudyProtocolDTO();
        assertEquals("01/01/2100", TsConverter.convertToString(spDto.getPrimaryCompletionDate()));
        assertEquals(ActualAnticipatedTypeCode.ANTICIPATED.getCode(), spDto.getPrimaryCompletionDateTypeCode()
            .getCode());
    }

    @Test
    public void testMissingPrimCompletionInvalidReverse() throws PAException {
        rXMLParser.setUrl(this.getClass().getResource("/sample-invalid-prim-completion-reverse.xml"));
        rXMLParser.parse();
        StudyProtocolDTO spDto = rXMLParser.getStudyProtocolDTO();
        assertEquals("01/01/2100", TsConverter.convertToString(spDto.getPrimaryCompletionDate()));
        assertEquals(ActualAnticipatedTypeCode.ANTICIPATED.getCode(), spDto.getPrimaryCompletionDateTypeCode()
            .getCode());
    }

    @Test(expected = PAException.class)
    public void testObservationalStudyType() throws PAException {
        rXMLParser.setUrl(this.getClass().getResource("/sample-pdq-input-observational.xml"));
        rXMLParser.parse();
    }

    @Test
    public void testStudyInd() throws PAException {
        when(identifierPersonSvc.search(any(IdentifiedPersonDTO.class)))
            .thenReturn(new ArrayList<IdentifiedPersonDTO>());
        rXMLParser.setUrl(this.getClass().getResource("/sample-with-ind.xml"));
        rXMLParser.parse();
        List<StudyIndldeDTO> indList = rXMLParser.getStudyIndldeDTOs();
        assertEquals(1, indList.size());
        StudyIndldeDTO indDTO = indList.get(0);
        assertEquals("CDER", indDTO.getGrantorCode().getCode());
    }
    
    @Test
    public void testWithIsStudyIndYes() throws PAException {
        when(identifierPersonSvc.search(any(IdentifiedPersonDTO.class)))
            .thenReturn(new ArrayList<IdentifiedPersonDTO>());
        rXMLParser.setUrl(this.getClass().getResource("/sample-with-ind.xml"));
        rXMLParser.parse();
        List<StudyIndldeDTO> indList = rXMLParser.getStudyIndldeDTOs();
        assertEquals(1, indList.size());
        StudyIndldeDTO indDTO = indList.get(0);
        assertEquals("CDER", indDTO.getGrantorCode().getCode());
        assertFalse(BlConverter.convertToBoolean(indDTO.getExemptIndicator()));
    }
    
    @Test
    public void testProperties() {
        rXMLParser.setStudyIndldeDTOs(new ArrayList<StudyIndldeDTO>());
        assertNotNull(rXMLParser.getStudyIndldeDTOs());

        rXMLParser.setLeadOrganizationDTO(new OrganizationDTO());
        assertNotNull(rXMLParser.getLeadOrganizationDTO());

        rXMLParser.setPrincipalInvestigatorDTO(new PersonDTO());
        assertNotNull(rXMLParser.getPrincipalInvestigatorDTO());

        rXMLParser.setSponsorOrganizationDTO(new OrganizationDTO());
        assertNotNull(rXMLParser.getSponsorOrganizationDTO());

        rXMLParser.setStudyIdentifierMap(new HashMap<String, String>());
        assertNotNull(rXMLParser.getStudyIdentifierMap());

        rXMLParser.setResponsiblePartyContact(new PersonDTO());
        assertNotNull(rXMLParser.getResponsiblePartyContact());

        rXMLParser.setRegAuthMap(new HashMap<String, String>());
        assertNotNull(rXMLParser.getRegAuthMap());

        rXMLParser.setStudyProtocolDTO(new StudyProtocolDTO());
        assertNotNull(rXMLParser.getStudyProtocolDTO());

        rXMLParser.setLeadOrganizationSiteIdentifierDTO(new StudySiteDTO());
        assertNotNull(rXMLParser.getLeadOrganizationSiteIdentifierDTO());
    }

    @Test
    public void testGetAlpha3CountryName() throws PAException {
        assertEquals("USA", rXMLParser.getAlpha3CountryName("U.S.A"));
    }

    @Test(expected = PAException.class)
    public void testGetAlpha3CountryNameNotFound() throws PAException {
        rXMLParser.getAlpha3CountryName("XXXX");
    }

    @Test
    public void testConvertToIvlPq() {
        assertNull(rXMLParser.convertToIvlPq(null, null, null, null));
    }

    @Test
    public void testTsFromString() {
        try {
            rXMLParser.tsFromString("yyyy-MM-dd", "123/33/22");
            fail("not a valid date");
        } catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }
}

/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The reg-web
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This reg-web Software License (the License) is between NCI and You. You (or
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
 * its rights in the reg-web Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the reg-web Software; (ii) distribute and
 * have distributed to and by third parties the reg-web Software and any
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
package gov.nih.nci.registry.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.AdditionalRegulatoryInfoDTO;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.iso.dto.StudyIndIdeStageDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.CommonsConstant;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.action.AbstractRegWebTest;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/**
 * @author mshestopalov
 *
 */
public class TrialConvertUtilsTest extends AbstractRegWebTest {
    TrialConvertUtils tCu = new TrialConvertUtils();

    @Test
    public void testAddSecondaryIdentifiers() {
        TrialDTO trialDto = getMockTrialDTO();
        List<Ii> secList = new ArrayList<Ii>();
        secList.add(IiConverter.convertToStudyProtocolIi(1L));
        secList.add(IiConverter.convertToStudyProtocolIi(2L));
        secList.add(IiConverter.convertToStudyProtocolIi(3L));
        List<Ii> secAddList = new ArrayList<Ii>();
        secAddList.add(IiConverter.convertToStudyProtocolIi(4L));
        secAddList.add(IiConverter.convertToStudyProtocolIi(5L));
        secAddList.add(IiConverter.convertToStudyProtocolIi(6L));
        Set<Ii> spSecList = new HashSet<Ii>();
        spSecList.add(IiConverter.convertToStudyProtocolIi(7L));

        trialDto.setSecondaryIdentifierList(secList);
        trialDto.setSecondaryIdentifierAddList(secAddList);

        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(spSecList));
        tCu.addSecondaryIdentifiers(spDTO, trialDto);
        assertEquals(7, trialDto.getSecondaryIdentifierList().size());
        assertNull(spDTO.getSecondaryIdentifiers().getItem());
    }

    @Test
    public void testconvertToCTEPStudySiteDTO() throws PAException, TooManyResultsException {
        TrialDTO trialDto = getMockTrialDTO();
        trialDto.setCtepIdentifier("CTEP12345");
        Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(1L);
        gov.nih.nci.pa.util.ServiceLocator paSvcLoc =
            mock(gov.nih.nci.pa.util.ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);
        String poOrgId = "1";
        OrganizationCorrelationServiceRemote orgCorrSvc =
            mock(OrganizationCorrelationServiceRemote.class);
        when(paSvcLoc.getOrganizationCorrelationService()).thenReturn(orgCorrSvc);
        Ii ctepRoIi = IiConverter.convertToPoResearchOrganizationIi("2");
        when(orgCorrSvc.getPOOrgIdentifierByIdentifierType(any(String.class))).thenReturn(poOrgId);
        when(orgCorrSvc.getPoResearchOrganizationByEntityIdentifier(any(Ii.class)))
            .thenReturn(ctepRoIi);
        StudySiteServiceLocal ssSvc = mock(StudySiteServiceLocal.class);
        when(paSvcLoc.getStudySiteService()).thenReturn(ssSvc);
        List<StudySiteDTO> ssList = new ArrayList<StudySiteDTO>();
        StudySiteDTO ssDto = new StudySiteDTO();
        ssDto.setIdentifier(IiConverter.convertToStudySiteIi(1L));
        ssList.add(ssDto);
        when(ssSvc.search(any(StudySiteDTO.class), any(LimitOffset.class))).thenReturn(ssList);
        StudySiteDTO rssDto = tCu.convertToCTEPStudySiteDTO(trialDto, studyProtocolIi);
        assertEquals("1", rssDto.getIdentifier().getExtension());
        assertEquals("CTEP12345", rssDto.getLocalStudyProtocolIdentifier().getValue());
        assertEquals("2", rssDto.getResearchOrganizationIi().getExtension());
    }

    @Test
    public void convertToTrialIndIdeDTO() {
        StudyIndIdeStageDTO dto = new StudyIndIdeStageDTO();
        dto.setIndldeTypeCode(CdConverter.convertToCd(IndldeTypeCode.IND));
        dto.setIndldeNumber(StConverter.convertToSt("1"));
        dto.setGrantorCode(CdConverter.convertToCd(GrantorCode.CDER));
        dto.setHolderTypeCode(CdConverter.convertToCd(HolderTypeCode.INVESTIGATOR));
        dto.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.TRUE));
        dto.setExpandedAccessStatusCode(CdConverter.convertToCd(ExpandedAccessStatusCode.AVAILABLE));
        dto.setExemptIndicator(BlConverter.convertToBl(Boolean.FALSE));

        TrialIndIdeDTO trialDTO = tCu.convertToTrialIndIdeDTO(dto);

        assertEquals(trialDTO.getIndIde(), "IND");
        assertEquals(trialDTO.getNumber(), "1");
        assertEquals(trialDTO.getGrantor(), "CDER");
        assertEquals(trialDTO.getHolderType(), "Investigator");
        assertEquals(trialDTO.getExpandedAccess(), "Yes");
        assertEquals(trialDTO.getExpandedAccessType(), "Available");
        assertEquals(trialDTO.getExemptIndicator(), CommonsConstant.NO);
    }
    
    @Test
    public void testConvertToAdditionalRegulatoryInfoDTO() throws Exception {
        TrialDTO trialDTO = new TrialDTO();
        trialDTO.setExportedFromUs("true");
        trialDTO.setFdaRegulatedDevice("true");
        trialDTO.setFdaRegulatedDrug("true");
        trialDTO.setPedPostmarketSurv("true");
        trialDTO.setPostPriorToApproval("true");
        trialDTO.setLastUpdatedDate("11-27-2016");
        trialDTO.setStudyProtocolId("12345");
        trialDTO.setMsId("987654321");
        
        AdditionalRegulatoryInfoDTO additionalRegulatoryInfoDTO = tCu.convertToAdditionalRegulatoryInfoDTO(trialDTO, "NCI-123");
        assertEquals("true", additionalRegulatoryInfoDTO.getExported_from_us());
        assertEquals("true", additionalRegulatoryInfoDTO.getFda_regulated_device());
        assertEquals("true", additionalRegulatoryInfoDTO.getFda_regulated_drug());
        assertEquals("true", additionalRegulatoryInfoDTO.getPed_postmarket_surv());
        assertEquals("true", additionalRegulatoryInfoDTO.getPost_prior_to_approval());
        assertEquals("11-27-2016", additionalRegulatoryInfoDTO.getDate_updated());
        assertEquals("12345", additionalRegulatoryInfoDTO.getStudy_protocol_id());
        assertEquals("NCI-123", additionalRegulatoryInfoDTO.getNci_id());
        assertEquals("987654321", additionalRegulatoryInfoDTO.getId());
        
        trialDTO.setExportedFromUs("No");
        trialDTO.setFdaRegulatedDevice("No");
        trialDTO.setFdaRegulatedDrug("No");
        trialDTO.setPedPostmarketSurv("No");
        trialDTO.setPostPriorToApproval("No");
        trialDTO.setLastUpdatedDate("11-28-2016");
        trialDTO.setStudyProtocolId("22345");
        trialDTO.setMsId("887654321");
        
        additionalRegulatoryInfoDTO = tCu.convertToAdditionalRegulatoryInfoDTO(trialDTO, "NCI-234");
        assertEquals("No", additionalRegulatoryInfoDTO.getExported_from_us());
        assertEquals("No", additionalRegulatoryInfoDTO.getFda_regulated_device());
        assertEquals("No", additionalRegulatoryInfoDTO.getFda_regulated_drug());
        assertEquals("No", additionalRegulatoryInfoDTO.getPed_postmarket_surv());
        assertEquals("No", additionalRegulatoryInfoDTO.getPost_prior_to_approval());
        assertEquals("11-28-2016", additionalRegulatoryInfoDTO.getDate_updated());
        assertEquals("22345", additionalRegulatoryInfoDTO.getStudy_protocol_id());
        assertEquals("NCI-234", additionalRegulatoryInfoDTO.getNci_id());
        assertEquals("887654321", additionalRegulatoryInfoDTO.getId());
    }

    @Test
    public void loadAdditionalRegulatoryInfoFromDtoTest() throws Exception {
        AdditionalRegulatoryInfoDTO additionalRegulatoryInfoDTO = new AdditionalRegulatoryInfoDTO();
        additionalRegulatoryInfoDTO.setExported_from_us("true");
        additionalRegulatoryInfoDTO.setFda_regulated_device("true");
        additionalRegulatoryInfoDTO.setFda_regulated_drug("true");
        additionalRegulatoryInfoDTO.setPed_postmarket_surv("true");
        additionalRegulatoryInfoDTO.setPost_prior_to_approval("true");
        additionalRegulatoryInfoDTO.setDate_updated("11-27-2016");
        additionalRegulatoryInfoDTO.setStudy_protocol_id("12345");
        additionalRegulatoryInfoDTO.setId("987654321");
        
        TrialDTO trialDTO = new TrialDTO();
        tCu.loadAdditionalRegulatoryInfoFromDto(trialDTO, additionalRegulatoryInfoDTO);
        
        assertEquals("true", trialDTO.getExportedFromUs());
        assertEquals("true", trialDTO.getFdaRegulatedDevice());
        assertEquals("true", trialDTO.getFdaRegulatedDrug());
        assertEquals("true", trialDTO.getPedPostmarketSurv());
        assertEquals("true", trialDTO.getPostPriorToApproval());
        assertEquals("11-27-2016", trialDTO.getLastUpdatedDate());
        assertEquals("987654321", trialDTO.getMsId());
        assertEquals(null, trialDTO.getStudyProtocolId());
        assertEquals(null, trialDTO.getNctIdentifier());
    }

    @Test
    public void loadAdditionalRegulatoryInfoFromDtoEmptyTest() throws Exception {
        AdditionalRegulatoryInfoDTO additionalRegulatoryInfoDTO = new AdditionalRegulatoryInfoDTO();
        
        TrialDTO trialDTO = new TrialDTO();
        tCu.loadAdditionalRegulatoryInfoFromDto(trialDTO, additionalRegulatoryInfoDTO);
        
        assertEquals(null, trialDTO.getExportedFromUs());
        assertEquals(null, trialDTO.getFdaRegulatedDevice());
        assertEquals(null, trialDTO.getFdaRegulatedDrug());
        assertEquals(null, trialDTO.getPedPostmarketSurv());
        assertEquals(null, trialDTO.getPostPriorToApproval());
        assertEquals(null, trialDTO.getLastUpdatedDate());
        assertEquals(null, trialDTO.getMsId());
        assertEquals(null, trialDTO.getStudyProtocolId());
        assertEquals(null, trialDTO.getNctIdentifier());
    }

}

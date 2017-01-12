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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author vrushali
 *
 */
public class PDQEligibilityCriteriaParserTest {
    private PDQAbstractionXMLParser abstractionElementParser;
    private PoServiceLocator poSvcLoc;
    private IdentifiedPersonCorrelationServiceRemote identifierPersonSvc;
    private IdentifiedOrganizationCorrelationServiceRemote identifierOrgSvc;
    private HealthCareFacilityCorrelationServiceRemote hcfSvc;
    private ServiceLocator paSvcLoc;
    private LookUpTableServiceRemote lookupSvc;

    @Before
    public void setup() throws PAException, NullifiedEntityException, TooManyResultsException {
        abstractionElementParser = new PDQAbstractionXMLParser();
        setupPoSvc();
        paSvcLoc = mock (ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);
        lookupSvc = mock (LookUpTableServiceRemote.class);
        when(paSvcLoc.getLookUpTableService()).thenReturn(lookupSvc);
        Country country = new Country();
        country.setAlpha3("USA");
        when(lookupSvc.getCountryByName(anyString())).thenReturn(country);
        PAServiceUtils paServiceUtil = mock (PAServiceUtils.class);
        abstractionElementParser.setPaServiceUtils(paServiceUtil);
        when(paServiceUtil.getOrganizationByCtepId(anyString())).thenReturn(new OrganizationDTO());
    }
    private void setupPoSvc() throws NullifiedEntityException, PAException, TooManyResultsException {
        poSvcLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poSvcLoc);
        identifierPersonSvc = mock(IdentifiedPersonCorrelationServiceRemote.class);
        identifierOrgSvc = mock(IdentifiedOrganizationCorrelationServiceRemote.class);
        when(poSvcLoc.getIdentifiedPersonEntityService()).thenReturn(identifierPersonSvc);
        when(poSvcLoc.getIdentifiedOrganizationEntityService()).thenReturn(identifierOrgSvc);
        IdentifiedPersonDTO idPersonDTO = new IdentifiedPersonDTO();
        idPersonDTO.setPlayerIdentifier(IiConverter.convertToPoPersonIi("1"));
        List<IdentifiedPersonDTO> idPerDtos = new ArrayList<IdentifiedPersonDTO>();
        idPerDtos.add(idPersonDTO);
        when(identifierPersonSvc.search(any(IdentifiedPersonDTO.class))).thenReturn(idPerDtos);
        List<IdentifiedOrganizationDTO> orgList = new ArrayList<IdentifiedOrganizationDTO>();
        IdentifiedOrganizationDTO idOrgDTO = new IdentifiedOrganizationDTO();
        idOrgDTO.setScoperIdentifier(IiConverter.convertToPoOrganizationIi("2"));
        when(identifierOrgSvc.search(any(IdentifiedOrganizationDTO.class))).thenReturn(orgList);
        hcfSvc = mock(HealthCareFacilityCorrelationServiceRemote.class);
        when(poSvcLoc.getHealthCareFacilityCorrelationService()).thenReturn(hcfSvc);
        List<HealthCareFacilityDTO> hcfDtos = new ArrayList<HealthCareFacilityDTO>();
        HealthCareFacilityDTO hcfDto = new HealthCareFacilityDTO();
        DSet<Ii> dset = new DSet<Ii>();
        dset.setItem(new HashSet<Ii>());
        dset.getItem().add(IiConverter.convertToPoHealthCareFacilityIi("2"));
        hcfDto.setIdentifier(dset);
        hcfDtos.add(hcfDto);
        when(hcfSvc.search(any(HealthCareFacilityDTO.class))).thenReturn(hcfDtos);
    }

    @Test
    public void testEligiblityCriteria() throws PAException {
        PDQEligibilityCriteriaParser eligibilityCriteria = new PDQEligibilityCriteriaParser();
        abstractionElementParser.setUrl(this.getClass().getResource("/sample-with-location.xml"));
        abstractionElementParser.parse();
        String[] otherCriterionTextBlock  = eligibilityCriteria.readEligibilityEntryCriteria(
                    abstractionElementParser.getDocument().getRootElement().getChild("eligibility"));
        assertNull(otherCriterionTextBlock);

        abstractionElementParser.setUrl(this.getClass().getResource("/CDR64184.xml"));
        abstractionElementParser.parse();
        otherCriterionTextBlock = eligibilityCriteria.readEligibilityEntryCriteria(
                abstractionElementParser.getDocument().getRootElement().getChild("eligibility"));
        assertNotNull(otherCriterionTextBlock);
        assertTrue(otherCriterionTextBlock[0].startsWith("Histologically"));
        assertTrue(otherCriterionTextBlock[0].contains("**   Any number of bone metastases by bone scan allowed"));
        assertTrue(otherCriterionTextBlock[1].contains("*  No more than 1 month from the beginning of antiandrogen therapy to the beginning of luteinizing hormone-releasing hormone (LHRH) agonist therapy"));
        assertTrue(otherCriterionTextBlock[4].startsWith("Performance status - SWOG 0-2"));
        assertFalse(otherCriterionTextBlock[5].contains("Should not show up"));
        assertFalse(otherCriterionTextBlock[5].contains("Hematopoietic"));
        assertTrue(otherCriterionTextBlock[5].startsWith("Recovered from any major infection"));
        assertTrue(otherCriterionTextBlock[11].contains("* Single or combination therapy allowed"));
    }
}

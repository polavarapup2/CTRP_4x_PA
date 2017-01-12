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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.StudySiteContact;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudySiteContactConverter;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.MockPoServiceLocator;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.TestSchema;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test service and converter.
 * @author hreinhart
 */
public class StudySiteContactServiceTest extends AbstractHibernateTestCase {
    private final StudySiteContactServiceLocal remoteEjb = new StudySiteContactBeanLocal();
    Long protocolId;
    Ii protocolIi;
    Long siteId;
    Ii siteIi;
    Long contactId;
    Ii contactIi;
    Long healthCareProviderId;
    Ii healthCareProviderIi;
    Long orgContactId;
    Ii orgContactIi;
    Long crsId;
    Ii crsIi;

    @Before
    public void setUp() throws Exception {
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();
        protocolId = TestSchema.studyProtocolIds.get(0);
        protocolIi = IiConverter.convertToStudyProtocolIi(protocolId);
        siteId = TestSchema.studySiteIds.get(0);
        siteIi = IiConverter.convertToIi(siteId);
        contactId = TestSchema.studySiteContactIds.get(0);
        contactIi = IiConverter.convertToIi(contactId);
        healthCareProviderId = TestSchema.healthCareFacilityIds.get(0);
        healthCareProviderIi = IiConverter.convertToIi(healthCareProviderId);
        orgContactId = TestSchema.organizationalContactIds.get(0);
        orgContactIi = IiConverter.convertToIi(orgContactId);
        crsId = TestSchema.clinicalResearchStaffIds.get(0);
        crsIi = IiConverter.convertToIi(crsId);
    }

    @Test
    public void get() throws Exception {
        StudySiteContactDTO spcDto = remoteEjb.get(contactIi);
        StudySiteContact spcBo = Converters.get(StudySiteContactConverter.class).convertFromDtoToDomain(spcDto);
        assertEquals(siteId, spcBo.getStudySite().getId());
        assertEquals(protocolId, spcBo.getStudyProtocol().getId());
    }

    @Test
    public void create() throws Exception {
        StudySiteContactDTO dto = createStudySiteContactDTO(null, null, null);
        StudySiteContactDTO result = remoteEjb.create(dto);
        assertFalse(ISOUtil.isIiNull(result.getIdentifier()));
    }

    /**
     * Tests that the creation method with a valid normal phone number for organizational contact
     */
    @Test
    public void createWithValidPhoneNumberOrg() throws Exception {
        createWithValidPhoneNumber(true, "1112223333", "111-222-3333");
    }

    /**
     * Tests that the creation method with a valid 800 phone number for organizational contact
     */
    @Test
    public void createWithValid800PhoneNumberOrg() throws Exception {
        createWithValidPhoneNumber(true, "1-8001112222", "800-111-2222");
    }

    /**
     * Tests that the creation method with a valid normal phone number for clinical research staff
     */
    @Test
    public void createWithValidPhoneNumberCrs() throws Exception {
        createWithValidPhoneNumber(false, "1112223333", "111-222-3333");
    }

    /**
     * Tests that the creation method with a valid 800 phone number for clinical research staff
     */
    @Test
    public void createWithValid800PhoneNumberCrs() throws Exception {
        createWithValidPhoneNumber(false, "1-8001112222", "800-111-2222");
    }

    /**
     * Tests that the creation method with a valid phone number
     */
    private void createWithValidPhoneNumber(boolean org, String input, String formatted) throws Exception {
        StudySiteContactDTO dto = (org) ? createStudySiteContactDTO(orgContactIi, null, input)
                : createStudySiteContactDTO(null, crsIi, input);
        StudySiteContactDTO inserted = remoteEjb.create(dto);
        StudySiteContactDTO result = remoteEjb.get(inserted.getIdentifier());
        List<String> phones = DSetConverter.convertDSetToList(result.getTelecomAddresses(), "PHONE");
        assertEquals("Wrong number of phone numbers", 1, phones.size());
        assertEquals("Wrong phone number", formatted, phones.get(0));
    }

    /**
     * Tests that the creation method with a invalid phone number for organizational contact
     */
    @Test
    public void createWithInvalidPhoneNumberOrg() throws Exception {
        try {
            StudySiteContactDTO dto = createStudySiteContactDTO(orgContactIi, null, "1234");
            remoteEjb.create(dto);
            fail("Create method should have failed because of the invalid phone number");
        } catch (PAException e) {
            assertEquals("Wrong error message",
                         "Invalid phone number: 1234 format for USA or CANADA is xxx-xxx-xxxxextxxxx", e.getMessage());
        }
    }

    /**
     * Tests that the creation method with a invalid phone number for clinical research staff
     */
    @Test
    public void createWithInvalidPhoneNumberCrs() throws Exception {
        try {
            StudySiteContactDTO dto = createStudySiteContactDTO(null, crsIi, "1234");
            remoteEjb.create(dto);
            fail("Create method should have failed because of the invalid phone number");
        } catch (PAException e) {
            assertEquals("Wrong error message",
                         "Invalid phone number: 1234 format for USA or CANADA is xxx-xxx-xxxxextxxxx", e.getMessage());
        }
    }

    private StudySiteContactDTO createStudySiteContactDTO(Ii orgContactIi, Ii crsIi, String phone) {
        StudySiteContactDTO dto = new StudySiteContactDTO();
        dto.setIdentifier(IiConverter.convertToIi((Long) null));
        dto.setPostalAddress(AddressConverterUtil.create("1", "2", "3", "4", "5", "ZZZ"));
        dto.setPrimaryIndicator(BlConverter.convertToBl(true));
        dto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.COORDINATING_INVESTIGATOR));
        dto.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.ACTIVE));
        dto.setStatusDateRange(IvlConverter.convertTs().convertToIvl(PAUtil.dateStringToTimestamp("1/1/2005"), null));
        dto.setStudySiteIi(siteIi);
        dto.setStudyProtocolIdentifier(protocolIi);
        dto.setHealthCareProviderIi(healthCareProviderIi);
        dto.setOrganizationalContactIi(orgContactIi);
        dto.setClinicalResearchStaffIi(crsIi);
        if (phone != null) {
            List<String> phones = new ArrayList<String>();
            phones.add(phone);
            DSet<Tel> dSet = new DSet<Tel>();
            dSet = DSetConverter.convertListToDSet(phones, "PHONE", dSet);
            dto.setTelecomAddresses(dSet);
        }
        return dto;
    }

    @Test
    public void delete() throws Exception {
        remoteEjb.delete(contactIi);
        try {
            StudySiteContactDTO spc = remoteEjb.get(contactIi);
            assertNull(spc);
        } catch (PAException e) {
            // expected behavior
        }
    }

    @Test
    public void iiRootTest() throws Exception {
        StudySiteContactDTO dto = remoteEjb.get(contactIi);
        assertEquals(dto.getStudyProtocolIdentifier().getRoot(), IiConverter.STUDY_PROTOCOL_ROOT);
    }
}

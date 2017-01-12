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

import static gov.nih.nci.pa.enums.StudyContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_INVESTIGATOR;
import static gov.nih.nci.pa.enums.StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.MockPoServiceLocator;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.TestSchema;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudyContactServiceBeanTest extends AbstractHibernateTestCase {
    private final StudyContactServiceLocal remoteEjb = new StudyContactBeanLocal();
    Ii pid;
    Ii clinicalResearchStaffId;

    @Before
    public void setUp() throws Exception {
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();
        pid = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
        clinicalResearchStaffId = IiConverter.convertToIi(TestSchema.clinicalResearchStaffIds.get(0));
    }

    @Test
    public void get() throws Exception {
        List<StudyContactDTO> statusList = remoteEjb.getByStudyProtocol(pid);
        assertEquals(1, statusList.size());
        List<StudyContactDTO> spList2 = remoteEjb.getByStudyProtocol(pid, statusList.get(0));
        assertEquals(IiConverter.convertToLong(statusList.get(0).getIdentifier()), IiConverter.convertToLong(spList2
            .get(0).getIdentifier()));
        List<StudyContactDTO> spList3 = remoteEjb.getByStudyProtocol(pid, statusList);
        assertEquals(IiConverter.convertToLong(statusList.get(0).getIdentifier()), IiConverter.convertToLong(spList3
            .get(0).getIdentifier()));
        remoteEjb.delete(statusList.get(0).getIdentifier());
    }

    @Test
    public void create() throws Exception {
        StudyContactDTO dto = createStudyContactDTO(null, STUDY_PRINCIPAL_INVESTIGATOR);
        remoteEjb.create(dto);
        assertEquals(dto.getStudyProtocolIdentifier(), pid);
        searchStudyContact();
    }

    @Test
    public void iiRootTest() throws Exception {
        List<StudyContactDTO> statusList = remoteEjb.getByStudyProtocol(pid);
        assertTrue(statusList.size() > 0);
        StudyContactDTO dto = statusList.get(0);
        assertEquals(dto.getStudyProtocolIdentifier().getRoot(), IiConverter.STUDY_PROTOCOL_ROOT);
    }

    /**
     * Tests that the creation method with a valid normal phone number
     */
    @Test
    public void createWithValidPhoneNumber() throws Exception {
        createWithValidPhoneNumber("1112223333", "111-222-3333");
    }

    /**
     * Tests that the creation method with a valid 800 phone number
     */
    @Test
    public void createWithValid800PhoneNumber() throws Exception {
        createWithValidPhoneNumber("1-8001112222", "800-111-2222");
    }

    /**
     * Tests that the creation method with a valid phone number
     */
    private void createWithValidPhoneNumber(String input, String formatted) throws Exception {
        StudyContactDTO dto = createStudyContactDTO(input, STUDY_PRINCIPAL_INVESTIGATOR);
        remoteEjb.create(dto);
        assertEquals(dto.getStudyProtocolIdentifier(), pid);
        StudyContactDTO result = searchStudyContact();
        List<String> phones = DSetConverter.convertDSetToList(result.getTelecomAddresses(), "PHONE");
        assertEquals("Wrong number of phone numbers", 1, phones.size());
        assertEquals("Wrong phone number", formatted, phones.get(0));
    }

    /**
     * Tests that the creation method with a invalid phone number
     */
    @Test
    public void createWithInvalidPhoneNumber() throws Exception {
        try {
            StudyContactDTO dto = createStudyContactDTO("1234", STUDY_PRINCIPAL_INVESTIGATOR);
            remoteEjb.create(dto);
            fail("Create method should have failed because of the invalid phone number");
        } catch (PAException e) {
            assertEquals("Wrong error message",
                         "Invalid phone number: 1234 format for USA or CANADA is xxx-xxx-xxxxextxxxx", e.getMessage());
        }
    }
    
    @Test
    public void getResponsiblePartyContact() throws Exception {
        StudyContactDTO dto = createStudyContactDTO(null, RESPONSIBLE_PARTY_SPONSOR_INVESTIGATOR);
        remoteEjb.create(dto);
        assertEquals(dto.getStudyProtocolIdentifier(), pid);
        
        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();
        
        StudyContactDTO rp = remoteEjb.getResponsiblePartyContact(pid);
        assertEquals(
                RESPONSIBLE_PARTY_SPONSOR_INVESTIGATOR,
                CdConverter.convertCdToEnum(StudyContactRoleCode.class,
                        rp.getRoleCode()));
    }
    
    @Test
    public void removeResponsiblePartyContact() throws Exception {
        StudyContactDTO dto = createStudyContactDTO(null, RESPONSIBLE_PARTY_SPONSOR_INVESTIGATOR);
        remoteEjb.create(dto);
        assertEquals(dto.getStudyProtocolIdentifier(), pid);
        
        StudyContactDTO rp = remoteEjb.getResponsiblePartyContact(pid);
        assertEquals(
                RESPONSIBLE_PARTY_SPONSOR_INVESTIGATOR,
                CdConverter.convertCdToEnum(StudyContactRoleCode.class,
                        rp.getRoleCode()));
        
        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();
        
        remoteEjb.removeResponsiblePartyContact(pid);
        assertNull(remoteEjb.getResponsiblePartyContact(pid));
    }

    private StudyContactDTO createStudyContactDTO(String phone, StudyContactRoleCode role) {
        StudyContactDTO dto = new StudyContactDTO();
        dto.setPrimaryIndicator(BlConverter.convertToBl(Boolean.TRUE));
        dto.setStudyProtocolIdentifier(pid);
        dto.setRoleCode(CdConverter.convertToCd(role));
        dto.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.ACTIVE));
        dto.setClinicalResearchStaffIi(clinicalResearchStaffId);
        if (phone != null) {
            List<String> phones = new ArrayList<String>();
            phones.add(phone);
            DSet<Tel> dSet = new DSet<Tel>();
            dSet = DSetConverter.convertListToDSet(phones, "PHONE", dSet);
            dto.setTelecomAddresses(dSet);
        }
        return dto;
    }

    private StudyContactDTO searchStudyContact() throws Exception {
        LimitOffset pagingParams = new LimitOffset(1, 1);
        StudyContactDTO dto2 = new StudyContactDTO();
        dto2.setStudyProtocolIdentifier(pid);
        List<StudyContactDTO> list = remoteEjb.search(dto2, pagingParams);
        assertEquals(1, list.size());
        return list.get(0);
    }
}

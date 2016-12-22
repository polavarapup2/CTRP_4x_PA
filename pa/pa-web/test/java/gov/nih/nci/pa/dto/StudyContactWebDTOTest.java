/**
 * 
 */
package gov.nih.nci.pa.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.convert.OrganizationalContactConverter;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.PABaseCorrelation;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

import org.junit.Before;
import org.junit.Test;

/**
 * @author vinodh
 *
 */
public class StudyContactWebDTOTest {
    
    private PAServiceUtils paSvcutils;
    
    private PABaseCorrelation<PAOrganizationalContactDTO , 
    OrganizationalContactDTO , OrganizationalContact ,
    OrganizationalContactConverter> paBaseCorrltn;
    
    @SuppressWarnings("unchecked")
    @Before
    public void before() throws PAException {
        paSvcutils  = mock(PAServiceUtils.class);
        paBaseCorrltn  = mock(PABaseCorrelation.class);
        OrganizationalContact oc = new OrganizationalContact();
        oc.setId(1L);
        oc.setIdentifier("1");
        Organization org = new Organization();
        org.setId(1L);
        org.setIdentifier("1");
        org.setName("Org Name");
        Person p = new Person();
        p.setId(1L);
        p.setIdentifier("1");
        p.setFirstName("FN");
        p.setLastName("LN");
        oc.setOrganization(org);
        oc.setPerson(p);
        when(paSvcutils.getOrganizationalContact(any(String.class))).thenReturn(oc);
        when(paBaseCorrltn.create(any(PAOrganizationalContactDTO.class))).thenReturn(1L);
    }
    
    @Test
    public void checkEquals() {
        StudyContactWebDTO scw1 = createSCWebDto(1);
        StudyContactWebDTO scw1Dup = createSCWebDto(1);
        StudyContactWebDTO scw2 = createSCWebDto(2);
        
        assertTrue(scw1.equals(scw1Dup));
        assertFalse(scw1.equals(scw2));
    }
    
    @Test
    public void convertBetweenSCDtoAndSCWebDto() throws PAException {
        StudyContactWebDTO scw1 = createSCWebDto(1);
        scw1.setPaServiceUtils(paSvcutils);
        scw1.setPaBaseCorrltn(paBaseCorrltn);
        scw1.setRoleCode(StudyContactRoleCode.DESIGNEE_CONTACT.getCode());
        StudyContactDTO sc1 = scw1.convertToStudyContactDto(null);
        
        assertNotNull(sc1);
        assertEquals(scw1.getId(), IiConverter.convertToLong(sc1.getIdentifier()));
        assertNull(scw1.getOrgContactId());
        assertEquals(new Long(1L), IiConverter.convertToLong(sc1.getOrganizationalContactIi()));
        assertEquals(scw1.getComments(), StConverter.convertToString(sc1.getComments()));
        assertEquals(scw1.getRoleCode(), CdConverter.convertCdToString(sc1.getRoleCode()));
        assertEquals(scw1.getPrsUserName(), StConverter.convertToString(sc1.getPrsUserName()));
        assertEquals(scw1.getEmail(), DSetConverter.convertDSetToList(
                sc1.getTelecomAddresses(), DSetConverter.TYPE_EMAIL).get(0));
        assertEquals(scw1.getPhoneWithExt(), DSetConverter.convertDSetToList(
                sc1.getTelecomAddresses(), DSetConverter.TYPE_PHONE).get(0));
        
        StudyContactWebDTO scw2 = new StudyContactWebDTO();
        scw2.setPaServiceUtils(paSvcutils);
        scw2.setPaBaseCorrltn(paBaseCorrltn);
        scw2.populateFrom(sc1);
        
        assertNotNull(scw2);
        assertEquals(IiConverter.convertToLong(sc1.getIdentifier()), scw2.getId());
        assertEquals(IiConverter.convertToString(sc1.getOrganizationalContactIi()), scw2.getOrgContactId());
        assertEquals(StConverter.convertToString(sc1.getComments()), scw2.getComments());
        assertEquals(CdConverter.convertCdToString(sc1.getRoleCode()), scw2.getRoleCode());
        assertEquals(StConverter.convertToString(sc1.getPrsUserName()), scw2.getPrsUserName());
        assertEquals(DSetConverter.convertDSetToList(
                sc1.getTelecomAddresses(), DSetConverter.TYPE_EMAIL).get(0),
                scw2.getEmail());
        assertEquals(DSetConverter.convertDSetToList(
                sc1.getTelecomAddresses(), DSetConverter.TYPE_PHONE).get(0),
                scw2.getPhoneWithExt());
    }

    /**
     * @return StudyContactWebDTO
     */
    private StudyContactWebDTO createSCWebDto(long index) {
        StudyContactWebDTO scWDto = new StudyContactWebDTO();
        scWDto.setSelPoOrgId("" + index);
        scWDto.setSelPoPrsnId("" + index);
        scWDto.setEditedPoOrgId("" + index);
        scWDto.setEditedPoPrsnId("" + index);
        scWDto.setPrsUserName("prsUserName" +  index);
        scWDto.setEmail("someone@some.com");
        scWDto.setPhone("703-111-1111");
        scWDto.setExt("123");
        scWDto.setComments("comments");
        scWDto.setStudyProtocolId(index);
        return scWDto;
    }
    
    
}

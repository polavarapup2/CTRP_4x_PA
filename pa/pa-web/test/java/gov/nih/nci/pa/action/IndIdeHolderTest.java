/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class IndIdeHolderTest {

	
	IndIdeHolder indIdeHolder;
	StudyIndldeDTO iso;
	
	@Before 
	public void setUp() throws PAException {
	  iso = new StudyIndldeDTO();
	  iso.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.FALSE));
	  iso.setExpandedAccessStatusCode(CdConverter.convertStringToCd("Active"));
	  iso.setGrantorCode(CdConverter.convertStringToCd("CIP"));
	  iso.setHolderTypeCode(CdConverter.convertStringToCd("NCI"));
	  iso.setIndldeNumber(StConverter.convertToSt("1234"));
	  iso.setIndldeTypeCode(CdConverter.convertStringToCd("IND"));
	  iso.setNciDivProgHolderCode(CdConverter.convertStringToCd("CIP"));
	  iso.setNihInstHolderCode(CdConverter.convertStringToCd(""));
	  iso.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
	  iso.setIdentifier(IiConverter.convertToIi("1"));
	  indIdeHolder = new IndIdeHolder(iso);
	 
	}
	/**
	 * Test method for {@link gov.nih.nci.pa.action.IndIdeHolder#IndIdeHolder(gov.nih.nci.pa.iso.dto.StudyIndldeDTO)}.
	 */
	@Test
	public void testIndIdeHolderStudyIndldeDTO() {
		 assertNotNull(indIdeHolder);
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.IndIdeHolder#getGroup3()}.
	 */
	@Test
	public void testGetGroup3() {
		assertNotNull(indIdeHolder.getGroup3());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.IndIdeHolder#getIndidenumber()}.
	 */
	@Test
	public void testGetIndidenumber() {
		assertNotNull(indIdeHolder.getIndidenumber());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.IndIdeHolder#getSubCat()}.
	 */
	@Test
	public void testGetSubCat() {
		assertNotNull(indIdeHolder.getSubCat());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.IndIdeHolder#getHolderType()}.
	 */
	@Test
	public void testGetHolderType() {
		assertNotNull(indIdeHolder.getHolderType());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.IndIdeHolder#getGroup4()}.
	 */
	@Test
	public void testGetGroup4() {
		assertNotNull(indIdeHolder.getGroup4());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.IndIdeHolder#getExpandedStatus()}.
	 */
	@Test
	public void testGetExpandedStatus() {
		assertNotNull(indIdeHolder.getExpandedStatus());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.IndIdeHolder#getProgramcodenihselectedvalue()}.
	 */
	@Test
	public void testGetProgramcodenihselectedvalue() {
		assertEquals(indIdeHolder.getProgramcodenihselectedvalue(),"");
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.IndIdeHolder#getProgramcodenciselectedvalue()}.
	 */
	@Test
	public void testGetProgramcodenciselectedvalue() {
		assertNotNull(indIdeHolder.getProgramcodenciselectedvalue());
	}

	/**
	 * Test method for {@link gov.nih.nci.pa.action.IndIdeHolder#getId()}.
	 */
	@Test
	public void testGetId() {
		assertNotNull(indIdeHolder.getId());
	}

}

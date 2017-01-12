package gov.nih.nci.pa.service;


import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.iso.dto.StudyRelationshipDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudyRelationshipServiceBeanTest extends AbstractHibernateTestCase {

    private StudyRelationshipServiceLocal localEjb = new StudyRelationshipBeanLocal();;
    private StudyRelationshipDTO srDto = new StudyRelationshipDTO();

    @Before
    public void setUp() throws Exception {
      TestSchema.primeData();
      srDto.setSourceStudyProtocolIdentifier(IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)));

      }

    @Test
    public void testSearchStudyRelationshipDTOLimitOffset() throws PAException,TooManyResultsException{
        LimitOffset limitOffset = new LimitOffset(1, 1);
        List<StudyRelationshipDTO> listDTO = localEjb.search(srDto, limitOffset);
        assertEquals(0,listDTO.size());
    }

}

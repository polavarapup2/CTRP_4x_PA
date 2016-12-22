package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.util.StudyAlternateTitleComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * @author ADas
 */
public class StudyAlternateTitleComparatorTest {
    
    @Test
    public void testStudyAlternateTitleComparator() {
        List<StudyAlternateTitle> result = new ArrayList<StudyAlternateTitle>(); 
        
        StudyAlternateTitle obj1 = new StudyAlternateTitle();
        obj1.setAlternateTitle("Test3");
        obj1.setId(1L);
        obj1.setCategory("Other");
        result.add(obj1);
        
        StudyAlternateTitle obj2 = new StudyAlternateTitle();
        obj2.setAlternateTitle("Test1");
        obj2.setId(2L);
        obj2.setCategory("Spelling/Formatting Correction");
        result.add(obj2);
        
        StudyAlternateTitle obj3 = new StudyAlternateTitle();
        obj3.setAlternateTitle("Test2");
        obj3.setId(3L);
        obj3.setCategory("Other");
        result.add(obj3);
        
        Collections.sort(result, new StudyAlternateTitleComparator());
        //Should be sorted by alternate title
        assertEquals(result.get(0).getAlternateTitle(), "Test1");
        assertEquals(result.get(1).getAlternateTitle(), "Test2");
        assertEquals(result.get(2).getAlternateTitle(), "Test3");
    }
}

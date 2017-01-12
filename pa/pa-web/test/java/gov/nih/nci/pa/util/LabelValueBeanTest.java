/**
 *
 */
package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class LabelValueBeanTest {

    LabelValueBean labelValue;
    @Before
    public void setUp() {
        labelValue = new LabelValueBean();
        labelValue.setId(1L);
        labelValue.setName("test");
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.LabelValueBean#getId()}.
     */
    @Test
    public void testGetId() {
        assertEquals(labelValue.getId().longValue(),Long.parseLong("1"));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.LabelValueBean#getName()}.
     */
    @Test
    public void testGetName() {
        assertEquals(labelValue.getName(),"test");
    }

    /**
     * Test method for {@link gov.nih.nci.pa.util.LabelValueBean#compareTo(gov.nih.nci.pa.util.LabelValueBean)}.
     */
    @Test
    public void testCompareTo() {
        LabelValueBean bean = new LabelValueBean();
        bean.setName("test");
        LabelValueBean bean1 = new LabelValueBean();
        bean1.setName("rest");
        assertEquals(0,labelValue.compareTo(bean));
        assertEquals(2,labelValue.compareTo(bean1));
    }

}

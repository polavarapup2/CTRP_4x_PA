/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class RemoteApiUtilTest {
    @Test
    public void testConvertToBl() {
        RemoteApiUtil.convertToBl(Boolean.FALSE);
        RemoteApiUtil.convertToBl(null);
    }
    @Test
    public void testConvertToDSetTel() {
        List<String> email = new ArrayList<String>();
        email.add("e@mail.co");
        List<String> fax = new ArrayList<String>();
        fax.add("fax");
        List<String> phone = new ArrayList<String>();
        phone.add("phone");
        List<String> url = new ArrayList<String>();
        url.add("http://www.test.co");
        List<String> text = new ArrayList<String>();
        text.add("text");
        RemoteApiUtil.convertToDSetTel(email, fax, phone, url, text);
        try {
            email = new ArrayList<String>();
            email.add("e@mail.co");
            fax = new ArrayList<String>();
            fax.add("fax");
            phone = new ArrayList<String>();
            phone.add("phone");
            url = new ArrayList<String>();
            url.add("test.co");
            text = new ArrayList<String>();
            text.add("text");
            RemoteApiUtil.convertToDSetTel(email, fax, phone, url, text);
            fail();
        } catch (Exception e) {
            
        }
        
    }
}

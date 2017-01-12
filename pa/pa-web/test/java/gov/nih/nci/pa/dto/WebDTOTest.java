package gov.nih.nci.pa.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test the getterr/setter behavior of web dtos.
 * 
 * @author Steve Lustbader
 */
@RunWith(Parameterized.class)
public class WebDTOTest {

    private static final Map<String, Comparable<?>> DEFAULT_ARGUMENTS = createDefaultArguments();
    private static final Set<String> PRIMITIVE_TYPES = createPrimitiveTypes();

    private Object target;

    /**
     * Gets the parameterized test data.
     * 
     * @return The parameters of the test
     */
    @Parameters
    public static Collection<? extends Object> data() {
        Object[][] data = new Object[][]{
                {new AnatomicSiteWebDTO() },
                {new BaseISDesignDetailsWebDTO()},
                {new CaDSRWebDTO()},
                {new ContactWebDTO() },
                {new DiseaseWebDTO() },
                {new GeneralTrialDesignWebDTO()},
                {new InterventionalStudyProtocolWebDTO() }, 
                {new InterventionWebDTO() },
                {new ISDesignDetailsWebDTO()},
                {new LookUpWebDTO()},
                //{new MilestoneTrialHistoryWebDTO()},
                //{new MilestoneWebDTO()},
                {new NCISpecificInformationWebDTO() },
                //{new OnholdWebDTO() },
                {new OSDesignDetailsWebDTO() }, 
                {new OutcomeMeasureWebDTO() }, 
                {new ParticipatingOrganizationsTabWebDTO() }, 
                {new PlannedMarkerWebDTO() }, 
                {new RegulatoryAuthorityWebDTO() }, 
                {new StudyIndldeWebDTO() },
                {new StudyOverallStatusWebDTO() },
                {new StudyParticipationWebDTO() }, 
                {new StudyProtocolWebDTO() }, 
                {new StudyResourcingWebDTO() },
                {new StudySiteAccrualAccessWebDTO() },
                {new SubGroupsWebDTO() }, 
                {new TrialArmsWebDTO() },
                {new TrialDocumentWebDTO() }, 
                {new TrialFundingWebDTO() },
                //{new TrialHistoryWebDTO() },
                {new TrialOwner() },
                {new TrialUpdateWebDTO() }
        };
        return Arrays.asList(data);
    }

    /**
     * creates default types and arguments.
     */
    private static Map<String, Comparable<?>> createDefaultArguments() {
        Map<String, Comparable<?>> args = new HashMap<String, Comparable<?>>();
        args.put("java.lang.String", "test");
        args.put("java.lang.Long", 1L);
        args.put("java.lang.Boolean", true);
        args.put("boolean", true);
        args.put("double", 0.0);
        return args;
    }

    private static Set<String> createPrimitiveTypes() {
        Set<String> types = new HashSet<String>();
        types.add("boolean");
        types.add("double");
        return types;
    }

    /**
     * Constructor.
     * @param target The object to test
     */
    public WebDTOTest(Object target) {
        this.target = target;
    }

    /**
     * The method introspects the target object, finding read/write properties, and tests the getter and setter.
     */
    @Test
    public void testGetterSetterBehavior() {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : descriptors) {
                if (descriptor.getWriteMethod() == null) {
                    continue;
                }
                assertGetterSetterBehavior(target, descriptor.getDisplayName(), null);
            }
        } catch (IntrospectionException e) {
            fail("Failed while introspecting target " + target.getClass());
        }
    }

    /**
     * The method accepts an explicit argument for the setter method.
     * 
     * @param target the object on which to invoke the getter and setter
     * @param property the property name, e.g. "firstName"
     * @param argument the property value, i.e. the value the setter will be invoked with
     */
    public void assertGetterSetterBehavior(Object target, String property, Object argument) {
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(property, target.getClass());
            Method writeMethod = descriptor.getWriteMethod();
            Method readMethod = descriptor.getReadMethod();
            Class<?> type = descriptor.getPropertyType();
            Object arg = argument;
            if (arg == null) {
                if (DEFAULT_ARGUMENTS.containsKey(type.getName())) {
                    arg = DEFAULT_ARGUMENTS.get(type.getName());
                }
            }
            writeMethod.invoke(target, arg);
            Object propertyValue = readMethod.invoke(target);
            if (PRIMITIVE_TYPES.contains(type.getName())) {
                assertEquals(property + " getter/setter test passed", arg, propertyValue);
            } else {
                assertSame(property + " getter/setter test passed", arg, propertyValue);
            }

        } catch (IntrospectionException e) {
            String msg = "Error creating PropertyDescriptor for property [" + property
                    + "]. Do you have a getter and a setter?";

            fail(msg);
        } catch (IllegalAccessException e) {
            String msg = "Error accessing property. Are the getter and setter both accessible?";

            fail(msg);
        } catch (InvocationTargetException e) {
            String msg = "Error invoking method on target";
            fail(msg);
        }
    }

}

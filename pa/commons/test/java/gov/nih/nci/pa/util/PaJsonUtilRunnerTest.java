package gov.nih.nci.pa.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PaJsonUtilRunnerTest {

    private String booleanStr;
    private Boolean expectedResult;

    @Before
    public void initialize() {

    }

    public PaJsonUtilRunnerTest(String booleanStr, Boolean expectedResult) {
        this.booleanStr = booleanStr;
        this.expectedResult = expectedResult;
    }
    
    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(new Object[][] {
            { "true", Boolean.TRUE },
            { "false", Boolean.TRUE },
            { "null", Boolean.TRUE },
            { "trueee", Boolean.FALSE },
            { "TRUE", Boolean.FALSE },
            { "falseers", Boolean.FALSE },
            { "", Boolean.FALSE },
        });
    }

    @Test
    public void isValidBooleanStringTest() {
        assertEquals(expectedResult, PAJsonUtil.isValidBooleanString(booleanStr));
    }

}

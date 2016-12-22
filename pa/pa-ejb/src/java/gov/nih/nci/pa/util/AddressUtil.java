package gov.nih.nci.pa.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * Class used to centralize address logic.
 * @author Hugh Reinhart
 * @since May 28, 2013
 */
public class AddressUtil {

    private static final List<String> US_OR_CANADA_CODE = Arrays.asList("USA", "CAN");
    private static final String AUSTRALIA_CODE = "AUS";
    private static final int US_CANADA_STATE_CODE_LEN = 2;
    private static final List<Integer> AUS_STATE_CODE_LENS = Arrays.asList(2, 3);

    /**
     * Validate address values and add action errors as needed.
     * @param street street
     * @param city city
     * @param state state
     * @param zip postal code
     * @param country country
     * @return error messages or empty collection
     */
    public static Collection<String> addressValidations(String street, String city, String state, 
            String zip, String country) {
        Collection<String> result = new ArrayList<String>();
        result.addAll(requiredField("Street address", street));
        result.addAll(requiredField("City", city));
        result.addAll(requiredField("Country", country));

        if (usaOrCanada(country)) {
            if (state == null || state.trim().length() != US_CANADA_STATE_CODE_LEN) {
                result.add("2-letter State/Province Code required for USA/Canada");
            }
            if (StringUtils.isBlank(zip)) {
                result.add("Zip is a required field for US and Canadian addresses.");
            }
        }

        if (australia(country)) {
            if (StringUtils.isBlank(state) || !AUS_STATE_CODE_LENS.contains(state.trim().length())) {
                result.add("2/3-letter State/Province Code required for Australia");
            }
            if (StringUtils.isBlank(zip)) {
                result.add("Zip is a required field for Australian addresses.");
            }
        }
        return result;
    }

    /**
     * Create an error if the value is blank.
     * @param field field name
     * @param value value
     * @return error message or empty collection
     */
    public static Collection<String> requiredField(String field, String value) {
        List<String> result = new ArrayList<String>();
        if (StringUtils.isBlank(value)) {
            result.add(field +  " is a required field");
        }
        return result;
    }

    /**
     * Does a supplied alpha3 country code correspond to either the United States or Canada.
     * @param alpha3  alpha3 country code
     * @return is US or Canada
     */
    public static boolean usaOrCanada(String alpha3) {
        return alpha3 != null && US_OR_CANADA_CODE.contains(alpha3);
    }

    /**
     * Does a supplied alpha3 country code correspond to Australia.
     * @param alpha3 alpha3 country code
     * @return is Australia
     */
    public static boolean australia(String alpha3) {
        return StringUtils.equals(AUSTRALIA_CODE,  alpha3);
    }

    /**
     * Upper case the state code for US and Canadian addresses. 
     * @param state state code
     * @param country country alpha3 code
     * @return fixed code
     */
    public static String fixState(String state, String country) {
        if (state != null && usaOrCanada(country)) {
            return state.toUpperCase(Locale.US);
        } else {
            return state;
        }
    }
}

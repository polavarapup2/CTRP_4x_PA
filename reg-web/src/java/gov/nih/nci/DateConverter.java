/*
 * $Id: RequestUtils.java 394468 2006-04-16 12:16:03Z tmjee $
 *
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nih.nci;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

/**
 * Class used to convert dates.
 */
@SuppressWarnings("rawtypes") // raw types inherited from superclass
public class DateConverter extends StrutsTypeConverter {
    /**
     * @param context context
     * @param values  values
     * @param toClass class to convert to
     * @return Object date object
     */
    @Override
    public final Object convertFromString(final Map context, final String[] values, final Class toClass) {
        if (values != null && values.length > 0 && values[0] != null && values[0].length() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
            try {
                return sdf.parse(values[0]);
            } catch (ParseException e) {
                throw new TypeConversionException(e);
            }
        }
        return null;
    }
    /**
     * @param context context
     * @param o object
     * @return String date string
     */
    @Override
    public final String convertToString(final Map context, final Object o) {
        if (o instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
            return sdf.format((Date) o);
        }
        return "";
    }
}


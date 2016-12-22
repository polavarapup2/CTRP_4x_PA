package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.enums.AssayPurposeCode;
import gov.nih.nci.pa.enums.AssayTypeCode;
import gov.nih.nci.pa.enums.AssayUseCode;
import gov.nih.nci.pa.enums.BioMarkerAttributesCode;
import gov.nih.nci.pa.enums.EvaluationTypeCode;
import gov.nih.nci.pa.enums.TissueCollectionMethodCode;
import gov.nih.nci.pa.enums.TissueSpecimenTypeCode;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * 
 * @author Reshma Koganti
 * 
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@SuppressWarnings("PMD.ExcessiveParameterList")
public class MarkerAttributesBeanLocal implements MarkerAttributesServiceLocal {
    /**
     * Gets the map <Key,Value> pair of all Biomarkers Attributes.
     * 
     * @return the map with the type and corresponding attribute value
     * @throws PAException
     *             on error.
     */
    public Map<String, String> getAllMarkerAttributes() throws PAException {
        Map<String, String> returnValue = new HashMap<String, String>();
        returnValue.putAll(attributeValues(BioMarkerAttributesCode.ASSAY_TYPE));
        returnValue
                .putAll(attributeValues(BioMarkerAttributesCode.EVALUATION_TYPE));
        returnValue
                .putAll(attributeValues(BioMarkerAttributesCode.BIOMARKER_PURPOSE));
        returnValue
                .putAll(attributeValues(BioMarkerAttributesCode.BIOMARKER_USE));
        returnValue
                .putAll(attributeValues(BioMarkerAttributesCode.SPECIMEN_TYPE));
        returnValue
                .putAll(attributeValues(BioMarkerAttributesCode.SPECIMEN_COLLECTION));
        return returnValue;

    }
   
    /**
     * return the list of values for the BioMarker attributes with CADSR value.
     * 
     * @param valueType
     *            the valueType
     * @return the map<Key, Map<key,value>> with the attribute value
     *
     */
    public Map<Long, Map<String, String>> attributeValuesWithCaDSR(
            BioMarkerAttributesCode valueType) {
        Map<Long, Map<String, String>> returnValue = new HashMap<Long, Map<String, String>>();
        
        Session session = PaHibernateUtil.getCurrentSession();
        List<Object[]> queryList = null;
        SQLQuery query = session
                .createSQLQuery("Select type_code, DESCRIPTION_TEXT, cadsr_id from "
                        + valueType.getName());
        queryList = query.list();
        for (Object[] oArr : queryList) {
            if (oArr[0] != null) {
                Map<String, String> value = new HashMap<String, String>();
                value.put(oArr[0].toString(), oArr[1].toString());
                Integer ret = null;
                BigInteger retBig = null;
                if (oArr[2] instanceof Integer) {
                    ret = (Integer) oArr[2];  
                    returnValue.put(ret.longValue(), value);
                } else if (oArr[2] instanceof BigInteger) {
                    retBig = (BigInteger) oArr[2];  
                    returnValue.put(retBig.longValue(), value);
                }
            }
        }
        return returnValue;
    }
    
    private Map<String, String> attributeValues(
            BioMarkerAttributesCode valueType) {
        Map<String, String> returnValue = new HashMap<String, String>();
        Session session = PaHibernateUtil.getCurrentSession();
        List<Object[]> queryList = null;
        SQLQuery query = session
                .createSQLQuery("Select type_code, DESCRIPTION_TEXT from "
                        + valueType.getName());
        queryList = query.list();
        int i = 0;
        for (Object[] oArr : queryList) {
            if (oArr[0] != null) {
                returnValue.put(valueType.getName() + i, oArr[0].toString());
                i++;
            }
        }
        return returnValue;
    }

    /**
     * return the list of values for the BioMarker attributes.
     * 
     * @param valueType
     *            the valueType
     * @return the list with the attribute value
     * @throws PAException
     *             on error.
     */
    public static List<String> getTypeValues(BioMarkerAttributesCode valueType)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        List<Object[]> queryList = null;
        SQLQuery query = session
                .createSQLQuery("Select type_code, DESCRIPTION_TEXT from "
                        + valueType.getName());
        queryList = query.list();
        List<String> returnValue = new ArrayList<String>();
        for (Object[] oArr : queryList) {
            if (oArr[0] != null) {
                returnValue.add(oArr[0].toString());
            }
        }
        
        return orderAttributes(returnValue, valueType);
    }
    
    private static List<String> orderAttributes(List<String> values, BioMarkerAttributesCode valueType) {
        List<String> listValue = new ArrayList<String>();
        if (valueType == BioMarkerAttributesCode.EVALUATION_TYPE) {
            String[] orderedList = EvaluationTypeCode.getDisplayNames();
            listValue = orderValues(orderedList, values); 
        } else if (valueType == BioMarkerAttributesCode.ASSAY_TYPE) {            
            String[] orderedList = AssayTypeCode.getDisplayNames();
            listValue = orderValues(orderedList, values); 
        } else if (valueType == BioMarkerAttributesCode.BIOMARKER_PURPOSE) {            
            String[] orderedList = AssayPurposeCode.getDisplayNames();
            listValue = orderValues(orderedList, values);
        } else if (valueType == BioMarkerAttributesCode.BIOMARKER_USE) {           
            String[] orderedList = AssayUseCode.getDisplayNames();
            listValue = orderValues(orderedList, values); 
        } else if (valueType == BioMarkerAttributesCode.SPECIMEN_TYPE) {           
            String[] orderedList = TissueSpecimenTypeCode.getDisplayNames();
            listValue = orderValues(orderedList, values);    
        } else if (valueType == BioMarkerAttributesCode.SPECIMEN_COLLECTION) {
            String[] orderedList = TissueCollectionMethodCode.getDisplayNames();
            listValue = orderValues(orderedList, values); 
        }
        return listValue;
    }
    
    private static List<String> orderValues(String[] orderedList, List<String> values) {
        List<String> proposedOrderedList = new ArrayList<String>();
        List<String> listValue = new ArrayList<String>();
        for (String value : orderedList) {
            proposedOrderedList.add(value);
        }
        for (String value : proposedOrderedList) {
            if (containsIgnoreCaseAndSpace(value, values)) {
                listValue.add(value);
            }
        }
        for (String value : values) {
            if (!containsIgnoreCaseAndSpace(value, proposedOrderedList)) {
                listValue.add(value);
            }
        } 
        return swapList(listValue);
    }
    
    private static boolean containsIgnoreCaseAndSpace(String str, List<String> list) {
        for (String i : list) {
            if (i.replaceAll(" ", "").equalsIgnoreCase(str.replaceAll(" ", ""))) {
                return true;
            }
        }
        return false;
    }
    
    private static List<String> swapList(List<String> listValue) {
        if (listValue.contains("Unspecified")) {
            int x = listValue.indexOf("Unspecified");
            if (x != listValue.size() - 2) {
                Collections.swap(listValue, listValue.size() - 2, x);
            }  
        }        
        if (listValue.contains("Other")) {
            int x = listValue.indexOf("Other");
            if (x != listValue.size() - 1) {
                Collections.swap(listValue, listValue.size() - 1, x);
            }
        }
        return listValue;
    }
    /**
     * Delete the Biomarker attribute tables and sync with the CaDSR values
     * 
     * @param valueType valueType
     * @param map map
     * @throws PAException on error.
     */
    public void updateMarker(BioMarkerAttributesCode valueType, Map<Long , Map<String, String>> map) 
    throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
    
        SQLQuery query = session
                .createSQLQuery("Delete from " + valueType.getName());    
        query.executeUpdate();
        Iterator<Map.Entry<Long, Map<String, String>>> itr = map.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<Long, Map<String, String>> entry = itr.next();
            SQLQuery query1 = session
            .createSQLQuery("insert into " 
                    + valueType.getName() + " (type_code, DESCRIPTION_TEXT, CADSR_ID) " 
                    + "values (:typeCode, :descriptionText, :cadsrId)");
            query1.setParameter("cadsrId", entry.getKey().longValue());
            Map<String, String> valueMap = entry.getValue();
            Iterator<Map.Entry<String, String>> itr1 = valueMap.entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry<String, String> entry1 = itr1.next();            
                query1.setParameter("descriptionText", entry1.getValue().toString());
                query1.setParameter("typeCode", entry1.getKey().toString());
            }
            query1.executeUpdate();
        } 
    }

}

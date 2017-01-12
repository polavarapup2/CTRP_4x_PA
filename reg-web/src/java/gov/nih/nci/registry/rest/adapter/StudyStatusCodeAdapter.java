package gov.nih.nci.registry.rest.adapter;

import gov.nih.nci.pa.enums.StudyStatusCode;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Hugh Reinhart
 * @since Mar 1, 2013
 */
public class StudyStatusCodeAdapter extends XmlAdapter<String, StudyStatusCode> {

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyStatusCode unmarshal(String v) throws JAXBException {
        return StudyStatusCode.getByCode(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String marshal(StudyStatusCode v) throws JAXBException {
        return v.getCode();
    }
}

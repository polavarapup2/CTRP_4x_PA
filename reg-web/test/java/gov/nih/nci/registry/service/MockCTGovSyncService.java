/**
 * 
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ctgov.ClinicalStudy;
import gov.nih.nci.pa.service.search.CTGovImportLogSearchCriteria;
import gov.nih.nci.pa.service.util.CTGovStudyAdapter;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author Denis G. Krylov
 * 
 */
public class MockCTGovSyncService implements CTGovSyncServiceLocal {

    /**
     * 
     */
    public MockCTGovSyncService() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.pa.service.util.CTGovSyncServiceLocal#getCtGovStudyByNctId
     * (java.lang.String)
     */
    @Override
    public ClinicalStudy getCtGovStudyByNctId(String nctID) throws PAException {
        String packageName = ClinicalStudy.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            return (ClinicalStudy) u.unmarshal((this.getClass()
                    .getResourceAsStream("/NCT01861054.xml")));
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.pa.service.util.CTGovSyncServiceLocal#getAdaptedCtGovStudyByNctId
     * (java.lang.String)
     */
    @Override
    public CTGovStudyAdapter getAdaptedCtGovStudyByNctId(String nctID)
            throws PAException {
        return new CTGovStudyAdapter(getCtGovStudyByNctId(nctID));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.pa.service.util.CTGovSyncServiceLocal#importTrial(java.lang
     * .String)
     */
    @Override
    public String importTrial(String nctID) throws PAException {        
        return "NCI-2013-001";
        		
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.nih.nci.pa.service.util.CTGovSyncServiceLocal#getLogEntries(CTGovImportLogSearchCriteria searchCriteria)
     */
    @Override
    public List<CTGovImportLog> getLogEntries(CTGovImportLogSearchCriteria searchCriteria)
            throws PAException {        
        return new ArrayList<CTGovImportLog>();
    }

}

package gov.nih.nci.pa.util;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import gov.nih.nci.pa.noniso.dto.TrialRegistrationConfirmationDTO;
import gov.nih.nci.pa.service.PAException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 
 * @author Reshma
 *
 */
public class CTGovImportMergeHelper {
    private static final Logger LOG = Logger.getLogger(CTGovImportMergeHelper.class);
   
    /**
     * POST
     */
    private static final String POST = "POST";
    /**
     * PUT
     */
    private static final String PUT = "PUT";
    private ImportRestClient client;
    
    /**
     * 
     * const
     */
    public CTGovImportMergeHelper() {
        super();
        this.client = new ImportRestClient();
    }
    /**
     * 
     * @param nctID the nctID
     * @param studyExists the studyExists
     * @return StudyProtocolIdentityDTO
     * @throws PAException PAException
     */
    public TrialRegistrationConfirmationDTO insertOrUpdateNctId(String nctID, 
            boolean studyExists) throws PAException {
        TrialRegistrationConfirmationDTO dto = new TrialRegistrationConfirmationDTO();
        try {
            String response = "";
            if (studyExists) {
                response = client.sendHTTPRequest(PaEarPropertyReader
                        .getCtrpImportCtApiUrl() + "/" + nctID, PUT, null);
            } else {
                response = client.sendHTTPRequest(PaEarPropertyReader
                        .getCtrpImportCtApiUrl() + "/" + nctID, POST, null);
            }
            if (response != null) {
                Document doc = parseXmlFile(response);
                NodeList list = doc.getElementsByTagName("paTrialID");
               dto.setSpID(list.item(0).getTextContent());
               list = doc.getElementsByTagName("nciTrialID");
               dto.setNciID(list.item(0).getTextContent());
//               try {
//                   JAXBContext jaxbContext = JAXBContext.newInstance(resultClass); 
//                   Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//                   T resultObj = (T) jaxbUnmarshaller.unmarshal(new StringReader(response));
//                   return  resultObj;
//               } catch (JAXBException e) {
//                   throw new ImportTrialException("Error in unmarshalling XML");
//               }
            }
        } catch (Exception e) {
            LOG.error(
                    "Error in importing ctgov xml with NCT number"
                            + nctID, e);
            throw new PAException(
                    "Error in importing ctgov xml with NCT number"
                            + nctID, e);
        }
        return dto;
    }
    
    private Document parseXmlFile(String in) {
        DocumentBuilder db = null;
        Document doc = null;
        InputSource is = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            is = new InputSource(new StringReader(in));
            doc = db.parse(is);
        } catch (ParserConfigurationException e) {
            LOG.error(e.getLocalizedMessage());
        } catch (SAXException e) {
            LOG.error(e.getLocalizedMessage());
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return doc;
    }

    
    /**
     * 
     * @return client
     */
    public ImportRestClient getClient() {
        return client;
    }

    /**
     * 
     * @param client
     *            the client
     */
    public void setClient(ImportRestClient client) {
        this.client = client;
    }
    
}


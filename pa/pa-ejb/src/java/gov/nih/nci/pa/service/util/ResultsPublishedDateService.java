package gov.nih.nci.pa.service.util;

import freemarker.template.TemplateException;
import gov.nih.nci.pa.service.PAException;

import java.io.IOException;

import javax.ejb.Local;

/**
 * @author Apratim K
 *
 */
@Local
public interface ResultsPublishedDateService {
    
 /**
 * Automatically update the "Trial Results Published Date" 
 * when a trial has results posted in ClinicalTrials.gov. 
 * @throws PAException PAException
 * @throws IOException IOException
 * @throws TemplateException TemplateException
 */
void updatePublishedDate() throws PAException, IOException, TemplateException;  

}

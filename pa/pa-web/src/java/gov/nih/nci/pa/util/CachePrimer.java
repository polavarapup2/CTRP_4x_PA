/**
 * 
 */
package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.PAException;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author dkrylov
 * 
 */
public class CachePrimer implements Job {

    private static final Logger LOG = Logger.getLogger(CachePrimer.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        try {
            PaRegistry.getCachingPAOrganizationService()
                    .getOrganizationsAssociatedWithStudyProtocol(
                            "Lead Organization");
            PaRegistry.getCachingPAPersonService()
                    .getAllPrincipalInvestigators();
        } catch (PAException e) {
            LOG.error(e, e);
        }
    }

}

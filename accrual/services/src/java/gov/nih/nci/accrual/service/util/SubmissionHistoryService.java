package gov.nih.nci.accrual.service.util;

import gov.nih.nci.accrual.dto.HistoricalSubmissionDto;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Local;


/**
 * @author Hugh Reinhart
 * @since Jul 23, 2012
 */
@Local
public interface SubmissionHistoryService {
        /**
         * Search for historical submissions.
         * @param from from date to search (null for to skip this criteria)
         * @param to date to search (null to skip)
         * @param ru the user
         * @return list of historical submissions
         * @throws PAException exception
         */
        List<HistoricalSubmissionDto> search(Timestamp from, Timestamp to, RegistryUser ru) throws PAException;
}

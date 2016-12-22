package gov.nih.nci.pa.service.status;

import gov.nih.nci.pa.service.status.json.StatusRules;

import java.io.IOException;

import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author vinodh
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StatusTransitionsConfig {

    /**
     * Loads the status rules from the status rules json config string
     * @param statusRulesStr status rules json config string
     * @return StatusRules object tree
     * @throws IOException - any IO exception in loading the json string
     */
    public StatusRules loadStatusRules(String statusRulesStr) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(Feature.ALLOW_COMMENTS, true);
        return mapper.readValue(statusRulesStr, StatusRules.class);
    }
}

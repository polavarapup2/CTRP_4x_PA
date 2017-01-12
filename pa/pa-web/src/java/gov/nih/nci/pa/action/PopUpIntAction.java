package gov.nih.nci.pa.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.InterventionWebDTO;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StreamResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Hugh Reinhart, Denis Krylov
 * @since 10/31/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class PopUpIntAction extends ActionSupport {
    private static final long serialVersionUID = 9987838321L;
    private static final Logger LOG = Logger.getLogger(PopUpIntAction.class);
    private String searchName;
    private String includeSynonym;
    private String exactMatch;

    private List<InterventionWebDTO> interWebList = new ArrayList<InterventionWebDTO>();

    // CHECKSTYLE:OFF
    /**
     * @return StreamResult
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * @throws JSONException
     *             JSONException
     */
    @SuppressWarnings("deprecation")
    public StreamResult search() throws UnsupportedEncodingException,
            JSONException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put("items", arr);
        if (StringUtils.isNotBlank(getSearchName())) {
            InterventionDTO criteria = new InterventionDTO();
            criteria.setName(StConverter.convertToSt(getSearchName()));
            criteria.setIncludeSynonym(StConverter.convertToSt(Boolean.FALSE
                    .toString()));
            criteria.setExactMatch(StConverter.convertToSt(Boolean.FALSE
                    .toString()));
            try {
                for (InterventionDTO inter : PaRegistry
                        .getInterventionService().search(criteria)) {
                    InterventionWebDTO dto = convertToWebDTO(inter);
                    JSONObject json = new JSONObject();
                    json.put("id", dto.getIdentifier());
                    json.put("text",
                            StringEscapeUtils.escapeHtml(dto.getName()));
                    json.put("type",
                            StringEscapeUtils.escapeHtml(dto.getType()));
                    json.put("ctgovType", StringEscapeUtils
                            .escapeHtml(StringUtils.defaultString(dto
                                    .getCtGovType())));
                    json.put("term",
                            StringEscapeUtils.escapeHtml(getSearchName()));
                    arr.put(json);
                }
            } catch (Exception e) {
                LOG.error(e, e);
            }
        }
        return new StreamResult(new ByteArrayInputStream(root.toString()
                .getBytes("UTF-8")));
    }

    // CHECKSTYLE:ON

    private void loadResultList() {
        interWebList.clear();
        String tName = ServletActionContext.getRequest().getParameter(
                "searchName");
        String includeSyn = ServletActionContext.getRequest().getParameter(
                "includeSynonym");
        String exactMat = ServletActionContext.getRequest().getParameter(
                "exactMatch");

        if (StringUtils.isEmpty(tName)) {
            error("Please enter at least one search criteria.");
            return;
        }

        InterventionDTO criteria = new InterventionDTO();
        criteria.setName(StConverter.convertToSt(tName));
        criteria.setIncludeSynonym(StConverter.convertToSt(includeSyn));
        criteria.setExactMatch(StConverter.convertToSt(exactMat));
        List<InterventionDTO> interList = null;
        try {
            interList = PaRegistry.getInterventionService().search(criteria);
        } catch (PAException e) {
            error(e.getMessage());
            return;
        } catch (Exception e) {
            error("Exception thrown while getting intervention list using service.",
                    e);
            return;
        }
        for (InterventionDTO inter : interList) {
            InterventionWebDTO newRec = convertToWebDTO(inter);
            interWebList.add(newRec);
        }
        loadAlternateNames();
    }

    /**
     * @param inter
     * @return
     */
    private InterventionWebDTO convertToWebDTO(InterventionDTO inter) {
        InterventionWebDTO newRec = new InterventionWebDTO();
        newRec.setType(CdConverter.convertCdToString(inter.getTypeCode()));
        newRec.setIdentifier(IiConverter.convertToString(inter.getIdentifier()));
        newRec.setDescription(StConverter.convertToString(inter
                .getDescriptionText()));
        newRec.setName(StConverter.convertToString(inter.getName()));
        newRec.setCtGovType(CdConverter.convertCdToString(inter
                .getCtGovTypeCode()));
        return newRec;
    }

    private void loadAlternateNames() {
        Ii[] ianIis = new Ii[interWebList.size()];
        int x = 0;
        for (InterventionWebDTO dto : interWebList) {
            ianIis[x++] = IiConverter.convertToIi(dto.getIdentifier());
        }
        List<InterventionAlternateNameDTO> ianList;
        try {
            ianList = PaRegistry.getInterventionAlternateNameService()
                    .getByIntervention(ianIis);
        } catch (Exception e) {
            error("Exception thrown while getting alternate names.", e);
            return;
        }
        HashMap<String, String> aNames = new HashMap<String, String>();
        for (InterventionAlternateNameDTO ian : ianList) {
            String id = IiConverter.convertToString(ian
                    .getInterventionIdentifier());
            if (aNames.containsKey(id)) {
                aNames.put(
                        id,
                        aNames.get(id) + ", "
                                + StConverter.convertToString(ian.getName()));
            } else {
                aNames.put(id, StConverter.convertToString(ian.getName()));
            }
        }
        for (InterventionWebDTO dto : interWebList) {
            dto.setOtherNames(aNames.get(dto.getIdentifier()));
        }
    }

    private void error(String errMsg, Throwable t) {
        LOG.error(errMsg, t);
        ServletActionContext.getRequest().setAttribute(
                Constants.FAILURE_MESSAGE, errMsg);
    }

    private void error(String errMsg) {
        error(errMsg, null);
    }

    /**
     * @return result
     */
    public String displayList() {
        loadResultList();
        return SUCCESS;
    }

    /**
     * @return the searchName
     */
    public String getSearchName() {
        return searchName;
    }

    /**
     * @param searchName
     *            the searchName to set
     */
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    /**
     * @return the interWebList
     */
    public List<InterventionWebDTO> getInterWebList() {
        return interWebList;
    }

    /**
     * @param interWebList
     *            the interWebList to set
     */
    public void setInterWebList(List<InterventionWebDTO> interWebList) {
        this.interWebList = interWebList;
    }

    /**
     * @return the includeSynonym
     */
    public String getIncludeSynonym() {
        return includeSynonym;
    }

    /**
     * @param includeSynonym
     *            the includeSynonym to set
     */
    public void setIncludeSynonym(String includeSynonym) {
        this.includeSynonym = includeSynonym;
    }

    /**
     * @return the exactMatch
     */
    public String getExactMatch() {
        return exactMatch;
    }

    /**
     * @param exactMatch
     *            the exactMatch to set
     */
    public void setExactMatch(String exactMatch) {
        this.exactMatch = exactMatch;
    }
}

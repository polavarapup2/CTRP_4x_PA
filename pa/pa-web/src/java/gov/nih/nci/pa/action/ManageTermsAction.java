package gov.nih.nci.pa.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.dto.InterventionWebDTO;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseAlternameDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseParentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.noniso.dto.PDQDiseaseNode;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseAlternameServiceLocal;
import gov.nih.nci.pa.service.PDQDiseaseParentServiceRemote;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.MailManagerService;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.LEXEVSLookupException;
import gov.nih.nci.pa.util.NCItTermsLookup;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action class for Managing Intervention/Disease terms
 *
 * @author Gopalakrishnan Unnikrishnan
 * @since 08/01/2014 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */

@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity",
        "PMD.ExcessiveMethodLength", "PMD.ExcessiveClassLength" , "PMD.TooManyMethods" ,
        "PMD.SignatureDeclareThrowsException" , "PMD.PreserveStackTrace",
         "PMD.InefficientEmptyStringCheck", "PMD.AvoidDuplicateLiterals", "PMD.TooManyFields" , 
         "PMD.NcssMethodCount" })
public class ManageTermsAction extends ActionSupport implements Preparable {

    private static final String ALTNAME_TYPECODE_SYNONYM = "Synonym";

    private static final String PARENT_DISEASE_CODE_ISA = "ISA";

    private static final long serialVersionUID = 9154119501161489767L;

    private static final String INTERVENTION = "intervention"; 
    private static final String SEARCH_INTERVENTION = "searchIntervention";
    private static final String SYNC_INTERVENTION = "syncIntervention";

    private static final String DISEASE = "disease"; 
    private static final String SEARCH_DISEASE = "searchDisease";
    private static final String PAGE_DISCRIMINATOR = "pageDiscriminator";
    private static final String SYNC_DISEASE = "syncDisease";

    private static final String AJAX_RESPONSE = "ajaxResponse";
    
    private static final int MAX_SYN_LENGTH = 200;
    
    private static final String CHEMICAL_STRUCTURE_NAME = "Chemical structure name";

    private InterventionServiceLocal interventionService;
    private InterventionAlternateNameServiceLocal interventionAltNameService;
    private InterventionWebDTO intervention = new InterventionWebDTO();
    private InterventionWebDTO currentIntervention = new InterventionWebDTO();

    private PDQDiseaseParentServiceRemote diseaseParentService;
    private PDQDiseaseServiceLocal diseaseService;
    private PDQDiseaseAlternameServiceLocal diseaseAltNameService;
    private DiseaseWebDTO disease = new DiseaseWebDTO();
    private DiseaseWebDTO currentDisease = new DiseaseWebDTO();

    private boolean importTerm = false;
    private String pageDiscriminator;
    
    private List<String> newAltnames  = new ArrayList<String>();
    private List<String> currentAltnames = new ArrayList<String>();

    private InputStream ajaxResponseStream;
    
    private String diseaseSearchTerm;
    private boolean searchSynonyms = false;
    
    private CSMUserUtil userService;

    private RegistryUserServiceLocal registryUserService;
    
    private MailManagerService mailManagerService;
    
    private boolean exactSearch;
   

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() throws PAException {
        // Initialize intervention services
        interventionService = PaRegistry.getInterventionService();
        interventionAltNameService = PaRegistry.getInterventionAlternateNameService();

        // Initialize disease services
        diseaseService = PaRegistry.getDiseaseService();
        diseaseParentService = PaRegistry.getDiseaseParentService();
        diseaseAltNameService = PaRegistry.getDiseaseAlternameService();
        userService = CSMUserService.getInstance();
        registryUserService = PaRegistry.getRegistryUserService();
        
        mailManagerService = PaRegistry.getMailManagerService();
       
    }

    /**
     * Deafult action
     *
     * @return res
     * @throws PAException
     *             exception
     */
    @Override
    public String execute() throws PAException {
        return SUCCESS;
    }

    /**
     * Create intervention
     *
     * @return view
     */
    public String createIntervention() {
        importTerm = false;
        pageDiscriminator = (String) ServletActionContext.getRequest().getParameter(PAGE_DISCRIMINATOR);
        return INTERVENTION;
    }

    /**
     * Create Disease
     *
     * @return view
     */
    public String createDisease() {
        importTerm = false;
        pageDiscriminator = (String) ServletActionContext.getRequest().getParameter(PAGE_DISCRIMINATOR);
        disease.setDisplayNameList(diseaseService.getAllDisplayNames());
        return DISEASE;
    }

    /**
     * Save a new intervention
     *
     * @return view
     */
    public String saveIntervention() {
        try {
            if (!validateIntervention()) {
                ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                        "Please correct the errors listed below and resubmit");
                return INTERVENTION;
            }

            if (getExistingIntervention(intervention.getNtTermIdentifier()) == null) { // Check
                                                                                       // if
                // intervention
                // with same
                // NCTid exists
                InterventionDTO dto = new InterventionDTO();
                dto.setPdqTermIdentifier(StConverter.convertToSt(intervention.getIdentifier()));
                dto.setNtTermIdentifier(StConverter.convertToSt(intervention.getNtTermIdentifier()));
                dto.setName(StConverter.convertToSt(intervention.getName()));
                dto.setCtGovTypeCode(CdConverter.convertStringToCd(intervention.getCtGovType()));                
                dto.setTypeCode(CdConverter.convertStringToCd(intervention.getType()));
                dto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                dto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil.today())));
                dto = interventionService.create(dto);
               
                if (importTerm) {
                InterventionWebDTO  interventionWebDTO = 
                     (InterventionWebDTO) ServletActionContext.getRequest().getSession().getAttribute("intervention");
               
                if (interventionWebDTO != null && interventionWebDTO.getAlterNames() != null) {
                    for (String altName : interventionWebDTO.getAlterNames().keySet()) {
                        InterventionAlternateNameDTO altDto = new InterventionAlternateNameDTO();                      
                        altDto.setName(StConverter.convertToSt(altName));
                        if (altName.length() > MAX_SYN_LENGTH) {
                           altDto.setNameTypeCode(StConverter.convertToSt(CHEMICAL_STRUCTURE_NAME));
                        } else {
                        altDto.setNameTypeCode(StConverter.convertToSt(
                                    interventionWebDTO.getAlterNames().get(altName)));
                        }                        
                        altDto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                        altDto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil
                                .today())));
                        altDto.setInterventionIdentifier(dto.getIdentifier());
                        interventionAltNameService.create(altDto);
                    }
                }
                } else {
                    for (String altName : intervention.getAlterNamesList()) {
                        InterventionAlternateNameDTO altDto = new InterventionAlternateNameDTO();
                        altDto.setName(StConverter.convertToSt(altName));
                        if (altName.length() > MAX_SYN_LENGTH) {
                           altDto.setNameTypeCode(StConverter.convertToSt(CHEMICAL_STRUCTURE_NAME));
                        } else {
                           altDto.setNameTypeCode(StConverter.convertToSt(ALTNAME_TYPECODE_SYNONYM));
                        }                        
                        altDto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                        altDto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil
                                .today())));
                        altDto.setInterventionIdentifier(dto.getIdentifier());
                        interventionAltNameService.create(altDto);
                    }
                }
                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE,
                        "New intervention " + intervention.getNtTermIdentifier() + " added successfully");
            } else {
                ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                        "Intervention with NCIt code " + intervention.getNtTermIdentifier() + " already exists!");
                return INTERVENTION;
            }
        } catch (PAException e) {
            LOG.error("Error saving intervention", e);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return INTERVENTION;
        }
        return SUCCESS;
    }

    /**
     * Search for intervention by NCIt code in NCI Thesaurus
     *
     * @return view
     */
    public String searchIntervention() {
        String ncitCode = intervention.getNtTermIdentifier();
        importTerm = true;
        pageDiscriminator = (String) ServletActionContext.getRequest().getParameter(PAGE_DISCRIMINATOR);
        if (StringUtils.isEmpty(ncitCode)) {
            if (ServletActionContext.getRequest().getParameter("searchStart") == null) {
                ServletActionContext.getRequest()
                        .setAttribute(Constants.FAILURE_MESSAGE, "Enter valid NCIt Identifier");
            }
            return SEARCH_INTERVENTION;
        }
        try {
            ncitCode = ncitCode.toUpperCase(Locale.getDefault());
            InterventionWebDTO temp = new NCItTermsLookup().lookupIntervention(ncitCode);
            if (temp != null) {
                intervention = temp;
                // Check if the term already exists
                InterventionDTO existingIntrvDto = getExistingIntervention(intervention.getNtTermIdentifier());
                if (existingIntrvDto != null) {
                  
                    currentIntervention = new InterventionWebDTO();
                    currentIntervention.setIdentifier(existingIntrvDto.getPdqTermIdentifier().getValue());
                    currentIntervention.setNtTermIdentifier(existingIntrvDto.getNtTermIdentifier().getValue());
                    currentIntervention.setName(existingIntrvDto.getName().getValue());
                    currentIntervention
                            .setCtGovType(CdConverter.convertCdToString(existingIntrvDto.getCtGovTypeCode()));
                    currentIntervention.setType(CdConverter.convertCdToString(existingIntrvDto.getTypeCode()));

                    List<InterventionAlternateNameDTO> altNames = interventionAltNameService
                            .getByIntervention(existingIntrvDto.getIdentifier());
                    if (altNames != null && !altNames.isEmpty()) {
                        for (Iterator<InterventionAlternateNameDTO> iterator = altNames.iterator();
                                            iterator.hasNext();) {
                            InterventionAlternateNameDTO interventionAlternateNameDTO = iterator.next();
                            
                                currentIntervention.getAlterNames().put(
                                       interventionAlternateNameDTO.getName().getValue(),
                                        interventionAlternateNameDTO.getNameTypeCode().getValue());
                            

                        }
                    }
                    Map<String, String> newAltNamesMap = intervention.getAlterNames();
                    newAltnames = new ArrayList<String>();
                    for (String newAltNameVal :newAltNamesMap.keySet()) {
                        if (newAltNamesMap.get(newAltNameVal) != null 
                                && (newAltNamesMap.get(newAltNameVal).equals("CAS Registry name")
                                 || newAltNamesMap.get(newAltNameVal).equals("NSC number"))) {
                           continue;
                        }
                        newAltnames.add(newAltNameVal);
                    }
                    
                    Map<String, String> currentMap = currentIntervention.getAlterNames();
                    currentAltnames = new ArrayList<String>();
                    
                    for (String currentAltNameVal : currentMap.keySet()) {
                        if (currentMap.get(currentAltNameVal) != null 
                                && (currentMap.get(currentAltNameVal).equals("CAS Registry name")
                                 || currentMap.get(currentAltNameVal).equals("NSC number"))) {
                            continue;
                        }
                        currentAltnames.add(currentAltNameVal);
                    }
                    ServletActionContext.getRequest().getSession().setAttribute("intervention", intervention);
                    ServletActionContext.getRequest().setAttribute(
                            Constants.FAILURE_MESSAGE,
                            "Intervention with NCIt code '" + ncitCode
                                    + "' already present in CTRP, compare the values below and click "
                                    + "'Sync Term' to update the CTRP term with values from NCIt");
                    return SYNC_INTERVENTION;
                }
            } else {
                ServletActionContext.getRequest().setAttribute(
                        Constants.FAILURE_MESSAGE,
                        "No intervention with NCIt code '" + ncitCode
                                + "' found in NCI Thesaurus, try a different code");

                return SEARCH_INTERVENTION;

            }
        } catch (Exception e) {
            LOG.error("Error looking up intervention", e);
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE,
                    "Error looking up intervention, make sure the NCIt identifier '" + ncitCode
                            + "' corresponds to a intervention. Ther error was: " + e.getLocalizedMessage());
            return SEARCH_INTERVENTION;
        }

        return INTERVENTION;
    }

    /**
     * Synchronize an existing CTRP intervention with intervention retrieved
     * from NCIt
     *
     * @return view
     * @throws PAException
     *             PAException
     */
    public String syncIntervention() throws PAException {
        InterventionWebDTO newIntervention = (InterventionWebDTO) ServletActionContext.getRequest().getSession()
                .getAttribute("intervention");
        if (newIntervention != null) {
            ServletActionContext.getRequest().getSession().removeAttribute("intervention");
            intervention = newIntervention;
        }

        InterventionDTO currentIntrv = getExistingIntervention(intervention.getNtTermIdentifier());
        if (currentIntrv != null) {
            currentIntrv.setName(StConverter.convertToSt(intervention.getName()));

            // Remove existing synonyms
            List<InterventionAlternateNameDTO> altNames = interventionAltNameService.getByIntervention(currentIntrv
                    .getIdentifier());
            if (altNames != null && !altNames.isEmpty()) {
                for (Iterator<InterventionAlternateNameDTO> iterator = altNames.iterator(); iterator.hasNext();) {
                    InterventionAlternateNameDTO interventionAlternateNameDTO = (InterventionAlternateNameDTO) iterator
                            .next();
                       interventionAltNameService.delete(interventionAlternateNameDTO.getIdentifier());
                    
                }
            }

            // Save new synonyms
            if (intervention.getAlterNames() != null) {
                for (String altName : intervention.getAlterNames().keySet()) {
                    InterventionAlternateNameDTO altDto = new InterventionAlternateNameDTO();
                    altDto.setName(StConverter.convertToSt(altName));
                    if (altName.length() > MAX_SYN_LENGTH) {
                        altDto.setNameTypeCode(StConverter.convertToSt(CHEMICAL_STRUCTURE_NAME));
                    } else {
                        altDto.setNameTypeCode(StConverter.convertToSt(intervention.getAlterNames().get(altName)));
                    }
                    altDto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                    altDto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil.today())));
                    altDto.setInterventionIdentifier(currentIntrv.getIdentifier());
                    interventionAltNameService.create(altDto);
                }
            }

            interventionService.update(currentIntrv);
            ServletActionContext.getRequest().setAttribute(
                    Constants.SUCCESS_MESSAGE,
                    "Intervention " + currentIntrv.getNtTermIdentifier().getValue()
                            + " synchronized with NCI thesaurus");
        } else {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                    "No intervention with NCIt code '" + intervention.getNtTermIdentifier() + "' found in CTRP");
        }
        return SUCCESS;
    }

    /**
     * Search for existing intervention by NCIt Id
     *
     * @param ncitId
     * @return matched intervention DTO, <code>null</code> if no match was found
     * @throws PAException
     */
    private InterventionDTO getExistingIntervention(String ncitId) throws PAException {
        InterventionDTO dto = new InterventionDTO();
        dto.setNtTermIdentifier(StConverter.convertToSt(ncitId));
        List<InterventionDTO> searcchResults = interventionService.search(dto);
        if (!searcchResults.isEmpty()) {
            return searcchResults.get(0);
        }
        return null;
    }

    /**
     * Validate data entered in the intervention form
     */
    private boolean validateIntervention() {
        boolean valid = true;
        if (StringUtils.isEmpty(intervention.getNtTermIdentifier())) {
            addFieldError("intervention.ntTermIdentifier", getText("manageTerms.fieldError.ntTermIdentifier"));
            valid = false;
        }

        if (StringUtils.isEmpty(intervention.getName())) {
            addFieldError("intervention.name", getText("manageTerms.fieldError.name"));
            valid = false;
        }

        return valid;
    }

    /**
     * @return the intervention
     */
    public InterventionWebDTO getIntervention() {
        return intervention;
    }

    /**
     * @param intervention
     *            the intervention to set
     */
    public void setIntervention(InterventionWebDTO intervention) {
        this.intervention = intervention;
    }

    /**
     * @return the currentintervention
     */
    public InterventionWebDTO getCurrentIntervention() {
        return currentIntervention;
    }

    /**
     * @param currentIntervention
     *            the currentIntervention to set
     */
    public void setCurrentIntervention(InterventionWebDTO currentIntervention) {
        this.currentIntervention = currentIntervention;
    }

    /**
     * @return the importTerm
     */
    public boolean isImportTerm() {
        return importTerm;
    }

    /**
     * @param importTerm
     *            the importTerm to set
     */
    public void setImportTerm(boolean importTerm) {
        this.importTerm = importTerm;
    }
    
 
    // Disease actions and methods

    /**
     * Save a new disease/condition
     *
     * @return view
     */
    public String saveDisease() {
        try {
          
            if (!validateDisease()) {
              
                // remove terms with empty C codes 
                removeTermsWithNullNCItCode(disease.getParentTermList());
                removeTermsWithNullNCItCode(disease.getChildTermList());
                ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                        "Please correct the errors listed below and resubmit");
                return DISEASE;
            }
            if (getExistingDisease(disease.getNtTermIdentifier()) == null) {

                List<String> missingTerms = new ArrayList<String>();
                List<PDQDiseaseParentDTO> parentDtos = new ArrayList<PDQDiseaseParentDTO>();
                List<PDQDiseaseParentDTO> childDtos = new ArrayList<PDQDiseaseParentDTO>();
                
                PDQDiseaseDTO diseaseDto = new PDQDiseaseDTO();
                diseaseDto.setDiseaseCode(StConverter.convertToSt(disease.getCode()));
                diseaseDto.setNtTermIdentifier(StConverter.convertToSt(disease.getNtTermIdentifier()));
                diseaseDto.setPreferredName(StConverter.convertToSt(disease.getPreferredName()));
                diseaseDto.setDisplayName(StConverter.convertToSt(disease.getMenuDisplayName()));
                diseaseDto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                diseaseDto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil.today())));
                diseaseDto = diseaseService.create(diseaseDto);
                
                // Save alter names
                if (disease.getAlterNameList() != null) {
                   //remove duplicate synonyms
                Set<String> namesSet = new HashSet<String>(disease.getAlterNameList());
                    List<String> uniqueNamesList = new ArrayList<String>(namesSet);
                    for (String altName : uniqueNamesList) {
                        PDQDiseaseAlternameDTO altDto = new PDQDiseaseAlternameDTO();
                        altDto.setAlternateName(StConverter.convertToSt(altName));
                        altDto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                        altDto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil
                                .today())));
                        altDto.setDiseaseIdentifier(diseaseDto.getIdentifier());
                        diseaseAltNameService.create(altDto);
                    }
                }

                // Check if all parent and children terms exists in CTRP
                for (Iterator<String> iterator = disease.getParentTermList().iterator(); iterator.hasNext();) {
                    String parentTerm = iterator.next();
                    PDQDiseaseDTO parent = null;
                    if (StringUtils.isNumeric(parentTerm)) {
                        parent = diseaseService.get(IiConverter.convertToIi(parentTerm));
                    } else {
                        String parentCode = parentTerm.split(":")[0];
                        parent = getExistingDisease(parentCode);
                        if (parent == null) {
                            missingTerms.add(parentCode);
                            parent = retrieveAndSaveMissingTerm(parentCode);
                        }
                    }
                    PDQDiseaseParentDTO p = new PDQDiseaseParentDTO();
                    p.setParentDiseaseCode(StConverter.convertToSt(PARENT_DISEASE_CODE_ISA));
                    p.setParentDiseaseIdentifier(parent.getIdentifier());
                    p.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                    p.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil.today())));
                    parentDtos.add(p);
                }

                for (Iterator<String> iterator = disease.getChildTermList().iterator(); iterator.hasNext();) {
                    String childTerm = iterator.next();
                    PDQDiseaseDTO child = null;
                    if (StringUtils.isNumeric(childTerm)) {
                        child = diseaseService.get(IiConverter.convertToIi(childTerm));
                    } else {
                        String childCode = childTerm.split(":")[0];
                        child = getExistingDisease(childCode);
                        if (child == null) {
                            missingTerms.add(childCode);
                            child = retrieveAndSaveMissingTerm(childCode);
                        }    
                    }
                    
                    PDQDiseaseParentDTO c = new PDQDiseaseParentDTO();
                    c.setDiseaseIdentifier(child.getIdentifier());
                    c.setParentDiseaseCode(StConverter.convertToSt(PARENT_DISEASE_CODE_ISA));
                    c.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                    c.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil.today())));
                    childDtos.add(c);
                }
                 saveParentChilds(diseaseDto);
                 if (importTerm) {
                     String displayName = null;
                     if (disease.getMenuDisplayName() != null) {
                         displayName = disease.getMenuDisplayName();
                     }   
                      //send email for sync
                     sendSyncEmail(disease.getNtTermIdentifier(), disease.getPreferredName(), displayName);
                 } else {
                  // Save parents
                     for (Iterator<PDQDiseaseParentDTO> iterator = parentDtos.iterator(); iterator.hasNext();) {
                         PDQDiseaseParentDTO parentDto = iterator.next();
                         parentDto.setDiseaseIdentifier(diseaseDto.getIdentifier());
                         diseaseParentService.create(parentDto);
                     }
                      // Save Children
                     for (Iterator<PDQDiseaseParentDTO> iterator = childDtos.iterator(); iterator.hasNext();) {
                         PDQDiseaseParentDTO childDto = iterator.next();
                         childDto.setParentDiseaseIdentifier(diseaseDto.getIdentifier());
                         diseaseParentService.create(childDto);
                     }
                 }
                if (!missingTerms.isEmpty()) {
                    String errorMsg = createTermsMissingErrorMessage(missingTerms);
                    ServletActionContext.getRequest().setAttribute(
                            Constants.SUCCESS_MESSAGE,
                            "The CTRP system imported the disease " + disease.getNtTermIdentifier() + " successfully."
                             + " It also imported the following parent/child terms:"
                             + errorMsg);
                } else {
                    ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE,
                            "New Disease " + disease.getNtTermIdentifier() + " added successfully");
                }
            } else {
                ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                        "Disease with NCIt code " + disease.getNtTermIdentifier() + " already exists!");
                // remove terms with empty C codes 
                removeTermsWithNullNCItCode(disease.getParentTermList());
                removeTermsWithNullNCItCode(disease.getChildTermList());
                return DISEASE;
            }
        } catch (PAException e) {
            LOG.error("Error saving disease", e);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            // remove terms with empty C codes 
            removeTermsWithNullNCItCode(disease.getParentTermList());
            removeTermsWithNullNCItCode(disease.getChildTermList());
            return DISEASE;
        } catch (LEXEVSLookupException lexe) {
            LOG.error("Error retrieving missing parent/child disease", lexe);
            // remove terms with empty C codes 
            removeTermsWithNullNCItCode(disease.getParentTermList());
            removeTermsWithNullNCItCode(disease.getChildTermList());
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, lexe.getLocalizedMessage());
            return DISEASE;
        }
    
        PopUpDisAction.getDiseaseTreeCache().removeAll();
        return SUCCESS;
    }

    /**
     * Search for Disease by NCIt code in NCI Thesaurus
     *
     * @return view
     */
    public String searchDisease() {
        String ncitCode = disease.getNtTermIdentifier();
        importTerm = true;
        pageDiscriminator = (String) ServletActionContext.getRequest().getParameter(PAGE_DISCRIMINATOR);
        if (StringUtils.isEmpty(ncitCode)) {
            if (ServletActionContext.getRequest().getParameter("searchStart") == null) {
                ServletActionContext.getRequest()
                        .setAttribute(Constants.FAILURE_MESSAGE, "Enter valid NCIt Identifier");
            }
            return SEARCH_DISEASE;
        }
        try {
            ncitCode = ncitCode.toUpperCase(Locale.getDefault());
            DiseaseWebDTO temp = new NCItTermsLookup().lookupDisease(ncitCode);
            if (temp != null) {
                disease = temp;
                // Check if the term already exists
                PDQDiseaseDTO existingDiseaseDto = getExistingDisease(disease.getNtTermIdentifier());
                if (existingDiseaseDto != null) {
                    currentDisease = new DiseaseWebDTO();
                    currentDisease.setCode(existingDiseaseDto.getDiseaseCode().getValue());
                    currentDisease.setNtTermIdentifier(existingDiseaseDto.getNtTermIdentifier().getValue());
                    currentDisease.setPreferredName(existingDiseaseDto.getPreferredName().getValue());
                    currentDisease.setMenuDisplayName(existingDiseaseDto.getDisplayName().getValue());
                    List<PDQDiseaseAlternameDTO> altNames = diseaseAltNameService.getByDisease(existingDiseaseDto
                            .getIdentifier());
                    if (altNames != null && !altNames.isEmpty()) {
                        for (Iterator<PDQDiseaseAlternameDTO> iterator = altNames.iterator(); iterator.hasNext();) {
                            PDQDiseaseAlternameDTO diseaseAlternateNameDTO = (PDQDiseaseAlternameDTO) iterator.next();
                            currentDisease.getAlterNameList()
                                    .add(diseaseAlternateNameDTO.getAlternateName().getValue());

                        }
                    }

                    getExistingParentsAndChildrenForDisease(existingDiseaseDto, currentDisease);

                    ServletActionContext.getRequest().getSession().setAttribute("disease", disease);
                    ServletActionContext.getRequest().getSession().setAttribute("currentDisease", currentDisease);
                    ServletActionContext.getRequest().setAttribute(
                            Constants.FAILURE_MESSAGE,
                            "Disease with NCIt code '" + ncitCode
                                    + "' already present in CTRP, compare the values below and click"
                                    + " 'Sync Term' to update the CTRP term with values from NCIt");
                    return SYNC_DISEASE;
                }
                disease.setDisplayNameList(diseaseService.getAllDisplayNames());
            } else {
                ServletActionContext.getRequest().setAttribute(
                        Constants.FAILURE_MESSAGE,
                        "No disease with NCIt code '" + ncitCode
                                + "' found in NCI Thesaurus, try a different code");

                return SEARCH_DISEASE;

            }
        } catch (Exception e) {
            LOG.error("Error looking up disease", e);
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE,
                    "Error looking up disease/condition, make sure the NCIt identifier '" + ncitCode
                            + "' corresponds to a disease/condition. Ther error was: " + e.getLocalizedMessage());
            return SEARCH_DISEASE;
        }

        return DISEASE;
    }

    /**
     * Synchronize an existing CTRP intervention with intervention retrieved
     * from NCIt
     * 
     * @return view
     */
    public String syncDisease() {
        
       
       
        DiseaseWebDTO newDisease = (DiseaseWebDTO) ServletActionContext.getRequest().getSession()
                .getAttribute("disease");
        if (newDisease != null) {
            disease = newDisease;
            currentDisease = (DiseaseWebDTO) ServletActionContext.getRequest().getSession()
                    .getAttribute("currentDisease");
            ServletActionContext.getRequest().getSession().removeAttribute("disease");
            ServletActionContext.getRequest().getSession().removeAttribute("currentDisease");
        }
        try {
            
            
         
            
            PDQDiseaseDTO currDisease = getExistingDisease(disease.getNtTermIdentifier());
            if (currDisease != null) {

                currDisease.setPreferredName(StConverter.convertToSt(disease.getPreferredName()));

                // Delete existing synonyms
                List<PDQDiseaseAlternameDTO> altNames = diseaseAltNameService.getByDisease(currDisease.getIdentifier());
                if (altNames != null && !altNames.isEmpty()) {
                    for (Iterator<PDQDiseaseAlternameDTO> iterator = altNames.iterator(); iterator.hasNext();) {
                        PDQDiseaseAlternameDTO diseaseAlternateNameDTO = iterator.next();
                        diseaseAltNameService.delete(diseaseAlternateNameDTO.getIdentifier());
                    }
                }
                
                

                // Save new synonyms
                if (disease.getAlterNameList() != null) {
                    //remove duplicate synonyms
                    Set<String> namesSet = new HashSet<String>(disease.getAlterNameList());
                    List<String> uniqueNamesList = new ArrayList<String>(namesSet);
                    for (String altName : uniqueNamesList) {
                        PDQDiseaseAlternameDTO altDto = new PDQDiseaseAlternameDTO();
                        altDto.setAlternateName(StConverter.convertToSt(altName));
                        altDto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                        altDto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil
                                .today())));
                        altDto.setDiseaseIdentifier(currDisease.getIdentifier());
                        diseaseAltNameService.create(altDto);
                    }
                }

                       

                saveParentChilds(currDisease);
                PopUpDisAction.getDiseaseTreeCache().removeAll();
                
                String displayName = null;
                
                if (currentDisease.getMenuDisplayName() != null) {
                    displayName = currentDisease.getMenuDisplayName();
                }
                
                //send email for sync
                sendSyncEmail(disease.getNtTermIdentifier(), disease.getPreferredName(), displayName);
               
               
               // ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE,
                 //       "Disease/Condition with NCIt code '" + disease.getNtTermIdentifier() 
                   //     + "' synchronized from NCIt");
            } else {
                ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                        "No Disease/Condition with NCIt code '" + disease.getNtTermIdentifier() + "' found in CTRP");
            }
        } catch (Exception e) {
            LOG.error("Error saving disease", e);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return DISEASE;
        }
        return SUCCESS;
    }
    
    private void sendSyncEmail(String ncitTerm, String preferredName, String displayName) throws PAException  {
        User user = null;
        user = userService.getCSMUser(UsernameHolder.getUser());
        String emailId = user.getEmailId();
        String userName = null;
       
        RegistryUser registryUser = registryUserService.getUser(user.getLoginName());
        
        if (StringUtils.isBlank(user.getEmailId())) {
            if (registryUser != null && StringUtils.isNotBlank(registryUser.getEmailAddress())) {
                emailId = registryUser.getEmailAddress(); 
                userName = registryUser.getLastName() + " , " + registryUser.getFirstName();
            } 
        } else {
            emailId = user.getEmailId();
            userName = user.getLastName() + " , " + user.getFirstName();
        }
        
       
        
        //send email only of email id is present for this user
        if (emailId != null && emailId.trim().length() > 0) {
            mailManagerService.sendSyncEmail(ncitTerm, emailId , preferredName 
                    , userName, displayName);
        }
    }
    
    /**
     * This method retrieves parent list recursivly
     * 
     * @param ncitCode
     * @param parentDtos
     * @param identifier
     * @param parents
     * @throws Exception
     */
    private void fetchParents(String ncitCode,
            List<PDQDiseaseParentDTO> parentDtos, Ii identifier,
            List<PDQDiseaseParentDTO> parents) throws Exception {
        List<String> ncitCodesList = new NCItTermsLookup().fetchTree(ncitCode,
                true);

        parents.addAll(diseaseParentService.getByChildDisease(identifier));

        // this is to avoid duplicates
        Set<String> ncitCodesSet = new HashSet<String>(ncitCodesList);
        for (String parentCode : ncitCodesSet) {
            PDQDiseaseDTO parent = null;

            parent = getExistingDisease(parentCode);
            if (parent == null) {
                parent = retrieveAndSaveMissingTerm(parentCode);
            }
            PDQDiseaseParentDTO p = new PDQDiseaseParentDTO();
            p.setParentDiseaseCode(StConverter
                    .convertToSt(PARENT_DISEASE_CODE_ISA));
            p.setParentDiseaseIdentifier(parent.getIdentifier());
            p.setStatusCode(CdConverter
                    .convertToCd(ActiveInactivePendingCode.ACTIVE));
            p.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil
                    .dateStringToTimestamp(PAUtil.today())));
            p.setDiseaseIdentifier(identifier);
            parentDtos.add(p);
            PDQDiseaseDTO currDisease = getExistingDisease(parentCode);
            // System.out.println("The parent code here--->" + parentCode);
            fetchParents(parentCode, parentDtos, currDisease.getIdentifier(),
                    parents);
        }
    }

    /**
     * This method retrieves children list recursivly
     * 
     * @param ncitCode
     * @param childDtos
     * @param identifier
     * @param childs
     * @throws Exception
     */
    private void fetchChildrens(String ncitCode,
            List<PDQDiseaseParentDTO> childDtos, Ii identifier,
            List<PDQDiseaseParentDTO> childs) throws Exception {
        List<String> ncitCodesList = new NCItTermsLookup().fetchTree(ncitCode,
                false);

        childs.addAll(diseaseParentService.getByParentDisease(identifier));
        // this is to avoid duplicates
        Set<String> ncitCodesSet = new HashSet<String>(ncitCodesList);
        for (String childCode : ncitCodesSet) {
            PDQDiseaseDTO child = null;

            child = getExistingDisease(childCode);
            if (child == null) {
                child = retrieveAndSaveMissingTerm(childCode);
            }
            PDQDiseaseParentDTO c = new PDQDiseaseParentDTO();
            c.setDiseaseIdentifier(child.getIdentifier());
            c.setParentDiseaseCode(StConverter
                    .convertToSt(PARENT_DISEASE_CODE_ISA));
            c.setStatusCode(CdConverter
                    .convertToCd(ActiveInactivePendingCode.ACTIVE));
            c.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil
                    .dateStringToTimestamp(PAUtil.today())));

            PDQDiseaseDTO currDisease = getExistingDisease(childCode);
            c.setParentDiseaseIdentifier(identifier);
            childDtos.add(c);

            fetchChildrens(childCode, childDtos, currDisease.getIdentifier(),
                    childs);
        }
    }

    /**
     * This method save entire parent child and child tree from Ncit if not
     * already exists
     * 
     * @param currDisease
     * @throws PAException
     */
    private void saveParentChilds(PDQDiseaseDTO currDisease) throws PAException {

        try {
            List<PDQDiseaseParentDTO> parents = new ArrayList<PDQDiseaseParentDTO>();

            List<PDQDiseaseParentDTO> childs = new ArrayList<PDQDiseaseParentDTO>();

            List<PDQDiseaseParentDTO> parentDtos = new ArrayList<PDQDiseaseParentDTO>();
            List<PDQDiseaseParentDTO> childDtos = new ArrayList<PDQDiseaseParentDTO>();

            fetchParents(disease.getNtTermIdentifier(), parentDtos,
                    currDisease.getIdentifier(), parents);
            fetchChildrens(disease.getNtTermIdentifier(), childDtos,
                    currDisease.getIdentifier(), childs);

            // this is moved to separate method because either all changes
            // should be done or none
            diseaseParentService.syncDisease(currDisease,
                    parents, childs, parentDtos, childDtos);

        } catch (Exception e) {
            throw new PAException(e.getMessage());
        }
    }

    /**
     * Get the list of existing parents and children
     *
     * @param existingDiseaseDto
     * @throws PAException
     */
    private void getExistingParentsAndChildrenForDisease(PDQDiseaseDTO existingDiseaseDto, DiseaseWebDTO disc)
            throws PAException {
        // Get parent & children terms
        List<PDQDiseaseParentDTO> parents = diseaseParentService.getByChildDisease(existingDiseaseDto.getIdentifier());
        if (parents != null && !parents.isEmpty()) {
            disc.getParentTermList().clear();
            for (Iterator<PDQDiseaseParentDTO> iterator = parents.iterator(); iterator.hasNext();) {
                PDQDiseaseDTO parent = diseaseService.get(iterator.next().getParentDiseaseIdentifier());
                disc.getParentTermList().add(
                   ((parent.getNtTermIdentifier().getValue() != null)?parent.getNtTermIdentifier().getValue() : "")
                   + ": " + parent.getPreferredName().getValue());
            }

        }

        List<PDQDiseaseParentDTO> children = diseaseParentService
                .getByParentDisease(existingDiseaseDto.getIdentifier());
        if (parents != null && !children.isEmpty()) {
            disc.getChildTermList().clear();
            for (Iterator<PDQDiseaseParentDTO> iterator = children.iterator(); iterator.hasNext();) {
                PDQDiseaseDTO child = diseaseService.get(iterator.next().getDiseaseIdentifier());
                disc.getChildTermList().add(
                        ((child.getNtTermIdentifier().getValue() != null)?child.getNtTermIdentifier().getValue() : "") 
                        + ": " + child.getPreferredName().getValue());
            }

        }

    }

    /**
     * Validate data entered in the disease form
     */
    private boolean validateDisease() {
        boolean valid = true;
        if (StringUtils.isEmpty(disease.getNtTermIdentifier())) {
            addFieldError("disease.ntTermIdentifier", getText("manageTerms.fieldError.ntTermIdentifier"));
            valid = false;
        }

        if (StringUtils.isEmpty(disease.getPreferredName())) {
            addFieldError("disease.preferredName", getText("manageTerms.fieldError.name"));
            valid = false;
        }

        if (StringUtils.isEmpty(disease.getMenuDisplayName())) {
            addFieldError("disease.menuDisplayName", getText("manageTerms.fieldError.menuDisplayName"));
            valid = false;
        }
        return valid;
    }

    /**
     * Create a well formatted missing terms error message for display in the UI
     *
     * @param missingTerms
     *            missing terms list
     * @return error message
     */
    private String createTermsMissingErrorMessage(List<String> missingTerms) {
        StringBuilder errorMsg = new StringBuilder();
        for (Iterator<String> iterator = missingTerms.iterator(); iterator.hasNext();) {
            String term = iterator.next();
            errorMsg.append(term);
            if (iterator.hasNext()) {
                errorMsg.append(", ");
            }
        }
        return errorMsg.toString();
    }

    /**
     * Search for existing disease by NCIt Id
     *
     * @param ncitId
     * @return matched intervention DTO, <code>null</code> if no match was found
     * @throws PAException
     */
    private PDQDiseaseDTO getExistingDisease(String ncitId) throws PAException {
        PDQDiseaseDTO searchCriteria = new PDQDiseaseDTO();
        searchCriteria.setNtTermIdentifier(StConverter.convertToSt(ncitId));
        List<PDQDiseaseDTO> searchResults = diseaseService.search(searchCriteria);
        if (!searchResults.isEmpty()) {
            return searchResults.get(0);
        }
        return null;
    }

    /**
     * Retrieve and save a term into CTRP. This terms relationships wont be
     * saved.
     *
     * @param ncitCode
     * @return created disease term reference
     * @throws PAException
     * @throws LEXEVSLookupException
     */
    private PDQDiseaseDTO retrieveAndSaveMissingTerm(String ncitCode) throws LEXEVSLookupException, PAException {
        DiseaseWebDTO disc = new NCItTermsLookup().lookupDisease(ncitCode);
        PDQDiseaseDTO diseaseDto = new PDQDiseaseDTO();
        diseaseDto.setNtTermIdentifier(StConverter.convertToSt(disc.getNtTermIdentifier()));
        diseaseDto.setPreferredName(StConverter.convertToSt(disc.getPreferredName()));
        diseaseDto.setDisplayName(StConverter.convertToSt(disc.getPreferredName()));
        diseaseDto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
        diseaseDto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil.today())));
        diseaseDto = diseaseService.create(diseaseDto);
        // Save alter names
        if (disc.getAlterNameList() != null) {
            for (String altName : disc.getAlterNameList()) {
                PDQDiseaseAlternameDTO altDto = new PDQDiseaseAlternameDTO();
                altDto.setAlternateName(StConverter.convertToSt(altName));
                altDto.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                altDto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(PAUtil.today())));
                altDto.setDiseaseIdentifier(diseaseDto.getIdentifier());
                diseaseAltNameService.create(altDto);
            }
        }
        return diseaseDto;
    }

    /**
     * @return the disease
     */
    public DiseaseWebDTO getDisease() {
        return disease;
    }

    /**
     * @param disease
     *            the disease to set
     */
    public void setDisease(DiseaseWebDTO disease) {
        this.disease = disease;
    }

    /**
     * @return the currentDisease
     */
    public DiseaseWebDTO getCurrentDisease() {
        return currentDisease;
    }

    /**
     * @param currentDisease
     *            the currentDisease to set
     */
    public void setCurrentDisease(DiseaseWebDTO currentDisease) {
        this.currentDisease = currentDisease;
    }

    // Ajax actions

    /**
     * Get disease details ajax action
     * 
     * @return view
     * @throws PAException
     *             PAException
     */
    public String ajaxGetDiseases() throws PAException {
        String ids = ServletActionContext.getRequest().getParameter("diseaseIds");
        StringBuilder result = new StringBuilder();
        if (ids != null && ids.length() != 0) {
            String[] diseaseIds = ids.split(",");
            for (int i = 0; i < diseaseIds.length; i++) {
                PDQDiseaseDTO dis = diseaseService.get(IiConverter.convertToIi(Long.parseLong(diseaseIds[i])));
                if (dis != null) {
                    result.append(dis.getIdentifier().getExtension() + ":" 
                        + ((dis.getNtTermIdentifier().getValue() != null) ? dis.getNtTermIdentifier().getValue() 
                                : "") + ":" + dis.getPreferredName().getValue());
                    if (i != diseaseIds.length) {
                        result.append('\n');
                    }
                }
            }
        }
        ajaxResponseStream = new ByteArrayInputStream(result.toString().getBytes());
        return AJAX_RESPONSE;
    }
    


    /** 
     * Search disease asynchronously and return JSON result 
     * @return ajaxrespose
     * @throws PAException exception
     * @throws JSONException exception
     */
    public String ajaxDiseaseSearch() throws PAException, JSONException {
        List<PDQDiseaseNode> result = null;
        if (!StringUtils.isEmpty(diseaseSearchTerm)) {
            PDQDiseaseDTO criteria = new PDQDiseaseDTO();
            criteria.setPreferredName(StConverter.convertToSt(diseaseSearchTerm));
            criteria.setIncludeSynonym(StConverter.convertToSt(String.valueOf(searchSynonyms)));
            criteria.setExactMatch(StConverter.convertToSt(String.valueOf(exactSearch)));
             result = diseaseService.weightedSearchDisease(criteria);
        }
        ajaxResponseStream = new ByteArrayInputStream(JSONUtil.serialize(result).toString().getBytes());
        return AJAX_RESPONSE;
    }
    
    /**
     * @return the ajaxResponseStream
     */
    public InputStream getAjaxResponseStream() {
        return ajaxResponseStream;
    }

    /**
     * @param ajaxResponseStream
     *            the ajaxResponseStream to set
     */
    public void setAjaxResponseStream(InputStream ajaxResponseStream) {
        this.ajaxResponseStream = ajaxResponseStream;
    }

    /**
     * Removes terms with null NCIt code
     * @param list
     */
    private void removeTermsWithNullNCItCode(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String term = list.get(i);
            if (StringUtils.isNumeric(term)) {
                list.remove(term);
            }
            
        }
    }

    /**
     * @return the diseaseSearchTerm
     */
    public String getDiseaseSearchTerm() {
        return diseaseSearchTerm;
    }

    /**
     * @param diseaseSearchTerm the diseaseSearchTerm to set
     */
    public void setDiseaseSearchTerm(String diseaseSearchTerm) {
        this.diseaseSearchTerm = diseaseSearchTerm;
    }

    /**
     * @return the searchSynonyms
     */
    public boolean isSearchSynonyms() {
        return searchSynonyms;
    }

    /**
     * @param searchSynonyms the searchSynonyms to set
     */
    public void setSearchSynonyms(boolean searchSynonyms) {
        this.searchSynonyms = searchSynonyms;
    }
    /**
     * 
     * @return pageDiscriminator pageDiscriminator
     */
    public String getPageDiscriminator() {
        return pageDiscriminator;
    }
    /**
     * 
     * @param pageDiscriminator pageDiscriminator
     */
    public void setPageDiscriminator(String pageDiscriminator) {
        this.pageDiscriminator = pageDiscriminator;
    }

    /**
     * @return newAltnames
     */
    public List<String> getNewAltnames() {
        return newAltnames;
    }

    /**
     * @param newAltnames newAltnames
     */
    public void setNewAltnames(List<String> newAltnames) {
        this.newAltnames = newAltnames;
    }

    /**
     * @return currentAltnames
     */
    public List<String> getCurrentAltnames() {
        return currentAltnames;
    }

    /**
     * @param currentAltnames currentAltnames
     */
    public void setCurrentAltnames(List<String> currentAltnames) {
        this.currentAltnames = currentAltnames;
    }

    /**
     * @return exactSearch
     */
    public boolean isExactSearch() {
        return exactSearch;
    }

    /**
     * @param exactSearch exactSearch
     */
    public void setExactSearch(boolean exactSearch) {
        this.exactSearch = exactSearch;
    }
    
    
   
    
    
}

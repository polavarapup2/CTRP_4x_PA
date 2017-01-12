/**
 * 
 */
package gov.nih.nci.pa.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.StudyIdentifierDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StudyIdentifierType;
import gov.nih.nci.pa.interceptor.PreventTrialEditingInterceptor;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtilsRemote;
import gov.nih.nci.pa.service.util.PAServiceUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * Various utility methods shared by action classes.
 * @author Denis G. Krylov
 *
 */
public final class ActionUtils {
    
    
    /**
     * Checks whether one of user's roles is in session.
     * @param session HttpSession
     * @return boolean
     */
    public static boolean isUserRoleInSession(HttpSession session) {
        boolean isReportViewer = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_REPORT_VIEWER));
        boolean isSecurityAdmin = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_SECURITY_ADMIN));
        return isAbstractor(session) || isReportViewer || isSecurityAdmin;
    }   
    
    /**
     * Checks whether one of user's roles is in session.
     * 
     * @param session
     *            HttpSession
     * @return boolean
     */
    public static boolean isAbstractor(HttpSession session) {
        boolean isSuAbstractor = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_SU_ABSTRACTOR));
        boolean isAbstractor = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_ABSTRACTOR));
        boolean isAdminAbstractor = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_ADMIN_ABSTRACTOR));
        boolean isScientificAbstractor = BooleanUtils
                .toBoolean((Boolean) session
                        .getAttribute(Constants.IS_SCIENTIFIC_ABSTRACTOR));
        boolean isResultsAbstractor = BooleanUtils
                .toBoolean((Boolean) session
                        .getAttribute(Constants.IS_RESULTS_ABSTRACTOR));
        return isAbstractor || isSuAbstractor || isScientificAbstractor
                || isAdminAbstractor || isResultsAbstractor;
    }  
    
    /**
     * Self-descriptive. The method will sort the passed-in {@link List} (without creating a new one) and will return
     * the same for convenience of invocation chaining. 
     * 
     * @param records
     *            records
     * @return List<StudyProtocolQueryDTO>
     */
    public static List<StudyProtocolQueryDTO> sortTrialsByNciId(List<StudyProtocolQueryDTO> records) {
        if (CollectionUtils.isNotEmpty(records)) {
            Collections.sort(records, new Comparator<StudyProtocolQueryDTO>() {
                @Override
                public int compare(StudyProtocolQueryDTO o1,
                        StudyProtocolQueryDTO o2) {
                    return StringUtils.defaultString(o2.getNciIdentifier())
                            .compareTo(
                                    StringUtils.defaultString(o1
                                            .getNciIdentifier()));
                }
            });
        }
        return records;
    }

    /**
     * @param request HttpServletRequest
     */
    public static void setUserRolesInSession(final HttpServletRequest request) {
        boolean isAbstractor = request.isUserInRole(Constants.ABSTRACTOR);
        boolean isSuAbstractor = request.isUserInRole(Constants.SUABSTRACTOR);
        boolean isScientificAbstractor =
            request.isUserInRole(Constants.SCIENTIFIC_ABSTRACTOR);
        boolean isAdminAbstractor = request.isUserInRole(Constants.ADMIN_ABSTRACTOR);
        boolean isReportViewer = request.isUserInRole(Constants.REPORT_VIEWER);
        boolean isSecAdmin = request.isUserInRole(Constants.SECURITY_ADMIN);
        final HttpSession session = request.getSession();
        session.setAttribute(Constants.IS_ABSTRACTOR, isAbstractor);
        session.setAttribute(Constants.IS_SU_ABSTRACTOR, isSuAbstractor);
        session.setAttribute(Constants.IS_ADMIN_ABSTRACTOR, isAdminAbstractor);
        session.setAttribute(Constants.IS_SCIENTIFIC_ABSTRACTOR,
                isScientificAbstractor);
        session.setAttribute(Constants.IS_REPORT_VIEWER, isReportViewer);
        session.setAttribute(Constants.IS_SECURITY_ADMIN, isSecAdmin);
        session.setAttribute(Constants.IS_RESULTS_ABSTRACTOR, request
                .isUserInRole(Constants.RESULTS_ABSTRACTOR));
        session.setAttribute(Constants.IS_ANY_ABSTRACTOR, isAbstractor(session));
    }
    
    
    /**
     * @param spqDTO spqDTO
     * @param checkoutCommands checkoutCommands
     */
    public static void setCheckoutCommands(StudyProtocolQueryDTO spqDTO,
            List<String> checkoutCommands) {
        HttpSession session = ServletActionContext.getRequest().getSession();
        boolean suAbs = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_SU_ABSTRACTOR));
        setAdminCheckoutCommands(spqDTO, suAbs, checkoutCommands);
        setScientificCheckoutCommands(spqDTO, suAbs, checkoutCommands);
        setSuperAbstractorCommands(spqDTO, suAbs, checkoutCommands);
    }

    /**
     * Super abstractors have a button that does both check-outs at the same
     * time.
     * 
     * @param spqDTO
     * @param suAbs
     * @param checkoutCommands
     * @see https://tracker.nci.nih.gov/browse/PO-3966
     */
    private static void setSuperAbstractorCommands(
            StudyProtocolQueryDTO spqDTO, boolean suAbs,
            List<String> checkoutCommands) {
        // for both uses by this super abstractor.
        if (spqDTO.getAdminCheckout().getCheckoutBy() != null && suAbs) {
            checkoutCommands.add("adminAndScientificCheckIn");
        } else if (suAbs) {
            checkoutCommands.add("adminAndScientificCheckOut");
        }
    }

    private static void setAdminCheckoutCommands(StudyProtocolQueryDTO spqDTO,
            boolean suAbs, List<String> checkoutCommands) {
        if (spqDTO.getAdminCheckout().getCheckoutBy() != null) {
            if (spqDTO.getAdminCheckout().getCheckoutBy()
                    .equalsIgnoreCase(UsernameHolder.getUser())
                    || suAbs) {
                checkoutCommands.add("adminCheckIn");
            }
        } else {
            HttpSession session = ServletActionContext.getRequest()
                    .getSession();
            boolean adminAbs = BooleanUtils.toBoolean((Boolean) session
                    .getAttribute(Constants.IS_ADMIN_ABSTRACTOR));
            if (adminAbs || suAbs) {
                checkoutCommands.add("adminCheckOut");
            }
        }
    }

    private static void setScientificCheckoutCommands(
            StudyProtocolQueryDTO spqDTO, boolean suAbs,
            List<String> checkoutCommands) {
        if (spqDTO.getScientificCheckout().getCheckoutBy() != null) {
            if (spqDTO.getScientificCheckout().getCheckoutBy()
                    .equalsIgnoreCase(UsernameHolder.getUser())
                    || suAbs) {
                checkoutCommands.add("scientificCheckIn");
            }
        } else {
            HttpSession session = ServletActionContext.getRequest()
                    .getSession();
            boolean scAbs = BooleanUtils.toBoolean((Boolean) session
                    .getAttribute(Constants.IS_SCIENTIFIC_ABSTRACTOR));
            if (scAbs || suAbs) {
                checkoutCommands.add("scientificCheckOut");
            }
        }
    }
    
    
    /**
     * @param studyProtocolQueryDTO studyProtocolQueryDTO
     * @param correlationUtils correlationUtils
     * @param paServiceUtils paServiceUtils
     * @throws PAException PAException
     */
    @SuppressWarnings({ "PMD.ExcessiveMethodLength" })
    public static void loadProtocolDataInSession(
            StudyProtocolQueryDTO studyProtocolQueryDTO,
            CorrelationUtilsRemote correlationUtils,
            PAServiceUtils paServiceUtils) throws PAException {
        HttpSession session = ServletActionContext.getRequest().getSession();            
        // put an entry in the session and store StudyProtocolQueryDTO
        studyProtocolQueryDTO.setOfficialTitle(studyProtocolQueryDTO.getOfficialTitle());
        if (studyProtocolQueryDTO.getPiId() != null) {
            Person piPersonInfo =
                    correlationUtils.getPAPersonByIi(IiConverter.convertToPaPersonIi(studyProtocolQueryDTO
                            .getPiId()));
            session.setAttribute(Constants.PI_PO_ID, piPersonInfo.getIdentifier());
        }
        
        session.setAttribute(Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
        final Ii spID = IiConverter.convertToStudyProtocolIi(studyProtocolQueryDTO.getStudyProtocolId());
        session.setAttribute(Constants.STUDY_PROTOCOL_II,
                spID);
        // When the study protocol is selected, set its token to be the current time in milliseconds.
        session.setAttribute(PreventTrialEditingInterceptor.STUDY_PROTOCOL_TOKEN, PreventTrialEditingInterceptor
            .generateToken());
        String loginName = UsernameHolder.getUser();
        session.setAttribute(Constants.LOGGED_USER_NAME, loginName);
         
        session.setAttribute("nctIdentifier", paServiceUtils.getStudyIdentifier(spID, PAConstants.NCT_IDENTIFIER_TYPE));
        if (!studyProtocolQueryDTO.isProprietaryTrial()) {
            session.setAttribute("dcpIdentifier", paServiceUtils
                    .getStudyIdentifier(spID,
                            PAConstants.DCP_IDENTIFIER_TYPE));
            session.setAttribute("ctepIdentifier", paServiceUtils
                    .getStudyIdentifier(spID,
                            PAConstants.CTEP_IDENTIFIER_TYPE));
        }
        
        session.setAttribute(Constants.TRIAL_SUBMITTER_ORG_PO_ID, studyProtocolQueryDTO.getSubmitterOrgId());
        session.setAttribute(Constants.TRIAL_SUBMITTER_ORG, studyProtocolQueryDTO.getSubmitterOrgName());
        
        final List<StudyIdentifierDTO> studyIdentifiers = PaRegistry
                .getStudyIdentifiersService().getStudyIdentifiers(spID);
        session.setAttribute(Constants.STUDY_IDENTIFIERS, studyIdentifiers);
        
        prepareListOfIdentifiersGroupedByType(studyIdentifiers, session);
        runTrialStatusTransitionValidations(studyProtocolQueryDTO, session);
    }

    /**
     * @param spqDTO StudyProtocolQueryDTO
     * @param session HttpSession
     * @throws PAException PAException
     */
    public static void runTrialStatusTransitionValidations(
            StudyProtocolQueryDTO spqDTO, HttpSession session) throws PAException {
        final Ii spID = IiConverter.convertToStudyProtocolIi(spqDTO
                .getStudyProtocolId());
        // Only bother with transition validation if the trial is checked out.
        if (spqDTO.getAdminCheckout().getCheckoutBy() != null
                || spqDTO.getScientificCheckout().getCheckoutBy() != null) {
            session.setAttribute(Constants.TRIAL_HAS_STATUS_ERRORS, PaRegistry
                    .getStudyOverallStatusService()
                    .statusHistoryHasErrors(spID));
            session.setAttribute(Constants.TRIAL_HAS_STATUS_WARNINGS,
                    PaRegistry.getStudyOverallStatusService()
                            .statusHistoryHasWarnings(spID));
        }
    }

    private static void prepareListOfIdentifiersGroupedByType(
            List<StudyIdentifierDTO> studyIdentifiers, HttpSession session) {
        Map<StudyIdentifierType, String> map = new LinkedHashMap<StudyIdentifierType, String>();
        session.setAttribute(Constants.STUDY_IDENTIFIERS_GROUPED_BY_TYPE, map);
        for (StudyIdentifierDTO dto : studyIdentifiers) {
            if (StringUtils.isNotBlank(dto.getValue())) {
                String values = StringUtils
                        .defaultString(map.get(dto.getType()));
                values += (values.isEmpty() ? dto.getValue() : ", " // NOPMD
                        + dto.getValue());
                map.put(dto.getType(), values);
            }
        }

    }
    
    /**
     * Compare list1 to list2 and generate a html stirng highlighting the terms missing in list2
     * @param list1 list1  
     * @param list2 list2
     * @return html string with missing terms highlighted
     */
    public static String generateListDiffStringForManageTerms(List<String> list1, List<String> list2) {
        StringBuilder result = new StringBuilder();
        Collections.sort(list1);
        Collections.sort(list2);
        for (Iterator<String> iterator = list1.iterator(); iterator.hasNext();) {
            String item = iterator.next();
            if (list2.contains(item)) {
                result.append(item);
                result.append("<br>");
            }
           
           
        }
        
        for (Iterator<String> iterator = list1.iterator(); iterator.hasNext();) {
            String item = iterator.next();
            
            if (!list2.contains(item)) {
                result.append("<font color='red'><strong>").append(item).append("</strong></font>");   
                result.append("<br>");
            } 
           
        }
        return result.toString();
    }

}

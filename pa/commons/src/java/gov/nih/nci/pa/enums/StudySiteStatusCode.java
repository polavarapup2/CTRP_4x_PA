/**
 * 
 */
package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Vrushali
 * 
 */
public enum StudySiteStatusCode implements CodedEnum<String> {
    /**
     * In Review.
     */
    IN_REVIEW("In Review"),
    /**
     * In Review.
     */
    DISAPPROVED("Disapproved"),
    /**
     * Approved.
     */
    APPROVED("Approved"),
    /**
     * Active.
     */
    ACTIVE("Active"),
    /**
     * Closed to Accrual.
     */
    CLOSED_TO_ACCRUAL("Closed to Accrual"),
    /**
     * Closed To Accrual And Intervention.
     */
    CLOSED_TO_ACCRUAL_AND_INTERVENTION("Closed to Accrual and Intervention"),
    /**
     * Temporarily Closed To Accrual.
     */
    TEMPORARILY_CLOSED_TO_ACCRUAL("Temporarily Closed to Accrual"),
    /**
     * Temporarily Closed To Accrual and Intervention.
     */
    TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION("Temporarily Closed to Accrual and Intervention"),
    /**
     * Withdrawn.
     */
    WITHDRAWN("Withdrawn"),
    /**
     * Administratively Complete.
     */
    ADMINISTRATIVELY_COMPLETE("Administratively Complete"),
    /**
     * Complete.
     */
    COMPLETE("Complete");

    private String code;

    /**
     * Constructor for TrialStatusCode.
     * @param code
     */

    private StudySiteStatusCode(String code) {
        this.code = code;
        register(this);
    }

    /**
     * @return code coded value of enum
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * @return String DisplayName
     */
    @Override
    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    /**
     * @return String display name
     */
    public String getName() {
        return name();
    }

    /**
     * @param code code
     * @return TrialStatusCode
     */
    public static StudySiteStatusCode getByCode(String code) {
        return getByClassAndCode(StudySiteStatusCode.class, code);
    }

    /**
     * construct a array of display names for Study Status coded Enum.
     * @return String[] display names for StudySiteStatusCode
     */
    public static String[] getDisplayNames() {
        StudySiteStatusCode[] studySiteStatusCodes = StudySiteStatusCode.values();
        String[] codedNames = new String[studySiteStatusCodes.length];
        for (int i = 0; i < studySiteStatusCodes.length; i++) {
            codedNames[i] = studySiteStatusCodes[i].getCode();
        }
        return codedNames;
    }

    private static final Map<StudySiteStatusCode, Set<StudySiteStatusCode>> TRANSITIONS;

    static {
        Map<StudySiteStatusCode, Set<StudySiteStatusCode>> tmp =
                new HashMap<StudySiteStatusCode, Set<StudySiteStatusCode>>();

        Set<StudySiteStatusCode> tmpSet = new HashSet<StudySiteStatusCode>();
        tmpSet.add(APPROVED);
        tmpSet.add(DISAPPROVED);
        tmp.put(IN_REVIEW, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<StudySiteStatusCode>();
        tmp.put(DISAPPROVED, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<StudySiteStatusCode>();
        tmpSet.add(ACTIVE);
        tmpSet.add(WITHDRAWN);
        tmp.put(APPROVED, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<StudySiteStatusCode>();
        tmp.put(WITHDRAWN, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<StudySiteStatusCode>();
        tmpSet.add(TEMPORARILY_CLOSED_TO_ACCRUAL);
        tmpSet.add(TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        tmpSet.add(ADMINISTRATIVELY_COMPLETE);
        tmpSet.add(CLOSED_TO_ACCRUAL);
        tmp.put(ACTIVE, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<StudySiteStatusCode>();
        tmpSet.add(ACTIVE);
        tmpSet.add(TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        tmpSet.add(ADMINISTRATIVELY_COMPLETE);
        tmp.put(TEMPORARILY_CLOSED_TO_ACCRUAL, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<StudySiteStatusCode>();
        tmpSet.add(ACTIVE);
        tmpSet.add(ADMINISTRATIVELY_COMPLETE);
        tmp.put(TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<StudySiteStatusCode>();
        tmp.put(ADMINISTRATIVELY_COMPLETE, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<StudySiteStatusCode>();
        tmpSet.add(CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        tmpSet.add(ADMINISTRATIVELY_COMPLETE);
        tmp.put(CLOSED_TO_ACCRUAL, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<StudySiteStatusCode>();
        tmpSet.add(ADMINISTRATIVELY_COMPLETE);
        tmpSet.add(COMPLETE);
        tmp.put(CLOSED_TO_ACCRUAL_AND_INTERVENTION, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<StudySiteStatusCode>();
        tmp.put(COMPLETE, Collections.unmodifiableSet(tmpSet));

        TRANSITIONS = Collections.unmodifiableMap(tmp);
    }

    /**
     * Helper method that indicates whether a transition to the new entity status is allowed.
     * 
     * @param newStatus transition to status
     * @return whether the transition is allowed
     */
    public boolean canTransitionTo(StudySiteStatusCode newStatus) {
        return TRANSITIONS.get(this).contains(newStatus);
    }

}

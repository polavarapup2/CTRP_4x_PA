package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * This class contains an enumeration for recording where the study was created in the system.
 * @author Dirk
 *
 */
public enum StudySourceCode implements CodedEnum<String> {
    
    /**
     * Trials registered through the batch upload interface.
     */
    BATCH("Batch"),
    
    /**
     * Trials registered though the registry
     */
    REGISTRY("Registry"),
    
    /**
     * Trials that where imported from ClinicalTrials.gov
     */
    CLINICAL_TRIALS_GOV("ClinicalTrials.gov"),
    
    /**
     * Trials entered though PDQ
     */
    PDQ("PDQ"),
    
    /**
     * Trials registered through the grid service.
     */
    GRID_SERVICE("Grid Service"),
    
    /**
     * Trials registered through the REST service.
     */
    REST_SERVICE("REST Service"),
    
    /**
     * Trials from PA not otherwise indicated.
     */
    OTHER("Other");
    
    private String code;
    
    /**
     * Constructor
     * @param name the name of the code
     */
    private StudySourceCode(String name) {
        code = name;
        register(this);
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDisplayName() {
        return sentenceCasedName(this);
    }
    
    /**
     * Returns the name of the study source
     * @return String name the name
     */
    public String getName() {
        return name();
    }

    /**
     * It returns the enum by the given code
     * @param code code the code to look up
     * @return the Code for the study source
     */
    public static StudySourceCode getByCode(String code) {
        return getByClassAndCode(StudySourceCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[] getDisplayNames() {
        StudySourceCode[] l = StudySourceCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }

}

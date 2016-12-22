package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;

/**
 * 
 * @author Reshma Koganti
 * 
 */
public enum EvaluationTypeCode implements CodedEnum<String> {
    /**
     * Level/Quantity.
     */
    LEVEL_QUANTITY("Level/Quantity"),

    /**
     * Genetic Analysis.
     */
    GENETIC_ANALYSIS("Genetic Analysis"),

    /**
     * Cell Functionality.
     */
    CELL_FUNCTIONALITY("Cell Functionality"),
    /**
     * Subtyping.
     */
    SUBTYPING("Subtyping"),
    /**
     * Protein Activity.
     */
    PROTEIN_ACTIVITY("Protein Activity"),
    /**
     * Proteolytic Cleavage.
     */
    PROTEOLYTIC_CLEAVAGE("Proteolytic Cleavage"),
    /**
     * Phosphorylation.
     */
    PHOSPHORYLATION("Phosphorylation"),

    /**
     * Methylation.
     */
    METHYLATION("Methylation"),
    /**
     * Acetylation.
     */
    ACETYLATION("Acetylation"),

    /**
     * Other.
     */
    OTHER("Other");

    private String code;

    /**
     * Constructor for assay type code.
     * 
     * @param code
     */
    private EvaluationTypeCode(String code) {
        this.code = code;
        register(this);
    }

    /**
     * @return the coded value of the enum
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayName() {
        return getCode();
    }

    /**
     * @return the display name
     */
    public String getName() {
        return name();
    }

    /**
     * @param code
     *            the code
     * @return the assay type with the given code
     */
    public static EvaluationTypeCode getByCode(String code) {
        return getByClassAndCode(EvaluationTypeCode.class, code);
    }

    /**
     * @return display names for assay type codes
     */
    public static String[] getDisplayNames() {
        EvaluationTypeCode[] codes = EvaluationTypeCode.values();
        String[] codedNames = new String[codes.length];
        for (int i = 0; i < codes.length; i++) {
            codedNames[i] = codes[i].getCode();
        }
        return codedNames;
    }

}

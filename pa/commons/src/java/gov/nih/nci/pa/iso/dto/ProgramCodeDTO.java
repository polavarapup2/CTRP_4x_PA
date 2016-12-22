package gov.nih.nci.pa.iso.dto;



import java.io.Serializable;
import java.util.Objects;

/**
 * Will represent a ProgramCode
 */
public class ProgramCodeDTO implements Serializable {
    private static final long serialVersionUID = 235503630643839412L;
    private static final int THIRTYONE = 31;
    private Long id;
    private String programCode;
    private String programName;
    private boolean active;

    /**
     * Creates a ProgramCodeDTO
     */
    public ProgramCodeDTO() {
        this(null, null, null, true);
    }

    /**
     * Creates a ProgramCodeDTO with given id
     * @param id ID
     */
    public ProgramCodeDTO(Long id) {
       this(id, null, null, true);
    }

    /**
     * Creates a ProgramCodeDTO
     * @param id  ID
     * @param programCode the code
     */
    public ProgramCodeDTO(Long id, String programCode) {
        this(id, programCode, null, true);
    }

    /**
     * Creates a ProgramCodeDTO
     * @param id  ID
     * @param programCode  code
     * @param programName  name
     */
    public ProgramCodeDTO(Long id, String programCode, String programName) {
        this(id, programCode, programName, true);
    }

    /**
     * Creates a ProgramCodeDTO
     * @param id  id
     * @param programCode code
     * @param programName  name
     * @param active  active flag
     */
    public ProgramCodeDTO(Long id, String programCode, String programName, boolean active) {
        this.id = id;
        this.programCode = programCode;
        this.programName = programName;
        this.active = active;
    }

    /**
     * Gets the id
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id
     * @param id - the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the programCode
     * @return - the programCode
     */
    public String getProgramCode() {
        return programCode;
    }

    /**
     * Sets the programCode
     * @param programCode the programCode
     */
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    /**
     * Gets the programName
     * @return the programName
     */
    public String getProgramName() {
        return programName;
    }

    /**
     * Sets the programName
     * @param programName the programName
     */
    public void setProgramName(String programName) {
        this.programName = programName;
    }

    /**
     * Gets active status
     * @return  the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active status
     * @param active  the active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Will return the display name
     * @return - the display name
     */
    public String getDisplayName() {
        if (programName == null || programName.equals("")) {
            return programCode;
        }
        return String.format("%s - %s", programCode, programName);
    }

    /**
     * Checks for equality
     * @param o - a ProgramCodeDTO
     * @return  true if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgramCodeDTO)) {
            return false;
        }

        ProgramCodeDTO that = (ProgramCodeDTO) o;
        if (id != null || that.id != null) {
            return Objects.equals(id, that.id);
        }
        return Objects.equals(programCode, that.programCode);

    }

    /**
     * Will find the hashcode
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = THIRTYONE * result + (programCode != null ? programCode.hashCode() : 0);
        return result;
    }
}

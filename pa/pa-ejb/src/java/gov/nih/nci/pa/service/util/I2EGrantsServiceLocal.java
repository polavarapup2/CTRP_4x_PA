package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.service.PAException;

import java.util.List;

import javax.ejb.Local;

/**
 * @author Hugh Reinhart
 * @since Jun 26, 2013
 */
@Local
public interface I2EGrantsServiceLocal {

    /**
     * Nested class to return information from I2E on a single grant.
     */
    static class I2EGrant {
        private String serialNumber;
        private String organizationName;
        private String projectName;
        private String piFirstName;
        private String piLastName;

        /**
         * @return the serialNumber
         */
        public String getSerialNumber() {
            return serialNumber;
        }
        /**
         * @param serialNumber the serialNumber to set
         */
        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }
        /**
         * @return the organizationName
         */
        public String getOrganizationName() {
            return organizationName;
        }
        /**
         * @param organizationName the organizationName to set
         */
        public void setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
        }
        /**
         * @return the projectName
         */
        public String getProjectName() {
            return projectName;
        }
        /**
         * @param projectName the projectName to set
         */
        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }
        /**
         * @return the piFirstName
         */
        public String getPiFirstName() {
            return piFirstName;
        }
        /**
         * @param piFirstName the piFirstName to set
         */
        public void setPiFirstName(String piFirstName) {
            this.piFirstName = piFirstName;
        }
        /**
         * @return the piLastName
         */
        public String getPiLastName() {
            return piLastName;
        }
        /**
         * @param piLastName the piLastName to set
         */
        public void setPiLastName(String piLastName) {
            this.piLastName = piLastName;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuffer result = new StringBuffer(serialNumber);
            result.append(" - ");
            result.append(organizationName);
            result.append("; ");
            result.append(projectName);
            result.append("; ");
            result.append(piFirstName);
            result.append(' ');
            result.append(piLastName);
            return result.toString();
        }
    }

    /**
     * Return all grants with serial numbers beginning with supplied string.
     * @param serialNumber full or partial grant serial number
     * @return list of grants from I2E system
     * @throws PAException exception
     */
    List<I2EGrant> getBySerialNumber(String serialNumber) throws PAException;

    /**
     * Return true if exact match for serial number found in grant view.
     * @param serialNumber full serial number
     * @return true if found
     * @throws PAException exception
     */
    Boolean isValidCaGrant(String serialNumber) throws PAException;
}

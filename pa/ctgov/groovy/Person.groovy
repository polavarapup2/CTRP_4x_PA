/**
 * 
 */

/**
 * @author Denis G. Krylov
 *
 */
class Person implements Comparable<Person> {

    String affiliation, fullName, firstName, middleName, lastName, prefix, suffix

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
        + ((fullName == null) ? 0 : fullName.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Person)) {
            return false;
        }
        Person other = (Person) obj;
        if (fullName == null) {
            if (other.fullName != null) {
                return false;
            }
        } else if (!fullName.equalsIgnoreCase(other.fullName)) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Person o) {
        return fullName.toLowerCase().compareTo(o.fullName.toLowerCase())
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Person [fullName=").append(fullName).append("]");
        return builder.toString();
    }
}

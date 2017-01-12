/**
 * 
 */

/**
 * @author Denis G. Krylov
 *
 */
abstract class Org implements Comparable<Org> {
    
    
    String  name, city, state, zip, country




    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.toLowerCase().hashCode());
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
        if (!(obj instanceof Org)) {
            return false;
        }
        Org other = (Org) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equalsIgnoreCase(other.name)) {
            return false;
        }
        return true;
    }




    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Org o) {
        return name.toLowerCase().compareTo(o.getName().toLowerCase()) 
    }




    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Org [name=").append(name).append("]");
        return builder.toString();
    }

}

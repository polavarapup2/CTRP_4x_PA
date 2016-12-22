import org.apache.commons.lang.StringUtils;

/**
 * 
 */

/**
 * @author Denis G. Krylov
 *
 */
class PdqOrg extends Org {

    String cdrId
    Collection ctepIds = new TreeSet()
    Collection poIds = new TreeSet()

    def addCtepId = {id -> ctepIds.add(id) }
    def addPoId = {id -> poIds.add(id) }

    def getPoIdsString = {StringUtils.join(poIds, ";")}
    def getCtepIdsString = {StringUtils.join(ctepIds, ";")}
}

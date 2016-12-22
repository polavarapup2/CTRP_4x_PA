package gov.nih.nci.registry.util;

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.registry.dto.RegistryUserWebDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * Various functions to return the organization lists needed for populating the registry
 * create trial pages. 
 * @author Dirk Walter
 *
 */
public class FilterOrganizationUtil {
    
    /**
     * the logger to use.
     */
    private static final Logger LOG = Logger.getLogger(FilterOrganizationUtil.class);
    
    /**
     * The name of the organization representing NCI.
     */
    public static final String NCI_NAME = PAConstants.NCI_ORG_NAME;
    
    /**
     * The PO id of the organization representing the Cancer Therapy
     * Evaluation Program.
     */
    public static final String CTEP_NAME = PAConstants.CTEP_ORG_NAME;
    
    /**
     * Get the lead organization for a trial and it's siblings for the current user.
     * @return the list of likely lead organizations.
     */
    public static List<OrgItem> getLeadOrganization() {
        RegistryUserWebDTO user = RegistryUtil.getRegistryUserWebDto(UsernameHolder.getUser());
        return getLeadOrganization(user);
    }
    
    /**
     * Get the lead organization for a trial and it's siblings for the specified user.
     * @param user the user to do the lookup for.
     * @return the list of likely lead organizations.
     */
    public static List<OrgItem> getLeadOrganization(RegistryUserWebDTO user) {
        ArrayList<OrgItem> list = new ArrayList<OrgItem>();
        
        if (user != null && user.getAffiliatedOrganizationId() != null) {
            Organization org = null;
            try {
                org = getOrganizationByPoID(user.getAffiliatedOrganizationId());
                list.add(new OrgItem(org, true));
            } catch (Exception ex) {
                LOG.error("An error occured while fetching the affiliated organization for user;" + user.getId(), ex);
                list.add(new OrgItem(OrgItem.ERROR_TYPE));
            }
            
            
            list.add(getSpacerItem());
            
            getRelatedOrgs(org, list);
        }
        
        return list;
    }
    
    /**
     * List related orgs.
     * @param org the organization whose relatives need to be added
     * @param list the list that the orgs get added to.
     */
    private static void getRelatedOrgs(Organization org, List<OrgItem> list) {
        if (org != null) {
            try {
                final long id = Long.parseLong(org.getIdentifier());
                final List<Long> siblings = FamilyHelper.getAllRelatedOrgs(id);
                final List<Organization> orgList = new ArrayList<Organization>();
                for (long orgId : siblings) {
                    if (id != orgId) {
                        final Organization tempOrg = getOrganizationByPoID(orgId);
                        if (tempOrg != null) {
                            orgList.add(tempOrg);
                        }
                    }
                }
                Collections.sort(orgList, new Comparator<Organization>() {
                    @Override
                    public int compare(Organization o1, Organization o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                for (Organization orgItem : orgList) {
                    list.add(new OrgItem(orgItem));
                }
            } catch (Exception e) {
                //Deliberately add an error to the list in case of error...
                list.add(new OrgItem(OrgItem.ERROR_TYPE));
                
                LOG.error("An error occured while fetching releated organizations.", e);
            }
        }
    }
    
    /**
     * Get the list of likely sponsor or summary 4 sponsor organizations, for the current user.
     * @return The likely List.
     */
    public static List<OrgItem> getSponsorOrganization() {
        RegistryUserWebDTO user = RegistryUtil.getRegistryUserWebDto(UsernameHolder.getUser());
        return getSponsorOrganization(user);
    }
    
    /**
     * Get the list of likely sponsor or summary 4 sponsor organizations, for the specified user.
     * @param user the user whose likely sponsor organizations to fetch.
     * @return The likely List.
     */
    public static List<OrgItem> getSponsorOrganization(RegistryUserWebDTO user) {
        ArrayList<OrgItem> list = new ArrayList<OrgItem>();

        Organization org = null;
        
        if (user != null && user.getAffiliatedOrganizationId() != null) {
            try {
                org = getOrganizationByPoID(user.getAffiliatedOrganizationId());
                list.add(new OrgItem(org, true));
            } catch (Exception ex) {
                LOG.error("An error occured while fetching the affiliated organization for user;" + user.getId(), ex);
                list.add(new OrgItem(OrgItem.ERROR_TYPE));
            }
        }
        
        try {
            list.add(new OrgItem(getOrganizationByName(NCI_NAME)));
            list.add(new OrgItem(getOrganizationByName(CTEP_NAME)));
        } catch (Exception ex) {
            LOG.error("An error occured while fetching the built in organizations.", ex);
        }
        
        list.add(getSpacerItem());
        
        getRelatedOrgs(org, list);
        
        return list;
    }
    
    /**
     * This is a placeholder
     * @return the list of default orgs for an account.
     */
    public static List<OrgItem> getAccountOrganization() {
        ArrayList<OrgItem> list = new ArrayList<OrgItem>();

        try {
            Session session = null;
            session = PaHibernateUtil.getCurrentSession();
            Query query = session.createQuery("select org from Organization org "
                    + "where org.id in (select id from AccountCommonOrganization) order by org.name asc");
    
            @SuppressWarnings("unchecked")
            List<Organization> orgList = query.list();

            for (Organization orgItem : orgList) {
                list.add(new OrgItem(orgItem));
            }

        } catch (Exception ex) {
            LOG.error("An error occured while fetching the common account organizations.", ex);
        }
        
        list.add(getSpacerItem());
        
        return list;
    }
    
    /**
     * Returns the Organization associated with a given PO id.
     * @param orgId the PO organization ID to look for.
     * @return the organization matching that id, or null.
     */
    public static Organization getOrganizationByPoID(long orgId) {
        Session session = null;
        session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery("select org from Organization org  where org.identifier = :poOrg "
                + "and org.statusCode = :code");
        query.setParameter("poOrg", "" + orgId);
        query.setParameter("code", EntityStatusCode.ACTIVE);
        Organization org = (Organization) query.uniqueResult();
        
        if (org == null) {
            query.setParameter("code", EntityStatusCode.PENDING);
            org = (Organization) query.uniqueResult();
        }
        
        return org;
    }
    
    /**
     * Returns the Organization associated with a given name.
     * @param name the organization name to look for.
     * @return the organization matching that name, or null.
     */
    public static Organization getOrganizationByName(String name) {
        Session session = null;
        session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery("select org from Organization org  where org.name = :poOrg "
                + "and org.statusCode = :code");
        query.setParameter("poOrg", name);
        query.setParameter("code", EntityStatusCode.ACTIVE);
        Organization org = (Organization) query.uniqueResult();
        
        if (org == null) {
            query.setParameter("code", EntityStatusCode.PENDING);
            org = (Organization) query.uniqueResult();
        }
        
        return org;
    }
    
    /**
     * Utility function to help populate the list.
     * @return a spacer entry.
     */
    private static OrgItem getSpacerItem() {
        return new OrgItem(OrgItem.SPACER_TYPE);
    }

    /**
     * A temporary class to help with returning the lost of organizations.
     * @author Dirk Walter
     *
     */
    protected static class OrgItem {
        /**
         * This value indicates it is a regular item.
         */
        public static final int NORMAL_TYPE = 1;
        
        /**
         * This one denoted the search item. This number is paired the the
         * javascript to properly trigger
         */
        public static final int SEARCH_TYPE = -1;
        /**
         * The other special value is used to denote blank space.
         */
        public static final int SPACER_TYPE = -2;
        /**
         * Any other negative value will be treated as an error.
         */
        public static final int ERROR_TYPE = -3;
        
        /**
         * The type of element.
         */
        private final int type;
        
        /**
         * the po Id of the organization.
         */
        private final String poId;
        
        /**
         * the ctep ID of the org,
         */
        private final String ctepId;
        
        /**
         * the organization or null if none.
         */
        private final Organization org;
        
        /**
         * if this item should be marked as "(Your Affiliation)"
         */
        private final boolean selfAffiliation;
        
        /**
         * special case constructor for the special values defined above.
         * @param typeId the special value to use.
         */
        public OrgItem(int typeId) {
            if (typeId >= 0) {
                throw new IllegalArgumentException("This constructor is only to be used for special cases.");
            }
            type = typeId;
            
            poId = "-1";
            ctepId = "N/A";
            org = null;
            selfAffiliation = false;
        }
        
        /**
         * the normal constructor takes the organization and does the rest.
         * @param organization the org to represent.
         * @throws PAException if an exception occurs
         * 
         */
        public OrgItem(Organization organization) throws PAException {
            if (organization == null) {
                throw new IllegalArgumentException("The Organization cannot be null.");
            }
            type = NORMAL_TYPE;
            poId = organization.getIdentifier();
            ctepId = getCtepId(poId);
            org = organization;
            selfAffiliation = false;
        }
        
        /**
         * The  normal constructor with the addition to specify the affiliation. 
         * @param organization the org.
         * @param yourAffiliation defaults to false.
         * @throws PAException if an exception occurs
         */
        public OrgItem(Organization organization, boolean yourAffiliation) throws PAException {
            if (organization == null) {
                throw new IllegalArgumentException("The Organization cannot be null.");
            }
            type = NORMAL_TYPE;
            poId = organization.getIdentifier();
            ctepId = getCtepId(poId);
            org = organization;
            selfAffiliation = yourAffiliation;
        }

        /**
         * return the type of response.
         * @return the type.
         */
        public int getType() {
            return type;
        }
        
        /**
         * returns this object's CTEP id.
         * @return the CTEP organization id.
         */
        public String getCtepId() {
            return ctepId;
        }
        
        /**
         * Returns the PO ID of the organization.
         * @return the PO ID.
         */
        public String getPoId() {
            return poId;
        }

        /**
         * Returns the display name of the object fot the list.
         * @return the display name.
         */
        public String getName() {
            if (org == null) {
                if (type == SPACER_TYPE) {
                    return "-----";
                } else if (type == SEARCH_TYPE) {
                    return "Search...";
                }
            } else {
                String name = org.getName();
                if (selfAffiliation) {
                   name += " (Your Affiliation)"; 
                }
                
                return name;
            }
            
            return "Error!";
        }
        
        /**
         * returns the name with html escapes applied.
         * @return the name suitable for html use
         */
        public String getHTMLName() {
            return escapeHtml(getName());
        }
        
        /**
         * Get the name html esscaped and with single quotes escaped.
         * @return the name suitable for js use.
         */
        public String getJSName() {
            return escapeHtml(getName()).replaceAll("'", "\\\\'");
        }
        
        /**
         * gets the CTEP id of the org from the cache or PO if needed.
         * @param poId the organization's po id
         * @return the ctep id
         * @throws PAException problems retrieving the id.
         */
        private static String getCtepId(final String poId) throws PAException {
           return PADomainUtils.getCtepId(poId);
        }
    }
    

}

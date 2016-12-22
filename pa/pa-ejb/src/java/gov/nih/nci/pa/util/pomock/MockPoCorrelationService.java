package gov.nih.nci.pa.util.pomock;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.services.correlation.AbstractOrganizationRoleDTO;
import gov.nih.nci.services.correlation.AbstractPersonRoleDTO;
import gov.nih.nci.services.correlation.AbstractRoleDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// CHECKSTYLE:OFF

public class MockPoCorrelationService {

    public static final Map<String, AbstractRoleDTO> STORE = new HashMap<String, AbstractRoleDTO>();
    
    public static int PO_ID_SEQ = 1; // NOPMD
    
    
    /**
     * @param startingCounter 
     * 
     */
    public static void reset(int startingCounter) {
        PO_ID_SEQ = startingCounter;
        STORE.clear();
    }

    public MockPoCorrelationService() {
        super();
    }

    /**
     * @param dto
     * @return
     */
    protected Ii createRole(AbstractRoleDTO dto, final String root) {
        final String id = (PO_ID_SEQ++) + "";
        dto.setStatus(CdConverter.convertToCd(EntityStatusCode.PENDING));
        final Ii ii = IiConverter.convertToIi(id);
        ii.setRoot(root);
        dto.setIdentifier(DSetConverter.convertIiToDset(ii));
        STORE.put(id, dto);
        return ii;
    }

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T extends AbstractRoleDTO> Collection<T> getRolesInStore(
            Class<T> c) {
        List<T> list = new ArrayList<T>();
        for (AbstractRoleDTO role : STORE.values()) {
            if (role.getClass().equals(c)) {
                list.add((T) role);
            }
        }
        return list;
    }
    
    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T extends AbstractOrganizationRoleDTO> List<T> searchOrganizationRole(
            T criteria) {
        List<T> list = new ArrayList<T>();
        for (AbstractRoleDTO role : STORE.values()) {
            if (role.getClass().equals(criteria.getClass())) {
                AbstractOrganizationRoleDTO orgRole = (AbstractOrganizationRoleDTO) role;
                boolean match = true;
                if (!ISOUtil.isIiNull(criteria.getPlayerIdentifier())) {
                    match = match
                            && criteria
                                    .getPlayerIdentifier()
                                    .getExtension()
                                    .equals(IiConverter.convertToString(orgRole
                                            .getPlayerIdentifier()));

                }
                if (ISOUtil.isDSetNotEmpty(criteria.getIdentifier())) {
                    match = match
                            && criteria
                                    .getIdentifier()
                                    .getItem()
                                    .iterator()
                                    .next()
                                    .getExtension()
                                    .equals(IiConverter.convertToString(orgRole
                                            .getIdentifier().getItem()
                                            .iterator().next()));

                }
                if (match) {
                    list.add((T) orgRole);
                }
            }
        }
        return list;
    }
    
    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T extends AbstractPersonRoleDTO> List<T> searchPersonRole(
            T criteria) {
        List<T> list = new ArrayList<T>();
        for (AbstractRoleDTO role : STORE.values()) {
            if (role.getClass().equals(criteria.getClass())) {
                AbstractPersonRoleDTO orgRole = (AbstractPersonRoleDTO) role;
                boolean match = true;
                if (!ISOUtil.isIiNull(criteria.getPlayerIdentifier())) {
                    match = match
                            && criteria
                                    .getPlayerIdentifier()
                                    .getExtension()
                                    .equals(IiConverter.convertToString(orgRole
                                            .getPlayerIdentifier()));

                }
                if (!ISOUtil.isIiNull(criteria.getScoperIdentifier())) {
                    match = match
                            && criteria
                                    .getScoperIdentifier()
                                    .getExtension()
                                    .equals(IiConverter.convertToString(orgRole
                                            .getScoperIdentifier()));

                }
                if (ISOUtil.isDSetNotEmpty(criteria.getIdentifier())) {
                    match = match
                            && criteria
                                    .getIdentifier()
                                    .getItem()
                                    .iterator()
                                    .next()
                                    .getExtension()
                                    .equals(IiConverter.convertToString(orgRole
                                            .getIdentifier().getItem()
                                            .iterator().next()));

                }
                if (match) {
                    list.add((T) orgRole);
                }
            }
        }
        return list;
    }

}

/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.CacheUtils;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 * 
 */
public class PAOrganizationServiceCachingDecoratorTest {

    private PAOrganizationServiceRemote remote;
    private Organization organization;
    private PAOrganizationServiceCachingDecorator decorator;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        remote = mock(PAOrganizationServiceRemote.class);
        organization = mock(Organization.class);
        decorator = new PAOrganizationServiceCachingDecorator(remote);

    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        CacheUtils.getCriteriaCollectionsCache().removeAll();
        CacheUtils.getSearchResultsCache().removeAll();
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.PAOrganizationServiceCachingDecorator#getOrganizationsAssociatedWithStudyProtocol(java.lang.String)}
     * .
     * 
     * @throws Exception
     */
    @Test
    public final void testGetOrganizationsAssociatedWithStudyProtocol()
            throws Exception {
        String orgType = "LEAD_ORGANIZATION";
        decorator.getOrganizationsAssociatedWithStudyProtocol(orgType);
        verify(remote).getOrganizationsAssociatedWithStudyProtocol(orgType);

        setUp();

        decorator.getOrganizationsAssociatedWithStudyProtocol(orgType);
        verify(remote, never()).getOrganizationsAssociatedWithStudyProtocol(
                orgType);

        CacheUtils.getCriteriaCollectionsCache().removeAll();
        decorator.getOrganizationsAssociatedWithStudyProtocol(orgType);
        verify(remote, times(1)).getOrganizationsAssociatedWithStudyProtocol(
                orgType);

    }
    
    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.PAOrganizationServiceCachingDecorator#getOrganizationsWithTypeAndNameAssociatedWithStudyProtocol(java.lang.String, java.lang.String)}
     * .
     * 
     * @throws Exception
     */
    @Test
    public final void testGetOrganizationsWithTypeAndNameAssociatedWithStudyProtocol()
            throws Exception {
        String orgType = "LEAD_ORGANIZATION";
        String orgTerm = "National";
        decorator.getOrganizationsWithTypeAndNameAssociatedWithStudyProtocol(orgType, orgTerm);
        verify(remote).getOrganizationsWithTypeAndNameAssociatedWithStudyProtocol(orgType, orgTerm);


    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.PAOrganizationServiceCachingDecorator#getOrganizationByIndetifers(gov.nih.nci.pa.domain.Organization)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testGetOrganizationByIndetifers() throws PAException {
        decorator.getOrganizationByIndetifers(organization);
        verify(remote).getOrganizationByIndetifers(organization);
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.PAOrganizationServiceCachingDecorator#getOrganizationIdsByNames(java.util.List)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testGetOrganizationIdsByNames() throws PAException {
        List<String> list = new ArrayList<String>();
        decorator.getOrganizationIdsByNames(list);
        verify(remote).getOrganizationIdsByNames(list);
    }

}

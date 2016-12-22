/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.CacheUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 * 
 */
public class ProtocolQueryServiceCachingDecoratorTest {

    private ProtocolQueryServiceLocal local;
    private ProtocolQueryServiceCachingDecorator decorator;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        local = mock(ProtocolQueryServiceLocal.class);
        decorator = new ProtocolQueryServiceCachingDecorator(local);
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
     * {@link gov.nih.nci.pa.service.util.ProtocolQueryServiceCachingDecorator#getStudyProtocolByCriteria(gov.nih.nci.pa.dto.StudyProtocolQueryCriteria)}
     * .
     * 
     * @throws Exception
     */
    @Test
    public final void testGetStudyProtocolByCriteria() throws Exception {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        decorator.getStudyProtocolByCriteria(criteria);
        verify(local).getStudyProtocolByCriteria(criteria);

        setUp();

        decorator.getStudyProtocolByCriteria(criteria);
        verify(local, never()).getStudyProtocolByCriteria(criteria);

        CacheUtils.getSearchResultsCache().removeAll();
        decorator.getStudyProtocolByCriteria(criteria);
        verify(local).getStudyProtocolByCriteria(criteria);
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.ProtocolQueryServiceCachingDecorator#getStudyProtocolByCriteriaForReporting(gov.nih.nci.pa.dto.StudyProtocolQueryCriteria)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testGetStudyProtocolByCriteriaForReporting()
            throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        decorator.getStudyProtocolByCriteriaForReporting(criteria);
        verify(local).getStudyProtocolByCriteriaForReporting(criteria);
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.ProtocolQueryServiceCachingDecorator#getStudyProtocolQueryResultList(gov.nih.nci.pa.dto.StudyProtocolQueryCriteria)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testGetStudyProtocolQueryResultList() throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        decorator.getStudyProtocolQueryResultList(criteria);
        verify(local).getStudyProtocolQueryResultList(criteria);
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.ProtocolQueryServiceCachingDecorator#getTrialSummaryByStudyProtocolId(java.lang.Long)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testGetTrialSummaryByStudyProtocolId() throws PAException {
        Long id = 1L;
        decorator.getTrialSummaryByStudyProtocolId(id);
        verify(local).getTrialSummaryByStudyProtocolId(id);
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.ProtocolQueryServiceCachingDecorator#getStudyProtocolByOrgIdentifier(java.lang.Long)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testGetStudyProtocolByOrgIdentifier() throws PAException {
        Long id = 1L;
        decorator.getStudyProtocolByOrgIdentifier(id);
        verify(local).getStudyProtocolByOrgIdentifier(id);
    }

}

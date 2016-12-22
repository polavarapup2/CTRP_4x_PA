package gov.nih.nci.pa.service.status;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.status.json.AppName;
import gov.nih.nci.pa.service.status.json.TransitionFor;
import gov.nih.nci.pa.service.status.json.TrialType;
import gov.nih.nci.pa.util.AbstractEjbTestCase;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

public class StatusTransitionServiceTest extends AbstractEjbTestCase {

    private StatusTransitionServiceBean statusTransitionServiceBean;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        statusTransitionServiceBean = (StatusTransitionServiceBean) getEjbBean(StatusTransitionServiceBean.class);
    }

    @Test
    public void testBeginTransition() throws PAException {
        List<StatusDto> statusDtos = statusTransitionServiceBean
                .validateStatusTransition(AppName.PA, TrialType.ABBREVIATED,
                        TransitionFor.TRIAL_STATUS, "IN_REVIEW", new Date(),
                        "APPROVED", new Date());

        assertNotNull(statusDtos);
        assertFalse(statusDtos.isEmpty());

        StatusDto sd = statusDtos.get(0);
        assertTrue(sd.hasErrors());
    }

    /**
     * Exercises all possible transitions ensuring none of them throws an
     * exception. This ensures the JSON file covers all possible scenarios.
     * 
     * @throws PAException
     */
    @Test
    public void testNoTransitionLeadsToPaException() throws PAException {
        Date today = new Date();
        Date yday = DateUtils.addDays(today, -1);

        for (StudyStatusCode from : StudyStatusCode.values()) {
            for (StudyStatusCode to : StudyStatusCode.values()) {
                statusTransitionServiceBean.validateStatusTransition(
                        AppName.PA, TrialType.COMPLETE,
                        TransitionFor.TRIAL_STATUS, from.name(), yday,
                        to.name(), yday);
                statusTransitionServiceBean.validateStatusTransition(
                        AppName.PA, TrialType.ABBREVIATED,
                        TransitionFor.TRIAL_STATUS, from.name(), yday,
                        to.name(), yday);
                statusTransitionServiceBean.validateStatusTransition(
                        AppName.REGISTRATION, TrialType.COMPLETE,
                        TransitionFor.TRIAL_STATUS, from.name(), yday,
                        to.name(), yday);
                statusTransitionServiceBean.validateStatusTransition(
                        AppName.REGISTRATION, TrialType.ABBREVIATED,
                        TransitionFor.TRIAL_STATUS, from.name(), yday,
                        to.name(), yday);

            }
        }

        for (RecruitmentStatusCode from : RecruitmentStatusCode.values()) {
            for (RecruitmentStatusCode to : RecruitmentStatusCode.values()) {
                statusTransitionServiceBean
                        .validateStatusTransition(AppName.PA,
                                TrialType.COMPLETE, TransitionFor.SITE_STATUS,
                                from.name(), yday, to.name(), yday);
                statusTransitionServiceBean
                        .validateStatusTransition(AppName.PA,
                                TrialType.ABBREVIATED,
                                TransitionFor.SITE_STATUS, from.name(), yday,
                                to.name(), yday);
                statusTransitionServiceBean
                        .validateStatusTransition(AppName.REGISTRATION,
                                TrialType.COMPLETE, TransitionFor.SITE_STATUS,
                                from.name(), yday, to.name(), yday);
                statusTransitionServiceBean
                        .validateStatusTransition(AppName.REGISTRATION,
                                TrialType.ABBREVIATED,
                                TransitionFor.SITE_STATUS, from.name(), yday,
                                to.name(), yday);

            }
        }

    }
}

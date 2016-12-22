/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.StudyObjective;
import gov.nih.nci.pa.iso.convert.StudyObjectiveConverter;
import gov.nih.nci.pa.iso.dto.StudyObjectiveDTO;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class StudyObjectiveBeanLocal
 extends AbstractStudyIsoService<StudyObjectiveDTO, StudyObjective, StudyObjectiveConverter>
 implements StudyObjectiveServiceLocal {

}

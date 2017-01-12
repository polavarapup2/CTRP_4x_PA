/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.domain.StratumGroup;
import gov.nih.nci.pa.iso.convert.StratumGroupConverter;
import gov.nih.nci.pa.iso.dto.StratumGroupDTO;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class StratumGroupBeanLocal extends
AbstractStudyIsoService<StratumGroupDTO, StratumGroup, StratumGroupConverter> implements StratumGroupServiceLocal {

}

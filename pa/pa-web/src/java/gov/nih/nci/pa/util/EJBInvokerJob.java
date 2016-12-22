/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.util;

import java.lang.reflect.Method;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * <p>
 * A <code>Job</code> that invokes a method on an EJB3 EJB. Based on the EJBInvokerJob.
 * </p>
 * <p>
 *
 * Expects the properties corresponding to the following keys to be in the <code>JobDataMap</code> when it executes:
 *
 * <ul>
 * <li><code>EJB_JNDI_NAME_KEY</code>- the JNDI name (location) of the EJB</li>
 * <li><code>EJB_INTERFACE_NAME_KEY</code>- the name of the EJB's business interface</li>
 * <li><code>EJB_METHOD_KEY</code>- the name of the method to invoke on the EJB.</li>
 * <li><code>EJB_ARGS_KEY</code>- an Object[] of the args to pass to the method (optional, if left out,
 * there are no arguments).</li>
 * <li><code>EJB_ARG_TYPES_KEY</code>- a Class[] of the types of the args to pass to the method
 * (optional, if left out, the types will be derived by calling getClass() on each of the arguments).</li>
 * </ul>
 *
 * <br/>
 * The following keys can also be used at need:
 * <ul>
 * <li><code>INITIAL_CONTEXT_FACTORY</code> - the context factory used to build the context.</li>
 * <li><code>PROVIDER_URL</code> - the name of the environment property for specifying configuration
 * information for the service provider to use.</li>
 * </ul>
 * </p>
 * <p>
 * The result of the EJB method invocation will be available to <code>Job/TriggerListener</code>s via
 * <code>{@link org.quartz.JobExecutionContext#getResult()}</code>.
 * </p>
 *
 * @author Adrian Brennan
 * @author Andrew Collins
 * @author James House
 * @author Joel Shellman
 * @author <a href="mailto:bonhamcm@thirdeyeconsulting.com">Chris Bonham</a>
 */
public class EJBInvokerJob implements Job {

    private static final Logger LOG  = Logger.getLogger(EJBInvokerJob.class);
    /** The Constant EJB_JNDI_NAME_KEY. */
    public static final String EJB_JNDI_NAME_KEY = "ejb";

    /** The Constant EJB_INTERFACE_NAME_KEY. */
    public static final String EJB_INTERFACE_NAME_KEY = "interfaceName";

    /** The Constant EJB_METHOD_KEY. */
    public static final String EJB_METHOD_KEY = "method";

    /** The Constant EJB_ARG_TYPES_KEY. */
    public static final String EJB_ARG_TYPES_KEY = "argTypes";

    /** The Constant EJB_ARGS_KEY. */
    public static final String EJB_ARGS_KEY = "args";

    /** The Constant INITIAL_CONTEXT_FACTORY. */
    public static final String INITIAL_CONTEXT_FACTORY = "java.naming.factory.initial";
    
    /** The Constant URL_PKG_PREFIXES. */
    public static final String URL_PKG_PREFIXES = "java.naming.factory.url.pkgs";

    /** The Constant PROVIDER_URL. */
    public static final String PROVIDER_URL = "java.naming.provider.url";

    /** The Constant PRINCIPAL. */
    public static final String PRINCIPAL = "java.naming.security.principal";

    /** The Constant CREDENTIALS. */
    public static final String CREDENTIALS = "java.naming.security.credentials";
    


    /** The initial context. */
    private InitialContext initialContext = null;

    /**
     * Instantiates a new eJB invoker job.
     */
    public EJBInvokerJob() {
        // do nothing
    }
    
    
    /** execute.
     * @param context JobExecutionContext
     * @throws JobExecutionException exception
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        
        JobDataMap dataMap = context.getMergedJobDataMap();

        String ejbJNDIName = dataMap.getString(EJB_JNDI_NAME_KEY);
        String methodName = dataMap.getString(EJB_METHOD_KEY);
        Object[] arguments = (Object[]) dataMap.get(EJB_ARGS_KEY);

        if (StringUtils.isBlank(ejbJNDIName)) {
            throw new JobExecutionException("must specify ejb JNDI name");
        }

        if (arguments == null) {
            arguments = new Object[0];
        }

        Object ejb = locateEjb(dataMap);

        Class<?>[] argTypes = initializeArgTypes(arguments, dataMap);

        try {
            Method methodToExecute = ejb.getClass().getDeclaredMethod(methodName, argTypes);
            Object returnObj = methodToExecute.invoke(ejb, arguments);

            context.setResult(returnObj);
        } catch (Exception e) {
            LOG.info("Caught error executing job", e);
            PaRegistry.getMailManagerService()
            .sendJobFailureNotification(context.getJobDetail().getName(), ExceptionUtils.getFullStackTrace(e));
            throw new JobExecutionException(e);
        } finally {
            // Don't close jndiContext until after method execution because
            // WebLogic requires context to be open to keep the user credentials
            // available. See JIRA Issue: QUARTZ-401
            if (initialContext != null) {
                try {
                    initialContext.close();
                } catch (NamingException e) {
                   LOG.error(e);
                }
            }
        }
    }

    private Class<?>[] initializeArgTypes(Object[] arguments, JobDataMap dataMap) {
        Class<?>[] argTypes = (Class<?>[]) dataMap.get(EJB_ARG_TYPES_KEY);
        if (argTypes == null) {
            argTypes = new Class[arguments.length];
            for (int i = 0; i < arguments.length; i++) {
                argTypes[i] = arguments[i].getClass();
            }
        }
        return argTypes;
    }

    /**
     * Locate ejb.
     *
     * @param dataMap the data map
     *
     * @return the t
     *
     * @throws JobExecutionException the job execution exception
     */
    @SuppressWarnings("unchecked")
    private <T> T locateEjb(JobDataMap dataMap) throws JobExecutionException {
        StringBuffer tBuffer = new StringBuffer("java:global/pa/pa-ejb/");
        tBuffer.append(dataMap.getString(EJB_JNDI_NAME_KEY));
        tBuffer.append('!');
        tBuffer.append(dataMap.getString(EJB_INTERFACE_NAME_KEY));
        String ejbJNDIName = tBuffer.toString();

        Object object = null;

        try {
            initialContext = new InitialContext();

            object = initialContext.lookup(ejbJNDIName);

            if (object == null) {
                throw new JobExecutionException("Cannot find " + ejbJNDIName);
            }
        } catch (NamingException e) {
            throw new JobExecutionException(e);
        }

        String ejbInterfaceName = dataMap.getString(EJB_INTERFACE_NAME_KEY);

        Class<?> ejbInterface = null;

        try {
            ejbInterface = Class.forName(ejbInterfaceName);
        } catch (ClassNotFoundException e) {
            LOG.error("Exception occurred performing class.forname on" + EJB_INTERFACE_NAME_KEY);
            throw new JobExecutionException(e);
        }

        if (!ejbInterface.isAssignableFrom(object.getClass())) {
            object = PortableRemoteObject.narrow(object, ejbInterface);
        }

        return (T) object;
    }
}

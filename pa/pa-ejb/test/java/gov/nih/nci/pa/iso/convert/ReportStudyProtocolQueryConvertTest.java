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
package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceIntegrationTest;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;

import org.junit.Before;
import org.junit.Test;

/**
 * @author mshestopalov
 *
 */
public class ReportStudyProtocolQueryConvertTest extends AbstractHibernateTestCase {

    ReportStudyProtocolQueryConverter studyProtocolQueryConverter = null;
    private class ReportStudyProtocolQueryConverterForTest extends ReportStudyProtocolQueryConverter {
        /**
         * @param registryUserSvc
         * @param paSvcUtils
         */
        public ReportStudyProtocolQueryConverterForTest(RegistryUserServiceLocal registryUserSvc,
                PAServiceUtils paSvcUtils) {
            super(registryUserSvc, paSvcUtils);
        }

        /**
         * Overriding the sql string because the postges sql and the hqldb are different enough
         * to cause lots of problems with that huge query in the bean while testing.
         * By using this fake query I am still able to test the rest of the parts of the
         * bean functionality.
         */
        @Override
        protected String generateReportingSql() {

            return "select crs_p.last_name, crs_p.first_name, crs_p.identifier, ro_org.name, ro_org.identifier, "
            + "ss.LOCAL_SP_INDENTIFIER, sr.type_code, sos.sosSc, sos.sosSd, dws.dwsSc, "
            + "dws.dwsSd, si.siId, si.siCm, si.siOd, si.siCd, "
            + "sp.OFFICIAL_TITLE, sp.PHASE_CODE, sp.PRIMARY_PURPOSE_CODE, null, "
            + "sp.RECORD_VERIFICATION_DATE, null, sp.PHASE_ADDITIONAL_QUALIFIER_CODE, "
            + "sp.DATE_LAST_CREATED, sp.AMENDMENT_NUMBER, sp.AMENDMENT_DATE, sp.SUBMISSION_NUMBER, "
            + "sp.STUDY_PROTOCOL_TYPE, sOi.extension, ss2.local_sp_indentifier as leadOrgId, "
            + "ss3.local_sp_indentifier as nctidentifier, sp.study_source "
            + "from study_protocol AS sp left join study_site AS ss ON sp.identifier = ss.study_protocol_identifier "
            + "left JOIN study_otheridentifiers sOi ON sp.identifier = sOi.study_protocol_id "
            + "AND sOi.root = :NCI_II_ROOT "
            + "JOIN research_organization AS ro ON ss.research_organization_identifier = ro.identifier "
            + "JOIN organization AS ro_org ON ro.organization_identifier = ro_org.identifier "
            + "left JOIN study_contact AS sc ON sc.study_protocol_identifier = sp.identifier "
            + "and sc.role_code = :piRole "
            + "JOIN clinical_research_staff AS crs ON sc.clinical_research_staff_identifier = crs.identifier "
            + "JOIN person AS crs_p ON crs.person_identifier = crs_p.identifier "
            + "left JOIN study_resourcing AS sr ON sr.study_protocol_identifier = sp.identifier and "
            + "sr.SUMM_4_REPT_INDICATOR = true "
            + "left join (select status_code as sosSc, status_date as sosSd, study_protocol_identifier as sosSpi from "
            + "study_overall_status where study_protocol_identifier = :spId limit 1) AS sos "
            + "ON sos.sosSpi = ss.study_protocol_identifier "
            + "left join (select status_code as dwsSc, status_date_range_low as dwsSd, study_protocol_identifier as dwsSpi "
            + "from document_workflow_status where study_protocol_identifier = :spId limit 1) "
            + "AS dws ON dws.dwsSpi = ss.study_protocol_identifier "
            + "left join (select identifier as siId, comments as siCm, open_date as siOd, close_date as siCd, "
            + "study_protocol_identifier as siSpi from study_inbox "
            + "where study_protocol_identifier = :spId limit 1) AS si "
            + "ON si.siSpi = ss.study_protocol_identifier "
            + "left join study_site AS ss2 "
            + "ON sp.identifier = ss2.study_protocol_identifier and ss2.functional_code = 'LEAD_ORGANIZATION' "
            + "left join study_site ss3 ON sp.identifier = ss3.study_protocol_identifier and "
            + "ss3.functional_code = 'IDENTIFIER_ASSIGNER' "
            + "join research_organization ro3 ON ss3.research_organization_identifier = ro3.identifier "
            + "join organization o3 ON ro3.organization_identifier = o3.identifier and o3.name = '"
            + PAConstants.CTGOV_ORG_NAME + "' "
            + "where sp.identifier = :spId and 'LEAD_ORGANIZATION' = :leadOrgRole limit 1";
        }
    }

    @Before
    public void init() throws Exception {
        TestSchema.primeData();
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        LookUpTableServiceRemote lookupSvc = mock(LookUpTableServiceRemote.class);
        when(paRegSvcLoc.getLookUpTableService()).thenReturn(lookupSvc);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        studyProtocolQueryConverter = new ReportStudyProtocolQueryConverterForTest(
                PaRegistry.getInstance().getRegistryUserService(), new PAServiceUtils());
    }

    @Test
    public void convertFromDomainToDTOTest() throws PAException {
        StudyProtocol sp = (new ProtocolQueryServiceIntegrationTest())
            .createStudyProtocol("1", false, Boolean.FALSE, false, false, false, false, true);
        assertNotNull(sp.getId());
        StudyProtocolQueryDTO spDTO = studyProtocolQueryConverter.convertToStudyProtocolDtoForReporting(
                sp.getId(), false, null);
        assertNotNull(spDTO);
        assertNotNull(spDTO.getOfficialTitle());
    }
}

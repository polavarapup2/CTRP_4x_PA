/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class InterventionServiceTest extends AbstractHibernateTestCase {
    private final InterventionServiceLocal remoteEjb = new InterventionBeanLocal();
    private final InterventionAlternateNameServiceLocal ianService = new InterventionAlternateNameServiceBean();
    private Ii ii;

    @Before
    public void init() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();
        ii = IiConverter.convertToIi(TestSchema.interventionIds.get(0));
     }

    @Test
    public void getTest() throws Exception {
        InterventionDTO dto = remoteEjb.get(ii);
        assertEquals(StConverter.convertToString(dto.getName()), "Chocolate Bar");
    }

    @Test
    public void createTest() throws Exception {
        Timestamp today = PAUtil.dateStringToTimestamp(PAUtil.today());
        InterventionDTO dto1 = new InterventionDTO();
        dto1.setDescriptionText(StConverter.convertToSt("description123"));
        dto1.setName(StConverter.convertToSt("name123"));
        dto1.setNtTermIdentifier(StConverter.convertToSt("nt123"));
        dto1.setPdqTermIdentifier(StConverter.convertToSt("pdq123"));
        dto1.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.PENDING));
        dto1.setStatusDateRangeLow(TsConverter.convertToTs(today));
        dto1.setTypeCode(CdConverter.convertToCd(InterventionTypeCode.GENETIC));

        InterventionDTO result = remoteEjb.create(dto1);
        Ii ii = result.getIdentifier();

        InterventionDTO dto2 = remoteEjb.get(ii);
        assertEquals(IiConverter.convertToLong(ii), IiConverter.convertToLong(dto2.getIdentifier()));
        assertEquals(StConverter.convertToString(dto1.getDescriptionText()), StConverter.convertToString(dto2.getDescriptionText()));
        assertEquals(StConverter.convertToString(dto1.getName()), StConverter.convertToString(dto2.getName()));
        assertEquals(StConverter.convertToString(dto1.getNtTermIdentifier()), StConverter.convertToString(dto2.getNtTermIdentifier()));
        assertEquals(StConverter.convertToString(dto1.getPdqTermIdentifier()), StConverter.convertToString(dto2.getPdqTermIdentifier()));
        assertEquals(CdConverter.convertCdToString(dto1.getStatusCode()), CdConverter.convertCdToString(dto2.getStatusCode()));
        assertEquals(TsConverter.convertToTimestamp(dto1.getStatusDateRangeLow()), TsConverter.convertToTimestamp(dto2.getStatusDateRangeLow()));
        assertEquals(CdConverter.convertCdToString(dto1.getTypeCode()), CdConverter.convertCdToString(dto2.getTypeCode()));
    }

    @Test
    public void searchTest() throws Exception {
        InterventionDTO searchCriteria = new InterventionDTO();
        searchCriteria.setName(StConverter.convertToSt("CHOCOLATE*"));
        searchCriteria.setExactMatch(StConverter.convertToSt("false"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("true"));
        List<InterventionDTO> r = remoteEjb.search(searchCriteria);
        assertEquals(1, r.size());

        searchCriteria.setName(StConverter.convertToSt("xCHOCOLATE*"));
        searchCriteria.setExactMatch(StConverter.convertToSt("false"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("true"));
        r = remoteEjb.search(searchCriteria);
        assertEquals(0, r.size());

        searchCriteria.setName(StConverter.convertToSt("HERSHEY"));
        searchCriteria.setExactMatch(StConverter.convertToSt("false"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("true"));
        r = remoteEjb.search(searchCriteria);
        assertEquals(1, r.size());

        searchCriteria.setName(StConverter.convertToSt("CHOCOLATE"));
        searchCriteria.setExactMatch(StConverter.convertToSt("true"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("false"));
        r = remoteEjb.search(searchCriteria);
        assertEquals(0, r.size());

        searchCriteria.setName(StConverter.convertToSt("CHOCOLATE BAR"));
        searchCriteria.setExactMatch(StConverter.convertToSt("true"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("false"));
        r = remoteEjb.search(searchCriteria);
        assertEquals(1, r.size());

        searchCriteria.setName(StConverter.convertToSt("HERSHEY"));
        searchCriteria.setExactMatch(StConverter.convertToSt("true"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("true"));
        r = remoteEjb.search(searchCriteria);
        assertEquals(1, r.size());

        searchCriteria.setName(null);
        searchCriteria.setNtTermIdentifier(null);
        searchCriteria.setTypeCode(CdConverter.convertToCd(InterventionTypeCode.GENETIC));
        searchCriteria.setExactMatch(StConverter.convertToSt("false"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("true"));
        try {
            r = remoteEjb.search(searchCriteria);
            fail("Service should throw PAException when searching w/o name.");
        } catch(PAException e) {
            // expected behavior
        }
        
        //Search by NCIt id
        searchCriteria.setNtTermIdentifier(StConverter.convertToSt("CTEST"));
        r = remoteEjb.search(searchCriteria);
        assertEquals(1, r.size());
        
        searchCriteria.setNtTermIdentifier(StConverter.convertToSt("INVALID"));
        r = remoteEjb.search(searchCriteria);
        assertEquals(0, r.size());
    }
    @Test
    public void noInactiveInterventionAlternateNameRecordsReturnedBySearchTest() throws Exception {
        InterventionDTO searchCriteria = new InterventionDTO();
        searchCriteria.setName(StConverter.convertToSt("HERSHEY"));
        searchCriteria.setExactMatch(StConverter.convertToSt("false"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("true"));
        List<InterventionDTO> r = remoteEjb.search(searchCriteria);
        int size = r.size();
        assertTrue(size > 0);
        Ii interventionIi = r.get(0).getIdentifier();
        List<InterventionAlternateNameDTO> ianList = ianService.getByIntervention(interventionIi);
        for (InterventionAlternateNameDTO ian : ianList) {
            ian.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.INACTIVE));
            ianService.update(ian);
        }

        r = remoteEjb.search(searchCriteria);
        assertEquals(size - 1, r.size());
    }
    @Test
    public void noInactiveInterventionRecordsReturnedBySearchTest() throws Exception {
        InterventionDTO searchCriteria = new InterventionDTO();
        searchCriteria.setName(StConverter.convertToSt("CHOCOLATE*"));
        searchCriteria.setExactMatch(StConverter.convertToSt("false"));
        searchCriteria.setIncludeSynonym(StConverter.convertToSt("true"));
        List<InterventionDTO> r = remoteEjb.search(searchCriteria);
        int size = r.size();
        assertTrue(size > 0);
        r.get(0).setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.INACTIVE));
        remoteEjb.update(r.get(0));
        r = remoteEjb.search(searchCriteria);
        assertEquals(size - 1, r.size());
    }
}

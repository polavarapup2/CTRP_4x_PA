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
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.TestSchema;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author NAmiruddin
 *
 */
public class RegulatoryInformationServiceTest extends AbstractHibernateTestCase  {

    private RegulatoryInformationBean bean = new RegulatoryInformationBean();
    private RegulatoryInformationServiceLocal remoteEjb = bean;
    private Long raId;
    private Long countryId;

    @Before
    public void setUp() throws Exception {
        Country c = TestSchema.createCountryObj();
        RegulatoryAuthority ra = TestSchema.createRegulatoryObj(c);
        TestSchema.addUpdObject(c);
        TestSchema.addUpdObject(ra);

        raId = ra.getId();
        countryId = c.getId();
    }

    @Test
    public void getDistinctCountryNamesTest() throws Exception {
        List<CountryRegAuthorityDTO> data = remoteEjb.getDistinctCountryNames();
        assertNotNull(data);
        assertEquals("Size does not match" , data.size(), 1);
        assertEquals("Alpha 2 does not match" , data.get(0).getAlpha2(), "CA");
        assertEquals("Alpha 3 does not match" , data.get(0).getAlpha3(), "CAM");
        assertEquals("Name does not match" , data.get(0).getName(), "Cayman Islands");
    }
    
    @Test
    public void getDistinctCountryNamesStartWithUSATest() throws Exception {
        ArrayList<Country> c = TestSchema.createCountryObjs();
        ArrayList<RegulatoryAuthority> ra = TestSchema.createRegulatoryObjs(c);
        TestSchema.addUpdObjects(c);
        TestSchema.addUpdObjects(ra);
        List<CountryRegAuthorityDTO> data = remoteEjb.getDistinctCountryNamesStartWithUSA();
        assertNotNull(data);
        assertEquals("Size matchs" , data.size(), 3);
        assertEquals("Alpha 2 does not match" , data.get(0).getAlpha2(), "US");
        assertEquals("Alpha 3 does not match" , data.get(0).getAlpha3(), "USA");
        assertEquals("Name does not match" , data.get(0).getName(), "United States");
        assertEquals("Alpha 2 does not match" , data.get(1).getAlpha2(), "CA");
        assertEquals("Alpha 3 does not match" , data.get(1).getAlpha3(), "CAM");
        assertEquals("Name does not match" , data.get(1).getName(), "Cayman Islands");
    }

    @Test
    public void getRegulatoryAuthorityNameIdTest() throws Exception {
        List<?> lst = remoteEjb.getRegulatoryAuthorityNameId(raId);
        assertNotNull(lst);
        RegulatoryAuthOrgDTO data = (RegulatoryAuthOrgDTO) lst.get(0);
        assertEquals("Name does not match" , data.getName(), "BWI reg body");
    }

    @Test
    public void getRegulatoryAuthorityInfoTest() throws Exception {
        List<Long> data = remoteEjb.getRegulatoryAuthorityInfo(raId);
        assertNotNull(data);
        assertEquals("Size does not match" , data.size(), 2);
        assertEquals("Id does not match" , data.get(0), raId);
    }

    @Test
    public void getCountryOrOrgNameTest() throws Exception {
        String name = remoteEjb.getCountryOrOrgName(countryId, "Country");
        assertNotNull(name);
        assertEquals("Name does not match" , name, "Cayman Islands");
    }

    @Test
    public void getTest() throws Exception {
        RegulatoryAuthority ra = remoteEjb.get(raId);
        assertNotNull(ra);
        assertEquals("Name does not match" , ra.getAuthorityName(), "BWI reg body");
    }

    @Test
    public void getRegulatoryAuthorityCountry() throws Exception {
        Country c = remoteEjb.getRegulatoryAuthorityCountry(raId);
        assertNotNull(c);
        assertEquals("Name does not match" , c.getName(), "Cayman Islands");
    }
}
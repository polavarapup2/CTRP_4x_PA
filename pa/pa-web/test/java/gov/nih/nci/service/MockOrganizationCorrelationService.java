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
package gov.nih.nci.service;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OversightCommittee;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author hreinhart
 *
 */
public class MockOrganizationCorrelationService implements
        OrganizationCorrelationServiceRemote {


    static ArrayList<Organization> paOrgList = new ArrayList<Organization>();
    static ArrayList<HealthCareFacility> hcfList = new ArrayList<HealthCareFacility>();
    static ArrayList<OversightCommittee> ocList = new ArrayList<OversightCommittee>();
    static ArrayList<ResearchOrganization> roList = new ArrayList<ResearchOrganization>();
    static HashMap<String, Organization> poPaOrgMap = new HashMap<String, Organization>();

    private static Long seq = 0L;

    public static String getOrgNameFromPoIdentifier(String poIdentifier) {
        return "Organization " + poIdentifier;
    }

    private void createPaOrg(String poId) {
        seq++;
        Organization o = new Organization();
        o.setCity("city"+seq);
        o.setCountryName("USA");
        o.setIdentifier(poId);
        o.setName(getOrgNameFromPoIdentifier(poId));
        o.setPostalCode("12345");
        o.setStatusCode(EntityStatusCode.ACTIVE);
        paOrgList.add(o);
        poPaOrgMap.put(poId, o);
    }

    /**
     * @param orgPoIdentifier
     * @return
     * @throws PAException
     */
    public Long createHealthCareFacilityCorrelations(String orgPoIdentifier)
            throws PAException {
        if(!poPaOrgMap.containsKey(orgPoIdentifier)) {
            createPaOrg(orgPoIdentifier);
        }
        for(HealthCareFacility hcf : hcfList) {
            if (hcf.getOrganization().getIdentifier().equals(orgPoIdentifier)) {
                ;
            }
            return hcf.getId();
        }
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setId(seq++);
        hcf.setIdentifier("po"+ seq);
        hcf.setOrganization(poPaOrgMap.get(orgPoIdentifier));
        hcf.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        hcf.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        hcfList.add(hcf);
        return hcf.getId();
    }

    /**
     * @param orgPoIdentifier
     * @return
     * @throws PAException
     */
    public Long createOversightCommitteeCorrelations(String orgPoIdentifier)
            throws PAException {
        if(!poPaOrgMap.containsKey(orgPoIdentifier)) {
            createPaOrg(orgPoIdentifier);
        }
        for(HealthCareFacility hcf : hcfList) {
            if (hcf.getOrganization().getIdentifier().equals(orgPoIdentifier)) {
                ;
            }
            return hcf.getId();
        }
        OversightCommittee oc = new OversightCommittee();
        oc.setId(seq++);
        oc.setIdentifier("po"+ seq);
        oc.setOrganization(poPaOrgMap.get(orgPoIdentifier));
        oc.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        oc.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        ocList.add(oc);
        return oc.getId();
    }

    /**
     * @param poOrg
     * @return
     * @throws PAException
     */
    public Organization createPAOrganizationUsingPO(OrganizationDTO poOrg)
            throws PAException {
        seq++;
        Organization o = new Organization();
        o.setCity("city"+seq);
        o.setCountryName("USA");
        o.setIdentifier(IiConverter.convertToString(poOrg.getIdentifier()));
        o.setName(EnOnConverter.convertEnOnToString(poOrg.getName()));
        o.setPostalCode("12345");
        o.setStatusCode(EntityStatusCode.ACTIVE);
        paOrgList.add(o);
        poPaOrgMap.put(o.getIdentifier(), o);
        return o;
    }

    /**
     * @param orgPoIdentifier
     * @return
     * @throws PAException
     */
    public Long createResearchOrganizationCorrelations(String orgPoIdentifier) {
        if(!poPaOrgMap.containsKey(orgPoIdentifier)) {
            createPaOrg(orgPoIdentifier);
        }
        for(HealthCareFacility hcf : hcfList) {
            if (hcf.getOrganization().getIdentifier().equals(orgPoIdentifier)) {
                ;
            }
            return hcf.getId();
        }
        ResearchOrganization ro = new ResearchOrganization();
        ro.setId(seq++);
        ro.setIdentifier("po"+ seq);
        ro.setOrganization(poPaOrgMap.get(orgPoIdentifier));
        ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        ro.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        roList.add(ro);
        return ro.getId();
    }

    /**
     * @param studyProtocolId
     * @param functionalCode
     * @return
     * @throws PAException
     */
    public List<Organization> getOrganizationByStudySite(
            Long studyProtocolId,
            StudySiteFunctionalCode functionalCode) throws PAException {
        ArrayList<Organization> orgList = new ArrayList<Organization>();

        StudySiteDTO criteria = new StudySiteDTO();
        criteria.setFunctionalCode(CdConverter.convertToCd(functionalCode));
        List<StudySiteDTO> spList = PaRegistry.getStudySiteService().
                getByStudyProtocol(IiConverter.convertToIi(studyProtocolId), criteria);
        for (StudySiteDTO sp : spList) {
            if (!ISOUtil.isIiNull(sp.getHealthcareFacilityIi())) {
                for (HealthCareFacility hcf : hcfList) {
                    if (hcf.getId().equals(IiConverter.convertToLong(sp.getHealthcareFacilityIi()))) {
                        orgList.add(hcf.getOrganization());
                    }
                }
            }
            if (!ISOUtil.isIiNull(sp.getOversightCommitteeIi())) {
                for (OversightCommittee oc : ocList) {
                    if (oc.getId().equals(IiConverter.convertToLong(sp.getOversightCommitteeIi()))) {
                        orgList.add(oc.getOrganization());
                    }
                }
            }
            if (!ISOUtil.isIiNull(sp.getResearchOrganizationIi())) {
                for (ResearchOrganization ro : roList) {
                    if (ro.getId().equals(IiConverter.convertToLong(sp.getResearchOrganizationIi()))) {
                        orgList.add(ro.getOrganization());
                    }
                }
            }
        }
        return orgList;
    }
    /**
     *
     * @param studyProtocolIi sp id
     * @param cd functional role code
     * @return Organization
     * @throws PAException onError
     */
    public Organization getOrganizationByFunctionRole(Ii studyProtocolIi , Cd cd) throws PAException {
        return null;
    }

    /**
     * returns the id of the given type.
     * @param identifierType identifierType
     * @return po identifier
     * @throws PAException on error
     */
    public String getPOOrgIdentifierByIdentifierType(String identifierType) throws  PAException {
        String poOrgId = "";
        if (identifierType.equalsIgnoreCase(PAConstants.NCT_IDENTIFIER_TYPE)) {
            poOrgId = "1";
        }
        return poOrgId;

    }
    
    @Override
    public String getPOOrgIdentifierByOrgName(String orgName)
            throws PAException {       
        return getPOOrgIdentifierByIdentifierType(PAConstants.NCT_IDENTIFIER_TYPE);
    }

    public Ii getPoResearchOrganizationByEntityIdentifier(Ii orgPoIdentifier)
            throws PAException {
        return IiConverter.convertToPoResearchOrganizationIi(orgPoIdentifier.getExtension());
    }


    public Long createHcfWithExistingPoHcf(Ii poHcfIdentifier) throws PAException {
        return 1L;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote#getROByFunctionRole(gov.nih.nci.iso21090.Ii, gov.nih.nci.iso21090.Cd)
     */
    public Ii getROByFunctionRole(Ii studyProtocolIi, Cd cd) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAffiliatedWithTrial(Long spId, Long orgId, StudySiteFunctionalCode type) throws PAException {
        // TODO Auto-generated method stub
        return false;
    }

}

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
package gov.nih.nci.pa.service; // NOPMD

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.FunctionalRole;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.convert.AbstractConverter;
import gov.nih.nci.pa.iso.dto.FunctionalRoleDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Base class for services which implement the getByStudyProtocol method with functional roles.
 * @author Naveen Amiruddin
 * @since 09/30/2008
 * @param <DTO> data transfer object
 * @param <BO> domain object
 * @param <CONVERTER> converter class
 */
public abstract class AbstractRoleIsoService<DTO extends FunctionalRoleDTO, BO extends FunctionalRole,
    CONVERTER extends AbstractConverter<DTO, BO>>
    extends AbstractStudyIsoService<DTO, BO, CONVERTER>
    implements RolePaService<DTO> {

    private static final Map<String, String> ROLE_ASSOCIATION_MAP = new HashMap<String, String>();
    static {
        ROLE_ASSOCIATION_MAP.put(IiConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME, "healthCareFacility");
        ROLE_ASSOCIATION_MAP.put(IiConverter.RESEARCH_ORG_IDENTIFIER_NAME, "researchOrganization");
        ROLE_ASSOCIATION_MAP.put(IiConverter.OVERSIGHT_COMMITTEE_IDENTIFIER_NAME, "oversightCommittee");
        ROLE_ASSOCIATION_MAP.put(IiConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME, "clinicalResearchStaff");
        ROLE_ASSOCIATION_MAP.put(IiConverter.HEALTH_CARE_PROVIDER_IDENTIFIER_NAME, "healthCareProvider");
        ROLE_ASSOCIATION_MAP.put(IiConverter.ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME, "organizationalContact");
    }

    private static final Map<String, String> ROLE_CLASS_MAP = new HashMap<String, String>();
    static {
        ROLE_CLASS_MAP.put(IiConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME, "HealthCareFacility");
        ROLE_CLASS_MAP.put(IiConverter.RESEARCH_ORG_IDENTIFIER_NAME, "ResearchOrganization");
        ROLE_CLASS_MAP.put(IiConverter.OVERSIGHT_COMMITTEE_IDENTIFIER_NAME, "OversightCommittee");
        ROLE_CLASS_MAP.put(IiConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME, "ClinicalResearchStaff");
        ROLE_CLASS_MAP.put(IiConverter.HEALTH_CARE_PROVIDER_IDENTIFIER_NAME, "HealthCareProvider");
        ROLE_CLASS_MAP.put(IiConverter.ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME, "OrganizationalContact");
    }

    private static final Map<String, String[]> CLASS_ROLE_MAP = new HashMap<String, String[]>();
    static {
    CLASS_ROLE_MAP.put("StudySite", new String[] {
            ROLE_ASSOCIATION_MAP.get(IiConverter.RESEARCH_ORG_IDENTIFIER_NAME),
            ROLE_ASSOCIATION_MAP.get(IiConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME),
                    ROLE_ASSOCIATION_MAP.get(IiConverter.OVERSIGHT_COMMITTEE_IDENTIFIER_NAME)});
    CLASS_ROLE_MAP.put("StudySiteContact", new String[] {
            ROLE_ASSOCIATION_MAP.get(IiConverter.ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME),
            ROLE_ASSOCIATION_MAP.get(IiConverter.HEALTH_CARE_PROVIDER_IDENTIFIER_NAME),
            ROLE_ASSOCIATION_MAP.get(IiConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME)});
    CLASS_ROLE_MAP.put("StudyContact", new String[] {
            ROLE_ASSOCIATION_MAP.get(IiConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME),
            ROLE_ASSOCIATION_MAP.get(IiConverter.HEALTH_CARE_PROVIDER_IDENTIFIER_NAME)});
    }

    /**
     * Get list of StudySites for a given protocol having
     * functional codes from a list.
     * @param studyProtocolIi id of protocol
     * @param dtos List containing desired functional codes
     * @return list StudySiteDTO
     * @throws PAException on error
     */
    public List<DTO> getByStudyProtocol(Ii studyProtocolIi, List<DTO> dtos) throws PAException {
        if (ISOUtil.isIiNull(studyProtocolIi)) {
            throw new PAException("Cannot call getByStudyProtocol method with a null identifier.");
        }
        StringBuffer criteria = new StringBuffer();

        StringBuffer hql = new StringBuffer("select spart from ");
        hql.append(getTypeArgument().getName());
        hql.append(" spart join spart.studyProtocol spro where spro.id = :studyProtocolId");
        boolean first = true;
        boolean appended = false;
        for (DTO crit : dtos) {
            if (first && crit != null) {
                hql.append(" and ");
                first = false;
            } else {
                criteria.append("or ");
            }

           appended = addTypeArgument(criteria, crit);
        }
        if (appended) {
            hql.append('(');
            hql.append(criteria);
            hql.append(')');
        }
        hql.append(" order by spart.id ");

        return runQuery(studyProtocolIi, hql);
    }

    private boolean addTypeArgument(StringBuffer criteria, DTO crit) {
        boolean appended = false;
        if (getTypeArgument().getName().equals("gov.nih.nci.pa.domain.StudyContact")) {
            StudyContactDTO spcDTO = (StudyContactDTO) crit;
            criteria.append("spart.roleCode = '"
                    + StudyContactRoleCode.getByCode(spcDTO.getRoleCode().getCode()) + "' ");
            appended = true;
        }
        if (getTypeArgument().getName().equals("gov.nih.nci.pa.domain.StudySiteContact")) {
            StudySiteContactDTO spcDTO = (StudySiteContactDTO) crit;
            criteria.append("spart.roleCode = '"
                    + StudySiteContactRoleCode.getByCode(spcDTO.getRoleCode().getCode()) + "' ");
            appended = true;
        }
        if (getTypeArgument().getName().equals("gov.nih.nci.pa.domain.StudySite")) {
            StudySiteDTO spcDTO = (StudySiteDTO) crit;
            criteria.append("spart.functionalCode = '"
                    + StudySiteFunctionalCode.getByCode(spcDTO.getFunctionalCode().getCode()) + "' ");
            appended = true;
        }
        return appended;
    }

    @SuppressWarnings("unchecked")
    private List<DTO> runQuery(Ii studyProtocolIi, StringBuffer hql) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session.createQuery(hql.toString());
        query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
        List<BO> queryList = query.list();

        List<DTO> resultList = new ArrayList<DTO>();
        for (BO sc : queryList) {
            resultList.add(convertFromDomainToDto(sc));
        }
        return resultList;
    }

    /**
     * Get list of StudySites for a given a given functional code.
     * @param studyProtocolIi id of protocol
     * @param dto Object with the functional code criteria
     * @return list dtos
     * @throws PAException on error
     */
    public List<DTO> getByStudyProtocol(Ii studyProtocolIi, DTO dto) throws PAException {
        List<DTO> spDtoList = new ArrayList<DTO>();
        spDtoList.add(dto);
        return getByStudyProtocol(studyProtocolIi, spDtoList);
    }

    /**
     *
     * This method updates the status of StudySite, StudySiteContact
     * or StudyContact when a Structural Role gets updated.
     *
     * @param ii ii of the structural roles
     * @param roleStatusCode role status code
     * @throws PAException on error
     */
    public void cascadeRoleStatus(Ii ii , Cd roleStatusCode) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();

        String roleLink = ROLE_ASSOCIATION_MAP.get(ii.getIdentifierName());

        checkCascadeRoleStatusInput(roleLink);

        StringBuffer hql = new StringBuffer("UPDATE ")
            .append(getTypeArgument().getSimpleName())
            .append(" ss SET ss.statusCode = :newStatus where ss.")
            .append(roleLink)
            .append(" = (select role from ")
            .append(ROLE_CLASS_MAP.get(ii.getIdentifierName()))
            .append(" role where identifier = :myId)");

        session.createQuery(hql.toString())
        .setString("newStatus", newFRStatusCode(roleStatusCode , ActStatusCode.ACTIVE).getName())
        .setString("myId", ii.getExtension())
        .executeUpdate();

        session.flush();

    }

    /**
     * @param roleLink
     * @param roleStatusCode
     * @throws PAException
     */
    private void checkCascadeRoleStatusInput(String roleLink) throws PAException {

        if (roleLink == null) {
            throw new PAException("Error attempting to change for for ii without name");
        }

        String[] validClasses = {"StudySite", "StudyContact", "StudySiteContact"};
        if (!ArrayUtils.contains(validClasses, getTypeArgument().getSimpleName())) {
            throw new PAException("Error attempting to change status for role: " + getTypeArgument().getSimpleName());
        }

        if (!ArrayUtils.contains(CLASS_ROLE_MAP.get(getTypeArgument().getSimpleName()), roleLink)) {
            throw new PAException("Unable to update Role: " + roleLink
                   + " for class : " + getTypeArgument().getSimpleName());
        }

    }

    private FunctionalRoleStatusCode newFRStatusCode(Cd roleStatusCode , ActStatusCode actStatusCode) {
        FunctionalRoleStatusCode returnStatusCode = null;
        StructuralRoleStatusCode roleCode = StructuralRoleStatusCode.getByCode(roleStatusCode.getCode());
        if (StructuralRoleStatusCode.NULLIFIED.equals(roleCode)) {
            returnStatusCode = FunctionalRoleStatusCode.NULLIFIED;
        } else if (ActStatusCode.NULLIFIED.equals(actStatusCode)) {
            returnStatusCode = FunctionalRoleStatusCode.NULLIFIED;
        } else {
            returnStatusCode = FunctionalRoleStatusCode.PENDING;
        }
        return returnStatusCode;
    }

    /**
     *
     * @param roleStatusCode
     * @param actStatusCode
     * @return cd
     */
    Cd getFunctionalRoleStatusCode(Cd roleStatusCode, ActStatusCode actStatusCode) {
        return CdConverter.convertToCd(newFRStatusCode(roleStatusCode, actStatusCode));
    }
}

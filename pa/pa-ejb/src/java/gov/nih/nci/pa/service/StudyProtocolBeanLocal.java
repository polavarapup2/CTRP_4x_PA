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

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.Family;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.NonInterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.PerformedActivity;
import gov.nih.nci.pa.domain.ProgramCode;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StratumGroup;
import gov.nih.nci.pa.domain.StudyCheckout;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyDisease;
import gov.nih.nci.pa.domain.StudyInbox;
import gov.nih.nci.pa.domain.StudyIndlde;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyOnhold;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolAssociation;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.domain.StudyRegulatoryAuthority;
import gov.nih.nci.pa.domain.StudyRelationship;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.dto.OrgFamilyDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.IdentifierType;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudySourceCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.iso.convert.AbstractStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.AnatomicSiteConverter;
import gov.nih.nci.pa.iso.convert.InterventionalStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.NonInterventionalStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.StudyProtocolAssociationConverter;
import gov.nih.nci.pa.iso.convert.StudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolAssociationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.search.StudyProtocolBeanSearchCriteria;
import gov.nih.nci.pa.service.search.StudyProtocolSortCriterion;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.FamilyProgramCodeServiceLocal;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.joda.time.DateMidnight;


/**
 * @author Naveen Amiruddin
 * @since 11/03/2009
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class StudyProtocolBeanLocal extends AbstractBaseSearchBean<StudyProtocol> implements StudyProtocolServiceLocal {

    private static final String UNCHECKED = "unchecked";
    private static final int MAX_SINGLE_EMAIL_OWNERS = 3;
    private static final Logger LOG  = Logger.getLogger(StudyProtocolBeanLocal.class);
    private static final String CREATE = "Create";
    private static final String UPDATE = "Update"; // NOPMD
    private static final int ONE_THOUSAND = 1000;
    @EJB
    private StudyIndldeServiceLocal studyIndldeService;
    
    @EJB
    private RegistryUserServiceLocal registryUserService;
    
    @EJB
//    @IgnoreDependency
    private MailManagerServiceLocal mailManagerService; 
    
    @EJB
    private ProtocolQueryServiceLocal protocolQueryService;

    @EJB
    private FamilyProgramCodeServiceLocal familyProgramCodeService;

    private PAServiceUtils paServiceUtils = new PAServiceUtils();

    private StudyProtocolDTO getStudyProtocolById(Long id) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocol studyProtocol = (StudyProtocol) session.get(StudyProtocol.class, id);

        if (studyProtocol == null) {
            throw new PAException("No matching study protocol for Ii.extension " + id);
        }
        return PADomainUtils.convertStudyProtocol(studyProtocol);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudyProtocolDTO updateStudyProtocol(StudyProtocolDTO studyProtocolDTO) throws PAException {
        // enforce business rules
        if (studyProtocolDTO == null) {
            throw new PAException(" studyProtocolDTO should not be null.");
        }

        enForceBusinessRules(studyProtocolDTO, null);
        Session session = PaHibernateUtil.getCurrentSession();
        Long studyProtocolId = IiConverter.convertToLong(studyProtocolDTO.getIdentifier());
        StudyProtocol sp = (StudyProtocol) session.load(StudyProtocol.class, studyProtocolId);

        StudyProtocolConverter.convertFromDTOToDomain(studyProtocolDTO, sp);

        setDefaultValues(sp, null, UPDATE);
        session.update(sp);
        return StudyProtocolConverter.convertFromDomainToDTO(sp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public InterventionalStudyProtocolDTO getInterventionalStudyProtocol(Ii ii) throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            throw new PAException("Ii should not be null");
        }
        Session session = PaHibernateUtil.getCurrentSession();
        Long studyProtocolId = IiConverter.convertToLong(ii);
        InterventionalStudyProtocol isp =
                (InterventionalStudyProtocol) session.load(InterventionalStudyProtocol.class, studyProtocolId);
        return InterventionalStudyProtocolConverter.convertFromDomainToDTO(isp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterventionalStudyProtocolDTO updateInterventionalStudyProtocol(InterventionalStudyProtocolDTO ispDTO, 
            String page) throws PAException {
        // enforce business rules
        
        if (ispDTO == null) {
            throw new PAException("InterventionalstudyProtocolDTO should not be null");

        }
        enForceBusinessRules(ispDTO, page);
        Session session = PaHibernateUtil.getCurrentSession();
        InterventionalStudyProtocol upd = InterventionalStudyProtocolConverter.convertFromDTOToDomain(ispDTO);
        setDefaultValues(upd, ispDTO, UPDATE);
        session.merge(upd);
        return InterventionalStudyProtocolConverter.convertFromDomainToDTO(upd);
    }
    

    /**
     * {@inheritDoc}
     */
    @Override
    public Ii createInterventionalStudyProtocol(InterventionalStudyProtocolDTO ispDTO) throws PAException {
        if (ispDTO == null) {
            throw new PAException("studyProtocolDTO should not be null.");
        }
        if (ispDTO.getIdentifier() != null && ispDTO.getIdentifier().getExtension() != null) {
            throw new PAException("Extension should be null, but got  = " + ispDTO.getIdentifier().getExtension());

        }
        setStudySource(ispDTO);
            
        enForceBusinessRules(ispDTO, null);
        InterventionalStudyProtocol isp = InterventionalStudyProtocolConverter.convertFromDTOToDomain(ispDTO);
        Session session = PaHibernateUtil.getCurrentSession();
        setDefaultValues(isp, ispDTO, CREATE);
        session.save(isp);
        return IiConverter.convertToStudyProtocolIi(isp.getId());
    }

    private void setStudySource(StudyProtocolDTO dto) {
        Cd src = dto.getStudySource();
        if (src == null || StringUtils.isEmpty(CdConverter.convertCdToString(src))) {
            dto.setStudySource(CdConverter.convertToCd(StudySourceCode.OTHER));
            LOG.warn("Replacing Empty Study Source Audit");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public NonInterventionalStudyProtocolDTO getNonInterventionalStudyProtocol(Ii ii) throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            throw new PAException("Ii should not be null ");
        }
        Session session = PaHibernateUtil.getCurrentSession();
        Long studyProtocolId = IiConverter.convertToLong(ii);
        NonInterventionalStudyProtocol osp =
                (NonInterventionalStudyProtocol) session.load(NonInterventionalStudyProtocol.class, studyProtocolId);
        return NonInterventionalStudyProtocolConverter.convertFromDomainToDTO(osp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NonInterventionalStudyProtocolDTO updateNonInterventionalStudyProtocol(
            NonInterventionalStudyProtocolDTO studyProtocolDTO)
            throws PAException {
        // enforce business rules
        if (studyProtocolDTO == null) {
            throw new PAException(" studyProtocolDTO should not be null.");
        }

        enForceBusinessRules(studyProtocolDTO, null);
        Session session = PaHibernateUtil.getCurrentSession();
        Long studyProtocolId = IiConverter.convertToLong(studyProtocolDTO
                .getIdentifier());
        NonInterventionalStudyProtocol sp = (NonInterventionalStudyProtocol) session
                .load(NonInterventionalStudyProtocol.class, studyProtocolId);

        NonInterventionalStudyProtocolConverter.convertFromDTOToDomain(
                studyProtocolDTO, sp);

        setDefaultValues(sp, null, UPDATE);
        session.update(sp);
        return NonInterventionalStudyProtocolConverter
                .convertFromDomainToDTO(sp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ii createNonInterventionalStudyProtocol(NonInterventionalStudyProtocolDTO ospDTO) throws PAException {
        if (ospDTO == null) {
            throw new PAException("studyProtocolDTO should not be null ");

        }
        if (ospDTO.getIdentifier() != null && ospDTO.getIdentifier().getExtension() != null) {
            throw new PAException("Extension should be null, but got  = " + ospDTO.getIdentifier().getExtension());

        }
        setStudySource(ospDTO);
        enForceBusinessRules(ospDTO, null);
        NonInterventionalStudyProtocol osp = NonInterventionalStudyProtocolConverter.convertFromDTOToDomain(ospDTO);
        Session session = PaHibernateUtil.getCurrentSession();
        setDefaultValues(osp, ospDTO, CREATE);
        session.save(osp);
        return IiConverter.convertToStudyProtocolIi(osp.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteStudyProtocol(Ii ii) throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            throw new PAException("Ii should not be null.");
        }
        try {
            String delete = "DELETE from ";
            String whereClause = " where studyProtocol.id = :id";
            List<String> hqls = new ArrayList<String>();
            hqls.add(delete + StudyOverallStatus.class.getName() + whereClause);
            hqls.add(delete + DocumentWorkflowStatus.class.getName() + whereClause);
            hqls.add(delete + Document.class.getName() + whereClause);
            hqls.add(delete + StudySite.class.getName() + whereClause);
            hqls.add(delete + StudyContact.class.getName() + whereClause);
            hqls.add(delete + StudyResourcing.class.getName() + whereClause);
            hqls.add(delete + Arm.class.getName() + whereClause);
            hqls.add(delete + StudyDisease.class.getName() + whereClause);
            hqls.add(delete + StudyMilestone.class.getName() + whereClause);
            hqls.add(delete + StudyOnhold.class.getName() + whereClause);
            hqls.add(delete + StudySubject.class.getName() + whereClause);
            hqls.add(delete + PerformedActivity.class.getName() + whereClause);
            hqls.add(delete + StudyInbox.class.getName() + whereClause);
            hqls.add(delete + StudyCheckout.class.getName() + whereClause);
            hqls.add(delete + StudyIndlde.class.getName() + whereClause);
            hqls.add(delete + StudyRecruitmentStatus.class.getName() + whereClause);
            hqls.add(delete + StudyRegulatoryAuthority.class.getName() + whereClause);
            hqls.add(delete + StratumGroup.class.getName() + whereClause);
            hqls.add(delete + StudyRelationship.class.getName()
                    + " where sourceStudyProtocol.id = :id or targetStudyProtocol.id = :id");
            hqls.add(delete + StudyProtocol.class.getName() + " where id = :id");


            Long spId = IiConverter.convertToLong(ii);
            Session session = PaHibernateUtil.getCurrentSession();
            session.createSQLQuery("DELETE from study_otheridentifiers where study_protocol_id = "
                    + spId).executeUpdate();
            session.createSQLQuery("DELETE from study_owner where study_id = " + spId).executeUpdate();
            session.createSQLQuery("DELETE from study_anatomic_site where study_protocol_identifier = "
                    + spId).executeUpdate();
            session.createSQLQuery("DELETE from planned_activity where study_protocol_identifier = "
                    + spId).executeUpdate();

            for (String hql : hqls) {
                session.createQuery(hql).setParameter("id", spId).executeUpdate();
            }
        }  catch (HibernateException hbe) {
            throw new PAException("Hibernate exception while deleting ii = "
                    + IiConverter.convertToString(ii) + ".", hbe);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(StudyProtocolDTO studyProtocolDTO) throws PAException {
        // Nothing to do
    }

    private void enForceBusinessRules(StudyProtocolDTO studyProtocolDTO, String page) throws PAException {
        boolean dateRulesApply = false;

        ActStatusCode ascStatusCode = null;
        if (!ISOUtil.isCdNull(studyProtocolDTO.getStatusCode())) {
            ascStatusCode = ActStatusCode.getByCode(studyProtocolDTO.getStatusCode().getCode());
        }
        if (ISOUtil.isIiNull(studyProtocolDTO.getIdentifier()) && ascStatusCode != null
                && ascStatusCode.equals(ActStatusCode.ACTIVE)) {
            dateRulesApply = true;
        }
        if (!ISOUtil.isIiNull(studyProtocolDTO.getIdentifier())) {
            Long studyProtocolId = Long.valueOf(studyProtocolDTO.getIdentifier().getExtension());            
            Session session = PaHibernateUtil.getCurrentSession();
            StudyProtocol oldBo = (StudyProtocol) session.get(StudyProtocol.class, studyProtocolId);
            if (BooleanUtils.isFalse(oldBo.getProprietaryTrialIndicator())) {
                StudyProtocolDates oldDates = oldBo.getDates();
                StudyProtocol newBo = StudyProtocolConverter.convertFromDTOToDomain(studyProtocolDTO);
                StudyProtocolDates newDates = newBo.getDates();
                if (!oldDates.equals(newDates)) {
                    dateRulesApply = true;
                }
            }
        }

        if (dateRulesApply) {
            enForceDateRules(studyProtocolDTO);
        } else {
            enforceAllowedDateTypes(extractStudyProtocolDatesFromDTO(studyProtocolDTO));
        }
        
        enForcePrimaryPurposeRules(studyProtocolDTO);
        if (isCorrelationRuleRequired(studyProtocolDTO) && page == null) {
            List<StudyIndldeDTO> list = getStudyIndldeService().getByStudyProtocol(studyProtocolDTO.getIdentifier());
            if (paServiceUtils.containsNonExemptInds(list)) {
                throw new PAException("Unable to set FDARegulatedIndicator to 'No', "
                        + " Please remove IND/IDEs and try again");
            }
        }

    }

    /**
     * @param studyProtocolDTO
     * @return
     */
    private boolean isCorrelationRuleRequired(StudyProtocolDTO studyProtocolDTO) {
        Boolean ctGovIndicator = BlConverter.convertToBoolean(studyProtocolDTO.getCtgovXmlRequiredIndicator());
        return BooleanUtils.isTrue(ctGovIndicator) && (studyProtocolDTO.getIdentifier() != null
                && studyProtocolDTO.getFdaRegulatedIndicator() != null)
                && (studyProtocolDTO.getFdaRegulatedIndicator().getValue() != null)
                && (!Boolean.valueOf(studyProtocolDTO.getFdaRegulatedIndicator().getValue()));
    }

    private void enForceDateRules(StudyProtocolDTO studyProtocolDTO) throws PAException {
        StudyProtocolDates dates = extractStudyProtocolDatesFromDTO(studyProtocolDTO);
        enforceAllowedDateTypes(dates);
        boolean unknownPrimaryCompletionDate = ISOUtil
                .isTsNull(studyProtocolDTO.getPrimaryCompletionDate());
        DateMidnight today = new DateMidnight();
        checkRequiredDates(dates, unknownPrimaryCompletionDate, studyProtocolDTO);
        DateMidnight startDate = new DateMidnight(dates.getStartDate());
        checkDateAndType(today, startDate, dates.getStartDateTypeCode(), "start date");
        if (unknownPrimaryCompletionDate) {
            if (dates.getPrimaryCompletionDateTypeCode() != ActualAnticipatedTypeCode.NA
                    && dates.getPrimaryCompletionDateTypeCode() != ActualAnticipatedTypeCode.ANTICIPATED) {
                throw new PAException(
                        "Unknown primary completion dates must be marked as Anticipated or N/A. ");
            }
        } else {
            DateMidnight primaryCompletionDate = new DateMidnight(dates.getPrimaryCompletionDate());
            checkDateAndType(today, primaryCompletionDate, dates.getPrimaryCompletionDateTypeCode(),
                             "primary completion date");
            if (primaryCompletionDate.isBefore(startDate)) {
                throw new PAException("Primary completion date must be >= start date.");
            }
            if (dates.getCompletionDate() != null) {
                DateMidnight completionDate = new DateMidnight(dates.getCompletionDate());
                if (primaryCompletionDate.isAfter(completionDate)) {
                    throw new PAException("Completion date must be >= Primary completion date.");
                }
            }
        }
    }

    /**
     * @param studyProtocolDTO
     * @return
     */
    private StudyProtocolDates extractStudyProtocolDatesFromDTO(
            StudyProtocolDTO studyProtocolDTO) {
        return AbstractStudyProtocolConverter
                .convertDatesToDomain(studyProtocolDTO);
    }

    private void enforceAllowedDateTypes(StudyProtocolDates dates)
            throws PAException {
        if (dates.getPrimaryCompletionDateTypeCode() == ActualAnticipatedTypeCode.NA
                && dates.getPrimaryCompletionDate() != null) {
            throw new PAException(
                    "If primary completion date is specified, its type cannot be 'N/A'");
        }
        if (dates.getCompletionDateTypeCode() == ActualAnticipatedTypeCode.NA) {
            throw new PAException("Completion date cannot have type of 'N/A'");
        }
        if (dates.getStartDateTypeCode() == ActualAnticipatedTypeCode.NA) {
            throw new PAException("Start date cannot have type of 'N/A'");
        }
    }

    private void checkRequiredDates(StudyProtocolDates dates, boolean unknownPrimaryCompletionDate, 
            StudyProtocolDTO studyProtocolDTO) throws PAException {
        if (dates.getStartDate() == null) {
            throw new PAException("Start date must be set.  ");
        }
        //don't validate primary completion date if it is non interventional trial 
        //and CTGovXmlRequired is false.
        if (PAUtil.isPrimaryCompletionDateRequired(studyProtocolDTO)) {
            if (dates.getPrimaryCompletionDate() == null && !unknownPrimaryCompletionDate) {
                throw new PAException("Primary Completion date must be set.  ");
            }
            if (dates.getPrimaryCompletionDateTypeCode() == null) {
                throw new PAException("Primary Completion date type must be set.  ");
            }        
        }               
        if (dates.getStartDateTypeCode() == null) {
            throw new PAException("Start date type must be set.  ");
        }        
        if (dates.getCompletionDate() != null && dates.getCompletionDateTypeCode() == null) {
            throw new PAException("Completion date type must be set.  ");
        }
    }

    private void checkDateAndType(DateMidnight today, DateMidnight date, ActualAnticipatedTypeCode type, String field)
            throws PAException {
        if (type == ActualAnticipatedTypeCode.ACTUAL && today.isBefore(date)) {
            throw new PAException(MessageFormat.format("Actual {0} cannot be in the future.  ", field));
        }
        if (type == ActualAnticipatedTypeCode.ANTICIPATED && today.isAfter(date)) {
            throw new PAException(MessageFormat.format("Anticipated {0} must be in the future.  ", field));
        }
    }

    private void enForcePrimaryPurposeRules(StudyProtocolDTO studyProtocolDTO) throws PAException {
        if (studyProtocolDTO.getPrimaryPurposeCode() == null) {
            throw new PAException("Primary Purpose Code must be set.");
        } else if (PrimaryPurposeCode.getByCode(CdConverter.convertCdToString(
                studyProtocolDTO.getPrimaryPurposeCode())) == null) {
            throw new PAException("Invalid Primary Purpose Code.");
        } else {
            enForcePrimaryPurposeOtherRules(studyProtocolDTO);
        }
    }

    private void enForcePrimaryPurposeOtherRules(StudyProtocolDTO studyProtocolDTO) throws PAException {
        if (PrimaryPurposeCode.OTHER.equals(PrimaryPurposeCode.getByCode(CdConverter.convertCdToString(studyProtocolDTO
            .getPrimaryPurposeCode())))
                && StringUtils.isBlank(StConverter.convertToString(studyProtocolDTO.getPrimaryPurposeOtherText()))) {
            throw new PAException("Primary Purpose Other Text is required when Primary Purpose Code is Other.");
        } else if (PrimaryPurposeCode.OTHER.equals(PrimaryPurposeCode.getByCode(CdConverter
            .convertCdToString(studyProtocolDTO.getPrimaryPurposeCode())))
                && PrimaryPurposeAdditionalQualifierCode.getByCode(CdConverter.convertCdToString(studyProtocolDTO
                    .getPrimaryPurposeAdditionalQualifierCode())) == null) {
            throw new PAException(
                    "Valid Primary Purpose Additional Qualifier Code is required when Primary Purpose Code is Other.");
        }
    }

    private void setDefaultValues(StudyProtocol sp, StudyProtocolDTO spDTO, String operation) {
        if (sp.getStatusCode() == null) {
            sp.setStatusCode(ActStatusCode.ACTIVE);
        }
        if (sp.getStatusDate() == null) {
            sp.setStatusDate(new Timestamp((new Date()).getTime()));
        }

        if (CREATE.equals(operation)) {
            User user = null;
            try {
                user =
                        spDTO.getUserLastCreated() != null ? CSMUserService.getInstance()
                            .getCSMUser(spDTO.getUserLastCreated().getValue()) : CSMUserService.getInstance()
                            .getCSMUser(UsernameHolder.getUser());
            } catch (PAException e) {
                LOG.info("Unable to set User for auditing", e);
            }
            sp.setUserLastCreated(user);
            sp.setDateLastCreated(new Timestamp((new Date()).getTime()));
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyProtocolDTO> search(StudyProtocolDTO dto, LimitOffset pagingParams) throws PAException,
            TooManyResultsException {
        if (dto == null) {
            throw new PAException("StudyProtocolDTO should not be null.");
        }

        StudyProtocol criteria = new StudyProtocol();
        criteria.setPhaseCode(PhaseCode.getByCode(CdConverter.convertCdToString(dto.getPhaseCode())));
        criteria.setOfficialTitle(StConverter.convertToString(dto.getOfficialTitle()));
        criteria.setPublicTitle(StConverter.convertToString(dto.getPublicTitle()));
        criteria.setStatusCode(ActStatusCode.getByCode(CdConverter.convertCdToString(dto.getStatusCode())));
        criteria.setOtherIdentifiers(DSetConverter.convertDsetToIiSet(dto.getSecondaryIdentifiers()));
        criteria.setSummary4AnatomicSites(AnatomicSiteConverter.convertToSet(dto.getSummary4AnatomicSites()));
        StudySite ss = generateIdentifierAssigner(dto.getIdentifier());
        if (ss != null) {
            criteria.getStudySites().add(ss);
        }
        int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
        PageSortParams<StudyProtocol> params =
                new PageSortParams<StudyProtocol>(maxLimit, pagingParams.getOffset(),
                        StudyProtocolSortCriterion.STUDY_PROTOCOL_ID, false);
        StudyProtocolBeanSearchCriteria crit;
        
        if (CollectionUtils.isNotEmpty(dto.getProcessingStatuses())) {
            crit = new StudyProtocolBeanSearchCriteria(criteria, dto.getProcessingStatuses());
        } else {
            crit = new StudyProtocolBeanSearchCriteria(criteria);
        }
        List<StudyProtocol> results = search(crit, params);
        return convertFromDomainToDTO(results);
    }

    private StudySite generateIdentifierAssigner(Ii identifier) {
        StudySite ss = null;
        if (isNonDbStudyProtocolIdentifier(identifier)) {
            ss = new StudySite();
            ss.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
            ss.setLocalStudyProtocolIdentifier(identifier.getExtension());
            if (StringUtils.equals(identifier.getRoot(), IiConverter.CTEP_STUDY_PROTOCOL_ROOT)) {
                ss.setResearchOrganization(PADomainUtils.createROExampleObjectByOrgName(PAConstants.CTEP_ORG_NAME));
            }
            if (StringUtils.equals(identifier.getRoot(), IiConverter.DCP_STUDY_PROTOCOL_ROOT)) {
                ss.setResearchOrganization(PADomainUtils.createROExampleObjectByOrgName(PAConstants.DCP_ORG_NAME));
            }
            if (StringUtils.equals(identifier.getRoot(), IiConverter.CCR_STUDY_PROTOCOL_ROOT)) {
                ss.setResearchOrganization(PADomainUtils.createROExampleObjectByOrgName(PAConstants.CCR_ORG_NAME));
            }
            if (StringUtils.equals(identifier.getRoot(), IiConverter.NCT_STUDY_PROTOCOL_ROOT)) {
                ss.setResearchOrganization(PADomainUtils.createROExampleObjectByOrgName(PAConstants.CTGOV_ORG_NAME));
            }
        }
        return ss;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyProtocolDTO> getAbstractedCollaborativeTrials() throws PAException {
        List<StudyProtocol> collaborativeTrials = new ArrayList<StudyProtocol>();
        List<DocumentWorkflowStatusCode> statuses =
                Arrays.asList(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE,
                              DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        // Get all DCP trials
        StudyProtocolBeanSearchCriteria crit =
                new StudyProtocolBeanSearchCriteria(getCollaborativeTrialCriteria(PAConstants.DCP_ORG_NAME), statuses);
        collaborativeTrials.addAll(search(crit));
        // Then get all CTEP trials
        crit = new StudyProtocolBeanSearchCriteria(getCollaborativeTrialCriteria(PAConstants.CTEP_ORG_NAME), statuses);
        collaborativeTrials.addAll(search(crit));
        return convertFromDomainToDTO(collaborativeTrials);
    }

    private StudyProtocol getCollaborativeTrialCriteria(String sponsorName) {
        StudyProtocol sp = new StudyProtocol();
        StudySite ss = new StudySite();
        ss.setFunctionalCode(StudySiteFunctionalCode.SPONSOR);
        ResearchOrganization ro = new ResearchOrganization();
        Organization org = new Organization();
        org.setName(sponsorName);
        ro.setOrganization(org);
        ss.setResearchOrganization(ro);
        sp.getStudySites().add(ss);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        return sp;
    }

    private List<StudyProtocolDTO> convertFromDomainToDTO(
            List<StudyProtocol> studyProtocolList) {
        List<StudyProtocolDTO> studyProtocolDTOList = new ArrayList<StudyProtocolDTO>();
        for (StudyProtocol sp : studyProtocolList) {
            StudyProtocolDTO studyProtocolDTO = PADomainUtils.convertStudyProtocol(sp);
            studyProtocolDTOList.add(studyProtocolDTO);
        }
        return studyProtocolDTOList;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void changeOwnership(StudyProtocolDTO studyProtocolDTO) throws PAException {
        //Intentionally left blank. This method is unused and should be removed in future releases.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> changeOwnership(Ii id, DSet<Tel> owners) throws PAException {
        if (ISOUtil.isIiNull(id)) {
            throw new PAException("Protocol identifier (Ii) must not be null");
        }
        Collection<String> unmatched = new ArrayList<String>();
        if (owners != null) {
            Long studyProtocolId = IiConverter.convertToLong(id);
            for (RegistryUser user : registryUserService
                    .getAllTrialOwners(studyProtocolId)) {
                registryUserService.removeOwnership(user.getId(),
                        studyProtocolId);
            }
            final Set<Tel> telecomAddrs = owners.getItem();
            if (telecomAddrs != null) {
                unmatched.addAll(setTrialOwners(studyProtocolId, telecomAddrs));
            }
        }
        return unmatched;
    }

    /**
     * @param studyProtocolId
     * @param telecomAddrs
     * @throws PAException
     */
    private Collection<String> setTrialOwners(Long studyProtocolId,
            final Set<Tel> telecomAddrs) throws PAException {
        Collection<String> unmatchedEmails = new ArrayList<String>();
        for (Tel tel : telecomAddrs) {
            String email = PAUtil.getEmail(tel);
            if (!PAUtil.isValidEmail(email)) {
                unmatchedEmails.add(email);
                continue;
            }
            List<RegistryUser> users = registryUserService
                    .getLoginNamesByEmailAddress(email);
            if (CollectionUtils.isEmpty(users)) {
                unmatchedEmails.add(email);
            } else {
                // PO-5892: to handle the situation when there are way too many users associated with a single
                // email address. This is the case on non-production tiers: hundreds of de-identified users
                // have example@example.com as email address. To reduce stress on the system, we'll trim
                // the collection to, say, 3 elements.                
                for (RegistryUser user : (users.size() > MAX_SINGLE_EMAIL_OWNERS ? users
                        .subList(0, MAX_SINGLE_EMAIL_OWNERS) : users)) {
                    registryUserService.assignOwnership(user.getId(),
                            studyProtocolId);
                }
            }
        }
        handleUnmatchedEmails(studyProtocolId, unmatchedEmails);
        return unmatchedEmails;
    }

    /**
     * Sends a warning email to CTRO telling about unmatched trial record owners.
     * @param studyProtocolId  the studyProtocolId
     * @param emails the emails
     */
    private void handleUnmatchedEmails(Long studyProtocolId,
            Collection<String> emails) {
        if (!emails.isEmpty()) {
            try {                
                mailManagerService.sendUnidentifiableOwnerEmail(
                        studyProtocolId, emails);
            } catch (Exception e) {
                LOG.error("Unable to send an email to CTRO", e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyProtocolDTO getStudyProtocol(Ii studyProtocolIi) throws PAException {
        StudyProtocolDTO studyProtocolDTO = null;
        if (ISOUtil.isIiNull(studyProtocolIi)) {
            throw new PAException(
                    "When searching for a study protocol, please provide a valid non-empty study identifier.");
        }

        if (isNCIIdentifier(studyProtocolIi)) {
            studyProtocolDTO = searchStudyProtocolByIi(studyProtocolIi);
        } else if (isNonDbStudyProtocolIdentifier(studyProtocolIi)) {
            studyProtocolDTO = getStudyProtocolByIi(studyProtocolIi);
        } else if (NumberUtils.isNumber(studyProtocolIi.getExtension())) {
            studyProtocolDTO = getStudyProtocolById(Long.valueOf(studyProtocolIi.getExtension()));
        }
        return studyProtocolDTO;
    }


    private StudyProtocolDTO searchStudyProtocolByIi(Ii studyProtocolIi) throws PAException {
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<StudyProtocolDTO> spList;
        try {
            StudyProtocolDTO spDTO = new StudyProtocolDTO();
            spDTO.setSecondaryIdentifiers(new DSet<Ii>());
            spDTO.getSecondaryIdentifiers().setItem(new HashSet<Ii>());
            spDTO.getSecondaryIdentifiers().getItem().add(studyProtocolIi);
            spDTO.setStatusCode(CdConverter.convertToCd(EntityStatusCode.ACTIVE));
            spList = search(spDTO, limit);
        } catch (TooManyResultsException e) {
            throw new PAException("found too many trials with this identifier " + studyProtocolIi.getExtension()
                    + " when only 1 expected.", e);
        }
        checkResults(spList, studyProtocolIi);
        return spList.get(0);
    }

    @SuppressWarnings(UNCHECKED)
    private StudyProtocolDTO getStudyProtocolByIi(Ii studyProtocolIi) throws PAException {
        List<StudyProtocol> results = searchProtocolsByIdentifier(studyProtocolIi);
        CollectionUtils.filter(results, new Predicate() {
            @Override
            public boolean evaluate(Object arg0) {
                StudyProtocol sp = (StudyProtocol) arg0;
                return sp.getDocumentWorkflowStatuses().isEmpty()
                        || (!DocumentWorkflowStatusCode.REJECTED.equals(sp
                                .getDocumentWorkflowStatuses().iterator()
                                .next().getStatusCode()));
            }
        });
        checkResults(results, studyProtocolIi);
        
        final StudyProtocol sp = results.get(0);
        StudyProtocolDTO studyProtocolDTO = PADomainUtils.convertStudyProtocol(sp);        
        return studyProtocolDTO; // NOPMD
    }

    /**
     * @param studyProtocolIi
     * @return
     * @throws HibernateException HibernateException
     */
    @SuppressWarnings("unchecked")
    private List<StudyProtocol> searchProtocolsByIdentifier(
            Ii studyProtocolIi)  {
        StudySite ss = generateIdentifierAssigner(studyProtocolIi);

        Criteria criteria = PaHibernateUtil.getCurrentSession().createCriteria(StudyProtocol.class);
        criteria.createAlias("studySites", "ss").createAlias("ss.researchOrganization", "ro")
            .createAlias("ro.organization", "org");
        criteria.add(Restrictions.eq("org.name",
                ss.getResearchOrganization().getOrganization().getName()));
        criteria.add(Restrictions.eq("ss.localStudyProtocolIdentifier", ss.getLocalStudyProtocolIdentifier()));
        criteria.add(Restrictions.ne("statusCode", ActStatusCode.INACTIVE));

        List<StudyProtocol> results = criteria.list();
        return results; // NOPMD
    }

    

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void checkResults(List<?> results, Ii ii) throws PAException {
        if (results.isEmpty()) {
            throw new PAException("Could not find any trials with this identifier " + ii.getExtension()
                    + " and this root " + ii.getRoot());
        } else if (new HashSet(results).size() != 1) {
            throw new PAException("Found multiple trials with this identifier " + ii.getExtension()
                    + " and this root " + ii.getRoot());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyProtocolDTO loadStudyProtocol(Ii ii) {
        StudyProtocolDTO studyProtocolDTO = null;
        if (ISOUtil.isIiNull(ii)) {
           return studyProtocolDTO;
        }
        try {
            if (isNCIIdentifier(ii)) {
                studyProtocolDTO = searchStudyProtocolByIi(ii);
            } else if (isNonDbStudyProtocolIdentifier(ii)) {
                studyProtocolDTO = getStudyProtocolByIi(ii);
            } else if (NumberUtils.isNumber(ii.getExtension())) {
                studyProtocolDTO = getStudyProtocolById(Long.valueOf(ii.getExtension()));
            }
        } catch (PAException e) {
            LOG.error("An error has occurred while trying to lookup the study protocol ii " + ii.getExtension()
                    + " with the root " + ii.getRoot(), e);
        }
        return studyProtocolDTO;
    }

    /**
     * Determines whether the given ii is either a DCP id, CTEP Id, NCT id.
     * @param studyProtocolIi
     * @return true iff the given ii is not a DB assigned id
     */
    private boolean isNonDbStudyProtocolIdentifier(Ii studyProtocolIi) {
        return studyProtocolIi != null
                && (StringUtils.equals(studyProtocolIi.getRoot(), IiConverter.DCP_STUDY_PROTOCOL_ROOT)
                        || StringUtils.equals(studyProtocolIi.getRoot(), IiConverter.CTEP_STUDY_PROTOCOL_ROOT)
                        || StringUtils.equals(studyProtocolIi.getRoot(), IiConverter.NCT_STUDY_PROTOCOL_ROOT));
    }

    /**
     * Determines whether the given ii is an NCI assigned id.
     * @param studyProtocolIi
     * @return true iff the given ii is an NCI assigned id.
     */
    private boolean isNCIIdentifier(Ii studyProtocolIi) {
        return studyProtocolIi != null
                && (StringUtils.equals(studyProtocolIi.getRoot(), IiConverter.STUDY_PROTOCOL_ROOT)
                && StringUtils.startsWith(studyProtocolIi.getExtension(), "NCI"));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings(UNCHECKED)
    public Map<Long, String> getTrialNciId(List<Long> listOfTrialIDs) {
        Session session = PaHibernateUtil.getCurrentSession();
        Map<Long, String> resultSet = new HashMap<Long, String>();
        List<Object[]> queryList = null;
        if (!listOfTrialIDs.isEmpty()) {
            SQLQuery query = session
            .createSQLQuery("select so.study_protocol_id, so.extension, " 
                    + "so.root from study_otheridentifiers as so " 
                    + "join study_protocol as sp on sp.identifier=so.study_protocol_id where sp.status_code ='ACTIVE'"
                    + " and so.study_protocol_id IN (:ids)"
                    + " and so.root = '"
                    + IiConverter.STUDY_PROTOCOL_ROOT
                    + "' order by so.root");
            
            query.setParameterList("ids", listOfTrialIDs);
            
            queryList = query.list();
            
            for (Object[] oArr : queryList) {
                BigInteger ret = null;
                if (oArr[0] instanceof BigInteger) { 
                    ret =  (BigInteger) oArr[0];
                    if (oArr[1] != null) {
                        resultSet.put(ret.longValue(), oArr[1].toString());
                    }
                }       
            }
        }
        return resultSet;
    }
    
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings(UNCHECKED)
    public Map<Long, String> getTrialProcessingStatus(List<Long> listOfTrialIDs) {
        Session session = PaHibernateUtil.getCurrentSession();
        Map<Long, String> resultSet = new HashMap<Long, String>();
        List<Object[]> queryList = null;
        for (Long identifier : listOfTrialIDs) {
            SQLQuery query = session
            .createSQLQuery("select dws.study_protocol_identifier,"
                    + " dws.status_code from document_workflow_status as dws"
                    + " WHERE dws.study_protocol_identifier = (select"
                    + " sp.identifier from study_protocol as sp where sp.identifier = (:id)"
                    + " and sp.status_code ='ACTIVE')"
                    + " order by dws.identifier desc LIMIT 1");
            query.setParameter("id", identifier);
            queryList = query.list();
            
            for (Object[] oArr : queryList) {
                BigInteger ret = null;
                if (oArr[0] instanceof BigInteger) { 
                    ret =  (BigInteger) oArr[0];
                    if (oArr[1] != null) {
                        resultSet.put(ret.longValue(), oArr[1].toString());
                    }
                }       
            }
        }
        return resultSet;
    
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings(UNCHECKED)
    public List<Long> getProtocolIdsWithNCIId(String nciId) {
        Session session = PaHibernateUtil.getCurrentSession();
        List<Long> resultSet = new ArrayList<Long>();
        List<Object> queryList = null;
        SQLQuery query = session
        .createSQLQuery("select DISTINCT(so.study_protocol_id) " 
                + " from study_otheridentifiers as so " 
                + " join study_protocol as sp on sp.identifier=so.study_protocol_id "
                + " join planned_activity as pa on pa.study_protocol_identifier= sp.identifier"
                + " join planned_marker as pm on pm.identifier = pa.identifier" 
                + " where sp.status_code ='ACTIVE'"
                + " and UPPER(so.extension) like UPPER(:nciId)"
                + " and so.root = '"
                + IiConverter.STUDY_PROTOCOL_ROOT
                + "'");
       
        query.setParameter("nciId", "%" + nciId + "%");
        queryList = query.list();
        for (Object oArr : queryList) {
            BigInteger ret = null;
            if (oArr instanceof BigInteger) { 
                ret =  (BigInteger) oArr;
                if (oArr != null) {
                    resultSet.add(ret.longValue());
                }
            }       
        }
        return resultSet;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings(UNCHECKED)
    public List<Long> getNonRejectedByPublicTitle(String publicTitle) {
        Session session = PaHibernateUtil.getCurrentSession();
        List<Long> resultSet = new ArrayList<Long>();
        List<Object> queryList = null;
        SQLQuery query = session.createSQLQuery("select DISTINCT(sp.identifier) "
                + " from study_protocol as sp where sp.status_code ='ACTIVE'"
                + " and UPPER(sp.public_tittle) = UPPER(:title)"
                + " and sp.identifier not in (select study_protocol_identifier from rv_dwf_current "
                + " where status_code='REJECTED')");
        query.setParameter("title", publicTitle);
        queryList = query.list();
        for (Object oArr : queryList) {
            BigInteger ret = null;
            if (oArr instanceof BigInteger) {
                ret = (BigInteger) oArr;
                if (oArr != null) {
                    resultSet.add(ret.longValue());
                }
            }
        }
        return resultSet;
    }

    /**
     * @param studyIndldeService the studyIndldeService to set
     */
    public void setStudyIndldeService(StudyIndldeServiceLocal studyIndldeService) {
        this.studyIndldeService = studyIndldeService;
    }

    /**
     * @return the studyIndldeService
     */
    public StudyIndldeServiceLocal getStudyIndldeService() {
        return studyIndldeService;
    }

    /**
     * @return the registryUserService
     */
    public RegistryUserServiceLocal getRegistryUserService() {
        return registryUserService;
    }

    /**
     * @param registryUserService the registryUserService to set
     */
    public void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }


    /**
     * @return the mailManagerService
     */
    public MailManagerServiceLocal getMailManagerService() {
        return mailManagerService;
    }

    /**
     * @param mailManagerService the mailManagerService to set
     */
    public void setMailManagerService(MailManagerServiceLocal mailManagerService) {
        this.mailManagerService = mailManagerService;
    }

    @SuppressWarnings(UNCHECKED)
    @Override
    public List<StudyProtocolAssociationDTO> getTrialAssociations(Long studyId)
            throws PAException {
        List<StudyProtocolAssociationDTO> list = new ArrayList<StudyProtocolAssociationDTO>();
        StudyProtocolAssociationConverter converter = new StudyProtocolAssociationConverter();
        
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();
        
        Query query = session.createQuery("from "
                + StudyProtocolAssociation.class.getSimpleName()
                + " spa where spa.studyProtocolA.id=" + studyId
                + " or spa.studyProtocolB.id=" + studyId);
        List<StudyProtocolAssociation> associations = query.list();
        for (StudyProtocolAssociation spa : associations) {
            if (spa.getStudyProtocolB() == null) {
                list.add(converter.convertFromDomainToDto(spa));
            } else {
                StudyProtocolAssociationDTO dto = converter
                        .convertFromDomainToDto(spa);
                StudyProtocol sp = spa.getStudyProtocolA().getId()
                        .equals(studyId) ? spa.getStudyProtocolB() : spa
                        .getStudyProtocolA();
                populateAssociationDetails(dto, sp.getId());
                list.add(dto);
            }
        }
        return list;
    }

    private void populateAssociationDetails(StudyProtocolAssociationDTO dto,
            Long spId) {
        Session session = PaHibernateUtil.getCurrentSession();
        session.clear();
        StudyProtocol sp = (StudyProtocol) session.get(StudyProtocol.class,
                spId);
        dto.setStudyIdentifier(StConverter.convertToSt(getTrialNciId(
                Arrays.asList(sp.getId())).get(sp.getId())));
        dto.setIdentifierType(CdConverter.convertToCd(IdentifierType.NCI));
        dto.setStudyProtocolType(CdConverter
                .convertToCd(sp instanceof NonInterventionalStudyProtocol ? StudyTypeCode.NON_INTERVENTIONAL
                        : StudyTypeCode.INTERVENTIONAL));
        dto.setStudySubtypeCode(CdConverter
                .convertToCd(sp instanceof NonInterventionalStudyProtocol ? ((NonInterventionalStudyProtocol) sp)
                        .getStudySubtypeCode() : null));
        dto.setOfficialTitle(StConverter.convertToSt(sp.getOfficialTitle()));
    }

    @Override
    public void createPendingTrialAssociation(
            StudyProtocolAssociationDTO trialAssociation) throws PAException {

        try {
            Session session = PaHibernateUtil.getCurrentSession();
            StudyProtocolAssociation bo = new StudyProtocolAssociationConverter()
                    .convertFromDtoToDomain(trialAssociation);
            session.save(bo);
            session.flush();
            updatePendingTrialAssociationToActive(bo);
        } catch (ConstraintViolationException e) {
            throw new PAValidationException("Association already exists");   // NOPMD         
        }
    }

    @Override
    public void deleteTrialAssociation(Ii id) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocolAssociation bo = (StudyProtocolAssociation) session.get(
                StudyProtocolAssociation.class,
                IiConverter.convertToLong(id));
        if (bo != null) {
            session.delete(bo);
        }

    }

    @Override
    public StudyProtocolAssociationDTO getTrialAssociation(long id)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocolAssociation bo = (StudyProtocolAssociation) session.get(
                StudyProtocolAssociation.class, id);
        if (bo != null) {
            return new StudyProtocolAssociationConverter()
                    .convertFromDomainToDto(bo);
        } else {
            return null;
        }
    }

    @Override
    public void update(StudyProtocolAssociationDTO association)
            throws PAException {
        try {
            Session session = PaHibernateUtil.getCurrentSession();
            StudyProtocolAssociation bo = new StudyProtocolAssociationConverter()
                    .convertFromDtoToDomain(association);
            session.merge(bo);
            session.flush();
            updatePendingTrialAssociationToActive(bo);
        } catch (ConstraintViolationException e) {
            throw new PAValidationException("Association already exists");   // NOPMD         
        }
    }

    private void updatePendingTrialAssociationToActive(
            StudyProtocolAssociation spa) throws PAException {
        if (spa.getStudyProtocolB() != null) {
            // already active.
            return;
        }
        if (StringUtils.isNotBlank(spa.getStudyIdentifier())
                && spa.getIdentifierType() != null) {
            StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
            criteria.setIdentifierType(spa.getIdentifierType().getCode());
            criteria.setIdentifier(spa.getStudyIdentifier().trim());            
            criteria.setMyTrialsOnly(false);
            List<StudyProtocolQueryDTO> trials = protocolQueryService
                    .getStudyProtocolByCriteria(criteria);
            for (StudyProtocolQueryDTO trialDTO : trials) {
                updatePendingTrialAssociationsToActive(trialDTO
                        .getStudyProtocolId());
            }
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public void createActiveTrialAssociation(Long trialA, Long trialB,
            Long associationToReplace) throws PAException {
        if (associationToReplace != null) {
            deleteTrialAssociation(IiConverter
                    .convertToIi(associationToReplace));
        }
        try {
            Session session = PaHibernateUtil.getCurrentSession();
            Query query = session.createQuery("from "
                    + StudyProtocolAssociation.class.getSimpleName()
                    + " spa where spa.studyProtocolA.id=" + trialA
                    + " and spa.studyProtocolB.id=" + trialB);
            if (CollectionUtils.isEmpty(query.list())) {
                StudyProtocolAssociation bo = new StudyProtocolAssociation();
                StudyProtocol studyProtocolA = new StudyProtocol();
                studyProtocolA.setId(trialA);
                StudyProtocol studyProtocolB = new StudyProtocol();
                studyProtocolB.setId(trialB);
                bo.setStudyProtocolA(studyProtocolA);
                bo.setStudyProtocolB(studyProtocolB);
                session.save(bo);
                session.flush();
            }
        } catch (ConstraintViolationException e) {
            throw new PAValidationException("Association already exists"); // NOPMD
        }
    }

    @Override
    public void updatePendingTrialAssociationsToActive(long studyId) {
        try {
            StudyProtocolQueryDTO queryDTO = protocolQueryService
                    .getTrialSummaryByStudyProtocolId(studyId);
            String leadID = queryDTO.getLocalStudyProtocolIdentifier();
            String nciID = queryDTO.getNciIdentifier();
            String nctID = paServiceUtils.getStudyIdentifier(
                    IiConverter.convertToStudyProtocolIi(studyId),
                    PAConstants.NCT_IDENTIFIER_TYPE);
            String ctepID = null;
            String dcpID = null;
            List<String> otherIDs = new ArrayList<String>();
            
            if (!queryDTO.isProprietaryTrial()) {
                dcpID = paServiceUtils.getStudyIdentifier(
                        IiConverter.convertToStudyProtocolIi(studyId),
                        PAConstants.DCP_IDENTIFIER_TYPE);
                ctepID = paServiceUtils.getStudyIdentifier(
                        IiConverter.convertToStudyProtocolIi(studyId),
                        PAConstants.CTEP_IDENTIFIER_TYPE);
                otherIDs = queryDTO.getOtherIdentifiers() != null ? queryDTO
                        .getOtherIdentifiers() : otherIDs;
            }
            
            updatePendingTrialAssociationsToActive(studyId, leadID, IdentifierType.LEAD_ORG);
            updatePendingTrialAssociationsToActive(studyId, nciID, IdentifierType.NCI);
            updatePendingTrialAssociationsToActive(studyId, nctID, IdentifierType.NCT);
            updatePendingTrialAssociationsToActive(studyId, ctepID, IdentifierType.CTEP);
            updatePendingTrialAssociationsToActive(studyId, dcpID, IdentifierType.DCP);
            updatePendingTrialAssociationsToActive(studyId, dcpID, IdentifierType.CCR);
            for (String otherID : otherIDs) {
                updatePendingTrialAssociationsToActive(studyId, otherID, IdentifierType.OTHER_IDENTIFIER);    
            }            
        } catch (PAException e) {
            LOG.error(e, e);
        }
    }

    @SuppressWarnings(UNCHECKED)
    private void updatePendingTrialAssociationsToActive(long studyId,
            String identifier, IdentifierType idType) throws PAException {

        if (StringUtils.isBlank(identifier)) {
            return;
        }
        Session session = PaHibernateUtil.getCurrentSession();
        Query query = session
                .createQuery("from "
                        + StudyProtocolAssociation.class.getSimpleName()
                        + " spa where spa.studyProtocolB is null and spa.studyIdentifier=? and spa.identifierType=?");
        query.setParameter(0, identifier);
        query.setParameter(1, idType);
        List<StudyProtocolAssociation> associations = query.list();
        for (StudyProtocolAssociation spa : associations) {
            createActiveTrialAssociation(spa.getStudyProtocolA().getId(),
                    studyId, spa.getId());
        }
    }

    /**
     * The familyProgramCodeService
     * @return the familyProgramCodeService
     */
    public FamilyProgramCodeServiceLocal getFamilyProgramCodeService() {
        return familyProgramCodeService;
    }

    /**
     * The familyProgramCodeService
     * @param familyProgramCodeService the familyProgramCodeService
     */
    public void setFamilyProgramCodeService(FamilyProgramCodeServiceLocal familyProgramCodeService) {
        this.familyProgramCodeService = familyProgramCodeService;
    }

    /**
     * @return the protocolQueryServiceLocal
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }

    /**
     * @param protocolQueryServiceLocal the protocolQueryServiceLocal to set
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryServiceLocal) {
        this.protocolQueryService = protocolQueryServiceLocal;
    }

    /**
     * @param paServiceUtils the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

    @Override
    public void changeStudyProtocolType(Ii studyProtocolIi,
            StudyTypeCode code) throws PAException {
        
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();

        session.createSQLQuery("update study_protocol set study_protocol_type='"
                + (code == StudyTypeCode.INTERVENTIONAL ? InterventionalStudyProtocol.class
                        .getSimpleName() : NonInterventionalStudyProtocol.class
                        .getSimpleName())
                + "' where identifier="
                + IiConverter.convertToLong(studyProtocolIi)).executeUpdate();
        session.flush();
        session.clear();
        
    }

    @Override
    public void addAnatomicSite(Ii studyProtocolIi, Cd site) throws PAException {
        final AnatomicSite siteDomainObj = AnatomicSiteConverter
                .convertFromDTOToDomain(site);        
        Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocol studyProtocol = (StudyProtocol) session
                .get(StudyProtocol.class,
                        IiConverter.convertToLong(studyProtocolIi));        
        final Set<AnatomicSite> protocolSites = studyProtocol
                .getSummary4AnatomicSites();
        for (AnatomicSite existentSite : protocolSites) {
            if (existentSite.getId().equals(siteDomainObj.getId())) {
                return;
            }
        }
        protocolSites.add(siteDomainObj);
        session.update(studyProtocol);
    }

    @Override
    public void removeAnatomicSite(Ii studyProtocolIi, Cd site)
            throws PAException {
        final AnatomicSite siteDomainObj = AnatomicSiteConverter
                .convertFromDTOToDomain(site);
        Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocol studyProtocol = (StudyProtocol) session
                .get(StudyProtocol.class,
                        IiConverter.convertToLong(studyProtocolIi));
        final Set<AnatomicSite> protocolSites = studyProtocol
                .getSummary4AnatomicSites();
        for (AnatomicSite existentSite : protocolSites) {
            if (existentSite.getId().equals(siteDomainObj.getId())) {
                protocolSites.remove(existentSite);
                session.update(studyProtocol);
                break;
            }
        }
    }
    
    @Override
    public void updateRecordVerificationDate(Long studyProtocolId) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();

        session.createSQLQuery("update study_protocol set record_verification_date='"
                + (new Timestamp(new Date().getTime()))
                + "' where identifier="
                + studyProtocolId).executeUpdate();
        session.flush();
        session.clear();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyProtocolDTO> getStudyProtocolsByNctId(String nctID)
            throws PAException {
        Ii ii = new Ii();
        ii.setExtension(nctID);
        ii.setRoot(IiConverter.NCT_STUDY_PROTOCOL_ROOT);
        return convertFromDomainToDTO(searchProtocolsByIdentifier(ii));
    }
    @Override
    public List<Long> getActiveAndInActiveTrialsByspId(Long id) 
             throws PAException {
         Session session = PaHibernateUtil.getCurrentSession();
         session.flush();
         List<Long> resultSet = new ArrayList<Long>();
         List<Object> queryList = null;
         SQLQuery query = session
                .createSQLQuery("select identifier from study_protocol where identifier in ("
                      + "select study_protocol_id from study_otheridentifiers where extension = "
                      + "(select extension from study_otheridentifiers where study_protocol_id  = :id "
                      + " and root = '"
                      + IiConverter.STUDY_PROTOCOL_ROOT + "')"
                      + " and root = '"
                      + IiConverter.STUDY_PROTOCOL_ROOT
                      + "') and amendment_number != '' ");
         query.setParameter("id", id);
         queryList = query.list();
         for (Object oArr : queryList) {
             BigInteger ret = null;
             if (oArr instanceof BigInteger) { 
                 ret =  (BigInteger) oArr;
                 if (oArr != null) {
                     resultSet.add(ret.longValue());
                 }
             }       
         }
         return resultSet;
    }
    
    @Override
    public boolean updateStudyProtocolResultsDate(Long studyId, String attribute, Timestamp value) {
        Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocol studyProtocol = (StudyProtocol) session
                .get(StudyProtocol.class, studyId);
        switch (attribute) {
        case "pcdSentToPIODate":
            studyProtocol.setPcdSentToPIODate(value);
            break;
        case "pcdConfirmedDate":
            studyProtocol.setPcdConfirmedDate(value);
            break;
        case "desgneeNotifiedDate":
            studyProtocol.setDesgneeNotifiedDate(value);
            break;
        case "reportingInProcessDate":
            studyProtocol.setReportingInProcessDate(value);
            break;
        case "threeMonthReminderDate":
            studyProtocol.setThreeMonthReminderDate(value);
            break;
        case "fiveMonthReminderDate":
            studyProtocol.setFiveMonthReminderDate(value);
            break;
        case "sevenMonthEscalationtoPIODate":
            studyProtocol.setSevenMonthEscalationtoPIODate(value);
            break;
        case "resultsSentToPIODate":
            studyProtocol.setResultsSentToPIODate(value);
            break;
        case "resultsApprovedByPIODate":
            studyProtocol.setResultsApprovedByPIODate(value);
            break;
        case "prsReleaseDate":
            studyProtocol.setPrsReleaseDate(value);
            break;
        case "qaCommentsReturnedDate":
            studyProtocol.setQaCommentsReturnedDate(value);
            break;
        case "trialPublishedDate":
            studyProtocol.setTrialPublishedDate(value);
            break;
        default:
            return false;
        }
        
        session.update(studyProtocol);
        return true;
    }


    /**
     * Will un-assign the given program codes to the study
     *
     * @param studyId     - the study PA identifier
     * @param programCode - a program codes
     * @throws PAException - exception when there is an error.
     */
    @Override
    public void unAssignProgramCode(Long studyId, ProgramCodeDTO programCode) throws PAException {
         unassignProgramCodesFromTrials(Arrays.asList(studyId), Arrays.asList(programCode));
    }

    /**
     * Will assign the given program codes to the study
     *
     * @param studyId          - the study PA identifier
     * @param organizationPoID - the organization PO identifier
     * @param programCodes     - a list of program codes
     * @throws PAException - exception when there is an error.
     */
    public void assignProgramCodes(Long studyId, Long organizationPoID, List<ProgramCodeDTO> programCodes)
            throws PAException {

        //fetch the study
        StudyProtocol studyProtocol = fetchStudyProtocol(studyId);

        List<OrgFamilyDTO> orgFamilyList = FamilyHelper.getByOrgId(organizationPoID);

        if (CollectionUtils.isEmpty(orgFamilyList)) {

            List<String> codes = new ArrayList<String>();
            for (ProgramCodeDTO pgc : programCodes) {
                codes.add(pgc.getProgramCode());
            }

            studyProtocol
                    .setComments(StringUtils.defaultString(studyProtocol
                            .getComments())
                            + (StringUtils.isBlank(studyProtocol.getComments()) ? ""
                                    : " ")
                            + "The following program code value was submitted "
                            + "but not recorded: "
                            + StringUtils.join(codes, ";")
                            + ". "
                            + "Starting in version 4.3.1, CTRP no longer records program codes for trials lead by a "
                            + "non designated cancer center organization.");

        } else {

            //collect familyPOIds and load families
            List<Long> familyPoIds = new ArrayList<Long>();

            for (OrgFamilyDTO orgFamilyDto : orgFamilyList) {
                familyPoIds.add(orgFamilyDto.getId());
            }

            List<Family> families = loadAllFamilies(familyPoIds);
            Map<Long, ProgramCode> validProgramCodeMap = new HashMap<Long, ProgramCode>();
            for (ProgramCodeDTO pgCode : programCodes) {
               for (Family family : families) {
                   ProgramCode pg = family.findActiveProgramCodeByCode(pgCode.getProgramCode());
                   if (pg != null) {
                       validProgramCodeMap.put(pg.getId(), pg);
                   }
               }
            }


            //find program codes to add
            for (ProgramCode pg : studyProtocol.getProgramCodes()) {
                if (validProgramCodeMap.containsKey(pg.getId())) {
                    validProgramCodeMap.remove(pg.getId());
                }
            }

            if (!validProgramCodeMap.isEmpty()) {
                studyProtocol.getProgramCodes().addAll(validProgramCodeMap.values());
            }
        }

        PaHibernateUtil.getCurrentSession().update(studyProtocol);
    }

    /**
     * Will assign program codes to trials
     * @param studyIds - a list of trial ids
     * @param familyPoId - the famailyPoId, where the progam codes are from
     * @param programCodes - program codes
     * @throws PAException - when there ia an error
     */
    @Override
    public void assignProgramCodesToTrials(List<Long> studyIds, Long familyPoId, List<ProgramCodeDTO> programCodes)
            throws PAException {

        Family family = fetchFamily(familyPoId);

        for (Long studyId : studyIds) {
            StudyProtocol studyProtocol = fetchStudyProtocol(studyId);
            addProgramCodesToStudyProtocol(studyProtocol, family, programCodes);
            PaHibernateUtil.getCurrentSession().update(studyProtocol);
        }

    }

    /**
     * Will add the program codes to the given study protocol
     * @param studyProtocol - a trial
     * @param family  - the family
     * @param programCodes - the program codes to add
     * @throws PAException - when there is an error
     */
    private void addProgramCodesToStudyProtocol(StudyProtocol studyProtocol,
                                                Family family, List<ProgramCodeDTO> programCodes)
            throws PAException {
        for (ProgramCodeDTO pgc : programCodes) {
            ProgramCode p = family.findActiveProgramCodeByCode(pgc.getProgramCode());
            if (p == null) {
                LOG.error("Unable to find an active program code in family " + pgc.getProgramCode());
                throw new PAException("Unable to find an active program code in family " + pgc.getProgramCode());
            }
            studyProtocol.getProgramCodes().add(p);
            LOG.info("Added programCode:" + p.getProgramCode() + " to study [studyId:" + studyProtocol.getId() + "]");
        }

    }

    /**
     * Will unassign program codes from trials
     * @param studyIds   - a list of trial ids
     * @param programCodes  - the program codes
     * @throws PAException - when there is an error
     */
    @Override
    public void unassignProgramCodesFromTrials(List<Long> studyIds,
                                               List<ProgramCodeDTO> programCodes) throws PAException {
           for (Long studyId: studyIds) {
               StudyProtocol studyProtocol = fetchStudyProtocol(studyId);
               removeProgramCodesFromStudyProtocol(studyProtocol, programCodes);
               PaHibernateUtil.getCurrentSession().update(studyProtocol);
           }
    }

    /**
     * Will remove the given program codes if present from study protocol
     * @param studyProtocol - a trial
     * @param programCodes - list of program code to remove
     * @return  List of program codes that actually removed
     * @throws PAException - when there is an error
     */
    private List<String> removeProgramCodesFromStudyProtocol(StudyProtocol studyProtocol,
                                                             List<ProgramCodeDTO> programCodes)
            throws PAException {
        List<String> removed = new ArrayList<String>();
        for (ProgramCodeDTO programCode: programCodes) {

            String pgcText = studyProtocol.getProgramCodeText();
            if (StringUtils.isNotEmpty(pgcText)) {
                List<String> validProgramCodes = new ArrayList<String>();
                for (String pgc : pgcText.trim().split("\\s*;\\s*")) {
                    if (!StringUtils.equals(pgc, programCode.getProgramCode())) {
                        validProgramCodes.add(pgc);
                    }
                }
                studyProtocol.setProgramCodeText(StringUtils.join(validProgramCodes, ";"));
            }

            if (CollectionUtils.isNotEmpty(studyProtocol.getProgramCodes())) {
                List<ProgramCode> list = new ArrayList<ProgramCode>();
                for (ProgramCode pg : studyProtocol.getProgramCodes()) {
                    if (programCode.getId() != null) {
                      if (pg.getId().equals(programCode.getId())) {
                          list.add(pg);
                          removed.add(pg.getProgramCode());
                      }
                    } else if (StringUtils.equalsIgnoreCase(pg.getProgramCode(), programCode.getProgramCode())) {
                        list.add(pg);
                        removed.add(pg.getProgramCode());
                    }
                }
                studyProtocol.getProgramCodes().removeAll(list);
            }
        }
        return removed;
    }

    /**
     * Will replace programcodes
     * @param studyIds - a list of trial ids
     * @param familyPoId - the family PO Id
     * @param programCode - a program code to replace
     * @param programCodes - the program codes newly selected
     * @throws PAException - when there is an error
     */
    @Override
    public void replaceProgramCodesOnTrials(List<Long> studyIds,
                                            Long familyPoId,
                                            ProgramCodeDTO programCode,
                                            List<ProgramCodeDTO> programCodes) throws PAException {
        Family family = fetchFamily(familyPoId);
        for (Long studyId : studyIds) {
            StudyProtocol studyProtocol = fetchStudyProtocol(studyId);
            if (!removeProgramCodesFromStudyProtocol(studyProtocol, Arrays.asList(programCode)).isEmpty()) {
                 addProgramCodesToStudyProtocol(studyProtocol, family, programCodes);
            }
            PaHibernateUtil.getCurrentSession().update(studyProtocol);
        }

    }

    /**
     * Will load the study protocol object
     * @param studyId the identifier of the study
     * @return   a StudyProtocol
     * @throws PAException  If study not present in DB
     */
    private StudyProtocol fetchStudyProtocol(Long studyId) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocol studyProtocol = (StudyProtocol) session.get(StudyProtocol.class, studyId);
        if (studyProtocol == null) {
            LOG.error("Unable to find study with the given identifier : " + studyId);
            throw new PAException("Unable to load study protocol with id : " + studyId);
        }
        return studyProtocol;
    }

    /**
     * Will load the Family objects based on PO identifier
     * @param poIds
     * @return
     */
    private List<Family> loadAllFamilies(List<Long> poIds) {
        return (List<Family>) PaHibernateUtil.getCurrentSession()
                .createQuery("select f from Family f where f.poId in (:ids)")
                .setParameterList("ids", poIds)
                .list();
    }

    /**
     * Will load the family from db based on POID
     * @param familyPoId - the PO Id of family
     * @return - Family
     * @throws PAException - when there is an error
     */
    private Family fetchFamily(Long familyPoId) throws PAException {
        List<Family> families = loadAllFamilies(Arrays.asList(familyPoId));
        if (CollectionUtils.isEmpty(families)) {
            LOG.error("Unable to find the family by poId: " + familyPoId);
            throw new PAException("Unable to fetch family having poId : " + familyPoId);
        }

        Family family = families.get(0);
        return family;
    }


}

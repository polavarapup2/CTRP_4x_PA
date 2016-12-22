package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.PlannedEligibilityCriterion;
import gov.nih.nci.pa.domain.PlannedProcedure;
import gov.nih.nci.pa.domain.PlannedSubstanceAdministration;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.iso.convert.PlannedActivityConverter;
import gov.nih.nci.pa.iso.convert.PlannedEligibilityCriterionConverter;
import gov.nih.nci.pa.iso.convert.PlannedProcedureConverter;
import gov.nih.nci.pa.iso.convert.PlannedSubstanceAdministrationConverter;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.PlannedProcedureDTO;
import gov.nih.nci.pa.iso.dto.PlannedSubstanceAdministrationDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.PlannedActivitySortCriterion;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
public class PlannedActivityBeanLocal extends
        AbstractStudyIsoService<PlannedActivityDTO, PlannedActivity, PlannedActivityConverter> implements
        PlannedActivityServiceLocal {

    private static final Logger LOG = Logger.getLogger(PlannedActivityBeanLocal.class);

    private static final String II_NOTFOUND = "Check the Ii value; found null.";

    @EJB
    private InterventionServiceLocal interventionSrv;

    /**
     * @param dto planned activity to create
     * @return the created planned activity
     * @throws PAException exception.
     */
    @Override
    public PlannedActivityDTO create(PlannedActivityDTO dto) throws PAException {
        businessRules(dto);
        return super.create(dto);
    }

    /**
     * @param dto planned activity to update
     * @return the created planned activity
     * @throws PAException exception.
     */
    @Override
    public PlannedActivityDTO update(PlannedActivityDTO dto) throws PAException {
        businessRules(dto);
        return super.update(dto);
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PlannedActivityDTO> getByArm(Ii ii) throws PAException {
        if (ISOUtil.isIiNull(ii)) {
           return new ArrayList<PlannedActivityDTO>();
        }
        PlannedActivity criteria = new PlannedActivity();
        Arm arm = new Arm();
        arm.setId(IiConverter.convertToLong(ii));
        criteria.getArms().add(arm);

        PageSortParams<PlannedActivity> params = new PageSortParams<PlannedActivity>(
                PAConstants.MAX_SEARCH_RESULTS, 0, Arrays.asList(
                        PlannedActivitySortCriterion.DISPLAY_ORDER,
                        PlannedActivitySortCriterion.PLANNED_ACTIVITY_ID),
                false);
        List<PlannedActivity> results = search(new AnnotatedBeanSearchCriteria<PlannedActivity>(criteria), params);
        return convertFromDomainToDTOs(results);
    }

    /**
     * @param ii study protocol index
     * @return list of PlannedEligibilityCriterion
     * @throws PAException exception
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PlannedEligibilityCriterionDTO> getPlannedEligibilityCriterionByStudyProtocol(Ii ii)
        throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            return new ArrayList<PlannedEligibilityCriterionDTO>();
        }

        Session session = null;
        List<PlannedEligibilityCriterion> queryList = new ArrayList<PlannedEligibilityCriterion>();
        session = PaHibernateUtil.getCurrentSession();
        Query query = null;

        // step 1: form the hql
        String hql = "select pa from PlannedEligibilityCriterion pa join pa.studyProtocol sp "
            + "where sp.id = :studyProtocolId order by pa.displayOrder,pa.id";

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("studyProtocolId", IiConverter.convertToLong(ii));

        // step 3: query the result
        queryList = query.list();
        ArrayList<PlannedEligibilityCriterionDTO> resultList = new ArrayList<PlannedEligibilityCriterionDTO>();
        for (PlannedEligibilityCriterion bo : queryList) {
            resultList.add(new PlannedEligibilityCriterionConverter().convertFromDomainToDto(bo));
        }
        return resultList;
    }

    /**
     * @param ii index
     * @return the PlannedEligibilityCriterion
     * @throws PAException exception.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public PlannedEligibilityCriterionDTO getPlannedEligibilityCriterion(Ii ii) throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            return null;
        }
        PlannedEligibilityCriterionDTO resultDto = null;
        Session session = null;
        session = PaHibernateUtil.getCurrentSession();
        PlannedEligibilityCriterion bo = (PlannedEligibilityCriterion) session.get(PlannedEligibilityCriterion.class,
                                                                                   IiConverter.convertToLong(ii));
        if (bo == null) {
            throw new PAException("Object not found using get() for id = " + IiConverter.convertToString(ii) + ".  ");
        }
        resultDto = new PlannedEligibilityCriterionConverter().convertFromDomainToDto(bo);
        return resultDto;
    }

    /**
     * @param dto PlannedEligibilityCriterion to create
     * @return the created PlannedEligibilityCriterion
     * @throws PAException exception.
     */
    public PlannedEligibilityCriterionDTO createPlannedEligibilityCriterion(PlannedEligibilityCriterionDTO dto)
        throws PAException {
        if (!ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Cannot call createPlannedEligibilityCriterion with a non null identifier");
        }
        if (ISOUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new PAException("Cannot call createPlannedEligibilityCriterion with a null protocol identifier");
        }
        return createOrUpdatePlannedEligibilityCriterion(dto);
    }

    /**
     * @param dto PlannedEligibilityCriterion to update
     * @return the updated PlannedEligibilityCriterion
     * @throws PAException exception.
     */
    public PlannedEligibilityCriterionDTO updatePlannedEligibilityCriterion(PlannedEligibilityCriterionDTO dto)
        throws PAException {
        if (ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Cannot call updatePlannedEligibilityCriterion with a null identifier");
        }
        return createOrUpdatePlannedEligibilityCriterion(dto);
    }

    /**
     * @param ii index
     * @throws PAException exception.
     */
    public void deletePlannedEligibilityCriterion(Ii ii) throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            throw new PAException(II_NOTFOUND);
        }
        Session session = PaHibernateUtil.getCurrentSession();
        PlannedEligibilityCriterion bo = (PlannedEligibilityCriterion) session.get(PlannedEligibilityCriterion.class,
                                                                                   IiConverter.convertToLong(ii));
        session.delete(bo);
    }

    /**
     * copies the study protocol record from source to target.
     * @param fromStudyProtocolIi source
     * @param toStudyProtocolIi target
     * @throws PAException exception.
     */
    public void copyPlannedEligibilityStudyCriterions(Ii fromStudyProtocolIi, Ii toStudyProtocolIi) throws PAException {
        List<PlannedEligibilityCriterionDTO> dtos = getPlannedEligibilityCriterionByStudyProtocol(fromStudyProtocolIi);
        for (PlannedEligibilityCriterionDTO dto : dtos) {
            dto.setIdentifier(null);
            dto.setStudyProtocolIdentifier(toStudyProtocolIi);
            createPlannedEligibilityCriterion(dto);
        }
    }

    /***
     * Planned Substance Methods
     */
    /**
     * {@inheritDoc}
     */
    public PlannedSubstanceAdministrationDTO createPlannedSubstanceAdministration(PlannedSubstanceAdministrationDTO dto)
        throws PAException {
        if (!ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Cannot call createPlannedSubstanceAdministration with a non null identifier.");
        }
        if (ISOUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new PAException("Cannot call createPlannedSubstanceAdministration with a null study protocol "
                    + "identifier");
        }
        validatePlannedSubstance(dto);
        return createOrUpdatePlannedSubstanceAdministration(dto);
    }

    /**
     * {@inheritDoc}
     */
    public PlannedSubstanceAdministrationDTO updatePlannedSubstanceAdministration(PlannedSubstanceAdministrationDTO dto)
        throws PAException {
        if (ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Create method should be used to modify existing.");
        }
        validatePlannedSubstance(dto);
        return createOrUpdatePlannedSubstanceAdministration(dto);
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PlannedSubstanceAdministrationDTO> getPlannedSubstanceAdministrationByStudyProtocol(Ii ii)
        throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            return new ArrayList<PlannedSubstanceAdministrationDTO>();
        }

        Session session = PaHibernateUtil.getCurrentSession();
        List<PlannedSubstanceAdministration> queryList = new ArrayList<PlannedSubstanceAdministration>();
        Query query = null;

        // step 1: form the hql
        String hql = "select pa from PlannedSubstanceAdministration pa join pa.studyProtocol sp "
            + "where sp.id = :studyProtocolId order by pa.id ";

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("studyProtocolId", IiConverter.convertToLong(ii));

        // step 3: query the result
        queryList = query.list();
        List<PlannedSubstanceAdministrationDTO> resultList = new ArrayList<PlannedSubstanceAdministrationDTO>();
        for (PlannedSubstanceAdministration bo : queryList) {
            resultList.add(new PlannedSubstanceAdministrationConverter().convertFromDomainToDto(bo));
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public PlannedSubstanceAdministrationDTO getPlannedSubstanceAdministration(Ii ii) throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            return null;
        }
        PlannedSubstanceAdministrationDTO resultDto = null;
        Session session = PaHibernateUtil.getCurrentSession();
        PlannedSubstanceAdministration bo =
            (PlannedSubstanceAdministration) session.get(PlannedSubstanceAdministration.class,
                                                         IiConverter.convertToLong(ii));
        if (bo == null) {
            throw new PAException("Object not found using get() for id = " + IiConverter.convertToString(ii) + ".  ");
        }
        resultDto = new PlannedSubstanceAdministrationConverter().convertFromDomainToDto(bo);
        return resultDto;
    }

    /**
     * Planned Procedure methods
     */
    /**
     * {@inheritDoc}
     */
    public PlannedProcedureDTO createPlannedProcedure(PlannedProcedureDTO dto) throws PAException {
        if (!ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Update method should be used to modify existing.  ");
        }
        if (ISOUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new PAException("StudyProtocol must be set.  ");
        }
        validatePlannedProcedure(dto);
        return createOrUpdatePlannedProcedure(dto);
    }

    /**
     * {@inheritDoc}
     */
    public PlannedProcedureDTO updatePlannedProcedure(PlannedProcedureDTO dto) throws PAException {
        if (ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Create method should be used to modify existing.  ");
        }
        validatePlannedProcedure(dto);
        return createOrUpdatePlannedProcedure(dto);
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PlannedProcedureDTO> getPlannedProcedureByStudyProtocol(Ii ii) throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            return new ArrayList<PlannedProcedureDTO>();
        }

        Session session = PaHibernateUtil.getCurrentSession();
        List<PlannedProcedure> queryList = new ArrayList<PlannedProcedure>();
        Query query = null;

        // step 1: form the hql
        String hql = "select pa from PlannedProcedure pa join pa.studyProtocol sp where sp.id = :studyProtocolId "
                + "order by pa.id ";

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("studyProtocolId", IiConverter.convertToLong(ii));

        // step 3: query the result
        queryList = query.list();
        List<PlannedProcedureDTO> resultList = new ArrayList<PlannedProcedureDTO>();
        for (PlannedProcedure bo : queryList) {
            resultList.add(PlannedProcedureConverter.convertFromDomainToDTO(bo));
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public PlannedProcedureDTO getPlannedProcedure(Ii ii) throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            return null;
        }
        PlannedProcedureDTO resultDto = null;
        Session session = PaHibernateUtil.getCurrentSession();
        PlannedProcedure bo = (PlannedProcedure) session.get(PlannedProcedure.class, IiConverter.convertToLong(ii));
        if (bo == null) {
            throw new PAException("Object not found using get() for id = " + IiConverter.convertToString(ii) + ".  ");
        }
        resultDto = PlannedProcedureConverter.convertFromDomainToDTO(bo);
        return resultDto;
    }

    private void validatePlannedSubstance(PlannedSubstanceAdministrationDTO dto) throws PAException {
        super.validate(dto);
        businessRules(dto);
        drugBusinessRules(dto);
        checkIfValuesExist(dto);
    }

    private void validatePlannedProcedure(PlannedProcedureDTO dto) throws PAException {
        super.validate(dto);
        businessRules(dto);
        checkIfValuesExist(dto);
    }

    /**
     * Check if values exist.
     *
     * @param dto the dto
     *
     * @throws PAException the PA exception
     */
    private void checkIfValuesExist(PlannedSubstanceAdministrationDTO dto) throws PAException {
        StringBuffer errorBuffer = new StringBuffer();
        if (!ISOUtil.isCdNull(dto.getDoseFormCode())) {
            boolean doseFormExists = PADomainUtils.checkIfValueExists(dto.getDoseFormCode().getCode(), "DOSE_FORM",
                                                                      "CODE");
            if (!doseFormExists) {
                errorBuffer.append("Error while checking for value ").append(dto.getDoseFormCode().getCode())
                           .append(" from table DOSE_FORM\n");
            }
        }
        if (!ISOUtil.isCdNull(dto.getDoseFrequencyCode())) {
            boolean doseFreqExists = PADomainUtils.checkIfValueExists(dto.getDoseFrequencyCode().getCode(),
                                                                      "DOSE_FREQUENCY", "CODE");
            if (!doseFreqExists) {
                errorBuffer.append("Error while checking for value ").append(dto.getDoseFrequencyCode().getCode())
                           .append(" from table DOSE_FREQUENCY\n");
            }
        }
        if (!ISOUtil.isCdNull(dto.getRouteOfAdministrationCode())) {
            boolean roaExists = PADomainUtils.checkIfValueExists(dto.getRouteOfAdministrationCode().getCode(),
                                                                 "ROUTE_OF_ADMINISTRATION", "CODE");
            if (!roaExists) {
                errorBuffer.append("Error while checking for value ")
                           .append(dto.getRouteOfAdministrationCode().getCode())
                           .append(" from table ROUTE_OF_ADMINSTRATION\n");
            }
        }
        if (dto.getDose() != null && dto.getDose().getHigh().getUnit() != null) {
            boolean doseUOMExists = PADomainUtils.checkIfValueExists(dto.getDose().getHigh().getUnit(),
                                                                     "UNIT_OF_MEASUREMENT", "CODE");
            if (!doseUOMExists) {
                errorBuffer.append("Error while checking for value ").append(dto.getDose().getHigh().getUnit())
                           .append(" from table UNIT_OF_MEASUREMENT\n");
            }
        }
        if (dto.getDoseTotal() != null && dto.getDoseTotal().getHigh().getUnit() != null) {
            boolean dosetotalExists = PADomainUtils.checkIfValueExists(dto.getDoseTotal().getHigh().getUnit(),
                                                                       "UNIT_OF_MEASUREMENT", "CODE");
            if (!dosetotalExists) {
                errorBuffer.append("Error while checking for value ").append(dto.getDoseTotal().getHigh().getUnit())
                           .append(" from table UNIT_OF_MEASUREMENT\n");
            }
        }
        if (dto.getDoseDuration() != null && dto.getDoseDuration().getUnit() != null) {
            boolean doseDurExists = PADomainUtils.checkIfValueExists(dto.getDoseDuration().getUnit(),
                                                                     "UNIT_OF_MEASUREMENT", "CODE");
            if (!doseDurExists) {
                errorBuffer.append("Error while checking for value ").append(dto.getDoseDuration().getUnit())
                           .append(" from table UNIT_OF_MEASUREMENT\n");
            }
        }
        if (dto.getSubcategoryCode().getCode().equals(ActivitySubcategoryCode.RADIATION.getCode())) {
            if (!ISOUtil.isCdNull(dto.getApproachSiteCode())) {
                boolean approachSite = PADomainUtils.checkIfValueExists(dto.getApproachSiteCode().getCode(),
                                                                        "TARGET_SITE", "CODE");
                if (!approachSite) {
                    errorBuffer.append("Error while checking for value ").append(dto.getApproachSiteCode().getCode())
                               .append(" from table TARGET_SITE\n");
                }
            }
            if (!ISOUtil.isCdNull(dto.getTargetSiteCode())) {
                boolean targetSite = PADomainUtils.checkIfValueExists(dto.getTargetSiteCode().getCode(), "TARGET_SITE",
                                                                      "CODE");
                if (!targetSite) {
                    errorBuffer.append("Error while checking for value ").append(dto.getTargetSiteCode().getCode())
                               .append(" from table TARGET_SITE\n");
                }
            }
        }
        if (errorBuffer.length() > 0) {
            throw new PAException("Validation Exception " + errorBuffer.toString());
        }
    }

    /**
     * Check if values exist.
     *
     * @param dto the dto
     *
     * @throws PAException the PA exception
     */
    private void checkIfValuesExist(PlannedProcedureDTO dto) throws PAException {
        StringBuffer errorBuffer = new StringBuffer();
        if (!ISOUtil.isCdNull(dto.getMethodCode())) {
            boolean approachSite = PADomainUtils.checkIfValueExists(dto.getMethodCode().getCode(), "METHOD_CODE",
                                                                    "CODE");
            if (!approachSite) {
                errorBuffer.append("Error while checking for value ").append(dto.getMethodCode().getCode())
                           .append(" from table METHOD_CODE\n");
            }
        }
        if (!ISOUtil.isCdNull(dto.getTargetSiteCode())) {
            boolean targetSite = PADomainUtils.checkIfValueExists(dto.getTargetSiteCode().getCode(), "TARGET_SITE",
                                                                  "CODE");
            if (!targetSite) {
                errorBuffer.append("Error while checking for value ").append(dto.getTargetSiteCode().getCode())
                           .append(" from table TARGET_SITE\n");
            }
        }
        if (errorBuffer.length() > 0) {
            throw new PAException("Validation Exception " + errorBuffer.toString());
        }
    }

    private PlannedSubstanceAdministrationDTO createOrUpdatePlannedSubstanceAdministration(
        PlannedSubstanceAdministrationDTO dto) throws PAException {
        
        PlannedSubstanceAdministration bo = null;
        PlannedSubstanceAdministrationDTO resultDto = null;
        Session session = null;
        session = PaHibernateUtil.getCurrentSession();
        Date today = new Date();
        User user = CSMUserService.getInstance().getCSMUser(UsernameHolder.getUser());         
        
        PlannedSubstanceAdministrationConverter converter = new PlannedSubstanceAdministrationConverter();
        bo = converter.convertFromDtoToDomain(dto);
        if (ISOUtil.isIiNull(dto.getIdentifier())) {        
            bo.setUserLastCreated(user);
            bo.setDateLastCreated(today);            
        }
        bo.setDateLastUpdated(today);
        bo.setUserLastUpdated(user);

        session.saveOrUpdate(bo);
        resultDto = converter.convertFromDomainToDto(bo);
        return resultDto;
    }

    private PlannedProcedureDTO createOrUpdatePlannedProcedure(PlannedProcedureDTO dto) throws PAException {
        PlannedProcedure bo = null;
        PlannedProcedureDTO resultDto = null;
        Session session = null;
        session = PaHibernateUtil.getCurrentSession();
        Date today = new Date();
        User user = CSMUserService.getInstance().getCSMUser(UsernameHolder.getUser()); 
        
        if (ISOUtil.isIiNull(dto.getIdentifier())) {
            bo = PlannedProcedureConverter.convertFromDTOToDomain(dto);
            bo.setUserLastCreated(user);
            bo.setDateLastCreated(today);            
        } else {
            bo = (PlannedProcedure) session.load(PlannedProcedure.class,
                                                 IiConverter.convertToLong(dto.getIdentifier()));
            PlannedProcedure delta = PlannedProcedureConverter.convertFromDTOToDomain(dto);
            bo = delta;           
            session.evict(bo);
        }
        bo.setDateLastUpdated(today);
        bo.setUserLastUpdated(user);
        
        session.saveOrUpdate(bo);
        resultDto = PlannedProcedureConverter.convertFromDomainToDTO(bo);
        return resultDto;
    }

    private PlannedEligibilityCriterionDTO createOrUpdatePlannedEligibilityCriterion(PlannedEligibilityCriterionDTO dto)
        throws PAException {
        PlannedEligibilityCriterion bo = null;
        Session session = PaHibernateUtil.getCurrentSession();
        final PlannedEligibilityCriterionConverter converter = new PlannedEligibilityCriterionConverter();
        if (ISOUtil.isIiNull(dto.getIdentifier())) {
            bo = converter.convertFromDtoToDomain(dto);
        } else {
            bo = (PlannedEligibilityCriterion) session.get(PlannedEligibilityCriterion.class,
                                                           IiConverter.convertToLong(dto.getIdentifier()));

            PlannedEligibilityCriterion delta = converter.convertFromDtoToDomain(dto);
            bo.setCriterionName(delta.getCriterionName());
            bo.setInclusionIndicator(delta.getInclusionIndicator());
            bo.setOperator(delta.getOperator());
            bo.setCategoryCode(delta.getCategoryCode());
            bo.setEligibleGenderCode(delta.getEligibleGenderCode());
            bo.setMaxUnit(delta.getMaxUnit());
            bo.setMinUnit(delta.getMinUnit());
            bo.setMaxValue(delta.getMaxValue());
            bo.setMinValue(delta.getMinValue());
            bo.setUnit(delta.getUnit());
            bo.setTextDescription(delta.getTextDescription());
            bo.setUserLastUpdated(delta.getUserLastCreated());
            bo.setDisplayOrder(delta.getDisplayOrder());
            bo.setStructuredIndicator(delta.getStructuredIndicator());
            bo.setTextValue(delta.getTextValue());
        }
        bo.setDateLastUpdated(new Date());
        session.saveOrUpdate(bo);
        return converter.convertFromDomainToDto(bo);
    }

    private void businessRules(PlannedActivityDTO dto) throws PAException {
        if (ISOUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new PAException("PlannedActivity.studyProtocol must be set.");
        }
        if (ISOUtil.isCdNull(dto.getCategoryCode())) {
            throw new PAException("PlannedActivity.categoryCode must be set.");
        }
        if (PAUtil.isTypeIntervention(dto.getCategoryCode())) {
            if (ISOUtil.isCdNull(dto.getSubcategoryCode())) {
                throw new PAException("Intervention type must be set.");
            }
            if (ISOUtil.isIiNull(dto.getInterventionIdentifier())) {
                throw new PAException("An Intervention must be selected.");
            }
            if (getDuplicateIi(dto) != null) {
                throw new PADuplicateException("This trial already includes the selected intervention.");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Ii getDuplicateIi(PlannedActivityDTO dto) throws PAException {
        try {
            InterventionDTO iDto = interventionSrv.get(dto.getInterventionIdentifier());
            String interventionName = iDto.getName().getValue();

            List<PlannedActivityDTO> paList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
            for (PlannedActivityDTO padto : paList) {
                Ii duplicateIi = comparePlannedActivities(dto, interventionName, padto);
                if (duplicateIi != null) {
                    return duplicateIi;
                }
            }
            return null;
        } finally {
            // loading of the objects into the session to check for duplicates
            // causes a non unique hibernate exception to occur when updating.
            PaHibernateUtil.getCurrentSession().flush();
            PaHibernateUtil.getCurrentSession().clear();
        }
    }

    private Ii comparePlannedActivities(PlannedActivityDTO dto, String interventionName, PlannedActivityDTO padto)
            throws PAException {
        boolean duplicate = false;
        if (!ISOUtil.isIiNull(padto.getInterventionIdentifier())) {
            InterventionDTO interDto = interventionSrv.get(padto.getInterventionIdentifier());
            String interName = interDto.getName().getValue();
            if (isDuplicate(dto, interventionName, padto, interName)) {
                duplicate = true;
                if (isSameIdentifier(dto, padto)) {
                    duplicate = false;
                }
            }
        }
        if (duplicate) {
            return padto.getIdentifier();
        }
        return null;
    }

    private boolean isDuplicate(PlannedActivityDTO dto, String interventionName, PlannedActivityDTO padto,
            String interName) {
        return interName.equals(interventionName)
                && padto.getSubcategoryCode().getCode().equals(dto.getSubcategoryCode().getCode())
                && ((padto.getTextDescription().getValue() == null && dto.getTextDescription().getValue() == null)
                        || (padto.getTextDescription().getValue() != null
                        && dto.getTextDescription().getValue() != null && padto.getTextDescription().getValue().equals(
                        dto.getTextDescription().getValue())))
                && ((ISOUtil.isBlNull(dto.getLeadProductIndicator())
                        && ISOUtil.isBlNull(padto.getLeadProductIndicator())
                        ) || (!ISOUtil.isBlNull(dto.getLeadProductIndicator())
                        && !ISOUtil.isBlNull(padto.getLeadProductIndicator()) && padto.getLeadProductIndicator()
                        .getValue().equals(dto.getLeadProductIndicator().getValue())));
    }

    private boolean isSameIdentifier(PlannedActivityDTO dto, PlannedActivityDTO padto) {
        if (!ISOUtil.isIiNull(dto.getIdentifier())) {
            String comp1 = padto.getIdentifier().getExtension();
            String comp2 = dto.getIdentifier().getExtension();
            if (comp1.equals(comp2)) {
                // skip if the id is same, this will happen during update
                return true;
            }
        }
        return false;
    }

    private void drugBusinessRules(PlannedSubstanceAdministrationDTO dto) throws PAException {
        boolean isDrug = ActivitySubcategoryCode.DRUG.getCode()
                                                     .equals(CdConverter.convertCdToString(dto.getSubcategoryCode()));

        if (!isDrug && (dto.getLeadProductIndicator() != null)) {
            LOG.info("Setting lead product indicator to null for non-drug PlannedActivity.");
            dto.setLeadProductIndicator(null);
        }
        if (dto.getLeadProductIndicator() == null || ISOUtil.isBlNull(dto.getLeadProductIndicator())) {
            LOG.info("Generating Bl (false) for non-drug PlannedActivity.");
            dto.setLeadProductIndicator(BlConverter.convertToBl(false));

        }
        // only one lead drug per study
        if (BlConverter.convertToBoolean(dto.getLeadProductIndicator())) {
            Long dtoId = IiConverter.convertToLong(dto.getIdentifier());
            boolean dtoIsNew = (dtoId == null);
            final Ii spii = dto.getStudyProtocolIdentifier();
            List<PlannedSubstanceAdministrationDTO> paList = getPlannedSubstanceAdministrationByStudyProtocol(spii);
            for (PlannedSubstanceAdministrationDTO pa : paList) {
                boolean paIsLead = (null == BlConverter.convertToBoolean(pa.getLeadProductIndicator())) ? false
                    : BlConverter.convertToBoolean(pa.getLeadProductIndicator());
                if ((!ISOUtil.isIiNull(pa.getInterventionIdentifier()))
                    && (dtoIsNew || !dtoId.equals(IiConverter.convertToLong(pa.getIdentifier()))) && paIsLead) {
                    throw new PAException("Only one drug may be marked as lead for a given study.");
                }
            }
        }
    }

    /**
     * @param interventionSrv the interventionSrv to set
     */
    public void setInterventionSrv(InterventionServiceLocal interventionSrv) {
        this.interventionSrv = interventionSrv;
    }

    @Override
    protected String getQueryOrderClause() {
        return " order by alias.displayOrder, alias.id";
    }

    @Override
    public void reorderInterventions(Ii studyProtocolIi, List<String> ids)
            throws PAException {
        List<PlannedActivityDTO> interventions = getByStudyProtocol(studyProtocolIi);
        for (PlannedActivityDTO intervention : interventions) {
            if (PAUtil.isTypeIntervention(intervention.getCategoryCode())) {
                String id = intervention.getIdentifier().getExtension();
                intervention.setDisplayOrder(ids.contains(id) ? IntConverter
                        .convertToInt(ids.indexOf(id)) : IntConverter
                        .convertToInt((Integer) null));
                super.update(intervention);
            }
        }
    }
    
    @Override
    public int getMaxDisplayOrderValue(Ii studyProtocolIi) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        String hql = "select max(pa.displayOrder) from PlannedActivity pa join pa.studyProtocol sp "
            + "where sp.id = :studyProtocolId and pa.categoryCode = :categoryCode";
        Query query = null;
        query = session.createQuery(hql);
        query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
        query.setParameter("categoryCode", ActivityCategoryCode.OTHER);
        List list = query.list();
        if (list.get(0) != null) {
            return ((Integer) list.get(0)).intValue();
        } else {
            return 0;
        }        
    }
    
}

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

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AbstractEntity;
import gov.nih.nci.pa.iso.convert.AbstractConverter;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.dto.BaseDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author Hugh Reinhart
 * @since 09/30/2008
 * @param <DTO> data transfer object
 * @param <BO> domain object
 * @param <CONVERTER> converter class
 */
public abstract class AbstractBaseIsoService<DTO extends BaseDTO, BO extends AbstractEntity,
    CONVERTER extends AbstractConverter<DTO, BO>> extends AbstractBaseSearchBean<BO> implements BasePaService<DTO> {

    private static final String UNCHECKED = "unchecked";
    private final Class<BO> typeArgument;
    private final Class<CONVERTER> converterArgument;
    private static final Logger LOG = Logger.getLogger(AbstractBaseIsoService.class);

    /**
     * The security domain.
     */
    public static final String SECURITY_DOMAIN = "pa";

    /**
     * Admin Abstractor Role name.
     */
    public static final String ADMIN_ABSTRACTOR_ROLE = "AdminAbstractor";

    /**
     * Abstractor Role name.
     */
    public static final String ABSTRACTOR_ROLE = "Abstractor";


    /**
     * Scientific Abstractor Role name.
     */
    public static final String SCIENTIFIC_ABSTRACTOR_ROLE = "ScientificAbstractor";
    
    /**
     * Super Abstractor Role name.
     */
    public static final String SUPER_ABSTRACTOR_ROLE = "SuAbstractor";


    /**
     * Submitter Role name.
     */
    public static final String SUBMITTER_ROLE = "Submitter";

    /**
     * default constructor.
     */
    @SuppressWarnings(UNCHECKED)
    public AbstractBaseIsoService() {
        Class<?> clss = getClass();
        Type type = clss.getGenericSuperclass();
        while (!(type instanceof ParameterizedType)) {
            clss = clss.getSuperclass();
            type = clss.getGenericSuperclass();
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;

        typeArgument = (Class<BO>) parameterizedType.getActualTypeArguments()[1];
        converterArgument = (Class<CONVERTER>) parameterizedType.getActualTypeArguments()[2];
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<DTO> getAll() throws PAException {

        Session session = PaHibernateUtil.getCurrentSession();
        String hql = "select alias from " + getTypeArgument().getName() + " alias "
                + getQueryOrderClause();

        Query query = session.createQuery(hql);
        return convertFromDomainToDTOs(query.list());
    }   

    /**
     * Builds the order by clause forv the HQL query that gets the objects by Study Protocol.
     * @return The order by clause forv the HQL query that gets the objects by Study Protocol.
     */
    protected String getQueryOrderClause() {
        return " order by alias.id";
    }

    /**
     * @param bo domain ojbect
     * @return DTO iso transfer object
     * @throws PAException exception
     */
    protected DTO convertFromDomainToDto(BO bo) throws PAException {
        return Converters.get(getConverterArgument()).convertFromDomainToDto(bo);
    }
    /**
     * @param dto iso transfer object
     * @return DTO iso transfer object
     * @throws PAException exception
     */
    protected BO convertFromDtoToDomain(DTO dto) throws PAException {
        return Converters.get(getConverterArgument()).convertFromDtoToDomain(dto);
    }

    /**
     * @param dto iso transfer object
     * @param bo domain ojbect to update
     * @throws PAException exception
     */
    protected void convertFromDtoToDomain(DTO dto, BO bo) throws PAException {
        Converters.get(getConverterArgument()).convertFromDtoToDomain(dto, bo);
    }

    /**
     * Get class of the implementation.
     *
     * @return the class
     */
    protected Class<BO> getTypeArgument() {
        return typeArgument;
    }

    /**
     * Get class of the implementation.
     *
     * @return the class
     */
    protected Class<CONVERTER> getConverterArgument() {
        return converterArgument;
    }


    /**
     * Converts from a list of domain objects to DTO objects.
     * @param boList boList
     * @return dtoList
     * @throws PAException on error
     */
    protected List<DTO> convertFromDomainToDTOs(List<BO> boList) throws PAException {
        return Converters.get(getConverterArgument()).convertFromDomainToDtos(boList);
    }

    /**
     * Converts from a list of domain objects to DTO objects.
     * @param dtoList dtoList
     * @return boList
     * @throws PAException on error
     */
    protected List<BO> convertFromDTOToDomains(List<DTO> dtoList) throws PAException {
        return Converters.get(getConverterArgument()).convertFromDtoToDomains(dtoList);
    }

    /**
     * @param ii index of object
     * @return null
     * @throws PAException exception
     */
    @Override
    @SuppressWarnings(UNCHECKED)
    public DTO get(Ii ii) throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            throw new PAException("Check the Ii value; null found.");
        }
        DTO resultDto = null;
        Session session = null;
        try {
            session = PaHibernateUtil.getCurrentSession();
            BO bo = (BO) session.get(getTypeArgument(), IiConverter.convertToLong(ii));
            if (bo == null) {
                LOG.warn("Object not found using get() for id = " + IiConverter.convertToString(ii));
                return resultDto;
            }
            resultDto = convertFromDomainToDto(bo);
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in get().", hbe);
        }
        return resultDto;
    }

    /**
     * @param ii index of object
     * @throws PAException exception
     */
    @Override
    @SuppressWarnings(UNCHECKED)
    public void delete(Ii ii) throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            throw new PAException("Check the Ii value; null found.");
        }
        Session session = null;
        try {
            session = PaHibernateUtil.getCurrentSession();
            //session.beginTransaction();
            BO bo = (BO) session.get(getTypeArgument(), IiConverter.convertToLong(ii));
            session.delete(bo);
            session.flush();
        }  catch (HibernateException hbe) {
            throw new PAException("Hibernate exception while deleting ii = "
                + IiConverter.convertToString(ii) + ".", hbe);
        }
    }

    @SuppressWarnings(UNCHECKED)
    private DTO createOrUpdate(DTO dto) throws PAException {
        BO bo = null;
        DTO resultDto = null;
        Session session = null;
        try {
            session = PaHibernateUtil.getCurrentSession();
            Date today = new Date();
            User user = CSMUserService.getInstance().getCSMUserFromCache(
                    UsernameHolder.getUser()); 
            if (ISOUtil.isIiNull(dto.getIdentifier())) {
                bo = convertFromDtoToDomain(dto);
                
                bo.setUserLastCreated(user);
                bo.setDateLastCreated(today);
            } else {
                bo = (BO) session.get(getTypeArgument(), IiConverter.convertToLong(dto.getIdentifier()));
                convertFromDtoToDomain(dto, bo);
            }
            bo.setUserLastUpdated(user);
            bo.setDateLastUpdated(today);
            session.saveOrUpdate(bo);
            resultDto = convertFromDomainToDto(bo);
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in createOrUpdate().", hbe);
        }
        return resultDto;
    }

    /**
     * @param dto arm to create
     * @return the created planned activity
     * @throws PAException exception.
     */
    @Override
    public DTO create(DTO dto) throws PAException {
        if (!ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Update method should be used to modify existing.");
        }
       return createOrUpdate(dto);
    }

    /**
     * @param dto arm to update
     * @return the updated planned activity
     * @throws PAException exception.
     */
    @Override
    public DTO update(DTO dto) throws PAException {
        if (ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Create method should be used to create new.");
        }
        return createOrUpdate(dto);
    }

    /**
     * A common validation method.
     * @param dto Dto object
     * @throws PAException if validation fails
     */
    @Override
    public void validate(DTO dto) throws PAException {
        StringBuffer sb = new StringBuffer();
        sb.append(dto == null ? "DTO cannot be null , " : "");
        BO bo;
        try {
            bo = convertFromDtoToDomain(dto);
            InvalidValue[] invalidValues = null;
            ClassValidator<BO> classValidator = new ClassValidator(bo.getClass());
            invalidValues = classValidator.getInvalidValues(bo);
            for (int i = 0; i < invalidValues.length; i++) {
                sb.append(invalidValues[i].getPropertyName()).append(' ');
                sb.append(invalidValues[i].getMessage().trim()).append('\n');
            }
            if (sb.length() > 0) {
                throw new PAValidationException("Validation Exception " + sb.toString());
            }
        } catch (PAException e) {
            throw new PAValidationException(e);
        }
    }
}

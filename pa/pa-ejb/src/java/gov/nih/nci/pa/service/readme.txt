To create an iso service using base classes:

1.  Determine if the class for which you will be creating a service has a foreign key to StudyProtocol.  If so you will 
    be using StudyDTO, StudyPaService, and AbstractStudyIsoService.  If not you should use BaseDTO, BasePaService, and 
    AbstractBaseIsoService 
2.  Create the domain class w/hiberante annotations by extending gov.nih.nci.pa.domain.AbstractEntity.  Use the @Entity 
    annotation.
3.  Create a corresponding iso dto by extending the appropriate base class (either gov.nih.nci.pa.iso.dto.BaseDTO or 
    StudyDTO).
4.  Create a converter class by extending gov.nih.nci.pa.iso.convert.AbstractConverter<DTO, BO>.
5.  Add the new converter class to gov.nih.nci.pa.iso.convert.Converters.
6.  Create a service interface by extending the appropriate base interface (either 
    gov.nih.nci.pa.service.BasePaService<DTO> or StudyPaService<DTO>) and using the @Remote annotation.  Adding custom 
    methods as needed.
7.  Create the service by extending either gov.nih.nci.pa.service.AbstractBaseIsoService<DTO, BO, CONVERTER> or 
    AbstractStudyIsoService<DTO, BO, CONVERTER> as appropriate.  Class should implement the interface created in the prior 
    step and include the @Stateless annotation.
8.  Customize the service by implementing and overriding methods as needed to implement additional methods and add custom 
    constraints to existing methods.
package gov.nih.nci.accrual.accweb.decorator;

import static org.junit.Assert.assertTrue;
import gov.nih.nci.accrual.dto.PatientListDto;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

public class SearchPatientDecoratorTest extends AbstractDecoratorTest<SearchPatientDecorator, PatientListDto> {

    @Override
    public void initBean() {
        bean = new SearchPatientDecorator();
   }

    @Override
    public PatientListDto initDataRow() {
        PatientListDto result = new PatientListDto();
        result.setAssignedIdentifier("assignedIdentifier");
        result.setDateLastUpdated(new Timestamp(new Date().getTime()));
        result.setIdentifier("123");
        result.setOrganizationName("organizationName");
        result.setRegistrationDate(new Timestamp(new Date().getTime()));
        return result;
    }

    @Override
    public PatientListDto initNullRow() {
        return new PatientListDto();
    }

    @Test
    public void getRegistrationDate() {
        setDataRow();
        assertTrue(0 != bean.getRegistrationDate().length());

        setNullRow();
        assertTrue(0 == bean.getRegistrationDate().length());
    }

    @Test
    public void getDateLastUpdated() {
        setDataRow();
        assertTrue(0 != bean.getDateLastUpdated().length());

        setNullRow();
        assertTrue(0 == bean.getDateLastUpdated().length());
    }
}

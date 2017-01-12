package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.util.Constants;

import java.util.Arrays;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class TrialInterventionTest extends AbstractPaActionTest {

    TrialInterventionsAction  trialIntervention;
    @Before
    public void prepare() throws Exception {
        trialIntervention = new TrialInterventionsAction();
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
        trialIntervention.prepare();
     }
    @Test
    public void testAddWithErrors() throws PAException{
        assertEquals("edit", trialIntervention.add());
    }
    @Test
    public void testAddSubstance() throws PAException{
        trialIntervention.setInterventionType(ActivitySubcategoryCode.DRUG.getCode());
        trialIntervention.setInterventionIdentifier("1");
        trialIntervention.setMinDoseValue("2");
        trialIntervention.setMaxDoseValue("4");
        trialIntervention.setMinDoseTotalValue("8");
        trialIntervention.setMaxDoseTotalValue("10");
        trialIntervention.setDoseUOM("ml");
        trialIntervention.setDoseTotalUOM("ml");
        assertEquals("list", trialIntervention.add());
    }
    @Test
    public void testAddSubstanceErr() throws PAException{
        trialIntervention.setInterventionType(ActivitySubcategoryCode.DRUG.getCode());
        trialIntervention.setInterventionIdentifier("1");
        trialIntervention.setMinDoseValue("a");
        trialIntervention.setMaxDoseValue("b");
        trialIntervention.setMinDoseTotalValue("c");
        trialIntervention.setMaxDoseTotalValue("d");
        trialIntervention.setDoseUOM("ml");
        trialIntervention.setDoseTotalUOM("ml");
        assertEquals(trialIntervention.add(),"edit");
        assertTrue(CollectionUtils.isNotEmpty(trialIntervention.getActionErrors()));

        trialIntervention.setMinDoseValue(null);
        trialIntervention.setMaxDoseValue("b");
        trialIntervention.setMinDoseTotalValue(null);
        trialIntervention.setMaxDoseTotalValue("d");
        trialIntervention.setDoseUOM("ml");
        trialIntervention.setDoseTotalUOM(null);
        assertEquals(trialIntervention.add(),"edit");
        assertTrue(CollectionUtils.isNotEmpty(trialIntervention.getActionErrors()));

        trialIntervention.setMinDoseValue("1");
        trialIntervention.setMaxDoseValue(null);
        trialIntervention.setMinDoseTotalValue("2");
        trialIntervention.setMaxDoseTotalValue(null);
        trialIntervention.setDoseUOM(null);
        trialIntervention.setDoseTotalUOM("ml");
        assertEquals(trialIntervention.add(),"edit");
        assertTrue(CollectionUtils.isNotEmpty(trialIntervention.getActionErrors()));

        trialIntervention.setMinDoseValue("1");
        trialIntervention.setMaxDoseValue("2");
        trialIntervention.setMinDoseTotalValue(null);
        trialIntervention.setMaxDoseTotalValue(null);
        trialIntervention.setDoseUOM("ml");
        trialIntervention.setDoseTotalUOM("ml");
        trialIntervention.setDoseDurationUOM(null);
        trialIntervention.setDoseDurationValue("1");

        assertEquals(trialIntervention.add(),"edit");
        assertTrue(CollectionUtils.isNotEmpty(trialIntervention.getActionErrors()));

        trialIntervention.setMinDoseValue("1");
        trialIntervention.setMaxDoseValue("2");
        trialIntervention.setMinDoseTotalValue("1");
        trialIntervention.setMaxDoseTotalValue(null);
        trialIntervention.setDoseUOM("ml");
        trialIntervention.setDoseTotalUOM(null);
        trialIntervention.setDoseDurationUOM("1");
        trialIntervention.setDoseDurationValue(null);
        assertEquals(trialIntervention.add(),"edit");
        assertTrue(CollectionUtils.isNotEmpty(trialIntervention.getActionErrors()));

    }

    @Test
    public void testAddProcedure() throws PAException{
        trialIntervention.setInterventionType("Procedure/Surgery");
        trialIntervention.setInterventionIdentifier("1");
        trialIntervention.setInterventionDescription("test");
        trialIntervention.setTargetSite("chest");
        trialIntervention.setProcedureName("test");
        assertEquals("list", trialIntervention.add());
    }
    @Test
    public void testAdd() throws PAException{
        trialIntervention.setInterventionType("Device");
        trialIntervention.setInterventionIdentifier("1");
        trialIntervention.setInterventionDescription("test");
        assertEquals("list", trialIntervention.add());
    }
    @Test
    public void testUpdateWithErrors() throws PAException {
        assertEquals("edit", trialIntervention.update());
    }
    @Test
    public void testUpdateSubstance() throws PAException{
        trialIntervention.setSelectedRowIdentifier("1");
        trialIntervention.setInterventionType("Drug");
        trialIntervention.setInterventionIdentifier("1");
        trialIntervention.setMinDoseValue("2");
        trialIntervention.setMaxDoseValue("4");
        trialIntervention.setMinDoseTotalValue("8");
        trialIntervention.setMaxDoseTotalValue("10");
        trialIntervention.setDoseUOM("ml");
        trialIntervention.setDoseTotalUOM("ml");
        assertEquals("list", trialIntervention.update());
    }
    @Test
    public void testUpdateProcedure() throws PAException{
        trialIntervention.setSelectedRowIdentifier("1");
        trialIntervention.setInterventionType("Procedure/Surgery");
        trialIntervention.setInterventionIdentifier("1");
        trialIntervention.setInterventionDescription("test");
        trialIntervention.setTargetSite("chest");
        trialIntervention.setProcedureName("test");
        assertEquals("list", trialIntervention.update());
    }
    @Test
    public void testUpdate() throws PAException{
        trialIntervention.setSelectedRowIdentifier("1");
        trialIntervention.setInterventionType("Device");
        trialIntervention.setInterventionIdentifier("1");
        trialIntervention.setInterventionDescription("test");
        assertEquals("list", trialIntervention.update());
    }

    @Test
    public void testDelete() throws PAException{
        trialIntervention.setObjectsToDelete(new String[] {"1"});
        assertEquals(trialIntervention.delete(),"list");
        trialIntervention.setObjectsToDelete(new String[] {"6"});
        assertEquals(trialIntervention.delete(),"list");
        assertTrue(ServletActionContext.getRequest().getAttribute(
                Constants.FAILURE_MESSAGE)!=null);
    }
    @Test
    public void testDisplay() throws PAException{
        getRequest().setupAddParameter("interventionId", "1");
        trialIntervention.setInterventionIdentifier("1");
        assertEquals(trialIntervention.display(),"edit");
        getRequest().setupAddParameter("interventionId", "");
        trialIntervention.setInterventionIdentifier("1");
        assertEquals(trialIntervention.display(),"edit");
    }
    @Test(expected=Exception.class)
    public void testDisplaySelectedTypeDoseForm() throws PAException{
        getRequest().setupAddParameter("id", "1");
        getRequest().setupAddParameter("className", "DoseForm");
        getRequest().setupAddParameter("divName", "DoseForm");
        trialIntervention.displaySelectedType();
        getRequest().setupAddParameter("id", "");
        getRequest().setupAddParameter("className", "DoseForm");
        getRequest().setupAddParameter("divName", "DoseForm");
        assertEquals("divName", trialIntervention.displaySelectedType());
    }
    @Test(expected=Exception.class)
    public void testDisplaySelectedTypeDoseFrequency() throws PAException{
        getRequest().setupAddParameter("id", "1");
        getRequest().setupAddParameter("className", "DoseFrequency");
        getRequest().setupAddParameter("divName", "DoseFrequency");
        trialIntervention.displaySelectedType();
    }
    @Test(expected=Exception.class)
    public void testDisplaySelectedTypeRouteOfAdministration() throws PAException{
        getRequest().setupAddParameter("id", "1");
        getRequest().setupAddParameter("className", "RouteOfAdministration");
        getRequest().setupAddParameter("divName", "RouteOfAdministration");
        trialIntervention.displaySelectedType();
    }
    @Test(expected=Exception.class)
    public void testDisplaySelectedTypeMethodCode() throws PAException{
        getRequest().setupAddParameter("id", "1");
        getRequest().setupAddParameter("className", "MethodCode");
        getRequest().setupAddParameter("divName", "MethodCode");
        trialIntervention.displaySelectedType();
    }
    @Test(expected=Exception.class)
    public void testDisplaySelectedTypeDoseUnitOfMeasurement() throws PAException{
        getRequest().setupAddParameter("id", "1");
        getRequest().setupAddParameter("className", "UnitOfMeasurement");
        getRequest().setupAddParameter("divName", "loadDoseUOM");
        trialIntervention.displaySelectedType();
    }
    @Test(expected=Exception.class)
    public void testDisplaySelectedTypeDoseDurationUnitOfMeasurement() throws PAException{
        getRequest().setupAddParameter("id", "1");
        getRequest().setupAddParameter("className", "UnitOfMeasurement");
        getRequest().setupAddParameter("divName", "loadDoseDurationUOM");
        trialIntervention.displaySelectedType();
    }
    @Test(expected=Exception.class)
    public void testDisplaySelectedTypeTotalDoseUnitOfMeasurement() throws PAException{
        getRequest().setupAddParameter("id", "1");
        getRequest().setupAddParameter("className", "UnitOfMeasurement");
        getRequest().setupAddParameter("divName", "loadTotalDoseUOM");
        trialIntervention.displaySelectedType();
    }
    @Test(expected=Exception.class)
    public void testDisplaySelectedTypeTargetSite() throws PAException{
        getRequest().setupAddParameter("id", "1");
        getRequest().setupAddParameter("className", "TargetSite");
        getRequest().setupAddParameter("divName", "TargetSite");
        trialIntervention.displaySelectedType();
    }
    @Test(expected=Exception.class)
    public void testDisplaySelectedTypeApproachSite() throws PAException{
        getRequest().setupAddParameter("id", "1");
        getRequest().setupAddParameter("className", "TargetSite");
        getRequest().setupAddParameter("divName", "ApproachSite");
        trialIntervention.displaySelectedType();
    }
    @Test
    public void testDisplaySubPage() throws PAException{
        getRequest().setupAddParameter("interventionId", "1");
        getRequest().setupAddParameter("interventionName", "Aspirin");
        getRequest().setupAddParameter("interventionOtherNames", "baby aspirin");
        getRequest().setupAddParameter("interventionType", "Drug");
        assertEquals("edit",trialIntervention.displaySubPage());

        getRequest().setupAddParameter("interventionId", "");
        getRequest().setupAddParameter("interventionName", "Aspirin");
        getRequest().setupAddParameter("interventionOtherNames", "baby aspirin");
        getRequest().setupAddParameter("interventionType", "Drug");
        assertEquals("edit",trialIntervention.displaySubPage());

    }
    @Test
    public void testGenerateErr() throws PAException{
        trialIntervention.setInterventionType(ActivitySubcategoryCode.DRUG.getCode());
        trialIntervention.setInterventionIdentifier("1");
        trialIntervention.setMinDoseValue(null);
        trialIntervention.setMaxDoseValue("4");
        trialIntervention.setMinDoseTotalValue("8");
        trialIntervention.setMaxDoseTotalValue("10");
        trialIntervention.setDoseUOM("ml");
        trialIntervention.setDoseTotalUOM("ml");
        assertEquals(trialIntervention.generate(),"edit");
        assertNotNull(CollectionUtils.isNotEmpty(trialIntervention.getActionErrors()));
    }
    @Test
    public void testGenerateForDevice() throws PAException{
        trialIntervention.setInterventionType(ActivitySubcategoryCode.DEVICE.getCode());
        trialIntervention.setInterventionIdentifier("1");
        trialIntervention.setMinDoseValue("1");
        trialIntervention.setMaxDoseValue("4");
        trialIntervention.setMinDoseTotalValue("8");
        trialIntervention.setMaxDoseTotalValue("10");
        trialIntervention.setDoseUOM("ml");
        trialIntervention.setDoseTotalUOM("ml");
        assertEquals(trialIntervention.generate(),"edit");
    }
    @Test
    public void testGenerateSubstance() throws PAException{
        trialIntervention.setInterventionType(ActivitySubcategoryCode.DRUG.getCode());
        trialIntervention.setInterventionIdentifier("1");
        trialIntervention.setMinDoseValue("2");
        trialIntervention.setMaxDoseValue("4");
        trialIntervention.setMinDoseTotalValue("8");
        trialIntervention.setMaxDoseTotalValue("10");
        trialIntervention.setDoseUOM("ml");
        trialIntervention.setDoseTotalUOM("ml");
        trialIntervention.setDoseDurationValue("1");
        trialIntervention.setDoseDurationUOM("ml");
        trialIntervention.setDoseForm("doseForm");
        trialIntervention.setRouteOfAdministration("routeOfAdministration");
        trialIntervention.setDoseDurationValue("3");
        trialIntervention.setDoseFrequency("hr");
        trialIntervention.setDoseRegimen("doseRegimen");
        trialIntervention.setApproachSite("approachSite");
        trialIntervention.setTargetSite("targetSite");
        trialIntervention.setProcedureName("procedureName");
        assertEquals(trialIntervention.generate(),"edit");
        assertNotNull(trialIntervention.getInterventionDescription());
    }
    
    @Test
    public void testGenerateSubstanceValue() throws PAException{
        trialIntervention.setInterventionType(ActivitySubcategoryCode.DRUG.getCode());
        trialIntervention.setInterventionIdentifier("1");
        trialIntervention.setMinDoseValue(null);
        trialIntervention.setMaxDoseValue(null);
        trialIntervention.setMinDoseTotalValue(null);
        trialIntervention.setMaxDoseTotalValue(null);
        trialIntervention.setDoseUOM(null);
        trialIntervention.setDoseTotalUOM(null);
        trialIntervention.setDoseDurationValue(null);
        trialIntervention.setDoseDurationUOM("ml");
        trialIntervention.setDoseForm(null);
        trialIntervention.setRouteOfAdministration("routeOfAdministration");
        trialIntervention.setDoseDurationValue("3");
        trialIntervention.setDoseFrequency(null);
        trialIntervention.setDoseRegimen(null);
        trialIntervention.setApproachSite("approachSite");
        trialIntervention.setTargetSite("targetSite");
        trialIntervention.setProcedureName("procedureName");
        assertEquals(trialIntervention.generate(),"edit");
        assertNotNull(trialIntervention.getInterventionDescription());
    }
    @Test
    public void testGenerateRadiation() throws PAException{
        trialIntervention.setInterventionType(ActivitySubcategoryCode.RADIATION.getCode());
        trialIntervention.setInterventionIdentifier("1");
        trialIntervention.setMinDoseValue("2");
        trialIntervention.setMaxDoseValue("4");
        trialIntervention.setMinDoseTotalValue("8");
        trialIntervention.setMaxDoseTotalValue("10");
        trialIntervention.setDoseUOM("ml");
        trialIntervention.setDoseTotalUOM("ml");
        trialIntervention.setDoseDurationValue("1");
        trialIntervention.setDoseDurationUOM("ml");
        trialIntervention.setDoseForm("doseForm");
        trialIntervention.setRouteOfAdministration("routeOfAdministration");
        trialIntervention.setDoseDurationValue("3");
        trialIntervention.setDoseFrequency("hr");
        trialIntervention.setDoseRegimen("doseRegimen");
        trialIntervention.setApproachSite("approachSite");
        trialIntervention.setTargetSite("targetSite");
        trialIntervention.setProcedureName("procedureName");
        assertEquals(trialIntervention.generate(),"edit");
        assertNotNull(trialIntervention.getInterventionDescription());
    }
    @Test
    public void testGenerateProcedure() throws PAException{
        trialIntervention.setInterventionType(ActivitySubcategoryCode.PROCEDURE_SURGERY.getCode());
        trialIntervention.setInterventionIdentifier("1");
        trialIntervention.setMinDoseValue("2");
        trialIntervention.setMaxDoseValue("4");
        trialIntervention.setMinDoseTotalValue("8");
        trialIntervention.setMaxDoseTotalValue("10");
        trialIntervention.setDoseUOM("ml");
        trialIntervention.setDoseTotalUOM("ml");
        trialIntervention.setProcedureName("procedureName");
        assertEquals(trialIntervention.generate(),"edit");
        assertNotNull(trialIntervention.getInterventionDescription());
    }
    @Test
    public void testGetterSetter() {
        assertNull(trialIntervention.getInterventionDescription());
        trialIntervention.setInterventionDescription("interventionDescription");
        assertNotNull(trialIntervention.getInterventionDescription());

        assertNull(trialIntervention.getDoseDurationValue());
        trialIntervention.setDoseDurationValue("doseDurationValue");
        assertNotNull(trialIntervention.getDoseDurationValue());

        assertNull(trialIntervention.getDoseDurationUOM());
        trialIntervention.setDoseDurationUOM("doseDurationUOM");
        assertNotNull(trialIntervention.getDoseDurationUOM());

        assertNull(trialIntervention.getDoseRegimen());
        trialIntervention.setDoseRegimen("doseRegimen");
        assertNotNull(trialIntervention.getDoseRegimen());

        assertNull(trialIntervention.getRouteOfAdministration());
        trialIntervention.setRouteOfAdministration("routeOfAdministration");
        assertNotNull(trialIntervention.getRouteOfAdministration());

        assertNull(trialIntervention.getDoseForm());
        trialIntervention.setDoseForm("doseForm");
        assertNotNull(trialIntervention.getDoseForm());

        assertNull(trialIntervention.getDoseFrequency());
        trialIntervention.setDoseFrequency("doseFrequency");
        assertNotNull(trialIntervention.getDoseFrequency());

        assertNull(trialIntervention.getSelectedType());
        trialIntervention.setSelectedType("selectedType");
        assertNotNull(trialIntervention.getSelectedType());

        assertNull(trialIntervention.getApproachSite());
        trialIntervention.setApproachSite("approachSite");
        assertNotNull(trialIntervention.getApproachSite());

        assertNull(trialIntervention.getDoseFrequencyCode());
        trialIntervention.setDoseFrequencyCode("doseFrequencyCode");
        assertNotNull(trialIntervention.getDoseFrequencyCode());

        assertNull(trialIntervention.getInterventionName());
        trialIntervention.setInterventionName("interventionName");
        assertNotNull(trialIntervention.getInterventionName());

        assertNull(trialIntervention.getInterventionOtherNames());
        trialIntervention.setInterventionOtherNames("interventionOtherNames");
        assertNotNull(trialIntervention.getInterventionOtherNames());
    }
    @Test
    public void testEditDrug() throws PAException {
        assertEquals("edit",trialIntervention.edit());
        trialIntervention.setSelectedRowIdentifier("1");
        trialIntervention.setSelectedType(ActivitySubcategoryCode.DRUG.getCode());
        assertEquals("edit",trialIntervention.edit());
    }
    @Test
    public void testEditRadiation() throws PAException {
        assertEquals("edit",trialIntervention.edit());
        trialIntervention.setSelectedRowIdentifier("1");
        trialIntervention.setSelectedType(ActivitySubcategoryCode.RADIATION.getCode());
        assertEquals("edit",trialIntervention.edit());
    }
    @Test
    public void testEditProcedureSurgery() throws PAException {
        trialIntervention.setSelectedRowIdentifier("1");
        trialIntervention.setSelectedType(ActivitySubcategoryCode.PROCEDURE_SURGERY.getCode());
        assertEquals("edit",trialIntervention.edit());
    }
    @Test
    public void testEditOther() throws PAException {
        // first create the new intervention to edit
        testAdd();
        // now edit it
        trialIntervention.setSelectedRowIdentifier("3");
        trialIntervention.setSelectedType(ActivitySubcategoryCode.DEVICE.getCode());
        assertEquals("edit",trialIntervention.edit());
    }
    @Test
    public void testCreate() throws PAException {
        assertEquals("edit",trialIntervention.create());
    }
    
    @Test
    public void testInterventionsReorder() throws PAException {
        PlannedActivityServiceLocal mock = mock(PlannedActivityServiceLocal.class);
        TrialInterventionsAction action = new TrialInterventionsAction();
        action.setPlannedActivityService(mock);
        action.setOrderString("3;2;1");
        action.setSpIi(IiConverter.convertToIi(1L));
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II,
                IiConverter.convertToIi(1L));
        action.order();
        verify(mock).reorderInterventions(IiConverter.convertToIi(1L),
                Arrays.asList(new String[] { "3", "2", "1" }));

    }
}

package com.ctepdataload;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import gov.nih.nci.pa.webservices.types.BaseParticipatingSite;
import gov.nih.nci.pa.webservices.types.Organization;
import gov.nih.nci.pa.webservices.types.OrganizationOrPersonID;
import gov.nih.nci.pa.webservices.types.ParticipatingSite;
import gov.nih.nci.pa.webservices.types.ParticipatingSiteUpdate;
import gov.nih.nci.pa.webservices.types.Person;
import gov.nih.nci.pa.webservices.types.RecruitmentStatus;
import gov.nih.nci.pa.webservices.types.BaseParticipatingSite.Investigator;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.ContactType;

public class XMLGenerator {

    
    public String generateNewSiteXML (String localtrialIdentifier, 
            String siteCtepId,String recruitementStatus,
            String recruitmentStatusDate,String piCtepId, 
            boolean skipContactInformation
            ) throws Exception {
        String result = null;
        
        //set local trial identifier
        ParticipatingSite participatingSite = new ParticipatingSite();
        
        if (localtrialIdentifier!=null) {
            participatingSite.setLocalTrialIdentifier(localtrialIdentifier);
        }
        else {
            participatingSite.setLocalTrialIdentifier("");    
        }
        
        //set existing org with ctepId
        Organization organization = new Organization();
        OrganizationOrPersonID organizationOrPersonID = createOrgOrPerson(siteCtepId);
        organization.setExistingOrganization(organizationOrPersonID);
        participatingSite.setOrganization(organization);
        
        populateBaseElement(recruitementStatus,
                recruitmentStatusDate, piCtepId, participatingSite,skipContactInformation);
        
        //generate xml 
        JAXBContext jaxbContext = JAXBContext.newInstance(ParticipatingSite.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal( participatingSite, stringWriter );
        result = stringWriter.toString();
        
        return result;
    }
    
    public String generateUpdateSiteXML (String localStudyIdentifier, String recruitementStatus,
            String recruitmentStatusDate,String piCtepId , boolean skipContactInformation
            ) throws Exception {
        String result = null;
        
        ParticipatingSiteUpdate participatingSiteUpdate = new ParticipatingSiteUpdate();
        if (localStudyIdentifier!=null) {
            participatingSiteUpdate.setLocalTrialIdentifier(localStudyIdentifier);
        }
        else {
            participatingSiteUpdate.setLocalTrialIdentifier("");    
        }
        
        //populate element
        populateBaseElement(recruitementStatus,recruitmentStatusDate,
                piCtepId,participatingSiteUpdate,skipContactInformation);
        
        //generate xml 
        JAXBContext jaxbContext = JAXBContext.newInstance(ParticipatingSiteUpdate.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal( participatingSiteUpdate, stringWriter );
        result = stringWriter.toString();
        
        return result;
    }
    private void populateBaseElement (String recruitementStatus,
            String recruitmentStatusDate,String piCtepId ,
            BaseParticipatingSite baseParticipatingSite, boolean skipContactInformation ) throws Exception {
        
        //set site recruitementStatus
        baseParticipatingSite.setRecruitmentStatus(RecruitmentStatus.valueOf(recruitementStatus));
        
        //set recruitmentStatus Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = dateFormat.parse(recruitmentStatusDate);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(gregorianCalendar);
        baseParticipatingSite.setRecruitmentStatusDate(xmlGregorianCalendar);
        
       
        //set pi ctepId
        Investigator investigator = new Investigator();
        Person person = new Person();
        OrganizationOrPersonID organizationOrPersonID2 = createOrgOrPerson(piCtepId);
        person.setExistingPerson(organizationOrPersonID2);
        investigator.setPerson(person);
       /* if (skipContactInformation) {
            investigator.setPrimaryContact(false);    
        }
        else {
            investigator.setPrimaryContact(true);  
        }*/
        investigator.setPrimaryContact(true);  
         investigator.setRole("Principal Investigator");
        
        baseParticipatingSite.getInvestigator().add(investigator);
        
        
       
        
    }
    
   /* public String generateUpdateXML () {
        
    }*/
    
    
    
   private OrganizationOrPersonID createOrgOrPerson(String ctepId) {
       OrganizationOrPersonID organizationOrPersonID = new OrganizationOrPersonID();
       organizationOrPersonID.setCtepID(ctepId);
       return organizationOrPersonID;
   }
}

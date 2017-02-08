//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.07 at 03:16:27 PM EST 
//


package gov.nih.nci.pa.service.ctgov;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for clinical_results_struct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="clinical_results_struct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="participant_flow" type="{}participant_flow_struct"/>
 *         &lt;element name="baseline" type="{}baseline_struct"/>
 *         &lt;element name="outcome_list">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="outcome" type="{}results_outcome_struct" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="reported_events" type="{}reported_events_struct" minOccurs="0"/>
 *         &lt;element name="certain_agreements" type="{}certain_agreements_struct" minOccurs="0"/>
 *         &lt;element name="limitations_and_caveats" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="point_of_contact" type="{}point_of_contact_struct" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clinical_results_struct", propOrder = {
    "participantFlow",
    "baseline",
    "outcomeList",
    "reportedEvents",
    "certainAgreements",
    "limitationsAndCaveats",
    "pointOfContact"
})
public class ClinicalResultsStruct {

    @XmlElement(name = "participant_flow", required = true)
    protected ParticipantFlowStruct participantFlow;
    @XmlElement(required = true)
    protected BaselineStruct baseline;
    @XmlElement(name = "outcome_list", required = true)
    protected ClinicalResultsStruct.OutcomeList outcomeList;
    @XmlElement(name = "reported_events")
    protected ReportedEventsStruct reportedEvents;
    @XmlElement(name = "certain_agreements")
    protected CertainAgreementsStruct certainAgreements;
    @XmlElement(name = "limitations_and_caveats")
    protected String limitationsAndCaveats;
    @XmlElement(name = "point_of_contact")
    protected PointOfContactStruct pointOfContact;

    /**
     * Gets the value of the participantFlow property.
     * 
     * @return
     *     possible object is
     *     {@link ParticipantFlowStruct }
     *     
     */
    public ParticipantFlowStruct getParticipantFlow() {
        return participantFlow;
    }

    /**
     * Sets the value of the participantFlow property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParticipantFlowStruct }
     *     
     */
    public void setParticipantFlow(ParticipantFlowStruct value) {
        this.participantFlow = value;
    }

    /**
     * Gets the value of the baseline property.
     * 
     * @return
     *     possible object is
     *     {@link BaselineStruct }
     *     
     */
    public BaselineStruct getBaseline() {
        return baseline;
    }

    /**
     * Sets the value of the baseline property.
     * 
     * @param value
     *     allowed object is
     *     {@link BaselineStruct }
     *     
     */
    public void setBaseline(BaselineStruct value) {
        this.baseline = value;
    }

    /**
     * Gets the value of the outcomeList property.
     * 
     * @return
     *     possible object is
     *     {@link ClinicalResultsStruct.OutcomeList }
     *     
     */
    public ClinicalResultsStruct.OutcomeList getOutcomeList() {
        return outcomeList;
    }

    /**
     * Sets the value of the outcomeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClinicalResultsStruct.OutcomeList }
     *     
     */
    public void setOutcomeList(ClinicalResultsStruct.OutcomeList value) {
        this.outcomeList = value;
    }

    /**
     * Gets the value of the reportedEvents property.
     * 
     * @return
     *     possible object is
     *     {@link ReportedEventsStruct }
     *     
     */
    public ReportedEventsStruct getReportedEvents() {
        return reportedEvents;
    }

    /**
     * Sets the value of the reportedEvents property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportedEventsStruct }
     *     
     */
    public void setReportedEvents(ReportedEventsStruct value) {
        this.reportedEvents = value;
    }

    /**
     * Gets the value of the certainAgreements property.
     * 
     * @return
     *     possible object is
     *     {@link CertainAgreementsStruct }
     *     
     */
    public CertainAgreementsStruct getCertainAgreements() {
        return certainAgreements;
    }

    /**
     * Sets the value of the certainAgreements property.
     * 
     * @param value
     *     allowed object is
     *     {@link CertainAgreementsStruct }
     *     
     */
    public void setCertainAgreements(CertainAgreementsStruct value) {
        this.certainAgreements = value;
    }

    /**
     * Gets the value of the limitationsAndCaveats property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimitationsAndCaveats() {
        return limitationsAndCaveats;
    }

    /**
     * Sets the value of the limitationsAndCaveats property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimitationsAndCaveats(String value) {
        this.limitationsAndCaveats = value;
    }

    /**
     * Gets the value of the pointOfContact property.
     * 
     * @return
     *     possible object is
     *     {@link PointOfContactStruct }
     *     
     */
    public PointOfContactStruct getPointOfContact() {
        return pointOfContact;
    }

    /**
     * Sets the value of the pointOfContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link PointOfContactStruct }
     *     
     */
    public void setPointOfContact(PointOfContactStruct value) {
        this.pointOfContact = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="outcome" type="{}results_outcome_struct" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "outcome"
    })
    public static class OutcomeList {

        @XmlElement(required = true)
        protected List<ResultsOutcomeStruct> outcome;

        /**
         * Gets the value of the outcome property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the outcome property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOutcome().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ResultsOutcomeStruct }
         * 
         * 
         */
        public List<ResultsOutcomeStruct> getOutcome() {
            if (outcome == null) {
                outcome = new ArrayList<ResultsOutcomeStruct>();
            }
            return this.outcome;
        }

    }

}

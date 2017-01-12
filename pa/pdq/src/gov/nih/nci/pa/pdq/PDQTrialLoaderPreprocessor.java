/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package gov.nih.nci.pa.pdq;

import gov.nih.nci.pa.util.PAConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.FilenameFilter;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author ludetc
 *
 */
public class PDQTrialLoaderPreprocessor {

    private static String srcFileDir;
    private static String destFileDir;

    private static String DEFAULT_ARM = "Arm I";

    private static String DEFAULT_FUTURE_DATE = "2100-01-01";
    private static String DEFAULT_PAST_DATE = "1900-01-01";

    private static List<String> COOP_GROUP_LIST = new ArrayList<String>();
    static {
        COOP_GROUP_LIST.add("american college of surgeons oncology trials group");
        COOP_GROUP_LIST.add("american college of surgeons oncology group");
        COOP_GROUP_LIST.add("cancer and leukemia group b");
        COOP_GROUP_LIST.add("eastern cooperative oncology group");
        COOP_GROUP_LIST.add("gynecologic oncology group");
        COOP_GROUP_LIST.add("national cancer institute of canada clinical trials group");
        COOP_GROUP_LIST.add("north central cancer treatment group");
        COOP_GROUP_LIST.add("national surgical adjuvant breast and bowel project");
        COOP_GROUP_LIST.add("radiation therapy oncology group");
        COOP_GROUP_LIST.add("southwest oncology group");
        COOP_GROUP_LIST.add("children's oncology group");
        COOP_GROUP_LIST.add("american college of radiology imaging network");
        COOP_GROUP_LIST.add("european organisation for research and treatment of cancer");
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: PDQTrialLoaderPreprocessor <sourceFileDirectory> <destFileDirectory>");
            System.exit(0);
        }
        srcFileDir = args[0];
        destFileDir = args[1];
        System.out.println("Input dir = " + srcFileDir);
        System.out.println("Output dir = " + destFileDir);

        PDQTrialLoaderPreprocessor instance = new PDQTrialLoaderPreprocessor();

        final File dir = new File(srcFileDir);

        String[] xmlFiles = dir.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        });

        System.out.println(xmlFiles.length + " files to process");

        for (String xmlFile : xmlFiles) {
            try {
                instance.process(xmlFile);
            } catch (Exception e) {
                System.out.println("Unable to process " + xmlFile + " \n" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Runs all pre-processing for a file.
     * @param xmlFile file to run.
     * @throws JDOMException jdom exception.
     * @throws IOException io exception.
     */
    public void process(String xmlFile) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(srcFileDir + "/" + xmlFile);

        replaceSponsor(document);
        changeLeadOrgId(document);
        changeMaxAge(document);
        addDefaultArm(document);
        checkOutcomeMeasure(document);

        // Do following 3 in this order.
        defaultStartDate(document);
        replaceStartDateType(document);
        replaceAnticipatedDate(document);

        processPrimaryPurposeCode(document);

        // call last. Trials will no longer be observational after this.
        prependObservational(document);

        removePartSites(document);
        checkRespParty(document);
        checkPrimaryCompDate(document);
        checkIndStudy(document);

        FileOutputStream fos = new FileOutputStream(destFileDir + "/" + xmlFile);
        XMLOutputter outputter = new XMLOutputter();
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        String output = outputter.outputString(document);
        osw.write(output);
        osw.close();
    }
    
    private void checkIndStudy(Document document) {
    	Element indStudy = document.getRootElement().getChild("is_ind_study");
    	if (indStudy!= null && !indStudy.getText().isEmpty()) {
    		if ("yes".equalsIgnoreCase(indStudy.getText())) {
    			Element fdaRegulated = document.getRootElement().getChild("is_fda_regulated");
    			if (fdaRegulated != null && !fdaRegulated.getText().isEmpty()
    					&& !fdaRegulated.getText().equalsIgnoreCase("yes")) {
    				fdaRegulated.setText("yes"); 
    			} else if (fdaRegulated == null) {
    				fdaRegulated = new Element("is_fda_regulated");
    				fdaRegulated.setText("yes");
    				document.getRootElement().addContent(fdaRegulated);
    			}
    		}
    	}        
    }
    
    private void checkPrimaryCompDate(Document document) {
        Element primaryComplDate = document.getRootElement().getChild("primary_compl_date");
        if (primaryComplDate.getText().isEmpty()) {
        	Element overallStatus = document.getRootElement().getChild("overall_status");
        	if ("Completed".equalsIgnoreCase(overallStatus.getText())) {
        		Element leadOrgStatusElmt = document.getRootElement().getChild("lead_org_status");                
        		primaryComplDate.setText(leadOrgStatusElmt.getAttributeValue("status_date"));
        		document.getRootElement().getChild("primary_compl_date_type").setText("Actual"); 
        	} else {
        		primaryComplDate.setText(DEFAULT_FUTURE_DATE);
        		document.getRootElement().getChild("primary_compl_date_type").setText("Anticipated");
        	}
        }
    }
    
    private void checkRespParty(Document document) {
        Element respParty = document.getRootElement().getChild("sponsors").getChild("resp_party");
        if (respParty == null) {
            Element rp = new Element("resp_party");
            rp.setAttribute("party-type", "organization");
            
            Element name = new Element("name_title");
            name.setText("National Cancer Institute");
            rp.addContent(name);
            
            Element org = new Element("organization");
            org.setAttribute("ctep-id", "NCI");
            org.setText("National Cancer Institute");
            rp.addContent(org);
            
            Element phone = new Element("phone");
            phone.setText("866-319-4357");
            rp.addContent(phone);
            
            Element email = new Element("email");
            email.setText("NCICTRO@mail.nih.gov");
            rp.addContent(email);

            Element sponsors = document.getRootElement().getChild("sponsors");
            sponsors.addContent(rp);
        }
    }

    private void checkOutcomeMeasure(Document document) {    	
    	for (Element primaryOutcome : (List<Element>) document.getRootElement().getChildren("primary_outcome")) {
    		appendOutcomeMeasure(primaryOutcome);
        }    	
    	for (Element secondaryOutcome : (List<Element>) document.getRootElement().getChildren("secondary_outcome")) {
    		appendOutcomeMeasure(secondaryOutcome);
        }	
	}

	private void appendOutcomeMeasure(Element primaryOutcome) {
		Element outcomeMeasure = primaryOutcome.getChild("outcome_measure");
		if (outcomeMeasure != null && outcomeMeasure.getText() != null && outcomeMeasure.getText().length() > 250) {
			outcomeMeasure.setText(outcomeMeasure.getText().substring(0, 250) + "....");
		}
	}

	private void processPrimaryPurposeCode(Document document) {
        Element studyDesign = document.getRootElement().getChild("study_design");
        addMissingPrimaryPurposeCode(studyDesign);
        replaceInvalidPrimaryPurposeCode(studyDesign);
        capitalizeWordsInPrimaryPurposeCode(studyDesign);
    }


    /**
     * PO-3818. Add primary purpose code (i.e. interventional_subtype) if missing.
     * @param studyDesign study_design element
     */
    private void addMissingPrimaryPurposeCode(Element studyDesign) {
        Element interventionalDesign = studyDesign.getChild("interventional_design");
        if (interventionalDesign == null) {
            addInterventionalDesignAndSubtype(studyDesign);
        } else if (interventionalDesign.getChild("interventional_subtype") == null) {
            addInterventionalSubtype(interventionalDesign);
        } else if (StringUtils.isBlank(interventionalDesign.getChild("interventional_subtype").getText())) {
            addInterventionalSubtypeText(interventionalDesign.getChild("interventional_subtype"));
        }
    }

    private void addInterventionalDesignAndSubtype(Element studyDesign) {
        Element interventionalDesign = new Element("interventional_design");
        addInterventionalSubtype(interventionalDesign);
        studyDesign.addContent(interventionalDesign);
    }

    private void addInterventionalSubtype(Element interventionalDesign) {
        Element interventionalSubtype = new Element("interventional_subtype");
        addInterventionalSubtypeText(interventionalSubtype);
        interventionalDesign.addContent(interventionalSubtype);
    }

    private void addInterventionalSubtypeText(Element interventionalSubtype) {
        interventionalSubtype.setText("Treatment");
    }

    /**
     * PO-3854. Replace invalid primary purpose codes (i.e. interventional_subtype) with valid codes.
     * @param studyDesign study_design element
     */
    private void replaceInvalidPrimaryPurposeCode(Element studyDesign) {
        Element interventionalDesign = studyDesign.getChild("interventional_design");
        Element primaryPurposeCode = interventionalDesign.getChild("interventional_subtype");
        if ("Educational/Counseling/Training".equalsIgnoreCase(primaryPurposeCode.getText())) {
            primaryPurposeCode.setText("Supportive Care");
        }
    }

    /**
     * PO-3849. Capitalize first letter of each word in primary purpose codes (i.e. interventional_subtype).
     * @param studyDesign study_design element
     */
    private void capitalizeWordsInPrimaryPurposeCode(Element studyDesign) {
        Element interventionalDesign = studyDesign.getChild("interventional_design");
        Element primaryPurposeCode = interventionalDesign.getChild("interventional_subtype");
        primaryPurposeCode.setText(WordUtils.capitalize(primaryPurposeCode.getText()));
    }

    /**
     * PO-3778. Remove all part sites but the first for cooperative group trials.
     * @param document
     */
    private void removePartSites(Document document) {
        Element leadOrg = document.getRootElement().getChild("lead_org");

        if (COOP_GROUP_LIST.contains(leadOrg.getText().trim().toLowerCase())) {
            List<Element> locations = document.getRootElement().getChildren("location");
            while(locations.size() > 1) {
                locations.remove(locations.size() - 1);
            }
        }
    }

    private void replaceAnticipatedDate(Document document) {
        Element startDate = document.getRootElement().getChild("start_date");
        Element primaryComplDate = document.getRootElement().getChild("primary_compl_date");
        if (startDate != null && "Anticipated".equalsIgnoreCase(startDate.getAttribute("date_type").getValue())) {
            startDate.setText(DEFAULT_FUTURE_DATE);
            primaryComplDate.setText(DEFAULT_FUTURE_DATE);
        }
    }

    /**
     * for obs. trials, default the start date based on overall status.
     * If Overall Status = Not Yet Recruiting
     * Then add an element for Start Date as
     * date_type="Anticipated" start_date=2100-01-01
     * If Overall Status = Recruiting/No Longer Recruiting (Closed)
     * date_type="Actual" start_date=1900-01-01
     * @param document
     */
    private void defaultStartDate(Document document) {
        Element studyDesign = document.getRootElement().getChild("study_design");
        if ("observational".equals(studyDesign.getChild("study_type").getText())) {
            Element startDate = document.getRootElement().getChild("start_date");
            Element overallStatus = document.getRootElement().getChild("overall_status");
            if (startDate == null
                    || (StringUtils.isBlank(startDate.getAttribute("date_type").getValue())
                            && StringUtils.isBlank(startDate.getText()))) {

                startDate = new Element("start_date");

                if ("Not Yet Recruiting".equalsIgnoreCase(overallStatus.getText())) {
                    document.getRootElement().removeChild("start_date");
                    startDate.setAttribute("date_type", "Anticipated");
                    startDate.setText(DEFAULT_FUTURE_DATE);
                    document.getRootElement().addContent(startDate);
                } else if ("Recruiting".equalsIgnoreCase(overallStatus.getText())
                        || "No longer recruiting".equalsIgnoreCase(overallStatus.getText())) {
                    document.getRootElement().removeChild("start_date");
                    startDate.setAttribute("date_type", "Actual");
                    startDate.setText(DEFAULT_PAST_DATE);
                    document.getRootElement().addContent(startDate);
                }
            }

            if ("Active, not recruiting".equalsIgnoreCase(overallStatus.getText())) {
                if (startDate != null
                        && startDate.getAttribute("date_type") != null
                        && StringUtils.equals("Actual", startDate.getAttribute("date_type").getValue())) {
                    startDate.setText(DEFAULT_FUTURE_DATE);
                    startDate.getAttribute("date_type").setValue("Anticipated");
                    document.getRootElement().getChild("primary_compl_date").setText(DEFAULT_FUTURE_DATE);
                    document.getRootElement().getChild("primary_compl_date_type").setText("Anticipated");
                }
            }

        }
    }

    /**
     * Condition: Study Type is Observational  <br>
     * Action: a. Prefix Official Title with "Observational - " <br>
     *         b. Change the Study Type = Interventional
     * @param document
     */
    private void prependObservational(Document document) {
        Element studyDesign = document.getRootElement().getChild("study_design");
        if ("observational".equals(studyDesign.getChild("study_type").getText())) {
            Element offTitle = document.getRootElement().getChild("official_title");
            offTitle.setText("Observational - " + offTitle.getText());
            studyDesign.getChild("study_type").setText("interventional");
        }
    }



    /**
     * replaces the lead org ID with the first secondary ID, removing the first secondary ID.
     * Add the org_study_Id as a secondary ID.
     * @param document jdom document
     */
    private void changeLeadOrgId(Document document) {
        Element idInfo = document.getRootElement().getChild("id_info");

        Element secId = idInfo.getChild("secondary_id");
        String studyId = idInfo.getChild("org_study_id").getText();

        idInfo.getChild("org_study_id").setText(secId.getText());
        idInfo.removeChild("secondary_id");

        Element newId = new Element("secondary_id");
        newId.setText(studyId);

        idInfo.addContent(newId);

    }

    /**
     * replaces the max age from 120 to 999.
     * @param document jdom document.
     */
    private void changeMaxAge(Document document) {
        Element maxAge = document.getRootElement().getChild("eligibility").getChild("maximum_age");

        if("120".equals(maxAge.getText())) {
            maxAge.setText("999");
        }

    }

    /**
     * replaces the sponsor to CTEP or DCP based on trial ID.
     * @param document jdom document
     */
    private void replaceSponsor(Document document) {
        String ctepId = document.getRootElement().getAttribute("ctep-id").getValue();
        String dcpId = document.getRootElement().getAttribute("dcp-id").getValue();

        Element agency = document.getRootElement()
            .getChild("sponsors").getChild("lead_sponsor").getChild("agency");

        String toBe = agency.getText();
        if (StringUtils.isNotEmpty(dcpId)) {
            toBe = PAConstants.DCP_ORG_NAME;
        } else if (StringUtils.isNotEmpty(ctepId)) {
            toBe = PAConstants.CTEP_ORG_NAME;
        }

        agency.setText(toBe);
    }

    /**
     * Adds a default arm, called "Arm I", link it to all Interventions and update number_of_arms to have the value 1.
     */
    private void addDefaultArm(Document document) {
        List<Element> armGroups = document.getRootElement().getChildren("arm_group");

        if (CollectionUtils.isEmpty(armGroups)) {
            Element group = new Element("arm_group");
            Element label = new Element("arm_group_label");
            label.setText(DEFAULT_ARM);
            group.addContent(label);
            document.getRootElement().addContent(group);

            List<Element> interventions = document.getRootElement().getChildren("intervention");

            for (Element intervention : interventions) {
                Element labelLink = new Element("arm_group_label");
                labelLink.setText(DEFAULT_ARM);
                intervention.addContent(labelLink);
            }
            //Default number_of_arms to 1
            Element studyDesign = document.getRootElement().getChild("study_design");
            if ("interventional".equals(studyDesign.getChild("study_type").getText())) {
                Element numberOfArms = studyDesign.getChild("interventional_design")
                    .getChild("number_of_arms");
                numberOfArms.setText("1");
            }
        }
    }

    /**
     * Replaces the start date type of 'Projected' with 'Anticipated'.
     * @param document jdom document
     */
    private void replaceStartDateType(Document document) {
        Element startDate = document.getRootElement().getChild("start_date");
        if (startDate != null) {
            String startDateType = startDate.getAttributeValue("date_type");
            if (StringUtils.equals("Projected", startDateType)) {
                startDate.setAttribute("date_type", "Anticipated");
            }
        }
    }
}

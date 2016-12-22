<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:param name="page-type" select="'A4'"/>
	<xsl:template match="/">
	<body style="margin:5; padding:0; font-family:Times New Roman; color:#000;font-size:13;background:#fff; min-width:920px">
<xsl:if test="clinical_study/brief_title">
	<font size="4" face="Times New Roman"><b>Protocol Title</b></font>
	<font size="2" face="Times New Roman"><p><i><xsl:value-of select="clinical_study/brief_title"/></i></p></font>
</xsl:if>
<xsl:if test="clinical_study/official_title">
    <font size="4" face="Times New Roman"><b>Original Title</b></font><br/>
	<font size="2" face="Times New Roman"><p><i><xsl:value-of select="clinical_study/official_title"/></i></p></font>
</xsl:if>
<xsl:if test="clinical_study/id_info">
	<font size="4" face="Times New Roman"><b> General Protocol Information</b></font><br/>
	<xsl:for-each select="clinical_study/id_info"><br/>
	<table style="border: 1px; font-family:Times New Roman;font-size:13;">
	<tbody align="left">
		<tr><td><b>Provider Name:</b></td><td><xsl:value-of select="provider_name"/></td></tr>
		<tr><td><b>Provider study ID:</b></td><td><xsl:value-of select="provider_study_id"/></td></tr>
		<tr><td><b>Org Name:</b></td><td><xsl:value-of select="org_name"/></td></tr>
		<tr><td><b>Org Study ID:</b></td><td><xsl:value-of select="org_study_id"/></td></tr>
		<tr><td><b>Secondary ID:</b></td><td><xsl:value-of select="secondary_id"/></td></tr>
		</tbody>
		</table>
		</xsl:for-each>
</xsl:if>
	<xsl:if test="clinical_study/ind_info">
		<table style="border: 1px; font-family:Times New Roman;font-size:13;">
		<tbody align="left">
		<xsl:for-each select="clinical_study/ind_info">
		<tr><td><b>Ind Grantor:</b></td><td><xsl:value-of select="ind_grantor"/></td></tr>
		<tr><td><b>Ind Number:</b></td><td><xsl:value-of select="ind_number"/></td></tr>
		<tr><td><b>Ind Serial Number:</b></td><td><xsl:value-of select="ind_serial_number"/></td></tr>
		<tr><td><b>Has Expanded Access:</b></td><td><xsl:value-of select="has_expanded_access"/></td></tr>
		</xsl:for-each>
		</tbody>
		</table>
	</xsl:if>		
		<br/><br/>
		<font size="4" face="Times New Roman"><b>Status</b></font><br/><br/>
		<table style="border: 1px; font-family:Times New Roman;font-size:13;">
			   
		<tbody align="left">
			<tr>
				<td><b>Overall Status:</b></td><td><xsl:value-of select="clinical_study/overall_status"/></td>
			</tr>
			<tr>
				<td><b>Start Date:</b></td><td><xsl:value-of select="clinical_study/start_date"/></td>
			</tr>
			<tr>
				<td><b>Primary Complete Date:</b></td><td><xsl:value-of select="clinical_study/primary_compl_date"/></td>
			</tr>
			<tr>
				<td><b>Primary Complete Date Type:</b></td><td><xsl:value-of select="clinical_study/primary_compl_date_type"/></td>
			</tr>
			<tr>
				<td><b>Phase</b></td><td><xsl:value-of select="clinical_study/phase"/></td>
			</tr>
		</tbody>
	</table>
	<br></br>
	
	<font size="4" face="Times New Roman"><b>Required Regulatory Information</b></font><br/><br/>
	 <table style="border: 1px; font-family:Times New Roman;font-size:13;">
	 <tbody align="left">
		<tr>
		<td>
		<b>Is FDA Regulated:</b></td><td><xsl:value-of select="clinical_study/is_fda_regulated"/></td>
		</tr>
		<tr><td><b>Is Section 801:</b></td><td><xsl:value-of select="clinical_study/is_section_801"/></td>
		</tr>
		<tr><td><b>Delayed Posting:</b></td><td><xsl:value-of select="clinical_study/delayed_posting"/></td>
		</tr>
		<tr><td><b>Is Study:</b></td><td><xsl:value-of select="clinical_study/is_ind_study"/></td>
		</tr>
		</tbody>
	</table>
	<br/>
<xsl:if test="clinical_study/oversight_info"> 
	<table style="border: 1px; font-family:Times New Roman;font-size:13;">
	   <xsl:for-each select="clinical_study/oversight_info">
			<tbody  align="left">
				<tr><td><b>Regulatory Authority:</b></td><td><xsl:value-of select="regulatory_authority"/></td></tr>
				<tr><td><b>Has DMC:</b></td><td><xsl:value-of select="has_dmc"/></td></tr>
				 <xsl:for-each select="irb_info">
				<tr><td> <b>Approval Status:</b></td><td><xsl:value-of select="approval_status"/></td></tr>
				<tr><td><b>Name:</b></td><td><xsl:value-of select="name"/></td></tr>
				<tr><td><b>Affiliation:</b></td><td><xsl:value-of select="affiliation"/></td></tr>
				<tr><td><b>Phone:</b></td><td><xsl:value-of select="phone"/></td></tr>
				<tr><td><b>Email:</b></td><td><xsl:value-of select="email"/></td></tr>
				<tr><td><b>Full Address:</b></td><td><xsl:value-of select="full_address"/></td></tr>
				</xsl:for-each>
				</tbody>
		</xsl:for-each>
	</table>
</xsl:if>
<br/><br/>	
<xsl:if test="clinical_study/sponsors">
	<xsl:for-each select="clinical_study/sponsors">
	<font size="4" face="Times New Roman"><b>Responsible Party</b></font>
		<p><font size="2" face="Times New Roman"><b>Lead Sponsor:</b></font><xsl:text> </xsl:text><font size="2" face="Times New Roman"><xsl:value-of select="lead_sponsor/agency"/></font></p>
     	  <p><font size="2" face="Times New Roman"><b>Collaborator(s)</b></font><br/><br/>
 			<xsl:for-each select="collaborator/agency">
			<font size="2" face="Times New Roman"> <b>Agency:</b></font><xsl:text> </xsl:text><font size="2" face="Times New Roman"> <xsl:apply-templates/></font><br/>
      </xsl:for-each></p>
     </xsl:for-each>
</xsl:if>
   <br/>
<xsl:if test="clinical_study/overall_official">
  <font size="4" face="Times New Roman"><b>Protocol Personnel(s)</b></font><br/><br/>
		<table style="border: 1px; font-family:Times New Roman;font-size:13;">
	   	<tbody align="left">
		<xsl:for-each select="clinical_study/overall_official">
			<tr>
				<td><b>Name:</b></td><td><xsl:value-of select="first_name"/><xsl:text> </xsl:text><xsl:value-of select="middle_name"/><xsl:value-of select="last_name"/></td>
			</tr>
			<tr>
				<td><b>Degrees:</b></td><td><xsl:value-of select="degrees"/></td>
			</tr>
			<tr>
				<td><b>Role:</b></td><td><xsl:value-of select="role"/></td>
			</tr>
				<tr>
				<td><b>Affliation:</b></td><td><xsl:value-of select="affiliation"/></td>
			</tr>
		</xsl:for-each>
			</tbody>
	</table>
</xsl:if>
	<br></br>
<xsl:if test="clinical_study/eligibility">
	<font size="4" face="Times New Roman"><b>Protocol Information</b></font><br/><br/>
	<table style="border: 1px; font-family:Times New Roman;font-size:13;">
	 <tbody align="left">
		<xsl:for-each select="clinical_study/eligibility">
			<tr valign="top">
				<td><b>Criteria:</b></td><td><pre><font size="2" face="Times New Roman"><i><xsl:value-of select="criteria/textblock"/></i></font></pre></td>
			</tr>
			<tr valign="top">
				<td><b>Healthy Volunteers:</b></td><td><xsl:value-of select="healthy_volunteers"/></td>
			</tr>
			<tr valign="top">
				<td><b>Expected Enrollment:</b></td><td><xsl:value-of select="expected_enrollment"/></td>
			</tr>
			<tr valign="top">
				<td><b>Gender:</b></td><td><xsl:value-of select="gender"/></td>
			</tr>
			<tr>
				<td><b>Minimum age:</b></td><td><xsl:value-of select="minimum_age"/></td>
			</tr>
			
			<tr>
				<td><b>Maximum age:</b></td><td><xsl:value-of select="maximum_age"/></td>
			</tr>
		</xsl:for-each>
			</tbody>
	</table>
</xsl:if>
<br></br>
<xsl:if test="clinical_study/brief_summary">	
	<xsl:for-each select="clinical_study/brief_summary">
		<font size="4" face="Times New Roman"><b>Brief Summary</b></font><br></br>
			<font size="2" face="Times New Roman"> <p><i><xsl:value-of select="textblock"/></i></p></font>
	</xsl:for-each>
</xsl:if>
<xsl:if test="clinical_study/detailed_description">	
	<xsl:for-each select="clinical_study/detailed_description">
	 <font size="4" face="Times New Roman"><b>Detailed Description</b></font><br/>
		<pre><font size="2" face="Times New Roman"><i> <xsl:value-of select="textblock"/></i></font></pre>
	</xsl:for-each>
</xsl:if>
<xsl:if test="clinical_study/study_design"> 	
	<font size="4" face="Times New Roman"><b>Protocol Design</b></font><br/>
	<table  style="border: 1px; font-family:Times New Roman;font-size:13;">
	  <xsl:for-each select="clinical_study/study_design">
		<tbody align="left">
			<tr>
				<td >
				<table  style="border: 1px; font-family:Times New Roman;font-size:13;">
				<tbody align="left">
				<tr><td><b>Study Type:</b></td><td><xsl:value-of select="study_type"/></td></tr>
				<xsl:if test="interventional_design">
				 <xsl:for-each select="interventional_design">
				 <tr><td><b>Interventional sub Type:</b></td><td><xsl:value-of select="interventional_subtype"/></td></tr>
				<tr><td><b>Interventional Phase:</b></td><td><xsl:value-of select="phase"/></td></tr>
				<tr><td><b>Interventional Allocation:</b></td><td><xsl:value-of select="allocation"/></td></tr>
				<tr><td><b>Interventional Masking:</b></td><td><xsl:value-of select="masking"/></td></tr>
				<tr><td><b>Interventional Masked Caregiver:</b></td><td><xsl:value-of select="masked_caregiver"/></td></tr>
				<tr><td><b>InterventionalMasked Subject:</b></td><td><xsl:value-of select="masked_subject"/></td></tr>
				<tr><td><b>Interventional Assignment:</b></td><td><xsl:value-of select="assignment"/></td></tr>
				<tr><td><b>Interventional Endpoint:</b></td><td><xsl:value-of select="endpoint"/></td></tr>
				<tr><td><b>Number of arms:</b></td><td><xsl:value-of select="number_of_arms"/></td></tr>
				</xsl:for-each>
				</xsl:if>
				</tbody>
				</table>
				</td>
			</tr>
			</tbody>
			</xsl:for-each>
	</table>
</xsl:if>
		<br/>
<xsl:if test="clinical_study/intervention"> 
    <font size="4" face="Times New Roman"><b>Intervention</b></font><br/><br/>
			<table style="border: 1px; font-family:Times New Roman;font-size:13;">
	  		<tbody align="left">
		<xsl:for-each select="clinical_study/intervention">
			<tr>
				<td><b>Type:</b></td><td><xsl:value-of select="intervention_type"/></td>
			</tr>
			<tr>
				<td><b>Name:</b></td><td><xsl:value-of select="intervention_name"/></td>
			</tr>
			<tr>
				<td ><b>Description:</b></td><td ><xsl:value-of select="intervention_description/textblock"/></td>
			</tr>
			<tr>
				<td><b>Other Name:</b></td><td><xsl:value-of select="intervention_other_name"/></td>
			</tr>
			<xsl:for-each select="arm_group_label">
				<tr>
				<td ><b>Arm Group Label:</b></td><td><xsl:apply-templates/></td>
			</tr>
			</xsl:for-each>
		</xsl:for-each>
			</tbody>
	</table>
</xsl:if>
<br/>
<font size="4" face="Times New Roman"><b>Protocol Outcomes</b></font><br/><br/>
<xsl:if test="clinical_study/primary_outcome"> 
	<table  style="border: 1px; font-family:Times New Roman;font-size:14;">
	   <thead >
			<tr align="left">
				<th style="font-family:Times New Roman;font-size:13;">Primary Outcomes</th>
			</tr>
		</thead>
		<tbody align="left">
		<ul>
			<tr>
				<td >
				<xsl:for-each select="clinical_study/primary_outcome">
				<li><xsl:value-of select="outcome_measure"/><xsl:text> </xsl:text>[<i>Designated as safety issue:<xsl:text> </xsl:text><xsl:value-of select="outcome_safety_issue"/></i>][<i>Designated Time frame:<xsl:text> </xsl:text><xsl:value-of select="outcome_time_frame"/></i>]
			    </li>
				</xsl:for-each>
				</td>
			</tr>
         </ul>
			</tbody>
	</table>
</xsl:if>
	<br/>
<xsl:if test="clinical_study/secondary_outcome"> 
	<table style="border: 1px; font-family:Times New Roman;font-size:14;">
	   <thead >
		<tr align="left">
			<th style="font-family:Times New Roman;font-size:13;">Secondary Outcomes</th>
		</tr>
		</thead>
		<tbody align="left">
		   <ul>
			<tr>
				<td>
				<xsl:for-each select="clinical_study/secondary_outcome">
				<li><xsl:value-of select="outcome_measure"/><xsl:text> </xsl:text>[<i>Designated as safety issue:<xsl:text> </xsl:text><xsl:value-of select="outcome_safety_issue"/></i>][<i>Designated Time frame:<xsl:text> </xsl:text><xsl:value-of select="outcome_time_frame"/></i>]
			    </li>
				</xsl:for-each>
			</td>
			</tr>
			</ul>
		</tbody>
	</table>
</xsl:if>
	<br/>
<xsl:if test="clinical_study/enrollment">
	<xsl:for-each select="clinical_study/enrollment">
		<font size="4" face="Times New Roman"><b>Enrollment</b></font><br></br>
			<font size="2" face="Times New Roman"><p><xsl:apply-templates/></p></font>
		</xsl:for-each>
</xsl:if>
<xsl:if test="clinical_study/arm_group">
  <font size="4" face="Times New Roman"><b>Arm Group</b></font><br/><br/>
	<table style="border: 1px; font-family:Times New Roman;font-size:13;">
	  <tbody align="left">
		<xsl:for-each select="clinical_study/arm_group">
			<tr>
				<td><b>Arm Group Label:</b></td><td><xsl:value-of select="arm_group_label"/></td>
			</tr>
			<tr>
				<td><b>Arm Type:</b></td><td><xsl:value-of select="arm_type"/></td>
			</tr>
			<tr>
				<td><b>Arm Group Description:</b></td><td><xsl:value-of select="arm_group_description/textblock"/></td>
			</tr>
			</xsl:for-each>
			</tbody>
	</table>
</xsl:if>
<br/>
<xsl:if test="clinical_study/keyword">
<font size="4" face="Times New Roman"><b>Diagnosis/Condition</b></font><br/><br/>
<table  style="border: 1px; font-family:Times New Roman;font-size:13;">
	  <tbody align="left">
		<xsl:for-each select="clinical_study/keyword">
		<ul>
		<tr>
			<td>
			<li><xsl:apply-templates/></li>
			</td>
		</tr>
		</ul>
		</xsl:for-each>
		</tbody>
	</table>
</xsl:if>
	<br/>
<xsl:if test="clinical_study/criteria"> 	
<xsl:for-each select="clinical_study/criteria">
<font size="4" face="Times New Roman"><b>Criteria</b></font><br></br>
	<font size="2" face="Times New Roman"><p><i><xsl:value-of select="textblock"/></i></p></font>
</xsl:for-each>
</xsl:if>
<br/>
<xsl:if test="clinical_study/location"> 
<font size="4" face="Times New Roman"><b>Location(s)</b></font><br/>
 <xsl:for-each select="clinical_study/location"><br/>
	<table style="border: 1px;border-color: #000;font-family:Times New Roman;font-size:13; background:#ccc">
	  <tbody align="left" >
			<tr>
				<td>
				 <table style="border: 1px; font-family:Times New Roman;font-size:14;" cellpadding="3" cellspacing="3">
				 <tr><td><b>Facility:</b></td><td><xsl:value-of select="facility/name"/></td>
				 <td><b>Address:</b></td><td><xsl:value-of select="facility/address"/></td>
				 <td><b>Status:</b></td><td><xsl:value-of select="status"/></td></tr>
				</table></td></tr>
				<xsl:for-each select="contact">
		        <tr><td>
		   		 <table style="border: 1px; font-family:Times New Roman;font-size:14;" cellpadding="3" cellspacing="3">	
				 <tr><td><b>Contact:</b></td><td><xsl:value-of select="first_name"/><xsl:text> </xsl:text><xsl:value-of select="last_name"/></td>
				 <td><b>Phone:</b></td><td><xsl:value-of select="phone"/></td>
				 <td><b>Email:</b></td><td><xsl:value-of select="email"/></td></tr>		
				 </table>
				 </td></tr>
		   		</xsl:for-each>
				<xsl:for-each select="investigator">
				<tr><td>
				<table style="border: 1px; font-family:Times New Roman;font-size:14;" cellpadding="3" cellspacing="3">
				<tr><td><b>Investigator:</b></td><td><xsl:value-of select="first_name"/><xsl:text> </xsl:text><xsl:value-of select="last_name"/></td>
				<td><b>Role:</b></td><td><xsl:value-of select="role"/></td></tr>
            	</table>
				</td> </tr>
				</xsl:for-each>
		   		</tbody>
	</table>
</xsl:for-each>
</xsl:if>
	<br/>
<xsl:if test="clinical_study/link"> 
<font size="4" face="Times New Roman"><b>Link(s)</b></font><br/><br/>
 <table style="border: 1px; font-family:Times New Roman;font-size:13;">
	   <tbody align="left">
		  <xsl:for-each select="clinical_study/link">
			<tr>
				<td><b>URL:</b></td><td><xsl:value-of select="url"/></td>
			</tr>
			<tr>
				<td><b>Description:</b></td><td><xsl:value-of select="description"/></td>
			</tr>
		  </xsl:for-each>
		</tbody>
	</table>
</xsl:if>
</body>
</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2008. Progress Software Corporation. All rights reserved.

<metaInformation>
	<scenarios>
		<scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="" htmlbaseurl="" outputurl="" processortype="saxon8" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline=""
		          additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="">
			<advancedProp name="sInitialMode" value=""/>
			<advancedProp name="bXsltOneIsOkay" value="true"/>
			<advancedProp name="bSchemaAware" value="true"/>
			<advancedProp name="bXml11" value="false"/>
			<advancedProp name="iValidation" value="0"/>
			<advancedProp name="bExtensions" value="true"/>
			<advancedProp name="iWhitespace" value="0"/>
			<advancedProp name="sInitialTemplate" value=""/>
			<advancedProp name="bTinyTree" value="true"/>
			<advancedProp name="bWarnings" value="true"/>
			<advancedProp name="bUseDTD" value="false"/>
			<advancedProp name="iErrorHandling" value="fatal"/>
		</scenario>
	</scenarios>
	<MapperMetaTag>
		<MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/>
		<MapperBlockPosition></MapperBlockPosition>
		<TemplateContext></TemplateContext>
		<MapperFilter side="source"></MapperFilter>
	</MapperMetaTag>
</metaInformation>
-->
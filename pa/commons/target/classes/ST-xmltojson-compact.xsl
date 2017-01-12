<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output standalone="yes" omit-xml-declaration="yes" include-content-type="no"/>
    
    <xsl:template match="/">
        <xsl:apply-templates select="/statusRules"/>
    </xsl:template>
    
	<xsl:template match="/statusRules">
{
/**   
 * This file contains status valid values, and status transition rules for PA and REGISTRATION for: 
 *  - Abbreviate Trials / Trials Status 
 *  - Abbreviate Trials / Site Status 
 *  - Complete Trials / Trial Status 
 *  - Complete Trials / Site Status 
 *  - General error messages related to Trial and Site Status 
 *- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  
 * Date: 12/22/2014 
 * Author: Charles Yaghmour 
 * Changes: Initial Version 
 *- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 * Date: 12/22/2014
 * Author: Charles Yaghmour
 * Date: 1/9/2015 (Charles Yaghmour)
 * Changes: Added APPROVED transitions to all sections
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
 * Date: 01/28/2014
 * Author: Charles Yaghmour
 * Changes: Modified all [PA]-[Transitions] sections to change Erros to Warnings per the requirements spreadsheet
 *         dated 1/29/2015. This allows the CTRO to continue with work uniterrupted until the data is clean     
 *         Updated the error message for the interim status [IN REVIEW] for transition from [STATUSZERO] to [ACTIVE]
 *         for all sections. It was referencing [APPROVED] in stead of [IN REVIEW]
 *         Deleted [ADMINISTRATIVELY_COMPLETE] from all sections. Duplicate entry
 *         Changed [COMPLETE] to {COMPLETED] for participating sites
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
 * Date: 03/12/2015
 * Author: Charles Yaghmour
 * Changes: Made changes to reflect the results of reviewing the requirements spreadsheet with the CTRO
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 * Date: 04/8/2015
 * Author: Charles Yaghmour
 * Changes: Corrected a typo in trial status values from [COMPLETED] to [COMPLETE]
 *         Changed all [True] to [true] to reduce any transformation when converting this file to JSON format
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
 *
 */
 
  "statusRulesbyApp" : {<xsl:apply-templates select="PA"/>,<xsl:apply-templates select="REGISTRATION"/>}}       
     </xsl:template>
     
    <xsl:template match="PA">"PA" : { "appName" : "PA", "statusRules" : {<xsl:apply-templates select="completeTrial"/>,<xsl:apply-templates select="AbbreviatedTrial"/>}}</xsl:template>
      
      <xsl:template match="REGISTRATION">"REGISTRATION" : { "appName" : "REGISTRATION", "statusRules" : {<xsl:apply-templates select="completeTrial"/>,<xsl:apply-templates select="AbbreviatedTrial"/>}}</xsl:template>
      
      <xsl:template match="completeTrial">"COMPLETE" : { "trialType" : "COMPLETE", "statusForList" : {<xsl:apply-templates select="trialStatus"/>,<xsl:apply-templates select="siteStatus"/>}}</xsl:template>
      
      <xsl:template match="AbbreviatedTrial">"ABBREVIATED" : { "trialType" : "ABBREVIATED", "statusForList" : {<xsl:apply-templates select="trialStatus"/>,<xsl:apply-templates select="siteStatus"/>}}</xsl:template>
      
       <xsl:template match="trialStatus">"TRIAL_STATUS" : { "type" : "TRIAL_STATUS", "statuses" : {<xsl:apply-templates select="transitions/status"/>}}</xsl:template>
      
      <xsl:template match="siteStatus">"SITE_STATUS" : { "type" : "SITE_STATUS", "statuses" : {<xsl:apply-templates select="transitions/status"/>}}</xsl:template>
      
      <xsl:template match="status"><xsl:variable name="statusNm" select="@name"/>"<xsl:value-of select="$statusNm"/>" : {"order" : <xsl:value-of select="../../validValues/status[@code=$statusNm]/@order"/>, "code" : "<xsl:value-of select="$statusNm"/>", "displayName" : "<xsl:value-of select="../../validValues/status[@code=$statusNm]/@display"/>", "transitions" : {<xsl:apply-templates select="nextStatus"/>}}<xsl:if test="not(position() = last())">,</xsl:if></xsl:template>
       
       <xsl:template match="nextStatus">"<xsl:value-of select="@name"/>" : { "name" : "<xsl:value-of select="@name"/>", "validTransition" : "<xsl:value-of select="@validTransition"/>", <xsl:if test="@exceptionType != ''">"errorType" : "<xsl:value-of select="@exceptionType"/>",</xsl:if> "errorMsgTemplate" : "<xsl:value-of select="@exceptionMessage"/>", "sameDateValidation" : { "canOccurOnSameDateAsPreviousState" : "<xsl:value-of select="sameDateValidation/@canOccurOnSameDayAsPreviousStatus"/>", <xsl:if test="sameDateValidation/@exceptionType != ''">"errorType" : "<xsl:value-of select="sameDateValidation/@exceptionType"/>",</xsl:if> "errorMsgTemplate" : "<xsl:value-of select="sameDateValidation/@exceptionMessage"/>"},"interimStatuses" : {<xsl:if test="interimStatuses/interimStatus"><xsl:apply-templates select="interimStatuses/interimStatus"/></xsl:if>}}<xsl:if test="not(position() = last())">,</xsl:if></xsl:template>
		
		<xsl:template match="interimStatus">"<xsl:value-of select="@name"/>" : { "name" : "<xsl:value-of select="@name"/>", "order" : <xsl:value-of select="@order"/>, "errorType" : "<xsl:value-of select="@exceptionType"/>", "errorMsgTemplate" : "<xsl:value-of select="@exceptionMessage"/>"}<xsl:if test="not(position() = last())">,</xsl:if></xsl:template>
	
</xsl:stylesheet>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.List"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="gov.nih.nci.pa.util.ActionUtils"%>
<%@page import="gov.nih.nci.pa.action.ManageTermsAction"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="pagePrefix" value="disclaimer." />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="${scriptPath}/js/jquery-ui-1.11.4.custom/jquery-ui.css"
    rel="stylesheet" media="all" type="text/css" />
<s:set name="action" value="action" />
<title><fmt:message key="manageTerms.syncDisease.page.title" /></title>
<s:head />
<script type="text/javascript">
 

    function syncDisease() {
        
    	 
    	
        var form  = document.forms[0];
        var datastring = $(form).serialize();
       
       var termValue = jQuery("#termValue").text(); 
       var msg ="The CTRP system is synching the term "+termValue+" with the NCIt. "
       msg = msg + "Depending on the number of parents and children in the disease term hierarchy,";
       msg = msg +" it can take from five minutes to two hours or more to sync the term.";
       msg = msg +" Please go to the CTRP Disease Term Tree in PA after a few minutes to verify.";
     
       //submit data then display msg to user and then take back to manage terms page     
        jQuery.ajax({
            type: "POST",
            url: "manageTermssyncDisease.action",
            data: datastring, 
            success: function(data)
            {              
            }
          });
       jQuery("#syncMsgDialog").text(msg);
       
        jQuery("#syncMsgDialog").dialog({
            autoOpen: false,
            resizable: false,
            modal: true,                      
            buttons: {
                "OK": function() {
                  jQuery(this).dialog("close");
                  var pageType =jQuery("#pageType").val();
                  if(pageType=="disease") {
                	  submitXsrfForm('/pa/protected/disease.action');
                  }
                  else {
                	  document.forms[0].action = "manageTerms.action";
                	  document.forms[0].submit();
                  }
                	  
                 
                 
              }
            }
         }); 
        jQuery("#syncMsgDialog").dialog("open");
        
       
        
      
    
        
        }
        
        
</script>    
</head>
<body>
  <s:if test="pageDiscriminator=='disease'">
         <s:hidden id="pageType" value="disease" />
        </s:if>
        <s:else>
         <s:hidden id="pageType" value="newSearch" />
         </s:else>
   
  
	<h1>
		<fmt:message key="manageTerms.syncDisease.page.title" />
	</h1>
	
	
	<c:set var="topic" scope="request" value="syncdisease" />
	<pa:failureMessage />
	
	 
	<table class="form">
		<thead>
			<tr>
				<th>Attribute</th>
				<th>Value in CTRP</th>
				<th>Value in NCIt</th>
			</tr>
		</thead>
		<tr>
			<td scope="row" width="20%"><label>NCIt Identifier:</label><span
				class="required">*</span></td>
			<td width="150px"><s:property
					value="currentDisease.ntTermIdentifier" /></td>
			<td width="150px"><s:property value="disease.ntTermIdentifier" /></td>
		</tr>
		<tr>
			<td scope="row"  width="20%"><label>CDR Identifier:</label></td>
			<td><s:property value="currentDisease.code" /></td>
			<td><s:property value="disease.code" /></td>
		</tr>
		<tr>
			<td scope="row"  width="20%"><label>Preferred Name:</label><span
				class="required">*</span></td>
			<td><s:if
					test="%{currentDisease.preferredName != disease.preferredName}">
					<font color="red"><strong>
				</s:if>
				<s:property value="currentDisease.preferredName" />
				<s:if
					test="%{currentDisease.preferredName != disease.preferredName}">
					</strong>
					</font>
				</s:if></td>
			<td><s:property value="disease.preferredName" /></td>
		</tr>
		<tr>
			<td scope="row"  width="20%"><label>Display Name:</label></td>
			<td><s:property value="currentDisease.menuDisplayName" /></td>
			<td><s:property value="disease.menuDisplayName" /></td>
		</tr>
		 <% List<String> newAltnames = (List<String>) ActionContext.getContext().getValueStack().findValue("disease.alterNameList");
		    List<String> currentAltnames = (List<String>) ActionContext.getContext().getValueStack().findValue("currentDisease.alterNameList"); 
		    List<String> newParentTerms = (List<String>) ActionContext.getContext().getValueStack().findValue("disease.parentTermList");
            List<String> currentParentTerms = (List<String>) ActionContext.getContext().getValueStack().findValue("currentDisease.parentTermList");
            List<String> newChildTerms = (List<String>) ActionContext.getContext().getValueStack().findValue("disease.childTermList");
            List<String> currentChildTerms = (List<String>) ActionContext.getContext().getValueStack().findValue("currentDisease.childTermList");
            %>
		<tr>
			<td scope="row"  width="20%"><label>Synonyms:</label></td>
			<td><%= ActionUtils.generateListDiffStringForManageTerms(currentAltnames, newAltnames ) %></td>
            <td><%= ActionUtils.generateListDiffStringForManageTerms(newAltnames, currentAltnames ) %></td>
		</tr>
		<tr>
		   
			<td scope="row"  width="20%"><label>Parent Terms:</label></td>
			<td> <%= ActionUtils.generateListDiffStringForManageTerms(currentParentTerms, newParentTerms ) %></td>
			<td><%= ActionUtils.generateListDiffStringForManageTerms(newParentTerms, currentParentTerms ) %></td>
		</tr>
		<tr>
			<td scope="row"  width="20%"><label>Child Terms:</label></td>
			<td> <%= ActionUtils.generateListDiffStringForManageTerms(currentChildTerms, newChildTerms ) %></td>
            <td><%= ActionUtils.generateListDiffStringForManageTerms(newChildTerms, currentChildTerms ) %></td>
		</tr>
	</table>
	<span id="termValue" style="display:none"><s:property value="currentDisease.ntTermIdentifier" /></span>
	<div align="center"><span class="info">Note:  'CDR Identifier' attribute is NOT synchronized from NCIt; its existing CTRP value(s) shown above will be retained.</span></div>
	<div class="actionsrow">
		<del class="btnwrapper">
			<ul class="btnrow">
				<li><s:a href="javascript:void(0)" cssClass="btn"
                                onclick="syncDisease()">
						<span class="btn_img"><span class="save">Sync Term</span></span>
					</s:a> <s:a href="manageTermssearchDisease.action?searchStart=true" cssClass="btn">
						<span class="btn_img"><span class="cancel">Cancel</span></span>
					</s:a></li>
			</ul>
		</del>
	</div>
	<div id="syncMsgDialog" style="display:none">
        </div>
</body>
</html>
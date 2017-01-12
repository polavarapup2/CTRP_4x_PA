<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html>
<head>
<link href="${scriptPath}/js/jquery-ui-1.11.4.custom/jquery-ui.css"
    rel="stylesheet" media="all" type="text/css" />
<c:url value="/protected/popupDisdisplayDiseaseWidget.action?lookUp=true"
	var="lookupUrl" />
<c:url value="/protected/manageTermsajaxGetDiseases.action?diseaseIds="
	var="diseaseAjaxURL" />
<c:url value="/protected/manageTermsajaxDisplayNameLookup.action?searchTerm="
    var="displayNamelookupUrl" />
	
<script type="text/javascript">
    
    jQuery(function() {
	    var displayNameList = <s:property escape="false" value="disease.displayNameList"/>;
	    jQuery("#menuDisplayName").autocomplete({source: displayNameList});
    });
    
	function saveDisease() {
		
		var pageName =jQuery("#pageName").val();
		if(pageName=="newTerm"){
			
			selectAllListItems();
	        if (checkForNullCodes('parentTerms') || checkForNullCodes('childTerms') ) {
	            var r = confirm("WARNING: One or more of the selected parent or child terms do not have a NCIt identifier, continue to Save?");
	            if (r == false) {
	                return;
	            }
	        }
			 document.forms[0].action = "manageTermssaveDisease.action";
	         document.forms[0].submit();
	         return;
		}
		   //  addFieldError("disease.preferredName", getText("manageTerms.fieldError.name"));		
		   var value = jQuery("#menuDisplayName").val()
		  if(value=="") {
			  alert('<s:text name="manageTerms.fieldError.menuDisplayName"/>');
			  jQuery("#menuDisplayName").focus();
			  return false;
		  }
		 
		
		
		
		var termValue = jQuery("#ntTermIdentifier").val();
		var msg ="The CTRP system is synching the term "+termValue+" with the NCIt. "
	    msg = msg + "Depending on the number of parents and children in the disease term hierarchy,";
	    msg = msg +" it can take from five minutes to two hours or more to sync the term.";
	    msg = msg +" Please go to the CTRP Disease Term Tree in PA after a few minutes to verify.";
		 
	  //submit data then display msg to user and then take back to manage terms page     
		 var form  = document.forms[0]
	     var datastring = $(form).serialize();
	        
	      jQuery.ajax({
	            type: "POST",
	            url: "manageTermssaveDisease.action",
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
	
	function checkForNullCodes(diseaseList){
		var opts = document.getElementById(diseaseList).options;
		for (var i=0; i<opts.length; i++) {
			if (opts[i].text.indexOf(':') == 0 ) {
				return true;
			}
		}
		return false;
	}

	function lookupParentDisease() {
		showPopWin('${lookupUrl}', 1100, 500, addToParentList,
				'Look Up Parent Diseases');
	}

	function lookupChildDisease() {
		showPopWin('${lookupUrl}', 1100, 500, addToChildList,
				'Lookup Child Diseases');
	}
	
	function lookupDisplayName() {
        showPopWin('${lookupUrl}', 1100, 500, setDisplayName,
                'Lookup Disease Display Name (Select only one term)');
    }

	function setDisplayName(ret){
        var retValue = ret.value;
        if(retValue != '') {
        	 // select only the first term if more than one term was selected
        	var retValue = retValue.split(',')[0];
        	jQuery.get('${diseaseAjaxURL}' + retValue, function(value) {
        		var name = value.split(':')[1];
                jQuery('#menuDisplayName').val(name);
            });
        } 
    }
	
	function addToParentList(ret) {
		var retValue = ret.value;
		if(retValue != '') {
			jQuery.get('${diseaseAjaxURL}' + retValue, function(value) {
				addDiseaseToList(value,'parentTerms')
			});
		}
	}

	function addToChildList(ret) {
		var retValue = ret.value;
		if(retValue != '') {
			jQuery.get('${diseaseAjaxURL}' + retValue, function(value) {
				addDiseaseToList(value, 'childTerms')
			});
		}
	}

	function addDiseaseToList(values, listName) {
		values = values.split('\n');
		for (var i = 0; i < values.length; i++) {
			if (values[i] != ''){
				var termAttrs = values[i].split(":")
				var val, opt
				if(termAttrs[1] == ''){
					val = termAttrs[0];
				}else{
					val = termAttrs[1]+': '+termAttrs[2];
				}
				if(jQuery('#' + listName + ' option[value="' + val + '"]').length == 0) {
				    var option = new Option(termAttrs[1]+': '+termAttrs[2], val, 'selected');
				    jQuery('#' + listName).append(option);
			    }
			}
		}
	}

	function selectAllListItems() {
		jQuery('#alterNames option').prop('selected', true);
		jQuery('#parentTerms option').prop('selected', true);
		jQuery('#childTerms option').prop('selected', true);
	}
	/**
	 * Udpates options in the result list
	 * @param from
	 * @param to
	 */
	function addToList(list, source) {
		var value = jQuery('#' + source).val()
		if (value != ''
				&& jQuery('#' + list + ' option[value="' + value + '"]').length == 0) {
			var option = new Option(value, value, 'selected');
			jQuery('#' + list).append(option);
		}
		jQuery('#' + source).val("");
	}

	function removeFromList(list) {
		var values = jQuery('#' + list).val();
		if (values != null) {
		for (var i = 0; i < values.length; i++) {
			jQuery('#' + list + ' option[value="' + values[i] + '"]')
					.remove();		}
		}
	}
	function cancel() {
		submitXsrfForm('${pageContext.request.contextPath}/protected/disease.action')
	}
	
</script>
</head>
<div id="box">
	<pa:failureMessage />
	
	<s:form name="diseaseForm" method="post">
		<s:hidden id="importTerm" name="importTerm" />
		<s:if test="%{!importTerm}">
           <s:hidden id="pageName" value="newTerm" />
        </s:if>
        <s:else>
        <s:hidden id="pageName" value="importTerm" />
         </s:else>
         
          <s:if test="pageDiscriminator=='disease'">
         <s:hidden id="pageType" value="disease" />
        </s:if>
        <s:else>
         <s:hidden id="pageType" value="newSearch" />
         </s:else>
         
		<table class="form">
		  <tr>
				<td scope="row" width="20%"><label for="ntTermIdentifier">
						NCIt Identifier</label><span class="required">*</span></td>
				<td width="150px">
				<s:if test="%{!importTerm}">
				    <s:textfield id="ntTermIdentifier"
						name="disease.ntTermIdentifier" maxlength="10" size="10"
						cssStyle="width:150px"/>
			    </s:if>
			    <s:else>
			         <s:textfield id="ntTermIdentifier"
                        name="disease.ntTermIdentifier" maxlength="10" size="10"
                        cssStyle="width:150px" readonly="true" cssClass="readonly"/>
			    </s:else>
			    <span class="formErrorMsg"> <s:fielderror>
							<s:param>disease.ntTermIdentifier</s:param>
						</s:fielderror>
				</span></td>

			</tr>
			<tr>
				<td scope="row" ><label for="code">CDR
						Identifier</label></td>
				<td><s:textfield id="code" name="disease.code" maxlength="15"
						size="15" cssStyle="width:150px" /> <span class="formErrorMsg">
						<s:fielderror>
							<s:param>disease.code</s:param>
						</s:fielderror>
				</span></td>
			</tr>
			<tr>
				<td scope="row" ><label for="preferredName">Preferred
						Name</label><span class="required">*</span></td>
				<td>
				<s:if test="%{!importTerm}">
				    <s:textfield id="preferredName"
						name="disease.preferredName" maxlength="1000" size="100"
						cssStyle="width:400px" />
				</s:if>
				<s:else>
				    <s:textfield id="preferredName"
                        name="disease.preferredName" maxlength="1000" size="100"
                        cssStyle="width:400px" readonly="true" cssClass="readonly"/>
				</s:else> <span
					class="formErrorMsg"> <s:fielderror>
							<s:param>disease.preferredName</s:param>
						</s:fielderror>
				</span></td>
			</tr>
			<tr>
				<td scope="row" ><label for="menuDisplayName">Display 
				Name</label><span class="required">*</span></td>
				<td><s:if test="%{importTerm && disease.menuDisplayName == null}">
				        <s:textfield id="menuDisplayName"
						name="disease.menuDisplayName" maxlength="200" size="100"
						cssStyle="width:400px" value="%{disease.preferredName}"/>
					</s:if>
					<s:else>
							<s:textfield id="menuDisplayName"
	                        name="disease.menuDisplayName" maxlength="200" size="100"
	                        cssStyle="width:400px"/>
					</s:else>
					 <span class="formErrorMsg"> <s:fielderror>
						<s:param>disease.menuDisplayName</s:param>
					</s:fielderror>
				</span></td>
			</tr>
			 <% List<String> newAltnames = (List<String>) ActionContext.getContext().getValueStack().findValue("disease.alterNameList");
                List<String> currentAltnames = (List<String>) ActionContext.getContext().getValueStack().findValue("currentDisease.alterNameList"); 
             %>
            <s:if test="%{!importTerm}">
				<tr>
					<td scope="row" ><label for="altName">Synonyms
					</label></td>
					<td><s:textfield id="altName" name="altName" maxlength="200"
							size="100" cssStyle="width:400px" readonly="%{importTerm}" /></td>
					<td><s:if test="%{!importTerm}"><s:a href="javascript:void(0)" cssClass="btn"
							onclick="addToList('alterNames','altName')">
							<span class="btn_img"><span class="add">Add</span></span>
						</s:a></s:if></td>
				</tr>
			</s:if>
			<tr>
			    <td scope="row" >
			    <s:if test="%{importTerm}">
			         <label for="altName">Synonyms</label>
				</s:if></td>
				<td><s:select id="alterNames" size="4"
						name="disease.alterNameList" cssStyle="width:400px"
						list="disease.alterNameList" multiple="true"
						readonly="%{importTerm}" /></td>
				<td><s:if test="%{!importTerm}"><s:a href="javascript:void(0)" cssClass="btn"
						onclick="removeFromList('alterNames')">
						<span class="btn_img"><span class="delete">Remove</span></span>
				</s:a></s:if></td>
			</tr>
			<tr>
				<td scope="row" ><label for="parentTerms">Parent
						Term NCIt Ids </label></td>
				<td><s:select id="parentTerms" size="6"
						name="disease.parentTermList" cssStyle="width:400px"
						list="disease.parentTermList" multiple="true"
						readonly="%{importTerm}" /></td>
						<s:if test="%{!importTerm}">
				<td><div style="float: left; width: 100%;">
						<s:a href="javascript:void(0)" cssClass="btn"
							onClick="lookupParentDisease()">
							<span class="btn_img"><span class="add">Look Up &
									Add</span></span>
						</s:a>
					</div>
					<br/><br/>
				<div style="float: left; width: 100%;">
						<s:a href="javascript:void(0)" cssClass="btn"
							onclick="removeFromList('parentTerms')">
							<span class="btn_img"><span class="delete">Remove</span></span>
						</s:a>
					</div></td></s:if>
			</tr>
			<tr>
				<td scope="row" ><label for="childTerms">Child
						Term NCIt Ids </label></td>
				<td><s:select id="childTerms" size="6"
						name="disease.childTermList" cssStyle="width:400px"
						list="disease.childTermList" multiple="true"
						readonly="%{importTerm}" /></td>
						<s:if test="%{!importTerm}">
				<td><div style="float: left; width: 100%;">
						<s:a href="javascript:void(0)" cssClass="btn"
							onclick="lookupChildDisease()">
							<span class="btn_img"><span class="add">Look Up &
									Add</span></span>
						</s:a>
					</div>
					<br/><br/>
					<div style="float: left; width: 100%;">
						<s:a href="javascript:void(0)" cssClass="btn"
							onclick="removeFromList('childTerms')">
							<span class="btn_img"><span class="delete">Remove</span></span>
						</s:a>
					</div></td></s:if>
			</tr>

		</table>
		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
					<li><s:if test="%{!importTerm}">
							<s:a href="javascript:void(0)" cssClass="btn"
								onclick="saveDisease()">
								<span class="btn_img"><span class="save">Save</span></span>
							</s:a>
						</s:if> <s:else>
							<s:a href="javascript:void(0)" cssClass="btn"
								onclick="saveDisease()">
								<span class="btn_img"><span class="copy">Import</span></span>
							</s:a>
						</s:else>
						 <s:if test="%{pageDiscriminator.equals('lookup')}">
						 <s:a href="manageTermssearchDisease.action?searchStart=true" cssClass="btn">
                           <span class="btn_img"><span class="cancel">Cancel</span></span>
                         </s:a>
						 </s:if>
						 <s:else>
						<s:if test="%{pageDiscriminator.equals('disease')}">
						
						 <s:a onclick="javascript:cancel();" href="javascript:void(0)" cssClass="btn">
                           <span class="btn_img"><span class="cancel">Cancel</span></span>
                         </s:a>
						</s:if>
						<s:else>
						<s:a href="manageTerms.action" cssClass="btn">
							<span class="btn_img"><span class="cancel">Cancel</span></span>
						</s:a>
						</s:else> 
						</s:else>
						</li>
			</del>
		</div>
		<div id="syncMsgDialog" style="display:none">
        </div>
	</s:form>
</div>
</html>
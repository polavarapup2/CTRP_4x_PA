<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="runadhoc" />

<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<c:url value="/protected/manageTermsajaxDiseaseSearch.action?" var="ajaxDiseaseSearchUrl" />
<title><fmt:message key="diseases.widget.title" /></title>

<link href="${stylePath}/subModalstyle.css" rel="stylesheet" type="text/css" media="all"/>
<link href="${stylePath}/subModal.css" rel="stylesheet" type="text/css" media="all"/>
<link href="${stylePath}/style.css" media="all" rel="stylesheet" type="text/css"/>
<link rel="address bar icon" href="${imagePath}/favicon.ico" />
<link rel="icon" href="${imagePath}/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${imagePath}/favicon.ico" type="image/x-icon" />

<link href="<c:url value='/styles/disease/jquery.ui.potato.menu.css'/>" media="all" rel="stylesheet" type="text/css" />
<link href="<c:url value='/styles/disease/jquery-ui/ui-lightness/jquery-ui-1.8.16.custom.css'/>" media="all" rel="stylesheet" type="text/css" />
<link href="<c:url value='/styles/disease/jquery-ui/ui-lightness/jquery.ui.selectmenu.css'/>" media="all" rel="stylesheet" type="text/css" />
<link href="<c:url value='/styles/disease/ml_breadcrumbs.css'/>" rel="stylesheet" type="text/css" media="all" />
<link href="<c:url value='/styles/disease/reportui.css'/>" rel="stylesheet" type="text/css" media="all" />


<script type="text/javascript">
    var treeAjaxURL = '<c:url value='/protected/ajaxDiseaseTreegetChildren.action'/>';
    var getBranchesURL = '<c:url value='/protected/ajaxDiseaseTreegetBranches.action'/>';
    var autoCompleteList = <s:property escape="false" value="autoCompleteList"/>;
</script>

<script type="text/javascript" src="${scriptPath}/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery-ui-1.11.4.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.ui.selectmenu.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.sizes.min.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.hotkeys.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.jstree.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.ui.potato.menu.js"></script>

<script type="text/javascript">
 jQuery.noConflict();
 var paApp = {
   contextPath: "${pageContext.request.contextPath}",
   imagePath: "${imagePath}",
   scriptPath: "${scriptPath}",
   staticPath: "${staticPath}",
   stylePath: "${stylePath}"
 };
 
</script>

<script type="text/javascript" src="${scriptPath}/js/prototype.js"></script>
<script type="text/javascript" src="${scriptPath}/js/coppa.js"></script>
<script type="text/javascript" src="${scriptPath}/js/subModalcommon.js"></script>
<script type="text/javascript" src="${scriptPath}/js/subModal.js"></script>

<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/disease/ml_breadcrumbs.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/disease/cookies_support.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/disease/diseasesFilter.js?5347859245'/>"></script>

<script type="text/javascript">

var preSelectDiseaseID = '<%=StringEscapeUtils.escapeJavaScript(request.getParameter("diseaseID"))%>';

(function($) {    
    $(function () {
    	if ($.isNumeric(preSelectDiseaseID)) {
    	    jQuery.diseasesFilter.showTree(preSelectDiseaseID);
    	}	
    })
}(jQuery));


function addDisease(diseaseid) {
    // first get the elements into a list
    var domelts = jQuery('#pdqDiseases option');
    // next translate that into an array of just the values
    var values = jQuery.map(domelts, function(elt, i) { return jQuery(elt).val();});
    jQuery("#returnVal").val(values);
    // If lookup close the window else add to the trial and close the window
    if(<%=request.getParameter("lookUp")%>){
        window.top.hidePopWin(true);
    }else{
	    var url = '<%=request.getContextPath()%>/protected/popupDisaddDiseases.action?selectedDiseaseIds='+values;
	    jQuery.get(url, function(data) {
	        window.top.hidePopWin(true);
	    });
    }  
}
  function cancel(){ 
      window.top.hidePopWin(true);
  }
</script>
</head>
<body>
    <h2>
        <a href="#"></a>
    </h2>
     <div id="diseasesSection">
         <input type="hidden" id="returnVal" name="returnVal" />
         <fmt:message key="diseases.widget.tableTitle" var="tableTitle" />
         <fmt:message key="diseases.widget.tableSummary" var="tableSummary" />
         
         <table class="form-table" title="${tableTitle}" summary="${tableSummary}">
             <tr>
                 <td>
                     <div class="selectDiseasewidget">
                         <div class="diseaserescol">
                             <fmt:message key="diseases.widget.searchHeader" var="searchHeader" />
                             <fmt:message key="diseases.widget.searchHeader.alt" var="alt" />
                             <h3>
                                 <img src="${imagePath}/arrow_white_down.gif" width="9" height="9" alt="${alt}" style="vertical-align: middle;" />${searchHeader}
                             </h3>
                             <div class="quickresults">
                                 <div class="dis_quickresults_header">
                                     <fmt:message key="diseases.widget.diseaseTitle" var="title" />
                                     <div class="ui-widget">
                                         <input type="text" class="hintTextbox" id="disease" name="disease" maxlength="255" size="30" title="${title}" value="${title}" />
                                     	<fmt:message key="diseases.widget.searchMagnifier.alt" var="title" />
                                     	<input type="image" src="${imagePath}/ico_magnifier.gif" alt="${title}" class="search_inner_button" />
                                     	<div class="quickresults_header_buttons right">
                                         <fmt:message key="diseases.widget.button.add.title" var="title" />
                                         <a id="add_all_disease" title="${title}" href="#"><fmt:message key="diseases.widget.button.add" /></a>
                                         <fmt:message key="diseases.widget.button.tree.title" var="title" />
                                         <a id="show_tree_disease" title="${title}" href="#">
                                         <fmt:message key="diseases.widget.button.tree" /></a>
                                         <fmt:message key="diseases.widget.button.reset.title" var="title" />
                                         <a id="reset_disease" title="${title}" href="#">
                                         <fmt:message key="diseases.widget.button.reset" /></a>
                                         <div class="clear"></div>
                                     	</div>
                                     </div>
                                     <div class="whiteline"></div>
                                     <div>
                                        <input type="checkbox" name="searchSynonym" id="searchSynonym"><label for="searchSynonym">Search Synonyms</label>&nbsp;&nbsp;&nbsp;&nbsp;
                                        <input type="checkbox" name="exactSearch" id="exactSearch"><label for="exactSearch">Exact match only</label>&nbsp;&nbsp;&nbsp;&nbsp;
                                        <span class="quickresults_count"><fmt:message key="diseases.widget.quickResultCount" /></span>
                                     </div>
                                 </div>
                                 <div class="quickresults_body">
                                     <div id="diseasebreadcrumbs">
                                         <!-- Boxes with breadcrumbs are built and inserted dynamically -->
                                     </div>
                                 </div>
                             </div>
                         </div>

                         <div class="selectionscol">
                             <fmt:message key="diseases.widget.selectionHeader" var="selectionHeader" />
                             <fmt:message key="diseases.widget.selectionHeader.alt" var="alt" />
                             <h3>
                                 <img src="${imagePath}/arrow_white_down.gif" width="9" height="9" alt="${alt}" style="vertical-align: middle;" />${selectionHeader}
                             </h3>
                             <div id="disease_selections_count" class="selections_count_normal">
                                 <fmt:message key="diseases.widget.noselection" />
                             </div>
                             <div class="clear"></div>
                             <div class="selectionslist">
                                 <ul id="selectedDiseases"  name="selectedDiseases" class="selectionslist_body">
                                 </ul>
                             </div>
                         </div>
                         <div class="clear"></div>
                     </div>
                 </td>
             </tr>
         </table>

         <div style="display: none">
             <s:select multiple="true" id="pdqDiseases" name="pdqDiseases" list="#{}" value="pdqDiseases" />
         </div>
     </div>

     <div class="actionsrow">
         <del class="btnwrapper">
             <ul class="btnrow">
                  <a href="javascript:void(0)" class="btn" onclick="addDisease()">
                      <span class="btn_img"><span class="add">Add</span></span>
                  </a>
                  <s:a onclick="javascript:cancel();" href="javascript:void(0)" cssClass="btn">
                      <span class="btn_img"><span class="cancel">Cancel</span></span>
                 </s:a>                                
             </ul>
         </del>
     </div>        
</body>
</html>
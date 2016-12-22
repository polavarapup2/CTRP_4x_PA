
<script type="text/javascript" src="${scriptPath}/js/prototype.js"></script>

<script type="text/javascript" src="${scriptPath}/js/jquery-1.10.2.min.js"></script> 
<script type='text/javascript' src="${scriptPath}/js/bootstrap.js"></script> 
<script type='text/javascript' src="${scriptPath}/js/bootstrap-datetimepicker.min.js"></script> 
<script type='text/javascript' src="${scriptPath}/js/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery-ui-1.8.23.custom.min.js"></script> 
<script type="text/javascript" src="${scriptPath}/js/select2.min.js"></script>

<script type="text/javascript">
jQuery.noConflict();
var registryApp = {
   contextPath: "${pageContext.request.contextPath}",
   imagePath: "${imagePath}",
   scriptPath: "${scriptPath}",
   staticPath: "${staticPath}",
   stylePath: "${stylePath}"
 };
 
</script>

<c:if test="${sessionScope.disclaimerAccepted}">
	<script type="text/javascript" language="javascript" src="${scriptPath}/js/subModalcommon.js"></script>
	<script type="text/javascript" language="javascript" src="${scriptPath}/js/subModal.js"></script>
	<script type="text/javascript" language="javascript" src="${scriptPath}/js/coppa.js?534785924"></script>
</c:if>

<script type="text/javascript" src="${scriptPath}/js/calendarpopup.js"></script>
<script type="text/javascript" src="${scriptPath}/js/tooltip.js"></script>

<script type="text/javascript" src="${scriptPath}/js/showhide.js"></script>
<script type="text/javascript" src="${scriptPath}/js/popup.js"></script>
<script type='text/javascript' src="${scriptPath}/js/timeout.js"></script> 

<script type="text/javascript" src="${scriptPath}/js/Help.js"></script>
<script type="text/javascript">
    Help.url = '<s:property value="@gov.nih.nci.pa.util.PaEarPropertyReader@getRegistryHelpUrl()" />';
    var contextPath = '${pageContext.request.contextPath}';
</script>
<%-- <script type="text/javascript" src="${scriptPath}/js/overlib.js"></script> --%>
<%-- <script type="text/javascript" src="${scriptPath}/js/cal2.js"></script> --%>
<script type="text/javascript" src="${scriptPath}/js/ajaxHelper.js?534785924"></script>
<!-- Javascript -->
<script type='text/javascript' src="${scriptPath}/js/css3-mediaqueries.js"></script>
<script type='text/javascript' src="${scriptPath}/js/custom.js"></script>
<script type='text/javascript' language="javascript" src="${scriptPath}/js/styleswitcher.js"></script>
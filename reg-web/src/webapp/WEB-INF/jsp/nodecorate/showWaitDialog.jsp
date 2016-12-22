<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<table align="center">
	<tr>
		<td align="center"><img src="../images/loading.gif" />
		<span class="wait_msg"><c:out
				value="${waitDialogMsg!=null?waitDialogMsg:'Submitting trial, please wait...'}"></c:out></span></td>
	</tr>
</table>


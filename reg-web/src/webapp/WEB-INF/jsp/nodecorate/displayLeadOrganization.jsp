<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
<tr>
<td><s:textfield label="Organization Name" name="selectedLeadOrg.name.part[0].value" size="30" readonly="true" cssClass="readonly" cssStyle="width:200px" /> 
</td>
<td class="value">
    <ul style="margin-top:-5px;">              
        <li style="padding-left:0">
         <a href="javascript:void(0)" class="btn" onclick="lookup4loadleadorg();" title="Opens a popup form to select Lead Organization"/>
         <span class="btn_img"><span class="organization">Look Up Org</span></span></a>
        </li>
    </ul>
</td>
</tr>
</table>
 <span class="alert-danger"> 
     <s:fielderror>
     <s:param>LeadOrgNotSelected</s:param>
    </s:fielderror>                            
  </span>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<table>
<tr>
<td><s:textfield label="poLeadPi Full Name" name="poLeadPiFullName" size="30"  readonly="true" cssClass="readonly" cssStyle="width:200px"/>
</td>
<td class="value">
    <ul style="margin-top:-5px;">              
        <li style="padding-left:0">
         <a href="javascript:void(0)" class="btn" onclick="lookup4loadleadpers();" title="Opens a popup form to select Principal Investigator"/>
         <span class="btn_img"><span class="person">Look Up Person</span></span></a>
        </li>
    </ul>
</td>
</tr>
</table>
 <span class="alert-danger"> 
     <s:fielderror>
     <s:param>LeadPINotSelected</s:param>
    </s:fielderror>                            
  </span>
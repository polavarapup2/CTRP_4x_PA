<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<table>
<tr>
<td><s:textfield label="Site Pi Full Name" name="personContactWebDTO.fullName" id="personContactWebDTO.fullName" size="30"  readonly="true" cssClass="readonly" cssStyle="width:200px"/>
</td>
<td class="value">
    <ul style="margin-top:-5px;">              
        <li style="padding-left:0">
         <a href="javascript:void(0)" class="btn" onclick="lookupperson();" title="Opens a popup form to select Principal Investigator"/>
         <span class="btn_img"><span class="person">Look Up Person</span></span></a>
        </li>
    </ul>
</td>
</tr>
<s:hidden name="personContactWebDTO.selectedPersId" id="personContactWebDTO.selectedPersId"></s:hidden>
</table>
<span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>personContactWebDTO.selectedPersId</s:param>
    </s:fielderror>                            
  </span>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
<tr>
<td>
<s:textfield label="First Name" name="(selectedLeadPrincipalInvestigator.getName().getPart()).get(1).getValue()" size="30"  readonly="true" cssStyle="width:200px"/>
<%--<input type="button" value="Look Up" onclick="lookupPerson();"/> --%>
</td><td> 
                  <ul style="margin-top:-1px;">             
                        <li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookupPerson();"/><span class="btn_img"><span class="search">Look Up</span></span></a></li>
                  </ul>
            </td>
      </tr>
</table>
<span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>LeadPINotSelected</s:param>
    </s:fielderror>                            
  </span>
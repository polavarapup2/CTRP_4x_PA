<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
<tr>
<td>
<s:textfield label="Organization Name" name="selectedLeadOrg.name.part[0].value" size="30" cssStyle="width:200px" />
<%-- <input type="button" value="Look Up" onclick="lookup4loadleadorg();"/> --%>
</td><td> 
                  <ul style="margin-top:-1px;">             
                        <li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookup4loadleadorg();"/><span class="btn_img"><span class="search">Look Up</span></span></a></li>
                  </ul>
            </td>
      </tr>
</table>
 <span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>LeadOrgNotSelected</s:param>
    </s:fielderror>                            
  </span>
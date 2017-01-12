<%@ taglib prefix="s" uri="/struts-tags"%>
<s:hidden name="gtdDTO.responsiblePersonIdentifier" id="gtdDTO.responsiblePersonIdentifier"/>
<span class="info">Select Either Personal Contact or Generic Contact</span>
<table>
<tr>
<td>
<s:textfield label="Responsible Party Name" name="gtdDTO.responsiblePersonName" id="gtdDTO.responsiblePersonName" size="30"  cssStyle="width:200px" readonly="true" cssClass="readonly"/>
</td><td> 
                  <ul style="margin-top:-1px;">             
                        <li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookup4loadresponsibleparty();"/><span class="btn_img"><span class="person">Look Up Person</span></span></a></li>
                  </ul>
                   </td>
      </tr>
</table>
<span class="formErrorMsg"> 
<s:fielderror>
<s:param>responsiblePersonName</s:param>
</s:fielderror>                            
</span>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
<tr>
<td>
<s:textfield label="Sponsor Name" name="selectedSponsor.name.part[0].value" size="30" cssStyle="width:200px" readonly="true"/> 
<%--<input type="button" value="Look Up" onclick="lookup4sponsor();"/>--%>
</td><td> 
                  <ul style="margin-top:-1px;">             
                        <li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookup4sponsor();"/><span class="btn_img"><span class="search">Look Up</span></span></a></li>
                  </ul>
            </td>
      </tr>
</table>
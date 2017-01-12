<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
<tr>
<td>
<s:textfield name="gtdDTO.sponsorName" size="30"  readonly="true"/>
<%--<input type="button" value="Look Up" onclick="lookup();"/>--%>
</td><td> 
                  <ul style="margin-top:-1px;">             
                        <li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookup();"/><span class="btn_img"><span class="search">Look Up</span></span></a></li>
                  </ul><s:hidden name="gtdDTO.sponsorIdentifier" />
                   </td>
      </tr>
</table>


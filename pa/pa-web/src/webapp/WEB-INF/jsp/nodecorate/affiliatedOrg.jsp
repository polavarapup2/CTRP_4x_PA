<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
    <tr>
      <td><s:textfield label="Organization Name" name="criteria.affiliatedOrgName" size="30"
                       cssStyle="width:200px" readonly="true" cssClass="readonly"/></td>
       <td><a href="javascript:void(0)" class="btn" onclick="lookup4loadorg();" /> <span
            class="btn_img"><span class="organization">Look Up Org</span></span></a>
      <s:hidden name="criteria.affiliatedOrgId" id="affiliatedOrgId" /></td>
    </tr>                
</table>

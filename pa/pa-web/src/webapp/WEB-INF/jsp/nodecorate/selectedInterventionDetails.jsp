<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="pagePrefix" value="nodecorate.selectedInterventionDetails."/>
<script type="text/javascript" language="javascript">

    function loadDetails(id, divName, className) {
             var url = '/pa/protected/ajaxptpTypeInterventiondisplaySelectedType.action';
             var params = {
                 className: className,
                 divName: divName,
                 id: id
             };
             var div = document.getElementById(divName);
             div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
             var aj = callAjaxPost(div, url, params);
             return false;
    }  
    
    function generate() {
        document.interventionForm.action="trialInterventionsgenerate.action";
        document.interventionForm.submit();
    }
</script>
<s:hidden name="currentAction"/>
<s:hidden name="selectedRowIdentifier"/>
<s:hidden name="interventionIdentifier"/>
<s:hidden name="selectedType"/>
<s:hidden name="displayOrder"/>
<table class="form">
    <tr>
        <td class="label">
            <label for="interventionType"><fmt:message key="${pagePrefix}type" /></label><span class="required">*</span>
        </td>
        <s:if test="%{currentAction != 'edit'}">
            <s:set name="interventionTypeValues" value="@gov.nih.nci.pa.enums.ActivitySubcategoryCode@getDisplayNames()" />
            <td class="value" colspan="2">
                <s:select id="interventionType" onchange="statusChange()" headerKey="" headerValue="--Select--" 
                          name="interventionType" value="interventionType" list="#interventionTypeValues" />
            </td>
        </s:if>
        <s:else>
            <td class="value" colspan="2">
                <s:textfield  id="interventionType" name="interventionType" readonly="true" cssClass="readonly" cssStyle="width:280px;float:left" />
            </td>    
        </s:else>
    </tr>
    <tr>
        <td scope="row" class="label">
            <label for="name"><fmt:message key="${pagePrefix}name" /></label><span class="required">*</span>
        </td>
        <td class="value" style="width: 250px">
            <s:textfield id="name" readonly="true" name="interventionName" maxlength="160" size="160" cssStyle="width:280px;float:left" cssClass="readonly"/> 
        </td>
        <td class="value">
            <s:if test="%{currentAction != 'edit'}">
                <ul style="margin-top: -6px;">
                    <li style="padding-left: 0">
                        <a href="javascript:void(0)" class="btn" onclick="lookup();" /><span class="btn_img"><span class="search"><fmt:message key="${pagePrefix}button.lookup" /></span></span></a>
                    </li>
                </ul>
            </s:if>
        </td>
    </tr>
    <tr>
        <td class="label">
            <label for="description"><fmt:message key="${pagePrefix}description" /></label>
        </td>
        <td class="value" colspan="2">
            <s:textarea id="description" name="interventionDescription" rows="20" cssStyle="width:280px;"
                maxlength="1000" cssClass="charcounter"/>
            <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>interventionDescription</s:param>
                </s:fielderror>  
            </span>            
        </td>
    </tr>
    <tr>
        <td class="label"><label for="otherNames"><fmt:message key="${pagePrefix}otherNames" /></label></td>
        <td class="value" colspan="2">
            <s:textarea id="otherNames" readonly="true" name="interventionOtherNames" rows="3" cssStyle="width:280px;float:left" cssClass="readonly"/>
        </td>
    </tr>
</table>
<s:div id="test" cssStyle="display:''">
    <s:if test="%{interventionType == 'Procedure/Surgery'}">
        <%@ include file="/WEB-INF/jsp/nodecorate/plannedProceduredetails.jsp"%> 
    </s:if>
    <s:if test="%{interventionType == 'Drug'}">
        <%@ include file="/WEB-INF/jsp/nodecorate/drugDetails.jsp"%> 
    </s:if>
    <s:if test="%{interventionType == 'Radiation'}">
        <%@ include file="/WEB-INF/jsp/nodecorate/radiationDetails.jsp"%> 
    </s:if>
</s:div>
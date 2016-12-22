<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<br/>
<pa:failureMessage/>
<pa:sucessMessage /> 
<div id="saveAndShowPersons">
    <%@ include file="/WEB-INF/jsp/nodecorate/selectedName.jsp" %>
</div>
<table class="form">
    <tr>
        <td scope="row" class="label">
            <label for="personContactWebDTO.telephone">Phone Number:</label><span class="required">**</span>
        </td>
        <td class="value" colspan="2">
            
            <s:textfield name="personContactWebDTO.telephone"  id="personContactWebDTO.telephone" maxlength="200" size="200"
            cssStyle="width: 200px" />
            <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>personContactWebDTO.telephone</s:param>
                </s:fielderror>
            </span>
        </td>
    </tr>
    <tr>
        <td scope="row" class="label">
            <label for="personContactWebDTO.email">Email Address:</label><span class="required">**</span>
        </td>
        <td class="value" colspan="2">
            <s:textfield name="personContactWebDTO.email"  id="personContactWebDTO.email" maxlength="200" size="200" 
             cssStyle="width: 200px" />
            <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>personContactWebDTO.email</s:param>
                </s:fielderror>
            </span>
        </td>
    </tr>
    <tr>
        <td scope="row" colspan="3">
            <span class="required">**</span>Enter either the contact's phone number or email address. You can provide both if the information is available.
        </td>
    </tr>
    <tr>
        <td class="label">
            <label for="personContactWebDTO.statusCode">Status:</label>
        </td>
        <td class="value" colspan="2">
            <s:textfield id="personContactWebDTO.statusCode" name="personContactWebDTO.statusCode" readonly="true" cssClass="readonly" maxlength="80" size="80" cssStyle="width: 200px"/>
        </td>
    </tr>
</table>
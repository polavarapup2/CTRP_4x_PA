<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-xs-4">
    <s:textfield label="poLeadPi Full Name" name="trialDTO.piName" id="trialDTO.piName" size="30"  readonly="true" cssClass="form-control"/>
    <span class="alert-danger" id="piIdentifierErr"> 
    <s:fielderror>
        <s:param>trialDTO.piIdentifier</s:param>
    </s:fielderror>                            
</span>
</div>
<div class="col-xs-4">
    <button type="button" class="btn btn-icon btn-default" onclick="lookup4loadleadpers();" title="Opens a popup form to select Principal Investigator"><i class="fa-user"></i>Look Up Person</button>
    <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="<fmt:message key="tooltip.pi" />"  data-placement="top" data-trigger="hover"></i>
</div>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="accordion">
	<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section2"><fmt:message key="submit.trial.otherIdentifiers"/><span class="required">*</span></a></div>
	<div id="section2" class="accordion-body in">
	<div class="container">
	<c:set var="rootConstant" value="<%=gov.nih.nci.pa.iso.util.IiConverter.STUDY_PROTOCOL_ROOT%>"/>
	<s:iterator id="trialDTO.secondaryIdentifierList" value="trialDTO.secondaryIdentifierList" status="sstats">
    <s:if test="root != rootConstant || root == null" >
    	<div class="form-group">
  			<label for="updateTrial_otherIdentifiers" class="col-xs-4 control-label"><fmt:message key="submit.trial.otherIdentifier"/></label>
  			<div class="col-xs-4">
             	<s:textfield id="updateTrial_otherIdentifiers" name="trialDTO.secondaryIdentifierList[%{#sstats.index}].extension" value="%{extension}" size="100" 
                             cssClass="%{#attr.updateOrAmendMode ? 'form-control readonly' : 'form-control'}" readonly="%{#attr.updateOrAmendMode}"/>
            </div>
        </div>
	</s:if>     
    </s:iterator>
    <div class="form-group">
        <label for="updateTrial_otherIdentifiers" class="col-xs-4 control-label"><fmt:message key="submit.trial.otherIdentifier"/></label>
        <div class="col-xs-8">
        	<div class="col-xs-12">
	    		<div class="col-xs-3">
		      		<input type="text" name="otherIdentifierOrg" id="otherIdentifierOrg" value="" class="form-control"/>
		     	</div>
		     	<div class="col-xs-3">
		      		<button onclick="addOtherIdentifier();" id="otherIdbtnid" type="button" class="btn btn-icon btn-default"><i class="fa-plus"></i>Add Other Identifier</button>
	      		</div>
      		</div>
      		<br/><br/>
      		<div id="otherIdentifierdiv" class="table-header-wrap">
      			<%@ include file="/WEB-INF/jsp/nodecorate/displayOtherIdentifiersForUpdate.jsp" %>
      		</div>
      	</div>
    </div>
    </div>
    </div>
</div>   
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="accordion">
	<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section7"><fmt:message key="update.trial.grantInfo"/><span class="required">*</span><i class="fa-question-circle help-text" id="popover" rel="popover" data-content='<fmt:message key="update.trial.grantInstructionalText"/>' data-placement="right" data-trigger="hover" data-toggle="modal" data-target=".bs-modal-lg" data-original-title="" title=""></i></a></div>
	<div id="section7" class="accordion-body in">
		<div class="container">
			<div class="table-header-wrap">
                 <table class="table table-striped table-bordered">
                 	<thead>
                         <tr>
                             <th><label for="fundingMechanismCode"><fmt:message key="update.trial.fundingMechanism"/></label> </th>
                             <th><label for="nihInstitutionCode"><fmt:message key="update.trial.instituteCode"/></label></th>
                             <th><label for="serialNumber"><fmt:message key="update.trial.serialNumber"/></label></th>
                             <th><label for="nciDivisionProgramCode"><fmt:message key="update.trial.divProgram"/></label></th>
                             <th style="display:none"><label for="fundingPercent"><fmt:message key="update.trial.fundingPercent"/></label></th>
                             <th></th>
                         </tr>
                     </thead>
                     <tbody>
                         <tr>
                             <s:set name="fundingMechanismValues" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getFundingMechanisms()" />
                             <td>
                                 <s:select headerKey="" headerValue="--Select--"
                                      name="fundingMechanismCode"
                                      list="#fundingMechanismValues"
                                      listKey="fundingMechanismCode"
                                      listValue="fundingMechanismCode"
                                      id="fundingMechanismCode"
                                      value="fundingMechanismCode"
                                      cssClass="form-control" />
                             </td>
                             <s:set name="nihInstituteCodes" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getNihInstitutes()" />
                             <td>
                                 <s:select headerKey="" headerValue="--Select--"
                                      name="nihInstitutionCode"
                                      list="#nihInstituteCodes"
                                      listKey="nihInstituteCode"
                                      listValue="nihInstituteCode"
                                      id="nihInstitutionCode"
                                      value="nihInstitutionCode"
                                      cssClass="form-control"  />
                                 <span class="alert-danger" >
                                     <s:fielderror>
                                         <s:param>nihInstitutionCode</s:param>
                                    </s:fielderror>
                                 </span>
                             </td>
                             <td>
                                 <s:textfield name="serialNumber" id="serialNumber" maxlength="200" size="50"  cssClass="form-control"  />
                                 <span class="alert-danger">
                                     <s:fielderror>
                                         <s:param>serialNumber</s:param>
                                     </s:fielderror>
                                 </span>
                             </td>
                             <s:set name="programCodes" value="@gov.nih.nci.pa.enums.NciDivisionProgramCode@getDisplayNames()" />
                             <td>
                                 <s:select headerKey="" headerValue="--Select--" name="nciDivisionProgramCode" id="nciDivisionProgramCode" list="#programCodes"  value="nciDivisionProgramCode" cssClass="form-control"/>
                                 <span class="alert-danger">
                                     <s:fielderror>
                                         <s:param>nciDivisionProgramCode</s:param>
                                     </s:fielderror>
                                 </span>
                             </td>
                             <td style="display:none">
                                 <s:textfield name="fundingPercent" id="fundingPercent" maxlength="5" size="5"  cssClass="form-control" />%
                                 <span class="alert-danger">
                                     <s:fielderror>
                                         <s:param>fundingPercent</s:param>
                                     </s:fielderror>
                                 </span>
                             </td>
                             <td>
                                 <button type="button" id="grantbtnid" class="btn btn-icon btn-default" onclick="addGrant();"><i class="fa-plus"></i>Add Grant</button></td>
                             </td>
                         </tr>
                     </tbody>
                 </table>      
			</div>
			<p/>
			<div id="grantdiv" class="table-header-wrap">
                 <%@ include file="/WEB-INF/jsp/nodecorate/updateTrialViewGrant.jsp" %>
             </div>
             <p/>
             <div id="grantadddiv" class="table-header-wrap">
                 <%@ include file="/WEB-INF/jsp/nodecorate/addGrantForUpdate.jsp" %>
             </div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:hidden name="indIdeUpdateDtosLen" id="indIdeUpdateDtosLen"/>
<div class="accordion">
	<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section9">FDA IND/IDE Information for applicable trials<span class="required">*</span></a></div>
	<div id="section9" class="accordion-body in">
		<div class="container">
			<div class="table-header-wrap">
			       <table class="data2">
			            <tr>
			                <td class="space">
			                    <%@ include file="/WEB-INF/jsp/nodecorate/updateIdeIndIndicator.jsp" %>
			                </td>
			            </tr>
			        </table>
			</div>
		</div>
	</div>
</div>
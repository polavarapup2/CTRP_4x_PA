<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:if test="existingDocuments.size > 0">
<div class="accordion">
	<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section5">Existing Trial Related Documents<span class="required">*</span></a></div>
	<div id="section5" class="accordion-body in">
		<div class="container">
			<div class="table-header-wrap">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>Document Type</th>
							<th>File Name</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="existingDocuments" var="docDto" status="stat">
							<tr>
								<td><s:property value="%{typeCode}" /></td>
								<td><a
									href="searchTrialviewDoc.action?identifier=${docDto.id}"><s:property
											value="%{fileName}" /></a></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</s:if>
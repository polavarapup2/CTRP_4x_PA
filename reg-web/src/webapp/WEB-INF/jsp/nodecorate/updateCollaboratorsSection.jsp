<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:if test="collaborators.size > 0" >
<div class="accordion">
	<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section12">Collaborators<span class="required">*</span></a></div>
	<div id="section12" class="accordion-body in">
		<div class="container">
			<div class="table-header-wrap">
			    <table class="table table-striped table-bordered">
			        	<thead>
			                <tr>
			                    <th>Collaborator</th>
			                    <th>Functional Role</th>
			                </tr>
			            </thead>
			            <tbody>
			                 <s:iterator value="collaborators" id="collaborators" status="stat" >
			                    <tr>
			                        <td>
			                            <s:hidden name="collaborators[%{#stat.index}].id" />
			                            <s:hidden name="collaborators[%{#stat.index}].name" />
			                            <s:property value="%{name}"/>
			                        </td>
			                        <s:set name="functionalCodeValues" value="@gov.nih.nci.pa.enums.StudySiteFunctionalCode@getCollaboratorDisplayNames()"/>
			                        <td>
			                            <s:hidden  name="collaborators[%{#stat.index}].functionalRole" />
			                            <s:property value="%{functionalRole}"/>
			                        </td>
			                    </tr>
			                </s:iterator>
			            </tbody>
			    </table>
			</div>
		</div>
	</div>
</div>         
</s:if>
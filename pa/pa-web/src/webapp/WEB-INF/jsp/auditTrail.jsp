<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:set name="topic" value="auditTrail" scope="request" />
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
	addCalendar("Cal1", "Select Date", "startDate", "auditTrailForm");
	addCalendar("Cal2", "Select Date", "endDate", "auditTrailForm");
	setWidth(90, 1, 15, 1);
	setFormat("mm/dd/yyyy");
</script>
<pa:studyUniqueToken/>
<div class="box">
	<table class="form">
		<s:form action="trialHistoryview.action?activeTab=auditTrail" id="auditTrailForm">
			<tr>
				<td class="label">
					<label for="auditedObject">
						<fmt:message key="auditTrail.auditedObject" />
					</label>
				</td>
				<td class="value">
					<s:set name="auditTrailOptions" value="@gov.nih.nci.pa.util.AuditTrailCode@getSortedValues()" />
					<s:select id="auditedObject" list="auditTrailOptions" name="auditTrailCode" listValue="name"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					<label for="startDate">                            
						<fmt:message key="auditTrail.startDate" />
					</label>
				</td>
				<td class="value">
					<s:textfield id="startDate" name="startDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
					<a href="javascript:showCal('Cal1')">
						<img src="<c:url value='/images/ico_calendar.gif'/>" alt="Select Date" class="calendaricon" />
					</a>
					<span class="formErrorMsg">
						<s:fielderror>
							<s:param>startDate</s:param>
						</s:fielderror>
					</span>
				</td>
			</tr>
			<tr>
				<td class="label">
					<label for="endDate">                         
						<fmt:message key="auditTrail.endDate" />
					</label>
				</td>
				<td class="value">
					<s:textfield id="endDate" name="endDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
					<a href="javascript:showCal('Cal2')">
						<img src="<c:url value='/images/ico_calendar.gif'/>" alt="Select Date" class="calendaricon" />
					</a>
					<span class="formErrorMsg">
						<s:fielderror>
							<s:param>endDate</s:param>
						</s:fielderror>
					</span>
				</td>
			</tr>
			<tr>
				<td>
					<s:a href="javascript:void(0)" cssClass="btn" onclick="$('auditTrailForm').submit();">
						<span class="btn_img" style="width:130px"><span class="confirm"><fmt:message key="auditTrail.view"/></span></span>						
					</s:a>
				</td>
			</tr>
		</s:form>
	</table>
</div>
<div class="box">
	<s:set name="auditTrail" value="auditTrail" scope="request"/>
	<c:if test="${auditTrail != null}">
		<display:table class="data" id="row" name="auditTrail" export="true" sort="list"  pagesize="20" requestURI="trialHistoryview.action?activeTab=auditTrail" 
			decorator="gov.nih.nci.pa.decorator.AuditTrailTagDecorator">
			<display:setProperty name="export.excel.filename" value="trialHistoryview.xls"/>
			<display:setProperty name="export.csv.filename" value="trialHistoryview.csv"/>
			<display:setProperty name="export.xml.filename" value="trialHistoryview.xml"/>
			<pa:displayTagProperties/>
			<display:column property="changeDate" escapeXml="true" titleKey="auditTrail.changeDate" sortable="true" headerClass="sortable" group="1" />
			<display:column property="userName" escapeXml="true" titleKey="auditTrail.userName" sortable="true" headerClass="sortable" group="2" />
			<display:column escapeXml="true" titleKey="auditTrail.dataElement" sortable="true" headerClass="sortable" >
				<fmt:message key="auditTrail.${row.attribute}" bundle="${auditTrailResources}"/>
			</display:column>
			<display:column property="formattedOldValue" escapeXml="true" titleKey="auditTrail.oldValue" sortable="true" headerClass="sortable" />
			<display:column property="formattedNewValue" escapeXml="true" titleKey="auditTrail.newValue" sortable="true" headerClass="sortable" />
			<display:column property="record.type" escapeXml="true" titleKey="auditTrail.changeType" sortable="true" headerClass="sortable" />
		</display:table>
	</c:if>
</div>
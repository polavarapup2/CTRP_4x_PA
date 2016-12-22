<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

        <div id="studyRecordChangesFormDiv" title="Add/Edit study record"
            style="display: none;">

            <img alt="Progress Indicator" height="16" width="16" id="indicatorChangeType"
                style="display: none; padding-right: 5px;"
                src="${imagePath}/loading.gif"/>

            <s:form name="studyRecordChangesForm" id="studyRecordChangesForm"
                action="resultsReportingCoverSheetaddOrEditStudyRecord.action">
                <p id="errChangeType"
                    style="color: red; font-weight: bold; font-size: 75%; display: none;"></p>
                <fieldset>   <input type="hidden" name="changeId" id="changeId" /> 
                    <label ><b>Change Type<span class="required">*</span>
                    </b></label> <input type="text" name="changeType" id="changeType"
                        class="text ui-widget-content ui-corner-all"> <label
                        for="reason"><b>Action Taken<span class="required">*</span></b></label>
                   <input type="text" name="actionTaken" id="actionTakenChangeType"
                        class="text ui-widget-content ui-corner-all">
                   <label ><b>Action Completion Date</b><span class="required">*</span></label>
                   <input type="text" id="actionCompletionDateChangeType" name ="actionCompletionDate"  maxlength="10" size="10" class="dateField" readonly="true">
                   <a href="javascript:showCal('Cal2')" align="right"> <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                                    </a> 
                </fieldset>
                    <s:hidden name="studyProtocolId" id="studyProtocolId" /> 
                    
                       
            </s:form>
        </div>
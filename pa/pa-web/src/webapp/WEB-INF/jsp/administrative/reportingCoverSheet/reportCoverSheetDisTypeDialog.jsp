<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div id="discrepancyFormDiv" title="Add/Edit a Flagged Trial"
            style="display: none;">

            <img alt="Progress Indicator" height="16" width="16" id="indicator"
                style="display: none; padding-right: 5px;"
                src="${imagePath}/loading.gif">

            <s:form name="addDiscrepancyForm" id="addDiscrepancyForm"
                action="resultsReportingCoverSheetaddOrEdit.action">
                <p id="err"
                    style="color: red; font-weight: bold; font-size: 75%; display: none;"></p>
                <fieldset>
                    <input type="hidden" name="id" id="id" /> <label ><b>Discrepancy Type<span class="required">*</span>
                    </b></label> <input type="text" name="discrepancyType" id="discrepancyType"
                        class="text ui-widget-content ui-corner-all"> <label
                        for="reason"><b>Action Taken<span class="required">*</span></b></label>
                   <input type="text" name="actionTaken" id="actionTaken"
                        class="text ui-widget-content ui-corner-all">
                   <label ><b>Action Completion Date</b><span class="required">*</span></label>
                   <input type="text" id="actionCompletionDate" name ="actionCompletionDate"  maxlength="10" size="10" class="dateField" readonly="true">
                   <a href="javascript:showCal('Cal1')" align="right"> <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                                    </a> 
                </fieldset>
                    <s:hidden name="studyProtocolId" id="studyProtocolId" /> 
                      <input type="hidden" name="oprationType" id="oprationType" /> 
                       
            </s:form>
</div>            
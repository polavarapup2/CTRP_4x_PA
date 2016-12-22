<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
        <c:url value="/report/studyProtocolpopUpStudyAlternateTitles.action" var="studyAlternateTitlesPopUpUrl"/>     
        <display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" uid="${displayTableUID}"
            name="records" requestURI="${requestURI}" export="${empty isBare}">                           
            <display:setProperty name="export.xml" value="false"/>
            <display:setProperty name="export.excel.filename" value="SearchTrialResults.xls"/>
            <display:setProperty name="export.excel.include_header" value="true"/>
            <display:setProperty name="export.csv.filename" value="SearchTrialResults.csv"/>
            <display:setProperty name="export.csv.include_header" value="true"/>
                
            <c:set scope="page" var="row" value="${pageScope[displayTableUID]}"/>
                
            <c:if test="${empty isBare}">                
	            <display:column escapeXml="true" class="title" titleKey="studyProtocol.nciIdentifier" property="nciIdentifier"
	                href="studyProtocolview.action" paramId="studyProtocolId" paramProperty="studyProtocolId" sortable="true" headerClass="sortable" media="html"/>
            </c:if>
            
            <c:if test="${isBare}">                
                <display:column escapeXml="false" class="title" titleKey="studyProtocol.nciIdentifier"
                    sortable="true" headerClass="sortable" media="html">
                        <a href="javascript:void(0);" onclick="handleTrialSelect(${row.studyProtocolId},'${row.nciIdentifier}');"><c:out value="${row.nciIdentifier}"/></a>
                </display:column>
            </c:if>
            <display:column class="title" titleKey="studyProtocol.nciIdentifier" sortable="true" headerScope="col" scope="row" media="excel csv xml">
                    <c:out value="${row.nciIdentifier}"/>
            </display:column>
            <display:column  title="Lead Organization" property="leadOrganizationName"
                                        media="excel csv xml"/>
            <display:column  title="Lead Org PO ID" property="leadOrganizationPOId"
                                        media="excel csv xml"/>                 
            <display:column escapeXml="false" titleKey="studyProtocol.processingPriority" property="processingPriority"
                sortable="true" headerClass="sortable"/>                
            <display:column escapeXml="true" titleKey="studyProtocol.ctepIdentifier" property="ctepId"
                sortable="true" headerClass="sortable"/>
            <display:column escapeXml="true" titleKey="studyProtocol.dcpIdentifier" property="dcpId"
                sortable="true" headerClass="sortable"/>
            <display:column escapeXml="false" titleKey="studyProtocol.officialTitle" maxLength= "200" sortable="true" property="officialTitle" headerClass="sortable" media="excel csv"/>              
            <display:column escapeXml="false" titleKey="studyProtocol.officialTitle" maxLength= "200" sortable="true" headerClass="sortable" media="html">
                <fmt:message key="studyAlternateTitles.text" var="title" />
                <!-- <c:out value="${row.officialTitle}"/> -->
                <c:if test="${not empty row.studyAlternateTitles}">                    
                    <a href="javascript:void(0)" title="${title}" onclick="displayStudyAlternateTitles('${row.studyProtocolId}')">(*)</a>
                </c:if>
                <c:out value="${row.officialTitle}"/>                
            </display:column>
            <display:column escapeXml="true" titleKey="studyProtocol.milestone" sortable="true" headerClass="sortable">
                <c:out value="${row.milestones.lastMilestone.milestone.code}" />
                <fmt:formatDate value="${row.milestones.lastMilestone.milestoneDate}" pattern="MM/dd/yyyy"/>
            </display:column>
            <display:column escapeXml="true" titleKey="studyProtocol.adminMilestone" sortable="true" headerClass="sortable">
                <c:out value="${row.milestones.adminMilestone.milestone.code}" />
                <fmt:formatDate value="${row.milestones.adminMilestone.milestoneDate}" pattern="MM/dd/yyyy"/>
            </display:column>
            <display:column escapeXml="true" titleKey="studyProtocol.scientificMilestone" sortable="true" headerClass="sortable">
                <c:out value="${row.milestones.scientificMilestone.milestone.code}" />
                <fmt:formatDate value="${row.milestones.scientificMilestone.milestoneDate}" pattern="MM/dd/yyyy"/>
            </display:column>
            <display:column escapeXml="true" titleKey="studyProtocol.documentWorkflowStatus" property="documentWorkflowStatusCode.code" sortable="true" headerClass="sortable"/>
            <display:column escapeXml="false" titleKey="studyProtocol.documentWorkflowStatusDate" property="documentWorkflowStatusDate" format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
            <display:column escapeXml="false" titleKey="studyProtocol.trialtype" sortable="true" headerClass="sortable">
                <c:out value="${row.studyProtocolType=='NonInterventionalStudyProtocol'?'Non-interventional':'Interventional'}"/>
            </display:column>
            <display:column titleKey="studyProtocol.trialSubType" sortable="true" headerClass="sortable">
                <c:out value="${row.studyProtocolType=='NonInterventionalStudyProtocol'?row.studySubtypeCode:''}"/>
            </display:column>
            
            <display:column escapeXml="false" titleKey="studyProtocol.recordVerificationDate" property="recordVerificationDate"  format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
            <display:column escapeXml="true" titleKey="studyProtocol.studyOnholdReasons" property="onHoldReasons"  headerClass="sortable"/>
            <display:column escapeXml="true" titleKey="studyProtocol.studyOnholdDates" property="onHoldDate" headerClass="sortable"/>
            <display:column escapeXml="true" titleKey="studyProtocol.submissionType" property="submissionTypeCode"  headerClass="sortable"/>
            <display:column escapeXml="true" titleKey="studyProtocol.studySource" property="studySource"  headerClass="sortable"/>
            
            <c:if test="${empty isBare}">
	            <display:column titleKey="studyProtocol.adminCheckOutBy"  sortable="true" headerClass="sortable" >
	                <s:if test="%{#attr.row.adminCheckout.checkoutByUsername != null}">
	                    <c:out value="${row.adminCheckout.checkoutByUsername}"/>
	                </s:if>
	            </display:column>
	            <display:column titleKey="studyProtocol.scientificCheckOutBy"  sortable="true" headerClass="sortable" >
	                <s:if test="%{#attr.row.scientificCheckout.checkoutByUsername != null}">
	                    <c:out value="${row.scientificCheckout.checkoutByUsername}"/>
	                </s:if>
	            </display:column>            
	            <c:if test="${sessionScope.isAbstractor || sessionScope.isSuAbstractor}">
	                <display:column class="title" titleKey="studyProtocol.action" href="studyProtocolview.action" property="action"
	                    paramId="studyProtocolId" paramProperty="studyProtocolId" sortable="true" headerClass="sortable" media="html"/>
	            </c:if>
	            <c:if test="${sessionScope.isSuAbstractor}">
	                <display:column class="title" title="Super User Action" sortable="true" headerClass="sortable" media="html">
	                    <s:if test="%{#attr.row.adminCheckout.checkoutBy != null}">
	                        <a href="javascript:void(0)" onclick="checkIn('adminCheckIn','${row.studyProtocolId}')">Check-In (Admin)</a><br/>
	                    </s:if>
	                    <s:if test="%{#attr.row.scientificCheckout.checkoutBy != null}">
	                        <a href="javascript:void(0)" onclick="checkIn('scientificCheckIn','${row.studyProtocolId}')">Check-In (Scientific)</a><br/>
	                    </s:if>
	                </display:column>
	            </c:if>
	            <display:column titleKey="studyProtocol.viewTSR" href="studyProtocolviewTSR.action" property="viewTSR" media="html"/>
            </c:if>
        </display:table>
        <script type="text/javascript">
            function generateTSR(id) {
                var form = document.sForm;
                form.target = "TSR";
                form.action = paApp.contextPath + "/protected/ajaxStudyProtocolviewTSR.action?studyProtocolId=" + id;
                form.submit();
            }
            
            function checkIn(action,id) {
                var form = document.sForm;
                form.target = "";
                
                var bs=new Array(64).join(' ');
                var comment=prompt(bs+"Enter check-in comment:"+bs,"");
                if (comment==null){
                    return;
                }
                form.elements["checkInReason"].value = comment.substr(0,200);
                
                form.action = paApp.contextPath + "/protected/studyProtocol" + action + ".action?studyProtocolId=" + id;
                form.submit();
            }
            
            function displayStudyAlternateTitles(studyProtocolId) {                	
            	var width = 300;
                var height = 300;
                if (Prototype.Browser.IE) {
                    width = 250;
                    height = 250;                   
                }
                showPopWin('${studyAlternateTitlesPopUpUrl}?studyProtocolId='+studyProtocolId, width, height, '', 'Trial Alternate Titles');
            }
            
            (function($) {    
                //******************
                //** On DOM Ready **
                //******************
                $(function () {
                	 // Add the Export option: CSV | Excel to the top of the search results in all Dashboards.
                    // This is not supported by Display Tag, so doing this manually.
                    $( "div.exportlinks" ).clone().insertBefore( "span.pagebanner" );
                })
            })(jQuery);
            
        </script>
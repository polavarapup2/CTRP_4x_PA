<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
  <h2><fmt:message key="reportCoverSheet.recordChanges.title" /></h2>
      <s:set name="studyRecordChagesList" value="studyRecordChagesList" scope="request"/>  
       <div id="msg" class="confirm_msg" style="display: none;">
                    <strong>Changes saved!</strong>
                </div>     
        <display:table name="${studyRecordChangeList}" id="recordChanges"
                    defaultsort="1" sort="list" pagesize="9999999"
                    requestURI="resultsReportingCoverSheetquery.action" export="false"  >
                    <display:setProperty name="basic.msg.empty_list"
                        value="No study record changes found." />
                    <display:setProperty name="basic.empty.showtable" value="false" />
                    <display:setProperty name="paging.banner.one_item_found" value="" />
                    <display:setProperty name="paging.banner.all_items_found" value="" />
                    <display:setProperty name="paging.banner.onepage" value="" />
                    <display:setProperty name="export.xml" value="false" />
                   
                    <display:column escapeXml="false" title="Change Type"  
                        property="changeType" style="width:35%"/>
                    <display:column escapeXml="false" title="Action Taken"  style="width:25%"
                        property="actionTaken" />
                    <display:column escapeXml="false" title="Action Completion Date"  style="width:20%;text-align:center"
                        property="actionCompletionDate" format="{0,date,MM/dd/yyyy}"/>
                   
                    <display:column escapeXml="false" title="Action" style="align:center;width:20%"  >
                        <div align="left">
                             <img name="edit" style="cursor: pointer;" alt="Click here to edit"
                                src="${imagePath}/ico_edit.gif" >
                                  <img name="delete" style="cursor: pointer;" alt="Click here to edit"
                                src="${imagePath}/ico_delete.gif" >
                        </div>
                    </display:column>
                  
                  <display:column escapeXml="false" media="html" property="id" 
                        class="id_holder" />
                </display:table>        
          
                 <div class="actionsrow">
                <del class="btnwrapper">
                    <ul class="btnrow">
                        <li><s:a href="javascript:void(0);" onclick="addStudyRecord();"
                                cssClass="btn" id="addStudyRecord">
                                <span class="btn_img"><span class="add">Add </span></span>
                            </s:a></li>
                       
                    </ul>
                </del>
            </div>
               <s:hidden name="studyProtocolId" id="studyProtocolId" /> 
               <s:hidden name="deleteType" id="deleteType" />
               
               
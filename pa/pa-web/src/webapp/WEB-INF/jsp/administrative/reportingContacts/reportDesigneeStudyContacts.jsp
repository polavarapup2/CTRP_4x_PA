<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
      <h3><fmt:message key="reportStudyContacts.designee.assigned.title" /></h3>
      <s:set name="studyDesigneeContactWebDtos" value="studyDesigneeContactWebDtos" scope="request"/>
       
      <display:table name="${studyDesigneeContactWebDtos}" id="dscWeb"
                    defaultsort="1" sort="list" pagesize="9999999"
                    requestURI="resultsReportingContactsquery.action" export="false"  >
                    <display:setProperty name="basic.msg.empty_list"
                        value="No designee study contacts found." />
                    <display:setProperty name="basic.empty.showtable" value="false" />
                    <display:setProperty name="paging.banner.one_item_found" value="" />
                    <display:setProperty name="paging.banner.all_items_found" value="" />
                    <display:setProperty name="paging.banner.onepage" value="" />
                    <display:setProperty name="export.xml" value="false" />
                   
                    <display:column escapeXml="false" title="Name"  
                        property="editedPrsnNm" />
                    <display:column escapeXml="false" title="Organization" 
                        property="editedOrgNm" />
                    <display:column escapeXml="false" title="PRS User Name" 
                        property="prsUserName" />
                    <display:column escapeXml="true" title="Comments" style="width:200px;"
                        property="comments" />
                     <display:column escapeXml="false" title="Email" 
                        property="email" />
                     <display:column escapeXml="false" title="Phone" 
                        property="phoneWithExt"/>
                    <display:column escapeXml="false" title="Access Revoked?" style="text-align:center">
                    ${dscWeb.statusCode == 'Suspended' ? 'Yes': 'No'}</display:column>
                    <display:column escapeXml="false" title="Actions" media="html">
                        <div align="center">
                             <img style="cursor: pointer;" alt="Click here to edit"
                                src="${imagePath}/ico_edit.gif" onclick="editDesigneeContact(${dscWeb.id});"/>
                            <img style="cursor: pointer;" alt="Click here to delete"
                                src="${imagePath}/ico_delete.gif" onclick="deleteDesigneeContact(${dscWeb.id});"/>
                        </div>
                    </display:column>
       </display:table>
        
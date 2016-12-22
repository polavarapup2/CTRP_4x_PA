<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
     <tr> 
         <td class="value" colspan="2">
            <c:if test="${fn:length(trialDTO.secondaryIdentifierAddList) > 0 ||
                fn:length(trialDTO.secondaryIdentifierList) > 0}">
                <div class="box"><h3>Secondary Identifiers </h3>
                <c:if test="${fn:length(trialDTO.secondaryIdentifierAddList) > 0}">
                         <%@ include file="/WEB-INF/jsp/nodecorate/displayOtherIdentifiersForUpdate.jsp" %>
                </c:if>
                <c:if test="${fn:length(trialDTO.secondaryIdentifierList) > 0}">
                   <%@ include file="/WEB-INF/jsp/nodecorate/displayOtherIdentifiers.jsp" %>
                </c:if>
            </div>
            </c:if>
         </td>
     </tr>
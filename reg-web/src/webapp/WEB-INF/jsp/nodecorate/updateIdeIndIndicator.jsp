<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
    
<s:if test="indIdeUpdateDtos != null && indIdeUpdateDtos.size > 0">
    <table class="table table-bordered table-stripped" id="indideTable">    
        <thead>
            <tr>
                <th>IND/IDE Types</th>
                <th>IND/IDE Number</th>
                <th>IND/IDE Grantor</th>
                <th>IND/IDE Holder Type</th>
                <th>NIH Institution, NCI Division/Program Code (if applicable)</th>
                <th>Expanded Access ?</th>
                <th>Expanded Access Type (if applicable)</th>
                <th>Exempt? (if applicable)</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <s:iterator id="indIdeUpdateDtos" value="indIdeUpdateDtos" status="indidestats">
                <tr>
                    <td style="white-space:nowrap;">     
                        <s:hidden name="indIdeUpdateDtos[%{#indidestats.index}].indIde" value="%{indIdeUpdateDtos[#indidestats.index].indIde}"/>
                        <s:property value="%{indIdeUpdateDtos[#indidestats.index].indIde}"/>                       
                    </td>
                    <td>
                        <s:hidden name="indIdeUpdateDtos[%{#indidestats.index}].number" value="%{indIdeUpdateDtos[#indidestats.index].number}"/>
                        <s:property value="%{indIdeUpdateDtos[#indidestats.index].number}"/> 
                    </td>
                    <td> 
                        <s:hidden name="indIdeUpdateDtos[%{#indidestats.index}].grantor" value="%{indIdeUpdateDtos[#indidestats.index].grantor}"/>
                        <s:property value="%{indIdeUpdateDtos[#indidestats.index].grantor}"/> 
                    </td>   
                    <td>
                        <s:hidden name="indIdeUpdateDtos[%{#indidestats.index}].holderType" value="%{indIdeUpdateDtos[#indidestats.index].holderType}"/>
                        <s:property value="%{indIdeUpdateDtos[#indidestats.index].holderType}"/>
                    </td>
                    <td>
                        <s:hidden name="indIdeUpdateDtos[%{#indidestats.index}].nihInstHolder" value="%{indIdeUpdateDtos[#indidestats.index].nihInstHolder}"/>
                        <s:hidden name="indIdeUpdateDtos[%{#indidestats.index}].nciDivProgHolder" value="%{indIdeUpdateDtos[#indidestats.index].nciDivProgHolder}"/>
                        <s:if test="%{indIdeUpdateDtos[#indidestats.index].holderType == 'NIH'}">
                            <s:property value="%{indIdeUpdateDtos[#indidestats.index].nihInstHolder}"/>
                        </s:if>
                        <s:elseif test="%{indIdeUpdateDtos[#indidestats.index].holderType == 'NCI'}">
                            <s:property value="%{indIdeUpdateDtos[#indidestats.index].nciDivProgHolder}"/>
                        </s:elseif>
                        <s:else></s:else>
                    </td>
                    <td>
                        <s:hidden name="indIdeUpdateDtos[%{#indidestats.index}].expandedAccess" value="%{indIdeUpdateDtos[#indidestats.index].expandedAccess}"/>
                        <s:property value="%{indIdeUpdateDtos[#indidestats.index].expandedAccess}"/>
                    </td>
                    <td>
                        <s:div id="show%{#indidestats.index}" cssStyle="display:''">
                            <s:hidden name="indIdeUpdateDtos[%{#indidestats.index}].expandedAccessType" value="%{indIdeUpdateDtos[#indidestats.index].expandedAccessType}"/>
                            <s:property value="%{indIdeUpdateDtos[#indidestats.index].expandedAccessType}"/> 
                        </s:div> 
                    </td> 
                    <td>
                        <s:hidden name="indIdeUpdateDtos[%{#indidestats.index}].exemptIndicator" value="%{indIdeUpdateDtos[#indidestats.index].exemptIndicator}"/>
                        <s:property value="%{indIdeUpdateDtos[#indidestats.index].exemptIndicator}"/> 
                        <s:hidden  name="indIdeUpdateDtos[%{#indidestats.index}].indIdeId" value="%{indIdeId}"/>
                    </td>
                </tr>                 
            </s:iterator>                    
        </tbody>
    </table>
</s:if>

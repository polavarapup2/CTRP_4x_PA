<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden name="currentAction"/>
<s:hidden name="disease.diseaseIdentifier"/>
<s:hidden name="disease.studyDiseaseIdentifier"/>
<table class="form">
    <tr>
        <td class="label"><s:label>Preferred Name:</s:label><span
            class="required">*</span></td>
        <td class="value" style="width: 250px">
            <s:textfield readonly="true" name="disease.preferredName" maxlength="160" size="160" 
                    cssStyle="width:280px;float:left" cssClass="readonly"/> 
        </td>
    </tr>
    <tr>
        <td class="label"><s:label>Code:</s:label></td>
        <td class="value">
            <s:textfield name="disease.code" cssStyle="width:100px;float:left" readonly="true" cssClass="readonly"/> 
        </td>
    </tr>
    <tr>
        <td class="label"><s:label>NCI Thesaurus Concept ID:</s:label></td>
        <td class="value">
            <s:textfield readonly="true" name="disease.conceptId" cssStyle="width:140px;float:left" cssClass="readonly" /> 
        </td>
    </tr>
    <tr>
        <td class="label"><s:label>Menu Display Name:</s:label></td>
        <td class="value">
            <s:textfield readonly="true" name="disease.menuDisplayName" cssStyle="width:280px;float:left" cssClass="readonly"/> 
        </td>
    </tr>
    <tr>
        <td class="label"><s:label>Parent Name:</s:label></td>
        <td class="value">
            <s:textfield readonly="true" name="disease.parentPreferredName" cssStyle="width:280px;float:left" cssClass="readonly"/> 
        </td>
    </tr>
    <tr><td/>
        <td class="value">
            <s:checkbox name="disease.ctGovXmlIndicator"/>
            <s:label cssClass="label">Include in XML?</s:label>
        </td>
    </tr>
</table>

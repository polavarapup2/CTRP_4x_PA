<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<style type="text/css">
/*Wrap error message text*/
ul.errorMessage > li > span {
    white-space:pre-wrap;
}
</style>
<s:if test="participatingSitesList.size > 0" >
<div class="accordion">
<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section11">Participating Sites<span class="required">*</span></a></div>
<div id="section11" class="accordion-body in">
<div class="container">
<div class="table-header-wrap">
	<table class="table table-striped table-bordered">
        <tr> 
            <td class="space">
                <table id="participating-sites-table"  class="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>Site</th>
                            <th>Recruitment Status</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                        <tbody>
                        <s:iterator id="participatingSitesList" value="participatingSitesList" status="psstats">
                          <s:hidden  name="participatingSitesList[%{#psstats.index}].id" value="%{id}"/>
                            <tr>
                                <td>
                                    <s:hidden name="participatingSitesList[%{#psstats.index}].name" value="%{name}"/>
                                    <s:property value="%{name}"/>
                                </td>
                                <s:set name="recruitmentStatusValues" value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()"  />
                                <td>
                                    <label><s:select headerKey="" headerValue="--Select--"
                                        name="participatingSitesList[%{#psstats.index}].recruitmentStatus" value="%{recruitmentStatus}"
                                        list="#recruitmentStatusValues" cssClass="form-control"/></label>
                                    <span class="alert-danger">
                                        <s:fielderror escape="false">
                                            <s:param>participatingsite.recStatus<s:property value="%{#psstats.index}"/></s:param>
                                        </s:fielderror>
                                    </span>
                                </td>
                                <td>
                                    <label><s:textfield  name="participatingSitesList[%{#psstats.index}].recruitmentStatusDate" value="%{recruitmentStatusDate}" cssClass="form-control"/></label>
                                    <span class="alert-danger">
                                        <s:fielderror>
                                            <s:param>participatingsite.recStatusDate<s:property value="%{#psstats.index}"/></s:param>
                                        </s:fielderror>
                                    </span>
                                </td>
                            </tr>
                        </s:iterator >
                    </tbody>
                </table>
            </td>
        </tr>
    </table>
</div>
</div>
</div>
</div>
</s:if>
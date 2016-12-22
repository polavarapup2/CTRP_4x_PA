<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="pcassignment.page.title" /></title>
        <s:head/>
        <link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css" media="all" />
        <link href="${pageContext.request.contextPath}/styles/jquery-datatables/css/buttons.dataTables.min.css" rel="stylesheet" type="text/css" media="all" />
        <style type="text/css">
            .txt-val {
                padding: 6px;
            }
            tr.cf {
                background: white !important;
                color: black !important;
            }
            a.mypgp, a.pgcrm {
                font-weight: normal !important;
            }
            div.ui-dialog-buttonset {
                float: none !important;
                text-align: center;
            }
            tr.selected > td {
                background-color: #b0bed9 !important;
            }
            div#pgcDisclaimer {
                padding-bottom: 15px;
            }
            .btn-group .dropdown-menu {
                 left: 0;
                 top:100%;
            }
            .dropdown-menu > .active > a {
                background-color: ghostwhite;
            }
            a.fpgc {
                color:white;
                padding-left: 10px;
            }
            a.pgcssa {
                padding-left: 2px;
            }
            a.pgcrm {
                white-space: nowrap;
            }
            a.pgcrm > span {
                color:#d03b39;
                padding-bottom: 3px;
            }
            div.spt {
               text-overflow: ellipsis;
               overflow: hidden;
               max-width: 36em;
               white-space: nowrap;
            }
            label.checkbox {
                text-overflow: ellipsis;
                overflow: hidden;
                max-width: 12em;
                white-space: nowrap;
            }
            li.select2-results__option {
                text-overflow: ellipsis;
                overflow: hidden;
                max-width: 12em;
                white-space: nowrap;
            }
            ul#select2-pgc-mrm-sel-results > li.select2-results__option {
                max-width: 24em;
            }
            ul#select2-pgc-madd-sel-results > li.select2-results__option {
                max-width: 24em;
            }
            ul#select2-pgc-mrpl-selone-results > li.select2-results__option {
                max-width: 24em;
            }
            ul#select2-pgc-mrpl-seltwo-results > li.select2-results__option {
                max-width: 24em;
            }

            li.select2-selection__choice > span.select2-selection__choice__remove {
                right: 3px !important;
                left: inherit !important;
                color:#d03b39 !important;
                padding-left:2px;
                float:right;
            }
            .table-responsive {
                overflow-x: visible;
                overflow-y: visible;
                border:none;
            }
            div.dataTables_filter {
                padding-top: 5px;
            }
            div.dataTables_length {
                padding-top: 5px;
                padding-left: 5px;
            }
            .scol {
                cursor: pointer;
            }
            .glyphicon {
              z-index: 20;
            }

        </style>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/dataTables.buttons.min.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jszip.min.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/buttons.html5.min.js'/>"></script>

        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/manage.programcodes.js'/>"></script>
        <script type="text/javascript" language="javascript">
            document.observe("dom:loaded", function() {
                <s:iterator var="p" value="familyDto.programCodesAsOrderedList">
                    <s:if test="active">
                        allProgramCodes.push({
                            id: <s:property value="id" /> ,
                            code: '<s:property value="programCode" />',
                            name: '<s:property value="programName" />',
                            familyId: <s:property value="familyDto.id" />,
                            familyPoId: <s:property value="familyDto.poId" />
                        });
                    </s:if>
                </s:iterator>
                pgcinit(jQuery);
            });

        </script>


    </head>
<body>
<div class="container">
<c:set var="topic" scope="request" value="manageprogramcodeassignments"/>
	<div class="row">
		<div class="col-md-12 ">
			<h1 class="heading"><span><fmt:message key="pcassignment.heading"/></span></h1>

            <div id="pgcErrors" class="alert alert-danger" style="display: none;" role="alert">
                <i class="fa fa-exclamation"></i>  <b class="error">Error</b> processing:
                <div id="pgcErrorMsg"></div>
            </div>

            <div id="pgcInfo" class="alert alert-success" style="display: none;" role="alert">
                <div id="pgcInfoMsg"></div>
            </div>

        </div>
		
		<div class="col-md-12">
            <s:form id="changeFamilyFrm" action="managePCAssignmentchangeFamily.action" cssClass="form-horizontal">
                <div class="form-group">
                    <label for="familyPoId" class="col-sm-2 control-label"><fmt:message key="programcodes.organization.family.label"/></label>

                        <s:if test="programCodeAdmin">
                            <div class="col-sm-2">
                                <s:select id="familyPoId" name="familyPoId" list="affiliatedFamilies"
                                          cssClass="form-control fn-sb" headerKey=""
                                          headerValue="--Select--" listKey="id" listValue="name"
                                          value="%{#session['family_dto'].poId}"
                                        />
                            </div>
                        </s:if>
                        <s:else>
                           <s:hidden id="familyPoId" name="familyPoId" />
                            <div class="fn-txt txt-val col-sm-6"><s:property value="familyDto.name"/></div>
                        </s:else>

                </div>
                <hr />
                <div class="form-group">
                    <label for="reportingPeriodEndDate" class="col-sm-2 control-label"><fmt:message key="programcodes.reporting.end.date.label"/></label>
                    <div class="col-sm-2">
                        <div id="datetimepicker" class="datetimepicker input-append">
                            <s:date name="reportingPeriodEndDate" var="endDate" format="MM/dd/yyyy"/>
                            <s:textfield id="reportingPeriodEndDate" name="reportingPeriodEndDate" type="text"
                                         cssClass="form-control" placeholder="mm/dd/yyyy" style="width:83px"
                                         value="%{endDate}"
                                    />

                            <span class="add-on btn-default"><i class="fa-calendar"></i></span>
                            <p class="text-success" id="date_flash" style="display:none;">Reporting period end date saved.</p>
                        </div>
                    </div>
                    <label for="reportingPeriodLength" class="col-sm-3 control-label"><fmt:message key="programcodes.reporting.period.length.label"/></label>
                    <div class="col-sm-2">
                        <s:select
                                id = "reportingPeriodLength"
                                name="reportingPeriodLength"
                                list="#{'1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7','8':'8','9':'9','10':'10',
                                '11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19',
                                '20':'20','21':'21','22':'22','23':'23','24':'24'}"
                                cssClass="form-control" style="width:51px"  value="%{reportingPeriodLength}"
                                />
                        <p class="text-success" id="length_flash" style="display:none;">Reporting period length saved.</p>
                    </div>
                </div>
                <hr />
                <div class="col-md-12">

                    <div id="trials" class="">
                        <div class="table-responsive table-wrapper">
                            <div id="row_wrapper" class="dataTables_wrapper no-footer">
                                The following trials were active during the specified reporting period:
                                <table id="trialsTbl" class="table table-striped table-bordered">
                                    <thead>
                                    <tr>
                                        <th>Trial ID(s)</th>
                                        <th>Title</th>
                                        <th>Lead Organization</th>
                                        <th>Principal Investigator</th>
                                        <th>Trial Status</th>
                                        <th>
                                            Program Code(s)
                                            <a name="fcpg-icon-loc"> </a>
                                            <s:if test="familyDto">
                                                <a id="fpgc-icon-a" class="fpgc" href="#fcpg-icon-loc"><span id="fpgc-icon" class="glyphicon glyphicon-filter"></span></a>
                                                <div id="fpgc-div" style="display:none;">
                                                    <select id="fpgc-sel" multiple="multiple">
                                                        <option id="fpgc-opt-none" value="-1" title='None' class="fpgc-nopt">None</option>
                                                        <s:iterator var="p" value="familyDto.programCodesAsOrderedList">
                                                            <s:if test="active">
                                                                <s:if test='programCodeId == id'>
                                                                    <option id="fpgc-opt-<s:property value="id" />" value="<s:property value="id" />" class="fpgc-oopt" title='<s:property value="displayName" />' selected ><s:property value="displayName" /></option>
                                                                </s:if>
                                                                <s:else>
                                                                    <option id="fpgc-opt-<s:property value="id" />" value="<s:property value="id" />" class="fpgc-oopt" title='<s:property value="displayName" />'><s:property value="displayName" /></option>
                                                                </s:else>
                                                            </s:if>
                                                        </s:iterator>
                                                    </select>

                                                </div>
                                            </s:if>
                                        </th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>


                        </div>

                        <div class="bottom no-border">

                            <div id="pgcDisclaimer" class="text-left">
                                Please select an action button to modify program code assignments for the selected trials in the table above.

                            </div>
                            <div class="btn-group">

                                <button type="button" class="btn btn-icon multi" id="assignPGCBtn" onclick="assignMultiple(jQuery);">
                                    <i class="fa fa-plus"></i>
                                    Assign
                                </button>
                                <button type="button" class="btn btn-icon multi" id="unassignPGCBtn"
                                        onclick="removeMultiple(jQuery);">
                                    <i class="fa fa-minus"></i>
                                    Unassign
                                </button>
                                <button type="button" class="btn btn-icon multi" id="replacePGCBtn"
                                        onclick="replaceMultiple(jQuery);">
                                    <i class="fa fa-exchange"></i>
                                    Replace
                                </button>
                            </div>
                        </div>


                    </div>

                   <s:hidden name="pgcFilter" id="pgcFilter"  value="%{#parameters.pgcFilter}" />

                </div>
            </s:form>
		</div>
	</div>
</div>

<div id="dialog-participation" title="Participating Sites"
     style="display: none;">
    <p>
		<span class="pgcpSite"></span> Participating sites:
    </p>
    <div>
        <div class="table-header-wrap">
            <table class="table table-bordered" id="participationTbl" width="100%">
                <thead>
                <tr>
                    <th width="50%" nowrap="nowrap">Site Name</th>
                    <th width="50%" nowrap="nowrap">Investigators</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>


<div id="pgc-madd-dialog" title="Assign Program Codes" style="display: none;">

    <div id="pgc-madd-Errors" class="alert alert-danger" style="display: none;" role="alert">
        <i class="fa fa-exclamation"></i>  <b class="error">Error</b> processing:
        <div id="pgc-madd-ErrorMsg"></div>
    </div>

    <p>
        Please select program code(s) to assign to the select trial(s):
        <div id="pgc-madd-indicator" style="display: none;">
            <img src="${pageContext.request.contextPath}/images/loading.gif" alt="Progress Indicator." width="18" height="18" />
        </div>
    </p>
    <div class="text-center" id="pgc-madd-sel-div">
        <s:if test="familyDto">
            <select id="pgc-madd-sel" multiple="multiple" style="width: 95%;"  data-placeholder="Select Program Code(s)">
                <s:iterator var="p" value="familyDto.programCodesAsOrderedList">
                    <s:if test="active">
                        <option id="pgc-madd-opt-<s:property value="id" />" value="<s:property value="programCode" />"><s:property value="displayName" /></option>
                    </s:if>
                </s:iterator>
            </select>
        </s:if>
    </div>
</div>


<div id="pgc-mrm-dialog" title="Unassign Program Codes" style="display: none;">

    <div id="pgc-mrm-Errors" class="alert alert-danger" style="display: none;" role="alert">
        <i class="fa fa-exclamation"></i>  <b class="error">Error</b> processing:
        <div id="pgc-mrm-ErrorMsg"></div>
    </div>

    <p>
        Please select program code(s) to unassign from the select trial(s):
    <div id="pgc-mrm-indicator" style="display: none;">
        <img src="${pageContext.request.contextPath}/images/loading.gif" alt="Progress Indicator." width="18" height="18" />
    </div>
    </p>
    <div class="text-center" id="pgc-mrm-sel-div">
        <s:if test="familyDto">
            <select id="pgc-mrm-sel" multiple="multiple" style="width: 95%;" data-placeholder="Select Program Code(s)">
                <s:iterator var="p" value="familyDto.programCodesAsOrderedList">
                    <s:if test="active">
                        <option id="pgc-mrm-opt-<s:property value="id" />" value="<s:property value="programCode" />"><s:property value="displayName" /></option>
                    </s:if>
                </s:iterator>
            </select>
        </s:if>
    </div>
</div>

<div id="pgc-mrm-dialog-empty"  title="Unassign Program Codes" style="display: none;">
    <p>
        Nothing to unassign.
    </p>
</div>

<div id="pgc-mrpl-dialog-empty" title="Replace Program Code Assignments" style="display: none;">
    <p>
        Nothing to replace.
    </p>
</div>

<div id="pgc-mrpl-dialog" title="Replace Program Code Assignments" style="display: none;">

    <div id="pgc-mrpl-Errors" class="alert alert-danger" style="display: none;" role="alert">
        <i class="fa fa-exclamation"></i>  <b class="error">Error</b> processing:
        <div id="pgc-mrpl-ErrorMsg"></div>
    </div>

    <p>
        Only one program code can be replaced at a time. Select the program code that you want to replace:
    <div id="pgc-mrpl-indicator" style="display: none;">
        <img src="${pageContext.request.contextPath}/images/loading.gif" alt="Progress Indicator." width="18" height="18" />
    </div>
    </p>
    <div id="pgc-mrpl-selone-div">
        <s:if test="familyDto">
            <select id="pgc-mrpl-selone" style="width: 100%;">
            </select>
        </s:if>
    </div>
    <p>Select one or more target program codes:</p>
    <div class="text-center" id="pgc-mrpl-seltwo-div">
        <s:if test="familyDto">
            <select id="pgc-mrpl-seltwo" multiple="multiple" style="width: 95%;"  data-placeholder="Select Program Code(s)">
                <s:iterator var="p" value="familyDto.programCodesAsOrderedList">
                    <s:if test="active">
                        <option value="<s:property value="programCode" />"><s:property value="displayName" /></option>
                    </s:if>
                </s:iterator>
            </select>
        </s:if>
    </div>
</div>

</body>

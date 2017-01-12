<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<s:set name="spID" scope="page" value="studyProtocolId" />
<s:set name="ssID" scope="page" value="siteDTO.id" />
<c:url value="/protected/popuplookuppersons.action"
	var="lookupPersonsUrl" />
<c:url value="/protected/searchTrialview.action?studyProtocolId=${spID}"
	var="viewTrialUrl" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<%@ include file="/WEB-INF/jsp/common/includecss.jsp"%>
<%@ include file="/WEB-INF/jsp/common/includejs.jsp"%>
<link
	href="<c:url value='/styles/jquery-datatables/css/jquery.dataTables.min.css'/>"
	rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/jquery.dataTables.min.js'/>"></script>
<c:url value="/protected/addSitepopup" var="backendUrlTemplate" />

<style type="text/css">
.fa-trash-o,.fa-edit {
	font-size: 150%;
	cursor: pointer;
	padding-right: 3px;
}

table#siteStatusHistoryTable {
    width: 100% !important;
}
#siteStatusHistoryTable td:nth-child(3) {
    white-space: pre-wrap;
}

#siteStatusHistoryTable td:nth-child(5) img {
    position: relative;
    top: -4px;
}

div.row label {
	margin-top: 5px;
}

#dialog-edit .row {
	padding-top: 5px;
}

#dialog-edit .charcounter,#dialog-delete .charcounter {
	font-size: 75%;
}

div.warning,b.warning {
	color: blue;
}

div.warning:before {
	content: "WARNING: ";
}

div.error:before {
	content: "ERROR: ";
}

div.error,b.error {
	color: red;
}

.no-border {
	border: 0px solid #DDDDDD !important;
}

.no-border-row td {
	border: 0px solid #DDDDDD !important;
}

.bottom-border-only {
	border-bottom: 1px solid #DDDDDD !important;
}

#siteStatusTable {
	margin-top: 0px !important;
	border: 0px solid #DDDDDD !important;
}

#siteStatusTable td {
	border: 0px solid #DDDDDD !important;
}
li.select2-results__option {
    text-overflow: ellipsis;
    overflow: hidden;
    max-width: 32em;
    white-space: nowrap;
}
li.select2-selection__choice > span.select2-selection__choice__remove {
    right: 3px !important;
    left: inherit !important;
    color:#d03b39 !important;
    padding-left:2px;
    float:right;
}
</style>

<script type="text/javascript" language="javascript">

    var backendUrlTemplate = '${backendUrlTemplate}';
    var deleteImg = '${pageContext.request.contextPath}/images/ico_delete.gif';
    var runValidationsOnInitialLoad = new Boolean('${not empty ssID}').valueOf();
    var ts = 0;
	
	function addSite() {
		$('addSiteForm').submit();
		<c:url value="/protected/addSitepopupshowWaitDialog.action" var="reviewProtocol"/>
		showPopWin('${reviewProtocol}', 600, 200, '', '${not empty ssID?'Update Participating Site':'Add Participating Site'}');
	}

	function doSave() {
		addSite();
	}
	
	function lookupSiteInvestigator() {
		showPopWin('${lookupPersonsUrl}', getViewportWidth()*0.90, getViewportHeight()*0.85, null, 'Persons');        
    }	
	
    function setpersid(id, name) {
    	name = name.replace(/&apos;/g,"'");
        $('investigator.id').value = id;
        $('investigator').value = name;
        
    }
    
    (function($) {
        $(function() {

            // enable multi-select on program-code-ids
            $("#programCode").select2({
                templateResult : function(pg){
                  return pg.title;
                }
            });
            //to handle select2 delete open issue
            $(".select2-hidden-accessible").on("select2:unselect",function (e) {
                ts = e.timeStamp;
            }).on("select2:opening", function (e) {
                if (e.timeStamp - ts < 100) {
                    e.preventDefault();
                }
            });

            var table = $('#siteStatusHistoryTable')
                    .DataTable(
                            {

                                bFilter : false,
                                "bSort" : false,
                                "columns" : [ {
                                    "data" : "statusDate"
                                }, {
                                    "data" : "statusCode"
                                }, {
                                    "data" : "comments"
                                }, {
                                    "data" : "validationErrors"
                                }, {
                                    "data" : "actions"
                                } ],
                                "columnDefs" : [ {
                                    "targets" : 4,
                                    "render" : function(data, type, r, meta) {
                                        var content = '<i class="fa fa-edit"></i><i class="fa fa-trash-o"></i>';
                                        return content;
                                    }
                                } ],
                                "ajax" : {
                                    "url" : backendUrlTemplate
                                            + "getStatusHistory.action",
                                    "type" : "POST"
                                },
                                "initComplete": function(settings, json) {
                                	if (runValidationsOnInitialLoad) {
                                   	 $('#runValidations')
                                        .val('true');
                                   	   runValidationsOnInitialLoad = false;
                                   	   table.ajax.reload();
                                   }
                                }
                            }).on('draw', function() {
                        $('#runValidations').val('');
                        if (table.data().length == 0) {
                            $('#siteStatusHistoryContainer').hide();
                        } else {
                            $('#siteStatusHistoryContainer').show();
                            $('img[rel=popover]').popover();
                        }
                    }).on('preXhr', function(e, settings, data) {
                        $('#indicator').show();
                        data.runValidations = $('#runValidations').val();
                    }).on('xhr', function() {
                        $('#indicator').hide();
                    });

            $('#siteStatusHistoryTable tbody')
                    .on(
                            'click',
                            '.fa-trash-o',
                            function() {
                                var uuid = table.row($(this).parents('tr'))
                                        .data().DT_RowId;
                                $('#deleteComment').val('');
                                $('#uuid').val(uuid);
                                $("#dialog-delete").dialog('open');
                            });

            $('#siteStatusHistoryTable tbody')
                    .on(
                            'click',
                            '.fa-edit',
                            function() {
                                var uuid = table.row($(this).parents('tr'))
                                        .data().DT_RowId;
                                var statusDate = table.row(
                                        $(this).parents('tr')).data().statusDate;
                                var statusCode = table.row(
                                        $(this).parents('tr')).data().statusCode;                              
                                $("#dialog-edit").dialog('open');
                                $('#uuid').val(uuid);
                                $('#statusDate').val(statusDate);
                                $('#statusCode').val(statusCode);                                
                                $('#editComment').val('');
                            });

            $("#dialog-edit")
                    .dialog(
                            {
                                modal : true,
                                autoOpen : false,
                                width : 460,
                                buttons : {
                                    "Save" : function() {
                                        if ($('#statusDate').val() == '') {
                                            alert('Please provide a valid status date.');
                                            return;
                                        }
                                        if ($.trim($('#editComment').val()) == '') {
                                            alert('Please enter a comment explaining why you are editing the status.');
                                            return;
                                        }                                 
                                        $(this).dialog("close");
                                        $('#indicator').show();
                                        $
                                                .ajax(
                                                        {
                                                            type : "POST",
                                                            url : backendUrlTemplate
                                                                    + "editStatus.action",
                                                            data : {
                                                                statusDate : $(
                                                                        '#statusDate')
                                                                        .val(),                                                               
                                                                statusCode : $(
                                                                        '#statusCode')
                                                                        .val(),
                                                                comment : $(
                                                                        '#editComment')
                                                                        .val(),
                                                                uuid : $(
                                                                        '#uuid')
                                                                        .val()
                                                            },
                                                            timeout : 30000
                                                        })
                                                .always(function() {
                                                    $('#indicator').hide();
                                                })
                                                .done(
                                                        function() {
                                                            $('#runValidations')
                                                                    .val('true');
                                                            table.ajax.reload();
                                                        })
                                                .fail(
                                                        function(jqXHR,
                                                                textStatus,
                                                                errorThrown) {
                                                            alert(jqXHR
                                                                    .getResponseHeader('msg'));
                                                        });

                                    },
                                    "Cancel" : function() {
                                        $(this).dialog("close");
                                    }
                                }
                            });

            $("#dialog-delete")
                    .dialog(
                            {
                                modal : true,
                                autoOpen : false,
                                buttons : {
                                    "Delete" : function() {
                                        if ($.trim($('#deleteComment').val()) != '') {
                                            $(this).dialog("close");
                                            $('#indicator').show();
                                            $
                                                    .ajax(
                                                            {
                                                                type : "POST",
                                                                url : backendUrlTemplate
                                                                        + "deleteStatus.action",
                                                                data : {
                                                                    comment : $(
                                                                            '#deleteComment')
                                                                            .val(),
                                                                    uuid : $(
                                                                            '#uuid')
                                                                            .val()
                                                                },
                                                                timeout : 30000
                                                            })
                                                    .always(function() {
                                                        $('#indicator').hide();
                                                    })
                                                    .done(
                                                            function() {
                                                                $(
                                                                        '#runValidations')
                                                                        .val(
                                                                                'true');
                                                                table.ajax
                                                                        .reload();
                                                            })
                                                    .fail(
                                                            function(jqXHR,
                                                                    textStatus,
                                                                    errorThrown) {
                                                                alert(jqXHR
                                                                        .getResponseHeader('msg'));
                                                            });

                                        }
                                    },
                                    "Cancel" : function() {
                                        $(this).dialog("close");
                                    }
                                }
                            });

            $('#addStatusBtn')
                    .bind(
                            'click',
                            function(e) {
                                var statusDate = $('#siteDTO_recruitmentStatusDate').val();
                                var statusCode = $('#siteDTO_recruitmentStatus')
                                        .val();                                

                                if (statusDate == '') {
                                    alert('Please provide a valid status date.');
                                    return;
                                } else if (statusCode == '') {
                                    alert('Please provide a status code.');
                                    return;
                                }
                                $('#indicator').show();
                                $
                                        .ajax(
                                                {
                                                    type : "POST",
                                                    url : backendUrlTemplate
                                                            + "addStatus.action",
                                                    data : {
                                                        statusDate : statusDate,
                                                        statusCode : statusCode
                                 
                                                    },
                                                    timeout : 30000
                                                })
                                        .always(function() {
                                            $('#indicator').hide();
                                        })
                                        .done(function() {
                                            $('#siteDTO_recruitmentStatusDate').val('');
                                            $('#siteDTO_recruitmentStatus').val('');                                            
                                            $('#runValidations').val('true');
                                            table.ajax.reload();
                                        })
                                        .fail(
                                                function(jqXHR, textStatus,
                                                        errorThrown) {
                                                    alert(jqXHR
                                                            .getResponseHeader('msg'));
                                                });

                            });

            var reviewBtnClickHandler = $('button.review')[0].onclick;
            $('button.review')[0].onclick = null;
            $('button.review')
                    .bind(
                            'click',
                            function(e) {
                                $("#transitionErrors").hide();
                                $("#transitionErrorsWarnings").hide();
                                var ind = $('button.review')
                                        .after(
                                                "<img class='progress_ind' src='${pageContext.request.contextPath}/images/loading.gif' width=18 height=18 />");
                                $
                                        .ajax(
                                                {
                                                    type : "POST",
                                                    dataType : "json",
                                                    url : backendUrlTemplate
                                                            + "getValidationSummary.action",
                                                    timeout : 30000
                                                })
                                        .always(function() {
                                            $('button.review + img').remove();
                                        })
                                        .done(
                                                function(data, textStatus,
                                                        jqXHR) {
                                                    if (data.errors == true) {
                                                        var divId = "#transitionErrors"
                                                                + (data.warnings == true ? "Warnings"
                                                                        : "");
                                                        $(divId).show();
                                                        var offset = $(divId)
                                                                .offset();
                                                        offset.left -= 80;
                                                        offset.top -= 80;
                                                        $('html, body')
                                                                .animate(
                                                                        {
                                                                            scrollTop : offset.top,
                                                                            scrollLeft : offset.left
                                                                        });
                                                        $('#runValidations')
                                                                .val('true');
                                                        table.ajax.reload();
                                                    } else {
                                                        reviewBtnClickHandler(e);
                                                    }
                                                })
                                        .fail(
                                                function(jqXHR, textStatus,
                                                        errorThrown) {
                                                    alert(jqXHR
                                                            .getResponseHeader('msg'));
                                                });

                            });
            

        });
    })(jQuery);

	
</script>

<s:if test="redirectToSummary">
	<script>
     window.top.location = "${viewTrialUrl}";
    </script>
</s:if>

</head>
<body class="addsite">
	<s:if test="redirectToSummary==false">
		<reg-web:failureMessage />
		<reg-web:sucessMessage />
		<reg-web:actionErrorsAndMessages />
		<div class="modal-body">
			<s:form name="addSiteForm" id="addSiteForm" action="addSitepopupsave"
				cssClass="form-horizontal" role="form">
				<s:token />
				<s:hidden name="studyProtocolId" />
				<s:hidden name="siteDTO.id" />
				<s:hidden name="pickedSiteOrgPoId" />
				<s:hidden name="addSitesMultiple" />
				<div class="container-fluid">
					<div class="form-group row">
						<label class="col-xs-4  control-label"> <fmt:message
								key="add.site.trial.nciIdentifier" /></label>
						<div class="col-xs-4">
							<c:out value="${sessionScope.NCI_ID}" />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-xs-4  control-label"> <fmt:message
								key="add.site.trial.localStudyProtocolIdentifier" /><span
							class="required">*</span></label>
						<div class="col-xs-4">
							<c:out value="${sessionScope.LEAD_ORG_ID}" />
						</div>
					</div>
					<div class="form-group row">
						<fmt:message key="studyAlternateTitles.text" var="title" />
						<label class="col-xs-4  control-label"> <fmt:message
								key="add.site.trial.officialTitle" /></label>
						<div class="col-xs-4">
							<c:out value="${sessionScope.TITLE}" />
							<c:if test="${not empty trialSummary.studyAlternateTitles}">
								<a href="javascript:void(0)" title="${title}"
									onclick="displayStudyAlternateTitles('${trialSummary.identifier.extension}')">(*)</a>
							</c:if>
						</div>
					</div>
					<div class="form-group row">
						<label class="col-xs-4 control-label" for="organizationName">
							<fmt:message key="add.site.orgName" /><span class="required">*</span>
						</label>
						<div class="col-xs-4">
							<s:textfield id="organizationName" name="siteDTO.name"
								readonly="true" cssClass="form-control readonly" />
							<span class="alert-danger"> <s:fielderror>
									<s:param>organizationName</s:param>
								</s:fielderror>
							</span>
						</div>
					</div>
					<div class="form-group row">
						<label class="col-xs-4 control-label" for="localIdentifier">
							<fmt:message key="add.site.localID" /><span class="required">*</span>
						</label>
						<div class="col-xs-4">
							<s:textfield id="localIdentifier"
								name="siteDTO.siteLocalTrialIdentifier" cssClass="form-control" />
							<span class="alert-danger"> <s:fielderror>
									<s:param>localIdentifier</s:param>
								</s:fielderror>
							</span>
						</div>
					</div>
					<div class="form-group row">
						<label class="col-xs-4 control-label" for="investigator">
							<fmt:message key="add.site.investigator" /><span
							class="required">*</span>
						</label>
						<div class="col-xs-4">
							<s:textfield id="investigator" name="siteDTO.investigator"
								readonly="true" cssClass="form-control readonly" />
							<span class="alert-danger"> <s:fielderror>
									<s:param>investigator</s:param>
								</s:fielderror>
							</span>
						</div>
						<div class="col-xs-2">
							<button type="button" class="btn btn-icon btn-default"
								onclick="lookupSiteInvestigator();">
								<i class="fa-search"></i>Look Up
							</button>
						</div>
						<s:hidden id="investigator.id" name="siteDTO.investigatorId" />
					</div>
                    <s:if test="#session.CANCER_TRIAL">
					<div class="form-group row">
						<label class="col-xs-4 control-label" for="programCode"> <fmt:message key="add.site.programCode" /></label>
						<div class="col-xs-4">
                            <select id="programCode" name="programCode" multiple="multiple"
                                    class="form-control" data-placeholder="Select Program Code(s)" style="width:100%;">
                                <c:forEach var="pgc" items="${sessionScope['PGC_MASTER_LIST']}">
                                    <c:if test="${pgc.active}">
                                        <option value="${pgc.id}" ${fn:contains(sessionScope['PGC_ID_LIST'],pgc.id )? 'selected' : ''} title="${pgc.displayName}">${pgc.programCode}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <c:if test="${sessionScope['isSiteAdmin']}">
                                <a id="programCodesMasterList" href="javascript:void(0)" onclick="window.parent.submitXsrfForm('${pageContext.request.contextPath}/siteadmin/programCodesexecute.action?selectedDTOId=${sessionScope['FAMILY_ID']}');">Manage Program Codes</a>
                            </c:if>
							<span class="alert-danger"> <s:fielderror>
									<s:param>programCode</s:param>
								</s:fielderror>
							</span>
						</div>
                        <span class="col-xs-4">
                               <fmt:message key="add.site.programCode.cancer.tooltip" />
                        </span>
					</div>
                    </s:if>
					<div class="row">
						<div class="col-xs-2">&nbsp;</div>
						<div class="col-xs-8">
							<div class="table-header-wrap">
								<table class="table no-border" id="siteStatusTable">
									<tbody>
										<tr>
											<td nowrap="nowrap"><label
												for="siteDTO_recruitmentStatusDate"><fmt:message
														key="add.site.statusDate" /><span class="required">*</span></label></td>
											<td nowrap="nowrap"><label
												for="siteDTO_recruitmentStatus"><fmt:message
														key="add.site.statusCode" /><span class="required">*</span></label></td>
											<td>&nbsp;</td>
										</tr>


										<tr>
											<td><div class="datetimepicker input-append">
													<s:textfield id="siteDTO_recruitmentStatusDate"
														name="siteDTO.recruitmentStatusDate" maxlength="10"
														size="10" cssClass="form-control" data-format="MM/dd/yyyy"
														value=""
														placeholder="mm/dd/yyyy" />
													<span class="add-on btn-default"><i
														class="fa-calendar"></i></span>
												</div> <span class="alert-danger"> <s:fielderror>
														<s:param>statusDate</s:param>
													</s:fielderror>
											</span></td>
											<td><s:set name="statusCodeValues"
													value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()" />
												<s:select headerKey="" headerValue="--Select--"
													id="siteDTO_recruitmentStatus"
													name="siteDTO.recruitmentStatus" list="#statusCodeValues"
													value="" cssClass="form-control" />
												<span class="alert-danger"> <s:fielderror>
														<s:param>statusCode</s:param>
													</s:fielderror>
											</span></td>
											<td><button type="button" id="addStatusBtn"
													class="btn btn-icon btn-default">
													<i class="fa-plus"></i>Add Status
												</button></td>
										</tr>
										<tr>
											<td><span class="info">Please refer to the <a
													href="https://wiki.nci.nih.gov/x/zYLpDw" target="newPage">Site
														Status Transition Rules</a>.
											</span></td>
											<td colspan="2"><span class="info">Note: Site
													Recruitment Status of Active or Enrolling by Invitation
													indicates that the Site is Open for Patient Accruals.
													Closed to Accrual Site Status indicates that the Site is
													Closed to Patient Accruals. </span></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="row">
						<div style="height: 16px;" align="center">
							<img id="indicator" style="display: none;"
								src="${pageContext.request.contextPath}/images/loading.gif"
								alt="Progress Indicator." width="16" height="16" />
						</div>
					</div>
					<div class="row">
						<div class="col-xs-2">&nbsp;</div>
						<div class="col-xs-8">
							<div id="siteStatusHistoryContainer">
								<div>
									<h6>Site Recruitment Status History</h6>
								</div>
								<div id="transitionErrors" class="alert alert-danger"
									style="display: none;">
									<i class="fa fa-exclamation"></i> Status Transition <b
										class="error">Errors</b> were found. This site cannot be saved
									until all Status Transition Errors have been resolved. Please
									use the action icons below to make corrections.
								</div>
								<div id="transitionErrorsWarnings" class="alert alert-danger"
									style="display: none;">
									<i class="fa fa-exclamation"></i> Status Transition <b
										class="error">Errors</b> and <b class="warning">Warnings</b>
									were found. This site cannot be saved until all Status
									Transition Errors have been resolved. Please use the action
									icons below to make corrections.
								</div>

								<div class="table-header-wrap">
									<table class="table table-bordered"
										id="siteStatusHistoryTable">
										<thead>
											<tr>
												<th nowrap="nowrap">Status Date</th>
												<th nowrap="nowrap">Status</th>
												<th nowrap="nowrap">Comments</th>
												<th nowrap="nowrap">Validation Messages</th>
												<th nowrap="nowrap">Actions</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<reg-web:saveAndCloseBtn />
				</div>
			</s:form>
		</div>
		<div id="dialog-delete" title="Please provide a comment"
			style="display: none;">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin: 0 7px 20px 0;"></span>Please provide a
				comment explaining why you are deleting this recruitment status:
			</p>
			<div>
				<s:textarea id="deleteComment" name="deleteComment" rows="2"
					maxlength="1000" cssClass="form-control charcounter"
					cssStyle="width: 100%;" />
			</div>
			<s:hidden name="uuid" id="uuid" />
			<s:hidden name="runValidations" id="runValidations" />
		</div>
		<div id="dialog-edit" title="Edit Site Recruitment Status" style="display: none;">
			<div class="container-fluid">
				<div class="row">
					<div class="col-xs-4" align="right">
						<label for="statusDate">Status Date:<span class="required">*</span></label>
					</div>
					<div class="col-xs-8">
						<div id="datetimepicker" class="datetimepicker input-append">
							<s:textfield id="statusDate" name="statusDate"
								data-format="MM/dd/yyyy" type="text" cssClass="form-control"
								placeholder="mm/dd/yyyy" />
							<span class="add-on btn-default"><i class="fa-calendar"></i></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-4" align="right">
						<label for="statusCode">Status:<span class="required">*</span></label>
					</div>
					<div class="col-xs-8">
						<s:select id="statusCode" name="statusCode"
							list="#statusCodeValues" cssClass="form-control" />
					</div>
				</div>				
				<div class="row">
					<div class="col-xs-4"></div>
					<div class="col-xs-8">
						<em style="font-size: 75%">Administratively Complete,
							Withdrawn and Temporarily Closed statuses only </em>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-4" align="right">
						<label for="editComment">Comment:<span class="required">*</span></label>
					</div>
					<div class="col-xs-8">
						<s:textarea id="editComment" name="editComment" rows="2"
							maxlength="1000" cssClass="form-control charcounter"
							cssStyle="width: 100%;" />
					</div>
				</div>
			</div>
		</div>
	</s:if>
</body>
</html>
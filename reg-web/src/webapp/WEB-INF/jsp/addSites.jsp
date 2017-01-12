<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="addSites.title" /></title>
<s:head />
<c:url value="/protected/addSites" var="backendUrlTemplate"/>  
<c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl" />
<c:url value="/protected/addSitesvalidateSiteData.action"
    var="validateUrl" />

<style type="text/css">

.fa-trash-o,.fa-edit {
    font-size: 150%;
    cursor: pointer;
    padding-right: 3px;
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

.popover {
    max-width: 150px !important;
    font-size: 80%;
    z-index: auto;
}

.status-history td {
    border: 1px solid #DDDDDD !important;
}

#dialog-edit .row {
    padding-top: 5px;
}

li.select2-results__option {
    text-overflow: ellipsis;
    overflow: hidden;
    max-width: 35em;
    white-space: nowrap;
}
td.pi > span {
    border: 1px solid #ccc;
    border-radius: 4px;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
    width: 100%;
    display: inline-block;
    height: 34px;
}

table.sr-table > tbody > tr > td {
    padding: 2px;
}

li.select2-selection__choice > span.select2-selection__choice__remove {
    right: 3px !important;
    left: inherit !important;
    color:#d03b39 !important;
    padding-left:2px;
    float:right;
}

th#th3 {
    padding-right: 20px;
}

</style>

<script type="text/javascript" language="javascript"
    src="<c:url value='/scripts/js/ajaxHelper.js?534785924'/>"></script>

<script type="text/javascript" language="javascript"
    src="<c:url value='/scripts/js/jquery.dataTables.min.js?534785924'/>"></script>

<script type="text/javascript" language="javascript">
    var backendUrlTemplate = '${backendUrlTemplate}';
    var deleteImg = '${pageContext.request.contextPath}/images/ico_delete.gif';
    var ts = 0;
    function resetSearch() {
        $('identifier').value = '';
        $('officialTitle').value = '';
    }
    
    function cancel() {       
        $('addSitesForm').action = 'addSites.action';
        $('addSitesForm').submit();
    }

    function search() {
        displayWaitPanel();
        $('addSitesForm').action = 'addSitessearch.action';
        $('addSitesForm').submit();
    }
    
    function beginAddingSites(spID) {
        disableSorting();
        $('plussign_'+spID).hide();
        $('saveCancelDiv').show();
        if ($('s2div_' + spID)!=null) {
            $('s2div_' + spID).show();  
        }
        prepareSiteEntryRow(spID);
        prepareSiteInformationFormControls(spID);
        showPopover(spID);
    }
    
    var popoverShownOnce = false;
    function showPopover(spID) {
        if (!popoverShownOnce) {
              popoverShownOnce = true;           
              jQuery("#trial_"+spID+"_info").popover({
                  'animation' : true,
                  'trigger': 'manual' 
              });
              jQuery("#trial_"+spID+"_info").popover('show');
        }
    }
    
    function prepareSiteInformationFormControls(spID) {
        var div = $('addSitesDiv_'+spID);
        var col = $('addSitesCol_'+spID);
        col.appendChild(div.parentNode.removeChild(div));   
        jQuery(div).show("blind");
    }
    
    function prepareSiteEntryRow(spID) {
        var trialRow = $('trialrow_'+spID);
        trialRow.addClassName('no-border-row');
        
        var addSiteRow = document.createElement('tr');
        addSiteRow.setAttribute('id','addSitesRow_'+spID);
        addSiteRow.setAttribute('class',trialRow.getAttribute('class'));
        $(addSiteRow).addClassName('bottom-border-only');
        
        var addSiteCol = document.createElement('td');
        addSiteCol.setAttribute('id','addSitesCol_'+spID);
        addSiteCol.setAttribute('colspan','3');
        $(addSiteCol).addClassName('no-border-row');
        addSiteRow.appendChild(addSiteCol);
        trialRow.parentNode.insertBefore(addSiteRow, trialRow.nextSibling);
    }
    
    var persid;
    var chosenname;
    function setpersid(persIdentifier, sname, email, phone) {
        persid = persIdentifier;
        chosenname = sname.replace(/&apos;/g,"'").replace(/,/g,", ");
    }
    function lookupPI(spID, index) {
         showPopup('${lookupPersUrl}', function () {
             if (persid!=null) {
                 $("trial_"+spID+"_site_"+index+"_pi_poid").value = persid;
                 $("trial_"+spID+"_site_"+index+"_pi_name").innerHTML = chosenname;     
                 persid = null;
                 chosenname = null;
             }
         }, 'Select Principal Investigator');       
    }
    
    function addAnotherSiteRow(spID, index) {
        $('trial_'+spID+'_site_'+index+'_btnrow').hide();
        index++;
        
        jQuery('#trial_'+spID+'_site_'+index+'_row').show('highlight');
        $('trial_'+spID+'_site_'+index+'_stathistrow').show();
        $('trial_'+spID+'_site_'+index+'_header').show();
        if ($('trial_'+spID+'_site_'+index+'_btnrow')!=null) {
            $('trial_'+spID+'_site_'+index+'_btnrow').show();
        }
        
        preSelectSiteOrgToBeDifferentFromPreviousRows(spID, index);
        
        
    }
    
    function preSelectSiteOrgToBeDifferentFromPreviousRows(spID, index) {
        var orgDD = $('trial_'+spID+'_site_'+index+'_org_poid');
        for (var i = 0; i < orgDD.length; i++) {
            var poOrgID = orgDD.options[i].value;
            if (isPoOrgIdNotSelectedInPreviousRows(poOrgID, spID, index)) {
                orgDD.setValue(poOrgID);
                return;
            }
        }       
    }
    
    function isPoOrgIdNotSelectedInPreviousRows(poOrgID, spID, index) {
        for (var i = index-1; i >= 0; i--) {
             if ($F('trial_'+spID+'_site_'+i+'_org_poid')==poOrgID) {
                 return false;
             }
        }
        return true;
    }
    
    function copySiteDataFromRowAbove(spID, index) {
        if (index <= 0) {
            return;
        }
        $('trial_'+spID+'_site_'+index+'_pi_name').innerHTML = $('trial_'+spID+'_site_'+(index-1)+'_pi_name').innerHTML;
        $('trial_'+spID+'_site_'+index+'_pi_poid').setValue($('trial_'+spID+'_site_'+(index-1)+'_pi_poid').getValue());
        $('trial_'+spID+'_site_'+index+'_localID').setValue($('trial_'+spID+'_site_'+(index-1)+'_localID').getValue());
        $('trial_'+spID+'_site_'+index+'_status').setValue($('trial_'+spID+'_site_'+(index-1)+'_status').getValue());
        $('trial_'+spID+'_site_'+index+'_statusDate').setValue($('trial_'+spID+'_site_'+(index-1)+'_statusDate').getValue());
        
        (function($) {
             if ($.fn.DataTable.isDataTable( '#trial_'+spID+'_site_'+(index-1)+'_trialStatusHistoryTable' ) ) {
                 var copyFromTable = $('#trial_'+spID+'_site_'+(index-1)+'_trialStatusHistoryTable').DataTable();
                 var queue = copyFromTable.data().toArray().clone();
                 if (queue.length > 0) {
                        $
                        .ajax(
                                {
                                    type : "POST",
                                    url : backendUrlTemplate
                                            + "clearStatusHistory.action",
                                    data : {                                       
                                        discriminator : 'trial_'+spID+'_site_'+index+'.statusHistory.'
                                    },
                                    timeout : 30000
                                })
                        .done(function() {
                             var currentStatus = queue[0];
                             $('#trial_'+spID+'_site_'+index+'_status').val(currentStatus.statusCode);
                             $('#trial_'+spID+'_site_'+index+'_statusDate').val(currentStatus.statusDate);
                             $( document ).ajaxStop(function() {
                                 queue.shift();
                                 if (queue.length > 0) {
                                     currentStatus = queue[0];
                                     $('#trial_'+spID+'_site_'+index+'_status').val(currentStatus.statusCode);
                                     $('#trial_'+spID+'_site_'+index+'_statusDate').val(currentStatus.statusDate);
                                     addSiteStatus(spID, index);
                                 }
                             });
                             addSiteStatus(spID, index);
                        });
                 }
             }
        })(jQuery);   
    }
    
    function addSiteStatus(spID, index) {
        (function($) {
            var statusCode = $('#trial_'+spID+'_site_'+index+'_status').val();
            var statusDate = $('#trial_'+spID+'_site_'+index+'_statusDate').val();
            if (statusDate == '') {
                alert('Please provide a valid status date.');
                return;
            } else if (statusCode == '') {
                alert('Please provide a status code.');
                return;
            }
            
            $('#trial_'+spID+'_site_'+index+'_statusHistoryDiv').show(); 
            if (!$.fn.DataTable.isDataTable( '#trial_'+spID+'_site_'+index+'_trialStatusHistoryTable' ) ) {
                   $('#trial_'+spID+'_site_'+index+'_trialStatusHistoryTable').DataTable({
                       "bPaginate" : false,
                       "bLengthChange" : false,
                       "bFilter" : false,
                       "bSort" : false,
                       "bInfo" : false,
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
                               var content = '<i class="fa fa-edit" title="Edit '+r.statusCode+' recruitment status" data-toggle="tooltip" ></i><i title="Delete '+r.statusCode+' recruitment status" data-toggle="tooltip" class="fa fa-trash-o"></i>';                               
                               return content;
                           }
                       } ],
                       "ajax" : {
                           "url" : backendUrlTemplate
                                   + "getStatusHistory.action",
                           "type" : "POST",
                           "data": function (d) {
                               d.discriminator = 'trial_'+spID+'_site_'+index+'.statusHistory.';
                               return d;
                           }
                       }
                   }).on('draw', function() {
                       $('#runValidations').val('');
                       $('[data-toggle="tooltip"]').tooltip({
                           'placement' : 'top'
                       });
                   }).on('preXhr', function(e, settings, data) {
                       $('#trial_'+spID+'_site_'+index+'_indicator').show();
                       data.runValidations = $('#runValidations').val();
                   }).on('xhr', function() {
                       $('#trial_'+spID+'_site_'+index+'_indicator').hide();
                   });
                   

                   $('#trial_'+spID+'_site_'+index+'_trialStatusHistoryTable tbody')
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
                                       
                                       if ($("#dialog-edit").dialog("instance")) {
                                           $("#dialog-edit").dialog("destroy");
                                       }
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
                                                           $(this).dialog("close");
                                                           $('#trial_'+spID+'_site_'+index+'_indicator').show();
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
                                                                                   uuid : uuid,
                                                                                   discriminator : 'trial_'+spID+'_site_'+index+'.statusHistory.'
                                                                               },
                                                                               timeout : 30000
                                                                           })
                                                                   .always(function() {
                                                                       $('#trial_'+spID+'_site_'+index+'_indicator').hide();
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
                                       
                                       $("#dialog-edit").dialog('open');
                                       $('#uuid').val(uuid);
                                       $('#statusDate').val(statusDate);
                                       $('#statusCode').val(statusCode);                                       
                                       $('#editComment').val('');
                                   });
                   
                   $('#trial_'+spID+'_site_'+index+'_trialStatusHistoryTable tbody')
                   .on(
                           'click',
                           '.fa-trash-o',
                           function() {
                               var uuid = table.row($(this).parents('tr'))
                                       .data().DT_RowId;
                               $('#trial_'+spID+'_site_'+index+'_indicator').show();
                               $
                               .ajax(
                                       {
                                           type : "POST",
                                           url : backendUrlTemplate
                                                   + "deleteStatus.action",
                                           data : {
                                               comment : '',
                                               uuid : uuid,
                                               discriminator : 'trial_'+spID+'_site_'+index+'.statusHistory.'
                                           },
                                           timeout : 30000
                                       })
                               .always(function() {
                                   $('#trial_'+spID+'_site_'+index+'_indicator').hide();
                               })
                               .done(
                                       function() {
                                           $('#runValidations').val('true');
                                           table.ajax.reload();
                                       })
                               .fail(
                                       function(jqXHR,
                                               textStatus,
                                               errorThrown) {
                                           alert(jqXHR
                                                   .getResponseHeader('msg'));
                                       });
                           });

            }
            var table = $('#trial_'+spID+'_site_'+index+'_trialStatusHistoryTable').DataTable();
            
            $('#trial_'+spID+'_site_'+index+'_indicator').show();
            $
            .ajax(
                    {
                        type : "POST",
                        url : backendUrlTemplate
                                + "addStatus.action",
                        data : {
                            statusDate : statusDate,
                            statusCode : statusCode,
                            discriminator : 'trial_'+spID+'_site_'+index+'.statusHistory.'
                        },
                        timeout : 30000
                    })
            .always(function() {
                 $('#trial_'+spID+'_site_'+index+'_indicator').hide();
            })
            .done(function() {
                $('#trial_'+spID+'_site_'+index+'_status').val('');
                $('#trial_'+spID+'_site_'+index+'_statusDate').val('');                
                $('#runValidations').val('true');
                table.ajax.reload();
            })
            .fail(
                    function(jqXHR, textStatus,
                            errorThrown) {
                        alert(jqXHR
                                .getResponseHeader('msg'));
                    });            
            
        })(jQuery);     
                
    }
    
    function save() {
        displayWaitPanel();
        hideErrorRows();
        
         var ajaxReq = new Ajax.Request('${validateUrl}', {
             method: 'post',
             parameters: $('addSitesForm').serialize(),
             onSuccess: function(transport) {
                 hideWaitPanel();   
                 var errors = transport.responseJSON;
                 if (errors.errors.length > 0) {
                     firstError = false;
                     $A(errors.errors).each(function(errObj) {                       
                         displayErrorsRow(errObj.spID, errObj.index, errObj.errors);
                     });
                 } else {
                     displayWaitPanel();
                     $('addSitesForm').action = 'addSitessave.action';
                     $('addSitesForm').submit();
                 }
             },
             onFailure: function(transport) {
                 hideWaitPanel();  
                 alert('Error when communicating with the server. Please try again.');                 
             },
             onException: function(requesterObj, exceptionObj) {
                 ajaxReq.options.onFailure(null);
             },
             on0: function(transport) {
                 ajaxReq.options.onFailure(transport);
             }
         });
    }
    
    function hideErrorRows() {
        $$('.errors').each(Element.hide);
    }
    
    var firstError = false;
    function displayErrorsRow(spID, index, errors) {
        $('trial_'+spID+'_site_'+index+'_errorDiv').innerHTML = errors;     
        $('trial_'+spID+'_site_'+index+'_errorRow').show();     
        
        if (!firstError) {
            firstError = true;
            var offset = jQuery('#trial_'+spID+'_site_'+index+'_errorRow')
                    .offset();
            offset.left -= 80;
            offset.top -= 80;
            jQuery('html, body')
                .animate(
                        {
                            scrollTop : offset.top,
                            scrollLeft : offset.left
                        });
        }
        
        jQuery('#trial_'+spID+'_site_'+index+'_errorMainDiv').show('blind');
    }
    
    function disableSorting() {
        jQuery("#th1").unbind('click');     
        jQuery("#th2").unbind('click');
    }
    
    var dataTable;
    
    (function($) {
        $(document).ready(function() {
            
            $('[data-toggle="tooltip"]').tooltip({
                'placement' : 'top'
            });

            dataTable = $('#trialsTable').dataTable({
                "bPaginate" : false,
                "bLengthChange" : false,
                "bFilter" : false,
                "bSort" : true,
                "bInfo" : false,
                "bAutoWidth" : false,

                "aoColumnDefs" : [ {
                    'bSortable' : false,
                    'aTargets' : [ 0,3 ]
                } ]
            });

            // enable multi-select on program-code-ids
            $("select.s2pgc").select2({
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
            
        });
    }(jQuery));
    
    
    
</script>
</head>
<body>
    <!-- main content begins-->
    <div class="container">
        <c:set var="topic" scope="request" value="addsite" />

        <s:form name="addSites" action="addSites.action" id="addSitesForm"
            cssClass="form-horizontal" role="form">
            <s:token />
            <s:hidden name="uuid" id="uuid" />
            <s:hidden name="runValidations" id="runValidations" />
            <h3 class="heading">
                <span>Search Trials</span>
            </h3>
            <div class="row">
                <div class="col-md-10 col-md-offset-1">
                    <p class="mb20">NOTE: Search results include only those eligible Abbreviated Trials with
                        which your affiliated organization or its family members are not yet participating</p>
                </div>

            </div>

            <reg-web:failureMessage />
            <reg-web:sucessMessage />
            <s:if test="hasActionErrors()">
                <div class="alert alert-danger">
                    <s:actionerror />
                </div>
            </s:if>

            <div id="filters">
                <div class="form-group">
                    <label for="identifier" class="col-md-3 control-label">NCI,
                        Lead Org, or Other Trial Identifier: </label>
                    <div class="col-md-3">
                        <s:textfield id="identifier" name="criteria.identifier"
                            placeholder="Examples: NCI-2008-00015; ECOG-1234" maxlength="50"
                            size="100" cssClass="form-control" />
                    </div>
                    <label for="officialTitle" class="col-md-1 control-label">
                        Trial Title: </label>
                    <div class="col-md-5">
                        <s:textfield id="officialTitle" name="criteria.officialTitle"
                            placeholder="Enter all or part of title" maxlength="400"
                            size="100" cssClass="form-control" />
                    </div>
                </div>
                <div class="bottom no-border">
                    <button type="button" class="btn btn-icon btn-primary" id="runSearchBtn"
                        onclick="search();">
                        <i class="fa-search"></i>
                        <fmt:message key="siteadministration.buttons.search" />
                    </button>
                    <button type="button" class="btn btn-icon btn-default" id="resetBtn"
                        onclick="resetSearch();">
                        <i class="fa-repeat"></i>
                        <fmt:message key="siteadministration.buttons.reset" />
                    </button>
                </div>
            </div>

            <c:if test="${not empty records}">
                <h3 class="heading">
                    <span>Trials</span>
                </h3>

                <div class="row">
                    <div class="col-md-10 col-md-offset-1">
                        <p class="mb20">
                            Here are the trials to which you can add sites. You can sort by
                            trial identifier or title by clicking on the corresponding column
                            header (note: sorting becomes disabled once you start entering
                            sites). To add sites to a trial, click the <b>Plus</b> sign and
                            enter the site information in the fields provided. After adding
                            your sites to each of the trials, scroll to the bottom of the 
                            page and click <b>Save.</b>
                        </p>
                    </div>

                </div>

                <div class="table-wrapper">

                    <table class="table table-bordered" id="trialsTable">
                        <thead class="sortable">
                            <tr>
                                <th id="th0"></th>
                                <th id="th1" nowrap="nowrap"><a>Trial Identifier<i
                                        class="fa-sort"></i></a></th>
                                <th id="th2"><a>Trial Title<i class="fa-sort"></i></a></th>
                                <c:if test="${requestScope['CANCER_TRIAL']}">
                                <th id="th3"><a>Program Code(s)<i class="fa fa-question-circle pgchelp" title='<fmt:message key="add.site.programCode.cancer.tooltip" />'></i></a></th>
                                </c:if>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${records}" var="trial" varStatus="stat">
                                <tr id="trialrow_${trial.studyProtocolId}">
                                    <td><i class="fa fa-plus-square" data-toggle="tooltip"
                                        onclick="beginAddingSites(${trial.studyProtocolId})"
                                        id="plussign_${trial.studyProtocolId}"
                                        title="Click here to add sites to ${trial.nciIdentifier}"></i></td>
                                    <td nowrap="nowrap">
                                        <c:out value="${trial.nciIdentifier}" />
                                    </td>
                                    <td>
                                        <c:out value="${trial.officialTitle}" />
                                    </td>
                                     <c:if test="${requestScope['CANCER_TRIAL']}">
                                    <td class="pgc">
                                    
                                        <div id="s2div_${trial.studyProtocolId}" style="display:none; max-width: 160px; ">
                                           
                                                <select id="pgc_${trial.studyProtocolId}" name="trial_${trial.studyProtocolId}_programCode" multiple="multiple"
                                                        class="s2pgc" data-placeholder="Program Code(s)" >
                                                    <c:forEach var="entry" items="${requestScope['PROGRAM_CODES']}">
                                                        <c:if test="${entry.value.active}">
                                                            <option value="${entry.value.id}" title="${entry.value.displayName}" ${ fn:contains(requestScope['TRIAL_PROGRAM_CODES'][trial.studyProtocolId], entry.value.id)? 'selected' : ''} >${entry.value.programCode}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                           
                                        </div>
                                     
                                    </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                </div>
                <div class="bottom no-border" id="saveCancelDiv"
                    style="display: none;">
                    <button type="button" class="btn btn-icon btn-primary" id="saveBtn"
                        onclick="save();">
                        <i class="fa-save"></i> Save
                    </button>
                    <button type="button" class="btn btn-icon btn-default" id="cancelBtn"
                        onclick="cancel();">
                        <i class="fa-times-circle"></i> Cancel
                    </button>
                </div>
            </c:if>

        </s:form>
    </div>
    <s:set name="statusCodeValues"
        value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()" />
    <c:forEach items="${records}" var="trial">
        <div id="addSitesDiv_${trial.studyProtocolId}" class="container-fluid"
            style="display: none;padding-left:12px">
            <div class="row">
                <div class="col-md-1"></div>
                <div class="col-md-11">
                    <div data-toggle="popover" data-placement="left"
                        data-content="<fmt:message key="tooltip.empty_site" />"
                        class="container-fluid" id="trial_${trial.studyProtocolId}_info" style="margin-left:45px;">
                        <table class="table no-border">
                            <c:forEach items="${trial.orgsThatCanBeAddedAsSite}" var="org"
                                varStatus="stat">
                                <c:set var="rowVisibility" value="${stat.first?'':'none'}" />
                                <tr class="bottom-border-only" style="display: ${rowVisibility};" 
                                   id="trial_${trial.studyProtocolId}_site_${stat.index}_header">
                                    <td><b>Site<span class="required">*</span></b></td>
                                    <td><b>Principal Investigator<span class="required">*</span></b></td>
                                    <td></td>
                                    <td><b>Local Trial Identifier<span class="required">*</span></b></td>
                                    <td></td>
                                </tr>
                                <tr
                                    id="trial_${trial.studyProtocolId}_site_${stat.index}_errorRow"
                                    style="display: none;" class="errors">
                                    <td colspan="8">
                                        <div class="container-fluid errors" style="display: none;"
                                            id="trial_${trial.studyProtocolId}_site_${stat.index}_errorMainDiv">
                                            <div class="row alert alert-danger">
                                                <div class="col-md-1">
                                                    <span
                                                        class="glyphicon glyphicon-large glyphicon-exclamation-sign"></span>
                                                </div>
                                                <div
                                                    id="trial_${trial.studyProtocolId}_site_${stat.index}_errorDiv"
                                                    class="col-md-11"></div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>

                                
                                <tr id="trial_${trial.studyProtocolId}_site_${stat.index}_row"
                                    style="display: ${rowVisibility};">
                                    <td><select class="form-control" style="min-width: 200px;"
                                        name="trial_${trial.studyProtocolId}_site_${stat.index}_org_poid"
                                        id="trial_${trial.studyProtocolId}_site_${stat.index}_org_poid">
                                            <c:forEach items="${trial.orgsThatCanBeAddedAsSite}"
                                                var="innerOrg">
                                                <option value="${innerOrg.identifier.extension}">
                                                    <c:out value="${innerOrg.name.part[0].value}" />
                                                </option>
                                            </c:forEach>
                                    </select></td>
                                    <td valign="middle" class="pi"><span
                                        id="trial_${trial.studyProtocolId}_site_${stat.index}_pi_name" style="padding:6px;">
                                            <i style="font-style: italic;color:#aaa;font-size:13px;">Not Selected</i>
                                    </span> <input type="hidden"
                                        id="trial_${trial.studyProtocolId}_site_${stat.index}_pi_poid"
                                        name="trial_${trial.studyProtocolId}_site_${stat.index}_pi_poid" />
                                    </td>
                                    <td nowrap="nowrap">
                                        <button title="Select PI for this site"
                                            data-toggle="tooltip"
                                            id="trial_${trial.studyProtocolId}_site_${stat.index}_pi_lookupBtn"
                                            onclick="lookupPI(${trial.studyProtocolId}, ${stat.index});"
                                            class="btn btn-default" type="button">
                                            <i class="fa-user"></i>&nbsp;
                                        </button>
                                    </td>
                                    <td><input type="text"
                                        id="trial_${trial.studyProtocolId}_site_${stat.index}_localID"
                                        name="trial_${trial.studyProtocolId}_site_${stat.index}_localID"
                                        placeholder="Enter Local Trial ID" maxlength="50"
                                        class="form-control" /></td>
                                    <c:if test="${not stat.first}">
                                        <td><div class="input-append" style="padding-left: 10px;"
                                                onclick="copySiteDataFromRowAbove(${trial.studyProtocolId},${stat.index});"
                                                title="Click here to quickly copy site information from the row above"
                                                data-toggle="tooltip">
                                                <span class="add-on"><i class="fa-hand-o-left"></i></span>
                                            </div></td>
                                    </c:if>
                                </tr>
                                <tr
                                    id="trial_${trial.studyProtocolId}_site_${stat.index}_stathistrow"
                                    style="display: ${rowVisibility};">
                                    <td colspan="8">
                                        <div class="container-fluid" style="padding-left:0px;">
                                            <div class="row">
                                                <div class="col-xs-10">
                                                    <div class="table-header-wrap">
                                                        <table  class="sr-table table no-border">
                                                                <tr>
                                                                    <td nowrap="nowrap"><label
                                                                        for="trial_${trial.studyProtocolId}_site_${stat.index}_status">Site Recruitment Status<span
                                                                        class="required">*</span></label></td>
                                                                    <td nowrap="nowrap"><label
                                                                        for="trial_${trial.studyProtocolId}_site_${stat.index}_statusDate">Site Recruitment Status Date<span
                                                                        class="required">*</span></label></td>      
                                                                    <td>&nbsp;</td>                                                            
                                                                </tr>
                                                                <tr>
                                                                   <td><s:select headerKey="" headerValue="--Select--"
                                                                            id="trial_%{#attr.trial.studyProtocolId}_site_%{#attr.stat.index}_status"
                                                                            name="trial_%{#attr.trial.studyProtocolId}_site_%{#attr.stat.index}_status"
                                                                            list="#statusCodeValues" cssClass="form-control" /></td>
                                                                    <td><div class="datetimepicker input-append" id="datetimepicker">
                                                                            <input type="text"
                                                                                id="trial_${trial.studyProtocolId}_site_${stat.index}_statusDate"
                                                                                name="trial_${trial.studyProtocolId}_site_${stat.index}_statusDate"
                                                                                style="min-width: 80px;"
                                                                                placeholder="mm/dd/yyyy" maxlength="10" size="10"
                                                                                data-format="MM/dd/yyyy" class="form-control" /> <span
                                                                                class="add-on btn-default"><i
                                                                                class="fa-calendar icon-calendar"></i></span>
                                                                        </div></td>
                                                                    <td><button type="button" id="trial_${trial.studyProtocolId}_site_${stat.index}_addStatusBtn"
                                                                            onclick="addSiteStatus(${trial.studyProtocolId},${stat.index});"
                                                                            class="btn btn-icon btn-default">
                                                                            <i class="fa-plus"></i>Add Status
                                                                        </button></td>
                                                                </tr>
                                                                <tr>
                                                                  <td colspan="3" align="left">
                                                                  <span class="info">Please refer to the <a
                                                                        href="https://wiki.nci.nih.gov/x/zYLpDw" target="newPage">Site
                                                                            Status Transition Rules</a>.
                                                                    </span>
                                                                  </td>
                                                                </tr>                                                          
                                                        </table>
                                                    </div>                                                    
                                                </div>                                                 
                                            </div>
                                            <div class="row">
                                                 <div class="col-xs-10">
                                                    <div style="height: 16px;" align="center">
                                                        <img id="trial_${trial.studyProtocolId}_site_${stat.index}_indicator" style="display: none;"
                                                            src="${pageContext.request.contextPath}/images/loading.gif"
                                                            alt="Progress Indicator." width="16" height="16" />
                                                    </div>
                                                 </div>
                                            </div>
                                            <div class="row" id="trial_${trial.studyProtocolId}_site_${stat.index}_statusHistoryDiv" style="display:none;">
                                                 <div class="col-xs-4">
                                                    <h5>Site Recruitment Status History</h5>
                                                 </div>
                                                 <div class="col-xs-offset-8"></div>
                                                 <div class="col-xs-11">
                                                     <div class="table-header-wrap">
                                                        <table class="table table-bordered status-history" id="trial_${trial.studyProtocolId}_site_${stat.index}_trialStatusHistoryTable">
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
                                                  <div class="col-xs-offset-1"></div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>                               
                                <c:if test="${not stat.last}">
                                    <tr
                                        id="trial_${trial.studyProtocolId}_site_${stat.index}_btnrow"
                                        style="display: ${rowVisibility};">
                                        <td colspan="8">
                                            <button type="button" class="btn btn-icon btn-primary"
                                                onclick="addAnotherSiteRow(${trial.studyProtocolId},${stat.index});">
                                                <i class="fa-plus-square"></i> Add Another Site
                                            </button>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
    
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
                <s:select id="statusCode" name="statusCode" list="#statusCodeValues"
                    cssClass="form-control" />
            </div>
        </div>   
        <div class="row">
            <div class="col-xs-4" align="right">
                <label for="editComment">Comment:</label>
            </div>
            <div class="col-xs-8">
                <s:textarea id="editComment" name="editComment" rows="2"
                    maxlength="1000" cssClass="form-control charcounter"
                    cssStyle="width: 100%;" />
            </div>
        </div>
    </div>
</div>
</body>
</html>

// used by ProgramCodeAssignmentAction jsp files.
var programCodeColumn = 5;
var trailsTable;
var participationTable;
var curStudyProtocol;
var curProgramCode;
var allProgramCodes = [];
var filteredProgramCodes = [];
var trialProgramCodeMap = {};
var tmparr = [];
var tmpS2Ds = [];
var tmpSelectTrs = null;
var fnlMsCtrl = null;
var curS2Ctrl = null;
var maddS2Ctrl = null;
var mrmS2Ctrl = null;
var mrplS2Ctrl1 = null;
var mrplS2Ctrl2 = null;
var s2closing = false;
var curFamilyPoId;
var ts = 0;

function ellipsis(s, len) {
    if (s.length <= len) {
        return s;
    }
    return s.substr(0,len-3) + "..." ;
}
function idfy(code) {
   return code.split(' ').join('_');
}
function pgcDisplayName(code, name) {
    if (name) {
        return code + " - " + name;
    }
    return code;
}
// will remove program code currently associated with the given study
function removeProgramCodeFromTrialMap($, sp, pgc) {
    tmparr = [];
    $(trialProgramCodeMap[sp]).each(function(i, p){
        if (p.code != pgc) {
            tmparr.push(p);
        }
    });
    trialProgramCodeMap[sp] = tmparr;
}


// will add program code to the trial map
function addProgramCodeToTrialMap($, sp, pgc) {
    $(allProgramCodes).each(function(i, p){
        if (p.code == pgc) {
//            console.log("pushing : " + p.code + " ==" +  pgc);
            trialProgramCodeMap[sp].push(p);
        }
    });

}

//will refresh the program codes currently selected in the funnel filter
function refreshFilteredProgramCodes($) {
    filteredProgramCodes = [];
    if ($("#fpgc-sel").val()) {
       $($("#fpgc-sel").val()).each(function(i, pgc){
            $(allProgramCodes).each(function(i, p) {
                if (pgc == p.code) {
                    filteredProgramCodes.push(p);
                }
            });
        });
    }
}

function isProgramCodeInCurrentFamily(pgcId) {
    for (var i = 0; i < allProgramCodes.length; i++) {
        if (allProgramCodes[i].familyPoId == curFamilyPoId && allProgramCodes[i].id == pgcId) {
            return true;
        }
    }
    return false;
}

function showProgramCodeS2InRow($, sp) {
    //clear the content of select box
    $("#" + sp + "_trSel").empty();

    tmpS2Ds = [{id:"", text:""}];
    tmparr = [];

    $("#" + sp + "_tra").hide();

    //creating data source for s2
    $(trialProgramCodeMap[sp]).each(function(i, p){
        tmparr.push(p.id);
//        console.log(" tmpS2Ds << [" + p.code + ", " + p.name + "removed:true]") ;
    });

    $(allProgramCodes).each(function(i, p){
        if (curFamilyPoId != p.familyPoId) {
            return;
        }
        if ($.inArray(p.id, tmparr) < 0) {
            tmpS2Ds.push({id: p.code, text: pgcDisplayName(p.code, p.name), title: pgcDisplayName(p.code, p.name)});
        }
    });

    $("#" + sp + "_trDiv").show();
    return $("#" + sp + "_trSel").select2({
        data:tmpS2Ds,
        placeholder: "Program Code"
    });

}

//Will refresh the trials table, and show the flash message
function handleReportingPeriodChange($, evt) {
    evt.preventDefault();
    trailsTable.ajax.reload(function(json){
        if (json && json.updated) {
            if (json.lengthChanged) {
                $('#length_flash').delay(100).fadeIn('normal', function() {
                    $(this).delay(2500).fadeOut();
                });
            }
            if(json.endDateChanged) {
                $('#date_flash').delay(100).fadeIn('normal', function() {
                    $(this).delay(2500).fadeOut();
                });
            }
        }
    });
}

// Will enable disable the assign/replace/remove buttons
function toggleMultiButtons($, grayOut) {

    $("button.multi").each(function(i, b){
        $(b).prop('disabled', grayOut);
    });
}

function pgcinit($) {

    refreshFilteredProgramCodes($);
    $("#familyPoId").on('change', function(evt) {
        $("#changeFamilyFrm").submit();
    });

    curFamilyPoId = $("#familyPoId").val();

    participationTable = $('#participationTbl').DataTable( {
        "dom": 't',
        serverSide: false,
        ajax: {
            url: "managePCAssignmentparticipation.action",
            type:"POST",
            data: function(d) {
                d.familyPoId = $("#familyPoId").val();
                d.studyProtocolId = curStudyProtocol;
            }
        },
        "columns" : [
            {width:"50%", data:"site"},
            {width:"50%", data: "investigator"}
        ],
        sProcessing: "Loading...",
        processing:true
    });

    trailsTable = $('#trialsTbl').DataTable( {
        "dom": 'lprftip<"row">B',
        "pagingType": "full_numbers",
        "order": [[ 0, "desc" ]],
        "oLanguage": {
            "sInfo": "Showing _START_ to _END_ of _TOTAL_",
            "sLengthMenu": "Show _MENU_",
            "oPaginate": {
                "sFirst": "<<",
                "sPrevious": "<",
                "sNext": ">",
                "sLast": ">>"
            }
        },
        "serverSide": false,
        sProcessing: "Loading...",
        processing:true,
        "ajax": {
            "url": "managePCAssignmentfindTrials.action",
            "type": "POST",
            "data": function ( d ) {
                d.familyPoId = $("#familyPoId").val();
                d.reportingPeriodEndDate = $("#reportingPeriodEndDate").val();
                d.reportingPeriodLength = $("#reportingPeriodLength").val();
                if ($("#fpgc-sel").val()) {
                    d.pgcListParam = $.grep($("#fpgc-sel").val(), function(v){ return $.isNumeric(v);}).join(',');
                }
            }
        },
        buttons: [
            {extend: 'excel',filename:'program_code_assignments', title: 'Program Code Assignments'},
            {extend: 'csv', extension:'.csv', filename:'program_code_assignments'}
        ],
        "columns": [
            {width:"15%", data: "identifiers", className:"scol"},
            {width: "24%", data:function(row, type, val, meta){
               return "<div class='spt scol' title='" + row.title +"'>" + row.title + "</div>";
            }},
            {width: "23%",data: function(row, type, val, meta){
                return "<div class='scol'>" + row.leadOrganizationName + "<br>" + '<a href="#" class="mypgp" rel="' + row.studyProtocolId + '">Show my participation</a></div>';
            }},
            {width: "15%",data: "piFullName" , className:'scol'},
            {width: "5%",data: "trialStatus",  className: 'scol'},
            {width:"18%",
                orderable: false ,
                className:"pgctd",
                data: function ( row, type, val, meta ) {
                    var snippet = "";
                    $(row.programCodes).each(function(i,pc) {
                        if (isProgramCodeInCurrentFamily(pc.id)) {
                            snippet = snippet +  '<a id="' + row.studyProtocolId + '_' + idfy(pc.code) +'_a" href="#" rel="' + row.studyProtocolId + '" title="' + pgcDisplayName(pc.code, pc.name) + '" pc="' +  pc.code + '" class="pg btn-xs pgcrm ">' + ellipsis(pc.code,10) + ' <span class="pg glyphicon glyphicon-remove"></span></a> ';
                        }
                    });

                    snippet = snippet + '<a id="' + row.studyProtocolId + '_tra" href="#" class="pgcssa" rel="' + row.studyProtocolId +'"><span class="glyphicon glyphicon-chevron-down"></span></a>';
                    snippet = snippet + '<div id="' + row.studyProtocolId + '_trDiv" class="pgcssd" style="display:none;"><select id="' + row.studyProtocolId + '_trSel" rel="' + row.studyProtocolId +'" style="width:100%;"></select></div>';
                    return snippet;
                }}

        ],
        "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
            trialProgramCodeMap[aData.studyProtocolId] = [];
            $(aData.programCodes).each(function(i,pc) {
                trialProgramCodeMap[aData.studyProtocolId].push({id: pc.id, code:pc.code, name:pc.name});
            });

        }


    });

    // Apply the search
    $('input.cf').on('keyup change', function () {
        trailsTable.columns($(this).attr("rel")).search(this.value).draw();
        $('#pgcFilter').val(this.value);
    } );


    $('#trialsTbl tbody').on('click', 'a.pgcrm', function (evt) {
        evt.preventDefault();
        curStudyProtocol = $.attr(this, 'rel');
        curProgramCode = $.attr(this, 'pc');
        unAssignProgramCode($, $(this).parents("td"), curStudyProtocol, curProgramCode);
    } );

    //populate data for select2 and show it
    $('#trialsTbl tbody').on('click', 'a.pgcssa', function (evt) {
        evt.preventDefault();
        curStudyProtocol = $.attr(this, 'rel');
        curS2Ctrl = showProgramCodeS2InRow($, curStudyProtocol);
        curS2Ctrl.on("select2:close", function(evt){
           evt.preventDefault();
           if (s2closing) {
               return; //https://github.com/select2/select2/issues/3503
           }

           curStudyProtocol = $.attr(this, 'rel');
           assignProgramCode($, this, $(this).parents("td"), curStudyProtocol, $(this).val());
        });


    } );


    $('#trialsTbl tbody').on('click', 'a.mypgp', function (evt) {
        evt.preventDefault();
        curStudyProtocol = $.attr(evt.target, 'rel');
        openParticipationDialog($);
    } );



    //initially disable the assign-remove-replace buttons
    toggleMultiButtons($, true);

    //click on row should select/deselct the row
    $('#trialsTbl tbody').on('click', 'tr', function (evt) {
        evt.preventDefault();
        $(this).toggleClass('selected');

        //enable disable assign-remove-replace button(s)
        toggleMultiButtons($, $("tr.selected").length <= 0);
    } );

    //initialize the date picker and length select box
    $('#datetimepicker').datetimepicker()
        .on('hide',function (evt) {
            handleReportingPeriodChange($, evt);
        }).on('change', function (evt) {
            handleReportingPeriodChange($, evt);
        });
    $("#reportingPeriodLength").on('change', function (evt) {
        handleReportingPeriodChange($, evt);
    });


    //initialize funnel select
    fnlMsCtrl = $("#fpgc-sel").multiselect({
        nonSelectedText: 'Select Program Code(s)' ,
        enableFiltering: false,
        includeSelectAllOption: true,
        selectAllText: 'Select/Deselect All',
        dropUp: true,
        dropRight:false,
        buttonWidth: '180px',
        filterPlaceholder: 'Search',
        onDropdownHide: function (evt) {
            trailsTable.ajax.reload();
            if (!$("#fpgc-sel").val()) {
                $("#fpgc-div").hide();
            }
        }

    });



    //initialize the assign multiple popup multiselect
    maddS2Ctrl = $("#pgc-madd-sel").select2({
        width:'100%',
        containerClass: 'pp',
        templateSelection : function(pg){
            return pg.id;
        }
    });

    mrmS2Ctrl = $("#pgc-mrm-sel").select2({
        width:'100%',
        containerClass: 'pp',
        templateSelection : function(pg){
            return pg.id;
        }
    });


    mrplS2Ctrl1 = $("#pgc-mrpl-selone").select2({
        width:'100%',
        containerClass: 'pp',
        templateSelection : function(pg){
            return pg.id;
        }
    }).on("change", function(e) {
        $("#pgc-mrpl-seltwo > option").each(function(i, o) {
            $(o).prop('disabled', false);
            if (o.value == $(e.target).val()) {
                $(o).prop('disabled', true);
            }
        });
        mrplS2Ctrl2.val("").trigger('change');
    });

    mrplS2Ctrl2 = $("#pgc-mrpl-seltwo").select2({
        width:'100%',
        containerClass: 'pp',
        templateSelection : function(pg){
            return pg.id;
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

    //handle click on funnel icon
    $("#fpgc-icon-a").on('click', function (evt) {
        evt.preventDefault();
        if ($("#fpgc-div").is(':hidden')) {
            $("#fpgc-div").show();
        } else {
            if (!$("#fpgc-sel").val()) {
                $("#fpgc-div").hide();
            }
        }
    });

    //minor logic on the funnel multi-select
    $("label.checkbox").each(function(i, el){
        $(el).attr('title', $(el).text());
    });

    //show the select box if there is value in pgcFilter
    if ($("#fpgc-sel").val()) {
        $("#fpgc-div").show();
    }

}

//will open up the dialog to show multiple PG selection for assignment
function assignMultiple($) {
    console.log("In assign multiple");
   //cleanup
   if ($('#pgc-madd-dialog').dialog("instance")) {
       $('#pgc-madd-dialog').dialog("destroy");
   }
    $("#pgc-madd-indicator").hide();
    $("#pgc-madd-sel").empty();
    tmparr = [];
    tmpS2Ds = [];
    $(allProgramCodes).each(function(i, pgc){
        tmparr[pgc.code.toString()] = 0;
    });

    tmpSelectTrs = $("#trialsTbl > tbody > tr.selected");
    $(tmpSelectTrs).each(function(i, tr){
        $("tr#" + tr.id + " > td.pgctd > a.pg").each(function(i, a){
            tmparr[$(a).attr("pc").toString()] = tmparr[$(a).attr("pc").toString()] + 1;
        });
    });
    $(allProgramCodes).each(function(i, pgc){
        if (tmparr[pgc.code.toString()] < tmpSelectTrs.length ) {
            tmpS2Ds.push(pgc);
        }
    });
//    console.log("tmpS2Ds:" + tmpS2Ds);

    $(tmpS2Ds).each(function(i, pgc) {
        $("#pgc-madd-sel").append($("<option />",{
            value: pgc.code,
            text: pgcDisplayName(pgc.code, pgc.name),
            title: pgcDisplayName(pgc.code, pgc.name)
           }
        ));
    });


    //show dialog
    $("#pgc-madd-dialog").dialog({
        modal : true,
        autoOpen : true,
        resizable: false,
        width : $(window).width() * 0.25,
        height : $(window).height() * 0.4,
        buttons : [
            {
                id:"pgc-madd-dialog-ok",
                text:"OK",
                click: function(evt) {
                    evt.preventDefault();
                    handlePopuInteractions($, $('#pgc-madd-sel').val(),[],
                        "pgc-madd",
                        "managePCAssignmentassignProgramCodesToTrials.action",
                        "Program Codes were successfully assigned",
                        "assign");
                }
            },
            {
                id:'pgc-madd-dialog-cancel',
                text:"Cancel",
                click: function(evt) {
                    evt.preventDefault();
                    $("#pgc-madd-dialog").dialog("close");
                }
            }
        ],
        open:function() {
            maddS2Ctrl.trigger("change");
            maddS2Ctrl.trigger("updateResults");
        }
    });

}

//Will show nothing to unassign box
function popNothingToRemove($) {

    console.log("In popNothingToRemove");

    //cleanup
    if ($('#pgc-mrm-dialog-empty').dialog("instance")) {
        $('#pgc-mrm-dialog-empty').dialog("destroy");
    }
    //show dialog
    $("#pgc-mrm-dialog-empty").dialog({
        modal : true,
        autoOpen : true,
        resizable: false,
        width : $(window).width() * 0.2,
        height : $(window).height() * 0.2,
        buttons : [
            {
                id:"pgc-mrm-dialog-empty-cancel",
                text:"OK",
                click: function(evt) {
                    evt.preventDefault();
                    $("#pgc-mrm-dialog-empty").dialog("close");
                }

            }
        ]
    });
}

//will unassign program codes from multiple trials
function removeMultiple($) {
    console.log("In remove multiple");
    tmparr = [];
    $("#trialsTbl > tbody > tr.selected > td.pgctd > a.pg").each(function(i, a){
        if (tmparr.indexOf($(a).attr("pc")) < 0) {
            tmparr.push($(a).attr("pc"));
        }
    });
    if (tmparr.length > 0) {
        popRemoveMultiple($, tmparr);
    } else {
        popNothingToRemove($);
    }
}

function popRemoveMultiple($, tmparr) {
    console.log("In popRemoveMultiple");
    //cleanup
    if ($('#pgc-mrm-dialog').dialog("instance")) {
        $('#pgc-mrm-dialog').dialog("destroy");
    }
    $('#pgc-mrm-indicator').hide();

    //empty out select box and add only the ones available on the trials
    $("#pgc-mrm-sel").empty();

    $(tmparr.sort()).each(function(i, p){
        tmpS2Ds = $.grep(allProgramCodes, function(pgc, j){
           return pgc.code == p;
        });

        if (tmpS2Ds.length > -1) {
            $("#pgc-mrm-sel").append($("<option />",{value: tmpS2Ds[0].code, text: pgcDisplayName(tmpS2Ds[0].code, tmpS2Ds[0].name), title: pgcDisplayName(tmpS2Ds[0].code, tmpS2Ds[0].name) }));
        }
    });

    //show dialog
    $("#pgc-mrm-dialog").dialog({
        modal : true,
        autoOpen : true,
        resizable: false,
        width : $(window).width() * 0.25,
        height : $(window).height() * 0.4,
        buttons : [
            {
                id:"pgc-mrm-dialog-ok",
                text:"OK",
                click: function(evt) {
                    evt.preventDefault();
                    handlePopuInteractions($, $('#pgc-mrm-sel').val(), [],
                        "pgc-mrm",
                        "managePCAssignmentunassignProgramCodesFromTrials.action",
                        "Program Codes were successfully unassigned",
                        "unassign");
                }
            },
            {
                id:"pgc-mrm-dialog-cancel",
                text:"Cancel",
                click: function(evt) {
                    evt.preventDefault();
                    $("#pgc-mrm-dialog").dialog("close");
                }

            }
        ],
        open:function() {
            //initialize the assign multiple popup multiselect
            mrmS2Ctrl.trigger("change");
            mrmS2Ctrl.trigger("updateResults");
        }
    });
}



//will replace program codes from multiple trials
function replaceMultiple($) {
    tmparr = [];
    $("#trialsTbl > tbody > tr.selected > td.pgctd > a.pg").each(function(i, a){
        if (tmparr.indexOf($(a).attr("pc")) < 0) {
            tmparr.push($(a).attr("pc"));
        }
    });
    if (tmparr.length > 0) {
        popReplaceMultiple($, tmparr);
    } else {
        popNothingToReplace($);
    }

}

//Will show nothing to unassign box
function popNothingToReplace($) {

    console.log("In popNothingToRemove");

    //cleanup
    if ($('#pgc-mrpl-dialog-empty').dialog("instance")) {
        $('#pgc-mrpl-dialog-empty').dialog("destroy");
    }
    //show dialog
    $("#pgc-mrpl-dialog-empty").dialog({
        modal : true,
        autoOpen : true,
        resizable: false,
        width : $(window).width() * 0.25,
        height : $(window).height() * 0.2,
        buttons : [
            {
                id:"pgc-mrpl-dialog-empty-cancel",
                text:"OK",
                click: function(evt) {
                    evt.preventDefault();
                    $("#pgc-mrpl-dialog-empty").dialog("close");
                }

            }
        ]
    });
}

//Will popup replace multiple
function popReplaceMultiple($, tmparr) {
    //cleanup
    if ($('#pgc-mrpl-dialog').dialog("instance")) {
        $('#pgc-mrpl-dialog').dialog("destroy");
    }
    $('#pgc-mrpl-indicator').hide();

    //empty out first select box and add only the ones available on the trials
    $("#pgc-mrpl-selone").empty();
    $("#pgc-mrpl-seltwo").empty();

    $(allProgramCodes).each(function(i, pgc){
        if (tmparr.indexOf(pgc.code) >= 0) {
            $("#pgc-mrpl-selone").append($("<option />",{value: pgc.code, text: pgcDisplayName(pgc.code, pgc.name), title: pgcDisplayName(pgc.code, pgc.name)}));
        }
        $("#pgc-mrpl-seltwo").append($("<option />",{value: pgc.code, text: pgcDisplayName(pgc.code, pgc.name), title: pgcDisplayName(pgc.code, pgc.name)}));
    });


   //show dialog
    $("#pgc-mrpl-dialog").dialog({
        modal : true,
        autoOpen : true,
        resizable: false,
        width : $(window).width() * 0.25,
        height : $(window).height() * 0.5,
        buttons : [
            {
                id:"pgc-mrpl-dialog-ok",
                text:"OK",
                click: function(evt) {
                    evt.preventDefault();
                    handlePopuInteractions($, $('#pgc-mrpl-seltwo').val(), [$('#pgc-mrpl-selone').val()],
                        "pgc-mrpl",
                        "managePCAssignmentreplaceProgramCodesInTrials.action",
                        "Program Code was successfully replaced",
                        "replace");
                }
            },
            {
                id:"pgc-mrpl-dialog-cancel",
                text:"Cancel",
                click: function(evt) {
                    evt.preventDefault();
                    $("#pgc-mrpl-dialog").dialog("close");
                }

            }
        ],
        open:function() {
            //initialize the first select box
            mrplS2Ctrl1.trigger("change");
            mrplS2Ctrl1.trigger("updateResults");
            mrplS2Ctrl2.trigger("change");
            mrplS2Ctrl2.trigger("updateResults");

        }
    });


}

//will open participation dialog
function openParticipationDialog($) {

    //cleanup

    if ($("#dialog-participation").dialog("instance")) {
        $("#dialog-participation").dialog("destroy");
    }
    $("#participationTbl>tbody").empty();
    $("span.pgcpSite").first().html($('#familyPoId option:selected').text());

    //create dialog
    $("#dialog-participation").dialog({
        modal : true,
        autoOpen : false,
        width : $(window).width() * 0.6,
        buttons : [
            {
                id:'participation-close',
                text: "Close",
                click: function(evt) {
                    evt.preventDefault();
                    $(this).dialog("close");
                }
            }
        ]
    });

    //show dialog
    $("#dialog-participation").dialog('open');

    //reload data in table
    participationTable.ajax.reload();
}

//will unassign the program-code for a specific trial
function unAssignProgramCode($, td, sp, pgc) {

    //insert indicator
    $(td).append('<img id="' + sp + "_" + idfy(pgc) + '_img" src="../images/loading.gif"/>');

    //call ajax function
    $.post("managePCAssignmentunassignProgramCode.action",
        {
            "studyProtocolId": sp,
            "pgcParam": pgc ,
            "familyPoId": $("#familyPoId").val()
        })
        .done(function (data) {
            $('#' + sp + '_' + idfy(pgc) + '_a').remove();
            $('#' + sp + '_' + idfy(pgc) + '_img').remove();
            removeProgramCodeFromTrialMap($,sp, pgc);
            //show confirmation
            $("#" + sp + "_" + idfy(pgc) + "_span").show();
        })
        .fail(function (jqXHR) {
            $('#' + sp + '_' + idfy(pgc) + '_img').remove();
            showAjaxErrorOnPage($,jqXHR.getResponseHeader('msg'));
        });
}


//will assign the program-code for a specific trial
function assignProgramCode($, s2, td, sp, pgc) {
    s2closing = true;
    //destroy the select2 and hide the div
    if( $("#" + sp + "_trSel").hasClass("select2-hidden-accessible")) {
        $(s2).select2("destroy");
    }

    $("#" + sp + "_trDiv").hide();

    if (pgc) {
        //show ajax indicator
        $(td).append('<img id="' + sp + "_" + idfy(pgc) + '_img" src="../images/loading.gif"/>');

        //invoke ajax function
        $.post("managePCAssignmentassignProgramCode.action",
            {
                "studyProtocolId": sp,
                "pgcParam": pgc,
                "familyPoId": $("#familyPoId").val()
            })
            .done(function (data) {
                s2closing = false;
                $('#' + sp + '_' + idfy(pgc) + '_img').remove();
                addProgramCodeToTrialMap($,sp, pgc);
                //add the program code in the TD
                $('<a id="' + sp + '_' + idfy(pgc) +'_a" href="#" rel="' + sp + '" title="' + pgc + '" pc="' +  pgc + '" class="pg btn-xs pgcrm ">' + ellipsis(pgc, 10) + ' <span class="pg glyphicon glyphicon-remove"></span></a> ').insertBefore("#" + sp + "_tra");
                //remove the indicator image
                $('#' + sp + '_' + idfy(pgc) + '_img').remove();
                //show the down arrow
                $("#" + sp + "_tra").show();
            })
            .fail(function (jqXHR) {
                s2closing = false;
                //remove indicator image and show the down arrow
                $('#' + sp + '_' + idfy(pgc) + '_img').remove();
                $("#" + sp + "_tra").show();
                showAjaxErrorOnPage($,jqXHR.getResponseHeader('msg'));
            });


    }  else {
        s2closing = false;
        //show the down arrow
        $("#" + sp + "_tra").show();
    }
}

//will handle the user interactions on assign/unassign/replace multiple popups
function handlePopuInteractions($, pgcListParamOne, pgcListParamTwo, containerId, ajaxAction, msg, action) {

    console.log(action + ": handlePopuInteractions [pgcListParamOne:" + pgcListParamOne + ", pgcListParamTwo:" + pgcListParamTwo + "]");
    //check if there are any values selected
    if (pgcListParamOne && pgcListParamTwo) {

        //find the study ids
        tmparr = [];
        $('tr.selected').each(function(i, tr){
            var sp = $(tr).attr("id").split("_")[1];
            //makesure the program code is really present in the study (to support - replace)
            var p = $.grep(trialProgramCodeMap[sp],function(pgc,i) {
                return pgcListParamOne.indexOf(pgc.code) > -1;
            });
            if (p) {
                tmparr.push(sp);
            }

        });

        //show the indicator
        $('#' +containerId+ "-indicator").show();

        console.log(action + ": handlePopuInteractions [studyProtocolList:" +tmparr.join(',') +
            ", pgcListParam:" + pgcListParamOne.join(",") +
            ", pgcParam:" + pgcListParamTwo.join(",") +
            ", familyPoId:" +  $("#familyPoId").val() );

        //invoke ajax function
        $.post(ajaxAction,
            {
                "studyProtocolIdListParam": tmparr.join(','),
                "pgcListParam": pgcListParamOne.join(","),
                "pgcParam": pgcListParamTwo.join(","),
                "familyPoId": $("#familyPoId").val()
            })
            .done(function (data) {
                //close the box
                $('#' +containerId+ "-dialog").dialog("close");

                //refresh the table
                trailsTable.clear();
                trailsTable.ajax.reload();
                //show the info message
                showInfoMessageOnPage($, msg);

                //disable buttons
                toggleMultiButtons($, true);

            })
            .fail(function (jqXHR) {
                $(containerId + "-Errors").text(jqXHR.getResponseHeader('msg')) ;
                $(containerId + "-Errors").show();
            });
    }  else {
        //no values selected - just close the box
        $('#' +containerId+ "-dialog").dialog("close");
    }
}

// Will show the Ajax errors on the page
function showAjaxErrorOnPage($, msg) {
    $("#pgcErrorsMsg").text(msg);
    $("#pgcErrors").show();
    scroll(0, $("#pgcErrors").position().top);
}


// Will show the Ajax errors on the page
function showInfoMessageOnPage($, msg) {
    $("#pgcInfoMsg").text(msg);
    $("#pgcInfo").show().delay(5000).hide("slow");
    scroll(0, $("#pgcInfo").position().top);
}




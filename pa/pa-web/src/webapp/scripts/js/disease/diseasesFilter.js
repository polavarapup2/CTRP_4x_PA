/**
 * Disease Filter of the Ad-Hoc Report
 * @input @global diseaseTree PDQ disease tree to run the name lookup against [{'id':'', 'name':'', 'parentId':'', 'hasChildren':''}, ...]
 */

var bJstreeOperationReady = true;
var jstreeNodeIdsToOpen = [];
var numberOfAjaxInProgress = 0;

function isJstreeOperationReady() {
    return bJstreeOperationReady;
}

function setJstreeOperationReady(bReady) {
    bJstreeOperationReady = bReady;
}


function selectDisease (diseaseId, diseaseName) {
	 var diseaseSelection = document.getElementById('selectedDiseases');
	 var selectedDisease = document.getElementById('pdqDiseases');
 
	 var found = false;
	 for (var i = 0; i < selectedDisease.length; i++){
		 if (selectedDisease.options[i].value == diseaseId){
		      found = true;
		      break;
		    }
	 }
	 
	 if(!found){
		 var li = document.createElement("li");
		 li.setAttribute("id", "liid_"+diseaseId);
		 li.innerHTML = '<a href="#" title="Click to remove" onclick="removeSelection('+diseaseId+')"></a><div><span class="selectionFeaturedElement">'+diseaseName+'</span></div>';
		 diseaseSelection.appendChild(li);
		 
	 	var newDis = document.createElement("option");
	 	newDis.value = diseaseId;
	 	selectedDisease.appendChild(newDis);
	 }
	 document.getElementById("disease_selections_count").innerHTML = (selectedDisease.length  + ' diseases selected');
	 document.getElementById("disease_selections_count").show();
}

function removeSelection(disId){
	var diseaseSelection = document.getElementById('selectedDiseases');
	var li = document.getElementById('liid_'+disId);
	diseaseSelection.removeChild(li);

	var selectedDisease = document.getElementById('pdqDiseases');
	 
	 var found = false;
	 for (var i = 0; i < selectedDisease.length; i++){
		 if (selectedDisease.options[i].value == disId){
			 selectedDisease.remove(i);
		     break;
		 }
	 }
	 document.getElementById("disease_selections_count").innerHTML = (selectedDisease.length  + ' diseases selected');
	 document.getElementById("disease_selections_count").show();
}

function addAllDiseases(){
	var selectedDisease = document.getElementById('pdqDiseases');
	selectedDisease.empty();
	for (var d in diseases) {
		var disease = diseases[d];
			//alert(disease);
			if(disease.id) {
				selectDisease(disease.id, disease.name);
			}
	}
}

function openNCItWindow(diseaseId){
	newwindow=window.open('http://ncit.nci.nih.gov/ncitbrowser/ConceptReport.jsp?dictionary=NCI_Thesaurus&ns=NCI_Thesaurus&code='+diseaseId,'toolbar=0,location=0,menubar=0,modal=yes,alwaysRaised=yes');
	if (window.focus) {newwindow.focus()}
	return false;
}

var diseaseTree = [];
var diseases = [];

(function($){
    $.diseasesFilter = {
        pdqDialog : {},
        
        //*******************************
        //** Search for Disease in PDQ **
        //*******************************
        searchDiseases : function () {
            var self = this;
            var searchTerm = $('#diseasesSection .diseaserescol input[type="text"]').val().toLowerCase();
            var searchSynonym = $('#searchSynonym').is(':checked');
            var exactSearch = $('#exactSearch').is(':checked');
            $('.ui-autocomplete').hide();
            jQuery.get('manageTermsajaxDiseaseSearch.action?diseaseSearchTerm='+searchTerm+'&searchSynonyms='+searchSynonym+'&exactSearch='+exactSearch, function(value) {
            	self.displaySearchResults(value);
            });
        },
        
        displaySearchResults : function (values){
        	var searchTerm = $('#diseasesSection .diseaserescol input[type="text"]').val().toLowerCase();
        	var searchSynonym = $('#searchSynonym').is(':checked');
        	var self = this;
        	var textTip = 'Add this disease to your selection';        	
			var imageTip = 'Click to open this item in the tree view';
			var imageFile = paApp.imagePath + '/tree_32.png';
			var detailsImageFile = paApp.imagePath + '/details.png';
			var detailsImageTip = 'Click to open the NCIt term details';
		    
			//alert (values);
			diseases = JSON.parse(values);
        	var breadcrumbsContainer = $('#diseasebreadcrumbs');
        	breadcrumbsContainer.empty();
        	var html = '';
        	
    		if(diseases.length > 0){
				if(searchSynonym){
					breadcrumbsContainer.append('<div><div style="float:left; width: 53%;"><h2>Preferred Term</h2></div><div style="margin-left:54%;"><h2>Synonyms</h2></div></div>');
				}else{
					breadcrumbsContainer.append('<div><div style="float:left; width: 100%;"><h2>Preferred Term</h2></div></div>');
				}
				for (var d in diseases) {
					var disease = diseases[d];
	     			//alert(disease);
	     			if(disease.id) {
		     			html += '<div class="breadcrumbItemPane"><div class="breadcrumbItemBox">';
						html += '<div class="breadcrumbFeaturedElement" >';
						html += '<div class="breadcrumbFeaturedElementText" title="'+textTip+ '" onclick="selectDisease('+disease.id+',\''+self.escapeHTML(disease.name)+'\')">' + self.hightligtMatch(self.escapeHTML(disease.name),searchTerm ) + '</div>';
						html += '<div class="breadcrumbFeaturedElementTreeLink" id="'+disease.id+'"><a href="#" title="'+imageTip+'"><img src="'+imageFile+'"></img></a></div>';
						html += '<div class="breadcrumbFeaturedElementImageLink"><a href="#" onclick="openNCItWindow(\''+disease.ncitId+'\')" title="'+detailsImageTip+'"><img src="'+detailsImageFile+'"></img></a></div>';
						html += '<div class="breadcrumbFeaturedElementImageLink" onclick="selectDisease('+disease.id+',\''+ self.escapeHTML(disease.name)+'\')"><a href="#"><img src="'+paApp.imagePath +'/ico_add.gif"></img></a></div>';
						html += '</div></div>';
						
						if(searchSynonym && disease.alterNames.length > 0){					
							html += '<div class="breadcrumbSynBox">'
							for( var k=0; k<disease.alterNames.length; k++ ) {
								html += '<div class="breadcrumbFeaturedElement"> <div class="breadcrumbSynonymElementText" title="Synonym">';
								html += self.hightligtMatch(self.escapeHTML(disease.alterNames[k]),searchTerm );
								html += '</div></div>';
								if(k != disease.alterNames.length - 1 ){ // Insert a separator except for the last one
								  html += '<div class="breadcrumbElementSeparator"> , </div>'
								}
							}
							html += '</div>'
						}
						html += '</div><hr width="100%">'
	     			}
	     		}
	     		breadcrumbsContainer.append(html);
	     		if(!searchSynonym){
					$('.breadcrumbItemBox').width('97%');
				}
			}	
    		
     		this.updateQuickresultsCount(searchTerm, diseases.length);
     		
     		 $('.breadcrumbFeaturedElementTreeLink').click(function(e) {     			 
                 self.showTree($(this).attr('id'));
                 e.preventDefault();
             });
     		 
     		 $('#selectedDiseases li a').click(function(e) {
     			var selid = $(this).parent().attr('id');
     			alert(selid);
     			$(this).parent().remove();
     			$('#pdqDiseases option[value="'+selid+'"]').first().remove();
     		 });
        },
        
        hightligtMatch: function (term, searchString){
        	var matchIdx = term.toLowerCase().indexOf(searchString)
        	if(matchIdx >=0 ){
        		return term.substr(0,matchIdx)+'<span class="breadcrumbHighlight">'+term.substr(matchIdx,searchString.length)+'</span>'+term.substr(matchIdx+searchString.length)
        	}else{
        		return term;
        	}
        },
        
        adjustBreadcrumbAppearance : function () {
            var regTextHeight = $('.breadcrumbElementText').outerHeight();
            $('.breadcrumbElementImageLink').height(regTextHeight);
    
            var featTextHeight = $('.breadcrumbFeaturedElementText').outerHeight();
            $('.breadcrumbFeaturedElementImageLink').height(featTextHeight);
    
            var regLinkHeight = $('.breadcrumbElementImageLink A').outerHeight();
            var regMarginTop = (regTextHeight-regLinkHeight)/2
            $('.breadcrumbElementImageLink A').css({'margin-top': ''+regMarginTop+'px'});
    
            var featLinkHeight = $('.breadcrumbFeaturedElementImageLink A').outerHeight();
            var featMarginTop = (featTextHeight-featLinkHeight)/2
            $('.breadcrumbFeaturedElementImageLink A').css({'margin-top': ''+featMarginTop+'px'});
        },
        
        addSelection : function (self2, curr) {
            var selectedElement = $('<div></div>');
            var id = curr.attr('id').match(/breadcrumb_box\d+_id(\d+)/)[1];
            selectedElement.attr('id', id);
            var parentIds = $.map(curr.prevAll('div[id^=breadcrumb_box]'), function(val,i) {
                return $(val).attr('id').match(/breadcrumb_box\d+_id(\d+)/)[1];
            });
            selectedElement.data('parentIds', parentIds);
            selectedElement.append(curr.prevAll().clone().get().reverse());
            selectedElement.append(curr.find('.breadcrumbElementText,.breadcrumbFeaturedElementText').clone());
            self2.addToSelections(self2.generateSelectionItemBlock(selectedElement));
        },

        
        addToSelections : function (item) {
            var self = this;
            var total=0, distinct=0;
            $('#diseasesSection .selectionslist_body li div').each(function(index) {
                total++;
                var candidate = self.unescapeHTML($(this).html());
                if( item.html != candidate ) 
                    distinct++;
            });
            if( total == distinct ) {
                var compositeId = 'sbidd' + function(arr){var s=''; for(var i=0; i<arr.length; i++) s+= '_'+arr[i]; return s;}(item.parentIds) + '_' + item.id;
                $('#diseasesSection .selectionslist_body').prepend('<li id="'+compositeId+'"><a href="#" title="Click to remove" /><div>' + item.html + '</div></li>');
                $('#diseasesSection .selectionslist_body li#'+compositeId).data('shortId', item.id); 
                $('#pdqDiseases').append('<option value="'+item.id+'" selected="selected">'+item.name+'</option>');
            }
        },
        
        updateSelections : function (e) {
            var self = this;
            $('#diseasesSection .selectionslist_body li a').bind('click',function (e) {
                $('#pdqDiseases option[value="'+$(this).parent().data('shortId')+'"]').first().remove();
                $(this).parent().remove();
                self.updateSelectionCount();
                e.preventDefault();
            });     
            self.updateSelectionCount(); 
            if( e!=null)
                e.preventDefault();             
        },
        
        updateQuickresultsCount : function ( searchTerm, count, bInit ) {
            if( bInit ) {
                $('#diseasesSection .quickresults_count').text( 'Hint: Press <Enter> when finished typing' ).show(); 
            } else {
                if( count == 1 ) 
                    $('#diseasesSection .quickresults_count').text( '' + count + ' result for "' + searchTerm + '"' ).show(); 
                else
                    $('#diseasesSection .quickresults_count').text( '' + count + ' results for "' + searchTerm + '"' ).show();
            }
        },   
            
        //*******************************************
        //** Add Diseases to Selected (for Report) **
        //*******************************************
        generateSelectionItemBlock : function ( item ) {
            var innerHtml = '<span class="selectionElementText">';
            $.each(item.children(), function(index, value) {
                if (index < item.children().length-1) {
                    innerHtml += $(this).text();
                    return true;
                }
                return false;
            });
            innerHtml += '</span><br>';
            if (innerHtml == '<span class="selectionElementText"></span><br>') {
                innerHtml = '';
            }    
            var name = $.trim(item.children().last().text());
            innerHtml += '<span class="selectionFeaturedElement">'+name+'</span>';
            var id = item.attr('id');
            var parentIds = item.data('parentIds');
            return { 'id':id, 'parentIds':parentIds, 'name':name, 'html':innerHtml };
        },
        
        updateSelectionCount : function () {
            var count = $('#diseasesSection .selectionslist_body li').length;
            if (count == 0) {
                $('#disease_selections_count').stop(true,true).hide()
                        .removeClass('selections_count_normal').removeClass('selections_count_highlight').addClass('selections_count_normal').text( 'no selections added' ); 
            } else {
                var newText = '';
                if (count == 1) {
                    newText = '1 selection added';
                } else {
                    newText = '' + count + ' selections added';
                }
                var oldText = $('#disease_selections_count').text();
                $('#disease_selections_count').text( newText ); 
                if (oldText != newText) {
                    $('#disease_selections_count').stop(true,true).hide()
                            .removeClass('selections_count_normal').removeClass('selections_count_highlight').addClass('selections_count_highlight')
                            .show().delay(1000).switchClass('selections_count_highlight', 'selections_count_normal', 1000); 
                }   
            } 
        },               
        //***********
        //** Reset **
        //***********
        resetDiseaseFilter : function () {
            var self = this;
            $('#diseasebreadcrumbs').empty();
            $('#diseasesSection .quickresults_header_buttons input[type="checkbox"]').attr('checked', false);
            $('#diseasesSection input[id="disease"]').val('Start typing a search term...');
            self.updateQuickresultsCount('',0,true);
            $('#diseasesSection .selectionslist_body').empty();
            $('#pdqDiseases').empty();
            $('#disease_selections_count').text('');
            $('#disease_selections_count').hide();
            diseases = [];
            self.updateSelectionCount();
        },
            
        //***************************************
        //** PDQ Tree in separate pop-up dialog **
        //***************************************
        showTree : function (id) {
            var self = this;
            self.pdqDialog.dialog('open');
            $('#pdq_tree_dialog').prev().css({'background-color': '#FF8080', 'background-image': 'none', 'border': '1px solid #A06060'});
            $('.pdq-tree-highlight ins').each(function(index,value) {
                $(value).unwrap();
            });
            
           
            
            if( typeof(id) != 'undefined' ) {
            	jstreeNodeIdsToOpen = [];
            	
            	if(!$("#loading").length) {
            	
            	  var loadingImg = paApp.imagePath + "/loading.gif";	
            	 $('#pdq_tree_dialog').prev().append($('<img id="loading" style="z-index: 10000;position: fixed;top: 25%;left: 25%;overflow: auto;"'
            	            +' src="'+loadingImg+'"/>'));
            	}
            	else {
            		$("#loading").show();
            	}
            	
            	$( "#waitDialog" ).dialog( "open" );
            	$.ajax(getBranchesURL, {            		
            		data: {
            			nodeID : id
            		},
            		dataType : 'json'
            		//timeout : 30000
            	}).always(function() {
            		$( "#waitDialog" ).dialog( "close" );
            	}).done(function( jsonStr ) {   
            		$("#loading").hide();
            		$('#pdq_tree').jstree("close_all");
            		var branches = jsonStr;
            		var idsOfNodesToHighlight = [];
            		
            		$(branches).each(function() {                   
                        var thisNodeParentIds = this;
                        thisNodeParentIds = $.map( $(thisNodeParentIds), function(val,i) {
                            return '_' + $(thisNodeParentIds).toArray().slice(0,i+1).join('_');
                        });
                        
                        var thisNodeId = thisNodeParentIds[thisNodeParentIds.length-1];
                        idsOfNodesToHighlight.push(thisNodeId);                        
                        thisNodeParentIds = thisNodeParentIds.slice(0, thisNodeParentIds.length-1);
                        jstreeNodeIdsToOpen = jstreeNodeIdsToOpen.concat(thisNodeParentIds.slice(0));                        
                    }); 
            		
            		
            		
                    setJstreeOperationReady(true);
                    var interval = setInterval( function() {  
                	    if (numberOfAjaxInProgress > 0 ) {
                	    	return;
                	    }
                        if(jstreeNodeIdsToOpen.length == 0) {
                            $('#pdq_tree').jstree("deselect_all");
                            
                            $(idsOfNodesToHighlight).each(function() {
                                $('#pdq_tree').jstree("select_node", $('#ptid'+this));                                
                                $('#ptid'+this+' a:first').wrapInner('<span class="pdq-tree-highlight"></span>');
                            });                            

                            self.adjustPDQTreeDimensions();
                            clearInterval(interval);
                        } else {
                            if( isJstreeOperationReady() ) {
                                setJstreeOperationReady(false);                                
                                while (jstreeNodeIdsToOpen.length>0 
                                			&& $('#pdq_tree').jstree("is_open", $('#ptid'+jstreeNodeIdsToOpen[0]))) {
                                	 jstreeNodeIdsToOpen.splice(0,1);
                                }                                
                                if (jstreeNodeIdsToOpen.length == 0) {
                                	setJstreeOperationReady(true);
                                } else {
                                	var nodeToOpen = $('#ptid'+jstreeNodeIdsToOpen[0]);
                                	self.scrollPDQTreeNodeIntoView(nodeToOpen[0]);
                                	$('#pdq_tree').jstree("open_node", nodeToOpen, false, true);
                                }
                            }
                        }
                    }, 100 );            		
            	});
            }
            self.adjustPDQTreeDimensions();
        },
        
        generatePDQTreeHtml : function () {
            var self = this;
            var pdqTree = $('<div id="pdq_tree"></div>');
            $(function () {
                $('#pdq_tree').jstree({ 
                    'plugins' : [ 'json_data', 'themes', 'ui', 'hotkeys'],
                    'core' : { 'initially_open' : $.map( FiveAmUtil.PDQPkg.tree_initially_open, function(val,i) { return 'ptid'+val }) },
                    'json_data' : {
                        'ajax': {
            		                "url"  : treeAjaxURL,            		              
                	                "data" : function (n) {
                	                		  return {nodeID : n.attr ? n.attr("id") : 0};
                        	             	}
            		            },
                        'progressive_render' : true 
                    },
                    'themes' : { 'theme' : 'default-p' },
                    'ui' : {'select_limit' : 1, 'selected_parent_close' : 'select_parent' }
                }).bind("select_node.jstree", function (e, data) { 
                    if ( typeof(data.rslt.e) != "undefined") { // Selection generated by mouse click
                        var id = data.rslt.obj.attr('id').match(/ptid([\d_]+)/)[1];
                        var dataIds = id.substring(1).split('_');
                        id = dataIds[dataIds.length-1];
                        selectDisease(id,  data.rslt.obj.children('a').text().trim());
                    } else { // Selection comes from click on breadcrumb image link
                        self.scrollPDQTreeNodeIntoView(data.rslt.obj);
                    }
                }).bind("after_open.jstree", function (e, data) { 
                    if( data.rslt.obj.context.nodeName.toLowerCase().indexOf('document')==-1 ) { // Open was generated by mouse click
                        self.adjustPDQTreeDimensions();
                    } else {
                    	if (jstreeNodeIdsToOpen.length > 0 ) {
                    		jstreeNodeIdsToOpen.splice(0,1);
                    	}
                        setJstreeOperationReady(true);
                    }
                }).bind("after_close.jstree", function (e, data) { 
                    if( data.rslt.obj.context.nodeName.toLowerCase().indexOf('document')==-1 ) { // Close was generated by mouse click
                        self.adjustPDQTreeDimensions();
                    } else {                      
                        setJstreeOperationReady(true);
                    }
                });
            });
            return pdqTree;
        },
        
        adjustPDQTreeDimensions : function () {
            var self = this;
            var clientHeight = self.getRealPDQTreeClientHeight();
            var viewHeight = $('#pdq_tree_dialog').height();
            if( clientHeight < viewHeight )
                $('#pdq_tree').height(viewHeight);
            else
                $('#pdq_tree').height(clientHeight);
        },
        
        scrollPDQTreeNodeIntoView : function ( node ) {
            var self = this;
            var clientHeight = self.getRealPDQTreeClientHeight();
            var viewHeight = $('#pdq_tree_dialog').height();
            var nodeTop = $(node).offset().top - $('#pdq_tree').offset().top;
            var scrollTop = $('#pdq_tree_dialog').scrollTop();
            if( viewHeight < clientHeight || nodeTop<scrollTop || nodeTop>scrollTop+viewHeight  ) {
                var newScrollTop =  Math.round( nodeTop - viewHeight/2 );
                newScrollTop = newScrollTop>=0 ? newScrollTop : 0; 
                $('#pdq_tree_dialog').scrollTop( newScrollTop );
            }
        },
        
        getRealPDQTreeClientHeight : function () {
            var lastNode = $('#pdq_tree li:visible:last');
            var realHeight = 0;
            if($(lastNode).toArray().length > 0)
                realHeight = $(lastNode).offset().top + $(lastNode).height() - $('#pdq_tree').offset().top;
            return realHeight;
        },
        
        //************************
        //** Save/Restore state **
        //************************
        saveState : function() {
            var stateObj = {
                'breadcrumb_boxes': [],
                'selection_boxes': $.map( $('li[id^=sbidd]'), function(val,i) {
                    return $(val).attr('id');  
                })
            };
            var stateStr = $.toJSON(stateObj);
            setCookieForDays( "diseasesFilterState", stateStr, 2 );
        },
     
        restoreState : function() {
            var self = this;
            var stateStr = getAndDeleteCookie( 'diseasesFilterState' );
            var stateObj = $.evalJSON( stateStr );
            if( stateObj != null) {
                if( typeof(stateObj.selection_boxes) != 'undefined' && stateObj.selection_boxes != null ) {
                    $.each( $.evalJSON(stateObj.selection_boxes), function(index, value) {
                        var id = value.match(/sbidd([\d_]+)/)[1];
                        var dataIds = id.substring(1).split('_');
                        id = dataIds[dataIds.length-1];
                        dataIds.splice(dataIds.length-1,1);
                        var parentIds = dataIds.slice(0);
                        dataIds.reverse();
                        var breadcrumbsPkg = {
                            'bcItems' : [FiveAmUtil.breadcrumbsPkg.createBox( id, dataIds, FiveAmUtil.PDQPkg.pdqData )],
                            'searchTerm' : ''
                        };
                        var bcItemMarkup = $('<div></div>').buildBreadcrumbs( breadcrumbsPkg );
                        var selectedElement = bcItemMarkup.find('.breadcrumbItemBox');
                        selectedElement.attr('id', id);
                        selectedElement.data('parentIds', parentIds);
                        self.addToSelections(self.generateSelectionItemBlock(selectedElement));
                        self.updateSelections(null);
                    });
                }
            }
        },
    
        //*******************
        //** Miscellaneous **
        //*******************
        escapeHTML : function (html) {
            var escaped = html;
            var findReplace = [[/&/g, "&amp;"], [/</g, "&lt;"], [/>/g, "&gt;"], [/"/g, "&quot;"]]
            for(var item in findReplace)
                if(findReplace.hasOwnProperty(item))
                    escaped = escaped.replace(findReplace[item][0], findReplace[item][1]);  
            return escaped;
        },
        
        unescapeHTML : function (html) {
            var unescaped = html;
            var findReplace = [[/&amp;/g, "&"], [/&lt;/g, "<"], [/&gt;/g, ">"], [/&quot;/g, "\""]]
            for(var item in findReplace)
                if(findReplace.hasOwnProperty(item))
                    unescaped = unescaped.replace(findReplace[item][0], findReplace[item][1]);  
            return unescaped;
        }
    };
    
    //******************
    //** On DOM Ready **
    //******************
    $(function() {
        $.jstree._themes = paApp.stylePath + "/disease/jstree/themes/";
        var df = $.diseasesFilter;
        
        // Clicking inside textboxes highlights the content
        $('input[type="text"]').bind('click',function (e) {
            $(this).focus();
            $(this).select();
            e.preventDefault();
        });
        
        $('#disease_selections_count').hide();
        $('#diseasesSection .quickresults_count').show();
        $('#diseasesSection .quickresults_header_buttons input[type="checkbox"]').attr('checked', false);
        df.restoreState();
        
        //**********
        //** Tabs **
        //**********
        $('#reporttabs li a').hover(
            function () {
                $(this).animate({left:20}, 300, function (){
                    $(this).animate({left:0}, 50);
                });
            }, 
            function () {
            }
        );
        
        $('ul#reporttabs li a').bind('click',function (e) {
            var linkIndex = $('ul#reporttabs li a').index(this);
            $('ul#reporttabs li a').removeClass('reporttab-active');
            $('.reporttab:visible').hide();
            $('.reporttab:eq('+linkIndex+')').show();
            $(this).addClass('reporttab-active');
            e.preventDefault();
        });
        
        $('.reporttab:first').show();   
        $('#reporttabs li a:first').addClass('reporttab-active');
        
        //*********************
        //** Category Toggle **
        //*********************
        $('.categorywrapper h2 a').bind('click',function (e) {
            $(this).parents('.categorywrapper').find('.category').slideToggle('slow', function() {});
            e.preventDefault();
        });
           
        $('.category:eq(1)').show();    
        
        if( $('.quickresults').height() != 0 )
            $('.selectionslist').height( $('.quickresults').height() - $('.selectionslist_body').padding().top - $('.selectionslist_body').padding().bottom );
        
        //*******************************
        //** Search for Disease in PDQ **
        //*******************************
        $('#diseasesSection .diseaserescol input[type="text"]').autocomplete({
            source : function(request, response) {
                var results = $.ui.autocomplete.filter(autoCompleteList, request.term);
                response(results.slice(0, 8).sort());
            }
        });
        
        $('#diseasesSection .search_inner_button').bind('click',function (e) {
            df.searchDiseases();
            e.preventDefault();
        });
        
        $('#diseasesSection .diseaserescol input[type="text"]').keypress(function(e) {
            if(e.keyCode == 13) {
                df.searchDiseases();
                e.preventDefault();
            }
        });
        
        //****************
        //** Select All **
        //****************
        $('#diseasesSection .quickresults_header_buttons input[type="checkbox"]').bind('click',function (e) {
            $('#diseasesSection .quickresults_body input[type="checkbox"]').attr('checked', $(this).is(':checked'));   
        });
        
        //*******************************************
        //** Add Diseases to Selected (for Report) **
        //*******************************************
        $('.quickresults_header_buttons #add_all_disease').bind('click',function (e) {
        	addAllDiseases();
        });
        
        //***********
        //** Reset **
        //***********
        $('.quickresults_header_buttons #reset_disease').bind('click',function (e) {
            df.resetDiseaseFilter();
            e.preventDefault();
        });
        
        //***************************************
        //** PDQ Tree in separate pop-up dialog **
        //***************************************
        df.pdqDialog = $('<div id="pdq_tree_dialog"></div>')
                .html( df.generatePDQTreeHtml() )
                .dialog({
                    autoOpen: false,
                    modal: false, 
                    title: 'NCIt/CTRP Tree',
                    position: [30,5],
                    width: 570,
                    height: 300
                });
        $('.ui-dialog .ui-dialog-content').css({'padding': '.4em .3em .3em .3em'});
                
        $('#show_tree_disease').click(function(e) {
            df.showTree();
            e.preventDefault();
        });
        
        $(document).bind("ajaxSend", function(){
        	numberOfAjaxInProgress++;
        }).bind("ajaxComplete", function(){
        	numberOfAjaxInProgress--;
        });
        
    });
})(jQuery);


FiveAmUtil = {
	    breadcrumbsPkg : {
	        clone : function (bcItem) {
	            var newBCItem = [];
	            if (bcItem == undefined && bcItem.length == undefined )
	                return newBCItem;
	            for ( var i=0 ; i < bcItem.length ; i++ )
	                newBCItem.push(eval(bcItem[i]));                
	            return newBCItem;
	        },
	    
	        createBox : function( id, parentIds, pdqData ) {
	            var bcItem = [];
	            bcItem.push({'id': id, 'name': pdqData[id].name, 'hasChildren':  pdqData[id].hasChildren, 'isFeatured': true});
	            for( var i=0; i<parentIds.length; i++ ) 
	                bcItem.push({'id': parentIds[i], 'name': pdqData[parentIds[i]].name, 'hasChildren': true, 'isFeatured': false});
	            return bcItem.reverse();
	        },

	        createPackage : function( searchTerm, searchResultsPDQ, pdqData ) {
	            var bcItems = [];
	            var populateBreadcrumbItem = function( bcItem, pdqData, pdqItemId, isFeatured, bcItems ) {
	                var pdqItem = pdqData[ pdqItemId ];
	                if(isFeatured){
	                	bcItem.push({'id':pdqItemId, 'name':pdqItem.name, 'hasChildren':pdqItem.hasChildren, 'isFeatured':isFeatured, 'alterNames':pdqItem.alterNames});
	            	}else{
	            		bcItem.push({'id':pdqItemId, 'name':pdqItem.name, 'hasChildren':pdqItem.hasChildren, 'isFeatured':isFeatured});
	            	}
	                if ( pdqItem.parentId != null && !(pdqItem.parentId.length==1 && pdqItem.parentId[0]==null) ) {
	                    for (var i=0 ; i < pdqItem.parentId.length ; i++ ) {
	                        populateBreadcrumbItem(FiveAmUtil.breadcrumbsPkg.clone(bcItem), pdqData, pdqItem.parentId[i], false, bcItems );
	                    }
	                } else {
	                    bcItems.push(bcItem.reverse());
	                }
	            }; 
	            for( var i=0; i<searchResultsPDQ.length; i++ ) {
	                var bcItem = [];
	                var pdqItemId = searchResultsPDQ[i];
	                populateBreadcrumbItem( bcItem, pdqData, pdqItemId, true, bcItems );
	            }
	            return {
	                'bcItems' : bcItems,
	                'searchTerm' : searchTerm
	            };
	        },
	    },  
	    
	    PDQPkg: {
	        pdqData : {},
	        pdqDataDictionary : [],
	        
	        initPdqData : function() {
	            this.pdqData = {};
	            for( i in diseaseTree ) {
	                if(!diseaseTree.hasOwnProperty(i))
	                    continue;
	                var pdqItem = diseaseTree[i];
	                if( !this.pdqData.hasOwnProperty( pdqItem.id )){
	                	if(pdqItem.alterNames){
	                    		this.pdqData[pdqItem.id] = {'name':pdqItem.name.toLowerCase(), 'parentId':[pdqItem.parentId], 'hasChildren':pdqItem.hasChildren, 'alterNames':pdqItem.alterNames};
	                    		
	                    	}else{
	                    		this.pdqData[pdqItem.id] = {'name':pdqItem.name.toLowerCase(), 'parentId':[pdqItem.parentId], 'hasChildren':pdqItem.hasChildren, 'alterNames':[]};
	                    		
	                    	}
	                }else{
	                    this.pdqData[pdqItem.id].parentId.push(pdqItem.parentId);
	                }
	            }
	        },
	        
	        initPdqDictionary : function() {
	            var hmDictionary = {};
	            for( var i in this.pdqData ) {
	                if(!this.pdqData.hasOwnProperty(i))
	                    continue;
	                var pdqItem = this.pdqData[i];
	                var words = pdqItem.name.split(' ');
	                for( j in words )
	                    if(words.hasOwnProperty(j))
	                        hmDictionary[words[j]]=1;
	            }
	            for( word in hmDictionary )
	                if(hmDictionary.hasOwnProperty(word))
	                    this.pdqDataDictionary.push(word);
	        },

	        init : function() {
	            this.initPdqData();
	            this.initPdqDictionary();
	        },  

	        searchPDQ : function(term, synonym) {
	            var results = new Array();
	            for( var i in this.pdqData ) {
	                if(!this.pdqData.hasOwnProperty(i))
	                    continue;
	                var pdqItem = this.pdqData[i];
	                if(pdqItem.name.indexOf(term)!=-1) {                	
	                    results.push([pdqItem.name, i]);
	                }else if (synonym){
	                	// Search synonyms
	                	for (var j=0; j < pdqItem.alterNames.length; j++) {
	                		if(pdqItem.alterNames[j].indexOf(term)!=-1) {
	                		     results.push([pdqItem.name , i]);
	                		     break;
	                		}
	                	}
	                }
	            }
	            var sortedResults = results.sort();
	            var ids=[];
	            for (i=0;i<sortedResults.length;i++){
	            	ids.push(sortedResults[i][1]);
	            }
	            return ids;
	        },

	        // When PDQ data is presented as a tree which nodes will be opened on load  
	        tree_initially_open: [ '_101', '_101_1873']     
	    }
	};

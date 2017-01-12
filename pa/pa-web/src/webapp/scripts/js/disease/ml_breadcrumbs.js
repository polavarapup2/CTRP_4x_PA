
//************************
//** Breadcrumbs Markup **
//************************
/*
<div class="breadcrumbItemPane">
	<input type="checkbox"/>				
	<div class="breadcrumbItemBox">	
		<div class="breadcrumbElement">	
			<div class="breadcrumbElementText">	
			<div class="breadcrumbElementImageLink">	
		</div>	
		<div class="breadcrumbElementSeparator"/>	
		<div class="breadcrumbElement">...</div>
		<div class="breadcrumbElementSeparator"/>	
		...	
		<div class="breadcrumbElementSeparator"/>	
		<div class="breadcrumbFeaturedElement">	
			<div class="breadcrumbFeaturedElementText">	
			<div class="breadcrumbFeaturedElementImageLink">	
		</div>	
	</div>									
</div>
*/   
    
(function($){
	$.fn.buildBreadcrumbs = function(settings){
		var breadcrumbsContainer = $(this);
		var localSettings = $.extend({}, $.fn.buildBreadcrumbs.defaults, settings);

		function _build(showSynonyms){
			var textTip = 'Add this item to your selections for the search';
			var imageTip = 'Click to open this item in the tree view';
			var imageFile = paApp.imagePath + '/tree_32.png';
						
			breadcrumbsContainer.empty();
			var bcItems = localSettings.bcItems;
			var term = localSettings.searchTerm;
			if(bcItems.length > 0){
				if(showSynonyms){
					breadcrumbsContainer.append('<div><div style="float:left; width: 53%;"><h2>Preferred Term</h2></div><div style="margin-left:54%;"><h2>Synonyms</h2></div></div>')
				}else{
					breadcrumbsContainer.append('<div><div style="float:left; width: 100%;"><h2>Preferred Term</h2></div></div>')
				}
			}
			for( var i in bcItems ) {
				if(!bcItems.hasOwnProperty(i))
					continue;
				var bcItem = bcItems[i];

				var featured;
				var html = '<div class="breadcrumbItemPane"><div class="breadcrumbItemBox">';
				if( bcItem[0].isFeatured ) {
					html += '<div id="breadcrumb_box'+i+'_id'+bcItem[0].id+'" class="breadcrumbFeaturedElement">'+
								'<div class="breadcrumbFeaturedElementText" title="'+textTip+'">' + bcItem[0].name + '</div>';
					if (bcItem[0].hasChildren)
						html += '<div class="breadcrumbFeaturedElementImageLink"><a href="#" title="'+imageTip+'"><img src="'+imageFile+'"></img></a></div>';
					html += '</div>';
					featured =  bcItem[0];
				} else {
					html += '<div id="breadcrumb_box'+i+'_id'+bcItem[0].id+'" class="breadcrumbElement">'+
							'<div class="breadcrumbElementText" title="'+textTip+'">' + bcItem[0].name + '</div>'+
							'<div class="breadcrumbElementImageLink"><a href="#" title="'+imageTip+'"><img src="'+imageFile+'"></img></a></div>'+
							'</div>';
				}
				for( var j=1; j<bcItem.length; j++ ) {
					html += '<div class="breadcrumbElementSeparator"> &gt; </div>';
					if( bcItem[j].isFeatured ) {
						html += '<div id="breadcrumb_box'+i+'_id'+bcItem[j].id+'" class="breadcrumbFeaturedElement">'+
								'<div class="breadcrumbFeaturedElementText" title="'+textTip+'">' + bcItem[j].name + '</div>';
						if (bcItem[j].hasChildren)
							html += '<div class="breadcrumbFeaturedElementImageLink"><a href="#" title="'+imageTip+'"><img src="'+imageFile+'"></img></a></div>';
						html += '</div>';
						featured =  bcItem[j];
					} else {
						html += '<div id="breadcrumb_box'+i+'_id'+bcItem[j].id+'" class="breadcrumbElement">'+
								'<div class="breadcrumbElementText" title="'+textTip+'">' + bcItem[j].name + '</div>'+
								'<div class="breadcrumbElementImageLink"><a href="#" title="'+imageTip+'"><img src="'+imageFile+'"></img></a></div>'+
								'</div>';
					}
				}
				
				
				html += '</div>';
				if(showSynonyms && featured.alterNames.length > 0){					
					html += '<div class="breadcrumbSynBox">'
					for( var k=0; k<featured.alterNames.length; k++ ) {
						html += '<div class="breadcrumbFeaturedElement"> <div class="breadcrumbSynonymElementText" title="Synonym">';
						html += featured.alterNames[k];
						html += '</div></div>';
						if(k != featured.alterNames.length - 1 ){ // Insert a separator except for the last one
						  html += '<div class="breadcrumbElementSeparator"> , </div>'
						}
					}
					html += '</div>'
				}
				html += '</div><hr width="100%">'
				breadcrumbsContainer.append(html);
			}
			
			if(!showSynonyms){
				$('.breadcrumbItemBox').width('97%');
			}
			$('.breadcrumbFeaturedElement').each( function() {
				$(this).html( $(this).html().replace(term,'<span class="breadcrumbHighlight">'+term+'</span>') );
			});
			$('.breadcrumbSynonymElement').each( function() {
				$(this).html( $(this).html().replace(term,'<span class="breadcrumbHighlight">'+term+'</span>') );
			});
		};
		
		//    Entry point
		_build($('#searchSynonym').is(':checked'));
		return breadcrumbsContainer;	
	};
	
	$.fn.buildBreadcrumbs.defaults = {
		showSpeed : 'fast',
		hideSpeed : ''
	};
})(jQuery);
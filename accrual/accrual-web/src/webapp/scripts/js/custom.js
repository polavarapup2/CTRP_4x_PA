	/* affix the navbar after scroll below header */

		if ($('#nav').length > 0) {
	        var stickyHeaderTop = $('#nav').offset().top;
	
	        $(window).scroll(function(){
	                if( $(window).scrollTop() > stickyHeaderTop ) {
	                        $('#nav').css({position: 'fixed', top: '-1px'});
	                        $('#stickyalias').css('display', 'block');
	                } else {
	                        $('#nav').css({position: 'static', top: '0px'});
	                        $('#stickyalias').css('display', 'none');
	                }
	        });

		}
//<![CDATA[ 

// This function will be executed when the user scrolls the page.
$(window).scroll(function(e) {
	
	if ($(".scroller_anchor").length == 0) return; // no scroller. terminate now.
	
    // Get the position of the location where the scroller starts.
    var scroller_anchor = $(".scroller_anchor").offset().top;
    
    // Check if the user has scrolled and the current position is after the scroller's start location and if its not already fixed at the top 
    if ($(this).scrollTop() >= scroller_anchor && $('.scroller').css('position') != 'fixed') 
    {    // Change the CSS of the scroller to hilight it and fix it at the top of the screen.
        $('.summary-box').css({
            'position': 'fixed',
            'top': '30px',
			'left': '0px',
			'border-radius': '0',
			
        });
        // Changing the height of the scroller anchor to that of scroller so that there is no change in the overall height of the page.
        $('.scroller_anchor').css('height', '100px');
    } 
    else if ($(this).scrollTop() < scroller_anchor && $('.summary-box').css('position') != 'relative') 
    {    // If the user has scrolled back to the location above the scroller anchor place it back into the content.
        
        // Change the height of the scroller anchor to 0 and now we will be adding the scroller back to the content.
        $('.scroller_anchor').css('height', '0px');
        
        // Change the CSS and put it back to its original position.
        $('.summary-box').css({
            'position': 'relative',
			'top': '0px',
			'padding-left': '15px',
			'border-radius': '5px',
			
        });
    }
});
//]]> 


$(document).ready(function() {
$('.navbar-nav .dropdown').hover(function() {
  $(this).find('.dropdown-menu').first().stop(true, true).delay(0).slideDown(200);
}, function() {
  $(this).find('.dropdown-menu').first().stop(true, true).delay(100).slideUp(200)
});

   jQuery(function ($) {
        $('[rel=tooltip]').tooltip()
    });
   
   jQuery.timeoutDialog(null);
});

//Multi-level Dropdown
$(function(){
	$(".dropdown-menu > li > a.trigger").on("mouseover",function(e){
		var current=$(this).next();
		var grandparent=$(this).parent().parent();
		if($(this).hasClass('left-caret')||$(this).hasClass('right-caret'))
			$(this).toggleClass('right-caret left-caret');
		grandparent.find('.left-caret').not(this).toggleClass('right-caret left-caret');
		grandparent.find(".sub-menu:visible").not(current).hide();
		current.toggle();
		e.stopPropagation();
	});
	$(".dropdown-menu > li > a:not(.trigger)").on("mouseoff",function(){
		var root=$(this).closest('.dropdown');
		root.find('.left-caret').toggleClass('right-caret left-caret');
		root.find('.sub-menu:visible').hide();
	});
});

//Collapse / Expand
$('.expandcollapse').click(function() {

        var newstate = $(this).attr('state') ^ 1,
            icon = newstate ? "plus" : "minus",
            text = newstate ? "Expand" : "Collapse";
    
        // if state=0, show all the accordion divs within the same block (in this case, within the same section)
        if ( $(this).attr('state')==="0" ) {
            console.log('1');
            $(this).siblings('div').find('div.accordion-body.in').collapse('hide');
			$('a.accordion-toggle').each(function() {
				$(this).addClass('collapsed');
			});
        }
        // otherwise, collapse all the divs
        else {
            console.log('2');
            $(this).siblings('div').find('div.accordion-body:not(.in)').collapse('show');
			$('a.accordion-toggle').each(function() {
				
				$(this).removeClass('collapsed')
			});
        }

        $(this).html("<i class=\"fa-" + icon + "-circle\"></i> " + text +" All");

        $(this).attr('state',newstate)

    });

    $('a[data-toggle="tab"]').on('shown', function (e) {

        var myState = $(this).attr('state'),
            state = $('.expandcollapse').attr('state');

        if(myState != state) {
          toggleTab($(this).prop('hash'));
          $(this).attr('state',state);
        }

    })

    function toggleTab(id){

        $(id).find('.collapse').each(function() {
            $(this).collapse('toggle');
          });

    }
// Tooltip
	jQuery(function ($) {
        $('[rel=tooltip]').tooltip()
    });
	
// Popover
	jQuery(function ($) {
        $('[rel=popover]').popover()
    });
	
// Date Picker
$(function() {
    $('.datetimepicker').datetimepicker({
      pickTime: false
    });
  });

	


jQuery(function ($) {
	$('#popover').popover({
		container: 'body',
		trigger: "hover"
	});   
 });


// Increase/Decrease text size 

$('#incfont').click(function(){    
        curSize= parseInt($('#wrap').css('font-size')) + 2;
  if(curSize<=19)
        $('#wrap').css('font-size', curSize);
        });  
  $('#decfont').click(function(){    
        curSize= parseInt($('#wrap').css('font-size')) - 2;
  if(curSize>=11)
        $('#wrap').css('font-size', curSize);
}); 

// Show/Hide Search Criteria  
  
  function toggledisplay (it, box) {
var vis = (box.checked) ? "none" : "block";
document.getElementById(it).style.display = vis;
}

// Multi-select Dropdown  
	$('#multi-select').multiselect({
			        	includeSelectAllOption: true
			        });
					
					
					
					
					
 


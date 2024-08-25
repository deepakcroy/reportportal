//deepak
function printWidget(elmId) 
{

  var divToPrint=document.getElementById(elmId).parentElement;
  var newWin=window.open('','Print-Window');
  newWin.document.open();
  newWin.document.write('<html><body onload="window.print()">'+divToPrint.innerHTML+'</body></html>');
  newWin.document.close();
  setTimeout(function(){newWin.close();},10);

}
(function ($, window, document, undefined) {
		// dpkkry...........Start..
		//SideBar Upper Level Accrodion Selection
	    var selSideMenu =  $("#mainSideMenu .accordion-group .leaflevel.active").parent().parent().find(".accordion-heading").eq(0);
	    $(selSideMenu).addClass("active").find(".accordion-toggle").eq(0).trigger("click");
	
	    //Rest Bredcrum preparation
	    //$("#breadcrumbs").append();
	    var bCrumbRest="";
	    if($("#mainSideMenu .accordion-heading.active").length>0)
    	{
	    	 bCrumbRest+="<li class='dropdown'><a href='javascript:void(0);'>"+$("#mainSideMenu .accordion-heading.active").eq(0).text().trim()+" </a> <span class='divider'>/</span>";
	    	 bCrumbRest+="<ul class='dropdown-menu'>";
	    	 $("#mainSideMenu .accordion-heading.active").parent().find(".accordion-content .leaflevel").each(function(){
	    		 bCrumbRest+= "<li><a href='"+$(this).find("a").eq(0).attr("href")+"'>"+$(this).text().trim()+"</a></li>";
	    	 });
             bCrumbRest+="</ul></li>";
    	}
	    if( $("#mainSideMenu .leaflevel.active a").length>0)
    	{
	    	var actMenu = $("#mainSideMenu .leaflevel.active a").eq(0);
		    var actMenuParts = $(actMenu).attr("href").split("/");
		    bCrumbRest+="<li class='active'>"+$(actMenu).text().trim()+" > "+actMenuParts[actMenuParts.length-1]+"</li>";
    	}
	    
	    $("#breadcrumbs").append(bCrumbRest);
		
		//On Sidebar Collapse and reload, page error resolver
		$('body').removeClass('sidebar-hidden');
	    $.cookie('sidebar-pref', null, {
	            expires: 30
	    });
	
	
	    //dpkkry............End
	    
        // REMOVE CSS FROM ELEMENT
        // ------------------------------------------------------------------------------------------------ * --> 
        $.fn.extend({
                removeCss: function (cssName) {
                        return this.each(function () {
                                var curDom = $(this);
                                jQuery.grep(cssName.split(","),

                                function (cssToBeRemoved) {
                                        curDom.css(cssToBeRemoved, '');
                                });
                                return curDom;
                        });
                }
        });

        // SIDEBAR RESIZE - CONVERT NAV
        // ------------------------------------------------------------------------------------------------ * --> 
        $(window).resize(function () {
                if($(window).width() < 767) {
                        $('.sidebar').addClass('collapse')
                        $('.sidebar, .footer-sidebar').removeCss('display');
                }
                if($(window).width() > 767) {
                        $('.sidebar').removeClass('collapse');
                        $('.sidebar').removeCss('height');

                        if(!$('body').hasClass('sidebar-hidden')) {
                                $('.sidebar, .footer-sidebar').css({
                                        'display': 'block'
                                });
                        } else {
                                $('.sidebar, .footer-sidebar').css({
                                        'display': 'none'
                                });
                        }
                }
        });
        $(function () {
                if($(window).width() < 767) {
                        $('.sidebar').addClass('collapse');
                }
                if($(window).width() > 767) {
                        $('.sidebar').removeClass('collapse');
                        $('.sidebar').removeCss('height');
                }
        });

        // SIDEBAR - SHOW OR HIDDEN
        // ------------------------------------------------------------------------------------------------ * -->        
        function showSidebar() {
                $('body').removeClass('sidebar-hidden');
                $.cookie('sidebar-pref', null, {
                        expires: 30
                });
                window.dispatchEvent(new Event('resize')); //will not work in load browser deepak
        };
		
		function hideSidebar() {
			
                $('body').addClass('sidebar-hidden');
                $.cookie('sidebar-pref', 'sidebar-hidden', {
                        expires: 30
                });
                window.dispatchEvent(new Event('resize')); //will not work in load browser deepak
        };
		
        $("#btnToggleSidebar").click(function () {
                $(this).toggleClass('fontello-icon-resize-full-2 fontello-icon-resize-small-2');
                $(this).toggleClass('active');
                $('#main-sidebar, #footer-sidebar').animate({
                        width: 'toggle'
                }, 0);
                //$('body').toggleClass('sidebar-display sidebar-hidden');
                if($('body').hasClass('sidebar-hidden')) {
                        showSidebar();
                } else {
                        hideSidebar();
                }

        	    $(".ct-widget").each(function(){ $(this).highcharts().reflow()});
        });

        // auto-load preference
        $('body').addClass($.cookie('sidebar-pref'));

        // SIDEBAR - CHANGE SIDEBAR
        // ------------------------------------------------------------------------------------------------ * -->
        $("#btnChangeSidebar").click(function () {
                $(this).toggleClass('fontello-icon-login fontello-icon-logout');
                $('body').toggleClass('sidebar-left sidebar-right');
                $('#mainSideMenu .chevron').toggleClass('fontello-icon-right-open-3 fontello-icon-left-open-3');
                $(this).toggleClass('active');
        });

        // SIDEBAR - CHANGE SIDEBAR COLOR
        // ------------------------------------------------------------------------------------------------ * -->
        $("#btnChangeSidebarColor").click(function () {
                $('#main-sidebar').toggleClass('sidebar-inverse');
        });

        // SCROLL - NICESCROLL
        // ------------------------------------------------------------------------------------------------ * -->
        // The document page (body)
        /*$("html").niceScroll({
					cursoropacitymin:0.1,
					cursoropacitymax:0.9,
					cursorcolor:"#adafb5",
					cursorwidth:"8px",
					cursorborder:"",
					cursorminheight:100,
					cursorborderradius:"8px",
					usetransition:600,
					background:"",
					railoffset:{top:10,left:-3},
					bouncescroll: true	
				}); */
				
				$("#main-sidebar").niceScroll({
					cursoropacitymin:0.1,
					cursoropacitymax:0.4,
					cursorcolor:"#adafb5",
					cursorwidth:"6px",
					cursorborder:"",
					cursorborderradius:"6px",
					usetransition:600,
					background:"",
					railoffset:{top:10,left:-1},
					bouncescroll: true
				});
				$(".scrollBox").niceScroll({
					cursoropacitymin:0.1,
					cursoropacitymax:0.4,
					cursorcolor:"#adafb5",
					cursorwidth:"6px",
					cursorborder:"",
					cursorborderradius:"6px",
					usetransition:600,
					background:"",
					railoffset:{top:10,left:-1},
					bouncescroll: true
				});

        // SCROLL TOP PAGE
        // ------------------------------------------------------------------------------------------------ * -->
        $(window).scroll(function () {
                if($(this).scrollTop() > 100) {
                        $('#btnScrollup').fadeIn('slow');
                } else {
                        $('#btnScrollup').fadeOut(600);
                }
        });

        $('#btnScrollup').click(function () {
                $("html, body").animate({
                        scrollTop: 0
                }, 500);
                return false;
        });

})(jQuery, this, document);
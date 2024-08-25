(function ($, window, document, undefined) {
        // COLLAPSE - WIDGET HEADER
        // ------------------------------------------------------------------------------------------------ * -->
        // Collapsible widget	
	//$(".ct-widget").each(function(){ $(this).highcharts().reflow()});
        $('.widget-content.collapse')
                .on('shown', function (e) {
                $(e.target)
                        .parent('.widget-collapsible')
                        .children('.widget-header')
                        .removeClass('collapsed');
                $(e.target)
                        .prev('.widget-header')
                        .find('.widget-toggle')
                        .toggleClass('fontello-icon-publish fontello-icon-window');
        });

        $('.widget-content.collapse')
                .on('hidden', function (e) {
                $(e.target)
                        .parent('.widget-collapsible')
                        .children('.widget-header')
                        .addClass('collapsed');
                $(e.target)
                        .prev('.widget-header')
                        .find('.widget-toggle')
                        .toggleClass('fontello-icon-window fontello-icon-publish');
        });
        //Help
        $("#btnHelp").click(function(){
        	$('.gritter-item-wrapper').remove();
        	//$.gritter.removeAll();


        	$.extend($.gritter.options, { 
        	    position: 'bottom-right', // defaults to 'top-right' but can be 'bottom-left', 'bottom-right', 'top-left', 'top-right' (added in 1.7.1)
        	    fade_in_speed: 'medium', 
        	    fade_out_speed: 2000, 
        	    time: 6000 
        	});
        	$.gritter.add({
    			title: 'About Us......!',
    			text: 'This softwre is developed and maintained by <a target="_blank" href="http://www.aditri.net">www.aditri.net</a>',
    			image: location.origin+"/"+location.pathname.split('/')[1]+'/assets/img/system/nav2.png',
    			sticky: true,
    			time: 1500, 
    			position: 'bottom-right',
    			class_name: 'gritter-dark' //gritter-light
    		});
    		return false;
        });
        //Settings
        $("#btnSetting").click(function(){
        	$('.gritter-item-wrapper').remove();
        	//$.gritter.removeAll();


        	$.extend($.gritter.options, { 
        	    position: 'bottom-right', // defaults to 'top-right' but can be 'bottom-left', 'bottom-right', 'top-left', 'top-right' (added in 1.7.1)
        	    fade_in_speed: 'medium', 
        	    fade_out_speed: 2000, 
        	    time: 6000 
        	});
        	$.gritter.add({
    			title: 'Theme Change......!',
    			text: 'Yet to implement, please have patience till then.',
    			image: location.origin+"/"+location.pathname.split('/')[1]+'/assets/img/demo/demo-avatar9606.jpg',
    			sticky: true,
    			time: 1500, 
    			position: 'bottom-right',
    			class_name: 'gritter-dark'
    		});
    		return false;
        });

        // BREADCRUMBS
        // ------------------------------------------------------------------------------------------------ * -->
        $('#breadcrumbs').xBreadcrumbs();

        // BOOTSTRAP DATEPICKER
        // ------------------------------------------------------------------------------------------------ * -->
		$('.datepicker').datepicker({
			format: 'mm-dd-yyyy'
		});
        // BOOTSTRAP DATERANGEPICKER
        // ------------------------------------------------------------------------------------------------ * -->
        $('.datepicker-range').daterangepicker();
        // BOOTSTRAP TIMEPICKER
        // ------------------------------------------------------------------------------------------------ * -->
        $('.timepicker').timepicker({
                minuteStep: 5,
                showInputs: false,
        });
		//RADIO, CHECKBOX etc. STYLE
        $("input.checkbox, input.radio, input:file.input-file").uniform({
            radioClass: 'radios' // edited class - the original radio
    	});
		
        // BOOTSTRAP BUTTON TOGGLE CHANGE COLOR ON ACTIVE
        // ------------------------------------------------------------------------------------------------ * -->
        $('.btn-group > .btn, .btn[data-toggle="button"]').click(function () {

			if($(this).attr('class-toggle') != undefined && !$(this).hasClass('disabled')) {
				var btnGroup = $(this).parent('.btn-group');

				if(btnGroup.attr('data-toggle') == 'buttons-radio') {
						btnGroup.find('.btn').each(function () {
								$(this).removeClass($(this).attr('class-toggle'));
						});
						$(this).addClass($(this).attr('class-toggle'));
				}

				if(btnGroup.attr('data-toggle') == 'buttons-checkbox' || $(this).attr('data-toggle') == 'button') {
						if($(this).hasClass('active')) {
								$(this).removeClass($(this).attr('class-toggle'));
						} else {
								$(this).addClass($(this).attr('class-toggle'));
						}
				}
			}
        });

       

        // FORM - CLEAR FIELD
        // ------------------------------------------------------------------------------------------------ * -->
        $.fn.clearicon = function (options) {
                // default settings
                var config = {
                        "in": 'fadeIn',
                                "out": 'fadeOut',
                                "speed": 'fast',
                                "css": {
                                "right": "-3px",
                                        "top": "-6px"
                        },
                                "html": '<i class="fontello-icon-cancel-circle f14 opaci45"></i>',
                                "click": function () {
                                // general purposes functionality for clearing a field element
                                $(this).val('').removeAttr('checked').removeAttr('selected').keyup();
                        }
                };
                if(options) $.extend(config, options);
                // do the magic
                $(this).each(function () {
                        // the current element
                        var self = $(this);
                        var ctr = $('<span style="position: relative; width:auto; overflow: hidden;" class="clear-icon-wrap"/>');
                        $(this).wrap(ctr);
                        var btn = $('<a style="position: absolute;" class="btn btn-glyph btn-link clear-icon-btn" href="javascript:;"/>');
                        btn.css(config.css).html(config.html);
                        self.after(btn);
                        // handle clicking of button
                        if(config.click) btn.click(function () {
                                config.click.apply(self);
                        });
                        // handle value changes of input
                        self.keyup(function () {
                                if(self.val().length > 0) {
                                        btn[config['in']](config.speed);
                                } else {
                                        btn[config['out']](config.speed);
                                }
                        });
                        // trigger initial state
                        self.keyup();
                });
                return this;
        };

        $('.clear-field').clearicon();
        $('.clear-textarea').clearicon({
                'css': {
                        'right': '-3px',
                                'top': '0'
                },
        });

        // BOOTSTRAP TOOLTIP
        // ------------------------------------------------------------------------------------------------ * -->
        $("a[rel=tooltip], input[rel=tooltip] ").tooltip()

        $('.Ttip').tooltip({
                placement: 'top'
        });
        $('.Rtip').tooltip({
                placement: 'right'
        });
        $('.Btip').tooltip({
                placement: 'bottom'
        });
        $('.Ltip').tooltip({
                placement: 'left'
        });

        

        // BOOTSTRAP POPOVER
        // ------------------------------------------------------------------------------------------------ * -->
        // popover demo
        $('.popover').popover()
        $("[rel=popover]")
                .popover({
                html: true
        });

        // popover hover
        $("[rel=popover-hover]")
                .popover({
                html: true,
                trigger: 'hover',
                delay: {
                        hide: 500
                }
        });

        // Popover hide click to element
        $('[rel=popover-click]')
                .popover({
                html: true,
                delay: {
                        show: 100,
                        hide: 300
                }
        })
                .click(function (e) {
                $(this).popover('toggle');
                e.stopPropagation();
        });

        

        // Bootstrap Hack for button radio to hidden input 
		// ------------------------------------------------------------------------------------------------ * -->
        var _old_toggle = $.fn.button.prototype.constructor.Constructor.prototype.toggle;
        $.fn.button.prototype.constructor.Constructor.prototype.toggle = function () {
                _old_toggle.apply(this);
                var $parent = this.$element.parent('[data-toggle="buttons-radio"]')
                var target = $parent ? $parent.data('target') : undefined;
                var value = this.$element.attr('value');
                if(target && value) {
                        $('#' + target).val(value);
                }
        };

})(jQuery, this, document);
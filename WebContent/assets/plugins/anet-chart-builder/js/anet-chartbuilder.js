;(function ( $ ) {
	var pluginName = 'anetChartifier';
	
	
	function Plugin(element, options) {
	    var el = element; var $el = $(element);
	    // Use the extend method to merge defaults/options as usual:
	    // But with the added first parameter of TRUE to signify a DEEP COPY
	    options = $.extend(true,{}, $.fn[pluginName].defaults, options);
	    
	    
	    
	    /************************** Start Plugin Functionality**********************************************/
	    function _printWidget(elmId) 
	    {

	      var divToPrint=document.getElementById(elmId).parentElement;
	      var newWin=window.open('','Print-Window');
	      newWin.document.open();
	      newWin.document.write('<html><body onload="window.print()">'+divToPrint.innerHTML+'</body></html>');
	      newWin.document.close();
	      setTimeout(function(){newWin.close();},10);

	    }
	    function _fullScreen(element) {
	        var requestMethod = element.requestFullScreen || element.webkitRequestFullScreen || element.mozRequestFullScreen || element.msRequestFullScreen;
	        if (requestMethod) { // Native full screen.
	            requestMethod.call(element);
	        } else if (typeof window.ActiveXObject !== "undefined") { // Older IE.
	            var wscript = new ActiveXObject("WScript.Shell");
	            if (wscript !== null) {
	                wscript.SendKeys("{F11}");
	            }
	        }
	    }
	    function _exitfullScreen(element) {
	        var requestMethod = element.exitFullscreen || element.webkitExitFullscreen || element.mozCancelFullScreen || element.msExitFullscreen;
	        if (requestMethod) { // Native full screen.
	            requestMethod.call(element);
	        } else if (typeof window.ActiveXObject !== "undefined") { // Older IE.
	            var wscript = new ActiveXObject("WScript.Shell");
	            if (wscript !== null) {
	                wscript.SendKeys("{F11}");
	            }
	        }
	    }
	    function _initWidgetModal( _URL,_width,_onLoadCallback){

	        var dlg =
	        $('<div class="modal fade" data-width="'+_width+'%">' +
	            '<div class="modal-dialog">' +
	                '<div class="modal-content">' +
	                '<div class="modal-body">' +
	                '</div>' +
	                '</div>' +
	            '</div>' +
	        '</div>'
	        );
	        dlg.find('.modal-body').css({minHeight:'400px'});
	        dlg.find('.modal-body').load(_URL, function (data) {
	            setTimeout(function () {
	                _onLoadCallback();
	                dlg.find('.anet-widget-btn-maximize').hide();
	                dlg.find('.anet-widget-btn-fullscreen').show();
	                dlg.find('.anet-widget-btn-exit').show();
	                
	                //make draggable
	                dlg.find('.anet-widget-proxy .widget-header').css({'cursor':'move'});
	                dlg.find( ".modal-content" ).parent().parent().draggable({ handle: ".anet-widget-proxy .widget-header" });
	                
	            },200);
	        });
	        
	        //dlg.modal('show');
	        dlg.modal({
	            show: true,
	            keyboard: false,
	            backdrop: 'static'
	        });
	        
	        
	        
	    	dlg.on('hidden.bs.modal', function () {
	    	    dlg.remove();
	    	    //location.reload();
	    	    setTimeout(function () {
	    	    	window.dispatchEvent(new Event('resize')); // window resize triggered
	    	    },200);
	    	    
	    	});
	    }
	    function _initWidgetHeader()
	    {
	    	var header = null;
	    	if(options.widget.hasToolbar)
			{
				header=$("<div>");		
				$(header).addClass("widget-header no-border");
				$(header).html("<h4><i class='btn btn-glyph btn-link fontello-icon-gauge-1'></i><img class='anet-widget-loader' src='"+options.widget.toolbar.loader+"' /></h4>")
				var widgetTool=$(" <div class='widget-tool'>");
				var toggle = 
				"<a class='anet-widget-btn-filter btn-table btn btn-glyph btn-link  fontello-icon-filter'></a>"
				+"<a class='anet-widget-btn-table btn btn-glyph btn-link  fontello-icon-table'></a>"
				+"<a class='anet-widget-btn-print btn btn-glyph btn-link   fontello-icon-print-2'></a>"
				+"<a class='anet-widget-btn-reload btn btn-glyph btn-link  fontello-icon-spin3'></a>"
				+"<a class='anet-widget-btn-maximize btn btn-glyph btn-link  fontello-icon-resize-full-2'></a>"
				+"<a class='anet-widget-btn-fullscreen btn btn-glyph btn-link  fontello-icon-popup-4' style='display:none;'></a>"
				+"<a class='anet-widget-btn-exit btn btn-glyph btn-link  fontello-icon-cancel-5' style='display:none;'></a>";
				$(widgetTool).html(toggle);
				$(header).append(widgetTool);
				
				
				
				//Filter Button Click
				$(header).find('.anet-widget-btn-filter').on('click', function(event){
					
				});
				//Table Button Click
				$(header).find('.anet-widget-btn-table').on('click', function(event){
					event.stopPropagation();
				    event.stopImmediatePropagation();
				    var tblVisible = $(this).attr("data-visible");
				    if(typeof tblVisible === "undefined")
				    {
				    	$(this).attr("data-visible","true");
				    	options.widget.content.datagrid.autoLoad=true;
					    _initDataGrid();
				    }
				    else
				    {
				    	tblVisible = ($(this).attr("data-visible") === 'true');
				    	if(tblVisible){
				    		$(this).attr("data-visible","false");
				    		options.widget.content.datagrid.autoLoad=false;
				    		
				    		$el.find(".widget-content-datagrid").hide();
				    		var modal = $el.closest(".anet-widget-proxy");
				    		if(modal.length>0)
				    		{
				    			var fsBtn = $el.closest(".anet-widget-proxy").find(".anet-widget-btn-fullscreen").eq(0);
				    			if(fsBtn.attr("anet-windowstate")=="fullscreen")
				    			{
				    				$el.find(".widget-content-datagrid").hide();
						    		var highChartDiv = $el.find(".highcharts-container").eq(0);
				    				$(highChartDiv).animate({
				    					height: screen.availHeight-60
						        	},400,function(){
						        		window.dispatchEvent(new Event('resize'));
						        	});
				    			}
				    			
				    		}
				    	}
				    	else
				    	{
				    		$(this).attr("data-visible","true");
				    		options.widget.content.datagrid.autoLoad=true;
						    _initDataGrid();
				    	}
				    }
				    
				});
				
				//Print Button Click
				$(header).find('.anet-widget-btn-print').on('click', function(event){
					event.stopPropagation();
				    event.stopImmediatePropagation();
				    
				    var widget=$(this).parent().parent().parent();
				    var widgetContent = $(widget).find(".widget-content").eq(0);
				    _printWidget($(widgetContent).attr('id').trim());
				});
				//Reload Button Click
				$(header).find('.anet-widget-btn-reload').on('click', function(event){
					_initChart(header,$el.find(".widget-content"));
					_initDataGrid();
				});
				//Maximize Button Click
				$(header).find('.anet-widget-btn-maximize').on('click', function(event){
					event.stopPropagation();
				    event.stopImmediatePropagation();
				    
				    
				    var widget=$(this).parent().parent().parent();
				    var widgetContent = $(widget).find(".widget-content").eq(0);
				    var anetId = $(widgetContent).attr("Id")+"_proxy";
				    var _url= options.widget.content.chart.proxyUrl; 
					var _qStr= "dboard="+options.widget.dashboard+"&wgt="+options.widget.reference+"&renderTo="+options.widget.id;
				    _url=_url+"?"+_qStr;
				    _initWidgetModal(_url,80,function(){
				    	
				    	var modal = $(".anet-widget-proxy").closest(".modal").eq(0);
				    	var fScrBtn = $(".anet-widget-proxy").find('.anet-widget-btn-fullscreen').eq(0);
					    fScrBtn.attr("anet-windowstate","normal");
					    fScrBtn.attr("anet-window-width",modal.width());
					    fScrBtn.attr("anet-window-height",modal.height());
				    });
				});
				//Fullscreen Button Click
				$(header).find('.anet-widget-btn-fullscreen').on('click', function(event){
					event.stopPropagation();
	    			event.stopImmediatePropagation();
	    			var fsBtn = $(this);
	    			var modal = $(".anet-widget-proxy").closest(".modal").eq(0);
	    			if(fsBtn.attr("anet-windowstate")=="normal")
	    			{
	    				fullScreen(document.documentElement);
		    			$(modal).css({"top":"0px","left":"0px","width":"99.3%","margin":"0px"});
		    			$(modal).height(screen.availHeight);

		    			$el.find(".highcharts-container").eq(0).height(screen.availHeight-80);
		    			fsBtn.attr("anet-windowstate","fullscreen");
		    			//resize window for sizing high charts if out of div
		    			window.dispatchEvent(new Event('resize'));
	    			}
	    			else
    				{
	    				var highChartDiv = $el.find(".highcharts-container").eq(0);
	    				modal.animate({
		        			width:fsBtn.attr("anet-window-width"),
			        		height: fsBtn.attr("anet-window-height"),
			        		top:100,
			        		left:(screen.availWidth-fsBtn.attr("anet-window-width"))/2
			        	},500,function(){
			        		window.dispatchEvent(new Event('resize'));
			        	});
	    				$(highChartDiv).animate({
	    					height: fsBtn.attr("anet-window-height")-20,
		        			width:fsBtn.attr("anet-window-width")-20
			        	},400,function(){});
	    				fsBtn.attr("anet-windowstate","normal");
    				}
	    			 
				});
				//Exit Button Click
				$(header).find('.anet-widget-btn-exit').on('click', function(event){
					event.stopPropagation();
	    			event.stopImmediatePropagation();
	    			
	    			$(".anet-widget-proxy").closest(".modal").modal('hide');
	    			_exitfullScreen(document.documentElement);
	    			window.dispatchEvent(new Event('resize')); // window resize triggered
				});
				
				
			}
	    	else
    		{
	    		header=$("<div>");
	    		$(header).html("<img class='anet-widget-loader' src='"+options.widget.toolbar.loader+"' />")
    		}
	    	return header;
	    }
	    function _initChart(header,content)
	    {
	    	var dgLoader = $(header).find('.anet-widget-loader').eq(0);
	    	var _url= "dashboard/initwidget"; 
			var _qStr= "dboard="+options.widget.dashboard+"&wgt="+options.widget.reference+"&renderTo="+options.widget.id;
			 $.ajax({
		        type: "GET",
		        url: _url,
		        data: _qStr,
		        cache:false,
		        async : true,
		        beforeSend: function () {
		        	$(dgLoader).show();
		        },
		        success: function (data) {
		        	$(content).html(data);
		        	$(dgLoader).hide()
		        	
		        	/*var modal = $el.closest(".anet-widget-proxy");
		    		if(modal.length>0)
		    		{
		    			var tblVisible = ($el.closest(".anet-widget-btn-table").attr("data-visible") === 'true');
		    			if(!tblVisible)
		    			{
		    				var fsBtn = $el.closest(".anet-widget-proxy").find(".anet-widget-btn-fullscreen").eq(0);
			    			if(fsBtn.attr("anet-windowstate")=="fullscreen")
			    			{
			    				var highChartDiv = $el.find(".highcharts-container").eq(0);
			    				$(highChartDiv).animate({
			    					height: screen.availHeight-60
					        	},400,function(){
					        		window.dispatchEvent(new Event('resize'));
					        	});
			    			}
		    				
		    			}
		    		}*/
		        },
		        error: function (xhr, ajaxOptions, error) {
		        }
		    });
	    }
	    function _initDataGrid()
	    {
	    	if(options.widget.content.datagrid.autoLoad)
	    	{
				var dgLoader = $el.find('.anet-widget-loader').eq(0);
	    		var _url=options.widget.content.datagrid.actionUrl;
			    var _qStr= "dboard="+options.widget.dashboard+"&wgt="+options.widget.reference;
			    $.ajax({
			        type: "GET",
			        url: _url,
			        data: _qStr,
			        cache:false,
			        async : true,
			        beforeSend: function () {
			        	$(dgLoader).show();
			        },
			        complete: function () {
			        },
			        success: function (data) {
			        	var widgetContent = $el.find(".widget-content").eq(0);
			        	var highChartDiv = $(widgetContent).find(".highcharts-container").eq(0);
			        	/*if($(".modal").length==0)
			        	{
			        		$(highChartDiv).animate({
				        		height: $el.height()/2
				        	},500,function(){
				        		window.dispatchEvent(new Event('resize')); 
				        	});
			        		
			        	}*/
			        	var modal = $(".anet-widget-proxy").closest(".modal").eq(0);
			        	// display data grid after 1/2 seconds
			        	setTimeout(function(){ 
			        		$(dgLoader).hide();
			        		
			        		var datagrid = $(widgetContent).attr("id");
			        		$el.find("#datagrid_"+datagrid).html(data);
			        		$el.find(".widget-content-datagrid").show();
			        		var fsBtn = $el.closest(".anet-widget-proxy").find(".anet-widget-btn-fullscreen").eq(0);
			        		if(fsBtn.length>0)
			        		{
			        			if(fsBtn.attr("anet-windowstate")=="fullscreen")
				    			{
			        				$el.closest(".anet-widget-proxy").css({"min-height":(screen.availHeight-20)+'px'});
				    			}
			        		}
			    			
			        		if(modal.length>0){
			    				$(highChartDiv).animate({
					        		height: screen.availHeight/2
					        	},500,function(){
					        		window.dispatchEvent(new Event('resize')); 
					        	});
			        		}
			        	}, 0.5*1000);
			        },
			        error: function (xhr, ajaxOptions, error) {
			        }
			    });
	    	}
	    }
	    function _initDataGridWrapper()
	    {
	    	var contentDataGrid = null;
	    	var contentDataGridToolBar = null;
	    	if(options.widget.content.datagrid.hasDataGrid)
	    	{
	    		contentDataGrid = $("<div>");
	    		$(contentDataGrid).addClass("widget-content-datagrid").attr("id","datagrid_"+options.widget.id);
	    		if(options.widget.content.datagrid.hasToolbar)
	    		{
	    			contentDataGridToolBar = $("<div>");
	    			$(contentDataGridToolBar).html("<img class='anet-widget-dg-loader' src='"+options.widget.toolbar.loader+"' style='display:none;' />");
	    			
	    			$(contentDataGrid).insertBefore(contentDataGridToolBar);
	    		}
	    	}
	    	return contentDataGrid;
	    }
	    function _initWidget()
	    {
	    	if(options.widget.contentOnly)
	    	{
	    		$el.removeAttr(options.widget.id);
	    		options.widget.hasToolbar=false;
	    	}
	    	else
	    	{
	    		$el.addClass("widget widget-box widget-collapsible").removeAttr(options.widget.id);
	    		options.widget.hasToolbar=true;
	    	}
	    	
	    	//header - append
			var header = _initWidgetHeader();
			if(header!=null)
				$el.append(header);
			
			var content=$("<div>");
			$(content).addClass("widget-content").attr("id",options.widget.id);
			$el.append(content).removeClass("anet-widget");
			
			//datagrid - append
			var dataGrid = _initDataGridWrapper();
			if(dataGrid!=null)
				$el.append(dataGrid);
			
			
			
			_initChart(header,content);
			_initDataGrid();
	    }
	   
	    
	    
	    /************************** End Plugin Functionlity ************************************************/
	    
	    
	    
	    
	    function init() {
	    	
	    	var validate=false;
	    	if(options.widget.id=='')
	    		alert("anet-id cannot be empty");
	    	else if(options.widget.dashboard=='')
	    		alert("anet-dashboard cannot be empty");
	    	else if(options.widget.reference=='')
	    		alert("anet-widget (reference) property cannot be empty");
	    	else
	    		validate=true;
	    	
	    	
	    	if(validate)
	    	{
	    		//console.log(options);
	    		_initWidget();
		    	hook('onInit');
	    	}
	    }
	    function option (key, val) {
	    	if (val) { options[key] = val; } else { return options[key]; }
	    }
	    function destroy() {
	      $el.each(function() {
	        var el = this;
	        var $el = $(this);
	        hook('onDestroy');
	        $el.removeData('plugin_' + pluginName);
	      });
	    }
	    function hook(hookName) {
	      if (options[hookName] !== undefined) {
	        options[hookName].call(el);
	      }
	    }
	    init();
	    return {
	      option: option, // registering a public member
	      destroy: destroy // registering a public method
	    };
	}
	$.fn[pluginName] = function(options) {
	    if (typeof arguments[0] === 'string') {
	      var methodName = arguments[0];
	      var args = Array.prototype.slice.call(arguments, 1);
	      
		  var returnVal;
	      this.each(function() {
	        if ($.data(this, 'plugin_' + pluginName) && typeof $.data(this, 'plugin_' + pluginName)[methodName] === 'function') {
	          returnVal = $.data(this, 'plugin_' + pluginName)[methodName].apply(this, args);
	        } else {
	          throw new Error('Method ' +  methodName + ' does not exist on jQuery.' + pluginName);
	        }
	      });
	      if (returnVal !== undefined){
	        return returnVal;
	      } else {
	        return this;
	      }
	    } else if (typeof options === "object" || !options) {
	      return this.each(function() {
	        if (!$.data(this, 'plugin_' + pluginName)) {
	          $.data(this, 'plugin_' + pluginName, new Plugin(this, options));
	        }
	      });
	    }
	  };
	/**
	* Default options initialization
	*/
	$.fn[pluginName].defaults = {
		widget:{
			id:"",
			dashboard:"",
			reference:"",
			contentOnly:false,
			refreshAfter:"5",
			hasToolbar:true,
			toolbar:{
				loader:""
				},
			content:{
				chart: {
					actionUrl:"dashboard/initwidget",
					proxyUrl:"dashboard/initwidgetproxy"
				},
				datagrid: {
					actionUrl:"dashboard/initdatagrid",
					autoLoad:false,
					hasDataGrid:true,
					hasToolbar:true,
					toolbar:{
					},
				}
			},
		},
		onInit: function() {},
		onDestroy: function() {}
	};
})(jQuery);
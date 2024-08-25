;(function ( $ ) {
	var pluginName = 'uiconverter';
	
	function Plugin(element, options) {
	    var el = element;
	    var $el = $(element);

	    options = $.extend({}, $.fn[pluginName].defaults, options);

	    function init() {
	    
	    	//init anet ui elements
	    	_lookupInit();
	    	_shuttleInit();
	    	_dropDownInit();
	    	_datePickerInit();
	    	_checkBoxInit();
	    	_textBoxInit();
	    	
	    	_radioInit();
	    	
	      hook('onInit');
	    }
	    /**
	     * private : method
	     * _lookupInit
	     */
		function _lookupInit()
		{
			$el.find( ".anet-lookup" ).each(function() {
	            var anetLookup = $( this );
	            var anetDataSource = anetLookup.attr("anet-datasource");
	            if (typeof anetDataSource !== typeof undefined && anetDataSource !== false) {
	            	// Do Nothing
	            }
	            else
	            	anetDataSource = "";
	            
	            var html=[];
				var lookupBtn = $("<div class='anet-lookup-btn'></div>");
				options.lookup.dataSource = anetDataSource;
				options.lookup.lookupItem = anetLookup.attr("id");
	            lookupBtn.click(function(){
	            	_lookupLoad(options.lookup.dlgLoadURL,options.lookup.lookupItem,options.lookup.dataSource,options.lookup.dlgWrapper,$('#'+options.lookup.rptIdHolder).attr('data-val'));
				});
				html.push(
	            		"<label for='"+anetLookup.attr("id")+"'>"+anetLookup.attr("anet-label")+"</label>",
	            		"<input type='text' id='"+anetLookup.attr("id")+"' name='"+anetLookup.attr("name")+"' class='anet-lookup'>",
	            		lookupBtn
	            );
	            anetLookup.removeAttr('id').removeAttr('name').removeAttr('anet-label').removeClass('anet-lookup').addClass('anet-row');
	            anetLookup.append( html );
	        });
		}
		/**
		 * private : method
		 * _lookupLoad
		 * on button click open lookup dialog
		 */
		function _lookupLoad(_url,lookupItem,anetDataSource,lookupSearchPanel,report) {
			var _where_conditions = $("#" + lookupItem).attr("data-lookup-where");
			if (typeof _where_conditions !== typeof undefined && _where_conditions !== false) {
			    if (_where_conditions != "")
			        _where_conditions = encodeURIComponent(_where_conditions);
			}
			else { _where_conditions = ""; }
			if (_where_conditions != "")
			    _where_conditions = "&where=" + _where_conditions;
			var qStr = "lookupItem=" + lookupItem +"&lookupDS="+ anetDataSource +"&rpt=" + report+_where_conditions;
			//alert(_url+"?"+qStr);
			$.ajax({
			    type: "GET",
			    url: _url,
			    data: qStr,
			    success: function (data) {
			        if (data == "") {
			            alert("Not Configured Yet!");
			            return;
			        }
			        $("#"+lookupSearchPanel).append(data);
			        
			        // Add Event
			        var _txtAnetDialogSearch = $("#" + lookupItem + "_dialog").find("#txtAnetDialogSearch").eq(0);
			        _txtAnetDialogSearch.keypress(function(event){
			        	var elm = $(this);
			        	_lookupLoadDialogSearchResult(event,elm);
			        });
			        var _anetDialogSearchLookup = $("#" + lookupItem + "_dialog").find("#anet-dialog-search-lookup").eq(0);
			        _anetDialogSearchLookup.click(function(){
			        	var elm = $(this);
			        	_lookupDlgSearchResult(elm);
			        });
			        
			        $(function () {
			            $("#" + lookupItem + "_dialog").dialog({
			                modal: true,
			                title: $("#" + lookupItem).prev().text() + " Search",
			                width: 650,
			                close: function () {
			                    $("#" + lookupItem + "_dialog").remove();
			                },
			                buttons: [
			                    {
			                    	text: "Add Item",
			                        "class": 'btn btn-warning',
			                    	click: function () {
			                            var searchVal = "";
			                            var chkSelector = $("#" + lookupItem + "_dialog .anet-dialog-data-table").find(".chkRCDialogValSelector");
			                            var rdoSelector = $("#" + lookupItem + "_dialog .anet-dialog-data-table").find(".rdoRCDialogValSelector");
			                            if ($(chkSelector).length > 0) {
			                                $(chkSelector).each(function (i) {
			                                    if ($(this).is(':checked'))
			                                        searchVal += $(this).val() + ",";
			                                });
			                                searchVal = searchVal.substring(0, searchVal.length - 1);
			                            }
			                            if ($(rdoSelector).length > 0) {
			                                $(rdoSelector).each(function (i) {
			                                    if ($(this).is(':checked')) {
			                                        searchVal += $(this).val();
			                                        return;
			                                    }
			                                });
			                            }
			                            if ($("#anet-dialog-search-checkbox").is(':checked')) {
			                                if ($("#" + lookupItem).val() != "") {
			                                    if (searchVal != "")
			                                        $("#" + lookupItem).val($("#" + lookupItem).val() + "," + searchVal);
			                                }
			                                else
			                                    $("#" + lookupItem).val(searchVal);
			                            }
			                            else
			                                $("#" + lookupItem).val(searchVal);
			                            
			                            $("#" + lookupItem + "_dialog").remove();
			                            $("#" + lookupItem).focus();
			                           // $(this).dialog("close");
			                        }
			                  }
			                ]
			            });
			            $(".anet-dialog-lodder").hide();
			        });
			    },
			    error: function (xhr, ajaxOptions, error) {
			    }
			});
		}
		function _lookupLoadDialogSearchResult(e, obj) {
		    if (e.keyCode == 13) {
		    	_lookupGetResult(obj);
		    }
		}
		function _lookupGetResult(obj) {
		    var _finalJSONStr = "";
		    if ($(obj).attr("data-where") != "") {
		        var jsonOBJ = JSON.parse($(obj).attr("data-where"));
		        if ($("#" + jsonOBJ.ValueFrom).length > 0)
		            _finalJSONStr = $("#" + jsonOBJ.ValueFrom).val();

		        jsonOBJ.ValueFrom = _finalJSONStr;
		        _finalJSONStr = JSON.stringify(jsonOBJ);
		    }
		    var qStr = "searchBy=" + $('input[name=rdoRCDialog]:checked').val() + "&searchValue=" + $(obj).val() + "&jsonReportControl=" + $("#txtAnetDialogSearch").attr("data-reportcontrol") + "&where=" + _finalJSONStr;
		    if($('select[name=pageSize]').length>0)
		    	qStr += "&pageSz=" + $('select[name=pageSize]').val();
		    if($('input[name=pageNumber]').length>0)
		    	qStr += "&pageNum=" + $('input[name=pageNumber]').val();
		    if($("#anet-dialog-insearch-checkbox").is(":checked"))
		    	qStr += "&appinSch=true"; // Apply in search
		    else
		    	qStr += "&appinSch=false";
		    $(".anet-dialog-lodder").show();
		    var _url = location.protocol + "//" + location.host + "/" + location.pathname.split("/")[1] + options.lookup.dlgLoadUIURL;
		    //if (location.host.contains("localhost"))//contains does not supported by IE
		    if (location.host.indexOf("localhost")>=0)
		        _url = location.protocol + "//" + location.host + $(obj).attr("data-ctx-path") + options.lookup.dlgLoadUIURL;
		    $.ajax({
		        type: "GET",
		        url: _url,
		        data: qStr,
		        cache:false,
		        success: function (data) {
		            $("#anet-dialog-data-table-dummy").show();
		            $("#anet-dialog-search-result").html(data);
		            $(".anet-dialog-lodder").hide();
		            
		            $("#anet-dialog-data-table-dummy td").eq(0).css("width", $("#anet-dialog-search-result .anet-dialog-data-table td").eq(0).width() + "px");
		            $("#anet-dialog-data-table-dummy td").eq(1).css("width", $("#anet-dialog-search-result .anet-dialog-data-table td").eq(1).width() + "px");
		            $("#anet-dialog-data-table-dummy td").eq(2).css("width", $("#anet-dialog-search-result .anet-dialog-data-table td").eq(2).width() + "px");
		            
		            //paginate init
		            var pgFirst = $("#"+options.lookup.lookupItem+"_dialog .anet-paginator-wrapper .pg-first").eq(0);
		            var pgPrev = $("#"+options.lookup.lookupItem+"_dialog .anet-paginator-wrapper .pg-prev").eq(0);
		            var pgNext = $("#"+options.lookup.lookupItem+"_dialog .anet-paginator-wrapper .pg-next").eq(0);
		            var pgLast = $("#"+options.lookup.lookupItem+"_dialog .anet-paginator-wrapper .pg-last").eq(0);
		            var pgSize = $("#"+options.lookup.lookupItem+"_dialog .anet-paginator-wrapper .pg-size").eq(0);
		            var pgReload = $("#"+options.lookup.lookupItem+"_dialog .anet-paginator-wrapper .pg-reload").eq(0);
		            pgFirst.click(function(){
		            	var elm = $(this);
		            	_lookupPgFirst(elm,elm.attr("anet-tr"),elm.attr("anet-pn"),elm.attr("anet-ps"));
		            });
		            pgPrev.click(function(){
		            	var elm = $(this);
		            	_lookupPgPrev(elm,elm.attr("anet-tr"),elm.attr("anet-pn"),elm.attr("anet-ps"));
		            });
		            pgNext.click(function(){
		            	var elm = $(this);
		            	_lookupPgNext(elm,elm.attr("anet-tr"),elm.attr("anet-pn"),elm.attr("anet-ps"));
		            });
		            pgLast.click(function(){
		            	var elm = $(this);
		            	_lookupPgLast(elm,elm.attr("anet-tr"),elm.attr("anet-pn"),elm.attr("anet-ps"));
		            });
		            pgSize.change(function(){
		            	var elm = $(this);
		            	_lookupPgReloadByPageSize(elm);
		            });
		            pgReload.click(function(){
		            	var elm = $(this);
		            	_lookupPgReload(elm);
		            });
		        },
		        error: function (xhr, ajaxOptions, error) {
		        }
		    });
		}
		function _lookupPgReload(obj){
			_lookupGetResult(document.getElementById("txtAnetDialogSearch"));
		}
		function _lookupPgReloadByPageSize(obj){
			_lookupPgReload(obj);
		}
		function _lookupPgFirst(obj,tr,cp,ps)
		{
			//tr = total records //cp = current page //ps = page size
			$(obj).parent().find(".page-number").eq(0).val('1');
			_lookupGetResult(document.getElementById("txtAnetDialogSearch"));
		}
		function _lookupPgLast(obj,tr,cp,ps)
		{
			var _tp = 0;
			if(parseInt(tr)%parseInt(ps)>0){
				_tp=Math.floor((parseInt(tr)/parseInt(ps)))+1;
			}else {
				if((parseInt(tr)<parseInt(ps))){
					_tp = 1
				} else {
					_tp = Math.floor(parseInt(tr)/parseInt(ps));
				}
			}
			$(obj).parent().find(".page-number").eq(0).val(_tp);
			_lookupGetResult(document.getElementById("txtAnetDialogSearch"));
		}
		function _lookupPgNext(obj,tr,cp,ps)
		{
			var _cp = parseInt(cp);
			var _tp = 0;
			if(parseInt(tr)%parseInt(ps)>0){
				_tp=Math.floor((parseInt(tr)/parseInt(ps)))+1;
			}else {
				if((parseInt(tr)<parseInt(ps))){
					_tp = 1
				} else {
					_tp = Math.floor(parseInt(tr)/parseInt(ps));
				}
			}
			if((_cp+1)>_tp)
				_cp=_tp;
			else
				_cp=_cp+1;
			
			$(obj).parent().find(".page-number").eq(0).val(_cp);
			_lookupGetResult(document.getElementById("txtAnetDialogSearch"));
		}
		function _lookupPgPrev(obj,tr,cp,ps)
		{
			var _cp = parseInt(cp);
			
			if((_cp-1)<1)
				_cp=1;
			else
				_cp=_cp-1;
			
			$(obj).parent().find(".page-number").eq(0).val(_cp);
			_lookupGetResult(document.getElementById("txtAnetDialogSearch"));
		}
		function _lookupDlgSearchResult(obj) {
		    _lookupGetResult(document.getElementById("txtAnetDialogSearch"));
		}
		/* END LOOKUP##########################################################################################*/
		/* START SHUTTLE##########################################################################################*/
		/**
	     * private : method
	     * _shuttleInit
	     */
		function _shuttleInit()
		{
			$el.find( ".anet-shuttle" ).each(function() {
	            var anetShuttle = $( this );
	            var html=[];
				var btnAddItem = $("<input type='button' class='anet-shuttle-ctrl' data-val='"+anetShuttle.attr('id')+"' id='"+anetShuttle.attr('id')+"AddItem' value='→' />");
				var btnAddAllItem =  $("<input type='button' class='anet-shuttle-ctrl' data-val='"+anetShuttle.attr('id')+"' id='"+anetShuttle.attr('id')+"AddAllItem' value='⇒' />");
				var btnRemoveItem =  $("<input type='button' class='anet-shuttle-ctrl' data-val='"+anetShuttle.attr('id')+"' id='"+anetShuttle.attr('id')+"RemoveItem' value='←' />");
				var btnRemoveAllItem =  $("<input type='button' class='anet-shuttle-ctrl' data-val='"+anetShuttle.attr('id')+"' id='"+anetShuttle.attr('id')+"RemoveAllItem' value='⇐' />");
				btnAddItem.click(function(){
					var obj=$(this);
					$("#"+obj.attr("data-val")+"_proxy option:selected").each(function(){
			            $("#" + obj.attr("data-val")).append($(this).clone());
			            $(this).remove(); 
			        });
			        $("#" + obj.attr("data-val") + "_proxy option:first-child").attr("selected", "selected");
				});
				btnAddAllItem.click(function(){
					var obj=$(this);
					 $("#" + obj.attr("data-val") + "_proxy option").each(function () {
				            $("#" + obj.attr("data-val")).append($(this).clone());
				            $(this).remove();
				        });
				});
				btnRemoveItem.click(function(){
					var obj=$(this);
					$("#" + obj.attr("data-val") + " option:selected").each(function () {
			            $("#" + obj.attr("data-val") + "_proxy").append($(this).clone());
			            $(this).remove();
			        });
			        $("#" + obj.attr("data-val") + " option:first-child").attr("selected", "selected");
				});
				btnRemoveAllItem.click(function(){
					var obj=$(this);
					$("#" + obj.attr("data-val") + " option").each(function () {
			            $("#" + obj.attr("data-val") + "_proxy").append($(this).clone());
			            $(this).remove();
			        });
				});
				var ctrlWrapper = $("<div class='anet-shuttle-ctrl-wrapper'>");
				ctrlWrapper.append(btnAddItem);
				ctrlWrapper.append(btnAddAllItem);
				ctrlWrapper.append(btnRemoveItem);
				ctrlWrapper.append(btnRemoveAllItem);
				
				var dpProxy = $("<select id='"+anetShuttle.attr('id')+"_proxy' class='anet-shuttle' multiple='multiple'></select>");
				var dp = $("<select id='"+anetShuttle.attr('id')+"' name='"+anetShuttle.attr('name')+"' class='anet-shuttle' multiple='multiple'></select>");
				dp.dblclick(function(){
					var obj=$(this);
					if ($("#" + obj.attr("id") + " option:selected").length > 0) {
	                    $("#" + obj.attr("id") + " option:selected").each(function () {
	                        $("#" + obj.attr("id")+"_proxy").append($(this).clone());
	                        $(this).remove();
	                    });
	                }
				});
				dpProxy.dblclick(function(){
					var obj=$(this);
					if ($("#" + obj.attr("id") + " option:selected").length > 0) {
	                    $("#" + obj.attr("id") + " option:selected").each(function () {
	                        $("#" + obj.attr("id").substr(0,(obj.attr("id").length-6))).append($(this).clone());
	                        $(this).remove();
	                    });
	                }
				});
				
				html.push(
	            		"<label for='"+anetShuttle.attr("id")+"'>"+anetShuttle.attr("anet-label")+"</label>",
	            		dpProxy,
	            		ctrlWrapper,
	            		dp
	            );
				//console.log (html);
				anetShuttle.removeAttr('id').removeAttr('name').removeAttr('anet-label').removeClass('anet-shuttle').addClass('anet-row');
				anetShuttle.append( html );
	        });
		}
		/* END SHUTTLE##########################################################################################*/
		/* START DROPDOWN##########################################################################################*/
		function _dropDownInit()
		{
			$el.find( ".anet-dropdown" ).each(function() {
	            var anetDP = $( this );
	            var html=[];
	            
	            var dp = $("<select id='"+anetDP.attr('id')+"' name='"+anetDP.attr('name')+"' class='anet-dropdown'></select>");
	            
	            html.push(
	            		"<label for='"+anetDP.attr("id")+"'>"+anetDP.attr("anet-label")+"</label>",
	            		dp
	            );
				//console.log (html);
	            anetDP.removeAttr('id').removeAttr('name').removeAttr('anet-label').removeClass('anet-dropdown').addClass('anet-row');
	            anetDP.append( html );
			});
		}
		/* END DROPDOWN##########################################################################################*/
		/* START DATEPICKER##########################################################################################*/
		function _datePickerInit()
		{
			$el.find( ".anet-datepicker" ).each(function() {
	            var anetDP = $( this );
	            var html=[];
	            
	            var dp = $("<input type='text' id='"+anetDP.attr('id')+"' name='"+anetDP.attr('name')+"' class='anet-datepicker' />");
	            var dpBtn = $("<div class='anet-datepicker-btn'></div>");
	            var anetFormat =  anetDP.attr('anet-format');
            	if (typeof anetFormat !== typeof undefined && anetFormat !== false) {
            		options.datePicker.format = anetFormat.replace(/^\s+|\s+$/g, ''); //Trim
	            }
            	
	            html.push(
	            		"<label for='"+anetDP.attr("id")+"'>"+anetDP.attr("anet-label")+"</label>",
	            		dp,
	            		dpBtn
	            );
				//console.log (html);
	            dpBtn.click(function () {
	            	dp.focus();
	            });
	            dp.datepicker({
	                format: options.datePicker.format
	            }).datepicker("update", options.datePicker.defaultDate);
	            //dp.datepicker('update', new Date());
	            anetDP.removeAttr('id').removeAttr('name').removeAttr('anet-label').removeClass('anet-datepicker').addClass('anet-row');
	            anetDP.append( html );
			});
		}
		/* END DATEPICKER##########################################################################################*/
		
		/* START CHECKBOX##########################################################################################*/
		function _checkBoxInit()
		{
			$el.find( ".anet-checkbox" ).each(function() {
				var anetElem = $( this );
	            var html=[];
	            
	            var elem = $("<input type='checkbox' id='"+anetElem.attr('id')+"' name='"+anetElem.attr('name')+"' class='anet-checkbox' />");
	            var attrChecked = anetElem.attr('anet-checked');
	            if (typeof attrChecked !== typeof undefined && attrChecked !== false) {
	            	anetElem.removeAttr('anet-checked');
	            	elem.attr('checked','checked');
	            }
	            html.push(
	            		"<label for='"+anetElem.attr("id")+"'>"+anetElem.attr("anet-label")+"</label>",
	            		elem
	            );
	           
	            anetElem.removeAttr('id').removeAttr('name').removeAttr('anet-label').removeAttr('anet-event').removeClass('anet-checkbox').addClass('anet-row');
	            anetElem.append( html );
			});
		}
		/* END CHECKBOX##########################################################################################*/
		
		/* START TEXTBOX##########################################################################################*/
		function _textBoxInit()
		{
			$el.find( ".anet-textbox" ).each(function() {
				var anetElem = $( this );
	            var html=[];
	            
	            var elem = $("<input type='text' id='"+anetElem.attr('id')+"' name='"+anetElem.attr('name')+"' class='anet-textbox' />");
	            html.push(
	            		"<label for='"+anetElem.attr("id")+"'>"+anetElem.attr("anet-label")+"</label>",
	            		elem
	            );
	           
	            anetElem.removeAttr('id').removeAttr('name').removeAttr('anet-label').removeAttr('anet-event').removeClass('anet-textbox').addClass('anet-row');
	            anetElem.append( html );
			});
		}
		/* END TEXTBOX##########################################################################################*/
		
		/* START RADIO##########################################################################################*/
		function _radioInit()
		{
			$el.find( ".anet-radio" ).each(function() {
				var anetElem = $( this );
	            var html=[];
	            //Radio items need to be implemented.....................???
	            var elem = $("<input type='text' id='"+anetElem.attr('id')+"' name='"+anetElem.attr('name')+"' class='anet-radio' />");
	            html.push(
	            		"<label for='"+anetElem.attr("id")+"'>"+anetElem.attr("anet-label")+"</label>",
	            		elem
	            );
	           
	            anetElem.removeAttr('id').removeAttr('name').removeAttr('anet-label').removeAttr('anet-event').removeClass('anet-radio').addClass('anet-row');
	            anetElem.append( html );
			});
		}
		/* END RADIO##########################################################################################*/
		
		function option (key, val) {
	      if (val) {
	        options[key] = val;
	      } else {
	        return options[key];
	      }
	    }
	    function destroy() {
	      $el.each(function() {
	        var el = this;
	        var $el = $(this);
	        // Add code to restore the element to its original state...
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
		cssDir: "css",
		imgDir: "img",
		lookup:{
			lookupItem: "",
			dataSource: "",
			dlgLoadURL : "openlookupdialog",
			dlgLoadUIURL : "/report/loadlookupdialog",
			dlgWrapper : "ReportContorlsFormWrapper",
			rptIdHolder : "anetDynamicFrm", //ID UI Element >> ????????
		},
		datePicker:{
			format:'dd-mm-yyyy',
			defaultDate: new  Date(),
			minDate: new Date('01-01-1900'),
			maxDate: new Date('12-12-9999')
		},
	    onInit: function() {},
	    onDestroy: function() {}
	  };
})(jQuery);

;(function ( $ ) {
	var pluginName = 'uibuilder';
	
	
	function Plugin(element, options) {
	    var el = element; var $el = $(element);
	    options = $.extend({}, $.fn[pluginName].defaults, options);
	    
	    
	    
	    /************************** Start Plugin Functionality**********************************************/
	    function _getControlType(_elemAnetRow)
	    {
	    	var name="";
	    	if(_elemAnetRow.hasClass("anet-textbox"))
	    		name = "TextBox";
	    	else if(_elemAnetRow.hasClass("anet-dropdown"))
	    		name = "DropDown";
	    	else if(_elemAnetRow.hasClass("anet-listbox"))
	    		name = "ListBox";
	    	else if(_elemAnetRow.hasClass("anet-shuttle"))
	    		name = "Shuttle";
	    	else if(_elemAnetRow.hasClass("anet-lookup"))
	    		name = "Lookup";
	    	else if(_elemAnetRow.hasClass("anet-checkbox"))
	    		name = "CheckBox";
	    	else if(_elemAnetRow.hasClass("anet-radio"))
	    		name = "Radio";
	    	else if(_elemAnetRow.hasClass("anet-datepicker"))
	    		name = "DatePicker";
	    	else if(_elemAnetRow.hasClass("anet-daterangepicker"))
	    		name = "DateRangePicker";
	    	return name;
	    }
	    function _initProperties(_propertyWindow,_labelAnetRow,_elemAnetRow)
	    {
	    	if(typeof _labelAnetRow.attr("for") != 'undefined'){
	    		
	    		_propertyWindow.find(".anet-property-control-type").eq(0).val(_getControlType(_elemAnetRow));
	    		_propertyWindow.find(".anet-property-id").eq(0).val(_elemAnetRow.attr("id"));
	    		_propertyWindow.find(".anet-property-name").eq(0).val(_elemAnetRow.attr("name"));
	    		_propertyWindow.find(".anet-property-label").eq(0).val(_labelAnetRow.text());
	    		
	    		var _hasAttr =_elemAnetRow.attr("anet-property-ds-type");
	    		if(typeof _hasAttr !== typeof undefined && _hasAttr !== false){
	    			_propertyWindow.find(".anet-property-ds-cat").eq(0).val(_elemAnetRow.attr("anet-property-ds-type")).change();
	    			_propertyWindow.find(".anet-property-ds-id").eq(0).val(_elemAnetRow.attr("anet-property-ds-id"));
	    			

	    			if(_elemAnetRow.attr("anet-property-ds-type")=="new"){
	    				_propertyWindow.find(".anet-property-ds-id").eq(0).parent().parent().addClass("anet-hidden");
	    				_propertyWindow.find(".anet-property-ds-type").eq(0).parent().parent().removeClass("anet-hidden");
	    			}
	    			else if(_elemAnetRow.attr("anet-property-ds-type")=="existing"){
	    				_propertyWindow.find(".anet-property-ds-type").eq(0).parent().parent().addClass("anet-hidden");
	    				_propertyWindow.find(".anet-property-ds-id").eq(0).parent().parent().removeClass("anet-hidden");
	    			}
	    			else {
	    				_propertyWindow.find(".anet-property-ds-type").eq(0).parent().parent().addClass("anet-hidden");
	    				_propertyWindow.find(".anet-property-ds-id").eq(0).parent().parent().addClass("anet-hidden");
	    			}
	    				
	    		}
	    		_propertyWindow.find(".anet-property-label-style").eq(0).val(_labelAnetRow.attr("style"));
	    		_propertyWindow.find(".anet-property-control-style").eq(0).val(_elemAnetRow.attr("style"));
	    		
        	}
	    }
	    function _initLookup(_url,lookupItem,anetDataSource,anetActiveControl) {
	    	//alert(anetActiveControl.attr("class"));
	    	//alert(lookupItem.attr('class'));
			var qStr = "lookupItem=" + lookupItem.attr('data-id') +"&lookupDS="+ anetDataSource;
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
			        lookupItem.parent().append(data);
			        
			        // Add Event
			        var _txtAnetDialogSearch = $("#" + lookupItem.attr('data-id') + "_dialog").find("#txtAnetDialogSearch").eq(0);
			        _txtAnetDialogSearch.keypress(function(event){
			        	var elm = $(this);
			        	_lookupLoadDialogSearchResult(event,elm);
			        });
			        var _anetDialogSearchLookup = $("#" + lookupItem.attr('data-id') + "_dialog").find("#anet-dialog-search-lookup").eq(0);
			        _anetDialogSearchLookup.click(function(){
			        	var elm = $(this);
			        	_lookupDlgSearchResult(elm);
			        });
			        
			        $(function () {
			            $("#" + lookupItem.attr('data-id') + "_dialog").dialog({
			                modal: true,
			                title: '',//$("#" + lookupItem).prev().text() + " Search",
			                width: 650,
			                close: function () {
			                    $("#" + lookupItem.attr('data-id') + "_dialog").remove();
			                },
			                buttons: [
			                    {
			                    	text: "Add Item",
			                        "class": 'btn btn-warning',
			                    	click: function () {
			                            var searchVal = $("#" + lookupItem.attr('data-id') + "_dialog input[name=rdoPWDSLookup]:checked").val();
			                            if(typeof searchVal == 'undefined')
			                            {
			                            	alert("Please select an item from the list");
			                            	return false;
			                            }
			                           lookupItem.val(searchVal);
			                           $("#" + lookupItem.attr('data-id') + "_dialog").remove();
			                           lookupItem.focus();
			                           //add anet-property-ds-type attribute to active control
			                           anetActiveControl.attr("anet-property-ds-id",searchVal);
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
	    function _lookupDlgSearchResult(obj) {
		    _lookupGetResult(document.getElementById("txtAnetDialogSearch"));
		}
	    function _lookupLoadDialogSearchResult(e, obj) {
		    if (e.keyCode == 13) {
		    	_lookupGetResult(obj);
		    }
		}
		function _lookupGetResult(obj) {
		    var qStr = "searchValue=" + $(obj).val();
		    
		    $(".anet-dialog-lodder").show();
		    var _url = location.protocol + "//" + location.host + "/" + location.pathname.split("/")[1] + options.propertiesWindow.dataSource.lookup.dlgLoadUIURL;
		    if (location.host.indexOf("localhost")>=0)
		        _url = location.protocol + "//" + location.host + $(obj).attr("data-ctx-path") + options.propertiesWindow.dataSource.lookup.dlgLoadUIURL;
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
		            
		        },
		        error: function (xhr, ajaxOptions, error) {
		        }
		    });
		}
		function _clearDocumentSelection()
		{
			//Text Selection
			if(document.selection && document.selection.empty) {
		        document.selection.empty();
		    } else if(window.getSelection) {
		        var sel = window.getSelection();
		        sel.removeAllRanges();
		    }
		}
		function _addRow(me){
			var anetRow = $("<div class='anet-row anet-editable'/>");
			anetRow.dblclick(function(){
				
				_clearDocumentSelection();
				
				$(".anet-editable").removeClass("anet-editable-active");
				anetRow.addClass("anet-editable-active");
				
				var _labelAnetRow = anetRow.find("label").eq(0);
				var _elemAnetRow = anetRow.find(".anet-elem").eq(0);
				
				var _meAnetRow = $(this);
				var _propertyWindow = _meAnetRow.parent().parent().parent().find(".anet-uibuilder-properties").eq(0);
				var _url = location.protocol + "//" + location.host + "/" + location.pathname.split("/")[1] + options.propertiesWindow.propertiesLoadURL;
				var _qStr = "";
				$.ajax({
				        type: "GET",
				        url: _url,
				        data: _qStr,
				        cache:false,
				        beforeSend: function(){
				        	_propertyWindow.html("<div class='anet-loader' />");
				        	
				        },
				        complete: function () {
				        },
				        success: function (data) {
				        	_propertyWindow.html(data);
				        	_initProperties(_propertyWindow,_labelAnetRow,_elemAnetRow);
				        	//assign event on change for anet-property-control-type
				        	var anetPropertyControlType = _propertyWindow.find(".anet-property-control-type").eq(0);
				        	var anetEditableActive = _propertyWindow.parent().find(".anet-editable-active").eq(0);
				        	
				        	//
				        	_propertyWindow.find(".nav-tabs a").click(function(e){
				        		var liveAnetEditableActive = _propertyWindow.parent().find(".anet-editable-active").eq(0);
				        		var elemCount = liveAnetEditableActive.find(".anet-elem").length;
				        		switch($(this).attr('href').toUpperCase())
				        		{
				        			case "#ANET-PROPERTY-GENERIC":
				        				break;
				        			case "#ANET-PROPERTY-DATASOURCE":
				        				if(elemCount<1)
				        				{
				        					alert('First select a control type from general tab');
				        					e.preventDefault();
				        					_propertyWindow.find(".nav-tabs a").eq(0).trigger('click');
				        					return false;
				        				}
					        			break;
				        			case "#ANET-PROPERTY-EVENT":
				        				if(elemCount<1)
				        				{
				        					alert('First select a control type from general tab');
				        					e.preventDefault();
				        					_propertyWindow.find(".nav-tabs a").eq(0).trigger('click');
				        					return false;
				        				}
					        			break;
				        		}
				        	});
				        	
				        	if (typeof anetPropertyControlType != 'undefined') {
				        		anetPropertyControlType.change(function(){
				        			var me = $(this);
				        			var label = $("<label/>");
				        			label.attr("for",_propertyWindow.find(".anet-property-id").eq(0).val().trim());
				        			label.text(_propertyWindow.find(".anet-property-label").eq(0).val().trim());
				        			var elem=null;
				        			var hndlrAfterElem="";
				        			var hndlrBeforeElem="";
				        			switch(me.val().toUpperCase())
				        			{
				        			case "TEXTBOX":
				        				if (typeof anetEditableActive != 'undefined') {
				        					elem = $("<input type='text' class='anet-textbox anet-elem' />");
				        				}
				        				break;
				        			case "DROPDOWN":
				        				if (typeof anetEditableActive != 'undefined') {
				        					elem = $("<select class='anet-dropdown anet-elem' />");
				        				}
				        				break;
				        			case "LISTBOX":
				        				elem = $("<select class='anet-listbox anet-elem' multiple='multiple' />");
				        				break;
				        			case "SHUTTLE":
				        				elem = $("<select class='anet-shuttle anet-elem' multiple='multiple'></select>");
				        				hndlrBeforeElem = $("<select id='_proxy' class='anet-shuttle' multiple='multiple' /><div class='anet-shuttle-ctrl-wrapper'><input type='button' class='anet-shuttle-ctrl' data-val='lstLocation' id='AddItem' value='→'><input type='button' class='anet-shuttle-ctrl' data-val='lstLocation' id='AddAllItem' value='⇒'><input type='button' class='anet-shuttle-ctrl' data-val='lstLocation' id='RemoveItem' value='←'><input type='button' class='anet-shuttle-ctrl' data-val='lstLocation' id='RemoveAllItem' value='⇐'></div>");
				        				break;
				        			case "LOOKUP":
				        				if (typeof anetEditableActive != 'undefined') {
				        					elem = $("<input type='text' class='anet-lookup anet-elem' />");
				        					hndlrAfterElem = $("<div class='anet-lookup-btn'></div>");
				        				}
				        				break;
				        			case "CHECKBOX":
				        				if (typeof anetEditableActive != 'undefined') {
				        					elem = $("<input type='text' class='anet-checkbox anet-elem' />");
				        				}
				        				break;
				        			case "RADIO":
				        				if (typeof anetEditableActive != 'undefined') {
				        					elem = $("<input type='text' class='anet-radio anet-elem' />");
				        				}
				        				break;
				        			case "DATEPICKER":
				        				if (typeof anetEditableActive != 'undefined') {
				        					elem = $("<input type='text' class='anet-datepicker anet-elem' />");
				        					hndlrAfterElem = $("<div class='anet-datepicker-btn'></div>");
				        					
				        					hndlrAfterElem.click(function () {
				        						elem.focus();
				        		            });
				        					elem.datepicker({
				        		                numberOfMonths: 3,
				        		                showButtonPanel: true,
				        		                changeYear: true
				        		            });
				        				}
				        				break;
				        			case "DATERANGEPICKER":
				        				alert("date range Picker");
				        				if (typeof anetEditableActive != 'undefined') {
				        					elem = $("<input type='text' class='anet-datepicker anet-elem' />");
				        					hndlrAfterElem = $("<div class='anet-datepicker-btn'></div>");
				        					
				        					hndlrAfterElem.click(function () {
				        						elem.focus();
				        		            });
				        					elem.datepicker({
				        		                numberOfMonths: 3,
				        		                showButtonPanel: true,
				        		                changeYear: true
				        		            });
				        				}
				        				break;
				        			}
				        			elem.attr("id",_propertyWindow.find(".anet-property-id").eq(0).val().trim());
				        			elem.attr("name",_propertyWindow.find(".anet-property-name").eq(0).val().trim());
				        			var _tmpHtml=[];
		        					_tmpHtml.push(
		        							label
		        							,hndlrBeforeElem
		        							,elem
		        							,hndlrAfterElem
		        							);
		        					anetEditableActive.html(_tmpHtml);
				        		});
				        	}
				        	
				        	//assign event on change for anet-property-id
				        	var anetPropertyId = _propertyWindow.find(".anet-property-id").eq(0);
				        	if (typeof anetPropertyId != 'undefined') {
				        		
				        		var label = null;
				        		var elem = null;
				        		var elem2 = null;
				        		var controlType = "";
				        		
				        		anetPropertyId.keyup(function(event){
				        			
				        			controlType = "anet-"+_propertyWindow.find(".anet-property-control-type").eq(0).val().toLowerCase();
				        			label = anetEditableActive.find("label").eq(0);
				        			elem = anetEditableActive.find("."+controlType).eq(0);
				        			
				        			if(controlType=="anet-shuttle"){
				        				elem2 = anetEditableActive.find("."+controlType).eq(1);
				        				if (typeof label != 'undefined') {
					        				label.attr("for",anetPropertyId.val());
					        			}
					        			if (typeof elem2 != 'undefined') {
					        				elem2.attr("id",anetPropertyId.val());
					        			}
					        			if (typeof elem != 'undefined') {
					        				elem.attr("id",anetPropertyId.val()+"_proxy");
					        			}
			        				}
				        			else
			        				{
				        				if (typeof label != 'undefined') {
					        				label.attr("for",anetPropertyId.val());
					        			}
					        			if (typeof elem != 'undefined') {
					        				elem.attr("id",anetPropertyId.val());
					        			}
			        				}
				        			
				        		});
				        	}
				        	//assign event on change for anet-property-name
				        	var anetPropertyName = _propertyWindow.find(".anet-property-name").eq(0);
				        	if (typeof anetPropertyName != 'undefined') {
				        		var elem=null;
				        		var controlType = "";
				        		anetPropertyName.keyup(function(event){
				        			controlType = "anet-"+_propertyWindow.find(".anet-property-control-type").eq(0).val().toLowerCase();
				        			elem = anetEditableActive.find("."+controlType).eq(0);
				        			if(controlType=="anet-shuttle"){
				        				elem = anetEditableActive.find("."+controlType).eq(1);
				        			}
				        			if (typeof elem != 'undefined') {
				        				elem.attr("name",anetPropertyName.val());
				        			}
				        			
				        		});
				        	}
				        	//assign event on change for anet-property-label
				        	var anetPropertyLabel = _propertyWindow.find(".anet-property-label").eq(0);
				        	if (typeof anetPropertyLabel != 'undefined') {
				        		var label=null;
				        		anetPropertyLabel.keyup(function(event){
				        			label = anetEditableActive.find("label").eq(0);
				        			if (typeof label != 'undefined') {
				        				label.text(anetPropertyLabel.val());
				        			}
				        		});
				        	}
				        	//assign event on change for anet-property-label-style
				        	var anetPropertyLabelStyle = _propertyWindow.find(".anet-property-label-style").eq(0);
				        	if (typeof anetPropertyLabelStyle != 'undefined') {
				        		var label=null;
				        		
				        		anetPropertyLabelStyle.blur(function(){
				        			label = anetEditableActive.find("label").eq(0);
				        			var arr = [];
				        			if (typeof label != 'undefined' && anetPropertyLabelStyle.val().trim()!="") {
				        				if(typeof label.attr('style') != 'undefined'){
				        					//label.attr('style',label.attr('style')+';'+anetPropertyLabelStyle.val());
				        					arr = label.attr('style').split(';');
				        					Array.prototype.push.apply(arr, anetPropertyLabelStyle.val().split(';'));
				        					var unique = arr.filter(function(itm, i, arr) {
				        					    return i == arr.indexOf(itm);
				        					});
				        					label.attr('style',unique.join(';'));
				        				}
				        				else
				        					label.attr('style',anetPropertyLabelStyle.val());
				        			}
				        			else
				        				label.removeAttr('style');
				        		});
				        	}
				        	//assign event on change for anet-property-control-style
				        	var anetPropertyControlStyle = _propertyWindow.find(".anet-property-control-style").eq(0);
				        	if (typeof anetPropertyControlStyle != 'undefined') {
				        		var elem=null;
				        		
				        		anetPropertyControlStyle.blur(function(){
				        			elem = anetEditableActive.find(".anet-elem").eq(0);
				        			var arr = [];
				        			if (typeof elem != 'undefined' && anetPropertyControlStyle.val().trim()!="") {
				        				if(typeof elem.attr('style') != 'undefined'){
				        					//label.attr('style',label.attr('style')+';'+anetPropertyLabelStyle.val());
				        					arr = elem.attr('style').split(';');
				        					Array.prototype.push.apply(arr, anetPropertyControlStyle.val().split(';'));
				        					var unique = arr.filter(function(itm, i, arr) {
				        					    return i == arr.indexOf(itm);
				        					});
				        					elem.attr('style',unique.join(';'));
				        				}
				        				else
				        					elem.attr('style',anetPropertyControlStyle.val());
				        			}
				        			else
				        				elem.removeAttr('style');
				        		});
				        	}
				        	//TAB - Data Source
				        	var anetPropertyDSCategory = _propertyWindow.find(".anet-property-ds-cat").eq(0);
				        	if (typeof anetPropertyDSCategory != 'undefined') {
				        		anetPropertyDSCategory.change(function(){
				        			if(anetEditableActive.find(".anet-elem").length>0){
				        				
				        				var anetActiveControl = anetEditableActive.find(".anet-elem").eq(0);
				        				
					        			if(anetPropertyDSCategory.val()=="new"){
					        				_propertyWindow.find(".anet-property-ds-id").eq(0).parent().parent().addClass("anet-hidden");
					        				_propertyWindow.find(".anet-property-ds-type").eq(0).parent().parent().removeClass("anet-hidden");
					        				
					        				if (typeof anetActiveControl != 'undefined') {
					        					anetActiveControl.attr("anet-property-ds-type","new");
						        			}
					        			} else if(anetPropertyDSCategory.val()=="existing"){
					        				_propertyWindow.find(".anet-property-ds-type").eq(0).parent().parent().addClass("anet-hidden");
					        				_propertyWindow.find(".anet-property-ds-id").eq(0).parent().parent().removeClass("anet-hidden");
					        				
					        				if (typeof anetActiveControl != 'undefined') {
					        					anetActiveControl.attr("anet-property-ds-type","existing");
						        			}
					        			} else { //None
					        				_propertyWindow.find(".anet-property-ds-id").eq(0).parent().parent().addClass("anet-hidden");
					        				_propertyWindow.find(".anet-property-ds-type").eq(0).parent().parent().addClass("anet-hidden");
					        				_propertyWindow.find(".anet-property-ds-lookup").eq(0).val("");
					        				
					        				if (typeof anetActiveControl != 'undefined') {
					        					anetActiveControl.removeAttr("anet-property-ds-type");
						        			}
					        			}
					        			
				        			} else { alert("First select a control type from general tab."); }
				        		});
				        	}
				        	var lookupItem = _propertyWindow.find(".anet-property-ds-id").eq(0);
				        	var lookupBtn = _propertyWindow.find(".anet-property-ds-id").parent().find(".anet-property-ds-lookup-btn").eq(0);
				        	lookupBtn.click(function(){
				        		var anetActiveControl = anetEditableActive.find(".anet-elem").eq(0);
				        		//alert(options.propertiesWindow.dataSource.lookup.dlgLoadURL);
				            	_initLookup(options.propertiesWindow.dataSource.lookup.dlgLoadURL,lookupItem,options.propertiesWindow.dataSource.lookup.dataSource,anetActiveControl);
							});
				        },
				        error: function (xhr, ajaxOptions, error) {
				        }
				    });
			});
			return anetRow;
		}
		
	    function _initAnetBuilder()
	    {
	    	var html=[];
	    	var layout = $("<div class='anet-uibuilder-layout'></div>")
	    	var properties = $("<div class='anet-uibuilder-properties' />");
	    	var wrapper = $("<div class='anet-uibuilder-wrapper' />");
	    	var toolbar = $("<div class='anet-toolbar' />");
	    	var content = $("<div class='anet-uibuilder-content' />");
	    	
	    	
	    	
			var addRowBtn = $("<div class='anet-addrow anet-btn btn btn-small btn-green'><i class='fontello-icon-list-add'></i> Row End</div>");
			var addRowBeforeBtn = $("<div class='anet-addrow-before anet-btn btn btn-small btn-green'><i class='fontello-icon-undo'></i> Row Before</div>");
			var addRowAfterBtn = $("<div class='anet-addrow-after anet-btn btn btn-small btn-green'><i class='fontello-icon-arrow-curved'></i> Row After</div>");
			var moveRowUpBtn = $("<div class='anet-moverow-up anet-btn btn btn-small btn-green'><i class='fontello-icon-up'></i></div>");
			var moveRowDownBtn = $("<div class='anet-moverow-down anet-btn btn btn-small btn-green'><i class='fontello-icon-down'></i></div>");
			var clearSelectedBtn = $("<div class='anet-btn anet-clear-sel btn btn-small btn-yellow'><i class='fontello-icon-scissors-1'></i> Rem Selected</div>");
			var clearBtn = $("<div class='anet-btn anet-clear btn btn-small btn-yellow'><i class='fontello-icon-erase'></i> Clear</div>");
			var saveBtn = $("<div class='anet-btn anet-save btn btn-small btn-red'><i class='fontello-icon-floppy'></i> Save</div>");
			addRowBtn.click(function(){
				var me = $(this);
				var anetRow=_addRow(me);
				var _content = me.parent().parent().find(".anet-uibuilder-content").eq(0);
				_content.append(anetRow);
			});
			addRowBeforeBtn.click(function(){
				var me = $(this);
				var anetRow=_addRow(me);
				var anetEditableActive = me.parent().parent().find(".anet-uibuilder-content .anet-editable-active").eq(0);
				anetRow.insertBefore(anetEditableActive);
			});
			addRowAfterBtn.click(function(){
				var me = $(this);
				var anetRow=_addRow(me);
				var anetEditableActive = me.parent().parent().find(".anet-uibuilder-content .anet-editable-active").eq(0);
				anetRow.insertAfter(anetEditableActive);
			});
			moveRowUpBtn.click(function(){
				var obj = $(".anet-editable-active").eq(0);
				var obj2= $(obj).prevAll("div.anet-row:first");
				$(obj2).before(obj);
			});
			moveRowDownBtn.click(function(){
				var obj = $(".anet-editable-active").eq(0);
				var obj2= $(obj).nextAll("div.anet-row:first");				
				$(obj2).after(obj);
			});
			clearSelectedBtn.click(function(){
				var me = $(this);
				me.parent().parent().find(".anet-uibuilder-content .anet-editable-active").remove();
				me.parent().parent().parent().find(".anet-uibuilder-properties").eq(0).html('');
			});
			clearBtn.click(function(){
				var me = $(this);
				me.parent().parent().find(".anet-uibuilder-content").eq(0).html('');
				me.parent().parent().parent().find(".anet-uibuilder-properties").eq(0).html('');
			});
			saveBtn.click(function(){
				
				alert("Yet need to develope!");
				var data=$(".anet-uibuilder-content").html();
				var encodedData = window.btoa(encodeURIComponent(data));
				alert(encodedData);
				
				// var decodedData = window.atob(encodedData);
				// decodeURIComponent(decodedData)
				
				/*
				$.ajax({
					type: "POST",
					url: options.saveReportUIURL,
					data: qStr,
					success: function (data) {
						}
					});
				*/
			});
			
			toolbar.append(addRowBtn).append(addRowBeforeBtn).append(addRowAfterBtn).append(moveRowUpBtn).append(moveRowDownBtn).append(clearSelectedBtn).append(clearBtn).append(saveBtn);
			wrapper.append(toolbar);
			wrapper.append(content);
			
			layout.append(wrapper);
			layout.append(properties);
			html.push(
					layout
			);
			
			$el.append(html);
	    }
	    
	    
	    /************************** End Plugin Functionlity ************************************************/
	    
	    
	    
	    
	    function init() {
	    	_initAnetBuilder();
	    	hook('onInit');
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
		cssDir: "css",
		imgDir: "img",
		toolbar:{
			
			},
		saveReportUIURL:"/developer/savereportui",
		propertiesWindow:{
			propertiesLoadURL:"/developer/loadproperties",
			dataSource:{
				lookup:{
					lookupItem: "",
					dataSource: "",
					dlgLoadURL : "opendslookupdialog",
					dlgLoadUIURL : "/developer/loaddslookupdialog"
				}
			}
		},
		onInit: function() {},
		onDestroy: function() {}
	};
})(jQuery);
/* Code Booster : UI HELPER */

// cb_lookup

// cb_shuttle

/*  */
var gblBase = "";
function getDialogAdvance(_title, _remoteURL, _okButtonTxt, _cancelButtonTxt, _width, _height, _onLoadCallback, _callback, _hideOnOkBtnClick,_hideOverflow,_reloadOnCloseClick,_onCloseCallback){
    var _base = location.protocol + "//" + location.host + "/" + gblBase;
    var dlg =
    $('<div class="modal fade">' +
        '<div class="modal-dialog">' +
            '<div class="modal-content">' +
                '<div class="modal-header">' +
                    '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
                    '<h4 class="modal-title" style="display:inline-block; padding-right:20px;">' + _title + '</h4>' +
                '</div>' +
            '<div class="modal-body">' +
            '</div>' +
            '<div class="modal-footer">' +
                '<button type="button" id="okButton" class="btn btn-success">' +
                    _okButtonTxt +
                '</button>' +
                '<button type="button" class="btn btn-warning close-modal" data-dismiss="modal">' +
                    _cancelButtonTxt +
                '</button>' +
            '</div>' +
            '</div>' +
        '</div>' +
    '</div>'
    );

    dlg.find('#okButton').click(function (event) {
        _callback();
        
        if (typeof (_hideOnOkBtnClick) != "undefined" && _hideOnOkBtnClick == true) {
            dlg.modal('hide');
        }
    });

    dlg.modal('show');

    dlg.find('.modal-header').css({'cursor':'move'});
   // dlg.on('show.bs.modal', function () {
    
    dlg.find('.modal-body').load(_remoteURL, function (data) {
        setTimeout(function () {
            _onLoadCallback();
            dlg.find(".content-loader").hide();
            dlg.find("#okButton").removeClass("hide");
            dlg.find(".close-modal").removeClass("hide");
            
            //make me draggable
            dlg.find( ".modal-content" ).parent().parent().draggable({ handle: ".modal-header" });
            
        },200);
    });
        
   // }).modal();

    dlg.on('hidden.bs.modal', function () {
    	
        dlg.remove();
        if (typeof _reloadOnCloseClick !== 'undefined' && _reloadOnCloseClick == true) {
            location.reload();
        }
        if (typeof _onCloseCallback !== 'undefined') {
        	_onCloseCallback();
        }
        //
    });

    if (typeof (_hideOverflow) != "undefined" && _hideOverflow == true) {
        dlg.find('.modal-body').css({ 'overflow': 'hidden' });
    }

    dlg.find('.modal-dialog').css({
        width: ((_width != 'undefined' && _width != '') ? _width : 'auto'),
        height: ((_height != 'undefined' && _height != '') ? _height : 'auto'),
        'max-height': ((_height != 'undefined' && _height != '') ? _height : ($(window).height() - 20))
    });
    dlg.find('.modal-body').css({ 
		'overflow-y':'auto',
		'height':(_height==null)?'auto':_height,
		'min-height':100,
		'max-height':$(window).height()-200
	});
    
}
function initWidgetModal( _URL,_width,_onLoadCallback){

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
            dlg.find('.anet-widget-proxy .widget-header').css({'cursor':'move'});
            //make me draggable
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
function initDashboardModal( _URL,_width,_onLoadCallback){

    var dlg =
    $('<div class="modal fade" data-width="'+_width+'%" role="dialog">' +
        '<div class="modal-dialog">' +
            '<div class="modal-content">' +
            '<div class="anet-modal-header">' +
            '<a class="btn btn-glyph btn-link  fontello-icon-list-numbered-1"></a>' +
            '<a class="btn btn-glyph btn-link  fontello-icon-th-large anet-dashboard-scale"></a>' +
            '<a class="btn btn-glyph btn-link  fontello-icon-spin3"></a>' +
            '<a class="btn btn-glyph btn-link  fontello-icon-cancel-5" data-dismiss="modal" aria-label="Close"></a>' +
            '</div>' +
            '<div class="modal-body">' +
            '</div>' +
            '</div>' +
        '</div>' +
    '</div>'
    );
    
    //dlg.find('.modal-body').css({minHeight:(screen.availHeight-45)+'px'});
    //dlg.find('.modal-body').height(screen.availHeight-10);
    
   //before modal open
    dlg.on('show.bs.modal', function (e) {
    });
    
    //load modal
    dlg.find('.modal-body').load(_URL, function (data) {
    	
    	dlg.find('.modal-body').css({minHeight:(screen.availHeight-45)+'px'});
    	setTimeout(function () {
            _onLoadCallback();
            
            dlg.find('.anet-modal-header').css({'cursor':'move'});
            //make me draggable
            dlg.find( ".modal-content" ).parent().parent().draggable({ handle: ".anet-modal-header" });
            dlg.css("margin-left","-50.1%");
            //dlg.css("top","0");
            dlg.css({"top":dlg.find('.modal-body').eq(0).height()/2+30}); // TOP POSITION SETTLE
            
           
        },500);
    	
    });
  
    //after modal open
    dlg.on('shown.bs.modal', function (e) {
    	
	});
    
    //show modal
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
	
	dlg.find(".anet-dashboard-scale").on("click",function(){
				
		var txt = '<div class="anet-dashboard-scale-progress progress progress-success progress-mini filled3"><div class="filler"><div id="anet-dashboard-scale-range-bar" class="bar"> </div></div></div><div id="anet-dashboard-scale-range" class="anet-dashboard-scale-range"></div>';
		$.gritter.add({
    		title: '',
            text: txt,//'The record has been saved or updated successfully!',
			sticky: true,// if you want it to fade out on its own or just sit there
			time: 2000, // the time you want it to be alive for before fading out
			position: 'top-right',
			class_name: 'gritter-light notyfy_success',
			before_open:function(){
				
			},
			after_open:function(e){
				$("#anet-dashboard-scale-range").slider({
			        orientation: "horizontal",
			        range: "min",
			        min: 30,
			        max: 100,
			        value: 30,
			        slide: function( event, ui ) {
			        	// on slide call everytime
			            $( "#anet-dashboard-scale-range-bar" ).css("width",ui.value+'%' );
			            $(this).find('.ui-slider-handle').text(ui.value);
			            
			          //resize dashboard widget ration
			           // var ht = $(".highcharts-container").eq(0).height();
			            //ht =
			            ht = 800*(ui.value/100);
			            $(".highcharts-container").height(ht);
			        	window.dispatchEvent(new Event('resize'));
			        },
			        create: function(event, ui) {
			        	// first time initialize
			            var v=$(this).slider('value');
			            $(this).find('.ui-slider-handle').text(v);
			            $( "#anet-dashboard-scale-range-bar" ).css("width",v+'%' );
			        }
			    });
			}
			//before_close:function(e, manual_close){},
			//after_close:function(e, manual_close){}

		}); 
	});
	function initSlider()
	{
		alert('test');
	}
	
}
function fullScreen(element) {
    // Supports most browsers and their versions.
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
function exitfullScreen(element) {
    // Supports most browsers and their versions.
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
function maxWindow() {
    window.moveTo(0, 0);

    if (document.all) {
        top.window.resizeTo(screen.availWidth, screen.availHeight);
    }

    else if (document.layers || document.getElementById) {
        if (top.window.outerHeight < screen.availHeight || top.window.outerWidth < screen.availWidth) {
            top.window.outerHeight = screen.availHeight;
            top.window.outerWidth = screen.availWidth;
        }
    }
}
function confirmDlg(heading, question, cancelButtonTxt, okButtonTxt, callback,hideOnOkButtonClick,reloadOnCloseClick,changeStateOKButtonClick) {
    var _okBtn = "";
    var _cancleBtn = "";
    if (okButtonTxt != "") {
        _okBtn = '<a id="okButton" class="btn btn-danger btn-sm">' + okButtonTxt + '</a>';
    }
    if (cancelButtonTxt != "") {
        _cancleBtn = '<a class="btn btn-primary btn-sm" data-dismiss="modal">' + cancelButtonTxt + '</a>';
    }
    var _base = location.protocol + "//" + location.host + "/" + gblBase;
    var confirmModal =
      $('<div class="modal fade">' +
          '<div class="modal-dialog">' +
          '<div class="modal-content" style="background-color: #f5f5f5;">' +
          '<div class="modal-header" style="background-color: #006cf8;border: 1px solid #4257ce;">' +
            '<a class="close" data-dismiss="modal" style="background-color: #2342c8;padding: 5px 10px;border: 1px solid #ccdbff;" >&times;</a>' +
           '<h4 class="modal-title" style="display:inline-block; padding-right:20px;color:#ffffff;font-size: 16px;font-weight: bold;">' + heading + '</h4>' +
            '<img class="content-loader" style="display:none;" src="' + _base + 'administration/img/loading.gif" alt="loading..." />' +
            '<span class="modal-response"></span>' +
          '</div>' +

          '<div class="modal-body">' +
          	'<i class="fa fa-2x fa-info-circle" style="color: #357196;"></i> '+
            '<span class="modal-response1" style="font-size: 14px; font-weight:bold; margin-left: 10px;">' + question + '</span>' +
          '</div>' +

          '<div class="modal-footer" style="margin-top: 10px;padding:10px 20px;background-color: #dbdbdb;border-top: 1px solid #ccc;">' +
            _okBtn
            +
            _cancleBtn
            +
          '</div>' +
          '</div>' +
          '</div>' +
        '</div>');

    if (okButtonTxt != "") {
        confirmModal.find('#okButton').click(function (event) {
			if (typeof changeStateOKButtonClick == "undefined")
                changeStateOKButtonClick = 0;
            if ($.trim(changeStateOKButtonClick) == "")
                changeStateOKButtonClick = 0;

            switch (changeStateOKButtonClick) {
                case 1: // hide
                    $(this).hide();
                    break;
                case 2: // disabled
                    $(this).attr("disabled", "disabled");
                    break;
                default:
                    $(this).attr("disabled", "disabled");
                    break;
            }
            callback();
            if (typeof hideOnOkButtonClick !== 'undefined' && hideOnOkButtonClick == true)
            {
                confirmModal.modal('hide');
            }
        });
    }
    confirmModal.modal('show');

    confirmModal.on('hidden.bs.modal', function () {
        confirmModal.remove();
        if (typeof reloadOnCloseClick !== 'undefined' && reloadOnCloseClick == true) {
            location.reload();
        }
    });
}



function save(obj,_action)
{
	
	
	if(!validateForm('qForm'))
		return false;
	if (typeof tinymce !== 'undefined' && tinymce.activeEditor!=null)
	{
		//$("#taDescription").val(tinyMCE.get('taDescription').getContent());
	}
	if(_action=="insert")
		$("#btnSave").hide();
	//$("#taComment").val(tinymce.activeEditor.getContent());
	var options = { 
	target:        '#modal-response',   // target element(s) to be updated with server response 
	type: 		   'post',
	beforeSubmit:  showRequest,  // pre-submit callback 
	success:       showResponse  // post-submit callback
	};
	
	if (typeof tinymce != 'undefined')
		tinyMCE.triggerSave();
	
	// bind form using 'ajaxForm'
	$('#qForm').ajaxForm(options).submit(); 
	
	
}
// pre-submit callback 
function showRequest(formData, jqForm, options) { 
	$(".modal-loading").show();	
} 
// post-submit callback 
function showResponse(responseText, statusText, xhr, form)  {
	$(".modal-loading").hide();
	$("#btnSave").show();
	
	if($(".user-photo").length>0)
	{
		var dt = new Date();
		$(".user-photo").attr("src",$(".user-photo").attr("src")+"?q="+dt.getTime());
	}
	
	// this for article edit page
	if($(".edit_form #article_img_strip").length>0)
	{
		$.ajax({
		  url: "article/get_images/"+$("#qForm #article_id").val(),
		  cache: false,
		  success: function(html){
		    $(".edit_form #article_img_strip").html(html);
		  }
		});
	}
}
function showRequest1(formData, jqForm, options) {
	$("#loader").show(); 
} 
// post-submit callback 
function showResponse1(responseText, statusText, xhr, form)  {
	$("#loader").hide();
	//bResetDisplay = false;
	oTable.fnStandingRedraw();
	//bResetDisplay = true;
}
function modalClose(_id)
{
	$("#"+_id+"_modal-body").hide();
	if (typeof oTable != 'undefined')
	{
		oTable.fnStandingRedraw();
		//tinymce.activeEditor.destroy();
		//oTable.fnDraw();
	}
}
/*function messageBox(_msg="",_type="success")
{
	var msg =
    '<div class="alert alert-'+_type+'">' +
        '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>' +_msg +
    '</div>';
	return msg;
}*/
function validateForm(qForm)
{
	//alert(_action);
	var isSuccess=true;
	//validation
	$('#'+qForm+' .err-msg').text(''); 
	$('#'+qForm+' .err-msg-style').removeClass("err-msg-style");
	$('#'+qForm+' input,textarea,select').each(function(){
		var attr = $(this).attr('required');
		 if(typeof attr !== typeof undefined && attr !== false)
		 {
		 	//alert($(this).val()+":"+$(this).attr('id'));
		 	if(($(this).val()).trim()=="")
		 	{
		 		$(this).parent().find(".err-msg").eq(0).text("(This field cannot be empty)");
		 		$(this).addClass("err-msg-style");
				isSuccess=false;
				$(this).focus();
		 		return isSuccess;
			}
		 	
		 }
		 //return;
	});
	return isSuccess;
}

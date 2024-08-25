function pgReload(obj){
	getLookUpResult(document.getElementById("txtDialogSearch"));
}
function pgReloadByPageSize(obj){
	pgReload(obj);
}
function pgFirst(obj,tr,cp,ps)
{
	//tr = total records
	//cp = current page
	//ps = page size
	$(obj).parent().find(".page-number").eq(0).val('1');
	getLookUpResult(document.getElementById("txtDialogSearch"));
}
function pgLast(obj,tr,cp,ps)
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
	getLookUpResult(document.getElementById("txtDialogSearch"));
}
function pgNext(obj,tr,cp,ps)
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
	getLookUpResult(document.getElementById("txtDialogSearch"));
}
function pgPrev(obj,tr,cp,ps)
{
	var _cp = parseInt(cp);
	
	if((_cp-1)<1)
		_cp=1;
	else
		_cp=_cp-1;
	
	$(obj).parent().find(".page-number").eq(0).val(_cp);
	getLookUpResult(document.getElementById("txtDialogSearch"));
}
function rcDlgSearchResult(e, obj) {
    getLookUpResult(document.getElementById("txtDialogSearch"));
}
function getLookUpResult(obj) {

    var _finalJSONStr = "";
    if ($(obj).attr("data-where") != "") {
        var jsonOBJ = JSON.parse($(obj).attr("data-where"));
        if ($("#" + jsonOBJ.ValueFrom).length > 0)
            _finalJSONStr = $("#" + jsonOBJ.ValueFrom).val();

        jsonOBJ.ValueFrom = _finalJSONStr;
        _finalJSONStr = JSON.stringify(jsonOBJ);
    }
    var qStr = "searchBy=" + $('input[name=rdoRCDialog]:checked').val() + "&searchValue=" + obj.value + "&jsonReportControl=" + $("#txtDialogSearch").attr("data-reportcontrol") + "&where=" + _finalJSONStr;
    
    if($('select[name=pageSize]').length>0)
    	qStr += "&pageSz=" + $('select[name=pageSize]').val();
    if($('input[name=pageNumber]').length>0)
    	qStr += "&pageNum=" + $('input[name=pageNumber]').val();
    if($("#rc-dialog-insearch-checkbox").is(":checked"))
    	qStr += "&appinSch=true"; // Apply in search
    else
    	qStr += "&appinSch=false";
    
    $(".rc-dialog-lodder").show();
    //alert($(obj).attr("data-where"));
    var _url = location.protocol + "//" + location.host + "/" + location.pathname.split("/")[1] + "/report/loadlookupdialog";
    //if (location.host.contains("localhost"))//contains does not supported by IE
    if (location.host.indexOf("localhost")>=0)
        _url = location.protocol + "//" + location.host + $(obj).attr("data-ctx-path") + "/report/loadlookupdialog";

    //alert (_url);
    
    $.ajax({
        type: "GET",
        url: _url,
        data: qStr,
        cache:false,
        success: function (data) {
            $("#rc-dialog-data-table-dummy").show();
            $("#dialogSearchResult").html(data);
            $(".rc-dialog-lodder").hide();

            
            $("#rc-dialog-data-table-dummy td").eq(0).css("width", $("#dialogSearchResult .rc-dialog-data-table td").eq(0).width() + "px");
            $("#rc-dialog-data-table-dummy td").eq(1).css("width", $("#dialogSearchResult .rc-dialog-data-table td").eq(1).width() + "px");
            $("#rc-dialog-data-table-dummy td").eq(2).css("width", $("#dialogSearchResult .rc-dialog-data-table td").eq(2).width() + "px");
            
            
        },
        error: function (xhr, ajaxOptions, error) {
        }
    });
}
function lookup(_url, lookupItem, lookupSearchPanel, report) {
    //var report = $("#frmRCDynamic").attr("data-val");
    var _where_conditions = $("#" + lookupItem).attr("data-lookup-where");
    
    if (typeof _where_conditions !== typeof undefined && _where_conditions !== false) {
        //arr = JSON.parse(_where_conditions);
        if (_where_conditions != "")
            _where_conditions = encodeURIComponent(_where_conditions);
    }
    else { _where_conditions = ""; }
    if (_where_conditions != "")
        _where_conditions = "&where=" + _where_conditions;
    var qStr = "lookupItem=" + lookupItem + "&rpt=" + report+_where_conditions;
    //alert(_url+"?"+qStr);
    $.ajax({
        type: "GET",
        url: _url,
        data: qStr,
        success: function (data) {
        	//alert(data);
            if (data == "") {
                alert("Not Configured Yet!");
                return;
            }
            $("#"+lookupSearchPanel).append(data);
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
	                            var chkSelector = $("#" + lookupItem + "_dialog .rc-dialog-data-table").find(".chkRCDialogValSelector");
	                            var rdoSelector = $("#" + lookupItem + "_dialog .rc-dialog-data-table").find(".rdoRCDialogValSelector");
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
	                            if ($("#rc-dialog-search-checkbox").is(':checked')) {
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
                $(".rc-dialog-lodder").hide();
            });
        },
        error: function (xhr, ajaxOptions, error) {
        }
    });
}
function loadDialogSearchResult(e, obj) {
    if (e.keyCode == 13) {
        getLookUpResult(obj);
    }
}

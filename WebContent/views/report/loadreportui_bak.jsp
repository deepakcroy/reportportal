<%@page import="com.prp.models.SessionObject"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="net.aditri.ui.report.*"%>
<script>
	var _sysautopreview = 0;
	function getQueryStringValueByName(name) {
	    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	        results = regex.exec(location.search);
	    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	}
	_sysautopreview = getQueryStringValueByName("sysautopreview");
	if (_sysautopreview != 1) {
	    jQuery(window).load(function () {
	        // Page Preloader
	        jQuery('#status').fadeOut();
	        jQuery('#preloader').delay(350).fadeOut(function () {
	            jQuery('body').delay(350).css({ 'overflow': 'visible' });
	        });
	        
	    });
	}
	
    /********************************************* Dual ListBox Events Start *********************************************/
    function next(obj) {
        $("#"+$(obj).attr("data-val")+"_proxy option:selected").each(function(){
            $("#" + $(obj).attr("data-val")).append($(this).clone());
            $(this).remove(); 
        });
        $("#" + $(obj).attr("data-val") + "_proxy option:first-child").attr("selected", "selected");
    }
    function previous(obj) {
        $("#" + $(obj).attr("data-val") + " option:selected").each(function () {
            $("#" + $(obj).attr("data-val") + "_proxy").append($(this).clone());
            $(this).remove();
        });
        $("#" + $(obj).attr("data-val") + " option:first-child").attr("selected", "selected");
    }
    function previousAll(obj) {
        $("#" + $(obj).attr("data-val") + " option").each(function () {
            $("#" + $(obj).attr("data-val") + "_proxy").append($(this).clone());
            $(this).remove();
        });
        
    }
    function nextAll(obj) {

        $("#" + $(obj).attr("data-val") + "_proxy option").each(function () {
            $("#" + $(obj).attr("data-val")).append($(this).clone());
            $(this).remove();
        });
    }
    /********************************************* Dual ListBox Events End ***********************************************/
    $(document).ready(function () {
        

        //initialize rc-conditionltextbox
        $(".rc-conditionltextbox").each(function () {
            var str = "";
            if ($(this).attr("data-conditions") == null)
                str = "<select id='" + $(this).attr('id') + "_lst'><option value='>'>&gt;</option><option value='<'>&lt;</option><option value='='>=</option></select>";
            else {
                var codtns = $(this).attr("data-conditions").split(",");
                for (i = 0; i < codtns.length; i++) {
                    switch (codtns[i].toUpperCase())
                    {
                        case "ALL":
                            str += "<option value=''>All</option>";
                            break;
                        case "EQ":
                            str +="<option value='='>=</option>";
                            break;
                        case "GT":
                            str += "<option value='>'>&gt;</option>";
                            break;
                        case "LT":
                            str += "<option value='<'>&lt;</option>";
                            break;
                        case "GTE":
                            str += "<option value='>='>&gt;=</option>";
                            break;
                        case "LTE":
                            str += "<option value='<='>&lt;=</option>";
                            break;
                    }
                }
                str = "<select id='" + $(this).attr('id') + "_lst'>"+str+"</select>"
            }
            $(str).insertBefore($(this));
        });
        // initialize rc-datepicker
        $(".rc-datepicker").each(function () {
            var objDP = $(this);
            $("<div class='rc-datepicker-btn' onclick='pickDate(\"" + $(this).attr("id") + "\",this)'></div>").insertAfter($(this));
            
            $(objDP).next(".rc-datepicker-btn").click(function () {
                $(objDP).focus();
            });

            $(this).datepicker({
                numberOfMonths: 3,
                showButtonPanel: true,
                changeYear: true
            });
            if ($(this).attr("data-default") == null)
                $(this).datepicker('setDate', new Date());
            else {
                if ($(this).attr("data-default") == "")
                    $(this).val($(this).attr("data-default"));
                else {
                    //month:-1,date:end,year:0
                    var _date = new Date();
                    var year = _date.getFullYear();
                    var month = _date.getMonth();
                    var date = 1;
                    var dateParam = "date:end";

                    var params = $(this).attr("data-default").split(",");
                    if (params.length > 0) {
                        for (i = 0; i < params.length; i++) {
                            switch ((params[i].split(":")[0]).toUpperCase()) {
                                case "MONTH":
                                    var _str = trim(params[i].split(":")[1]);
                                    if (_str.indexOf('#') >= 0)//month:#3 indicates April will be selected : 0=January,1=February,...
                                    {
                                        
                                        if (month == 0 || month == 1) //january or february
                                            year = year - 1;

                                        month = parseInt(_str.substring(1, _str.length));
                                        
                                    }
                                    else
                                        month = _date.getMonth() + parseInt(params[i].split(":")[1]);
                                    break;
                                case "DATE":
                                    dateParam = params[i];
                                    break;
                                case "YEAR":
                                    // year = _date.getFullYear() + parseInt(params[i].split(":")[1]);
                                    year = year + parseInt(params[i].split(":")[1]);
                                    break;
                            }
                            var _tmpDParam = (dateParam.split(":")[1]).toUpperCase();
                            var _tmpDateParam = _tmpDParam;
                            var _tmpDate = 0;
                            if (_tmpDParam.indexOf("CURRENT") >= 0) {
                                if (_tmpDParam.indexOf("-") > 0) {
                                    _tmpDateParam = _tmpDParam.split("-")[0];
                                    _tmpDate = "-" + _tmpDParam.split("-")[1];
                                }
                                else if (_tmpDParam.indexOf("+") > 0) {
                                    _tmpDateParam = _tmpDParam.split("+")[0];
                                    _tmpDate = _tmpDParam.split("+")[1];
                                }
                            }
                            switch (_tmpDateParam) {
                                case "END":
                                    date = parseInt(getLastDateOfMonth(year, month));
                                    break;
                                case "START":
                                    date = 1;
                                    break;
                                case "CURRENT":
                                    date = _date.getDate() + parseInt(_tmpDate);
                                    break;
                                default:
                                    date = parseInt(dateParam.split(":")[1]);
                                    break;
                            }
                        }
                    }
                    $(this).val(month+"/"+date+"/"+year);
                    
                }
            }
        });

        // initialize rc-monthpicker
        $(".rc-monthpicker").each(function () {
            var objDP = $(this);


            $("<div class='rc-monthpicker-btn' ></div>").insertAfter($(this));

            $(objDP).attr("readonly","true");

            $(objDP).next(".rc-monthpicker-btn").click(function () {
                $(objDP).focus();
            });
            $(objDP).focus(function () {
                $(".ui-datepicker-calendar").css("display", "none");
            });

            var _formate = "M,yy";
            if ($(this).attr("date-formate") != null)
                _formate = $(this).attr("date-formate");

            var _date = new Date();
            var year = _date.getFullYear();
            var month = _date.getMonth();
            var date = 1;
            var dateParam="date:end";

            if ($(this).attr("default-date-selection") != null) {
                //month:-1,date:end,year:-1
                var params = $(this).attr("default-date-selection").split(",");
                
                if (params.length > 0) {
                    for (i = 0; i < params.length; i++) {
                        switch ((params[i].split(":")[0]).toUpperCase()) {
                            case "MONTH":
                                var _str=trim(params[i].split(":")[1]);
                                if (_str.indexOf('#') >= 0)//month:#3 indicates April will be selected : 0=January,1=February,2=March,...
                                    month = parseInt(_str.substring(1, _str.length));
                                else
                                    month = _date.getMonth() + parseInt(params[i].split(":")[1]);
                                break;
                            case "DATE":
                                dateParam = params[i];
                                break;
                            case "YEAR":
                                year = _date.getFullYear() + parseInt(params[i].split(":")[1]);
                                break;
                        }
                        switch ((dateParam.split(":")[1]).toUpperCase())
                        {
                            case "END":
                                date = parseInt(getLastDateOfMonth(year, month));
                                break;
                            case "START":
                                date = 1;
                                break;
                            default:
                                date = parseInt(dateParam.split(":")[1]);
                                break;
                        }
                    }
                }
            }
            
            
            $(this).datepicker({
                changeMonth: true,
                changeYear: true,
                showButtonPanel: true,
                dateFormat: _formate, //'MM,yy'
                onClose: function (dateText, inst) {
                    var _month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                    var _year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                    $(this).datepicker('setDate', new Date(_year, _month, 1));
                },
                defaultDate: new Date(year, month, date)
            });
            $(this).val(getMonthName(month) + "," + year);

        });
        //initialize rc-fystartdate //fiscal year start date
        $(".rc-fystartdate").each(function () {
            var dt = new Date();
            var _year = dt.getFullYear();
            if (dt.getMonth() < 3)
                _year = _year-1;

            if ($(this).attr("report-param") != null)
                $(this).attr("report-val", "3/1/" + _year);
            else
                $(this).attr("data-val", "3/1/" + _year);
        });
        //initialize rc-todate
        $(".rc-todate").each(function () {
            var dt=new Date();
            if ($(this).attr("report-param") != null)
                $(this).attr("report-val", (dt.getMonth()+1) + "/" + dt.getDate() + "/" + dt.getFullYear());
            else
                $(this).attr("data-val", (dt.getMonth()+1) + "/" + dt.getDate() + "/" + dt.getFullYear());
        });


        //rc-quarterpicker
        $(".rc-quarterpicker").each(function () {
            var qs = "<Select id='" + $(this).attr('id') + "' class='quarters'><option value='Q1'>Q1</option><option value='Q2'>Q2</option><option value='Q3'>Q3</option><option value='Q4'>Q4</option></select>";
            var yrs = "";
            var dt = new Date().getFullYear() + 1;
            for (i = dt; i > (dt - 10) ; i--) { //load Ten Years
                if(i==(dt-1))
                    yrs += "<option value='" + i + "' selected>" + i + "</option>";
                else
                    yrs += "<option value='" + i + "'>" + i + "</option>";
            }
            yrs = "<select id='" + $(this).attr('id') + "_yrs' class='years'>" + yrs + "</select>";
            $(this).append(qs + yrs);
            

        });
        
        // initialize rc-dualbox
        $(".rc-duallistbox").each(function () {
            var _id = $(this).attr("Id");
            $("<select id='" + _id + "_proxy' class='rc-duallistbox' multiple='multiple'>" + $(this).clone().html() + "</select>" + getDualBoxControls(_id)).insertBefore($(this).empty());
            $("#" + _id + "_proxy").width($(this).width());

            //on dbl click previous
            $(this).dblclick(function () {
                if ($("#" + _id + " option:selected").length > 0) {
                    $("#" + _id + " option:selected").each(function () {
                        $("#" + _id+"_proxy").append($(this).clone());
                        $(this).remove();
                    });
                }
            });
            //on dbl click next
            $("#" + _id + "_proxy").dblclick(function () {
                if ($("#" + _id + "_proxy option:selected").length > 0) {
                    $("#" + _id + "_proxy option:selected").each(function () {
                        $("#" + _id).append($(this).clone());
                        $(this).remove();
                    });
                }
            });
        });

        // initialize rc-lookup
        var report = $("#frmRCDynamic").attr("data-val");
        $(".rc-lookup").each(function () {
            //alert($(this));
            $("<div class='rc-lookup-btn' onclick='lookup(\"openlookupdialog\",\"" + $(this).attr("id") + "\",\"ReportContorlsFormWrapper\",\"" + report + "\")'></div>").insertAfter($(this));
        });
        //initialize rc-num-range
        $(".rc-num-range").each(function () {
            $(this).addClass("rc-num-range-ctrl");
            $("<span style='display:inline-block'>From &nbsp</span>").insertBefore($(this));
            $("<span style='display:inline-block;'>&nbsp To &nbsp</span><input class='rc-num-range-ctrl' name='" + $(this).attr("id") + "_to' id='" + $(this).attr("id") + "_to' />").insertAfter($(this));

            $(this).ForceNumericOnly();
            $("#" + $(this).attr('id') + "_to").ForceNumericOnly();
        });

        // initialize rc-combobox
        $(".rc-combobox").each(function () {
            if ($(this).attr("data-event") != null) {
                var arr = new Array();
                arr = $(this).attr("data-event").split(",params:[");
                //alert(arr[0].split(":")[1].toUpperCase());
                switch (arr[0].split(":")[1].toUpperCase()) {
                    case "SHOWHIDE":
                        $(this).change(function () {
                            // alert('t');
                            var str = arr[1].replace("]", "");
                            str = trim(str);
                            var xprsn = new Array();
                            xprsn = str.split(",");
                            for (i = 0; i < xprsn.length; i++) {
                                var ctrls = new Array();
                                ctrls = xprsn[i].split(":");
                                if ($(this).find('option:selected').text().toUpperCase() == trim(ctrls[0]).toUpperCase())
                                {
                                    var finalCtls = new Array();

                                    // show controls
                                    if (ctrls[1].match(/[^(]+(?=\))/g) != null) {
                                        finalCtls = (ctrls[1].match(/[^(]+(?=\))/g)[0]).split(" ");
                                        for (j = 0; j < finalCtls.length; j++) {
                                            $("#" + trim(finalCtls[j])).parent().show();
                                        }
                                    }

                                    //hide controls
                                    finalCtls = [];
                                    if (ctrls[2].match(/[^(]+(?=\))/g) != null) {
                                        finalCtls = (ctrls[2].match(/[^(]+(?=\))/g)[0]).split(" ");
                                        for (j = 0; j < finalCtls.length; j++) {
                                            $("#" + trim(finalCtls[j])).parent().hide();
                                        }
                                    }
                                }   
                            }
                        });
                        break;
                    case "FILLONCHANGE":
                        $(this).change(function () {
                            var params = arr[1].split(",")[0].replace("]", "").split(" ");
                            var filterFor = "";
                            var filterValue = "";
                            for (j = 0; j < params.length; j++) {
                                filterFor = params[j];
                                if(j==0)
                                    filterValue = $(this).val();
                                if (trim(filterValue) == "")
                                    filterValue = "_RC_EMPTY_";
                                var qStr = "report=" + $("#frmRCDynamic").attr("data-val") + "&filterValue=" + filterValue + "&filterFor=" + filterFor;
                                $.ajax({
                                    type: "GET",
                                    async: false,
                                    url: "Home/FillOnChangeReportControl",
                                    data: qStr,
                                    success: function (data) {
                                       // alert(filterFor);
                                        if (data != "") {
                                            $("#" + filterFor).html(data);
                                            filterValue = "";
                                            if ($("#" + filterFor).val() == "")//All
                                            {
                                                $("#" + filterFor + " option").not(":first").each(function () {
                                                    filterValue += $(this).attr("value") + ",";
                                                });
                                                filterValue = filterValue.replace(/^,|,$/g, '');
                                            }
                                            else
                                                filterValue = $("#" + filterFor).val();
                                        }
                                    },
                                    error: function (xhr, ajaxOptions, error) {
                                    }
                                });
                            }
                        });
                        break;
                }

            }

        });
        
        // initialize rc-lookup
        $(".rc-radio").each(function () {
            if ($(this).attr("data-event") != null) {
                var arr = new Array();
                arr = $(this).attr("data-event").split(",params:[");
                
                //alert(arr[0].split(":")[1].toUpperCase());
                switch (arr[0].split(":")[1].toUpperCase())
                {
                    case "HIDE":
                        $(this).change(function () {
                           // alert('t');
                            var str = arr[1].replace("]", "");
                            str = trim(str);
                            var ctrls = new Array();
                            ctrls = str.split(" "); //Multiple Controls Will be Separated with Single Space
                            for (i = 0; i < ctrls.length; i++) {
                                $("#" + ctrls[i]).parent().hide();
                            }
                        });
                        break;
                    case "SHOW":
                       // alert('t1');
                        $(this).change(function () {
                            var str = arr[1].replace("]", "");
                            str = trim(str);
                            var ctrls = new Array();
                            ctrls = str.split(" ");//Multiple Controls Will be Separated with Single Space
                            for (i=0;i<ctrls.length;i++) {
                                $("#" + ctrls[i]).parent().show();
                            }
                        });
                        break;
                    case "SHOWHIDE":
                        $(this).change(function () {
                            var str = arr[1].replace("]", "");
                            str = trim(str);
                            var ctrls = new Array();
                            ctrls = str.split(",");//Multiple Controls Will be Separated with Single Space
                            for (i = 0; i < ctrls.length; i++) {
                                if (ctrls[i].match(/[^(]+(?=\))/g) != null) {
                                    finalCtls = (ctrls[i].match(/[^(]+(?=\))/g)[0]).split(" ");
                                    
                                    if (ctrls[i].indexOf("show=")>=0)//Show
                                    {
                                        for (j = 0; j < finalCtls.length; j++) {
                                            $("#" + trim(finalCtls[j])).parent().show();
                                        }
                                    }
                                    else { //Hide
                                        for (j = 0; j < finalCtls.length; j++) {
                                            $("#" + trim(finalCtls[j])).parent().hide();
                                        }
                                    }
                                    
                                }
                            }
                            
                        });
                        break;
                    case "LABELCHANGE":
                        //  alert('t1');
                        $(this).change(function () {
                            var str = arr[1].replace("]", ""); //Multiple Controls Will be Separated with Single Space
                            str = str.split("{").join("");
                            str = str.split("}").join("");
                            str = trim(str);
                            var ctrls = new Array();
                            ctrls = str.split(",");
                            for (i = 0; i < ctrls.length; i++) {
                                var kv=new Array();
                                kv = ctrls[i].split(":");
                                $("#" + kv[0]).parent().find("label").eq(0).text(kv[1]);
                            }
                        });
                        break;
                    case "VALUECHANGE":
                        break;
                }
            }
        });
        //initialize rc-checkbox
        $(".rc-checkbox").each(function () {
            if ($(this).attr("data-event") != null) {
                var arr = new Array();
                arr = $(this).attr("data-event").split(",params:[");
                switch (arr[0].split(":")[1].toUpperCase()) {
                    case "SHOW":
                        $(this).change(function () {
                            var str = arr[1].replace("]", "");
                            str = trim(str);

                            var ctrls = new Array();
                            ctrls = str.split(" ");//Multiple Controls Will be Separated with Single Space
                            if ($(this).is(":checked") == true) {
                                for (i = 0; i < ctrls.length; i++) {
                                    $("#" + ctrls[i]).parent().show();
                                }
                            }
                            else {
                                for (i = 0; i < ctrls.length; i++) {
                                    $("#" + ctrls[i]).parent().hide();
                                }
                            }
                        });
                        break;
                    case "HIDE":
                        $(this).change(function () {
                            var str = arr[1].replace("]", "");
                            str = trim(str);

                            var ctrls = new Array();
                            ctrls = str.split(" ");//Multiple Controls Will be Separated with Single Space
                            if ($(this).is(":checked") == true) {
                                for (i = 0; i < ctrls.length; i++) {
                                    $("#" + ctrls[i]).parent().hide();
                                }
                            }
                            else {
                                for (i = 0; i < ctrls.length; i++) {
                                    $("#" + ctrls[i]).parent().show();
                                }
                            }
                        });
                        break;
                }
            }
        });

        //rc-fiscalyear
        $(".rc-fiscalyear").each(function () {
            //last 10 years
            var _opt = "";
            var dt = new Date();
            var curYear = dt.getFullYear();
            var selYear = dt.getFullYear();
            if (dt.getMonth() >= 3)
                curYear = curYear + 1;
            for (var i = 0; i < 10; i++) {
                if (selYear==(curYear - i))
                    _opt += "<option value='" + (curYear - i) + "' Selected>" + (curYear - i) + "</option>";
                else
                    _opt += "<option value='" + (curYear - i) + "'>" + (curYear - i) + "</option>";
            }
            $(this).html(_opt);
           
        });

        //rc-checklist
        $(".rc-checklist").each(function () {
            var _chkList = "";
            if ($(this).attr("data-items") != null) {
                var arr = new Array();
                arr = $(this).attr("data-items").match(/[^{]+(?=\})/g);
                //alert(arr);
                for (i = 0; i < arr.length; i++) {
                    
                    var arr1 = new Array();
                    arr1 = arr[i].split(",");
                    _chkList += "<li><input type='checkbox' id='" + arr1[1].split(":")[1] + "' data-val='" + arr1[1].split(":")[1] + "'></input>" + arr1[0].split(":")[1] + "</li>";
                }
                $(this).parent().find("label").eq(0).css("width", "0px");
                $(this).css("min-width","100px");
                $("<ul class='rc-checklist-items'>" + _chkList + "</ul>").insertAfter($(this));

                $(this).change(function () {
                    var _ul = $(this).parent().find(".rc-checklist-items").eq(0);
                    if ($(this).is(':checked')) {
                        $(_ul).find("input[type=checkbox]").each(function () {
                            $(this).attr("checked", true);
                        });
                    }
                    else {
                        $(_ul).find("input[type=checkbox]").each(function () {
                            $(this).removeAttr("checked");
                        });
                    }
                });
                
            }
        });
        function getDualBoxControls(obj) {
            var str = "";
            str = "<div style='display:inline-block; margin-left:4px; margin-right:4px;'><input type='button' class='anet-duallistbox-ctrl' data-val='" + obj + "' id='" + obj + "Next' onclick='next(this)' value='&rarr;' />";
            str += "<input type='button' class='anet-duallistbox-ctrl' data-val='" + obj + "' id='" + obj + "NextAll' onclick='nextAll(this)' value='&rArr;' />";
            str += "<input type='button' class='anet-duallistbox-ctrl' data-val='" + obj + "' id='" + obj + "Previous' onclick='previous(this)' value='&larr;' />";
            str += "<input type='button' class='anet-duallistbox-ctrl' data-val='" + obj + "' id='" + obj + "PreviousAll' onclick='previousAll(this)' value='&lArr;' /></div>";
            return str;
        }
    });
    function fillReportControl(ctrlId, ctrlType, ctrlData) {
    	//alert(ctrlId);
    	//alert(ctrlData);
        //var decoded = $("<div/>").html(ctrlData).text(); // for .Net
        var decoded = ctrlData;
        switch (ctrlType) {
            case "DropDown":
                $("#" + ctrlId).html(decoded);
                break;
            case "ListBox":
                $("#" + ctrlId).html(decoded);
                break;
            case "Shuttle":
                $("#" + ctrlId+"_proxy").html(decoded);
               // alert($("#" + ctrlId+"_proxy").length);
                break;
            case "Lookup":
                alert(ctrlData);
                break;
        }
    }
    
    /* var niceScrollFrmContent;
    $(function () {

        niceScrollFrmContent = $("#frmContentInner").niceScroll({
            preservenativescrolling: false,
            horizrailenabled: false,
            cursorwidth: '8px',
            cursorborder: 'none',
            cursorborderradius: '0px',
            cursorcolor: "#cccccc",
            autohidemode: false,
            background: "#ebebeb"
        });

        $("#frmContentInner").css("overflow-x", "hidden");
    }); */
</script>


<div id="ReportContorlsFormWrapper">
    <div id="ReportContorlsForm">
        <form id="frmRCDynamic" data-val="${objReportUI.getReportId()}" data-procedure="${objReportUI.getProcedureName()}" data-rdl="${objReportUI.getReportName()}" method="get" action="/">
           <div class="frmContent well well-nice">
               <h5 id="hRptTitle" style="display:inline-block;">${objReportUI.getReportHeader()}</h5>
               <span id="rptPreviewer"></span>
               <div style=" font-style:italic; font-size:11px; padding-left:10px; padding-top:5px; padding-bottom:5px; border-bottom:1px solid silver; "><div id="rptDesc" style="display:inline-block; max-width:95%; margin-left:-10px; white-space: nowrap;overflow: hidden;text-overflow:ellipsis;">${objReportUI.getReportDescription()}</div></div>
               <div id="frmContentInner" style='padding-top:5px;'>
       
       			${objReportUI.getFormBody()}
       
                </div>
               <div class="anet-datarow" id="anet-rpt-ctrls" style="padding-top:2px;">
                    <label></label>
                    <input type="submit" id="btnRCSubmit"  class="anet-submit" value="Show Report" />
                    <input type="button" id="btnLoadDefault"  class="anet-submit" style="width:67px;" value="Reset" onclick="resetCurrentUI()" />
                    <input type="button" id="btnRCExportAs" onclick="exportReportAS(this)"  class="anet-submit" value="Export As" />
                    <input type="button" id="btnRCEmailAs" onclick="emailReportAS(this)"  class="anet-submit" value="Email As" />
                    <!--<input type="button" id="btnRCExportPDF"  class="anet-submit" value="Export PDF" />
                    <input type="button" id="btnRCExportExcel"  class="anet-submit" value="Export Excel" />-->
                   <img id="btnJobSetting" onclick="getJobExecutions(this)" data-jobmode="false" title="Scheduling This Report" style=" display:block; float:right; cursor:pointer; width:20px;" src="${pageContext.request.contextPath}/assets/img/system/setting.png" />
               </div>
           </div>
        </form>
        <script>
        
		$("#rptHolder").uiconverter();

		</script>
		
		
        <%
        ReportUI oReportUI=null;
        if (request.getAttribute("objReportUI")!=null)
        	oReportUI = (ReportUI)request.getAttribute("objReportUI");
        if(oReportUI!=null)
        {
        	if(oReportUI.getReportControls()!=null)
        	{
	        	for(ReportControl obj:oReportUI.getReportControls())
	        	{
	        		%>
	        		<script>
	                fillReportControl("<%=obj.getId()%>", "<%=obj.getControlType()%>", "<%=obj.getControlData()%>")
	                </script>
	        		<%
	        	}
        	}
        }
        %>
        
        
        
        
    </div>
    <div id="reportExecutionWrapper">
        <form name="reportExecutionFRM" id="reportExecutionFRM" target="_blank" action="" data-base="http://localhost:8010/advance-birt/frameset" method="post" data-skey="${sKey}" data-ukey="${uKey}" >
            <input id="hdnReportExecutionQStr" type="hidden" name="qStr" value="" />
        </form>
    </div>
    <!-- ---------------------------------- EMAIL/OTHER DIALOG FORM START --------------------------------------- -->
    <div id="dialog-form-email" title="Send Mail" data-emailas="EXCEL" style="display:none;"></div>
    <div id="jobdetail_dialog"  style="display:none;"></div>
    <div id="joblog_dialog"  style="display:none;"></div>
    
    <!-- ---------------------------------- EMAIL/OTHER DIALOG FORM END ----------------------------------------- -->
</div>


<script>
function initReportGateWay() {
    var _procedureName = "";
    var _reportName = "";
    ${objReportUI.getReportGateway()}
    //alert(_procedureName);
    var arrRst = new Array();
    arrRst[0] = _procedureName;
    arrRst[1] = _reportName;

    return arrRst;
}
</script>
<%-- <script src="${pageContext.request.contextPath}/assets/js/net.aditri.ui.common.js"></script> --%>
<%-- <script src="${pageContext.request.contextPath}/assets/js/loadReportUI.js"></script> --%>
<%-- <script src="${pageContext.request.contextPath}/assets/js/net.aditri.lookup.js"></script> --%>



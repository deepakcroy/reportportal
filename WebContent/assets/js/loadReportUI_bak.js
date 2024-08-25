function prepareRptParams(){
    var oReportSnippet = "";
    var _ReportName = $("#frmRCDynamic").attr("data-rdl");
    $(".rc-reportparam").each(function (i) {
        if ($(this).hasClass("rc-checklist")) {
            var str = "";
            $(".rc-checklist-items input[type='checkbox']").each(function () { if ($(this).is(":checked")) { str += $(this).attr('data-val') + ","; } });
            oReportSnippet += "{" + "\"name" + "\":" + "\"" + $(this).attr("report-param") + "\"," + "\"value" + "\":" + "\"" + str + "\"},"
        }
        else if ($(this).attr('type') != null && $(this).attr('type') == "checkbox") {
        	oReportSnippet += "{" + "\"name" + "\":" + "\"" + $(this).attr("report-param") + "\"," + "\"value" + "\":" + "\"" + ($(this).is(':checked') ? "Yes" : "No") + "\"},"
        }
        else if($(this).hasClass("rc-duallistbox"))
    	{
        	var optVals="";
        	$(this).find("option").each(function(i){
        		optVals +=  $(this).val()+",";
        	});
        	optVals = optVals.substr(0,optVals.length-1);
        	oReportSnippet += "{" + "\"name" + "\":" + "\"" + $(this).attr("report-param") + "\"," + "\"value" + "\":" + "\"" + optVals + "\"},"
    	}
        else {
            if ($(this).attr("report-val") != null)
            	oReportSnippet += "{" + "\"name" + "\":" + "\"" + $(this).attr("report-param") + "\"," + "\"value" + "\":" + "\"" + $(this).attr("report-val") + "\"},"
            else {
            	oReportSnippet += "{" + "\"name" + "\":" + "\"" + $(this).attr("report-param") + "\"," + "\"value" + "\":" + "\"" + $(this).val() + "\"},"
            }
        }

    });
    //hRptTitle
    oReportSnippet = oReportSnippet.substring(0, oReportSnippet.length - 1);
    oReportSnippet = "{" + "\"reportName" + "\":" + "\"" + $("#frmRCDynamic").attr("data-val") + "\"," + "\"reportTitle" + "\":" + "\"" + $("#hRptTitle").text() + "\"," + "\"reportFile" + "\":" + "\"" + _ReportName + "\"," + "\"params" + "\": [" + oReportSnippet + "]}";
    return oReportSnippet;
}
/*
 * function name: prepareQStr
 * It is replace of prepareReportUrl()
 * */
function prepareQStr()
{
	var _qStr="";
	var _tmpQStr = "";
	var _tmpVal = "";
	// rc-duallistbox
    $(".rc-duallistbox").each(function (i) {
    	var attrName = $(this).attr('name');
        if (typeof attrName !== typeof undefined && attrName !== false) {
            _tmpQStr += $(this).attr("name") + "=";
            $(this).find("option").each(function (i) {
            	_tmpVal += $(this).attr("value") + ",";
            });
            _tmpVal = _tmpVal.replace(/^,|,$/g, ''); //   ^, matches the comma at the start of the string and ,$ matches the comma at the end 
            _tmpQStr = _tmpQStr + _tmpVal.substring(0, _tmpVal.length) + "&";
        }
    });
    _qStr = _qStr+_tmpQStr;
    // rc-lookup
    _tmpQStr = "";
	_tmpVal = "";
    $(".rc-lookup").each(function (i) {
    	var attrName = $(this).attr('name');
    	if (typeof attrName !== typeof undefined && attrName !== false) {
    		_tmpQStr += $(this).attr("name") + "=";
            _tmpVal += $(this).val().replace(/^,|,$/g, '');
            _tmpQStr = _tmpQStr + _tmpVal.substring(0, _tmpVal.length) + "&";
    	}
    });
    _qStr = _qStr+_tmpQStr;
 // rc-lookup
    _tmpQStr = "";
	_tmpVal = "";
    $(".rc-textbox").each(function (i) {
    	var attrName = $(this).attr('name');
    	if (typeof attrName !== typeof undefined && attrName !== false) {
    		_tmpQStr += $(this).attr("name") + "=";
            _tmpVal += $(this).val().replace(/^,|,$/g, '');
            _tmpQStr = _tmpQStr + _tmpVal.substring(0, _tmpVal.length) + "&";
    	}
    });
    _qStr = _qStr+_tmpQStr;
    _qStr = _qStr.substring(0, _qStr.length-1);
    return  _qStr; 
}
/* function name: prepareReportUrl
 * Obsolete
 * */
function prepareReportUrl() {
    //Code Block Formation: Code Block Must Be Initialized At the beginning of initialization of all controls
    $(".rc-codeblock").each(function (i) {
        eval(trim($(this).text()));
    });

    var _sqlProcedure = "";
    var _ReportName = "";

    var arrRst = new Array();
    arrRst = initReportGateWay();

    //exit();
    if (arrRst[0] != "")//Procedure
        _sqlProcedure = arrRst[0];
    else
        _sqlProcedure = $("#frmRCDynamic").attr("data-procedure");

    if (arrRst[1] != "")//Report
        _ReportName = arrRst[1];
    else
        _ReportName = $("#frmRCDynamic").attr("data-rdl");


    if (_sqlProcedure == "") {
        return "No procedure is set yet!";
    }
    if (_ReportName == "") {
        return "No RDLName is set yet!";
    }
   
    // checkbox
    var jsonObj = "";
    $(".rc-checkbox:input[type=checkbox]").each(function (i) {
        if ($(this).attr("data-param") != null) {
            var _val = "yes";
            if ($(this).attr("data-valyes") != null && $(this).is(':checked'))
                _val = $(this).attr("data-valyes");
            else if ($(this).attr("data-valno") != null && !$(this).is(':checked'))
                _val = $(this).attr("data-valno");
            else {
                _val = ($(this).is(":indeterminate")?"":($(this).is(':checked') ? "Yes" : "No"));
            }

            jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + _val + "\"},"
        }
    });

    // rc-duallistbox
    var dlbQStr = "";
    $(".rc-duallistbox").each(function (i) {
        if ($(this).attr("name") != "") {
            var dlbVals = "";
            dlbQStr += $(this).attr("name") + "=";
            $(this).find("option").each(function (i) {
                dlbVals += $(this).attr("value") + ",";
            });
            dlbVals = dlbVals.replace(/^,|,$/g, ''); //   ^, matches the comma at the start of the string and ,$ matches the comma at the end 
            dlbQStr = dlbQStr + dlbVals.substring(0, dlbVals.length - 1) + "&";
            if ($(this).attr("data-param") != null)
                jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + dlbVals + "\"},"
        }
    });

    // rc-textbox
    $(".rc-textbox").each(function (i) {

        if (!validateRequiredField($(this)))
            exit();

        if ($(this).attr("data-param") != null)
            jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
    });

    //rc-conditionltextbox
    $(".rc-conditionltextbox").each(function (i) {
        if ($(this).attr("data-param") != null) {
            //if ($(this).attr("data-required") != null) {
            //}
            jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + trim($(this).val()) + "\"},"
            jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-condition-param") + "\"," + "\"Value" + "\":" + "\"" + trim($("#" + $(this).attr('id') + "_lst").val()) + "\"},"
        }
    });

    // rc-datepicker
    $(".rc-datepicker").each(function (i) {
        if ($(this).attr("data-param") != null)
            jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
    });
    // rc-monthpicker
    $(".rc-monthpicker").each(function (i) {
        if ($(this).attr("data-param") != null) {
            if ($(this).attr("date-selection-type") != null) {
                var _year = trim($(this).val().split(",")[1]);
                var _month = getMonthCode(trim($(this).val().split(",")[0]));
                var _date = getLastDateOfMonth(_year, _month);
                var _val = "";
                switch (trim($(this).attr("date-selection-type").toUpperCase())) {
                    case "LAST-MONTH-DATE":
                        _val = (_month + 1) + "/" + _date + "/" + _year;
                        break;
                    case "FIRST-MONTH-DATE":
                        _val = (_month + 1) + "/" + 1 + "/" + _year;
                        break;
                    default:
                        _val = (_month + 1) + "/" + 1 + "/" + _year;
                        break;
                }
                //alert(_val);
                jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + _val + "\"},"
            }
            else
                jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
        }
        else {
            if ($(this).attr("data-param-month") != null && $(this).attr("data-param-year") != null) {
                jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param-month") + "\"," + "\"Value" + "\":" + "\"" + trim($(this).val().split(",")[0]) + "\"},"
                jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param-year") + "\"," + "\"Value" + "\":" + "\"" + trim($(this).val().split(",")[1]) + "\"},"
            }
        }
    });
    // rc-quarterpicker
    $(".rc-quarterpicker").each(function (i) {
        if ($(this).attr("data-param-quarter") != null && ($(this).attr("data-param-start") != null || $(this).attr("data-param-end") != null)) {

            var qtr = $(this).find(".quarters").eq(0).val();
            var sdt = $(this).find(".years").eq(0).val(); //start year
            var edt = sdt;  //end year
            var dt = new Date();
            if (sdt == "")
                sdt = dt.getFullYear() + "";

            switch (qtr.toUpperCase()) {
                case "Q1":
                    sdt = "3/1/" + sdt;
                    edt = new Date(dt.getFullYear, 3 + 3, 1) + "";//Adding 3 Moth to reach next quarter
                    break;
                case "Q2":
                    sdt = "6/1/" + sdt;
                    edt = new Date(dt.getFullYear, 6 + 3, 1) + "";
                    break;
                case "Q3":
                    sdt = "9/1/" + sdt;
                    edt = new Date(dt.getFullYear, 9 + 3, 1) + "";
                    break;
                case "Q4":
                    sdt = "12/1/" + sdt;
                    edt = new Date(dt.getFullYear + 1, 3, 1) + "";
                    break;
            }
            jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param-quarter") + "\"," + "\"Value" + "\":" + "\"" + qtr + "\"},"

            if ($(this).attr("data-param-start") != null && $(this).attr("data-param-start") != "") //start-date
                jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param-start") + "\"," + "\"Value" + "\":" + "\"" + sdt + "\"},"

            if ($(this).attr("data-param-end") != null && $(this).attr("data-param-end") != "") //end-date
                jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param-end") + "\"," + "\"Value" + "\":" + "\"" + edt + "\"},"
        }
    });
    // rc-combobox
    $(".rc-combobox").each(function (i) {
        if ($(this).attr("data-param") != null)
            jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
    });
    // rc-fiscalyear
    $(".rc-fiscalyear").each(function (i) {
        if ($(this).attr("data-param") != null) {
            if ($(this).attr("data-is-fy-start-date") != null)
                jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + ("3/1/" + $(this).val()) + "\"},"
            else
                jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
            //jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
        }
    });
    // rc-radio
    $(".rc-radio:radio:checked").each(function (i) {
        if ($(this).attr("data-param") != null)
            jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).attr("data-val") + "\"},"
    });
    //rc-num-range
    $(".rc-num-range").each(function (i) {
        if ($(this).attr("data-param") != null) {
            var params = new Array();
            params = $(this).attr("data-param").split(",");
            if (params.length > 1) {
                jsonObj += "{" + "\"Name" + "\":" + "\"" + trim(params[0]) + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
                jsonObj += "{" + "\"Name" + "\":" + "\"" + trim(params[1]) + "\"," + "\"Value" + "\":" + "\"" + $("#" + $(this).attr("Id") + "_to").val() + "\"},"
            }
        }
    });
    // rc-lookup
    $(".rc-lookup").each(function (i) {
        if (!validateRequiredField($(this)))
            exit();


        if ($(this).attr("data-param") != null)
            jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).val().replace(/^,|,$/g, '') + "\"},"  //replace(/^,|,$/g, '') omitting the start and ending ,
    });

    // rc-dataparam
    $(".rc-dataparam").each(function (i) {
        if ($(this).attr("data-param") != null)
            jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).attr("data-val") + "\"},"
    });
    //User_Id
    jsonObj += "{" + "\"Name" + "\":" + "\" user_id \"," + "\"Value" + "\":" + "\"" + $("#loggedUser").attr("data-networkalias") + "\"},";
    //$("#frmRCDynamic").attr("data-procedure")
    jsonObj = "{" + "\"Name" + "\":" + "\"" + _sqlProcedure + "\"," + "\"Params" + "\": [" + jsonObj + "]}";
    jsonObj = encodeURIComponent(jsonObj);

    var rdlReportObj = "";
    $(".rc-reportparam").each(function (i) {
        if ($(this).hasClass("rc-checklist")) {
            var str = "";
            $(".rc-checklist-items input[type='checkbox']").each(function () { if ($(this).is(":checked")) { str += $(this).attr('data-val') + ","; } });
            rdlReportObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("report-param") + "\"," + "\"Value" + "\":" + "\"" + str + "\"},"
        }
        else if ($(this).attr('type') != null && $(this).attr('type') == "checkbox") {
            rdlReportObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("report-param") + "\"," + "\"Value" + "\":" + "\"" + ($(this).is(':checked') ? "Yes" : "No") + "\"},"
        }
        else {
            if ($(this).attr("report-val") != null)
                rdlReportObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("report-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).attr("report-val") + "\"},"
            else {
                rdlReportObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("report-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
            }
        }

    });
    //hRptTitle
    rdlReportObj = rdlReportObj.substring(0, rdlReportObj.length - 1);
    rdlReportObj = "{" + "\"ReportNumber" + "\":" + "\"" + $("#frmRCDynamic").attr("data-val") + "\"," + "\"ReportTitle" + "\":" + "\"" + $("#hRptTitle").text() + "\"," + "\"RDLName" + "\":" + "\"" + _ReportName + "\"," + "\"Params" + "\": [" + rdlReportObj + "]}";
    //GET Report Params: End
    // alert(jsonObj);
    // exit();
    var redirect = "/MRMReports/ReportView.aspx?spParams=" + jsonObj + "&rptParams=" + rdlReportObj;
    var _tmpLink = location.pathname.replace(/^[/]|[/]$/g, '');
    if (_tmpLink != "") {
        redirect = "/" + _tmpLink + "/MRMReports/ReportView.aspx?spParams=" + jsonObj + "&rptParams=" + rdlReportObj;
    }
    // window.open(redirect, "_blank");

    var qStr = "url=" + _tmpLink + "&spParams=" + jsonObj + "&rptParams=" + rdlReportObj;

    return qStr;
}
function submitReportForm() {
    if ($.browser.msie && $.browser.version < 9 && _sysautopreview!=1) {
        var params = [
            'height=' + (screen.availHeight - 85).toString(),
            'width=' + (screen.availWidth - 10).toString(),
            'top=0',
            'left=0',
            'resizable=yes',
            'scrollbars=yes',
            'toolbar=no',
            'menubar=no',
            'location=yes'
        ].join(',');
        var _popup = 'popup' + new Date().getTime();
        window.open('about:blank', _popup, params);
        document.reportExecutionFRM.setAttribute('target', _popup);
        document.reportExecutionFRM.setAttribute('onsubmit', '');
        document.reportExecutionFRM.submit();
    }
    else {
        if (_sysautopreview == 1)
            $("#reportExecutionFRM").attr("target", "_self");
        else
            $("#reportExecutionFRM").attr("target", "_blank");

        $("#reportExecutionFRM").submit();
    }
}

function setControlsDefaultOrSavedValue(myObject) {
    for (i in myObject) {
        var ctrl = trim(myObject[i]["Type"]);
        // alert("---|"+ctrl+"|---");
        //alert(ctrl);
        switch (ctrl) {
            case "TEXTBOX":
                $("#" + myObject[i]["Id"]).val(myObject[i]["Value"]);
                break;
            case "CONDITIONALTEXTBOX":
                //$("#" + myObject[i]["Id"] + "_lst").val();
                $("#" + myObject[i]["Id"]).val((myObject[i]["Value"]));
                break;
            case "CHECKBOX":
                if (myObject[i]["Value"] == "")
                {
                    $("#" + myObject[i]["Id"]).attr('indeterminate', true);
                }
                else if (myObject[i]["Value"] == "Yes") {
                    $("#" + myObject[i]["Id"]).attr('checked', 'checked');
                }
                else {
                    $("#" + myObject[i]["Id"]).removeAttr('checked');
                }
                break;
            case "RADIO":
                if (myObject[i]["Value"] == "Yes")
                    $("#" + myObject[i]["Id"]).attr('checked', 'checked');
                else
                    $("#" + myObject[i]["Id"]).removeAttr('checked');
                break;
            case "COMBOBOX":
                $("#" + myObject[i]["Id"]).val(myObject[i]["Value"]);
                break;
            case "DUALLISTBOX":
                if (myObject[i]["Value"] != "") {
                    var vals = myObject[i]["Value"].split(",");
                    for (j in vals) {
                        if (vals[j] != "")
                            $("#" + myObject[i]["Id"]).append($("#" + myObject[i]["Id"] + "_proxy option[value=" + vals[j] + "]"));
                    }
                }
                break;
            case "MONTHPICKER":
                $("#" + myObject[i]["Id"]).val(myObject[i]["Value"]);
                //$("#" + myObject[i]["Id"]).datepicker('setDate', new Date(parseInt(myObject[i]["Value"].split(',')[1]), parseInt(getMonthCode(myObject[i]["Value"].split(',')[0])), 1));
                break;
            case "DATEPICKER":
                $("#" + myObject[i]["Id"]).val(myObject[i]["Value"]);
                break;
            case "LOOKUP":
                $("#" + myObject[i]["Id"]).val(myObject[i]["Value"]);
                break;
            case "NUMRANGE":
                $("#" + myObject[i]["Id"]).val(myObject[i]["Value"]);
                break;
            default:
                break;
        }
    }

}


function exportReport(obj)
{
    var exportType = trim($(obj).text());
    var qStr = "rptParams="+prepareRptParams();
    //console.log(qStr);exit;
    var _tmpLink = location.pathname.replace(/^[/]|[/]$/g, '');
    var arr = _tmpLink.split("/");
    _tmpLink = arr[0];
    if (exportType == "Excel (Data)")
        redirect = _tmpLink + "/report/exportexceldata?" + qStr;
    else if(exportType == "PDF")
        redirect = _tmpLink + "/report/exportpdf?" + qStr;
    else
    	redirect = _tmpLink + "/report/exportexcel?" + qStr;
    
    window.location.href = redirect + "&sysExportType=" + trim($(obj).text());
    
   
    /*$.ajax({
        type: "POST",
        url: "Home/CompressAndLoadUrl",
        data: qStr,
        cache:false,
        success: function (data) {
            //alert(data);
            var _tmpLink = location.pathname.replace(/^[/]|[/]$/g, '');
            if (_tmpLink != "") {
                if (exportType != "Excel (Data)")
                    redirect = "/" + _tmpLink + "/report/exportexcel?qStr=" + data;
                else
                    redirect = "/" + _tmpLink + "/report/exportexceldata?qStr=" + data;
            }
            else {
                if (exportType != "Excel (Data)")
                    redirect = "/report/exportexcel?qStr=" + data;
                else
                    redirect = "/report/exportexceldata?qStr=" + data;
            }
            window.location.href = redirect + "&sysExportType=" + trim($(obj).text());
            //window.open(redirect + "&sys_export_type=" + trim($(obj).text()), "_blank");
        },
        error: function (xhr, ajaxOptions, error) {
            alert(error);
        }
    });*/
}
function exportReportAS(obj) {
    if ($("#menuExportAs").length > 0) {
        $("#menuExportAs").remove();
    }
    else {

        if ($("#menuEmailAs").length > 0)
            $("#menuEmailAs").remove();

        var str = "";
        str = "<div id='menuExportAs' style='min-width:150px; height:60px; border:1px solid #ccc; border-radius:5px; padding:5px; position:absolute;z-index:100; margin-top:-99px; margin-left:303px; background-color:#fff; '><ul id='rc-exportas-menu'><li onclick='exportReport(this)'><a href='#'><span class='menu-ico-excel'></span>Excel (Data)</a></li><li onclick='exportReport(this)'><a href='#'><span class='menu-ico-excelf'></span>Excel (Formatted)</a></li><li onclick='exportReport(this)'><a href='#'><span class='menu-ico-pdf'></span>PDF</a></li></ul></div>";
        $(str).insertAfter(obj);
    }
}
function emailReport(obj) {
    var exp_type = trim($(obj).text());
    $("#dialog-form-email").html('<iframe id="sysEmailFrmContainer" frameborder="0" scrolling="no" width="100%" height="330" src="./Home/GetEmailForm?attachmentType=' + exp_type + '"></iframe>');
    $("#dialog-form-email").dialog({
        //autoOpen: false,
        position: 'center',
        width: 980,
        height: 440,
        modal: true,
        buttons: {
            Send: function () {
                //Valid Email
                if (validateMultipleEmails($("#sysEmailFrmContainer").contents().find("#txt_SysEmailUserTo").eq(0), ',')) {
                    var qStr = prepareReportUrl();
                    $("#sysEmailFrmContainer").contents().find("#img_SysLoader").show();
                    $.ajax({
                        type: "POST",
                        url: "Home/CompressAndLoadUrl",
                        data: qStr,
                        success: function (data) {
                            qStr = "qStr=" + data + "&sys_export_type=" + exp_type + "&sys_email_to=" + $("#sysEmailFrmContainer").contents().find("#txt_SysEmailUserTo").val() + "&sys_email_subject=" + $("#sysEmailFrmContainer").contents().find("#txt_SysEmailUserSubject").val() + "&sys_email_body=" + $("#sysEmailFrmContainer").contents().find('iframe').eq(0).contents().find("#editor").html();
                            $.ajax({
                                type: "POST",
                                url: "Home/SendEmail", //ExportReport
                                data: qStr,
                                success: function (data) {
                                    $("#sysEmailFrmContainer").contents().find("#img_SysLoader").hide();
                                    $("#sysEmailFrmContainer").contents().find("#lbl_SysEmailStatus").html(data);
                                    $("#sysEmailFrmContainer").contents().find("#lbl_SysEmailStatus").show();
                                },
                                error: function (xhr, ajaxOptions, error) {
                                    alert(error);
                                }
                            });
                        },
                        error: function (xhr, ajaxOptions, error) {
                            //alert(error);
                        }
                    });
                }
            },
            Close: function () {
                $("#sysEmailFrmContainer").contents().find("#img_SysLoader").hide();
                $(this).dialog("close");
            }
        }
    });
}
function emailReportAS(obj) {
    if ($("#menuEmailAs").length > 0)
        $("#menuEmailAs").remove();
    else {

        if ($("#menuExportAs").length>0)
            $("#menuExportAs").remove();

        var str = "";
        str = "<div id='menuEmailAs'  style='min-width:150px; height:60px; border:1px solid #ccc; border-radius:5px; padding:5px; position:absolute;z-index:100; margin-top:-99px; margin-left:367px; background-color:#fff; '><ul id='rc-exportas-menu'><li onclick='emailReport(this)'><a href='#'><span class='menu-ico-excel'></span>Excel (Data)</a></li><li onclick='emailReport(this)'><a href='#'><span class='menu-ico-excelf'></span>Excel (Formatted)</a></li><li onclick='emailReport(this)'><a href='#'><span class='menu-ico-pdf'></span>PDF</a></li></ul></div>";
        $(str).insertAfter(obj);
    }
}
function resetCurrentUI() {
    var _data = "[" + defaultUIJSON + "]";
    var myObject = eval('(' + _data + ')');
    setControlsDefaultOrSavedValue(myObject);
    //Specially Handel DualListBox
    $(".rc-duallistbox").each(function () {
    	var attrName = $(this).attr('name');
        if (typeof attrName !== typeof undefined && attrName !== false) {
        	var ctrl = $(this).attr('id');
            $("#" + ctrl + " option").each(function () {
                $("#" + ctrl + "_proxy").append($(this).clone());
                $(this).remove();
            });
        }
    });
}


function validateEmail(field) {
    var regex = /^[a-zA-Z0-9._-]+@@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,5}$/;
    return (regex.test(field)) ? true : false;
}
function validateMultipleEmails(emailcntl, seperator) {
    var value = emailcntl.val();
    if (value != '') {
        var result = value.split(seperator);
        for (var i = 0; i < result.length; i++) {
            if (result[i] != '') {
                if (!validateEmail(result[i])) {
                    emailcntl.focus();
                    alert('Please check, ' + result[i] + ' email addresses not valid!');
                    return false;
                }
            }
        }
    }
    return true;
}
function validateRequiredField(obj) {

    if ($(obj).attr("data-required") != null && trim($(obj).val()) == "") {
        if ($("#" + $(obj).attr("id") + "_valid").length > 0)
            $("#" + $(obj).attr("id") + "_valid").remove();
        else {
            //remove validation
            var obj1 = $(obj);
            $(obj1).keyup(function () {
                if (trim($(obj1).val()) != "") {
                    $("#" + $(obj1).attr("id") + "_valid").remove();
                }
            });
        }

        $(obj).parent().append($("<span id='" + $(obj).attr("id") + "_valid' style='color:red;'>This field is required</span>"));
        $(obj).focus();
        //exit();
        return false;
    }
    return true;
}


// Scheduling REPORT JOB START
function prepareJobScheduleUrl() {
    //Code Block Formation: Code Block Must Be Initialized At the beginning of initialization of all controls
    $(".rc-codeblock").each(function (i) {
        eval(trim($(this).text()));
    });

    var _sqlProcedure = "";
    var _ReportName = "";

    var arrRst = new Array();
    arrRst = initReportGateWay();
    if (arrRst[0] != "")//Procedure
        _sqlProcedure = arrRst[0];
    else
        _sqlProcedure = $("#frmRCDynamic").attr("data-procedure");

    if (arrRst[1] != "")//Report
        _ReportName = arrRst[1];
    else
        _ReportName = $("#frmRCDynamic").attr("data-rdl");


    if (_sqlProcedure == "") {
        return "No procedure is set yet!";
    }
    if (_ReportName == "") {
        return "No RDLName is set yet!";
    }
    

    // checkbox
    var jsonObj = "";
    $(".rc-checkbox:input[type=checkbox]").each(function (i) {
        if ($(this).attr("data-param") != null)
            jsonObj += "{" + "\"Id" + "\":" + "\""+$(this).attr("Id")+"\"," + "\"Type" + "\":" + "\"CHECKBOX\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + ($(this).is(':checked') ? "Yes" : "No") + "\"},"
    });

    // rc-duallistbox
    var dlbQStr = "";
    $(".rc-duallistbox").each(function (i) {
        if ($(this).attr("name") != "") {
            var dlbVals = "";
            dlbQStr += $(this).attr("name") + "=";
            $(this).find("option").each(function (i) {
                dlbVals += $(this).attr("value") + ",";
            });
            dlbVals = dlbVals.replace(/^,|,$/g, ''); //   ^, matches the comma at the start of the string and ,$ matches the comma at the end 
            dlbQStr = dlbQStr + dlbVals.substring(0, dlbVals.length - 1) + "&";
            if ($(this).attr("data-param") != null)
                jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Type" + "\":" + "\"DUALLISTBOX\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + dlbVals + "\"},"
        }
    });

    // rc-textbox
    $(".rc-textbox").each(function (i) {

        if (!validateRequiredField($(this)))
            exit();

        if ($(this).attr("data-param") != null)
            jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Type" + "\":" + "\"TEXTBOX\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
    });

    //rc-conditionltextbox
    $(".rc-conditionltextbox").each(function (i) {
        if ($(this).attr("data-param") != null) {
            jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Type" + "\":" + "\"CONDITIONALTEXTBOX\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + trim($(this).val()) + "\"},"
            jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "_lst\"," + "\"Type" + "\":" + "\"NA\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-condition-param") + "\"," + "\"Value" + "\":" + "\"" + trim($("#" + $(this).attr('id') + "_lst").val()) + "\"},"
        }
    });

    // rc-sch-datepicker
    $(".rc-sch-datepicker").each(function (i) {
        if ($(this).attr("data-param") != null)
            jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Type" + "\":" + "\"DATEPICKER\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
    });
    // rc-monthpicker
    $(".rc-monthpicker").each(function (i) {
        if ($(this).attr("data-param") != null) {
            if ($(this).attr("date-selection-type") != null) {
                var _year = trim($(this).val().split(",")[1]);
                var _month = getMonthCode(trim($(this).val().split(",")[0]));
                var _date = getLastDateOfMonth(_year, _month);
                var _val = "";
                switch (trim($(this).attr("date-selection-type").toUpperCase())) {
                    case "LAST-MONTH-DATE":
                        _val = (_month + 1) + "/" + _date + "/" + _year;
                        break;
                    case "FIRST-MONTH-DATE":
                        _val = (_month + 1) + "/" + 1 + "/" + _year;
                        break;
                    default:
                        _val = (_month + 1) + "/" + 1 + "/" + _year;
                        break;
                }
                //alert(_val);
                jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Type" + "\":" + "\"MONTHPICKER\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + _val + "\"},"
            }
            else
                jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Type" + "\":" + "\"MONTHPICKER\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
        }
        else {
            if ($(this).attr("data-param-month") != null && $(this).attr("data-param-year") != null) {
                jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Type" + "\":" + "\"MONTHPICKER\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param-month") + "\"," + "\"Value" + "\":" + "\"" + trim($(this).val().split(",")[0]) + "\"},"
                jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Type" + "\":" + "\"MONTHPICKER\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param-year") + "\"," + "\"Value" + "\":" + "\"" + trim($(this).val().split(",")[1]) + "\"},"
            }
        }
    });
    // rc-quarterpicker
    $(".rc-quarterpicker").each(function (i) {
        //alert($(this).attr("data-param-quarter"));
        if ($(this).attr("data-param-quarter") != null && ($(this).attr("data-param-start") != null || $(this).attr("data-param-end") != null)) {

            var qtr = $(this).find(".quarters").eq(0).val();
            var sdt = $(this).find(".years").eq(0).val(); //start year
            var edt = sdt;  //end year
            var dt = new Date();
            if (sdt == "")
                sdt = dt.getFullYear() + "";

            switch (qtr.toUpperCase()) {
                case "Q1":
                    sdt = "3/1/" + sdt;
                    edt = new Date(dt.getFullYear, 3 + 3, 1) + "";//Adding 3 Moth to reach next quarter
                    break;
                case "Q2":
                    sdt = "6/1/" + sdt;
                    edt = new Date(dt.getFullYear, 6 + 3, 1) + "";
                    break;
                case "Q3":
                    sdt = "9/1/" + sdt;
                    edt = new Date(dt.getFullYear, 9 + 3, 1) + "";
                    break;
                case "Q4":
                    sdt = "12/1/" + sdt;
                    edt = new Date(dt.getFullYear + 1, 3, 1) + "";
                    break;
            }
            jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Type" + "\":" + "\"QUARTERPICKER\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param-quarter") + "\"," + "\"Value" + "\":" + "\"" + qtr + "\"},"

            if ($(this).attr("data-param-start") != null && $(this).attr("data-param-start") != "") //start-date
                jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "_yrs\"," + "\"Type" + "\":" + "\"QUARTERPICKER\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param-start") + "\"," + "\"Value" + "\":" + "\"" + sdt + "\"},"

            if ($(this).attr("data-param-end") != null && $(this).attr("data-param-end") != "") //end-date
                jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "_yrs\"," + "\"Type" + "\":" + "\"QUARTERPICKER\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param-end") + "\"," + "\"Value" + "\":" + "\"" + edt + "\"},"
        }
    });
    // rc-combobox
    $(".rc-combobox").each(function (i) {
        if ($(this).attr("data-param") != null)
            jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Type" + "\":" + "\"COMBOBOX\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
    });
    // rc-fiscalyear
    $(".rc-fiscalyear").each(function (i) {
        if ($(this).attr("data-param") != null) {
            if ($(this).attr("data-is-fy-start-date") != null)
                jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Type" + "\":" + "\"FISCALYEAR\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + ("3/1/" + $(this).val()) + "\"},"
            else
                jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Type" + "\":" + "\"FISCALYEAR\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
            //jsonObj += "{" + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
        }
    });
    // rc-radio
    $(".rc-radio:radio:checked").each(function (i) {
        if ($(this).attr("data-param") != null)
            jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Type" + "\":" + "\"RADIO\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).attr("data-val") + "\"},"
    });
    //rc-num-range
    $(".rc-num-range").each(function (i) {
        if ($(this).attr("data-param") != null) {
            var params = new Array();
            params = $(this).attr("data-param").split(",");
            if (params.length > 1) {
                jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Type" + "\":" + "\"NUMRANGE\"," + "\"Name" + "\":" + "\"" + trim(params[0]) + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
                jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "_to\"," + "\"Type" + "\":" + "\"NUMRANGE\"," + "\"Name" + "\":" + "\"" + trim(params[1]) + "\"," + "\"Value" + "\":" + "\"" + $("#" + $(this).attr("Id") + "_to").val() + "\"},"
            }
        }
    });
    // rc-lookup
    $(".rc-lookup").each(function (i) {
        if (!validateRequiredField($(this)))
            exit();
        if ($(this).attr("data-param") != null)
            jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Type" + "\":" + "\"LOOKUP\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).val().replace(/^,|,$/g, '') + "\"},"  //replace(/^,|,$/g, '') omitting the start and ending ,
    });
    // rc-dataparam
    $(".rc-dataparam").each(function (i) {
        if ($(this).attr("data-param") != null)
            jsonObj += "{" + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Type" + "\":" + "\"DATAPARAM\"," + "\"Name" + "\":" + "\"" + $(this).attr("data-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).attr("data-val") + "\"},"
    });
    //User_Id
    jsonObj += "{" + "\"Id" + "\":" + "\"NA\"," + "\"Type" + "\":" + "\"NA\"," + "\"Name" + "\":" + "\" user_id \"," + "\"Value" + "\":" + "\"" + $("#loggedUser").attr("data-networkalias") + "\"},";
    //$("#frmRCDynamic").attr("data-procedure")
    jsonObj = "{" + "\"SPName" + "\":" + "\"" + _sqlProcedure + "\"," + "\"Params" + "\": [" + jsonObj + "]}";

    // jsonObj = encodeURIComponent(jsonObj);
    

    //GET Report Params: Start
    //$("#frmRCDynamic").attr("data-val");
    var rdlReportObj = "";
    $(".rc-reportparam").each(function (i) {
        if ($(this).hasClass("rc-checklist")) {
            var str = "";
            $(".rc-checklist-items input[type='checkbox']").each(function () { if ($(this).is(":checked")) { str += $(this).attr('data-val') + ","; } });
            rdlReportObj += "{" + "\"Type" + "\":" + "\"NA\"," + "\"Name" + "\":" + "\"" + $(this).attr("report-param") + "\"," + "\"Value" + "\":" + "\"" + str + "\"},"
        }
        else if ($(this).attr('type') != null && $(this).attr('type') == "checkbox") {
            rdlReportObj += "{" + "\"Type" + "\":" + "\"NA\"," + "\"Name" + "\":" + "\"" + $(this).attr("report-param") + "\"," + "\"Value" + "\":" + "\"" + ($(this).is(':checked') ? "Yes" : "No") + "\"},"
        }
        else {
            if ($(this).attr("report-val") != null)
                rdlReportObj += "{" + "\"Type" + "\":" + "\"NA\"," + "\"Name" + "\":" + "\"" + $(this).attr("report-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).attr("report-val") + "\"},"
            else {
                rdlReportObj += "{" + "\"Type" + "\":" + "\"NA\"," + "\"Name" + "\":" + "\"" + $(this).attr("report-param") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
            }
        }
    });
    //hRptTitle
    rdlReportObj = rdlReportObj.substring(0, rdlReportObj.length - 1);
    rdlReportObj = "{" + "\"ReportNumber" + "\":" + "\"" + $("#frmRCDynamic").attr("data-val") + "\"," + "\"ReportTitle" + "\":" + "\"" + $("#hRptTitle").text() + "\"," + "\"RDLName" + "\":" + "\"" + _ReportName + "\"," + "\"Params" + "\": [" + rdlReportObj + "]}";
    //GET Report Params: End
    
    var arrRst = new Array();
    arrRst[0] = jsonObj;
    arrRst[1] = rdlReportObj;

    return arrRst;
}
function editCurrentJob(obj) {
    var arrRst = new Array();
    arrRst=prepareJobScheduleUrl();
    var _exeId = $(obj).attr("data-executionid");
    
    var qStr = "schedule=" + $(obj).attr("data-schedule") + "&jobexecution=" + $(obj).attr("data-jobexecution") + "&rptNumber=" + $(obj).attr("data-reportnumber");
    var _url = "Home/EditJobExecution";
    $.ajax({
        type: "GET",
        url: _url,
        data: qStr,
        cache:false,
        success: function (data) {
            $("<div id='rptScheduleSetupMain_dialog'></div>").dialog({
                modal: true,
                title: $("#hRptTitle").text() + " (Job Schedualer Setup)",
                width: 800,
                height: 600,
                close: function () {
                    $(".job-edit").hide();
                    loadJobExecutions();
                    $(this).dialog('destroy').remove();
                },
                buttons: {
                    Save: function () {
                        saveJobExecution(obj, _exeId, arrRst);
                    }
                }
            }).html(data);
        },
        error: function (xhr, ajaxOptions, error) {
            alert(error);
        }
    });
}
function showCurrentReportJobLog(obj) {
    //  $("#joblog_dialog").html('<iframe id="jobLogFrmContainer" frameborder="0" scrolling="no" width="100%" height="330" src="./Home/GetJobExecutionById?executionId=' + exeid + '"></iframe>');
    $("#joblog_dialog").dialog({
        modal: true,
        resizable: true,
        title: "Logs of Report " + $("#frmRCDynamic").attr("data-val"),
        width: 850,
        height: 500,
        close: function () {
        },
        buttons: {
            Cancel: function () {
                $("#joblog_dialog").dialog("close");
            }
        }
    });
}
function showJobDetail(obj, exeid) {
    $("#jobdetail_dialog").html('<iframe id="jobDetailFrmContainer" frameborder="0" scrolling="no" width="100%" height="330" src="./Home/GetJobExecutionById?executionId=' + exeid + '"></iframe>');
    $("#jobdetail_dialog").dialog({
        modal: true,
        resizable: true,
        title: "Job Setting of Report " + $("#frmRCDynamic").attr("data-val"),
        width: 650,
        height: 300,
        close: function () {
        },
        buttons: {
            Cancel: function () {
                $("#jobdetail_dialog").dialog( "close" );
            }
        }
    });
}
function getJobExecutions(obj) {
    
    if ($(obj).attr("data-jobmode") == "false") {
        $(obj).attr("data-jobmode", "true");
        $(obj).attr("title", "Back To Report Mode");

        $(".frmContent,.rc-datarow").css("background-color", "#C4E0B6");
        // $(".data-row").css("background-color", "#B4FBB4");

        $(obj).attr("src", "Images/Theme1/arrow-left.png");
        $("#rc-rpt-ctrls .rc-submit").hide();
        //$("#rc-rpt-ctrls .rc-submit").not("#btnLoadDefault").hide();
        $("#rc-rpt-ctrls").append("<input id='btnRCVerify' class='rc-job-sch-btn' onclick='verifyProcedure(this)' value='Verify Report' type='button'/>");
        $(".rc-datepicker").each(function () {
            var _parent = $(this).parent();
            $(this).parent().find('.rc-datepicker,.rc-datepicker-btn').hide();
            $(_parent).append("<select id='" + $(this).attr("Id") + "_jobsch' class='rc-sch-datepicker' data-param='" + $(this).attr('data-param') + "'><option value='CurrentDate'>Current Date</option><option value='DayBeforeToDay'>Day Before Today</option><option value='FirstMonthDate'>First Month Date</option><option value='LastMonthDate'>Last Month Date</option><option value='DayBeforeLastMonthDate'>Day Before Last Month Date</option><option value='FiscalYearStartDate'>Fiscal Year Start Date</option><option value='FiscalYearEndDate'>Fiscal Year End Date</option></select>");
        });
        $(".rc-monthpicker").each(function () {
            var _parent = $(this).parent();
            $(this).parent().find('.rc-monthpicker,.rc-monthpicker-btn').hide();
            $(_parent).append("<select id='" + $(this).attr("Id") + "_jobsch' class='rc-sch-monthpicker' data-param='" + $(this).attr('data-param') + "'><option value='CurrentDate'>Current Date</option><option value='DayBeforeToDay'>Day Before Today</option><option value='FirstMonthDate'>First Month Date</option><option value='LastMonthDate'>Last Month Date</option><option value='DayBeforeLastMonthDate'>Day Before Last Month Date</option><option value='FiscalYearStartDate'>Fiscal Year Start Date</option><option value='FiscalYearEndDate'>Fiscal Year End Date</option></select>");
        });
        $("#frmContentInner").append("<div id='rc-sch-joblist' style='display:none;'><img id='introLoader' style='display:none;' src='/Images/Theme1/loading35.gif'><div id='rc-sch-joblist-loader'></div><div id='rc-sch-joblist-detail'></div></div>");

        rePositionSchJobList();
        
        loadJobExecutions();
    }
    else {
        $(obj).attr("data-jobmode", "false");
        $(obj).attr("src", "Images/Theme1/setting.png");
        $(obj).attr("title", "Scheduling This Report");

        $(".frmContent,.rc-datarow").css("background-color", "#EDEFED");

        $("#rc-rpt-ctrls .rc-job-sch-btn").remove();
        $("#rc-rpt-ctrls .rc-submit").show();

        $(".rc-sch-datepicker").remove();
        $(".rc-datepicker,.rc-datepicker-btn").show();

        $("#rc-sch-joblist").remove();
    }
}
function loadJobExecutions() {
    /*
    //var _sqlProcedure = "";
    var _rdlReport = "";
    if ($("#frmRCDynamic").attr("data-rdl") == "") {
        var arrRst = new Array();
        arrRst = initReportGateWay();
        //_sqlProcedure = arrRst[0];
        _rdlReport = arrRst[1];
    }
    else
        _rdlReport = $("#frmRCDynamic").attr("data-rdl");
    */

    var qStr = "rptNumber=" + trim($("#frmRCDynamic").attr("data-val"));
    $.ajax({
        type: 'GET',
        url: 'Home/GetJobExecutions',
        data: qStr,
        beforeSend: function () {
            $("#introLoader").show();
            //$("#rc-sch-joblist-loader").html('');
        },
        complete: function () {
            $("#introLoader").hide();
        },
        success: function (result) {
            $("#rc-sch-joblist-detail").html(result);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.status + ":" + thrownError);

        }
    });
}
function loadCurrentJob(obj) {
    
    resetCurrentUI();

    var _data = $(obj).attr("data-spparams");
    var myObject = eval('(' + _data + ')');
    setControlsDefaultOrSavedValue(myObject);
    $(obj).parent().parent().find(".job-edit").eq(0).show();
}
function createJob(obj) {
    

    var arrRst = new Array();
    arrRst = prepareJobScheduleUrl();
    var _exeId = $(obj).attr("data-executionid");

    //var qStr = "schedule=" + $(obj).attr("data-schedule") + "&jobexecution=" + $(obj).attr("data-jobexecution");
    var qStr = "spParams=" + $(obj).attr("data-spparams") + "&spName=" + $(obj).attr("data-procedure") + "&rptParams=" + $(obj).attr("data-rptparams") + "&rptName=" + $(obj).attr("data-report") + "&rptNumber=" + $(obj).attr("data-reportnumber");
    var _url = "Home/LoadDefaultJobExecution";
    $.ajax({
        type: "GET",
        url: _url,
        data: qStr,
        cache: false,
        success: function (data) {
            $("<div id='rptScheduleSetupMain_dialog'></div>").dialog({
                modal: true,
                title: $("#hRptTitle").text() + " (Job Schedualer Setup)",
                width: 800,
                height: 600,
                close: function () {
                    $(".job-edit").hide();
                    loadJobExecutions();
                    $(this).dialog('destroy').remove();
                },
                buttons: {
                    Save: function () {
                        saveJobExecution(obj, _exeId, arrRst);
                    }
                }
            }).html(data);
        },
        error: function (xhr, ajaxOptions, error) {
            alert(error);
        }
    });


}
function saveJobExecution(obj, _exeId, arrRst) {
    var count = 0;
    $("#exportItems input[type=checkbox]").each(function () {
        if ($(this).is(":checked"))
            count++;
    });
    if ($("#JobName").val() == "") {
        alert("Job name cannot be empty!");
        $("#JobName").focus();
        return false;
    }
    else if ($("#ScheduleName").val() == "") {
        alert("Schedule name cannot be empty!");
        $("#ScheduleName").focus();
        return false;
    }
    else if (count == 0) {
        alert("Please check at least one attaching file type.");
        return false;
    }
    else if ($("#txt_SysEmailUserTo").val() == "") {
        alert("Email address cannot be empty!");
        return false;
    }
    else if (validateMultipleEmails($("#txt_SysEmailUserTo").eq(0), ",")) {
        //alert(_exeId + $("#hdn_sqlprocedure").val() + $("#hdn_rdlreport").val() + preparejsonNotificationSchedule());

        var jsonJobExe = {};
        jsonJobExe["ExecutionId"] = _exeId;
        jsonJobExe["JobName"] = $('#JobName').val();
        jsonJobExe["ScheduleId"] = $('#schedulediv input[name="selectschedule"]:checked').val() == 1 ?
                                     0 : $('#CurrentSelectedSchedule').val();
        jsonJobExe["EmailTo"] = $("#txt_SysEmailUserTo").eq(0).val();
        jsonJobExe["SPName"] = $(obj).attr("data-procedure");
        jsonJobExe["SPParams"] = arrRst[0];
        jsonJobExe["RPTNumber"] = $(obj).attr("data-reportnumber");
        jsonJobExe["RPTName"] = $(obj).attr("data-report");
        jsonJobExe["RPTParams"] = arrRst[1];

        jsonJobExe["IsExcelData"] = $("#excelData").is(":checked");
        jsonJobExe["IsExcelFormated"] = $("#excelFormatted").is(":checked");
        jsonJobExe["IsPDF"] = $("#pdf").is(":checked");
        jsonJobExe["IsWord"] = $("#word").is(":checked");
        jsonJobExe["IsMHTML"] = $("#mhtml").is(":checked");
        jsonJobExe["IsBodyHTML"] = ($("#bodyhtml").length>0)?$("#bodyhtml").is(":checked"):'false';
        


        var qStr = "jobexecutionobj=" + JSON.stringify(jsonJobExe) + "&scheduleobj=" + preparejsonNotificationSchedule(); // + "&spname=" + $(obj).attr("data-procedure") + "&rptname=" + $(obj).attr("data-report");

        $.ajax({
            type: "POST",
            url: "Home/UpdateJobExecution",
            data: qStr,
            success: function (data) {
                if (data.toUpperCase() == "TRUE") {
                    showDialog("Saving...", "Successfully Saved", "ui-icon-info", 400, 130);
                }
            },
            error: function (xhr, ajaxOptions, error) {
                alert(error);
            }
        });
    }
}
function verifyProcedure(obj) {
    
    var arrRst = new Array();
    arrRst=prepareJobScheduleUrl();
    //alert(arrRst[1]);
   
    var qStr = "spParams=" + encodeURIComponent(arrRst[0]) + "&rptParams=" + arrRst[1];
    $.ajax({
        type: 'POST',
        url:'Home/VerifySQLExecution',
        data: qStr,
        beforeSend: function () {
            $("#introLoader").show();
            $("#rc-sch-joblist-loader").html('');
        },
        complete: function () {
            $("#introLoader").hide();
        },
        success: function (result) {
            //alert(result);
            
            $("#rc-sch-joblist-loader").html(result);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.status + ":" + thrownError);
            
        }
    });
}
// Scheduling REPORT JOB END

$(document).ready(function () {
	
	//defaultUIJSON = getCurrentSelectionAsJSON();
    /*if (_sysautopreview != 1) { // IF sysautopreview <> 1 THEN LOAD SAVED UI, OTHERWISE NOT
        var qStr = "formId=" + $("#frmRCDynamic").attr("data-val");
        $.ajax({
            type: "POST",
            url: "Home/LoadSavedUI",
            data: qStr,
            beforeSend: function () {
                defaultUIJSON = getCurrentSelectionAsJSON();
            },
            success: function (data) {
                if (trim(data) != "") {
                    //alert(data);
                    defaultUIJSONAfterLoad = data;
                    var _data = "[" + data + "]";
                    var myObject = eval('(' + _data + ')');
                    setControlsDefaultOrSavedValue(myObject);
                }
            },
            error: function (xhr, ajaxOptions, error) {
                alert(error);
            }
        });
    }*/
    $("#btnRCSubmit").click(function (e) {
        e.preventDefault();

        //var qStr = prepareReportUrl();
        var qStr = prepareQStr();
        if (qStr == "No procedure is set yet!") {
            alert("No procedure is set yet!");
            return;
        }
        if (qStr == "No RDLName is set yet!") {
            alert("No RDLName is set yet");
            return;
        }
        if (qStr == "Validation Error") {
            return;
        }
        var _report = $("#frmRCDynamic").attr("data-val");
        var _dt = new Date();

        var mQStr = "";
        if (location.search.indexOf("sysautopreview=1") >= 0 && location.search.indexOf("LinkLevel=") >= 0) {
            var arr = location.search.split("&");
            for (i = 0; i < arr.length; i++) {
                if (arr[i].indexOf("sysautopreview=1") < 0 && arr[i].indexOf("sysreport=") < 0 && arr[i].indexOf("sKey=") < 0) {
                    mQStr += "&" + arr[i];
                }
            }
        }
        //alert(mQStr);
        $("#hdnReportExecutionQStr").val(qStr);
        //alert(qStr);
        //exit();
        _report = $("#frmRCDynamic").attr("data-rdl");
        $("#reportExecutionFRM").attr("action",$("#reportExecutionFRM").attr("data-base")+"?__report=reports/"+_report+".rptdesign&"+qStr+"&sKey="+$("#reportExecutionFRM").attr("data-skey")+"&uKey="+$("#reportExecutionFRM").attr("data-ukey"))
        //alert( $("#reportExecutionFRM").attr("action"));
        submitReportForm();


    });

    //Populating Color Box Controls Title
    $("#cboxPrevious").live("hover", function () {
        $(this).attr("title", "Previous");
    });
    $("#cboxNext").live("hover", function () {
        $(this).attr("title", "Next");
    });
    $("#cboxClose").live("hover", function () {
        $(this).attr("title", "Close");
    });
    if ($("#loggedUser").attr("data-utype") != "Admin") {
        $("#btnJobSetting").remove();
    }
});

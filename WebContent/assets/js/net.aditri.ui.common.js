var defaultUIJSON = "";
var defaultUIJSONAfterLoad = "";
$(document).ready(function () {
    $(document).click(function (event) {
        if (!$(event.target).is("#btnRCExportAs")) {
            if ($("#menuExportAs").is(':visible'))
                $("#menuExportAs").hide();
        }
        if (!$(event.target).is("#btnRCEmailAs")) {
            if ($("#menuEmailAs").is(':visible'))
                $("#menuEmailAs").hide();
        }
        if (!$(event.target).is("#rootnode, #lnkDataAreaNext,#bredCrumb_root")) {
            if ($(".submenuDataArea").is(':visible'))
                $(".submenuDataArea").parent().hide();

            if ($(".submenuRptModule").is(':visible'))
                $(".submenuRptModule").parent().hide();

            if ($("#bredCrumb_holder").is(':visible'))
                $("#bredCrumb_holder").hide();
        }
        if (!$(event.target).is("#adminSetting")) {
            if ($(".admin-dropdown").is(':visible'))
                $(".admin-dropdown").hide();
        }
        if (!$(event.target).is("#helpManual")) {
            if ($(".help-dropdown").is(':visible'))
                $(".help-dropdown").hide();
        }
        $(".sub-admin-dropdown").hide();

    });


    $(".widget-expand").live("click", function () {
        $('.' + $(this).attr('data-val') + ' .expandable-hitarea').click();
    });
    $(".widget-collapse").live("click", function () {
        $('.' + $(this).attr('data-val') + ' .collapsable-hitarea').click();
    });

});
/************************************COMMON FUNCTION******************************************************************/
Date.prototype.addDays = function (days) {
    var dat = new Date(this.valueOf());
    dat.setDate(dat.getDate() + days);
    return dat;
}

function getLastDateOfMonth(year, month) { //number of date in a month
    return 32 - new Date(year, month, 32).getDate();
}
function getMonthCode(mn) {
    var code = 0;
    switch (mn) {
        case "Jan":
            code = 0;
            break;
        case "Feb":
            code = 1;
            break;
        case "Mar":
            code = 2;
            break;
        case "Apr":
            code = 3;
            break;
        case "May":
            code = 4;
            break;
        case "Jun":
            code = 5;
            break;
        case "Jul":
            code = 6;
            break;
        case "Aug":
            code = 7;
            break;
        case "Sep":
            code = 8;
            break;
        case "Oct":
            code = 9;
            break;
        case "Nov":
            code = 10;
            break;
        case "Dec":
            code = 11;
            break;
        default:
            code = 0;
            break;
    }
    return code;
}
function getMonthName(mn) {
    var month = new Array();


    month[0] = "Jan";
    month[1] = "Feb";
    month[2] = "Mar";
    month[3] = "Apr";
    month[4] = "May";
    month[5] = "Jun";
    month[6] = "Jul";
    month[7] = "Aug";
    month[8] = "Sep";
    month[9] = "Oct";
    month[10] = "Nov";
    month[11] = "Dec";
    return month[mn];
}

// Numeric only control handler
jQuery.fn.ForceNumericOnly = function () {
    return this.each(function () {
        $(this).keydown(function (e) {
            var key = e.charCode || e.keyCode || 0;
            return (
                key == 8 ||
                key == 9 ||
                key == 13 ||
                key == 46 ||
                key == 110 ||
                key == 190 ||
                (key >= 35 && key <= 40) ||
                (key >= 48 && key <= 57) ||
                (key >= 96 && key <= 105));
        });
    });
};
function scrollFormTop() {
    $("#frmContentInner").scrollTop(0);
}
function scrollFormBottom() {
    $("#frmContentInner").scrollTop($("#frmContentInner").height());
}

function trim(str) {
    return str.replace(/^\s+|\s+$/g, "");
}
function showDialog(_title, _msg, _ico, _w, _h) {
    $("<div id='rc-dialog-common' title='" + _title + "'><p><span class='ui-icon " + _ico + "' style='float:left; margin:0 7px 20px 0;'></span>" + _msg + "</p></div>").dialog({
        resizable: false,
        width: _w,
        height: _h,
        modal: true,
        buttons: {
            "Ok": function () {
                $("#rc-dialog-common").remove();
            }
        }
    });
}
function getClassNameWithNumberSuffix(el) {
    var className = null;
    var regexp = /\w+\d+/;
    $($(el).attr('class').split(' ')).each(function (i) {
        if (i == 1) {
            className = this;
            return className;
        }

    });

    return className;
}
/********************************************* COMMON FUNCTION********************************************************/

function initializeDragDrop(selector, module, isEditable) {
    var connectWith = "";
    if (module != "home")
        connectWith = ".drop-panel ." + selector;
    else
        connectWith = ".drop-panel .rptBrowser ." + selector;


    if (isEditable) {
        $('.rptTree .' + selector + ' li').draggable({
            //   disabled: (isEditable?false:true),
            helper: "clone",
            connectToSortable: connectWith
        });

        $('.rptTree .' + selector + ' li').css("cursor", "move");
    }
    else {
        $('.rptTree .' + selector + ' li').draggable('destroy');

        $('.rptTree .' + selector + ' li').css("cursor", "pointer");
    }

    // connectToSortable: connectWith+" , #favList"
    var isExist = false;
    $(connectWith).sortable({
        'items': ".rptItem",//"." + selector + " .rptItem",
        'receive': function (event, ui) {
            var clazz = getClassNameWithNumberSuffix(ui.item);


            if ($(connectWith + ' .' + clazz).length > 1) {
                // $(connectWith + ' .' + clazz + ':not(:first)').remove();
                isExist = true;
                //$('.rptTree .' + selector + ' .' + clazz).draggable("option", "revert", true);
            }
            else {
                isExist = false;
            }
        },
        sort: function () {
            if (!isEditable) {
                return false;
            }
        },
        stop: function (event, ui) {
            if (isExist) {
                $(ui.item).remove();
                isExist = false;
            }
            else {

                $(ui.item).mouseover(function () {
                    $(this).find(".rptDelete").show();
                });
                $(ui.item).mouseout(function () {
                    $(this).find(".rptDelete").hide();
                });
            }

            if ($(this).html() != "") {
                $(this).removeClass("emptylist");
            }
        }
    });
}
function signAs() {
    $.ajax({
        type: 'GET',
        url: 'Home/SignInAsDifferentUser',
        cache: false,
        beforeSend: function () {
            $("#preloader").show();
        },
        complete: function () {
        },
        success: function (result) {
            if (result == "True") {
                window.location.reload();
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            $("#preloader").html("<span style='display:inline-block;padding: 10px;font-weight: bold;'><font color='red'>Sorry!</font>You Are Unauthorized!!!</span>");
        }
    });
}

function clearBredCrumbModule() {
    $("#lnkModuleReports").parent().remove();
    var _menu = $("#rootMenu li:not(:first-child)")[0].outerHTML;
    //createCookie('RC_MRM_BREDECRUMB', _menu, 1);
    createCookie('RC_MRM_BREDECRUMB', "", -1);
    //LOAD DASHBOARD
    loadDashBoard("Home/LoadDashboard");

}
function clearExternalLink() {
    location.href = "./";
}
function clearCookieOnly() {
    createCookie('RC_MRM_BREDECRUMB', "", -1);
}
function clearCookie() {
    $("#lnkModuleReports").parent().remove();
    createCookie('RC_MRM_BREDECRUMB', "", -1);
    location.href = "./";
}
function goHome() {
    clearBredCrumbModule();
}

function getCurrentModule() {
    var moduleId = "home";
    if ($("#lnkModuleReports").attr("data-val") != null && $("#lnkModuleReports").attr("data-val") != "")
        moduleId = $("#lnkModuleReports").attr("data-val");
    return moduleId;
}
function loadDashBoard(_action, _dialogInit) {
    //if (typeof _isAsync == 'undefined')
    //    _isAsync = true;

    var moduleId = getCurrentModule();
    if (moduleId == "home" && _action != "Home/LoadDefaultDashboard")
        _action = "Home/LoadHomeDashboard";
    //alert(_action);
    qStr = "module=" + moduleId;
    $.ajax({
        type: 'GET',
        url: _action,//'Home/LoadDashboard',
        data: qStr,
        // async:(_isAsync)?true:false,
        cache: false,
        beforeSend: function () {
            $("#introLoader").show();
        },
        complete: function () {
            $("#introLoader").hide();
            if (typeof _dialogInit != 'undefined') {
                var obj = JSON.parse(_dialogInit);
                showDialog(obj.dialogs[0].Title, obj.dialogs[0].Body, obj.dialogs[0].Icon, obj.dialogs[0].Width, obj.dialogs[0].Height);
            }
        },
        success: function (result) {
            $("#ctrlSave").show();
            if (trim(result) == "error") {
                loadDashBoard("Home/LoadDefaultDashboard");
            }
            else {

                //ON Module Change Bring Current Module On Top of Left Navigation Pan (Reports Tree)
                var tmpNode = $("#leftPane #" + $("#lnkModuleReports").attr("data-class")).parent().parent().parent();
                var node = $(tmpNode).clone();
                $(node).insertAfter("#leftPane #favouritePan");
                $(node).find(".collapsable,.expandable").removeClass("collapsable expandable");
                $(node).find(".hitarea").remove();
                $(node).find("#" + $("#lnkModuleReports").attr("data-class")).treeview();
                $(tmpNode).remove();

                //Collaspe All Open Accrodion
                $("#leftPane .leftPane-item-header-ctrlUP").each(function () {
                    $(this).removeClass("leftPane-item-header-ctrlUP").addClass("leftPane-item-header-ctrlDW");
                    var temCont = $(this).parent().parent().find(".leftPane-item-container");
                    //alert($(temCont).html());
                    $(temCont).hide();
                });


                $("#WidgetHolder").html(result);
                var selector = $("#lnkModuleReports").attr("data-class");
                if (selector != null)
                    loadReports($("#" + selector).parent().parent().find(".ctrlUPDown"));
                else {
                    loadReports($("#favList").parent().parent().find(".ctrlUPDown"));
                }
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.status + ":" + thrownError);
            //alert(thrownError);
        }
    });
}
function loadRptModule(obj, _submenuHolder, _nextAction, _isReadPrevious) {
    if (_isReadPrevious) {
        _nextAction = _nextAction + "/" + $(obj).parent().text();
    }
    if ($("." + _submenuHolder).html() == null) {
        $.ajax({
            type: "GET",
            url: _nextAction, //"Home/ReportModule",
            success: function (data) {
                $("#menuHolder").append(data);
                $(".submenuDataArea").parent().hide();
            },
            error: function (xhr, ajaxOptions, error) {
                alert(error);
            }
        });
    }
    else {
        $("." + _submenuHolder).parent().show();
        $(".submenuDataArea").parent().hide();
    }
}
function loadReports(obj) {
    var prnt = $(obj).parent().parent();
    if ($(obj).hasClass("leftPane-item-header-ctrlUP")) {

        $(obj).removeClass("leftPane-item-header-ctrlUP");
        $(obj).addClass("leftPane-item-header-ctrlDW");
        $(prnt).children().last().slideUp(200);

        niceScrollLeftPan.resize();
    }
    else {
        $(obj).removeClass("leftPane-item-header-ctrlDW");
        $(obj).addClass("leftPane-item-header-ctrlUP");
        $(prnt).children().last().slideDown(200);

        //Load Reports Only One
        if ($(prnt).find(".rptTree").html() == "") {
            if ($(obj).parent().parent().parent().attr("id") != "favouritePan") {

                var qStr = "module=" + $(obj).parent().find('.leftPane-item-title').eq(0).attr("data-name");
                $.ajax({
                    type: 'GET',
                    url: 'Home/LoadReports',
                    cache: false,
                    data: qStr,
                    beforeSend: function () {
                        //alert('before send');
                    },
                    complete: function () {
                        // alert('Complete');
                    },
                    success: function (result) {

                        $(prnt).find(".rptTree").html(result);
                        $(prnt).find(".rptTree").treeview();
                        var moduleId = "";
                        moduleId = getCurrentModule();
                        //if favourite list is selected and need to initialize for drag and drop 
                        if ($(".leftPane-item-header-ctrlSave").css("background-image").indexOf("save.png") >= 0) {
                            $(prnt).find(".rptTree .rptGroup li").draggable({
                                //   disabled: false,
                                helper: "clone",
                                connectToSortable: "#favList"
                            });
                            $(".rptTree .rptGroup li").css("cursor", "move");
                        }

                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(xhr.status);
                        //alert(thrownError);
                    }
                });
            }
            niceScrollLeftPan.resize();
        }

    }
}
function buildWidgetReloadDashboard(obj) {
    var objStr = obj.id;
    var moduleId = getCurrentModule();
    if (moduleId != "home") {
        var qStr = "moduleId=" + $("#lnkModuleReports").attr("data-class") + "&moduleName=" + moduleId + "&reportGroup=" + objStr;
        var str = "";
        $.ajax({
            type: 'GET',
            url: 'Home/ReloadWidgetItem',
            data: qStr,
            cache: false,
            beforeSend: function () {
            },
            complete: function () {
            },
            success: function (result) {
                if (result == "True") {
                    var dialogInit = '{ "dialogs" : [' + '{ "Title":"Restore Current Widget" , "Body":"Dashboard widget has successfully been restored!","Icon":"ui-icon-info","Width":"400","Height":"130" }' + ']}';
                    loadDashBoard("Home/LoadDashboard", dialogInit);
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                //alert(xhr.status);
            }
        });
    }
    else {
        var qStr = "module=" + $(obj).attr("title") + "&moduleid=" + obj.id;
        var str = "";
        $.ajax({
            type: 'GET',
            url: 'Home/ReloadHomeWidgetItem',
            data: qStr,
            cache: false,
            beforeSend: function () {
            },
            complete: function () {
            },
            success: function (result) {
                if (result == "True") {
                    var dialogInit = '{ "dialogs" : [' + '{ "Title":"Restore Current Home Widget" , "Body":"Dashboard widget has successfully been restored!","Icon":"ui-icon-info","Width":"400","Height":"130" }' + ']}';
                    loadDashBoard("Home/LoadHomeDashboard", dialogInit);
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status);
            }
        });
    }

}


function selectMenuItem(obj, myParent, _nextAction, _submenuHolder, _isReadPrevious) {

    var _html = "";
    if ($("#" + myParent).html() == null) {
        if (myParent != "lnkModuleReports") {
            _html = "<li><div class='box1' data-val='" + $(obj).attr("data-val") + "' data-color='" + $(obj).attr("data-color") + "' data-forecolor='" + $(obj).attr("data-forecolor") + "' id='" + myParent + "'><a>" + $(obj).attr("data-val") + "</a><span id='" + myParent + "Next' onclick='loadRptModule(this,\"" + _submenuHolder + "\",\"" + _nextAction + "\"," + _isReadPrevious + ")' class='box header-nav-selector'></span></div></li>";
        }
        else
            _html = "<li><div class='box1' data-class='" + $(obj).attr("data-class") + "' data-val='" + $(obj).attr("data-val") + "' id='" + myParent + "'><a>" + $(obj).attr("data-val") + "</a></div></li>";
        $("#rootMenu").append(_html);
    }
    else {
        if (myParent != "lnkModuleReports") {
            _html = "<li><div class='box1' data-val='" + $(obj).attr("data-val") + "' data-color='" + $(obj).attr("data-color") + "' data-forecolor='" + $(obj).attr("data-forecolor") + "' id='" + myParent + "'><a>" + $(obj).attr("data-val") + "</a><span id='" + myParent + "Next' onclick='loadRptModule(this,\"" + _submenuHolder + "\",\"" + _nextAction + "\"," + _isReadPrevious + ")' class='box header-nav-selector'></span></div></li>";
        }
        else
            _html = "<li><div class='box1' data-class='" + $(obj).attr("data-class") + "' data-val='" + $(obj).attr("data-val") + "' id='" + myParent + "'><a>" + $(obj).attr("data-val") + "</a></div></li>";
        $("#" + myParent).parent().replaceWith(_html);
    }
    $("#rootMenu .box1 a").css("background-color", $("#lnkDataArea").attr("data-color"));
    $("#rootMenu .box1 a").css("color", $("#lnkDataArea").attr("data-forecolor"));

    // _html = "";
    if ($("#rootMenu li:not(:first-child)").length > 1)
        _html = $("#rootMenu li:not(:first-child)")[0].outerHTML + $("#rootMenu li:not(:first-child)")[1].outerHTML;
    else
        _html = $("#rootMenu li:not(:first-child)")[0].outerHTML;

    createCookie('RC_MRM_BREDECRUMB', _html, 30);
    $(obj).parent().parent().parent().hide();


    if ($("#bredCrumb_root").is(":visible") && myParent != "lnkModuleReports") {

        $("#bredCrumb_root").css("background-color", $("#lnkDataArea").attr("data-color"));
        $("#bredCrumb_root").css("color", $("#lnkDataArea").attr("data-forecolor"));
        //LOAD REPORT
        ajaxReportUILoad($(".bredCrumb_Item").attr("data-val"), $("#lnkDataArea").text());
    }
    //LOAD DASHBOARD
    if (myParent == "lnkModuleReports") {

        $("#bredCrumb").css("display", "none");
        // $("#bredCrumb_holder").css("display", "none");

        loadDashBoard("Home/LoadDashboard");
    }
}
function getCurrentSelectionAsJSON() {
    var jsonObj = "";

    // rc-textbox
    $(".rc-textbox").each(function (i) {
        jsonObj += "{" + "\"Type" + "\":" + "\"TEXTBOX\"," + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
    });
    // rc-conditionltextbox
    $(".rc-conditionltextbox").each(function (i) {
        jsonObj += "{" + "\"Type" + "\":" + "\"CONDITIONALTEXTBOX\"," + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Value" + "\":" + "\"" + trim($(this).val()) + "\"},"
        // jsonObj += "{" + "\"Type" + "\":" + "\"CONDITIONALTEXTBOX\"," + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Value" + "\":" + "\"" + trim($(this).val()) + "\"},"
    });

    // radio
    $(".rc-radio").each(function (i) {

        jsonObj += "{" + "\"Type" + "\":" + "\"RADIO\"," + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Value" + "\":" + "\"" + ($(this).is(':checked') ? "Yes" : "No") + "\"},"
    });

    // checkbox
    // $("input[type=checkbox]").each(function (i) {
    $(".rc-checkbox:input[type=checkbox]").each(function (i) {
        jsonObj += "{" + "\"Type" + "\":" + "\"CHECKBOX\"," + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Value" + "\":" + "\"" + ($(this).is(":indeterminate") ? "" : ($(this).is(':checked') ? "Yes" : "No")) + "\"},"
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
            dlbQStr = dlbQStr + dlbVals.substring(0, dlbVals.length - 1) + "&";
            jsonObj += "{" + "\"Type" + "\":" + "\"DUALLISTBOX\"," + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Value" + "\":" + "\"" + dlbVals + "\"},"
        }
    });

    // rc-datepicker
    $(".rc-datepicker").each(function (i) {
        jsonObj += "{" + "\"Type" + "\":" + "\"DATEPICKER\"," + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
    });
    //JOB::       rc-sch-datepicker 
    $(".rc-sch-datepicker").each(function (i) {
        jsonObj += "{" + "\"Type" + "\":" + "\"DATEPICKER\"," + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
    });


    // rc-monthpicker
    $(".rc-monthpicker").each(function (i) {
        jsonObj += "{" + "\"Type" + "\":" + "\"MONTHPICKER\"," + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
    });

    // rc-combobox
    $(".rc-combobox").each(function (i) {
        jsonObj += "{" + "\"Type" + "\":" + "\"COMBOBOX\"," + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
    });

    // rc-fiscalyear
    $(".rc-fiscalyear").each(function (i) {
        jsonObj += "{" + "\"Type" + "\":" + "\"COMBOBOX\"," + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
    });

    //rc-num-range
    $(".rc-num-range").each(function (i) {
        jsonObj += "{" + "\"Type" + "\":" + "\"NUMRANGE\"," + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Value" + "\":" + "\"" + $(this).val() + "\"},"
        jsonObj += "{" + "\"Type" + "\":" + "\"NUMRANGE\"," + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "_to" + "\"," + "\"Value" + "\":" + "\"" + $("#" + $(this).attr("Id") + "_to").val() + "\"},"
    });

    // rc-lookup
    $(".rc-lookup").each(function (i) {
        jsonObj += "{" + "\"Type" + "\":" + "\"LOOKUP\"," + "\"Id" + "\":" + "\"" + $(this).attr("Id") + "\"," + "\"Value" + "\":" + "\"" + $(this).val().replace(/^,|,$/g, '') + "\"},"
    });
    return jsonObj;
}


function getManual(doc) {
    $.ajax({
        type: 'GET',
        url: 'Home/GetManual',
        data:"doc="+doc,
        cache: false,
        beforeSend: function () {
            $("#introLoader").show();
            $("#ctrlSave").hide();
        },
        complete: function () {
            $("#introLoader").hide();
        },
        success: function (result) {
            $("#WidgetHolder").html(result);
        }
    });
}
function getAssignUserGroup() {
    $.ajax({
        type: 'GET',
        url: 'UserInfo/AssignUserGroup',
        cache: false,
        beforeSend: function () {
            $("#introLoader").show();
            $("#ctrlSave").hide();
            //$(".admin-dropdown").hide();
        },
        complete: function () {
            $("#introLoader").hide();
        },
        success: function (result) {
            $("#WidgetHolder").html(result);
        }
    });
}
function getUserGroupSettings() {

    $.ajax({
        type: 'GET',
        url: 'UserGroupInfo',
        cache: false,
        beforeSend: function () {
            $("#introLoader").show();
            $("#ctrlSave").hide();
            //$(".admin-dropdown").hide();
        },
        complete: function () {
            $("#introLoader").hide();
        },
        success: function (result) {
            $("#WidgetHolder").html(result);
        }
    });
}
function loadUserPermission() {
    $.ajax({
        type: 'GET',
        url: 'Home/LoadUserPermission',
        cache: false,
        beforeSend: function () {
            $("#introLoader").show();
            $("#ctrlSave").hide();
            //$(".admin-dropdown").hide();
        },
        complete: function () {
            $("#introLoader").hide();
        },
        success: function (result) {
            $("#WidgetHolder").html(result);
        }
    });
}


function saveCurrentSettings() {
    $(".widget-head .edit").each(function () {
        if ($(this).width() > 18) {
            showDialog("Edit Mode Open!", "Before Open Please Close Any Edit Mode!", "ui-icon-alert", 400, 130);
            exit();
        }
    });
    $("#introLoader").show();
    var moduleId = getCurrentModule();
    var column1 = "";
    var column2 = "";
    var column3 = "";
    if (moduleId != "home") {
        var _sql = "";
        var _sql1 = "";
        $("#WidgetHolder .rptItemParent").each(function () {
            _sql1 = "('" + $("#loggedUser").attr('data-networkalias') + "','" + $(this).attr("data-moduleid") + "','" + $(this).attr("data-val") + "','" + $(this).attr("data-grpname") + "','"
            $(this).find(".rptItem").each(function () {
                _sql += _sql1 + $(this).attr("data-val") + "','" + $(this).text() + "'),";
            });
        });
        _sql = encodeURIComponent(_sql);

        var _sqlLayout = "";
        var count = 1;
        $("#column1 .rptItemParent").each(function () {
            _sqlLayout += "('" + $("#loggedUser").attr('data-networkalias') + "','" + $(this).attr("data-moduleid") + "','" + $(this).attr("data-val") + "','" + $(this).attr("data-grpname") + "',1," + count + "),";
            count++;
        });
        count = 1;
        $("#column2 .rptItemParent").each(function () {
            _sqlLayout += "('" + $("#loggedUser").attr('data-networkalias') + "','" + $(this).attr("data-moduleid") + "','" + $(this).attr("data-val") + "','" + $(this).attr("data-grpname") + "',2," + count + "),";
            count++;
        });
        count = 1;
        $("#column3 .rptItemParent").each(function () {
            _sqlLayout += "('" + $("#loggedUser").attr('data-networkalias') + "','" + $(this).attr("data-moduleid") + "','" + $(this).attr("data-val") + "','" + $(this).attr("data-grpname") + "',3," + count + "),";
            count++;
        });
        _sqlLayout = encodeURIComponent(_sqlLayout);
        //console.log(_sqlLayout);
        $.ajax({
            type: 'POST',
            url: 'Home/UpdateDashboard',
            data: "sql=" + _sql + "&layout=" + _sqlLayout + "&module=" + $("#lnkModuleReports").attr("data-class"),
            beforeSend: function () {
            },
            complete: function () {
            },
            success: function (result) {

                if (result == "True") {
                    var dialogInit = '{ "dialogs" : [' + '{ "Title":"Saved Successfully" , "Body":"Dashboard has successfully been saved!","Icon":"ui-icon-info","Width":"400","Height":"130" }' + ']}';
                    loadDashBoard("Home/LoadDashboard", dialogInit);
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status);
                alert(thrownError);
            }
        });
    }
    else {
        //Home Module
        var _module = "";
        var _rptGroup = "";
        var _rptGroupName = "";
        var _values = "";
        var _allVals = "";
        $("#column1 .rptBrowser").each(function () {

            _module = $(this).parent().attr("data-val");
            $(this).find(".rptGroup").each(function () {
                var _grp = $(this);
                _rptGroup = $(this).attr("data-val");
                _rptGroupName = $(this).attr("data-name");

                _values = prepareSQLWithModuleRptGroup(_module, _rptGroup, _rptGroupName);
                $(this).find("li").each(function () {
                    _allVals += _values + "'" + $(this).attr("data-val") + "','" + trim($(this).text()) + "'),";
                });
            });
        });
        //alert(_allVals);
        //exit();
        $("#column2 .rptBrowser").each(function () {

            _module = $(this).parent().attr("data-val");
            $(this).find(".rptGroup").each(function () {
                var _grp = $(this);
                _rptGroup = $(this).attr("data-val");
                _rptGroupName = $(this).attr("data-name");

                _values = prepareSQLWithModuleRptGroup(_module, _rptGroup, _rptGroupName);
                $(this).find("li").each(function () {
                    _allVals += _values + "'" + $(this).attr("data-val") + "','" + trim($(this).text()) + "'),";
                });
            });
        });
        $("#column3 .rptBrowser").each(function () {

            _module = $(this).parent().attr("data-val");
            $(this).find(".rptGroup").each(function () {
                var _grp = $(this);
                _rptGroup = $(this).attr("data-val");
                _rptGroupName = $(this).attr("data-name");

                _values = prepareSQLWithModuleRptGroup(_module, _rptGroup, _rptGroupName);
                $(this).find("li").each(function () {
                    _allVals += _values + "'" + $(this).attr("data-val") + "','" + trim($(this).text()) + "'),";
                });
            });
        });
        _sqlStr = "sql=" + encodeURIComponent(_allVals.substr(0, _allVals.length - 1) + ";");


        _allVals = "";
        var i = 1;
        $("#column1 .widget-head h3").not(":first").each(function () {
            _module = $(this).text().split('-')[0];
            $(this).parent().parent().find(".rptBrowser").each(function () {
                _rptGroup = $(this).parent().attr("data-val");
                _allVals += prepareSQLWithModuleRptGroupLayout(_rptGroup, _module) + 1 + "," + i + "),";
                i++;
            });
        });
        i = 1;
        $("#column2 .widget-head h3").each(function () {
            _module = $(this).text().split('-')[0];
            $(this).parent().parent().find(".rptBrowser").each(function () {
                _rptGroup = $(this).parent().attr("data-val");
                _allVals += prepareSQLWithModuleRptGroupLayout(_rptGroup, _module) + 2 + "," + i + "),";
                i++;
            });
        });
        i = 1;
        $("#column3 .widget-head h3").each(function () {
            _module = $(this).text().split('-')[0];
            $(this).parent().parent().find(".rptBrowser").each(function () {
                _rptGroup = $(this).parent().attr("data-val");
                _allVals += prepareSQLWithModuleRptGroupLayout(_rptGroup, _module) + 3 + "," + i + "),";
                i++;
            });
        });
        var _sqlRel = "sqlLayout=" + encodeURIComponent(_allVals.substr(0, _allVals.length - 1) + ";");


        //console.log(_sqlStr);
        $.ajax({
            type: 'POST',
            url: 'Home/UpdateHomeDashboard',
            data: _sqlStr + "&" + _sqlRel,
            beforeSend: function () {
                //alert('before send');
            },
            complete: function () {
                //alert('Complete');
            },
            success: function (result) {
                //alert(result);
                if (result == "True") {
                    var dialogInit = '{ "dialogs" : [' + '{ "Title":"Saved Successfully" , "Body":"Dashboard has successfully been saved!","Icon":"ui-icon-info","Width":"400","Height":"130" }' + ']}';
                    loadDashBoard("Home/LoadHomeDashboard", dialogInit);
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status);
                //alert(thrownError);
            }
        });
    }
}
function prepareSQLWithModuleRptGroup(_module, _rptGroup, _rptGroupName) {
    return "('" + $("#loggedUser").attr('data-networkalias') + "','" + _module + "','" + _rptGroup + "','" + _rptGroupName + "',";
}
function prepareSQLWithModuleRptGroupLayout(_module, _rptGroup) {
    return "('" + $("#loggedUser").attr('data-networkalias') + "','" + _module + "','" + _rptGroup + "',";
}

function showBredCrumbReport(obj) {
    $("#bredCrumb_holder").css("display", "inline-block");
}

function ajaxReportUILoad(report, dataarea) {
    var w = $("#WidgetHolder").width() / 2 - 400;
    var h = $("#WidgetHolder").height() / 2 - 100;
    $("#WidgetHolder").html("<div id='rc-Large-Loadder' style='margin-left:" + w + "px;margin-top:" + h + "px;'><img src='Images/Theme1/loading_final.gif' /></div>");
    qStr = "report=" + report + "&dataAreaId=" + dataarea;
    $.ajax({
        type: 'GET',
        cache: false,
        url: 'Home/LoadReportUI',
        data: qStr,
        beforeSend: function () {
        },
        complete: function () {
        },
        success: function (result) {
            $("#ctrlSave").hide();
            $("#rc-Large-Loadder").remove();
            $("#WidgetHolder").html(result);
            $(".rptItem").css("cursor", "pointer");
            //
            if (_sysautopreview == 1) {
                var loader = setInterval(function () { $("#btnRCSubmit").trigger("click"); clearInterval(loader); }, 1000);
            }
            else { // Don Not Neet To Load Preview Images if sysautopreview = 1
                var qStr = "ReportId=" + report;
                $.ajax({
                    type: "GET",
                    url: "Home/GetReportPreviewImages",
                    data: qStr,
                    success: function (data) {
                        if (data != "") {
                            $("#rptPreviewer").html(data);
                            $("#rptPreviewer .group1").colorbox({ rel: 'group1' });
                        }
                    },
                    error: function (xhr, ajaxOptions, error) {
                    }
                });
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.status + ":" + thrownError);
        }
    });
}
function loadReportUICore(obj) {
    var _rpt = $(obj).parent().attr("data-val");
    var _isBookMark = false; //(($(obj).parent().parent().parent().attr("id") == "bookmark") ? true : false);

    if ($(obj).parent().parent().attr("id") == "favList") {
        _isBookMark = true;
        if ($(".leftPane-item-header-ctrlSave").css("background-image").indexOf("save.png") > 0)
            return;
    }
    if ($(obj).parent().css("cursor") == "move") {
        return;
    }

    var moduleId = "";
    moduleId = getCurrentModule();
    var _width = 0;
    if (moduleId != "home")
        _width = $(obj).parent().parent().parent().parent().find(".edit").eq(0).width();
    else
        _width = $(obj).parent().parent().parent().parent().parent().parent().parent().find(".edit").eq(0).width();

    var admin_submenu = $(obj).parent().parent();
    
    
    if (_width < 20) { // if edit menu clicked to edit then report will not load

        if (!admin_submenu.hasClass("sub-admin-dropdown")) {
            $("#bredCrumb_root").text((_isBookMark == false ? $(obj).parent().parent().attr("data-val") : "My Favourite") + " > ");
            $("#bredCrumb_root").css("background-color", $("#lnkDataArea").attr("data-color"));
            $("#bredCrumb_root").css("color", $("#lnkDataArea").attr("data-forecolor"));

            $(".bredCrumb_Item").remove();
            $("<span class='bredCrumb_Item' data-val='" + $(obj).parent().attr("data-val") + "' style='font-size:10px; padding:3px; margin-top:2px; margin-left:4px; text-decoration:underline;'>" + $(obj).parent().text() + "</span>").insertAfter($("#bredCrumb_root"));
            $("#bredCrumb_holder").html($(obj).parent().parent().parent().html());
            $("#bredCrumb_holder .rptDelete").remove();
            $("#bredCrumb_holder ul").attr("data-request", "BREDCRUMB");
            $("#bredCrumb").css("display", "inline-block");
        }

        ajaxReportUILoad(_rpt, $("#lnkDataArea").text());
    }
    
}
function loadReportUI(obj) {

    if ($("#frmRCDynamic").length == 0) {
        loadReportUICore(obj);
    }
    else {
        var willAsk4Save = true;
        var currentUISelection = "";
        
        if ($("#frmRCDynamic.setup-form").length = 0) { // if not setup form

            currentUISelection = getCurrentSelectionAsJSON();
            currentUISelection = currentUISelection.replace(/^,|,$/g, '');

            if (defaultUIJSONAfterLoad != "") {
                if (defaultUIJSONAfterLoad.replace(/^,|,$/g, '') == currentUISelection) {
                    willAsk4Save = false;
                }
            }
            else if (defaultUIJSON != "") {
                if (defaultUIJSON.replace(/^,|,$/g, '') == currentUISelection) {
                    willAsk4Save = false;
                }
            }
        }
        else {
            willAsk4Save = false;
        }
        if (willAsk4Save) {
            $("<div id='rc-dialog-confirm' title='Save and Exit or Exit?'><p><span class='ui-icon ui-icon-alert' style='float:left; margin:0 7px 20px 0;'></span>Do you want to save last filter settings of " + $("#hRptTitle").text() + "?</p></div>").dialog({
                resizable: false,
                width: 400,
                height: 200,
                modal: true,
                buttons: {
                    "Yes": function () {
                        //var jsonObj = getCurrentSelectionAsJSON();
                        var qStr = "formId=" + $("#frmRCDynamic").attr("data-val") + "&formData=" + currentUISelection;
                        $.ajax({
                            type: "POST",
                            url: "Home/SaveCurrentUI",
                            data: qStr,
                            success: function (data) {
                                loadReportUICore(obj);
                            },
                            error: function (xhr, ajaxOptions, error) {
                                alert(error);
                            }
                        });
                        $("#rc-dialog-confirm").remove();
                        return saveCurrentUI;
                    },
                    "No": function () {
                        //alert(defaultUIJSON);
                        $("#rc-dialog-confirm").remove();
                        loadReportUICore(obj);
                    }
                }
            });
        }
        else {
            loadReportUICore(obj);
        }
    }
}
function resetCurrentModule(obj) {
    loadDashBoard("Home/LoadDefaultDashboard");
}
function deleteReport(obj) {
    // $(obj).parent().remove();
    if ($(obj).parent().parent().find("li").length > 1) {
        $(obj).parent().hide('slow', function () { $(obj).parent().remove(); });
    }
    else {
        if ($(obj).parent().parent().attr("id") != "favList")
            alert("You cannot delete this report. At least one report must remain in a widget.");
        else
            $(obj).parent().hide('slow', function () { $(obj).parent().remove(); });
    }
}

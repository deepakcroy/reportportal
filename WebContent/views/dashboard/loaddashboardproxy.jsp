<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="net.aditri.ui.dashboard.*"%>
<%@ page import="net.aditri.web.utility.KeyValue"%>
<style>
.widget-header .fontello-icon-gauge-1{color:#7d7d7d;cursor:default;}
.modal-scrollable{overflow-x: hidden;}
.anet-modal-header{background: #f0f2f8;text-align: right; padding:2px;}
.anet-dashboard-scale-progress{margin-top:20px;margin-bottom:0;}
.anet-dashboard-scale-range{width:92%;}
.ui-slider-handle{
    margin-top: -12px;
    padding: 2px 8px;
    border-radius: 4px;
    
    
    
    background: #e5e7eb;
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #e5e7eb), color-stop(100%, #d8dade));
    background: -webkit-linear-gradient(top, #e5e7eb 0%, #d8dade 100%);
    background: -moz-linear-gradient(top, #e5e7eb 0%, #d8dade 100%);
    background: -ms-linear-gradient(top, #e5e7eb 0%, #d8dade 100%);
    background: -o-linear-gradient(top, #e5e7eb 0%, #d8dade 100%);
    background: linear-gradient(to bottom, #e5e7eb 0%, #d8dade 100%);
    filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#ffe5e7eb', endColorstr='#ffd8dade', GradientType=0 );
    filter: progid:dximagetransform.microsoft.gradient(enabled=false);
    -webkit-box-shadow: inset 0px 1px 0 rgba(255, 255, 255, 1);
    -moz-box-shadow: inset 0px 1px 0 rgba(255, 255, 255, 1);
    box-shadow: inset 0px 1px 0 rgba(255, 255, 255, 1);
 
 	color: #5f5c5c!Important;text-shadow: 0px 1px #f9f9f9!Important;   
}
.ui-slider-handle:hover{text-decoration:none!Important;}

.ui-slider { position: relative; text-align: left; }
.ui-slider .ui-slider-handle { position: absolute; z-index: 2; width: 1.2em; height: 1.2em; cursor: default; }
.ui-slider .ui-slider-range { position: absolute; z-index: 1; font-size: .7em; display: block; border: 0; background-position: 0 0; }

.ui-slider-horizontal { height: .8em; }
.ui-slider-horizontal .ui-slider-handle { top: -.3em; margin-left: -.6em; }
.ui-slider-horizontal .ui-slider-range { top: 0; height: 100%; }
.ui-slider-horizontal .ui-slider-range-min { left: 0; }
.ui-slider-horizontal .ui-slider-range-max { right: 0; }
</style>
<div class="anet-dashboard-proxy">
<% 
Dashboard oDashboard=null;
if (request.getAttribute("objDashboard")!=null)
{
	oDashboard = (Dashboard)request.getAttribute("objDashboard");
	//out.println("<div class='span12' style='margin:0;'><h4 class='ct-dboard-title'><a class='anet-widget-btn-fullscreen-dboard btn btn-link btn-glyph fontello-icon-popup-4' anet-dashboard='"+oDashboard.getId()+"' style='margin:4px;background-color:#797979;color:#060505;'></a>"+oDashboard.getTitle()+"</h4><p class='ct-dboard-desc'>"+oDashboard.getDescription()+"</p></div>");
}

if(oDashboard!=null)
{
	out.println(oDashboard.getLayout());
}
%>
</div>
<script type="text/javascript">

$(document).ready(function(){

	
		
	$(".modal-scrollable").niceScroll({
		cursoropacitymin:1,
		cursoropacitymax:1,
		cursorcolor:"#b5b5b5",
		cursorwidth:"6px",
		cursorborder:"",
		cursorborderradius:"6px",
		usetransition:600,
		background:"",
		zindex:'99999999',
		railoffset:{top:10,left:-4},
		bouncescroll: true
	});
	
	$(".anet-dashboard-proxy .anet-widget").each(function(){
		var el = $(this);
		
		var _anteDashboard = el.attr("anet-dashboard"); 
		if (typeof _anteDashboard === "undefined") {
			_anteDashboard='<%=oDashboard.getId()%>';
		    el.attr("anet-dashboard",_anteDashboard);
		}
		
		var _anetContentOnly = el.attr("anet-contentonly");
		if (typeof _anetContentOnly === "undefined")
			_anetContentOnly = false;
		else
			_anetContentOnly = (el.attr("anet-contentonly")=='true');
		
		el.anetChartifier({
			widget:{
				id: el.attr('anet-id')+"_proxy",
				dashboard:el.attr('anet-dashboard'),
				reference:el.attr('anet-widget'),
				contentOnly:_anetContentOnly,			
				toolbar:{
					loader:"${pageContext.request.contextPath}/assets/img/system/loading35.gif"
				}
			}
		});
	});
	
	<%-- $(".anet-dashboard-proxy .anet-widget1").each(function(){
		var _anteDashboard = $(this).attr("anet-dashboard"); 
		if (typeof _anteDashboard === "undefined") {
			_anteDashboard='<%=oDashboard.getId()%>';
		    $(this).attr("anet-dashboard",_anteDashboard);
		}
		var anetId=$(this).attr("anet-id")+"_proxy";
		$(this).addClass("widget widget-box widget-collapsible").removeAttr("anet-id");
		var header=$("<div>");
		
		$(header).addClass("widget-header no-border");
		$(header).html("<h4><i class='btn btn-glyph btn-link fontello-icon-gauge-1'></i><img class='anet-widget-loader' src='${pageContext.request.contextPath}/assets/img/system/loading35.gif' /></h4>")
		var widgetTool=$(" <div class='widget-tool'>");
		var toggle = 
		"<a class='anet-widget-btn-filter btn-table btn btn-glyph btn-link  fontello-icon-filter'></a>"
		+"<a class='anet-widget-btn-table btn btn-glyph btn-link  fontello-icon-table'></a>"
		+"<a class='anet-widget-btn-print btn btn-glyph btn-link   fontello-icon-print-2'></a>"
		+"<a class='anet-widget-btn-reload btn btn-glyph btn-link  fontello-icon-spin3'></a>"
		+"<a class='anet-widget-btn-maximize btn btn-glyph btn-link  fontello-icon-resize-full-2'></a>";
		
		
		$(widgetTool).html(toggle);
		$(header).append(widgetTool);
		
		var content=$("<div>");
		$(content).addClass("widget-content").attr("id",anetId);
		
		var contentDataGridToolBar = $("<div>");
		$(contentDataGridToolBar).html("<img class='anet-widget-dg-loader' src='${pageContext.request.contextPath}/assets/img/system/loading35.gif' style='display:none;' />");
		var contentDataGrid = $("<div>");
		$(contentDataGrid).addClass("widget-content-datagrid").attr("id","datagrid_"+anetId);
		
		$(this).html();
		$(this).append(header);
		$(this).append(content).append(contentDataGridToolBar).append(contentDataGrid).removeClass("anet-widget");
		
		var _url= "dashboard/initwidget"; 
		var _qStr= "dboard="+_anteDashboard+"&wgt="+$(this).attr("anet-widget")+"&renderTo="+anetId;
		 $.ajax({
	        type: "GET",
	        url: _url,
	        data: _qStr,
	        cache:false,
	        async : true,
	        success: function (data) {
	        	$(content).html(data);
	        	$(header).find(".anet-widget-loader").hide();
	        	window.dispatchEvent(new Event('resize')); //resize window for sizing high charts if out of div
	        },
	        error: function (xhr, ajaxOptions, error) {
	        }
	    });
	});
	$(".anet-widget-btn-fullscreen-dboard").on('click', function(event){
		event.stopPropagation();
	    event.stopImmediatePropagation();
	    
	
	    var _url= "dashboard/loaddashboardproxy"; 
		var _qStr= "dboard="+$(this).attr("anet-dashboard")
	    _url=_url+"?"+_qStr;
	    initWidgetModal(_url,80,function(){
	    });
	    
	}); --%>
	
	
	
	
});
</script>
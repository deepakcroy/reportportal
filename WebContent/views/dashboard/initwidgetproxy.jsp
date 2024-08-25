<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="net.aditri.ui.dashboard.*"%>
<%@ page import="net.aditri.web.utility.KeyValue"%>
<style>
.anet-widget{min-height: 300px; margin: 0 auto;}
.anet-dboard-title{font-size:14px;}
.anet-dboard-desc{font-size:11px;font-style:italic;}
.modal-scrollable{overflow-x: hidden;}
</style>
<script>
function renderHighCharts(renderTo,options,extOPtions,jsonXAxisData,jsonSerisColumnData)
{
	var finalOptions = eval("({" + extOPtions + options + "})");
	if(jsonXAxisData!="")
	{
		var arr=jsonXAxisData.split(",");
		var arrTemp=[];
		for(i=0;i<arr.length;i++)
		{
			arrTemp[i] = arr[i].replace(/[\""]/g, '\\"');
		}
		if(typeof finalOptions.xAxis === 'undefined')
			finalOptions.xAxis={};
		finalOptions.xAxis.categories=arrTemp;
	}
	if(jsonSerisColumnData!="")
	{
		var sr = "["+jsonSerisColumnData+"]";
		finalOptions.series = eval(sr);
	}
	$('#'+renderTo).highcharts(finalOptions);
}
</script>

<div class="widget">
<%
String widgetHtml=null;
if (request.getAttribute("widgetHtml")!=null)
{
	out.println(request.getAttribute("widgetHtml"));
}
 %>
</div>
<script>
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
	
	$(".anet-widget-proxy .anet-widget").each(function(){
		var el = $(this);
		el.anetChartifier({
			widget:{
				id: el.attr('anet-id'),
				dashboard:el.attr('anet-dashboard'),
				reference:el.attr('anet-widget'),				
				toolbar:{
					loader:'${pageContext.request.contextPath}/assets/img/system/loading35.gif'
				}
			}
		});
	});
	
	/* $(".anet-widget-proxy .anet-widget1").each(function(){
		var anetId=$(this).attr("anet-id");
		$(this).addClass("widget widget-box widget-collapsible").removeAttr("anet-id");
		//var anetWidget = $(this);
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
		var _qStr= "dboard="+$(this).attr("anet-dashboard")+"&wgt="+$(this).attr("anet-widget")+"&renderTo="+anetId;
		 $.ajax({
	        type: "GET",
	        url: _url,
	        data: _qStr,
	        cache:false,
	        async : true,
	        beforeSend: function () {
	        	$(widgetTool).find(".anet-widget-btn-maximize").eq(0).hide(); 
	        	$("<a class='anet-widget-btn-fullscreen btn btn-glyph btn-link  fontello-icon-popup-4'></a><a class='anet-widget-btn-exit btn btn-glyph btn-link  fontello-icon-cancel-5'></a>").insertAfter($(widgetTool).find(".anet-widget-btn-maximize").eq(0));
	        },
	        complete: function () {
	        },
	        success: function (data) {
	        	$(content).html(data);
	        	$(header).find(".anet-widget-loader").hide();
	        	
	        	$(header).find(".anet-widget-btn-exit").on('click', function(event){
	        		event.stopPropagation();
	    			event.stopImmediatePropagation();
	    			
	    			//$(".modal").modal('toggle');
	    			$(".anet-widget-proxy").closest(".modal").modal('hide');
	    			exitfullScreen(document.documentElement);
	    			window.dispatchEvent(new Event('resize')); // window resize triggered
	    			
	    			
	        	});
	        	$(header).find(".anet-widget-btn-fullscreen").on('click', function(event){
	        		event.stopPropagation();
	    			event.stopImmediatePropagation();
	    			
	    			fullScreen(document.documentElement);
	    			
	    			var modal = $(".modal-dialog").parent();
	    			$(modal).css({"top":"0px","left":"0px","width":"99.3%","margin":"0px"});
	    			$(modal).height(screen.availHeight);
	    			
	    			
	    			
	    			$(content).find(".highcharts-container").eq(0).height(screen.availHeight-80);
	    			window.dispatchEvent(new Event('resize')); //resize window for sizing high charts if out of div
	        	});
	        	
	        },
	        error: function (xhr, ajaxOptions, error) {
	        }
	    });
	}); */

	
});
</script>
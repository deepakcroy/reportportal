<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="net.aditri.web.listener.AppEnv,com.app.module.Module"%>
<%@ page import="java.util.List"%>

<style>
#page-content .well-black .widget-box{
	padding-bottom: 4px;
    background-color: #dfe1e5;
}
.widget-header .fontello-icon-gauge-1{color:#7d7d7d; cursor:default;}
.dashboard-holder{min-height:600px;}
.anet-widget-btn-fullscreen-dboard{margin: 4px;color: #ffffff;}
.anet-widget-btn-fullscreen-dboard:hover{color:red;}
</style>
<%-- <s:text name="#session.LOGGEDUSER.getMenuList().get(0).getTitle()" />

<s:property value="message"/>
<s:textfield name="message"/>  --%>
<div class='span12' style='margin:5px 18px;'><a class='anet-widget-btn-fullscreen-dboard btn btn-link btn-glyph fontello-icon-popup-4'></a></div>

<div class="dashboard-holder">
<div class="well well-black">
	<%
	 //List<Module> modules = AppEnv.getModules();
	 //out.print(modules.get(0).getName());
	 %>
</div>
</div>
<script src="${pageContext.request.contextPath}/assets/plugins/highcharts/code/highcharts.js"></script>
<%-- <script src="${pageContext.request.contextPath}/assets/plugins/highcharts/code/modules/exporting.js"></script> --%>
<script type="text/javascript">

$(document).ready(function(){

	$(".anet-widget").each(function(){
		var anetId=$(this).attr("anet-id");
		$(this).addClass("widget widget-box widget-collapsible").removeAttr("anet-id");
		//var anetWidget = $(this);
		var header=$("<div>");
		
		$(header).addClass("widget-header no-border");
		$(header).html("<h4><i class='btn btn-glyph btn-link fontello-icon-gauge-1'></i><img class='anet-widget-loader' src='assets/img/system/loading35.gif' /></h4>")
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
		//alert(_qStr);
		 
		 $.ajax({
	        type: "GET",
	        url: _url,
	        data: _qStr,
	        cache:false,
	        async : true,
	        success: function (data) {
	        	//console.log(data);
	        	$(content).html(data);
	        	$(header).find(".anet-widget-loader").hide();
	        },
	        error: function (xhr, ajaxOptions, error) {
	        }
	    });
		
		
	});

	
});
</script>



<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="net.aditri.ui.dashboard.*"%>
<%@ page import="net.aditri.web.utility.KeyValue"%>
<style>
.widget-header .fontello-icon-gauge-1{color:#7d7d7d;cursor:default;}
.anet-dashboard-bookmark{color:red;float:right; background-color:red;}
.anet-widget-btn-fullscreen-dboard{margin: 4px;color: #ffffff;}
.anet-widget-btn-fullscreen-dboard:hover{color:red;}
</style>
<% 
Dashboard oDashboard=null;
if (request.getAttribute("objDashboard")!=null)
{
	oDashboard = (Dashboard)request.getAttribute("objDashboard");
	out.println("<div class='span12' style='margin:0;'><h4 class='ct-dboard-title'><a class='anet-widget-btn-fullscreen-dboard btn btn-link btn-glyph fontello-icon-popup-4' anet-dashboard='"+oDashboard.getId()+"'></a>"+oDashboard.getTitle()+"<a class='anet-dashboard-bookmark btn btn-glyph fontello-icon-bookmark-2'></a></h4><p class='ct-dboard-desc'>"+oDashboard.getDescription()+"</p></div>");
}

if(oDashboard!=null)
{
	out.println(oDashboard.getLayout());
}
%>
<script type="text/javascript">

$(document).ready(function(){

	$(".anet-widget").each(function(){
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
				id: el.attr('anet-id'),
				dashboard:el.attr('anet-dashboard'),
				reference:el.attr('anet-widget'),
				contentOnly:_anetContentOnly,			
				toolbar:{
					loader:"${pageContext.request.contextPath}/assets/img/system/loading35.gif"
				}
			}
		});
	});
	
	
	$(".anet-widget-btn-fullscreen-dboard").on('click', function(event){
		event.stopPropagation();
	    event.stopImmediatePropagation();

	    var _url= "dashboard/loaddashboardproxy"; 
		var _qStr= "dboard="+$(this).attr("anet-dashboard")
	    _url=_url+"?"+_qStr;
	    initDashboardModal(_url,99.5,function(){
	    	fullScreen(document.documentElement);
	    });
	});
	
	
	
	
});
</script>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
<%
/* out.print("\n"+request.getAttribute("sendToJSP"));
out.print("\n"+request.getAttribute("sendURL"));
String currentURL = null;
if( request.getAttribute("javax.servlet.forward.request_uri") != null ){
    currentURL = (String)request.getAttribute("javax.servlet.forward.request_uri");
}
if( currentURL != null && request.getAttribute("javax.servlet.include.query_string") != null ){
    currentURL += "?" + request.getQueryString();
}
out.print("\n<br/>"+currentURL);
out.print("\n<br/>"+request.getRequestURL().toString()); */
%>
<div class="dashboard-holder">
<div class="well well-black">
	<div class="row-fluid">
	    <div class="span6">
	        <div class='anet-widget' anet-id='widget1' anet-dashboard="pos/dailyvending" anet-widget="widget1"></div>
	    </div>
		<div class="span6">
        	<div class='anet-widget' anet-id='widget2' anet-dashboard="sales/daily-sales" anet-widget="widget2"></div>
    	</div>
	</div>
	<div class="row-fluid">
		<div class="span6">
			<div class='anet-widget' anet-id='widget3' anet-dashboard="common/noofvending" anet-widget="widget1"></div>
		</div>
		<div class="span6">
			<div class='anet-widget' anet-id='widget4' anet-dashboard="common/noofvending" anet-widget="widget2"></div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<div class='anet-widget' anet-id='widget5' anet-dashboard="common/noofvending" anet-widget="totalvending"></div>
		</div>
	</div>
</div>
</div>
<script src="${pageContext.request.contextPath}/assets/plugins/highcharts/code/highcharts.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/anet-chart-builder/js/anet-chartbuilder.js"></script>
<script type="text/javascript">

$(document).ready(function(){

	$(".anet-widget").each(function(){
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
	
});
</script>



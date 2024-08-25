<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="net.aditri.ui.dashboard.*"%>
<%@ page import="net.aditri.web.utility.KeyValue"%>
<style>
.ct-widget{min-height: 300px; margin: 0 auto;}
.ct-dboard-title{font-size:14px;}
.ct-dboard-desc{font-size:11px;font-style:italic;}
</style>
<script>
function renderHighCharts(renderTo,options,extOPtions,jsonXAxisData,jsonSerisColumnData)
{
	//console.log("({" + extOPtions + options + "})");
	var finalOptions = eval("({" + extOPtions + options + "})");
	if(jsonXAxisData!="")
	{
		var arr=jsonXAxisData.split(",");
		var arrTemp=[];
		for(i=0;i<arr.length;i++)
		{
			arrTemp[i] = arr[i].replace(/[\""]/g, '\\"');
		}
		//var xs = "["+jsonXAxisData+"]";
		//console.log(xs);
		finalOptions.xAxis={};
		//finalOptions.xAxis.categories=eval(xs);
		finalOptions.xAxis.categories=arrTemp;
		//console.log(finalOptions);
	}
	//console.log(jsonSerisColumnData);
	if(jsonSerisColumnData!="")
	{
		var sr = "["+jsonSerisColumnData+"]";
		//console.log(sr);
		finalOptions.series = eval(sr);
	}
	//console.log(finalOptions);	
	$('#'+renderTo).highcharts(finalOptions);
}
</script>
<% 
Dashboard oDashboard=null;
if (request.getAttribute("objDashboard")!=null)
{
	oDashboard = (Dashboard)request.getAttribute("objDashboard");
	out.println("<h4 class='ct-dboard-title'>"+oDashboard.getTitle()+"</h4><p class='ct-dboard-desc'>"+oDashboard.getDescription()+"</p>");
}
%>
<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto">
<%
if(oDashboard!=null)
{
	out.println(oDashboard.getLayout());
	if(oDashboard.getWidgets()!=null)
	{
	String extOptions="";
 	for(Widget oWidget:oDashboard.getWidgets())
 	{
 		for(Chart oChart:oWidget.getCharts())
 		{
 		if(!oChart.isCredit())
 			extOptions+="credits: {enabled: false},";
 		/* if(oChart.getChartType()!="")
 	 		extOptions+="chart: {type: '"+oChart.getChartType()+"'},"; */
 		//extOptions+="title: {text: '"+oChart.getTitle()+"'},";
 		//extOptions+="subtitle: {text: '"+oChart.getSubTitle()+"'},";
 		for(KeyValue oKeyValue:oChart.getNodes())
 		{
 			extOptions+=oKeyValue.key+":"+oKeyValue.value+",";
 		}
 		/* if(oChart.getControlData()!="")
 			extOptions+="series: "+oChart.getControlData()+","; */
 		extOptions = extOptions.trim();
 		//extOptions = extOptions.substring(0, extOptions.length() - 1);
 		//out.println(extOptions);
 		//Gson gson = new Gson();
 		//String jsonChartData = gson.toJson(oChart.getChartData());
 	
 		%>
 		<script>
 		// load all widget at a time. no asyc call for each dashboard
 		renderHighCharts("<%=oChart.getRenderTo()%>","<%=oChart.getInit()%>","<%=extOptions%>","<%=oChart.getChartData().getJsonXAxisData()%>","<%=oChart.getChartData().getJsonSeriesColumnData()%>")
         </script>
 		<%
 		}
 	}
	}
}
%>
</div>
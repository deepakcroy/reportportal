<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="net.aditri.ui.dashboard.*"%>
<%@ page import="net.aditri.web.utility.KeyValue"%>
<style>
.anet-widget{min-height: 300px; margin: 0 auto;}
.anet-dboard-title{font-size:14px;}
.anet-dboard-desc{font-size:11px;font-style:italic;}
</style>
<script>
function renderHighCharts(renderTo,options,extOPtions,jsonXAxisData,jsonSerisColumnData)
{
	//console.log("({" + extOPtions + options + "})");
	var finalOptions = eval("({" + extOPtions + options + "})");
	//console.log(finalOptions.xAxis);
	//console.log(escape(jsonXAxisData));
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
	//console.log(finalOptions);
	$('#'+renderTo).highcharts(finalOptions);
}
</script>

<div class="widget" style="margin: 0 auto">
<%
Widget oWidget=null;
String renderTo=null;
if (request.getAttribute("oWidget")!=null)
{

	oWidget = (Widget)request.getAttribute("oWidget");
	String extOptions="";
	
	if (request.getAttribute("renderTo")!=null)
		renderTo=request.getAttribute("renderTo").toString().trim();
	
	for(Chart oChart:oWidget.getCharts())
	{
		if(!oChart.isCredit())
			extOptions+="credits: {enabled: false},";
		for(KeyValue oKeyValue:oChart.getNodes())
		{
			extOptions+=oKeyValue.key+":"+oKeyValue.value+",";
		}
		extOptions = extOptions.trim();
		
		//Overwrite renderTo (from UI)
		if(renderTo!=null)
			oChart.setRenderTo(renderTo);
		if(oChart.getChartData()!=null)
		{
		%>
		<script>
		renderHighCharts("<%=oChart.getRenderTo()%>","<%=oChart.getInit()%>","<%=extOptions%>","<%=oChart.getChartData().getJsonXAxisData()%>","<%=oChart.getChartData().getJsonSeriesColumnData()%>")
        </script>
		<%
		}
	} 
}
%>
</div>
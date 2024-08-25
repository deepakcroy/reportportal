<style>
.ct-widget{min-height: 300px; margin: 0 auto;}
.ct-dboard-title{font-size:14px;}
.ct-dboard-desc{font-size:11px;font-style:italic;}
#page-content .well-black .widget-box{
	padding-bottom: 4px;
    background-color: #dfe1e5;
}
.dashboard-holder{min-height:340px;}
</style>

<div class="well well-black">
	<div id="dBoardHolder" class="dashboard-holder">
		<div class="row-fluid">
			<div class="span6">
				<div class='anet-widget' anet-id='widget1' anet-dashboard='common/noofvending' anet-widget='widgetTransparent'></div>
			</div>
			<div class="span6">
				<div class='anet-widget' anet-id='widget2' anet-dashboard='common/noofvending' anet-widget='widgetPieTransparent'></div>
			</div>
		</div>
		<div class="row-fluid">
			<div id="g1" class="span6 grider">
               <div class="row-fluid">
					<div id="dboardByModule"></div>
               </div> 
            </div>
            
            <div id="g2" class="span6 grider">
               <div class="row-fluid">
					<div id="dboardByModule2"></div>
               </div>
           </div>  
		</div>
		
	</div>
</div>
<script src="${pageContext.request.contextPath}/assets/plugins/highcharts/code/highcharts.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/anet-chart-builder/js/anet-chartbuilder.js"></script>
<script>
$(document).ready(function(){
	
	$(".leaflevel").click(function(){
		$(".leaflevel").removeClass("active");
		$(this).addClass("active");
		$(".accordion-heading").removeClass("active");
		$(this).parent().parent().find(".accordion-heading").eq(0).addClass("active");
		var eml=$(this).find("a").eq(0);
		if($(eml).attr("href").indexOf("#")>0)
		{
			arr=$(eml).attr("href").split("#");
			if(arr.length==2)
				loadDashboard('loaddashboard',arr[1],'dBoardHolder');
		}
		
	});
	if(location.href.indexOf("#")>0)
	{
		arr=location.href.split("#");
		if(arr.length==2)
			loadDashboard('loaddashboard',arr[1],'dBoardHolder');
	}

});
function loadDashboard(_action,_dboardName,_holder)
{
	$.ajax({
        type : 'GET',
        url : _action,
        data : "rpt="+_dboardName,
        beforeSend : function (){
            $("#ct-page-loading").show();
            $("#"+_holder).html("");
        },
        complete : function (){
        	 $("#ct-page-loading").hide();
        },
        success : function (data) {
        	if(data.substring(0,100).indexOf("CT-LOGIN-PAGE")<0) //NOT FOUND
        		$("#"+_holder).html(data);
        	else
        		location.href=_context+"/login";
        },
        error : function (xhr, textStatus, errorThrown) {
          
        }
        
    });
}





$(document).ready(function(){

	//set gradient
	Highcharts.setOptions({
	    colors: Highcharts.map(Highcharts.getOptions().colors, function (color) {
	        return {
	            radialGradient: {
	                cx: 0.5,
	                cy: 0.3,
	                r: 0.7
	            },
	            stops: [
	                [0, color],
	                [1, Highcharts.color(color).brighten(-0.3).get('rgb')] // darken
	            ]
	        };
	    })
	});


	// Create the chart
	if($("#dboardByModule").length>0)
	{
	
		
		Highcharts.chart('dboardByModule', {
	    chart: {
	        type: 'column',
	        backgroundColor: 'transparent',
	        style: {
	            fontFamily: '\'Unica One\', sans-serif'
	        },
	        plotBorderColor: '#606063'
	    },
	     credits: {
	        enabled: false
	    },
	    title: {
	    	text:''
	    },
	    xAxis: {
	        type: 'category',
	        gridLineColor: '#707073',
	        labels: {
	            style: {
	                color: '#E0E0E3'
	            }
	        },
	        lineColor: '#707073',
	        minorGridLineColor: '#505053',
	        tickColor: '#707073',
	        title: {
	            style: {
	                color: '#A0A0A3'
	            }
	        }
	    },
	    yAxis: {
	        title: {
	            text: 'Total percent market share',
	            style: {
	                color: '#A0A0A3'
	            }
	        },
	        gridLineColor: '#4e4d4d',
	        labels: {
	            style: {
	                color: '#E0E0E3'
	            }
	        },
	        lineColor: '#4e4d4d',
	        minorGridLineColor: '#4e4d4d',
	        tickColor: '#707073',
	        tickWidth: 1
	    },
	    legend: {
	        enabled: false
	    },
	    plotOptions: {
	        series: {
	            borderWidth: 0,
	            dataLabels: {
	                enabled: true,
	                format: '{point.y:.1f}%',
	                color: '#F0F0F3',
	                style: {
	                    fontSize: '13px'
	                }                
	            }
	        }
	    },
	
	    tooltip: {
	        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
	    },
	
	    series: [
	        {
	            name: "Browsers",
	            colorByPoint: true,
	            data: [
	                {
	                    name: "Chrome",
	                    y: 62.74,
	                    drilldown: "Chrome"
	                },
	                {
	                    name: "Firefox",
	                    y: 10.57,
	                    drilldown: "Firefox"
	                },
	                {
	                    name: "Internet Explorer",
	                    y: 7.23,
	                    drilldown: "Internet Explorer"
	                },
	                {
	                    name: "Safari",
	                    y: 5.58,
	                    drilldown: "Safari"
	                },
	                {
	                    name: "Edge",
	                    y: 4.02,
	                    drilldown: "Edge"
	                },
	                {
	                    name: "Opera",
	                    y: 1.92,
	                    drilldown: "Opera"
	                },
	                {
	                    name: "Other",
	                    y: 7.62,
	                    drilldown: null
	                }
	            ]
	        }
	    ]
	});
	
	
	
	
// Build the chart
Highcharts.chart('dboardByModule2', {
    chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        type: 'line',
        backgroundColor: 'transparent',
    },
     credits: {
	        enabled: false
	    },
    title: {
        text: ''
    },
    tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
    },
    accessibility: {
        point: {
            valueSuffix: '%'
        }
    },
     xAxis: {
	        type: 'category',
	        gridLineColor: '#707073',
	        labels: {
	            style: {
	                color: '#E0E0E3'
	            }
	        },
	        lineColor: '#707073',
	        minorGridLineColor: '#505053',
	        tickColor: '#707073',
	        title: {
	            style: {
	                color: '#A0A0A3'
	            }
	        }
	    },
    yAxis: {
	        title: {
	            text: 'Total percent market share',
	            style: {
	                color: '#A0A0A3'
	            }
	        },
	        gridLineColor: '#4e4d4d',
	        labels: {
	            style: {
	                color: '#E0E0E3'
	            }
	        },
	        lineColor: '#4e4d4d',
	        minorGridLineColor: '#4e4d4d',
	        tickColor: '#707073',
	        tickWidth: 1
	    },
	    legend: {
	        enabled: false
	    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: true,
                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                connectorColor: 'silver',
                color: '#F0F0F3'
            }
        }
    },
    series: [{
        name: 'Share',
        data: [
            { name: 'Chrome', y: 61.41 },
            { name: 'Internet Explorer', y: 11.84 },
            { name: 'Firefox', y: 10.85 },
            { name: 'Edge', y: 4.67 },
            { name: 'Safari', y: 4.18 },
            { name: 'Other', y: 7.05 }
        ]
    }]
});
	
	
	}
	
	$(".anet-widget").each(function(){
	   var el = $(this);
		el.anetChartifier({
			widget:{
				id: el.attr('anet-id'),
				dashboard:el.attr('anet-dashboard'),
				reference:el.attr('anet-widget'),
				contentOnly:true,
				toolbar:{
					loader:'${pageContext.request.contextPath}/assets/img/system/loading35.gif'
				},
				content:{
					datagrid:{
						autoLoad:false
					}
				}
			}
		});
	});
	
	
});
</script>
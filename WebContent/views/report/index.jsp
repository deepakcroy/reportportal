<%-- <link href="${pageContext.request.contextPath}/assets/css/net.aditri/ui.common.css" rel="stylesheet"> --%>
<%-- <link href="${pageContext.request.contextPath}/assets/css/net.aditri/lookup.css" rel="stylesheet"> --%>

<script src="${pageContext.request.contextPath}/assets/js/net.aditri.ui.common.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/loadReportUI.js"></script>
<link href="${pageContext.request.contextPath}/assets/plugins/anet-ui-toolset/css/anet-uiconverter.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/assets/plugins/anet-ui-toolset/js/anet-uiconverter.js"></script>
<div class="well well-black">
<div style="height:30px; margin-top:-10px;" >
<div id="ct-page-loading" style="display:none;"><i>Please Wait...</i><img src="${pageContext.request.contextPath}/assets/img/system/loading35.gif" /></div>
</div>

<div id="rptHolder">

</div>
</div>
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
				loadReport('loadreportui',arr[1],'rptHolder');
		}
		
	});
	if(location.href.indexOf("#")>0)
	{
		arr=location.href.split("#");
		if(arr.length==2)
			loadReport('loadreportui',arr[1],'rptHolder');
	}

});
function loadReport(_action,_rptName,_holder)
{
	$.ajax({
        type : 'GET',
        url : _action,
        data : "rpt="+_rptName,
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
</script>
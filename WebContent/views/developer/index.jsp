<link href="${pageContext.request.contextPath}/assets/plugins/anet-ui-toolset/css/anet-uiconverter.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/plugins/anet-ui-toolset/css/anet-uibuilder.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/assets/plugins/anet-ui-toolset/js/anet-uibuilder.js"></script>
<div id="ct-page-loading" style="display:none;float:right;"><i>Please Wait...</i><img src="${pageContext.request.contextPath}/assets/img/system/loading35.gif" /></div>
<div class="content" id="developerWrapper">
	
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
				initDeveloper('initdeveloper',arr[1],'developerWrapper');
		}
		
	});
	if(location.href.indexOf("#")>0)
	{
		arr=location.href.split("#");
		if(arr.length==2)
			initDeveloper('initdeveloper',arr[1],'developerWrapper');
	}
});
function initDeveloper(_action,_page,_holder)
{
	$.ajax({
        type : 'GET',
        url : _action,
        data : "typ="+_page, //db-config, intrfc-builder etc.
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
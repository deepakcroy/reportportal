<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<script src="${pageContext.request.contextPath}/assets/plugins/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
</head>
<h6>Global Report Database Config Setup </h6>

<div class="content" id="gblHolder">
	<div id="splitter" class="span12" style="min-height:100px;">
		<div id="LeftPane" class="span4" style="padding-top: 10px;">
			<ul class="btn-toolbar"> 
			     <li><a onclick="loadCreateForm(this)" data-url="${pageContext.request.contextPath}/setup/menu/saveOrUpdateForm"  class="btn btn-small btn-green"><i class=" fontello-icon-list-add"></i> <s:property value="getText('button.create')" /> </a></li>
			     <li>
                     <select id="example6" multiple="multiple" style="display: none;">
                          <option value="1" >Encrypted</option>
                          <option value="0">Not Encrypted</option>
                      </select>
                 </li>
			 </ul>
			<div class="widget widget-simple widget-user">
				<ul class="dbConfigList widget-list list-bordered thumb-large list">
				<c:forEach var="item" items="${dbConfigs}">
					<li>
					<span style='color:red;'>${item.isPWDEncrypt()?"<i class='fontello-icon-lock-5'></i>":"<i class='fontello-icon-lock-open-2'></i>" }</span>
					<span>${item.getName()}</span>
					<span style='float:right;color:blue;cursor:pointer;' onclick="loadDBConfig(this,'${item.getName()}')"><i class="fontello-icon-edit"></i></span>
					</li>
				</c:forEach>
				</ul>
			</div>
		</div>
		<div id="RightPane" class="span8">
			<div id="frm_menu_ops">
					<form id="qForm"></form>
				</div>
		</div>
	</div>
</div>
<script>
function loadDBConfig(obj,cname)
{
	$.ajax({
        type: 'get',
		url: './savereportdbcfgform?cname='+cname,
        //data: $("#form1").serialize(),
        beforeSend: function (){
        	$("#ct-page-loading").show();
			//$("#frm_menu_ops").html("<i class='fa fa-spinner fa-spin'></i>");
        },
        complete : function (){
       	 $("#ct-page-loading").hide();
        },
        success: function (data) {
           $("#frm_menu_ops").html(data);
        },
        error: function(){
        }
    }); 
}
function loadCreateForm(obj)
{
	//alert('test');
	 $.ajax({
	        type: 'get',
			url: './savereportdbcfgform',
	        //data: $("#form1").serialize(),
	        beforeSend: function (){
				//$("#frm_menu_ops").html("<i class='fa fa-spinner fa-spin'></i>");
	        },
	        success: function (data) {
	           $("#frm_menu_ops").html(data);
	        },
	        error: function(){
	        }
	    }); 
}
$(document).ready(function(){
	$('#example6').multiselect({
		button: 'btn btn-small btn-yellow'
	});	
});
</script>
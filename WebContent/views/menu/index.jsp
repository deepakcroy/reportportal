<%@ page import="net.aditri.web.utility.StringHelper,com.prp.models.SessionObject,com.app.module.Module"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<style>
.label-positive[href], .badge-positive[href]{background-color: #41453F;}
.err-key{color:red; font-weight:bold; padding:10px;}
.err-val{padding:10px}
.frm_menu .treeview{background-color:white;}
.frm_menu .treeview i{color:#000000;}
.menu_ctrl_wrapper{display: inline-block; margin-left: 20px;margin-bottom: 5px; font-size: 10px;}
.menu_ctrl{display:inline-block;padding: 0px 4px;}
.menu_ctrl .ctrl{padding:2px;cursor: pointer;}
</style>
<script src="${pageContext.request.contextPath}/assets/plugins/bootstrap-toggle-button/js/bootstrap-toggle-button.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
<div class="content">	
	<div class="widget widget-box">
       <div class="tabbable tabs-left">
        <ul class="nav nav-tabs">
         <%
         SessionObject sessionObj = (SessionObject)session.getAttribute("LOGGEDUSER");
         int tab=1;
         if(sessionObj.getModules()!=null)
         {
         	for(Map.Entry<String,Module> oModule:sessionObj.getModules().entrySet())
         	{
         	%>
         		<li class="<%= (tab==1)?"active":"" %>">
         			<a href="#vtab-<%= oModule.getKey().toString() %>" data-toggle="tab" data-module="<%= oModule.getKey().toString() %>">
         			<% if(oModule.getValue().isEditable()) { %>
         				<span class="fontello-icon-edit"></span>
         			<% } else { %>
         				<span class="fontello-icon-lock-1"></span>
         			<% } %>
         			<%= oModule.getKey().toString() %>
         			</a>
         			<% if(tab==1) { %>
         				<input class="current-module" value="<%= oModule.getKey().toString() %>" type='hidden'/>
         			<% } %>
         		</li>
         	<%
         	tab++;
         	}
         }
        %>
        </ul>
        <div class="tab-content" style='min-height:300px;'>
        	<% 
        	if(sessionObj.getModules()!=null)
        	{
        		@SuppressWarnings("unchecked")
        		Map<String,String> trees=(HashMap<String,String>)request.getAttribute("menuTrees");
        		tab=1;
          		for(Map.Entry<String,Module> oModule:sessionObj.getModules().entrySet())
          		{
          		%>
          		<div class="tab-pane <%= (tab==1)?"active":"" %>" id="vtab-<%=oModule.getKey().toString()%>">
	          		<% if(trees.containsKey(oModule.getKey())) { %>
	          		<div class="splitter span12" style="min-height:400px;">
	           		<div class="LeftPane span5" style="padding-top: 10px;">
	           			<% if(oModule.getValue().isEditable()){ %>
						<ul class="btn-toolbar"> 
						     <li><a onclick="loadCreateForm(this)" data-module='<%=oModule.getKey().toString() %>' data-url="${pageContext.request.contextPath}/setup/menu/saveorupdateform"  class="btn btn-small btn-green"><i class=" fontello-icon-list-add"></i> <s:property value="getText('button.create')" /> </a></li>
						     <li>
			                     <select class="btn-state" multiple="multiple" style="display: none;">
			                          <option value="1" selected>Active</option>
			                          <option value="0">Inactive</option>
			                      </select>
			                 </li>
						 </ul>
						 <% } %>
						<div class="frm_menu" data-module="<%=oModule.getKey().toString() %>" style="margin-top:10px; border: 1px solid #e8e8e8; ">
							<div class="frm_menu_<%=oModule.getKey().toString() %>">
							<%
								out.print("\n"+trees.get(oModule.getKey()));
							%>
							</div>
						</div>
					</div>
					<div class="RightPane span7">
						<div style="min-height: 200px;padding:10px;  width:96%;margin-bottom: 100px;">
										
							<div id="frm_menu_ops_<%=oModule.getKey().toString()%>">
								<form id="qForm_<%=oModule.getKey().toString()%>"></form>
							</div>
						
						</div>
					</div>
					</div>
	          		<% }  %>
          		</div>
          		<%
          		tab++;
          		}
        	}
        	%>
        </div>
       </div>
	</div>
</div>
<script>

function includeInactiveMenue(obj)
{
	refreshMenu(($(obj).is(":checked")?1:0));
}
function refreshMenu(includeInactive)
{
	$.ajax({
        type: 'get',
		url: './menu/refreshmenu',
		data:'status='+includeInactive,
        beforeSend: function (){
			//$(".frm_menu").html("<i class='fa fa-spinner fa-spin'></i>");
        },
		//data:$("#qForm").serialize(),
        success: function (data) {
		 if(data.substring(0,100).indexOf("CT-LOGIN-PAGE")<0) //NOT FOUND
			$("#frm_menu_ops_"+$(obj).attr('data-module')).html(data);
		 else
			location.href=_context+"/login";
           
        },
        error: function(){
        }
    });
}

function saveCurrentMenuItem(obj)
{
	/* if(!isValidated())
		return false; */
	$.ajax({
        type: 'post',
		url: './menu/saveorupdate',
        beforeSend: function (){
			//$("#frm_menu_ops").html("<i class='fa fa-spinner fa-spin'></i>");
        },
		data:$("#qForm_"+$(obj).attr("data-module")).serialize(),
        success: function (response) {
        	var errorMsg="";
	        if(!response.isSuccess){
	        	errorMsg = "Error!!!";
	        }
            $.gritter.add({
        		title: (errorMsg!="")?'Error Occurred...!':'Operation Status...!',
                text: response.message,
    			sticky: (errorMsg!="")?true:false,
    			time: 3000,
    			position: 'bottom-right',
    			class_name: (response.isSuccess)?'gritter-light notyfy_success':'gritter-light notyfy_error'
    		});
        },
        error: function(){
        }
    });
}
function loadCreateForm(obj)
{
	$.ajax({
	        type: 'get',
			url: './menu/saveorupdateform?module='+$('.current-module').val(),
	        //data: $("#form1").serialize(),
	        beforeSend: function (){
				//$("#frm_menu_ops").html("<i class='fa fa-spinner fa-spin'></i>");
	        },
	        success: function (data) {
	           if(data.substring(0,100).indexOf("CT-LOGIN-PAGE")<0) //NOT FOUND
	        		$("#frm_menu_ops_"+$(obj).attr('data-module')).html(data);
	        	else
	        		location.href=_context+"/login";
	        },
	        error: function(){
	        }
	    });
}
$(document).ready(function(){
	//$("#setupWrapper .nav-tabs .active").text().trim()
	$(".LeftPane ul .menu_ctrl .ctrl").on("click",function(){
		switch(($(this).attr("data-val")).toUpperCase())
		{
			case "EDIT":
				 $.ajax({
			        //crossDomain: true, //set as a cross domain requests
			        type: 'get',
					url: './menu/saveorupdateform?id='+$(this).attr("data-id"),
			        //data: $("#form1").serialize(),
			        beforeSend: function (){
						//$("#frm_menu_ops").html("<i class='fa fa-spinner fa-spin'></i>");
			        },
			        success: function (data) {
					  if(data.substring(0,100).indexOf("CT-LOGIN-PAGE")<0) //NOT FOUND
			        		$("#frm_menu_ops_"+$('.current-module').val()).html(data);
			        	else
			        		location.href=_context+"/login";
			        },
			        error: function(){
			        }
			    });
			break;
			default:
				// do nothing
			break;
		}
	});
	$('.btn-state').multiselect({
		button: 'btn btn-small btn-yellow'
	});	
	
	$(".frm_menu").each(function(i){
		var menu =$(this).find("ul").eq(0);
		$(menu).addClass("widget");
		 if(i==0)
			$(menu).treeview();
		else
		{
			$(menu).treeview();
			
			$(menu).find(".expandable-hitarea").each(function(){
				$(this).removeClass("expandable-hitarea").addClass("collapsable-hitarea");
			});
			$(menu).find(".lastExpandable-hitarea").each(function(){
				$(this).removeClass("lastExpandable-hitarea").addClass("lastCollapsable-hitarea");
			});
			$(menu).find(".expandable").each(function(){
				$(this).removeClass("expandable").addClass("collapsable");
			});
			$(menu).find(".lastExpandable").each(function(){
				$(this).removeClass("lastExpandable").addClass("lastCollapsable");
			});
		}
			
	});
	$(".tabs-left .nav li a").click(function(){
		$('.current-module').eq(0).val($(this).attr("data-module"));
	});
});

    
</script>
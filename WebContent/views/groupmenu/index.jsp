<%@ page import="net.aditri.web.utility.StringHelper,com.prp.models.SessionObject,com.app.module.Module"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.frm_menu .treeview{background-color:white;}
.frm_menu .treeview i{color:#000000;}
.menu_ctrl_wrapper{display: inline-block; margin-left: 20px;margin-bottom: 5px; font-size: 10px;}
.menu_ctrl{display:inline-block;padding: 0px 4px;}
.menu_ctrl .ctrl{padding:2px;cursor: pointer;}
.groupList{list-style:none;}
.tab-content{background-color:#ffffff!Important;}
</style>
<h5>Assign Menu to Group</h5>
<div class="content">
	<div id="splitter" class="span12" style="min-height:100px;">
		<div id="LeftPane" class="span3 well" style="padding-top: 10px;">
			<ul class="btn-toolbar"> 
			     <li>
                 <div id="ugall-tgbtn" style="">
                      <input type="checkbox" id="chkAllUG" name="chkAllUG" />
                  </div>
			     </li>
			 </ul>
			<ul class="groupList">
			<c:forEach var="item" items="${groups}">
				<li>
				<input class="group checkbox" id="${item.getId()}" type="checkbox"/> <a data-id="${item.getId()}" data-url="${pageContext.request.contextPath}/setup/groupmenu/listmymenu" href="javascript:void(0)" onClick="getMyMenuList(this)"> ${item.getGroupName()}  </a> <i class=' fontello-icon-right-hand hide'></i>
				</li>
			</c:forEach>
			</ul>
		</div>
		<div id="RightPane" class="span9 well">
			<div style="min-height: 200px;padding:0px 20px;  width:96%;">
				<div>
					<ul class="btn-toolbar"> 
						<li>
						<div id="roleall-tgbtn" style="">
		                	<input type="checkbox" id="chkAllRole" name="chkAllRole" />
		                </div>
		                </li>
		                <li style="">
		                	<a onclick="saveMenuList(this)" data-url="${pageContext.request.contextPath}/setup/groupmenu/saveorupdate"  class="btn btn-small btn-green"><i class="fontello-icon-floppy"></i> <s:property value="getText('button.save')" /> </a>
		                </li>
	                </ul>
                </div>
                
                <div class="widget widget-box">
	                <div class="tabbable tabs-right">
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
		                			<span class="fontello-icon-ok-circle-2"></span>
				         			<%= oModule.getKey().toString() %>
		                			</a>
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
		                		Map<String,String> trees=(HashMap<String,String>)request.getAttribute("menuTrees");
		                		tab=1;
			                	for(Map.Entry<String,Module> oModule:sessionObj.getModules().entrySet())
			                	{
			                		%>
			                		<div class="tab-pane <%= (tab==1)?"active":"" %>" id="vtab-<%=oModule.getKey().toString() %>">
				                		<div class="frm_menu" style="margin-top:5px; ">
											<% 
											if(trees.containsKey(oModule.getKey()))
												out.print("\n"+trees.get(oModule.getKey()));
											 %>
										</div>
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
		</div>
	</div>
</div>

<script>
$(document).ready(function(){
	$('#ugall-tgbtn').toggleButtons({
		width: 160,
		label: {
				enabled: "Uncheck",
				disabled: "Check"
		},
		onChange: function ($el, status, e) {
			$(".groupList .group").each(function(){
				if(status)
				{
					$(this).attr("checked","checked");
					$(this).parent().addClass("checked");
				}
				else
				{
					$(this).removeAttr("checked");
					$(this).parent().removeClass("checked");
				}
					
			});
		}
	});
	
	$('#roleall-tgbtn').toggleButtons({
		width: 160,
		label: {
				enabled: "Uncheck",
				disabled: "Check"
		},
		onChange: function ($el, status, e) {
			$(".frm_menu ul .menuitem").each(function(){
				if(status)
				{
					$(this).attr("checked","checked");
				}
				else
				{
					$(this).removeAttr("checked");
				}
					
			});
		}
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
});

function getMyMenuList(elm)
{
	
	$.ajax({
        type : 'GET',
        url : $(elm).attr("data-url"),
        data : "ugs="+$(elm).attr("data-id"),
        beforeSend : function (){
        	$(".groupList .fontello-icon-right-hand").addClass("hide");
        	$(elm).parent().find(".fontello-icon-right-hand").eq(0).removeClass("hide");
        },
        complete : function (){
        	
        },
        success : function (data) {
        	
           if(Array.isArray(data))
       	   {
        	   $(".frm_menu .menuitem").removeAttr("checked");
        	   $.each( data, function( index, obj ) {
        		   $(".frm_menu #"+data[index].menuid).attr("checked","checked");
        	   });	   
       	   }
        },
        error : function (xhr, textStatus, errorThrown) {
          
        } 
    });
}
function saveMenuList(obj)
{
	$('.gritter-item-wrapper').remove();
    var elm = {}, cart = [];
	$(".groupList .group").each(function(){
		if($(this).is(":checked"))
		{
			elm = {};
			elm.key = $(this).attr("id");
            elm.value = 1;
            cart.push(elm);
		}
	});
	
	strRI="";
	$(".frm_menu .menuitem").each(function(){
		if($(this).is(":checked"))
			strRI += $(this).attr("id")+",";
	});
	strRI=strRI.trim();
	strRI=strRI.substr(0,strRI.length-1);
	if(cart.length==0||strRI.trim()=="")
	{
		$.gritter.add({
			title: '<span><i style="color:red;" class=" fontello-icon-cancel-circle"></i>Error...!!!</span>',
	        text: 'Select atleast one Group and one Role!',
			sticky: true,
			time: 4000,
			position: 'bottom-right',
			class_name: 'gritter-light notyfy_error'
		});
	}
	else
	{
		$.ajax({
            type : 'POST',
            url : $(obj).attr("data-url"),
            //async : false,
            data : "ugs="+JSON.stringify(cart)+"&ris="+strRI, // "ugs="+strUG+"&ris="+strRI,
            beforeSend : function (){
                
            },
            complete : function (){
            	
            },
            success : function (response) {
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
            error : function (xhr, textStatus, errorThrown) {
              
            }
        });
	}
}
</script>
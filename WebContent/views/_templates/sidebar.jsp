<%@ page import="java.util.List,java.util.ArrayList,org.apache.commons.lang3.StringUtils" %>
<%@ page import="com.prp.models.SessionObject,com.prp.models.Menu,com.prp.actions.MenuAction,com.app.module.Module"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix = "fmt"  %> --%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<style>
#mainSideMenu{padding-bottom:40px;}
</style>
<%
String ctx = request.getContextPath();
String[] arrURL = request.getAttribute("javax.servlet.forward.request_uri").toString().split("/");
String parent = StringUtils.capitalize(arrURL[2]); //package
String controller="Home";
if(arrURL.length>3)
	controller=arrURL[3];
String action = com.opensymphony.xwork2.ActionContext.getContext().getName().trim();


List<Menu> menus = new ArrayList<Menu>();
SessionObject sessionObj = (SessionObject)session.getAttribute("LOGGEDUSER");
Module oModule = sessionObj.getModules().get(parent);
if(oModule!=null)
	menus = oModule.getMenus();  
Long parentId=0L;
if(menus.size()>0)
{
	for(Menu m:menus)
	{
		if(m.getModule().equalsIgnoreCase(parent) && m.getParentId()==0L)
		{
			parentId=m.getId();
			break;
		}
	}
}
%>
<%!
String menuTreeStr="";
public String prepareSideMenu(Long parentId,List<Menu> items,Menu oMenu,String ctx, String controller, String action)
{
	//System.out.println(parentId);
	List<Menu> list = new ArrayList<Menu>();
	 list = new MenuAction().getChildren(parentId,items);
	 String[] linkParts = null; 
	 if(list.size()>0)
	 {
		 if(oMenu.getId()==null)
		 	menuTreeStr=menuTreeStr+"<ul id='mainSideMenu' class=\"filetree\">";
		 else
		 {
			menuTreeStr=menuTreeStr+"<ul id='"+oMenu.getTitle().replaceAll("\\s+","")+oMenu.getId()+"'>";
		 }
	 }
	 for(Menu obj:list)
	 {
		Long currentMenuId=obj.getId();
		if(!obj.isLeafLevel())

			menuTreeStr=menuTreeStr+"<li><span class=\"folder\"><a href='#"+obj.getTitle().replaceAll("\\s+","")+obj.getId()+"' >  "+obj.getTitle()+"</a></span>";
	 	else
	 	{
	 		linkParts = obj.getLink().split("/");
	 		if(obj.getController().equalsIgnoreCase(controller) && linkParts[linkParts.length-1].trim().equalsIgnoreCase(action) )
	 			menuTreeStr=menuTreeStr+"<li class='leaflevel active'><span class=\"file\"><a href='"+ctx+"/"+obj.getModule().toLowerCase()+"/"+obj.getLink()+"'>"+obj.getTitle()+"</a></span>";
			else
				menuTreeStr=menuTreeStr+"<li class='leaflevel'><span class=\"file\"><a href='"+ctx+"/"+obj.getModule().toLowerCase()+"/"+obj.getLink()+"'>"+obj.getTitle()+"</a></span>";
	 	}
	 	prepareSideMenu(currentMenuId,items,obj,ctx,controller, action);
		menuTreeStr=menuTreeStr+"</li>";
	 }
	 if(list.size()>0)
	 {
		 menuTreeStr=menuTreeStr+"</ul>";
	 }
	 return menuTreeStr;  
}
%>
<div id="main-sidebar" class="sidebar sidebar-inverse">
	<div class="sidebar-item">
	    <div class="media profile">
	        <div class="media-thumb media-left thumb-bordereb"> <a class="img-shadow" href="javascript:void(0);"><img class="thumb" src="${pageContext.request.contextPath}/assets/img/demo/demo-avatar9606.jpg"></a> </div>
	        <div class="media-body">
	            <h5 class="media-heading">Welcome, <s:text name="#session.LOGGEDUSER.getUser().getUserName()" /> 
	            <%-- <small>as Administrator</small> --%>
	            </h5>
	            <!-- <p class="data">Last Access:<br/></p> -->
	            <%-- <p class="data">Last Access:<br><s:text name="#session.getLastAccessedTime()" /></p> --%>
	        </div>
	    </div>
	</div>
	<!-- // sidebar item - profile -->
	<% 
	//out.print("<h1>"+parent+"</h1>");
	//if(oModule!=null)
		//out.print("<h1 style='color:red;'>module not null</h1>");
	//else
		//out.print("<h1 style='color:red;'>module is null</h1>");
	//out.print("<h1 style='color:red;'>"+action+"</h1>");
	if(menus.size()>0) {
		out.println(prepareSideMenu(parentId,menus,new Menu(),ctx,controller,action)); menuTreeStr="";
	} 
	%>
	<!-- // sidebar menu -->
	<div class="sidebar-item"></div>
	<!-- // sidebar item --> 
</div>
<!-- // sidebar -->
<script>
$(document).ready(function(){
	if($("#mainSideMenu").length>0)
		$("#mainSideMenu").treeview();
});
</script>
<%@ page import="net.aditri.web.utility.StringHelper,com.prp.models.SessionObject,com.app.module.Module"%>
<%@ page import="java.util.Map,java.util.HashMap"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div id="header-container">
	<div id="header">
	    <div class="navbar navbar-inverse navbar-fixed-top">
	        <div class="navbar-inner">
	            <div class="container-fluid">
	                <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
	                 <!-- <a class="brand" href="javascript:void(0);"><img src="assets/img/demo/logo-brand.png"></a> -->
	                 <div class="nav-collapse collapse">
						<ul class="nav user-menu visible-desktop">
	                         <li><a class="btn-glyph  fontello-icon-users-1 tip-bc" href="javascript:void(0);" title="Logged in users"><span class="badge badge-important"><%= SessionObject.getActivesessions().size() %></span></a></li>
	                    </ul>
	                    <ul class="nav">
	                    <%
	                    String currentURL = null;
	                    if( request.getAttribute("javax.servlet.forward.request_uri") != null ){
	                        currentURL = (String)request.getAttribute("javax.servlet.forward.request_uri");
	                    }
						String[] urlParts = currentURL.split("/");
	                    SessionObject sessionObj = (SessionObject)session.getAttribute("LOGGEDUSER");
	                    if(sessionObj.getModules()!=null)
	                    {
	                    	for(Map.Entry oModule:sessionObj.getModules().entrySet())
	                    	{
	                    	%>
	                    		<li class="<%=java.util.Arrays.binarySearch(urlParts, oModule.getKey().toString().toLowerCase())>0?"active":""%>">
	                    		<a href="<%=request.getContextPath()+"/"+oModule.getKey().toString().toLowerCase()+"/"+((Module)oModule.getValue()).getDefaultLink()%>">
	                    			<span class="<%=((Module)oModule.getValue()).getIcon() %>"></span>
	                    			<%=oModule.getKey().toString()%>
	                    		</a>
	                    		</li>
	                    	<%
	                    	}
	                    }
	                    %>
	                    </ul>
	                    
	                </div>
	            </div>
	        </div>
	    </div>
	    <!-- // navbar -->
	    <div class="header-drawer">
	        <div class="mobile-nav text-center visible-phone"> <a href="javascript:void(0);" class="mobile-btn" data-toggle="collapse" data-target=".sidebar"><i class="aweso-icon-chevron-down"></i> Components</a> </div>
	        <!-- // Resposive navigation -->
	        <div class="breadcrumbs-nav hidden-phone">
	            <ul id="breadcrumbs" class="breadcrumb">
	                <li><a href="javascript:void(0);"><i class="fontello-icon-home f12"></i> <%=StringHelper.capitalizeFirstLetter(urlParts[2])%> </a> <span class="divider">/</span></li>
	            </ul>
	        </div>
	        <!-- // breadcrumbs --> 
	    </div>
	    <!-- // drawer --> 
    </div>
</div>
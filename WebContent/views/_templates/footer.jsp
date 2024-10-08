<%@ taglib prefix="s" uri="/struts-tags" %>
<footer id="footer-fix">
	<div id="footer-sidebar" class="footer-sidebar">
	    <div class="navbar">
	        <div class="btn-toolbar"> <a class="btn btn-glyph btn-link" href="javascript:void(0);"><i class="fontello-icon-up-open-1"></i></a> </div>
	    </div>
	</div>
	<!-- // footer sidebar -->
	
	<div id="footer-content" class="footer-content">
	    <div class="navbar navbar-inverse">
	        <div class="navbar-inner">
	            <ul class="nav pull-left">
	                <li class="divider-vertical hidden-phone"></li>
	                <li><a id="btnToggleSidebar" class="btn-glyph fontello-icon-resize-full-2 tip hidden-phone" href="javascript:void(0);" title="show hide sidebar"></a></li>
	                <!-- <li class="divider-vertical hidden-phone"></li> -->
	                <!-- <li><a id="btnChangeSidebar" class="btn-glyph fontello-icon-login tip hidden-phone" href="javascript:void(0);" title="change sidebar position"></a></li> -->
	                <!-- <li class="divider-vertical"></li> -->
	                <!-- <li><a id="btnChangeSidebarColor" class="btn-glyph fontello-icon-palette tip" href="javascript:void(0);" title="change sidebar color"></a></li> -->
	                <li class="divider-vertical"></li>
	                <li><a class="btn-glyph fontello-icon-cw" href="javascript:void(0);" onclick="location.reload()"></a></li>
	                <li class="divider-vertical"></li>
	                <li><a class="fontello-icon-home-3" href="${pageContext.request.contextPath}/home"></a></li>
	                <li class="divider-vertical"></li>
	            </ul>
	            <ul class="nav pull-right">
	                <li class="divider-vertical"></li>
	                <li><a id="btnHelp" class="btn-glyph fontello-icon-help-2 tip" href="javascript:void(0);" title="help to page"></a></li>
	                <li class="divider-vertical"></li>
	                <li><a id="btnSetting" class="btn-glyph fontello-icon-cog-4 tip" href="javascript:void(0);" title="settings app"></a></li>
	                <li class="divider-vertical"></li>
	                <li><a id="btnLogout" class="btn-glyph fontello-icon-logout-1 tip" href="${pageContext.request.contextPath}/logout" title="logout"></a></li>
	                <li class="divider-vertical"></li>
	                <li><a id="btnScrollup" class="scrollup btn-glyph fontello-icon-up-open-1" href="javascript:void(0);"><span class="hidden-phone">Scroll</span></a></li>
	            </ul>
	        </div>
	    </div>
	</div>
	<!-- // footer content --> 
	    
</footer>
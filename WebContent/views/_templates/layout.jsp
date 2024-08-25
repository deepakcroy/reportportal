<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>  
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if IE 9 ]>    <html class="ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html>
<!--<![endif]-->
<head>
<meta charset="UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le styles -->

<link href="${pageContext.request.contextPath}/assets/css/lib/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/lib/bootstrap-responsive.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/boo-extension.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/boo.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/boo-coloring.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/boo-utility.css" rel="stylesheet">

<link href="${pageContext.request.contextPath}/assets/plugins/treeview/jquery.treeview.css" rel="stylesheet">
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <script src="${pageContext.request.contextPath}/assets/plugins/selectivizr/selectivizr-min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/plugins/flot/excanvas.min.js"></script>
<![endif]-->

<!-- Le fav and touch icons -->
<link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/ico/favicon.png">
<link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/assets/ico/apple-touch-icon-144-precomposed.html">
<link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/assets/ico/apple-touch-icon-114-precomposed.html">
<link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/assets/ico/apple-touch-icon-72-precomposed.html">
<link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/ico/apple-touch-icon-57-precomposed.html">

<script src="${pageContext.request.contextPath}/assets/js/lib/jquery.js"></script> <!-- 1.8.3 -->
<script>
var _context='${pageContext.request.contextPath}';
</script>
</head>

<body class="sidebar-left ">
<div class="page-container">
	<tiles:insertAttribute name="header" />
    <!-- // header-container -->
    
    <div id="main-container">
    	<tiles:insertAttribute name="sidebar" />
        
        <div id="main-content" class="main-content container-fluid">
            <div class="row-fluid page-head">
                <h2 class="page-title"><i class=" fontello-icon-print"></i><small style='color:red;'> <s:property value="getText('main.title.part1')" /></small> <small><s:property value="getText('main.title.part2')" /></small> </h2>
                <p class="pagedesc" style="margin-left:52px;margin-top:0px;font-weight: bold;font-size: 11px;"> <s:property value="getText('main.subtitle')" /></p>
                <div class="page-bar">
                    <div class="btn-toolbar"> </div>
                </div>
                
            </div>
            <!-- // page head -->
            
            <div id="page-content" class="page-content">
                <section>
                    <div class="row-fluid margin-top20">
                        <div class="span12">
                            <div class="row-fluid">
                             
                             <tiles:insertAttribute name="body" />
							   

                            </div>
                        </div>
                        <!-- // column --> 
                    </div>
                    <!-- // Example row --> 
                </section>
            </div>
            <!-- // page content --> 
            
        </div>
        <!-- // main-content --> 
        
    </div>
    <!-- // main-container  -->
    <tiles:insertAttribute name="footer" />
    
    <!-- // footer-fix  --> 
    
</div>
<!-- // page-container  --> 

<!-- Le javascript --> 
<!-- Placed at the end of the document so the pages load faster --> 


<script src="${pageContext.request.contextPath}/assets/js/lib/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/lib/jquery.cookie.js"></script> 
<script src="${pageContext.request.contextPath}/assets/js/lib/jquery.date.min.js"></script> 
<script src="${pageContext.request.contextPath}/assets/js/lib/jquery.mousewheel.js"></script> 
<script src="${pageContext.request.contextPath}/assets/js/lib/jquery.load-image.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/lib/bootstrap/bootstrap.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/js/lib/jquery.form.min.js"></script> 

<!-- Plugins Bootstrap -->
<script src="${pageContext.request.contextPath}/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script> 
<script src="${pageContext.request.contextPath}/assets/plugins/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/plugins/bootstrap-daterangepicker/js/bootstrap-daterangepicker.js"></script> 
<script src="${pageContext.request.contextPath}/assets/plugins/bootstrap-toggle-button/js/bootstrap-toggle-button.js"></script> 

<script src="${pageContext.request.contextPath}/assets/plugins/bootstrap-select/bootstrap-select.js"></script> 
<script src="${pageContext.request.contextPath}/assets/plugins/bootstrap-bootbox/bootbox.min.js"></script> 
<script src="${pageContext.request.contextPath}/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"></script> 
<script src="${pageContext.request.contextPath}/assets/plugins/bootstrap-modal/js/bootstrap-modal.js"></script>



<!-- Plugins Custom - System --> 
<script src="${pageContext.request.contextPath}/assets/plugins/pl-system/nicescroll/jquery.nicescroll.min.js"></script> 
<script src="${pageContext.request.contextPath}/assets/plugins/pl-system/xbreadcrumbs/xbreadcrumbs.js"></script> 



<!-- Plugins Tables --> 
<script src="${pageContext.request.contextPath}/assets/plugins/pl-table/datatables/media/js/jquery.dataTables.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/pl-table/datatables/plugin/jquery.dataTables.fnReloadAjax.js"></script>
<%-- <script src="${pageContext.request.contextPath}/assets/plugins/pl-table/datatables/plugin/jquery.dataTables.plugins.js"></script> 
<script src="${pageContext.request.contextPath}/assets/plugins/pl-table/datatables/plugin/jquery.dataTables.columnFilter.js"></script>  --%>


<%-- <script src="${pageContext.request.contextPath}/assets/plugins/pl-system-info/qtip2/dist/jquery.qtip.min.js"></script> --%> 
<script src="${pageContext.request.contextPath}/assets/plugins/pl-system-info/gritter/js/jquery.gritter.min.js"></script> 
<%-- <script src="${pageContext.request.contextPath}/assets/plugins/pl-system-info/notyfy/jquery.notyfy.js"></script> --%>
<script src="${pageContext.request.contextPath}/assets/plugins/pl-form/uniform/jquery.uniform.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/plugins/treeview/jquery.treeview.js" ></script>

<script src="${pageContext.request.contextPath}/assets/plugins/cb-ui-helper/cb.ui.helper.js"></script> 
<!-- main js --> 
<script src="${pageContext.request.contextPath}/assets/js/core.js"></script> 
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>   

</body>
</html>
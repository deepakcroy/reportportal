<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!--CT-LOGIN-PAGE-->
<!DOCTYPE html>
<%@taglib uri="/struts-tags" prefix="s"%>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if IE 9 ]>    <html class="ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html>
<!--<![endif]-->
<head>
<meta charset="utf-8">
<title>Login</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="description" content="">
<meta name="author" content="">

<!-- Custom styles -->
<style type="text/css">
.signin-content {
  max-width: 360px;
  margin: 0 auto 20px;
}
</style>
<link href="${pageContext.request.contextPath}/assets/css/lib/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/lib/bootstrap-responsive.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/boo.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/fonts/fontello/css/fontello.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/boo-coloring.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/boo-utility.css" rel="stylesheet">
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <script src="${pageContext.request.contextPath}/assets/plugins/selectivizr/selectivizr-min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/plugins/flot/excanvas.min.js"></script>
<![endif]-->
<link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/ico/favicon.png">
<link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/assets/ico/apple-touch-icon-144-precomposed.html">
<link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/assets/ico/apple-touch-icon-114-precomposed.html">
<link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/assets/ico/apple-touch-icon-72-precomposed.html">
<link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/ico/apple-touch-icon-57-precomposed.html">
</head>

<body class="signin signin-vertical">
<div class="page-container ct-signin-page">
    <div id="header-container">
        <div id="header">
            <div class="navbar-inverse navbar-fixed-top">
                <div class="navbar-inner">
                    <div class="container"> </div>
                </div>
            </div>
            <!-- // navbar -->
            
            <div class="header-drawer" style="height:3px"> </div>
            <!-- // breadcrumbs --> 
        </div>
        <!-- // drawer --> 
    </div>
    <!-- // header-container -->
    
    <div id="main-container">
        <div id="main-content" class="main-content container">
            <div class="signin-content">
                
                <div class="well well-black well-impressed">
                    <div class="tab-content overflow">
                        <div class="tab-pane fade in active" id="login">
                            <h3 class="no-margin-top"><i class="fontello-icon-user-4"></i> Sign in</h3>
                            <form class="form-tied margin-00" method="post" action="login" name="login_form">
                                <fieldset>
                                    <legend class="two"><span>&nbsp;</span></legend>
                                    <ul>
                                        <li>
                                            <input id="user" class="input-block-level" type="text" name="userName" placeholder="your ID" value="">
                                        </li>
                                        <li>
                                            <input id="password" class="input-block-level" type="password" name="password" placeholder="password" value="">
                                        </li>
                                        <li>
                                        	<div class="error" style="color:red; font-size:12px;margin-top:10px;"><s:actionerror escape="false" /></div>
                                        </li>
                                    </ul>
                                    <button type="submit" class="btn btn-yellow btn-block btn-large">SIGN IN</button>
                                    <hr class="margin-xm">
                                    <label class="checkbox pull-left">
                                        <input id="remember" class="checkbox" type="checkbox">
                                        Remember me </label>
                                    <a href="#forgot" class="pull-right link" data-toggle="tab">Forgot Password?</a>
                                </fieldset>
                            </form>
                            <!-- // form --> 
                            
                        </div>
                        <!-- // Tab Login -->
                        
                        <div class="tab-pane fade" id="forgot">
                            <h3 class="no-margin-top">Forgot your password?</h3>
                            <form class="margin-00" method="post" action="/" name="forgot_password">
                                <p class="note">Enter your e-mail address, we will send you an e-mail code for password reset.</p>
                                <input id="email" class="input-block-level" type="email" name="id_email_forgot" placeholder="Email">
                                <p class="text-center">and</p>
                                <input id="email" class="input-block-level" type="tel" name="id_phone_forgot" placeholder="Phone number">
                                <hr class="margin-xm">
                                <button type="submit" class="btn btn-yellow">Submit</button>
                                <p>Have you remembered? <a href="#login" class="pull-right link" data-toggle="tab">Try to log in again.</a></p>
                            </form>
                            <!-- // form --> 
                            
                        </div>
                        <!-- // Tab Forgot -->
                        
                        <div class="tab-pane fade" id="register">
                            <h3 class="no-margin-top"><i class="fontello-icon-users"></i> New User Registration</h3>
                            <form class="form-tied margin-00" method="post" action="" name="login_form">
                                <fieldset>
                                    <legend class="two"><span>Account information</span></legend>
                                    <ul>
                                        <li>
                                            <input id="idLogin" class="input-block-level" type="text" name="id_name" placeholder="name">
                                        </li>
                                        <li>
                                            <input id="idLogin" class="input-block-level" type="text" name="id_username" placeholder="username">
                                        </li>
                                        <li>
                                            <input id="idLogin" class="input-block-level" type="text" name="id_email_address" placeholder="email address">
                                        </li>
                                    </ul>
                                </fieldset>
                                <fieldset>
                                    <legend class="two"><span>Password</span></legend>
                                    <ul>
                                        <li>
                                            <input id="idPassw" class="input-block-level" type="password" name="password" placeholder="password">
                                        </li>
                                        <li>
                                            <input id="idPassw" class="input-block-level" type="password" name="confirm_password" placeholder="confirm password">
                                        </li>
                                    </ul>
                                    <button type="submit" class="btn btn-green btn-block btn-large">REGISTER</button>
                                    <hr class="margin-xm">
                                    <p>Have you remembered? <a href="#login" class="pull-right link" data-toggle="tab">Try to log in again.</a></p>
                                </fieldset>
                            </form>
                            <!-- // form --> 
                            
                        </div>
                        <!-- // Tab Forgot --> 
                        
                    </div>
                </div>
                <!-- // Well-Black --> 
                
                
                <div class="web-description">
                    <h5>Copyright &copy; 2020 www.aditri.net</h5>
                    <p>All rights reserved.</p>
                </div>
                
                
            </div>
            <!-- // sign-content -->
            
            
            
        </div>
        <!-- // main-content --> 
        
    </div>
    <!-- // main-container  --> 
    
</div>
<!-- // page-container --> 

<!-- Le javascript --> 
<!-- Placed at the end of the document so the pages load faster --> 
<script src="${pageContext.request.contextPath}/assets/js/lib/jquery.js"></script> 
<script src="${pageContext.request.contextPath}/assets/js/lib/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/lib/jquery.cookie.js"></script> 
<script src="${pageContext.request.contextPath}/assets/js/lib/bootstrap/bootstrap.js"></script> 
</body>
</html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="net.aditri.web.utility.EnumHelper"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<s:form action="saveOrUpdate" cssClass="form-horizontal">
	<s:push value="user">
		<div class="row-fluid">
	    <div class="span12 form-dark">
	        <fieldset>
	        	<%-- <s:actionerror escape="false" /> --%>
	          	<s:hidden name="id" />
	          	<s:hidden name="createdBy" />
	          	<%-- <s:hidden name="createdAt" /> --%>
	          	<s:hidden name="modifiedBy" />
	          	<%-- <s:hidden name="modifiedAt" /> --%>
	          	
	            <ul class="form-list label-left list-bordered dotted">
	                <li class="section-form">
	                    <h4><i class="fontello-icon-user-add"></i><s:property value="getText('app.password')" /></h4>
	                </li>
	                <!-- // section form divider -->
	                
	                
	                <!-- // form item -->
	                
	                <li class="control-group">
	                    <label for="userName" class="control-label"><span class="required"><s:property value="getText('label.user.name')" />*</span></label>
	                    <div class="controls">
	                        <input id="userName" class="span11" name="userName" value="${user.userName}" type="text" >
	                    </div>
	                </li>
	                <!-- // form item -->
	                
	                <li class="control-group">
	                    <label for="password" class="control-label"><s:property value="getText('label.user.pwd')" /> <span class="required">*</span></label>
	                    <div class="controls">
	                        <input id="password" class="span11" name="password" value="${user.password}" type="password" >
	                    </div>
	                </li>
	                <!-- // form item -->
	                
	                <li class="control-group">
	                    <label for="conPassword" class="control-label"><s:property value="getText('label.user.conPwd')" /></label>
	                    <div class="controls">
	                        <input id="conPassword" class="span11" name="conPassword" value="" type="password" >
	                    </div>
	                </li>
	                <!-- // form item -->
	                <li class="control-group">
	                    <label for="status" class="control-label"><s:property value="getText('label.status')" /> <span class="required">*</span></label>
	                    <div class="controls">
	                    	<select id="status" class="span11" name="status">
                           	<c:forEach var="item" items="${user.status.values()}">
						   	<option value="${item.getName()}" ${item.getName()==user.status?'selected="selected"':'' } >${item.getName()}</option>
						    </c:forEach>
                           </select>
	                    </div>
	                </li>
	                <!-- // form item -->
	                
	                
	            </ul>
	        </fieldset>
	        </div>
	    </div>
    </s:push>
</s:form>
</body>
</html>
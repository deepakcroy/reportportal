<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aditri.web.utility.EnumHelper"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<s:form action="saveOrUpdate" cssClass="form-horizontal">
	<s:push value="group">
		<div class="row-fluid">
	    <div class="span12 form-dark">
	        <fieldset>
	          	<s:hidden name="id" />
	          	<s:hidden name="createdBy" />
	          	<s:hidden name="modifiedBy" />
	          	
	            <ul class="form-list label-left list-bordered dotted">
	                <li class="section-form">
	                    <h4><i class="fontello-icon-user-add"></i><s:property value="getText('label.group.form.header')" /></h4>
	                </li>
	                <!-- // section form divider -->
	                
	                
	                <!-- // form item -->
	                <li class="control-group">
	                    <label for="groupName" class="control-label"><span class="required"><s:property value="getText('label.group.groupname')" />*</span></label>
	                    <div class="controls">
	                        <input id="groupName" class="span11" name="groupName" value="${group.groupName}" type="text" >
	                    </div>
	                </li>
	                <!-- // form item -->
	                
	                <li class="control-group">
	                    <label for="description" class="control-label"><s:property value="getText('label.group.description')" /> <span class="required">*</span></label>
	                    <div class="controls">
	                        <input id="description" class="span11" name="description" value="${group.description}" type="text" >
	                    </div>
	                </li>
	                <!-- // form item -->
	                
	                <li class="control-group">
	                    <label for="status" class="control-label"><s:property value="getText('label.status')" /> <span class="required">*</span></label>
	                    <div class="controls">
	                    	<select id="status" class="span11" name="status">
                           	<c:forEach var="item" items="${group.status.values()}">
						   	<option value="${item.getName()}" ${item.getName()==group.status?'selected="selected"':'' } >${item.getName()}</option>
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
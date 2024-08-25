<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aditri.ui.common.Enum"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="tabbable tabs-top">
<ul class="nav nav-tabs">
    <li class="active"><a href="#anet-property-generic" data-toggle="tab"> <i class="fontello-icon-align-justify"></i> General</a></li>
    <li class=""><a href="#anet-property-datasource" data-toggle="tab"> <i class="fontello-icon-database"></i> Data Source</a></li>
    <li><a href="#anet-property-event" data-toggle="tab"> <i class="fontello-icon-harbor"></i> Event</a></li>
</ul>
<div class="tab-content">
	<div id="anet-property-generic" class="tab-pane active">
		<table class="anet-property-generic table boo-table table-striped table-content table-hover table-bordered">
			<caption>Generic Properties</caption>
			<thead>
				<tr>
					<td class="anet-property-key">Property</td>
					<td class="anet-property-value">Value</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Control Type</td>
					<td>
					<c:set var="controlTypes" value="<%=net.aditri.ui.common.Enum.ControlType.values()%>"/>
					<select class="anet-property-control-type span12" data-id='anet-property-control-type'>
						<option value="" selected>Select Control Type</option>
			        	<c:forEach items="${controlTypes}"  var="item">
				   		<option value="${item.getName()}"  >${item.getName()}</option>
				    	</c:forEach>
			        </select>
					</td>
				</tr>
				<tr>
					<td>Name*</td>
					<td><input class="anet-property-id" type="text" data-id='anet-property-id' /></td>
				</tr>
				<tr>
					<td>Param*</td>
					<td><input class="anet-property-name" type="text" data-id='anet-property-name' /></td>
				</tr>
				<tr>
					<td>Label*</td>
					<td><input class="anet-property-label" type="text" data-id='anet-property-label' value="Label Name" /></td>
				</tr>
				<tr>
					<td>Label Style</td>
					<td><input class="anet-property-label-style" type="text" data-id='anet-property-label-style' /></td>
				</tr>
				<tr>
					<td>Control Style</td>
					<td><input class="anet-property-control-style" type="text" data-id='anet-property-control-style' /></td>
				</tr>
			</tbody>
			</table>
	</div>
	<div id="anet-property-datasource" class="tab-pane">
		<table class="anet-property-datasource table boo-table table-striped table-content table-hover table-bordered">
			<caption>DataSource Properties</caption>
			<thead>
				<tr>
					<td class="anet-property-key"></td>
					<td class="anet-property-value"></td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Type</td>
					<td><select class="anet-property-ds-cat" data-id='anet-property-ds-cat' ><option value='' selected='selected'>None</option><option value='new'>New</option><option value='existing'>Existing</option></select></td>
				</tr>
				<tr class='anet-hidden'>
					<td>Data Source</td>
					<td><input class="anet-property-ds-id anet-property-ds-lookup" data-id='anet-property-ds-id' type="text" readonly="readonly" /><div class="anet-property-ds-lookup-btn"></div></td>
				</tr>
				<tr class='anet-hidden'>
					<td>Data Source Type</td>
					<td>
					<c:set var="dsTypes" value="<%=net.aditri.ui.common.Enum.SourceType.values()%>"/>
					<select class="anet-property-ds-type span12" data-id='anet-property-ds-type'>
						<option value="" selected>Select Source Type</option>
			        	<c:forEach items="${dsTypes}"  var="item">
				   		<option value="${item.getName()}"  >${item.getName()}</option>
				    	</c:forEach>
			        </select>
					</td>
				</tr>
				
			</tbody>
			</table>
	</div>
	<div id="anet-property-event" class="tab-pane">
		<table class="anet-property-event table boo-table table-striped table-content table-hover table-bordered">
			<caption>Event Properties</caption>
			<thead>
				<tr>
					<td class="anet-property-key"></td>
					<td class="anet-property-value"></td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>OnClick</td>
					<td><input class="anet-property-event-click" data-id="anet-property-event-click" type="text" /></td>
				</tr>
				<tr>
					<td>OnChange</td>
					<td><input class="anet-property-event-change" data-id="anet-property-event-change" type="text" value="Label Name" /></td>
				</tr>
				
			</tbody>
			</table>
	</div>
</div>
</div>


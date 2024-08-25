<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List,java.util.ArrayList"%>
<%@ page import="net.aditri.web.utility.EnumHelper"%>
<%@ page import="com.prp.models.Menu"%>
<s:push value="menu">
<form id="qForm_${menu.module}" class="form-horizontal " role="form" action="saveorupdate" method="post">
    <div class="row-fluid">
       <div class="span12 form-dark">
           <fieldset>
               <s:hidden name="id" />
               <s:hidden name="createdBy" />
               <s:hidden name="modifiedBy" />
               <ul class="form-list label-left list-bordered dotted">
               	   <li class="section-form">
                       <h4><s:property value="getText('label.menu.heading')" /></h4>
                   </li>
                    <!-- // form item -->
                   <li class="control-group">
                       <label for="controller" class="control-label"><s:property value="getText('label.menu.controller')" /></label>
                       <div class="controls">
                      		<input id="module" value="${menu.module}" name="module"  type="hidden" readonly />
                       		<select id="controller" class="span11" name="controller">
                           	   <c:forEach var="item" items="${controllers}">
							   	<option value="${item}" data-module="${item}" data-controller="${item}" ${item==menu.controller?'selected="selected"':'' } >${item}</option>
							   </c:forEach>
                           </select>
                       </div>
                   </li>
                   <!-- // section form divider -->
               	   <li class="control-group">
                       <label for="parentId" class="control-label"><s:property value="getText('label.menu.parent')" /> <span class="required">*</span></label>
                       <div class="controls">
                           <select id="parentId" class="span11" name="parentId" data-module="${menu.module}" onchange="onMenuChange(this)" >
                           	   <c:forEach var="item" items="${menuList}">
							   	<option value="${item.id}" data-module="${item.module}" data-controller="${item.controller}" data-link="${item.link}" ${item.id==menu.getParentId()?'selected="selected"':'' } >${item.title}</option>
							   </c:forEach>
                           </select>
                       </div>
                   </li>
                   <!-- // form item -->
                   <li class="control-group">
                       <label for="position" class="control-label"><s:property value="getText('label.menu.position')" /> <span class="required">*</span></label>
                       <div class="controls">
                           <select id="position" class="span11" name="position">
                           	   <c:forEach var="item" items="${menuList}">
                           	    <option value="${item.id}" ${item.id==menu.getPosition()?'selected="selected"':'' } >${item.title}</option>
							   </c:forEach>
                           </select>
                       </div>
                   </li>
                   <!-- // form item -->
                   <li class="control-group">
                       <label for="title" class="control-label"><s:property value="getText('label.menu.title')" /></label>
                       <div class="controls">
                           <input id="title" class="span11" value="${menu.title}" name="title" type="text"/>
                       </div>
                   </li>
                   <!-- // form item -->
                   <li class="control-group">
                       <label for="link" class="control-label"><s:property value="getText('label.menu.link')" /> <span class="required">*</span></label>
                       <div class="controls">
                           <input id="link" class="span11" name="link" value="${(menu.link==null)?'show#':menu.link}" type="text">
                           <i class="fontello-icon-search-5"></i>
                       </div>
                   </li>
                   <!-- // form item -->
                   <li class="control-group">
                       <label for="leafLevel" class="control-label"><s:property value="getText('label.menu.leaflevel')" /></label>
                       <div class="controls">
                           <div class="danger-tgbtn" style="">
                                <input type="checkbox" id="leafLevel" name="leafLevel" ${menu.isLeafLevel()?'checked="checked"':'' }>
                            </div>
                       </div>
                   </li>
                   <!-- // form item -->
                   <li class="control-group">
                       <label for="status" class="control-label"><s:property value="getText('label.status')" /> <span class="required">*</span></label>
                       <div class="controls">
                           <select id="status" class="span11" name="status">
                           	<c:forEach var="item" items="${menu.status.values()}">
						   	<option value="${item.getName()}" ${item.getName()==menu.status?'selected="selected"':'' } >${item.getName()}</option>
						    </c:forEach>
                           </select>
                       </div>
                   </li>
                   <!-- // form item -->
               </ul>
           </fieldset>
           <!-- // fieldset Input -->
           <div style="text-align:right;padding-right:40px;">
           		<button type="button" onclick="saveCurrentMenuItem(this)" data-module='${menu.module}' class="btn btn-blue"><s:property value="getText('button.menu.save')" /></button>
           		<button type="button" class="btn btn-default" onclick="$('#qForm_${menu.module}').html('');"><s:property value="getText('button.cancel')" /></button>
           </div>
       </div>
   </div>
</form>
</s:push>	


<script>
function onMenuChange(obj)
{
		var opt = $(obj).find("option:selected");
		var frm = "#qForm_"+$(obj).attr("data-module");
		if($(opt).val()>0)
		{
			$(frm+" #controller").val($(opt).attr("data-controller"));
			$(frm+" #module").val($(opt).attr("data-module"));
			$(frm+" #link").val($(opt).attr("data-link"));
		}
		else
		{
			$(frm+" #controller").val("");
			$(frm+" #module").val("");
			$(frm+" #link").val("show#");
		}
}

$(document).ready(function(){
	$("#qForm_${moduleName} .danger-tgbtn").toggleButtons({
		style: {
				enabled: "warning",
				disabled: "danger"
		},
		label: {
			enabled: "Yes",
			disabled: "No"
		}
	});
	/* $("#qForm_${moduleName} .danger-tgbtn").on('click', function () {
       alert($(this).toggleButtons('toggleState'));
      
    }); */
});
</script>
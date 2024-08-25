<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List,java.util.ArrayList"%>
<%@ page import="net.aditri.web.utility.EnumHelper"%>
<script src="${pageContext.request.contextPath}/assets/plugins/bootstrap-toggle-button/js/bootstrap-toggle-button.js"></script>
<div class="widget widget-simple">
<s:form id="qForm" cssClass="form-horizontal" role="form" action="savereportdbcfg" method="post">
<s:push value="DBPool">

<div class="row-fluid">
    <div class="span12 form-dark">
        <fieldset>
            <legend>Database configuration creation</legend>
            <ul class="form-list label-left list-bordered dotted">
                
                <li class="control-group">
                    <label for="Name" class="control-label">Name</label>
                    <div class="controls">
                        <input id="Name" class="span6" name="Name" type="text" value="${DBPool.dbConfig.name}">
                        <input id="hdnConName" class="span6" name="hdnConName" type="hidden" value="${DBPool.dbConfig.name}">
                    </div>
                </li>
                <!-- // form item -->
                
                <li class="control-group">
                    <label for="DBServer" class="control-label">DB Server <span class="required">*</span></label>
                    <div class="controls">
	                    <select id="DBServer" class="span11" name="DBServer">
                          	<c:forEach var="item" items="${DBPool.dbConfig.dbServer.values()}">
					   		<option value="${item.getName()}" ${item.getName()==DBPool.dbConfig.dbServer?'selected="selected"':'' } >${item.getName()}</option>
					    	</c:forEach>
                        </select>
                    </div>
                </li>
                <!-- // form item -->
                
                <li class="control-group">
                    <label for="ConString" class="control-label">Connection String <span class="required">*</span></label>
                    <div class="controls">
                    	<textarea id="ConString" name="ConString" class="input-block-level" rows="3">${DBPool.dbConfig.conString}</textarea>
                    </div>
                </li>
                <!-- // form item -->
                
                <li class="control-group">
                    <label for="accountSuffix" class="control-label">Driver</label>
                    <div class="controls">
                        <input id="Driver" class="span6" name="Driver" value="${DBPool.dbConfig.driver}" type="text">
                    </div>
                </li>
                <!-- // form item -->
                
                <li class="control-group">
                    <label for="User" class="control-label">User</label>
                    <div class="controls">
                        <input id="User" class="span11" name="User" value="${DBPool.dbConfig.user}" type="text">
                    </div>
                </li>
                <!-- // form item -->
                 <li class="control-group">
                    <label for="PWD" class="control-label">Password </label>
                    <div class="controls">
                        <input id="PWD" class="span6" name="PWD" value="${DBPool.dbConfig.pwd}" type="password">
                    </div>
                </li>
                <!-- // form item -->
                <li class="control-group">
                    <label for="IsEncrypted" class="control-label">Encrypt</label>
                    <div class="controls">
                        <input type='hidden' id="IsEncrypted" name='IsEncrypted' value='${DBPool.dbConfig.isPWDEncrypt()?true:false}' />
                        <div id="EncryptedHolder" class="btn-group change" data-toggle="buttons-radio" data-target="EncryptInput">
                            <button id="IsEncryptedYes" onclick="this.value=true;$('#IsEncryptedNo').val('false');$('#IsEncrypted').val('true');" type="button" class="btn ${DBPool.dbConfig.isPWDEncrypt()?'active':''}"   value="${DBPool.dbConfig.isPWDEncrypt()?true:false}">&nbsp; Yes &nbsp;</button>
                            <button id="IsEncryptedNo" onclick="this.value=true;$('#IsEncryptedYes').val('false');$('#IsEncrypted').val('false');" type="button" class="btn ${DBPool.dbConfig.isPWDEncrypt()?'':'active'}"  value="${DBPool.dbConfig.isPWDEncrypt()?false:true}">No</button>
                        </div>
                    </div>
                </li>
                <!-- // form item -->
                
               
                
            </ul>
        </fieldset>
       
        <!-- // fieldset Input -->
        <div class="form-actions">
            <button type="button" onclick="saveRptDBConfig()" class="btn btn-blue">Submit &amp; Validate</button>
            <button class="btn cancel">Cancel</button>
        </div>
    </div>
</div>
                                        
</s:push>
</s:form>
</div>
<script>
function saveRptDBConfig()
{
	var options = {
		beforeSubmit: function (arr, $form, options) {
			alert('loading...');
           },
           success: function (data) {
               alert(data);
               return false;
           },
           type: "POST"
	};
	$("#qForm").ajaxForm(options).submit();
}
</script>
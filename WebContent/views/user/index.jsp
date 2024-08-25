<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<style>
.label-positive[href], .badge-positive[href] {
    background-color: #41453F;
}
.err-key{color:red; font-weight:bold; padding:10px;}
.err-val{padding:10px}
</style>
<div class="well well-black" style="padding-bottom:40px;">
<ul class="btn-toolbar"> 
     <li><a onclick="createOrEditForm(this)" data-url="${pageContext.request.contextPath}/setup/user/saveorupdateform"  class="btn btn-green"><i class=" fontello-icon-list-add"></i> <s:property value="getText('button.create')" /> </a></li> 
     <li><a onclick="delete(this)" data-url="${pageContext.request.contextPath}/setup/user/delete" class="btn btn-red"><i class="fontello-icon-trash-1"></i> <s:property value="getText('button.delete')" /> </a> </li>
 </ul>

<div class="content">
<table id="userDataTable" class="table table-striped table-bordered table-condensed table-content table-hover boo-table">
<thead>
    <tr>
    	<th>Action</th>
        <th>Id</th>
        <th>Name</th>
        <th>Password</th>
        <th>Status</th>
        <th>Created By</th>
        <th>Created At</th>
        <th>Modified By</th>
        <th>Modified At</th>

    </tr>
</thead>
</table>
</div>
</div>

<script>
function createOrEditForm(obj)
{
	var url = $(obj).attr("data-url");
	
	var _title= "<s:property value="getText('modal.title')"/>";
	var _save= "<s:property value="getText('modal.save')"/>";
	var _close= "<s:property value="getText('modal.close')"/>";
	
	getDialogAdvance(_title,(url),_save,_close,null,null,function
	(){
		//alert(url);
	// on load complete

	},function(){
	// ok button click
		var options = {
				 type: "post",
				 url:'./user/saveorupdate',//+$("#qForm").serialize(),
			 	//target: "#responsePanel",
			    beforeSubmit: function (arr, $form, options) {
			    	$('.gritter-item-wrapper').remove();
			    },
			    complete: function(xhr, textStatus){
			    	
			    },
		        success: function(response, statusString, xhr, $form){
		        	var errorMsg = "";
		        	if(!response.isSuccess) // Check Validation
	        		{
		        		//console.log(response.fieldMessage.password);
			        	//loop through or enumarate response.fieldMessage object (HashMap)
			        	for (var key in response.fieldMessage) {
		        		  /* if (response.fieldMessage.hasOwnProperty(key)) {
		        		    //console.log(key + " -> " + response.fieldMessage[key]);
		        		  } */
			        		errorMsg +='<span class="err-key">'+key+"</span>-><span class='err-val'>"+response.fieldMessage[key]+"</span><br/>";
		        		}
	        		}
		        	
		        	$.gritter.add({
		        		title: (errorMsg!="")?'Error Occurred...!':'Operation Status...!',
		                text: (errorMsg!="")?errorMsg:response.message,//'The record has been saved or updated successfully!',
		    			sticky: (errorMsg!="")?true:false,// if you want it to fade out on its own or just sit there
		    			time: 4000, // the time you want it to be alive for before fading out
		    			position: 'bottom-right',
		    			class_name: (response.isSuccess)?'gritter-light notyfy_success':'gritter-light notyfy_error'
		    		}); 
			    },
		        error: function(response, status, err){
		        	alert(response);
			    }
		    };
			$("#saveOrUpdate").ajaxForm(options).submit();
		
	},false,false,false,function(){
		//$.gritter.removeAll();
		$('.gritter-item-wrapper').remove();
		if (typeof oTable != 'undefined')
		{
			oTable.fnStandingRedraw();
			//oTable.fnDraw();
		}
	});
}
var oTable=null;
$(document).ready(function() {
	
	 oTable = $('#userDataTable').dataTable( {
	    //'sScrollX':true,
	 	"bFilter": false,
	 	"bLengthChange": false,
		// "sDom": '<"top"ip>rt<"bottom"fl><"clear">',
		//"bInfo": false,
        //"bProcessing": true,
        "bServerSide": true,
		//"bStateSave": false,
		"sPaginationType": "full_numbers",
       // "sAjaxSource": "./userDataTable"+location.search,
        "sAjaxSource": "./user/userdatatable",
        /* "aoColumns": [
			
			null,
			{"bSortable": false},
            null,
			{"bSortable": false},
			null,
			null,
			null,
			null,
			{"bSortable": true},
			{"bSortable": true},
			null

        ], */
      //  "aaSorting": [[0, 'desc']],
		"iDisplayLength":5
    } );  
});
</script>

<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="">
<h5>Assign User to Group</h5>                   
<div class="row-fluid">
	<div class="span4">
		<div class="row-fluid">
		
            <ul class="anet-singlelist-box inline well well-nice" style="background-color:#dbdbdb;">
                <li class="listbox-left span12 grider-item">
                    <h5 class="simple-header" style="border-bottom-color: #c5c5c5;"><i class="fontello-icon-users"></i> User Groups</h5>
                    <div class="anet-linstbox-wrapper well well-small well-impressed bg-gray-light" style="margin-bottom:10px;">
                        <input class="anet-listbox-filter input-block-level" type="text" placeholder="Search filter">
                        <div class="btn-group margin-0m">
                            <button class="anet-listbox-clear btn btn-small btn-yellow" type="button"><i class="fontello-icon-filter"></i> Clear</button>
                       </div>
                       <select multiple="multiple" class="anet-listbox input-block-level" style="height:150px;">
                       	  <c:forEach var="item" items="${userGroups}">
						   	<option class="anet-option" data-value="${item.groupName}" value="${item.id}">${item.groupName}</option>
						   </c:forEach>
                       </select>
                   </div>
                   <button onclick="listGroupUser(this)" data-url="${pageContext.request.contextPath}/setup/groupuser/listgroupuser" style="width:100%;" class="anet-singlelist-box-submit btn btn-yellow btn-small pull-right">Show users belong to this group</button>
               </li>
            </ul>
            
        </div>
	</div>
    <div class="span8">
        <div class="row-fluid">
        
            <ul class="anet-duallist-box inline well well-nice">
                <li class="listbox-left span6 grider-item">
                    <h5 class="simple-header" style="border-bottom-color: #c5c5c5;"><i class="fontello-icon-users-1"></i> Users</h5>
                    <div class="anet-linstbox-from well well-small well-impressed bg-gray-light" style="margin-bottom:10px;">
                        <input class="anet-listbox-filter input-block-level" type="text" placeholder="Search filter">
                        <div class="btn-group margin-0m">
                            <button class="anet-listbox-clear btn btn-small btn-yellow" type="button"><i class="fontello-icon-filter"></i> Clear</button>
                            <button class="anet-listbox-to btn btn-small btn-boo" type="button" style="width:60px;"><i class="fontello-icon-to-end-1"></i></button>
                            <button class="anet-listbox-all btn btn-small btn-boo" type="button" style="width:35px;"><i class="fontello-icon-to-end-alt"></i></button>
                       </div>
                       <select multiple="multiple" data-item="anet-listbox" class="anet-listbox input-block-level" style="height:150px">
                           <c:forEach var="item" items="${users}">
						   	<option class="anet-option" data-value="${item.userName}" value="${item.id}"> ${item.userName}&nbsp;(${item.id})</option>
						   </c:forEach>
                       </select>
                   </div>
               </li>
               <li class="listbox-right span6  grider-item">
                   <h5 class="simple-header" style="border-bottom-color: #c5c5c5;"><i class="fontello-icon-user-add"></i> Assigned Users</h5>
                   <div class="anet-linstbox-to well well-small well-impressed bg-gray-light margin-0m">
                       <div class="margin-0m">
                           <input class="anet-listbox-filter input-block-level" type="text" placeholder="Search filter">
                           <div class="btn-group">
                               <button class="anet-listbox-all btn btn-small btn-boo" type="button" style="width:35px;" title="copy all items"><i class="fontello-icon-to-start-alt"></i></button>
                               <button class="anet-listbox-to btn btn-small btn-boo" type="button" style="width:60px;"><i class="fontello-icon-to-start-1"></i></button>
                               <button class="anet-listbox-clear btn btn-small btn-yellow" type="button"><i class="fontello-icon-filter"></i> Clear</button>
                           </div>
                       </div>
                       <select multiple="multiple" data-item="anet-listbox-proxy" class="anet-listbox-proxy input-block-level" style="height:150px">
                       </select>
                    </div>
                    <button data-url="${pageContext.request.contextPath}/setup/groupuser/saveorupdate" id="btnAssignGroup" style="width:100%;" class="anet-duallist-box-submit btn btn-green btn-small pull-right" type="submit">Assign users to selected group</button>
                </li>
            </ul>
            
        </div>
    </div>
</div>
</div>
<script>
function listGroupUser(elm)
{
	var arrUG = $("#ugView :selected");
	ugs = "";
	$.each( arrUG, function( index, obj ) {
		ugs+=obj.value+",";
	});
	ugs=ugs.trim();
	ugs=ugs.substr(0,ugs.length-1);
	if(ugs!="")
	{
		$.ajax({
            type : 'GET',
            url : $(elm).attr("data-url"),
            //async : false,
            data : "ugs="+ugs,
            beforeSend : function (){
            },
            complete : function (){
            },
            success : function (data) {
              
               //console.log(data[0].USER_ID);
               if(Array.isArray(data))
           	   {
           	    	//alert(data[0].userid);
            	   $("#allTo3").trigger('click');
            	   $.each( data, function( index, obj ) {
            		   $("#userView option[value='"+data[index].userid+"']").attr("selected","selected");
         			});
            	   $("#to4").trigger('click');
           	   }
            },
            error : function (xhr, textStatus, errorThrown) {
            }
        });
	}
}
$(function() {
	$("#btnAssignGroup").click(function(){
		$('.gritter-item-wrapper').remove();
		
		var arrUG = $("#ugView :selected");
		var arrUser = $("#userSelView option");
		if(arrUG.length==0||arrUser.length==0)
		{
			$.gritter.add({
				title: '<span><i style="color:red;" class=" fontello-icon-cancel-circle"></i>Error...!!!</span>',
		        text: 'Select atleast one user group and one user!',
				sticky: true,
				time: 4000,
				position: 'bottom-right',
				class_name: 'gritter-light notyfy_error'
			});
		}
		else
		{
			var us="";
			var ugs="";
			$.each( arrUser, function( index, obj ) {
			  //alert( index + ": " + obj.value );
				us+=obj.value+",";
			});
			us=us.trim();
			us=us.substr(0,us.length-1);
			$.each( arrUG, function( index, obj ) {
				ugs+=obj.value+",";
			});
			ugs=ugs.trim();
			ugs=ugs.substr(0,ugs.length-1);
			$.ajax({
	            type : 'POST',
	            url : $(this).attr("data-url"),
	            //async : false,
	            data : "us="+us+"&ugs="+ugs, // "ugs="+strUG+"&ris="+strRI,
	            beforeSend : function (){
	                
	            },
	            complete : function (){
	            	
	            },
	            success : function (data) {
	               alert(data.message);
	              // console.log(data);
	            },
	            error : function (xhr, textStatus, errorThrown) {
	              
	            }
	            
	        });
		}
	});
	
	
	
	
	
	
});


$(document).ready(function(){

	// dual-list-box initialize start ---------------------------------------------------------
	$(".anet-duallist-box").each(function(i){
		var dlBox= $(this);
		var lBox = dlBox.find(".anet-linstbox-from .anet-listbox").eq(0);
		var lBoxProxy = dlBox.find(".anet-linstbox-to .anet-listbox-proxy").eq(0);
		
		lBox.on('dblclick','.anet-option',function(){
			var opt = $(this);
			lBoxProxy.append(opt);
			lBoxProxy.find("option:selected").removeAttr("selected");
		});
		lBoxProxy.on('dblclick','.anet-option',function(){
			var opt = $(this);			
			lBox.append(opt);
			lBox.find("option:selected").removeAttr("selected");
		});
		
		var lBoxTo = dlBox.find(".anet-linstbox-from .anet-listbox-to").eq(0);
		var lBoxAll = dlBox.find(".anet-linstbox-from .anet-listbox-all").eq(0);
		var lBoxClear = dlBox.find(".anet-linstbox-from .anet-listbox-clear").eq(0);
		var lBoxFilter = dlBox.find(".anet-linstbox-from .anet-listbox-filter").eq(0);
		
		var lBoxProxyTo = dlBox.find(".anet-linstbox-to .anet-listbox-to").eq(0);
		var lBoxProxyAll = dlBox.find(".anet-linstbox-to .anet-listbox-all").eq(0);
		var lBoxProxyClear = dlBox.find(".anet-linstbox-to .anet-listbox-clear").eq(0);
		var lBoxProxyFilter = dlBox.find(".anet-linstbox-to .anet-listbox-filter").eq(0);
		
		lBoxTo.click(function(){
			var arrOpt = lBox.find('option:selected');
			lBoxProxy.append(arrOpt);
			lBoxProxy.find("option:selected").removeAttr("selected");
		});
		lBoxAll.click(function(){
			var arrOpt = lBox.find('option:visible');
			lBoxProxy.append(arrOpt);
			lBoxProxy.find("option:selected").removeAttr("selected");
		});
		lBoxClear.click(function(){
			lBoxFilter.val("");
			lBoxFilter.focus();
			lBox.find('option:hidden').show();
		});
		var _lBoxFilterVal="";
		var _lBoxOptVal = "";
		lBoxFilter.keyup(function(event){
			 _lBoxFilterVal=$(this).val();
			lBox.find('option').each(function(){
				_lBoxOptVal=$(this).attr("data-value");
				if (_lBoxOptVal.toLowerCase().indexOf(_lBoxFilterVal.toLowerCase())>=0) 
		        	$(this).show();
		        else
		        	$(this).hide();
			});
		});
		
		
		lBoxProxyTo.click(function(){
			var arrOpt = lBoxProxy.find('option:selected');
			lBox.append(arrOpt);
			lBox.find("option:selected").removeAttr("selected");
		});
		lBoxProxyAll.click(function(){
			var arrOpt = lBoxProxy.find('option:visible');
			lBox.append(arrOpt);
			lBox.find("option:selected").removeAttr("selected");
		});
		lBoxProxyClear.click(function(){
			lBoxProxyFilter.val("");
			lBoxProxy.focus();
			lBoxProxy.find('option:hidden').show();
		});
		var _lBoxProxyFilterVal="";
		var _lBoxProxyOptVal = "";
		lBoxProxyFilter.keyup(function(event){
			 _lBoxProxyFilterVal=$(this).val();
			lBoxProxy.find('option').each(function(){
				_lBoxProxyOptVal=$(this).attr("data-value");
				if (_lBoxProxyOptVal.toLowerCase().indexOf(_lBoxProxyFilterVal.toLowerCase())>=0) 
		        	$(this).show();
		        else
		        	$(this).hide();
			});
		});
		
	});
	// dual-list-box initialize End ---------------------------------------------------------
	
	// single-list-box initialize Start ---------------------------------------------------------
	$(".anet-singlelist-box").each(function(i){
		var slBox= $(this);
		var lBox = slBox.find(".anet-linstbox-wrapper .anet-listbox").eq(0);
		lBox.on('dblclick','.anet-option',function(){
			var opt = $(this);
			alert('ajax call....');
		});
		
		var lBoxClear = slBox.find(".anet-linstbox-wrapper .anet-listbox-clear").eq(0);
		var lBoxFilter = slBox.find(".anet-linstbox-wrapper .anet-listbox-filter").eq(0);
		
		lBoxClear.click(function(){
			lBoxFilter.val("");
			lBoxFilter.focus();
			lBox.find('option:hidden').show();
		});
		
		var _lBoxFilterVal="";
		var _lBoxOptVal = "";
		lBoxFilter.keyup(function(event){
			_lBoxFilterVal=$(this).val();
			lBox.find('option').each(function(){
				_lBoxOptVal=$(this).attr("data-value");
				if (_lBoxOptVal.toLowerCase().indexOf(_lBoxFilterVal.toLowerCase())>=0) 
		        	$(this).show();
		        else
		        	$(this).hide();
			});
		});
		
	});
	// single-list-box initialize End ---------------------------------------------------------
});

</script>   
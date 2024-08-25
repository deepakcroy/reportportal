;(function ( $ ) {
	$.fn.uiconverter = function( options ) {
		var defaults = {
            cssDir: "css",
            imgDir: "img",
            lookup:{
            	dlgLoadURL : "openlookupdialog",
            	dlgWrapper : "ReportContorlsFormWrapper",
            	rptIdHolder : "frmRCDynamic", //ID UI Element >> ????????
            	
            }
        };
        var settings = $.extend({}, defaults, options);
        
        //anet-lookup
        this.find( ".anet-lookup" ).each(function() {
            var anetLookup = $( this );
            var html=[];
            html.push(
            		"<label for='"+anetLookup.attr("id")+"'>"+anetLookup.attr("anet-label")+"</label>",
            		"<input type='text' id='"+anetLookup.attr("id")+"' name='"+anetLookup.attr("name")+"' class='anet-lookup'>",
            		"<div class='anet-lookup-btn' onclick=\"lookupt('"+settings.lookup.dlgLoadURL+"','"+anetLookup.attr("id")+"','"+settings.lookup.dlgWrapper+"','"+$('#'+settings.lookup.rptIdHolder).attr('data-val')+"')\"></div>"
            );
            anetLookup.removeAttr('id').removeAttr('name').removeAttr('anet-label').removeClass('anet-lookup').addClass('anet-row');
            anetLookup.append( html );
        });
        
        
        
        //anet-shuttle
        
        
        
        //console.log(this.html());
		/*return this.css({
			color: settings.color,
			backgroundColor: settings.backgroundColor
		});*/
        return this;
	};
}( jQuery ));
function lookupt(a,b,c,d){
	alert('test....');
}
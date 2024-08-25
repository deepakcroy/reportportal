<%@page import="com.prp.models.SessionObject"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="net.aditri.ui.report.*"%>
<script>
   
    
</script>


<div id="ReportContorlsFormWrapper">
    <div id="ReportContorlsForm">
        <form id="anetDynamicFrm" data-val="${objReportUI.getReportId()}" data-rptname="${objReportUI.getReportName()}" method="get" action="/">
           <div class="frmContent well well-nice">
               <h5 id="hRptTitle" style="display:inline-block; margin-bottom:0px;">${objReportUI.getReportHeader()}</h5>
               <span id="rptPreviewer"></span>
               <div style=" font-style:italic; font-size:11px; padding-left:10px; padding-top:5px; padding-bottom:5px; border-bottom:1px solid silver; "><div id="rptDesc" style="display:inline-block; max-width:95%; margin-left:-10px; white-space: nowrap;overflow: hidden;text-overflow:ellipsis;">${objReportUI.getReportDescription()}</div></div>
               <div id="frmContentInner" style='padding-top:5px;'>
       
       			${objReportUI.getFormBody()}
       
                </div>
               <div class="anet-datarow" id="anet-rpt-ctrls" style="padding-top:2px;">
                    <label></label>
                    <input type="submit" id="btnSubmit" onclick="showReport(event,this)"  class="anet-submit" value="Show Report" />
                    <input type="button" id="btnLoadDefault"  class="anet-submit" style="width:67px;" value="Reset" onclick="resetCurrentUI()" />
                    <input type="button" id="btnRCExportAs" onclick="exportReportAS(this)"  class="anet-submit" value="Export As" />
                    <input type="button" id="btnRCEmailAs" onclick="emailReportAS(this)"  class="anet-submit" value="Email As" />
                    <!--<input type="button" id="btnRCExportPDF"  class="anet-submit" value="Export PDF" />
                    <input type="button" id="btnRCExportExcel"  class="anet-submit" value="Export Excel" />-->
                   
                    <%-- <img id="btnJobSetting" onclick="getJobExecutions(this)" data-jobmode="false" title="Scheduling This Report" style=" display:block; float:right; cursor:pointer; width:20px;" src="${pageContext.request.contextPath}/assets/img/system/setting.png" /> --%>
                    <img id="btnJobSetting" style=" display:block; float:right; cursor:pointer; width:20px;" src="${pageContext.request.contextPath}/assets/img/system/setting.png" />
               </div>
           </div>
        </form>
        <script>
		$("#rptHolder").uiconverter({});
		$("#rptHolder").uiconverter('destroy')[0]; //destroying the method
		</script>
		
		
        <%
        ReportUI oReportUI=null;
        if (request.getAttribute("objReportUI")!=null)
        	oReportUI = (ReportUI)request.getAttribute("objReportUI");
        if(oReportUI!=null)
        {
        	if(oReportUI.getReportControls()!=null)
        	{
	        	for(ReportControl obj:oReportUI.getReportControls())
	        	{
	        		%>
	        		<script>
	                fillReportControl("<%=obj.getTempId()%>", "<%=obj.getControlType()%>", "<%=obj.getControlData()%>")
	                </script>
	        		<%
	        	}
        	}
        }
        %>
        
        
        
        
    </div>
    <div id="reportExecutionWrapper">
        <form name="reportExecutionFRM" id="reportExecutionFRM" target="_blank" action="" data-base="http://localhost:8010/advance-birt/frameset" method="post" data-skey="${sKey}" data-ukey="${uKey}" >
            <input id="hdnReportExecutionQStr" type="hidden" name="qStr" value="" />
        </form>
    </div>
    <!-- ---------------------------------- EMAIL/OTHER DIALOG FORM START --------------------------------------- -->
    <div id="dialog-form-email" title="Send Mail" data-emailas="EXCEL" style="display:none;"></div>
    <div id="jobdetail_dialog"  style="display:none;"></div>
    <div id="joblog_dialog"  style="display:none;"></div>
    
    <!-- ---------------------------------- EMAIL/OTHER DIALOG FORM END ----------------------------------------- -->
</div>

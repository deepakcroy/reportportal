package com.prp.actions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IExcelRenderOption;
import org.eclipse.birt.report.engine.api.IHTMLRenderOption;
import org.eclipse.birt.report.engine.api.IPDFRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ModelDriven;
import com.prp.models.Report;
import com.prp.models.SessionObject;
import com.prp.models.User;
import com.prp.models.WhereBuilder;

import net.aditri.dao.DataManager;
import net.aditri.ui.common.DBConfig;
import net.aditri.ui.common.DataSource;
import net.aditri.ui.common.Param;
import net.aditri.ui.common.QueryBuilder;
import net.aditri.ui.common.SQLFunction;
import net.aditri.ui.common.SQLInline;
import net.aditri.ui.common.SQLProcedure;
import net.aditri.ui.common.SQLTable;
import net.aditri.ui.common.SQLView;
import net.aditri.ui.common.Enum.ControlType;
import net.aditri.ui.common.Enum.DBServer;
import net.aditri.ui.common.Enum.FieldSelectionType;
import net.aditri.ui.common.Enum.SourceType;
import net.aditri.ui.report.ReportControl;
import net.aditri.ui.report.ReportSnippet;
import net.aditri.ui.report.ReportUI;
import net.aditri.util.IgnoreEmptyNodeList;
import net.aditri.util.XmlUtil;
import net.aditri.web.utility.BaseWebAction;

import net.aditri.report.birt.BirtEngine;
import net.aditri.report.birt.Constants;


public class ReportAction extends BaseWebAction implements ModelDriven<Report> {
	private static final long serialVersionUID = 1L;
	private Report report = new Report();
	public Report getModel() {
		return this.report;
	}
	public Report getReport() {
		return report;
	}
	public void setReport(Report report) {
		this.report = report;
	}
	public String show()
	{
		//String s=getRequest().getRequestURL().toString();
		/*List<Menu> list = menuDAO.list(new Menu());
		this.createMenuItem(Long.parseLong("0"),list);
		this.getRequest().setAttribute("menuTree", menuTreeString);*/
		
		return SUCCESS;
	}
	public String viewReport()
	{
		return SUCCESS;
	}
	public String loadReportUI()
    {
		String rpt="";
		
		if(getRequest().getParameter("rpt")!=null)
			rpt = getRequest().getParameter("rpt").toString();

		getRequest().setAttribute("rpt",rpt );
		
		ReportUI oReportUI = new ReportUI(); 
		oReportUI.setReportId(rpt);// String report="AP-1-1-1";
		//oReportUI.setReportDataArea("");//
		
		 List<ReportControl> oReportControls = new ArrayList<ReportControl>();
		 
		//List<ReportControl> oReportControls = new ArrayList<ReportControl>();
		//User oUser = ((SessionObject)this.getSession().getAttribute("LOGGEDUSER")).getUser();
		//String user = oUser.getUserName();
		
		
		//http://viralpatel.net/blogs/java-xml-xpath-tutorial-parse-xml/
		//1.1 Creating a Java DOM XML Parser
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		//builderFactory.setValidating(true);
	    builderFactory.setIgnoringElementContentWhitespace(true);
	    //builderFactory.setNamespaceAware(true);
	    //builderFactory.setIgnoringComments(true);
	    //builderFactory.setCoalescing(true);
	    
		DocumentBuilder builder = null;
		try {
		    builder = builderFactory.newDocumentBuilder();
		    
		    
		    
		    //1.2 Parsing XML with a Java DOM Parser
		    //Document xmlDocument = builder.parse(this.getSession().getServletContext().getResourceAsStream("/WEB-INF/ReportUI.rccfg"));Document xmlDocument = builder.parse(this.getSession().getServletContext().getResourceAsStream("/WEB-INF/ReportUI.rccfg"));
		    //Document xmlDocument = builder.parse(new FileInputStream(this.getRequest().getContextPath()+"/WebContent/views/report/ReportUI.rccfg"));
		    Document xdoc = builder.parse(new FileInputStream(this.getSession().getServletContext().getRealPath("/views/report/ReportsUI.xml")));
		    
		    //1.3 Creating an XPath object
		    XPath xPath =  XPathFactory.newInstance().newXPath();
		    
		    //1.4 Using XPath to parse the XML
		    //String expression = "/Employees/Employee[@emplid='3333']/email";

		  //read a string value
		  //String email = xPath.compile(expression).evaluate(xdoc);
		  //read an xml node using xpath
		  //Node node = (Node) xPath.compile(expression).evaluate(xdoc, XPathConstants.NODE);
		  //read a nodelist using xpath
		  //NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xdoc, XPathConstants.NODESET);
		   
		   
		   NodeList oXmlNodeList = (NodeList) xPath.compile("//Forms/Form[@ReportId='" + oReportUI.getReportId() + "']").evaluate(xdoc, XPathConstants.NODESET);
		   if (oXmlNodeList.getLength() > 0)
		   {
			   if (oXmlNodeList.item(0).getAttributes().getNamedItem("ReportHeader") != null)
	                oReportUI.setReportHeader(oXmlNodeList.item(0).getAttributes().getNamedItem("ReportHeader").getNodeValue());
	            else
	            	 oReportUI.setReportHeader("Report header is not set yet!");
			   
			   if (oXmlNodeList.item(0).getAttributes().getNamedItem("Description") != null)
	                oReportUI.setReportDescription(oXmlNodeList.item(0).getAttributes().getNamedItem("Description").getNodeValue());
	            else
	                oReportUI.setReportDescription("Report description is not set yet!");
			   

			   if (oXmlNodeList.item(0).getAttributes().getNamedItem("ReportName") != null)
	                oReportUI.setReportName(oXmlNodeList.item(0).getAttributes().getNamedItem("ReportName").getNodeValue());
	            else
	                oReportUI.setReportName("");
			   
			  
			   for(Node oXmlNode:XmlUtil.asList(new IgnoreEmptyNodeList(oXmlNodeList.item(0).getChildNodes())))
			   {
				   if (oXmlNode.getNodeName().equalsIgnoreCase("ReportBody"))
	               {
	                    oReportUI.setFormBody(oXmlNode.getTextContent());// = oXmlNode.InnerText;
	                    try {
							Document oAnetDoc = XmlUtil.convertXMLDocument (oReportUI.getFormBody(),"net.aditri.ui.form.body");
							NodeList oAnetNodeList = (NodeList) xPath.compile("//div[@anet-datasource]").evaluate(oAnetDoc, XPathConstants.NODESET);
							if(oAnetNodeList.getLength() > 0) {
								 for(Node oAnetNode:XmlUtil.asList(new IgnoreEmptyNodeList(oAnetNodeList))) {
									 
									 String anetId = oAnetNode.getAttributes().getNamedItem("id").getNodeValue().trim();
									 Node oNodeReportControl = (Node) xPath.compile("//AnetDatasource/ReportControl[@Id='" + oAnetNode.getAttributes().getNamedItem("anet-datasource").getNodeValue().trim() + "']").evaluate(xdoc, XPathConstants.NODE);
									 if(oNodeReportControl!=null) {
										 if(!isReportControlExist(oReportControls,anetId)) {
											 ReportControl tmpReportControl = initReportControl(oNodeReportControl,anetId);
											 if(tmpReportControl.getId()!=null)
												 oReportControls.add(tmpReportControl);
										 }
									 }
								 }
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
	                    continue;
	               }
				   if (oXmlNode.getNodeName().equalsIgnoreCase("ReportControls"))
	               {
					   if (oXmlNode.hasChildNodes())
	                   {
						   NodeList rptCtrls = new IgnoreEmptyNodeList(oXmlNode.getChildNodes());
						   //System.out.println(rptCtrls.getLength());
						   for (Node oNode: XmlUtil.asList(rptCtrls))
						   {
							   String anetId = oNode.getAttributes().getNamedItem("Id").getNodeValue().trim();
							   if(!isReportControlExist(oReportControls,anetId)) {
								   ReportControl tmpReportControl = initReportControl(oNode,anetId);
									 if(tmpReportControl.getId()!=null)
										 oReportControls.add(tmpReportControl);
							   }
						   }
	                   }
	               }
			   }
			   oReportUI.setReportControls(oReportControls);
               oReportUI.setReportGateway(QueryBuilder.GetReportGateway(oReportUI.getReportId(), xdoc));
		   }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}	
		finally{
			User oUser = ((SessionObject)this.getSession().getAttribute("LOGGEDUSER")).getUser();
			this.getRequest().setAttribute("uKey", oUser.getUserName());
			//this.getRequest().setAttribute("sKey", this.getSession().getId().split("\\.")[0]);
			this.getRequest().setAttribute("sKey", this.getSession().getId());
			this.getRequest().setAttribute("objReportUI", oReportUI);
		}
       /* return PartialView(oReportUI);*/
		return SUCCESS;
    }
	public boolean isReportControlExist(List<ReportControl> lstReportControl,String Id) {
		boolean isExist = false;
		for(ReportControl o:lstReportControl) {
			if(o.getTempId().trim().equalsIgnoreCase(Id))
				isExist=true;
			break;
		}
		return isExist;
	}
	public ReportControl initReportControl(Node oNode, String anetId)
	{
		ControlType oControlType;
		ReportControl oReportControl = new ReportControl();
		try {
	    oControlType = ControlType.valueOf(oNode.getAttributes().getNamedItem("ControlType").getNodeValue());
	    if (oControlType != ControlType.Lookup)
        {
            oReportControl.setId(oNode.getAttributes().getNamedItem("Id").getNodeValue().trim());
            oReportControl.setTempId(anetId);
            oReportControl.setNameField(oNode.getAttributes().getNamedItem("NameField").getNodeValue().trim());
            oReportControl.setValueField(oNode.getAttributes().getNamedItem("ValueField").getNodeValue().trim());
            oReportControl.setControlType(oControlType);
            if (oNode.getAttributes().getNamedItem("AllowAll") != null)
                 oReportControl.setAllowAll(oNode.getAttributes().getNamedItem("AllowAll").getNodeValue().trim().toUpperCase().equals("FALSE")?false:true);
            if (oNode.getAttributes().getNamedItem("DefaultValue")!= null)
                 oReportControl.setDefaultValue(oNode.getAttributes().getNamedItem("DefaultValue").getNodeValue().trim());
            else
                 oReportControl.setDefaultValue("");
            DataSource oReportDataSource = new DataSource();
            DataManager oDataManager;
            if (oNode.getAttributes().getNamedItem("DefaultValueAsTop1")!= null)
            {
                 if (oNode.getAttributes().getNamedItem("DefaultValueAsTop1").getNodeValue().trim().toUpperCase().equals("TRUE"))
                     oReportControl.setDefaultValueAsTop1(true);
                 else
                     oReportControl.setDefaultValueAsTop1(false);
            }
            //ONLY FOR Default Value OF COMBOBOX Coming From DATABASE
            if (oNode.getAttributes().getNamedItem("DefaultValueFromDataSource2")!= null)
            {
         	   for (Node o:XmlUtil.asList(oNode.getChildNodes()))
         	   {
         		   if (o.getNodeName().equalsIgnoreCase("DataSource2"))
         		   {
         			   oReportDataSource.setConnectionName((o.getAttributes().getNamedItem("ConnectionName") != null) ? o.getAttributes().getNamedItem("ConnectionName").getNodeValue().trim() : "");
         			   //oReportDataSource.setConnectionString((o.getAttributes().getNamedItem("ConnectionString") != null) ? o.getAttributes().getNamedItem("ConnectionString").getNodeValue().trim() : "");
         			   
         			   if (!oReportDataSource.getConnectionName().equals(""))
                            oDataManager = new DataManager(o.getAttributes().getNamedItem("ConnectionName").getNodeValue().trim(), true);
                        //else if (!oReportDataSource.getConnectionString().equals(""))
                            //oDataManager = new DataManager(o.getAttributes().getNamedItem("ConnectionString").getNodeValue().trim(), false); // Connet From Connection String
                        else
                            oDataManager = new DataManager();
             		   
             		   oReportDataSource.setSourceType(SourceType.valueOf(o.getAttributes().getNamedItem("SourceType").getNodeValue().trim()));
             		   
             		   SQLFunction oSQLFunction = new SQLFunction();
                        oSQLFunction.setName(o.getChildNodes().item(0).getAttributes().getNamedItem("Name")!=null?o.getChildNodes().item(0).getAttributes().getNamedItem("Name").getNodeValue().trim():"");
                        oSQLFunction.setReturnAs(o.getChildNodes().item(0).getAttributes().getNamedItem("ReturnAs")!=null?o.getChildNodes().item(0).getAttributes().getNamedItem("ReturnAs").getNodeValue().trim():"");
                        oReportDataSource.setFunction(oSQLFunction);
                        
                        oReportControl.setDataSource(oReportDataSource);
                        oReportControl.setDefaultValue(oDataManager.GetReportControlDefaultValue(oReportControl));
         		   }  
         	   }
            }
            Node oDSNode = (new IgnoreEmptyNodeList(oNode.getChildNodes())).item(0);
            oReportDataSource.setConnectionName((oDSNode.getAttributes().getNamedItem("ConnectionName") != null) ? oDSNode.getAttributes().getNamedItem("ConnectionName").getNodeValue().trim() : "");
            //oReportDataSource.setConnectionString((oNode.getAttributes().getNamedItem("ConnectionString") != null) ? oNode.getAttributes().getNamedItem("ConnectionString").getNodeValue().trim() : "");
            
            if (!oReportDataSource.getConnectionName().equals(""))
            {
         	   oDataManager = new DataManager(this.getDBConfig(oReportDataSource.getConnectionName()));
            }
                
            //else if (!oReportDataSource.getConnectionString().equals(""))
                //oDataManager = new DataManager(oNode.getAttributes().getNamedItem("ConnectionString").getNodeValue().trim(), false); // Connet From Connection String
            else
                oDataManager = new DataManager();
            
            oReportDataSource.setSourceType(SourceType.valueOf(oDSNode.getAttributes().getNamedItem("SourceType").getNodeValue().trim()));
            Node oSourceTypeNode = (new IgnoreEmptyNodeList(oDSNode.getChildNodes())).item(0);
            switch (oReportDataSource.getSourceType())
            {
                case SQLView:
             	   oReportDataSource.setView(new SQLView(oSourceTypeNode.getAttributes().getNamedItem("Name").getNodeValue().trim()));
             	   oReportControl.setDataSource(oReportDataSource);
             	   oReportControl.setControlData(oDataManager.GetReportControlValue(oReportControl,QueryBuilder.BuildQuery(oSourceTypeNode, oReportControl, oReportDataSource, oDataManager)));
             	   break;
                case SQLTable:
             	   oReportDataSource.setTable(new SQLTable(oSourceTypeNode.getAttributes().getNamedItem("Name").getNodeValue().trim()));
             	   oReportControl.setDataSource(oReportDataSource);
             	   
             	   oReportControl.setControlData(oDataManager.GetReportControlValue(oReportControl,QueryBuilder.BuildQuery(oSourceTypeNode, oReportControl, oReportDataSource, oDataManager)));
             	   break;
                case SQLProcedure:
             	   oReportDataSource.setSourceType(SourceType.SQLProcedure);
             	   SQLProcedure oSQLProcedure = new SQLProcedure();
                    List<Param> oSQLParams = new ArrayList<Param>();
                    Node oProcedureNode = (new IgnoreEmptyNodeList(oNode.getChildNodes())).item(0);
                    if (oDSNode.hasChildNodes() && oProcedureNode.hasChildNodes())
                    {
                 	   //NodeList oParamNodeList = new IgnoreEmptyNodeList(oProcedureNode.getChildNodes());
                 	   oSQLProcedure.setName(oProcedureNode.getAttributes().getNamedItem("Name")!=null?oProcedureNode.getAttributes().getNamedItem("Name").getNodeValue().trim():"");
                        if (oSQLProcedure.getName().equals(""))
                            return null;
                        
                        NodeList sqlParams = new IgnoreEmptyNodeList(oProcedureNode.getChildNodes());//SelectNodes("Param");
                        for (Node oSQLParam:XmlUtil.asList(sqlParams))
                        {
                     	   oSQLParams.add(new Param(oSQLParam.getAttributes().getNamedItem("Name")!=null?oSQLParam.getAttributes().getNamedItem("Name").getNodeValue().trim():"Param_dummy",oSQLParam.getAttributes().getNamedItem("Value")!=null?oSQLParam.getAttributes().getNamedItem("Value").getNodeValue().trim():"",oSQLParam.getAttributes().getNamedItem("IsStatic")!=null? Boolean.getBoolean(oSQLParam.getAttributes().getNamedItem("IsStatic").getNodeValue().trim()):false));
                        }
                       oSQLProcedure.setParams(oSQLParams);
                       oReportDataSource.setProcedure(oSQLProcedure);
                    }
                    oReportControl.setDataSource(oReportDataSource);
                    oReportControl.setControlData(oDataManager.GetReportControlValue(oReportControl));
             	   break;
                case SQLInline:
                	oReportDataSource.setSourceType(SourceType.SQLInline);
                	SQLInline oSQLInline = new SQLInline();
                	NodeList oNodeList= new IgnoreEmptyNodeList(oSourceTypeNode.getChildNodes());
                	String sql="";
                	for (Node oSQLNode: XmlUtil.asList(oNodeList))
                    {
                		if(oSQLNode.getNodeName().trim().toUpperCase()=="SQL") {
                			sql = oSQLNode.getTextContent().trim();
                			break;
                		}
                    }
                	oSQLInline.setSql(sql);
                	oReportDataSource.setInline(oSQLInline);
                	oReportControl.setDataSource(oReportDataSource);
              	   	oReportControl.setControlData(oDataManager.GetReportControlValue(oReportControl,QueryBuilder.BuildQuery(oSourceTypeNode, oReportControl, oReportDataSource, oDataManager)));
                    
               	   break;
                case SQLFunction:
             	  // yet need to implement
             	   break;
                case WebService:
             	   break;
                case Array:
             	   break;
                case JSON:
             	   break;
                /*default:
             	   break;*/
            }
            //oReportControls.add(oReportControl);
        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			
		}
	    return oReportControl;
	}
	public String loadLookupDialog()
	{
		String searchBy="";
		if(getRequest().getParameter("searchBy")!=null)
			searchBy = getRequest().getParameter("searchBy").toString();
		
		String searchValue="";
		if(getRequest().getParameter("searchValue")!=null)
			searchValue = getRequest().getParameter("searchValue").toString();
		
		String jsonReportControl="";
		if(getRequest().getParameter("jsonReportControl")!=null)
			jsonReportControl = getRequest().getParameter("jsonReportControl").toString();
		
		String where="";
		if(getRequest().getParameter("where")!=null)
			where = getRequest().getParameter("where").toString();
		
		int pageNumber = 1;
		if(getRequest().getParameter("pageNum")!=null)
			pageNumber = Integer.parseInt(getRequest().getParameter("pageNum"));
		
		int pageSize = 100;
		if(getRequest().getParameter("pageSz")!=null)
			pageSize = Integer.parseInt(getRequest().getParameter("pageSz"));
		
		boolean inSearch=false;
		if(getRequest().getParameter("appinSch")!=null)
			inSearch = Boolean.valueOf(getRequest().getParameter("appinSch").trim());
			
		String _str = "";
        String _where = "";
        Gson gson = new GsonBuilder().create();
        ReportControl oReportControl=gson.fromJson(jsonReportControl, ReportControl.class);
       // JavaScriptSerializer jss=new JavaScriptSerializer();
       // ReportControl oReportControl = jss.Deserialize<ReportControl>(jsonReportControl);

        if (!where.equals(""))
        {
        	//WhereBuilder oWhere = jss.Deserialize<WhereBuilder>(where);
        	WhereBuilder oWhere=gson.fromJson(where, WhereBuilder.class);
            if (oWhere.getValueFrom().trim() != "") // SPECIAL CASE
            {
                if (!oWhere.getValueFrom().trim().contains(",")) //for single selection
                    _where = " " + oWhere.getDBField() + oWhere.getCondition() + "'" + oWhere.getValueFrom() + "' ";
                else //for multiple selection
                {
                    String _strVals = "";
                    String[] arr = oWhere.getValueFrom().split(",");
                    for(int i=0;i<arr.length;i++)
                    {
                        _strVals += "'" + arr[i] + "',";
                    }
                    _strVals =  _strVals.substring(0,_strVals.length()-1);
                    _where = " " + oWhere.getDBField() + "IN (" + _strVals + ")";
                }
            }
            else
                _where = "";
        }

        try {
        	DataManager oDataManager;

        	if (!oReportControl.getDataSource().getConnectionName().equals(""))
            {
         	   oDataManager = new DataManager(this.getDBConfig(oReportControl.getDataSource().getConnectionName()));
            }
        	else
        		oDataManager = new DataManager();
        	
            /*if (!oReportControl.getDataSource().getConnectionName().equals(""))
                oDataManager = new DataManager(oReportControl.getDataSource().getConnectionName(),true);
            else if(!oReportControl.getDataSource().getConnectionName().equals(""))
                oDataManager = new DataManager(oReportControl.getDataSource().getConnectionName(), false);
            else
                oDataManager = new DataManager();*/
			_str = oDataManager.GetLookupReportControlValue(searchBy, searchValue, oReportControl,_where,pageNumber,pageSize,inSearch);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        finally{
        	this.getRequest().setAttribute("_lookup_search_result", _str);
        }
        return SUCCESS;
	}
	
	public String openLookupDialog()
    {
		/*
		 * If Pass report as parameter through url 404 error will occur
		 * Because the compiler will treat "report" as the member variable report of ReportAction
		 * */
		String lookupItem="";
		String lookupDataSource="";
		String report="";
		String where="";
		if(getRequest().getParameter("lookupItem")!=null)
			lookupItem = getRequest().getParameter("lookupItem").toString();
		if(getRequest().getParameter("lookupDS")!=null)
			lookupDataSource = getRequest().getParameter("lookupDS").toString();
		if(getRequest().getParameter("rpt")!=null)
			report = getRequest().getParameter("rpt").toString();
		if(getRequest().getParameter("where")!=null)
			where = getRequest().getParameter("where").toString();

		
		String _str="";
        ReportControl oReportControl = new ReportControl();
        NodeList oXmlNodeList = null;
		//1.1 Creating a Java DOM XML Parser
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	    builderFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = null;
		try {
		    builder = builderFactory.newDocumentBuilder();
		    Document xdoc = builder.parse(new FileInputStream(this.getSession().getServletContext().getRealPath("/views/report/ReportsUI.xml")));
		    XPath xPath =  XPathFactory.newInstance().newXPath();
		    if (report != "")
	        {
		    	if(lookupDataSource.trim().equals(""))
		    		oXmlNodeList = (NodeList) xPath.compile("//Forms/Form[@ReportId='" + report + "']/ReportControls/ReportControl[@Id='" + lookupItem + "']").evaluate(xdoc, XPathConstants.NODESET);
		    	else
		    		oXmlNodeList = (NodeList) xPath.compile("//AnetDatasource/ReportControl[@Id='" + lookupDataSource + "']").evaluate(xdoc, XPathConstants.NODESET);
	        }
	        /*else
	        {
	            xdoc.Load(Server.MapPath("~/App_Data/Common.rccfg"));
	            oXmlNodeList = xdoc.DocumentElement.SelectNodes("//Setup/CommonControls/ReportControl[@Id='" + lookupItem + "']");
	        }*/
	        for(Node oXmlNode:XmlUtil.asList(new IgnoreEmptyNodeList(oXmlNodeList)))
	        {
	        	if (oXmlNode.getAttributes().getNamedItem("Id") != null)
	                oReportControl.setId(oXmlNode.getAttributes().getNamedItem("Id").getNodeValue().trim());
	            else
	                return getConfigurationError(report,lookupItem,"ReportControl","Id");

	            if (oXmlNode.getAttributes().getNamedItem("NameField") != null)
	                oReportControl.setNameField(oXmlNode.getAttributes().getNamedItem("NameField").getNodeValue().trim());
	            else
	                return getConfigurationError(report,lookupItem,"ReportControl","NameField");

	            if (oXmlNode.getAttributes().getNamedItem("ValueField") != null)
	                oReportControl.setValueField(oXmlNode.getAttributes().getNamedItem("ValueField").getNodeValue().trim());
	            else
	                getConfigurationError(report, lookupItem, "ReportControl", "ValueField");

	            if (oXmlNode.getAttributes().getNamedItem("ControlType") != null)
	                oReportControl.setControlType(ControlType.Lookup);
	            else
	                getConfigurationError(report, lookupItem, "ReportControl", "ControlType");

	            if (oXmlNode.getAttributes().getNamedItem("FieldSelection") != null)
	                oReportControl.setFieldSelectionType(FieldSelectionType.valueOf(oXmlNode.getAttributes().getNamedItem("FieldSelection").getNodeValue().trim()));
	            else
	                oReportControl.setFieldSelectionType(FieldSelectionType.Single);
	            
	            if(oXmlNode.hasChildNodes())
	            {
	            	Node oNode = new IgnoreEmptyNodeList(oXmlNode.getChildNodes()).item(0);
	            	if (oNode.getNodeName().equalsIgnoreCase("DataSource")) //getLocalName
	                {
	            		DataSource oReportDataSource = new DataSource();
	            		
	            		oReportDataSource.setConnectionName((oNode.getAttributes().getNamedItem("ConnectionName") != null) ? oNode.getAttributes().getNamedItem("ConnectionName").getNodeValue().trim() : "");
	                    oReportDataSource.setConnectionString((oNode.getAttributes().getNamedItem("ConnectionString") != null) ? oNode.getAttributes().getNamedItem("ConnectionString").getNodeValue().trim() : "");
	                    
	                    //if (oNode.getAttributes().getNamedItem("SourceType") != null && (oNode.getAttributes().getNamedItem("SourceType").getNodeValue().toUpperCase().trim() == "SQLVIEW" || oNode.getAttributes().getNamedItem("SourceType").getNodeValue().toUpperCase().trim() == "SQLTABLE"))
	                    if (oNode.getAttributes().getNamedItem("SourceType") != null)
	                    {
	                    	String _sourceType = oNode.getAttributes().getNamedItem("SourceType").getNodeValue().toUpperCase().trim();
	                    	NodeList oNodeChilds = new IgnoreEmptyNodeList(oNode.getChildNodes());
	                    	if ( _sourceType.equals("SQLVIEW"))
	                    	{
	                    		oReportDataSource.setSourceType(SourceType.SQLView);
	                            if (oNode.hasChildNodes())
	                            {
	                                SQLView oSQLView = new SQLView();
	                                if (oNodeChilds.item(0).getNodeName().equalsIgnoreCase("View"))
	                                {
	                                    if (oNodeChilds.item(0).getAttributes().getNamedItem("Name") != null)
	                                        oSQLView.setName(oNodeChilds.item(0).getAttributes().getNamedItem("Name").getNodeValue().trim());
	                                    else
	                                        return getConfigurationError(report, lookupItem, "View", "Name"); ;
	                                }
	                                else
	                                    return getConfigurationError(report,lookupItem,"View");

	                                oReportDataSource.setView(oSQLView);
	                            }
	                    	}
	                    	else if (_sourceType.equals("SQLTABLE"))
	                    	{
	                    		oReportDataSource.setSourceType(SourceType.SQLTable);
                                if (oNode.hasChildNodes())
                                {
                                    SQLTable oSQLTable = new SQLTable();
                                    if ( oNodeChilds.item(0).getNodeName().equalsIgnoreCase("Table"))
                                    {
                                        if (oNodeChilds.item(0).getAttributes().getNamedItem("Name") != null)
                                            oSQLTable.setName(oNodeChilds.item(0).getAttributes().getNamedItem("Name").getNodeValue().trim());
                                        else
                                            return getConfigurationError(report,lookupItem,"Table","Name");;
                                    }
                                    else
                                        return getConfigurationError(report,lookupItem,"Table");

                                    oReportDataSource.setTable(oSQLTable);
                                }
	                    	}
	                    	else if(_sourceType.equals("SQLPROCEDURE"))
	                    	{
	                    		oReportDataSource.setSourceType(SourceType.SQLProcedure);
	                            SQLProcedure oSQLProcedure = new SQLProcedure();
	                            List<Param> oSQLParams = new ArrayList<Param>();
	                            if (oNode.hasChildNodes() && oNodeChilds.item(0).hasChildNodes())
	                            {
	                                oSQLProcedure.setName((oNodeChilds.item(0).getAttributes().getNamedItem("Name") != null)? oNodeChilds.item(0).getAttributes().getNamedItem("Name").getNodeValue().trim() : "");
	                                if (oSQLProcedure.getName() == "")
	                                    return getConfigurationError(report, lookupItem, "Procedure","Name");

	                                //NodeList sqlParams = oNode.getChildNodes().item(0).getChildNodes().item(0).SelectNodes("Param");
	                                NodeList sqlParams = new IgnoreEmptyNodeList(oNodeChilds.item(0).getChildNodes().item(0).getChildNodes());
	                                for (Node oSQLParam:XmlUtil.asList(sqlParams))
	                                {
	                                    oSQLParams.add( new Param(oSQLParam.getAttributes().getNamedItem("Name")!=null?oSQLParam.getAttributes().getNamedItem("Name").getNodeValue().trim():"Param_dummy",oSQLParam.getAttributes().getNamedItem("Value")!=null?oSQLParam.getAttributes().getNamedItem("Value").getNodeValue().trim():"",oSQLParam.getAttributes().getNamedItem("IsStatic")!=null?Boolean.parseBoolean(oSQLParam.getAttributes().getNamedItem("IsStatic").getNodeValue().trim()):false));
	                                }
	                               oSQLProcedure.setParams(oSQLParams);
	                               oReportDataSource.setProcedure(oSQLProcedure);
	                            }
	                    	}
	                    	oReportControl.setDataSource(oReportDataSource);
                            Gson gson = new Gson();
                            String _jss = gson.toJson(oReportControl);
                            _str += "<div id='" + lookupItem + "_dialog'><div style='font-size:11px;' id='" + lookupItem + "' ><label class='anet-dialog-search-label'>Search By</label><input data-ctx-path='"+this.getRequest().getContextPath()+"' type='text' data-where='" + where + "' id='txtAnetDialogSearch' value='' data-reportcontrol='" + _jss + "'  />&nbsp;&nbsp;";
                            _str += "<div id='anet-dialog-search-lookup' data-ctx-path='"+this.getRequest().getContextPath()+"' ></div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                            _str += "&nbsp;&nbsp;<input type='radio' name='rdoRCDialog' value='" + oXmlNode.getAttributes().getNamedItem("NameField").getNodeValue().trim() + "' checked />&nbsp;&nbsp;" + oXmlNode.getAttributes().getNamedItem("NameField").getNodeValue().trim() + " &nbsp;&nbsp;<input type='radio' name='rdoRCDialog' value='" + oXmlNode.getAttributes().getNamedItem("ValueField").getNodeValue().trim() + "'  />&nbsp;&nbsp;" + oXmlNode.getAttributes().getNamedItem("ValueField").getNodeValue().trim() + "</div>";

                            if (oReportControl.getFieldSelectionType() == FieldSelectionType.Multiple)
                                _str += "&nbsp;&nbsp;<input type='checkbox' name='chkRCDialog' id='anet-dialog-search-checkbox' checked />&nbsp;&nbsp;<label class='anet-dialog-search-label'>Append Selected Values?</label>";
                            
                            _str += "&nbsp;&nbsp;<input type='checkbox' name='chkRCInSearch' id='anet-dialog-insearch-checkbox' />&nbsp;&nbsp;<label class='anet-dialog-search-label'>Apply Search From Set?</label>";
                            _str += "<span class='anet-dialog-lodder' ></span>";
                            _str += "<table id='anet-dialog-data-table-dummy'><thead><tr><td style='width:50px;'>Select</td><td style='min-width:100px'>" + oXmlNode.getAttributes().getNamedItem("ValueField").getNodeValue().trim() + "</td><td style='min-width:400px'>" + oXmlNode.getAttributes().getNamedItem("NameField").getNodeValue().trim() + "</td></tr></thead></table>";
                            _str+= "<div id='anet-dialog-search-result'></div></div>";
	                    }
	                }
	            }
	            
	        }
	    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} 
		finally
		{
			this.getRequest().setAttribute("_lookup", _str);
		}
        //return _str;
        return SUCCESS;
    }
	private String getConfigurationError(String form,String control,String tag)
    {
        return "Problem found in "+tag+" tag of ReportControl " + control + " of ReportUIForm " + form + ". Please check config file.";
    }
    private String getConfigurationError(String form, String control, String tag,String attribute)
    {
        return "Problem found in " + tag + " tag [in attribute = "+attribute+"] of ReportControl " + control + " of ReportUIForm " + form + ". Please check config file.";
    }
	private DBConfig getDBConfig(String conName){
 	   	
 	   
 	    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = null;
		Node oDBConfigNode = null;
		try 
		{
		    builder = builderFactory.newDocumentBuilder();
		    Document xdoc = builder.parse(new FileInputStream(this.getSession().getServletContext().getRealPath("/views/report/ReportsUI.xml")));
			XPath xPath =  XPathFactory.newInstance().newXPath();
			
			oDBConfigNode = (Node) xPath.compile("//DBConfigs/DBConfig[@Name='" + conName + "']").evaluate(xdoc, XPathConstants.NODE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}	
		finally{
			
		}
	   DBConfig oDBConfig=new DBConfig();   
 	   oDBConfig.setName(conName);
 	   oDBConfig.setDbServer( DBServer.valueOf(oDBConfigNode.getAttributes().getNamedItem("DBServer").getNodeValue()));
 	   oDBConfig.setConString(oDBConfigNode.getAttributes().getNamedItem("ConString").getNodeValue());
 	   oDBConfig.setDriver(oDBConfigNode.getAttributes().getNamedItem("Driver").getNodeValue());
 	   oDBConfig.setUser(oDBConfigNode.getAttributes().getNamedItem("User").getNodeValue());
 	   oDBConfig.setPwd(oDBConfigNode.getAttributes().getNamedItem("PWD").getNodeValue());
 	   oDBConfig.setPWDEncrypt( (oDBConfigNode.getAttributes().getNamedItem("PWDEncrypt")!=null)?Boolean.valueOf(oDBConfigNode.getAttributes().getNamedItem("PWDEncrypt").getNodeValue().trim().toUpperCase()):false);
 	   
 	   return oDBConfig;
	}
	
	
	
	private InputStream inputStream;
	private String fileName;
	private long contentLength;
	
	public String getFileName() {
		return fileName;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public long getContentLength() {
		return contentLength;
	}
	public String downloadFile() throws FileNotFoundException {
		
		File fileToDownload = new File("D:/debuginfo.txt");
        inputStream = new FileInputStream(fileToDownload);
        
        fileName = fileToDownload.getName();
        contentLength = fileToDownload.length();
	        
		return SUCCESS; 
	}
	
	public void exportPDF() throws ServletException {
		String rptParams="";
		if(getRequest().getParameter("rptParams")!=null)
			rptParams = getRequest().getParameter("rptParams").toString();
		
		Gson gson = new GsonBuilder().create();
		ReportSnippet oReportSnippet=gson.fromJson(rptParams, ReportSnippet.class);
		String[] arr = oReportSnippet.getReportFile().split("/");
		
		this.getResponse().setContentType("application/pdf");
    	this.getResponse().setHeader("Content-Disposition", "attachment; filename="+arr[arr.length-1]+".pdf"); // inline, attachment
    	
		
    	
        
        ServletContext sc = this.getRequest().getSession().getServletContext();
        this.birtReportEngine = BirtEngine.getBirtEngine(sc);

        IReportRunnable design;
        try {
        	//String rp=  "D:\\apache-tomcat-7.0.70\\webapps\\advance-birt\\report\\";
            // Open report design
            //design = birtReportEngine.openReportDesign(sc.getRealPath("/reports") + "/" + oReportSnippet.getReportFile()+".rptdesign");
            design = birtReportEngine.openReportDesign(sc.getRealPath("/reports") + "/"+oReportSnippet.getReportFile()+".rptdesign"); //testWebReport
            
        	//design = birtReportEngine.openReportDesign(sc.getRealPath(rp +"\\reports") + "\\" + oReportSnippet.getReportFile()+".rptdesign");
            // create task to run and render report
            IRunAndRenderTask task = birtReportEngine.createRunAndRenderTask(design);

            // set output options
            IPDFRenderOption options = new PDFRenderOption();
            options.setOutputFormat(IPDFRenderOption.OUTPUT_FORMAT_PDF);
            options.setOutputStream(this.getResponse().getOutputStream());

            // run report
            task.setRenderOption(options);
          //set report parameter
            //task.setParameterValue("RP_param1", paramValue);
            //task.setParameterValue("RP_param2", paramValue);
            for(Param oParam:oReportSnippet.getParams()) {
            	task.setParameterValue(oParam.getName(), oParam.getValue());
            }
            task.run();
            task.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServletException(e);
        }
	}
	public void exportExcel() throws ServletException {
		String rptParams="";
		if(getRequest().getParameter("rptParams")!=null)
			rptParams = getRequest().getParameter("rptParams").toString();
		Gson gson = new GsonBuilder().create();
		ReportSnippet oReportSnippet=gson.fromJson(rptParams, ReportSnippet.class);
		
		String[] arr = oReportSnippet.getReportFile().split("/");
		
        this.getResponse().setContentType("application/vnd.ms-excel");
        this.getResponse().setHeader("Content-Disposition", "inline; filename="+arr[arr.length-1]+".xls"); // inline, attachment
        
        

        ServletContext sc = this.getRequest().getSession().getServletContext();
        this.birtReportEngine = BirtEngine.getBirtEngine(sc);

        IReportRunnable design;
        try {
            // Open report design
        	
            //design = birtReportEngine.openReportDesign(sc.getRealPath("/reports") + "/" + reportName);
        	design = birtReportEngine.openReportDesign(sc.getRealPath("/reports") + "/"+oReportSnippet.getReportFile()+".rptdesign"); //testWebReport
            
        	// create task to run and render report
            IRunAndRenderTask task = birtReportEngine.createRunAndRenderTask(design);

            // set output options
            IExcelRenderOption options = new EXCELRenderOption();
            options.setOutputFormat(Constants.XLS_FORMAT);
            options.setOutputStream(this.getResponse().getOutputStream());

            // run report
            task.setRenderOption(options);
            //task.setParameterValue("RP_param1", paramValue);
            //task.setParameterValue("RP_param2", paramValue);
            for(Param oParam:oReportSnippet.getParams()) {
            	task.setParameterValue(oParam.getName(), oParam.getValue());
            }
            task.run();
            task.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServletException(e);
        }
    }
	
	
	private IReportEngine birtReportEngine = null;
    protected static Logger logger = Logger.getLogger("org.eclipse.birt");
	@SuppressWarnings("unused")
	private ByteArrayOutputStream renderReportPage(HttpServletRequest request) throws ServletException {

        // get report name and launch the engine
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String reportName = request.getParameter("ReportName");
        ServletContext sc = request.getSession().getServletContext();
        this.birtReportEngine = BirtEngine.getBirtEngine(sc);

        IReportRunnable design;
        try {
            // Open report design
            design = birtReportEngine.openReportDesign(sc.getRealPath("/reports") + "/" + reportName);
            // create task to run and render report
            IRunAndRenderTask task = birtReportEngine.createRunAndRenderTask(design);

            // set output options
            IHTMLRenderOption options = new HTMLRenderOption();
            options.setOutputFormat(IHTMLRenderOption.OUTPUT_FORMAT_HTML);
            options.setEmbeddable(true);
            options.setOutputStream(out);

            // set the image handler to a HTMLServerImageHandler if you plan on using the base image url.
            options.setImageHandler(new HTMLServerImageHandler());
            options.setBaseImageURL(request.getContextPath() + "/images");
            options.setImageDirectory(sc.getRealPath("/images"));

            // run report
            task.setRenderOption(options);
            //set report parameter
            //task.setParameterValue("RP_param1", paramValue);
            //task.setParameterValue("RP_param2", paramValue);
            task.run();
            task.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServletException(e);
        }

        return out;
    }
}

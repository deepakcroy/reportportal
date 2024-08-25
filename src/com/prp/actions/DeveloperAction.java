package com.prp.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import com.opensymphony.xwork2.ModelDriven;
import com.prp.models.DBPool;
import com.prp.models.SessionObject;
import com.prp.models.User;

import net.aditri.dao.DBResponse;
import net.aditri.dao.DataManager;
import net.aditri.dao.HibernateUtil;
import net.aditri.ui.common.DBConfig;
import net.aditri.ui.common.Enum.DBServer;
import net.aditri.util.IgnoreEmptyNodeList;
import net.aditri.util.XmlUtil;
import net.aditri.web.utility.BaseWebAction;


public class DeveloperAction extends BaseWebAction implements ModelDriven<DBPool> {
private static final long serialVersionUID = 1L;
	
	private DBPool dbPool = new DBPool();
	
	public DBPool getModel() {
		return this.dbPool;
	}
	public DBPool getDBPool() {
		return this.dbPool;
	}
	public void setDBPool(DBPool dbPool) {
		this.dbPool = dbPool;
	}
	
	public String show()
	{	
		
		return SUCCESS;
	}
	public String initDeveloper()
	{
		raise404IFNotAjax(this.getRequest());
		String typ = this.getRequest().getParameter("typ");
		String _page = typ.toUpperCase();
		String _pageResult = "ERROR";
		switch(_page)
		{
			case "RPT-DBCONFIG":
				_pageResult = initDBConfig(typ);
				break;
			case "DBOARD-DBCONFIG":
				_pageResult = initDBConfig(typ);
				break;
			case "RPT-UI-BUILDER":
				_pageResult = initReportUIBuilder(typ);
				break;
			default:
				_pageResult = "ERROR";
		}
		return _pageResult;
	}
	private String initDBConfig(String typ)
	{
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = null;
		List<DBConfig> dbConfigList = new ArrayList<DBConfig>();
		try {
		    builder = builderFactory.newDocumentBuilder();
		    Document xdoc = null;
		    if(typ.toLowerCase().equals("rpt-dbconfig"))
			{
		    	xdoc = builder.parse(new FileInputStream(this.getSession().getServletContext().getRealPath("/views/report/ReportsUI.xml")));
			}
			else //if(typ.toLowerCase().equals("dboard-dbconfig"))
			{
				xdoc = builder.parse(new FileInputStream(this.getSession().getServletContext().getRealPath("/views/dashboard/Dashboards.xml")));
			}
		    XPath xPath =  XPathFactory.newInstance().newXPath();
		    NodeList oXmlNodeList = (NodeList) xPath.compile("//DBConfigs/DBConfig").evaluate(xdoc, XPathConstants.NODESET);
		    
		    if (oXmlNodeList.getLength() > 0)
		    {
		    	for(int i=0;i<oXmlNodeList.getLength();i++)
		    	{
		    		DBConfig oDBConfig = new DBConfig();
		    		oDBConfig.setName(oXmlNodeList.item(i).getAttributes().getNamedItem("Name").getNodeValue());
		    		//Enum
		    		oDBConfig.setDbServer(DBServer.valueOf(oXmlNodeList.item(i).getAttributes().getNamedItem("DBServer").getNodeValue()));
		    		oDBConfig.setConString(oXmlNodeList.item(i).getAttributes().getNamedItem("ConString").getNodeValue());
		    		oDBConfig.setDriver(oXmlNodeList.item(i).getAttributes().getNamedItem("Driver").getNodeValue());
		    		oDBConfig.setUser(oXmlNodeList.item(i).getAttributes().getNamedItem("User").getNodeValue());
		    		oDBConfig.setPwd(oXmlNodeList.item(i).getAttributes().getNamedItem("PWD").getNodeValue());
		    		oDBConfig.setPWDEncrypt( (oXmlNodeList.item(i).getAttributes().getNamedItem("PWDEncrypt")!=null)?Boolean.valueOf(oXmlNodeList.item(i).getAttributes().getNamedItem("PWDEncrypt").getNodeValue().trim().toLowerCase()):false);
		    		
		    		dbConfigList.add(oDBConfig);
		    	}
		    	
		    }
		}
		catch (FileNotFoundException e) {
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
			this.getRequest().setAttribute("dbConfigs", dbConfigList);
		}
		if(typ.toLowerCase().equals("rpt-dbconfig"))
		{
			return "REPORT-DBCFG";
		}
		else if(typ.toLowerCase().equals("dboard-dbconfig"))
		{
			return "DASHBOARD-DBCFG";
		}
		else
			return ERROR;
	}
	private String initReportUIBuilder(String typ)
	{
		return "REPORT-UI-BUILDER";
	}
	public String loadProperties()
	{
		//net.aditri.ui.common.Enum.ControlType[] c = net.aditri.ui.common.Enum.ControlType.values();
		
		return SUCCESS;
	}
	public String saveReportUI() {
		HttpServletRequest request = this.getRequest();
		raise404IFNotAjax(request);
		//.................................save..............deepak
		Session session = null;
		Transaction transaction = null;
		
		DBResponse oDBResponse = new DBResponse();
		Query query =null;
		final User oUser = ((SessionObject)this.getSession().getAttribute("LOGGEDUSER")).getUser();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			
			/*if(action.equals("EDIT")) {
				query =session.getNamedQuery("SQL_EDIT_MENU");
				query.setParameter("P_MENU_ID", NumberUtils.toInt(getRequest().getParameter("id"),0));
			} else {
				query =session.getNamedQuery("SQL_INSERT_UIDEF");
				query.setParameter("P_CREATEDBY", oUser.getUserName());
			}*/
			
			query =session.getNamedQuery("SQL_INSERT_UIDEF");
			query.setParameter("P_CREATEDBY", oUser.getUserName());
			
			int isSuccess = query.executeUpdate();
			if(isSuccess==1) {
				oDBResponse =new DBResponse(true,"Operation Successfully Executed.");
				printObject(oDBResponse);
			}
			
		}  catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} 
		catch (Exception e)
		{
			transaction.rollback();
			e.printStackTrace();
		}
		finally {
			if (session != null) {
				transaction.commit();
	            session.close();
			}
		}
		return null;
	}
	public String saveDBoardDBCfgForm()
	{
		HttpServletRequest request = this.getRequest();
		raise404IFNotAjax(request);
		setGlobalDBConfig(request,"/views/dashboard/Dashboards.xml");
		return INPUT;
	}
	public String saveReportDBCfgForm()
	{
		//HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletRequest request = this.getRequest();
		raise404IFNotAjax(request);
		setGlobalDBConfig(request,"/views/report/ReportsUI.xml");
		return INPUT;
	}
	public String saveReportDBCfg()
	{
		HttpServletRequest request = this.getRequest();
		raise404IFNotAjax(request);
		
		saveGlobalDBConfig(request,"/views/report/ReportsUI.xml");
		this.print("done!");
		return null;
	}
	public String saveDBoardDBCfg()
	{
		HttpServletRequest request = this.getRequest();
		raise404IFNotAjax(request);
		
		saveGlobalDBConfig(request,"/views/dashboard/Dashboards.xml");
		this.print("done!");
		return null;
	}
	
	private void setGlobalDBConfig(HttpServletRequest request,String configFile) 
	{
		//DBConfig oDBConfig = new DBConfig(); 
		String cName = request.getParameter("cname");
		DBConfig oDBConfig = new DBConfig();
		if(cName!=null)
		{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			builderFactory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = null;
		
			
			try {
			    builder = builderFactory.newDocumentBuilder();
			    Document xdoc = null;
			    xdoc = builder.parse(new FileInputStream(this.getSession().getServletContext().getRealPath(configFile)));
			    XPath xPath =  XPathFactory.newInstance().newXPath();
			    Node oXmlNode = (Node) xPath.compile("//DBConfigs/DBConfig[@Name='"+cName+"']").evaluate(xdoc, XPathConstants.NODE);
			    
		    	
	    		oDBConfig.setName(oXmlNode.getAttributes().getNamedItem("Name").getNodeValue());
	    		oDBConfig.setDbServer(DBServer.valueOf(oXmlNode.getAttributes().getNamedItem("DBServer").getNodeValue()));
	    		
	    		oDBConfig.setConString(oXmlNode.getAttributes().getNamedItem("ConString").getNodeValue());
	    		oDBConfig.setDriver(oXmlNode.getAttributes().getNamedItem("Driver").getNodeValue());
	    		oDBConfig.setUser(oXmlNode.getAttributes().getNamedItem("User").getNodeValue());
	    		oDBConfig.setPwd(oXmlNode.getAttributes().getNamedItem("PWD").getNodeValue());
	    		
	    		oDBConfig.setPWDEncrypt( (oXmlNode.getAttributes().getNamedItem("PWDEncrypt")!=null)?Boolean.valueOf(oXmlNode.getAttributes().getNamedItem("PWDEncrypt").getNodeValue().trim().toLowerCase()):false);
	    		
	    		//global.setDbConfig(oDBConfig);
			}
			catch (FileNotFoundException e) {
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
				//
			}
		}
		this.dbPool.setDbConfig(oDBConfig);
	}
	private void saveGlobalDBConfig(HttpServletRequest request,String configFile)
	{
		String conName = request.getParameter("hdnConName");
		String conString = request.getParameter("ConString");
		String dbServer = request.getParameter("DBServer");
		String driver = request.getParameter("Driver");
		String encrypt = request.getParameter("IsEncrypted");
		
		String user = request.getParameter("User");
		String pwd = request.getParameter("PWD");
		
		if(conName!=null && !conName.equals("")) //update
		{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			builderFactory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = null;
			try {
			    builder = builderFactory.newDocumentBuilder();
			    Document xdoc = null;
			    xdoc = builder.parse(new FileInputStream(this.getSession().getServletContext().getRealPath(configFile)));
			    XPath xPath =  XPathFactory.newInstance().newXPath();
			    Node oXmlNode = (Node) xPath.compile("//DBConfigs/DBConfig[@Name='"+conName+"']").evaluate(xdoc, XPathConstants.NODE);
			    
		    	if(oXmlNode.getAttributes().getNamedItem("User")!=null)
		    		oXmlNode.getAttributes().getNamedItem("User").setNodeValue(user);
		    	if(oXmlNode.getAttributes().getNamedItem("ConString")!=null)
		    		oXmlNode.getAttributes().getNamedItem("ConString").setNodeValue(conString);
		    	if(oXmlNode.getAttributes().getNamedItem("DBServer")!=null)
		    		oXmlNode.getAttributes().getNamedItem("DBServer").setNodeValue(dbServer);
		    	if(oXmlNode.getAttributes().getNamedItem("Driver")!=null)
		    		oXmlNode.getAttributes().getNamedItem("Driver").setNodeValue(driver);
		    	if(oXmlNode.getAttributes().getNamedItem("PWDEncrypt")!=null)
		    		oXmlNode.getAttributes().getNamedItem("PWDEncrypt").setNodeValue(encrypt);
		    	
		    	if(oXmlNode.getAttributes().getNamedItem("User")!=null)
		    		oXmlNode.getAttributes().getNamedItem("User").setNodeValue(user);
		    	
		    	if(oXmlNode.getAttributes().getNamedItem("PWD")!=null) {
				    if(Boolean.valueOf(encrypt)) {
				    	pwd = DataManager.EncryptDBPWD(pwd);
				    	oXmlNode.getAttributes().getNamedItem("PWD").setNodeValue(pwd);
				    }
				    else
				    	oXmlNode.getAttributes().getNamedItem("PWD").setNodeValue(pwd);
		    	}
			    // Save the result to XML doc
			    Transformer xformer = TransformerFactory.newInstance().newTransformer();
			    xformer.transform(new DOMSource(xdoc), new StreamResult(new File(this.getSession().getServletContext().getRealPath(configFile) )));
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}
			finally
			{
				//
				
			}
		}
		else // INSERT
		{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			builderFactory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = null;
			try {
			    builder = builderFactory.newDocumentBuilder();
			    Document xdoc = null;
			    xdoc = builder.parse(new FileInputStream(this.getSession().getServletContext().getRealPath(configFile)));
			    XPath xPath =  XPathFactory.newInstance().newXPath();
			    NodeList oDBConfigs = (NodeList) xPath.compile("//DBConfigs/DBConfig").evaluate(xdoc, XPathConstants.NODESET);
			    List<Node> oDBConfigList  = XmlUtil.asList(new IgnoreEmptyNodeList(oDBConfigs));
			    Node oInsertAfterDBConfig = oDBConfigList.get(0);
			    
			    Element oElmDBConfig = xdoc.createElement("DBConfig");

			    oElmDBConfig.setAttribute("Name",request.getParameter("Name"));
			    oElmDBConfig.setAttribute("DBServer",dbServer);
			    oElmDBConfig.setAttribute("ConString",conString);
			    oElmDBConfig.setAttribute("Driver",driver);
			    oElmDBConfig.setAttribute("User",user);
			    
			    if(Boolean.valueOf(encrypt)) {
			    	pwd = DataManager.EncryptDBPWD(pwd);
			    	oElmDBConfig.setAttribute("PWD",pwd);
			    }
			    else
			    	oElmDBConfig.setAttribute("PWD",pwd);
			    
			    oElmDBConfig.setAttribute("PWDEncrypt",encrypt);
			    
			    oInsertAfterDBConfig.getParentNode().appendChild(oElmDBConfig);
		    	
			    // Save the result to XML doc
			    Transformer xformer = TransformerFactory.newInstance().newTransformer();
			    
			    //for pretty print
			    xformer.setOutputProperty(OutputKeys.INDENT, "yes");
			    xformer.transform(new DOMSource(xdoc), new StreamResult(new File(this.getSession().getServletContext().getRealPath(configFile) )));
			    
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}
			finally
			{
				//
			}
		}
	}
	public String openDSLookupDialog() {
		String _html="";
		_html += "<div id='anet-property-ds-id_dialog'><div style='font-size:11px;' id='anet-property-ds-id' ><label class='anet-dialog-search-label'>Search By Name</label><input data-ctx-path='"+this.getRequest().getContextPath()+"' type='text' id='txtAnetDialogSearch' value=''  />&nbsp;&nbsp;";
		_html += "<div id='anet-dialog-search-lookup' data-ctx-path='"+this.getRequest().getContextPath()+"' ></div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		
        _html += "<span class='anet-dialog-lodder' ></span>";
        _html += "<table id='anet-dialog-data-table-dummy'><thead><tr><td style='width:50px;'>Select</td><td style='min-width:100px'>Name</td><td style='min-width:400px'>Information</td></tr></thead></table>";
        _html+= "<div id='anet-dialog-search-result'></div></div>";
		this.getRequest().setAttribute("_lookup", _html);
		return SUCCESS;
	}
	public String loadDSLookupDialog()
	{
		
		String searchValue="";
		if(getRequest().getParameter("searchValue")!=null)
			searchValue = getRequest().getParameter("searchValue").toString();
		
		
			
		String _str = "";
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
        	 builder = builderFactory.newDocumentBuilder();
        	 Document xdoc = builder.parse(new FileInputStream(this.getSession().getServletContext().getRealPath("/views/report/ReportsUI.xml")));
 		     XPath xPath =  XPathFactory.newInstance().newXPath();
 		     NodeList oXmlNodeList = (NodeList) xPath.compile("//AnetDatasource/ReportControl[contains(@Id,'" + searchValue + "')]").evaluate(xdoc, XPathConstants.NODESET);
 		     
 		    _str += "<div class='anet-dialog-search-result-grid'><table class='anet-dialog-data-table'><thead><tr><td style='width:65px;'>&nbsp;</td><td style='min-width:100px'>&nbsp;</td><td style='min-width:400px'>&nbsp;</td></tr></thead><tbody>";
 		    
 		    if (oXmlNodeList.getLength() > 0) {
 		    	for(Node oNode:XmlUtil.asList(new IgnoreEmptyNodeList(oXmlNodeList))) {
	 		    	_str +="<tr>";
	 		    	_str +="<td><input type='radio' class='chkRCDialogValSelector' name='rdoPWDSLookup' value='"+oNode.getAttributes().getNamedItem("Id").getNodeValue().trim()+"' /></td>";
	 		    	_str +="<td>"+oNode.getAttributes().getNamedItem("Id").getNodeValue().trim()+"</td>";
	 		    	_str +="<td>"+oNode.getAttributes().getNamedItem("NameField").getNodeValue().trim()+"</td>";
	 		    	_str +="</tr>";
 		    	}
 		    }
 		   _str += "</tbody></table></div>";
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
        finally{
        	this.getRequest().setAttribute("_lookup_search_result", _str);
        }
        return SUCCESS;
	}
}

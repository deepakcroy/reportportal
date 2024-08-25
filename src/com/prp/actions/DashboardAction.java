package com.prp.actions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.opensymphony.xwork2.ModelDriven;

import net.aditri.dao.DataManager;
import net.aditri.ui.common.DBConfig;
import net.aditri.ui.common.DataSource;
import net.aditri.ui.common.Param;
import net.aditri.ui.common.QueryBuilder;
import net.aditri.ui.common.SQLInline;
import net.aditri.ui.common.SQLProcedure;
import net.aditri.ui.common.SQLTable;
import net.aditri.ui.common.SQLView;
import net.aditri.ui.common.Enum.ChartType;
import net.aditri.ui.common.Enum.DBServer;
import net.aditri.ui.common.Enum.SourceType;
import net.aditri.ui.dashboard.Chart;
import net.aditri.ui.dashboard.ChartData;
import net.aditri.ui.dashboard.Dashboard;
import net.aditri.ui.dashboard.Widget;
import net.aditri.ui.report.ReportControl;
import net.aditri.util.IgnoreEmptyNodeList;
import net.aditri.util.XmlUtil;
import net.aditri.web.utility.BaseWebAction;
import net.aditri.web.utility.KeyValue;


public class DashboardAction extends BaseWebAction implements ModelDriven<Dashboard> {
private static final long serialVersionUID = 1L;
	
	private Dashboard dashboard = new Dashboard();

	public Dashboard getModel() {
		return this.dashboard;
	}

	public Dashboard getDashboard() {
		return dashboard;
	}

	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}

	public String show() {
		return SUCCESS;
	}

	private Widget _initWidget(Node widgetNode, Document xdoc, XPath xPath) {
		Widget oWidget = new Widget();

		try {
			List<Chart> charts = new ArrayList<Chart>();
			if (widgetNode.getAttributes().getNamedItem("Id") != null)
				oWidget.setId(widgetNode.getAttributes().getNamedItem("Id").getNodeValue());
			for (Node chartNode : XmlUtil.asList(new IgnoreEmptyNodeList(widgetNode.getChildNodes()))) {
				Chart oChart = new Chart();
				if (chartNode.getAttributes().getNamedItem("Id") != null)
					oChart.setId(chartNode.getAttributes().getNamedItem("Id").getNodeValue().trim());
				if (chartNode.getAttributes().getNamedItem("RenderTo") != null)
					oChart.setRenderTo(chartNode.getAttributes().getNamedItem("RenderTo").getNodeValue().trim());
				if (chartNode.getAttributes().getNamedItem("ChartType") != null)
					oChart.setType(ChartType.valueOf(
							chartNode.getAttributes().getNamedItem("ChartType").getNodeValue().trim().toUpperCase()));

				ChartData oChartData = new ChartData();
				if (chartNode.getAttributes().getNamedItem("XAxis") != null)
					oChartData.setXAxis(chartNode.getAttributes().getNamedItem("XAxis").getNodeValue().trim());
				if (chartNode.getAttributes().getNamedItem("SeriesColumn") != null)
					oChartData.setSeriesColumn(
							chartNode.getAttributes().getNamedItem("SeriesColumn").getNodeValue().trim().split(","));

				List<KeyValue> nodes = new ArrayList<KeyValue>();
				DataManager oDataManager;
				DataSource oDataSource = new DataSource();
				ReportControl oReportControl = new ReportControl();
				for (Node chartChildNode : XmlUtil.asList(new IgnoreEmptyNodeList(chartNode.getChildNodes()))) {
					switch (chartChildNode.getNodeName().toUpperCase()) {
					case "CHART":
						nodes.add(new KeyValue("chart", chartChildNode.getTextContent().trim().replaceAll("\n", "")));
						break;
					case "TITLE":
						nodes.add(new KeyValue("title", chartChildNode.getTextContent().trim().replaceAll("\n", "")));
						break;
					case "SUBTITLE":
						nodes.add(
								new KeyValue("subtitle", chartChildNode.getTextContent().trim().replaceAll("\n", "")));
						break;
					case "SERIESCOLUMN": // if exist overwrite previous setting
						oChartData.setSeriesColumn(
								chartChildNode.getTextContent().replaceAll("\n", "").trim().split(","));
						break;
					case "TOOLTIP":
						nodes.add(new KeyValue("tooltip", chartChildNode.getTextContent().trim().replaceAll("\n", "")));
						break;
					case "PLOTOPTIONS":
						nodes.add(new KeyValue("plotOptions",
								chartChildNode.getTextContent().trim().replaceAll("\n", "")));
						break;
					case "XAXIS":
						nodes.add(new KeyValue("xAxis", chartChildNode.getTextContent().trim().replaceAll("\n", "")));
						break;
					case "YAXIS":
						nodes.add(new KeyValue("yAxis", chartChildNode.getTextContent().trim().replaceAll("\n", "")));
						break;
					case "LEGEND":
						nodes.add(new KeyValue("legend", chartChildNode.getTextContent().trim().replaceAll("\n", "")));
						break;
					case "INIT":
						oChart.setInit(chartChildNode.getTextContent().trim().replaceAll("\n", ""));
						break;
					case "DATASOURCE":
						oDataSource.setConnectionName(
								(chartChildNode.getAttributes().getNamedItem("ConnectionName") != null)
										? chartChildNode.getAttributes().getNamedItem("ConnectionName").getNodeValue()
												.trim()
										: "");
						if (!oDataSource.getConnectionName().equals("")) {
							Node oDBConfigNode = (Node) xPath
									.compile("//DBConfigs/DBConfig[@Name='" + oDataSource.getConnectionName() + "']")
									.evaluate(xdoc, XPathConstants.NODE);
							DBConfig oDBConfig = new DBConfig();
							oDBConfig.setName(oDataSource.getConnectionName());
							oDBConfig.setDbServer(DBServer
									.valueOf(oDBConfigNode.getAttributes().getNamedItem("DBServer").getNodeValue()));
							oDBConfig.setConString(
									oDBConfigNode.getAttributes().getNamedItem("ConString").getNodeValue());
							oDBConfig.setDriver(oDBConfigNode.getAttributes().getNamedItem("Driver").getNodeValue());
							oDBConfig.setUser(oDBConfigNode.getAttributes().getNamedItem("User").getNodeValue());
							oDBConfig.setPwd(oDBConfigNode.getAttributes().getNamedItem("PWD").getNodeValue());
							oDBConfig.setPWDEncrypt(oDBConfigNode.getAttributes().getNamedItem("PWDEncrypt") != null
									? Boolean.getBoolean(oDBConfigNode.getAttributes().getNamedItem("PWDEncrypt")
											.getNodeValue().trim().toUpperCase())
									: false);

							oDataManager = new DataManager(oDBConfig);
						} else
							oDataManager = new DataManager();

						oDataSource.setSourceType(SourceType.valueOf(
								chartChildNode.getAttributes().getNamedItem("SourceType").getNodeValue().trim()));
						Node oSourceTypeNode = (new IgnoreEmptyNodeList(chartChildNode.getChildNodes())).item(0);
						switch (oDataSource.getSourceType()) {
						case SQLView:
							oDataSource.setView(new SQLView(
									oSourceTypeNode.getAttributes().getNamedItem("Name").getNodeValue().trim()));
							oChart.setDataSource(oDataSource);
							oReportControl.setDataSource(oDataSource);
							if (oChart.getType() != null && oChart.getType() == ChartType.PIE)
								oChart.setChartData(oDataManager.GetChartData4Pie(oChartData, QueryBuilder
										.BuildQuery(oSourceTypeNode, oReportControl, oDataSource, oDataManager)));
							else
								oChart.setChartData(oDataManager.GetChartData(oChartData, QueryBuilder
										.BuildQuery(oSourceTypeNode, oReportControl, oDataSource, oDataManager)));
							break;
						case SQLTable:
							oDataSource.setTable(new SQLTable(
									oSourceTypeNode.getAttributes().getNamedItem("Name").getNodeValue().trim()));
							oChart.setDataSource(oDataSource);
							oReportControl.setDataSource(oDataSource);
							if (oChart.getType() != null || oChart.getType() == ChartType.PIE)
								oChart.setChartData(oDataManager.GetChartData4Pie(oChartData, QueryBuilder
										.BuildQuery(oSourceTypeNode, oReportControl, oDataSource, oDataManager)));
							else
								oChart.setChartData(oDataManager.GetChartData(oChartData, QueryBuilder
										.BuildQuery(oSourceTypeNode, oReportControl, oDataSource, oDataManager)));
							break;
						case SQLProcedure:
							oDataSource.setSourceType(SourceType.SQLProcedure);
							SQLProcedure oSQLProcedure = new SQLProcedure();
							List<Param> oSQLParams = new ArrayList<Param>();
							Node oProcedureNode = (new IgnoreEmptyNodeList(chartChildNode.getChildNodes())).item(0);
							if (chartChildNode.hasChildNodes() && oProcedureNode.hasChildNodes()) {
								oSQLProcedure.setName(oProcedureNode.getAttributes().getNamedItem("Name") != null
										? oProcedureNode.getAttributes().getNamedItem("Name").getNodeValue().trim()
										: "");
								if (oSQLProcedure.getName().equals(""))
									return null;

								NodeList sqlParams = new IgnoreEmptyNodeList(oProcedureNode.getChildNodes());// SelectNodes("Param");
								for (Node oSQLParam : XmlUtil.asList(sqlParams)) {
									oSQLParams.add(new Param(
											oSQLParam.getAttributes().getNamedItem("Name") != null
													? oSQLParam.getAttributes().getNamedItem("Name").getNodeValue()
															.trim()
													: "Param_dummy",
											oSQLParam.getAttributes().getNamedItem("Value") != null
													? oSQLParam.getAttributes().getNamedItem("Value").getNodeValue()
															.trim()
													: "",
											oSQLParam.getAttributes().getNamedItem("IsStatic") != null
													? Boolean.getBoolean(oSQLParam.getAttributes()
															.getNamedItem("IsStatic").getNodeValue().trim())
													: false));
								}
								oSQLProcedure.setParams(oSQLParams);
								oDataSource.setProcedure(oSQLProcedure);
							}
							oChart.setDataSource(oDataSource);
							break;
						case SQLInline:
							oDataSource.setSourceType(SourceType.SQLInline);
							SQLInline oSQLInline = new SQLInline();
							String sql = "";
							NodeList oNodeList = new IgnoreEmptyNodeList(oSourceTypeNode.getChildNodes());
							for (Node oSQLNode : XmlUtil.asList(oNodeList)) {
								if (oSQLNode.getNodeName().trim().toUpperCase() == "SQL") {
									sql = oSQLNode.getTextContent().trim();
									break;
								}
							}
							oSQLInline.setSql(sql);
							oDataSource.setInline(oSQLInline);
							oReportControl.setDataSource(oDataSource);
							if (oChart.getType() != null || oChart.getType() == ChartType.PIE)
								oChart.setChartData(oDataManager.GetChartData4Pie(oChartData,oSQLInline.getSql()));
							else
								oChart.setChartData(oDataManager.GetChartData(oChartData,oSQLInline.getSql()));
							break;
						case SQLFunction:
							// yet need to implement
							break;
						default:
							break;
						}
						break;
					}

				}
				oChart.setNodes(nodes);
				charts.add(oChart);
			}
			oWidget.setCharts(charts);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		return oWidget;
	}

	public String initWidget() {

		String dboard = "", wgt = "";
		String renderTo = null;

		if (getRequest().getParameter("dboard") != null)
			dboard = getRequest().getParameter("dboard").trim();
		if (getRequest().getParameter("wgt") != null)
			wgt = getRequest().getParameter("wgt").trim();
		if (getRequest().getParameter("renderTo") != null)
			renderTo = getRequest().getParameter("renderTo").trim();

		Widget oWidget = new Widget();

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = null;
		try {

			builder = builderFactory.newDocumentBuilder();
			Document xdoc = builder.parse(new FileInputStream(
					this.getSession().getServletContext().getRealPath("/views/dashboard/Dashboards.xml")));
			XPath xPath = XPathFactory.newInstance().newXPath();
			Node widgetNode = (Node) xPath
					.compile("//Dashboards/Dashboard[@Id='" + dboard + "']/Widgets/Widget[@Id='" + wgt + "']")
					.evaluate(xdoc, XPathConstants.NODE);
			if (widgetNode != null) {
				oWidget = _initWidget(widgetNode, xdoc, xPath);
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
		} finally {
			this.getRequest().setAttribute("oWidget", oWidget);
			this.getRequest().setAttribute("renderTo", renderTo);
		}
		return SUCCESS;
	}
	
	public String initWidgetProxy() {
		String widgetHtml="";
		String dboard = "",wgt="";
		String renderTo = "";

		if (getRequest().getParameter("dboard") != null)
			dboard = getRequest().getParameter("dboard").trim();
		if (getRequest().getParameter("wgt") != null)
			wgt = getRequest().getParameter("wgt").trim();
		if (getRequest().getParameter("renderTo") != null)
			renderTo = getRequest().getParameter("renderTo").trim();
		
		renderTo = renderTo+"_proxy";

		widgetHtml+="<div class=\"anet-widget-proxy row-fluid\"><div class=\"span12\">";
		widgetHtml+="<div class=\"anet-widget\" anet-id='"+renderTo+"' anet-dashboard='"+dboard+"' anet-widget='"+wgt+"'></div>";
		widgetHtml+="</div></div>";
		
		this.getRequest().setAttribute("widgetHtml", widgetHtml);
		
		return SUCCESS;
	}
	
	
	
	public String initDataGrid() {
		String dboard = "", wgt = "";
		String renderTo = null;
		String html = "";

		if (getRequest().getParameter("dboard") != null)
			dboard = getRequest().getParameter("dboard").trim();
		if (getRequest().getParameter("wgt") != null)
			wgt = getRequest().getParameter("wgt").trim();
		if (getRequest().getParameter("renderTo") != null)
			renderTo = getRequest().getParameter("renderTo").trim();

		DataManager oDataManager = null;
		DataSource oDataSource = new DataSource();
		String sql = "";
		ReportControl oReportControl = new ReportControl();

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = null;
		try {

			builder = builderFactory.newDocumentBuilder();
			Document xdoc = builder.parse(new FileInputStream(
					this.getSession().getServletContext().getRealPath("/views/dashboard/Dashboards.xml")));
			XPath xPath = XPathFactory.newInstance().newXPath();
			Node dsNode = (Node) xPath.compile("//Dashboards/Dashboard[@Id='" + dboard + "']/Widgets/Widget[@Id='" + wgt
					+ "']/HighChart/DataSource").evaluate(xdoc, XPathConstants.NODE);
			if (dsNode != null) {
				oDataSource.setConnectionName((dsNode.getAttributes().getNamedItem("ConnectionName") != null)
						? dsNode.getAttributes().getNamedItem("ConnectionName").getNodeValue().trim()
						: "");
				if (!oDataSource.getConnectionName().equals("")) {
					Node oDBConfigNode = (Node) xPath
							.compile("//DBConfigs/DBConfig[@Name='" + oDataSource.getConnectionName() + "']")
							.evaluate(xdoc, XPathConstants.NODE);
					DBConfig oDBConfig = new DBConfig();
					oDBConfig.setName(oDataSource.getConnectionName());
					oDBConfig.setDbServer(
							DBServer.valueOf(oDBConfigNode.getAttributes().getNamedItem("DBServer").getNodeValue()));
					oDBConfig.setConString(oDBConfigNode.getAttributes().getNamedItem("ConString").getNodeValue());
					oDBConfig.setDriver(oDBConfigNode.getAttributes().getNamedItem("Driver").getNodeValue());
					oDBConfig.setUser(oDBConfigNode.getAttributes().getNamedItem("User").getNodeValue());
					oDBConfig.setPwd(oDBConfigNode.getAttributes().getNamedItem("PWD").getNodeValue());
					oDBConfig.setPWDEncrypt(oDBConfigNode.getAttributes().getNamedItem("PWDEncrypt") != null
							? Boolean.getBoolean(oDBConfigNode.getAttributes().getNamedItem("PWDEncrypt").getNodeValue()
									.trim().toUpperCase())
							: false);

					oDataManager = new DataManager(oDBConfig);
				} else
					oDataManager = new DataManager();

				oDataSource.setSourceType(
						SourceType.valueOf(dsNode.getAttributes().getNamedItem("SourceType").getNodeValue().trim()));
				Node oSourceTypeNode = (new IgnoreEmptyNodeList(dsNode.getChildNodes())).item(0);

				switch (oDataSource.getSourceType()) {
				case SQLView:
					oDataSource.setView(
							new SQLView(oSourceTypeNode.getAttributes().getNamedItem("Name").getNodeValue().trim()));
					oReportControl.setDataSource(oDataSource);
					sql = QueryBuilder.BuildQuery(oSourceTypeNode, oReportControl, oDataSource, oDataManager);
					html = oDataManager.GetHtmlTable(sql);
					break;
				case SQLTable:
					oDataSource.setTable(
							new SQLTable(oSourceTypeNode.getAttributes().getNamedItem("Name").getNodeValue().trim()));
					oReportControl.setDataSource(oDataSource);
					sql = QueryBuilder.BuildQuery(oSourceTypeNode, oReportControl, oDataSource, oDataManager);
					html = oDataManager.GetHtmlTable(sql);
					break;
				case SQLProcedure:
					oDataSource.setSourceType(SourceType.SQLProcedure);
					SQLProcedure oSQLProcedure = new SQLProcedure();
					List<Param> oSQLParams = new ArrayList<Param>();
					Node oProcedureNode = (new IgnoreEmptyNodeList(dsNode.getChildNodes())).item(0);
					if (dsNode.hasChildNodes() && oProcedureNode.hasChildNodes()) {
						oSQLProcedure.setName(oProcedureNode.getAttributes().getNamedItem("Name") != null
								? oProcedureNode.getAttributes().getNamedItem("Name").getNodeValue().trim()
								: "");
						if (oSQLProcedure.getName().equals(""))
							return null;

						NodeList sqlParams = new IgnoreEmptyNodeList(oProcedureNode.getChildNodes());// SelectNodes("Param");
						for (Node oSQLParam : XmlUtil.asList(sqlParams)) {
							oSQLParams
									.add(new Param(
											oSQLParam.getAttributes().getNamedItem("Name") != null
													? oSQLParam.getAttributes().getNamedItem("Name").getNodeValue()
															.trim()
													: "Param_dummy",
											oSQLParam.getAttributes().getNamedItem("Value") != null ? oSQLParam
													.getAttributes().getNamedItem("Value").getNodeValue().trim() : "",
											oSQLParam.getAttributes().getNamedItem("IsStatic") != null
													? Boolean.getBoolean(oSQLParam.getAttributes()
															.getNamedItem("IsStatic").getNodeValue().trim())
													: false));
						}
						oSQLProcedure.setParams(oSQLParams);
						oDataSource.setProcedure(oSQLProcedure);
					}
					break;
				case SQLInline:
					oDataSource.setSourceType(SourceType.SQLInline);
					SQLInline oSQLInline = new SQLInline();
					NodeList oNodeList = new IgnoreEmptyNodeList(oSourceTypeNode.getChildNodes());
					for (Node oSQLNode : XmlUtil.asList(oNodeList)) {
						if (oSQLNode.getNodeName().trim().toUpperCase() == "SQL") {
							sql = oSQLNode.getTextContent().trim();
							break;
						}
					}
					oSQLInline.setSql(sql);
					oDataSource.setInline(oSQLInline);
					oReportControl.setDataSource(oDataSource);
					html = oDataManager.GetHtmlTable(oSQLInline.getSql());
					break;
				case SQLFunction:
					// yet need to implement
					break;
				default:
					break;
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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.getRequest().setAttribute("tableHTML", html);
			this.getRequest().setAttribute("renderTo", renderTo);
		}

		return SUCCESS;
	}
	public String loadDashboard() {
		String dbrd = "";

		if (getRequest().getParameter("rpt") != null)
			dbrd = getRequest().getParameter("rpt").trim();

		getRequest().setAttribute("dbrd", dbrd);

		Dashboard oDashboard = new Dashboard();
		oDashboard.setId(dbrd);
		//List<Widget> widgets = new ArrayList<Widget>();

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();
			Document xdoc = builder.parse(new FileInputStream(
					this.getSession().getServletContext().getRealPath("/views/dashboard/Dashboards.xml")));
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList oXmlNodeList = (NodeList) xPath.compile("//Dashboards/Dashboard[@Id='" + oDashboard.getId() + "']")
					.evaluate(xdoc, XPathConstants.NODESET);
			if (oXmlNodeList.getLength() > 0) {
				if (oXmlNodeList.item(0).getAttributes().getNamedItem("Title") != null)
					oDashboard.setTitle(oXmlNodeList.item(0).getAttributes().getNamedItem("Title").getNodeValue().trim());
				if (oXmlNodeList.item(0).getAttributes().getNamedItem("Description") != null)
					oDashboard.setDescription(
							oXmlNodeList.item(0).getAttributes().getNamedItem("Description").getNodeValue().trim());

				for (Node oXmlNode : XmlUtil.asList(new IgnoreEmptyNodeList(oXmlNodeList.item(0).getChildNodes()))) {
					if (oXmlNode.getNodeName().equalsIgnoreCase("Layout")) {
						oDashboard.setLayout(oXmlNode.getTextContent());// = oXmlNode.InnerText;
						continue;
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
		} finally {
			this.getRequest().setAttribute("objDashboard", oDashboard);
		}
		return SUCCESS;
	}
	public String loadDashboardProxy() {
		String dboard = "";

		if (getRequest().getParameter("dboard") != null)
			dboard = getRequest().getParameter("dboard").trim();

		
		Dashboard oDashboard = new Dashboard();
		oDashboard.setId(dboard);

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();
			Document xdoc = builder.parse(new FileInputStream(
					this.getSession().getServletContext().getRealPath("/views/dashboard/Dashboards.xml")));
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList oXmlNodeList = (NodeList) xPath.compile("//Dashboards/Dashboard[@Id='" + oDashboard.getId() + "']")
					.evaluate(xdoc, XPathConstants.NODESET);
			if (oXmlNodeList.getLength() > 0) {
				if (oXmlNodeList.item(0).getAttributes().getNamedItem("Title") != null)
					oDashboard.setTitle(oXmlNodeList.item(0).getAttributes().getNamedItem("Title").getNodeValue().trim());
				if (oXmlNodeList.item(0).getAttributes().getNamedItem("Description") != null)
					oDashboard.setDescription(
							oXmlNodeList.item(0).getAttributes().getNamedItem("Description").getNodeValue().trim());

				for (Node oXmlNode : XmlUtil.asList(new IgnoreEmptyNodeList(oXmlNodeList.item(0).getChildNodes()))) {
					if (oXmlNode.getNodeName().equalsIgnoreCase("Layout")) {
						oDashboard.setLayout(oXmlNode.getTextContent());// = oXmlNode.InnerText;
						continue;
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
		} finally {
			this.getRequest().setAttribute("objDashboard", oDashboard);
		}
		return SUCCESS;
	}
	public String loadDashboardAll() {
		String dbrd = "";

		if (getRequest().getParameter("rpt") != null)
			dbrd = getRequest().getParameter("rpt").trim();

		getRequest().setAttribute("dbrd", dbrd);

		Dashboard oDashboard = new Dashboard();
		// dbrd="widget1";
		oDashboard.setId(dbrd);
		List<Widget> widgets = new ArrayList<Widget>();

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();
			Document xdoc = builder.parse(new FileInputStream(
					this.getSession().getServletContext().getRealPath("/views/dashboard/Dashboards.xml")));
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList oXmlNodeList = (NodeList) xPath.compile("//Dashboards/Dashboard[@Id='" + oDashboard.getId() + "']")
					.evaluate(xdoc, XPathConstants.NODESET);
			if (oXmlNodeList.getLength() > 0) {
				if (oXmlNodeList.item(0).getAttributes().getNamedItem("Title") != null)
					oDashboard
							.setTitle(oXmlNodeList.item(0).getAttributes().getNamedItem("Title").getNodeValue().trim());
				if (oXmlNodeList.item(0).getAttributes().getNamedItem("Description") != null)
					oDashboard.setDescription(
							oXmlNodeList.item(0).getAttributes().getNamedItem("Description").getNodeValue().trim());

				for (Node oXmlNode : XmlUtil.asList(new IgnoreEmptyNodeList(oXmlNodeList.item(0).getChildNodes()))) {
					if (oXmlNode.getNodeName().equalsIgnoreCase("Layout")) {
						oDashboard.setLayout(oXmlNode.getTextContent());// = oXmlNode.InnerText;
						continue;
					}
					if (oXmlNode.getNodeName().equalsIgnoreCase("Widgets")) {
						if (oXmlNode.hasChildNodes()) {
							for (Node widgetNode : XmlUtil.asList(new IgnoreEmptyNodeList(oXmlNode.getChildNodes()))) {
								widgets.add(_initWidget(widgetNode, xdoc, xPath));
							}
							oDashboard.setWidgets(widgets);
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
		} finally {
			this.getRequest().setAttribute("objDashboard", oDashboard);
		}
		return SUCCESS;
	}
}

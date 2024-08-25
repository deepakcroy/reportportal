package net.aditri.ui.common;

import java.sql.SQLException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.aditri.dao.DataManager;
import net.aditri.ui.report.ReportControl;
import net.aditri.util.IgnoreEmptyNodeList;
import net.aditri.util.XmlUtil;

public class QueryBuilder {

	public static String BuildQuery(Node oSourceTypeNode, ReportControl oReportControl, DataSource oDataSource, DataManager oDataManager,String filterValue) throws SQLException
    {
		String sql = "";
        String where = "";
        String orderBy = "";
        String groupBy = "";
        
        if (oSourceTypeNode.hasChildNodes())
        {
            //find Where          
        	NodeList oWhereNodeList= new IgnoreEmptyNodeList(oSourceTypeNode.getChildNodes());
            for (Node oWhereNode: XmlUtil.asList(oWhereNodeList))
            {
                switch (oWhereNode.getNodeName().trim().toUpperCase())
                {
                	case "WHERE":
                    if (filterValue != null)
                    {
                        if (oWhereNode.getAttributes().getNamedItem("Field") != null && oWhereNode.getAttributes().getNamedItem("Condition") != null)
                        {
                            String _condition = "=";
                            String _foundCondition = oWhereNode.getAttributes().getNamedItem("Condition").getNodeValue().trim().toUpperCase();
                            if (filterValue == "_RC_EMPTY_")
                                _foundCondition = "NE";
                            switch (_foundCondition)
                            {
                                case "EQ":
                                    _condition = "=";
                                    break;
                                case "NE":
                                    _condition = "<>";
                                    break;
                                case "GT":
                                    _condition = ">";
                                    break;
                                case "LT":
                                    _condition = "<";
                                    break;
                                case "GTE":
                                    _condition = ">=";
                                    break;
                                case "LTE":
                                    _condition = "<=";
                                    break;
                                case "IN":
                                    _condition = "IN";
                                    break;
                            }
                            if(_condition!="IN")
                                where = " " + oWhereNode.getAttributes().getNamedItem("Field").getNodeValue().trim() + _condition +" '"+filterValue+"'";
                            else
                                where = " " + oWhereNode.getAttributes().getNamedItem("Field").getNodeValue().trim() + " "+_condition + "(" + filterValue + ")";
                        }
                    }
                    where = (oWhereNode.getTextContent().trim() == "") ? where : oWhereNode.getTextContent().trim() + where;
                    break;
                	case "SELECT":
                		switch (oReportControl.getDataSource().getSourceType())
                        {
                		case SQLView:
                			sql = "SELECT "+oWhereNode.getTextContent().trim() + " FROM "+oReportControl.getDataSource().getView().getName()+" ";
                			break;
                		case SQLTable:
                			sql = "SELECT "+oWhereNode.getTextContent().trim() + " FROM "+oReportControl.getDataSource().getTable().getName()+" ";
                			break;
                		//case SQLInline:
                			//sql = "SELECT "+oWhereNode.getTextContent().trim() + " FROM "+oReportControl.getDataSource().getTable().getName()+" ";
                			//break;
            			default:
            				sql = "SELECT "+oWhereNode.getTextContent().trim() + " FROM "+oReportControl.getDataSource().getView().getName()+" ";
            				break;
                        }
                	break;
                }
            }
        }

        if (oSourceTypeNode.getAttributes().getNamedItem("OrderBy") != null)
        {
            orderBy = oSourceTypeNode.getAttributes().getNamedItem("OrderBy").getNodeValue();
        }
        if (oSourceTypeNode.getAttributes().getNamedItem("GroupBy") != null)
        {
            groupBy = oSourceTypeNode.getAttributes().getNamedItem("GroupBy").getNodeValue();
        }
        if(sql!="")
        	sql = sql + " WHERE 1=1 " + (where != "" ? " AND " + where : "") + (groupBy != "" ? " GROUP BY " + groupBy : "") + (orderBy != "" ? " ORDER BY " + orderBy : "");
        else
        {
        	where = " WHERE 1=1 " + (where != "" ? " AND " + where : "") + (groupBy != "" ? " GROUP BY " + groupBy : "") + (orderBy != "" ? " ORDER BY " + orderBy : "");
            switch (oReportControl.getDataSource().getSourceType())
            {
                case SQLView:
                	sql = "SELECT " + (orderBy != "" ? "" : " DISTINCT ") + oReportControl.getNameField() + "," + oReportControl.getValueField() + " FROM " + oReportControl.getDataSource().getView().getName() + where;
                    break;
                case SQLTable:
                	sql = "SELECT " + (orderBy != "" ? "" : " DISTINCT ") + oReportControl.getNameField() + "," + oReportControl.getValueField() + " FROM " + oReportControl.getDataSource().getTable().getName() + where;
                    break;
                case SQLProcedure:
                	sql = oReportControl.getDataSource().getProcedure().getName();
                    break;
                case SQLInline:
                	sql = "SELECT " + (orderBy != "" ? "" : " DISTINCT ") + oReportControl.getNameField() + "," + oReportControl.getValueField() + " FROM (" + oReportControl.getDataSource().getInline().getSql() + ") "+ where;
                    break;
                default:
                	sql = "SELECT " + (orderBy != "" ? "" : " DISTINCT ") + oReportControl.getNameField() + "," + oReportControl.getValueField() + " FROM " + oReportControl.getDataSource().getTable().getName() + where;
                	break;
            }
        }
        System.out.println(sql);
        return sql;//oDataManager.GetReportControlValue(oReportControl, where, orderBy);
    }
    public static String BuildQuery(Node oSourceTypeNode, ReportControl oReportControl, DataSource oReportDataSource, DataManager oDataManager) throws SQLException
    {
        return BuildQuery(oSourceTypeNode, oReportControl, oReportDataSource, oDataManager,null);
    }
    public static String GetReportGateway(String reportId, Document xdoc)
    {
        String str = "";
        try
        {
           XPath xPath =  XPathFactory.newInstance().newXPath();
		   NodeList gateways = (NodeList) xPath.compile("//Forms/Form[@ReportId='" + reportId + "']/ReportGateway/Gateway").evaluate(xdoc, XPathConstants.NODESET);
           int Count = 0;
           boolean hasElse = false;
           if (gateways.getLength() > 0)
           {
                for (Node gateway:XmlUtil.asList(gateways))
                {
                    hasElse = false;
                    Count++;
                    if(Count==1)
                        str += "if( ";
                    else if (Count == gateways.getLength())
                    {
                    	 if(gateway.getAttributes().getNamedItem("Otherwise") != null)
                         {
                             str += "else";
                             hasElse = true;
                         }
                         else
                             str += "else if( ";
                    }
                    else
                        str += "else if( ";

                    NodeList groups = new IgnoreEmptyNodeList(gateway.getChildNodes());
                    for(Node group:XmlUtil.asList(groups))
                    {
                        if (group.getChildNodes().getLength() > 0)
                        {
                            str += "(";
                            for (Node ctrl: XmlUtil.asList(group.getChildNodes()))
                            {
                                if (ctrl.getAttributes().getNamedItem("Id")== null || ctrl.getAttributes().getNamedItem("Value") == null || ctrl.getAttributes().getNamedItem("Type") == null )
                                {
                                    return "Error: Section: [ReportGateWay], Form: ["+reportId+"]";
                                }

                                String _relation = "";
                                if (ctrl.getAttributes().getNamedItem("Join") != null)
                                    _relation = ctrl.getAttributes().getNamedItem("Join").getNodeValue().trim().toUpperCase().equals("AND")? "&&" : "||";

                                String _condition="==";
                                if(ctrl.getAttributes().getNamedItem("Condition")!= null)
                                {
                                    switch (ctrl.getAttributes().getNamedItem("Condition").getNodeValue().trim().toUpperCase())
                                    {
                                        case "EQ":
                                            _condition="==";
                                            break;
                                        case "NE":
                                            _condition = "!=";
                                            break;
                                        case "GT":
                                            _condition = ">";
                                            break;
                                        case "LT":
                                            _condition = "<";
                                            break;
                                        case "GTE":
                                            _condition = ">=";
                                            break;
                                        case "LTE":
                                            _condition = "<=";
                                            break;
                                    }
                                }
                                
                                
                                switch (ctrl.getAttributes().getNamedItem("Type").getNodeValue().trim().toUpperCase())
                                {
                                    case "COMBOBOX":
                                        str += " $('#" + ctrl.getAttributes().getNamedItem("Id").getNodeValue().trim() + "').val()" + _condition + "'" + ctrl.getAttributes().getNamedItem("Value").getNodeValue().trim() + "' " + _relation + " ";
                                        break;
                                    case "TEXTBOX":
                                        break;
                                    case "CHECKBOX":
                                        str += " $('#" + ctrl.getAttributes().getNamedItem("Id").getNodeValue().trim() + "').is(':checked')==true " + " " + _relation + " ";
                                        break;
                                    case "RADIO":
                                        str += " $('#" + ctrl.getAttributes().getNamedItem("Id").getNodeValue().trim() + "').attr('checked')==true " + " " + _relation + " ";
                                        break;
                                }
                            }
                            str += ") " + (group.getAttributes().getNamedItem("Join")!=null?group.getAttributes().getNamedItem("Join").getNodeValue().trim().toUpperCase()=="AND"?"&&":"||":"")+" ";
                        }
                    }
                    if (hasElse)
                        str += "{";
                    else
                        str += "){";

                    str += "_procedureName='" + gateway.getAttributes().getNamedItem("ProcedureName").getNodeValue().trim()+"';";
                    str += "_reportName='" + gateway.getAttributes().getNamedItem("ReportName").getNodeValue().trim()+"';";
                    str += "}";
                }

                return str;
            }
            else
                return "";
        }
        catch (XPathExpressionException e) {
			e.printStackTrace();
		}
        return str;
    }
}

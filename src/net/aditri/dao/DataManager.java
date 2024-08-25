package net.aditri.dao;

import net.aditri.ui.common.DBConfig;
import net.aditri.ui.common.Enum.ControlType;
import net.aditri.ui.common.Enum.FieldSelectionType;
import net.aditri.ui.common.Enum.SourceType;
import net.aditri.ui.dashboard.ChartData;
import net.aditri.ui.report.ReportControl;
import net.aditri.util.StringUtil;
import net.aditri.web.utility.StringHelper;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;


import com.google.common.io.BaseEncoding;


public class DataManager {
	
	private String connectionString;
    private Connection connection;
    private String commandText;
    
    private static String passPharase="a-di-tr-i-net"; //For database password encryption
    /**
     * 
     * @param encryptedPWD
     * @return
     */
    public static String EncryptDBPWD(String origPWD) {
    	String dbPWD = "";
    	try {
    		dbPWD = BaseEncoding.base64().encode(origPWD.getBytes("UTF-8"))+BaseEncoding.base64().encode(passPharase.getBytes("UTF-8"));
    		dbPWD = BaseEncoding.base64().encode(dbPWD.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return dbPWD;
    }
    private String DecryptDBPWD(String encryptedPWD) {
    	String dbPWD="";
    	String encryptedPassPharase="";
    	encryptedPassPharase = BaseEncoding.base64().encode(passPharase.getBytes());
    	dbPWD = new String(BaseEncoding.base64().decode(encryptedPWD));
    	dbPWD = dbPWD.replace(encryptedPassPharase, "");
    	dbPWD = new String(BaseEncoding.base64().decode(dbPWD));
    	return dbPWD;
    }
	public DataManager()
	{
		Properties prop = new Properties();
		this.setConnectionString(prop.getProperty("database.connection.url"));
		try {
			if(this.connection==null)
			{
				Class.forName("database.connection.driver");
				connection = DriverManager.getConnection(this.getConnectionString(),prop.getProperty("database.connection.user"),prop.getProperty("database.connection.pwd"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
	}
	public DataManager(DBConfig oDBConfig)
	{
		this.setConnectionString(oDBConfig.getConString());
		if(oDBConfig.isPWDEncrypt()) {
			oDBConfig.setPwd(this.DecryptDBPWD(oDBConfig.getPwd()));
		}
		try
		{
			switch(oDBConfig.getDbServer())
			{
			case ORACLE:
				if(connection==null)
				{
					Class.forName(oDBConfig.getDriver());
					connection = DriverManager.getConnection(this.getConnectionString(),oDBConfig.getUser(),oDBConfig.getPwd());
				}
				break;
			case MYSQL:
				if(connection==null)
				{
					Class.forName(oDBConfig.getDriver());
					connection = DriverManager.getConnection(this.getConnectionString(),oDBConfig.getUser(),oDBConfig.getPwd());
				}
				break;
			default:
				if(connection==null)
				{
					Class.forName(oDBConfig.getDriver());
					connection = DriverManager.getConnection(this.getConnectionString(),oDBConfig.getUser(),oDBConfig.getPwd());
				}
				break;
			}
			
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public DataManager(String conString, boolean byConnectionName) //for connection name or
	{
		this.setConnectionString("jdbc:oracle:thin:@localhost:1521:orcl");
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(this.getConnectionString(),"REPORTDB","821216");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getPassPharase() {
		return passPharase;
	}
	
	public String getConnectionString() {
		return connectionString;
	}
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}
	
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public String getCommandText() {
		return commandText;
	}
	public void setCommandText(String commandText) {
		this.commandText = commandText;
	}
	
	public String GetReportControlValue(ReportControl oReportControl) throws SQLException
	{
		return GetReportControlValue(oReportControl,  "");
	}
	public String GetReportControlValue(ReportControl oReportControl, String sql) throws SQLException
	{
		String str = "";
        Statement statement=null;
		ResultSet rs=null;
		
        this.commandText = "";

        /*if (oReportControl.isAllowDefaultFilter())
        {
            _where = " WHERE DATAAREAID='" + dataArea + "'" + (where != "" ? " AND " + where : "") + (orderBy != "" ? " ORDER BY " + orderBy : "");
        }
        else // No DataAreaId Filter
        {
            _where = " WHERE 1=1 " + (where != "" ? " AND " + where : "") + (orderBy != "" ? " ORDER BY " + orderBy : "");
        }*/
        /*_where = " WHERE 1=1 " + (where != "" ? " AND " + where : "") + (orderBy != "" ? " ORDER BY " + orderBy : "");
        switch (oReportControl.getDataSource().getSourceType())
        {
            case SQLView:
            	this.commandText = "SELECT " + (orderBy != "" ? "" : " DISTINCT ") + oReportControl.getNameField() + "," + oReportControl.getValueField() + " FROM " + oReportControl.getDataSource().getView().getName() + _where;
                break;
            case SQLTable:
            	this.commandText = "SELECT " + (orderBy != "" ? "" : " DISTINCT ") + oReportControl.getNameField() + "," + oReportControl.getValueField() + " FROM " + oReportControl.getDataSource().getTable().getName() + _where;
                break;
            case SQLProcedure:
            	this.commandText = oReportControl.getDataSource().getProcedure().getName();
                break;
            default:
            	this.commandText = "SELECT " + (orderBy != "" ? "" : " DISTINCT ") + oReportControl.getNameField() + "," + oReportControl.getValueField() + " FROM " + oReportControl.getDataSource().getTable().getName() + _where;
            	break;
        }*/
        switch (oReportControl.getDataSource().getSourceType())
        {
            case SQLView:
            	this.commandText = sql;
                break;
            case SQLTable:
            	this.commandText = sql;
                break;
            case SQLProcedure:
            	this.commandText = oReportControl.getDataSource().getProcedure().getName();
                break;
            default:
            	this.commandText = sql;
            	break;
        }
        
        
		try {
			
			 if (oReportControl.getDataSource().getSourceType() == SourceType.SQLProcedure)
             {
				 /*cmd.CommandType = CommandType.StoredProcedure;
                 cmd.Parameters.Clear();
                 foreach (Param oParam in oReportControl.DataSource.Procedure.Params)
                 {
                     cmd.Parameters.Add("@" + oParam.Name, SqlDbType.NVarChar).Value = oParam.Value;
                 }
                 //All procedures need to have dataarea id as parameter
                 cmd.Parameters.Add("@DATAAREAID", SqlDbType.NVarChar).Value = dataAreaId;*/
             }
			statement = connection.createStatement();
			
			rs = statement.executeQuery(commandText);
			if (oReportControl.getNameField().toUpperCase().trim().equals(oReportControl.getValueField().toUpperCase().trim()))
            {
				if(oReportControl.getControlType().equals(ControlType.DropDown) && oReportControl.isAllowAll()==true)
                    str = "<option title='All' value=''>All</option>";
				
				if (!oReportControl.getDefaultValue().equals(""))
                {
					while (rs.next()) {
						if (oReportControl.getDefaultValue().equalsIgnoreCase(rs.getString(oReportControl.getValueField())))
                            str += "<option value='" + rs.getString(oReportControl.getValueField()) + "' title='" + rs.getString(oReportControl.getValueField()) + "' Selected>" + rs.getString(oReportControl.getValueField()) + " </option>";
                        else
                            str += "<option value='" + rs.getString(oReportControl.getValueField()) + "' title='" + rs.getString(oReportControl.getValueField()) + "'>" + rs.getString(oReportControl.getValueField()) + " </option>";
					}
                }
				else
				{
					if (oReportControl.isDefaultValueAsTop1())
                    {
                        int count = 0;
                        while (rs.next())
                        {
                            if (count==0)
                                str += "<option value='" + rs.getString(oReportControl.getValueField()) + "' title='" + rs.getString(oReportControl.getValueField()) + "' Selected>" + rs.getString(oReportControl.getValueField()) + " </option>";
                            else
                                str += "<option value='" + rs.getString(oReportControl.getValueField()) + "' title='" + rs.getString(oReportControl.getValueField()) + "'>" + rs.getString(oReportControl.getValueField()) + " </option>";
                            count++;
                        }
                    }
                    else
                    {
                    	while (rs.next())
                        {
                            str += "<option value='" + rs.getString(oReportControl.getValueField()) + "' title='" + rs.getString(oReportControl.getValueField()) + "'>" + rs.getString(oReportControl.getValueField()) + " </option>";
                        }
                    }
				}
            }
			else
			{
				if (oReportControl.getControlType().equals(ControlType.DropDown)  && oReportControl.isAllowAll() == true)
                    str = "<option title='All' value=''>All</option>";
				
				if (!oReportControl.getDefaultValue().equals(""))
                {
					while (rs.next())
                    {
                        if (oReportControl.getDefaultValue().trim().equalsIgnoreCase(rs.getString(oReportControl.getValueField())))
                            str += "<option value='" + rs.getString(oReportControl.getValueField()) + "' title='" + rs.getString(oReportControl.getValueField()) + " (" + rs.getString(oReportControl.getNameField()) + ")' Selected>" + rs.getString(oReportControl.getValueField()) + " (" + rs.getString(oReportControl.getNameField()) + ") </option>";
                        else
                            str += "<option value='" + rs.getString(oReportControl.getValueField()) + "' title='" + rs.getString(oReportControl.getValueField()) + " (" + rs.getString(oReportControl.getNameField()) + ")'>" + rs.getString(oReportControl.getValueField()) + " (" + rs.getString(oReportControl.getNameField()) + ") </option>";
                    }
                }
				else
				{
					if (oReportControl.isDefaultValueAsTop1())
                    {
                        int count = 0;
                        while (rs.next())
                        {
                            if(count==0)
                                str += "<option value='" + rs.getString(oReportControl.getValueField()) + "' title='" + rs.getString(oReportControl.getValueField()) + " (" + rs.getString(oReportControl.getNameField()) + ")' Selected>" + rs.getString(oReportControl.getValueField()) + " (" + rs.getString(oReportControl.getNameField()) + ") </option>";
                            else
                                str += "<option value='" + rs.getString(oReportControl.getValueField()) + "' title='" + rs.getString(oReportControl.getValueField()) + " (" + rs.getString(oReportControl.getNameField()) + ")'>" + rs.getString(oReportControl.getValueField()) + " (" + rs.getString(oReportControl.getNameField()) + ") </option>";
                        }
                    }
                    else
                    {
                    	while (rs.next())
                        {
                            str += "<option value='" + rs.getString(oReportControl.getValueField()) + "' title='" + rs.getString(oReportControl.getValueField()) + " (" + rs.getString(oReportControl.getNameField()) + ")'>" + rs.getString(oReportControl.getValueField()) + " (" + rs.getString(oReportControl.getNameField()) + ") </option>";
                        }
                    }
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        finally
        {
        	if (statement != null)
				statement.close();
			if (connection != null) 
				connection.close();
			if(!rs.isClosed())
				rs.close();
        }
        return str;
	}
	public String GetReportControlDefaultValue(ReportControl oReportControl)
	{
		/*string str = "";
        CommandText = "";
        switch (oReportControl.DataSource.SourceType)
        {
            case SourceType.SQLView:
                break;
            case SourceType.SQLTable:
                break;
            case SourceType.SQLProcedure:
                break;
            case SourceType.SQLFunction:
                if (oReportControl.DataSource.Function.ReturnAs != "")
                    CommandText = "SELECT " + oReportControl.DataSource.Function.Name + " ('" + dataAreaId + "') AS " + oReportControl.DataSource.Function.ReturnAs;
                else
                    CommandText = "SELECT " + oReportControl.DataSource.Function.Name + " ('" + dataAreaId + "')";
                break;
        }
        try
        {
            Open();
            using (SqlCommand cmd = new SqlCommand())
            {
                cmd.Connection = Connection;
                cmd.CommandText = CommandText;
                


                DataTable datatable = new DataTable();
                SqlDataAdapter adapter = new SqlDataAdapter(cmd);
                adapter.Fill(datatable);
                if(datatable.Rows.Count>0)
                {
                    str = datatable.Rows[0][oReportControl.DataSource.Function.ReturnAs].ToString();
                }
                
                adapter.Dispose();
                cmd.Dispose();
                datatable.Dispose();

            }
            if (IsOpen)
            {
                Close();
            }
        }
        catch (SqlException sqlEx)
        {

        }
        catch (Exception ex)
        {
        }
        finally
        {
        }
        return str;*/
		return "";
	}
	
	public ChartData  GetChartData(ChartData oChartData,String sql) throws SQLException
	{
		
        Statement statement=null;
		ResultSet rs=null;
		/*if (oSourceTypeNode.hasChildNodes())
        {
			NodeList oWhereNodeList= new IgnoreEmptyNodeList(oSourceTypeNode.getChildNodes());
            for (Node oWhereNode: XmlUtil.asList(oWhereNodeList))
            {
            	switch (oWhereNode.getNodeName().trim().toUpperCase())
                {
	                case "SELECT":
	                	selList= new ArrayList<String>( Arrays.asList(oWhereNode.getTextContent().trim().split(",")));
	            	break;
                }
            }
        }*/
        this.commandText = sql;
        try {
    		statement = connection.createStatement();
			String xAxis="";
			String seriesColumn="";
			rs = statement.executeQuery(commandText);
			ResultSetMetaData rsmd = rs.getMetaData();
			int sColumn=0;
			if(oChartData.getSeriesColumn()!=null)
				sColumn=oChartData.getSeriesColumn().length;
			else
				sColumn = rsmd.getColumnCount()-1; 
			
			String sc[] = new String[sColumn];
			int j=0;
			while(rs.next())
			{
				for(int i=1;i<=rsmd.getColumnCount();i++)
				{
					//System.out.println(rsmd.getColumnName(i));
					sc[j]=(sc[j]!=null)?sc[j]:"";
					if(rsmd.getColumnName(i).equalsIgnoreCase(oChartData.getXAxis()))
					{
						
						//rsmd.getColumnTypeName(i)
						xAxis+=StringUtil.escapeString(rs.getString(i))+",";
					}
					else
					{
						if(oChartData.getSeriesColumn()!=null)
						{
							if(StringHelper.inArrayIgnoreCase(Arrays.asList(oChartData.getSeriesColumn()),rsmd.getColumnName(i)))
							{
								
								sc[j]+=StringUtil.escapeString(rs.getString(i))+",";
								j++;
							}
						}
					}
				}
				j=0;
				
			}
			xAxis=(xAxis.substring(0,xAxis.length()-1));
			oChartData.setJsonXAxisData(xAxis);
			for(int i=0;i<oChartData.getSeriesColumn().length;i++)
			{
				seriesColumn+="{name:'"+oChartData.getSeriesColumn()[i]+"',data:["+StringUtil.escapeString(sc[i].substring(0,sc[i].length()-1))+"]},";
			}
			if(!seriesColumn.isEmpty())
			{
				seriesColumn=(seriesColumn.substring(0,seriesColumn.length()-1));
			}
			//seriesColumn=(seriesColumn.substring(0,seriesColumn.length()-1));
			oChartData.setJsonSeriesColumnData(seriesColumn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    finally
	    {
	    	if (statement != null)
				statement.close();
			if (connection != null) 
				connection.close();
			if(!rs.isClosed())
				rs.close();
	    }
		return oChartData;
	}
	
	public ChartData  GetChartData4Pie(ChartData oChartData,String sql) throws SQLException
	{
		
        Statement statement=null;
		ResultSet rs=null;
		
        this.commandText = sql;
        try {
    		statement = connection.createStatement();
			String seriesColumn = "";
			String series = "";
			rs = statement.executeQuery(commandText);
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next())
			{
				seriesColumn+="{name:'"+rs.getObject(rsmd.getColumnName(1)).toString()+"',y:"+rs.getObject(rsmd.getColumnName(2)).toString()+"},";
			}
			if(!seriesColumn.isEmpty())
			{
				seriesColumn=(seriesColumn.substring(0,seriesColumn.length()-1));
				series="{name:'"+oChartData.getSeriesColumn()[0]+"',data:["+seriesColumn+"]}";
			}
			else
			{
				series="{name:'"+oChartData.getSeriesColumn()[0]+"',data:[]}";
			}
			oChartData.setJsonSeriesColumnData(series);
			//System.out.println(series);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
        }
	    finally
	    {
	    	if (statement != null)
				statement.close();
			if (connection != null) 
				connection.close();
			if(!rs.isClosed())
				rs.close();
	    }
		return oChartData;
	}
	
	
	public String GetLookupReportControlValue( String searchBy, String searchValue, ReportControl oReportControl,String _additionalWhere,int pageNumber, int pageSize, boolean inSearch) throws SQLException
    {
        String str = "";
        String where = "";
        String sqlCount = "";
        int totalRecords = 0;
        Statement statement=null;
		ResultSet rs=null;
		
        if (!searchValue.equals(""))
        {
        	if(!inSearch)
        	{
	            if (searchBy.equalsIgnoreCase(oReportControl.getNameField())){
	            	if(searchValue.startsWith("=")){
	            		searchValue = searchValue.substring(1, searchValue.length());
	            		where = " AND UPPER(" + oReportControl.getNameField() + ") = '" + searchValue.toUpperCase() + "'";
	            	}
	            	else if(searchValue.endsWith("=")){
	            		searchValue = searchValue.substring(0, searchValue.length()-1);
	            		where = " AND UPPER(" + oReportControl.getNameField() + ") = '" + searchValue.toUpperCase() + "'";
	            	}
	            	else if(searchValue.startsWith("*")){
	            		searchValue = searchValue.substring(1, searchValue.length());
	            		where = " AND UPPER(" + oReportControl.getNameField() + ") LIKE '%" + searchValue.toUpperCase() + "'";
	            	}
	            	else if(searchValue.endsWith("*")){
	            		searchValue = searchValue.substring(0, searchValue.length()-1);
	            		where = " AND UPPER(" + oReportControl.getNameField() + ") LIKE '" + searchValue.toUpperCase() + "%'";
	            	}
	            	else
	            	{
	            		where = " AND UPPER(" + oReportControl.getNameField() + ") LIKE '%" + searchValue.toUpperCase() + "%'";
	            	}
	            }
	            else
	                where = " AND " + searchBy + "='" + searchValue + "'";
        	}
        	else
        	{
        		String[] arr = searchValue.split(",");
        		searchValue = "";
        		for(String s:arr){
        			searchValue+="'"+s+"',";
        		}
        		searchValue = searchValue.substring(0,searchValue.length()-1);
        		if (searchBy.equalsIgnoreCase(oReportControl.getNameField()))
        			where = " AND " + oReportControl.getNameField() + " IN (" + searchValue + ")";
        		else
        			where = " AND " + oReportControl.getValueField() + " IN (" + searchValue + ")";
        	}
        }
        
        where = " WHERE 1=1 " + where;
        
        if (!_additionalWhere.trim().equals(""))
        {
            where = where + " AND "+_additionalWhere; 
        }

        switch (oReportControl.getDataSource().getSourceType())
        {
	        case SQLView:
	        	this.commandText = "SELECT DISTINCT " + oReportControl.getNameField() + "," + oReportControl.getValueField() + " FROM " + oReportControl.getDataSource().getView().getName() + " " + where;
	        	sqlCount = "SELECT COUNT(*) FROM "+ oReportControl.getDataSource().getView().getName() + " " + where;
	        	break;
	        case SQLTable:
	        	this.commandText = "SELECT DISTINCT " + oReportControl.getNameField() + "," + oReportControl.getValueField() + " FROM " + oReportControl.getDataSource().getTable().getName() + " " + where;
	        	sqlCount = "SELECT COUNT(*) FROM "+ oReportControl.getDataSource().getTable().getName() + " " + where;
	        	break;
	        case SQLProcedure:
	        	this.commandText = oReportControl.getDataSource().getProcedure().getName();
	            break;
			default:
				break;
        }
        
        try
        {
        	statement = connection.createStatement();
            if (oReportControl.getDataSource().getSourceType() == SourceType.SQLProcedure)
            {
            	/*
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.Clear();
                foreach (Param oParam in oReportControl.DataSource.Procedure.Params)
                {
                    switch (oParam.Name)
                    {
                        case "search_for":
                            cmd.Parameters.Add("@search_for", SqlDbType.NVarChar).Value = oParam.Value;
                            break;
                        case "opt_cond_value":
                            cmd.Parameters.Add("@opt_cond_value", SqlDbType.NVarChar).Value = oParam.Value;
                            break;;
                    }
                }
                
                cmd.Parameters.Add("@search_char", SqlDbType.NVarChar).Value = searchValue;
                cmd.Parameters.Add("@search_by", SqlDbType.NVarChar).Value = searchBy;
                
                if (oReportControl.AllowDefaultFilter)
                    cmd.Parameters.Add("@DATAAREAID", SqlDbType.NVarChar).Value = dataAreaId;
             */   
            }
            else
            {
            	this.commandText = "SELECT "+oReportControl.getNameField() + "," + oReportControl.getValueField()+" FROM (SELECT a.*,rownum r_ FROM ( "+this.commandText+" ) a  WHERE rownum<(("+pageNumber+"*"+pageSize+")+1) ) WHERE r_>=((("+pageNumber+"-1)*"+pageSize+")+1)";
            	rs = statement.executeQuery(sqlCount);
            	rs.next();
            	totalRecords = rs.getInt(1);
            	rs.close();
            }
			rs = statement.executeQuery(commandText);
            str += "<div class='anet-dialog-search-result-grid'><table class='anet-dialog-data-table'><thead><tr><td style='width:65px;'>&nbsp;</td><td style='min-width:100px'>&nbsp;</td><td style='min-width:400px'>&nbsp;</td></tr></thead><tbody>";
            if (oReportControl.getFieldSelectionType() != FieldSelectionType.Multiple)
            {
                while(rs.next())
                {
                    str += "<tr><td><input type='radio' class='rdoRCDialogValSelector' name='" + oReportControl.getNameField() + "' value='" + rs.getString(oReportControl.getValueField()) + "' /></td><td>" + rs.getString(oReportControl.getValueField()) + "</td><td>" + rs.getString(oReportControl.getNameField()) + "</td></tr>";
                }
            }
            else
            {
                while(rs.next())
                {
                    str += "<tr><td><input type='checkbox' class='chkRCDialogValSelector' name='" + oReportControl.getNameField() + "' value='" + rs.getString(oReportControl.getValueField()) + "' /></td><td>" + rs.getString(oReportControl.getValueField()) + "</td><td>" + rs.getString(oReportControl.getNameField()) + "</td></tr>";
                }
            }
            str += "</tbody></table></div>";
            
            str += "<div class='anet-paginator-wrapper'>";
            str += "<span class='total-records'>Total Records: "+totalRecords+" </span>";
            //str += "<span><select onchange='pgReloadByPageSize(this)' class='page-size' name='pageSize'><option value='100' "+((pageSize==100)?"selected='selected'":"")+">100</option><option value='200' "+((pageSize==200)?"selected='selected'":"")+">200</option><option value='500' "+((pageSize==500)?"selected='selected'":"")+">500</option></select> </span>";
            //str += "<span onclick=\"pgFirst(this,\'"+totalRecords+"\',\'"+pageNumber+"\',\'"+pageSize+"\')\" class='pg-first fontello-icon-to-start'></span>";
            //str += "<span onclick=\"pgPrev(this,\'"+totalRecords+"\',\'"+pageNumber+"\',\'"+pageSize+"\')\"class='pg-prev  fontello-icon-left-dir-2'></span>";
            //str += "<span>Page <input class='page-number' type='text' name='pageNumber' value='"+pageNumber+"' /> of "+((totalRecords%pageSize)>0?((totalRecords/pageSize)+1):((totalRecords<pageSize)?1:(totalRecords/pageSize)))+" </span>";
            //str += "<span onclick=\"pgNext(this,\'"+totalRecords+"\',\'"+pageNumber+"\',\'"+pageSize+"\')\" class='pg-next fontello-icon-right-dir-2'></span>";
            //str += "<span onclick=\"pgLast(this,\'"+totalRecords+"\',\'"+pageNumber+"\',\'"+pageSize+"\')\" class='pg-last fontello-icon-to-end'></span>";
            //str += "<span onclick=\"pgReload(this)\" class='pg-reload fontello-icon-cw'></span>";
            
            str += "<span><select class='pg-size' name='pageSize'><option value='100' "+((pageSize==100)?"selected='selected'":"")+">100</option><option value='200' "+((pageSize==200)?"selected='selected'":"")+">200</option><option value='500' "+((pageSize==500)?"selected='selected'":"")+">500</option></select> </span>";
            str += "<span anet-tr='"+totalRecords+"' anet-pn='"+pageNumber+"' anet-ps='"+pageSize+"' class='pg-first fontello-icon-to-start'></span>";
            str += "<span anet-tr='"+totalRecords+"' anet-pn='"+pageNumber+"' anet-ps='"+pageSize+"' class='pg-prev  fontello-icon-left-dir-2'></span>";
            str += "<span>Page <input class='page-number' type='text' name='pageNumber' value='"+pageNumber+"' /> of "+((totalRecords%pageSize)>0?((totalRecords/pageSize)+1):((totalRecords<pageSize)?1:(totalRecords/pageSize)))+" </span>";
            str += "<span anet-tr='"+totalRecords+"' anet-pn='"+pageNumber+"' anet-ps='"+pageSize+"' class='pg-next fontello-icon-right-dir-2'></span>";
            str += "<span anet-tr='"+totalRecords+"' anet-pn='"+pageNumber+"' anet-ps='"+pageSize+"' class='pg-last fontello-icon-to-end'></span>";
            str += "<span anet-tr='"+totalRecords+"' anet-pn='"+pageNumber+"' anet-ps='"+pageSize+"' class='pg-reload fontello-icon-cw'></span>";
            str += "</div>";
	        
	    }
	    catch (SQLException sqlEx)
	    {
	    	sqlEx.printStackTrace();
	    }
	    finally
	    {
	    	if (statement != null)
				statement.close();
			if (connection != null) 
				connection.close();
			if(!rs.isClosed())
				rs.close();
	    }
        return str;
    }
	
	public String GetHtmlTable(String sql) throws SQLException
	{
		String html="<table class=\"table table-bordered table-striped table-condensed table-content boo-table\">";
		Statement statement=null;
		ResultSet rs=null;
		
        this.commandText = sql;
        try {
    		statement = connection.createStatement();
			rs = statement.executeQuery(commandText);
			ResultSetMetaData rsmd = rs.getMetaData();
			html+="<thead><tr>";
			String th="";
			for(int i=1;i<=rsmd.getColumnCount();i++)
			{
				th+="<th>"+rsmd.getColumnName(i)+"</th>";
			}
			html+=th+"</tr></thead>";
			html+="<tbody>";
			String td="";
			int dataType=12; //12 = VARCHAR < java.sql,Types
			while(rs.next())
			{
				td +="<tr>";
				for(int i=1;i<=rsmd.getColumnCount();i++)
				{
					dataType=rsmd.getColumnType(i);
					if(dataType==java.sql.Types.INTEGER || dataType==java.sql.Types.DECIMAL || dataType==java.sql.Types.DOUBLE || dataType==java.sql.Types.FLOAT || dataType==java.sql.Types.NUMERIC )
						td+="<td class=\"text-right\">"+StringUtil.escapeString(rs.getString(i))+"</td>";	
					else
						td+="<td>"+StringUtil.escapeString(rs.getString(i))+"</td>";	
				}
				td +="</tr>";
			}
			html+=td+"</tbody></table>";
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    finally
	    {
	    	if (statement != null)
				statement.close();
			if (connection != null) 
				connection.close();
			if(!rs.isClosed())
				rs.close();
	    }
		return html;
	}
}

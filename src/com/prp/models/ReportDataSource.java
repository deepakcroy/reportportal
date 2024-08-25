package com.prp.models;

import net.aditri.ui.common.Enum.SourceType;
import net.aditri.ui.common.SQLFunction;
import net.aditri.ui.common.SQLProcedure;
import net.aditri.ui.common.SQLTable;
import net.aditri.ui.common.SQLView;

public class ReportDataSource {
	private String ConnectionString;
	private String ConnectionName;
	private SourceType SourceType;
	private SQLView View;
	private SQLTable Table;
	private SQLProcedure Procedure;
	private SQLFunction Function;
	
	public ReportDataSource(){
		this.setSourceType(net.aditri.ui.common.Enum.SourceType.SQLProcedure);
		this.setProcedure(new SQLProcedure());
		this.setView(new SQLView());
		this.setTable(new SQLTable());
		this.setFunction(new SQLFunction());
	}
	
	
	public String getConnectionString() {
		return ConnectionString;
	}
	public void setConnectionString(String connectionString) {
		ConnectionString = connectionString;
	}
	public String getConnectionName() {
		return ConnectionName;
	}
	public void setConnectionName(String connectionName) {
		ConnectionName = connectionName;
	}
	public SourceType getSourceType() {
		return SourceType;
	}
	public void setSourceType(SourceType sourceType) {
		SourceType = sourceType;
	}
	public SQLView getView() {
		return View;
	}
	public void setView(SQLView view) {
		View = view;
	}
	public SQLTable getTable() {
		return Table;
	}
	public void setTable(SQLTable table) {
		Table = table;
	}
	public SQLProcedure getProcedure() {
		return Procedure;
	}
	public void setProcedure(SQLProcedure procedure) {
		Procedure = procedure;
	}
	public SQLFunction getFunction() {
		return Function;
	}
	public void setFunction(SQLFunction function) {
		Function = function;
	}
	
	
}

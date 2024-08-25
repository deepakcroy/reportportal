package net.aditri.ui.dashboard;

public class ChartData {
	private String xAxis;
	private String[] seriesColumn ;
	private String jsonXAxisData; //categories
	private String jsonSeriesColumnData;
	public String getXAxis() {
		return xAxis;
	}
	public void setXAxis(String xAxisField) {
		this.xAxis = xAxisField;
	}
	public String[] getSeriesColumn() {
		return seriesColumn;
	}
	public void setSeriesColumn(String[] seriesColumn) {
		this.seriesColumn = seriesColumn;
	}
	public String getJsonXAxisData() {
		return jsonXAxisData;
	}
	public void setJsonXAxisData(String jsonXAxisData) {
		this.jsonXAxisData = jsonXAxisData;
	}
	public String getJsonSeriesColumnData() {
		return jsonSeriesColumnData;
	}
	public void setJsonSeriesColumnData(String jsonSeriesColumnData) {
		this.jsonSeriesColumnData = jsonSeriesColumnData;
	}
	public ChartData()
	{
		this.xAxis="";
		this.seriesColumn=null;
		this.jsonXAxisData="";
		this.jsonSeriesColumnData="";
	}
	
}

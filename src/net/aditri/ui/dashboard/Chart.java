package net.aditri.ui.dashboard;

import java.util.List;

import net.aditri.ui.common.DataSource;
import net.aditri.ui.common.Enum.ChartType;
import net.aditri.web.utility.KeyValue;

public class Chart {
	private String id;
	private ChartType type;
	private String renderTo;
	private DataSource dataSource;
	private boolean credit;
	private String Init;
	private List<KeyValue> nodes;
	private ChartData chartData;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRenderTo() {
		return renderTo;
	}
	public void setRenderTo(String renderTo) {
		this.renderTo = renderTo;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public boolean isCredit() {
		return credit;
	}
	public void setCredit(boolean credit) {
		this.credit = credit;
	}
	public String getInit() {
		return Init;
	}
	public void setInit(String init) {
		Init = init;
	}
	
	public List<KeyValue> getNodes() {
		return nodes;
	}
	public void setNodes(List<KeyValue> nodes) {
		this.nodes = nodes;
	}
	
	public ChartData getChartData() {
		return chartData;
	}
	public void setChartData(ChartData chartData) {
		this.chartData = chartData;
	}
	public ChartType getType() {
		return type;
	}
	public void setType(ChartType type) {
		this.type = type;
	}
	
	
}

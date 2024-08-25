package net.aditri.ui.dashboard;

import java.util.List;

public class Widget {
	private String id;
	private List<Chart> charts;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Chart> getCharts() {
		return charts;
	}
	public void setCharts(List<Chart> charts) {
		this.charts = charts;
	}
	public Widget()
	{
		
	}
	public Widget(String id,List<Chart> charts)
	{
		this.id=id;
		this.charts=charts;
	}
}

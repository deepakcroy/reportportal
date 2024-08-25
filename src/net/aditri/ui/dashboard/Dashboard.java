package net.aditri.ui.dashboard;

import java.util.List;

public class Dashboard {
	private String id;
	private String title;
	private String description;
	private String layout;
	
	private List<Widget> widgets;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public List<Widget> getWidgets() {
		return widgets;
	}

	public void setWidgets(List<Widget> widgets) {
		this.widgets = widgets;
	}
	
}

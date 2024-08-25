package com.app.module;

import java.io.Serializable;
import java.util.List;

import com.prp.models.Menu;

public class Module implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String version;
	private String icon;
	private String createTime;
	private String defaultLink;
	private List<Menu> menus;
	private List<String> controllers;
	private boolean editable;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getDefaultLink() {
		return defaultLink;
	}
	public void setDefaultLink(String defaultLink) {
		this.defaultLink = defaultLink;
	}
	
	public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	
	public List<String> getControllers() {
		return controllers;
	}
	public void setControllers(List<String> controllers) {
		this.controllers = controllers;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	
	
}

package com.prp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="Menu")
@javax.persistence.Table(name = "\"MENU\"")
public class Menu extends Logger {
	@Id
	//oracle
	//@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_gen")
	//@SequenceGenerator(name="seq_gen", sequenceName="MENUITEM_SEQ")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name="MENU_ID")	
	private Long id;
	
	@Column(name="PARENT_ID")
	private Long parentId;
	@Column(name="TITLE")
	private String title;
	@Column(name="CONTROLLER")
	private String controller; //controller
	@Column(name="MODULE")
	private String module; //module
	@Column(name="LINK")
	private String link;
	@Column(name="POSITION")
	private Integer position;
	@Column(name="ISNEWTAB")
	private boolean newTab;
	@Column(name="ISLEAFLEVEL")
	private boolean leafLevel;
/*	@Column(name="ORDERSEQ")
	private Integer orderSeq;*/
	
	public Menu(Long id,Long pId,String title,String controller,String module,String link, Integer pos,boolean newTab,boolean leafLevel)
	{
		this.id=id;
		this.parentId=pId;
		this.title=title;
		this.controller=controller;
		this.module=module;
		this.link=link;
		this.position=pos;
		this.newTab=newTab;
		this.leafLevel=leafLevel;
	}
	public Menu(String title,String controller,String module,String link)
	{
		this.title=title;
		this.controller=controller;
		this.module=module;
		this.link=link;
	}
	public Menu()
	{
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getController() {
		return controller;
	}
	public void setController(String controller) {
		this.controller = controller;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public boolean isNewTab() {
		return newTab;
	}
	public void setNewTab(boolean newTab) {
		this.newTab = newTab;
	}
	public boolean isLeafLevel() {
		return leafLevel;
	}
	public void setLeafLevel(boolean leafLevel) {
		this.leafLevel = leafLevel;
	}
	
	/*public Integer getOrderSeq() {
		return orderSeq;
	}
	public void setOrderSeq(Integer orderSeq) {
		this.orderSeq = orderSeq;
	}*/
	
}

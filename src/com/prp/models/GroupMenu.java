package com.prp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="GroupMenu")
@javax.persistence.Table(name = "\"GROUP_MENU\"")
public class GroupMenu extends Logger {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name="GROUPMENU_ID")	
	private Long id;
	
	@Column(name="MENUID")
	private Long  menuId;
	
	@Column(name="GROUPID") 
	private Long  groupId;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public GroupMenu()
	{
		
	}
	public GroupMenu(Long id,Long menuId, Long groupId)
	{
		this.id=id;
		this.menuId=menuId;
		this.groupId=groupId;
	}
	public GroupMenu(Long id,Long menuId, Long groupId,String createdBy,String modifiedBy)
	{
		this.id=id;
		this.menuId=menuId;
		this.groupId=groupId;
		this.setCreatedBy(createdBy);
		this.setModifiedBy(modifiedBy);
	}
}

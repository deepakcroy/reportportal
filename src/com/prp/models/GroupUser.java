package com.prp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="GroupUser")
@javax.persistence.Table(name = "\"GROUP_USER\"")
public class GroupUser extends Logger {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name="GROUPUSER_ID")	
	private Long id;
	
	@Column(name="GROUPID")
	private Long  groupId;
	
	@Column(name="USERID")
	private Long  userId;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public GroupUser()
	{
		
	}
	public GroupUser(Long id,Long groupId,Long userId)
	{
		this.id=id;
		this.groupId=groupId;
		this.userId=userId;
	}
	public GroupUser(Long id,Long groupId,Long userId, String createdBy,String modifiedBy)
	{
		this.id=id;
		this.groupId=groupId;
		this.userId=userId;
		this.setCreatedBy(createdBy);
		this.setModifiedBy(modifiedBy);
	}
}

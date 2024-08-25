package com.prp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.SequenceGenerator;


@Entity(name="Role")
@javax.persistence.Table(name = "\"ROLEITEM\"")
public class Role extends Logger {
	@Id
	//oracle
	//@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_gen")
	//@SequenceGenerator(name="seq_gen", sequenceName="ROLEITEM_SEQ")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name="ROLEITEM_ID")	
	private Long id;
	
	@Column(name="USERUGID") //User Or UserGroup Id
	private Long  userUGId;
	
	@Column(name="ROLEID") //Menu Id
	private Long  roleId;
	
	@Column(name="ISUG") //IS USER GROUP OR USER
	private boolean isUserGroup;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserUGId() {
		return userUGId;
	}

	public void setUserUGId(Long userUGId) {
		this.userUGId = userUGId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public boolean isUserGroup() {
		return isUserGroup;
	}

	public void setUserGroup(boolean isUserGroup) {
		this.isUserGroup = isUserGroup;
	}
	public Role()
	{
		
	}
	public Role(Long id,Long userUGId, Long roleId,boolean isUserGroup)
	{
		this.id=id;
		this.userUGId=userUGId;
		this.roleId=roleId;
		this.isUserGroup=isUserGroup;
	}
	public Role(Long id,Long userUGId, Long roleId,boolean isUserGroup,String createdBy,String modifiedBy)
	{
		this.id=id;
		this.userUGId=userUGId;
		this.roleId=roleId;
		this.isUserGroup=isUserGroup;
		this.setCreatedBy(createdBy);
		this.setModifiedBy(modifiedBy);
	}
}

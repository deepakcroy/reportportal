package com.prp.models;

import java.util.Date;
//import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.aditri.web.utility.EnumHelper.Status;

//import org.hibernate.annotations.Type;
//import javax.servlet.http.HttpSession;
@MappedSuperclass   // this annotation need to be used for creating the properties as table filed in base class through hibernate
public class Logger {
	
	@Column(name="\"STATUS\"") // for handling sql reserved words 
	private Status status;
	
	@Column(name="CREATEDBY")
	private String createdBy;
	
	@Column(name="CREATEDAT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	
	@Column(name="MODIFIEDBY")
	private String modifiedBy;
	
	@Column(name="MODIFIEDAT")
	@Temporal(TemporalType.TIMESTAMP)
	//@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") //this is from spring framework 3
	private Date modifiedAt;
	
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public Date getModifiedAt() {
		return modifiedAt;
	}
	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Logger()
	{
		this.status = Status.Active;
		this.createdBy="";
		this.createdAt = new Date();
		this.modifiedBy="";
		this.modifiedAt = new Date();
	}
}

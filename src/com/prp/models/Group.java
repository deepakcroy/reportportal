package com.prp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;

@Entity(name="Group")
@javax.persistence.Table(name = "\"GROUP\"") // for handling sql reserved words 
public class Group extends Logger {
	
	private Long id;
	private String groupName;
	private String description;
	
	@Id
	//oracle
	//@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_gen")
	//@SequenceGenerator(name="seq_gen", sequenceName="USERGROUP_SEQ")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name="GROUP_ID")	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="GROUPNAME")
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}

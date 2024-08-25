package com.prp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.SequenceGenerator;

@Entity(name="User")
@javax.persistence.Table(name = "\"USER\"")
public class User extends Logger {

	private Long id;
	private String userName;
	private String password;
	
	/*public User()
	{
		super();
	}*/
	
	
	@Id
	//oracle
	//@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_gen")
	//@SequenceGenerator(name="seq_gen", sequenceName="USER_SEQ")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name="USER_ID")	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="PASSWORD")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="USER_NAME")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}

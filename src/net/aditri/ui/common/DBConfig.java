package net.aditri.ui.common;

import net.aditri.ui.common.Enum.DBServer;

public class DBConfig {
	private String name;
	private DBServer dbServer;
	private String conString;
	private String driver;
	private String user;
	private String pwd;
	private boolean isPWDEncrypt;
	
	
	public DBConfig()
	{
		this.dbServer = DBServer.MYSQL;
	}
	public DBConfig(String name,DBServer dbServer,String conString, String driver, String user,String pwd, boolean isPWDEncrypt)
	{
		this.name = name;
		this.dbServer = dbServer;
		this.conString = conString;
		this.driver = driver;
		this.user = user;
		this.pwd = pwd;
		this.isPWDEncrypt = isPWDEncrypt;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DBServer getDbServer() {
		return dbServer;
	}
	public void setDbServer(DBServer dbServer) {
		this.dbServer = dbServer;
	}
	public String getConString() {
		return conString;
	}
	public void setConString(String conString) {
		this.conString = conString;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public boolean isPWDEncrypt() {
		return isPWDEncrypt;
	}
	public void setPWDEncrypt(boolean isPWDEncrypt) {
		this.isPWDEncrypt = isPWDEncrypt;
	}
	
	
	
}

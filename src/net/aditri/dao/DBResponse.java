package net.aditri.dao;

public class DBResponse {
	
	private boolean isSuccess;
	private String message;
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public DBResponse()
	{
		
	}
	public DBResponse(boolean isSuccess, String msg)
	{
		this.isSuccess = isSuccess;
		this.message = msg;
	}
	
}

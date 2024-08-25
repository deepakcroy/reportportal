package net.aditri.web.utility;

import java.util.Map;

public class ValidationResponse {
	private boolean isSuccess;
	private Map<String,String> fieldMessage;
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public Map<String, String> getFieldMessage() {
		return fieldMessage;
	}
	public void setFieldMessage(Map<String, String> fieldMessage) {
		this.fieldMessage = fieldMessage;
	}
	public ValidationResponse()
	{
		
	}
	/**
	 * If errors occur, pass field error through ValidationResponse Constructor 
	 * boolean's default value = false
	 * so, in this constructor isSuccess will return false
	 */
	public ValidationResponse(Map<String, String> fieldMessage)
	{
		this.fieldMessage = fieldMessage;
	}
}

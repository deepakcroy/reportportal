package com.prp.models;

public class WhereBuilder {
	private String DBField;
	private String ValueFrom;
	private String Condition;
	public String getDBField() {
		return DBField;
	}
	public void setDBField(String dBField) {
		DBField = dBField;
	}
	public String getValueFrom() {
		return ValueFrom;
	}
	public void setValueFrom(String valueFrom) {
		ValueFrom = valueFrom;
	}
	public String getCondition() {
		return Condition;
	}
	public void setCondition(String condition) {
		Condition = condition;
	}
	
	
}

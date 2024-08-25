package net.aditri.ui.common;

public class Param {
	private String name;
	private String value;
	private boolean isStatic;
	
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isStatic() {
		return isStatic;
	}
	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
	public Param()
    {
        this.isStatic = false;
    }
    public Param(String _name, String _value)
    {
        this.name = _name;
        this.value = _value;
    }
    public Param(String _name, String _value, boolean _isStatic)
    {
        this.name = _name;
        this.value = _value;
        this.isStatic = _isStatic;
    }
}

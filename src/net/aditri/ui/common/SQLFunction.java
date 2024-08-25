package net.aditri.ui.common;

public class SQLFunction {
	private String name;
	private String returnAs;
	
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReturnAs() {
		return returnAs;
	}
	public void setReturnAs(String returnAs) {
		this.returnAs = returnAs;
	}
	public SQLFunction()
    {
    }
    public SQLFunction(String _Name)
    {
        this.name = _Name;
    }
    public SQLFunction(String _Name,String _ReturnAs)
    {
        this.name = _Name;
        this.returnAs = _ReturnAs;
    }
}

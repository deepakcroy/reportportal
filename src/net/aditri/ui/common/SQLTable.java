package net.aditri.ui.common;

public class SQLTable {
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SQLTable()
    {
    }
    public SQLTable(String _Name)
    {
        this.name = _Name;
    }
}

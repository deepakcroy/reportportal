package net.aditri.ui.common;
import java.util.ArrayList;
import java.util.List;

public class SQLProcedure {
	private String name;
    private List<Param> params;

    public SQLProcedure()
    {
        this.params = new ArrayList<Param>();
    }
    public SQLProcedure(String _name,List<Param> _params)
    {
        this.name = _name;
        this.params = _params;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Param> getParams() {
		return params;
	}
	public void setParams(List<Param> params) {
		this.params = params;
	}
    
}

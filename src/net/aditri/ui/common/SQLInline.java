package net.aditri.ui.common;

public class SQLInline {
	private String sql;
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public SQLInline()
    {
    }
    public SQLInline(String sql)
    {
        this.sql = sql;
    }
}

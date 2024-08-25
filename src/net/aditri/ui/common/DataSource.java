package net.aditri.ui.common;
import net.aditri.ui.common.Enum.SourceType;
public class DataSource {
	
	private String connectionString;
	private String connectionName;
	
	private  SourceType sourceType;
	
	private SQLView view;
	private SQLTable table;
	private SQLProcedure procedure;
	private SQLFunction function;
	private SQLInline inline;
	
    public String getConnectionString() {
		return connectionString;
	}
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}
	public String getConnectionName() {
		return connectionName;
	}
	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}
	public SourceType getSourceType() {
		return sourceType;
	}
	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}
	public SQLView getView() {
		return view;
	}
	public void setView(SQLView view) {
		this.view = view;
	}
	public SQLTable getTable() {
		return table;
	}
	public void setTable(SQLTable table) {
		this.table = table;
	}
	public SQLProcedure getProcedure() {
		return procedure;
	}
	public void setProcedure(SQLProcedure procedure) {
		this.procedure = procedure;
	}
	public SQLFunction getFunction() {
		return function;
	}
	public void setFunction(SQLFunction function) {
		this.function = function;
	}
	public SQLInline getInline() {
		return inline;
	}
	public void setInline(SQLInline inline) {
		this.inline = inline;
	}
	public DataSource()
    {
        this.sourceType = SourceType.SQLProcedure;
        this.procedure = new SQLProcedure();
        this.view = new SQLView();
        this.table = new SQLTable();
        this.function = new SQLFunction();
        this.inline = new SQLInline();
    }
    public DataSource(SourceType _SourceType)
    {
        this.sourceType = _SourceType;
    }
    
}

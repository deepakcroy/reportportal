package net.aditri.ui.common;

public class Enum {
    public enum SourceType
    {
        SQLTable(1),
        SQLView(2),
        SQLProcedure(3),
        SQLFunction(4),
        SQLInline(5),
    	WebService(6),
    	Array(7),
    	JSON(8);
    	
        private int value;
		private SourceType(int value) {
	        this.value = value;
	    }
	    public int getValue() {
	        return value;
	    }
	    @Override
	    public String toString(){
	        return this.name();
	    }
	    public String getName(){
	        return this.name();
	    }
    }
    public enum ControlType
    {
    	TextBox(1),
    	DropDown(2),
        ListBox(3),
        Shuttle(4),
        Lookup(5),
        CheckBox(6),
        Radio(7),
        DatePicker(8),
        DateRangePicker(9)
        ;
        
        private int value;
		private ControlType(int value) {
	        this.value = value;
	    }
	    public int getValue() {
	        return value;
	    }
	    @Override
	    public String toString(){
	        return this.name();
	    }
	    public String getName(){
	        return this.name();
	    }
    }
    public enum ChartType
    {
        BAR(1),
        COLUMN(2),
        LINE(3),
        AREA(4),
        STACK(5),
        PIE(6);
        
        private int value;
		private ChartType(int value) {
	        this.value = value;
	    }
	    public int getValue() {
	        return value;
	    }
	    @Override
	    public String toString(){
	        return this.name();
	    }
	    public String getName(){
	        return this.name();
	    }
    }
    public enum FieldSelectionType
    {
        Multiple(1),
        Single(2);
        private int value;
		private FieldSelectionType(int value) {
	        this.value = value;
	    }
	    public int getValue() {
	        return value;
	    }
	    @Override
	    public String toString(){
	        return this.name();
	    }
	    public String getName(){
	        return this.name();
	    }
    }
    public enum DBServer
    {
    	MYSQL(1),
        ORACLE(2),
        SQLSERVER(3),
        DB2(4),
        POSTGRESQL(5);
        private int value;
		private DBServer(int value) {
	        this.value = value;
	    }
	    public int getValue() {
	        return value;
	    }
	    @Override
	    public String toString(){
	        return this.name();
	    }
	    public String getName(){
	        return this.name();
	    }
    }
}

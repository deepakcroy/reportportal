package net.aditri.ui.report;


import net.aditri.ui.common.DataSource;
import net.aditri.ui.common.Enum.ControlType;
import net.aditri.ui.common.Enum.FieldSelectionType;


public class ReportControl {
	private String id;
	private String tempId;
	private String nameField;
	private String valueField;
	private ControlType controlType;
	private FieldSelectionType fieldSelectionType;
	private DataSource dataSource;
	
	private String controlData;
	private boolean allowAll;
	
	private String defaultValue;
	private boolean defaultValueAsTop1;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTempId() {
		return tempId;
	}
	public void setTempId(String tempId) {
		this.tempId = tempId;
	}
	public String getNameField() {
		return nameField;
	}
	public void setNameField(String nameField) {
		this.nameField = nameField;
	}
	public String getValueField() {
		return valueField;
	}
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
	public ControlType getControlType() {
		return controlType;
	}
	public void setControlType(ControlType controlType) {
		this.controlType = controlType;
	}
	public FieldSelectionType getFieldSelectionType() {
		return fieldSelectionType;
	}
	public void setFieldSelectionType(FieldSelectionType fieldSelectionType) {
		this.fieldSelectionType = fieldSelectionType;
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public String getControlData() {
		return controlData;
	}
	public void setControlData(String controlData) {
		this.controlData = controlData;
	}
	public boolean isAllowAll() {
		return allowAll;
	}
	public void setAllowAll(boolean allowAll) {
		this.allowAll = allowAll;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public boolean isDefaultValueAsTop1() {
		return defaultValueAsTop1;
	}
	public void setDefaultValueAsTop1(boolean defaultValueAsTop1) {
		this.defaultValueAsTop1 = defaultValueAsTop1;
	}
	
	
	public ReportControl()
	{
		this.controlData = "";
        this.fieldSelectionType = FieldSelectionType.Single;
        this.allowAll = true;
        this.defaultValue = "";
        this.defaultValueAsTop1 = false;
        this.tempId = "";
	}
	public ReportControl(String _id, ControlType _ControlType,String _NameField,String _ValueField, DataSource _dataSource)
    {
        this.id = _id;
        this.controlType = _ControlType;
        this.nameField = _NameField;
        this.valueField = _ValueField;
        this.dataSource = _dataSource;
    }
	public ReportControl(String _id, ControlType _ControlType, String _NameField, String _ValueField,FieldSelectionType _FieldSelectionType, DataSource _dataSource)
	{
	    this.id = _id;
	    this.controlType = _ControlType;
	    this.nameField = _NameField;
	    this.valueField = _ValueField;
	    this.fieldSelectionType = _FieldSelectionType;
	    this.dataSource = _dataSource;
	}
	public ReportControl(String _id, ControlType _ControlType, String _NameField, String _ValueField, FieldSelectionType _FieldSelectionType,boolean _AllowAll, DataSource _dataSource)
    {
        this.id = _id;
        this.controlType = _ControlType;
        this.nameField = _NameField;
        this.valueField = _ValueField;
        this.fieldSelectionType = _FieldSelectionType;
        this.allowAll = _AllowAll;
        this.dataSource = _dataSource;
    }
    
}

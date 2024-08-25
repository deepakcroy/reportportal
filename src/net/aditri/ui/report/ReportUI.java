package net.aditri.ui.report;

import java.util.List;

public class ReportUI {

	private String reportId;
	private String reportHeader;
	private String reportDescription;
	private String reportName;
	private String formBody;
	private String reportGateway;
	private List<ReportControl> reportControls;
	
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportHeader() {
		return reportHeader;
	}
	public void setReportHeader(String reportHeader) {
		this.reportHeader = reportHeader;
	}
	public String getReportDescription() {
		return reportDescription;
	}
	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getFormBody() {
		return formBody;
	}
	public void setFormBody(String formBody) {
		this.formBody = formBody;
	}
	public String getReportGateway() {
		return reportGateway;
	}
	public void setReportGateway(String reportGateway) {
		this.reportGateway = reportGateway;
	}
	public List<ReportControl> getReportControls() {
		return reportControls;
	}
	public void setReportControls(List<ReportControl> reportControls) {
		this.reportControls = reportControls;
	}
	
}

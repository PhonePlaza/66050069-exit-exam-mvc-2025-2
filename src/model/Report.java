package model;

/**
 * Report - Entity class สำหรับการรายงานข่าวลือ
 */
public class Report {

    private String reportId; // PK
    private String reporterId; // FK -> User.userId
    private String rumourId; // FK -> Rumour.rumourId
    private String reportDate; // DD/MM/YYYY
    private String reportType; // DISTORTED / INCITE / FALSE_INFO

    // Constructors
    public Report() {
    }

    public Report(String reportId, String reporterId, String rumourId,
            String reportDate, String reportType) {
        this.reportId = reportId;
        this.reporterId = reporterId;
        this.rumourId = rumourId;
        this.reportDate = reportDate;
        this.reportType = reportType;
    }

    // Getters & Setters
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReporterId() {
        return reporterId;
    }

    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }

    public String getRumourId() {
        return rumourId;
    }

    public void setRumourId(String rumourId) {
        this.rumourId = rumourId;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId='" + reportId + '\'' +
                ", reporterId='" + reporterId + '\'' +
                ", rumourId='" + rumourId + '\'' +
                ", reportType='" + reportType + '\'' +
                '}';
    }
}

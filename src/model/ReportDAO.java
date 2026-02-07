package model;

import util.AppConfig;
import util.CSVHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * ReportDAO - Data Access Object สำหรับ Report
 */
public class ReportDAO {

    private static final String[] HEADER = {
            "reportId", "reporterId", "rumourId", "reportDate", "reportType"
    };

    private CSVHelper csvHelper = CSVHelper.getInstance();
    private int nextId = 1;

    public ReportDAO() {
        // หา ID ถัดไปจากข้อมูลที่มีอยู่
        List<Report> reports = findAll();
        for (Report report : reports) {
            String id = report.getReportId();
            if (id != null && id.startsWith("R")) {
                try {
                    int num = Integer.parseInt(id.substring(1));
                    if (num >= nextId) {
                        nextId = num + 1;
                    }
                } catch (NumberFormatException e) {
                    // ignore
                }
            }
        }
    }

    // ดึง Report ทั้งหมด
    public List<Report> findAll() {
        List<Report> reports = new ArrayList<>();
        List<String[]> data = csvHelper.readCSV(AppConfig.REPORTS_FILE);

        for (String[] row : data) {
            if (row.length >= 5) {
                Report report = parseReport(row);
                reports.add(report);
            }
        }

        return reports;
    }

    // บันทึก Report ใหม่
    public void save(Report report) {
        // สร้าง ID อัตโนมัติ
        if (report.getReportId() == null || report.getReportId().isEmpty()) {
            report.setReportId("R" + String.format("%03d", nextId++));
        }

        String[] row = toRow(report);
        csvHelper.appendCSV(AppConfig.REPORTS_FILE, row);
    }

    // ตรวจสอบว่าผู้ใช้เคยรายงานข่าวนี้แล้วหรือยัง
    public boolean hasUserReportedRumour(String userId, String rumourId) {
        List<Report> reports = findAll();
        for (Report report : reports) {
            if (report.getReporterId().equals(userId) &&
                    report.getRumourId().equals(rumourId)) {
                return true;
            }
        }
        return false;
    }

    // นับจำนวน Report ของข่าวลือ
    public int countReportsByRumour(String rumourId) {
        int count = 0;
        List<Report> reports = findAll();
        for (Report report : reports) {
            if (report.getRumourId().equals(rumourId)) {
                count++;
            }
        }
        return count;
    }

    // ดึง Reports ทั้งหมดของข่าวลือ
    public List<Report> getReportsByRumour(String rumourId) {
        List<Report> result = new ArrayList<>();
        List<Report> reports = findAll();
        for (Report report : reports) {
            if (report.getRumourId().equals(rumourId)) {
                result.add(report);
            }
        }
        return result;
    }

    // Helper Methods
    private Report parseReport(String[] row) {
        Report report = new Report();
        report.setReportId(row[0]);
        report.setReporterId(row[1]);
        report.setRumourId(row[2]);
        report.setReportDate(row[3]);
        report.setReportType(row[4]);
        return report;
    }

    private String[] toRow(Report report) {
        return new String[] {
                report.getReportId(),
                report.getReporterId(),
                report.getRumourId(),
                report.getReportDate(),
                report.getReportType()
        };
    }
}

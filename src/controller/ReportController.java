package controller;

import model.Report;
import model.Rumour;
import model.ReportDAO;
import model.RumourDAO;
import util.Validator;

/**
 * ReportController - ควบคุม logic เกี่ยวกับการรายงาน
 */
public class ReportController {

    private ReportDAO reportDAO;
    private RumourDAO rumourDAO;
    private RumourController rumourController;

    public ReportController() {
        this.reportDAO = new ReportDAO();
        this.rumourDAO = new RumourDAO();
        this.rumourController = new RumourController();
    }

    // ตรวจสอบว่าสามารถเพิ่ม Report ได้หรือไม่
    public String canAddReport(String userId, String rumourId) {
        // 1. Check if rumour exists
        Rumour rumour = rumourDAO.findById(rumourId);
        if (rumour == null) {
            return "Rumour not found";
        }

        // 2. Check if rumour is verified
        if (rumour.isVerified()) {
            return "This rumour has been verified. Cannot add more reports.";
        }

        // 3. Check if user already reported
        if (reportDAO.hasUserReportedRumour(userId, rumourId)) {
            return "You have already reported this rumour";
        }

        return null; // Can report
    }

    // เพิ่ม Report ใหม่
    public boolean addReport(Report report) {
        // 1.Validate ข้อมูล
        if (!Validator.validateNotEmpty(report.getReporterId())) {
            return false;
        }
        if (!Validator.validateNotEmpty(report.getRumourId())) {
            return false;
        }
        if (!Validator.validateDate(report.getReportDate())) {
            return false;
        }
        if (!Validator.validateReportType(report.getReportType())) {
            return false;
        }

        // 2.ตรวจสอบ business rules
        String canAdd = canAddReport(report.getReporterId(), report.getRumourId());
        if (canAdd != null) {
            return false;
        }

        // 3.บันทึก Report
        reportDAO.save(report);

        // 4.ตรวจสอบและอัพเดทสถานะ PANIC
        rumourController.checkAndUpdatePanicStatus(report.getRumourId());

        return true;
    }

    // นับจำนวน Report ของข่าวลือ
    public int getReportCount(String rumourId) {
        return reportDAO.countReportsByRumour(rumourId);
    }
}

package controller;

import model.Rumour;
import model.RumourDAO;
import model.ReportDAO;
import util.AppConfig;
import util.Constants;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * RumourController - ควบคุม logic เกี่ยวกับข่าวลือ
 */
public class RumourController {

    private RumourDAO rumourDAO;
    private ReportDAO reportDAO;

    public RumourController() {
        this.rumourDAO = new RumourDAO();
        this.reportDAO = new ReportDAO();
    }

    // ดึงข่าวลือทั้งหมด พร้อมคำนวณ reportCount
    public List<Rumour> getAllRumours() {
        List<Rumour> rumours = rumourDAO.findAll();

        // คำนวณ reportCount สำหรับแต่ละข่าว
        for (Rumour rumour : rumours) {
            int count = reportDAO.countReportsByRumour(rumour.getRumourId());
            rumour.setReportCount(count);
        }

        return rumours;
    }

    // ค้นหาข่าวลือตาม ID

    public Rumour getRumourById(String rumourId) {
        Rumour rumour = rumourDAO.findById(rumourId);
        if (rumour != null) {
            int count = reportDAO.countReportsByRumour(rumourId);
            rumour.setReportCount(count);
        }
        return rumour;
    }

    // ดึงข่าวลือเรียงตามจำนวน report (มากไปน้อย)

    public List<Rumour> getRumoursSortedByReportCountDesc() {
        List<Rumour> rumours = getAllRumours();

        Collections.sort(rumours, new Comparator<Rumour>() {
            @Override
            public int compare(Rumour r1, Rumour r2) {
                return r2.getReportCount() - r1.getReportCount(); // descending
            }
        });

        return rumours;
    }

    // ดึงข่าวลือเรียงตามจำนวน report (น้อยไปมาก)
    public List<Rumour> getRumoursSortedByReportCountAsc() {
        List<Rumour> rumours = getAllRumours();

        Collections.sort(rumours, new Comparator<Rumour>() {
            @Override
            public int compare(Rumour r1, Rumour r2) {
                return r1.getReportCount() - r2.getReportCount(); // ascending
            }
        });

        return rumours;
    }

    // ดึงข่าวลือเรียงตาม credibilityScore (น้อยไปมาก)
    public List<Rumour> getRumoursSortedByCredibilityAsc() {
        List<Rumour> rumours = getAllRumours();

        Collections.sort(rumours, new Comparator<Rumour>() {
            @Override
            public int compare(Rumour r1, Rumour r2) {
                return r1.getCredibilityScore() - r2.getCredibilityScore(); // ascending
            }
        });

        return rumours;
    }

    // ดึงข่าวลือเรียงตาม credibilityScore (มากไปน้อย)
    public List<Rumour> getRumoursSortedByCredibilityDesc() {
        List<Rumour> rumours = getAllRumours();

        Collections.sort(rumours, new Comparator<Rumour>() {
            @Override
            public int compare(Rumour r1, Rumour r2) {
                return r2.getCredibilityScore() - r1.getCredibilityScore(); // descending
            }
        });

        return rumours;
    }

    // ตรวจสอบและอัพเดทสถานะ PANIC
    public void checkAndUpdatePanicStatus(String rumourId) {
        Rumour rumour = rumourDAO.findById(rumourId);
        if (rumour == null)
            return;

        int reportCount = reportDAO.countReportsByRumour(rumourId);

        // ถ้า report >= threshold และยังไม่เป็น PANIC
        if (reportCount >= AppConfig.PANIC_THRESHOLD &&
                !Constants.STATUS_PANIC.equals(rumour.getStatus())) {

            rumour.setStatus(Constants.STATUS_PANIC);
            rumourDAO.update(rumour);
        }
    }

    // Verify ข่าวลือ (เฉพาะ VERIFIER)
    public boolean verifyRumour(String rumourId, String result) {
        if (!Constants.VERIFY_TRUE.equals(result) && !Constants.VERIFY_FALSE.equals(result)) {
            return false;
        }

        Rumour rumour = rumourDAO.findById(rumourId);
        if (rumour == null)
            return false;

        rumour.setVerified(true);
        rumour.setVerificationResult(result);
        rumourDAO.update(rumour);

        return true;
    }

    // ดึงข่าวที่สถานะ PANIC
    public List<Rumour> getPanicRumours() {
        List<Rumour> rumours = rumourDAO.getPanicRumours();
        for (Rumour rumour : rumours) {
            rumour.setReportCount(reportDAO.countReportsByRumour(rumour.getRumourId()));
        }
        return rumours;
    }

    // ดึงข่าวที่ถูก verify แล้ว
    public List<Rumour> getVerifiedRumours() {
        List<Rumour> rumours = rumourDAO.getVerifiedRumours();
        for (Rumour rumour : rumours) {
            rumour.setReportCount(reportDAO.countReportsByRumour(rumour.getRumourId()));
        }
        return rumours;
    }
}

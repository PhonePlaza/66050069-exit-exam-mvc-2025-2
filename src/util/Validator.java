package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Validator - ตรวจสอบความถูกต้องของข้อมูล
 */
public class Validator {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    static {
        DATE_FORMAT.setLenient(false); // ไม่อนุญาตวันที่ไม่ถูกต้อง เช่น 32/13/2026
    }

    /**
     * ตรวจสอบรหัสข่าวลือ
     * - ต้องมี 8 หลัก
     * - ตัวแรกต้องไม่เป็น 0
     */
    public static boolean validateRumourId(String id) {
        if (id == null || id.length() != 8) {
            return false;
        }
        // ตัวแรกต้องไม่เป็น 0
        if (id.charAt(0) == '0') {
            return false;
        }
        // ต้องเป็นตัวเลขทั้งหมด
        for (char c : id.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    // ตรวจสอบรูปแบบวันที่ DD/MM/YYYY
    public static boolean validateDate(String date) {
        if (date == null || date.isEmpty()) {
            return false;
        }
        try {
            DATE_FORMAT.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // ตรวจสอบ String ว่าไม่ว่าง
    public static boolean validateNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // ตรวจสอบคะแนนความน่าเชื่อถือ (0-100)
    public static boolean validateCredibilityScore(int score) {
        return score >= 0 && score <= 100;
    }

    // ตรวจสอบจำนวนเต็มบวก (>= 0)
    public static boolean validatePositiveInt(int value) {
        return value >= 0;
    }

    // ตรวจสอบประเภทรายงาน
    public static boolean validateReportType(String type) {
        if (type == null)
            return false;
        return type.equals(Constants.REPORT_DISTORTED) ||
                type.equals(Constants.REPORT_INCITE) ||
                type.equals(Constants.REPORT_FALSE_INFO);
    }

    // ตรวจสอบบทบาทผู้ใช้
    public static boolean validateUserRole(String role) {
        if (role == null)
            return false;
        return role.equals(Constants.ROLE_REGULAR_USER) ||
                role.equals(Constants.ROLE_VERIFIER);
    }

    // ตรวจสอบสถานะข่าวลือ
    public static boolean validateRumourStatus(String status) {
        if (status == null)
            return false;
        return status.equals(Constants.STATUS_NORMAL) ||
                status.equals(Constants.STATUS_PANIC);
    }
}

package util;

/**
 * Constants สำหรับระบบติดตามข่าวลือ
 * เก็บค่าคงที่และ Display Names
 */
public class Constants {

    // Report Types
    public static final String REPORT_DISTORTED = "DISTORTED";
    public static final String REPORT_INCITE = "INCITE";
    public static final String REPORT_FALSE_INFO = "FALSE_INFO";

    // User Roles
    public static final String ROLE_REGULAR_USER = "REGULAR_USER";
    public static final String ROLE_VERIFIER = "VERIFIER";

    // Rumour Status
    public static final String STATUS_NORMAL = "NORMAL";
    public static final String STATUS_PANIC = "PANIC";

    // Verification Results
    public static final String VERIFY_TRUE = "TRUE";
    public static final String VERIFY_FALSE = "FALSE";

    // Display Names (Thai)
    public static String getReportTypeDisplay(String type) {
        if (type == null)
            return "";
        switch (type) {
            case REPORT_DISTORTED:
                return "บิดเบือน";
            case REPORT_INCITE:
                return "ปลุกปั่น";
            case REPORT_FALSE_INFO:
                return "ข้อมูลเท็จ";
            default:
                return type;
        }
    }

    // แปลง User Role เป็นภาษาไทย
    public static String getUserRoleDisplay(String role) {
        if (role == null)
            return "";
        switch (role) {
            case ROLE_REGULAR_USER:
                return "ผู้ใช้ทั่วไป";
            case ROLE_VERIFIER:
                return "ผู้ตรวจสอบ";
            default:
                return role;
        }
    }

    // แปลง Status เป็นภาษาไทย

    public static String getStatusDisplay(String status) {
        if (status == null)
            return "";
        switch (status) {
            case STATUS_NORMAL:
                return "ปกติ";
            case STATUS_PANIC:
                return "ตื่นตระหนก";
            default:
                return status;
        }
    }

    // แปลง Verification Result เป็นภาษาไทย
    public static String getVerificationDisplay(String result) {
        if (result == null)
            return "ยังไม่ตรวจสอบ";
        switch (result) {
            case VERIFY_TRUE:
                return "ข่าวจริง";
            case VERIFY_FALSE:
                return "ข่าวเท็จ";
            default:
                return result;
        }
    }
}

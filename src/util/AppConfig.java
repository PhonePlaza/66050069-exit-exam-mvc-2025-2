package util;

/**
 * AppConfig - การตั้งค่าระบบ
 */
public class AppConfig {

    /**
     * จำนวน report ที่ทำให้ข่าวลือเปลี่ยนสถานะเป็น PANIC
     * ถ้า report >= PANIC_THRESHOLD -> status = PANIC
     */
    public static final int PANIC_THRESHOLD = 5;

    // Path ไปยังโฟลเดอร์ data
    public static final String DATA_PATH = "data/";

    // ชื่อไฟล์ CSV
    public static final String RUMOURS_FILE = DATA_PATH + "rumours.csv";
    public static final String REPORTS_FILE = DATA_PATH + "reports.csv";
    public static final String USERS_FILE = DATA_PATH + "users.csv";
}

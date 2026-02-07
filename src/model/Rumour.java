package model;

/**
 * Rumour - Entity class สำหรับข่าวลือ
 */
public class Rumour {

    private String rumourId; // 8 หลัก, ไม่ขึ้นต้นด้วย 0
    private String title;
    private String source;
    private String createdDate; // DD/MM/YYYY
    private int credibilityScore; // 0-100
    private String status; // NORMAL / PANIC
    private boolean verified;
    private String verificationResult; // TRUE / FALSE / null

    // Transient field - ไม่บันทึกลง CSV, คำนวณตอน runtime
    private transient int reportCount;

    // Constructors
    public Rumour() {
    }

    public Rumour(String rumourId, String title, String source, String createdDate,
            int credibilityScore, String status, boolean verified, String verificationResult) {
        this.rumourId = rumourId;
        this.title = title;
        this.source = source;
        this.createdDate = createdDate;
        this.credibilityScore = credibilityScore;
        this.status = status;
        this.verified = verified;
        this.verificationResult = verificationResult;
    }

    // ==================== Getters & Setters ====================

    public String getRumourId() {
        return rumourId;
    }

    public void setRumourId(String rumourId) {
        this.rumourId = rumourId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getCredibilityScore() {
        return credibilityScore;
    }

    public void setCredibilityScore(int credibilityScore) {
        this.credibilityScore = credibilityScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getVerificationResult() {
        return verificationResult;
    }

    public void setVerificationResult(String verificationResult) {
        this.verificationResult = verificationResult;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }

    @Override
    public String toString() {
        return "Rumour{" +
                "rumourId='" + rumourId + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", reportCount=" + reportCount +
                '}';
    }
}

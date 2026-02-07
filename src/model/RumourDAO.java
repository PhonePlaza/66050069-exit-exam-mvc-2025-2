package model;

import util.AppConfig;
import util.CSVHelper;
import util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * RumourDAO - Data Access Object สำหรับ Rumour
 */
public class RumourDAO {

    private static final String[] HEADER = {
            "rumourId", "title", "source", "createdDate",
            "credibilityScore", "status", "verified", "verificationResult"
    };

    private CSVHelper csvHelper = CSVHelper.getInstance();

    // ดึงข่าวลือทั้งหมด
    public List<Rumour> findAll() {
        List<Rumour> rumours = new ArrayList<>();
        List<String[]> data = csvHelper.readCSV(AppConfig.RUMOURS_FILE);

        for (String[] row : data) {
            if (row.length >= 8) {
                Rumour rumour = parseRumour(row);
                rumours.add(rumour);
            }
        }

        return rumours;
    }

    // ค้นหาข่าวลือตาม ID
    public Rumour findById(String rumourId) {
        List<Rumour> rumours = findAll();
        for (Rumour rumour : rumours) {
            if (rumour.getRumourId().equals(rumourId)) {
                return rumour;
            }
        }
        return null;
    }

    // บันทึกข่าวลือใหม่
    public void save(Rumour rumour) {
        String[] row = toRow(rumour);
        csvHelper.appendCSV(AppConfig.RUMOURS_FILE, row);
    }

    // อัพเดทข่าวลือ
    public void update(Rumour updatedRumour) {
        List<Rumour> rumours = findAll();
        List<String[]> data = new ArrayList<>();

        for (Rumour rumour : rumours) {
            if (rumour.getRumourId().equals(updatedRumour.getRumourId())) {
                data.add(toRow(updatedRumour));
            } else {
                data.add(toRow(rumour));
            }
        }

        csvHelper.writeCSV(AppConfig.RUMOURS_FILE, HEADER, data);
    }

    // ดึงข่าวลือที่สถานะ PANIC
    public List<Rumour> getPanicRumours() {
        List<Rumour> result = new ArrayList<>();
        List<Rumour> rumours = findAll();

        for (Rumour rumour : rumours) {
            if (Constants.STATUS_PANIC.equals(rumour.getStatus())) {
                result.add(rumour);
            }
        }

        return result;
    }

    // ดึงข่าวลือที่ถูก verify แล้ว
    public List<Rumour> getVerifiedRumours() {
        List<Rumour> result = new ArrayList<>();
        List<Rumour> rumours = findAll();

        for (Rumour rumour : rumours) {
            if (rumour.isVerified()) {
                result.add(rumour);
            }
        }

        return result;
    }

    // Helper Methods

    private Rumour parseRumour(String[] row) {
        Rumour rumour = new Rumour();
        rumour.setRumourId(row[0]);
        rumour.setTitle(row[1]);
        rumour.setSource(row[2]);
        rumour.setCreatedDate(row[3]);
        rumour.setCredibilityScore(parseIntSafe(row[4]));
        rumour.setStatus(row[5]);
        rumour.setVerified(parseBooleanSafe(row[6]));
        rumour.setVerificationResult(row.length > 7 && !row[7].isEmpty() ? row[7] : null);
        return rumour;
    }

    private String[] toRow(Rumour rumour) {
        return new String[] {
                rumour.getRumourId(),
                rumour.getTitle(),
                rumour.getSource(),
                rumour.getCreatedDate(),
                String.valueOf(rumour.getCredibilityScore()),
                rumour.getStatus(),
                String.valueOf(rumour.isVerified()),
                rumour.getVerificationResult() != null ? rumour.getVerificationResult() : ""
        };
    }

    private int parseIntSafe(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private boolean parseBooleanSafe(String value) {
        return "true".equalsIgnoreCase(value);
    }
}

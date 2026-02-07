package model;

import util.AppConfig;
import util.CSVHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * UserDAO - Data Access Object สำหรับ User
 */
public class UserDAO {

    private static final String[] HEADER = {
            "userId", "name", "role"
    };

    private CSVHelper csvHelper = CSVHelper.getInstance();

    // ดึง User ทั้งหมด
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        List<String[]> data = csvHelper.readCSV(AppConfig.USERS_FILE);

        for (String[] row : data) {
            if (row.length >= 3) {
                User user = parseUser(row);
                users.add(user);
            }
        }

        return users;
    }

    // ค้นหา User ตาม ID
    public User findById(String userId) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    // บันทึก User ใหม่
    public void save(User user) {
        String[] row = toRow(user);
        csvHelper.appendCSV(AppConfig.USERS_FILE, row);
    }

    // Helper Methods
    private User parseUser(String[] row) {
        User user = new User();
        user.setUserId(row[0]);
        user.setName(row[1]);
        user.setRole(row[2]);
        return user;
    }

    private String[] toRow(User user) {
        return new String[] {
                user.getUserId(),
                user.getName(),
                user.getRole()
        };
    }
}

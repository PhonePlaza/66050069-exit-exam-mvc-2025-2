package controller;

import model.User;
import model.UserDAO;

import java.util.List;

/**
 * UserController - ควบคุม logic เกี่ยวกับผู้ใช้
 */
public class UserController {

    private UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    // ดึงผู้ใช้ทั้งหมด
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    // ค้นหาผู้ใช้ตาม ID
    public User getUserById(String userId) {
        return userDAO.findById(userId);
    }

    // ดึงเฉพาะผู้ตรวจสอบ (VERIFIER)
    public java.util.List<User> getVerifiers() {
        java.util.List<User> all = userDAO.findAll();
        java.util.List<User> verifiers = new java.util.ArrayList<>();
        for (User user : all) {
            if ("VERIFIER".equals(user.getRole())) {
                verifiers.add(user);
            }
        }
        return verifiers;
    }
}

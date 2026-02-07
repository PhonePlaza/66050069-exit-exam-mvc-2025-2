package model;

/**
 * User - Entity class สำหรับผู้ใช้งาน
 */
public class User {

    private String userId;
    private String name;
    private String role; // REGULAR_USER / VERIFIER

    // Constructors
    public User() {
    }

    public User(String userId, String name, String role) {
        this.userId = userId;
        this.name = name;
        this.role = role;
    }

    // Getters & Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

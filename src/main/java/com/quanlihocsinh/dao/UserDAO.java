package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Person;
import com.quanlihocsinh.model.User;
import com.quanlihocsinh.util.DBUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserDAO {

    private PersonDAO personDAO = new PersonDAO();

    public User checkLogin(String username, String plainPassword) throws SQLException {
        String sql = "SELECT user_id, username, password_hash, role_id, person_id, is_active, created_at FROM Users WHERE username = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hash = rs.getString("password_hash");
                    boolean isActive = rs.getBoolean("is_active");
                    if (!isActive)
                        return null;

                    if (BCrypt.checkpw(plainPassword, hash)) {
                        User u = new User();
                        u.setUserId(rs.getInt("user_id"));
                        u.setUsername(rs.getString("username"));
                        u.setPasswordHash(hash);
                        u.setRoleId(rs.getInt("role_id"));
                        u.setPersonId(rs.getInt("person_id"));
                        u.setIsActive(isActive);
                        u.setCreatedAt(rs.getTimestamp("created_at"));

                        if (u.getPersonId() > 0) {
                            Person p = personDAO.getById(u.getPersonId());
                            u.setProfile(p);
                        }
                        return u;
                    } else {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public void createUserLinkedToPerson(String username, String plainPassword, int roleId, Integer personId)
            throws SQLException {

        String checkUserSql = "SELECT COUNT(1) FROM Users WHERE username = ?";
        String checkLinkedSql = "SELECT COUNT(1) FROM Users WHERE person_id = ?";
        String insertSql = "INSERT INTO Users(username, password_hash, role_id, person_id, is_active, created_at) VALUES (?, ?, ?, ?, 1, GETDATE())";

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            // Check username trùng
            try (PreparedStatement ps = conn.prepareStatement(checkUserSql)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    throw new SQLException("Username already exists");
                }
            }

            // Nếu có personId → kiểm tra liên kết
            if (personId != null) {
                try (PreparedStatement ps = conn.prepareStatement(checkLinkedSql)) {
                    ps.setInt(1, personId);
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    if (rs.getInt(1) > 0) {
                        throw new SQLException("Profile already linked to another account");
                    }
                }
            }

            String hashed = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

            // INSERT — cho phép personId NULL
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, username);
                ps.setString(2, hashed);
                ps.setInt(3, roleId);

                if (personId == null) {
                    ps.setNull(4, Types.INTEGER);
                } else {
                    ps.setInt(4, personId);
                }

                ps.executeUpdate();
            }

            conn.commit();
        }
    }

}

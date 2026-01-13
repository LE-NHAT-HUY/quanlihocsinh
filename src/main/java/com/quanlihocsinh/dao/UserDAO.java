package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Person;
import com.quanlihocsinh.model.User;
import com.quanlihocsinh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public User checkLogin(String username, String password) throws Exception {
        String sql = "SELECT * FROM Users WHERE username = ? AND password_hash = ?";
        User user = null;

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setUserID(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setRoleId(rs.getInt("role_id"));
                    user.setPersonId(rs.getInt("person_id"));

                    user.setProfile(getPersonProfile(conn, user.getPersonId()));

                    if (user.getRoleId() == 2) {

                        String sqlT = "SELECT ID FROM tblTeacher WHERE PersonID = ?";
                        try (PreparedStatement psT = conn.prepareStatement(sqlT)) {
                            psT.setInt(1, user.getPersonId());
                            ResultSet rsT = psT.executeQuery();
                            if (rsT.next())
                                user.setEntityId(rsT.getInt("ID"));
                        }
                    } else if (user.getRoleId() == 3) {

                        String sqlS = "SELECT ID FROM tblStudent WHERE PersonID = ?";
                        try (PreparedStatement psS = conn.prepareStatement(sqlS)) {
                            psS.setInt(1, user.getPersonId());
                            ResultSet rsS = psS.executeQuery();
                            if (rsS.next())
                                user.setEntityId(rsS.getInt("ID"));
                        }
                    }
                }
            }
        }
        return user;
    }

    private Person getPersonProfile(Connection conn, int personId) {
        Person p = new Person();
        String sql = "SELECT person_id, fullname, birth, gender, address, phone, images, is_active FROM Person WHERE person_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, personId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p.setPersonId(rs.getInt("person_id"));
                p.setFullName(rs.getString("fullname"));
                p.setBirth(rs.getDate("birth"));
                p.setGender(rs.getString("gender"));
                p.setAddress(rs.getString("address"));
                p.setPhone(rs.getString("phone"));
                p.setImages(rs.getString("images"));
                p.setActive(rs.getBoolean("is_active"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public void createAccount(String username, String password, int roleId, String fullName) throws Exception {
        Connection conn = DBUtil.getConnection();
        try {
            conn.setAutoCommit(false);

            String sqlPerson = "INSERT INTO Person(fullname, is_active) VALUES(?, 1)";
            int newPersonId = 0;
            try (PreparedStatement ps = conn.prepareStatement(sqlPerson, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, fullName);
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next())
                    newPersonId = rs.getInt(1);
            }

            String sqlUser = "INSERT INTO Users(username, password_hash, role_id, person_id, is_active, created_at) VALUES(?, ?, ?, ?, 1, GETDATE())";
            try (PreparedStatement ps = conn.prepareStatement(sqlUser)) {
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setInt(3, roleId);
                ps.setInt(4, newPersonId);
                ps.executeUpdate();
            }

            if (roleId == 2) {

                String sqlT = "INSERT INTO tblTeacher(TeacherID, FullName, PersonID, IsActive) VALUES(?, ?, ?, 1)";
                try (PreparedStatement ps = conn.prepareStatement(sqlT)) {
                    ps.setString(1, username);
                    ps.setString(2, fullName);
                    ps.setInt(3, newPersonId);
                    ps.executeUpdate();
                }
            } else if (roleId == 3) {

                String sqlS = "INSERT INTO tblStudent(StudentID, FullName, PersonID, IsActive) VALUES(?, ?, ?, 1)";
                try (PreparedStatement ps = conn.prepareStatement(sqlS)) {
                    ps.setString(1, username);
                    ps.setString(2, fullName);
                    ps.setInt(3, newPersonId);
                    ps.executeUpdate();
                }
            }

            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        String sql = "SELECT u.*, p.fullname, p.images FROM Users u " +
                "LEFT JOIN Person p ON u.person_id = p.person_id " +
                "WHERE u.is_active = 1";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setRoleId(rs.getInt("role_id"));
                u.setPersonId(rs.getInt("person_id"));

                Person p = new Person();
                p.setFullName(rs.getString("fullname"));
                p.setImages(rs.getString("images"));
                u.setProfile(p);

                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
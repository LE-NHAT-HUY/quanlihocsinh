package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Person;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    // Lấy thông tin Person theo ID
    public Person getById(int personId) throws SQLException {
        // SQL lấy các trường thông tin cá nhân cơ bản
        // Lưu ý: Đã bỏ original_table, original_id, person_type để khớp với DB mới
        String sql = "SELECT person_id, fullname, birth, gender, address, phone, images, is_active FROM Person WHERE person_id = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, personId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    // Hàm lấy danh sách Person chưa liên kết (Dùng cho dropdown khi tạo User)
    public List<Person> getUnlinkedPersons(String personType) throws SQLException {
        // Logic mới: Chỉ lấy những Person chưa có trong bảng Users
        String sql = "SELECT p.person_id, p.fullname, p.is_active " +
                "FROM Person p LEFT JOIN Users u ON p.person_id = u.person_id " +
                "WHERE u.user_id IS NULL";

        // Lưu ý: Tham số personType không còn được sử dụng trong SQL nữa
        // do hệ thống mới phân quyền qua bảng Users

        List<Person> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            // Nếu bạn muốn lọc kỹ hơn, có thể thêm điều kiện vào SQL,
            // hiện tại ta lấy tất cả person chưa có tài khoản.

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Person p = new Person();
                    p.setPersonId(rs.getInt("person_id"));

                    // SỬA QUAN TRỌNG: setFullName (viết hoa chữ N)
                    p.setFullName(rs.getString("fullname"));

                    p.setActive(rs.getBoolean("is_active"));

                    list.add(p);
                }
            }
        }
        return list;
    }

    // Hàm ánh xạ từ ResultSet sang Object Person
    private Person mapRow(ResultSet rs) throws SQLException {
        Person p = new Person();

        p.setPersonId(rs.getInt("person_id"));

        // SỬA QUAN TRỌNG: setFullName (viết hoa chữ N để khớp với Model Person)
        p.setFullName(rs.getString("fullname"));

        p.setBirth(rs.getDate("birth"));
        p.setGender(rs.getString("gender"));
        p.setAddress(rs.getString("address"));
        p.setPhone(rs.getString("phone"));
        p.setImages(rs.getString("images"));
        p.setActive(rs.getBoolean("is_active"));

        // Các trường cũ đã được loại bỏ để tránh lỗi:
        // p.setOriginalTable(...);
        // p.setPersonType(...);

        return p;
    }

    // Thêm vào file src/java/com/quanlihocsinh/dao/PersonDAO.java

    public void update(Person p) throws SQLException {
        String sql = "UPDATE Person SET fullname=?, birth=?, gender=?, address=?, phone=? WHERE person_id=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getFullName());
            ps.setDate(2, p.getBirth());
            ps.setString(3, p.getGender());
            ps.setString(4, p.getAddress());
            ps.setString(5, p.getPhone());
            ps.setInt(6, p.getPersonId());

            ps.executeUpdate();
        }
    }
}
package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Person;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    public Person getById(int personId) throws SQLException {
        String sql = "SELECT person_id, original_table, original_id, fullname, birth, gender, address, phone, images, person_type, is_active FROM Person WHERE person_id = ?";
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

    public List<Person> getUnlinkedPersons(String personType) throws SQLException {
        String sql = "SELECT p.person_id, p.fullname, p.person_type, p.is_active " +
                "FROM Person p LEFT JOIN Users u ON p.person_id = u.person_id " +
                "WHERE u.user_id IS NULL AND (p.person_type = ? OR ? IS NULL)";

        List<Person> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, personType);
            ps.setString(2, personType);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Person p = new Person();
                    p.setPersonId(rs.getInt("person_id"));
                    p.setFullname(rs.getString("fullname"));
                    p.setPersonType(rs.getString("person_type"));
                    p.setActive(rs.getBoolean("is_active"));
                    list.add(p);
                }
            }
        }
        return list;
    }

    private Person mapRow(ResultSet rs) throws SQLException {
        Person p = new Person();

        p.setPersonId(rs.getInt("person_id"));
        p.setOriginalTable(rs.getString("original_table"));
        p.setOriginalId(rs.getInt("original_id"));
        p.setFullname(rs.getString("fullname"));
        p.setBirth(rs.getDate("birth"));
        p.setGender(rs.getString("gender"));
        p.setAddress(rs.getString("address"));
        p.setPhone(rs.getString("phone"));
        p.setImages(rs.getString("images"));
        p.setPersonType(rs.getString("person_type"));
        p.setActive(rs.getBoolean("is_active"));

        return p;
    }
}

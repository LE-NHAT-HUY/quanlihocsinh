package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Person;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    public Person getById(int personId) throws SQLException {

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

    public List<Person> getUnlinkedPersons(String personType) throws SQLException {
        String sql = "SELECT p.person_id, p.fullname, p.is_active " +
                "FROM Person p LEFT JOIN Users u ON p.person_id = u.person_id " +
                "WHERE u.user_id IS NULL";

        List<Person> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Person p = new Person();
                    p.setPersonId(rs.getInt("person_id"));

                    p.setFullName(rs.getString("fullname"));

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

        p.setFullName(rs.getString("fullname"));

        p.setBirth(rs.getDate("birth"));
        p.setGender(rs.getString("gender"));
        p.setAddress(rs.getString("address"));
        p.setPhone(rs.getString("phone"));
        p.setImages(rs.getString("images"));
        p.setActive(rs.getBoolean("is_active"));

        return p;
    }

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
package com.quanlihocsinh.dao;

import com.quanlihocsinh.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StatisticsDAO {

    private int countTable(String tableName) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countStudents() {
        return countTable("tblStudent");
    }

    public int countTeachers() {
        return countTable("tblTeacher");
    }

    public int countClasses() {
        return countTable("tblClass");
    }

    public int countSubjects() {
        return countTable("tblSubject");
    }
}
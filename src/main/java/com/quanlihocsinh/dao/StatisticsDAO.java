package com.quanlihocsinh.dao;

import com.quanlihocsinh.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StatisticsDAO {

    // Hàm chung để đếm số lượng từ bất kỳ bảng nào
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

    // 1. Đếm Học sinh
    // LƯU Ý: Kiểm tra tên bảng trong SQL của bạn là 'Student' hay 'tblStudent'
    public int countStudents() {
        return countTable("tblStudent"); // Hoặc "tblStudent"
    }

    // 2. Đếm Giáo viên
    // LƯU Ý: Kiểm tra tên bảng là 'Teacher' hay 'tblTeacher'
    public int countTeachers() {
        return countTable("tblTeacher"); // Hoặc "Teacher"
    }

    // 3. Đếm Lớp học
    public int countClasses() {
        return countTable("tblClass");
    }

    // 4. Đếm Môn học
    public int countSubjects() {
        return countTable("tblSubject");
    }
}
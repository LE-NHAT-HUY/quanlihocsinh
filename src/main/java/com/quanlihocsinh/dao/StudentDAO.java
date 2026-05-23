package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Student;
import com.quanlihocsinh.model.tblClass;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("ID"));
        s.setStudentID(rs.getString("StudentID"));
        s.setFullName(rs.getString("FullName"));

        Date birth = rs.getDate("Birth");
        if (birth != null) {
            s.setBirth(new java.util.Date(birth.getTime()));
        }

        s.setGender(rs.getString("Gender"));
        s.setAddress(rs.getString("Address"));
        s.setNation(rs.getString("Nation"));
        s.setReligion(rs.getString("Religion"));
        s.setStatusStudent(rs.getString("StatusStudent"));
        s.setNumberPhone(rs.getString("NumberPhone"));
        s.setIsActive(rs.getBoolean("IsActive"));
        s.setImages(rs.getString("Images"));
        s.setHamlet(rs.getString("Hamlet"));
        s.setCommune(rs.getString("Commune"));
        s.setProvince(rs.getString("Province"));
        s.setNationality(rs.getString("Nationality"));

        return s;
    }

    public List<Student> getAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM dbo.tblStudent ORDER BY FullName";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToStudent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Student> getStudentsNotInClass(int classId) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT s.* FROM dbo.tblStudent s " +
                "WHERE s.StudentID NOT IN (SELECT sc.StudentID FROM dbo.studentclass sc WHERE sc.ClassID = ?) " +
                "AND s.IsActive = 1 " +
                "ORDER BY s.FullName";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, classId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToStudent(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Student getById(int id) {
        String sql = "SELECT * FROM dbo.tblStudent WHERE ID=?";
        Student s = null;

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    s = mapResultSetToStudent(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }

    public int add(Student s) throws SQLException {
        String personSql = "INSERT INTO Person(fullname, birth, gender, address, phone, images, is_active) " +
                "VALUES (?, ?, ?, ?, ?, ?, 1)";
        String studentSql = "INSERT INTO dbo.tblStudent " +
                "(StudentID, FullName, PersonID, Birth, Gender, Address, Nation, Religion, StatusStudent, NumberPhone, IsActive, Images, Hamlet, Commune, Province, Nationality) "
                +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Connection conn = DBUtil.getConnection();
        try {
            conn.setAutoCommit(false);
            System.out.println("[StudentDAO.add] Preparing transactional INSERT for studentID=" + s.getStudentID());

            int personId;
            try (PreparedStatement personPs = conn.prepareStatement(personSql, Statement.RETURN_GENERATED_KEYS)) {
                personPs.setString(1, s.getFullName());
                if (s.getBirth() != null) {
                    personPs.setDate(2, new java.sql.Date(s.getBirth().getTime()));
                } else {
                    personPs.setNull(2, Types.DATE);
                }
                personPs.setString(3, s.getGender());
                personPs.setString(4, s.getAddress());
                personPs.setString(5, s.getNumberPhone());
                personPs.setString(6, s.getImages());

                personPs.executeUpdate();
                try (ResultSet rs = personPs.getGeneratedKeys()) {
                    if (!rs.next()) {
                        throw new SQLException("Không lấy được person_id sau khi insert Person");
                    }
                    personId = rs.getInt(1);
                }
            }

            int affected;
            try (PreparedStatement studentPs = conn.prepareStatement(studentSql)) {
                studentPs.setString(1, s.getStudentID());
                studentPs.setString(2, s.getFullName());
                studentPs.setInt(3, personId);

                if (s.getBirth() != null)
                    studentPs.setDate(4, new java.sql.Date(s.getBirth().getTime()));
                else
                    studentPs.setNull(4, Types.DATE);

                studentPs.setString(5, s.getGender());
                studentPs.setString(6, s.getAddress());
                studentPs.setString(7, s.getNation());
                studentPs.setString(8, s.getReligion());
                studentPs.setString(9, s.getStatusStudent());
                studentPs.setString(10, s.getNumberPhone());
                studentPs.setBoolean(11, s.isIsActive());
                studentPs.setString(12, s.getImages());
                studentPs.setString(13, s.getHamlet());
                studentPs.setString(14, s.getCommune());
                studentPs.setString(15, s.getProvince());
                studentPs.setString(16, s.getNationality());

                affected = studentPs.executeUpdate();
            }

            conn.commit();
            System.out.println("[StudentDAO.add] INSERT affected rows=" + affected + ", personId=" + personId);
            return affected;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public void update(Student s) {
        String sql = "UPDATE dbo.tblStudent SET " +
                "StudentID=?, FullName=?, Birth=?, Gender=?, Address=?, Nation=?, Religion=?, StatusStudent=?, NumberPhone=?, "
                +
                "IsActive=?, Images=?, Hamlet=?, Commune=?, Province=?, Nationality=? WHERE ID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getStudentID());
            ps.setString(2, s.getFullName());

            if (s.getBirth() != null)
                ps.setDate(3, new java.sql.Date(s.getBirth().getTime()));
            else
                ps.setNull(3, Types.DATE);

            ps.setString(4, s.getGender());
            ps.setString(5, s.getAddress());
            ps.setString(6, s.getNation());
            ps.setString(7, s.getReligion());
            ps.setString(8, s.getStatusStudent());
            ps.setString(9, s.getNumberPhone());
            ps.setBoolean(10, s.isIsActive());
            ps.setString(11, s.getImages());
            ps.setString(12, s.getHamlet());
            ps.setString(13, s.getCommune());
            ps.setString(14, s.getProvince());
            ps.setString(15, s.getNationality());

            ps.setInt(16, s.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM dbo.tblStudent WHERE ID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void toggleStatus(int id, boolean status) {
        String sql = "UPDATE dbo.tblStudent SET IsActive=? WHERE ID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Student getByStudentId(String studentId) {
        String sql = "SELECT * FROM dbo.tblStudent WHERE StudentID=?";
        Student s = null;

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    s = mapResultSetToStudent(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }

    public tblClass getCurrentClassByStudentId(String studentId) {
        tblClass cls = null;

        String sql = "SELECT c.* " +
                "FROM dbo.tblClass c " +
                "INNER JOIN dbo.tblStudentClass sc ON c.ClassID = sc.ClassID " +
                "WHERE sc.StudentID = ? AND c.IsActive = 1";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cls = new tblClass();
                    cls.setClassID(rs.getInt("ClassID"));
                    cls.setClassName(rs.getString("ClassName"));
                    cls.setGradeID(rs.getInt("GradeID"));
                    cls.setCohortID(rs.getInt("CohortID"));
                    cls.setSchoolYear(rs.getString("SchoolYear"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cls;
    }

    public void updateProfileInfo(int studentID, String fullName, Date birth, String gender, String address,
            String phone,
            String hamlet, String commune, String province, String nation, String religion, String nationality)
            throws SQLException {

        String sql = "UPDATE tblStudent SET FullName=?, Birth=?, Gender=?, Address=?, NumberPhone=?, " +
                "Hamlet=?, Commune=?, Province=?, Nation=?, Religion=?, Nationality=? " +
                "WHERE ID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fullName);
            ps.setDate(2, birth);
            ps.setString(3, gender);
            ps.setString(4, address);
            ps.setString(5, phone);

            ps.setString(6, hamlet);
            ps.setString(7, commune);
            ps.setString(8, province);
            ps.setString(9, nation);
            ps.setString(10, religion);
            ps.setString(11, nationality);

            ps.setInt(12, studentID);

            ps.executeUpdate();
        }
    }

}

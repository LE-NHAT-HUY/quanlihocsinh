package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.TeacherContract;
import com.quanlihocsinh.util.DBUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class TeacherContractDAO {

    private TeacherContract map(ResultSet rs) throws SQLException {
        TeacherContract contract = new TeacherContract();
        contract.setContractID(rs.getInt("ContractID"));
        contract.setTeacherID(rs.getInt("TeacherID"));
        contract.setContractNumber(rs.getString("ContractNumber"));
        contract.setContractType(rs.getString("ContractType"));
        contract.setSignDate(rs.getDate("SignDate"));
        contract.setStartDate(rs.getDate("StartDate"));
        contract.setEndDate(rs.getDate("EndDate"));
        contract.setSalaryCoefficient(rs.getBigDecimal("SalaryCoefficient"));
        contract.setBaseSalary(rs.getBigDecimal("BaseSalary"));
        contract.setContractStatus(rs.getString("ContractStatus"));

        try {
            contract.setTeacherName(rs.getString("TeacherName"));
        } catch (SQLException ignored) {
        }

        return contract;
    }

    public List<TeacherContract> getAllByTeacher(int teacherID) {
        List<TeacherContract> list = new ArrayList<>();
        String sql = "SELECT c.*, t.FullName AS TeacherName " +
                "FROM tblTeacherContract c " +
                "JOIN tblTeacher t ON c.TeacherID = t.ID " +
                "WHERE c.TeacherID = ? " +
                "ORDER BY c.ContractID DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teacherID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<TeacherContract> getAll() {
        List<TeacherContract> list = new ArrayList<>();
        String sql = "SELECT c.*, t.FullName AS TeacherName " +
                "FROM tblTeacherContract c " +
                "JOIN tblTeacher t ON c.TeacherID = t.ID " +
                "ORDER BY c.ContractID DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public TeacherContract getById(int contractID) {
        String sql = "SELECT c.*, t.FullName AS TeacherName " +
                "FROM tblTeacherContract c " +
                "JOIN tblTeacher t ON c.TeacherID = t.ID " +
                "WHERE c.ContractID = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, contractID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void add(TeacherContract contract) {
        String sql = "INSERT INTO tblTeacherContract(TeacherID, ContractNumber, ContractType, SignDate, StartDate, EndDate, SalaryCoefficient, BaseSalary, ContractStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, contract.getTeacherID());
            ps.setString(2, contract.getContractNumber());
            ps.setString(3, contract.getContractType());
            if (contract.getSignDate() != null) {
                ps.setDate(4, new Date(contract.getSignDate().getTime()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            if (contract.getStartDate() != null) {
                ps.setDate(5, new Date(contract.getStartDate().getTime()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            if (contract.getEndDate() != null) {
                ps.setDate(6, new Date(contract.getEndDate().getTime()));
            } else {
                ps.setNull(6, Types.DATE);
            }
            ps.setBigDecimal(7, contract.getSalaryCoefficient());
            ps.setBigDecimal(8, contract.getBaseSalary());
            ps.setString(9, contract.getContractStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to insert teacher contract", e);
        }
    }

    public void update(TeacherContract contract) {
        String sql = "UPDATE tblTeacherContract SET TeacherID=?, ContractNumber=?, ContractType=?, SignDate=?, StartDate=?, EndDate=?, SalaryCoefficient=?, BaseSalary=?, ContractStatus=? WHERE ContractID=?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, contract.getTeacherID());
            ps.setString(2, contract.getContractNumber());
            ps.setString(3, contract.getContractType());
            if (contract.getSignDate() != null) {
                ps.setDate(4, new Date(contract.getSignDate().getTime()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            if (contract.getStartDate() != null) {
                ps.setDate(5, new Date(contract.getStartDate().getTime()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            if (contract.getEndDate() != null) {
                ps.setDate(6, new Date(contract.getEndDate().getTime()));
            } else {
                ps.setNull(6, Types.DATE);
            }
            ps.setBigDecimal(7, contract.getSalaryCoefficient());
            ps.setBigDecimal(8, contract.getBaseSalary());
            ps.setString(9, contract.getContractStatus());
            ps.setInt(10, contract.getContractID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update teacher contract", e);
        }
    }

    public void delete(int contractID) {
        String sql = "DELETE FROM tblTeacherContract WHERE ContractID=?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, contractID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete teacher contract", e);
        }
    }
}

package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.TeacherContractDAO;
import com.quanlihocsinh.dao.TeacherDAO;
import com.quanlihocsinh.model.Teacher;
import com.quanlihocsinh.model.TeacherContract;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@WebServlet("/admin/teacher-contract")
public class TeacherContractController extends HttpServlet {

    private final TeacherContractDAO contractDAO = new TeacherContractDAO();
    private final TeacherDAO teacherDAO = new TeacherDAO();

    private void setFlash(HttpServletRequest request, String success, String error) {
        HttpSession session = request.getSession();
        if (success != null) {
            session.setAttribute("flashSuccess", success);
        }
        if (error != null) {
            session.setAttribute("flashError", error);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty() || "list".equalsIgnoreCase(action)) {
            showList(request, response);
            return;
        }

        switch (action.toLowerCase()) {
            case "add":
                request.setAttribute("mode", "add");
                showForm(request, response, null, "add");
                break;
            case "edit":
                int editId = parseIntOrZero(request.getParameter("id"));
                TeacherContract contract = contractDAO.getById(editId);
                if (contract == null) {
                    setFlash(request, null, "Không tìm thấy hợp đồng cần chỉnh sửa.");
                    response.sendRedirect(request.getContextPath() + "/admin/teacher-contract");
                    return;
                }
                showForm(request, response, contract, "edit");
                break;
            case "delete":
                int deleteId = parseIntOrZero(request.getParameter("id"));
                int teacherID = parseIntOrZero(request.getParameter("teacherID"));
                contractDAO.delete(deleteId);
                setFlash(request, "Đã xóa hợp đồng.", null);
                response.sendRedirect(buildListUrl(request, teacherID));
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/teacher-contract");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            request.setAttribute("flashError", "Thiếu hành động xử lý form.");
            request.setAttribute("teachers", teacherDAO.getAll());
            request.setAttribute("selectedTeacherId", parseIntOrZero(request.getParameter("teacherID")));
            request.setAttribute("contract", buildContractFromRequest(request));
            request.setAttribute("mode", "add");
            request.getRequestDispatcher("/WEB-INF/views/admin/teacher/contract/form.jsp").forward(request, response);
            return;
        }

        try {
            TeacherContract contract = buildContractFromRequest(request);
            if (contract.getTeacherID() <= 0) {
                request.setAttribute("flashError", "Vui lòng chọn giáo viên.");
                request.setAttribute("teachers", teacherDAO.getAll());
                request.setAttribute("selectedTeacherId", contract.getTeacherID());
                request.setAttribute("contract", contract);
                request.setAttribute("mode", "add");
                request.getRequestDispatcher("/WEB-INF/views/admin/teacher/contract/form.jsp").forward(request,
                        response);
                return;
            }

            if ("add".equalsIgnoreCase(action)) {
                contractDAO.add(contract);
                setFlash(request, "Thêm hợp đồng thành công.", null);
                response.sendRedirect(buildListUrl(request, contract.getTeacherID()));
            } else if ("edit".equalsIgnoreCase(action)) {
                contract.setContractID(parseIntOrZero(request.getParameter("id")));
                contractDAO.update(contract);
                setFlash(request, "Cập nhật hợp đồng thành công.", null);
                response.sendRedirect(buildListUrl(request, contract.getTeacherID()));
            } else if ("delete".equalsIgnoreCase(action)) {
                int contractId = parseIntOrZero(request.getParameter("id"));
                contractDAO.delete(contractId);
                setFlash(request, "Đã xóa hợp đồng.", null);
                response.sendRedirect(buildListUrl(request, contract.getTeacherID()));
            } else {
                request.setAttribute("flashError", "Hành động không hợp lệ: " + action);
                request.setAttribute("teachers", teacherDAO.getAll());
                request.setAttribute("selectedTeacherId", contract.getTeacherID());
                request.setAttribute("contract", contract);
                request.setAttribute("mode", "add");
                request.getRequestDispatcher("/WEB-INF/views/admin/teacher/contract/form.jsp").forward(request,
                        response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("flashError", "Không lưu được hợp đồng: " + e.getMessage());
            request.setAttribute("teachers", teacherDAO.getAll());
            request.setAttribute("selectedTeacherId", parseIntOrZero(request.getParameter("teacherID")));
            request.setAttribute("contract", buildContractFromRequest(request));
            request.setAttribute("mode", "add");
            request.getRequestDispatcher("/WEB-INF/views/admin/teacher/contract/form.jsp").forward(request, response);
        }
    }

    private void showList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int teacherID = parseIntOrZero(request.getParameter("teacherID"));
        List<Teacher> teachers = teacherDAO.getAll();
        request.setAttribute("teachers", teachers);
        request.setAttribute("selectedTeacherId", teacherID);
        request.setAttribute("selectedTeacher", teacherID > 0 ? teacherDAO.getById(teacherID) : null);
        request.setAttribute("contracts",
                teacherID > 0 ? contractDAO.getAllByTeacher(teacherID) : contractDAO.getAll());
        request.getRequestDispatcher("/WEB-INF/views/admin/teacher/contract/list.jsp").forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response, TeacherContract contract,
            String mode)
            throws ServletException, IOException {
        int teacherID = parseIntOrZero(request.getParameter("teacherID"));
        if (contract != null && teacherID <= 0) {
            teacherID = contract.getTeacherID();
        }
        request.setAttribute("teachers", teacherDAO.getAll());
        request.setAttribute("selectedTeacherId", teacherID);
        request.setAttribute("contract", contract);
        request.setAttribute("mode", mode);
        request.getRequestDispatcher("/WEB-INF/views/admin/teacher/contract/form.jsp").forward(request, response);
    }

    private TeacherContract buildContractFromRequest(HttpServletRequest request) {
        TeacherContract contract = new TeacherContract();
        contract.setTeacherID(parseIntOrZero(request.getParameter("teacherID")));
        contract.setContractNumber(request.getParameter("contractNumber"));
        contract.setContractType(request.getParameter("contractType"));
        contract.setSignDate(parseDateOrNull(request.getParameter("signDate")));
        contract.setStartDate(parseDateOrNull(request.getParameter("startDate")));
        contract.setEndDate(parseDateOrNull(request.getParameter("endDate")));
        contract.setSalaryCoefficient(parseBigDecimalOrNull(request.getParameter("salaryCoefficient")));
        contract.setBaseSalary(parseBigDecimalOrNull(request.getParameter("baseSalary")));
        contract.setContractStatus(request.getParameter("contractStatus"));
        return contract;
    }

    private int parseIntOrZero(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    private BigDecimal parseBigDecimalOrNull(String value) {
        try {
            if (value == null || value.trim().isEmpty()) {
                return null;
            }
            return new BigDecimal(value.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private Date parseDateOrNull(String value) {
        try {
            if (value == null || value.trim().isEmpty()) {
                return null;
            }
            return Date.valueOf(value.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private String buildListUrl(HttpServletRequest request, int teacherID) {
        String url = request.getContextPath() + "/admin/teacher-contract";
        if (teacherID > 0) {
            url += "?teacherID=" + teacherID;
        }
        return url;
    }
}

package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.StudentClassDAO;
import com.quanlihocsinh.dao.TblClassDAO;
import com.quanlihocsinh.dao.StudentDAO;
import com.quanlihocsinh.dao.CohortDAO;
import com.quanlihocsinh.model.Student;
import com.quanlihocsinh.model.StudentClass;
import com.quanlihocsinh.model.tblClass;
import com.quanlihocsinh.model.Cohort;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/studentclass/*")
public class StudentClassController extends HttpServlet {

    private StudentClassDAO scDAO = new StudentClassDAO();
    private TblClassDAO classDAO = new TblClassDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private CohortDAO cohortDAO = new CohortDAO();

    private int parseIntOrDefault(String value, int defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        return Integer.parseInt(value.trim());
    }

    private void setFlashMessage(HttpServletRequest request, String success, String error) {
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

        String action = request.getPathInfo();
        if (action == null || action.equals("/"))
            action = "/list";

        try {
            if (action.equals("/delete")) {
                String studentClassIDStr = request.getParameter("studentClassID");
                String classIDStr = request.getParameter("classID");
                String yearSemesterIDStr = request.getParameter("yearSemesterID");

                System.out.println("[StudentClassController.delete] studentClassID=" + studentClassIDStr
                        + ", classID=" + classIDStr
                        + ", yearSemesterID=" + yearSemesterIDStr);

                int studentClassID = parseIntOrDefault(studentClassIDStr, 0);
                int classID = parseIntOrDefault(classIDStr, 0);
                int yearSemesterID = parseIntOrDefault(yearSemesterIDStr, 1);

                if (studentClassID <= 0) {
                    setFlashMessage(request, null, "Không thể xóa: dữ liệu không hợp lệ");
                    response.sendRedirect(request.getContextPath() + "/admin/studentclass/list?classID=" + classID
                            + "&yearSemesterID=" + yearSemesterID);
                    return;
                }

                boolean deleted = scDAO.delete(studentClassID);
                System.out.println("[StudentClassController.delete] deleted=" + deleted);

                if (deleted) {
                    if (classID > 0) {
                        classDAO.decrementCurrentStudents(classID);
                    }
                    setFlashMessage(request, "Xóa học sinh khỏi lớp thành công", null);
                } else {
                    setFlashMessage(request, null, "Xóa học sinh khỏi lớp thất bại");
                }

                response.sendRedirect(request.getContextPath() + "/admin/studentclass/list?classID=" + classID
                        + "&yearSemesterID=" + yearSemesterID);
                return;
            }

            if (action.equals("/list")) {
                int classID = 0;
                String classIDStr = request.getParameter("classID");
                if (classIDStr != null && !classIDStr.isEmpty()) {
                    classID = Integer.parseInt(classIDStr);
                }

                int yearSemesterID = 1;
                String yearStr = request.getParameter("yearSemesterID");
                if (yearStr != null && !yearStr.isEmpty()) {
                    yearSemesterID = Integer.parseInt(yearStr);
                }

                List<StudentClass> studentsInClass = scDAO.getByClassAndYear(classID, yearSemesterID);
                List<tblClass> allClasses = classDAO.getAll();
                List<tblClass> classes = classDAO.getAllActive();

                // --- MỚI: LỌC DANH SÁCH LỚP CÙNG KHỐI ĐỂ CHUYỂN ---
                List<tblClass> transferableClasses = new ArrayList<>();
                if (classID > 0) {
                    tblClass currentClass = null;
                    // 1. Tìm thông tin lớp hiện tại từ danh sách đầy đủ để vẫn xử lý được lớp đang
                    // ẩn
                    for (tblClass c : allClasses) {
                        if (c.getClassID() == classID) {
                            currentClass = c;
                            break;
                        }
                    }

                    // 2. Lọc ra các lớp đang hoạt động có cùng GradeID và khác lớp hiện tại
                    if (currentClass != null) {
                        for (tblClass c : classes) {
                            // Dùng String.valueOf để so sánh an toàn bất kể getGradeID trả về int hay
                            // String
                            if (String.valueOf(c.getGradeID()).equals(String.valueOf(currentClass.getGradeID()))
                                    && c.getClassID() != classID) {
                                transferableClasses.add(c);
                            }
                        }
                    }
                }
                // Gửi danh sách lớp đã lọc qua view
                request.setAttribute("transferableClasses", transferableClasses);
                // ---------------------------------------------------

                request.setAttribute("studentsInClass", studentsInClass);
                request.setAttribute("classes", classes);
                request.setAttribute("classID", classID);
                request.setAttribute("yearSemesterID", yearSemesterID);

                request.getRequestDispatcher("/WEB-INF/views/admin/studentclass/index.jsp").forward(request, response);

            } else if (action.equals("/add")) {
                String classIDStr = request.getParameter("classID");
                String yearSemesterIDStr = request.getParameter("yearSemesterID");

                int classID = parseIntOrDefault(classIDStr, 0);
                int yearSemesterID = parseIntOrDefault(yearSemesterIDStr, 1);

                System.out.println("[StudentClassController.add-doGet] classID=" + classID
                        + ", yearSemesterID=" + yearSemesterID
                        + ", classIDStr='" + classIDStr + "'"
                        + ", yearSemesterIDStr='" + yearSemesterIDStr + "'");

                if (classID <= 0) {
                    setFlashMessage(request, null, "Vui lòng chọn một lớp hợp lệ, không được dùng '-- Tất cả lớp --'");
                    response.sendRedirect(
                            request.getContextPath() + "/admin/studentclass/list?yearSemesterID=" + yearSemesterID);
                    return;
                }

                // ✅ FIX: Lấy CHỈ những học sinh CHƯA có lớp trong năm học này
                List<Student> availableStudents = scDAO.getStudentsNotInClass(classID, yearSemesterID);

                request.setAttribute("classes", classDAO.getAllActive());
                request.setAttribute("students", availableStudents);
                request.setAttribute("cohorts", cohortDAO.getAll());
                request.setAttribute("classID", classID);
                request.setAttribute("selectedClassID", classID);
                request.setAttribute("yearSemesterID", yearSemesterID);

                request.getRequestDispatcher("/WEB-INF/views/admin/studentclass/add.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();

        try {
            if (action != null && action.equals("/transfer")) {
                // --- LOGIC XỬ LÝ CHUYỂN LỚP ---
                String studentId = request.getParameter("studentId");
                String fromClassIdStr = request.getParameter("fromClassId");
                String toClassIdStr = request.getParameter("toClassId");
                String yearSemesterIdStr = request.getParameter("yearSemesterID");

                if (studentId != null && !studentId.isEmpty()
                        && fromClassIdStr != null && !fromClassIdStr.isEmpty()
                        && toClassIdStr != null && !toClassIdStr.isEmpty()
                        && yearSemesterIdStr != null && !yearSemesterIdStr.isEmpty()) {
                    int fromClassId = Integer.parseInt(fromClassIdStr);
                    int toClassId = Integer.parseInt(toClassIdStr);
                    int yearSemesterId = Integer.parseInt(yearSemesterIdStr);

                    // Gọi DAO thực hiện chuyển trong Database
                    boolean success = scDAO.transferStudent(studentId, fromClassId, toClassId, yearSemesterId);

                    if (success) {
                        // Cập nhật lại sĩ số 2 lớp
                        classDAO.decrementCurrentStudents(fromClassId);
                        classDAO.incrementCurrentStudents(toClassId);

                        setFlashMessage(request, "Chuyển lớp thành công", null);
                        response.sendRedirect(request.getContextPath() + "/admin/studentclass/list?classID="
                                + fromClassId + "&yearSemesterID=" + yearSemesterId);
                    } else {
                        setFlashMessage(request, null, "Chuyển lớp thất bại");
                        response.sendRedirect(request.getContextPath() + "/admin/studentclass/list?classID="
                                + fromClassId + "&yearSemesterID=" + yearSemesterId);
                    }
                } else {
                    setFlashMessage(request, null, "Chuyển lớp thất bại: thông tin không hợp lệ");
                    response.sendRedirect(request.getContextPath()
                            + "/admin/studentclass/list");
                }
                return;
            }

            // --- LOGIC XỬ LÝ THÊM MỚI HỌC SINH VÀO LỚP ---
            String[] studentIDs = request.getParameterValues("studentID");
            String classIDStr = request.getParameter("classID");
            String yearSemesterIDStr = request.getParameter("yearSemesterID");

            System.out.println("[StudentClassController.add] Enter doPost /add");
            System.out.println("[StudentClassController.add] studentIDs="
                    + (studentIDs == null ? null : java.util.Arrays.toString(studentIDs))
                    + ", classID=" + classIDStr
                    + ", yearSemesterID=" + yearSemesterIDStr);

            if (studentIDs == null || studentIDs.length == 0 ||
                    classIDStr == null || classIDStr.isEmpty() ||
                    yearSemesterIDStr == null || yearSemesterIDStr.isEmpty()) {
                setFlashMessage(request, null, "Thông tin không hợp lệ");
                response.sendRedirect(request.getContextPath() + "/admin/studentclass/add?classID=" + classIDStr
                        + "&yearSemesterID=" + yearSemesterIDStr);
                return;
            }

            int classID = Integer.parseInt(classIDStr);
            int yearSemesterID = Integer.parseInt(yearSemesterIDStr);

            if (classID <= 0) {
                setFlashMessage(request, null, "Vui lòng chọn lớp hợp lệ, không được dùng '-- Tất cả lớp --'");
                response.sendRedirect(
                        request.getContextPath() + "/admin/studentclass/list?yearSemesterID=" + yearSemesterID);
                return;
            }

            System.out.println("[StudentClassController.add] Parsed values: classID=" + classID
                    + ", yearSemesterID=" + yearSemesterID);

            int addedCount = 0;
            int skippedCount = 0;
            int failedCount = 0;

            for (String studentID : studentIDs) {
                if (studentID == null || studentID.trim().isEmpty()) {
                    continue;
                }

                String trimmedStudentID = studentID.trim();
                System.out.println("[StudentClassController.add] Checking studentID=" + trimmedStudentID);

                if (scDAO.isStudentInAnyClass(trimmedStudentID, yearSemesterID)) {
                    skippedCount++;
                    continue;
                }

                StudentClass sc = new StudentClass();
                sc.setStudentID(trimmedStudentID);
                sc.setClassID(classID);
                sc.setCohortID(0);
                sc.setActive(true);
                sc.setYearSemesterID(yearSemesterID);

                System.out.println("[StudentClassController.add] Before DAO insert: studentID=" + sc.getStudentID()
                        + ", classID=" + sc.getClassID()
                        + ", cohortID=" + sc.getCohortID()
                        + ", active=" + sc.isActive()
                        + ", yearSemesterID=" + sc.getYearSemesterID());

                try {
                    boolean isAdded = scDAO.add(sc);
                    System.out.println(
                            "[StudentClassController.add] DAO insert result for " + trimmedStudentID + " = " + isAdded);
                    if (isAdded) {
                        addedCount++;
                        classDAO.incrementCurrentStudents(classID);
                    } else {
                        failedCount++;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    failedCount++;
                }
            }

            if (addedCount > 0 && failedCount == 0 && skippedCount == 0) {
                setFlashMessage(request, "Thêm " + addedCount + " học sinh thành công", null);
                response.sendRedirect(request.getContextPath() + "/admin/studentclass/list?classID=" + classID
                        + "&yearSemesterID=" + yearSemesterID);
            } else if (addedCount > 0) {
                setFlashMessage(request,
                        "Đã thêm " + addedCount + " học sinh, bỏ qua " + skippedCount + " và lỗi " + failedCount,
                        null);
                response.sendRedirect(request.getContextPath() + "/admin/studentclass/list?classID=" + classID
                        + "&yearSemesterID=" + yearSemesterID);
            } else {
                setFlashMessage(request, null, "Không có học sinh nào được thêm");
                response.sendRedirect(request.getContextPath() + "/admin/studentclass/add?classID=" + classID
                        + "&yearSemesterID=" + yearSemesterID);
            }

        } catch (NumberFormatException | SQLException e) {
            throw new ServletException(e);
        }
    }
}
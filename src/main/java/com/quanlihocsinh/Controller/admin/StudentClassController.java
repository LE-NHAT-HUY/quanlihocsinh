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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();
        if (action == null || action.equals("/"))
            action = "/list";

        try {
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
                List<tblClass> classes = classDAO.getAll();

                // --- MỚI: LỌC DANH SÁCH LỚP CÙNG KHỐI ĐỂ CHUYỂN ---
                List<tblClass> transferableClasses = new ArrayList<>();
                if (classID > 0) {
                    tblClass currentClass = null;
                    // 1. Tìm thông tin lớp hiện tại từ danh sách
                    for (tblClass c : classes) {
                        if (c.getClassID() == classID) {
                            currentClass = c;
                            break;
                        }
                    }

                    // 2. Lọc ra các lớp có cùng GradeID và khác lớp hiện tại
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
                int classID = (classIDStr != null) ? Integer.parseInt(classIDStr) : 0;

                request.setAttribute("classes", classDAO.getAll());
                request.setAttribute("students", studentDAO.getAll());
                request.setAttribute("cohorts", cohortDAO.getAll());
                request.setAttribute("selectedClassID", classID);

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

                        response.sendRedirect(request.getContextPath() + "/admin/studentclass/list?classID="
                                + fromClassId + "&yearSemesterID=" + yearSemesterId + "&msg=transfer_success");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/admin/studentclass/list?classID="
                                + fromClassId + "&yearSemesterID=" + yearSemesterId + "&error=transfer_failed");
                    }
                } else {
                    response.sendRedirect(request.getContextPath()
                            + "/admin/studentclass/list?error=transfer_failed_invalid_params");
                }
                return;
            }

            // --- LOGIC XỬ LÝ THÊM MỚI HỌC SINH VÀO LỚP ---
            String studentID = request.getParameter("studentID");
            String classIDStr = request.getParameter("classID");
            String yearSemesterIDStr = request.getParameter("yearSemesterID");
            String cohortIDStr = request.getParameter("cohortID");

            if (studentID == null || studentID.isEmpty() ||
                    classIDStr == null || classIDStr.isEmpty() ||
                    yearSemesterIDStr == null || yearSemesterIDStr.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/admin/studentclass/list");
                return;
            }

            int classID = Integer.parseInt(classIDStr);
            int yearSemesterID = Integer.parseInt(yearSemesterIDStr);
            int cohortID = (cohortIDStr != null && !cohortIDStr.isEmpty()) ? Integer.parseInt(cohortIDStr) : 0;

            StudentClass sc = new StudentClass();
            sc.setStudentID(studentID);
            sc.setClassID(classID);
            sc.setCohortID(cohortID);
            sc.setActive(true);
            sc.setYearSemesterID(yearSemesterID);

            boolean isAdded = scDAO.add(sc);
            if (isAdded) {
                classDAO.incrementCurrentStudents(classID);
            }

            response.sendRedirect(request.getContextPath() + "/admin/studentclass/list?classID=" + classID
                    + "&yearSemesterID=" + yearSemesterID);

        } catch (NumberFormatException | SQLException e) {
            throw new ServletException(e);
        }
    }
}
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
import java.sql.SQLException;
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

        int classID = 0;
        int yearSemesterID = 1;

        try {
            if (request.getParameter("classID") != null && !request.getParameter("classID").isEmpty())
                classID = Integer.parseInt(request.getParameter("classID"));
            if (request.getParameter("yearSemesterID") != null && !request.getParameter("yearSemesterID").isEmpty())
                yearSemesterID = Integer.parseInt(request.getParameter("yearSemesterID"));
        } catch (NumberFormatException e) {
            // ignore
        }

        try {
            switch (action) {
                case "/add":
                    // <CHANGE> Lấy danh sách học sinh chưa có trong lớp
                    List<Student> students = scDAO.getStudentsNotInClass(classID, yearSemesterID);
                    List<tblClass> classes = classDAO.getAll();
                    List<Cohort> cohorts = cohortDAO.getAll();

                    // Lấy thông tin lớp hiện tại
                    tblClass currentClass = classDAO.getById(classID);

                    request.setAttribute("students", students);
                    request.setAttribute("classes", classes);
                    request.setAttribute("cohorts", cohorts);
                    request.setAttribute("currentClass", currentClass);
                    request.setAttribute("classID", classID);
                    request.setAttribute("yearSemesterID", yearSemesterID);
                    request.getRequestDispatcher("/WEB-INF/views/admin/studentclass/add.jsp").forward(request,
                            response);
                    break;

                case "/list":
                    List<StudentClass> studentsInClass = scDAO.getByClassAndYear(classID, yearSemesterID);
                    List<tblClass> allClasses = classDAO.getAll();

                    // Lấy thông tin chi tiết học sinh
                    for (StudentClass sc : studentsInClass) {
                        Student s = studentDAO.getByStudentId(sc.getStudentID());
                        sc.setStudent(s);
                    }

                    request.setAttribute("studentsInClass", studentsInClass);
                    request.setAttribute("classes", allClasses);
                    request.setAttribute("classID", classID);
                    request.setAttribute("yearSemesterID", yearSemesterID);
                    request.getRequestDispatcher("/WEB-INF/views/admin/studentclass/list.jsp").forward(request,
                            response);
                    break;

                case "/delete":
                    if (request.getParameter("studentClassID") != null
                            && !request.getParameter("studentClassID").isEmpty()) {
                        int studentClassID = Integer.parseInt(request.getParameter("studentClassID"));
                        scDAO.delete(studentClassID);

                        // <CHANGE> Giảm số lượng học sinh hiện tại của lớp
                        if (classID > 0) {
                            classDAO.decrementCurrentStudents(classID);
                        }
                    }
                    response.sendRedirect(request.getContextPath() + "/admin/student-class/list?classID=" + classID
                            + "&yearSemesterID=" + yearSemesterID);
                    break;

                default:
                    response.sendRedirect(request.getContextPath() + "/admin/class?action=list");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
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

            scDAO.add(sc);

            // <CHANGE> Tăng số lượng học sinh hiện tại của lớp
            classDAO.incrementCurrentStudents(classID);

            response.sendRedirect(request.getContextPath() + "/admin/studentclass/list?classID=" + classID
                    + "&yearSemesterID=" + yearSemesterID);

        } catch (NumberFormatException | SQLException e) {
            throw new ServletException(e);
        }
    }
}
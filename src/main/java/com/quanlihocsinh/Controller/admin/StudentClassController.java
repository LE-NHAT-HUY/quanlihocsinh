package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.StudentClassDAO;
import com.quanlihocsinh.dao.TblClassDAO;
import com.quanlihocsinh.model.Student;
import com.quanlihocsinh.model.StudentClass;
import com.quanlihocsinh.model.tblClass;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/student-class/*")
public class StudentClassController extends HttpServlet {

    private StudentClassDAO scDAO = new StudentClassDAO();
    private TblClassDAO classDAO = new TblClassDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();
        if (action == null || action.equals("/"))
            action = "/list";

        int classID = 1;
        int yearSemesterID = 1;

        try {
            if (request.getParameter("classID") != null && !request.getParameter("classID").isEmpty())
                classID = Integer.parseInt(request.getParameter("classID"));
            if (request.getParameter("yearSemesterID") != null && !request.getParameter("yearSemesterID").isEmpty())
                yearSemesterID = Integer.parseInt(request.getParameter("yearSemesterID"));
        } catch (NumberFormatException e) {

        }

        try {
            switch (action) {
                case "/add":
                    List<Student> students = scDAO.getStudentsNotInClass(classID, yearSemesterID);
                    List<tblClass> classes = classDAO.getAll();
                    request.setAttribute("students", students);
                    request.setAttribute("classes", classes);
                    request.setAttribute("classID", classID);
                    request.setAttribute("yearSemesterID", yearSemesterID);
                    request.getRequestDispatcher("/WEB-INF/views/admin/studentclass/add.jsp").forward(request,
                            response);
                    break;

                case "/list":
                    List<StudentClass> studentsInClass = scDAO.getByClassAndYear(classID, yearSemesterID);
                    request.setAttribute("studentsInClass", studentsInClass);
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
                    }
                    response.sendRedirect(request.getContextPath() + "/admin/student-class/list?classID=" + classID
                            + "&yearSemesterID=" + yearSemesterID);
                    break;

                default:
                    response.sendRedirect(request.getContextPath() + "/admin/class/list");
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
                response.sendRedirect(request.getContextPath() + "/admin/student-class/list");
                return;
            }

            int classID = Integer.parseInt(classIDStr);
            int yearSemesterID = Integer.parseInt(yearSemesterIDStr);
            int cohortID = (cohortIDStr != null && !cohortIDStr.isEmpty()) ? Integer.parseInt(cohortIDStr) : 0;
            boolean isActive = request.getParameter("isActive") != null;

            StudentClass sc = new StudentClass();
            sc.setStudentID(studentID);
            sc.setClassID(classID);
            sc.setCohortID(cohortID);
            sc.setActive(isActive);
            sc.setYearSemesterID(yearSemesterID);

            scDAO.add(sc);

            response.sendRedirect(request.getContextPath() + "/admin/student-class/list?classID=" + classID
                    + "&yearSemesterID=" + yearSemesterID);

        } catch (NumberFormatException | SQLException e) {
            throw new ServletException(e);
        }
    }
}

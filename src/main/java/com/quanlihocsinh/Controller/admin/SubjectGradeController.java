package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.GradeDAO;
import com.quanlihocsinh.dao.SubjectDAO;
import com.quanlihocsinh.dao.SubjectGradeDAO;
import com.quanlihocsinh.model.Grade;
import com.quanlihocsinh.model.Subject;
import com.quanlihocsinh.model.SubjectGrade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/subject-grade")
public class SubjectGradeController extends HttpServlet {

    private final GradeDAO gradeDAO = new GradeDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final SubjectGradeDAO subjectGradeDAO = new SubjectGradeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        List<Grade> grades = gradeDAO.getAll();
        int gradeID = parseIntOrZero(request.getParameter("gradeID"));
        if (gradeID <= 0 || findGradeById(grades, gradeID) == null) {
            Grade defaultGrade = findGradeByName(grades, "6");
            if (defaultGrade == null && !grades.isEmpty()) {
                defaultGrade = grades.get(0);
            }
            if (defaultGrade != null) {
                gradeID = defaultGrade.getGradeID();
            }
        }

        Grade currentGrade = findGradeById(grades, gradeID);

        List<SubjectGrade> assignedSubjects = subjectGradeDAO.getSubjectsByGrade(gradeID);
        List<Subject> unassignedSubjects = subjectGradeDAO.getSubjectsNotInGrade(gradeID);
        boolean fallbackAllSubjects = false;

        if (unassignedSubjects == null || unassignedSubjects.isEmpty()) {
            unassignedSubjects = subjectDAO.findAllActive();
            fallbackAllSubjects = true;
        }

        request.setAttribute("grades", grades);
        request.setAttribute("currentGrade", gradeID);
        request.setAttribute("currentGradeName",
                currentGrade != null ? currentGrade.getGradeName() : String.valueOf(gradeID));
        request.setAttribute("gradeID", gradeID);
        request.setAttribute("assignedSubjects", assignedSubjects);
        request.setAttribute("unassignedSubjects", unassignedSubjects);
        request.setAttribute("fallbackAllSubjects", fallbackAllSubjects);

        HttpSession session = request.getSession(false);
        if (session != null) {
            Object success = session.getAttribute("flashSuccess");
            Object error = session.getAttribute("flashError");
            if (success != null) {
                request.setAttribute("flashSuccess", success);
                session.removeAttribute("flashSuccess");
            }
            if (error != null) {
                request.setAttribute("flashError", error);
                session.removeAttribute("flashError");
            }
        }

        request.getRequestDispatcher("/WEB-INF/views/admin/subject/subject_grade.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        int gradeID = parseIntOrZero(request.getParameter("gradeID"));
        HttpSession session = request.getSession();

        try {
            if ("save".equalsIgnoreCase(action)) {
                int subjectID = parseIntOrZero(request.getParameter("subjectID"));
                int periods = parseIntOrZero(request.getParameter("periods"));

                if (subjectID <= 0 || gradeID <= 0 || periods <= 0) {
                    session.setAttribute("flashError", "Vui lòng chọn môn học, khối và số tiết hợp lệ.");
                } else if (subjectGradeDAO.assignSubjectToGrade(subjectID, gradeID, periods)) {
                    session.setAttribute("flashSuccess", "Đã lưu phân môn theo khối.");
                } else {
                    String detail = subjectGradeDAO.getLastErrorMessage();
                    session.setAttribute("flashError",
                            detail == null || detail.isBlank()
                                    ? "Không thể lưu phân môn theo khối."
                                    : "Không thể lưu phân môn theo khối: " + detail);
                }
            } else if ("delete".equalsIgnoreCase(action)) {
                int subjectID = parseIntOrZero(request.getParameter("subjectID"));
                if (subjectID <= 0 || gradeID <= 0) {
                    session.setAttribute("flashError", "Thiếu thông tin để xóa.");
                } else if (subjectGradeDAO.removeSubjectFromGrade(subjectID, gradeID)) {
                    session.setAttribute("flashSuccess", "Đã xóa môn khỏi khối.");
                } else {
                    String detail = subjectGradeDAO.getLastErrorMessage();
                    session.setAttribute("flashError",
                            detail == null || detail.isBlank()
                                    ? "Không thể xóa môn khỏi khối."
                                    : "Không thể xóa môn khỏi khối: " + detail);
                }
            }
        } catch (Exception e) {
            session.setAttribute("flashError", "Lỗi xử lý: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/subject-grade?gradeID=" + gradeID);
    }

    private int parseIntOrZero(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    private Grade findGradeById(List<Grade> grades, int gradeID) {
        if (grades == null) {
            return null;
        }
        for (Grade grade : grades) {
            if (grade.getGradeID() == gradeID) {
                return grade;
            }
        }
        return null;
    }

    private Grade findGradeByName(List<Grade> grades, String gradeName) {
        if (grades == null) {
            return null;
        }
        for (Grade grade : grades) {
            if (gradeName.equals(String.valueOf(grade.getGradeName()))) {
                return grade;
            }
        }
        return null;
    }
}

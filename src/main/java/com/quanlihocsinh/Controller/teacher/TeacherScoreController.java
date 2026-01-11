package com.quanlihocsinh.Controller.teacher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quanlihocsinh.dao.*;
import com.quanlihocsinh.model.*;
import com.quanlihocsinh.service.ScoreService;
import com.quanlihocsinh.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/teacher/scores/*")
public class TeacherScoreController extends HttpServlet {

    private StudentClassDAO studentClassDAO;
    private TblClassDAO tblClassDAO;
    private SubjectDAO subjectDAO;
    private YearSemesterDAO yearSemesterDAO;
    private ScoreDAO scoreDAO;
    private ScoreService scoreService;
    private TeacherSubjectDAO teacherSubjectDAO;
    private MenuDAO menuDAO; // 1. Thêm MenuDAO
    private ObjectMapper mapper;

    @Override
    public void init() throws ServletException {
        studentClassDAO = new StudentClassDAO();
        tblClassDAO = new TblClassDAO();
        subjectDAO = new SubjectDAO();
        yearSemesterDAO = new YearSemesterDAO();
        scoreDAO = new ScoreDAO();
        scoreService = new ScoreService();
        teacherSubjectDAO = new TeacherSubjectDAO();
        menuDAO = new MenuDAO(); // 2. Khởi tạo MenuDAO
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || user.getRoleId() != 2) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 3. LẤY MENU CHO SIDEBAR (Chạy mỗi khi vào trang)
        List<menu> menuList = menuDAO.getAllMenus();
        req.setAttribute("menuList", menuList);

        int teacherID = user.getEntityId(); // Lấy ID Teacher (12)
        String path = req.getPathInfo();

        try {
            // === TRANG DANH SÁCH / XEM ĐIỂM ===
            if (path == null || "/".equals(path) || "/list".equals(path)) {

                // ... (Logic lấy dữ liệu giữ nguyên như cũ) ...
                List<tblClass> classes = tblClassDAO.getAll();
                req.setAttribute("classes", classes != null ? classes : new ArrayList<>());

                List<YearSemester> yearSemesters = yearSemesterDAO.getAllActive();
                req.setAttribute("yearSemesters", yearSemesters != null ? yearSemesters : new ArrayList<>());

                List<Subject> subjects = teacherSubjectDAO.findSubjectsByTeacher(teacherID);
                req.setAttribute("subjects", subjects);

                int classID = parseInt(req.getParameter("classID"));
                int subjectID = parseInt(req.getParameter("subjectID"));
                int yearSemesterID = parseInt(req.getParameter("yearSemesterID"));

                req.setAttribute("classID", classID);
                req.setAttribute("subjectID", subjectID);
                req.setAttribute("yearSemesterID", yearSemesterID);

                if (classID > 0 && subjectID > 0 && yearSemesterID > 0) {
                    boolean isAllowed = false;
                    for (Subject s : subjects) {
                        // So sánh trực tiếp 2 số int với nhau
                        if (s.getSubjectID() == subjectID) {
                            isAllowed = true;
                            break;
                        }
                    }

                    if (isAllowed) {
                        List<StudentClass> studentsInClass = studentClassDAO.getStudentsByClass(classID);
                        List<Score> scores = scoreDAO.getByClassSubjectYear(classID, subjectID, yearSemesterID);
                        Map<String, Score> scoreMap = new HashMap<>();
                        if (scores != null) {
                            for (Score s : scores)
                                scoreMap.put(s.getStudentID(), s);
                        }
                        req.setAttribute("studentsInClass", studentsInClass);
                        req.setAttribute("scoreMap", scoreMap);
                        tblClass cls = tblClassDAO.getById(classID);
                        req.setAttribute("selectedClass", cls);
                    } else {
                        req.setAttribute("errorMessage", "Bạn không được phân công dạy môn này!");
                    }
                }

                // 4. GÁN LAYOUT CHO TRANG LIST
                req.setAttribute("pageTitle", "Quản lý điểm số");
                req.setAttribute("contentPage", "/WEB-INF/views/teacher/score/list.jsp");
                req.getRequestDispatcher("/WEB-INF/views/shared/Layoutteacher.jsp").forward(req, resp);
                return;
            }

            // === TRANG NHẬP ĐIỂM ===
            if ("/add".equals(path)) {
                // ... (Logic lấy dữ liệu giữ nguyên như cũ) ...
                int classID = parseInt(req.getParameter("classID"));
                int subjectID = parseInt(req.getParameter("subjectID"));
                int yearSemesterID = parseInt(req.getParameter("yearSemesterID"));

                boolean isAllowed = false;
                Subject currentSubject = null;
                List<Subject> subjects = teacherSubjectDAO.findSubjectsByTeacher(teacherID);

                for (Subject s : subjects) {
                    // So sánh trực tiếp 2 số int với nhau
                    if (s.getSubjectID() == subjectID) {
                        isAllowed = true;
                        break;
                    }
                }

                if (!isAllowed) {
                    // Nếu lỗi quyền, quay về trang list (vẫn dùng layout)
                    req.setAttribute("errorMessage", "Không có quyền truy cập!");
                    req.setAttribute("contentPage", "/WEB-INF/views/teacher/score/list.jsp");
                    req.getRequestDispatcher("/WEB-INF/views/shared/Layoutteacher.jsp").forward(req, resp);
                    return;
                }

                List<StudentClass> studentsInClass = studentClassDAO.getStudentsByClass(classID);
                List<Score> scores = scoreDAO.getByClassSubjectYear(classID, subjectID, yearSemesterID);
                Map<String, Score> scoreMap = new HashMap<>();
                if (scores != null) {
                    for (Score s : scores)
                        scoreMap.put(s.getStudentID(), s);
                }

                tblClass classObj = tblClassDAO.getById(classID);

                req.setAttribute("studentsInClass", studentsInClass);
                req.setAttribute("scoreMap", scoreMap);
                req.setAttribute("classObj", classObj);
                req.setAttribute("subject", currentSubject);
                req.setAttribute("classID", classID);
                req.setAttribute("subjectID", subjectID);
                req.setAttribute("yearSemesterID", yearSemesterID);

                // 5. GÁN LAYOUT CHO TRANG ADD
                req.setAttribute("pageTitle", "Nhập điểm chi tiết");
                req.setAttribute("contentPage", "/WEB-INF/views/teacher/score/add.jsp");
                req.getRequestDispatcher("/WEB-INF/views/shared/Layout.jsp").forward(req, resp);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Logic POST (Lưu điểm) giữ nguyên
        // Lưu ý: Sau khi lưu xong ta dùng sendRedirect nên không cần gán Layout ở đây.
        // Trừ khi có lỗi validate và muốn forward lại form thì mới cần gán Layout.

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null || user.getRoleId() != 2) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int teacherID = user.getEntityId();
        String path = req.getPathInfo();

        try {
            if ("/saveBulk".equals(path)) {
                // ... (Logic lưu điểm giữ nguyên 100% từ code cũ) ...
                int classID = parseInt(req.getParameter("classID"));
                int subjectID = parseInt(req.getParameter("subjectID"));
                int yearSemesterID = parseInt(req.getParameter("yearSemesterID"));

                boolean isAllowed = false;
                List<Subject> subjects = teacherSubjectDAO.findSubjectsByTeacher(teacherID);
                for (Subject s : subjects) {
                    if (s.getSubjectID() == subjectID) {
                        isAllowed = true;
                        break;
                    }
                }
                if (!isAllowed) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }

                List<StudentClass> students = studentClassDAO.getStudentsByClass(classID);
                Connection conn = DBUtil.getConnection();
                conn.setAutoCommit(false);
                try {
                    for (StudentClass sc : students) {
                        int key = sc.getStudentClassID();
                        String studentID = sc.getStudentID();

                        // Parse params...
                        Double oral1 = parseDouble(req.getParameter("oral1_" + key));
                        Double oral2 = parseDouble(req.getParameter("oral2_" + key));
                        Double s15_1 = parseDouble(req.getParameter("s15_1_" + key));
                        Double s15_2 = parseDouble(req.getParameter("s15_2_" + key));
                        Double mid = parseDouble(req.getParameter("mid_" + key));
                        Double fin = parseDouble(req.getParameter("fin_" + key));
                        String notes = req.getParameter("notes_" + key);

                        if (oral1 == null && oral2 == null && s15_1 == null && s15_2 == null &&
                                mid == null && fin == null && (notes == null || notes.trim().isEmpty()))
                            continue;

                        Score existing = scoreDAO.findByStudentSubjectYear(studentID, subjectID, yearSemesterID);
                        Score s = new Score();
                        if (existing != null)
                            s = existing;
                        else {
                            s.setStudentID(studentID);
                            s.setSubjectID(subjectID);
                            s.setYearSemesterID(yearSemesterID);
                        }

                        s.setOralScore1(oral1);
                        s.setOralScore2(oral2);
                        s.setScore15Minute1(s15_1);
                        s.setScore15Minute2(s15_2);
                        s.setMidtermScore(mid);
                        s.setFinalScore(fin);
                        s.setNotes(notes);
                        s.setActive(true);

                        scoreService.calculateAveragesAndRating(s);

                        if (existing != null)
                            scoreDAO.update(s);
                        else
                            scoreDAO.insert(s);
                    }
                    conn.commit();
                } catch (Exception e) {
                    conn.rollback();
                    throw e;
                } finally {
                    conn.close();
                }

                // Redirect về trang list kèm thông báo thành công
                resp.sendRedirect(req.getContextPath() + "/teacher/scores?classID=" + classID +
                        "&subjectID=" + subjectID + "&yearSemesterID=" + yearSemesterID + "&msg=saved");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private int parseInt(String v) {
        try {
            return Integer.parseInt(v);
        } catch (Exception e) {
            return 0;
        }
    }

    private Double parseDouble(String v) {
        if (v == null || v.trim().isEmpty())
            return null;
        try {
            return Double.parseDouble(v);
        } catch (Exception e) {
            return null;
        }
    }
}
package com.quanlihocsinh.Controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quanlihocsinh.dao.ScoreDAO;
import com.quanlihocsinh.dao.StudentClassDAO;
import com.quanlihocsinh.dao.StudentDAO;
import com.quanlihocsinh.dao.SubjectDAO;
import com.quanlihocsinh.dao.TblClassDAO;
import com.quanlihocsinh.dao.YearSemesterDAO;
import com.quanlihocsinh.model.Score;
import com.quanlihocsinh.model.Student;
import com.quanlihocsinh.model.StudentClass;
import com.quanlihocsinh.model.Subject;
import com.quanlihocsinh.model.YearSemester;
import com.quanlihocsinh.model.tblClass;
import com.quanlihocsinh.service.ScoreService;
import com.quanlihocsinh.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/admin/scores/*")
public class ScoreController extends HttpServlet {

    private StudentClassDAO studentClassDAO;
    private TblClassDAO tblClassDAO;
    private SubjectDAO subjectDAO;
    private YearSemesterDAO yearSemesterDAO;
    private ScoreDAO scoreDAO;
    private ScoreService scoreService;
    private ObjectMapper mapper;

    @Override
    public void init() throws ServletException {
        studentClassDAO = new StudentClassDAO();
        tblClassDAO = new TblClassDAO();
        subjectDAO = new SubjectDAO();
        yearSemesterDAO = new YearSemesterDAO();
        scoreDAO = new ScoreDAO();
        scoreService = new ScoreService();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo();

        try {
            // ===== LIST =====
            // ===== LIST =====
            if (path == null || "/".equals(path) || "/list".equals(path)) {
                // Luôn lấy danh sách lớp, môn học và học kỳ
                List<tblClass> classes = tblClassDAO.getAll();
                req.setAttribute("classes", classes);

                List<Subject> subjects = subjectDAO.findAllActive();
                req.setAttribute("subjects", subjects);

                List<YearSemester> yearSemesters = yearSemesterDAO.getAllActive();
                req.setAttribute("yearSemesters", yearSemesters);

                // Lấy parameter, parse an toàn
                int classID = 0;
                int subjectID = 0;
                int yearSemesterID = 0;

                try {
                    String classParam = req.getParameter("classID");
                    if (classParam != null && !classParam.isEmpty())
                        classID = Integer.parseInt(classParam);

                    String subjectParam = req.getParameter("subjectID");
                    if (subjectParam != null && !subjectParam.isEmpty())
                        subjectID = Integer.parseInt(subjectParam);

                    String yearParam = req.getParameter("yearSemesterID");
                    if (yearParam != null && !yearParam.isEmpty())
                        yearSemesterID = Integer.parseInt(yearParam);
                } catch (NumberFormatException ignored) {
                }

                req.setAttribute("classID", classID);
                req.setAttribute("subjectID", subjectID);
                req.setAttribute("yearSemesterID", yearSemesterID);

                // Lấy danh sách học sinh và điểm nếu classID > 0 và subjectID > 0 và
                // yearSemesterID > 0
                if (classID > 0 && subjectID > 0 && yearSemesterID > 0) {
                    // Lấy danh sách học sinh trong lớp
                    List<StudentClass> studentsInClass = studentClassDAO.getStudentsByClass(classID);

                    // Lấy điểm đã có cho lớp + môn + học kỳ
                    List<Score> scores = scoreDAO.getByClassSubjectYear(classID, subjectID, yearSemesterID);

                    // Tạo map để JSP tra cứu điểm theo studentID
                    Map<String, Score> scoreMap = new HashMap<>();
                    for (Score s : scores) {
                        scoreMap.put(s.getStudentID(), s);
                    }

                    // Debug
                    System.out.println("===== DEBUG: LIST SCORES =====");
                    System.out.println(
                            "Class: " + classID + ", Subject: " + subjectID + ", YearSemester: " + yearSemesterID);
                    System.out.println("Number of students: " + studentsInClass.size());
                    System.out.println("Number of scores: " + scores.size());

                    req.setAttribute("studentsInClass", studentsInClass);
                    req.setAttribute("scoreMap", scoreMap);

                    // Lấy thông tin lớp, môn, học kỳ để hiển thị
                    tblClass cls = tblClassDAO.getById(classID);
                    Subject sub = subjectDAO.getSubjectById(subjectID);
                    YearSemester ys = yearSemesterDAO.getById(yearSemesterID);

                    if (cls != null)
                        req.setAttribute("selectedClass", cls);
                    if (sub != null)
                        req.setAttribute("selectedSubject", sub);
                    if (ys != null)
                        req.setAttribute("selectedYearSemester", ys);
                }

                req.getRequestDispatcher("/WEB-INF/views/admin/score/list.jsp").forward(req, resp);
                return;
            }
            // ===== ADD =====
            if ("/add".equals(path)) {
                int classID = parseInt(req.getParameter("classID"));
                int subjectID = parseInt(req.getParameter("subjectID"));
                int yearSemesterID = parseInt(req.getParameter("yearSemesterID"));

                if (classID <= 0 || subjectID <= 0 || yearSemesterID <= 0) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin lớp/môn/học kỳ");
                    return;
                }

                // Lấy lớp và môn
                tblClass cls = tblClassDAO.getById(classID);
                Subject sub = subjectDAO.getSubjectById(subjectID);

                // THAY ĐỔI QUAN TRỌNG: Lấy học sinh trong lớp (không phân biệt học kỳ)
                List<StudentClass> studentsInClass = studentClassDAO.getStudentsByClass(classID);

                // Debug
                System.out.println("===== DEBUG: ADD SCORE =====");
                System.out.println("Lớp: " + classID + ", Môn: " + subjectID + ", Học kỳ: " + yearSemesterID);
                System.out.println("Số học sinh trong lớp: " + studentsInClass.size());

                // Lấy điểm đã có cho lớp + môn + học kỳ CỤ THỂ
                List<Score> scores = scoreDAO.getByClassSubjectYear(classID, subjectID, yearSemesterID);
                System.out.println("Số bản ghi điểm đã có: " + scores.size());

                // Tạo map để JSP tra cứu điểm theo studentID
                Map<String, Score> scoreMap = new HashMap<>();
                for (Score s : scores) {
                    scoreMap.put(s.getStudentID(), s);
                    System.out.println("Điểm của học sinh " + s.getStudentID() + ": " + s.getAverageScore());
                }

                // Set attribute cho JSP
                req.setAttribute("classObj", cls);
                req.setAttribute("subject", sub);
                req.setAttribute("studentsInClass", studentsInClass);
                req.setAttribute("scoreMap", scoreMap);
                req.setAttribute("classID", classID);
                req.setAttribute("subjectID", subjectID);
                req.setAttribute("yearSemesterID", yearSemesterID);

                req.getRequestDispatcher("/WEB-INF/views/admin/score/add.jsp").forward(req, resp);
                return;
            }

            resp.sendError(HttpServletResponse.SC_NOT_FOUND);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // POST: saveBulk (insert/update nhiều), delete, ajaxCalc
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        try {
            // Trong doPost, phần saveBulk
            if ("/saveBulk".equals(path)) {
                int classID = parseInt(req.getParameter("classID"));
                int subjectID = parseInt(req.getParameter("subjectID"));
                int yearSemesterID = parseInt(req.getParameter("yearSemesterID"));

                if (classID <= 0 || subjectID <= 0 || yearSemesterID <= 0) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin lớp/học kỳ/môn học");
                    return;
                }

                // Lấy danh sách học sinh trong lớp (không phân biệt học kỳ)
                List<StudentClass> students = studentClassDAO.getStudentsByClass(classID);

                Connection conn = DBUtil.getConnection();
                conn.setAutoCommit(false);
                try {
                    for (StudentClass sc : students) {
                        int key = sc.getStudentClassID();
                        String studentID = sc.getStudentID();

                        Double oral1 = parseDouble(req.getParameter("oral1_" + key));
                        Double oral2 = parseDouble(req.getParameter("oral2_" + key));
                        Double s15_1 = parseDouble(req.getParameter("s15_1_" + key));
                        Double s15_2 = parseDouble(req.getParameter("s15_2_" + key));
                        Double mid = parseDouble(req.getParameter("mid_" + key));
                        Double fin = parseDouble(req.getParameter("fin_" + key));
                        String notes = req.getParameter("notes_" + key);

                        // Nếu tất cả rỗng và không có notes thì bỏ qua
                        if (oral1 == null && oral2 == null && s15_1 == null && s15_2 == null &&
                                mid == null && fin == null && (notes == null || notes.trim().isEmpty()))
                            continue;

                        // Kiểm tra tồn tại điểm cho student+subject+year
                        Score existing = scoreDAO.findByStudentSubjectYear(studentID, subjectID, yearSemesterID);
                        Score s = new Score();

                        if (existing != null) {
                            s = existing;
                        } else {
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

                        if (existing != null) {
                            scoreDAO.update(s);
                        } else {
                            scoreDAO.insert(s);
                        }
                    }
                    conn.commit();
                } catch (Exception e) {
                    conn.rollback();
                    throw e;
                } finally {
                    conn.close();
                }

                resp.sendRedirect(req.getContextPath() + "/admin/scores?classID=" + classID +
                        "&subjectID=" + subjectID + "&yearSemesterID=" + yearSemesterID);
                return;
            }

            if ("/delete".equals(path)) {
                int scoreID = parseInt(req.getParameter("scoreID"));
                int classID = parseInt(req.getParameter("classID"));
                int subjectID = parseInt(req.getParameter("subjectID"));
                int yearSemesterID = parseInt(req.getParameter("yearSemesterID"));
                if (scoreID > 0) {
                    scoreDAO.delete(scoreID);
                }
                resp.sendRedirect(req.getContextPath() + "/admin/scores?classID=" + classID + "&subjectID=" + subjectID
                        + "&yearSemesterID=" + yearSemesterID);
                return;
            }

            if ("/ajaxCalc".equals(path)) {
                Score s = mapper.readValue(req.getInputStream(), Score.class);
                scoreService.calculateAveragesAndRating(s);
                resp.setContentType("application/json;charset=UTF-8");
                mapper.writeValue(resp.getOutputStream(), s);
                return;
            }

            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // helper
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

    private void setDouble(PreparedStatement ps, int idx, Double v) throws SQLException {
        if (v == null)
            ps.setNull(idx, java.sql.Types.DOUBLE);
        else
            ps.setDouble(idx, v);
    }
}

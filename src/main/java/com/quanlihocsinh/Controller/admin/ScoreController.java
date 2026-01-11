package com.quanlihocsinh.Controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quanlihocsinh.dao.*; // Import rút gọn
import com.quanlihocsinh.model.*; // Import rút gọn
import com.quanlihocsinh.service.ScoreService;
import com.quanlihocsinh.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects; // Cần thêm cái này để so sánh

@WebServlet("/admin/scores/*")
public class ScoreController extends HttpServlet {

    private ScoreLogDAO scoreLogDAO;
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
        scoreLogDAO = new ScoreLogDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo();

        try {
            // === LIST VIEW ===
            if (path == null || "/".equals(path) || "/list".equals(path)) {
                List<tblClass> classes = tblClassDAO.getAll();
                req.setAttribute("classes", classes);

                List<Subject> subjects = subjectDAO.findAllActive();
                req.setAttribute("subjects", subjects);

                List<YearSemester> yearSemesters = yearSemesterDAO.getAllActive();
                req.setAttribute("yearSemesters", yearSemesters);

                int classID = parseInt(req.getParameter("classID"));
                int subjectID = parseInt(req.getParameter("subjectID"));
                int yearSemesterID = parseInt(req.getParameter("yearSemesterID"));

                req.setAttribute("classID", classID);
                req.setAttribute("subjectID", subjectID);
                req.setAttribute("yearSemesterID", yearSemesterID);

                if (classID > 0 && subjectID > 0 && yearSemesterID > 0) {
                    List<StudentClass> studentsInClass = studentClassDAO.getStudentsByClass(classID);
                    List<Score> scores = scoreDAO.getByClassSubjectYear(classID, subjectID, yearSemesterID);

                    Map<String, Score> scoreMap = new HashMap<>();
                    for (Score s : scores) {
                        scoreMap.put(s.getStudentID(), s);
                    }

                    req.setAttribute("studentsInClass", studentsInClass);
                    req.setAttribute("scoreMap", scoreMap);

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

            // === ADD VIEW ===
            if ("/add".equals(path)) {
                int classID = parseInt(req.getParameter("classID"));
                int subjectID = parseInt(req.getParameter("subjectID"));
                int yearSemesterID = parseInt(req.getParameter("yearSemesterID"));

                if (classID <= 0 || subjectID <= 0 || yearSemesterID <= 0) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin lớp/môn/học kỳ");
                    return;
                }

                tblClass cls = tblClassDAO.getById(classID);
                Subject sub = subjectDAO.getSubjectById(subjectID);
                List<StudentClass> studentsInClass = studentClassDAO.getStudentsByClass(classID);
                List<Score> scores = scoreDAO.getByClassSubjectYear(classID, subjectID, yearSemesterID);

                Map<String, Score> scoreMap = new HashMap<>();
                for (Score s : scores) {
                    scoreMap.put(s.getStudentID(), s);
                }

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

            // === HISTORY VIEW ===
            if ("/history".equals(path)) {
                List<ScoreLogDTO> logs = scoreLogDAO.getAllLogs();
                req.setAttribute("logs", logs);
                req.getRequestDispatcher("/WEB-INF/views/admin/score/history.jsp").forward(req, resp);
                return;
            }

            resp.sendError(HttpServletResponse.SC_NOT_FOUND);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        // 1. Lấy thông tin người đang đăng nhập để ghi log
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // Nếu admin chưa đăng nhập, redirect về login (tuỳ logic của bạn)
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int adminID = user.getEntityId(); // Lấy ID của Admin thực hiện hành động

        try {
            // === SAVE BULK (LƯU ĐIỂM) ===
            if ("/saveBulk".equals(path)) {
                int classID = parseInt(req.getParameter("classID"));
                int subjectID = parseInt(req.getParameter("subjectID"));
                int yearSemesterID = parseInt(req.getParameter("yearSemesterID"));

                if (classID <= 0 || subjectID <= 0 || yearSemesterID <= 0) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin lớp/học kỳ/môn học");
                    return;
                }

                List<StudentClass> students = studentClassDAO.getStudentsByClass(classID);

                Connection conn = DBUtil.getConnection();
                conn.setAutoCommit(false); // Bắt đầu Transaction
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

                        if (oral1 == null && oral2 == null && s15_1 == null && s15_2 == null &&
                                mid == null && fin == null && (notes == null || notes.trim().isEmpty()))
                            continue;

                        Score existing = scoreDAO.findByStudentSubjectYear(studentID, subjectID, yearSemesterID);

                        // Object tạm để so sánh
                        Score newScoreState = new Score();
                        newScoreState.setOralScore1(oral1);
                        newScoreState.setOralScore2(oral2);
                        newScoreState.setScore15Minute1(s15_1);
                        newScoreState.setScore15Minute2(s15_2);
                        newScoreState.setMidtermScore(mid);
                        newScoreState.setFinalScore(fin);
                        newScoreState.setNotes(notes);

                        String changeLog = "";
                        String action = "";

                        if (existing != null) {
                            // Cập nhật điểm cũ
                            // [MỚI]: Tính toán sự thay đổi
                            changeLog = getChangeDetails(existing, newScoreState);
                            action = "UPDATE";

                            // Gán giá trị mới vào object existing
                            existing.setOralScore1(oral1);
                            existing.setOralScore2(oral2);
                            existing.setScore15Minute1(s15_1);
                            existing.setScore15Minute2(s15_2);
                            existing.setMidtermScore(mid);
                            existing.setFinalScore(fin);
                            existing.setNotes(notes);
                            existing.setActive(true);

                            scoreService.calculateAveragesAndRating(existing);
                            scoreDAO.update(existing);
                        } else {
                            // Thêm mới
                            action = "INSERT";
                            changeLog = "Nhập mới điểm lần đầu (Admin).";

                            Score s = new Score();
                            s.setStudentID(studentID);
                            s.setSubjectID(subjectID);
                            s.setYearSemesterID(yearSemesterID);
                            s.setOralScore1(oral1);
                            s.setOralScore2(oral2);
                            s.setScore15Minute1(s15_1);
                            s.setScore15Minute2(s15_2);
                            s.setMidtermScore(mid);
                            s.setFinalScore(fin);
                            s.setNotes(notes);
                            s.setActive(true);

                            scoreService.calculateAveragesAndRating(s);
                            scoreDAO.insert(s);
                        }

                        // [MỚI]: GHI LOG VÀO DB
                        if (changeLog != null && !changeLog.isEmpty()) {
                            ScoreLog log = new ScoreLog(
                                    adminID, // ID người sửa (Admin)
                                    studentID,
                                    subjectID,
                                    yearSemesterID,
                                    action,
                                    changeLog);
                            scoreLogDAO.insert(conn, log); // Dùng chung connection để cùng commit
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
                        "&subjectID=" + subjectID + "&yearSemesterID=" + yearSemesterID + "&msg=saved");
                return;
            }

            if ("/delete".equals(path)) {
                int scoreID = parseInt(req.getParameter("scoreID"));
                int classID = parseInt(req.getParameter("classID"));
                int subjectID = parseInt(req.getParameter("subjectID"));
                int yearSemesterID = parseInt(req.getParameter("yearSemesterID"));
                if (scoreID > 0) {
                    scoreDAO.delete(scoreID);
                    // Có thể thêm log xóa ở đây nếu muốn
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

    // === CÁC HÀM PHỤ TRỢ (HELPER) ===

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

    // [MỚI]: Hàm so sánh thay đổi để ghi log
    private String getChangeDetails(Score oldS, Score newS) {
        StringBuilder sb = new StringBuilder();

        compareAndAppend(sb, "Miệng 1", oldS.getOralScore1(), newS.getOralScore1());
        compareAndAppend(sb, "Miệng 2", oldS.getOralScore2(), newS.getOralScore2());
        compareAndAppend(sb, "15p Lần 1", oldS.getScore15Minute1(), newS.getScore15Minute1());
        compareAndAppend(sb, "15p Lần 2", oldS.getScore15Minute2(), newS.getScore15Minute2());
        compareAndAppend(sb, "Giữa kì", oldS.getMidtermScore(), newS.getMidtermScore());
        compareAndAppend(sb, "Cuối kì", oldS.getFinalScore(), newS.getFinalScore());

        if (!Objects.equals(oldS.getNotes(), newS.getNotes())) {
            String oldNote = oldS.getNotes() == null ? "" : oldS.getNotes();
            String newNote = newS.getNotes() == null ? "" : newS.getNotes();
            if (!oldNote.equals(newNote)) {
                sb.append("Ghi chú: [").append(oldNote).append(" -> ").append(newNote).append("]; ");
            }
        }
        return sb.toString();
    }

    private void compareAndAppend(StringBuilder sb, String label, Double oldVal, Double newVal) {
        if (!Objects.equals(oldVal, newVal)) {
            String o = oldVal == null ? "_" : String.valueOf(oldVal);
            String n = newVal == null ? "_" : String.valueOf(newVal);
            sb.append(label).append(": ").append(o).append(" -> ").append(n).append("; ");
        }
    }
}
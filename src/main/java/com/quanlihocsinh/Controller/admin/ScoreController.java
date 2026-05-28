package com.quanlihocsinh.Controller.admin;

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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet("/admin/scores/*")
public class ScoreController extends HttpServlet {

    private ScoreLogDAO scoreLogDAO;
    private GradeDAO gradeDAO;
    private StudentClassDAO studentClassDAO;
    private TblClassDAO tblClassDAO;
    private SubjectDAO subjectDAO;
    private SubjectGradeDAO subjectGradeDAO;
    private YearSemesterDAO yearSemesterDAO;
    private ScoreDAO scoreDAO;
    private ScoreService scoreService;
    private ObjectMapper mapper;

    @Override
    public void init() throws ServletException {
        gradeDAO = new GradeDAO();
        studentClassDAO = new StudentClassDAO();
        tblClassDAO = new TblClassDAO();
        subjectDAO = new SubjectDAO();
        subjectGradeDAO = new SubjectGradeDAO();
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

            if (path == null || "/".equals(path) || "/list".equals(path)) {
                List<Grade> grades = gradeDAO.getAll();
                req.setAttribute("grades", grades);

                List<YearSemester> yearSemesters = yearSemesterDAO.getAllActive();
                req.setAttribute("yearSemesters", yearSemesters);

                int gradeID = parseInt(req.getParameter("gradeID"));
                int classID = parseInt(req.getParameter("classID"));
                int subjectID = parseInt(req.getParameter("subjectID"));
                int yearSemesterID = parseInt(req.getParameter("yearSemesterID"));

                tblClass selectedClass = null;
                List<?> subjects;

                if (classID > 0) {
                    selectedClass = tblClassDAO.getById(classID);
                    if (gradeID <= 0 && selectedClass != null) {
                        gradeID = selectedClass.getGradeID();
                    }
                }

                List<tblClass> classes = gradeID > 0
                        ? tblClassDAO.getAllActiveByGrade(resolveClassGradeValue(gradeID))
                        : java.util.Collections.emptyList();

                if (gradeID > 0) {
                    subjects = subjectGradeDAO.getSubjectsByGrade(gradeID);
                } else {
                    subjects = java.util.Collections.emptyList();
                }

                req.setAttribute("gradeID", gradeID);
                req.setAttribute("classes", classes);
                req.setAttribute("subjects", subjects);

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

                    tblClass cls = selectedClass != null ? selectedClass : tblClassDAO.getById(classID);
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

            if ("/history".equals(path)) {
                String action = req.getParameter("action");

                List<Grade> grades = gradeDAO.getAll();
                req.setAttribute("grades", grades);

                List<YearSemester> yearSemesters = yearSemesterDAO.getAllActive();
                req.setAttribute("yearSemesters", yearSemesters);

                int gradeID = parseInt(req.getParameter("gradeID"));
                int classID = parseInt(req.getParameter("classID"));
                int subjectID = parseInt(req.getParameter("subjectID"));
                int yearSemesterID = parseInt(req.getParameter("yearSemesterID"));

                // If user provided classID but not gradeID, resolve it
                if (classID > 0 && gradeID <= 0) {
                    tblClass selectedClass = tblClassDAO.getById(classID);
                    if (selectedClass != null) {
                        gradeID = resolveClassGradeValue(selectedClass.getGradeID());
                    }
                }

                // Determine whether we should show history: only when user requested it or
                // provided filters
                boolean showHistory = false;
                if ("viewHistory".equals(action) || classID > 0 || subjectID > 0 || yearSemesterID > 0) {
                    showHistory = true;
                }

                List<tblClass> classes = gradeID > 0
                        ? tblClassDAO.getAllActiveByGrade(resolveClassGradeValue(gradeID))
                        : java.util.Collections.emptyList();
                List<?> subjects = gradeID > 0
                        ? subjectGradeDAO.getSubjectsByGrade(gradeID)
                        : java.util.Collections.emptyList();

                req.setAttribute("gradeID", gradeID);
                req.setAttribute("classID", classID);
                req.setAttribute("subjectID", subjectID);
                req.setAttribute("yearSemesterID", yearSemesterID);
                req.setAttribute("classes", classes);
                req.setAttribute("subjects", subjects);
                req.setAttribute("showHistory", showHistory);

                if (showHistory) {
                    List<ScoreLogDTO> logs = scoreLogDAO.getFilteredLogs(classID, subjectID, yearSemesterID);
                    System.out.println("=== DEBUG SCORE LOG SIZE: " + (logs != null ? logs.size() : "NULL") + " ===");
                    req.setAttribute("logs", logs);
                }
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

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int adminID = user.getEntityId();

        try {

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
                conn.setAutoCommit(false);
                try {
                    List<ScoreLog> pendingLogs = new ArrayList<>();

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
                        boolean changed = false;

                        if (existing != null) {

                            changeLog = getChangeDetailsJson(existing, newScoreState);
                            action = isBlankExistingScore(existing) ? "INSERT" : "UPDATE";
                            if ("INSERT".equals(action)) {
                                changeLog = "{\"type\":\"INSERT\"}";
                            }
                            changed = true;

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

                            action = "INSERT";
                            changeLog = "{\"type\":\"INSERT\"}";
                            changed = true;

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

                        if (changed && changeLog != null && !"{}".equals(changeLog.trim())) {
                            ScoreLog log = new ScoreLog(
                                    adminID,
                                    classID,
                                    studentID,
                                    subjectID,
                                    yearSemesterID,
                                    action,
                                    changeLog);
                            pendingLogs.add(log);
                        }
                    }

                    scoreLogDAO.insertBatch(conn, pendingLogs);
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

    private int parseInt(String v) {
        try {
            return Integer.parseInt(v);
        } catch (Exception e) {
            return 0;
        }
    }

    private int resolveClassGradeValue(int gradeID) {
        Grade grade = gradeDAO.getById(gradeID);
        if (grade == null || grade.getGradeName() == null) {
            return gradeID;
        }

        try {
            return Integer.parseInt(grade.getGradeName().trim());
        } catch (Exception e) {
            return gradeID;
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

    private String getChangeDetailsJson(Score oldS, Score newS) {
        Map<String, Object> changes = new java.util.LinkedHashMap<>();

        appendChange(changes, "oral1", oldS.getOralScore1(), newS.getOralScore1());
        appendChange(changes, "oral2", oldS.getOralScore2(), newS.getOralScore2());
        appendChange(changes, "s15_1", oldS.getScore15Minute1(), newS.getScore15Minute1());
        appendChange(changes, "s15_2", oldS.getScore15Minute2(), newS.getScore15Minute2());
        appendChange(changes, "mid", oldS.getMidtermScore(), newS.getMidtermScore());
        appendChange(changes, "fin", oldS.getFinalScore(), newS.getFinalScore());

        if (!Objects.equals(oldS.getNotes(), newS.getNotes())) {
            changes.put("notes", new String[] {
                    oldS.getNotes() == null ? "" : oldS.getNotes(),
                    newS.getNotes() == null ? "" : newS.getNotes() });
        }

        try {
            return mapper.writeValueAsString(changes);
        } catch (Exception e) {
            return "{}";
        }
    }

    private void appendChange(Map<String, Object> changes, String key, Double oldVal, Double newVal) {
        if (!Objects.equals(oldVal, newVal)) {
            changes.put(key, new Double[] { oldVal, newVal });
        }
    }

    private void compareAndAppend(StringBuilder sb, String label, Double oldVal, Double newVal) {
        if (!Objects.equals(oldVal, newVal)) {
            String o = oldVal == null ? "_" : String.valueOf(oldVal);
            String n = newVal == null ? "_" : String.valueOf(newVal);
            sb.append(label).append(": ").append(o).append(" -> ").append(n).append("; ");
        }
    }

    private boolean isBlankExistingScore(Score score) {
        if (score == null) {
            return true;
        }

        return isBlankScoreValue(score.getOralScore1())
                && isBlankScoreValue(score.getOralScore2())
                && isBlankScoreValue(score.getScore15Minute1())
                && isBlankScoreValue(score.getScore15Minute2())
                && isBlankScoreValue(score.getMidtermScore())
                && isBlankScoreValue(score.getFinalScore())
                && isBlankText(score.getNotes());
    }

    private boolean isBlankScoreValue(Double value) {
        return value == null || value.doubleValue() == 0.0d;
    }

    private boolean isBlankText(String value) {
        return value == null || value.trim().isEmpty();
    }
}
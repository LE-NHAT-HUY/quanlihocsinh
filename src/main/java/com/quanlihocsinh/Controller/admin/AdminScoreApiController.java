package com.quanlihocsinh.Controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quanlihocsinh.dao.SubjectGradeDAO;
import com.quanlihocsinh.dao.GradeDAO;
import com.quanlihocsinh.dao.TblClassDAO;
import com.quanlihocsinh.model.Grade;
import com.quanlihocsinh.model.SubjectGrade;
import com.quanlihocsinh.model.tblClass;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = { "/admin/api/classes", "/admin/api/subjects" })
public class AdminScoreApiController extends HttpServlet {

    private TblClassDAO tblClassDAO;
    private SubjectGradeDAO subjectGradeDAO;
    private GradeDAO gradeDAO;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        tblClassDAO = new TblClassDAO();
        subjectGradeDAO = new SubjectGradeDAO();
        gradeDAO = new GradeDAO();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        int gradeID = parseInt(req.getParameter("gradeID"));

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        if (gradeID <= 0) {
            objectMapper.writeValue(resp.getWriter(), new ArrayList<>());
            return;
        }

        if ("/admin/api/classes".equals(path)) {
            List<Map<String, Object>> result = new ArrayList<>();
            int classGradeValue = resolveClassGradeValue(gradeID);
            for (tblClass cls : tblClassDAO.getAllActiveByGrade(classGradeValue)) {
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("classID", cls.getClassID());
                item.put("className", cls.getClassName());
                item.put("gradeID", cls.getGradeID());
                item.put("currentStudents", cls.getCurrentStudents());
                item.put("maxStudents", cls.getMaxStudents());
                result.add(item);
            }
            objectMapper.writeValue(resp.getWriter(), result);
            return;
        }

        if ("/admin/api/subjects".equals(path)) {
            List<Map<String, Object>> result = new ArrayList<>();
            for (SubjectGrade subject : subjectGradeDAO.getSubjectsByGrade(gradeID)) {
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("subjectID", subject.getSubjectID());
                item.put("subjectName", subject.getSubjectName());
                item.put("periods", subject.getPeriods());
                result.add(item);
            }
            objectMapper.writeValue(resp.getWriter(), result);
            return;
        }

        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
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
}

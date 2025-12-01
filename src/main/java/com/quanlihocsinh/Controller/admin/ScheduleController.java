package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.ScheduleDAO;
import com.quanlihocsinh.model.Schedule;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/admin/schedule/*")
public class ScheduleController extends HttpServlet {
    private ScheduleDAO dao = new ScheduleDAO();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null)
            action = "list";

        switch (action) {
            case "add":
                request.getRequestDispatcher("/WEB-INF/views/admin/schedule/add.jsp").forward(request, response);
                break;
            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Schedule sEdit = dao.getById(idEdit);
                request.setAttribute("schedule", sEdit);
                request.getRequestDispatcher("/WEB-INF/views/admin/schedule/edit.jsp").forward(request, response);
                break;
            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                dao.delete(idDelete);
                response.sendRedirect(request.getContextPath() + "/admin/schedule?action=list");
                break;
            case "toggleStatus":
                int idToggle = Integer.parseInt(request.getParameter("id"));
                boolean newStatus = "on".equals(request.getParameter("isActive"))
                        || "true".equals(request.getParameter("isActive"));
                dao.toggleStatus(idToggle, newStatus);
                response.sendRedirect(request.getContextPath() + "/admin/schedule?action=list");
                break;
            case "list":
            default:
                List<Schedule> list = dao.getAll();
                request.setAttribute("schedules", list);
                request.getRequestDispatcher("/WEB-INF/views/admin/schedule/list.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null)
            action = "list";

        switch (action) {
            case "add":
                Schedule sAdd = extractScheduleFromRequest(request);
                dao.add(sAdd);
                response.sendRedirect(request.getContextPath() + "/admin/schedule?action=list");
                break;
            case "edit":
                Schedule sEdit = extractScheduleFromRequest(request);
                sEdit.setScheduleID(Integer.parseInt(request.getParameter("id")));
                dao.update(sEdit);
                response.sendRedirect(request.getContextPath() + "/admin/schedule?action=list");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/schedule?action=list");
                break;
        }
    }

    private Schedule extractScheduleFromRequest(HttpServletRequest request) {
        Schedule s = new Schedule();

        s.setSemesterID(parseInt(request.getParameter("semesterID")));
        s.setClassID(parseInt(request.getParameter("classID")));
        s.setSubjectID(parseInt(request.getParameter("subjectID")));
        s.setTeacherID(request.getParameter("teacherID"));
        s.setNotes(request.getParameter("notes"));
        s.setIsActive("on".equals(request.getParameter("isActive")) || "true".equals(request.getParameter("isActive")));

        try {
            String start = request.getParameter("startDate");
            String end = request.getParameter("endDate");
            if (start != null && !start.isEmpty())
                s.setStartDate(sdf.parse(start));
            if (end != null && !end.isEmpty())
                s.setEndDate(sdf.parse(end));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        s.setPeriod(parseInt(request.getParameter("period")));
        s.setDayOfWeek(parseInt(request.getParameter("dayOfWeek")));
        String hr = request.getParameter("homeroomTeacher");
        s.setHomeroomTeacher(hr != null && !hr.isEmpty() ? hr.charAt(0) : 'N');
        s.setWeekNumber(parseInt(request.getParameter("weekNumber")));

        return s;
    }

    private int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }
}

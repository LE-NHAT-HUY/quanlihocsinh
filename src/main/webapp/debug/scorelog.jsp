<%@ page import="java.sql.*" %>
<%@ page import="com.quanlihocsinh.util.DBUtil" %>
<%
    response.setContentType("text/html; charset=UTF-8");
%>
<html>
<head>
    <title>ScoreLog Debug Dump</title>
    <style>table{border-collapse:collapse}td,th{border:1px solid #ccc;padding:6px}</style>
</head>
<body>
<h2>ScoreLog - Recent Rows</h2>
<%
    try (Connection conn = DBUtil.getConnection()) {
        DatabaseMetaData md = conn.getMetaData();
        boolean hasClass = false;
        try (ResultSet cols = md.getColumns(null, null, "ScoreLog", "ClassID")) {
            hasClass = cols.next();
        } catch (Exception e) { }
        out.println("<p>ClassID column present: " + hasClass + "</p>");

        String sql = "SELECT TOP 100 LogID, " + (hasClass ? "ClassID, " : "") +
                "TeacherID, StudentID, SubjectID, SemesterID, ActionType, ChangeContent, ChangeDate FROM ScoreLog ORDER BY ChangeDate DESC";
        out.println("<p>SQL: " + sql + "</p>");
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            out.println("<table>");
            out.println("<tr>");
            if (hasClass) out.println("<th>ClassID</th>");
            out.println("<th>LogID</th><th>TeacherID</th><th>StudentID</th><th>SubjectID</th><th>SemesterID</th><th>ActionType</th><th>ChangeContent</th><th>ChangeDate</th>");
            out.println("</tr>");
            while (rs.next()) {
                out.println("<tr>");
                if (hasClass) out.println("<td>" + rs.getObject("ClassID") + "</td>");
                out.println("<td>" + rs.getObject("LogID") + "</td>");
                out.println("<td>" + rs.getObject("TeacherID") + "</td>");
                out.println("<td>" + rs.getObject("StudentID") + "</td>");
                out.println("<td>" + rs.getObject("SubjectID") + "</td>");
                out.println("<td>" + rs.getObject("SemesterID") + "</td>");
                out.println("<td>" + rs.getObject("ActionType") + "</td>");
                out.println("<td>" + rs.getObject("ChangeContent") + "</td>");
                out.println("<td>" + rs.getObject("ChangeDate") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }
    } catch (Exception e) {
        out.println("<pre>");
        e.printStackTrace(out);
        out.println("</pre>");
    }
%>
</body>
</html>
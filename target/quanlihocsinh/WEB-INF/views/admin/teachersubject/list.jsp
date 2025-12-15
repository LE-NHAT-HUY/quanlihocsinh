<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Gán giáo viên - môn học</h2>

<form method="post" action="${pageContext.request.contextPath}/admin/teacher-subject">
    <input type="hidden" name="action" value="assign"/>

    <select name="teacherID">
        <c:forEach var="t" items="${teachers}">
            <option value="${t.id}">${t.fullName}</option>
        </c:forEach>
    </select>

    <select name="subjectID">
        <c:forEach var="s" items="${subjects}">
            <option value="${s.subjectID}">${s.subjectName}</option>
        </c:forEach>
    </select>

    <button type="submit">Gán</button>
</form>

<hr>

<table border="1">
    <tr>
        <th>TeacherID</th>
        <th>SubjectID</th>
        <th>Action</th>
    </tr>
    <c:forEach var="m" items="${mappings}">
        <tr>
            <td>${m.teacherID}</td>
            <td>${m.subjectID}</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/admin/teacher-subject">
                    <input type="hidden" name="action" value="unassign"/>
                    <input type="hidden" name="teacherID" value="${m.teacherID}"/>
                    <input type="hidden" name="subjectID" value="${m.subjectID}"/>
                    <button type="submit">Hủy</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

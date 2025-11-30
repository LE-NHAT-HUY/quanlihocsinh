<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main class="main">
    <h2>Thêm Học sinh vào Lớp</h2>
    <form action="${pageContext.request.contextPath}/admin/studentclass" method="post">
        <label>Học sinh:</label>
        <select name="studentID">
            <c:forEach var="student" items="${students}">
                <option value="${student.id}">${student.fullName}</option>
            </c:forEach>
        </select><br/>

        <label>Lớp:</label>
        <select name="classID">
            <c:forEach var="cls" items="${classes}">
                <option value="${cls.classID}">${cls.className}</option>
            </c:forEach>
        </select><br/>

        <label>CohortID (tùy chọn):</label>
        <input type="text" name="cohortID"/><br/>

        <label>YearSemesterID:</label>
        <input type="text" name="yearSemesterID" required/><br/>

        <button type="submit">Thêm</button>
    </form>
</main>

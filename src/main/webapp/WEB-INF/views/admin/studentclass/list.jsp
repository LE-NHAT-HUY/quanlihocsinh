<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main class="main">
    <h2>Danh sách Học sinh trong Lớp</h2>
    <a href="${pageContext.request.contextPath}/admin/studentclass/add">Thêm Học sinh vào Lớp</a>
    <table border="1" cellspacing="0" cellpadding="5">
        <tr>
            <th>ID</th>
            <th>Học sinh</th>
            <th>Lớp</th>
            <th>CohortID</th>
            <th>YearSemesterID</th>
            <th>Active</th>
            <th>Thao tác</th>
        </tr>
        <c:forEach var="sc" items="${studentClassList}">
            <tr>
                <td>${sc.studentClassID}</td>
                <td>${sc.studentID}</td>
                <td>${sc.classID}</td>
                <td>${sc.cohortID}</td>
                <td>${sc.yearSemesterID}</td>
                <td>${sc.isActive}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/studentclass/delete?id=${sc.studentClassID}" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</main>

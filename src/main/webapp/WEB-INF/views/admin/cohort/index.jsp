<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.quanlihocsinh.model.Cohort" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<h2>Danh sách niên khóa</h2>

<table>
    <thead>
        <tr>
            <th>STT</th>
            <th>Mã khóa</th>
            <th>Năm vào</th>
            <th>Năm ra</th>
            <th>Niên khóa</th>
            <th>Hiển thị</th>
            <th>Chức năng</th>
        </tr>
    </thead>
    <tbody>
        <c:set var="stt" value="0"/>
        <c:forEach var="item" items="${cohorts}">
            <c:set var="stt" value="${stt + 1}" />
            <tr>
                <td>${stt}</td>
                <td>${item.cohortID}</td>
                <td>${item.startYear}</td>
                <td>${item.endYear}</td>
                <td>${item.cohortName}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin/cohort/toggleStatus" method="post">
                        <input type="hidden" name="id" value="${item.cohortID}" />
                        <input type="checkbox" name="isActive" ${item.isActive ? 'checked' : ''} onchange="this.form.submit()" />
                    </form>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/cohort/edit?id=${item.cohortID}">Edit</a>
                    <a href="${pageContext.request.contextPath}/admin/cohort/delete?id=${item.cohortID}"
                       onclick="return confirm('Bạn có chắc muốn xóa niên khóa này không?');">
                       Delete
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

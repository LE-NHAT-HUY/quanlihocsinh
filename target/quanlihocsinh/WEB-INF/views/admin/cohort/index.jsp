<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.quanlihocsinh.model.Cohort" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<h2>Danh sách niên khóa</h2>

<table border="1" cellspacing="0" cellpadding="5">
    <thead>
        <tr>
            <th>STT</th>
            <th>Mã khóa</th>
            <th>Năm vào</th>
            <th>Năm ra</th>
            <th>Niên khóa</th>
            <th>Hiển thị</th>
        </tr>
    </thead>
    <tbody>
        <c:set var="index" value="0"/>
        <c:forEach var="item" items="${cohorts}">
            <c:set var="index" value="${index + 1}"/>
            <tr>
                <td>${index}</td>
                <td>${item.cohortID}</td>
                <td>${item.startYear}</td>
                <td>${item.endYear}</td>
                <td>${item.cohortName}</td>
                <td>${item.isActive}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main class="main">
    <div class="pagetitle">
        <h2>Danh sách Môn học</h2>
        <a href="add" class="btn btn-primary">Thêm môn học mới</a>
    </div>

    <table class="table table-bordered table-hover mt-3">
        <thead class="table-light">
            <tr>
                <th>ID</th>
                <th>Tên môn</th>
                <th>Số tiết</th>
                <th>Học kỳ</th>
                <th>Trạng thái</th>
                <th>Phòng ban</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="subject" items="${subjectList}">
                <tr>
                    <td>${subject.subjectID}</td>
                    <td>${subject.subjectName}</td>
                    <td>${subject.numberOfLesson}</td>
                    <td>${subject.semester}</td>
                    <td>
                        <c:choose>
                            <c:when test="${subject.isActive}">Hoạt động</c:when>
                            <c:otherwise>Không hoạt động</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${subject.departmentID}</td>
                    <td>
                        <a href="edit?id=${subject.subjectID}" class="btn btn-warning btn-sm">Sửa</a>
                        <a href="delete?id=${subject.subjectID}" class="btn btn-danger btn-sm"
                           onclick="return confirm('Bạn có chắc muốn xóa môn học này?');">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</main>

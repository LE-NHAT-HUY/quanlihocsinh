<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Nội dung chính của trang profile/list.jsp -->
<div class="page-header mb-3">
    <h3>Hồ sơ người học</h3>
</div>

<div class="table-responsive">
    <table class="table table-striped table-bordered">
        <thead class="table-dark">
            <tr>
                <th>#</th>
                <th>Mã HS</th>
                <th>Họ và tên</th>
                <th>Ngày sinh</th>
                <th>Giới tính</th>
                <th>Địa chỉ</th>
                <th>SĐT</th>
                <th>Trạng thái</th>
                <th>Ảnh</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="student" items="${studentList}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${student.studentID}</td>
                    <td>${student.fullName}</td>
                    <td>
                        <c:if test="${not empty student.birth}">
                            <fmt:formatDate value="${student.birth}" pattern="dd/MM/yyyy"/>
                        </c:if>
                    </td>
                    <td>${student.gender}</td>
                    <td>${student.address}</td>
                    <td>${student.numberPhone}</td>
                    <td>
                        <c:choose>
                            <c:when test="${student.isActive}">Đang học</c:when>
                            <c:otherwise>Ngừng học</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${not empty student.images}">
                            <img src="${pageContext.request.contextPath}/assets/img/${student.images}" 
                                 alt="${student.fullName}" 
                                 style="width:50px; height:50px; object-fit:cover; border-radius:50%;">
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

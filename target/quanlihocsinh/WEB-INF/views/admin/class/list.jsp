<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>
<main id="main" class="main">
    <div class="pagetitle">
        <h2>Danh sách Lớp học</h2>
        <a href="${pageContext.request.contextPath}/admin/class?action=add" class="btn btn-success mb-2">
            <i class="bi bi-plus-circle"></i> Thêm mới
        </a>
    </div>

    <section class="section dashboard">
        <div class="card recent-sales overflow-auto">
            <div class="card-body mt-4">
                <table class="table table-borderless datatable">
                    <thead>
                        <tr>
                            <th class="text-center">STT</th>
                            <th class="text-center">ID</th>
                            <th class="text-center">Tên lớp</th>
                            <th class="text-center">Khối</th>
                            <th class="text-center">Năm học</th>
                            <th class="text-center">Khóa</th>
                            <th class="text-center">Sĩ số</th>
                            <th class="text-center">Trạng thái</th>
                            <th class="text-center">Chức năng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="stt" value="0"/>
                        <c:forEach var="cls" items="${classes}">
                            <c:set var="stt" value="${stt + 1}" />
                            <tr>
                                <td class="text-center">${stt}</td>
                                <td class="text-center">${cls.classID}</td>
                                <td class="text-center"> ${cls.gradeID}${cls.className}</td>
                                <td class="text-center">
                                    ${cls.gradeID}
                                </td>

                                <td class="text-center">
                                    <c:choose>
                                        <c:when test="${not empty cls.schoolYear}">
                                            ${cls.schoolYear}
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="cohort" items="${cohortList}">
                                                <c:if test="${cohort.cohortID == cls.cohortID}">
                                                    ${cohort.startYear} - ${cohort.endYear}
                                                </c:if>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                               <td class="text-center">
                                    <c:forEach var="cohort" items="${cohortList}">
                                        <c:if test="${cohort.cohortID == cls.cohortID}">
                                            ${cohort.cohortName}
                                        </c:if>
                                    </c:forEach>
                                </td>




                                <td class="text-center">${cls.currentStudents} / ${cls.maxStudents}</td>
                                <td class="text-center">
                                    <form action="${pageContext.request.contextPath}/admin/class" method="post">
                                        <input type="hidden" name="action" value="toggleStatus"/>
                                        <input type="hidden" name="id" value="${cls.classID}" />
                                        <input type="checkbox" name="isActive" ${cls.active ? "checked" : ""} onchange="this.form.submit()" />
                                    </form>
                                </td>
                                <td class="text-center">
                                    <a href="${pageContext.request.contextPath}/admin/class?action=edit&id=${cls.classID}" class="btn btn-primary btn-sm">
                                        <i class="bi bi-pencil"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/admin/class?action=delete&id=${cls.classID}" class="btn btn-danger btn-sm"
                                       onclick="return confirm('Bạn có chắc muốn xóa lớp này không?');">
                                        <i class="bi bi-trash"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/admin/studentclass/list?classID=${cls.classID}" class="btn btn-info btn-sm" title="Xem học sinh">
                                        <i class="bi bi-people"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty classes}">
                            <tr>
                                <td colspan="8" class="text-center py-4 text-muted">
                                    <i class="bi bi-inbox fs-1 d-block mb-2"></i>
                                    Chưa có lớp học nào
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </section>
</main>

<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<c:if test="${not empty classes}">
<script>
$(document).ready(function() {
  $('.datatable').DataTable({
    "pageLength": 10,
    "lengthMenu": [5,10,25,50,100],
    "order": [],
    "columnDefs": [ { "orderable": false, "targets": [6,7] } ]
  });
});
</script>
</c:if>

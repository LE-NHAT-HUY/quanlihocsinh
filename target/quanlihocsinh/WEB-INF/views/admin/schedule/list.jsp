<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Danh sách Thời khóa biểu</h2>
        <a href="${pageContext.request.contextPath}/admin/schedule?action=add" class="btn btn-success mb-2">
            <i class="bi bi-plus-circle"></i> Thêm mới
        </a>
    </div>

    <section class="section dashboard">
        <div class="row">
            <div class="col-12">
                <div class="card recent-sales overflow-auto">
                    <div class="card-body mt-4">
                        <table class="table table-borderless datatable">
                            <thead>
                                <tr>
                                    <th class="text-center">STT</th>
                                    <th class="text-center">ID</th>
                                    <th class="text-center">SemesterID</th>
                                    <th class="text-center">ClassID</th>
                                    <th class="text-center">SubjectID</th>
                                    <th class="text-center">TeacherID</th>
                                    <th class="text-center">Ngày bắt đầu</th>
                                    <th class="text-center">Ngày kết thúc</th>
                                    <th class="text-center">Hoạt động</th>
                                    <th class="text-center">Chức năng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="stt" value="0"/>
                                <c:forEach var="schedule" items="${schedules}">
                                    <c:set var="stt" value="${stt + 1}" />
                                    <tr>
                                        <td class="text-center">${stt}</td>
                                        <td class="text-center">${schedule.scheduleID}</td>
                                        <td class="text-center">${schedule.semesterID}</td>
                                        <td class="text-center">${schedule.classID}</td>
                                        <td class="text-center">${schedule.subjectID}</td>
                                        <td class="text-center">${schedule.teacherID}</td>
                                        <td class="text-center">${schedule.startDate}</td>
                                        <td class="text-center">${schedule.endDate}</td>
                                        <td class="text-center">
                                            <form action="${pageContext.request.contextPath}/admin/schedule" method="get">
                                                <input type="hidden" name="action" value="toggleStatus"/>
                                                <input type="hidden" name="id" value="${schedule.scheduleID}" />
                                                <input type="checkbox" name="isActive" ${schedule.isActive ? "checked" : ""} onchange="this.form.submit()"/>
                                            </form>
                                        </td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/admin/schedule?action=edit&id=${schedule.scheduleID}" class="btn btn-primary btn-sm">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/schedule?action=delete&id=${schedule.scheduleID}" class="btn btn-danger btn-sm"
                                               onclick="return confirm('Bạn có chắc muốn xóa thời khóa biểu này không?');">
                                                <i class="bi bi-trash"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<!-- DataTables -->
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script>
$(document).ready(function() {
    $('.datatable').DataTable({
        "pageLength": 10,
        "lengthMenu": [5,10,25,50,100],
        "order": [],
        "columnDefs": [ { "orderable": false, "targets": [8,9] } ]
    });
});
</script>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Danh sách Giáo viên</h2>
        <a href="${pageContext.request.contextPath}/admin/teacher?action=add" class="btn btn-success mb-2">
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
                                    <th class="text-center">Mã GV</th>
                                    <th class="text-center">Họ tên</th>
                                    <th class="text-center">Ngày sinh</th>
                                    <th class="text-center">Giới tính</th>
                                    <th class="text-center">Địa chỉ</th>
                                    <th class="text-center">Trạng thái</th>
                                    <th class="text-center">Chức năng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="stt" value="0"/>
                                <c:forEach var="teacher" items="${teachers}">
                                    <c:set var="stt" value="${stt + 1}" />
                                    <tr>
                                        <td class="text-center">${stt}</td>
                                        <td class="text-center">${teacher.id}</td>
                                        <td class="text-center">${teacher.teacherID}</td>
                                        <td class="text-center">${teacher.fullName}</td>
                                        <td class="text-center">
                                            <c:if test="${not empty teacher.birth}">
                                                <fmt:formatDate value="${teacher.birth}" pattern="yyyy-MM-dd"/>
                                            </c:if>
                                        </td>
                                        <td class="text-center">${teacher.gender}</td>
                                        <td class="text-center">${teacher.address}</td>

                                        <td class="text-center">
                                            <form action="${pageContext.request.contextPath}/admin/teacher" method="post">
                                                <input type="hidden" name="action" value="toggleStatus"/>
                                                <input type="hidden" name="id" value="${teacher.id}" />
                                                <input type="checkbox" name="isActive" ${teacher.isActive ? "checked" : ""} onchange="this.form.submit()" />
                                            </form>
                                        </td>

                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/admin/teacher?action=edit&id=${teacher.id}" class="btn btn-primary btn-sm">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/teacher?action=delete&id=${teacher.id}" class="btn btn-danger btn-sm"
                                               onclick="return confirm('Bạn có chắc muốn xóa giáo viên này không?');">
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
        "columnDefs": [ { "orderable": false, "targets": [7,8] } ]
    });
});
</script>

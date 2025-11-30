<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Danh sách người dùng</h2>
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
                                    <th class="text-center">Mã User</th>
                                    <th class="text-center">Tên đăng nhập</th>
                                    <th class="text-center">Họ và tên</th>
                                    <th class="text-center">Quyền</th>
                                    <th class="text-center">Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="stt" value="0"/>
                                <c:forEach var="u" items="${users}">
                                    <c:set var="stt" value="${stt + 1}" />
                                    <tr>
                                        <td class="text-center">${stt}</td>
                                        <td class="text-center">${u.userID}</td>
                                        <td class="text-center">${u.username}</td>
                                        <td class="text-center">${u.fullName}</td>
                                        <td class="text-center">
                                            <c:set var="roles" value="${userRolesMap[u.userID]}" />
                                            <c:choose>
                                                <c:when test="${empty roles}">
                                                    Chưa phân quyền
                                                </c:when>
                                                <c:otherwise>
                                                    <c:forEach var="r" items="${roles}" varStatus="status">
                                                        ${r.roleName}<c:if test="${!status.last}">, </c:if>
                                                    </c:forEach>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/admin/assign-role?id=${u.userID}"
                                               class="btn btn-primary btn-sm">
                                                <i class="bi bi-person-check"></i> Gán quyền
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
        "columnDefs": [ { "orderable": false, "targets": [4,5] } ]
    });
});
</script>

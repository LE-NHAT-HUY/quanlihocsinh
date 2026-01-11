<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle d-flex justify-content-between align-items-center">
        <h2>Danh sách tài khoản</h2>
        <a href="${pageContext.request.contextPath}/admin/createUser" class="btn btn-primary">
            <i class="bi bi-plus-circle me-1"></i> Thêm tài khoản mới
        </a>
    </div>

    <section class="section dashboard">
        <div class="row">
            <div class="col-12">
                <c:if test="${param.msg == 'success'}">
                    <div class="alert alert-success alert-dismissible fade show mt-2">
                        Thao tác thành công!
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <div class="card recent-sales overflow-auto mt-3">
                    <div class="card-body mt-4">
                        <table class="table table-borderless datatable table-hover">
                            <thead class="table-light">
                                <tr>
                                    <th class="text-center">ID</th>
                                    <th>Tên đăng nhập</th>
                                    <th>Họ và tên</th>
                                    <th class="text-center">Vai trò</th>
                                    <th class="text-center">Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="u" items="${users}">
                                    <tr>
                                        <td class="text-center text-muted">#${u.userID}</td>
                                        
                                        <td class="fw-bold text-primary">
                                            ${u.username}
                                        </td>
                                        
                                        <td>
                                            <div class="d-flex align-items-center">
                                                <div class="rounded-circle d-flex align-items-center justify-content-center bg-light me-2" style="width: 32px; height: 32px; overflow: hidden;">
                                                    <c:choose>
                                                        <%-- Sửa fullname -> fullName --%>
                                                        <c:when test="${not empty u.profile.images}">
                                                            <img src="${pageContext.request.contextPath}/assets/img/${u.profile.images}" style="width: 100%; height: 100%; object-fit: cover;">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <i class="bi bi-person text-secondary"></i>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <span>${u.profile.fullName}</span>
                                            </div>
                                        </td>
                                        
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${u.roleId == 1}">
                                                    <span class="badge bg-danger"><i class="bi bi-shield-lock me-1"></i>Admin</span>
                                                </c:when>
                                                <c:when test="${u.roleId == 2}">
                                                    <span class="badge bg-primary"><i class="bi bi-person-video3 me-1"></i>Giáo viên</span>
                                                </c:when>
                                                <c:when test="${u.roleId == 3}">
                                                    <span class="badge bg-success"><i class="bi bi-mortarboard me-1"></i>Học sinh</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary">Khách</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/admin/editUser?id=${u.userID}" 
                                               class="btn btn-outline-primary btn-sm" title="Sửa">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            
                                            <c:if test="${sessionScope.user.userID != u.userID}">
                                                <a href="${pageContext.request.contextPath}/admin/deleteUser?id=${u.userID}" 
                                                   class="btn btn-outline-danger btn-sm" 
                                                   onclick="return confirm('Bạn có chắc muốn xóa tài khoản ${u.username}?');"
                                                   title="Xóa">
                                                    <i class="bi bi-trash"></i>
                                                </a>
                                            </c:if>
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

<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script>
    $(document).ready(function() {
        $('.datatable').DataTable({
            "pageLength": 10,
            "columnDefs": [ { "orderable": false, "targets": [4] } ],
            "language": { "url": "//cdn.datatables.net/plug-ins/1.13.6/i18n/vi.json" }
        });
    });
</script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Gán quyền cho tài khoản: ${user.username}</h2>
        <a href="${pageContext.request.contextPath}/admin/assign-role" class="btn btn-secondary mb-2">
            <i class="bi bi-arrow-left-circle"></i> Quay lại danh sách
        </a>
    </div>

    <section class="section dashboard">
        <div class="row">
            <div class="col-12">
                <div class="card overflow-auto">
                    <div class="card-body mt-4">
                        <form method="post">
                            <input type="hidden" name="userId" value="${user.userID}" />

                            <div class="mb-3">
                                <c:forEach var="role" items="${roles}">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" 
                                               name="role" value="${role.roleID}"
                                               id="role${role.roleID}"
                                               <c:forEach var="aid" items="${assigned}">
                                                   <c:if test="${aid == role.roleID}">checked</c:if>
                                               </c:forEach> />
                                        <label class="form-check-label" for="role${role.roleID}">
                                            ${role.roleName}
                                        </label>
                                    </div>
                                </c:forEach>
                            </div>

                            <button type="submit" class="btn btn-success">
                                <i class="bi bi-save"></i> Lưu thay đổi
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<!-- DataTables (nếu muốn thêm phân trang/checkbox table cho nhiều role) -->
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script>
$(document).ready(function() {
    $('.datatable').DataTable({
        "pageLength": 10,
        "lengthMenu": [5,10,25,50,100],
        "order": [],
        "columnDefs": [ { "orderable": false, "targets": [0] } ]
    });
});
</script>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle d-flex flex-wrap justify-content-between align-items-center gap-2">
        <h2 class="mb-0">Danh sách Giáo viên</h2>
        <a href="${pageContext.request.contextPath}/admin/teacher?action=add" class="btn btn-primary">
            <i class="bi bi-plus-lg me-1"></i> Thêm mới
        </a>
    </div>

    <c:if test="${not empty sessionScope.flashSuccess}">
        <div class="alert alert-success">${sessionScope.flashSuccess}</div>
        <c:remove var="flashSuccess" scope="session" />
    </c:if>
    <c:if test="${not empty sessionScope.flashError}">
        <div class="alert alert-danger">${sessionScope.flashError}</div>
        <c:remove var="flashError" scope="session" />
    </c:if>

    <section class="section dashboard">
        <div class="row">
            <div class="col-12">
                <div class="card recent-sales overflow-auto">
                    <div class="card-body mt-4">
                        <table class="table table-borderless datatable align-middle">
                            <thead>
                                <tr>
                                    <th class="text-center">STT</th>
                                    <th class="text-center">Mã GV</th>
                                    <th class="text-center">Họ tên</th>
                                    <th class="text-center">Giới tính</th>
                                    <th class="text-center">Điện thoại</th>
                                    <th class="text-center">Email</th>
                                    <th class="text-center">Hoạt động</th>
                                    <th class="text-center">Chức năng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="stt" value="0"/>
                                <c:forEach var="teacher" items="${teachers}">
                                    <c:set var="stt" value="${stt + 1}" />
                                    <tr>
                                        <td class="text-center">${stt}</td>
                                        <td class="text-center">${teacher.teacherID}</td>
                                        <td>${teacher.fullName}</td>
                                        <td class="text-center">${teacher.gender}</td>
                                        <td class="text-center">${teacher.numberPhone}</td>
                                        <td class="text-center">${teacher.email}</td>
                                        <td class="text-center">
                                            <form action="${pageContext.request.contextPath}/admin/teacher" method="get" class="d-inline">
                                                <input type="hidden" name="action" value="toggleStatus"/>
                                                <input type="hidden" name="id" value="${teacher.id}" />
                                                <input type="checkbox" name="isActive" ${teacher.isActive ? 'checked' : ''} onchange="this.form.submit()" />
                                            </form>
                                        </td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/admin/teacher?action=edit&id=${teacher.id}" class="btn btn-primary btn-sm">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            <a href="#" class="btn btn-warning btn-sm text-white"
                                               onclick="openTeacherAccountModal('${teacher.teacherID}'); return false;">
                                                <i class="bi bi-key-fill"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/teacher-degree?teacherID=${teacher.id}" class="btn btn-warning btn-sm text-white">
                                                <i class="bi bi-award"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/teacher-contract?teacherID=${teacher.id}" class="btn btn-info btn-sm text-white">
                                                <i class="bi bi-file-earmark-text"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/teacher?action=delete&id=${teacher.id}" class="btn btn-danger btn-sm"
                                               onclick="return confirm('Bạn có chắc muốn xóa giáo viên này không?');">
                                                <i class="bi bi-trash"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty teachers}">
                                    <tr>
                                        <td colspan="8" class="text-center py-4 text-muted">Chưa có giáo viên nào</td>
                                    </tr>
                                </c:if>
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
        pageLength: 10,
        lengthMenu: [5, 10, 25, 50, 100],
        order: [],
        columnDefs: [{ orderable: false, targets: [6, 7] }]
    });
});
</script>

<!-- Teacher account modal -->
<div class="modal fade" id="teacherAccountModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Quản lý tài khoản giáo viên</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div id="tAccountLoading" class="text-muted">Đang tải thông tin tài khoản...</div>

                <div id="tAccountEmptyState" class="d-none">
                    <div class="alert alert-warning mb-3">Giáo viên này chưa có tài khoản.</div>
                    <button type="button" class="btn btn-success" onclick="generateTeacherAccount(currentTeacherAccountID)">Cấp tài khoản ngay</button>
                </div>

                <div id="tAccountDetailState" class="d-none">
                    <form id="tAccountForm" onsubmit="return false;">
                        <input type="hidden" id="tAccountUserId" value="">
                        <input type="hidden" id="tAccountTeacherID" value="">
                        <div class="row g-3">
                            <div class="col-md-6">
                                <label class="form-label">Username</label>
                                <input type="text" id="tAccountUsername" class="form-control" readonly>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Họ tên</label>
                                <input type="text" id="tAccountFullName" class="form-control" readonly>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Password mới</label>
                                <input type="password" id="tAccountPassword" class="form-control" placeholder="Để trống nếu không đổi mật khẩu">
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Trạng thái</label>
                                <select id="tAccountStatus" class="form-select">
                                    <option value="1">Kích hoạt</option>
                                    <option value="0">Khóa</option>
                                </select>
                            </div>
                        </div>
                        <div class="mt-4 d-flex gap-2">
                            <button type="button" class="btn btn-primary" onclick="updateTeacherAccount()">Lưu thay đổi</button>
                            <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Đóng</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
let currentTeacherAccountID = null;
let teacherAccountModalInstance = null;

function getTeacherAccountModalInstance() {
    const modalEl = document.getElementById('teacherAccountModal');
    if (!teacherAccountModalInstance) {
        teacherAccountModalInstance = bootstrap.Modal.getOrCreateInstance(modalEl);
    }
    return teacherAccountModalInstance;
}

function setTeacherAccountState(state) {
    document.getElementById('tAccountLoading').classList.add('d-none');
    document.getElementById('tAccountEmptyState').classList.add('d-none');
    document.getElementById('tAccountDetailState').classList.add('d-none');
    if (state === 'loading') document.getElementById('tAccountLoading').classList.remove('d-none');
    if (state === 'empty') document.getElementById('tAccountEmptyState').classList.remove('d-none');
    if (state === 'detail') document.getElementById('tAccountDetailState').classList.remove('d-none');
}

async function openTeacherAccountModal(teacherID) {
    currentTeacherAccountID = teacherID;
    setTeacherAccountState('loading');
    getTeacherAccountModalInstance().show();
    try {
        const resp = await fetch('<%= request.getContextPath() %>/admin/teacher-account?teacherID=' + encodeURIComponent(teacherID), { headers: { 'Accept': 'application/json' }});
        if (!resp.ok) throw new Error('HTTP ' + resp.status);
        const data = await resp.json();
        if (data && data.exists) {
            document.getElementById('tAccountUserId').value = data.userId || '';
            document.getElementById('tAccountTeacherID').value = data.teacherID || teacherID;
            document.getElementById('tAccountUsername').value = data.username || '';
            document.getElementById('tAccountFullName').value = data.fullName || '';
            document.getElementById('tAccountPassword').value = '';
            document.getElementById('tAccountStatus').value = data.isActive ? '1' : '0';
            setTeacherAccountState('detail');
        } else {
            setTeacherAccountState('empty');
        }
    } catch (e) {
        console.error(e);
        setTeacherAccountState('empty');
    }
}

async function generateTeacherAccount(teacherID) {
    try {
    const body = new URLSearchParams();
    body.append('action','generate');
    body.append('teacherID', teacherID);
    const resp = await fetch('<%= request.getContextPath() %>/admin/teacher-account', { method: 'POST', headers: { 'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8' }, body: body.toString() });
        const raw = await resp.text();
        let data = null;
        try { data = raw ? JSON.parse(raw) : null; } catch(err) { throw new Error('Phản hồi không phải JSON: ' + raw); }
        if (!resp.ok || !data || !data.success) { alert((data && data.message) ? data.message : 'Không cấp được tài khoản'); return; }
        document.getElementById('tAccountUserId').value = data.userId || '';
        document.getElementById('tAccountTeacherID').value = data.teacherID || teacherID;
        document.getElementById('tAccountUsername').value = data.teacherID || teacherID;
        document.getElementById('tAccountFullName').value = data.fullName || '';
        document.getElementById('tAccountPassword').value = '';
        document.getElementById('tAccountStatus').value = data.isActive ? '1' : '0';
        alert(data.message + (data.rawPassword ? '\nMật khẩu tạm: ' + data.rawPassword : ''));
        setTeacherAccountState('detail');
    } catch (e) {
        console.error(e);
        alert('Có lỗi khi cấp tài khoản: ' + e.message);
    }
}

async function updateTeacherAccount() {
    try {
        const body = new URLSearchParams();
        body.append('action','update');
        body.append('userId', document.getElementById('tAccountUserId').value);
        body.append('teacherID', document.getElementById('tAccountTeacherID').value);
        body.append('password', document.getElementById('tAccountPassword').value);
        body.append('isActive', document.getElementById('tAccountStatus').value);
        const resp = await fetch('<%= request.getContextPath() %>/admin/teacher-account', { method: 'POST', headers: { 'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8' }, body: body.toString() });
        const raw = await resp.text();
        let data = null;
        try { data = raw ? JSON.parse(raw) : null; } catch(err) { throw new Error('Phản hồi không phải JSON: ' + raw); }
        if (!resp.ok || !data || !data.success) { alert((data && data.message) ? data.message : 'Không lưu được thay đổi'); return; }
        alert(data.message || 'Cập nhật tài khoản thành công');
        openTeacherAccountModal(document.getElementById('tAccountTeacherID').value);
    } catch (e) {
        console.error(e);
        alert('Có lỗi khi cập nhật tài khoản: ' + e.message);
    }
}
</script>

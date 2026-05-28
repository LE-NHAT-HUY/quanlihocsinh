<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
  <div class="pagetitle">
    <h2>Danh sách Học sinh</h2>
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
    <div class="card recent-sales overflow-auto">
      <div class="card-body mt-4">
        <table class="table table-borderless datatable">
          <thead>
            <tr>
              <th class="text-center">STT</th>
              <th class="text-center">ID</th>
              <th class="text-center">Mã HS</th>
              <th class="text-center">Họ tên</th>
              <th class="text-center">Ngày sinh</th>
              <th class="text-center">Giới tính</th>
              <th class="text-center">Địa chỉ</th>
              <th class="text-center">Hoạt động</th>
              <th class="text-center">Chức năng</th>
            </tr>
          </thead>
          <tbody>
            <c:set var="stt" value="0"/>
            <c:forEach var="s" items="${students}">
              <c:set var="stt" value="${stt + 1}" />
              <tr>
                <td class="text-center">${stt}</td>
                <td class="text-center">${s.id}</td>
                <td class="text-center">${s.studentID}</td>
                <td class="text-center">${s.fullName}</td>
                <td class="text-center">
                  <c:if test="${not empty s.birth}">
                    <fmt:formatDate value="${s.birth}" pattern="yyyy-MM-dd"/>
                  </c:if>
                </td>
                <td class="text-center">${s.gender}</td>
                <td class="text-center">${s.address}</td>
                <td class="text-center">
                  <form action="${pageContext.request.contextPath}/admin/student" method="post">
                    <input type="hidden" name="action" value="toggleStatus"/>
                    <input type="hidden" name="id" value="${s.id}" />
                    <input type="checkbox" name="isActive" ${s.isActive ? "checked" : ""} onchange="this.form.submit()" />
                  </form>
                </td>
                <td class="text-center">
                  <a href="${pageContext.request.contextPath}/admin/student?action=edit&id=${s.id}" class="btn btn-primary btn-sm">
                    <i class="bi bi-pencil"></i>
                  </a>
                  <a href="#" class="btn btn-warning btn-sm text-white"
                     onclick="openAccountModal('${s.studentID}'); return false;">
                    <i class="bi bi-key-fill"></i>
                  </a>
                  <a href="${pageContext.request.contextPath}/admin/student?action=delete&id=${s.id}" class="btn btn-danger btn-sm"
                     onclick="return confirm('Bạn có chắc muốn xóa học sinh này không?');">
                    <i class="bi bi-trash"></i>
                  </a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
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

<div class="modal fade" id="accountModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Quản lý tài khoản học sinh</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <div id="accountLoading" class="text-muted">Đang tải thông tin tài khoản...</div>

        <div id="accountEmptyState" class="d-none">
          <div class="alert alert-warning mb-3">Học sinh này chưa có tài khoản.</div>
          <button type="button" class="btn btn-success" onclick="generateAccount(currentAccountStudentID)">
            Cấp tài khoản ngay
          </button>
        </div>

        <div id="accountDetailState" class="d-none">
          <form id="accountForm" onsubmit="return false;">
            <input type="hidden" id="accountUserId" value="">
            <input type="hidden" id="accountStudentID" value="">

            <div class="row g-3">
              <div class="col-md-6">
                <label class="form-label">Username</label>
                <input type="text" id="accountUsername" class="form-control" readonly>
              </div>
              <div class="col-md-6">
                <label class="form-label">Họ tên</label>
                <input type="text" id="accountFullName" class="form-control" readonly>
              </div>
              <div class="col-md-6">
                <label class="form-label">Password mới</label>
                <input type="password" id="accountPassword" class="form-control"
                       placeholder="Để trống nếu không đổi mật khẩu">
              </div>
              <div class="col-md-6">
                <label class="form-label">Trạng thái</label>
                <select id="accountStatus" class="form-select">
                  <option value="1">Kích hoạt</option>
                  <option value="0">Khóa</option>
                </select>
              </div>
            </div>

            <div class="mt-4 d-flex gap-2">
              <button type="button" class="btn btn-primary" onclick="updateAccount()">Lưu thay đổi</button>
              <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Đóng</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<script>
let currentAccountStudentID = null;
let accountModalInstance = null;

function getAccountModalInstance() {
  const modalEl = document.getElementById('accountModal');
  if (!accountModalInstance) {
    accountModalInstance = bootstrap.Modal.getOrCreateInstance(modalEl);
  }
  return accountModalInstance;
}

function setAccountState(state) {
  document.getElementById('accountLoading').classList.add('d-none');
  document.getElementById('accountEmptyState').classList.add('d-none');
  document.getElementById('accountDetailState').classList.add('d-none');

  if (state === 'loading') {
    document.getElementById('accountLoading').classList.remove('d-none');
  } else if (state === 'empty') {
    document.getElementById('accountEmptyState').classList.remove('d-none');
  } else if (state === 'detail') {
    document.getElementById('accountDetailState').classList.remove('d-none');
  }
}

async function openAccountModal(studentID) {
  currentAccountStudentID = studentID;
  setAccountState('loading');
  getAccountModalInstance().show();

  try {
    const response = await fetch('${pageContext.request.contextPath}/admin/student-account?studentID=' + encodeURIComponent(studentID), {
      method: 'GET',
      headers: { 'Accept': 'application/json' }
    });

    if (!response.ok) {
      throw new Error('HTTP ' + response.status);
    }

    const data = await response.json();

    if (data && data.exists) {
      document.getElementById('accountUserId').value = data.userId || '';
      document.getElementById('accountStudentID').value = data.studentID || studentID;
      document.getElementById('accountUsername').value = data.username || '';
      document.getElementById('accountFullName').value = data.fullName || '';
      document.getElementById('accountPassword').value = '';
      document.getElementById('accountStatus').value = data.isActive ? '1' : '0';
      setAccountState('detail');
    } else {
      setAccountState('empty');
    }
  } catch (error) {
    console.error(error);
    setAccountState('empty');
  }
}

async function generateAccount(studentID) {
  try {
    const body = new URLSearchParams();
    body.append('action', 'generate');
    body.append('studentID', studentID);

    const response = await fetch('${pageContext.request.contextPath}/admin/student-account', {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
      body: body.toString()
    });

    const rawText = await response.text();
    let data = null;
    try {
      data = rawText ? JSON.parse(rawText) : null;
    } catch (parseError) {
      console.error('Student account generate returned non-JSON:', rawText);
      throw new Error('Phản hồi từ server không phải JSON hợp lệ');
    }

    if (!response.ok || !data || !data.success) {
      alert((data && data.message) ? data.message : 'Không cấp được tài khoản');
      return;
    }

    document.getElementById('accountUserId').value = data.userId || '';
    document.getElementById('accountStudentID').value = data.studentID || studentID;
    document.getElementById('accountUsername').value = data.studentID || studentID;
    document.getElementById('accountFullName').value = data.fullName || '';
    document.getElementById('accountPassword').value = '';
    document.getElementById('accountStatus').value = data.isActive ? '1' : '0';

    alert(data.message + (data.rawPassword ? '\nMật khẩu tạm: ' + data.rawPassword : ''));
    setAccountState('detail');
  } catch (error) {
    console.error(error);
    alert('Có lỗi khi cấp tài khoản: ' + error.message);
  }
}

async function updateAccount() {
  try {
    const body = new URLSearchParams();
    body.append('action', 'update');
    body.append('userId', document.getElementById('accountUserId').value);
    body.append('studentID', document.getElementById('accountStudentID').value);
    body.append('password', document.getElementById('accountPassword').value);
    body.append('isActive', document.getElementById('accountStatus').value);

    const response = await fetch('${pageContext.request.contextPath}/admin/student-account', {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
      body: body.toString()
    });

    const data = await response.json();
    if (!response.ok || !data.success) {
      alert(data.message || 'Không lưu được thay đổi');
      return;
    }

    alert(data.message || 'Cập nhật tài khoản thành công');
    await openAccountModal(currentAccountStudentID);
  } catch (error) {
    console.error(error);
    alert('Có lỗi khi cập nhật tài khoản');
  }
}
</script>

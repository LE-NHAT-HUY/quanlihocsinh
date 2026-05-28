<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">

    <!-- Tiêu đề -->
    <div class="pagetitle">
        <h2>Thêm học sinh vào lớp</h2>
        <a href="${pageContext.request.contextPath}/admin/studentclass/list?classID=${classID}&yearSemesterID=${yearSemesterID}" 
           class="btn btn-secondary">
            Quay lại danh sách
        </a>
    </div>

    <!-- Nội dung -->
    <section class="section dashboard mt-3">

        <c:if test="${not empty sessionScope.flashSuccess}">
            <div class="alert alert-success">${sessionScope.flashSuccess}</div>
            <c:remove var="flashSuccess" scope="session" />
        </c:if>
        <c:if test="${not empty sessionScope.flashError}">
            <div class="alert alert-danger">${sessionScope.flashError}</div>
            <c:remove var="flashError" scope="session" />
        </c:if>

        <!-- Thông tin lớp -->
        <c:if test="${not empty currentClass}">
            <div class="alert alert-info">
                <strong>Lớp:</strong> ${currentClass.gradeID}${currentClass.className} |
                <strong>Sĩ số:</strong> ${currentClass.currentStudents}/${currentClass.maxStudents} |
                <strong>Năm học:</strong> ${currentClass.schoolYear}
            </div>
        </c:if>

        <!-- Form -->
        <form action="${pageContext.request.contextPath}/admin/studentclass/add" method="post">
            <input type="hidden" name="classID" value="${not empty classID ? classID : selectedClassID}">
            <input type="hidden" name="yearSemesterID" value="${yearSemesterID}">

            <div class="mb-3">
                <label class="form-label">Học sinh</label>
                <input type="text" id="searchInput" class="form-control mb-3"
                       placeholder="Nhập mã học sinh (MHS) hoặc tên để tìm nhanh..." />

                <div class="table-responsive" style="max-height: 400px; overflow-y: auto;">
                    <table class="table table-bordered table-hover align-middle mb-0">
                        <thead class="table-light sticky-top">
                            <tr>
                                <th class="text-center" style="width: 90px;">Chọn</th>
                                <th>Mã Học Sinh</th>
                                <th>Họ và Tên</th>
                            </tr>
                        </thead>
                        <tbody id="studentTableBody">
                            <c:forEach var="s" items="${students}">
                                <tr class="student-row" data-search="${s.studentID} ${s.fullName}">
                                    <td class="text-center">
                                        <input type="checkbox" name="studentID" value="${s.studentID}">
                                    </td>
                                    <td>${s.studentID}</td>
                                    <td>${s.fullName}</td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty students}">
                                <tr>
                                    <td colspan="3" class="text-center text-muted py-4">
                                        Không còn học sinh nào có thể thêm
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Nút -->
            <div class="mt-4">
                <button type="submit" class="btn btn-success"
                        <c:if test="${empty students}">disabled</c:if>>
                    Thêm mới
                </button>
                <a href="${pageContext.request.contextPath}/admin/studentclass/list?classID=${classID}&yearSemesterID=${yearSemesterID}" 
                   class="btn btn-secondary ms-2">
                    Hủy
                </a>
            </div>

        </form>
    </section>

</main>

<script>
document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('searchInput');
    const rows = document.querySelectorAll('#studentTableBody .student-row');
    const tableBody = document.getElementById('studentTableBody');

    if (!searchInput || rows.length === 0) return;

    function filterRows() {
        const keyword = searchInput.value.trim().toLowerCase();

        rows.forEach(function (row) {
            const checkbox = row.querySelector('input[type="checkbox"][name="studentID"]');
            const searchText = (row.dataset.search || '').toLowerCase();
            const isChecked = checkbox ? checkbox.checked : false;
            const matches = searchText.includes(keyword);

            row.style.display = matches || isChecked ? '' : 'none';
        });
    }

    if (tableBody) {
        tableBody.addEventListener('change', function (e) {
            const checkbox = e.target.closest('input[type="checkbox"][name="studentID"]');
            if (!checkbox) return;

            const row = checkbox.closest('tr');
            if (row) {
                row.classList.toggle('table-warning', checkbox.checked);
            }

            filterRows();
        });
    }

    searchInput.addEventListener('keyup', filterRows);

    filterRows();
});
</script>

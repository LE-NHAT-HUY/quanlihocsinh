<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.quanlihocsinh.model.Cohort" %>
<jsp:include page="/WEB-INF/views/shared/_LayoutAdmin.jspf" />

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Thêm khóa</h2>
    </div>
    <div class="container shadow p-5">
        <form action="<%= request.getContextPath() %>/admin/cohort/add" method="post">
            <div class="row">

                <div class="form-group col-md-6 mb-3">
                    <label>Năm vào</label>
                    <input type="number" class="form-control" name="startYear" id="startYear" placeholder="Năm vào..." required>
                </div>

                <div class="form-group col-md-6 mb-3">
                    <label>Năm ra</label>
                    <input type="number" class="form-control" name="endYear" id="endYear" placeholder="Năm ra..." readonly>
                </div>

                <div class="form-group col-md-6 mb-3">
                    <label>Niên khóa</label>
                    <input type="text" class="form-control" name="cohortName" id="cohortName" placeholder="Niên khóa..." readonly>
                </div>

            </div>

            <div class="form-check mt-3">
                <input type="checkbox" class="form-check-input" name="isActive" id="IsActive" checked>
                <label class="form-check-label" for="IsActive">Hiển thị</label>
            </div>

            <a href="<%= request.getContextPath() %>/admin/cohort/list" class="btn btn-lg btn-warning p-2">
                <i class="bi bi-arrow-left-circle"></i> Quay lại
            </a>

            <button type="submit" class="btn btn-lg btn-primary p-2">
                <i class="bi bi-file-plus-fill"></i> Lưu thông tin
            </button>
        </form>
    </div>
</main>

<script>
    const startYearInput = document.getElementById("startYear");
    const endYearInput = document.getElementById("endYear");
    const cohortNameInput = document.getElementById("cohortName");

    function calculateCohortAndSuggestEnd() {
        const start = parseInt(startYearInput.value);

        if (!isNaN(start)) {
            // Gợi ý năm ra = năm vào + 4
            endYearInput.value = start + 4;

            // Tính niên khóa
            cohortNameInput.value = start - 1959;
        } else {
            endYearInput.value = "";
            cohortNameInput.value = "";
        }
    }

    startYearInput.addEventListener("input", calculateCohortAndSuggestEnd);

    // Nếu người dùng tự sửa năm ra, vẫn cập nhật niên khóa
    endYearInput.addEventListener("input", function() {
        const start = parseInt(startYearInput.value);
        const end = parseInt(endYearInput.value);

        if (!isNaN(start) && !isNaN(end) && end >= start) {
            cohortNameInput.value = end - start + 1;
        } else {
            cohortNameInput.value = "";
        }
    });
</script>

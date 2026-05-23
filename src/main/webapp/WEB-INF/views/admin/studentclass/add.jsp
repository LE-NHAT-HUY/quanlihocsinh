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

            <!-- Chọn học sinh -->
            <div class="mb-3">
                <label class="form-label">Học sinh</label>
                <select name="studentID" class="form-control" required>
                    <option value="">-- Chọn học sinh --</option>
                    <c:forEach var="s" items="${students}">
                        <option value="${s.studentID}">
                            ${s.studentID} - ${s.fullName}
                        </option>
                    </c:forEach>
                </select>

                <c:if test="${empty students}">
                    <small class="text-danger">
                        Không còn học sinh nào có thể thêm
                    </small>
                </c:if>
            </div>

            <!-- Chọn khóa -->
            <div class="mb-3">
                <label class="form-label">Khóa học</label>
                <select name="cohortID" class="form-control">
                    <option value="">-- Chọn khóa --</option>
                    <c:forEach var="co" items="${cohorts}">
                        <option value="${co.cohortID}"
                            <c:if test="${currentClass.cohortID == co.cohortID}">selected</c:if>>
                            Khóa ${co.cohortName} (${co.startYear} - ${co.endYear})
                        </option>
                    </c:forEach>
                </select>
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

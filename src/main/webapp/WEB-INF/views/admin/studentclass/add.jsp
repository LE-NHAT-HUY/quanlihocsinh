<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Thêm học sinh vào lớp</h2>
        <a href="${pageContext.request.contextPath}/admin/student-class/list?classID=${classID}&yearSemesterID=${yearSemesterID}" 
           class="btn btn-secondary">Quay lại</a>
    </div>

    <section class="section mt-3">
        <form action="${pageContext.request.contextPath}/admin/student-class" method="post" class="row g-3">
            <input type="hidden" name="classID" value="${classID}" />
            <input type="hidden" name="yearSemesterID" value="${yearSemesterID}" />

            <!-- Học sinh -->
            <div class="col-md-6">
                <label class="form-label">Học sinh</label>
                <select name="studentID" class="form-select" required>
                    <option value="">-- Chọn học sinh --</option>
                    <c:forEach var="s" items="${students}">
                        <option value="${s.studentID}">${s.fullName} - ${s.studentID}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Lớp -->
            <div class="col-md-6">
                <label class="form-label">Lớp</label>
                <select name="classID" class="form-select" required>
                    <c:forEach var="c" items="${classes}">
                        <option value="${c.classID}" <c:if test="${c.classID == classID}">selected</c:if>>
                            ${c.className} - ${c.classID}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <!-- Cohort -->
            <div class="col-md-6">
                <label class="form-label">Khoá (CohortID, tùy chọn)</label>
                <input type="number" name="cohortID" class="form-control"/>
            </div>

            <!-- Active -->
            <div class="col-md-6 form-check mt-4">
                <input type="checkbox" class="form-check-input" name="isActive" checked />
                <label class="form-check-label">Hoạt động</label>
            </div>

            <div class="col-12">
                <button type="submit" class="btn btn-primary">Thêm học sinh</button>
            </div>
        </form>
    </section>
</main>

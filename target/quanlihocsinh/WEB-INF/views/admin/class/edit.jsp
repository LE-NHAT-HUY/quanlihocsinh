<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>
<%@ page import="com.quanlihocsinh.model.tblClass" %>
<main id="main" class="main">
    <div class="pagetitle">
        <h2>Chỉnh sửa lớp học</h2>
    </div>

    <section class="section">
        <form action="${pageContext.request.contextPath}/admin/class/edit" method="post">
            <!-- ID lớp học -->
            <input type="hidden" name="classID" value="${classObj.classID}" />

            <!-- Tên lớp -->
            <div class="mb-3">
                <label for="className" class="form-label">Tên lớp</label>
                <input type="text" class="form-control" id="className" name="className" value="${classObj.className}" required />
            </div>

                        <div class="mb-3">
                    <label for="gradeID">Khối</label>
                    <select id="gradeID" name="gradeID" class="form-control">
                        <c:forEach var="g" items="${grades}">
                            <option value="${g.gradeID}">${g.gradeName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="cohortID">Khóa học</label>
                    <select id="cohortID" name="cohortID" class="form-control">
                        <c:forEach var="c" items="${cohorts}">
                            <option value="${c.cohortID}">${c.cohortName}</option>
                        </c:forEach>
                    </select>
                </div>


            <!-- Sĩ số tối đa -->
            <div class="mb-3">
                <label for="maxStudents" class="form-label">Sĩ số tối đa</label>
                <input type="number" class="form-control" id="maxStudents" name="maxStudents" value="${classObj.maxStudents}" required />
            </div>

            <!-- Sĩ số hiện tại (readonly) -->
            <div class="mb-3">
                <label for="currentStudents" class="form-label">Sĩ số hiện tại</label>
                <input type="number" class="form-control" id="currentStudents" name="currentStudents" value="${classObj.currentStudents}" readonly />
            </div>

            <!-- Hiển thị -->
            <div class="mb-3 form-check">
                <input type="checkbox" class="form-check-input" id="isActive" name="isActive" ${classObj.isActive ? 'checked' : ''} />
                <label class="form-check-label" for="isActive">Hiển thị</label>
            </div>

            <!-- Nút Lưu và Hủy -->
            <button type="submit" class="btn btn-primary">Cập nhật</button>
            <a href="${pageContext.request.contextPath}/admin/class/list" class="btn btn-secondary">Hủy</a>
        </form>
    </section>
</main>

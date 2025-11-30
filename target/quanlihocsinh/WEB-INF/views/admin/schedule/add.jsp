<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Thêm Thời khóa biểu mới</h2>
        <a href="${pageContext.request.contextPath}/admin/schedule" class="btn btn-secondary mb-2">Quay lại</a>
    </div>

    <section class="section">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/admin/schedule" method="post">
                            <input type="hidden" name="action" value="add" />

                            <div class="mb-3">
                                <label>SemesterID</label>
                                <input type="number" name="semesterID" class="form-control" required />
                            </div>
                            <div class="mb-3">
                                <label>ClassID</label>
                                <input type="number" name="classID" class="form-control" required />
                            </div>
                            <div class="mb-3">
                                <label>SubjectID</label>
                                <input type="number" name="subjectID" class="form-control" required />
                            </div>
                            <div class="mb-3">
                                <label>TeacherID</label>
                                <input type="text" name="teacherID" class="form-control" required />
                            </div>
                            <div class="mb-3">
                                <label>Notes</label>
                                <textarea name="notes" class="form-control"></textarea>
                            </div>
                            <div class="mb-3 form-check">
                                <input type="checkbox" name="isActive" class="form-check-input" checked />
                                <label class="form-check-label">Kích hoạt</label>
                            </div>
                            <div class="mb-3">
                                <label>StartDate</label>
                                <input type="date" name="startDate" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>EndDate</label>
                                <input type="date" name="endDate" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Period</label>
                                <input type="number" name="period" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>DayOfWeek</label>
                                <input type="number" name="dayOfWeek" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>HomeroomTeacher</label>
                                <input type="text" name="homeroomTeacher" class="form-control" maxlength="1" />
                            </div>
                            <div class="mb-3">
                                <label>WeekNumber</label>
                                <input type="number" name="weekNumber" class="form-control" />
                            </div>

                            <button type="submit" class="btn btn-success">Thêm mới</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

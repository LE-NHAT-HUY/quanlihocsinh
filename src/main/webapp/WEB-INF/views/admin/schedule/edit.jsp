<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Chỉnh sửa Thời khóa biểu</h2>
        <a href="${pageContext.request.contextPath}/admin/schedule" class="btn btn-secondary mb-2">Quay lại</a>
    </div>

    <section class="section">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/admin/schedule" method="post">
                            <input type="hidden" name="action" value="edit" />
                            <input type="hidden" name="id" value="${schedule.scheduleID}" />

                            <div class="mb-3">
                                <label>Học kỳ</label>
                                <input type="number" name="semesterID" value="${schedule.semesterID}" class="form-control" required />
                            </div>
                            <div class="mb-3">
                                <label>Lớp</label>
                                <input type="number" name="classID" value="${schedule.classID}" class="form-control" required />
                            </div>
                            <div class="mb-3">
                                <label>Môn học</label>
                                <input type="number" name="subjectID" value="${schedule.subjectID}" class="form-control" required />
                            </div>
                            <div class="mb-3">
                                <label>Giáo viên</label>
                                <input type="text" name="teacherID" value="${schedule.teacherID}" class="form-control" required />
                            </div>
                            <div class="mb-3">
                                <label>Ghi chú</label>
                                <textarea name="notes" class="form-control">${schedule.notes}</textarea>
                            </div>
                            <div class="mb-3 form-check">
                                <input type="checkbox" name="isActive" class="form-check-input" ${schedule.isActive ? 'checked' : ''} />
                                <label class="form-check-label">Kích hoạt</label>
                            </div>
                            <div class="mb-3">
                                <label>Ngày bắt đầu</label>
                                <input type="date" name="startDate" value="${schedule.startDate}" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Ngày kết thúc</label>
                                <input type="date" name="endDate" value="${schedule.endDate}" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Tiết học</label>
                                <input type="number" name="period" value="${schedule.period}" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Thứ trong tuần</label>
                                <input type="number" name="dayOfWeek" value="${schedule.dayOfWeek}" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Giáo viên chủ nhiệm</label>
                                <input type="text" name="homeroomTeacher" value="${schedule.homeroomTeacher}" class="form-control" maxlength="1" />
                            </div>
                            <div class="mb-3">
                                <label>Tuần</label>
                                <input type="number" name="weekNumber" value="${schedule.weekNumber}" class="form-control" />
                            </div>


                            <button type="submit" class="btn btn-primary">Cập nhật</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

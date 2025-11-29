<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Thêm Giáo viên mới</h2>
        <a href="${pageContext.request.contextPath}/admin/teacher" class="btn btn-secondary mb-2">Quay lại</a>
    </div>

    <section class="section">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/admin/teacher" method="post">
                            <input type="hidden" name="action" value="add" />

                            <div class="mb-3">
                                <label>Mã GV</label>
                                <input type="text" name="teacherID" class="form-control" required />
                            </div>
                            <div class="mb-3">
                                <label>Họ tên</label>
                                <input type="text" name="fullName" class="form-control" required />
                            </div>
                            <div class="mb-3">
                                <label>Ngày sinh</label>
                                <input type="date" name="birth" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Giới tính</label>
                                <select name="gender" class="form-select">
                                    <option value="Nam">Nam</option>
                                    <option value="Nữ">Nữ</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label>Địa chỉ</label>
                                <input type="text" name="address" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Trạng thái</label>
                                <input type="text" name="statusTeacher" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>CCCD</label>
                                <input type="text" name="cccd" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Dân tộc</label>
                                <input type="text" name="nation" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Tôn giáo</label>
                                <input type="text" name="religion" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Nhóm DV</label>
                                <input type="text" name="groupDV" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Điện thoại</label>
                                <input type="text" name="numberPhone" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Số BHXH</label>
                                <input type="text" name="numberBHXH" class="form-control" />
                            </div>
                            <div class="mb-3 form-check">
                                <input type="checkbox" name="isActive" class="form-check-input" checked />
                                <label class="form-check-label">Kích hoạt</label>
                            </div>
                            <div class="mb-3">
                                <label>DepartmentID</label>
                                <input type="number" name="departmentID" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Hamlet</label>
                                <input type="number" name="hamlet" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Commune</label>
                                <input type="text" name="commune" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Province</label>
                                <input type="text" name="province" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Nationality</label>
                                <input type="text" name="nationality" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Images</label>
                                <input type="text" name="images" class="form-control" />
                            </div>

                            <button type="submit" class="btn btn-success">Thêm mới</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Chỉnh sửa Giáo viên</h2>
        <a href="${pageContext.request.contextPath}/admin/teacher" class="btn btn-secondary mb-2">Quay lại</a>
    </div>

    <section class="section">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/admin/teacher" method="post">
                            <input type="hidden" name="action" value="edit" />
                            <input type="hidden" name="id" value="${teacher.id}" />

                            <div class="mb-3">
                                <label>Mã GV</label>
                                <input type="text" name="teacherID" value="${teacher.teacherID}" class="form-control" required />
                            </div>
                            <div class="mb-3">
                                <label>Họ tên</label>
                                <input type="text" name="fullName" value="${teacher.fullName}" class="form-control" required />
                            </div>
                            <div class="mb-3">
                                <label>Ngày sinh</label>
                                <input type="date" name="birth" value="${teacher.birth}" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Giới tính</label>
                                <select name="gender" class="form-select">
                                    <option value="Nam" ${teacher.gender=='Nam' ? 'selected' : ''}>Nam</option>
                                    <option value="Nữ" ${teacher.gender=='Nữ' ? 'selected' : ''}>Nữ</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label>Địa chỉ</label>
                                <input type="text" name="address" value="${teacher.address}" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Trạng thái</label>
                                <input type="text" name="statusTeacher" value="${teacher.statusTeacher}" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>CCCD</label>
                                <input type="text" name="cccd" value="${teacher.cccd}" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Dân tộc</label>
                                <input type="text" name="nation" value="${teacher.nation}" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Tôn giáo</label>
                                <input type="text" name="religion" value="${teacher.religion}" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Nhóm DV</label>
                                <input type="text" name="groupDV" value="${teacher.groupDV}" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Điện thoại</label>
                                <input type="text" name="numberPhone" value="${teacher.numberPhone}" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Số BHXH</label>
                                <input type="text" name="numberBHXH" value="${teacher.numberBHXH}" class="form-control" />
                            </div>
                            <div class="mb-3 form-check">
                                <input type="checkbox" name="isActive" class="form-check-input" ${teacher.isActive ? 'checked' : ''} />
                                <label class="form-check-label">Kích hoạt</label>
                            </div>
                            <div class="mb-3">
                                <label>DepartmentID</label>
                                <input type="number" name="departmentID" value="${teacher.departmentID}" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Hamlet</label>
                                <input type="number" name="hamlet" value="${teacher.hamlet}" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Commune</label>
                                <input type="text" name="commune" value="${teacher.commune}" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Province</label>
                                <input type="text" name="province" value="${teacher.province}" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Nationality</label>
                                <input type="text" name="nationality" value="${teacher.nationality}" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Images</label>
                                <input type="text" name="images" value="${teacher.images}" class="form-control" />
                            </div>

                            <button type="submit" class="btn btn-primary">Cập nhật</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

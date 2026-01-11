<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">

    <div class="pagetitle">
        <h2>Gán giáo viên - môn học</h2>
    </div>

    <!-- FORM GÁN -->
    <section class="section dashboard">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body mt-4">

                        <form class="row g-3"
                              method="post"
                              action="${pageContext.request.contextPath}/admin/teachersubject">

                            <input type="hidden" name="action" value="assign"/>

                            <div class="col-md-5">
                                <label class="form-label">Giáo viên</label>
                                <select name="teacherID" class="form-select" required>
                                    <option value="">-- Chọn giáo viên --</option>
                                    <c:forEach var="t" items="${teachers}">
                                        <option value="${t.id}">
                                            ${t.fullName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="col-md-5">
                                <label class="form-label">Môn học</label>
                                <select name="subjectID" class="form-select" required>
                                    <option value="">-- Chọn môn học --</option>
                                    <c:forEach var="s" items="${subjects}">
                                        <option value="${s.subjectID}">
                                            ${s.subjectName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="col-md-2 d-flex align-items-end">
                                <button type="submit" class="btn btn-success w-100">
                                    <i class="bi bi-plus-circle"></i> Gán
                                </button>
                            </div>

                        </form>

                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- DANH SÁCH ĐÃ GÁN -->
    <section class="section dashboard">
        <div class="row">
            <div class="col-12">
                <div class="card recent-sales overflow-auto">
                    <div class="card-body mt-4">

                        <table class="table table-borderless datatable">
                            <thead>
                                <tr>
                                    <th class="text-center">STT</th>
                                    <th class="text-center">Giáo viên</th>
                                    <th class="text-center">Môn học</th>
                                    <th class="text-center">Chức năng</th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:set var="stt" value="0"/>

                                <c:forEach var="m" items="${mappings}">
                                    <c:set var="stt" value="${stt + 1}"/>

                                    <tr>
                                        <td class="text-center">${stt}</td>
                                        <td>${m.teacherName}</td>
                                        <td>${m.subjectName}</td>
                                        <td class="text-center">
                                            <form method="post"
                                                  action="${pageContext.request.contextPath}/admin/teachersubject/list"
                                                  style="display:inline">

                                                <input type="hidden" name="action" value="unassign"/>
                                                <input type="hidden" name="teacherID" value="${m.teacherID}"/>
                                                <input type="hidden" name="subjectID" value="${m.subjectID}"/>

                                                <button class="btn btn-danger btn-sm"
                                                        onclick="return confirm('Hủy gán môn học này?');">
                                                    <i class="bi bi-trash"></i>
                                                </button>
                                            </form>
                                        </td>
                                    </tr>

                                </c:forEach>
                            </tbody>

                        </table>

                    </div>
                </div>
            </div>
        </div>
    </section>

</main>

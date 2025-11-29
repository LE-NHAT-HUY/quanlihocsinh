<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.quanlihocsinh.model.tblClass" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Danh sách lớp học</h2>
        <a href="${pageContext.request.contextPath}/admin/class/add" class="btn btn-success mb-2">
            <i class="bi bi-plus-circle"></i> Thêm lớp mới
        </a>
    </div>

    <section class="section dashboard">
        <div class="row">
            <div class="col-12">
                <div class="card recent-sales overflow-auto">
                    <div class="card-body mt-4">
                        <table class="table table-borderless datatable">
                            <thead>
                                <tr>
                                    <th class="text-center">STT</th>
                                    <th class="text-center">Mã lớp</th>
                                    <th class="text-center">Tên lớp</th>
                                    <th class="text-center">Sĩ số tối đa</th>
                                    <th class="text-center">Sĩ số hiện tại</th>
                                    <th class="text-center">Niên khóa</th>
                                    <th class="text-center">Hiển thị</th>
                                    <th class="text-center">Chức năng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="stt" value="0" />
                                <c:forEach var="cls" items="${classes}">
                                    <c:set var="stt" value="${stt + 1}" />
                                    <tr>
                                        <td class="text-center">${stt}</td>
                                        <td class="text-center">${cls.classID}</td>
                                        <td class="text-left text-primary">${cls.className}</td>
                                        <td class="text-center">${cls.maxStudents}</td>
                                        <td class="text-center">${cls.currentStudents}</td>
                                        <td class="text-center">${cls.schoolYear}</td>
                                        <td class="text-center">
                                            <form action="${pageContext.request.contextPath}/admin/class/toggleStatus" method="post">
                                                <input type="hidden" name="id" value="${cls.classID}" />
                                                <input type="checkbox" name="isActive" ${cls.isActive ? 'checked' : ''} onchange="this.form.submit()" />
                                            </form>
                                        </td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/admin/class/edit?id=${cls.classID}" class="btn btn-primary btn-sm">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/class/delete?id=${cls.classID}" class="btn btn-danger btn-sm" 
                                               onclick="return confirm('Bạn có chắc muốn xóa lớp này không?');">
                                                <i class="bi bi-trash"></i>
                                            </a>
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

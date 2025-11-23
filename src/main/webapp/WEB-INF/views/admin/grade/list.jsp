<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.quanlihocsinh.model.Grade" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Danh sách Khối</h2>
        <a href="${pageContext.request.contextPath}/admin/grade/add" class="btn btn-success mb-2">
            <i class="bi bi-plus-circle"></i> Thêm mới
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
                                        <th class="text-center">Mã Khối Lớp</th>
                                        <th class="text-center">Tên Khối Lớp</th>
                                        <th class="text-center">Mô tả</th>
                                        <th class="text-center">Hiển thị</th>
                                        <th class="text-center">Chức năng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="stt" value="0"/>
                                <c:forEach var="item" items="${grades}">
                                    <c:set var="stt" value="${stt + 1}" />
                                    <tr>
                                        <td class="text-center">${stt}</td>
                                        <td class="text-center">${item.gradeID}</td>
                                        <td class="text-center">${item.gradeName}</td>
                                        <td class="text-center">${item.description}</td>
                                        <td class="text-center">
                                            <form action="${pageContext.request.contextPath}/admin/grade/toggleStatus" method="post">
                                                <input type="hidden" name="id" value="${item.gradeID}" />
                                                <input type="checkbox" name="isActive" ${item.isActive ? 'checked' : ''} onchange="this.form.submit()" />
                                            </form>
                                        </td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/admin/grade/edit?id=${item.gradeID}" class="btn btn-primary btn-sm">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/grade/delete?id=${item.gradeID}" class="btn btn-danger btn-sm"
                                               onclick="return confirm('Bạn có chắc muốn xóa grade này không?');">
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

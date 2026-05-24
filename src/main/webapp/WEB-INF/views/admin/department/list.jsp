<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.quanlihocsinh.model.Department" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Danh sách Khoa / Tổ bộ môn</h2>
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
                                    <th class="text-center">Mã Khoa</th>
                                    <th class="text-center">Tên Khoa / Tổ bộ môn</th>
                                    <th class="text-center">Mô tả</th>
                                    <th class="text-center">Hiển thị</th>
                                    <th class="text-center">Chức năng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="stt" value="0" />
                                <c:forEach var="item" items="${departments}">
                                    <c:set var="stt" value="${stt + 1}" />
                                    <tr>
                                        <td class="text-center">${stt}</td>
                                        <td class="text-center">${item.departmentID}</td>
                                        <td class="text-center">${item.departmentName}</td>
                                        <td class="text-center">${item.description}</td>
                                        <td class="text-center">
                                            <form action="${pageContext.request.contextPath}/admin/department/toggleStatus" method="post">
                                                <input type="hidden" name="id" value="${item.departmentID}" />
                                                <input type="checkbox" name="isActive" ${item.isActive ? 'checked' : ''} onchange="this.form.submit()" />
                                            </form>
                                        </td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/admin/department/edit?id=${item.departmentID}" class="btn btn-primary btn-sm">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/department/delete?id=${item.departmentID}" class="btn btn-danger btn-sm"
                                               onclick="return confirm('Bạn có chắc muốn xóa khoa/tổ bộ môn này không?');">
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
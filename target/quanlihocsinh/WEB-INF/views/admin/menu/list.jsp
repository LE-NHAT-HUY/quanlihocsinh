<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.quanlihocsinh.model.menu" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Danh sách Menu</h2>
        <a href="${pageContext.request.contextPath}/admin/menu/add" class="btn btn-success mb-2">
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
                                    <th class="text-center">ID</th>
                                    <th class="text-center">Tên Menu</th>
                                    <th class="text-center">Controller</th>
                                    <th class="text-center">Action</th>
                                    <th class="text-center">Level</th>
                                    <th class="text-center">ParentID</th>
                                    <th class="text-center">Thứ tự</th>
                                    <th class="text-center">Vị trí</th>
                                    <th class="text-center">Hiển thị</th>
                                    <th class="text-center">Chức năng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="stt" value="0"/>
                                <c:forEach var="item" items="${menus}">
                                    <c:set var="stt" value="${stt + 1}" />
                                    <tr>
                                        <td class="text-center">${stt}</td>
                                        <td class="text-center">${item.menuID}</td>
                                        <td class="text-center">${item.menuName}</td>
                                        <td class="text-center">${item.controllerName}</td>
                                        <td class="text-center">${item.actionName}</td>
                                        <td class="text-center">${item.levels}</td>
                                        <td class="text-center">${item.parentID}</td>
                                        <td class="text-center">${item.menuOrder}</td>
                                        <td class="text-center">${item.position}</td>

                                        <td class="text-center">
                                            <form action="${pageContext.request.contextPath}/admin/menu/toggleStatus" method="post">
                                                <input type="hidden" name="id" value="${item.menuID}" />
                                                <input type="checkbox" name="isActive" ${item.isActive ? 'checked' : ''} onchange="this.form.submit()" />
                                            </form>
                                        </td>

                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/admin/menu/edit?id=${item.menuID}"
                                               class="btn btn-primary btn-sm">
                                                <i class="bi bi-pencil"></i>
                                            </a>

                                            <a href="${pageContext.request.contextPath}/admin/menu/delete?id=${item.menuID}"
                                               class="btn btn-danger btn-sm"
                                               onclick="return confirm('Bạn có chắc muốn xóa menu này không?');">
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

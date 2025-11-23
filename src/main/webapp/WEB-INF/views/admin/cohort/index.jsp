<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.quanlihocsinh.model.Cohort" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Danh sách chức năng</h2>
    </div>

    <section class="section dashboard">
        <div class="row">
            <div class="col-12">
                <div class="card recent-sales overflow-auto">
                    <div class="card-body mt-4">
                        <table class="table table-borderless datatable">
                            <thead>
                                <tr>
                                    <th class="col-1 text-center">STT</th>
                                    <th class="col-1 text-center">Mã khóa</th>
                                    <th class="col-2 text-center">Năm vào</th>
                                    <th class="col-2 text-center">Năm ra</th>
                                    <th class="col-1 text-center">Niên khóa</th>
                                    <th class="col-1 text-center">Hiển thị</th>
                                    <th class="col-1 text-center">Chức năng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="stt" value="0"/>
                                <c:forEach var="item" items="${cohorts}">
                                    <c:set var="stt" value="${stt + 1}" />
                                    <tr>
                                        <td class="text-center">${stt}</td>
                                        <td class="text-left text-primary text-center">
                                            <a href="${pageContext.request.contextPath}/admin/cohort/details?id=${item.cohortID}">
                                                ${item.cohortID}
                                            </a>
                                        </td>
                                        <td class="text-center">${item.startYear}</td>
                                        <td class="text-center">${item.endYear}</td>
                                        <td class="text-center">${item.cohortName}</td>

                                        <td class="text-center">
                                            <form action="${pageContext.request.contextPath}/admin/cohort/toggleStatus" method="post">
                                                <input type="hidden" name="id" value="${item.cohortID}" />
                                                <div class="form-check form-switch d-flex justify-content-center">
                                                    <input class="form-check-input toggle-status-switch" type="checkbox"
                                                           name="isActive" ${item.isActive ? 'checked' : ''} onchange="this.form.submit()" />
                                                </div>
                                            </form>
                                        </td>

                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/admin/cohort/edit?id=${item.cohortID}" class="btn btn-primary btn-sm" title="Edit the Cohort">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/cohort/delete?id=${item.cohortID}" class="btn btn-danger btn-sm" title="Delete the Cohort"
                                               onclick="return confirm('Bạn có chắc muốn xóa niên khóa này không?');">
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

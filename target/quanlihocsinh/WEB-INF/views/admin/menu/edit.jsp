<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <h2>Chỉnh sửa Menu</h2>
    <form action="${pageContext.request.contextPath}/admin/menu" method="post">
        <input type="hidden" name="action" value="edit" />
        <input type="hidden" name="menuID" value="${menu.menuID}" />

        <div class="mb-2">
            <label>Tên Menu:</label>
            <input type="text" name="menuName" class="form-control" value="${menu.menuName}" required />
        </div>
        <div class="mb-2">
            <label>Controller:</label>
            <input type="text" name="controllerName" class="form-control" value="${menu.controllerName}" />
        </div>
        <div class="mb-2">
            <label>Action:</label>
            <input type="text" name="actionName" class="form-control" value="${menu.actionName}" />
        </div>
        <div class="mb-2">
            <label>Level:</label>
            <input type="number" name="levels" class="form-control" value="${menu.levels}" />
        </div>
        <div class="mb-2">
            <label>ParentID:</label>
            <input type="number" name="parentID" class="form-control" value="${menu.parentID}" />
        </div>
        <div class="mb-2">
            <label>Thứ tự:</label>
            <input type="number" name="menuOrder" class="form-control" value="${menu.menuOrder}" />
        </div>
        <div class="mb-2">
            <label>Vị trí:</label>
            <input type="number" name="position" class="form-control" value="${menu.position}" />
        </div>
        <div class="mb-2">
            <label>Icon:</label>
            <input type="text" name="icon" class="form-control" value="${menu.icon}" />
        </div>
        <div class="mb-2">
            <label>IDName:</label>
            <input type="text" name="idName" class="form-control" value="${menu.IDName}" />
        </div>
        <div class="mb-2">
            <label>Item Target:</label>
            <input type="text" name="itemTarget" class="form-control" value="${menu.itemTarget}" />
        </div>
        <div class="form-check mb-2">
            <input type="checkbox" name="isActive" class="form-check-input" ${menu.isIsActive() ? "checked" : ""} />
            <label class="form-check-label">Hiển thị</label>
        </div>

        <button type="submit" class="btn btn-success">Lưu</button>
        <a href="${pageContext.request.contextPath}/admin/menu" class="btn btn-secondary">Hủy</a>
    </form>
</main>

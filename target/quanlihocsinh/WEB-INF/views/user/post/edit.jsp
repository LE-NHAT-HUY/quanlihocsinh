<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main class="main">

<h2>Sửa bài viết</h2>

<form method="post" action="post">
    <input type="hidden" name="action" value="edit">
    <input type="hidden" name="PostID" value="${post.postID}">

    <div class="mb-3">
        <label>Tiêu đề</label>
        <input type="text" name="Title" class="form-control" value="${post.title}" required>
    </div>

    <div class="mb-3">
        <label>Tóm tắt</label>
        <textarea name="Summary" class="form-control">${post.summary}</textarea>
    </div>

    <div class="mb-3">
        <label>Nội dung</label>
        <textarea name="Content" class="form-control" rows="5">${post.content}</textarea>
    </div>

    <div class="mb-3">
        <label>Đường dẫn ảnh</label>
        <input type="text" name="ImageUrl" class="form-control" value="${post.imageUrl}">
    </div>

    <div class="mb-3">
        <label>Thứ tự</label>
        <input type="number" name="PostOrder" class="form-control" value="${post.postOrder}">
    </div>

    <div class="form-check">
        <input type="checkbox" name="IsActive" class="form-check-input"
               ${post.active ? "checked" : ""}>
        <label class="form-check-label">Kích hoạt</label>
    </div>

    <div class="form-check">
        <input type="checkbox" name="IsFeatured" class="form-check-input"
               ${post.featured ? "checked" : ""}>
        <label class="form-check-label">Nổi bật</label>
    </div>

    <div class="mb-3 mt-3">
        <label>Người tạo</label>
        <input type="text" name="CreatedBy" class="form-control" value="${post.createdBy}">
    </div>

    <button class="btn btn-primary">Cập nhật</button>
</form>

</main>

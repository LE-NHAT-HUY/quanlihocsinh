<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main class="main">

<h2>Thêm bài viết</h2>

<form method="post" action="post">
    <input type="hidden" name="action" value="add">

    <div class="mb-3">
        <label>Tiêu đề</label>
        <input type="text" name="Title" class="form-control" required>
    </div>

    <div class="mb-3">
        <label>Tóm tắt</label>
        <textarea name="Summary" class="form-control"></textarea>
    </div>

    <div class="mb-3">
        <label>Nội dung</label>
        <textarea name="Content" class="form-control" rows="5"></textarea>
    </div>

    <div class="mb-3">
        <label>Đường dẫn ảnh</label>
        <input type="text" name="ImageUrl" class="form-control">
    </div>

    <div class="mb-3">
        <label>Thứ tự hiển thị</label>
        <input type="number" name="PostOrder" class="form-control" value="1">
    </div>

    <div class="form-check">
        <input type="checkbox" name="IsActive" class="form-check-input">
        <label class="form-check-label">Kích hoạt</label>
    </div>

    <div class="form-check">
        <input type="checkbox" name="IsFeatured" class="form-check-input">
        <label class="form-check-label">Nổi bật</label>
    </div>

    <div class="mb-3 mt-3">
        <label>Người tạo</label>
        <input type="text" name="CreatedBy" class="form-control" value="admin">
    </div>

    <button class="btn btn-primary">Lưu</button>
</form>

</main>

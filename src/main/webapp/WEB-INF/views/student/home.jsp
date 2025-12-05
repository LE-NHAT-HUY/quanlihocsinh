<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<main class="content">
    <div class="row g-3">

        <!-- CỘT TRÁI: GIỚI THIỆU + BẢN ĐỒ -->
        <div class="col-lg-8">

            <!-- Giới thiệu trường -->
            <div class="card card-shadow mb-3">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center mb-2">
                        <h5 class="card-title mb-0">Giới thiệu Trường THPT Anh Sơn I</h5>
                        <span class="badge-chip">
                            <i class="bi bi-award-fill"></i> Trường chuẩn quốc gia
                        </span>
                    </div>

                    <div class="row mt-2">
                        <div class="col-md-7">
                            <p class="mb-1">
                                <strong>Gần 60 năm phát triển</strong>, nhà trường đã đào tạo nhiều thế hệ học sinh
                                thành đạt, góp phần xây dựng quê hương Nghệ An.
                            </p>
                            <ul class="mb-2">
                                <li>Tỷ lệ tốt nghiệp THPT trên 98% hằng năm.</li>
                                <li>Nhiều học sinh đạt giải cấp tỉnh, cấp quốc gia.</li>
                                <li>Cơ sở vật chất hiện đại, môi trường thân thiện.</li>
                                <li>Đội ngũ giáo viên tâm huyết, chuyên môn cao.</li>
                            </ul>
                            <p class="mb-0 text-muted" style="font-size: 13px;">
                                Tầm nhìn: Trở thành trường chuẩn quốc gia, tiên phong đổi mới giáo dục miền Trung.
                            </p>
                        </div>
                        <div class="col-md-5">
                            <img
                                    src="${pageContext.request.contextPath}/assets/img/Untitled-1-1.jpg5_-1.jpg"
                                    alt="Trường THPT Anh Sơn I"
                                    class="intro-img" />

                                 </div>  

                    </div>
                </div>
            </div>

            <!-- Bản đồ -->
            <div class="card card-shadow">
                <div class="card-body">
                    <h6 class="card-title mb-2">Vị trí trường trên bản đồ</h6>
                    <div class="map-placeholder">
                        <div class="map-placeholder-header">
                            18°55'37.5"N 105°12'27.5"E — THPT Anh Sơn I, Nghệ An
                        </div>
                        <div class="map-fake mt-2 mb-2">
                            <div class="map-road"></div>
                            <div class="map-marker">
                                <i class="bi bi-geo-alt-fill"></i>
                            </div>
                        </div>
                        <small class="text-muted">
                            (Có thể nhúng Google Map thật bằng &lt;iframe&gt; tại khu vực này.)
                        </small>
                    </div>
                </div>
            </div>

        </div>

        <!-- CỘT PHẢI: THỐNG KÊ + TỔ CHUYÊN MÔN -->
        <div class="col-lg-4">

            <!-- Thống kê nhanh -->
            <div class="card card-shadow mb-3">
                <div class="card-body">
                    <h6 class="card-title mb-3">Thống kê nhanh</h6>
                    <div class="row g-2">
                        <div class="col-6">
                            <div class="p-2 bg-white rounded-3 border">
                                <div class="stat-value">
                                    <c:out value="${totalStudents}" default="--" />
                                </div>
                                <div class="stat-label">Học sinh</div>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="p-2 bg-white rounded-3 border">
                                <div class="stat-value">
                                    <c:out value="${totalTeachers}" default="--" />
                                </div>
                                <div class="stat-label">Giáo viên</div>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="p-2 bg-white rounded-3 border">
                                <div class="stat-value">
                                    <c:out value="${totalClasses}" default="--" />
                                </div>
                                <div class="stat-label">Lớp học</div>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="p-2 bg-white rounded-3 border">
                                <div class="stat-value">
                                    <c:out value="${totalSubjects}" default="--" />
                                </div>
                                <div class="stat-label">Môn học</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Tổ chuyên môn -->
            <div class="card card-shadow">
                <div class="card-body">
                    <h6 class="card-title mb-2">Thông tin tổ chuyên môn</h6>

                    <div class="mb-2">
                        <div class="fw-semibold">Ban giám hiệu</div>
                        <ul class="ps-3 mb-1" style="font-size: 13px;">
                            <li>Ngô Đình Anh, SĐT: 0937 858 693</li>
                            <li>Nguyễn Thanh Hằng, SĐT: 0986 557 575</li>
                            <li>Nguyễn Thị Hương, SĐT: 0885 858 586</li>
                        </ul>
                    </div>

                    <div class="mb-2">
                        <div class="fw-semibold">Tổ Ngoại ngữ</div>
                        <ul class="ps-3 mb-1" style="font-size: 13px;">
                            <li>Phạm Thế Báo, SĐT: 0382 737 481</li>
                            <li>Nguyễn Thị Đào, SĐT: 0994 748 423</li>
                        </ul>
                    </div>

                    <div class="mb-2">
                        <div class="fw-semibold">Khoa học Xã hội</div>
                        <ul class="ps-3 mb-1" style="font-size: 13px;">
                            <li>Nguyễn Thị Sương, SĐT: 0928 978 287</li>
                        </ul>
                    </div>

                    <div class="mb-2">
                        <div class="fw-semibold">Tổ Toán</div>
                        <ul class="ps-3 mb-1" style="font-size: 13px;">
                            <li>Thành Công, SĐT: 0576 868 686</li>
                        </ul>
                    </div>

                    <div class="mb-2">
                        <div class="fw-semibold">Tổ Giáo dục chính trị</div>
                        <ul class="ps-3 mb-1" style="font-size: 13px;">
                            <li>Nguyễn Thị Minh Anh, SĐT: 0585 959 584</li>
                            <li>Nguyễn Văn Tiến, SĐT: 0986 546 574</li>
                        </ul>
                    </div>

                    <div>
                        <div class="fw-semibold">Tổ Giáo dục thể chất</div>
                        <ul class="ps-3 mb-0" style="font-size: 13px;">
                            <li>Phạm Đình Toàn, SĐT: 0385 858 484</li>
                        </ul>
                    </div>

                </div>
            </div>

        </div>

    </div>
</main>

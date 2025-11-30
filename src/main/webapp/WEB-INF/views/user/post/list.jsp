<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="row g-3">

    <!-- CỘT TRÁI: Giới thiệu + Bản đồ -->
    <div class="col-lg-8">

        <div class="card card-shadow mb-3">
            <div class="card-body">
                <h5 class="card-title">Giới thiệu Trường THPT Anh Sơn I</h5>
                <p>Trường THPT Anh Sơn I với gần 60 năm phát triển, đào tạo nhiều thế hệ học sinh thành đạt.</p>
            </div>
        </div>

        <div class="card card-shadow">
            <div class="card-body">
                <h6 class="card-title">Vị trí trường trên bản đồ</h6>
                <div class="map-placeholder">
                    <div class="map-placeholder-header">18°55'37.5"N 105°12'27.5"E — THPT Anh Sơn I</div>
                    <div class="map-fake mt-2 mb-2">
                        <div class="map-road"></div>
                        <div class="map-marker"><i class="bi bi-geo-alt-fill"></i></div>
                    </div>
                    <small class="text-muted">(Có thể nhúng Google Map thật bằng &lt;iframe&gt; tại đây.)</small>
                </div>
            </div>
        </div>

    </div>

    <!-- CỘT PHẢI: Thống kê -->
    <div class="col-lg-4">

        <div class="card card-shadow mb-3">
            <div class="card-body">
                <h6 class="card-title mb-3">Thống kê nhanh</h6>
                <div class="row g-2">
                    <div class="col-6">
                        <div class="p-2 bg-white rounded-3 border">
                            <div class="stat-value"><c:out value="${totalStudents}" default="--" /></div>
                            <div class="stat-label">Học sinh</div>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="p-2 bg-white rounded-3 border">
                            <div class="stat-value"><c:out value="${totalTeachers}" default="--" /></div>
                            <div class="stat-label">Giáo viên</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

</div>

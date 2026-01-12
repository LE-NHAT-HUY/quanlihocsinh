<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/student-profile.css" />

<div class="profile-page student-profile">
    <div class="header-nav mb-3">
        <c:forEach var="i" begin="1" end="8">
            <button type="button" class="nav-btn" onclick="scrollToAndOpen(<c:out value='${i}'/>)">
                <c:out value="${i}"/>
            </button>
        </c:forEach>
        <button type="button" class="all-btn" onclick="toggleAll()">Tất cả</button>
    </div>

    <div class="accordion-list">
        <div id="section-1" class="accordion-item shadow-sm">
            <button class="accordion-header" onclick="toggleAccordion(1)">
                <span id="icon-1" class="rotate-icon rotate">▶</span>
                <span class="num-badge">1</span>
                <strong>Thông tin chung</strong>
            </button>
            <div id="content-1" class="accordion-content active">
                <div class="profile-top">
                    <div class="profile-avatar">
                        <img src="${pageContext.request.contextPath}/assets/img/${student.images}" 
                             onerror="this.src='https://congsv.vinhuni.edu.vn/assets/layout/images/male.png'" />
                    </div>
                    <div class="profile-basic">
                        <div class="field-row small">
                            <div class="form-group">
                                <label>Mã học sinh</label>
                                <div class="info-display"><c:out value="${student.studentID}"/></div>
                            </div>
                            <div class="form-group">
                                <label>Họ và tên</label>
                                <div class="info-display"><c:out value="${student.fullName}"/></div>
                            </div>
                        </div>
                        <div class="field-row small">
                            <div class="form-group">
                                <label>Ngày sinh</label>
                                <div class="info-display"><fmt:formatDate value="${student.birth}" pattern="dd/MM/yyyy" /></div>
                            </div>
                            <div class="form-group">
                                <label>Giới tính</label>
                                <div class="info-display"><c:out value="${student.gender}"/></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="section-block mt-3">
                    <div class="field-row">
                        <div class="form-group"><label>Dân tộc</label><div class="info-display"><c:out value="${student.nation}"/></div></div>
                        <div class="form-group"><label>Tôn giáo</label><div class="info-display"><c:out value="${student.religion}"/></div></div>
                        <div class="form-group"><label>Quốc tịch</label><div class="info-display">Việt Nam</div></div>
                    </div>
                </div>
            </div>
        </div>

        <div id="section-2" class="accordion-item shadow-sm">
            <button class="accordion-header" onclick="toggleAccordion(2)">
                <span id="icon-2" class="rotate-icon">▶</span>
                <span class="num-badge">2</span>
                <strong>Quan hệ thân nhân</strong>
            </button>
            <div id="content-2" class="accordion-content">
                <div class="section-title">THÔNG TIN CHA</div>
                <div class="field-row">
                    <div class="form-group"><label>Họ và tên cha</label><div class="info-display">Chưa cập nhật</div></div>
                    <div class="form-group"><label>Năm sinh</label><div class="info-display">Chưa cập nhật</div></div>
                </div>
                <div class="section-title">THÔNG TIN MẸ</div>
                <div class="field-row">
                    <div class="form-group"><label>Họ và tên mẹ</label><div class="info-display">Chưa cập nhật</div></div>
                    <div class="form-group"><label>Số điện thoại</label><div class="info-display">Chưa cập nhật</div></div>
                </div>
            </div>
        </div>

        <div id="section-3" class="accordion-item shadow-sm">
            <button class="accordion-header" onclick="toggleAccordion(3)">
                <span id="icon-3" class="rotate-icon">▶</span>
                <span class="num-badge">3</span>
                <strong>Đối tượng đào tạo</strong>
            </button>
            <div id="content-3" class="accordion-content">
                <div class="field-row">
                    <div class="form-group"><label>Thành phần xuất thân</label><div class="info-display">Chưa cập nhật</div></div>
                    <div class="form-group"><label>Đối tượng ưu tiên</label><div class="info-display">Không</div></div>
                </div>
            </div>
        </div>

        <div id="section-4" class="accordion-item shadow-sm">
            <button class="accordion-header" onclick="toggleAccordion(4)">
                <span id="icon-4" class="rotate-icon">▶</span>
                <span class="num-badge">4</span>
                <strong>Thông tin Đoàn</strong>
            </button>
            <div id="content-4" class="accordion-content">
                <div class="field-row">
                    <div class="form-group"><label>Là Đoàn viên</label><div class="info-display">Chưa cập nhật</div></div>
                    <div class="form-group"><label>Ngày vào Đoàn</label><div class="info-display">Chưa cập nhật</div></div>
                </div>
            </div>
        </div>

        <div id="section-5" class="accordion-item shadow-sm">
            <button class="accordion-header" onclick="toggleAccordion(5)">
                <span id="icon-5" class="rotate-icon">▶</span>
                <span class="num-badge">5</span>
                <strong>Thông tin bảo hiểm</strong>
            </button>
            <div id="content-5" class="accordion-content">
                <div class="field-row">
                    <div class="form-group"><label>Số thẻ BHYT</label><div class="info-display">Chưa cập nhật</div></div>
                    <div class="form-group"><label>Hạn sử dụng</label><div class="info-display">Chưa cập nhật</div></div>
                </div>
            </div>
        </div>

        <div id="section-6" class="accordion-item shadow-sm">
            <button class="accordion-header" onclick="toggleAccordion(6)">
                <span id="icon-6" class="rotate-icon">▶</span>
                <span class="num-badge">6</span>
                <strong>Quá trình học tập</strong>
            </button>
            <div id="content-6" class="accordion-content">
                <table class="document-table">
                    <thead>
                        <tr><th>STT</th><th>Thời gian</th><th>Tên trường</th><th>Xếp loại</th></tr>
                    </thead>
                    <tbody>
                        <tr><td colspan="4" class="text-center text-muted">Chưa có dữ liệu quá trình học tập</td></tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div id="section-7" class="accordion-item shadow-sm">
            <button class="accordion-header" onclick="toggleAccordion(7)">
                <span id="icon-7" class="rotate-icon">▶</span>
                <span class="num-badge">7</span>
                <strong>Liên hệ</strong>
            </button>
            <div id="content-7" class="accordion-content">
                <div class="field-row">
                    <div class="form-group"><label>Điện thoại</label><div class="info-display"><c:out value="${student.numberPhone}"/></div></div>
                    <div class="form-group"><label>Địa chỉ hiện tại</label><div class="info-display"><c:out value="${student.address}"/></div></div>
                </div>
            </div>
        </div>

        <div id="section-8" class="accordion-item shadow-sm">
            <button class="accordion-header" onclick="toggleAccordion(8)">
                <span id="icon-8" class="rotate-icon">▶</span>
                <span class="num-badge">8</span>
                <strong>Hồ sơ số hóa</strong>
            </button>
            <div id="content-8" class="accordion-content" style="padding:0;">
                <table class="document-table">
                    <thead>
                        <tr><th>STT</th><th>Tên hồ sơ</th><th>Đính kèm</th></tr>
                    </thead>
                    <tbody>
                        <tr><td>1</td><td>Giấy khai sinh</td><td><button class="btn btn-sm btn-light">Chọn file</button></td></tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script>
    function toggleAccordion(num) {
        var content = document.getElementById('content-' + num);
        var icon = document.getElementById('icon-' + num);
        if (!content) return;
        
        if (content.classList.contains('active')) {
            content.classList.remove('active');
            if(icon) icon.classList.remove('rotate');
        } else {
            content.classList.add('active');
            if(icon) icon.classList.add('rotate');
        }
    }

    function scrollToAndOpen(num) {
        var section = document.getElementById('section-' + num);
        var content = document.getElementById('content-' + num);
        var icon = document.getElementById('icon-' + num);
        
        if (section && content) {
            content.classList.add('active');
            if(icon) icon.classList.add('rotate');
            section.scrollIntoView({ behavior: 'smooth', block: 'start' });
            
            var navButtons = document.querySelectorAll('.nav-btn');
            for(var i = 0; i < navButtons.length; i++) {
                if ((i + 1) === num) navButtons[i].classList.add('active');
                else navButtons[i].classList.remove('active');
            }
        }
    }

    function toggleAll() {
        var contents = document.querySelectorAll('.accordion-content');
        var icons = document.querySelectorAll('.rotate-icon');
        var isAnyClosed = false;
        
        for (var i = 0; i < contents.length; i++) {
            if (!contents[i].classList.contains('active')) {
                isAnyClosed = true;
                break;
            }
        }
        
        for (var j = 0; j < contents.length; j++) {
            if (isAnyClosed) {
                contents[j].classList.add('active');
                if(icons[j]) icons[j].classList.add('rotate');
            } else {
                contents[j].classList.remove('active');
                if(icons[j]) icons[j].classList.remove('rotate');
            }
        }
    }
</script>
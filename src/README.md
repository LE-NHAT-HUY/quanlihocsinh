===============================
HƯỚNG DẪN LÀM VIỆC VỚI DỰ ÁN
===============================

1. Đẩy code lên GitHub (lần đầu)
--------------------------------
cd đường/dẫn/đến/project
git init
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/username/ten-repo.git
git branch -M main
git push -u origin main

2. Đẩy code lên GitHub (các lần sau)
------------------------------------
git add .
git commit -m "Mô tả thay đổi"
git push

3. Kéo code về từ GitHub
-----------------------
git pull origin main

4. Build project với Maven
-------------------------
mvn clean package

5. Chạy project với Tomcat (plugin Tomcat7 Maven)
------------------------------------------------
mvn tomcat7:run

6. Các lệnh Maven khác
----------------------
- Cập nhật dependency: mvn clean install
- Chạy tests: mvn test
- Xem thông tin project: mvn help:effective-pom

7. Ghi chú
----------
- Thay https://github.com/username/ten-repo.git bằng đường dẫn repo của bạn.
- Nếu GitHub yêu cầu token khi push, dùng Personal Access Token thay cho mật khẩu.
